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
Ext.define('Sonicle.webtop.drm.view.ExpenseNote', {
	extend: 'WTA.sdk.ModelView',
	dockableConfig: {
		title: '{expenseNote.tit}',
		iconCls: 'wtdrm-icon-company-xs',
		width: 700,
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
					title: me.mye.res('WorkReport.tit'),
					xtype: 'panel',
					layout: {
						type: 'vbox',
						align: 'stretch'
					},
					defaults: {
						margin: '0 0 10 0'
					},
					items: [
						{
							xtype: 'container',
							layout: 'column',
							flex: 1,
							items: [
								{
									xtype: 'wtform',
									items: [
										WTF.lookupCombo('id', 'desc', {
											bind: '',
											store: Ext.create('Sonicle.webtop.drm.store.UserGroupType', {
												autoLoad: true
											}),
											fieldLabel: me.mys.res('expenseNote.fld-company.lbl')
										}),
										{
											xtype: 'textfield',
											fieldLabel: me.mys.res('expenseNote.fld-description.lbl')
										},
										{
											xtype: 'datefield',
											fieldLabel: me.mys.res('expenseNote.fld-fromDate.lbl')
										},
										{
											xtype: 'fieldcontainer',
											layout: 'hbox',
											defaults: {
												margin: '0 5 0 0'
											},
											items: [
												{
													xtype: 'textfield',
													fieldLabel: me.mys.res('expenseNote.fld-totCurrency.lbl')
												},
												WTF.lookupCombo('id', 'desc', {
													bind: '',
													store: Ext.create('Sonicle.webtop.drm.store.UserGroupType', {
														autoLoad: true
													}),
													width: 50
												}),
												{
													xtype: 'button'
												}
											]
										},
										WTF.lookupCombo('id', 'desc', {
											bind: '',
											store: Ext.create('Sonicle.webtop.drm.store.StatusType', {
												autoLoad: true
											}),
											fieldLabel: me.mys.res('expenseNote.fld-status.lbl')

										})
									]
								},
								{
									xtype: 'wtform',
									items: [
										WTF.lookupCombo('id', 'desc', {
											bind: '',
											store: Ext.create('Sonicle.webtop.drm.store.UserGroupType', {
												autoLoad: true
											}),
											fieldLabel: me.mys.res('expenseNote.fld-nominative.lbl')

										}),
										{
											xtype: 'soplaceholderfield'
										},
										{
											xtype: 'datefield',
											fieldLabel: me.mys.res('expenseNote.fld-toDate.lbl')
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
							xtype: 'tabpanel',
							flex: 1,
							items: [
								{
									xtype: 'grid',
									reference: 'gpExpenseNoteDetail',
									title: me.mys.res('expenseNote.details.tit'),
									iconCls: '',
									border: true,
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
								},
								{
									xtype: 'grid',
									reference: '',
									title: me.mys.res('expenseNote.consumptive.tit'),
									iconCls: '',
									border: true,
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
				},
				{
					title: me.mys.res('expenseNote.documents.tit')
				}
			]
		});
	}
});

