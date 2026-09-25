var dialogoConfirmarCancelar;
var dialogoConfirmar;

$(document).ready(function() {

	$('#btnInciaTramite').click(function() {
		inicarTramite();
	});
			

	$('#btnInicioCancelarTramite').click(function(){
		cerrarWizard();
	});
	

	dialogoConfirmarCancelar = $("#dialog-confirm-cancelar").dialog({
		resizable : false,
		height : 'auto',
		modal : true,
		autoOpen : false,
		buttons : {
			"ACEPTAR" : function() {
				cancelarTramite();
			},
			"CANCELAR" : function() {
				$(this).dialog("close");
			}
		}
	});
	
	dialogoConfirmarCommon = $( "#dialog-confirm-common" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false
	 });

	setSizeWithinIframe(document, 900);
});

	function inicarTramite() {
		$("#formIniciaTramite").attr('action', 'http://zidsetest.imss.gob.mx:7003/imss/AccesoIDSE.idse?siteId='+idSite+'&idUsuario=' +parent.AtributosPersonaCtrl.personaPortal.rfc+
			'&rfc_fiel='+parent.AtributosPersonaCtrl.personaFirmada.rfc+'&peticion_acceso=0');
		
		//alert ($("#formIniciaTramite").attr('action'));

		
		$("#formIniciaTramite").submit();
		
	}

	function cerrarWizard() {	
		parent.wizardRegistroMovAfiliatorios.cerrar();
	}
	
	
	
	
	
	