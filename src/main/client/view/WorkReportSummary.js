/*
 * webtop-contacts is a WebTop Service developed by Sonicle S.r.l.
 * Copyright (C) 2014 Sonicle S.r.l.
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
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, see http://www.gnu.org/licenses or write to
 * the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301 USA.
 *
 * You can contact Sonicle S.r.l. at email address sonicle@sonicle.com
 *
 * The interactive user interfaces in modified source and object code versions
 * of this program must display Appropriate Legal Notices, as required under
 * Section 5 of the GNU Affero General Public License version 3.
 *
 * In accordance with Section 7(b) of the GNU Affero General Public License
 * version 3, these Appropriate Legal Notices must retain the display of the
 * "Powered by Sonicle WebTop" logo. If the display of the logo is not reasonably
 * feasible for technical reasons, the Appropriate Legal Notices must display
 * the words "Powered by Sonicle WebTop".
 */
Ext.define('Sonicle.webtop.drm.view.WorkReportSummary', {
	extend: 'WTA.sdk.UIView',
	requires: [
		'Sonicle.String'
	],
	
	dockableConfig: {
		iconCls: 'wt-icon-print',
		width: 280,
		height: 230,
		modal: true,
		minimizable: false,
		maximizable: false
	},
	promptConfirm: false,
	writableOnly: false,
	
	viewModel: {
		data: {
			operatorId: null,
			companyId: null,
			from: new Date(new Date().getFullYear(), 0, 1),
			to: new Date(),
			docStatusId: null
		}
	},
	defaultButton: 'btnok',
	
	constructor: function(cfg) {
		var me = this;
		me.callParent([cfg]);
	},
	
	initComponent: function() {
		var me = this;
		
		Ext.apply(me, {
			buttons: [
				{
					reference: 'btnok',
					text: me.mys.res('act-printSummary.lbl'),
					tooltip: WT.res('act-print.lbl'),
					iconCls: 'wt-icon-print',
					handler: function() {
						me.okView();
					}
				}
			]
		});
		me.callParent(arguments);
		
		me.add({
			region: 'center',
			xtype: 'panel',
			items: [
				{
					xtype: 'container',
					layout: {
						type: 'vbox', align: 'stretch'
					},
					defaults: {
						margin: '5 5 0 5'
					},
					items: [
						WTF.localCombo('id', 'desc', {
							reference: 'flduser',
							bind: '{operatorId}',
							anyMatch: true,
							store: {
								autoLoad: true,
								model: 'WTA.model.Simple',
								proxy: WTF.proxy(me.mys.ID, 'LookupOperators'),
								listeners: {
									load: function (s) {
										if (s.loadCount === 1) {
											var meta = s.getProxy().getReader().metaData;
											if(meta.selected) {
												me.getViewModel().set('operatorId', meta.selected);
												WTU.loadWithExtraParams(me.lookupReference('fldcompany').getStore(), {
													operator: meta.selected
												});
											}
										}
									}
								}
							},
							listeners: {
								select: function (s, r) {
									WTU.loadWithExtraParams(me.lookupReference('fldcompany').getStore(), {
										operator: r.id
									});
								}
							},
							triggers: {
								clear: WTF.clearTrigger()
							},
							fieldLabel: me.mys.res('workReportSummary.fld-nominative.lbl')
						}),
						WTF.localCombo('id', 'desc', {
							reference: 'fldcompany',
							bind: '{companyId}',
							store: {
								autoLoad: false,
								model: 'WTA.model.Simple',
								proxy: WTF.proxy(me.mys.ID, 'LookupCompanies', null, {
									extraParams: {
										operator: null
									}
								}),
								listeners: {
									load: function (s) {
										var meta = s.getProxy().getReader().metaData;
										if (meta.selected) {
											me.getViewModel().set('companyId', meta.selected);
										}
									}
								}
							},
							fieldLabel: me.mys.res('workReportSummary.fld-company.lbl')
						}),
						{
							xtype: 'datefield',
							startDay: WT.getStartDay(),
							reference: 'fldfrom',
							bind: '{from}',
							fieldLabel: me.mys.res('workReportSummary.fld-fromDate.lbl')
						},
						{
							xtype: 'datefield',
							startDay: WT.getStartDay(),
							reference: 'fldto',
							bind: '{to}',
							fieldLabel: me.mys.res('workReportSummary.fld-toDate.lbl')
						},
						WTF.localCombo('id', 'desc', {
							reference: 'flddocstatus',
							bind: '{docStatusId}',
							store: {
								autoLoad: true,
								model: 'WTA.model.Simple',
								proxy: WTF.proxy(me.mys.ID, 'LookupDocStatuses'),
								listeners: {
									load: function (s) {
										me.getViewModel().set('docStatusId', me.mys.getVar('workReportDefaultStatus'));
									}
								}
							},
							triggers: {
								clear: WTF.clearTrigger()
							},
							fieldLabel: me.mys.res('workReportSummary.fld-status.lbl')
						})
					]
				}
			]
		});		
	},

	okView: function() {
		var me = this,
				vm = me.getVM();
		vm.set('result', 'ok');
		me.fireEvent('viewok', me, vm.get('operatorId'), vm.get('companyId'), vm.get('from'), vm.get('to'), vm.get('docStatusId'));
		me.closeView(false);
	}
});
