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
Ext.define('Sonicle.webtop.drm.model.PrepareOpportunityActions', {
	extend: 'WTA.ux.data.BaseModel',
	requires: [
		'Sonicle.webtop.drm.model.Activity'
	],
	proxy: WTF.apiProxy('com.sonicle.webtop.drm', 'ManagePrepareOpportunityActions', 'data', {
		writer: {
			type: 'sojson',
			writeAssociations: true
		}
	}),
	identifier: 'negative',
	idProperty: 'id',
	fields: [
		WTF.field('id', 'int', false),
		WTF.field('opportunityId', 'int', true),
		WTF.field('operatorId', 'string', true),
		WTF.field('startDate', 'date', true, {dateFormat: 'Y-m-d H:i:s', defaultValue: new Date()}),
		WTF.field('endDate', 'date', true, {dateFormat: 'Y-m-d H:i:s', defaultValue: new Date()})
	],
	hasMany: [
		WTF.hasMany('actionActivities', 'Sonicle.webtop.drm.model.Activity')
	],
	
	setStart: function(date) {
		var me = this,
				dt = Ext.isDate(date) ? date : new Date(),
				dur = Sonicle.Date.diff(me.get('startDate'), me.get('endDate'), Ext.Date.MINUTE, true);
		
		me.set('startDate', dt);
		me.set('endDate', Ext.Date.add(dt, Ext.Date.MINUTE, dur, true));
	},
	
	setEnd: function(date) {
		var me = this,
				dt = Ext.isDate(date) ? date : new Date(),
				dur = Sonicle.Date.diff(me.get('startDate'), me.get('endDate'), Ext.Date.MINUTE, true),
				sta = me.get('startDate');
		
		me.set('endDate', dt);
		if (!Ext.isDate(sta)) return;
		if (dt < sta) me.set('startDate', Ext.Date.add(dt, Ext.Date.MINUTE, -dur, true));
	},
	
	setStartDate: function(date) {
		var me = this,
				dt = Ext.isDate(date) ? date : new Date(),
				dur = Sonicle.Date.diff(me.get('startDate'), me.get('endDate'), Ext.Date.MINUTE, true),
				v;
		
		v = me.setDatePart('startDate', dt);
		me.set('endDate', Ext.Date.add(v, Ext.Date.MINUTE, dur, true));
	},
	
	setStartTime: function(date) {
		var me = this,
				dt = Ext.isDate(date) ? date : new Date(),
				dur = Sonicle.Date.diff(me.get('startDate'), me.get('endDate'), Ext.Date.MINUTE, true),
				v;
		
		v = me.setTimePart('startDate', dt);
		me.set('endDate', Ext.Date.add(v, Ext.Date.MINUTE, dur, true));
	},
	
	setEndDate: function(date) {
		var me = this,
				dt = Ext.isDate(date) ? date : new Date(),
				sta = me.get('startDate'),
				dur = Sonicle.Date.diff(sta, me.get('endDate'), Ext.Date.MINUTE, true),
				v;
		
		v = me.setDatePart('endDate', dt);
		if (!Ext.isDate(sta)) return;
		if (v < sta) me.set('startDate', Ext.Date.add(v, Ext.Date.MINUTE, -dur, true));
	},
	
	setEndTime: function(date) {
		var me = this,
				dt = Ext.isDate(date) ? date : new Date(),
				sta = me.get('startDate'),
				v;
		
		v = me.setTimePart('endDate', dt);
		if (!Ext.isDate(sta)) return;
		if (v < sta) me.set('startDate', v);
	}
});

