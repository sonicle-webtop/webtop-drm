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
Ext.define('Sonicle.webtop.drm.ux.SearchPanel', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.wtdrmsearchpanel',
	layout: 'column',
	referenceHolder: true,
	sid: null,
	initComponent: function () {
		var me = this;
		me.callParent(arguments);

		me.add({
			xtype: 'wtform',
			items: [
				WTF.lookupCombo('id', 'desc', {
					reference: 'fldcompany',
					store: {
						autoLoad: true,
						model: 'WTA.model.Simple',
						proxy: WTF.proxy(me.sid, 'LookupCompanies'),
						listeners: {
							load: function (s) {
								if (s.loadCount === 1) {
									var meta = s.getProxy().getReader().metaData;
									if (meta.selected) {
										//me.getModel().set('companyId', meta.selected);
										me.lookupReference('fldcompany').setValue(meta.selected);
									}
								}
							}
						}
					},
					listeners: {
						select: function (s, rec) {
							me.lookupReference('flduser').getStore().load();
						}
					},
					fieldLabel: WT.res(me.sid, 'workReport.fld-company.lbl')

				}),
				WTF.remoteCombo('id', 'desc', {
					reference: 'flduser',
					store: {
						autoLoad: true,
						model: 'WTA.model.Simple',
						proxy: WTF.proxy(me.sid, 'LookupCompanyUsers'),
						listeners: {
							beforeload: function (s) {
								//TODO verificare utente per company
								WTU.applyExtraParams(s, {
									companyId: me.lookupReference('fldcompany').getValue()
								});
							}
						}
					},
					//TODO 
					fieldLabel: WT.res(me.sid, 'workReport.fld-nominative.lbl')
				}),
				{
					xtype: 'combo',
					reference: 'fldyear',
					store: Ext.create('Sonicle.webtop.drm.store.YearStore', {
						autoLoad: true

					}),
					valueField: 'year',
					displayField: 'year',
					fieldLabel: WT.res(me.sid, 'workReport.fld-year.lbl')
				},
				WTF.remoteCombo('id', 'desc', {
					reference: 'fldmasterdata',
					autoLoadOnValue: true,
					store: {
						model: 'WTA.model.Simple',
						proxy: WTF.proxy(WT.ID, 'LookupCustomersSuppliers'),
						listeners: {
							beforeload: {
								fn: function (s) {
									//TODO - verificare valore parentMasterDataId
//									WTU.applyExtraParams(s, {
//									 parentMasterDataId: me.getModel().get('masterDataId')
//									 });
								}
							}
						}
					},
					listeners: {
						select: {
							fn: function (c, rec) {
								var locked = rec.get('lockStatus');
								console.log(locked);
								if (locked === 'L') {
									Ext.Msg.alert('Status', 'Selected Customer is locked');
								}
								me.lookupReference('fldcausal').getStore().load();

							}
						}
					},
					fieldLabel: WT.res(me.sid, 'workReport.fld-realCustomer.lbl'),
					anchor: '100%'
				}),
				{
					xtype: 'datefield',
					reference: 'fldfrom',
					fieldLabel: WT.res(me.sid, 'workReport.fld-fromDate.lbl')
				},
				WTF.localCombo('id', 'desc', {
					reference: 'flddocstatus',
					store: {
						autoLoad: true,
						model: 'WTA.model.Simple',
						proxy: WTF.proxy(me.sid, 'LookupDocStatuses')
					},
					fieldLabel: WT.res(me.sid, 'workReport.fld-status.lbl')
				}),
				WTF.remoteCombo('id', 'desc', {
					reference: 'fldcausal',
					autoLoadOnValue: true,
					store: {
						model: 'WTA.model.CausalLkp',
						proxy: WTF.proxy(WT.ID, 'LookupCausals'),
						filters: [{
								filterFn: function (rec) {
									if (rec.get('readOnly')) {
										if (rec.getId() !== me.lookupReference('fldcausal').getValue()) {
											return null;
										}
									}
									return rec;
								}
							}],
						listeners: {
							beforeload: {
								fn: function (s) {
									WTU.applyExtraParams(s, {
										profileId: WT.getVar('profileId'), //mo.get('_profileId'),
										masterDataId: me.lookupReference('fldmasterdata').getValue()
									});
								}
							}
						}
					},
					triggers: {
						clear: WTF.clearTrigger()
					},
					fieldLabel: WT.res(me.sid, 'workReport.fld-confirmation.lbl'),
					anchor: '100%'
				}),
				WTF.localCombo('id', 'desc', {
					reference: 'fldchargeto',
					store: Ext.create('Sonicle.webtop.drm.store.Charged', {
						autoLoad: true
					}),
					fieldLabel: WT.res(me.sid, 'gpWorkReport.chargeTo.lbl'),
					width: 250
				}),
				{
					xtype: 'button',
					text: WT.res(me.sid, 'btn-search.lbl'),
					handler: function () {
						me.fireEvent('search', me, me.getQueryObj());
					}
				}
			]
		},
				{
					xtype: 'wtform',
					items: [
						{
							xtype: 'textfield',
							reference: 'fldworkreportno',
							fieldLabel: WT.res(me.sid, 'workReport.fld-workreportno.lbl')
						},
						{
							xtype: 'textfield',
							reference: 'fldreference',
							fieldLabel: WT.res(me.sid, 'workReport.fld-reference.lbl')},
						{
							xtype: 'textfield',
							reference: 'flddescription',
							fieldLabel: WT.res(me.sid, 'workReport.details.fld-description.lbl')},
						{
							xtype: 'datefield',
							reference: 'fldto',
							fieldLabel: WT.res(me.sid, 'workReport.fld-toDate.lbl')
						},
						WTF.localCombo('id', 'desc', {
							reference: 'fldbusinesstrip',
							store: {
								autoLoad: true,
								model: 'WTA.model.Simple',
								proxy: WTF.proxy(me.sid, 'LookupBusinessTrip')
							},
							fieldLabel: WT.res(me.sid, 'gpReportDetail.trasferts.lbl')
						}),
						{
							xtype: 'textfield',
							reference: 'fldnotes',
							fieldLabel: WT.res(me.sid, 'workReport.fld-notes.lbl')
						}
					]
				});

	},
	getQueryObj: function () {
		var me = this;
		return {
			companyId: me.lookupReference('fldcompany').getValue(),
			userId: me.lookupReference('flduser').getValue(),
			workReportNo: me.lookupReference('fldworkreportno').getValue(),
			year: me.lookupReference('fldyear').getValue(),
			customerId: me.lookupReference('fldmasterdata').getValue(),
			from: me.lookupReference('fldfrom').getValue(),
			to: me.lookupReference('fldto').getValue(),
			causalId: me.lookupReference('fldcausal').getValue(),
			referenceNo: me.lookupReference('fldreference').getValue(),
			businessTripId: me.lookupReference('fldbusinesstrip').getValue(),
			description: me.lookupReference('flddescription').getValue(),
			notes: me.lookupReference('fldnotes').getValue(),
			docStatusId: me.lookupReference('flddocstatus').getValue(),
			chargeTo: me.lookupReference('fldchargeto').getValue()
		};
	}

});
