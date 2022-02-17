/*
 * webtop-contacts is a WebTop Service developed by Sonicle S.r.l.
 * Copyright (C) 2014 Sonicle S.r.l.
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
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, see http://www.gnu.org/licenses or write to
 * the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301 USA.
 *
 * You can contact Sonicle S.r.l. at email address sonicle@sonicle.com
 *
 * The interactive user interfaces in modified source and object code versions
 * of this program must display Appropriate Legal Notices, as required under
 * Section 5 of the GNU Affero General Public License version 3.
 *
 * In accordance with Section 7(b) of the GNU Affero General Public License
 * version 3, these Appropriate Legal Notices must retain the display of the
 * "Powered by Sonicle WebTop" logo. If the display of the logo is not reasonably
 * feasible for technical reasons, the Appropriate Legal Notices must display
 * the words "Powered by Sonicle WebTop".
 */
Ext.define('Sonicle.webtop.drm.view.CloneHolidayDates', {
	extend: 'WTA.sdk.UIView',
	requires: [
		'Sonicle.String'
	],
	
	dockableConfig: {
		iconCls: 'wtdrm-icon-configurationHolidayDates',
		width: 220,
		height: 160,
		modal: true,
		minimizable: false,
		maximizable: false
	},
	promptConfirm: false,
	writableOnly: false,
	
	viewModel: {
		data: {
			fromYear: (new Date().getFullYear() - 1),
			toYear: new Date().getFullYear()
		}
	},
	defaultButton: 'btnok',
	
	constructor: function(cfg) {
		var me = this;
		me.callParent([cfg]);
	},
	
	initComponent: function() {
		var me = this;
		
		Ext.apply(me, {
			buttons: [
				{
					reference: 'btnok',
					text: WT.res('act-clone.lbl'),
					tooltip: WT.res('act-clone.lbl'),
					iconCls: 'far fa-clone',
					handler: function() {
						me.clone();
					}
				}
			]
		});
		me.callParent(arguments);
		
		me.add({
			region: 'center',
			xtype: 'panel',
			items: [
				{
					xtype: 'container',
					layout: {
						type: 'vbox', align: 'stretch'
					},
					defaults: {
						margin: '5 5 0 5'
					},
					items: [						
						{
							xtype: 'numberfield',
							reference: 'fldfromyear',
							bind: '{fromYear}',
                            allowDecimals: false,
                            minValue: 0,
                            selectOnFocus: true,
                            allowBlank: false,
							fieldLabel: me.mys.res('cloneHolidayDates.fld-fromYear.lbl')
						},
						{
							xtype: 'numberfield',
							reference: 'fldtoyear',
							bind: '{toYear}',
                            allowDecimals: false,
                            minValue: 0,
                            selectOnFocus: true,
                            allowBlank: false,
							fieldLabel: me.mys.res('cloneHolidayDates.fld-toYear.lbl')
						}
					]
				}
			]
		});		
	},

	clone: function() {
		var me = this,
            vm = me.getVM();
		vm.set('result', 'ok');
		me.fireEvent('clone', me, vm.get('fromYear'), vm.get('toYear'));
		me.closeView(false);
	}
});