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
				 			+ '" width="100%" height="100%" frameborder="0"/>');
			
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
		 * Objeto de fisica
		 */
		fisica : {}, 
		
		/**
		 * Para visualizar el componente
		 */
		visualizar : function(){
			//Para que cada vez que se abra el dialogo se cree de nuevo.
			this.init( this.config.contenedor);
		},
		
		cerrar : function(){
			
		}
}