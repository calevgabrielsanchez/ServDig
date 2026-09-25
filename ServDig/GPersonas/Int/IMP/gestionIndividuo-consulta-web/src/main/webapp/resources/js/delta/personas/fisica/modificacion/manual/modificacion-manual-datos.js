var ModificacionManualDatosFisicaCtrl = {

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
	    		ModificacionManualDatosFisicaCtrl.limpiarElementosSesion();
	    		ModificacionManualDatosFisicaCtrl.callbacks.call();
	    }});
	},
	
	// Metodo para invocar la MDM de persona física
	modificacionManual : function(){
		this.dialogo.dialog('open');
		this.setDatosSalida(null);
		$('#' + this.config.contenedor).html('<iframe id="mmdFrame" src="' + this.config.url + '" width="100%" height="100%" frameborder="0" onload="set_size(\'mmdFrame\')"/>');
	},
	
	// Datos de configuracion inicial de la ICA de persona fisica
	config : {
		url : "/gestionIndividuo-consulta-web/persona/fisica/modificacion-manual/initModManualFisica",
		title : "MDM Persona F&iacute;sica",
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
		
		/*
		 * Banderas para indicar qué datos se desean modificar dentro del grupo de
		 * DATOS RENAPO
		 */
		indCapturaNombre : '',
		indCapturaCURP : '',
		indCapturaSexo : '',
		indCapturaFechaNacimiento : '',
		indCapturaLugarNacimiento : '',
		indCapturaDocumentoProbatorio : '',

		/*
		 * Banderas para indicar qué datos se desean modificar dentro del grupo de
		 * DATOS SAT
		 */
		indCapturaRFC  : '',
		indCapturaDomicilioFiscal : '',
		indCapturaMediosContactoFiscales : '',
	
		/*
		 * Banderas para indicar qué datos se desean modificar dentro del grupo de
		 * DATOS COMPLEMENTARIOS
		 */
		indCapturaDomicilioParticular : '',
		indCapturaMediosContactoParticular : '',
	
		indAutorizacion : ''
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
	
	// Funcion para el control de cerrado de la pagina
	cerrar : function(){
		this.dialogo.dialog('close');
	},
	
	/*
	 * Función para limpiar los elementos en sesión de la modificación manual,
	 * se puso aquí para agregarla en el cerrar del dialogo y seguir permitiendo
	 * el setteo de la función de callback de cada gestión en el momento de
	 * cerrar.
	 */
	limpiarElementosSesion: function (){
		
		// Se limpia la sesión de la administración de domicilios
		$.postJSON('/gestionDomicilios-web/domicilio/administrar/particular/limpiar-domicilios', null, function(data) {
			
		}).error(function(data){
			fnProcesarErrores(data, "form#mdmPersonaFisicaForm");
		});
		
		// Se limpia la sesión de la administración de medios de contacto
		$.postJSON('/gestionMediosContacto-web/medios/particulares/administrar/limpiar-medios', null, function(data) {
			
		}).error(function(data){
			fnProcesarErrores(data, "form#mdmPersonaFisicaForm");
		});
		
		// Se limpia la sesión de la administración de documentos probatorios
		$.postJSON('/gestionDocumentoProbatorio-web/documentos/probatorios/administrar/limpiar-documentos-probatorios', null, function(data) {
			
		}).error(function(data){
			fnProcesarErrores(data, "form#mdmPersonaFisicaForm");
		});
		
		// Se limpia la sesión de la administración de medios de contacto fiscales
		$.postJSON('/gestionMediosContacto-web/medios/fiscales/administrar/limpiar-medios', null, function(data) {
			
		}).error(function(data){
			fnProcesarErrores(data, "form#mdmPersonaFisicaForm");
		});
	}	
};