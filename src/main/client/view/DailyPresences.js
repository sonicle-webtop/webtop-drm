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
Ext.define('Sonicle.webtop.drm.view.DailyPresences', {
	extend: 'WTA.sdk.UIView',
	requires: [
		'Sonicle.String'
	],
	
	dockableConfig: {
		iconCls: 'wtdrm-icon-dailyPresences-xs',
		width: 535,
		height: 494,
		modal: true,
		minimizable: false,
		maximizable: false
	},
	promptConfirm: false,
	writableOnly: false,
	
	viewModel: {
		data: {
			date: new Date()
		}
	},
	defaultButton: 'btnok',
	
	constructor: function(cfg) {
		var me = this;
		
		me.callParent([cfg]);
	},
	
	initComponent: function () {
		var me = this;
		
		Ext.apply(me, {
			buttons: [
				{
					reference: 'btnok',
					text: me.mys.res('btn-search.lbl'),
					handler: function() {
						me.search();
					}
				}
			]
		});

		me.callParent(arguments);

		me.add({
			region: 'center',
			xtype: 'panel',
			layout: 'border',
			items: [
				{
					xtype: 'panel',
					region: 'north',
					height: '140px',
					defaults: {
						margin: '10 10 10 10',
						labelWidth: 60
					},
					items: [
						{
							xtype: 'datefield',
							startDay: WT.getStartDay(),
							format: WT.getShortDateFmt(),
							reference: 'flddate',
							bind: '{date}',
							fieldLabel: me.mys.res('dailyPresences.fld-date.lbl'),
							width: '180',
							selectOnFocus: true
						}
					]
				},
				{
					region: 'center',
					xtype: 'grid',
					reference: 'gpDailyPresences',
					store: {
						sorters: [{ property: 'userName' }],
						autoLoad: true,
						model: 'Sonicle.webtop.drm.model.GridTimetable',
						proxy: WTF.apiProxy(me.mys.ID, 'ManageGridTimetableListUsers')
					},
					columns: [
						{
							xtype: 'soiconcolumn',
							hideable: false,
							align: 'center',
							getIconCls: function(v, rec) {
								return me.mys.cssIconCls((rec.get('type') === 'M') ? 'mainstamp' : 'companystamp', 'xs');
							},
							iconSize: WTU.imgSizeToPx('xs'),
							width: 30
						}, {
							dataIndex: 'userId',
							hidden: true,
							header: me.res('dailyPresences.user.lbl'),
						}, {
							dataIndex: 'userName',
							header: me.res('dailyPresences.user.lbl'),
							flex: 3
						}, {
							header: me.res('dailyPresences.entrance.lbl'),
							dataIndex: 'entrance',
							flex: 1,
							hideable: false,
							align: 'center'
						}, {
							header: me.res('dailyPresences.exit.lbl'),
							dataIndex: 'exit',
							flex: 1,
							hideable: false,
							align: 'center'
						}, {
							xtype: 'soiconcolumn',
							hideable: false,
							align: 'center',
							getIconCls: function(v, rec) {
								if (rec.get('location') === 'S') {
									return 'wtdrm-icon-smartworking';
								} else {
									return 'wtdrm-icon-office';
								}
							},
							iconSize: WTU.imgSizeToPx('xs'),
							width: 30
						}
					]
				}
			]
		});
	},
	
	search: function() {
		var me = this,
            vm = me.getVM(),
			pars = {},
			sto = me.lref('gpDailyPresences').getStore();
	
		if (vm.get('date') !== undefined)
			Ext.apply(pars, {date: vm.get('date')});

		WTU.loadWithExtraParams(sto, pars);
	}
});
