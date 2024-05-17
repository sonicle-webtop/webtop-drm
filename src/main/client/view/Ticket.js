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
Ext.define('Sonicle.webtop.drm.view.Ticket', {
	extend: 'WTA.sdk.ModelView',
	requires: [
		'Sonicle.webtop.drm.model.Ticket',
		'WTA.ux.data.SimpleSourceModel',
		'WTA.ux.grid.Attachments',
		'Sonicle.Bytes',
		'WTA.ux.UploadBar'
	],
	dockableConfig: {
		title: '{ticket.tit}',
		iconCls: 'wtdrm-icon-ticket',
		width: 700,
		height: 480
	},
	fieldTitle: 'number',
	modelName: 'Sonicle.webtop.drm.model.Ticket',
	showSave: true,
	
	blnOpenJob: false,
	
	constructor: function(cfg) {
		var me = this;
		me.callParent([cfg]);
		
		WTU.applyFormulas(me.getViewModel(), {
			date: {
				bind: {bindTo: '{record.date}'},
				get: function(val) {
					return val ? Ext.Date.clone(val): null;
				},
				set: function(val) {
					this.get('record').setDate(val);
				}
			},
			time: {
				bind: {bindTo: '{record.date}'},
				get: function(val) {
					return (val) ? Ext.Date.clone(val): null;
				},
				set: function(val) {
					this.get('record').setTime(val);
				}
			}
		});
	},
	
	initComponent: function () {
		var me = this,
			gpId = Ext.id(null, 'gridpanel');		
		
		/*
		me.callParent(arguments);
		me.initActions();
		me.initCxm();
		*/
	   
		Ext.apply(me, {
			tbar: [
				// me.getAct('ticket', 'addJob'),
				{
					xtype: 'button',	
					reference: 'btnaddjob',
					text: me.mys.res('ticket.btn-addJob.lbl'),
					iconCls: 'wt-icon-add',
					handler: function () {
						me.addJob(true);
					}
				},
				{
					xtype: 'button',	
					reference: 'btncloseticket',
					text: me.mys.res('ticket.btn-closeTicket.lbl'),
					iconCls: 'wt-icon-lock',
					handler: function () {
						me.closeTicket();
					}
				},
				'->',
				WTF.localCombo('id', 'desc', {
					reference: 'fldcompany',
					bind: '{record.companyId}',
					autoLoadOnValue: true,
					tabIndex: 200,
					selectOnFocus: true,
					allowBlank: false,
					listConfig: {
						escapeDisplay: true
					},
					store: {
						model: 'WTA.model.Simple',
						proxy: WTF.proxy(me.mys.ID, 'LookupCompanies', null, {
							extraParams: {
								operator: null
							}
						}),
						listeners: {
							beforeload: function(s,op) {
								WTU.applyExtraParams(op.getProxy(), {operator: me.getModel().get('fromOperatorId')});
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
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: me.mys.res('ticket.fld-company.lbl'),
					anchor: '100%'
				})
			]
		});
		
		me.callParent(arguments);
		me.initActions();
		me.initCxm();
	   
		var main, dett, dett2, dett3, attachs, jobs;
		
		main = {
			xtype: 'wtform',
			reference: 'headfields',
			modelValidation: true,
			defaults: {
				labelWidth: 110
			},
			items: [
				{
					xtype: 'fieldcontainer',
					fieldLabel: me.mys.res('ticket.fld-fromOperator.lbl'),
					layout: 'hbox',
					defaults: {
						margin: '0 72 0 0'
					},
					anchor: '100%',
					items: [
						WTF.localCombo(((me.blnNew) ? 'id' : 'userId'),  ((me.blnNew) ? 'desc' : 'displayName'), {
							reference: 'fldfromoperator',
							bind: '{record.fromOperatorId}',
							anyMatch: true,
							selectOnFocus: true,
							store: {
								autoLoad: true,
								model: 'WTA.model.Simple',
								proxy: WTF.proxy(me.mys.ID, ((me.blnNew) ? 'LookupOperators' : 'LookupAllOperators')),
								listeners: {
									load: function (s) {
										if (me.isMode('new')) {
											// set users of Webtop
											if (s.loadCount >= 1) {
												me.lookupReference('fldfromoperator').setValue(WT.getVar('userId'));

												me.lref('fldcompany').getStore().load();
												me.lref('fldmasterdata').getStore().load();
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
								}
							},
							// fieldLabel: me.mys.res('ticket.fld-fromOperator.lbl'),
							allowBlank: false,
							readOnly: ((me.blnNew) ? false : true),
							width: 200,
							tabIndex: 201
						}),
						WTF.localCombo('userId', 'displayName', {
							reference: 'fldtooperator',
							bind: '{record.toOperatorId}',
							anyMatch: true,
							selectOnFocus: true,
							listConfig: {
								escapeDisplay: true
							},
							store: {
								autoLoad: true,
								model: 'WTA.model.Simple',
								proxy: WTF.proxy(me.mys.ID, 'LookupAllOperators')
							},
							fieldLabel: me.mys.res('ticket.fld-toOperator.lbl'),
							allowBlank: false,
							readOnly: false,
							width: 300,
							tabIndex: 202
						})						
					]
				},				
				WTF.remoteCombo('id', 'desc', {
					reference: 'fldmasterdata',
					bind: '{record.customerId}',
					autoLoadOnValue: true,
					tabIndex: 203,
					queryMode: 'remote',
					triggerAction: 'query',
					listConfig: {
						escapeDisplay: true
					},
					store: {
						autoLoad: true,
						model: 'WTA.model.Simple',
						proxy: WTF.proxy(me.mys.ID, 'LookupRealCustomers', null, {
							extraParams: {
								operator: null
							}
						}),
						listeners: {
							beforeload: function(s,op) {
								WTU.applyExtraParams(op.getProxy(), {operator: me.getModel().get('fromOperatorId')});
							}
						}
					},
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: me.mys.res('ticket.fld-realCustomer.lbl'),
					anchor: '100%',
					listeners: {
						select: function (s, r) {
							me.getViewModel().set('customerStatId', null);
							WTU.loadWithExtraParams(me.lref('fldstatmasterdata').getStore(), {
								realCustomer: r.id
							});
						}
					}
				}),
				WTF.remoteCombo('id', 'desc', {
					reference: 'fldstatmasterdata',
					bind: '{record.customerStatId}',
					autoLoadOnValue: true,
					tabIndex: 204,
					store: {
						autoLoad: true,
						model: 'WTA.model.Simple',
						proxy: WTF.proxy(me.mys.ID, (me.mys.getVar('useStatisticCustomer') === true) ? 'LookupStatisticCustomers' : 'LookupRealCustomers', null, {
							extraParams: {
								realCustomer: null,
								operator: null
							}
						}),
						listeners: {
							load: function (s) {
								if (me.isMode('new')) {
									var meta = s.getProxy().getReader().metaData;
									if (meta){
										if ((meta.selected) && (me.getModel().get('fldstatmasterdata') === null)) {
											me.lookupReference('fldstatmasterdata').setValue(meta.selected);
										} else {
                                            me.lookupReference('fldstatmasterdata').setValue(me.getModel().get('customerStatId'));
                                        }
                                    } else {
										me.lookupReference('fldstatmasterdata').setValue(me.lookupReference('fldmasterdata').getValue());
									}
								}
							},
							beforeload: function(s,op) {
								WTU.applyExtraParams(op.getProxy(), {
									operator: me.getModel().get('fromOperatorId'),
									realCustomer: me.getModel().get('customerId')
								});
							}
						}
					},
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: me.mys.res('ticket.fld-customerStat.lbl'),
					anchor: '100%'
				}), {
					xtype: 'textfield',
					bind: '{record.title}',
					reference: 'fldtitle',
					fieldLabel: me.mys.res('ticket.fld-title.lbl'),
					anchor: '100%',
					selectOnFocus: true,
					tabIndex: 205
				}, {
					xtype: 'formseparator'
				}, {
					xtype: 'fieldcontainer',
					fieldLabel: me.mys.res('ticket.fld-date.lbl'),
					layout: 'hbox',
					defaults: {
						margin: '0 10 0 0'
					},
					items: [
						{
							xtype: 'datefield',
							bind: {
								value: '{date}'
							},
							// startDay: WT.getStartDay(),
							format: WT.getShortDateFmt(),
							margin: '0 5 0 0',
							width: 105,
							tabIndex: 206
						}, {
							xtype: 'timefield',
							bind: {
								value: '{time}'
							},
							format: WT.getShortTimeFmt(),
							margin: '0 5 0 0',
							width: 80,
							tabIndex: 207
						}, {
							xtype: 'button',
							iconCls: 'far fa-clock wt-theme-glyph',
							tooltip: me.mys.res('ticket.btn-now.tip'),					
							handler: function() {
								me.getModel().setTime(new Date());
							}
						}, {
							xtype: 'combo',
							bind: '{record.timezone}',
							typeAhead: true,
							queryMode: 'local',
							forceSelection: true,
							selectOnFocus: true,
							store: Ext.create('WTA.store.Timezone', {
								autoLoad: true
							}),
							valueField: 'id',
							displayField: 'desc',
							fieldLabel: me.mys.res('ticket.fld-timezone.lbl'),
							margin: 0,
							flex: 1,
							labelWidth: 75,
							tabIndex: 208
						}
					]
				}
			]
		};
		
		dett = {
			xtype: 'wtform',
			reference: 'dettfields',
			title: me.mys.res('ticket.dett.tit'),
			modelValidation: true,
			defaults: {
				labelWidth: 110
			},
			items: [
				{
					xtype: 'textareafield',
					bind: '{record.description}',
					fieldLabel: me.mys.res('ticket.fld-description.lbl'),
					height: 100,
					anchor: '100%',
					tabIndex: 209
				}, {
					xtype: 'formseparator'				
				}, 			 
				WTF.localCombo('id', 'desc', {
					// reference: 'fldticketcategory',
					bind: '{record.ticketCategoryId}',
					tabIndex: 210,
					autoLoadOnValue: true,
					selectOnFocus: true,
					listConfig: {
						escapeDisplay: true
					},
					store: {
						autoLoad: true,
						model: 'WTA.model.Simple',
						proxy: WTF.proxy(me.mys.ID, 'LookupTicketCategories'),
						listeners: {
							load: function (s) {
								if (me.isMode('new')) {
									var meta = s.getProxy().getReader().metaData;									
									if (meta){
										if (meta.selected) {
											// me.lookupReference('fldticketcategory').setValue(meta.selected);
											me.getModel().set('ticketCategoryId', meta.selected);
										}
									}
								}
							}
						}
					},
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: WT.res(me.mys.ID, 'ticket.fld-ticketCategory.lbl'),
					anchor: '100%'
				}),
				{
					xtype: 'fieldcontainer',
					fieldLabel: me.mys.res('ticket.fld-status.lbl'),
					layout: 'hbox',
					defaults: {
						margin: '0 72 0 0'
					},
					items: [
						WTF.localCombo('id', 'desc', {
							reference: 'fldstatus',
							bind: '{record.statusId}',
							tabIndex: 211,
							store: {
								autoLoad: true,
								model: 'WTA.model.Simple',
								proxy: WTF.proxy(me.mys.ID, 'LookupDocStatuses')
							},
							triggers: {
								clear: WTF.clearTrigger()
							},
							// fieldLabel: WT.res(me.sid, 'ticket.fld-status.lbl'),
							width: '200px'
						}),
						WTF.lookupCombo('id', 'desc', {
							reference: 'fldpriority',
							bind: '{record.priorityId}',
							store: Ext.create('Sonicle.webtop.drm.store.PriorityType', {
								autoLoad: true
							}),
							triggers: {
								clear: WTF.clearTrigger()
							},
							fieldLabel: WT.res(me.mys.ID, 'ticket.fld-priority.lbl'),
							width: '300px',
							tabIndex: 212
						})						
					]
				}				
			]
		};
		
		dett2 = {
			xtype: 'wtform',
			reference: 'dett2fields',
			title: me.mys.res('ticket.dett2.tit'),
			modelValidation: true,
			defaults: {
				labelWidth: 110
			},
			items: [
				{
					xtype: 'textfield',
					bind: '{record.release}',
					reference: 'fldrelease',
					fieldLabel: me.mys.res('ticket.fld-release.lbl'),
					anchor: '100%',
					selectOnFocus: true,
					tabIndex: 213
				},
				{
					xtype: 'textareafield',
					bind: '{record.environment}',
					fieldLabel: me.mys.res('ticket.fld-environment.lbl'),
					height: 140,
					anchor: '100%',
					tabIndex: 214
				}
			]
		};
		
		dett3 = {
			xtype: 'wtform',
			reference: 'dett3fields',
			title: me.mys.res('ticket.dett3.tit'),
			modelValidation: true,
			defaults: {
				labelWidth: 110
			},
			items: [
				{
					xtype: 'textareafield',
					bind: '{record.simulation}',
					fieldLabel: me.mys.res('ticket.fld-simulation.lbl'),
					height: 80,
					anchor: '100%',
					tabIndex: 215
				},
				{
					xtype: 'textareafield',
					bind: '{record.suggestion}',
					fieldLabel: me.mys.res('ticket.fld-suggestion.lbl'),
					height: 80,
					anchor: '100%',
					tabIndex: 216
				}
			]
		};
		
		attachs = {
			xtype: 'wtattachmentsgrid',
			title: me.mys.res('ticket.attachments.tit'),
			bind: {
				store: '{record.attachments}'
			},
			sid: me.mys.ID,
			uploadContext: 'EventAttachment',
			uploadTag: WT.uiid(me.getId()),
			dropElementId: null,
			highlightDrop: true,
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
					me.lref('tpnlinner').getLayout().setActiveItem(s);
				}
			}
		};
		
		jobs = {
			xtype: 'grid',
			title: me.mys.res('ticket.jobs.tit'),
			reference: 'gpTicketJobs',
			store: {
				autoLoad: false,
				model: 'Sonicle.webtop.drm.model.GridJobs',
				proxy: WTF.apiProxy(me.mys.ID, 'ManageGridTicketJobs')
			},
			columns: [
				{
					dataIndex: 'operatorDescription',
					header: me.res('gpJob.user.lbl'),
					flex: 3
				},{
					dataIndex: 'startDate',
					header: me.res('gpJob.startdate.lbl'),
					xtype: 'datecolumn',
					format: WT.getShortDateFmt(),
					flex: 2
				},{
					dataIndex: 'startDate',
					header: me.res('gpJob.starttime.lbl'),
					xtype: 'datecolumn',
					format: WT.getShortTimeFmt(),
					flex: 2
				},{
					dataIndex: 'endDate',
					header: me.res('gpJob.endtime.lbl'),
					xtype: 'datecolumn',
					format: WT.getShortTimeFmt(),
					flex: 2
				},{
					dataIndex: 'activityDescription',
					header: me.res('gpJob.activity.lbl'),
					flex: 3
				}
			],
            listeners: {
                rowdblclick: function (s, rec) {
                    me.mys.editJobUI(rec);
                }
            }		
		};
		
		me.add({
			region: 'center',
			xtype: 'container',
			layout: {
				type: 'vbox',
				align: 'stretch'
			},
			items: [
				main,
				{
					xtype: 'wttabpanel',
					reference: 'tpnlinner',
					activeTab: 0,
					deferredRender: false,
					items: [
						dett,
						dett2,
						dett3,
						attachs,
						jobs
					],
					flex: 1
				}
			]
		});		

		me.on('viewinvalid', me.onViewInvalid);
		me.on('viewload', me.onViewLoad);
		me.on('viewclose', me.onViewClose);
		me.on("beforeviewsave", me.onBeforeViewSave);
	},
	
	reloadJobs: function () {		
		var me = this;
		
		WTU.loadWithExtraParams(me.lref('gpTicketJobs').getStore(), {
			ticketId: me.getModel().get('ticketId')
		});
	},
	
	openAttachmentUI: function(rec, download) {
		var me = this,
				name = rec.get('name'),
				uploadId = rec.get('_uplId'),
				url;
		
		if (!Ext.isEmpty(uploadId)) {
			url = WTF.processBinUrl(me.mys.ID, 'DownloadTicketAttachment', {
				inline: !download,
				uploadId: uploadId
			});
		} else {
			url = WTF.processBinUrl(me.mys.ID, 'DownloadTicketAttachment', {
				inline: !download,
				wrId: me.getModel().getId(),
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
		
		if(me.isMode(me.MODE_NEW)) {
			mo.set('statusId', me.mys.getVar('ticketDefaultStatus'));
			mo.set('priorityId', me.mys.getVar('ticketDefaultPriority'));		
			mo.set('ticketCategoryId', me.mys.getVar('ticketDefaultTicketCategory'));
			// me.lref('btnaddjob').setDisabled(true);
			// me.getAct('ticket', 'addJob').setDisabled(true);
		} else {
			me.reloadJobs();
		}
		
		me.setDisabledBtnFromStatus();
		
		me.lref('fldtooperator').focus(true);
		
		if(mo.get('fromOperatorId') === null) me.lref('fldfromoperator').setReadOnly(false);
		
		mo.getProxy().getExtraParams().close = false;
	},
	
	onViewClose: function(s) {
		s.mys.cleanupUploadedFiles(WT.uiid(s.getId()));
	},
	
	onBeforeViewSave: function (s, mo) {	
		var me = this;
				
		me.setMode(me.MODE_EDIT);
		
		me.setDisabledBtnFromStatus();
	},
	
	setDisabledBtnFromStatus: function () {
		var me = this,
			mo = me.getModel(),
			closeTckt = false;
		
		closeTckt = ((mo.get('statusId') === parseInt(me.mys.getVar('ticketDefaultCloseStatus'))) ? true : false);
		if(me.isMode(me.MODE_NEW)) closeTckt = true;
		me.lref('btnaddjob').setDisabled(closeTckt);
		me.lref('btncloseticket').setDisabled(closeTckt);		
	},
	
	initActions: function () {
		var me = this;
		/*
		me.addAct('ticket', 'addJob', {
			text: me.mys.res('ticket.btn-addJob.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-add',
			handler: function () {
				me.mys.addJobFromTicket(
					me.getModel().get('toOperatorId'), 
					me.getModel().get('customerId'), 
					me.getModel().get('customerStatId'), 
					me.getModel().get('companyId'), 
					me.getModel().get('title'), 
					me.getModel().get('timezone'), 
					me.getModel().get('ticketId'), {
					callback: function (success, model) {
						if (success) {
							me.reloadJobs();
						} else {
							alert('error');
						}
					}
				});
			}
		});
		*/
	},
	
	initCxm: function () {
		var me = this;
	},
	
	addJob: function() {
		var me = this,
			mo = me.getModel();
		
		me.mys.addJobFromTicket(
			mo.get('toOperatorId'), 
			mo.get('customerId'), 
			mo.get('customerStatId'), 
			mo.get('companyId'), 
			mo.get('title'), 
			mo.get('timezone'), 
			mo.get('ticketId'), {
			callback: function (success) {
				if (success) {
					me.reloadJobs();
				} else {
					alert('error');
				}
			}
		});
	},
		
	closeTicket: function() {
		var me = this,
			mo = me.getModel();
			
		WT.confirm(me.res('ticket.confirm.statusclose'), function(bid) {
			if (bid === 'yes') {
				mo.getProxy().getExtraParams().close = true;
				me.blnOpenJob = true;
				me.saveView(true);		
			}
		});
		/*
		WT.confirm(WT.res('confirm.close'), function(bid) {
			if (bid === 'yes') {
				me.wait();
				WT.ajaxReq(me.mys.ID, 'ManageTicket', {
					params: {
						crud: 'end',
						ticketIds: WTU.arrayAsParam([rec.get('ticketId')])
					},
					callback: function(success, model) {
						me.unwait();
						if (success) {
							me.fireEvent('viewsave', me, true, model);
							// me.saveView(false);
							me.addJob(false);							
							me.closeView(false);
						}
					}
				});
			}
		}, me);
		*/
	},
	
	onViewInvalid: function (s, mo, errs) {
		var me = this;
		WTU.updateFieldsErrors(me.lref('headfields'), errs);
		WTU.updateFieldsErrors(me.lref('dett2fields'), errs);
		WTU.updateFieldsErrors(me.lref('dett3fields'), errs);
	}
});
