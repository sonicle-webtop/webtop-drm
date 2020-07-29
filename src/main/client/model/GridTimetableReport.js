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

Ext.define('Sonicle.webtop.drm.model.GridTimetableReport', {
	extend: 'WTA.ux.data.BaseModel',
	fields: [
		WTF.field('id', 'int', false),
		WTF.field('domainId', 'string', false),
		WTF.field('company', 'string', false),
		WTF.field('user', 'string', false),
		WTF.field('date', 'string', false),
		WTF.field('workingHours', 'string', true),
		WTF.field('overtime', 'string', true),
		WTF.field('paidLeave', 'string', true),
		WTF.field('unpaidLeave', 'string', true),
		WTF.field('holiday', 'string', true),
		WTF.field('medicalVisit', 'string', true),
		WTF.field('contractual', 'string', true),
		WTF.field('causal', 'string', true),
		WTF.field('hour', 'string', true),
		WTF.field('detail', 'string', true),
		WTF.field('note', 'string', true),
		WTF.field('targetUser', 'string', false),
		WTF.field('jobHours', 'string', true),
		WTF.field('workReportHours', 'string', true),
		WTF.calcField('totHours', 'string', ['workingHours', 'overtime'], function(v, rec) {
			return Sonicle.webtop.drm.model.GridTimetableReport.calcTotHours(
					rec.get('workingHours'),
					rec.get('overtime')
			);
		}),
		WTF.field('sickness', 'string', true)
	],
	
	statics: {
		calcTotHours: function(workingHours, overtime) {
			var wh = 0;
			var oh = 0;
			
			if (workingHours !== null) {
				var h = workingHours.split('.');
				var wh = (+h[0]) * 60 + (+h[1]);				
			}
			if (overtime !== null) {
				var h = overtime.split('.');
				var oh = (+h[0]) * 60 + (+h[1]);				
			}
			if (wh !== 0 || oh !== 0) {
				var th = wh + oh;
				var hours = Math.floor(th / 60);
				var minutes = th % 60;
				return Sonicle.webtop.drm.model.GridTimetableReport.pad(hours, 2) + "." + Sonicle.webtop.drm.model.GridTimetableReport.pad(minutes, 2);				
			} else {
				return null;
			}		
		},
		
		pad: function (num, size) {
			var s = num + "";
			while (s.length < size) s = "0" + s;
			return s;
		}
	}
});
