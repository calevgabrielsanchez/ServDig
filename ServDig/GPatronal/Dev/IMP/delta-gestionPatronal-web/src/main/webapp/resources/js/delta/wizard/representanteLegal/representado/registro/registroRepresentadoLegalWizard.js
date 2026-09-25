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
			this.config.representado = null;
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
		        closeOnEscape: false,
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
	        		WizardRegistroRepresentadoLegalCtrl.limpiarElementosSesion();
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
			url :  "/wizard/tramite/representado/registro/",
			title:"IMSS Digital",
			contenedor : {}, 
			idPersona:"",
			curpPersona:"",
			rfcPersona:"",
			representado: null,
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
			this.init(this.config.contenedor, this.config.idPersona, this.config.curpPersona, 
				this.config.rfcPersona);
						
			var url = this.config.contextPath + this.config.url + this.config.idPersona + "/"
			+ this.config.curpPersona + "/" + this.config.rfcPersona;
			
			$('#' + this.config.contenedor).html(
					'<iframe id="altaRepresentadoFrame" src="' + url
				 			+ '" width="100%" height="100%" '
				 			+ 'onload="set_size(\'altaRepresentadoFrame\')" frameBorder="0"/>');
			
			this.dialogo.dialog('open');
			
		}, 
	
		cerrar : function(){
			//Cerramos el dialogo
			this.dialogo.dialog('close');
			this.dialogo.dialog('destroy');
		},
		
		limpiarElementosSesion: function (){
			
			this.config.representado = null;
			// Se limpia la sesi�n de la administraci�n de domicilios
			$.postJSON(this.config.contextPath + '/wizard/tramite/representado/registro/limpiar-sesion', null, function(data) {
				
			}).error(function(data){
				
			});	
		},
		
		setRepresentado: function(representado) {
			this.config.representado = representado;
		} ,
		
		getRepresentado: function() {
			return this.config.representado;
		}
};