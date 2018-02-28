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
package com.sonicle.webtop.drm;

/**
 *
 * @author lssndrvs
 */
public class DrmSettings {
	//creo le constati che servono per le impostazioni sia per utente che per servizio

	/**
	 * [system + domain] [boolean] Enable or Disable use Statistic Customer
	 */
	public static final String USE_STATISTIC_CUSTOMER = "customer.usestatistic";

	public static final String PRINT_DAYS_TRANSFERT = "workreport.daystrasfert.print";

	public static final String PRINT_TRANSFERT_DESCRIPTION = "workreport.transfert.print.description";

	public static final String PRINT_SIGNATURE = "workreport.print.signature";

	public static final String ROUNDING_HOUR = "workreport.roundinghour";
	
	public static final String TRACKING = "workreport.tracking";
	
	public static final String MAIL_TRACKING = "workreport.tracking.mail";
	
	public static final String CLOUD_TRACKING = "workreport.tracking.cloud";
	
	public static final String DEFAULT_APPLY_SIGNATURE = "workreport.default.applysignature";
	
	public static final String DEFAULT_CHARGE_TO = "workreport.default.chargeto";
	
	public static final String DEFAULT_FREE_SUPPORT = "workreport.default.freesupport";
	
	public static final String DEFAULT_DOC_STATUS_ID = "workreport.default.docstatusid";
	
	public static final String TIMETABLE_ALLOWED_ADDRESSES = "timetable.allowed.addresses";
	
	public static final String TIMETABLE_ALLOWED_USERS = "timetable.allowed.users";
	
	public static final String TIMETABLE_STAFF_OFFICE_EMAIL = "timetable.staff_office_email";
	
	public static final String TIMETABLE_REQUEST_HOLIDAYS_PERMITS_PREVIOUS_DATES = "timetable_request_holidays_premits_previous_dates";

	public static final String TIMETABLE_TOT_TOLERANCE = "timetable.total.tolerance.in.minutes";
	
	public static final String TIMETABLE_ROUNDING = "timetable.rounding";
	
	public static final String TIMETABLE_MINIMUM_EXTRAORDINARY = "timetable.minimum.extraordinary";
	
	public static final String TIMETABLE_BREAK_ANOMALY = "timetable.break.anomaly";
	
	public static final String TIMETABLE_READONLY_EVENTS = "timetable.readonly_events";
}
