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
import com.sonicle.webtop.drm.bol.ODrmProfile;
import com.sonicle.webtop.drm.bol.OProfileMasterdata;
import com.sonicle.webtop.drm.bol.OProfileSupervisedUser;
import com.sonicle.webtop.drm.bol.OProfileMember;
import com.sonicle.webtop.drm.bol.OWorkReport;
import com.sonicle.webtop.drm.bol.OWorkReportAttachment;
import com.sonicle.webtop.drm.bol.OWorkReportRow;
import com.sonicle.webtop.drm.bol.OWorkReportSetting;
import com.sonicle.webtop.drm.bol.OWorkType;
import com.sonicle.webtop.drm.bol.model.RBWorkReportRows;
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
import com.sonicle.webtop.drm.dal.DrmProfileDAO;
import com.sonicle.webtop.drm.dal.ProfileMasterdataDAO;
import com.sonicle.webtop.drm.dal.ProfileSupervisedUserDAO;
import com.sonicle.webtop.drm.dal.ProfileMemberDAO;
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
import com.sonicle.webtop.drm.model.DrmProfile;
import com.sonicle.webtop.drm.model.FileContent;
import com.sonicle.webtop.drm.model.ProfileMasterdata;
import com.sonicle.webtop.drm.model.ProfileSupervisedUser;
import com.sonicle.webtop.drm.model.ProfileMember;
import com.sonicle.webtop.drm.model.WorkReport;
import com.sonicle.webtop.drm.model.WorkReportAttachment;
import com.sonicle.webtop.drm.model.WorkReportRow;
import com.sonicle.webtop.drm.model.WorkReportSetting;
import com.sonicle.webtop.drm.model.WorkType;
import eu.medsea.mimeutil.MimeType;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.imgscalr.Scalr;

/**
 *
 * @author stfnnvl
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

	//-----------------------------------------------------------------------------------------
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

	//qnd arrica dall'api
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
//		group.setSupervisiorUserId(oGroup.getSupervisiorUserId());
//		group.setCustomerId(oGroup.getCustomerId());

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
//		oGroup.setSupervisiorUserId(group.getSupervisiorUserId());
//		oGroup.setCustomerId(group.getCustomerId());

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

	//-----------------------------------------------------------------
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

	//-----------------------------------------------------------------
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

	//qnd arrica dall'api
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

			//INSERT ATTACHMENT IN DB
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
			//TODO CREAZIONE REALE DEL FILE SU FILESYSTEM
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
		//WorkReportRowDAO wrkDDao = WorkReportRowDAO.getInstance();

		try {
			con = WT.getConnection(SERVICE_ID, false);

			wrkDao.logicalDelete(con, workReportId, createRevisionTimestamp());

			//wrkDDao.deleteByWorkReport(con, workReportId);
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
}
