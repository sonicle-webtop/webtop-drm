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

Ext.define('Sonicle.webtop.drm.store.Currency', {
	extend: 'Ext.data.ArrayStore',
	
	model: 'WTA.model.Simple',
	data: [
		['AED',''],
		['AFN',''],
		['ALL',''],
		['AMD',''],
		['ANG',''],
		['AOA',''],
		['ARS',''],
		['AUD',''],
		['AWG',''],
		['AZN',''],
		['BAM',''],
		['BBD',''],
		['BDT',''],
		['BGN',''],
		['BHD',''],
		['BIF',''],
		['BMD',''],
		['BND',''],
		['BOB',''],
		['BOV',''],
		['BRL',''],
		['BSD',''],
		['BTN',''],
		['BWP',''],
		['BYN',''],
		['BZD',''],
		['CAD',''],
		['CDF',''],
		['CHE',''],
		['CHF',''],
		['CHW',''],
		['CLF',''],
		['CLP',''],
		['CNY',''],
		['COP',''],
		['COU',''],
		['CRC',''],
		['CUC',''],
		['CUP',''],
		['CVE',''],
		['CZK',''],
		['DJF',''],
		['DKK',''],
		['DOP',''],
		['DZD',''],
		['EGP',''],
		['ERN',''],
		['ETB',''],
		['EUR',''],
		['FJD',''],
		['FKP',''],
		['GBP',''],
		['GEL',''],
		['GHS',''],
		['GIP',''],
		['GMD',''],
		['GNF',''],
		['GTQ',''],
		['GYD',''],
		['HKD',''],
		['HNL',''],
		['HRK',''],
		['HTG',''],
		['HUF',''],
		['IDR',''],
		['ILS',''],
		['INR',''],
		['IQD',''],
		['IRR',''],
		['ISK',''],
		['JMD',''],
		['JOD',''],
		['JPY',''],
		['KES',''],
		['KGS',''],
		['KHR',''],
		['KMF',''],
		['KPW',''],
		['KRW',''],
		['KWD',''],
		['KYD',''],
		['KZT',''],
		['LAK',''],
		['LBP',''],
		['LKR',''],
		['LRD',''],
		['LSL',''],
		['LYD',''],
		['MAD',''],
		['MDL',''],
		['MGA',''],
		['MKD',''],
		['MMK',''],
		['MNT',''],
		['MOP',''],
		['MRO',''],
		['MUR',''],
		['MVR',''],
		['MWK',''],
		['MXN',''],
		['MXV',''],
		['MYR',''],
		['MZN',''],
		['NAD',''],
		['NGN',''],
		['NIO',''],
		['NOK',''],
		['NPR',''],
		['NZD',''],
		['OMR',''],
		['PAB',''],
		['PEN',''],
		['PGK',''],
		['PHP',''],
		['PKR',''],
		['PLN',''],
		['PYG',''],
		['QAR',''],
		['RON',''],
		['RSD',''],
		['RUB',''],
		['RWF',''],
		['SAR',''],
		['SBD',''],
		['SCR',''],
		['SDG',''],
		['SEK',''],
		['SGD',''],
		['SHP',''],
		['SLL',''],
		['SOS',''],
		['SRD',''],
		['SSP',''],
		['STD',''],
		['SYP',''],
		['SZL',''],
		['THB',''],
		['TJS',''],
		['TMT',''],
		['TND',''],
		['TOP',''],
		['TRY',''],
		['TTD',''],
		['TWD',''],
		['TZS',''],
		['UAH',''],
		['UGX',''],
		['USD',''],
		['USN',''],
		['USS',''],
		['UYU',''],
		['UZS',''],
		['VEF',''],
		['VND',''],
		['VUV',''],
		['WST',''],
		['XAF',''],
		['XAG',''],
		['XAL',''],
		['XAU',''],
		['XBA',''],
		['XBB',''],
		['XBC',''],
		['XBD',''],
		['XCD',''],
		['XCP',''],
		['XDR',''],
		['XFO',''],
		['XFU',''],
		['XOF',''],
		['XPD',''],
		['XPF',''],
		['XPT',''],
		['XTS',''],
		['XXX',''],
		['YER',''],
		['ZAR',''],
		['ZMW',''],
		['ZWL','']
	],
	
	constructor: function(cfg) {
		var me = this;
		Ext.each(me.config.data, function(row) {
			row[1] = row[0];
		});
		me.callParent([cfg]);
	}
});
