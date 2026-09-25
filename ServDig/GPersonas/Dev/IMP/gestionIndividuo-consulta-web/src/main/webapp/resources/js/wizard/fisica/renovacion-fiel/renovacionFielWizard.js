/*
 * JS de control del Wizard de Actualizacion de Datos
 * de Persona.
 */

var WizardRenovacionFielCtrl = {
		
		/*
		 * Funcion para inicializar la configuracion
		 */
		init : function(_contenedor, _tipoPersona, _idPersona, _curpPersona, _rfcPersona){
			
			this.config.contenedor = _contenedor;
			
			this.config.tipoPersona= _tipoPersona;
			this.config.idPersona = _idPersona;
			this.config.curpPersona = _curpPersona;
			this.config.rfcPersona = _rfcPersona;

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
	            modal: true,
	            resizable: false,
	            overlay: {
	                opacity: 0.5,
	                background: "black"
	            },
				position : {
					my : "top",
					at : "top",
					of : window,
					offset : "0 10"
				}
	        });
	        
	        /*
	         * Configuramos el metodo onClose del dialogo
	         * 
	         */
	        this.dialogo.dialog({
	        	beforeClose: function(event, ui) { 
	        		WizardRenovacionFielCtrl.limpiarElementosSesion();
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
			url :  "/gestionIndividuo-consulta-web/wizard/tramite/renovacion/fiel/init",
			title:"IMSS Digital",
			contenedor : {}, 
			tipoPersona : "",  
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
			this.init(this.config.contenedor, this.config.tipoPersona,
				this.config.idPersona, this.config.curpPersona, 
				this.config.rfcPersona);
			
			this.dialogo.dialog('open');
			
			var url = this.config.url;
			/*
			+ this.config.tipoPersona + "/"
			+ this.config.idPersona + "/" + this.config.curpPersona + "/"
			+ this.config.rfcPersona;
			*/
			
			$('#' + this.config.contenedor).html(
					'<iframe id="renovacionFielFrame" src="' + url
				 			+ '" width="100%" height="100%" frameborder="0" ' 
				 			+ 'onload="set_size(\'renovacionFielFrame\')" />');
			
		}, 
	
		cerrar : function(){
			//Cerramos el dialogo
			this.dialogo.dialog('close');
			
			//Destruimos el dialogo
			this.dialogo.dialog('destroy');
			
		},
		
		acceso : function(_curp,_password){
			parent.AuthenticateSSO.authenticate(_curp,_password);
			parent.AuthenticateSSO.setOnCloseCallback(function(){
				$("#formlogin").submit();
			});
			
		},
		
		limpiarElementosSesion: function (){
			
			// Se limpia la sesión de la administración de domicilios
			$.postJSON('/gestionIndividuo-consulta-web/wizard/tramite/renovacion/fiel/limpiar-sesion', null, function(data) {
				
			}).error(function(data){
				
			});	
		}
};