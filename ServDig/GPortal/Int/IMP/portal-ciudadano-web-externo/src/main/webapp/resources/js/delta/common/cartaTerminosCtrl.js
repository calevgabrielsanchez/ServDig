var CartaTerminosCtrl = {
	wizardPrincipal : null,
	
	setWizardPrincipal : function(wizardPrincipal){
		CartaTerminosCtrl.wizardPrincipal = wizardPrincipal;
	},
	
	setOnCancelCallback : function(_fnCallback){
		this.cancelCallbacks = _fnCallback;
	},
	
	setOnAceptCallback : function(_fnCallback){
		this.aceptCallbacks = _fnCallback;
	},

	/*
	 * Funcion para inicializar la configuracion
	 */
	init : function(_contenedor, _tipoTramite) {
		
		this.config.contenedor = _contenedor;
		this.config.tipoTramite = _tipoTramite;
		
		var d = null;		
		if ($('#' + _contenedor).length == 0) {
			d = $('#' + _contenedor, parent.document);
		} else {
			d = $('#' + _contenedor);
		}

		/*
		 * Configuracion del dialogo
		 */
        this.dialogo = d.dialog({
            title: this.config.title,
	        closeOnEscape: false,
            width : 900,
            modal: true,
            resizable: false,
            autoResize : true,
            open: function(event) { $(".ui-dialog-titlebar-close", $(this).parent()).hide(); },
            overlay: {
                opacity: 0.5,
                background: "black"
            },
            position: { my: "top", at: "top", of: window, offset: "0 10" }
        });

		/*
		 * Configuramos el metodo onClose del dialogo
		 * 
		 */
        this.dialogo.dialog({
        	beforeClose: function(event, ui) {        		
        		$('iframe#cartaTCFrame').attr("src", "");
        	},
        	close: function (event, ui) {
        		$(this).dialog('destroy').empty();
        	}
        });
	},
	/*
	 * 
	 */
	setOnCloseCallback : function(_fnCallback) {
		this.callbacks = _fnCallback;
	},
	/*
	 * Datos de configuracion inicial de la consulta de la persona moral.
	 */
	config : {
		url : "/portal-ciudadano-web-externo/cartaTerminos/mostrarCartaTerminos/",
		title : "IMSS Digital ",
		contenedor : {},
		tipoTramite : ""
		
	},
	/*
	 * Callback a invocar cuando se termine la invocacion de la consulta
	 */
	callbacks : {},
	
	cancelCallbacks : {},
	
	aceptCallbacks : {},

	dialogo : {},

	/**
	 * 
	 */
	abrir : function() {
		// Para que cada vez que se abra el dialogo se cree de nuevo.
		this.init(this.config.contenedor, this.config.tipoTramite);
		this.dialogo.dialog('open');
		
		var url = this.config.url + this.config.tipoTramite;
		
		$('#' + this.config.contenedor).html(
				'<iframe id="cartaTCFrame" src="' + url
						+ '" width="100%" height="100%" frameborder="0"'
						+ 'onload="set_size(\'cartaTCFrame\')" frameborder="0" />');

	},

	cerrar : function() {
		// Cerramos el dialogo
		this.dialogo.dialog('close');
		//this.dialogo.dialog('destroy');
	},
	
	cancelarCarta : function() {
		if (jQuery.isFunction(CartaTerminosCtrl.cancelCallbacks)) {
			this.cerrar();
			CartaTerminosCtrl.cancelCallbacks.call();
		} else {
			var _wizard = null;
			var _subWizard = null;
					
			if(this.wizardPrincipal != null){
				_wizard = this.wizardPrincipal;
				if(this.wizardPrincipal.wizardPrincipal != null){
					_subWizard = this.wizardPrincipal.wizardPrincipal;				
				}				
			}
			
			if (_wizard != null) {
		    	_wizard.cerrar();
			}
			if (_subWizard != null) {
				_subWizard.cerrar();
			}
			
			//Cerrar dialogo carta TC
			CartaTerminosCtrl.cerrar();			
		}
	},
	
	abrirWizardPrincipal : function() {
		if (jQuery.isFunction(CartaTerminosCtrl.aceptCallbacks)) {
			this.cerrar();
			CartaTerminosCtrl.aceptCallbacks.call();
		} else {
			var _wizard = null;
			
			if(this.wizardPrincipal != null){
				_wizard = this.wizardPrincipal;
			}
			
			if (_wizard != null) {
		    	_wizard.abrir();
			}
			
			//Cerrar dialogo carta TC
			CartaTerminosCtrl.cerrar();	
		}
	}
	
	
	
	
	
};