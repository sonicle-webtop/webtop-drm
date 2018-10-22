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

import com.sonicle.commons.web.ServletUtils;
import com.sonicle.commons.web.json.JsonResult;
import com.sonicle.webtop.core.app.WT;
import com.sonicle.webtop.core.app.WebTopSession;
import com.sonicle.webtop.core.bol.js.JsWTSPublic;
import com.sonicle.webtop.core.sdk.BasePublicService;
import com.sonicle.webtop.core.sdk.UserProfileId;
import com.sonicle.webtop.core.sdk.WTException;
import com.sonicle.webtop.core.app.servlet.ServletHelper;
import static com.sonicle.webtop.drm.Service.logger;
import com.sonicle.webtop.drm.model.LeaveRequest;
import freemarker.template.TemplateException;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author lssndrvs
 */
public class PublicService extends BasePublicService {
	
	public static final String PUBPATH_CONTEXT_LEAVE_REQUEST = "leave-request";
	
	@Override
	public void initialize() throws Exception {
		
	}

	@Override
	public void cleanup() throws Exception {
		
	}
	
	private WebTopSession getWts() {
		return getEnv().getWebTopSession();
	}
	
	private DrmManager getAdminManager(String domainId) {
		return (DrmManager)WT.getServiceManager(SERVICE_ID, true, new UserProfileId(domainId, "admin"));
	}
	
	@Override
	public void processDefaultAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BasePublicService.PublicPath path = new BasePublicService.PublicPath(request.getPathInfo());
		WebTopSession wts = getEnv().getWebTopSession();
		
		try {
			try {
				String domainId = WT.findDomainIdByPublicName(path.getDomainPublicName());
				if (domainId == null) throw new WTException("Invalid domain public name [{0}]", path.getDomainPublicName());
				
				if (path.getContext().equals(PUBPATH_CONTEXT_LEAVE_REQUEST)) {
					LeaveRequestUrlPath lrUrlPath = new LeaveRequestUrlPath(path.getRemainingPath());
					DrmManager adminDrmMgr = getAdminManager(domainId);

					if (!StringUtils.isBlank(lrUrlPath.getPublicUid())) {
						if (lrUrlPath.isActionReply()) {
							String aid = ServletUtils.getStringParameter(request, "aid", true);
							String crud = ServletUtils.getStringParameter(request, "crud", true);
							String resp = ServletUtils.getStringParameter(request, "resp", true);
							
							Boolean responseStatus = toResponseStatus(resp);
							if (responseStatus == null) throw new WTException("Invalid resp [{0}]", resp);
							
							LeaveRequest lr = adminDrmMgr.getLeaveRequest(Integer.valueOf(lrUrlPath.getPublicUid()));
							
							if(lr != null){
								if("new".equals(crud)){
									lr.setResult(responseStatus);
									adminDrmMgr.updateLeaveRequest(lr, true);
								} else if("delete".equals(crud)){
									adminDrmMgr.updateCancellationLeaveRequest(Integer.valueOf(lrUrlPath.getPublicUid()), responseStatus);
								} else{
									throw new WTException("Invalid crud [{0}]", crud);
								}	

								writeLeaveRequestPage(request, response, wts, resp);
							} else {
								logger.trace("Invalid id [{}]", lrUrlPath.getPublicUid());
								writeLeaveRequestPage(request, response, wts, "notfound");
							}
						}
					}					

				} else {
					logger.error("Invalid context [{}]", path.getContext());
					writeErrorPage(request, response, wts, "badrequest");
				}
				
			} catch(Exception ex) {
				logger.error("Error", ex);
				writeErrorPage(request, response, wts, "badrequest");
			}
		} catch(Throwable t) {
			logger.error("Unexpected error", t);
		}
	}
	
	private Boolean toResponseStatus(String resp) {
		if(resp.equals("yes")) {
			return true;
		} else if(resp.equals("no")) {
			return false;
		} else {
			return null;
		}
	}
	
	private void writeErrorPage(HttpServletRequest request, HttpServletResponse response, WebTopSession wts, String reskey) throws IOException, TemplateException {
		JsWTSPublic.Vars vars = new JsWTSPublic.Vars();
		vars.put("view", "Error");
		vars.put("reskey", reskey);
		writePage(response, wts, vars, ServletHelper.getBaseUrl(request));
	}
	
	private void writeLeaveRequestPage(HttpServletRequest request, HttpServletResponse response, WebTopSession wts, String reskey) throws IOException, TemplateException {
		JsWTSPublic.Vars vars = new JsWTSPublic.Vars();
		vars.put("view", "LeaveRequest");
		vars.put("reskey", reskey);
		writePage(response, wts, vars, ServletHelper.getBaseUrl(request));
	}
	
	public static class LeaveRequestUrlPath extends BasePublicService.UrlPathTokens {
		public final static String TOKEN_REPLY = "reply";
			
		public LeaveRequestUrlPath(String remainingPath) {
			super(StringUtils.split(remainingPath, "/", 2));
		}
		
		public String getPublicUid() {
			return getTokenAt(0);
		}
		
		public String getAction() {
			return getTokenAt(1);
		}
		
		public boolean isActionReply() {
			return StringUtils.equals(getAction(), TOKEN_REPLY);
		}
	}
}
