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
Ext.define('Sonicle.webtop.drm.model.ExpenseNote', {
	extend: 'WTA.ux.data.BaseModel',
	requires: [
		'Sonicle.data.writer.Json',
		'Sonicle.webtop.drm.model.ExpenseNoteDocument',
		'Sonicle.webtop.drm.model.ExpenseNoteDetail'
	],
	proxy: WTF.apiProxy('com.sonicle.webtop.drm', 'ManageExpenseNote', 'data', {
		writer: {
			type: 'sojson',
			writeAssociations: true
		}
	}),
	identifier: 'negative',
	idProperty: 'id',
	fields: [
		WTF.field('id', 'string', true),
		WTF.field('operatorId', 'string', false),
		WTF.field('companyId', 'int', false),
		WTF.field('fromDate', 'date', false, {dateFormat: 'Y-m-d', defaultValue: new Date()}),
		WTF.field('toDate', 'date', false, {dateFormat: 'Y-m-d', defaultValue: new Date()}),
		WTF.field('totCurrency', 'float', true, {
			defaultValue: 0
		}),
		WTF.field('currency', 'string', false),
		WTF.field('description', 'string', true),
		WTF.field('status', 'string', false, {defaultValue: 'O'})
	],
	hasMany: [
		WTF.hasMany('documents', 'Sonicle.webtop.drm.model.ExpenseNoteDocument'),
		WTF.hasMany('details', 'Sonicle.webtop.drm.model.ExpenseNoteDetail')
	]
});