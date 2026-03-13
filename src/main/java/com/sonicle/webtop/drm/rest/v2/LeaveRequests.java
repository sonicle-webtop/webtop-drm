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

import com.sonicle.commons.time.DateTimeUtils;
import com.sonicle.commons.time.JodaTimeUtils;
import com.sonicle.webtop.core.app.RunContext;
import com.sonicle.webtop.core.app.WT;
import com.sonicle.webtop.core.sdk.UserProfileId;
import com.sonicle.webtop.core.sdk.WTException;
import com.sonicle.webtop.drm.DrmManager;
import com.sonicle.webtop.drm.DrmServiceSettings;
import com.sonicle.webtop.drm.LeaveRequestQuery;
import com.sonicle.webtop.drm.TimetableStampQuery;
import com.sonicle.webtop.drm.bol.OLeaveRequest;
import com.sonicle.webtop.drm.model.LeaveRequest;
import com.sonicle.webtop.drm.model.TimetableStamp;
import com.sonicle.webtop.drm.swagger.v2.api.LeaveRequestsApi;
import com.sonicle.webtop.drm.swagger.v2.model.ApiApiError;
import com.sonicle.webtop.drm.swagger.v2.model.ApiLeaveRequest;
import com.sonicle.webtop.drm.swagger.v2.model.ApiLeaveRequestsResult;
import com.sonicle.webtop.drm.swagger.v2.model.ApiTimetableEntry;
import com.sonicle.webtop.drm.swagger.v2.model.ApiTimetableEntriesResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.core.Response;
import org.codehaus.plexus.util.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author gabriele.bulfon
 */
public class LeaveRequests extends LeaveRequestsApi {
	
	private static final Logger logger = LoggerFactory.getLogger(LeaveRequests.class);
	
	private static final String ACTION_DELETE = "delete";
	private static final String ACTION_APPROVE = "approve";
	private static final String ACTION_REFUSE = "refuse";
	private static final String ACTION_CANCEL = "cancel";
	private static final String ACTION_REQUEST_CANCELLATION = "requestCancellation";

	@Override
	public Response listLeaveRequests(String userId, String startDate, Integer leaveRequestId) {
		UserProfileId currentProfileId = RunContext.getRunProfileId();
		DrmManager manager = getManager();
		try {
			LeaveRequestQuery query = new LeaveRequestQuery();
			query.fromDate = JodaTimeUtils.parseLocalDate(JodaTimeUtils.ISO_LOCALDATE_FMT, startDate);
			if (userId != null) query.userId = userId;
			ApiLeaveRequestsResult alrr = new ApiLeaveRequestsResult();
			ArrayList<ApiLeaveRequest> items = new ArrayList<>();
			if (leaveRequestId!=null) {
				LeaveRequest lr = manager.getLeaveRequest(leaveRequestId);
				ApiLeaveRequest alr = new ApiLeaveRequest();
				alr.setLeaveRequestId(leaveRequestId);
				alr.setCompanyId(lr.getCompanyId());
				alr.setDomainId(lr.getDomainId());
				alr.setFromDate(JodaTimeUtils.print(JodaTimeUtils.ISO_LOCALDATE_FMT, lr.getFromDate()));
				alr.setFromHour(lr.getFromHour());
				alr.setManagerId(lr.getManagerId());
				alr.setToDate(JodaTimeUtils.print(JodaTimeUtils.ISO_LOCALDATE_FMT, lr.getToDate()));
				alr.setToHour(lr.getToHour());
				alr.setType(lr.getType());
				alr.setUserId(lr.getUserId());
				alr.setResult(lr.getResult());
				alr.setStatus(lr.getStatus());
				alr.setNotes(lr.getNotes());
				alr.setCancReason(lr.getCancReason());
				alr.setUser(WT.getUserData(new UserProfileId(lr.getDomainId(), lr.getUserId())).getDisplayName());
				alr.setManager(WT.getUserData(new UserProfileId(lr.getDomainId(), lr.getManagerId())).getDisplayName());
				items.add(alr);
			}
			else if (startDate!=null) {
				for (OLeaveRequest oLR : manager.listLeaveRequest(query)) {
					ApiLeaveRequest alr = new ApiLeaveRequest();
					alr.setCompanyId(oLR.getCompanyId());
					alr.setDomainId(oLR.getDomainId());
					alr.setEmployeeCancReq(oLR.getEmployeeCancReq());
					alr.setFromDate(JodaTimeUtils.print(JodaTimeUtils.ISO_LOCALDATE_FMT, oLR.getFromDate()));
					alr.setFromHour(oLR.getFromHour());
					alr.setLeaveRequestId(oLR.getLeaveRequestId());
					alr.setManagerId(oLR.getManagerId());
					alr.setResult(oLR.getResult());
					alr.setStatus(oLR.getStatus());
					alr.setToDate(JodaTimeUtils.print(JodaTimeUtils.ISO_LOCALDATE_FMT, oLR.getToDate()));
					alr.setToHour(oLR.getToHour());
					alr.setType(oLR.getType());
					alr.setUserId(oLR.getUserId());
					alr.setNotes(oLR.getNotes());
					alr.setCancReason(oLR.getCancReason());
					alr.setUser(WT.getUserData(new UserProfileId(oLR.getDomainId(), oLR.getUserId())).getDisplayName());
					alr.setManager(WT.getUserData(new UserProfileId(oLR.getDomainId(), oLR.getManagerId())).getDisplayName());
					items.add(alr);
				}
			}
			alrr.setItems(items);
			return respOk(alrr);
		} catch(WTException exc) {
			logger.error("[{}] getTimetable({})", currentProfileId, startDate, exc);
			return respError(exc);
		}
	}

	@Override
	public Response addLeaveRequest(ApiLeaveRequest apiLeaveRequest) {
		UserProfileId currentProfileId = RunContext.getRunProfileId();
		String userId = currentProfileId.getUserId();
		DrmManager manager = getManager();
		DrmServiceSettings dss =  manager.getServiceSettings();
		try {
			LeaveRequest lr = new LeaveRequest();
			lr.setCompanyId(apiLeaveRequest.getCompanyId());
			lr.setUserId(userId);
			lr.setManagerId(apiLeaveRequest.getManagerId());
			lr.setType(apiLeaveRequest.getType());
			lr.setFromDate(JodaTimeUtils.parseLocalDate(JodaTimeUtils.ISO_LOCALDATE_FMT, apiLeaveRequest.getFromDate()));
			lr.setToDate(JodaTimeUtils.parseLocalDate(JodaTimeUtils.ISO_LOCALDATE_FMT, apiLeaveRequest.getToDate()));
			lr.setFromHour(apiLeaveRequest.getFromHour());
			lr.setToHour(apiLeaveRequest.getToHour());
			lr.setStatus(apiLeaveRequest.getStatus());
			lr.setNotes(apiLeaveRequest.getNotes());
			manager.addLeaveRequest(lr, dss.getMedicalVisitsAutomaticallyApproved(), dss.getSicknessAutomaticallyApproved());
			return respOk();
		} catch(WTException exc) {
			logger.error("[{}] addLeaveRequest({})", currentProfileId, apiLeaveRequest.getUserId(), exc);
			return respError(exc);
		}
	}

	@Override
	public Response updateLeaveRequests(ApiLeaveRequest apiLeaveRequest) {
		UserProfileId currentProfileId = RunContext.getRunProfileId();
		String userId = currentProfileId.getUserId();
		String domainId = currentProfileId.getDomainId();
		DrmManager manager = getManager();
		try {
			LeaveRequest lr = new LeaveRequest();
			lr.setLeaveRequestId(apiLeaveRequest.getLeaveRequestId());
			lr.setCompanyId(apiLeaveRequest.getCompanyId());
			lr.setDomainId(domainId);
			lr.setUserId(userId);
			lr.setManagerId(apiLeaveRequest.getManagerId());
			lr.setType(apiLeaveRequest.getType());
			lr.setFromDate(JodaTimeUtils.parseLocalDate(JodaTimeUtils.ISO_LOCALDATE_FMT, apiLeaveRequest.getFromDate()));
			lr.setToDate(JodaTimeUtils.parseLocalDate(JodaTimeUtils.ISO_LOCALDATE_FMT, apiLeaveRequest.getToDate()));
			lr.setFromHour(apiLeaveRequest.getFromHour());
			lr.setToHour(apiLeaveRequest.getToHour());
			lr.setStatus(apiLeaveRequest.getStatus());
			lr.setResult(apiLeaveRequest.getResult());
			lr.setNotes(apiLeaveRequest.getNotes());
			lr.setCancRequest(false);
			manager.updateLeaveRequest(lr, true);
			return respOk();
		} catch(Exception exc) {
			logger.error("[{}] updateLeaveRequest({})", currentProfileId, apiLeaveRequest.getUserId(), exc);
			return respError(exc);
		}
	}

	@Override
	public Response deleteLeaveRequest(Integer leaveRequestId, String action, String text) {
		UserProfileId currentProfileId = RunContext.getRunProfileId();
		DrmManager manager = getManager();
		try {
			if (action == null || action.equals(ACTION_DELETE)) manager.deleteLeaveRequest(leaveRequestId);
			else if (action.equals(ACTION_REQUEST_CANCELLATION)) {
				LeaveRequest lr=manager.timetableRequestCancellation(leaveRequestId, text);
				manager.createOrUpdateLeaveRequestEventIntoLeaveRequestCalendar(lr);
			}
			else if (action.equals(ACTION_CANCEL)) {
				manager.cancelLeaveRequest(leaveRequestId, true);
			}
			else if (action.equals(ACTION_APPROVE)) {
				manager.approveLeaveRequest(leaveRequestId);
			}
			else if (action.equals(ACTION_REFUSE)) {
				manager.declineLeaveRequest(leaveRequestId);
			}
			else throw new Exception("Unknown action "+action);
			
			return respOk();
		} catch(Exception exc) {
			logger.error("[{}] deleteLeaveRequest({})", currentProfileId, leaveRequestId, exc);
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
