(function() {
	var dialogoConfirmarCancelar;
	var dialogoConfirmar;
	var cancelarDesdeBoton = false;
	
	var idPersonaGlobal;
	var rfcGlobal;
	var nssCifradoGlobal;
	var _WizardAltaCVROCtrl;	
	var invocarFirmaDigital;
	
	$(document).ready(function() {
		
		_WizardAltaCVROCtrl = parent.WizardAltaCVROCtrl;	
		
		$('#cancelarSolicitud').click(function(event) {
			event.preventDefault();
			cancelarDesdeBoton = true;
			dialogoConfirmarCancelar.dialog('open');
		});
		
		$('#siguientePaso').click(function(event){
			var valor = $('input#chkTCCuestionario').filter(":checked").val();
			  if(valor == undefined){
				  muestraMsgError('Para finalizar debes aceptar los T&eacute;rminos y Condiciones.');
				  return false;
			  }else{
				  //$( "#impresionDocumentosForm").submit();
				  //closeWizard();
				  event.preventDefault();
				  firmarTramite(null);
			  }
		});
	
		dialogoConfirmarCancelar = $('#dialog-confirm-cancelar').dialog({
			resizable: false,
			height:'auto',
			modal: true,
			autoOpen: false,
			dialogClass: 'no-close',
		    closeOnEscape: false,
			buttons: {
				'Cancelar': function() {
			 		$(this).dialog( "close" );
			 	},
				'Aceptar': function() {
			 		cancelable = false;
					$(this).dialog( "close" );
					$.ajax('/${mvn.web.app.root}/wizard/continuacionVoluntaria/comunes/cancelarSolicitud',{
						complete: function() {
							if(cancelarDesdeBoton && ventanilla && tieneSeguros)
								$('#formBackToVentanillaMain').submit();
							else
								closeWizard();
						}
					});
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
		
		
		function firmarTramite(firmaResponse) {
			$.blockUI();
			
			var url = '/${mvn.web.app.root}/wizard/continuacionVoluntaria/comunes/procesar-datos-firma';
			cancelable = false;
			$.postJSON(url, firmaResponse, function(data) {
				$.unblockUI();
				var msgButton;
				var buttons = [];
				if(data.error === undefined) {
					cancelable = false;
					idPersonaGlobal = data.solicitud.tramite[0].persona.idPersona;
					nssCifradoGlobal = data.solicitud.tramite[0].persona.nssCifrado;
					
					parent.ProcesandoSolicitudCtrl.setOnSolicitudExitosaCallback(function() {
						_WizardAltaCVROCtrl.setDatos(idPersonaGlobal, nssCifradoGlobal);
						_WizardAltaCVROCtrl.abrir();
					});
					parent.ProcesandoSolicitudCtrl.abrir(data.solicitud.numSolicitud);
					closeWizard();
				}
				else {
					$('#mensajeDialogo').html('Ocurri&oacute; un error al intentar terminar la solicitud <strong>' + $('#folioSolicitud').val() + '</strong>. Intenta nuevamente.');
					msgButton = 'Aceptar';
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
				text : 'Aceptar',
				click : function() {
					$(this).dialog('close');
					if(listener)
						listener();
				}
			}]);
			
			$('#mensajeDialogo').html(mensaje);
			dialogoConfirmar.dialog('open');
		}

	});

})();