var IndividuoMoralInvoker = {
		
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
	        
	        /*
		     * Configuracion del dialogo
		     */
	        this.dialogo = d.dialog({
	            title: this.config.title,
	            autoOpen: false,
	            width: 980,
	            height: 900,
	            modal: true,
	            resizable: false,
	            autoResize: true,
	            overlay: {
	                opacity: 0.5,
	                background: "black"
	            }
	        }).width(900).height(900);
	        
	        
	        /*
	         * Configuramos el metodo onClose del dialogo
	         * 
	         */
	        this.dialogo.dialog({
	        	beforeClose: function(event, ui) { 
	        		
	        		IndividuoMoralInvoker.callbacks.call( IndividuoMoralInvoker.moral);
	        	}	
	        })
	        
			
		},
		
		
		/*
		 * Datos de configuracion inicial
		 * del detalle de la persona Moral.
		 */
		config : {
			url :  "/gestionIndividuo-web/individuo/detalleMoral",
			title:"Detalle Persona Moral",
			contenedor : {}
		},
		
		/*
		 * Objeto de moral
		 */
		moral : {}, 
		
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