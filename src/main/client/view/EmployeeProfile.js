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
		width: 550,
		height: 600,
		modal: true
	},
	fieldTitle: 'description',
	modelName: 'Sonicle.webtop.drm.model.EmployeeProfile',
    
	constructor: function (cfg) {
		var me = this;
		me.callParent([cfg]);
		
		Sonicle.VMUtils.applyFormulas(me.getVM(), {
			foStampingModeO: WTF.foFieldTwoWay('record', 'stampingMode', 
				function(v, mo, fieldName) {
					return Sonicle.String.contains(v, "O");
				}, function(v, mo, fieldName) {
					return Sonicle.String.replaceAll(mo.get(fieldName),'O','')+(v ? 'O' : '');					
				}
			),
			foStampingModeS: WTF.foFieldTwoWay('record', 'stampingMode', 
				function(v, mo, fieldName) {
					return Sonicle.String.contains(v, "S");
				}, function(v, mo, fieldName) {
					return Sonicle.String.replaceAll(mo.get(fieldName),'S','')+(v ? 'S' : '');	
				}
			),
			foStampingModeA: WTF.foFieldTwoWay('record', 'stampingMode', 
				function(v, mo, fieldName) {
					return Sonicle.String.contains(v, "A");
				}, function(v, mo, fieldName) {
					return Sonicle.String.replaceAll(mo.get(fieldName),'A','')+(v ? 'A' : '');					
				}
			),
			foPswDisabled: WTF.foGetFn('_mode', null, function(val) {
				return val !== me.MODE_NEW || !me.askForPassword;
			})
		});
	},
    
	initComponent: function () {
		var me = this;
		me.callParent(arguments);
		me.add({
			region: 'center',
			xtype: 'tabpanel',
			items: [
				{
					xtype: 'wtform',
					reference: 'employeeProfileform',
					title: me.mys.res('timetable.settings.tit'),
					modelValidation: true,
					scrollable: true,
					defaults: {
						labelWidth: 240
					},
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
                            width: 480
						}),
						{
							xtype: 'textfield',
							bind: '{record.number}',
							allowBlank: false,
							fieldLabel: me.mys.res('EmployeeProfile.fld-number.lbl'),
							selectOnFocus: true,
                            width: 360
						},
						{
							xtype: 'textfield',
							bind: '{record.headquartersCode}',
							fieldLabel: me.mys.res('EmployeeProfile.fld-headquartersCode.lbl'),
							selectOnFocus: true,
							width: 360,
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
                            width: 360
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
                            width: 480
						}),
						{
							xtype: 'numberfield',
							bind: '{record.minimumNumberOfHoursPerTicket}',
							emptyText: me.mys.getVar('defaultMinimumNumberOfHoursPerTicket'),
							allowBlank: true,
							minValue: 0,
							maxValue: 12,
							fieldLabel: me.mys.res('EmployeeProfile.fld-minimumNumberOfHoursPerTicket.lbl'),
							width: 480
						},
						{
							xtype: 'checkbox',
							bind: '{foStampingModeO}',
							boxLabel: me.mys.res('timetable.settings.fld-stampingModeO.lbl')
						},
						{
							xtype: 'checkbox',
							bind: '{foStampingModeS}',
							boxLabel: me.mys.res('timetable.settings.fld-stampingModeS.lbl')
						},
						{
							xtype: 'checkbox',
							bind: '{foStampingModeA}',
							boxLabel: me.mys.res('timetable.settings.fld-stampingModeA.lbl')
						},
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

