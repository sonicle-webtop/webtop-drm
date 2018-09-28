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
Ext.define('Sonicle.webtop.drm.view.OpportunitySetting', {
	extend: 'WTA.sdk.ModelView',
	requires: [
		'Sonicle.webtop.drm.model.OpportunityField',
		'Sonicle.grid.plugin.DDOrdering'
	],
	dockableConfig: {
		title: '{opportunity.config.tit}',
		iconCls: 'wtdrm-icon-configuration-generalconfiguration-xs',
		width: 500,
		height: 500
	},
	modelName: 'Sonicle.webtop.drm.model.OpportunitySetting',
	initComponent: function () {
		var me = this;
		me.callParent(arguments);
		me.initActions();
		me.add({
			region: 'center',
			xtype: 'tabpanel',
			items: [
				{
					xtype: 'tabpanel',
					title: me.mys.res('opportunity.configurationfield.tit'),
					items: [
						{
							xtype: 'grid',
							reference: 'generalFieldsGrid',
							title: me.mys.res('opportunity.configurationfield.main.tit'),
							modelValidation: true,
							flex: 1,
							border: true,
							viewConfig: {
								plugins: [{
									ptype: 'sogridviewddordering',
									orderField: 'order'
								}]
							},
							plugins: [
								Ext.create('Ext.grid.plugin.CellEditing', {
									clicksToEdit: 1
								})
							],
							bind: {
								store: '{record.generalFields}'
							},
							columns: [
								{
									dataIndex: 'fieldId',
									header: me.mys.res('gpOpportunityFields.field.lbl'),
									flex: 1
								},
								{
									dataIndex: 'label',
									flex: 1,
									editor: {
										xtype: 'textfield',
										selectOnFocus: true
									},
									header: me.mys.res('gpOpportunityFields.label.lbl')
								},
								{
									dataIndex: 'visible',
									xtype: 'checkcolumn',
									width: '25',
									editor: {
										xtype: 'checkbox',
										matchFieldWidth: true
									},
									header: me.mys.res('gpOpportunityFields.visible.lbl')
								},
								{
									dataIndex: 'required',
									xtype: 'checkcolumn',
									width: '25',
									editor: {
										xtype: 'checkbox',
										matchFieldWidth: true
									},
									header: me.mys.res('gpOpportunityFields.required.lbl')
								},
								{
									dataIndex: 'showOnGrid',
									xtype: 'checkcolumn',
									width: '25',
									editor: {
										xtype: 'checkbox',
										matchFieldWidth: true
									},
									header: me.mys.res('gpOpportunityFields.showOnGrid.lbl')
								}
							]
						},
						{
							xtype: 'grid',
							reference: 'visitReportFieldsGrid',
							title: me.mys.res('opportunity.configurationfield.visitreport.tit'),
							modelValidation: true,
							flex: 1,
							border: true,
							viewConfig: {
								plugins: [{
									ptype: 'sogridviewddordering',
									orderField: 'order'
								}]
							},
							plugins: [
								Ext.create('Ext.grid.plugin.CellEditing', {
									clicksToEdit: 1
								})
							],
							bind: {
								store: '{record.visitReportFields}'
							},
							columns: [
								{
									dataIndex: 'fieldId',
									header: me.mys.res('gpOpportunityFields.field.lbl'),
									flex: 1
								},
								{
									dataIndex: 'label',
									flex: 1,
									editor: {
										xtype: 'textfield',
										selectOnFocus: true
									},
									header: me.mys.res('gpOpportunityFields.label.lbl')
								},
								{
									dataIndex: 'visible',
									xtype: 'checkcolumn',
									width: '25',
									editor: {
										xtype: 'checkbox',
										matchFieldWidth: true
									},
									header: me.mys.res('gpOpportunityFields.visible.lbl')
								},
								{
									dataIndex: 'required',
									xtype: 'checkcolumn',
									width: '25',
									editor: {
										xtype: 'checkbox',
										matchFieldWidth: true
									},
									header: me.mys.res('gpOpportunityFields.required.lbl')
								},
								{
									dataIndex: 'showOnGrid',
									xtype: 'checkcolumn',
									width: '25',
									editor: {
										xtype: 'checkbox',
										matchFieldWidth: true
									},
									header: me.mys.res('gpOpportunityFields.showOnGrid.lbl')
								}
							]
						},
						{
							xtype: 'grid',
							reference: 'notesSignatureFieldsGrid',
							title: me.mys.res('opportunity.configurationfield.notessignature.tit'),
							modelValidation: true,
							flex: 1,
							border: true,
							viewConfig: {
								plugins: [{
									ptype: 'sogridviewddordering',
									orderField: 'order'
								}]
							},
							plugins: [
								Ext.create('Ext.grid.plugin.CellEditing', {
									clicksToEdit: 1
								})
							],
							bind: {
								store: '{record.notesSignatureFields}'
							},
							columns: [
								{
									dataIndex: 'fieldId',
									header: me.mys.res('gpOpportunityFields.field.lbl'),
									flex: 1
								},
								{
									dataIndex: 'label',
									flex: 1,
									editor: {
										xtype: 'textfield',
										allowBlank: false,
										selectOnFocus: true
									},
									header: me.mys.res('gpOpportunityFields.label.lbl')
								},
								{
									dataIndex: 'visible',
									xtype: 'checkcolumn',
									editor: {
										xtype: 'checkbox',
										matchFieldWidth: true
									},
									header: me.mys.res('gpOpportunityFields.visible.lbl')
								},
								{
									dataIndex: 'required',
									xtype: 'checkcolumn',
									editor: {
										xtype: 'checkbox',
										matchFieldWidth: true
									},
									header: me.mys.res('gpOpportunityFields.required.lbl')
								},
								{
									dataIndex: 'showOnGrid',
									xtype: 'checkcolumn',
									width: '25',
									editor: {
										xtype: 'checkbox',
										matchFieldWidth: true
									},
									header: me.mys.res('gpOpportunityFields.showOnGrid.lbl')
								}
							]
						}
					],
					tbar: [
						me.getAct('initializeFields')
					]
				}
			]
		});
	},
	initActions: function () {
		var me = this;
		
		me.addAct('initializeFields', {
			text: me.mys.res('act-initializefields.lbl'),
			tooltip: null,
			iconCls: 'wtdrm-icon-opportunity-initializefields-xs',
			handler: function () {
				WT.confirm(me.mys.res('opportunity.configurationfield.confirm.initializefields'), function(bid) {
					if (bid === 'yes') {
						WT.ajaxReq(me.mys.ID, 'InitializeOpportunityFields', {
							params: {},
							callback: function (success, json) {
								if(success){
									me.closeView(true);
								}
							}
						});
					}
				}, this);
			}
		});
	}
});
