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
Ext.define('Sonicle.webtop.drm.view.Profile', {
	extend: 'WTA.sdk.ModelView',
	requires: [
		'Sonicle.webtop.drm.model.Profile',
		'Sonicle.webtop.drm.ux.UserGrid',
		'Sonicle.webtop.drm.ux.CustomerGrid'
	],
	dockableConfig: {
		title: '{profile.tit}', //localizzato
		iconCls: 'wtdrm-icon-company-xs',
		width: 500,
		height: 500
	},
	fieldTitle: 'description    ',
	modelName: 'Sonicle.webtop.drm.model.Profile',
	constructor: function (cfg) {
		var me = this;
		me.callParent([cfg]);
		WTU.applyFormulas(me.getVM(), {
			foActiveCard: WTF.foDefaultIfEmpty('record', 'type', 'E'),
			foHiddenCard: WTF.foIsEqual('record', 'type', 'E')
		});
	},
	initComponent: function () {
		var me = this;
		me.callParent(arguments);
		me.add({
			region: 'center',
			xtype: 'panel',
			layout: 'vbox',
			items: [
				{
					xtype: 'wtform',
					items: [
						{
							xtype: 'textfield',
							bind: '{record.description}',
							fieldLabel: me.mys.res('profile.description.lbl')

						},
						WTF.lookupCombo('id', 'desc', {
							bind: '{record.type}',
							reference: 'fldtype',
							allowBlank: false,
							store: Ext.create('Sonicle.webtop.drm.store.ProfileType', {
								autoLoad: true
							}),
							readOnly: true,
							fieldLabel: me.mys.res('profile.type.lbl'),
							width: 250
						})
					]
				},
				{
					xtype: 'tabpanel',
					items: [{
							title: 'Foo'
						}, {
							title: 'Bar'
						}]
				},
				/*{
				 xtype: 'wtdrmusergrid',
				 title: me.mys.res('profile.gpAssignedUsers.tit'),
				 sid: me.mys.ID,
				 actionsInToolbar: false,
				 width: '100%',
				 bind: {
				 store: '{record.associatedUsers}' //record. nome proprietà con dentro gli utenti
				 },
				 listeners: {
				 pick: function (s, vals, recs) {
				 var mo = me.getModel();
				 mo.associatedUsers().add({
				 userId: vals
				 });
				 }
				 }
				 },*/
				{xtype: 'panel',
					layout: 'card',
					width: '100%',
					reference: 'pnlgrid',
					bind: {
						activeItem: '{foActiveCard}'
					},
					items: [
						{
							xtype: 'wtdrmcustomergrid',
							title: me.mys.res('profile.gpCustumer.tit'),
							itemId: 'E',
							sid: me.mys.ID,
							bind: {
								store: '{record.associatedCustomers}' //record. nome proprietà con dentro gli utenti,
										//,hidden: '{foHiddenCard}',
										//disabled: '{foHiddenCard}'
							},
							listeners: {
								pick: function (s, vals, recs) {
									//evento della griglia che restituisce l'out put del picker
									var mo = me.getModel(); //modello

									//proprieta del modello che contiene lo store dei dati interessati
									mo.associatedCustomers().add({
										masterDataId: vals
									});
								}
							}
						},
						{
							xtype: 'wtdrmusergrid',
							title: me.mys.res('profile.gpSupervisedUsers.tit'),
							itemId: 'S',
							sid: me.mys.ID,
							actionsInToolbar: false,
							width: '100%',
							bind: {
								store: '{record.supervisedUsers}' //record. nome proprietà con dentro gli utenti
										//	,hidden: '{foHiddenCard}'
							},
							listeners: {
								pick: function (s, vals, recs) {
									var mo = me.getModel();
									mo.supervisedUsers().add({
										userId: vals
									});
								}
							}
						}
					]
				}
			]
		}
		/*	{
		 xtype: 'wtdrmusergrid',
		 title: me.mys.res('profile.gpAssignedUsers.tit'),
		 sid: me.mys.ID,
		 actionsInToolbar: false,
		 width: '100%',
		 bind: {
		 store: '{record.associatedUsers}' //record. nome proprietà con dentro gli utenti
		 },
		 listeners: {
		 pick: function (s, vals, recs) {
		 var mo = me.getModel();
		 mo.associatedUsers().add({
		 userId: vals
		 });
		 }
		 }
		 } */
		);
	}
});

