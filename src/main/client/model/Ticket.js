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
Ext.define('Sonicle.webtop.drm.model.Ticket', {
	extend: 'WTA.ux.data.BaseModel',
	requires: [
		'Sonicle.data.writer.Json',
		'Sonicle.webtop.drm.model.TicketAttachment'
	],
	proxy: WTF.apiProxy('com.sonicle.webtop.drm', 'ManageTicket', 'data', {
		writer: {
			type: 'sojson',
			writeAssociations: true
		},
		extraParams: {
			close: false
		}
	}),
	identifier: 'negative',
	idProperty: 'ticketId',
	
	fields: [
		WTF.field('ticketId', 'string', true),
		WTF.field('fromOperatorId', 'string', false),
		WTF.field('toOperatorId', 'string', false),
		WTF.field('companyId', 'int', false),
		WTF.field('customerId', 'string', false),
		WTF.field('customerStatId', 'string', false),
		WTF.field('ticketCategoryId', 'int', false),
		WTF.field('date', 'date', false, {dateFormat: 'Y-m-d H:i:s'}),
		WTF.field('timezone', 'string', false),
		WTF.field('title', 'string', false),
		WTF.field('statusId', 'int', false),
		WTF.field('priorityId', 'string', false,),		
		WTF.field('description', 'string', true),
		WTF.field('release', 'string', true),
		WTF.field('environment', 'string', true),
		WTF.field('suggestion', 'string', true),
		WTF.field('simulation', 'string', true),
		WTF.field('number', 'string', true),
		WTF.field('domainId', 'string', true),
		
		WTF.roField('_profileId', 'string'),
		
		WTF.field('eventId', 'int', true)
	],
	hasMany: [
		WTF.hasMany('attachments', 'Sonicle.webtop.drm.model.TicketAttachment')
	],
	
	setDate: function(date) {
		var me = this,
				dt = Ext.isDate(date) ? date : new Date(),
				// dur = Sonicle.Date.diff(me.get('startDate'), me.get('endDate'), Ext.Date.MINUTE, true),
				v;
		
		v = me.setDatePart('date', dt);
		// me.set('endDate', Ext.Date.add(v, Ext.Date.MINUTE, dur, true));
	},
	
	setTime: function(date) {
		var me = this,
				dt = Ext.isDate(date) ? date : new Date(),
				// dur = Sonicle.Date.diff(me.get('startDate'), me.get('endDate'), Ext.Date.MINUTE, true),
				v;
		
		v = me.setTimePart('date', dt);
		// me.set('endDate', Ext.Date.add(v, Ext.Date.MINUTE, dur, true));
	}
	
});