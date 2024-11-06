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
Ext.define('Sonicle.webtop.drm.ux.TimetableRequestSearch', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.wtdrmtimetablerequestsearch',
	uses: [
		'Sonicle.Date'
	],
	layout: 'column',
	referenceHolder: true,
	sid: null,
	
	viewModel: {
		data: {
			userId: null,
			companyId: null,
			type: null,
			fromDate: Ext.Date.add(new Date(), Ext.Date.DAY, -30),
			toDate: null,
			status: null,
			result: null
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
					bind: '{userId}',
					emptyText: WT.res(me.sid, 'timetableRequestSearch.operator-emptytext.lbl'),
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
										me.getViewModel().set('userId', meta.selected);
										WTU.loadWithExtraParams(me.lookupReference('fldcompany').getStore(), {
											operator: meta.selected
										});
									}
								}
							}
						}
					},
					listeners: {
						select: function (cmb, r) {
							WTU.loadWithExtraParams(me.lookupReference('fldcompany').getStore(), {
								operator: r.id
							});
						},
						change: function (cmb, newval, oldaval) {
							WTU.loadWithExtraParams(me.lookupReference('fldcompany').getStore(), {
								operator: null
							});
						}
					},
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: WT.res(me.sid, 'timetableRequestSearch.operator.lbl'),
					width: '420px',
					tabIndex: 101
				}),
				{
					xtype: 'datefield',
					startDay: WT.getStartDay(),
					reference: 'fldfrom',
					bind: '{fromDate}',
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: WT.res(me.sid, 'timetableRequestSearch.fromDate.lbl'),
					width: '420px',
					tabIndex: 103
				},
				WTF.localCombo('id', 'desc', {
					reference: 'fldtype',
					bind: '{type}',
					store: {
						autoLoad: true,
						model: 'WTA.model.Simple',
						proxy: WTF.proxy(me.sid, 'LookupLeaveRequestType')
					},
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: WT.res(me.sid, 'timetableRequestSearch.type.lbl'),
					width: '420px',
					tabIndex: 105
				}),
				WTF.lookupCombo('id', 'desc', {
					reference: 'fldapproved',
					bind: '{result}',
					store: Ext.create('Sonicle.webtop.drm.store.ApprovedType', {
						autoLoad: true
					}),
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: WT.res(me.sid, 'timetableRequestSearch.approved.lbl'),
					width: '420px',
					tabIndex: 107
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
				WTF.localCombo('id', 'desc', {
					reference: 'fldcompany',
					bind: '{companyId}',
					emptyText: WT.res(me.sid, 'timetableRequestSearch.company-emptytext.lbl'),
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
									Ext.defer(function() {
										me.getViewModel().set('companyId', meta.selected);	
									},100);
								}
							}
						}
					},
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: WT.res(me.sid, 'timetableRequestSearch.company.lbl'),
					width: '420px',
					tabIndex: 102
				}),
				{
					xtype: 'datefield',
					startDay: WT.getStartDay(),
					reference: 'fldto',
					bind: '{toDate}',
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: WT.res(me.sid, 'timetableRequestSearch.toDate.lbl'),
					width: '420px',
					tabIndex: 104
				},
				WTF.localCombo('id', 'desc', {
					reference: 'fldstatus',
					bind: '{status}',
					store: Ext.create('Sonicle.webtop.drm.store.TimetableRequestStatus', {
						autoLoad: true
					}),
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: WT.res(me.sid, 'timetableRequestSearch.status.lbl'),
					width: '420px',
					tabIndex: 106
				})
			]
		});
	},
	
	extractData: function () {
		var me = this,
			SoDate = Sonicle.Date;

		var query = {
			companyId: me.lookupReference('fldcompany').getValue(),
			userId: me.lookupReference('flduser').getValue(),
			type: me.lookupReference('fldtype').getValue(),
			fromDate: SoDate.format(me.lookupReference('fldfrom').getValue(), 'Y-m-d'),
			toDate: SoDate.format(me.lookupReference('fldto').getValue(), 'Y-m-d'),
			status: me.lookupReference('fldstatus').getValue(),
			result: me.lookupReference('fldapproved').getValue()
		};
		
		me.fireEvent('search', me, query);
	},
	
	getData: function () {
		var me = this,
			SoDate = Sonicle.Date;

		var query = {
			companyId: me.lookupReference('fldcompany').getValue(),
			userId: me.lookupReference('flduser').getValue(),
			type: me.lookupReference('fldtype').getValue(),
			fromDate: SoDate.format(me.lookupReference('fldfrom').getValue(), 'Y-m-d'),
			toDate: SoDate.format(me.lookupReference('fldto').getValue(), 'Y-m-d'),
			status: me.lookupReference('fldstatus').getValue(),
			result: me.lookupReference('fldapproved').getValue()
		};
		
		return query;
	},
	
	getOperatorId: function () {
		return this.lookupReference('flduser').getValue();
	}
});
