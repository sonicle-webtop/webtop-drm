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
Ext.define('Sonicle.webtop.drm.ux.TicketSearch', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.wtdrmticketsearch',
	uses: [
		'Sonicle.Date'
	],
	layout: 'column',
	referenceHolder: true,
	sid: null,
	s: null,
	useStatisticCustomer: null,
	
	viewModel: {
		data: {
			companyId: null,
			fromOperatorId: null,
			toOperatorId: null,
			realCustomerId: null,
			statisticCustomerId: null,
			startDate: null,
			endDate: null,
			ticketCategoryId: null,
			title: null,
			statusId: null,
			priorityId: null
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
					fieldLabel: WT.res(me.sid, 'ticket.fld-company.lbl'),
					width: '420px'
				}), 
				WTF.localCombo('userId', 'displayName', {
					reference: 'fldfromoperator',
					bind: '{fromOperatorId}',
					anyMatch: true,
					tabIndex: 101,
					listConfig: {
						escapeDisplay: true
					},
					store: {
						autoLoad: true,
						model: 'WTA.model.Simple',
						proxy: WTF.proxy(me.sid, 'LookupAllOperators')
					},
					listeners: {
						select: function (s, r) {
							/*
							WTU.loadWithExtraParams(me.lookupReference('fldcompany').getStore(), {
								operator: r.id
							});
							WTU.loadWithExtraParams(me.lookupReference('fldmasterdata').getStore(), {
								operator: r.id
							});
							WTU.loadWithExtraParams(me.lookupReference('fldstatmasterdata').getStore(), {
								operator: r.id
							});
							*/
						}
					},
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: WT.res(me.sid, 'ticket.fld-fromOperator.lbl'),
					width: '420px'
				}),
				WTF.remoteCombo('id', 'desc', {
					reference: 'fldmasterdata',
					bind: '{realCustomerId}',
					autoLoadOnValue: true,
					tabIndex: 104,
					queryMode: 'remote',
					triggerAction: 'query',
					listConfig: {
						escapeDisplay: true
					},
					store: {
						autoLoad: true,
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
					fieldLabel: WT.res(me.sid, 'ticket.fld-realCustomer.lbl'),
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
					fieldLabel: WT.res(me.sid, 'ticket.fld-title.lbl'),
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
					fieldLabel: WT.res(me.sid, 'ticket.fld-startDate.lbl'),
					width: '420px'
				},
				WTF.localCombo('id', 'desc', {
					reference: 'fldstatus',
					bind: '{statusId}',
					tabIndex: 112,
					store: {
						autoLoad: true,
						model: 'WTA.model.Simple',
						proxy: WTF.proxy(me.sid, 'LookupDocStatuses')
					},
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: WT.res(me.sid, 'ticket.fld-status.lbl'),
					width: '420px'
				}),
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
				{
					xtype: 'soplaceholderfield'
				},
				WTF.localCombo('id', 'desc', {
					reference: 'fldtooperator',
					bind: '{toOperatorId}',
					anyMatch: true,
					tabIndex: 101,
					listConfig: {
						escapeDisplay: true
					},
					store: {
						autoLoad: true,
						model: 'WTA.model.Simple',
						proxy: WTF.proxy(me.sid, 'LookupOperators')	,
						listeners: {
							load: function (s) {
								if (s.loadCount >= 1) {
									// var meta = s.getProxy().getReader().metaData;
									// if(meta.selected) {
										// me.getViewModel().set('toOperatorId', meta.selected);
										me.getViewModel().set('toOperatorId', WT.getVar('userId'));
										
										WTU.loadWithExtraParams(me.lookupReference('fldcompany').getStore(), {
											operator: WT.getVar('userId')
										});
										WTU.loadWithExtraParams(me.lookupReference('fldmasterdata').getStore(), {
											operator: WT.getVar('userId')
										});
										WTU.loadWithExtraParams(me.lookupReference('fldstatmasterdata').getStore(), {
											operator: WT.getVar('userId'),
											realCustomer: null
										});		
									// }																									
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
					fieldLabel: WT.res(me.sid, 'ticket.fld-toOperator.lbl'),
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
					fieldLabel: WT.res(me.sid, 'job.fld-customerStat.lbl'),
					width: '420px'
				}),
				WTF.localCombo('id', 'desc', {
					reference: 'fldticketcategory',
					bind: '{ticketCategoryId}',
					tabIndex: 109,
					listConfig: {
						escapeDisplay: true
					},
					store: {
						autoLoad: true,
						model: 'WTA.model.Simple',
						proxy: WTF.proxy(me.sid, 'LookupTicketCategories')
					},
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: WT.res(me.sid, 'ticket.fld-ticketCategory.lbl'),
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
				},
				WTF.lookupCombo('id', 'desc', {
					reference: 'fldpriority',
					bind: '{priorityId}',
					store: Ext.create('Sonicle.webtop.drm.store.PriorityType', {
						autoLoad: true
					}),
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: WT.res(me.sid, 'ticket.fld-priority.lbl'),
					width: '420px',
					tabIndex: 105

				})
			]
		});
		
		// setto default status
		me.getViewModel().set('statusId', me.s.getVar('ticketDefaultStatus'));
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
			fromOperatorId: me.lookupReference('fldfromoperator').getValue(),
			toOperatorId: me.lookupReference('fldtooperator').getValue(),
			customerId: me.lookupReference('fldmasterdata').getValue(),
			customerStatId: me.lookupReference('fldstatmasterdata').getValue(),
			startDate: SoDate.format(me.lookupReference('fldstartdate').getValue(), 'Y-m-d'),
			endDate: SoDate.format(me.lookupReference('fldenddate').getValue(), 'Y-m-d'),
			title: me.lookupReference('fldtitle').getValue(),
			ticketCategoryId: me.lookupReference('fldticketcategory').getValue(),
			statusId: me.lookupReference('fldstatus').getValue(),
			priorityId: me.lookupReference('fldpriority').getValue()
		};
		
		return query;
	},
	
	getFromOperatorId: function () {
		return this.lookupReference('fldfromopertor').getValue();
	}
});
