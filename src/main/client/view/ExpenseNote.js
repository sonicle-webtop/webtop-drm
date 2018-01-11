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
		'WTA.ux.data.SimpleSourceModel',
		'Sonicle.Bytes',
		'WTA.ux.UploadBar'
	],
	dockableConfig: {
		title: '{expenseNote.tit}',
		iconCls: 'wtdrm-icon-expensenote-xs',
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
				'->',
				WTF.localCombo('id', 'desc', {
					reference: 'flduser',
					bind: '{record.operatorId}',
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
					fieldLabel: me.mys.res('expenseNote.fld-operator.lbl')
				})
			]
		});
		
		me.callParent(arguments);
		me.initActions();
		me.initCxm();
		
		me.add({
			region: 'center',
			xtype: 'tabpanel',
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
											width: '420px'
										}),
										{
											xtype: 'datefield',
											reference: 'fldfromdate',
											bind: '{record.fromDate}',
											fieldLabel: me.mys.res('expenseNote.fld-fromDate.lbl'),
											width: '420px'
										},
										{
											xtype: 'fieldcontainer',
											layout: 'hbox',
											defaults: {
												margin: '0 5 0 0'
											},
											items: [
												{
													xtype: 'textfield',
													reference: 'fldtotcurrency',
													bind: '{record.totCurrency}',
													fieldLabel: me.mys.res('expenseNote.fld-totCurrency.lbl'),
													width: '285px'
												},
												WTF.lookupCombo('id', 'desc', {
													reference: 'fldcurrency',
													bind: '{record.currency}',
													store: Ext.create('Sonicle.webtop.drm.store.UserGroupType', {
														autoLoad: true
													}),
													width: '100px'
												}),
												{
													xtype: 'button',
													iconCls: 'wtdrm-icon-totcurrency-xs',
													handler: function() {
														alert("Ciao");
													}
												}
											]
										}
									]
								},
								{
									xtype: 'wtform',
									items: [
										{
											xtype: 'textfield',
											reference: 'flddescription',
											bind: '{record.description}',
											fieldLabel: me.mys.res('expenseNote.fld-description.lbl'),
											width: '420px'
										},
										{
											xtype: 'datefield',
											reference: 'fldtodate',
											bind: '{record.toDate}',
											fieldLabel: me.mys.res('expenseNote.fld-toDate.lbl'),
											width: '420px'
										},
										WTF.lookupCombo('id', 'desc', {
											reference: 'fldstatus',
											bind: '{record.status}',
											store: Ext.create('Sonicle.webtop.drm.store.StatusType', {
												autoLoad: true
											}),
											fieldLabel: me.mys.res('expenseNote.fld-status.lbl'),
											width: '420px'

										})
									]
								}
							]
						},
						{
							xtype: 'grid',
							reference: 'gpExpenseNoteDetail',
							title: me.mys.res('expenseNote.tab.detail.tit'),
							iconCls: '',
							border: true,
							flex: 2,
							store: null,
							columns: [
								{text: me.mys.res('gpExpenseNoteDetail.date.lbl'), dataIndex: 'date', flex: 1},
								{text: me.mys.res('gpExpenseNoteDetail.description.lbl'), dataIndex: 'description', flex: 2},
								{text: me.mys.res('gpExpenseNoteDetail.customer.lbl'), dataIndex: 'customer', flex: 2},
								{text: me.mys.res('gpExpenseNoteDetail.typeofcost.lbl'), dataIndex: 'typeOfCost', flex: 2},
								{text: me.mys.res('gpExpenseNoteDetail.otherspresent.lbl'), dataIndex: 'othersPresent', flex: 1},
								{text: me.mys.res('gpExpenseNoteDetail.total.lbl'), dataIndex: 'total', flex: 1},
								{text: me.mys.res('gpExpenseNoteDetail.currency.lbl'), dataIndex: 'currency', flex: 1},
								{text: me.mys.res('gpExpenseNoteDetail.documenttotal.lbl'), dataIndex: 'documentTotal', flex: 1},
								{text: me.mys.res('gpExpenseNoteDetail.businesspayment.lbl'), dataIndex: 'businessPayment', flex: 1},
								{text: me.mys.res('gpExpenseNoteDetail.invoice.lbl'), dataIndex: 'invoice', flex: 1},
								{text: me.mys.res('gpExpenseNoteDetail.invoicenumber.lbl'), dataIndex: 'invoiceNumber', flex: 1},
								{text: me.mys.res('gpExpenseNoteDetail.totalexceeded.lbl'), dataIndex: 'totalExceeded', flex: 1}
							],
							tbar: [
								me.getAct('expenseNoteDetail', 'add'),
								me.getAct('expenseNoteDetail', 'remove'),
								'-',
								me.getAct('expenseNoteDetail', 'edit')
							]
						}
					]
				},
				{
					title: me.mys.res('expenseNote.tab.documents.tit'),
					xtype: 'grid',
					id: gpId,
					reference: 'gpDocuments',
					bind: {
						store: '{record.documents}'
					},
					selModel: {
						type: 'checkboxmodel',
						mode: 'MULTI'
					},
					columns: [
						{
							xtype: 'solinkcolumn',
							dataIndex: 'fileName',
							header: me.mys.res('gpExpenseNoteDocument.filename.lbl'),
							flex: 3,
							listeners: {
								linkclick: function (s, idx, rec) {
									me.downloadAttachment([rec.getId()]);
								}
							}
						}, {
							xtype: 'sobytescolumn',
							dataIndex: 'size',
							header: me.mys.res('gpExpenseNoteDocument.size.lbl'),
							flex: 1
						}
					],
					tbar: [
						me.getAct('downloadDocument'),
						me.getAct('openDocument'),
						me.getAct('deleteDocument')
					],
					bbar: {
						xtype: 'wtuploadbar',
						sid: me.mys.ID,
						uploadContext: 'UploadExpenseNoteDocument',
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
							me.updateDisabled('downloadDocument');
							me.updateDisabled('openDocument');
							me.updateDisabled('deleteDocument');
						}
					},
					plugins: [{
						ptype: 'sofiledrop',
						text: WT.res('sofiledrop.text')
					}]
				}
			]
		});
	},
	
	initActions: function () {
		var me = this;
		
		me.addAct('expenseNoteDetail', 'add', {
			text: WT.res('act-add.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-add-xs',
			handler: function () {
				me.addExpenseNoteDetail({
					callback: function (success) {
						if (success) {
							me.gpExpenseNoteDetail().getStore().load();
						}
					}
				});
			}
		});
		me.addAct('expenseNoteDetail', 'remove', {
			text: WT.res('act-remove.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-remove-xs',
			disabled: true,
			handler: function () {
				
			}
		});
		me.addAct('expenseNoteDetail', 'edit', {
			text: WT.res('act-edit.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-edit-xs',
			disabled: true,
			handler: function () {
				
			}
		});
		
		me.addAct('openDocument', {
			tooltip: null,
			iconCls: 'wtdrm-icon-openAttachment-xs',
			disabled: true,
			handler: function () {
				var sel = me.getSelectedFiles();
				if (sel.length > 0) {
					ids = me.selectionIds(sel);
					me.openDocuments(ids);
				}
			}
		});
		me.addAct('downloadDocument', {
			tooltip: null,
			iconCls: 'wtdrm-icon-downloadAttachment-xs',
			disabled: true,
			handler: function () {
				var sel = me.getSelectedFiles();

				if (sel.length > 0) {
					ids = me.selectionIds(sel);
					me.downloadDocument(ids);
				}
			}
		});
		me.addAct('deleteDocument', {
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
	},
	
	addExpenseNoteDetail: function (opts) {
		opts = opts || {};
		var me = this,
				vct = WT.createView(me.mys.ID, 'view.ExpenseNoteDetail');
		vct.getView().on('viewsave', function (s, success, model) {
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vct.show(false,
				function () {
					vct.getView().begin('new');
				});
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

