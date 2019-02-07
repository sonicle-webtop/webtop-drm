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
package com.sonicle.webtop.drm.bol.model;

import com.sonicle.webtop.core.CoreManager;
import com.sonicle.webtop.core.sdk.WTException;
import com.sonicle.webtop.drm.DrmManager;
import com.sonicle.webtop.drm.model.WorkReportRow;

/**
 *
 * @author lssndrvs
 */
public class RBWorkReportRows {
	public Integer id;
	public String workReportId;
	public Integer rowNo;
	public Integer workTypeId;
	public String workTypeExternalId;
	public String workTypeDescription;
	public Integer duration;
	public Boolean extra;

	public RBWorkReportRows(DrmManager drmMgr, WorkReportRow wrr) throws WTException {
		this.id = wrr.getId();
		this.workReportId = wrr.getWorkReportId();
		this.rowNo = wrr.getRowNo();
		this.workTypeId = wrr.getWorkTypeId();
		this.workTypeExternalId = drmMgr.getWorkType(wrr.getWorkTypeId()).getExternalId();
		this.workTypeDescription = drmMgr.getWorkType(wrr.getWorkTypeId()).getDescription();
		this.duration = wrr.getDuration();
		this.extra = wrr.getExtra();
	}
	
	public RBWorkReportRows() throws WTException {
		this.rowNo = 999;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWorkReportId() {
		return workReportId;
	}

	public void setWorkReportId(String workReportId) {
		this.workReportId = workReportId;
	}

	public Integer getRowNo() {
		return rowNo;
	}

	public void setRowNo(Integer rowNo) {
		this.rowNo = rowNo;
	}

	public Integer getWorkTypeId() {
		return workTypeId;
	}

	public void setWorkTypeId(Integer workTypeId) {
		this.workTypeId = workTypeId;
	}

	public String getWorkTypeDescription() {
		return workTypeDescription;
	}

	public void setWorkTypeDescription(String workTypeDescription) {
		this.workTypeDescription = workTypeDescription;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Boolean getExtra() {
		return extra;
	}

	public void setExtra(Boolean extra) {
		this.extra = extra;
	}

	public String getWorkTypeExternalId() {
		return workTypeExternalId;
	}

	public void setWorkTypeExternalId(String workTypeExternalId) {
		this.workTypeExternalId = workTypeExternalId;
	}
	
}
