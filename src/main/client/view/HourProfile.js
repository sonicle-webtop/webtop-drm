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
Ext.define('Sonicle.webtop.drm.view.HourProfile', {
	extend: 'WTA.sdk.ModelView',
	requires: [
		'Sonicle.webtop.drm.model.HourProfile'
	],
	dockableConfig: {
		title: '{HourProfile.tit}',
		iconCls: 'wtdrm-icon-configuration-ourprofile-xs',
		width: 1160,
		height: 500,
		modal: true,
		minimizable: false
	},
	fieldTitle: 'description',
	modelName: 'Sonicle.webtop.drm.model.HourProfile',
	constructor: function (cfg) {
		var me = this;
		me.callParent([cfg]);
	},
	initComponent: function () {
		var me = this;
		me.callParent(arguments);
		me.add({
			region: 'center',
			xtype: 'panel',
			layout: 'vbox',
			defaults: {
				labelWidth: 140
			},
			items: [
				{
					xtype: 'wtform',
					reference: 'hourProfileform',
					modelValidation: true,
					items: [
						{
							xtype: 'textfield',
							bind: '{record.description}',
							allowBlank: false,
							fieldLabel: me.mys.res('HourProfile.fld-description.lbl'),
							selectOnFocus: true
						}
					]
				},
				{
					title: me.mys.res('LineHours.tit'),
					xtype: 'grid',
					reference: 'gpLineHours',
					bind: {
						store: '{record.lineHours}'
					},
					width: '100%',
					border: true,
					columns: [
						{
							dataIndex: 'lineId',
							width: '25',
							header: me.mys.res('gpLineHours.lineId.lbl')
						},{
                            dataIndex: 'e_1',
                            editor: WTF.localCombo('id', 'desc', {
								store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
									autoLoad: true
								}),
                                regex: new RegExp('^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$'),
								regexText: me.mys.res('gpLineHours.column.format.lbl'),    
                                forceSelection: false
                            }),   
							header: me.mys.res('gpLineHours.1_e.lbl'),
							flex: 2                    
                        },{
                            dataIndex: 'u_1',
                            editor: WTF.localCombo('id', 'desc', {
								store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
									autoLoad: true
								}),
                                regex: new RegExp('^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$'),
								regexText: me.mys.res('gpLineHours.column.format.lbl'),    
                                forceSelection: false
                            }),   
							header: me.mys.res('gpLineHours.1_u.lbl'),
							flex: 2                    
                        },{
                            dataIndex: 'e_2',
                            editor: WTF.localCombo('id', 'desc', {
								store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
									autoLoad: true
								}),
                                regex: new RegExp('^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$'),
								regexText: me.mys.res('gpLineHours.column.format.lbl'),    
                                forceSelection: false
                            }),   
							header: me.mys.res('gpLineHours.2_e.lbl'),
							flex: 2                    
                        },{
                            dataIndex: 'u_2',
                            editor: WTF.localCombo('id', 'desc', {
								store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
									autoLoad: true
								}),
                                regex: new RegExp('^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$'),
								regexText: me.mys.res('gpLineHours.column.format.lbl'),    
                                forceSelection: false
                            }),   
							header: me.mys.res('gpLineHours.2_u.lbl'),
							flex: 2                    
                        },{
                            dataIndex: 'e_3',
                            editor: WTF.localCombo('id', 'desc', {
								store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
									autoLoad: true
								}),
                                regex: new RegExp('^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$'),
								regexText: me.mys.res('gpLineHours.column.format.lbl'),    
                                forceSelection: false
                            }),   
							header: me.mys.res('gpLineHours.3_e.lbl'),
							flex: 2                    
                        },{
                            dataIndex: 'u_3',
                            editor: WTF.localCombo('id', 'desc', {
								store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
									autoLoad: true
								}),
                                regex: new RegExp('^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$'),
								regexText: me.mys.res('gpLineHours.column.format.lbl'),    
                                forceSelection: false
                            }),   
							header: me.mys.res('gpLineHours.3_u.lbl'),
							flex: 2                    
                        },{
                            dataIndex: 'e_4',
                            editor: WTF.localCombo('id', 'desc', {
								store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
									autoLoad: true
								}),
                                regex: new RegExp('^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$'),
								regexText: me.mys.res('gpLineHours.column.format.lbl'),    
                                forceSelection: false
                            }),   
							header: me.mys.res('gpLineHours.4_e.lbl'),
							flex: 2                    
                        },{
                            dataIndex: 'u_4',
                            editor: WTF.localCombo('id', 'desc', {
								store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
									autoLoad: true
								}),
                                regex: new RegExp('^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$'),
								regexText: me.mys.res('gpLineHours.column.format.lbl'),    
                                forceSelection: false
                            }),   
							header: me.mys.res('gpLineHours.4_u.lbl'),
							flex: 2                    
                        },{
                            dataIndex: 'e_5',
                            editor: WTF.localCombo('id', 'desc', {
								store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
									autoLoad: true
								}),
                                regex: new RegExp('^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$'),
								regexText: me.mys.res('gpLineHours.column.format.lbl'),    
                                forceSelection: false
                            }),   
							header: me.mys.res('gpLineHours.5_e.lbl'),
							flex: 2                    
                        },{
                            dataIndex: 'u_5',
                            editor: WTF.localCombo('id', 'desc', {
								store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
									autoLoad: true
								}),
                                regex: new RegExp('^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$'),
								regexText: me.mys.res('gpLineHours.column.format.lbl'),    
                                forceSelection: false
                            }),   
							header: me.mys.res('gpLineHours.5_u.lbl'),
							flex: 2                    
                        },{
                            dataIndex: 'e_6',
                            editor: WTF.localCombo('id', 'desc', {
								store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
									autoLoad: true
								}),
                                regex: new RegExp('^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$'),
								regexText: me.mys.res('gpLineHours.column.format.lbl'),    
                                forceSelection: false
                            }),   
							header: me.mys.res('gpLineHours.6_e.lbl'),
							flex: 2                    
                        },{
                            dataIndex: 'u_6',
                            editor: WTF.localCombo('id', 'desc', {
								store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
									autoLoad: true
								}),
                                regex: new RegExp('^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$'),
								regexText: me.mys.res('gpLineHours.column.format.lbl'),    
                                forceSelection: false
                            }),   
							header: me.mys.res('gpLineHours.6_u.lbl'),
							flex: 2                    
                        },{
                            dataIndex: 'e_7',
                            editor: WTF.localCombo('id', 'desc', {
								store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
									autoLoad: true
								}),
                                regex: new RegExp('^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$'),
								regexText: me.mys.res('gpLineHours.column.format.lbl'),    
                                forceSelection: false
                            }),   
							header: me.mys.res('gpLineHours.7_e.lbl'),
							flex: 2                    
                        },{
                            dataIndex: 'u_7',
                            editor: WTF.localCombo('id', 'desc', {
								store: Ext.create('Sonicle.webtop.drm.store.WorkingHours', {
									autoLoad: true
								}),
                                regex: new RegExp('^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$'),
								regexText: me.mys.res('gpLineHours.column.format.lbl'),    
                                forceSelection: false
                            }),   
							header: me.mys.res('gpLineHours.7_u.lbl'),
							flex: 2                    
                        }
					],
					tbar: [
						me.addAct('add', {
							text: WT.res('act-add.lbl'),
							tooltip: null,
							iconCls: 'wt-icon-add-xs',
							handler: function () {
								me.addLineHour();
							},
							scope: me
						}),
						me.addAct('delete', {
							text: WT.res('act-delete.lbl'),
							tooltip: null,
							iconCls: 'wt-icon-delete',
							handler: function () {
								var sm = me.lref('gpLineHours').getSelectionModel();
								me.deleteLineHour(sm.getSelection());
							},
							scope: me
						})
					],
					plugins: {
						ptype: 'cellediting',
						clicksToEdit: 1
					}

				}
			]
		});
		
		me.on('viewinvalid', me.onViewInvalid);
	},
	
	onViewInvalid: function (s, mo, errs) {
		var me = this;
		WTU.updateFieldsErrors(me.lref('hourProfileform'), errs);
	},
	
	addLineHour: function () {
		var me = this;
		var gp = me.lref('gpLineHours'),
            sto = gp.getStore(),
            cep = gp.findPlugin('cellediting');

		cep.cancelEdit();

		sto.add(sto.createModel({
			lineId: sto.getCount() + 1
		}));

		cep.startEditByPosition({row: sto.getCount(), column: 0});
	},
    
	deleteLineHour: function (rec) {
		var me = this,
            grid = me.lref('gpLineHours'),
            sto = grid.getStore(),
            cellediting = grid.findPlugin('cellediting');

		WT.confirm(WT.res('confirm.delete'), function (bid) {
			if (bid === 'yes') {
				cellediting.cancelEdit();
				sto.remove(rec);
			}
		}, me);
	}
});

