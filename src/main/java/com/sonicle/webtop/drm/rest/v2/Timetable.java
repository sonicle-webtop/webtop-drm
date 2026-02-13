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
import com.sonicle.webtop.core.app.RunContext;
import com.sonicle.webtop.core.app.WT;
import com.sonicle.webtop.core.sdk.UserProfileId;
import com.sonicle.webtop.core.sdk.WTException;
import com.sonicle.webtop.drm.DrmManager;
import com.sonicle.webtop.drm.TimetableStampQuery;
import com.sonicle.webtop.drm.model.TimetableStamp;
import com.sonicle.webtop.drm.swagger.v2.api.TimetableApi;
import com.sonicle.webtop.drm.swagger.v2.model.ApiApiError;
import com.sonicle.webtop.drm.swagger.v2.model.ApiTimetableEntry;
import com.sonicle.webtop.drm.swagger.v2.model.ApiTimetableEntriesResult;
import java.util.ArrayList;
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
	public Response listTimetableEntries(String operatorId, Integer year, Integer month) {
		UserProfileId currentProfileId = RunContext.getRunProfileId();
		DrmManager manager = getManager();
		try {
			TimetableStampQuery query = new TimetableStampQuery();
			query.operatorId = operatorId;
			query.year = year;
			query.month = month;
			List<TimetableStamp> list = manager.listTimetableStamps(query);
			ApiTimetableEntriesResult ater = new ApiTimetableEntriesResult();
			ArrayList<ApiTimetableEntry> items = new ArrayList<>();
			for (TimetableStamp ts: list) {
				ApiTimetableEntry entry = new ApiTimetableEntry();
				entry.setType(ts.getType());
				entry.setDate((null != ts.getEntrance()) ? ((ts.getEntrance().getDayOfMonth()< 10 ? "0" + ts.getEntrance().getDayOfMonth() : ts.getEntrance().getDayOfMonth()) + "/" + (ts.getEntrance().getMonthOfYear() < 10 ? "0" + ts.getEntrance().getMonthOfYear() : ts.getEntrance().getMonthOfYear()) + "/" + (ts.getEntrance().getYear())) : (""));
				entry.setEntrance((null != ts.getEntrance()) ? ((ts.getEntrance().getHourOfDay() < 10 ? "0" + ts.getEntrance().getHourOfDay() : ts.getEntrance().getHourOfDay()) + ":" + (ts.getEntrance().getMinuteOfHour() < 10 ? "0" + ts.getEntrance().getMinuteOfHour() : ts.getEntrance().getMinuteOfHour())) : (""));
				entry.setExit((null != ts.getExit()) ? ((ts.getExit().getHourOfDay() < 10 ? "0" + ts.getExit().getHourOfDay() : ts.getExit().getHourOfDay()) + ":" + (ts.getExit().getMinuteOfHour() < 10 ? "0" + ts.getExit().getMinuteOfHour() : ts.getExit().getMinuteOfHour())) : (""));
				entry.setMinutes((int)ts.getMinutes());
				entry.setLocation(ts.getLocation());
				items.add(entry);
			}
			ater.setItems(items);
			return respOk(ater);
		} catch(WTException exc) {
			logger.error("[{}] getTimetable({})", currentProfileId, operatorId, year, month, exc);
			return respError(exc);
		}
	}

	@Override
	public Response punch(String type, String location) {
		UserProfileId currentProfileId = RunContext.getRunProfileId();
		DrmManager manager = getManager();
		try {
			TimetableStamp stamp = new TimetableStamp();
			stamp.setType(type);
			if (location != null) stamp.setLocation(location);
			manager.setTimetable(stamp);
			return respOk();
		} catch(WTException exc) {
			logger.error("[{}] punch({})", currentProfileId, type, location, exc);
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
