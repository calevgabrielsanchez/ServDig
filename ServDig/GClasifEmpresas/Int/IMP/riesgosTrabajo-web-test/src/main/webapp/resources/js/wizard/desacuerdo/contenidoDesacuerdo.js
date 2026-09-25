 /**
 * 
 */
var registroDesacuerdoCtrl = {
	materia: 2,
	causa:3,
	capturaMotivo: 1,
	contextApp: '/${mvn.web.app.root}',
	origenApp: '${mvn.web.app.origin.id}',
	intervalId: null,
	init: function() {
		$(".radioMateria").on("click",{attr:"materia"},registroDesacuerdoCtrl.setCombo);
		$(".capturaMotivo").on("change",{attr:"capturaMotivo"},registroDesacuerdoCtrl.setCombo);
		if($("#guardarTramite").length) {
			$("#guardarTramite").on("click",registroDesacuerdoCtrl.guardarTramite);
		}
		$("#finalizarTramite").on("click", registroDesacuerdoCtrl.finalizar);
		$("#componenteBoveda").boveda({
			tipoTramite: 155, 
			idTramite: $("#tramiteId").val(), 
			folio: $("#noFolioSolicitud").val(),
			rutaBoveda: "/rtt",
			tipoDocumental: "D:RTT:escrito_desacuerdo"
		});
		$("#toolTipMotivo").tooltip();
		funcionesComunes.init();
		registroDesacuerdoCtrl.initValidator();
		registroDesacuerdoCtrl.procesarRetomar();
	},
	procesarRetomar: function() {
		var retomando = $("#retomandoSolicitud").val() == "1";
		if(retomando) {
			$.blockUI();
			$.postJSON(registroDesacuerdoCtrl.contextApp + "/escrito/wizard/retomar", null, function(data) {
				var escritoDesacuerdo = data.tramite, $formulario = $("#formEscrito");
				
				if(escritoDesacuerdo) {
					$formulario.find("#tramiteId").val(escritoDesacuerdo.tramiteId);
					
					if(escritoDesacuerdo.mail != null) {
						$formulario.find("#mail").val(escritoDesacuerdo.mail);
					}
					
					if(escritoDesacuerdo.capturaMotivo != null) {
						registroDesacuerdoCtrl.capturaMotivo = escritoDesacuerdo.capturaMotivo;
						$formulario.find("#capturaMotivo"+escritoDesacuerdo.capturaMotivo).prop("checked",true).change();
						if(escritoDesacuerdo.capturaMotivo) {
							$formulario.find("#motivoDesacuerdo").val(escritoDesacuerdo.motivoDesacuerdo);
						} else {
							registroDesacuerdoCtrl.intervalId = setInterval(registroDesacuerdoCtrl.verificarBoveda, 1000);
						}
					}
					
					if(escritoDesacuerdo.causaDesacuerdo) {
						var materiaDes = escritoDesacuerdo.causaDesacuerdo.materiaDesacuerdo.idMateria,
						causaDesacuerdo = escritoDesacuerdo.causaDesacuerdo.idCausaDes;
	
						if(materiaDes == 1){
							$formulario.find("#radioClasificacion").prop("checked",true).click();
						} else {
							$formulario.find("#materiaDeterminacion").val(causaDesacuerdo)
						}
					}
					
					$formulario.find("#folioImpugnado").val(escritoDesacuerdo.folioImpugnado);
					
				}
				$.unblockUI();
			}).error(function(data){
				console.log("ocurrio un error al retomar");
			});
		}
	},
	verificarBoveda: function() {
		if(typeof bovedaCtrl !== "undefined" && bovedaCtrl != null) {
			//console.log("ya existe el componente de boveda");
			if(bovedaCtrl.documentosTramite.length || bovedaCtrl.documentosTramiteCapturados.length) {
				//console.log("ya se cargaron los documentos")
				$("#componenteBoveda").boveda("optional",1639, registroDesacuerdoCtrl.capturaMotivo);
				//console.log("voy a limpiar el intervalo " + registroDesacuerdoCtrl.intervalId)
				clearInterval(registroDesacuerdoCtrl.intervalId);
			} /*else {
				console.log("aun no de cargan los documentos")
			}*/
			
		} /*else {
			console.log("aun no existe el componente de boveda");
		}*/
	},
	setCombo: function(event) {
		var attr = event.data.attr;
		registroDesacuerdoCtrl[attr] = parseInt(this.value,10);
		if(registroDesacuerdoCtrl[attr+"Change"])registroDesacuerdoCtrl[attr+"Change"]();
	},
	materiaChange: function() {
		var $folioResolucion = $("#folioImpugnado"),
		materiaSeleccionada=registroDesacuerdoCtrl.tiposMaterias[registroDesacuerdoCtrl.materia-1];
		
		$folioResolucion.val("");
		registroDesacuerdoCtrl.causa=materiaSeleccionada.causaDefault;
		materiaSeleccionada.activar();
		$folioResolucion.attr("placeHolder",materiaSeleccionada.formatoFolio);
		$folioResolucion.attr("maxlength",materiaSeleccionada.maxlength);
	},
	initValidator: function() {

		jQuery.validator.addMethod("folioImpugnado", function(value, element) {
			expresionRegular = registroDesacuerdoCtrl.tiposMaterias[registroDesacuerdoCtrl.materia-1].regExp;
			return this.optional( element ) || expresionRegular.test( value );
		}, 'Formato del folio inv&aacute;lido.');
				
		console.log("inicio el validador");
		
		$("#formEscrito").validate({
			errorClass: "errorDocs",
			errorElement: "span",
			rules: { 
				"causaDesacuerdo.materiaDesacuerdo.idMateria": {
					required:true
				}, 
				"causaDesacuerdo.idCausaDes": {
					min: {
						param:1,
						depends: function(element) {
							return $("#radioMateria").is(":checked")
						}
					}
				},
				"folioImpugnado": {
					required: true,
					folioImpugnado: true
				},
				"mail": {
					email: true,
					maxlength: 50
				},
				"motivoDesacuerdo": {
					required: {
						depends: function(element) {
							return $("#capturaMotivo1").is(":checked")
						}
					},
					maxlength: 2500
				}
			}, 
			messages: {
				"causaDesacuerdo.idCausaDes": {min: MENSAJE_CAMPO_OBLIGATORIO}
			}
		});
	},
	tiposMaterias: [{
		name: "clasificacion",
		formatoFolio: "CE-NN-NN-NN/NN/NNNN/NNNN",
		maxlength: 24,
		regExp: /CE\-\d{2}\-\d{2}\-\d{2}\/\d{2}\/\d{4}\/\d{4}/,
		causaDefault: 4,
		activar: function() {
			registroDesacuerdoCtrl.mostrarDivs("hide","show");
		}
	},{
		name: "prima",
		formatoFolio: "NN/NN-NNNNN",
		maxlength: 11,
		regExp: /\d{2}\/\d{2}\-\d{5}/,
		causaDefault: 3,
		activar: function() {
			$("#materiaDeterminacion").val(registroDesacuerdoCtrl.causa);
			$("#materiaDeterminacion").trigger("click");
			registroDesacuerdoCtrl.mostrarDivs("show","hide");
		}
	}],
	capturaMotivoChange: function() {
		$("#divMotivoDesacuerdo")[registroDesacuerdoCtrl.capturaMotivo ? "show" : "hide"]();
		if(typeof bovedaCtrl !== "undefined" && bovedaCtrl != null) {
			$("#componenteBoveda").boveda("optional",1639, registroDesacuerdoCtrl.capturaMotivo);
		}
		$("#motivoDesacuerdo").val("");
	},
	mostrarDivs: function(divMateria, divClas) {
		$("#divMateria")[divMateria]();
		$("#divClasificacion")[divClas]();
	},
	finalizar: function() {
		var $formulario = $("#formEscrito");
		if($formulario.valid() & $("#componenteBoveda").boveda("valid")) {
			if(registroDesacuerdoCtrl.origenApp == "1") {
				var opciones = {
						titulo: 'Confirmaci&oacute;n requerida',
						mensaje:"Se proceder&aacute; con el registro del escrito, estas seguro de continuar?",
						buttons: {
							'No': dialogosCtrl.close, 
							'Si': function() {
								$(this).dialog('close');
								registroDesacuerdoCtrl.procesarFinalizado(true);
							}
						}
				};

				dialogosCtrl.abrirDialogo(opciones);
			} else {
				registroDesacuerdoCtrl.invocarFirma();
			}
		}
	},
	invocarFirma: function() {
		var componenteFirma = {
			rfc: parent.FirmanteCtrl.rfc,
			curp: parent.FirmanteCtrl.curp,
			fechaElectronica : new Date(),
			cad_original:$('#contenidoFirmar').val(),
			registroPatronal : $("#spanNRP").text(),
			nombreCompleto : parent.FirmanteCtrl.nombreRazonSocial,
			idTipoSolicitud : 59,
			descripcionTipoSolicitud : "ESCRITO DE DESACUERDO",
			idTipoTramite : [155]
		};
		
		if(typeof componenteFirmaElectronica !== "undefined") {
			componenteFirmaElectronica.callback = registroDesacuerdoCtrl.procesarFirmado;
			componenteFirmaElectronica.firmarTramite(componenteFirma);
		}
	},
	procesarFirmado: function(responseFirma) {
		$.postJSON(registroDesacuerdoCtrl.contextApp + "/escrito/wizard/procesarDatosFirma", responseFirma, function(data) {
			registroDesacuerdoCtrl.procesarFinalizado(true);
		}).error(function(data){
			registroDesacuerdoCtrl.dialogos.errorFinalizar.mensaje=data.mensaje;
			dialogosCtrl.abrirDialogo(registroDesacuerdoCtrl.dialogos.errorFinalizar);
		});
	},
	guardarTramite: function() {
		registroDesacuerdoCtrl.procesarFinalizado(false);
	},
	procesarFinalizado: function(finalizar) {
		var escrito = $("#formEscrito").toObject(),
		urlOperacion = registroDesacuerdoCtrl.contextApp + "/escrito/wizard/" + (finalizar ? "finalizar" : "guardar") + "Tramite";
		if(escrito.causaDesacuerdo.materiaDesacuerdo.idMateria == 1) {
			escrito.causaDesacuerdo.idCausaDes = 4;
		}
		
		$.blockUI();
		$.postJSON(urlOperacion, escrito, function(data) {
			if(data.correcto) {
				if(finalizar) {
					var folioSolicitud = data.solicitud.noFolioSolicitud,
					folioRecepcion = data.solicitud.tramites[0].folioRecepcion;
					console.log("el folio de la solicitud es: " + folioSolicitud + " y el folio de recepcion es " + folioRecepcion);
					if(registroDesacuerdoCtrl.origenApp == "1") {
						location.href = registroDesacuerdoCtrl.contextApp + "/escrito/wizard/tramiteFinalizado";
					} else {
						$.unblockUI()
						var opcionesMensaje = registroDesacuerdoCtrl.dialogos.tramiteFinalizado;
						opcionesMensaje.mensaje +="Folio solicitud: <strong>"  + folioSolicitud + "</strong><br>";
						opcionesMensaje.mensaje +="Folio recepci&oacute;n: <strong>"  + folioRecepcion + "</strong><br>";
						dialogosCtrl.abrirDialogo(opcionesMensaje);
					}
				} else {
					$.unblockUI()
					dialogosCtrl.abrirDialogo(registroDesacuerdoCtrl.dialogos.guardado);
				}
			} else {
				$.unblockUI()
				console.log("ocurrio un error al finalizar el tramite");
				registroDesacuerdoCtrl.dialogos.errorFinalizar.mensaje=data.mensaje;
				dialogosCtrl.abrirDialogo(registroDesacuerdoCtrl.dialogos.errorFinalizar);
			}
		}).error(function(){ 
			$.unblockUI()
			console.log("ocurrio un error al finalizar el tramite");
			dialogosCtrl.abrirDialogo(registroDesacuerdoCtrl.dialogos.errorFinalizar);
		});
	},
	dialogos: {
		"guardado": {
			titulo: 'Guardado',
			mensaje:"Los cambios han sido guardados con exito.",
			buttons: {
				'Aceptar': function() {
					$(this).dialog('close');
				}
			}
		},
		"errorFinalizar": {
			titulo: 'Error',
			mensaje:"Ocurri&oacute; un error al finalizar el tr&aacute;mite, intentalo mas tarde.",
			buttons: {
				'Aceptar': funcionesComunes.procesarSalirTramite
			}
		},
		"tramiteFinalizado": {
			titulo: 'Solicitud finalizada',
			mensaje: 'Tu solicitud ha finalizado correctamente.<br>',
			buttons: {
				'Aceptar': funcionesComunes.procesarSalirTramite,
				'Ver documentos': funcionesComunes.verDocumentos
			}
		}
	}
};

$(document).ready(registroDesacuerdoCtrl.init);