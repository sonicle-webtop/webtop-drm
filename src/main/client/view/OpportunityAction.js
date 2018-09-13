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
												margin: '0 10 0 0'
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
													tabIndex: 101
												}), 
												{
													xtype: 'datefield',
													startDay: WT.getStartDay(),
													reference: 'date',
													bind: '{record.date}',
													format: WT.getShortDateFmt(),
													tabIndex: 102,
													selectOnFocus: true,
													fieldLabel: me.mys.res('opportunityAction.fld-date.lbl')
												}
											]
										},{
											xtype: 'fieldcontainer',
											fieldLabel: me.mys.res('opportunityAction.fld-fromHour.lbl'),
											layout: 'hbox',
											defaults: {
												margin: '0 10 0 0'
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
													tabIndex: 103,
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
													tabIndex: 104,
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
											tabIndex: 105,
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
											tabIndex: 106
										}), {
											xtype: 'textfield',
											bind: '{record.description}',
											fieldLabel: me.mys.res('opportunityAction.fld-description.lbl'),
											width: '510px',
											tabIndex: 107,
											selectOnFocus: true
										}, {
											xtype: 'textareafield',
											bind: '{record.subsequentActions}',
											fieldLabel: me.mys.res('opportunityAction.fld-subsequentActions.lbl'),
											labelAlign: 'top',
											anchor: '100%',
											tabIndex: 108
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
				}, {
					title: me.mys.res('opportunityAction.documents.tit'),
					xtype: 'grid',
					id: gpId,
					reference: 'gpDocument',
					bind: {
						store: '{record.actionDocuments}'
					},
					selModel: {
						type: 'checkboxmodel',
						mode: 'MULTI'
					},
					columns: [
						{
							xtype: 'solinkcolumn',
							dataIndex: 'fileName',
							header: me.mys.res('gpOpportunityActionDocument.filename.lbl'),
							flex: 3,
							listeners: {
								linkclick: function (s, idx, rec) {
									me.downloadDocument([rec.getId()]);
								}
							}
						}, {
							xtype: 'sobytescolumn',
							dataIndex: 'size',
							header: me.mys.res('gpOpportunityActionDocument.size.lbl'),
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
						uploadContext: 'UploadOpportunityActionDocument',
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
	
	gpDocument: function () {
		this.lref('gpDocument');
	},
	getSelectedFiles: function () {
		return this.lref('gpDocument').getSelection();
	},
	selectionIds: function (sel) {
		var ids = [];
		Ext.iterate(sel, function (rec) {
			ids.push(rec.getId());
		});
		return ids;
	},
	addDocument: function (uploadId, file) {
		var me = this;
		var gp = me.lref('gpDocument'),
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
				grid = me.lref('gpDocument'),
				sto = grid.getStore();

		WT.confirm(WT.res('confirm.delete'), function (bid) {
			if (bid === 'yes') {
				sto.remove(rec);
			}
		}, me);
	},
	openDocuments: function (documentIds) {
		Sonicle.URLMgr.openFile(WTF.processBinUrl(this.mys.ID, 'DownloadOpportunityActionDocument', {
			documentIds: WTU.arrayAsParam(documentIds)
		}));
	},
	downloadDocument: function (documentIds) {
		Sonicle.URLMgr.openFile(WTF.processBinUrl(this.mys.ID, 'DownloadOpportunityActionDocument', {
			raw: 1,
			documentIds: WTU.arrayAsParam(documentIds)
		}));
	},
	
	onViewLoad: function(s, success) {
		if(!success) return;
		var me = this,
				mo = me.getModel();
		
		if(mo.get('operatorId') === null) me.lref('flduser').setReadOnly(false);
	},
	
	initActions: function () {
		var me = this;

		me.addAct('openDocument', {
			tooltip: null,
			iconCls: 'wtdrm-icon-openDocument-xs',
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
			iconCls: 'wtdrm-icon-downloadDocument-xs',
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
			iconCls: 'wtdrm-icon-deleteDocument-xs',
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
	onViewInvalid: function (s, mo, errs) {
		var me = this;
		
		WTU.updateFieldsErrors(me.lref('mainForm'), errs);
	}
});
