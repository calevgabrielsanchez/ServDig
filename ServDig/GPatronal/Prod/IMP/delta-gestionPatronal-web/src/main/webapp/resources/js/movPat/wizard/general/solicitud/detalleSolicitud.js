// JS de control para mostrar el detalle de una solicitud

var DetalleSolicitudCtrl = {
		
		/*
		 * Funcion para inicializar la configuracion
		 */
		init : function(_contenedor, _folioSolicitud){
			
			this.config.contenedor = _contenedor;
			this.config.folioSolicitud = _folioSolicitud;
	        
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
	            //height : 900,
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
	        		//se limpia el div, ya que si no se hace se vuelve a consultar la solicitud
	        		$('#' + DetalleSolicitudCtrl.config.contenedor).html("");
	        		DetalleSolicitudCtrl.limpiarElementosSesion();
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
			url :  "/gestionSolicitud-visor-web/portlet/solicitudes/detalle/",
			title:"Detalle de solicitud",
			contenedor : {}, 
			folioSolicitud:""
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
			this.init(this.config.contenedor, this.config.folioSolicitud);
			
			this.dialogo.dialog('open');
			
			var url = this.config.url + this.config.folioSolicitud;
			
			$('#' + this.config.contenedor).html(
					'<iframe id="detalleSolicitudFrame" src="' + url
				 			+ '" width="100%" height="100%" onload="set_size(\'detalleSolicitudFrame\')" frameBorder="0"/>');
		}, 
	
		cerrar : function(){
			//Cerramos el dialogo
			this.dialogo.dialog('close');
		},
		
		limpiarElementosSesion: function (){
			//$.post('/gestionSolicitud-visor-web/portlet/solicitudes/limpiar-sesion');
			
			$.ajax('/gestionSolicitud-visor-web/portlet/solicitudes/limpiar-sesion/', { 
			    type: "POST",
			    beforeSend: function() {
			    	try{
			    		$loaderDiv.dialog('close');
			    	}catch(e){}
			    },
			    error: function() {
			    },
			    success: function(data) {
			    }
			});
			
			
			
		}
};