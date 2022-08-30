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

import com.sonicle.commons.time.DateTimeUtils;
import com.sonicle.webtop.core.CoreManager;
import com.sonicle.webtop.core.app.WT;
import com.sonicle.webtop.core.sdk.UserProfileId;
import com.sonicle.webtop.core.sdk.WTException;
import com.sonicle.webtop.drm.DrmManager;
import com.sonicle.webtop.drm.bol.OCausal;
import com.sonicle.webtop.drm.bol.OTimetableReport;
import com.sonicle.webtop.drm.model.Causal;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormatter;

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
	public String targetUserId;
	public String targetUserDescription;
	public Float jobHours;
	public Float workReportHours;
	public Float totHours;
	public Float sickness;
	public Float other;
	public String gisCausalCode;
	public String gisCausalDescription;
	public Integer ticket;
	public Boolean ticketManagement;
	
	public RBTimetableReport(CoreManager coreMgr, DrmManager drmMgr, OTimetableReport otr, Locale lcl) throws WTException, IOException {		
		this.id = otr.getId();
		this.companyId = otr.getCompanyId();
		this.companyDescription = drmMgr.getCompany(otr.getCompanyId()).getName();
		this.userId = otr.getUserId();
		this.userDescription = WT.getUserData(new UserProfileId(otr.getDomainId(), otr.getUserId())).getDisplayName();
		this.date = concatDate(otr.getDate(), lcl);
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
		this.targetUserId = otr.getTargetUserId();
		this.targetUserDescription = WT.getUserData(new UserProfileId(otr.getDomainId(), otr.getTargetUserId())).getDisplayName();
		this.workReportHours = convertInMinutes(otr.getWorkReportHours());
		this.jobHours = convertInMinutes(otr.getJobHours());
		this.totHours = ((this.workingHours == null) ? 0 : this.workingHours) + ((this.overtime == null) ? 0 : this.overtime);
		this.totHours = (this.totHours == 0) ? null : this.totHours;
		this.sickness = convertInMinutes(otr.getSickness());
		this.other = convertInMinutes(otr.getOther());
		if (otr.getCausalId() != null){
			this.gisCausalCode = drmMgr.getCausal(otr.getCausalId()).getExternalCode();
			this.gisCausalDescription = drmMgr.getCausal(otr.getCausalId()).getDescription();
		}
		this.ticket = calcTicket(drmMgr, otr);
		this.ticketManagement = drmMgr.getTimetableSetting().getTicketManagement();
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

	private Integer calcTicket(DrmManager drmMgr, OTimetableReport otr) throws WTException{
		Integer tkt = 0;
		Boolean tktManagement = drmMgr.getTimetableSetting().getTicketManagement();
		
		if(tktManagement){
			Integer minHourForTkt = drmMgr.getEmployeeProfile(otr.getDomainId(), otr.getUserId()).minimumNumberOfHoursPerTicket == null ? drmMgr.getTimetableSetting().getMinimumNumberOfHoursPerTicket() : drmMgr.getEmployeeProfile(otr.getDomainId(), otr.getUserId()).minimumNumberOfHoursPerTicket;

			if(minHourForTkt != null){
				Integer sign = otr.getCausalId() == null ? null : drmMgr.getCausal(otr.getCausalId()).getSign();
				Integer wh = 0;
				Integer ph = 0;
				Integer uh = 0;
				Integer mh = 0;
				Integer ch = 0;
				Integer sh = 0;
				Integer ov = 0;
				Integer hh = 0;
				Integer ot = 0;

				if (otr.getWorkingHours() != null) {
					String[] h = otr.getWorkingHours().split("\\.");
					wh = (+Integer.parseInt(h[0])) * 60 + (+Integer.parseInt(h[1]));
				}
				if (otr.getPaidLeave()!= null) {
					String[] h = otr.getPaidLeave().split("\\.");
					ph = (+Integer.parseInt(h[0])) * 60 + (+Integer.parseInt(h[1]));
				}
				if (otr.getUnpaidLeave()!= null) {
					String[] h = otr.getUnpaidLeave().split("\\.");
					uh = (+Integer.parseInt(h[0])) * 60 + (+Integer.parseInt(h[1]));
				}
				if (otr.getMedicalVisit()!= null) {
					String[] h = otr.getMedicalVisit().split("\\.");
					mh = (+Integer.parseInt(h[0])) * 60 + (+Integer.parseInt(h[1]));
				}
				if (otr.getContractual()!= null) {
					String[] h = otr.getContractual().split("\\.");
					ch = (+Integer.parseInt(h[0])) * 60 + (+Integer.parseInt(h[1]));
				}
				if (otr.getSickness()!= null) {
					String[] h = otr.getSickness().split("\\.");
					sh = (+Integer.parseInt(h[0])) * 60 + (+Integer.parseInt(h[1]));
				}
				if (otr.getOvertime()!= null) {
					String[] h = otr.getOvertime().split("\\.");
					ov = (+Integer.parseInt(h[0])) * 60 + (+Integer.parseInt(h[1]));
				}
				if (otr.getHoliday()!= null) {
					String[] h = otr.getHoliday().split("\\.");
					hh = (+Integer.parseInt(h[0])) * 60 + (+Integer.parseInt(h[1]));
				}
				if (otr.getOther()!= null) {
					String[] h = otr.getOther().split("\\.");
					ot = (+Integer.parseInt(h[0])) * 60 + (+Integer.parseInt(h[1]));
				}
				if(otr.getTotalLineHour() != null){
					Integer th = wh - ph - uh - mh - ch - sh - hh;
					th = th + ov;

					if(sign != null){
						if(sign == -1) th = th - ot;
						else if(sign == 1) th = th + ot;
					}

					minHourForTkt = minHourForTkt * 60;

					if(th >= minHourForTkt){ 
						return 1;
					}else{ 
						return null;
					}	
				}else {
					return null;
				}
			}	
		}

		return tkt;
	}
	
	private String concatDate(LocalDateTime dtDate, Locale lcl){		
		// SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy EEE", lcl);
        
        DateTimeFormatter dtFm = DateTimeUtils.createFormatter("dd/MM/yyyy EEE").withLocale(lcl);
        
        return dtFm.print(dtDate);
		
		/*
		switch(dtDate.getDayOfWeek()){
			case 1:
				return dtDate.getDayOfMonth() + "/" + dtDate.getMonthOfYear() + "/" + dtDate.getYear() + " Lunedì";
			case 2: 
				return dtDate.getDayOfMonth() + "/" + dtDate.getMonthOfYear() + "/" + dtDate.getYear() + " Martedì";
			case 3: 
				return dtDate.getDayOfMonth() + "/" + dtDate.getMonthOfYear() + "/" + dtDate.getYear() + " Mercoledì";
			case 4: 
				return dtDate.getDayOfMonth() + "/" + dtDate.getMonthOfYear() + "/" + dtDate.getYear() + " Giovedì";
			case 5: 
				return dtDate.getDayOfMonth() + "/" + dtDate.getMonthOfYear() + "/" + dtDate.getYear() + " Venerdì";
			case 6: 
				return dtDate.getDayOfMonth() + "/" + dtDate.getMonthOfYear() + "/" + dtDate.getYear() + " Sabato";
			case 7: 
				return dtDate.getDayOfMonth() + "/" + dtDate.getMonthOfYear() + "/" + dtDate.getYear() + " Domenica";
			default: 
				return dtDate.getDayOfMonth() + "/" + dtDate.getMonthOfYear() + "/" + dtDate.getYear() + " ";
		}
		*/
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

	public String getTargetUserId() {
		return targetUserId;
	}

	public void setTargetUserId(String targetUserId) {
		this.targetUserId = targetUserId;
	}

	public String getTargetUserDescription() {
		return targetUserDescription;
	}

	public void setTargetUserDescription(String targetUserDescription) {
		this.targetUserDescription = targetUserDescription;
	}
	
	public Float getWorkReportHours() {
		return workReportHours;
	}

	public void setWorkReportHours(Float workReportHours) {
		this.workReportHours = workReportHours;
	}

	public Float getJobHours() {
		return jobHours;
	}

	public void setJobHours(Float jobHours) {
		this.jobHours = jobHours;
	}

	public Float getTotHours() {
		return totHours;
	}

	public void setTotHours(Float totHours) {
		this.totHours = totHours;
	}	
	
	public Float getSickness() {
		return sickness;
	}

	public void setSickness(Float sickness) {
		this.sickness = sickness;
	}

	public Float getOther() {
		return other;
	}

	public void setOther(Float other) {
		this.other = other;
	}

	public String getGisCausalCode() {
		return gisCausalCode;
	}

	public void setGisCausalCode(String gisCausalCode) {
		this.gisCausalCode = gisCausalCode;
	}

	public String getGisCausalDescription() {
		return gisCausalDescription;
	}

	public void setGisCausalDescription(String gisCausalDescription) {
		this.gisCausalDescription = gisCausalDescription;
	}

	public Integer getTicket() {
		return ticket;
	}

	public void setTicket(Integer ticket) {
		this.ticket = ticket;
	}

	public Boolean getTicketManagement() {
		return ticketManagement;
	}

	public void setTicketManagement(Boolean ticketManagement) {
		this.ticketManagement = ticketManagement;
	}
}
