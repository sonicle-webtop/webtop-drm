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
Ext.define('Sonicle.webtop.drm.view.Opportunity', {
	extend: 'WTA.sdk.ModelView',
	requires: [
		'Sonicle.webtop.drm.model.Opportunity',
		'WTA.ux.data.SimpleSourceModel',
		'Sonicle.Bytes',
		'WTA.ux.UploadBar'
	],
	dockableConfig: {
		title: '{opportunity.tit}',
		iconCls: 'wtdrm-icon-opportunity-xs',
		width: 920,
		height: 500
	},
	fieldTitle: 'id',
	modelName: 'Sonicle.webtop.drm.model.Opportunity',
	
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
					fieldLabel: me.mys.res('opportunity.fld-nominative.lbl'),
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
					layout: {
						type: 'vbox', align: 'stretch'
					},
					title: me.mys.res('opportunity.general.tit'),
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
											fieldLabel: me.mys.res('opportunity.fld-company.lbl'),
											width: '420px'
										}),
										WTF.lookupCombo('id', 'desc', {
											reference: 'fromhour',
											bind: '{record.fromHour}',
											fieldLabel: me.mys.res('opportunity.fld-fromhour.lbl'),
											allowBlank: true,
											editable: true,
											forceSelection: false,
											queryMode: 'local',
											triggerAction: 'all',
											regex: new RegExp('^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$'),
											regexText: me.mys.res('gpLineHours.column.format.lbl'),
											tabIndex: 4,
											store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
												autoLoad: true
											})
										})
									]
								}, {
									xtype: 'wtform',
									reference: 'column2',
									modelValidation: true,
									defaults: {
										labelWidth: 120
									},
									items: [
										{
											xtype: 'datefield',
											startDay: WT.getStartDay(),
											reference: 'date',
											bind: '{record.date}',
											format: WT.getShortDateFmt(),
											tabIndex: 105,
											selectOnFocus: true,
											fieldLabel: me.mys.res('opportunity.fld-date.lbl'),
											width: '420px'
										},
										WTF.lookupCombo('id', 'desc', {
											reference: 'tohour',
											bind: '{record.toHour}',
											fieldLabel: me.mys.res('opportunity.fld-tohour.lbl'),
											allowBlank: true,
											editable: true,
											forceSelection: false,
											queryMode: 'local',
											triggerAction: 'all',
											regex: new RegExp('^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$'),
											regexText: me.mys.res('gpLineHours.column.format.lbl'),
											tabIndex: 5,
											store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
												autoLoad: true
											})
										})
									]
								}
							]
						}
					]
				},{
					title: me.mys.res('opportunity.visitreport.tit'),
					xtype: 'container',
					layout: 'border',
					flex: 1,
					width: '100%',
					items: [
					]
				},{
					title: me.mys.res('opportunity.notessignature.tit'),
					xtype: 'container',
					layout: 'border',
					flex: 1,
					width: '100%',
					items: [
					]
				},{
					title: me.mys.res('opportunity.opportunityactions.tit'),
					xtype: 'grid',
					reference: 'gpOpportunityActions',
					disabled: true,
					store: {
						autoLoad: false,
						model: 'Sonicle.webtop.drm.model.OpportunityAction',
						proxy: WTF.apiProxy(me.mys.ID, 'ManageGridOpportunityAction', null, {
							extraParams: {
								opportunityId: null
							}
						})
					},
					columns: [
						{
							header: me.res('gpOpportunityActions.description.lbl'),
							dataIndex: 'description',
							tdCls: 'resultTdCls',
							flex: 1
						}, {
							header: me.res('gpOpportunityActions.date.lbl'),
							dataIndex: 'date',
							xtype: 'datecolumn',
							format:'d-m-Y',
							tdCls: 'resultTdCls',
							flex: 1
						}, {
							header: me.res('gpOpportunityActions.fromHour.lbl'),
							dataIndex: 'fromHour',
							tdCls: 'resultTdCls',
							flex: 1
						}, {
							header: me.res('gpOpportunityActions.toHour.lbl'),
							dataIndex: 'toHour',
							tdCls: 'resultTdCls',
							flex: 1
						}, {
							header: me.res('gpOpportunityActions.place.lbl'),
							dataIndex: 'place',
							tdCls: 'resultTdCls',
							flex: 1
						}, {
							xtype: 'solookupcolumn',
							dataIndex: 'operatorId',
							store: Ext.create('Ext.data.Store', {
								autoLoad: true,
								model: 'WTA.model.Simple',
								proxy: WTF.proxy(me.mys.ID, 'LookupOperators')
							}),
							displayField: 'desc',
							header: me.res('gpOpportunityActions.operatorId.lbl'),
							tdCls: 'resultTdCls',
							flex: 1
						}, {
							xtype: 'solookupcolumn',
							dataIndex: 'activityId',
							store: Ext.create('Ext.data.Store', {
								autoLoad: true,
								model: 'WTA.model.ActivityLkp',
								//Rimuovere gli extraParams quando Matteo m fa la modifica
								proxy: WTF.proxy(WT.ID, 'LookupActivities', null, {
									extraParams: {
										profileId: WT.getVar('profileId')
									}
								})
							}),
							displayField: 'desc',
							header: me.res('gpOpportunityActions.activityId.lbl'),
							tdCls: 'resultTdCls',
							flex: 1
						}, {
							xtype: 'solookupcolumn',
							dataIndex: 'statusId',
							store: Ext.create('Ext.data.Store', {
								autoLoad: true,
								model: 'WTA.model.Simple',
								proxy: WTF.proxy(me.mys.ID, 'LookupDocStatuses')
							}),
							displayField: 'desc',
							header: me.res('gpOpportunityActions.statusId.lbl'),
							tdCls: 'resultTdCls',
							flex: 1
						}
					],
					tbar: [
						me.getAct('addOpportunityAction'),
						'-',
						me.getAct('editOpportunityAction'),
						me.getAct('deleteOpportunityAction')
					],
					listeners: {
						rowclick: function (s, rec) {
							me.updateDisabled('editOpportunityAction');
							me.updateDisabled('deleteOpportunityAction');
						},
						rowdblclick: function (s, rec) {
							me.editOpportunityActionUI(rec);
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

										tip.update(rec.get('subsequentActions'));
									}
								}
							});
						}
					}
				},{
					title: me.mys.res('opportunity.notessignature.tit'),
					xtype: 'container',
					layout: 'border',
					flex: 1,
					width: '100%',
					items: [
					]
				},{
					title: me.mys.res('opportunity.documents.tit'),
					xtype: 'grid',
					id: gpId,
					reference: 'gpDocument',
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
							header: me.mys.res('gpOpportunityDocument.filename.lbl'),
							flex: 3,
							listeners: {
								linkclick: function (s, idx, rec) {
									me.downloadDocument([rec.getId()]);
								}
							}
						}, {
							xtype: 'sobytescolumn',
							dataIndex: 'size',
							header: me.mys.res('gpOpportunityDocument.size.lbl'),
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
						uploadContext: 'UploadOpportunityDocument',
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
		me.on('beforerender', me.onBeforerender);
	},
	addOpportunityActionUI: function() {
		var me = this;
		me.addOpportunityAction({}, {
			callback: function(success, model) {
				if (success) me.gpOpportunityAction().getStore().reload();
			}
		});
	},
	addOpportunityAction: function (data, opts) {
		var me = this,
				vct = WT.createView(me.mys.ID, 'view.OpportunityAction');
		
		vct.getView().on('viewsave', function(s, success, model) {
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vct.show(false, function() {
			vct.getView().begin('new', {
				data: {
					opportunityId: me.getModel().get('id'),
					operatorId: me.lref('flduser').getValue()
				}
			});
		});
	},
	editOpportunityActionUI: function(rec) {
		var me = this,
				vct = WT.createView(me.mys.ID, 'view.OpportunityAction');
		
		vct.getView().on('viewsave', function(s, success, model) {
			if (success) me.gpOpportunityAction().getStore().reload();
		});
		vct.show(false, function() {
			vct.getView().begin('edit', {
				data: {
					id: rec.get('id')
				}
			});
		});
	},
	deleteOpportunityActionUI: function (rec) {
		var me = this,
				sto = me.gpOpportunityAction().getStore(),
				msg;
		if (rec) {
			msg = me.res('act.confirm.delete', Ext.String.ellipsis(rec.get('description'), 40));
		} else {
			msg = me.res('gpOpportunityActions.confirm.delete.selection');
		}
		WT.confirm(msg, function (bid) {
			if (bid === 'yes') {
				me.deleteOpportunityAction(rec.get('id'), {
					callback: function (success) {
						if (success)
							sto.remove(rec);
					}
				});
			}
		});
	},
	deleteOpportunityAction: function (id, opts) {
		opts = opts || {};
		var me = this;
		WT.ajaxReq(me.mys.ID, 'ManageOpportunityAction', {
			params: {
				crud: 'delete',
				ids: WTU.arrayAsParam(id)
			},
			callback: function (success, json) {
				Ext.callback(opts.callback, opts.scope || me, [success, json]);
			}
		});
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
		Sonicle.URLMgr.openFile(WTF.processBinUrl(this.mys.ID, 'DownloadOpportunityDocument', {
			documentIds: WTU.arrayAsParam(documentIds)
		}));
	},
	downloadDocument: function (documentIds) {
		Sonicle.URLMgr.openFile(WTF.processBinUrl(this.mys.ID, 'DownloadOpportunityDocument', {
			raw: 1,
			documentIds: WTU.arrayAsParam(documentIds)
		}));
	},
	onViewLoad: function(s, success) {
		if(!success) return;
		var me = this,
				mo = me.getModel();
		
		if(mo.get('operatorId') === null) me.lref('flduser').setReadOnly(false);
		
		if(me.isMode(me.MODE_EDIT)) {
			me.lref('gpOpportunityActions').setDisabled(false);
			WTU.loadWithExtraParams(me.lref('gpOpportunityActions').getStore(), {
				opportunityId: me.getModel().get('id')
			});
		}
	},
	gpOpportunityAction: function () {
		var me = this;
		
		return me.lref('gpOpportunityActions');
	},
	gpOpportunityActionSelected: function () {
		var me = this;
		
		return me.lref('gpOpportunityActions').getSelection()[0];
	},
	initActions: function () {
		var me = this;
		me.addAct('addOpportunityAction', {
			text: WT.res('act-add.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-add-xs',
			handler: function () {
				me.addOpportunityActionUI({
					callback: function (success) {
						if (success) {
							me.lref('gpOpportunityActions').getStore().reload();
						}
					}
				});
			},
			scope: me
		});
		me.addAct('editOpportunityAction', {
			text: WT.res('act-edit.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-edit-xs',
			disabled: true,
			handler: function () {
				var rec = me.gpOpportunityActionSelected();
				me.editOpportunityActionUI(rec);
			},
			scope: me
		});
		me.addAct('deleteOpportunityAction', {
			text: WT.res('act-delete.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-delete',
			disabled: true,
			handler: function () {
				var rec = me.gpOpportunityActionSelected();
				me.deleteOpportunityActionUI(rec);
			},
			scope: me
		});
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
	},
	onBeforerender: function (t, eOpts) {
		var me = this;
		
		WT.ajaxReq(me.ID, 'GetOpportunityConfigurationFields', {
			params: {},
			callback: function (success, json) {
				if(success) alert("Ok!");
			}
		});
	} 
});
