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
Ext.define('Sonicle.webtop.drm.view.TimetableRequest', {
	extend: 'WTA.sdk.ModelView',
	requires: [
		'Sonicle.webtop.drm.model.TimetableRequest',
		'WTA.ux.data.SimpleSourceModel',
		'Sonicle.Bytes',
		'WTA.ux.UploadBar'
	],
	dockableConfig: {
		title: '{timetableRequest.tit}',
		iconCls: 'wtdrm-icon-timetable2-xs',
		width: 580,
		height: 470
	},
	fieldTitle: 'leaveRequestId',
	modelName: 'Sonicle.webtop.drm.model.TimetableRequest',
	initComponent: function () {
		var me = this,
			gpId = Ext.id(null, 'gridpanel');
		
		Ext.apply(me, {
			tbar: [
				'->',
				WTF.localCombo('id', 'desc', {
					reference: 'flduser',
					bind: '{record.userId}',
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
										me.lref('fldmanager').getStore().load();
									}
								}
							}
						}
					},
					listeners: {
						select: function (s, r) {
							me.lref('fldcompany').getStore().load();
							me.lref('fldmanager').getStore().load();
						}
					},
					fieldLabel: me.mys.res('timetableRequest.fld-user.lbl'),
					allowBlank: true,
					readOnly: true
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
					title: me.mys.res('timetableRequest.tab.timetableRequestTit'),
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
							xtype: 'wtform',
							reference: 'form',
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
												WTU.applyExtraParams(op.getProxy(), {operator: me.getModel().get('userId')});
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
									fieldLabel: me.mys.res('timetableRequest.fld-company.lbl'),
									width: '555px'
								}),
								WTF.localCombo('id', 'desc', {
									reference: 'fldmanager',
									bind: '{record.managerId}',
									autoLoadOnValue: true,
									store: {
										model: 'WTA.model.Simple',
										proxy: WTF.proxy(me.mys.ID, 'LookupManagers', null, {
											extraParams: {
												operator: null
											}
										}),
										listeners: {
											beforeload: function(s,op) {
												WTU.applyExtraParams(op.getProxy(), {operator: me.getModel().get('userId')});
											},
											load: function (s) {
												if (me.isMode('new')) {
													var meta = s.getProxy().getReader().metaData;
													if (meta.selected) {
														me.lookupReference('fldmanager').setValue(meta.selected);
													}
												}
											}
										}
									},
									fieldLabel: me.mys.res('timetableRequest.fld-manager.lbl'),
									width: '555px'
								}),
								WTF.localCombo('id', 'desc', {
									reference: 'fldtype',
									bind: '{record.type}',
									store: {
										autoLoad: true,
										model: 'WTA.model.Simple',
										proxy: WTF.proxy(me.mys.ID, 'LookupLeaveRequestType'),
										listeners: {
											load: function (s) {
												if (me.isMode('new')) {
													var meta = s.getProxy().getReader().metaData;
													if (meta.selected) {
														me.lookupReference('fldtype').setValue(meta.selected);
													}
												}
											}
										}
									},
									listeners: {
										change: function (t, n, o, e) {
											if(n === 'H'){
												me.lref('fldfromhour').setValue(null);
												me.lref('fldtohour').setValue(null);
												me.lref('hours').setHidden(true);
											}else{
												me.lref('hours').setHidden(false);
											}
										}
									},
									fieldLabel: me.mys.res('timetableRequest.fld-type.lbl'),
									width: '555px'
								}),
								{
									xtype: 'fieldcontainer',
									layout: 'hbox',
									defaults: {
										margin: '0 5 0 0'
									},
									items: [
										{
											xtype: 'datefield',
											reference: 'fldfromdate',
											bind: '{record.fromDate}',
											fieldLabel: me.mys.res('timetableRequest.fld-fromDate.lbl'),
											listeners: {
												select: function (t, v, o) {
													if(v > me.lref('fldtodate').getValue()) me.lref('fldtodate').setValue(v);
												}
											}
										},
										{
											xtype: 'datefield',
											reference: 'fldtodate',
											bind: '{record.toDate}',
											fieldLabel: me.mys.res('timetableRequest.fld-toDate.lbl'),
											listeners: {
												select: function (t, v, o) {
													if(v < me.lref('fldfromdate').getValue()) me.lref('fldfromdate').setValue(v);
												}
											}
										}
									]
								},
								{
									xtype: 'fieldcontainer',
									layout: 'hbox',
									defaults: {
										margin: '0 5 0 0'
									},
									hidden: true,
									reference: 'hours',
									items: [
										WTF.localCombo('id', 'desc', {
											reference: 'fldfromhour',
											bind: '{record.fromHour}',
											store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
												autoLoad: true
											}),
											fieldLabel: me.mys.res('timetableRequest.fld-fromHour.lbl'),
											editable: true
										}),
										WTF.localCombo('id', 'desc', {
											reference: 'fldtohour',
											bind: '{record.toHour}',
											store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
												autoLoad: true
											}),
											fieldLabel: me.mys.res('timetableRequest.fld-toHour.lbl'),
											editable: true
										})
									]
								},
								{
									xtype: 'textarea',
									reference: 'fldnotes',
									bind: '{record.notes}',
									fieldLabel: me.mys.res('timetableRequest.fld-notes.lbl'),
									width: '555px'
								},
								{
									xtype: 'checkbox',
									reference: 'fldcancellationrequest',
									bind: '{record.cancRequest}',
									boxLabel: me.mys.res('timetableRequest.fld-cancellationRequest.lbl'),
									hideEmptyLabel: false,
									hidden: true,
									listeners: {
										change: function (t, newV, oldV, o) {
											if(newV === true){
												me.lref('fldcancellationreason').setHidden(false);
											}else{
												me.lref('fldcancellationreason').setValue('');
												me.lref('fldcancellationreason').setHidden(true);
											}
										}
									}
								},
								{
									xtype: 'textarea',
									reference: 'fldcancellationreason',
									bind: '{record.cancReason}',
									fieldLabel: me.mys.res('timetableRequest.fld-cancellationReason.lbl'),
									width: '555px',
									hidden: true
								},
								WTF.lookupCombo('id', 'desc', {
									reference: 'fldapprove',
									bind: '{record.result}',
									store: Ext.create('Sonicle.webtop.drm.store.ApprovedType', {
										autoLoad: true
									}),
									fieldLabel: me.mys.res('timetableRequest.fld-approve.lbl'),
									width: '555px',
									hidden: true
								}),
								WTF.lookupCombo('id', 'desc', {
									reference: 'fldapprovecanc',
									bind: '{record.canceResult}',
									store: Ext.create('Sonicle.webtop.drm.store.ApprovedType', {
										autoLoad: true
									}),
									fieldLabel: me.mys.res('timetableRequest.fld-approvecanc.lbl'),
									width: '555px',
									hidden: true
								})
							]
						}
					]
				},
				{
					title: me.mys.res('timetableRequest.tab.documents.tit'),
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
							header: me.mys.res('timetableRequest.filename.lbl'),
							flex: 3,
							listeners: {
								linkclick: function (s, idx, rec) {
									me.downloadDocument([rec.getId()]);
								}
							}
						}, {
							xtype: 'sobytescolumn',
							dataIndex: 'size',
							header: me.mys.res('timetableRequest.size.lbl'),
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
						uploadContext: 'UploadTimetableRequestDocument',
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
		
		me.on('viewinvalid', me.onViewInvalid);
		me.on('viewload', me.onViewLoad);
	},
	
	initActions: function () {
		var me = this;
		
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
		me.addRef('cxmGridDocument', Ext.create({
			xtype: 'menu',
			items: [
				me.getAct('openDocument'),
				me.getAct('downloadDocument'),
				'-',
				me.getAct('deleteDocument')
			],
			listeners: {
				beforeshow: function (s) {
					me.updateDisabled('openDocument');
					me.updateDisabled('downloadDocument');
					me.updateDisabled('deleteDocument');
				}
			}
		}));
	},
	
	gpDocuments: function () {
		this.lref('gpDocuments');
	},
	getSelectedFiles: function () {
		return this.lref('gpDocuments').getSelection();
	},
	selectionIds: function (sel) {
		var ids = [];
		Ext.iterate(sel, function (rec) {
			ids.push(rec.getId());
		});
		return ids;
	},
	
	updateDisabled: function (action) {
		var me = this,
				dis = me.isDisabled(action);
		me.setActDisabled(action, dis);
	},
	
	isDisabled: function (action) {
		var me = this, sel;
		switch (action) {
			case 'openDocument':
				sel = me.getSelectedFiles();
				return false;
			case 'downloadDocument':
				sel = me.getSelectedFiles();
				return false;
			case 'deleteDocument':
				sel = me.getSelectedFiles();
				return false;
		}
	},
	
	onViewLoad: function(s, success) {
		if(!success) return;
		var me = this,
				mo = me.getModel();
	
		if(mo.get('userId') === null) me.lref('flduser').setReadOnly(false);
	
		WT.ajaxReq(me.mys.ID, 'IsTimetableRequestPreviousDate', {
			params: {},
			callback: function (success, json) {
				if(success){
					if(json.data != true){
						me.lookupReference('fldfromdate').setMinValue(new Date());
						me.lookupReference('fldtodate').setMinValue(new Date());
					}
				}
			}
		});
		if(me.isMode(me.MODE_VIEW)){
			me.getAct('saveClose').setDisabled(true);
			me.lref('fldcancellationrequest').setHidden(false);
			me.lref('fldapprove').setHidden(false);
			me.lref('fldapprovecanc').setHidden(false);
		}else if(me.isMode(me.MODE_EDIT)) {
			me.lref('flduser').setReadOnly(true);
			me.lref('fldcompany').setReadOnly(true);
			me.lref('fldmanager').setReadOnly(true);
			me.lref('fldcancellationrequest').setHidden(false);
			
			if(me.lref('fldcancellationrequest').getValue() === true){
				me.lref('fldtype').setReadOnly(true);
				me.lref('fldfromdate').setReadOnly(true);
				me.lref('fldtodate').setReadOnly(true);
				me.lref('fldfromhour').setReadOnly(true);
				me.lref('fldtohour').setReadOnly(true);
				me.lref('fldnotes').setReadOnly(true);
				me.lref('fldcancellationrequest').setReadOnly(true);
				me.lref('fldcancellationreason').setReadOnly(true);
			}
			
			WT.ajaxReq(me.mys.ID, 'IsLineManagerLogged', {
				params: {},
				callback: function (success, json) {
					if(success){
						if(json.data == true){
							me.lref('fldtype').setReadOnly(true);
							me.lref('fldfromdate').setReadOnly(true);
							me.lref('fldtodate').setReadOnly(true);
							me.lref('fldfromhour').setReadOnly(true);
							me.lref('fldtohour').setReadOnly(true);
							me.lref('fldnotes').setReadOnly(true);
							me.lref('fldcancellationreason').setReadOnly(true);
							
							if(mo.get('userId') === mo.get('managerId')){
								if(me.lref('fldcancellationrequest').getValue() === true){
									me.lref('fldcancellationrequest').setReadOnly(true);
								}else{
									me.lref('fldcancellationrequest').setReadOnly(false);
								}
							}else{
								me.lref('fldcancellationrequest').setReadOnly(true);
							}
							
							if(mo.get('cancRequest') === true){
								me.lref('fldapprovecanc').setHidden(false);
							}else{
								me.lref('fldapprove').setHidden(false);
							}
						}
					}
				}
			});
		}
	},
	
	onViewInvalid: function (s, mo, errs) {
		var me = this;
		WTU.updateFieldsErrors(me.lref('form'), errs);
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
		Sonicle.URLMgr.openFile(WTF.processBinUrl(this.mys.ID, 'DownloadTimetableRequestDocument', {
			documentIds: WTU.arrayAsParam(documentIds)
		}));
	},
	downloadDocument: function (documentIds) {
		Sonicle.URLMgr.openFile(WTF.processBinUrl(this.mys.ID, 'DownloadTimetableRequestDocument', {
			raw: 1,
			documentIds: WTU.arrayAsParam(documentIds)
		}));
	}
});

