var urlCWizardTramite	= context_path + '/wizard/tramite/actualizar/datos/';

var dialogoConfirmarCancelar;
var dialogoConfirmar;
var fecExpedicionDatePicker;
var fecRegistroDatePicker;
var minDate = 364;
var idForma = 'soForm';

$(document).ready(function() {
	// Se incializa el blockUI para las peticiones AJAX
	$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
	
	fecExpedicionDatePicker = $("#fecExpedicion").datepicker({
		showOn : "both",
		buttonImage: context_path + "/static/resources/imagenes/calendar.gif",
		buttonImageOnly: true,
		dateFormat: "dd/mm/yy",
		changeMonth : true,
		changeYear : true
		//minDate : -minDate
	});
	
	fecRegistroDatePicker = $("#fecRegistro").datepicker({
		showOn : "both",
		buttonImage: context_path + "/static/resources/imagenes/calendar.gif",
		buttonImageOnly: true,
		dateFormat: "dd/mm/yy",
		changeMonth : true,
		changeYear : true
		//minDate : -minDate
	});
	
	// se renderea la tabla de resultados del sat como dataTable
	$('#tabla-imss-sat').dataTable({
		bInfo : false,
		sPaginationType : null,
		bJQueryUI : false,
		bSort : false,
		bFilter : false,
		bPaginate : false,
		bAutoWidth : true

	});
	
	$('#esSindicato').click(function() {
		habilitaSeccion();
	});
	
	habilitaSeccion();
	
	
	//Siguiente - Seleccion de RL
	$('#mostrarRLVentanilla').click(function() {
		validaDatos(procesarSolicitudVentanilla);			
	});
	
	$('#finalizarTramite').click(function() {
		validaDatos(fnInvocaFirma);	
	});

	$('#guardarTramite').click(function() {
		validaDatos(fnGuardaTramite);
	});

	$('#cancelarTramite').click(function() {
		dialogoConfirmarCancelar.dialog( "open" );
	});

	$('#cerrarWizard').click(function() {
		cerrarWizard();
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
	
	dialogoConfirmar = $( "#dialog-confirm" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false
	});
	
	
	if(parent.WizardActualizacionDatosCtrl.config.idOrigen == 2){
		/* 
		 * Al cerrar el di�logo de la firma digital,
		 * se ejecuta la funci�n para finalizar el tr�mite
		 */
		parent.FirmaDigitalCtrl.setOnCloseCallback(function() {
			
			dialogoConfirmar.dialog("option", "buttons", [ {
				text : 'ACEPTAR',
				click : function() {
					$(this).dialog('close');
				}
			}]);
			
			if(parent.FirmaDigitalCtrl.datosSalida == null) {
				$('#mensajeDialogo').text("La validaci\u00f3n de la firma no pudo ser realizada");
				dialogoConfirmar.dialog('open');
			}else {
				if(parent.FirmaDigitalCtrl.datosSalida.Resultado == 0) {
					var firmaResponse = {
						cadenaOriginal               : parent.FirmaDigitalCtrl.datosSalida.contenedores[0].cadori,
						recibo                       : parent.FirmaDigitalCtrl.datosSalida.firmas[0],
						reciboNotarial               : parent.FirmaDigitalCtrl.datosSalida.folio,
						urlAcuseFirma                : parent.FirmaDigitalCtrl.datosSalida.acuse,
						serialCertificado            : parent.FirmaDigitalCtrl.datosSalida.serie_cert,
						strIniciaVigenciaCertificado : parent.FirmaDigitalCtrl.datosSalida.vigIni,
						strFinVigenciaCertificado    : parent.FirmaDigitalCtrl.datosSalida.vigFin
					};

					firmarTramite(firmaResponse);
				} else {
					$('#mensajeDialogo').text("La validaci\u00f3n de la firma no pudo ser realizada");
					dialogoConfirmar.dialog('open');
				}
			}
		});		
	}

});

var fnInvocaFirma = function(){
	//alert("se invoca firma");
	var afectadoCopy = $.extend({},parent.AtributosPersonaCtrl.personaPortal);
	//console.log("el valor de razon social anterior antes de replace: " + afectadoCopy.nombreRazonSocial);
	afectadoCopy.nombreRazonSocial = afectadoCopy.nombreRazonSocial.replace(/\"/g,"\\\""); 
	//console.log("El valor de razon social anterior despues de replace: " + afectadoCopy.nombreRazonSocial);
	
	var nuevosDatosCopy = $.extend({},nuevosDatosPersonales);
	//console.log("el valor de razon social nuevo antes de replace: " + nuevosDatosCopy.nombreRazonSocial);
	nuevosDatosCopy.nombreRazonSocial = nuevosDatosCopy.nombreRazonSocial.replace(/\"/g,"\\\"");
	//console.log("El valor de razon social nuevo despues de replace: " + nuevosDatosCopy.nombreRazonSocial);
	
	var componenteFirma = {
			idTipoSolicitud : codigoTipoSolicitud,
			descripcionTipoSolicitud : descripcionTipoSolicitud,
			idTipoTramite : arrayCodigoTipoTramite,
			folioSolicitud : $('#folioSolicitud').val(),
			curp : datosEntradaFirma.curp,
			rfc : datosEntradaFirma.rfc,
			validarRFC : true,
			registroPatronal : datosEntradaFirma.registroPatronal,
			nombreCompleto : datosEntradaFirma.nombreCompleto,
			fechaElectronica : datosEntradaFirma.fechaElectronica,
			cad_original : $('#contenidoFirmar').val(),
			tipo_operacion : 'firmaCMS',
			firma_archivo : true,
			min_archivos : 1,
			max_archivos : 6,
			afectado: [afectadoCopy],
			afectadoNuevosValores: [nuevosDatosCopy],
			tipoAcuse: '1',
			acuse: 'CNRS'
		};

		parent.iniciarFirmaDigital(componenteFirma);
};

function habilitaSeccion(){
	if($('#esSindicato').is(':checked')){
		$('#seccionEscritura').hide();
		$('#seccionSindicato').show();
	}else{
		$('#seccionEscritura').show();
		$('#seccionSindicato').hide();
	}
}

function firmarTramite(firmaResponse) {
	var url = urlCWizardTramite + 'procesarDatosFirma';

	$.postJSON(url, firmaResponse, function(data) {
		finalizarTramite();
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog('open');
	});
}

function finalizarTramite() {
	var idSolicitudPendiente = $('#idSolicitud').val();
	var folioSolicitudPendiente = $('#folioSolicitud').val();	
	var personaFirmada = 0;
	
	if (typeof parent.AtributosPersonaCtrl !== 'undefined') {
		personaFirmada = parent.AtributosPersonaCtrl.personaFirmada.idPersona;
	}	
	if(isEmpty(personaFirmada))
		personaFirmada=0;
	
	var url = urlCWizardTramite + 'finalizar/solicitud/' + idSolicitudPendiente+'/'+personaFirmada;
	
	var persona=armaForma();
	
	$.postJSON(url, persona, function(data) {
		parent.ProcesandoSolicitudCtrl.abrir(folioSolicitudPendiente);
		cerrarWizard();
	}).error(function(data){
		$('#mensajeDialogo').text(data.error);
		dialogoConfirmar.dialog('open');
	});
}


function validaDatos(fnCallback){
	var urlValidacion = urlCWizardTramite +  'validar/datos/constitucion';
	var contenedor="#forma";
	var personaMoral=armaForma();
	var dateRegex = /^(0[1-9]|[12][0-9]|3[01])\/(0[1-9]|1[012])\/(19|20)\d\d$/;
	
	fnHideErrores(contenedor);
	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
		}
	}]);
	
	var correcto = true;
	/*
	if (typeof personaMoral.escrituraConstitutiva !== 'undefined') {
		if (personaMoral.escrituraConstitutiva.fechaExpedicion != "") {
			if (!dateRegex.test(personaMoral.escrituraConstitutiva.fechaExpedicion)) {
				fnShowError('span#fecExpedicionError', 'Formato de fecha invá1lido');
				correcto = false;
				$('#mensajeDialogo').text("La informaci\u00f3n proporcionada presenta errores en la escritura constitutiva");
				dialogoConfirmar.dialog('open');
			}
		}
	} else if (typeof personaMoral.registroSindicato !== 'undefined') {
		if (personaMoral.registroSindicato.fechaRegistro != "") {
			if (!dateRegex.test(personaMoral.registroSindicato.fechaRegistro)) {
				fnShowError('span#fecRegistroError', 'Formato de fecha invá1lido');
				correcto = false;
				$('#mensajeDialogo').text("La informaci\u00f3n proporcionada presenta errores en el sindicato");
				dialogoConfirmar.dialog('open');
			}
		}
	}
	*/
	
	if (correcto) {
		$.postJSON(urlValidacion, personaMoral, function(data) {
			fnCallback();
		}).error(function(data){
			fnProcesarErroresDeCaptura(data, contenedor);
			$('#mensajeDialogo').text("La informaci\u00f3n proporcionada presenta errores al validar las diferencias");
			dialogoConfirmar.dialog('open');
		}).error(function(data){
			$('#mensajeDialogo').text(data.mensaje);
			dialogoConfirmar.dialog('open');
		});
	}
}



var fnGuardaTramite = function guardarTramite() {
	var idSolicitudPendiente = $('#idSolicitud').val();
	var url = urlCWizardTramite +  'guardar/solicitud/'	+ idSolicitudPendiente;
	var personaMoral=armaForma();
	
	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
		}
	}]);
	
	$.postJSON(url, personaMoral, function(data) {
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog('open');
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog('open');
	});
};

function armaForma(){
var personaMoral=new Object();
	
	if($('#esSindicato').is(':checked')==false){
//		alert('Armare escritura');
		personaMoral.escrituraConstitutiva=new Object();
		personaMoral.escrituraConstitutiva.cveEscrituraConstitutiva=$('#cveEscritura').val();  
		personaMoral.escrituraConstitutiva.numEscritura=$('#numEscritura').val();
		personaMoral.escrituraConstitutiva.numNotaria=$('#numNotaria').val();
		personaMoral.escrituraConstitutiva.fechaExpedicion=$('#fecExpedicion').val();
		personaMoral.escrituraConstitutiva.folioMercantil=$('#folioMercantil').val();
		personaMoral.escrituraConstitutiva.seccion=$('#seccion').val();
		personaMoral.escrituraConstitutiva.partida=$('#partida').val();
		personaMoral.escrituraConstitutiva.volumen=$('#volumen').val();
		personaMoral.escrituraConstitutiva.foja=$('#foja').val();
		personaMoral.escrituraConstitutiva.lugarExpedicion=new Object();
		personaMoral.escrituraConstitutiva.lugarExpedicion.clave=$('#idMunicipio').val();
		personaMoral.escrituraConstitutiva.lugarExpedicion.entidadFederativa=new Object();
		personaMoral.escrituraConstitutiva.lugarExpedicion.entidadFederativa.clave=$('#idEstado').val();
		
		
		personaMoral.registroSindicato=undefined;
	}else{
//		alert('Armare sindicato');
		personaMoral.registroSindicato=new Object();
		personaMoral.registroSindicato.cveRegistroSindicato=$('#cveSindicato').val();
		personaMoral.registroSindicato.numReferenciadocRegistro=$('#numRerencia').val();
		personaMoral.registroSindicato.fechaRegistro=$('#fecRegistro').val();
		personaMoral.registroSindicato.autoridadLaboral=$('#autLaboral').val();
		personaMoral.escrituraConstitutiva=undefined;
	}
	return personaMoral;
}

function cancelarTramite() {
	var idSolicitudPendiente = $('#idSolicitud').val();
	var url = urlCWizardTramite + 'cancelar/solicitud/'	+ idSolicitudPendiente;
	
	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
			cerrarWizard();
		}
	}]);
	
	$.postJSON(url, null, function(data) {
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog('open');
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog('open');
	});
}

function cerrarWizard() {	
	parent.WizardActualizacionDatosCtrl.cerrar();
}

function isEmpty(valor) {
	return (valor == undefined || valor == "");
}

function prepararRequest(sSource, data, async, callback) {
	var request = $.ajax({
		url : sSource,
		async : async,
		type : "POST",
		data : data ? JSON.stringify(data) : null,
		dataType : "json",
		contentType : "application/json; charset=utf-8"
	});
	request.done(callback);
	request.fail(callback);
}

function mostrarDialogoH(mensaje) {	
	$('#mensajeDialogo').html(mensaje);
	dialogoConfirmarCommon.dialog('open');
}

//Funciones para mostrar seleccion de Representantes Legales
function procesarSolicitudVentanilla() {
	var idSolicitudPendiente = $('#idSolicitud').val();
	var folioSolicitudPendiente = $('#folioSolicitud').val();
	var personaFirmada = 0;
	if (typeof parent.AtributosPersonaCtrl !== 'undefined') {
		personaFirmada = parent.AtributosPersonaCtrl.personaFirmada.idPersona;
	}
	if(isEmpty(personaFirmada))
		personaFirmada=0;
		
	var url = urlCWizardTramite + 'finalizar/solicitud/' + idSolicitudPendiente+'/'+personaFirmada;
	var persona=armaForma();
	
	prepararRequest(url, persona, true, mostrarRepresentantesLegales);
	
}

function mostrarRepresentantesLegales(response){	
	if (response.mensaje != undefined && response.mensaje != null) {
		var idSolicitudPendiente = $('#idSolicitud').val();
		setTimeout(function(){
			var urlAction = parent.representantesLegalesCtrl.config.urlRLVentanilla 
				+ idSolicitudPendiente+"/"+idTipoTramite;
			$.blockUI();
			$('form#'+idForma).attr('action', urlAction);
			$('form#'+idForma).submit();
		}, 90);
	}else{
		var mensaje = "Ocurrio un error al procesar la solicitud.";
		if (response.error == "") {			
		} else {
			mensaje = response.error;
		}
		mostrarDialogoH(mensaje);
	}	
}