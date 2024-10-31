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
package com.sonicle.webtop.drm.rest.v1;

import com.sonicle.commons.time.DateTimeUtils;
import com.sonicle.webtop.core.app.RunContext;
import com.sonicle.webtop.core.app.WT;
import com.sonicle.webtop.core.sdk.UserProfileId;
import com.sonicle.webtop.core.sdk.WTException;
import com.sonicle.webtop.drm.DrmManager;
import com.sonicle.webtop.drm.model.TimetableStamp;
import com.sonicle.webtop.drm.swagger.v1.api.TimetableApi;
import com.sonicle.webtop.drm.swagger.v1.model.ApiApiError;
import com.sonicle.webtop.drm.swagger.v1.model.ApiTimetableEntry;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.core.Response;
import org.codehaus.plexus.util.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author gabriele.bulfon
 */
public class Timetable extends TimetableApi {
	
	private static final Logger logger = LoggerFactory.getLogger(Timetable.class);
	private static final DateTimeFormatter ISO_DATETIME_FMT = DateTimeUtils.createFormatter("yyyyMMdd'T'HHmmss'Z'", DateTimeZone.UTC);

	@Override
	public Response addTimetableEntries(List<ApiTimetableEntry> entries) {
		UserProfileId currentProfileId = RunContext.getRunProfileId();
		DrmManager manager = getManager();
		HashMap<String, String> id2userId = new HashMap<>();
		try {
			for(ApiTimetableEntry entry: entries) {
				String employeeId=entry.getEmployeeId();
				String userId=id2userId.get(employeeId);
				if (userId==null) {
					userId=manager.getEmployeeProfile(employeeId).getUserId();
					id2userId.put(employeeId, userId);
				}
				TimetableStamp ts=new TimetableStamp();
				ts.setDomainId(currentProfileId.getDomainId());
				ts.setUserId(userId);
				ts.setType("M");
				ts.setEntrance(DateTimeUtils.parseDateTime(ISO_DATETIME_FMT, entry.getEntrance()));
				ts.setExit(DateTimeUtils.parseDateTime(ISO_DATETIME_FMT, entry.getExit()));
				String location = entry.getLocation();
				if (StringUtils.isEmpty(location)) location = "O";
				ts.setLocation(location);
				manager.addTimetableStamp(ts);
			}
			return respOkCreated();
		} catch(WTException exc) {
			logger.error("[{}] addTimetableEntries({})", currentProfileId, entries, exc);
			return respError(exc);
		}
	}

	/*
	 * Delete only "Office" entries, keep "Smart" entries.
	 */
	@Override
	public Response deleteTimetableEntries(String fromDate, String toDate, List<String> employeeIds) {
		UserProfileId currentProfileId = RunContext.getRunProfileId();
		DrmManager manager = getManager();
		try {
			List<String> userIds = (employeeIds!=null && employeeIds.size()>0) ? manager.getEmployeeUserIds(employeeIds) : null;
			manager.deleteTimetableStamp(
				userIds, 
				DateTimeUtils.parseDateTime(ISO_DATETIME_FMT, fromDate),
				DateTimeUtils.parseDateTime(ISO_DATETIME_FMT, toDate),
				"O"
			);
			return respOk();
		} catch(WTException exc) {
			logger.error("[{}] addTimetableEntries({})", currentProfileId, employeeIds, fromDate, toDate);
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
