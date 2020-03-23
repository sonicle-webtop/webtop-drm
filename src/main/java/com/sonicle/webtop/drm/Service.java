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
import com.sonicle.commons.LangUtils;
import com.sonicle.commons.net.IPUtils;
import com.sonicle.commons.time.DateTimeUtils;
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
import com.sonicle.webtop.contacts.model.ContactLookup;
import com.sonicle.webtop.contacts.model.Grouping;
import com.sonicle.webtop.contacts.model.ListContactsResult;
import com.sonicle.webtop.contacts.model.ShowBy;
import com.sonicle.webtop.core.CoreUserSettings;
import com.sonicle.webtop.core.app.WT;
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
import com.sonicle.webtop.core.sdk.UserProfile.Data;
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
import com.sonicle.webtop.drm.bol.js.JsPrepareOpportunityAction;
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
import com.sonicle.webtop.drm.model.GroupCategory;
import com.sonicle.webtop.drm.model.HourProfile;
import com.sonicle.webtop.drm.model.LeaveRequest;
import com.sonicle.webtop.drm.model.LeaveRequestDocument;
import com.sonicle.webtop.drm.model.LeaveRequestDocumentWithBytes;
import com.sonicle.webtop.drm.model.LeaveRequestDocumentWithStream;
import com.sonicle.webtop.drm.model.Opportunity;
import com.sonicle.webtop.drm.model.OpportunityAction;
import com.sonicle.webtop.drm.model.OpportunityActionDocument;
import com.sonicle.webtop.drm.model.OpportunityActionDocumentWithBytes;
import com.sonicle.webtop.drm.model.OpportunityActionDocumentWithStream;
import com.sonicle.webtop.drm.model.OpportunityDocument;
import com.sonicle.webtop.drm.model.OpportunityDocumentWithBytes;
import com.sonicle.webtop.drm.model.OpportunityDocumentWithStream;
import com.sonicle.webtop.drm.model.OpportunityField;
import com.sonicle.webtop.drm.model.OpportunitySetting;
import com.sonicle.webtop.drm.model.TimetableReport;
import com.sonicle.webtop.drm.model.TimetableSetting;
import com.sonicle.webtop.drm.model.TimetableStamp;
import com.sonicle.webtop.drm.model.WorkReport;
import com.sonicle.webtop.drm.model.WorkReportAttachment;
import com.sonicle.webtop.drm.model.WorkReportAttachmentWithBytes;
import com.sonicle.webtop.drm.model.WorkReportAttachmentWithStream;
import com.sonicle.webtop.drm.model.WorkReportSetting;
import com.sonicle.webtop.drm.rpt.RptTimetableReport;
import com.sonicle.webtop.drm.rpt.RptWorkReport;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
import com.sonicle.webtop.calendar.ICalendarManager;
import com.sonicle.webtop.calendar.model.Calendar;
import com.sonicle.webtop.calendar.model.Event;
import com.sonicle.webtop.calendar.model.EventInstance;
import com.sonicle.webtop.calendar.model.EventKey;
import com.sonicle.webtop.calendar.model.UpdateEventTarget;
import com.sonicle.webtop.drm.bol.ODay;
import com.sonicle.webtop.drm.bol.ODefaultCostType;
import com.sonicle.webtop.drm.bol.OExpenseNote;
import com.sonicle.webtop.drm.bol.js.JsCostType;
import com.sonicle.webtop.drm.bol.js.JsExpenseNote;
import com.sonicle.webtop.drm.bol.js.JsExpenseNoteSetting;
import com.sonicle.webtop.drm.bol.js.JsFilter;
import com.sonicle.webtop.drm.bol.js.JsGridExpenseNote;
import com.sonicle.webtop.drm.bol.model.RBExpenseNote;
import com.sonicle.webtop.drm.bol.model.RBOpportunity;
import com.sonicle.webtop.drm.bol.model.RBTimetableEncoReport;
import com.sonicle.webtop.drm.bol.model.RBWorkReportSummary;
import com.sonicle.webtop.drm.model.CostType;
import com.sonicle.webtop.drm.model.ExpenseNote;
import com.sonicle.webtop.drm.model.ExpenseNoteDetailDocumentWithBytes;
import com.sonicle.webtop.drm.model.ExpenseNoteDocument;
import com.sonicle.webtop.drm.model.ExpenseNoteDocumentWithBytes;
import com.sonicle.webtop.drm.model.ExpenseNoteDocumentWithStream;
import com.sonicle.webtop.drm.model.ExpenseNoteSetting;
import com.sonicle.webtop.drm.model.WorkReportSummary;
import com.sonicle.webtop.drm.rpt.RptExpenseNote;
import com.sonicle.webtop.drm.rpt.RptOpportunity;
import com.sonicle.webtop.drm.rpt.RptWorkReportSummary;
import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import javax.imageio.ImageIO;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author lssndrvs
 */
public class Service extends BaseService {

	public static final Logger logger = WT.getLogger(Service.class);

	private DrmManager manager;
	private DrmServiceSettings ss;
	private DrmUserSettings us;
	private LinkedHashMap<String, RootProgramNode> programs = new LinkedHashMap();

	private LinkedHashMap<String, String> groupCategories = new LinkedHashMap();

	@Override
	public void initialize() throws Exception {

		UserProfileId pid = getEnv().getProfileId();

		manager = (DrmManager) WT.getServiceManager(SERVICE_ID);
		ss = new DrmServiceSettings(SERVICE_ID, pid.getDomainId());
		us = new DrmUserSettings(SERVICE_ID, new UserProfileId(pid.getDomain(), pid.getUserId()));

		RootProgramNode prog = null;
		
		if("".equals(ss.getOpportunityGeneralTitle()) || null == ss.getOpportunityGeneralTitle())
			prog = new RootProgramNode(lookupResource(DrmTreeNode.OPPORTUNITY), "wtdrm-icon-opportunity-xs");
		else
			prog = new RootProgramNode(ss.getOpportunityGeneralTitle(), "wtdrm-icon-opportunity-xs");
		
		programs.put(prog.getId(), prog);

		prog = new RootProgramNode(lookupResource(DrmTreeNode.WORK_REPORT), "wtdrm-icon-workreport-xs");
		programs.put(prog.getId(), prog);

		prog = new RootProgramNode(lookupResource(DrmTreeNode.EXPENSE_NOTE), "wtdrm-icon-expensenote-xs");
		programs.put(prog.getId(), prog);

		prog = new RootProgramNode(lookupResource(DrmTreeNode.TIMETABLE), "wtdrm-icon-timetable-xs");
		prog.addSubPrograms(lookupResource(DrmTreeNode.TIMETABLE_STAMP), "wtdrm-icon-timetable1-xs");
		prog.addSubPrograms(lookupResource(DrmTreeNode.TIMETABLE_REQUEST), "wtdrm-icon-timetable2-xs");
		prog.addSubPrograms(lookupResource(DrmTreeNode.TIMETABLE_REPORT), "wtdrm-icon-timetable3-xs");
		//prog.addSubPrograms(lookupResource(DrmTreeNode.TIMETABLE_SUMMARY), "wtdrm-icon-timetable4-xs");
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
		DateTimeFormatter hmf = DateTimeUtils.createHmFormatter();
		
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
		vs.put("workReportDefaultStatus", ss.getWorkReportDefaultDocStatusId());
		vs.put("opportunityDefaultStatus", ss.getOpportunityDefaultDocStatusId());
		vs.put("opportunityTitle", ss.getOpportunityGeneralTitle());
		vs.put("opportunityEnablePrint", ss.getOpportunityGeneralEnablePrint());
		vs.put("medicalVisitsAutomaticallyApproved", ss.getMedicalVisitsAutomaticallyApproved());
		vs.put("workdayStart", hmf.print(us.getWorkdayStart()));
		vs.put("workdayEnd", hmf.print(us.getWorkdayEnd()));
		
		try {
			vs.put("opportunityRequiredFields", getOpportunityRequiredFields());
			vs.put("expenseNoteDefaultCurrency", getExpenseNoteDefaultCurrency());
			vs.put("expenseNoteKmCost", getExpenseNoteKmCost());
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
	
	private String getExpenseNoteDefaultCurrency() throws WTException {
		try{
			String value = us.getDefaultCurrency();
			if(value == null || "".equals(value)){
				ExpenseNoteSetting enS = manager.getExpenseNoteSetting();
				if(enS != null) value = enS.getDefaultCurrency();
			}
			
			return (value == null) ? "EUR" : value;			
		} catch (Exception ex) {
			throw new WTException("Error in getExpenseNoteDefaultCurrency", ex);
		}
	}
	
	private String getExpenseNoteKmCost() throws WTException {
		try{
			String value = us.getKmCost();
			BigDecimal value2 = null;
			if(value == null || "".equals(value)){
				ExpenseNoteSetting enS = manager.getExpenseNoteSetting();
				if(enS != null) value2 = enS.getKmCost();
				if(value2 != null) value = value2.toString();
			}
			return (value == null) ? "1" : value;	
		} catch (Exception ex) {
			throw new WTException("Error in getExpenseNoteDefaultCurrency", ex);
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
	
	public void processGetCostType(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {	
			Integer typeId = ServletUtils.getIntParameter(request, "typeId", true);
			
			CostType ct = manager.getCostType(typeId);
				
			new JsonResult(new JsCostType(ct)).printTo(out);
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action GetCostType", ex);
		}
	}
	
	public void processLookupCompleteCostTypes(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {			
			List<JsCostType> cts = new ArrayList();
			
			for (CostType ct : manager.listCostTypes()) {
				cts.add(new JsCostType(ct));
			}
				
			new JsonResult(cts, cts.size()).printTo(out);
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action LookupCompleteCostTypes", ex);
		}
	}
	
	public void processLookupCostTypes(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {			
			List<JsSimple> cts = new ArrayList();
			
			for (CostType ct : manager.listCostTypes()) {
				cts.add(new JsSimple(ct.getId(), ct.getDescription()));
			}
				
			new JsonResult(cts, cts.size()).printTo(out);
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action LookupCostTypes", ex);
		}
	}
	
	public void processLookupOperators(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			List<JsSimple> jsUser = new ArrayList();
			Data uD;
			
			for (String usr : manager.listOperators()) {
				uD = WT.getUserData(new UserProfileId(getEnv().getProfileId().getDomain(), usr));
				if(uD != null)
					jsUser.add(new JsSimple(usr, uD.getDisplayName()));
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
	
	public void processLookupCustomersSuppliers(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			String operator = ServletUtils.getStringParameter(request, "operator", null);
			List<JsSimple> customers = new ArrayList();
			
			if(operator != null){
				DrmManager manager = (DrmManager)WT.getServiceManager(SERVICE_ID, new UserProfileId(getEnv().getProfileId().getDomain(), operator));
				
				Map<String, MasterData> items = WT.getCoreManager().listMasterData(Arrays.asList(EnumUtils.toSerializedName(MasterData.Type.CUSTOMER), EnumUtils.toSerializedName(MasterData.Type.SUPPLIER)));
				for(MasterData customer : items.values()) {
					customers.add(new JsSimple(customer.getMasterDataId(), customer.getDescription()));
				}
			}
				
			new JsonResult(customers, customers.size()).printTo(out);
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action LookupCustomersSuppliers", ex);
		}
	}
	
	public void processLookupRealCustomers(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			String operator = ServletUtils.getStringParameter(request, "operator", null);
			List<String> idCustomers = new ArrayList();
			List<JsSimple> customers = new ArrayList();
			
			if(operator != null){
				DrmManager manager = (DrmManager)WT.getServiceManager(SERVICE_ID, new UserProfileId(getEnv().getProfileId().getDomain(), operator));

				idCustomers = manager.listCustomersByProfileUser();
				
				Map<String, MasterData> items = null;
				if(idCustomers.size() > 0)
					items = WT.getCoreManager().listMasterDataIn(idCustomers);
				else
					items = WT.getCoreManager().listMasterData(Arrays.asList(EnumUtils.toSerializedName(MasterData.Type.CUSTOMER)));
				
				for (MasterData customer : items.values()) {
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
			
			List<JsSimple> customers = new ArrayList();
			List<String> idCustomers = new ArrayList();
			
			idCustomers = manager.listCustomersByProfileUser();
			
			Map<String, MasterData> items = null;
			if(idCustomers.size() > 0)
				items = WT.getCoreManager().listMasterDataIn(idCustomers);
			else
				items = WT.getCoreManager().listMasterData(Arrays.asList(EnumUtils.toSerializedName(MasterData.Type.CUSTOMER)));
			
			items.putAll(WT.getCoreManager().listChildrenMasterData(Arrays.asList(EnumUtils.toSerializedName(MasterData.Type.CUSTOMER))));

			for(MasterData customer : items.values()) {
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
	
	public void processLookupCostType(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {

			List<JsSimple> costTypes = new ArrayList();

			costTypes.add(new JsSimple(EnumUtils.toSerializedName(ODefaultCostType.STAY), lookupResource("costType.S")));
			costTypes.add(new JsSimple(EnumUtils.toSerializedName(ODefaultCostType.HIGHWAY), lookupResource("costType.H")));
			costTypes.add(new JsSimple(EnumUtils.toSerializedName(ODefaultCostType.PARK), lookupResource("costType.P")));
			costTypes.add(new JsSimple(EnumUtils.toSerializedName(ODefaultCostType.MEALS), lookupResource("costType.M")));
			costTypes.add(new JsSimple(EnumUtils.toSerializedName(ODefaultCostType.OTHER), lookupResource("costType.O")));
			costTypes.add(new JsSimple(EnumUtils.toSerializedName(ODefaultCostType.KM), lookupResource("costType.K")));

			new JsonResult(costTypes, costTypes.size()).printTo(out);
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action LookupCostType", ex);
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
			int page = ServletUtils.getIntParameter(request, "page", true);
			int limit = ServletUtils.getIntParameter(request, "limit", 50);
			String value = "";
			String pattern;
			
			List<JsSimpleSource> contacts = new ArrayList();
			Set<Integer> categoryIds = new LinkedHashSet();
			Data uD;
			
			//Check for multiple filters
			try {
				JsFilter.List filterList = ServletUtils.getObjectParameter(request,"filter",JsFilter.List.class,false);
				if (filterList!=null) {
					//in this case I'm sure the filter is only one
					for(JsFilter filter: filterList) {
						value += filter.value;
					}
				}
			} catch(Exception exc) {
				logger.error("Excetpion",exc);
			}
			
			pattern = StringUtils.isBlank(value) ? null : "%" + value + "%";
					
			IContactsManager contactManager = (IContactsManager) WT.getServiceManager("com.sonicle.webtop.contacts", getEnv().getProfileId());
			categoryIds = contactManager.listCategoryIds();
			categoryIds.addAll(contactManager.listIncomingCategoryIds());
			ListContactsResult lcr = contactManager.listContacts(categoryIds, false, Grouping.ALPHABETIC, ShowBy.FIRST_LAST, pattern, page, limit, true);
			for(ContactLookup c: lcr.items){
				String fullName = StringUtils.isEmpty(c.getFullName(true)) ? "" : c.getFullName(true);
				String company = StringUtils.isEmpty(c.getCompanyDescription()) ? "" : c.getCompanyDescription();
				String info = (fullName.length() > 0 && company.length() > 0) ? fullName + " - " + company : fullName + company;
				uD = WT.getUserData(c.getCategoryProfileId());
				contacts.add(new JsSimpleSource(c.getContactId(), info, "[" + uD.getDisplayName() + " / " + c.getCategoryName() + "]"));
			}

			new JsonResult(contacts, lcr.fullCount).setPage(page).printTo(out);
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
					item.additionalInfo = (o.getActionId() == 0) ? getGridOpportunityAdditionalInfo(fields, o) : (((o.getActivityId() != null) ? "[" + WT.getCoreManager().getActivity(o.getActivityId()).getDescription() + "] " : "") + ((o.getDescription() != null) ? o.getDescription() : ""));					
					
					if(o.getStatusId() != null){
						if("C".equals(manager.getDocStatus(o.getStatusId()).getType())){
							if(o.getActionId() == 0){
								if(manager.countOpportunityActionsStatusOpenByOpportunityId(o.getId()) == 0) item.isTotallyClosed = true;
							}else{
								item.isTotallyClosed = true;
							}
						}
					}
					
					
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
			if (crud.equals(Crud.READ)) {

				Integer id = ServletUtils.getIntParameter(request, "id", true);

				Opportunity o = manager.getOpportunity(id);

				item = new JsOpportunity(o);

				new JsonResult(item).printTo(out);

			} else if (crud.equals(Crud.CREATE)) {

				Payload<MapItem, JsOpportunity> pl = ServletUtils.getPayload(request, JsOpportunity.class);

				Opportunity o = JsOpportunity.createOpportunity(pl.data);
				
				for (JsOpportunity.Document jsdoc : pl.data.documents) {
					UploadedFile upFile = getUploadedFileOrThrow(jsdoc._uplId);
					OpportunityDocumentWithStream doc = new OpportunityDocumentWithStream(upFile.getFile());
					doc.setId(jsdoc.id);
					doc.setFileName(upFile.getFilename());
					doc.setSize(upFile.getSize());
					doc.setMediaType(upFile.getMediaType());
					o.getDocuments().add(doc);
				}
				
				Integer eventId = createOrUpdateOpportunityEventIntoOpportunityCalendar(o);
				o.setEventId(eventId);
				
				manager.addOpportunity(o);

				new JsonResult().printTo(out);

			} else if (crud.equals(Crud.UPDATE)) {

				Payload<MapItem, JsOpportunity> pl = ServletUtils.getPayload(request, JsOpportunity.class);

				Opportunity o = JsOpportunity.createOpportunity(pl.data);
				
				for (JsOpportunity.Document jsdoc : pl.data.documents) {
					if(!StringUtils.isBlank(jsdoc._uplId)){
						UploadedFile upFile = getUploadedFileOrThrow(jsdoc._uplId);
						OpportunityDocumentWithStream doc = new OpportunityDocumentWithStream(upFile.getFile());
						doc.setId(jsdoc.id);
						doc.setFileName(upFile.getFilename());
						doc.setSize(upFile.getSize());
						doc.setMediaType(upFile.getMediaType());
						o.getDocuments().add(doc);
					}else{
						OpportunityDocument doc = new OpportunityDocument();
						doc.setId(jsdoc.id);
						doc.setFileName(jsdoc.name);
						doc.setSize(jsdoc.size);
						o.getDocuments().add(doc);
					}
				}

				Integer eventId = createOrUpdateOpportunityEventIntoOpportunityCalendar(o);
				o.setEventId(eventId);
				
				for(OpportunityAction oAct : o.getActions())
					updateOpportunityActionEventTitle(oAct, o);
				
				manager.updateOpportunity(o);

				new JsonResult().printTo(out);

			} else if (crud.equals(Crud.DELETE)) {

				StringArray ids = ServletUtils.getObjectParameter(request, "ids", StringArray.class, true);

				Opportunity o = manager.getOpportunity(Integer.parseInt(ids.get(0)));
				
				deleteOpportunityEvent(o);
				
				for(OpportunityAction oAct : o.getActions())
					deleteOpportunityActionEvent(oAct);
				
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
			if (crud.equals(Crud.READ)) {

				Integer id = ServletUtils.getIntParameter(request, "id", true);

				OpportunityAction oAct = manager.getOpportunityAction(id);

				item = new JsOpportunityAction(oAct);

				new JsonResult(item).printTo(out);

			} else if (crud.equals(Crud.CREATE)) {

				Payload<MapItem, JsOpportunityAction> pl = ServletUtils.getPayload(request, JsOpportunityAction.class);

				OpportunityAction oAct = JsOpportunityAction.createOpportunityAction(pl.data);
				
				for (JsOpportunityAction.Document jsdoc : pl.data.actionDocuments) {
					UploadedFile upFile = getUploadedFileOrThrow(jsdoc._uplId);
					OpportunityActionDocumentWithStream doc = new OpportunityActionDocumentWithStream(upFile.getFile());
					doc.setId(jsdoc.id);
					doc.setFileName(upFile.getFilename());
					doc.setSize(upFile.getSize());
					doc.setMediaType(upFile.getMediaType());
					oAct.getActionDocuments().add(doc);
				}

				Integer eventId = createOrUpdateOpportunityActionEventIntoOpportunityCalendar(oAct);
				oAct.setEventId(eventId);
				
				manager.addOpportunityAction(oAct);

				new JsonResult().printTo(out);

			} else if (crud.equals(Crud.UPDATE)) {

				Payload<MapItem, JsOpportunityAction> pl = ServletUtils.getPayload(request, JsOpportunityAction.class);

				OpportunityAction oAct = JsOpportunityAction.createOpportunityAction(pl.data);
				
				for (JsOpportunityAction.Document jsdoc : pl.data.actionDocuments) {
					if(!StringUtils.isBlank(jsdoc._uplId)){
						UploadedFile upFile = getUploadedFileOrThrow(jsdoc._uplId);
						OpportunityActionDocumentWithStream doc = new OpportunityActionDocumentWithStream(upFile.getFile());
						doc.setId(jsdoc.id);
						doc.setFileName(upFile.getFilename());
						doc.setSize(upFile.getSize());
						doc.setMediaType(upFile.getMediaType());
						oAct.getActionDocuments().add(doc);
					}else{
						OpportunityActionDocument doc = new OpportunityActionDocument();
						doc.setId(jsdoc.id);
						doc.setFileName(jsdoc.name);
						doc.setSize(jsdoc.size);
						oAct.getActionDocuments().add(doc);
					}
				}

				Integer eventId = createOrUpdateOpportunityActionEventIntoOpportunityCalendar(oAct);
				oAct.setEventId(eventId);
				
				manager.updateOpportunityAction(oAct);

				new JsonResult().printTo(out);

			} else if (crud.equals(Crud.DELETE)) {

				StringArray ids = ServletUtils.getObjectParameter(request, "ids", StringArray.class, true);
				
				OpportunityAction oAct = manager.getOpportunityAction(Integer.parseInt(ids.get(0)));
				
				deleteOpportunityActionEvent(oAct);

				manager.deleteOpportunityAction(Integer.parseInt(ids.get(0)));

				new JsonResult().printTo(out);
			}
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ManageOpportunityAction", ex);
		}
	}
	
	public void processManagePrepareOpportunityActions(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try{
			Payload<MapItem, JsPrepareOpportunityAction> pl = ServletUtils.getPayload(request, JsPrepareOpportunityAction.class);

			List<OpportunityAction> oActs = JsPrepareOpportunityAction.createOpportunityActions(pl.data);
			String statusId = ss.getOpportunityDefaultDocStatusId();
			
			for (OpportunityAction act : oActs) {
				if(statusId != null && !"null".equals(statusId)) act.setStatusId(Integer.parseInt(statusId));
				
				Integer eventId = createOrUpdateOpportunityActionEventIntoOpportunityCalendar(act);
				act.setEventId(eventId);

				manager.addOpportunityAction(act);
			}

			new JsonResult().printTo(out);
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ManagePrepareOpportunityActions", ex);
		}
	}
	
	public void processManageGridExpenseNote(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try {
			String crud = ServletUtils.getStringParameter(request, "crud", true);
			
			if (crud.equals(Crud.READ)) {
				
				String query = ServletUtils.getStringParameter(request, "query", null);
				ExpenseNoteQuery eNQuery = ExpenseNoteQuery.fromJson(query);
				List<JsGridExpenseNote> jsGridEN = new ArrayList();
				
				for (OExpenseNote oEN : manager.listExpenseNote(eNQuery)) {
					jsGridEN.add(new JsGridExpenseNote(oEN));
				}

				new JsonResult(jsGridEN).printTo(out);
			}
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ManageGridExpenseNote", ex);
		}
	}
	
	public void processManageExpenseNote(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		JsExpenseNote item = null;
		try {
			String crud = ServletUtils.getStringParameter(request, "crud", true);
			if (crud.equals(Crud.READ)) {

				Integer id = ServletUtils.getIntParameter(request, "id", true);

				ExpenseNote eN = manager.getExpenseNote(id);

				item = new JsExpenseNote(eN);

				new JsonResult(item).printTo(out);

			} else if (crud.equals(Crud.CREATE)) {

				Payload<MapItem, JsExpenseNote> pl = ServletUtils.getPayload(request, JsExpenseNote.class);

				ExpenseNote eN = JsExpenseNote.createExpenseNote(pl.data);
				
				for (JsExpenseNote.Document jsdoc : pl.data.documents) {
					UploadedFile upFile = getUploadedFileOrThrow(jsdoc._uplId);
					ExpenseNoteDocumentWithStream doc = new ExpenseNoteDocumentWithStream(upFile.getFile());
					doc.setId(jsdoc.id);
					doc.setFileName(upFile.getFilename());
					doc.setSize(upFile.getSize());
					doc.setMediaType(upFile.getMediaType());
					eN.getDocuments().add(doc);
				}
				
				//Save description for suggestions
				if (eN.getDescription() !=null && eN.getDescription().trim().length() > 0)
					WT.getCoreManager().addServiceStoreEntry(SERVICE_ID, "expenseNoteDescription", eN.getDescription().toUpperCase(), eN.getDescription());

				
				manager.addExpenseNote(eN);

				new JsonResult().printTo(out);

			} else if (crud.equals(Crud.UPDATE)) {

				Payload<MapItem, JsExpenseNote> pl = ServletUtils.getPayload(request, JsExpenseNote.class);

				ExpenseNote eN = JsExpenseNote.createExpenseNote(pl.data);
				
				for (JsExpenseNote.Document jsdoc : pl.data.documents) {
					if(!StringUtils.isBlank(jsdoc._uplId)){
						UploadedFile upFile = getUploadedFileOrThrow(jsdoc._uplId);
						ExpenseNoteDocumentWithStream doc = new ExpenseNoteDocumentWithStream(upFile.getFile());
						doc.setId(jsdoc.id);
						doc.setFileName(upFile.getFilename());
						doc.setSize(upFile.getSize());
						doc.setMediaType(upFile.getMediaType());
						eN.getDocuments().add(doc);
					}else{
						ExpenseNoteDocument doc = new ExpenseNoteDocument();
						doc.setId(jsdoc.id);
						doc.setFileName(jsdoc.name);
						doc.setSize(jsdoc.size);
						eN.getDocuments().add(doc);
					}
				}
				
				if (eN.getDescription() !=null && eN.getDescription().trim().length() > 0)
					WT.getCoreManager().addServiceStoreEntry(SERVICE_ID, "expenseNoteDescription", eN.getDescription().toUpperCase(), eN.getDescription());
				
				manager.updateExpenseNote(eN);

				new JsonResult().printTo(out);

			} else if (crud.equals(Crud.DELETE)) {

				StringArray ids = ServletUtils.getObjectParameter(request, "ids", StringArray.class, true);

				ExpenseNote eN = manager.getExpenseNote(Integer.parseInt(ids.get(0)));
				
				manager.deleteExpenseNote(Integer.parseInt(ids.get(0)));

				new JsonResult().printTo(out);
			}
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ManageExpenseNote", ex);
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
			if (crud.equals(Crud.READ)) {

				String id = ServletUtils.getStringParameter(request, "id", true);

				WorkReport wrkRpt = manager.getWorkReport(id);

				String _profileId = "";

				item = new JsWorkReport(wrkRpt, ptz, _profileId);

				new JsonResult(item).printTo(out);

			} else if (crud.equals(Crud.CREATE)) {

				Payload<MapItem, JsWorkReport> pl = ServletUtils.getPayload(request, JsWorkReport.class);

				WorkReport wrkRpt = JsWorkReport.createWorkReport(pl.data, ptz);

				for (JsWorkReport.Attachment jsatt : pl.data.attachments) {
					UploadedFile upFile = getUploadedFileOrThrow(jsatt._uplId);
					WorkReportAttachmentWithStream att = new WorkReportAttachmentWithStream(upFile.getFile());
					att.setWorkReportAttachmentId(jsatt.id);
					att.setFileName(upFile.getFilename());
					att.setSize(upFile.getSize());
					att.setMediaType(upFile.getMediaType());
					wrkRpt.getAttachments().add(att);
				}
				
				String wrId = manager.addWorkReport(wrkRpt);
				wrkRpt = manager.getWorkReport(wrId);
				
				Integer eventId = createOrUpdateWorkReportEventIntoWorkReportCalendar(wrkRpt);
				wrkRpt.setEventId(eventId);
				
				manager.updateWorkReport(wrkRpt);
				
				new JsonResult().printTo(out);

			} else if (crud.equals(Crud.UPDATE)) {

				Payload<MapItem, JsWorkReport> pl = ServletUtils.getPayload(request, JsWorkReport.class);

				WorkReport wrkRpt = JsWorkReport.createWorkReport(pl.data, ptz);

				for (JsWorkReport.Attachment jsatt : pl.data.attachments) {
					if(!StringUtils.isBlank(jsatt._uplId)){
						UploadedFile upFile = getUploadedFileOrThrow(jsatt._uplId);
						WorkReportAttachmentWithStream att = new WorkReportAttachmentWithStream(upFile.getFile());
						att.setWorkReportAttachmentId(jsatt.id);
						att.setFileName(upFile.getFilename());
						att.setSize(upFile.getSize());
						att.setMediaType(upFile.getMediaType());
						wrkRpt.getAttachments().add(att);
					}else{
						WorkReportAttachment att = new WorkReportAttachment();
						att.setWorkReportAttachmentId(jsatt.id);
						att.setFileName(jsatt.name);
						att.setSize(jsatt.size);
						wrkRpt.getAttachments().add(att);
					}
				}

				Integer eventId = createOrUpdateWorkReportEventIntoWorkReportCalendar(wrkRpt);
				wrkRpt.setEventId(eventId);
				
				manager.updateWorkReport(wrkRpt);

				new JsonResult().printTo(out);

			} else if (crud.equals(Crud.DELETE)) {

				StringArray ids = ServletUtils.getObjectParameter(request, "reportIds", StringArray.class, true);

				WorkReport wrkRpt = manager.getWorkReport(ids.get(0));
				
				deleteWorkReportEvent(wrkRpt);
				
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
			if (crud.equals(Crud.READ)) {

				Integer id = ServletUtils.getIntParameter(request, "id", true);

				LeaveRequest lr = manager.getLeaveRequest(id);

				item = new JsLeaveRequest(lr);

				new JsonResult(item).printTo(out);

			} else if (crud.equals(Crud.CREATE)) {

				Payload<MapItem, JsLeaveRequest> pl = ServletUtils.getPayload(request, JsLeaveRequest.class);

				LeaveRequest lr = JsLeaveRequest.createLeaveRequest(pl.data);

				for (JsLeaveRequest.Document jsdoc : pl.data.documents) {
					UploadedFile upFile = getUploadedFileOrThrow(jsdoc._uplId);
					LeaveRequestDocumentWithStream doc = new LeaveRequestDocumentWithStream(upFile.getFile());
					doc.setLeaveRequestDocumentId(jsdoc.id);
					doc.setFileName(upFile.getFilename());
					doc.setSize(upFile.getSize());
					doc.setMediaType(upFile.getMediaType());
					lr.getDocuments().add(doc);
				}

				Integer eventId = createOrUpdateLeaveRequestEventIntoLeaveRequestCalendar(lr);
				lr.setEventId(eventId);
				
				manager.addLeaveRequest(lr, ss.getMedicalVisitsAutomaticallyApproved());

				new JsonResult().printTo(out);

			} else if (crud.equals(Crud.UPDATE)) {

				Payload<MapItem, JsLeaveRequest> pl = ServletUtils.getPayload(request, JsLeaveRequest.class);

				LeaveRequest lr = JsLeaveRequest.createLeaveRequest(pl.data);

				for (JsLeaveRequest.Document jsdoc : pl.data.documents) {
					if(!StringUtils.isBlank(jsdoc._uplId)){
						UploadedFile upFile = getUploadedFileOrThrow(jsdoc._uplId);
						LeaveRequestDocumentWithStream doc = new LeaveRequestDocumentWithStream(upFile.getFile());
						doc.setLeaveRequestDocumentId(jsdoc.id);
						doc.setFileName(upFile.getFilename());
						doc.setSize(upFile.getSize());
						doc.setMediaType(upFile.getMediaType());
						lr.getDocuments().add(doc);
					}else{
						LeaveRequestDocument doc = new LeaveRequestDocument();
						doc.setLeaveRequestDocumentId(jsdoc.id);
						doc.setFileName(jsdoc.name);
						doc.setSize(jsdoc.size);
						lr.getDocuments().add(doc);
					}
				}
				
				Integer eventId = createOrUpdateLeaveRequestEventIntoLeaveRequestCalendar(lr);
				lr.setEventId(eventId);

				manager.updateLeaveRequest(lr, false);

				new JsonResult().printTo(out);

			} else if (crud.equals(Crud.DELETE)) {

				IntegerArray ids = ServletUtils.getObjectParameter(request, "leaveRequestIds", IntegerArray.class, true);
				
				LeaveRequest lr = manager.getLeaveRequest(ids.get(0));
				
				deleteLeaveRequestEvent(lr);
				
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
			
			Integer eventId = createOrUpdateLeaveRequestEventIntoLeaveRequestCalendar(lr);
			lr.setEventId(eventId);
			
			manager.updateLeaveRequest(lr, true);

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
			
			LeaveRequest lr = manager.getLeaveRequest(ids.get(0));
			
			Integer eventId = createOrUpdateLeaveRequestEventIntoLeaveRequestCalendar(lr);
			lr.setEventId(eventId);

			new JsonResult().printTo(out);
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ManageCancellationLeaveRequest", ex);
		}
	}
	
	public void processDownloadOpportunityDocument(HttpServletRequest request, HttpServletResponse response) {

		try {
			boolean inline = ServletUtils.getBooleanParameter(request, "inline", false);
			String attachmentId = ServletUtils.getStringParameter(request, "attachmentId", null);
			
			if (!StringUtils.isBlank(attachmentId)) {
				Integer oId = ServletUtils.getIntParameter(request, "oId", true);
				
				OpportunityDocumentWithBytes docData = manager.getOpportunityDocument(oId, attachmentId);
				InputStream is = null;
				try {
					is = new ByteArrayInputStream(docData.getBytes());
					ServletUtils.writeFileResponse(response, inline, docData.getFileName(), null, docData.getSize(), is);
				} finally {
					IOUtils.closeQuietly(is);
				}
			} else {
				String uploadId = ServletUtils.getStringParameter(request, "uploadId", true);
				
				UploadedFile uplFile = getUploadedFileOrThrow(uploadId);
				InputStream is = null;
				try {
					is = new FileInputStream(uplFile.getFile());
					ServletUtils.writeFileResponse(response, inline, uplFile.getFilename(), null, uplFile.getSize(), is);
				} finally {
					IOUtils.closeQuietly(is);
				}
			}
			
		} catch(Throwable t) {
			logger.error("Error in DownloadOpportunityDocument", t);
			ServletUtils.writeErrorHandlingJs(response, t.getMessage());
		}
	}
	
	public void processDownloadOpportunityActionDocument(HttpServletRequest request, HttpServletResponse response) {

		try {
			boolean inline = ServletUtils.getBooleanParameter(request, "inline", false);
			String attachmentId = ServletUtils.getStringParameter(request, "attachmentId", null);
			
			if (!StringUtils.isBlank(attachmentId)) {
				Integer oAId = ServletUtils.getIntParameter(request, "oAId", true);
				
				OpportunityActionDocumentWithBytes docData = manager.getOpportunityActionDocument(oAId, attachmentId);
				InputStream is = null;
				try {
					is = new ByteArrayInputStream(docData.getBytes());
					ServletUtils.writeFileResponse(response, inline, docData.getFileName(), null, docData.getSize(), is);
				} finally {
					IOUtils.closeQuietly(is);
				}
			} else {
				String uploadId = ServletUtils.getStringParameter(request, "uploadId", true);
				
				UploadedFile uplFile = getUploadedFileOrThrow(uploadId);
				InputStream is = null;
				try {
					is = new FileInputStream(uplFile.getFile());
					ServletUtils.writeFileResponse(response, inline, uplFile.getFilename(), null, uplFile.getSize(), is);
				} finally {
					IOUtils.closeQuietly(is);
				}
			}
			
		} catch(Throwable t) {
			logger.error("Error in DownloadOpportunityActionDocument", t);
			ServletUtils.writeErrorHandlingJs(response, t.getMessage());
		}
	}
	
	public void processDownloadExpenseNoteDocument(HttpServletRequest request, HttpServletResponse response) {

		try {
			boolean inline = ServletUtils.getBooleanParameter(request, "inline", false);
			String attachmentId = ServletUtils.getStringParameter(request, "attachmentId", null);
			
			if (!StringUtils.isBlank(attachmentId)) {
				Integer eNId = ServletUtils.getIntParameter(request, "eNId", true);
				
				ExpenseNoteDocumentWithBytes docData = manager.getExpenseNoteDocument(eNId, attachmentId);
				InputStream is = null;
				try {
					is = new ByteArrayInputStream(docData.getBytes());
					ServletUtils.writeFileResponse(response, inline, docData.getFileName(), null, docData.getSize(), is);
				} finally {
					IOUtils.closeQuietly(is);
				}
			} else {
				String uploadId = ServletUtils.getStringParameter(request, "uploadId", true);
				
				UploadedFile uplFile = getUploadedFileOrThrow(uploadId);
				InputStream is = null;
				try {
					is = new FileInputStream(uplFile.getFile());
					ServletUtils.writeFileResponse(response, inline, uplFile.getFilename(), null, uplFile.getSize(), is);
				} finally {
					IOUtils.closeQuietly(is);
				}
			}
			
		} catch(Throwable t) {
			logger.error("Error in DownloadExpenseNoteDocument", t);
			ServletUtils.writeErrorHandlingJs(response, t.getMessage());
		}
	}
	
	public void processDownloadExpenseNoteDetailDocument(HttpServletRequest request, HttpServletResponse response) {

		try {
			boolean inline = ServletUtils.getBooleanParameter(request, "inline", false);
			String attachmentId = ServletUtils.getStringParameter(request, "attachmentId", null);
			
			if (!StringUtils.isBlank(attachmentId)) {
				Integer eNDId = ServletUtils.getIntParameter(request, "eNDId", true);
				
				ExpenseNoteDetailDocumentWithBytes docData = manager.getExpenseNoteDetailDocument(eNDId, attachmentId);
				InputStream is = null;
				try {
					is = new ByteArrayInputStream(docData.getBytes());
					ServletUtils.writeFileResponse(response, inline, docData.getFileName(), null, docData.getSize(), is);
				} finally {
					IOUtils.closeQuietly(is);
				}
			} else {
				String uploadId = ServletUtils.getStringParameter(request, "uploadId", true);
				
				UploadedFile uplFile = getUploadedFileOrThrow(uploadId);
				InputStream is = null;
				try {
					is = new FileInputStream(uplFile.getFile());
					ServletUtils.writeFileResponse(response, inline, uplFile.getFilename(), null, uplFile.getSize(), is);
				} finally {
					IOUtils.closeQuietly(is);
				}
			}
			
		} catch(Throwable t) {
			logger.error("Error in DownloadExpenseNoteDetailDocument", t);
			ServletUtils.writeErrorHandlingJs(response, t.getMessage());
		}
	}

	public void processDownloadWorkReportAttachment(HttpServletRequest request, HttpServletResponse response) {

		try {
			boolean inline = ServletUtils.getBooleanParameter(request, "inline", false);
			String attachmentId = ServletUtils.getStringParameter(request, "attachmentId", null);
			
			if (!StringUtils.isBlank(attachmentId)) {
				String wrId = ServletUtils.getStringParameter(request, "wrId", true);
				
				WorkReportAttachmentWithBytes attData = manager.getWorkReportAttachment(wrId, attachmentId);
				InputStream is = null;
				try {
					is = new ByteArrayInputStream(attData.getBytes());
					ServletUtils.writeFileResponse(response, inline, attData.getFileName(), null, attData.getSize(), is);
				} finally {
					IOUtils.closeQuietly(is);
				}
			} else {
				String uploadId = ServletUtils.getStringParameter(request, "uploadId", true);
				
				UploadedFile uplFile = getUploadedFileOrThrow(uploadId);
				InputStream is = null;
				try {
					is = new FileInputStream(uplFile.getFile());
					ServletUtils.writeFileResponse(response, inline, uplFile.getFilename(), null, uplFile.getSize(), is);
				} finally {
					IOUtils.closeQuietly(is);
				}
			}
			
		} catch(Throwable t) {
			logger.error("Error in DownloadWorkReportAttachment", t);
			ServletUtils.writeErrorHandlingJs(response, t.getMessage());
		}
	}
	
	public void processDownloadTimetableRequestDocument(HttpServletRequest request, HttpServletResponse response) {

		try {
			boolean inline = ServletUtils.getBooleanParameter(request, "inline", false);
			String attachmentId = ServletUtils.getStringParameter(request, "attachmentId", null);
			
			if (!StringUtils.isBlank(attachmentId)) {
				Integer trId = ServletUtils.getIntParameter(request, "trId", true);
				
				LeaveRequestDocumentWithBytes docData = manager.getLeaveRequestDocument(trId, attachmentId);
				InputStream is = null;
				try {
					is = new ByteArrayInputStream(docData.getBytes());
					ServletUtils.writeFileResponse(response, inline, docData.getFileName(), null, docData.getSize(), is);
				} finally {
					IOUtils.closeQuietly(is);
				}
			} else {
				String uploadId = ServletUtils.getStringParameter(request, "uploadId", true);
				
				UploadedFile uplFile = getUploadedFileOrThrow(uploadId);
				InputStream is = null;
				try {
					is = new FileInputStream(uplFile.getFile());
					ServletUtils.writeFileResponse(response, inline, uplFile.getFilename(), null, uplFile.getSize(), is);
				} finally {
					IOUtils.closeQuietly(is);
				}
			}
			
		} catch(Throwable t) {
			logger.error("Error in DownloadTimetableRequestDocument", t);
			ServletUtils.writeErrorHandlingJs(response, t.getMessage());
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
					
					item.defaultStatus = ss.getOpportunityDefaultDocStatusId();
					item.title = ss.getOpportunityGeneralTitle();
					item.enablePrint = ss.getOpportunityGeneralEnablePrint();
				} else {
					item = new JsOpportunitySetting(new OpportunitySetting());
				}

				new JsonResult(item).printTo(out);

			} else if (crud.equals(Crud.UPDATE)) {

				Payload<MapItem, JsOpportunitySetting> pl = ServletUtils.getPayload(request, JsOpportunitySetting.class);

				OpportunitySetting setting = JsOpportunitySetting.createOpportunitySetting(pl.data);
				
				if (pl.map.has("defaultStatus")) {
					ss.setOpportunityDefaultDocStatusId(pl.data.defaultStatus);
				}
				if (pl.map.has("title")) {
					ss.setOpportunityGeneralTitle(pl.data.title);
				}
				if (pl.map.has("enablePrint")) {
					ss.setOpportunityGeneralEnablePrint(pl.data.enablePrint);
				}
				
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
					item.defaultStatus = ss.getWorkReportDefaultDocStatusId();
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
					ss.setWorkReportDefaultDocStatusId(pl.data.defaultStatus);
				}

				manager.updateWorkReportSetting(wrkSetting);

				new JsonResult().printTo(out);

			}
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ManageWorkReportSetting", ex);
		}
	}
	
	public void processManageExpenseNoteSetting(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		JsExpenseNoteSetting item = null;
		try {
			String crud = ServletUtils.getStringParameter(request, "crud", true);

			if (crud.equals(Crud.READ)) {
				ExpenseNoteSetting enSetting = manager.getExpenseNoteSetting();

				if (enSetting != null) {
					item = new JsExpenseNoteSetting(enSetting);
				} else {
					item = new JsExpenseNoteSetting(new ExpenseNoteSetting());
				}

				new JsonResult(item).printTo(out);

			} else if (crud.equals(Crud.UPDATE)) {
				Payload<MapItem, JsExpenseNoteSetting> pl = ServletUtils.getPayload(request, JsExpenseNoteSetting.class);

				ExpenseNoteSetting enSetting = JsExpenseNoteSetting.createExpenseNoteSetting(pl.data);

				manager.updateExpenseNoteSetting(enSetting);

				new JsonResult().printTo(out);

			}
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ManageExpenseNoteSetting", ex);
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

				if (pl.map.has("medicalVisitsAutomaticallyApproved")) {
					ss.setMedicalVisitsAutomaticallyApproved(pl.data.medicalVisitsAutomaticallyApproved);
				}
				
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
	
	public void processPrintExpenseNote(HttpServletRequest request, HttpServletResponse response) {
		ArrayList<RBExpenseNote> itemsEn = new ArrayList<>();
		
		try {
			
			String filename = ServletUtils.getStringParameter(request, "filename", "print");
			IntegerArray ids = ServletUtils.getObjectParameter(request, "ids", IntegerArray.class, true);
			
			ExpenseNote en = null;
			CompanyPicture picture = null;
			Company company = null;
			
			for(Integer id : ids) {
				picture = null;
				en = manager.getExpenseNote(id);
				company = manager.getCompany(en.getCompanyId());
				if(company.getHasPicture()) picture = manager.getCompanyPicture(company.getCompanyId());
				itemsEn.add(new RBExpenseNote(WT.getCoreManager(), manager, en, ss, picture));
			}
			
			ReportConfig.Builder builder = reportConfigBuilder();
			RptExpenseNote rpt = new RptExpenseNote(builder.build());
			rpt.setDataSource(itemsEn);
			
			filename = "expense_note"
					+ ((null != en.getId()) ? "_" + en.getId() : "");
			
			ServletUtils.setFileStreamHeaders(response, filename + ".pdf");
			WT.generateReportToStream(rpt, AbstractReport.OutputType.PDF, response.getOutputStream());
			
		} catch(Exception ex) {
			logger.error("Error in action PrintExpenseNote", ex);
			ex.printStackTrace();
			ServletUtils.writeErrorHandlingJs(response, ex.getMessage());
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
			
			filename = "report"
					+ ((null != wr.getNumber()) ? "-" + wr.getNumber() : "")
					+ ((null != wr.getYear()) ? "-" + wr.getYear() : "");
			
			ServletUtils.setFileStreamHeaders(response, filename + ".pdf");
			WT.generateReportToStream(rpt, AbstractReport.OutputType.PDF, response.getOutputStream());
			
		} catch(Exception ex) {
			logger.error("Error in action PrintWorkReport", ex);
			ex.printStackTrace();
			ServletUtils.writeErrorHandlingJs(response, ex.getMessage());
		}
	}
	
	public void processPrintWorkReportSummary(HttpServletRequest request, HttpServletResponse response) {
		ArrayList<RBWorkReportSummary> itemsWrS = new ArrayList<>();
		List<WorkReportSummary> wrss = new ArrayList<>();
		CompanyPicture picture = null;
		Image imgPicture = null;
		Company company = null;
		String filters = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			String filename = ServletUtils.getStringParameter(request, "filename", "print");
			String operatorId = ServletUtils.getStringParameter(request, "operatorId", null);
			Integer companyId = ServletUtils.getIntParameter(request, "companyId", true);
			Date from = ServletUtils.getDateParameter(request, "from", true);
			Date to = ServletUtils.getDateParameter(request, "to", true);
			Integer docStatusId = ServletUtils.getIntParameter(request, "docStatusId", null);
			
			LocalDate fromDate = new LocalDate(from);
			LocalDate toDate = new LocalDate(to);
			
			filters += (operatorId == null) 
					? "Tutti gli Utenti supervisionati da " + WT.getCoreManager().getUser(new UserProfileId(getEnv().getProfileId().getDomainId(), getEnv().getProfileId().getUserId())).getDisplayName()  
					: " Utente " + WT.getCoreManager().getUser(new UserProfileId(getEnv().getProfileId().getDomainId(), operatorId)).getDisplayName();
			filters += " - Ditta " + manager.getCompany(companyId).getName();
			filters += " - Dal " + sdf.format(from) + " al " + sdf.format(to);
			filters += (docStatusId == null) ? "" : " - Status " + manager.getDocStatus(docStatusId).getDescription();
			
			company = manager.getCompany(companyId);
			if(company.getHasPicture()) picture = manager.getCompanyPicture(company.getCompanyId());
			if(picture != null) {
				try(ByteArrayInputStream bais = new ByteArrayInputStream(picture.getBytes())) {
					imgPicture = ImageIO.read(bais);
				}
			}
			
			wrss = manager.listWorkReportSummaryByFilters(operatorId, companyId, fromDate, toDate, docStatusId);
			
			for(WorkReportSummary wrs : wrss) {
				itemsWrS.add(new RBWorkReportSummary(WT.getCoreManager(), manager, wrs));
			}
			
			ReportConfig.Builder builder = reportConfigBuilder();
			RptWorkReportSummary rpt = new RptWorkReportSummary(builder.build());
			rpt.setDataSource(itemsWrS);
			rpt.getParameters().put("FILTERS_DESCRIPTION", filters);
			rpt.getParameters().put("COMPANY_PICTURE", imgPicture);
			
			ServletUtils.setFileStreamHeaders(response, filename + ".pdf");
			WT.generateReportToStream(rpt, AbstractReport.OutputType.PDF, response.getOutputStream());
			
		} catch(Exception ex) {
			logger.error("Error in action PrintWorkReportSummary", ex);
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
	
	public void processPrintTimetableEncoReport(HttpServletRequest request, HttpServletResponse response) {
		ArrayList<RBTimetableEncoReport> items = new ArrayList<>();
		List<OTimetableReport> trs = null;
		
		try {
			String filename = ServletUtils.getStringParameter(request, "filename", "print");
			
			trs = manager.getTimetableReport();
			
			for(OTimetableReport otr : trs) {
				items.add(new RBTimetableEncoReport(WT.getCoreManager(), manager, otr));
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
	
	public void processExportTimetableSummaryExcel(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		try{
			ArrayList<ODay> days = null;
			String FILE_NAME = "/temp/PROGRAMM FERIE E PERMESSI.xlsx";
			
			String query = ServletUtils.getStringParameter(request, "query", null);
			TimetableSummaryExcelQuery tQuery = TimetableSummaryExcelQuery.fromJson(query);
			
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Recuperati_Foglio1");
			
			//Font
			XSSFFont font = workbook.createFont();
			font.setFontHeightInPoints((short) 8);
			font.setFontName("Arial");
			font.setItalic(false);
			font.setColor(HSSFColor.BLACK.index);
			
			//Dates & Days name
			days = manager.generateDaysFromRange(tQuery.fromDate, tQuery.toDate);
			
			//Style
			XSSFCellStyle styleDayName = workbook.createCellStyle();
			styleDayName.setRotation((short)90);
			styleDayName.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			styleDayName.setVerticalAlignment(VerticalAlignment.CENTER);
			styleDayName.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			styleDayName.setFont(font);
			
			XSSFCellStyle styleDate = workbook.createCellStyle();
			styleDate.setRotation((short)90);
			styleDate.setVerticalAlignment(VerticalAlignment.CENTER);
			styleDate.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			styleDate.setFont(font);
			
			XSSFCellStyle styleDateWeekend = workbook.createCellStyle();
			styleDateWeekend.setRotation((short)90);
			styleDateWeekend.setVerticalAlignment(VerticalAlignment.CENTER);
			styleDateWeekend.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			styleDateWeekend.setFont(font);
			styleDateWeekend.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());  
			styleDateWeekend.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			
			XSSFCellStyle styleDip = workbook.createCellStyle();
			styleDateWeekend.setFont(font);		

			int rowNum = 0;
			int cellNum = 1;
			
			Row row = sheet.createRow(rowNum++);
			row.setHeightInPoints((short)108);
			
			for(ODay d : days){
				String dayName = lookupResource("timetableSummaryExcel.day." + d.getDayName().trim());
				Cell cell = row.createCell(cellNum++);
				cell.setCellValue((String) dayName);
				
				if("Saturday".equals(d.getDayName().trim()) || "Sunday".equals(d.getDayName().trim())){
					sheet.setColumnWidth(cellNum - 1, 1 * 256);
				}else{
					sheet.setColumnWidth(cellNum - 1, 5 * 256);
				}
				
				cell.setCellStyle(styleDayName);
			}
			
			row = sheet.createRow(rowNum++);
			row.setHeightInPoints((short)80);

			
			Cell dipCell = row.createCell(0);
			dipCell.setCellValue((String) "DIP");
			dipCell.setCellStyle(styleDip);
			
			cellNum = 1;
			
			for(ODay d : days){
				String month = lookupResource("timetableSummaryExcel.month." + d.getDate().getMonthOfYear());
				Cell cell = row.createCell(cellNum++);
				cell.setCellValue((String) (d.getDate().getDayOfMonth() + "-" + month + "-" + d.getDate().getYear()));
				
				if("Saturday".equals(d.getDayName().trim()) || "Sunday".equals(d.getDayName().trim())){
					sheet.setColumnWidth(cellNum - 1, 1 * 256);
					cell.setCellStyle(styleDateWeekend);
				}else{
					sheet.setColumnWidth(cellNum - 1, 5 * 256);
					cell.setCellStyle(styleDate);
				}
			}
			
			//Users and Holidays			
			
			try {
				FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
				workbook.write(outputStream);
				workbook.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			new JsonResult(true).printTo(out);
		} catch (Exception ex) {
			new JsonResult(ex).printTo(out);
			logger.error("Error in action ExportSummaryExcel", ex);
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
						if(o.getExecutedWith() != null){
							Data uD =  WT.getUserData(new UserProfileId(o.getDomainId(), o.getExecutedWith()));
							if(uD != null)
								additionalInfo += field.getLabel() + ": " + uD.getDisplayName() + " / ";
						}
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
						if(o.getSignedBy() != null){
							Data uD = WT.getUserData(new UserProfileId(o.getDomainId(), o.getSignedBy()));
							if(uD != null)
								additionalInfo +=  field.getLabel() + ": " + uD.getDisplayName() + " / ";
						}
						break;
					case "success":
						if(o.getSuccess()!= null)
							additionalInfo += field.getLabel() + ": " +  ((o.getSuccess()) ? lookupResource("grpOpportunity.fld.yes") : lookupResource("grpOpportunity.fld.no")) + " / ";
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
	
	private Integer createOrUpdateWorkReportEventIntoWorkReportCalendar(WorkReport wrkRpt) throws WTException {
		ICalendarManager cm = (ICalendarManager)WT.getServiceManager("com.sonicle.webtop.calendar", true, getEnv().getProfileId());
		Integer eventId = null;
		Event ev = null;
		
		if (cm != null) {
			Integer wrCalId = us.getWorkReportCalendarId();
			
			if(wrCalId == null || !cm.existCalendar(wrCalId)){
				wrCalId = createWorkReportCalendar(cm, wrkRpt);
				us.setWorkReportCalendarId(wrCalId);
			}
			
			if(wrkRpt.getEventId() != null){
				ev = cm.getEvent(wrkRpt.getEventId());
				
				if(ev != null){
					eventId = updateWorkReportEvent(cm, wrkRpt, ev);
				}else{
					eventId = createWorkReportEvent(cm, wrkRpt, wrCalId);
				}
			}else{
				eventId = createWorkReportEvent(cm, wrkRpt, wrCalId);
			}			
		}
		
		return eventId;
	}
	
	private Integer createWorkReportCalendar(ICalendarManager cm, WorkReport wrkRpt) throws WTException{
		Calendar cal = new Calendar();
		cal.setName(lookupResource("workreport.calendar.name"));
		cal.setColor("#FFAD46");
		cal.setProfileId(new UserProfileId(getEnv().getProfileId().getDomainId(), wrkRpt.getOperatorId()));
		
		cal = cm.addCalendar(cal);
		
		return cal.getCalendarId();
	}
	
	private int createWorkReportEvent(ICalendarManager cm, WorkReport wrkRpt, int wrCalId) throws WTException{
		DateTimeZone tz = getEnv().getProfile().getTimeZone();
		Event ev = new Event();
		String title = "";

		ev.setCalendarId(wrCalId);
		ev.setAllDay(true);
		ev.setTimezone(tz.getID());
		ev.setIsPrivate(true);
		ev.setBusy(false);

		if(wrkRpt.getFromDate() != null) 
			ev.setStartDate(wrkRpt.getFromDate().toDateTimeAtStartOfDay(tz));
		if(wrkRpt.getToDate() != null)
			ev.setEndDate(wrkRpt.getToDate().toDateTimeAtStartOfDay(tz));
		if(wrkRpt.getCausalId() != null)
			ev.setCausalId(wrkRpt.getCausalId());
		if(wrkRpt.getDescription() != null)
			ev.setDescription(wrkRpt.getDescription());
		if(wrkRpt.getReferenceNo()!= null)
			ev.setTitle(wrkRpt.getReferenceNo());
		if(wrkRpt.getCustomerId() != null)
			ev.setMasterDataId(wrkRpt.getCustomerId());
		if(wrkRpt.getCustomerStatId()!= null)
			ev.setStatMasterDataId(wrkRpt.getCustomerStatId());
		if(wrkRpt.getNumber() != null)
			title += wrkRpt.getNumber() + " ";
		if(wrkRpt.getYear()!= null)
			title += wrkRpt.getYear()+ " ";
		if(wrkRpt.getReferenceNo()!= null)
			title += "/ " + wrkRpt.getReferenceNo()+ " ";
		ev.setTitle(title);

		ev = cm.addEvent(ev, false);
		
		return ev.getEventId();
	}
	
	private int updateWorkReportEvent(ICalendarManager cm, WorkReport wrkRpt, Event ev) throws WTException{
		EventInstance evI = new EventInstance(EventKey.buildKey(ev.getEventId(), null), ev);
		DateTimeZone tz = getEnv().getProfile().getTimeZone();
		String title = "";
		
		if(wrkRpt.getFromDate() != null) 
			evI.setStartDate(wrkRpt.getFromDate().toDateTimeAtStartOfDay(tz));
		if(wrkRpt.getToDate() != null)
			evI.setEndDate(wrkRpt.getToDate().toDateTimeAtStartOfDay(tz));
		if(wrkRpt.getCausalId() != null)
			evI.setCausalId(wrkRpt.getCausalId());
		if(wrkRpt.getDescription() != null)
			evI.setDescription(wrkRpt.getDescription());
		if(wrkRpt.getReferenceNo()!= null)
			evI.setTitle(wrkRpt.getReferenceNo());
		if(wrkRpt.getCustomerId() != null)
			evI.setMasterDataId(wrkRpt.getCustomerId());
		if(wrkRpt.getCustomerStatId()!= null)
			evI.setStatMasterDataId(wrkRpt.getCustomerStatId());
		if(wrkRpt.getNumber() != null)
			title += wrkRpt.getNumber() + " ";
		if(wrkRpt.getYear()!= null)
			title += wrkRpt.getYear()+ " ";
		if(wrkRpt.getReferenceNo()!= null)
			title += "/ " + wrkRpt.getReferenceNo()+ " ";
		evI.setTitle(title);

		cm.updateEventInstance(UpdateEventTarget.ALL_SERIES, evI, false, false, false);
		
		return evI.getEventId();
	}
	
	private void deleteWorkReportEvent(WorkReport wrkRpt) throws WTException{		
		ICalendarManager cm = (ICalendarManager)WT.getServiceManager("com.sonicle.webtop.calendar", true, getEnv().getProfileId());
		
		if (cm != null) {
			if(wrkRpt.getEventId() != null){
				Event ev = cm.getEvent(wrkRpt.getEventId());
				if(ev != null)
					cm.deleteEventInstance(UpdateEventTarget.ALL_SERIES, EventKey.buildKey(wrkRpt.getEventId(), null), false);
			}
		}
	}
	
	private Integer createOrUpdateOpportunityEventIntoOpportunityCalendar(Opportunity o) throws WTException {
		ICalendarManager cm = (ICalendarManager)WT.getServiceManager("com.sonicle.webtop.calendar", true, getEnv().getProfileId());
		Integer eventId = null;
		Event ev = null;
		
		if (cm != null) {
			Integer oCalId = us.getOpportunityCalendarId();
			
			if(oCalId == null || !cm.existCalendar(oCalId)){
				oCalId = createOpportunityCalendar(cm, o);
				us.setOpportunityCalendarId(oCalId);
			}
			
			if(o.getEventId() != null){
				ev = cm.getEvent(o.getEventId());
				
				if(ev != null){
					eventId = updateOpportunityEvent(cm, o, ev);
				}else{
					eventId = createOpportunityEvent(cm, o, oCalId);
				}
			}else{
				eventId = createOpportunityEvent(cm, o, oCalId);
			}
		}
		
		return eventId;
	}
	
	private Integer createOpportunityCalendar(ICalendarManager cm, Opportunity o) throws WTException{
		Calendar cal = new Calendar();
		cal.setName(lookupResource("opportunity.calendar.name"));
		cal.setColor("#9FC6E7");
		cal.setProfileId(new UserProfileId(getEnv().getProfileId().getDomainId(), o.getOperatorId()));
		
		cal = cm.addCalendar(cal);
		
		return cal.getCalendarId();
	}
	
	private int createOpportunityEvent(ICalendarManager cm, Opportunity o, int oCalId) throws WTException{
		DateTimeZone tz = getEnv().getProfile().getTimeZone();
		Event ev = new Event();

		ev.setCalendarId(oCalId);
		ev.setTimezone(tz.getID());
		ev.setIsPrivate(true);
		ev.setBusy(false);
		
		if(o.getStartDate() != null){			
				ev.setAllDay(true);
				
				ev.setStartDate(o.getStartDate());
				ev.setEndDate(o.getEndDate());
		}
		if(o.getPlace() != null)
			ev.setLocation(o.getPlace());
		if(o.getCausalId() != null)
			ev.setCausalId(o.getCausalId());
		if(o.getObjective() != null)
			ev.setDescription(o.getObjective());
		if(o.getDescription() != null)
			ev.setTitle(o.getDescription());
		if(o.getCustomerId() != null)
			ev.setMasterDataId(o.getCustomerId());
		if(o.getCustomerStatId() != null)
			ev.setStatMasterDataId(o.getCustomerStatId());
		if(o.getActivityId() != null)
			ev.setActivityId(o.getActivityId());

		ev = cm.addEvent(ev, false);
		
		return ev.getEventId();
	}
	
	private int updateOpportunityEvent(ICalendarManager cm, Opportunity o, Event ev) throws WTException{
		EventInstance evI = new EventInstance(EventKey.buildKey(ev.getEventId(), null), ev);
		DateTimeZone tz = getEnv().getProfile().getTimeZone();

		evI.setTimezone(tz.getID());
		
		if(o.getStartDate() != null){			
				evI.setAllDay(true);
				
				evI.setStartDate(o.getStartDate());
				evI.setEndDate(o.getEndDate());
		}
		if(o.getPlace() != null)
			evI.setLocation(o.getPlace());
		if(o.getCausalId() != null)
			evI.setCausalId(o.getCausalId());
		if(o.getObjective() != null)
			evI.setDescription(o.getObjective());
		if(o.getDescription() != null)
			evI.setTitle(o.getDescription());
		if(o.getCustomerId() != null)
			evI.setMasterDataId(o.getCustomerId());
		if(o.getCustomerStatId() != null)
			evI.setStatMasterDataId(o.getCustomerStatId());
		if(o.getActivityId() != null)
			evI.setActivityId(o.getActivityId());

		cm.updateEventInstance(UpdateEventTarget.ALL_SERIES, evI, false, false, false);
		
		return evI.getEventId();
	}
	
	private void deleteOpportunityEvent(Opportunity o) throws WTException{		
		ICalendarManager cm = (ICalendarManager)WT.getServiceManager("com.sonicle.webtop.calendar", true, getEnv().getProfileId());
		
		if (cm != null) {
			if(o.getEventId() != null){
				Event ev = cm.getEvent(o.getEventId());
				if(ev != null)
					cm.deleteEventInstance(UpdateEventTarget.ALL_SERIES, EventKey.buildKey(o.getEventId(), null), false);
			}
		}
	}
	
	private Integer createOrUpdateOpportunityActionEventIntoOpportunityCalendar(OpportunityAction oAct) throws WTException {
		ICalendarManager cm = (ICalendarManager)WT.getServiceManager("com.sonicle.webtop.calendar", true, getEnv().getProfileId());
		Integer eventId = null;
		Event ev = null;
		
		Opportunity o = manager.getOpportunity(oAct.getOpportunityId());
		
		if (cm != null) {
			Integer oCalId = us.getOpportunityCalendarId();
			
			if(oCalId == null || !cm.existCalendar(oCalId)){
				oCalId = createOpportunityCalendar(cm, o);
				us.setOpportunityCalendarId(oCalId);
			}
			
			if(oAct.getEventId() != null){
				ev = cm.getEvent(oAct.getEventId());
				
				if(ev != null){
					eventId = updateOpportunityActionEvent(cm, oAct, o, ev);
				}else{
					eventId = createOpportunityActionEvent(cm, oAct, o, oCalId);
				}
			}else{
				eventId = createOpportunityActionEvent(cm, oAct, o, oCalId);
			}
		}
		
		return eventId;
	}
	
	private int createOpportunityActionEvent(ICalendarManager cm, OpportunityAction oAct, Opportunity o, int oCalId) throws WTException{
		DateTimeZone tz = getEnv().getProfile().getTimeZone();
		Event ev = new Event();
		String title = "";
		
		ev.setCalendarId(oCalId);
		ev.setTimezone(tz.getID());
		ev.setIsPrivate(true);
		ev.setBusy(false);
		
		if(oAct.getStartDate() != null){			
				ev.setAllDay(true);
				
				ev.setStartDate(oAct.getStartDate());
				ev.setEndDate(oAct.getEndDate());
		}
		if(o.getDescription() != null)
			title += o.getDescription() + " > ";
		if(oAct.getActivityId() != null)
			title += "[" + WT.getCoreManager().getActivity(oAct.getActivityId()).getDescription() + "] ";
		if(oAct.getDescription() != null)
			title += oAct.getDescription();
		if(oAct.getPlace() != null)
			ev.setLocation(oAct.getPlace());
		if(oAct.getSubsequentActions() != null)
			ev.setDescription(oAct.getSubsequentActions());
		if(oAct.getActivityId() != null)
			ev.setActivityId(oAct.getActivityId());

		ev.setTitle(title);
		
		ev = cm.addEvent(ev, false);
		
		return ev.getEventId();
	}
	
	private int updateOpportunityActionEvent(ICalendarManager cm, OpportunityAction oAct, Opportunity o, Event ev) throws WTException{
		EventInstance evI = new EventInstance(EventKey.buildKey(ev.getEventId(), null), ev);
		DateTimeZone tz = getEnv().getProfile().getTimeZone();
		String title = "";

		evI.setTimezone(tz.getID());
		
		if(oAct.getStartDate() != null){			
				evI.setAllDay(true);
				
				evI.setStartDate(oAct.getStartDate());
				evI.setEndDate(oAct.getEndDate());
		}
		if(o.getDescription() != null)
			title += o.getDescription() + " > ";
		if(oAct.getActivityId() != null)
			title += "[" + WT.getCoreManager().getActivity(oAct.getActivityId()).getDescription() + "] ";
		if(oAct.getDescription() != null)
			title += oAct.getDescription();
		if(oAct.getPlace() != null)
			evI.setLocation(oAct.getPlace());
		if(oAct.getSubsequentActions() != null)
			evI.setDescription(oAct.getSubsequentActions());
		if(oAct.getActivityId() != null)
			evI.setActivityId(oAct.getActivityId());

		evI.setTitle(title);

		cm.updateEventInstance(UpdateEventTarget.ALL_SERIES, evI, false, false, false);
		
		return evI.getEventId();
	}
	
	private void updateOpportunityActionEventTitle(OpportunityAction oAct, Opportunity o) throws WTException{
		ICalendarManager cm = (ICalendarManager)WT.getServiceManager("com.sonicle.webtop.calendar", true, getEnv().getProfileId());
		String title = "";
		Event ev = null;

		if (cm != null) {
			if(o.getDescription() != null)
				title += o.getDescription() + " > ";
			if(oAct.getDescription() != null)
				title += oAct.getDescription();
			
			if(oAct.getEventId() != null){
				ev = cm.getEvent(oAct.getEventId());
				
				if(ev != null){
					EventInstance evI = new EventInstance(EventKey.buildKey(ev.getEventId(), null), ev);
					evI.setTitle(title);
					cm.updateEventInstance(UpdateEventTarget.ALL_SERIES, evI, false, false, false);
				}
			}
		}
	}
	
	private void deleteOpportunityActionEvent(OpportunityAction oAct) throws WTException{		
		ICalendarManager cm = (ICalendarManager)WT.getServiceManager("com.sonicle.webtop.calendar", true, getEnv().getProfileId());
		
		if (cm != null) {
			if(oAct.getEventId() != null){
				Event ev = cm.getEvent(oAct.getEventId());
				if(ev != null)
					cm.deleteEventInstance(UpdateEventTarget.ALL_SERIES, EventKey.buildKey(oAct.getEventId(), null), false);
			}
		}
	}
	
 	private Integer createOrUpdateLeaveRequestEventIntoLeaveRequestCalendar(final LeaveRequest lReq) throws WTException {
 		TimetableSetting ts = manager.getTimetableSetting();
 		UserProfileId upi = null;
 		Integer activityId = null; 
 		
 		if(ts != null){
 			if(ts.getCalendarUserId() != null){
 				upi = new UserProfileId(getEnv().getProfileId().getDomainId(), ts.getCalendarUserId());
 			}else{
 				upi = getEnv().getProfileId();
 			}
 			
 			activityId = ts.getDefaultEventActivityId();
 		}else{
 			upi = getEnv().getProfileId();
 		}
 		
 		return catchEventId(upi, lReq, activityId);
 		
 		/*
 		ICalendarManager cm = (ICalendarManager)WT.getServiceManager("com.sonicle.webtop.calendar", true, upi);
 		
		ICalendarManager cm = (ICalendarManager)WT.getServiceManager("com.sonicle.webtop.calendar", true, getEnv().getProfileId());
		Integer eventId = null;
		Event ev = null;
		
		if (cm != null) {
			Integer lrCalId = us.getLeaveRequestCalendarId();
			
			if(lrCalId == null || !cm.existCalendar(lrCalId)){
				lrCalId = createLeaveRequestCalendar(cm, lReq);
				us.setLeaveRequestCalendarId(lrCalId);
			}
			
			if(lReq.getEventId() != null){
				ev = cm.getEvent(lReq.getEventId());
				
				if(ev != null){
					eventId = updateLeaveRequestEvent(cm, lReq, ev);
				}else{
					eventId = createLeaveRequestEvent(cm, lReq, lrCalId);
				}
			}else{
				eventId = createLeaveRequestEvent(cm, lReq, lrCalId);
			}			
		}
		
		return eventId;*/
	}
	
	private Integer catchEventId(final UserProfileId targetPid, final LeaveRequest lReq, final Integer activityId) throws WTException {
		final ICalendarManager cm = (ICalendarManager)WT.getServiceManager("com.sonicle.webtop.calendar", true, targetPid);
		
		Integer eventId = null;
		
		if (cm != null) {
			eventId = WT.runPrivileged(new Callable<Integer>(){
				public Integer call() throws WTException {
					Integer lrCalId = us.getLeaveRequestCalendarId();
			
					if(lrCalId == null || cm.getCalendar(lrCalId) == null){
						lrCalId = createLeaveRequestCalendar(cm, lReq);

						us.setLeaveRequestCalendarId(lrCalId);
					}

					if(lReq.getEventId() != null){
						Event ev = cm.getEvent(lReq.getEventId());

						if(ev != null){
							return updateLeaveRequestEvent(cm, lReq, ev);
						}else{
							return createLeaveRequestEvent(cm, lReq, lrCalId, activityId);
						}
					}else{
						return createLeaveRequestEvent(cm, lReq, lrCalId, activityId);
					}		
				}
			});	
		}
		return eventId;
	}
	
	private Integer createLeaveRequestCalendar(ICalendarManager cm, LeaveRequest lReq) throws WTException{
		Calendar cal = new Calendar();
		cal.setName(lookupResource("leaverequest.calendar.name"));
		cal.setColor("#42D692");
		cal.setProfileId(new UserProfileId(getEnv().getProfileId().getDomainId(), lReq.getUserId()));
		
		cal = cm.addCalendar(cal);
		
		return cal.getCalendarId();
	}
	
	private int createLeaveRequestEvent(ICalendarManager cm, LeaveRequest lReq, int lrCalId, Integer activityId) throws WTException{
		DateTimeZone tz = getEnv().getProfile().getTimeZone();
		Event ev = new Event();
		String title = "";
		
		ev.setCalendarId(lrCalId);
		ev.setAllDay(true);
		ev.setTimezone(tz.getID());
		ev.setIsPrivate(true);
		ev.setBusy(false);

		ev.setActivityId(activityId);
		
		if(lReq.getFromDate() != null && lReq.getToDate() != null){			
			if(lReq.getFromHour() != null && lReq.getToHour() != null){
				ev.setAllDay(false);
				
				ev.setDatesAndTimes(false, tz.getID(), lReq.getFromDate().toDateTimeAtStartOfDay(tz).withTime(Integer.parseInt(lReq.getFromHour().split(":")[0]), Integer.parseInt(lReq.getFromHour().split(":")[1]), 0, 0), lReq.getToDate().toDateTimeAtStartOfDay(tz).withTime(Integer.parseInt(lReq.getToHour().split(":")[0]), Integer.parseInt(lReq.getToHour().split(":")[1]), 0, 0));
			}else{
				ev.setAllDay(true);
				
				ev.setStartDate(lReq.getFromDate().toDateTimeAtStartOfDay(tz));
				ev.setEndDate(lReq.getToDate().toDateTimeAtStartOfDay(tz));
			}
		}
		
		if(lReq.getType() != null) 
			title += lookupResource("leaverequest.type." + lReq.getType()) + " ";
		if(lReq.getNotes()!= null) 
			ev.setDescription(lReq.getNotes());
		if(LangUtils.value(lReq.getCancResult(), Boolean.FALSE))
			title += lookupResource("leaverequest.calendar.requesttype.D");
		else if(LangUtils.value(lReq.getCancRequest(), Boolean.FALSE))
			title += lookupResource("leaverequest.calendar.requesttype.RD");
		else if(LangUtils.value(lReq.getResult(), Boolean.FALSE))
			title += lookupResource("leaverequest.calendar.requesttype.A");
		else if(!LangUtils.value(lReq.getResult(), Boolean.TRUE))
			title += lookupResource("leaverequest.calendar.requesttype.NA");
		else
			title += lookupResource("leaverequest.calendar.requesttype.S");
		
		ev.setTitle(title);

		ev = cm.addEvent(ev, false);
		
		return ev.getEventId();
	}
	
	private int updateLeaveRequestEvent(ICalendarManager cm, LeaveRequest lReq, Event ev) throws WTException{
		EventInstance evI = new EventInstance(EventKey.buildKey(ev.getEventId(), null), ev);
		DateTimeZone tz = getEnv().getProfile().getTimeZone();
		String title = "";
		
		if(lReq.getFromDate() != null && lReq.getToDate() != null){			
			if(lReq.getFromHour() != null && lReq.getToHour() != null){
				evI.setAllDay(false);
				
				evI.setDatesAndTimes(false, tz.getID(), lReq.getFromDate().toDateTimeAtStartOfDay(tz).withTime(Integer.parseInt(lReq.getFromHour().split(":")[0]), Integer.parseInt(lReq.getFromHour().split(":")[1]), 0, 0), lReq.getToDate().toDateTimeAtStartOfDay(tz).withTime(Integer.parseInt(lReq.getToHour().split(":")[0]), Integer.parseInt(lReq.getToHour().split(":")[1]), 0, 0));
			}else{
				evI.setAllDay(true);
				
				evI.setStartDate(lReq.getFromDate().toDateTimeAtStartOfDay(tz));
				evI.setEndDate(lReq.getToDate().toDateTimeAtStartOfDay(tz));
			}
		}
		
		if(lReq.getType() != null) 
			title += lookupResource("leaverequest.type." + lReq.getType()) + " ";
		if(lReq.getNotes()!= null) 
			evI.setDescription(lReq.getNotes());
		if(LangUtils.value(lReq.getCancResult(), Boolean.FALSE))
			title += lookupResource("leaverequest.calendar.requesttype.D");
		else if(LangUtils.value(lReq.getCancRequest(), Boolean.FALSE))
			title += lookupResource("leaverequest.calendar.requesttype.RD");
		else if(LangUtils.value(lReq.getResult(), Boolean.FALSE))
			title += lookupResource("leaverequest.calendar.requesttype.A");
		else if(!LangUtils.value(lReq.getResult(), Boolean.TRUE))
			title += lookupResource("leaverequest.calendar.requesttype.NA");
		else
			title += lookupResource("leaverequest.calendar.requesttype.S");
		
		evI.setTitle(title);

		cm.updateEventInstance(UpdateEventTarget.ALL_SERIES, evI, false, false, false);
		
		return evI.getEventId();
	}
	
	private void deleteLeaveRequestEvent(LeaveRequest lReq) throws WTException{		
		ICalendarManager cm = (ICalendarManager)WT.getServiceManager("com.sonicle.webtop.calendar", true, getEnv().getProfileId());
		
		if (cm != null) {
			if(lReq.getEventId() != null){
				Event ev = cm.getEvent(lReq.getEventId());
				if(ev != null)
					cm.deleteEventInstance(UpdateEventTarget.ALL_SERIES, EventKey.buildKey(lReq.getEventId(), null), false);
			}
		}
	}
	
	public void processPrintOpportunity(HttpServletRequest request, HttpServletResponse response) {
		ArrayList<RBOpportunity> itemsO = new ArrayList<>();
		
		try {
			String filename = ServletUtils.getStringParameter(request, "filename", "print");
			IntegerArray ids = ServletUtils.getObjectParameter(request, "ids", IntegerArray.class, true);
			
			Opportunity o = null;
			CompanyPicture picture = null;
			Company company = null;
			
			for(Integer id : ids) {
				picture = null;
				o = manager.getOpportunity(id);
				company = manager.getCompany(o.getCompanyId());
				if(company.getHasPicture()) picture = manager.getCompanyPicture(company.getCompanyId());
				itemsO.add(new RBOpportunity(WT.getCoreManager(), manager, o, ss, picture));
			}
			
			ReportConfig.Builder builder = reportConfigBuilder();
			RptOpportunity rpt = new RptOpportunity(builder.build());
			rpt.setDataSource(itemsO);
			
			filename = ""
					+ ((null != ss.getOpportunityGeneralTitle()) ? ss.getOpportunityGeneralTitle() : "Opportunità")
					+ ((null != o.getSector()) ? "-" + o.getSector() : "");
			
			ServletUtils.setFileStreamHeaders(response, filename + ".pdf");
			WT.generateReportToStream(rpt, AbstractReport.OutputType.PDF, response.getOutputStream());
			
		} catch(Exception ex) {
			logger.error("Error in action PrintOpportunity", ex);
			ex.printStackTrace();
			ServletUtils.writeErrorHandlingJs(response, ex.getMessage());
		}
	}
	
	public void processPrepareSendOpportunityAsEmail(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws FileNotFoundException, WTException {
		ArrayList<MapItem> items = new ArrayList<>();
		ArrayList<RBOpportunity> itemsO = new ArrayList<>();
		File file = WT.createTempFile("DRM");
		
		try {
			String tag = ServletUtils.getStringParameter(request, "uploadTag", true);
			IntegerArray ids = ServletUtils.getObjectParameter(request, "ids", IntegerArray.class, true);
			
			Opportunity o = null;
			CompanyPicture picture = null;
			Company company = null;
			
			for(Integer id : ids) {
				picture = null;
				o = manager.getOpportunity(id);
				company = manager.getCompany(o.getCompanyId());
				if(company.getHasPicture()) picture = manager.getCompanyPicture(company.getCompanyId());
				itemsO.add(new RBOpportunity(WT.getCoreManager(), manager, o, ss, picture));
			}
			
			ReportConfig.Builder builder = reportConfigBuilder();
			RptOpportunity rpt = new RptOpportunity(builder.build());
			rpt.setDataSource(itemsO);
			
			final String filename = ""
					+ ((null != ss.getOpportunityGeneralTitle()) ? ss.getOpportunityGeneralTitle() : "Opportunità")
					+ ((null != o.getSector()) ? "-" + o.getSector() : "")
					+ ".pdf";
			
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(file);
				WT.generateReportToStream(rpt, AbstractReport.OutputType.PDF, fos);	
			} finally {
				IOUtils.closeQuietly(fos);
			}
			
			UploadedFile upfile = addAsUploadedFile(tag, filename, "pdf", new FileInputStream(file));
			items.add(new MapItem()
					.add("uploadId", upfile.getUploadId())
					.add("fileName", filename)
					.add("fileSize", upfile.getSize())
				);
			
			//Attachments
			for(OpportunityDocument oDoc : o.getDocuments()){
				OpportunityDocumentWithBytes docData = manager.getOpportunityDocument(o.getId(), oDoc.getId());
				file = WT.createTempFile("DRM");
				InputStream is = null;
				try {
					is = new ByteArrayInputStream(docData.getBytes());
					IOUtils.copy(is, new FileOutputStream(file));
				} finally {
					IOUtils.closeQuietly(is);
				}
				upfile = addAsUploadedFile(tag, oDoc.getFileName(), oDoc.getMediaType(), new FileInputStream(file));
				items.add(new MapItem()
						.add("uploadId", upfile.getUploadId())
						.add("fileName", upfile.getFilename())
						.add("fileSize", upfile.getSize())
					);
			}
			
			new JsonResult(items).printTo(out);
			
		} catch(Exception ex) {
			logger.error("Error in PrepareSendOpportunityAsEmail", ex);
			new JsonResult(ex).printTo(out);
		}
	}
	
	public void processPrepareSendWorkReportAsEmail(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws FileNotFoundException, WTException {
		ArrayList<MapItem> items = new ArrayList<>();
		ArrayList<RBWorkReport> itemsWr = new ArrayList<>();
		File file = WT.createTempFile("DRM");
		
		try {
			String tag = ServletUtils.getStringParameter(request, "uploadTag", true);
			StringArray ids = ServletUtils.getObjectParameter(request, "ids", StringArray.class, true);
			IContactsManager contactManager = (IContactsManager) WT.getServiceManager("com.sonicle.webtop.contacts", getEnv().getProfileId());
			
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
			
			final String filename = "report"
					+ ((null != wr.getNumber()) ? "-" + wr.getNumber() : "")
					+ ((null != wr.getYear()) ? "-" + wr.getYear() : "")
					+ ".pdf";
			
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(file);
				WT.generateReportToStream(rpt, AbstractReport.OutputType.PDF, fos);	
			} finally {
				IOUtils.closeQuietly(fos);
			}
			
			UploadedFile upfile = addAsUploadedFile(tag, filename, "pdf", new FileInputStream(file));
			items.add(new MapItem()
					.add("uploadId", upfile.getUploadId())
					.add("fileName", filename)
					.add("fileSize", upfile.getSize())
				);
			
			//Attachments
			for(WorkReportAttachment wrAtt : wr.getAttachments()){
				WorkReportAttachmentWithBytes docData = manager.getWorkReportAttachment(wr.getWorkReportId(), wrAtt.getWorkReportAttachmentId());
				file = WT.createTempFile("DRM");
				InputStream is = null;
				try {
					is = new ByteArrayInputStream(docData.getBytes());
					IOUtils.copy(is, new FileOutputStream(file));
				} finally {
					IOUtils.closeQuietly(is);
				}
				upfile = addAsUploadedFile(tag, wrAtt.getFileName(), wrAtt.getMediaType(), new FileInputStream(file));
				items.add(new MapItem()
						.add("uploadId", upfile.getUploadId())
						.add("fileName", upfile.getFilename())
						.add("fileSize", upfile.getSize())
					);
			}
			
			new JsonResult(items).printTo(out);
			
		} catch(Exception ex) {
			logger.error("Error in PrepareSendWorkReportAsEmail", ex);
			new JsonResult(ex).printTo(out);
		}
	}
	
	public void processPrepareSendExpenseNoteAsEmail(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws FileNotFoundException, WTException {
		ArrayList<MapItem> items = new ArrayList<>();
		ArrayList<RBExpenseNote> itemsEn = new ArrayList<>();
		File file = WT.createTempFile("DRM");
		
		try {
			String tag = ServletUtils.getStringParameter(request, "uploadTag", true);
			IntegerArray ids = ServletUtils.getObjectParameter(request, "ids", IntegerArray.class, true);
			
			ExpenseNote en = null;
			CompanyPicture picture = null;
			Company company = null;
			
			for(Integer id : ids) {
				picture = null;
				en = manager.getExpenseNote(id);
				company = manager.getCompany(en.getCompanyId());
				if(company.getHasPicture()) picture = manager.getCompanyPicture(company.getCompanyId());
				itemsEn.add(new RBExpenseNote(WT.getCoreManager(), manager, en, ss, picture));
			}
			
			ReportConfig.Builder builder = reportConfigBuilder();
			RptExpenseNote rpt = new RptExpenseNote(builder.build());
			rpt.setDataSource(itemsEn);
			
			final String filename = "expense_note"
					+ ((null != en.getId()) ? "_" + en.getId() : "")
					+ ".pdf";
			
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(file);
				WT.generateReportToStream(rpt, AbstractReport.OutputType.PDF, fos);	
			} finally {
				IOUtils.closeQuietly(fos);
			}
			
			UploadedFile upfile = addAsUploadedFile(tag, filename, "pdf", new FileInputStream(file));
			items.add(new MapItem()
					.add("uploadId", upfile.getUploadId())
					.add("fileName", filename)
					.add("fileSize", upfile.getSize())
				);
			
			//Attachments
			for(ExpenseNoteDocument enDoc : en.getDocuments()){
				ExpenseNoteDocumentWithBytes docData = manager.getExpenseNoteDocument(en.getId(), enDoc.getId());
				file = WT.createTempFile("DRM");
				InputStream is = null;
				try {
					is = new ByteArrayInputStream(docData.getBytes());
					IOUtils.copy(is, new FileOutputStream(file));
				} finally {
					IOUtils.closeQuietly(is);
				}
				upfile = addAsUploadedFile(tag, enDoc.getFileName(), enDoc.getMediaType(), new FileInputStream(file));
				items.add(new MapItem()
						.add("uploadId", upfile.getUploadId())
						.add("fileName", upfile.getFilename())
						.add("fileSize", upfile.getSize())
					);
			}
			
			new JsonResult(items).printTo(out);
			
		} catch(Exception ex) {
			logger.error("Error in PrepareSendExpenseNoteAsEmail", ex);
			new JsonResult(ex).printTo(out);
		}
	}
}
