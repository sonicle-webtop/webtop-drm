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
Ext.define('Sonicle.webtop.drm.model.TimetableSettingGeneral', {
	extend: 'WTA.ux.data.BaseModel',
	requires: [
		'Sonicle.data.writer.Json'
		// 'Sonicle.webtop.drm.model.HolidayDate'
	],
	proxy: WTF.apiProxy('com.sonicle.webtop.drm', 'ManageTimetableSetting', 'data', {
		writer: {
			type: 'sojson',
			writeAssociations: true
		}
	}),
	identifier: 'negativestring',
	idProperty: 'id',
	fields: [
		WTF.field('id', 'string', true),
		WTF.field('timetableSettingId', 'int', true),
		WTF.field('domainId', 'string', true),
		WTF.field('allowedAddresses', 'string', true),
		WTF.field('allowedUsers', 'string', true),
		WTF.field('staffOfficeEmail', 'string', true),
		WTF.field('requestsHolidaysPermitsPreviousDates', 'bool', true, {defaultValue: false}),
		WTF.field('totalToleranceInMinutes', 'string', true),
		WTF.field('rounding', 'string', true),
		WTF.field('minimumExtraordinary', 'string', true),
		WTF.field('calendarUserId', 'string', true),
		WTF.field('defaultEventActivityId', 'string', true),
		WTF.field('companyExit', 'bool', true, {defaultValue: false}),
		WTF.field('manageStamp', 'bool', true, {defaultValue: false}),
		WTF.field('breakAnomaly', 'bool', true, {defaultValue: false}),
		WTF.field('readOnlyEvents', 'bool', true, {defaultValue: false}),
		WTF.field('requestsPermitsNotRemunered', 'bool', true, {defaultValue: false}),
		WTF.field('requestsPermitsMedicalVisits', 'bool', true, {defaultValue: false}),
		WTF.field('requestsPermitsContractuals', 'bool', true, {defaultValue: false}),
		WTF.field('medicalVisitsAutomaticallyApproved', 'bool', true, {defaultValue: false}),
		WTF.field('requestsSickness', 'bool', true, {defaultValue: false}),
		WTF.field('sicknessAutomaticallyApproved', 'bool', true, {defaultValue: false}),
		WTF.field('defaultCausalWorkingHours', 'string', true),
		WTF.field('defaultCausalOvertime', 'string', true),
		WTF.field('defaultCausalPermits', 'string', true),
		WTF.field('defaultCausalHolidays', 'string', true),
		WTF.field('defaultCausalSickness', 'string', true),
		WTF.field('defaultCausalMedicalVisit', 'string', true),
		WTF.field('minimumNumberOfHoursPerTicket', 'int', true),
		WTF.field('ticketManagement', 'bool', true, {defaultValue: false}),
		WTF.field('automaticOvertime', 'bool', true, {defaultValue: false}),
		WTF.field('defaultStampingMode', 'string', true, {defaultValue: 'B'})
	]
});