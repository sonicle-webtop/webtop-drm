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
Ext.define('Sonicle.webtop.drm.view.TimetableSettingGeneral', {
	extend: 'WTA.sdk.ModelView',
	dockableConfig: {
		title: '{timetable.config.general.tit}',
		iconCls: 'wtdrm-icon-configuration',
		width: 650,
		height: 670
	},
	modelName: 'Sonicle.webtop.drm.model.TimetableSettingGeneral',
	initComponent: function () {
		var me = this;
		me.callParent(arguments);
		me.add({
			region: 'center',
			xtype: 'tabpanel',
			items: [
				{
					xtype: 'wtform',
						title: me.mys.res('timetable.settings.tit'),
					defaults: {
						labelWidth: 160
					},
					items: [
						{
							xtype: 'textfield',
							bind: '{record.allowedAddresses}',
							fieldLabel: me.mys.res('timetable.settings.fld-allowedaddresses.lbl'),
							selectOnFocus: true,
							emptyText: me.mys.res('timetable.settings.fld-allowedaddresses.emp'),
							width: 500
						},
						{
							xtype: 'textfield',
							bind: '{record.allowedUsers}',
							fieldLabel: me.mys.res('timetable.settings.fld-allowedusers.lbl'),
							selectOnFocus: true,
							emptyText: me.mys.res('timetable.settings.fld-allowedusers.emp'),
							width: 500
						},
						{
							xtype: 'textfield',
							bind: '{record.staffOfficeEmail}',
							fieldLabel: me.mys.res('timetable.settings.fld-staffofficeemail.lbl'),
							selectOnFocus: true
						},
						{
							xtype: 'numberfield',
							bind: '{record.totalToleranceInMinutes}',
							allowDecimals: false,
							allowBlank: true,
							editable: true,
							selectOnFocus: true,
							minValue: 1,
							maxValue: 1000,
							fieldLabel: me.mys.res('timetable.settings.fld-totaltoleranceinminutes.lbl')
						},
						WTF.lookupCombo('id', 'desc', {
							bind: '{record.rounding}',
							store: Ext.create('Sonicle.webtop.drm.store.RoundingHour', {
								autoLoad: true
							}),
							triggers: {
								clear: WTF.clearTrigger()
							},
							emptyText: WT.res('word.none.male'),
							fieldLabel: me.mys.res('timetable.settings.fld-rounding.lbl'),
							selectOnFocus: true,
							editable: true
						}),
						{
							xtype: 'numberfield',
							bind: '{record.minimumExtraordinary}',
							allowDecimals: false,
							allowBlank: true,
							editable: true,
							selectOnFocus: true,
							minValue: 1,
							maxValue: 1000,
							fieldLabel: me.mys.res('timetable.settings.fld-minimumextraordinary.lbl')
						},
						WTF.localCombo('id', 'desc', {
							bind: '{record.calendarUserId}',
							anyMatch: true,
							allowBlank: true,
							emptyText: me.mys.res('timetable.settings.fld-calendaruserid-emptytext.lbl'),
							store: {
								autoLoad: true,
								model: 'WTA.model.Simple',
								proxy: WTF.proxy(me.mys.ID, 'LookupUsers')
							},
							triggers: {
								clear: WTF.clearTrigger()
							},
							fieldLabel: me.mys.res('timetable.settings.fld-calendaruserid.lbl')
						}),
						WTF.remoteCombo('id', 'desc', {
							bind: '{record.defaultEventActivityId}',
							autoLoadOnValue: true,
							anyMatch: true,
							allowBlank: true,
							store: {
								autoLoad: true,
								model: 'WTA.model.ActivityLkp',
								proxy: WTF.proxy(WT.ID, 'LookupActivities', null, {
									extraParams: {
										profileId: WT.getVar('profileId')
									}
								}),
								filters: [{
									filterFn: function(rec) {
										if(rec.get('readOnly')) {
											if(rec.getId() !== me.lref('activity').getValue()) {
												return null;
											}
										}
										return rec;
									}
								}]
							},
							triggers: {
								clear: WTF.clearTrigger()
							},
							fieldLabel: me.mys.res('timetable.settings.fld-defaulteventactivity.lbl')
						}),
						{
							xtype: 'checkbox',
							bind: '{record.manageStamp}',
							boxLabel: me.mys.res('timetable.settings.fld-managestamp.lbl')
						},
						{
							xtype: 'checkbox',
							bind: '{record.companyExit}',
							boxLabel: me.mys.res('timetable.settings.fld-companyexit.lbl')
						},
						{
							xtype: 'checkbox',
							bind: '{record.breakAnomaly}',
							boxLabel: me.mys.res('timetable.settings.fld-breakanomaly.lbl')
						},
						{
							xtype: 'checkbox',
							bind: '{record.readOnlyEvents}',
							boxLabel: me.mys.res('timetable.settings.fld-readonlyevents.lbl')
						},
						{
							xtype: 'checkbox',
							bind: '{record.requestsHolidaysPermitsPreviousDates}',
							boxLabel: me.mys.res('timetable.settings.fld-requestsholidayspermitspreviousdates.lbl')
						},
						{
							xtype: 'checkbox',
							bind: '{record.requestsPermitsNotRemunered}',
							boxLabel: me.mys.res('timetable.settings.fld-requestspermitsnotremunered.lbl')
						},
						{
							xtype: 'checkbox',
							bind: '{record.requestsPermitsMedicalVisits}',
							boxLabel: me.mys.res('timetable.settings.fld-requestspermitsmedicalvisits.lbl')
						},
						{
							xtype: 'checkbox',
							bind: '{record.requestsPermitsContractuals}',
							boxLabel: me.mys.res('timetable.settings.fld-requestspermitscontractuals.lbl')
						},
						{
							xtype: 'checkbox',
							bind: '{record.medicalVisitsAutomaticallyApproved}',
							boxLabel: me.mys.res('timetable.settings.fld-medicalVisitsAutomaticallyApproved.lbl')
						},
						{
							xtype: 'checkbox',
							bind: '{record.requestsSickness}',
							boxLabel: me.mys.res('timetable.settings.fld-requestssickness.lbl')
						},
						{
							xtype: 'checkbox',
							bind: '{record.sicknessAutomaticallyApproved}',
							boxLabel: me.mys.res('timetable.settings.fld-sicknessAutomaticallyApproved.lbl')
						}
					]
				},
				{
					xtype: 'wtform',
					title: me.mys.res('timetable.settings.tit2'),
					defaults: {
						labelWidth: 160
					},
					items: [
						WTF.localCombo('id', 'desc', {
							bind: '{record.defaultCausalWorkingHours}',
							anyMatch: true,
							allowBlank: true,
							store: {
								autoLoad: true,
								model: 'WTA.model.Simple',
								proxy: WTF.proxy(me.mys.ID, 'LookupGisCausals', null, {
									extraParams: {
										filter: false
									}
								})
							},
							triggers: {
								clear: WTF.clearTrigger()
							},
							fieldLabel: me.mys.res('timetable.settings.fld-defaultCausalWorkingHours.lbl'),
							width: 500
						}),
						WTF.localCombo('id', 'desc', {
							bind: '{record.defaultCausalOvertime}',
							anyMatch: true,
							allowBlank: true,
							store: {
								autoLoad: true,
								model: 'WTA.model.Simple',
								proxy: WTF.proxy(me.mys.ID, 'LookupGisCausals', null, {
									extraParams: {
										filter: false
									}
								})
							},
							triggers: {
								clear: WTF.clearTrigger()
							},
							fieldLabel: me.mys.res('timetable.settings.fld-defaultCausalOvertime.lbl'),
							width: 500
						}),
						WTF.localCombo('id', 'desc', {
							bind: '{record.defaultCausalPermits}',
							anyMatch: true,
							allowBlank: true,
							store: {
								autoLoad: true,
								model: 'WTA.model.Simple',
								proxy: WTF.proxy(me.mys.ID, 'LookupGisCausals', null, {
									extraParams: {
										filter: false
									}
								})
							},
							triggers: {
								clear: WTF.clearTrigger()
							},
							fieldLabel: me.mys.res('timetable.settings.fld-defaultCausalPermits.lbl'),
							width: 500
						}),
						WTF.localCombo('id', 'desc', {
							bind: '{record.defaultCausalHolidays}',
							anyMatch: true,
							allowBlank: true,
							store: {
								autoLoad: true,
								model: 'WTA.model.Simple',
								proxy: WTF.proxy(me.mys.ID, 'LookupGisCausals', null, {
									extraParams: {
										filter: false
									}
								})
							},
							triggers: {
								clear: WTF.clearTrigger()
							},
							fieldLabel: me.mys.res('timetable.settings.fld-defaultCausalHolidays.lbl'),
							width: 500
						}),
						WTF.localCombo('id', 'desc', {
							bind: '{record.defaultCausalSickness}',
							anyMatch: true,
							allowBlank: true,
							store: {
								autoLoad: true,
								model: 'WTA.model.Simple',
								proxy: WTF.proxy(me.mys.ID, 'LookupGisCausals', null, {
									extraParams: {
										filter: false
									}
								})
							},
							triggers: {
								clear: WTF.clearTrigger()
							},
							fieldLabel: me.mys.res('timetable.settings.fld-defaultCausalSickness.lbl'),
							width: 500
						}),
						WTF.localCombo('id', 'desc', {
							bind: '{record.defaultCausalMedicalVisit}',
							anyMatch: true,
							allowBlank: true,
							store: {
								autoLoad: true,
								model: 'WTA.model.Simple',
								proxy: WTF.proxy(me.mys.ID, 'LookupGisCausals', null, {
									extraParams: {
										filter: false
									}
								})
							},
							triggers: {
								clear: WTF.clearTrigger()
							},
							fieldLabel: me.mys.res('timetable.settings.fld-defaultCausalMedicalVisit.lbl'),
							width: 500
						})
					]
				}
			]
		});
	}
});
