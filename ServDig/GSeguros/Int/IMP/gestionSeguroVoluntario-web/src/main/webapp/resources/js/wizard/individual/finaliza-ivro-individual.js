
(function() {

	var idPersonaGlobal;
	var urlPathParam = null;
	var _WizardDetalleSeguroCtrl;

	$(document).ready(function() {

		_WizardDetalleSeguroCtrl = parent.WizardDetalleSeguroCtrl;

		$("#cerrarWizard").click(function() {
			parent.WizardSeguroIvroIndivCtrl.cerrar();
		});
		$("#terminarTramite").click(function() {
			terminatramite();
		});

		$("#cancelarTramite").click(function() {
			DialogoConfirmIVROPersonalCancel.dialog('open');
		});

		var terminatramite = function () {
			if(isInternet) {
				muestraMensajeFirma();
			} else {
				validaDomicilio();



			}
		};

		var DialogoConfirmIVROPersonalCancel = $('#cancelar-dialogo' ).dialog({
			autoOpen: false,
			modal: true,
			buttons: {
				"ACEPTAR": function() {
					$( this ).dialog( "close" );
					$.blockUI();
					var url = '/${mvn.web.app.root}/wizard/individual/cancelaTramite';

					$.postJSON(url, {},function(data) {
						$.unblockUI();
						mostrarMensajeConfirm();
					});
			 	},
			 	"CANCELAR": function() {
			 		$( this ).dialog( "close" );
			 		return false;
			 	}
			 }
		});

		var DialogoConfirmIVROPersonalConfirm = $( "#confirmar-dialogo" ).dialog({
				resizable: false,
				height:'auto',
				modal: true,
				dialogClass: "no-close",
			    closeOnEscape: false,
				autoOpen: false
		});

		function mostrarMensajeConfirm() {
			DialogoConfirmIVROPersonalConfirm.dialog("option", "buttons", [ {
				text : 'ACEPTAR',
				click : function() {
					$(this).dialog('close');
					parent.WizardSeguroIvroIndivCtrl.cerrar();
				}
			}]);
			DialogoConfirmIVROPersonalConfirm.dialog('open');
		}

		// FINALIZAR TRAMITE
		var obtenerClaveSeguroFinalizado = function(param){
			//console.log("obtenerClaveSeguroFinalizado" + idPersonaGlobal);
			var url = urlPathParam + '/${mvn.web.app.root}/wizard/individual/obtenerClaveSeguro/'+idPersonaGlobal;
			prepararRequest(url, null, true, muestraDetalleSeguro);
		};

		var muestraDetalleSeguro = function (idSeguro) {

			if(idSeguro!=undefined && idSeguro!=null){
				//console.log("muestraDetalleSeguro " + idSeguro);
				//WizardDetalleSeguroCtrl.setIdSeguro(idSeguro);

				console.log("************************** Inicia obtenerLcSipare");
				obtenerLcSipare(idSeguro);
				parent.construirDialogo("#dialogoMensajes",
						"Mensaje de sistema", "El detalle de sus seguros se est\u00e1 procesando. Se recomienda cerrar las ventanas e ingresar nuevamente para verificar sus seguros", true,
						undefined, undefined, 250, 400);
				console.log("************************** Termina obtenerLcSipare");
				/*
				 * Se comenta seccion para no mostrar el detalle del seguro cuando finaliza un tramite
				 * _WizardDetalleSeguroCtrl.init("wizardDetalleSeguroComponent",idSeguro);
				 *_WizardDetalleSeguroCtrl.abrir();
				*/
			}else{
				console.log("Se presento un problema al mostrar detalle de seguro");
			}
		};

		var obtenerLcSipare = function(idSeguro) {
			var liga = '/${mvn.web.app.root}/wizard/detalle/seguro/finalizarSeguroDetalle/obtenerLcSipare/';
				$.ajax({
					url : liga + idSeguro,
					dataType : 'json',
					success : function(response) {
						console.log('Respondio obtenerLcSipare');
						$.unblockUI();
					},
					error : function(error) {
						$.unblockUI();
						var msgError = error.msgError;
						if (typeof msgError === 'undefined') {
							msgError = 'Ocurri\u00f3 un error inesperado al enviar correo.';
						}
						parent.construirDialogo("#dialogoMensajes",
								"Mensaje de sistema", msgError, true,
								undefined, undefined, 250, 400);
					}
				});
		}

		var finalizaTramite = function (firma){

			$.blockUI();
			$("#error-guardarSolicitud").hide();
			var url = '/${mvn.web.app.root}/wizard/individual/terminaTramite';
			$.postJSON(url, firma,function(data) {
				$.unblockUI();
				if(data.errorFormGeneral) {
					$("#mensajeErrorSolicitud").val(data.errorFormGeneral);
					$("#error-guardarSolicitud").show();
				} else {
					idPersonaGlobal = data.tramite[0].beneficiarios[0].idPersona;
					urlPathParam = getPathUrl();
					parent.ProcesandoSolicitudCtrl.setBeforeCloseCallback(obtenerClaveSeguroFinalizado);
					parent.ProcesandoSolicitudCtrl.abrir(data.numSolicitud);
					parent.WizardSeguroIvroIndivCtrl.cerrar();
				}
			}).error (function () {
				$.unblockUI();
				console.log("Error al generar la peticion");
			});
		};

		var retornoValidarDom = function (solicitud) {

			console.log("solicitud " + solicitud);
			$.unblockUI();
				console.log("solicitud.errorFormGeneral " + solicitud.errorFormGeneral );
				if(solicitud.errorFormGeneral == "SIN DOMICILIO") {
					$('#otraUbicacionForm').submit();
				}else{
					console.log("va a finalizar el tramite" );
					finalizaTramite({});
				}
		};
		function validaDomicilio(){

			var url = '/${mvn.web.app.root}/wizard/individual/validaDomicilio';
			console.log("url " + url);
			$.blockUI();
			$("#error-guardarSolicitud").hide();

			prepararRequest(url, null, false, retornoValidarDom)
		};

		function prepararRequest(sSource, data, async, callback) {
			var request = $.ajax({
				url : sSource,
				async : async,
				type : "POST",
				data : data ? JSON.stringify(data) : null,
				dataType : "json",
				contentType : "application/json; charset=utf-8"
			});
			request.done(callback);
			request.fail(callback);
		}

		function getPathUrl(){
		    var loc = window.location;
		    var url = "" + loc.protocol + "//" + loc.host;
		    return url;
		}
		/////////////////////////////////////////FIRMA DEL TRAMITE

		function muestraMensajeFirma() {
			dialogoConfirmar.dialog("option", "buttons", [ {
				text : 'ACEPTAR',
				click : function() {
					$(this).dialog('close');
					invocarFirmaDigital();
				}
			}, {
				text : 'CANCELAR',
				click : function() {
					$(this).dialog('close');
				}
			}]);

			dialogoConfirmar.dialog('open');
		}
		var dialogoConfirmar = $( "#dialog-confirm" ).dialog({
			resizable: false,
			height:'auto',
			modal: true,
			dialogClass: "no-close",
		    closeOnEscape: false,
			autoOpen: false
		 });

		var invocarFirmaDigital = function() {

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

						finalizaTramite(firmaResponse);
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
				cad_original:datosEntradaFirma.cadenaOriginal,
				registroPatronal : "",
				nombreCompleto : parent.FirmanteCtrl.nombreRazonSocial,
				idTipoSolicitud : datosSol.idTipoSolicitud,
				descripcionTipoSolicitud : datosSol.descripcionTipoSolicitud,
				folioSolicitud : datosSol.folioSolicitud,
				idTipoTramite : datosSol.idTipoTramite,
				afectado: [{curp: parent.FirmanteCtrl.curp,
					        nombreRazonSocial: parent.FirmanteCtrl.nombreRazonSocial,
					        rfc:parent.FirmanteCtrl.rfc}]
			};

			parent.iniciarFirmaDigital(componenteFirma);
		};

	});

})();