/*
 * webtop-tasks is a WebTop Service developed by Sonicle S.r.l.
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
Ext.define('Sonicle.webtop.drm.view.UserOptions', {
	extend: 'WTA.sdk.UserOptionsView',
		
	initComponent: function() {
		var me = this;
		me.callParent(arguments);
		
		me.add(
			{
				xtype: 'wtopttabsection',
				title: WT.res(me.ID, 'opts.expensenote.tit'),
				items: [
					{
						xtype: 'numberfield',
						bind: '{record.kmCost}',
						minValue: 0,
						fieldLabel: WT.res(me.ID, 'opts.expensenote.fld-kmCost.lbl'),
						width: 280,
						listeners: {
							blur: {
								fn: me.onBlurAutoSave,
								scope: me
							}
						},
						triggers: {
							clear: WTF.clearTrigger()
						}
					},
					WTF.localCombo('id', 'desc', {
						bind: '{record.defaultCurrency}',
						fieldLabel: WT.res(me.ID, 'opts.expensenote.fld-defaultCurrency.lbl'),
						store: Ext.create('Sonicle.webtop.drm.store.Currency', {
							autoLoad: true
						}),
						width: 280,
						listeners: {
							blur: {
								fn: me.onBlurAutoSave,
								scope: me
							}
						},
						triggers: {
							clear: WTF.clearTrigger()
						}
					})
				]
			}, {
				xtype: 'wtopttabsection',
				title: WT.res(me.ID, 'opts.ticket.tit'),
				items: [
					{
						xtype: 'checkbox',
						reference: 'ticketNotifyMail',
						bind: '{record.ticketNotifyMail}',
						boxLabel: WT.res(me.ID, 'opts.ticket.fld-notifyMail.lbl'),
						boxLabelAlign: 'before',
						listeners: {
							change: {
								fn: function(s, n) {
									me.lref('ticketAutomaticClose').setDisabled(!n);
									if (n === false) {
										me.lref('ticketAutomaticClose').setValue(false);
									}
									//TODO: workaround...il modello veniva salvato prima dell'aggionamento
									Ext.defer(function() {
										me.onBlurAutoSave(s);
									}, 200);
								},
								scope: me
							}
						}
					},
					{
						xtype: 'checkbox',
						reference: 'ticketAutomaticClose',
						bind: '{record.ticketAutomaticClose}',
						boxLabel: WT.res(me.ID, 'opts.ticket.fld-automaticClose.lbl'),
						// boxLabelAlign: 'after',
						listeners: {
							change: {
								fn: function(s) {
									//TODO: workaround...il modello veniva salvato prima dell'aggionamento
									Ext.defer(function() {
										me.onBlurAutoSave(s);
									}, 200);
								},
								scope: me
							}
						}
					}
				]
			}
		);
	}
});
