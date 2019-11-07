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
package com.sonicle.webtop.drm.bol.js;

import com.sonicle.webtop.core.app.WT;
import com.sonicle.webtop.core.sdk.UserProfileId;
import com.sonicle.webtop.core.sdk.WTException;
import com.sonicle.webtop.drm.DrmManager;
import com.sonicle.webtop.drm.bol.OTimetableReport;
import com.sonicle.webtop.drm.model.TimetableReport;

/**
 *
 * @author lssndrvs
 */
public class JsGridTimetableReport {

	public Integer id;
	public String domainId;
	public String user;
	public String company;
	public String date;
	public String workingHours;
	public String overtime;
	public String paidLeave;
	public String unpaidLeave;
	public String holiday;
	public String medicalVisit;
	public String contractual;
	public String causal;
	public String hour;
	public String detail;
	public String note;
	public String targetUser;

	public JsGridTimetableReport(OTimetableReport tr, DrmManager drmMgr) throws WTException {
		this.id = tr.getId();
		this.domainId = tr.getDomainId();
		this.user = WT.getUserData(new UserProfileId(tr.getDomainId(), tr.getUserId())).getDisplayName();
		this.company = drmMgr.getCompany(tr.getCompanyId()).getName();
		this.date = concatDate(tr);
		this.workingHours =tr.getWorkingHours();
		this.overtime = tr.getOvertime();
		this.paidLeave = tr.getPaidLeave();
		this.unpaidLeave = tr.getUnpaidLeave();
		this.holiday = tr.getHoliday();
		this.medicalVisit = tr.getMedicalVisit();
		this.contractual = tr.getContractual();
		this.causal = tr.getCausal();
		this.hour = tr.getHour();
		this.detail = tr.getDetail();
		this.note = tr.getNote();
		this.targetUser = WT.getUserData(new UserProfileId(tr.getDomainId(), tr.getTargetUserId())).getDisplayName();
	}
	
	public static TimetableReport createTimetableReport(JsGridTimetableReport js) {

		TimetableReport tr = new TimetableReport();
		tr.setId(js.id);
		tr.setWorkingHours(js.workingHours);
		tr.setOvertime(js.overtime);
		tr.setPaidLeave(js.paidLeave);
		tr.setUnpaidLeave(js.unpaidLeave);
		tr.setHoliday(js.holiday);
		tr.setMedicalVisit(js.medicalVisit);
		tr.setContractual(js.contractual);
		tr.setCausal(js.causal);
		tr.setHour(js.hour);
		tr.setDetail(js.detail);
		tr.setNote(js.note);

		return tr;
	}
	
	private String concatDate(OTimetableReport otr){		
		switch(otr.getDate().getDayOfWeek()){
			case 1:
				return ((otr.getDate().getDayOfMonth() < 10) ? "0" + otr.getDate().getDayOfMonth() : otr.getDate().getDayOfMonth()) + "/" + ((otr.getDate().getMonthOfYear() < 10) ? "0" + otr.getDate().getMonthOfYear() : otr.getDate().getMonthOfYear()) + "/" + otr.getDate().getYear() + " Lun";
			case 2: 
				return ((otr.getDate().getDayOfMonth() < 10) ? "0" + otr.getDate().getDayOfMonth() : otr.getDate().getDayOfMonth()) + "/" + ((otr.getDate().getMonthOfYear() < 10) ? "0" + otr.getDate().getMonthOfYear() : otr.getDate().getMonthOfYear()) + "/" + otr.getDate().getYear() + " Mar";
			case 3: 
				return ((otr.getDate().getDayOfMonth() < 10) ? "0" + otr.getDate().getDayOfMonth() : otr.getDate().getDayOfMonth()) + "/" + ((otr.getDate().getMonthOfYear() < 10) ? "0" + otr.getDate().getMonthOfYear() : otr.getDate().getMonthOfYear()) + "/" + otr.getDate().getYear() + " Mer";
			case 4: 
				return ((otr.getDate().getDayOfMonth() < 10) ? "0" + otr.getDate().getDayOfMonth() : otr.getDate().getDayOfMonth()) + "/" + ((otr.getDate().getMonthOfYear() < 10) ? "0" + otr.getDate().getMonthOfYear() : otr.getDate().getMonthOfYear()) + "/" + otr.getDate().getYear() + " Gio";
			case 5: 
				return ((otr.getDate().getDayOfMonth() < 10) ? "0" + otr.getDate().getDayOfMonth() : otr.getDate().getDayOfMonth()) + "/" + ((otr.getDate().getMonthOfYear() < 10) ? "0" + otr.getDate().getMonthOfYear() : otr.getDate().getMonthOfYear()) + "/" + otr.getDate().getYear() + " Ven";
			case 6: 
				return ((otr.getDate().getDayOfMonth() < 10) ? "0" + otr.getDate().getDayOfMonth() : otr.getDate().getDayOfMonth()) + "/" + ((otr.getDate().getMonthOfYear() < 10) ? "0" + otr.getDate().getMonthOfYear() : otr.getDate().getMonthOfYear()) + "/" + otr.getDate().getYear() + " Sab";
			case 7: 
				return ((otr.getDate().getDayOfMonth() < 10) ? "0" + otr.getDate().getDayOfMonth() : otr.getDate().getDayOfMonth()) + "/" + ((otr.getDate().getMonthOfYear() < 10) ? "0" + otr.getDate().getMonthOfYear() : otr.getDate().getMonthOfYear()) + "/" + otr.getDate().getYear() + " Dom";
			default: 
				return ((otr.getDate().getDayOfMonth() < 10) ? "0" + otr.getDate().getDayOfMonth() : otr.getDate().getDayOfMonth()) + "/" + ((otr.getDate().getMonthOfYear() < 10) ? "0" + otr.getDate().getMonthOfYear() : otr.getDate().getMonthOfYear()) + "/" + otr.getDate().getYear() + " ";
		}
	}

}
