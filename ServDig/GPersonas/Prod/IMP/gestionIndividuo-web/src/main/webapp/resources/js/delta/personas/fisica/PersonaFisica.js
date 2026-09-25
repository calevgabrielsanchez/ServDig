/*
 * Javascript para el control de los servicios  de la persona fisica
 * a traves de este objeto se dara acceso a los demas modulos ( terceros)
 * a la funcionalidad de Consulta de Persona Fisica.
 *
 */

var PersonaFisicaCtrl = {
		/*
		 * Tipo del servicio, 
		 * 1: Simple se refiere a la consulta simple, la cual recibe
		 * cualquier parametro de entrada y consulta personas que ya fueron
		 * registradas.
		 * 
		 * 2: Se refiere al servicio en donde la finalidad es ubicar a una persona
		 * en cualquiera de las 3 entidades ( imss, sat y renapo) ademas de que 
		 * complementa las calificaciones faltantes del registro en caso de
		 * que ya exista.
		 */
		tipoServicio: {
			SIMPLE : '1',
			COMPLETO: '2'
		},
		/*
		 * Tipo de contexto, se refiere al tipo de acceso que tiene el servicio, en donde:
		 * 
		 * 1: INTERNO : se refiere a usuarios internos
		 * 2: EXTERNO: se refiere a los usuarios externos.
		 */
		tipoContexto:{
			INTERNO: '1',
			EXTERNO: '2'
		},
		
		servicioURL : {
			simpleInterno :"/gestionIndividuo-web/persona/fisica/busqueda-embebida",
			completoInterno:"/gestionIndividuo-consulta-web/persona/fisica/ubicar" 
		},
		
		tipoBusqueda:{
			RENAPO : 'renapo',
			SAT : 'sat'
		},
		
		dialogo:{},
		init:function(idContenedorPersona, sTipoServicio, sTipoContexto,sTipoBusqueda ){
			
			/*
			 * Inicializamos el dialogo que contiene la pantalla 
			 * de la consulta de personas morales.
			 */
			var horizontalPadding = 15;
	        var verticalPadding = 15;
	        
	        
	        this.config.contenedor = idContenedorPersona;
	        this.config.servicio = sTipoServicio;
	        this.config.contexto = sTipoContexto;
	        this.config.tipoBusqueda = sTipoBusqueda;
	        
	        
	        if(sTipoContexto == PersonaFisicaCtrl.tipoContexto.INTERNO){
	        	if(sTipoServicio == PersonaFisicaCtrl.tipoServicio.SIMPLE){
		        	this.config.url = PersonaFisicaCtrl.servicioURL.simpleInterno;
		        }else if(sTipoServicio== PersonaFisicaCtrl.tipoServicio.COMPLETO ){
		        	this.config.url = PersonaFisicaCtrl.servicioURL.completoInterno + "/"+PersonaFisicaCtrl.config.tipoBusqueda;
		        }
	        }
		    var d = $('#' + idContenedorPersona);
		    /*
		     * Configuracion del dialogo
		     */
	        this.dialogo = d.dialog({
	            title: this.config.title,
	            autoOpen: false,
	            width: 'auto',
	            height: 'auto',
	            modal: true,
	            resizable: false,
//	            autoResize: true,
	            overlay: {
	                opacity: 0.5,
	                background: "black"
	            }, 
	            buttons: {
	            	'Cerrar': function() {
	            		$( this ).dialog( "close" );
	            	}
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
	        			
	        			/**
	        			 * LUDS: 22/02/2013 
	        			 * Se modifico, para validar que el objeto de la personaBuscada
	        			 * fuera diferente de nulo para validar la propiedad de 
	        			 * sexo y lugar de nacimiento
	        			 * BUG identificado por Derechohabientes.
	        			 */
	        			if(PersonaFisicaCtrl.personaBuscada){
	        				if(PersonaFisicaCtrl.personaBuscada.sexo.idSexo == -1){
		        				PersonaFisicaCtrl.personaBuscada.sexo = undefined;
		        			}
		        			if(PersonaFisicaCtrl.personaBuscada.lugarNacimiento.clave == -1){
		        				delete PersonaFisicaCtrl.personaBuscada.lugarNacimiento;
		        			}
		        			PersonaFisicaCtrl.callbacks.call(PersonaFisicaCtrl.personaBuscada);
	        			}
	        			
	        		}else{
//	        			alert('getPersona()');
	        			PersonaFisicaCtrl.callbacks.call(PersonaFisicaCtrl.getPersona());
	        		}
	        		
	        		PersonaFisicaCtrl.limpiarObjetoRespuesta();
	        	}	
	        });
		},
		/*
		 * Metodo para invocar la consulta de personas 
		 * morales.
		 */
		buscar:function(){
			
			//Para que cada vez que se abra el dialogo se cree de nuevo.
			this.init(this.config.contenedor, this.config.servicio,
				this.config.contexto, this.config.tipoBusqueda);
			
			this.dialogo.dialog('open');
			
			 $('#' + this.config.contenedor).html(
						'<iframe id="site" src="' + this.config.url
					 			+ '" width="1000px" height="1000px" frameborder="0" />');
		},
		
		/*
		 * Datos de configuracion inicial
		 * de la consulta de la persona moral.
		 */
		config : {
			url :  "/gestionIndividuo-web/persona/fisica/busqueda-embebida",
			title:"Buscar persona f\u00EDsica",
			contenedor: {},
			servicio : "",
			contexto: "",
			tipoBusqueda: ""
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
//			alert('setOnCloseCallback() sin consulta');
			this.callbacks = _fnCallback;
		},
		
		setPersona : function( objPersona){
			this.persona = objPersona;
			this.personaEncontrada = true;
		},
		
		getPersona : function(){
//			alert('se invoco al getPersona()');
			return this.persona;
		},
		
		persona : {},
		personaEncontrada : false,
		/**
		 * LUDS: 22/02/2013
		 * Se modifico para validar que la propiedad 
		 * sea nula para evitar problemas.
		 */
		personaBuscada:null,
		
		cerrar : function(){
			//Cerramos el dialogo
			this.dialogo.dialog('close');
			
			//Destruimos el dialogo
			this.dialogo.dialog('destroy');
			
		},
		
		limpiarObjetoRespuesta: function() {
			var url = '/gestionIndividuo-consulta-web/persona/fisica/ubicar/limpiar-objeto-respuesta';
			$.post(url);
		}
}