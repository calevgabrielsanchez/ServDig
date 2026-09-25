/*
 * JS de control del Wizard de Modificacion de Clasificacion.
 */

var WizardAltaPatronalCtrl = {
		
	setRfc :  function(rfc){
		this.config.rfc = rfc;
	},
		
	/*
	 * Funcion para inicializar la configuracion
	 */
	init : function(_contenedor, _cveIdPersona, _cveTipoPersona) {
		this.config.contenedor = _contenedor;
		this.config.cveIdPersona = _cveIdPersona;
		this.config.cveTipoPersona = _cveTipoPersona;
		this.config.contextPath = '/delta-gestionPatronal-web';
		this.config.idOrigen = '2';
		/*
		 * Se crea una variable para el control del dialogo
		 * que sera a traves de un iFrame
		 */
		var d = $('#' + _contenedor);

		/*
		 * Configuracion del dialogo
		 */
		this.dialogo = d.dialog({
			title : this.config.title,
			autoOpen : false,
			width : 950,
			height : 725,
			modal : true,
			resizable : false,
			overlay : {
				opacity : 0.5,
				background : "black"
			},
            position: { my: "top", at: "top", of: window, offset: "0 10" }
		});

		/*
		 * Configuramos el metodo onClose del dialogo
		 * 
		 */
		this.dialogo.dialog({
			beforeClose : function(event, ui) {
				WizardAltaPatronalCtrl.limpiarElementosSesion();
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
	 * Datos de configuracion inicial
	 * de la consulta de la persona moral.
	 */
	config : {
		url : "/wizard/tramite/registro/patronal/",
		title : "Alta patronal",
		contenedor : {},
		cveIdPersona : "",
		cveTipoPersona : "",
		contextPath : "",
		idOrigen:"",
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
		this.init(this.config.contenedor, this.config.cveIdPersona, this.config.cveTipoPersona);
		
		this.dialogo.dialog('open');

		var url = this.config.contextPath  + this.config.url + this.config.cveIdPersona + "/" + this.config.cveTipoPersona;
		$('#' + this.config.contenedor).html('<iframe id="site" src="' + url + '" width="100%" height="100%" frameborder="0"/>');
	},

	cerrar : function() {
		//Cerramos el dialogo
		this.dialogo.dialog('close');
		this.dialogo.dialog('destroy');
	},

	/*
	 * Funci�n para limpiar los elementos en sesi�n, se puso aqu�
	 * para agregarla en el cerrar del dialogo y seguir permitiendo el
	 * setteo de la funci�n de callback de cada gesti�n en el momento de
	 * cerrar.
	 */
	limpiarElementosSesion : function() {

		// Se limpia la sesi�n de la Modificacion de Clasificacion
		$.postJSON(this.config.contextPath + '/wizard/tramite/registro/patronal/limpiar-sesion', null, function(data) {
			
		}).error(function(data){
			
		});	
	}
};