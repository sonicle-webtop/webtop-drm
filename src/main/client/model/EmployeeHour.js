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
Ext.define('Sonicle.webtop.drm.model.EmployeeHour', {
	extend: 'WTA.ux.data.BaseModel',	
	
	fields: [
		WTF.field('id', 'int', true),
		WTF.field('domainId', 'string', true),
		WTF.field('employeeProfileId', 'int', true),
		WTF.field('lineId', 'int', true),
		WTF.field('e_1', 'string', true),
		WTF.field('u_1', 'string', true),
		WTF.field('e_2', 'string', true),
		WTF.field('u_2', 'string', true),
		WTF.field('e_3', 'string', true),
		WTF.field('u_3', 'string', true),
		WTF.field('e_4', 'string', true),
		WTF.field('u_4', 'string', true),
		WTF.field('e_5', 'string', true),
		WTF.field('u_5', 'string', true),
		WTF.field('e_6', 'string', true),
		WTF.field('u_6', 'string', true),
		WTF.field('e_7', 'string', true),
		WTF.field('u_7', 'string', true),
	]
});

