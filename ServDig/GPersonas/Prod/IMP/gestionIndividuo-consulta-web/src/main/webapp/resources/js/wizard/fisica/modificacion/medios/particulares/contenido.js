var dialogoConfirmarCancelar;
var dialogoConfirmarCommon;

$(document).ready(function() {	
	// Se carga el m�dulo de medios particulares
	if($('#isRetomar').val() == 'true'){
		ejecutarRetomarAdmonMedios();
	} else {
		ejecutarAdmonMedios();
	}
	
	$('#finalizarTramite').click(function() {
		if (parent.WizardModificacionMediosParticularesCtrl.config.idOrigen == 2) {
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
				firma_archivo : false,
				min_archivos : 0,
				max_archivos : 0,
				afectado: [construirAfectado()],
				mediosContacto: obtenerMedios(),
				tipoAcuse: '1',
				acuse: 'MC'
			};
			
			parent.iniciarFirmaDigital(componenteFirma);
		} else {
			finalizarTramite();
		}
	});

	$('#guardarTramite').click(function() {
		guardarTramite();
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
				$( this ).dialog( "close" );
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
	
	
	if (parent.WizardModificacionMediosParticularesCtrl.config.idOrigen == 2) {
		/* 
		 * Al cerrar el di�logo de la firma digital,
		 * se ejecuta la funci�n para finalizar el tr�mite
		 */
		parent.FirmaDigitalCtrl.setOnCloseCallback(function() {
			dialogoConfirmarCommon.dialog("option", "buttons", [ {
				text : 'ACEPTAR',
				click : function() {
					$(this).dialog('close');
				}
			}]);
			
			if(parent.FirmaDigitalCtrl.datosSalida == null) {
				mostrarDialogo("La validaci\u00f3n de la firma no pudo ser realizada");
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
					mostrarDialogo("La validaci\u00f3n de la firma no pudo ser realizada");
				}
			}
		});
	}
	

});

function firmarTramite(firmaResponse) {
	var url = '/${mvn.web.app.root}'+'/wizard/tramite/modificar/medios/particulares/procesarDatosFirma';

	$.postJSON(url, firmaResponse, function(data) {
		finalizarTramite();
	}).error(function(data){
		mostrarDialogo(data.mensaje);
	});
}

function finalizarTramite() {
	
	var idSolicitudPendiente = $('#idSolicitud').val();
	var url = '/${mvn.web.app.root}'+'/wizard/tramite/modificar/medios/particulares/finalizar/solicitud/'
				+ idSolicitudPendiente;
	
	dialogoConfirmarCommon.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
			cerrarWizard();
		}
	}]);
		
	// Llamado a funci�n com�n para el procesamiento de los cambios
	fnProcesarMediosCommon(url, false);

}

function guardarTramite() {
	
	var idSolicitudPendiente = $('#idSolicitud').val();
	var url = '/${mvn.web.app.root}'+'/wizard/tramite/modificar/medios/particulares/guardar/solicitud/'
				+ idSolicitudPendiente;
	
	dialogoConfirmarCommon.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
		}
	}]);
	
	// Llamado a funci�n com�n para el procesamiento de los cambios
	fnProcesarMediosCommon(url, true);

}

function cancelarTramite() {
	var idSolicitudPendiente = $('#idSolicitud').val();
	var url = '/${mvn.web.app.root}'+'/wizard/tramite/modificar/medios/particulares/cancelar/solicitud/'
				+ idSolicitudPendiente;
	
	dialogoConfirmarCommon.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
			cerrarWizard();
		}
	}]);
	
	$.blockUI();
	
	$.postJSON(url, null, function(data) {
		$.unblockUI();
		mostrarDialogo(data.mensaje);
	}).error(function(data){
		$.unblockUI();
		mostrarDialogo(data.mensaje);
	});
}

function cerrarWizard() {	
	parent.WizardModificacionMediosParticularesCtrl.cerrar();
}

function ejecutarAdmonMedios(){
	var idPersona = $('#idPersona').val();
	var url = '/gestionMediosContacto-web/medios/particulares/administrar/init/' + idPersona;
	
	$.post(url, function(data) {
		$("#admonMediosContactoDiv").html(data);
	}).error(function(data) {
		
	});	
}

function ejecutarRetomarAdmonMedios(){
	var idSolicitud = $('#idSolicitud').val();
	var idPersona = $('#idPersona').val();
	var url = '/gestionMediosContacto-web/medios/particulares/administrar/init/retomar/' + idSolicitud + "/" + idPersona;
	
	$.post(url, function(data) {
		$("#admonMediosContactoDiv").html(data);
	}).error(function(data) {
		
	});	
}

function construirAfectado() {
	var personaAfectada = new Object();
	personaAfectada.nombreRazonSocial = datosEntradaFirma.nombreCompleto;
	personaAfectada.rfc = datosEntradaFirma.rfc;
	personaAfectada.curp = datosEntradaFirma.curp;
	
	return personaAfectada;
}

function obtenerMedios() {
	var medios;
	var idPersona = $('#idPersona').val();
	
	$.ajax({
		url: '/gestionMediosContacto-web/medios/particulares/administrar/obtener-medios/' + idPersona,
        type: "POST",
        dataType: "json",
        async: false,
        contentType: "application/json; charset=utf-8",
        success:  function(data) {
        	medios = data;
		}
	}).error(function(resultado){
		medios = null;
	});
	
	return medios;
}
//Funci�n com�n para ejecutar la modificaci�n de los datos de la persona
var fnProcesarMediosCommon = function (url, isGuardar) {

	$.blockUI();
	
	var resultado = null;
	var idPersona = $('#idPersona').val();
	
	/*
	 * Como las propiedades del formulario ya son clases mas complejas que 
	 * a su vez tienen otras propiedades, ya no podemos usar el metodo
	 * 'serializeObject'. En vez de, usaremos el metodo 'toObject'
	 */
	var oForm = $("form#mdmPersonaFisicaForm").toObject();
	
	// Se quitan propiedas de los datatables, que no deben ser enviados
	delete oForm.tblAdmonMediosFiscales_length;
	delete oForm.tblAdmonMedios_length;
	delete oForm.idPersona;
				
	// Se va por los medios de contacto que est�n dentro del m�dulo de gesti�n de medios
	$.getJSON('/gestionMediosContacto-web/medios/particulares/administrar/obtener-medios/' + idPersona, function(data) {
		oForm.personaFisica.mediosContacto = data;
		
		// Se ejecuta la URL recibida
		$.postJSON(url, oForm, function(data) {
			resultado = data;
			
			$.unblockUI();
			
			if (isGuardar) {
				mostrarDialogo(resultado.mensaje);
			} else {
				var folioSolicitudPendiente = $('#folioSolicitud').val();
				/*
				 * Si resultado no trae el mensaje nulo, significa que fue exitoso y, por lo
				 * tanto, se debe ejecutar la funci�n del comet para esperar la atenci�n de
				 * la solicitud
				 */
				if (resultado.mensaje == null || typeof resultado.mensaje === 'undefined') {
					parent.ProcesandoSolicitudCtrl.abrir(folioSolicitudPendiente);
					cerrarWizard();
				} else {
					mostrarDialogo(resultado.mensaje);
				}
			}
			
		}).error(function(data){
			$.unblockUI();
			fnProcesarErrores(data, "form#mdmPersonaFisicaForm");
		});
	}).error(function(data){
		$.unblockUI();
		fnProcesarErrores(data, "form#mdmPersonaFisicaForm");
	});
	
};

function mostrarDialogo (mensaje) {
	$('#mensajeDialogo').text(mensaje);
	dialogoConfirmarCommon.dialog('open');
}