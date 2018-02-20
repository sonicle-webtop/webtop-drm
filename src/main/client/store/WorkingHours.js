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

Ext.define('Sonicle.webtop.drm.store.WorkingHours', {
	extend: 'Ext.data.ArrayStore',
	
	model: 'WTA.model.Simple',
	data: [
		['00:00','00:00'],
		['00:15','00:15'],
		['00:30','00:30'],
		['00:45','00:45'],
		['01:00','01:00'],
		['01:15','01:15'],
		['01:30','01:30'],
		['01:45','01:45'],
		['02:00','02:00'],
		['02:15','02:15'],
		['02:30','02:30'],
		['02:45','02:45'],
		['03:00','03:00'],
		['03:15','03:15'],
		['03:30','03:30'],
		['03:45','03:45'],
		['04:00','04:00'],
		['04:15','04:15'],
		['04:30','04:30'],
		['04:45','04:45'],
		['05:00','05:00'],
		['05:15','05:15'],
		['05:30','05:30'],
		['05:45','05:45'],
		['06:00','06:00'],
		['06:15','06:15'],
		['06:30','06:30'],
		['06:45','06:45'],
		['07:00','07:00'],
		['07:15','07:15'],
		['07:30','07:30'],
		['07:45','07:45'],
		['08:00','08:00'],
		['08:15','08:15'],
		['08:30','08:30'],
		['08:45','08:45'],
		['09:00','09:00'],
		['09:15','09:15'],
		['09:30','09:30'],
		['09:45','09:45'],
		['10:00','10:00'],
		['10:15','10:15'],
		['10:30','10:30'],
		['10:45','10:45'],
		['11:00','11:00'],
		['11:15','11:15'],
		['11:30','11:30'],
		['11:45','11:45'],
		['12:00','12:00'],
		['12:15','12:15'],
		['12:30','12:30'],
		['12:45','12:45'],
		['13:00','13:00'],
		['13:15','13:15'],
		['13:30','13:30'],
		['13:45','13:45'],
		['14:00','14:00'],
		['14:15','14:15'],
		['14:30','14:30'],
		['14:45','14:45'],
		['15:00','15:00'],
		['15:15','15:15'],
		['15:30','15:30'],
		['15:45','15:45'],
		['16:00','16:00'],
		['16:15','16:15'],
		['16:30','16:30'],
		['16:45','16:45'],
		['17:00','17:00'],
		['17:15','17:15'],
		['17:30','17:30'],
		['17:45','17:45'],
		['18:00','18:00'],
		['18:15','18:15'],
		['18:30','18:30'],
		['18:45','18:45'],
		['19:00','19:00'],
		['19:15','19:15'],
		['19:30','19:30'],
		['19:45','19:45'],
		['20:00','20:00'],
		['20:15','20:15'],
		['20:30','20:30'],
		['20:45','20:45'],
		['21:00','21:00'],
		['21:15','21:15'],
		['21:30','21:30'],
		['21:45','21:45'],
		['22:00','22:00'],
		['22:15','22:15'],
		['22:30','22:30'],
		['22:45','22:45'],
		['23:00','23:00'],
		['23:15','23:15'],
		['23:30','23:30'],
		['23:45','23:45']
	],
	
	constructor: function(cfg) {
		var me = this;
		Ext.each(me.config.data, function(row) {
			row[1] = row[0];
		});
		me.callParent([cfg]);
	}
});
