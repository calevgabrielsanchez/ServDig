
var dialogoConfirmarCancelar;
var dialogoConfirmar;


$(document).ready(function(){
	
	$('#btnInciaTramite').click(function(){
		validaForma();
	});
	
	$('#btnInicioCancelarTramite').click(function(){
		dialogoConfirmarCancelar.dialog( "open" );
	});
	
	$("form input").keypress(function(event) {
	    if (event.which == 13) {
	        event.preventDefault();
	    }
	});
	
	dialogoConfirmarCancelar = $( "#dialog-confirm-cancelar" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false,
		buttons: {
			"ACEPTAR": function() {
				parent.WizardRenovacionFielCtrl.cerrar();
		 	},
		 	"CANCELAR": function() {
		 		$( this ).dialog( "close" );
		 	}
		 }
	 });
	
	dialogoConfirmar = $( "#dialog-confirm" ).dialog({
		resizable: false,
		height:200,
		modal: true,
		autoOpen: false,
		buttons: {
			"ACEPTAR": function() {
				parent.registroUsuarioDatosBasicosWizard.cerrar();
		 	}
		 }
	 });
});


function validaForma(){
	//var strcurp = $('#curp').val();
	var url = '/gestionIndividuo-consulta-web/wizard/tramite/renovacion/fiel/validar/datos';
	var oForm = $("form#usuarioForm").toObject();
	fnHideErrores("form#usuarioForm");
	
	$.postJSON(url, oForm, function(data) {
		firmarRegistroUsuario();
	}).error(function(data2){
		fnProcesarErrores(data2, "form#usuarioForm");
	});
	
}



function firmarRegistroUsuario() {
		
		parent.FirmaDigitalCtrl.setOnCloseCallback(function() {
			if(parent.FirmaDigitalCtrl.datosSalida == null) {
				mostrarMensaje("La validaci&oacute;n de la FIEL no pudo ser realizada");
			}else {
				if(parent.FirmaDigitalCtrl.datosSalida.Resultado == 0) {
					var rfcFirma = parent.FirmaDigitalCtrl.datosSalida.rfc;
					var curpFirma = parent.FirmaDigitalCtrl.datosSalida.curp.toUpperCase();
					var serie = parent.FirmaDigitalCtrl.datosSalida.serie_cert;
					
					var curpLocal = $('#usuario').val().toUpperCase();
					
					$('#usuario').val(curpLocal);
					$('#fisica\\.curp').val(curpFirma) ;
					$('#fisica\\.rfc').val(rfcFirma) ;
					$('#password').val(serie);
					
					$("#usuarioForm").submit();
					
				} else {
					mostrarMensaje("La validaci&oacute;n de tus  datos no pudo ser concluida en este momento, por favor intenta m&aacute;s tarde.");
				}
			}
			
		});

		var fechaSistema = new Date();

		var componenteFirma = {
			idTipoSolicitud : codigoTipoSolicitud,
			descripcionTipoSolicitud : descripcionTipoSolicitud,
			idTipoTramite : arrayCodigoTipoTramite,
			folioSolicitud : '',
			curp : $('#curp').val(),
			rfc : $('#rfcPersona').val(),
			validarRFC : false,
			registroPatronal : '',
			nombreCompleto : '',
			fechaElectronica : dateFormat(fechaSistema, "dd/mm/yy"),
			cad_original : 'Autenticacion',
			tipo_operacion : 'autentica',
			firma_archivo : false,
			min_archivos : 0,
			max_archivos : 0
		};

		parent.iniciarFirmaDigital(componenteFirma);	
}

function mostrarMensaje(mensaje) {
	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
		}
	}]);
	
	$('#mensajeDialogo').html(mensaje);
	dialogoConfirmar.dialog('open');
}


function cancelarInicioTramtieRegistroUsuario() {
	parent.WizardRegistroUsuarioCtrl.cerrar();
}