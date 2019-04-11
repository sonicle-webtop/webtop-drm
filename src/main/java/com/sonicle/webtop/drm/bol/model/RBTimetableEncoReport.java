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
public class RBTimetableEncoReport {
	
	public Integer id;
	public Integer companyId;
	public String companyDescription;
	public String userId;
	public String userDescription;
	public String date;
	public Float workingHours;
	public Float overtime;
	public Float paidLeave;
	public Float unpaidLeave;
	public Float holiday;
	public Float medicalVisit;
	public Float contractual;
	public String causal;
	public Float hour;
	public String detail;
	public String note;
		
	public RBTimetableEncoReport(CoreManager coreMgr, DrmManager drmMgr, OTimetableReport otr) throws WTException, IOException {		
		this.id = otr.getId();
		this.companyId = otr.getCompanyId();
		this.companyDescription = drmMgr.getCompany(otr.getCompanyId()).getName();
		this.userId = otr.getUserId();
		this.userDescription = WT.getUserData(new UserProfileId(otr.getDomainId(), otr.getUserId())).getDisplayName();
		this.date = concatDate(otr);
		this.workingHours = convertInMinutes(otr.getWorkingHours());
		this.overtime = convertInMinutes(otr.getOvertime());
		this.paidLeave = convertInMinutes(otr.getPaidLeave());
		this.unpaidLeave = convertInMinutes(otr.getUnpaidLeave());
		this.holiday = convertInMinutes(otr.getHoliday());
		this.medicalVisit = convertInMinutes(otr.getMedicalVisit());
		this.contractual = convertInMinutes(otr.getContractual());
		this.causal = otr.getCausal();
		this.hour = convertInMinutes(otr.getHour());
		this.detail = otr.getDetail();
		this.note = otr.getNote();
	}

	private Float convertInMinutes(String hour){
		Integer minutes;
		
		if(null == hour){
			return null;
		}
		else {
			String [] parts = hour.split("\\.");
			if(parts.length > 1)
				minutes = ((Integer.parseInt(parts[0])*60) + Integer.parseInt(parts[1]));
			else 
				minutes = Integer.parseInt(parts[0])*60;
			
			return minutes.floatValue();
		}
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

	public Float getWorkingHours() {
		return workingHours;
	}

	public void setWorkingHours(Float workingHours) {
		this.workingHours = workingHours;
	}

	public Float getOvertime() {
		return overtime;
	}

	public void setOvertime(Float overtime) {
		this.overtime = overtime;
	}

	public Float getPaidLeave() {
		return paidLeave;
	}

	public void setPaidLeave(Float paidLeave) {
		this.paidLeave = paidLeave;
	}

	public Float getUnpaidLeave() {
		return unpaidLeave;
	}

	public void setUnpaidLeave(Float unpaidLeave) {
		this.unpaidLeave = unpaidLeave;
	}

	public Float getHoliday() {
		return holiday;
	}

	public void setHoliday(Float holiday) {
		this.holiday = holiday;
	}

	public Float getMedicalVisit() {
		return medicalVisit;
	}

	public void setMedicalVisit(Float medicalVisit) {
		this.medicalVisit = medicalVisit;
	}

	public Float getContractual() {
		return contractual;
	}

	public void setContractual(Float contractual) {
		this.contractual = contractual;
	}

	public String getCausal() {
		return causal;
	}

	public void setCausal(String causal) {
		this.causal = causal;
	}

	public Float getHour() {
		return hour;
	}

	public void setHour(Float hour) {
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
