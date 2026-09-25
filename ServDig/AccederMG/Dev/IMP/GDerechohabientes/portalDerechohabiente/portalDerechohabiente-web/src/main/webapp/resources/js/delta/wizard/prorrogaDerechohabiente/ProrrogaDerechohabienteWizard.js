/*
 * JS de control del Wizard
 */

var WizardProrrogaDerechohabienteCtrl = {
		
		/*
		 * Funcion para inicializar la configuracion
		 */
		init : function(_contenedor, _idAsignacionNss, _nss, _tipoProrroga, _idIntegrante){
			

			this.config.contenedor = _contenedor;
			this.config.idAsignacionNss = _idAsignacionNss;
			this.config.nss = _nss;
			this.config.tipoProrroga = _tipoProrroga;
			this.config.idIntegrante = _idIntegrante;
	        
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
	        		WizardProrrogaDerechohabienteCtrl.limpiarElementosSesion();
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
			url :  "/${mvn.web.app.root}/wizard/prorroga/tramite/",
			title:"IMSS Digital",
			contenedor : {}, 
			idAsignacionNss : "",
			nss: "",
			tipoProrroga: "",
			idIntegrante: ""
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
				this.config.idAsignacionNss, this.config.nss, 
				this.config.tipoProrroga, this.config.idIntegrante);
			
			this.dialogo.dialog('open');
			
			var url = this.config.url + this.config.idAsignacionNss + "/"
			+ this.config.nss + "/"
			+ this.config.tipoProrroga + "/" + this.config.idIntegrante;
			
			$('#' + this.config.contenedor).html(
					'<iframe id="prorrogaDerechohabienteFrame" src="' + url
				 			+ '" width="100%" height="100%" '
				 			+ 'onload="set_size(\'prorrogaDerechohabienteFrame\', 900)" frameborder="0" />');
			
		}, 
	
		cerrar : function(){
			//Cerramos el dialogo
			this.dialogo.dialog('close');	
		},
		
		limpiarElementosSesion: function (){
			
			// Se limpia la sesión de la administración de domicilios
			$.postJSON('/${mvn.web.app.root}/wizard/prorroga/limpiar-session', null, function(data) {
				
			}).error(function(data){
				
			});	
		}
};