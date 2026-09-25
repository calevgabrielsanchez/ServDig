(function() {
	
$.getScript('/${mvn.web.app.root}/static/resources/js/wizard/seguroDomestico/comunes/common.js');
	
	var dialogoConfirmarCancelar;
	var dialogoConfirmar;
	var cancelarDesdeBoton = false;
	
	var idPersonaGlobal;
	var rfcGlobal;
	var nssCifradoGlobal;
	var _WizardDetalleSeguroDomestico;	
	var invocarFirmaDigital;
	
	$(document).ready(function() {
		
		_WizardDetalleSeguroDomestico = parent.WizardDetalleDomesticoCtrl;	
		
		$('#cancelarSolicitud').click(function(event) {
			event.preventDefault();
			cancelarDesdeBoton = true;
			dialogoConfirmarCancelar.dialog('open');
		});
		
		$('#siguientePaso').click(function(event) {
			event.preventDefault();
			if(ventanilla)
				firmarTramite(null);
			else
				mostrarFirmarTramite();
		});
		
		$('#cambiaDomicilio').click(function(event) {
				//alert('cambiaDomicilio');
				parent.DomicilioCtrl.init('domiciliosComponent'); 
				parent.DomicilioCtrl.setOnCloseCallback(fnOnDomicilioReturn);
				parent.DomicilioCtrl.localizar();
		});
		
		$('#salirSolicitud').click(function(event) {
			event.preventDefault();
			if(ventanilla && tieneSeguros)
				$('#formBackToVentanillaMain').submit();
			else
				closeWizard();
		});
		
		$('#tblTrabajadores').dataTable({
			'bFilter': false,
			'bDestroy': true,
			'bLengthChange': false,
			'bAutoWidth': false,
			'bPaginate' : false,
			'bInfo': false,
			/*'sPaginationType': 'none',*/
			'aoColumnDefs': [{'sSortDataType': 'html', 'sType': 'html', 'aTargets': [0]}],
			'aoColumns' : [{'sWidth': '20%'},
		                   {'sWidth': '40%'},
		                   {'sWidth': '20%'},
		                   {'sWidth': '20%'}]
			}	
		);
	
		dialogoConfirmarCancelar = $('#dialog-confirm-cancelar').dialog({
			resizable: false,
			height:'auto',
			modal: true,
			autoOpen: false,
			dialogClass: 'no-close',
		    closeOnEscape: false,
			buttons: {
				'ACEPTAR': function() {
			 		cancelable = false;
					$(this).dialog( "close" );
					$.ajax('/${mvn.web.app.root}/wizard/seguroDomestico/comunes/cancelarSolicitud',{
						complete: function() {
							if(cancelarDesdeBoton && ventanilla && tieneSeguros)
                                parent.WizardIVROVentanillaSeguroDomesticoCtrl.abrir();
							else
								closeWizard();
						}
					});
			 	},
			 	'CANCELAR': function() {
			 		$(this).dialog( "close" );
			 	}
			 }
		 });
	
		dialogoConfirmar = $('#dialog-confirm').dialog({
			resizable: false,
			height:'auto',
			modal: true,
			dialogClass: "no-close",
		    closeOnEscape: false,
			autoOpen: false
		 });
	});
	
	function mostrarFirmarTramite() {
		mostrarMensaje("A continuaci&oacute;n se te solicitar&aacute; el ingreso de tu FIEL para firmar digitalmente el tr&aacute;mite.", invocarFirmaDigital);
	}
	
	invocarFirmaDigital = function() {
		
		parent.FirmaDigitalCtrl.setOnCloseCallback(function() {
			if(parent.FirmaDigitalCtrl.datosSalida == null) {
				mostrarMensaje("La validaci&oacute;n de la firma no pudo ser realizada");
			}else {
				if(parent.FirmaDigitalCtrl.datosSalida.Resultado == 0) {
					var firmaResponse = {
						cadenaOriginal               : parent.FirmaDigitalCtrl.datosSalida.contenedores[0].cadori,
						recibo                       : parent.FirmaDigitalCtrl.datosSalida.firmas[0],
						reciboNotarial               : parent.FirmaDigitalCtrl.datosSalida.folio,
						urlAcuseFirma                : parent.FirmaDigitalCtrl.datosSalida.acuse,
						serialCertificado            : parent.FirmaDigitalCtrl.datosSalida.serie_cert,
						strIniciaVigenciaCertificado : parent.FirmaDigitalCtrl.datosSalida.vigIni,
						strFinVigenciaCertificado    : parent.FirmaDigitalCtrl.datosSalida.vigFin
					};
	
					firmarTramite(firmaResponse);
				} else {
					mostrarMensaje("La validaci&oacute;n de la firma no pudo ser realizada");
				}
			}
		});
		
		var componenteFirma = {
			tipo_operacion :'firmaCMS',
			acuse:'CDCT',
			tipoAcuse: '1',
			rfc: parent.FirmanteCtrl.rfc,
			validarRFC :true,
			curp: parent.FirmanteCtrl.curp,
			firma_archivo : false,
			min_archivos : 0,
			max_archivos : 0,
			fechaElectronica : datosEntradaFirma.fechaElectronica,
			cad_original:$('#contenidoFirmar').val(),
			registroPatronal : "",
			nombreCompleto : parent.FirmanteCtrl.nombreRazonSocial,
			idTipoSolicitud : codigoTipoSolicitud,
			descripcionTipoSolicitud : descripcionTipoSolicitud,
			folioSolicitud : $('#folioSolicitud').val(),
			idTipoTramite : arrayCodigoTipoTramite,
			afectado: [{curp: parent.FirmanteCtrl.curp, 
				        nombreRazonSocial: parent.FirmanteCtrl.nombreRazonSocial, 
				        rfc:parent.FirmanteCtrl.rfc}]
		};
	
		parent.iniciarFirmaDigital(componenteFirma);
	}
	
	function firmarTramite(firmaResponse) {
		$.blockUI();
		
		var url = '/${mvn.web.app.root}/wizard/seguroDomestico/comunes/procesar-datos-firma';
	
		$.postJSON(url, firmaResponse, function(data) {
			$.unblockUI();
			var msgButton;
			var buttons = [];
			if(data.error === undefined) {
				cancelable = false;
				closeWizard();
				//Obtención de datos del patron.
				idPersonaGlobal = data.solicitud.tramite[0].persona.idPersona;
				rfcGlobal = data.solicitud.tramite[0].persona.rfc;
				nssCifradoGlobal = data.solicitud.tramite[0].persona.nssCifrado;
				//Procesar y seter de beforecallback (Metodo que se ejecutara al finalizar el procesar)
				parent.ProcesandoSolicitudCtrl.setBeforeCloseCallback(
					function () {
						//console.log("muestraDetalleSeguroDomestico |" + idPersonaGlobal +"|"+ rfcGlobal +"|"+ nssCifradoGlobal +"|");		
						_WizardDetalleSeguroDomestico.setDatos(idPersonaGlobal, rfcGlobal, nssCifradoGlobal);
						_WizardDetalleSeguroDomestico.abrir();
					});
				parent.ProcesandoSolicitudCtrl.abrir(data.solicitud.numSolicitud);								
			}
			else {
				$('#mensajeDialogo').html('Ocurri&oacute; un error al intentar terminar la solicitud <strong>' + $('#folioSolicitud').val() + '</strong>. Intenta nuevamente.');
				msgButton = 'ACEPTAR';
			}
			buttons.push({
				text: msgButton,
				click: function() {
					$(this).dialog('close');
					if(data.error === undefined)
						closeWizard();
				}
			});
			
			dialogoConfirmar.dialog("option", "buttons", buttons);
			
			dialogoConfirmar.dialog('open');
		}).error(function(data){
			mostrarMensaje(data.mensaje);
		});
	}
	
	function mostrarMensaje(mensaje, listener) {
		dialogoConfirmar.dialog("option", "buttons", [ {
			text : 'ACEPTAR',
			click : function() {
				$(this).dialog('close');
				if(listener)
					listener();
			}
		}]);
		
		$('#mensajeDialogo').html(mensaje);
		dialogoConfirmar.dialog('open');
	}

})();


/*
 * JS de soporte para la inclusion y llamado
 * de los componentes de domicilio, para ubicar y lozaclizar 
 * un domicilio nacional.
 */




/**
 * Objeto de control de domicilio, a traves de este objeto de JS
 * se iniciliaza, configura , invoca y regresa un objeto 
 * domicilio, el cual fue localizado.
 */
var DomicilioCtrl = {
		
		/*
		 * Funcion para inicializar la configuracion
		 * del domicilio.
		 */
		init : function(_contenedor){
			this.config.contenedor = _contenedor;			
			this.config.contextPath = '/${mvn.web.app.root}';
			this.config.idOrigen = '${mvn.web.app.origin.id}';
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
	            width: 950,	            
	            modal: true,
	            resizable: false,
	            autoResize: true,
	            overlay: {
	                opacity: 0.5,
	                background: "black"
	            },
	            position: { my: "top", at: "top", of: window, offset: "0 10" }
	        });
	        
	        
	        /*
	         * Configuramos el metodo onClose del dialogo
	         * 
	         */
	        this.dialogo.dialog({
	        	beforeClose: function(event, ui) { 
				
	        		if ( event.originalEvent && $(event.originalEvent.target).closest(".ui-dialog-titlebar-close").length ) {
				    	//DO NOTHING
				    }else{
	        			DomicilioCtrl.callbacks.call( DomicilioCtrl.domicilio);
				    }
	        	},
	        	close: function (event, ui) {
	        		$(this).dialog('destroy').empty();
	        	}
	        });
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
			url :  '/${mvn.web.app.root}' + "/domicilio/nacional/ubicar/",
			title:"Localizar domicilio nacional",
			contenedor : {},			
			contextPath : "",
			idOrigen:""
		},
		/*
		 * Callback a invocar cuando se 
		 * termine la invocacion de la consulta
		 */
		callbacks : {},
		
		dialogo : {},
		/*
		 * Objeto domicilio 
		 */
		domicilio : {}, 
		/**
		 * 
		 */
		localizar : function(){
			//Para que cada vez que se abra el dialogo se cree de nuevo.
			this.init( this.config.contenedor);
			
			this.dialogo.dialog('open');
			
			$('#' + this.config.contenedor).html(
					'<iframe id="domicilioFrame" src="' + this.config.url
				 			+ '" width="100%" height="100%" frameborder="0" onload="set_size(\'domicilioFrame\', 900)"/>');
			
		}, 
	
		cerrar : function(){
			//Cerramos el dialogo
			this.dialogo.dialog('close');
		},
		
		tipoDomicilio : {},
		
		setTipoDomicilio : function (_tipoDomicilio){
			this.tipoDomicilio = _tipoDomicilio;
		},
		
		getTipoDomicilio : function (){
			return this.tipoDomicilio;
		}
};