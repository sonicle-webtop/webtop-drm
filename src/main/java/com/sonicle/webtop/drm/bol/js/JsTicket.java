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
import com.sonicle.webtop.drm.model.Ticket;
import com.sonicle.webtop.drm.model.TicketAttachment;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author dnllr
 */
public class JsTicket {

	public String ticketId;
	public Integer companyId;
	public String fromOperatorId;
	public String toOperatorId;
	public String customerId;
	public String customerStatId;
	public String date;
	public String timezone;
	public Integer ticketCategoryId;
	public String title;
	public String description;
	public Integer eventId;
	public String domainId;
	public Integer statusId;
	public String priorityId;
	public String release;
	public String environment;
	public String suggestion;
	public String simulation;
	public String number;
	
	public List<Attachment> attachments = new ArrayList();

	public String _profileId;

	public JsTicket(Ticket tckt, String ownerPid) {
		DateTimeZone eventTz = DateTimeZone.forID(tckt.getTimezone());
		DateTimeFormatter ymdhmsZoneFmt = DateTimeUtils.createYmdHmsFormatter(eventTz);		
		
		this.ticketId = tckt.getTicketId();
		this.companyId = tckt.getCompanyId();
		this.fromOperatorId = tckt.getFromOperatorId();
		this.toOperatorId = tckt.getToOperatorId();
		this.customerId = tckt.getCustomerId();
		this.customerStatId = tckt.getCustomerStatId();
		
		// CalendarUtils.EventBoundary eventBoundary = CalendarUtils.toEventBoundaryForRead(false, job.getStartDate(), job.getEndDate(), DateTimeZone.forID(job.getTimezone()));
		this.date = ymdhmsZoneFmt.print(tckt.getDate());
		this.timezone = tckt.getTimezone();
		
		this.ticketCategoryId = tckt.getTicketCategoryId();
		this.statusId = tckt.getStatusId();
		this.priorityId = tckt.getPriorityId();
		this.title = tckt.getTitle();
		this.description = tckt.getDescription();
		this.eventId = tckt.getEventId();
		this.release = tckt.getRelease();
		this.environment = tckt.getEnvironment();
		this.suggestion = tckt.getSuggestion();
		this.simulation = tckt.getSimulation();
		this.number = tckt.getNumber();
		this.domainId = tckt.getDomainId();
		
		attachments = new ArrayList<>(tckt.getAttachments().size());
		for (TicketAttachment att : tckt.getAttachments()) {
			Attachment jsatt = new Attachment();
			jsatt.id = att.getTicketAttachmentId();
			jsatt.name = att.getFileName();
			jsatt.size = att.getSize();
			attachments.add(jsatt);
		}

		this._profileId = ownerPid;
	}

	public static Ticket createTicket(JsTicket js, String domainId) {
		Ticket tckt = new Ticket();
		
		tckt.setTicketId(js.ticketId);
		tckt.setCompanyId(js.companyId);
		tckt.setFromOperatorId(js.fromOperatorId);
		tckt.setToOperatorId(js.toOperatorId);
		tckt.setCustomerId(js.customerId);
		tckt.setCustomerStatId(js.customerStatId);
		
		// Incoming fields are in a precise timezone, so we need to instantiate
		// the formatter specifying the right timezone to use.
		// Then DateTime objects are automatically translated to UTC
		DateTimeZone eventTz = DateTimeZone.forID(js.timezone);
		DateTime event = DateTimeUtils.parseYmdHmsWithZone(js.date, eventTz);
		
		// CalendarUtils.EventBoundary eventBoundary = CalendarUtils.toEventBoundaryForWrite(false, eventStart, eventEnd, eventTz);
		// tckt.setDateAndTimes(eventBoundary.timezone.getID(), eventBoundary.start, eventBoundary.end);
		tckt.setDate(event);
		tckt.setTimezone(js.timezone);
		
		tckt.setTicketCategoryId(js.ticketCategoryId);
		tckt.setPriorityId(js.priorityId);
		tckt.setStatusId(js.statusId);
		tckt.setTitle(js.title);
		tckt.setDescription(js.description);
		tckt.setRelease(js.release);
		tckt.setEnvironment(js.environment);
		tckt.setSuggestion(js.suggestion);
		tckt.setSimulation(js.simulation);
		tckt.setEventId(js.eventId);
		tckt.setNumber(js.number);
		
		tckt.setDomainId(domainId);
		
		// Attachment needs to be treated outside this class in order to have complete access to their streams
		
		return tckt;
	}
	
	public static class Attachment {
		public String id;
		public String name;
		public Long size;
		public String _uplId;
	}
}
