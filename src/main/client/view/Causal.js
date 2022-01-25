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
Ext.define('Sonicle.webtop.drm.view.Causal', {
	extend: 'WTA.sdk.ModelView',
	requires: [
		'Sonicle.webtop.drm.model.Causal'
	],
	dockableConfig: {
		title: '{causal.tit}',
		iconCls: 'wtdrm-icon-configuration-causal-xs',
		width: 400,
		height: 150,
		modal: true
	},
	fieldTitle: 'id',
	modelName: 'Sonicle.webtop.drm.model.Causal',
    
	constructor: function (cfg) {
		var me = this;
		me.callParent([cfg]);
	},
    
	initComponent: function () {
		var me = this;
		me.callParent(arguments);
		me.add({
			region: 'center',
			xtype: 'panel',
			layout: 'vbox',
			defaults: {
				labelWidth: 180
			},
			items: [
				{
					xtype: 'wtform',
					reference: 'causalform',
					modelValidation: true,
					items: [
						{
							xtype: 'textfield',
							bind: '{record.id}',
							reference: 'fldid',
							allowBlank: false,
							fieldLabel: me.mys.res('causal.fld-id.lbl'),
							selectOnFocus: true,
                            width: 380,
							maxLength: 5
						},
						{
							xtype: 'textfield',
							bind: '{record.description}',
							allowBlank: false,
							fieldLabel: me.mys.res('causal.fld-description.lbl'),
							selectOnFocus: true,
                            width: 380,
							maxLength: 100
						},
						{
							xtype: 'textfield',
							bind: '{record.externalCode}',
							allowBlank: false,
							fieldLabel: me.mys.res('causal.fld-externalCode.lbl'),
							selectOnFocus: true,
                            width: 380,
							maxLength: 5
						}
					]
				}
			]
		});
		
		me.on('viewinvalid', me.onViewInvalid);
		me.on('viewload', me.onViewLoad);
	},
	
	onViewInvalid: function (s, mo, errs) {
		var me = this;
		WTU.updateFieldsErrors(me.lref('causalform'), errs);
	},
	
	onViewLoad: function(s, success) {
		if(!success) return;
		var me = this;
		
		if(me.isMode(me.MODE_EDIT)) {
			me.lref('fldid').setReadOnly(true);
		}
	}
});