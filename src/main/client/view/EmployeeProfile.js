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
Ext.define('Sonicle.webtop.drm.view.EmployeeProfile', {
	extend: 'WTA.sdk.ModelView',
	requires: [
		'Sonicle.webtop.drm.model.EmployeeProfile'
	],
	dockableConfig: {
		title: '{EmployeeProfile.tit}',
		iconCls: 'wtdrm-icon-configurationEmployeeProfiles',
		width: 450,
		height: 400,
		modal: true
	},
	fieldTitle: 'description',
	modelName: 'Sonicle.webtop.drm.model.EmployeeProfile',
    
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
				labelWidth: 240
			},
			items: [
				{
					xtype: 'wtform',
					reference: 'employeeProfileform',
					modelValidation: true,
					items: [
						WTF.localCombo('id', 'desc', {
							bind: '{record.userId}',
							reference: 'flduser',
							anyMatch: true,
							allowBlank: false,
							listConfig: {
								escapeDisplay: true
							},
							store: {
								autoLoad: true,
								model: 'WTA.model.Simple',
								proxy: WTF.proxy(me.mys.ID, 'LookupUsers')
							},
							fieldLabel: me.mys.res('EmployeeProfile.fld-employee.lbl'),
                            width: 380
						}),
						{
							xtype: 'textfield',
							bind: '{record.number}',
							allowBlank: false,
							fieldLabel: me.mys.res('EmployeeProfile.fld-number.lbl'),
							selectOnFocus: true,
                            width: 260
						},
						{
							xtype: 'textfield',
							bind: '{record.headquartersCode}',
							fieldLabel: me.mys.res('EmployeeProfile.fld-headquartersCode.lbl'),
							selectOnFocus: true,
							width: 300,
							maxLength: 4
						},
						{
							xtype: 'numberfield',
							bind: '{record.tolerance}',
							allowDecimals: false,
							allowBlank: true,
							editable: true,
							selectOnFocus: true,
							minValue: 1,
							maxValue: 1000,
							fieldLabel: me.mys.res('EmployeeProfile.fld-tolerance.lbl'),
                            width: 180
						},
						WTF.localCombo('id', 'desc', {
							bind: '{record.hourProfileId}',
							reference: 'fldhourprofile',
							anyMatch: true,
							allowBlank: true,
							listConfig: {
								escapeDisplay: true
							},
							store: {
								autoLoad: true,
								model: 'WTA.model.Simple',
								proxy: WTF.proxy(me.mys.ID, 'LookupHourProfiles')
							},
							fieldLabel: me.mys.res('EmployeeProfile.fld-hourProfiles.lbl'),
                            width: 380
						}),
						{
							xtype: 'numberfield',
							bind: '{record.minimumNumberOfHoursPerTicket}',
							emptyText: me.mys.getVar('defaultMinimumNumberOfHoursPerTicket'),
							allowBlank: true,
							minValue: 0,
							maxValue: 12,
							fieldLabel: me.mys.res('EmployeeProfile.fld-minimumNumberOfHoursPerTicket.lbl'),
							width: 380
						},
						WTF.lookupCombo('id', 'desc', {
							bind: '{record.stampingMode}',
							store: Ext.create('Sonicle.webtop.drm.store.StampingMode', {
								autoLoad: true
							}),
							triggers: {
								clear: WTF.clearTrigger()
							},
							fieldLabel: me.mys.res('timetable.settings.fld-stampingMode.lbl'),
							emptyText: me.mys.res('store.stampingMode.'+me.mys.getVar("defaultStampingMode")),
							selectOnFocus: true,
							editable: true
						}),
						{
							xtype: 'checkbox',
							bind: '{record.extraordinary}',
							boxLabel: me.mys.res('EmployeeProfile.fld-extraordinary.lbl')
						},
						{
							xtype: 'checkbox',
							bind: '{record.onlyPresence}',
							boxLabel: me.mys.res('EmployeeProfile.fld-onlyPresence.lbl')
						},
						{
							xtype: 'checkbox',
							bind: '{record.noStamping}',
							boxLabel: me.mys.res('EmployeeProfile.fld-noStamping.lbl')
						}
					]
				}
			]
		});
		
		me.on('viewinvalid', me.onViewInvalid);
	},
	
	onViewInvalid: function (s, mo, errs) {
		var me = this;
		WTU.updateFieldsErrors(me.lref('employeeProfileform'), errs);
	}
});

