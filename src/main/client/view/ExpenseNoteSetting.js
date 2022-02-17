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
Ext.define('Sonicle.webtop.drm.view.ExpenseNoteSetting', {
	extend: 'WTA.sdk.ModelView',
	dockableConfig: {
		title: '{expensenote.config.tit}',
		iconCls: 'wtdrm-icon-configuration',
		width: 650,
		height: 500
	},
	modelName: 'Sonicle.webtop.drm.model.ExpenseNoteSetting',
	reloadOnClose: false,
	
	initComponent: function () {
		var me = this;
		
		me.lookupStore = Ext.create('Ext.data.Store', {
			autoLoad: true,
			model: 'WTA.model.Simple',
			proxy: WTF.proxy(me.mys.ID, 'LookupCostType')
		});
		
		me.callParent(arguments);
		me.add({
			region: 'center',
			xtype: 'tabpanel',
			items: [
				{
					title: me.mys.res('expenseNote.settings.tit'),
					xtype: 'wtform',
					tbar: [
					],
					items: [
						{
							bind: '{record.averageMaximum}',
							xtype: 'checkbox',
							boxLabel: me.mys.res('expenseNote.settings.fld-average.lbl')
						},
						{
							xtype: 'numberfield',
							bind: '{record.kmCost}',
							fieldLabel: me.mys.res('expenseNote.settings.fld-kmCost.lbl'),
							minValue: 0
						},
						WTF.localCombo('id', 'desc', {
							bind: '{record.defaultCurrency}',
							fieldLabel: me.mys.res('expenseNote.settings.fld-defaultCurrency.lbl'),
							store: Ext.create('Sonicle.webtop.drm.store.Currency', {
								autoLoad: true
							})
						})
//						{
//							xtype: 'checkbox',
//							boxLabel: me.mys.res('expenseNote.settings.fld-tracking.lbl')
//						},
//						{
//							xtype: 'checkbox',
//							boxLabel: me.mys.res('expenseNote.settings.fld-mail.lbl')
//						},
//						{
//							xtype: 'checkbox',
//							boxLabel: me.mys.res('expenseNote.settings.fld-cloud.lbl')
//						},
//						{
//							xtype: 'checkbox',
//							boxLabel: me.mys.res('expenseNote.settings.fld-calendar.lbl')
//						}
					]
				},
				{
					title: me.mys.res('costtypes.tit'),
					xtype: 'grid',
					reference: 'gpCostType',
					bind: {
						store: '{record.costsType}'
					},
					width: '100%',
					border: true,
					flex: 2,
					columns: [
						{
							dataIndex: 'description',
							editor: {
								xtype: 'textfield',
								allowBlank: false,
								selectOnFocus: true
							},
							header: me.mys.res('gpcosttypes.description.lbl'),
							flex: 3
						},
						{
							dataIndex: 'maxImport',
							xtype: 'numbercolumn',
							format: '0.00',
							editor: {
								xtype: 'numberfield',
								selectOnFocus: true,
								allowDecimals: true,
								decimalSeparator: '.',
								decimalPrecision: 2,
								step: 0.01
							},
							header: me.mys.res('gpcosttypes.maxImport.lbl'),
							flex: 2
						},
						{
							xtype: 'solookupcolumn',
							dataIndex: 'costType',
							store: me.lookupStore,
							editor: Ext.create(WTF.lookupCombo('id', 'desc', {
								allowBlank: false,
								store: me.lookupStore
							})),
							displayField: 'desc',
							header: me.mys.res('gpcosttypes.type.lbl'),
							flex: 3
						},
						{
							dataIndex: 'withOthers',
							xtype: 'checkcolumn',
							editor: {
								xtype: 'checkbox',
								matchFieldWidth: true
							},
							header: me.mys.res('gpcosttypes.withOthers.lbl'),
							flex: 2
						},
						{
							dataIndex: 'perPerson',
							xtype: 'checkcolumn',
							editor: {
								xtype: 'checkbox',
								matchFieldWidth: true
							},
							header: me.mys.res('gpcosttypes.perPerson.lbl'),
							flex: 2
						},
						{
							dataIndex: 'km',
							xtype: 'checkcolumn',
							editor: {
								xtype: 'checkbox',
								matchFieldWidth: true
							},
							header: me.mys.res('gpcosttypes.km.lbl'),
							flex: 1
						},
						{
							dataIndex: 'advancePayment',
							xtype: 'checkcolumn',
							editor: {
								xtype: 'checkbox',
								matchFieldWidth: true
							},
							header: me.mys.res('gpcosttypes.advancePayment.lbl'),
							flex: 2
						},
						{
							dataIndex: 'exchange',
							xtype: 'checkcolumn',
							editor: {
								xtype: 'checkbox',
								matchFieldWidth: true
							},
							header: me.mys.res('gpcosttypes.exchange.lbl'),
							flex: 2
						}
					],
					tbar: [
						me.addAct('add', {
							text: WT.res('act-add.lbl'),
							tooltip: null,
							iconCls: 'wt-icon-add',
							handler: function () {
								me.addCostType();
							},
							scope: me
						}),
						me.addAct('delete', {
							text: WT.res('act-delete.lbl'),
							tooltip: null,
							iconCls: 'wt-icon-delete',
							handler: function () {
								var sm = me.lref('gpCostType').getSelectionModel();
								me.deleteCostType(sm.getSelection());
							},
							scope: me
						})
					],
					plugins: {
						ptype: 'cellediting',
						clicksToEdit: 1
					}

				}
			]
		});
	},
	
	addCostType: function () {
		var me = this;
		var gp = me.lref('gpCostType'),
				sto = gp.getStore(),
				cep = gp.findPlugin('cellediting');


		cep.cancelEdit();

		sto.add(sto.createModel({
			description: me.mys.res('gpcosttypes.defaultdescription.lbl'),
			maxImport: 0,
			costType: '',
			withOthers: false,
			perPerson: false,
			km: false,
			advancePayment: false,
			exchange: false
		}));

		cep.startEditByPosition({row: sto.getCount(), column: 0});
	},
	
	deleteCostType: function (rec) {
		var me = this,
				grid = me.lref('gpCostType'),
				sto = grid.getStore(),
				cellediting = grid.findPlugin('cellediting');

		WT.confirm(WT.res('confirm.delete'), function (bid) {
			if (bid === 'yes') {
				cellediting.cancelEdit();
				sto.remove(rec);
			}
		}, me);
	}
});
