/*
 * JS de control del Wizard de Actualizacion de Datos
 * de Persona.
 */

var WizardModificacionMediosFiscalesCtrl = {
		
		/*
		 * Funcion para inicializar la configuracion
		 */
		init : function(_contenedor, _tipoPersona, _idPersona){
			
			this.config.contenedor = _contenedor;
			
			this.config.tipoPersona= _tipoPersona;
			this.config.idPersona = _idPersona;

	        this.config.contenedor = _contenedor;
	        
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
	            autoOpen: false,
	            width : 900,
	            height : 900,
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
	        		WizardModificacionMediosFiscalesCtrl.limpiarElementosSesion();
	        	},
	        	close: function (event, ui) {
	        		$(this).dialog('destroy').empty();
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
			url :  "/gestionIndividuo-consulta-web/wizard/tramite/modificar/medios/fiscales/",
			title:"Modificaci&oacute;n Medios de Contacto Fiscales",
			contenedor : {}, 
			tipoPersona : "",  
			idPersona:""
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
			this.init(this.config.contenedor, this.config.tipoPersona,
				this.config.idPersona);
			
			this.dialogo.dialog('open');
			
			var url = this.config.url + this.config.tipoPersona + "/"
			+ this.config.idPersona;
			
			$('#' + this.config.contenedor).html(
					'<iframe id="site" src="' + url
				 			+ '" width="100%" height="100%" frameborder="0" />');
			
		}, 
	
		cerrar : function(){
			//Cerramos el dialogo
			this.dialogo.dialog('close');
		},

		limpiarElementosSesion: function (){
			
			// Se limpia la sesión de la administración de domicilios
			$.postJSON('/gestionIndividuo-consulta-web/wizard/tramite/modificar/medios/fiscales/limpiar-sesion', null, function(data) {
				
			}).error(function(data){
				
			});	
		}
};