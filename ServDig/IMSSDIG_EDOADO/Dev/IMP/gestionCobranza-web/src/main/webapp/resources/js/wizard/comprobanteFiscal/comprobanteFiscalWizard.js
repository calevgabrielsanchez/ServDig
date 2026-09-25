/*
 * JS de control del Wizard de Obtencion de Comprobante Fiscal.
 */

var WizardComprobanteFiscalCtrl = {
	/*
	 * Funcion para inicializar la configuracion
	 */
	init : function(_contenedor, _nrp, _rfc) {

		this.config.contenedor = _contenedor;
		this.config.nrp = _nrp;
		this.config.rfc = _rfc;

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
			minHeight : 400,
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
				WizardComprobanteFiscalCtrl.limpiarElementosSesion();
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
		url : "/gestionCobranza-web/wizard/comprobanteFiscal/",
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
		this.init(this.config.contenedor, this.config.nrp, this.config.rfc);

		this.dialogo.dialog('open');
		var url = this.config.url + this.config.nrp + "/" + this.config.rfc;

		$('#' + this.config.contenedor).html(
						'<iframe id="obtenerComprFiscalFrame" src="' + url
								+ '" width="100%" height="100%" onload="set_size(\'obtenerComprFiscalFrame\')" frameborder="0"/>');
	},
	cerrar : function() {
		// Cerramos el dialogo
		this.dialogo.dialog('close');
	},

	/*
	 * Función para limpiar los elementos en sesión, se puso aquí para agregarla
	 * en el cerrar del dialogo y seguir permitiendo el setteo de la función de
	 * callback de cada gestión en el momento de cerrar.
	 */
	limpiarElementosSesion : function() {
		// Se limpia la sesión de la Modificacion de Clasificacion
		$.postJSON('/gestionCobranza-web/edoadeudo/obtener/comprobanteFiscal/limpiar-sesion', null, function(data) {
			
		}).error(function(data){
			
		});	
	}
};
