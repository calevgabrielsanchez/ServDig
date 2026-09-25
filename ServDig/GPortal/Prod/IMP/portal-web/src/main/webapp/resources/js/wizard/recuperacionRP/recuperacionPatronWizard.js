/*
 * JS de control del Wizard
 */

var WizardRecuperacionPatronCtrl = {
		
		/*
		 * Funcion para inicializar la configuracion
		 */
		init : function(_contenedor, _idPersona, _tipoPersona, _rfcPersona){
			
			this.config.contenedor = _contenedor;
			this.config.idPersona = _idPersona;
			this.config.tipoPersona = _tipoPersona;
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
	        		WizardRecuperacionPatronCtrl.limpiarElementosSesion();
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
			url :  "/portal-web/wizard/tramite/recuperacion/patron/",
			title:"IMSS Digital",
			contenedor : {}, 
			idPersona:"",
			tipoPersona:"",
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
				this.config.idPersona, this.config.tipoPersona, 
				this.config.rfcPersona);
			
			this.dialogo.dialog('open');
			
			var url = this.config.url
			+ this.config.idPersona + "/"
			+ this.config.tipoPersona + "/"
			+ this.config.rfcPersona;
			
			$('#' + this.config.contenedor).html(
					'<iframe id="recuperarPatronFrame" src="' + url
				 			+ '" width="100%" height="100%" '
				 			+ 'onload="set_size(\'recuperarPatronFrame\')" frameborder="0" />');
			
		}, 
	
		cerrar : function(){
			//Cerramos el dialogo
			this.dialogo.dialog('close');
			this.dialogo.dialog('destroy');
		},
		
		limpiarElementosSesion: function (){
			
			// Se limpia la sesión de la administración de domicilios
			$.postJSON('/portal-web/wizard/tramite/recuperacion/patron/limpiar-sesion', null, function(data) {
				
			}).error(function(data){
				
			});	
		}
};