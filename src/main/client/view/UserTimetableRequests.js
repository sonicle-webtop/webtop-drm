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
Ext.define('Sonicle.webtop.drm.view.UserTimetableRequests', {
	extend: 'WTA.sdk.UIView',
	requires: [
		'Sonicle.String'
	],
	
	dockableConfig: {
		iconCls: 'wtdrm-icon-timetableRequests',
		width: 900,
		height: 500,
		modal: true,
		minimizable: false,
		maximizable: false
	},
	writableOnly: false,

	
	constructor: function(cfg) {
		var me = this;
		
		me.callParent([cfg]);
	},
	
	initComponent: function () {
		var me = this;
		me.callParent(arguments);

		me.add({
			region: 'center',
			xtype: 'panel',
			layout: 'border',
			items: [
				{
					region: 'center',
					xtype: 'grid',
					reference: 'gpUserTimetableRequests',
					store: {
						autoLoad: true,
						model: 'Sonicle.webtop.drm.model.GridTimetableRequests',
						proxy: WTF.apiProxy(me.mys.ID, 'ManageGridUserTimetableRequests', null, {
							extraParams: {
								targetUserId: me.targetUserId,
								date: me.date
							}
						})
					},
					columns: [
						{
							xtype: 'soiconcolumn',
							align: 'center',
							dataIndex: 'result',
							getIconCls: function(v, rec) {
								if (rec.get('status') === 'D') {
									return 'wtdrm-icon-timetableCancRequest-approved';
								} else if ((rec.get('result') === true || rec.get('result') === false) && rec.get('employeeCancReq') === true) {
									return 'wtdrm-icon-timetableCancRequest-new'; 
								} else if (rec.get('result') === true) {
									return 'wtdrm-icon-timetableRequest-approved';
								} else if (rec.get('result') === false) {
									return 'wtdrm-icon-timetableRequest-declined';
								} else {
									return 'wtdrm-icon-timetableRequest-sent';
								}
								//return me.mys.cssIconCls((rec.get('status') === 'D') ? 'cancellationrequest-approved' : ((rec.get('result') === true || rec.get('result') === false) && rec.get('employeeCancReq') === true) ? 'cancellationrequest-new' : (rec.get('result') === true) ? 'approvedrequest' : (rec.get('result') === false) ? 'notapprovedrequest' : 'sendedrequest', 'xs');
							},
							iconSize: WTU.imgSizeToPx('xs'),
							width: 30,
							cls: 'resultCls',
							tdCls: 'resultTdCls'
						}, {
							header: me.mys.res('gpTimetableRequest.type.lbl'),
							dataIndex: 'type',
							flex: 1,
							renderer: function (value, meta, record, rowIndex, colIndex, store) {
								switch(value) {
									case 'H':
										return me.mys.res('gpTimetableRequest.type.H.lbl');
										break;
									case 'P':
										return me.mys.res('gpTimetableRequest.type.P.lbl');
										break;
									case 'U':
										return me.mys.res('gpTimetableRequest.type.U.lbl');
										break;
									case 'M':
										return me.mys.res('gpTimetableRequest.type.M.lbl');
										break;
									case 'C':
										return me.mys.res('gpTimetableRequest.type.C.lbl');
										break;
									case 'O':
										return me.mys.res('gpTimetableRequest.type.O.lbl');
										break;
									case 'S':
										return me.mys.res('gpTimetableRequest.type.S.lbl');
										break;
									case 'W':
										return me.mys.res('gpTimetableRequest.type.W.lbl');
										break;
									default:
										return '';
										break;
								}
							}
						}, {
							header: me.mys.res('gpTimetableRequest.requestBy.lbl'),
							dataIndex: 'user',
							flex: 1
						}, {
							header: me.mys.res('gpTimetableRequest.manager.lbl'),
							dataIndex: 'manager',
							flex: 1
						}, {
							header: me.mys.res('gpTimetableRequest.fromDate.lbl'),
							dataIndex: 'fromDate',
							xtype: 'datecolumn',
							format: WT.getShortDateFmt(),
							flex: 1
						}, {
							header: me.mys.res('gpTimetableRequest.fromHour.lbl'),
							dataIndex: 'fromHour',
							flex: 1
						}, {
							header: me.mys.res('gpTimetableRequest.toDate.lbl'),
							dataIndex: 'toDate',
							xtype: 'datecolumn',
							format: WT.getShortDateFmt(),
							flex: 1
						}, {
							header: me.mys.res('gpTimetableRequest.toHour.lbl'),
							dataIndex: 'toHour',
							flex: 1
						}, {
							header: me.mys.res('gpTimetableRequest.status.lbl'),
							dataIndex: 'status',
							flex: 1,
							renderer: function (value, meta, record, rowIndex, colIndex, store) {
								switch(value) {
									case 'O':
										return me.mys.res('gpTimetableRequest.status.O.lbl');
										break;
									case 'C':
										return me.mys.res('gpTimetableRequest.status.C.lbl');
										break;
									case 'D':
										return me.mys.res('gpTimetableRequest.status.D.lbl');
										break;
									default:
										return '';
										break;
								}
							}
						}
					],
					listeners: {
						rowdblclick: function (s, rec) {
							me.mys.editUserTimetableRequestUI(rec);
						},
						render: function(grid) {
							var view = grid.getView();
							grid.tip = Ext.create('Ext.tip.ToolTip', {
								target: view.getId(),
								delegate: view.itemSelector + ' .resultTdCls',
								trackMouse: true,
								listeners: {
									beforeshow: function updateTipBody(tip) {
										var tipGridView = tip.target.component;
										var rec = tipGridView.getRecord(tip.triggerElement);

										tip.update((rec.get('status') === 'D') ? me.mys.res('gpTimetableRequest.tip.result.delete.lbl') : ((rec.get('result') === true || rec.get('result') === false) && rec.get('employeeCancReq') === true) ? me.mys.res('gpTimetableRequest.tip.result.requestcancellation.lbl') : (rec.get('result') === true) ? me.mys.res('gpTimetableRequest.tip.result.approved.lbl') : (rec.get('result') === false) ? me.mys.res('gpTimetableRequest.tip.result.notapproved.lbl') : me.mys.res('gpTimetableRequest.tip.result.sended.lbl'));
									}
								}
							});
						}
					}
				}
			]
		});
	}
});
