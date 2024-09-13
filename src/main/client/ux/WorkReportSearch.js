/* 
 * Copyright (C) 2017 Sonicle S.r.l.
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License version 3 as published by
 * the Free Software Foundation with the addition of the following permission
 * added to Section 15 as permitted in Section 7(a): FOR ANY PART OF THE COVERED
 * WORK IN WHICH THE COPYRIGHT IS OWNED BY SONICLE, SONICLE DISCLAIMS THE
 * WARRANTY OF NON INFRINGEMENT OF THIRD PARTY RIGHTS.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, see http://www.gnu.org/licenses or write to
 * the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301 USA.
 *
 * You can contact Sonicle S.r.l. at email address sonicle[at]sonicle[dot]com
 *
 * The interactive user interfaces in modified source and object code versions
 * of this program must display Appropriate Legal Notices, as required under
 * Section 5 of the GNU Affero General Public License version 3.
 *
 * In accordance with Section 7(b) of the GNU Affero General Public License
 * version 3, these Appropriate Legal Notices must retain the display of the
 * Sonicle logo and Sonicle copyright notice. If the display of the logo is not
 * reasonably feasible for technical reasons, the Appropriate Legal Notices must
 * display the words "Copyright (C) 2017 Sonicle S.r.l.".
 */
Ext.define('Sonicle.webtop.drm.ux.WorkReportSearch', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.wtdrmworkreportsearch',
	uses: [
		'Sonicle.Date',
		'WTA.model.CausalLkp'
	],
	layout: 'column',
	referenceHolder: true,
	sid: null,
	useStatisticCustomer: null,
	
	viewModel: {
		data: {
			operatorId: null,
			companyId: null,
			realCustomerId: null,
			statisticCustomerId: null,
			from: Ext.Date.getFirstDateOfMonth(new Date()),
			to: new Date(),
			referenceNo: null,
			causalId: null,
			businessTripId: null,
			description: null,
			notes: null,
			docStatusId: null,
			chargeTo: null,
			year: null,
			number: null
		}
	},
	
	initComponent: function () {
		var me = this;
		me.callParent(arguments);

		me.add({
			xtype: 'wtform',
			defaults: {
				labelWidth: 120
			},
			items: [
				WTF.localCombo('id', 'desc', {
					reference: 'flduser',
					bind: '{operatorId}',
					anyMatch: true,
					tabIndex: 101,
					listConfig: {
						escapeDisplay: true
					},
					store: {
						autoLoad: true,
						model: 'WTA.model.Simple',
						proxy: WTF.proxy(me.sid, 'LookupOperators'),
						listeners: {
							load: function (s) {
								if (s.loadCount === 1) {
									var meta = s.getProxy().getReader().metaData;
									if(meta.selected) {
										me.getViewModel().set('operatorId', meta.selected);
										WTU.loadWithExtraParams(me.lookupReference('fldcompany').getStore(), {
											operator: meta.selected
										});
										WTU.loadWithExtraParams(me.lookupReference('fldmasterdata').getStore(), {
											operator: meta.selected
										});
										WTU.loadWithExtraParams(me.lookupReference('fldstatmasterdata').getStore(), {
											operator: meta.selected,
											realCustomer: null
										});
										WTU.loadWithExtraParams(me.lookupReference('fldcausal').getStore());
									}
								}
							}
						}
					},
					listeners: {
						select: function (s, r) {
							WTU.loadWithExtraParams(me.lookupReference('fldcompany').getStore(), {
								operator: r.id
							});
							WTU.loadWithExtraParams(me.lookupReference('fldmasterdata').getStore(), {
								operator: r.id
							});
							WTU.loadWithExtraParams(me.lookupReference('fldstatmasterdata').getStore(), {
								operator: r.id
							});
							WTU.loadWithExtraParams(me.lookupReference('fldcausal').getStore());
						}
					},
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: WT.res(me.sid, 'workReport.fld-nominative.lbl'),
					width: '420px'
				}),
				WTF.localCombo('id', 'desc', {
					reference: 'fldmasterdata',
					bind: '{realCustomerId}',
					autoLoadOnValue: true,
					tabIndex: 103,
					listConfig: {
						escapeDisplay: true
					},
					store: {
						model: 'WTA.model.Simple',
						proxy: WTF.proxy(me.sid, 'LookupRealCustomers', null, {
							extraParams: {
								operator: null
							}
						})
					},
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: WT.res(me.sid, 'workReport.fld-realCustomer.lbl'),
					width: '420px',
					listeners: {
						select: function (s, r) {
							me.getViewModel().set('statisticCustomerId', null);
							WTU.loadWithExtraParams(me.lookupReference('fldstatmasterdata').getStore(), {
								realCustomer: r.id
							});
							WTU.loadWithExtraParams(me.lookupReference('fldcausal').getStore(), {
								masterDataId: r.id
							});
						}
					}
				}),
				{
					xtype: 'datefield',
					startDay: WT.getStartDay(),
					reference: 'fldfrom',
					bind: '{from}',
					tabIndex: 105,
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: WT.res(me.sid, 'workReport.fld-fromDate.lbl'),
					width: '420px'
				},
				{
					xtype: 'textfield',
					reference: 'fldreference',
					bind: '{referenceNo}',
					tabIndex: 107,
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: WT.res(me.sid, 'workReport.fld-reference.lbl'),
					width: '420px'
				},
				WTF.localCombo('id', 'desc', {
					reference: 'fldbusinesstrip',
					bind: '{businessTripId}',
					tabIndex: 109,
					store: {
						autoLoad: true,
						model: 'WTA.model.Simple',
						proxy: WTF.proxy(me.sid, 'LookupBusinessTrip')
					},
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: WT.res(me.sid, 'workReport.fld-businesstrip.lbl'),
					width: '420px'
				}),
				{
					xtype: 'textfield',
					reference: 'flddescription',
					bind: '{description}',
					tabIndex: 110,
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: WT.res(me.sid, 'workReport.fld-description.lbl'),
					width: '420px'
				},
				WTF.localCombo('id', 'desc', {
					reference: 'flddocstatus',
					bind: '{docStatusId}',
					tabIndex: 112,
					store: {
						autoLoad: true,
						model: 'WTA.model.Simple',
						proxy: WTF.proxy(me.sid, 'LookupDocStatuses')
					},
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: WT.res(me.sid, 'workReport.fld-status.lbl'),
					width: '420px'
				}),
				{
					xtype: 'numberfield',
					reference: 'fldyear',
					bind: '{year}',
					tabIndex: 114,
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: WT.res(me.sid, 'workReport.fld-year.lbl'),
					width: '420px'
				},
				{
					xtype: 'button',
					text: WT.res(me.sid, 'btn-search.lbl'),
					handler: function () {
						me.extractData();
					}
				}
			]
		},
		{
			xtype: 'wtform',
			defaults: {
				labelWidth: 120
			},
			items: [
				WTF.localCombo('id', 'desc', {
					reference: 'fldcompany',
					bind: '{companyId}',
					tabIndex: 102,
					listConfig: {
						escapeDisplay: true
					},
					store: {
						autoLoad: false,
						model: 'WTA.model.Simple',
						proxy: WTF.proxy(me.sid, 'LookupCompanies', null, {
							extraParams: {
								operator: null
							}
						}),
						listeners: {
							load: function (s) {
								var meta = s.getProxy().getReader().metaData;
								if (meta.selected) {
									me.getViewModel().set('companyId', meta.selected);
								}
							}
						}
					},
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: WT.res(me.sid, 'workReport.fld-company.lbl'),
					width: '420px'
				}),
				WTF.remoteCombo('id', 'desc', {
					reference: 'fldstatmasterdata',
					bind: '{statisticCustomerId}',
					autoLoadOnValue: true,
					tabIndex: 104,
					listConfig: {
						escapeDisplay: true
					},
					store: {
						autoLoad: true,
						model: 'WTA.model.Simple',
						proxy: WTF.proxy(me.sid, (me.useStatisticCustomer === true) ? 'LookupStatisticCustomers' : 'LookupRealCustomers', null, {
							extraParams: {
								realCustomer: null,
								operator: null
							}
						}),
						listeners: {
							load: function (s) {
								var meta = s.getProxy().getReader().metaData;
								if(meta){
									if (meta.selected) {
										me.getViewModel().set('statisticCustomerId', meta.selected);
									}
								}else {
									me.lookupReference('fldstatmasterdata').setValue(me.lookupReference('fldmasterdata').getValue());
								}
							}
						}
					},
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: WT.res(me.sid, 'workReport.fld-customerStat.lbl'),
					width: '420px'
				}),
				{
					xtype: 'datefield',
					startDay: WT.getStartDay(),
					reference: 'fldto',
					bind: '{to}',
					tabIndex: 106,
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: WT.res(me.sid, 'workReport.fld-toDate.lbl'),
					width: '420px'
				},
				WTF.remoteCombo('id', 'desc', {
					reference: 'fldcausal',
					bind: '{causalId}',
					autoLoadOnValue: false,
					tabIndex: 108,
					listConfig: {
						escapeDisplay: true
					},
					store: {
						model: 'WTA.model.CausalLkp',
						proxy: WTF.proxy(WT.ID, 'LookupCausals', null, {
							extraParams: {
								profileId: null,
								masterDataId: null
							}
						}),
						filters: [{
							filterFn: function (rec) {
								if (rec.get('readOnly')) {
									if (rec.getId() !== me.lookupReference('fldcausal').getValue()) {
										return null;
									}
								}
								return rec;
							}
						}],
						listeners: {
							beforeload: function(s,op) {
								WTU.applyExtraParams(op.getProxy(), {
									profileId: WT.toPid(WT.getVar('domainId'), me.getViewModel().get('operatorId'))
								});
							}
						}
					},
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: WT.res(me.sid, 'workReport.fld-confirmation.lbl'),
					width: '420px'
				}),
				{
					xtype: 'soplaceholderfield'
				},
				{
					xtype: 'textfield',
					reference: 'fldnotes',
					bind: '{notes}',
					tabIndex: 111,
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: WT.res(me.sid, 'workReport.fld-notes.lbl'),
					width: '420px'
				},
				WTF.localCombo('id', 'desc', {
					reference: 'fldchargeto',
					bind: '{chargeTo}',
					tabIndex: 113,
					store: Ext.create('Sonicle.webtop.drm.store.Charged', {
						autoLoad: true
					}),
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: WT.res(me.sid, 'gpWorkReport.chargeTo.lbl'),
					width: '420px'
				}),
				{
					xtype: 'numberfield',
					reference: 'fldnumber',
					bind: '{number}',
					tabIndex: 115,
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: WT.res(me.sid, 'workReport.fld-number.lbl'),
					width: '420px'
				}
			]
		});
	},
	
	extractData: function () {
		var me = this,
			SoDate = Sonicle.Date;

		var query = {
			companyId: me.lookupReference('fldcompany').getValue(),
			operatorId: me.lookupReference('flduser').getValue(),
			customerId: me.lookupReference('fldmasterdata').getValue(),
			customerStatId: me.lookupReference('fldstatmasterdata').getValue(),
			fromDate: SoDate.format(me.lookupReference('fldfrom').getValue(), 'Y-m-d'),
			toDate: SoDate.format(me.lookupReference('fldto').getValue(), 'Y-m-d'),
			referenceNo: me.lookupReference('fldreference').getValue(),
			number: me.lookupReference('fldnumber').getValue(),
			businessTripId: me.lookupReference('fldbusinesstrip').getValue(),
			description: me.lookupReference('flddescription').getValue(),
			notes: me.lookupReference('fldnotes').getValue(),
			docStatusId: me.lookupReference('flddocstatus').getValue(),
			chargeTo: me.lookupReference('fldchargeto').getValue(),
			year: me.lookupReference('fldyear').getValue(),
			causalId: me.lookupReference('fldcausal').getValue()			
		};
		
		me.fireEvent('search', me, query);
	},
	
	getData: function () {
		var me = this,
			SoDate = Sonicle.Date;

		var query = {
			companyId: me.lookupReference('fldcompany').getValue(),
			operatorId: me.lookupReference('flduser').getValue(),
			customerId: me.lookupReference('fldmasterdata').getValue(),
			customerStatId: me.lookupReference('fldstatmasterdata').getValue(),
			fromDate: SoDate.format(me.lookupReference('fldfrom').getValue(), 'Y-m-d'),
			toDate: SoDate.format(me.lookupReference('fldto').getValue(), 'Y-m-d'),
			referenceNo: me.lookupReference('fldreference').getValue(),
			number: me.lookupReference('fldnumber').getValue(),
			businessTripId: me.lookupReference('fldbusinesstrip').getValue(),
			description: me.lookupReference('flddescription').getValue(),
			notes: me.lookupReference('fldnotes').getValue(),
			docStatusId: me.lookupReference('flddocstatus').getValue(),
			chargeTo: me.lookupReference('fldchargeto').getValue(),
			year: me.lookupReference('fldyear').getValue(),
			causalId: me.lookupReference('fldcausal').getValue()			
		};
		
		return query;
	},
	
	getOperatorId: function () {
		return this.lookupReference('flduser').getValue();
	}
});
