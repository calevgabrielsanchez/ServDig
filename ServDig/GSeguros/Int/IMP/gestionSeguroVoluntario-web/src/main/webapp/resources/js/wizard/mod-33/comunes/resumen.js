var dialogoConfirmarCancelar;

(function() {

	$.getScript('/${mvn.web.app.root}/static/resources/js/wizard/mod-33/comunes/common.js');
	$.getScript('/${mvn.web.app.root}/static/resources/js/wizard/comunes/TCCuestionarioControl.js');


	var dialogoConfirmar;
	var cancelarDesdeBoton = false;
	var idPersonaGlobal;
	var nssCifradoGlobal;
	var _wizardAltaSeguroFamiliarCtrl = null;
	var invocarFirmaDigital;
	var terminosAceptados = false;

	$(document).ready(function() {

		_wizardAltaSeguroFamiliarCtrl = parent.WizardAltaSeguroFamiliarCtrl;
		
		$('#chkTCCuestionario').change(function() {
			terminosAceptados = $(this).prop('checked');
		});
		
		$('#linkTCCuestionario').click(function() {
			//dialogIVROTCCuestionario.dialog('open');
			parent.CartaTerminosCtrl.init('divComponentCommon', 124);
		    parent.CartaTerminosCtrl.abrir();
		});

		$('#cancelarSolicitud').click(function(event) {
			event.preventDefault();
			cancelarDesdeBoton = true;
			dialogoConfirmarCancelar.dialog('open');
		});

		$('#siguientePaso').click(function(event) {
			event.preventDefault();

			if(terminosAceptados) {
				firmarTramite(null);
			} else {
				mostrarMensaje('Para continuar debe aceptar los T\u00e9rminos y Condiciones', null);
			}
			
		});

		$('#salirSolicitud').click(function(event) {
			event.preventDefault();
			if (ventanilla && tieneSeguros)
				$('#formBackToVentanillaMain').submit();
			else
				closeWizard();
		});

		$('#tblTrabajadores').dataTable({
			'bFilter' : false,
			'bDestroy' : true,
			'bLengthChange' : false,
			'bAutoWidth' : false,
			'bPaginate' : false,
			'bInfo' : false,
			'aoColumnDefs' : [ {
				'sSortDataType' : 'html',
				'sType' : 'html',
				'aTargets' : [ 0 ]
			} ],
			'aoColumns' : [ {
				'sWidth' : '20%'
			}, {
				'sWidth' : '40%'
			}, {
				'sWidth' : '20%'
			}, {
				'sWidth' : '20%'
			} ]
		});

		dialogoConfirmarCancelar = $('#dialog-confirm-cancelar').dialog({
			resizable : false,
			height : 'auto',
			modal : true,
			autoOpen : false,
			dialogClass : 'no-close',
			closeOnEscape : false,
			buttons : {
				'ACEPTAR' : function() {
					cancelable = false;
					$(this).dialog("close");
					
					$.ajax('/${mvn.web.app.root}/wizard/seguroFamiliar/comunes/cancelarSolicitud',{
						complete : function() {
							if (cancelarDesdeBoton && ventanilla && tieneSeguros) {
								$('#formBackToVentanillaMain').submit();
							} else {
								closeWizard();
							}
						}
					});
				},
				'CANCELAR' : function() {
					$(this).dialog("close");
				}
			}
		});

		dialogoConfirmar = $('#dialog-confirm').dialog({
			resizable : false,
			height : 'auto',
			modal : true,
			dialogClass : "no-close",
			closeOnEscape : false,
			autoOpen : false
		});
	});

	function mostrarFirmarTramite() {
		mostrarMensaje(
				"A continuaci&oacute;n se te solicitar&aacute; el ingreso de tu FIEL para firmar digitalmente el tr&aacute;mite.",
				invocarFirmaDigital);
	}

	invocarFirmaDigital = function() {

		parent.FirmaDigitalCtrl.setOnCloseCallback(function() {
			if (parent.FirmaDigitalCtrl.datosSalida == null) {
				mostrarMensaje("La validaci&oacute;n de la firma no pudo ser realizada");
			} else {
				if (parent.FirmaDigitalCtrl.datosSalida.Resultado == 0) {
					var firmaResponse = {
						cadenaOriginal : parent.FirmaDigitalCtrl.datosSalida.contenedores[0].cadori,
						recibo : parent.FirmaDigitalCtrl.datosSalida.firmas[0],
						reciboNotarial : parent.FirmaDigitalCtrl.datosSalida.folio,
						urlAcuseFirma : parent.FirmaDigitalCtrl.datosSalida.acuse,
						serialCertificado : parent.FirmaDigitalCtrl.datosSalida.serie_cert,
						strIniciaVigenciaCertificado : parent.FirmaDigitalCtrl.datosSalida.vigIni,
						strFinVigenciaCertificado : parent.FirmaDigitalCtrl.datosSalida.vigFin
					};

					firmarTramite(firmaResponse);
				} else {
					mostrarMensaje("La validaci&oacute;n de la firma no pudo ser realizada");
				}
			}
		});

		var componenteFirma = {
			tipo_operacion : 'firmaCMS',
			acuse : 'CDCT',
			tipoAcuse : '1',
			rfc : parent.FirmanteCtrl.rfc,
			validarRFC : true,
			curp : parent.FirmanteCtrl.curp,
			firma_archivo : false,
			min_archivos : 0,
			max_archivos : 0,
			fechaElectronica : datosEntradaFirma.fechaElectronica,
			cad_original : $('#contenidoFirmar').val(),
			registroPatronal : "",
			nombreCompleto : parent.FirmanteCtrl.nombreRazonSocial,
			idTipoSolicitud : codigoTipoSolicitud,
			descripcionTipoSolicitud : descripcionTipoSolicitud,
			folioSolicitud : $('#folioSolicitud').val(),
			idTipoTramite : arrayCodigoTipoTramite,
			afectado : [ {
				curp : parent.FirmanteCtrl.curp,
				nombreRazonSocial : parent.FirmanteCtrl.nombreRazonSocial,
				rfc : parent.FirmanteCtrl.rfc
			} ]
		};

		parent.iniciarFirmaDigital(componenteFirma);
	};

    function ejecutaProcesarDatos(callback,firmaResponse) {
        var url = '/${mvn.web.app.root}/wizard/seguroFamiliar/comunes/procesar-datos-firma';
        cancelable = false;
        var temp = $.postJSON(url, firmaResponse, function(data) {
            console.log("Procesando datos...");
            callback(data);
        });
    }

	function firmarTramite(firmaResponse) {
		$.blockUI();

		cancelable = false;
		
		ejecutaProcesarDatos(function (data) {
			$.unblockUI();
			var msgButton;
			var buttons = [];
			if (data.error === undefined) {
				cancelable = false;
				idPersonaGlobal = data.solicitud.tramite[0].persona.idPersona;
				nssCifradoGlobal = data.solicitud.tramite[0].persona.nssCifrado;
				/*
				 * Se settea callback del componente de
				 * procesando, se utiliza el mismo
				 * WizardAltaSeguroFamiliarCtrl, ya que este
                 * componente por si mismo ya valida si existe
                 * seguro y si es asi muestra el detalle
				 */
				parent.ProcesandoSolicitudCtrl.setOnSolicitudExitosaCallback(function() {
					_wizardAltaSeguroFamiliarCtrl.setDatos(idPersonaGlobal, nssCifradoGlobal);
                    _wizardAltaSeguroFamiliarCtrl.setMuestraWizard(true);
					_wizardAltaSeguroFamiliarCtrl.abrir();
				});
				parent.ProcesandoSolicitudCtrl.abrir(data.solicitud.numSolicitud);
				closeWizard();
			} else {
				$('#mensajeDialogo').html(
					'Ocurri&oacute; un error al intentar terminar la solicitud <strong>'
							+ $('#folioSolicitud').val()
							+ '</strong>. Intenta nuevamente.');
				
				msgButton = 'ACEPTAR';
			}
			buttons.push({
				text : msgButton,
				click : function() {
					$(this).dialog('close');
					if (data.error === undefined) {
						closeWizard();
					}
				}
			});

			dialogoConfirmar.dialog("option", "buttons", buttons);

			dialogoConfirmar.dialog('open');
        },firmaResponse).error(function(data) {
			mostrarMensaje(data.mensaje);
		});
	}

	function mostrarMensaje(mensaje, listener) {
		dialogoConfirmar.dialog("option", "buttons", [ {
			text : 'ACEPTAR',
			click : function() {
				$(this).dialog('close');
				if (listener)
					listener();
			}
		} ]);

		$('#mensajeDialogo').html(mensaje);
		dialogoConfirmar.dialog('open');
	}

})();