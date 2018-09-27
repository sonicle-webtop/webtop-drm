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
Ext.define('Sonicle.webtop.drm.ux.TimetableReportGenerate', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.wtdrmtimetablereportgenerate',
	layout: 'column',
	referenceHolder: true,
	sid: null,
	
	viewModel: {
		data: {
			userId: null,
			companyId: null,
			month: new Date().getMonth() + 1,
			year: new Date().getFullYear(),
			fromDay: 1
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
						select: function (s, r) {
							WTU.loadWithExtraParams(me.lookupReference('fldcompany').getStore(), {
								operator: r.id
							});
						}
					},
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: WT.res(me.sid, 'timetableReportGenerate.fld-operator.lbl'),
					width: '420px',
					tabIndex: 101
				}),
				{
					xtype: 'fieldcontainer',
					layout: 'hbox',
					defaults: {
						margin: '0 5 0 0',
						labelWidth: 120
					},
					items: [
						WTF.lookupCombo('id', 'desc', {
							reference: 'fldmonth',
							bind: '{month}',
							store: Ext.create('Sonicle.webtop.drm.store.MonthStore', {
								autoLoad: true
							}),
							fieldLabel: WT.res(me.sid, 'timetableReportGenerate.fld-monthyear.lbl'),
							width: 270,
							tabIndex: 103

						}),
						WTF.lookupCombo('year', 'year', {
							reference: 'fldyear',
							bind: '{year}',
							store: Ext.create('Sonicle.webtop.drm.store.YearStore', {
								autoLoad: true
							}),
							width: 145,
							tabIndex: 104
						})
					]
				},
				{
					xtype: 'button',
					text: WT.res(me.sid, 'btn-generate.lbl'),
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
					allowBlank: false,
					forceSelection: true,
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
					fieldLabel: WT.res(me.sid, 'timetableReportGenerate.fld-company.lbl'),
					width: '420px',
					tabIndex: 102
				}),
				 {
                    xtype: 'numberfield',
					reference: 'fldfromday',
					bind: '{fromDay}',
					allowBlank: false,
					editable: false,
                    minValue: 1,
                    maxValue: 31,
					fieldLabel: WT.res(me.sid, 'timetableReportGenerate.fld-fromday.lbl'),
					tabIndex: 105
                }
			]
		});
	},
	
	extractData: function () {
		var me = this;

		var query = {
			companyId: me.lookupReference('fldcompany').getValue(),
			userId: me.lookupReference('flduser').getValue(),
			month: me.lookupReference('fldmonth').getValue(),
			year: me.lookupReference('fldyear').getValue(),
			fromDay: me.lookupReference('fldfromday').getValue()
		};
		
		me.fireEvent('generate', me, query);
	},
	
	getData: function () {
		var me = this;

		var query = {
			companyId: me.lookupReference('fldcompany').getValue(),
			userId: me.lookupReference('flduser').getValue(),
			month: me.lookupReference('fldmonth').getValue(),
			year: me.lookupReference('fldyear').getValue(),
			fromDay: me.lookupReference('fldfromday').getValue()
		};
		
		return query;
	}
});
