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
Ext.define('Sonicle.webtop.drm.view.Activity', {
	extend: 'WTA.sdk.ModelView',
	requires: [
		'Sonicle.webtop.drm.model.Activity',
		'Sonicle.webtop.drm.ux.GroupGrid'
	],
	dockableConfig: {
		title: '{activity.tit}',
		iconCls: 'wtdrm-icon-configurationActivities',
		width: 500,
		height: 500
	},
	fieldTitle: 'externalId',
	modelName: 'Sonicle.webtop.drm.model.Activity',
	initComponent: function () {
		var me = this;

		me.callParent(arguments);

		me.add({
			region: 'center',
			xtype: 'container',
			layout: {
				type: 'vbox',
				align: 'stretch'
			},
			items: [
				{
					xtype: 'wtform',
					items: [{
							xtype: 'textfield',
							bind: '{record.externalId}',
							fieldLabel: me.mys.res('activity.fld-externalid.lbl'),
							anchor: '100%'
						}, {
							xtype: 'textfield',
							bind: '{record.description}',
							fieldLabel: me.mys.res('activity.fld-description.lbl'),
							anchor: '100%'
						}, {
							xtype: 'fieldcontainer',
							// fieldLabel: me.mys.res('activity.fld-customer.lbl'),
							layout: 'hbox',
							defaults: {
								margin: '0 10 0 0'
							},
							items: [{
								xtype: 'checkbox',
								bind: '{record.customer}',
								boxLabel: me.mys.res('activity.fld-customer.lbl'),
								checked: false
							}, {
								xtype: 'checkbox',
								bind: '{record.timetable}',
								boxLabel: me.mys.res('activity.fld-timetable.lbl'),
								checked: false
							}, {
								xtype: 'checkbox',
								bind: '{record.exportable}',
								boxLabel: me.mys.res('activity.fld-exportable.lbl'),
								checked: true
							}
						]}, {
							xtype: 'fieldcontainer',
							// fieldLabel: me.mys.res('activity.fld-customer.lbl'),
							layout: 'hbox',
							defaults: {
								margin: '0 10 0 0'
							},
							items: [{
								xtype: 'checkbox',
								bind: '{record.jobs}',
								boxLabel: me.mys.res('activity.fld-jobs.lbl'),
								checked: false
							}, {
								xtype: 'checkbox',
								bind: '{record.opportunities}',
								boxLabel: me.mys.res('activity.fld-opportunities.lbl'),
								checked: false
							}
						]}
					]
				},
				{
					xtype: 'wtdrmgroupgrid',
					sid: me.mys.ID,
					title: me.mys.res('wtdrmgroupgrid.tit'),
					bind: {
						store: '{record.associatedGroups}'
					},
					listeners: {
						pick: function (s, vals, recs) {
							var mo = me.getModel(); 

							mo.associatedGroups().add({
								groupId: vals[0]
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
