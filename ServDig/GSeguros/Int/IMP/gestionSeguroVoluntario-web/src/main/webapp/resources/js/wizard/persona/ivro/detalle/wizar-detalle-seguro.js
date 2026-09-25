/*
 * JS de control del Wizard de modalidad de la persona 
 * para seguro individual
 */

var WizardDetalleSeguroCtrl = {
		
		setIdSeguro : function(_idSeguro){
			this.config.idSeguro = _idSeguro;
		},
		
		setIdSeguroRenovacion : function(_title){
			this.config.title = _title;
		},
		
		/*
		 * Funcion para inicializar la configuracion
		 */
		init : function(_contenedor, _idSeguro){
			
			this.config.contenedor = _contenedor;
			this.config.idSeguro = _idSeguro;

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
		        closeOnEscape: false,
	            width : 950,
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
	        		$('iframe#detalleSeguroFrame').attr("src", "");
	        		WizardDetalleSeguroCtrl.limpiarElementosSesion();
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
		 * detalle seguro
		 */
		config : {
			url :  "/${mvn.web.app.root}/wizard/detalle/seguro/",
			title:"Detalle Seguro",	
			contenedor : {}, 
			idSeguro:""
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
			this.init(this.config.contenedor, this.config.idSeguro);
			
			this.dialogo.dialog('open');
			
			var url = this.config.url + this.config.idSeguro;
			$('#' + this.config.contenedor).html(
					'<iframe id="detalleSeguroFrame" src="' + url
				 			+ '" width="100%" height="100%" '
				 			+ 'onload="set_size(\'detalleSeguroFrame\')" frameBorder="0"/>');
			
			
		}, 
	
		cerrar : function(){
			WizardDetalleSeguroCtrl.setIdSeguro(null);
			//Cerramos el dialogo
			this.dialogo.dialog('close');
		},

		limpiarElementosSesion: function () {			
				
		}
};