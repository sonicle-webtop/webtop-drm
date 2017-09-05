/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
Ext.define('Sonicle.webtop.drm.model.ServiceVars', {
	extend: 'WTA.sdk.model.ServiceVars',
	fields: [
		WTF.field('useStatisticCustomer', 'boolean', false), //nomeCampo,tipo,allowBlank
		WTF.field('printDaysTransfert', 'boolean', false), //nomeCampo,tipo,allowBlank
		WTF.field('printTransfertDescription', 'boolean', false), //nomeCampo,tipo,allowBlank
		WTF.field('printSignature', 'boolean', false),
		WTF.field('roundingHour', 'int', true),
		WTF.field('tracking', 'boolean', true),
		WTF.field('mailTracking', 'boolean', true),
		WTF.field('cloudTracking', 'boolean', true)
		//nomeCampo,tipo,allowBlank//nomeCampo,tipo,allowBlank
	]
});