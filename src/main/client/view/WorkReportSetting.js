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
Ext.define('Sonicle.webtop.drm.view.WorkReportSetting', {
	extend: 'WTA.sdk.ModelView',
	dockableConfig: {
		title: '{workreport.config.tit}',
		iconCls: 'wtdrm-icon-configuration-generalconfiguration-xs',
		width: 500,
		height: 500
	},
	modelName: 'Sonicle.webtop.drm.model.WorkReportSetting',
	reloadOnClose: false,
	
	initComponent: function () {
		var me = this;
		me.callParent(arguments);
		me.add({
			region: 'center',
			xtype: 'tabpanel',
			items: [
				{
					xtype: 'wtform',
					title: me.mys.res('workReport.settings.tit'),
					items: [
						{
							xtype: 'textfield',
							bind: '{record.workReportSequence}',
							fieldLabel: me.mys.res('workReport.settings.fld-sequence.lbl')
						},
						{
							xtype: 'textareafield',
							bind: '{record.warranty}',
							fieldLabel: me.mys.res('workReport.settings.fld-warranty.lbl'),
                            width: 500
						},
						{
							xtype: 'checkbox',
							bind: '{record.printDaysTransfert}',
							boxLabel: me.mys.res('workReport.settings.fld-printDaysTrasfert.lbl')
						},
						{
							xtype: 'checkbox',
							bind: '{record.printTransfertDescription}',
							boxLabel: me.mys.res('workReport.settings.fld-printTrasfertDescription.lbl')
						},
						{
							xtype: 'checkbox',
							bind: '{record.printSignature}',
							boxLabel: me.mys.res('workReport.settings.fld-printSignature.lbl')
						},
						WTF.lookupCombo('id', 'desc', {
							bind: '{record.roundingHour}',
							store: Ext.create('Sonicle.webtop.drm.store.RoundingHour', {
								autoLoad: true
							}),
							triggers: {
								clear: WTF.clearTrigger()
							},
							emptyText: WT.res('word.none.male'),
							fieldLabel: me.mys.res('workReport.settings.fld-rounding.lbl'),
                            width: 80
						}),
						{
							xtype: 'checkbox',
							bind: '{record.tracking}',
							boxLabel: me.mys.res('workReport.settings.fld-tracking.lbl')
						},
						{
							xtype: 'checkbox',
							bind: '{record.trackingMail}',
							boxLabel: me.mys.res('workReport.settings.fld-mail.lbl')
						},
						{
							xtype: 'checkbox',
							bind: '{record.trackingCloud}',
							boxLabel: me.mys.res('workReport.settings.fld-cloud.lbl')
						}
					]
				},
				{
					title: me.mys.res('worktype.tit'),
					xtype: 'grid',
					reference: 'gpWorkType',
					bind: {
						store: '{record.types}'
					},
					width: '100%',
					border: true,
					flex: 2,
					columns: [
						{
							dataIndex: 'externalId',
							editor: {
								xtype: 'textfield',
								allowBlank: true
							},
							header: me.mys.res('gpworktype.externalId.lbl'),
							width: 100
						},
						{
							dataIndex: 'description',
							editor: {
								xtype: 'textfield',
								allowBlank: false
							},
							header: me.mys.res('gpworktype.description.lbl'),
							flex: 1
						}
					],
					tbar: [
						me.addAct('add', {
							text: WT.res('act-add.lbl'),
							tooltip: null,
							iconCls: 'wt-icon-add-xs',
							handler: function () {
								me.addType();
							},
							scope: me
						}),
						me.addAct('delete', {
							text: WT.res('act-delete.lbl'),
							tooltip: null,
							iconCls: 'wt-icon-delete',
							handler: function () {
								var sm = me.lref('gpWorkType').getSelectionModel();
								me.deleteType(sm.getSelection());
							},
							scope: me
						})
					],
					plugins: {
						ptype: 'cellediting',
						clicksToEdit: 1
					}

				},
				{
					title: me.mys.res('businesstrip.tit'),
					xtype: 'grid',
					reference: 'gpBusinessTrip',
					bind: {
						store: '{record.trips}'
					},
					width: '100%',
					flex: 2,
					columns: [
						{
							dataIndex: 'externalId',
							editor: {
								xtype: 'textfield',
								allowBlank: true
							},
							header: me.mys.res('gpbusinesstrip.externalId.lbl'),
							width: 150
						},
						{
							dataIndex: 'description',
							editor: {
								xtype: 'textfield',
								allowBlank: false
							},
							header: me.mys.res('gpbusinesstrip.description.lbl'),
							flex: 1
						}
					],
					tbar: [
						me.addAct('add', {
							text: WT.res('act-add.lbl'),
							tooltip: null,
							iconCls: 'wt-icon-add-xs',
							handler: function () {
								me.addBusinessTrip();
							},
							scope: me
						}),
						me.addAct('delete', {
							text: WT.res('act-delete.lbl'),
							tooltip: null,
							iconCls: 'wt-icon-delete',
							handler: function () {
								var sm = me.lref('gpBusinessTrip').getSelectionModel();
								me.deleteBusinessTrip(sm.getSelection());
							},
							scope: me
						})
					],
					plugins: {
						ptype: 'cellediting',
						clicksToEdit: 1
					}
				},
				{
					xtype: 'wtform',
					title: me.mys.res('workReportDefault.tit'),
					defaults: {
						labelWidth: 80
					},
					items: [
						{
							xtype: 'checkbox',
							bind: '{record.defaultApplySignature}',
							boxLabel: me.mys.res('workReport.settings.fld-defaultApplySignature.lbl')
						},
						{
							xtype: 'checkbox',
							bind: '{record.defaultChargeTo}',
							boxLabel: me.mys.res('workReport.settings.fld-defaultChargeTo.lbl')
						},
						{
							xtype: 'checkbox',
							bind: '{record.defaultFreeSupport}',
							boxLabel: me.mys.res('workReport.settings.fld-defaultFreeSupport.lbl')
						},
						WTF.localCombo('id', 'desc', {
							bind: '{record.defaultStatus}',
							store: {
								autoLoad: true,
								model: 'WTA.model.Simple',
								proxy: WTF.proxy(me.mys.ID, 'LookupDocStatuses')
							},
							fieldLabel: me.mys.res('workReport.settings.fld-defaultStatus.lbl')
						})
					]
				}
			]
		});
		me.on("beforeviewsave", me.onBeforeViewSave);
		me.on("viewsave", me.onViewSave);
	},
	addType: function () {
		var me = this;
		var gp = me.lref('gpWorkType'),
				sto = gp.getStore(),
				cep = gp.findPlugin('cellediting');


		cep.cancelEdit();

		sto.add(sto.createModel({
			externalId: null,
			description: null
		}));

		cep.startEditByPosition({row: sto.getCount(), column: 0});
	},
	deleteType: function (rec) {
		var me = this,
				grid = me.lref('gpWorkType'),
				sto = grid.getStore(),
				cellediting = grid.findPlugin('cellediting');

		WT.confirm(WT.res('confirm.delete'), function (bid) {
			if (bid === 'yes') {
				cellediting.cancelEdit();
				sto.remove(rec);
			}
		}, me);
	},
	addBusinessTrip: function () {
		var me = this;
		var gp = me.lref('gpBusinessTrip'),
				sto = gp.getStore(),
				cep = gp.findPlugin('cellediting');


		cep.cancelEdit();

		sto.add(sto.createModel({
			externalId: null,
			description: null
		}));

		cep.startEditByPosition({row: sto.getCount(), column: 0});
	},
	deleteBusinessTrip: function (rec) {
		var me = this,
				grid = me.lref('gpBusinessTrip'),
				sto = grid.getStore(),
				cellediting = grid.findPlugin('cellediting');

		WT.confirm(WT.res('confirm.delete'), function (bid) {
			if (bid === 'yes') {
				cellediting.cancelEdit();
				sto.remove(rec);
			}
		}, me);
	},
	onBeforeViewSave: function (s, mo) {		
		s.reloadOnClose = s.needsReload(mo);
	},
	onViewSave: function (s, success, mo) {
		var me = this;
		
		if(me.reloadOnClose)
			WT.confirm(me.mys.res('configuration.confirm.logout'), function (bid) {
				if (bid === 'yes') {
					WT.logout();
				}
			});
	},
	needsReload: function (mo){
		if(mo.isModified('defaultApplySignature') 
				|| mo.isModified('defaultChargeTo') 
				|| mo.isModified('defaultFreeSupport') 
				|| mo.isModified('defaultStatus')) 
			return true;
		else 
			return false;
	}
});
