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

import com.sonicle.webtop.core.app.WT;
import com.sonicle.webtop.core.sdk.UserProfileId;
import com.sonicle.webtop.drm.bol.OEmployeeProfile;
import com.sonicle.webtop.drm.bol.OTimetableStamp;
import org.joda.time.DateTime;

/**
 *
 * @author lssndrvs
 */
public class JsGridTimetableStampList {
		
	public int id;
	public String domainId;
	public String userId;
	public String type;
	public String date;
	public String entrance;
	public String exit;
	
	public JsGridTimetableStampList(OTimetableStamp ts){
		this.id = ts.getId();
		this.domainId = ts.getDomainId();
		this.userId = ts.getUserId();
		this.type = ts.getType();
		this.date = (null != ts.getEntrance()) ? ((ts.getEntrance().getDayOfMonth()< 10 ? "0" + ts.getEntrance().getDayOfMonth() : ts.getEntrance().getDayOfMonth()) + "/" + (ts.getEntrance().getMonthOfYear() < 10 ? "0" + ts.getEntrance().getMonthOfYear() : ts.getEntrance().getMonthOfYear()) + "/" + (ts.getEntrance().getYear())) : ("");
		this.entrance = (null != ts.getEntrance()) ? ((ts.getEntrance().getHourOfDay() < 10 ? "0" + ts.getEntrance().getHourOfDay() : ts.getEntrance().getHourOfDay()) + ":" + (ts.getEntrance().getMinuteOfHour() < 10 ? "0" + ts.getEntrance().getMinuteOfHour() : ts.getEntrance().getMinuteOfHour())) : ("");
		this.exit = (null != ts.getExit()) ? ((ts.getExit().getHourOfDay() < 10 ? "0" + ts.getExit().getHourOfDay() : ts.getExit().getHourOfDay()) + ":" + (ts.getExit().getMinuteOfHour() < 10 ? "0" + ts.getExit().getMinuteOfHour() : ts.getExit().getMinuteOfHour())) : ("");
		
	}
}
