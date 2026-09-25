/*
 * JS de soporte para la inclusion y llamado
 * de los componentes de domicilio, para ubicar y lozaclizar 
 * un domicilio nacional.
 */




/**
 * Objeto de control de domicilio, a traves de este objeto de JS
 * se iniciliaza, configura , invoca y regresa un objeto 
 * domicilio, el cual fue localizado.
 */
var DomicilioCtrl = {
		
		/*
		 * Funcion para inicializar la configuracion
		 * del domicilio.
		 */
		init : function(_opciones){
			
			this.config.contenedor = _opciones.contenedor;
			this.config.opciones = _opciones;
			this.config.contextPath = '/${mvn.web.app.root}';
			this.config.idOrigen = '${mvn.web.app.origin.id}';
			
			/*
			 * Inicializamos el dialogo que contiene la pantalla 
			 * de la consulta de personas morales.
			 */
			var horizontalPadding = 15;
	        var verticalPadding = 15;
	        
	        
			/*
			 * Se crea una variable para el control del dialogo
			 * que sera a traves de un iFrame
			 */
	        var d = $('#' + this.config.contenedor);
	        
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
	        		
	        		DomicilioCtrl.callbacks.call( DomicilioCtrl.domicilio);
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
		 * de la consulta de la persona moral.
		 */
		config : {
			url :  '/${mvn.web.app.root}'+"/domicilio/nacional/ubicar/delegacion",
			title:"Localizar domicilio nacional",
			contextPath : "",
			idOrigen:"",
			opciones: {},
			contenedor : {}
		},
		/*
		 * Callback a invocar cuando se 
		 * termine la invocacion de la consulta
		 */
		callbacks : {},
		
		dialogo : {},
		/*
		 * Objeto domicilio 
		 */
		domicilio : {}, 
		/**
		 * 
		 */
		localizar : function(){
			
			this.init( this.config.opciones);
			
			this.dialogo.dialog('open');
			
			var opciones = this.config.opciones;
			var idDelegacion = opciones.idDelegacion != undefined ? opciones.idDelegacion : 0;
			var umfOrigen = opciones.origen != undefined ? opciones.origen : 0;
			var umfDestino = opciones.destino != undefined ? opciones.destino : 0;

			$('#' + this.config.contenedor).html(
					'<iframe id="site" src="' + this.config.url + "?idDelegacion=" + idDelegacion + "&idUmfOrigen="+umfOrigen+"&idUmfDestino="+umfDestino
				 			+ '" width="100%" height="100%" frameborder="0"/>');
			
		}, 
	
		cerrar : function(){
			//Cerramos el dialogo
			this.dialogo.dialog('close');
			
			//Destruimos el dialogo
			this.dialogo.dialog('destroy');
			
		}
		
}