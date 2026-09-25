var urlWizardBajaRepresentado 	= context_path + '/wizard/tramite/representado/baja/';
var dialogoConfirmarCancelar;
var dialogoConfirmar;

$(document).ready(function() {
	// Se incializa el blockUI para las peticiones AJAX
	$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
	
	/*
	 * se iniciliza el componente del acordeon con la opcion 'autoHeight: false'
	 * para que cada DIV colapsable tenga la altura de acuerdo a su contenido
	 */
	$('#acordeon').accordion({
		autoHeight : false,
		collapsible : true
	});

	$('#finalizarTramite').click(function() {
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

function buscarCURP() {
	$("#formPersona").submit();
}

function muestraMensajeFirma() {
	dialogoConfirmar.text('A continuacion se le pedira la informacion de la FIEL de la empresa a representar');
	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			firmarRepresentado();
		}
	}, {
		text : 'CANCELAR',
		click : function() {
			$(this).dialog('close');
		}
	}]);
	
	dialogoConfirmar.dialog('open');
}

function construirAfectado() {
	var personaAfectada = new Object();
	personaAfectada.nombreRazonSocial = datosEntradaFirma.nombreCompleto;
	personaAfectada.rfc = datosEntradaFirma.rfc;
	personaAfectada.curp = datosEntradaFirma.curp;
	
	return personaAfectada;
}

function crearArrayRepresentantes() {
	var representantes = new Array();
	
	var url = urlWizardBajaRepresentado + 'get/seleccionados';
	var representados = getRepresentantesSeleccionados();
	var persona = {
		'representantesLegales' : representados
	};
	
	if(representados.length > 0) {
		$.postJSON(url,persona,function(data) {
			
			var representantesLegalesActuales = data.rLSeleccionados;
			
			for(var i= 0;i<representantesLegalesActuales.length;i++) {
				for(var j=0; j < representados.length; j++) {
					if(representantesLegalesActuales[i].cveIdRepresentanteLegal == representados[j].cveIdRepresentanteLegal) {
						var representado = representantesLegalesActuales[i].sujetoObligado;
						var patronRec = new Object();
						
						if(representantesLegalesActuales[i].personaFisicaRepresentada != null) {
							patronRec.idPersona = representantesLegalesActuales[i].personaFisicaRepresentada.idPersona;
							patronRec.nombreRazonSocial= ""+ representantesLegalesActuales[i].personaFisicaRepresentada.nombre + " " + representantesLegalesActuales[i].personaFisicaRepresentada.primerApellido +" " + representantesLegalesActuales[i].personaFisicaRepresentada.segundoApellido;
							patronRec.rfc = representantesLegalesActuales[i].personaFisicaRepresentada.rfc;
							patronRec.curp = representantesLegalesActuales[i].personaFisicaRepresentada.curp;
						} else {
							patronRec.idPersona = representantesLegalesActuales[i].personaMoralRepresentada.idPersona;
							patronRec.nombreRazonSocial= ""+ representantesLegalesActuales[i].personaMoralRepresentada.razonSocial;
							patronRec.rfc = representantesLegalesActuales[i].personaMoralRepresentada.rfc;
							
						}
						
						representantes.push(patronRec);
					}
				}
			}
			
			if(parent.WizardBajaRepresentadoLegalCtrl.config.idOrigen == 2){
				//INTERNET
				firmarRepresentante(representantes);
			}else{
				//VENTANILLA
				finalizarTramite();
			}
			
			
		});
		
	}
}

function firmarRepresentante(representados) {
	
	var rfcRepresentante = parent.WizardBajaRepresentadoLegalCtrl.config.rfcPersona;
	
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
		idTipoTramite : arrayCodigoTipoTramite,
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
		representados: representados,
		afectado: [construirAfectado()],
		tipoAcuse: '1',
		acuse: 'BRL'
	};

	parent.iniciarFirmaDigital(componenteFirma);
}

function firmarTramite(firmaResponse) {
	var url = urlWizardBajaRepresentado + 'procesarDatosFirma';

	$.postJSON(url, firmaResponse, function(data) {
		finalizarTramite();
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog('open');
	});
}

function finalizarTramite(){	
	var url = urlWizardBajaRepresentado + 'solicitud/finalizar';
	var representados = getRepresentantesSeleccionados();
	var persona = {
		'representantesLegales' : representados
	};
		
	var folioSolicitudPendiente = $("#folioSolicitud").val();
	var idSolicitudPendiente = $("#idSolicitud").val();
		
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

function guardarTramite() {	
	var representados = getRepresentantesSeleccionados();
	
	if(representados.length == 0) {
		mostrarMensajeSeleccionarRepresentantes();
	} else {
		var url = urlWizardBajaRepresentado + 'solicitud/guardar';
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

function firmarRepresentado() {

	parent.FirmaDigitalCtrl.setOnCloseCallback(function() {
			if(parent.FirmaDigitalCtrl.datosSalida == null) {
				mostrarMensaje("La validaci&oacute;n de la autenticaci&oacute;n no pudo ser realizada");
			}else {
				if(parent.FirmaDigitalCtrl.datosSalida.Resultado == 0) {
					$("#forma").submit();
				} else {
					mostrarMensaje("La validaci&oacute;n de la autenticaci&oacute;n no pudo ser realizada");
				}
			}
	});
		
	var fechaSistema = new Date();
		
	var componenteFirma = {
			idTipoSolicitud : codigoTipoSolicitud,
			descripcionTipoSolicitud : descripcionTipoSolicitud,
			folioSolicitud : '',
			idTipoTramite : arrayCodigoTipoTramite,
			curp : '',
			rfc : $('#rfc').val(),
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

function cancelarTramite() {
	
	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
			cerrarWizard();
		}
	}]);
	
	var idSolicitudPendiente = $('#idSolicitud').val();
	var url = urlWizardBajaRepresentado + 'solicitud/cancelar/'+idSolicitudPendiente;
	
	$.postJSON(url, null, function(data) {
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog( "open" );
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.open();
	});
}

function cerrarWizard() {	
	parent.WizardBajaRepresentadoLegalCtrl.cerrar();
}