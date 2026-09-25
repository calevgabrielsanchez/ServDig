var IndividuoFisicoInvoker = {
		
		/*
		 * Funcion para inicializar la configuracion
		 */
		init : function(_contenedor){
			
			
			this.config.contenedor = _contenedor;
			
			/*
			 * Inicializamos el dialogo que contiene la pantalla 
			 * 
			 */
			var horizontalPadding = 15;
	        var verticalPadding = 15;
			/*
			 * Se crea una variable para el control del dialogo
			 * que sera a traves de un iFrame
			 */
	        var d = $('#' + _contenedor).html(
					'<iframe id="site" src="' + this.config.url
				 			+ '" width="100%" height="100%" frameborder="0" />');
//	        
//	        /*
//		     * Configuracion del dialogo
//		     */
//	        this.dialogo = d.dialog({
//	            title: this.config.title,
//	            autoOpen: false,
//	            width: 980,
//	            height: 900,
//	            modal: true,
//	            resizable: false,
//	            autoResize: true,
//	            overlay: {
//	                opacity: 0.5,
//	                background: "black"
//	            }
//	        }).width(900).height(900);
//	        
	        
	        /*
	         * Configuramos el metodo onClose del dialogo
	         * 
	         */
	        this.dialogo.dialog({
	        	beforeClose: function(event, ui) { 
	        		
	        		IndividuoFisicoInvoker.callbacks.call( IndividuoFisicoInvoker.fisica);
	        	}	
	        })
	        
			
		},

		/*
		 * 
		 */
		setOnCloseCallback : function(_fnCallback){
			this.callbacks = _fnCallback;
		},

		
		/*
		 * Datos de configuracion inicial
		 * del detalle de la persona Fisica.
		 */
		config : {
			url :  "/gestionIndividuo-web/individuo/detalleFisica",
			title:"Detalle Persona F\u00EDsica",
			contenedor : {}
		},
		
		/*
		 * Callback a invocar cuando se 
		 * termine la invocacion de la consulta
		 */
		callbacks : {},
		
		
		/*
		 * Objeto de fisica
		 */
		fisica : {}, 
		
		/**
		 * Para visualizar el componente
		 */
		visualizar : function(){
			//Para que cada vez que se abra el dialogo se cree de nuevo.
			this.init( this.config.contenedor);
			
			this.dialogo.dialog('open');
		},
		
		cerrar : function(){
			//Cerramos el dialogo
			this.dialogo.dialog('close');
			
			//Destruimos el dialogo
			this.dialogo.dialog('destroy');
			
		}
}