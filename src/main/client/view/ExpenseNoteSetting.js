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
	extend: 'WTA.sdk.DockableView',
	dockableConfig: {
		title: '{expensenote.config.tit}',
		iconCls: 'wtdrm-icon-configuration-generalconfiguration-xs',
		width: 500,
		height: 500
	},
	fieldTitle: 'name',
	modelName: 'Sonicle.webtop.drm.model.Report',
	initComponent: function () {
		var me = this;
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
						WTF.lookupCombo('id', 'desc', {
							bind: '',
							store: Ext.create('Sonicle.webtop.drm.store.UserGroupType', {
								autoLoad: true
							}),
							fieldLabel: me.mys.res('expenseNote.settings.fld-profile.lbl'),
							width: 250
						}),
						{
							xtype: 'checkbox',
							boxLabel: me.mys.res('expenseNote.settings.fld-average.lbl')
						},
						{
							xtype: 'checkbox',
							boxLabel: me.mys.res('expenseNote.settings.fld-tracking.lbl')
						},
						{
							xtype: 'checkbox',
							boxLabel: me.mys.res('expenseNote.settings.fld-mail.lbl')
						},
						{
							xtype: 'checkbox',
							boxLabel: me.mys.res('expenseNote.settings.fld-cloud.lbl')
						},
						{
							xtype: 'checkbox',
							boxLabel: me.mys.res('expenseNote.settings.fld-calendar.lbl')
						}
					]
				},
				{
					title: me.mys.res('expenseNote.settings.type.tit'),
					xtype: 'container',
					layout: 'border',
					items: [
						{
							region: 'center',
							xtype: 'wtform',
							flex: 1,
							items: [
								{
									xtype: 'textfield',
									fieldLabel: me.mys.res('expenseNote.settings.type.fld-description.lbl')
								},
								{
									xtype: 'textfield',
									fieldLabel: me.mys.res('expenseNote.settings.type.fld-maxImport.lbl')
								},
								WTF.lookupCombo('id', 'desc', {
									bind: '',
									store: Ext.create('Sonicle.webtop.drm.store.UserGroupType', {
										autoLoad: true
									}),
									fieldLabel: me.mys.res('expenseNote.settings.type.fld-costType.lbl'),
									width: 250
								}),
								{
									xtpye: 'fieldcontainer',
									layout: 'hbox',
									items: [
										{
											xtype: 'checkbox',
											boxLabel: me.mys.res('expenseNote.settings.type.fld-costWithPresent.lbl')
										},
										{
											xtype: 'checkbox',
											boxLabel: me.mys.res('expenseNote.settings.type.fld-forPerson.lbl')
										}
									]
								},
								{
									xtype: 'checkbox',
									boxLabel: me.mys.res('expenseNote.settings.type.fld-costKm.lbl')
								},
								{
									xtype: 'checkbox',
									boxLabel: me.mys.res('expenseNote.settings.type.fld-costAdvance.lbl')
								},
								{
									xtype: 'checkbox',
									boxLabel: me.mys.res('expenseNote.settings.type.fld-costCurrency.lbl')
								}
							]
						},
						{
							region: 'south',
							split: true,
							xtype: 'grid',
							width: '100%',
							border: true,
							flex: 1,
							store: {
								fields: ['task', 'hour', 'active'],
								sorters: ['name', 'task'],
								data: [
									{task: 'Foo', hour: 10, active: true},
									{task: 'Soo', hour: 10, active: true},
									{task: 'Foo', hour: 10, active: true},
									{task: 'Foo', hour: 10, active: true},
									{task: 'Foo', hour: 10, active: true},
									{task: 'Foo', hour: 10, active: true},
									{task: 'Foo', hour: 10, active: true},
									{task: 'Foo', hour: 10, active: true},
									{task: 'Foo', hour: 10, active: true},
									{task: 'Foo', hour: 10, active: true}
								]
							},
							columns: [
								{xtype: 'rownumberer', text: 'Rif'},
								{text: 'Task Description', dataIndex: 'task', flex: 2},
								{text: 'Hour', dataIndex: 'hour', flex: 1}
							]
						}
					]
				},
				{
					title: me.mys.res('expenseNote.settings.actualCost.tit'),
					xtype: 'container',
					layout: 'border',
					items: [
						{
							region: 'center',
							xtype: 'wtform',
							flex: 1,
							items: [
								{
									xtype: 'textfield',
									fieldLabel: me.mys.res('expenseNote.settings.actualCost.fld-description.lbl')
								},
								{
									xtype: 'textfield',
									fieldLabel: me.mys.res('expenseNote.settings.actualCost.fld-maxImport.lbl')
								},
								WTF.lookupCombo('id', 'desc', {
									bind: '',
									store: Ext.create('Sonicle.webtop.drm.store.UserGroupType', {
										autoLoad: true
									}),
									fieldLabel: me.mys.res('expenseNote.settings.actualCost.fld-costType.lbl'),
									width: 250
								}),
								{
									xtype: 'checkbox',
									boxLabel: me.mys.res('expenseNote.settings.actualCost.fld-costWithPresent.lbl')
								},
								{
									xtype: 'checkbox',
									boxLabel: me.mys.res('expenseNote.settings.actualCost.fld-costKm.lbl')
								}
							]
						},
						{
							region: 'south',
							split: true,
							xtype: 'grid',
							width: '100%',
							border: true,
							flex: 1,
							store: {
								fields: ['task', 'hour', 'active'],
								sorters: ['name', 'task'],
								data: [
									{task: 'Foo', hour: 10, active: true},
									{task: 'Soo', hour: 10, active: true},
									{task: 'Foo', hour: 10, active: true},
									{task: 'Foo', hour: 10, active: true},
									{task: 'Foo', hour: 10, active: true},
									{task: 'Foo', hour: 10, active: true},
									{task: 'Foo', hour: 10, active: true},
									{task: 'Foo', hour: 10, active: true},
									{task: 'Foo', hour: 10, active: true},
									{task: 'Foo', hour: 10, active: true}
								]
							},
							columns: [
								{xtype: 'rownumberer', text: 'Rif'},
								{text: 'Task Description', dataIndex: 'task', flex: 2},
								{text: 'Hour', dataIndex: 'hour', flex: 1}
							]
						}
					]
				}
			]
		});
	}
});
