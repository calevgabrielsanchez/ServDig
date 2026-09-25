/*
 * JS de control del Wizard de Registro de Obra.
 */

var WizardRegistroObraCtrl = {
	/*
	 * Funcion para inicializar la configuracion
	 */
	init : function(_contenedor, _rfc, _nrp, _cveIdPersona, _cveTipoPersona) {

		this.config.contenedor = _contenedor;
		this.config.rfc = _rfc;
		this.config.nrp = _nrp;
		this.config.cveIdPersona = _cveIdPersona;
		this.config.cveTipoPersona = _cveTipoPersona;

		/*
		 * Se crea una variable para el control del dialogo que sera a traves de
		 * un iFrame
		 */
		var d = $('#' + _contenedor);

		/*
		 * Configuracion del dialogo
		 */
		this.dialogo = d.dialog({
			title : this.config.title,
			autoOpen : false,
			width : 900,
			minHeight : 650,
			maxHeight : 900,
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
				
			},
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
		url : "/sdroc_web/escritorio",
		title : "IMSS Digital",
		contenedor : {},
		nrp : "",
		rfc : ""
	},
	/*
	 * Callback a invocar cuando se 
	 * termine la invocacion de la consulta
	 */
	callbacks : {},

	dialogo : {},
	/**
	 * 
	 */
	abrir : function() {
		// Para que cada vez que se abra el dialogo se cree de nuevo.
		this.init(this.config.contenedor, this.config.rfc, this.config.nrp, this.config.cveIdPersona, this.config.cveTipoPersona);
		
		this.dialogo.dialog('open');
		var url = this.config.url + "?" + "rfc=" + this.config.rfc + "&nrp=" + this.config.nrp + "&cveIdPersona=" + this.config.cveIdPersona + "&cveTipoPersona=" + this.config.cveTipoPersona;

		$('#' + this.config.contenedor).html(
						'<iframe id="registroObraFrame" src="' + url
								+ '" width="100%" height="600px" frameborder="0"/>');
	},
	cerrar : function() {
		// Cerramos el dialogo
		this.dialogo.dialog('close');
	}
};
