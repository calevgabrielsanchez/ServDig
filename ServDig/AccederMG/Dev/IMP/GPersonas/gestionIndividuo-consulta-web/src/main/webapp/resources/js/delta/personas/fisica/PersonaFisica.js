/*
 * Javascript para el control de los servicios exteriores de la persona moral
 * a traves de este objeto se dara acceso a los demas modulos ( externos)
 * a la funcionalidad de Persona Fisica.
 *
 *
 */

var PersonaFisicaCtrl = {
		

		dialogo:{},
		init:function(idContenedorPersona){
			
			/*
			 * Inicializamos el dialogo que contiene la pantalla 
			 * de la consulta de personas morales.
			 */
			var horizontalPadding = 15;
	        var verticalPadding = 15;
	        
	        
	        this.config.contenedor = idContenedorPersona;
	        
		    var d = $('#' + idContenedorPersona);
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
	         * Configurando los callbacks
	         */
	        //this.callbacks = $.Callbacks();
	        
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
	        		if(!PersonaFisicaCtrl.personaEncontrada){
	        			/*
	        			 * No se encontro a la persona
	        			 */ 
	        			alert('personaBuscada-consulta');
	        			PersonaFisicaCtrl.callbacks.call(PersonaFisicaCtrl.personaBuscada);
	        		}else{
	        			alert('getPersona()-consulta');
	        			PersonaFisicaCtrl.callbacks.call(PersonaFisicaCtrl.getPersona());
	        		}
	        	}	
	        })
	        
	        
		},
		/*
		 * Metodo para invocar la consulta de personas 
		 * morales.
		 */
		buscar:function(){
			
			
			this.dialogo.dialog('open');
			
			 $('#' + this.config.contenedor).html(
						'<iframe id="site" src="' + this.config.url
					 			+ '" width="100%" height="100%" frameborder="0"/>');
			
			
		},
		
		/*
		 * Datos de configuracion inicial
		 * de la consulta de la persona moral.
		 */
		config : {
			url :  "/gestionIndividuo-web/persona/fisica/busqueda-embebida",
			title:"Buscar persona fisica",
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
			alert('setOnCloseCallback()-consulta');
			this.callbacks = _fnCallback;
		},
		
		setPersona : function( objPersona){
			this.persona = objPersona;
			this.personaEncontrada = true;
		},
		
		getPersona : function(){
			alert('se invoco al getPersona()-consulta');
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


