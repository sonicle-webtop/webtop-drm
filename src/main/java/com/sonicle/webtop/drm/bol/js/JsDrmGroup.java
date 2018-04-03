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

import com.sonicle.webtop.drm.model.DrmGroup;
import com.sonicle.webtop.drm.model.DrmGroupUserAssociation;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lssndrvs
 */
public class JsDrmGroup {

	public String groupId;
	public String domainId;
	public String groupCategory;
	public String name;
	public String description;
	public String groupType;
	public String customerId;
	public String supervisiorUserId;
	
	public List<DrmGroupUserAssociation> associatedUsers = new ArrayList();

	public JsDrmGroup(DrmGroup group) {
		this.groupId = group.getGroupId();
		this.domainId = group.getDomainId();
		this.name = group.getName();
		this.description = group.getDescription();
		this.groupCategory= group.getGroupCategory();
		this.groupType = group.getGroupType();
		
		this.associatedUsers = group.getAssociatedUsers();
	}

	public static DrmGroup createDrmGroup(JsDrmGroup js) {

		DrmGroup newDrmGroup = new DrmGroup();

		newDrmGroup.setGroupId(js.groupId);
		newDrmGroup.setDomainId(js.domainId);
		newDrmGroup.setName(js.name);
		newDrmGroup.setDescription(js.description);
		newDrmGroup.setGroupCategory(js.groupCategory);
		newDrmGroup.setGroupType(js.groupType);
		
		newDrmGroup.setAssociatedUsers(js.associatedUsers);

		return newDrmGroup;
	}

}
