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
		WTF.field('targetUserId', 'string', false),
		WTF.field('jobHours', 'string', true),
		WTF.field('workReportHours', 'string', true),
		WTF.field('sickness', 'string', true),
		WTF.field('other', 'string', true),
		WTF.field('causalId', 'string', true),
		WTF.field('causalOperation', 'string', true),
		WTF.field('totalLineHour', 'string', true),
		WTF.field('userId', 'string', false),
		WTF.field('dateObj', 'string'),
		WTF.field('hasRequest', 'boolean',true),
//		WTF.calcField('totHours', 'string', ['workingHours', 'overtime'], function(v, rec) {
//			return Sonicle.webtop.drm.model.GridTimetableReport.calcTotHours(
//					rec.get('workingHours'),
//					rec.get('overtime')
//			);
//		}),
		WTF.calcField('missingHours', 'string', ['workingHours', 'paidLeave', 'unpaidLeave', 'medicalVisit', 'contractual', 'sickness', 'overtime', 'holiday', 'other', 'causalId', 'totalLineHour'], function(v, rec) {
			return Sonicle.webtop.drm.model.GridTimetableReport.calcMissingHours(
					rec.get('workingHours'),
					rec.get('paidLeave'),
					rec.get('unpaidLeave'),
					rec.get('medicalVisit'),
					rec.get('contractual'),
					rec.get('sickness'),
					rec.get('overtime'),
					rec.get('holiday'),
					rec.get('other'),
					rec.get('causalId'),
					rec.get('totalLineHour')
			);
		}),
		WTF.calcField('ticket', 'int', ['workingHours', 'paidLeave', 'unpaidLeave', 'medicalVisit', 'contractual', 'sickness', 'overtime', 'holiday', 'other', 'causalId', 'totalLineHour', 'userId'], function(v, rec) {
			return Sonicle.webtop.drm.model.GridTimetableReport.calcTicket(
					rec.get('workingHours'),
					rec.get('paidLeave'),
					rec.get('unpaidLeave'),
					rec.get('medicalVisit'),
					rec.get('contractual'),
					rec.get('sickness'),
					rec.get('overtime'),
					rec.get('holiday'),
					rec.get('other'),
					rec.get('causalId'),
					rec.get('totalLineHour'),
					rec.get('userId'),
					rec.get('detail')?rec.get('detail').includes('[S]'):false
			);
		})
	],
	
	statics: {
//		calcTotHours: function(workingHours, overtime) {
//			var wh = 0;
//			var oh = 0;
//			
//			if (workingHours !== null) {
//				var h = workingHours.split('.');
//				var wh = (+h[0]) * 60 + (+h[1]);				
//			}
//			if (overtime !== null) {
//				var h = overtime.split('.');
//				var oh = (+h[0]) * 60 + (+h[1]);				
//			}
//			if (wh !== 0 || oh !== 0) {
//				var th = wh + oh;
//				var hours = Math.floor(th / 60);
//				var minutes = th % 60;
//				return Sonicle.webtop.drm.model.GridTimetableReport.pad(hours, 2) + "." + Sonicle.webtop.drm.model.GridTimetableReport.pad(minutes, 2);				
//			} else {
//				return null;
//			}		
//		},
		
		calcMissingHours: function(workingHours, paidLeave, unpaidLeave, medicalVisit, contractual, sickness, overtime, holiday,  other, causalId, totalLineHour) {
			var hs = WT.getVar('com.sonicle.webtop.drm', 'causalsOperation');
			var ao = WT.getVar('com.sonicle.webtop.drm', 'automaticOvertime');
			
			//if(ao !== true){
				var sign = hs[causalId];
				var wh = 0;
				var ph = 0;
				var uh = 0;
				var mh = 0;
				var ch = 0;
				var sh = 0;
				var ov = 0;
				var hh = 0;
				var ot = 0;
				var ret;

				if (workingHours !== null) {
					var h = workingHours.split('.');
					wh = (+h[0]) * 60 + (+h[1]);
				}
				if (paidLeave !== null) {
					var h = paidLeave.split('.');
					ph = (+h[0]) * 60 + (+h[1]);
				}
				if (unpaidLeave !== null) {
					var h = unpaidLeave.split('.');
					uh = (+h[0]) * 60 + (+h[1]);
				}
				if (medicalVisit !== null) {
					var h = medicalVisit.split('.');
					mh = (+h[0]) * 60 + (+h[1]);
				}
				if (contractual !== null) {
					var h = contractual.split('.');
					ch = (+h[0]) * 60 + (+h[1]);
				}
				if (sickness !== null) {
					var h = sickness.split('.');
					sh = (+h[0]) * 60 + (+h[1]);
				}
				if (overtime !== null) {
					var h = overtime.split('.');
					ov = (+h[0]) * 60 + (+h[1]);
				}
				if (holiday !== null) {
					var h = holiday.split('.');
					hh = (+h[0]) * 60 + (+h[1]);
				}
				if (other !== null) {
					var h = other.split('.');
					ot = (+h[0]) * 60 + (+h[1]);
				}
				if(totalLineHour !== null){
					if (wh !== 0 || ph !== 0 || uh !== 0 || mh !== 0 || ch !== 0 || sh !== 0 || hh !== 0 || ot !== 0) {
						var th = totalLineHour - wh - ph - uh - mh - ch - sh - hh;
						th = th + ov;

						if(sign === -1) th = th - ot;
						else if(sign === 1) th = th + ot;

						if(th < 0){
							th = Math.abs(th);
							var hours = Math.floor(th / 60);
							var minutes = Math.abs(th % 60);
							hours = Math.abs(hours);
							ret = '+'+Sonicle.webtop.drm.model.GridTimetableReport.pad(hours, 2) + "." + Sonicle.webtop.drm.model.GridTimetableReport.pad(minutes, 2);
						}else{
							var hours = Math.floor(th / 60);
							var minutes = Math.abs(th % 60);
							var chsign=(th===0)?'&nbsp;':'-';
							ret = chsign+Sonicle.webtop.drm.model.GridTimetableReport.pad(hours, 2) + "." + Sonicle.webtop.drm.model.GridTimetableReport.pad(minutes, 2);
						}
						return ret;
					} else {
						var hours = Math.floor(totalLineHour / 60);
						var minutes = Math.abs(totalLineHour % 60);
						ret = '-'+Sonicle.webtop.drm.model.GridTimetableReport.pad(hours, 2) + "." + Sonicle.webtop.drm.model.GridTimetableReport.pad(minutes, 2);
						return ret;
					}
				}else {
					return null;
				}
			//}else{
			//	return null;
			//}	
		},
		
		calcTicket: function(workingHours, paidLeave, unpaidLeave, medicalVisit, contractual, sickness, overtime, holiday,  other, causalId, totalLineHour, userId, isSmart) {
			var mt = WT.getVar('com.sonicle.webtop.drm', 'minimumNumberOfHoursPerTicket');		
			var hs = WT.getVar('com.sonicle.webtop.drm', 'causalsOperation');
			var sign = hs[causalId];
			var wh = 0;
			var ph = 0;
			var uh = 0;
			var mh = 0;
			var ch = 0;
			var sh = 0;
			var ov = 0;
			var hh = 0;
			var ot = 0;
			
			if (isSmart) return 0;
			
			if (workingHours !== null) {
				var h = workingHours.split('.');
				wh = (+h[0]) * 60 + (+h[1]);
			}
			if (paidLeave !== null) {
				var h = paidLeave.split('.');
				ph = (+h[0]) * 60 + (+h[1]);
			}
			if (unpaidLeave !== null) {
				var h = unpaidLeave.split('.');
				uh = (+h[0]) * 60 + (+h[1]);
			}
			if (medicalVisit !== null) {
				var h = medicalVisit.split('.');
				mh = (+h[0]) * 60 + (+h[1]);
			}
			if (contractual !== null) {
				var h = contractual.split('.');
				ch = (+h[0]) * 60 + (+h[1]);
			}
			if (sickness !== null) {
				var h = sickness.split('.');
				sh = (+h[0]) * 60 + (+h[1]);
			}
			if (overtime !== null) {
				var h = overtime.split('.');
				ov = (+h[0]) * 60 + (+h[1]);
			}
			if (holiday !== null) {
				var h = holiday.split('.');
				hh = (+h[0]) * 60 + (+h[1]);
			}
			if (other !== null) {
				var h = other.split('.');
				ot = (+h[0]) * 60 + (+h[1]);
			}
			if(totalLineHour !== null){
				var th = wh - ph - uh - mh - ch - sh - hh;
				th = th + ov;

				if(sign === -1) th = th - ot;
				else if(sign === 1) th = th + ot;
					
				var minHTkt = mt[userId];
				minHTkt = minHTkt * 60;
				
				if(th >= minHTkt){ 
					return 1;
				}else{ 
					return 0;
				}	
			}else {
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
