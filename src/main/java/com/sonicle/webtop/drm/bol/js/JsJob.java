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
package com.sonicle.webtop.drm.bol.js;

import com.sonicle.commons.time.DateTimeUtils;
import com.sonicle.webtop.calendar.CalendarUtils;
import com.sonicle.webtop.drm.model.Job;
import com.sonicle.webtop.drm.model.JobAttachment;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author dnllr
 */
public class JsJob {

	public String jobId;
	public Integer companyId;
	public String operatorId;
	public String customerId;
	public String customerStatId;
	public String startDate;
	public String endDate;
	public String timezone;
	public Integer activityId;
	public String title;
	public String description;
	public Integer eventId;
	public String domainId;
	public String ticketId;
	public String number;
	public Integer causalId;
	
	public List<Attachment> attachments = new ArrayList();

	public String _profileId;

	public JsJob(Job job, String ownerPid) {
		DateTimeZone eventTz = DateTimeZone.forID(job.getTimezone());
		DateTimeFormatter ymdhmsZoneFmt = DateTimeUtils.createYmdHmsFormatter(eventTz);		
		
		this.jobId = job.getJobId();
		this.companyId = job.getCompanyId();
		this.operatorId = job.getOperatorId();
		this.customerId = job.getCustomerId();
		this.customerStatId = job.getCustomerStatId();
		
		CalendarUtils.EventBoundary eventBoundary = CalendarUtils.toEventBoundaryForRead(false, job.getStartDate(), job.getEndDate(), DateTimeZone.forID(job.getTimezone()));
		startDate = ymdhmsZoneFmt.print(eventBoundary.start);
		endDate = ymdhmsZoneFmt.print(eventBoundary.end);
		timezone = eventBoundary.timezone.getID();
		
		this.activityId = job.getActivityId();
		this.title = job.getTitle();
		this.description = job.getDescription();
		this.eventId = job.getEventId();
		
		this.domainId = job.getDomainId();
		this.ticketId = job.getTicketId();
		this.number = job.getNumber();
		this.causalId = job.getCausalId();
		
		attachments = new ArrayList<>(job.getAttachments().size());
		for (JobAttachment att : job.getAttachments()) {
			Attachment jsatt = new Attachment();
			jsatt.id = att.getJobAttachmentId();
			jsatt.name = att.getFileName();
			jsatt.size = att.getSize();
			attachments.add(jsatt);
		}

		this._profileId = ownerPid;
	}

	public static Job createJob(JsJob js, String domainId) {
		Job job = new Job();
		
		job.setJobId(js.jobId);
		job.setCompanyId(js.companyId);
		job.setOperatorId(js.operatorId);
		job.setCustomerId(js.customerId);
		job.setCustomerStatId(js.customerStatId);
		
		// Incoming fields are in a precise timezone, so we need to instantiate
		// the formatter specifying the right timezone to use.
		// Then DateTime objects are automatically translated to UTC
		DateTimeZone eventTz = DateTimeZone.forID(js.timezone);
		DateTime eventStart = DateTimeUtils.parseYmdHmsWithZone(js.startDate, eventTz);
		DateTime eventEnd = DateTimeUtils.parseYmdHmsWithZone(js.endDate, eventTz);
		
		CalendarUtils.EventBoundary eventBoundary = CalendarUtils.toEventBoundaryForWrite(false, eventStart, eventEnd, eventTz);
		job.setDatesAndTimes(eventBoundary.timezone.getID(), eventBoundary.start, eventBoundary.end);
		
		job.setActivityId(js.activityId);
		job.setTitle(js.title);
		job.setDescription(js.description);
		job.setEventId(js.eventId);
		job.setCausalId(js.causalId);
		
		job.setDomainId(domainId);
		job.setTicketId(js.ticketId);
		job.setNumber(js.number);
		
		// Attachment needs to be treated outside this class in order to have complete access to their streams
		
		return job;
	}
	
	public static class Attachment {
		public String id;
		public String name;
		public Long size;
		public String _uplId;
	}
}
