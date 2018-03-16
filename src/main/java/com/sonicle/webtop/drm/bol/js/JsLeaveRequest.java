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

import com.sonicle.webtop.drm.model.LeaveRequest;
import com.sonicle.webtop.drm.model.LeaveRequestDocument;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.LocalDate;

/**
 *
 * @author lssndrvs
 */
public class JsLeaveRequest {

	public Integer leaveRequestId;
	public String domainId;
	public Integer companyId;
	public String userId;
	public String managerId;
	public String type;
	public String fromDate;
	public String toDate;
	public String fromHour;
	public String toHour;
	public String status;
	public String notes;
	public Boolean result;
	public Boolean cancRequest;
	public String cancReason;
	public Boolean canceResult;

	public List<JsGridLeaveRequestDocument> documents = new ArrayList();

	public JsLeaveRequest(LeaveRequest lr) {		
		this.leaveRequestId = lr.getLeaveRequestId();
		this.domainId = lr.getDomainId();
		this.companyId = lr.getCompanyId();
		this.userId = lr.getUserId();
		this.managerId = lr.getManagerId();
		this.type = lr.getType();
		this.fromDate = lr.getFromDate().toString();
		this.toDate = lr.getToDate().toString();
		this.fromHour = lr.getFromHour();
		this.toHour = lr.getToHour();
		this.status = lr.getStatus();
		this.notes = lr.getNotes();
		this.result = lr.getResult();
		this.cancRequest = lr.getCancRequest();
		this.cancReason = lr.getCancReason();
		this.canceResult = lr.getCancResult();
		
		for (LeaveRequestDocument lrDoc : lr.getDocuments()) {
			this.documents.add(new JsGridLeaveRequestDocument(lrDoc));
		}
	}

	public static LeaveRequest createLeaveRequest(JsLeaveRequest js) {

		LeaveRequest lr = new LeaveRequest();
		lr.setLeaveRequestId(js.leaveRequestId);
		lr.setDomainId(js.domainId);
		lr.setCompanyId(js.companyId);
		lr.setUserId(js.userId);
		lr.setManagerId(js.managerId);
		lr.setType(js.type);
		lr.setFromDate(new LocalDate(js.fromDate));
		lr.setToDate(new LocalDate(js.toDate));
		lr.setFromHour(js.fromHour);
		lr.setToHour(js.toHour);
		lr.setStatus(js.status);
		lr.setNotes(js.notes);
		lr.setResult(js.result);
		lr.setCancRequest(js.cancRequest);
		lr.setCancReason(js.cancReason);
		lr.setCancResult(js.canceResult);
		
		for (JsGridLeaveRequestDocument jsLrDoc : js.documents) {

			lr.getDocuments().add(JsGridLeaveRequestDocument.createLeaveRequestDocument(jsLrDoc));

		}

		return lr;
	}
}
