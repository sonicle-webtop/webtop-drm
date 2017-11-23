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
Ext.define('Sonicle.webtop.drm.view.Company', {
	extend: 'WTA.sdk.ModelView',
	requires: [
		'Sonicle.form.field.Image',
		'Sonicle.webtop.drm.model.Company',
		'Sonicle.webtop.drm.ux.UserGrid'
	],
	dockableConfig: {
		title: '{company.tit}', //localizzato
		iconCls: 'wtdrm-icon-configuration-companiesconfiguration-xs',
		width: 500,
		height: 530
	},
	fieldTitle: 'name',
	modelName: 'Sonicle.webtop.drm.model.Company',
	initComponent: function () {
		var me = this;

		me.callParent(arguments);

		me.add({
			region: 'center',
			xtype: 'tabpanel',
			items: [{
					xtype: 'wtdrmusergrid',
					title: me.mys.res('company.tab-users.tit'),
					sid: me.mys.ID,
					bind: {
						store: '{record.associatedUsers}'
					},
					listeners: {
						pick: function (s, vals, recs) {
							var mo = me.getModel(); 

							mo.associatedUsers().add({
								userId: vals
							});
						}
					}
				},
				{
					xtype: 'wtform',
					title: me.mys.res('company.tab-company.tit'),
					items: [{
							xtype: 'textfield',
							bind: '{record.name}',
							fieldLabel: me.mys.res('company.fld-name.lbl')
						}, {
							xtype: 'textfield',
							bind: '{record.address}',
							fieldLabel: me.mys.res('company.fld-address.lbl')
						}, {
							xtype: 'textfield',
							bind: '{record.postalCode}',
							fieldLabel: me.mys.res('company.fld-postalcode.lbl')
						}, {
							xtype: 'textfield',
							bind: '{record.city}',
							fieldLabel: me.mys.res('company.fld-city.lbl')
						}, {
							xtype: 'textfield',
							bind: '{record.state}',
							fieldLabel: me.mys.res('company.fld-state.lbl')
						}, {
							xtype: 'textfield',
							bind: '{record.phone}',
							fieldLabel: me.mys.res('company.fld-phone.lbl')
						}, {
							xtype: 'textfield',
							bind: '{record.fax}',
							fieldLabel: me.mys.res('company.fld-fax.lbl')
						}, {
							xtype: 'textfield',
							bind: '{record.vat}',
							fieldLabel: me.mys.res('company.fld-vat.lbl')
						}, {
							xtype: 'textfield',
							bind: '{record.taxCode}',
							fieldLabel: me.mys.res('company.fld-taxCode.lbl')
						}, {
							xtype: 'textfield',
							bind: '{record.rea}',
							fieldLabel: me.mys.res('company.fld-rea.lbl')
						}, {
							xtype: 'textfield',
							bind: '{record.businessRegister}',
							fieldLabel: me.mys.res('company.fld-businessRegister.lbl')
						},{
							xtype: 'soimagefield',
							reference: 'fldpic',
							bind: '{record.picture}',
							fieldLabel: me.mys.res('company.fld-picture.lbl'),
							imageWidth: 250,
							imageHeight: 60,
							geometry: 'square',
							imageUrl: WTF.processBinUrl(me.mys.ID, 'GetCompanyPicture'),
							clearTriggerCls: 'wtcon-trash-trigger',
							uploadTriggerCls: 'wtcon-add-trigger',
							uploaderConfig: WTF.uploader(me.mys.ID, 'CompanyPicture', {
								extraParams: {tag: me.getId()},
								mimeTypes: [
									{title: 'Image files', extensions: 'jpeg,jpg,png'}
								]
							}),
							listeners: {
								uploadstarted: function() {
									me.wait();
								},
								uploadcomplete: function() {
									me.unwait();
								},
								uploaderror: function() {
									me.unwait();
								},
								fileuploaded: function(s, file, json) {
									me.getModel().set('picture', json.data.uploadId);
								}
							}
						}]
				}, {
					xtype: 'wtform',
					title: me.mys.res('company.tab-footer.tit'),
					items: [
						WTF.lookupCombo('id', 'desc', {
							bind: '{record.footerColumns}',
							store: Ext.create('Sonicle.webtop.drm.store.FooterType', {
								autoLoad: true
							}),
							fieldLabel: me.mys.res('company.fld-footerColumns.lbl'),
							width: 250
						}),
						{
							xtype: 'textarea',
							fieldLabel: me.mys.res('company.fld-footerColumnsLeft.lbl'),
							bind: '{record.footerColumnLeft}'
						}, {xtype: 'textarea',
							fieldLabel: me.mys.res('company.fld-footerColumnsRight.lbl'),
							bind: '{record.footerColumnRight}'
						}]
				}]
		});
	}
});
