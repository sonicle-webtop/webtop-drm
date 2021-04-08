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

import com.sonicle.commons.time.DateTimeUtils;
import com.sonicle.commons.web.json.MapItem;
import com.sonicle.webtop.core.CoreManager;
import com.sonicle.webtop.core.app.WT;
import com.sonicle.webtop.core.model.MasterData;
import com.sonicle.webtop.core.sdk.UserProfileId;
import static com.sonicle.webtop.drm.Service.logger;
import com.sonicle.webtop.drm.bol.OLeaveRequest;
import com.sonicle.webtop.drm.bol.OTicket;
import com.sonicle.webtop.drm.bol.OViewTicket;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Locale;
import jakarta.mail.internet.AddressException;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author lssndrvs
 */
public class TplHelper {

	private static final String SERVICE_ID = "com.sonicle.webtop.drm";

	public static String buildLeaveRequestTitle(Locale locale, OLeaveRequest lr) {
		return WT.lookupResource(SERVICE_ID, locale, MessageFormat.format(DrmLocale.EMAIL_REQUEST_SUBJECT_X, lr.getType()));
	}
	
	public static String buildLeaveRequestCancellationTitle(Locale locale, OLeaveRequest lr) {
		return WT.lookupResource(SERVICE_ID, locale, MessageFormat.format(DrmLocale.EMAIL_REQUEST_CANCELLATION_SUBJECT_X, lr.getType()));
	}

	public static String buildLeaveRequestBody(Locale locale, OLeaveRequest lr, String recipientEmail, boolean answer, String servicePublicUrl) throws IOException, TemplateException, AddressException {
		DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");
		
		MapItem i18n = new MapItem();
		i18n.put("whenStart", WT.lookupResource(SERVICE_ID, locale, DrmLocale.TPL_EMAIL_REQUEST_WHEN_START));
		i18n.put("whenEnd", WT.lookupResource(SERVICE_ID, locale, DrmLocale.TPL_EMAIL_REQUEST_WHEN_END));
		i18n.put("notes", WT.lookupResource(SERVICE_ID, locale, DrmLocale.TPL_EMAIL_REQUEST_NOTES));
		i18n.put("approve", WT.lookupResource(SERVICE_ID, locale, DrmLocale.TPL_EMAIL_REQUEST_APPROVE));
		i18n.put("approveYes", WT.lookupResource(SERVICE_ID, locale, DrmLocale.TPL_EMAIL_REQUEST_APPROVE_YES));
		i18n.put("approveNo", WT.lookupResource(SERVICE_ID, locale, DrmLocale.TPL_EMAIL_REQUEST_APPROVE_NO));

		MapItem req = new MapItem();
		req.put("user", WT.getUserData(new UserProfileId(lr.getDomainId(), lr.getUserId())).getDisplayName());
		req.put("startDate", lr.getFromDate().toString(fmt) + " " + ((lr.getFromHour() != null) ? lr.getFromHour() : ""));
		req.put("endDate", lr.getToDate().toString(fmt) + " " + ((lr.getToHour() != null) ? lr.getToHour() : ""));
		req.put("notes", (lr.getNotes() != null) ? lr.getNotes() : "");

		MapItem vars = new MapItem();
		vars.put("i18n", i18n);
		vars.put("answer", answer);
		vars.put("request", req);
		vars.put("approveYesUrl", DrmManager.buildLeaveRequestReplyPublicUrl(servicePublicUrl, lr.getLeaveRequestId().toString(), recipientEmail, "new", "yes"));
		vars.put("approveNoUrl", DrmManager.buildLeaveRequestReplyPublicUrl(servicePublicUrl, lr.getLeaveRequestId().toString(), recipientEmail, "new", "no"));

		return WT.buildTemplate(SERVICE_ID, "tpl/email/leaveRequest-body.html", vars);
	}
	
	public static String buildLeaveRequestCancellationBody(Locale locale, OLeaveRequest lr, String recipientEmail, boolean answer, String servicePublicUrl) throws IOException, TemplateException, AddressException {
		DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");
		
		MapItem i18n = new MapItem();
		i18n.put("whenStart", WT.lookupResource(SERVICE_ID, locale, DrmLocale.TPL_EMAIL_REQUEST_WHEN_START));
		i18n.put("whenEnd", WT.lookupResource(SERVICE_ID, locale, DrmLocale.TPL_EMAIL_REQUEST_WHEN_END));
		i18n.put("notes", WT.lookupResource(SERVICE_ID, locale, DrmLocale.TPL_EMAIL_REQUEST_NOTES));
		i18n.put("approve", WT.lookupResource(SERVICE_ID, locale, DrmLocale.TPL_EMAIL_REQUEST_APPROVE));
		i18n.put("approveYes", WT.lookupResource(SERVICE_ID, locale, DrmLocale.TPL_EMAIL_REQUEST_APPROVE_YES));
		i18n.put("approveNo", WT.lookupResource(SERVICE_ID, locale, DrmLocale.TPL_EMAIL_REQUEST_APPROVE_NO));

		MapItem req = new MapItem();
		req.put("user", WT.getUserData(new UserProfileId(lr.getDomainId(), lr.getUserId())).getDisplayName());
		req.put("startDate", lr.getFromDate().toString(fmt) + " " + ((lr.getFromHour() != null) ? lr.getFromHour() : ""));
		req.put("endDate", lr.getToDate().toString(fmt) + " " + ((lr.getToHour() != null) ? lr.getToHour() : ""));
		req.put("notes", (lr.getCancReason() != null) ? lr.getCancReason() : "");

		MapItem vars = new MapItem();
		vars.put("i18n", i18n);
		vars.put("answer", answer);
		vars.put("request", req);
		vars.put("approveYesUrl", DrmManager.buildLeaveRequestReplyPublicUrl(servicePublicUrl, lr.getLeaveRequestId().toString(), recipientEmail, "delete", "yes"));
		vars.put("approveNoUrl", DrmManager.buildLeaveRequestReplyPublicUrl(servicePublicUrl, lr.getLeaveRequestId().toString(), recipientEmail, "delete", "no"));

		return WT.buildTemplate(SERVICE_ID, "tpl/email/leaveRequest-body.html", vars);
	}
	
	public static String buildTicketNotificationSubject(Locale locale, OViewTicket oVwTckt) {
		String subject = MessageFormat.format(WT.lookupResource(SERVICE_ID, locale, DrmLocale.TPL_TICKET_NOTIFICATION_EMAIL_SUBJECT), oVwTckt.getNumber(), oVwTckt.getCustomerDescription());
		
		return subject;
	}
	
	public static String buildTicketNotificationBody(Locale locale, OViewTicket oVwTckt) throws IOException, TemplateException {
		DateTimeZone eventTz = DateTimeZone.forID(oVwTckt.getTimezone());
		DateTimeFormatter hmsZoneFmt = DateTimeUtils.createFormatter("HH:mm:ss", eventTz);
		DateTimeFormatter dmyZoneFmt = DateTimeUtils.createFormatter("dd/MM/yyyy", eventTz);
		
		try {
		CoreManager coreMgr = WT.getCoreManager();
		
		MasterData md = coreMgr.getMasterData(oVwTckt.getCustomerId());
		
		MapItem label = new MapItem();
		label.put("date", WT.lookupResource(SERVICE_ID, locale, DrmLocale.TPL_TICKET_NOTIFICATION_EMAIL_BODY_LABEL_DATE));
		label.put("time", WT.lookupResource(SERVICE_ID, locale, DrmLocale.TPL_TICKET_NOTIFICATION_EMAIL_BODY_LABEL_TIME));
		label.put("customer", WT.lookupResource(SERVICE_ID, locale, DrmLocale.TPL_TICKET_NOTIFICATION_EMAIL_BODY_LABEL_CUSTOMER));
		label.put("statCustomer", WT.lookupResource(SERVICE_ID, locale, DrmLocale.TPL_TICKET_NOTIFICATION_EMAIL_BODY_LABEL_STAT_CUSTOMER));
		label.put("title", WT.lookupResource(SERVICE_ID, locale, DrmLocale.TPL_TICKET_NOTIFICATION_EMAIL_BODY_LABEL_TITLE));
		label.put("description", WT.lookupResource(SERVICE_ID, locale, DrmLocale.TPL_TICKET_NOTIFICATION_EMAIL_BODY_LABEL_DESCRIPTION));
		label.put("detail", WT.lookupResource(SERVICE_ID, locale, DrmLocale.TPL_TICKET_NOTIFICATION_EMAIL_BODY_LABEL_DETAIL));
		label.put("telephone", WT.lookupResource(SERVICE_ID, locale, DrmLocale.TPL_TICKET_NOTIFICATION_EMAIL_BODY_LABEL_TELEPHONE));
		label.put("email", WT.lookupResource(SERVICE_ID, locale, DrmLocale.TPL_TICKET_NOTIFICATION_EMAIL_BODY_LABEL_EMAIL));

		MapItem field = new MapItem();
		field.put("date", dmyZoneFmt.print(oVwTckt.getDate()));
		field.put("time", hmsZoneFmt.print(oVwTckt.getDate()));
		field.put("customer", oVwTckt.getCustomerDescription());
		field.put("statCustomer", oVwTckt.getCustomerStatDescription());
		field.put("title", oVwTckt.getTitle());
		field.put("description", (oVwTckt.getDescription() == null) ? "" : oVwTckt.getDescription());
		field.put("address", (md.getAddress() == null) ? "" : md.getAddress());
		field.put("postalCode", (md.getPostalCode() == null) ? "" : md.getPostalCode());
		field.put("city", (md.getCity() == null) ? "" : md.getCity());
		field.put("country", (md.getCountry() == null) ? "" : md.getCountry());
		field.put("telephone", (md.getTelephone() == null) ? "" : md.getTelephone());
		field.put("email", (md.getEmail() == null) ? "" : md.getEmail());

		MapItem vars = new MapItem();
		vars.put("label", label);
		vars.put("field", field);

		return WT.buildTemplate(SERVICE_ID, "tpl/email/notificationTicket.html", vars);
		
		} catch (Exception ex) {
			logger.error("Problem build body Ticket Notify Email", ex);
		}
		return StringUtils.EMPTY;
	}
	
}
