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
Ext.define('Sonicle.webtop.drm.view.PrepareOpportunityActions', {
	extend: 'WTA.sdk.ModelView',
	requires: [
		'Sonicle.webtop.drm.ux.ActivityGrid',
		'Sonicle.webtop.drm.model.PrepareOpportunityActions',
		'WTA.ux.data.SimpleSourceModel',
		'Sonicle.Bytes',
		'WTA.ux.UploadBar'
	],
	dockableConfig: {
		title: '{prepareOpportunityActions.tit}',
		iconCls: 'wtdrm-icon-opportunityAction',
		width: 325,
		height: 324,
		modal: true,
		minimizable: false
	},
	modelName: 'Sonicle.webtop.drm.model.PrepareOpportunityActions',
	
	constructor: function(cfg) {
		var me = this;
		
		me.callParent([cfg]);
		
		WTU.applyFormulas(me.getVM(), {
			startDate: {
				bind: {bindTo: '{record.startDate}'},
				get: function(val) {
					return val ? Ext.Date.clone(val): null;
				},
				set: function(val) {
					this.get('record').setStartDate(val);
				}
			},
			startTime: {
				bind: {bindTo: '{record.startDate}'},
				get: function(val) {
					return (val) ? Ext.Date.clone(val): null;
				},
				set: function(val) {
					this.get('record').setStartTime(val);
				}
			},
			endDate: {
				bind: {bindTo: '{record.endDate}'},
				get: function(val) {
					return val ? Ext.Date.clone(val): null;
				},
				set: function(val) {
					this.get('record').setEndDate(val);
				}
			},
			endTime: {
				bind: {bindTo: '{record.endDate}'},
				get: function(val) {
					return val ? Ext.Date.clone(val): null;
				},
				set: function(val) {
					this.get('record').setEndTime(val);
				}
			}
		});
	},
	
	initComponent: function () {
		var me = this;

		me.callParent(arguments);

		me.add({
			region: 'center',
			xtype: 'container',
			layout: 'border',
			refernceHolder: true,
			items: [
				{
					xtype: 'container',
					region: 'north',
					height: '100px',
					layout: {
						type: 'vbox', align: 'stretch'
					},
					items: [
						Ext.create({
							xtype: 'wtform',
							reference: 'mainForm',
							modelValidation: true,
							defaults: {
								labelWidth: 75
							},
							items: [
								{
									xtype: 'fieldcontainer',
									fieldLabel: me.mys.res('prepareOpportunityActions.fld-startDate.lbl'),
									layout: 'hbox',
									defaults: {
										margin: '0 10 0 0'
									},
									items: [{
										xtype: 'datefield',
										bind: {
											value: '{startDate}'
										},
										startDay: WT.getStartDay(),
										format: WT.getShortDateFmt(),
										margin: '0 5 0 0',
										width: 105
									}, {
										xtype: 'timefield',
										bind: {
											value: '{startTime}'
										},
										format: WT.getShortTimeFmt(),
										margin: '0 5 0 0',
										width: 80
									}, {
										xtype: 'button',
										iconCls: 'far fa-clock wt-theme-glyph',
										tooltip: me.mys.res('prepareOpportunityActions.btn-now.tip'),
										handler: function() {
											me.getModel().setStartTime(new Date());
										}
									}]
								}, {
									xtype: 'fieldcontainer',
									fieldLabel: me.mys.res('prepareOpportunityActions.fld-endDate.lbl'),
									layout: 'hbox',
									defaults: {
										margin: '0 10 0 0'
									},
									items: [{
										xtype: 'datefield',
										bind: {
											value: '{endDate}'
										},
										startDay: WT.getStartDay(),
										format: WT.getShortDateFmt(),
										margin: '0 5 0 0',
										width: 105
									}, {
										xtype: 'timefield',
										bind: {
											value: '{endTime}'
										},
										format: WT.getShortTimeFmt(),
										margin: '0 5 0 0',
										width: 80
									}, {
										xtype: 'button',
										iconCls: 'far fa-clock wt-theme-glyph',
										tooltip: me.mys.res('prepareOpportunityActions.btn-now.tip'),
										handler: function() {
											me.getModel().setEndTime(new Date());
										}
									}]
								}
							]
						})
					]
				}, 
				{
					region: 'center',
					xtype: 'wtdrmactivitygrid',
					title: me.mys.res('prepareOpportunityAction.gpActionActivities.tit'),
					sid: me.mys.ID,
					actionsInToolbar: false,
					width: '100%',
					flex: 1,
					border: true,
					bind: {
						store: '{record.actionActivities}'
					},
					listeners: {
						pick: function (s, vals, recs) {
							var mo = me.getModel();
							console.log("pick: "+recs[0].getId());
							mo.actionActivities().add({
								activityId: recs[0].getId(),
								description: recs[0].get('description')
							});
						}
					}
				}
			]
		});

		me.on('viewinvalid', me.onViewInvalid);
		me.on('viewload', me.onViewLoad);
	},
	
	onViewLoad: function(s, success) {
		if(!success) return;
		var me = this,
				mo = me.getModel();
		
		if(mo.get('operatorId') === null) me.lref('flduser').setReadOnly(false);
	},
	
	onViewInvalid: function (s, mo, errs) {
		var me = this;
		
		WTU.updateFieldsErrors(me.lref('mainForm'), errs);
	}
});
