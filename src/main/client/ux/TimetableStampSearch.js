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
Ext.define('Sonicle.webtop.drm.ux.TimetableStampSearch', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.wtdrmtimetablestampsearch',
	uses: [
		'Sonicle.Date'
	],
	layout: 'column',
	referenceHolder: true,
	sid: null,
	
	viewModel: {
		data: {
			operatorId: null,
			month: Ext.Date.format(new Date(),'n'),
			year: Ext.Date.format(new Date(),'Y')
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
					editable: false,
					typeAhead: false,
					selectOnFocus: false,
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
									}
								}
							}
						}
					},
					fieldLabel: WT.res(me.sid, 'timetablestamp.fld-nominative.lbl'),
					width: '420px'
				}),
				WTF.lookupCombo('id', 'desc', {
					reference: 'fldmonth',
					bind: '{month}',
					store: Ext.create('Sonicle.webtop.drm.store.MonthStore', {
						autoLoad: true
					}),
					fieldLabel: WT.res(me.sid, 'timetablestamp.fld-month.lbl'),
					width: '420px',
					tabIndex: 102

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
				WTF.lookupCombo('year', 'year', {
					reference: 'fldyear',
					bind: '{year}',
					store: Ext.create('Sonicle.webtop.drm.store.YearStore', {
						autoLoad: true
					}),
					fieldLabel: WT.res(me.sid, 'timetablestamp.fld-year.lbl'),
					width: '420px',
					tabIndex: 103
				})
			]
		});
	},
	
	extractData: function () {
		var me = this;

		var query = {
			operatorId: me.lookupReference('flduser').getValue(),
			month: me.lookupReference('fldmonth').getValue(),
			year: me.lookupReference('fldyear').getValue()
		};
		
		me.fireEvent('search', me, query);
	},
	
	getData: function () {
		var me = this;

		var query = {
			operatorId: me.lookupReference('flduser').getValue(),
			month: me.lookupReference('fldmonth').getValue(),
			year: me.lookupReference('fldyear').getValue()
		};
		
		return query;
	},
	
	getOperatorId: function () {
		return this.lookupReference('flduser').getValue();
	},
	
	getRefDate: function () {
		var month = this.lookupReference('fldmonth').getValue();
		var year = this.lookupReference('fldyear').getValue();
		
		return new Date(year, month - 1, 1);
	}
});
