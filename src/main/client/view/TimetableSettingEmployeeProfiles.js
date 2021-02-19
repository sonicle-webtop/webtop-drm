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
Ext.define('Sonicle.webtop.drm.view.TimetableSettingEmployeeProfiles', {
    extend: 'WTA.sdk.DockableView',
	requires: [
        'Sonicle.webtop.drm.model.GridEmployeeProfiles'
	],
	dockableConfig: {
		title: '{timetable.config.employeeprofiles.tit}',
		iconCls: 'wtdrm-icon-configuration-generalconfiguration-xs',
		width: 650,
		height: 670
	},
	initComponent: function () {
		var me = this;
		
        me.callParent(arguments);
        
		me.add({
            region: 'center',
			xtype: 'panel',
			items: [
                {
					// title: me.mys.res('employeeprofiles.tit'),
					xtype: 'grid',
					reference: 'gpEmployeeProfile',
					store: {
						autoLoad: true,
						model: 'Sonicle.webtop.drm.model.GridEmployeeProfiles',
						proxy: WTF.apiProxy(me.mys.ID, 'ManageGridEmployeeProfile', 'data', {
							writer: {
								allowSingle: false
							}
						}),
                        groupField: 'hourProfile'
					},
                    features: [{
                        ftype: 'grouping',
                        groupHeaderTpl: '{name}',
                        enableGroupingMenu: false
                    }],
					width: '100%',
					border: true,
					flex: 1,
					columns: [
						{
							dataIndex: 'user',
							header: me.mys.res('gpEmployeeProfile.employee.lbl'),
							flex: 2
						},
						{
							dataIndex: 'number',
							header: me.mys.res('gpEmployeeProfile.number.lbl'),
							flex: 1
						},
						{
							dataIndex: 'tolerance',
							header: me.mys.res('gpEmployeeProfile.tolerance.lbl'),
							flex: 1
						},						
						{
							xtype: 'checkcolumn',
							disabled: true,
							dataIndex: 'extraordinary',
							header: me.mys.res('gpEmployeeProfile.extraordinary.lbl'),
							flex: 1
						},
						{
							xtype: 'checkcolumn',
							disabled: true,
							dataIndex: 'onlyPresence',
							header: me.mys.res('gpEmployeeProfile.onlyPresence.lbl'),
							flex: 1
						}
					],
					tbar: [
						me.addAct('add', {
							text: WT.res('act-add.lbl'),
							tooltip: null,
							iconCls: 'wt-icon-add-xs',
							handler: function () {
								me.addEmployeeProfile({
									callback: function (success) {
										if (success) {
											me.lref('gpEmployeeProfile').getStore().load();
										}
									}
								});
							},
							scope: me
						}),
						'-',
						me.addAct('edit', {
							text: WT.res('act-edit.lbl'),
							tooltip: null,
							iconCls: 'wt-icon-edit-xs',
							handler: function () {
								var sel = me.lref('gpEmployeeProfile').getSelection()[0];
								me.editEmployeeProfileUI(sel);
							},
							scope: me
						}),
						me.addAct('delete', {
							text: WT.res('act-delete.lbl'),
							tooltip: null,
							iconCls: 'wt-icon-delete',
							handler: function () {
								var sel = me.lref('gpEmployeeProfile').getSelection()[0];
								me.deleteEmployeeProfileUI(sel);
							},
							scope: me
						})
					],
					listeners: {
						rowdblclick: function (s, rec) {
							me.editEmployeeProfileUI(rec);
						}
					}
				}
            ]
		});
	},
	
	addEmployeeProfile: function (opts) {
		opts = opts || {};
		var me = this,
			vw = WT.createView(me.mys.ID, 'view.EmployeeProfile', {swapReturn: true});
	
		vw.on('viewsave', function (s, success, model) {
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});	
		vw.showView(function () {
			vw.begin('new', {
				data: {
				}
			});
		});
	},
	
	editEmployeeProfileUI: function (rec) {
		var me = this;
		me.editEmployeeProfile(rec.id, {
			callback: function (success, model) {
				if (success) {
					me.lref('gpEmployeeProfile').getStore().load();
				} else {
					alert('error');
				}
			}
		});
	},
	
	editEmployeeProfile: function (id, opts) {
		opts = opts || {};
		var me = this,
				vw = WT.createView(me.mys.ID, 'view.EmployeeProfile', {swapReturn: true});
		vw.on('viewsave', function (s, success, model) {
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vw.showView(function () {
            vw.begin('edit', {
                data: {
                    id: id
                }
            });
        });
	},
	
	deleteEmployeeProfileUI: function (rec) {
		var me = this,
            sto = me.lref('gpEmployeeProfile').getStore(),
            msg;
		if (rec) {
			msg = me.mys.res('act.confirm.delete', Ext.String.ellipsis(rec.get('user'), 40));
		}
		WT.confirm(msg, function (bid) {
			if (bid === 'yes') {
				me.deleteEmployeeProfile(rec.id, {
					callback: function (success) {
						if (success)
							sto.remove(rec);
					}
				});
			}
		});
	},
    
	deleteEmployeeProfile: function (id, opts) {
		opts = opts || {};
		var me = this;
		WT.ajaxReq(me.mys.ID, 'ManageEmployeeProfile', {
			params: {
				crud: 'delete',
				id: WTU.arrayAsParam(id)
			},
			callback: function (success, json) {
				Ext.callback(opts.callback, opts.scope || me, [success, json]);
			}
		});
	}
});