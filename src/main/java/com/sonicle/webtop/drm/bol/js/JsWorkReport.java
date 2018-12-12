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
import com.sonicle.webtop.drm.model.WorkReportRow;
import com.sonicle.webtop.drm.model.WorkReport;
import com.sonicle.webtop.drm.model.WorkReportAttachment;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

/**
 *
 * @author lssndrvs
 */
public class JsWorkReport {

	public String workReportId;
	public Integer number;
	public Integer year;
	public Integer companyId;
	public String operatorId;
	public Integer docStatusId;
	public Integer contactId;
	public String customerId;
	public String customerStatId;
	public String fromDate;
	public String toDate;
	public String referenceNo;
	public Integer causalId;
	public String ddtNo;
	public String ddtDate;
	public String notes;
	public String description;
	public Boolean applySignature;
	public Boolean chargeTo;
	public Boolean freeSupport;
	public Integer businessTripId;
	public Integer dayTrasfert;
	public Integer eventId;

	public List<JsGridWorkReportRow> details = new ArrayList();
	public List<Attachment> attachments = new ArrayList();

	public String _profileId;

	public JsWorkReport(WorkReport report, DateTimeZone profileTz, String ownerPid) {
		this.workReportId = report.getWorkReportId();
		this.number = report.getNumber();
		this.year = report.getYear();
		this.companyId = report.getCompanyId();
		this.operatorId = report.getOperatorId();
		this.docStatusId = report.getDocStatusId();
		this.contactId = report.getContactId();
		this.customerId = report.getCustomerId();
		this.customerStatId = report.getCustomerStatId();
		this.fromDate = report.getFromDate().toString();
		this.toDate = report.getToDate().toString();
		this.referenceNo = report.getReferenceNo();
		this.causalId = report.getCausalId();
		this.ddtNo = report.getDdtNo();
		this.ddtDate = (null != report.getDdtDate()) ? report.getDdtDate().toString() : null;
		this.notes = report.getNotes();
		this.description = report.getDescription();
		this.applySignature = report.getApplySignature();
		this.chargeTo = report.getChargeTo();
		this.freeSupport = report.getFreeSupport();
		this.businessTripId = report.getBusinessTripId();
		this.dayTrasfert = report.getDayTrasfert();
		this.eventId = report.getEventId();

		for (WorkReportRow wrkDetail : report.getDetails()) {
			this.details.add(new JsGridWorkReportRow(wrkDetail));
		}
		
		attachments = new ArrayList<>(report.getAttachments().size());
		for (WorkReportAttachment att : report.getAttachments()) {
			Attachment jsatt = new Attachment();
			jsatt.id = att.getWorkReportAttachmentId();
			jsatt.name = att.getFileName();
			jsatt.size = att.getSize();
			attachments.add(jsatt);
		}

		this._profileId = ownerPid;
	}

	public static WorkReport createWorkReport(JsWorkReport js, DateTimeZone profileTz) {

		WorkReport rw = new WorkReport();
		rw.setWorkReportId(js.workReportId);
		rw.setNumber(js.number);
		rw.setYear(js.year);
		rw.setCompanyId(js.companyId);
		rw.setOperatorId(js.operatorId);
		rw.setDocStatusId(js.docStatusId);
		rw.setContactId(js.contactId);
		rw.setCustomerId(js.customerId);
		rw.setCustomerStatId(js.customerStatId);
		rw.setFromDate(new LocalDate(js.fromDate));
		rw.setToDate(new LocalDate(js.toDate));
		rw.setReferenceNo(js.referenceNo);
		rw.setCausalId(js.causalId);
		rw.setDdtNo(js.ddtNo);
		if(null != js.ddtDate){
			rw.setDdtDate(new LocalDate(js.ddtDate));
		}
		rw.setNotes(js.notes);
		rw.setDescription(js.description);
		rw.setApplySignature(js.applySignature);
		rw.setChargeTo(js.chargeTo);
		rw.setFreeSupport(js.freeSupport);
		rw.setBusinessTripId(js.businessTripId);
		rw.setDayTrasfert(js.dayTrasfert);
		rw.setEventId(js.eventId);

		for (JsGridWorkReportRow jsWrkDetail : js.details) {

			rw.getDetails().add(JsGridWorkReportRow.createWorkReportRow(jsWrkDetail));

		}
		
		// Attachment needs to be treated outside this class in order to have complete access to their streams
		
		return rw;
	}
	
	public static class Attachment {
		public String id;
		public String name;
		public Long size;
		public String _uplId;
	}
}
