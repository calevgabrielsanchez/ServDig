/*
 * JS de control del Wizard
 */

var WizardRegistroDerechohabienteCtrl = {
		
		/*
		 * Funcion para inicializar la configuracion
		 */
		init : function(_contenedor, _idAsignacionNss, _nss, _parentesco){
			

			this.config.contenedor = _contenedor;
			this.config.idAsignacionNss = _idAsignacionNss;
			this.config.parentesco = _parentesco != undefined ? _parentesco : 0;
			this.config.nss = _nss;
			this.datosSalida.registroCorrecto= false;
			this.datosSalida.mostrarDocumentos= false;
			this.datosSalida.redireccionar = false;
			this.datosSalida.folioSolicitud= "";
	        
			/*
			console.debug("El nss al que se le agregara un integrante es: %s",_nss);
			console.debug("El idAsignacionNss es: %s",_idAsignacionNss );
			console.debug("El parentesco a registrar es: %s",this.config.parentesco);*/
			/*
			 * Se crea una variable para el control del dialogo
			 * que sera a traves de un iFrame
			 */
	        var d = $('#' + _contenedor);
	        
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
			url :  "/${mvn.web.app.root}/wizard/registro/tramite/",
			title:"IMSS Digital",
			contenedor : {}, 
			idAsignacionNss : "",
			parentesco: "",
			nss: ""
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
			this.init(this.config.contenedor, this.config.idAsignacionNss, this.config.nss, this.config.parentesco);
			
			this.dialogo.dialog('open');
			
			var url = this.config.url + this.config.idAsignacionNss +"/" + this.config.nss+  "/"
			+ this.config.parentesco ;
			
			$('#' + this.config.contenedor).html(
					'<iframe id="registroDerechohabienteFrame" src="' + url
				 			+ '" width="100%" height="100%" '
				 			+ 'onload="set_size(\'registroDerechohabienteFrame\', 900)" frameborder="0" />');
			
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
			
			// Se limpia la sesi�n de la administraci�n de domicilios
			$.postJSON('/${mvn.web.app.root}/wizard/registro/limpiar-session', null, function(data) {
				
			}).error(function(data){
				
			});	
		}
};