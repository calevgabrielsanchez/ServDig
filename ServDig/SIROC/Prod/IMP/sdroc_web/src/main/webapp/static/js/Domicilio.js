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
		init : function(_contenedor){
			this.config.contenedor = _contenedor;			
			this.config.contextPath = '/gestionDomicilios-web';
			this.config.idOrigen = '2';
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
	            width: 950,
	            height:500,
	            modal: true,
	            resizable: false,
	            autoResize: true,
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
	        		if ( event.originalEvent && $(event.originalEvent.target).closest(".ui-dialog-titlebar-close").length ) {
				    	//DO NOTHING
				    }else{
	        			DomicilioCtrl.callbacks.call( DomicilioCtrl.domicilio);
				    }
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
//			url : 'http://imss.gob.mx:90'+ '/gestionDomicilios-web' + "/domicilio/nacional/ubicar/",
			url : '/gestionDomicilios-web' + "/domicilio/nacional/ubicar/",			
			//url : 'http://11.5.41.229' + '/gestionDomicilios-web' + "/domicilio/nacional/ubicar/",
			title:"Localizar domicilio nacional",
			contenedor : {},			
			contextPath : "",
			idOrigen:""
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
			//Para que cada vez que se abra el dialogo se cree de nuevo.
			this.init( this.config.contenedor);
			
			this.dialogo.dialog('open');
			var cadUrl = this.config.url;			
			$('#' + this.config.contenedor).html(
                    '<iframe id="domicilioFrame" src="' + this.config.url
                                + '" width="100%" height="100%" frameborder="0" onload="set_size(\'domicilioFrame\', 900)"/>');
			
			
		}, 
	
		cerrar : function(){
			//Cerramos el dialogo
			this.dialogo.dialog('close');
		},
		
		tipoDomicilio : {},
		
		setTipoDomicilio : function (_tipoDomicilio){
			this.tipoDomicilio = _tipoDomicilio;
		},
		
		getTipoDomicilio : function (){
			return this.tipoDomicilio;
		}
};