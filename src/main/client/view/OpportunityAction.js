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
Ext.define('Sonicle.webtop.drm.view.OpportunityAction', {
	extend: 'WTA.sdk.ModelView',
	requires: [
		'Sonicle.webtop.drm.ux.ContactGrid',
		'Sonicle.webtop.drm.model.OpportunityAction',
		'WTA.ux.grid.Attachments',
		'WTA.ux.data.SimpleSourceModel',
		'Sonicle.Bytes',
		'WTA.ux.UploadBar'
	],
	dockableConfig: {
		title: '{opportunityAction.tit}',
		iconCls: 'wtdrm-icon-opportunityaction-xs',
		width: 535,
		height: 470,
		modal: true,
		minimizable: false
	},
	fieldTitle: 'id',
	modelName: 'Sonicle.webtop.drm.model.OpportunityAction',
	
	constructor: function(cfg) {
		var me = this;
		me.callParent([cfg]);
	},
	
	initComponent: function () {
		var me = this,
			gpId = Ext.id(null, 'gridpanel');

		Ext.apply(me, {
			tbar: [
				'->',
				WTF.localCombo('id', 'desc', {
					reference: 'flduser',
					bind: '{record.operatorId}',
					anyMatch: true,
					selectOnFocus: true,
					store: {
						autoLoad: true,
						model: 'WTA.model.Simple',
						proxy: WTF.proxy(me.mys.ID, 'LookupOperators')
					},
					fieldLabel: me.mys.res('opportunityAction.fld-nominative.lbl'),
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
					xtype: 'container',
					title: me.mys.res('opportunityAction.general.tit'),
					layout: 'border',
					refernceHolder: true,
					items: [
						{
							xtype: 'container',
							region: 'north',
							height: '740px',
							layout: {
								type: 'vbox', align: 'stretch'
							},
							title: me.mys.res('opportunityAction.general.tit'),
							items: [
								Ext.create({
									xtype: 'wtform',
									reference: 'mainForm',
									modelValidation: true,
									defaults: {
										labelWidth: 90
									},
									items: [
										{
											xtype: 'fieldcontainer',
											fieldLabel: me.mys.res('opportunityAction.fld-status.lbl'),
											layout: 'hbox',
											defaults: {
												margin: '0 10 0 0',
												labelWidth: 45
											},
											items: [
												WTF.localCombo('id', 'desc', {
													reference: 'status',
													bind: '{record.statusId}',
													store: {
														autoLoad: true,
														model: 'WTA.model.Simple',
														proxy: WTF.proxy(me.mys.ID, 'LookupDocStatuses')
													},
													tabIndex: 401
												}), 
												{
													xtype: 'datefield',
													startDay: WT.getStartDay(),
													reference: 'date',
													bind: '{record.date}',
													format: WT.getShortDateFmt(),
													tabIndex: 402,
													selectOnFocus: true,
													fieldLabel: me.mys.res('opportunityAction.fld-date.lbl')
												}
											]
										},{
											xtype: 'fieldcontainer',
											fieldLabel: me.mys.res('opportunityAction.fld-fromHour.lbl'),
											layout: 'hbox',
											defaults: {
												margin: '0 10 0 0',
												labelWidth: 45
											},
											items: [
												WTF.lookupCombo('id', 'desc', {
													reference: 'fromhour',
													bind: '{record.fromHour}',
													allowBlank: true,
													editable: true,
													forceSelection: false,
													queryMode: 'local',
													triggerAction: 'all',
													regex: new RegExp('^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$'),
													regexText: me.mys.res('gpLineHours.column.format.lbl'),
													tabIndex: 403,
													store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
														autoLoad: true
													})
												}), 
												WTF.lookupCombo('id', 'desc', {
													reference: 'tohour',
													bind: '{record.toHour}',
													fieldLabel: me.mys.res('opportunityAction.fld-toHour.lbl'),
													allowBlank: true,
													editable: true,
													forceSelection: false,
													queryMode: 'local',
													triggerAction: 'all',
													regex: new RegExp('^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$'),
													regexText: me.mys.res('gpLineHours.column.format.lbl'),
													tabIndex: 404,
													store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
														autoLoad: true
													})
												})
											]
										}, {
											xtype: 'textfield',
											fieldLabel: me.mys.res('opportunityAction.fld-place.lbl'),
											bind: '{record.place}',
											width: '510px',
											tabIndex: 405,
											selectOnFocus: true
										}, WTF.remoteCombo('id', 'desc', {
											reference: 'activity',
											bind: '{record.activityId}',
											autoLoadOnValue: true,
											store: {
												model: 'WTA.model.ActivityLkp',
												//Rimuovere gli extraParams quando Matteo m fa la modifica
												proxy: WTF.proxy(WT.ID, 'LookupActivities', null, {
													extraParams: {
														profileId: WT.getVar('profileId')
													}
												}),
												filters: [{
													filterFn: function(rec) {
														if(rec.get('readOnly')) {
															if(rec.getId() !== me.lref('activity').getValue()) {
																return null;
															}
														}
														return rec;
													}
												}]
											},
											triggers: {
												clear: WTF.clearTrigger()
											},
											fieldLabel: me.mys.res('opportunityAction.fld-activity.lbl'),
											width: '510px',
											tabIndex: 406
										}), {
											xtype: 'textfield',
											bind: '{record.description}',
											fieldLabel: me.mys.res('opportunityAction.fld-description.lbl'),
											width: '510px',
											tabIndex: 407,
											selectOnFocus: true
										}, {
											xtype: 'textareafield',
											bind: '{record.subsequentActions}',
											fieldLabel: me.mys.res('opportunityAction.fld-subsequentActions.lbl'),
											labelAlign: 'top',
											anchor: '100%',
											tabIndex: 408
										}
									]
								})
							]
						}, 
						{
							region: 'center',
							xtype: 'wtdrmcontactgrid',
							title: me.mys.res('opportunityAction.gpActionInterlocutors.tit'),
							sid: me.mys.ID,
							actionsInToolbar: false,
							width: '100%',
							flex: 1,
							border: true,
							bind: {
								store: '{record.actionInterlocutors}'
							},
							listeners: {
								pick: function (s, vals, recs) {
									var mo = me.getModel();
									mo.actionInterlocutors().add({
										contactId: recs[0].getId(),
										desc: recs[0].get('desc')
									});
								}
							}
						}
					]
				},{
					xtype: 'wtattachmentsgrid',
					title: me.mys.res('opportunityAction.documents.tit'),
					bind: {
						store: '{record.actionDocuments}'
					},
					sid: me.mys.ID,
					uploadContext: 'OpportunityActionDocument',
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
	openAttachmentUI: function(rec, download) {
		var me = this,
				name = rec.get('name'),
				uploadId = rec.get('_uplId'),
				url;
		
		if (!Ext.isEmpty(uploadId)) {
			url = WTF.processBinUrl(me.mys.ID, 'DownloadOpportunityActionDocument', {
				inline: !download,
				uploadId: uploadId
			});
		} else {
			url = WTF.processBinUrl(me.mys.ID, 'DownloadOpportunityActionDocument', {
				inline: !download,
				oAId: me.getModel().getId(),
				attachmentId: rec.get('id')
			});
		}
		if (download) {
			Sonicle.URLMgr.downloadFile(url, {filename: name});
		} else {
			Sonicle.URLMgr.openFile(url, {filename: name});
		}
	},
	onViewLoad: function(s, success) {
		if(!success) return;
		var me = this,
				mo = me.getModel();
		
		if(mo.get('operatorId') === null) me.lref('flduser').setReadOnly(false);
		
		if(me.isMode(me.MODE_NEW)) {
			mo.set('statusId', me.mys.getVar('opportunityDefaultStatus'));
		}
	},
	onViewClose: function(s) {
		s.mys.cleanupUploadedFiles(WT.uiid(s.getId()));
	},
	initActions: function () {
		var me = this;
	},
	initCxm: function () {
		var me = this;
	},
	onViewInvalid: function (s, mo, errs) {
		var me = this;
		
		WTU.updateFieldsErrors(me.lref('mainForm'), errs);
	}
});
