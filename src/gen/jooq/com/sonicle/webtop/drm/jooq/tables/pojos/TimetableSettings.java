/**
 * This class is generated by jOOQ
 */
package com.sonicle.webtop.drm.jooq.tables.pojos;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.5.3"
	},
	comments = "This class is generated by jOOQ"
)
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TimetableSettings implements java.io.Serializable {

	private static final long serialVersionUID = 1348004085;

	private java.lang.Integer timetableSettingId;
	private java.lang.String  domainId;
	private java.lang.String  allowedAddresses;
	private java.lang.String  allowedUsers;
	private java.lang.String  staffOfficeEmail;
	private java.lang.Boolean requestsHolidaysPermitsPreviousDates;
	private java.lang.String  totalToleranceInMinutes;
	private java.lang.String  rounding;
	private java.lang.String  minimumExtraordinary;
	private java.lang.Boolean breakAnomaly;
	private java.lang.Boolean readOnlyEvents;
	private java.lang.Boolean requestsPermitsNotRemunered;
	private java.lang.Boolean requestsPermitsMedicalVisits;
	private java.lang.Boolean requestsPermitsContractuals;
	private java.lang.Boolean companyExit;
	private java.lang.Boolean manageStamp;
	private java.lang.Boolean medicalVisitsAutomaticallyApproved;
	private java.lang.String  calendarUserId;
	private java.lang.Integer defaultEventActivityId;
	private java.lang.Boolean requestsSickness;
	private java.lang.Boolean sicknessAutomaticallyApproved;
	private java.lang.String  defaultCausalWorkingHours;
	private java.lang.String  defaultCausalOvertime;
	private java.lang.String  defaultCausalPermits;
	private java.lang.String  defaultCausalHolidays;
	private java.lang.String  defaultCausalSickness;

	public TimetableSettings() {}

	public TimetableSettings(
		java.lang.Integer timetableSettingId,
		java.lang.String  domainId,
		java.lang.String  allowedAddresses,
		java.lang.String  allowedUsers,
		java.lang.String  staffOfficeEmail,
		java.lang.Boolean requestsHolidaysPermitsPreviousDates,
		java.lang.String  totalToleranceInMinutes,
		java.lang.String  rounding,
		java.lang.String  minimumExtraordinary,
		java.lang.Boolean breakAnomaly,
		java.lang.Boolean readOnlyEvents,
		java.lang.Boolean requestsPermitsNotRemunered,
		java.lang.Boolean requestsPermitsMedicalVisits,
		java.lang.Boolean requestsPermitsContractuals,
		java.lang.Boolean companyExit,
		java.lang.Boolean manageStamp,
		java.lang.Boolean medicalVisitsAutomaticallyApproved,
		java.lang.String  calendarUserId,
		java.lang.Integer defaultEventActivityId,
		java.lang.Boolean requestsSickness,
		java.lang.Boolean sicknessAutomaticallyApproved,
		java.lang.String  defaultCausalWorkingHours,
		java.lang.String  defaultCausalOvertime,
		java.lang.String  defaultCausalPermits,
		java.lang.String  defaultCausalHolidays,
		java.lang.String  defaultCausalSickness
	) {
		this.timetableSettingId = timetableSettingId;
		this.domainId = domainId;
		this.allowedAddresses = allowedAddresses;
		this.allowedUsers = allowedUsers;
		this.staffOfficeEmail = staffOfficeEmail;
		this.requestsHolidaysPermitsPreviousDates = requestsHolidaysPermitsPreviousDates;
		this.totalToleranceInMinutes = totalToleranceInMinutes;
		this.rounding = rounding;
		this.minimumExtraordinary = minimumExtraordinary;
		this.breakAnomaly = breakAnomaly;
		this.readOnlyEvents = readOnlyEvents;
		this.requestsPermitsNotRemunered = requestsPermitsNotRemunered;
		this.requestsPermitsMedicalVisits = requestsPermitsMedicalVisits;
		this.requestsPermitsContractuals = requestsPermitsContractuals;
		this.companyExit = companyExit;
		this.manageStamp = manageStamp;
		this.medicalVisitsAutomaticallyApproved = medicalVisitsAutomaticallyApproved;
		this.calendarUserId = calendarUserId;
		this.defaultEventActivityId = defaultEventActivityId;
		this.requestsSickness = requestsSickness;
		this.sicknessAutomaticallyApproved = sicknessAutomaticallyApproved;
		this.defaultCausalWorkingHours = defaultCausalWorkingHours;
		this.defaultCausalOvertime = defaultCausalOvertime;
		this.defaultCausalPermits = defaultCausalPermits;
		this.defaultCausalHolidays = defaultCausalHolidays;
		this.defaultCausalSickness = defaultCausalSickness;
	}

	public java.lang.Integer getTimetableSettingId() {
		return this.timetableSettingId;
	}

	public void setTimetableSettingId(java.lang.Integer timetableSettingId) {
		this.timetableSettingId = timetableSettingId;
	}

	public java.lang.String getDomainId() {
		return this.domainId;
	}

	public void setDomainId(java.lang.String domainId) {
		this.domainId = domainId;
	}

	public java.lang.String getAllowedAddresses() {
		return this.allowedAddresses;
	}

	public void setAllowedAddresses(java.lang.String allowedAddresses) {
		this.allowedAddresses = allowedAddresses;
	}

	public java.lang.String getAllowedUsers() {
		return this.allowedUsers;
	}

	public void setAllowedUsers(java.lang.String allowedUsers) {
		this.allowedUsers = allowedUsers;
	}

	public java.lang.String getStaffOfficeEmail() {
		return this.staffOfficeEmail;
	}

	public void setStaffOfficeEmail(java.lang.String staffOfficeEmail) {
		this.staffOfficeEmail = staffOfficeEmail;
	}

	public java.lang.Boolean getRequestsHolidaysPermitsPreviousDates() {
		return this.requestsHolidaysPermitsPreviousDates;
	}

	public void setRequestsHolidaysPermitsPreviousDates(java.lang.Boolean requestsHolidaysPermitsPreviousDates) {
		this.requestsHolidaysPermitsPreviousDates = requestsHolidaysPermitsPreviousDates;
	}

	public java.lang.String getTotalToleranceInMinutes() {
		return this.totalToleranceInMinutes;
	}

	public void setTotalToleranceInMinutes(java.lang.String totalToleranceInMinutes) {
		this.totalToleranceInMinutes = totalToleranceInMinutes;
	}

	public java.lang.String getRounding() {
		return this.rounding;
	}

	public void setRounding(java.lang.String rounding) {
		this.rounding = rounding;
	}

	public java.lang.String getMinimumExtraordinary() {
		return this.minimumExtraordinary;
	}

	public void setMinimumExtraordinary(java.lang.String minimumExtraordinary) {
		this.minimumExtraordinary = minimumExtraordinary;
	}

	public java.lang.Boolean getBreakAnomaly() {
		return this.breakAnomaly;
	}

	public void setBreakAnomaly(java.lang.Boolean breakAnomaly) {
		this.breakAnomaly = breakAnomaly;
	}

	public java.lang.Boolean getReadOnlyEvents() {
		return this.readOnlyEvents;
	}

	public void setReadOnlyEvents(java.lang.Boolean readOnlyEvents) {
		this.readOnlyEvents = readOnlyEvents;
	}

	public java.lang.Boolean getRequestsPermitsNotRemunered() {
		return this.requestsPermitsNotRemunered;
	}

	public void setRequestsPermitsNotRemunered(java.lang.Boolean requestsPermitsNotRemunered) {
		this.requestsPermitsNotRemunered = requestsPermitsNotRemunered;
	}

	public java.lang.Boolean getRequestsPermitsMedicalVisits() {
		return this.requestsPermitsMedicalVisits;
	}

	public void setRequestsPermitsMedicalVisits(java.lang.Boolean requestsPermitsMedicalVisits) {
		this.requestsPermitsMedicalVisits = requestsPermitsMedicalVisits;
	}

	public java.lang.Boolean getRequestsPermitsContractuals() {
		return this.requestsPermitsContractuals;
	}

	public void setRequestsPermitsContractuals(java.lang.Boolean requestsPermitsContractuals) {
		this.requestsPermitsContractuals = requestsPermitsContractuals;
	}

	public java.lang.Boolean getCompanyExit() {
		return this.companyExit;
	}

	public void setCompanyExit(java.lang.Boolean companyExit) {
		this.companyExit = companyExit;
	}

	public java.lang.Boolean getManageStamp() {
		return this.manageStamp;
	}

	public void setManageStamp(java.lang.Boolean manageStamp) {
		this.manageStamp = manageStamp;
	}

	public java.lang.Boolean getMedicalVisitsAutomaticallyApproved() {
		return this.medicalVisitsAutomaticallyApproved;
	}

	public void setMedicalVisitsAutomaticallyApproved(java.lang.Boolean medicalVisitsAutomaticallyApproved) {
		this.medicalVisitsAutomaticallyApproved = medicalVisitsAutomaticallyApproved;
	}

	public java.lang.String getCalendarUserId() {
		return this.calendarUserId;
	}

	public void setCalendarUserId(java.lang.String calendarUserId) {
		this.calendarUserId = calendarUserId;
	}

	public java.lang.Integer getDefaultEventActivityId() {
		return this.defaultEventActivityId;
	}

	public void setDefaultEventActivityId(java.lang.Integer defaultEventActivityId) {
		this.defaultEventActivityId = defaultEventActivityId;
	}

	public java.lang.Boolean getRequestsSickness() {
		return this.requestsSickness;
	}

	public void setRequestsSickness(java.lang.Boolean requestsSickness) {
		this.requestsSickness = requestsSickness;
	}

	public java.lang.Boolean getSicknessAutomaticallyApproved() {
		return this.sicknessAutomaticallyApproved;
	}

	public void setSicknessAutomaticallyApproved(java.lang.Boolean sicknessAutomaticallyApproved) {
		this.sicknessAutomaticallyApproved = sicknessAutomaticallyApproved;
	}

	public java.lang.String getDefaultCausalWorkingHours() {
		return this.defaultCausalWorkingHours;
	}

	public void setDefaultCausalWorkingHours(java.lang.String defaultCausalWorkingHours) {
		this.defaultCausalWorkingHours = defaultCausalWorkingHours;
	}

	public java.lang.String getDefaultCausalOvertime() {
		return this.defaultCausalOvertime;
	}

	public void setDefaultCausalOvertime(java.lang.String defaultCausalOvertime) {
		this.defaultCausalOvertime = defaultCausalOvertime;
	}

	public java.lang.String getDefaultCausalPermits() {
		return this.defaultCausalPermits;
	}

	public void setDefaultCausalPermits(java.lang.String defaultCausalPermits) {
		this.defaultCausalPermits = defaultCausalPermits;
	}

	public java.lang.String getDefaultCausalHolidays() {
		return this.defaultCausalHolidays;
	}

	public void setDefaultCausalHolidays(java.lang.String defaultCausalHolidays) {
		this.defaultCausalHolidays = defaultCausalHolidays;
	}

	public java.lang.String getDefaultCausalSickness() {
		return this.defaultCausalSickness;
	}

	public void setDefaultCausalSickness(java.lang.String defaultCausalSickness) {
		this.defaultCausalSickness = defaultCausalSickness;
	}
}
