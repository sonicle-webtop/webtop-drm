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
import com.sonicle.webtop.drm.bol.OBusinessTrip;
import com.sonicle.webtop.drm.bol.OCompany;
import com.sonicle.webtop.drm.bol.OCompanyPicture;
import com.sonicle.webtop.drm.bol.OCompanyUser;
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
import com.sonicle.webtop.drm.bol.OHolidayDate;
import com.sonicle.webtop.drm.bol.OHourProfile;
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
import com.sonicle.webtop.drm.bol.OTimetableEvent;
import com.sonicle.webtop.drm.bol.OTimetableReport;
import com.sonicle.webtop.drm.bol.OTimetableSetting;
import com.sonicle.webtop.drm.bol.OTimetableStamp;
import com.sonicle.webtop.drm.bol.OWorkReport;
import com.sonicle.webtop.drm.bol.OWorkReportAttachment;
import com.sonicle.webtop.drm.bol.OWorkReportRow;
import com.sonicle.webtop.drm.bol.OWorkReportSetting;
import com.sonicle.webtop.drm.bol.OWorkType;
import com.sonicle.webtop.drm.dal.LeaveRequestDocumentDAO;
import com.sonicle.webtop.drm.dal.OpportunityActionDocumentDAO;
import com.sonicle.webtop.drm.dal.OpportunityDocumentDAO;
import com.sonicle.webtop.drm.dal.WorkReportAttachmentDAO;
import com.sonicle.webtop.drm.model.BusinessTrip;
import com.sonicle.webtop.drm.model.Company;
import com.sonicle.webtop.drm.model.CompanyPicture;
import com.sonicle.webtop.drm.model.CompanyUserAssociation;
import com.sonicle.webtop.drm.model.DocStatus;
import com.sonicle.webtop.drm.model.DocStatusGroupAssociation;
import com.sonicle.webtop.drm.model.DrmFolder;
import com.sonicle.webtop.drm.model.DrmFolderGroupAssociation;
import com.sonicle.webtop.drm.model.DrmGroup;
import com.sonicle.webtop.drm.model.DrmGroupUserAssociation;
import com.sonicle.webtop.drm.model.DrmLineManager;
import com.sonicle.webtop.drm.model.DrmProfile;
import com.sonicle.webtop.drm.model.EmployeeProfile;
import com.sonicle.webtop.drm.model.HolidayDate;
import com.sonicle.webtop.drm.model.HourProfile;
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
import com.sonicle.webtop.drm.model.TimetableReport;
import com.sonicle.webtop.drm.model.TimetableSetting;
import com.sonicle.webtop.drm.model.TimetableStamp;
import com.sonicle.webtop.drm.model.UserForManager;
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

/**
 *
 * @author avasi
 */
public class ManagerUtils {
	
	static String getProductName() {
		return WT.getPlatformName() + " DRM";
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
		wrkRpt.setDayTrasfert(oWrkRpt.getDayTrasfert().intValue());
		wrkRpt.setEventId(oWrkRpt.getEventId());

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
		oWrkRpt.setDayTrasfert(wrkRpt.getDayTrasfert().shortValue());
		oWrkRpt.setEventId(wrkRpt.getEventId());

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
		wrkDetail.setRowFlag(oWrkDetail.getRowFlag());

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
		oWrkDetail.setRowFlag(wrkDetail.getRowFlag());

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

		return stamp;
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
		if(null != e && null != u){
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
			
			return period.getHours()+ "." + period.getMinutes();
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
		o.setDate(oOpt.getDate());
		o.setFromHour(oOpt.getFromHour());
		o.setToHour(oOpt.getToHour());
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
		oOpt.setDate(o.getDate());
		oOpt.setFromHour(o.getFromHour());
		oOpt.setToHour(o.getToHour());
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
		oAct.setDate(act.getDate());
		oAct.setFromHour(act.getFromHour());
		oAct.setToHour(act.getToHour());
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
		act.setDate(oAct.getDate());
		act.setFromHour(oAct.getFromHour());
		act.setToHour(oAct.getToHour());
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
	
	static HolidayDate createHolidayDate(OHolidayDate oHd) {

		if (oHd == null) {
			return null;
		}

		HolidayDate hd = new HolidayDate();
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
		oHd.setDomainId(hd.getDomainId());
		oHd.setDate(hd.getDate());
		oHd.setDescription(hd.getDescription());

		return oHd;
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
			}
		}
		
		return new ArrayList(hashTr.values());
	}
	
	static List<OTimetableReport> mergeStampAndEventByDate(List<OTimetableReport> trsf){
		
		HashMap<LocalDate, OTimetableReport> hashTr = new HashMap();
	
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
			}
		}
		
		//Add empty days into HashMaps
		if(!trsf.isEmpty()){
			List<DateTime> dates = new ArrayList();
			for(int i = trsf.get(0).getDate().dayOfMonth().getMinimumValue(); i <= trsf.get(0).getDate().dayOfMonth().getMaximumValue(); i++){
				dates.add(trsf.get(0).getDate().withDayOfMonth(i));
			}

			for(DateTime dt : dates){
				if(hashTr.get(dt.toLocalDate()) == null){
					OTimetableReport temp = new OTimetableReport();
					temp.setDomainId(trsf.get(0).getDomainId());
					temp.setCompanyId(trsf.get(0).getCompanyId());
					temp.setUserId(trsf.get(0).getUserId());
					temp.setDate(dt);
					
					hashTr.put(dt.toLocalDate(), temp);
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
		
		for(OTimetableEvent te : tel){
			tr = new OTimetableReport();
			
			tr.setDomainId(te.getDomainId());
			tr.setCompanyId(te.getCompanyId());
			tr.setUserId(te.getUserId());
			tr.setDate(te.getDate().toDateTimeAtStartOfDay());
			
			OLeaveRequestType requestType = EnumUtils.forSerializedName(te.getType(), OLeaveRequestType.class);
			
			if(OLeaveRequestType.HOLIDAY.equals(requestType)){
				tr.setHoliday(te.getHour());
			}else if(OLeaveRequestType.PAID_LEAVE.equals(requestType)){
				tr.setPaidLeave(te.getHour());
			}else if(OLeaveRequestType.UNPAID_LEAVE.equals(requestType)){
				tr.setUnpaidLeave(te.getHour());
			}else if(OLeaveRequestType.CONTRACTUAL.equals(requestType)){
				tr.setContractual(te.getHour());
			}else if(OLeaveRequestType.MEDICAL_VISIT.equals(requestType)){
				tr.setMedicalVisit(te.getHour());
			}else if(OLeaveRequestType.OVERTIME.equals(requestType)){
				tr.setOvertime(te.getHour());
			}
			
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

		return oTr;
	}
}
