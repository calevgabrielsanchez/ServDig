CONTEXT_PATH_APLICACION = '/${mvn.web.app.root}';
var dialogoConfirmarCancelar;
var dialogoConfirmar;
var dialogoError;
//variables para ICA
var existeA = false;

$(document).ready(function() {
	// Se incializa el blockUI para las peticiones AJAX
	$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
	
	/**
	 * Configuracion para el llamado de ICA al momento de modificar datos (como en ventanilla)
	 */
	$("#llamarIca1").click(
		function() {
			//var valida=$('form#correccion').valid();
			//if(valida) {
				llamarICA();
			//}
		}	
	);
	
	$('#capturarDocumentosCorreccionDatos').click(function() {
		var tipoTramite = $("form#formularioCorreccionDatosDerechohabiente #tipoTramite\\.idTipoTramite").val();
		var idTramite = $("form#formularioCorreccionDatosDerechohabiente #tramiteId").val();
		
		if(!parent.WizardCapturaDocumentosProbatoriosCtrl.isPrimeraCaptura()) {
			mostrarMensajeDocumentosExixtentes();
		} else {
			parent.WizardCapturaDocumentosProbatoriosCtrl.init("divCapturaDocs",tipoTramite,idTramite);
			parent.WizardCapturaDocumentosProbatoriosCtrl.abrir();
		}
	});
	
	$('#finalizarTramite').click(function() {
		validarInfo();
	});
	
	$('#guardarCerrarTramite').click(function() {
		guardarTramite(true);
	});

	$('#guardarTramite').click(function() {
		guardarTramite(false);
	});

	$('#cancelarTramite').click(function() {
		dialogoConfirmarCancelar.dialog( "open" );
	});

	$('#cerrarWizard').click(function() {
		//parent.WizardCapturaDocumentosProbatoriosCtrl.limpiarIndicadores();
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
	
	dialogoError = $( "#dialog-error" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false,
		dialogClass: "no-close",
	    closeOnEscape: false,
	    buttons: {
		 	"ACEPTAR": function() {
		 		$( this ).dialog( "close" );
		 	}
		 }
	 });
	
	llamarICA();
});

function validarInfo() {
	var tramiteCorreccionDatos = $("form#formularioCorreccionDatosDerechohabiente").toObject();
	var url = CONTEXT_PATH_APLICACION + '/wizard/correccion/validaciones';
	fnHideErrores("form#formularioCorreccionDatosDerechohabiente");
	
	// recuperamos los valores de las listas desactivadas.
	lugarNacimiento = new Object();
	estadoCivil = new Object();
	parentesco = new Object();
	sexo = new Object();
	
	tramiteCorreccionDatos.lugarNacimiento = lugarNacimiento;
	tramiteCorreccionDatos.estadoCivil = estadoCivil;
	tramiteCorreccionDatos.parentesco = parentesco;
	tramiteCorreccionDatos.sexo = sexo;
	
	tramiteCorreccionDatos.lugarNacimiento.clave = $('#lugarNacimiento\\.clave').val();
	tramiteCorreccionDatos.estadoCivil.idEstadoCivil = $('#estadoCivil\\.idEstadoCivil').val();
	tramiteCorreccionDatos.parentesco.idParentesco = $('#parentesco\\.idParentesco').val();
	tramiteCorreccionDatos.sexo.idSexo = $('#sexo\\.idSexo:not(:selected)').val();
		
	$.postJSON(url, tramiteCorreccionDatos, function(data2) {
		validarDocumentos();
	}).error(function(data){
		fnProcesarErrores(data, "form#formularioCorreccionDatosDerechohabiente");
	});
}

function validarDocumentos() {
	var requiereDocs = $("#requiereDocs").val() == 1;
	//var tipoTramite = $("#tipoTramite\\.idTipoTramite").val();
	
	//if(tipoTramite == 24) {
	if(requiereDocs){
		if(parent.WizardCapturaDocumentosProbatoriosCtrl.isCapturaFinalizada()) {
			invocarFirmaDigital();
		} else {
			mostrarMensajeError("Debe completar la documentaci&oacute;n para finalizar el tr&aacute;mite");
		}
	} else {
		invocarFirmaDigital();
	}
	
	
}

function invocarFirmaDigital() {
	
	var numeroArchivos = 0;
	var requiereDoctos = false;
	
	//if(parent.WizardCapturaDocumentosProbatoriosCtrl.getNumeroDocumentosRequeridos() > 0) {
	if(requiereDocs){
		requiereDoctos = true;
		numeroArchivos = parent.WizardCapturaDocumentosProbatoriosCtrl.getNumeroDocumentosRequeridos();
	}

	parent.FirmaDigitalCtrl.setOnCloseCallback(function() {
		
		if(parent.FirmaDigitalCtrl.datosSalida == null) {
			mostrarMensaje("La validaci&oacute;n de la firma no pudo ser realizada", false);
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
				mostrarMensaje("La validaci&oacute;n de la firma no pudo ser realizada", false);
			}
		}
	});
	
	var componenteFirma = {
		tipo_operacion :'firmaCMS',
		acuse:'AcuseV1.0',
		rfc: parent.FirmanteCtrl.rfc,
		validarRFC :true,
		curp: parent.FirmanteCtrl.curp,
		firma_archivo : requiereDoctos,
		min_archivos : numeroArchivos,
		max_archivos : numeroArchivos,
		fechaElectronica : datosEntradaFirma.fechaElectronica,
		cad_original:$('#contenidoFirmar').val(),
		registroPatronal : "",
		nombreCompleto : parent.FirmanteCtrl.nombreRazonSocial,
		idTipoSolicitud : codigoTipoSolicitud,
		descripcionTipoSolicitud : descripcionTipoSolicitud,
		folioSolicitud : $('#folioSolicitud').val(),
		idTipoTramite : arrayCodigoTipoTramite
	};

	parent.iniciarFirmaDigital(componenteFirma);
}

function firmarTramite(firmaResponse) {
	var url = CONTEXT_PATH_APLICACION + '/wizard/correccion/procesarDatosFirma';

	$.postJSON(url, firmaResponse, function(data) {
		finalizarTramite();
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog('open');
	});
}

function finalizarTramite() {
	
		var url = CONTEXT_PATH_APLICACION + '/wizard/correccion/solicitud/finalizar';
		var tramiteCorreccionDatos = $("form#formularioCorreccionDatosDerechohabiente").toObject();
		
		dialogoConfirmar.dialog("option", "buttons", [ {
			text : 'ACEPTAR',
			click : function() {
				$(this).dialog('close');
				cerrarWizard();
			}
		}]);
		
		$.postJSON(url, tramiteCorreccionDatos, function(data) {
			parent.WizardCapturaDocumentosProbatoriosCtrl.limpiarIndicadores();
			$('#mensajeDialogo').text(data.mensaje);
			dialogoConfirmar.dialog('open');
		}).error(function(data){
			$('#mensajeDialogo').text(data.mensaje);
			dialogoConfirmar.dialog('open');
		});
}

function guardarTramite(cerrar) {
		var url = CONTEXT_PATH_APLICACION + '/wizard/correccion/guardar';
		var tramiteCorreccionDatos = $("form#formularioCorreccionDatosDerechohabiente").toObject();
		$.blockUI();
		
		// recuperamos los valores de las listas desactivadas.
		lugarNacimiento = new Object();
		estadoCivil = new Object();
		parentesco = new Object();
		sexo = new Object();
		
		tramiteCorreccionDatos.lugarNacimiento = lugarNacimiento;
		tramiteCorreccionDatos.estadoCivil = estadoCivil;
		tramiteCorreccionDatos.parentesco = parentesco;
		tramiteCorreccionDatos.sexo = sexo;
		
		tramiteCorreccionDatos.lugarNacimiento.clave = $('#lugarNacimiento\\.clave').val();
		tramiteCorreccionDatos.estadoCivil.idEstadoCivil = $('#estadoCivil\\.idEstadoCivil').val();
		tramiteCorreccionDatos.parentesco.idParentesco = $('#parentesco\\.idParentesco').val();
		tramiteCorreccionDatos.sexo.idSexo = $('#sexo\\.idSexo:not(:selected)').val();
		
		$.postJSON(url, tramiteCorreccionDatos , function(data) {
			mostrarMensaje(data.mensaje, cerrar);
		}).error(function(data){
			mostrarMensaje(data.mensaje, true);
		});
}

function mostrarMensajeError(mensaje) {
	$('#mensajeError').html(mensaje);
	dialogoError.dialog('open');
}

function mostrarMensajeDocumentosExixtentes() {
	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'CONTINUAR',
		click : function() {
			var tipoTramite = $("#tipoTramite\\.idTipoTramite").val();
			var idTramite = $("#tramiteId").val();
			
			parent.WizardCapturaDocumentosProbatoriosCtrl.init("divCapturaDocs",tipoTramite,idTramite);
			parent.WizardCapturaDocumentosProbatoriosCtrl.abrir();
			$(this).dialog('close');
		}
	}, {
		text : 'CANCELAR',
		click : function() {
			$(this).dialog('close');
		}
	}]);
	
	$('#mensajeDialogo').html("Se eliminaran los datos de los documentos que ya se han capturado");
	dialogoConfirmar.dialog('open');
}

function mostrarMensaje(mensaje, cerrar) {
	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			if(cerrar) {
				cerrarWizard();
			}
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
	var url = CONTEXT_PATH_APLICACION + '/wizard/correccion/solicitud/cancelar';
	
	$.postJSON(url, {solicitudId : idSolicitudPendiente}, function(data) {
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog( "open" );
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.open();
	});
}

function cerrarWizard() {	
	parent.WizardCorreccionDerechohabienteCtrl.cerrar();
}


function llamarICA() {
	//inicializamos el objeto del ica
	parent.identificarCambiosAutomaticosPersonaFisicaCtrl.init('dialogICACorreccionDatosWeb1');
	
	//parametros para iniciar la pantalla de ica
	var datosEntradaICA = {
		indMostrarPantalla : true,
		idPersona : $('#idPersona').val(),
		curp : $('#curpCap').val(),
		indBusquedaRENAPO : true,
		indUsuarioExterno : false,
		rfc: '',
		nombrePersona : '',
		primerApellido :'',
		segundoApellido : '',
		indBusquedaSAT : false
	};
	//Establecemos los parametros de entrada
	parent.identificarCambiosAutomaticosPersonaFisicaCtrl.datosEntrada = datosEntradaICA;
	
	//establecemos el callback
	parent.identificarCambiosAutomaticosPersonaFisicaCtrl.setOnCloseCallback(callbackICA); 

	//abrimos la pantalla del ica
	parent.identificarCambiosAutomaticosPersonaFisicaCtrl.identificarCambios();
}

// Invocado tras dar clic en Aceptar en la pantalla del ICA
function setDatosPersona(persona) {
	$("#curpCap").val(persona.curp);
	$("#curpValidada").val(persona.curp);
	$("#sexo\\.idSexo").val(persona.sexo.idSexo);
	$("#nombre").val(persona.nombre);
	$("#primerApellido").val(persona.primerApellido);
	$("#segundoApellido").val(persona.segundoApellido);
	$("#fechaNacimiento").val(persona.fechaNacimientoFormateada);
	$("#lugarNacimiento\\.clave").val(persona.lugarNacimiento.clave);
	
}