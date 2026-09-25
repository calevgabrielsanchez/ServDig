/**
 * Objeto de control de medios de contacto, a traves de este objeto de JS
 * se iniciliaza, configura , invoca y regresa un objeto 
 * con los medios de contacto-.
 */
var AdmonRegistroMedioContactoCtrl = {

	/*
	 * Funcion para inicializar la configuracion
	 */
	init : function(_contenedor) {

		this.config.contenedor = _contenedor;

		/*
		 * Inicializamos el dialogo que contiene la pantalla 
		 *
		 * Se crea una variable para el control del dialogo
		 * que sera a traves de un iFrame
		 */
		var d = $('#' + _contenedor);

		// Configuracion del dialogo
		this.dialogo = d.dialog({
			title : this.config.title,
			autoOpen : false,
			width : 500,
			modal : true,
			resizable : false,
			overlay : {
				opacity : 0.5,
				background : "black"
			},
			position: { my: "top", at: "top", of: window, offset: "0 10" }
		});

		// Configuramos el metodo onClose del dialogo
		this.dialogo.dialog({
			beforeClose : function(event, ui) {
				/*
				 * Workaround para evitar que el iframe se recarge al momento de
				 * cerrar el dialogo de jQuery, este comportamiento es resultado
				 * de un bug de jQuery
				 */
				$('iframe#registroMedioFrame').attr("src", "");
			},
			close : function(event, ui) {
	    		// Se destruye el dialogo
	    		$(this).dialog('destroy').empty();
	    		AdmonRegistroMedioContactoCtrl.callbacks.call();
			}
		});
	},

	setOnCloseCallback : function(_fnCallback) {
		this.callbacks = _fnCallback;
	},

	/*
	 * Datos de configuracion inicial
	 * de la consulta de la persona moral.
	 */
	config : {
		url : "/gestionMediosContacto-web/medios/particulares/administrar/init-agregar/",
		idPersona : "",
		title : "Registrar Medio de Contacto Particular",
		contenedor : {}
	},

	/*
	 * Callback a invocar cuando se 
	 * termine la invocacion de la consulta
	 */
	callbacks : {},

	dialogo : {},

	// Objeto de medios de contacto 
	mediosContacto : {},

	registrar : function() {
		//Para que cada vez que se abra el dialogo se cree de nuevo.
		this.init(this.config.contenedor);

		this.dialogo.dialog('open');
		$('#' + this.config.contenedor).html(
				'<iframe id="registroMedioFrame" src="' + this.config.url + this.config.idPersona
						+ '" width="100%" height="100%" frameBorder="0" '
						+ 'onload="set_size(\'registroMedioFrame\')" />');
	},
	
	cerrar : function() {
		//Cerramos el dialogo
		this.dialogo.dialog('close');
	}
};