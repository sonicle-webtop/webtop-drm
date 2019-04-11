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
Ext.define('Sonicle.webtop.drm.view.Group', {
	extend: 'WTA.sdk.ModelView',
	requires: [
		'Sonicle.webtop.drm.model.Group',
		'Sonicle.webtop.drm.ux.UserGrid'
	],
	dockableConfig: {
		title: '{group.tit}',
		iconCls: 'wtdrm-icon-company-xs',
		width: 500,
		height: 500
	},
	fieldTitle: 'name',
	modelName: 'Sonicle.webtop.drm.model.Group',
	constructor: function (cfg) {
		var me = this;
		me.callParent([cfg]);
		WTU.applyFormulas(me.getVM(), {
			foActiveType: WTF.foCompare('record', 'groupType', function (v) {
				if (v === 'E')
					return v;
				if (v === 'S')
					return v;
				if (v === 'G')
					return v;
				return 'empty';
			})
		});
	},
	initComponent: function () {
		var me = this;
		me.callParent(arguments);
		me.add(
				{
					region: 'center',
					xtype: 'container',
					layout: {
						type: 'vbox',
						align: 'stretch'
					},
					items: [
						{
							xtype: 'wtform',
							items: [
								{
									xtype: 'textfield',
									bind: '{record.name}',
									fieldLabel: me.mys.res('group.fld-name.lbl')
								},
								{
									xtype: 'textarea',
									bind: '{record.description}',
									fieldLabel: me.mys.res('group.fld-description.lbl')
								},
								WTF.lookupCombo('id', 'desc', {
									bind: '{record.groupType}',
									store: Ext.create('Sonicle.webtop.drm.store.UserGroupType', {
										autoLoad: true
									}),
									fieldLabel: me.mys.res('group.fld-userType.lbl'),
									width: 250
								})
							]
						},
						{
							xtype: 'container',
							layout: 'card',
							reference: 'pnltype',
							bind: {
								activeItem: '{foActiveType}'
							},
							items: [{
									xtype: 'panel',
									itemId: 'empty'
								},
								{
									xtype: 'wtform',
									itemId: 'E',
									items: [
										WTF.lookupCombo('id', 'desc', {
											bind: '',
											store: null,
											fieldLabel: me.mys.res('group.fld-external.lbl'),
											width: 250
										})
									]
								},
								{
									xtype: 'wtform',
									itemId: 'S',
									items: [
										WTF.lookupCombo('id', 'desc', {
											bind: '',
											store: null,
											fieldLabel: me.mys.res('group.fld-supervisor.lbl'),
											width: 250
										})
									]
								},
								{
									xtype: 'wtform',
									itemId: 'G',
									items: [
										WTF.lookupCombo('id', 'desc', {
											bind: '',
											store: null,
											fieldLabel: me.mys.res('group.fld-supervisor.lbl'),
											width: 250
										})]
								}
							]
						},
						{
							xtype: 'wtdrmusergrid',
							sid: me.mys.ID,
							title: me.mys.res('wtdrmusergrid.tit'),
							bind: {
								store: '{record.associatedUsers}'
							},
							listeners: {
								pick: function (s, vals, recs) {
									var mo = me.getModel();
									
									mo.associatedUsers().add({
										userId: vals[0]
									});
								}
							},
							flex: 1,
							width: '100%'
						}
					]
				});
	}
});

