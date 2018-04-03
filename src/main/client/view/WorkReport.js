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
Ext.define('Sonicle.webtop.drm.view.WorkReport', {
	extend: 'WTA.sdk.ModelView',
	requires: [
		'Sonicle.webtop.drm.model.WorkReport',
		'WTA.ux.data.SimpleSourceModel',
		'Sonicle.Bytes',
		'WTA.ux.UploadBar'
	],
	dockableConfig: {
		title: '{workReport.tit}',
		iconCls: 'wtdrm-icon-workreport-xs',
		width: 910,
		height: 500
	},
	fieldTitle: 'workReportNo',
	modelName: 'Sonicle.webtop.drm.model.WorkReport',
	
	initComponent: function () {
		var me = this,
			gpId = Ext.id(null, 'gridpanel');
	
		me.lookupStore = Ext.create('Ext.data.Store', {
			autoLoad: true,
			model: 'WTA.model.Simple',
			proxy: WTF.proxy(me.mys.ID, 'LookupWorkType')
		});

		Ext.apply(me, {
			tbar: [
				me.addAct('printWorkReport', {
					text: null,
					tooltip: WT.res('act-print.lbl'),
					iconCls: 'wt-icon-print-xs',
					handler: function() {
						me.printWorkReport(me.getModel().getId());
					}
				}),
				'->',
				{
					xtype: 'wtform',
					reference: 'toolbar',
					modelValidation: true,
					items: [
						WTF.localCombo('id', 'desc', {
							reference: 'flduser',
							bind: '{record.operatorId}',
							anyMatch: true,
							selectOnFocus: true,
							store: {
								autoLoad: true,
								model: 'WTA.model.Simple',
								proxy: WTF.proxy(me.mys.ID, 'LookupOperators'),
								listeners: {
									load: function (s) {
										if (me.isMode('new')) {
											var meta = s.getProxy().getReader().metaData;
											if (meta.selected) {
												me.lookupReference('flduser').setValue(meta.selected);
											}
											if (s.loadCount === 1) {
												me.lref('fldcompany').getStore().load();
												me.lref('fldmasterdata').getStore().load();
												me.lref('fldcausal').getStore().load();
											}
										}
									}
								}
							},
							listeners: {
								select: function (s, r) {
									me.lref('fldcompany').getStore().load();
									me.lref('fldmasterdata').getStore().load();
									me.lref('fldstatmasterdata').getStore().load();
									me.lref('fldcausal').getStore().load();
								}
							},
							fieldLabel: me.mys.res('workReport.fld-nominative.lbl')
						})
					]
				}
			]
		});

		me.callParent(arguments);
		me.initActions();
		me.initCxm();

		me.add({
			region: 'center',
			xtype: 'wttabpanel',
			items: [
				{
					xtype: 'container',
					layout: {
						type: 'vbox', align: 'stretch'
					},
					title: me.mys.res('workReport.report.tit'),
					items: [
						{
							xtype: 'container',
							layout: 'column',
							items: [
								{
									xtype: 'wtform',
									reference: 'column1',
									modelValidation: true,
									defaults: {
										labelWidth: 120
									},
									items: [
										WTF.localCombo('id', 'desc', {
											reference: 'fldcompany',
											bind: '{record.companyId}',
											autoLoadOnValue: true,
											tabIndex: 101,
											selectOnFocus: true,
											store: {
												model: 'WTA.model.Simple',
												proxy: WTF.proxy(me.mys.ID, 'LookupCompanies', null, {
													extraParams: {
														operator: null
													}
												}),
												listeners: {
													beforeload: function(s,op) {
														WTU.applyExtraParams(op.getProxy(), {operator: me.getModel().get('operatorId')});
													},
													load: function (s) {
														if (me.isMode('new')) {
															var meta = s.getProxy().getReader().metaData;
															if (meta.selected) {
																me.lookupReference('fldcompany').setValue(meta.selected);
															}
														}
													}
												}
											},
											fieldLabel: me.mys.res('workReport.fld-company.lbl'),
											width: '420px'
										}),
										WTF.localCombo('id', 'desc', {
											xtype: 'sosourcecombo',
											bind: '{record.contactId}',
											anyMatch: true,
											tabIndex: 102,
											selectOnFocus: true,
											store: {
												autoLoad: true,
												model: 'WTA.ux.data.SimpleSourceModel',
												proxy: WTF.proxy(me.mys.ID, 'LookupContacts')
											},
											sourceField: 'src',
											fieldLabel: me.mys.res('workReport.fld-contact.lbl'),
											width: '420px'
										}),
										WTF.localCombo('id', 'desc', {
											reference: 'fldmasterdata',
											bind: '{record.customerId}',
											autoLoadOnValue: true,
											tabIndex: 103,
											selectOnFocus: true,
											store: {
												model: 'WTA.model.Simple',
												proxy: WTF.proxy(me.mys.ID, 'LookupRealCustomers', null, {
													extraParams: {
														operator: null
													}
												}),
												listeners: {
													beforeload: function(s,op) {
														WTU.applyExtraParams(op.getProxy(), {operator: me.getModel().get('operatorId')});
													},
													load: function (s) {
														/*
														if (s.loadCount === 1) {
															if (!Ext.isEmpty(me.getModel().get('customerId'))) {
																me.lookupReference('fldmasterdata').setValue(me.getModel().get('customerId'));

																WTU.loadWithExtraParams(me.lref('fldstatmasterdata').getStore(), {
																	realCustomer: me.getModel().get('customerId')
																});
																WTU.loadWithExtraParams(me.lref('fldcausal').getStore(), {
																	masterDataId: me.getModel().get('customerId')
																});
															}
														}
														*/
													}
												}
											},
											fieldLabel: me.mys.res('workReport.fld-realCustomer.lbl'),
											width: '420px',
											listeners: {
												select: function (s, r) {
													me.getViewModel().set('customerStatId', null);
													WTU.loadWithExtraParams(me.lref('fldstatmasterdata').getStore(), {
														realCustomer: r.id
													});
													WTU.loadWithExtraParams(me.lref('fldcausal').getStore(), {
														masterDataId: r.id
													});
												}
											}
										}), {
											xtype: 'datefield',
											reference: 'fldfromdate',
											bind: '{record.fromDate}',
											format: WT.getShortDateFmt(),
											tabIndex: 105,
											selectOnFocus: true,
											listeners: {
												select: function (s, v) {
													if (me.lref('fldtodate').getValue() === null || v > me.lref('fldtodate').getValue()) {
														me.lref('fldtodate').setValue(v);
													}
													var days = Sonicle.Date.diffDays(me.lref('fldfromdate').getValue(),
															me.lref('fldtodate').getValue());
													me.lref('flddaytrasfert').setValue(days);
												}
													},
											fieldLabel: me.mys.res('workReport.fld-fromDate.lbl'),
											width: '420px'
										}, {
											xtype: 'textfield',
											bind: '{record.referenceNo}',
											fieldLabel: me.mys.res('workReport.fld-reference.lbl'),
											width: '420px',
											tabIndex: 107,
											selectOnFocus: true
										}, {
											xtype: 'textfield',
											bind: '{record.ddtNo}',
											fieldLabel: me.mys.res('workReport.fld-ddt.lbl'),
											width: '420px',
											tabIndex: 109,
											selectOnFocus: true
										}
									]
								}, {
									xtype: 'wtform',
									reference: 'column2',
									modelValidation: true,
									defaults: {
										labelWidth: 120
									},
									items: [{
											xtype: 'soplaceholderfield'
										}, {
											xtype: 'soplaceholderfield'
										},
										WTF.localCombo('id', 'desc', {
											reference: 'fldstatmasterdata',
											bind: '{record.customerStatId}',
											autoLoadOnValue: true,
											tabIndex: 104,
											selectOnFocus: true,
											store: {
												model: 'WTA.model.Simple',
												proxy: WTF.proxy(me.mys.ID, 'LookupStatisticCustomers', null, {
													extraParams: {
														operator: null,
														realCustomer: null
													}
												}),
												listeners: {
													load: function (s) {
														if (me.isMode('new')) {
															var meta = s.getProxy().getReader().metaData;
															if(meta){
																if (meta.selected) {
																	me.lookupReference('fldstatmasterdata').setValue(meta.selected);
																}
															}
														}
													},
													beforeload: function(s,op) {
														WTU.applyExtraParams(op.getProxy(), {
															operator: me.getModel().get('operatorId'),
															realCustomer: me.getModel().get('customerId')
														});
													}
												}
											},
											fieldLabel: me.mys.res('workReport.fld-customerStat.lbl'),
											width: '420px'
										}),
										{
											xtype: 'datefield',
											reference: 'fldtodate',
											bind: '{record.toDate}',
											tabIndex: 106,
											selectOnFocus: true,
											listeners: {
												select: function (s, rec) {
													var days = Sonicle.Date.diffDays(me.lref('fldfromdate').getValue(),
															me.lref('fldtodate').getValue());
													me.lref('flddaytrasfert').setValue(days);
												}
											},
											format: WT.getShortDateFmt(),
											fieldLabel: me.mys.res('workReport.fld-toDate.lbl'),
											width: '420px'
										},
										WTF.localCombo('id', 'desc', {
											reference: 'fldcausal',
											bind: '{record.causalId}',
											autoLoadOnValue: true,
											tabIndex: 108,
											selectOnFocus: true,
											store: {
												model: 'WTA.model.CausalLkp',
												proxy: WTF.proxy(WT.ID, 'LookupCausals', null, {
													extraParams: {
														profileId: null,
														masterDataId: null
													}
												}),
												filters: [{
													filterFn: function (rec) {
														if (rec.get('readOnly')) {
															if (rec.getId() !== me.lref('fldcausal').getValue()) {
																return null;
															}
														}
														return rec;
													}
												}],
												listeners: {
													beforeload: function(s,op) {
														WTU.applyExtraParams(op.getProxy(), {
															profileId: WT.toPid(WT.getVar('domainId'), me.getModel().get('operatorId')),
															masterDataId: me.getModel().get('customerId')
														});
													}
												}
											},
											triggers: {
												clear: WTF.clearTrigger()
											},
											fieldLabel: me.mys.res('workReport.fld-confirmation.lbl'),
											width: '420px'
										}),
										{
											xtype: 'datefield',
											bind: '{record.ddtDate}',
											format: WT.getShortDateFmt(),
											fieldLabel: me.mys.res('workReport.fld-ddtToDate.lbl'),
											width: '420px',
											tabIndex: 110,
											selectOnFocus: true
										}
									]
								}
							]
						},
						{
							xtype: 'wtform',
							reference: 'otherfields',
							modelValidation: true,
							items: [{
									xtype: 'textareafield',
									bind: '{record.notes}',
									fieldLabel: me.mys.res('workReport.fld-notes.lbl'),
									labelAlign: 'top',
									anchor: '100%'
								}, {
									xtype: 'container',
									layout: 'hbox',
									defaults: {
										margin: '0 10 0 0'
									},
									items: [
										{
											xtype: 'checkbox',
											reference: 'fldapplySignature',
											bind: '{record.applySignature}',
											boxLabel: me.mys.res('workReport.fld-signature.lbl'),
											boxLabelAlign: 'before'
										},
										{
											xtype: 'checkbox',
											reference: 'fldchargeTo',
											bind: '{record.chargeTo}',
											boxLabel: me.mys.res('workReport.fld-chargeTo.lbl'),
											boxLabelAlign: 'before'
										},
										{
											xtype: 'checkbox',
											reference: 'fldfreeSupport',
											bind: '{record.freeSupport}',
											boxLabel: me.mys.res('workReport.fld-assistance.lbl'),
											boxLabelAlign: 'before'
										}
									]
								},
								WTF.localCombo('id', 'desc', {
									reference: 'flddoctatus',
									bind: '{record.docStatusId}',
									store: {
										autoLoad: true,
										model: 'WTA.model.Simple',
										proxy: WTF.proxy(me.mys.ID, 'LookupDocStatuses')
									},
									fieldLabel: me.mys.res('workReport.fld-status.lbl')
								})
							]
						}
					]
				}, {
					title: me.mys.res('workReport.rows.tit'),
					xtype: 'container',
					layout: 'border',
					flex: 1,
					width: '100%',
					items: [
						{
							region: 'center',
							xtype: 'container',
							refernceHolder: true,
							layout: {
								type: 'vbox',
								align: 'stretch'
							},
							items: [
								{
									xtype: 'grid',
									reference: 'gpReportRows',
									modelValidation: true,
									iconCls: '',
									flex: 1,
									border: true,
									bind: {
										store: '{record.details}'
									},
									features: [
										{
											ftype: 'summary'
										}
									],
									columns: [
										{
											dataIndex: 'rowNo',
											width: '25',
											header: me.mys.res('gpReportRows.rif.lbl')
										}, {
											xtype: 'solookupcolumn',
											dataIndex: 'workTypeId',
											store: me.lookupStore,
											editor: Ext.create(WTF.lookupCombo('id', 'desc', {
												allowBlank: false,
												store: me.lookupStore
											})),
											displayField: 'desc',
											header: me.mys.res('gpReportRows.workType.lbl'),
											flex: 2
										}, {
											dataIndex: 'duration',
											xtype: 'numbercolumn',
											flex: 1,
											format: '0.0',
											editor: {
												xtype: 'numberfield',
												allowBlank: false,
												minValue: 0,
												allowDecimals: true,
												decimalSeparator: '.',
												decimalPrecision: 1,
												step: 0.5
											},
											summaryType: 'sum',
											summaryRenderer: function (value, summaryData, dataIndex) {
												return Ext.String.format(me.mys.res('gpReportRows.totalHours.lbl') + ' {0}', value);
											},
											header: me.mys.res('gpReportRows.duration.lbl')
										}, {
											dataIndex: 'specialFlag',
											xtype: 'checkcolumn',
											width: '25',
											editor: {
												xtype: 'checkbox',
												matchFieldWidth: true
											},
											header: me.mys.res('gpReportRows.rowFlag.lbl')
										}
									],
									tbar: [
										me.addAct('add', {
											text: WT.res('act-add.lbl'),
											tooltip: null,
											iconCls: 'wt-icon-add-xs',
											handler: function () {
												me.addDetail();
											},
											scope: me
										}),
										me.addAct('delete', {
											text: WT.res('act-delete.lbl'),
											tooltip: null,
											iconCls: 'wt-icon-delete-xs',
											handler: function () {
												var sm = me.lref('gpReportRows').getSelectionModel();
												me.deleteDetail(sm.getSelection());
											},
											scope: me
										})
									],
									plugins: {
										ptype: 'cellediting',
										clicksToEdit: 1
									}
								}, {
									xtype: 'wtform',
									items: [
										{
											xtype: 'numberfield',
											reference: 'flddaytrasfert',
											bind: '{record.dayTrasfert}',
											minValue: 0,
											fieldLabel: me.mys.res('gpReportRows.hourTravel.lbl')
										},
										WTF.remoteCombo('id', 'desc', {
											reference: 'fldbusinesstrip',
											bind: '{record.businessTripId}',
											store: {
												autoLoad: true,
												model: 'WTA.model.Simple',
												proxy: WTF.proxy(me.mys.ID, 'LookupBusinessTrip')
											},
											/*listeners: {
											 select: function (s, rec) {
											 var soDate = Sonicle.Date;
											 var days = soDate.diffDays(me.lref('fldfromdate').getValue(),
											 me.lref('fldtodate').getValue());
											 me.lref('flddaytrasfert').setValue(days);
											 }
											 },*/
											triggers: {
												clear: WTF.clearTrigger()
											},
											fieldLabel: me.mys.res('gpReportRows.trasferts.lbl')})
									]
								}
							]
						}, {
							region: 'east',
							xtype: 'panel',
							layout: 'fit',
							title: me.mys.res('workReport.details.fld-description.lbl'),
							flex: 1,
							split: true,
							items: [
								{
									xtype: 'textareafield',
									reference: 'description',
									bind: '{record.description}',
									maxLength: 2048,
									enableKeyEvents: true
								}
							]
						}
					]
				}, {
					title: me.mys.res('workReport.attachment.tit'),
					xtype: 'grid',
					id: gpId,
					reference: 'gpAttachment',
					bind: {
						store: '{record.attachments}'
					},
					selModel: {
						type: 'checkboxmodel',
						mode: 'MULTI'
					},
					columns: [
						{
							xtype: 'solinkcolumn',
							dataIndex: 'fileName',
							header: me.mys.res('gpWorkReportAttachment.filename.lbl'),
							flex: 3,
							listeners: {
								linkclick: function (s, idx, rec) {
									me.downloadAttachment([rec.getId()]);
								}
							}
						}, {
							xtype: 'sobytescolumn',
							dataIndex: 'size',
							header: me.mys.res('gpWorkReportAttachment.size.lbl'),
							flex: 1
						}
					],
					tbar: [
						me.getAct('downloadAttachment'),
						me.getAct('openAttachment'),
						me.getAct('deleteAttachment')
					],
					bbar: {
						xtype: 'wtuploadbar',
						sid: me.mys.ID,
						uploadContext: 'UploadWorkReportAttachment',
						dropElement: gpId,
						listeners: {
							fileuploaded: function (s, file, json) {
								me.addDocument(json.data.uploadId, file);
							}
						}
					},
					listeners: {
						rowcontextmenu: function (s, rec, itm, i, e) {
							var sm = s.getSelectionModel();
							sm.select(rec);
							WT.showContextMenu(e, me.getRef('cxmGridDocument'), {
								file: rec
							});
						},
						select: function (s, rec, i, e) {
							me.updateDisabled('downloadAttachment');
							me.updateDisabled('openAttachment');
							me.updateDisabled('deleteAttachment');
						}
					},
					plugins: [{
						ptype: 'sofiledrop',
						text: WT.res('sofiledrop.text')
					}]
				}
			]
		});

		me.on('viewinvalid', me.onViewInvalid);
		me.on('viewload', me.onViewLoad);

	},
	gpAttachment: function () {
		this.lref('gpAttachment');
	},
	getSelectedFiles: function () {
		return this.lref('gpAttachment').getSelection();
	},
	selectionIds: function (sel) {
		var ids = [];
		Ext.iterate(sel, function (rec) {
			ids.push(rec.getId());
		});
		return ids;
	},
	addDetail: function () {
		var me = this;
		var gp = me.lref('gpReportRows'),
				sto = gp.getStore(),
				cep = gp.findPlugin('cellediting');

		cep.cancelEdit();

		sto.add(sto.createModel({
			rowNo: sto.getCount() + 1,
			duration: 1,
			specialFlag: false
		}));

		cep.startEditByPosition({row: sto.getCount(), column: 1});
	},
	deleteDetail: function (rec) {
		var me = this,
				grid = me.lref('gpReportRows'),
				sto = grid.getStore(),
				cep = grid.findPlugin('cellediting');

		WT.confirm(WT.res('confirm.delete'), function (bid) {
			if (bid === 'yes') {
				cep.cancelEdit();
				sto.remove(rec);
				me.updateRowNo(sto);
			}
		}, me);
	},
	addDocument: function (uploadId, file) {
		var me = this;
		var gp = me.lref('gpAttachment'),
				sto = gp.getStore();

		sto.add(sto.createModel({
			id: uploadId,
			fileName: file.name,
			size: file.size,
			mediaType: file.type
		}));
	},
	deleteDocument: function (rec) {
		var me = this,
				grid = me.lref('gpAttachment'),
				sto = grid.getStore();

		WT.confirm(WT.res('confirm.delete'), function (bid) {
			if (bid === 'yes') {
				sto.remove(rec);
			}
		}, me);
	},
	openAttachments: function (attachmentIds) {
		Sonicle.URLMgr.openFile(WTF.processBinUrl(this.mys.ID, 'DownloadWorkReportAttachment', {
			attachmentIds: WTU.arrayAsParam(attachmentIds)
		}));
	},
	downloadAttachment: function (attachmentIds) {
		Sonicle.URLMgr.openFile(WTF.processBinUrl(this.mys.ID, 'DownloadWorkReportAttachment', {
			raw: 1,
			attachmentIds: WTU.arrayAsParam(attachmentIds)
		}));
	},
	printWorkReport: function(contactId) {
		var me = this;
		if(me.getModel().isDirty()) {
			WT.warn(WT.res('warn.print.notsaved'));
		} else {
			me.mys.printWorkReport([contactId]);
		}
	},
	
	onViewLoad: function(s, success) {
		if(!success) return;
		var me = this,
				mo = me.getModel();
	
		if(me.isMode(me.MODE_NEW)) {
			mo.set('applySignature', me.mys.getVar('defaultApplySignature'));
			mo.set('chargeTo', me.mys.getVar('defaultChargeTo'));
			mo.set('freeSupport', me.mys.getVar('defaultFreeSupport'));
			mo.set('docStatusId', me.mys.getVar('defaultStatus'));
		}
	},
	
	initActions: function () {
		var me = this;
		me.addAct('openAttachment', {
			tooltip: null,
			iconCls: 'wtdrm-icon-openAttachment-xs',
			disabled: true,
			handler: function () {
				var sel = me.getSelectedFiles();
				if (sel.length > 0) {
					ids = me.selectionIds(sel);
					me.openAttachments(ids);
				}
			}
		});
		me.addAct('downloadAttachment', {
			tooltip: null,
			iconCls: 'wtdrm-icon-downloadAttachment-xs',
			disabled: true,
			handler: function () {
				var sel = me.getSelectedFiles();

				if (sel.length > 0) {
					ids = me.selectionIds(sel);
					me.downloadAttachment(ids);
				}
			}
		});
		me.addAct('deleteAttachment', {
			tooltip: null,
			iconCls: 'wtdrm-icon-deleteAttachment-xs',
			disabled: true,
			handler: function () {
				var sel = me.getSelectedFiles();
				
				if (sel.length > 0) {
					me.deleteDocument(sel);
				}
			}
		});
	},
	initCxm: function () {
		var me = this;
		me.addRef('cxmGridDocument', Ext.create({
			xtype: 'menu',
			items: [
				me.getAct('openAttachment'),
				me.getAct('downloadAttachment'),
				'-',
				me.getAct('deleteAttachment')
			],
			listeners: {
				beforeshow: function (s) {
					me.updateDisabled('openAttachment');
					me.updateDisabled('downloadAttachment');
					me.updateDisabled('deleteAttachment');
				}
			}
		}));
	},
	/**
	 * @private
	 */
	updateDisabled: function (action) {
		var me = this,
				dis = me.isDisabled(action);
		me.setActDisabled(action, dis);
	},
	/**
	 * @private
	 */
	isDisabled: function (action) {
		var me = this, sel;
		switch (action) {
			case 'openAttachment':
				sel = me.getSelectedFiles();
				return false;
			case 'downloadAttachment':
				sel = me.getSelectedFiles();
				return false;
			case 'deleteAttachment':
				sel = me.getSelectedFiles();
				return false;
		}
	},
	onViewInvalid: function (s, mo, errs) {
		var me = this;
		WTU.updateFieldsErrors(me.lref('toolbar'), errs);
		WTU.updateFieldsErrors(me.lref('column1'), errs);
		WTU.updateFieldsErrors(me.lref('column2'), errs);
		WTU.updateFieldsErrors(me.lref('otherfields'), errs);
		WTU.updateFieldsErrors(me.lref('gpReportRows'), errs);
	},
	updateRowNo: function (sto) {
		for (var i = 0; i <= sto.getCount(); i++) {
			sto.data.items[i].set('rowNo', i + 1);
		}
	}
});
