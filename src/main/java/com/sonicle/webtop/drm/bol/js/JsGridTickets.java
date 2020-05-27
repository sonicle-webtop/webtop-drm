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
import com.sonicle.webtop.drm.bol.OViewTicket;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author dnllr
 */
public class JsGridTickets {

	public String ticketId;
	public Integer companyId;
	public String companyDescription;
	public String fromOperatorId;
	public String fromOperatorDescription;
	public String toOperatorId;
	public String toOperatorDescription;	
	public String customerId;
	public String customerDescription;
	public String customerStatId;
	public String customerStatDescription;
	public String date;
	public String timezone;
	public Integer ticketCategoryId;
	public String ticketCategoryDescription;
	public Integer statusId;
	public String statusDescription;	
	public String priorityId;
	public String number;	
	public String title;

	public String _profileId;

	public JsGridTickets(OViewTicket tckt, DateTimeZone profileTz, String ownerPid) {
		DateTimeZone eventTz = DateTimeZone.forID(tckt.getTimezone());
		DateTimeFormatter ymdhmsZoneFmt = DateTimeUtils.createYmdHmsFormatter(eventTz);
		
		this.ticketId = tckt.getTicketId();
		this.companyId = tckt.getCompanyId();
		this.companyDescription = tckt.getCompanyDescription();
		this.fromOperatorId = tckt.getFromOperatorId();
		this.fromOperatorDescription = tckt.getFromOperatorDescription();
		this.toOperatorId = tckt.getToOperatorId();
		this.toOperatorDescription = tckt.getToOperatorDescription();		
		this.customerId = tckt.getCustomerId();
		this.customerDescription = tckt.getCustomerDescription();
		this.customerStatId = tckt.getCustomerStatId();
		this.customerStatDescription = tckt.getCustomerStatDescription();
		this.ticketCategoryId = tckt.getTicketCategoryId();
		this.ticketCategoryDescription = tckt.getTicketCategoryDescription();
		this.title = tckt.getTitle();
		this.number = tckt.getNumber();
		
		// CalendarUtils.EventBoundary eventBoundary = CalendarUtils.toEventBoundaryForRead(false, job.getStartDate(), job.getEndDate(), DateTimeZone.forID(job.getTimezone()));
		this.date = ymdhmsZoneFmt.print(tckt.getDate());
		// endDate = ymdhmsZoneFmt.print(eventBoundary.end);
		this.timezone = tckt.getTimezone();
		
		this.statusId = tckt.getStatusId();
		this.statusDescription = tckt.getStatusDescription();
		this.priorityId = tckt.getPriorityId();
		
		this._profileId = ownerPid;

	}

}
