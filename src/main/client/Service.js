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
		'Sonicle.webtop.drm.model.GridOpportunities',
		'Sonicle.webtop.drm.ux.OpportunitySearch',
		'Sonicle.webtop.drm.view.Opportunity',
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
		'Sonicle.webtop.drm.ux.TimetableSummaryExcel',
		'Sonicle.webtop.drm.model.GridTimetableReport',
		'Sonicle.webtop.drm.ux.TimetableStampSearch',
		'Sonicle.webtop.drm.model.GridTimetableList',
		'Sonicle.webtop.drm.view.TimetableStamp',
		'Sonicle.webtop.drm.model.GridJobs',
		'Sonicle.webtop.drm.ux.JobSearch',
		'Sonicle.webtop.drm.view.Job',		
		'Sonicle.webtop.drm.model.GridTickets',
		'Sonicle.webtop.drm.ux.TicketSearch',
		'Sonicle.webtop.drm.view.Ticket',
		'Sonicle.webtop.drm.ux.ChooseTicketConfirmBox',
		'Sonicle.webtop.drm.view.TimetableSettingCausals',
		'Sonicle.webtop.drm.model.GridCausals',
		'Sonicle.webtop.drm.view.Causal',
		'Sonicle.webtop.drm.model.Causal',
		'Sonicle.webtop.drm.view.TimetableSettingGis',
		'Sonicle.webtop.drm.model.TimetableSettingGis',
		'Sonicle.webtop.drm.view.TimetableSettingGeneral',
		'Sonicle.webtop.drm.model.TimetableSettingGeneral',
		'Sonicle.webtop.drm.store.RoundingHour',
		'Sonicle.webtop.drm.view.DailyPresences',
		'Sonicle.webtop.drm.view.UserTimetableRequests'
	],
    uses: [
        'Sonicle.webtop.drm.ServiceApi'  
    ],
    
	needsReload: true,
    api: null,
	opportunityRequiredFields: null,
	timetableReportGenerateQuery: null,
	
    getApiInstance: function() {
		var me = this;
		if (!me.api) me.api = Ext.create('Sonicle.webtop.drm.ServiceApi', {service: me});
		return me.api;
	},
    
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
							me.getMainComponent().getLayout().setActiveItem(rec.data.id);
							me.onActivateTab(rec.data.id);
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
				{
					xtype: 'panel',
					itemId: 'empty'
				},
				{
					xtype: 'container',
					itemId: 'oppo',
					layout: 'border',
					items: [
						{
							region: 'north',
							xtype: 'wtdrmopportunitysearch',
							reference: 'filtersOpportunity',
							title: (me.getVar('opportunityTitle') == null  || me.getVar('opportunityTitle') == '') ? me.res('gpOpportunity.tit.lbl') : me.getVar('opportunityTitle'),
							iconCls: 'wtdrm-icon-opportunity',
							titleCollapse: true,
							collapsible: true,
							sid: me.ID,
							listeners: {
								search: function(s, query){
									me.reloadOpportunity(query);
								}
							}
						},
						{
							region: 'center',
							xtype: 'grid',
							reference: 'gpOpportunity',
							viewConfig: {
								getRowClass: function(r, rowIndex, rp, ds) {
									if(r.get('isTotallyClosed') === true){
										return 'opportunity-grid-row-closed';
									} else{
										if(r.get('actionId') !== 0){
											if(r.get('startDate') >= new Date()) 
												return 'opportunity-grid-row-notexpired';
											else 
												return 'opportunity-grid-row-expired';
										}
									}
								} 
							},
							store: {
								autoLoad: false,
								model: 'Sonicle.webtop.drm.model.GridOpportunities',
								proxy: WTF.apiProxy(me.ID, 'ManageGridOpportunity')
							},
							columns: [
								{
									dataIndex: 'id',
									header: me.res('gpOpportunity.id.lbl'),
									flex: 1,
									renderer: function (value, meta, record, rowIndex, colIndex, store) {
										switch(record.get('actionId')) {
											case 0:
												return value;
												break;
											default:
												return '';
												break;
										}
									}
								},
								{
									xtype: 'solookupcolumn',
									dataIndex: 'companyId',
									store: {
										autoLoad: true,
										model: 'WTA.model.Simple',
										proxy: WTF.proxy(me.ID, 'LookupAllCompanies')
									},
									header: me.res('gpOpportunity.company.lbl'),
									displayField: 'desc',
									flex: 3,
									hidden: true
								},
								{
									xtype: 'solookupcolumn',
									dataIndex: 'operatorId',
									store: {
										autoLoad: true,
										model: 'WTA.model.Simple',
										proxy: WTF.proxy(me.ID, 'LookupOperators')
									},
									header: me.res('gpOpportunity.user.lbl'),
									displayField: 'desc',
									flex: 3
								},
								{
									dataIndex: 'startDate',
									header: me.res('gpOpportunity.fromdate.lbl'),
									xtype: 'datecolumn',
									format: WT.getShortDateTimeFmt(),
									flex: 3
								},
								{
									dataIndex: 'endDate',
									header: me.res('gpOpportunity.todate.lbl'),
									xtype: 'datecolumn',
									format: WT.getShortDateTimeFmt(),
									flex: 3
								},
								{
									dataIndex: 'additionalInfo',
									header: me.res('gpOpportunity.additionalinfo.lbl'),
									flex: 20
								}
							],
							tbar: [
								me.getAct('opportunity', 'add'),
								'-',
								me.getAct('opportunity', 'edit'),
								me.getAct('opportunity', 'delete'),
								'-',
								me.getAct('opportunity', 'addAction'),
								me.getAct('opportunity', 'prepareActions')
							],
							listeners: {
								rowclick: function (s, rec) {
									me.getAct('opportunity', 'edit').setDisabled(false);
									me.getAct('opportunity', 'delete').setDisabled(false);
									
									if(rec.get('actionId') === 0){
										me.getAct('opportunity', 'addAction').setDisabled(false);
										me.getAct('opportunity', 'prepareActions').setDisabled(false);
									}else{
										me.getAct('opportunity', 'addAction').setDisabled(true);
										me.getAct('opportunity', 'prepareActions').setDisabled(true);
									}
								},
								rowdblclick: function (s, rec) {
									me.editUI(rec);
								}
							}
						}
					]
				},{
					xtype: 'container',
					itemId: 'wrkr',
					layout: 'border',
					items: [
						{
							region: 'north',
							xtype: 'wtdrmworkreportsearch',
							reference: 'filtersWorkReport',
							title: me.res('gpWorkReport.tit.lbl'),
							iconCls: 'wtdrm-icon-workReport',
							titleCollapse: true,
							collapsible: true,
							sid: me.ID,
							useStatisticCustomer: me.getVar('useStatisticCustomer'),
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
									dataIndex: 'number',
									header: me.res('gpWorkReport.number.lbl'),
									flex: 2
								},
								{
									dataIndex: 'year',
									header: me.res('gpWorkReport.year.lbl'),
									flex: 1
								},
								{
									dataIndex: 'referenceNo',
									header: me.res('gpWorkReport.reference.lbl'),
									flex: 2
								},{
									dataIndex: 'companyDescription',
									header: me.res('gpWorkReport.company.lbl'),
									flex: 3
								},{
									dataIndex: 'operatorDescription',
									header: me.res('gpWorkReport.user.lbl'),
									flex: 3
								},{
									dataIndex: 'customerDescription',
									header: me.res('gpWorkReport.realcustomer.lbl'),
									flex: 3
								},{
									dataIndex: 'customerStatDescription',
									header: me.res('gpWorkReport.statcustomer.lbl'),
									flex: 3
								},{
									dataIndex: 'causalDescription',
									header: me.res('gpWorkReport.causal.lbl'),
									flex: 3
								},
								/*
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
									flex: 3,
									hidden: true
								},
								{
									xtype: 'solookupcolumn',
									dataIndex: 'operatorId',
									store: {
										autoLoad: true,
										model: 'WTA.model.Simple',
										proxy: WTF.proxy(me.ID, 'LookupOperators')
									},
									header: me.res('gpWorkReport.user.lbl'),
									displayField: 'desc',
									flex: 3
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
									flex: 3
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
									flex: 3
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
									flex: 2
								},
								*/
								{
									dataIndex: 'fromDate',
									header: me.res('gpWorkReport.from.lbl'),
									xtype: 'datecolumn',
									format: WT.getShortDateFmt(),
									flex: 2
								},
								{
									dataIndex: 'toDate',
									header: me.res('gpWorkReport.to.lbl'),
									xtype: 'datecolumn',
									format: WT.getShortDateFmt(),
									flex: 2
								},{
									dataIndex: 'businessTripDescription',
									header: me.res('gpWorkReport.trasfert.lbl'),
									flex: 3,
									hidden: true
								},{
									dataIndex: 'docStatusDescription',
									header: me.res('gpWorkReport.docstatus.lbl'),
									flex: 3
								},{
									dataIndex: 'totHours',
									header: me.res('gpWorkReport.tothours.lbl'),
									flex: 2
								},
								/*
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
									flex: 3
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
									flex: 2,
									hidden: true
								},
								*/
								{
									xtype: 'checkcolumn',
									dataIndex: 'freeSupport',
									header: me.res('gpWorkReport.freesupport.lbl'),
									disabled: true,
									flex: 3,
									hidden: true
								},
								{
									xtype: 'checkcolumn',
									dataIndex: 'chargeTo',
									header: me.res('gpWorkReport.chargeTo.lbl'),
									disabled: true,
									flex: 3,
									hidden: true
								}
							],
							tbar: [
								me.getAct('workReport', 'add'),
								'-',
								me.getAct('workReport', 'edit'),
								me.getAct('workReport', 'delete'),
								'-',
								me.getAct('workReport', 'printSummary')
							],
							listeners: {
								rowclick: function (s, rec) {
									me.getAct('workReport', 'edit').setDisabled(false);
									me.getAct('workReport', 'delete').setDisabled(false);
									me.getAct('workReport', 'sendMail').setDisabled(false);
								},
								rowdblclick: function (s, rec) {
									me.editWorkReportUI(rec);
								}
							}
						}
					]
				},{
					xtype: 'container',
					itemId: 'expn',
					layout: 'border',
					items: [
						{
							region: 'north',
							xtype: 'wtdrmexpensenotesearch',
							reference: 'filtersExpenseNote',
							title: me.res('expenseNote.tit.lbl'),
							iconCls: 'wtdrm-icon-expenseNote',
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
									xtype: 'solookupcolumn',
									dataIndex: 'companyId',
									store: {
										autoLoad: true,
										model: 'WTA.model.Simple',
										proxy: WTF.proxy(me.ID, 'LookupAllCompanies')
									},
									header: me.res('gpExpenseNote.company.lbl'),
									displayField: 'desc',
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
									xtype: 'solookupcolumn',
									dataIndex: 'operatorId',
									store: {
										autoLoad: true,
										model: 'WTA.model.Simple',
										proxy: WTF.proxy(me.ID, 'LookupOperators')
									},
									header: me.res('gpExpenseNote.operator.lbl'),
									displayField: 'desc',
									flex: 1
								}, {
									xtype: 'solookupcolumn',
									dataIndex: 'statusId',
									store: Ext.create('Sonicle.webtop.drm.store.StatusType', {
										autoLoad: true
									}),
									header: me.res('gpExpenseNote.status.lbl'),
									displayField: 'desc',
									flex: 1
								}
							],
							tbar: [
								me.getAct('expenseNote', 'add'),
								'-',
								me.getAct('expenseNote', 'edit'),
								me.getAct('expenseNote', 'delete')
							],
							listeners: {
								rowclick: function (s, rec) {
									me.getAct('expenseNote', 'edit').setDisabled(false);
									me.getAct('expenseNote', 'delete').setDisabled(false);
								},
								rowdblclick: function (s, rec) {
									me.editExpenseNoteUI(rec);
								}
							}
						}
					]
				},
				{
					xtype: 'panel',
					itemId: 'tmtb'
				},
				{
					xtype: 'tabpanel',
					itemId: 'tmtb.stmp',
					tabPosition: 'bottom',
					items: [
						{
							xtype: 'container',
							itemId: 'tts',
							layout: 'border',
							title: me.res('timetabledaily.tit.lbl'),
							iconCls: 'wtdrm-icon-timetableDaily',
							listeners: {
								activate: function() {
									me.getMainComponent().lookupReference('gpTimetable').getStore().reload();
								}
							},
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
												return rec.get('type') === 'M' ? 'wtdrm-icon-stampDefault' : 'wtdrm-icon-stampCompany';
											},
											iconSize: WTU.imgSizeToPx('xs'),
											width: 30
										}, {
											header: me.res('gpTimetable.entrance.lbl'),
											dataIndex: 'entrance',
											flex: 1,
											hideable: false,
											align: 'center'
										}, {
											header: me.res('gpTimetable.exit.lbl'),
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
										}/*, {
											xtype: 'solookupcolumn',
											header: me.res('gpTimetable.activityId.lbl'),
											dataIndex: 'activityId',
											store: {
												autoload: true,
												model: 'WTA.model.ActivityLkp',
												proxy: WTF.proxy(WT.ID, 'LookupActivities')
											},
											displayField: 'desc',
											flex: 2,
											hideable: false,
											align: 'center'
										}*/
									],
									tbar: [
										{
											xtype: 'button',
											reference: 'btnMainStamp',
											scale: 'large',
											disabled: true,
											text: me.res('gpTimetable.mainstamp.lbl'),
											iconCls: 'wtdrm-icon-stampDefault',
											handler: function () {
												WT.ajaxReq(me.ID, 'CheckExistEntranceTimetableStamp', {
													params: {
														type: 'M'
													},
													callback: function (success, json) {
														if(success){
															if(json.data == true){
																WT.ajaxReq(me.ID, 'SetTimetable', {
																	params: {
																		type: 'M',
																		timestamp: new Date()
																	},
																	callback: function (success, json) {
																		if(success) me.getMainComponent().lookupReference('gpTimetable').getStore().reload();
																	}
																});
															}else{
																var wnd = Ext.create('Ext.window.Window', {
																	title: me.res('gpTimetable.mainstamp.wnd.workplace.lbl'),
																	height: 135,
																	width: 400,
																	modal: true,
																	resizable: false,
																	layout: {
																		type: 'hbox',
																		align: 'middle'
																	},
																	items:[
																		{
																			xtype: 'button',
																			width: 200,
																			height: 100,
																			scale: 'large',
																			text: me.res('gpTimetable.mainstamp.wnd.workplaceoffice.lbl'),
																			iconCls: 'wtdrm-icon-office',
																			handler: function () {
																				me.setTimetable("O");
																				wnd.destroy();
																			}
																		},
																		{
																			xtype: 'button',
																			width: 200,
																			height: 100,
																			scale: 'large',
																			text: me.res('gpTimetable.mainstamp.wnd.workplacesmartworking.lbl'),
																			iconCls: 'wtdrm-icon-smartworking',
																			handler: function () {
																				me.setTimetable("S");
																				wnd.destroy();
																			}
																		}
																	] 
																}).show();
															}
														}
													}
												});
											}
										},
										'-',
										{
											xtype: 'button',
											reference: 'btnCompanyStamp',
											scale: 'large',
											hidden: true,
											text: me.res('gpTimetable.companystamp.lbl'),
											iconCls: 'wtdrm-icon-stampCompany',
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
							itemId: 'ttslist',
							title: me.res('timetable.tit.lbl'),
							iconCls: 'wtdrm-icon-timetableList',
							listeners: {
								activate: function() {
									me.getMainComponent().lookupReference('gpTimetableStamp').getStore().reload();
								}
							},
							items: [
								{
									region: 'north',
									xtype: 'wtdrmtimetablestampsearch',
									reference: 'filtersTimetableStamp',
									title: me.res('timetablesearch.tit.lbl'),
									iconCls: 'wtdrm-icon-timetableList',
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
												if (rec.get('type') === 'M') {
													return 'wtdrm-icon-stampType-default';
												} else if (rec.get('type') === 'C') {
													return 'wtdrm-icon-stampType-company';
												} else {
													return 'wtdrm-icon-stampType-special';
												}
											},
											iconSize: WTU.imgSizeToPx('xs'),
											width: 30
										}, {
											header: me.res('gpTimetable.date.lbl'),
											dataIndex: 'date',
											flex: 1,
											hideable: false,
											align: 'center'
										}, {
											header: me.res('gpTimetable.entrance.lbl'),
											dataIndex: 'entrance',
											flex: 1,
											hideable: false,
											align: 'center'
										}, {
											header: me.res('gpTimetable.exit.lbl'),
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
										}/*, {
											xtype: 'solookupcolumn',
											header: me.res('gpTimetable.activityId.lbl'),
											dataIndex: 'activityId',
											store: {
												autoload: true,
												model: 'WTA.model.ActivityLkp',
												proxy: WTF.proxy(WT.ID, 'LookupActivities')
											},
											displayField: 'desc',
											flex: 2,
											hideable: false,
											align: 'center'
										}*/
									],
									tbar: [
										me.getAct('timetableStamp', 'add'),
										'-',
										me.getAct('timetableStamp', 'delete')
									]
								}
							]
						}
					]
				},
				{
					xtype: 'container',
					itemId: 'tmtb.rqst',
					layout: 'border',
					items: [
						{
							region: 'north',
							xtype: 'wtdrmtimetablerequestsearch',
							reference: 'filtersTimetableRequest',
							title: me.res('timetableRequest.tit.lbl'),
							iconCls: 'wtdrm-icon-timetableRequests',
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
							viewConfig: {
								getRowClass: function(r, rowIndex, rp, ds) {
									if(r.get('userId') === WT.getVar('userId'))
										return 'timetablerequest-grid-row-ownrequests';
								} 
							}, 
							store: {
								autoLoad: false,
								model: 'Sonicle.webtop.drm.model.GridTimetableRequests',
								proxy: WTF.apiProxy(me.ID, 'ManageGridTimetableRequest'),
								listeners: {
									beforeload: function() {
										me.gpTimetableRequest().setSelection(null);
										me.updateTimetableRequestAction(null);
										return true;
									}
								}
							},
							columns: [
								{
									xtype: 'soiconcolumn',
									align: 'center',
									dataIndex: 'result',
									getIconCls: function(v, rec) {
										if (rec.get('status') === 'D') {
											return 'wtdrm-icon-timetableCancRequest-approved';
										} else if ((rec.get('result') === true || rec.get('result') === false) && rec.get('employeeCancReq') === true) {
											return 'wtdrm-icon-timetableCancRequest-new'; 
										} else if (rec.get('result') === true) {
											return 'wtdrm-icon-timetableRequest-approved';
										} else if (rec.get('result') === false) {
											return 'wtdrm-icon-timetableRequest-declined';
										} else {
											return 'wtdrm-icon-timetableRequest-sent';
										}
										//return me.cssIconCls((rec.get('status') === 'D') ? 'cancellationrequest-approved' : ((rec.get('result') === true || rec.get('result') === false) && rec.get('employeeCancReq') === true) ? 'cancellationrequest-new' : (rec.get('result') === true) ? 'approvedrequest' : (rec.get('result') === false) ? 'notapprovedrequest' : 'sendedrequest', 'xs');
									},
									iconSize: WTU.imgSizeToPx('xs'),
									width: 30,
									cls: 'resultCls',
									tdCls: 'resultTdCls'
								}, {
									header: me.res('gpTimetableRequest.type.lbl'),
									dataIndex: 'type',
									flex: 1,
									renderer: function (value, meta, record, rowIndex, colIndex, store) {
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
											case 'S':
												return me.res('gpTimetableRequest.type.S.lbl');
												break;
											default:
												return '';
												break;
										}
									}
								}, {
									header: me.res('gpTimetableRequest.requestBy.lbl'),
									dataIndex: 'user',
									flex: 1
								}, {
									header: me.res('gpTimetableRequest.manager.lbl'),
									dataIndex: 'manager',
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
									renderer: function (value, meta, record, rowIndex, colIndex, store) {
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
								me.getAct('timetableRequest', 'delete'),
								'-',
								me.getAct('timetableRequest', 'approve'),
								me.getAct('timetableRequest', 'decline'),
								me.getAct('timetableRequest', 'requestcancellation'),
								me.getAct('timetableRequest', 'cancel')
							],
							listeners: {
								selectionchange: function (t, recs) {
									me.updateTimetableRequestAction(recs[0]);
									
								},
								rowdblclick: function (s, rec) {
									me.editTimetableRequestUI(rec);
								},
								afterrender: function (t) {
									WT.ajaxReq(me.ID, 'IsLineManagerLogged', {
										params: {},
										callback: function (success, json) {
											if(success){
												if(json.data == true){
													me.getAct('timetableRequest', 'approve').setHidden(false);
													me.getAct('timetableRequest', 'decline').setHidden(false);
													me.getAct('timetableRequest', 'cancel').setHidden(false);
												}
											}
										}
									});
								},
								render: function(grid) {
									var view = grid.getView();
									grid.tip = Ext.create('Ext.tip.ToolTip', {
										target: view.getId(),
										delegate: view.itemSelector + ' .resultTdCls',
										trackMouse: true,
										listeners: {
											beforeshow: function updateTipBody(tip) {
												var tipGridView = tip.target.component;
												var rec = tipGridView.getRecord(tip.triggerElement);

												tip.update((rec.get('status') === 'D') ? me.res('gpTimetableRequest.tip.result.delete.lbl') : ((rec.get('result') === true || rec.get('result') === false) && rec.get('employeeCancReq') === true) ? me.res('gpTimetableRequest.tip.result.requestcancellation.lbl') : (rec.get('result') === true) ? me.res('gpTimetableRequest.tip.result.approved.lbl') : (rec.get('result') === false) ? me.res('gpTimetableRequest.tip.result.notapproved.lbl') : me.res('gpTimetableRequest.tip.result.sended.lbl'));
											}
										}
									});
								}
							}
						}
					]
				},
				{
					xtype: 'container',
					itemId: 'tmtb.rprt',
					layout: 'border',
					items: [
						{
							region: 'north',
							xtype: 'wtdrmtimetablereportgenerate',
							reference: 'filtersTimetableReport',
							title: me.res('timetableReport.tit.lbl'),
							iconCls: 'wtdrm-icon-timetableReport',
							titleCollapse: true,
							collapsible: true,
							sid: me.ID,
							isSupervisorUser: me.getVar('isSupervisorUser'),
							listeners: {
								generateOrView: function(s, query){
									if(query.mode === 1){
										me.timetableReportGenerateQuery = query;
										me.reloadTimetableReport(query);
									}else if(query.mode === 2){
										WT.confirm(me.res('gpTimetableReport.regeneratereport.lbl'), function (bid) {
											if (bid === 'yes') {
												me.timetableReportGenerateQuery = query;
												me.reloadTimetableReport(query);
											}
										});
									}
								},
								openWndDailyPresences: function(s){
									var me = this,
											vw = WT.createView(me.sid, 'view.DailyPresences', {
												swapReturn: true,
												viewCfg: {
													dockableConfig: {
														title: '{dailyPresences.tit}'
													}
												}
											});
									vw.showView();
								}
							}
						}, {
							region: 'center',
							xtype: 'grid',
							reference: 'gpTimetableReport',
                            cls: 'wtdrm-grid',
							modelValidation: true,
							bufferedRenderer: false,
							viewConfig: {
								getRowClass: function(r, rowIndex, rp, ds) {
									var today = new Date();
									if (r.get('date')==null) return;
									
									var dateParts = r.get('date').substring(0, 10).split("/");
									var dateObject = new Date(dateParts[2], dateParts[1] - 1, +dateParts[0]); 

									if (today > dateObject){
										var mh=r.get('missingHours');
										if(mh === null){
											return null;
										} else if (mh.charAt(0) === '+') {
											return 'timetablereport-grid-row-plus';
										} else if (mh.charAt(0) === '-') {
											return 'timetablereport-grid-row-minus';
										} else {
											return 'timetablereport-grid-row-correct';
										}
									}
								} 
							},
							plugins: [
								Ext.create('Ext.grid.plugin.CellEditing', {
									clicksToEdit: 1
								})
							],
							selModel: {
								selType: 'cellmodel'
							},
							store: {
								autoLoad: false,
								autoSync: true,
								model: 'Sonicle.webtop.drm.model.GridTimetableReport',
								proxy: WTF.apiProxy(me.ID, 'ManageGridTimetableReport'),
								groupField: 'targetUser'
							},
							features: [{
								// id: 'group',
								ftype: 'grouping',
								// groupHeaderTpl: '{name}'
                                groupHeaderTpl: '{name}',
								// hideGroupedHeader: true,
								enableGroupingMenu: false
							}],
							columns: [
								{
									xtype: 'soiconcolumn',
									dataIndex: 'hasRequests',
									editable: false,
									iconSize: WTU.imgSizeToPx('xs'),
									menuDisabled: true,
									width: 35,
									getIconCls: function(value,rec) {
										return value ? 'far fa-eye' : '';
									},
									handler: function(g, ridx, cidx, evt, rec) {
										if (rec.get("hasRequests")) {
											var	vw = WT.createView(me.ID, 'view.UserTimetableRequests', {
													swapReturn: true,
													viewCfg: {
														targetUserId: rec.get('targetUserId'),
														date: rec.get('dateObj'),
														dockableConfig: {
															title: me.res('usertimetablerequests.tit') + rec.get('date')
														}
													}
												});
											vw.showView();
										}
									}
								},								
								{
									header: me.res('gpTimetableReport.date.lbl'),
									dataIndex: 'date',
									editable: false,
									// flex: 2,
                                    cls: 'header',
                                    width: 100
								}, {
									header: me.res('gpTimetableReport.workingHours.lbl'),
									dataIndex: 'workingHours',
									editable: me.getVar('isSupervisorUser'),
									editor: Ext.create(WTF.lookupCombo('id', 'desc', {
										readOnly: !me.getVar('isSupervisorUser'),
										allowBlank: true,
										store:  Ext.create('Sonicle.webtop.drm.store.TimetableStampHours', {
											autoLoad: true
										}),
										triggers: {
											clear: WTF.clearTrigger()
										}
									})),
                                    width: 70,
                                    cls: 'header'
								}, {
									header: me.res('gpTimetableReport.overtime.lbl'),
									dataIndex: 'overtime',
									editable: me.getVar('isSupervisorUser'),
									editor: Ext.create(WTF.lookupCombo('id', 'desc', {
										readOnly: !me.getVar('isSupervisorUser'),
										allowBlank: true,
										store:  Ext.create('Sonicle.webtop.drm.store.TimetableStampHours', {
											autoLoad: true
										}),
										triggers: {
											clear: WTF.clearTrigger()
										}
									})),
                                    width: 70,
                                    cls: 'header'
								}, {
									header: me.res('gpTimetableReport.paidLeave.lbl'),
									dataIndex: 'paidLeave',
									editable: me.getVar('isSupervisorUser'),
									editor: Ext.create(WTF.lookupCombo('id', 'desc', {
										readOnly: !me.getVar('isSupervisorUser'),
										allowBlank: true,
										store:  Ext.create('Sonicle.webtop.drm.store.TimetableStampHours', {
											autoLoad: true
										}),
										triggers: {
											clear: WTF.clearTrigger()
										}
									})),
                                    width: 80,
                                    cls: 'header'
								}, 
//								{
//									header: me.res('gpTimetableReport.unpaidLeave.lbl'),
//									dataIndex: 'unpaidLeave',
//									editable: true,
//									editor: Ext.create(WTF.lookupCombo('id', 'desc', {
//                                        allowBlank: true,
//										store:  Ext.create('Sonicle.webtop.drm.store.TimetableStampHours', {
//											autoLoad: true
//										}),
//										triggers: {
//											clear: WTF.clearTrigger()
//										}
//									})),
//                                    width: 85,
//                                    cls: 'header'
//								}, 
								{
									header: me.res('gpTimetableReport.holiday.lbl'),
									dataIndex: 'holiday',
									editable: me.getVar('isSupervisorUser'),
									editor: Ext.create(WTF.lookupCombo('id', 'desc', {
										readOnly: !me.getVar('isSupervisorUser'),
										allowBlank: true,
										store:  Ext.create('Sonicle.webtop.drm.store.TimetableStampHours', {
											autoLoad: true
										}),
										triggers: {
											clear: WTF.clearTrigger()
										}
									})),
                                    width: 70,
                                    cls: 'header'
								},
								{
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
                                    width: 80,
                                    cls: 'header'
								}, 
								{
									header: me.res('gpTimetableReport.sickness.lbl'),
									dataIndex: 'sickness',
									editable: me.getVar('isSupervisorUser'),
									editor: Ext.create(WTF.lookupCombo('id', 'desc', {
										readOnly: !me.getVar('isSupervisorUser'),
										allowBlank: true,
										store:  Ext.create('Sonicle.webtop.drm.store.TimetableStampHours', {
											autoLoad: true
										}),
										triggers: {
											clear: WTF.clearTrigger()
										}
									})),
                                    width: 80,
                                    cls: 'header'
								}, {
									header: me.res('gpTimetableReport.other.lbl'),
									dataIndex: 'other',
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
                                    width: 60,
                                    cls: 'header'
								}, {
									xtype: 'solookupcolumn',
									header: me.res('gpTimetableReport.causalId.lbl'),
									dataIndex: 'causalId',
									editable: true,
									store: Ext.create('Ext.data.Store', {
										autoLoad: true,
										model: 'WTA.model.Simple',
										proxy: WTF.proxy(me.ID, 'LookupGisCausals', null, {
											extraParams: {
												filter: true
											}
										})
									}),
									editor: Ext.create(WTF.lookupCombo('id', 'desc', {
										ignoreNoChange: true,
										allowBlank: true,
										editable: true,
										anyMatch: true,
										store: Ext.create('Ext.data.Store', {
											autoLoad: true,
											model: 'WTA.model.Simple',
											proxy: WTF.proxy(me.ID, 'LookupGisCausals', null, {
												extraParams: {
													filter: true
												}
											})
										}),
										triggers: {
											clear: WTF.clearTrigger()
										}
									})),
									displayField: 'desc',
                                    flex: 2,
                                    cls: 'header'
								}, {
									header: me.res('gpTimetableReport.workReportHours.lbl'),
									dataIndex: 'workReportHours',
									editable: false,	
                                    width: 80,
                                    cls: 'header'
								}, {
									header: me.res('gpTimetableReport.jobHours.lbl'),
									dataIndex: 'jobHours',
									editable: false,	
                                    width: 80,
                                    cls: 'header'
								}, 
//								{
//									header: me.res('gpTimetableReport.totHours.lbl'),
//									dataIndex: 'totHours',
//									editable: false,	
//                                    width: 70,
//                                    cls: 'header'
//								}, 
								{
									header: me.res('gpTimetableReport.missingHours.lbl'),
									dataIndex: 'missingHours',
									editable: false,	
                                    width: 100,
									align: 'right',
                                    cls: 'header'
								}, {
									header: me.res('gpTimetableReport.detail.lbl'),
									dataIndex: 'detail',
									editable: true,
									editor: 'textfield',
									flex: 2,
                                    cls: 'header'
								}, {
									header: me.res('gpTimetableReport.note.lbl'),
									dataIndex: 'note',
									editable: true,
									editor: 'textfield',
									flex: 2,
                                    cls: 'header'
								}
							],
							tbar: [
//								me.getAct('timetableReport', 'save'),
//								'-',
								me.getAct('timetableReport', 'print'),
								(me.getVar('integrationGis') === true) ? me.getAct('timetableReport', 'export') : null
							]
/*							listeners: {
								rowdblclick: function (s, rec) {
									var	vw = WT.createView(me.ID, 'view.UserTimetableRequests', {
											swapReturn: true,
											viewCfg: {
												targetUserId: rec.get('userId'),
												date: rec.get('dateObj'),
												dockableConfig: {
													title: me.res('usertimetablerequests.tit') + rec.get('date')
												}
											}
										});
									vw.showView();
								}
							}*/
						}
					]
				},{
					xtype: 'container',
					itemId: 'tmtb.sumx',
					layout: 'border',
					items: [
						{
							region: 'center',
							xtype: 'wtdrmtimetablesummaryexcel',
							reference: 'filtersTimetableSummaryExcel',
							title: me.res('timetableSummaryExcel.tit.lbl'),
							iconCls: 'wtdrm-icon-timetable4',
							titleCollapse: false,
							collapsible: false,
							sid: me.ID,
							listeners: {
								generate: function(s, query){
									if (query !== undefined){
										WT.ajaxReq(me.ID, 'ExportTimetableSummaryExcel', {
											params: {
												query: Ext.JSON.encode(query)
											},
											callback: function (success, json) {
												if(success){

												}
											}
										});
									}
								}
							}
						}]
				},{
					xtype: 'container',
					itemId: 'job',
					layout: 'border',
					items: [
						{
							region: 'north',
							xtype: 'wtdrmjobsearch',
							reference: 'filtersJob',
							title: me.res('gpJob.tit.lbl'),
							iconCls: 'wtdrm-icon-job',
							titleCollapse: true,
							collapsible: true,
							sid: me.ID,
							useStatisticCustomer: me.getVar('useStatisticCustomer'),
							listeners: {
								search: function(s, query){
									me.reloadJob(query);
								}
							}
						},
						{
							region: 'center',
							xtype: 'grid',
							reference: 'gpJob',
							store: {
								autoLoad: false,
								model: 'Sonicle.webtop.drm.model.GridJobs',
								proxy: WTF.apiProxy(me.ID, 'ManageGridJob')
							},
							columns: [
								{
									dataIndex: 'companyDescription',
									header: me.res('gpJob.company.lbl'),
									flex: 3
								},{
									dataIndex: 'operatorDescription',
									header: me.res('gpJob.user.lbl'),
									flex: 3
								},{
									dataIndex: 'customerDescription',
									header: me.res('gpJob.realcustomer.lbl'),
									flex: 3
								},{
									dataIndex: 'customerStatDescription',
									header: me.res('gpJob.statcustomer.lbl'),
									flex: 3
								},{
									dataIndex: 'activityDescription',
									header: me.res('gpJob.activity.lbl'),
									flex: 3
								},{
									dataIndex: 'startDate',
									header: me.res('gpJob.startdate.lbl'),
									xtype: 'datecolumn',
									format: WT.getShortDateFmt(),
									flex: 2
								},{
									dataIndex: 'startDate',
									header: me.res('gpJob.starttime.lbl'),
									xtype: 'datecolumn',
									format: WT.getShortTimeFmt(),
									flex: 2
								},{
									dataIndex: 'endDate',
									header: me.res('gpJob.endtime.lbl'),
									xtype: 'datecolumn',
									format: WT.getShortTimeFmt(),
									flex: 2
								},{
									dataIndex: 'title',
									header: me.res('gpJob.title.lbl'),
									flex: 3
								}
							],
							tbar: [								
								me.getAct('job', 'add'),
								'-',
								me.getAct('job', 'edit'),
								me.getAct('job', 'delete'),
								'-',
								me.getAct('job', 'associate'),
								'-',
								me.getAct('job', 'export')
							],
							listeners: {
								rowclick: function (s, rec) {
									me.getAct('job', 'edit').setDisabled(false);
									me.getAct('job', 'delete').setDisabled(false);
									me.getAct('job', 'associate').setDisabled(false);
								},
								rowdblclick: function (s, rec) {
									me.editJobUI(rec);
								}
							}
						}
					]
				},{
					xtype: 'container',
					itemId: 'tckt',
					layout: 'border',
					items: [
						{
							region: 'north',
							xtype: 'wtdrmticketsearch',
							reference: 'filtersTicket',
							title: me.res('gpTicket.tit.lbl'),
							iconCls: 'wtdrm-icon-ticket',
							titleCollapse: true,
							collapsible: true,
							sid: me.ID,
							s: me,
							useStatisticCustomer: me.getVar('useStatisticCustomer'),
							listeners: {
								search: function(s, query){
									me.reloadTicket(query);
								}
							}
						},
						{
							region: 'center',
							xtype: 'grid',
							reference: 'gpTicket',
							store: {
								autoLoad: false,
								model: 'Sonicle.webtop.drm.model.GridTickets',
								proxy: WTF.apiProxy(me.ID, 'ManageGridTicket')
							},
							columns: [
								{
									dataIndex: 'number',
									header: me.res('gpTicket.number.lbl'),
									// flex: 2
									width: 130
								},{
									dataIndex: 'companyDescription',
									header: me.res('gpTicket.company.lbl'),
									flex: 2
								},{
									dataIndex: 'toOperatorDescription',
									header: me.res('gpTicket.toOperator.lbl'),
									flex: 3
								},{
									dataIndex: 'customerDescription',
									header: me.res('gpTicket.realCustomer.lbl'),
									flex: 3
								},{
									dataIndex: 'customerStatDescription',
									header: me.res('gpTicket.statCustomer.lbl'),
									flex: 3
								},{
									dataIndex: 'ticketCategoryDescription',
									header: me.res('gpTicket.ticketCategory.lbl'),
									flex: 3
								},{
									dataIndex: 'date',
									header: me.res('gpTicket.date.lbl'),
									xtype: 'datecolumn',
									format: WT.getShortDateTimeFmt(),
									flex: 3
								},{
									dataIndex: 'title',
									header: me.res('gpTicket.title.lbl'),
									flex: 2
								},{
									dataIndex: 'statusDescription',
									header: me.res('gpTicket.status.lbl'),
									flex: 2
								} /*,{
									xtype: 'solookupcolumn',
									dataIndex: 'priorityId',
									store: Ext.create('Sonicle.webtop.drm.store.PriorityType', {
										autoLoad: true
									}),
									header: me.res('gpTicket.priority.lbl'),
									displayField: 'desc',
									flex: 1
								}*/
							],
							tbar: [								
								me.getAct('ticket', 'add'),
								'-',
								me.getAct('ticket', 'edit'),
								me.getAct('ticket', 'delete'),
								'-',
								me.getAct('ticket', 'close')
							],
							listeners: {
								rowclick: function (s, rec) {
									me.getAct('ticket', 'edit').setDisabled(false);
									me.getAct('ticket', 'delete').setDisabled(false);
									me.getAct('ticket', 'close').setDisabled(rec.get('statusId') === parseInt(me.getVar('ticketDefaultCloseStatus')));
								},
								rowdblclick: function (s, rec) {
									me.editTicketUI(rec);
								}
							}
						}
					]
				}
			]
		}));
		
		//Opportunity Fields
		me.opportunityRequiredFields = me.getVar('opportunityRequiredFields');
	},
	
	filtersOpportunity: function () {
		return this.getMainComponent().lookupReference('filtersOpportunity');
	},
	
	gpOpportunity: function () {
		return this.getMainComponent().lookupReference('gpOpportunity');
	},
	
	gpOpportunitySelected: function () {
		return this.getMainComponent().lookupReference('gpOpportunity').getSelection()[0];
	},
	
	filtersWorkReport: function () {
		return this.getMainComponent().lookupReference('filtersWorkReport');
	},
	
	filtersJob: function () {
		return this.getMainComponent().lookupReference('filtersJob');
	},

	filtersTicket: function () {
		return this.getMainComponent().lookupReference('filtersTicket');
	},
	
	gpWorkReport: function () {
		return this.getMainComponent().lookupReference('gpWorkReport');
	},
	
	gpJob: function () {
		return this.getMainComponent().lookupReference('gpJob');
	},
	
	gpWorkReportSelected: function () {
		return this.getMainComponent().lookupReference('gpWorkReport').getSelection()[0];
	},
	
	gpJobSelected: function () {
		return this.getMainComponent().lookupReference('gpJob').getSelection()[0];
	},
	
	filtersExpenseNote: function () {
		return this.getMainComponent().lookupReference('filtersExpenseNote');
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
	
	gpTicket: function () {
		return this.getMainComponent().lookupReference('gpTicket');
	},
	
	gpTicketSelected: function () {
		return this.getMainComponent().lookupReference('gpTicket').getSelection()[0];
	},
	
	filtersTimetableReport: function () {
		return this.getMainComponent().lookupReference('filtersTimetableReport');
	},
	
	filtersTimetableSummaryExcel: function () {
		return this.getMainComponent().lookupReference('filtersTimetableSummaryExcel');
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
				iconCls: 'wtdrm-icon-configuration',
				handler: function () {
					me.configurationView();
				}
			});
		}
		if (WT.isPermitted(me.ID,'OPPORTUNITY_SETTINGS','MANAGE')){
			me.addAct('toolbox', 'opSetting', {
				text: me.res('toolbox.op-settings.lbl'),
				tooltip: null,
				iconCls: 'wtdrm-icon-configuration',
				handler: function () {
					me.opportunitySetting();
				}
			});
		}
		if (WT.isPermitted(me.ID,'WORK_REPORT_SETTINGS','MANAGE')){
			me.addAct('toolbox', 'wrSetting', {
				text: me.res('toolbox.wr-settings.lbl'),
				tooltip: null,
				iconCls: 'wtdrm-icon-configuration',
				handler: function () {
					me.workReportSetting();
				}
			});
		}
		if (WT.isPermitted(me.ID,'EXPENSE_NOTE_SETTINGS','MANAGE')){
			me.addAct('toolbox', 'enSetting', {
				text: me.res('toolbox.en-settings.lbl'),
				tooltip: null,
				iconCls: 'wtdrm-icon-configuration',
				handler: function () {
					me.expenseNoteSetting();
				}
			});
		}
		if (WT.isPermitted(me.ID,'TIMETABLE_SETTINGS','MANAGE')){
			me.addAct('toolbox', 'ttSetting', {
                xtype: 'splitbutton',
                text: me.res('toolbox.tt-settings.lbl'),
				iconCls: 'wtdrm-icon-configuration',
                handler: function () {
					// do nothing()
				},
                menu: {
                    items: [
                       {
                            text: me.res('toolbox.tt-settings-general.lbl'),
                            tooltip: null,
                            iconCls: 'wtdrm-icon-configuration',
                            handler: function () {
                                me.timetableSettingGeneral();
                            }
                        },
                        {
                            text: me.res('toolbox.tt-settings-holidays.lbl'),
                            tooltip: null,
                            iconCls: 'wtdrm-icon-configuration',
                            handler: function () {
                                me.timetableSettingHolidays();
                            }
                        },
                        {
                            text: me.res('toolbox.tt-settings-employeeprofiles.lbl'),
                            tooltip: null,
                            iconCls: 'wtdrm-icon-configuration',
                            handler: function () {
                                me.timetableSettingEmployeeProfiles();
                            }
                        },
                        {
                            text: me.res('toolbox.tt-settings-hourprofiles.lbl'),
                            tooltip: null,
                            iconCls: 'wtdrm-icon-configuration',
                            handler: function () {
                                me.timetableSettingHourProfiles();
                            }
                        },
						{
                            text: me.res('toolbox.tt-settings-causals.lbl'),
                            tooltip: null,
                            iconCls: 'wt-icon-options',
                            handler: function () {
                                me.timetableSettingCausals();
                            }
                        },
						{
                            text: me.res('toolbox.tt-settings-gis.lbl'),
                            tooltip: null,
                            iconCls: 'wt-icon-options',
							hidden: !me.getVar('integrationGis'),
                            handler: function () {
                                me.timetableSettingGis();
                            }
                        }
                    ]
                }
            });
		}
		if (WT.isPermitted(me.ID,'TICKET_SETTINGS','MANAGE')){
			me.addAct('toolbox', 'tkSetting', {
				text: me.res('toolbox.tk-settings.lbl'),
				tooltip: null,
				iconCls: 'wtdrm-icon-configuration',
				handler: function () {
					me.ticketSetting();
				}
			});
		}
		me.addAct('opportunity', 'add', {
			text: me.res('act-addOpportunity.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-add',
			handler: function () {
				me.addOpportunity({
					callback: function (success) {
						if (success) {
							me.filtersOpportunity().extractData();
						}
					}
				});
			}
		});
		me.addAct('opportunity', 'edit', {
			text: WT.res('act-edit.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-edit',
			disabled: true,
			handler: function () {
				var sel = me.gpOpportunitySelected();
				me.editUI(sel);
			}
		});
		me.addAct('opportunity', 'delete', {
			text: WT.res('act-delete.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-delete',
			disabled: true,
			handler: function () {
				var sel = me.gpOpportunitySelected();
				me.deleteUI(sel);
			}
		});
		me.addAct('opportunity', 'addAction', {
			text: me.res('act-addOpportunityAction.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-add',
			disabled: true,
			handler: function () {
				me.addOpportunityActionUI({
					callback: function (success) {
						if (success) {
							me.filtersOpportunity().extractData();
						}
					}
				});
			}
		});
		me.addAct('opportunity', 'prepareActions', {
			text: me.res('act-prepareOpportunityAction.lbl'),
			tooltip: null,
			iconCls: 'wtdrm-icon-initializeOpportunity',
			disabled: true,
			handler: function () {
				me.prepareOpportunityActions({
					callback: function (success) {
						if (success) {
							me.filtersOpportunity().extractData();
						}
					}
				});
			}
		});
		me.addAct('workReport', 'add', {
			text: WT.res('act-add.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-add',
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
			iconCls: 'wt-icon-edit',
			disabled: true,
			handler: function () {
				var sel = me.gpWorkReportSelected();
				me.editWorkReportUI(sel);
			}
		});
		me.addAct('workReport', 'delete', {
			text: WT.res('act-delete.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-delete',
			disabled: true,
			handler: function () {
				var sel = me.gpWorkReportSelected();
				me.deleteWorkReportUI(sel);
			}
		});
		me.addAct('workReport', 'printSummary', {
			text: me.res('act-printSummary.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-print',
			handler: function () {
				me.printWorkReportSummary();
			}
		});
		me.addAct('job', 'add', {
			text: WT.res('act-add.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-add',
			handler: function () {
				me.addJob({
					callback: function (success) {
						if (success) {
							me.filtersJob().extractData();
						}
					}
				});
			}
		});
		me.addAct('job', 'edit', {
			text: WT.res('act-edit.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-edit',
			disabled: true,
			handler: function () {
				var sel = me.gpJobSelected();
				me.editJobUI(sel);
			}
		});
		me.addAct('job', 'delete', {
			text: WT.res('act-delete.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-delete',
			disabled: true,
			handler: function () {
				var sel = me.gpJobSelected();
				me.deleteJobUI(sel);
			}
		});
		me.addAct('expenseNote', 'add', {
			text: WT.res('act-add.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-add',
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
			iconCls: 'wt-icon-edit',
			disabled: true,
			handler: function () {
				var sel = me.gpExpenseNoteSelected();
				me.editExpenseNoteUI(sel);
			}
		});
		me.addAct('expenseNote', 'delete', {
			text: WT.res('act-delete.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-delete',
			disabled: true,
			handler: function () {
				var sel = me.gpExpenseNoteSelected();
				me.deleteExpenseNoteUI(sel);
			}
		});
		me.addAct('timetableRequest', 'add', {
			text: WT.res('act-add.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-add',
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
			iconCls: 'wt-icon-edit',
			disabled: true,
			handler: function () {
				var sel = me.gpTimetableRequestSelected();
				me.editTimetableRequestUI(sel);
			}
		});
		me.addAct('timetableRequest', 'delete', {
			text: WT.res('act-delete.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-delete',
			disabled: true,
			handler: function () {
				var sel = me.gpTimetableRequestSelected();
				me.deleteTimetableRequestUI(sel);
			}
		});
		me.addAct('timetableRequest', 'approve', {
			text: me.res('timetablerequest.approve.lbl'),
			tooltip: null,
			iconCls: 'wtdrm-icon-timetableRequest-approved',
			disabled: true,
			hidden: true,
			handler: function () {
				var sel = me.gpTimetableRequestSelected();
				me.approveDeclineTimetableRequestUI(sel, true);
			}
		});
		me.addAct('timetableRequest', 'decline', {
			text: me.res('timetablerequest.decline.lbl'),
			tooltip: null,
			iconCls: 'wtdrm-icon-timetableRequest-declined',
			disabled: true,
			hidden: true,
			handler: function () {
				var sel = me.gpTimetableRequestSelected();
				me.approveDeclineTimetableRequestUI(sel, false);
			}
		});
		me.addAct('timetableRequest', 'cancel', {
			text: me.res('timetablerequest.cancel.lbl'),
			tooltip: null,
			iconCls: 'wtdrm-icon-timetableCancRequest-approved',
			disabled: true,
			hidden: true,
			handler: function () {
				var sel = me.gpTimetableRequestSelected();
				me.cancelTimetableRequestUI(sel);
			}
		});
		me.addAct('timetableRequest', 'requestcancellation', {
			text: me.res('timetablerequest.requestcancellation.lbl'),
			tooltip: null,
			iconCls: 'wtdrm-icon-timetableCancRequest-new',
			disabled: true,
			handler: function () {
				var sel = me.gpTimetableRequestSelected();
				me.timetableRequestCancellationUI(sel);
			}
		});
		me.addAct('timetableReport', 'save', {
			text: WT.res('act-save.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-save',
			handler: function () {
				
			}
		});
		me.addAct('timetableReport', 'print', {
			text: WT.res('act-print.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-print',
			handler: function () {
				me.printTimetableReport();
			}
		});		
		me.addAct('timetableReport', 'export', {
			text: me.res('act-export.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-file-download',
			handler: function () {
				me.exportTimetableReportGis();
			}
		});
		me.addAct('timetableStamp', 'add', {
			text: WT.res('act-add.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-add',
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
		me.addAct('timetableStamp', 'delete', {
			text: WT.res('act-delete.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-delete',
			disabled: true,
			handler: function () {
				var sel = me.gpTimetableStampSelected();
				me.deleteTimetableStampUI(sel);
			}
		});
		me.addAct('ticket', 'add', {
			text: WT.res('act-add.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-add',
			handler: function () {
				me.addTicket({
					callback: function (success, model, blnOpenJob) {
						if (success) {
							me.filtersTicket().extractData();
							
							if (blnOpenJob) me.closeTicket(model);
						}
					}
				});
			}
		});
		me.addAct('ticket', 'edit', {
			text: WT.res('act-edit.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-edit',
			disabled: true,
			handler: function () {
				var sel = me.gpTicketSelected();
				me.editTicketUI(sel);
			}
		});
		me.addAct('ticket', 'delete', {
			text: WT.res('act-delete.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-delete',
			disabled: true,
			handler: function () {
				var sel = me.gpTicketSelected();
				me.deleteTicketUI(sel);
			}
		});
		me.addAct('ticket', 'close', {
			text: me.res('ticket.btn-closeTicket.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-lock',
			disabled: true,
			handler: function () {
				var sel = me.gpTicketSelected();
				me.closeTicket(sel);
			}
		});
		me.addAct('job', 'associate', {
			text: me.res('job.btn-associateTicket.lbl'),
			tooltip: null,
			iconCls: 'wtdrm-icon-associateTicket',
			disabled: true,
			handler: function () {
				var sel = me.gpJobSelected();
				me.associateTicket(sel);
			}
		});
		me.addAct('job', 'export', {
			text: me.res('job.btn-export.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-file-download',
			handler: function () {
				me.exportJobs();
			}
		});
	},
	
	initCxm: function () {
		var me = this;
	},

	addOpportunity: function (opts) {
		opts = opts || {};

		var me = this,
			fop = me.filtersOpportunity(),
			vw = WT.createView(me.ID, 'view.Opportunity', {
                swapReturn: true, 
                viewCfg: {
                    dockableConfig: {
                        title: (me.getVar('opportunityTitle') == null  || me.getVar('opportunityTitle') == '') ? me.res('opportunity.tit') : me.getVar('opportunityTitle')
                    }
                }
            });
		vw.on('viewsave', function (s, success, model) {
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vw.showView(function () {
            vw.begin('new', {
                data: {
                    operatorId: fop.getOperatorId()
                }
            });
        });
	},
	editUI: function (rec) {
		var me = this;
		if(rec.get('actionId') === 0) me.editOpportunityUI(rec);
		else me.editOpportunityActionUI(rec);
		
	},
	editOpportunityUI: function (rec) {
		var me = this;
		me.editOpportunity(rec.get('id'), {
			callback: function (success, model) {
				if (success) {
					this.gpOpportunity().getStore().load();
				} else {
					alert('error');
				}
			}
		});
	},
	editOpportunity: function (id, opts) {
		opts = opts || {};
		var me = this,
            vw = WT.createView(me.ID, 'view.Opportunity', {
                swapReturn: true, viewCfg: {
                    dockableConfig: {
                        title: (me.getVar('opportunityTitle') == null  || me.getVar('opportunityTitle') == '') ? me.res('opportunity.tit') : me.getVar('opportunityTitle')
                    }
                }
            });
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
	editOpportunityActionUI: function(rec) {
		var me = this;
		me.editOpportunityAction(rec.get('actionId'), {
			callback: function (success, model) {
				if (success) {
					this.gpOpportunity().getStore().load();
				} else {
					alert('error');
				}
			}
		});
	},
	editOpportunityAction: function(id, opts) {
		opts = opts || {};
		var me = this,
				vw = WT.createView(me.ID, 'view.OpportunityAction', {swapReturn: true});
		
		vw.on('viewsave', function (s, success, model) {
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vw.showView(function() {
			vw.begin('edit', {
				data: {
					id: id
				}
			});
		});
	},
	deleteUI: function (rec) {
		var me = this;
		if(rec.get('actionId') === 0) me.deleteOpportunityUI(rec);
		else me.deleteOpportunityActionUI(rec);
	},
	deleteOpportunityUI: function (rec) {
		var me = this,
				sto = me.gpOpportunity().getStore(),
				msg;
		if (rec) {
			msg = me.res('act.confirm.delete', Ext.String.ellipsis(rec.get('id'), 40));
		} else {
			msg = me.res('gpOpportunity.confirm.delete.selection');
		}
		WT.confirm(msg, function (bid) {
			if (bid === 'yes') {
				me.deleteOpportunity(rec.get('id'), {
					callback: function (success) {
						if (success) {
							me.filtersOpportunity().extractData();
						}
					}
				});
			}
		});
	},
	deleteOpportunity: function (id, opts) {
		opts = opts || {};
		var me = this;
		WT.ajaxReq(me.ID, 'ManageOpportunity', {
			params: {
				crud: 'delete',
				ids: WTU.arrayAsParam(id)
			},
			callback: function (success, json) {
				Ext.callback(opts.callback, opts.scope || me, [success, json]);
			}
		});
	},
	deleteOpportunityActionUI: function (rec) {
		var me = this,
				sto = me.gpOpportunity().getStore(),
				msg;
		if (rec) {
			msg = me.res('act.confirm.delete', Ext.String.ellipsis(rec.get('actionId'), 40));
		} else {
			msg = me.res('gpOpportunityActions.confirm.delete.selection');
		}
		WT.confirm(msg, function (bid) {
			if (bid === 'yes') {
				me.deleteOpportunityAction(rec.get('actionId'), {
					callback: function (success) {
						if (success) {
							me.filtersOpportunity().extractData();
						}
					}
				});
			}
		});
	},
	deleteOpportunityAction: function (id, opts) {
		opts = opts || {};
		var me = this;
		WT.ajaxReq(me.ID, 'ManageOpportunityAction', {
			params: {
				crud: 'delete',
				ids: WTU.arrayAsParam(id)
			},
			callback: function (success, json) {
				Ext.callback(opts.callback, opts.scope || me, [success, json]);
			}
		});
	},
	addOpportunityActionUI: function() {
		var me = this;
		me.addOpportunityAction({}, {
			callback: function(success, model) {
				if (success) {
					me.filtersOpportunity().extractData();
				}
			}
		});
	},
	addOpportunityAction: function (data, opts) {
		var me = this,
				vw = WT.createView(me.ID, 'view.OpportunityAction', {swapReturn: true});
		
		var sel = me.gpOpportunitySelected();
		
		vw.on('viewsave', function(s, success, model) {
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vw.showView(function() {
			vw.begin('new', {
				data: {
					opportunityId: sel.get('id'),
					operatorId: sel.get('operatorId')
				}
			});
		});
	},
	prepareOpportunityActions: function (opts) {
		opts = opts || {};

		var me = this,
				vw = WT.createView(me.ID, 'view.PrepareOpportunityActions', {swapReturn: true});
		
		var sel = me.gpOpportunitySelected();
		
		vw.on('viewsave', function(s, success, model) {
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vw.showView(function() {
			vw.begin('new', {
				data: {
					opportunityId: sel.get('id'),
					operatorId: sel.get('operatorId')
				}
			});
		});
	},
	opportunitySetting: function (opts) {
		opts = opts || {};

		var me = this,
				vw = WT.createView(me.ID, 'view.OpportunitySetting', {swapReturn: true});
		vw.on('viewsave', function (s, success, model) { 
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vw.showView(function () {
            vw.begin('edit', {
                data: {
                    id: 'oppo'
                }
            });
        });
	},
	printOpportunity: function(ids) {
		var me = this, url;
		url = WTF.processBinUrl(me.ID, 'PrintOpportunity', {ids: WTU.arrayAsParam(ids)});
		Sonicle.URLMgr.openFile(url, {filename: 'opportunity', newWindow: true});
	},
	
	addWorkReport: function (opts) {
		opts = opts || {};

		var me = this,
			fwr = me.filtersWorkReport(),
			vw = WT.createView(me.ID, 'view.WorkReport', {swapReturn: true});
		vw.on('viewsave', function (s, success, model) {
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vw.showView(function () {
            vw.begin('new', {
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
				vw = WT.createView(me.ID, 'view.WorkReport', {swapReturn: true});
		vw.on('viewsave', function (s, success, model) {
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vw.showView(function () {
			vw.begin('edit', {
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
			msg = me.res('act.confirm.delete', Ext.String.ellipsis(rec.get('number'), 40));
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
	editJobUI: function (rec) {
		var me = this;
		me.editJob(rec.get('jobId'), {
			callback: function (success, model) {
				if (success) {
					this.gpJob().getStore().load();
				} else {
					alert('error');
				}
			}
		});
	},
	editJob: function (jobId, opts) {
		opts = opts || {};
		var me = this,
            vw = WT.createView(me.ID, 'view.Job', {swapReturn: true});
		vw.on('viewsave', function (s, success, model) {
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vw.showView(function () {
			vw.begin('edit', {
				data: {
					jobId: jobId
				}
			});
		});
	},
	deleteJobUI: function (rec) {
		var me = this,
				sto = me.gpJob().getStore(),
				msg;
		if (rec) {
			msg = me.res('act.confirm.delete', Ext.String.ellipsis(rec.get('number'), 40));
		} else {
			msg = me.res('gpJob.confirm.delete.selection');
		}
		WT.confirm(msg, function (bid) {
			if (bid === 'yes') {
				me.deleteJob(rec.get('jobId'), {
					callback: function (success) {
						if (success)
							sto.remove(rec);
					}
				});
			}
		});
	},
	deleteJob: function (jobId, opts) {
		opts = opts || {};
		var me = this;
		WT.ajaxReq(me.ID, 'ManageJob', {
			params: {
				crud: 'delete',
				jobIds: WTU.arrayAsParam(jobId)
			},
			callback: function (success, json) {
				Ext.callback(opts.callback, opts.scope || me, [success, json]);
			}
		});
	},
	workReportSetting: function (opts) {
		opts = opts || {};

		var me = this,
				vw = WT.createView(me.ID, 'view.WorkReportSetting', {swapReturn: true});
		vw.on('viewsave', function (s, success, model) { 
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vw.showView(function () {
					vw.begin('edit', {
						data: {
							id: 'wrkr'
						}
					});
				});
	},
	ticketSetting: function (opts) {
		opts = opts || {};

		var me = this,
				vw = WT.createView(me.ID, 'view.TicketSetting', {swapReturn: true});
		vw.on('viewsave', function (s, success, model) { 
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vw.showView(function () {
					vw.begin('edit', {
						data: {
							id: 'tckt'
						}
					});
				});
	},
	addJob: function (opts) {
		opts = opts || {};

		var me = this,
			fj = me.filtersJob(),
			vw = WT.createView(me.ID, 'view.Job', {swapReturn: true});
		vw.on('viewsave', function (s, success, model) {
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vw.showView(function () {
				vw.begin('new', {
					data: {
						operatorId: fj.getOperatorId(),
						startDate: new Date(),
						endDate: new Date(),
						timezone: WT.getVar('timezone')
					}
				});
			});
	},
    addJobFromAliseoWeb: function (data, opts) {
		opts = opts || {};

		var me = this,
			vw = WT.createView(me.ID, 'view.Job', {swapReturn: true});
		vw.on('viewsave', function (s, success, model) {
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vw.showView(function () {
				vw.begin('new', {
					data: {
						operatorId: data.operatorId,
						startDate: data.startDate,
						endDate: data.endDate,
						timezone: data.timezone,
                        customerId: data.customerId,
                        customerStatId: data.customerStatId
					}
				});
			});
	},
	addJobFromTicket: function (operatorId, customerId, customerStatId, companyId, title, timezone, ticketId, opts) {
		opts = opts || {};

		var me = this,
			vw = WT.createView(me.ID, 'view.Job', {swapReturn: true});
		vw.on('viewsave', function (s, success, model) {
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vw.showView(function () {
				vw.begin('new', {
					data: {
						operatorId: operatorId,
						customerId: customerId,
						customerStatId: customerStatId,
						companyId: companyId,
						title: title,
						ticketId: ticketId,
						timezone: timezone,
						startDate: new Date(),
						endDate: new Date()
					}
				});
			});
	},
	closeTicket: function(mo) {
		var me = this,
			ft = me.filtersTicket();
				
		// me.mys.wait();
		WT.ajaxReq(me.ID, 'ManageTicket', {
			params: {
				crud: 'end',
				ticketIds: WTU.arrayAsParam([mo.get('ticketId')])
			},
			callback: function(success) {
				// me.mys.unwait();
				if (success) {
					me.gpTicket().getStore().load();						

					me.addJobFromTicket(
						mo.get('toOperatorId'), 
						mo.get('customerId'), 
						mo.get('customerStatId'), 
						mo.get('companyId'), 
						mo.get('title'), 
						mo.get('timezone'), 
						mo.get('ticketId')
					);
				}
			}
		});
	},
	associateTicket: function(mo) {
		var me = this;
        
		WT.confirm(me.res('confirmBox.associateTicket.lbl'), function(bid, value) {
			if (bid === 'ok') {
				if (value !== null) {
					WT.ajaxReq(me.ID, 'ManageJob', {
						params: {
							crud: 'associate',
							ticketId: value,
							jobId: mo.get('jobId')
						},
						callback: function(success) {
							if (success) {
								WT.info(me.res('confirmBox.associateTicket.ok.lbl'));
							}
						}
					});		
				}
			}
		}, me, {
			buttons: Ext.Msg.OKCANCEL,
			title: me.res('act-associateTicket.confirm.tit'),
			instClass: 'Sonicle.webtop.drm.ux.ChooseTicketConfirmBox',
			instConfig: {
				sid: me.ID,
				customerId: mo.get('customerId')
			}
		});
	},
	exportJobs: function () {
		var me = this,
			fj = me.filtersJob();	
		
		WT.ajaxReq(me.ID, 'ExportJobs', {
			params: {
				op: 'do',
				query: Ext.JSON.encode(fj.getData())
			},
			callback: function(success) {
				if (success) {
					Sonicle.URLMgr.download(WTF.processBinUrl(me.ID, 'ExportJobs'));
				}
			}
		});
	},
	exportTimetableReportGis: function () {
		var me = this;
		
		WT.ajaxReq(me.ID, 'ExportTimetableReportGis', {
			params: {
				op: 'do',
				query: Ext.JSON.encode(me.timetableReportGenerateQuery)
			},
			callback: function(success) {
				if (success) {
					Sonicle.URLMgr.download(WTF.processBinUrl(me.ID, 'ExportTimetableReportGis'));
				}
			}
		});
	},
	addTicket: function (opts) {
		opts = opts || {};
		var me = this,
			ft = me.filtersTicket(),
			vw = WT.createView(me.ID, 'view.Ticket', {
                swapReturn: true,
                viewCfg: {
                    blnNew: true
                }
        });
		vw.on('viewsave', function (s, success, model) {			
			Ext.callback(opts.callback, opts.scope || me, [success, model, s.blnOpenJob === true]);
		});
		vw.showView(function () {
				vw.begin('new', {
					data: {
						fromOperatorId: WT.getVar('userId'),
						date: new Date(),
						timezone: WT.getVar('timezone'),
                        blnNew: true
					}
				});
			});
	},
    addTicketFromAliseoWeb: function (data, opts) {
		opts = opts || {};

		var me = this,
			vw = WT.createView(me.ID, 'view.Ticket', {
                swapReturn: true,
                viewCfg: {
                    blnNew: true
                }
        });
		vw.on('viewsave', function (s, success, model) {
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vw.showView(function () {
				vw.begin('new', {
					data: {
						fromOperatorId: data.fromOperatorId,
						date: data.date,
						timezone: data.timezone,
                        customerId: data.customerId,
                        customerStatId: data.customerStatId
					}
				});
			});
	},
	editTicketUI: function (rec) {
		var me = this;
		me.editTicket(rec.get('ticketId'), {
			callback: function (success, model, blnOpenJob) {
				if (success) {
					this.gpTicket().getStore().load();
					
					if (blnOpenJob) me.closeTicket(model);
				} else {
					alert('error');
				}
			}
		});
	},
	editTicket: function (ticketId, opts) {
		opts = opts || {};
		var me = this,
				vw = WT.createView(me.ID, 'view.Ticket', {swapReturn: true});
		vw.on('viewsave', function (s, success, model) {
			Ext.callback(opts.callback, opts.scope || me, [success, model, s.blnOpenJob === true]);
		});
		vw.showView(function () {
			vw.begin('edit', {
				data: {
					ticketId: ticketId
				}
			});
		});
	},
	deleteTicketUI: function (rec) {
		var me = this,
				sto = me.gpTicket().getStore(),
				msg;
		if (rec) {
			msg = me.res('act.confirm.delete', Ext.String.ellipsis(rec.get('number'), 40));
		} else {
			msg = me.res('gpTicket.confirm.delete.selection');
		}
		WT.confirm(msg, function (bid) {
			if (bid === 'yes') {
				me.deleteTicket(rec.get('ticketId'), {
					callback: function (success) {
						if (success)
							sto.remove(rec);
					}
				});
			}
		});
	},
	deleteTicket: function (ticketId, opts) {
		opts = opts || {};
		var me = this;
		WT.ajaxReq(me.ID, 'ManageTicket', {
			params: {
				crud: 'delete',
				ticketIds: WTU.arrayAsParam(ticketId)
			},
			callback: function (success, json) {
				Ext.callback(opts.callback, opts.scope || me, [success, json]);
			}
		});
	},	
	reloadOpportunity: function (query) {
		var me = this,
				pars = {},
				sto;
		if (me.isActive() && me.itemActiveId() === 'oppo') {
			sto = me.gpOpportunity().getStore();
			if (query !== undefined)
				Ext.apply(pars, {query: Ext.JSON.encode(query)});
			WTU.loadWithExtraParams(sto, pars);
		} else {
			me.needsReload = true;
		}
	},
	reloadWorkReport: function (query) {
		var me = this,
				pars = {},
				sto;
		if (me.isActive() && me.itemActiveId() === 'wrkr') {
			sto = me.gpWorkReport().getStore();
			if (query !== undefined)
				Ext.apply(pars, {query: Ext.JSON.encode(query)});
			WTU.loadWithExtraParams(sto, pars);
		} else {
			me.needsReload = true;
		}
	},
	reloadJob: function (query) {
		var me = this,
				pars = {},
				sto;
		if (me.isActive() && me.itemActiveId() === 'job') {
			sto = me.gpJob().getStore();
			if (query !== undefined)
				Ext.apply(pars, {query: Ext.JSON.encode(query)});
			WTU.loadWithExtraParams(sto, pars);
		} else {
			me.needsReload = true;
		}
	},
	reloadTicket: function (query) {
		var me = this,
				pars = {},
				sto;
		if (me.isActive() && me.itemActiveId() === 'tckt') {
			sto = me.gpTicket().getStore();
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
	printWorkReportSummary: function(opts) {
		opts = opts || {};

		var me = this,
			vw = WT.createView(me.ID, 'view.WorkReportSummary', {
				swapReturn: true,
				viewCfg: {
						dockableConfig: {
							title: me.res('workReportSummary.tit')
						}
					}
			});
		vw.on('viewok', function(s, operatorId, companyId, from, to, docStatusId) {
			var url = WTF.processBinUrl(me.ID, 'PrintWorkReportSummary', {
				operatorId: operatorId,
				companyId: companyId,
				from: from,
				to: to,
				docStatusId: docStatusId
			});
			Sonicle.URLMgr.openFile(url, {filename: 'workreportsummary', newWindow: true});
		});
		vw.showView();
	},
	addExpenseNote: function (opts) {
		opts = opts || {};

		var me = this,
				fen = me.filtersExpenseNote(),
				vw = WT.createView(me.ID, 'view.ExpenseNote', {swapReturn: true});
		vw.on('viewsave', function (s, success, model) {
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vw.showView(function () {
					vw.begin('new', {
						data: {
							operatorId: fen.getOperatorId()
						}
					});
				});
	},
	editExpenseNoteUI: function (rec) {
		var me = this;
		me.editExpenseNote(rec.get('id'), {
			callback: function (success, model) {
				if (success) {
					this.gpExpenseNote().getStore().load();
				} else {
					alert('error');
				}
			}
		});
	},
	editExpenseNote: function (id, opts) {
		opts = opts || {};
		var me = this,
				vw = WT.createView(me.ID, 'view.ExpenseNote', {swapReturn: true});
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
	deleteExpenseNoteUI: function (rec) {
		var me = this,
				sto = me.gpExpenseNote().getStore(),
				msg;
		if (rec) {
			msg = me.res('act.confirm.delete', Ext.String.ellipsis(rec.get('id'), 40));
		} else {
			msg = me.res('gpWorkReport.confirm.delete.selection');
		}
		WT.confirm(msg, function (bid) {
			if (bid === 'yes') {
				me.deleteExpenseNote(rec.get('id'), {
					callback: function (success) {
						if (success)
							sto.remove(rec);
					}
				});
			}
		});
	},
	deleteExpenseNote: function (id, opts) {
		opts = opts || {};
		var me = this;
		WT.ajaxReq(me.ID, 'ManageExpenseNote', {
			params: {
				crud: 'delete',
				ids: WTU.arrayAsParam(id)
			},
			callback: function (success, json) {
				Ext.callback(opts.callback, opts.scope || me, [success, json]);
			}
		});
	},
	expenseNoteSetting: function (opts) {		
		opts = opts || {};

		var me = this,
				vw = WT.createView(me.ID, 'view.ExpenseNoteSetting', {swapReturn: true});
		vw.on('viewsave', function (s, success, model) { 
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vw.showView(function () {
					vw.begin('edit', {
						data: {
							id: 'expn'
						}
					});
				});
	},
	reloadExpenseNote: function (query) {
		var me = this,
				pars = {},
				sto;
		if (me.isActive() && me.itemActiveId() === 'expn') {
			sto = me.gpExpenseNote().getStore();
			if (query !== undefined)
				Ext.apply(pars, {query: Ext.JSON.encode(query)});
			WTU.loadWithExtraParams(sto, pars);
		} else {
			me.needsReload = true;
		}
	},
	printExpenseNote: function(ids) {
		var me = this, url;
		url = WTF.processBinUrl(me.ID, 'PrintExpenseNote', {ids: WTU.arrayAsParam(ids)});
		Sonicle.URLMgr.openFile(url, {filename: 'expensenote', newWindow: true});
	},
	updateTimetableRequestAction: function (sel){
		var me = this;
		
		if(sel){
			if(sel.get("userId") === WT.getVar('userId')){
				//se sono l'utente loggato ad aver effettuato la richiesta disabilito pulsanti del supervisore
				me.getAct('timetableRequest', 'approve').setDisabled(true);
				me.getAct('timetableRequest', 'decline').setDisabled(true);
				me.getAct('timetableRequest', 'cancel').setDisabled(true);
				if(sel.get('status') === 'D'){
					//e se la richiesta è stata cancellata, disabilito tutti i pulsanti
					me.getAct('timetableRequest', 'edit').setDisabled(true);
					me.getAct('timetableRequest', 'delete').setDisabled(true);
					me.getAct('timetableRequest', 'requestcancellation').setDisabled(true);
				}else if(sel.get('result') === null){
					//e se il supervisore non ha ancora gestito la richiesta, abilito update, delete e disabilito request delete
					me.getAct('timetableRequest', 'edit').setDisabled(false);
					me.getAct('timetableRequest', 'delete').setDisabled(false);
					me.getAct('timetableRequest', 'requestcancellation').setDisabled(true);
				}else if(sel.get('result') !== null){
					//e se il supervisore ha già gestito la richiesta, disabilito update, delete e abilito request delete
					me.getAct('timetableRequest', 'edit').setDisabled(true);
					me.getAct('timetableRequest', 'delete').setDisabled(true);
					me.getAct('timetableRequest', 'requestcancellation').setDisabled(false);
					//Ma se è attiva l'approvazione automatica dei permessi per visita medica, attivo il bottone di cancellazione fisica sulla riga di visita medica e disabilito request delete
					if(me.getVar('medicalVisitsAutomaticallyApproved') && sel.get('type') === 'M'){
						me.getAct('timetableRequest', 'delete').setDisabled(false);
						me.getAct('timetableRequest', 'requestcancellation').setDisabled(true);
					}
				}
			}else if(sel.get("managerId") === WT.getVar('userId')){
				//se sono l'utente loggato ad essere il supervisore
				if(sel.get("userId") !== WT.getVar('userId')){
					//e se non sono su una mia richiesta
					if(sel.get('status') === 'D'){
						//e se la richiesta è stata cancellata, disabilito tutti i pulsanti meno delete
						me.getAct('timetableRequest', 'edit').setDisabled(true);
						me.getAct('timetableRequest', 'delete').setDisabled(false);
						me.getAct('timetableRequest', 'requestcancellation').setDisabled(true);
						me.getAct('timetableRequest', 'approve').setDisabled(true);
						me.getAct('timetableRequest', 'decline').setDisabled(true);
						me.getAct('timetableRequest', 'cancel').setDisabled(true);
					}else if(sel.get('result') === null){
						//e se non ho ancora gestito la richiesta, abilito approve e decline e disabilito tutto il resto
						me.getAct('timetableRequest', 'approve').setDisabled(false);
						me.getAct('timetableRequest', 'decline').setDisabled(false);
						me.getAct('timetableRequest', 'edit').setDisabled(true);
						me.getAct('timetableRequest', 'delete').setDisabled(true);
						me.getAct('timetableRequest', 'requestcancellation').setDisabled(true);
						me.getAct('timetableRequest', 'cancel').setDisabled(true);
					}else if(sel.get('result') !== null){
						//e se ho già gestito la richiesta, disabilito tutto
						me.getAct('timetableRequest', 'edit').setDisabled(true);
						me.getAct('timetableRequest', 'delete').setDisabled(true);
						me.getAct('timetableRequest', 'requestcancellation').setDisabled(true);
						me.getAct('timetableRequest', 'approve').setDisabled(true);
						me.getAct('timetableRequest', 'decline').setDisabled(true);
						me.getAct('timetableRequest', 'cancel').setDisabled(true);
						if(sel.get('employeeCancReq') === true && sel.get('status') === 'C'){
							//ma se lo status della richiesta è chiuso ed è stata richiesta la cancellazione, abilito pulsante cancel
							me.getAct('timetableRequest', 'cancel').setDisabled(false);
						}
					}
				}
			}
		} else {
			me.getAct('timetableRequest', 'edit').setDisabled(true);
			me.getAct('timetableRequest', 'delete').setDisabled(true);
			me.getAct('timetableRequest', 'requestcancellation').setDisabled(true);
			me.getAct('timetableRequest', 'approve').setDisabled(true);
			me.getAct('timetableRequest', 'decline').setDisabled(true);
			me.getAct('timetableRequest', 'cancel').setDisabled(true);
		}
	},
	addTimetableRequest: function (opts) {
		opts = opts || {};

		var me = this,
				ftr = me.filtersTimetableRequest(),
				vw = WT.createView(me.ID, 'view.TimetableRequest', {swapReturn: true});
		vw.on('viewsave', function (s, success, model) {
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vw.showView(function () {
					vw.begin('new', {
						data: {
							userId: ftr.getOperatorId()
						}
					});
				});
	},
	editUserTimetableRequestUI: function (rec) {
		var me = this;
		me.editTimetableRequest(rec.get('leaveRequestId'), rec.get('status'), rec.get('userId'), {
			callback: function (success, model) {
				if (!success) {
					alert('error');
				}
			}
		});
	},
	editTimetableRequestUI: function (rec) {
		var me = this;
		me.editTimetableRequest(rec.get('leaveRequestId'), rec.get('status'), rec.get('userId'), {
			callback: function (success, model) {
				if (success) {
					this.gpTimetableRequest().getStore().load();
				} else {
					alert('error');
				}
			}
		});
	},
	editTimetableRequest: function (leaveRequestId, status, userId, opts) {
		opts = opts || {};
		var mode = (status === 'C' || status === 'D') ? 'view' : (userId !== WT.getVar('userId')) ? 'view' : 'edit';
		var me = this,
				vw = WT.createView(me.ID, 'view.TimetableRequest', {swapReturn: true});
		vw.on('viewsave', function (s, success, model) {
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vw.showView(function () {
					vw.begin(mode, {
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
	approveDeclineTimetableRequestUI: function (rec,choice) {
		var me = this,
				sto = me.gpTimetableRequest().getStore(),
				msg;
		if (rec) {
			msg = (choice === true) ? me.res('timetableRequest.act.approve') : me.res('timetableRequest.act.decline');
		}
		WT.confirm(msg, function (bid) {
			if (bid === 'yes') {
				me.approveDeclineTimetableRequest(rec.get('leaveRequestId'), choice, {
					callback: function (success) {
						if (success) {
							this.gpTimetableRequest().getStore().load();
							me.getAct('timetableRequest', 'approve').setDisabled(true);
							me.getAct('timetableRequest', 'decline').setDisabled(true);
						} else {
							alert('error');
						}
					}
				});
			}
		});
	},
	approveDeclineTimetableRequest: function (leaveRequestId, choice, opts) {
		opts = opts || {};
		var me = this;
		me.getMainComponent().setLoading(me.res('waiting'));
		WT.ajaxReq(me.ID, 'ManageLeaveRequestSupervisorChoice', {
			params: {
				leaveRequestIds: WTU.arrayAsParam(leaveRequestId),
				choice: choice
			},
			callback: function (success, json) {
				me.getMainComponent().setLoading(false);
				Ext.callback(opts.callback, opts.scope || me, [success, json]);
			}
		});
	},
	timetableRequestCancellationUI: function (rec) {
		var me = this;
		me.timetableRequestCancellation(rec.get('leaveRequestId'), {
			callback: function (success, model) {
				if (success) {
					this.gpTimetableRequest().getStore().load();
				} else {
					alert('error');
				}
			}
		});
	},
	timetableRequestCancellation: function (leaveRequestId, opts) {
		opts = opts || {};
		var me = this;
		
		WT.prompt('',{
			title: me.res("timetableRequestCancellation.tit"),
			fn: function(btn, text, cfg) {
				if (btn=='ok') {
					if(Ext.isEmpty(text)){
						Ext.MessageBox.show(Ext.apply({}, {msg: cfg.msg}, cfg));
					}else{
						me.getMainComponent().setLoading(me.res('waiting'));
						WT.ajaxReq(me.ID, 'ManageTimetableRequestCancellation', {
							params: {
								leaveRequestId: leaveRequestId,
								cancellationReason: text
							},
							callback: function (success, json) {
								me.getMainComponent().setLoading(false);
								Ext.callback(opts.callback, opts.scope || me, [success, json]);
							}
						});
					}
				}
			},
			scope: me,
			width: 600,
			multiline: 200,
			value: ''
		});
	},
	cancelTimetableRequestUI: function (rec) {
		var me = this,
				sto = me.gpTimetableRequest().getStore(),
				msg;
		if (rec) {
			msg = me.res('timetableRequest.act.cancel');
		}
		WT.confirm(msg, function (bid) {
			if (bid === 'yes') {
				me.cancelTimetableRequest(rec.get('leaveRequestId'), {
					callback: function (success) {
						if (success) {
							this.gpTimetableRequest().getStore().load();
							me.getAct('timetableRequest', 'cancel').setDisabled(true);
						} else {
							alert('error');
						}
					}
				});
			}
		});
	},
	cancelTimetableRequest: function (leaveRequestId, opts) {
		opts = opts || {};
		var me = this;
		me.getMainComponent().setLoading(me.res('waiting'));
		WT.ajaxReq(me.ID, 'ManageCancellationLeaveRequest', {
			params: {
				leaveRequestIds: WTU.arrayAsParam(leaveRequestId),
				choice: true
			},
			callback: function (success, json) {
				me.getMainComponent().setLoading(false);
				Ext.callback(opts.callback, opts.scope || me, [success, json]);
			}
		});
	},
	timetableSettingGeneral: function (opts) {
		opts = opts || {};

		var me = this,
				vw = WT.createView(me.ID, 'view.TimetableSettingGeneral', {swapReturn: true});
		vw.on('viewsave', function (s, success, model) {
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vw.showView(function () {
            vw.begin('edit', {
                data: {
                    id: 'tmtb'
                }
            });
        });
	},
    timetableSettingHolidays: function (opts) {
		opts = opts || {};

		var me = this,
            vw = WT.createView(me.ID, 'view.TimetableSettingHolidays', {swapReturn: true});
		vw.showView();
	},
    timetableSettingEmployeeProfiles: function (opts) {
		opts = opts || {};

		var me = this,
            vw = WT.createView(me.ID, 'view.TimetableSettingEmployeeProfiles', {swapReturn: true});
		vw.showView();
	},
    timetableSettingHourProfiles: function (opts) {
		opts = opts || {};

		var me = this,
            vw = WT.createView(me.ID, 'view.TimetableSettingHourProfiles', {swapReturn: true});
		vw.showView();
	},
	timetableSettingCausals: function (opts) {
		opts = opts || {};

		var me = this,
            vw = WT.createView(me.ID, 'view.TimetableSettingCausals', {swapReturn: true});
		vw.showView();
	},
    
	timetableSettingGis: function (opts) {
		opts = opts || {};

		var me = this,
            vw = WT.createView(me.ID, 'view.TimetableSettingGis', {swapReturn: true});
		vw.on('viewsave', function (s, success, model) {
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vw.showView(function () {
            vw.begin('edit', {
                data: {
                    id: 'tmtbgis'
                }
            });
        });
	},
	
	reloadTimetableRequest: function (query) {
		var me = this,
				pars = {},
				sto;
		if (me.isActive() && me.itemActiveId() === 'tmtb.rqst') {
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
		if (me.isActive() && me.itemActiveId() === 'tmtb.rprt') {
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
						me.getAct('timetableStamp', 'delete').setDisabled(false);
					}else {
						me.getAct('timetableStamp', 'add').setDisabled(true);
						me.getAct('timetableStamp', 'delete').setDisabled(true);
					}
				}
			}
		});
	},
	printTimetableReport: function() {
		var me = this, url;
		url = WTF.processBinUrl(me.ID, 'PrintTimetableReport', {query: Ext.JSON.encode(me.timetableReportGenerateQuery)});
		Sonicle.URLMgr.openFile(url, {filename: 'timetablereport', newWindow: true});
	},
	reloadTimetableStamp: function (query) {
		var me = this,
				pars = {},
				sto;
		if (me.isActive() && me.itemActiveId() === 'tmtb.stmp') {
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
				vw = WT.createView(me.ID, 'view.TimetableStamp', {swapReturn: true});
		vw.on('viewsave', function (s, success, model) {
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vw.showView(function () {
            vw.begin('new', {
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
	
	setTimetable: function (location){
		var me = this;
		
		WT.ajaxReq(me.ID, 'SetTimetable', {
			params: {
				type: 'M',
				location: location
			},
			callback: function (success, json) {
				if(success) me.getMainComponent().lookupReference('gpTimetable').getStore().reload();
			}
		});
	},
	
	configurationView: function () {
		var me = this,
				vw = WT.createView(me.ID, 'view.Configurations', {swapReturn: true});
		vw.showView();
	},
	
	onActivateTab: function (id) {
		var me = this;
		
		switch (id){
			case 'oppo':
				me.reloadOpportunity(me.filtersOpportunity().getData());
				break;
			case 'wrkr':
				me.reloadWorkReport(me.filtersWorkReport().getData());
				break;
			case 'expn':
				me.reloadExpenseNote(me.filtersExpenseNote().getData());
				break;
			case 'tmtb':
				break;
			case 'tmtb.stmp':
				me.enablingStampButtons();
				me.enablingManageStampsButtons();
				me.getMainComponent().lookupReference('gpTimetable').getStore().reload();
				me.reloadTimetableStamp(me.filtersTimetableStamp().getData());
				break;
			case 'tmtb.rqst':
				me.reloadTimetableRequest(me.filtersTimetableRequest().getData());
				break;
			case 'tmtb.rprt':
				me.reloadTimetableReport(null);
				break;
			case 'job':
				me.reloadJob(me.filtersJob().getData());
				break;
			case 'tckt':				
				me.reloadTicket(me.filtersTicket().getData());
				break;			
		}
	}
});
