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
Ext.define('Sonicle.webtop.drm.model.TimetableRequest', {
	extend: 'WTA.ux.data.BaseModel',
	requires: [
		'Sonicle.data.writer.Json',
		'Sonicle.webtop.drm.model.LeaveRequestDocument'
	],
	proxy: WTF.apiProxy('com.sonicle.webtop.drm', 'ManageLeaveRequest', 'data', {
		writer: {
			type: 'sojson',
			writeAssociations: true
		}
	}),
	identifier: 'negative',
	idProperty: 'leaveRequestId',
	fields: [
		WTF.field('leaveRequestId', 'int', true),
		WTF.field('domainId', 'string', true),
		WTF.field('companyId', 'int', false),
		WTF.field('userId', 'string', false),
		WTF.field('managerId', 'string', false),
		WTF.field('type', 'string', false),
		WTF.field('fromDate', 'date', false, {dateFormat: 'Y-m-d', defaultValue: new Date()}),
		WTF.field('toDate', 'date', false, {dateFormat: 'Y-m-d', defaultValue: new Date()}),
		WTF.field('fromHour', 'string', true),
		WTF.field('toHour', 'string', true),
		WTF.field('status', 'string', true),
		WTF.field('notes', 'string', true),
		WTF.field('result', 'bool', true),
		WTF.field('cancRequest', 'bool', true),
		WTF.field('cancReason', 'string', true),
		WTF.field('cancResult', 'bool', true),
		WTF.field('eventId', 'int', true)
	],
	hasMany: [
		WTF.hasMany('documents', 'Sonicle.webtop.drm.model.LeaveRequestDocument')
	],
	
	setFromDate: function(date) {
		var me = this,
				dt = Ext.isDate(date) ? date : new Date(),
				end = me.get('toDate'), v;
		
		v = me.setDatePart('fromDate', dt);
		if (!Ext.isDate(end)) return;
		if (v > end) me.set('toDate', v);
	},	
	setToDate: function(date) {
		var me = this,
				dt = Ext.isDate(date) ? date : new Date(),
				sta = me.get('fromDate'), v;
		
		v = me.setDatePart('toDate', dt);
		if (!Ext.isDate(sta)) return;
		if (v < sta) me.set('fromDate', v);
	}
});