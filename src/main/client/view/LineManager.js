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
Ext.define('Sonicle.webtop.drm.view.LineManager', {
	extend: 'WTA.sdk.ModelView',
	requires: [
		'Sonicle.webtop.drm.model.LineManager',
		'Sonicle.webtop.drm.ux.UserGrid'
	],
	dockableConfig: {
		title: '{manager.tit}',
		iconCls: 'wtdrm-icon-configurationLineManagers',
		width: 500,
		height: 500
	},
	fieldTitle: 'description',
	modelName: 'Sonicle.webtop.drm.model.LineManager',
	constructor: function (cfg) {
		var me = this;
		me.callParent([cfg]);
	},
	initComponent: function () {
		var me = this;
		me.callParent(arguments);
		me.add({
			region: 'center',
			xtype: 'panel',
			layout: 'vbox',
			items: [
				{
					xtype: 'wtform',
					items: [
						WTF.localCombo('id', 'desc', {
							bind: '{record.userId}',
							reference: 'flduser',
							anyMatch: true,
							allowBlank: false,
							listConfig: {
								escapeDisplay: true
							},
							store: {
								autoLoad: true,
								model: 'WTA.model.Simple',
								proxy: WTF.proxy(me.mys.ID, 'LookupUsers')
							},
							fieldLabel: me.mys.res('manager.manager.lbl'),
							width: 250
						})
					]
				},
				{
					xtype: 'wtdrmusergrid',
					title: me.mys.res('manager.gpUsersForManager.tit'),
					sid: me.mys.ID,
					actionsInToolbar: false,
					width: '100%',
					flex: 1,
					bind: {
						store: '{record.associatedUsers}'
					},
					listeners: {
						pick: function (s, vals, recs) {
							var mo = me.getModel();
							mo.associatedUsers().add({
								userId: vals
							});
						}
					}
				}
			]
		});
		
		me.on('viewload', me.onViewLoad);
	},
	
	onViewLoad: function(s, success) {
		if(!success) return;
		var me = this;
		
		if(me.isMode(me.MODE_EDIT)) {
			me.lref('flduser').setReadOnly(true);
		}
	}
});

