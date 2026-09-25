var tramiteDRSCtrl = {

	dialogo : {},
	init : function(idContenedor){
		
	    this.config.contenedor = idContenedor;
	    
	    var d = $('#' + idContenedor);

	    // Configuracion del dialogo
	    this.dialogo = d.dialog({
	        title : this.config.title,
	        autoOpen : false,
	        autoResize : true,
	        width : 1000,
	        height : 800,
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
	    		
//	    		FirmaDigitalCtrl.limpiarElementosSesion();
//	    		FirmaDigitalCtrl.callbacks.call();
	    }});
	},
	
	config : {
//		url : "/gestionSolicitud-web/firma-digital/init",
		url : "/delta-gestionPatronal-web/cmp/afiliacion/ica/",
		title : "Cambio de raz\u00F3n social",
		contenedor : {}
	},
	
	
	//Objeto con los atributos para los parámetros de entrada
	datosEntrada : {
		idPersona : '',
		idTipoPersona : ''
	},
	
	datosSalida : {},
	
	setOnCloseCallback : function(_fnCallback){
		this.callbacks = _fnCallback;
	},
		
	
	setDatosEntrada : function(objDatosEntrada){
		this.datoEntrada = objDatosEntrada;
	},
	
	setDatosSalida : function(objDatosSalida){
		this.datosSalida = objDatosSalida;
	},
	
	// Metodo para invocar la MDM de persona física
	desplegar : function(){
		this.dialogo.dialog('open');
		this.setDatosSalida(null);
		$('#' + this.config.contenedor).html('<iframe id="site" src="' + this.config.url + this.datosEntrada.idPersona + '/' + this.datosEntrada.idTipoPersona + '" width="100%" height="100%" frameborder="0"/>');
	},
	
	cerrar : function(){
		this.dialogo.dialog('close');
	},
	
};


