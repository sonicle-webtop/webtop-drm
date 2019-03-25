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
		'Sonicle.webtop.drm.model.ExpenseNoteDetail',
		'Sonicle.webtop.drm.model.ExpenseNoteDetailDocument',
		'Sonicle.webtop.drm.model.CostType'
	],
	dockableConfig: {
		title: '{expenseNoteDetail.tit}',
		iconCls: 'wtdrm-icon-expensenote-xs',
		width: 450,
		height: 390
	},
	fieldTitle: 'name',
	modelName: 'Sonicle.webtop.drm.model.ExpenseNoteDetail',
	initComponent: function () {
		var me = this,
			gpId = Ext.id(null, 'gridpanel');

		me.callParent(arguments);

		me.add({
			region: 'center',
			xtype: 'wttabpanel',
			items: [
				{
					title: me.mys.res('expenseNoteDetail.tab.detail'),
					xtype: 'container',
					layout: {
						type: 'vbox', align: 'stretch'
					},
					refernceHolder: true,
					items: [
						Ext.create({
							xtype: 'wtform',
							reference: 'mainForm',
							modelValidation: true,
							defaults: {
								labelWidth: 100
							},
							items: [
								{
									xtype: 'datefield',
									startDay: WT.getStartDay(),
									bind: '{record.date}',
									reference: 'flddate',
									fieldLabel: me.mys.res('expenseNoteDetail.fld-date.lbl'),
									width: 210
								},
								{
									xtype: 'textfield',
									bind: '{record.description}',
									reference: 'flddescription',
									fieldLabel: me.mys.res('expenseNoteDetail.fld-description.lbl'),
									width: 420
								},
								WTF.localCombo('id', 'desc', {
									reference: 'fldmasterdata',
									bind: '{record.masterdata}',
									autoLoadOnValue: true,
									selectOnFocus: true,
									store: {
										autoLoad: true,
										model: 'WTA.model.Simple',
										proxy: WTF.proxy(me.mys.ID, 'LookupCustomersSuppliers', null, {
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
									fieldLabel: me.mys.res('expenseNoteDetail.fld-masterdata.lbl'),
									width: 420
								}),
								{
									xtype: 'soplaceholderfield'
								},
								WTF.localCombo('id', 'description', {
									bind: '{record.costType}',
									reference: 'fldcosttype',
									autoLoadOnValue: true,
									selectOnFocus: true,
									forceSelection: true,
									store: {
										autoLoad: true,
										model: 'Sonicle.webtop.drm.model.CostType',
										proxy: WTF.proxy(me.mys.ID, 'LookupCostTypes')
									},
									listeners: {
										select: function (s, r) {
											me.lref('fldkm').setHidden(true);
											me.lref('fldkm').setValue(null);
											
											me.lref('fldwithothers').setHidden(true);
											me.lref('fldwithothers').setValue(null);	
											
											if(me.lref('fldcurrency').getValue() === me.mys.getVar('expenseNoteDefaultCurrency')){
												me.lref('cntTot').setHidden(true);
												
												me.lref('fldchange').setReadOnly(true);
												me.lref('fldchange').setValue(1);
											}else{
												me.lref('fldchange').setReadOnly(false);
											}
											
											if(r.get('withOthers') === true)
												me.lref('fldwithothers').setHidden(false);
											if(r.get('km') === true)
												me.lref('fldkm').setHidden(false);
											if(r.get('exchange') === true){
												me.lref('cntTot').setHidden(false);
												me.lref('fldchange').setReadOnly(false);
											}
										}
									},
									fieldLabel: me.mys.res('expenseNoteDetail.fld-costtype.lbl'),
									width: 420
								}),
								{
									xtype: 'textfield',
									bind: '{record.withOthers}',
									reference: 'fldwithothers',
									fieldLabel: me.mys.res('expenseNoteDetail.fld-withothers.lbl'),
									width: 420,
									hidden: true
								},
								{
									xtype: 'numberfield',
									bind: '{record.km}',
									reference: 'fldkm',
									fieldLabel: me.mys.res('expenseNoteDetail.fld-km.lbl'),
									width: 210,
									hidden: true,
									listeners: {
										blur: function (t, e, o) {	
											var val1 = t.getValue();
											var val2 = me.mys.getVar('expenseNoteKmCost');
											var val3 = me.lref('fldchange').getValue();
											
											var tot1 = val1*val2;

											me.lref('fldtotal').setValue(tot1);
											me.lref('fldtotaldoc').setValue(tot1*val3);
										}
									}
								},
								{
									xtype: 'fieldcontainer',
									fieldLabel: me.mys.res('expenseNoteDetail.fld-total.lbl'),
									layout: 'hbox',
									defaults: {
										margin: '0 5 0 0'
									},
									items: [
										{
											xtype: 'numberfield',
											reference: 'fldtotal',
											bind: '{record.total}',
											width: 85,
											listeners: {
												blur: function (t, e, o) {	
													var val1 = t.getValue();
													var val2 = me.lref('fldchange').getValue();
													
													me.lref('fldtotaldoc').setValue(val1*val2);
												}
											}
										},
										WTF.localCombo('id', 'desc', {
											reference: 'fldcurrency',
											bind: '{record.currency}',
											autoLoadOnValue: true,
											store: Ext.create('Sonicle.webtop.drm.store.Currency', {
												autoLoad: true
											}),
											listeners: {
												select: function (s, r) {	
													var rec = me.lref('fldcosttype').getStore().getById(me.lref('fldcosttype').getValue());
														
													if(rec === null){
														me.lref('cntTot').setHidden(true);
														me.lref('fldchange').setReadOnly(true);
														me.lref('fldchange').setValue(1);
													
														if(r.id !== me.mys.getVar('expenseNoteDefaultCurrency')){
															me.lref('fldchange').setReadOnly(false);
															me.lref('cntTot').setHidden(false);
														}
													}else if(rec.get('exchange') === false){
														me.lref('cntTot').setHidden(true);
														me.lref('fldchange').setReadOnly(true);
														me.lref('fldchange').setValue(1);
													
														if(r.id !== me.mys.getVar('expenseNoteDefaultCurrency'))
															me.lref('fldchange').setReadOnly(false);
															me.lref('cntTot').setHidden(false);
													}
												}
											},
											width: 85
										}),
										{
											xtype: 'label',
											width: 46,
											margin: 3,
											html: me.mys.res('expenseNoteDetail.fld-change.lbl')
										},
										{
											xtype: 'numberfield',
											reference: 'fldchange',
											bind: '{record.change}',
											readOnly: true,
											width: 83,
											listeners: {
												blur: function (t, e, o) {	
													var val1 = t.getValue();
													var val2 = me.lref('fldtotal').getValue();
													
													me.lref('fldtotaldoc').setValue(val1*val2);
												}
											}
										}
									]
								},
								{
									xtype: 'fieldcontainer',
									reference: 'cntTot',
									hidden: true, 
									fieldLabel: me.mys.res('expenseNoteDetail.fld-documenttotal.lbl'),
									layout: 'hbox',
									defaults: {
										margin: '0 5 0 0'
									},
									items: [
										{
											xtype: 'numberfield',
											reference: 'fldtotaldoc',
											bind: '{record.totalDoc}',
											width: 85
										},
										WTF.localCombo('id', 'desc', {
											reference: 'fldCurrencyCostDoc',
											bind: '{record.currencyCostDoc}',
											autoLoadOnValue: true,
											readOnly: true,
											store: Ext.create('Sonicle.webtop.drm.store.Currency', {
												autoLoad: true
											}),
											width: 85
										})
									]
								},
								{
									xtype: 'textfield',
									bind: '{record.invoiceNumber}',
									fieldLabel: me.mys.res('expenseNoteDetail.fld-invoicenumber.lbl'),
									width: 420
								},
								{
									xtype: 'fieldcontainer',
									fieldLabel: '&nbsp;',
									labelSeparator: '',
									layout: 'hbox',
									defaults: {
										labelWidht: 45,
										margin: '0 15 0 0'
									},
									items: [
										{
											xtype: 'checkbox',
											bind: '{record.businessPayment}',
											boxLabel: me.mys.res('expenseNoteDetail.fld-businesspayment.lbl'),
											boxLabelAlign: 'before'
										},
										{
											xtype: 'checkbox',
											bind: '{record.reinvoice}',
											boxLabel: me.mys.res('expenseNoteDetail.fld-reinvoice.lbl'),
											boxLabelAlign: 'before'
										}
									]
								}
							]
						})	
					]
				},
				{
					title: me.mys.res('expenseNoteDetail.tab.documents.tit'),
					xtype: 'grid',
					id: gpId,
					reference: 'gpDocuments',
					bind: {
						store: '{record.detailDocuments}'
					},
					selModel: {
						type: 'checkboxmodel',
						mode: 'MULTI'
					},
					columns: [
						{
							xtype: 'solinkcolumn',
							dataIndex: 'fileName',
							header: me.mys.res('gpExpenseNoteDetailDocument.filename.lbl'),
							tdCls: 'wt-theme-text-lnk',
							flex: 3,
							listeners: {
								linkclick: function (s, idx, rec) {
									me.downloadAttachment([rec.getId()]);
								}
							}
						}, {
							xtype: 'sobytescolumn',
							dataIndex: 'size',
							header: me.mys.res('gpExpenseNoteDetailDocument.size.lbl'),
							flex: 1
						}
					],
					tbar: [
						me.getAct('downloadDetailDocument'),
						me.getAct('openDetailDocument'),
						me.getAct('deleteDetailDocument')
					],
					bbar: {
						xtype: 'wtuploadbar',
						sid: me.mys.ID,
						uploadContext: 'UploadExpenseNoteDocument',
						listeners: {
							fileuploaded: function (s, file, json) {
								me.addDocument(json.data.uploadId, file);
							}
						}
					},
					listeners: {
						rowcontextmenu: function (s, rec, itm, i, e) {
							var sm = s.getSelectionModel();
							sm.select(rec);
							WT.showContextMenu(e, me.getRef('cxmGridDocument'), {
								file: rec
							});
						},
						select: function (s, rec, i, e) {
							me.updateDisabled('downloadDetailDocument');
							me.updateDisabled('openDetailDocument');
							me.updateDisabled('deleteDetailDocument');
						}
					},
					plugins: [{
						ptype: 'sofiledrop',
						text: WT.res('sofiledrop.text')
					}]
				}
			]
		});
		
		me.on('viewload', me.onViewLoad);
	},
	
	onViewLoad: function(s, success) {
		if(!success) return;
		var me = this,
				mo = me.getModel();
		
		mo.set('currencyCostDoc', me.mys.getVar('expenseNoteDefaultCurrency'));
		
		if(me.isMode(me.MODE_NEW)) {
			mo.set('currency', me.mys.getVar('expenseNoteDefaultCurrency'));
		}
	}
});
