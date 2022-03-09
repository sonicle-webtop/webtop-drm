/*
 * Copyright (C) 2018 Sonicle S.r.l.
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
 * display the words "Copyright (C) 2018 Sonicle S.r.l.".
 */
package com.sonicle.webtop.drm;

import com.sonicle.commons.EnumUtils;
import com.sonicle.commons.IdentifierUtils;
import com.sonicle.commons.PathUtils;
import com.sonicle.webtop.core.app.WT;
import com.sonicle.webtop.core.dal.BaseDAO;
import com.sonicle.webtop.core.dal.DAOException;
import com.sonicle.webtop.drm.bol.OActivity;
import com.sonicle.webtop.drm.bol.OActivityGroup;
import com.sonicle.webtop.drm.bol.OBusinessTrip;
import com.sonicle.webtop.drm.bol.OCausal;
import com.sonicle.webtop.drm.bol.OCompany;
import com.sonicle.webtop.drm.bol.OCompanyPicture;
import com.sonicle.webtop.drm.bol.OCompanyUser;
import com.sonicle.webtop.drm.bol.OCostType;
import com.sonicle.webtop.drm.bol.ODay;
import com.sonicle.webtop.drm.bol.ODocStatus;
import com.sonicle.webtop.drm.bol.ODocStatusGroup;
import com.sonicle.webtop.drm.bol.ODrmFolder;
import com.sonicle.webtop.drm.bol.ODrmFolderGroup;
import com.sonicle.webtop.drm.bol.ODrmGroup;
import com.sonicle.webtop.drm.bol.ODrmGroupUser;
import com.sonicle.webtop.drm.bol.ODrmLineManager;
import com.sonicle.webtop.drm.bol.ODrmLineManagerUsers;
import com.sonicle.webtop.drm.bol.ODrmProfile;
import com.sonicle.webtop.drm.bol.OEmployeeProfile;
import com.sonicle.webtop.drm.bol.OExpenseNote;
import com.sonicle.webtop.drm.bol.OExpenseNoteDetail;
import com.sonicle.webtop.drm.bol.OExpenseNoteDetailDocument;
import com.sonicle.webtop.drm.bol.OExpenseNoteDocument;
import com.sonicle.webtop.drm.bol.OExpenseNoteSetting;
import com.sonicle.webtop.drm.bol.OHolidayDate;
import com.sonicle.webtop.drm.bol.OHourProfile;
import com.sonicle.webtop.drm.bol.OJob;
import com.sonicle.webtop.drm.bol.OJobAttachment;
import com.sonicle.webtop.drm.bol.OLeaveRequest;
import com.sonicle.webtop.drm.bol.OLeaveRequestDocument;
import com.sonicle.webtop.drm.bol.OLeaveRequestType;
import com.sonicle.webtop.drm.bol.OLineHour;
import com.sonicle.webtop.drm.bol.OOpportunity;
import com.sonicle.webtop.drm.bol.OOpportunityAction;
import com.sonicle.webtop.drm.bol.OOpportunityActionDocument;
import com.sonicle.webtop.drm.bol.OOpportunityActionInterlocutor;
import com.sonicle.webtop.drm.bol.OOpportunityDocument;
import com.sonicle.webtop.drm.bol.OOpportunityField;
import com.sonicle.webtop.drm.bol.OOpportunityInterlocutor;
import com.sonicle.webtop.drm.bol.OProfileMasterdata;
import com.sonicle.webtop.drm.bol.OProfileMember;
import com.sonicle.webtop.drm.bol.OProfileSupervisedUser;
import com.sonicle.webtop.drm.bol.OTicket;
import com.sonicle.webtop.drm.bol.OTicketAttachment;
import com.sonicle.webtop.drm.bol.OTicketCategory;
import com.sonicle.webtop.drm.bol.OTimetableEvent;
import com.sonicle.webtop.drm.bol.OTimetableReport;
import com.sonicle.webtop.drm.bol.OTimetableSetting;
import com.sonicle.webtop.drm.bol.OTimetableStamp;
import com.sonicle.webtop.drm.bol.OViewJob;
import com.sonicle.webtop.drm.bol.OViewTicket;
import com.sonicle.webtop.drm.bol.OWorkReport;
import com.sonicle.webtop.drm.bol.OWorkReportAttachment;
import com.sonicle.webtop.drm.bol.OWorkReportRow;
import com.sonicle.webtop.drm.bol.OWorkReportSetting;
import com.sonicle.webtop.drm.bol.OWorkType;
import com.sonicle.webtop.drm.dal.ExpenseNoteDetailDocumentDAO;
import com.sonicle.webtop.drm.dal.ExpenseNoteDocumentDAO;
import com.sonicle.webtop.drm.dal.HolidayDateDAO;
import com.sonicle.webtop.drm.dal.JobAttachmentDAO;
import com.sonicle.webtop.drm.dal.LeaveRequestDocumentDAO;
import com.sonicle.webtop.drm.dal.LineHourDAO;
import com.sonicle.webtop.drm.dal.OpportunityActionDocumentDAO;
import com.sonicle.webtop.drm.dal.OpportunityDocumentDAO;
import com.sonicle.webtop.drm.dal.TicketAttachmentDAO;
import com.sonicle.webtop.drm.dal.WorkReportAttachmentDAO;
import com.sonicle.webtop.drm.model.Activity;
import com.sonicle.webtop.drm.model.ActivityGroupAssociation;
import com.sonicle.webtop.drm.model.BusinessTrip;
import com.sonicle.webtop.drm.model.Causal;
import com.sonicle.webtop.drm.model.Company;
import com.sonicle.webtop.drm.model.CompanyPicture;
import com.sonicle.webtop.drm.model.CompanyUserAssociation;
import com.sonicle.webtop.drm.model.CostType;
import com.sonicle.webtop.drm.model.DocStatus;
import com.sonicle.webtop.drm.model.DocStatusGroupAssociation;
import com.sonicle.webtop.drm.model.DrmFolder;
import com.sonicle.webtop.drm.model.DrmFolderGroupAssociation;
import com.sonicle.webtop.drm.model.DrmGroup;
import com.sonicle.webtop.drm.model.DrmGroupUserAssociation;
import com.sonicle.webtop.drm.model.DrmLineManager;
import com.sonicle.webtop.drm.model.DrmProfile;
import com.sonicle.webtop.drm.model.EmployeeProfile;
import com.sonicle.webtop.drm.model.ExpenseNote;
import com.sonicle.webtop.drm.model.ExpenseNoteDetail;
import com.sonicle.webtop.drm.model.ExpenseNoteDetailDocument;
import com.sonicle.webtop.drm.model.ExpenseNoteDetailDocumentWithStream;
import com.sonicle.webtop.drm.model.ExpenseNoteDocument;
import com.sonicle.webtop.drm.model.ExpenseNoteDocumentWithStream;
import com.sonicle.webtop.drm.model.ExpenseNoteSetting;
import com.sonicle.webtop.drm.model.HolidayDate;
import com.sonicle.webtop.drm.model.HourProfile;
import com.sonicle.webtop.drm.model.Job;
import com.sonicle.webtop.drm.model.JobAttachment;
import com.sonicle.webtop.drm.model.JobAttachmentWithStream;
import com.sonicle.webtop.drm.model.LeaveRequest;
import com.sonicle.webtop.drm.model.LeaveRequestDocument;
import com.sonicle.webtop.drm.model.LeaveRequestDocumentWithStream;
import com.sonicle.webtop.drm.model.LineHour;
import com.sonicle.webtop.drm.model.Opportunity;
import com.sonicle.webtop.drm.model.OpportunityAction;
import com.sonicle.webtop.drm.model.OpportunityActionDocument;
import com.sonicle.webtop.drm.model.OpportunityActionDocumentWithStream;
import com.sonicle.webtop.drm.model.OpportunityActionInterlocutor;
import com.sonicle.webtop.drm.model.OpportunityDocument;
import com.sonicle.webtop.drm.model.OpportunityDocumentWithStream;
import com.sonicle.webtop.drm.model.OpportunityField;
import com.sonicle.webtop.drm.model.OpportunityInterlocutor;
import com.sonicle.webtop.drm.model.ProfileMasterdata;
import com.sonicle.webtop.drm.model.ProfileMember;
import com.sonicle.webtop.drm.model.ProfileSupervisedUser;
import com.sonicle.webtop.drm.model.Ticket;
import com.sonicle.webtop.drm.model.TicketAttachment;
import com.sonicle.webtop.drm.model.TicketAttachmentWithStream;
import com.sonicle.webtop.drm.model.TicketCategory;
import com.sonicle.webtop.drm.model.TimetableReport;
import com.sonicle.webtop.drm.model.TimetableSetting;
import com.sonicle.webtop.drm.model.TimetableStamp;
import com.sonicle.webtop.drm.model.UserForManager;
import com.sonicle.webtop.drm.model.ViewJob;
import com.sonicle.webtop.drm.model.ViewTicket;
import com.sonicle.webtop.drm.model.WorkReport;
import com.sonicle.webtop.drm.model.WorkReportAttachment;
import com.sonicle.webtop.drm.model.WorkReportAttachmentWithStream;
import com.sonicle.webtop.drm.model.WorkReportRow;
import com.sonicle.webtop.drm.model.WorkReportSetting;
import com.sonicle.webtop.drm.model.WorkType;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.jooq.Result;

/**
 *
 * @author avasi
 */
public class ManagerUtils {
	
	static String getProductName() {
		return WT.getPlatformName() + " DRM";
	}
	
	static Job createJob(OJob oJob) {
		if (oJob == null) {
			return null;
		}
		Job job = new Job();
		job.setJobId(oJob.getJobId());
		job.setCompanyId(oJob.getCompanyId());
		job.setOperatorId(oJob.getOperatorId());
		job.setCustomerId(oJob.getCustomerId());
		job.setCustomerStatId(oJob.getCustomerStatId());
		job.setTimezone(oJob.getTimezone());
		job.setStartDate(oJob.getStartDate());
		job.setEndDate(oJob.getEndDate());
		job.setActivityId(oJob.getActivityId());
		job.setTitle(oJob.getTitle());
		job.setDescription(oJob.getDescription());
		job.setEventId(oJob.getEventId());		
		job.setDomainId(oJob.getDomainId());
		job.setTicketId(oJob.getTicketId());
		job.setNumber(oJob.getNumber());
		job.setCausalId(oJob.getCausalId());
		
		return job;
	}
	
	static OJob createOJob(Job job) {
		if (job == null) {
			return null;
		}
		OJob oJob = new OJob();
		oJob.setJobId(job.getJobId());
		oJob.setCompanyId(job.getCompanyId());
		oJob.setOperatorId(job.getOperatorId());
		oJob.setCustomerId(job.getCustomerId());
		oJob.setCustomerStatId(job.getCustomerStatId());
		oJob.setStartDate(job.getStartDate());
		oJob.setEndDate(job.getEndDate());
		oJob.setTimezone(job.getTimezone());
		oJob.setActivityId(job.getActivityId());
		oJob.setTitle(job.getTitle());
		oJob.setDescription(job.getDescription());
		oJob.setEventId(job.getEventId());
		oJob.setDomainId(job.getDomainId());
		oJob.setTicketId(job.getTicketId());
		oJob.setNumber(job.getNumber());
		oJob.setCausalId(job.getCausalId());
		
		return oJob;
	}
	
	static JobAttachment createJobAttachment(OJobAttachment src) {
		if (src == null) return null;
		return fillJobAttachment(new JobAttachment(), src);
	}
	
	static <T extends JobAttachment> T fillJobAttachment(T tgt, OJobAttachment src) {
		if ((tgt != null) && (src != null)) {
			tgt.setJobAttachmentId(src.getJobAttachmentId());
			tgt.setRevisionTimestamp(src.getRevisionTimestamp());
			tgt.setRevisionSequence(src.getRevisionSequence());
			tgt.setFileName(src.getFilename());
			tgt.setSize(src.getSize());
			tgt.setMediaType(src.getMediaType());
		}
		return tgt;
	}
	
	static WorkReport createWorkReport(OWorkReport oWrkRpt) {
		if (oWrkRpt == null) {
			return null;
		}
		WorkReport wrkRpt = new WorkReport();
		wrkRpt.setWorkReportId(oWrkRpt.getWorkReportId());
		wrkRpt.setNumber(oWrkRpt.getNumber());
		wrkRpt.setYear(oWrkRpt.getYear());
		wrkRpt.setCompanyId(oWrkRpt.getCompanyId());
		wrkRpt.setOperatorId(oWrkRpt.getOperatorId());
		wrkRpt.setRevisionStatus(EnumUtils.forSerializedName(oWrkRpt.getRevisionStatus(), WorkReport.RevisionStatus.class));
		wrkRpt.setRevisionTimestamp(oWrkRpt.getRevisionTimestamp());
		wrkRpt.setRevisionSequence(oWrkRpt.getRevisionSequence());
		wrkRpt.setDocStatusId(oWrkRpt.getDocStatusId());
		wrkRpt.setContactId(oWrkRpt.getContactId());
		wrkRpt.setCustomerId(oWrkRpt.getCustomerId());
		wrkRpt.setCustomerStatId(oWrkRpt.getCustomerStatId());
		wrkRpt.setFromDate(oWrkRpt.getFromDate());
		wrkRpt.setToDate(oWrkRpt.getToDate());
		wrkRpt.setReferenceNo(oWrkRpt.getReferenceNo());
		wrkRpt.setCausalId(oWrkRpt.getCausalId());
		wrkRpt.setDdtNo(oWrkRpt.getDdtNo());
		wrkRpt.setDdtDate(oWrkRpt.getDdtDate());
		wrkRpt.setNotes(oWrkRpt.getNotes());
		wrkRpt.setDescription(oWrkRpt.getDescription());
		wrkRpt.setApplySignature(oWrkRpt.getApplySignature());
		wrkRpt.setChargeTo(oWrkRpt.getChargeTo());
		wrkRpt.setFreeSupport(oWrkRpt.getFreeSupport());
		wrkRpt.setBusinessTripId(oWrkRpt.getBusinessTripId());
		wrkRpt.setBusinessTripDays(oWrkRpt.getBusinessTripDays().intValue());
		wrkRpt.setEventId(oWrkRpt.getEventId());		
		wrkRpt.setTimetableHours((null != oWrkRpt.getTimetableHours()) ? oWrkRpt.getTimetableHours() : 0);
		wrkRpt.setDomainId(oWrkRpt.getDomainId());
				
		return wrkRpt;
	}

	static OWorkReport createOWorkReport(WorkReport wrkRpt) {
		if (wrkRpt == null) {
			return null;
		}
		OWorkReport oWrkRpt = new OWorkReport();
		oWrkRpt.setWorkReportId(wrkRpt.getWorkReportId());
		oWrkRpt.setNumber(wrkRpt.getNumber());
		oWrkRpt.setYear(wrkRpt.getYear());
		oWrkRpt.setCompanyId(wrkRpt.getCompanyId());
		oWrkRpt.setOperatorId(wrkRpt.getOperatorId());
		oWrkRpt.setRevisionStatus(EnumUtils.toSerializedName(wrkRpt.getRevisionStatus()));
		oWrkRpt.setRevisionTimestamp(wrkRpt.getRevisionTimestamp());
		oWrkRpt.setRevisionSequence(wrkRpt.getRevisionSequence());
		oWrkRpt.setDocStatusId(wrkRpt.getDocStatusId());
		oWrkRpt.setContactId(wrkRpt.getContactId());
		oWrkRpt.setCustomerId(wrkRpt.getCustomerId());
		oWrkRpt.setCustomerStatId(wrkRpt.getCustomerStatId());
		oWrkRpt.setFromDate(wrkRpt.getFromDate());
		oWrkRpt.setToDate(wrkRpt.getToDate());
		oWrkRpt.setReferenceNo(wrkRpt.getReferenceNo());
		oWrkRpt.setCausalId(wrkRpt.getCausalId());
		oWrkRpt.setDdtNo(wrkRpt.getDdtNo());
		oWrkRpt.setDdtDate(wrkRpt.getDdtDate());
		oWrkRpt.setNotes(wrkRpt.getNotes());
		oWrkRpt.setDescription(wrkRpt.getDescription());
		oWrkRpt.setApplySignature(wrkRpt.getApplySignature());
		oWrkRpt.setChargeTo(wrkRpt.getChargeTo());
		oWrkRpt.setFreeSupport(wrkRpt.getFreeSupport());
		oWrkRpt.setBusinessTripId(wrkRpt.getBusinessTripId());
		oWrkRpt.setBusinessTripDays(wrkRpt.getBusinessTripDays().shortValue());
		oWrkRpt.setEventId(wrkRpt.getEventId());
		oWrkRpt.setTimetableHours(wrkRpt.getTimetableHours());
		oWrkRpt.setDomainId(wrkRpt.getDomainId());
		
		return oWrkRpt;
	}

	static WorkReportRow createWorkReportRow(OWorkReportRow oWrkDetail) {

		if (oWrkDetail == null) {
			return null;
		}

		WorkReportRow wrkDetail = new WorkReportRow();
		wrkDetail.setId(oWrkDetail.getId());
		wrkDetail.setWorkReportId(oWrkDetail.getWorkReportId());
		wrkDetail.setRowNo(oWrkDetail.getRowNo().intValue());
		wrkDetail.setWorkTypeId(oWrkDetail.getWorkTypeId());
		wrkDetail.setDuration(oWrkDetail.getDuration().intValue());
		wrkDetail.setExtra(oWrkDetail.getExtra());

		return wrkDetail;
	}

	static OWorkReportRow createOWorkReportRow(WorkReportRow wrkDetail) {

		if (wrkDetail == null) {
			return null;
		}

		OWorkReportRow oWrkDetail = new OWorkReportRow();
		oWrkDetail.setId(wrkDetail.getId());
		oWrkDetail.setWorkReportId(wrkDetail.getWorkReportId());
		oWrkDetail.setRowNo(wrkDetail.getRowNo().shortValue());
		oWrkDetail.setWorkTypeId(wrkDetail.getWorkTypeId());
		oWrkDetail.setDuration(wrkDetail.getDuration().shortValue());
		oWrkDetail.setExtra(wrkDetail.getExtra());

		return oWrkDetail;
	}
	
	static OWorkReportAttachment doWorkReportAttachmentInsert(Connection con, String wrId, WorkReportAttachmentWithStream attachment) throws DAOException, IOException {
		WorkReportAttachmentDAO attDao = WorkReportAttachmentDAO.getInstance();
		
		OWorkReportAttachment oatt = createOWorkReportAttachment(attachment);
		oatt.setWorkReportAttachmentId(IdentifierUtils.getUUIDTimeBased());
		oatt.setWorkReportId(wrId);
		attDao.insert(con, oatt, BaseDAO.createRevisionTimestamp());
		
		InputStream is = attachment.getStream();
		try {
			attDao.insertBytes(con, oatt.getWorkReportAttachmentId(), IOUtils.toByteArray(is));
		} finally {
			IOUtils.closeQuietly(is);
		}
		
		return oatt;
	}
	
	static OJobAttachment doJobAttachmentInsert(Connection con, String wrId, JobAttachmentWithStream attachment) throws DAOException, IOException {
		JobAttachmentDAO attDao = JobAttachmentDAO.getInstance();
		
		OJobAttachment oatt = createOJobAttachment(attachment);
		oatt.setJobAttachmentId(IdentifierUtils.getUUIDTimeBased());
		oatt.setJobId(wrId);
		attDao.insert(con, oatt, BaseDAO.createRevisionTimestamp());
		
		InputStream is = attachment.getStream();
		try {
			attDao.insertBytes(con, oatt.getJobAttachmentId(), IOUtils.toByteArray(is));
		} finally {
			IOUtils.closeQuietly(is);
		}
		
		return oatt;
	}
	
	static OWorkReportAttachment createOWorkReportAttachment(WorkReportAttachment src) {
		if (src == null) return null;
		return fillOWorkReportAttachment(new OWorkReportAttachment(), src);
	}
	
	static WorkReportAttachment createWorkReportAttachment(OWorkReportAttachment src) {
		if (src == null) return null;
		return fillWorkReportAttachment(new WorkReportAttachment(), src);
	}
	
	static <T extends OWorkReportAttachment> T fillOWorkReportAttachment(T tgt, WorkReportAttachment src) {
		if ((tgt != null) && (src != null)) {
			tgt.setWorkReportAttachmentId(src.getWorkReportAttachmentId());
			tgt.setRevisionTimestamp(src.getRevisionTimestamp());
			tgt.setRevisionSequence(src.getRevisionSequence());
			tgt.setFilename(src.getFileName());
			tgt.setSize(src.getSize());
			tgt.setMediaType(src.getMediaType());
		}
		return tgt;
	}
	
	static <T extends WorkReportAttachment> T fillWorkReportAttachment(T tgt, OWorkReportAttachment src) {
		if ((tgt != null) && (src != null)) {
			tgt.setWorkReportAttachmentId(src.getWorkReportAttachmentId());
			tgt.setRevisionTimestamp(src.getRevisionTimestamp());
			tgt.setRevisionSequence(src.getRevisionSequence());
			tgt.setFileName(src.getFilename());
			tgt.setSize(src.getSize());
			tgt.setMediaType(src.getMediaType());
		}
		return tgt;
	}
	
	static List<WorkReportAttachment> createWorkReportAttachmentList(List<OWorkReportAttachment> items) {
		ArrayList<WorkReportAttachment> list = new ArrayList<>(items.size());
		for (OWorkReportAttachment item : items) {
			list.add(createWorkReportAttachment(item));
		}
		return list;
	}
	
	static boolean doJobAttachmentUpdate(Connection con, JobAttachmentWithStream attachment) throws DAOException, IOException {
		JobAttachmentDAO attDao = JobAttachmentDAO.getInstance();
		
		OJobAttachment oatt = createOJobAttachment(attachment);
		attDao.update(con, oatt, BaseDAO.createRevisionTimestamp());
		
		InputStream is = attachment.getStream();
		try {
			attDao.deleteBytesById(con, oatt.getJobAttachmentId());
			return attDao.insertBytes(con, oatt.getJobAttachmentId(), IOUtils.toByteArray(is)) == 1;
		} finally {
			IOUtils.closeQuietly(is);
		}
	}
	
	static OJobAttachment createOJobAttachment(JobAttachment src) {
		if (src == null) return null;
		return fillOJobAttachment(new OJobAttachment(), src);
	}
	
	static <T extends OJobAttachment> T fillOJobAttachment(T tgt, JobAttachment src) {
		if ((tgt != null) && (src != null)) {
			tgt.setJobAttachmentId(src.getJobAttachmentId());
			tgt.setRevisionTimestamp(src.getRevisionTimestamp());
			tgt.setRevisionSequence(src.getRevisionSequence());
			tgt.setFilename(src.getFileName());
			tgt.setSize(src.getSize());
			tgt.setMediaType(src.getMediaType());
		}
		return tgt;
	}	
	
	static List<JobAttachment> createJobAttachmentList(List<OJobAttachment> items) {
		ArrayList<JobAttachment> list = new ArrayList<>(items.size());
		for (OJobAttachment item : items) {
			list.add(createJobAttachment(item));
		}
		return list;
	}
	
	static boolean doWorkReportAttachmentUpdate(Connection con, WorkReportAttachmentWithStream attachment) throws DAOException, IOException {
		WorkReportAttachmentDAO attDao = WorkReportAttachmentDAO.getInstance();
		
		OWorkReportAttachment oatt = createOWorkReportAttachment(attachment);
		attDao.update(con, oatt, BaseDAO.createRevisionTimestamp());
		
		InputStream is = attachment.getStream();
		try {
			attDao.deleteBytesById(con, oatt.getWorkReportAttachmentId());
			return attDao.insertBytes(con, oatt.getWorkReportAttachmentId(), IOUtils.toByteArray(is)) == 1;
		} finally {
			IOUtils.closeQuietly(is);
		}
	}
	
	static OLeaveRequestDocument doLeaveRequestDocumentInsert(Connection con, Integer lrId, LeaveRequestDocumentWithStream document) throws DAOException, IOException {
		LeaveRequestDocumentDAO docDao = LeaveRequestDocumentDAO.getInstance();
		
		OLeaveRequestDocument odoc = createOLeaveRequestDocument(document);
		odoc.setLeaveRequestDocumentId(IdentifierUtils.getUUIDTimeBased());
		odoc.setLeaveRequestId(lrId);
		docDao.insert(con, odoc, BaseDAO.createRevisionTimestamp());
		
		InputStream is = document.getStream();
		try {
			docDao.insertBytes(con, odoc.getLeaveRequestDocumentId(), IOUtils.toByteArray(is));
		} finally {
			IOUtils.closeQuietly(is);
		}
		
		return odoc;
	}
	
	static OLeaveRequestDocument createOLeaveRequestDocument(LeaveRequestDocument src) {
		if (src == null) return null;
		return fillOLeaveRequestDocument(new OLeaveRequestDocument(), src);
	}
	
	static LeaveRequestDocument createLeaveRequestDocument(OLeaveRequestDocument src) {
		if (src == null) return null;
		return fillLeaveRequestDocument(new LeaveRequestDocument(), src);
	}
	
	static <T extends OLeaveRequestDocument> T fillOLeaveRequestDocument(T tgt, LeaveRequestDocument src) {
		if ((tgt != null) && (src != null)) {
			tgt.setLeaveRequestDocumentId(src.getLeaveRequestDocumentId());
			tgt.setRevisionTimestamp(src.getRevisionTimestamp());
			tgt.setRevisionSequence(src.getRevisionSequence());
			tgt.setFilename(src.getFileName());
			tgt.setSize(src.getSize());
			tgt.setMediaType(src.getMediaType());
		}
		return tgt;
	}
	
	static <T extends LeaveRequestDocument> T fillLeaveRequestDocument(T tgt, OLeaveRequestDocument src) {
		if ((tgt != null) && (src != null)) {
			tgt.setLeaveRequestDocumentId(src.getLeaveRequestDocumentId());
			tgt.setRevisionTimestamp(src.getRevisionTimestamp());
			tgt.setRevisionSequence(src.getRevisionSequence());
			tgt.setFileName(src.getFilename());
			tgt.setSize(src.getSize());
			tgt.setMediaType(src.getMediaType());
		}
		return tgt;
	}
	
	static List<LeaveRequestDocument> createLeaveRequestDocumentList(List<OLeaveRequestDocument> items) {
		ArrayList<LeaveRequestDocument> list = new ArrayList<>(items.size());
		for (OLeaveRequestDocument item : items) {
			list.add(createLeaveRequestDocument(item));
		}
		return list;
	}
	
	static boolean doLeaveRequestDocumentUpdate(Connection con, LeaveRequestDocumentWithStream document) throws DAOException, IOException {
		LeaveRequestDocumentDAO docDao = LeaveRequestDocumentDAO.getInstance();
		
		OLeaveRequestDocument odoc = createOLeaveRequestDocument(document);
		docDao.update(con, odoc, BaseDAO.createRevisionTimestamp());
		
		InputStream is = document.getStream();
		try {
			docDao.deleteBytesById(con, odoc.getLeaveRequestDocumentId());
			return docDao.insertBytes(con, odoc.getLeaveRequestDocumentId(), IOUtils.toByteArray(is)) == 1;
		} finally {
			IOUtils.closeQuietly(is);
		}
	}
	
	static OOpportunityDocument doOpportunityDocumentInsert(Connection con, Integer oId, OpportunityDocumentWithStream document) throws DAOException, IOException {
		OpportunityDocumentDAO docDao = OpportunityDocumentDAO.getInstance();
		
		OOpportunityDocument odoc = createOOpportunityDocument(document);
		odoc.setId(IdentifierUtils.getUUIDTimeBased());
		odoc.setOpportunityId(oId);
		docDao.insert(con, odoc, BaseDAO.createRevisionTimestamp());
		
		InputStream is = document.getStream();
		try {
			docDao.insertBytes(con, odoc.getId(), IOUtils.toByteArray(is));
		} finally {
			IOUtils.closeQuietly(is);
		}
		
		return odoc;
	}
	
	static OOpportunityDocument createOOpportunityDocument(OpportunityDocument src) {
		if (src == null) return null;
		return fillOOpportunityDocument(new OOpportunityDocument(), src);
	}
	
	static OpportunityDocument createOpportunityDocument(OOpportunityDocument src) {
		if (src == null) return null;
		return fillOpportunityDocument(new OpportunityDocument(), src);
	}
	
	static <T extends OOpportunityDocument> T fillOOpportunityDocument(T tgt, OpportunityDocument src) {
		if ((tgt != null) && (src != null)) {
			tgt.setId(src.getId());
			tgt.setRevisionTimestamp(src.getRevisionTimestamp());
			tgt.setRevisionSequence(src.getRevisionSequence());
			tgt.setFilename(src.getFileName());
			tgt.setSize(src.getSize());
			tgt.setMediaType(src.getMediaType());
		}
		return tgt;
	}
	
	static <T extends OpportunityDocument> T fillOpportunityDocument(T tgt, OOpportunityDocument src) {
		if ((tgt != null) && (src != null)) {
			tgt.setId(src.getId());
			tgt.setRevisionTimestamp(src.getRevisionTimestamp());
			tgt.setRevisionSequence(src.getRevisionSequence());
			tgt.setFileName(src.getFilename());
			tgt.setSize(src.getSize());
			tgt.setMediaType(src.getMediaType());
		}
		return tgt;
	}
	
	static List<OpportunityDocument> createOpportunityDocumentList(List<OOpportunityDocument> items) {
		ArrayList<OpportunityDocument> list = new ArrayList<>(items.size());
		for (OOpportunityDocument item : items) {
			list.add(createOpportunityDocument(item));
		}
		return list;
	}
	
	static boolean doOpportunityDocumentUpdate(Connection con, OpportunityDocumentWithStream document) throws DAOException, IOException {
		OpportunityDocumentDAO docDao = OpportunityDocumentDAO.getInstance();
		
		OOpportunityDocument odoc = createOOpportunityDocument(document);
		docDao.update(con, odoc, BaseDAO.createRevisionTimestamp());
		
		InputStream is = document.getStream();
		try {
			docDao.deleteBytesById(con, odoc.getId());
			return docDao.insertBytes(con, odoc.getId(), IOUtils.toByteArray(is)) == 1;
		} finally {
			IOUtils.closeQuietly(is);
		}
	}
	
	static OOpportunityActionDocument doOpportunityActionDocumentInsert(Connection con, Integer oActId, OpportunityActionDocumentWithStream document) throws DAOException, IOException {
		OpportunityActionDocumentDAO docDao = OpportunityActionDocumentDAO.getInstance();
		
		OOpportunityActionDocument odoc = createOOpportunityActionDocument(document);
		odoc.setId(IdentifierUtils.getUUIDTimeBased());
		odoc.setOpportunityActionId(oActId);
		docDao.insert(con, odoc, BaseDAO.createRevisionTimestamp());
		
		InputStream is = document.getStream();
		try {
			docDao.insertBytes(con, odoc.getId(), IOUtils.toByteArray(is));
		} finally {
			IOUtils.closeQuietly(is);
		}
		
		return odoc;
	}
	
	static OOpportunityActionDocument createOOpportunityActionDocument(OpportunityActionDocument src) {
		if (src == null) return null;
		return fillOOpportunityActionDocument(new OOpportunityActionDocument(), src);
	}
	
	static OpportunityActionDocument createOpportunityActionDocument(OOpportunityActionDocument src) {
		if (src == null) return null;
		return fillOpportunityActionDocument(new OpportunityActionDocument(), src);
	}
	
	static <T extends OOpportunityActionDocument> T fillOOpportunityActionDocument(T tgt, OpportunityActionDocument src) {
		if ((tgt != null) && (src != null)) {
			tgt.setId(src.getId());
			tgt.setRevisionTimestamp(src.getRevisionTimestamp());
			tgt.setRevisionSequence(src.getRevisionSequence());
			tgt.setFilename(src.getFileName());
			tgt.setSize(src.getSize());
			tgt.setMediaType(src.getMediaType());
		}
		return tgt;
	}
	
	static <T extends OpportunityActionDocument> T fillOpportunityActionDocument(T tgt, OOpportunityActionDocument src) {
		if ((tgt != null) && (src != null)) {
			tgt.setId(src.getId());
			tgt.setRevisionTimestamp(src.getRevisionTimestamp());
			tgt.setRevisionSequence(src.getRevisionSequence());
			tgt.setFileName(src.getFilename());
			tgt.setSize(src.getSize());
			tgt.setMediaType(src.getMediaType());
		}
		return tgt;
	}
	
	static List<OpportunityActionDocument> createOpportunityActionDocumentList(List<OOpportunityActionDocument> items) {
		ArrayList<OpportunityActionDocument> list = new ArrayList<>(items.size());
		for (OOpportunityActionDocument item : items) {
			list.add(createOpportunityActionDocument(item));
		}
		return list;
	}
	
	static boolean doOpportunityActionDocumentUpdate(Connection con, OpportunityActionDocumentWithStream document) throws DAOException, IOException {
		OpportunityActionDocumentDAO docDao = OpportunityActionDocumentDAO.getInstance();
		
		OOpportunityActionDocument odoc = createOOpportunityActionDocument(document);
		docDao.update(con, odoc, BaseDAO.createRevisionTimestamp());
		
		InputStream is = document.getStream();
		try {
			docDao.deleteBytesById(con, odoc.getId());
			return docDao.insertBytes(con, odoc.getId(), IOUtils.toByteArray(is)) == 1;
		} finally {
			IOUtils.closeQuietly(is);
		}
	}
	
	static OCompany createOCompany(Company com) {
		if (com == null) {
			return null;
		}
		OCompany ocom = new OCompany();
		ocom.setCompanyId(com.getCompanyId());
		ocom.setDomainId(com.getDomainId());
		ocom.setName(com.getName());
		ocom.setAddress(com.getAddress());
		ocom.setPostalCode(com.getPostalCode());
		ocom.setCity(com.getCity());
		ocom.setState(com.getState());
		ocom.setPhone(com.getPhone());
		ocom.setFax(com.getFax());
		ocom.setVat(com.getVat());
		ocom.setTaxCode(com.getTaxCode());
		ocom.setBusinessRegister(com.getBusinessRegister());
		ocom.setRea(com.getRea());
		ocom.setFooterColumns(com.getFooterColumns());
		ocom.setFooterColumnLeft(com.getFooterColumnLeft());
		ocom.setFooterColumnRight(com.getFooterColumnRight());

		return ocom;
	}

	static Company createCompany(OCompany oCom, boolean hasPicture) {
		if (oCom == null) {
			return null;
		}
		
		Company com = new Company();
		com.setCompanyId(oCom.getCompanyId());
		com.setDomainId(oCom.getDomainId());
		com.setName(oCom.getName());
		com.setAddress(oCom.getAddress());
		com.setPostalCode(oCom.getPostalCode());
		com.setCity(oCom.getCity());
		com.setState(oCom.getState());
		com.setPhone(oCom.getPhone());
		com.setFax(oCom.getFax());
		com.setVat(oCom.getVat());
		com.setTaxCode(oCom.getTaxCode());
		com.setBusinessRegister(oCom.getBusinessRegister());
		com.setRea(oCom.getRea());
		com.setFooterColumns(oCom.getFooterColumns());
		com.setFooterColumnLeft(oCom.getFooterColumnLeft());
		com.setFooterColumnRight(oCom.getFooterColumnRight());
		com.setHasPicture(hasPicture);
		
		return com;
	}

	static CompanyUserAssociation createCompanyUser(OCompanyUser oComUsr) {

		if (oComUsr == null) {
			return null;
		}

		CompanyUserAssociation comUsr = new CompanyUserAssociation();
		comUsr.setAssociationId(oComUsr.getAssociationId());
		comUsr.setCompanyId(oComUsr.getCompanyId());
		comUsr.setUserId(oComUsr.getUserId());

		return comUsr;
	}

	static OCompanyUser createOCompanyUser(CompanyUserAssociation comUsr) {

		if (comUsr == null) {
			return null;
		}

		OCompanyUser oComUsr = new OCompanyUser();
		oComUsr.setAssociationId(comUsr.getAssociationId());
		oComUsr.setCompanyId(comUsr.getCompanyId());
		oComUsr.setUserId(comUsr.getUserId());

		return oComUsr;
	}
	
	static ODocStatus createODocStatus(DocStatus dStat) {
		if (dStat == null) {
			return null;
		}
		ODocStatus oDstat = new ODocStatus();
		oDstat.setDocStatusId(dStat.getDocStatusId());
		oDstat.setName(dStat.getName());
		oDstat.setDescription(dStat.getDescription());
		oDstat.setType(dStat.getType());
		oDstat.setBuiltIn(dStat.getBuiltIn());

		return oDstat;
	}

	static DocStatus createDocStatus(ODocStatus oDstat) {
		if (oDstat == null) {
			return null;
		}
		DocStatus dStat = new DocStatus();
		dStat.setDocStatusId(oDstat.getDocStatusId());
		dStat.setName(oDstat.getName());
		dStat.setDescription(oDstat.getDescription());
		dStat.setType(oDstat.getType());
		dStat.setBuiltIn(oDstat.getBuiltIn());

		return dStat;
	}

	static DocStatusGroupAssociation createDocStatusProfile(ODocStatusGroup odocS) {

		if (odocS == null) {
			return null;
		}

		DocStatusGroupAssociation docStatusGroup = new DocStatusGroupAssociation();
		docStatusGroup.setAssociationId(odocS.getAssociationId());
		docStatusGroup.setDocStatusId(odocS.getDocStatusId());
		docStatusGroup.setGroupId(odocS.getGroupId());

		return docStatusGroup;
	}

	static ODocStatusGroup createODocStatusProfile(DocStatusGroupAssociation docS) {

		if (docS == null) {
			return null;
		}

		ODocStatusGroup odocS = new ODocStatusGroup();
		odocS.setAssociationId(docS.getAssociationId());
		odocS.setDocStatusId(docS.getDocStatusId());
		odocS.setGroupId(docS.getGroupId());

		return odocS;
	}
	
	static DrmGroup createDrmGroup(ODrmGroup oGroup) {

		if (oGroup == null) {
			return null;
		}

		DrmGroup group = new DrmGroup();
		group.setGroupId(oGroup.getGroupId());
		group.setDomainId(oGroup.getDomainId());
		group.setName(oGroup.getName());
		group.setDescription(oGroup.getDescription());
		group.setGroupCategory(oGroup.getGroupCategory());
		group.setGroupType(oGroup.getGroupType());

		return group;
	}

	static ODrmGroup createODrmGroup(DrmGroup group) {

		if (group == null) {
			return null;
		}

		ODrmGroup oGroup = new ODrmGroup();
		oGroup.setGroupId(group.getGroupId());
		oGroup.setDomainId(group.getDomainId());
		oGroup.setName(group.getName());
		oGroup.setDescription(group.getDescription());
		oGroup.setGroupCategory(group.getGroupCategory());
		oGroup.setGroupType(group.getGroupType());

		return oGroup;
	}

	static DrmGroupUserAssociation createDrmGroupUser(ODrmGroupUser oGrpUsr) {

		if (oGrpUsr == null) {
			return null;
		}

		DrmGroupUserAssociation grpUsr = new DrmGroupUserAssociation();
		grpUsr.setAssociationId(oGrpUsr.getAssociationId());
		grpUsr.setGroupId(oGrpUsr.getGroupId());
		grpUsr.setUserId(oGrpUsr.getUserId());

		return grpUsr;
	}

	static ODrmGroupUser createODrmGroupUser(DrmGroupUserAssociation grpUsr) {

		if (grpUsr == null) {
			return null;
		}

		ODrmGroupUser oGrpUsr = new ODrmGroupUser();
		oGrpUsr.setAssociationId(grpUsr.getAssociationId());
		oGrpUsr.setGroupId(grpUsr.getGroupId());
		oGrpUsr.setUserId(grpUsr.getUserId());

		return oGrpUsr;
	}
	
	static EmployeeProfile createEmployeeProfile(OEmployeeProfile oEP) {

		if (oEP == null) {
			return null;
		}

		EmployeeProfile eProfile = new EmployeeProfile();
		eProfile.setId(oEP.getId());
		eProfile.setDomainId(oEP.getDomainId());
		eProfile.setUserId(oEP.getUserId());
		eProfile.setNumber(oEP.getNumber());
		eProfile.setTolerance(oEP.getTolerance());
		eProfile.setExtraordinary(oEP.getExtraordinary());
		eProfile.setOnlyPresence(oEP.getOnlyPresence());
		eProfile.setHourProfileId(oEP.getHourProfileId());

		return eProfile;
	}

	static OEmployeeProfile createOEmployeeProfile(EmployeeProfile eProfile) {
		if (eProfile == null) {
			return null;
		}

		OEmployeeProfile oEp = new OEmployeeProfile();
		oEp.setId(eProfile.getId());
		oEp.setDomainId(eProfile.getDomainId());
		oEp.setUserId(eProfile.getUserId());
		oEp.setNumber(eProfile.getNumber());
		oEp.setTolerance(eProfile.getTolerance());
		oEp.setExtraordinary(eProfile.getExtraordinary());
		oEp.setOnlyPresence(eProfile.getOnlyPresence());
		oEp.setHourProfileId(eProfile.getHourProfileId());

		return oEp;
	}
	
	static HourProfile createHourProfile(OHourProfile oHP) {

		if (oHP == null) {
			return null;
		}

		HourProfile hpProfile = new HourProfile();
		hpProfile.setId(oHP.getId());
		hpProfile.setDomainId(oHP.getDomainId());
		hpProfile.setDescription(oHP.getDescription());
		
		return hpProfile;
	}

	static OHourProfile createOHourProfile(HourProfile hProfile) {

		if (hProfile == null) {
			return null;
		}

		OHourProfile oHp = new OHourProfile();
		oHp.setId(hProfile.getId());
		oHp.setDomainId(hProfile.getDomainId());
		oHp.setDescription(hProfile.getDescription());

		return oHp;
	}
	
	static LineHour createLineHour(OLineHour oEH) {

		if (oEH == null) {
			return null;
		}

		LineHour eh = new LineHour();
		eh.setId(oEH.getId());
		eh.setDomainId(oEH.getDomainId());
		eh.setHourProfileId(oEH.getHourProfileId());
		eh.setLineId(oEH.getLineId());
		eh.setE_1(oEH.get_1E());
		eh.setU_1(oEH.get_1U());
		eh.setH_1(oEH.get_1H());
		eh.setE_2(oEH.get_2E());
		eh.setU_2(oEH.get_2U());
		eh.setH_2(oEH.get_2H());
		eh.setE_3(oEH.get_3E());
		eh.setU_3(oEH.get_3U());
		eh.setH_3(oEH.get_3H());
		eh.setE_4(oEH.get_4E());
		eh.setU_4(oEH.get_4U());
		eh.setH_4(oEH.get_4H());
		eh.setE_5(oEH.get_5E());
		eh.setU_5(oEH.get_5U());
		eh.setH_5(oEH.get_5H());
		eh.setE_6(oEH.get_6E());
		eh.setU_6(oEH.get_6U());
		eh.setH_6(oEH.get_6H());
		eh.setE_7(oEH.get_7E());
		eh.setU_7(oEH.get_7U());
		eh.setH_7(oEH.get_7H());

		return eh;
	}

	static OLineHour createOLineHour(LineHour eh) {

		if (eh == null) {
			return null;
		}

		OLineHour oEH = new OLineHour();
		oEH.setId(eh.getId());
		oEH.setDomainId(eh.getDomainId());
		oEH.setHourProfileId(eh.getHourProfileId());
		oEH.setLineId(eh.getLineId());
		oEH.set_1E(eh.getE_1());
		oEH.set_1U(eh.getU_1());
		oEH.set_1H(getDiffHours(eh.getE_1(), eh.getU_1()));
		oEH.set_2E(eh.getE_2());
		oEH.set_2U(eh.getU_2());
		oEH.set_2H(getDiffHours(eh.getE_2(), eh.getU_2()));
		oEH.set_3E(eh.getE_3());
		oEH.set_3U(eh.getU_3());
		oEH.set_3H(getDiffHours(eh.getE_3(), eh.getU_3()));
		oEH.set_4E(eh.getE_4());
		oEH.set_4U(eh.getU_4());
		oEH.set_4H(getDiffHours(eh.getE_4(), eh.getU_4()));
		oEH.set_5E(eh.getE_5());
		oEH.set_5U(eh.getU_5());
		oEH.set_5H(getDiffHours(eh.getE_5(), eh.getU_5()));
		oEH.set_6E(eh.getE_6());
		oEH.set_6U(eh.getU_6());
		oEH.set_6H(getDiffHours(eh.getE_6(), eh.getU_6()));
		oEH.set_7E(eh.getE_7());
		oEH.set_7U(eh.getU_7());
		oEH.set_7H(getDiffHours(eh.getE_7(), eh.getU_7()));

		return oEH;
	}
	
	static TimetableStamp createTimetableStamp(OTimetableStamp oTS) {

		if (oTS == null) {
			return null;
		}

		TimetableStamp stamp = new TimetableStamp();
		stamp.setId(oTS.getId());
		stamp.setDomainId(oTS.getDomainId());
		stamp.setUserId(oTS.getUserId());
		stamp.setType(oTS.getType());
		stamp.setEntrance(oTS.getEntrance());
		stamp.setExit(oTS.getExit());
		stamp.setLocation(oTS.getLocation());
		// stamp.setActivityId(oTS.getActivityId());

		return stamp;
	}
	
	static TimetableStamp fillTimetableStamp(TimetableStamp tgt, OTimetableStamp src) {
		if ((tgt != null) && (src != null)) {
			tgt.setId(src.getId());
			tgt.setDomainId(src.getDomainId());
			tgt.setUserId(src.getUserId());
			tgt.setType(src.getType());
			tgt.setEntrance(src.getEntrance());
			tgt.setExit(src.getExit());
			tgt.setLocation(src.getLocation());
			// tgt.setActivityId(src.getActivityId());
		}
		return tgt;
	}

	static OTimetableStamp createOTimetableStamp(TimetableStamp stamp) {

		if (stamp == null) {
			return null;
		}

		OTimetableStamp oTS = new OTimetableStamp();
		oTS.setId(stamp.getId());
		oTS.setDomainId(stamp.getDomainId());
		oTS.setUserId(stamp.getUserId());
		oTS.setType(stamp.getType());
		oTS.setEntrance(stamp.getEntrance());
		oTS.setExit(stamp.getExit());
		oTS.setLocation(stamp.getLocation());

		return oTS;
	}

	static DrmProfile createProfile(ODrmProfile oProfile) {

		if (oProfile == null) {
			return null;
		}

		DrmProfile profile = new DrmProfile();
		profile.setProfileId(oProfile.getProfileId());
		profile.setDomainId(oProfile.getDomainId());
		profile.setDescription(oProfile.getDescription());
		profile.setType(oProfile.getType());

		return profile;
	}

	static ODrmProfile createODrmProfile(DrmProfile profile) {

		if (profile == null) {
			return null;
		}

		ODrmProfile oProfile = new ODrmProfile();
		oProfile.setProfileId(profile.getProfileId());
		oProfile.setDomainId(profile.getDomainId());
		oProfile.setDescription(profile.getDescription());
		oProfile.setType(profile.getType());

		return oProfile;
	}

	static ProfileMasterdata createProfileMasterdata(OProfileMasterdata oPrfMasterD) {

		if (oPrfMasterD == null) {
			return null;
		}

		ProfileMasterdata prfMasterD = new ProfileMasterdata();
		prfMasterD.setId(oPrfMasterD.getId());
		prfMasterD.setProfileId(oPrfMasterD.getProfileId());
		prfMasterD.setMasterDataId(oPrfMasterD.getMasterDataId());

		return prfMasterD;
	}

	static OProfileMasterdata createOProfileMasterdata(ProfileMasterdata prfMasterD) {

		if (prfMasterD == null) {
			return null;
		}

		OProfileMasterdata oPrfMasterD = new OProfileMasterdata();
		oPrfMasterD.setId(prfMasterD.getId());
		oPrfMasterD.setProfileId(prfMasterD.getProfileId());
		oPrfMasterD.setMasterDataId(prfMasterD.getMasterDataId());

		return oPrfMasterD;
	}

	static ProfileSupervisedUser createProfileSupervisedUser(OProfileSupervisedUser oPrfSupervised) {

		if (oPrfSupervised == null) {
			return null;
		}

		ProfileSupervisedUser prfSupervised = new ProfileSupervisedUser();
		prfSupervised.setId(oPrfSupervised.getId());
		prfSupervised.setProfileId(oPrfSupervised.getProfileId());
		prfSupervised.setUserId(oPrfSupervised.getUserId());

		return prfSupervised;
	}

	static OProfileSupervisedUser createOProfileSupervisedUser(ProfileSupervisedUser prfSupervised) {

		if (prfSupervised == null) {
			return null;
		}

		OProfileSupervisedUser oPrfSupervised = new OProfileSupervisedUser();
		oPrfSupervised.setId(prfSupervised.getId());
		oPrfSupervised.setProfileId(prfSupervised.getProfileId());
		oPrfSupervised.setUserId(prfSupervised.getUserId());

		return oPrfSupervised;
	}
	
	static ProfileMember createProfileMember(OProfileMember with) {
		return fillProfileMember(new ProfileMember(), with);
	}

	static ProfileMember fillProfileMember(ProfileMember fill, OProfileMember with) {
		if ((fill != null) && (with != null)) {
			fill.setId(with.getId());
			fill.setProfileId(with.getProfileId());
			fill.setUserId(with.getUserId());
		}
		return fill;
	}

	static OProfileMember fillOProfileMember(OProfileMember fill, ProfileMember with) {
		if ((fill != null) && (with != null)) {
			fill.setId(with.getId());
			fill.setProfileId(with.getProfileId());
			fill.setUserId(with.getUserId());
		}
		return fill;
	}
	
	static OLineHour fillOLineHour(OLineHour fill, LineHour with) {
		if ((fill != null) && (with != null)) {
			fill.setLineId(with.getLineId());
			fill.set_1E(with.getE_1());
			fill.set_1U(with.getU_1());
			fill.set_1H(getDiffHours(with.getE_1(), with.getU_1()));
			fill.set_2E(with.getE_2());
			fill.set_2U(with.getU_2());
			fill.set_2H(getDiffHours(with.getE_2(), with.getU_2()));
			fill.set_3E(with.getE_3());
			fill.set_3U(with.getU_3());
			fill.set_3H(getDiffHours(with.getE_3(), with.getU_3()));
			fill.set_4E(with.getE_4());
			fill.set_4U(with.getU_4());
			fill.set_4H(getDiffHours(with.getE_4(), with.getU_4()));
			fill.set_5E(with.getE_5());
			fill.set_5U(with.getU_5());
			fill.set_5H(getDiffHours(with.getE_5(), with.getU_5()));
			fill.set_6E(with.getE_6());
			fill.set_6U(with.getU_6());
			fill.set_6H(getDiffHours(with.getE_6(), with.getU_6()));
			fill.set_7E(with.getE_7());
			fill.set_7U(with.getU_7());
			fill.set_7H(getDiffHours(with.getE_7(), with.getU_7()));
		}
		return fill;
	}
	
	static String getDiffHours(String e, String u){
		if(null != e && null != u && !e.isEmpty() && !u.isEmpty()){
			java.util.Date startDate = new java.util.Date();
			java.util.Date endDate = new java.util.Date();
			startDate.setHours(Integer.parseInt(e.split(":")[0]));
			startDate.setMinutes(Integer.parseInt(e.split(":")[1]));
			startDate.setSeconds(0);
			endDate.setHours(Integer.parseInt(u.split(":")[0]));
			endDate.setMinutes(Integer.parseInt(u.split(":")[1]));
			endDate.setSeconds(0);

			Interval interval = new Interval(startDate.getTime(), endDate.getTime());
			Period period = interval.toPeriod();
			
			// return String.format("%02d:%02d", period.getHours(), period.getMinutes());
			return String.valueOf((period.getHours() * 60) + period.getMinutes());
			// return period.getHours() + "." + period.getMinutes();
			
		}else{
			return null;
		}
	  }
	
	static ODrmLineManagerUsers fillODrmLineManagerUsers(ODrmLineManagerUsers fill, UserForManager with) {
		if ((fill != null) && (with != null)) {
			fill.setUserId(with.getUserId());
		}
		return fill;
	}
	
	static ODrmFolder createODrmFolder(DrmFolder fld) {
		if (fld == null) {
			return null;
		}
		ODrmFolder oFld = new ODrmFolder();
		oFld.setFolderId(fld.getFolderId());
		oFld.setName(fld.getName());
		oFld.setDescription(fld.getDescription());
		oFld.setExpired(fld.isExpired());

		return oFld;
	}

	static DrmFolder createFolder(ODrmFolder oFld) {
		if (oFld == null) {
			return null;
		}
		DrmFolder fld = new DrmFolder();
		fld.setFolderId(oFld.getFolderId());
		fld.setName(oFld.getName());
		fld.setDescription(oFld.getDescription());
		fld.setExpired(oFld.getExpired());

		return fld;
	}

	static DrmFolderGroupAssociation createDrmFolderGroup(ODrmFolderGroup oFldGrp) {

		if (oFldGrp == null) {
			return null;
		}

		DrmFolderGroupAssociation fldGrp = new DrmFolderGroupAssociation();
		fldGrp.setAssociationId(oFldGrp.getAssociationId());
		fldGrp.setFolderId(oFldGrp.getFolderId());
		fldGrp.setGroupId(oFldGrp.getGroupId());

		return fldGrp;
	}

	static ODrmFolderGroup createODrmFolderGroup(DrmFolderGroupAssociation fldGrp) {

		if (fldGrp == null) {
			return null;
		}

		ODrmFolderGroup oFldGrp = new ODrmFolderGroup();
		oFldGrp.setAssociationId(fldGrp.getAssociationId());
		oFldGrp.setFolderId(fldGrp.getFolderId());
		oFldGrp.setGroupId(fldGrp.getGroupId());

		return oFldGrp;
	}
	
	static Opportunity createOpportunity(OOpportunity oOpt) {
		if (oOpt == null) {
			return null;
		}
		Opportunity o = new Opportunity();
		o.setId(oOpt.getId());
		o.setDomainId(oOpt.getDomainId());
		o.setCompanyId(oOpt.getCompanyId());
		o.setOperatorId(oOpt.getOperatorId());
		o.setStartDate(oOpt.getStartDate());
		o.setEndDate(oOpt.getEndDate());
		o.setExecutedWith(oOpt.getExecutedWith());
		o.setCustomerId(oOpt.getCustomerId());
		o.setCustomerStatId(oOpt.getCustomerStatId());
		o.setSector(oOpt.getSector());
		o.setDescription(oOpt.getDescription());
		o.setPlace(oOpt.getPlace());
		o.setObjective(oOpt.getObjective());
		o.setCausalId(oOpt.getCausalId());
		o.setActivityId(oOpt.getActivityId());
		o.setObjective2(oOpt.getObjective_2());
		o.setResult(oOpt.getResult());
		o.setDiscoveries(oOpt.getDiscoveries());
		o.setCustomerPotential(oOpt.getCustomerPotential());
		o.setNotes(oOpt.getNotes());
		o.setStatusId(oOpt.getStatusId());
		o.setSignedBy(oOpt.getSignedBy());
		o.setSignature(oOpt.getSignature());
		o.setSuccess(oOpt.getSuccess());
		o.setEventId(oOpt.getEventId());

		return o;
	}

	static OOpportunity createOOpportunity(Opportunity o) {
		if (o == null) {
			return null;
		}
		OOpportunity oOpt = new OOpportunity();
		oOpt.setId(o.getId());
		oOpt.setDomainId(o.getDomainId());
		oOpt.setCompanyId(o.getCompanyId());
		oOpt.setOperatorId(o.getOperatorId());
		oOpt.setStartDate(o.getStartDate());
		oOpt.setEndDate(o.getEndDate());
		oOpt.setExecutedWith(o.getExecutedWith());
		oOpt.setCustomerId(o.getCustomerId());
		oOpt.setCustomerStatId(o.getCustomerStatId());
		oOpt.setSector(o.getSector());
		oOpt.setDescription(o.getDescription());
		oOpt.setPlace(o.getPlace());
		oOpt.setObjective(o.getObjective());
		oOpt.setCausalId(o.getCausalId());
		oOpt.setActivityId(o.getActivityId());
		oOpt.setObjective_2(o.getObjective2());
		oOpt.setResult(o.getResult());
		oOpt.setDiscoveries(o.getDiscoveries());
		oOpt.setCustomerPotential(o.getCustomerPotential());
		oOpt.setNotes(o.getNotes());
		oOpt.setStatusId(o.getStatusId());
		oOpt.setSignedBy(o.getSignedBy());
		oOpt.setSignature(o.getSignature());
		oOpt.setSuccess(o.getSuccess());
		oOpt.setEventId(o.getEventId());

		return oOpt;
	}
	
	static OOpportunityAction createOOpportunityAction(OpportunityAction act) {

		if (act == null) {
			return null;
		}

		OOpportunityAction oAct = new OOpportunityAction();
		oAct.setId(act.getId());
		oAct.setOpportunityId(act.getOpportunityId());
		oAct.setOperatorId(act.getOperatorId());
		oAct.setStatusId(act.getStatusId());
		oAct.setStartDate(act.getStartDate());
		oAct.setEndDate(act.getEndDate());
		oAct.setDescription(act.getDescription());
		oAct.setPlace(act.getPlace());
		oAct.setSubsequentActions(act.getSubsequentActions());
		oAct.setActivityId(act.getActivityId());
		oAct.setEventId(act.getEventId());

		return oAct;
	}

	static OpportunityAction createOpportunityAction(OOpportunityAction oAct) {

		if (oAct == null) {
			return null;
		}

		OpportunityAction act = new OpportunityAction();
		act.setId(oAct.getId());
		act.setOpportunityId(oAct.getOpportunityId());
		act.setOperatorId(oAct.getOperatorId());
		act.setStatusId(oAct.getStatusId());
		act.setStartDate(oAct.getStartDate());
		act.setEndDate(oAct.getEndDate());
		act.setDescription(oAct.getDescription());
		act.setPlace(oAct.getPlace());
		act.setSubsequentActions(oAct.getSubsequentActions());
		act.setActivityId(oAct.getActivityId());
		act.setEventId(oAct.getEventId());

		return act;
	}
	
	static OOpportunityActionInterlocutor createOOpportunityActionInterlocutor(OpportunityActionInterlocutor actInterl) {

		if (actInterl == null) {
			return null;
		}

		OOpportunityActionInterlocutor oActInterl = new OOpportunityActionInterlocutor();
		oActInterl.setId(actInterl.getId());
		oActInterl.setOpportunityActionId(actInterl.getOpportunityActionId());
		oActInterl.setContactId(actInterl.getContactId());

		return oActInterl;
	}

	static OpportunityActionInterlocutor createOpportunityActionInterlocutor(OOpportunityActionInterlocutor oActInterl) {

		if (oActInterl == null) {
			return null;
		}

		OpportunityActionInterlocutor actInterl = new OpportunityActionInterlocutor();
		actInterl.setId(oActInterl.getId());
		actInterl.setOpportunityActionId(oActInterl.getOpportunityActionId());
		actInterl.setContactId(oActInterl.getContactId());

		return actInterl;
	}
	
	static OOpportunityInterlocutor createOOpportunityInterlocutor(OpportunityInterlocutor interl) {

		if (interl == null) {
			return null;
		}

		OOpportunityInterlocutor oInterl = new OOpportunityInterlocutor();
		oInterl.setId(interl.getId());
		oInterl.setOpportunityId(interl.getOpportunityId());
		oInterl.setContactId(interl.getContactId());

		return oInterl;
	}

	static OpportunityInterlocutor createOpportunityInterlocutor(OOpportunityInterlocutor oInterl) {

		if (oInterl == null) {
			return null;
		}

		OpportunityInterlocutor interl = new OpportunityInterlocutor();
		interl.setId(oInterl.getId());
		interl.setOpportunityId(oInterl.getOpportunityId());
		interl.setContactId(oInterl.getContactId());

		return interl;
	}
	
	static OpportunityField createOpportunityField(OOpportunityField oOField) {

		if (oOField == null) {
			return null;
		}

		OpportunityField oField = new OpportunityField();
		
		oField.setDomainId(oOField.getDomainId());
		oField.setTab(EnumUtils.forSerializedName(oOField.getTabId(), OpportunityField.Tab.class));
		oField.setFieldId(oOField.getFieldId());
		oField.setVisible(oOField.getVisible());
		oField.setRequired(oOField.getRequired());
		oField.setOrder(oOField.getOrder());
		oField.setLabel(oOField.getLabel());
		oField.setShowOnGrid(oOField.getShowOnGrid());

		return oField;
	}

	static OOpportunityField createOOpportunityField(OpportunityField oField) {

		if (oField == null) {
			return null;
		}

		OOpportunityField oOField = new OOpportunityField();
		oOField.setDomainId(oField.getDomainId());
		oOField.setTabId(EnumUtils.toSerializedName(oField.getTab()));
		oOField.setFieldId(oField.getFieldId());
		oOField.setVisible(oField.getVisible());
		oOField.setRequired(oField.getRequired());
		oOField.setOrder(oField.getOrder());		
		oOField.setLabel(oField.getLabel());
		oOField.setShowOnGrid(oField.getShowOnGrid());

		return oOField;
	}
	
	static List<LocalDate> getDateRange(LocalDate start, LocalDate end) {
        List<LocalDate> ret = new ArrayList<LocalDate>();
        LocalDate tmp = start;
        while(tmp.isBefore(end) || tmp.equals(end)) {
            ret.add(tmp);
            tmp = tmp.plusDays(1);
        }
        return ret;
    }
	
	static String getHourRange(String start, String end) {
        String[] fractions1 = start.split(":");
		String[] fractions2 = end.split(":");
		
		Integer hours1 = Integer.parseInt(fractions1[0]);
		Integer hours2 = Integer.parseInt(fractions2[0]);
		Integer minutes1 = Integer.parseInt(fractions1[1]);
		Integer minutes2 = Integer.parseInt(fractions2[1]); 
		
		int hourDiff = hours2- hours1;
		
		int minutesDiff = minutes2 - minutes1;
		
		if (minutesDiff < 0) {
			minutesDiff = 60 + minutesDiff;
			hourDiff--;
		}
		if (hourDiff < 0) {
			hourDiff = 24 + hourDiff ;
		}
		return hourDiff + "." + minutesDiff;
    }
	
	static LeaveRequest createLeaveRequest(OLeaveRequest oLr) {
		if (oLr == null) {
			return null;
		}
		LeaveRequest lr = new LeaveRequest();
		lr.setLeaveRequestId(oLr.getLeaveRequestId());
		lr.setDomainId(oLr.getDomainId());
		lr.setCompanyId(oLr.getCompanyId());
		lr.setUserId(oLr.getUserId());
		lr.setManagerId(oLr.getManagerId());
		lr.setType(oLr.getType());
		lr.setFromDate(oLr.getFromDate());
		lr.setToDate(oLr.getToDate());
		lr.setFromHour(oLr.getFromHour());
		lr.setToHour(oLr.getToHour());
		lr.setStatus(oLr.getStatus());
		lr.setNotes(oLr.getNotes());
		lr.setCancRequest(oLr.getEmployeeCancReq());
		lr.setResult(oLr.getResult());
		lr.setCancReason(oLr.getCancReason());
		lr.setCancResult(oLr.getCancResult());
		lr.setEventId(oLr.getEventId());

		return lr;
	}

	static OLeaveRequest createOLeaveRequest(LeaveRequest lr) {
		if (lr == null) {
			return null;
		}
		OLeaveRequest oLr = new OLeaveRequest();
		oLr.setLeaveRequestId(lr.getLeaveRequestId());
		oLr.setDomainId(lr.getDomainId());
		oLr.setCompanyId(lr.getCompanyId());
		oLr.setUserId(lr.getUserId());
		oLr.setManagerId(lr.getManagerId());
		oLr.setType(lr.getType());
		oLr.setFromDate(lr.getFromDate());
		oLr.setToDate(lr.getToDate());
		oLr.setFromHour(lr.getFromHour());
		oLr.setToHour(lr.getToHour());
		oLr.setStatus(lr.getStatus());
		oLr.setNotes(lr.getNotes());
		oLr.setResult(lr.getResult());
		oLr.setEmployeeCancReq(lr.getCancRequest());
		oLr.setCancReason(lr.getCancReason());
		oLr.setCancResult(lr.getCancResult());
		oLr.setEventId(lr.getEventId());

		return oLr;
	}
	
	static OExpenseNoteSetting createOExpenseNoteSetting(ExpenseNoteSetting enSetting) {
		if (enSetting == null) {
			return null;
		}
		OExpenseNoteSetting oSetting = new OExpenseNoteSetting();
		oSetting.setExpenseNoteSettingId(enSetting.getExpenseNoteSettingId());
		oSetting.setDomainId(enSetting.getDomainId());
		oSetting.setAverageMaximum(enSetting.getAverageMaximum());
		oSetting.setKmCost(enSetting.getKmCost());
		oSetting.setDefaultCurrency(enSetting.getDefaultCurrency());

		return oSetting;
	}

	static ExpenseNoteSetting createExpenseNoteSetting(OExpenseNoteSetting oEnSetting) {
		if (oEnSetting == null) {
			return null;
		}
		ExpenseNoteSetting enSetting = new ExpenseNoteSetting();
		enSetting.setExpenseNoteSettingId(oEnSetting.getExpenseNoteSettingId());
		enSetting.setDomainId(oEnSetting.getDomainId());
		enSetting.setAverageMaximum(oEnSetting.getAverageMaximum());
		enSetting.setKmCost(oEnSetting.getKmCost());
		enSetting.setDefaultCurrency(oEnSetting.getDefaultCurrency());

		return enSetting;
	}
	
	static OTimetableSetting createOTimetableSetting(TimetableSetting tSetting) {
		if (tSetting == null) {
			return null;
		}
		OTimetableSetting oSetting = new OTimetableSetting();
		oSetting.setTimetableSettingId(tSetting.getTimetableSettingId());
		oSetting.setDomainId(tSetting.getDomainId());
		oSetting.setCompanyExit(tSetting.getCompanyExit());
		oSetting.setManageStamp(tSetting.getManageStamp());
		oSetting.setAllowedAddresses(tSetting.getAllowedAddresses());
		oSetting.setAllowedUsers(tSetting.getAllowedUsers());
		oSetting.setStaffOfficeEmail(tSetting.getStaffOfficeEmail());
		oSetting.setRequestsHolidaysPermitsPreviousDates(tSetting.getRequestsHolidaysPermitsPreviousDates());
		oSetting.setTotalToleranceInMinutes(tSetting.getTotalToleranceInMinutes());
		oSetting.setRounding(tSetting.getRounding());
		oSetting.setMinimumExtraordinary(tSetting.getMinimumExtraordinary());
		oSetting.setBreakAnomaly(tSetting.getBreakAnomaly());
		oSetting.setReadOnlyEvents(tSetting.getReadOnlyEvents());
		oSetting.setRequestsPermitsNotRemunered(tSetting.getRequestsPermitsNotRemunered());
		oSetting.setRequestsPermitsMedicalVisits(tSetting.getRequestsPermitsMedicalVisits());
		oSetting.setRequestsPermitsContractuals(tSetting.getRequestsPermitsContractuals());
		oSetting.setMedicalVisitsAutomaticallyApproved(tSetting.getMedicalVisitsAutomaticallyApproved());
		oSetting.setCalendarUserId(tSetting.getCalendarUserId());
		oSetting.setDefaultEventActivityId(tSetting.getDefaultEventActivityId());
		oSetting.setRequestsSickness(tSetting.getRequestsSickness());
		oSetting.setSicknessAutomaticallyApproved(tSetting.getSicknessAutomaticallyApproved());
		oSetting.setDefaultCausalHolidays(tSetting.getDefaultCausalHolidays());
		oSetting.setDefaultCausalOvertime(tSetting.getDefaultCausalOvertime());
		oSetting.setDefaultCausalPermits(tSetting.getDefaultCausalPermits());
		oSetting.setDefaultCausalSickness(tSetting.getDefaultCausalSickness());
		oSetting.setDefaultCausalMedicalVisit(tSetting.getDefaultCausalMedicalVisit());
		oSetting.setDefaultCausalWorkingHours(tSetting.getDefaultCausalWorkingHours());
		
		return oSetting;
	}

	static TimetableSetting createTimetableSetting(OTimetableSetting oTSetting) {
		if (oTSetting == null) {
			return null;
		}
		TimetableSetting tSetting = new TimetableSetting();
		tSetting.setTimetableSettingId(oTSetting.getTimetableSettingId());
		tSetting.setDomainId(oTSetting.getDomainId());
		tSetting.setCompanyExit(oTSetting.getCompanyExit());
		tSetting.setManageStamp(oTSetting.getManageStamp());
		tSetting.setAllowedAddresses(oTSetting.getAllowedAddresses());
		tSetting.setAllowedUsers(oTSetting.getAllowedUsers());
		tSetting.setStaffOfficeEmail(oTSetting.getStaffOfficeEmail());
		tSetting.setRequestsHolidaysPermitsPreviousDates(oTSetting.getRequestsHolidaysPermitsPreviousDates());
		tSetting.setTotalToleranceInMinutes(oTSetting.getTotalToleranceInMinutes());
		tSetting.setRounding(oTSetting.getRounding());
		tSetting.setMinimumExtraordinary(oTSetting.getMinimumExtraordinary());
		tSetting.setBreakAnomaly(oTSetting.getBreakAnomaly());
		tSetting.setReadOnlyEvents(oTSetting.getReadOnlyEvents());
		tSetting.setRequestsPermitsNotRemunered(oTSetting.getRequestsPermitsNotRemunered());
		tSetting.setRequestsPermitsMedicalVisits(oTSetting.getRequestsPermitsMedicalVisits());
		tSetting.setRequestsPermitsContractuals(oTSetting.getRequestsPermitsContractuals());
		tSetting.setMedicalVisitsAutomaticallyApproved(oTSetting.getMedicalVisitsAutomaticallyApproved());
		tSetting.setCalendarUserId(oTSetting.getCalendarUserId());
		tSetting.setDefaultEventActivityId(oTSetting.getDefaultEventActivityId());
		tSetting.setRequestsSickness(oTSetting.getRequestsSickness());
		tSetting.setSicknessAutomaticallyApproved(oTSetting.getSicknessAutomaticallyApproved());
		tSetting.setDefaultCausalHolidays(oTSetting.getDefaultCausalHolidays());
		tSetting.setDefaultCausalOvertime(oTSetting.getDefaultCausalOvertime());
		tSetting.setDefaultCausalPermits(oTSetting.getDefaultCausalPermits());
		tSetting.setDefaultCausalSickness(oTSetting.getDefaultCausalSickness());
		tSetting.setDefaultCausalMedicalVisit(oTSetting.getDefaultCausalMedicalVisit());
		tSetting.setDefaultCausalWorkingHours(oTSetting.getDefaultCausalWorkingHours());
		
		return tSetting;
	}
	
	static OWorkReportSetting createOWorkReportSetting(WorkReportSetting wrkSetting) {
		if (wrkSetting == null) {
			return null;
		}
		OWorkReportSetting oSetting = new OWorkReportSetting();
		oSetting.setWorkReportSettingId(wrkSetting.getWorkReportSettingId());
		oSetting.setDomainId(wrkSetting.getDomainId());
		oSetting.setWarrantyText(wrkSetting.getWarrantyText());

		return oSetting;
	}

	static WorkReportSetting createWorkReportSetting(OWorkReportSetting oWrkSetting) {
		if (oWrkSetting == null) {
			return null;
		}
		WorkReportSetting wrkSetting = new WorkReportSetting();
		wrkSetting.setWorkReportSettingId(oWrkSetting.getWorkReportSettingId());
		wrkSetting.setDomainId(oWrkSetting.getDomainId());
		wrkSetting.setWarrantyText(oWrkSetting.getWarrantyText());

		return wrkSetting;
	}

	static WorkType createWorkType(OWorkType oType) {

		if (oType == null) {
			return null;
		}

		WorkType type = new WorkType();
		type.setWorkTypeId(oType.getWorkTypeId());
		type.setDomainId(oType.getDomainId());
		type
				.setRevisionStatus(EnumUtils.forSerializedName(oType.getRevisionStatus(), WorkType.RevisionStatus.class
				));
		type.setExternalId(oType.getExternalId());
		type.setDescription(oType.getDescription());

		return type;
	}

	static OWorkType createOWorkType(WorkType type) {

		if (type == null) {
			return null;
		}

		OWorkType oType = new OWorkType();
		oType.setWorkTypeId(type.getWorkTypeId());
		oType.setDomainId(type.getDomainId());
		oType.setRevisionStatus(EnumUtils.toSerializedName(type.getRevisionStatus()));
		oType.setExternalId(type.getExternalId());
		oType.setDescription(type.getDescription());

		return oType;
	}
	
	static CostType createCostType(OCostType oCt) {

		if (oCt == null) {
			return null;
		}

		CostType ct = new CostType();
		ct.setDomainId(oCt.getDomainId());
		ct.setId(oCt.getId());
		ct.setDescription(oCt.getDescription());
		ct.setMaxImport(oCt.getMaxImport());
		ct.setCostType(oCt.getCostType());
		ct.setWithOthers(oCt.getWithOthers());
		ct.setPerPerson(oCt.getPerPerson());
		ct.setKm(oCt.getKm());
		ct.setAdvancePayment(oCt.getAdvancePayment());
		ct.setExchange(oCt.getExchange());

		return ct;
	}

	static OCostType createOCostType(CostType ct) {

		if (ct == null) {
			return null;
		}

		OCostType oCt = new OCostType();
		oCt.setDomainId(ct.getDomainId());
		oCt.setId(ct.getId());
		oCt.setDescription(ct.getDescription());
		oCt.setMaxImport(ct.getMaxImport());
		oCt.setCostType(ct.getCostType());
		oCt.setWithOthers(ct.getWithOthers());
		oCt.setPerPerson(ct.getPerPerson());
		oCt.setKm(ct.getKm());
		oCt.setAdvancePayment(ct.getAdvancePayment());
		oCt.setExchange(ct.getExchange());

		return oCt;
	}
	
	static HolidayDate createHolidayDate(OHolidayDate oHd) {
		if (oHd == null) {
			return null;
		}

		HolidayDate hd = new HolidayDate();
        
        hd.setHolidayDateId(oHd.getHolidayDateId());
		hd.setDomainId(oHd.getDomainId());
		hd.setDate(oHd.getDate());
		hd.setDescription(oHd.getDescription());

		return hd;
	}

	static OHolidayDate createOHolidayDate(HolidayDate hd) {
		if (hd == null) {
			return null;
		}

		OHolidayDate oHd = new OHolidayDate();
        
        oHd.setHolidayDateId(hd.getHolidayDateId());
		oHd.setDomainId(hd.getDomainId());
		oHd.setDate(hd.getDate());
		oHd.setDescription(hd.getDescription());

		return oHd;
	}
	
	static Causal createCausal(OCausal oC) {
		if (oC == null) {
			return null;
		}

		Causal c = new Causal();
        
        c.setId(oC.getId());
		c.setDescription(oC.getDescription());
		c.setExternalCode(oC.getExternalCode());
		c.setSign(oC.getSign().intValue());

		return c;
	}

	static OCausal createOCausal(Causal c) {
		if (c == null) {
			return null;
		}

		OCausal oC = new OCausal();
        
        oC.setId(c.getId());
		oC.setDescription(c.getDescription());
		oC.setExternalCode(c.getExternalCode());
		oC.setSign(new BigDecimal(c.getSign()));

		return oC;
	}

	static BusinessTrip createBusinessTrip(OBusinessTrip oTrip) {

		if (oTrip == null) {
			return null;
		}

		BusinessTrip trip = new BusinessTrip();
		trip.setBusinessTripId(oTrip.getBusinessTripId());
		trip.setDomainId(oTrip.getDomainId());
		trip
				.setRevisionStatus(EnumUtils.forSerializedName(oTrip.getRevisionStatus(), BusinessTrip.RevisionStatus.class
				));
		trip.setExternalId(oTrip.getExternalId());
		trip.setDescription(oTrip.getDescription());

		return trip;
	}

	static OBusinessTrip createOBusinessTrip(BusinessTrip trip) {

		if (trip == null) {
			return null;
		}

		OBusinessTrip oTrip = new OBusinessTrip();
		oTrip.setBusinessTripId(trip.getBusinessTripId());
		oTrip.setDomainId(trip.getDomainId());
		oTrip.setRevisionStatus(EnumUtils.toSerializedName(trip.getRevisionStatus()));
		oTrip.setExternalId(trip.getExternalId());
		oTrip.setDescription(trip.getDescription());

		return oTrip;
	}
	
	static CompanyPicture createCompanyPicture(OCompanyPicture with) {
		return fillContactPicture(new CompanyPicture(), with);
	}
	
	static CompanyPicture fillContactPicture(CompanyPicture fill, OCompanyPicture with) {
		if ((fill != null) && (with != null)) {
			fill.setWidth(with.getWidth());
			fill.setHeight(with.getHeight());
			fill.setMediaType(with.getMediaType());
			fill.setBytes(with.getBytes());
		}
		return fill;
	}
	
	static DrmLineManager createLineManager(ODrmLineManager oLineManager) {

		if (oLineManager == null) {
			return null;
		}

		DrmLineManager manager = new DrmLineManager();
		manager.setDomainId(oLineManager.getDomainId());
		manager.setUserId(oLineManager.getUserId());

		return manager;
	}
	
	static ODrmLineManager createODrmLineManager(DrmLineManager manager) {

		if (manager == null) {
			return null;
		}

		ODrmLineManager oManager = new ODrmLineManager();
		oManager.setDomainId(manager.getDomainId());
		oManager.setUserId(manager.getUserId());

		return oManager;
	}
	
	static UserForManager createUserForManager(ODrmLineManagerUsers oLineManagerUser) {

		if (oLineManagerUser == null) {
			return null;
		}

		UserForManager userForManager = new UserForManager();
		userForManager.setDomainId(oLineManagerUser.getDomainId());
		userForManager.setManagerUserId(oLineManagerUser.getLineManagerUserId());
		userForManager.setUserId(oLineManagerUser.getUserId());

		return userForManager;
	}
	
	static ODrmLineManagerUsers createODrmLineManagerUser(UserForManager ufm) {

		if (ufm == null) {
			return null;
		}

		ODrmLineManagerUsers oLmu = new ODrmLineManagerUsers();
		oLmu.setDomainId(ufm.getDomainId());
		oLmu.setLineManagerUserId(ufm.getManagerUserId());
		oLmu.setUserId(ufm.getUserId());

		return oLmu;
	}
	
	static List<OTimetableReport> mergeEventByDate(List<OTimetableReport> trsf){
		
		HashMap<LocalDate, OTimetableReport> hashTr = new HashMap();
	
		for(OTimetableReport otr : trsf){
			if(hashTr.get(otr.getDate().toLocalDate()) == null){
				hashTr.put(otr.getDate().toLocalDate(), otr);
			}else{
				if(hashTr.get(otr.getDate().toLocalDate()).getOvertime() == null)
					hashTr.get(otr.getDate().toLocalDate()).setOvertime(otr.getOvertime());
				if(hashTr.get(otr.getDate().toLocalDate()).getPaidLeave() == null)
					hashTr.get(otr.getDate().toLocalDate()).setPaidLeave(otr.getPaidLeave());
				if(hashTr.get(otr.getDate().toLocalDate()).getUnpaidLeave() == null)
					hashTr.get(otr.getDate().toLocalDate()).setUnpaidLeave(otr.getUnpaidLeave());
				if(hashTr.get(otr.getDate().toLocalDate()).getHoliday() == null)
					hashTr.get(otr.getDate().toLocalDate()).setHoliday(otr.getHoliday());
				if(hashTr.get(otr.getDate().toLocalDate()).getMedicalVisit() == null)
					hashTr.get(otr.getDate().toLocalDate()).setMedicalVisit(otr.getMedicalVisit());
				if(hashTr.get(otr.getDate().toLocalDate()).getContractual() == null)
					hashTr.get(otr.getDate().toLocalDate()).setContractual(otr.getContractual());
				if(hashTr.get(otr.getDate().toLocalDate()).getSickness() == null)
					hashTr.get(otr.getDate().toLocalDate()).setSickness(otr.getSickness());
			}
		}
		
		return new ArrayList(hashTr.values());
	}
	
	static List<OTimetableReport> mergeWorkReportByDate(List<OTimetableReport> trsf){
		
		HashMap<LocalDate, OTimetableReport> hashTr = new HashMap();
	
		for(OTimetableReport otr : trsf){
			if(hashTr.get(otr.getDate().toLocalDate()) == null){
				hashTr.put(otr.getDate().toLocalDate(), otr);
			}else{
				if(hashTr.get(otr.getDate().toLocalDate()).getWorkReportHours() == null)
					hashTr.get(otr.getDate().toLocalDate()).setWorkReportHours(otr.getWorkReportHours());				
			}
		}
		
		return new ArrayList(hashTr.values());
	}
	
	static List<OTimetableReport> mergeJobByDate(List<OTimetableReport> trsf){
		
		HashMap<LocalDate, OTimetableReport> hashTr = new HashMap();
	
		for(OTimetableReport otr : trsf){
			if(hashTr.get(otr.getDate().toLocalDate()) == null){
				hashTr.put(otr.getDate().toLocalDate(), otr);
			}else{
				if(hashTr.get(otr.getDate().toLocalDate()).getWorkReportHours() == null)
					hashTr.get(otr.getDate().toLocalDate()).setWorkReportHours(otr.getWorkReportHours());				
			}
		}
		
		return new ArrayList(hashTr.values());
	}
	
	static List<OTimetableReport> mergeStampAndEventByDate(Connection con, List<OTimetableReport> trsf, Integer hourProfileId){
		Integer calculateWorkingHours = 0;
		Integer calculateOvertimeHours = 0;
		Integer calculateLeaveHours = 0;
		Integer workingHours = 0;
		Integer paidLeaveHours = 0;
		Integer unpaidLeaveHours = 0;
		Integer medicalVisitHours = 0;
		Integer overtimeHours = 0;
        Integer holidayHours = 0;
        Integer sicknessHours = 0;
		LineHourDAO lhDAO = LineHourDAO.getInstance();
		HashMap<LocalDate, OTimetableReport> hashTr = new HashMap();
		HolidayDateDAO hdDAO = HolidayDateDAO.getInstance();
		
		for(OTimetableReport otr : trsf){
			if(hashTr.get(otr.getDate().toLocalDate()) == null){
				hashTr.put(otr.getDate().toLocalDate(), otr);
			}else{
				if(hashTr.get(otr.getDate().toLocalDate()).getWorkingHours() == null)
					hashTr.get(otr.getDate().toLocalDate()).setWorkingHours(otr.getWorkingHours());
				if(hashTr.get(otr.getDate().toLocalDate()).getCausal() == null)
					hashTr.get(otr.getDate().toLocalDate()).setCausal(otr.getCausal());
				if(hashTr.get(otr.getDate().toLocalDate()).getHour() == null)
					hashTr.get(otr.getDate().toLocalDate()).setHour(otr.getHour());
				if(hashTr.get(otr.getDate().toLocalDate()).getDetail() == null)
					hashTr.get(otr.getDate().toLocalDate()).setDetail(otr.getDetail());
				if(hashTr.get(otr.getDate().toLocalDate()).getNote() == null)
					hashTr.get(otr.getDate().toLocalDate()).setNote(otr.getNote());
				if(hashTr.get(otr.getDate().toLocalDate()).getOvertime() == null)
					hashTr.get(otr.getDate().toLocalDate()).setOvertime(otr.getOvertime());
				if(hashTr.get(otr.getDate().toLocalDate()).getPaidLeave() == null)
					hashTr.get(otr.getDate().toLocalDate()).setPaidLeave(otr.getPaidLeave());
				if(hashTr.get(otr.getDate().toLocalDate()).getUnpaidLeave() == null)
					hashTr.get(otr.getDate().toLocalDate()).setUnpaidLeave(otr.getUnpaidLeave());
				if(hashTr.get(otr.getDate().toLocalDate()).getHoliday() == null)
					hashTr.get(otr.getDate().toLocalDate()).setHoliday(otr.getHoliday());
				if(hashTr.get(otr.getDate().toLocalDate()).getMedicalVisit() == null)
					hashTr.get(otr.getDate().toLocalDate()).setMedicalVisit(otr.getMedicalVisit());
				if(hashTr.get(otr.getDate().toLocalDate()).getContractual() == null)
					hashTr.get(otr.getDate().toLocalDate()).setContractual(otr.getContractual());
				if(hashTr.get(otr.getDate().toLocalDate()).getWorkReportHours() == null)
					hashTr.get(otr.getDate().toLocalDate()).setWorkReportHours(otr.getWorkReportHours());	
				if(hashTr.get(otr.getDate().toLocalDate()).getJobHours() == null)
					hashTr.get(otr.getDate().toLocalDate()).setJobHours(otr.getJobHours());	
				if(hashTr.get(otr.getDate().toLocalDate()).getSickness() == null)
					hashTr.get(otr.getDate().toLocalDate()).setSickness(otr.getSickness());
			}
		}
		
		//Add empty days into HashMaps
		if(!trsf.isEmpty()){
			List<DateTime> dates = new ArrayList();
			for(int i = trsf.get(0).getDate().dayOfMonth().getMinimumValue(); i <= trsf.get(0).getDate().dayOfMonth().getMaximumValue(); i++){
				dates.add(trsf.get(0).getDate().withDayOfMonth(i));
			}

			for(DateTime dt : dates){
				OHolidayDate oHD = hdDAO.selectByDomainDate(con, trsf.get(0).getDomainId(), dt);
				
				if(hashTr.get(dt.toLocalDate()) == null){
					OTimetableReport temp = new OTimetableReport();
					temp.setDomainId(trsf.get(0).getDomainId());
					temp.setCompanyId(trsf.get(0).getCompanyId());
					temp.setUserId(trsf.get(0).getUserId());
					temp.setDate(dt);																						
					
					hashTr.put(dt.toLocalDate(), temp);
					
					if (oHD != null)
						hashTr.get(dt.toLocalDate()).setNote((oHD.getDescription() == null) ? "" : oHD.getDescription());
						
				} else {										
					String[] hourMin = null;
					int hour = 0;
					int mins = 0;
					workingHours = 0;
					paidLeaveHours = 0;
					overtimeHours = 0;
					medicalVisitHours = 0;
					unpaidLeaveHours = 0;
                    holidayHours = 0;
                    sicknessHours = 0;
                    
					if (hashTr.get(dt.toLocalDate()).getWorkingHours() != null) {
						hourMin = hashTr.get(dt.toLocalDate()).getWorkingHours().split("\\.");
						hour = Integer.parseInt(hourMin[0]);
						mins = (hourMin.length > 1) ? Integer.parseInt(hourMin[1]) : 0;
						workingHours = (hour * 60) + mins;
					}
					if (hashTr.get(dt.toLocalDate()).getWorkReportHours() != null) {
						hourMin = hashTr.get(dt.toLocalDate()).getWorkReportHours().split("\\.");
						hour = Integer.parseInt(hourMin[0]);
						mins = (hourMin.length > 1) ? Integer.parseInt(hourMin[1]) : 0;
						workingHours += (hour * 60) + mins;
					}
					if (hashTr.get(dt.toLocalDate()).getJobHours() != null) {
						hourMin = hashTr.get(dt.toLocalDate()).getJobHours().split("\\.");
						hour = Integer.parseInt(hourMin[0]);
						mins = (hourMin.length > 1) ? Integer.parseInt(hourMin[1]) : 0;
						workingHours += (hour * 60) + mins;
					}					
					if (hashTr.get(dt.toLocalDate()).getPaidLeave()!= null) {
						hourMin = hashTr.get(dt.toLocalDate()).getPaidLeave().split("\\.");
						hour = Integer.parseInt(hourMin[0]);
						mins = (hourMin.length > 1) ? Integer.parseInt(hourMin[1]) : 0;
						paidLeaveHours = (hour * 60) + mins;
					}
					if (hashTr.get(dt.toLocalDate()).getOvertime()!= null) {
						hourMin = hashTr.get(dt.toLocalDate()).getOvertime().split("\\.");
						hour = Integer.parseInt(hourMin[0]);
						mins = (hourMin.length > 1) ? Integer.parseInt(hourMin[1]) : 0;
						overtimeHours = (hour * 60) + mins;
					}	
					if (hashTr.get(dt.toLocalDate()).getMedicalVisit()!= null) {
						hourMin = hashTr.get(dt.toLocalDate()).getMedicalVisit().split("\\.");
						hour = Integer.parseInt(hourMin[0]);
						mins = (hourMin.length > 1) ? Integer.parseInt(hourMin[1]) : 0;
						medicalVisitHours = (hour * 60) + mins;
					}	
					if (hashTr.get(dt.toLocalDate()).getUnpaidLeave()!= null) {
						hourMin = hashTr.get(dt.toLocalDate()).getUnpaidLeave().split("\\.");
						hour = Integer.parseInt(hourMin[0]);
						mins = (hourMin.length > 1) ? Integer.parseInt(hourMin[1]) : 0;
						unpaidLeaveHours = (hour * 60) + mins;
					}	
					if (hashTr.get(dt.toLocalDate()).getHoliday()!= null) {
						hourMin = hashTr.get(dt.toLocalDate()).getHoliday().split("\\.");
						hour = Integer.parseInt(hourMin[0]);
						mins = (hourMin.length > 1) ? Integer.parseInt(hourMin[1]) : 0;
						holidayHours = (hour * 60) + mins;
					}	
					if (hashTr.get(dt.toLocalDate()).getSickness()!= null) {
						hourMin = hashTr.get(dt.toLocalDate()).getSickness().split("\\.");
						hour = Integer.parseInt(hourMin[0]);
						mins = (hourMin.length > 1) ? Integer.parseInt(hourMin[1]) : 0;
						sicknessHours = (hour * 60) + mins;
					}
                    
					// qui dovrei prendermi se ha un profilo le ore di lavoro del giorno, se non ha profilo al momento non faccio nulla
					String profileHour = lhDAO.selectSumLineHourByHourProfileIdDayOfWeek(con, hourProfileId, hashTr.get(dt.toLocalDate()).getDate().toLocalDate().dayOfWeek().get());
					// ora devo calcolarmi il calculateWorkingHours - profileHour
					int profileHours = (profileHour != null) ? Integer.valueOf(profileHour) : 0;
						
					calculateWorkingHours = 0;
					calculateOvertimeHours = 0;
					calculateLeaveHours = 0;
                    
                    /*
					if (profileHours <= workingHours) {
						calculateWorkingHours = profileHours;
						calculateOvertimeHours = (workingHours - (profileHours + overtimeHours)) + overtimeHours;
						calculateLeaveHours = 0 + paidLeaveHours;
					} else if (workingHours != 0) {
						calculateWorkingHours = workingHours;
						calculateOvertimeHours = 0 + overtimeHours;
						if ((workingHours + paidLeaveHours + unpaidLeaveHours + medicalVisitHours) >= profileHours){
							calculateLeaveHours = paidLeaveHours;
						} else {
							calculateLeaveHours = (profileHours - (workingHours + paidLeaveHours + unpaidLeaveHours + medicalVisitHours)) + paidLeaveHours;
						}
					} else {
						calculateWorkingHours = 0;
						calculateOvertimeHours = overtimeHours;
						calculateLeaveHours = paidLeaveHours;
					}																	
                    */

//					VASI - Commentato in data 04/03/2022 per inserire nel timetable report i dati puliti senza calcoli

// 					if (profileHours <= workingHours) {
//						calculateWorkingHours = profileHours;
//						calculateOvertimeHours = (workingHours - (profileHours + overtimeHours)) + overtimeHours;
//						calculateLeaveHours = 0 + paidLeaveHours;
//					} else {
//						calculateWorkingHours = workingHours;
//						calculateOvertimeHours = 0 + overtimeHours;
//						if ((workingHours + paidLeaveHours + unpaidLeaveHours + medicalVisitHours + holidayHours + sicknessHours) >= profileHours){
//							calculateLeaveHours = paidLeaveHours;
//						} else {
//							calculateLeaveHours = (profileHours - (workingHours + paidLeaveHours + unpaidLeaveHours + medicalVisitHours + holidayHours + sicknessHours)) + paidLeaveHours;
//						}
//					}	
//                    
//					if (oHD != null) {							
//						calculateOvertimeHours += calculateWorkingHours;
//						calculateWorkingHours = 0;
//						hashTr.get(dt.toLocalDate()).setWorkingHours(null);
//						calculateLeaveHours = 0;
//						hashTr.get(dt.toLocalDate()).setPaidLeave(null);
//						hashTr.get(dt.toLocalDate()).setNote((oHD.getDescription() == null) ? "" : oHD.getDescription());
//					}						
//					if (calculateWorkingHours > 0) {
//						int h = calculateWorkingHours / 60;
//						int m = calculateWorkingHours % 60;
//						hashTr.get(dt.toLocalDate()).setWorkingHours(String.format("%d.%02d", h, m));
//					} else {
//						hashTr.get(dt.toLocalDate()).setWorkingHours(null);
//					}
//					if (calculateLeaveHours > 0) {
//						int h = calculateLeaveHours / 60;
//						int m = calculateLeaveHours % 60;
//						hashTr.get(dt.toLocalDate()).setPaidLeave(String.format("%d.%02d", h, m));	
//					} else {
//						hashTr.get(dt.toLocalDate()).setPaidLeave(null);
//					}
//					if (calculateOvertimeHours > 0) {
//						int h = calculateOvertimeHours / 60;
//						int m = calculateOvertimeHours % 60;
//						hashTr.get(dt.toLocalDate()).setOvertime(String.format("%d.%02d", h, m));	
//					} else {
//						hashTr.get(dt.toLocalDate()).setOvertime(null);
//					}
					
				}
				
			}
		}
		
		return new ArrayList(hashTr.values());
	}
	
	static BigDecimal round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_DOWN);       
		
        return bd;
    }
	
	static List<OTimetableReport> createOTimetableReport(List<OTimetableEvent> tel){
		List<OTimetableReport> trl = new ArrayList();
		OTimetableReport tr = null;
		String[] hourMin = null;
		
		for(OTimetableEvent te : tel){
			tr = new OTimetableReport();
			
			tr.setDomainId(te.getDomainId());
			tr.setCompanyId(te.getCompanyId());
			tr.setUserId(te.getUserId());
			tr.setDate(te.getDate().toDateTimeAtStartOfDay());
			
			OLeaveRequestType requestType = EnumUtils.forSerializedName(te.getType(), OLeaveRequestType.class);
			
			hourMin = te.getHour().split("\\.");
			int h = Integer.parseInt(hourMin[0]);
			int m = (hourMin.length > 1) ? Integer.parseInt(hourMin[1]) : 0;
			
			if(OLeaveRequestType.HOLIDAY.equals(requestType)){
				tr.setHoliday(String.format("%d.%02d", h, m));
			}else if(OLeaveRequestType.PAID_LEAVE.equals(requestType)){
				tr.setPaidLeave(String.format("%d.%02d", h, m));
			}else if(OLeaveRequestType.UNPAID_LEAVE.equals(requestType)){
				tr.setUnpaidLeave(String.format("%d.%02d", h, m));
			}else if(OLeaveRequestType.CONTRACTUAL.equals(requestType)){
				tr.setContractual(String.format("%d.%02d", h, m));
			}else if(OLeaveRequestType.MEDICAL_VISIT.equals(requestType)){
				tr.setMedicalVisit(String.format("%d.%02d", h, m));
			}else if(OLeaveRequestType.OVERTIME.equals(requestType)){
				tr.setOvertime(String.format("%d.%02d", h, m));
			}else if(OLeaveRequestType.SICKNESS.equals(requestType)){
				tr.setSickness(String.format("%d.%02d", h, m));
			}
			
			trl.add(tr);
		}
		
		return trl;
	}
	
	static List<OTimetableReport> createOTimetableReportByWorkReport(List<OWorkReport> tel){
		List<OTimetableReport> trl = new ArrayList();
		OTimetableReport tr = null;
		
		for(OWorkReport te : tel){
			tr = new OTimetableReport();
			
			tr.setDomainId(te.getDomainId());
			tr.setCompanyId(te.getCompanyId());
			tr.setUserId(te.getOperatorId());
			tr.setDate(te.getFromDate().toDateTimeAtStartOfDay());
			
			tr.setWorkReportHours(String.format("%d.%02d", (te.getTimetableHours() / 60), (te.getTimetableHours() % 60)));
			
			trl.add(tr);
		}
		
		return trl;
	}
	
	static List<OTimetableReport> createOTimetableReportByJob(List<OJob> tel){
		List<OTimetableReport> trl = new ArrayList();
		OTimetableReport tr = null;
			
		for(OJob te : tel){
			tr = new OTimetableReport();
			
			tr.setDomainId(te.getDomainId());
			tr.setCompanyId(te.getCompanyId());
			tr.setUserId(te.getOperatorId());
			tr.setDate(te.getStartDate().toLocalDate().toDateTimeAtStartOfDay());
			
			tr.setJobHours(String.format("%d.%02d", (te.getJobHours() / 60), (te.getJobHours() % 60)));
			
			trl.add(tr);
		}
		
		return trl;
	}
	
	static OTimetableReport createOTimetableReport(TimetableReport tr) {
		if (tr == null) {
			return null;
		}
		OTimetableReport oTr = new OTimetableReport();
		oTr.setId(tr.getId());
		oTr.setWorkingHours(tr.getWorkingHours());
		oTr.setOvertime(tr.getOvertime());
		oTr.setPaidLeave(tr.getPaidLeave());
		oTr.setUnpaidLeave(tr.getUnpaidLeave());
		oTr.setHoliday(tr.getHoliday());
		oTr.setMedicalVisit(tr.getMedicalVisit());
		oTr.setContractual(tr.getContractual());
		oTr.setCausal(tr.getCausal());
		oTr.setHour(tr.getHour());
		oTr.setDetail(tr.getDetail());
		oTr.setNote(tr.getNote());
		oTr.setSickness(tr.getSickness());
		oTr.setOther(tr.getOther());
		oTr.setCausalId(tr.getCausalId());
		
		return oTr;
	}
	
	static ExpenseNote createExpenseNote(OExpenseNote oEn) {
		if (oEn == null) {
			return null;
		}
		ExpenseNote eN = new ExpenseNote();
		eN.setId(oEn.getId());
		eN.setDomainId(oEn.getDomainId());
		eN.setOperatorId(oEn.getOperatorId());
		eN.setCompanyId(oEn.getCompanyId());
		eN.setFromDate(oEn.getFromDate());
		eN.setToDate(oEn.getToDate());
		eN.setTotCurrency(oEn.getTotCurrency());
		eN.setCurrency(oEn.getCurrency());
		eN.setDescription(oEn.getDescription());
		eN.setStatus(oEn.getStatus());

		return eN;
	}

	static OExpenseNote createOExpenseNote(ExpenseNote eN) {
		if (eN == null) {
			return null;
		}
		OExpenseNote oEn = new OExpenseNote();
		oEn.setId(eN.getId());
		oEn.setDomainId(eN.getDomainId());
		oEn.setOperatorId(eN.getOperatorId());
		oEn.setCompanyId(eN.getCompanyId());
		oEn.setFromDate(eN.getFromDate());
		oEn.setToDate(eN.getToDate());
		oEn.setTotCurrency(eN.getTotCurrency());
		oEn.setCurrency(eN.getCurrency());
		oEn.setDescription(eN.getDescription());
		oEn.setStatus(eN.getStatus());

		return oEn;
	}
	
	static ExpenseNoteDetail createExpenseNoteDetail(OExpenseNoteDetail oEnD) {

		if (oEnD == null) {
			return null;
		}

		ExpenseNoteDetail eND = new ExpenseNoteDetail();
		eND.setId(oEnD.getId());
		eND.setExpenseNoteId(oEnD.getExpenseNoteId());
		eND.setOperatorId(oEnD.getOperatorId());
		eND.setCompanyId(oEnD.getCompanyId());
		eND.setTypeId(oEnD.getTypeId());
		eND.setTotal(oEnD.getTotal());
		eND.setDate(oEnD.getDate());
		eND.setPaymentCompany(oEnD.getPaymentCompany());
		eND.setInvoice(oEnD.getInvoice());
		eND.setInvoiceNumber(oEnD.getInvoiceNumber());
		eND.setWithOthers(oEnD.getWithOthers());
		eND.setCustomerId(oEnD.getCustomerId());
		eND.setKm(oEnD.getKm());
		eND.setCurrency(oEnD.getCurrency());
		eND.setChange(oEnD.getChange());
		eND.setDescription(oEnD.getDescription());
		eND.setTotalDoc(oEnD.getTotalDoc());
		eND.setCurrencyDoc(oEnD.getCurrencyDoc());

		return eND;
	}
	
	static OExpenseNoteDetail createOExpenseNoteDetail(ExpenseNoteDetail EnD) {

		if (EnD == null) {
			return null;
		}

		OExpenseNoteDetail oEnD = new OExpenseNoteDetail();
		oEnD.setId(EnD.getId());
		oEnD.setExpenseNoteId(EnD.getExpenseNoteId());
		oEnD.setOperatorId(EnD.getOperatorId());
		oEnD.setCompanyId(EnD.getCompanyId());
		oEnD.setTypeId(EnD.getTypeId());
		oEnD.setTotal(EnD.getTotal());
		oEnD.setDate(EnD.getDate());
		oEnD.setPaymentCompany(EnD.getPaymentCompany());
		oEnD.setInvoice(EnD.getInvoice());
		oEnD.setInvoiceNumber(EnD.getInvoiceNumber());
		oEnD.setWithOthers(EnD.getWithOthers());
		oEnD.setCustomerId(EnD.getCustomerId());
		oEnD.setKm(EnD.getKm());
		oEnD.setCurrency(EnD.getCurrency());
		oEnD.setChange(EnD.getChange());
		oEnD.setDescription(EnD.getDescription());
		oEnD.setTotalDoc(EnD.getTotalDoc());
		oEnD.setCurrencyDoc(EnD.getCurrencyDoc());

		return oEnD;
	}
	
	static OExpenseNoteDocument doExpenseNoteDocumentInsert(Connection con, Integer id, ExpenseNoteDocumentWithStream document) throws DAOException, IOException {
		ExpenseNoteDocumentDAO docDao = ExpenseNoteDocumentDAO.getInstance();
		
		OExpenseNoteDocument doc = createOExpenseNoteDocument(document);
		doc.setId(IdentifierUtils.getUUIDTimeBased());
		doc.setExpenseNoteId(id);
		docDao.insert(con, doc, BaseDAO.createRevisionTimestamp());
		
		InputStream is = document.getStream();
		try {
			docDao.insertBytes(con, doc.getId(), IOUtils.toByteArray(is));
		} finally {
			IOUtils.closeQuietly(is);
		}
		
		return doc;
	}
	
	static OExpenseNoteDocument createOExpenseNoteDocument(ExpenseNoteDocument src) {
		if (src == null) return null;
		return fillOExpenseNoteDocument(new OExpenseNoteDocument(), src);
	}
	
	static ExpenseNoteDocument createExpenseNoteDocument(OExpenseNoteDocument src) {
		if (src == null) return null;
		return fillExpenseNoteDocument(new ExpenseNoteDocument(), src);
	}
	
	static <T extends OExpenseNoteDocument> T fillOExpenseNoteDocument(T tgt, ExpenseNoteDocument src) {
		if ((tgt != null) && (src != null)) {
			tgt.setId(src.getId());
			tgt.setRevisionTimestamp(src.getRevisionTimestamp());
			tgt.setRevisionSequence(src.getRevisionSequence());
			tgt.setFilename(src.getFileName());
			tgt.setSize(src.getSize());
			tgt.setMediaType(src.getMediaType());
		}
		return tgt;
	}
	
	static <T extends ExpenseNoteDocument> T fillExpenseNoteDocument(T tgt, OExpenseNoteDocument src) {
		if ((tgt != null) && (src != null)) {
			tgt.setId(src.getId());
			tgt.setRevisionTimestamp(src.getRevisionTimestamp());
			tgt.setRevisionSequence(src.getRevisionSequence());
			tgt.setFileName(src.getFilename());
			tgt.setSize(src.getSize());
			tgt.setMediaType(src.getMediaType());
		}
		return tgt;
	}
	
	static List<ExpenseNoteDocument> createExpenseNoteDocumentList(List<OExpenseNoteDocument> items) {
		ArrayList<ExpenseNoteDocument> list = new ArrayList<>(items.size());
		for (OExpenseNoteDocument item : items) {
			list.add(createExpenseNoteDocument(item));
		}
		return list;
	}
	
	static boolean doExpenseNoteDocumentUpdate(Connection con, ExpenseNoteDocumentWithStream document) throws DAOException, IOException {
		ExpenseNoteDocumentDAO docDao = ExpenseNoteDocumentDAO.getInstance();
		
		OExpenseNoteDocument doc = createOExpenseNoteDocument(document);
		docDao.update(con, doc, BaseDAO.createRevisionTimestamp());
		
		InputStream is = document.getStream();
		try {
			docDao.deleteBytesById(con, doc.getId());
			return docDao.insertBytes(con, doc.getId(), IOUtils.toByteArray(is)) == 1;
		} finally {
			IOUtils.closeQuietly(is);
		}
	}
	
	static OExpenseNoteDetailDocument doExpenseNoteDetailDocumentInsert(Connection con, Integer id, ExpenseNoteDetailDocumentWithStream document) throws DAOException, IOException {
		ExpenseNoteDetailDocumentDAO docDao = ExpenseNoteDetailDocumentDAO.getInstance();
		
		OExpenseNoteDetailDocument doc = createOExpenseNoteDetailDocument(document);
		doc.setId(IdentifierUtils.getUUIDTimeBased());
		doc.setExpenseNoteDetailId(id);
		docDao.insert(con, doc, BaseDAO.createRevisionTimestamp());
		
		InputStream is = document.getStream();
		try {
			docDao.insertBytes(con, doc.getId(), IOUtils.toByteArray(is));
		} finally {
			IOUtils.closeQuietly(is);
		}
		
		return doc;
	}
	
	static OExpenseNoteDetailDocument createOExpenseNoteDetailDocument(ExpenseNoteDetailDocument src) {
		if (src == null) return null;
		return fillOExpenseNoteDetailDocument(new OExpenseNoteDetailDocument(), src);
	}
	
	static ExpenseNoteDetailDocument createExpenseNoteDetailDocument(OExpenseNoteDetailDocument src) {
		if (src == null) return null;
		return fillExpenseNoteDetailDocument(new ExpenseNoteDetailDocument(), src);
	}
	
	static <T extends OExpenseNoteDetailDocument> T fillOExpenseNoteDetailDocument(T tgt, ExpenseNoteDetailDocument src) {
		if ((tgt != null) && (src != null)) {
			tgt.setId(src.getId());
			tgt.setRevisionTimestamp(src.getRevisionTimestamp());
			tgt.setRevisionSequence(src.getRevisionSequence());
			tgt.setFilename(src.getFileName());
			tgt.setSize(src.getSize());
			tgt.setMediaType(src.getMediaType());
		}
		return tgt;
	}
	
	static <T extends ExpenseNoteDetailDocument> T fillExpenseNoteDetailDocument(T tgt, OExpenseNoteDetailDocument src) {
		if ((tgt != null) && (src != null)) {
			tgt.setId(src.getId());
			tgt.setRevisionTimestamp(src.getRevisionTimestamp());
			tgt.setRevisionSequence(src.getRevisionSequence());
			tgt.setFileName(src.getFilename());
			tgt.setSize(src.getSize());
			tgt.setMediaType(src.getMediaType());
		}
		return tgt;
	}
	
	static List<ExpenseNoteDetailDocument> createExpenseNoteDetailDocumentList(List<OExpenseNoteDetailDocument> items) {
		ArrayList<ExpenseNoteDetailDocument> list = new ArrayList<>(items.size());
		for (OExpenseNoteDetailDocument item : items) {
			list.add(createExpenseNoteDetailDocument(item));
		}
		return list;
	}
	
	static boolean doExpenseNoteDetailDocumentUpdate(Connection con, ExpenseNoteDetailDocumentWithStream document) throws DAOException, IOException {
		ExpenseNoteDetailDocumentDAO docDao = ExpenseNoteDetailDocumentDAO.getInstance();
		
		OExpenseNoteDetailDocument doc = createOExpenseNoteDetailDocument(document);
		docDao.update(con, doc, BaseDAO.createRevisionTimestamp());
		
		InputStream is = document.getStream();
		try {
			docDao.deleteBytesById(con, doc.getId());
			return docDao.insertBytes(con, doc.getId(), IOUtils.toByteArray(is)) == 1;
		} finally {
			IOUtils.closeQuietly(is);
		}
	}
	
	static TicketCategory createTicketCategory(OTicketCategory oTcktCategory) {

		if (oTcktCategory == null) {
			return null;
		}

		TicketCategory tcktCategory = new TicketCategory();
		tcktCategory.setTicketCategoryId(oTcktCategory.getTicketCategoryId());
		tcktCategory.setDomainId(oTcktCategory.getDomainId());
		tcktCategory
				.setRevisionStatus(EnumUtils.forSerializedName(oTcktCategory.getRevisionStatus(), TicketCategory.RevisionStatus.class
				));
		tcktCategory.setExternalId(oTcktCategory.getExternalId());
		tcktCategory.setDescription(oTcktCategory.getDescription());

		return tcktCategory;
	}

	static OTicketCategory createOTicketCategory(TicketCategory tcktCategory) {

		if (tcktCategory == null) {
			return null;
		}

		OTicketCategory oTcktCategory = new OTicketCategory();
		oTcktCategory.setTicketCategoryId(tcktCategory.getTicketCategoryId());
		oTcktCategory.setDomainId(tcktCategory.getDomainId());
		oTcktCategory.setRevisionStatus(EnumUtils.toSerializedName(tcktCategory.getRevisionStatus()));
		oTcktCategory.setExternalId(tcktCategory.getExternalId());
		oTcktCategory.setDescription(tcktCategory.getDescription());

		return oTcktCategory;
	}
	
	static Ticket createTicket(OTicket oTckt) {
		if (oTckt == null) {
			return null;
		}
		Ticket tckt = new Ticket();
		tckt.setTicketId(oTckt.getTicketId());
		tckt.setCompanyId(oTckt.getCompanyId());
		tckt.setFromOperatorId(oTckt.getFromOperatorId());
		tckt.setToOperatorId(oTckt.getToOperatorId());
		tckt.setCustomerId(oTckt.getCustomerId());
		tckt.setCustomerStatId(oTckt.getCustomerStatId());
		tckt.setTimezone(oTckt.getTimezone());
		tckt.setDate(oTckt.getDate());
		tckt.setTicketCategoryId(oTckt.getTicketCategoryId());
		tckt.setPriorityId(oTckt.getPriorityId());
		tckt.setStatusId(oTckt.getStatusId());
		tckt.setTitle(oTckt.getTitle());
		tckt.setDescription(oTckt.getDescription());
		tckt.setRelease(oTckt.getRelease());
		tckt.setEnvironment(oTckt.getEnvironment());
		tckt.setSuggestion(oTckt.getSuggestion());
		tckt.setSimulation(oTckt.getSimulation());
		tckt.setEventId(oTckt.getEventId());		
		tckt.setDomainId(oTckt.getDomainId());
		tckt.setNumber(oTckt.getNumber());
		
		return tckt;
	}
	
	static OTicket createOTicket(Ticket tckt) {
		if (tckt == null) {
			return null;
		}
		OTicket oTckt = new OTicket();
		oTckt.setTicketId(tckt.getTicketId());
		oTckt.setCompanyId(tckt.getCompanyId());
		oTckt.setFromOperatorId(tckt.getFromOperatorId());
		oTckt.setToOperatorId(tckt.getToOperatorId());
		oTckt.setCustomerId(tckt.getCustomerId());
		oTckt.setCustomerStatId(tckt.getCustomerStatId());
		oTckt.setDate(tckt.getDate());
		oTckt.setTimezone(tckt.getTimezone());
		oTckt.setTicketCategoryId(tckt.getTicketCategoryId());
		oTckt.setPriorityId(tckt.getPriorityId());
		oTckt.setStatusId(tckt.getStatusId());
		oTckt.setTitle(tckt.getTitle());
		oTckt.setDescription(tckt.getDescription());
		oTckt.setRelease(tckt.getRelease());
		oTckt.setEnvironment(tckt.getEnvironment());
		oTckt.setSimulation(tckt.getSimulation());
		oTckt.setSuggestion(tckt.getSuggestion());
		oTckt.setEventId(tckt.getEventId());
		oTckt.setDomainId(tckt.getDomainId());
		oTckt.setNumber(tckt.getNumber());
		
		return oTckt;
	}
	
	static TicketAttachment createTicketAttachment(OTicketAttachment src) {
		if (src == null) return null;
		return fillTicketAttachment(new TicketAttachment(), src);
	}
	
	static <T extends TicketAttachment> T fillTicketAttachment(T tgt, OTicketAttachment src) {
		if ((tgt != null) && (src != null)) {
			tgt.setTicketAttachmentId(src.getTicketAttachmentId());
			tgt.setRevisionTimestamp(src.getRevisionTimestamp());
			tgt.setRevisionSequence(src.getRevisionSequence());
			tgt.setFileName(src.getFilename());
			tgt.setSize(src.getSize());
			tgt.setMediaType(src.getMediaType());
		}
		return tgt;
	}
	
	static OTicketAttachment doTicketAttachmentInsert(Connection con, String wrId, TicketAttachmentWithStream attachment) throws DAOException, IOException {
		TicketAttachmentDAO attDao = TicketAttachmentDAO.getInstance();
		
		OTicketAttachment oatt = createOTicketAttachment(attachment);
		oatt.setTicketAttachmentId(IdentifierUtils.getUUIDTimeBased());
		oatt.setTicketId(wrId);
		attDao.insert(con, oatt, BaseDAO.createRevisionTimestamp());
		
		InputStream is = attachment.getStream();
		try {
			attDao.insertBytes(con, oatt.getTicketAttachmentId(), IOUtils.toByteArray(is));
		} finally {
			IOUtils.closeQuietly(is);
		}
		
		return oatt;
	}
	
	static OTicketAttachment createOTicketAttachment(TicketAttachment src) {
		if (src == null) return null;
		return fillOTicketAttachment(new OTicketAttachment(), src);
	}
	
	static <T extends OTicketAttachment> T fillOTicketAttachment(T tgt, TicketAttachment src) {
		if ((tgt != null) && (src != null)) {
			tgt.setTicketAttachmentId(src.getTicketAttachmentId());
			tgt.setRevisionTimestamp(src.getRevisionTimestamp());
			tgt.setRevisionSequence(src.getRevisionSequence());
			tgt.setFilename(src.getFileName());
			tgt.setSize(src.getSize());
			tgt.setMediaType(src.getMediaType());
		}
		return tgt;
	}
	
	static List<TicketAttachment> createTicketAttachmentList(List<OTicketAttachment> items) {
		ArrayList<TicketAttachment> list = new ArrayList<>(items.size());
		for (OTicketAttachment item : items) {
			list.add(createTicketAttachment(item));
		}
		return list;
	}
	
	static boolean doTicketAttachmentUpdate(Connection con, TicketAttachmentWithStream attachment) throws DAOException, IOException {
		TicketAttachmentDAO attDao = TicketAttachmentDAO.getInstance();
		
		OTicketAttachment oatt = createOTicketAttachment(attachment);
		attDao.update(con, oatt, BaseDAO.createRevisionTimestamp());
		
		InputStream is = attachment.getStream();
		try {
			attDao.deleteBytesById(con, oatt.getTicketAttachmentId());
			return attDao.insertBytes(con, oatt.getTicketAttachmentId(), IOUtils.toByteArray(is)) == 1;
		} finally {
			IOUtils.closeQuietly(is);
		}
	}
	
	static OActivity createOActivity(Activity act) {
		if (act == null) {
			return null;
		}
		OActivity oAct = new OActivity();
		oAct.setActivityId(act.getActivityId());
		oAct.setExternalId(act.getExternalId());
		oAct.setDescription(act.getDescription());
		oAct.setDomainId(act.getDomainId());
		oAct.setCustomer(act.getCustomer());
		oAct.setTimetable(act.getTimetable());
		oAct.setJobs(act.getJobs());
		oAct.setOpportunities(act.getOpportunities());
		oAct.setExportable(act.getExportable());

		return oAct;
	}

	static Activity createActivity(OActivity oAct) {
		if (oAct == null) {
			return null;
		}
		Activity act = new Activity();
		act.setActivityId(oAct.getActivityId());
		act.setExternalId(oAct.getExternalId());
		act.setDescription(oAct.getDescription());
		act.setDomainId(oAct.getDomainId());
		act.setCustomer(oAct.getCustomer());
		act.setTimetable(oAct.getTimetable());
		act.setJobs(oAct.getJobs());
		act.setOpportunities(oAct.getOpportunities());
		act.setExportable(oAct.getExportable());

		return act;
	}

	static ActivityGroupAssociation createActivityGroup(OActivityGroup oActGroup) {
		if (oActGroup == null) {
			return null;
		}

		ActivityGroupAssociation actGroup = new ActivityGroupAssociation();
		actGroup.setAssociationId(oActGroup.getAssociationId());
		actGroup.setActivityId(oActGroup.getActivityId());
		actGroup.setGroupId(oActGroup.getGroupId());

		return actGroup;
	}

	static OActivityGroup createOActivityGroup(ActivityGroupAssociation actGroup) {
		if (actGroup == null) {
			return null;
		}

		OActivityGroup oActGroup = new OActivityGroup();
		oActGroup.setAssociationId(actGroup.getAssociationId());
		oActGroup.setActivityId(actGroup.getActivityId());
		oActGroup.setGroupId(actGroup.getGroupId());

		return oActGroup;
	}
    
    static ViewTicket createViewTicket(OViewTicket oTckt) {
		if (oTckt == null) {
			return null;
		}
		ViewTicket tckt = new ViewTicket();
		tckt.setTicketId(oTckt.getTicketId());
		tckt.setCompanyId(oTckt.getCompanyId());
        tckt.setCompanyDescription(oTckt.getCompanyDescription());
		tckt.setFromOperatorId(oTckt.getFromOperatorId());
        tckt.setFromOperatorDescription(oTckt.getFromOperatorDescription());
		tckt.setToOperatorId(oTckt.getToOperatorId());
        tckt.setToOperatorDescription(oTckt.getToOperatorDescription());
		tckt.setCustomerId(oTckt.getCustomerId());
        tckt.setCustomerDescription(oTckt.getCustomerDescription());
		tckt.setCustomerStatId(oTckt.getCustomerStatId());
        tckt.setCustomerStatDescription(oTckt.getCustomerStatDescription());
		tckt.setTimezone(oTckt.getTimezone());
		tckt.setDate(oTckt.getDate());
		tckt.setTicketCategoryId(oTckt.getTicketCategoryId());
        tckt.setTicketCategoryDescription(oTckt.getTicketCategoryDescription());
		tckt.setPriorityId(oTckt.getPriorityId());
		tckt.setStatusId(oTckt.getStatusId());
        tckt.setStatusDescription(oTckt.getStatusDescription());
		tckt.setTitle(oTckt.getTitle());
		tckt.setNumber(oTckt.getNumber());
		
		return tckt;
	}
    
    static ViewJob createViewJob(OViewJob oJb) {
		if (oJb == null) {
			return null;
		}
		ViewJob jb = new ViewJob();
		jb.setJobId(oJb.getJobId());
		jb.setCompanyId(oJb.getCompanyId());
        jb.setCompanyDescription(oJb.getCompanyDescription());
		jb.setOperatorId(oJb.getOperatorId());
        jb.setOperatorDescription(oJb.getOperatorDescription());
		jb.setCustomerId(oJb.getCustomerId());
        jb.setCustomerDescription(oJb.getCustomerDescription());
		jb.setCustomerStatId(oJb.getCustomerStatId());
        jb.setCustomerStatDescription(oJb.getCustomerStatDescription());
		jb.setTimezone(oJb.getTimezone());
		jb.setStartDate(oJb.getStartDate());
        jb.setEndDate(oJb.getEndDate());
		jb.setActivityId(oJb.getActivityId());
        jb.setActivityDescription(oJb.getActivityDescription());
		jb.setCausalId(oJb.getCausalId());
        jb.setCausalDescription(oJb.getCausalDescription());
		jb.setTitle(oJb.getTitle());
		jb.setNumber(oJb.getNumber());
		
		return jb;
	}
}
