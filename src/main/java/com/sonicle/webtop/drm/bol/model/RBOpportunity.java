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
package com.sonicle.webtop.drm.bol.model;

import com.sonicle.webtop.core.CoreManager;
import com.sonicle.webtop.core.app.WT;
import com.sonicle.webtop.core.sdk.UserProfile;
import com.sonicle.webtop.core.sdk.UserProfileId;
import com.sonicle.webtop.core.sdk.WTException;
import com.sonicle.webtop.drm.DrmManager;
import com.sonicle.webtop.drm.DrmServiceSettings;
import static com.sonicle.webtop.drm.Service.logger;
import com.sonicle.webtop.drm.model.CompanyPicture;
import com.sonicle.webtop.drm.model.Opportunity;
import com.sonicle.webtop.drm.model.OpportunityAction;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.imageio.ImageIO;

/**
 *
 * @author lssndrvs
 */
public class RBOpportunity {
	
	public String title;
	
	public Integer id;
	public Integer companyId;
	public String companyDescription;
	public String operatorId;
	public String operatorDescription;
	public Date startDate;
	public Date endDate;
	public String executedWithId;
	public String executedWithDexcription;
	public String customerId;
	public String customerDescription;
	public String customerStatId;
	public String customerStatDescription;
	public String sector;
	public String description;
	public String place;
	public String objective;
	public Integer causalId;
	public String causalDescription;
	public Integer activityId;
	public String activityDescription;
	public String objective2;
	public String result;
	public String discoveries;
	public String customerPotential;
	public String notes;
	public Integer statusId;
	public String statusDescription;
	public String signedById;
	public String signedByDescription;
	public Boolean signature;
	public Boolean success;
	public Integer deliverWithin;
	
	public ArrayList<RBOpportunityAction> oActs;
	
	public Image picture;
	
	public RBOpportunity(CoreManager coreMgr, DrmManager drmMgr, Opportunity o, DrmServiceSettings ss, CompanyPicture picture) throws WTException, IOException {		
		
		this.title = (ss.getOpportunityGeneralTitle() != null) ? ss.getOpportunityGeneralTitle() : "Opportunità";
		
		this.id = o.getId();
		this.companyId = o.getCompanyId();
		this.companyDescription = (o.getCompanyId() == null) ? null : drmMgr.getCompany(o.getCompanyId()).getName();
		this.operatorId = o.getOperatorId();
		this.operatorDescription = (o.getOperatorId() == null) ? null : lookupOperator(o.getOperatorProfileId(drmMgr.getTargetProfileId().getDomainId()));
		this.startDate = (o.getStartDate() == null) ? null : o.getStartDate().toDate();
		this.endDate = (o.getEndDate() == null) ? null : o.getEndDate().toDate();
		this.executedWithId = o.getExecutedWith();
		this.executedWithDexcription = (o.getExecutedWith() == null) ? null : lookupOperator(o.getExecutedWithProfileId(drmMgr.getTargetProfileId().getDomainId()));
		this.customerId = o.getCustomerId();
		this.customerDescription = (o.getCustomerId() == null) ? null : coreMgr.getMasterData(o.getCustomerId()).getDescription();
		this.customerStatId = o.getCustomerStatId();
		this.customerStatDescription = (o.getCustomerStatId() == null) ? null : coreMgr.getMasterData(o.getCustomerStatId()).getDescription();
		this.sector = o.getSector();
		this.description = o.getDescription();
		this.place = o.getPlace();
		this.objective = o.getObjective();
		this.causalId = o.getCausalId();
		this.causalDescription = (o.getCausalId() == null) ? null : coreMgr.getCausal(o.getCausalId()).getDescription();
		this.activityId = o.getActivityId();
		this.activityDescription = (o.getActivityId() == null) ? null : coreMgr.getActivity(o.getActivityId()).getDescription();
		this.objective2 = o.getObjective2();
		this.result = o.getResult();
		this.discoveries = o.getDiscoveries();
		this.customerPotential = o.getCustomerPotential();
		this.notes = o.getNotes();
		this.statusId = o.getStatusId();
		this.statusDescription = (o.getStatusId() == null) ? null : drmMgr.getDocStatus(o.getStatusId()).getName();
		this.signedById = o.getSignedBy();
		this.signedByDescription = (o.getSignedBy() == null) ? null : lookupOperator(o.getSignedByProfileId(drmMgr.getTargetProfileId().getDomainId()));
		this.signature = o.getSignature();
		this.success = o.getSuccess();
		this.deliverWithin = (o.getEndDate() == null) ? null : o.getEndDate().getMonthOfYear();
		
		oActs = new ArrayList<>();
		for(OpportunityAction oA : o.getActions()){
			oActs.add(new RBOpportunityAction(coreMgr, drmMgr, oA));
		}
		
		if(picture != null) {
			try {
				try(ByteArrayInputStream bais = new ByteArrayInputStream(picture.getBytes())) {
					this.picture = ImageIO.read(bais);
				}
			} catch (IOException ex) {
				logger.error("Error in Opportunity Picture Configuration ", ex);
			}
		}
	}
	
	private String lookupOperator(UserProfileId operatorProfile){
		UserProfile.Data ud = WT.getUserData(operatorProfile);
		return (ud != null) ? ud.getDisplayName() : operatorProfile.toString();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getExecutedWithId() {
		return executedWithId;
	}

	public void setExecutedWithId(String executedWithId) {
		this.executedWithId = executedWithId;
	}

	public String getExecutedWithDexcription() {
		return executedWithDexcription;
	}

	public void setExecutedWithDexcription(String executedWithDexcription) {
		this.executedWithDexcription = executedWithDexcription;
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

	public String getCustomerStatId() {
		return customerStatId;
	}

	public void setCustomerStatId(String customerStatId) {
		this.customerStatId = customerStatId;
	}

	public String getCustomerStatDescription() {
		return customerStatDescription;
	}

	public void setCustomerStatDescription(String customerStatDescription) {
		this.customerStatDescription = customerStatDescription;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getObjective() {
		return objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
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

	public void setCausalDexcription(String causalDescription) {
		this.causalDescription = causalDescription;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public String getActivityDescription() {
		return activityDescription;
	}

	public void setActivityDescription(String activityDescription) {
		this.activityDescription = activityDescription;
	}

	public String getObjective2() {
		return objective2;
	}

	public void setObjective2(String objective2) {
		this.objective2 = objective2;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getDiscoveries() {
		return discoveries;
	}

	public void setDiscoveries(String discoveries) {
		this.discoveries = discoveries;
	}

	public String getCustomerPotential() {
		return customerPotential;
	}

	public void setCustomerPotential(String customerPotential) {
		this.customerPotential = customerPotential;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public String getSignedById() {
		return signedById;
	}

	public void setSignedById(String signedById) {
		this.signedById = signedById;
	}

	public String getSignedByDescription() {
		return signedByDescription;
	}

	public void setSignedByDescription(String signedByDescription) {
		this.signedByDescription = signedByDescription;
	}

	public Boolean getSignature() {
		return signature;
	}

	public void setSignature(Boolean signature) {
		this.signature = signature;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getDeliverWithin() {
		String value = null;
		
		switch(this.deliverWithin){
			case 1: value = "Gennaio";
					break;
			case 2: value = "Febbraio";
					break;
			case 3: value = "Marzo";
					break;	
			case 4: value = "Aprile";
					break;
			case 5: value = "Maggio";
					break;
			case 6: value = "Giugno";
					break;
			case 7: value = "Luglio";
					break;
			case 8: value = "Agosto";
					break;
			case 9: value = "Settembre";
					break;	
			case 10: value = "Ottobre";
					break;
			case 11: value = "Novembre";
					break;
			case 12: value = "Dicembre";
					break;
			default: value = null;
					break;
		}
		
		return value;
	}

	public void setDeliverWithin(Integer deliverWithin) {
		this.deliverWithin = deliverWithin;
	}

	public ArrayList<RBOpportunityAction> getoActs() {
		return oActs;
	}

	public void setoActs(ArrayList<RBOpportunityAction> oActs) {
		this.oActs = oActs;
	}

	public Image getPicture() {
		return picture;
	}

	public void setPicture(Image picture) {
		this.picture = picture;
	}
}
