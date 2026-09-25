var ProcesandoSolicitudCtrl = {
	countdown : 60,
	timeoutHandler : null,
	dialogo : null,
	init : function(idContenedor, tiempoEspera, tiempoIntervaloEspera) {
		var self = this;
		this.config.contenedor = idContenedor;
		
		if (tiempoEspera) {
			this.config.tiempoEspera = tiempoEspera;
		}
		if (tiempoIntervaloEspera) {
			this.config.tiempoIntervaloEspera = tiempoIntervaloEspera;
		}

		// Configuracion del dialogo
		this.dialogo = $('#' + idContenedor).dialog({
			title : this.config.title,
			autoOpen : false,
			width : 900,
			height: 340,
			modal : true,
			resizable : false,
			autoResize : true,
			overlay : {
				opacity : 0.5,
				background : "black"
			},
			beforeClose : function(event, ui) {
				if($.isFunction(self.beforeCloseCallback)) {
					self.beforeCloseCallback.call(self, self.config.folio);
				}
			},
			close: function (event, ui) {
								
				self.onCloseCallback = null;
				self.beforeCloseCallback = null;
				self.validateCallback = null;
				self.onSolicitudExitosaCallback = null;
				self.config.folio = '';
				
				if($.isFunction(self.onCloseCallback)) {
					self.onCloseCallback.call(self, self.config.folio);
				}
        		$(this).dialog('destroy').empty();
        	}
		});
	},

	abrir : function(folioSolicitud, idSolicitud, tipoSolicitud) {
		this.init(this.config.contenedor);
		
		this.config.folio = folioSolicitud;
		
		this.dialogo.dialog('open');
		var url = "";
		

		if (tipoSolicitud != undefined && idSolicitud != undefined) {
			url = this.config.url + folioSolicitud + "/" + idSolicitud + "/" + tipoSolicitud;
		} else if (idSolicitud != undefined) {
			url = this.config.url + folioSolicitud + "/" + idSolicitud;
		} else {
			url = this.config.url + folioSolicitud;
		}
		
		$('#' + this.config.contenedor).html('<iframe id="procesandoFrame" src="' + url
				+ '" width="100%" height="100%" ' 
				+ 'frameborder="0" />');
		
		this.iniciaConteo();
	},

	config : {
		url : "/wizard-web/procesando/solicitud/",
		title : "Procesando solicitud...",
		tiempoEspera : 60,
		tiempoIntervaloEspera : 20,
		contenedor : {},
		idOrigen	:	'',
		folio: '',
		abrirDialogoExito: true
	},

	// Callback a invocar cuando se termine la invocacion de la consulta
	onCloseCallback : null,
	beforeCloseCallback : null,
	validateCallback : null,
	onSolicitudExitosaCallback : null,

	setOnCloseCallback : function(_fnCallback) {
		if ($.isFunction(_fnCallback) || _fnCallback == null) {
			this.onCloseCallback =  _fnCallback;
		} else {
			$.error('No se recibió una función para onCloseCallback');
		}
	},
	
	setBeforeCloseCallback : function(_fnCallback) {
		if ($.isFunction(_fnCallback) || _fnCallback == null) {
			this.beforeCloseCallback =  _fnCallback;
		} else {
			$.error('No se recibió una función para beforeCloseCallback');
		}
	},

	setValidateCallback : function(_fnCallback){
		if ($.isFunction(_fnCallback) || _fnCallback == null) {
			this.validateCallback =  _fnCallback;
		} else {
			$.error('No se recibió una función para validateCallback');
		}
	},
	
	setOnSolicitudExitosaCallback : function(_fnCallback){
		if ($.isFunction(_fnCallback) || _fnCallback == null) {
			this.onSolicitudExitosaCallback =  _fnCallback;
		} else {
			$.error('No se recibió una función para onSolicitudExitosa');
		}
	},
	
	setAbrirDialogoExito : function(_abrirDialogoExito){
		this.config.abrirDialogoExito=_abrirDialogoExito;
	},
	
	setIdOrigen : function(_idOrigen){
		this.config.idOrigen=_idOrigen;
	},
	
	cerrar : function() {
		this.dialogo.dialog('close');
	},

	iniciaConteo: function() {
		var tiempoEsperaComet = 30000; // milisegundos
		var self = this;
		var counter = this.config.tiempoEspera;
		var testInterval = this.config.tiempoIntervaloEspera;
		
		self.timeoutHandler = setTimeout(function(){
			console.log('TERMINA LA ESPERA PARA AVISO DEL COMET');
			
			self.countdown = window.setInterval(function() {
//				console.log('COUNTER -> ' + counter);
				if (counter <= 0) {
//					console.log('ULTIMA VALIDACION');
					window.clearInterval(self.countdown);
					self.validarUltimaVez(true);
				} else if ((counter % testInterval) == 0) {
//					console.log('SE CHECA ESTADO DE LA SOLICITUD');
					self.validarUltimaVez(false);
				}
				counter -= 1;	
			}, 1000);
		}, tiempoEsperaComet);
	},
	
	validarUltimaVez: function(isUltimaValidacion){
		this.validateCallback(isUltimaValidacion);
	},
	
	pararValidaciones : function() {
		window.clearInterval(this.countdown);
		clearTimeout(this.timeoutHandler);
	}
};