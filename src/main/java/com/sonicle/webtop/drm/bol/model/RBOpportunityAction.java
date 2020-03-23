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
import com.sonicle.webtop.drm.model.OpportunityAction;
import java.util.Date;

/**
 *
 * @author lssndrvs
 */
public class RBOpportunityAction {

	public Integer id;
	public Integer opportunityId;
	public String operatorId;
	public String operatorDescription;
	public Integer statusId;
	public String statusDescription;
	public Date startDate;
	public Date endDate;
	public String description;
	public String place;
	public String subsequentActions;
	public Integer activityId;
	public String activityDescription;	
	
	RBOpportunityAction(CoreManager coreMgr, DrmManager drmMgr, OpportunityAction oA) throws WTException {
		this.id = oA.getId();
		this.opportunityId = oA.getOpportunityId();
		this.operatorId = oA.getOperatorId();
		this.operatorDescription = (oA.getOperatorId() == null) ? null : lookupOperator(oA.getOperatorProfileId(drmMgr.getTargetProfileId().getDomainId()));
		this.statusId = oA.getStatusId();
		this.statusDescription = (oA.getStatusId() == null) ? null : drmMgr.getDocStatus(oA.getStatusId()).getName();
		this.startDate = (oA.getStartDate() == null) ? null : oA.getStartDate().toDate();
		this.endDate = (oA.getEndDate() == null) ? null : oA.getEndDate().toDate();
		this.description = oA.getDescription();
		this.place = oA.getPlace();
		this.subsequentActions = oA.getSubsequentActions();
		this.activityId = oA.getActivityId();
		this.activityDescription = (oA.getActivityId() == null) ? null : coreMgr.getActivity(oA.getActivityId()).getDescription();
	}

	private String lookupOperator(UserProfileId operatorProfile){
		UserProfile.Data ud = WT.getUserData(operatorProfile);
		return (ud != null) ? ud.getDisplayName() : operatorProfile.toString();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOpportunityId() {
		return opportunityId;
	}

	public void setOpportunityId(Integer opportunityId) {
		this.opportunityId = opportunityId;
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

	public String getSubsequentActions() {
		return subsequentActions;
	}

	public void setSubsequentActions(String subsequentActions) {
		this.subsequentActions = subsequentActions;
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
}
