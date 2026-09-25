var ModificacionManualDatosMoralCtrl = {

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
	    		ModificacionManualDatosMoralCtrl.limpiarElementosSesion();
	    		ModificacionManualDatosMoralCtrl.callbacks.call();
	    }});
	},
	
	// Metodo para invocar la MDM de persona física
	modificacionManual : function(){
		this.dialogo.dialog('open');
		this.setDatosSalida(null);
		$('#' + this.config.contenedor).html('<iframe id="mmdMoralFrame" src="' + this.config.url + '" width="100%" height="100%" frameborder="0" onload="set_size(\'mmdMoralFrame\')"/>');
	},
	
	// Datos de configuracion inicial de la ICA de persona fisica
	config : {
		url : "/gestionIndividuo-consulta-web/persona/moral/modificacion-manual/initModManualMoral",
		title : "MDM Persona Moral",
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
		 * DATOS SAT
		 */
		indCapturaRFC  : '',
		indCapturaDomicilioFiscal : '',
		indCapturaMediosContactoFiscales : '',
		indCapturaRazonSocial : '',
		indCapturaFechaConstitucion : '',
		indCapturaTipoSociedad : '',
		
		/*
		 * Banderas para indicar qué datos se desean modificar dentro del grupo de
		 * DATOS COMPLEMENTARIOS
		 * 
		 * Las banderas de acta constitutiva y registro sindical son mutuamente excluyentes
		 */
		indCapturaActaConstitutiva : '',
		indCapturaRegistroSindicato : '',
		
		indAutorizacion : ''
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
		this.dialogo.dialog('close');
	},
	
	/*
	 * Función para limpiar los elementos en sesión de la modificación manual,
	 * se puso aquí para agregarla en el cerrar del dialogo y seguir permitiendo
	 * el setteo de la función de callback de cada gestión en el momento de
	 * cerrar.
	 */
	limpiarElementosSesion : function (){
		
		// Se limpia la sesión de la administración de medios de contacto fiscales
		$.postJSON('/gestionMediosContacto-web/medios/fiscales/administrar/limpiar-medios', null, function(data) {
			
		}).error(function(data){
			fnProcesarErrores(data, "form#mdmPersonaFisicaForm");
		});
	}
};