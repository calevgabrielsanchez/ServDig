(function($,window){

	/**
	 * Clase para generar Wizards de cambio de clínica
	 * 
	 */
	WizardCambioClinicaGeneralModule = function(options){
			
		var _this = this;
		
		
		/**
		 * Opciones por default
		 * 
		 * @param String contenedor Id del contenedor del wizard
		 * @param Long idAsignacionNss id de la asignacion de la persona
		 * @param function onClose
		 * @param String url url que se asignara al iframe incrustado en el dialog
		 * @param String title Título del dialogo
		 * @param String accionesId Id del contenedor de los botones
		 */
		this.options = $.extend({
			contenedor 			: 'divWizardClinica',
			idAsignacionNss		: null,		
			onClose				: function(){},
			url 				: "/portal-ciudadano-web-externo/clinica/",
			title				: "IMSS Digital",
			autoOpen			: false,
			accionesId			: 'clinicaBotones',
			noFolioSolicitud	: 0,
			limpiarSesion		: true
		},options);
		
		
		/**
		 * Variable donde se asignara la referencia al dialogo
		 */
		this.dialogo = undefined;
		
		
		/**
		 * Elimina las variables de sesión que se crearon para el trámite
		 */
		this.limpiarElementosSesion = function (){
			
			$.blockUI();
			$.postJSON( _this.options.url + 'limpiar-session', null, function(data) {
			}).error(function(data){
			}).always(function(){
				$.unblockUI();
				
				// ------------------------------------------
		    	// Para que los dialogos no tapen el BlockUI
		    	// ------------------------------------------
				$.ui.dialog.maxZ = 0;
			
			});	
			
		};
	
		
		this.cerrar = function(){
			
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
	            autoOpen: false,
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
	        		
	        		// --------------------------------------------------------------
	        		// Función definida por el usuario que se ejecuta antes de
	        		// cerrar el dialogo
	        		// --------------------------------------------------------------
	        		if(jQuery.isFunction(_this.options.onClose)) {
	        			_this.options.onClose(_this.options);
	        		}
	        		
	        		_this.dialogo.dialog('destroy').empty();
	        		
	        		
	        		if(_this.options.limpiarSesion)
	        			_this.limpiarElementosSesion();
	        		
	        	}
	        });
			
			
			_this.cargarPagina("inicial/" +_this.options.idAsignacionNss);
			
			
		};
		
		
		/**
		 * Carga una página en el dialogo
		 * 
		 * @param pagina Url de la pagina en el contexto del dialogo
		 * @param callback funcion a ejecutar una vez cargada la página
		 */
		this.cargarPagina = function(pagina,callback){
			$.blockUI();
			
			pagina += '?_=' + (new Date()).getTime();
			$('#' + _this.options.contenedor).load(_this.options.url + pagina, function(){
			
				var pageEnviroment = this;
				var $this = $(this);
				
				$('#' + _this.options.accionesId).on('shown.bs.dropdown', function () {
					var extra = $(this).children("ul.dropdown-menu").outerHeight(true) + 10;
					var actual = $this.outerHeight(true);
					$this.css("height",(actual+extra));
					
					if( !$this.isVisibleBottom() ){
						var windowScrollTop = $(window).scrollTop();
						window.scrollTo(0, windowScrollTop+extra+20);
					}

				}).on('hidden.bs.dropdown', function () {
					var extra = $(this).children("ul.dropdown-menu").outerHeight(true);
					var actual = $this.outerHeight(true);
					$this.css("height",(actual-extra)-10);
				});
				
				
				
				
				if( $.isFunction( callback ) ){
					callback.call(pageEnviroment,_this.options);
				};
				
				_this.dialogo.dialog('open');
				$.unblockUI();
								
			});
		};
		
		
		// ------------------------------------------
    	// Para que los dialogos no tapen el BlockUI
    	// ------------------------------------------
		$.ui.dialog.maxZ = 0;
		
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
			setOption : function(option, value){
				for (var propName in _this.options){
			        if( _this.options[propName] == option ){
			        	_this.options[propName] = value;
			        	return;
			        }
			    }
			},
			verOpciones : function (){
				return _this.options;
			},
			cerrar : _this.cerrar,
			abrir : _this.abrir,
			getDialog : function(){ return _this.dialogo; },
			setOnClose : function(fn){
				_this.options.onClose = fn;
			},
			ocultar : function(){
				$('#' + _this.options.contenedor).parent().hide();
			},
			setFolioSolicitud : function(noFolioSolicitud){
				_this.options.noFolioSolicitud = noFolioSolicitud;
			},
			mostrar : function(){
				$('#' + _this.options.contenedor).parent().show();
			},
			cargarPagina :_this.cargarPagina
		
			
		};

	};
	
	
}(jQuery,window));


// --------------
// Controlador 
// --------------
var WizardCambioClinicaGeneralCtrl = {
		instance : null,
		init : function(options){
			this.instance = new WizardCambioClinicaGeneralModule(options);
			return this;
		},
		abrir : function(){
			this.instance.abrir();
		},
		cerrar : function(){
			this.instance.cerrar();
		},
		setOnClose : function(fn){
			this.instance.setOnClose(fn);
			return this;
		},
		ocultar : function(){
			this.instance.ocultar();
		},
		mostrar : function(){
			this.instance.mostrar();
		},
		setFolioSolicitud : function(noFolioSolicitud){
			this.instance.setFolioSolicitud(noFolioSolicitud);
			return this;
		},
		cargarPagina : function(pagina, callback){
			this.instance.cargarPagina(pagina, callback);
		}
		
};
