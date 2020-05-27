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

import com.sonicle.commons.EnumUtils;
import com.sonicle.commons.IdentifierUtils;
import com.sonicle.commons.LangUtils;
import com.sonicle.commons.LangUtils.CollectionChangeSet;
import static com.sonicle.commons.LangUtils.getCollectionChanges;
import com.sonicle.commons.PathUtils;
import com.sonicle.commons.db.DbUtils;
import com.sonicle.webtop.contacts.IContactsManager;
import com.sonicle.webtop.contacts.model.Contact;
import com.sonicle.webtop.core.CoreManager;
import com.sonicle.webtop.core.app.WT;
import com.sonicle.webtop.core.bol.OUser;
import com.sonicle.webtop.core.dal.DAOException;
import com.sonicle.webtop.core.dal.UserDAO;
import com.sonicle.webtop.core.sdk.BaseManager;
import com.sonicle.webtop.core.sdk.UserProfile;
import com.sonicle.webtop.core.sdk.UserProfileId;
import com.sonicle.webtop.core.sdk.WTException;
import com.sonicle.webtop.core.util.LogEntries;
import static com.sonicle.webtop.drm.ManagerUtils.createCostType;
import static com.sonicle.webtop.drm.Service.logger;
import com.sonicle.webtop.drm.bol.OActivity;
import com.sonicle.webtop.drm.bol.OActivityGroup;
import com.sonicle.webtop.drm.bol.OBusinessTrip;
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
import com.sonicle.webtop.drm.bol.OLineHour;
import com.sonicle.webtop.drm.bol.OEmployeeProfile;
import com.sonicle.webtop.drm.bol.OExpenseNote;
import com.sonicle.webtop.drm.bol.OExpenseNoteDetail;
import com.sonicle.webtop.drm.bol.OExpenseNoteDetailDocument;
import com.sonicle.webtop.drm.bol.OExpenseNoteDetailDocumentData;
import com.sonicle.webtop.drm.bol.OExpenseNoteDocument;
import com.sonicle.webtop.drm.bol.OExpenseNoteDocumentData;
import com.sonicle.webtop.drm.bol.OExpenseNoteSetting;
import com.sonicle.webtop.drm.bol.OHolidayDate;
import com.sonicle.webtop.drm.bol.OHourProfile;
import com.sonicle.webtop.drm.bol.OJob;
import com.sonicle.webtop.drm.bol.OJobAttachment;
import com.sonicle.webtop.drm.bol.OLeaveRequest;
import com.sonicle.webtop.drm.bol.OLeaveRequestDocument;
import com.sonicle.webtop.drm.bol.OLeaveRequestDocumentData;
import com.sonicle.webtop.drm.bol.OLeaveRequestType;
import com.sonicle.webtop.drm.bol.OOpportunity;
import com.sonicle.webtop.drm.bol.OOpportunityAction;
import com.sonicle.webtop.drm.bol.OOpportunityActionDocument;
import com.sonicle.webtop.drm.bol.OOpportunityActionDocumentsData;
import com.sonicle.webtop.drm.bol.OOpportunityActionInterlocutor;
import com.sonicle.webtop.drm.bol.OOpportunityDocument;
import com.sonicle.webtop.drm.bol.OOpportunityDocumentsData;
import com.sonicle.webtop.drm.bol.OOpportunityField;
import com.sonicle.webtop.drm.bol.OOpportunityInterlocutor;
import com.sonicle.webtop.drm.bol.OProfileMasterdata;
import com.sonicle.webtop.drm.bol.OProfileSupervisedUser;
import com.sonicle.webtop.drm.bol.OProfileMember;
import com.sonicle.webtop.drm.bol.OTicket;
import com.sonicle.webtop.drm.bol.OTicketAttachment;
import com.sonicle.webtop.drm.bol.OTicketCategory;
import com.sonicle.webtop.drm.bol.OTimetableEvent;
import com.sonicle.webtop.drm.bol.OTimetableReport;
import com.sonicle.webtop.drm.bol.OTimetableSetting;
import com.sonicle.webtop.drm.bol.OTimetableStamp;
import com.sonicle.webtop.drm.bol.OViewJob;
import com.sonicle.webtop.drm.bol.OViewTicket;
import com.sonicle.webtop.drm.bol.OViewWorkReport;
import com.sonicle.webtop.drm.bol.OWorkReport;
import com.sonicle.webtop.drm.bol.OWorkReportAttachment;
import com.sonicle.webtop.drm.bol.OWorkReportRow;
import com.sonicle.webtop.drm.bol.OWorkReportSetting;
import com.sonicle.webtop.drm.bol.OWorkReportsAttachmentsData;
import com.sonicle.webtop.drm.bol.OWorkType;
import com.sonicle.webtop.drm.bol.VOpportunityEntry;
import com.sonicle.webtop.drm.dal.ActivityDAO;
import com.sonicle.webtop.drm.dal.ActivityGroupDAO;
import com.sonicle.webtop.drm.dal.BusinessTripDao;
import com.sonicle.webtop.drm.dal.CompanyDAO;
import com.sonicle.webtop.drm.dal.CompanyPictureDAO;
import com.sonicle.webtop.drm.dal.CompanyUserDAO;
import com.sonicle.webtop.drm.dal.CostTypeDAO;
import com.sonicle.webtop.drm.dal.DocStatusDAO;
import com.sonicle.webtop.drm.dal.DocStausGroupDAO;
import com.sonicle.webtop.drm.dal.DrmFolderDAO;
import com.sonicle.webtop.drm.dal.DrmFolderGroupDAO;
import com.sonicle.webtop.drm.dal.DrmGroupDAO;
import com.sonicle.webtop.drm.dal.DrmGroupUserDAO;
import com.sonicle.webtop.drm.dal.DrmLineManagerDAO;
import com.sonicle.webtop.drm.dal.DrmProfileDAO;
import com.sonicle.webtop.drm.dal.DrmUserForManagerDAO;
import com.sonicle.webtop.drm.dal.LineHourDAO;
import com.sonicle.webtop.drm.dal.EmployeeProfileDAO;
import com.sonicle.webtop.drm.dal.ExpenseNoteDAO;
import com.sonicle.webtop.drm.dal.ExpenseNoteDetailDAO;
import com.sonicle.webtop.drm.dal.ExpenseNoteDetailDocumentDAO;
import com.sonicle.webtop.drm.dal.ExpenseNoteDocumentDAO;
import com.sonicle.webtop.drm.dal.ExpenseNoteSettingDAO;
import com.sonicle.webtop.drm.dal.HolidayDateDAO;
import com.sonicle.webtop.drm.dal.HourProfileDAO;
import com.sonicle.webtop.drm.dal.JobAttachmentDAO;
import com.sonicle.webtop.drm.dal.JobDAO;
import com.sonicle.webtop.drm.dal.LeaveRequestDAO;
import com.sonicle.webtop.drm.dal.LeaveRequestDocumentDAO;
import com.sonicle.webtop.drm.dal.OpportunityActionDAO;
import com.sonicle.webtop.drm.dal.OpportunityActionDocumentDAO;
import com.sonicle.webtop.drm.dal.OpportunityActionInterlocutorDAO;
import com.sonicle.webtop.drm.dal.OpportunityDAO;
import com.sonicle.webtop.drm.dal.OpportunityDocumentDAO;
import com.sonicle.webtop.drm.dal.OpportunityFieldDAO;
import com.sonicle.webtop.drm.dal.OpportunityInterlocutorDAO;
import com.sonicle.webtop.drm.dal.ProfileMasterdataDAO;
import com.sonicle.webtop.drm.dal.ProfileSupervisedUserDAO;
import com.sonicle.webtop.drm.dal.ProfileMemberDAO;
import com.sonicle.webtop.drm.dal.TicketAttachmentDAO;
import com.sonicle.webtop.drm.dal.TicketCategoryDAO;
import com.sonicle.webtop.drm.dal.TicketDAO;
import com.sonicle.webtop.drm.dal.TimetableEventDAO;
import com.sonicle.webtop.drm.dal.TimetableReportDAO;
import com.sonicle.webtop.drm.dal.TimetableSettingDAO;
import com.sonicle.webtop.drm.dal.TimetableStampDAO;
import com.sonicle.webtop.drm.dal.UtilityDAO;
import com.sonicle.webtop.drm.dal.WorkReportAttachmentDAO;
import com.sonicle.webtop.drm.dal.WorkReportDAO;
import com.sonicle.webtop.drm.dal.WorkReportRowDAO;
import com.sonicle.webtop.drm.dal.WorkReportSettingDAO;
import com.sonicle.webtop.drm.dal.WorkReportSummaryDAO;
import com.sonicle.webtop.drm.dal.WorkTypeDAO;
import com.sonicle.webtop.drm.model.Activity;
import com.sonicle.webtop.drm.model.ActivityGroupAssociation;
import com.sonicle.webtop.drm.model.BusinessTrip;
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
import com.sonicle.webtop.drm.model.LineHour;
import com.sonicle.webtop.drm.model.EmployeeProfile;
import com.sonicle.webtop.drm.model.ExpenseNote;
import com.sonicle.webtop.drm.model.ExpenseNoteDetail;
import com.sonicle.webtop.drm.model.ExpenseNoteDetailDocumentWithBytes;
import com.sonicle.webtop.drm.model.ExpenseNoteDocument;
import com.sonicle.webtop.drm.model.ExpenseNoteDocumentWithBytes;
import com.sonicle.webtop.drm.model.ExpenseNoteDocumentWithStream;
import com.sonicle.webtop.drm.model.ExpenseNoteSetting;
import com.sonicle.webtop.drm.model.HolidayDate;
import com.sonicle.webtop.drm.model.HourProfile;
import com.sonicle.webtop.drm.model.Job;
import com.sonicle.webtop.drm.model.JobAttachment;
import com.sonicle.webtop.drm.model.JobAttachmentWithStream;
import com.sonicle.webtop.drm.model.LeaveRequest;
import com.sonicle.webtop.drm.model.LeaveRequestDocument;
import com.sonicle.webtop.drm.model.LeaveRequestDocumentWithBytes;
import com.sonicle.webtop.drm.model.LeaveRequestDocumentWithStream;
import com.sonicle.webtop.drm.model.Opportunity;
import com.sonicle.webtop.drm.model.OpportunityAction;
import com.sonicle.webtop.drm.model.OpportunityActionDocument;
import com.sonicle.webtop.drm.model.OpportunityActionDocumentWithBytes;
import com.sonicle.webtop.drm.model.OpportunityActionDocumentWithStream;
import com.sonicle.webtop.drm.model.OpportunityActionInterlocutor;
import com.sonicle.webtop.drm.model.OpportunityDocument;
import com.sonicle.webtop.drm.model.OpportunityDocumentWithBytes;
import com.sonicle.webtop.drm.model.OpportunityDocumentWithStream;
import com.sonicle.webtop.drm.model.OpportunityField;
import com.sonicle.webtop.drm.model.OpportunityInterlocutor;
import com.sonicle.webtop.drm.model.OpportunitySetting;
import com.sonicle.webtop.drm.model.ProfileMasterdata;
import com.sonicle.webtop.drm.model.ProfileSupervisedUser;
import com.sonicle.webtop.drm.model.ProfileMember;
import com.sonicle.webtop.drm.model.Ticket;
import com.sonicle.webtop.drm.model.TicketAttachment;
import com.sonicle.webtop.drm.model.TicketAttachmentWithStream;
import com.sonicle.webtop.drm.model.TicketCategory;
import com.sonicle.webtop.drm.model.TicketSetting;
import com.sonicle.webtop.drm.model.TimetableReport;
import com.sonicle.webtop.drm.model.TimetableSetting;
import com.sonicle.webtop.drm.model.TimetableStamp;
import com.sonicle.webtop.drm.model.UserForManager;
import com.sonicle.webtop.drm.model.WorkReport;
import com.sonicle.webtop.drm.model.WorkReportAttachment;
import com.sonicle.webtop.drm.model.WorkReportAttachmentWithBytes;
import com.sonicle.webtop.drm.model.WorkReportAttachmentWithStream;
import com.sonicle.webtop.drm.model.WorkReportRow;
import com.sonicle.webtop.drm.model.WorkReportSetting;
import com.sonicle.webtop.drm.model.WorkReportSummary;
import com.sonicle.webtop.drm.model.WorkType;
import com.sonicle.webtop.drm.util.EmailNotification;
import eu.medsea.mimeutil.MimeType;
import freemarker.template.TemplateException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.imgscalr.Scalr;
import org.joda.time.LocalDate;
import org.joda.time.Minutes;
import org.jooq.Record;
import org.jooq.Result;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.cellprocessor.joda.FmtDateTime;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.prefs.CsvPreference;

/**
 *
 * @author lssndrvs
 */
public class DrmManager extends BaseManager {

	public DrmManager(boolean fastInit, UserProfileId targetProfileId) {
		super(fastInit, targetProfileId);
	}

	private DateTime createRevisionTimestamp() {
		return DateTime.now(DateTimeZone.UTC);
	}

	public List<OCompany> listCompaniesByDomainUser() throws WTException {
		Connection con = null;
		CompanyDAO compDao = CompanyDAO.getInstance();
		List<OCompany> companies = null;
		try {
			con = WT.getConnection(SERVICE_ID);

			companies = compDao.selectCompaniesByDomainUser(con, getTargetProfileId().getDomainId(), getTargetProfileId().getUserId());
			
			return companies;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public List<OCompany> listCompaniesByDomain() throws WTException {
		Connection con = null;
		CompanyDAO compDao = CompanyDAO.getInstance();
		List<OCompany> companies = null;
		try {
			con = WT.getConnection(SERVICE_ID);

			companies = compDao.selectCompaniesByDomain(con, getTargetProfileId().getDomainId());
			
			return companies;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public List<OUser> listCompanyUsers(int companyId) throws WTException {

		CoreManager coreMgr = WT.getCoreManager(getTargetProfileId());

		Connection con = null;
		CompanyUserDAO compDao = CompanyUserDAO.getInstance();
		UserDAO usrDao = UserDAO.getInstance();

		try {

			con = WT.getConnection(SERVICE_ID);

			List<OUser> users = coreMgr.listUsers(true);
			List<OUser> users2 = new ArrayList();

			HashSet<String> userIds = new HashSet(compDao.viewUserIdByCompany(con, companyId));

			for (OUser usr : users) {
				if (userIds.contains(usr.getUserId())) {
					users2.add(usr);
				}
			}

			return users2;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public List<OUser> listCompanyProfileUsers(int companyId) throws WTException {

		Connection con = null;
		DrmProfileDAO profileDao = DrmProfileDAO.getInstance();
		ProfileSupervisedUserDAO prfSupDao = ProfileSupervisedUserDAO.getInstance();
		List<OUser> users2 = new ArrayList();

		try {

			con = WT.getConnection(SERVICE_ID);

			List<OUser> users = listCompanyUsers(companyId);

			ODrmProfile profile = profileDao.selectProfileTypeByUserId(con, getTargetProfileId().getDomainId(),
					getTargetProfileId().getUserId());
			
			if (profile != null) {
				HashSet<String> userIds = new HashSet(prfSupDao.viewUserdIdByProfile(con, profile.getProfileId()));
				if (profile.getType().equals(EnumUtils.toSerializedName(DrmProfile.Type.SUPERVISOR))) {
					for (OUser usr : users) {
						if (userIds.contains(usr.getUserId())) {
							users2.add(usr);
						}
					}
				}
			}
			
			return users2;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public Company getCompany(int companyId) throws WTException {
		Connection con = null;
		CompanyDAO compDao = CompanyDAO.getInstance();
		CompanyPictureDAO cmpPicDao = CompanyPictureDAO.getInstance();
		CompanyUserDAO comUsrDao = CompanyUserDAO.getInstance();

		Company company = null;
		try {

			con = WT.getConnection(SERVICE_ID);

			boolean hasPicture = cmpPicDao.hasPicture(con, companyId);
			company = ManagerUtils.createCompany(compDao.selectById(con, companyId), hasPicture);

			for (OCompanyUser oComUsr : comUsrDao.selectByCompany(con, companyId)) {
				company.getAssociatedUsers().add(ManagerUtils.createCompanyUser(oComUsr));
			}

			return company;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public OCompany addCompany(Company company) throws WTException {
		return addCompany(company, null);
	}

	public OCompany addCompany(Company company, CompanyPicture picture) throws WTException {

		Connection con = null;

		try {
			con = WT.getConnection(SERVICE_ID, false);

			CompanyDAO compDao = CompanyDAO.getInstance();
			CompanyUserDAO compUsrDao = CompanyUserDAO.getInstance();

			OCompany newCompany = ManagerUtils.createOCompany(company);
			newCompany.setDomainId(getTargetProfileId().getDomainId());
			newCompany.setCompanyId(compDao.getSequence(con).intValue());

			OCompanyUser newCompUsr = null;
			for (CompanyUserAssociation compUsr : company.getAssociatedUsers()) {
				newCompUsr = ManagerUtils.createOCompanyUser(compUsr);
				newCompUsr.setAssociationId(compUsrDao.getSequence(con).intValue());
				newCompUsr.setCompanyId(newCompany.getCompanyId());
				compUsrDao.insert(con, newCompUsr);
			}

			compDao.insertCompany(con, newCompany);
			
			if (company.getHasPicture()) {
				if (picture != null) {
					doInsertCompanyPicture(con, newCompany.getCompanyId(), picture);
				}
			}

			DbUtils.commitQuietly(con);

			return newCompany;

		} catch (SQLException | DAOException ex) {

			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");

		} catch (Exception ex) {

			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);

		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public OCompany updateCompany(Company company) throws WTException, IOException {
		return updateCompany(company, null);
	}

	public OCompany updateCompany(Company item, CompanyPicture picture) throws WTException, IOException {

		Connection con = null;
		CompanyDAO compDao = CompanyDAO.getInstance();
		CompanyUserDAO compUsrDao = CompanyUserDAO.getInstance();

		try {

			Company oldCompany = getCompany(item.getCompanyId());

			con = WT.getConnection(SERVICE_ID, false);

			OCompany company = ManagerUtils.createOCompany(item);

			compDao.update(con, company);

			LangUtils.CollectionChangeSet<CompanyUserAssociation> changesSet1 = LangUtils.getCollectionChanges(oldCompany.getAssociatedUsers(), item.getAssociatedUsers());

			for (CompanyUserAssociation compUsrs : changesSet1.inserted) {

				OCompanyUser comU = ManagerUtils.createOCompanyUser(compUsrs);

				comU.setAssociationId(compUsrDao.getSequence(con).intValue());
				comU.setCompanyId(item.getCompanyId());

				compUsrDao.insert(con, comU);
			}

			for (CompanyUserAssociation compUsrs : changesSet1.deleted) {
				compUsrDao.deleteById(con, compUsrs.getAssociationId());
			}
			
			if (item.getHasPicture()) {
				if (picture != null) {
					doUpdateCompanyPicture(con, company.getCompanyId(), picture);
				}
			} else {
				doDeleteCompanyPicture(con, company.getCompanyId());
			}

			DbUtils.commitQuietly(con);

			return company;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public void deleteCompany(int companyId) throws WTException {

		Connection con = null;
		CompanyDAO compDao = CompanyDAO.getInstance();
		CompanyUserDAO comUsrDao = CompanyUserDAO.getInstance();
		try {
			con = WT.getConnection(SERVICE_ID, false);

			compDao.deleteById(con, companyId);

			comUsrDao.deleteByCompany(con, companyId);
			
			doDeleteCompanyPicture(con, companyId);

			DbUtils.commitQuietly(con);

		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");
		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public List<OEmployeeProfile> listEmployeeProfiles() throws WTException {
		Connection con = null;
		EmployeeProfileDAO epDao = EmployeeProfileDAO.getInstance();
		List<OEmployeeProfile> eps = null;
		try {

			con = WT.getConnection(SERVICE_ID);

			eps = epDao.selectEmployeeProfileByDomain(con, getTargetProfileId().getDomainId());

			return eps;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public List<OHourProfile> listHourProfiles() throws WTException {
		Connection con = null;
		HourProfileDAO hpDao = HourProfileDAO.getInstance();
		List<OHourProfile> hps = null;
		try {

			con = WT.getConnection(SERVICE_ID);

			hps = hpDao.selectHourProfileByDomain(con, getTargetProfileId().getDomainId());

			return hps;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public List<ODocStatus> listDocStatuses() throws WTException {
		Connection con = null;
		DocStatusDAO staDao = DocStatusDAO.getInstance();
		List<ODocStatus> statuses = null;
		
		try {

			con = WT.getConnection(SERVICE_ID);

			statuses = staDao.select(con);

			return statuses;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public DocStatus getDocStatus(int docStatusId) throws WTException {
		Connection con = null;
		DocStatusDAO docDao = DocStatusDAO.getInstance();
		DocStausGroupDAO docGroupDao = DocStausGroupDAO.getInstance();
		DocStatus docStatus = null;
		try {

			con = WT.getConnection(SERVICE_ID);

			docStatus = ManagerUtils.createDocStatus(docDao.selectById(con, docStatusId));

			for (ODocStatusGroup odocS : docGroupDao.selectByDocStatus(con, docStatusId)) {
				docStatus.getAssociatedProfiles().add(ManagerUtils.createDocStatusProfile(odocS));
			}

			return docStatus;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public ODocStatus addDocStatus(DocStatus docStatus) throws WTException {

		Connection con = null;

		try {
			con = WT.getConnection(SERVICE_ID, false);

			DocStatusDAO docDao = DocStatusDAO.getInstance();
			DocStausGroupDAO docGDao = DocStausGroupDAO.getInstance();

			ODocStatus newDocStatus = ManagerUtils.createODocStatus(docStatus);
			newDocStatus.setDocStatusId(docDao.getSequence(con).intValue());
			newDocStatus.setBuiltIn(false);

			ODocStatusGroup newDocS = null;
			for (DocStatusGroupAssociation docS : docStatus.getAssociatedProfiles()) {

				newDocS = ManagerUtils.createODocStatusProfile(docS);
				newDocS.setDocStatusId(newDocStatus.getDocStatusId());
				newDocS.setAssociationId(docGDao.getSequence(con).intValue());

				docGDao.insert(con, newDocS);
			}

			docDao.insertDocStatus(con, newDocStatus);

			DbUtils.commitQuietly(con);

			return newDocStatus;

		} catch (SQLException | DAOException ex) {

			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");

		} catch (Exception ex) {

			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);

		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public ODocStatus updateDocStatus(DocStatus item) throws WTException {

		Connection con = null;
		DocStatusDAO docDao = DocStatusDAO.getInstance();
		DocStausGroupDAO docGDao = DocStausGroupDAO.getInstance();
		try {

			DocStatus oldDocStatus = getDocStatus(item.getDocStatusId());

			con = WT.getConnection(SERVICE_ID, false);

			ODocStatus docStatus = ManagerUtils.createODocStatus(item);

			docDao.update(con, docStatus);

			LangUtils.CollectionChangeSet<DocStatusGroupAssociation> changesSet1 = LangUtils.getCollectionChanges(oldDocStatus.getAssociatedProfiles(), item.getAssociatedProfiles());

			for (DocStatusGroupAssociation docsGroup : changesSet1.inserted) {

				ODocStatusGroup docG = ManagerUtils.createODocStatusProfile(docsGroup);

				docG.setAssociationId(docGDao.getSequence(con).intValue());
				docG.setDocStatusId(item.getDocStatusId());

				docGDao.insert(con, docG);
			}

			for (DocStatusGroupAssociation docsGroup : changesSet1.deleted) {
				docGDao.deleteById(con, docsGroup.getAssociationId());
			}

			DbUtils.commitQuietly(con);

			return docStatus;

		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");

		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public void deleteDocStatus(int docStatusId) throws WTException {

		Connection con = null;
		DocStatusDAO docDao = DocStatusDAO.getInstance();
		DocStausGroupDAO docGrpDao = DocStausGroupDAO.getInstance();
		try {
			con = WT.getConnection(SERVICE_ID, false);

			docDao.deleteById(con, docStatusId);
			docGrpDao.deleteByDocStatus(con, docStatusId);

			DbUtils.commitQuietly(con);

		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");
		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public OWorkType getWorkType(int workTypeId) throws WTException {
		Connection con = null;
		WorkTypeDAO wtDao = WorkTypeDAO.getInstance();
		OWorkType wt = null;
		
		try {
			con = WT.getConnection(SERVICE_ID);

			wt = wtDao.selectById(con, getTargetProfileId().getDomainId(), workTypeId);

			return wt;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public List<ODrmGroup> listGroups(String groupCategory) throws WTException {
		Connection con = null;
		DrmGroupDAO grpDao = DrmGroupDAO.getInstance();
		List<ODrmGroup> groups = null;
		try {

			con = WT.getConnection(SERVICE_ID);

			if (StringUtils.isEmpty(groupCategory)) {
				groups = grpDao.selectGroupsByDomain(con, getTargetProfileId().getDomainId());
			} else {
				groups = grpDao.selectGroupsByDomainCategory(con, getTargetProfileId().getDomainId(), groupCategory);

			}
			return groups;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public DrmGroup getDrmGroup(String groupId) throws WTException {
		Connection con = null;
		DrmGroupDAO grpDao = DrmGroupDAO.getInstance();
		DrmGroupUserDAO grpUsrDao = DrmGroupUserDAO.getInstance();
		DrmGroup group = null;
		try {

			con = WT.getConnection(SERVICE_ID);

			group = ManagerUtils.createDrmGroup(grpDao.selectById(con, groupId));

			for (ODrmGroupUser oGrpUsr : grpUsrDao.selectByGroup(con, groupId)) {
				group.getAssociatedUsers().add(ManagerUtils.createDrmGroupUser(oGrpUsr));
			}

			return group;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public ODrmGroup addDrmGroup(DrmGroup group) throws WTException {

		Connection con = null;

		try {
			con = WT.getConnection(SERVICE_ID, false);

			DrmGroupDAO grpDao = DrmGroupDAO.getInstance();
			DrmGroupUserDAO grpUsrDao = DrmGroupUserDAO.getInstance();

			ODrmGroup newDrmGroup = ManagerUtils.createODrmGroup(group);
			newDrmGroup.setGroupId(IdentifierUtils.getUUID());
			newDrmGroup.setDomainId(getTargetProfileId().getDomainId());

			ODrmGroupUser newGrpUsr = null;
			for (DrmGroupUserAssociation grpUsr : group.getAssociatedUsers()) {

				newGrpUsr = ManagerUtils.createODrmGroupUser(grpUsr);
				newGrpUsr.setGroupId(newDrmGroup.getGroupId());
				newGrpUsr.setAssociationId(grpUsrDao.getSequence(con).intValue());

				grpUsrDao.insert(con, newGrpUsr);
			}
			grpDao.insert(con, newDrmGroup);

			DbUtils.commitQuietly(con);

			return newDrmGroup;

		} catch (SQLException | DAOException ex) {

			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");

		} catch (Exception ex) {

			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);

		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public ODrmGroup updateDrmGroup(DrmGroup item) throws WTException {

		Connection con = null;
		DrmGroupDAO groDao = DrmGroupDAO.getInstance();
		DrmGroupUserDAO grpUsrDao = DrmGroupUserDAO.getInstance();
		try {

			DrmGroup oldDrmGroup = getDrmGroup(item.getGroupId());

			con = WT.getConnection(SERVICE_ID, false);

			ODrmGroup group = ManagerUtils.createODrmGroup(item);

			groDao.update(con, group);

			LangUtils.CollectionChangeSet<DrmGroupUserAssociation> changesSet1 = LangUtils.getCollectionChanges(oldDrmGroup.getAssociatedUsers(), item.getAssociatedUsers());

			for (DrmGroupUserAssociation grpUsr : changesSet1.inserted) {

				ODrmGroupUser oGrpUsr = ManagerUtils.createODrmGroupUser(grpUsr);

				oGrpUsr.setAssociationId(grpUsrDao.getSequence(con).intValue());
				oGrpUsr.setGroupId(item.getGroupId());

				grpUsrDao.insert(con, oGrpUsr);
			}

			for (DrmGroupUserAssociation grpUser : changesSet1.deleted) {
				grpUsrDao.deleteById(con, grpUser.getAssociationId());
			}
			DbUtils.commitQuietly(con);

			return group;

		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");

		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public void deleteDrmGroup(String groupId) throws WTException {

		Connection con = null;
		DrmGroupDAO grpDao = DrmGroupDAO.getInstance();
		DrmGroupUserDAO grpUsrDao = DrmGroupUserDAO.getInstance();

		try {
			con = WT.getConnection(SERVICE_ID, false);

			grpDao.deleteById(con, groupId);

			grpUsrDao.deleteByGroup(con, groupId);

			DbUtils.commitQuietly(con);

		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");
		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public List<ODrmProfile> listProfiles() throws WTException {
		Connection con = null;
		DrmProfileDAO pflDao = DrmProfileDAO.getInstance();
		List<ODrmProfile> profiles = null;
		try {

			con = WT.getConnection(SERVICE_ID);

			profiles = pflDao.selectProfileByDomain(con, getTargetProfileId().getDomainId());

			return profiles;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public List<OProfileMember> getDrmProfileMemberByUserId(String userId) throws WTException, SQLException {
		Connection con = null;
		ProfileMemberDAO pmDao = ProfileMemberDAO.getInstance();
		ArrayList<OProfileMember> apf= null;
		try {
			con = WT.getConnection(SERVICE_ID);
			
			return pmDao.selectByUserId(con, userId);
		} catch (DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public DrmProfile getDrmProfile(String profileId) throws WTException {
		Connection con = null;
		DrmProfileDAO pflDao = DrmProfileDAO.getInstance();
		ProfileMasterdataDAO prfMasterDao = ProfileMasterdataDAO.getInstance();
		ProfileSupervisedUserDAO prfSupervisedDao = ProfileSupervisedUserDAO.getInstance();
		ProfileMemberDAO prfUserDao = ProfileMemberDAO.getInstance();

		DrmProfile profile = null;
		try {

			con = WT.getConnection(SERVICE_ID);

			profile = ManagerUtils.createProfile(pflDao.selectById(con, profileId));

			if (profile.type.equals(EnumUtils.toSerializedName(DrmProfile.Type.EXTERNAL))) {
				for (OProfileMasterdata oPrfMaster : prfMasterDao.selectByProfile(con, profileId)) {
					profile.getAssociatedCustomers().add(ManagerUtils.createProfileMasterdata(oPrfMaster));
				}
			} else if (profile.type.equals(EnumUtils.toSerializedName(DrmProfile.Type.SUPERVISOR))) {
				for (OProfileSupervisedUser oPrfSupervised : prfSupervisedDao.selectByProfile(con, profileId)) {
					profile.getSupervisedUsers().add(ManagerUtils.createProfileSupervisedUser(oPrfSupervised));
				}
			}

			for (OProfileMember oPrfUser : prfUserDao.selectByProfile(con, profileId)) {
				profile.getAssociatedUsers().add(ManagerUtils.createProfileMember(oPrfUser));
			}

			return profile;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public OEmployeeProfile addEmployeeProfile(EmployeeProfile eProfile) throws WTException {

		Connection con = null;

		try {
			con = WT.getConnection(SERVICE_ID, false);

			EmployeeProfileDAO epDao = EmployeeProfileDAO.getInstance();

			OEmployeeProfile oEP = ManagerUtils.createOEmployeeProfile(eProfile);
			oEP.setId(epDao.getSequence(con).intValue());
			oEP.setDomainId(getTargetProfileId().getDomainId());

			epDao.insert(con, oEP);

			DbUtils.commitQuietly(con);

			return oEP;

		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");
		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public OEmployeeProfile updateEmployeeProfile(EmployeeProfile item) throws WTException {

		Connection con = null;
	
		try {
			con = WT.getConnection(SERVICE_ID, false);
			
			EmployeeProfileDAO epDao = EmployeeProfileDAO.getInstance();
			OEmployeeProfile eProfile = ManagerUtils.createOEmployeeProfile(item);

			epDao.update(con, eProfile);

			DbUtils.commitQuietly(con);

			return eProfile;

		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");
		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public void deleteEmployeeProfile(Integer id) throws WTException {

		Connection con = null;
	
		try {
			con = WT.getConnection(SERVICE_ID, false);
			
			EmployeeProfileDAO epDao = EmployeeProfileDAO.getInstance();

			epDao.deleteById(con, id);

			DbUtils.commitQuietly(con);

		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");
		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public OHourProfile addHourProfile(HourProfile hProfile) throws WTException {

		Connection con = null;

		try {
			con = WT.getConnection(SERVICE_ID, false);

			HourProfileDAO hpDao = HourProfileDAO.getInstance();
			LineHourDAO lhDao = LineHourDAO.getInstance();

			OHourProfile oHP = ManagerUtils.createOHourProfile(hProfile);
			oHP.setId(hpDao.getSequence(con).intValue());
			oHP.setDomainId(getTargetProfileId().getDomainId());

			OLineHour oLH = null;
			for (LineHour eh : hProfile.getLineHours()) {
				oLH = ManagerUtils.fillOLineHour(new OLineHour(), eh);
				oLH.setId(lhDao.getSequence(con).intValue());
				oLH.setDomainId(oHP.getDomainId());
				oLH.setHourProfileId(oHP.getId());
				lhDao.insert(con, oLH);
			}

			hpDao.insert(con, oHP);

			DbUtils.commitQuietly(con);

			return oHP;

		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");
		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public OHourProfile updateHourProfile(HourProfile item) throws WTException {

		Connection con = null;
	
		try {
			con = WT.getConnection(SERVICE_ID, false);
			
			HourProfileDAO hpDao = HourProfileDAO.getInstance();
			LineHourDAO lhDao = LineHourDAO.getInstance();
		
			HourProfile oldHP = getHourProfile(item.getId());
			OHourProfile hProfile = ManagerUtils.createOHourProfile(item);

			hpDao.update(con, hProfile);

			LangUtils.CollectionChangeSet<LineHour> changesSet1 = LangUtils.getCollectionChanges(oldHP.getLineHours(), item.getLineHours());
			for (LineHour eh : changesSet1.inserted) {
				final OLineHour oLH = ManagerUtils.fillOLineHour(new OLineHour(), eh);
				oLH.setId(lhDao.getSequence(con).intValue());
				oLH.setDomainId(item.getDomainId());
				oLH.setHourProfileId(item.getId());
				lhDao.insert(con, oLH);
			}

			for (LineHour eh : changesSet1.deleted) {
				lhDao.deleteById(con, eh.getId());
			}
			
			for (LineHour eh : changesSet1.updated) {
				OLineHour oLH = ManagerUtils.createOLineHour(eh);
				lhDao.update(con, oLH);
			}

			DbUtils.commitQuietly(con);

			return hProfile;

		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");
		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public void deleteHourProfile(Integer id) throws WTException {

		Connection con = null;
	
		try {
			con = WT.getConnection(SERVICE_ID, false);
			
			HourProfileDAO hpDao = HourProfileDAO.getInstance();
			LineHourDAO lhDao = LineHourDAO.getInstance();

			hpDao.deleteById(con, id);

			lhDao.deleteByHourProfileId(con, id);

			DbUtils.commitQuietly(con);

		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");
		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public ODrmProfile addDrmProfile(DrmProfile profile) throws WTException {

		Connection con = null;

		try {
			con = WT.getConnection(SERVICE_ID, false);

			DrmProfileDAO pflDao = DrmProfileDAO.getInstance();
			ProfileMasterdataDAO prfMastDao = ProfileMasterdataDAO.getInstance();
			ProfileSupervisedUserDAO prfSupervisedDao = ProfileSupervisedUserDAO.getInstance();
			ProfileMemberDAO prfUserDao = ProfileMemberDAO.getInstance();

			ODrmProfile newDrmProfile = ManagerUtils.createODrmProfile(profile);
			newDrmProfile.setProfileId(IdentifierUtils.getUUID());
			newDrmProfile.setDomainId(getTargetProfileId().getDomainId());

			OProfileMasterdata newPrfMast = null;
			OProfileSupervisedUser newPrfSupervised = null;
			if (newDrmProfile.getType().equals(EnumUtils.toSerializedName(DrmProfile.Type.EXTERNAL))) {
				for (ProfileMasterdata prfMasterD : profile.getAssociatedCustomers()) {

					newPrfMast = ManagerUtils.createOProfileMasterdata(prfMasterD);
					newPrfMast.setId(prfMastDao.getSequence(con).intValue());
					newPrfMast.setProfileId(newDrmProfile.getProfileId());

					prfMastDao.insert(con, newPrfMast);
				}
			} else if (newDrmProfile.getType().equals(EnumUtils.toSerializedName(DrmProfile.Type.SUPERVISOR))) {
				for (ProfileSupervisedUser prfSupervised : profile.getSupervisedUsers()) {

					newPrfSupervised = ManagerUtils.createOProfileSupervisedUser(prfSupervised);
					newPrfSupervised.setId(prfMastDao.getSequence(con).intValue());
					newPrfSupervised.setProfileId(newDrmProfile.getProfileId());

					prfSupervisedDao.insert(con, newPrfSupervised);
				}
			}

			OProfileMember opmem = null;
			for (ProfileMember pmem : profile.getAssociatedUsers()) {
				opmem = ManagerUtils.fillOProfileMember(new OProfileMember(), pmem);
				opmem.setId(prfMastDao.getSequence(con).intValue());
				opmem.setProfileId(newDrmProfile.getProfileId());
				prfUserDao.insert(con, opmem);
			}

			pflDao.insert(con, newDrmProfile);

			DbUtils.commitQuietly(con);

			return newDrmProfile;

		} catch (SQLException | DAOException ex) {

			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");

		} catch (Exception ex) {

			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);

		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public ODrmProfile updateDrmProfile(DrmProfile item) throws WTException {

		Connection con = null;
		DrmProfileDAO pflDao = DrmProfileDAO.getInstance();
		ProfileMasterdataDAO pmasDao = ProfileMasterdataDAO.getInstance();
		ProfileSupervisedUserDAO psupDao = ProfileSupervisedUserDAO.getInstance();
		ProfileMemberDAO pmemDao = ProfileMemberDAO.getInstance();

		try {

			DrmProfile oldDrmProfile = getDrmProfile(item.getProfileId());

			con = WT.getConnection(SERVICE_ID, false);

			ODrmProfile profile = ManagerUtils.createODrmProfile(item);

			pflDao.update(con, profile);

			if (profile.getType().equals(EnumUtils.toSerializedName(DrmProfile.Type.EXTERNAL))) {
				LangUtils.CollectionChangeSet<ProfileMasterdata> changesSet1 = LangUtils.getCollectionChanges(oldDrmProfile.getAssociatedCustomers(), item.getAssociatedCustomers());

				for (ProfileMasterdata prfMaster : changesSet1.inserted) {
					OProfileMasterdata oGrpUsr = ManagerUtils.createOProfileMasterdata(prfMaster);
					oGrpUsr.setId(pmasDao.getSequence(con).intValue());
					oGrpUsr.setProfileId(item.getProfileId());
					pmasDao.insert(con, oGrpUsr);
				}

				for (ProfileMasterdata prfMaster : changesSet1.deleted) {
					pmasDao.deleteById(con, prfMaster.getId());
				}

			} else if (profile.getType().equals(EnumUtils.toSerializedName(DrmProfile.Type.SUPERVISOR))) {

				LangUtils.CollectionChangeSet<ProfileSupervisedUser> changesSet1 = LangUtils.getCollectionChanges(oldDrmProfile.getSupervisedUsers(), item.getSupervisedUsers());

				for (ProfileSupervisedUser prfSupervised : changesSet1.inserted) {
					OProfileSupervisedUser oPrfSupervised = ManagerUtils.createOProfileSupervisedUser(prfSupervised);
					oPrfSupervised.setId(pmasDao.getSequence(con).intValue());
					oPrfSupervised.setProfileId(item.getProfileId());
					psupDao.insert(con, oPrfSupervised);
				}

				for (ProfileSupervisedUser psup : changesSet1.deleted) {
					psupDao.deleteById(con, psup.getId());
				}
			}

			LangUtils.CollectionChangeSet<ProfileMember> changesSet1 = LangUtils.getCollectionChanges(oldDrmProfile.getAssociatedUsers(), item.getAssociatedUsers());
			for (ProfileMember pmem : changesSet1.inserted) {
				final OProfileMember opmem = ManagerUtils.fillOProfileMember(new OProfileMember(), pmem);
				opmem.setId(pmasDao.getSequence(con).intValue());
				opmem.setProfileId(item.getProfileId());
				pmemDao.insert(con, opmem);
			}

			for (ProfileMember pmem : changesSet1.deleted) {
				pmemDao.deleteById(con, pmem.getId());
			}

			DbUtils.commitQuietly(con);

			return profile;

		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");

		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public void deleteDrmProfile(String profileId) throws WTException {

		Connection con = null;
		DrmProfileDAO pflDao = DrmProfileDAO.getInstance();
		ProfileMasterdataDAO prfMasterDao = ProfileMasterdataDAO.getInstance();
		ProfileSupervisedUserDAO prfSupervisedDao = ProfileSupervisedUserDAO.getInstance();
		ProfileMemberDAO prfUserDao = ProfileMemberDAO.getInstance();

		try {
			con = WT.getConnection(SERVICE_ID, false);

			pflDao.deleteById(con, profileId);

			prfMasterDao.deleteByProfile(con, profileId);
			prfUserDao.deleteByProfile(con, profileId);
			prfSupervisedDao.deleteByProfile(con, profileId);

			DbUtils.commitQuietly(con);

		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");
		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public List<ODrmFolder> listFolders() throws WTException {
		Connection con = null;
		DrmFolderDAO fldDao = DrmFolderDAO.getInstance();
		List<ODrmFolder> folders = null;
		try {

			con = WT.getConnection(SERVICE_ID);

			folders = fldDao.selectFolders(con);

			return folders;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public DrmFolder getDrmFolder(String folderId) throws WTException {
		Connection con = null;
		DrmFolderDAO fldDao = DrmFolderDAO.getInstance();
		DrmFolderGroupDAO fldGDao = DrmFolderGroupDAO.getInstance();

		DrmFolder folder = null;
		try {

			con = WT.getConnection(SERVICE_ID);

			folder = ManagerUtils.createFolder(fldDao.selectById(con, folderId));

			for (ODrmFolderGroup oFldG : fldGDao.selectByDocStatus(con, folderId)) {
				folder.getAssociatedGroups().add(ManagerUtils.createDrmFolderGroup(oFldG));
			}

			return folder;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public ODrmFolder addDrmFolder(DrmFolder folder) throws WTException {

		Connection con = null;

		try {
			con = WT.getConnection(SERVICE_ID, false);

			DrmFolderDAO fldDao = DrmFolderDAO.getInstance();
			DrmFolderGroupDAO fldGDao = DrmFolderGroupDAO.getInstance();

			ODrmFolder newFolder = ManagerUtils.createODrmFolder(folder);
			newFolder.setFolderId(IdentifierUtils.getUUID());

			ODrmFolderGroup newFldGrp = null;
			for (DrmFolderGroupAssociation fldG : folder.getAssociatedGroups()) {
				newFldGrp = ManagerUtils.createODrmFolderGroup(fldG);
				newFldGrp.setAssociationId(fldGDao.getSequence(con).intValue());
				newFldGrp.setFolderId(newFolder.getFolderId());

				fldGDao.insert(con, newFldGrp);
			}

			fldDao.insertFolder(con, newFolder);

			DbUtils.commitQuietly(con);

			return newFolder;

		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");
		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public ODrmFolder updateFolder(DrmFolder item) throws WTException {

		Connection con = null;
		DrmFolderDAO fldDao = DrmFolderDAO.getInstance();
		DrmFolderGroupDAO fldGDao = DrmFolderGroupDAO.getInstance();

		try {
			DrmFolder oldDrmFolder = getDrmFolder(item.getFolderId());

			con = WT.getConnection(SERVICE_ID, false);

			ODrmFolder folder = ManagerUtils.createODrmFolder(item);

			fldDao.update(con, folder);

			LangUtils.CollectionChangeSet<DrmFolderGroupAssociation> changesSet1 = LangUtils.getCollectionChanges(oldDrmFolder.getAssociatedGroups(), item.getAssociatedGroups());

			for (DrmFolderGroupAssociation fldGroup : changesSet1.inserted) {

				ODrmFolderGroup fldG = ManagerUtils.createODrmFolderGroup(fldGroup);

				fldG.setAssociationId(fldGDao.getSequence(con).intValue());
				fldG.setFolderId(item.getFolderId());

				fldGDao.insert(con, fldG);
			}

			for (DrmFolderGroupAssociation fldGroup : changesSet1.deleted) {
				fldGDao.deleteById(con, fldGroup.getAssociationId());
			}

			DbUtils.commitQuietly(con);

			return folder;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public void deleteFolder(String folderId) throws WTException {

		Connection con = null;
		DrmFolderDAO fldDao = DrmFolderDAO.getInstance();
		DrmFolderGroupDAO fldGrpDao = DrmFolderGroupDAO.getInstance();

		try {
			con = WT.getConnection(SERVICE_ID, false);

			fldDao.deleteById(con, folderId);

			fldGrpDao.deleteByFolder(con, folderId);

			DbUtils.commitQuietly(con);

		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");
		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public List<OExpenseNote> listExpenseNote(ExpenseNoteQuery query) throws WTException {
		Connection con = null;
		ExpenseNoteDAO eNDao = ExpenseNoteDAO.getInstance();
		List<OExpenseNote> oENotes = null;
		try {
			con = WT.getConnection(SERVICE_ID);
			oENotes = eNDao.selectExpenseNotes(con, query, getTargetProfileId().getDomainId(), getTargetProfileId().getUserId());

			return oENotes;
			
		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public List<OViewWorkReport> listWorkReports(WorkReportQuery query) throws WTException {
		Connection con = null;
		WorkReportDAO wrkDao = WorkReportDAO.getInstance();
		List<OViewWorkReport> workRpts = null;
		try {
			con = WT.getConnection(SERVICE_ID);
			workRpts = wrkDao.selectViewWorkReports(con, query, getTargetProfileId().getDomainId(), getTargetProfileId().getUserId());

			return workRpts;
			
		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public List<OViewJob> listJobs(JobQuery query) throws WTException {
		Connection con = null;
		JobDAO jbDao = JobDAO.getInstance();
		List<OViewJob> jobs = null;
		try {
			con = WT.getConnection(SERVICE_ID);
			jobs = jbDao.selectViewJobs(con, query, getTargetProfileId().getDomainId(), getTargetProfileId().getUserId());

			return jobs;
			
		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public List<WorkReportSummary> listWorkReportSummaryByFilters(String operatorId, Integer companyId, LocalDate from, LocalDate to, Integer docStatusId)throws WTException, SQLException {
		Connection con = null;
		List<String> users = null;
		List<WorkReportSummary> wrss = new ArrayList<>();
		
		
		WorkReportSummaryDAO wrsDao = WorkReportSummaryDAO.getInstance();
		com.sonicle.webtop.drm.dal.UserDAO userDao = com.sonicle.webtop.drm.dal.UserDAO.getInstance();
		
		try {		
			con = WT.getConnection(SERVICE_ID);
			
			if(operatorId == null){
				users = userDao.selectUserSupervisedByDomain(con, getTargetProfileId().getDomainId(), getTargetProfileId().getUserId());
				users.add(0, getTargetProfileId().getUserId());
				
				wrss = wrsDao.selectWorkReportSummaryByCompanyFromDateToDateStatus(con, users, companyId, from, to, docStatusId);
			}else{
				wrss = wrsDao.selectWorkReportSummaryByUserCompanyFromDateToDateStatus(con, operatorId, companyId, from, to, docStatusId);
			}
			
			return wrss;
		} catch (DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public List<OOpportunity> listOpportunities(OpportunityQuery query) throws WTException {
		Connection con = null;
		OpportunityDAO oDao = OpportunityDAO.getInstance();
		List<OOpportunity> opportunities = null
				;
		try {
			con = WT.getConnection(SERVICE_ID);
			opportunities = oDao.selectOpportunities(con, query, getTargetProfileId().getDomainId(), getTargetProfileId().getUserId());

			return opportunities;
			
		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public List<VOpportunityEntry> listOpportunitiesAndActions(OpportunityQuery query) throws WTException {
		Connection con = null;
		OpportunityDAO oDao = OpportunityDAO.getInstance();
		List<VOpportunityEntry> opportunities = null;
		try {
			con = WT.getConnection(SERVICE_ID);
			opportunities = oDao.viewOpportunitiesAndActions(con, query, getTargetProfileId().getDomainId(), getTargetProfileId().getUserId());

			return opportunities;
			
		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public Opportunity getOpportunity(Integer id) throws WTException {
		Connection con = null;
		OpportunityDAO oDAO = OpportunityDAO.getInstance();
		OpportunityActionDAO oActDAO = OpportunityActionDAO.getInstance();
		OpportunityInterlocutorDAO oIDao = OpportunityInterlocutorDAO.getInstance();
		OpportunityDocumentDAO oDDao = OpportunityDocumentDAO.getInstance();
		IContactsManager contactManager = (IContactsManager) WT.getServiceManager("com.sonicle.webtop.contacts", getTargetProfileId());
		
		Opportunity o = null;
		
		try {

			con = WT.getConnection(SERVICE_ID);

			o = ManagerUtils.createOpportunity(oDAO.selectById(con, id));
			
			for (OOpportunityAction oAct : oActDAO.selectByOpportunity(con, id)) {
				o.getActions().add(ManagerUtils.createOpportunityAction(oAct));
			}
			
			for (OOpportunityInterlocutor oInt : oIDao.selectByOpportunity(con, id)) {
				OpportunityInterlocutor oI = ManagerUtils.createOpportunityInterlocutor(oInt);
				Contact c = contactManager.getContact(oInt.getContactId());
				if(c != null){
					String fullName = StringUtils.isEmpty(c.getFullName(true)) ? "" : c.getFullName(true);
					String company = (c.getCompany() == null) ? "" : (StringUtils.isEmpty(c.getCompany().getCompanyDescription()) ? "" : c.getCompany().getCompanyDescription());
					String info = (fullName.length() > 0 && company.length() > 0) ? fullName + " - " + company : fullName + company;
					oI.setDesc(info);
				}
				o.getInterlocutors().add(oI);
			}

			for (OOpportunityDocument oDoc : oDDao.selectByOpportunity(con, id)) {
				o.getDocuments().add(ManagerUtils.createOpportunityDocument(oDoc));
			}

			return o;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public OOpportunity addOpportunity(Opportunity o) throws WTException {
		Connection con = null;
		OpportunityDAO oDao = OpportunityDAO.getInstance();
		OpportunityInterlocutorDAO oIDao = OpportunityInterlocutorDAO.getInstance();
		OpportunityDocumentDAO oDDao = OpportunityDocumentDAO.getInstance();
		
		try {
			con = WT.getConnection(SERVICE_ID, false);
			
			DateTime revisionTimestamp = createRevisionTimestamp();

			OOpportunity newO = ManagerUtils.createOOpportunity(o);
			newO.setId(oDao.getOpportunitySequence(con).intValue());
			newO.setDomainId(getTargetProfileId().getDomainId());
			
			OOpportunityInterlocutor newOInt = null;
			for (OpportunityInterlocutor oInt : o.getInterlocutors()) {
				newOInt = ManagerUtils.createOOpportunityInterlocutor(oInt);
				newOInt.setId(oIDao.getSequence(con).intValue());
				newOInt.setOpportunityId(newO.getId());

				oIDao.insert(con, newOInt);
			}

			ArrayList<OOpportunityDocument> oDocs = new ArrayList<>();
			
			for (OpportunityDocument doc : o.getDocuments()) {
				if (!(doc instanceof OpportunityDocumentWithStream)) throw new IOException("Attachment stream not available [" + doc.getId() + "]");
				oDocs.add(ManagerUtils.doOpportunityDocumentInsert(con, newO.getId(), (OpportunityDocumentWithStream)doc));
			}

			oDao.insert(con, newO);

			DbUtils.commitQuietly(con);

			return newO;

		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");
		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public OOpportunity updateOpportunity(Opportunity item) throws WTException {
		Connection con = null;
		OpportunityDAO oDao = OpportunityDAO.getInstance();
		OpportunityInterlocutorDAO oIDao = OpportunityInterlocutorDAO.getInstance();
		OpportunityDocumentDAO docDao = OpportunityDocumentDAO.getInstance();
		
		try {
			con = WT.getConnection(SERVICE_ID, false);
			
			DateTime revisionTimestamp = createRevisionTimestamp();
			
			Opportunity oldO = getOpportunity(item.getId());

			OOpportunity o = ManagerUtils.createOOpportunity(item);
			
			//Opportunity
			oDao.update(con, o);
			
			//Opportunity Interlocutors
			LangUtils.CollectionChangeSet<OpportunityInterlocutor> changesSet2 = LangUtils.getCollectionChanges(oldO.getInterlocutors(), item.getInterlocutors());
			
			for (OpportunityInterlocutor oInt : changesSet2.inserted) {

				OOpportunityInterlocutor oOInt = ManagerUtils.createOOpportunityInterlocutor(oInt);

				oOInt.setId(oIDao.getSequence(con).intValue());
				oOInt.setOpportunityId(item.getId());

				oIDao.insert(con, oOInt);
			}

			for (OpportunityInterlocutor oInt : changesSet2.deleted) {
				oIDao.deleteById(con, oInt.getId());
			}

			for (OpportunityInterlocutor oInt : changesSet2.updated) {
				OOpportunityInterlocutor oOInt = ManagerUtils.createOOpportunityInterlocutor(oInt);
				oOInt.setOpportunityId(item.getId());

				oIDao.update(con, oOInt);
			}

			//Opportunity Documents
			List<OpportunityDocument> oldDocs = ManagerUtils.createOpportunityDocumentList(docDao.selectByOpportunity(con, item.getId()));
			CollectionChangeSet<OpportunityDocument> changeSet = LangUtils.getCollectionChanges(oldDocs, item.getDocuments());

			for (OpportunityDocument doc : changeSet.inserted) {
				if (!(doc instanceof OpportunityDocumentWithStream)) throw new IOException("Attachment stream not available [" + doc.getId()+ "]");
				ManagerUtils.doOpportunityDocumentInsert(con, o.getId(), (OpportunityDocumentWithStream)doc);
			}
			for (OpportunityDocument doc : changeSet.updated) {
				if (!(doc instanceof OpportunityDocumentWithStream)) continue;
				ManagerUtils.doOpportunityDocumentUpdate(con, (OpportunityDocumentWithStream)doc);
			}
			for (OpportunityDocument doc : changeSet.deleted) {
				docDao.deleteById(con, doc.getId());
			}

			DbUtils.commitQuietly(con);

			return o;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} catch (IOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public void deleteOpportunity(Integer id) throws WTException {
		Connection con = null;
		OpportunityDAO oDao = OpportunityDAO.getInstance();
		OpportunityActionDAO oADao = OpportunityActionDAO.getInstance();
		OpportunityActionInterlocutorDAO oAIDao = OpportunityActionInterlocutorDAO.getInstance();
		OpportunityActionDocumentDAO oADDao = OpportunityActionDocumentDAO.getInstance();
		OpportunityInterlocutorDAO oIDao = OpportunityInterlocutorDAO.getInstance();
		OpportunityDocumentDAO oDDao = OpportunityDocumentDAO.getInstance();
		
		try {
			con = WT.getConnection(SERVICE_ID, false);
			
			for(OOpportunityAction oppAct : oADao.selectByOpportunity(con, id)){
				oADDao.deleteByOpportunityAction(con, oppAct.getId());
				oAIDao.deleteByOpportunityAction(con, oppAct.getId());
			}
			
			oADao.deleteByOpportunity(con, id);
			oIDao.deleteByOpportunity(con, id);
			oDDao.deleteByOpportunity(con, id);
			oDao.deleteById(con, id);
			
			DbUtils.commitQuietly(con);

		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");
		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public OpportunityAction getOpportunityAction(Integer id) throws WTException {
		Connection con = null;
		OpportunityActionDAO oADao = OpportunityActionDAO.getInstance();
		OpportunityActionInterlocutorDAO oAIDao = OpportunityActionInterlocutorDAO.getInstance();
		OpportunityActionDocumentDAO oADDao = OpportunityActionDocumentDAO.getInstance();
		IContactsManager contactManager = (IContactsManager) WT.getServiceManager("com.sonicle.webtop.contacts", getTargetProfileId());
		
		OpportunityAction oAct = null;
		
		try {

			con = WT.getConnection(SERVICE_ID);

			oAct = ManagerUtils.createOpportunityAction(oADao.select(con, id));

			for (OOpportunityActionInterlocutor oActInt : oAIDao.selectByOpportunityAction(con, oAct.getId())) {
				OpportunityActionInterlocutor oAI = ManagerUtils.createOpportunityActionInterlocutor(oActInt);
				Contact c = contactManager.getContact(oActInt.getContactId());
				if(c != null){
					String fullName = StringUtils.isEmpty(c.getFullName(true)) ? "" : c.getFullName(true);
					String company = (c.getCompany() == null) ? "" : (StringUtils.isEmpty(c.getCompany().getCompanyDescription()) ? "" : c.getCompany().getCompanyDescription());
					String info = (fullName.length() > 0 && company.length() > 0) ? fullName + " - " + company : fullName + company;
					oAI.setDesc(info);
				}
				oAct.getActionInterlocutors().add(oAI);
			}

			for (OOpportunityActionDocument oActDoc : oADDao.selectByOpportunityAction(con, oAct.getId())) {
				oAct.getActionDocuments().add(ManagerUtils.createOpportunityActionDocument(oActDoc));
			}

			return oAct;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public int countOpportunityActionsStatusOpenByOpportunityId(Integer opportunityId) throws WTException {
		Connection con = null;
		OpportunityActionDAO oADao = OpportunityActionDAO.getInstance();
		int count;
		
		try {
			con = WT.getConnection(SERVICE_ID);

			count = oADao.countByStatusOpenOpportunityId(con, opportunityId);

			return count;
		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public Integer addOpportunityAction(OpportunityAction oAct) throws WTException {
		Connection con = null;
		OpportunityActionDAO oActDao = OpportunityActionDAO.getInstance();
		OpportunityActionInterlocutorDAO oActIntDao = OpportunityActionInterlocutorDAO.getInstance();
		OpportunityActionDocumentDAO oActDocDao = OpportunityActionDocumentDAO.getInstance();
		
		try {
			con = WT.getConnection(SERVICE_ID, false);
			
			DateTime revisionTimestamp = createRevisionTimestamp();

			OOpportunityAction newOOAct = ManagerUtils.createOOpportunityAction(oAct);
			newOOAct.setId(oActDao.getSequence(con).intValue());
			
			OOpportunityActionInterlocutor newOActInt = null;
			for (OpportunityActionInterlocutor oActInt : oAct.getActionInterlocutors()) {
				newOActInt = ManagerUtils.createOOpportunityActionInterlocutor(oActInt);
				newOActInt.setId(oActIntDao.getSequence(con).intValue());
				newOActInt.setOpportunityActionId(newOOAct.getId());

				oActIntDao.insert(con, newOActInt);
			}

			ArrayList<OOpportunityActionDocument> oDocs = new ArrayList<>();
			
			for (OpportunityActionDocument doc : oAct.getActionDocuments()) {
				if (!(doc instanceof OpportunityActionDocumentWithStream)) throw new IOException("Attachment stream not available [" + doc.getId() + "]");
				oDocs.add(ManagerUtils.doOpportunityActionDocumentInsert(con, newOOAct.getId(), (OpportunityActionDocumentWithStream)doc));
			}

			oActDao.insert(con, newOOAct);

			DbUtils.commitQuietly(con);

			// return newOOAct;
			return newOOAct.getId();

		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");
		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public OOpportunityAction updateOpportunityAction(OpportunityAction item) throws WTException {
		Connection con = null;
		OpportunityActionDAO oActDao = OpportunityActionDAO.getInstance();
		OpportunityActionInterlocutorDAO oActIntDao = OpportunityActionInterlocutorDAO.getInstance();
		OpportunityActionDocumentDAO docDao = OpportunityActionDocumentDAO.getInstance();
		
		try {
			con = WT.getConnection(SERVICE_ID, false);
			
			DateTime revisionTimestamp = createRevisionTimestamp();
			
			OpportunityAction oldOAct = getOpportunityAction(item.getId());

			OOpportunityAction oOAct = ManagerUtils.createOOpportunityAction(item);
			
			//Opportunity Action
			oActDao.update(con, oOAct);
			
			
			//Opportunity Action Interlocutors
			LangUtils.CollectionChangeSet<OpportunityActionInterlocutor> changesSet2 = LangUtils.getCollectionChanges(oldOAct.getActionInterlocutors(), item.getActionInterlocutors());
			
			for (OpportunityActionInterlocutor oActInt : changesSet2.inserted) {

				OOpportunityActionInterlocutor oOActInt = ManagerUtils.createOOpportunityActionInterlocutor(oActInt);

				oOActInt.setId(oActIntDao.getSequence(con).intValue());
				oOActInt.setOpportunityActionId(item.getId());

				oActIntDao.insert(con, oOActInt);
			}

			for (OpportunityActionInterlocutor oActInt : changesSet2.deleted) {
				oActIntDao.deleteById(con, oActInt.getId());
			}

			for (OpportunityActionInterlocutor oActInt : changesSet2.updated) {
				OOpportunityActionInterlocutor oOActInt = ManagerUtils.createOOpportunityActionInterlocutor(oActInt);
				oOActInt.setOpportunityActionId(item.getId());

				oActIntDao.update(con, oOActInt);
			}

			//Opportunity Action Documents
			List<OpportunityActionDocument> oldDocs = ManagerUtils.createOpportunityActionDocumentList(docDao.selectByOpportunityAction(con, item.getId()));
			CollectionChangeSet<OpportunityActionDocument> changeSet = LangUtils.getCollectionChanges(oldDocs, item.getActionDocuments());

			for (OpportunityActionDocument doc : changeSet.inserted) {
				if (!(doc instanceof OpportunityActionDocumentWithStream)) throw new IOException("Attachment stream not available [" + doc.getId()+ "]");
				ManagerUtils.doOpportunityActionDocumentInsert(con, oOAct.getId(), (OpportunityActionDocumentWithStream)doc);
			}
			for (OpportunityActionDocument doc : changeSet.updated) {
				if (!(doc instanceof OpportunityActionDocumentWithStream)) continue;
				ManagerUtils.doOpportunityActionDocumentUpdate(con, (OpportunityActionDocumentWithStream)doc);
			}
			for (OpportunityActionDocument doc : changeSet.deleted) {
				docDao.deleteById(con, doc.getId());
			}

			DbUtils.commitQuietly(con);

			return oOAct;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} catch (IOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public void deleteOpportunityAction(Integer id) throws WTException {
		Connection con = null;
		OpportunityActionDAO oActDao = OpportunityActionDAO.getInstance();
		OpportunityActionInterlocutorDAO oActIntDao = OpportunityActionInterlocutorDAO.getInstance();
		OpportunityActionDocumentDAO oActDocDao = OpportunityActionDocumentDAO.getInstance();
		
		try {
			con = WT.getConnection(SERVICE_ID, false);
			
			oActIntDao.deleteByOpportunityAction(con, id);
			oActDocDao.deleteByOpportunityAction(con, id);
			oActDao.deleteById(con, id);
			
			DbUtils.commitQuietly(con);

		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");
		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public OpportunityDocumentWithBytes getOpportunityDocument(Integer oId, String opportunityDocumentId) throws WTException {
		Connection con = null;
		OpportunityDocumentDAO docDao = OpportunityDocumentDAO.getInstance();
		OOpportunityDocument oDoc = null;
		try {

			con = WT.getConnection(SERVICE_ID);

			oDoc = docDao.select(con, opportunityDocumentId);
			if(oDoc == null) return null;
			
			OOpportunityDocumentsData oDocData = docDao.selectBytesById(con, opportunityDocumentId);
			if(oDocData == null) return null;
			
			return ManagerUtils.fillOpportunityDocument(new OpportunityDocumentWithBytes(oDocData.getBytes()), oDoc);

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public OpportunityActionDocumentWithBytes getOpportunityActionDocument(Integer oAId, String opportunityActionDocumentId) throws WTException {
		Connection con = null;
		OpportunityActionDocumentDAO docDao = OpportunityActionDocumentDAO.getInstance();
		OOpportunityActionDocument oDoc = null;
		try {

			con = WT.getConnection(SERVICE_ID);

			oDoc = docDao.select(con, opportunityActionDocumentId);
			if(oDoc == null) return null;
			
			OOpportunityActionDocumentsData oDocData = docDao.selectBytesById(con, opportunityActionDocumentId);
			if(oDocData == null) return null;
			
			return ManagerUtils.fillOpportunityActionDocument(new OpportunityActionDocumentWithBytes(oDocData.getBytes()), oDoc);

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public Job getJob(String jobId) throws WTException {
		Connection con = null;
		JobDAO jobDao = JobDAO.getInstance();
		JobAttachmentDAO attDao = JobAttachmentDAO.getInstance();
		Job job = null;
		try {

			con = WT.getConnection(SERVICE_ID);

			job = ManagerUtils.createJob(jobDao.selectById(con, jobId));

			for (OJobAttachment oAtt : attDao.selectByJob(con, jobId)) {
				job.getAttachments().add(ManagerUtils.createJobAttachment(oAtt));
			}

			return job;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public WorkReport getWorkReport(String workReportId) throws WTException {
		Connection con = null;
		WorkReportDAO wrkDao = WorkReportDAO.getInstance();
		WorkReportRowDAO wrkDDao = WorkReportRowDAO.getInstance();
		WorkReportAttachmentDAO attDao = WorkReportAttachmentDAO.getInstance();
		WorkReport report = null;
		try {

			con = WT.getConnection(SERVICE_ID);

			report = ManagerUtils.createWorkReport(wrkDao.selectById(con, workReportId));

			for (OWorkReportRow oWrkDetail : wrkDDao.selectByWorkReport(con, workReportId)) {
				report.getDetails().add(ManagerUtils.createWorkReportRow(oWrkDetail));
			}

			for (OWorkReportAttachment oAtt : attDao.selectByWorkReport(con, workReportId)) {
				report.getAttachments().add(ManagerUtils.createWorkReportAttachment(oAtt));
			}

			return report;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public String addWorkReport(WorkReport wrkRpt) throws WTException {

		Connection con = null;

		try {
			con = WT.getConnection(SERVICE_ID, false);

			DateTime revisionTimestamp = createRevisionTimestamp();

			WorkReportDAO wrkDao = WorkReportDAO.getInstance();
			WorkReportRowDAO wrkDDao = WorkReportRowDAO.getInstance();

			OWorkReport newWorkReport = ManagerUtils.createOWorkReport(wrkRpt);
			newWorkReport.setWorkReportId(IdentifierUtils.getUUID());
			newWorkReport.setNumber(wrkDao.getWorkReportSequence(con).intValue());
			newWorkReport.setYear(DateTime.now().getYear());

			OWorkReportRow newWrkDetail = null;
			for (WorkReportRow wrkDetail : wrkRpt.getDetails()) {
				newWrkDetail = ManagerUtils.createOWorkReportRow(wrkDetail);
				newWrkDetail.setId(wrkDDao.getSequence(con).intValue());
				newWrkDetail.setWorkReportId(newWorkReport.getWorkReportId());

				wrkDDao.insert(con, newWrkDetail);
			}
			
			ArrayList<OWorkReportAttachment> oAtts = new ArrayList<>();
			
			for (WorkReportAttachment att : wrkRpt.getAttachments()) {
				if (!(att instanceof WorkReportAttachmentWithStream)) throw new IOException("Attachment stream not available [" + att.getWorkReportAttachmentId()+ "]");
				oAtts.add(ManagerUtils.doWorkReportAttachmentInsert(con, newWorkReport.getWorkReportId(), (WorkReportAttachmentWithStream)att));
			}

			wrkDao.insert(con, newWorkReport, revisionTimestamp);

			DbUtils.commitQuietly(con);

			return newWorkReport.getWorkReportId();
		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");
		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public String addJob(Job job) throws WTException {
		Connection con = null;

		try {
			con = WT.getConnection(SERVICE_ID, false);

			JobDAO jobDao = JobDAO.getInstance();

			OJob newJob = ManagerUtils.createOJob(job);
			newJob.setJobId(IdentifierUtils.getUUID());
			newJob.setNumber(MessageFormat.format("JB{0}{1}", String.valueOf(newJob.getStartDate().getYear()), String.format("%06d", jobDao.getSequence(con).intValue())));
			
			ArrayList<OJobAttachment> oAtts = new ArrayList<>();
			
			for (JobAttachment att : job.getAttachments()) {
				if (!(att instanceof JobAttachmentWithStream)) throw new IOException("Attachment stream not available [" + att.getJobAttachmentId()+ "]");
				oAtts.add(ManagerUtils.doJobAttachmentInsert(con, newJob.getJobId(), (JobAttachmentWithStream)att));
			}

			jobDao.insert(con, newJob);

			DbUtils.commitQuietly(con);

			return newJob.getJobId();
			
		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");
		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public void updateWorkReport(WorkReport item) throws WTException {

		Connection con = null;
		WorkReportDAO wrkDao = WorkReportDAO.getInstance();
		WorkReportRowDAO detailDao = WorkReportRowDAO.getInstance();
		WorkReportAttachmentDAO attDao = WorkReportAttachmentDAO.getInstance();
		try {
			DateTime revisionTimestamp = createRevisionTimestamp();

			WorkReport oldWorkReport = getWorkReport(item.getWorkReportId());

			con = WT.getConnection(SERVICE_ID, false);

			OWorkReport report = ManagerUtils.createOWorkReport(item);

			wrkDao.update(con, report, revisionTimestamp);

			LangUtils.CollectionChangeSet<WorkReportRow> changesSet1 = LangUtils.getCollectionChanges(oldWorkReport.getDetails(), item.getDetails());
			for (WorkReportRow wrkDetail : changesSet1.inserted) {

				OWorkReportRow oWrkDetail = ManagerUtils.createOWorkReportRow(wrkDetail);

				oWrkDetail.setId(detailDao.getSequence(con).intValue());
				oWrkDetail.setWorkReportId(item.getWorkReportId());

				detailDao.insert(con, oWrkDetail);
			}

			for (WorkReportRow wrkDetail : changesSet1.deleted) {
				detailDao.deleteById(con, wrkDetail.getId());
			}

			for (WorkReportRow wrkDetail : changesSet1.updated) {
				OWorkReportRow oWrkDetail = ManagerUtils.createOWorkReportRow(wrkDetail);
				oWrkDetail.setWorkReportId(item.getWorkReportId());

				detailDao.update(con, oWrkDetail);
			}

			List<WorkReportAttachment> oldAtts = ManagerUtils.createWorkReportAttachmentList(attDao.selectByWorkReport(con, item.getWorkReportId()));
			CollectionChangeSet<WorkReportAttachment> changeSet = LangUtils.getCollectionChanges(oldAtts, item.getAttachments());

			for (WorkReportAttachment att : changeSet.inserted) {
				if (!(att instanceof WorkReportAttachmentWithStream)) throw new IOException("Attachment stream not available [" + att.getWorkReportAttachmentId()+ "]");
				ManagerUtils.doWorkReportAttachmentInsert(con, report.getWorkReportId(), (WorkReportAttachmentWithStream)att);
			}
			for (WorkReportAttachment att : changeSet.updated) {
				if (!(att instanceof WorkReportAttachmentWithStream)) continue;
				ManagerUtils.doWorkReportAttachmentUpdate(con, (WorkReportAttachmentWithStream)att);
			}
			for (WorkReportAttachment att : changeSet.deleted) {
				attDao.deleteById(con, att.getWorkReportAttachmentId());
			}

			DbUtils.commitQuietly(con);
		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} catch (IOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public void updateJob(Job item) throws WTException {
		Connection con = null;
		JobDAO jobDao = JobDAO.getInstance();
		JobAttachmentDAO attDao = JobAttachmentDAO.getInstance();
		try {
			con = WT.getConnection(SERVICE_ID, false);

			OJob job = ManagerUtils.createOJob(item);

			jobDao.update(con, job);

			List<JobAttachment> oldAtts = ManagerUtils.createJobAttachmentList(attDao.selectByJob(con, item.getJobId()));
			CollectionChangeSet<JobAttachment> changeSet = LangUtils.getCollectionChanges(oldAtts, item.getAttachments());

			for (JobAttachment att : changeSet.inserted) {
				if (!(att instanceof JobAttachmentWithStream)) throw new IOException("Attachment stream not available [" + att.getJobAttachmentId()+ "]");
				ManagerUtils.doJobAttachmentInsert(con, job.getJobId(), (JobAttachmentWithStream)att);
			}
			for (JobAttachment att : changeSet.updated) {
				if (!(att instanceof JobAttachmentWithStream)) continue;
				ManagerUtils.doJobAttachmentUpdate(con, (JobAttachmentWithStream)att);
			}
			for (JobAttachment att : changeSet.deleted) {
				attDao.deleteById(con, att.getJobAttachmentId());
			}

			DbUtils.commitQuietly(con);
			
		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} catch (IOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public void deleteJob(String jobId) throws WTException {

		Connection con = null;
		JobDAO jobDao = JobDAO.getInstance();

		try {
			con = WT.getConnection(SERVICE_ID, false);

			jobDao.deleteById(con, jobId);

			DbUtils.commitQuietly(con);

		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");
		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public void deleteWorkReport(String workReportId) throws WTException {

		Connection con = null;
		WorkReportDAO wrkDao = WorkReportDAO.getInstance();

		try {
			con = WT.getConnection(SERVICE_ID, false);

			wrkDao.logicalDelete(con, workReportId, createRevisionTimestamp());

			DbUtils.commitQuietly(con);

		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");
		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public WorkReportAttachmentWithBytes getWorkReportAttachment(String workReportId, String workReportAttachmentId) throws WTException {
		Connection con = null;
		WorkReportAttachmentDAO attDao = WorkReportAttachmentDAO.getInstance();
		OWorkReportAttachment oAtt = null;
		try {

			con = WT.getConnection(SERVICE_ID);

			oAtt = attDao.select(con, workReportAttachmentId);
			if(oAtt == null) return null;
			
			OWorkReportsAttachmentsData oAttData = attDao.selectBytesById(con, workReportAttachmentId);
			if(oAttData == null) return null;
			
			return ManagerUtils.fillWorkReportAttachment(new WorkReportAttachmentWithBytes(oAttData.getBytes()), oAtt);

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public List<OOpportunityField> getOpportunitySettingFieldsInGrid()throws WTException, SQLException {
		Connection con = null;
		OpportunityFieldDAO ofDao = OpportunityFieldDAO.getInstance();
		
		try {		
			con = WT.getConnection(SERVICE_ID);
			
			return ofDao.selectFieldIdByDomainIdVisibleShowOnGrid(con, getTargetProfileId().getDomainId());
		} catch (DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public OpportunitySetting getOpportunitySetting() throws WTException {
		Connection con = null;
		OpportunityFieldDAO ofDao = OpportunityFieldDAO.getInstance();

		OpportunitySetting setting = null;
		try {
			con = WT.getConnection(SERVICE_ID);

			setting = new OpportunitySetting();
			
			if (setting != null) {
				for (OOpportunityField oOField : ofDao.selectByDomainIdTabId(con, getTargetProfileId().getDomainId(), EnumUtils.toSerializedName(OpportunityField.Tab.MAIN))) {
					setting.getGeneralFields().add(ManagerUtils.createOpportunityField(oOField));
				}

				for (OOpportunityField oOField : ofDao.selectByDomainIdTabId(con, getTargetProfileId().getDomainId(), EnumUtils.toSerializedName(OpportunityField.Tab.VISIT_REPORT))) {
					setting.getVisitReportFields().add(ManagerUtils.createOpportunityField(oOField));
				}
				
				for (OOpportunityField oOField : ofDao.selectByDomainIdTabId(con, getTargetProfileId().getDomainId(), EnumUtils.toSerializedName(OpportunityField.Tab.NOTES_SIGNATURE))) {
					setting.getNotesSignatureFields().add(ManagerUtils.createOpportunityField(oOField));
				}
			}
			
			return setting;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public void updateOpportunitySetting(OpportunitySetting item) throws WTException {
		Connection con = null;
		OpportunityFieldDAO ofDao = OpportunityFieldDAO.getInstance();

		try {

			con = WT.getConnection(SERVICE_ID, false);

			for (OpportunityField field : item.getGeneralFields()) {

				OOpportunityField oField = ManagerUtils.createOOpportunityField(field);

				ofDao.update(con, oField);
			}
			
			for (OpportunityField field : item.getVisitReportFields()) {

				OOpportunityField oField = ManagerUtils.createOOpportunityField(field);

				ofDao.update(con, oField);
			}
			
			for (OpportunityField field : item.getNotesSignatureFields()) {

				OOpportunityField oField = ManagerUtils.createOOpportunityField(field);

				ofDao.update(con, oField);
			}

			DbUtils.commitQuietly(con);

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public void initializeOpportunityFields() throws WTException {
		Connection con = null;
		OpportunityFieldDAO ofDao = OpportunityFieldDAO.getInstance();
		
		try {
			con = WT.getConnection(SERVICE_ID, false);
			
			ofDao.deleteByDomainId(con, getTargetProfileId().getDomainId());
			ofDao.insertByDomainIdDefault(con, getTargetProfileId().getDomainId());
			
			DbUtils.commitQuietly(con);
			
		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public WorkReportSetting getWorkReportSetting() throws WTException {
		Connection con = null;
		WorkReportSettingDAO wSettDao = WorkReportSettingDAO.getInstance();
		WorkTypeDAO wrkTDao = WorkTypeDAO.getInstance();
		BusinessTripDao tripDao = BusinessTripDao.getInstance();

		WorkReportSetting setting = null;
		try {

			con = WT.getConnection(SERVICE_ID);

			setting = ManagerUtils.createWorkReportSetting(wSettDao.selectByDomainId(con, getTargetProfileId().getDomainId()));
			if (setting != null) {
				setting.setWorkReportSequence(wSettDao.getWorkReportSequence(con).intValue());

				for (OWorkType oType : wrkTDao.selectByDomain(con, getTargetProfileId().getDomainId())) {
					setting.getTypes().add(ManagerUtils.createWorkType(oType));
				}

				for (OBusinessTrip oTrip : tripDao.selectByDomain(con, getTargetProfileId().getDomainId())) {
					setting.getTrips().add(ManagerUtils.createBusinessTrip(oTrip));
				}
			}
			return setting;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public List<OLeaveRequest> listLeaveRequest(LeaveRequestQuery query) throws WTException {
		Connection con = null;
		LeaveRequestDAO lrDao = LeaveRequestDAO.getInstance();
		DrmLineManagerDAO lmDao = DrmLineManagerDAO.getInstance();
		ODrmLineManager oLm = null;
		List<OLeaveRequest> lrs = new ArrayList<>();
		
		try {
			con = WT.getConnection(SERVICE_ID);
			
			oLm = lmDao.selectLineManagerByDomainUserId(con, getTargetProfileId().getDomainId(), query.userId);
			lrs = lrDao.selectLeaveRequests(con, query, getTargetProfileId().getDomainId(), (oLm != null) ? true : false);
			
			if((oLm != null)) lrs.addAll(lrDao.selectLeaveRequestsForManager(con, query, getTargetProfileId().getDomainId()));

			return lrs;
			
		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public LeaveRequest getLeaveRequest(Integer leaveRequestId) throws WTException {
		Connection con = null;
		LeaveRequestDAO lrDao = LeaveRequestDAO.getInstance();
		LeaveRequestDocumentDAO docDao = LeaveRequestDocumentDAO.getInstance();
		LeaveRequest lr = null;
		try {

			con = WT.getConnection(SERVICE_ID);

			lr = ManagerUtils.createLeaveRequest(lrDao.selectById(con, leaveRequestId));

			for (OLeaveRequestDocument oDoc : docDao.selectByLeaveRequest(con, leaveRequestId)) {
				lr.getDocuments().add(ManagerUtils.createLeaveRequestDocument(oDoc));
			}

			return lr;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public void addLeaveRequest(LeaveRequest lv, Boolean medicalVisitsAutomaticallyApproved) throws WTException {
		Connection con = null;
		LeaveRequestDAO lrDao = LeaveRequestDAO.getInstance();
		TimetableEventDAO teDao = TimetableEventDAO.getInstance();
		EmployeeProfileDAO epDao = EmployeeProfileDAO.getInstance();
		HourProfileDAO hpDao = HourProfileDAO.getInstance();
		LineHourDAO lhDao = LineHourDAO.getInstance();
		try {
			con = WT.getConnection(SERVICE_ID, false);

			DateTime employeeReqTimestamp = createRevisionTimestamp();

			OLeaveRequest newLr = ManagerUtils.createOLeaveRequest(lv);
			newLr.setLeaveRequestId(lrDao.getLeaveRequestSequence(con).intValue());
			newLr.setDomainId(getTargetProfileId().getDomainId());
			newLr.setEmployeeReqTimestamp(employeeReqTimestamp);
			
			if(EnumUtils.toSerializedName(OLeaveRequestType.MEDICAL_VISIT).equals(lv.getType()) && medicalVisitsAutomaticallyApproved) {
				newLr.setStatus("C");
				newLr.setResult(true);
			}
			else {
				newLr.setStatus("O");
			}
			
			newLr.setEmployeeCancReq(false);

			ArrayList<OLeaveRequestDocument> oDocs = new ArrayList<>();
			
			for (LeaveRequestDocument doc : lv.getDocuments()) {
				if (!(doc instanceof LeaveRequestDocumentWithStream)) throw new IOException("Attachment stream not available [" + doc.getLeaveRequestDocumentId()+ "]");
				oDocs.add(ManagerUtils.doLeaveRequestDocumentInsert(con, newLr.getLeaveRequestId(), (LeaveRequestDocumentWithStream)doc));
			}

			lrDao.insert(con, newLr, employeeReqTimestamp);

			

			if(EnumUtils.toSerializedName(OLeaveRequestType.MEDICAL_VISIT).equals(lv.getType()) && medicalVisitsAutomaticallyApproved){
				//Insert in TimetableEvents
				List<LocalDate> dts = ManagerUtils.getDateRange(newLr.getFromDate(), newLr.getToDate());

				for(LocalDate ld : dts){
					OTimetableEvent oTe = new OTimetableEvent();
					oTe.setTimetableEventId(teDao.getSequence(con).intValue());
					oTe.setDomainId(newLr.getDomainId());
					oTe.setCompanyId(newLr.getCompanyId());
					oTe.setUserId(newLr.getUserId());
					oTe.setType(newLr.getType());
					oTe.setDate(ld);
					oTe.setLeaveRequestId(newLr.getLeaveRequestId());

					String hRange = null;

					if(newLr.getFromHour() == null || newLr.getToHour() == null){
						//Get Hours from Template
						OEmployeeProfile ep = epDao.selectEmployeeProfileByDomainUser(con, newLr.getDomainId(), newLr.getUserId());

						if(ep != null){
							OHourProfile hp = hpDao.selectHourProfileById(con, ep.getHourProfileId());

							if(hp != null){
								hRange = lhDao.selectSumLineHourByHourProfileIdDayOfWeek(con, hp.getId(), ld.getDayOfWeek());
							}
						}
					}else{
						hRange = ManagerUtils.getHourRange(newLr.getFromHour(), newLr.getToHour());
					}

					if(hRange == null) hRange = "8";

					oTe.setHour(hRange);

					teDao.insert(con, oTe);
					}
			}else{
				notifyLeaveRequest(newLr);
			}
			
			DbUtils.commitQuietly(con);

		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");
		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public OLeaveRequest updateLeaveRequest(LeaveRequest item, boolean sendMail) throws WTException, MessagingException, TemplateException {
		Connection con = null;
		LeaveRequestDAO lrDao = LeaveRequestDAO.getInstance();
		LeaveRequestDocumentDAO docDao = LeaveRequestDocumentDAO.getInstance();
		TimetableEventDAO teDao = TimetableEventDAO.getInstance();
		EmployeeProfileDAO epDao = EmployeeProfileDAO.getInstance();
		HourProfileDAO hpDao = HourProfileDAO.getInstance();
		LineHourDAO lhDao = LineHourDAO.getInstance();
		
		try {
			DateTime revisionTimestamp = createRevisionTimestamp();

			LeaveRequest oldLr = getLeaveRequest(item.getLeaveRequestId());

			con = WT.getConnection(SERVICE_ID, false);

			OLeaveRequest lr = ManagerUtils.createOLeaveRequest(item);
			lr.setEmployeeReqTimestamp(revisionTimestamp);
			if(lr.getEmployeeCancReq()) lr.setEmployeeCancReqTimestamp(revisionTimestamp);
			if(lr.getResult() != null){
				lr.setManagerRespTimestamp(revisionTimestamp);
				lr.setStatus("C");
				
				if(lr.getResult() == true){
					//Insert in TimetableEvents
					List<LocalDate> dts = ManagerUtils.getDateRange(lr.getFromDate(), lr.getToDate());

					for(LocalDate ld : dts){
						OTimetableEvent oTe = new OTimetableEvent();
						oTe.setTimetableEventId(teDao.getSequence(con).intValue());
						oTe.setDomainId(lr.getDomainId());
						oTe.setCompanyId(lr.getCompanyId());
						oTe.setUserId(lr.getUserId());
						oTe.setType(lr.getType());
						oTe.setDate(ld);
						oTe.setLeaveRequestId(lr.getLeaveRequestId());

						String hRange = null;
						
						if(lr.getFromHour() == null || lr.getToHour() == null){
							//Get Hours from Template
							OEmployeeProfile ep = epDao.selectEmployeeProfileByDomainUser(con, lr.getDomainId(), lr.getUserId());
							
							if(ep != null){
								OHourProfile hp = hpDao.selectHourProfileById(con, ep.getHourProfileId());

								if(hp != null){
									hRange = lhDao.selectSumLineHourByHourProfileIdDayOfWeek(con, hp.getId(), ld.getDayOfWeek());
								}
							}
						}else{
							hRange = ManagerUtils.getHourRange(lr.getFromHour(), lr.getToHour());
						}
						
						if(hRange == null) hRange = "8";
						
						oTe.setHour(hRange);
						
						teDao.insert(con, oTe);
					}
				}
			}
			if(lr.getCancResult() != null){
				lr.setManagerCancRespTimetamp(revisionTimestamp);
				if(lr.getCancResult() == true){
					lr.setStatus("D");
				}
			}

			lrDao.update(con, lr);
			
			List<LeaveRequestDocument> oldDocs = ManagerUtils.createLeaveRequestDocumentList(docDao.selectByLeaveRequest(con, item.getLeaveRequestId()));
			CollectionChangeSet<LeaveRequestDocument> changeSet = LangUtils.getCollectionChanges(oldDocs, item.getDocuments());

			for (LeaveRequestDocument doc : changeSet.inserted) {
				if (!(doc instanceof LeaveRequestDocumentWithStream)) throw new IOException("Attachment stream not available [" + doc.getLeaveRequestDocumentId()+ "]");
				ManagerUtils.doLeaveRequestDocumentInsert(con, lr.getLeaveRequestId(), (LeaveRequestDocumentWithStream)doc);
			}
			for (LeaveRequestDocument doc : changeSet.updated) {
				if (!(doc instanceof LeaveRequestDocumentWithStream)) continue;
				ManagerUtils.doLeaveRequestDocumentUpdate(con, (LeaveRequestDocumentWithStream)doc);
			}
			for (LeaveRequestDocument doc : changeSet.deleted) {
				docDao.deleteById(con, doc.getLeaveRequestDocumentId());
			}
			
			DbUtils.commitQuietly(con);
			
			if(sendMail) notifyLeaveRequest(lr);
			
			return lr;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} catch (IOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public void updateCancellationLeaveRequest(int id, Boolean choice) throws WTException, MessagingException, IOException, TemplateException {
		Connection con = null;
		LeaveRequestDAO lrDao = LeaveRequestDAO.getInstance();
		TimetableEventDAO teDao = TimetableEventDAO.getInstance();
		
		try {
			DateTime revisionTimestamp = createRevisionTimestamp();

			con = WT.getConnection(SERVICE_ID, false);

			OLeaveRequest lr = lrDao.selectById(con, id);
			
			lr.setManagerCancRespTimetamp(revisionTimestamp);
			lr.setCancResult(choice);
			if(choice)lr.setStatus("D");
				
			lrDao.update(con, lr);
			
			//Delete in TimetableEvents
			teDao.deleteByLeaveRequestId(con, id);
			
			DbUtils.commitQuietly(con);
			
			notifyLeaveRequestCancellation(lr);
			
		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public void deleteLeaveRequest(Integer leaveRequestId) throws WTException {

		Connection con = null;
		LeaveRequestDAO lrDao = LeaveRequestDAO.getInstance();
		LeaveRequestDocumentDAO docDao = LeaveRequestDocumentDAO.getInstance();
		TimetableEventDAO teDao = TimetableEventDAO.getInstance();
		
		try {
			con = WT.getConnection(SERVICE_ID, false);

			lrDao.deleteById(con, leaveRequestId);

			docDao.deleteByLeaveRequest(con, leaveRequestId);
			
			//Delete in TimetableEvents
			teDao.deleteByLeaveRequestId(con, leaveRequestId);
			
			DbUtils.commitQuietly(con);

		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");
		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public LeaveRequestDocumentWithBytes getLeaveRequestDocument(Integer lrId, String leaveRequestDocumentId) throws WTException {
		Connection con = null;
		LeaveRequestDocumentDAO docDao = LeaveRequestDocumentDAO.getInstance();
		OLeaveRequestDocument oDoc = null;
		try {

			con = WT.getConnection(SERVICE_ID);

			oDoc = docDao.select(con, leaveRequestDocumentId);
			if(oDoc == null) return null;
			
			OLeaveRequestDocumentData oDocData = docDao.selectBytesById(con, leaveRequestDocumentId);
			if(oDocData == null) return null;
			
			return ManagerUtils.fillLeaveRequestDocument(new LeaveRequestDocumentWithBytes(oDocData.getBytes()), oDoc);

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public ExpenseNoteSetting getExpenseNoteSetting() throws WTException {
		Connection con = null;
		ExpenseNoteSettingDAO enSettDao = ExpenseNoteSettingDAO.getInstance().getInstance();
		CostTypeDAO ctDao = CostTypeDAO.getInstance();

		ExpenseNoteSetting setting = null;
		
		try {
			con = WT.getConnection(SERVICE_ID);

			setting = ManagerUtils.createExpenseNoteSetting(enSettDao.selectByDomainId(con, getTargetProfileId().getDomainId()));
			
			if (setting != null) {
				for (OCostType oCt : ctDao.selectByDomain(con, getTargetProfileId().getDomainId())) {
					setting.getCostTypes().add(ManagerUtils.createCostType(oCt));
				}
			}
			return setting;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public void updateExpenseNoteSetting(ExpenseNoteSetting item) throws WTException {

		Connection con = null;
		ExpenseNoteSettingDAO enSettDao = ExpenseNoteSettingDAO.getInstance().getInstance();
		CostTypeDAO ctDao = CostTypeDAO.getInstance();

		try {
			con = WT.getConnection(SERVICE_ID, false);

			ExpenseNoteSetting oldExpenseNoteSetting = getExpenseNoteSetting();

			OExpenseNoteSetting setting = ManagerUtils.createOExpenseNoteSetting(item);

			if (oldExpenseNoteSetting == null) {

				setting.setExpenseNoteSettingId(enSettDao.getSequence(con).intValue());
				setting.setDomainId(getTargetProfileId().getDomainId());

				enSettDao.insert(con, setting);

			} else {
				enSettDao.update(con, setting);
			}

			List<CostType> cts = oldExpenseNoteSetting == null ? new ArrayList() : oldExpenseNoteSetting.getCostTypes();
			
			CollectionChangeSet<CostType> changesSet1 = getCollectionChanges(cts, item.getCostTypes());
			for (CostType ct : changesSet1.inserted) {

				OCostType oCt = ManagerUtils.createOCostType(ct);
				oCt.setDomainId(getTargetProfileId().getDomainId());
				oCt.setId(ctDao.getSequence(con).intValue());

				ctDao.insert(con, oCt);
			}

			for (CostType ct : changesSet1.deleted) {
				ctDao.deleteByIdDomainId(con, ct.getId(), ct.getDomainId());
			}

			for (CostType ct : changesSet1.updated) {

				OCostType oCt = ManagerUtils.createOCostType(ct);
				OCostType newOCt = null;
				
				newOCt = ctDao.selectByIdDomainId(con, oCt.getId(), oCt.getDomainId());

				if(newOCt != null) ctDao.updateByIdDomainId(con, oCt);
			}

			DbUtils.commitQuietly(con);

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public TimetableSetting getTimetableSetting() throws WTException {
		Connection con = null;
		TimetableSettingDAO tSettDao = TimetableSettingDAO.getInstance();
		HolidayDateDAO hdDao = HolidayDateDAO.getInstance();

		TimetableSetting setting = null;
		
		try {
			con = WT.getConnection(SERVICE_ID);

			setting = ManagerUtils.createTimetableSetting(tSettDao.selectByDomainId(con, getTargetProfileId().getDomainId()));
			
			if (setting != null) {
				for (OHolidayDate oHd : hdDao.selectByDomain(con, getTargetProfileId().getDomainId())) {
					setting.getHolidayDates().add(ManagerUtils.createHolidayDate(oHd));
				}
			}
			return setting;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public ArrayList<ODay> generateDaysFromRange(LocalDate fromDate, LocalDate toDate) throws WTException {
		Connection con = null;
		UtilityDAO uDao = UtilityDAO.getInstance();
		Result<Record> result = null;
		ArrayList<ODay> days = new ArrayList();
		ODay day;
		
		try {
			con = WT.getConnection(SERVICE_ID);

			result = uDao.selectDaysByDateRange(con, fromDate, toDate);
			
			for(Record r : result){
				day = new ODay(r.getValue(0, String.class), r.getValue(1, Long.class));
				
				days.add(day);
			}
			
			return days;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public void updateTimetableSetting(TimetableSetting item) throws WTException {

		Connection con = null;
		TimetableSettingDAO tDao = TimetableSettingDAO.getInstance();
		HolidayDateDAO hdDao = HolidayDateDAO.getInstance();

		try {
			con = WT.getConnection(SERVICE_ID, false);

			TimetableSetting oldTimetableSetting = getTimetableSetting();

			OTimetableSetting setting = ManagerUtils.createOTimetableSetting(item);

			if (oldTimetableSetting == null) {

				setting.setTimetableSettingId(tDao.getSequence(con).intValue());
				setting.setDomainId(getTargetProfileId().getDomainId());

				tDao.insert(con, setting);

			} else {
				tDao.update(con, setting);
			}

			List<HolidayDate> hds = oldTimetableSetting == null ? new ArrayList() : oldTimetableSetting.getHolidayDates();
			
			CollectionChangeSet<HolidayDate> changesSet1 = getCollectionChanges(hds, item.getHolidayDates());
			for (HolidayDate hd : changesSet1.inserted) {

				OHolidayDate oHd = ManagerUtils.createOHolidayDate(hd);
				oHd.setDomainId(getTargetProfileId().getDomainId());

				hdDao.insert(con, oHd);
			}

			for (HolidayDate hd : changesSet1.deleted) {
				hdDao.deleteByDomainIdDate(con, hd.getDomainId(), hd.getDate().toDateTimeAtStartOfDay());
			}

			for (HolidayDate hd : changesSet1.updated) {

				OHolidayDate oHd = ManagerUtils.createOHolidayDate(hd);
				OHolidayDate newOHd = null;
				
				newOHd = hdDao.selectByDomainDate(con, oHd.getDomainId(), DateTime.parse(oHd.getDate().toString()));

				if(newOHd != null) hdDao.updateByDomainIdDate(con, oHd);
				else hdDao.updateByDomainId(con, oHd);
			}

			DbUtils.commitQuietly(con);

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public OWorkReportSetting addWorkReportSetting(WorkReportSetting wrkSett) throws WTException {
		Connection con = null;
		try {
			con = WT.getConnection(SERVICE_ID, false);

			WorkReportSettingDAO settingDao = WorkReportSettingDAO.getInstance();
			WorkTypeDAO typeDao = WorkTypeDAO.getInstance();
			BusinessTripDao tripDao = BusinessTripDao.getInstance();

			OWorkReportSetting newWorkSetting = ManagerUtils.createOWorkReportSetting(wrkSett);
			newWorkSetting.setWorkReportSettingId(settingDao.getSequence(con).intValue());
			newWorkSetting.setDomainId(getTargetProfileId().getDomainId());

			OWorkType newWrkType = null;
			for (WorkType t : wrkSett.getTypes()) {
				newWrkType = ManagerUtils.createOWorkType(t);
				newWrkType.setWorkTypeId(typeDao.getSequence(con).intValue());
				newWrkType.setDomainId(wrkSett.getDomainId());

				typeDao.insert(con, newWrkType);
			}

			OBusinessTrip newBusinessTrip = null;
			for (BusinessTrip t : wrkSett.getTrips()) {
				newBusinessTrip = ManagerUtils.createOBusinessTrip(t);
				newBusinessTrip.setBusinessTripId(typeDao.getSequence(con).intValue());
				newBusinessTrip.setDomainId(wrkSett.getDomainId());

				tripDao.insert(con, newBusinessTrip);
			}

			settingDao.insert(con, newWorkSetting);
			settingDao.setWorkReportSequence(con, wrkSett.getWorkReportSequence());

			DbUtils.commitQuietly(con);

			return newWorkSetting;

		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");
		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public void updateWorkReportSetting(WorkReportSetting item) throws WTException {

		Connection con = null;
		WorkReportSettingDAO wkrSDao = WorkReportSettingDAO.getInstance();
		WorkTypeDAO typeDao = WorkTypeDAO.getInstance();
		BusinessTripDao tripDao = BusinessTripDao.getInstance();

		try {

			con = WT.getConnection(SERVICE_ID, false);

			WorkReportSetting oldWorkReportSetting = getWorkReportSetting();

			OWorkReportSetting setting = ManagerUtils.createOWorkReportSetting(item);

			if (oldWorkReportSetting == null) {

				setting.setWorkReportSettingId(wkrSDao.getSequence(con).intValue());
				setting.setDomainId(getTargetProfileId().getDomainId());

				wkrSDao.insert(con, setting);

			} else {
				wkrSDao.update(con, setting);
			}

			if(null == item.getWorkReportSequence()){
				Long value = wkrSDao.getWorkReportSequence(con);
				wkrSDao.setWorkReportSequence(con, value.intValue());
			}else{
				wkrSDao.setWorkReportSequence(con, item.getWorkReportSequence());
			}

			List<WorkType> types = oldWorkReportSetting == null ? new ArrayList() : oldWorkReportSetting.getTypes();

			CollectionChangeSet<WorkType> changesSet1 = getCollectionChanges(types, item.getTypes());

			for (WorkType type : changesSet1.inserted) {

				OWorkType oType = ManagerUtils.createOWorkType(type);

				oType.setWorkTypeId(typeDao.getSequence(con).intValue());
				oType.setDomainId(getTargetProfileId().getDomainId());

				typeDao.insert(con, oType);
			}

			for (WorkType type : changesSet1.deleted) {
				typeDao.deleteById(con, type.getWorkTypeId());
			}

			for (WorkType type : changesSet1.updated) {

				OWorkType oType = ManagerUtils.createOWorkType(type);

				typeDao.update(con, oType);
			}

			List<BusinessTrip> trips = oldWorkReportSetting == null ? new ArrayList() : oldWorkReportSetting.getTrips();

			CollectionChangeSet<BusinessTrip> changesSet2 = getCollectionChanges(trips, item.getTrips());

			for (BusinessTrip trip : changesSet2.inserted) {

				OBusinessTrip oTrip = ManagerUtils.createOBusinessTrip(trip);

				oTrip.setBusinessTripId(typeDao.getSequence(con).intValue());
				oTrip.setDomainId(getTargetProfileId().getDomainId());

				tripDao.insert(con, oTrip);
			}

			for (BusinessTrip trip : changesSet2.deleted) {
				tripDao.deleteById(con, trip.getBusinessTripId());
			}

			for (BusinessTrip trip : changesSet2.updated) {

				OBusinessTrip oTrip = ManagerUtils.createOBusinessTrip(trip);

				tripDao.update(con, oTrip);
			}

			DbUtils.commitQuietly(con);

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public List<OWorkType> listWorkType() throws WTException {
		Connection con = null;
		WorkTypeDAO wrkDao = WorkTypeDAO.getInstance();
		List<OWorkType> workTypes = null;
		try {

			con = WT.getConnection(SERVICE_ID);

			workTypes = wrkDao.selectByDomain(con, getTargetProfileId().getDomainId());

			return workTypes;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public List<OBusinessTrip> listBusinessTrip() throws WTException {
		Connection con = null;
		BusinessTripDao tripDao = BusinessTripDao.getInstance();
		List<OBusinessTrip> trips = null;
		try {

			con = WT.getConnection(SERVICE_ID);

			trips = tripDao.selectByDomain(con, getTargetProfileId().getDomainId());

			return trips;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public OBusinessTrip getBusinessTripById(Integer businessTripId) throws WTException {
		Connection con = null;
		BusinessTripDao tripDao = BusinessTripDao.getInstance();
		OBusinessTrip trip = null;
		try {

			con = WT.getConnection(SERVICE_ID);

			trip = tripDao.selectById(con, getTargetProfileId().getDomainId(), businessTripId);

			return trip;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	List<String> listOperators() throws WTException {
		Connection con = null;
		com.sonicle.webtop.drm.dal.UserDAO userDao = com.sonicle.webtop.drm.dal.UserDAO.getInstance();
		List<String> users = null;
		try {			
			con = WT.getConnection(SERVICE_ID);
			users = userDao.selectUserSupervisedByDomain(con, getTargetProfileId().getDomainId(), getTargetProfileId().getUserId());
			users.add(0, getTargetProfileId().getUserId());
			
			return users;
		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	List<String> listManagersByDomainUser(String userId) throws WTException {
		Connection con = null;
		DrmUserForManagerDAO lmDao = DrmUserForManagerDAO.getInstance();
		List<String> managers = null;
		
		try {			
			con = WT.getConnection(SERVICE_ID);
			managers = lmDao.selectManagerByDomainUserId(con, getTargetProfileId().getDomainId(), userId);
			
			return managers;
		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	List<String> listCustomersByProfileUser() throws WTException {
		Connection con = null;
		ProfileMasterdataDAO pmDao = ProfileMasterdataDAO.getInstance();
		List<String> idCustomers = null;
		try {			
			con = WT.getConnection(SERVICE_ID);
			idCustomers = pmDao.selectCustomersByProfileUser(con, getTargetProfileId().getUserId());
			
			return idCustomers;
		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public ExpenseNote getExpenseNote(Integer id) throws WTException {
		Connection con = null;
		ExpenseNoteDAO eNDAO = ExpenseNoteDAO.getInstance();
		ExpenseNoteDetailDAO eNDetDAO = ExpenseNoteDetailDAO.getInstance();
		ExpenseNoteDocumentDAO eNDocDao = ExpenseNoteDocumentDAO.getInstance();
		
		ExpenseNote eN = null;
		
		try {

			con = WT.getConnection(SERVICE_ID);

			eN = ManagerUtils.createExpenseNote(eNDAO.selectById(con, id));
			
			for (OExpenseNoteDetail oEnD : eNDetDAO.selectByExpenseNote(con, id)) {
				eN.getDetails().add(ManagerUtils.createExpenseNoteDetail(oEnD));
			}
			
			for (OExpenseNoteDocument eNDoc : eNDocDao.selectByExpenseNote(con, id)) {
				eN.getDocuments().add(ManagerUtils.createExpenseNoteDocument(eNDoc));
			}

			return eN;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public OExpenseNote addExpenseNote(ExpenseNote eN) throws WTException {
		Connection con = null;
		ExpenseNoteDAO eNDAO = ExpenseNoteDAO.getInstance();
		ExpenseNoteDetailDAO eNDetDAO = ExpenseNoteDetailDAO.getInstance();
		
		try {
			con = WT.getConnection(SERVICE_ID, false);

			OExpenseNote newEn = ManagerUtils.createOExpenseNote(eN);
			newEn.setId(eNDAO.getExpenseNoteSequence(con).intValue());
			newEn.setDomainId(getTargetProfileId().getDomainId());
			
			OExpenseNoteDetail newENDet = null;
			for (ExpenseNoteDetail eNDet : eN.getDetails()) {
				newENDet = ManagerUtils.createOExpenseNoteDetail(eNDet);
				newENDet.setId(eNDetDAO.getSequence(con).intValue());
				newENDet.setExpenseNoteId(newEn.getId());

				if (newENDet.getDescription() !=null && newENDet.getDescription().trim().length() > 0)
					WT.getCoreManager().addServiceStoreEntry(SERVICE_ID, "expenseNoteDescription", newENDet.getDescription().toUpperCase(), newENDet.getDescription());
				
				eNDetDAO.insert(con, newENDet);
			}

			ArrayList<OExpenseNoteDocument> eNDocs = new ArrayList<>();
			
			for (ExpenseNoteDocument doc : eN.getDocuments()) {
				if (!(doc instanceof ExpenseNoteDocumentWithStream)) throw new IOException("Attachment stream not available [" + doc.getId() + "]");
				eNDocs.add(ManagerUtils.doExpenseNoteDocumentInsert(con, newEn.getId(), (ExpenseNoteDocumentWithStream)doc));
			}

			eNDAO.insert(con, newEn);

			DbUtils.commitQuietly(con);

			return newEn;

		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");
		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public OExpenseNote updateExpenseNote(ExpenseNote item) throws WTException {
		Connection con = null;
		ExpenseNoteDAO eNDAO = ExpenseNoteDAO.getInstance();
		ExpenseNoteDetailDAO eNDetDAO = ExpenseNoteDetailDAO.getInstance();
		ExpenseNoteDocumentDAO eNDocDao = ExpenseNoteDocumentDAO.getInstance();
		
		try {
			con = WT.getConnection(SERVICE_ID, false);
			
			ExpenseNote oldEN = getExpenseNote(item.getId());

			OExpenseNote oEN = ManagerUtils.createOExpenseNote(item);
			
			//Expense Note
			eNDAO.update(con, oEN);
			
			//Expense Note Detail
			LangUtils.CollectionChangeSet<ExpenseNoteDetail> changesSet2 = LangUtils.getCollectionChanges(oldEN.getDetails(), item.getDetails());
			
			for (ExpenseNoteDetail eNDet : changesSet2.inserted) {

				OExpenseNoteDetail oENDet = ManagerUtils.createOExpenseNoteDetail(eNDet);

				oENDet.setId(eNDetDAO.getSequence(con).intValue());
				oENDet.setExpenseNoteId(item.getId());

				if (eNDet.getDescription() !=null && eNDet.getDescription().trim().length() > 0)
					WT.getCoreManager().addServiceStoreEntry(SERVICE_ID, "expenseNoteDescription", eNDet.getDescription().toUpperCase(), eNDet.getDescription());
				
				eNDetDAO.insert(con, oENDet);
			}

			for (ExpenseNoteDetail eNDet : changesSet2.deleted) {
				eNDetDAO.deleteById(con, eNDet.getId());
			}

			for (ExpenseNoteDetail eNDet : changesSet2.updated) {
				OExpenseNoteDetail oENDet = ManagerUtils.createOExpenseNoteDetail(eNDet);
				oENDet.setExpenseNoteId(item.getId());

				if (eNDet.getDescription() !=null && eNDet.getDescription().trim().length() > 0)
					WT.getCoreManager().addServiceStoreEntry(SERVICE_ID, "expenseNoteDescription", eNDet.getDescription().toUpperCase(), eNDet.getDescription());
				
				eNDetDAO.update(con, oENDet);
			}

			//Expense Note Document
			List<ExpenseNoteDocument> oldDocs = ManagerUtils.createExpenseNoteDocumentList(eNDocDao.selectByExpenseNote(con, item.getId()));
			CollectionChangeSet<ExpenseNoteDocument> changeSet = LangUtils.getCollectionChanges(oldDocs, item.getDocuments());

			for (ExpenseNoteDocument doc : changeSet.inserted) {
				if (!(doc instanceof ExpenseNoteDocumentWithStream)) throw new IOException("Attachment stream not available [" + doc.getId()+ "]");
				ManagerUtils.doExpenseNoteDocumentInsert(con, oEN.getId(), (ExpenseNoteDocumentWithStream)doc);
			}
			for (ExpenseNoteDocument doc : changeSet.updated) {
				if (!(doc instanceof ExpenseNoteDocumentWithStream)) continue;
				ManagerUtils.doExpenseNoteDocumentUpdate(con, (ExpenseNoteDocumentWithStream)doc);
			}
			for (ExpenseNoteDocument doc : changeSet.deleted) {
				eNDocDao.deleteById(con, doc.getId());
			}

			DbUtils.commitQuietly(con);

			return oEN;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} catch (IOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public void deleteExpenseNote(Integer id) throws WTException {
		Connection con = null;
		ExpenseNoteDAO eNDAO = ExpenseNoteDAO.getInstance();
		ExpenseNoteDetailDAO eNDetDAO = ExpenseNoteDetailDAO.getInstance();
		ExpenseNoteDocumentDAO eNDocDao = ExpenseNoteDocumentDAO.getInstance();
		ExpenseNoteDetailDocumentDAO eNDetDocDao = ExpenseNoteDetailDocumentDAO.getInstance();
		
		try {
			con = WT.getConnection(SERVICE_ID, false);
			
			for(OExpenseNoteDetail oENDet : eNDetDAO.selectByExpenseNote(con, id)){
				eNDetDAO.deleteById(con, oENDet.getId());
				eNDetDocDao.deleteByExpenseNoteDetail(con, oENDet.getId());
			}
			
			eNDocDao.deleteByExpenseNote(con, id);
			eNDAO.deleteById(con, id);
			
			DbUtils.commitQuietly(con);

		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");
		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public ExpenseNoteDocumentWithBytes getExpenseNoteDocument(Integer eNId, String expenseNoteDocumentId) throws WTException {
		Connection con = null;
		ExpenseNoteDocumentDAO docDao = ExpenseNoteDocumentDAO.getInstance();
		OExpenseNoteDocument oENDoc = null;
		try {

			con = WT.getConnection(SERVICE_ID);

			oENDoc = docDao.select(con, expenseNoteDocumentId);
			if(oENDoc == null) return null;
			
			OExpenseNoteDocumentData oDocData = docDao.selectBytesById(con, expenseNoteDocumentId);
			if(oDocData == null) return null;
			
			return ManagerUtils.fillExpenseNoteDocument(new ExpenseNoteDocumentWithBytes(oDocData.getBytes()), oENDoc);

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public ExpenseNoteDetailDocumentWithBytes getExpenseNoteDetailDocument(Integer eNDDId, String expenseNoteDetailDocumentId) throws WTException {
		Connection con = null;
		ExpenseNoteDetailDocumentDAO docDao = ExpenseNoteDetailDocumentDAO.getInstance();
		OExpenseNoteDetailDocument oDoc = null;
		try {

			con = WT.getConnection(SERVICE_ID);

			oDoc = docDao.select(con, expenseNoteDetailDocumentId);
			if(oDoc == null) return null;
			
			OExpenseNoteDetailDocumentData oDocData = docDao.selectBytesById(con, expenseNoteDetailDocumentId);
			if(oDocData == null) return null;
			
			return ManagerUtils.fillExpenseNoteDetailDocument(new ExpenseNoteDetailDocumentWithBytes(oDocData.getBytes()), oDoc);

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	boolean checkCustomersByProfileUser(String realCustomerId) throws WTException {
		Connection con = null;
		ProfileMasterdataDAO pmDao = ProfileMasterdataDAO.getInstance();
		String opmId = null;
		try {			
			con = WT.getConnection(SERVICE_ID);
			opmId = pmDao.checkCustomersByProfileUser(con, realCustomerId, getTargetProfileId().getUserId());
			
			return (opmId == null) ? false : true;
		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public CompanyPicture getCompanyPicture(int companyId) throws WTException {
		CompanyDAO cmpDao = CompanyDAO.getInstance();
		CompanyPictureDAO cpicDao = CompanyPictureDAO.getInstance();
		Connection con = null;
		
		try {
			con = WT.getConnection(SERVICE_ID);
			
			OCompany cmp = cmpDao.selectById(con, companyId);
			if(cmp == null) throw new WTException("Unable to retrieve company [{0}]", companyId);
			
			OCompanyPicture pic = cpicDao.select(con, companyId);
			return ManagerUtils.createCompanyPicture(pic);
			
		} catch(SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} catch(Throwable t) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(t);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public void updateCompanyPicture(int companyId, CompanyPicture picture) throws WTException {
		CompanyDAO cmpDao = CompanyDAO.getInstance();
		Connection con = null;
		
		try {
			con = WT.getConnection(SERVICE_ID, false);
			
			if (picture == null) throw new WTException("Specified picture is null");
			OCompany ocmp = cmpDao.selectById(con, companyId);
			if (ocmp == null) throw new WTException("Unable to retrieve company [{0}]", companyId);
			
			doUpdateCompanyPicture(con, companyId, picture);
			DbUtils.commitQuietly(con);
			
		} catch(SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");
		} catch(Throwable t) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(t);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public void doUpdateCompanyPicture(Connection con, int companyId, CompanyPicture picture) throws IOException, DAOException {
		doDeleteCompanyPicture(con, companyId);
		doInsertCompanyPicture(con, companyId, picture);
	}
	
	private void doDeleteCompanyPicture(Connection con, int companyId) throws DAOException {
		CompanyPictureDAO cpicDao = CompanyPictureDAO.getInstance();
		cpicDao.delete(con, companyId);
	}
	
	private void doInsertCompanyPicture(Connection con, int companyId, CompanyPicture picture) throws IOException, DAOException {
		CompanyPictureDAO cpicDao = CompanyPictureDAO.getInstance();
		
		OCompanyPicture ocpic = new OCompanyPicture();
		ocpic.setCompanyId(companyId);
		ocpic.setMediaType(picture.getMediaType());

		BufferedImage bi = ImageIO.read(new ByteArrayInputStream(picture.getBytes()));
		if((bi.getWidth() > 720) || (bi.getHeight() > 720)) {
			bi = Scalr.resize(bi, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC, 720);
			ocpic.setWidth(bi.getWidth());
			ocpic.setHeight(bi.getHeight());
			String formatName = new MimeType(picture.getMediaType()).getSubType();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				ImageIO.write(bi, formatName, baos);
				baos.flush();
				ocpic.setBytes(baos.toByteArray());
			} catch(IOException ex1) {
				logger.warn("Error resizing image", ex1);
			} finally {
				IOUtils.closeQuietly(baos);
			}
		} else {
			ocpic.setWidth(bi.getWidth());
			ocpic.setHeight(bi.getHeight());
			ocpic.setBytes(picture.getBytes());
		}
		cpicDao.insert(con, ocpic);
	}

	public Iterable<ODrmLineManager> listLineManagers() throws WTException {
		Connection con = null;
		DrmLineManagerDAO mngDao = DrmLineManagerDAO.getInstance();
		List<ODrmLineManager> managers = null;
		try {

			con = WT.getConnection(SERVICE_ID);

			managers = mngDao.selectLineManagerByDomain(con, getTargetProfileId().getDomainId());

			return managers;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public void deleteDrmLineManager(String domainId, String userId) throws WTException {
		Connection con = null;
		DrmLineManagerDAO mngDao = DrmLineManagerDAO.getInstance();
		DrmUserForManagerDAO ufmDao = DrmUserForManagerDAO.getInstance();

		try {
			con = WT.getConnection(SERVICE_ID, false);

			mngDao.deleteByDomainIdUserId(con,domainId, userId);

			ufmDao.deleteByDomainIdLineManagerUserId(con, domainId, userId);

			DbUtils.commitQuietly(con);

		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");
		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public DrmLineManager getDrmLineManager(String userId) throws WTException {
		Connection con = null;
		DrmLineManagerDAO mngDao = DrmLineManagerDAO.getInstance();
		DrmUserForManagerDAO ufmngDao = DrmUserForManagerDAO.getInstance();

		DrmLineManager manager = null;
		try {

			con = WT.getConnection(SERVICE_ID);

			manager = ManagerUtils.createLineManager(mngDao.selectLineManagerByDomainUserId(con, getTargetProfileId().getDomainId(), userId));

			for (ODrmLineManagerUsers olmu : ufmngDao.listByDomainLineManagerUserId(con, getTargetProfileId().getDomainId(), userId)) {
				manager.getAssociatedUsers().add(ManagerUtils.createUserForManager(olmu));
			}

			return manager;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public ODrmLineManager addDrmLineManager(DrmLineManager lineManager) throws WTException {
		Connection con = null;

		try {
			con = WT.getConnection(SERVICE_ID, false);

			DrmLineManagerDAO mngDao = DrmLineManagerDAO.getInstance();
			DrmUserForManagerDAO ufmngDao = DrmUserForManagerDAO.getInstance();

			ODrmLineManager newDrmLineManager = ManagerUtils.createODrmLineManager(lineManager);
			newDrmLineManager.setDomainId(getTargetProfileId().getDomainId());

			ODrmLineManagerUsers newLmu = null;
			
			for (UserForManager ufm : lineManager.getAssociatedUsers()) {
				ufm.setManagerUserId(newDrmLineManager.getUserId());
				ufm.setDomainId(getTargetProfileId().getDomainId());
				newLmu = ManagerUtils.createODrmLineManagerUser(ufm);

				ufmngDao.insert(con, newLmu);
			}

			mngDao.insert(con, newDrmLineManager);

			DbUtils.commitQuietly(con);

			return newDrmLineManager;

		} catch (SQLException | DAOException ex) {

			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");

		} catch (Exception ex) {

			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);

		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public ODrmLineManager updateDrmLineManager(DrmLineManager item) throws WTException {
		Connection con = null;
		
		try {
			DrmLineManager oldDrmLineManage = getDrmLineManager(item.getUserId());
			DrmUserForManagerDAO ufmngDao = DrmUserForManagerDAO.getInstance();
			ODrmLineManager manager = ManagerUtils.createODrmLineManager(item);
			
			con = WT.getConnection(SERVICE_ID, false);
			
			LangUtils.CollectionChangeSet<UserForManager> changesSet1 = LangUtils.getCollectionChanges(oldDrmLineManage.getAssociatedUsers(), item.getAssociatedUsers());
			for (UserForManager ufm : changesSet1.inserted) {
				final ODrmLineManagerUsers oufm = ManagerUtils.fillODrmLineManagerUsers(new ODrmLineManagerUsers(), ufm);
				oufm.setDomainId(getTargetProfileId().getDomainId());
				oufm.setLineManagerUserId(item.getUserId());
				ufmngDao.insert(con, oufm);
			}

			for (UserForManager ufm : changesSet1.deleted) {
				ufmngDao.deleteByDomainIdLineManagerUserIdUserId(con, ufm.getDomainId(), ufm.getManagerUserId(), ufm.getUserId());
			}

			DbUtils.commitQuietly(con);

			return manager;

		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");

		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public void deleteDrmLineManager(String userId) throws WTException {
		Connection con = null;
		
		try {
			con = WT.getConnection(SERVICE_ID, false);
			
			DrmLineManagerDAO mngDao = DrmLineManagerDAO.getInstance();
			DrmUserForManagerDAO ufmngDao = DrmUserForManagerDAO.getInstance();

			mngDao.deleteByDomainIdUserId(con, getTargetProfileId().getDomainId(), userId);

			ufmngDao.deleteByDomainIdLineManagerUserId(con, getTargetProfileId().getDomainId(), userId);

			DbUtils.commitQuietly(con);

		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");
		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public EmployeeProfile getEmployeeProfile(Integer id) throws WTException {
		Connection con = null;
		EmployeeProfileDAO epDao =EmployeeProfileDAO.getInstance();
		LineHourDAO ehDao = LineHourDAO.getInstance();

		EmployeeProfile employeeProfile = null;
		try {

			con = WT.getConnection(SERVICE_ID);

			employeeProfile = ManagerUtils.createEmployeeProfile(epDao.selectEmployeeProfileById(con, id));

			return employeeProfile;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public HourProfile getHourProfile(Integer id) throws WTException {
		Connection con = null;
		HourProfileDAO hpDao = HourProfileDAO.getInstance();
		LineHourDAO lhDao = LineHourDAO.getInstance();

		HourProfile hProfile = null;
		try {

			con = WT.getConnection(SERVICE_ID);

			hProfile = ManagerUtils.createHourProfile(hpDao.selectHourProfileById(con, id));

			for (OLineHour oEH : lhDao.selectLineHourByHourProfileId(con, id)) {
				hProfile.getLineHours().add(ManagerUtils.createLineHour(oEH));
			}

			return hProfile;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public OTimetableStamp setTimetable(TimetableStamp stamp) throws WTException {

		Connection con = null;
		List<OTimetableStamp> stamps = null;
		boolean chek = false;
		
		try {
			con = WT.getConnection(SERVICE_ID, false);

			TimetableStampDAO tsDao = TimetableStampDAO.getInstance();

			OTimetableStamp oTS = ManagerUtils.createOTimetableStamp(stamp);
			
			//Check stamps, if not exixt insert a new object, else check exit
			stamps = tsDao.getDailyStampsByDomainUserIdType(con, getTargetProfileId().getDomainId(), getTargetProfileId().getUserId(), oTS.getType());
			
			if(stamps.size() > 0){
				for(OTimetableStamp ts : stamps){
					if(ts.getExit() == null){
						ts.setExit(new DateTime());
						
						tsDao.update(con, ts);
						chek = true;
						
						break;
					}
				}
				if(!chek){
					oTS.setId(tsDao.getSequence(con).intValue());
					oTS.setDomainId(getTargetProfileId().getDomainId());
					oTS.setUserId(getTargetProfileId().getUserId());
					oTS.setEntrance(new DateTime());

					tsDao.insert(con, oTS);
				}
			}else{
				oTS.setId(tsDao.getSequence(con).intValue());
				oTS.setDomainId(getTargetProfileId().getDomainId());
				oTS.setUserId(getTargetProfileId().getUserId());
				oTS.setEntrance(new DateTime());
				
				tsDao.insert(con, oTS);
			}			

			DbUtils.commitQuietly(con);

			return oTS;

		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");
		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public CostType getCostType(Integer typeId) throws WTException {
		Connection con = null;
		CostTypeDAO ctDao = CostTypeDAO.getInstance();
		CostType ct = new CostType();
		
		try {

			con = WT.getConnection(SERVICE_ID);

			
			ct = createCostType(ctDao.selectByIdDomainId(con, typeId, getTargetProfileId().getDomainId()));

			return ct;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public List<CostType> listCostTypes() throws WTException {
		Connection con = null;
		CostTypeDAO ctDao = CostTypeDAO.getInstance();
		List<CostType> cts = new ArrayList();
		
		try {

			con = WT.getConnection(SERVICE_ID);

			for (OCostType oCt : ctDao.selectByDomain(con, getTargetProfileId().getDomainId())) {
				cts.add(ManagerUtils.createCostType(oCt));
			}

			return cts;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public List<TimetableStamp> listTimetableStamp() throws WTException {
		Connection con = null;
		TimetableStampDAO tsDao = TimetableStampDAO.getInstance();
		List<TimetableStamp> tss = null;
		
		try {

			con = WT.getConnection(SERVICE_ID);

			// tss = tsDao.getDailyStampsByDomainUserId(con, getTargetProfileId().getDomainId(), getTargetProfileId().getUserId());
			tss = new ArrayList<>();
			for (OTimetableStamp ots : tsDao.getDailyStampsByDomainUserId(con, getTargetProfileId().getDomainId(), getTargetProfileId().getUserId())) {
				tss.add(ManagerUtils.fillTimetableStamp(new TimetableStamp(), ots));
			}
			
			return tss;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public List<TimetableStamp> listTimetableStamps(TimetableStampQuery query) throws WTException {
		Connection con = null;
		TimetableStampDAO tsDao = TimetableStampDAO.getInstance();
		List<TimetableStamp> tss = null;
		
		try {

			con = WT.getConnection(SERVICE_ID);
			
			query.operatorId = query.operatorId == null ? getTargetProfileId().getUserId() : query.operatorId;
			query.month = query.month == null ? new DateTime().getMonthOfYear() : query.month;
			query.year = query.year == null ? new DateTime().getYear() : query.year;
			
			// tss = tsDao.getStampsListByDomainUserDateRange(con, getTargetProfileId().getDomainId(), query.operatorId, query.month, query.year);
			tss = new ArrayList<>();
			for (OTimetableStamp ots : tsDao.getStampsListByDomainUserDateRange(con, getTargetProfileId().getDomainId(), query.operatorId, query.month, query.year)) {
				tss.add(ManagerUtils.fillTimetableStamp(new TimetableStamp(), ots));
			}

			return tss;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public OTimetableStamp addTimetableStamp(TimetableStamp ts) throws WTException {
		Connection con = null;

		try {
			con = WT.getConnection(SERVICE_ID, false);

			TimetableStampDAO tsDao = TimetableStampDAO.getInstance();
			OTimetableStamp oTs = ManagerUtils.createOTimetableStamp(ts);
			
			oTs.setId(tsDao.getSequence(con).intValue());
			oTs.setDomainId(getTargetProfileId().getDomainId());
			oTs.setType(OTimetableStamp.TYPE_SPECIAL);


			tsDao.insert(con, oTs);

			DbUtils.commitQuietly(con);

			return oTs;

		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");

		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);

		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public void deleteTimetableStamp(int id) throws WTException {

		Connection con = null;
		TimetableStampDAO tsDao = TimetableStampDAO.getInstance();
		try {
			con = WT.getConnection(SERVICE_ID, false);

			tsDao.deleteById(con, id);

			DbUtils.commitQuietly(con);

		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");
		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public void timetableRequestCancellation(Integer id, String cancellationReason) throws WTException {
		Connection con = null;

		try {
			con = WT.getConnection(SERVICE_ID, false);
			
			LeaveRequestDAO lrDao = LeaveRequestDAO.getInstance();
			
			OLeaveRequest lr = lrDao.selectById(con, id);
			lr.setCancReason(cancellationReason);
			
			lrDao.updateRequestCancellation(con, id, cancellationReason, createRevisionTimestamp());

			DbUtils.commitQuietly(con);
			
			notifyLeaveRequestCancellation(lr);
			
		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");

		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);

		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public List<OTimetableReport> generateTimetableReport(TimetableReportQuery query) throws WTException {
		Connection con = null;
		List<OTimetableReport> trs = new ArrayList();
		List<OTimetableReport> ttrs;
		List<OTimetableReport> trsf = null;
		List<OTimetableEvent> te = null;
		List<OWorkReport> wr = null;
		
		try {
			con = WT.getConnection(SERVICE_ID, false);
			TimetableReportDAO trDao = TimetableReportDAO.getInstance();
			TimetableStampDAO tstmpDao = TimetableStampDAO.getInstance();
			TimetableEventDAO teDao = TimetableEventDAO.getInstance();

			if(query != null){
				//Empty Table
				trDao.deleteByDomainIdUserId(con, getTargetProfileId().getDomainId(), getTargetProfileId().getUserId());
				
				//Reset Sequence
				//trDao.restartTimetableReportTempSequence(con);

				//Get Data - Calculate Working Hours and Extraordinary (If permits from settings)

				if(query.targetUserId == null){
					//Get Data for All Users for Company
					List<OUser> users = listCompanyUsers(query.companyId);
					for (OUser usr : users) {
						ttrs = new ArrayList();
						
						trsf = tstmpDao.getStampsByDomainUserDateRange(con, getTargetProfileId().getDomainId(), query.companyId, usr.getUserId(), query.fromDay, query.month, query.year);
						te = teDao.getEventsByDomainUserDateRange(con, getTargetProfileId().getDomainId(), query.companyId, usr.getUserId(), query.fromDay, query.month, query.year);
						// wr = wrDao.getWorkReportsByDomainUserDateRange(con, getTargetProfileId().getDomainId(), query.companyId, query.userId, query.fromDay, query.month, query.year);
						
						ttrs.addAll(mergeStampByDate(trsf, con));
						ttrs.addAll(ManagerUtils.mergeEventByDate(ManagerUtils.createOTimetableReport(te)));
						
						ttrs = ManagerUtils.mergeStampAndEventByDate(ttrs);
						
						for(OTimetableReport itm : ttrs) itm.setTargetUserId(usr.getUserId());
						
						trs.addAll(ttrs);
					}
				} else {					
					//Empty Table for single user
					trDao.deleteByDomainIdUserId(con, getTargetProfileId().getDomainId(), query.targetUserId);

					//Reset Sequence
					trDao.restartTimetableReportTempSequence(con);	
					
					//Get Data for Single User Selected
					trsf = tstmpDao.getStampsByDomainUserDateRange(con, getTargetProfileId().getDomainId(), query.companyId, query.targetUserId, query.fromDay, query.month, query.year);
					te = teDao.getEventsByDomainUserDateRange(con, getTargetProfileId().getDomainId(), query.companyId, query.targetUserId, query.fromDay, query.month, query.year);
					// wr = wrDao.getWorkReportsByDomainUserDateRange(con, getTargetProfileId().getDomainId(), query.companyId, query.userId, query.fromDay, query.month, query.year);

					trs.addAll(mergeStampByDate(trsf, con));
					trs.addAll(ManagerUtils.mergeEventByDate(ManagerUtils.createOTimetableReport(te)));
					// aggiungere i work_report che hanno il campo timetable_hours > 0 e i jobs solo di alcune attività 
					
					trs = ManagerUtils.mergeStampAndEventByDate(trs);
					
					for(OTimetableReport itm : trs) itm.setTargetUserId(query.targetUserId);
				}
				
				//Insert Data in Table
				for(OTimetableReport otr : trs){
					otr.setId(trDao.getTimetableReportTempSequence(con).intValue());
					otr.setUserId(getTargetProfileId().getUserId());
					trDao.insert(con, otr);
				}
			}
			
			//Select for DomainId
			trs = trDao.selectByDomainIdUserId(con, getTargetProfileId().getDomainId(), getTargetProfileId().getUserId());
			
			DbUtils.commitQuietly(con);

			return trs;

		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");
		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public List<OTimetableReport> getTimetableReport() throws WTException{
		Connection con = null;
		List<OTimetableReport> trs = new ArrayList();
		TimetableReportDAO trDao = TimetableReportDAO.getInstance();

		try {
			con = WT.getConnection(SERVICE_ID);

			trs = trDao.selectByDomainIdUserId(con, getTargetProfileId().getDomainId(), getTargetProfileId().getUserId());

			return trs;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	private List<OTimetableReport> mergeStampByDate(List<OTimetableReport> trsf, Connection con){
		TimetableSettingDAO tsDao = TimetableSettingDAO.getInstance();
		EmployeeProfileDAO epDao = EmployeeProfileDAO.getInstance();
		
		TimetableSetting setting = ManagerUtils.createTimetableSetting(tsDao.selectByDomainId(con, getTargetProfileId().getDomainId()));
		OEmployeeProfile oep = null;
		Integer tolerance = 0;
		Integer rounding = 0;
		
		HashMap<LocalDate, OTimetableReport> hashTr = new HashMap();
		
		//Add the minutes worked
		for(OTimetableReport otr : trsf){
			if(hashTr.get(otr.getDate().toLocalDate()) == null){
				hashTr.put(otr.getDate().toLocalDate(), otr);
			}else{
				String wh1 = otr.getWorkingHours();
				String wh2 = hashTr.get(otr.getDate().toLocalDate()).getWorkingHours();
				
				
				Float wh1f = (wh1 != null) ? Float.parseFloat(wh1) : 0.0f;
				Float wh2f = (wh2 != null) ? Float.parseFloat(wh2) : 0.0f;
				
				Float wht = wh1f + wh2f;
				BigDecimal bd = ManagerUtils.round(wht, 2);
				
				String val = bd.toString();
				
				hashTr.get(otr.getDate().toLocalDate()).setWorkingHours(val);
				hashTr.get(otr.getDate().toLocalDate()).setDetail(hashTr.get(otr.getDate().toLocalDate()).getDetail() + otr.getDetail());
			}
		}
		
		trsf =  new ArrayList(hashTr.values());
		
		//Turn the minutes into hours
		for(OTimetableReport otr : trsf){
			oep = epDao.selectEmployeeProfileByDomainUser(con, getTargetProfileId().getDomainId(), otr.getUserId());
				
			if(oep != null){
				if(StringUtils.isEmpty(oep.getTolerance())){
					if(setting != null){
						if(!StringUtils.isEmpty(setting.getTotalToleranceInMinutes())){
							tolerance = Integer.parseInt(setting.getTotalToleranceInMinutes());
						}
					}
				}else{
					tolerance = Integer.parseInt(oep.getTolerance());
				}
			}

			Float wht = (float) (Float.parseFloat(otr.getWorkingHours()) + tolerance) / 60f;

			BigDecimal bd = ManagerUtils.round(wht, 2);
			String val = bd.toString();
			
			rounding = Integer.parseInt(setting.getRounding());
			
			if(rounding == 15){
				if(Integer.parseInt(val.substring(val.length() - 2)) >= 25 && Integer.parseInt(val.substring(val.length() - 2)) < 50)
					val = val.substring(0, val.length() - 2) + "15";
				else if(Integer.parseInt(val.substring(val.length() - 2)) >= 50 && Integer.parseInt(val.substring(val.length() - 2)) < 75)
					val = val.substring(0, val.length() - 2) + "30";
				else if(Integer.parseInt(val.substring(val.length() - 2)) >= 75 && Integer.parseInt(val.substring(val.length() - 2)) < 100)
					val = val.substring(0, val.length() - 2) + "45";
				else if(Integer.parseInt(val.substring(val.length() - 2)) < 25)
					val = val.substring(0, val.length() - 2) + "00";
			}else if(rounding == 30){
				if(Integer.parseInt(val.substring(val.length() - 2)) >= 50)
					val = val.substring(0, val.length() - 2) + "30";
				else if(Integer.parseInt(val.substring(val.length() - 2)) < 50)
					val = val.substring(0, val.length() - 2) + "00";
			} else if(rounding == 60){
				val = val.substring(0, val.length() - 2) + "00";
			}			

			otr.setWorkingHours(val);
		}
		
		return trsf;
	}
	
	public void updateTimetableReport(TimetableReport item) throws WTException {
		Connection con = null;
		TimetableReportDAO trDao = TimetableReportDAO.getInstance();
		try {
			con = WT.getConnection(SERVICE_ID, false);

			OTimetableReport oTr = ManagerUtils.createOTimetableReport(item);

			trDao.update(con, oTr);

			DbUtils.commitQuietly(con);
			
		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");

		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	private void notifyLeaveRequest(OLeaveRequest lr) throws MessagingException, IOException, TemplateException {	
		UserProfile.Data udFrom = WT.getUserData(new UserProfileId(lr.getDomainId(), lr.getUserId()));
		InternetAddress from = udFrom.getPersonalEmail();
		UserProfile.Data udTo = WT.getUserData(new UserProfileId(lr.getDomainId(), lr.getManagerId()));
		InternetAddress to = udTo.getPersonalEmail();
		
		String servicePublicUrl = WT.getServicePublicUrl(lr.getDomainId(), SERVICE_ID);
		
		Session session = getMailSession();

		try {
			boolean answer = (lr.getResult() == null);
			
			String bodyHeader = TplHelper.buildLeaveRequestTitle(udTo.getLocale(), lr);
			String html = TplHelper.buildLeaveRequestBody(udTo.getLocale(), lr, to.getAddress(), answer, servicePublicUrl);
			String source = EmailNotification.buildSource(udTo.getLocale(), SERVICE_ID);
			String because = WT.lookupResource(SERVICE_ID, udTo.getLocale(), DrmLocale.EMAIL_REMINDER_SUPERVISOR_FOOTER_BECAUSE);

			String msgSubject = EmailNotification.buildSubject(udTo.getLocale(), SERVICE_ID, bodyHeader);
			
			EmailNotification.BecauseBuilder builder = new EmailNotification.BecauseBuilder().withCustomBody(bodyHeader, html);
			
			if(lr.getResult() != null){
				from = udTo.getPersonalEmail();
				to = udFrom.getPersonalEmail();
				
				because = WT.lookupResource(SERVICE_ID, udTo.getLocale(), DrmLocale.EMAIL_REMINDER_USER_FOOTER_BECAUSE);
				
				if (lr.getResult() == true) {
					builder.greenMessage(lookupResource(udTo.getLocale(), DrmLocale.TPL_EMAIL_RESPONSEUPDATE_MSG_APPROVE));
				} else if (lr.getResult() == false) {
					builder.redMessage(lookupResource(udTo.getLocale(), DrmLocale.TPL_EMAIL_RESPONSEUPDATE_MSG_DECLINE));
				}
			}
			
			String msgBody = builder.build(udTo.getLocale(), source, because, to.getAddress()).write();
			
			WT.sendEmail(session, true, from, to, msgSubject, msgBody);

		} catch (IOException | TemplateException | MessagingException ex) {
			logger.error("Unable to notify recipient after leave request [{}]", ex, to.getAddress());
		}
	}
	
	private void notifyLeaveRequestCancellation(OLeaveRequest lr) throws MessagingException, IOException, TemplateException {
		UserProfile.Data udFrom = WT.getUserData(new UserProfileId(lr.getDomainId(), lr.getUserId()));
		InternetAddress from = udFrom.getPersonalEmail();
		UserProfile.Data udTo = WT.getUserData(new UserProfileId(lr.getDomainId(), lr.getManagerId()));
		InternetAddress to = udTo.getPersonalEmail();
		
		String servicePublicUrl = WT.getServicePublicUrl(lr.getDomainId(), SERVICE_ID);
		
		Session session = getMailSession();

		try {
			boolean answer = (lr.getCancResult() == null);
			
			String bodyHeader = TplHelper.buildLeaveRequestCancellationTitle(udTo.getLocale(), lr);
			String html = TplHelper.buildLeaveRequestCancellationBody(udTo.getLocale(), lr, to.getAddress(), answer, servicePublicUrl);
			String source = EmailNotification.buildSource(udTo.getLocale(), SERVICE_ID);
			String because = WT.lookupResource(SERVICE_ID, udTo.getLocale(), DrmLocale.EMAIL_REMINDER_SUPERVISOR_FOOTER_BECAUSE);

			String msgSubject = EmailNotification.buildSubject(udTo.getLocale(), SERVICE_ID, bodyHeader);
			
			EmailNotification.BecauseBuilder builder = new EmailNotification.BecauseBuilder().withCustomBody(bodyHeader, html);
				
			if(lr.getCancResult() != null){
				from = udTo.getPersonalEmail();
				to = udFrom.getPersonalEmail();
				
				because = WT.lookupResource(SERVICE_ID, udTo.getLocale(), DrmLocale.EMAIL_REMINDER_USER_FOOTER_BECAUSE);
				
				if (lr.getCancResult() == true) {
					builder.greenMessage(lookupResource(udTo.getLocale(), DrmLocale.TPL_EMAIL_RESPONSEUPDATE_MSG_APPROVE));
				} else if (lr.getCancResult() == false) {
					builder.redMessage(lookupResource(udTo.getLocale(), DrmLocale.TPL_EMAIL_RESPONSEUPDATE_MSG_DECLINE));
				}
			}
			
			String msgBody = builder.build(udTo.getLocale(), source, because, to.getAddress()).write();
			
			WT.sendEmail(session, true, from, to, msgSubject, msgBody);

		} catch (IOException | TemplateException | MessagingException ex) {
			logger.error("Unable to notify recipient after leave request cancellation [{}]", ex, to.getAddress());
		}
	}
	
	public List<OOpportunityField> getOpportunityFieldsByDomainIdTabId(String domainId, String tabId) throws WTException {
		Connection con = null;
		OpportunityFieldDAO oFDao = OpportunityFieldDAO.getInstance();
		List<OOpportunityField> oFields = null;
		
		try {
			con = WT.getConnection(SERVICE_ID);

			oFields = oFDao.selectByDomainIdTabId(con, domainId, tabId);
			
			if(oFields == null) oFields = oFDao.selectByDomainIdTabId(con, "*", tabId);
			
			return oFields;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	static String buildLeaveRequestReplyPublicUrl(String publicBaseUrl, String leaveRequestPublicId, String recipientEmail, String crud, String resp) {
		String s = PublicService.PUBPATH_CONTEXT_LEAVE_REQUEST + "/" + leaveRequestPublicId + "/" + PublicService.LeaveRequestUrlPath.TOKEN_REPLY + "?aid=" + recipientEmail + "&crud=" + crud + "&resp=" + resp;
		return PathUtils.concatPaths(publicBaseUrl, s);
	}
	
	public TicketSetting getTicketSetting() throws WTException {
		Connection con = null;
		TicketCategoryDAO tcktCDao = TicketCategoryDAO.getInstance();

		TicketSetting setting = null;
		try {

			con = WT.getConnection(SERVICE_ID);

			setting = new TicketSetting();
			
			if (setting != null) {
				for (OTicketCategory oTcktC : tcktCDao.selectByDomain(con, getTargetProfileId().getDomainId())) {
					setting.getCategories().add(ManagerUtils.createTicketCategory(oTcktC));
				}
			}
			return setting;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public void updateTicketSetting(TicketSetting item) throws WTException {
		Connection con = null;
		TicketCategoryDAO tcktCDao = TicketCategoryDAO.getInstance();

		try {
			con = WT.getConnection(SERVICE_ID, false);
			
			TicketSetting oldTicketSetting = getTicketSetting();
			
			List<TicketCategory> categories = oldTicketSetting == null ? new ArrayList() : oldTicketSetting.getCategories();

			CollectionChangeSet<TicketCategory> changesSet1 = getCollectionChanges(categories, item.getCategories());

			for (TicketCategory category : changesSet1.inserted) {

				OTicketCategory oTcktC = ManagerUtils.createOTicketCategory(category);

				oTcktC.setTicketCategoryId(tcktCDao.getSequence(con).intValue());
				oTcktC.setDomainId(getTargetProfileId().getDomainId());

				tcktCDao.insert(con, oTcktC);
			}

			for (TicketCategory category : changesSet1.deleted) {
				tcktCDao.deleteById(con, category.getTicketCategoryId());
			}

			for (TicketCategory category : changesSet1.updated) {

				OTicketCategory oTcktC = ManagerUtils.createOTicketCategory(category);

				tcktCDao.update(con, oTcktC);
			}

			DbUtils.commitQuietly(con);

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public List<OTicketCategory> listTicketCategories() throws WTException {
		Connection con = null;
		TicketCategoryDAO tCatDao = TicketCategoryDAO.getInstance();
		List<OTicketCategory> tCats = null;
		
		try {
			con = WT.getConnection(SERVICE_ID);

			tCats = tCatDao.selectByDomain(con, getTargetProfileId().getDomainId());

			return tCats;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public List<OViewTicket> listTickets(TicketQuery query) throws WTException {
		Connection con = null;
		TicketDAO tcktDao = TicketDAO.getInstance();
		List<OViewTicket> tckts = null;
		
		try {
			con = WT.getConnection(SERVICE_ID);
			tckts = tcktDao.selectViewTickets(con, query, getTargetProfileId().getDomainId(), getTargetProfileId().getUserId());

			return tckts;
			
		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
		
	public String addTicket(Ticket tckt, Boolean close) throws WTException {
		Connection con = null;

		try {
			con = WT.getConnection(SERVICE_ID, false);

			TicketDAO tcktDao = TicketDAO.getInstance();

			OTicket newTckt = ManagerUtils.createOTicket(tckt);
			newTckt.setTicketId(IdentifierUtils.getUUID());
			newTckt.setNumber(MessageFormat.format("TK{0}{1}", String.valueOf(newTckt.getDate().getYear()), String.format("%06d", tcktDao.getSequence(con).intValue())));
			
			ArrayList<OTicketAttachment> oAtts = new ArrayList<>();
			
			for (TicketAttachment att : tckt.getAttachments()) {
				if (!(att instanceof TicketAttachmentWithStream)) throw new IOException("Attachment stream not available [" + att.getTicketAttachmentId()+ "]");
				oAtts.add(ManagerUtils.doTicketAttachmentInsert(con, newTckt.getTicketId(), (TicketAttachmentWithStream)att));
			}

			tcktDao.insert(con, newTckt);
			
			DrmServiceSettings dss = new DrmServiceSettings(SERVICE_ID, newTckt.getDomainId());
			
			notifyTicket(tcktDao.selectViewById(con, newTckt.getTicketId()), dss.getTicketDefaultCloseDocStatusId(), close);
			automaticCloseTicket(con, tcktDao.selectViewById(con, newTckt.getTicketId()), dss.getTicketDefaultCloseDocStatusId());
			
			DbUtils.commitQuietly(con);

			return newTckt.getTicketId();
			
		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");
		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public Ticket getTicket(String tcktId) throws WTException {
		Connection con = null;
		TicketDAO tcktDao = TicketDAO.getInstance();
		TicketAttachmentDAO attDao = TicketAttachmentDAO.getInstance();
		Ticket tckt = null;
		
		try {
			con = WT.getConnection(SERVICE_ID);

			tckt = ManagerUtils.createTicket(tcktDao.selectById(con, tcktId));
			
			for (OTicketAttachment oAtt : attDao.selectByTicket(con, tcktId)) {
				tckt.getAttachments().add(ManagerUtils.createTicketAttachment(oAtt));
			}

			return tckt;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public void updateTicket(Ticket item, Boolean close) throws WTException {
		Connection con = null;
		TicketDAO tcktDao = TicketDAO.getInstance();
		TicketAttachmentDAO attDao = TicketAttachmentDAO.getInstance();		
		
		try {
			con = WT.getConnection(SERVICE_ID, false);

			OTicket tckt = ManagerUtils.createOTicket(item);
			
			tcktDao.update(con, tckt);

			List<TicketAttachment> oldAtts = ManagerUtils.createTicketAttachmentList(attDao.selectByTicket(con, item.getTicketId()));
			CollectionChangeSet<TicketAttachment> changeSet = LangUtils.getCollectionChanges(oldAtts, item.getAttachments());

			for (TicketAttachment att : changeSet.inserted) {
				if (!(att instanceof TicketAttachmentWithStream)) throw new IOException("Attachment stream not available [" + att.getTicketAttachmentId()+ "]");
				ManagerUtils.doTicketAttachmentInsert(con, tckt.getTicketId(), (TicketAttachmentWithStream)att);
			}
			for (TicketAttachment att : changeSet.updated) {
				if (!(att instanceof TicketAttachmentWithStream)) continue;
				ManagerUtils.doTicketAttachmentUpdate(con, (TicketAttachmentWithStream)att);
			}
			for (TicketAttachment att : changeSet.deleted) {
				attDao.deleteById(con, att.getTicketAttachmentId());
			}
			
			DrmServiceSettings dss = new DrmServiceSettings(SERVICE_ID, item.getDomainId());
			
			notifyTicket(tcktDao.selectViewById(con, tckt.getTicketId()), dss.getTicketDefaultCloseDocStatusId(), close);
			automaticCloseTicket(con, tcktDao.selectViewById(con, tckt.getTicketId()), dss.getTicketDefaultCloseDocStatusId());
			
			DbUtils.commitQuietly(con);
			
		} catch (MessagingException | TemplateException | SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} catch (IOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public void deleteTicket(String tcktId) throws WTException {
		Connection con = null;
		TicketDAO tcktDao = TicketDAO.getInstance();

		try {
			con = WT.getConnection(SERVICE_ID, false);

			tcktDao.deleteById(con, tcktId);

			DbUtils.commitQuietly(con);

		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");
		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public List<OViewJob> listJobsByTicketId(String ticketId) throws WTException {
		Connection con = null;
		JobDAO jbDao = JobDAO.getInstance();
		List<OViewJob> jobs = null;
		
		try {
			con = WT.getConnection(SERVICE_ID);
			jobs = jbDao.selectViewJobsByTicketId(con, ticketId, getTargetProfileId().getDomainId());

			return jobs;
			
		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public void closeTicket(String tcktId, String defaultCloseStatusId) throws WTException {
		Connection con = null;
		TicketDAO tcktDao = TicketDAO.getInstance();

		try {
			con = WT.getConnection(SERVICE_ID, false);

			tcktDao.closeById(con, tcktId, defaultCloseStatusId);

			DbUtils.commitQuietly(con);

		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");
		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public Activity getActivity(int activityId) throws WTException {
		Connection con = null;
		ActivityDAO actDao = ActivityDAO.getInstance();
		ActivityGroupDAO actGroupDao = ActivityGroupDAO.getInstance();
		Activity act = null;
		
		try {
			con = WT.getConnection(SERVICE_ID);

			act = ManagerUtils.createActivity(actDao.selectById(con, activityId));

			for (OActivityGroup oActGroup : actGroupDao.selectByActivity(con, activityId)) {
				act.getAssociatedProfiles().add(ManagerUtils.createActivityGroup(oActGroup));
			}

			return act;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public OActivity addActivity(Activity act) throws WTException {
		Connection con = null;

		try {
			con = WT.getConnection(SERVICE_ID, false);

			ActivityDAO actDao = ActivityDAO.getInstance();
			ActivityGroupDAO actGroupDao = ActivityGroupDAO.getInstance();

			OActivity newAct = ManagerUtils.createOActivity(act);
			newAct.setActivityId(actDao.getSequence(con).intValue());

			OActivityGroup newActGroup = null;
			for (ActivityGroupAssociation actGroup : act.getAssociatedProfiles()) {

				newActGroup = ManagerUtils.createOActivityGroup(actGroup);
				newActGroup.setActivityId(newAct.getActivityId());
				newActGroup.setAssociationId(actGroupDao.getSequence(con).intValue());

				actGroupDao.insert(con, newActGroup);
			}

			actDao.insert(con, newAct);

			DbUtils.commitQuietly(con);

			return newAct;

		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");

		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);

		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public OActivity updateActivity(Activity item) throws WTException {
		Connection con = null;
		ActivityDAO actDao = ActivityDAO.getInstance();
		ActivityGroupDAO actGroupDao = ActivityGroupDAO.getInstance();
		
		try {
			Activity oldAct = getActivity(item.getActivityId());

			con = WT.getConnection(SERVICE_ID, false);

			OActivity act = ManagerUtils.createOActivity(item);

			actDao.update(con, act);

			LangUtils.CollectionChangeSet<ActivityGroupAssociation> changesSet1 = LangUtils.getCollectionChanges(oldAct.getAssociatedProfiles(), item.getAssociatedProfiles());

			for (ActivityGroupAssociation actGroup : changesSet1.inserted) {
				OActivityGroup oActGroup = ManagerUtils.createOActivityGroup(actGroup);

				oActGroup.setAssociationId(actGroupDao.getSequence(con).intValue());
				oActGroup.setActivityId(item.getActivityId());

				actGroupDao.insert(con, oActGroup);
			}

			for (ActivityGroupAssociation actGroup : changesSet1.deleted) {
				actGroupDao.deleteById(con, actGroup.getAssociationId());
			}

			DbUtils.commitQuietly(con);

			return act;

		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");
		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public void deleteActivity(int activityId) throws WTException {
		Connection con = null;
		ActivityDAO actDao = ActivityDAO.getInstance();
		ActivityGroupDAO actGroupDao = ActivityGroupDAO.getInstance();
		
		try {
			con = WT.getConnection(SERVICE_ID, false);

			actDao.deleteById(con, activityId);
			actGroupDao.deleteByActivity(con, activityId);

			DbUtils.commitQuietly(con);

		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");
		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public List<OActivity> listActivities(ActivityType type) throws WTException {
		Connection con = null;
		ActivityDAO actDao = ActivityDAO.getInstance();
		List<OActivity> acts = null;
		
		try {
			con = WT.getConnection(SERVICE_ID);

			acts = actDao.selectByDomain(con, getTargetProfileId().getDomainId(), type);

			return acts;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	private void notifyTicket(OViewTicket oVwTckt, String defaultCloseDocStatusId, Boolean close) throws IOException, TemplateException, MessagingException {	
		UserProfile.Data udFrom = WT.getUserData(new UserProfileId(oVwTckt.getDomainId(), oVwTckt.getFromOperatorId()));
		InternetAddress from = udFrom.getPersonalEmail();
		UserProfile.Data udTo = WT.getUserData(new UserProfileId(oVwTckt.getDomainId(), oVwTckt.getToOperatorId()));
		InternetAddress to = udTo.getPersonalEmail();
		
		Session session = getMailSession();

		try {
			DrmUserSettings dus = new DrmUserSettings(SERVICE_ID, new UserProfileId(oVwTckt.getDomainId(), oVwTckt.getToOperatorId()));
				
			if ((dus.getTicketNotifyMail().equals("true")) && (oVwTckt.getStatusId() != Integer.valueOf(defaultCloseDocStatusId)) && (!close)) {
				String msgSubject = TplHelper.buildTicketNotificationSubject(udTo.getLocale(), oVwTckt);				
				String msgBody = TplHelper.buildTicketNotificationBody(udTo.getLocale(), oVwTckt);
				
				WT.sendEmail(session, true, from, to, msgSubject, msgBody);
			}			
			
		} catch (IOException ex) {
			logger.error("Unable to notify recipient for ticket [{}]", ex, to.getAddress());
		}
	}
	
	public List<OViewTicket> listTicketsOpenedByToOperatorId(TicketQuery query, String domainId, String userId) throws WTException {
		Connection con = null;
		TicketDAO tcktDao = TicketDAO.getInstance();
		List<OViewTicket> tckts = null;
		
		try {
			con = WT.getConnection(SERVICE_ID);
			tckts = tcktDao.selectViewTickets(con, query, getTargetProfileId().getDomainId(), getTargetProfileId().getUserId());

			return tckts;
			
		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	private void automaticCloseTicket(Connection con, OViewTicket oVwTckt, String defaultCloseDocStatusId) throws WTException {
		DrmUserSettings dus = new DrmUserSettings(SERVICE_ID, new UserProfileId(oVwTckt.getDomainId(), oVwTckt.getToOperatorId()));		
		TicketDAO tcktDao = TicketDAO.getInstance();
		
		if ((dus.getTicketAutomaticClose().equals("true"))) {
			tcktDao.closeById(con, oVwTckt.getTicketId(), defaultCloseDocStatusId);
			// closeTicket(oVwTckt.getTicketId(), dss.getTicketDefaultCloseDocStatusId());
		}
	}
	
	public List<OTicket> listTicketsByNumberCustomer(String query, String customerId) throws WTException {
		Connection con = null;
		TicketDAO tcktDao = TicketDAO.getInstance();
		List<OTicket> tckts = null;
		
		try {
			con = WT.getConnection(SERVICE_ID);
			tckts = tcktDao.selectByNumberCustomer(con, query, getTargetProfileId().getDomainId(), customerId);

			return tckts;
			
		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public void associateTicket(String jobId, String ticketId) throws WTException {
		Connection con = null;
		JobDAO jobDao = JobDAO.getInstance();

		try {
			con = WT.getConnection(SERVICE_ID, false);

			jobDao.associateTicket(con, jobId, ticketId);

			DbUtils.commitQuietly(con);

		} catch (SQLException | DAOException ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex, "DB error");
		} catch (Exception ex) {
			DbUtils.rollbackQuietly(con);
			throw new WTException(ex);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public void exportJobs(LogEntries log, JobQuery jq, OutputStream os) throws Exception {
		Connection con = null;
		ICsvMapWriter mapw = null;		
		
		try {
			//TODO: Gestire campi visit_id e action_id provenienti dal servizio DRM
			final String dateFmt = "yyyy-MM-dd";
			final String timeFmt = "HH:mm:ss";
			final String[] headers = new String[]{
				"domainId",
				"number", 
				"companyId", "companyDescription",
				"userId", "userDescription", 
				"startDate", "startTime", "endDate", "endTime", 
				"timezone", "duration", 
				"title", "description", 
				"activityId", "activityDescription", 
				"causalId", "causalDescription", 				
				"masterDataId", "masterDataDescription",
				"statMasterDataId", "statMasterDataDescription"
			};
			final CellProcessor[] processors = new CellProcessor[]{
				new NotNull(), 
				new NotNull(), 
				new NotNull(), null, 
				new NotNull(), null, 
				new FmtDateTime(dateFmt), new FmtDateTime(timeFmt), new FmtDateTime(dateFmt), new FmtDateTime(timeFmt), 
				new NotNull(), new NotNull(),
				null, null, 
				null, null, 
				null, null,
				null, null, 
				null, null
			};
			
			CsvPreference pref = new CsvPreference.Builder('"', ';', "\n").build();
			mapw = new CsvMapWriter(new OutputStreamWriter(os), pref);
			mapw.writeHeader(headers);
			
			HashMap<String, Object> map = null;
			for (OViewJob jb : listJobs(jq)) {
				map = new HashMap<>();
				map.put("domainId", jb.getDomainId());
				map.put("number", jb.getNumber());
				map.put("companyId", jb.getCompanyId());	
				map.put("companyDescription", jb.getCompanyDescription());	
				map.put("userId", jb.getOperatorId());	
				map.put("userDescription", jb.getOperatorDescription());	
				
				DateTime startDt = jb.getStartDate().withZone(DateTimeZone.forID(jb.getTimezone()));
				map.put("startDate", startDt);
				map.put("startTime", startDt);
				DateTime endDt = jb.getEndDate().withZone(DateTimeZone.forID(jb.getTimezone()));
				map.put("endDate", endDt);
				map.put("endTime", endDt);
				map.put("timezone", jb.getTimezone());
				map.put("duration", Minutes.minutesBetween(jb.getStartDate(), jb.getEndDate()).getMinutes());	

				map.put("title", jb.getTitle());
				map.put("description", jb.getDescription());
				map.put("activityId", jb.getActivityId());
				map.put("activityDescription", jb.getActivityDescription());
				map.put("causalId", jb.getCausalId());
				map.put("causalDescription", jb.getCausalDescription());
				map.put("masterDataId", jb.getCustomerId());
				map.put("masterDataDescription", jb.getCustomerDescription());
				map.put("statMasterDataId", jb.getCustomerStatId());
				map.put("statMasterDataDescription", jb.getCustomerStatDescription());
				
				mapw.write(map, headers, processors);
			}
			
			mapw.flush();
			
		} catch(Exception ex) {
			throw ex;
		} finally {
			DbUtils.closeQuietly(con);
			try { if(mapw != null) mapw.close(); } catch(Exception ex) { /* Do nothing... */ }
		}
	}
}
