var WizardEscritoCtrl = {

	
	init : function(datos) {
		this.config.contenedor = datos.contenedor;
		this.config.rp = datos.rp;
		this.config.contextPath = '/${mvn.web.app.root}';
		this.config.idOrigen = '${mvn.web.app.origin.id}';
		
		/*
		 * Se crea una variable para el control del dialogo que sera a traves de
		 * un iFrame
		 */
		var d = null;
		
		if ($('#' + this.config.contenedor).length == 0) {
			d = $('#' + _contenedor, parent.document);
		} else {
			d = $('#' + this.config.contenedor);
		}

		/*
		 * Configuracion del dialogo
		 */
		this.dialogo = d.dialog({
			title : this.config.title,
			autoOpen : false,
			width : 1050,
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
			close : function(event, ui) {
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
		url : '/escrito/wizard',
		title : 'IMSS Digital',
		container : null,
		rfc : null,
		razonSocial: null,
		rp: null,
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
		this.dialogo.dialog('open');
		
		var url = this.config.contextPath + this.config.url + '/' + this.config.rp;
		
		$('#' + this.config.contenedor).html(
				'<iframe id="solicitarRttFrame" src="' + url
						+ '" width="100%" height="100%" frameborder="0"'
						+ 'onload="set_size(\'solicitarRttFrame\')" frameborder="0" />');

	},

	cerrar : function() {
		// Cerramos el dialogo
		this.dialogo.dialog('close');
	}
};