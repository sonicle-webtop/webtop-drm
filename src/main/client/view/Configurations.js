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
 *
 */
Ext.define('Sonicle.webtop.drm.view.Configurations', {
	extend: 'WTA.sdk.DockableView',
	requires: [
		'Sonicle.webtop.drm.model.GridDocStatuses',
		'Sonicle.webtop.drm.model.GridCompany',
		'Sonicle.webtop.drm.model.GridFolders'
	],
	dockableConfig: {
		title: 'Configurations',
		iconCls: 'wt-icon-activity-xs',
		width: 800,
		height: 600
	},
	initComponent: function () {
		var me = this;
		me.callParent(arguments);
		me.initActions();
		me.initCxm();
		me.add({
			region: 'center',
			xtype: 'tabpanel',
			tabPosition: 'left',
			tabRotation: 0,
			tabConfig: {
				textAlign: 'left'
			},
			items: [
				{
					xtype: 'wtform',
					title: me.mys.res('config.configGeneral.tit'),
					iconCls: '',
					items: [{
							xtype: 'checkbox',
							value: me.mys.getVar('useStatisticCustomer'),
							//hideEmptyLabel: true,
							boxLabel: me.mys.res('configGeneral.fld-useStatisticCustomer.lbl'),
							listeners: {
								change: function (s, nv) {
									me.updateConfiguration('useStatisticCustomer', nv);
								}
							}
						}]
				},
				{
					xtype: 'grid',
					reference: 'gpCompany',
					title: me.mys.res('config.company.tit'), //da risorse localizzazione
					iconCls: '',
					store: {
						autoLoad: true,
						//autoSync: true,
						model: 'Sonicle.webtop.drm.model.GridCompany',
						proxy: WTF.apiProxy(me.mys.ID, 'ManageCompanies', 'data', {
							writer: {//con parametro a false invio sempre un array per l 'operazione di delete del server
								allowSingle: false
							}
						})
					},
					columns: [{
							xtype: 'rownumberer'
						}, {
							dataIndex: 'name',
							header: me.mys.res('config.gpcompany.name.lbl'),
							flex: 1
						}, {
							dataIndex: 'address',
							header: me.mys.res('config.gpcompany.address.lbl'),
							flex: 2
						}],
					tbar: [
						me.getAct('company', 'add'),
						'-',
						me.getAct('company', 'edit'),
						me.getAct('company', 'remove')
					],
					listeners: {
						rowclick: function (s, rec) {
							me.getAct('company', 'edit').setDisabled(false);
							me.getAct('company', 'remove').setDisabled(false);
						},
						rowdblclick: function (s, rec) {
							me.editCompanyUI(rec);
						}
					}
				},
				{
					xtype: 'panel',
					title: me.mys.res('config.groups.tit'),
					iconCls: '',
					items: [{
							xtype: 'treepanel',
							reference: 'treeGroup',
							rootVisible: false,
							store: {
								autoLoad: true,
								model: 'Sonicle.webtop.drm.model.TreeNode',
								proxy: WTF.proxy(me.mys.ID, 'LoadGroupsTree')
							},
							root: {
								id: 'root',
								expanded: true
							},
							tbar: [
								me.getAct('addGroup'),
								'-',
								me.getAct('editGroup'),
								me.getAct('deleteGroup')
							],
							listeners: {
								//aggiungo l'item ctx menu
								itemcontextmenu: function (s, rec, itm, i, e) {//sender,record,item,index,event
									//mostro il menu cxt
									WT.showContextMenu(e, me.getRef('cxmGrid'), {node: rec});
								},
								itemclick: function (s, rec, itm, i, e) {
									me.updateDisabled('editGroup');
									me.updateDisabled('addGroup');
									me.updateDisabled('deleteGroup');
								},
								itemdblclick: function (s, rec, itm, i, e) {
									if (rec.get('depth') !== 1)
										me.editGroupUI(rec);
								}
							}
						}]
				},
				{
					xtype: 'grid',
					title: me.mys.res('config.profile.tit'),
					reference: 'gpProfile',
					store: {
						autoLoad: true,
						modelName: 'Sonicle.webtop.drm.model.GridProfiles',
						proxy: WTF.apiProxy(me.mys.ID, 'ManageGridProfiles')
					},
					columns: [
						{
							dataIndex: 'description',
							header: me.mys.res('gpProfile.fld-description.lbl')
						}
					],
					tbar: [
						me.getAct('addProfile'),
						'-',
						me.getAct('editProfile'),
						me.getAct('deleteProfile')
					],
					listeners: {
						itemclick: function (s, rec, itm, i, e) {
							me.updateDisabled('editProfile');
							me.updateDisabled('addProfile');
							me.updateDisabled('deleteProfile');
						},
						itemdblclick: function (s, rec, itm, i, e) {
							if (rec.get('depth') !== 1)
								me.editProfileUI(rec);
						}
					}
				},
				{
					xtype: 'grid',
					reference: 'gpFolders',
					title: me.mys.res('config.folders.tit'),
					iconCls: '',
					store: {
						autoLoad: true,
						//autoSync: true,
						model: 'Sonicle.webtop.drm.model.GridFolders',
						proxy: WTF.apiProxy(me.mys.ID, 'ManageGridFolders', 'data', {
							writer: {//con parametro a false invio sempre un array per l 'operazione di delete del server
								allowSingle: false
							}
						})
					},
					columns: [{
							header: me.mys.res('config.gpFolders.name.lbl'), //localizzata
							dataIndex: 'name',
							flex: 1,
							hideable: false
						}, {
							header: me.mys.res('config.gpFolders.description.lbl'), //localizzata
							dataIndex: 'description'
						}],
					tbar: [
						me.getAct('folder', 'add'),
						me.getAct('folder', 'remove'),
						'-',
						me.getAct('folder', 'edit')
					],
					listeners: {
						rowclick: function (s, rec) {
							me.getAct('folder', 'remove').setDisabled(false);
							me.getAct('folder', 'edit').setDisabled(false);
						},
						rowdblclick: function (s, rec) {
							me.editFolderUI(rec);
						}
					}
				},
				{
					xtype: 'grid',
					reference: 'gpDocStatus',
					title: me.mys.res('config.status.tit'),
					iconCls: '',
					store: {
						autoLoad: true,
						//autoSync: true,
						model: 'Sonicle.webtop.drm.model.GridDocStatuses',
						proxy: WTF.apiProxy(me.mys.ID, 'ManageGridDocStatuses', 'data', {
							writer: {//con parametro a false invio sempre un array per l 'operazione di delete del server
								allowSingle: false
							}
						})
					},
					columns: [{
							header: me.mys.res('config.gpdocStatus.name.lbl'), //localizzata
							dataIndex: 'name',
							flex: 1,
							hideable: false
						}, {
							header: me.mys.res('config.gpdocStatus.description.lbl'), //localizzata
							dataIndex: 'description'
						}],
					tbar: [
						me.getAct('docStatus', 'add'),
						me.getAct('docStatus', 'remove'),
						'-',
						me.getAct('docStatus', 'edit')
					],
					listeners: {
						rowclick: function (s, rec) {
							me.getAct('docStatus', 'remove').setDisabled(false);
							me.getAct('docStatus', 'edit').setDisabled(false);
						},
						rowdblclick: function (s, rec) {
							me.editDocStatusUI(rec);
						}
					}
				},
				{
					xtype: 'panel',
					title: me.mys.res('config.enabledProgram.tit'),
					iconCls: ''
				}]
		});
	},
	initActions: function () {
		var me = this;
		//-----------------------------------------
		me.addAct('company', 'add', {
			text: WT.res('act-add.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-add-xs',
			handler: function () {
				//add la view
				me.addCompany({
					callback: function (success) {
						if (success) {
							alert('success');
							me.gpCompany().getStore().load();
						}
					}
				});
			}
		});
		me.addAct('company', 'remove', {
			text: WT.res('act-remove.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-remove-xs',
			disabled: true,
			handler: function () {
				var sel = me.getSelectedCompany();
				if (sel) {
					me.deleteCompanyUI(sel);
				}
			}
		});
		me.addAct('company', 'edit', {
			text: WT.res('act-edit.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-edit-xs',
			disabled: true,
			handler: function () {
				var sel = me.getSelectedCompany();
				if (sel) {
					me.editCompanyUI(sel);
				}
			}
		});
		//------------------------------------------
		me.addAct('addGroup', {
			text: WT.res('act-add.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-add-xs',
			handler: function () {
				//add la view
				me.addGroup({
					callback: function (success) {
						if (success) {
							alert('success');
							me.treeGroup().getStore().load();
						}
					}
				});
			}
		});
		me.addAct('deleteGroup', {
			text: WT.res('act-remove.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-remove-xs',
			disabled: true,
			handler: function () {
				var sel = me.treeGroupSelected();
				if (sel) {
					me.deleteGroupUI(sel);
				}
			}
		});
		me.addAct('editGroup', {
			text: WT.res('act-edit.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-edit-xs',
			disabled: true,
			handler: function () {
				var sel = me.treeGroupSelected();
				if (sel) {
					me.editGroupUI(sel);
				}
			}
		});
		//-----------------------------------------
		me.addAct('addProfile', {
			text: WT.res('act-add.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-add-xs',
			handler: function () {
				//add la view
				me.addProfile({
					callback: function (success) {
						if (success) {
							alert('success');
							me.gpProfile().getStore().load();
						}
					}
				});
			}
		});
		me.addAct('deleteProfile', {
			text: WT.res('act-remove.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-remove-xs',
			disabled: true,
			handler: function () {
				var sel = me.getSelectedProfile();
				if (sel) {
					me.deleteProfileUI(sel);
				}
			}
		});
		me.addAct('editProfile', {
			text: WT.res('act-edit.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-edit-xs',
			disabled: true,
			handler: function () {
				var sel = me.getSelectedProfile();
				if (sel) {
					me.editProfileUI(sel);
				}
			}
		});
		//-----------------------------------------
		me.addAct('folder', 'add', {
			text: WT.res('act-add.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-add-xs',
			handler: function () {
				//add la view
				me.addFolder({
					callback: function (success) {
						if (success) {
							alert('success');
							me.gpFolder().getStore().load();
						}
					}
				});
			}
		});
		me.addAct('folder', 'remove', {
			text: WT.res('act-remove.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-remove-xs',
			disabled: true,
			handler: function () {
				var sel = me.getSelectedFolder();
				if (sel) {
					me.deleteFolderUI(sel);
				}
			}
		});
		me.addAct('folder', 'edit', {
			text: WT.res('act-edit.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-edit-xs',
			disabled: true,
			handler: function () {
				var sel = me.getSelectedFolder();
				if (sel) {
					me.editFolderUI(sel);
				}
			}
		});
		//-----------------------------------------
		me.addAct('docStatus', 'add', {
			text: WT.res('act-add.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-add-xs',
			handler: function () {
				//add la view
				me.addDocStatus({
					callback: function (success) {
						if (success) {
							alert('success');
							me.gpDocStatus().getStore().load();
						}
					}
				});
			}
		});
		me.addAct('docStatus', 'remove', {
			text: WT.res('act-remove.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-remove-xs',
			disabled: true,
			handler: function () {
				var sel = me.getSelectedDocStatus();
				if (sel) {
					me.deleteDocStatusUI(sel);
				}
			}
		});
		me.addAct('docStatus', 'edit', {
			text: WT.res('act-edit.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-edit-xs',
			disabled: true,
			handler: function () {
				var sel = me.getSelectedDocStatus();
				if (sel) {
					me.editDocStatusUI(sel);
				}
			}
		});

		//----------------------------------------
	},
	initCxm: function () {
		var me = this;
		me.addRef('cxmGrid', Ext.create({
			xtype: 'menu',
			items: [
				//ottengo l'azione
				me.getAct('addGroup'),
				'-', //linea separatore
				me.getAct('editGroup'),
				me.getAct('deleteGroup')
			],
			listeners: {
				beforeshow: function (s) {
					var rec = me.treeGroupSelected();
					me.updateDisabled('editGroup');
					me.updateDisabled('addGroup');
					me.updateDisabled('deleteGroup');
				}
			}
		}));
	},
	updateDisabled: function (action) {
		var me = this,
				dis = me.isDisabled(action);
		if (action === 'editProfile' || action === 'deleteProfile') {
			me.setActDisabled(action, dis);
		} else if (action === 'addProfile') {
			me.setActDisabled(action, dis);
		} else {
			me.setActDisabled(action, dis);
		}
		if (action === 'editGroup' || action === 'deleteGroup') {
			me.setActDisabled(action, dis);
		} else if (action === 'addGroup') {
			me.setActDisabled(action, dis);
		} else {
			me.setActDisabled(action, dis);
		}
	},
	/**
	 * @private
	 */
	isDisabled: function (action) {
		var me = this, sel, er;
		switch (action) {
			case 'editProfile':
			case 'deleteProfile':
				sel = me.getSelectedProfile();
				if (sel.lenght > 0)
					return true;
		}
	},
	//--------------
	treeGroup: function () {
		return this.lref('treeGroup');
	},
	treeGroupSelected: function () {
		return this.treeGroup().getSelection()[0];
	},
	gpCompany: function () {
		return this.lref('gpCompany');
	},
	getSelectedCompany: function () {
		return this.gpCompany().getSelection()[0];
	},
	gpDocStatus: function () {
		return this.lref('gpDocStatus');
	},
	getSelectedDocStatus: function () {
		return this.gpDocStatus().getSelection()[0];
	},
	gpFolder: function () {
		return this.lref('gpFolders');
	},
	getSelectedFolder: function () {
		return this.gpFolder().getSelection()[0];
	},
	gpProfile: function () {
		return this.lref('gpProfile');
	},
	getSelectedProfile: function () {
		return this.gpProfile().getSelection()[0];
	},
	updateConfiguration: function (name, val) {
		var me = this, pars = {};
		pars[name] = val;
		//richiamo a livello server il metodo per il salvataggio della variabie
		WT.ajaxReq(me.mys.ID, 'UpdateConfiguration', {
			params: pars,
			callback: function (success, json) {
				if (!success)
					WT.error(json.message);
			}
		});
	},
	//----------------------------------------------------

	addCompany: function (opts) {

		opts = opts || {}; //se nullo controlla e lo seta a obj empty

		var me = this,
				//viewcontainer => recupero la view di webtop => id del servizio, nome View
				vct = WT.createView(me.mys.ID, 'view.Company');
		vct.getView().on('viewsave', function (s, success, model) { //sender,success,modello
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		//al caricamento della finestra inizio il begin edit data
		vct.show(false, //mostro la view
				function () {
					vct.getView().begin('new');
				});
	},
	editCompanyUI: function (rec) {
		var me = this;
		me.editCompany(rec.get('companyId'), {
			callback: function (success, model) {
				if (success) {
					//rec.set('text', model.get('name')); //modifico il nodo a livello client senza ricaricare
					//ottengo lo store del componente 
					this.gpCompany().getStore().load();
				} else {
					alert('error');
				}
			}
		});
	},
	editCompany: function (companyId, opts) {

		opts = opts || {}; //se nullo controlla e lo seta a obj empty

		var me = this,
				//viewcontainer => recupero la view di webtop => id del servizio, nome View
				vct = WT.createView(me.mys.ID, 'view.Company');
		vct.getView().on('viewsave', function (s, success, model) { //sender,success,modello
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		//al caricamento della finestra inizio il begin edit data
		vct.show(false, //mostro la view
				function () {
					vct.getView().begin('edit', {
						data: {
							companyId: companyId
						}
					});
				});
	},
	deleteCompanyUI: function (rec) {
		var me = this,
				sto = me.gpCompany().getStore(),
				msg;
		if (rec) {
			msg = me.mys.res('act.confirm.delete', Ext.String.ellipsis(rec.get('name'), 40));
		} else {
			alert('non cancellando');
			//msg = me.mys.res('gptasks.confirm.delete.selection');
		}
		WT.confirm(msg, function (bid) {
			if (bid === 'yes') {
				me.deleteCompany(rec.get('companyId'), {
					callback: function (success) {
						if (success)
							sto.remove(rec);
						//me.reloadTasks();
					}
				});
			}
		});
	},
	deleteCompany: function (companyId, opts) {
		opts = opts || {};
		var me = this;
		WT.ajaxReq(me.mys.ID, 'ManageCompany', {
			params: {
				crud: 'delete',
				companyIds: WTU.arrayAsParam(companyId)
			},
			callback: function (success, json) {
				Ext.callback(opts.callback, opts.scope || me, [success, json]);
			}
		});
	},
	//----------------------------------
	addGroupUI: function (rec) {
		var me = this;
		me.addGroup(rec.get('groupCategory'), {
			callback: function (success, model) {
				if (success) {
					var sto = this.treeGroup().getStore();
					sto.load({
						node: rec
					});
				} else {
					alert('error');
				}
			}
		});
	},
	addGroup: function (groupCategory, opts) {

		opts = opts || {}; //se nullo controlla e lo seta a obj empty

		var me = this,
				//viewcontainer => recupero la view di webtop => id del servizio, nome View
				vct = WT.createView(me.mys.ID, 'view.Group');
		vct.getView().on('viewsave', function (s, success, model) { //sender,success,modello
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		//al caricamento della finestra inizio il begin edit data
		vct.show(false, //mostro la view
				function () {
					vct.getView().begin('new', {
						data: {
							groupCategory: groupCategory
						}
					});
				});
	},
	editGroupUI: function (rec) {
		var me = this;
		me.editGroup(rec.getId(), {
			callback: function (success, model) {
				if (success) {
					//rec.set('text', model.get('name')); //modifico il nodo a livello client senza ricaricare
					//ottengo lo store del componente
					var sto = this.treeGroup().getStore();
					sto.load({
						node: sto.getNodeById(model.get('groupCategory'))
					});
				} else {
					alert('error');
				}
			}
		});
	},
	editGroup: function (groupId, opts) {

		opts = opts || {}; //se nullo controlla e lo seta a obj empty

		var me = this,
				//viewcontainer => recupero la view di webtop => id del servizio, nome View
				vct = WT.createView(me.mys.ID, 'view.Group');
		vct.getView().on('viewsave', function (s, success, model) { //sender,success,modello
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		//al caricamento della finestra inizio il begin edit data
		vct.show(false, //mostro la view
				function () {
					vct.getView().begin('edit', {
						data: {
							groupId: groupId
						}
					});
				});
	},
	deleteGroupUI: function (rec) {
		var me = this,
				sto = me.treeGroup().getStore(),
				msg;
		if (rec) {
			msg = me.mys.res('act.confirm.delete', Ext.String.ellipsis(rec.get('text'), 40));
		} else {
			alert('non cancellando');
			//msg = me.mys.res('gptasks.confirm.delete.selection');
		}
		WT.confirm(msg, function (bid) {
			if (bid === 'yes') {
				me.deleteGroup(rec.getId(), {
					callback: function (success) {
						if (success)
							rec.remove();
					}
				});
			}
		});
	},
	deleteGroup: function (groupId, opts) {
		opts = opts || {};
		var me = this;
		WT.ajaxReq(me.mys.ID, 'ManageGroup', {
			params: {
				crud: 'delete',
				groupIds: WTU.arrayAsParam(groupId)
			},
			callback: function (success, json) {
				Ext.callback(opts.callback, opts.scope || me, [success, json]);
			}
		});
	},
	//------------------------------------

	addDocStatus: function (opts) {

		opts = opts || {}; //se nullo controlla e lo seta a obj empty

		var me = this,
				//viewcontainer => recupero la view di webtop => id del servizio, nome View
				vct = WT.createView(me.mys.ID, 'view.DocStatus');
		vct.getView().on('viewsave', function (s, success, model) { //sender,success,modello
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		//al caricamento della finestra inizio il begin edit data
		vct.show(false, //mostro la view
				function () {
					vct.getView().begin('new');
				});
	},
	editDocStatusUI: function (rec) {
		var me = this;
		me.editDocStatus(rec.get('docStatusId'), {
			callback: function (success, model) {
				if (success) {
					//rec.set('text', model.get('name')); //modifico il nodo a livello client senza ricaricare
					//ottengo lo store del componente 
					this.gpDocStatus().getStore().load();
				} else {
					alert('error');
				}
			}
		});
	},
	editDocStatus: function (docStatusId, opts) {

		opts = opts || {}; //se nullo controlla e lo seta a obj empty

		var me = this,
				//viewcontainer => recupero la view di webtop => id del servizio, nome View
				vct = WT.createView(me.mys.ID, 'view.DocStatus');
		vct.getView().on('viewsave', function (s, success, model) { //sender,success,modello
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		//al caricamento della finestra inizio il begin edit data
		vct.show(false, //mostro la view
				function () {
					vct.getView().begin('edit', {
						data: {
							docStatusId: docStatusId
						}
					});
				});
	},
	deleteDocStatusUI: function (rec) {
		var me = this,
				sto = me.gpDocStatus().getStore(),
				msg;
		if (rec) {
			msg = me.mys.res('act.confirm.delete', Ext.String.ellipsis(rec.get('name'), 40));
		} else {
			alert('non cancellando');
			//msg = me.mys.res('gptasks.confirm.delete.selection');
		}
		WT.confirm(msg, function (bid) {
			if (bid === 'yes') {
				me.deleteDocStatus(rec.get('docStatusId'), {
					callback: function (success) {
						if (success)
							sto.remove(rec);
					}
				});
			}
		});
	},
	deleteDocStatus: function (docStatusId, opts) {
		opts = opts || {};
		var me = this;
		WT.ajaxReq(me.mys.ID, 'ManageStatus', {
			params: {
				crud: 'delete',
				docStatusIds: WTU.arrayAsParam(docStatusId)
			},
			callback: function (success, json) {
				Ext.callback(opts.callback, opts.scope || me, [success, json]);
			}
		});
	},
	//-----------------------------------

	addFolder: function (opts) {

		opts = opts || {}; //se nullo controlla e lo seta a obj empty

		var me = this,
				//viewcontainer => recupero la view di webtop => id del servizio, nome View
				vct = WT.createView(me.mys.ID, 'view.Folder');
		vct.getView().on('viewsave', function (s, success, model) { //sender,success,modello
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		//al caricamento della finestra inizio il begin edit data
		vct.show(false, //mostro la view
				function () {
					vct.getView().begin('new');
				});
	},
	editFolderUI: function (rec) {
		var me = this;
		me.editFolder(rec.get('folderId'), {
			callback: function (success, model) {
				if (success) {
					//rec.set('text', model.get('name')); //modifico il nodo a livello client senza ricaricare
					//ottengo lo store del componente 
					this.gpFolder().getStore().load();
				} else {
					alert('error');
				}
			}
		});
	},
	editFolder: function (folderId, opts) {

		opts = opts || {}; //se nullo controlla e lo seta a obj empty

		var me = this,
				//viewcontainer => recupero la view di webtop => id del servizio, nome View
				vct = WT.createView(me.mys.ID, 'view.Folder');
		vct.getView().on('viewsave', function (s, success, model) { //sender,success,modello
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		//al caricamento della finestra inizio il begin edit data
		vct.show(false, //mostro la view
				function () {
					vct.getView().begin('edit', {
						data: {
							folderId: folderId
						}
					});
				});
	},
	deleteFolderUI: function (rec) {
		var me = this,
				sto = me.gpFolder().getStore(),
				msg;
		if (rec) {
			msg = me.mys.res('act.confirm.delete', Ext.String.ellipsis(rec.get('name'), 40));
		} else {
			alert('non cancellando');
			//msg = me.mys.res('gptasks.confirm.delete.selection');
		}
		WT.confirm(msg, function (bid) {
			if (bid === 'yes') {
				me.deleteFolder(rec.get('folderId'), {
					callback: function (success) {
						if (success)
							sto.remove(rec);
						//me.reloadTasks();
					}
				});
			}
		});
	},
	deleteFolder: function (folderId, opts) {
		opts = opts || {};
		var me = this;
		WT.ajaxReq(me.mys.ID, 'ManageFolder', {
			params: {
				crud: 'delete',
				folderIds: WTU.arrayAsParam(folderId)
			},
			callback: function (success, json) {
				Ext.callback(opts.callback, opts.scope || me, [success, json]);
			}
		});
	},
	//-------------------------------
	addProfile: function (opts) {

		opts = opts || {}; //se nullo controlla e lo seta a obj empty

		var me = this,
				//viewcontainer => recupero la view di webtop => id del servizio, nome View
				vct = WT.createView(me.mys.ID, 'view.Profile');
		vct.getView().on('viewsave', function (s, success, model) { //sender,success,modello
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		//al caricamento della finestra inizio il begin edit data
		vct.show(false, //mostro la view
				function () {
					vct.getView().begin('new');
				});
	},
	editProfileUI: function (rec) {
		var me = this;
		me.editProfile(rec.get('profileId'), {
			callback: function (success, model) {
				if (success) {
					//rec.set('text', model.get('name')); //modifico il nodo a livello client senza ricaricare
					//ottengo lo store del componente 
					this.gpProfile().getStore().load();
				} else {
					alert('error');
				}
			}
		});
	},
	editProfile: function (profileId, opts) {

		opts = opts || {}; //se nullo controlla e lo seta a obj empty

		var me = this,
				//viewcontainer => recupero la view di webtop => id del servizio, nome View
				vct = WT.createView(me.mys.ID, 'view.Profile');
		vct.getView().on('viewsave', function (s, success, model) { //sender,success,modello
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		//al caricamento della finestra inizio il begin edit data
		vct.show(false, //mostro la view
				function () {
					vct.getView().begin('edit', {
						data: {
							profileId: profileId
						}
					});
				});
	},
	deleteProfileUI: function (rec) {
		var me = this,
				sto = me.gpProfile().getStore(),
				msg;
		if (rec) {
			msg = me.mys.res('act.confirm.delete', Ext.String.ellipsis(rec.get('name'), 40));
		} else {
			alert('non cancellando');
			//msg = me.mys.res('gptasks.confirm.delete.selection');
		}
		WT.confirm(msg, function (bid) {
			if (bid === 'yes') {
				me.deleteFolder(rec.get('profileId'), {
					callback: function (success) {
						if (success)
							sto.remove(rec);
						//me.reloadTasks();
					}
				});
			}
		});
	},
	deleteProfile: function (profileId, opts) {
		opts = opts || {};
		var me = this;
		WT.ajaxReq(me.mys.ID, 'ManageProfile', {
			params: {
				crud: 'delete',
				folderIds: WTU.arrayAsParam(profileId)
			},
			callback: function (success, json) {
				Ext.callback(opts.callback, opts.scope || me, [success, json]);
			}
		});
	}

});