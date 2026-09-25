var urlWizardBajaRepresentante	= context_path + '/wizard/tramite/representante/baja/';

var dialogoConfirmarCancelar;
var dialogoConfirmar;

$(document).ready(function() {
	// Se incializa el blockUI para las peticiones AJAX
	$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);

	$('#finalizarTramite').click(function() {
		var representados = getRepresentantesSeleccionados();
		
		if(representados.length == 0) {
			mostrarMensajeSeleccionarRepresentantes();
		} else {
			crearArrayRepresentantes();
		}
	});
	
	//Siguiente - Seleccion de RL
	$('#mostrarRLVentanilla').click(function() {
		var representados = getRepresentantesSeleccionados();
		
		if(representados.length == 0) {
			mostrarMensajeSeleccionarRepresentantes();
		} else {
			crearArrayRepresentantes();
		}		
	});
	
	$('#seleccionarPatrones').click(function() {
		buscarCURP();
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
		dialogClass: "no-close",
	    closeOnEscape: false,
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
		autoOpen: false,
		dialogClass: "no-close",
	    closeOnEscape: false
	 });
});

function firmarRepresentante(representantes) {
	
	var rfcRepresentante = parent.WizardBajaRepresentateLegalCtrl.config.rfcPersona;
	
	parent.FirmaDigitalCtrl.setOnCloseCallback(function() {
		
		if(parent.FirmaDigitalCtrl.datosSalida == null) {
			mostrarMensaje("La validaci&oacute;n de la firma no pudo ser realizada");
		}else {
			if(parent.FirmaDigitalCtrl.datosSalida.Resultado == 0) {
				var firmaResponse = {
					cadenaOriginal               : parent.FirmaDigitalCtrl.datosSalida.contenedores[0].cadori,
					recibo                       : parent.FirmaDigitalCtrl.datosSalida.contenedores[0].cms,
					reciboNotarial               : parent.FirmaDigitalCtrl.datosSalida.folio,
					urlAcuseFirma                : parent.FirmaDigitalCtrl.datosSalida.acuse,
					serialCertificado            : parent.FirmaDigitalCtrl.datosSalida.serie_cert,
					strIniciaVigenciaCertificado : parent.FirmaDigitalCtrl.datosSalida.vigIni,
					strFinVigenciaCertificado    : parent.FirmaDigitalCtrl.datosSalida.vigFin
				};

				firmarTramite(firmaResponse);
			} else {
				mostrarMensaje("La validaci&oacute;n de la firma no pudo ser realizada");
			}
		}
	});

	var componenteFirma = {
		idTipoSolicitud : codigoTipoSolicitud,
		descripcionTipoSolicitud : descripcionTipoSolicitud,
		folioSolicitud : $('#folioSolicitud').val(),
		curp : parent.FirmanteCtrl.curp,
		idTipoTramite : arrayCodigoTipoTramite,
		rfc : parent.FirmanteCtrl.rfc,
		validarRFC : true,
		registroPatronal : datosEntradaFirma.registroPatronal,
		nombreCompleto : parent.FirmanteCtrl.nombreRazonSocial,
		fechaElectronica : datosEntradaFirma.fechaElectronica,
		cad_original : $('#contenidoFirmar').val(),
		tipo_operacion : 'firmaCMS',
		firma_archivo : false,
		min_archivos : 0,
		max_archivos : 0,
		representados: representantes,
		afectado: [parent.AtributosPersonaCtrl.personaPortal],
		acuse: 'BRL',
		tipoAcuse: 1
	};
	
	parent.iniciarFirmaDigital(componenteFirma);
}

function construirAfectado() {
	var personaAfectada = new Object();
	personaAfectada.nombreRazonSocial = datosEntradaFirma.nombreCompleto;
	personaAfectada.rfc = datosEntradaFirma.rfc;
	personaAfectada.curp = datosEntradaFirma.curp;
	
	return personaAfectada;
}

function firmarTramite(firmaResponse) {
	var url = urlWizardBajaRepresentante + 'procesarDatosFirma';

	$.postJSON(url, firmaResponse, function(data) {
		finalizarTramite();
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog('open');
	});
}

function guardarTramite() {
	
	var representados = getRepresentantesSeleccionados();	
	if(representados.length == 0) {
		mostrarMensajeSeleccionarRepresentantes();
	} else {
		var url = urlWizardBajaRepresentante + 'solicitud/guardar';
		var persona = {
			'representantesLegales' : representados
		};		
		
		$.postJSON(url, persona , function(data) {
			mostrarMensaje(data.mensaje);
		}).error(function(data){
			mostrarMensaje(data.mensaje);
		});
	}
}

function crearArrayRepresentantes() {
	var representantes = new Array();	
	var url = urlWizardBajaRepresentante + 'get/seleccionados';
	var representados = getRepresentantesSeleccionados();	
	var persona = {
		'representantesLegales' : representados
	};
	
	if(representados.length > 0) {
		$.postJSON(url,persona,function(data) {
			var representantesLegalesActuales = data.rLSeleccionados;			
			representados = getRepresentantesSeleccionados();			
			persona = {
				'representantesLegales' : representados
			};			
			
			for(var i= 0;i<representantesLegalesActuales.length;i++) {
				for(var j=0; j < representados.length; j++) {
					if(representantesLegalesActuales[i].cveIdRepresentanteLegal == representados[j].cveIdRepresentanteLegal) {
						var fisica = representantesLegalesActuales[i].personaFisica;
						var patronRec = new Object();
						
							patronRec.nombreRazonSocial= ""+ fisica.nombre + " " + fisica.primerApellido +" " + fisica.segundoApellido;
							patronRec.rfc = fisica.rfc;
							patronRec.curp = fisica.curp;
						
						representantes.push(patronRec);
					}
				}
			}
			
			if(parent.WizardBajaRepresentateLegalCtrl.config.idOrigen == 2){
				//INTERNET
				firmarRepresentante(representantes);
			}else{
				//VENTANILLA				
				var url = urlWizardBajaRepresentante + 'validarSolicitud';
				prepararRequest(url, persona, true, validarSolicitud);				
			}
			
		});
		
	}
}

function validarSolicitud(response) {
	if (response.mensaje != undefined && response.mensaje != null) {
		if(parent.WizardBajaRepresentateLegalCtrl.config.idOrigen == 2){
			finalizarTramite();	
		}else{
			procesarSolicitudVentanilla();
		}		
	}else{
		mostrarMensajeRepresentanteActivo(response.error);
	}
}

function finalizarTramite(){
	var folioSolicitudPendiente = $("#folioSolicitud").val();
	var idSolicitudPendiente = $("#idSolicitud").val();
	
	var url = urlWizardBajaRepresentante + 'solicitud/finalizar/'+idSolicitudPendiente;
	var representados = getRepresentantesSeleccionados();
	var persona = {
		'representantesLegales' : representados
	};
	
	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
			cerrarWizard();
		}
	}]);
	
	$.postJSON(url, persona , function(data) {
		parent.ProcesandoSolicitudCtrl.abrir(folioSolicitudPendiente);
		cerrarWizard();
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog('open');
	});
}

//Funciones para mostrar seleccion de Representantes Legales
function procesarSolicitudVentanilla() {
	var folioSolicitudPendiente = $("#folioSolicitud").val();
	var idSolicitudPendiente = $("#idSolicitud").val();
	var url = urlWizardBajaRepresentante + 'solicitud/finalizar/'+idSolicitudPendiente;
	var representados = getRepresentantesSeleccionados();
	var persona = {
		'representantesLegales' : representados
	};

	
	prepararRequest(url, persona, true, mostrarRepresentantesLegales);
	
}

function mostrarRepresentantesLegales(response){
	if (response.mensaje != undefined && response.mensaje != null) {
		var idSolicitudPendiente = $('#idSolicitud').val();
		setTimeout(function(){
			var urlAction = parent.representantesLegalesCtrl.config.urlRLVentanilla + idSolicitudPendiente+"/"+idTipoTramite;
			$.blockUI();
			$('form#soForm').attr('action', urlAction);
			$('form#soForm').submit();
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

function mostrarMensajeRepresentanteActivo(mensaje) {
	$('#mensajeDialogo').html(mensaje);

	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
		}
	}]);
	
	dialogoConfirmar.dialog('open');
}


function getRepresentantesSeleccionados() {
	var representantes = new Array();
	$("input[@name='representanteL']:checked").each(function() {
		var idRep = $(this).val();
		 if($.trim(idRep).length > 0)
			 representantes.push({"cveIdRepresentanteLegal": idRep});
	 });
	
	return representantes;
}

function mostrarMensajeSeleccionarRepresentantes() {
	$('#mensajeDialogo').text("Debe seleccionar al menos una empresa representada a dar de baja");

	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
		}
	}]);
	
	dialogoConfirmar.dialog('open');
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

function cancelarTramite() {
	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
			cerrarWizard();
		}
	}]);
	
	var idSolicitudPendiente = $('#idSolicitud').val();
	var url = urlWizardBajaRepresentante + 'solicitud/cancelar/'+ idSolicitudPendiente;
	
	$.postJSON(url, null, function(data) {
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog( "open" );
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.open();
	});
}

function cerrarWizard() {	
	parent.WizardBajaRepresentateLegalCtrl.cerrar();
}

