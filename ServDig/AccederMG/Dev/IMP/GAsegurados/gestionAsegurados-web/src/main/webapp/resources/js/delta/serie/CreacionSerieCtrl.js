var CreacionSerieCtrl = {

	dialogo : {},
	init : function(idContenedor) {

		this.config.contenedor = idContenedor;

		var d = $('#' + idContenedor);

		// Configuracion del dialogo
		this.dialogo = d.dialog({
			title : this.config.title,
			autoOpen : false,
			width : 600,
			minHeight : 200,
			modal : true,
			resizable : false,
			autoResize : true,
			overlay : {
				opacity : 0.5,
				background : "black"
			},
			position: { my: "top", at: "top", of: window, offset: "0 10" }
		});

		// Configuramos el metodo onClose del dialogo
		this.dialogo.dialog({
			close : function(event, ui) {
				if (event.originalEvent == undefined
						|| $(event.originalEvent.target).closest(
								".ui-dialog-titlebar-close").length == undefined) {
					CreacionSerieCtrl.callbacks.call();
				}
				// Se destruye el dialogo
				$(this).dialog('destroy');
			}
		});
	},

	// Datos de configuracion inicial
	config : {
		url : "/gestionAsegurados-web/serie/crearSerie/inicio",
		title : "Crear Serie",
		contenedor : {}
	},

	// Callback a invocar cuando se termine la invocacion de la consulta
	callbacks : {},

	setOnCloseCallback : function(_fnCallback) {
		this.callbacks = _fnCallback;
	},

	//Objeto con los atributos para los parámetros de entrada
	datosEntrada : {
		idPersona : '',
		idModulo : ''
	},

	//Objeto de salida
	datosSalida : {},

	setDatosEntrada : function(objDatosEntrada) {
		this.datoEntrada = objDatosEntrada;
	},

	getDatosSalida : function() {
		return this.datosSalida;
	},

	setDatosSalida : function(objDatosSalida) {
		this.datosSalida = objDatosSalida;
	},

	// Funcion para el control de cerrado de la pagina
	cerrar : function() {
		// Se cierra el dialogo
		this.dialogo.dialog('close');
	},

	iniciarCreacionSerie : function() {
		$('#' + this.config.contenedor)
				.html(
						'<iframe id="crearSerieFrame" src="'
								+ this.config.url
								+ '" width="100%" height="100%" frameBorder="0" onload="set_size(\'crearSerieFrame\')" />');
		this.dialogo.dialog('open');
	}
};