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
Ext.define('Sonicle.webtop.drm.ux.TimetableLeavesChart', {
	extend: 'WTA.sdk.BaseView',
	alias: 'widget.wtdrmtimetableleaveschart',
	requires: [
		'Sonicle.Data',
		'Sonicle.String',
		'Sonicle.Utils',
		'Sonicle.VMUtils',
		'Sonicle.webtop.drm.model.LeaveEvent'
	],
	mixins: [
		'WTA.mixin.PanelUtil',
		'WTA.mixin.Waitable'
	],
	
	layout: 'fit',
	referenceHolder: true,
	
	viewModel: {
		data: {
			data: {
				date: null
			}
		}
	},
	
	initComponent: function () {
		var me = this;
		me.setVMData('date', new Date());
		me.callParent(arguments);
		me.add({
			xtype: 'sofullcalendarpanel',
			reference: 'fullcalendar',
			border: false,
			store: {
				model: 'Sonicle.webtop.drm.model.LeaveEvent',
				proxy: WTF.apiProxy(me.sid, 'LeavesChart', 'events', {
					autoAbort: true
				})
			},
			showResources: true,
			resources: function(fetchInfo, successCallback, failureCallback) {
				var filterFld = me.resfilterfield(),
					sfilter = filterFld ? filterFld.getValue() : undefined;
				
				WT.ajaxReq(me.sid, 'LeavesChart', {
					params: {
						type: 'resources'
					},
					callback: function (success, json) {
						if (success) {
							if (!Ext.isEmpty(sfilter)) {
								var data = [];
								Ext.iterate(json.data, function(item) {
									if (Sonicle.String.contains(item.title, sfilter, true)) data.push(item);
								});
								successCallback(data);
							} else {
								successCallback(json.data);
							}
						} else {
							failureCallback();
						}
					}
				});
			},
			eventsForceSolidDisplay: false,
			locale: WT.getLanguageCode(),
			startDay: WT.getStartDay(),
			use24HourTime: WT.getUse24HourTime(),
			initialDate: me.getVMData('date'),
			initialView: 'monthtimeline',
			//initialView: me.toFullCalendarViewName(me.getVar('view')),
			//slotResolution: me.getVar('timeResolution'),
			businessHours: {
				daysOfWeek: [1,2,3,4,5],
				startTime: '00:00',
				endTime: '24:00'
			},
			//scrollTime: Ext.String.leftPad(Math.max(me.getVar('workdayStart').getHours()-1, 0), 2, '0') + ':00',
			dayView: false,
			week5View: false,
			weekView: false,
			biweekView: false,
			monthView: false,
			yearView: false,
			dayTimelineView: true,
			weekTimelineView: true,
			monthTimelineView: true,
			extraViewConfig: {
				monthtimeline: {
					slotLabelFormat: {day: '2-digit', weekday: 'narrow', omitCommas: true},
					slotMinWidth: 55
				}
			},
			toolbarLayout: ['extraItems', '-', 'controlButtons', '->', 'headerText', '->', 'viewButtons'],
			toolbarExtraItems: [
				{
					xtype: 'datefield',
					bind: '{data.date}',
					editable: false,
					startDay: WT.getStartDay(),
					format: WT.getShortDateFmt(),
					listeners: {
						select: function(s, v) {
							me.fullcalendar().moveTo(v);
						}
					},
					fieldLabel: WT.res(me.sid, 'timetableLeavesChart.fld-goToDate.lbl'),
					width: 250
				}
			],
			buttonConfigs: {
				today: { ui: '{segmented|toolbar}' },
				previous: { ui: '{segmented|toolbar}' },
				next: { ui: '{segmented|toolbar}' },
				dayTimelineView: { ui: '{segmented|toolbar}' },
				weekTimelineView: { ui: '{segmented|toolbar}' },
				monthTimelineView: { ui: '{segmented|toolbar}' }
			},
			buttonTexts: {
				reload: { tooltip: WT.res('act-refresh.lbl') },
				today: { text: WT.res('sofullcalendarpanel.goToday.lbl'), tooltip: WT.res('sofullcalendarpanel.goToday.tip') },
				previous: { tooltip: WT.res('sofullcalendarpanel.goPrevious.tip') },
				next: { tooltip: WT.res('sofullcalendarpanel.goNext.tip') },
				daytimelineView: { text: WT.res('sofullcalendarpanel.view.daytimeline.lbl'), tooltip: WT.res('sofullcalendarpanel.view.daytimeline.tip') },
				weektimelineView: { text: WT.res('sofullcalendarpanel.view.weektimeline.lbl'), tooltip: WT.res('sofullcalendarpanel.view.weektimeline.tip') },
				monthtimelineView: { text: WT.res('sofullcalendarpanel.view.monthtimeline.lbl'), tooltip: WT.res('sofullcalendarpanel.view.monthtimeline.tip') }
			},
			texts: {
				weekShort: WT.res('sofullcalendarpanel.weekShort'),
				resourcesAreaTitle: WT.res(me.sid, 'timetableLeavesChart.resourcesareatitle.lbl')
			},
			eventClassNamesFunction: function(fcViewType, fcEvent, fcArg, context) {
				var SoS = Sonicle.String,
					ret = Sonicle.fullcalendar.Panel.appointmentEventClassNamesFunction.apply(this, arguments),
					exProps = fcEvent.extendedProps;
				if (SoS.isIn(exProps.reqStatus, ['S', 'RD'])) {
					ret.push('fc-event-style-slashed');
				} else if (SoS.isIn(exProps.reqStatus, ['D'])) {
					ret.push('fc-event-style-crossed');
				}
				return ret;
			},
			eventContentRenderer: function(fcViewType, fcEvent, fcArg, context) {
				var SoD = Sonicle.Date,
					SoS = Sonicle.String,
					seconds = SoD.diff(fcEvent.start, SoD.idate(fcEvent.end, fcEvent.start), 'seconds', true),
					ret = Sonicle.fullcalendar.Panel.appointmentEventContentRenderer.apply(this, arguments),
					tit = WT.res(me.sid, 'store.leaverequesttype.short.'+fcEvent.extendedProps.reqType),
					dur;
				
				dur = SoD.humanReadableDuration(seconds);
				// Current impl. of humanReadableDuration is not customizable, hack resulting text here
				if (SoS.contains(dur, ' ')) dur = SoS.replaceAll(dur, 'm', ''); // remove last m (if any)
				dur = SoS.replaceAll(dur, ' ', '');
				dur = SoS.replaceAll(dur, ',', '');
				return ret.replace(/(<div\s+class="so-cal-appo-title">)(.*?)(<\/div>)/g, '$1<span style="font-weight:bold;">' + tit + '</span>&nbsp;' + dur + '$3');
			},
			eventTooltipRenderer: function(fcViewType, fcEvent, fcArg, context) {
				var ret = Sonicle.fullcalendar.Panel.appointmentEventTooltipRenderer.apply(this, arguments),
					exProps = fcEvent.extendedProps,
					newTitle = exProps.reqTypeText + " " + exProps.reqStatusText;
				
				return ret.text.replace(/(<div\s+class="so-cal-appo-hov-title">)(.*?)(<\/div>)/g, '$1' + newTitle + '$3');
			},
			resourceLabelContentRenderer: Sonicle.fullcalendar.Panel.resourceLabelWithSwatchContentRenderer,
			resourceAreaWidth: 'auto',
			listeners: {
				scope: me,
				reloadclick: function(s) {
					s.getStore().load();
				},
				viewchange: function(s, name, info) {
					me.setVMData('date', info.start);
				}
			},
			tbar: [
				{
					xtype: 'textfield',
					reference: 'fldresfilter',
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: WT.res(me.sid, 'timetableLeavesChart.fld-recourcesFilter.lbl'),
					listeners: {
						specialkey: function(s, e) {
							if (e.getKey() === e.ENTER) {
								me.fullcalendar().refreshResources();
							}
						},
						clear: function() {
							me.resfilterfield().setValue(null); // Cleanup field now, otherwise it will still be full at refresh time!
							me.fullcalendar().refreshResources();
						}
					},
					width: 250
				}
			],
			bbar: [
				'->',
				{
					xtype: 'tbtext',
					html: me.legendText('H')
				}, {
					xtype: 'tbtext',
					html: me.legendText('P')
				}, {
					xtype: 'tbtext',
					html: me.legendText('U')
				}, {
					xtype: 'tbtext',
					html: me.legendText('M')
				}, {
					xtype: 'tbtext',
					html: me.legendText('C')
				}, {
					xtype: 'tbtext',
					html: me.legendText('S')
				}, {
					xtype: 'tbtext',
					html: me.legendText('W')
				},
				'->'
			],
			editable: false
		});
	},
	
	fullcalendar: function() {
		return this.lref('fullcalendar');
	},
	
	resfilterfield: function() {
		return this.lref('fldresfilter');
	},
	
	reload: function() {
		this.fullcalendar().getStore().load();
	},
	
	privates: {
		legendText: function(id) {
			var prefix = 'store.leaverequesttype.';
			return WT.res(this.sid, prefix+'short.'+id) + ': ' + WT.res(this.sid, prefix+id);
		}
	}
});

