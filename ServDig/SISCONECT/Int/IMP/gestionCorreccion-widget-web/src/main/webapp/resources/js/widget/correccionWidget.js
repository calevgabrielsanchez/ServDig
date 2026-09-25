/*
 * JS de control del Wizard de Actualizacion de Datos
 * de Persona.
 */

var WizardRegistroRepresentadoLegalCtrl = {
		
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
	            dialogClass: "no-close",
		        closeOnEscape: false,
	            width : 900,
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
	        		WizardRegistroRepresentadoLegalCtrl.limpiarElementosSesion();
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
			url :  "",
			title:"Registro de empresa a representar",
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
			
			var url = this.config.url + this.config.idPersona + "/"
			+ this.config.curpPersona + "/"
			+ this.config.rfcPersona;
			
			$('#' + this.config.contenedor).html(
					'<iframe id="site" src="' + url
				 			+ '" width="100%" height="100%" />');
			
		}, 
	
		cerrar : function(){
			//Cerramos el dialogo
			this.dialogo.dialog('close');
			
			//Destruimos el dialogo
			this.dialogo.dialog('destroy');
			
		},
		
		limpiarElementosSesion: function (){
			
			// Se limpia la sesión de la administración de domicilios
			$.postJSON('/portal-web/wizard/tramite/representado/registro/limpiar-sesion', null, function(data) {
				
			}).error(function(data){
				
			});	
		}
};



var correccionWidget = {
	editar : function() {
		
		alert('33');
		
		WizardRegistroRepresentadoLegalCtrl.init('correccionWidget', '', '', '');
		WizardRegistroRepresentadoLegalCtrl.abrir();
	}
};

$("#abreCorre").live('click', function() {
	
	correccionWidget.editar();
});