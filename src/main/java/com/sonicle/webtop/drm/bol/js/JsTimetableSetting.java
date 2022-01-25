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
 * types.
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

import com.sonicle.webtop.drm.model.TimetableSetting;

/**
 *
 * @author lssndrvs
 */
public class JsTimetableSetting {
	public String id = "tmtb";
	public Integer timetableSettingId;
	public String domainId;
	public String allowedAddresses;
	public String allowedUsers;
	public String staffOfficeEmail;
	public Boolean requestsHolidaysPermitsPreviousDates;
	public String totalToleranceInMinutes;
	public String rounding;
	public String minimumExtraordinary;
	public Boolean companyExit;
	public Boolean manageStamp;
	public Boolean breakAnomaly;
	public Boolean readOnlyEvents;
	public Boolean requestsPermitsNotRemunered;
	public Boolean requestsPermitsMedicalVisits;
	public Boolean requestsPermitsContractuals;
	public Boolean medicalVisitsAutomaticallyApproved;
	public String calendarUserId;
	public Integer defaultEventActivityId;
	public Boolean sicknessAutomaticallyApproved;
	public Boolean requestsSickness;
	public String defaultCausalWorkingHours;
	public String defaultCausalOvertime;
	public String defaultCausalPermits;
	public String defaultCausalHolidays;
	public String defaultCausalSickness;
	
	// public List<JsGridHolidayDate> holidayDates = new ArrayList();

	public JsTimetableSetting(TimetableSetting setting) {
		this.timetableSettingId = setting.getTimetableSettingId();
		this.domainId = setting.getDomainId();
		this.allowedAddresses = setting.getAllowedAddresses();
		this.allowedUsers = setting.getAllowedUsers();		
		this.staffOfficeEmail = setting.getStaffOfficeEmail();
		this.requestsHolidaysPermitsPreviousDates = setting.getRequestsHolidaysPermitsPreviousDates();
		this.totalToleranceInMinutes = setting.getTotalToleranceInMinutes();
		this.rounding = setting.getRounding();
		this.minimumExtraordinary = setting.getMinimumExtraordinary();
		this.companyExit = setting.getCompanyExit();
		this.manageStamp = setting.getManageStamp();
		this.breakAnomaly = setting.getBreakAnomaly();
		this.readOnlyEvents = setting.getReadOnlyEvents();
		this.requestsPermitsNotRemunered = setting.getRequestsPermitsNotRemunered();
		this.requestsPermitsMedicalVisits = setting.getRequestsPermitsMedicalVisits();
		this.requestsPermitsContractuals = setting.getRequestsPermitsContractuals();
		this.medicalVisitsAutomaticallyApproved = setting.getMedicalVisitsAutomaticallyApproved();
		this.calendarUserId = setting.getCalendarUserId();
		this.defaultEventActivityId = setting.getDefaultEventActivityId();
		this.requestsSickness = setting.getRequestsSickness();
		this.sicknessAutomaticallyApproved = setting.getSicknessAutomaticallyApproved();
		this.defaultCausalWorkingHours = setting.getDefaultCausalWorkingHours();
		this.defaultCausalOvertime = setting.getDefaultCausalOvertime();
		this.defaultCausalPermits = setting.getDefaultCausalPermits();
		this.defaultCausalHolidays = setting.getDefaultCausalHolidays();
		this.defaultCausalSickness = setting.getDefaultCausalSickness();
		
        /*
		for (HolidayDate hd : setting.getHolidayDates()) {
			this.holidayDates.add(new JsGridHolidayDate(hd));
		}
        */
	}

	public static TimetableSetting createTimetableSetting(JsTimetableSetting js) {

		TimetableSetting tt = new TimetableSetting();
		tt.setTimetableSettingId(js.timetableSettingId);
		tt.setDomainId(js.domainId);
		tt.setAllowedAddresses(js.allowedAddresses);
		tt.setAllowedUsers(js.allowedUsers);
		tt.setStaffOfficeEmail(js.staffOfficeEmail);
		tt.setRequestsHolidaysPermitsPreviousDates(js.requestsHolidaysPermitsPreviousDates);
		tt.setTotalToleranceInMinutes(js.totalToleranceInMinutes);
		tt.setRounding(js.rounding);
		tt.setMinimumExtraordinary(js.minimumExtraordinary);
		tt.setCompanyExit(js.companyExit);
		tt.setManageStamp(js.manageStamp);
		tt.setBreakAnomaly(js.breakAnomaly);
		tt.setReadOnlyEvents(js.readOnlyEvents);
		tt.setRequestsPermitsNotRemunered(js.requestsPermitsNotRemunered);
		tt.setRequestsPermitsMedicalVisits(js.requestsPermitsMedicalVisits);
		tt.setRequestsPermitsContractuals(js.requestsPermitsContractuals);
		tt.setMedicalVisitsAutomaticallyApproved(js.medicalVisitsAutomaticallyApproved);
		tt.setCalendarUserId(js.calendarUserId);
		tt.setDefaultEventActivityId(js.defaultEventActivityId);
		tt.setRequestsSickness(js.requestsSickness);
		tt.setSicknessAutomaticallyApproved(js.sicknessAutomaticallyApproved);
		tt.setDefaultCausalWorkingHours(js.defaultCausalWorkingHours);
		tt.setDefaultCausalOvertime(js.defaultCausalOvertime);
		tt.setDefaultCausalPermits(js.defaultCausalPermits);
		tt.setDefaultCausalHolidays(js.defaultCausalHolidays);
		tt.setDefaultCausalSickness(js.defaultCausalSickness);
		
        /*
		for (JsGridHolidayDate hd : js.holidayDates) {
			tt.getHolidayDates().add(JsGridHolidayDate.createHolidayDate(hd));
		}
        */
        
		return tt;
	}
    
    /*
	public static HolidayDate createHolidayDate(JsGridHolidayDate jsHolidayDate) {
		if (jsHolidayDate == null) {
			return null;
		}

		HolidayDate hd = new HolidayDate();
		hd.setDomainId(jsHolidayDate.domainId);

		return hd;
		
	}
	
	public static ArrayList<HolidayDate> createHolidayDateList(JsTimetableSetting jstts) {
		ArrayList<HolidayDate> list = new ArrayList<>();
		for(JsGridHolidayDate jshd : jstts.holidayDates) {
			list.add(createHolidayDate(jshd));
		}
		return list;
	}
    */
}
