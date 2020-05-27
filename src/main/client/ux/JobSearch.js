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
Ext.define('Sonicle.webtop.drm.ux.JobSearch', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.wtdrmjobsearch',
	uses: [
		'Sonicle.Date'
	],
	layout: 'column',
	referenceHolder: true,
	sid: null,
	useStatisticCustomer: null,
	
	activeType: 'jobs',
	
	viewModel: {
		data: {
			operatorId: null,
			companyId: null,
			realCustomerId: null,
			statisticCustomerId: null,
			startDate: new Date(),
			endDate: new Date(),
			activityId: null,
			title: null
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
						}
					},
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: WT.res(me.sid, 'job.fld-nominative.lbl'),
					width: '420px'
				}),
				WTF.remoteCombo('id', 'desc', {
					reference: 'fldmasterdata',
					bind: '{realCustomerId}',
					autoLoadOnValue: true,
					tabIndex: 104,
					queryMode: 'remote',
					triggerAction: 'query',
					store: {
						model: 'WTA.model.Simple',
						proxy: WTF.proxy(me.sid, 'LookupRealCustomers', null, {
							extraParams: {
								operator: null
							}
						}),
						listeners: {
							load: function (s) {
								var meta = s.getProxy().getReader().metaData;
								if(meta){
									if (meta.selected) {
										me.getViewModel().set('realCustomerId', meta.selected);
									}
								}else {
									me.lookupReference('fldmasterdata').setValue(me.lookupReference('fldmasterdata').getValue());
								}
							}
						}
					},
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: WT.res(me.sid, 'job.fld-realCustomer.lbl'),
					width: '420px',
					listeners: {
						select: function (s, r) {
							me.getViewModel().set('statisticCustomerId', null);
							WTU.loadWithExtraParams(me.lookupReference('fldstatmasterdata').getStore(), {
								realCustomer: r.id
							});
						}
					}
				}),								
				{
					xtype: 'textfield',
					reference: 'fldtitle',
					bind: '{title}',
					tabIndex: 110,
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: WT.res(me.sid, 'job.fld-title.lbl'),
					width: '420px'
				},
				{
					xtype: 'datefield',
					startDay: WT.getStartDay(),
					reference: 'fldstartdate',
					bind: '{startDate}',
					tabIndex: 105,
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: WT.res(me.sid, 'job.fld-startDate.lbl'),
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
					fieldLabel: WT.res(me.sid, 'job.fld-company.lbl'),
					width: '420px'
				}),
				WTF.remoteCombo('id', 'desc', {
					reference: 'fldstatmasterdata',
					bind: '{statisticCustomerId}',
					autoLoadOnValue: true,
					tabIndex: 104,
					store: {
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
					fieldLabel: WT.res(me.sid, 'job.fld-customerStat.lbl'),
					width: '420px'
				}),	
				WTF.localCombo('activityId', 'description', {
					reference: 'fldactivity',
					bind: '{activityId}',
					tabIndex: 109,
					autoLoadOnValue: true,
					store: {
						autoLoad: true,
						model: 'WTA.model.Simple',
						proxy: WTF.proxy(me.sid, 'LookupActivities', null, {
							extraParams: {
								type: null
							}
						}),
						listeners: {
							beforeload: function(s, op) {
								WTU.applyExtraParams(op.getProxy(), {
									type: me.activeType
								});
							}
						}
					},
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: WT.res(me.sid, 'job.fld-activity.lbl'),
					width: '420px'
				}),
				{
					xtype: 'datefield',
					startDay: WT.getStartDay(),
					reference: 'fldenddate',
					bind: '{endDate}',
					tabIndex: 105,
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: WT.res(me.sid, 'job.fld-endDate.lbl'),
					width: '420px'
				}
			]
		});
	},
		
	extractData: function () {
		var me = this;
			
		var query = me.getData();
		
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
			startDate: SoDate.format(me.lookupReference('fldstartdate').getValue(), 'Y-m-d'),
			endDate: SoDate.format(me.lookupReference('fldenddate').getValue(), 'Y-m-d'),
			title: me.lookupReference('fldtitle').getValue(),
			activityId: me.lookupReference('fldactivity').getValue()
		};
		
		return query;
	},
	
	getOperatorId: function () {
		return this.lookupReference('flduser').getValue();
	}
});
