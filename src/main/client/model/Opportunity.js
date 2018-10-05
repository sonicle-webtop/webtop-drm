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
Ext.define('Sonicle.webtop.drm.model.Opportunity', {
	extend: 'WTA.ux.data.BaseModel',
	requires: [
		'Sonicle.data.writer.Json',
		'Sonicle.webtop.drm.model.OpportunityInterlocutor',
		'Sonicle.webtop.drm.model.OpportunityAction',
		'Sonicle.webtop.drm.model.OpportunityDocument',
		'Sonicle.webtop.drm.ux.OpportunityValidator'
	],
	proxy: WTF.apiProxy('com.sonicle.webtop.drm', 'ManageOpportunity', 'data', {
		writer: {
			type: 'sojson',
			writeAssociations: true
		}
	}),
	identifier: 'negative',
	idProperty: 'id',
	fields: [
		WTF.field('domainId', 'string', true),
		WTF.field('id', 'int', true),
		WTF.field('operatorId', 'string', false),
		WTF.field('companyId', 'int', false),
		WTF.field('date', 'date', false, {dateFormat: 'Y-m-d', defaultValue: new Date()}),
		WTF.field('fromHour', 'string', true),
		WTF.field('toHour', 'string', true),
		WTF.field('executedWith', 'string', true, {validators: [{type: 'wtdrmdynpresence', requiredFieldGroupName: 'mainFields', requiredFieldName: 'executed_with'}]}),
		WTF.field('customerId', 'string', true, {validators: [{type: 'wtdrmdynpresence', requiredFieldGroupName: 'mainFields', requiredFieldName: 'customer'}]}),
		WTF.field('customerStatId', 'string', true, {validators: [{type: 'wtdrmdynpresence', requiredFieldGroupName: 'mainFields', requiredFieldName: 'statistic_customer'}]}),
		WTF.field('sector', 'string', true, {validators: [{type: 'wtdrmdynpresence', requiredFieldGroupName: 'mainFields', requiredFieldName: 'sector'}]}),
		WTF.field('description', 'string', true, {validators: [{type: 'wtdrmdynpresence', requiredFieldGroupName: 'mainFields', requiredFieldName: 'description'}]}),
		WTF.field('place', 'string', true, {validators: [{type: 'wtdrmdynpresence', requiredFieldGroupName: 'mainFields', requiredFieldName: 'place'}]}),
		WTF.field('objective', 'string', true, {validators: [{type: 'wtdrmdynpresence', requiredFieldGroupName: 'mainFields', requiredFieldName: 'objective'}]}),
		WTF.field('causalId', 'string', true, {validators: [{type: 'wtdrmdynpresence', requiredFieldGroupName: 'mainFields', requiredFieldName: 'causal'}]}),
		WTF.field('activityId', 'string', true, {validators: [{type: 'wtdrmdynpresence', requiredFieldGroupName: 'mainFields', requiredFieldName: 'activity'}]}),
		WTF.field('objective2', 'string', true, {validators: [{type: 'wtdrmdynpresence', requiredFieldGroupName: 'visitReportFields', requiredFieldName: 'objective'}]}),
		WTF.field('result', 'string', true, {validators: [{type: 'wtdrmdynpresence', requiredFieldGroupName: 'visitReportFields', requiredFieldName: 'result'}]}),
		WTF.field('discoveries', 'string', true, {validators: [{type: 'wtdrmdynpresence', requiredFieldGroupName: 'visitReportFields', requiredFieldName: 'discoveries'}]}),
		WTF.field('customerPotential', 'string', true, {validators: [{type: 'wtdrmdynpresence', requiredFieldGroupName: 'visitReportFields', requiredFieldName: 'customer_potential'}]}),
		WTF.field('notes', 'string', true, {validators: [{type: 'wtdrmdynpresence', requiredFieldGroupName: 'notesSignatureFields', requiredFieldName: 'notes'}]}),
		WTF.field('statusId', 'string', true, {validators: [{type: 'wtdrmdynpresence', requiredFieldGroupName: 'notesSignatureFields', requiredFieldName: 'status'}]}),
		WTF.field('signedBy', 'string', true, {validators: [{type: 'wtdrmdynpresence', requiredFieldGroupName: 'notesSignatureFields', requiredFieldName: 'signed_by'}]}),
		WTF.field('signature', 'bool', true, {validators: [{type: 'wtdrmdynpresence', requiredFieldGroupName: 'notesSignatureFields', requiredFieldName: 'signature'}]}),
		WTF.field('success', 'bool', true, {validators: [{type: 'wtdrmdynpresence', requiredFieldGroupName: 'notesSignatureFields', requiredFieldName: 'success'}]})
	],
	hasMany: [
		WTF.hasMany('interlocutors', 'Sonicle.webtop.drm.model.OpportunityInterlocutor'),
		WTF.hasMany('documents', 'Sonicle.webtop.drm.model.OpportunityDocument')
	]
});