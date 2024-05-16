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
Ext.define('Sonicle.webtop.drm.view.TimetableSettingHolidays', {
    extend: 'WTA.sdk.DockableView',
	requires: [
        'Sonicle.webtop.drm.model.GridHolidayDates'
	],
	dockableConfig: {
		title: '{timetable.config.holidays.tit}',
		iconCls: 'wtdrm-icon-configuration',
		width: 650,
		height: 670
	},
	initComponent: function () {
		var me = this;
		
        me.callParent(arguments);
        
		me.add({
            region: 'center',
			xtype: 'panel',
			layout: 'vbox',
			items: [
                {
                    // title: me.mys.res('holidaydates.tit'),
                    xtype: 'grid',
                    reference: 'gpHolidayDate',
					flex: 1,
                    store: {
                        autoLoad: true,
                        model: 'Sonicle.webtop.drm.model.GridHolidayDates',
                        proxy: WTF.apiProxy(me.mys.ID, 'ManageGridHolidayDate', 'data', {
                            writer: {
                                allowSingle: false
                            }
                        }),
                        groupField: 'year',
                        groupDir: 'DESC'
                    },
                    features: [{
                        ftype: 'grouping',
                        groupHeaderTpl: '{name}',
                        enableGroupingMenu: false,
                        startCollapsed: true
                    }],
                    width: '100%',
                    border: true,
                    flex: 2,
                    columns: [
                        {
                            dataIndex: 'date',
                            editor: {
                                xtype: 'datefield',
                                startDay: WT.getStartDay(),
                                allowBlank: false,
                                selectOnFocus: true
                            },
                            xtype:'datecolumn', 
                            format: WT.getShortDateFmt(),
                            header: me.mys.res('gpholidaydate.date.lbl'),
                            width: 110
                        },
                        {
                            dataIndex: 'description',
                            editor: {
                                xtype: 'textfield',
                                allowBlank: false,
                                selectOnFocus: true
                            },
                            header: me.mys.res('gpholidaydate.description.lbl'),
                            flex: 1
                        }
                    ],
                    tbar: [
                        me.addAct('add', {
                            text: WT.res('act-add.lbl'),
                            tooltip: null,
                            iconCls: 'wt-icon-add',
                            handler: function () {
                                me.addHolidayDate({
                                    callback: function (success) {
                                        if (success) {
                                            me.lref('gpHolidayDate').getStore().load();
                                        }
                                    }
                                });
                            },
                            scope: me
                        }),
                        '-',
                        me.addAct('edit', {
                            text: WT.res('act-edit.lbl'),
                            tooltip: null,
                            iconCls: 'wt-icon-edit',
                            handler: function () {
                                var sel = me.lref('gpHolidayDate').getSelection()[0];
                                me.editHolidayDateUI(sel);
                            },
                            scope: me
                        }),
                        me.addAct('delete', {
                            text: WT.res('act-delete.lbl'),
                            tooltip: null,
                            iconCls: 'wt-icon-delete',
                            handler: function () {
                                var sel = me.lref('gpHolidayDate').getSelection()[0];
                                me.deleteHolidayDateUI(sel);
                            },
                            scope: me
                        }),
                        '-',
                        me.addAct('clone', {
                            text: WT.res('act-clone.lbl'),
                            tooltip: null,
                            iconCls: 'far fa-clone',
                            handler: function () {
                                me.cloneHolidayDate({
                                    callback: function (success) {
                                        if (success) {
                                            me.lref('gpHolidayDate').getStore().load();
                                        }
                                    }
                                });
                            },
                            scope: me
                        })                        
                    ],
                    listeners: {
                        rowdblclick: function (s, rec) {
                            me.editHolidayDateUI(rec);
                        },
                        afterrender: function (grid, eOpts) {
                            var groupingFeature = grid.getView().features[0]; 
                            
                            grid.store.load({
                                callback: function() {
                                    if (grid.store.getCount() > 0)
                                        groupingFeature.expand(grid.store.getAt(0).data.year.toString(), true);                                    
                                }
                            });                            
                        },
                        groupclick: function (view, node, group, e, eOpts) {
                            view.features[0].collapseAll();
                            view.features[0].expand(group);
                        }
                    }
                }
            ]
		});
	},
	
	addHolidayDate: function (opts) {
		opts = opts || {};
		var me = this,
			vw = WT.createView(me.mys.ID, 'view.HolidayDate', {swapReturn: true});
	
		vw.on('viewsave', function (s, success, model) {
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});	
		vw.showView(function () {
			vw.begin('new', {
				data: {
				}
			});
		});
	},   

    cloneHolidayDate: function(opts) {
		opts = opts || {};

		var me = this,
			vw = WT.createView(me.mys.ID, 'view.CloneHolidayDates', {
				swapReturn: true,
				viewCfg: {
						dockableConfig: {
							title: me.mys.res('cloneHolidayDates.tit')
						}
					}
			});
		vw.on('clone', function(s, fromYear, toYear) {
            WT.ajaxReq(me.mys.ID, 'ManageHolidayDate', {
                params: {
                    crud: 'clone',
                    fromYear: fromYear,
                    toYear: toYear
                },
                callback: function (success, json) {
                    Ext.callback(opts.callback, opts.scope || me, [success, json]);
                }
            });
		});
		vw.showView();
	},
    
    editHolidayDateUI: function (rec) {
		var me = this;
		me.editHolidayDate(rec.get('holidayDateId'), {
			callback: function (success, model) {
				if (success) {
					me.lref('gpHolidayDate').getStore().load();
				} else {
					alert('error');
				}
			}
		});
	},
	
	editHolidayDate: function (holidayDateId, opts) {
		opts = opts || {};
		var me = this,
            vw = WT.createView(me.mys.ID, 'view.HolidayDate', {swapReturn: true});
		vw.on('viewsave', function (s, success, model) {
			Ext.callback(opts.callback, opts.scope || me, [success, model]);
		});
		vw.showView(function () {
            vw.begin('edit', {
                data: {
                    holidayDateId: holidayDateId
                }
            });
        });
	},
    
    deleteHolidayDateUI: function (rec) {
		var me = this,
            sto = me.lref('gpHolidayDate').getStore(),
            msg;
		if (rec) {
			msg = me.mys.res('act.confirm.delete', Ext.String.ellipsis(rec.get('user'), 40));
		}
		WT.confirm(msg, function (bid) {
			if (bid === 'yes') {
				me.deleteHolidayDate(rec.get('holidayDateId'), {
					callback: function (success) {
						if (success)
							sto.remove(rec);
					}
				});
			}
		});
	},
    
	deleteHolidayDate: function (holidayDateId, opts) {
		opts = opts || {};
		var me = this;
		WT.ajaxReq(me.mys.ID, 'ManageHolidayDate', {
			params: {
				crud: 'delete',
				holidayDateId: WTU.arrayAsParam(holidayDateId)
			},
			callback: function (success, json) {
				Ext.callback(opts.callback, opts.scope || me, [success, json]);
			}
		});
	}
});
