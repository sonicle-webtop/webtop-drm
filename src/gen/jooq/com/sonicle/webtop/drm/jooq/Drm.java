/**
 * This class is generated by jOOQ
 */
package com.sonicle.webtop.drm.jooq;

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
public class Drm extends org.jooq.impl.SchemaImpl {

	private static final long serialVersionUID = -64942157;

	/**
	 * The reference instance of <code>drm</code>
	 */
	public static final Drm DRM = new Drm();

	/**
	 * No further instances allowed
	 */
	private Drm() {
		super("drm");
	}

	@Override
	public final java.util.List<org.jooq.Sequence<?>> getSequences() {
		java.util.List result = new java.util.ArrayList();
		result.addAll(getSequences0());
		return result;
	}

	private final java.util.List<org.jooq.Sequence<?>> getSequences0() {
		return java.util.Arrays.<org.jooq.Sequence<?>>asList(
			com.sonicle.webtop.drm.jooq.Sequences.SEQ_ACTIVITIES,
			com.sonicle.webtop.drm.jooq.Sequences.SEQ_ACTIVITIES_GROUPS,
			com.sonicle.webtop.drm.jooq.Sequences.SEQ_BUSINESS_TRIPS,
			com.sonicle.webtop.drm.jooq.Sequences.SEQ_COMPANIES,
			com.sonicle.webtop.drm.jooq.Sequences.SEQ_COMPANIES_USERS,
			com.sonicle.webtop.drm.jooq.Sequences.SEQ_COST_TYPES,
			com.sonicle.webtop.drm.jooq.Sequences.SEQ_DOC_STATUSES,
			com.sonicle.webtop.drm.jooq.Sequences.SEQ_DOC_STATUSES_GROUPS,
			com.sonicle.webtop.drm.jooq.Sequences.SEQ_EMPLOYEE_PROFILES,
			com.sonicle.webtop.drm.jooq.Sequences.SEQ_EXPENSE_NOTE_DETAILS,
			com.sonicle.webtop.drm.jooq.Sequences.SEQ_EXPENSE_NOTE_SETTINGS,
			com.sonicle.webtop.drm.jooq.Sequences.SEQ_EXPENSE_NOTES,
			com.sonicle.webtop.drm.jooq.Sequences.SEQ_FOLDERS_GROUPS,
			com.sonicle.webtop.drm.jooq.Sequences.SEQ_GROUPS_USERS,
			com.sonicle.webtop.drm.jooq.Sequences.SEQ_HOLIDAY_DATES,
			com.sonicle.webtop.drm.jooq.Sequences.SEQ_HOUR_PROFILES,
			com.sonicle.webtop.drm.jooq.Sequences.SEQ_JOBS,
			com.sonicle.webtop.drm.jooq.Sequences.SEQ_LEAVE_REQUEST_DOCUMENTS,
			com.sonicle.webtop.drm.jooq.Sequences.SEQ_LEAVE_REQUESTS,
			com.sonicle.webtop.drm.jooq.Sequences.SEQ_LINE_HOURS,
			com.sonicle.webtop.drm.jooq.Sequences.SEQ_OPPORTUNITIES,
			com.sonicle.webtop.drm.jooq.Sequences.SEQ_OPPORTUNITY_ACTION_INTERLOCUTORS,
			com.sonicle.webtop.drm.jooq.Sequences.SEQ_OPPORTUNITY_ACTIONS,
			com.sonicle.webtop.drm.jooq.Sequences.SEQ_OPPORTUNITY_INTERLOCUTORS,
			com.sonicle.webtop.drm.jooq.Sequences.SEQ_PROFILES_MASTERDATA,
			com.sonicle.webtop.drm.jooq.Sequences.SEQ_PROFILES_MEMBERS,
			com.sonicle.webtop.drm.jooq.Sequences.SEQ_PROFILES_SUPERVISED_USERS,
			com.sonicle.webtop.drm.jooq.Sequences.SEQ_PROFILES_USERS,
			com.sonicle.webtop.drm.jooq.Sequences.SEQ_TICKET_CATEGORIES,
			com.sonicle.webtop.drm.jooq.Sequences.SEQ_TICKETS,
			com.sonicle.webtop.drm.jooq.Sequences.SEQ_TICKETS_SETTINGS,
			com.sonicle.webtop.drm.jooq.Sequences.SEQ_TIMETABLE_EVENT,
			com.sonicle.webtop.drm.jooq.Sequences.SEQ_TIMETABLE_REPORT_TEMP,
			com.sonicle.webtop.drm.jooq.Sequences.SEQ_TIMETABLE_SETTINGS,
			com.sonicle.webtop.drm.jooq.Sequences.SEQ_TIMETABLE_STAMP,
			com.sonicle.webtop.drm.jooq.Sequences.SEQ_WORK_REPORT_DETAILS,
			com.sonicle.webtop.drm.jooq.Sequences.SEQ_WORK_REPORTS,
			com.sonicle.webtop.drm.jooq.Sequences.SEQ_WORK_REPORTS_COUNT,
			com.sonicle.webtop.drm.jooq.Sequences.SEQ_WORK_REPORTS_SETTINGS,
			com.sonicle.webtop.drm.jooq.Sequences.SEQ_WORK_TYPES);
	}

	@Override
	public final java.util.List<org.jooq.Table<?>> getTables() {
		java.util.List result = new java.util.ArrayList();
		result.addAll(getTables0());
		return result;
	}

	private final java.util.List<org.jooq.Table<?>> getTables0() {
		return java.util.Arrays.<org.jooq.Table<?>>asList(
			com.sonicle.webtop.drm.jooq.tables.Activities.ACTIVITIES,
			com.sonicle.webtop.drm.jooq.tables.ActivitiesGroups.ACTIVITIES_GROUPS,
			com.sonicle.webtop.drm.jooq.tables.BusinessTrips.BUSINESS_TRIPS,
			com.sonicle.webtop.drm.jooq.tables.Companies.COMPANIES,
			com.sonicle.webtop.drm.jooq.tables.CompaniesPictures.COMPANIES_PICTURES,
			com.sonicle.webtop.drm.jooq.tables.CompaniesUsers.COMPANIES_USERS,
			com.sonicle.webtop.drm.jooq.tables.CostTypes.COST_TYPES,
			com.sonicle.webtop.drm.jooq.tables.DocStatuses.DOC_STATUSES,
			com.sonicle.webtop.drm.jooq.tables.DocStatusesGroups.DOC_STATUSES_GROUPS,
			com.sonicle.webtop.drm.jooq.tables.EmployeeProfiles.EMPLOYEE_PROFILES,
			com.sonicle.webtop.drm.jooq.tables.EnabledPrograms.ENABLED_PROGRAMS,
			com.sonicle.webtop.drm.jooq.tables.ExpenseNote.EXPENSE_NOTE,
			com.sonicle.webtop.drm.jooq.tables.ExpenseNoteDetail.EXPENSE_NOTE_DETAIL,
			com.sonicle.webtop.drm.jooq.tables.ExpenseNoteDetailDocuments.EXPENSE_NOTE_DETAIL_DOCUMENTS,
			com.sonicle.webtop.drm.jooq.tables.ExpenseNoteDetailDocumentsData.EXPENSE_NOTE_DETAIL_DOCUMENTS_DATA,
			com.sonicle.webtop.drm.jooq.tables.ExpenseNoteDocuments.EXPENSE_NOTE_DOCUMENTS,
			com.sonicle.webtop.drm.jooq.tables.ExpenseNoteDocumentsData.EXPENSE_NOTE_DOCUMENTS_DATA,
			com.sonicle.webtop.drm.jooq.tables.ExpenseNoteSettings.EXPENSE_NOTE_SETTINGS,
			com.sonicle.webtop.drm.jooq.tables.Folders.FOLDERS,
			com.sonicle.webtop.drm.jooq.tables.FoldersGroups.FOLDERS_GROUPS,
			com.sonicle.webtop.drm.jooq.tables.Groups.GROUPS,
			com.sonicle.webtop.drm.jooq.tables.GroupsUsers.GROUPS_USERS,
			com.sonicle.webtop.drm.jooq.tables.HolidayDate.HOLIDAY_DATE,
			com.sonicle.webtop.drm.jooq.tables.HourProfiles.HOUR_PROFILES,
			com.sonicle.webtop.drm.jooq.tables.JobAttachments.JOB_ATTACHMENTS,
			com.sonicle.webtop.drm.jooq.tables.JobAttachmentsData.JOB_ATTACHMENTS_DATA,
			com.sonicle.webtop.drm.jooq.tables.Jobs.JOBS,
			com.sonicle.webtop.drm.jooq.tables.LeaveRequestDocuments.LEAVE_REQUEST_DOCUMENTS,
			com.sonicle.webtop.drm.jooq.tables.LeaveRequestDocumentsData.LEAVE_REQUEST_DOCUMENTS_DATA,
			com.sonicle.webtop.drm.jooq.tables.LeaveRequests.LEAVE_REQUESTS,
			com.sonicle.webtop.drm.jooq.tables.LineHours.LINE_HOURS,
			com.sonicle.webtop.drm.jooq.tables.LineManagerUsers.LINE_MANAGER_USERS,
			com.sonicle.webtop.drm.jooq.tables.LineManagers.LINE_MANAGERS,
			com.sonicle.webtop.drm.jooq.tables.Opportunities.OPPORTUNITIES,
			com.sonicle.webtop.drm.jooq.tables.OpportunityActionDocuments.OPPORTUNITY_ACTION_DOCUMENTS,
			com.sonicle.webtop.drm.jooq.tables.OpportunityActionDocumentsData.OPPORTUNITY_ACTION_DOCUMENTS_DATA,
			com.sonicle.webtop.drm.jooq.tables.OpportunityActionInterlocutors.OPPORTUNITY_ACTION_INTERLOCUTORS,
			com.sonicle.webtop.drm.jooq.tables.OpportunityActions.OPPORTUNITY_ACTIONS,
			com.sonicle.webtop.drm.jooq.tables.OpportunityDocuments.OPPORTUNITY_DOCUMENTS,
			com.sonicle.webtop.drm.jooq.tables.OpportunityDocumentsData.OPPORTUNITY_DOCUMENTS_DATA,
			com.sonicle.webtop.drm.jooq.tables.OpportunityFields.OPPORTUNITY_FIELDS,
			com.sonicle.webtop.drm.jooq.tables.OpportunityInterlocutors.OPPORTUNITY_INTERLOCUTORS,
			com.sonicle.webtop.drm.jooq.tables.Profiles.PROFILES,
			com.sonicle.webtop.drm.jooq.tables.ProfilesMasterdata.PROFILES_MASTERDATA,
			com.sonicle.webtop.drm.jooq.tables.ProfilesMembers.PROFILES_MEMBERS,
			com.sonicle.webtop.drm.jooq.tables.ProfilesSupervisedUsers.PROFILES_SUPERVISED_USERS,
			com.sonicle.webtop.drm.jooq.tables.TicketAttachments.TICKET_ATTACHMENTS,
			com.sonicle.webtop.drm.jooq.tables.TicketAttachmentsData.TICKET_ATTACHMENTS_DATA,
			com.sonicle.webtop.drm.jooq.tables.TicketCategories.TICKET_CATEGORIES,
			com.sonicle.webtop.drm.jooq.tables.Tickets.TICKETS,
			com.sonicle.webtop.drm.jooq.tables.TimetableEvents.TIMETABLE_EVENTS,
			com.sonicle.webtop.drm.jooq.tables.TimetableReportTemp.TIMETABLE_REPORT_TEMP,
			com.sonicle.webtop.drm.jooq.tables.TimetableSettings.TIMETABLE_SETTINGS,
			com.sonicle.webtop.drm.jooq.tables.TimetableStamp.TIMETABLE_STAMP,
			com.sonicle.webtop.drm.jooq.tables.VwJobs.VW_JOBS,
			com.sonicle.webtop.drm.jooq.tables.VwTickets.VW_TICKETS,
			com.sonicle.webtop.drm.jooq.tables.VwWorkReports.VW_WORK_REPORTS,
			com.sonicle.webtop.drm.jooq.tables.VwWorkReports_2.VW_WORK_REPORTS_2,
			com.sonicle.webtop.drm.jooq.tables.VwWorkReports_3.VW_WORK_REPORTS_3,
			com.sonicle.webtop.drm.jooq.tables.WorkReports.WORK_REPORTS,
			com.sonicle.webtop.drm.jooq.tables.WorkReportsAttachments.WORK_REPORTS_ATTACHMENTS,
			com.sonicle.webtop.drm.jooq.tables.WorkReportsAttachmentsData.WORK_REPORTS_ATTACHMENTS_DATA,
			com.sonicle.webtop.drm.jooq.tables.WorkReportsRows.WORK_REPORTS_ROWS,
			com.sonicle.webtop.drm.jooq.tables.WorkReportsSettings.WORK_REPORTS_SETTINGS,
			com.sonicle.webtop.drm.jooq.tables.WorkTypes.WORK_TYPES);
	}
}
