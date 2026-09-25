var urlBeneficio 		= '/${mvn.web.app.root}/wizard/tramite/solicitar/riss';
var idFormaBeneficio	= 'soForm';
var dialogoConfirmarCancelar;
var dialogoConfirmarCommon;

$(document).ready(function(){
	$('#btnInciarTramite').click(function(){
		inicarTramite();
	});
		
	$('#btnCancelarInicioTramite').click(function(){
		cancelarInicioTramite();
	});
	
	$('#btnCancelarTramite').click(function() {
		dialogoConfirmarCancelar.dialog( "open" );
	});
		
	dialogoConfirmarCancelar = $( "#dialog-confirm-cancelar" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false,
		buttons: {
			"ACEPTAR": function() {
				cancelarTramite();
		 	},
		 	"CANCELAR": function() {
		 		$( this ).dialog( "close" );
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
	var url = urlBeneficio+'/crear/solicitud';
	
	$('form#'+idFormaBeneficio).attr('action',url);
	$('form#'+idFormaBeneficio).submit();
}

function cancelarInicioTramite() {
	parent.WizardSolicitarRifCtrl.cerrar();
}

function cancelarTramite() {
	var idSolicitudRegistrada = $('#idSolicitudRegistrada').val();
	var url = urlBeneficio+'/cancelar/solicitud/'+ idSolicitudRegistrada;
	
	dialogoConfirmarCommon.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
			cerrarWizard();
		}
	}]);
	
	$.postJSON(url, null, function(data) {
		mostrarDialogo(data.mensaje);
	}).error(function(data){
		mostrarDialogo(data.mensaje);
	});
}

function cerrarWizard() {	
	parent.WizardSolicitarRifCtrl.cerrar();
}

function mostrarDialogo (mensaje) {
	$('#mensajeDialogo').text(mensaje);
	dialogoConfirmarCommon.dialog('open');
}