/*
 * JS de control del Wizard de Modificación de Domicilio Particular
 */

var WizardRegistrarDomicilioParticularCtrl = {
		
		/*
		 * Funcion para inicializar la configuracion
		 */
		init : function(_contenedor, _tipoPersona, _idPersona, _tipoTramite){
			
			this.config.contenedor = _contenedor;			
			this.config.tipoPersona= _tipoPersona;
			this.config.idPersona = _idPersona;
			this.config.contextPath = '/${mvn.web.app.root}';
			this.config.idOrigen = '${mvn.web.app.origin.id}';
			
			console.log("Valor de tipoTramite: " + _tipoTramite);
			if(_tipoTramite != undefined){
				this.config.tipoTramite = _tipoTramite;
			}
			else{
				this.config.tipoTramite = "2";//ACTUALIZACION_DATOS_GENERALES(2) que es el flujo original
			}
	        
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
	            minHeight : 400,
	            maxHeight : 900,
	            modal: true,
	            resizable: false,
	            autoResize : true,
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
	        		WizardRegistrarDomicilioParticularCtrl.limpiarElementosSesion();
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
			url :  "/wizard/tramite/registrar/domicilio/particular/",
			title:"IMSS Digital",
			contenedor : {}, 
			tipoPersona : "",  
			idPersona:"",
			tipoTramite:"",
			contextPath : "",
			idOrigen:""
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
			console.log("tipo tramite: " + this.config.tipoTramite);
			var tipoTramite = "";
			
			if( this.config.tipoTramite != undefined ){
				tipoTramite = this.config.tipoTramite;
			}
			
			this.init(this.config.contenedor, this.config.tipoPersona,
				this.config.idPersona, this.config.tipoTramite);
			
			this.dialogo.dialog('open');
			
			var url = this.config.contextPath  + this.config.url + this.config.tipoPersona + "/"
			+ this.config.idPersona + "/" + tipoTramite;
			
			$('#' + this.config.contenedor).html(
					'<iframe id="registrarDomPartFrame" src="' + url
				 			+ '" width="100%" height="100%" onload="set_size(\'registrarDomPartFrame\')" frameborder="0"/>');
			
		}, 
	
		cerrar : function(){
			//Cerramos el dialogo
			this.dialogo.dialog('close');
		},
		
		/*
		 * Función para limpiar los elementos en sesión, se puso aquí
		 * para agregarla en el cerrar del dialogo y seguir permitiendo el
		 * setteo de la función de callback de cada gestión en el momento de
		 * cerrar.
		 */
		limpiarElementosSesion: function (){
			
			// Se limpia la sesión de la administración de domicilios
			$.postJSON(this.config.contextPath  + '/wizard/tramite/registrar/domicilio/particular/limpiar-sesion', null, function(data) {
				
			}).error(function(data){
				
			});	
		}
};