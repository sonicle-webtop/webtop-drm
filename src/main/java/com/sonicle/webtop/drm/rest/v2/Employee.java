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
package com.sonicle.webtop.drm.rest.v2;

import com.sonicle.commons.EnumUtils;
import com.sonicle.webtop.core.app.RunContext;
import com.sonicle.webtop.core.app.WT;
import com.sonicle.webtop.core.sdk.UserProfileId;
import com.sonicle.webtop.core.sdk.WTException;
import com.sonicle.webtop.drm.DrmManager;
import com.sonicle.webtop.drm.bol.OCompany;
import com.sonicle.webtop.drm.bol.OLeaveRequest;
import com.sonicle.webtop.drm.bol.OLeaveRequestType;
import com.sonicle.webtop.drm.model.EmployeeProfile;
import com.sonicle.webtop.drm.swagger.v2.api.EmployeeApi;
import com.sonicle.webtop.drm.swagger.v2.model.ApiApiError;
import com.sonicle.webtop.drm.swagger.v2.model.ApiEmployee;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author gabriele.bulfon
 */
public class Employee extends EmployeeApi {
	
	private static final Logger logger = LoggerFactory.getLogger(Employee.class);

	@Override
	public Response getEmployeeProfile() {
		UserProfileId currentProfileId = RunContext.getRunProfileId();
		String userId = currentProfileId.getUserId();
		String domainId = currentProfileId.getDomainId();
		DrmManager manager = getManager();
		try {
			EmployeeProfile ep = manager.getEmployeeProfile(domainId, userId); 
			ApiEmployee ae = new ApiEmployee();
			ae.setDomainId(domainId);
			ae.setExtraordinary(ep.getExtraordinary());
			ae.setHeadquartersCode(ep.getHeadquartersCode());
			ae.setHourProfileId(ep.getHourProfileId());
			ae.setId(ep.getId());
			ae.setMinimumNumberOfHoursPerTicket(ep.getMinimumNumberOfHoursPerTicket());
			ae.setNoStamping(ep.getNoStamping());
			ae.setNumber(ep.getNumber());
			ae.setOnlyPresence(ep.getOnlyPresence());
			ae.setStampingMode(ep.getStampingMode());
			ae.setTolerance(ep.getTolerance());
			ae.setUserId(ep.getUserId());
			ae.setIsManager(manager.isLineManager(domainId, userId));
			List<OLeaveRequestType> olrTypes = manager.listLeaveRequestTypes(domainId, userId);
			List<String> olrList = new ArrayList<>();
			for(OLeaveRequestType olrt: olrTypes) olrList.add(EnumUtils.toSerializedName(olrt));
			ae.setLeaveRequestTypes(olrList);
			return respOk(ae);
		} catch(WTException exc) {
			logger.error("[{}] getEmployeeProfile({})", currentProfileId, userId, exc);
			return respError(exc);
		}
	}
	
	

	@Override
	protected Object createErrorEntity(Response.Status status, String message) {
		return new ApiApiError()
				.code(status.getStatusCode())
				.description(message);
	}
	
	private DrmManager getManager() {
		return getManager(RunContext.getRunProfileId());
	}
	
	private DrmManager getManager(UserProfileId targetProfileId) {
		return (DrmManager)WT.getServiceManager(SERVICE_ID, targetProfileId);
	}
	
	

}
