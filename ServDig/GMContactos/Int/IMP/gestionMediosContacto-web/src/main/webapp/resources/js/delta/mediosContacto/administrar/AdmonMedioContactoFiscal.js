/**
 * Objeto de control de medios de contacto, a traves de este objeto de JS
 * se iniciliaza, configura , invoca y regresa un objeto 
 * con los medios de contacto-.
 */
var AdmonMedioContactoFiscalCtrl = {

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
			height : 400,
			modal : true,
			resizable : false,
			overlay : {
				opacity : 0.5,
				background : "black"
			}
		});

		// Configuramos el metodo onClose del dialogo
		this.dialogo.dialog({
			beforeClose : function(event, ui) {
				AdmonMedioContactoFiscalCtrl.callbacks
						.call(AdmonMedioContactoFiscalCtrl.mediosContacto);
			},
			close : function(event, ui) {
	    		// Se destruye el dialogo
	    		$(this).dialog('destroy');
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
		url : "/gestionMediosContacto-web/medios/fiscales/administrar/init-agregar",
		urlModificar : "/gestionMediosContacto-web/medios/fiscales/administrar/modificar/",
		title : "Registrar/Modificar medio de contacto fiscal",
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
				'<iframe id="site" src="' + this.config.url
						+ '" width="100%" height="100%" />');
	},
	
	modificar : function(index) {
		//Para que cada vez que se abra el dialogo se cree de nuevo.
		this.init(this.config.contenedor);

		this.dialogo.dialog('open');
		$('#' + this.config.contenedor).html(
				'<iframe id="site" src="' + this.config.urlModificar + index 
						+ '" width="100%" height="100%" />');
	},
	cerrar : function() {
		//Cerramos el dialogo
		this.dialogo.dialog('close');
	}
};