var WizardCorreccionDatosAseguradoCURP = {

	setDatos : function(_idPersona, _nssCifrado, _curp) {
		this.config.idPersona = _idPersona;
		this.config.nssCifrado = _nssCifrado;
		this.config.curp = _curp;
		this.init(this.config.container, this.config.idPersona, this.config.nssCifrado, this.config.curp);
		},

	init : function(_container, _idPersona, _nssCifrado, _curp) {
		// this.config.url = _url;
		this.config.container = _container;
		this.config.idPersona = _idPersona;
		this.config.nssCifrado = _nssCifrado;
		this.config.curp = _curp;
		
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
				// $('iframe#wizardCDACURPFrame').attr("src",
				// "");
				var windowFrame = window.frames['wizardCDACURPFrame'].contentWindow;
				if (windowFrame === undefined || windowFrame == null)
					windowFrame = window.frames['wizardCDACURPFrame'].frameElement.contentWindow;
				var cancel = windowFrame.cancelable;
				if (cancel !== undefined && cancel == true) {
					event.preventDefault();
					windowFrame.cancelarDesdeBoton = false;
					windowFrame.dialogoConfirmarCancelar.dialog('open');
				}
			},
			close : function(event, ui) {
				WizardCorreccionDatosAseguradoCURP.limpiarDatos();
				$(this).dialog('destroy').empty();
			}
		});
	},
	
	open : function() {
		this.dialogo.dialog('open');
		var url = this.config.url + '/' + this.config.curp;
		$('#' + this.config.container).html(
			'<iframe id="wizardCDACURPFrame" src="'
					+ url
					+ '" width="100%" height="100%" frameborder="0"'
					+ 'onload="set_size(\'wizardCDACURPFrame\')" frameborder="0" />');
	},
	
	close : function() {
		if (!$.isEmptyObject(this.dialogo)) {
			this.dialogo.dialog('close');
		} else {
			$('#wizardCDACURPFrame').parent().dialog('close');
		}
	},
	
	abrir : function() {
		this.validarAccesoTramite();
	},
	
	cerrar : function() {
		this.close();
	},
	
	cerrarSolicitud: function(){
		consola.log(' -- Finalizando solcitud:::');
	},
	
	validarAccesoTramite : function() {		
		var _wizard = this;
		
		$.blockUI();
		

		$.ajax({
			url : '/${mvn.web.app.root}/wizard/correccionDatosAsegurado' + '/validarAccesoTramite/'
					+ this.config.curp,
			dataType : 'json',
			success : function(response) {
				$.unblockUI();
				if (response.error == true) {
					construirDialogo("#dialogoMensajes",
							"Mensaje de sistema", response.msgError,
							true, undefined, undefined, 250, 400);
				} else {
					_wizard.open();
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
		$.ajax('/${mvn.web.app.root}/wizard/correccionDatosAsegurado/comunes/limpiarDatos').done(listener);
	},
	
	config : {
		url : '/${mvn.web.app.root}/wizard/correccionDatosAsegurado/init',
		title : 'Correcci\u00F3n de Datos de Asegurado con CURP',
		container : null,
		idPersona : null,
		curp : null
	},
	
	dialogo : {}
};