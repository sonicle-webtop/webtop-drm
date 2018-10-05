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
 * types.
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
import com.sonicle.commons.net.IPUtils;
import com.sonicle.commons.web.Crud;
import com.sonicle.commons.web.ServletUtils;
import com.sonicle.commons.web.ServletUtils.IntegerArray;
import com.sonicle.commons.web.ServletUtils.StringArray;
import com.sonicle.commons.web.json.JsonResult;
import com.sonicle.commons.web.json.MapItem;
import com.sonicle.commons.web.json.Payload;
import com.sonicle.commons.web.json.PayloadAsList;
import com.sonicle.commons.web.json.extjs.ExtTreeNode;
import com.sonicle.commons.web.json.extjs.LookupMeta;
import com.sonicle.commons.web.json.extjs.ResultMeta;
import com.sonicle.webtop.contacts.IContactsManager;
import com.sonicle.webtop.contacts.model.ContactItemEx;
import com.sonicle.webtop.contacts.model.FolderContacts;
import com.sonicle.webtop.core.CoreUserSettings;
import com.sonicle.webtop.core.app.WT;
import com.sonicle.webtop.core.app.WebTopSession;
import com.sonicle.webtop.core.app.WebTopSession.UploadedFile;
import com.sonicle.webtop.core.bol.OGroup;
import com.sonicle.webtop.core.bol.OUser;
import com.sonicle.webtop.core.bol.js.JsSimple;
import com.sonicle.webtop.core.bol.js.JsSimpleSource;
import com.sonicle.webtop.core.io.output.AbstractReport;
import com.sonicle.webtop.core.io.output.ReportConfig;
import com.sonicle.webtop.core.model.Causal;
import com.sonicle.webtop.core.model.CausalExt;
import com.sonicle.webtop.core.model.MasterData;
import com.sonicle.webtop.core.sdk.BaseService;
import com.sonicle.webtop.core.sdk.UserProfile;
import com.sonicle.webtop.core.sdk.UserProfileId;
import com.sonicle.webtop.core.sdk.WTException;
import com.sonicle.webtop.drm.bol.OBusinessTrip;
import com.sonicle.webtop.drm.bol.OCompany;
import com.sonicle.webtop.drm.bol.ODocStatus;
import com.sonicle.webtop.drm.bol.ODrmFolder;
import com.sonicle.webtop.drm.bol.ODrmGroup;
import com.sonicle.webtop.drm.bol.ODrmLineManager;
import com.sonicle.webtop.drm.bol.ODrmProfile;
import com.sonicle.webtop.drm.bol.OEmployeeProfile;
import com.sonicle.webtop.drm.bol.OHourProfile;
import com.sonicle.webtop.drm.bol.OLeaveRequest;
import com.sonicle.webtop.drm.bol.OLeaveRequestType;
import com.sonicle.webtop.drm.bol.OOpportunity;
import com.sonicle.webtop.drm.bol.OOpportunityAction;
import com.sonicle.webtop.drm.bol.OOpportunityField;
import com.sonicle.webtop.drm.bol.OTimetableReport;
import com.sonicle.webtop.drm.bol.OTimetableStamp;
import com.sonicle.webtop.drm.bol.OWorkReport;
import com.sonicle.webtop.drm.bol.OWorkType;
import com.sonicle.webtop.drm.bol.VOpportunityEntry;
import com.sonicle.webtop.drm.bol.js.JsCompany;
import com.sonicle.webtop.drm.bol.js.JsGridCompanies;
import com.sonicle.webtop.drm.bol.js.JsGridDocStatuses;
import com.sonicle.webtop.drm.bol.js.JsDocStatus;
import com.sonicle.webtop.drm.bol.js.JsDrmFolder;
import com.sonicle.webtop.drm.bol.js.JsDrmGroup;
import com.sonicle.webtop.drm.bol.js.JsDrmLineManager;
import com.sonicle.webtop.drm.bol.js.JsDrmProfile;
import com.sonicle.webtop.drm.bol.js.JsEmployeeProfile;
import com.sonicle.webtop.drm.bol.js.JsGridEmployeeProfile;
import com.sonicle.webtop.drm.bol.js.JsGridFolders;
import com.sonicle.webtop.drm.bol.js.JsGridHourProfile;
import com.sonicle.webtop.drm.bol.js.JsGridLeaveRequest;
import com.sonicle.webtop.drm.bol.js.JsGridLineManager;
import com.sonicle.webtop.drm.bol.js.JsGridOpportunity;
import com.sonicle.webtop.drm.bol.js.JsGridOpportunityAction;
import com.sonicle.webtop.drm.bol.js.JsGridProfiles;
import com.sonicle.webtop.drm.bol.js.JsGridTimetableReport;
import com.sonicle.webtop.drm.bol.js.JsGridTimetableStamp;
import com.sonicle.webtop.drm.bol.js.JsGridTimetableStampList;
import com.sonicle.webtop.drm.bol.js.JsGridWorkReports;
import com.sonicle.webtop.drm.bol.js.JsHourProfile;
import com.sonicle.webtop.drm.bol.js.JsLeaveRequest;
import com.sonicle.webtop.drm.bol.js.JsOpportunity;
import com.sonicle.webtop.drm.bol.js.JsOpportunityAction;
import com.sonicle.webtop.drm.bol.js.JsOpportunityField;
import com.sonicle.webtop.drm.bol.js.JsOpportunitySetting;
import com.sonicle.webtop.drm.bol.js.JsTimetableSetting;
import com.sonicle.webtop.drm.bol.js.JsTimetableStamp;
import com.sonicle.webtop.drm.bol.js.JsWorkReport;
import com.sonicle.webtop.drm.bol.js.JsWorkReportSetting;
import com.sonicle.webtop.drm.bol.model.RBTimetableReport;
import com.sonicle.webtop.drm.bol.model.RBWorkReport;
import com.sonicle.webtop.drm.model.Company;
import com.sonicle.webtop.drm.model.CompanyPicture;
import com.sonicle.webtop.drm.model.DocStatus;
import com.sonicle.webtop.drm.model.DrmFolder;
import com.sonicle.webtop.drm.model.DrmGroup;
import com.sonicle.webtop.drm.model.DrmLineManager;
import com.sonicle.webtop.drm.model.DrmProfile;
import com.sonicle.webtop.drm.model.EmployeeProfile;
import com.sonicle.webtop.drm.model.FileContent;
import com.sonicle.webtop.drm.model.GroupCategory;
import com.sonicle.webtop.drm.model.HourProfile;
import com.sonicle.webtop.drm.model.LeaveRequest;
import com.sonicle.webtop.drm.model.LeaveRequestDocument;
import com.sonicle.webtop.drm.model.Opportunity;
import com.sonicle.webtop.drm.model.OpportunityAction;
import com.sonicle.webtop.drm.model.OpportunityActionDocument;
import com.sonicle.webtop.drm.model.OpportunityDocument;
import com.sonicle.webtop.drm.model.OpportunityField;
import com.sonicle.webtop.drm.model.OpportunitySetting;
import com.sonicle.webtop.drm.model.TimetableReport;
import com.sonicle.webtop.drm.model.TimetableSetting;
import com.sonicle.webtop.drm.model.TimetableStamp;
import com.sonicle.webtop.drm.model.WorkReport;
import com.sonicle.webtop.drm.model.WorkReportAttachment;
import com.sonicle.webtop.drm.model.WorkReportSetting;
import com.sonicle.webtop.drm.rpt.RptTimetableReport;
import com.sonicle.webtop.drm.rpt.RptWorkReport;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author lssndrvs
 */
public class Service extends BaseService {

	public static final Logger logger = WT.getLogger(Service.class);

	private DrmManager manager;
	private DrmServiceSettings ss;
	private LinkedHashMap<String, RootProgramNode> programs = new LinkedHashMap();

	private LinkedHashMap<String, String> groupCategories = new LinkedHashMap();

	@Override
	public void initialize() throws Exception {

		UserProfileId pid = getEnv().getProfileId();

		manager = (DrmManager) WT.getServiceManager(SERVICE_ID);
		ss = new DrmServiceSettings(SERVICE_ID, pid.getDomainId());

		RootProgramNode prog = null;
		
		prog = new RootProgramNode(lookupResource(DrmTreeNode.OPPORTUNITY), "wtdrm-icon-opportunity-xs");
		programs.put(prog.getId(), prog);

		prog = new RootProgramNode(lookupResource(DrmTreeNode.WORK_REPORT), "wtdrm-icon-workreport-xs");
		programs.put(prog.getId(), prog);

		prog = new RootProgramNode(lookupResource(DrmTreeNode.EXPENSE_NOTE), "wtdrm-icon-expensenote-xs");
		programs.put(prog.getId(), prog);

		prog = new RootProgramNode(lookupResource(DrmTreeNode.TIMETABLE), "wtdrm-icon-timetable-xs");
		prog.addSubPrograms(lookupResource(DrmTreeNode.TIMETABLE_STAMP), "wtdrm-icon-timetable1-xs");
		prog.addSubPrograms(lookupResource(DrmTreeNode.TIMETABLE_REQUEST), "wtdrm-icon-timetable2-xs");
		prog.addSubPrograms(lookupResource(DrmTreeNode.TIMETABLE_REPORT), "wtdrm-icon-timetable3-xs");
		programs.put(prog.getId(), prog);		

		groupCategories.put(EnumUtils.toSerializedName(GroupCategory.IDENTITY), lookupResource("groupCategory.I"));
		groupCategories.put(EnumUtils.toSerializedName(GroupCategory.STRUCTURE), lookupResource("groupCategory.S"));
		groupCategories.put(EnumUtils.toSerializedName(GroupCategory.POLICY), lookupResource("groupCategory.P"));
	}

	@Override
	public void cleanup() throws Exception {
	}

	@Override
	public ServiceVars returnServiceVars() {

		ServiceVars vs = new ServiceVars();

		vs.put("useStatisticCustomer", ss.getUseStatisticCustomer());
		vs.put("printDaysTransfert", ss.getPrintDaysTransfert());
		vs.put("printTransfertDescription", ss.getPrintTransfertDescription());
		vs.put("printSignature", ss.getPrintSignature());
		vs.put("roundingHour", ss.getRoundingHour());
		vs.put("tracking", ss.getTracking());
		vs.put("mailTracking", ss.getMailTracking());
		vs.put("cloudTracking", ss.getCloudTracking());
		vs.put("defaultApplySignature", ss.getDefaultApplySignature());
		vs.put("defaultChargeTo", ss.getDefaultChargeTo());
		vs.put("defaultFreeSupport", ss.getDefaultFreeSupport());
		vs.put("defaultStatus", ss.getDefaultDocStatusId());
		
		try {
			vs.put("opportunityRequiredFields", getOpportunityRequiredFields());
		} catch (WTException ex) {
			java.util.logging.Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return vs;
	}

	private HashMap<String,ArrayList<JsOpportunityField>> getOpportunityRequiredFields() throws WTException {
		try{
			HashMap<String,ArrayList<JsOpportunityField>> opportunityRequiredFields = new HashMap<>();
			ArrayList<JsOpportunityField> mainFields = new ArrayList<>();
			ArrayList<JsOpportunityField> visitReportFields = new ArrayList<>();
			ArrayList<JsOpportunityField> notesSignatureFields = new ArrayList<>();

			for(OOpportunityField OOf : manager.getOpportunityFieldsByDomainIdTabId(getEnv().getProfileId().getDomainId(), EnumUtils.toSerializedName(OpportunityField.Tab.MAIN))){
				mainFields.add(new JsOpportunityField(OOf));
			}
			for(OOpportunityField OOf : manager.getOpportunityFieldsByDomainIdTabId(getEnv().getProfileId().getDomainId(), EnumUtils.toSerializedName(OpportunityField.Tab.VISIT_REPORT))){
				visitReportFields.add(new JsOpportunityField(OOf));
			}
			for(OOpportunityField OOf : manager.getOpportunityFieldsByDomainIdTabId(getEnv().getProfileId().getDomainId(), EnumUtils.toSerializedName(OpportunityField.Tab.NOTES_SIGNATURE))){
				notesSignatureFields.add(new JsOpportunityField(OOf));
			}

			opportunityRequiredFields.put("mainFields", mainFields);
			opportunityRequiredFields.put("visitReportFields", visitReportFields);
			opportunityRequiredFields.put("notesSignatureFields", notesSignatureFields);

			return opportunityRequiredFields;
			
		} catch (Exception ex) {
			throw new WTException("Error in getOpportunityConfigurationFields", ex);
		}
	}

	public void processLookupUsers(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			List<JsSimple> jsUser = new ArrayList();
			ResultMeta meta = null;

			for (OUser usr : WT.getCoreManager().listUsers(true)) {
				jsUser.add(new JsSimple(usr.getUserId(), usr.getDisplayName()));
				if(getEnv().getProfileId().getUserId().equals(usr.getUserId())) meta = new LookupMeta().setSelected(getEnv().getProfileId().getUserId());
			}
			
			new JsonResult(jsUser, meta, jsUser.size()).printTo(out);
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action LookupUsers", ex);
		}
	}
	
	public void processLookupOperators(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			List<JsSimple> jsUser = new ArrayList();
			
			for (String usr : manager.listOperators()) {
				jsUser.add(new JsSimple(usr, WT.getUserData(new UserProfileId(getEnv().getProfileId().getDomain(), usr)).getDisplayName()));
			}
			
			ResultMeta meta = new LookupMeta().setSelected(jsUser.get(0).id);
			
			new JsonResult(jsUser, meta, jsUser.size()).printTo(out);
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action LookupOperators", ex);
		}
	}

	public void processLookupCompanies(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			String operator = ServletUtils.getStringParameter(request, "operator", null);
			List<JsSimple> companies = new ArrayList();
			
			if(operator != null){
				DrmManager manager = (DrmManager)WT.getServiceManager(SERVICE_ID, new UserProfileId(getEnv().getProfileId().getDomain(), operator));

				for (OCompany com : manager.listCompaniesByDomainUser()) {
					companies.add(new JsSimple(com.getCompanyId(), com.getName()));
				}
			}
			Integer selected = companies.isEmpty() ? null : (Integer) companies.get(0).id;
			ResultMeta meta = new LookupMeta().setSelected(selected);
				
			new JsonResult(companies, meta, companies.size()).printTo(out);
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action LookupCompanies", ex);
		}
	}
	
	public void processLookupHourProfiles(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			List<JsSimple> hourProfiles = new ArrayList();
			
			DrmManager manager = (DrmManager)WT.getServiceManager(SERVICE_ID);

			for (OHourProfile hp : manager.listHourProfiles()) {
				hourProfiles.add(new JsSimple(hp.getId(), hp.getDescription()));
			}
			
			new JsonResult(hourProfiles, hourProfiles.size()).printTo(out);
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action LookupHourProfiles", ex);
		}
	}
	
	public void processLookupAllCompanies(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			List<JsSimple> companies = new ArrayList();
			
			for (OCompany com : manager.listCompaniesByDomain()) {
				companies.add(new JsSimple(com.getCompanyId(), com.getName()));
			}
			
			Integer selected = companies.isEmpty() ? null : (Integer) companies.get(0).id;
			ResultMeta meta = new LookupMeta().setSelected(selected);
				
			new JsonResult(companies, meta, companies.size()).printTo(out);
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action LookupAllCompanies", ex);
		}
	}
	
	public void processLookupRealCustomers(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			String operator = ServletUtils.getStringParameter(request, "operator", null);
			List<String> idCustomers = new ArrayList();
			List<MasterData> items = new ArrayList<>();
			List<JsSimple> customers = new ArrayList();
			
			if(operator != null){
				DrmManager manager = (DrmManager)WT.getServiceManager(SERVICE_ID, new UserProfileId(getEnv().getProfileId().getDomain(), operator));

				idCustomers = manager.listCustomersByProfileUser();
				
				if(idCustomers.size() > 0)
					items = WT.getCoreManager().listMasterDataIn(idCustomers);
				else
					items = WT.getCoreManager().listMasterData(Arrays.asList(EnumUtils.toSerializedName(MasterData.Type.CUSTOMER)));
				
				for(MasterData customer : items) {
					customers.add(new JsSimple(customer.getMasterDataId(), customer.getDescription()));
				}
			}
				
			new JsonResult(customers, customers.size()).printTo(out);
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action LookupRealCustomers", ex);
		}
	}
	
	public void processLookupStatisticCustomers(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			String realCustomerId = ServletUtils.getStringParameter(request, "realCustomer", null);
			String operator = ServletUtils.getStringParameter(request, "operator", null);
			boolean chek = false;
			List<MasterData> items = new ArrayList<>();
			List<JsSimple> customers = new ArrayList();
			ResultMeta meta = null;
			
			if(realCustomerId != null && operator != null){
				DrmManager manager = (DrmManager)WT.getServiceManager(SERVICE_ID, new UserProfileId(getEnv().getProfileId().getDomain(), operator));

				chek = manager.checkCustomersByProfileUser(realCustomerId);
				items = WT.getCoreManager().listChildrenMasterData(realCustomerId, Arrays.asList(EnumUtils.toSerializedName(MasterData.Type.CUSTOMER)));
				
				for(MasterData customer : items) {
					customers.add(new JsSimple(customer.getMasterDataId(), customer.getDescription()));
				}
				
				Object selected = customers.isEmpty() ? null : customers.get(0).id;
				meta = new LookupMeta().setSelected(selected);
			}
				
			if(meta == null)
				new JsonResult(customers, customers.size()).printTo(out); 
			else 
				new JsonResult(customers, meta, customers.size()).printTo(out);
			
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action LookupStatisticCustomers", ex);
		}
	}
	
	public void processLookupAllStatisticCustomers(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			DrmManager manager = (DrmManager)WT.getServiceManager(SERVICE_ID, new UserProfileId(getEnv().getProfileId().getDomain(), getEnv().getProfileId().getUserId()));
			
			List<MasterData> items = new ArrayList<>();
			List<JsSimple> customers = new ArrayList();
			List<String> idCustomers = new ArrayList();
			
			idCustomers = manager.listCustomersByProfileUser();
				
			if(idCustomers.size() > 0)
				items = WT.getCoreManager().listMasterDataIn(idCustomers);
			else
				items = WT.getCoreManager().listMasterData(Arrays.asList(EnumUtils.toSerializedName(MasterData.Type.CUSTOMER)));
			
			items.addAll(WT.getCoreManager().listChildrenMasterData(Arrays.asList(EnumUtils.toSerializedName(MasterData.Type.CUSTOMER))));

			for(MasterData customer : items) {
				customers.add(new JsSimple(customer.getMasterDataId(), customer.getDescription()));
			}
				
			new JsonResult(customers, customers.size()).printTo(out);
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action LookupAllStatisticCustomers", ex);
		}
	}

	public void processLookupDocStatuses(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {

			List<JsSimple> docStatuses = new ArrayList();

			for (ODocStatus doc : manager.listDocStatuses()) {
				docStatuses.add(new JsSimple(doc.getDocStatusId(), doc.getName()));
			}
			
			Integer selected = docStatuses.isEmpty() ? null : (Integer) docStatuses.get(0).id;
			ResultMeta meta = new LookupMeta().setSelected(selected);

			new JsonResult(docStatuses, meta, docStatuses.size()).printTo(out);
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action LookupDocStatuses", ex);
		}
	}
	
	public void processLookupManagers(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			String operator = ServletUtils.getStringParameter(request, "operator", null);
			List<JsSimple> managers = new ArrayList();
			
			if(operator != null){
				DrmManager manager = (DrmManager)WT.getServiceManager(SERVICE_ID, new UserProfileId(getEnv().getProfileId().getDomain(), operator));

				for (String u : manager.listManagersByDomainUser(operator)) {
					managers.add(new JsSimple(WT.getCoreManager().getUser(new UserProfileId(getEnv().getProfileId().getDomain(), u)).getUserId(), WT.getCoreManager().getUser(new UserProfileId(getEnv().getProfileId().getDomain(), u)).getDisplayName()));
				}
			}
			String selected = managers.isEmpty() ? null : (String) managers.get(0).id;
			ResultMeta meta = new LookupMeta().setSelected(selected);
				
			new JsonResult(managers, meta, managers.size()).printTo(out);
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action LookupManagers", ex);
		}
	}
	
	private JsSimple createLeaveRequestJsSimple(OLeaveRequestType rs){
		String id = EnumUtils.toSerializedName(rs);
		
		return new JsSimple(id, lookupResource("leaveRequestType." + id));
	}
	
	public void processLookupLeaveRequestType(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			TimetableSetting ts = manager.getTimetableSetting();
			
			List<JsSimple> types = new ArrayList();
			
			types.add(createLeaveRequestJsSimple(OLeaveRequestType.HOLIDAY));
			types.add(createLeaveRequestJsSimple(OLeaveRequestType.PAID_LEAVE));
			types.add(createLeaveRequestJsSimple(OLeaveRequestType.OVERTIME));
			
			if(ts != null){
				if(ts.getRequestsPermitsNotRemunered())types.add(createLeaveRequestJsSimple(OLeaveRequestType.UNPAID_LEAVE));
				if(ts.getRequestsPermitsMedicalVisits())types.add(createLeaveRequestJsSimple(OLeaveRequestType.MEDICAL_VISIT));
				if(ts.getRequestsPermitsContractuals())types.add(createLeaveRequestJsSimple(OLeaveRequestType.CONTRACTUAL));
			}
			
			String selected = types.isEmpty() ? null : (String) types.get(0).id;
			ResultMeta meta = new LookupMeta().setSelected(selected);

			new JsonResult(types, meta, types.size()).printTo(out);
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action LookupLeaveRequestType", ex);
		}
	}

	public void processLookupWorkType(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {

			List<JsSimple> workTypes = new ArrayList();

			for (OWorkType wt : manager.listWorkType()) {
				workTypes.add(new JsSimple(wt.getWorkTypeId(), wt.getDescription()));
			}
			
			Integer selected = workTypes.isEmpty() ? null : (Integer) workTypes.get(0).id;
			ResultMeta meta = new LookupMeta().setSelected(selected);

			new JsonResult(workTypes, meta, workTypes.size()).printTo(out);
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action LookupWorkType", ex);
		}
	}

	public void processLookupBusinessTrip(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {

			List<JsSimple> trips = new ArrayList();

			for (OBusinessTrip type : manager.listBusinessTrip()) {
				trips.add(new JsSimple(type.getBusinessTripId(), type.getDescription()));
			}

			new JsonResult(trips).printTo(out);
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action LookupBusinessTrip", ex);
		}
	}
	
	public void processLookupContacts(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			List<JsSimpleSource> contacts = new ArrayList();
			List<Integer> categoryIds = new ArrayList();
					
			IContactsManager contactManager = (IContactsManager) WT.getServiceManager("com.sonicle.webtop.contacts", getEnv().getProfileId());
			categoryIds = contactManager.listCategoryIds();
			for(FolderContacts fc : contactManager.listFolderContacts(categoryIds, "", null)){
				String owner = WT.getUserData(fc.folder.getProfileId()).getDisplayName();
				for(ContactItemEx c : fc.contacts){
					contacts.add(new JsSimpleSource(c.getContactId(), c.getFirstName() + " " + c.getLastName(), "[" + owner + " / " + fc.folder.getName() + "]"));
				}
			}

			new JsonResult(contacts).printTo(out);
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action LookupContacts", ex);
		}
	}
	
	public void processLookupCausals(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		List<JsSimple> items = new ArrayList<>();
		
		try {
			List<CausalExt> caus = null;
			caus = WT.getCoreManager().listAllLiveCausals();
			
			for(Causal cau : caus) {
				items.add(new JsSimple(cau.getCausalId(), cau.getDescription()));
			}
			new JsonResult(items, items.size()).printTo(out);
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in LookupCausals", ex);
		}
	}

	public void processLoadEnabledProgramTree(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {

		String node = "";
		List<ExtTreeNode> treeNode = new ArrayList<>();

		try {
			node = ServletUtils.getStringParameter(request, "node", true);
			if (StringUtils.equals(node, "root")) {
				for (RootProgramNode program : programs.values()) {
					treeNode.add(new ExtTreeNode(program.getId(), program.getId(), program.getSubPrograms().isEmpty(), program.getIconClass()));
				}
			} else {
				for (ProgramNode subProg : programs.get(node).getSubPrograms()) {
					treeNode.add(new ExtTreeNode(subProg.getId(), subProg.getId(), true, subProg.getIconClass()));
				}
			}
			new JsonResult(treeNode).printTo(out);
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in LoadEnabledProgramTree", ex);
		}

	}

	public void processManageCompanies(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			String crud = ServletUtils.getStringParameter(request, "crud", true);
			if (crud.equals(Crud.READ)) {

				List<JsGridCompanies> jsCompanies = new ArrayList();

				for (OCompany com : manager.listCompaniesByDomain()) {

					jsCompanies.add(new JsGridCompanies(com));
				}

				new JsonResult(jsCompanies).printTo(out);

			}
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ManageCompanies", ex);
		}
	}

	public void processManageCompany(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			String crud = ServletUtils.getStringParameter(request, "crud", true);
			if (crud.equals(Crud.READ)) {
				int id = ServletUtils.getIntParameter(request, "id", true);

				Company company = manager.getCompany(id);

				new JsonResult(new JsCompany(company)).printTo(out);
			} else if (crud.equals(Crud.CREATE)) {
				Payload<MapItem, JsCompany> pl = ServletUtils.getPayload(request, JsCompany.class);
				
				Company company = JsCompany.createCompany(pl.data);
				CompanyPicture picture = null;
				if(company.getHasPicture() && hasUploadedFile(pl.data.picture)) {
					picture = getUploadedCompanyPicture(pl.data.picture);
				}
				
				if(picture != null) {
					manager.addCompany(company, picture);
				} else {
					manager.addCompany(company);
				}

				new JsonResult().printTo(out);
			} else if (crud.equals(Crud.UPDATE)) {

				Payload<MapItem, JsCompany> pl = ServletUtils.getPayload(request, JsCompany.class);
				Company company = JsCompany.createCompany(pl.data);
				CompanyPicture picture = null;
				if(company.getHasPicture() && hasUploadedFile(pl.data.picture)) {
					picture = getUploadedCompanyPicture(pl.data.picture);
				}
				
				if(picture != null) {
					manager.updateCompany(company, picture);
				} else {
					manager.updateCompany(company);
				}

				new JsonResult().printTo(out);
			} else if (crud.equals(Crud.DELETE)) {

				IntegerArray ids = ServletUtils.getObjectParameter(request, "companyIds", IntegerArray.class, true);

				manager.deleteCompany(ids.get(0));

				new JsonResult().printTo(out);
			}
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ManageCompany", ex);
		}
	}
	
	public void processGetCompanyPicture(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			String id = ServletUtils.getStringParameter(request, "id", true);
			
			CompanyPicture picture = null;
			if(hasUploadedFile(id)) {
				picture = getUploadedCompanyPicture(id);
			} else {
				int contactId = Integer.parseInt(id);
				picture = manager.getCompanyPicture(contactId);
			}
			
			if(picture != null) {
				try(ByteArrayInputStream bais = new ByteArrayInputStream(picture.getBytes())) {
					ServletUtils.writeContent(response, bais, picture.getMediaType());
				}
			}			
		} catch(Exception ex) {
			logger.error("Error in action GetCompanyPicture", ex);
		}
	}
	
	private CompanyPicture getUploadedCompanyPicture(String id) throws WTException {
		UploadedFile upl = getUploadedFile(id);
		if(upl == null) throw new WTException("Uploaded file not found [{0}]", id);
		
		CompanyPicture pic = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(upl.getFile());
			pic = new CompanyPicture("image/png", IOUtils.toByteArray(fis));
		} catch (FileNotFoundException ex) {
			throw new WTException(ex, "File not found {0}");
		} catch (IOException ex) {
			throw new WTException(ex, "Unable to read file {0}");
		} finally {
			IOUtils.closeQuietly(fis);
		}
		return pic;
	}

	public void processUpdateConfiguration(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		Connection con = null;
		try {
			Boolean useStatisticCustomer = ServletUtils.getBooleanParameter(request, "useStatisticCustomer", null);

			if (useStatisticCustomer != null) {
				ss.setUseStatisticCustomer(useStatisticCustomer);
			}
			new JsonResult().printTo(out);
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action UpdateConfiguration", ex);
		} finally {
		}
	}

	public void processLoadGroupsTree(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {

		String node = "";
		List<ExtTreeNode> treeNode = new ArrayList<>();

		try {
			node = ServletUtils.getStringParameter(request, "node", true);
			if (StringUtils.equals(node, "root")) {
				for (Map.Entry<String, String> entry : groupCategories.entrySet()) {
					ExtTreeNode extNode = new ExtTreeNode(entry.getKey(), entry.getValue(), false, "");
					extNode.put("groupCategory", entry.getKey());
					extNode.put("depth", 0);
					treeNode.add(extNode);
				}
			} else {
				for (ODrmGroup subGroup : manager.listGroups(node)) {
					ExtTreeNode extNode = new ExtTreeNode(subGroup.getGroupId(), subGroup.getName(), true, "");
					extNode.put("groupCategory", subGroup.getGroupCategory());
					extNode.put("depth", 1);
					treeNode.add(extNode);
				}
			}
			new JsonResult(treeNode).printTo(out);
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action LoadGroupsTree", ex);
		}

	}

	public void processManageGroup(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			String crud = ServletUtils.getStringParameter(request, "crud", true);
			if (crud.equals(Crud.READ)) {
				String id = ServletUtils.getStringParameter(request, "id", true);

				DrmGroup group = manager.getDrmGroup(id);

				new JsonResult(new JsDrmGroup(group)).printTo(out);
			} else if (crud.equals(Crud.CREATE)) {

				Payload<MapItem, JsDrmGroup> pl = ServletUtils.getPayload(request, JsDrmGroup.class);
				manager.addDrmGroup(JsDrmGroup.createDrmGroup(pl.data));

				new JsonResult().printTo(out);
			} else if (crud.equals(Crud.UPDATE)) {
				Payload<MapItem, JsDrmGroup> pl = ServletUtils.getPayload(request, JsDrmGroup.class);

				manager.updateDrmGroup(JsDrmGroup.createDrmGroup(pl.data));

				new JsonResult().printTo(out);
			} else if (crud.equals(Crud.DELETE)) {

				StringArray ids = ServletUtils.getObjectParameter(request, "groupIds", StringArray.class, true);

				manager.deleteDrmGroup(ids.get(0));

				new JsonResult().printTo(out);
			}
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ManageGroup", ex);
		}
	}

	public void processLookupGroups(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {

			List<JsSimple> groups = new ArrayList();

			/*for (ODrmGroup grp : manager.listGroups(null)) {
				groups.add(new JsSimpleGrouped(grp.getGroupId(), grp.getName(), grp.getGroupCategory(), groupCategories.get(grp.getGroupCategory())));
			}*/
			for (OGroup grp : WT.getCoreManager(getEnv().getProfileId()).listGroups()) {
				groups.add(new JsSimple(grp.getGroupId(), grp.getDisplayName()));
			}

			new JsonResult(groups).printTo(out);
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action LookupGroups", ex);
		}
	}

	public void processManageGridProfiles(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			String crud = ServletUtils.getStringParameter(request, "crud", true);
			if (crud.equals(Crud.READ)) {

				List<JsGridProfiles> jsGridProfiles = new ArrayList();

				for (ODrmProfile pf : manager.listProfiles()) {

					jsGridProfiles.add(new JsGridProfiles(pf));
				}

				new JsonResult(jsGridProfiles).printTo(out);

			} else if (crud.equals(Crud.DELETE)) {
				PayloadAsList <JsGridProfiles.List> pl = ServletUtils.getPayloadAsList(request, JsGridProfiles.List.class);
				
				manager.deleteDrmProfile(pl.data.get(0).profileId);

				new JsonResult().printTo(out);
			}
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ManageGridProfiles", ex);
		}
	}
	
	public void processManageGridLineManager(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			String crud = ServletUtils.getStringParameter(request, "crud", true);
			if (crud.equals(Crud.READ)) {

				List<JsGridLineManager> jsGridLineManager = new ArrayList();
				JsGridLineManager record = null;
						
				for (ODrmLineManager m : manager.listLineManagers()) {
					record = new JsGridLineManager(m);
					record.description = WT.getCoreManager().getUser(new UserProfileId(record.domainId, record.userId)).getDisplayName();
					jsGridLineManager.add(record);
				}

				new JsonResult(jsGridLineManager).printTo(out);

			} else if (crud.equals(Crud.DELETE)) {
				PayloadAsList <JsGridLineManager.List> pl = ServletUtils.getPayloadAsList(request, JsGridLineManager.List.class);
				
				manager.deleteDrmLineManager(pl.data.get(0).domainId, pl.data.get(0).userId);

				new JsonResult().printTo(out);
			}
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ManageGridLineManager", ex);
		}
	}
	
	public void processManageEmployeeProfile(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			String crud = ServletUtils.getStringParameter(request, "crud", true);
			if (crud.equals(Crud.READ)) {
				String id = ServletUtils.getStringParameter(request, "id", false);
				
				if(id != null){
					EmployeeProfile ep = manager.getEmployeeProfile(Integer.parseInt(id));

					new JsonResult(new JsEmployeeProfile(ep)).printTo(out);
				}
			} else if (crud.equals(Crud.CREATE)) {

				Payload<MapItem, JsEmployeeProfile> pl = ServletUtils.getPayload(request, JsEmployeeProfile.class);
				manager.addEmployeeProfile(JsEmployeeProfile.createEmployeeProfile(pl.data));

				new JsonResult().printTo(out);
			} else if (crud.equals(Crud.UPDATE)) {
				Payload<MapItem, JsEmployeeProfile> pl = ServletUtils.getPayload(request, JsEmployeeProfile.class);

				manager.updateEmployeeProfile(JsEmployeeProfile.createEmployeeProfile(pl.data));

				new JsonResult().printTo(out);
			} else if (crud.equals(Crud.DELETE)) {

				StringArray id = ServletUtils.getObjectParameter(request, "id", StringArray.class, true);
				manager.deleteEmployeeProfile(Integer.parseInt(id.get(0)));

				new JsonResult().printTo(out);
			}
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ManageEmployeeProfile", ex);
		}
	}
	
	public void processManageHourProfile(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			String crud = ServletUtils.getStringParameter(request, "crud", true);
			if (crud.equals(Crud.READ)) {
				String id = ServletUtils.getStringParameter(request, "id", false);
				
				if(id != null){
					HourProfile hp = manager.getHourProfile(Integer.parseInt(id));

					new JsonResult(new JsHourProfile(hp)).printTo(out);
				}
			} else if (crud.equals(Crud.CREATE)) {

				Payload<MapItem, JsHourProfile> pl = ServletUtils.getPayload(request, JsHourProfile.class);
				manager.addHourProfile(JsHourProfile.createHourProfile(pl.data));

				new JsonResult().printTo(out);
			} else if (crud.equals(Crud.UPDATE)) {
				Payload<MapItem, JsHourProfile> pl = ServletUtils.getPayload(request, JsHourProfile.class);

				manager.updateHourProfile(JsHourProfile.createHourProfile(pl.data));

				new JsonResult().printTo(out);
			} else if (crud.equals(Crud.DELETE)) {

				StringArray id = ServletUtils.getObjectParameter(request, "id", StringArray.class, true);
				manager.deleteHourProfile(Integer.parseInt(id.get(0)));

				new JsonResult().printTo(out);
			}
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ManageHourProfile", ex);
		}
	}
	
	public void processManageGridEmployeeProfile(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			String crud = ServletUtils.getStringParameter(request, "crud", true);
			JsGridEmployeeProfile jsGEP;
			HourProfile hp;
			
			if (crud.equals(Crud.READ)) {

				List<JsGridEmployeeProfile> jsGridEP = new ArrayList();

				for (OEmployeeProfile oEP : manager.listEmployeeProfiles()) {
					jsGEP = new JsGridEmployeeProfile(oEP);
					hp = manager.getHourProfile(oEP.getHourProfileId());
					jsGEP.hourProfile = (hp != null) ? hp.getDescription() : "";
					jsGridEP.add(jsGEP);
				}

				new JsonResult(jsGridEP).printTo(out);

			}
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ManageGridEmployeeProfile", ex);
		}
	}
	
	public void processManageGridHourProfile(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			String crud = ServletUtils.getStringParameter(request, "crud", true);
			if (crud.equals(Crud.READ)) {

				List<JsGridHourProfile> jsGridHP = new ArrayList();

				for (OHourProfile oHP : manager.listHourProfiles()) {

					jsGridHP.add(new JsGridHourProfile(oHP));
				}

				new JsonResult(jsGridHP).printTo(out);

			}
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ManageGridEmployeeProfile", ex);
		}
	}

	public void processManageProfile(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			String crud = ServletUtils.getStringParameter(request, "crud", true);
			if (crud.equals(Crud.READ)) {
				String id = ServletUtils.getStringParameter(request, "id", true);

				DrmProfile pfl = manager.getDrmProfile(id);

				new JsonResult(new JsDrmProfile(pfl)).printTo(out);
			} else if (crud.equals(Crud.CREATE)) {

				Payload<MapItem, JsDrmProfile> pl = ServletUtils.getPayload(request, JsDrmProfile.class);
				manager.addDrmProfile(JsDrmProfile.createDrmProfile(pl.data));

				new JsonResult().printTo(out);
			} else if (crud.equals(Crud.UPDATE)) {
				Payload<MapItem, JsDrmProfile> pl = ServletUtils.getPayload(request, JsDrmProfile.class);

				manager.updateDrmProfile(JsDrmProfile.createDrmProfile(pl.data));

				new JsonResult().printTo(out);
			} else if (crud.equals(Crud.DELETE)) {

				StringArray ids = ServletUtils.getObjectParameter(request, "profileIds", StringArray.class, true);
				manager.deleteDrmProfile(ids.get(0));

				new JsonResult().printTo(out);
			}
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ManageProfile", ex);
		}
	}

	public void processManageLineManager(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			String crud = ServletUtils.getStringParameter(request, "crud", true);
			if (crud.equals(Crud.READ)) {
				String userId = ServletUtils.getStringParameter(request, "id", true);

				DrmLineManager mng = manager.getDrmLineManager(userId);

				new JsonResult(new JsDrmLineManager(mng)).printTo(out);
			} else if (crud.equals(Crud.CREATE)) {

				Payload<MapItem, JsDrmLineManager> pl = ServletUtils.getPayload(request, JsDrmLineManager.class);
				
				manager.addDrmLineManager(JsDrmLineManager.createDrmLineManager(pl.data));

				new JsonResult().printTo(out);
			} else if (crud.equals(Crud.UPDATE)) {
				Payload<MapItem, JsDrmLineManager> pl = ServletUtils.getPayload(request, JsDrmLineManager.class);

				manager.updateDrmLineManager(JsDrmLineManager.createDrmLineManager(pl.data));

				new JsonResult().printTo(out);
			} else if (crud.equals(Crud.DELETE)) {

				StringArray ids = ServletUtils.getObjectParameter(request, "userIds", StringArray.class, true);
				manager.deleteDrmLineManager(ids.get(0));

				new JsonResult().printTo(out);
			}
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ManageLineManager", ex);
		}
	}
	
	public void processManageGridDocStatuses(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			String crud = ServletUtils.getStringParameter(request, "crud", true);
			if (crud.equals(Crud.READ)) {

				List<JsGridDocStatuses> jsGridDocStatuses = new ArrayList();

				for (ODocStatus st : manager.listDocStatuses()) {

					jsGridDocStatuses.add(new JsGridDocStatuses(st));
				}

				new JsonResult(jsGridDocStatuses).printTo(out);

			}
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ManageStatuses", ex);
		}
	}

	public void processManageDocStatus(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			String crud = ServletUtils.getStringParameter(request, "crud", true);
			if (crud.equals(Crud.READ)) {
				int id = ServletUtils.getIntParameter(request, "id", true);

				DocStatus docStat = manager.getDocStatus(id);

				new JsonResult(new JsDocStatus(docStat)).printTo(out);
			} else if (crud.equals(Crud.CREATE)) {

				Payload<MapItem, JsDocStatus> pl = ServletUtils.getPayload(request, JsDocStatus.class);
				manager.addDocStatus(JsDocStatus.createDocStatus(pl.data));

				new JsonResult().printTo(out);
			} else if (crud.equals(Crud.UPDATE)) {
				Payload<MapItem, JsDocStatus> pl = ServletUtils.getPayload(request, JsDocStatus.class);

				manager.updateDocStatus(JsDocStatus.createDocStatus(pl.data));

				new JsonResult().printTo(out);
			} else if (crud.equals(Crud.DELETE)) {

				IntegerArray ids = ServletUtils.getObjectParameter(request, "docStatusIds", IntegerArray.class, true);

				manager.deleteDocStatus(ids.get(0));

				new JsonResult().printTo(out);
			}
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ManageCompany", ex);
		}
	}

	public void processManageGridFolders(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			String crud = ServletUtils.getStringParameter(request, "crud", true);
			if (crud.equals(Crud.READ)) {

				List<JsGridFolders> jsGridFolders = new ArrayList();

				for (ODrmFolder fd : manager.listFolders()) {

					jsGridFolders.add(new JsGridFolders(fd));
				}

				new JsonResult(jsGridFolders).printTo(out);

			}
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ManageCompany", ex);
		}
	}

	public void processManageFolder(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			String crud = ServletUtils.getStringParameter(request, "crud", true);
			if (crud.equals(Crud.READ)) {
				String id = ServletUtils.getStringParameter(request, "id", true);

				DrmFolder fld = manager.getDrmFolder(id);

				new JsonResult(new JsDrmFolder(fld)).printTo(out);
			} else if (crud.equals(Crud.CREATE)) {

				Payload<MapItem, JsDrmFolder> pl = ServletUtils.getPayload(request, JsDrmFolder.class);
				manager.addDrmFolder(JsDrmFolder.createDrmFolder(pl.data));

				new JsonResult().printTo(out);
			} else if (crud.equals(Crud.UPDATE)) {
				Payload<MapItem, JsDrmFolder> pl = ServletUtils.getPayload(request, JsDrmFolder.class);

				manager.updateFolder(JsDrmFolder.createDrmFolder(pl.data));

				new JsonResult().printTo(out);
			} else if (crud.equals(Crud.DELETE)) {

				StringArray ids = ServletUtils.getObjectParameter(request, "folderIds", StringArray.class, true);
				manager.deleteFolder(ids.get(0));

				new JsonResult().printTo(out);
			}
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ManageCompany", ex);
		}
	}
	
	public void processManageGridOpportunity(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			String crud = ServletUtils.getStringParameter(request, "crud", true);
			
			if (crud.equals(Crud.READ)) {
				
				String query = ServletUtils.getStringParameter(request, "query", null);
				OpportunityQuery oQuery = OpportunityQuery.fromJson(query);
				JsGridOpportunity item;
				List<JsGridOpportunity> jsGridOpportunity = new ArrayList();
				
				List<OOpportunityField> fields = manager.getOpportunitySettingFieldsInGrid();
				
				for (VOpportunityEntry o : manager.listOpportunitiesAndActions(oQuery)) {
					item = new JsGridOpportunity(o);
					item.additionalInfo = (o.getActionId() == 0) ? getGridOpportunityAdditionalInfo(fields, o) : o.getDescription();					
					
					jsGridOpportunity.add(item);
				}

				new JsonResult(jsGridOpportunity).printTo(out);
			}
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ManageGridOpportunity", ex);
		}
	}
	
	public void processManageOpportunity(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		JsOpportunity item = null;
		try {
			String crud = ServletUtils.getStringParameter(request, "crud", true);
			HashMap<String, File> files = new HashMap<>();
			if (crud.equals(Crud.READ)) {

				Integer id = ServletUtils.getIntParameter(request, "id", true);

				Opportunity o = manager.getOpportunity(id);

				item = new JsOpportunity(o);

				new JsonResult(item).printTo(out);

			} else if (crud.equals(Crud.CREATE)) {

				Payload<MapItem, JsOpportunity> pl = ServletUtils.getPayload(request, JsOpportunity.class);

				Opportunity o = JsOpportunity.createOpportunity(pl.data);
				
				for (OpportunityDocument doc : o.getDocuments()) {

					WebTopSession.UploadedFile uf = getUploadedFile(doc.getId().toString());

					if (uf != null) {
						files.put(uf.getUploadId(), uf.getFile());
					}
				}

				manager.addOpportunity(o, files);

				new JsonResult().printTo(out);

			} else if (crud.equals(Crud.UPDATE)) {

				Payload<MapItem, JsOpportunity> pl = ServletUtils.getPayload(request, JsOpportunity.class);

				Opportunity o = JsOpportunity.createOpportunity(pl.data);
				
				for (OpportunityDocument doc : o.getDocuments()) {

					WebTopSession.UploadedFile uf = getUploadedFile(doc.getId().toString());

					if (uf != null) {
						files.put(uf.getUploadId(), uf.getFile());
					}
				}

				manager.updateOpportunity(o, files);

				new JsonResult().printTo(out);

			} else if (crud.equals(Crud.DELETE)) {

				StringArray ids = ServletUtils.getObjectParameter(request, "ids", StringArray.class, true);

				manager.deleteOpportunity(Integer.parseInt(ids.get(0)));

				new JsonResult().printTo(out);
			}
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ManageOpportunity", ex);
		}
	}
	
	public void processManageOpportunityAction(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		JsOpportunityAction item = null;
		try {
			String crud = ServletUtils.getStringParameter(request, "crud", true);
			HashMap<String, File> files = new HashMap<>();
			if (crud.equals(Crud.READ)) {

				Integer id = ServletUtils.getIntParameter(request, "id", true);

				OpportunityAction oAct = manager.getOpportunityAction(id);

				item = new JsOpportunityAction(oAct);

				new JsonResult(item).printTo(out);

			} else if (crud.equals(Crud.CREATE)) {

				Payload<MapItem, JsOpportunityAction> pl = ServletUtils.getPayload(request, JsOpportunityAction.class);

				OpportunityAction oAct = JsOpportunityAction.createOpportunityAction(pl.data);
				
				for (OpportunityActionDocument doc : oAct.getActionDocuments()) {

					WebTopSession.UploadedFile uf = getUploadedFile(doc.getId().toString());

					if (uf != null) {
						files.put(uf.getUploadId(), uf.getFile());
					}
				}

				manager.addOpportunityAction(oAct, files);

				new JsonResult().printTo(out);

			} else if (crud.equals(Crud.UPDATE)) {

				Payload<MapItem, JsOpportunityAction> pl = ServletUtils.getPayload(request, JsOpportunityAction.class);

				OpportunityAction oAct = JsOpportunityAction.createOpportunityAction(pl.data);
				
				for (OpportunityActionDocument doc : oAct.getActionDocuments()) {

					WebTopSession.UploadedFile uf = getUploadedFile(doc.getId().toString());

					if (uf != null) {
						files.put(uf.getUploadId(), uf.getFile());
					}
				}

				manager.updateOpportunityAction(oAct, files);

				new JsonResult().printTo(out);

			} else if (crud.equals(Crud.DELETE)) {

				StringArray ids = ServletUtils.getObjectParameter(request, "ids", StringArray.class, true);

				manager.deleteOpportunityAction(Integer.parseInt(ids.get(0)));

				new JsonResult().printTo(out);
			}
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ManageOpportunityAction", ex);
		}
	}

	public void processManageGridWorkReport(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			String crud = ServletUtils.getStringParameter(request, "crud", true);
			DateTimeZone ptz = getEnv().getProfile().getTimeZone();
			if (crud.equals(Crud.READ)) {

				String query = ServletUtils.getStringParameter(request, "query", null);
				WorkReportQuery wrQuery = WorkReportQuery.fromJson(query);
				List<JsGridWorkReports> jsGridWorkReports = new ArrayList();

				for (OWorkReport wr : manager.listWorkReports(wrQuery)) {
					jsGridWorkReports.add(new JsGridWorkReports(wr, ptz, ""));
				}

				new JsonResult(jsGridWorkReports).printTo(out);
			}
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ManageGridWorkReport", ex);
		}
	}

	public void processManageWorkReport(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		JsWorkReport item = null;
		try {
			String crud = ServletUtils.getStringParameter(request, "crud", true);
			DateTimeZone ptz = getEnv().getProfile().getTimeZone();
			HashMap<String, File> files = new HashMap<>();
			if (crud.equals(Crud.READ)) {

				String id = ServletUtils.getStringParameter(request, "id", true);

				WorkReport wrkRpt = manager.getWorkReport(id);

				String _profileId = "";

				item = new JsWorkReport(wrkRpt, ptz, _profileId);

				new JsonResult(item).printTo(out);

			} else if (crud.equals(Crud.CREATE)) {

				Payload<MapItem, JsWorkReport> pl = ServletUtils.getPayload(request, JsWorkReport.class);

				WorkReport wrkRpt = JsWorkReport.createWorkReport(pl.data, ptz);

				for (WorkReportAttachment att : wrkRpt.getAttachments()) {

					WebTopSession.UploadedFile uf = getUploadedFile(att.getWorkReportAttachmentId());

					if (uf != null) {
						files.put(uf.getUploadId(), uf.getFile());
					}
				}

				manager.addWorkReport(wrkRpt, files);

				new JsonResult().printTo(out);

			} else if (crud.equals(Crud.UPDATE)) {

				Payload<MapItem, JsWorkReport> pl = ServletUtils.getPayload(request, JsWorkReport.class);

				WorkReport wrkRpt = JsWorkReport.createWorkReport(pl.data, ptz);

				for (WorkReportAttachment att : wrkRpt.getAttachments()) {

					WebTopSession.UploadedFile uf = getUploadedFile(att.getWorkReportAttachmentId());

					if (uf != null) {
						files.put(uf.getUploadId(), uf.getFile());
					}
				}

				manager.updateWorkReport(wrkRpt, files);

				new JsonResult().printTo(out);

			} else if (crud.equals(Crud.DELETE)) {

				StringArray ids = ServletUtils.getObjectParameter(request, "reportIds", StringArray.class, true);

				manager.deleteWorkReport(ids.get(0));

				new JsonResult().printTo(out);
			}
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ManageWorkReport", ex);
		}
	}
	
	public void processManageLeaveRequest(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		JsLeaveRequest item = null;
		try {
			String crud = ServletUtils.getStringParameter(request, "crud", true);
			HashMap<String, File> files = new HashMap<>();
			if (crud.equals(Crud.READ)) {

				Integer id = ServletUtils.getIntParameter(request, "id", true);

				LeaveRequest lr = manager.getLeaveRequest(id);

				item = new JsLeaveRequest(lr);

				new JsonResult(item).printTo(out);

			} else if (crud.equals(Crud.CREATE)) {

				Payload<MapItem, JsLeaveRequest> pl = ServletUtils.getPayload(request, JsLeaveRequest.class);

				LeaveRequest lr = JsLeaveRequest.createLeaveRequest(pl.data);

				for (LeaveRequestDocument doc : lr.getDocuments()) {

					WebTopSession.UploadedFile uf = getUploadedFile(doc.getLeaveRequestDocumentId().toString());

					if (uf != null) {
						files.put(uf.getUploadId(), uf.getFile());
					}
				}

				manager.addLeaveRequest(lr, files);

				new JsonResult().printTo(out);

			} else if (crud.equals(Crud.UPDATE)) {

				Payload<MapItem, JsLeaveRequest> pl = ServletUtils.getPayload(request, JsLeaveRequest.class);

				LeaveRequest lr = JsLeaveRequest.createLeaveRequest(pl.data);

				for (LeaveRequestDocument doc : lr.getDocuments()) {

					WebTopSession.UploadedFile uf = getUploadedFile(doc.getLeaveRequestDocumentId().toString());

					if (uf != null) {
						files.put(uf.getUploadId(), uf.getFile());
					}
				}

				manager.updateLeaveRequest(lr, files, false);

				new JsonResult().printTo(out);

			} else if (crud.equals(Crud.DELETE)) {

				IntegerArray ids = ServletUtils.getObjectParameter(request, "leaveRequestIds", IntegerArray.class, true);

				manager.deleteLeaveRequest(ids.get(0));

				new JsonResult().printTo(out);
			}
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ManageLeaveRequest", ex);
		}
	}
	
	public void processManageLeaveRequestSupervisorChoice(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			IntegerArray ids = ServletUtils.getObjectParameter(request, "leaveRequestIds", IntegerArray.class, true);
			Boolean choice = ServletUtils.getBooleanParameter(request, "choice", false);

			LeaveRequest lr = manager.getLeaveRequest(ids.get(0));
			lr.setResult(choice);
			
			manager.updateLeaveRequest(lr, null, true);

			new JsonResult().printTo(out);

		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ManageLeaveRequestSupervisorChoice", ex);
		}
	}
	
	public void processManageCancellationLeaveRequest(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			
			IntegerArray ids = ServletUtils.getObjectParameter(request, "leaveRequestIds", IntegerArray.class, true);
			Boolean choice = ServletUtils.getBooleanParameter(request, "choice", false);
			
			manager.updateCancellationLeaveRequest(ids.get(0), choice);

			new JsonResult().printTo(out);
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ManageCancellationLeaveRequest", ex);
		}
	}
	
	public void processDownloadOpportunityDocument(HttpServletRequest request, HttpServletResponse response) {

		try {
			StringArray documentIds = ServletUtils.getObjectParameter(request, "documentIds", StringArray.class, true);

			Integer raw = ServletUtils.getIntParameter(request, "raw", 0);

			String fileId = documentIds.get(0);

			InputStream is = null;
			FileContent fc = null;
			try {

				if (hasUploadedFile(fileId)) {
					fc = toFileContent(getUploadedFile(fileId));
				} else {
					fc = manager.getOpportunityDocumentContent(fileId);
				}

				is = fc.getStream();

				OutputStream os = response.getOutputStream();
				ServletUtils.setContentLengthHeader(response, fc.getSize());
				if (raw == 1) {
					ServletUtils.setFileStreamHeadersForceDownload(response, fc.getFilename());
				} else {
					ServletUtils.setFileStreamHeaders(response, fc.getMediaType(), fc.getFilename());
				}
				IOUtils.copy(is, os);

			} finally {
				IOUtils.closeQuietly(is);
			}

		} catch (Exception ex) {
			logger.error("Error in action DownloadFiles", ex);
			ServletUtils.writeErrorHandlingJs(response, ex.getMessage());
		}
	}
	
	public void processDownloadOpportunityActionDocument(HttpServletRequest request, HttpServletResponse response) {

		try {
			StringArray documentIds = ServletUtils.getObjectParameter(request, "documentIds", StringArray.class, true);

			Integer raw = ServletUtils.getIntParameter(request, "raw", 0);

			String fileId = documentIds.get(0);

			InputStream is = null;
			FileContent fc = null;
			try {

				if (hasUploadedFile(fileId)) {
					fc = toFileContent(getUploadedFile(fileId));
				} else {
					fc = manager.getOpportunityActionDocumentContent(fileId);
				}

				is = fc.getStream();

				OutputStream os = response.getOutputStream();
				ServletUtils.setContentLengthHeader(response, fc.getSize());
				if (raw == 1) {
					ServletUtils.setFileStreamHeadersForceDownload(response, fc.getFilename());
				} else {
					ServletUtils.setFileStreamHeaders(response, fc.getMediaType(), fc.getFilename());
				}
				IOUtils.copy(is, os);

			} finally {
				IOUtils.closeQuietly(is);
			}

		} catch (Exception ex) {
			logger.error("Error in action DownloadFiles", ex);
			ServletUtils.writeErrorHandlingJs(response, ex.getMessage());
		}
	}

	public void processDownloadWorkReportAttachment(HttpServletRequest request, HttpServletResponse response) {

		try {
			StringArray attachmentIds = ServletUtils.getObjectParameter(request, "attachmentIds", StringArray.class, true);

			Integer raw = ServletUtils.getIntParameter(request, "raw", 0);

			String fileId = attachmentIds.get(0);

			InputStream is = null;
			FileContent fc = null;
			try {

				if (hasUploadedFile(fileId)) {
					fc = toFileContent(getUploadedFile(fileId));
				} else {
					fc = manager.getWorkReportAttachmentContent(fileId);
				}

				is = fc.getStream();

				OutputStream os = response.getOutputStream();
				ServletUtils.setContentLengthHeader(response, fc.getSize());
				if (raw == 1) {
					ServletUtils.setFileStreamHeadersForceDownload(response, fc.getFilename());
				} else {
					ServletUtils.setFileStreamHeaders(response, fc.getMediaType(), fc.getFilename());
				}
				IOUtils.copy(is, os);

			} finally {
				IOUtils.closeQuietly(is);
			}

		} catch (Exception ex) {
			logger.error("Error in action DownloadFiles", ex);
			ServletUtils.writeErrorHandlingJs(response, ex.getMessage());
		}
	}
	
	public void processDownloadTimetableRequestDocument(HttpServletRequest request, HttpServletResponse response) {

		try {
			StringArray documentIds = ServletUtils.getObjectParameter(request, "documentIds", StringArray.class, true);

			Integer raw = ServletUtils.getIntParameter(request, "raw", 0);

			String fileId = documentIds.get(0);

			InputStream is = null;
			FileContent fc = null;
			try {

				if (hasUploadedFile(fileId)) {
					fc = toFileContent(getUploadedFile(fileId));
				} else {
					fc = manager.getLeaveRequestDocumentContent(fileId);
				}

				is = fc.getStream();

				OutputStream os = response.getOutputStream();
				ServletUtils.setContentLengthHeader(response, fc.getSize());
				if (raw == 1) {
					ServletUtils.setFileStreamHeadersForceDownload(response, fc.getFilename());
				} else {
					ServletUtils.setFileStreamHeaders(response, fc.getMediaType(), fc.getFilename());
				}
				IOUtils.copy(is, os);

			} finally {
				IOUtils.closeQuietly(is);
			}

		} catch (Exception ex) {
			logger.error("Error in action DownloadFiles", ex);
			ServletUtils.writeErrorHandlingJs(response, ex.getMessage());
		}
	}

	private FileContent toFileContent(UploadedFile uploaded) throws WTException {

		try {
			if (uploaded == null) {
				throw new WTException("Uploaded file is null");
			}

			return new FileContent(uploaded.getFilename(), uploaded.getSize(), uploaded.getMediaType(), new FileInputStream(uploaded.getFile()));
		} catch (FileNotFoundException ex) {
			throw new WTException("File non found", ex);
		}
	}
	
	public void processInitializeOpportunityFields(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try{
			manager.initializeOpportunityFields();

			new JsonResult(true).printTo(out);
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action InitializeOpportunityFields", ex);
		}
	}

	public void processManageOpportunitySetting(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		JsOpportunitySetting item = null;
		
		try {
			String crud = ServletUtils.getStringParameter(request, "crud", true);

			if (crud.equals(Crud.READ)) {

				OpportunitySetting setting = manager.getOpportunitySetting();

				if (setting != null) {
					item = new JsOpportunitySetting(setting);
				} else {
					item = new JsOpportunitySetting(new OpportunitySetting());
				}

				new JsonResult(item).printTo(out);

			} else if (crud.equals(Crud.UPDATE)) {

				Payload<MapItem, JsOpportunitySetting> pl = ServletUtils.getPayload(request, JsOpportunitySetting.class);

				OpportunitySetting setting = JsOpportunitySetting.createOpportunitySetting(pl.data);
				
				manager.updateOpportunitySetting(setting);

				new JsonResult().printTo(out);
			}
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ManageOpportunitySetting", ex);
		}
	}
	
	public void processManageWorkReportSetting(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		JsWorkReportSetting item = null;
		try {
			String crud = ServletUtils.getStringParameter(request, "crud", true);

			if (crud.equals(Crud.READ)) {

				WorkReportSetting wrkRptSetting = manager.getWorkReportSetting();

				if (wrkRptSetting != null) {
					item = new JsWorkReportSetting(wrkRptSetting);

					item.printDaysTransfert = ss.getPrintDaysTransfert();
					item.printTransfertDescription = ss.getPrintTransfertDescription();
					item.printSignature = ss.getPrintSignature();
					item.roundingHour = ss.getRoundingHour() == null ? 0 : ss.getRoundingHour();
					item.tracking = ss.getTracking();
					item.trackingMail = ss.getMailTracking();
					item.trackingCloud = ss.getCloudTracking();
					
					item.defaultApplySignature = ss.getDefaultApplySignature();
					item.defaultChargeTo = ss.getDefaultChargeTo();
					item.defaultFreeSupport = ss.getDefaultFreeSupport();
					item.defaultStatus = ss.getDefaultDocStatusId();
				} else {
					item = new JsWorkReportSetting(new WorkReportSetting());
				}

				new JsonResult(item).printTo(out);

			} else if (crud.equals(Crud.UPDATE)) {

				Payload<MapItem, JsWorkReportSetting> pl = ServletUtils.getPayload(request, JsWorkReportSetting.class);

				WorkReportSetting wrkSetting = JsWorkReportSetting.createWorkReportSetting(pl.data);

				if (pl.map.has("printDaysTransfert")) {
					ss.setPrintDaysTransfert(pl.data.printDaysTransfert);
				}

				if (pl.map.has("printTransfertDescription")) {
					ss.setPrintTransfertDescription(pl.data.printTransfertDescription);
				}

				if (pl.map.has("printSignature")) {
					ss.setPrintSignature(pl.data.printSignature);
				}

				if (pl.map.has("roundingHour")) {
					ss.setRoundingHour(pl.data.roundingHour);
				}

				if (pl.map.has("tracking")) {
					ss.setTracking(pl.data.tracking);
				}

				if (pl.map.has("trackingMail")) {
					ss.setMailTracking(pl.data.trackingMail);
				}

				if (pl.map.has("trackingCloud")) {
					ss.setCloudTracking(pl.data.trackingCloud);
				}
				
				if (pl.map.has("defaultApplySignature")) {
					ss.setDefaultApplySignature(pl.data.defaultApplySignature);
				}
				
				if (pl.map.has("defaultChargeTo")) {
					ss.setDefaultChargeTo(pl.data.defaultChargeTo);
				}
				
				if (pl.map.has("defaultFreeSupport")) {
					ss.setDefaultFreeSupport(pl.data.defaultFreeSupport);
				}
				
				if (pl.map.has("defaultStatus")) {
					ss.setDefaultDocStatusId(pl.data.defaultStatus);
				}

				manager.updateWorkReportSetting(wrkSetting);

				new JsonResult().printTo(out);

			}
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ManageWorkReportSetting", ex);
		}
	}

	public void processManageTimetableSetting(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		JsTimetableSetting item = null;
		try {
			String crud = ServletUtils.getStringParameter(request, "crud", true);

			if (crud.equals(Crud.READ)) {
				TimetableSetting ttSetting = manager.getTimetableSetting();

				if (ttSetting != null) {
					item = new JsTimetableSetting(ttSetting);
				} else {
					item = new JsTimetableSetting(new TimetableSetting());
				}

				new JsonResult(item).printTo(out);

			} else if (crud.equals(Crud.UPDATE)) {
				Payload<MapItem, JsTimetableSetting> pl = ServletUtils.getPayload(request, JsTimetableSetting.class);

				TimetableSetting tSetting = JsTimetableSetting.createTimetableSetting(pl.data);

				manager.updateTimetableSetting(tSetting);

				new JsonResult().printTo(out);

			}
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ManageTimetableSetting", ex);
		}
	}
	
	public void processManageTimetableStamp(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			String crud = ServletUtils.getStringParameter(request, "crud", true);

			if (crud.equals(Crud.CREATE)) {
				Payload<MapItem, JsTimetableStamp> pl = ServletUtils.getPayload(request, JsTimetableStamp.class);
				
				TimetableStamp ts = JsTimetableStamp.createTimetableStamp(pl.data);
				
				manager.addTimetableStamp(ts);

				new JsonResult().printTo(out);
			} else if (crud.equals(Crud.DELETE)) {

				IntegerArray ids = ServletUtils.getObjectParameter(request, "ids", IntegerArray.class, true);

				manager.deleteTimetableStamp(ids.get(0));

				new JsonResult().printTo(out);
			}
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ManageTimetableStamp", ex);
		}
	}
	
	public void processManageTimetableRequestCancellation(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			
			Integer id = ServletUtils.getIntParameter(request, "leaveRequestId", true);
			String cancellationReason = ServletUtils.getStringParameter(request, "cancellationReason", true);

			manager.timetableRequestCancellation(id, cancellationReason);

			new JsonResult().printTo(out);
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ManageTimetableRequestCancellation", ex);
		}
	}
	
	public void processPrintWorkReport(HttpServletRequest request, HttpServletResponse response) {
		ArrayList<RBWorkReport> itemsWr = new ArrayList<>();
		
		try {
			IContactsManager contactManager = (IContactsManager) WT.getServiceManager("com.sonicle.webtop.contacts", getEnv().getProfileId());
			
			String filename = ServletUtils.getStringParameter(request, "filename", "print");
			StringArray ids = ServletUtils.getObjectParameter(request, "ids", StringArray.class, true);
			
			WorkReport wr = null;
			CompanyPicture picture = null;
			Company company = null;
			
			for(String id : ids) {
				picture = null;
				wr = manager.getWorkReport(id);
				company = manager.getCompany(wr.getCompanyId());
				if(company.getHasPicture()) picture = manager.getCompanyPicture(company.getCompanyId());
				itemsWr.add(new RBWorkReport(WT.getCoreManager(), manager, contactManager, wr, ss, picture));
			}
			
			ReportConfig.Builder builder = reportConfigBuilder();
			RptWorkReport rpt = new RptWorkReport(builder.build());
			rpt.setDataSource(itemsWr);
			
			ServletUtils.setFileStreamHeaders(response, filename + ".pdf");
			WT.generateReportToStream(rpt, AbstractReport.OutputType.PDF, response.getOutputStream());
			
		} catch(Exception ex) {
			logger.error("Error in action PrintWorkReport", ex);
			ex.printStackTrace();
			ServletUtils.writeErrorHandlingJs(response, ex.getMessage());
		}
	}
	
	public void processPrintTimetableReport(HttpServletRequest request, HttpServletResponse response) {
		ArrayList<RBTimetableReport> items = new ArrayList<>();
		List<OTimetableReport> trs = null;
		
		try {
			String filename = ServletUtils.getStringParameter(request, "filename", "print");
			
			trs = manager.getTimetableReport();
			
			for(OTimetableReport otr : trs) {
				items.add(new RBTimetableReport(WT.getCoreManager(), manager, otr));
			}
			
			ReportConfig.Builder builder = reportConfigBuilder();
			RptTimetableReport rpt = new RptTimetableReport(builder.build());
			rpt.setDataSource(items);
			
			ServletUtils.setFileStreamHeaders(response, filename + ".pdf");
			WT.generateReportToStream(rpt, AbstractReport.OutputType.PDF, response.getOutputStream());
			
		} catch(Exception ex) {
			logger.error("Error in action PrintTimetableReport", ex);
			ex.printStackTrace();
			ServletUtils.writeErrorHandlingJs(response, ex.getMessage());
		}
	}
	
	public void processSetTimetable(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			String type = ServletUtils.getStringParameter(request, "type", true);
			
			TimetableStamp stamp = new TimetableStamp();
			stamp.setType(type);
			
			if(ChekIpAddressNetwork()){
				manager.setTimetable(stamp);
			}
			
			new JsonResult().printTo(out);
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action SetTimetable", ex);
		}
	}
	
	public void processChekManageStampsButtons(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			new JsonResult(ChekManageStampsButtons()).printTo(out);
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ChekManageStampsButtons", ex);
		}
	}
	
	private boolean ChekManageStampsButtons() throws WTException, UnknownHostException, SQLException{
		boolean enabling = false;
		TimetableSetting ts = manager.getTimetableSetting();
		
		if(ts != null){
			enabling = ts.getManageStamp();
			
			if(!enabling){
				//Attivo solo se chi è loggato è supervisore
				if(manager.getDrmProfileMemberByUserId(getEnv().getProfileId().getUserId()).size() > 0) enabling = true;
			}
		}else{
			if(manager.getDrmProfileMemberByUserId(getEnv().getProfileId().getUserId()).size() > 0) enabling = true;
		}
		
		return enabling;
	}
	
	public void processChekCompanyExitAuthorization(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			new JsonResult(ChekCompanyExitAuthorization()).printTo(out);
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ChekCompanyExitAuthorization", ex);
		}
	}
	
	private boolean ChekCompanyExitAuthorization() throws WTException, UnknownHostException{
		boolean enabling = false;
		TimetableSetting ts = manager.getTimetableSetting();

		if(ts != null){
			enabling = ts.getCompanyExit();
		}
		
		return enabling;
	}
	
	public void processChekIpAddressNetwork(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			new JsonResult(ChekIpAddressNetwork()).printTo(out);
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ChekIpAddressNetwork", ex);
		}
	}
	
	private boolean ChekIpAddressNetwork() throws WTException, UnknownHostException{
		boolean enabling = false;
		TimetableSetting ts = manager.getTimetableSetting();

		if(!StringUtils.isEmpty(ts.getAllowedAddresses())){
			String ip = getEnv().getWebTopSession().getRemoteIP();

			for(String address : ts.getAllowedAddresses().split(",")){
				if(address.contains("/")){
					//Network
					if(IPUtils.isIPInRange(new String[]{address}, ip)){
						enabling = true;
					}
				}else{
					//IP
					if(address.equals(ip)){
						enabling = true;
					}
				}
			}
		}else{
			enabling = true;
		}
		
		return enabling;
	}
	
	public void processManageGridTimetable(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			String crud = ServletUtils.getStringParameter(request, "crud", true);
			if (crud.equals(Crud.READ)) {

				List<JsGridTimetableStamp> jsGridTS = new ArrayList();

				for (OTimetableStamp oTS : manager.listTimetableStamp()) {

					jsGridTS.add(new JsGridTimetableStamp(oTS));
				}

				new JsonResult(jsGridTS).printTo(out);

			}
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ManageGridTimetable", ex);
		}
	}
	
	public void processManageGridTimetableList(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			String crud = ServletUtils.getStringParameter(request, "crud", true);
			if (crud.equals(Crud.READ)) {
				
				String query = ServletUtils.getStringParameter(request, "query", null);
				TimetableStampQuery tsQuery = TimetableStampQuery.fromJson(query);
				
				List<JsGridTimetableStampList> jsGridTSL = new ArrayList();

				for (OTimetableStamp oTS : manager.listTimetableStamps(tsQuery)) {

					jsGridTSL.add(new JsGridTimetableStampList(oTS));
				}

				new JsonResult(jsGridTSL).printTo(out);

			}
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ManageGridTimetableList", ex);
		}
	}
	
	public void processManageGridTimetableRequest(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			String crud = ServletUtils.getStringParameter(request, "crud", true);
			if (crud.equals(Crud.READ)) {

				String query = ServletUtils.getStringParameter(request, "query", null);
				LeaveRequestQuery lrQuery = LeaveRequestQuery.fromJson(query);
				List<JsGridLeaveRequest> jsGridLR = new ArrayList();

				for (OLeaveRequest oLR : manager.listLeaveRequest(lrQuery)) {

					jsGridLR.add(new JsGridLeaveRequest(oLR));
				}

				new JsonResult(jsGridLR).printTo(out);

			}
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ManageGridTimetable", ex);
		}
	}
	
	public void processManageGridTimetableReport(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			String crud = ServletUtils.getStringParameter(request, "crud", true);
			if (crud.equals(Crud.READ)) {
				String query = ServletUtils.getStringParameter(request, "query", null);
				
				TimetableReportQuery trQuery = TimetableReportQuery.fromJson(query);
				
				List<JsGridTimetableReport> jsGridTR = new ArrayList();
				
				for (OTimetableReport oTR : manager.generateTimetableReport(trQuery)) {

					jsGridTR.add(new JsGridTimetableReport(oTR, manager));
				}
				
				new JsonResult(jsGridTR).printTo(out);
			}else if(crud.equals(Crud.UPDATE)){
				Payload<MapItem, JsGridTimetableReport> pl = ServletUtils.getPayload(request, JsGridTimetableReport.class);
				
				TimetableReport tr = JsGridTimetableReport.createTimetableReport(pl.data);

				manager.updateTimetableReport(tr);

				new JsonResult().printTo(out);
			}
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ManageGridTimetableReport", ex);
		}
	}
	
	public void processIsTimetableRequestPreviousDate(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try{
			TimetableSetting ts = manager.getTimetableSetting();
			
			Boolean isPreviousDate = ts.getRequestsHolidaysPermitsPreviousDates();

			new JsonResult(isPreviousDate).printTo(out);
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ManageGridTimetable", ex);
		}
	}
	
	public void processIsLineManagerLogged(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try{
			DrmLineManager lm = manager.getDrmLineManager(getEnv().getProfileId().getUserId());
			
			Boolean isLineManagerLogged = (lm != null) ? true : false;

			new JsonResult(isLineManagerLogged).printTo(out);
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ManageGridTimetable", ex);
		}
	}
	
	public String getGridOpportunityAdditionalInfo(List<OOpportunityField> fields, VOpportunityEntry o) throws WTException{
		String additionalInfo = "";
		
		if(fields != null){
			for(OOpportunityField field : fields){
				switch(field.getFieldId()){
					case "activity":		
						if(o.getActivityId() != null)
							additionalInfo += field.getLabel() + ": " + WT.getCoreManager().getActivity(o.getActivityId()).getDescription() + " / ";		
						break;
					case "causal":	
						if(o.getCausalId() != null)
							additionalInfo += field.getLabel() + ": " + WT.getCoreManager().getCausal(o.getCausalId()).getDescription() + " / ";			
						break;
					case "customer":
						if(o.getCustomerId() != null)
							additionalInfo += field.getLabel() + ": " + WT.getCoreManager().getMasterData(o.getCustomerId()).getDescription() + " / ";		
						break;
					case "description":
						if(o.getDescription() != null)
							additionalInfo += field.getLabel() + ": " + o.getDescription() + " / ";		
						break;
					case "executed_with":
						if(o.getExecutedWith() != null)
							additionalInfo += field.getLabel() + ": " + WT.getUserData(new UserProfileId(o.getDomainId(), o.getExecutedWith())).getDisplayName() + " / ";		
						break;
					case "place":
						if(o.getPlace() != null)
							additionalInfo += field.getLabel() + ": " + o.getPlace() + " / ";				
						break;
					case "sector":
						if(o.getSector() != null)
							additionalInfo += field.getLabel() + ": " + o.getSector() + " / ";			
						break;
					case "statistic_customer":
						if(o.getCustomerStatId() != null)
							additionalInfo += field.getLabel() + ": " + WT.getCoreManager().getMasterData(o.getCustomerStatId()).getDescription() + " / ";	
						break;
					case "status":	
						if(o.getStatusId() != null)
							additionalInfo += field.getLabel() + ": " + manager.getDocStatus(o.getStatusId()).getDescription() + " / ";			
						break;
					case "signature":
						if(o.getSignature() != null)
							additionalInfo += field.getLabel() + ": " +  ((o.getSignature()) ? lookupResource("grpOpportunity.fld.yes") : lookupResource("grpOpportunity.fld.no")) + " / ";		
						break;
					case "notes":
						if(o.getNotes() != null)
							additionalInfo += field.getLabel() + ": " + o.getNotes() + " / ";		
						break;
					case "signed_by":
						if(o.getSignedBy() != null)
							additionalInfo +=  field.getLabel() + ": " + WT.getUserData(new UserProfileId(o.getDomainId(), o.getSignedBy())).getDisplayName() + " / ";	
						break;
					case "won":
						if(o.getWon() != null)
							additionalInfo += field.getLabel() + ": " +  ((o.getWon()) ? lookupResource("grpOpportunity.fld.yes") : lookupResource("grpOpportunity.fld.no")) + " / ";
						break;
					case "discoveries":
						if(o.getDiscoveries() != null)
							additionalInfo += field.getLabel() + ": " + o.getDiscoveries() + " / ";		
						break;
					case "customer_potential":
						if(o.getCustomerPotential() != null)
							additionalInfo += field.getLabel() + ": " + o.getCustomerPotential() + " / ";	
						break;
					case "result":
						if(o.getResult() != null)
							additionalInfo += field.getLabel() + ": " + o.getResult() + " / ";			
						break;
					case "objective":
						if(EnumUtils.toSerializedName(OpportunityField.Tab.MAIN).equals(field.getTabId())){
							if(o.getObjective() != null)
								additionalInfo += field.getLabel() + ": " + o.getObjective() + " / ";
						}else{
							if(o.getObjective_2() != null)
								additionalInfo += field.getLabel() + ": " + o.getObjective_2() + " / ";
						}
						break;
					default:
						break;
				}
			}
		}
		
		return additionalInfo;
	}
	
	private ReportConfig.Builder reportConfigBuilder() {
		UserProfile.Data ud = getEnv().getProfile().getData();
		CoreUserSettings cus = getEnv().getCoreUserSettings();
		return new ReportConfig.Builder()
				.useLocale(ud.getLocale())
				.useTimeZone(ud.getTimeZone().toTimeZone())
				.dateFormatShort(cus.getShortDateFormat())
				.dateFormatLong(cus.getLongDateFormat())
				.timeFormatShort(cus.getShortTimeFormat())
				.timeFormatLong(cus.getLongTimeFormat())
				.generatedBy(WT.getPlatformName() + " " + lookupResource(WorkReportLocale.SERVICE_NAME))
				.printedBy(ud.getDisplayName());
	}
}
