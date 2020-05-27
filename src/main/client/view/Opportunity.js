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
		'Sonicle.webtop.drm.ux.ContactGrid',
		'Sonicle.webtop.drm.model.Opportunity',
		'WTA.ux.grid.Attachments',
		'WTA.ux.data.SimpleSourceModel',
		'Sonicle.Bytes',
		'WTA.ux.UploadBar'
	],
	dockableConfig: {
		title: '{opportunity.tit}',
		iconCls: 'wtdrm-icon-opportunity-xs',
		width: 920,
		height: 572
	},
	fieldTitle: 'id',
	modelName: 'Sonicle.webtop.drm.model.Opportunity',
	
	activeType: 'opportunities',
	
	constructor: function(cfg) {
		var me = this;
		
		me.callParent([cfg]);
		
		WTU.applyFormulas(me.getVM(), {
			startDate: {
				bind: {bindTo: '{record.startDate}'},
				get: function(val) {
					return val ? Ext.Date.clone(val): null;
				},
				set: function(val) {
					this.get('record').setStartDate(val);
				}
			},
			startTime: {
				bind: {bindTo: '{record.startDate}'},
				get: function(val) {
					return (val) ? Ext.Date.clone(val): null;
				},
				set: function(val) {
					this.get('record').setStartTime(val);
				}
			},
			endDate: {
				bind: {bindTo: '{record.endDate}'},
				get: function(val) {
					return val ? Ext.Date.clone(val): null;
				},
				set: function(val) {
					this.get('record').setEndDate(val);
				}
			},
			endTime: {
				bind: {bindTo: '{record.endDate}'},
				get: function(val) {
					return val ? Ext.Date.clone(val): null;
				},
				set: function(val) {
					this.get('record').setEndTime(val);
				}
			}
		});
	},
	
	initComponent: function () {
		var me = this,
			gpId = Ext.id(null, 'gridpanel');
	
		var opportunityStructureFields = {
			mainFields: {
				executed_with: 
					WTF.localCombo('id', 'desc', {
					reference: 'executedWith',
					bind: '{record.executedWith}',
					anyMatch: true,
					selectOnFocus: true,
					width: '420px',
					hidden: true,
					store: {
						autoLoad: true,
						model: 'WTA.model.Simple',
						proxy: WTF.proxy(me.mys.ID, 'LookupOperators')
					}
				}), customer: 
					WTF.localCombo('id', 'desc', {
					reference: 'customer',
					bind: '{record.customerId}',
					autoLoadOnValue: true,
					selectOnFocus: true,
					width: '420px',
					hidden: true,
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
							}
						}
					},
					listeners: {
						select: function (s, r) {
							me.getViewModel().set('customerStatId', null);
							WTU.loadWithExtraParams(me.lref('statisticCustomer').getStore(), {
								realCustomer: r.id
							});
							WTU.loadWithExtraParams(me.lref('causal').getStore(), {
								masterDataId: r.id
							});
						}
					}
				}), statistic_customer:
					WTF.localCombo('id', 'desc', {
					reference: 'statisticCustomer',
					bind: '{record.customerStatId}',
					autoLoadOnValue: true,
					selectOnFocus: true,
					width: '420px',
					hidden: true,
					store: {
						model: 'WTA.model.Simple',
						proxy: WTF.proxy(me.mys.ID, (me.mys.getVar('useStatisticCustomer') === true) ? 'LookupStatisticCustomers' : 'LookupRealCustomers', null, {
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
											me.lookupReference('statisticCustomer').setValue(meta.selected);
										}
									}else {
										me.lookupReference('statisticCustomer').setValue(me.lookupReference('customer').getValue());
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
					}
				}), sector: {
					xtype: 'textfield',
					reference: 'sector',
					bind: '{record.sector}',
					width: '420px',
					hidden: true
				}, description: {
					xtype: 'textfield',
					reference: 'description',
					bind: '{record.description}',
					width: '420px',
					hidden: true
				}, place: {
					xtype: 'textfield',
					reference: 'place',
					bind: '{record.place}',
					width: '420px',
					hidden: true
				}, objective: {
					xtype: 'textareafield',
					reference: 'objective',
					bind: '{record.objective}',
					width: '420px',
					hidden: true
				}, causal: WTF.localCombo('id', 'desc', {
					reference: 'causal',
					bind: '{record.causalId}',
					autoLoadOnValue: true,
					selectOnFocus: true,
					width: '420px',
					hidden: true,
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
									if (rec.getId() !== me.lref('causal').getValue()) {
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
					}
				})/*, activity: WTF.remoteCombo('id', 'desc', {
					reference: 'activity',
					bind: '{record.activityId}',
					autoLoadOnValue: true,
					width: '420px',
					hidden: true,
					store: {
						model: 'WTA.model.ActivityLkp',
						//Rimuovere gli extraParams quando Matteo m fa la modifica
						proxy: WTF.proxy(me.mys.ID, 'LookupActivities', null, {
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
					}
				})*/, activity: WTF.localCombo('activityId', 'description', {
					reference: 'activity',
					bind: '{record.activityId}',
					autoLoadOnValue: true,
					width: '420px',
					hidden: true,
					store: {
						autoLoad: true,
						model: 'WTA.model.Simple',
						proxy: WTF.proxy(me.mys.ID, 'LookupActivities', null, {
							extraParams: {
								type: null
							}
						}),
						listeners: {
							beforeload: function(s, op) {
								WTU.applyExtraParams(op.getProxy(), {
									type: me.activeType
								});
							}
						},
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
					}
				}), interlocutors:{
					xtype: 'wtdrmcontactgrid',
					reference: 'interlocutors',
					sid: me.mys.ID,
					actionsInToolbar: false,
					width: '100%',
					height: 100,
					flex: 1,
					border: true,
					style: 'margin-bottom: 5px;',
					hidden: true,
					bind: {
						store: '{record.interlocutors}'
					},
					listeners: {
						pick: function (s, vals, recs) {
							var mo = me.getModel();
							mo.interlocutors().add({
								contactId: recs[0].getId(),
								desc: recs[0].get('desc')
							});
						}
					}
				}
			},
			visitReportFields: {
				objective: {
					xtype: 'textareafield',
					reference: 'objective2',
					bind: '{record.objective2}',
					width: '420px',
					hidden: true
				}, result: {
					xtype: 'textareafield',
					reference: 'result',
					bind: '{record.result}',
					width: '420px',
					hidden: true
				}, discoveries: {
					xtype: 'textareafield',
					reference: 'discoveries',
					bind: '{record.discoveries}',
					width: '420px',
					hidden: true
				}, customer_potential: {
					xtype: 'textareafield',
					reference: 'customerPotential',
					bind: '{record.customerPotential}',
					width: '420px',
					hidden: true
				}
			},
			notesSignatureFields: {
				notes: {
					xtype: 'textareafield',
					reference: 'notes',
					bind: '{record.notes}',
					width: '420px',
					hidden: true
				}, status: WTF.localCombo('id', 'desc', {
					reference: 'status',
					bind: '{record.statusId}',
					width: '420px',
					hidden: true,
					store: {
						autoLoad: true,
						model: 'WTA.model.Simple',
						proxy: WTF.proxy(me.mys.ID, 'LookupDocStatuses')
					}
				}), signed_by: WTF.localCombo('id', 'desc', {
					reference: 'signedBy',
					bind: '{record.signedBy}',
					anyMatch: true,
					selectOnFocus: true,
					width: '420px',
					hidden: true,
					store: {
						autoLoad: true,
						model: 'WTA.model.Simple',
						proxy: WTF.proxy(me.mys.ID, 'LookupOperators')
					}
				}), signature: {
					xtype: 'checkbox',
					reference: 'signature',
					bind: '{record.signature}',
					boxLabelAlign: 'before',
					hidden: true
				}, success: {
					xtype: 'checkbox',
					reference: 'success',
					bind: '{record.success}',
					boxLabelAlign: 'before',
					hidden: true
				}
			}
		};

		Ext.apply(me, {
			tbar: [
				me.addAct('printOpportunity', {
					text: null,
					tooltip: WT.res('act-print.lbl'),
					iconCls: 'wt-icon-print',
					hidden: !me.mys.getVar('opportunityEnablePrint'),
					handler: function() {
						me.printOpportunity(me.getModel().getId());
					}
				}),
				me.addAct('sendMail', {
					text: me.res('act-sendMail.lbl'),
					tooltip: null,
					iconCls: 'wtdrm-icon-mail-xs ',
					hidden: !me.mys.getVar('opportunityEnablePrint'),
					handler: function () {
						me.sendMail(me.getModel().getId());
					}
				}),
				'->',
				WTF.localCombo('id', 'desc', {
					reference: 'user',
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
										me.lref('company').getStore().load();
										me.lref('customer').getStore().load();
										me.lref('causal').getStore().load();
									}
								}
							}
						}
					},
					listeners: {
						select: function (s, r) {
							me.lref('company').getStore().load();
							me.lref('customer').getStore().load();
							me.lref('statisticCustomer').getStore().load();
							me.lref('causal').getStore().load();
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
			reference: 'mainPanel',
			items: [
				{
					xtype: 'container',
					layout: {
						type: 'vbox', align: 'stretch'
					},
					title: me.mys.res('opportunity.general.tit'),
					reference: 'general',
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
										labelWidth: 150
									},
									items: [
										WTF.localCombo('id', 'desc', {
											reference: 'company',
											bind: '{record.companyId}',
											autoLoadOnValue: true,
											tabIndex: 301,
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
																me.lookupReference('company').setValue(meta.selected);
															}
														}
													}
												}
											},
											fieldLabel: me.mys.res('opportunity.fld-company.lbl'),
											width: '420px'
										}),
										{
											xtype: 'fieldcontainer',
											fieldLabel: me.mys.res('opportunity.fld-startDate.lbl'),
											layout: 'hbox',
											defaults: {
												margin: '0 10 0 0'
											},
											items: [{
												xtype: 'datefield',
												bind: {
													value: '{startDate}'
												},
												startDay: WT.getStartDay(),
												format: WT.getShortDateFmt(),
												margin: '0 5 0 0',
												width: 105
											}, {
												xtype: 'timefield',
												bind: {
													value: '{startTime}'
												},
												format: WT.getShortTimeFmt(),
												margin: '0 5 0 0',
												width: 80
											}, {
												xtype: 'button',
												iconCls: 'wtcal-icon-now-xs',
												tooltip: me.mys.res('opportunity.btn-now.tip'),
												handler: function() {
													me.getModel().setStartTime(new Date());
												}
											}]
										}, {
											xtype: 'fieldcontainer',
											fieldLabel: me.mys.res('opportunity.fld-endDate.lbl'),
											layout: 'hbox',
											defaults: {
												margin: '0 10 0 0'
											},
											items: [{
												xtype: 'datefield',
												bind: {
													value: '{endDate}'
												},
												startDay: WT.getStartDay(),
												format: WT.getShortDateFmt(),
												margin: '0 5 0 0',
												width: 105
											}, {
												xtype: 'timefield',
												bind: {
													value: '{endTime}'
												},
												format: WT.getShortTimeFmt(),
												margin: '0 5 0 0',
												width: 80
											}, {
												xtype: 'button',
												iconCls: 'wtcal-icon-now-xs',
												tooltip: me.mys.res('opportunity.btn-now.tip'),
												handler: function() {
													me.getModel().setEndTime(new Date());
												}
											}]
										}
									]
								}, {
									xtype: 'wtform',
									reference: 'column2',
									modelValidation: true,
									defaults: {
										labelWidth: 150
									},
									items: [
										{
											xtype: 'soplaceholderfield'
										},
										{
											xtype: 'soplaceholderfield'
										},
										{
											xtype: 'soplaceholderfield'
										}
									]
								}
							]
						},
						{
							xtype: 'container',
							layout: 'border',
							refernceHolder: true,
							flex: 1,
							width: '100%',
							items: [
								Ext.create({
									xtype: 'wtform',
									reference: 'mainForm',
									modelValidation: true,
									region: 'center',
									defaults: {
										labelWidth: 150
									},
									items: [
									]
								})
							]
						}
					]
				},{
					title: me.mys.res('opportunity.visitreport.tit'),
					reference: 'visitreport',
					xtype: 'container',
					layout: 'border',
					refernceHolder: true,
					flex: 1,
					width: '100%',
					items: [
						Ext.create({
							xtype: 'wtform',
							reference: 'visitReportForm',
							modelValidation: true,
							region: 'center',
							defaults: {
								labelWidth: 150
							},
							items: [
							]
						})
					]
				},{
					title: me.mys.res('opportunity.notessignature.tit'),
					reference: 'notessignature',
					xtype: 'container',
					layout: 'border',
					refernceHolder: true,
					flex: 1,
					width: '100%',
					items: [
						Ext.create({
							xtype: 'wtform',
							reference: 'notesSignatureForm',
							modelValidation: true,
							region: 'center',
							defaults: {
								labelWidth: 150
							},
							items: [
							]
						})
					]
				},{
					xtype: 'wtattachmentsgrid',
					title: me.mys.res('opportunity.documents.tit'),
					bind: {
						store: '{record.documents}'
					},
					sid: me.mys.ID,
					uploadContext: 'OpportunityDocument',
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
		
		//Additional Opportunity Fields
		var tabIndex = 305;
		Ext.each(me.mys.opportunityRequiredFields.mainFields, function(element) {
			var cfg = opportunityStructureFields.mainFields[element.field];
			var field = null;
			cfg.fieldLabel = element.label;		//Field Label
			cfg.tabIndex = tabIndex;			//Tab Index
			cfg.hidden = !element.visible;		//Visible Field
			
			if(element.field === 'interlocutors'){
				cfg.title = element.label;			//Only for Interlocutors, Field Title
			}
			
			field = Ext.create(cfg);
			if(element.field !== 'interlocutors'){
				var fieldId=field.initialConfig.bind.substring(8,field.initialConfig.bind.length-1);
				if(element.visible === true) Sonicle.webtop.drm.model.Opportunity.getField(fieldId).allowNull = !element.required;	//Field Required, by Model (Only if Field is visible and Field is not 'interlocutors')
			}
			
			me.lref('mainForm').add(field);
			
			tabIndex++;
		});
		Ext.each(me.mys.opportunityRequiredFields.visitReportFields, function(element) {
			var cfg = opportunityStructureFields.visitReportFields[element.field];
			var field = null;
			cfg.fieldLabel = element.label;
			cfg.tabIndex = tabIndex;
			cfg.hidden = !element.visible;
			
			field = Ext.create(cfg);
			var fieldId=field.initialConfig.bind.substring(8,field.initialConfig.bind.length-1);
			if(element.visible === true) Sonicle.webtop.drm.model.Opportunity.getField(fieldId).allowNull = !element.required;
			
			me.lref('visitReportForm').add(field);
			
			tabIndex++;
		});
		Ext.each(me.mys.opportunityRequiredFields.notesSignatureFields, function(element) {
			var cfg = opportunityStructureFields.notesSignatureFields[element.field];
			var field = null;
			cfg.fieldLabel = element.label;
			cfg.tabIndex = tabIndex;
			cfg.hidden = !element.visible;
			
			field = Ext.create(cfg);
			var fieldId=field.initialConfig.bind.substring(8,field.initialConfig.bind.length-1);
			if(element.visible === true) Sonicle.webtop.drm.model.Opportunity.getField(fieldId).allowNull = !element.required;
			
			me.lref('notesSignatureForm').add(field);
			
			tabIndex++;
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
			url = WTF.processBinUrl(me.mys.ID, 'DownloadOpportunityDocument', {
				inline: !download,
				uploadId: uploadId
			});
		} else {
			url = WTF.processBinUrl(me.mys.ID, 'DownloadOpportunityDocument', {
				inline: !download,
				oId: me.getModel().getId(),
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
		
		if(mo.get('operatorId') === null) me.lref('user').setReadOnly(false);
		
		if(me.isMode(me.MODE_NEW)) {
			mo.set('statusId', me.mys.getVar('opportunityDefaultStatus'));
		}
		
		var isVisible = false;
		Ext.each(me.lref('mainForm').items.items, function(element) {
			if(element.isVisible() || !element.hidden) isVisible = true;
		});
		if(!isVisible) me.lref('general').tab.hide();
		
		isVisible = false;
		Ext.each(me.lref('visitReportForm').items.items, function(element) {
			if(element.isVisible() || !element.hidden) isVisible = true;
		});
		if(!isVisible) me.lref('visitreport').tab.hide();
		
		isVisible = false;
		Ext.each(me.lref('notesSignatureForm').items.items, function(element) {
			if(element.isVisible() || !element.hidden) isVisible = true;
		});
		if(!isVisible) me.lref('notessignature').tab.hide();
	},
	onViewClose: function(s) {
		s.mys.cleanupUploadedFiles(WT.uiid(s.getId()));
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
	},
	initCxm: function () {
		var me = this;
	},
	onViewInvalid: function (s, mo, errs) {
		var me = this;
		
		WTU.updateFieldsErrors(me.lref('mainForm'), errs);
		WTU.updateFieldsErrors(me.lref('visitReportForm'), errs);
		WTU.updateFieldsErrors(me.lref('notesSignatureForm'), errs);
	},
	printOpportunity: function(id) {
		var me = this;
		if(me.getModel().isDirty()) {
			WT.warn(WT.res('warn.print.notsaved'));
		} else {
			me.mys.printOpportunity([id]);
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
				WT.ajaxReq(me.mys.ID, 'PrepareSendOpportunityAsEmail', {
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
	}
});
