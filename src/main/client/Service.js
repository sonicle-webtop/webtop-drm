/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
Ext.define('Sonicle.webtop.drm.Service', {
	extend: 'WTA.sdk.Service',
	requires: [
		'Sonicle.grid.column.Icon',
		'Sonicle.grid.column.Color',
		'Sonicle.grid.column.Lookup',
		'WTA.ux.data.EmptyModel',
		'WTA.ux.data.SimpleModel',
		'WTA.ux.panel.Fields',
		'Sonicle.webtop.drm.model.GridWorkReports',
		'Sonicle.webtop.drm.ux.WorkReportSearch',
		'Sonicle.webtop.drm.view.WorkReport',
		'Sonicle.webtop.drm.model.TreeNode'
	],
	needsReload: true,
	init: function () {

		var me = this;
		//inizializzo le azioni
		me.initAction();
		//inizializzo i menu contestuali
		me.initCxm();
		//me.on('activate', me.onActivate, me);
		//creo toolbar
		me.setToolbar(Ext.create({
			xtype: 'toolbar',
			referenceHolder: true, //riferimento alla toolbar
			items: [
			]
		}));
		//creo sidebar
		me.setToolComponent(Ext.create({
			xtype: 'panel',
			referenceHolder: true,
			title: me.getName(),
			items: [{
					xtype: 'treepanel',
					reference: 'tree',
					rootVisible: false, //nn mostra il primo nodo
					store: {
						autoLoad: true, //carica automaticamente tramite il proxy i dati
						model: 'Sonicle.webtop.drm.model.TreeNode', //modello dei dati => Ext.data.Model modello di dati di default
						proxy: WTF.proxy(me.ID, 'LoadEnabledProgramTree')   //da dove carico i dati
					},
					root: {//radice dell'albero e id per capire ql effettivamente è la root
						id: 'root',
						expanded: false //tt l'ablero chiuso
					},
					listeners: {
						itemclick: function (s, rec, itm, i, e) {
							me.getMainComponent().getLayout().setActiveItem(i + 1);
						}
					}
				}]
		}));
		//creo maincontent
		me.setMainComponent(Ext.create({
			xtype: 'panel',
			layout: 'card',
			referenceHolder: true,
			activeItem: 0,
			items: [
				{xtype: 'panel'},
				{
					xtype: 'container',
					itemId: 'wr',
					layout: 'border',
					items: [
						{
							region: 'north',
							xtype: 'wtdrmworkreportsearch',
							title: me.res('gpWorkReport.tit.lbl'),
							iconCls: 'wtdrm-icon-workreport-xs',
							titleCollapse: true,
							collapsible: true,
							sid: me.ID,
							listeners: {
								search: function(s, query){
									me.reloadWorkReport(query);
								}
							}
						},
						{
							region: 'center',
							xtype: 'grid',
							reference: 'gpWorkReport',
							store: {
								autoLoad: false,
								model: 'Sonicle.webtop.drm.model.GridWorkReports',
								proxy: WTF.apiProxy(me.ID, 'ManageGridWorkReport')
							},
							columns: [
								{
									dataIndex: 'workReportNo',
									header: me.res('gpWorkReport.workreportno.lbl'),
									flex: 1
								},
								{
									dataIndex: 'referenceNo',
									header: me.res('gpWorkReport.reference.lbl'),
									flex: 1
								},
								{
									xtype: 'solookupcolumn',
									dataIndex: 'companyId',
									store: {
										autoLoad: true,
										model: 'WTA.model.Simple',
										proxy: WTF.proxy(me.ID, 'LookupAllCompanies')
									},
									header: me.res('gpWorkReport.company.lbl'),
									displayField: 'desc',
									flex: 1,
									hidden: true
								},
								{
									xtype: 'solookupcolumn',
									dataIndex: 'operatorId',
									store: {
										autoLoad: true,
										model: 'WTA.model.Simple',
										proxy: WTF.proxy(WT.ID, 'LookupDomainUsers')
									},
									header: me.res('gpWorkReport.user.lbl'),
									displayField: 'desc',
									flex: 1
								},
								{
									xtype: 'solookupcolumn',
									dataIndex: 'customerId',
									store: {
										autoLoad: true,
										model: 'WTA.model.Simple',
										proxy: WTF.proxy(WT.ID, 'LookupCustomersSuppliers')
									},
									header: me.res('gpWorkReport.realcustomer.lbl'),
									displayField: 'desc',
									flex: 1
								},
								{
									xtype: 'solookupcolumn',
									dataIndex: 'customerStatId',
									store: {
										autoLoad: true,
										model: 'WTA.model.Simple',
										proxy: WTF.proxy(me.ID, 'LookupAllStatisticCustomers')
									},
									header: me.res('gpWorkReport.statcustomer.lbl'),
									displayField: 'desc',
									flex: 1
								},
								{
									xtype: 'solookupcolumn',
									dataIndex: 'causalId',
									store: {
										autoLoad: true,
										model: 'WTA.model.CausalLkp',
										proxy: WTF.proxy(me.ID, 'LookupCausals'),
										listeners: {
											beforeload: {
												fn: function (s) {
													var mo = me.gpWorkReport().getView().getStore().getProxy().getModel();
													WTU.applyExtraParams(s, {
														profileId: WT.getVar('profileId'),
														masterDataId: mo.getField('customerId')
													});
												},
												scope: me
											}
										}
									},
									header: me.res('gpWorkReport.causal.lbl'),
									displayField: 'desc',
									flex: 1
								},
								{
									dataIndex: 'fromDate',
									header: me.res('gpWorkReport.from.lbl'),
									xtype: 'datecolumn',
									format: WT.getShortDateFmt(),
									flex: 1
								},
								{
									dataIndex: 'toDate',
									header: me.res('gpWorkReport.to.lbl'),
									xtype: 'datecolumn',
									format: WT.getShortDateFmt(),
									flex: 1
								},
								{
									xtype: 'solookupcolumn',
									dataIndex: 'businessTripId',
									store: {
										autoLoad: true,
										model: 'WTA.model.Simple',
										proxy: WTF.proxy(me.ID, 'LookupBusinessTrip')
									},
									displayField: 'desc',
									header: me.res('gpWorkReport.trasfert.lbl'),
									flex: 1
								},
								{
									xtype: 'solookupcolumn',
									dataIndex: 'docStatusId',
									store: {
										autoLoad: true,
										model: 'WTA.model.Simple',
										proxy: WTF.proxy(me.ID, 'LookupDocStatuses')
									},
									header: me.res('gpWorkReport.docstatus.lbl'),
									displayField: 'desc',
									flex: 1,
									hidden: true
								},
								{
									xtype: 'checkcolumn',
									dataIndex: 'freeSupport',
									header: me.res('gpWorkReport.freesupport.lbl'),
									disabled: true,
									flex: 1,
									hidden: true
								},
								{
									xtype: 'checkcolumn',
									dataIndex: 'chargeTo',
									header: me.res('gpWorkReport.chargeTo.lbl'),
									disabled: true,
									flex: 1,
									hidden: true
								}
							],
							tbar: [
								me.getAct('workReport', 'add'),
								'-',
								me.getAct('workReport', 'edit'),
								me.getAct('workReport', 'remove')
							],
							listeners: {
								rowclick: function (s, rec) {
									me.getAct('workReport', 'edit').setDisabled(false);
									me.getAct('workReport', 'remove').setDisabled(false);
								},
								rowdblclick: function (s, rec) {
									me.editWorkReportUI(rec);
								}
							}
						}]
				},
				{
					xtype: 'grid',
					title: 'Expense Note',
					itemId: 'en',
					referenceHolder: true,
					store: {
						fields: ['id', 'desc'],
						data: [
							{id: 1, desc: 'prova'}, {id: 2, desc: 'prova'}
						]
					},
					columns: [
						{
							text: 'ID',
							dataIndex: 'id'
						}, {
							text: 'Desc',
							dataIndex: 'desc'
						}
					],
					tbar: [
						me.getAct('expenseNote', 'add'),
						'-',
						me.getAct('expenseNote', 'edit'),
						me.getAct('expenseNote', 'remove')
					],
					listeners: {
						rowclick: function (s, rec) {
							me.getAct('expenseNote', 'edit').setDisabled(false);
							me.getAct('expenseNote', 'remove').setDisabled(false);
						},
						rowdblclick: function (s, rec) {
							me.editExpenseNoteUI(rec);
						}
					}
				},
				{xtype: 'panel'}
			]
		}));
	},
	//-------------------------------
	//Getter
	gpWorkReport: function () {
		return this.getMainComponent().lookupReference('gpWorkReport');
	},
	gpWorkReportSelected: function () {
		return this.getMainComponent().lookupReference('gpWorkReport').getSelection()[0];
	},
	itemActive: function () {
		return this.getMainComponent().getLayout().getActiveItem();
	},
	itemActiveId: function () {
		return this.itemActive().getItemId();
	},
	activeSearchPanel: function(){
		this.itemActive().lookupReference('searchPanel');
	},
	//----------------------------------------------------
	initAction: function () {
		var me = this;
		me.addAct('toolbox', 'configurations', {
			text: me.res('toolbox.configuration.lbl'),
			tooltip: null,
			iconCls: 'wtdrm-icon-configuration-xs',
			handler: function () {
				me.configurationView();
			}
		});
		me.addAct('toolbox', 'wrSetting', {
			text: me.res('toolbox.wr-settings.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-options-xs',
			handler: function () {
				me.workReportSetting();
			}
		});
		me.addAct('toolbox', 'enSetting', {
			text: me.res('toolbox.en-settings.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-options-xs',
			handler: function () {
				me.expenseNoteSetting();
			}
		});
		me.addAct('workReport', 'add', {
			text: WT.res('act-add.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-add-xs',
			handler: function () {
				me.addWorkReport({
					callback: function (success) {
						if (success) {
							me.gpWorkReport().getStore().load();
						}
					}
				});
			}
		});
		me.addAct('workReport', 'edit', {
			text: WT.res('act-edit.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-edit-xs',
			disabled: true,
			handler: function () {
				var sel = me.gpWorkReportSelected();
				me.editWorkReportUI(sel);
			}
		});
		me.addAct('workReport', 'remove', {
			text: WT.res('act-remove.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-remove-xs',
			disabled: true,
			handler: function () {
				var sel = me.gpWorkReportSelected();
				me.deleteWorkReportUI(sel);
			}
		});
		//-------------------------------------------

		me.addAct('expenseNote', 'add', {
			text: WT.res('act-add.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-add-xs',
			handler: function () {

				me.addExpenseNote({
					callback: function (success) {
						if (success) {
							alert('success');
							//me.gpWorkReport().getStore().load();
						}
					}
				});
			}
		});
		me.addAct('expenseNote', 'edit', {
			text: WT.res('act-edit.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-edit-xs',
			disabled: true,
			handler: function () {


			}
		});
		me.addAct('expenseNote', 'remove', {
			text: WT.res('act-remove.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-remove-xs',
			disabled: true,
			handler: function () {

				//me.addReport();

			}
		});
		//-------------------------------------------

	},
	//inizializ	zo il menu Contestuale
	initCxm: function () {
		var me = this;
	},
	//--------------------------------------------------------------

	addWorkReport: function (opts) {
		opts = opts || {};

		var me = this,
			vct = WT.createView(me.ID, 'view.WorkReport');
		vct.getView().on('viewsave', function (s, success, model) { //sender,success,modello
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vct.show(false,
			function () {
				vct.getView().begin('new');
			});
	},
	editWorkReportUI: function (rec) {
		var me = this;
		me.editWorkReport(rec.get('workReportId'), {
			callback: function (success, model) {
				if (success) {
					//rec.set('text', model.get('name')); //modifico il nodo a livello client senza ricaricare
					//ottengo lo store del componente 
					this.gpWorkReport().getStore().load();
				} else {
					alert('error');
				}
			}
		});
	},
	editWorkReport: function (workReportId, opts) {
		opts = opts || {};
		var me = this,
				vct = WT.createView(me.ID, 'view.WorkReport');
		vct.getView().on('viewsave', function (s, success, model) {
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vct.show(false,
				function () {
					vct.getView().begin('edit', {
						data: {
							workReportId: workReportId
						}
					});
				});
	},
	deleteWorkReportUI: function (rec) {
		var me = this,
				sto = me.gpWorkReport().getStore(),
				msg;
		if (rec) {
			msg = me.res('act.confirm.delete', Ext.String.ellipsis(rec.get('workReportNo'), 40));
		} else {
			msg = me.res('gpWorkReport.confirm.delete.selection');
		}
		WT.confirm(msg, function (bid) {
			if (bid === 'yes') {
				me.deleteWorkReport(rec.get('workReportId'), {
					callback: function (success) {
						if (success)
							sto.remove(rec);
						//me.reloadTasks();
					}
				});
			}
		});
	},
	deleteWorkReport: function (reportId, opts) {
		opts = opts || {};
		var me = this;
		WT.ajaxReq(me.ID, 'ManageWorkReport', {
			params: {
				crud: 'delete',
				reportIds: WTU.arrayAsParam(reportId)
			},
			callback: function (success, json) {
				Ext.callback(opts.callback, opts.scope || me, [success, json]);
			}
		});
	},
	workReportSetting: function (opts) {
		opts = opts || {}; //se nullo controlla e lo seta a obj empty

		var me = this,
				//viewcontainer => recupero la view di webtop => id del servizio, nome View
				vct = WT.createView(me.ID, 'view.WorkReportSetting');
		vct.getView().on('viewsave', function (s, success, model) { //sender,success,modello
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		//al caricamento della finestra inizio il begin edit data
		vct.show(false, //mostro la view
				function () {
					vct.getView().begin('edit', {
						data: {
							id: 'wr'
						}
					});
				});
	},
	
	reloadWorkReport: function (query) {
		var me = this,
				pars = {},
				sto;
		if (me.isActive() && me.itemActiveId() === 'wr') {
			sto = me.gpWorkReport().getStore();
			if (query !== undefined)
				Ext.apply(pars, {query: Ext.JSON.encode(query)});
			WTU.loadWithExtraParams(sto, pars);
		} else {
			me.needsReload = true;
		}
	},

	addExpenseNote: function (opts) {
		opts = opts || {}; //se nullo controlla e lo seta a obj empty

		var me = this,
				//viewcontainer => recupero la view di webtop => id del servizio, nome View
				vct = WT.createView(me.ID, 'view.ExpenseNote');
		vct.getView().on('viewsave', function (s, success, model) { //sender,success,modello
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		//al caricamento della finestra inizio il begin edit data
		vct.show(false, //mostro la view
				function () {
					vct.getView().begin('new');
				});
	},
	expenseNoteSetting: function (opts) {
		var me = this,
				vw = WT.createView(me.ID, 'view.ExpenseNoteSetting');
		vw.show(false);
	},
	//---------------------------------------------------------
	configurationView: function () {
		var me = this,
				vw = WT.createView(me.ID, 'view.Configurations');
		vw.show(false);
	},
//-----------------------------------------------------
	onActivate: function () {
		var me = this,
				gp = me.gpWorkReport();
		if (me.needsReload) {
			me.needsReload = false;
			me.reloadWorkReport();
		}
	}
});

