var identificarCambiosAutomaticosPersonaFisicaCtrl = {

	dialogo : {},
	init : function(idContenedor){
		
	    this.config.contenedor = idContenedor;
	    
	    var d = $('#' + idContenedor);

	    // Configuracion del dialogo
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
            position: { my: "top", at: "top", of: window, offset: "0 10" }
	    });
	    	    
	    // Configuramos el metodo onClose del dialogo
	    this.dialogo.dialog({
	    	beforeClose : function(event, ui) {
	    		identificarCambiosAutomaticosPersonaFisicaCtrl.limpiarElementosSesion();
	    		if ( event.originalEvent && $(event.originalEvent.target).closest(".ui-dialog-titlebar-close").length ) {
	    			return;
	    		}else if (identificarCambiosAutomaticosPersonaFisicaCtrl.ejecutarCallback) {
	    			identificarCambiosAutomaticosPersonaFisicaCtrl.callbacks.call();
	    		}
	    }});
	},
	
	// Metodo para invocar la ICA de persona fisica
	identificarCambios : function(){
		this.dialogo.dialog('open');
		this.setDatosSalida(null);
		$('#' + this.config.contenedor).html(
				'<iframe id="ICAFisicaFrame" src="' + this.config.url
						+ '" width="100%" height="100%" frameborder="0" onload="set_size(\'ICAFisicaFrame\')" />');
	},
	
	// Datos de configuracion inicial de la ICA de persona fisica
	config : {
		url : "/gestionIndividuo-consulta-web/persona/fisica/identificar/cambios-automaticos/ingresar-datos",
		title : "ICA Persona F&iacute;sica",
		contenedor : {}
	},
	
	// Callback a invocar cuando se termine la invocacion de la consulta
	callbacks : {},

	setOnCloseCallback : function(_fnCallback){
		this.callbacks = _fnCallback;
	},
		
	//Objeto con los atributos para los parametros de entrada
	datosEntrada : {
		indMostrarPantalla : '',
		indUsuarioExterno : '',
		idPersona : '',
		curp: '',
		rfc: '',
		nombrePersona : '',
		primerApellido :'',
		segundoApellido : '',
		indBusquedaRENAPO : '',
		indBusquedaSAT : ''
	},
	
	//Objeto de salida
	datosSalida : {},
	
	setDatosEntrada : function(objDatosEntrada){
		this.datosEntrada = objDatosEntrada;
	},
	
	getDatosSalida : function (){
		return this.datosSalida;
	},
	
	setDatosSalida : function(objDatosSalida){
		this.datosSalida = objDatosSalida;
	},
	
	ejecutarCallback : {}, 
	
	// Funcion para el control de cerrado de la pagina
	cerrar : function(ejecutarCallback){
		this.ejecutarCallback = ejecutarCallback;
		this.dialogo.dialog('close');
	},
	

	/*
	 * Funcon para limpiar los elementos en seson del ICA, se puso aqui
	 * para agregarla en el cerrar del dialogo y seguir permitiendo el
	 * setteo de la funcion de callback de cada gestión en el momento de
	 * cerrar.
	 */
	limpiarElementosSesion: function (){
		
		// Se limpia la sesion
		$.postJSON('/gestionIndividuo-consulta-web/persona/fisica/identificar/cambios-automaticos/limpiar-ica', null, function(data) {
			
		}).error(function(data){
			
		});	
	}
		
};