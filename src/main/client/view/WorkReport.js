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
		'Sonicle.Bytes',
		'Sonicle.upload.Button'
	],
	dockableConfig: {
		title: '{workReport.tit}', //localizzato
		iconCls: 'wtdrm-icon-workReport-xs',
		width: 910,
		height: 500
	},
	fieldTitle: 'workReportNo',
	modelName: 'Sonicle.webtop.drm.model.WorkReport',
	initComponent: function () {
		var me = this,
				gpId = Ext.id(null, 'gridpanel');

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
					title: 'Report',
					items: [
						{
							xtype: 'container',
							layout: 'column',
							items: [
								{
									xtype: 'wtform',
									reference: 'column1',
									modelValidation: true,
									items: [
										WTF.localCombo('id', 'desc', {
											bind: '{record.companyId}',
											store: {
												autoLoad: true,
												model: 'WTA.model.Simple',
												proxy: WTF.proxy(me.mys.ID, 'LookupCompanies'),
												listeners: {
													load: function (s) {
														if (s.loadCount === 1) {
															var meta = s.getProxy().getReader().metaData;
															if (meta.selected) {
																me.getModel().set('companyId', meta.selected);
															}
														}
													}
												}
											},
											listeners: {
												select: function (s, rec) {
													me.lref('flduser').getStore().load();
												}
											},
											fieldLabel: me.mys.res('workReport.fld-company.lbl')
										}),
										WTF.localCombo('id', 'desc', {
											bind: '{record.contactId}',
											store: {
												autoLoad: true,
												model: 'WTA.model.Simple',
												proxy: WTF.proxy(me.mys.ID, 'LookupContacts')
											},
											fieldLabel: me.mys.res('workReport.fld-contact.lbl')
										}),
										{
											xtype: 'fieldcontainer',
											layout: 'hbox',
											defaults: {
												margin: '0 10 0 0'
											},
											items: [
												WTF.remoteCombo('id', 'desc', {
													reference: 'fldmasterdata',
													bind: '{record.customerId}',
													autoLoadOnValue: true,
													store: {
														model: 'WTA.model.Simple',
														proxy: WTF.proxy(WT.ID, 'LookupCustomersSuppliers'),
														listeners: {
															beforeload: {
																fn: function (s) {
																	//TODO - verificare valore parentMasterDataId
																	WTU.applyExtraParams(s, {
																		parentMasterDataId: me.getModel().get('masterDataId')
																	});
																}
															}
														}
													},
													listeners: {
														select: {
															fn: function (c, rec) {
																var locked = rec.get('lockStatus');
																console.log(locked);
																if (locked === 'L') {
																	Ext.Msg.alert('Status', 'Selected Customer is locked');
																}
															}
														}
													},
													fieldLabel: me.mys.res('workReport.fld-realCustomer.lbl'),
													anchor: '100%'
												}),
												{
													xtype: 'button'
												}
											]
										},
										{
											xtype: 'datefield',
											reference: 'fldfromdate',
											bind: '{record.fromDate}',
											format: WT.getShortDateFmt(),
											listeners:
													{
														select: function (s, v)
														{
															if (me.lref('fldtodate').getValue() === null || v > me.lref('fldtodate').getValue()) {
																me.lref('fldtodate').setValue(v);
															}
															var days = Sonicle.Date.diffDays(me.lref('fldfromdate').getValue(),
																	me.lref('fldtodate').getValue());
															me.lref('flddaytrasfert').setValue(days);
														}
													},
											fieldLabel: me.mys.res('workReport.fld-fromDate.lbl')
										},
										{
											xtype: 'textfield',
											bind: '{record.referenceNo}',
											fieldLabel: me.mys.res('workReport.fld-reference.lbl')
										},
										{
											xtype: 'textfield',
											bind: '{record.ddtNo}',
											fieldLabel: me.mys.res('workReport.fld-ddt.lbl')
										}
									]
								},
								{
									xtype: 'wtform',
									reference: 'column2',
									modelValidation: true,
									defaults: {
										labelWidth: 150
									},
									items: [
										WTF.remoteCombo('id', 'desc', {
											reference: 'flduser',
											bind: '{record.userId}',
											store: {
												autoLoad: true,
												model: 'WTA.model.Simple',
												proxy: WTF.proxy(me.mys.ID, 'LookupCompanyUsers'),
												listeners: {
													beforeload: function (s) {
														WTU.applyExtraParams(s, {
															companyId: me.getModel().get('companyId')
														});
													}
												}
											},
											//TODO 
											fieldLabel: me.mys.res('workReport.fld-nominative.lbl')
										}),
										{
											xtype: 'soplaceholderfield'
										},
										WTF.remoteCombo('id', 'desc', {
											reference: 'fldstatmasterdata',
											bind: '{record.customerStatId}',
											autoLoadOnValue: true,
											store: {
												model: 'WTA.model.Simple',
												proxy: WTF.proxy(WT.ID, 'LookupStatisticCustomersSuppliers'),
												listeners: {
													beforeload: {
														fn: function (s) {
															WTU.applyExtraParams(s, {
																parentMasterDataId: me.getModel().get('masterDataId')
															});
														}
													}
												}
											},
											fieldLabel: me.mys.res('workReport.fld-customerStat.lbl'),
											anchor: '100%'
										}),
										{
											xtype: 'datefield',
											reference: 'fldtodate',
											bind: '{record.toDate}',
											listeners: {
												select: function (s, rec) {
													var days = Sonicle.Date.diffDays(me.lref('fldfromdate').getValue(),
															me.lref('fldtodate').getValue());
													me.lref('flddaytrasfert').setValue(days);
												}
											},
											format: WT.getShortDateFmt(),
											fieldLabel: me.mys.res('workReport.fld-toDate.lbl')
										},
										WTF.remoteCombo('id', 'desc', {
											reference: 'fldcausal',
											bind: '{record.causalId}',
											autoLoadOnValue: true,
											store: {
												model: 'WTA.model.CausalLkp',
												proxy: WTF.proxy(WT.ID, 'LookupCausals'),
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
													beforeload: {
														fn: function (s) {
															var mo = me.getModel();
															WTU.applyExtraParams(s, {
																profileId: WT.getVar('profileId'), //mo.get('_profileId'),
																masterDataId: mo.get('customerId')
															});
														}
													}
												}
											},
											triggers: {
												clear: WTF.clearTrigger()
											},
											fieldLabel: me.mys.res('workReport.fld-confirmation.lbl'),
											anchor: '100%'
										}),
										{
											xtype: 'datefield',
											bind: '{record.ddtDate}',
											format: WT.getShortDateFmt(),
											fieldLabel: me.mys.res('workReport.fld-ddtToDate.lbl')
										}
									]
								}
							]
						},
						{
							//NOTES AND SUPPLIES
							xtype: 'wtform',
							items: [
								{
									xtype: 'textareafield',
									bind: '{record.notes}',
									fieldLabel: me.mys.res('workReport.fld-notes.lbl'),
									labelAlign: 'top',
									anchor: '100%'
								},
								{
									//FLAGG BOTTOM WINDOW
									xtype: 'container',
									layout: 'hbox',
									defaults: {
										margin: '0 10 0 0'
									},
									items: [
										{
											xtype: 'checkbox',
											bind: '{record.applySignature}',
											boxLabel: me.mys.res('workReport.fld-signature.lbl'),
											boxLabelAlign: 'before'
										},
										{
											xtype: 'checkbox',
											bind: '{record.chargeTo}',
											boxLabel: me.mys.res('workReport.fld-chargeTo.lbl'),
											boxLabelAlign: 'before'
										},
										{
											xtype: 'checkbox',
											bind: '{record.freeSupport}',
											boxLabel: me.mys.res('workReport.fld-assistance.lbl'),
											boxLabelAlign: 'before'
										}
									]
								},
								WTF.localCombo('id', 'desc', {
									bind: '{record.docStatusId}',
									store: {
										autoLoad: true,
										model: 'WTA.model.Simple',
										proxy: WTF.proxy(me.mys.ID, 'LookupDocStatuses'),
										listeners: {
											load: function (s) {
												me.getModel().set('docStatusId', 11);
											}
										}
									},
									//TODO - IN BASE AL PROFILO CI SARA LO STATO DEL DOC PREDEFINITO
									/*listeners: {
									 afterrender: function (combo) {
									 this.store.load({
									 callback: function () {
									 combo.setValue(combo.store.getAt(0).get(combo.valueField));
									 }
									 });
									 }
									 },*/
									fieldLabel: me.mys.res('workReport.fld-status.lbl')
								})
							]
						}
					]
				},
				{
					title: me.mys.res('workReport.details.tit'),
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
								{//TODO MASCHERA REPORT DETAIL
									xtype: 'grid',
									reference: 'gpReportDetail',
									modelValidation: true,
									//title: me.mys.res('workReport.details.tit'),
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
											header: me.mys.res('gpReportDetail.rif.lbl')
										},
										{
											xtype: 'solookupcolumn',
											dataIndex: 'workTypeId',
											store: {
												autoLoad: true,
												model: 'WTA.model.Simple',
												proxy: WTF.proxy(me.mys.ID, 'LookupWorkType')
											},
											editor: Ext.create(WTF.lookupCombo('id', 'desc', {
												allowBlank: false,
												store: {
													autoLoad: true,
													model: 'WTA.model.Simple',
													proxy: WTF.proxy(me.mys.ID, 'LookupWorkType')
												}
											})),
											displayField: 'desc',
											header: me.mys.res('gpReportDetail.workType.lbl'),
											flex: 2
										},
										{
											dataIndex: 'duration',
											xtype: 'numbercolumn',
											flex: 1,
											editor: {
												xtype: 'numberfield',
												allowBlank: false,
												minValue: 0,
												allowDecimals: true,
												decimalSeparator: ',',
												decimalPrecision: 2,
												step: 0.5
											},
											summaryType: 'sum',
											summaryRenderer: function (value, summaryData, dataIndex) {
												return Ext.String.format('Total Hour{0}: {1}', value > 1 ? 's' : '', value);
											},
											header: me.mys.res('gpReportDetail.duration.lbl')
										},
										{
											dataIndex: 'specialFlag',
											xtype: 'checkcolumn',
											width: '25',
											editor: {
												xtype: 'checkbox',
												matchFieldWidth: true
											},
											header: me.mys.res('gpReportDetail.rowFlag.lbl')
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
												var sm = me.lref('gpReportDetail').getSelectionModel();
												me.deleteDetail(sm.getSelection());
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
									xtype: 'wtform',
									items: [
										{
											xtype: 'numberfield',
											reference: 'flddaytrasfert',
											bind: '{record.dayTrasfert}',
											minValue: 0,
											fieldLabel: me.mys.res('gpReportDetail.hourTravel.lbl')
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
											fieldLabel: me.mys.res('gpReportDetail.trasferts.lbl')})
									]
								}
							]
						},
						{
							region: 'east',
							xtype: 'panel',
							layout: 'fit',
							title: me.mys.res('workReport.details.fld-description.lbl'),
							flex: 1,
							split: true,
							items: [
								{
									xtype: 'textareafield',
									maxLength: 2048,
									enableKeyEvents: true,
									style: {textTransform: 'uppercase', overflow: 'scroll', fontFamily: 'courier new'},
									listeners: {
										change: function (field, newValue) {
											field.setValue(newValue.toUpperCase());
										}
									}
								}
							]
						}
					]
				},
				{
					title: me.mys.res('workReport.document.tit'),
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
							listeners: {
								linkclick: function (s, idx, rec) {
									me.downloadAttachment([rec.getId()]);
								}
							}
						},
						{
							xtype: 'sobytescolumn',
							dataIndex: 'size',
							header: me.mys.res('gpWorkReportAttachment.size.lbl'),
							width: 110
						}
					],
					tbar: [
						me.getAct('downloadAttachment'),
						me.getAct('openAttachment')
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
		var gp = me.lref('gpReportDetail'),
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
				grid = me.lref('gpReportDetail'),
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
	initActions: function () {
		var me = this;
		me.addAct('openAttachment', {
			tooltip: null,
			disabled: true,
			handler: function () {
				var sel = me.getSelectedFiles();
				if (sel.length > 0) {
					ids = me.selectionIds(sel);
					//	if(sel) me.openFileUI(sel);
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
			handler: function () {
				var sel = me.getSelectedFiles();
				me.deleteDocument(sel);
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
		}
	},
	onViewInvalid: function (s, mo, errs) {
		var me = this;
		WTU.updateFieldsErrors(me.lref('column1'), errs);
		WTU.updateFieldsErrors(me.lref('column2'), errs);
		WTU.updateFieldsErrors(me.lref('gpReportDetail'), errs);
	},
	updateRowNo: function (sto) {
		for (var i = 0; i <= sto.getCount(); i++) {
			sto.data.items[i].set('rowNo', i + 1);
		}
	}
});
