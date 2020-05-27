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
package com.sonicle.webtop.drm;

import com.sonicle.webtop.core.sdk.BaseUserSettings;
import com.sonicle.webtop.core.sdk.UserProfileId;
import static com.sonicle.webtop.drm.DrmSettings.*;
import org.joda.time.LocalTime;

/**
 *
 * @author lssndrvs
 */
public class DrmUserSettings extends BaseUserSettings {
	
	private DrmServiceSettings ss;

	public DrmUserSettings(String serviceId, UserProfileId profileId) {
		super(serviceId, profileId);
		this.ss = new DrmServiceSettings(serviceId, profileId.getDomainId());
	}
	
	public Integer getOpportunityCalendarId() {
		return getInteger(OPPORTUNITY_CALENDAR_ID, null); 
	}

	public void setOpportunityCalendarId(int value) {
		setInteger(OPPORTUNITY_CALENDAR_ID, value); 
	}
	
	public Integer getWorkReportCalendarId() {
		return getInteger(WORK_REPORT_CALENDAR_ID, null); 
	}
	
	public Integer getJobCalendarId() {
		return getInteger(JOB_CALENDAR_ID, null); 
	}
	
	public void setWorkReportCalendarId(int value) {
		setInteger(WORK_REPORT_CALENDAR_ID, value); 
	}

	public void setJobCalendarId(int value) {
		setInteger(JOB_CALENDAR_ID, value); 
	}
	
	public Integer getLeaveRequestCalendarId() {
		return getInteger(LEAVE_REQUEST_CALENDAR_ID, null); 
	}

	public void setLeaveRequestCalendarId(int value) {
		setInteger(LEAVE_REQUEST_CALENDAR_ID, value); 
	}
	
	public String getKmCost() {
		return getString(EXPENSE_NOTE_KM_COST, null); 
	}

	public void setKmCost(String value) {
		setString(EXPENSE_NOTE_KM_COST, value);
	}
	
	public String getDefaultCurrency() {
		return getString(EXPENSE_NOTE_DEFAULT_CURRENCY, null); 
	}

	public void setDefaultCurrency(String value) {
		setString(EXPENSE_NOTE_DEFAULT_CURRENCY, value);
	}
	
	public LocalTime getWorkdayStart() {
		LocalTime value = getTime(WORKDAY_START, (LocalTime)null, "HH:mm");
		if(value != null) return value;
		return ss.getDefaultWorkdayStart();
	}
	
	public boolean setWorkdayStart(LocalTime value) {
		return setTime(WORKDAY_START, value, "HH:mm");
	}
	
	public LocalTime getWorkdayEnd() {
		LocalTime value = getTime(WORKDAY_END, (LocalTime)null, "HH:mm");
		if(value != null) return value;
		return ss.getDefaultWorkdayEnd();
	}
	
	public boolean setWorkdayEnd(LocalTime value) {
		return setTime(WORKDAY_END, value, "HH:mm");
	}
	
	public String getTicketNotifyMail() {
		return getString(TICKET_NOTIFY_MAIL, null); 
	}

	public void setTicketNotifyMail(String value) {
		setString(TICKET_NOTIFY_MAIL, value);
	}
	
	public String getTicketAutomaticClose() {
		return getString(TICKET_AUTOMATIC_CLOSE, null); 
	}

	public void setTicketAutomaticClose(String value) {
		setString(TICKET_AUTOMATIC_CLOSE, value);
	}	
}
