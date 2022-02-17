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
Ext.define('Sonicle.webtop.drm.view.ExpenseNote', {
	extend: 'WTA.sdk.ModelView',
	requires: [
		'Sonicle.webtop.drm.model.ExpenseNote',
		'WTA.ux.grid.Attachments',
		'WTA.ux.data.SimpleSourceModel',
		'Sonicle.Bytes',
		'WTA.ux.UploadBar',
		'WTA.ux.field.SuggestCombo'
	],
	dockableConfig: {
		title: '{expenseNote.tit}',
		iconCls: 'wtdrm-icon-expenseNote',
		width: 910,
		height: 500
	},
	fieldTitle: 'expenseNoteId',
	modelName: 'Sonicle.webtop.drm.model.ExpenseNote',
	initComponent: function () {
		var me = this,
			gpId = Ext.id(null, 'gridpanel');
		
		Ext.apply(me, {
			tbar: [
				me.addAct('printExpenseNote', {
					text: null,
					tooltip: WT.res('act-print.lbl'),
					iconCls: 'wt-icon-print',
					handler: function() {
						me.printExpenseNote(me.getModel().getId());
					}
				}),
				me.addAct('sendMail', {
					text: me.res('act-sendMail.lbl'),
					tooltip: null,
					iconCls: 'wtdrm-icon-mailto',
					handler: function () {
						me.sendMail(me.getModel().getId());
					}
				}),
				'->',
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
									if (s.loadCount === 1) {
										me.lref('fldcompany').getStore().load();
									}
								}
							}
						}
					},
					listeners: {
						select: function (s, r) {
							me.lref('fldcompany').getStore().load();
						}
					},
					fieldLabel: me.mys.res('expenseNote.fld-operator.lbl'),
					allowBlank: false,
					readOnly: true
				})
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
					title: me.mys.res('expenseNote.tab.expenseNotetit'),
					xtype: 'panel',
					layout: {
						type: 'vbox',
						align: 'stretch'
					},
					defaults: {
						margin: '0 0 10 0'
					},
					items: [
						{
							xtype: 'container',
							layout: 'column',
							flex: 1,
							items: [
								{
									xtype: 'wtform',
									reference: 'mainForm1',
									modelValidation: true,
									items: [
										WTF.localCombo('id', 'desc', {
											reference: 'fldcompany',
											bind: '{record.companyId}',
											autoLoadOnValue: true,
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
											fieldLabel: me.mys.res('expenseNote.fld-company.lbl'),
											width: '420px',
											tabIndex: 501
										}),
										{
											xtype: 'datefield',
											startDay: WT.getStartDay(),
											format: WT.getShortDateFmt(),
											reference: 'fldfromdate',
											bind: '{record.fromDate}',
											fieldLabel: me.mys.res('expenseNote.fld-fromDate.lbl'),
											width: '420px',
											selectOnFocus: true,
											tabIndex: 503
										},
										{
											xtype: 'fieldcontainer',
											layout: 'hbox',
											defaults: {
												margin: '0 5 0 0'
											},
											items: [
												{
													xtype: 'numberfield',
													reference: 'fldtotcurrency',
													bind: '{record.totCurrency}',
													fieldLabel: me.mys.res('expenseNote.fld-totCurrency.lbl'),
													readOnly: true,
													width: '285px',
													selectOnFocus: true,
													tabIndex: 505
												},
												WTF.localCombo('id', 'desc', {
													reference: 'fldcurrency',
													bind: '{record.currency}',
													readOnly: true,
													store: Ext.create('Sonicle.webtop.drm.store.Currency', {
														autoLoad: true
													}),
													width: '130px',
													tabIndex: 506
												})
											]
										}
									]
								},
								{
									xtype: 'wtform',
									reference: 'mainForm2',
									modelValidation: true,
									items: [
										{
											xtype: 'wtsuggestcombo',
											reference: 'flddescription',
											bind: '{record.description}',
											sid: me.mys.ID,
											suggestionContext: 'expenseNoteDescription',
											fieldLabel: me.mys.res('expenseNote.fld-description.lbl'),
											width: '420px',
											selectOnFocus: true,
											tabIndex: 502
										},
										{
											xtype: 'datefield',
											startDay: WT.getStartDay(),
											reference: 'fldtodate',
											bind: '{record.toDate}',
											fieldLabel: me.mys.res('expenseNote.fld-toDate.lbl'),
											width: '420px',
											selectOnFocus: true,
											tabIndex: 504
										},
										WTF.lookupCombo('id', 'desc', {
											reference: 'fldstatus',
											bind: '{record.status}',
											store: Ext.create('Sonicle.webtop.drm.store.StatusType', {
												autoLoad: true
											}),
											fieldLabel: me.mys.res('expenseNote.fld-status.lbl'),
											width: '420px',
											tabIndex: 507
										})
									]
								}
							]
						},
						{
							xtype: 'grid',
							reference: 'gpExpenseNoteDetail',
							modelValidation: true,
							title: me.mys.res('expenseNote.tab.detail.tit'),
							bind: {
								store: '{record.details}'
							},
							iconCls: '',
							border: true,
							flex: 2,
							columns: [
								{
									dataIndex: 'date', 
									xtype: 'datecolumn',
									format: 'd/m/Y',
									header: me.mys.res('gpExpenseNoteDetail.date.lbl'), 
									flex: 1
								},
								{
									dataIndex: 'description', 
									header: me.mys.res('gpExpenseNoteDetail.description.lbl'),
									flex: 2
								},
								{
									xtype: 'solookupcolumn',
									dataIndex: 'customerId',
									store: {
										autoLoad: true,
										model: 'WTA.model.Simple',
										proxy: WTF.proxy(WT.ID, 'LookupCustomersSuppliers')
									},
									header: me.mys.res('gpExpenseNoteDetail.customer.lbl'), 
									displayField: 'desc',
									flex: 2
								},
								{
									xtype: 'solookupcolumn',
									dataIndex: 'typeId', 
									store: {
										autoLoad: true,
										model: 'WTA.model.Simple',
										proxy:  WTF.proxy(me.mys.ID, 'LookupCostTypes')
									},
									header: me.mys.res('gpExpenseNoteDetail.typeofcost.lbl'), 
									displayField: 'desc',
									flex: 2
								},
								{
									dataIndex: 'withOthers', 
									header: me.mys.res('gpExpenseNoteDetail.otherspresent.lbl'), 
									flex: 1
								},
								{
									dataIndex: 'total', 
									header: me.mys.res('gpExpenseNoteDetail.total.lbl'), 
									flex: 1
								},
								{
									dataIndex: 'currency', 
									header: me.mys.res('gpExpenseNoteDetail.currency.lbl'),
									flex: 1
								},
								{
									dataIndex: 'totalDoc', 
									header: me.mys.res('gpExpenseNoteDetail.documenttotal.lbl'), 
									flex: 1
								},
								{
									xtype: 'checkcolumn',
									dataIndex: 'paymentCompany', 
									header: me.mys.res('gpExpenseNoteDetail.paymentcompany.lbl'), 
									flex: 1
								},
								{
									xtype: 'checkcolumn',
									dataIndex: 'invoice',
									header: me.mys.res('gpExpenseNoteDetail.invoice.lbl'),
									flex: 1
								},
								{
									dataIndex: 'invoiceNumber', 
									header: me.mys.res('gpExpenseNoteDetail.invoicenumber.lbl'),
									flex: 1
								}
							],
							tbar: [
								me.getAct('expenseNoteDetail', 'add'),
								me.getAct('expenseNoteDetail', 'delete'),
								'-',
								me.getAct('expenseNoteDetail', 'edit')
							],
							listeners: {
								rowclick: function (s, rec) {
									me.getAct('expenseNoteDetail', 'edit').setDisabled(false);
									me.getAct('expenseNoteDetail', 'delete').setDisabled(false);
								},
								rowdblclick: function (s, rec) {
									me.editExpenseNoteDetailUI(rec);
								}
							}
						}
					]
				},
				{
					xtype: 'wtattachmentsgrid',
					title: me.mys.res('expenseNote.tab.documents.tit'),
					bind: {
						store: '{record.documents}'
					},
					sid: me.mys.ID,
					uploadContext: 'ExpenseNoteDocument',
					uploadTag: WT.uiid(me.getId()),
					dropElementId: me.getId(),
					typeField: 'ext',
					listeners: {
						attachmentlinkclick: function(s, rec) {
							me.openAttachmentUI(rec, false);
						},
						attachmentdownloadclick: function(s, rec) {
							me.openAttachmentUI(rec, true);
						},
						attachmentdeleteclick: function(s, rec) {
							s.getStore().remove(rec);
						},
						attachmentuploaded: function(s, uploadId, file) {
							var sto = s.getStore();
							sto.add(sto.createModel({
								name: file.name,
								size: file.size,
								_uplId: uploadId
							}));
							me.getComponent(0).getLayout().setActiveItem(s);
						}
					}
				}
			]
		});
		
		me.on('viewinvalid', me.onViewInvalid);
		me.on('viewload', me.onViewLoad);
		me.on('viewclose', me.onViewClose);
	},
	
	initActions: function () {
		var me = this;
		
		me.addAct('expenseNoteDetail', 'add', {
			text: WT.res('act-add.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-add',
			handler: function () {
				me.addExpenseNoteDetail(
					me.getModel().get('operatorId'), me.getModel().get('companyId'), me.getModel().get('fromDate'), me.getModel().get('toDate'), {
						callback: function(){
						}
				});
			}
		});
		me.addAct('expenseNoteDetail', 'delete', {
			text: WT.res('act-delete.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-delete',
			disabled: true,
			handler: function () {
				var sm = me.lref('gpExpenseNoteDetail').getSelectionModel();
				me.deleteExpenseNoteDetail(sm.getSelection());
			}
		});
		me.addAct('expenseNoteDetail', 'edit', {
			text: WT.res('act-edit.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-edit',
			disabled: true,
			handler: function () {
				var sm = me.lref('gpExpenseNoteDetail').getSelectionModel();
				me.editExpenseNoteDetailUI(
						sm.getSelection(), {
				});
			}
		});		
	},
	
	initCxm: function () {
		var me = this;
	},
	
	printExpenseNote: function(id) {
		var me = this;
		if(me.getModel().isDirty()) {
			WT.warn(WT.res('warn.print.notsaved'));
		} else {
			me.mys.printExpenseNote([id]);
		}
	},
	
	sendMail: function(id) {
		var me = this;
		if(me.getModel().isDirty()) {
			WT.warn(WT.res('warn.print.notsaved'));
		} else {
			me.wait();
			var opts = {};
			var	mapi = WT.getServiceApi('com.sonicle.webtop.mail');
			if (mapi) {
				var meid = mapi.buildMessageEditorId();
				WT.ajaxReq(me.mys.ID, 'PrepareSendExpenseNoteAsEmail', {
					params: {
						uploadTag: meid,
						ids: WTU.arrayAsParam(id)
					},
					callback: function(success, json) {
						mapi.newMessage({
							messageEditorId: meid,
							format: 'html',
							content: '<br>',
							attachments: json.data
						}, {
							dirty: true,
							contentReady: false,
							appendContent: false
						});
						Ext.callback(opts.callback, opts.scope || me.mys, [true]);
						me.unwait();
					}
				});
			}
		}
	},
	
	onViewLoad: function(s, success) {
		if(!success) return;
		var me = this,
				mo = me.getModel();
		
		if(me.isMode(me.MODE_NEW)) {
			mo.set('currency', me.mys.getVar('expenseNoteDefaultCurrency'));
		}
		
		if(mo.get('operatorId') === null) me.lref('flduser').setReadOnly(false);
	},
	
	onViewInvalid: function (s, mo, errs) {
		var me = this;
		WTU.updateFieldsErrors(me.lref('mainForm1'), errs);
		WTU.updateFieldsErrors(me.lref('mainForm2'), errs);
	},
	
	onViewClose: function(s) {
		s.mys.cleanupUploadedFiles(WT.uiid(s.getId()));
	},
	
	openAttachmentUI: function(rec, download) {
		var me = this,
				name = rec.get('name'),
				uploadId = rec.get('_uplId'),
				url;
		
		if (!Ext.isEmpty(uploadId)) {
			url = WTF.processBinUrl(me.mys.ID, 'DownloadExpenseNoteDocument', {
				inline: !download,
				uploadId: uploadId
			});
		} else {
			url = WTF.processBinUrl(me.mys.ID, 'DownloadExpenseNoteDocument', {
				inline: !download,
				eNId: me.getModel().getId(),
				attachmentId: rec.get('id')
			});
		}
		if (download) {
			Sonicle.URLMgr.downloadFile(url, {filename: name});
		} else {
			Sonicle.URLMgr.openFile(url, {filename: name});
		}
	},
	
	calculateTotal: function () {
		var me = this,
				totCurrency = 0;
		
		me.getModel().details().getData().items.forEach(function(element) {
			totCurrency += element.data.totalDoc;
		});
		
		me.getModel().set('totCurrency', totCurrency);
	},
	
	addNewOnSave: function (model, fromDate, toDate, opts) {
		opts = opts || {};
		var me = this,
				vw = WT.createView(me.mys.ID, 'view.ExpenseNoteDetail', {
				swapReturn: true,
				viewCfg: {
					dateMinValue: fromDate,
					dateMaxValue: toDate
				}
		});
		vw.on('viewsave', function (s, success, model) {
			if (success) {
				var sto = me.getModel().details();
				sto.add(sto.createModel(model.data));
				if(s.addNewOnSave) me.addNewOnSave(model, fromDate, toDate, opts);
				
				me.calculateTotal();
			}
			Ext.callback(opts.callback, opts.scope || me, [success, model, s.addNewOnSave]);
		});
		vw.showView(function () {
					vw.begin('new', {
						data: {
							operatorId: model.get('operatorId'),
							companyId: model.get('companyId'),
							date: model.get('date'),
							description: model.get('description'),
							customerId: model.get('customerId')
						}
					});
				});
	},
	addExpenseNoteDetail: function (operatorId, companyId, fromDate, toDate, opts) {
		opts = opts || {};
		var me = this,
				vw = WT.createView(me.mys.ID, 'view.ExpenseNoteDetail', {
					swapReturn: true,
					viewCfg: {
						dateMinValue: fromDate,
						dateMaxValue: toDate
					}
		});
		vw.on('viewsave', function (s, success, model) {
			if (success) {
				var sto = me.getModel().details();
				sto.add(sto.createModel(model.data));
				if(s.addNewOnSave) me.addNewOnSave(model, fromDate, toDate, opts);
				
				me.calculateTotal();
			}
			Ext.callback(opts.callback, opts.scope || me, [success, model, s.addNewOnSave]);
		});
		
		var dates=[];
		dates.push(fromDate);
		me.getModel().details().getData().items.forEach(function(element) {
			dates.push(element.data.date);
		});
		
		vw.showView(function () {
					vw.begin('new', {
						data: {
							operatorId: operatorId,
							companyId: companyId,
							date: new Date(Math.max.apply(null,dates))
						}
					});
				});		
	},
	deleteExpenseNoteDetail: function (rec) {
		var me = this,
				grid = me.lref('gpExpenseNoteDetail'),
				sto = grid.getStore();

		WT.confirm(WT.res('confirm.delete'), function (bid) {
			if (bid === 'yes') {
				sto.remove(rec);
				
				me.calculateTotal();
			}
		}, me);
	},
	editExpenseNoteDetailUI: function (rec, opts) {
		opts = opts || {};
		var me = this,
				vw = WT.createView(me.mys.ID, 'view.ExpenseNoteDetail', {
					swapReturn: true,
					viewCfg: {
						dateMinValue: me.getModel().get('fromDate'),
						dateMaxValue: me.getModel().get('toDate')
					}
				});
		vw.on('viewsave', function (s, success, model) {
			if (success){
				me.editExpenseNoteDetail(model.getId(), model.getData());
				if(s.addNewOnSave) me.addNewOnSave(model, me.getModel().get('fromDate'),  me.getModel().get('toDate'), opts);
				
				me.calculateTotal();
			} 
		});
		vw.showView(function () {
					vw.begin('edit', {
						data: me.fromToSieveExpenseNoteDetail(rec.getData())
					});
				});
	},
	editExpenseNoteDetail: function(id, data) {
		var me = this,
				sto = me.lref('gpExpenseNoteDetail').getStore(),
				rec = sto.getById(id);
		if (rec) {
			rec.set(me.fromToSieveExpenseNoteDetail(data));
		}
	},
	
	fromToSieveExpenseNoteDetail: function(data) {
		return {
			id: data.id,
			expenseNoteId: data.expenseNoteId,
			operatorId: data.operatorId,
			companyId: data.companyId,
			typeId: data.typeId,
			total: data.total,
			date: data.date,
			paymentCompany: data.paymentCompany,
			invoice: data.invoice,
			invoiceNumber: data.invoiceNumber,
			withOthers: data.withOthers,
			customerId: data.customerId,
			km: data.km,
			totalDoc: data.totalDoc,
			currency: data.currency,
			change: data.change,
			currencyDoc: data.currencyDoc,
			description: data.description,
			detailDocuments: data.detailDocuments
		};
	},
	
	addDocument: function (uploadId, file) {
		var me = this;
		var gp = me.lref('gpDocuments'),
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
				grid = me.lref('gpDocuments'),
				sto = grid.getStore();

		WT.confirm(WT.res('confirm.delete'), function (bid) {
			if (bid === 'yes') {
				sto.remove(rec);
			}
		}, me);
	},
	openDocuments: function (documentIds) {
		Sonicle.URLMgr.openFile(WTF.processBinUrl(this.mys.ID, 'DownloadExpenseNoteDocument', {
			documentIds: WTU.arrayAsParam(documentIds)
		}));
	},
	downloadDocument: function (documentIds) {
		Sonicle.URLMgr.openFile(WTF.processBinUrl(this.mys.ID, 'DownloadExpenseNoteDocument', {
			raw: 1,
			documentIds: WTU.arrayAsParam(documentIds)
		}));
	},
	
	gpExpenseNoteDetail: function () {
		return this.lref('gpExpenseNoteDetail');
	}
});

