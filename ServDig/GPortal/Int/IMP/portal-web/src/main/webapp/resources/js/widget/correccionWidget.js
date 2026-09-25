/*
 * JS de control del Wizard de Actualizacion de Datos
 * de Persona.
 * 
 * Widget para sistema de correccion
 */

var WizardCorreccionCtrl = {
		
		/*
		 * Funcion para inicializar la configuracion
		 */
		init : function(_contenedor, _idPersona, _curpPersona, _rfcPersona){
			
			
			this.config.contenedor = _contenedor;
			this.config.idPersona = _idPersona;
			this.config.curpPersona = _curpPersona;
			this.config.rfcPersona = _rfcPersona;
	        
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
	            closeOnEscape: false,
	            width : 1000,
	            height : 900,
	            modal: true,
	            resizable: false,
	            overlay: {
	                opacity: 0.5,
	                background: "black"
	            }
	        });
	        
	        /*
	         * Configuramos el metodo onClose del dialogo
	         * 
	         */
	        this.dialogo.dialog({
	        	beforeClose: function(event, ui) { 
	        		WizardCorreccionCtrl.limpiarElementosSesion();
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
			url :  "/correccion-web/iniciarPatron.do?patIDSEnpie=",
			title:"Correcci\u00f3n patronal",
			contenedor : {},
			idPersona:"",
			curpPersona:"",
			rfcPersona:""
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
			this.init(this.config.contenedor,
				this.config.idPersona, this.config.curpPersona, 
				this.config.rfcPersona);
			
			this.dialogo.dialog('open');
			
			var url = this.config.url + AtributosPersonaCtrl.personaPortal.registroPatronal;
			
			$('#' + this.config.contenedor).html(
					'<iframe id="site" src="' + url
				 			+ '" width="100%" height="100%" frameborder="0"/>');
			
		}, 
	
		cerrar : function(){
			//Cerramos el dialogo
			this.dialogo.dialog('close');
			
			//Destruimos el dialogo
			this.dialogo.dialog('destroy');   
			
		},
		
		limpiarElementosSesion: function (){
			
			// Se limpia la sesi�n de la administraci�n de domicilios
			$.postJSON('/portal-web/wizard/tramite/representado/registro/limpiar-sesion', null, function(data) {
				
			}).error(function(data){
				
			});	
		}
};



$("#abreCorre").live('click', function() {
	
	var regpat = AtributosPersonaCtrl.personaPortal.registroPatronal;
	
	
	WizardCorreccionCtrl.init('correccionWidget', regpat, '', '');
	WizardCorreccionCtrl.abrir();
});