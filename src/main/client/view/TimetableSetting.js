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
Ext.define('Sonicle.webtop.drm.view.TimetableSetting', {
	extend: 'WTA.sdk.ModelView',
	requires: [
		'Sonicle.webtop.drm.model.GridEmployeeProfiles',
		'Sonicle.webtop.drm.model.GridHourProfiles'
	],
	dockableConfig: {
		title: '{timetable.config.tit}', //localizzato
		iconCls: 'wtdrm-icon-configuration-generalconfiguration-xs',
		width: 650,
		height: 500
	},
	modelName: 'Sonicle.webtop.drm.model.TimetableSetting',
	initComponent: function () {
		var me = this;
		me.callParent(arguments);
		me.add({
			region: 'center',
			xtype: 'tabpanel',
			items: [
				{
					xtype: 'wtform',
					title: me.mys.res('timetable.settings.tit'),
					defaults: {
						labelWidth: 160
					},
					items: [
						{
							xtype: 'textfield',
							bind: '{record.allowedAddresses}',
							fieldLabel: me.mys.res('timetable.settings.fld-allowedaddresses.lbl'),
							selectOnFocus: true,
							emptyText: me.mys.res('timetable.settings.fld-allowedaddresses.emp'),
							width: 500
						},
						{
							xtype: 'textfield',
							bind: '{record.allowedUsers}',
							fieldLabel: me.mys.res('timetable.settings.fld-allowedusers.lbl'),
							selectOnFocus: true,
							emptyText: me.mys.res('timetable.settings.fld-allowedusers.emp'),
							width: 500
						},
						{
							xtype: 'textfield',
							bind: '{record.staffOfficeEmail}',
							fieldLabel: me.mys.res('timetable.settings.fld-staffofficeemail.lbl'),
							selectOnFocus: true
						},
						{
							xtype: 'textfield',
							bind: '{record.totalToleranceInMinutes}',
							fieldLabel: me.mys.res('timetable.settings.fld-totaltoleranceinminutes.lbl'),
							selectOnFocus: true
						},
						WTF.lookupCombo('id', 'desc', {
							bind: '{record.rounding}',
							store: Ext.create('Sonicle.webtop.drm.store.RoundingHour', {
								autoLoad: true
							}),
							triggers: {
								clear: WTF.clearTrigger()
							},
							emptyText: WT.res('word.none.male'),
							fieldLabel: me.mys.res('timetable.settings.fld-rounding.lbl'),
							selectOnFocus: true,
							editable: true
						}),
						{
							xtype: 'textfield',
							bind: '{record.minimumExtraordinary}',
							fieldLabel: me.mys.res('timetable.settings.fld-minimumextraordinary.lbl'),
							selectOnFocus: true
						},
						{
							xtype: 'checkbox',
							bind: '{record.breakAnomaly}',
							boxLabel: me.mys.res('timetable.settings.fld-breakanomaly.lbl')
						},
						{
							xtype: 'checkbox',
							bind: '{record.readOnlyEvents}',
							boxLabel: me.mys.res('timetable.settings.fld-readonlyevents.lbl')
						},
						{
							xtype: 'checkbox',
							bind: '{record.requestsHolidaysPermitsPreviousDates}',
							boxLabel: me.mys.res('timetable.settings.fld-requestsholidayspermitspreviousdates.lbl')
						}
					]
				},
				{
					title: me.mys.res('holidaydates.tit'),
					xtype: 'grid',
					reference: 'gpHolidayDate',
					bind: {
						store: '{record.holidayDates}'
					},
					width: '100%',
					border: true,
					flex: 2,
					columns: [
						{
							dataIndex: 'date',
							editor: {
								xtype: 'datefield',
								allowBlank: false,
								selectOnFocus: true
							},
							xtype:'datecolumn', 
							format: WT.getShortDateFmt(),
							header: me.mys.res('gpholidaydate.date.lbl'),
							flex: 1
						},
						{
							dataIndex: 'description',
							editor: {
								xtype: 'textfield',
								allowBlank: false,
								selectOnFocus: true
							},
							header: me.mys.res('gpholidaydate.description.lbl'),
							flex: 1
						}
					],
					tbar: [
						me.addAct('add', {
							text: WT.res('act-add.lbl'),
							tooltip: null,
							iconCls: 'wt-icon-add-xs',
							handler: function () {
								me.addHolidayDate();
							},
							scope: me
						}),
						me.addAct('delete', {
							text: WT.res('act-delete.lbl'),
							tooltip: null,
							iconCls: 'wt-icon-delete-xs',
							handler: function () {
								var sm = me.lref('gpHolidayDate').getSelectionModel();
								me.deleteHolidayDate(sm.getSelection());
							},
							scope: me
						})
					],
					plugins: {
						ptype: 'cellediting',
						clicksToEdit: 1
					}

				},
				{
					title: me.mys.res('employeeprofiles.tit'),
					xtype: 'grid',
					reference: 'gpEmployeeProfile',
					store: {
						autoLoad: true,
						model: 'Sonicle.webtop.drm.model.GridEmployeeProfiles',
						proxy: WTF.apiProxy(me.mys.ID, 'ManageGridEmployeeProfile', 'data', {
							writer: {
								allowSingle: false
							}
						})
					},
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
							dataIndex: 'hourProfile',
							header: me.mys.res('gpEmployeeProfile.hourProfile.lbl'),
							flex: 2
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
							iconCls: 'wt-icon-delete-xs',
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

				},
				{
					title: me.mys.res('ourprofiles.tit'),
					xtype: 'grid',
					reference: 'gpHourProfile',
					store: {
						autoLoad: true,
						model: 'Sonicle.webtop.drm.model.GridHourProfiles',
						proxy: WTF.apiProxy(me.mys.ID, 'ManageGridHourProfile', 'data', {
							writer: {
								allowSingle: false
							}
						})
					},
					width: '100%',
					border: true,
					flex: 1,
					columns: [
						{
							dataIndex: 'description',
							header: me.mys.res('gpHourProfile.description.lbl'),
							flex: 2
						}
					],
					tbar: [
						me.addAct('add', {
							text: WT.res('act-add.lbl'),
							tooltip: null,
							iconCls: 'wt-icon-add-xs',
							handler: function () {
								me.addHourProfile({
									callback: function (success) {
										if (success) {
											me.lref('gpHourProfile').getStore().load();
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
								var sel = me.lref('gpHourProfile').getSelection()[0];
								me.editHourProfileUI(sel);
							},
							scope: me
						}),
						me.addAct('delete', {
							text: WT.res('act-delete.lbl'),
							tooltip: null,
							iconCls: 'wt-icon-delete-xs',
							handler: function () {
								var sel = me.lref('gpHourProfile').getSelection()[0];
								me.deleteHourProfileUI(sel);
							},
							scope: me
						})
					],
					listeners: {
						rowdblclick: function (s, rec) {
							me.editHourProfileUI(rec);
						}
					}

				}
			]
		});
	},
	
	addHolidayDate: function () {
		var me = this;
		var gp = me.lref('gpHolidayDate'),
				sto = gp.getStore(),
				cep = gp.findPlugin('cellediting');


		cep.cancelEdit();

		sto.add(sto.createModel({
			date: new Date(),
			description: me.mys.res('gpholidaydate.defaultdescription.lbl')
		}));

		cep.startEditByPosition({row: sto.getCount(), column: 0});
	},
	deleteHolidayDate: function (rec) {
		var me = this,
				grid = me.lref('gpHolidayDate'),
				sto = grid.getStore(),
				cellediting = grid.findPlugin('cellediting');

		WT.confirm(WT.res('confirm.delete'), function (bid) {
			if (bid === 'yes') {
				cellediting.cancelEdit();
				sto.remove(rec);
			}
		}, me);
	},
	
	addEmployeeProfile: function (opts) {
		opts = opts || {};
		var me = this,
			vct = WT.createView(me.mys.ID, 'view.EmployeeProfile');
	
		vct.getView().on('viewsave', function (s, success, model) {
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});	
		vct.show(false, function () {
			vct.getView().begin('new', {
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
				vct = WT.createView(me.mys.ID, 'view.EmployeeProfile');
		vct.getView().on('viewsave', function (s, success, model) {
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vct.show(false,
				function () {
					vct.getView().begin('edit', {
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
	},
	
	addHourProfile: function (opts) {
		opts = opts || {};
		var me = this,
			vct = WT.createView(me.mys.ID, 'view.HourProfile');
	
		vct.getView().on('viewsave', function (s, success, model) {
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});	
		vct.show(false, function () {
			vct.getView().begin('new', {
				data: {
				}
			});
		});
	},
	
	editHourProfileUI: function (rec) {
		var me = this;
		me.editHourProfile(rec.id, {
			callback: function (success, model) {
				if (success) {
					me.lref('gpHourProfile').getStore().load();
				} else {
					alert('error');
				}
			}
		});
	},
	
	editHourProfile: function (id, opts) {
		opts = opts || {};
		var me = this,
				vct = WT.createView(me.mys.ID, 'view.HourProfile');
		vct.getView().on('viewsave', function (s, success, model) {
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vct.show(false,
				function () {
					vct.getView().begin('edit', {
						data: {
							id: id
						}
					});
				});
	},
	
	deleteHourProfileUI: function (rec) {
		var me = this,
				sto = me.lref('gpHourProfile').getStore(),
				msg;
		if (rec) {
			msg = me.mys.res('act.confirm.delete', Ext.String.ellipsis(rec.get('description'), 40));
		}
		WT.confirm(msg, function (bid) {
			if (bid === 'yes') {
				me.deleteHourProfile(rec.id, {
					callback: function (success) {
						if (success)
							sto.remove(rec);
					}
				});
			}
		});
	},
	deleteHourProfile: function (id, opts) {
		opts = opts || {};
		var me = this;
		WT.ajaxReq(me.mys.ID, 'ManageHourProfile', {
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
