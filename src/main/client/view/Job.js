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
Ext.define('Sonicle.webtop.drm.view.Job', {
	extend: 'WTA.sdk.ModelView',
	requires: [
		'Sonicle.webtop.drm.model.Job',
		'WTA.ux.data.SimpleSourceModel',
		'WTA.ux.grid.Attachments',
		'Sonicle.Bytes',
		'WTA.ux.UploadBar'
	],
	dockableConfig: {
		title: '{job.tit}',
		iconCls: 'wtdrm-icon-job-xs',
		width: 700,
		height: 600
	},
	fieldTitle: 'number',
	modelName: 'Sonicle.webtop.drm.model.Job',
	
	activeType: 'jobs',
	
	constructor: function(cfg) {
		var me = this;
		me.callParent([cfg]);
		
		WTU.applyFormulas(me.getViewModel(), {
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
			gpId = Ext.id(null, 'gridpanel'),	
			vm = me.getViewModel();
	   
		Ext.apply(me, {
			tbar: [
				'->',
				WTF.localCombo('id', 'desc', {
					reference: 'flduser',
					bind: '{record.operatorId}',
					anyMatch: true,
					selectOnFocus: true,
					tabIndex: 200,
					store: {
						autoLoad: true,
						model: 'WTA.model.Simple',
						proxy: WTF.proxy(me.mys.ID, 'LookupOperators'),
						listeners: {
							load: function (s) {
								if (me.isMode('new')) {
									if (s.loadCount === 1) {
										me.lref('fldcompany').getStore().load();
										me.lref('fldmasterdata').getStore().load();
										me.lref('fldcausal').getStore().load();
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
							me.lref('fldcausal').getStore().load();
						}
					},
					fieldLabel: me.mys.res('job.fld-nominative.lbl'),
					allowBlank: false,
					readOnly: true
				})
			]
		});

		me.callParent(arguments);
		me.initActions();
		me.initCxm();
		
		var main, dett, attachs;
		
		main = {
			xtype: 'wtform',
			reference: 'headfields',
			modelValidation: true,
			defaults: {
				labelWidth: 110
			},
			items: [				
				WTF.localCombo('id', 'desc', {
					reference: 'fldcompany',
					bind: '{record.companyId}',
					autoLoadOnValue: true,
					tabIndex: 201,
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
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: me.mys.res('job.fld-company.lbl'),
					anchor: '100%'
				}),
				WTF.remoteCombo('id', 'desc', {
					reference: 'fldmasterdata',
					bind: '{record.customerId}',
					autoLoadOnValue: true,
					tabIndex: 202,
					queryMode: 'remote',
					triggerAction: 'query',
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
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: me.mys.res('job.fld-realCustomer.lbl'),
					anchor: '100%',
					listeners: {
						select: function (s, r) {
							me.getViewModel().set('customerStatId', null);
							WTU.loadWithExtraParams(me.lref('fldstatmasterdata').getStore(), {
								realCustomer: r.id
							});
							WTU.loadWithExtraParams(me.lref('fldcausal').getStore(), {
								masterDataId: r.id
							});
						}
					}
				}),
				WTF.remoteCombo('id', 'desc', {
					reference: 'fldstatmasterdata',
					bind: '{record.customerStatId}',
					autoLoadOnValue: true,
					tabIndex: 203,
					store: {
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
										if (meta.selected) {
											me.lookupReference('fldstatmasterdata').setValue(meta.selected);
										}
									} else {
										me.lookupReference('fldstatmasterdata').setValue(me.lookupReference('fldmasterdata').getValue());
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
					},
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: me.mys.res('job.fld-customerStat.lbl'),
					anchor: '100%'
				}),
				{
					xtype: 'textfield',
					bind: '{record.title}',
					reference: 'fldtitle',
					fieldLabel: me.mys.res('job.fld-title.lbl'),
					anchor: '100%',
					selectOnFocus: true,
					tabIndex: 204
				},
				WTF.localCombo('activityId', 'description', {
					reference: 'fldactivity',
					bind: '{record.activityId}',
					tabIndex: 205,
					autoLoadOnValue: true,
					// selectOnFocus: true,
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
						}
					},
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: WT.res(me.mys.ID, 'job.fld-activity.lbl'),
					anchor: '100%',
					listeners: {
						select: function (s, r) {
							me.getModel().set('requireCustomer', r.data.customer);
						}
					}
				}),
				WTF.localCombo('id', 'desc', {
					reference: 'fldcausal',
					bind: '{record.causalId}',
					autoLoadOnValue: true,
					tabIndex: 208,
					selectOnFocus: true,
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
									if (rec.getId() !== me.lref('fldcausal').getValue()) {
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
					},
					fieldLabel: me.mys.res('job.fld-causal.lbl'),
					anchor: '100%'
				}),
				{
					xtype: 'formseparator'
				}, {
					xtype: 'fieldcontainer',
					fieldLabel: me.mys.res('job.fld-startDate.lbl'),
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
						width: 105,
						tabIndex: 206
					}, {
						xtype: 'timefield',
						bind: {
							value: '{startTime}'
						},
						format: WT.getShortTimeFmt(),
						margin: '0 5 0 0',
						width: 80,
						tabIndex: 206
					}, {
						xtype: 'button',
						iconCls: 'wtcal-icon-now-xs',
						tooltip: me.mys.res('job.btn-now.tip'),					
						handler: function() {
							me.getModel().setStartTime(new Date());
						}
					}]
				}, {
					xtype: 'fieldcontainer',
					fieldLabel: me.mys.res('job.fld-endDate.lbl'),
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
						width: 105,
						tabIndex: 207
					}, {
						xtype: 'timefield',
						bind: {
							value: '{endTime}'
						},
						format: WT.getShortTimeFmt(),
						margin: '0 5 0 0',
						width: 80,
						tabIndex: 208
					}, {
						xtype: 'button',
						iconCls: 'wtcal-icon-now-xs',
						tooltip: me.mys.res('job.btn-now.tip'),
						handler: function() {
							me.getModel().setEndTime(new Date());
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
						fieldLabel: me.mys.res('job.fld-timezone.lbl'),
						margin: 0,
						flex: 1,
						labelWidth: 75,
						tabIndex: 209
					}]
				}
			]
		};
		
		dett = {
			xtype: 'wtform',
			reference: 'dettfields',
			title: me.mys.res('job.dett.tit'),
			modelValidation: true,
			defaults: {
				labelWidth: 110
			},
			items: [
                {
                    xtype: 'textareafield',
                    reference: 'flddescription',
                    bind: '{record.description}',
                    fieldLabel: me.mys.res('job.fld-description.lbl'),
                    height: 130,
                    anchor: '100%',
                    tabIndex: 210
                }, {
                    xtype: 'grid',
                    reference: 'gpJobTicket',
                    title: me.mys.res('job.fld-ticket.lbl'),
                    cls: 'wtdrm-grid',
                    modelValidation: true,
                        viewConfig: {
                            getRowClass: function(r, rowIndex, rp, ds) {
                                return 'rows';
                        } 
                    },
                    store: {
                        autoLoad: false,
                        model: 'Sonicle.webtop.drm.model.GridTickets',
                        proxy: WTF.apiProxy(me.mys.ID, 'ManageGridTicket')
                    },
                    columns: [
                        {
                            dataIndex: 'number',
                            header: me.res('gpTicket.number.lbl'),
                            width: 100
                        },{
                            dataIndex: 'customerDescription',
                            header: me.res('gpTicket.realCustomer.lbl'),
                            flex: 3
                        },{
                            dataIndex: 'ticketCategoryDescription',
                            header: me.res('gpTicket.ticketCategory.lbl'),
                            flex: 3
                        },{
                            dataIndex: 'date',
                            header: me.res('gpTicket.date.lbl'),
                            xtype: 'datecolumn',
                            format: WT.getShortDateTimeFmt(),
                            width: 120
                        },{
                            dataIndex: 'statusDescription',
                            header: me.res('gpTicket.status.lbl'),
                            flex: 2
                        }
                    ],
                    listeners: {
                        rowdblclick: function (s, rec) {
                            me.mys.editTicketUI(rec);
                        }
                    }
                }
            ]
		};
		
		attachs = {
			xtype: 'wtattachmentsgrid',
			title: me.mys.res('job.attachments.tit'),
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
						attachs
					],
					flex: 1
				}
			]
		});		

		me.on('viewinvalid', me.onViewInvalid);
		me.on('viewload', me.onViewLoad);
		me.on('viewclose', me.onViewClose);
		vm.bind('{record.ticketId}', me.onTicketIdChanged, me);
	},
	
	onTicketIdChanged: function() {
		var me = this,
			mo = me.getModel();
        
		if (mo.get('ticketId') !== null) {
            var query = {
                ticketId: mo.get('ticketId')
            };
            
            var sto = me.lref('gpJobTicket').getStore();
            var pars = {};
            
			if (query !== undefined)
				Ext.apply(pars, {query: Ext.JSON.encode(query)});
			WTU.loadWithExtraParams(sto, pars);
            
            /*
			WT.ajaxReq(me.mys.ID, 'ManageTicket', {
				params: {
					crud: 'jobTicket',
					query: query
				},
				callback: function(success, model) {
					if (success) {
						me.lref('tcktNumber').setValue(model.data.number);
                        me.lref('tcktDate').setValue(model.data.date);
					}
				}
			});
            */
		}
	},
	
	openAttachmentUI: function(rec, download) {
		var me = this,
				name = rec.get('name'),
				uploadId = rec.get('_uplId'),
				url;
		
		if (!Ext.isEmpty(uploadId)) {
			url = WTF.processBinUrl(me.mys.ID, 'DownloadJobAttachment', {
				inline: !download,
				uploadId: uploadId
			});
		} else {
			url = WTF.processBinUrl(me.mys.ID, 'DownloadJobAttachment', {
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
			/*
			mo.set('applySignature', me.mys.getVar('defaultApplySignature'));
			mo.set('chargeTo', me.mys.getVar('defaultChargeTo'));
			mo.set('freeSupport', me.mys.getVar('defaultFreeSupport'));
			mo.set('docStatusId', me.mys.getVar('workReportDefaultStatus'));
			*/
		}
		
		me.lref('fldactivity').focus(true);
		
		if(mo.get('operatorId') === null) me.lref('flduser').setReadOnly(false);
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
		WTU.updateFieldsErrors(me.lref('headfields'), errs);
		WTU.updateFieldsErrors(me.lref('dettfields'), errs);
	}
});
