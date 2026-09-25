var _urlAltaPatronal = '/delta-gestionPatronal-web-ventanilla/wizard/tramite/registro/patronal/';
var _urlAltaSocios = '/delta-gestionPatronal-web-ventanilla/wizard/tramite/socios/';
var _tituloDialogoError = 'Trámite improcedente';
var _idDivDialogoGeneral = '#dialogoMensajesGeneral';	
var mostrarProcesando = true;
var _datos = null;

var _wizard = null;
var _objPersona = null;
var _tramiteMostrar = null;

var _busquedaCtrl = null;
var _identidadCtrl = null;
var _sujetoCtrl = null;
var _reloadDetailSubscription;
var _fnTramiteFinalizado = null;
	
$(function() {
	
	$.ajaxSetup({ cache: false });
		
	_busquedaCtrl = $('div#busquedaContainer').busqueda({ttc: _ttc, tf: _tf}) ;
	_identidadCtrl = $('div#identidadContainer').identidad({
		complete : function() {
			$('button#buttonIniciarTramite').removeAttr('disabled');
		},
		onClean : function() {
			var buttonDetalleSolicitud = $('button#btnDetalleSolicitud').length;
			
			var buttonDetalleSeguro = $('button#btnDetalleSeguro').length;
				if(buttonDetalleSeguro>0)
					$('button#btnDetalleSeguro').remove();
			
			if(buttonDetalleSolicitud>0){
				var btnHtmlIniciarTrm = '<button type="button" id="buttonIniciarTramite" ' 
					+ 'class="btn btn-primary btn-block" disabled="disabled" tabindex="4">INICIAR TR&Aacute;MITE</button>';
				$('button#btnDetalleSolicitud').after(btnHtmlIniciarTrm).remove();
				var buttonNuevoTramite = $('button#btnNuevoTramite').length;
				if(buttonNuevoTramite>0)
					$('button#btnNuevoTramite').remove();
				
				$('button#buttonIniciarTramite').bind('click',function(e){
					e.preventDefault();
					procesaTramite();
				});
			}
			$('button#buttonIniciarTramite').attr('disabled','disabled');
			if (typeof _reloadDetailSubscription !== 'undefined') {
				conector.unsubscribe(_reloadDetailSubscription);
			}
		},
		onIdentidadValidada : function(){
			var _subscription = createReloadSubscriber();
			_reloadDetailSubscription = conector.subscribe(_subscription.channel, _subscription.action);
		}
	});
	
	_identidadCtrl.identidad('option', 'idTipoTramite', _tramite);
	_sujetoCtrl = $('div#sujetoContainer').sujeto();
				
	$('button#buttonIniciarTramite').click(function(){
		procesaTramite();
	});
	
	// Se incializa el componente de domicilios
	DomicilioCtrl.init('domiciliosComponent');
	
	// Se inicializa el componente de Procesando Solicitud
	ProcesandoSolicitudCtrl.init('procesandoSolicitudComponent', _tiempoEspera, _tiempoIntervaloEspera);
	
	$('#refrescarIdentidad').click(function(){
		_identidadCtrl.identidad('actualizar');
	});
	
	$('#refrescarSujeto').click(function(){
		_sujetoCtrl.sujeto('actualizar');
	});
	
	if (typeof WizardDetalleSeguroCtrl !== 'undefined') {
		//Inicializar detalle seguro persona.
		WizardDetalleSeguroCtrl.init('wizardDetalleSeguroComponent', null);	
	}
	
	if (typeof WizardDetalleDomesticoCtrl !== 'undefined') {
		//Inicializar detalle seguro domestico.
		WizardDetalleDomesticoCtrl.init('wizardDetalleSeguroDomesticoComponent', null, null, null);
	}
	
});

function openWizard(tramite, objPersona) {
	
	if (typeof WizardAgregarRLCtrl !== 'undefined') {
		WizardAgregarRLCtrl.init(null,null);
	}
	
	if (tramite === tipoTramiteEnum.ALTA_SRT) {
		//console.log(" -- ALTA PATRONAL FISICA");		
		
		//Validaciones para efectuar Alta Patronal	
		var _url = _urlAltaPatronal + 'validarPersonaParaAlta/' + objPersona.idPersona + '/' 
			+ objPersona.tipoPersona.idTipoPersona;
		
		$.getJSON(_url, function(data) {
			var _respuesta = data.respuesta;
			
			if (_respuesta == 1) {
				construirDialogo(_idDivDialogoGeneral, _tituloDialogoError, data.msgError, true, undefined, undefined, 250, 600);
				$.unblockUI();
				return;
			} else {
				_wizard = WizardAltaPatronalCtrl;
				_wizard.init('dialogoTramite', objPersona.idPersona, objPersona.tipoPersona.idTipoPersona);
				_wizard.abrir();
				$.unblockUI();
			}
		});
	} else if (tramite === tipoTramiteEnum.ALTA_SRT_PM) {
		//console.log(" -- ALTA PATRONAL MORAL");
		
		_objPersona = objPersona;
		validarParaAltaPatronalMoral();
		
		return;
	} else if (tramite === tipoTramiteEnum.ACTUALIZACION_SOCIO) {
		// console.log(" -- ALTA DE SOCIO");

		representantesLegalesCtrl.init(tramite);
		representantesLegalesCtrl.setWizardPrincipal(WizardAltaSociosCtrl);

		// Validar Socio Sindicato
		var _url = _urlAltaSocios + 'validaciones/SocioSindicato/' + objPersona.idPersona;

		$.getJSON(_url, function(data) {
			var respuesta = data.respuesta;
			if (respuesta == 1) {
				construirDialogo(_idDivDialogoGeneral, _tituloDialogoError, data.msgError, true, undefined, undefined, 250, 600);
				$.unblockUI();
				return;
			} else {
				_wizard = WizardAltaSociosCtrl;
				_wizard.init('dialogoTramite', objPersona.idPersona, objPersona.rfc);
				_wizard.abrir();
				$.unblockUI();
			}
		});
	} else if (tramite === tipoTramiteEnum.BAJA_SOCIO) {
		// console.log(" -- BAJA DE SOCIO");

		representantesLegalesCtrl.init(tramite);
		representantesLegalesCtrl.setWizardPrincipal(WizardBajaSociosCtrl);

		_wizard = WizardBajaSociosCtrl;
		_wizard.init('dialogoTramite', objPersona.idPersona, objPersona.rfc);
		_wizard.abrir();
		$.unblockUI();
	} else if (tramite === tipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL) {
		// console.log(" -- REGISTRO DE REPRESENTADO LEGAL");

		representantesLegalesCtrl.init(tramite);
		representantesLegalesCtrl.setWizardPrincipal(WizardRegistroRepresentadoLegalCtrl);

		_wizard = WizardRegistroRepresentadoLegalCtrl;
		_wizard.init("dialogoTramite", objPersona.idPersona, objPersona.curp, objPersona.rfc);
		_wizard.abrir();
		$.unblockUI();
	} else if(tramite === tipoTramiteEnum.BAJA_REPRESENTANTE_LEGAL){
		// console.log(" -- BAJA DE REPRESENTANTE LEGAL");

		representantesLegalesCtrl.init(tramite);
		representantesLegalesCtrl.setWizardPrincipal(WizardBajaRepresentateLegalCtrl);

		var _cveFisicaMoral = 0;
		if (objPersona.tipoPersona.idTipoPersona == 1) {
			if (objPersona.cveFisica != undefined) {
				_cveFisicaMoral = objPersona.cveFisica;
			}
			_wizard = WizardBajaRepresentateLegalCtrl;
			_wizard.init('dialogoTramite', objPersona.idPersona, _cveFisicaMoral, objPersona.tipoPersona.idTipoPersona, objPersona.rfc);
			_wizard.abrir();
			$.unblockUI();
		} else {
			_cveFisicaMoral = objPersona.idPersona;
			_wizard = WizardBajaRepresentateLegalCtrl;
			_wizard.init('dialogoTramite', objPersona.idPersona, _cveFisicaMoral, objPersona.tipoPersona.idTipoPersona, objPersona.rfc);
			_wizard.abrir();
			$.unblockUI();
		}
	} else if (tramite === tipoTramiteEnum.RECUPERACION_REGISTRO_PATRONAL) {
		// console.log(" -- RECUPERACION PATRONAL");
		_wizard = WizardRecuperacionPatronCtrl;
		_wizard.init('dialogoTramite', objPersona.idPersona,
				objPersona.tipoPersona.idTipoPersona, objPersona.rfc, 1);
		_wizard.abrir();
		$.unblockUI();
	} else if (tramite === tipoTramiteEnum.COMPRA_SEGURO_INDIVIDUAL){
		// console.log(" -- COMPRA DE SEGURO PERSONAL");
		$.blockUI();

		var datos = {
			contenedor : 'dialogoTramite',
			idPersona : objPersona.idPersona,
			rfc : ((objPersona.rfc == undefined || objPersona.rfc == '' || objPersona.rfc == null) ? 'SIN_RFC'
					: objPersona.rfc)
		};

		_datos = datos;

		// Efectuar validaciones para acceder al tramite
		validarAccesoTramiteSeguroIndividual();
		return;
	} else if (tramite === tipoTramiteEnum.COMPRA_SEGURO_DOMESTICO) {
		// console.log(" -- COMPRA DE SEGURO DOMESTICO");

		/*
		 * Es requerido inicializar el componente de renovacion y solo cuando
		 * aplica renovacion se utiliza (Sino NO es creado en el primer nivel).
		 */
		WizardIVRORenovacionSeguroDomesticoCtrl.init('wizardAltaSeguroVoluntario', null, null, null);

		// Efectuar validaciones para acceder al tramite
		_objPersona = objPersona;
		validarAccesoTramiteSeguroDomestico();
		// return;
	} else if (tramite === tipoTramiteEnum.CARTA_NO_ADEUDO){
		if (objPersona.rfc != null) {
			_wizard = WizardCartaNoAdeudoCtrl;
			if ($('input#userCtrl').length > 0) {
				curpSesion = $('input#userCtrl').val();
				_wizard.init('dialogoTramite', objPersona.idPersona, objPersona.tipoPersona.idTipoPersona, objPersona.rfc, curpSesion);
			} else
				_wizard.init('dialogoTramite', objPersona.idPersona, objPersona.tipoPersona.idTipoPersona, objPersona.rfc, null);
			
			_wizard.abrir();
		} else {
			construirDialogoCartaNoAdeudo(
					_idDivDialogoGeneral,
					_tituloDialogoError,
					"* Para continuar con el tr&aacute;mite, es necesario el RFC.",
					true, undefined, undefined, 200, 450);
		}
		$.unblockUI();
	}
}

function solicitudFinalizada(folio) {	

	console.log('En callback del finalizar solicitud -> ' + folio);
	
	var btnHtml = '<button type="button" id="btnDetalleSolicitud" ' 
		+ 'class="btn btn-primary btn-block">MOSTRAR DETALLE SOLICITUD</button>'
		+ '<button type="button" id="btnNuevoTramite"'
		+ 'class="btn btn-default btn-block">NUEVO TR&Aacute;MITE</button>';
			
	$('button#buttonIniciarTramite').after(btnHtml).remove();
	$('button#btnDetalleSolicitud').click(function(e){
		e.preventDefault();
		ejecutarConsultaSolicitudPorFolio(folio);
	});
	$('button#btnNuevoTramite').click(function(e){
		e.preventDefault();
		window.location = '/portal-ventanilla-web/portal';
	});
	
	if (_fnTramiteFinalizado != null && $.isFunction(_fnTramiteFinalizado)) {
		_fnTramiteFinalizado.call();
	}
}

function ejecutarConsultaSolicitudPorFolio (folio) {
	
	if (folio == null || folio == '' || typeof folio === 'undefined') {
		
		alert('El n\u00FAmero de folio es requerido para la realizar la b\u00FAsqueda.');
		
		return false;
	}
	
	DetalleSolicitudCtrl.init("detalleSolicitudComponent",folio);
	DetalleSolicitudCtrl.abrir();
	
}
function construirDialogo(divId, titulo, mensaje, error, callback, callbackForXButton, height, width) {
	
	$('#textoMensajeGeneral').html(mensaje);
	$('#textoMensajeGeneral').removeAttr("style");
	if (error) {
		$('#textoMensajeGeneral').attr("style", "color: red;");
	} else {
		$('#textoMensajeGeneral').attr("style", "color: blue;");
	}
	
	if(height == undefined){
		height=150;
	}
	if(width == undefined){
		width=400;
	}
	
	var objDialogo = $(divId).dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		height : height,
		width : width,
		title : titulo,
		close: function(event, ui) {
			if ( event.originalEvent && $(event.originalEvent.target).closest(".ui-dialog-titlebar-close").length ) {
				if ( callbackForXButton != undefined && jQuery.isFunction(callbackForXButton)) {
					callbackForXButton();
				}
			}
		},
		buttons : {
			"Aceptar" : function() {
				if (jQuery.isFunction(callback)) {
					callback();
				}
				$(this).dialog("close");
			}
		}
	});
	objDialogo.dialog('open');
}

function createReloadSubscriber() {
	var persona = _identidadCtrl.identidad('option', 'personaUbicada');
	
	return {
		channel : [ '/portlets/modificacion/', persona.idPersona ].join(''),
		action : function(message) {
			var _id = message.data.idPortlet;
			
			if (_id == 'detalleIdentidad') {
				_identidadCtrl.identidad('actualizar');
			} else if (_id == 'detalleSujeto') {
				_sujetoCtrl.sujeto('actualizar');
			}
		}
	};
}

function procesaTramite() {
	$.blockUI();

	ProcesandoSolicitudCtrl.setOnSolicitudExitosaCallback(function(folio) {
		solicitudFinalizada(folio);
	});

	var objPersona = _identidadCtrl.identidad('option', 'personaUbicada');
	openWizard(_tramite, objPersona);
}

function validarParaAltaPatronalMoral(){
	// Validaciones para efectuar Alta Patronal
	console.log('Se va a validarParaAltaPatronal');
	var _url = _urlAltaPatronal + 'validarPersonaParaAlta/' + _objPersona.idPersona + '/' + _objPersona.tipoPersona.idTipoPersona;
	var _callback = null;
	
	$.ajax({
		dataType : "json",
		url : _url,
		success : function(data) {
			var _respuesta = data.respuesta;
			_tramiteMostrar = data.tramiteMostrar;

			if (_respuesta === 1) {
				if (_tramiteMostrar === tipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES) {
					// Alta Datos Generales
					_callback = actualizarDatosGenerales;
				} else if (_tramiteMostrar === tipoTramiteEnum.ACTUALIZACION_SOCIO) {
					// Alta Socio
					_callback = agregarSocio;
				}
				console.log('_respuesta es: ' + _respuesta + ', _tramiteMostrar es: ' + _tramiteMostrar);
				construirDialogo(_idDivDialogoGeneral, _tituloDialogoError, data.msgError, true, _callback, undefined, 250, 600);
				$.unblockUI();
			} else {
				abrirWizardAltaPatronalCtrl();
			}
		}
	});
}

function agregarSocio(){
	
	representantesLegalesCtrl.init(_tramiteMostrar);
	representantesLegalesCtrl.setWizardPrincipal(WizardAltaSociosCtrl);
	
	//Validar Socio Sindicato
	var _url = _urlAltaSocios + 'validaciones/SocioSindicato/' + _objPersona.idPersona;
	
	$.getJSON(_url, function(data) {
		var respuesta = data.respuesta;
		if (respuesta == 1) {
			construirDialogo(_idDivDialogoGeneral, _tituloDialogoError, data.msgError, true, undefined, undefined, 250, 600);
			$.unblockUI();
			return;
		} else {
			//Inicializando callback para cuando termine el 'procesando' exitosamente
			ProcesandoSolicitudCtrl.setOnSolicitudExitosaCallback(function(){
				ProcesandoSolicitudCtrl.cerrar();
				validarParaAltaPatronalMoral();
			});
			
			_wizard = WizardAltaSociosCtrl;
			_wizard.init('dialogoTramite', _objPersona.idPersona, _objPersona.rfc);
			_wizard.abrir();
			$.unblockUI();
		}
	});
}

function actualizarDatosGenerales(){
	ProcesandoSolicitudCtrl.setOnSolicitudExitosaCallback(function(){
		ProcesandoSolicitudCtrl.cerrar();
		validarParaAltaPatronalMoral();
	});
	
	//Se ejecuta el mismo método que se usa en accionesIdentidad (Menu donde está la opción Acciones> Datos Generales)
	datosPersonales();
}

function abrirWizardAltaPatronalCtrl(){
	//Se quita callbackExitoso para regresar a su estado normal (null) y no siga ejecutándose el callback seteado para otros trámites
	ProcesandoSolicitudCtrl.setOnSolicitudExitosaCallback(null);
	
	_wizard = WizardAltaPatronalCtrl;
	_wizard.init('dialogoTramite', _objPersona.idPersona, _objPersona.tipoPersona.idTipoPersona);
	_wizard.abrir();
	$.unblockUI();
}

function validarAccesoTramiteSeguroIndividual() {
	$.ajax({
		dataType: "json",
		url: '/gestionSeguroVoluntario-web-ventanilla/wizard/individual/validarAccesoTramite/' + _datos.idPersona + '/' + _datos.rfc,
		success: accesoTramiteSeguroIndividualIVRO
	});
}

function accesoTramiteSeguroIndividualIVRO(response) {
	$.unblockUI();
	
	if (response.error == true) {
		var callbackIvro = undefined;
		var abrirDomicilio = false;
		if (response.msgError.indexOf("696") != -1) {
			abrirDomicilio = true;
			response.msgError = response.msgError.substring(6,
					response.msgError.lenght);
		}
		$divError = $('<div></div>');

		$divError.dialog({
			autoOpen : false,
			resizable : false,
			width : 400,
			height : 'auto',
			title : 'Mensaje del sistema',
			modal : true,
			close : function() {
				if (abrirDomicilio) {
					callbackActualizarDomicilio();
				} else {
					$.unblockUI();
				}
			},
			buttons : {
				"Aceptar" : function() {
					$(this).dialog('close');
				}
			}
		}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

		var htmlError = '<div class="alert alert-info">' + ''
				+ '<strong>Importante: </strong>' + response.msgError
				+ '</div>';
		$divError.html(htmlError);
		$divError.dialog('open');

	} else {
		procesaSeguroPersonalIVRO();
	}
}

function validarAccesoTramiteSeguroDomestico() {
	var idPersona = _objPersona.idPersona;
	var rfc = ((_objPersona.rfc == undefined || _objPersona.rfc == '' || _objPersona.rfc == null) ? 'SIN_RFC' : _objPersona.rfc);

	$.ajax({
		dataType : "json",
		url : '/gestionSeguroVoluntario-web-ventanilla/wizard/seguroDomestico/validarAccesoTramite/' + idPersona + '/' + rfc,
		success : accesoTramiteSeguroDomesticoIVRO
	});
}

function accesoTramiteSeguroDomesticoIVRO(response) {
	$.unblockUI();
	if (response.error == true) {
		mostrarMensajeError(response.msgError);
	} else {
		procesaSeguroDomesticoIVRO();
	}
}

function procesaSeguroDomesticoIVRO() {
	var idPersona = _objPersona.idPersona;
	var rfc = ((_objPersona.rfc == undefined || _objPersona.rfc == '' || _objPersona.rfc == null) ? 'SIN_RFC'
			: _objPersona.rfc);
	var nssCifrado = _objPersona.nssCifrado;

	nssCifrado = nssCifrado != undefined && nssCifrado != null
			&& nssCifrado != '' ? nssCifrado : -1;

	_wizard = WizardIVROVentanillaSeguroDomesticoCtrl;
	_wizard.init('dialogoTramite', idPersona, rfc, nssCifrado);
	_wizard.abrir();
	$.unblockUI();
	
	_fnTramiteFinalizado = function() {
		var btnDetalleSeguro = '<button type="button" id="btnDetalleSeguro" '
				+ 'class="btn btn-success btn-block">MOSTRAR DETALLE SEGURO</button>';
		$('button#btnDetalleSolicitud').before(btnDetalleSeguro);
		$('button#btnDetalleSeguro').click(function() {
			_wizard.abrir();
		});
	};
}
        
function callbackActualizarDomicilio() {
	ProcesandoSolicitudCtrl.setOnSolicitudExitosaCallback(function(){
		ProcesandoSolicitudCtrl.cerrar();
		procesaSeguroPersonalIVRO();
	});
	
	$.getScript('/gestionDomicilios-web-ventanilla/static/resources/js/delta/domicilios/wizard/general/WizardDomicilioGeneral.js', function(){
		_wizardActualizarDomiclio = WizardDomicilioGeneralCtrl;
		_wizardActualizarDomiclio.init({
			idPersona : _datos.idPersona
		}).abrir();
	});
	
	$.unblockUI();
}

function procesaSeguroPersonalIVRO() {
	/* 
	 * Se quita callbackExitoso para regresar a su estado normal (null) y no
	 * siga ejecutándose el callback seteado para otros trámites
	 */
	ProcesandoSolicitudCtrl.setOnSolicitudExitosaCallback(null);

	_wizard = WizardSeguroIvroIndivCtrl;
	_wizard.init(_datos);

	/*
	 * Antes de abrir el dialogo, revisar si es nueva compra de seguro ó
	 * renovación
	 */
	var esRenovacion = _wizard.validarCompraRenovacion();
	if (esRenovacion) {
		_datos.title = 'Renovaci\u00f3n de Incorporaci\u00f3n Voluntaria al R\u00E9gimen Obligatorio';
	}
	_wizard.init(_datos);
	_wizard.abrir();
	$.unblockUI();

	_fnTramiteFinalizado = function() {
		var btnDetalleSeguro = '<button type="button" id="btnDetalleSeguro" '
				+ 'class="btn btn-success btn-block">MOSTRAR DETALLE SEGURO</button>';
		$('button#btnDetalleSolicitud').before(btnDetalleSeguro);
		$('button#btnDetalleSeguro').click(function() {
			_wizard.abrir();
		});
	};
}

function mostrarMensajeError(mensaje) {
	$divError = $('<div></div');

	$divError.dialog({
		autoOpen : false,
		resizable : false,
		width : 400,
		height : 'auto',
		title : 'Mensaje del sistema',
		modal : true,
		close : function() {
			$.unblockUI();
		},
		buttons : {
			"Aceptar" : function() {
				$(this).dialog('close');
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	var htmlError = '<div class="alert alert-info">' + ''
			+ '<strong>Importante: </strong>' + mensaje + '</div>';
	$divError.html(htmlError);
	$divError.dialog('open');
}