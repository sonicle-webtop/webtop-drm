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
Ext.define('Sonicle.webtop.drm.view.TicketSetting', {
	extend: 'WTA.sdk.ModelView',
	dockableConfig: {
		title: '{ticket.config.tit}',
		iconCls: 'wtdrm-icon-configuration-generalconfiguration-xs',
		width: 500,
		height: 500
	},
	modelName: 'Sonicle.webtop.drm.model.TicketSetting',
	reloadOnClose: false,
	
	initComponent: function () {
		var me = this;
		me.callParent(arguments);
		me.add({
			region: 'center',
			xtype: 'tabpanel',
			items: [									
				{
					title: me.mys.res('ticketcategory.tit'),
					xtype: 'grid',
					reference: 'gpTicketCategory',
					bind: {
						store: '{record.categories}'
					},
					width: '100%',
					border: true,
					flex: 2,
					columns: [
						{
							dataIndex: 'externalId',
							editor: {
								xtype: 'textfield',
								allowBlank: true
							},
							header: me.mys.res('gpticketcategory.externalId.lbl'),
							flex: 1
						},
						{
							dataIndex: 'description',
							editor: {
								xtype: 'textfield',
								allowBlank: false
							},
							header: me.mys.res('gpticketcategory.description.lbl'),
							flex: 2
						}
					],
					tbar: [
						me.addAct('add', {
							text: WT.res('act-add.lbl'),
							tooltip: null,
							iconCls: 'wt-icon-add-xs',
							handler: function () {
								me.addCategory();
							},
							scope: me
						}),
						me.addAct('delete', {
							text: WT.res('act-delete.lbl'),
							tooltip: null,
							iconCls: 'wt-icon-delete',
							handler: function () {
								var sm = me.lref('gpTicketCategory').getSelectionModel();
								me.deleteCategory(sm.getSelection());
							},
							scope: me
						})
					],
					plugins: {
						ptype: 'cellediting',
						clicksToEdit: 1
					}
				},
				{
					xtype: 'wtform',
					title: me.mys.res('ticket.config.default.tit'),
					defaults: {
						labelWidth: 100
					},
					items: [
						WTF.localCombo('id', 'desc', {
							bind: '{record.defaultStatus}',
							store: {
								autoLoad: true,
								model: 'WTA.model.Simple',
								proxy: WTF.proxy(me.mys.ID, 'LookupDocStatuses')
							},
							fieldLabel: me.mys.res('ticket.config.fld-defaultStatus.lbl')
						}),
						WTF.lookupCombo('id', 'desc', {
							bind: '{record.defaultPriority}',
							store: Ext.create('Sonicle.webtop.drm.store.PriorityType', {
								autoLoad: true
							}),
							fieldLabel: me.mys.res('ticket.config.fld-defaultPriority.lbl')
						}),
						WTF.localCombo('id', 'desc', {
							bind: '{record.defaultTicketCategory}',
							store: {
								autoLoad: true,
								model: 'WTA.model.Simple',
								proxy: WTF.proxy(me.mys.ID, 'LookupTicketCategories')
							},
							fieldLabel: me.mys.res('ticket.config.fld-defaultTicketCategory.lbl'),
                            width: 350
						}),
						WTF.localCombo('id', 'desc', {
							bind: '{record.defaultCloseStatus}',
							store: {
								autoLoad: true,
								model: 'WTA.model.Simple',
								proxy: WTF.proxy(me.mys.ID, 'LookupDocStatuses')
							},
							fieldLabel: me.mys.res('ticket.config.fld-defaultCloseStatus.lbl')
						})
					]
				}
			]
		});
		me.on("beforeviewsave", me.onBeforeViewSave);
		me.on("viewsave", me.onViewSave);
	},
	addCategory: function () {
		var me = this;
		var gp = me.lref('gpTicketCategory'),
				sto = gp.getStore(),
				cep = gp.findPlugin('cellediting');


		cep.cancelEdit();

		sto.add(sto.createModel({
			externalId: null,
			description: null
		}));

		cep.startEditByPosition({row: sto.getCount(), column: 0});
	},
	deleteCategory: function (rec) {
		var me = this,
				grid = me.lref('gpTicketCategory'),
				sto = grid.getStore(),
				cellediting = grid.findPlugin('cellediting');

		WT.confirm(WT.res('confirm.delete'), function (bid) {
			if (bid === 'yes') {
				cellediting.cancelEdit();
				sto.remove(rec);
			}
		}, me);
	},
	onBeforeViewSave: function (s, mo) {		
		s.reloadOnClose = s.needsReload(mo);
	},
	onViewSave: function (s, success, mo) {
		var me = this;
		
		if(me.reloadOnClose)
			WT.confirm(me.mys.res('configuration.confirm.logout'), function (bid) {
				if (bid === 'yes') {
					WT.logout();
				}
			});
	},
	needsReload: function (mo){
		if(mo.isModified('defaultStatus') || mo.isModified('defaultPriority') 
			|| mo.isModified('defaultTicketCategory') || mo.isModified('defaultCloseStatus')) 
			return true;
		else 
			return false;
	}
});
