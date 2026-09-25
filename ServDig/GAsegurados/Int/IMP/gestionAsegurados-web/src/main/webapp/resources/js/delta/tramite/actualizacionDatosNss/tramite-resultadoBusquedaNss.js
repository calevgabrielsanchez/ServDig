/**
 * 
 */


$.getScript("/gestionIndividuo-consulta-web/static/resources/js/delta/personas/fisica/modificacion/manual/modificacion-manual-datos.js").error(function() {
	alert("no fue posible cargar MDM");
});
$.getScript("/gestionIndividuo-consulta-web/static/resources/js/delta/personas/fisica/identificar/cambios-automaticos/identificar-cambios-automaticos.js").error(function() {
	alert("no fue posible cargar ICA");
});

var dialogoConfirmar;

$(document).ready(
		
	function() {
		$("#continuar").click(
			function() {
				llamarICAMDM();
			}
		);
		
		$("#regresar").click(
			function() {
				var url = context_path + "/tramite/actualiza/datos/iniciar";
				var form = $('form#registroPersonaFisicaForm').attr('action' , url);
				form.attr('method' , 'get');
				form.submit();
			}	
		);
		
		$("form#registroPersonaFisicaForm input").keypress(function(event) {
		    if (event.which == 13) {
		        event.preventDefault();
		    }
		});
		
		$("form#registroPersonaFisicaForm input#curp").live(
				'blur', function() {
			var curpRe = $.trim($("#curp").val().toUpperCase());
			$("#curp").val(curpRe);
		});
		
		dialogoConfirmar = $( "#dialog-confirm" ).dialog({
			resizable: false,
			height:'auto',
			modal: true,
			autoOpen: false,
			dialogClass: "no-close",
		    closeOnEscape: false
		 });
	}
	
);

function llamarICAMDM() {
	var curpRegistro = $("#curp").val();
	
	if($.trim(curpRegistro).length > 0) {
		var url = '/gestionAsegurados-web/tramite/actualiza/datos/validar/curp';
		
		var oForm = $("form#registroPersonaFisicaForm").toObject();
		fnHideErrores("form#registroPersonaFisicaForm");
		
		$.postJSON(url, oForm, function(data) {
			llamarICA();
		}).error(function(data2){
			fnProcesarErrores(data2, "form#registroPersonaFisicaForm");
		});
	} else {
		llamarMDM();
	}
}

function llamarMDM() {
	//inicializamos el objeto del mdm
	ModificacionManualDatosFisicaCtrl.init('dialogMDM'); 

	//parametros de entrda de mdm
	var datosEntradaMDM = {
		idPersona : $('#idPersona').val(),
		indCapturaNombre : true,
		indCapturaCURP : false,
		indCapturaSexo : true, 
		indCapturaFechaNacimiento : true,
		indCapturaLugarNacimiento : true,
		indCapturaDocumentoProbatorio : false,
		indCapturaRFC  : false,
		indCapturaDomicilioFiscal : false,
		indCapturaMediosContactoFiscales : false,
		indCapturaDomicilioParticular : false,
		indCapturaMediosContactoParticular : false,
		indAutorizacion : false
	}
	
	//Establecemos los datos de entrada del MDM
	ModificacionManualDatosFisicaCtrl.datosEntrada = datosEntradaMDM;
	
	//establecemos el callback
	ModificacionManualDatosFisicaCtrl.setOnCloseCallback(callbackMDM);
	
	//llamamos al servicio de modificacion manuel
	ModificacionManualDatosFisicaCtrl.modificacionManual();
}

function llamarICA() {
	//inicializamos el objeto del ica
	parent.identificarCambiosAutomaticosPersonaFisicaCtrl.init('dialogICA');
	
	//parametros para iniciar la pantalla de ica
	var datosEntradaICA = {
		indMostrarPantalla : true,
		idPersona : $('#idPersona').val(),
		curp : $('#curp').val(),
		indBusquedaRENAPO : true,
		indUsuarioExterno : false,
		rfc: '',
		nombrePersona : '',
		primerApellido :'',
		segundoApellido : '',
		indBusquedaSAT : false
	}
	//Establecemos los parametros de entrada
	parent.identificarCambiosAutomaticosPersonaFisicaCtrl.datosEntrada = datosEntradaICA;
	
	//establecemos el callback
	parent.identificarCambiosAutomaticosPersonaFisicaCtrl.setOnCloseCallback(callbackICA); 

	//abrimos la pantalla del ica
	parent.identificarCambiosAutomaticosPersonaFisicaCtrl.identificarCambios();
}

var callbackICA = function () {
	//Obtenemos los datos de salida del ica
	var datosSalidaICA = parent.identificarCambiosAutomaticosPersonaFisicaCtrl.getDatosSalida();
	if(datosSalidaICA != null) {
		
		if(validarCambiosICA(datosSalidaICA)) {
			var url = "/gestionAsegurados-web/tramite/actualiza/datos/guardarICA";
			
			$.postJSON(url,datosSalidaICA,
				function(data) {
				var url = context_path + "/tramite/actualiza/datos/finalizar";
				var form = $('form#registroPersonaFisicaForm').attr('action' , url);
				form.attr('method' , 'post');
				form.submit();
			});
		} else {
			mostrarMensaje("La informaci&oacute;n est&aacute; actualizada, no hay ning&uacute;n cambio por aplicar");
		}
	} else {
		mostrarMensaje("La informaci&oacute;n est&aacute; actualizada, no hay ning&uacute;n cambio por aplicar");
	}
}

var callbackMDM = function () {
	var datosSalidaMDM = ModificacionManualDatosFisicaCtrl.getDatosSalida();
	if(datosSalidaMDM != null) {
		
		if(datosSalidaMDM.mdmDatosEntrada != null) {
			
			if(validarCambiosMDM(datosSalidaMDM.mdmDatosEntrada)) {
				var url = "/gestionAsegurados-web/tramite/actualiza/datos/guardarMDM";
				
				$.postJSON(url,datosSalidaMDM.mdmDatosEntrada,
					function(data) {
					var url = context_path + "/tramite/actualiza/datos/finalizar";
					var form = $('form#registroPersonaFisicaForm').attr('action' , url);
					form.attr('method' , 'post');
					form.submit();
				});
				
			} else {
				mostrarMensaje("La informaci&oacute;n est&aacute; actualizada, no hay ning&uacute;n cambio por aplicar");
			}
		} else {
			mostrarMensaje("La informaci&oacute;n est&aacute; actualizada, no hay ning&uacute;n cambio por aplicar");
		}
	}
}

var validarCambiosICA = function(ica) {
	
	if(ica.personaFisicaIMSS != null) {
		return true;
	}
	
	return false;
}

var validarCambiosMDM = function(mdm) {
	var huboCambios = false;
	if(mdm != null) {
		var cambios = mdm.cambios;
		
		if(cambios.nombre != "NINGUNO") {
			return true;
		}
		if(cambios.primerApellido != "NINGUNO") {
			return true;
		}
		if(cambios.segundoApellido != "NINGUNO") {
			return true;
		}
		if(cambios.sexo != "NINGUNO") {
			return true;
		}
		if(cambios.nacionalidad != "NINGUNO") {
			return true;
		}
		if(cambios.fechaNacimiento != "NINGUNO") {
			return true;
		}
		
	} 
	
	return false;
}

function mostrarMensaje(mensaje) {
	$('#mensajeDialogo').html(mensaje);

	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
		}
	}]);
	
	dialogoConfirmar.dialog('open');
	
}