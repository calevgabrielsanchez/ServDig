var WizardAltaSociosCtrl = {

	/*
	 * Funcion para inicializar la configuracion
	 */
	init : function(_contenedor, _idPersona, _rfc) {
		
		this.config.contenedor = _contenedor;
		this.config.idPersona = _idPersona;
		this.config.rfc = _rfc;
		this.config.contextPath = '/${mvn.web.app.root}';
		this.config.idOrigen = '${mvn.web.app.origin.id}';
		
		/*
		 * Se crea una variable para el control del dialogo que sera a traves de
		 * un iFrame
		 */
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
			title : this.config.title,
			autoOpen : false,
			width : 900,
			modal : true,
			resizable : false,
			autoResize : true,
			overlay : {
				opacity : 0.5,
				background : "black"
			},
			position : {
				my : "top",
				at : "top",
				of : window,
				offset : "0 10"
			}
		});

		/*
		 * Configuramos el metodo onClose del dialogo
		 * 
		 */
		this.dialogo.dialog({
			beforeClose : function(event, ui) {
				WizardAltaSociosCtrl.limpiarElementosSesion();
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
		url : "/wizard/tramite/socios/iniciarRegistro/",
		title : "IMSS Digital",
		contenedor : {},
		rfc : "",
		idPersona : "",
		contextPath : "",
		idOrigen:""
	},
	/*
	 * Callback a invocar cuando se termine la invocacion de la consulta
	 */
	callbacks : {},

	dialogo : {},

	/**
	 * 
	 */
	abrir : function() {
		// Para que cada vez que se abra el dialogo se cree de nuevo.
		this.init(this.config.contenedor, this.config.idPersona, this.config.rfc);
		this.dialogo.dialog('open');
		
		var url = this.config.contextPath  + this.config.url + this.config.rfc 
		+ "/" + this.config.idPersona;
		
		$('#' + this.config.contenedor).html('<iframe id="wizardSociosFrame" src="' + url
			+ '" width="100%" height="100%" frameborder="0"'
			+ 'onload="set_size(\'wizardSociosFrame\')" frameborder="0" />');
		
	},

	cerrar : function() {
		// Cerramos el dialogo
		this.dialogo.dialog('close');
		this.dialogo.dialog('destroy');
	},
	
	limpiarElementosSesion : function() {
		
		// Se limpia la sesión
		$.postJSON(this.config.contextPath + '/wizard/tramite/socios/limpiar-sesion', null, function(data) {
			
		}).error(function(data){
			
		});	
	}
	
};