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
package com.sonicle.webtop.drm.rest.v2;

import com.sonicle.webtop.core.app.RunContext;
import com.sonicle.webtop.core.app.WT;
import com.sonicle.webtop.core.sdk.UserProfileId;
import com.sonicle.webtop.core.sdk.WTException;
import com.sonicle.webtop.drm.DrmManager;
import com.sonicle.webtop.drm.swagger.v2.api.ManagersApi;
import com.sonicle.webtop.drm.swagger.v2.model.ApiApiError;
import com.sonicle.webtop.drm.swagger.v2.model.ApiManager;
import com.sonicle.webtop.drm.swagger.v2.model.ApiManagersResult;
import com.sonicle.webtop.drm.swagger.v2.model.ApiUser;
import com.sonicle.webtop.drm.swagger.v2.model.ApiUsersResult;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author gabriele.bulfon
 */
public class Managers extends ManagersApi {
	
	private static final Logger logger = LoggerFactory.getLogger(Managers.class);

	@Override
	public Response listManagers(String userId) {
		UserProfileId currentProfileId = RunContext.getRunProfileId();
		/*if (userId == null)*/ userId = currentProfileId.getUserId();
		DrmManager manager = getManager();
		try {
			List<String> list = manager.listManagersByDomainUser(userId); 
			ApiManagersResult amr = new ApiManagersResult();
			ArrayList<ApiManager> items = new ArrayList<>();
			for (String managerId: list) {
				ApiManager m = new ApiManager();
				m.setId(managerId);
				m.setDisplayname(WT.getUserData(new UserProfileId(currentProfileId.getDomainId(), managerId)).getDisplayName());
				items.add(m);
			}
			amr.setItems(items);
			return respOk(amr);
		} catch(WTException exc) {
			logger.error("[{}] getManagers({})", currentProfileId, userId, exc);
			return respError(exc);
		}
	}

	@Override
	public Response listManagedUsers() {
		UserProfileId currentProfileId = RunContext.getRunProfileId();
		DrmManager manager = getManager();
		try {
			List<DrmManager.Operator> list = manager.listManagedAndSupervisedOperators(); 
			ApiUsersResult aur = new ApiUsersResult();
			ArrayList<ApiUser> items = new ArrayList<>();
			for (DrmManager.Operator op: list) {
				ApiUser u = new ApiUser();
				u.setId(op.usr);
				u.setDisplayname(op.dn);
				items.add(u);
			}
			aur.setItems(items);
			return respOk(aur);
		} catch(WTException exc) {
			logger.error("[{}] listManagedUsers({})", currentProfileId, exc);
			return respError(exc);
		}
	}

	@Override
	protected Object createErrorEntity(Response.Status status, String message) {
		return new ApiApiError()
				.code(status.getStatusCode())
				.description(message);
	}
	
	private DrmManager getManager() {
		return getManager(RunContext.getRunProfileId());
	}
	
	private DrmManager getManager(UserProfileId targetProfileId) {
		return (DrmManager)WT.getServiceManager(SERVICE_ID, targetProfileId);
	}
	
	

}
