
var dialogoConfirmarCancelar;
var dialogoConfirmar;


$(document).ready(function(){
	
	$('#btnInciaTramite').click(function(){
		
		validaForma();
	});
	
	$('#btnInicioCancelarTramite').click(function(){
		//cancelarInicioTramtieRegistroUsuario();
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
		 	"Cancelar": function() {
		 		$( this ).dialog( "close" );
		 	},
		 	"Aceptar": function() {
				parent.WizardRegistroUsuarioCtrl.cerrar();
		 	}
		 }
	 });
	
	dialogoConfirmar = $( "#dialog-confirm" ).dialog({
		resizable: false,
		height:200,
		modal: true,
		autoOpen: false,
		buttons: {
			"Aceptar": function() {
				parent.registroUsuarioDatosBasicosWizard.cerrar();
		 	}
		 }
	 });
});


function validaForma(){
	//var strcurp = $('#curp').val();
	var url = '/gestionIndividuo-consulta-web/wizard/tramite/registro/usuario/validar/datos';
	var oForm = $("form#fisicaForm").toObject();
	
	$.postJSON(url, oForm, function(data) {
		firmarRegistroUsuario();
	}).error(function(data2){
		fnProcesarErrores(data2, "form#fisicaForm");
	});
	
}



function firmarRegistroUsuario() {
		
		parent.FirmaDigitalCtrl.setOnCloseCallback(function() {
			if(parent.FirmaDigitalCtrl.datosSalida == null) {
				mostrarMensaje("La validaci&oacute;n de la autenticaci&oacute;n no pudo ser realizada");
			}else {
				if(parent.FirmaDigitalCtrl.datosSalida.Resultado == 0) {
					var rfcFirma = parent.FirmaDigitalCtrl.datosSalida.rfc;
					var curpFirma = parent.FirmaDigitalCtrl.datosSalida.curp;
					var curpLocal = $('#curp').val();
					if(curpLocal.toUpperCase() != curpFirma.toUpperCase()){
						mostrarMensaje("La CURP capturada no coincide con la registrada en el certificado. Verifica y vuelve a intentar.");
					}else{
						$('#rfc').val(rfcFirma) ;
						$("#fisicaForm").submit();
					}
					
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
		text : 'Aceptar',
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