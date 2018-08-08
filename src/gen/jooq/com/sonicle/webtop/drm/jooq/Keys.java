/**
 * This class is generated by jOOQ
 */
package com.sonicle.webtop.drm.jooq;

/**
 * A class modelling foreign key relationships between tables of the <code>drm</code> 
 * schema
 */
@javax.annotation.Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.5.3"
	},
	comments = "This class is generated by jOOQ"
)
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

	// -------------------------------------------------------------------------
	// IDENTITY definitions
	// -------------------------------------------------------------------------

	public static final org.jooq.Identity<com.sonicle.webtop.drm.jooq.tables.records.BusinessTripsRecord, java.lang.Integer> IDENTITY_BUSINESS_TRIPS = Identities0.IDENTITY_BUSINESS_TRIPS;
	public static final org.jooq.Identity<com.sonicle.webtop.drm.jooq.tables.records.CompaniesRecord, java.lang.Integer> IDENTITY_COMPANIES = Identities0.IDENTITY_COMPANIES;
	public static final org.jooq.Identity<com.sonicle.webtop.drm.jooq.tables.records.CompaniesUsersRecord, java.lang.Integer> IDENTITY_COMPANIES_USERS = Identities0.IDENTITY_COMPANIES_USERS;
	public static final org.jooq.Identity<com.sonicle.webtop.drm.jooq.tables.records.DocStatusesRecord, java.lang.Integer> IDENTITY_DOC_STATUSES = Identities0.IDENTITY_DOC_STATUSES;
	public static final org.jooq.Identity<com.sonicle.webtop.drm.jooq.tables.records.DocStatusesGroupsRecord, java.lang.Integer> IDENTITY_DOC_STATUSES_GROUPS = Identities0.IDENTITY_DOC_STATUSES_GROUPS;
	public static final org.jooq.Identity<com.sonicle.webtop.drm.jooq.tables.records.FoldersGroupsRecord, java.lang.Integer> IDENTITY_FOLDERS_GROUPS = Identities0.IDENTITY_FOLDERS_GROUPS;
	public static final org.jooq.Identity<com.sonicle.webtop.drm.jooq.tables.records.GroupsUsersRecord, java.lang.Integer> IDENTITY_GROUPS_USERS = Identities0.IDENTITY_GROUPS_USERS;
	public static final org.jooq.Identity<com.sonicle.webtop.drm.jooq.tables.records.ProfilesMasterdataRecord, java.lang.Integer> IDENTITY_PROFILES_MASTERDATA = Identities0.IDENTITY_PROFILES_MASTERDATA;
	public static final org.jooq.Identity<com.sonicle.webtop.drm.jooq.tables.records.ProfilesMembersRecord, java.lang.Integer> IDENTITY_PROFILES_MEMBERS = Identities0.IDENTITY_PROFILES_MEMBERS;
	public static final org.jooq.Identity<com.sonicle.webtop.drm.jooq.tables.records.ProfilesSupervisedUsersRecord, java.lang.Integer> IDENTITY_PROFILES_SUPERVISED_USERS = Identities0.IDENTITY_PROFILES_SUPERVISED_USERS;
	public static final org.jooq.Identity<com.sonicle.webtop.drm.jooq.tables.records.WorkReportsRowsRecord, java.lang.Integer> IDENTITY_WORK_REPORTS_ROWS = Identities0.IDENTITY_WORK_REPORTS_ROWS;
	public static final org.jooq.Identity<com.sonicle.webtop.drm.jooq.tables.records.WorkReportsSettingsRecord, java.lang.Integer> IDENTITY_WORK_REPORTS_SETTINGS = Identities0.IDENTITY_WORK_REPORTS_SETTINGS;
	public static final org.jooq.Identity<com.sonicle.webtop.drm.jooq.tables.records.WorkTypesRecord, java.lang.Integer> IDENTITY_WORK_TYPES = Identities0.IDENTITY_WORK_TYPES;

	// -------------------------------------------------------------------------
	// UNIQUE and PRIMARY KEY definitions
	// -------------------------------------------------------------------------

	public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.BusinessTripsRecord> BUSINESS_TRIPS_PKEY = UniqueKeys0.BUSINESS_TRIPS_PKEY;
	public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.CompaniesRecord> COMPANIES_PKEY = UniqueKeys0.COMPANIES_PKEY;
	public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.CompaniesPicturesRecord> COMPANIES_PICTURES_PKEY = UniqueKeys0.COMPANIES_PICTURES_PKEY;
	public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.CompaniesUsersRecord> COMPANIES_USERS_PKEY = UniqueKeys0.COMPANIES_USERS_PKEY;
	public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.DocStatusesRecord> DOC_STATUSES_PKEY = UniqueKeys0.DOC_STATUSES_PKEY;
	public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.DocStatusesGroupsRecord> DOC_STATUSES_GROUPS_PKEY = UniqueKeys0.DOC_STATUSES_GROUPS_PKEY;
	public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.EmployeeProfilesRecord> EMPLOYEE_PROFILES_PKEY = UniqueKeys0.EMPLOYEE_PROFILES_PKEY;
	public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.EnabledProgramsRecord> ENABLED_PROGRAMS_PKEY = UniqueKeys0.ENABLED_PROGRAMS_PKEY;
	public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.FoldersRecord> FOLDERS_PKEY = UniqueKeys0.FOLDERS_PKEY;
	public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.FoldersGroupsRecord> FOLDERS_GROUPS_PKEY = UniqueKeys0.FOLDERS_GROUPS_PKEY;
	public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.GroupsRecord> GROUPS_PKEY = UniqueKeys0.GROUPS_PKEY;
	public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.GroupsUsersRecord> GROUPS_USERS_PKEY = UniqueKeys0.GROUPS_USERS_PKEY;
	public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.HolidayDateRecord> HOLIDAY_DATE_PKEY = UniqueKeys0.HOLIDAY_DATE_PKEY;
	public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.HourProfilesRecord> OUR_PROFILES_PKEY = UniqueKeys0.OUR_PROFILES_PKEY;
	public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.LeaveRequestDocumentsRecord> LEAVE_REQUEST_DOCUMENTS_PKEY = UniqueKeys0.LEAVE_REQUEST_DOCUMENTS_PKEY;
	public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.LeaveRequestsRecord> LEAVE_REQUESTS_PKEY = UniqueKeys0.LEAVE_REQUESTS_PKEY;
	public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.LineHoursRecord> EMPLOYEE_HOURS_PKEY = UniqueKeys0.EMPLOYEE_HOURS_PKEY;
	public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.LineManagerUsersRecord> LINE_MANAGER_USERS_PKEY = UniqueKeys0.LINE_MANAGER_USERS_PKEY;
	public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.LineManagersRecord> LINE_MANAGERS_PKEY = UniqueKeys0.LINE_MANAGERS_PKEY;
	public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.OpportunitiesRecord> OPPORTUNITY_PKEY = UniqueKeys0.OPPORTUNITY_PKEY;
	public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.OpportunityActionDocumentsRecord> OPPORTUNITY_ACTION_DOCUMENTS_PKEY = UniqueKeys0.OPPORTUNITY_ACTION_DOCUMENTS_PKEY;
	public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.OpportunityActionInterlocutorsRecord> OPPORTUNITY_ACTION_INTERLOCUTORS_PKEY = UniqueKeys0.OPPORTUNITY_ACTION_INTERLOCUTORS_PKEY;
	public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.OpportunityActionsRecord> OPPORTUNITY_ACTIONS_PKEY = UniqueKeys0.OPPORTUNITY_ACTIONS_PKEY;
	public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.OpportunityDocumentsRecord> OPPORTUNITY_DOCUMENTS_PKEY = UniqueKeys0.OPPORTUNITY_DOCUMENTS_PKEY;
	public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.OpportunityFieldsRecord> OPPORTUNITY_FIELDS_PKEY = UniqueKeys0.OPPORTUNITY_FIELDS_PKEY;
	public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.OpportunityInterlocutorsRecord> OPPORTUNITY_INTERLOCUTORS_PKEY = UniqueKeys0.OPPORTUNITY_INTERLOCUTORS_PKEY;
	public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.ProfilesRecord> PROFILES_PKEY = UniqueKeys0.PROFILES_PKEY;
	public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.ProfilesMasterdataRecord> PROFILES_MASTERDATA_PKEY = UniqueKeys0.PROFILES_MASTERDATA_PKEY;
	public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.ProfilesMembersRecord> PROFILES_USERS_PKEY = UniqueKeys0.PROFILES_USERS_PKEY;
	public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.ProfilesSupervisedUsersRecord> PROFILES_SUPERVISED_USERS_PKEY = UniqueKeys0.PROFILES_SUPERVISED_USERS_PKEY;
	public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.TimetableEventsRecord> TIMETABLE_EVENTS_PKEY = UniqueKeys0.TIMETABLE_EVENTS_PKEY;
	public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.TimetableReportTempRecord> TIMETABLE_REPORT_TEMP_PKEY = UniqueKeys0.TIMETABLE_REPORT_TEMP_PKEY;
	public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.TimetableSettingsRecord> TIMETABLE_SETTINGS_PKEY = UniqueKeys0.TIMETABLE_SETTINGS_PKEY;
	public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.TimetableStampRecord> TIMETABLE_STAMP_PKEY = UniqueKeys0.TIMETABLE_STAMP_PKEY;
	public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.WorkReportsRecord> WORK_REPORTS_PKEY = UniqueKeys0.WORK_REPORTS_PKEY;
	public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.WorkReportsAttachmentsRecord> WORK_REPORTS_ATTACHMENTS_PKEY = UniqueKeys0.WORK_REPORTS_ATTACHMENTS_PKEY;
	public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.WorkReportsRowsRecord> WORK_REPORTS_DETAILS_PKEY = UniqueKeys0.WORK_REPORTS_DETAILS_PKEY;
	public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.WorkReportsSettingsRecord> WORK_REPORTS_SETTINGS_PKEY = UniqueKeys0.WORK_REPORTS_SETTINGS_PKEY;
	public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.WorkTypesRecord> WORK_TYPES_PKEY = UniqueKeys0.WORK_TYPES_PKEY;

	// -------------------------------------------------------------------------
	// FOREIGN KEY definitions
	// -------------------------------------------------------------------------


	// -------------------------------------------------------------------------
	// [#1459] distribute members to avoid static initialisers > 64kb
	// -------------------------------------------------------------------------

	private static class Identities0 extends org.jooq.impl.AbstractKeys {
		public static org.jooq.Identity<com.sonicle.webtop.drm.jooq.tables.records.BusinessTripsRecord, java.lang.Integer> IDENTITY_BUSINESS_TRIPS = createIdentity(com.sonicle.webtop.drm.jooq.tables.BusinessTrips.BUSINESS_TRIPS, com.sonicle.webtop.drm.jooq.tables.BusinessTrips.BUSINESS_TRIPS.BUSINESS_TRIP_ID);
		public static org.jooq.Identity<com.sonicle.webtop.drm.jooq.tables.records.CompaniesRecord, java.lang.Integer> IDENTITY_COMPANIES = createIdentity(com.sonicle.webtop.drm.jooq.tables.Companies.COMPANIES, com.sonicle.webtop.drm.jooq.tables.Companies.COMPANIES.COMPANY_ID);
		public static org.jooq.Identity<com.sonicle.webtop.drm.jooq.tables.records.CompaniesUsersRecord, java.lang.Integer> IDENTITY_COMPANIES_USERS = createIdentity(com.sonicle.webtop.drm.jooq.tables.CompaniesUsers.COMPANIES_USERS, com.sonicle.webtop.drm.jooq.tables.CompaniesUsers.COMPANIES_USERS.ASSOCIATION_ID);
		public static org.jooq.Identity<com.sonicle.webtop.drm.jooq.tables.records.DocStatusesRecord, java.lang.Integer> IDENTITY_DOC_STATUSES = createIdentity(com.sonicle.webtop.drm.jooq.tables.DocStatuses.DOC_STATUSES, com.sonicle.webtop.drm.jooq.tables.DocStatuses.DOC_STATUSES.DOC_STATUS_ID);
		public static org.jooq.Identity<com.sonicle.webtop.drm.jooq.tables.records.DocStatusesGroupsRecord, java.lang.Integer> IDENTITY_DOC_STATUSES_GROUPS = createIdentity(com.sonicle.webtop.drm.jooq.tables.DocStatusesGroups.DOC_STATUSES_GROUPS, com.sonicle.webtop.drm.jooq.tables.DocStatusesGroups.DOC_STATUSES_GROUPS.ASSOCIATION_ID);
		public static org.jooq.Identity<com.sonicle.webtop.drm.jooq.tables.records.FoldersGroupsRecord, java.lang.Integer> IDENTITY_FOLDERS_GROUPS = createIdentity(com.sonicle.webtop.drm.jooq.tables.FoldersGroups.FOLDERS_GROUPS, com.sonicle.webtop.drm.jooq.tables.FoldersGroups.FOLDERS_GROUPS.ASSOCIATION_ID);
		public static org.jooq.Identity<com.sonicle.webtop.drm.jooq.tables.records.GroupsUsersRecord, java.lang.Integer> IDENTITY_GROUPS_USERS = createIdentity(com.sonicle.webtop.drm.jooq.tables.GroupsUsers.GROUPS_USERS, com.sonicle.webtop.drm.jooq.tables.GroupsUsers.GROUPS_USERS.ASSOCIATION_ID);
		public static org.jooq.Identity<com.sonicle.webtop.drm.jooq.tables.records.ProfilesMasterdataRecord, java.lang.Integer> IDENTITY_PROFILES_MASTERDATA = createIdentity(com.sonicle.webtop.drm.jooq.tables.ProfilesMasterdata.PROFILES_MASTERDATA, com.sonicle.webtop.drm.jooq.tables.ProfilesMasterdata.PROFILES_MASTERDATA.ID);
		public static org.jooq.Identity<com.sonicle.webtop.drm.jooq.tables.records.ProfilesMembersRecord, java.lang.Integer> IDENTITY_PROFILES_MEMBERS = createIdentity(com.sonicle.webtop.drm.jooq.tables.ProfilesMembers.PROFILES_MEMBERS, com.sonicle.webtop.drm.jooq.tables.ProfilesMembers.PROFILES_MEMBERS.ID);
		public static org.jooq.Identity<com.sonicle.webtop.drm.jooq.tables.records.ProfilesSupervisedUsersRecord, java.lang.Integer> IDENTITY_PROFILES_SUPERVISED_USERS = createIdentity(com.sonicle.webtop.drm.jooq.tables.ProfilesSupervisedUsers.PROFILES_SUPERVISED_USERS, com.sonicle.webtop.drm.jooq.tables.ProfilesSupervisedUsers.PROFILES_SUPERVISED_USERS.ID);
		public static org.jooq.Identity<com.sonicle.webtop.drm.jooq.tables.records.WorkReportsRowsRecord, java.lang.Integer> IDENTITY_WORK_REPORTS_ROWS = createIdentity(com.sonicle.webtop.drm.jooq.tables.WorkReportsRows.WORK_REPORTS_ROWS, com.sonicle.webtop.drm.jooq.tables.WorkReportsRows.WORK_REPORTS_ROWS.ID);
		public static org.jooq.Identity<com.sonicle.webtop.drm.jooq.tables.records.WorkReportsSettingsRecord, java.lang.Integer> IDENTITY_WORK_REPORTS_SETTINGS = createIdentity(com.sonicle.webtop.drm.jooq.tables.WorkReportsSettings.WORK_REPORTS_SETTINGS, com.sonicle.webtop.drm.jooq.tables.WorkReportsSettings.WORK_REPORTS_SETTINGS.WORK_REPORT_SETTING_ID);
		public static org.jooq.Identity<com.sonicle.webtop.drm.jooq.tables.records.WorkTypesRecord, java.lang.Integer> IDENTITY_WORK_TYPES = createIdentity(com.sonicle.webtop.drm.jooq.tables.WorkTypes.WORK_TYPES, com.sonicle.webtop.drm.jooq.tables.WorkTypes.WORK_TYPES.WORK_TYPE_ID);
	}

	private static class UniqueKeys0 extends org.jooq.impl.AbstractKeys {
		public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.BusinessTripsRecord> BUSINESS_TRIPS_PKEY = createUniqueKey(com.sonicle.webtop.drm.jooq.tables.BusinessTrips.BUSINESS_TRIPS, com.sonicle.webtop.drm.jooq.tables.BusinessTrips.BUSINESS_TRIPS.BUSINESS_TRIP_ID);
		public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.CompaniesRecord> COMPANIES_PKEY = createUniqueKey(com.sonicle.webtop.drm.jooq.tables.Companies.COMPANIES, com.sonicle.webtop.drm.jooq.tables.Companies.COMPANIES.COMPANY_ID);
		public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.CompaniesPicturesRecord> COMPANIES_PICTURES_PKEY = createUniqueKey(com.sonicle.webtop.drm.jooq.tables.CompaniesPictures.COMPANIES_PICTURES, com.sonicle.webtop.drm.jooq.tables.CompaniesPictures.COMPANIES_PICTURES.COMPANY_ID);
		public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.CompaniesUsersRecord> COMPANIES_USERS_PKEY = createUniqueKey(com.sonicle.webtop.drm.jooq.tables.CompaniesUsers.COMPANIES_USERS, com.sonicle.webtop.drm.jooq.tables.CompaniesUsers.COMPANIES_USERS.ASSOCIATION_ID);
		public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.DocStatusesRecord> DOC_STATUSES_PKEY = createUniqueKey(com.sonicle.webtop.drm.jooq.tables.DocStatuses.DOC_STATUSES, com.sonicle.webtop.drm.jooq.tables.DocStatuses.DOC_STATUSES.DOC_STATUS_ID);
		public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.DocStatusesGroupsRecord> DOC_STATUSES_GROUPS_PKEY = createUniqueKey(com.sonicle.webtop.drm.jooq.tables.DocStatusesGroups.DOC_STATUSES_GROUPS, com.sonicle.webtop.drm.jooq.tables.DocStatusesGroups.DOC_STATUSES_GROUPS.ASSOCIATION_ID);
		public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.EmployeeProfilesRecord> EMPLOYEE_PROFILES_PKEY = createUniqueKey(com.sonicle.webtop.drm.jooq.tables.EmployeeProfiles.EMPLOYEE_PROFILES, com.sonicle.webtop.drm.jooq.tables.EmployeeProfiles.EMPLOYEE_PROFILES.ID);
		public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.EnabledProgramsRecord> ENABLED_PROGRAMS_PKEY = createUniqueKey(com.sonicle.webtop.drm.jooq.tables.EnabledPrograms.ENABLED_PROGRAMS, com.sonicle.webtop.drm.jooq.tables.EnabledPrograms.ENABLED_PROGRAMS.DOMAIN_ID, com.sonicle.webtop.drm.jooq.tables.EnabledPrograms.ENABLED_PROGRAMS.GROUP_ID);
		public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.FoldersRecord> FOLDERS_PKEY = createUniqueKey(com.sonicle.webtop.drm.jooq.tables.Folders.FOLDERS, com.sonicle.webtop.drm.jooq.tables.Folders.FOLDERS.FOLDER_ID);
		public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.FoldersGroupsRecord> FOLDERS_GROUPS_PKEY = createUniqueKey(com.sonicle.webtop.drm.jooq.tables.FoldersGroups.FOLDERS_GROUPS, com.sonicle.webtop.drm.jooq.tables.FoldersGroups.FOLDERS_GROUPS.ASSOCIATION_ID);
		public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.GroupsRecord> GROUPS_PKEY = createUniqueKey(com.sonicle.webtop.drm.jooq.tables.Groups.GROUPS, com.sonicle.webtop.drm.jooq.tables.Groups.GROUPS.GROUP_ID);
		public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.GroupsUsersRecord> GROUPS_USERS_PKEY = createUniqueKey(com.sonicle.webtop.drm.jooq.tables.GroupsUsers.GROUPS_USERS, com.sonicle.webtop.drm.jooq.tables.GroupsUsers.GROUPS_USERS.ASSOCIATION_ID);
		public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.HolidayDateRecord> HOLIDAY_DATE_PKEY = createUniqueKey(com.sonicle.webtop.drm.jooq.tables.HolidayDate.HOLIDAY_DATE, com.sonicle.webtop.drm.jooq.tables.HolidayDate.HOLIDAY_DATE.DOMAIN_ID, com.sonicle.webtop.drm.jooq.tables.HolidayDate.HOLIDAY_DATE.DATE);
		public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.HourProfilesRecord> OUR_PROFILES_PKEY = createUniqueKey(com.sonicle.webtop.drm.jooq.tables.HourProfiles.HOUR_PROFILES, com.sonicle.webtop.drm.jooq.tables.HourProfiles.HOUR_PROFILES.ID);
		public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.LeaveRequestDocumentsRecord> LEAVE_REQUEST_DOCUMENTS_PKEY = createUniqueKey(com.sonicle.webtop.drm.jooq.tables.LeaveRequestDocuments.LEAVE_REQUEST_DOCUMENTS, com.sonicle.webtop.drm.jooq.tables.LeaveRequestDocuments.LEAVE_REQUEST_DOCUMENTS.LEAVE_REQUEST_DOCUMENT_ID);
		public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.LeaveRequestsRecord> LEAVE_REQUESTS_PKEY = createUniqueKey(com.sonicle.webtop.drm.jooq.tables.LeaveRequests.LEAVE_REQUESTS, com.sonicle.webtop.drm.jooq.tables.LeaveRequests.LEAVE_REQUESTS.LEAVE_REQUEST_ID);
		public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.LineHoursRecord> EMPLOYEE_HOURS_PKEY = createUniqueKey(com.sonicle.webtop.drm.jooq.tables.LineHours.LINE_HOURS, com.sonicle.webtop.drm.jooq.tables.LineHours.LINE_HOURS.ID);
		public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.LineManagerUsersRecord> LINE_MANAGER_USERS_PKEY = createUniqueKey(com.sonicle.webtop.drm.jooq.tables.LineManagerUsers.LINE_MANAGER_USERS, com.sonicle.webtop.drm.jooq.tables.LineManagerUsers.LINE_MANAGER_USERS.DOMAIN_ID, com.sonicle.webtop.drm.jooq.tables.LineManagerUsers.LINE_MANAGER_USERS.LINE_MANAGER_USER_ID, com.sonicle.webtop.drm.jooq.tables.LineManagerUsers.LINE_MANAGER_USERS.USER_ID);
		public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.LineManagersRecord> LINE_MANAGERS_PKEY = createUniqueKey(com.sonicle.webtop.drm.jooq.tables.LineManagers.LINE_MANAGERS, com.sonicle.webtop.drm.jooq.tables.LineManagers.LINE_MANAGERS.DOMAIN_ID, com.sonicle.webtop.drm.jooq.tables.LineManagers.LINE_MANAGERS.USER_ID);
		public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.OpportunitiesRecord> OPPORTUNITY_PKEY = createUniqueKey(com.sonicle.webtop.drm.jooq.tables.Opportunities.OPPORTUNITIES, com.sonicle.webtop.drm.jooq.tables.Opportunities.OPPORTUNITIES.ID);
		public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.OpportunityActionDocumentsRecord> OPPORTUNITY_ACTION_DOCUMENTS_PKEY = createUniqueKey(com.sonicle.webtop.drm.jooq.tables.OpportunityActionDocuments.OPPORTUNITY_ACTION_DOCUMENTS, com.sonicle.webtop.drm.jooq.tables.OpportunityActionDocuments.OPPORTUNITY_ACTION_DOCUMENTS.ID);
		public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.OpportunityActionInterlocutorsRecord> OPPORTUNITY_ACTION_INTERLOCUTORS_PKEY = createUniqueKey(com.sonicle.webtop.drm.jooq.tables.OpportunityActionInterlocutors.OPPORTUNITY_ACTION_INTERLOCUTORS, com.sonicle.webtop.drm.jooq.tables.OpportunityActionInterlocutors.OPPORTUNITY_ACTION_INTERLOCUTORS.ID);
		public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.OpportunityActionsRecord> OPPORTUNITY_ACTIONS_PKEY = createUniqueKey(com.sonicle.webtop.drm.jooq.tables.OpportunityActions.OPPORTUNITY_ACTIONS, com.sonicle.webtop.drm.jooq.tables.OpportunityActions.OPPORTUNITY_ACTIONS.ID);
		public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.OpportunityDocumentsRecord> OPPORTUNITY_DOCUMENTS_PKEY = createUniqueKey(com.sonicle.webtop.drm.jooq.tables.OpportunityDocuments.OPPORTUNITY_DOCUMENTS, com.sonicle.webtop.drm.jooq.tables.OpportunityDocuments.OPPORTUNITY_DOCUMENTS.ID);
		public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.OpportunityFieldsRecord> OPPORTUNITY_FIELDS_PKEY = createUniqueKey(com.sonicle.webtop.drm.jooq.tables.OpportunityFields.OPPORTUNITY_FIELDS, com.sonicle.webtop.drm.jooq.tables.OpportunityFields.OPPORTUNITY_FIELDS.DOMAIN_ID, com.sonicle.webtop.drm.jooq.tables.OpportunityFields.OPPORTUNITY_FIELDS.TAB_ID, com.sonicle.webtop.drm.jooq.tables.OpportunityFields.OPPORTUNITY_FIELDS.FIELD_ID);
		public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.OpportunityInterlocutorsRecord> OPPORTUNITY_INTERLOCUTORS_PKEY = createUniqueKey(com.sonicle.webtop.drm.jooq.tables.OpportunityInterlocutors.OPPORTUNITY_INTERLOCUTORS, com.sonicle.webtop.drm.jooq.tables.OpportunityInterlocutors.OPPORTUNITY_INTERLOCUTORS.ID);
		public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.ProfilesRecord> PROFILES_PKEY = createUniqueKey(com.sonicle.webtop.drm.jooq.tables.Profiles.PROFILES, com.sonicle.webtop.drm.jooq.tables.Profiles.PROFILES.PROFILE_ID);
		public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.ProfilesMasterdataRecord> PROFILES_MASTERDATA_PKEY = createUniqueKey(com.sonicle.webtop.drm.jooq.tables.ProfilesMasterdata.PROFILES_MASTERDATA, com.sonicle.webtop.drm.jooq.tables.ProfilesMasterdata.PROFILES_MASTERDATA.ID);
		public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.ProfilesMembersRecord> PROFILES_USERS_PKEY = createUniqueKey(com.sonicle.webtop.drm.jooq.tables.ProfilesMembers.PROFILES_MEMBERS, com.sonicle.webtop.drm.jooq.tables.ProfilesMembers.PROFILES_MEMBERS.ID);
		public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.ProfilesSupervisedUsersRecord> PROFILES_SUPERVISED_USERS_PKEY = createUniqueKey(com.sonicle.webtop.drm.jooq.tables.ProfilesSupervisedUsers.PROFILES_SUPERVISED_USERS, com.sonicle.webtop.drm.jooq.tables.ProfilesSupervisedUsers.PROFILES_SUPERVISED_USERS.ID);
		public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.TimetableEventsRecord> TIMETABLE_EVENTS_PKEY = createUniqueKey(com.sonicle.webtop.drm.jooq.tables.TimetableEvents.TIMETABLE_EVENTS, com.sonicle.webtop.drm.jooq.tables.TimetableEvents.TIMETABLE_EVENTS.TIMETABLE_EVENT_ID);
		public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.TimetableReportTempRecord> TIMETABLE_REPORT_TEMP_PKEY = createUniqueKey(com.sonicle.webtop.drm.jooq.tables.TimetableReportTemp.TIMETABLE_REPORT_TEMP, com.sonicle.webtop.drm.jooq.tables.TimetableReportTemp.TIMETABLE_REPORT_TEMP.ID);
		public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.TimetableSettingsRecord> TIMETABLE_SETTINGS_PKEY = createUniqueKey(com.sonicle.webtop.drm.jooq.tables.TimetableSettings.TIMETABLE_SETTINGS, com.sonicle.webtop.drm.jooq.tables.TimetableSettings.TIMETABLE_SETTINGS.TIMETABLE_SETTING_ID);
		public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.TimetableStampRecord> TIMETABLE_STAMP_PKEY = createUniqueKey(com.sonicle.webtop.drm.jooq.tables.TimetableStamp.TIMETABLE_STAMP, com.sonicle.webtop.drm.jooq.tables.TimetableStamp.TIMETABLE_STAMP.ID);
		public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.WorkReportsRecord> WORK_REPORTS_PKEY = createUniqueKey(com.sonicle.webtop.drm.jooq.tables.WorkReports.WORK_REPORTS, com.sonicle.webtop.drm.jooq.tables.WorkReports.WORK_REPORTS.WORK_REPORT_ID);
		public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.WorkReportsAttachmentsRecord> WORK_REPORTS_ATTACHMENTS_PKEY = createUniqueKey(com.sonicle.webtop.drm.jooq.tables.WorkReportsAttachments.WORK_REPORTS_ATTACHMENTS, com.sonicle.webtop.drm.jooq.tables.WorkReportsAttachments.WORK_REPORTS_ATTACHMENTS.WORK_REPORT_ATTACHMENT_ID);
		public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.WorkReportsRowsRecord> WORK_REPORTS_DETAILS_PKEY = createUniqueKey(com.sonicle.webtop.drm.jooq.tables.WorkReportsRows.WORK_REPORTS_ROWS, com.sonicle.webtop.drm.jooq.tables.WorkReportsRows.WORK_REPORTS_ROWS.ID);
		public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.WorkReportsSettingsRecord> WORK_REPORTS_SETTINGS_PKEY = createUniqueKey(com.sonicle.webtop.drm.jooq.tables.WorkReportsSettings.WORK_REPORTS_SETTINGS, com.sonicle.webtop.drm.jooq.tables.WorkReportsSettings.WORK_REPORTS_SETTINGS.WORK_REPORT_SETTING_ID);
		public static final org.jooq.UniqueKey<com.sonicle.webtop.drm.jooq.tables.records.WorkTypesRecord> WORK_TYPES_PKEY = createUniqueKey(com.sonicle.webtop.drm.jooq.tables.WorkTypes.WORK_TYPES, com.sonicle.webtop.drm.jooq.tables.WorkTypes.WORK_TYPES.WORK_TYPE_ID);
	}
}
