var DetalleNotificacionCtrl = {

	dialogo : {},
	init : function(idContenedor){
		
	    this.config.contenedor = idContenedor;
	    
	    var d = $('#' + idContenedor);

	    // Configuracion del dialogo
	    this.dialogo = d.dialog({
	        title : this.config.title,
	        autoOpen : false,
	        width : 900,
	        height : 600,
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
	    		DetalleNotificacionCtrl.callbacks.call();
	    	},
	    	close : function(event, ui) {
	    		// Se destruye el dialogo
	    		$(this).dialog('destroy').remove();
	    	}
	    });
	},
	
	// Metodo para mostrar el detalle de las notificaiones de una persona física
	mostrarDetalleNotificaciones : function(){
		this.dialogo.dialog('open');
		$('#' + this.config.contenedor).html('<iframe id="notificacionesIframe" src="' + this.config.url + this.datosEntrada.idPersona + '/' + this.datosEntrada.idModulo + '" width="100%" height="100%" frameborder="0" />');
	},
	
	// Metodo para mostrar el detalle de las notificaiones de una persona moral
	mostrarDetalleNotificacionesMoral : function(){
		this.dialogo.dialog('open');
		$('#' + this.config.contenedor).html('<iframe id="notificacionesMoralIframe" src="' + this.config.urlMoral + this.datosEntrada.idPersona + '/' + this.datosEntrada.idModulo + '" width="100%" height="100%" frameborder="0" />');
	},
	
	// Datos de configuracion inicial
	config : {
		url : "/gestionIndividuo-consulta-web/notificaciones/persona/obtener-detalle/",
		urlMoral : "/gestionIndividuo-consulta-web/notificaciones/persona-moral/obtener-detalle/",
		title : "Detalle Notificaciones",
		contenedor : {}
	},
	
	// Callback a invocar cuando se termine la invocacion de la consulta
	callbacks : {},

	setOnCloseCallback : function(_fnCallback){
		this.callbacks = _fnCallback;
	},
		
	//Objeto con los atributos para los parámetros de entrada
	datosEntrada : {
		idPersona : '',
		idModulo : ''
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
	
	// Funcion para el control de cerrado de la pagina
	cerrar : function(){
		// Se cierra el dialogo
		this.dialogo.dialog('close');
	}
};