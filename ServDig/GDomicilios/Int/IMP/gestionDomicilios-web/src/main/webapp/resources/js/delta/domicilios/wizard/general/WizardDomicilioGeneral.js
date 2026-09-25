(function($,window){

	/**
	 * Clase para generar Wizards de asignación y actualización de domicilio
	 * 
	 */
	WizardDomicilioGeneralModule = function(options){
			
		var _this = this;
		
		
		/**
		 * Opciones por default
		 * 
		 * @param String contenedor Id del contenedor del wizard
		 * @param String url url que se asignara al iframe incrustado en el dialog
		 * @param String title Título del dialogo
		 * @param Integer tipoTramite Id del tipo de tramite
		 * @param Integer idPersona el id de la persona
		 * @pararm Long noFolioSolicitud folio de la solicitud procesada
		 */
		this.options = $.extend({
			contenedor 						: 'wizardDatosActualizacion',
			idTipoTramite					: null,
			idPersona 						: null,
			idPersonaInteresada 			: null,
			onClose							: function(){},
			url 							: '/${mvn.web.app.root}'+ '/wizard/domicilio/',
			title							: "IMSS Digital",
			autoOpen						: true,
			noFolioSolicitud				: 0,
			contextPath 					: '/${mvn.web.app.root}',
			idOrigen						: '${mvn.web.app.origin.id}'
		},options);
		
		
		/**
		 * Variable donde se asignara la referencia al dialogo
		 */
		this.dialogo = undefined;
		
		
		/**
		 * Elimina las variables de sesión que se crearon para el trámite
		 */
		this.limpiarElementosSesion = function (){
				
			// Se limpia la sesión de la administración de domicilios
			$.postJSON( _this.options.url + 'limpiar-session', null, function(data) {
			}).error(function(data){
			});	
			
		};
	
		
		this.formFromObject = function(action, fields){
			
			var $form = $("<form/>", { action:action, method:'POST' });
			
			$.each( fields, function( key, value ) {
	         	$form.append($("<input/>",{ type:'hidden',name:key,value:value }));
	        });
			
			return $form;
		};
		
		
		this.cerrar = function(){
			
			// ---------------------------------------------
			// Validamos si se creo un dialogo y se cierra
			// ---------------------------------------------
			if( _this.dialogo )
				_this.dialogo.dialog('close');	
				
		};
		
		this.abrir = function(){
			
			// ---------------------------------------------------------
			// El dialogo se auto destruye al cerrarse el wizard.
			// Se crea uno nuevo al abrir el wizard
			// ---------------------------------------------------------
			_this.dialogo = $("#"+_this.options.contenedor).dialog({
	            title: _this.options.title,
	            closeOnEscape: false,
	            autoOpen: true,
	            width : 900,
	            minHeight : 400,
	            maxHeight : 900,
	            modal: true,
	            resizable: false,
	            autoResize : true,
	            overlay: {
	                opacity: 0.5,
	                background: "black"
	            },
	            position: { my: "top", at: "top", of: window, offset: "0 10" },
	            beforeClose: function(event, ui) { 
	        		
	            	
	        		
	        	}, 
	        	close: function (event, ui) {
	        		
	        		_this.limpiarElementosSesion();
	        		WizardCapturaDocumentosProbatoriosCtrl.limpiarIndicadores();
	        		
	        		// --------------------------------------------------------------
	        		// Función definida por el usuario que se ejecuta antes de
	        		// cerrar el dialogo
	        		// --------------------------------------------------------------
	        		if(jQuery.isFunction(_this.options.onClose)) {
	        			_this.options.onClose(_this.options);
	        		}
	        		
	        		// --------------------------------------------------
	        		// Destruye el dialogo y deja el div vacío
	        		// --------------------------------------------------
	        		_this.dialogo.dialog('destroy').empty();
	        		
	        	}
	        });
			
			
			if( (_this.options.idPersonaInteresada == null) || (typeof( _this.options.idPersonaInteresada ) === 'undefined') )
				_this.options.idPersonaInteresada = _this.options.idPersona;
			
			
			// ---------------------------------------------------------
			// Iframe incrustado que se cargara dentro del dialog
			// ---------------------------------------------------------
			var url = _this.options.url + _this.options.idPersona + "/"+ _this.options.idPersonaInteresada + ((_this.options.idTipoTramite)? "/"+ _this.options.idTipoTramite:'') ;
			
			$('#' + _this.options.contenedor).html('<iframe id="wizardDatosActualizacionFrame" src="' + url
				+ '" width="100%" height="100%" onload="set_size(\'wizardDatosActualizacionFrame\', 900)" frameborder="0" />');
			
		};
		
		
		
		/**
		 * El wizard se lanza automaticamente al crear una instancia
		 */
		if( _this.options.autoOpen ){
			_this.cerrar();
			_this.abrir();
		}
		
		/**
		 * Funciones publicas
		 */	
		return {
			
			verOpciones : function (){
			
				// ----------------------------------------------
				// Opciones con las que se configuro el wizard
				// ----------------------------------------------
				return _this.options;
				
			},
			cerrar : _this.cerrar,
			abrir : _this.abrir,
			getDialog : function(){ return _this.dialogo; },
			setFolioSolicitud : function(noFolioSolicitud){
				_this.options.noFolioSolicitud = noFolioSolicitud;
			},
			getFolioSolicitud : function(){
				return _this.options.noFolioSolicitud;
			},
			setOnClose : function(fn){
				_this.options.onClose = fn;
			}
		
			
		};

	};
	
	
}(jQuery,window));


// -----------------------------------------------------------
// Controlador para el modulo de domicilios
//-----------------------------------------------------------
var WizardDomicilioGeneralCtrl = {
		instance : null,
		init : function(options){
			WizardDomicilioGeneralCtrl.instance = new WizardDomicilioGeneralModule(options);
			return WizardDomicilioGeneralCtrl;
		},
		abrir : function(){
			WizardDomicilioGeneralCtrl.instance.abrir();
		},
		cerrar : function(){
			WizardDomicilioGeneralCtrl.instance.cerrar();
		},
		setOnClose : function(fn){
			WizardDomicilioGeneralCtrl.instance.setOnClose(fn);
			return WizardDomicilioGeneralCtrl;
		},
		setFolioSolicitud : function(noFolioSolicitud){
			WizardDomicilioGeneralCtrl.instance.setFolioSolicitud(noFolioSolicitud);
			return WizardDomicilioGeneralCtrl;
		}
		
};
