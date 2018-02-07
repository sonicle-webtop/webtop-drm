/* 
 * Copyright (C) 2014 Sonicle S.r.l.
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
 * display the words "Copyright (C) 2014 Sonicle S.r.l.".
 */
package com.sonicle.webtop.drm.bol.model;

import com.sonicle.webtop.contacts.IContactsManager;
import com.sonicle.webtop.core.CoreManager;
import com.sonicle.webtop.core.app.WT;
import com.sonicle.webtop.core.sdk.UserProfile;
import com.sonicle.webtop.core.sdk.UserProfileId;
import com.sonicle.webtop.core.sdk.WTException;
import com.sonicle.webtop.drm.DrmManager;
import com.sonicle.webtop.drm.DrmServiceSettings;
import static com.sonicle.webtop.drm.Service.logger;
import com.sonicle.webtop.drm.model.CompanyPicture;
import com.sonicle.webtop.drm.model.WorkReport;
import com.sonicle.webtop.drm.model.WorkReportRow;
import com.sonicle.webtop.drm.model.WorkReportSetting;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.imageio.ImageIO;
import org.joda.time.DateTime;

/**
 *
 * @author malbinola
 */
public class RBWorkReport {
	public String workReportId;
	public String workReportNo;
	public Integer companyId;
	public String companyDescription;
	public String operatorId;
	public String operatorDescription;
	public String revisionStatus;
	public DateTime revisionTimestamp;
	public Integer revisionSequence;
	public Integer docStatusId;
	public String docStatusDescription;
	public Integer contactId;
	public String contactDescription;
	public String customerId;
	public String customerDescription;
	public String customerAddress;
	public String customerCity;
	public String statisticCustomerId;
	public String statisticCustomerDescription;
	public Date fromDate;
	public Date toDate;
	public String referenceNo;
	public Integer causalId;
	public String causalDescription;
	public String ddtNo;
	public Date ddtDate;
	public String notes;
	public String description;
	public Boolean applySignature;
	public Boolean chargeTo;
	public Boolean freeSupport;
	public Integer businessTripId;
	public String businessTripDescription;
	public Integer dayTransfert;
	public ArrayList<RBWorkReportRows> wrRows;
	public String warranty;
	public String footer;
	public Boolean printDaysTransfert;
	public Boolean printTransfertDescription;
	public Boolean printSignature;
	public Image picture;
		
	public RBWorkReport(CoreManager coreMgr, DrmManager drmMgr, IContactsManager contactMgr, WorkReport wr, DrmServiceSettings ss, CompanyPicture picture) throws WTException, IOException {		
		this.workReportId = wr.getWorkReportId();
		this.workReportNo = wr.getWorkReportNo();
		this.companyId = wr.getCompanyId();
		this.companyDescription = drmMgr.getCompany(wr.getCompanyId()).getName();
		this.operatorId = wr.getOperatorId();
		this.operatorDescription = lookupOperator(wr.getOperatorProfileId(drmMgr.getTargetProfileId().getDomainId()));
		this.revisionStatus = wr.getRevisionStatus().name();
		this.revisionTimestamp = wr.getRevisionTimestamp();
		this.revisionSequence = wr.getRevisionSequence();
		this.docStatusId = wr.getDocStatusId();
		this.docStatusDescription = drmMgr.getDocStatus(wr.getDocStatusId()).getName();
		this.contactId = wr.getContactId();
		if (this.contactId != null)
			this.contactDescription = contactMgr.getContact(wr.getContactId()).getFullName();
		this.customerId = wr.getCustomerId();
		this.customerDescription = coreMgr.getMasterData(wr.getCustomerId()).getDescription();
		this.customerAddress = coreMgr.getMasterData(wr.getCustomerId()).getAddress();
		this.customerCity = coreMgr.getMasterData(wr.getCustomerId()).getCity();
		this.statisticCustomerId = wr.getCustomerStatId();
		this.statisticCustomerDescription = coreMgr.getMasterData(wr.getCustomerStatId()).getDescription();
		this.fromDate = wr.getFromDate().toDate();
		this.toDate = wr.getToDate().toDate();
		this.referenceNo = wr.getReferenceNo();
		this.causalId = wr.getCausalId();
		this.causalDescription = coreMgr.getCausal(wr.getCausalId()).getDescription();
		this.ddtNo = wr.getDdtNo();
		if(wr.getDdtDate() != null)
			this.ddtDate = wr.getDdtDate().toDate();
		this.notes = wr.getNotes();
		this.description = wr.getDescription();
		this.applySignature = wr.getApplySignature();
		this.chargeTo = wr.getChargeTo();
		this.freeSupport = wr.getFreeSupport();
		this.businessTripId = wr.getBusinessTripId();
		this.businessTripDescription = "";
		if (this.businessTripId != null)
			this.businessTripDescription=drmMgr.getBusinessTripById(this.businessTripId).getDescription();
		this.dayTransfert = wr.getDayTrasfert();
		
		wrRows = new ArrayList<>();
		for(WorkReportRow wrr : wr.getDetails()){
			wrRows.add(new RBWorkReportRows(drmMgr, wrr));
		}
		//Add empty rows (for maximum 18)
		for(int i = 0; i < (18 - wr.getDetails().size()); i++) wrRows.add(new RBWorkReportRows());
		
		this.warranty="";
		WorkReportSetting wrs=drmMgr.getWorkReportSetting();
		if (wrs!=null)
			this.warranty = wrs.getWarrantyText();
		this.footer = drmMgr.getCompany(wr.getCompanyId()).getFooterColumnLeft();
		
		this.printTransfertDescription = ss.getPrintTransfertDescription();
		this.printDaysTransfert = ss.getPrintDaysTransfert();
		this.printSignature = ss.getPrintSignature();
		
		if(picture != null) {
			try {
				try(ByteArrayInputStream bais = new ByteArrayInputStream(picture.getBytes())) {
					this.picture = ImageIO.read(bais);
				}
			} catch (IOException ex) {
				logger.error("Error in WorkReport Picture Configuration ", ex);
			}
		}
	}
	
	private String lookupOperator(UserProfileId operatorProfile){
		UserProfile.Data ud = WT.getUserData(operatorProfile);
		return (ud != null) ? ud.getDisplayName() : operatorProfile.toString();
	}

	public String getWorkReportId() {
		return workReportId;
	}

	public void setWorkReportId(String workReportId) {
		this.workReportId = workReportId;
	}

	public String getWorkReportNo() {
		return workReportNo;
	}

	public void setWorkReportNo(String workReportNo) {
		this.workReportNo = workReportNo;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getCompanyDescription() {
		return companyDescription;
	}

	public void setCompanyDescription(String companyDescription) {
		this.companyDescription = companyDescription;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorDescription() {
		return operatorDescription;
	}

	public void setOperatorDescription(String operatorDescription) {
		this.operatorDescription = operatorDescription;
	}

	public String getRevisionStatus() {
		return revisionStatus;
	}

	public void setRevisionStatus(String revisionStatus) {
		this.revisionStatus = revisionStatus;
	}

	public DateTime getRevisionTimestamp() {
		return revisionTimestamp;
	}

	public void setRevisionTimestamp(DateTime revisionTimestamp) {
		this.revisionTimestamp = revisionTimestamp;
	}

	public Integer getRevisionSequence() {
		return revisionSequence;
	}

	public void setRevisionSequence(Integer revisionSequence) {
		this.revisionSequence = revisionSequence;
	}

	public Integer getDocStatusId() {
		return docStatusId;
	}

	public void setDocStatusId(Integer docStatusId) {
		this.docStatusId = docStatusId;
	}

	public String getDocStatusDescription() {
		return docStatusDescription;
	}

	public void setDocStatusDescription(String docStatusDescription) {
		this.docStatusDescription = docStatusDescription;
	}

	public Integer getContactId() {
		return contactId;
	}

	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	public String getContactDescription() {
		return contactDescription;
	}

	public void setContactDescription(String contactDescription) {
		this.contactDescription = contactDescription;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerDescription() {
		return customerDescription;
	}

	public void setCustomerDescription(String customerDescription) {
		this.customerDescription = customerDescription;
	}

	public String getStatisticCustomerId() {
		return statisticCustomerId;
	}

	public void setStatisticCustomerId(String statisticCustomerId) {
		this.statisticCustomerId = statisticCustomerId;
	}

	public String getStatisticCustomerDescription() {
		return statisticCustomerDescription;
	}

	public void setStatisticCustomerDescription(String statisticCustomerDescription) {
		this.statisticCustomerDescription = statisticCustomerDescription;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public Integer getCausalId() {
		return causalId;
	}

	public void setCausalId(Integer causalId) {
		this.causalId = causalId;
	}

	public String getCausalDescription() {
		return causalDescription;
	}

	public void setCausalDescription(String causalDescription) {
		this.causalDescription = causalDescription;
	}

	public String getDdtNo() {
		return ddtNo;
	}

	public void setDdtNo(String ddtNo) {
		this.ddtNo = ddtNo;
	}

	public Date getDdtDate() {
		return ddtDate;
	}

	public void setDdtDate(Date ddtDate) {
		this.ddtDate = ddtDate;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getApplySignature() {
		return applySignature;
	}

	public void setApplySignature(Boolean applySignature) {
		this.applySignature = applySignature;
	}

	public Boolean getChargeTo() {
		return chargeTo;
	}

	public void setChargeTo(Boolean chargeTo) {
		this.chargeTo = chargeTo;
	}

	public Boolean getFreeSupport() {
		return freeSupport;
	}

	public void setFreeSupport(Boolean freeSupport) {
		this.freeSupport = freeSupport;
	}

	public Integer getBusinessTripId() {
		return businessTripId;
	}

	public void setBusinessTripId(Integer businessTripId) {
		this.businessTripId = businessTripId;
	}

	public String getBusinessTripDescription() {
		return businessTripDescription;
	}

	public void setBusinessTripDescription(String businessTripDescription) {
		this.businessTripDescription = businessTripDescription;
	}

	public Integer getDayTransfert() {
		return dayTransfert;
	}

	public void setDayTransfert(Integer dayTransfert) {
		this.dayTransfert = dayTransfert;
	}

	public ArrayList<RBWorkReportRows> getWrRows() {
		return wrRows;
	}

	public void setWrRows(ArrayList<RBWorkReportRows> wrRows) {
		this.wrRows = wrRows;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getCustomerCity() {
		return customerCity;
	}

	public void setCustomerCity(String customerCity) {
		this.customerCity = customerCity;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public String getWarranty() {
		return warranty;
	}

	public void setWarranty(String warranty) {
		this.warranty = warranty;
	}

	public Boolean getPrintDaysTransfert() {
		return printDaysTransfert;
	}

	public void setPrintDaysTransfert(Boolean printDaysTransfert) {
		this.printDaysTransfert = printDaysTransfert;
	}

	public Boolean getPrintTransfertDescription() {
		return printTransfertDescription;
	}

	public void setPrintTransfertDescription(Boolean printTransfertDescription) {
		this.printTransfertDescription = printTransfertDescription;
	}

	public Boolean getPrintSignature() {
		return printSignature;
	}

	public void setPrintSignature(Boolean printSignature) {
		this.printSignature = printSignature;
	}

	public Image getPicture() {
		return picture;
	}

	public void setPicture(Image picture) {
		this.picture = picture;
	}
}
