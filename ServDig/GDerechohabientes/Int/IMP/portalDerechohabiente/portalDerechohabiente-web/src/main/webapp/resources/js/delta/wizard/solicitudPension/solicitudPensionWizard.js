/*
 * JS de control del Wizard de Solicitud de Pension
 */

var WizardSolicitudPensionCtrl = {
	init : function(_container) {
		this.config.contenedor = _container;
		
		c = $('#'+ _container);
		
		this.dialogo = c.dialog({
				title : this.config.title,
				autoOpen : false,
				width : 900,
				height : 500,
				modal : true,
				resizable : false,
				autoResize : true,
				overlay : {
					opacity : 0.5,
					background : 'black'
				},
				position : {
					my : 'top',
					at : 'top',
					of : window,
					offset : '0 10'
				}
			});
		
		 this.dialogo.dialog({
	        	beforeClose: function(event, ui) {
	        		//se limpia el div, ya que si no se hace se vuelve a consultar la solicitud
	        		//$('#' + WizardSolicitudPensionCtrl.config.contenedor).html("");
	        		//WizardSolicitudPensionCtrl.limpiarElementosSesion();
	        	},
	        	close: function (event, ui) {
	        		$(this).dialog('destroy').empty();
	        	}
	        });
	},
	setOnCloseCallback : function(_fnCallback){
		this.callbacks = _fnCallback;
	},
	/*
	 * Datos de configuracion inicial
	 * de la consulta de la persona moral.
	 */
	config : {
		url :  "http://gestionpension-stage.imss.gob.mx/pensiones-web-externo/pensiones/iniciar.do",
		title: "Solicitud de Pension",
		contenedor : {}
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
		this.init(this.config.contenedor);
		this.dialogo.dialog('open');
		var url = this.config.url;
		
		$('#' + this.config.contenedor).html(
				'<iframe id="site" src="' + url
			 			+ '" width="100%" height="100%" frameBorder="0"/>');
		
	},
	cerrar : function(){
		
		//Cerramos el dialogo
		this.dialogo.dialog('close');
		
		//Destruimos el dialogo
		this.dialogo.dialog('destroy');
		
	}, 
	limpiarElementosSesion: function() {
		// Se limpia la sesi�n de la administraci�n de domicilios
//		$.blockUI();
//		$.postJSON('/portal-ciudadano-web-externo/wizard/comprobante/vigencia/limpiar-session', null, function(data) {
//			
//		}).error(function(data){
//			
//		}).always(function(){
//			$.unblockUI();
//		});	
	}
};


$("#iniciarSolicitudPension").live('click', function() {
	
	WizardSolicitudPensionCtrl.init('wizardSolicitudPension');
	WizardSolicitudPensionCtrl.abrir();
	
});
