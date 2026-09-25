var dialogoConfirmarCancelar;
var dialogoConfirmar;
var rpsRecuperados="";
var PATRONES_AGREGADOS=0;
$.getScript("/portal-web/static/resources/js/wizard/recuperacionRP/establecerNombreComercialWizard.js");

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

	$('#agregarPatronRecuperar').click(
		function() {
			validacionFormulario();
		}	
	);
	
	$('#finalizarTramite').click(function() {
		if(!getPatronesSeleccionados()) {
			MostrarMensajeSeleccionarPatrones();
		} else {
			firmarRepresentante();			
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
	
	dialogoPedirNombreComercial = $("#setNombreComercialDiv").dialog(
		{
			resizable: false,
			height:'auto',
			modal: true,
			autoOpen: false,
			dialogClass: "no-close",
		    closeOnEscape: false
		}
	);
});

function validacionFormulario() {
	var oForm = $("form#forma").toObject();
	var url = '/portal-web/wizard/tramite/recuperacion/patron/validaciones';
	fnHideErrores("form#forma");
	
	$.postJSON(url, oForm, function(data2) {
		agregarPatron(oForm);
	}).error(function(data){
		fnProcesarErrores(data, "form#forma");
	});
}

function agregarPatron(patron) {
	
	var url = '/portal-web/wizard/tramite/recuperacion/patron/agregarPatron';
	
	$.postJSON(url, patron, function(data) {
		if(data.encontrado) {
			if(data.existeRelacion) {
				mostrarMensajeError("Ya existe la relaci&oacute;n con este patr&oacute;n");
				reiniciarFormulario();
			} else {
				if(data.recuperado) {
					mostrarMensajeError("El registro patronal ya fue recuperado para este rfc");
					reiniciarFormulario();
				} else{
					if(data.duplicado) {
						mostrarMensajeError("Este patron ya se encuentra en la lista de patrones a a&ntilde;adir");
						reiniciarFormulario();
					} else {
						if(data.sujeto.nombreComercial == null) {
							pedirNombreComercial(data.sujeto);
						} else{
							agregarPatronLista(data.sujeto);
						}
					}
				}
			}
		} else {
			mostrarMensajeError("No se han encontrado coincidencias con los datos proporcionados");
		}
	});
}

function pedirNombreComercial(patron) {
	
	WizardNombreComercialCtrl.setOnCloseCallback(function() {
		if(WizardNombreComercialCtrl.resultado != null) {
			patron.nombreComercial = WizardNombreComercialCtrl.resultado;
			agregarPatronLista(patron);
		} else {
			patron.nombreComercial = "";
			agregarPatronLista(patron);
		}
	});
	WizardNombreComercialCtrl.init("setNombreComercialDiv", patron.cveIdSujetoObligado);
	WizardNombreComercialCtrl.abrir();
}

function agregarPatronLista(patron) {
	var tabla = $("#tabla-patrones");
	
	var fila = "<tr>";
	fila += "<td>"+patron.numeroRegistroPatronal +""+ patron.modalidad.numModalidad+ "" +patron.digVerificador+"</td>";
	fila += "<td>" + patron.nombreComercial + "</td>";
	fila += "<td><input type='button' value='Quitar' class='btn btn-secondary' onclick='eliminarPatronLista("+patron.cveIdSujetoObligado+")'></td>"
	fila += "</tr>";
	
	PATRONES_AGREGADOS++;
	//Agregamos la fila al tbody
	$(tabla).find('tbody').append(fila);
	reiniciarFormulario();
	
}

function eliminarPatronLista(idPatron) {
	var url = '/portal-web/wizard/tramite/recuperacion/patron/eliminarPatron';
	var tabla = $("#tabla-patrones");
		
	$.postJSON(url, {'cveIdSujetoObligado': idPatron}, function(data) {
		if(data.sujetos != null && data.sujetos.length >0 ) {
			var sujetos = data.sujetos;
			PATRONES_AGREGADOS = sujetos.length;
			$(tabla).find('tbody').html("");
			
			for(var i=0; i< sujetos.length;i++) {
				var fila = "<tr>";
				fila += "<td>"+sujetos[i].numeroRegistroPatronal +""+ sujetos[i].modalidad.numModalidad+ "" +sujetos[i].digVerificador+"</td>";
				fila += "<td>"+sujetos[i].nombreComercial+"</td>";
				fila += "<td><input type='button' value='Quitar' class='btn btn-secondary' onclick='eliminarPatronLista("+sujetos[i].cveIdSujetoObligado+")'></td>"
				fila += "</tr>";
				$(tabla).find('tbody').append(fila);
			}
		} else {
			PATRONES_AGREGADOS = 0;
			$(tabla).find('tbody').html("");
		}
	});	
}

function firmarRepresentante() {
	
	var url = '/portal-web/wizard/tramite/recuperacion/patron/getRpsRecuperar';
	
	$.postJSON(url, {}, function(data) {
		if(data.patrones != null && data.patrones.length >0 ) {
			var patrones = data.patrones;
			PATRONES_AGREGADOS = patrones.length;
			var recuperados = construirArrayRecuperados(patrones);
			if(PATRONES_AGREGADOS == 1) {				
				invocarFirmaDigital(recuperados,patrones[0].numeroRegistroPatronal +""+ patrones[0].modalidad.numModalidad+ "" +patrones[0].digVerificador);	
				
			} else {
				var rps = "";
				for(var i=0; i< patrones.length;i++) {
					
					rps += ""+patrones[i].numeroRegistroPatronal +""+ patrones[i].modalidad.numModalidad+ "" +patrones[i].digVerificador+"";
					
					if(i != (PATRONES_AGREGADOS-1)) {
						rps += ",";
					}
				}
				invocarFirmaDigital(recuperados,rps);	
				
			}
		}
	});	
}

function construirAfectado() {
	var personaAfectada = new Object();
	personaAfectada.nombreRazonSocial = datosEntradaFirma.nombreCompleto;
	personaAfectada.rfc = datosEntradaFirma.rfc;
	personaAfectada.curp = datosEntradaFirma.curp;
	
	return personaAfectada;
}
/** 
 * Metodo para llenar array con los 
 * @param patrones
 * @returns {Array}
 */
function construirArrayRecuperados (patrones) {
	
	var arrayPatronesRec = new Array();
	if(patrones != null && patrones.length >0 ) {
		PATRONES_AGREGADOS = patrones.length;
		
		for(var i=0; i< patrones.length;i++) {
			var patron = patrones[i];
			var patronRec = new Object();
			
			patronRec.registroPatronal = ""+patron.numeroRegistroPatronal +""+patron.modalidad.numModalidad+ "" +patron.digVerificador+"";
			
			if(patron.fisica != null) {
				patronRec.nombreRazonSocial= ""+ patron.fisica.nombre + " " + patron.fisica.primerApellido +" " + patron.fisica.segundoApellido;
				patronRec.rfc = patron.fisica.rfc;
				patronRec.curp = patron.fisica.curp;
			} else {
				patronRec.nombreRazonSocial= ""+ patron.moral.razonSocial;
				patronRec.rfc = patron.moral.rfc;
				
			}
			
			arrayPatronesRec.push(patronRec);
		}
	}
	
	return arrayPatronesRec;
}

function invocarFirmaDigital(patrones,registrosPatronales) {
	var rfcRepresentante = parent.WizardRecuperacionPatronCtrl.config.rfcPersona;
	
	parent.FirmaDigitalCtrl.setOnCloseCallback(function() {		
		if(parent.FirmaDigitalCtrl.datosSalida == null) {
			mostrarMensaje("La validaci&oacute;n de la firma no pudo ser realizada");
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
				mostrarMensaje("La validaci&oacute;n de la firma no pudo ser realizada");
			}
		}
	});
	
	parent.AtributosPersonaCtrl.personaPortal.registroPatronal =  registrosPatronales;
	
	var componenteFirma = {
			idTipoSolicitud : codigoTipoSolicitud,
			descripcionTipoSolicitud : descripcionTipoSolicitud,
			folioSolicitud : $('#folioSolicitud').val(),
			idTipoTramite : arrayCodigoTipoTramite,
			curp : parent.FirmanteCtrl.curp,
			rfc : parent.FirmanteCtrl.rfc,
			validarRFC : true,
			registroPatronal : registrosPatronales,
			nombreCompleto : parent.FirmanteCtrl.nombreRazonSocial,
			fechaElectronica : datosEntradaFirma.fechaElectronica,
			cad_original : $('#contenidoFirmar').val(),
			tipo_operacion : 'firmaCMS',
			firma_archivo : false,
			min_archivos : 0,
			max_archivos : 0,
			afectado: [parent.AtributosPersonaCtrl.personaPortal],
			tipoAcuse: '1',
			acuse: 'CDCT'
		};
	
	parent.iniciarFirmaDigital(componenteFirma);
}

function firmarTramite(firmaResponse) {
	var url = '/portal-web/wizard/tramite/recuperacion/patron/procesarDatosFirma';

	$.postJSON(url, firmaResponse, function(data) {
		finalizarTramite();
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog('open');
	});
}

function finalizarTramite() {
	
	var url = '/portal-web/wizard/tramite/recuperacion/patron/solicitud/finalizar';
	var folioSolicitudPendiente = $("#folioSolicitud").val();
	var idSolicitudPendiente = $("#idSolicitud").val();
		
	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
				cerrarWizard();
			}
	}]);
		
	$.postJSON(url, {}, function(data) {
		parent.ProcesandoSolicitudCtrl.abrir(folioSolicitudPendiente);
		cerrarWizard();
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog('open');
	});
}

function guardarTramite() {
	
	if(!getPatronesSeleccionados()) {
		MostrarMensajeSeleccionarPatrones();
	} else {
		var url = '/portal-web/wizard/tramite/recuperacion/patron/solicitud/guardar';
		
		$.postJSON(url, {} , function(data) {
			mostrarMensaje(data.mensaje);
		}).error(function(data){
			mostrarMensaje(data.mensaje);
		});
	}
}

function getPatronesSeleccionados() {
	
	if(PATRONES_AGREGADOS == 0) {
		return false;
	} else {
		return true;
	}
}

function MostrarMensajeSeleccionarPatrones() {
	$('#mensajeDialogo').text("Debe agregar al menos un registro patronal");

	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
		}
	}]);
	
	dialogoConfirmar.dialog('open');
}


function mostrarMensajeError(mensaje) {
	$('#mensajeDialogo').html(mensaje);

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
	var url = '/portal-web/wizard/tramite/recuperacion/patron/solicitud/cancelar';
	
	$.postJSON(url, {solicitudId : idSolicitudPendiente}, function(data) {
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog( "open" );
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.open();
	});
}

function cerrarWizard() {	
	parent.WizardRecuperacionPatronCtrl.cerrar();
}

function reiniciarFormulario() {
	$("#numeroRegistroPatronal").val("");
	$("#subdelegacion\\.delegacion\\.id").val("");
	$("#subdelegacion\\.id").val("");
	$("#stringClasificacion").val("");
}