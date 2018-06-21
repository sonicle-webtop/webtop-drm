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
		'Sonicle.webtop.drm.model.GridExpenseNotes',
		'Sonicle.webtop.drm.ux.ExpenseNoteSearch',
		'Sonicle.webtop.drm.view.ExpenseNote',
		'Sonicle.webtop.drm.model.TreeNode',
		'Sonicle.webtop.drm.model.GridTimetable',
		'Sonicle.webtop.drm.ux.TimetableRequestSearch',
		'Sonicle.webtop.drm.model.GridTimetableRequests',
		'Sonicle.webtop.drm.view.TimetableRequest',
		'Sonicle.webtop.drm.ux.TimetableReportGenerate',
		'Sonicle.webtop.drm.model.GridTimetableReport',
		'Sonicle.webtop.drm.ux.TimetableStampSearch',
		'Sonicle.webtop.drm.model.GridTimetableList',
		'Sonicle.webtop.drm.view.TimetableStamp'
	],
	needsReload: true,
	init: function () {

		var me = this;
		me.initAction();
		me.initCxm();
		
		me.setToolbar(Ext.create({
			xtype: 'toolbar',
			referenceHolder: true,
			items: [
			]
		}));
		
		me.setToolComponent(Ext.create({
			xtype: 'panel',
			layout: 'border',
			referenceHolder: true,
			title: me.getName(),
			items: [
				{
					region: 'center',
					xtype: 'treepanel',
					reference: 'tree',
					rootVisible: false,
					store: {
						autoLoad: true,
						model: 'Sonicle.webtop.drm.model.TreeNode',
						proxy: WTF.proxy(me.ID, 'LoadEnabledProgramTree')
					},
					root: {
						id: 'root',
						expanded: false
					},
					listeners: {
						itemclick: function (s, rec, itm, i, e) {
							me.getMainComponent().getLayout().setActiveItem(i + 1);
							me.onActivate(i + 1);
						}
					}
				}]
		}));
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
							reference: 'filtersWorkReport',
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
						}
					]
				},{
					xtype: 'container',
					itemId: 'en',
					layout: 'border',
					items: [
						{
							region: 'north',
							xtype: 'wtdrmexpensenotesearch',
							title: me.res('expenseNote.tit.lbl'),
							iconCls: 'wtdrm-icon-expensenote-xs',
							titleCollapse: true,
							collapsible: true,
							sid: me.ID,
							listeners: {
								search: function(s, query){
									me.reloadExpenseNote(query);
								}
							}
						}, {
							region: 'center',
							xtype: 'grid',
							reference: 'gpExpenseNote',
							store: {
								autoLoad: false,
								model: 'Sonicle.webtop.drm.model.GridExpenseNotes',
								proxy: WTF.apiProxy(me.ID, 'ManageGridExpenseNote')
							},
							columns: [
								{
									header: me.res('gpExpenseNote.company.lbl'),
									dataIndex: 'company',
									flex: 1
								}, {
									header: me.res('gpExpenseNote.description.lbl'),
									dataIndex: 'description',
									flex: 1
								}, {
									header: me.res('gpExpenseNote.fromDate.lbl'),
									dataIndex: 'fromDate',
									xtype: 'datecolumn',
									format: WT.getShortDateFmt(),
									flex: 1
								}, {
									header: me.res('gpExpenseNote.toDate.lbl'),
									dataIndex: 'toDate',
									xtype: 'datecolumn',
									format: WT.getShortDateFmt(),
									flex: 1
								}, {
									header: me.res('gpExpenseNote.operator.lbl'),
									dataIndex: 'operator',
									flex: 1
								}, {
									header: me.res('gpExpenseNote.currency.lbl'),
									dataIndex: 'currency',
									flex: 1
								}, {
									header: me.res('gpExpenseNote.kmAmount.lbl'),
									dataIndex: 'kmAmount',
									flex: 1
								}, {
									header: me.res('gpExpenseNote.total.lbl'),
									dataIndex: 'total',
									flex: 1
								}, {
									header: me.res('gpExpenseNote.status.lbl'),
									dataIndex: 'status',
									flex: 1
								}, {
									header: me.res('gpExpenseNote.attachments.lbl'),
									dataIndex: 'attachments',
									flex: 1
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
						}
					]
				},
				{xtype: 'panel'},
				{
					xtype: 'tabpanel',
					itemId: 'ttss',
					tabPosition: 'bottom',
					items: [
						{
							xtype: 'container',
							itemId: 'tts',
							layout: 'border',
							title: me.res('timetabledaily.tit.lbl'),
							iconCls: 'wtdrm-icon-timetable-timetabledaily-xs',
							items: [
								{
									region: 'north',
									xtype: 'panel',
									height: 150,
									sid: me.ID,
									layout: {
										type: 'hbox',
										align: 'center',
										pack: 'center'
									}, 
									items: [
										{
											xtype: 'label',
											text: Ext.Date.format(new Date(),'j') + ' ' + me.res('timetable.date.month' + Ext.Date.format(new Date(),'m') + '.lbl') + ' ' + Ext.Date.format(new Date(),'Y'),
											margin: '0 0 0 10',
											style: 'font-weight:bold;font-size: 24px;color: #157fcc;'
										}
									]
								},
								{
									region: 'center',
									xtype: 'grid',
									reference: 'gpTimetable',
									store: {
										autoLoad: true,
										model: 'Sonicle.webtop.drm.model.GridTimetable',
										proxy: WTF.apiProxy(me.ID, 'ManageGridTimetable')
									},
									columns: [
										{
											xtype: 'soiconcolumn',
											hideable: false,
											align: 'center',
											getIconCls: function(v, rec) {
												return me.cssIconCls((rec.get('type') === 'M') ? 'mainstamp' : 'companystamp', 'xs');
											},
											iconSize: WTU.imgSizeToPx('xs'),
											width: 30
										},
										{
											header: me.res('gpTimetable.entrance.lbl'),
											dataIndex: 'entrance',
											flex: 1,
											hideable: false,
											align: 'center'
										},
										{
											header: me.res('gpTimetable.exit.lbl'),
											dataIndex: 'exit',
											flex: 1,
											hideable: false,
											align: 'center'
										}
									],
									tbar: [
										{
											xtype: 'button',
											reference: 'btnMainStamp',
											disabled: true,
											text: me.res('gpTimetable.mainstamp.lbl'),
											iconCls: 'wtdrm-icon-mainstamp-m',
											handler: function () {
												WT.ajaxReq(me.ID, 'SetTimetable', {
													params: {
														type: 'M'
													},
													callback: function (success, json) {
														if(success) me.getMainComponent().lookupReference('gpTimetable').getStore().reload();
													}
												});
											}
										},
										'-',
										{
											xtype: 'button',
											reference: 'btnCompanyStamp',
											hidden: true,
											text: me.res('gpTimetable.companystamp.lbl'),
											iconCls: 'wtdrm-icon-companystamp-m',
											handler: function () {
												WT.ajaxReq(me.ID, 'SetTimetable', {
													params: {
														type: 'C',
														timestamp: new Date()
													},
													callback: function (success, json) {
														if(success) me.getMainComponent().lookupReference('gpTimetable').getStore().reload();
													}
												});
											}
										}
									]
								}
							]
						},
						{
							xtype: 'container',
							layout: 'border',
							title: me.res('timetable.tit.lbl'),
							iconCls: 'wtdrm-icon-timetable-timetablelist-xs',
							items: [
								{
									region: 'north',
									xtype: 'wtdrmtimetablestampsearch',
									reference: 'filtersTimetableStamp',
									title: me.res('timetablesearch.tit.lbl'),
									iconCls: 'wtdrm-icon-timetable1-xs',
									titleCollapse: true,
									collapsible: true,
									sid: me.ID,
									listeners: {
										search: function(s, query){
											me.reloadTimetableStamp(query);
										}
									}
								},
								{
									region: 'center',
									xtype: 'grid',
									reference: 'gpTimetableStamp',
									store: {
										autoLoad: false,
										model: 'Sonicle.webtop.drm.model.GridTimetableList',
										proxy: WTF.apiProxy(me.ID, 'ManageGridTimetableList'),
										groupField: 'date'
									},
									features: [{
										id: 'group',
										ftype: 'grouping',
										groupHeaderTpl: '{name}',
										hideGroupedHeader: true,
										enableGroupingMenu: false
									}],
									columns: [
										{
											xtype: 'soiconcolumn',
											hideable: false,
											align: 'center',
											getIconCls: function(v, rec) {
												return me.cssIconCls((rec.get('type') === 'M') ? 'mainstamp' : ((rec.get('type') === 'C') ? 'companystamp' : 'specialstamp'), 'xs');
											},
											iconSize: WTU.imgSizeToPx('xs'),
											width: 30
										},
										{
											header: me.res('gpTimetable.date.lbl'),
											dataIndex: 'date',
											flex: 1,
											hideable: false,
											align: 'center'
										},
										{
											header: me.res('gpTimetable.entrance.lbl'),
											dataIndex: 'entrance',
											flex: 1,
											hideable: false,
											align: 'center'
										},
										{
											header: me.res('gpTimetable.exit.lbl'),
											dataIndex: 'exit',
											flex: 1,
											hideable: false,
											align: 'center'
										}
									],
									tbar: [
										me.getAct('timetableStamp', 'add'),
										'-',
										me.getAct('timetableStamp', 'remove')
									]
								}
							]
						}
					]
				},
				{
					xtype: 'container',
					itemId: 'ttr',
					layout: 'border',
					items: [
						{
							region: 'north',
							xtype: 'wtdrmtimetablerequestsearch',
							reference: 'filtersTimetableRequest',
							title: me.res('timetableRequest.tit.lbl'),
							iconCls: 'wtdrm-icon-timetable2-xs',
							titleCollapse: true,
							collapsible: true,
							sid: me.ID,
							listeners: {
								search: function(s, query){
									me.reloadTimetableRequest(query);
								}
							}
						}, {
							region: 'center',
							xtype: 'grid',
							reference: 'gpTimetableRequest',
							store: {
								autoLoad: false,
								model: 'Sonicle.webtop.drm.model.GridTimetableRequests',
								proxy: WTF.apiProxy(me.ID, 'ManageGridTimetableRequest')
							},
							columns: [
								{
									xtype: 'soiconcolumn',
									align: 'center',
									dataIndex: 'result',
									getIconCls: function(v, rec) {
										return me.cssIconCls((rec.get('result') === true) ? 'approvedrequest' : (rec.get('result') === false) ? 'notapprovedrequest' : 'sendedrequest', 'xs');
									},
									iconSize: WTU.imgSizeToPx('xs'),
									width: 30
								}, {
									header: me.res('gpTimetableRequest.type.lbl'),
									dataIndex: 'type',
									flex: 1,
									renderer: function (value, record) {
										switch(value) {
											case 'H':
												return me.res('gpTimetableRequest.type.H.lbl');
												break;
											case 'P':
												return me.res('gpTimetableRequest.type.P.lbl');
												break;
											case 'U':
												return me.res('gpTimetableRequest.type.U.lbl');
												break;
											case 'M':
												return me.res('gpTimetableRequest.type.M.lbl');
												break;
											case 'C':
												return me.res('gpTimetableRequest.type.C.lbl');
												break;
											case 'O':
												return me.res('gpTimetableRequest.type.O.lbl');
												break;
											default:
												return '';
												break;
										}
									}
								}, {
									header: me.res('gpTimetableRequest.requestBy.lbl'),
									dataIndex: 'userId',
									flex: 1
								}, {
									header: me.res('gpTimetableRequest.manager.lbl'),
									dataIndex: 'managerId',
									flex: 1
								}, {
									header: me.res('gpTimetableRequest.fromDate.lbl'),
									dataIndex: 'fromDate',
									xtype: 'datecolumn',
									format: WT.getShortDateFmt(),
									flex: 1
								}, {
									header: me.res('gpTimetableRequest.fromHour.lbl'),
									dataIndex: 'fromHour',
									flex: 1
								}, {
									header: me.res('gpTimetableRequest.toDate.lbl'),
									dataIndex: 'toDate',
									xtype: 'datecolumn',
									format: WT.getShortDateFmt(),
									flex: 1
								}, {
									header: me.res('gpTimetableRequest.toHour.lbl'),
									dataIndex: 'toHour',
									flex: 1
								}, {
									header: me.res('gpTimetableRequest.status.lbl'),
									dataIndex: 'status',
									flex: 1,
									renderer: function (value, record) {
										switch(value) {
											case 'O':
												return me.res('gpTimetableRequest.status.O.lbl');
												break;
											case 'C':
												return me.res('gpTimetableRequest.status.C.lbl');
												break;
											case 'D':
												return me.res('gpTimetableRequest.status.D.lbl');
												break;
											default:
												return '';
												break;
										}
									}
								}
							],
							tbar: [
								me.getAct('timetableRequest', 'add'),
								me.getAct('timetableRequest', 'edit'),
								'-',
								me.getAct('timetableRequest', '	approve'),
								me.getAct('timetableRequest', '	decline'),
								me.getAct('timetableRequest', 'requestcancellation'),
								me.getAct('timetableRequest', 'delete'),
							],
							listeners: {
								rowclick: function (s, rec) {
									me.getAct('timetableRequest', 'edit').setDisabled(false);
									me.getAct('timetableRequest', 'remove').setDisabled(false);
								},
								rowdblclick: function (s, rec) {
									me.editTimetableRequestUI(rec);
								}
							}
						}
					]
				},
				{
					xtype: 'container',
					itemId: 'ttrpt',
					layout: 'border',
					items: [
						{
							region: 'north',
							xtype: 'wtdrmtimetablereportgenerate',
							reference: 'filtersTimetableReport',
							title: me.res('timetableReport.tit.lbl'),
							iconCls: 'wtdrm-icon-timetable3-xs',
							titleCollapse: true,
							collapsible: true,
							sid: me.ID,
							listeners: {
								generate: function(s, query){
									WT.confirm(me.res('gpTimetableReport.regeneratereport.lbl'), function (bid) {
										if (bid === 'yes') {
											me.reloadTimetableReport(query);
										}
									});
								}
							}
						}, {
							region: 'center',
							xtype: 'grid',
							reference: 'gpTimetableReport',
							modelValidation: true,
							plugins: [
								Ext.create('Ext.grid.plugin.CellEditing', {
									clicksToEdit: 1
								})
							],
							selModel: {
								selType: 'cellmodel'
							},
							store: {
								autoLoad: true,
								autoSync: true,
								model: 'Sonicle.webtop.drm.model.GridTimetableReport',
								proxy: WTF.apiProxy(me.ID, 'ManageGridTimetableReport')
							},
							columns: [
								{
									header: me.res('gpTimetableReport.operator.lbl'),
									dataIndex: 'user',
									editable: false,
									flex: 1
								}, {
									header: me.res('gpTimetableReport.date.lbl'),
									dataIndex: 'date',
									editable: false,
									flex: 1
								}, {
									header: me.res('gpTimetableReport.workingHours.lbl'),
									dataIndex: 'workingHours',
									editable: true,
									editor: Ext.create(WTF.lookupCombo('id', 'desc', {
										allowBlank: true,
										store:  Ext.create('Sonicle.webtop.drm.store.TimetableStampHours', {
											autoLoad: true
										}),
										triggers: {
											clear: WTF.clearTrigger()
										}
									})),
									flex: 1
								}, {
									header: me.res('gpTimetableReport.overtime.lbl'),
									dataIndex: 'overtime',
									editable: true,
									editor: Ext.create(WTF.lookupCombo('id', 'desc', {
										allowBlank: true,
										store:  Ext.create('Sonicle.webtop.drm.store.TimetableStampHours', {
											autoLoad: true
										}),
										triggers: {
											clear: WTF.clearTrigger()
										}
									})),
									flex: 1
								}, {
									header: me.res('gpTimetableReport.paidLeave.lbl'),
									dataIndex: 'paidLeave',
									editable: true,
									editor: Ext.create(WTF.lookupCombo('id', 'desc', {
										allowBlank: true,
										store:  Ext.create('Sonicle.webtop.drm.store.TimetableStampHours', {
											autoLoad: true
										}),
										triggers: {
											clear: WTF.clearTrigger()
										}
									})),
									flex: 1
								}, {
									header: me.res('gpTimetableReport.unpaidLeave.lbl'),
									dataIndex: 'unpaidLeave',
									editable: true,
									editor: Ext.create(WTF.lookupCombo('id', 'desc', {
										allowBlank: true,
										store:  Ext.create('Sonicle.webtop.drm.store.TimetableStampHours', {
											autoLoad: true
										}),
										triggers: {
											clear: WTF.clearTrigger()
										}
									})),
									flex: 1
								}, {
									header: me.res('gpTimetableReport.holiday.lbl'),
									dataIndex: 'holiday',
									editable: true,
									editor: Ext.create(WTF.lookupCombo('id', 'desc', {
										allowBlank: true,
										store:  Ext.create('Sonicle.webtop.drm.store.TimetableStampHours', {
											autoLoad: true
										}),
										triggers: {
											clear: WTF.clearTrigger()
										}
									})),
									flex: 1
								}, {
									header: me.res('gpTimetableReport.medicalVisit.lbl'),
									dataIndex: 'medicalVisit',
									editable: true,
									editor: Ext.create(WTF.lookupCombo('id', 'desc', {
										allowBlank: true,
										store:  Ext.create('Sonicle.webtop.drm.store.TimetableStampHours', {
											autoLoad: true
										}),
										triggers: {
											clear: WTF.clearTrigger()
										}
									})),
									flex: 1
								}, {
									header: me.res('gpTimetableReport.contractual.lbl'),
									dataIndex: 'contractual',
									editable: true,
									editor: Ext.create(WTF.lookupCombo('id', 'desc', {
										allowBlank: true,
										store:  Ext.create('Sonicle.webtop.drm.store.TimetableStampHours', {
											autoLoad: true
										}),
										triggers: {
											clear: WTF.clearTrigger()
										}
									})),
									flex: 1
								}, {
									header: me.res('gpTimetableReport.causal.lbl'),
									dataIndex: 'causal',
									editable: true,
									editor: 'textfield',
									flex: 1
								}, {
									header: me.res('gpTimetableReport.hour.lbl'),
									dataIndex: 'hour',
									editable: true,
									editor: Ext.create(WTF.lookupCombo('id', 'desc', {
										allowBlank: true,
										store:  Ext.create('Sonicle.webtop.drm.store.TimetableStampHours', {
											autoLoad: true
										}),
										triggers: {
											clear: WTF.clearTrigger()
										}
									})),
									flex: 1
								}, {
									header: me.res('gpTimetableReport.detail.lbl'),
									dataIndex: 'detail',
									editable: true,
									editor: 'textfield',
									flex: 1
								}, {
									header: me.res('gpTimetableReport.note.lbl'),
									dataIndex: 'note',
									editable: true,
									editor: 'textfield',
									flex: 1
								}
							],
							tbar: [
//								me.getAct('timetableReport', 'save'),
//								'-',
								me.getAct('timetableReport', 'print')
//								me.getAct('timetableReport', 'export')
							]
						}
					]
				}
			]
		}));
	},
	
	filtersWorkReport: function () {
		return this.getMainComponent().lookupReference('filtersWorkReport');
	},
	
	gpWorkReport: function () {
		return this.getMainComponent().lookupReference('gpWorkReport');
	},
	
	gpWorkReportSelected: function () {
		return this.getMainComponent().lookupReference('gpWorkReport').getSelection()[0];
	},
	
	gpExpenseNote: function () {
		return this.getMainComponent().lookupReference('gpExpenseNote');
	},
	
	gpExpenseNoteSelected: function () {
		return this.getMainComponent().lookupReference('gpExpenseNote').getSelection()[0];
	},
	
	filtersTimetableRequest: function () {
		return this.getMainComponent().lookupReference('filtersTimetableRequest');
	},
	
	gpTimetableRequest: function () {
		return this.getMainComponent().lookupReference('gpTimetableRequest');
	},
	
	gpTimetableRequestSelected: function () {
		return this.getMainComponent().lookupReference('gpTimetableRequest').getSelection()[0];
	},
	
	filtersTimetableReport: function () {
		return this.getMainComponent().lookupReference('filtersTimetableReport');
	},
	
	gpTimetableReport: function () {
		return this.getMainComponent().lookupReference('gpTimetableReport');
	},
	
	gpTimetableReportSelected: function () {
		return this.getMainComponent().lookupReference('gpTimetableReportSelected').getSelection()[0];
	},
	
	filtersTimetableStamp: function () {
		return this.getMainComponent().lookupReference('filtersTimetableStamp');
	},
	
	gpTimetableStamp: function () {
		return this.getMainComponent().lookupReference('gpTimetableStamp');
	},
	
	gpTimetableStampSelected: function () {
		return this.getMainComponent().lookupReference('gpTimetableStamp').getSelection()[0];
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
	
	initAction: function () {
		var me = this;
		if (WT.isPermitted(me.ID,'CONFIGURATIONS','MANAGE')){
			me.addAct('toolbox', 'configurations', {
				text: me.res('toolbox.configuration.lbl'),
				tooltip: null,
				iconCls: 'wtdrm-icon-configuration-xs',
				handler: function () {
					me.configurationView();
				}
			});
		}
		if (WT.isPermitted(me.ID,'WORK_REPORT_SETTINGS','MANAGE')){
			me.addAct('toolbox', 'wrSetting', {
				text: me.res('toolbox.wr-settings.lbl'),
				tooltip: null,
				iconCls: 'wt-icon-options-xs',
				handler: function () {
					me.workReportSetting();
				}
			});
		}
		if (WT.isPermitted(me.ID,'EXPENSE_NOTE_SETTINGS','MANAGE')){
			me.addAct('toolbox', 'enSetting', {
				text: me.res('toolbox.en-settings.lbl'),
				tooltip: null,
				iconCls: 'wt-icon-options-xs',
				handler: function () {
					me.expenseNoteSetting();
				}
			});
		}
		if (WT.isPermitted(me.ID,'TIMETABLE_SETTINGS','MANAGE')){
			me.addAct('toolbox', 'ttSetting', {
				text: me.res('toolbox.tt-settings.lbl'),
				tooltip: null,
				iconCls: 'wt-icon-options-xs',
				handler: function () {
					me.timetableSetting();
				}
			});
		}
		me.addAct('workReport', 'add', {
			text: WT.res('act-add.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-add-xs',
			handler: function () {
				me.addWorkReport({
					callback: function (success) {
						if (success) {
							me.filtersWorkReport().extractData();
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
		me.addAct('expenseNote', 'add', {
			text: WT.res('act-add.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-add-xs',
			handler: function () {
				me.addExpenseNote({
					callback: function (success) {
						if (success) {
							me.gpExpenseNote().getStore().load();
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
				var sel = me.gpExpenseNoteSelected();
				me.editExpenseNoteUI(sel);
			}
		});
		me.addAct('expenseNote', 'remove', {
			text: WT.res('act-remove.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-remove-xs',
			disabled: true,
			handler: function () {
				var sel = me.gpExpenseNoteSelected();
				me.deleteExpenseNoteUI(sel);
			}
		});
		me.addAct('timetableRequest', 'add', {
			text: WT.res('act-add.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-add-xs',
			handler: function () {
				me.addTimetableRequest({
					callback: function (success) {
						if (success) {
							me.gpTimetableRequest().getStore().load();
						}
					}
				});
			}
		});
		me.addAct('timetableRequest', 'edit', {
			text: WT.res('act-edit.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-edit-xs',
			disabled: true,
			handler: function () {
				var sel = me.gpTimetableRequestSelected();
				me.editTimetableRequestUI(sel);
			}
		});
		me.addAct('timetableRequest', 'remove', {
			text: WT.res('act-remove.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-remove-xs',
			disabled: true,
			handler: function () {
				var sel = me.gpTimetableRequestSelected();
				me.deleteTimetableRequestUI(sel);
			}
		});
		me.addAct('timetableReport', 'save', {
			text: WT.res('act-save.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-save-xs',
			handler: function () {
				
			}
		});
		me.addAct('timetableReport', 'print', {
			text: WT.res('act-print.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-print-xs',
			handler: function () {
				me.printTimetableReport();
			}
		});
		me.addAct('timetableReport', 'export', {
			text: me.res('act-export.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-file-download-xs',
			handler: function () {
				
			}
		});
		me.addAct('timetableStamp', 'add', {
			text: WT.res('act-add.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-add-xs',
			disabled: true,
			handler: function () {
				me.addTimetableStamp({
					callback: function (success) {
						if (success) {
							me.gpTimetableStamp().getStore().load();
						}
					}
				});
			}
		});
		me.addAct('timetableStamp', 'remove', {
			text: WT.res('act-remove.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-remove-xs',
			disabled: true,
			handler: function () {
				var sel = me.gpTimetableStampSelected();
				me.deleteTimetableStampUI(sel);
			}
		});
	},
	
	initCxm: function () {
		var me = this;
	},

	addWorkReport: function (opts) {
		opts = opts || {};

		var me = this,
			fwr = me.filtersWorkReport(),
			vct = WT.createView(me.ID, 'view.WorkReport');
		vct.getView().on('viewsave', function (s, success, model) {
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vct.show(false,
			function () {
				vct.getView().begin('new', {
					data: {
						operatorId: fwr.getOperatorId()
					}
				});
			});
	},
	editWorkReportUI: function (rec) {
		var me = this;
		me.editWorkReport(rec.get('workReportId'), {
			callback: function (success, model) {
				if (success) {
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
		opts = opts || {};

		var me = this,
				vct = WT.createView(me.ID, 'view.WorkReportSetting');
		vct.getView().on('viewsave', function (s, success, model) { 
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vct.show(false,
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
	printWorkReport: function(ids) {
		var me = this, url;
		url = WTF.processBinUrl(me.ID, 'PrintWorkReport', {ids: WTU.arrayAsParam(ids)});
		Sonicle.URLMgr.openFile(url, {filename: 'workreport', newWindow: true});
	},
	addExpenseNote: function (opts) {
		opts = opts || {};

		var me = this,
				vct = WT.createView(me.ID, 'view.ExpenseNote');
		vct.getView().on('viewsave', function (s, success, model) {
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vct.show(false, 
				function () {
					vct.getView().begin('new');
				});
	},
	editExpenseNoteUI: function (rec) {
		var me = this;
		me.editWorkReport(rec.get('expenseNoteId'), {
			callback: function (success, model) {
				if (success) {
					this.gpExpenseNote().getStore().load();
				} else {
					alert('error');
				}
			}
		});
	},
	editExpenseNote: function (expenseNoteId, opts) {
		opts = opts || {};
		var me = this,
				vct = WT.createView(me.ID, 'view.ExpenseNote');
		vct.getView().on('viewsave', function (s, success, model) {
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vct.show(false,
				function () {
					vct.getView().begin('edit', {
						data: {
							expenseNoteId: expenseNoteId
						}
					});
				});
	},
	deleteExpenseNoteUI: function (rec) {
		var me = this,
				sto = me.gpExpenseNote().getStore(),
				msg;
		if (rec) {
			msg = me.res('act.confirm.delete', Ext.String.ellipsis(rec.get('expenseNoteId'), 40));
		} else {
			msg = me.res('gpWorkReport.confirm.delete.selection');
		}
		WT.confirm(msg, function (bid) {
			if (bid === 'yes') {
				me.deleteExpenseNote(rec.get('expenseNoteId'), {
					callback: function (success) {
						if (success)
							sto.remove(rec);
					}
				});
			}
		});
	},
	deleteExpenseNote: function (expenseNoteId, opts) {
		opts = opts || {};
		var me = this;
		WT.ajaxReq(me.ID, 'ManageExpenseNote', {
			params: {
				crud: 'delete',
				reportIds: WTU.arrayAsParam(expenseNoteId)
			},
			callback: function (success, json) {
				Ext.callback(opts.callback, opts.scope || me, [success, json]);
			}
		});
	},
	expenseNoteSetting: function (opts) {
		var me = this,
				vw = WT.createView(me.ID, 'view.ExpenseNoteSetting');
		vw.show(false);
	},
	reloadExpenseNote: function (query) {
		var me = this,
				pars = {},
				sto;
		if (me.isActive() && me.itemActiveId() === 'en') {
			sto = me.gpExpenseNote().getStore();
			if (query !== undefined)
				Ext.apply(pars, {query: Ext.JSON.encode(query)});
			WTU.loadWithExtraParams(sto, pars);
		} else {
			me.needsReload = true;
		}
	},
	addTimetableRequest: function (opts) {
		opts = opts || {};

		var me = this,
				ftr = me.filtersTimetableRequest(),
				vct = WT.createView(me.ID, 'view.TimetableRequest');
		vct.getView().on('viewsave', function (s, success, model) {
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vct.show(false, 
				function () {
					vct.getView().begin('new', {
						data: {
							userId: ftr.getOperatorId()
						}
					});
				});
	},
	editTimetableRequestUI: function (rec) {
		var me = this;
		me.editTimetableRequest(rec.get('leaveRequestId'), rec.get('status'), {
			callback: function (success, model) {
				if (success) {
					this.gpTimetableRequest().getStore().load();
				} else {
					alert('error');
				}
			}
		});
	},
	editTimetableRequest: function (leaveRequestId, status, opts) {
		opts = opts || {};
		var mode = (status === 'C' || status === 'D') ? 'view' : 'edit';
		var me = this,
				vct = WT.createView(me.ID, 'view.TimetableRequest');
		vct.getView().on('viewsave', function (s, success, model) {
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vct.show(false,
				function () {
					vct.getView().begin(mode, {
						data: {
							leaveRequestId: leaveRequestId
						}
					});
				});
	},
	deleteTimetableRequestUI: function (rec) {
		var me = this,
				sto = me.gpTimetableRequest().getStore(),
				msg;
		if (rec) {
			msg = me.res('act.confirm.delete', Ext.String.ellipsis(rec.get('leaveRequestId'), 40));
		} else {
			msg = me.res('gpTimetableRequest.confirm.delete.selection');
		}
		WT.confirm(msg, function (bid) {
			if (bid === 'yes') {
				me.deleteTimetableRequest(rec.get('leaveRequestId'), {
					callback: function (success) {
						if (success)
							sto.remove(rec);
					}
				});
			}
		});
	},
	deleteTimetableRequest: function (leaveRequestId, opts) {
		opts = opts || {};
		var me = this;
		WT.ajaxReq(me.ID, 'ManageLeaveRequest', {
			params: {
				crud: 'delete',
				leaveRequestIds: WTU.arrayAsParam(leaveRequestId)
			},
			callback: function (success, json) {
				Ext.callback(opts.callback, opts.scope || me, [success, json]);
			}
		});
	},
	timetableSetting: function (opts) {
		opts = opts || {};

		var me = this,
				vct = WT.createView(me.ID, 'view.TimetableSetting');
		vct.getView().on('viewsave', function (s, success, model) {
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vct.show(false,
				function () {
					vct.getView().begin('edit', {
						data: {
							id: 'tt'
						}
					});
				});
	},
	reloadTimetableRequest: function (query) {
		var me = this,
				pars = {},
				sto;
		if (me.isActive() && me.itemActiveId() === 'ttr') {
			sto = me.gpTimetableRequest().getStore();
			if (query !== undefined)
				Ext.apply(pars, {query: Ext.JSON.encode(query)});
			WTU.loadWithExtraParams(sto, pars);
		} else {
			me.needsReload = true;
		}
	},
	reloadTimetableReport: function (query) {
		var me = this,
				pars = {},
				sto;
		if (me.isActive() && me.itemActiveId() === 'ttrpt') {
			sto = me.gpTimetableReport().getStore();
			if (query !== undefined)
				Ext.apply(pars, {query: Ext.JSON.encode(query)});
			WTU.loadWithExtraParams(sto, pars);
		} else {
			me.needsReload = true;
		}
	},
	enablingStampButtons: function () {
		//Bottoni di timbratura standard
		var me = this;
		WT.ajaxReq(me.ID, 'ChekIpAddressNetwork', {
			params: {},
			callback: function (success, json) {
				if(success){
					if(json.data === true){
						me.getMainComponent().lookupReference('btnMainStamp').setDisabled(false);
						WT.ajaxReq(me.ID, 'ChekCompanyExitAuthorization', {
							params: {},
							callback: function (success, json) {
								if(success){
									if(json.data === true){
										me.getMainComponent().lookupReference('btnCompanyStamp').setHidden(false);
									}else {
										me.getMainComponent().lookupReference('btnCompanyStamp').setHidden(true);
									}
								}
							}
						});
					}else {
						me.getMainComponent().lookupReference('btnMainStamp').setDisabled(true);
						me.getMainComponent().lookupReference('btnCompanyStamp').setHidden(true);
					}
				}
			}
		});
	},
	enablingManageStampsButtons: function () {
		//Bottoni di inserimento/cancellazione timbratura manuale
		var me = this;
		WT.ajaxReq(me.ID, 'ChekManageStampsButtons', {
			params: {},
			callback: function (success, json) {
				if(success){
					if(json.data === true){
						me.getAct('timetableStamp', 'add').setDisabled(false);
						me.getAct('timetableStamp', 'remove').setDisabled(false);
					}else {
						me.getAct('timetableStamp', 'add').setDisabled(true);
						me.getAct('timetableStamp', 'remove').setDisabled(true);
					}
				}
			}
		});
	},
	printTimetableReport: function() {
		var me = this, url;
		url = WTF.processBinUrl(me.ID, 'PrintTimetableReport', {});
		Sonicle.URLMgr.openFile(url, {filename: 'timetablereport', newWindow: true});
	},
	reloadTimetableStamp: function (query) {
		var me = this,
				pars = {},
				sto;
		if (me.isActive() && me.itemActiveId() === 'ttss') {
			sto = me.gpTimetableStamp().getStore();
			if (query !== undefined)
				Ext.apply(pars, {query: Ext.JSON.encode(query)});
			WTU.loadWithExtraParams(sto, pars);
		} else {
			me.needsReload = true;
		}
	},
	
	addTimetableStamp: function (opts) {
		opts = opts || {};

		var me = this,
				fts = me.filtersTimetableStamp(),
				vct = WT.createView(me.ID, 'view.TimetableStamp');
		vct.getView().on('viewsave', function (s, success, model) {
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vct.show(false, 
				function () {
					vct.getView().begin('new', {
						data: {
							userId: fts.getOperatorId(),
							date: fts.getRefDate()
						}
					});
				});
	},

	deleteTimetableStampUI: function (rec) {
		var me = this,
				sto = me.gpTimetableStamp().getStore(),
				msg;
		if (rec) {
			msg = me.res('act.confirm.delete', Ext.String.ellipsis(rec.get('id'), 40));
		
			WT.confirm(msg, function (bid) {
				if (bid === 'yes') {
					me.deleteTimetableStamp(rec.get('id'), {
						callback: function (success) {
							if (success)
								sto.remove(rec);
						}
					});
				}
			});
		}
	},
	deleteTimetableStamp: function (id, opts) {
		opts = opts || {};
		var me = this;
		WT.ajaxReq(me.ID, 'ManageTimetableStamp', {
			params: {
				crud: 'delete',
				ids: WTU.arrayAsParam(id)
			},
			callback: function (success, json) {
				Ext.callback(opts.callback, opts.scope || me, [success, json]);
			}
		});
	},
	
	configurationView: function () {
		var me = this,
				vw = WT.createView(me.ID, 'view.Configurations');
		vw.show(false);
	},
	
	onActivate: function (tabIndex) {
		var me = this;
		
		switch (tabIndex){
			case 1:
				me.reloadWorkReport(me.filtersWorkReport().getData());
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				me.enablingStampButtons();
				me.enablingManageStampsButtons();
				me.getMainComponent().lookupReference('gpTimetable').getStore().reload();
				me.reloadTimetableStamp(me.filtersTimetableStamp().getData());
				break;
			case 5:
				me.reloadTimetableRequest(me.filtersTimetableRequest().getData());
				break;
			case 6:
				me.reloadTimetableReport(null);
				break;
		}
	}
});

