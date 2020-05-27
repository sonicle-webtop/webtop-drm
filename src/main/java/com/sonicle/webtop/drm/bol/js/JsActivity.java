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

import com.sonicle.webtop.drm.model.Activity;
import com.sonicle.webtop.drm.model.ActivityGroupAssociation;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dnllr
 */
public class JsActivity {

	public Integer activityId;
	public String externalId;
	public String description;
	public String domainId;
	public Boolean customer;
	public Boolean timetable;
	public Boolean jobs;
	public Boolean opportunities;
	public Boolean exportable;
	
	public List<ActivityGroupAssociation> associatedGroups = new ArrayList();

	public JsActivity(Activity act) {
		this.activityId = act.getActivityId();
		this.externalId = act.getExternalId();
		this.description = act.getDescription();
		this.domainId = act.getDomainId();
		this.customer = act.getCustomer();
		this.timetable = act.getTimetable();
		this.jobs = act.getJobs();
		this.opportunities = act.getOpportunities();
		this.exportable = act.getExportable();
		
		this.associatedGroups = act.getAssociatedProfiles();
	}

	public static Activity createActivity(JsActivity js, String domainId) {
		Activity newAct = new Activity();

		newAct.setActivityId(js.activityId);
		newAct.setExternalId(js.externalId);
		newAct.setDescription(js.description);
		newAct.setDomainId(domainId);
		newAct.setCustomer(js.customer);
		newAct.setTimetable(js.timetable);
		newAct.setJobs(js.jobs);
		newAct.setOpportunities(js.opportunities);
		newAct.setExportable(js.exportable);
		
		newAct.setAssociatedProfiles(js.associatedGroups);

		return newAct;
	}
}