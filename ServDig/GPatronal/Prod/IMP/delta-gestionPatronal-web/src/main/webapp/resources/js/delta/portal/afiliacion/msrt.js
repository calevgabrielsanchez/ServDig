var ModSRTCtrl = {

	dialogo : {},
	init : function(idContenedor){
		
	    this.config.contenedor = idContenedor;
	    this.config.contextPath = '/${mvn.web.app.root}';
		this.config.idOrigen = '${mvn.web.app.origin.id}';
		
	    var d = $('#' + idContenedor);

	    // Configuracion del dialogo
	    this.dialogo = d.dialog({
	        title : this.config.title,
	        autoOpen : false,
	        autoResize : true,
	        width : 1200,
	        height : 1000,
	        modal : true,
	        resizable : false,
	        autoResize : true,
	        overlay : {
	            opacity : 0.5,
	            background : "black"
	        }
	    });
	    
	    
	    // Configuramos el metodo onClose del dialogo
//	    this.dialogo.dialog({
//	    	beforeClose : function(event, ui) { 
	    		
//	    		FirmaDigitalCtrl.limpiarElementosSesion();
//	    		FirmaDigitalCtrl.callbacks.call();
//	    }});
	},
	
	config : {
		url : '/${mvn.web.app.root}' + '/cmp/clasificacion/msrt/',
		title : "Modificaci\u00F3n de Seguro de Riesgo de Trabajo",
		contenedor : {},
		contextPath : "",
		idOrigen:""
	},
	
	
	//Objeto con los atributos para los parámetros de entrada
	datosEntrada : {
		numeroRegistroPatronal : ''
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
		$('#' + this.config.contenedor).html('<iframe id="site" src="' + this.config.url + this.datosEntrada.numeroRegistroPatronal + '" width="100%" height="100%" frameborder="0"/>');
	},
	
	cerrar : function(){
		this.dialogo.dialog('close');
	},
	
};


