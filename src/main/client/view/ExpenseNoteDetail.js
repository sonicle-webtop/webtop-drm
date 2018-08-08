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
Ext.define('Sonicle.webtop.drm.view.ExpenseNoteDetail', {
	extend: 'WTA.sdk.ModelView',
	requires: [
		'Sonicle.webtop.drm.model.ExpenseNoteDetail'
	],
	dockableConfig: {
		title: '{expenseNoteDetail.tit}',
		iconCls: 'wtdrm-icon-expensenote-xs',
		width: 500,
		height: 370
	},
	fieldTitle: 'name',
	modelName: 'Sonicle.webtop.drm.model.ExpenseNoteDetail',
	initComponent: function () {
		var me = this;

		me.callParent(arguments);

		me.add({
			region: 'center',
			xtype: 'wtform',
			items: [
				{
					xtype: 'datefield',
					startDay: WT.getStartDay(),
					bind: '{record.date}',
					reference: 'flddate',
					fieldLabel: me.mys.res('expenseNoteDetail.fld-date.lbl'),
					width: '420px'
				},
				WTF.localCombo('id', 'desc', {
					bind: '{record.description}',
					reference: 'flddescription',
					autoLoadOnValue: true,
					store: {
						
					},
					fieldLabel: me.mys.res('expenseNoteDetail.fld-description.lbl'),
					width: '420px'
				}),
				{
					xtype: 'fieldcontainer',
					layout: 'hbox',
					defaults: {
						margin: '0 5 0 0'
					},
					items: [
						WTF.localCombo('id', 'desc', {
							reference: 'fldmasterdata',
							bind: '{record.masterdata}',
							autoLoadOnValue: true,
							store: {

							},
							fieldLabel: me.mys.res('expenseNoteDetail.fld-masterdata.lbl'),
							width: '390px'
						}),
						{
							xtype: 'button',
							iconCls: 'wtdrm-icon-notes-xs',
							handler: function() {
								alert("Ciao");
							}
						}
					]
				},
				{
					xtype: 'soplaceholderfield'
				},
				WTF.localCombo('id', 'desc', {
					bind: '{record.costType}',
					reference: 'fldcosttype',
					autoLoadOnValue: true,
					store: {
						
					},
					fieldLabel: me.mys.res('expenseNoteDetail.fld-costtype.lbl'),
					width: '420px'
				}),
				{
					xtype: 'fieldcontainer',
					layout: 'hbox',
					defaults: {
						margin: '0 5 0 0'
					},
					items: [
						{
							xtype: 'textfield',
							bind: '{record.total}',
							fieldLabel: me.mys.res('expenseNoteDetail.fld-total.lbl'),
							width: '250px'
						},
						WTF.localCombo('id', 'desc', {
							reference: 'fldvalue',
							bind: '{record.value}',
							autoLoadOnValue: true,
							store: {

							},
							width: '80px'
						}),
						{
							xtype: 'textfield',
							bind: '{record.change}',
							fieldLabel: me.mys.res('expenseNoteDetail.fld-change.lbl'),
							labelWidth: '50px',
							width: '220px'
						}
					]
				},
				{
					xtype: 'checkbox',
					bind: '{record.businessPayment}',
					boxLabel: me.mys.res('expenseNoteDetail.fld-businesspayment.lbl'),
					boxLabelAlign: 'before'
				},
				{
					xtype: 'textfield',
					bind: '{record.invoiceNumber}',
					fieldLabel: me.mys.res('expenseNoteDetail.fld-invoicenumber.lbl'),
					width: '420px'
				},
				{
					xtype: 'checkbox',
					bind: '{record.reinvoice}',
					boxLabel: me.mys.res('expenseNoteDetail.fld-reinvoice.lbl'),
					boxLabelAlign: 'before'
				}
			]
		});
	}
});
