/* 
 * Copyright (C) 2024 Sonicle S.r.l.
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
 * display the words "Copyright (C) 2024 Sonicle S.r.l.".
 */

Ext.define('Sonicle.webtop.drm.ux.Opportunity', {
	extend: 'Sonicle.webtop.drm.ux.BaseContainer',
	alias: ['widget.wtdrmopportunity'],
	
	mys: null,
	layout: 'border',
	referenceHolder: true,
	
	initComponent: function() {
		var me = this;
		me.callParent(arguments);
		
		me.initActions();
		
		me.add(
			{
				region: 'north',
				xtype: 'wtdrmopportunitysearch',
				reference: 'filtersOpportunity',
				title: (me.getVar('opportunityTitle') == null  || me.getVar('opportunityTitle') == '') ? me.res('gpOpportunity.tit.lbl') : me.getVar('opportunityTitle'),
				iconCls: 'wtdrm-icon-opportunity',
				titleCollapse: true,
				collapsible: true,
				sid: me.ID,
				listeners: {
					search: function(s, query){
						me.reloadOpportunity(query);
					}
				}
			},
			{
				region: 'center',
				xtype: 'grid',
				reference: 'gpOpportunity',
				viewConfig: {
					getRowClass: function(r, rowIndex, rp, ds) {
						if(r.get('isTotallyClosed') === true){
							return 'opportunity-grid-row-closed';
						} else{
							if(r.get('actionId') !== 0){
								if(r.get('startDate') >= new Date()) 
									return 'opportunity-grid-row-notexpired';
								else 
									return 'opportunity-grid-row-expired';
							}
						}
					} 
				},
				store: {
					autoLoad: false,
					model: 'Sonicle.webtop.drm.model.GridOpportunities',
					proxy: WTF.apiProxy(me.ID, 'ManageGridOpportunity')
				},
				columns: [
					{
						dataIndex: 'id',
						header: me.res('gpOpportunity.id.lbl'),
						flex: 1,
						renderer: function (value, meta, record, rowIndex, colIndex, store) {
							switch(record.get('actionId')) {
								case 0:
									return value;
									break;
								default:
									return '';
									break;
							}
						}
					},
					{
						xtype: 'solookupcolumn',
						dataIndex: 'companyId',
						store: {
							autoLoad: true,
							model: 'WTA.model.Simple',
							proxy: WTF.proxy(me.ID, 'LookupAllCompanies')
						},
						header: me.res('gpOpportunity.company.lbl'),
						displayField: 'desc',
						flex: 3,
						hidden: true
					},
					{
						xtype: 'solookupcolumn',
						dataIndex: 'operatorId',
						store: {
							autoLoad: true,
							model: 'WTA.model.Simple',
							proxy: WTF.proxy(me.ID, 'LookupOperators')
						},
						header: me.res('gpOpportunity.user.lbl'),
						displayField: 'desc',
						flex: 3
					},
					{
						dataIndex: 'startDate',
						header: me.res('gpOpportunity.fromdate.lbl'),
						xtype: 'datecolumn',
						format: WT.getShortDateTimeFmt(),
						flex: 3
					},
					{
						dataIndex: 'endDate',
						header: me.res('gpOpportunity.todate.lbl'),
						xtype: 'datecolumn',
						format: WT.getShortDateTimeFmt(),
						flex: 3
					},
					{
						dataIndex: 'additionalInfo',
						header: me.res('gpOpportunity.additionalinfo.lbl'),
						flex: 20
					}
				],
				tbar: [
					me.getAct('opportunity', 'add'),
					'-',
					me.getAct('opportunity', 'edit'),
					me.getAct('opportunity', 'delete'),
					'-',
					me.getAct('opportunity', 'addAction'),
					me.getAct('opportunity', 'prepareActions')
				],
				listeners: {
					rowclick: function (s, rec) {
						me.getAct('opportunity', 'edit').setDisabled(false);
						me.getAct('opportunity', 'delete').setDisabled(false);

						if(rec.get('actionId') === 0){
							me.getAct('opportunity', 'addAction').setDisabled(false);
							me.getAct('opportunity', 'prepareActions').setDisabled(false);
						}else{
							me.getAct('opportunity', 'addAction').setDisabled(true);
							me.getAct('opportunity', 'prepareActions').setDisabled(true);
						}
					},
					rowdblclick: function (s, rec) {
						me.editUI(rec);
					}
				}
			}
		);
	},
	
	initActions: function() {
		me.addAct('opportunity', 'add', {
			text: me.res('act-addOpportunity.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-add',
			handler: function () {
				me.addOpportunity({
					callback: function (success) {
						if (success) {
							me.filtersOpportunity().extractData();
						}
					}
				});
			}
		});
		me.addAct('opportunity', 'edit', {
			text: WT.res('act-edit.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-edit',
			disabled: true,
			handler: function () {
				var sel = me.gpOpportunitySelected();
				me.editUI(sel);
			}
		});
		me.addAct('opportunity', 'delete', {
			text: WT.res('act-delete.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-delete',
			disabled: true,
			handler: function () {
				var sel = me.gpOpportunitySelected();
				me.deleteUI(sel);
			}
		});
		me.addAct('opportunity', 'addAction', {
			text: me.res('act-addOpportunityAction.lbl'),
			tooltip: null,
			iconCls: 'wt-icon-add',
			disabled: true,
			handler: function () {
				me.addOpportunityActionUI({
					callback: function (success) {
						if (success) {
							me.filtersOpportunity().extractData();
						}
					}
				});
			}
		});
		me.addAct('opportunity', 'prepareActions', {
			text: me.res('act-prepareOpportunityAction.lbl'),
			tooltip: null,
			iconCls: 'wtdrm-icon-initializeOpportunity',
			disabled: true,
			handler: function () {
				me.prepareOpportunityActions({
					callback: function (success) {
						if (success) {
							me.filtersOpportunity().extractData();
						}
					}
				});
			}
		});
	},

	addOpportunity: function (opts) {
		opts = opts || {};

		var me = this,
			fop = me.filtersOpportunity(),
			vw = WT.createView(me.ID, 'view.Opportunity', {
                swapReturn: true, 
                viewCfg: {
                    dockableConfig: {
                        title: (me.getVar('opportunityTitle') == null  || me.getVar('opportunityTitle') == '') ? me.res('opportunity.tit') : me.getVar('opportunityTitle')
                    }
                }
            });
		vw.on('viewsave', function (s, success, model) {
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vw.showView(function () {
            vw.begin('new', {
                data: {
                    operatorId: fop.getOperatorId()
                }
            });
        });
	},
	
	editUI: function (rec) {
		var me = this;
		if(rec.get('actionId') === 0) me.editOpportunityUI(rec);
		else me.editOpportunityActionUI(rec);
		
	},
	
	editOpportunityUI: function (rec) {
		var me = this;
		me.editOpportunity(rec.get('id'), {
			callback: function (success, model) {
				if (success) {
					this.gpOpportunity().getStore().load();
				} else {
					alert('error');
				}
			}
		});
	},
	
	editOpportunity: function (id, opts) {
		opts = opts || {};
		var me = this,
            vw = WT.createView(me.ID, 'view.Opportunity', {
                swapReturn: true, viewCfg: {
                    dockableConfig: {
                        title: (me.getVar('opportunityTitle') == null  || me.getVar('opportunityTitle') == '') ? me.res('opportunity.tit') : me.getVar('opportunityTitle')
                    }
                }
            });
		vw.on('viewsave', function (s, success, model) {
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vw.showView(function () {
			vw.begin('edit', {
				data: {
					id: id
				}
			});
		});
	},
	
	editOpportunityActionUI: function(rec) {
		var me = this;
		me.editOpportunityAction(rec.get('actionId'), {
			callback: function (success, model) {
				if (success) {
					this.gpOpportunity().getStore().load();
				} else {
					alert('error');
				}
			}
		});
	},
	
	editOpportunityAction: function(id, opts) {
		opts = opts || {};
		var me = this,
				vw = WT.createView(me.ID, 'view.OpportunityAction', {swapReturn: true});
		
		vw.on('viewsave', function (s, success, model) {
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vw.showView(function() {
			vw.begin('edit', {
				data: {
					id: id
				}
			});
		});
	},
	
	deleteUI: function (rec) {
		var me = this;
		if(rec.get('actionId') === 0) me.deleteOpportunityUI(rec);
		else me.deleteOpportunityActionUI(rec);
	},
	
	deleteOpportunityUI: function (rec) {
		var me = this,
				sto = me.gpOpportunity().getStore(),
				msg;
		if (rec) {
			msg = me.res('act.confirm.delete', Ext.String.ellipsis(rec.get('id'), 40));
		} else {
			msg = me.res('gpOpportunity.confirm.delete.selection');
		}
		WT.confirm(msg, function (bid) {
			if (bid === 'yes') {
				me.deleteOpportunity(rec.get('id'), {
					callback: function (success) {
						if (success) {
							me.filtersOpportunity().extractData();
						}
					}
				});
			}
		});
	},
	
	deleteOpportunity: function (id, opts) {
		opts = opts || {};
		var me = this;
		WT.ajaxReq(me.ID, 'ManageOpportunity', {
			params: {
				crud: 'delete',
				ids: WTU.arrayAsParam(id)
			},
			callback: function (success, json) {
				Ext.callback(opts.callback, opts.scope || me, [success, json]);
			}
		});
	},
	
	deleteOpportunityActionUI: function (rec) {
		var me = this,
				sto = me.gpOpportunity().getStore(),
				msg;
		if (rec) {
			msg = me.res('act.confirm.delete', Ext.String.ellipsis(rec.get('actionId'), 40));
		} else {
			msg = me.res('gpOpportunityActions.confirm.delete.selection');
		}
		WT.confirm(msg, function (bid) {
			if (bid === 'yes') {
				me.deleteOpportunityAction(rec.get('actionId'), {
					callback: function (success) {
						if (success) {
							me.filtersOpportunity().extractData();
						}
					}
				});
			}
		});
	},
	
	deleteOpportunityAction: function (id, opts) {
		opts = opts || {};
		var me = this;
		WT.ajaxReq(me.ID, 'ManageOpportunityAction', {
			params: {
				crud: 'delete',
				ids: WTU.arrayAsParam(id)
			},
			callback: function (success, json) {
				Ext.callback(opts.callback, opts.scope || me, [success, json]);
			}
		});
	},
	
	addOpportunityActionUI: function() {
		var me = this;
		me.addOpportunityAction({}, {
			callback: function(success, model) {
				if (success) {
					me.filtersOpportunity().extractData();
				}
			}
		});
	},
	
	addOpportunityAction: function (data, opts) {
		var me = this,
				vw = WT.createView(me.ID, 'view.OpportunityAction', {swapReturn: true});
		
		var sel = me.gpOpportunitySelected();
		
		vw.on('viewsave', function(s, success, model) {
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vw.showView(function() {
			vw.begin('new', {
				data: {
					opportunityId: sel.get('id'),
					operatorId: sel.get('operatorId')
				}
			});
		});
	},
	
	prepareOpportunityActions: function (opts) {
		opts = opts || {};

		var me = this,
				vw = WT.createView(me.ID, 'view.PrepareOpportunityActions', {swapReturn: true});
		
		var sel = me.gpOpportunitySelected();
		
		vw.on('viewsave', function(s, success, model) {
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vw.showView(function() {
			vw.begin('new', {
				data: {
					opportunityId: sel.get('id'),
					operatorId: sel.get('operatorId')
				}
			});
		});
	},
	
	opportunitySetting: function (opts) {
		opts = opts || {};

		var me = this,
				vw = WT.createView(me.ID, 'view.OpportunitySetting', {swapReturn: true});
		vw.on('viewsave', function (s, success, model) { 
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vw.showView(function () {
            vw.begin('edit', {
                data: {
                    id: 'oppo'
                }
            });
        });
	},
	
	printOpportunity: function(ids) {
		var me = this, url;
		url = WTF.processBinUrl(me.ID, 'PrintOpportunity', {ids: WTU.arrayAsParam(ids)});
		Sonicle.URLMgr.openFile(url, {filename: 'opportunity', newWindow: true});
	},
		
	reloadData: function (query) {
		var me = this,
				pars = {},
				sto;
		if (me.isActive() && me.itemActiveId() === 'oppo') {
			sto = me.gpOpportunity().getStore();
			if (query !== undefined)
				Ext.apply(pars, {query: Ext.JSON.encode(query)});
			WTU.loadWithExtraParams(sto, pars);
		} else {
			me.needsReload = true;
		}
	}
});

