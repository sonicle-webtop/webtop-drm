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

Ext.define('Sonicle.webtop.drm.store.TimetableStampHours', {
	extend: 'Ext.data.ArrayStore',
	
	model: 'WTA.model.Simple',
	data: [
		['',''],
		['0.00','0.00'],
		['0.15','0.15'],
		['0.30','0.30'],
		['0.45','0.45'],
		['1.00','1.00'],
		['1.15','1.15'],
		['1.30','1.30'],
		['1.45','1.45'],
		['2.00','2.00'],
		['2.15','2.15'],
		['2.30','2.30'],
		['2.45','2.45'],
		['3.00','3.00'],
		['3.15','3.15'],
		['3.30','3.30'],
		['3.45','3.45'],
		['4.00','4.00'],
		['4.15','4.15'],
		['4.30','4.30'],
		['4.45','4.45'],
		['5.00','5.00'],
		['5.15','5.15'],
		['5.30','5.30'],
		['5.45','5.45'],
		['6.00','6.00'],
		['6.15','6.15'],
		['6.30','6.30'],
		['6.45','6.45'],
		['7.00','7.00'],
		['7.15','7.15'],
		['7.30','7.30'],
		['7.45','7.45'],
		['8.00','8.00'],
		['8.15','8.15'],
		['8.30','8.30'],
		['8.45','8.45'],
		['9.00','9.00'],
		['9.15','9.15'],
		['9.30','9.30'],
		['9.45','9.45'],
		['10.00','10.00'],
		['10.15','10.15'],
		['10.30','10.30'],
		['10.45','10.45'],
		['11.00','11.00'],
		['11.15','11.15'],
		['11.30','11.30'],
		['11.45','11.45'],
		['12.00','12.00']
	],
	
	constructor: function(cfg) {
		var me = this;
		Ext.each(me.config.data, function(row) {
			row[1] = row[0];
		});
		me.callParent([cfg]);
	}
});
