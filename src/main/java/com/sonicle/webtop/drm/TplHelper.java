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

import com.sonicle.commons.web.json.MapItem;
import com.sonicle.webtop.core.app.WT;
import com.sonicle.webtop.core.sdk.UserProfileId;
import com.sonicle.webtop.drm.bol.OLeaveRequest;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Locale;
import javax.mail.internet.AddressException;
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

	public static String buildLeaveRequestBody(Locale locale, OLeaveRequest lr) throws IOException, TemplateException, AddressException {
		DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");
		
		MapItem i18n = new MapItem();
		i18n.put("whenStart", WT.lookupResource(SERVICE_ID, locale, DrmLocale.TPL_EMAIL_REQUEST_WHEN_START));
		i18n.put("whenEnd", WT.lookupResource(SERVICE_ID, locale, DrmLocale.TPL_EMAIL_REQUEST_WHEN_END));
		i18n.put("notes", WT.lookupResource(SERVICE_ID, locale, DrmLocale.TPL_EMAIL_REQUEST_NOTES));

		MapItem req = new MapItem();
		req.put("user", WT.getUserData(new UserProfileId(lr.getDomainId(), lr.getUserId())).getDisplayName());
		req.put("startDate", lr.getFromDate().toString(fmt) + " " + ((lr.getFromHour() != null) ? lr.getFromHour() : ""));
		req.put("endDate", lr.getToDate().toString(fmt) + " " + ((lr.getToHour() != null) ? lr.getToHour() : ""));
		req.put("notes", (lr.getNotes() != null) ? lr.getNotes() : "");
		req.put("occurs", null);

		MapItem vars = new MapItem();
		vars.put("i18n", i18n);
		vars.put("request", req);

		return WT.buildTemplate(SERVICE_ID, "tpl/email/leaveRequest-body.html", vars);
	}
}
