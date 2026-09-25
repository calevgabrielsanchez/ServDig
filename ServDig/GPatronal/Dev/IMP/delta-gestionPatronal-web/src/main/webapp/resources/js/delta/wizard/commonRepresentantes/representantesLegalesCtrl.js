var representantesLegalesCtrl = {
	
	//Wizar IVRO Compra Seguro Personal (118)
	wizardPrincipal : null,
		
	setWizardPrincipal : function(wizardPrincipal){
		representantesLegalesCtrl.wizardPrincipal = wizardPrincipal;
	},
		
	/*
	 * Datos de configuracion inicial.
	 * 
	 */
	config : {
		contextPath : "",
		idOrigen	: "",
		urlRLVentanilla	: "",
		urlValidarRL    : "",
		urlFinalizarTramite	: "",
				
		tipoTramite		: 0,		
		urlCancelarTramite	: ""
		
	},
		
	/*
	 * Funcion para inicializar la configuracion
	 * 
	 */
	init : function(_tipoTramite) {
		this.config.contextPath 			= '/${mvn.web.app.root}';
		this.config.idOrigen 				= '${mvn.web.app.origin.id}';
		//URL para mostrar los RL
		this.config.urlRLVentanilla 		= '/${mvn.web.app.root}/wizard/alta/representeLegal/';
		//URL para validar RL
		this.config.urlValidarRL	 		= '/${mvn.web.app.root}/wizard/alta/representeLegal/validarRL/';	
		//URLs para finalizar tramite Ventanilla
		this.config.urlFinalizarTramite	 	= '/${mvn.web.app.root}/wizard/alta/representeLegal/finalizarVentanilla/';
		
		//Tipo de tramite
		this.config.tipoTramite				= _tipoTramite;
		//URLs cancelar tramite correspondiente 
		this.config.urlCancelarTramite	 	= '/${mvn.web.app.root}/wizard/alta/representeLegal/cancelar/solicitud/';
	},

	abrir : function() {
		// Para que cada vez que se abra el dialogo se cree de nuevo.
		this.init(this.config.tipoTramite, this.config.urlCancelarTramite);

	},
	
	limpiar : function() {		
	},
	
	cerrarWizardPrincipal : function() {
		if (this.wizardPrincipal != null) {
			this.wizardPrincipal.cerrar();
		}
	},
	
	redirectListaRepresentantesLegales : function(_idForma, _idSolicitud, _idTipoTramite) {
		setTimeout(function(){
			var urlAction = parent.representantesLegalesCtrl.config.urlRLVentanilla + _idSolicitud+"/"+_idTipoTramite;
			$.blockUI();
			$('form#'+_idForma).attr('action', urlAction);
			$('form#'+_idForma).submit();
		}, 90);	
	}
	
};
