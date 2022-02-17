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
Ext.define('Sonicle.webtop.drm.view.TimetableStamp', {
	extend: 'WTA.sdk.ModelView',
	requires: [
		'Sonicle.webtop.drm.model.TimetableStamp',
		'WTA.ux.data.SimpleSourceModel'
	],
	dockableConfig: {
		title: '{timetableStamp.tit}',
		iconCls: 'wtdrm-icon-timetable-stamps',
		width: 460,
		height: 140
	},
	fieldTitle: 'leaveRequestId',
	modelName: 'Sonicle.webtop.drm.model.TimetableStamp',
	initComponent: function () {
		var me = this;
		
		Ext.apply(me, {
			tbar: [
				'->',
				WTF.localCombo('id', 'desc', {
					reference: 'flduser',
					bind: '{record.userId}',
					store: {
						autoLoad: true,
						model: 'WTA.model.Simple',
						proxy: WTF.proxy(me.mys.ID, 'LookupOperators')
					},
					fieldLabel: me.mys.res('timetableStamp.fld-user.lbl'),
					readOnly: true
				})
			]
		});
		
		me.callParent(arguments);
		
		me.add({
			region: 'center',
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
					xtype: 'wtform',
					reference: 'form',
					modelValidation: true,
					items: [
						{
							xtype: 'fieldcontainer',
							fieldLabel: me.mys.res('timetableStamp.fld-stamp.lbl'),
							layout: 'hbox',
							defaults: {
								margin: '0 10 0 0'
							},
							items: [
								{
									xtype: 'datefield',
									startDay: WT.getStartDay(),
									reference: 'fldfromdate',
									bind: '{record.date}',
									width: 105
								},
								{ 
									xtype: 'timefield',
									bind: '{record.fromHour}',
									format: 'H:i',
									margin: '0 5 0 0',
									width: 105
								},
								{
									xtype: 'timefield',
									bind: '{record.toHour}',
									format: 'H:i',
									margin: '0 5 0 0',
									width: 105
								}
							]
						}
					]
				}
			]
		});
		
		me.on('viewinvalid', me.onViewInvalid);
		me.on('viewload', me.onViewLoad);
	},
	
	onViewLoad: function(s, success) {
		if(!success) return;
		var me = this,
				mo = me.getModel(),
				date = me.lookupReference('fldfromdate').getValue();
		
		me.lookupReference('fldfromdate').setMinValue(Ext.Date.getFirstDateOfMonth(date));
		me.lookupReference('fldfromdate').setMaxValue(Ext.Date.getLastDateOfMonth(date));
	},
	
	onViewInvalid: function (s, mo, errs) {
		var me = this;
		WTU.updateFieldsErrors(me.lref('form'), errs);
	}
});

