var BusquedaPersonaIntegranteCtrl = {
		dialogo:{},
		init:function(idContenedor){
			
			/*
			 * Inicializamos el dialogo que contiene la pantalla 
			 * de la consulta de personas morales.
			 */
			var horizontalPadding = 15;
	        var verticalPadding = 15;
	        
	        this.config.contenedor = idContenedor;
	        this.personaEncontrada = false;
	        this.persona = {};
	        this.personaBuscada = {};
	        
		    var d = $('#' + idContenedor);
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
	        		
	        		/*
	        		 * Se modifico para que verifique si se encontro a una persona
	        		 * o no.
	        		 */
	        		if(!BusquedaPersonaIntegranteCtrl.personaEncontrada){
	        			BusquedaPersonaIntegranteCtrl.callbacks.call(BusquedaPersonaIntegranteCtrl.personaBuscada);
	        		}else{
	        			BusquedaPersonaIntegranteCtrl.callbacks.call(BusquedaPersonaIntegranteCtrl.getPersona());
	        		}
	        	}	
	        })
	        
	        
		},
		/*
		 * Metodo para invocar la consulta de personas 
		 * morales.
		 */
		buscar:function(curp){
			
			
			this.dialogo.dialog('open');
			//console.debug("La curp que se manda es %s",curp);
			 $('#' + this.config.contenedor).html(
						'<iframe id="site" src="' + this.config.url + "/" + curp
					 			+ '" width="100%" height="100%" frameborder="0"/>');
			
			
		},
		
		/*
		 * Datos de configuracion inicial
		 * de la consulta de la persona moral.
		 */
		config : {
			url :  "/${mvn.web.app.root}/derechohabiente/buscarPersona/init",
			title:"Buqueda de persona",
			contenedor: {}
		},
		
		/*
		 * Callback a invocar cuando se 
		 * termine la invocacion de la consulta
		 */
		callbacks : {},
		/*
		 * 
		 */
		setOnCloseCallback : function(_fnCallback){
			this.callbacks = _fnCallback;
		},
		
		setPersona : function( objPersona){
			this.persona = objPersona;
			this.personaEncontrada = true;
		},
		
		getPersona : function(){
			return this.persona;
		},
		
		persona : {},
		personaEncontrada : false,
		personaBuscada:{},
		/*Funcion para el control de cerrado de la pagina*/
		cerrar : function(){
			
			this.dialogo.dialog('close');
		}	
}