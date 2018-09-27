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
Ext.define('Sonicle.webtop.drm.ux.OpportunitySearch', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.wtdrmopportunitysearch',
	uses: [
		'Sonicle.Date'
	],
	layout: 'column',
	referenceHolder: true,
	sid: null,
	
	viewModel: {
		data: {
			operatorId: null,
			companyId: null,
			date: new Date(),
			fromHour: null,
			toHour: null
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
						}
					},
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: WT.res(me.sid, 'opportunity.fld-nominative.lbl'),
					width: '420px'
				}),
				{
					xtype: 'datefield',
					startDay: WT.getStartDay(),
					reference: 'flddate',
					bind: '{date}',
					tabIndex: 103,
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: WT.res(me.sid, 'opportunity.fld-date.lbl'),
					width: '420px'
				},
				WTF.lookupCombo('id', 'desc', {
					reference: 'fldfromhour',
					bind: '{fromHour}',
					fieldLabel: WT.res(me.sid, 'opportunity.fld-fromhour.lbl'),
					allowBlank: true,
					editable: true,
					forceSelection: false,
					queryMode: 'local',
					triggerAction: 'all',
					regex: new RegExp('^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$'),
					regexText: WT.res(me.sid, 'gpLineHours.column.format.lbl'),
					tabIndex: 104,
					store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
						autoLoad: true
					})
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
					fieldLabel: WT.res(me.sid, 'opportunity.fld-company.lbl'),
					width: '420px'
				}),
				{
					xtype: 'soplaceholderfield'
				},
				WTF.lookupCombo('id', 'desc', {
					reference: 'fldtohour',
					bind: '{toHour}',
					fieldLabel: WT.res(me.sid, 'opportunity.fld-tohour.lbl'),
					allowBlank: true,
					editable: true,
					forceSelection: false,
					queryMode: 'local',
					triggerAction: 'all',
					regex: new RegExp('^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$'),
					regexText: WT.res(me.sid, 'gpLineHours.column.format.lbl'),
					tabIndex: 105,
					store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
						autoLoad: true
					})
				})
			]
		});
	},
	
	extractData: function () {
		var me = this,
			SoDate = Sonicle.Date;

		var query = {
			companyId: me.lookupReference('fldcompany').getValue(),
			operatorId: me.lookupReference('flduser').getValue(),
			date: SoDate.format(me.lookupReference('flddate').getValue(), 'Y-m-d'),
			fromHour: me.lookupReference('fldfromhour').getValue(),
			toHour: me.lookupReference('fldtohour').getValue()			
		};
		
		me.fireEvent('search', me, query);
	},
	
	getData: function () {
		var me = this,
			SoDate = Sonicle.Date;

		var query = {
			companyId: me.lookupReference('fldcompany').getValue(),
			operatorId: me.lookupReference('flduser').getValue(),
			date: SoDate.format(me.lookupReference('flddate').getValue(), 'Y-m-d'),
			fromHour: me.lookupReference('fldfromhour').getValue(),
			toHour: me.lookupReference('fldtohour').getValue()			
		};
		
		return query;
	},
	
	getOperatorId: function () {
		return this.lookupReference('flduser').getValue();
	}
});
