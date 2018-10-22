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
		'WTA.ux.grid.Attachments',
		'WTA.ux.data.SimpleSourceModel',
		'Sonicle.Bytes',
		'WTA.ux.UploadBar'
	],
	dockableConfig: {
		title: '{timetableRequest.tit}',
		iconCls: 'wtdrm-icon-timetable2-xs',
		width: 580,
		height: 360
	},
	fieldTitle: 'leaveRequestId',
	modelName: 'Sonicle.webtop.drm.model.TimetableRequest',
	
	constructor: function(cfg) {
		var me = this;
		me.callParent([cfg]);
		
		WTU.applyFormulas(me.getVM(), {
			fromDate: {
				bind: {bindTo: '{record.fromDate}'},
				get: function(val) {
					return (val) ? Ext.Date.clone(val): null;
				},
				set: function(val) {
					this.get('record').setFromDate(val);
				}
			},
			toDate: {
				bind: {bindTo: '{record.toDate}'},
				get: function(val) {
					return (val) ? Ext.Date.clone(val): null;
				},
				set: function(val) {
					this.get('record').setToDate(val);
				}
			}
		});
	},
	
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
											startDay: WT.getStartDay(),
											reference: 'fldfromdate',
											bind: '{fromDate}',
											fieldLabel: me.mys.res('timetableRequest.fld-fromDate.lbl')
										},
										{
											xtype: 'datefield',
											startDay: WT.getStartDay(),
											reference: 'fldtodate',
											bind: '{toDate}',
											fieldLabel: me.mys.res('timetableRequest.fld-toDate.lbl')
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
									xtype: 'textarea',
									reference: 'fldcancellationreason',
									bind: '{record.cancReason}',
									fieldLabel: me.mys.res('timetableRequest.fld-cancellationReason.lbl'),
									width: '555px',
									hidden: true
								}
							]
						}
					]
				}, {
					xtype: 'wtattachmentsgrid',
					title: me.mys.res('timetableRequest.tab.documents.tit'),
					bind: {
						store: '{record.documents}'
					},
					sid: me.mys.ID,
					uploadContext: 'TimetableRequestDocument',
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
	},
	initCxm: function () {
		var me = this;
	},	
	onViewLoad: function(s, success) {
		if(!success) return;
		var me = this,
				mo = me.getModel();
	
		if(mo.get('userId') === null) me.lref('flduser').setReadOnly(false);
		if(mo.get('cancRequest') === true) me.lref('fldcancellationreason').setHidden(false);
	
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
		}else if(me.isMode(me.MODE_EDIT)) {
			me.lref('flduser').setReadOnly(true);
			me.lref('fldcompany').setReadOnly(true);
			me.lref('fldmanager').setReadOnly(true);
		}
	},
	onViewInvalid: function (s, mo, errs) {
		var me = this;
		WTU.updateFieldsErrors(me.lref('form'), errs);
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
			url = WTF.processBinUrl(me.mys.ID, 'DownloadLeaveRequestDocument', {
				inline: !download,
				uploadId: uploadId
			});
		} else {
			url = WTF.processBinUrl(me.mys.ID, 'DownloadLeaveRequestDocument', {
				inline: !download,
				trId: me.getModel().getId(),
				attachmentId: rec.get('id')
			});
		}
		if (download) {
			Sonicle.URLMgr.downloadFile(url, {filename: name});
		} else {
			Sonicle.URLMgr.openFile(url, {filename: name});
		}
	}
});

