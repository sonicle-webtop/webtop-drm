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

import com.sonicle.webtop.core.sdk.BaseServiceSettings;
import static com.sonicle.webtop.core.sdk.BaseServiceSettings.DEFAULT_PREFIX;
import static com.sonicle.webtop.drm.DrmSettings.*;
import org.joda.time.LocalTime;

/**
 *
 * @author lssndrvs
 */
public class DrmServiceSettings extends BaseServiceSettings {

	public DrmServiceSettings(String serviceId, String domainId) {
		super(serviceId, domainId);
	}

	public boolean isUseProgramPermissions() {
		return getBoolean(USE_PROGRAM_PERMISSIONS, Boolean.FALSE); 
	}
	
	public boolean getUseStatisticCustomer() {
		return getBoolean(USE_STATISTIC_CUSTOMER, Boolean.FALSE); 
	}

	public void setUseStatisticCustomer(boolean enabled) {
		setBoolean(USE_STATISTIC_CUSTOMER, enabled); 
	}

	public boolean getPrintDaysTransfert() {
		return getBoolean(PRINT_DAYS_TRANSFERT, Boolean.FALSE); 
	}

	public void setPrintDaysTransfert(boolean enabled) {
		setBoolean(PRINT_DAYS_TRANSFERT, enabled); 
	}

	public boolean getPrintTransfertDescription() {
		return getBoolean(PRINT_TRANSFERT_DESCRIPTION, Boolean.FALSE); 
	}

	public void setPrintTransfertDescription(boolean enabled) {
		setBoolean(PRINT_TRANSFERT_DESCRIPTION, enabled); 
	}

	public boolean getPrintSignature() {
		return getBoolean(PRINT_SIGNATURE, Boolean.FALSE); 
	}

	public void setPrintSignature(boolean enabled) {
		setBoolean(PRINT_SIGNATURE, enabled);
	}

	public Integer getRoundingHour() {
		return getInteger(ROUNDING_HOUR, -1);
	}

	public void setRoundingHour(Integer rounding) {
		setInteger(ROUNDING_HOUR, rounding == null ? -1 : rounding);
	}

	public boolean getTracking() {
		return getBoolean(TRACKING, Boolean.FALSE);
	}

	public void setTracking(boolean enabled) {
		setBoolean(TRACKING, enabled);
	}

	public boolean getMailTracking() {
		return getBoolean(MAIL_TRACKING, Boolean.FALSE);
	}

	public void setMailTracking(boolean enabled) {
		setBoolean(MAIL_TRACKING, enabled);
	}

	public boolean getCloudTracking() {
		return getBoolean(CLOUD_TRACKING, Boolean.FALSE);
	}

	public void setCloudTracking(boolean enabled) {
		setBoolean(CLOUD_TRACKING, enabled);
	}
	
	public boolean getDefaultApplySignature() {
		return getBoolean(DEFAULT_APPLY_SIGNATURE, Boolean.FALSE); 
	}

	public void setDefaultApplySignature(boolean enabled) {
		setBoolean(DEFAULT_APPLY_SIGNATURE, enabled); 
	}
	
	public boolean getDefaultChargeTo() {
		return getBoolean(DEFAULT_CHARGE_TO, Boolean.FALSE);
	}

	public void setDefaultChargeTo(boolean enabled) {
		setBoolean(DEFAULT_CHARGE_TO, enabled); 
	}
	
	public boolean getDefaultFreeSupport() {
		return getBoolean(DEFAULT_FREE_SUPPORT, Boolean.FALSE); 
	}

	public void setDefaultFreeSupport(boolean enabled) {
		setBoolean(DEFAULT_FREE_SUPPORT, enabled); 
	}
	
	public String getWorkReportDefaultDocStatusId() {
		return getString(WORK_REPORT_DEFAULT_DOC_STATUS_ID, ""); 
	}

	public void setWorkReportDefaultDocStatusId(String value) {
		setString(WORK_REPORT_DEFAULT_DOC_STATUS_ID, value); 
	}
	
	public String getOpportunityDefaultDocStatusId() {
		return getString(OPPORTUNITY_DEFAULT_DOC_STATUS_ID, ""); 
	}

	public void setOpportunityDefaultDocStatusId(String value) {
		setString(OPPORTUNITY_DEFAULT_DOC_STATUS_ID, value); 
	}
	
	public String getOpportunityGeneralTitle() {
		return getString(OPPORTUNITY_GENERAL_TITLE, ""); 
	}
	
	public void setOpportunityGeneralTitle(String value) {
		setString(OPPORTUNITY_GENERAL_TITLE, value); 
	}
	
	public boolean getOpportunityGeneralEnablePrint() {
		return getBoolean(OPPORTUNITY_GENERAL_ENABLE_PRINT, Boolean.FALSE); 
	}
	
	public void setOpportunityGeneralEnablePrint(boolean value) {
		setBoolean(OPPORTUNITY_GENERAL_ENABLE_PRINT, value); 
	}
	
	public boolean getMedicalVisitsAutomaticallyApproved() {
		return getBoolean(LEAVE_REQUEST_MEDICAL_VISITS_AUTOMATICALLY_APPROVED, Boolean.FALSE); 
	}

	public void setMedicalVisitsAutomaticallyApproved(boolean value) {
		setBoolean(LEAVE_REQUEST_MEDICAL_VISITS_AUTOMATICALLY_APPROVED, value); 
	}
	
	public LocalTime getDefaultWorkdayStart() {
		return getTime(DEFAULT_PREFIX + WORKDAY_START, "09:00", "HH:mm");
	}
	
	public LocalTime getDefaultWorkdayEnd() {
		return getTime(DEFAULT_PREFIX + WORKDAY_END, "18:00", "HH:mm");
	}
	
	public String getTicketDefaultDocStatusId() {
		return getString(TICKET_DEFAULT_DOC_STATUS_ID, ""); 
	}

	public void setTicketDefaultDocStatusId(String value) {
		setString(TICKET_DEFAULT_DOC_STATUS_ID, value); 
	}
	
	public String getTicketDefaultPriorityId() {
		return getString(TICKET_DEFAULT_PRIORITY_ID, ""); 
	}

	public void setTicketDefaultPriorityId(String value) {
		setString(TICKET_DEFAULT_PRIORITY_ID, value); 
	}
	
	public String getTicketDefaultTicketCategoryId() {
		return getString(TICKET_DEFAULT_TICKET_CATEGORY_ID, ""); 
	}

	public void setTicketDefaultTicketCategoryId(String value) {
		setString(TICKET_DEFAULT_TICKET_CATEGORY_ID, value); 
	}
	
	public String getTicketDefaultCloseDocStatusId() {
		return getString(TICKET_DEFAULT_CLOSE_DOC_STATUS_ID, ""); 
	}

	public void setTicketDefaultCloseDocStatusId(String value) {
		setString(TICKET_DEFAULT_CLOSE_DOC_STATUS_ID, value); 
	}
	
	public boolean getSicknessAutomaticallyApproved() {
		return getBoolean(LEAVE_REQUEST_SICKNESS_AUTOMATICALLY_APPROVED, Boolean.FALSE); 
	}

	public void setSicknessAutomaticallyApproved(boolean value) {
		setBoolean(LEAVE_REQUEST_SICKNESS_AUTOMATICALLY_APPROVED, value); 
	}
	
	public boolean getIntegrationGis() {
		return getBoolean(INTEGRATION_GIS, Boolean.FALSE);
	}
	
	public boolean getIntegrationTS() {
		return getBoolean(INTEGRATION_TS, Boolean.FALSE);
	}
	
	public String getGisCompanyCode() {
		return getString(GIS_COMPANY_CODE, ""); 
	}

	public void setGisCompanyCode(String value) {
		setString(GIS_COMPANY_CODE, value); 
	}
	
	public String getTSCompanyCode() {
		return getString(TS_COMPANY_CODE, ""); 
	}

	public void setTSCompanyCode(String value) {
		setString(TS_COMPANY_CODE, value); 
	}
	
	public boolean isTSComposedEmployeeCode() {
		return getBoolean(TS_COMPOSED_EMPLOYEE_CODE, false);
	}
	
	public void setTSComposedEmployeeCode(Boolean b) {
		setBoolean(TS_COMPOSED_EMPLOYEE_CODE, b);
	}
}
