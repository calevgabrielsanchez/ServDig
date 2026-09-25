var WizardAltaCVROCtrl = {

	setDatos : function(_idPersona, _nssCifrado) {
		this.config.idPersona = _idPersona;
		this.config.nssCifrado = _nssCifrado;
		this.init(this.config.container, this.config.idPersona, this.config.nssCifrado);
		},

	setDatosRenovacion : function(_idPersona, _nssCifrado, _title, _url) {

			this.config.idPersona = _idPersona;
			this.config.nssCifrado = _nssCifrado;
			this.config.title = _title;
			this.config.url = _url + "/" + this.config.idSeguroCifrado ;
			this.init(this.config.container, this.config.idPersona, this.config.nssCifrado);
			},

	init : function(_container, _idPersona, _nssCifrado) {
		// this.config.url = _url;
		this.config.container = _container;
		this.config.idPersona = _idPersona;
		this.config.nssCifrado = _nssCifrado;

		var c = null;

		if ($('#' + _container).lenght == 0)
			c = $('#' + _container, parent.document);
		else
			c = $('#' + _container);

		this.dialogo = c.dialog({
			title : this.config.title,
			autoOpen : false,
			width : 960,
			modal : true,
			resizable : false,
			autoResize : true,
			overlay : {
				opacity : 0.5,
				background : 'black'
			},
			position : {
				my : 'top',
				at : 'top',
				of : window,
				offset : '0 10'
			},

			beforeClose : function(event, ui) {
				// $('iframe#wizardAltaCVROFrame').attr("src",
				// "");
				var windowFrame = window.frames['wizardAltaCVROFrame'].contentWindow;
				if (windowFrame === undefined || windowFrame == null)
					windowFrame = window.frames['wizardAltaCVROFrame'].frameElement.contentWindow;
				var cancel = windowFrame.cancelable;
				if (cancel !== undefined && cancel == true) {
					event.preventDefault();
					windowFrame.cancelarDesdeBoton = false;
					windowFrame.dialogoConfirmarCancelar.dialog('open');
				}
			},
			close : function(event, ui) {
				WizardAltaCVROCtrl.limpiarDatos();
				$(this).dialog('destroy').empty();
			}
		});
	},

	open : function() {
		this.dialogo.dialog('open');
		var url = this.config.url + '/' + this.config.idPersona + '/'
				+ this.config.nssCifrado;
		$('#' + this.config.container).html(
			'<iframe id="wizardAltaCVROFrame" src="'
					+ url
					+ '" width="100%" height="100%" frameborder="0"'
					+ 'onload="set_size(\'wizardAltaCVROFrame\')" frameborder="0" />');
	},

	close : function() {
		if (!$.isEmptyObject(this.dialogo)) {
			this.dialogo.dialog('close');
		} else {
			$('#wizardAltaCVROFrame').parent().dialog('close');
		}
	},

	abrir : function() {
		this.validarAccesoTramite();
	},
	
	abrirConBajaExpresa: function(){
		this.validarAccesoTramiteConBajaExpresa();
	},

	cerrar : function() {
		this.close();
	},

	cerrarSolicitud: function(){
		consola.log(' -- Finalizando solcitud:::');
	},
	
	validarAccesoTramiteConBajaExpresa : function() {
		var _wizard = this;
		console.log('###Inicia validar acceso tramite');
		$.blockUI();


		$.ajax({
			url : '/${mvn.web.app.root}/wizard/continuacionVoluntaria' + '/validarTramiteRecompraExpresa/'
					+ this.config.idPersona+"/"+this.config.correo+'/',
			dataType : 'json',
			success : function(response) {
				$.unblockUI();
				if (response.lineasGeneradas !== 'undefined' && response.lineasGeneradas == false){

					var liga = '/${mvn.web.app.root}/wizard/detalle/seguro/finalizarSeguroDetalle/obtenerLcSipare/';
						$.ajax({
							url : liga + response.idSeguro,
							dataType : 'json',
							success : function(response) {
								console.log('Respondio obtenerLcSipare');
								$.unblockUI();
							},
							error : function(error) {
								$.unblockUI();
								var msgError = error.msgError;
								if (typeof msgError === 'undefined') {
									msgError = 'Ocurri\u00f3 un error inesperado al enviar correo.';
								}
								parent.construirDialogo("#dialogoMensajes",
										"Mensaje de sistema", msgError, true,
										undefined, undefined, 250, 400);
							}
						});

					$.unblockUI();
			        construirDialogo("#dialogoMensajes",
			                "Mensaje de sistema", response.msgError,
			                true, undefined, undefined, 250, 400);
				} else {
					if (response.tieneSeguro == true) {
						_wizard.config.idSeguro =response.idSeguro;
						if (typeof WizardDetalleSeguroCtrl === 'undefined') {
							var urlDetalle = '/gestionSeguroVoluntario-web-ciudadano/static/resources/js/wizard/persona/ivro/detalle/wizar-detalle-seguro.js';

							$.getScript(urlDetalle, function(){
								WizardDetalleSeguroCtrl.setIdSeguroRenovacion("Estado del seguro");
								WizardDetalleSeguroCtrl.init(_wizard.config.container, response.idSeguro);
								WizardDetalleSeguroCtrl.abrir(response.idSeguro);
							});
						} else {
							WizardDetalleSeguroCtrl.setIdSeguroRenovacion("Estado del seguro");
							WizardDetalleSeguroCtrl.init(_wizard.config.container, response.idSeguro);
							WizardDetalleSeguroCtrl.abrir(response.idSeguro);
						}
					}
					else if (response.error == true) {
						construirDialogo("#dialogoMensajes",
								"Mensaje de sistema", response.msgError,
								true, undefined, undefined, 250, 400);
					} else {
						_wizard.open();
					}
				}
			},
			error : function(error) {
				$.unblockUI();

				var msgError = error.msgError;
				if (typeof msgError === 'undefined') {
					msgError = 'Ocurri\u00f3 un error inesperado al validar el acceso al tr\u00e1mite.';
				}

				construirDialogo("#dialogoMensajes",
						"Mensaje de sistema", msgError, true,
						undefined, undefined, 250, 400);
			}
		});		
	},

	validarAccesoTramite : function() {
		var _wizard = this;
		console.log('###Inicia validar acceso tramite');
		$.blockUI();


		$.ajax({
			url : '/${mvn.web.app.root}/wizard/continuacionVoluntaria' + '/validarAccesoTramite/'
					+ this.config.idPersona+"/"+this.config.correo+'/',
			dataType : 'json',
			success : function(response) {
				$.unblockUI();
				if (response.lineasGeneradas !== 'undefined' && response.lineasGeneradas == false){

					var liga = '/${mvn.web.app.root}/wizard/detalle/seguro/finalizarSeguroDetalle/obtenerLcSipare/';
						$.ajax({
							url : liga + response.idSeguro,
							dataType : 'json',
							success : function(response) {
								console.log('Respondio obtenerLcSipare');
								$.unblockUI();
							},
							error : function(error) {
								$.unblockUI();
								var msgError = error.msgError;
								if (typeof msgError === 'undefined') {
									msgError = 'Ocurri\u00f3 un error inesperado al enviar correo.';
								}
								parent.construirDialogo("#dialogoMensajes",
										"Mensaje de sistema", msgError, true,
										undefined, undefined, 250, 400);
							}
						});

					$.unblockUI();
			        construirDialogo("#dialogoMensajes",
			                "Mensaje de sistema", response.msgError,
			                true, undefined, undefined, 250, 400);
				} else {
					if (response.tieneSeguro == true) {
						_wizard.config.idSeguro =response.idSeguroCifrado;
						_wizard.config.idSeguroCifrado =response.idSeguroCifrado;
						if (typeof WizardDetalleSeguroCtrl === 'undefined') {
							var urlDetalle = '/gestionSeguroVoluntario-web-ciudadano/static/resources/js/wizard/persona/ivro/detalle/wizar-detalle-seguro.js';

							$.getScript(urlDetalle, function(){
								WizardDetalleSeguroCtrl.setIdSeguroRenovacion("Estado del seguro");
								WizardDetalleSeguroCtrl.init(_wizard.config.container, response.idSeguro);
								WizardDetalleSeguroCtrl.abrir(response.idSeguro);
							});
						} else {
							WizardDetalleSeguroCtrl.setIdSeguroRenovacion("Estado del seguro");
							WizardDetalleSeguroCtrl.init(_wizard.config.container, response.idSeguro);
							WizardDetalleSeguroCtrl.abrir(response.idSeguro);
						}
					}
					else if (response.error == true) {
						construirDialogo("#dialogoMensajes",
								"Mensaje de sistema", response.msgError,
								true, undefined, undefined, 250, 400);
					} else {
						_wizard.open();
					}
				}
			},
			error : function(error) {
				$.unblockUI();

				var msgError = error.msgError;
				if (typeof msgError === 'undefined') {
					msgError = 'Ocurri\u00f3 un error inesperado al validar el acceso al tr\u00e1mite.';
				}

				construirDialogo("#dialogoMensajes",
						"Mensaje de sistema", msgError, true,
						undefined, undefined, 250, 400);
			}
		});
	},

	limpiarDatos : function (listener) {
		$.ajax('/${mvn.web.app.root}/wizard/continuacionVoluntaria/comunes/limpiarDatos').done(listener);
	},

	config : {
		url : '/${mvn.web.app.root}/wizard/continuacionVoluntaria/alta/init',
		title : 'Inscripci\u00F3n a la continuaci\u00F3n voluntaria en el r\u00E9gimen obligatorio',
		container : null,
		idPersona : null,
		idSeguro : null,
		correo : null,
		idSeguroCifrado : null
	},

	dialogo : {}
};