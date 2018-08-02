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
import com.sonicle.commons.LangUtils;
import com.sonicle.commons.LangUtils.CollectionChangeSet;
import static com.sonicle.commons.LangUtils.getCollectionChanges;
import com.sonicle.commons.PathUtils;
import com.sonicle.commons.db.DbUtils;
import com.sonicle.webtop.core.CoreManager;
import com.sonicle.webtop.core.app.WT;
import com.sonicle.webtop.core.bol.OUser;
import com.sonicle.webtop.core.dal.DAOException;
import com.sonicle.webtop.core.dal.UserDAO;
import com.sonicle.webtop.core.sdk.BaseManager;
import com.sonicle.webtop.core.sdk.UserProfile;
import com.sonicle.webtop.core.sdk.UserProfileId;
import com.sonicle.webtop.core.sdk.WTException;
import com.sonicle.webtop.core.util.IdentifierUtils;
import static com.sonicle.webtop.drm.Service.logger;
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
import com.sonicle.webtop.drm.bol.OLineHour;
import com.sonicle.webtop.drm.bol.OEmployeeProfile;
import com.sonicle.webtop.drm.bol.OHolidayDate;
import com.sonicle.webtop.drm.bol.OHourProfile;
import com.sonicle.webtop.drm.bol.OLeaveRequest;
import com.sonicle.webtop.drm.bol.OLeaveRequestDocument;
import com.sonicle.webtop.drm.bol.OLeaveRequestType;
import com.sonicle.webtop.drm.bol.OOpportunity;
import com.sonicle.webtop.drm.bol.OOpportunityAction;
import com.sonicle.webtop.drm.bol.OOpportunityActionDocument;
import com.sonicle.webtop.drm.bol.OOpportunityActionInterlocutor;
import com.sonicle.webtop.drm.bol.OOpportunityDocument;
import com.sonicle.webtop.drm.bol.OOpportunityField;
import com.sonicle.webtop.drm.bol.OOpportunityInterlocutor;
import com.sonicle.webtop.drm.bol.OProfileMasterdata;
import com.sonicle.webtop.drm.bol.OProfileSupervisedUser;
import com.sonicle.webtop.drm.bol.OProfileMember;
import com.sonicle.webtop.drm.bol.OTimetableEvent;
import com.sonicle.webtop.drm.bol.OTimetableReport;
import com.sonicle.webtop.drm.bol.OTimetableSetting;
import com.sonicle.webtop.drm.bol.OTimetableStamp;
import com.sonicle.webtop.drm.bol.OWorkReport;
import com.sonicle.webtop.drm.bol.OWorkReportAttachment;
import com.sonicle.webtop.drm.bol.OWorkReportRow;
import com.sonicle.webtop.drm.bol.OWorkReportSetting;
import com.sonicle.webtop.drm.bol.OWorkType;
import com.sonicle.webtop.drm.dal.BusinessTripDao;
import com.sonicle.webtop.drm.dal.CompanyDAO;
import com.sonicle.webtop.drm.dal.CompanyPictureDAO;
import com.sonicle.webtop.drm.dal.CompanyUserDAO;
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
import com.sonicle.webtop.drm.dal.HolidayDateDAO;
import com.sonicle.webtop.drm.dal.HourProfileDAO;
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
import com.sonicle.webtop.drm.dal.TimetableEventDAO;
import com.sonicle.webtop.drm.dal.TimetableReportDAO;
import com.sonicle.webtop.drm.dal.TimetableSettingDAO;
import com.sonicle.webtop.drm.dal.TimetableStampDAO;
import com.sonicle.webtop.drm.dal.WorkReportAttachmentDAO;
import com.sonicle.webtop.drm.dal.WorkReportDAO;
import com.sonicle.webtop.drm.dal.WorkReportRowDAO;
import com.sonicle.webtop.drm.dal.WorkReportSettingDAO;
import com.sonicle.webtop.drm.dal.WorkTypeDAO;
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
import com.sonicle.webtop.drm.model.LineHour;
import com.sonicle.webtop.drm.model.EmployeeProfile;
import com.sonicle.webtop.drm.model.FileContent;
import com.sonicle.webtop.drm.model.HolidayDate;
import com.sonicle.webtop.drm.model.HourProfile;
import com.sonicle.webtop.drm.model.LeaveRequest;
import com.sonicle.webtop.drm.model.LeaveRequestDocument;
import com.sonicle.webtop.drm.model.Opportunity;
import com.sonicle.webtop.drm.model.OpportunityAction;
import com.sonicle.webtop.drm.model.OpportunityActionDocument;
import com.sonicle.webtop.drm.model.OpportunityActionInterlocutor;
import com.sonicle.webtop.drm.model.OpportunityDocument;
import com.sonicle.webtop.drm.model.OpportunityField;
import com.sonicle.webtop.drm.model.OpportunityInterlocutor;
import com.sonicle.webtop.drm.model.OpportunitySetting;
import com.sonicle.webtop.drm.model.ProfileMasterdata;
import com.sonicle.webtop.drm.model.ProfileSupervisedUser;
import com.sonicle.webtop.drm.model.ProfileMember;
import com.sonicle.webtop.drm.model.TimetableReport;
import com.sonicle.webtop.drm.model.TimetableSetting;
import com.sonicle.webtop.drm.model.TimetableStamp;
import com.sonicle.webtop.drm.model.UserForManager;
import com.sonicle.webtop.drm.model.WorkReport;
import com.sonicle.webtop.drm.model.WorkReportAttachment;
import com.sonicle.webtop.drm.model.WorkReportRow;
import com.sonicle.webtop.drm.model.WorkReportSetting;
import com.sonicle.webtop.drm.model.WorkType;
import com.sonicle.webtop.drm.util.EmailNotification;
import eu.medsea.mimeutil.MimeType;
import freemarker.template.TemplateException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.imgscalr.Scalr;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.Period;

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
			company = createCompany(compDao.selectById(con, companyId), hasPicture);

			for (OCompanyUser oComUsr : comUsrDao.selectByCompany(con, companyId)) {
				company.getAssociatedUsers().add(createCompanyUser(oComUsr));
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

			OCompany newCompany = createOCompany(company);
			newCompany.setDomainId(getTargetProfileId().getDomainId());
			newCompany.setCompanyId(compDao.getSequence(con).intValue());

			OCompanyUser newCompUsr = null;
			for (CompanyUserAssociation compUsr : company.getAssociatedUsers()) {
				newCompUsr = createOCompanyUser(compUsr);
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

			OCompany company = createOCompany(item);

			compDao.update(con, company);

			LangUtils.CollectionChangeSet<CompanyUserAssociation> changesSet1 = LangUtils.getCollectionChanges(oldCompany.getAssociatedUsers(), item.getAssociatedUsers());

			for (CompanyUserAssociation compUsrs : changesSet1.inserted) {

				OCompanyUser comU = createOCompanyUser(compUsrs);

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

	private OCompany createOCompany(Company com) {
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

	private Company createCompany(OCompany oCom, boolean hasPicture) {
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

	private CompanyUserAssociation createCompanyUser(OCompanyUser oComUsr) {

		if (oComUsr == null) {
			return null;
		}

		CompanyUserAssociation comUsr = new CompanyUserAssociation();
		comUsr.setAssociationId(oComUsr.getAssociationId());
		comUsr.setCompanyId(oComUsr.getCompanyId());
		comUsr.setUserId(oComUsr.getUserId());

		return comUsr;
	}

	private OCompanyUser createOCompanyUser(CompanyUserAssociation comUsr) {

		if (comUsr == null) {
			return null;
		}

		OCompanyUser oComUsr = new OCompanyUser();
		oComUsr.setAssociationId(comUsr.getAssociationId());
		oComUsr.setCompanyId(comUsr.getCompanyId());
		oComUsr.setUserId(comUsr.getUserId());

		return oComUsr;
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

			statuses = staDao.selectStatuses(con);

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

			docStatus = createDocStatus(docDao.selectById(con, docStatusId));

			for (ODocStatusGroup odocS : docGroupDao.selectByDocStatus(con, docStatusId)) {
				docStatus.getAssociatedProfiles().add(createDocStatusProfile(odocS));
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

			ODocStatus newDocStatus = createODocStatus(docStatus);
			newDocStatus.setDocStatusId(docDao.getSequence(con).intValue());
			newDocStatus.setBuiltIn(false);

			ODocStatusGroup newDocS = null;
			for (DocStatusGroupAssociation docS : docStatus.getAssociatedProfiles()) {

				newDocS = createODocStatusProfile(docS);
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

			ODocStatus docStatus = createODocStatus(item);

			docDao.update(con, docStatus);

			LangUtils.CollectionChangeSet<DocStatusGroupAssociation> changesSet1 = LangUtils.getCollectionChanges(oldDocStatus.getAssociatedProfiles(), item.getAssociatedProfiles());

			for (DocStatusGroupAssociation docsGroup : changesSet1.inserted) {

				ODocStatusGroup docG = createODocStatusProfile(docsGroup);

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

	private ODocStatus createODocStatus(DocStatus dStat) {
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

	private DocStatus createDocStatus(ODocStatus oDstat) {
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

	private DocStatusGroupAssociation createDocStatusProfile(ODocStatusGroup odocS) {

		if (odocS == null) {
			return null;
		}

		DocStatusGroupAssociation docStatusGroup = new DocStatusGroupAssociation();
		docStatusGroup.setAssociationId(odocS.getAssociationId());
		docStatusGroup.setDocStatusId(odocS.getDocStatusId());
		docStatusGroup.setGroupId(odocS.getGroupId());

		return docStatusGroup;
	}

	private ODocStatusGroup createODocStatusProfile(DocStatusGroupAssociation docS) {

		if (docS == null) {
			return null;
		}

		ODocStatusGroup odocS = new ODocStatusGroup();
		odocS.setAssociationId(docS.getAssociationId());
		odocS.setDocStatusId(docS.getDocStatusId());
		odocS.setGroupId(docS.getGroupId());

		return odocS;
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

			group = createDrmGroup(grpDao.selectById(con, groupId));

			for (ODrmGroupUser oGrpUsr : grpUsrDao.selectByGroup(con, groupId)) {
				group.getAssociatedUsers().add(createDrmGroupUser(oGrpUsr));
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

			ODrmGroup newDrmGroup = createODrmGroup(group);
			newDrmGroup.setGroupId(IdentifierUtils.getUUID());
			newDrmGroup.setDomainId(getTargetProfileId().getDomainId());

			ODrmGroupUser newGrpUsr = null;
			for (DrmGroupUserAssociation grpUsr : group.getAssociatedUsers()) {

				newGrpUsr = createODrmGroupUser(grpUsr);
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

			ODrmGroup group = createODrmGroup(item);

			groDao.update(con, group);

			LangUtils.CollectionChangeSet<DrmGroupUserAssociation> changesSet1 = LangUtils.getCollectionChanges(oldDrmGroup.getAssociatedUsers(), item.getAssociatedUsers());

			for (DrmGroupUserAssociation grpUsr : changesSet1.inserted) {

				ODrmGroupUser oGrpUsr = createODrmGroupUser(grpUsr);

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

	private DrmGroup createDrmGroup(ODrmGroup oGroup) {

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

	private ODrmGroup createODrmGroup(DrmGroup group) {

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

	private DrmGroupUserAssociation createDrmGroupUser(ODrmGroupUser oGrpUsr) {

		if (oGrpUsr == null) {
			return null;
		}

		DrmGroupUserAssociation grpUsr = new DrmGroupUserAssociation();
		grpUsr.setAssociationId(oGrpUsr.getAssociationId());
		grpUsr.setGroupId(oGrpUsr.getGroupId());
		grpUsr.setUserId(oGrpUsr.getUserId());

		return grpUsr;
	}

	private ODrmGroupUser createODrmGroupUser(DrmGroupUserAssociation grpUsr) {

		if (grpUsr == null) {
			return null;
		}

		ODrmGroupUser oGrpUsr = new ODrmGroupUser();
		oGrpUsr.setAssociationId(grpUsr.getAssociationId());
		oGrpUsr.setGroupId(grpUsr.getGroupId());
		oGrpUsr.setUserId(grpUsr.getUserId());

		return oGrpUsr;
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

			profile = createProfile(pflDao.selectById(con, profileId));

			if (profile.type.equals(EnumUtils.toSerializedName(DrmProfile.Type.EXTERNAL))) {
				for (OProfileMasterdata oPrfMaster : prfMasterDao.selectByProfile(con, profileId)) {
					profile.getAssociatedCustomers().add(createProfileMasterdata(oPrfMaster));
				}
			} else if (profile.type.equals(EnumUtils.toSerializedName(DrmProfile.Type.SUPERVISOR))) {
				for (OProfileSupervisedUser oPrfSupervised : prfSupervisedDao.selectByProfile(con, profileId)) {
					profile.getSupervisedUsers().add(createProfileSupervisedUser(oPrfSupervised));
				}
			}

			for (OProfileMember oPrfUser : prfUserDao.selectByProfile(con, profileId)) {
				profile.getAssociatedUsers().add(createProfileMember(oPrfUser));
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

			OEmployeeProfile oEP = createOEmployeeProfile(eProfile);
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
			OEmployeeProfile eProfile = createOEmployeeProfile(item);

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

			OHourProfile oHP = createOHourProfile(hProfile);
			oHP.setId(hpDao.getSequence(con).intValue());
			oHP.setDomainId(getTargetProfileId().getDomainId());

			OLineHour oLH = null;
			for (LineHour eh : hProfile.getLineHours()) {
				oLH = fillOLineHour(new OLineHour(), eh);
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
			OHourProfile hProfile = createOHourProfile(item);

			hpDao.update(con, hProfile);

			LangUtils.CollectionChangeSet<LineHour> changesSet1 = LangUtils.getCollectionChanges(oldHP.getLineHours(), item.getLineHours());
			for (LineHour eh : changesSet1.inserted) {
				final OLineHour oLH = fillOLineHour(new OLineHour(), eh);
				oLH.setId(lhDao.getSequence(con).intValue());
				oLH.setDomainId(item.getDomainId());
				oLH.setHourProfileId(item.getId());
				lhDao.insert(con, oLH);
			}

			for (LineHour eh : changesSet1.deleted) {
				lhDao.deleteById(con, eh.getId());
			}
			
			for (LineHour eh : changesSet1.updated) {
				OLineHour oLH = createOLineHour(eh);
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

			ODrmProfile newDrmProfile = createODrmProfile(profile);
			newDrmProfile.setProfileId(IdentifierUtils.getUUID());
			newDrmProfile.setDomainId(getTargetProfileId().getDomainId());

			OProfileMasterdata newPrfMast = null;
			OProfileSupervisedUser newPrfSupervised = null;
			if (newDrmProfile.getType().equals(EnumUtils.toSerializedName(DrmProfile.Type.EXTERNAL))) {
				for (ProfileMasterdata prfMasterD : profile.getAssociatedCustomers()) {

					newPrfMast = createOProfileMasterdata(prfMasterD);
					newPrfMast.setId(prfMastDao.getSequence(con).intValue());
					newPrfMast.setProfileId(newDrmProfile.getProfileId());

					prfMastDao.insert(con, newPrfMast);
				}
			} else if (newDrmProfile.getType().equals(EnumUtils.toSerializedName(DrmProfile.Type.SUPERVISOR))) {
				for (ProfileSupervisedUser prfSupervised : profile.getSupervisedUsers()) {

					newPrfSupervised = createOProfileSupervisedUser(prfSupervised);
					newPrfSupervised.setId(prfMastDao.getSequence(con).intValue());
					newPrfSupervised.setProfileId(newDrmProfile.getProfileId());

					prfSupervisedDao.insert(con, newPrfSupervised);
				}
			}

			OProfileMember opmem = null;
			for (ProfileMember pmem : profile.getAssociatedUsers()) {
				opmem = fillOProfileMember(new OProfileMember(), pmem);
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

			ODrmProfile profile = createODrmProfile(item);

			pflDao.update(con, profile);

			if (profile.getType().equals(EnumUtils.toSerializedName(DrmProfile.Type.EXTERNAL))) {
				LangUtils.CollectionChangeSet<ProfileMasterdata> changesSet1 = LangUtils.getCollectionChanges(oldDrmProfile.getAssociatedCustomers(), item.getAssociatedCustomers());

				for (ProfileMasterdata prfMaster : changesSet1.inserted) {
					OProfileMasterdata oGrpUsr = createOProfileMasterdata(prfMaster);
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
					OProfileSupervisedUser oPrfSupervised = createOProfileSupervisedUser(prfSupervised);
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
				final OProfileMember opmem = fillOProfileMember(new OProfileMember(), pmem);
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
	
	private EmployeeProfile createEmployeeProfile(OEmployeeProfile oEP) {

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

	private OEmployeeProfile createOEmployeeProfile(EmployeeProfile eProfile) {

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
	
	private HourProfile createHourProfile(OHourProfile oHP) {

		if (oHP == null) {
			return null;
		}

		HourProfile hpProfile = new HourProfile();
		hpProfile.setId(oHP.getId());
		hpProfile.setDomainId(oHP.getDomainId());
		hpProfile.setDescription(oHP.getDescription());
		
		return hpProfile;
	}

	private OHourProfile createOHourProfile(HourProfile hProfile) {

		if (hProfile == null) {
			return null;
		}

		OHourProfile oHp = new OHourProfile();
		oHp.setId(hProfile.getId());
		oHp.setDomainId(hProfile.getDomainId());
		oHp.setDescription(hProfile.getDescription());

		return oHp;
	}
	
	private LineHour createLineHour(OLineHour oEH) {

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

	private OLineHour createOLineHour(LineHour eh) {

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
	
	private TimetableStamp createTimetableStamp(OTimetableStamp oTS) {

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

	private OTimetableStamp createOTimetableStamp(TimetableStamp stamp) {

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

	private DrmProfile createProfile(ODrmProfile oProfile) {

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

	private ODrmProfile createODrmProfile(DrmProfile profile) {

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

	private ProfileMasterdata createProfileMasterdata(OProfileMasterdata oPrfMasterD) {

		if (oPrfMasterD == null) {
			return null;
		}

		ProfileMasterdata prfMasterD = new ProfileMasterdata();
		prfMasterD.setId(oPrfMasterD.getId());
		prfMasterD.setProfileId(oPrfMasterD.getProfileId());
		prfMasterD.setMasterDataId(oPrfMasterD.getMasterDataId());

		return prfMasterD;
	}

	private OProfileMasterdata createOProfileMasterdata(ProfileMasterdata prfMasterD) {

		if (prfMasterD == null) {
			return null;
		}

		OProfileMasterdata oPrfMasterD = new OProfileMasterdata();
		oPrfMasterD.setId(prfMasterD.getId());
		oPrfMasterD.setProfileId(prfMasterD.getProfileId());
		oPrfMasterD.setMasterDataId(prfMasterD.getMasterDataId());

		return oPrfMasterD;
	}

	private ProfileSupervisedUser createProfileSupervisedUser(OProfileSupervisedUser oPrfSupervised) {

		if (oPrfSupervised == null) {
			return null;
		}

		ProfileSupervisedUser prfSupervised = new ProfileSupervisedUser();
		prfSupervised.setId(oPrfSupervised.getId());
		prfSupervised.setProfileId(oPrfSupervised.getProfileId());
		prfSupervised.setUserId(oPrfSupervised.getUserId());

		return prfSupervised;
	}

	private OProfileSupervisedUser createOProfileSupervisedUser(ProfileSupervisedUser prfSupervised) {

		if (prfSupervised == null) {
			return null;
		}

		OProfileSupervisedUser oPrfSupervised = new OProfileSupervisedUser();
		oPrfSupervised.setId(prfSupervised.getId());
		oPrfSupervised.setProfileId(prfSupervised.getProfileId());
		oPrfSupervised.setUserId(prfSupervised.getUserId());

		return oPrfSupervised;
	}
	
	private ProfileMember createProfileMember(OProfileMember with) {
		return fillProfileMember(new ProfileMember(), with);
	}

	private ProfileMember fillProfileMember(ProfileMember fill, OProfileMember with) {
		if ((fill != null) && (with != null)) {
			fill.setId(with.getId());
			fill.setProfileId(with.getProfileId());
			fill.setUserId(with.getUserId());
		}
		return fill;
	}

	private OProfileMember fillOProfileMember(OProfileMember fill, ProfileMember with) {
		if ((fill != null) && (with != null)) {
			fill.setId(with.getId());
			fill.setProfileId(with.getProfileId());
			fill.setUserId(with.getUserId());
		}
		return fill;
	}
	
	private OLineHour fillOLineHour(OLineHour fill, LineHour with) {
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
	
	public String getDiffHours(String e, String u){
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
	
	private ODrmLineManagerUsers fillODrmLineManagerUsers(ODrmLineManagerUsers fill, UserForManager with) {
		if ((fill != null) && (with != null)) {
			fill.setUserId(with.getUserId());
		}
		return fill;
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

			folder = createFolder(fldDao.selectById(con, folderId));

			for (ODrmFolderGroup oFldG : fldGDao.selectByDocStatus(con, folderId)) {
				folder.getAssociatedGroups().add(createDrmFolderGroup(oFldG));
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

			ODrmFolder newFolder = createODrmFolder(folder);
			newFolder.setFolderId(IdentifierUtils.getUUID());

			ODrmFolderGroup newFldGrp = null;
			for (DrmFolderGroupAssociation fldG : folder.getAssociatedGroups()) {
				newFldGrp = createODrmFolderGroup(fldG);
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

			ODrmFolder folder = createODrmFolder(item);

			fldDao.update(con, folder);

			LangUtils.CollectionChangeSet<DrmFolderGroupAssociation> changesSet1 = LangUtils.getCollectionChanges(oldDrmFolder.getAssociatedGroups(), item.getAssociatedGroups());

			for (DrmFolderGroupAssociation fldGroup : changesSet1.inserted) {

				ODrmFolderGroup fldG = createODrmFolderGroup(fldGroup);

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

	private ODrmFolder createODrmFolder(DrmFolder fld) {
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

	private DrmFolder createFolder(ODrmFolder oFld) {
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

	private DrmFolderGroupAssociation createDrmFolderGroup(ODrmFolderGroup oFldGrp) {

		if (oFldGrp == null) {
			return null;
		}

		DrmFolderGroupAssociation fldGrp = new DrmFolderGroupAssociation();
		fldGrp.setAssociationId(oFldGrp.getAssociationId());
		fldGrp.setFolderId(oFldGrp.getFolderId());
		fldGrp.setGroupId(oFldGrp.getGroupId());

		return fldGrp;
	}

	private ODrmFolderGroup createODrmFolderGroup(DrmFolderGroupAssociation fldGrp) {

		if (fldGrp == null) {
			return null;
		}

		ODrmFolderGroup oFldGrp = new ODrmFolderGroup();
		oFldGrp.setAssociationId(fldGrp.getAssociationId());
		oFldGrp.setFolderId(fldGrp.getFolderId());
		oFldGrp.setGroupId(fldGrp.getGroupId());

		return oFldGrp;
	}

	public List<OWorkReport> listWorkReports(WorkReportQuery query) throws WTException {
		Connection con = null;
		WorkReportDAO wrkDao = WorkReportDAO.getInstance();
		List<OWorkReport> workRpts = null;
		try {
			con = WT.getConnection(SERVICE_ID);
			workRpts = wrkDao.selectWorkReports(con, query);

			return workRpts;
			
		} catch (SQLException | DAOException ex) {
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
			opportunities = oDao.selectOpportunities(con, query);

			return opportunities;
			
		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public Opportunity getOpportunity(Integer id) throws WTException {
		Connection con = null;
		OpportunityDAO oDao = OpportunityDAO.getInstance();
		OpportunityActionDAO oADao = OpportunityActionDAO.getInstance();
		OpportunityActionInterlocutorDAO oAIDao = OpportunityActionInterlocutorDAO.getInstance();
		OpportunityActionDocumentDAO oADDao = OpportunityActionDocumentDAO.getInstance();
		OpportunityInterlocutorDAO oIDao = OpportunityInterlocutorDAO.getInstance();
		OpportunityDocumentDAO oDDao = OpportunityDocumentDAO.getInstance();
		
		Opportunity o = null;
		
		try {

			con = WT.getConnection(SERVICE_ID);

			o = createOpportunity(oDao.selectById(con, id));

			for (OOpportunityAction oAct : oADao.selectByOpportunity(con, id)) {
				OpportunityAction act = createOpportunityAction(oAct);
				
				for (OOpportunityActionInterlocutor oActInt : oAIDao.selectByOpportunityAction(con, act.getId())) {
					act.getActionInterlocutors().add(createOpportunityActionInterlocutor(oActInt));
				}

				for (OOpportunityActionDocument oActDoc : oADDao.selectByOpportunityAction(con, act.getId())) {
					act.getActionDocuments().add(createOpportunityActionDocument(oActDoc));
				}
				o.getActions().add(act);
			}
			
			for (OOpportunityInterlocutor oInt : oIDao.selectByOpportunity(con, id)) {
				o.getInterlocutors().add(createOpportunityInterlocutor(oInt));
			}

			for (OOpportunityDocument oDoc : oDDao.selectByOpportunity(con, id)) {
				o.getDocuments().add(createOpportunityDocument(oDoc));
			}

			return o;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public OOpportunity addOpportunity(Opportunity o, HashMap<String, File> files) throws WTException {
		Connection con = null;
		OpportunityDAO oDao = OpportunityDAO.getInstance();
		OpportunityActionDAO oADao = OpportunityActionDAO.getInstance();
		OpportunityActionInterlocutorDAO oAIDao = OpportunityActionInterlocutorDAO.getInstance();
		OpportunityActionDocumentDAO oADDao = OpportunityActionDocumentDAO.getInstance();
		OpportunityInterlocutorDAO oIDao = OpportunityInterlocutorDAO.getInstance();
		OpportunityDocumentDAO oDDao = OpportunityDocumentDAO.getInstance();
		
		try {
			con = WT.getConnection(SERVICE_ID, false);
			
			DateTime revisionTimestamp = createRevisionTimestamp();

			OOpportunity newO = createOOpportunity(o);
			newO.setId(oDao.getOpportunitySequence(con).intValue());
			newO.setDomainId(getTargetProfileId().getDomainId());

			OOpportunityAction newOAct = null;
			for (OpportunityAction oAct : o.getActions()) {
				
				OOpportunityActionInterlocutor newOActInt = null;
				for (OpportunityActionInterlocutor oActInt : oAct.getActionInterlocutors()) {
					newOActInt = createOOpportunityActionInterlocutor(oActInt);
					newOActInt.setId(oAIDao.getSequence(con).intValue());
					newOActInt.setOpportunityActionId(oAct.getId());

					oAIDao.insert(con, newOActInt);
				}
				
				OOpportunityActionDocument newOActDoc = null;
				for (OpportunityActionDocument oActDoc : oAct.getActionDocuments()) {
					newOActDoc = createOOpportunityActionDocument(oActDoc);
					newOActDoc.setOpportunityActionId(oAct.getId());
					newOActDoc.setRevisionTimestamp(revisionTimestamp);
					newOActDoc.setRevisionSequence((short) 1);

					oADDao.insert(con, newOActDoc);
				}
				
				newOAct = createOOpportunityAction(oAct);
				newOAct.setId(oADao.getSequence(con).intValue());
				newOAct.setOpportunityId(newO.getId());

				oADao.insert(con, newOAct);
			}
			
			OOpportunityInterlocutor newOInt = null;
			for (OpportunityInterlocutor oInt : o.getInterlocutors()) {
				newOInt = createOOpportunityInterlocutor(oInt);
				newOInt.setId(oIDao.getSequence(con).intValue());
				newOInt.setOpportunityId(newO.getId());

				oIDao.insert(con, newOInt);
			}

			for (OpportunityDocument doc : o.getDocuments()) {
				OOpportunityDocument oDoc = createOOpportunityDocument(doc);
				oDoc.setOpportunityId(newO.getId());
				oDoc.setRevisionTimestamp(revisionTimestamp);
				oDoc.setRevisionSequence((short) 1);

				oDDao.insert(con, oDoc);
			}

			oDao.insert(con, newO);

			File destDir = new File(getOpportunityPath(newO.getId()));
			if (!destDir.exists()) {
				destDir.mkdir();
			}
			
			for (OpportunityDocument doc : o.getDocuments()) {

				File file = files.get(doc.getId());
				String fileName = PathUtils.addFileExension(file.getName(), FilenameUtils.getExtension(doc.getFileName()));
				File destFile = new File(destDir, fileName);

				FileUtils.copyFile(file, destFile);
			}

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

	public OOpportunity updateOpportunity(Opportunity item, HashMap<String, File> files) throws WTException {
		Connection con = null;
		OpportunityDAO oDao = OpportunityDAO.getInstance();
		OpportunityActionDAO oADao = OpportunityActionDAO.getInstance();
		OpportunityActionInterlocutorDAO oAIDao = OpportunityActionInterlocutorDAO.getInstance();
		OpportunityActionDocumentDAO oADDao = OpportunityActionDocumentDAO.getInstance();
		OpportunityInterlocutorDAO oIDao = OpportunityInterlocutorDAO.getInstance();
		OpportunityDocumentDAO oDDao = OpportunityDocumentDAO.getInstance();
		
		try {
			con = WT.getConnection(SERVICE_ID, false);
			
			DateTime revisionTimestamp = createRevisionTimestamp();
			
			Opportunity oldO = getOpportunity(item.getId());

			OOpportunity o = createOOpportunity(item);
			
			//Opportunity
			oDao.update(con, o);
			
			//Opportunity Actions
			LangUtils.CollectionChangeSet<OpportunityAction> changesSet1 = LangUtils.getCollectionChanges(oldO.getActions(), item.getActions());
			
			for (OpportunityAction oAct : changesSet1.inserted) {
				
				OOpportunityActionInterlocutor newOActInt = null;
				for (OpportunityActionInterlocutor oActInt : oAct.getActionInterlocutors()) {
					newOActInt = createOOpportunityActionInterlocutor(oActInt);
					newOActInt.setId(oAIDao.getSequence(con).intValue());
					newOActInt.setOpportunityActionId(oAct.getId());

					oAIDao.insert(con, newOActInt);
				}
				
				OOpportunityActionDocument newOActDoc = null;
				for (OpportunityActionDocument oActDoc : oAct.getActionDocuments()) {
					newOActDoc = createOOpportunityActionDocument(oActDoc);
					newOActDoc.setOpportunityActionId(oAct.getId());
					newOActDoc.setRevisionTimestamp(revisionTimestamp);
					newOActDoc.setRevisionSequence((short) 1);

					oADDao.insert(con, newOActDoc);
				}
				
				OOpportunityAction oOAct = createOOpportunityAction(oAct);

				oOAct.setId(oADao.getSequence(con).intValue());
				oOAct.setOpportunityId(item.getId());

				oADao.insert(con, oOAct);
			}

			for (OpportunityAction oAct : changesSet1.deleted) {
				oAIDao.deleteByOpportunityAction(con, oAct.getId());
				oADDao.deleteByOpportunityAction(con, oAct.getId());
				oADao.deleteById(con, oAct.getId());
			}

			for (OpportunityAction oAct : changesSet1.updated) {
				
				List <OpportunityActionInterlocutor> oldActInt = null;
				for(OOpportunityActionInterlocutor oOActInt : oAIDao.selectByOpportunityAction(con, oAct.getId())){
					oldActInt.add(createOpportunityActionInterlocutor(oOActInt));
				}
				
				//Opportunity Action Interlocutors
				LangUtils.CollectionChangeSet<OpportunityActionInterlocutor> changesSet4 = LangUtils.getCollectionChanges(oldActInt, oAct.getActionInterlocutors());
				
				for (OpportunityActionInterlocutor oActInt : changesSet4.inserted) {

					OOpportunityActionInterlocutor oOActInt = createOOpportunityActionInterlocutor(oActInt);

					oOActInt.setId(oAIDao.getSequence(con).intValue());
					oOActInt.setOpportunityActionId(oAct.getId());

					oAIDao.insert(con, oOActInt);
				}

				for (OpportunityActionInterlocutor oActInt : changesSet4.deleted) {
					oAIDao.deleteById(con, oActInt.getId());
				}

				for (OpportunityActionInterlocutor oActInt : changesSet4.updated) {
					OOpportunityActionInterlocutor oOActInt = createOOpportunityActionInterlocutor(oActInt);
					oOActInt.setOpportunityActionId(oAct.getId());

					oAIDao.update(con, oOActInt);
				}
				
				List <OpportunityActionDocument> oldActDoc = null;
				for(OOpportunityActionDocument oOActDoc : oADDao.selectByOpportunityAction(con, oAct.getId())){
					oldActDoc.add(createOpportunityActionDocument(oOActDoc));
				}
				
				//Opportunity Action Documents
				LangUtils.CollectionChangeSet<OpportunityActionDocument> changesSet5 = LangUtils.getCollectionChanges(oldActDoc, oAct.getActionDocuments());
				
				File destDir = new File(getOpportunityActionPath(oAct.getId()));
				if (!destDir.exists()) {
					destDir.mkdirs();
				}

				for (OpportunityActionDocument oActDoc : changesSet5.inserted) {

					OOpportunityActionDocument oOActDoc = createOOpportunityActionDocument(oActDoc);

					oOActDoc.setOpportunityActionId(oAct.getId());
					oOActDoc.setRevisionTimestamp(revisionTimestamp);
					oOActDoc.setRevisionSequence((short) 1);

					oADDao.insert(con, oOActDoc);

					File file = files.get(oActDoc.getId());
					String fileName = PathUtils.addFileExension(file.getName(), FilenameUtils.getExtension(oActDoc.getFileName()));
					File destFile = new File(destDir, fileName);

					FileUtils.copyFile(file, destFile);
				}

				for (OpportunityActionDocument oActDoc : changesSet5.deleted) {

					String fileName = PathUtils.addFileExension(oActDoc.getId().toString(), FilenameUtils.getExtension(oActDoc.getFileName()));
					File file = new File(destDir, fileName);

					FileUtils.deleteQuietly(file);
					oADDao.deleteById(con, oActDoc.getId());
				}
				
				OOpportunityAction oOAct = createOOpportunityAction(oAct);
				oOAct.setOpportunityId(item.getId());

				oADao.update(con, oOAct);
			}
			
			//Opportunity Interlocutors
			LangUtils.CollectionChangeSet<OpportunityInterlocutor> changesSet2 = LangUtils.getCollectionChanges(oldO.getInterlocutors(), item.getInterlocutors());
			
			for (OpportunityInterlocutor oInt : changesSet2.inserted) {

				OOpportunityInterlocutor oOInt = createOOpportunityInterlocutor(oInt);

				oOInt.setId(oIDao.getSequence(con).intValue());
				oOInt.setOpportunityId(item.getId());

				oIDao.insert(con, oOInt);
			}

			for (OpportunityInterlocutor oInt : changesSet2.deleted) {
				oIDao.deleteById(con, oInt.getId());
			}

			for (OpportunityInterlocutor oInt : changesSet2.updated) {
				OOpportunityInterlocutor oOInt = createOOpportunityInterlocutor(oInt);
				oOInt.setOpportunityId(item.getId());

				oIDao.update(con, oOInt);
			}

			//Opportunity Documents
			LangUtils.CollectionChangeSet<OpportunityDocument> changesSet3 = LangUtils.getCollectionChanges(oldO.getDocuments(), item.getDocuments());

			File destDir = new File(getOpportunityPath(item.getId()));
			if (!destDir.exists()) {
				destDir.mkdirs();
			}

			for (OpportunityDocument oDoc : changesSet3.inserted) {

				OOpportunityDocument oODoc = createOOpportunityDocument(oDoc);

				oODoc.setOpportunityId(item.getId());
				oODoc.setRevisionTimestamp(revisionTimestamp);
				oODoc.setRevisionSequence((short) 1);

				oDDao.insert(con, oODoc);

				File file = files.get(oDoc.getId());
				String fileName = PathUtils.addFileExension(file.getName(), FilenameUtils.getExtension(oDoc.getFileName()));
				File destFile = new File(destDir, fileName);

				FileUtils.copyFile(file, destFile);
			}

			for (OpportunityDocument oDoc : changesSet3.deleted) {

				String fileName = PathUtils.addFileExension(oDoc.getId().toString(), FilenameUtils.getExtension(oDoc.getFileName()));
				File file = new File(destDir, fileName);

				FileUtils.deleteQuietly(file);
				oDDao.deleteById(con, oDoc.getId());
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

	private Opportunity createOpportunity(OOpportunity oOpt) {
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
		o.setWon(oOpt.getWon());

		return o;
	}

	private OOpportunity createOOpportunity(Opportunity o) {
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
		oOpt.setWon(o.getWon());

		return oOpt;
	}
	
	private OOpportunityAction createOOpportunityAction(OpportunityAction act) {

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

		return oAct;
	}

	private OpportunityAction createOpportunityAction(OOpportunityAction oAct) {

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

		return act;
	}
	
	private OOpportunityActionInterlocutor createOOpportunityActionInterlocutor(OpportunityActionInterlocutor actInterl) {

		if (actInterl == null) {
			return null;
		}

		OOpportunityActionInterlocutor oActInterl = new OOpportunityActionInterlocutor();
		oActInterl.setId(actInterl.getId());
		oActInterl.setOpportunityActionId(actInterl.getOpportunityActionId());
		oActInterl.setContactId(actInterl.getContactId());

		return oActInterl;
	}

	private OpportunityActionInterlocutor createOpportunityActionInterlocutor(OOpportunityActionInterlocutor oActInterl) {

		if (oActInterl == null) {
			return null;
		}

		OpportunityActionInterlocutor actInterl = new OpportunityActionInterlocutor();
		actInterl.setId(oActInterl.getId());
		actInterl.setOpportunityActionId(oActInterl.getOpportunityActionId());
		actInterl.setContactId(oActInterl.getContactId());

		return actInterl;
	}
	
	private OOpportunityActionDocument createOOpportunityActionDocument(OpportunityActionDocument actDoc) {

		if (actDoc == null) {
			return null;
		}

		OOpportunityActionDocument oActDoc = new OOpportunityActionDocument();
		oActDoc.setId(actDoc.getId());
		oActDoc.setOpportunityActionId(actDoc.getOpportunityActionId());
		oActDoc.setFilename(actDoc.getFileName());
		oActDoc.setSize(actDoc.getSize());
		oActDoc.setMediaTpye(actDoc.getMediaType());

		return oActDoc;
	}

	private OpportunityActionDocument createOpportunityActionDocument(OOpportunityActionDocument oActDoc) {

		if (oActDoc == null) {
			return null;
		}

		OpportunityActionDocument actDoc = new OpportunityActionDocument();
		actDoc.setId(oActDoc.getId());
		actDoc.setOpportunityActionId(oActDoc.getOpportunityActionId());
		actDoc.setFileName(oActDoc.getFilename());
		actDoc.setSize(oActDoc.getSize());
		actDoc.setMediaType(oActDoc.getMediaTpye());

		return actDoc;
	}
	
	private OOpportunityInterlocutor createOOpportunityInterlocutor(OpportunityInterlocutor interl) {

		if (interl == null) {
			return null;
		}

		OOpportunityInterlocutor oInterl = new OOpportunityInterlocutor();
		oInterl.setId(interl.getId());
		oInterl.setOpportunityId(interl.getOpportunityId());
		oInterl.setContactId(interl.getContactId());

		return oInterl;
	}

	private OpportunityInterlocutor createOpportunityInterlocutor(OOpportunityInterlocutor oInterl) {

		if (oInterl == null) {
			return null;
		}

		OpportunityInterlocutor interl = new OpportunityInterlocutor();
		interl.setId(oInterl.getId());
		interl.setOpportunityId(oInterl.getOpportunityId());
		interl.setContactId(oInterl.getContactId());

		return interl;
	}
	
	private OOpportunityDocument createOOpportunityDocument(OpportunityDocument doc) {

		if (doc == null) {
			return null;
		}

		OOpportunityDocument oDoc = new OOpportunityDocument();
		oDoc.setId(doc.getId());
		oDoc.setOpportunityId(doc.getOpportunityId());
		oDoc.setFilename(doc.getFileName());
		oDoc.setSize(doc.getSize());
		oDoc.setMediaTpye(doc.getMediaType());

		return oDoc;
	}

	private OpportunityDocument createOpportunityDocument(OOpportunityDocument oDoc) {

		if (oDoc == null) {
			return null;
		}

		OpportunityDocument doc = new OpportunityDocument();
		doc.setId(oDoc.getId());
		doc.setOpportunityId(oDoc.getOpportunityId());
		doc.setFileName(oDoc.getFilename());
		doc.setSize(oDoc.getSize());
		doc.setMediaType(oDoc.getMediaTpye());

		return doc;
	}
	
	public String getOpportunityPath(Integer id) {

		return PathUtils.concatPathParts(WT.getServiceHomePath(getTargetProfileId().getDomainId(), SERVICE_ID),
				"Opportunity", id.toString());

	}

	public OpportunityDocument getOpportunityDocument(Integer opportunityDocumentId) throws WTException {
		Connection con = null;
		OpportunityDocumentDAO oDDao = OpportunityDocumentDAO.getInstance();
		OpportunityDocument doc = null;
		try {

			con = WT.getConnection(SERVICE_ID);

			doc = createOpportunityDocument(oDDao.select(con, opportunityDocumentId));

			return doc;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public FileContent getOpportunityDocumentContent(Integer opportunityDocumentId) throws WTException {

		OpportunityDocument doc = getOpportunityDocument(opportunityDocumentId);

		if (doc == null) {
			return null;
		}

		File file = new File(getOpportunityPath(doc.getOpportunityId()),
				PathUtils.addFileExension(doc.getId().toString(),
						PathUtils.getFileExtension(doc.getFileName())));

		if (!file.canRead() || !file.exists()) {
			throw new WTException("File not exists or not readable [{0}]", file.getAbsolutePath());
		}
		try {
			return new FileContent(doc.getFileName(), file.length(), doc.getMediaType(), new FileInputStream(file));
		} catch (FileNotFoundException ex) {
			throw new WTException("File not found", ex);
		}
	}
	
	public String getOpportunityActionPath(Integer id) {

		return PathUtils.concatPathParts(WT.getServiceHomePath(getTargetProfileId().getDomainId(), SERVICE_ID),
				"OpportunityAction", id.toString());

	}

	public OpportunityActionDocument getOpportunityActionDocument(Integer opportunityActionDocumentId) throws WTException {
		Connection con = null;
		OpportunityActionDocumentDAO oADDao = OpportunityActionDocumentDAO.getInstance();
		OpportunityActionDocument actDoc = null;
		try {

			con = WT.getConnection(SERVICE_ID);

			actDoc = createOpportunityActionDocument(oADDao.select(con, opportunityActionDocumentId));

			return actDoc;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public FileContent getOpportunityActionDocumentContent(Integer opportunityActionDocumentId) throws WTException {

		OpportunityActionDocument actDoc = getOpportunityActionDocument(opportunityActionDocumentId);

		if (actDoc == null) {
			return null;
		}

		File file = new File(getOpportunityActionPath(actDoc.getOpportunityActionId()),
				PathUtils.addFileExension(actDoc.getId().toString(),
						PathUtils.getFileExtension(actDoc.getFileName())));

		if (!file.canRead() || !file.exists()) {
			throw new WTException("File not exists or not readable [{0}]", file.getAbsolutePath());
		}
		try {
			return new FileContent(actDoc.getFileName(), file.length(), actDoc.getMediaType(), new FileInputStream(file));
		} catch (FileNotFoundException ex) {
			throw new WTException("File not found", ex);
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

			report = createWorkReport(wrkDao.selectById(con, workReportId));

			for (OWorkReportRow oWrkDetail : wrkDDao.selectByWorkReport(con, workReportId)) {
				report.getDetails().add(createWorkReportRow(oWrkDetail));
			}

			for (OWorkReportAttachment oAtt : attDao.selectByWorkReport(con, workReportId)) {
				report.getAttachments().add(createWorkReportAttachment(oAtt));
			}

			return report;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public OWorkReport addWorkReport(WorkReport wrkRpt, HashMap<String, File> files) throws WTException {

		Connection con = null;

		try {
			con = WT.getConnection(SERVICE_ID, false);

			DateTime revisionTimestamp = createRevisionTimestamp();
			String workReportPath = "";

			WorkReportDAO wrkDao = WorkReportDAO.getInstance();
			WorkReportRowDAO wrkDDao = WorkReportRowDAO.getInstance();
			WorkReportAttachmentDAO attDao = WorkReportAttachmentDAO.getInstance();

			OWorkReport newWorkReport = createOWorkReport(wrkRpt);
			newWorkReport.setWorkReportId(IdentifierUtils.getUUID());
			newWorkReport.setWorkReportNo(wrkDao.getWorkReportSequence(con) + "-" + newWorkReport.getFromDate().year().getAsText());

			OWorkReportRow newWrkDetail = null;
			for (WorkReportRow wrkDetail : wrkRpt.getDetails()) {
				newWrkDetail = createOWorkReportRow(wrkDetail);
				newWrkDetail.setId(wrkDDao.getSequence(con).intValue());
				newWrkDetail.setWorkReportId(newWorkReport.getWorkReportId());

				wrkDDao.insert(con, newWrkDetail);
			}

			for (WorkReportAttachment attachment : wrkRpt.getAttachments()) {
				OWorkReportAttachment oAtt = createOWorkReportAttachment(attachment);
				oAtt.setWorkReportId(newWorkReport.getWorkReportId());
				oAtt.setRevisionTimestamp(revisionTimestamp);
				oAtt.setRevisionSequence((short) 1);

				attDao.insert(con, oAtt);
			}

			wrkDao.insert(con, newWorkReport, revisionTimestamp);

			File destDir = new File(getWorkReportPath(newWorkReport.getWorkReportId()));
			if (!destDir.exists()) {
				destDir.mkdir();
			}
			
			for (WorkReportAttachment attachment : wrkRpt.getAttachments()) {

				File file = files.get(attachment.getWorkReportAttachmentId());
				String fileName = PathUtils.addFileExension(file.getName(), FilenameUtils.getExtension(attachment.getFileName()));
				File destFile = new File(destDir, fileName);

				FileUtils.copyFile(file, destFile);
			}

			DbUtils.commitQuietly(con);

			return newWorkReport;

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

	public OWorkReport updateWorkReport(WorkReport item, HashMap<String, File> files) throws WTException {

		Connection con = null;
		WorkReportDAO wrkDao = WorkReportDAO.getInstance();
		WorkReportRowDAO detailDao = WorkReportRowDAO.getInstance();
		WorkReportAttachmentDAO attDao = WorkReportAttachmentDAO.getInstance();
		try {
			DateTime revisionTimestamp = createRevisionTimestamp();

			WorkReport oldWorkReport = getWorkReport(item.getWorkReportId());

			con = WT.getConnection(SERVICE_ID, false);

			OWorkReport report = createOWorkReport(item);

			wrkDao.update(con, report, revisionTimestamp);

			LangUtils.CollectionChangeSet<WorkReportRow> changesSet1 = LangUtils.getCollectionChanges(oldWorkReport.getDetails(), item.getDetails());
			for (WorkReportRow wrkDetail : changesSet1.inserted) {

				OWorkReportRow oWrkDetail = createOWorkReportRow(wrkDetail);

				oWrkDetail.setId(detailDao.getSequence(con).intValue());
				oWrkDetail.setWorkReportId(item.getWorkReportId());

				detailDao.insert(con, oWrkDetail);
			}

			for (WorkReportRow wrkDetail : changesSet1.deleted) {
				detailDao.deleteById(con, wrkDetail.getId());
			}

			for (WorkReportRow wrkDetail : changesSet1.updated) {
				OWorkReportRow oWrkDetail = createOWorkReportRow(wrkDetail);
				oWrkDetail.setWorkReportId(item.getWorkReportId());

				detailDao.update(con, oWrkDetail);
			}

			LangUtils.CollectionChangeSet<WorkReportAttachment> changesSet2 = LangUtils.getCollectionChanges(oldWorkReport.getAttachments(), item.getAttachments());

			File destDir = new File(getWorkReportPath(item.getWorkReportId()));
			if (!destDir.exists()) {
				destDir.mkdirs();
			}

			for (WorkReportAttachment wrkAttachment : changesSet2.inserted) {

				OWorkReportAttachment oWrkAttachment = createOWorkReportAttachment(wrkAttachment);

				oWrkAttachment.setWorkReportId(item.getWorkReportId());
				oWrkAttachment.setRevisionTimestamp(revisionTimestamp);
				oWrkAttachment.setRevisionSequence((short) 1);

				attDao.insert(con, oWrkAttachment);

				File file = files.get(wrkAttachment.getWorkReportAttachmentId());
				String fileName = PathUtils.addFileExension(file.getName(), FilenameUtils.getExtension(wrkAttachment.getFileName()));
				File destFile = new File(destDir, fileName);

				FileUtils.copyFile(file, destFile);
			}

			for (WorkReportAttachment wrkAttachment : changesSet2.deleted) {

				String fileName = PathUtils.addFileExension(wrkAttachment.getWorkReportAttachmentId(), FilenameUtils.getExtension(wrkAttachment.getFileName()));
				File file = new File(destDir, fileName);

				FileUtils.deleteQuietly(file);
				attDao.deleteById(con, wrkAttachment.getWorkReportAttachmentId());
			}

			DbUtils.commitQuietly(con);

			return report;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} catch (IOException ex) {
			throw new WTException(ex, "DB error");
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

	private WorkReport createWorkReport(OWorkReport oWrkRpt) {
		if (oWrkRpt == null) {
			return null;
		}
		WorkReport wrkRpt = new WorkReport();
		wrkRpt.setWorkReportId(oWrkRpt.getWorkReportId());
		wrkRpt.setWorkReportNo(oWrkRpt.getWorkReportNo());
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

		return wrkRpt;
	}

	private OWorkReport createOWorkReport(WorkReport wrkRpt) {
		if (wrkRpt == null) {
			return null;
		}
		OWorkReport oWrkRpt = new OWorkReport();
		oWrkRpt.setWorkReportId(wrkRpt.getWorkReportId());
		oWrkRpt.setWorkReportNo(wrkRpt.getWorkReportNo());
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

		return oWrkRpt;
	}

	private WorkReportRow createWorkReportRow(OWorkReportRow oWrkDetail) {

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

	private OWorkReportRow createOWorkReportRow(WorkReportRow wrkDetail) {

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

	private OWorkReportAttachment createOWorkReportAttachment(WorkReportAttachment attachment) {

		if (attachment == null) {
			return null;
		}

		OWorkReportAttachment oAtt = new OWorkReportAttachment();
		oAtt.setWorkReportAttachmentId(attachment.getWorkReportAttachmentId());
		oAtt.setWorkReportId(attachment.getWorkReportId());
		oAtt.setFilename(attachment.getFileName());
		oAtt.setSize(attachment.getSize());
		oAtt.setMediaTpye(attachment.getMediaType());

		return oAtt;
	}

	private WorkReportAttachment createWorkReportAttachment(OWorkReportAttachment oAttachment) {

		if (oAttachment == null) {
			return null;
		}

		WorkReportAttachment att = new WorkReportAttachment();
		att.setWorkReportAttachmentId(oAttachment.getWorkReportAttachmentId());
		att.setWorkReportId(oAttachment.getWorkReportId());
		att.setFileName(oAttachment.getFilename());
		att.setSize(oAttachment.getSize());
		att.setMediaType(oAttachment.getMediaTpye());

		return att;
	}

	public String getWorkReportPath(String workReportId) {

		return PathUtils.concatPathParts(WT.getServiceHomePath(getTargetProfileId().getDomainId(), SERVICE_ID),
				"WorkReports", workReportId);

	}

	public WorkReportAttachment getWorkReportAttachment(String workReportAttachmentId) throws WTException {
		Connection con = null;
		WorkReportAttachmentDAO attDao = WorkReportAttachmentDAO.getInstance();
		WorkReportAttachment attachment = null;
		try {

			con = WT.getConnection(SERVICE_ID);

			attachment = createWorkReportAttachment(attDao.select(con, workReportAttachmentId));

			return attachment;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public FileContent getWorkReportAttachmentContent(String workReportAttachmentId) throws WTException {

		WorkReportAttachment attachment = getWorkReportAttachment(workReportAttachmentId);

		if (attachment == null) {
			return null;
		}

		File file = new File(getWorkReportPath(attachment.getWorkReportId()),
				PathUtils.addFileExension(attachment.getWorkReportAttachmentId(),
						PathUtils.getFileExtension(attachment.getFileName())));

		if (!file.canRead() || !file.exists()) {
			throw new WTException("File not exists or not readable [{0}]", file.getAbsolutePath());
		}
		try {
			return new FileContent(attachment.getFileName(), file.length(), attachment.getMediaType(), new FileInputStream(file));
		} catch (FileNotFoundException ex) {
			throw new WTException("File not found", ex);
		}
	}
	
	private OpportunityField createOpportunityField(OOpportunityField oOField) {

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

		return oField;
	}

	private OOpportunityField createOOpportunityField(OpportunityField oField) {

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

		return oOField;
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
					setting.getGeneralFields().add(createOpportunityField(oOField));
				}

				for (OOpportunityField oOField : ofDao.selectByDomainIdTabId(con, getTargetProfileId().getDomainId(), EnumUtils.toSerializedName(OpportunityField.Tab.VISIT_REPORT))) {
					setting.getVisitReportFields().add(createOpportunityField(oOField));
				}
				
				for (OOpportunityField oOField : ofDao.selectByDomainIdTabId(con, getTargetProfileId().getDomainId(), EnumUtils.toSerializedName(OpportunityField.Tab.NOTES_SIGNATURE))) {
					setting.getNotesSignatureFields().add(createOpportunityField(oOField));
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

				OOpportunityField oField = createOOpportunityField(field);

				ofDao.update(con, oField);
			}
			
			for (OpportunityField field : item.getVisitReportFields()) {

				OOpportunityField oField = createOOpportunityField(field);

				ofDao.update(con, oField);
			}
			
			for (OpportunityField field : item.getNotesSignatureFields()) {

				OOpportunityField oField = createOOpportunityField(field);

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

			setting = createWorkReportSetting(wSettDao.selectByDomainId(con, getTargetProfileId().getDomainId()));
			if (setting != null) {
				setting.setWorkReportSequence(wSettDao.getWorkReportSequence(con).intValue());

				for (OWorkType oType : wrkTDao.selectByDomain(con, getTargetProfileId().getDomainId())) {
					setting.getTypes().add(createWorkType(oType));
				}

				for (OBusinessTrip oTrip : tripDao.selectByDomain(con, getTargetProfileId().getDomainId())) {
					setting.getTrips().add(createBusinessTrip(oTrip));
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

			lr = createLeaveRequest(lrDao.selectById(con, leaveRequestId));

			for (OLeaveRequestDocument oDoc : docDao.selectByLeaveRequest(con, leaveRequestId)) {
				lr.getDocuments().add(createLeaveRequestDocument(oDoc));
			}

			return lr;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public OLeaveRequest addLeaveRequest(LeaveRequest lv, HashMap<String, File> files) throws WTException {

		Connection con = null;

		try {
			con = WT.getConnection(SERVICE_ID, false);

			DateTime employeeReqTimestamp = createRevisionTimestamp();

			LeaveRequestDAO lrDao = LeaveRequestDAO.getInstance();
			LeaveRequestDocumentDAO docDao = LeaveRequestDocumentDAO.getInstance();

			OLeaveRequest newLr = createOLeaveRequest(lv);
			newLr.setLeaveRequestId(lrDao.getLeaveRequestSequence(con).intValue());
			newLr.setDomainId(getTargetProfileId().getDomainId());
			newLr.setEmployeeReqTimestamp(employeeReqTimestamp);
			newLr.setStatus("O");
			newLr.setEmployeeCancReq(false);

			for (LeaveRequestDocument doc : lv.getDocuments()) {
				OLeaveRequestDocument oDoc = createOLeaveRequestDocument(doc);
				oDoc.setLeaveRequestId(newLr.getLeaveRequestId());
				oDoc.setRevisionTimestamp(employeeReqTimestamp);
				oDoc.setRevisionSequence((short) 1);

				docDao.insert(con, oDoc);
			}

			lrDao.insert(con, newLr, employeeReqTimestamp);

			File destDir = new File(getLeaveRequestPath(newLr.getLeaveRequestId()));
			if (!destDir.exists()) {
				destDir.mkdir();
			}
			
			for (LeaveRequestDocument doc : lv.getDocuments()) {

				File file = files.get(doc.getLeaveRequestDocumentId());
				String fileName = PathUtils.addFileExension(file.getName(), FilenameUtils.getExtension(doc.getFileName()));
				File destFile = new File(destDir, fileName);

				FileUtils.copyFile(file, destFile);
			}

			DbUtils.commitQuietly(con);

			notifyLeaveRequest(newLr);
			
			return newLr;

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

	public OLeaveRequest updateLeaveRequest(LeaveRequest item, HashMap<String, File> files, boolean sendMail) throws WTException, MessagingException, TemplateException {
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

			OLeaveRequest lr = createOLeaveRequest(item);
			lr.setEmployeeReqTimestamp(revisionTimestamp);
			if(lr.getEmployeeCancReq()) lr.setEmployeeCancReqTimestamp(revisionTimestamp);
			if(lr.getResult() != null){
				lr.setManagerRespTimestamp(revisionTimestamp);
				lr.setStatus("C");
				
				if(lr.getResult() == true){
					//Insert in TimetableEvents
					List<LocalDate> dts = getDateRange(lr.getFromDate(), lr.getToDate());

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
							hRange = getHourRange(lr.getFromHour(), lr.getToHour());
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

			if(files != null){
				LangUtils.CollectionChangeSet<LeaveRequestDocument> changesSet2 = LangUtils.getCollectionChanges(oldLr.getDocuments(), item.getDocuments());

				File destDir = new File(getLeaveRequestPath(item.getLeaveRequestId()));
				if (!destDir.exists()) {
					destDir.mkdirs();
				}

				for (LeaveRequestDocument lrDoc : changesSet2.inserted) {

					OLeaveRequestDocument oLrDoc = createOLeaveRequestDocument(lrDoc);

					oLrDoc.setLeaveRequestId(item.getLeaveRequestId());
					oLrDoc.setRevisionTimestamp(revisionTimestamp);
					oLrDoc.setRevisionSequence((short) 1);

					docDao.insert(con, oLrDoc);

					File file = files.get(lrDoc.getLeaveRequestDocumentId());
					String fileName = PathUtils.addFileExension(file.getName(), FilenameUtils.getExtension(lrDoc.getFileName()));
					File destFile = new File(destDir, fileName);

					FileUtils.copyFile(file, destFile);
				}

				for (LeaveRequestDocument lrDoc : changesSet2.deleted) {

					String fileName = PathUtils.addFileExension(lrDoc.getLeaveRequestDocumentId().toString(), FilenameUtils.getExtension(lrDoc.getFileName()));
					File file = new File(destDir, fileName);

					FileUtils.deleteQuietly(file);
					docDao.deleteById(con, lrDoc.getLeaveRequestDocumentId());
				}
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
	
	public static List<LocalDate> getDateRange(LocalDate start, LocalDate end) {
        List<LocalDate> ret = new ArrayList<LocalDate>();
        LocalDate tmp = start;
        while(tmp.isBefore(end) || tmp.equals(end)) {
            ret.add(tmp);
            tmp = tmp.plusDays(1);
        }
        return ret;
    }
	
	public String getHourRange(String start, String end) {
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

	private LeaveRequest createLeaveRequest(OLeaveRequest oLr) {
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

		return lr;
	}

	private OLeaveRequest createOLeaveRequest(LeaveRequest lr) {
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

		return oLr;
	}

	private OLeaveRequestDocument createOLeaveRequestDocument(LeaveRequestDocument doc) {

		if (doc == null) {
			return null;
		}

		OLeaveRequestDocument oDoc = new OLeaveRequestDocument();
		oDoc.setLeaveRequestDocumentId(doc.getLeaveRequestDocumentId());
		oDoc.setLeaveRequestId(doc.getLeaveRequestId());
		oDoc.setFilename(doc.getFileName());
		oDoc.setSize(doc.getSize());
		oDoc.setMediaTpye(doc.getMediaType());

		return oDoc;
	}

	private LeaveRequestDocument createLeaveRequestDocument(OLeaveRequestDocument oDoc) {

		if (oDoc == null) {
			return null;
		}

		LeaveRequestDocument doc = new LeaveRequestDocument();
		doc.setLeaveRequestDocumentId(oDoc.getLeaveRequestDocumentId());
		doc.setLeaveRequestId(oDoc.getLeaveRequestId());
		doc.setFileName(oDoc.getFilename());
		doc.setSize(oDoc.getSize());
		doc.setMediaType(oDoc.getMediaTpye());

		return doc;
	}

	public String getLeaveRequestPath(Integer leaveRequestId) {

		return PathUtils.concatPathParts(WT.getServiceHomePath(getTargetProfileId().getDomainId(), SERVICE_ID),
				"LeaveRequest", leaveRequestId.toString());

	}

	public LeaveRequestDocument getLeaveRequestDocument(String leaveRequestDocumentId) throws WTException {
		Connection con = null;
		LeaveRequestDocumentDAO docDao = LeaveRequestDocumentDAO.getInstance();
		LeaveRequestDocument doc = null;
		try {

			con = WT.getConnection(SERVICE_ID);

			doc = createLeaveRequestDocument(docDao.select(con, leaveRequestDocumentId));

			return doc;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	public FileContent getLeaveRequestDocumentContent(String leaveRequestDocumentId) throws WTException {

		LeaveRequestDocument doc = getLeaveRequestDocument(leaveRequestDocumentId);

		if (doc == null) {
			return null;
		}

		File file = new File(getLeaveRequestPath(doc.getLeaveRequestId()),
				PathUtils.addFileExension(doc.getLeaveRequestDocumentId().toString(),
						PathUtils.getFileExtension(doc.getFileName())));

		if (!file.canRead() || !file.exists()) {
			throw new WTException("File not exists or not readable [{0}]", file.getAbsolutePath());
		}
		try {
			return new FileContent(doc.getFileName(), file.length(), doc.getMediaType(), new FileInputStream(file));
		} catch (FileNotFoundException ex) {
			throw new WTException("File not found", ex);
		}
	}
	
	public TimetableSetting getTimetableSetting() throws WTException {
		Connection con = null;
		TimetableSettingDAO tSettDao = TimetableSettingDAO.getInstance();
		HolidayDateDAO hdDao = HolidayDateDAO.getInstance();

		TimetableSetting setting = null;
		
		try {
			con = WT.getConnection(SERVICE_ID);

			setting = createTimetableSetting(tSettDao.selectByDomainId(con, getTargetProfileId().getDomainId()));
			
			if (setting != null) {
				for (OHolidayDate oHd : hdDao.selectByDomain(con, getTargetProfileId().getDomainId())) {
					setting.getHolidayDates().add(createHolidayDate(oHd));
				}
			}
			return setting;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	private OTimetableSetting createOTimetableSetting(TimetableSetting tSetting) {
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

	private TimetableSetting createTimetableSetting(OTimetableSetting oTSetting) {
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
	
	public void updateTimetableSetting(TimetableSetting item) throws WTException {

		Connection con = null;
		TimetableSettingDAO tDao = TimetableSettingDAO.getInstance();
		HolidayDateDAO hdDao = HolidayDateDAO.getInstance();

		try {
			con = WT.getConnection(SERVICE_ID, false);

			TimetableSetting oldTimetableSetting = getTimetableSetting();

			OTimetableSetting setting = createOTimetableSetting(item);

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

				OHolidayDate oHd = createOHolidayDate(hd);
				oHd.setDomainId(getTargetProfileId().getDomainId());

				hdDao.insert(con, oHd);
			}

			for (HolidayDate hd : changesSet1.deleted) {
				hdDao.deleteByDomainIdDate(con, hd.getDomainId(), hd.getDate().toDateTimeAtStartOfDay());
			}

			for (HolidayDate hd : changesSet1.updated) {

				OHolidayDate oHd = createOHolidayDate(hd);
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

			OWorkReportSetting newWorkSetting = createOWorkReportSetting(wrkSett);
			newWorkSetting.setWorkReportSettingId(settingDao.getSequence(con).intValue());
			newWorkSetting.setDomainId(getTargetProfileId().getDomainId());

			OWorkType newWrkType = null;
			for (WorkType t : wrkSett.getTypes()) {
				newWrkType = createOWorkType(t);
				newWrkType.setWorkTypeId(typeDao.getSequence(con).intValue());
				newWrkType.setDomainId(wrkSett.getDomainId());

				typeDao.insert(con, newWrkType);
			}

			OBusinessTrip newBusinessTrip = null;
			for (BusinessTrip t : wrkSett.getTrips()) {
				newBusinessTrip = createOBusinessTrip(t);
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

			OWorkReportSetting setting = createOWorkReportSetting(item);

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

				OWorkType oType = createOWorkType(type);

				oType.setWorkTypeId(typeDao.getSequence(con).intValue());
				oType.setDomainId(getTargetProfileId().getDomainId());

				typeDao.insert(con, oType);
			}

			for (WorkType type : changesSet1.deleted) {
				typeDao.deleteById(con, type.getWorkTypeId());
			}

			for (WorkType type : changesSet1.updated) {

				OWorkType oType = createOWorkType(type);

				typeDao.update(con, oType);
			}

			List<BusinessTrip> trips = oldWorkReportSetting == null ? new ArrayList() : oldWorkReportSetting.getTrips();

			CollectionChangeSet<BusinessTrip> changesSet2 = getCollectionChanges(trips, item.getTrips());

			for (BusinessTrip trip : changesSet2.inserted) {

				OBusinessTrip oTrip = createOBusinessTrip(trip);

				oTrip.setBusinessTripId(typeDao.getSequence(con).intValue());
				oTrip.setDomainId(getTargetProfileId().getDomainId());

				tripDao.insert(con, oTrip);
			}

			for (BusinessTrip trip : changesSet2.deleted) {
				tripDao.deleteById(con, trip.getBusinessTripId());
			}

			for (BusinessTrip trip : changesSet2.updated) {

				OBusinessTrip oTrip = createOBusinessTrip(trip);

				tripDao.update(con, oTrip);
			}

			DbUtils.commitQuietly(con);

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	private OWorkReportSetting createOWorkReportSetting(WorkReportSetting wrkSetting) {
		if (wrkSetting == null) {
			return null;
		}
		OWorkReportSetting oSetting = new OWorkReportSetting();
		oSetting.setWorkReportSettingId(wrkSetting.getWorkReportSettingId());
		oSetting.setDomainId(wrkSetting.getDomainId());
		oSetting.setWarrantyText(wrkSetting.getWarrantyText());

		return oSetting;
	}

	private WorkReportSetting createWorkReportSetting(OWorkReportSetting oWrkSetting) {
		if (oWrkSetting == null) {
			return null;
		}
		WorkReportSetting wrkSetting = new WorkReportSetting();
		wrkSetting.setWorkReportSettingId(oWrkSetting.getWorkReportSettingId());
		wrkSetting.setDomainId(oWrkSetting.getDomainId());
		wrkSetting.setWarrantyText(oWrkSetting.getWarrantyText());

		return wrkSetting;
	}

	private WorkType createWorkType(OWorkType oType) {

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

	private OWorkType createOWorkType(WorkType type) {

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
	
	private HolidayDate createHolidayDate(OHolidayDate oHd) {

		if (oHd == null) {
			return null;
		}

		HolidayDate hd = new HolidayDate();
		hd.setDomainId(oHd.getDomainId());
		hd.setDate(oHd.getDate());
		hd.setDescription(oHd.getDescription());

		return hd;
	}

	private OHolidayDate createOHolidayDate(HolidayDate hd) {

		if (hd == null) {
			return null;
		}

		OHolidayDate oHd = new OHolidayDate();
		oHd.setDomainId(hd.getDomainId());
		oHd.setDate(hd.getDate());
		oHd.setDescription(hd.getDescription());

		return oHd;
	}

	private BusinessTrip createBusinessTrip(OBusinessTrip oTrip) {

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

	private OBusinessTrip createOBusinessTrip(BusinessTrip trip) {

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
			return createCompanyPicture(pic);
			
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
			writeLog("COMPANY_UPDATE", String.valueOf(companyId));
			
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
	
	private CompanyPicture createCompanyPicture(OCompanyPicture with) {
		return fillContactPicture(new CompanyPicture(), with);
	}
	
	private CompanyPicture fillContactPicture(CompanyPicture fill, OCompanyPicture with) {
		if ((fill != null) && (with != null)) {
			fill.setWidth(with.getWidth());
			fill.setHeight(with.getHeight());
			fill.setMediaType(with.getMediaType());
			fill.setBytes(with.getBytes());
		}
		return fill;
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
	
	private DrmLineManager createLineManager(ODrmLineManager oLineManager) {

		if (oLineManager == null) {
			return null;
		}

		DrmLineManager manager = new DrmLineManager();
		manager.setDomainId(oLineManager.getDomainId());
		manager.setUserId(oLineManager.getUserId());

		return manager;
	}
	
	private ODrmLineManager createODrmLineManager(DrmLineManager manager) {

		if (manager == null) {
			return null;
		}

		ODrmLineManager oManager = new ODrmLineManager();
		oManager.setDomainId(manager.getDomainId());
		oManager.setUserId(manager.getUserId());

		return oManager;
	}
	
	private UserForManager createUserForManager(ODrmLineManagerUsers oLineManagerUser) {

		if (oLineManagerUser == null) {
			return null;
		}

		UserForManager userForManager = new UserForManager();
		userForManager.setDomainId(oLineManagerUser.getDomainId());
		userForManager.setManagerUserId(oLineManagerUser.getLineManagerUserId());
		userForManager.setUserId(oLineManagerUser.getUserId());

		return userForManager;
	}
	
	private ODrmLineManagerUsers createODrmLineManagerUser(UserForManager ufm) {

		if (ufm == null) {
			return null;
		}

		ODrmLineManagerUsers oLmu = new ODrmLineManagerUsers();
		oLmu.setDomainId(ufm.getDomainId());
		oLmu.setLineManagerUserId(ufm.getManagerUserId());
		oLmu.setUserId(ufm.getUserId());

		return oLmu;
	}

	public DrmLineManager getDrmLineManager(String userId) throws WTException {
		Connection con = null;
		DrmLineManagerDAO mngDao = DrmLineManagerDAO.getInstance();
		DrmUserForManagerDAO ufmngDao = DrmUserForManagerDAO.getInstance();

		DrmLineManager manager = null;
		try {

			con = WT.getConnection(SERVICE_ID);

			manager = createLineManager(mngDao.selectLineManagerByDomainUserId(con, getTargetProfileId().getDomainId(), userId));

			for (ODrmLineManagerUsers olmu : ufmngDao.listByDomainLineManagerUserId(con, getTargetProfileId().getDomainId(), userId)) {
				manager.getAssociatedUsers().add(createUserForManager(olmu));
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

			ODrmLineManager newDrmLineManager = createODrmLineManager(lineManager);
			newDrmLineManager.setDomainId(getTargetProfileId().getDomainId());

			ODrmLineManagerUsers newLmu = null;
			
			for (UserForManager ufm : lineManager.getAssociatedUsers()) {
				ufm.setManagerUserId(newDrmLineManager.getUserId());
				ufm.setDomainId(getTargetProfileId().getDomainId());
				newLmu = createODrmLineManagerUser(ufm);

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
			ODrmLineManager manager = createODrmLineManager(item);
			
			con = WT.getConnection(SERVICE_ID, false);
			
			LangUtils.CollectionChangeSet<UserForManager> changesSet1 = LangUtils.getCollectionChanges(oldDrmLineManage.getAssociatedUsers(), item.getAssociatedUsers());
			for (UserForManager ufm : changesSet1.inserted) {
				final ODrmLineManagerUsers oufm = fillODrmLineManagerUsers(new ODrmLineManagerUsers(), ufm);
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

			employeeProfile = createEmployeeProfile(epDao.selectEmployeeProfileById(con, id));

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

			hProfile = createHourProfile(hpDao.selectHourProfileById(con, id));

			for (OLineHour oEH : lhDao.selectLineHourByHourProfileId(con, id)) {
				hProfile.getLineHours().add(createLineHour(oEH));
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

			OTimetableStamp oTS = createOTimetableStamp(stamp);
			
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
	
	public List<OTimetableStamp> listTimetableStamp() throws WTException {
		Connection con = null;
		TimetableStampDAO tsDao = TimetableStampDAO.getInstance();
		List<OTimetableStamp> tss = null;
		
		try {

			con = WT.getConnection(SERVICE_ID);

			tss = tsDao.getDailyStampsByDomainUserId(con, getTargetProfileId().getDomainId(), getTargetProfileId().getUserId());

			return tss;

		} catch (SQLException | DAOException ex) {
			throw new WTException(ex, "DB error");
		} finally {
			DbUtils.closeQuietly(con);
		}
	}
	
	public List<OTimetableStamp> listTimetableStamps(TimetableStampQuery query) throws WTException {
		Connection con = null;
		TimetableStampDAO tsDao = TimetableStampDAO.getInstance();
		List<OTimetableStamp> tss = null;
		
		try {

			con = WT.getConnection(SERVICE_ID);
			
			query.operatorId = query.operatorId == null ? getTargetProfileId().getUserId() : query.operatorId;
			query.month = query.month == null ? new DateTime().getMonthOfYear() : query.month;
			query.year = query.year == null ? new DateTime().getYear() : query.year;
			
			tss = tsDao.getStampsListByDomainUserDateRange(con, getTargetProfileId().getDomainId(), query.operatorId, query.month, query.year);

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
			OTimetableStamp oTs = createOTimetableStamp(ts);
			
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
		
		try {
			con = WT.getConnection(SERVICE_ID, false);
			TimetableReportDAO trDao = TimetableReportDAO.getInstance();
			TimetableStampDAO tstmpDao = TimetableStampDAO.getInstance();
			TimetableEventDAO teDao = TimetableEventDAO.getInstance();

			if(query != null){
				//Empty Table
				trDao.deleteByDomainId(con, getTargetProfileId().getDomainId());
				
				//Reset Sequence
				trDao.restartTimetableReportTempSequence(con);

				//Get Data - Calculate Working Hours and Extraordinary (If permits from settings)
				if(query.userId == null){
					//Get Data for All Users for Company
					List<OUser> users = listCompanyUsers(query.companyId);
					for (OUser usr : users) {
						ttrs = new ArrayList();
						
						trsf = tstmpDao.getStampsByDomainUserDateRange(con, getTargetProfileId().getDomainId(), query.companyId, usr.getUserId(), query.fromDay, query.month, query.year);
						te = teDao.getEventsByDomainUserDateRange(con, getTargetProfileId().getDomainId(), query.companyId, usr.getUserId(), query.fromDay, query.month, query.year);

						ttrs.addAll(mergeStampByDate(trsf, con));
						ttrs.addAll(mergeEventByDate(createOTimetableReport(te)));
						
						ttrs = mergeStampAndEventByDate(ttrs);
						
						trs.addAll(ttrs);
					}
				}else{
					//Get Data for Single User Selected
					trsf = tstmpDao.getStampsByDomainUserDateRange(con, getTargetProfileId().getDomainId(), query.companyId, query.userId, query.fromDay, query.month, query.year);
					te = teDao.getEventsByDomainUserDateRange(con, getTargetProfileId().getDomainId(), query.companyId, query.userId, query.fromDay, query.month, query.year);

					trs.addAll(mergeStampByDate(trsf, con));
					trs.addAll(mergeEventByDate(createOTimetableReport(te)));
					
					trs = mergeStampAndEventByDate(trs);
				}
				
				//Insert Data in Table
				for(OTimetableReport otr : trs){
					otr.setId(trDao.getTimetableReportTempSequence(con).intValue());
					trDao.insert(con, otr);
				}
			}
			
			//Select for DomainId
			trs = trDao.selectByDomainId(con, getTargetProfileId().getDomainId());
			
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

			trs = trDao.selectByDomainId(con, getTargetProfileId().getDomainId());

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
		
		TimetableSetting setting = createTimetableSetting(tsDao.selectByDomainId(con, getTargetProfileId().getDomainId()));
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
				BigDecimal bd = round(wht, 2);
				
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

			BigDecimal bd = round(wht, 2);
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
	
	private List<OTimetableReport> mergeEventByDate(List<OTimetableReport> trsf){
		
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
	
	private List<OTimetableReport> mergeStampAndEventByDate(List<OTimetableReport> trsf){
		
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
	
	public static BigDecimal round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_DOWN);       
		
        return bd;
    }
	
	private List<OTimetableReport> createOTimetableReport(List<OTimetableEvent> tel){
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
	
	public void updateTimetableReport(TimetableReport item) throws WTException {
		Connection con = null;
		TimetableReportDAO trDao = TimetableReportDAO.getInstance();
		try {
			con = WT.getConnection(SERVICE_ID, false);

			OTimetableReport oTr = createOTimetableReport(item);

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
	
	private OTimetableReport createOTimetableReport(TimetableReport tr) {
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
	
	public static String buildLeaveRequestReplyPublicUrl(String publicBaseUrl, String leaveRequestPublicId, String recipientEmail, String crud, String resp) {
		String s = PublicService.PUBPATH_CONTEXT_LEAVE_REQUEST + "/" + leaveRequestPublicId + "/" + PublicService.LeaveRequestUrlPath.TOKEN_REPLY + "?aid=" + recipientEmail + "&crud=" + crud + "&resp=" + resp;
		return PathUtils.concatPaths(publicBaseUrl, s);
	}
}
