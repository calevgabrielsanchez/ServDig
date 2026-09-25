var identificarCambiosAutomaticosPersonaMoralCtrl = {

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
	        }
	    });
	    
	    
	    // Configuramos el metodo onClose del dialogo
	    this.dialogo.dialog({
	    	beforeClose : function(event, ui) {
	    		identificarCambiosAutomaticosPersonaMoralCtrl.limpiarElementosSesion();
	    		
	    		if (identificarCambiosAutomaticosPersonaMoralCtrl.ejecutarCallback) {
	    			identificarCambiosAutomaticosPersonaMoralCtrl.callbacks.call();
	    		}
	    }});
	},
	
	// Metodo para invocar la ICA de persona física
	identificarCambios : function(){
		this.dialogo.dialog('open');
		this.setDatosSalida(null);
		$('#' + this.config.contenedor).html(
				'<iframe id="ICAMoralFrame" src="' + this.config.url
						+ '" width="100%" height="100%" frameborder="0" onload="set_size(\'ICAMoralFrame\')"/>');
	},
	
	// Datos de configuracion inicial de la ICA de persona fisica
	config : {
		url : "/gestionIndividuo-consulta-web/persona/moral/identificar/cambios-automaticos/ingresar-datos",
		
		title : "ICA Persona Moral",
		contenedor : {}
	},
	
	// Callback a invocar cuando se termine la invocacion de la consulta
	callbacks : {},

	setOnCloseCallback : function(_fnCallback){
		this.callbacks = _fnCallback;
	},
		
	//Objeto con los atributos para los parámetros de entrada
	datosEntrada : {
		indMostrarPantalla : '',
		idPersona : '',
		rfc: ''
	},
	
	//Objeto de salida
	datosSalida : {},
	
	setDatosEntrada : function(objDatosEntrada){
		this.datoEntrada = objDatosEntrada;
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
	 * Función para limpiar los elementos en sesión del ICA, se puso aquí
	 * para agregarla en el cerrar del dialogo y seguir permitiendo el
	 * setteo de la función de callback de cada gestión en el momento de
	 * cerrar.
	 */
	limpiarElementosSesion: function (){
		
		// Se limpia la sesión de la administración de domicilios
		$.postJSON('/gestionIndividuo-consulta-web/persona/moral/identificar/cambios-automaticos/limpiar-ica', null, function(data) {
			
		}).error(function(data){
			
		});	
	}
		
};