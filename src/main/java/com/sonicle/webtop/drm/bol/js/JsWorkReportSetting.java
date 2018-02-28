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
 * types.
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

import com.sonicle.webtop.drm.model.BusinessTrip;
import com.sonicle.webtop.drm.model.WorkReportSetting;
import com.sonicle.webtop.drm.model.WorkType;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lssndrvs
 */
public class JsWorkReportSetting {

	public String id = "wr";
	public Integer workReportSettingId;
	public String domainId;
	public Integer workReportSequence;
	public String warranty;
	public Boolean printDaysTransfert;
	public Boolean printTransfertDescription;
	public Boolean printSignature;
	public Integer roundingHour;
	public Boolean tracking;
	public Boolean trackingMail;
	public Boolean trackingCloud;
	public Boolean defaultApplySignature;
	public Boolean defaultChargeTo;
	public Boolean defaultFreeSupport;
	public String defaultStatus;

	public List<JsGridWorkType> types = new ArrayList();
	public List<JsGridBusinessTrip> trips = new ArrayList();

	public JsWorkReportSetting(WorkReportSetting setting) {

		this.workReportSettingId = setting.getWorkReportSettingId();
		this.domainId = setting.getDomainId();
		this.warranty = setting.getWarrantyText();
		this.workReportSequence = setting.getWorkReportSequence();

		for (WorkType type : setting.getTypes()) {
			this.types.add(new JsGridWorkType(type));
		}

		for (BusinessTrip trip : setting.getTrips()) {
			this.trips.add(new JsGridBusinessTrip(trip));
		}
		
		this.printDaysTransfert = false;
		this.printTransfertDescription = false;
		this.printSignature = false;
		this.tracking = false;
		this.trackingMail = false;
		this.trackingCloud = false;
		this.defaultApplySignature = false;
		this.defaultChargeTo = false;
		this.defaultFreeSupport = false;
		this.defaultStatus = null;

	}

	public static WorkReportSetting createWorkReportSetting(JsWorkReportSetting js) {

		WorkReportSetting rw = new WorkReportSetting();
		rw.setWorkReportSettingId(js.workReportSettingId);
		rw.setDomainId(js.domainId);
		rw.setWarrantyText(js.warranty);
		rw.setWorkReportSequence(js.workReportSequence);

		for (JsGridWorkType type : js.types) {

			rw.getTypes().add(JsGridWorkType.createWorkType(type));

		}

		for (JsGridBusinessTrip trip : js.trips) {

			rw.getTrips().add(JsGridBusinessTrip.createBusinessTrip(trip));

		}

		return rw;
	}

	public static WorkType createWorkType(JsGridWorkType jsWorkType) {
		if (jsWorkType == null) {
			return null;
		}

		WorkType type = new WorkType();
		type.setWorkTypeId(jsWorkType.workTypeId);
		type.setDomainId(jsWorkType.domainId);
		type.setExternalId(jsWorkType.externalId);
		type.setDescription(jsWorkType.description);

		return type;
		
	}
	
	public static BusinessTrip createBusinessTrip(JsGridBusinessTrip jsTrip) {
		if (jsTrip == null) {
			return null;
		}

		BusinessTrip trip = new BusinessTrip();
		trip.setBusinessTripId(jsTrip.businessTripId);
		trip.setDomainId(jsTrip.domainId);
		trip.setExternalId(jsTrip.externalId);
		trip.setDescription(jsTrip.description);

		return trip;
		
	}
	
	public static ArrayList<WorkType> createWorkTypeList(JsWorkReportSetting jswrs) {
		ArrayList<WorkType> list = new ArrayList<>();
		for(JsGridWorkType jsmf : jswrs.types) {
			list.add(createWorkType(jsmf));
		}
		return list;
	}
	
	public static ArrayList<BusinessTrip> createBusinessTripList(JsWorkReportSetting jswrs) {
		ArrayList<BusinessTrip> list = new ArrayList<>();
		for(JsGridBusinessTrip jsmf : jswrs.trips) {
			list.add(createBusinessTrip(jsmf));
		}
		return list;
	}
}
