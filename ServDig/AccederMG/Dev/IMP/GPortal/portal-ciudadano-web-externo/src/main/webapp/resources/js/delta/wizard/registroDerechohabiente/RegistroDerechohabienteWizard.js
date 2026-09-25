/*
 * JS de control del Wizard
 */

var WizardRegistroDerechohabienteCtrl = {
		
		/*
		 * Funcion para inicializar la configuracion
		 */
		init : function(_opciones){
			

			this.config.opciones = _opciones;
			this.config.contenedor =  _opciones.contenedor;
			
			//alert(contenedor);
	        
			/*
			console.debug("El nss al que se le agregara un integrante es: %s",_nss);
			console.debug("El idAsignacionNss es: %s",_idAsignacionNss );
			console.debug("El parentesco a registrar es: %s",this.config.parentesco);*/
			/*
			 * Se crea una variable para el control del dialogo
			 * que sera a traves de un iFrame
			 */
	        var d = $('#' + this.config.contenedor);
	        
	        /*
		     * Configuracion del dialogo
		     */
	        this.dialogo = d.dialog({
	            title: this.config.title,
	            closeOnEscape: false,
	            autoOpen: false,
	            width : 900,
	            modal: true,
	            resizable: false,
	            overlay: {
	                opacity: 0.5,
	                background: "black"
	            },
	            position: { my: "top", at: "top", of: window, offset: "0 10" }
	        });
	        
	        /*
	         * Configuramos el metodo onClose del dialogo
	         * 
	         */
	        this.dialogo.dialog({
	        	beforeClose: function(event, ui) { 
	        		
	        	}, 
	        	close: function (event, ui) {
	        		WizardRegistroDerechohabienteCtrl.limpiarElementosSesion();
	        		if(jQuery.isFunction(WizardRegistroDerechohabienteCtrl.callbacks)) {
	        			WizardRegistroDerechohabienteCtrl.callbacks.call();
	        		}
	        	}
	        });
		},
		/*
		 * 
		 */
		setOnCloseCallback : function(_fnCallback){
			this.callbacks = _fnCallback;
		},
		/*
		 * Datos de configuracion inicial
		 * de la consulta de la persona moral.
		 */
		config : {
			urlRegistroBeneficiarios :  "/portal-ciudadano-web-externo/wizard/registro/beneficiario",
			urlRegistroAsegurado: "/portal-ciudadano-web-externo/wizard/registro/asegurado",
			title:"IMSS Digital",
			contenedor : {},
			opciones : {}
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
		abrir : function(){
			//Para que cada vez que se abra el dialogo se cree de nuevo.
			this.init(this.config.opciones);
			//
			this.dialogo.dialog('open');
			
			var idAsignacionNSS = 0;
			var registroAsegurado = false;
			
			if(this.config.opciones.idAsignacion != null && this.config.opciones.idAsignacion != undefined && this.config.opciones.idAsignacion != '') {
				
				idAsignacionNSS = this.config.opciones.idAsignacion; 
			}
			
			if(this.config.opciones.registroAsegurado != null && this.config.opciones.registroAsegurado != undefined) {
				registroAsegurado = this.config.opciones.registroAsegurado;
			}
			
			//vemos cual es la url que se tiene que mandar
			var url = registroAsegurado ? this.config.urlRegistroAsegurado : this.config.urlRegistroBeneficiarios;
			url += "/" + idAsignacionNSS;
			
			$('#' + this.config.contenedor).html(
					'<iframe id="registroDerechohabienteFrame" src="' + url
				 			+ '" width="100%" height="100%" '
				 			+ 'onload="set_size(\'registroDerechohabienteFrame\', 900)" frameborder="0" />');
			
		}, 
		mostrarMensajeError : function(mensaje) {
			
			this.dialogo.dialog("option", "buttons", [ {
				text : 'ACEPTAR',
				click : function() {
					WizardRegistroDerechohabienteCtrl.cerrar();
				}
			}]);
			
			var mensajeE = "<div class='alert alert-danger'>"+mensaje+"</div>";
			$('#' + this.config.contenedor).html(mensajeE);
		},
		datosSalida : {
			registroCorrecto: false,
			mostrarDocumentos: false,
			redireccionar : false,
			folioSolicitud: ""
		},
		setDatosSalida : function(_datosSalida) {
			this.datosSalida = _datosSalida;
		},
		getDatosSalida : function() {
			return this.datosSalida;
		},
		cerrar : function(){
			//Cerramos el dialogo
			this.dialogo.dialog('close');	
		},
		
		limpiarElementosSesion: function (){
			
			// Se limpia la sesión de la administración de domicilios
			$.blockUI();
			$.postJSON('/portal-ciudadano-web-externo/wizard/registro/limpiar-session', null, function(data) {
				
			}).error(function(data){
				
			}).always(function(){
				$.unblockUI();
			});	
		}
};