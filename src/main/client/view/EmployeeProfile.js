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
Ext.define('Sonicle.webtop.drm.view.EmployeeProfile', {
	extend: 'WTA.sdk.ModelView',
	requires: [
		'Sonicle.webtop.drm.model.EmployeeProfile'
	],
	dockableConfig: {
		title: '{EmployeeProfile.tit}',
		iconCls: 'wtdrm-icon-configuration-employeeprofile-xs',
		width: 1160,
		height: 500,
		modal: true
	},
	fieldTitle: 'description',
	modelName: 'Sonicle.webtop.drm.model.EmployeeProfile',
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
			defaults: {
				labelWidth: 140
			},
			items: [
				{
					xtype: 'wtform',
					reference: 'employeeProfileform',
					modelValidation: true,
					items: [
						WTF.localCombo('id', 'desc', {
							bind: '{record.userId}',
							reference: 'flduser',
							anyMatch: true,
							allowBlank: false,
							store: {
								autoLoad: true,
								model: 'WTA.model.Simple',
								proxy: WTF.proxy(me.mys.ID, 'LookupUsers')
							},
							fieldLabel: me.mys.res('EmployeeProfile.fld-employee.lbl')
						}),
						{
							xtype: 'textfield',
							bind: '{record.number}',
							allowBlank: false,
							fieldLabel: me.mys.res('EmployeeProfile.fld-number.lbl'),
							selectOnFocus: true
						},
						{
							xtype: 'textfield',
							bind: '{record.tolerance}',
							fieldLabel: me.mys.res('EmployeeProfile.fld-tolerance.lbl'),
							selectOnFocus: true
						},
						{
							xtype: 'checkbox',
							bind: '{record.extraordinary}',
							boxLabel: me.mys.res('EmployeeProfile.fld-extraordinary.lbl')
						},
						{
							xtype: 'checkbox',
							bind: '{record.onlyPresence}',
							boxLabel: me.mys.res('EmployeeProfile.fld-onlyPresence.lbl')
						}
					]
				},
				{
					title: me.mys.res('EmployeeHours.tit'),
					xtype: 'grid',
					reference: 'gpEmployeeHours',
					bind: {
						store: '{record.employeeHours}'
					},
					width: '100%',
					border: true,
					columns: [
						{
							dataIndex: 'lineId',
							width: '25',
							header: me.mys.res('gpEmployeeHours.lineId.lbl')
						},
						{
							xtype: 'solookupcolumn',
							dataIndex: 'e_1',
							store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
								autoLoad: true
							}),
							editor: Ext.create(WTF.lookupCombo('id', 'desc', {
								allowBlank: false,
								store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
									autoLoad: true
								})
							})),
							displayField: 'desc',
							header: me.mys.res('gpEmployeeHours.1_e.lbl'),
							flex: 2
						},
						{
							xtype: 'solookupcolumn',
							dataIndex: 'u_1',
							store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
								autoLoad: true
							}),
							editor: Ext.create(WTF.lookupCombo('id', 'desc', {
								allowBlank: false,
								store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
									autoLoad: true
								})
							})),
							displayField: 'desc',
							header: me.mys.res('gpEmployeeHours.1_u.lbl'),
							flex: 2
						},
						{
							xtype: 'solookupcolumn',
							dataIndex: 'e_2',
							store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
								autoLoad: true
							}),
							editor: Ext.create(WTF.lookupCombo('id', 'desc', {
								allowBlank: false,
								store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
									autoLoad: true
								})
							})),
							displayField: 'desc',
							header: me.mys.res('gpEmployeeHours.2_e.lbl'),
							flex: 2
						},
						{
							xtype: 'solookupcolumn',
							dataIndex: 'u_2',
							store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
								autoLoad: true
							}),
							editor: Ext.create(WTF.lookupCombo('id', 'desc', {
								allowBlank: false,
								store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
									autoLoad: true
								})
							})),
							displayField: 'desc',
							header: me.mys.res('gpEmployeeHours.2_u.lbl'),
							flex: 2
						},
						{
							xtype: 'solookupcolumn',
							dataIndex: 'e_3',
							store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
								autoLoad: true
							}),
							editor: Ext.create(WTF.lookupCombo('id', 'desc', {
								allowBlank: false,
								store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
									autoLoad: true
								})
							})),
							displayField: 'desc',
							header: me.mys.res('gpEmployeeHours.3_e.lbl'),
							flex: 2
						},
						{
							xtype: 'solookupcolumn',
							dataIndex: 'u_3',
							store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
								autoLoad: true
							}),
							editor: Ext.create(WTF.lookupCombo('id', 'desc', {
								allowBlank: false,
								store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
									autoLoad: true
								})
							})),
							displayField: 'desc',
							header: me.mys.res('gpEmployeeHours.3_u.lbl'),
							flex: 2
						},
						{
							xtype: 'solookupcolumn',
							dataIndex: 'e_4',
							store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
								autoLoad: true
							}),
							editor: Ext.create(WTF.lookupCombo('id', 'desc', {
								allowBlank: false,
								store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
									autoLoad: true
								})
							})),
							displayField: 'desc',
							header: me.mys.res('gpEmployeeHours.4_e.lbl'),
							flex: 2
						},
						{
							xtype: 'solookupcolumn',
							dataIndex: 'u_4',
							store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
								autoLoad: true
							}),
							editor: Ext.create(WTF.lookupCombo('id', 'desc', {
								allowBlank: false,
								store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
									autoLoad: true
								})
							})),
							displayField: 'desc',
							header: me.mys.res('gpEmployeeHours.4_u.lbl'),
							flex: 2
						},
						{
							xtype: 'solookupcolumn',
							dataIndex: 'e_5',
							store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
								autoLoad: true
							}),
							editor: Ext.create(WTF.lookupCombo('id', 'desc', {
								allowBlank: false,
								store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
									autoLoad: true
								})
							})),
							displayField: 'desc',
							header: me.mys.res('gpEmployeeHours.5_e.lbl'),
							flex: 2
						},
						{
							xtype: 'solookupcolumn',
							dataIndex: 'u_5',
							store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
								autoLoad: true
							}),
							editor: Ext.create(WTF.lookupCombo('id', 'desc', {
								allowBlank: false,
								store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
									autoLoad: true
								})
							})),
							displayField: 'desc',
							header: me.mys.res('gpEmployeeHours.5_u.lbl'),
							flex: 2
						},
						{
							xtype: 'solookupcolumn',
							dataIndex: 'e_6',
							store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
								autoLoad: true
							}),
							editor: Ext.create(WTF.lookupCombo('id', 'desc', {
								allowBlank: false,
								store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
									autoLoad: true
								})
							})),
							displayField: 'desc',
							header: me.mys.res('gpEmployeeHours.6_e.lbl'),
							flex: 2
						},
						{
							xtype: 'solookupcolumn',
							dataIndex: 'u_6',
							store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
								autoLoad: true
							}),
							editor: Ext.create(WTF.lookupCombo('id', 'desc', {
								allowBlank: false,
								store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
									autoLoad: true
								})
							})),
							displayField: 'desc',
							header: me.mys.res('gpEmployeeHours.6_u.lbl'),
							flex: 2
						},
						{
							xtype: 'solookupcolumn',
							dataIndex: 'e_7',
							store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
								autoLoad: true
							}),
							editor: Ext.create(WTF.lookupCombo('id', 'desc', {
								allowBlank: false,
								store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
									autoLoad: true
								})
							})),
							displayField: 'desc',
							header: me.mys.res('gpEmployeeHours.7_e.lbl'),
							flex: 2
						},
						{
							xtype: 'solookupcolumn',
							dataIndex: 'u_7',
							store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
								autoLoad: true
							}),
							editor: Ext.create(WTF.lookupCombo('id', 'desc', {
								allowBlank: false,
								store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
									autoLoad: true
								})
							})),
							displayField: 'desc',
							header: me.mys.res('gpEmployeeHours.7_u.lbl'),
							flex: 2
						}
					],
					tbar: [
						me.addAct('add', {
							text: WT.res('act-add.lbl'),
							tooltip: null,
							iconCls: 'wt-icon-add-xs',
							handler: function () {
								me.addEmployeeHour();
							},
							scope: me
						}),
						me.addAct('delete', {
							text: WT.res('act-delete.lbl'),
							tooltip: null,
							iconCls: 'wt-icon-delete-xs',
							handler: function () {
								var sm = me.lref('gpEmployeeHours').getSelectionModel();
								me.deleteEmployeeHour(sm.getSelection());
							},
							scope: me
						})
					],
					plugins: {
						ptype: 'cellediting',
						clicksToEdit: 1
					}

				}
			]
		});
		
		me.on('viewinvalid', me.onViewInvalid);
	},
	
	onViewInvalid: function (s, mo, errs) {
		var me = this;
		WTU.updateFieldsErrors(me.lref('employeeProfileform'), errs);
	},
	
	addEmployeeHour: function () {
		var me = this;
		var gp = me.lref('gpEmployeeHours'),
				sto = gp.getStore(),
				cep = gp.findPlugin('cellediting');


		cep.cancelEdit();

		sto.add(sto.createModel({
			lineId: sto.getCount() + 1
		}));

		cep.startEditByPosition({row: sto.getCount(), column: 0});
	},
	deleteEmployeeHour: function (rec) {
		var me = this,
				grid = me.lref('gpEmployeeHours'),
				sto = grid.getStore(),
				cellediting = grid.findPlugin('cellediting');

		WT.confirm(WT.res('confirm.delete'), function (bid) {
			if (bid === 'yes') {
				cellediting.cancelEdit();
				sto.remove(rec);
			}
		}, me);
	}
});

