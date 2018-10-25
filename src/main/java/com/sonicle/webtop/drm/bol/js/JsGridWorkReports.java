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

import com.sonicle.webtop.drm.bol.OWorkReport;
import org.joda.time.DateTimeZone;

/**
 *
 * @author lssndrvs
 */
public class JsGridWorkReports {

	public String workReportId;
	public Integer number;
	public Integer year;
	public Integer companyId;
	public String operatorId;
	public Integer docStatusId;
	public String customerId;
	public String customerStatId;
	public String fromDate;
	public String toDate;
	public String referenceNo;
	public Integer causalId;
	public String notes;
	public String description;
	public Boolean chargeTo;
	public Integer businessTripId;
	public Boolean freeSupport;

	public String _profileId;

	public JsGridWorkReports(OWorkReport report, DateTimeZone profileTz, String ownerPid) {
		this.workReportId = report.getWorkReportId();
		this.number = report.getNumber();
		this.year = report.getYear();
		this.companyId = report.getCompanyId();
		this.operatorId = report.getOperatorId();
		this.customerId = report.getCustomerId();
		this.customerStatId = report.getCustomerStatId();
		this.docStatusId = report.getDocStatusId();
		this.fromDate = report.getFromDate().toString();
		this.toDate = report.getToDate().toString();
		this.referenceNo = report.getReferenceNo();
		this.causalId = report.getCausalId();
		this.notes = report.getNotes();
		this.description = report.getDescription();
		this.chargeTo = report.getChargeTo();
		this.businessTripId = report.getBusinessTripId();
		this.freeSupport = report.getFreeSupport();

		this._profileId = ownerPid;

	}

}
