/* 
 * Copyright (C) 2014 Sonicle S.r.l.
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
 * display the words "Copyright (C) 2014 Sonicle S.r.l.".
 */
package com.sonicle.webtop.drm.bol.model;

import com.sonicle.webtop.core.CoreManager;
import com.sonicle.webtop.core.app.WT;
import com.sonicle.webtop.core.sdk.UserProfileId;
import com.sonicle.webtop.core.sdk.WTException;
import com.sonicle.webtop.drm.DrmManager;
import com.sonicle.webtop.drm.bol.OTimetableReport;
import java.io.IOException;

/**
 *
 * @author malbinola
 */
public class RBTimetableReport {
	
	public Integer id;
	public Integer companyId;
	public String companyDescription;
	public String userId;
	public String userDescription;
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
		
	public RBTimetableReport(CoreManager coreMgr, DrmManager drmMgr, OTimetableReport otr) throws WTException, IOException {		
		this.id = otr.getId();
		this.companyId = otr.getCompanyId();
		this.companyDescription = drmMgr.getCompany(otr.getCompanyId()).getName();
		this.userId = otr.getUserId();
		this.userDescription = WT.getUserData(new UserProfileId(otr.getDomainId(), otr.getUserId())).getDisplayName();
		this.date = concatDate(otr);
		this.workingHours = otr.getWorkingHours();
		this.overtime = otr.getOvertime();
		this.paidLeave = otr.getPaidLeave();
		this.unpaidLeave = otr.getUnpaidLeave();
		this.holiday = otr.getHoliday();
		this.medicalVisit = otr.getMedicalVisit();
		this.contractual = otr.getContractual();
		this.causal = otr.getCausal();
		this.hour = otr.getHour();
		this.detail = otr.getDetail();
		this.note = otr.getNote();
	}	
	
	private String concatDate(OTimetableReport otr){		
		switch(otr.getDate().getDayOfWeek()){
			case 1:
				return otr.getDate().getDayOfMonth() + "/" + otr.getDate().getMonthOfYear() + "/" + otr.getDate().getYear() + " Lunedì";
			case 2: 
				return otr.getDate().getDayOfMonth() + "/" + otr.getDate().getMonthOfYear() + "/" + otr.getDate().getYear() + " Martedì";
			case 3: 
				return otr.getDate().getDayOfMonth() + "/" + otr.getDate().getMonthOfYear() + "/" + otr.getDate().getYear() + " Mercoledì";
			case 4: 
				return otr.getDate().getDayOfMonth() + "/" + otr.getDate().getMonthOfYear() + "/" + otr.getDate().getYear() + " Giovedì";
			case 5: 
				return otr.getDate().getDayOfMonth() + "/" + otr.getDate().getMonthOfYear() + "/" + otr.getDate().getYear() + " Venerdì";
			case 6: 
				return otr.getDate().getDayOfMonth() + "/" + otr.getDate().getMonthOfYear() + "/" + otr.getDate().getYear() + " Sabato";
			case 7: 
				return otr.getDate().getDayOfMonth() + "/" + otr.getDate().getMonthOfYear() + "/" + otr.getDate().getYear() + " Domenica";
			default: 
				return otr.getDate().getDayOfMonth() + "/" + otr.getDate().getMonthOfYear() + "/" + otr.getDate().getYear() + " ";
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getCompanyDescription() {
		return companyDescription;
	}

	public void setCompanyDescription(String companyDescription) {
		this.companyDescription = companyDescription;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserDescription() {
		return userDescription;
	}

	public void setUserDescription(String userDescription) {
		this.userDescription = userDescription;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getWorkingHours() {
		return workingHours;
	}

	public void setWorkingHours(String workingHours) {
		this.workingHours = workingHours;
	}

	public String getOvertime() {
		return overtime;
	}

	public void setOvertime(String overtime) {
		this.overtime = overtime;
	}

	public String getPaidLeave() {
		return paidLeave;
	}

	public void setPaidLeave(String paidLeave) {
		this.paidLeave = paidLeave;
	}

	public String getUnpaidLeave() {
		return unpaidLeave;
	}

	public void setUnpaidLeave(String unpaidLeave) {
		this.unpaidLeave = unpaidLeave;
	}

	public String getHoliday() {
		return holiday;
	}

	public void setHoliday(String holiday) {
		this.holiday = holiday;
	}

	public String getMedicalVisit() {
		return medicalVisit;
	}

	public void setMedicalVisit(String medicalVisit) {
		this.medicalVisit = medicalVisit;
	}

	public String getContractual() {
		return contractual;
	}

	public void setContractual(String contractual) {
		this.contractual = contractual;
	}

	public String getCausal() {
		return causal;
	}

	public void setCausal(String causal) {
		this.causal = causal;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
