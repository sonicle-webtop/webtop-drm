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
Ext.define('Sonicle.webtop.drm.ux.CustomerGrid', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.wtdrmcustomergrid',
	requires: [
		'Sonicle.picker.List',
		'WTA.ux.PickerWindow',
		'Sonicle.grid.column.Lookup',
		'Sonicle.webtop.drm.model.DrmCustomerLkp'
	],
	/**
	 * @cfg {String} sid
	 * The service ID.
	 */

	initComponent: function () {
		var me = this;

		me.lookupStore = Ext.create('Ext.data.Store', {
			autoLoad: true,
			model: 'Sonicle.webtop.drm.model.DrmCustomerLkp',
			proxy: WTF.proxy(WT.ID, 'LookupCustomersSuppliers', 'data')
		});

		me.selModel = {
			type: 'rowmodel'
		};

		if (!me.viewConfig) {
			me.viewConfig = {
				deferEmptyText: false,
				emptyText: WT.res(me.sid, 'profile.associatedcustomer.emp')
			};
		}

		if (!me.columns) {
			me.hideHeaders = true;
			me.columns = [{
					xtype: 'solookupcolumn',
					dataIndex: 'masterDataId',
					store: me.lookupStore,
					displayField: 'label',
					flex: 1
				}, {
					xtype: 'actioncolumn',
					align: 'center',
					width: 50,
					items: [{
							iconCls: 'fa fa-minus-circle',
							tooltip: WT.res('act-remove.lbl'),
							handler: function (gp, ri) {
								gp.getStore().removeAt(ri);
							}
						}]
				}];
		}
		me.tools = me.tools || [];
		me.tools.push({
			type: 'plus',
			callback: me.onAddClick,
			scope: me
		});
		
		me.callParent(arguments);
 	},

	createPicker: function () {
		var me = this;
		return Ext.create({
			xtype: 'wtpickerwindow',
			title: WT.res(me.sid, 'associatedcustomer.picker.tit'),
			height: 350,
			items: [{
					xtype: 'solistpicker',
					store: me.lookupStore,
					enableGrouping: false, //abilita il raggruppamento step 1 vd 
					valueField: 'id', //sempre 'id' poiche arriva dal SimpleModel
					displayField: 'desc',
					searchField: 'desc',
					emptyText: WT.res(me.sid, 'grid.empty'),
					searchText: WT.res(me.sid, 'associatedcustomer.picker.search'),
					okText: WT.res('act-ok.lbl'),
					cancelText: WT.res('act-cancel.lbl'),
					listeners: {
						cancelclick: function () {
							if (me.picker)
								me.picker.close();
						}
					},
					handler: me.onPickerPick,
					scope: me
				}]
		});
	},
	bindStore: function (store, initial) {
		var me = this;
		me.callParent(arguments);
		if (store) {
			store.on('remove', me.onStoreRemove, me);
		}
	},
	unbindStore: function () {
		var me = this;
		if (me.store) {
			me.store.un('remove', me.onStoreRemove, me);
		}
		me.callParent(arguments);
	},
	privates: {
		onSelectionChange: function (s, sel) {
			this.removeAction.setDisabled(sel.length === 0);
		},
		onStoreRemove: function (s, recs) {
			this.getSelectionModel().deselect(recs); // Fix for updating selection
		},
		onAddClick: function () {
			var me = this;
			me.picker = me.createPicker();
			me.picker.show();
		},
		onRemoveClick: function () {
			var me = this,
					rec = me.getSelection()[0];
			if (rec)
				me.getStore().remove(rec);
		},
		onPickerPick: function (s, val, rec) {
			var me = this;
			me.fireEvent('pick', me, val, rec);
			me.picker.close();
			me.picker = null;
		}
	}
});
