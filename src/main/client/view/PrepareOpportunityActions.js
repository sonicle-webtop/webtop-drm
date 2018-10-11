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
Ext.define('Sonicle.webtop.drm.view.PrepareOpportunityActions', {
	extend: 'WTA.sdk.ModelView',
	requires: [
		'Sonicle.webtop.drm.ux.ActivityGrid',
		'Sonicle.webtop.drm.model.PrepareOpportunityActions',
		'WTA.ux.data.SimpleSourceModel',
		'Sonicle.Bytes',
		'WTA.ux.UploadBar'
	],
	dockableConfig: {
		title: '{prepareOpportunityActions.tit}',
		iconCls: 'wtdrm-icon-prepareopportunityaction-xs',
		width: 220,
		height: 300,
		modal: true,
		minimizable: false
	},
	modelName: 'Sonicle.webtop.drm.model.PrepareOpportunityActions',
	
	constructor: function(cfg) {
		var me = this;
		me.callParent([cfg]);
	},
	
	initComponent: function () {
		var me = this;

		me.callParent(arguments);

		me.add({
			region: 'center',
			xtype: 'container',
			layout: 'border',
			refernceHolder: true,
			items: [
				{
					xtype: 'container',
					region: 'north',
					height: '100px',
					layout: {
						type: 'vbox', align: 'stretch'
					},
					items: [
						Ext.create({
							xtype: 'wtform',
							reference: 'mainForm',
							modelValidation: true,
							defaults: {
								labelWidth: 45
							},
							items: [
								{
									xtype: 'datefield',
									startDay: WT.getStartDay(),
									reference: 'date',
									bind: '{record.date}',
									format: WT.getShortDateFmt(),
									tabIndex: 402,
									selectOnFocus: true,
									fieldLabel: me.mys.res('prepareOpportunityActions.fld-date.lbl')
								}
							]
						})
					]
				}, 
				{
					region: 'center',
					xtype: 'wtdrmactivitygrid',
					title: me.mys.res('prepareOpportunityAction.gpActionActivities.tit'),
					sid: me.mys.ID,
					actionsInToolbar: false,
					width: '100%',
					flex: 1,
					border: true,
					bind: {
						store: '{record.actionActivities}'
					},
					listeners: {
						pick: function (s, vals, recs) {
							var mo = me.getModel();
							mo.actionActivities().add({
								id: recs[0].getId(),
								desc: recs[0].get('desc')
							});
						}
					}
				}
			]
		});

		me.on('viewinvalid', me.onViewInvalid);
		me.on('viewload', me.onViewLoad);
	},
	
	onViewLoad: function(s, success) {
		if(!success) return;
		var me = this,
				mo = me.getModel();
		
		if(mo.get('operatorId') === null) me.lref('flduser').setReadOnly(false);
	},
	
	onViewInvalid: function (s, mo, errs) {
		var me = this;
		
		WTU.updateFieldsErrors(me.lref('mainForm'), errs);
	}
});
