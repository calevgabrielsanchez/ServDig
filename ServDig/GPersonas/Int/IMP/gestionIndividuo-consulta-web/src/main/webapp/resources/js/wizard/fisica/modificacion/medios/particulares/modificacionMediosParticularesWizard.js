/*
 * JS de control del Wizard de Actualizacion de Datos
 * de Persona.
 */

var WizardModificacionMediosParticularesCtrl = {
		
		/*
		 * Funcion para inicializar la configuracion
		 */
		init : function(_contenedor, _tipoPersona, _idPersona){
			
			this.config.contenedor = _contenedor;
			
			this.config.tipoPersona= _tipoPersona;
			this.config.idPersona = _idPersona;
	        this.config.contextPath = '/${mvn.web.app.root}';
	        this.config.idOrigen = '${mvn.web.app.origin.id}';
	        
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
	            minHeight : 550,
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
	        		WizardModificacionMediosParticularesCtrl.limpiarElementosSesion();
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
			url :  "/wizard/tramite/modificar/medios/particulares/",
			title:"IMSS Digital",
			contenedor : {}, 
			tipoPersona : "",  
			idPersona:"",
			contextPath:"",
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
			this.init(this.config.contenedor, this.config.tipoPersona,
				this.config.idPersona);
			
			this.dialogo.dialog('open');
			
			var url = this.config.contextPath + this.config.url + this.config.tipoPersona + "/"
			+ this.config.idPersona ;
			
			$('#' + this.config.contenedor).html(
					'<iframe id="modificarMediosPartFrame" src="' + url
					+ '" width="100%" height="100%" '
		 			+ 'onload="set_size(\'modificarMediosPartFrame\')" frameborder="0" />');
			
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
			
			// Se limpia la sesión de la administración de medios
			// Se limpia la sesión de la administración de domicilios
			$.postJSON(this.config.contextPath +'/wizard/tramite/modificar/medios/particulares/limpiar-sesion', null, function(data) {
				
			}).error(function(data){
				
			});
			
		}
};