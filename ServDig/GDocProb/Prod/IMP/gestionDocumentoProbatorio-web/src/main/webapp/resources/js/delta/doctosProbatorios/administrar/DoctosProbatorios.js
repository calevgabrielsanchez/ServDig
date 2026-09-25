var DocsProbatoriosCtrl = {

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
			width : '70%',
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
				DocsProbatoriosCtrl.callbacks
						.call(DocsProbatoriosCtrl.docsProbatorios);
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
		url : "/gestionDocumentoProbatorio-web/documentos/probatorios/administrar/init-agregar",
		urlModificar : "/gestionDocumentoProbatorio-web/documentos/probatorios/administrar/modificar/",
		title : "Registrar Documento Probatorio",
		contenedor : {}
	},

	/*
	 * Callback a invocar cuando se 
	 * termine la invocacion de la consulta
	 */
	callbacks : {},

	dialogo : {},

	// Objeto de medios de contacto 
	docsProbatorios : {},

	registrar : function() {
		//Para que cada vez que se abra el dialogo se cree de nuevo.
		this.init(this.config.contenedor);

		this.dialogo.dialog('open');
		$('#' + this.config.contenedor).html(
				'<iframe id="admonDocProbatorioFrame" src="' + this.config.url
						+ '" width="100%" height="100%" frameborder="0" onload="set_size(\'admonDocProbatorioFrame\')"/>');
	},

	modificar : function(index) {
		//Para que cada vez que se abra el dialogo se cree de nuevo.
		this.init(this.config.contenedor);

		this.dialogo.dialog('open');
		$('#' + this.config.contenedor).html(
				'<iframe id="admonDocProbatorioFrame" src="' + this.config.urlModificar + index
						+ '" width="100%" height="100%" frameborder="0" onload="set_size(\'admonDocProbatorioFrame\')"/>');
	},
	cerrar : function() {
		//Cerramos el dialogo
		this.dialogo.dialog('close');

		//Destruimos el dialogo
		this.dialogo.dialog('destroy');

	}
};