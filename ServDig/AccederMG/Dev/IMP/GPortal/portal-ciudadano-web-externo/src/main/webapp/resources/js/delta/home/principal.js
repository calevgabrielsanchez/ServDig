var conector;

var tramitesPortalCiudadano = {
	divContenedorComprobante : 'divComprobanteVigencia',
	divComponentCommon		 : 'divComponentCommon',
	divActualizacionRfcComponent : 'divActualizacionRfcComponent',
	divBajaDerechohabiente : 'divWizardBajaDerechohabiente',
	
	registroAsegurado : function (){
		if(this.validaNss()) {
			var opcionesRegistro = this.getOpcionesRegistro(true);
			
			WizardRegistroDerechohabienteCtrl.init(opcionesRegistro);
			WizardRegistroDerechohabienteCtrl.abrir();
		}
	},
	registroBeneficiario : function (){
		
		if(this.validaNss()) {
			var opcionesRegistro = this.getOpcionesRegistro(false);
			
			WizardRegistroDerechohabienteCtrl.init(opcionesRegistro);
			WizardRegistroDerechohabienteCtrl.abrir();
		}
	},
	bajaBeneficiario : function() {
		if(this.validaNss()) {
			var opciones = this.getOpcionesRegistro(false);
			
			WizardBajaDerechohabienteCtrl.init(this.divBajaDerechohabiente, opciones.idAsignacion, 
					opciones.nss, 0, 0);
			WizardBajaDerechohabienteCtrl.abrir();
		}
	},
	comprobanteVigencia : function() {
		if(this.validaNss()) {
			let nss = $("#nss").val();
			$.ajax({
				url : context_path + '/home/validaDerechosArco/'+ nss,
				dataType: 'json',
				cache: false,
				contentType: false,
				processData: false,
				type: 'POST',
				method: 'POST',
			}).done(function (response) {
				if (response) {
                    tramitesPortalCiudadano.mostrarMensajeError('La Constancia de Vigencia de Derechos no se puede generar por Internet. En caso de que se requiera generar su Constancia, favor de acudir a la Subdelegaci&oacute;n');
				} else {
					let opciones = tramitesPortalCiudadano.getOpcionesRegistro(false);
					WizardComprobanteVigenciaCtrl.init(tramitesPortalCiudadano.divContenedorComprobante, opciones);
					WizardComprobanteVigenciaCtrl.abrir(false);
				}
			});
		}
	}, 
	cambioClinica : function() {
		if(this.validaNss()) {
			WizardCambioClinicaGeneralCtrl.init({idAsignacionNss:$("#cveIdAsingacionNSS").val()}).abrir();
		}
	},
	validaNss : function() {
		var idAsignacion = $("#cveIdAsingacionNSS").val();
		
		if(idAsignacion == '') {
			this.mostrarMensajeError('Estimado asegurado(a) o pensionado(a), el N&uacute;mero de seguridad social asociado' +
					' a la CURP que ingres&oacute;, requiere realizar su aclaraci&oacute;n, por lo que le agradeceremos acudir a la '+
					'Subdelegaci&oacute;n m&aacute;s cercana.')
//			this.mostrarMensajeError('Para realizar este tr&aacute;mite es necesario contar con un NSS asociado'+
//					', elija la opci&oacute;n Asignaci&oacute;n de N&uacute;mero de Seguridad Social');
			return false;
		} 
		
		return true;
	},
	getOpcionesRegistro : function(registroAsegurado) {
		var opcionesRegistro = {
			'contenedor' : 'divWizardRegistro',
			'idAsignacion': (''+$("#cveIdAsingacionNSS").val()),
			'idPersona' : (''+$("#cveIdPersona").val()),
			'curp' : (''+$("#curp").val()),
			'correo' : (''+$("#correo").val()),
			'nss': (''+$("#nss").val()),
			'registroAsegurado' : registroAsegurado,
			'nombreCompleto' : (''+$("#nombre").val())
		};
		
		return opcionesRegistro;
	},
	mostrarMensajeError : function(mensaje) {
		$divError = $('<div></div>');

		$divError.dialog({
			autoOpen : false,
			resizable : false,
			width: 400,
			height : 'auto',
			title : 'Mensaje del sistema',
			modal : true,
			buttons : {
				"Aceptar" : function() {
					$(this).dialog('close');	
				}
			}
		}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

		var htmlError ='<div class="alert alert-info">'+
			''+
			'<strong>Importante: </strong>'+ mensaje + '</div>';
		$divError.html(htmlError);
		$divError.dialog('open');
	},
	ivroIndividual : function() {
		var idPersona = $("#cveIdPersona").val();
		var rfc = $("#rfc").val();
		var _correo = $("#correo").val();
		if(rfc== undefined || rfc=='')
			rfc='SIN_RFC';

		console.log("inicia acceso al tramite LALO");


		var url = '/gestionSeguroVoluntario-web-ciudadano/wizard/individual/validarAccesoTramite/' + idPersona + '/' + _correo + '/'+ rfc +'?'+Math.random();

		$.getJSON(url, iniciarValidacionesIvroindiv);

	    
	},
	cartaTerminoIvroIndividual : function(data) {
		
		console.log("abre carta de terminos");
		
		if(data.error){
			tramitesPortalCiudadano.mostrarMensajeError(data.msgError);
		}else if (data.mostrarDetalleSeguro) {
			WizardSeguroIvroIndivCtrl.abrir();
		} else {
			CartaTerminosCtrl.init(tramitesPortalCiudadano.divComponentCommon, 118);
			CartaTerminosCtrl.setWizardPrincipal(WizardSeguroIvroIndivCtrl);
			CartaTerminosCtrl.abrir();
		}
	},
	cartaTerminoIvroIndividualRfc : function(data) {
		
		console.log("abre carta de terminos");
		if(data.error){
			tramitesPortalCiudadano.mostrarMensajeError(data.msgError);
		}else if (data.mostrarDetalleSeguro) {
			WizardSeguroIvroIndivCtrl.abrir();
		} else {
			CartaTerminosCtrl.init(tramitesPortalCiudadano.divComponentCommon, 118);
			CartaTerminosCtrl.setWizardPrincipal(WizardActualizaRfcCtrl);
			CartaTerminosCtrl.abrir();
		}
	},tramiteProrroga : function() {

        WizardProrrogaDerechohabienteCtrl.init('divWizardTramiteProrroga',$("#cveIdAsingacionNSS").val(),0,0,0);
        WizardProrrogaDerechohabienteCtrl.abrir();

    },
	ivroDomestico : function() {
		//Es requerido inicializar el componente de renovacion
		// y solo cuando aplica renovacion se utiliza (Sino no es creado en el primer nivel).
		WizardIVRORenovacionSeguroDomesticoCtrl.init('wizardAltaSeguroVoluntario', null, null, null);
		
		var idPersona = $("#cveIdPersona").val();
		var rfc = $("#rfc").val();
        var _correo = $("#correo").val();
		var urlValidarAccesoTramite = '/gestionSeguroVoluntario-web-ciudadano/wizard/seguroDomestico/validarAccesoTramite/';
		if(rfc == null || rfc == '' || typeof rfc === 'undefined'){
			rfc='SIN_RFC';
		}

        urlValidarAccesoTramite = urlValidarAccesoTramite  + idPersona + '/' + _correo + '/' + rfc;
		$.getJSON(urlValidarAccesoTramite, validarAccesoTramiteDomestico);
		
	},
	cartaIvroDomestico : function(data) {
		if (data.mostrarDetalleSeguro) {
			WizardIVROVentanillaSeguroDomesticoCtrl.abrir();
		} else {
			CartaTerminosCtrl.init(tramitesPortalCiudadano.divComponentCommon, _idTramiteIvroDomestico);
		    CartaTerminosCtrl.setWizardPrincipal(WizardIVROVentanillaSeguroDomesticoCtrl);
		    CartaTerminosCtrl.abrir();
		}
	},
	cartaIvroDomesticoRfc : function(data) {
		if (data.mostrarDetalleSeguro) {
			WizardIVROVentanillaSeguroDomesticoCtrl.abrir();
		} else {
			CartaTerminosCtrl.init(tramitesPortalCiudadano.divComponentCommon, _idTramiteIvroDomestico);
		    CartaTerminosCtrl.setWizardPrincipal(WizardActualizaRfcCtrl);
		    CartaTerminosCtrl.abrir();
		}
	},
	beneficiosRiss : function() {
		var rfc = $("#rfc").val();
		var idPersona = $("#cveIdPersona").val();
		
		//Verificar solicitar Actualizacion del RFC 
		WizardActualizaRfcCtrl.validarSolicitarRfc(idPersona);
		
		if(WizardActualizaRfcCtrl.config.solicitarRfc){
			//Preparar Wizar WizardSolicitarRifCtrl			
			WizardSolicitarRifCtrl.init('divWizardBeneficiosRiss', rfc, idPersona);
			//Preparar Actualizar RFC
			WizardActualizaRfcCtrl.init(this.divActualizacionRfcComponent, idPersona, true, _idTramiteRiss);
			WizardActualizaRfcCtrl.setWizardPrincipal(WizardSolicitarRifCtrl);
			WizardActualizaRfcCtrl.abrir();
			
		}else{
			//Preparar Wizar WizardSolicitarRifCtrl
			WizardSolicitarRifCtrl.init('divWizardBeneficiosRiss', rfc, idPersona);
			WizardSolicitarRifCtrl.abrir();			
		}
	},
	

	registroAltaPatronal : function() {
		var rfc = $("#rfc").val();
		var idPersona = $("#cveIdPersona").val();
		
		var url = '/delta-gestionPatronal-web-ciudadano/wizard/tramite/registro/patronal/validarPersonaParaAlta/' 
			+ idPersona + '/' + tipoPersonaFisica;
		
		$.blockUI();
		$.getJSON(url, function(data) {
			
			$.unblockUI();
			
			var respuesta = data.respuesta;
	        if (respuesta == 1) {
				construirDialogo("#dialogoMensajes", "Importante", data.msgError, true, undefined, undefined, 250, 600);
				return;
			} else {
				//Verificar solicitar Actualizar RFC 
				var idPersona = $("#cveIdPersona").val();
				
				WizardActualizaRfcCtrl.validarSolicitarRfc(idPersona);
				
				if(WizardActualizaRfcCtrl.config.solicitarRfc){
					//Preparar Wizar WizardSolicitarRifCtrl			
					WizardAltaPatronalCtrl.init('wizardAltaPatronal', idPersona, tipoPersonaFisica);
					//Preparar Actualizar RFC
					WizardActualizaRfcCtrl.init(tramitesPortalCiudadano.divActualizacionRfcComponent, idPersona, true, _idTramiteAltaPatronal);
					WizardActualizaRfcCtrl.setWizardPrincipal(WizardAltaPatronalCtrl);
					//Preparar CT
				    CartaTerminosCtrl.init(tramitesPortalCiudadano.divComponentCommon, _idTramiteAltaPatronal);
				    CartaTerminosCtrl.setWizardPrincipal(WizardActualizaRfcCtrl);
				    CartaTerminosCtrl.abrir();			
				}else{
					WizardAltaPatronalCtrl.init('wizardAltaPatronal', idPersona, tipoPersonaFisica);
					//Abrir dialogo CT
				    CartaTerminosCtrl.init(tramitesPortalCiudadano.divComponentCommon, _idTramiteAltaPatronal);
				    CartaTerminosCtrl.setWizardPrincipal(WizardAltaPatronalCtrl);
				    CartaTerminosCtrl.abrir();	
				}
			}
	    });
		
		
	},
	
	altaSeguroFamiliar : function() {
		var _idPersona = $("#cveIdPersona").val();
		var _nssCifrado = $("#nssCifrado").val();
		var _correo = $("#correo").val();
		
		WizardAltaSeguroFamiliarCtrl.init('divWizardSeguroFamiliar', _idPersona, _nssCifrado, _correo);
        WizardAltaSeguroFamiliarCtrl.setMuestraWizard(false);
		WizardAltaSeguroFamiliarCtrl.abrir();
	},

	altaContinuacionVoluntaria : function() {
		var _idPersona = $("#cveIdPersona").val();
		var _nssCifrado = $("#nssCifrado").val();
		var _correo = $("#correo").val();
		WizardAltaCVROCtrl.config.correo =_correo;
		WizardAltaCVROCtrl.init('divWizardContinuacionVoluntaria', _idPersona, _nssCifrado);
		WizardAltaCVROCtrl.abrir();
	}
	
};

$(document).ready(function() {
	
	CometCtrl.prototype.addSubscriber = function(subscription) {
		this.listInitialSubscription.push(subscription);
	};

	setTimeout(function() {
		conector = new CometCtrl();
		conector.init(false);
	}, 1100);
	
	$('div#menu-wrapper').menu({
		'opcionesSrc' : 'opcionesPrincipalesCiudadano'
	});
	
	$("#cambioClinica").click(function(){
		tramitesPortalCiudadano.cambioClinica();
	});

	$("#registroAseg").click(function(){
		tramitesPortalCiudadano.registroAsegurado();
	});

	$("#consultaVig").click(function(){
		tramitesPortalCiudadano.comprobanteVigencia();
	});
	
	$("#bajaDerechohabiente").click(function(){
		tramitesPortalCiudadano.bajaBeneficiario();
	});

	$("#registroBen").click(function(){
		tramitesPortalCiudadano.registroBeneficiario();
	});
	
	$("#asignacionNSS").click(function(){
		alert("Click asignacionNSS");
	});
	
	$('#ivroIndividual').click(function(){
		tramitesPortalCiudadano.ivroIndividual();
	});

    $('#tramiteProrroga').click(function(){
        tramitesPortalCiudadano.tramiteProrroga();
    });

	$('#ivroDomestico').click(function(){
		tramitesPortalCiudadano.ivroDomestico();
	});
	
	$('#beneficiosRiss').click(function(){
		tramitesPortalCiudadano.beneficiosRiss();
	});

	$('#registroAltaPatronal').click(function(){
		tramitesPortalCiudadano.registroAltaPatronal();
	});
	
	$('#seguroFamiliar').click(function(){
		tramitesPortalCiudadano.altaSeguroFamiliar();
	});
	
	$('#inscripcionCVRO').click(function(){
		tramitesPortalCiudadano.altaContinuacionVoluntaria();
	});
	
	/**
	 * Verifica si la perte inferior de un elemento es visible en la pantalla
	 */
	$.fn.isVisibleBottom = function() {
	    var windowScrollTopView = $(window).scrollTop();
	    var windowBottomView = windowScrollTopView + $(window).height();
	    var elemTop = $(this).offset().top;
	    var elemBottom = elemTop + $(this).height();
	    return (elemBottom <= windowBottomView) ;
	};

	/**
	 * Verifica si la perte superior de un elemento es visible en la pantalla
	 */
	
    $.fn.isVisibleTop = function() {
        var windowScrollTopView = $(window).scrollTop();
        var elemTop = $(this).offset().top;
        return (elemTop >= windowScrollTopView);
    };
	
    // ---------------------------------------------------------
    // Para que los dialogos no oculten los mensajes ajax
    // ---------------------------------------------------------
    $.blockUI.defaults.baseZ = 3000;
    
    // Se inicializa el componente de Procesando Solicitud
    ProcesandoSolicitudCtrl.init('procesandoSolicitudComponent', tiempoEspera, tiempoIntervaloEspera);
    ProcesandoSolicitudCtrl.setOnCloseCallback(function() {});
       
    //ACCIONES DE DOMICILIOS
    if ($('li#registroDomicilio').length > 0) {
			$.getScript('/gestionDomicilios-web-ciudadano/static/resources/js/delta/domicilios/wizard/general/WizardDomicilioGeneral.js', function(){
				$(document).on('click', 'li#registroDomicilio', function() {
					ProcesandoSolicitudCtrl.setOnSolicitudExitosaCallback(null);
					actualizarDomicilio();
				});
			});
    }
    
    function actualizarDomicilio() {
    	init();
		_wizardActualizarDomiclio = WizardDomicilioGeneralCtrl;
		_wizardActualizarDomiclio.init({
			idPersona : _datoPersona.idPersona
		}).abrir();
	}
    
    function getJsonPersona(){
    	var data = $('input#jsonCiudadano').val();
    	return JSON.parse(data);
    }
    
    function precargarInformacionIdentidad() {
    	var ciudadano = getJsonPersona();
		var _personaUbicada = {
			idPersona : ciudadano.cveIdPersona,
			tipoPersona : 1,
			curp : ciudadano.curp != null ? ciudadano.curp
					: 'SIN_CURP',
			rfc : ciudadano.rfc != null ? ciudadano.rfc
					: 'SIN_RFC',
			contenedor : 'wizardDatosActualizacion'
		};
		return _personaUbicada;
	}

	function init() {
		_datoPersona = precargarInformacionIdentidad();
	}
	
	//Inicializar detalle seguro persona.
	WizardDetalleSeguroCtrl.init('wizardDetalleSeguroComponent', null);	
	//Inicializar detalle seguro domestico.
	WizardDetalleDomesticoCtrl.init('wizardDetalleSeguroDomesticoComponent', null, null, null);
	
});

function ejecutarConsultaSolicitudPorFolio (folio) {
	
	if (folio == null || folio == '' || typeof folio === 'undefined') {
		
		alert('El n\u00FAmero de folio es requerido para la realizar la b\u00FAsqueda.');
		
		return false;
	}
		
	DetalleSolicitudCtrl.setEsFolioNormal(true);
	DetalleSolicitudCtrl.init("detalleSolicitudComponent",folio);
	DetalleSolicitudCtrl.abrir();
	
}

function iniciarValidacionesIvroindiv(response){
	//Inicia validaci�n Generaci�n de LC
	if (response.lineasGeneradas !== 'undefined' && response.lineasGeneradas == false){
		$.unblockUI();
        construirDialogo("#dialogoMensajes",
                "Mensaje de sistema", response.msgError,
                true, undefined, undefined, 250, 400);
	} else {
		
		if(response.error==true){
			
			//IVRO2024 Se inhibe el acceso al tramite
			if(response.msgError.indexOf("IVRO2024")!=-1){
				console.log("Se inhibe el acceso al tramite IVRO 2024");
			}else{
								
				var callbackIvro = undefined;
				var abrirDomicilio=false;
				if(response.msgError.indexOf("696")!=-1){
					abrirDomicilio=true;
					response.msgError=response.msgError.substring(5, response.msgError.length);
				}
				
				$divError = $('<div></div');
				$divError.dialog({
					autoOpen : false,
					resizable : false,
					width: 400,
					height : 'auto',
					title : 'Mensaje del sistema',
					modal : true,
					close: function(){
						if(abrirDomicilio)
							callbackActualizarDomicilio();
					},
					buttons : {
						"Aceptar" : function() {
							$(this).dialog('close');	
						}
					}
				}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
		
				var htmlError ='<div class="alert alert-info">'+
					''+
					'<strong>Importante: </strong>'+ response.msgError + '</div>';
				$divError.html(htmlError);
				$divError.dialog('open');
			
			}
				
		}else{
			iniciarFlujoPrincipalIvroindividual(null);
		}
	}
}

function iniciarFlujoPrincipalIvroindividual(folio){
	ProcesandoSolicitudCtrl.setOnSolicitudExitosaCallback(null);
	
	var datos = {
			contenedor : 'divWizardIvroIndividual',
	        idPersona : $("#cveIdPersona").val(),
	        rfc : $("#rfc").val()
	    };
		
		var rfc = datos.rfc;
		var idPersona = datos.idPersona;
		
		//Preparar Wizar WizardSeguroIvroIndivCtrl
		if(rfc == null || rfc == '' || typeof rfc === 'undefined'){
			datos.rfc = 'SIN_RFC';
		    WizardSeguroIvroIndivCtrl.init(datos);
		}else{
			 WizardSeguroIvroIndivCtrl.init(datos);
		}
		
		//Verificar solicitar Actualizacion del RFC 
		WizardActualizaRfcCtrl.validarSolicitarRfc(idPersona);
		
		if(WizardActualizaRfcCtrl.config.solicitarRfc){
						
			//Preparar Actualizar RFC
			WizardActualizaRfcCtrl.init(tramitesPortalCiudadano.divActualizacionRfcComponent, idPersona, false, 118);
			WizardActualizaRfcCtrl.setWizardPrincipal(WizardSeguroIvroIndivCtrl);
			console.log("valida Seguro Comprado LALO 1");
			
			//Preparar CT
			var url = '/gestionSeguroVoluntario-web-ciudadano/wizard/individual/validarSeguroComprado/' + idPersona;
			$.getJSON(url, tramitesPortalCiudadano.cartaTerminoIvroIndividualRfc);
			
		}else{
			
			console.log("valida Seguro Comprado LALO 2");
			
			var url = '/gestionSeguroVoluntario-web-ciudadano/wizard/individual/validarSeguroComprado/' + idPersona;
			$.getJSON(url, tramitesPortalCiudadano.cartaTerminoIvroIndividual);
		}	    

}

function validarAccesoTramiteDomestico(response){
	if(response.error==true){
		tramitesPortalCiudadano.mostrarMensajeError(response.msgError);
	}else{
		iniciarFlujoPrincipalIvroDomestico();
	}
}

function iniciarFlujoPrincipalIvroDomestico(){
	var idPersona = $("#cveIdPersona").val();
	var rfc = $("#rfc").val();
	var nssCifrado = $("#nssCifrado").val();
	var urlValidarSeguroComprado = '/gestionSeguroVoluntario-web-ciudadano/wizard/seguroDomestico/validarSeguroComprado/';
	if(nssCifrado == null || nssCifrado == '' || typeof nssCifrado === 'undefined'){
		nssCifrado = "-1";
	}
	//Verificar solicitar Actualizacion del RFC 
	WizardActualizaRfcCtrl.validarSolicitarRfc(idPersona);
	
	if(WizardActualizaRfcCtrl.config.solicitarRfc){
		//Preparar Wizar WizardIVROVentanillaSeguroDomesticoCtrl
		WizardIVROVentanillaSeguroDomesticoCtrl.init('wizardAltaSeguroVoluntario', idPersona, rfc, nssCifrado);
		
		//Preparar Actualizar RFC
		WizardActualizaRfcCtrl.init(tramitesPortalCiudadano.divActualizacionRfcComponent, idPersona, true, _idTramiteIvroDomestico);
		WizardActualizaRfcCtrl.setWizardPrincipal(WizardIVROVentanillaSeguroDomesticoCtrl);

		//Preparar CT
		if(rfc == null || rfc == '' || typeof rfc === 'undefined'){
			rfc='SIN_RFC';
		}
		
		urlValidarSeguroComprado = urlValidarSeguroComprado + idPersona + '/' + rfc + '/' + nssCifrado;
		$.getJSON(urlValidarSeguroComprado, tramitesPortalCiudadano.cartaIvroDomesticoRfc);	
		
	}else{
		//Preparar Wizar WizardIVROVentanillaSeguroDomesticoCtrl
		WizardIVROVentanillaSeguroDomesticoCtrl.init('wizardAltaSeguroVoluntario', idPersona, rfc, nssCifrado);

		//Preparar CT
		if(rfc == null || rfc == '' || typeof rfc === 'undefined'){
			rfc='SIN_RFC';
		}
		
		urlValidarSeguroComprado = urlValidarSeguroComprado + idPersona + '/' + rfc + '/' + nssCifrado;
		$.getJSON(urlValidarSeguroComprado, tramitesPortalCiudadano.cartaIvroDomestico);		
	}	
}


function callbackActualizarDomicilio() {
	
	var idPersonaDomicilio=$("#cveIdPersona").val();
	ProcesandoSolicitudCtrl.setOnSolicitudExitosaCallback(function(){
		ProcesandoSolicitudCtrl.cerrar();
		iniciarFlujoPrincipalIvroindividual();
	});
	
	$.getScript('/gestionDomicilios-web-ciudadano/static/resources/js/delta/domicilios/wizard/general/WizardDomicilioGeneral.js', function(){
		_wizardActualizarDomiclio = WizardDomicilioGeneralCtrl;
		_wizardActualizarDomiclio.init({
			idPersona : idPersonaDomicilio
		}).abrir();
	});
	
	
}

function construirDialogo(divId, titulo, mensaje, error, callback, callbackForXButton, height, width) {
	$("#textoMensaje").html(mensaje);
	$("#textoMensaje").removeAttr("style");
	if (error) {
		$("#textoMensaje").attr("style", "color: red;");
	} else {
		$("#textoMensaje").attr("style", "color: blue;");
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
