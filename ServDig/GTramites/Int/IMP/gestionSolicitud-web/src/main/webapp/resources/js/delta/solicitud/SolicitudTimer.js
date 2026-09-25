
/*
 * JS  para el manejo del timer para solicitar el estado de la solicitud
 * para saber si la solicitud ya fue procesada.
 */


var SolicitudTimer = {
		
		
		/*
		 * Datos de la configuracion 
		 * inicial del timer
		 */
		config:{
			//Url del servicio a consultar
			url : '/gestionSolicitud-web/solicitud/consultar/estatus',
			urlFrame:'/gestionSolicitud-web/solicitud/espera',
			title :' Solicitud en proceso'
			
		}, 
		//Inicializacion del dialgo
		init : function( _dialogo ){
			
			
			/*
			 * Inicializamos el dialogo que contiene la pantalla 
			 * de la consulta de personas morales.
			 */
			var horizontalPadding = 15;
	        var verticalPadding = 15;
	        

		    var d = $('#' + _dialogo).html(
				'<iframe id="site" src="' + this.config.urlFrame
			 			+ '" width="100%" height="100%" frameBorder="0"/>');
		    /*
		     * Configuracion del dialogo
		     */
	        this.dialogo = d.dialog({
	        	//con la funcion de abajo quitamos el boton cerrar X
	        	open: function(event, ui) { 
	        			$(this).parent().children().children('.ui-dialog-titlebar-close').hide();
	        		  },
	            title: this.config.title,
	            autoOpen: false,
	            width: 500,
	            height: 500,
	            modal: true,
	            resizable: false,
	            autoResize: true,
	            overlay: {
	                opacity: 0.5,
	                background: "black"
	            }
	        }).width(500).height(500);
	        
	        
	        /*
	         * Configuramos el metodo onClose del dialogo
	         * 
	         */
	        this.dialogo.dialog({
	        	beforeClose: function(event, ui) { 
	        		SolicitudTimer.callbacks.call();
	        	}	
	        })
	        
	        
			
		},
		/*
		 * Dialogo a mostrar de espera
		 */
		dialogo : {},
		/*
		 * Funcion que iniciar el proceso de verificacion
		 * de chequeo de la solicitud
		 */
		isEnProceso : function( _folio){
			$.blockUI();
			this.folio = _folio;
			this.timer = $.timer( function(){ SolicitudTimer.fnCheckSolicitud(_folio); } , 10000 , true);
			this.timer.play();
		},
		
		fnCheckSolicitud : function(){
			$.getJSON( this.config.url , {'folio': this.folio} , function(){
				SolicitudTimer.timer.stop();
				// Debemos de quitar el dialogo de espera.
				//SolicitudTimer.dialogo.dialog('close');
				
				$.unblockUI();
				
				SolicitudTimer.callbacks.call();
				
			}).error(function(data){
				/*
				 * En caso de que la solicitud termine con error, debemos de
				 * deterner el Timer y ejectuar el callback
				 * del manejo del error.
				 */
				var objError = jQuery.parseJSON(data.responseText);
				var mensaje = objError.erroresNegocio;
				var idEstadoSolicitud = objError.idEstadoSolicitud;
				
				if( idEstadoSolicitud == 11){
					
					SolicitudTimer.timer.stop();
					
					$.unblockUI();
					
					alert(mensaje);
					
					SolicitudTimer.setOnSolicitudConError.call();
				}
				
			});
		},
		/*
		 * Callback a invocar cuando 
		 * la solicitud ya haya sido procesada
		 */
		callbacks : {},
		
		setOnSolicitudProcesada : function(_fnCallback){
			this.callbacks = _fnCallback;
		},
		
		setOnSolicitudConError : {},
		
		/*
		 * Folio de la solicitud que estamos verificando
		 */
		folio :{}, timer :{}
		
		
}