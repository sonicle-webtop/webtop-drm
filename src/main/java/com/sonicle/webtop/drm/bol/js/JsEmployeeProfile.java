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

import com.sonicle.webtop.drm.model.EmployeeProfile;

/**
 *
 * @author lssndrvs
 */
public class JsEmployeeProfile {

	public Integer id;
	public String domainId;
	public String userId;
	public String number;
	public String tolerance;
	public Boolean extraordinary;
	public Boolean onlyPresence;
	public Integer hourProfileId;
	public String headquartersCode;
	public Boolean noStamping;
	public Integer minimumNumberOfHoursPerTicket;
	public String stampingMode;


	public JsEmployeeProfile(EmployeeProfile ep) {
		this.id = ep.getId();
		this.domainId = ep.getDomainId();
		this.userId = ep.getUserId();
		this.number = ep.getNumber();
		this.tolerance = ep.getTolerance();
		this.extraordinary = ep.getExtraordinary();
		this.onlyPresence = ep.getOnlyPresence();
		this.hourProfileId = ep.getHourProfileId();
		this.headquartersCode = ep.getHeadquartersCode();
		this.noStamping = ep.getNoStamping();
		this.minimumNumberOfHoursPerTicket = ep.getMinimumNumberOfHoursPerTicket();
		this.stampingMode = ep.getStampingMode();
	}

	public static EmployeeProfile createEmployeeProfile(JsEmployeeProfile js) {

		EmployeeProfile newHp = new EmployeeProfile();

		newHp.setId(js.id);
		newHp.setDomainId(js.domainId);
		newHp.setUserId(js.userId);
		newHp.setNumber(js.number);
		newHp.setTolerance(js.tolerance);
		newHp.setExtraordinary(js.extraordinary);
		newHp.setOnlyPresence(js.onlyPresence);
		newHp.setHourProfileId(js.hourProfileId);
		newHp.setHeadquartersCode(js.headquartersCode);
		newHp.setNoStamping(js.noStamping);
		newHp.setMinimumNumberOfHoursPerTicket(js.minimumNumberOfHoursPerTicket);
		newHp.setStampingMode(js.stampingMode);

		return newHp;
	}

}
