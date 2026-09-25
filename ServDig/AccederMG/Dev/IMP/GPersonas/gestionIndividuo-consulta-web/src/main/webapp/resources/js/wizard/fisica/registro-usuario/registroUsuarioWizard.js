/*
 * JS de control del Wizard de Actualizacion de Datos
 * de Persona.
 */

var WizardRegistroUsuarioCtrl = {
		
		/*
		 * Funcion para inicializar la configuracion
		 */
		init : function(_contenedor, _tipoPersona, _idPersona, _curpPersona, _rfcPersona){
			
			this.config.contenedor = _contenedor;
			
			this.config.tipoPersona= _tipoPersona;
			this.config.idPersona = _idPersona;
			this.config.curpPersona = _curpPersona;
			this.config.rfcPersona = _rfcPersona;
			this.datosSalida = null;
	        this.config.contenedor = _contenedor;
	        
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
	            modal: true,
	            resizable: false,
	            position: { my: "top", at: "top", of: window, offset: "0 10" }, 
	            overlay: {
	                opacity: 0.5,
	                background: "black"
	            }
	        });
	        
	     // Configuramos el metodo onClose del dialogo
		    this.dialogo.dialog({
		    	beforeClose : function(event, ui) {
		    		WizardRegistroUsuarioCtrl.limpiarElementosSesion();
		    		WizardRegistroUsuarioCtrl.callbacks.call();
		    }});
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
			url :  "/gestionIndividuo-consulta-web/wizard/tramite/registro/usuario/init",
			title:"IMSS Digital",
			contenedor : {}, 
			tipoPersona : "",  
			idPersona:"",
			curpPersona:"",
			rfcPersona:""
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
			this.init(this.config.contenedor, this.config.tipoPersona,
				this.config.idPersona, this.config.curpPersona, 
				this.config.rfcPersona);
			
			this.dialogo.dialog('open');
			
			var url = this.config.url;
			/*
			+ this.config.tipoPersona + "/"
			+ this.config.idPersona + "/" + this.config.curpPersona + "/"
			+ this.config.rfcPersona;
			*/
			
			$('#' + this.config.contenedor).html(
					'<iframe id="site" src="' + url
				 			+ '" width="100%" height="100%" '
							+ 'onload="set_size(\'site\')" frameBorder="0"/>');
			
		}, 
	
		cerrar : function(){
			//Cerramos el dialogo
			this.dialogo.dialog('close');
			//Destruimos el dialogo
			this.dialogo.dialog('destroy');
			
		},
		
		acceso : function(_curp,_password){
			parent.AuthenticateSSO.authenticate(_curp,_password);
			parent.AuthenticateSSO.setOnCloseCallback(function(){
				$("#formlogin").submit();
			});
			
		},
		
		datosSalida: {},
		
		setDatosSalida: function(_datosSalida) {
			this.datosSalida = _datosSalida;
		},
		
		/*
		 * Funci�n para limpiar los elementos en sesi�n, se puso aqu�
		 * para agregarla en el cerrar del dialogo y seguir permitiendo el
		 * setteo de la funci�n de callback de cada gesti�n en el momento de
		 * cerrar.
		 */
		limpiarElementosSesion: function (){
			
			// Se limpia la sesi�n de la administraci�n de domicilios
			$.postJSON('/gestionIndividuo-consulta-web/wizard/tramite/registro/usuario/limpiar-sesion', null, function(data) {
				
			}).error(function(data){
				
			});	
		}
};