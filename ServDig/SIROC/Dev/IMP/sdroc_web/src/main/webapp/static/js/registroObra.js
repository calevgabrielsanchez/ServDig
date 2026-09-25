/*
 *Fecha UM    : 07 de Octubre del 2021
 *Version UM  : 3.7
 *Autor UM    : Erika Gutierrez
 *Descripcion : Cambios para el mantenimiento SIROC para tratamiento de patrones con amparo
 */
var registroObraCtrl = {
	procesarFirma: function(firmaResponse) {
		var accion = "/sdroc_web/datosFirma";

		$.blockUI();
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : accion,
			data : JSON.stringify(firmaResponse),
			cache : false,
			success : function(response) {
				$.unblockUI();
				registroObraCtrl.guardarDatos();
			}
		});
	},
	guardarDatos: function() {
		// validacion de datos requeridos
		if ($("#passCvePrivada").val() == '') {
			$("#mesgError").removeClass("hidden");
		} else {
			$("#mesgError").addClass('hidden');
			registroObraCtrl.jsonRegistroObras();
		}
	},
	descripcionPatron: function(cvePatron) {
		var descripcion = "";
		switch (cvePatron) {
			case 1:
				descripcion = "Propietario";
				break;
			case 2:
				descripcion = "Contratista";
				break;
			case 3:
				descripcion = "Subcontratista";
				break;
			case 4:
				descripcion = "Intermediario";
				break;
			case 5:
				descripcion = "Subcontratista de obra especializada";
				break;
		}
		return descripcion;
	},
	jsonRegistroObras: function() {

		var url = "/sdroc_web/registrarObra";
		if (tipoObra == 1) {
			cveTipoObra = 74;
			desTipoObra = 'Privada';
		} else {
			cveTipoObra = 75;
			desTipoObra = 'Pubica';
		}
		var desTipoPatron = registroObraCtrl.descripcionPatron(parseInt(tipoPatron,10)),
		cveSubDelegacion = $("#idCveSubDelegacion").val(),
		cveDelegacion = $("#idCveDelegacion").val(),
		desSubDelegacion = $("#idDesSubDelegacion").val(),
		desDelegacion = $("#idDesDelegacion").val(),
		numRegistro = Math.floor((Math.random() * 50000000) + 1),
		numRegistroPrincipal = 0;
		
		if ($("#idCveRegistroObraPrincipal").val() != '') {
			numRegistroPrincipal = $("#idCveRegistroObraPrincipal").val();
		}
		

		var refApellidoPaterno = $("#idApellidoPaterno").val(),
		refApellidoMaterno = $("#idApellidoMaterno").val(),
		nomPatron = $("#idNombre").val(),
		cveRfc = $("#lblRFCPatron").text(),
		cveRegPatronal = $("#lblRegPatron").text(),
		refRazonSocial = $("#lblRazonSocialPatron").text()
		numLicitacion = null;
		// var numLicitacion = $("#txtNumAviso").val();
		// if(numLicitacion == '' || numLicitacion == null){
		// numLicitacion = 0;
		// }

		var desObjetoContratoSubEsp = $("#txtObjetoContratoSubEsp").val();

		var cveObjetoContrato = $("#selObjetoContrato").val(),
		desObjetoContrato = $("#selObjetoContrato option:selected").text();
		if (cveObjetoContrato == '' || cveObjetoContrato == null || cveObjetoContrato == '0') {
			cveObjetoContrato = 1;
		}

		var refOtroObjetoContrato = $("#otroObjetoContrato").val()
		var impEjercido = 0,
		impContratado = 0,
		rfcSinNumReg = $("#rfcSinNumReg").val();

		if (rfcSinNumReg != '' && rfcSinNumReg != null) {
			$("#txtAcuseReg").html('Aviso de Ubicaci&oacute;n de Obra');
			$("#txtExitoReg").html('Aviso de Ubicaci&oacute;n de Obra exitoso.');
			$("#txtGeneraReg").html('Aviso de Ubicaci&oacute;n de Obra generado correctamente.');
			
			var intTipoPatron = parseInt(tipoPatron,10)
			if(intTipoPatron == 2){
				$("#msgAvisoContratista").removeClass("hidden");
			}else if(intTipoPatron == 3 || intTipoPatron == 5){
				$("#msgAvisoSubcontratista").removeClass("hidden");
			}else if(intTipoPatron == 4){
				$("#msgAvisoIntemediario").removeClass("hidden");
			}
			
		} else {
			$("#txtAcuseReg").text('Acuse de registro de la obra');
			$("#txtExitoReg").text('Registro de obra exitoso.');
			$("#txtGeneraReg").text('Registro de obra generado correctamente.');
			
		}
		// var texRazonSocial = $('#texRazonSocial').val();

		var fecInicio = $("#fecInicio").val(),
		dateIni = null;
		if (fecInicio != '') {
			var partsIn = fecInicio.split('/');
			dateIni = new Date(partsIn[2],parseInt(partsIn[1],10) - 1, partsIn[0]);
		}
		var fecTermino = $("#fecTermino").val(),
		dateFin = null;
		if (fecTermino != '') {
			var partsFin = fecTermino.split('/');
			dateFin = new Date(partsFin[2],	parseInt(partsFin[1],10) - 1, partsFin[0]);
		}
		var selecTipoObra = $("#selTipoObra").val();
		var desSelecTipoObra = $("#selTipoObra option:selected").text();

		if (selecTipoObra == undefined || selecTipoObra == null || selecTipoObra == 0) {
			selecTipoObra = cveTipoObra;
		}

		var numAviso = 0;

		if ($("#txtNumAviso").val() != "") {
			numAviso = $("#txtNumAviso").val();
		}
		

		var monto = $("#txtMonto").val().replace(/,/g, '');
		if (monto == null || monto == '') {
			monto = '0';
		}
		var superficie = $("#txtSuperficie").val().replace(/,/g, '');
		if (superficie == null || superficie == '') {
			superficie = '0';
		}
		// variables subcontratista especializado
		var numAproxTrabajadores = $("#txtNumTrabajadores").val().replace(/,/g, '');
		if (numAproxTrabajadores == null || numAproxTrabajadores == '') {
			numAproxTrabajadores = '0';
		}

		var numRegStps = $("#txtNumRegSTPS").val();

		var numProcedimiento = null;
		// BRHG ModificaciÃ³n para asignar el valo rde 0 al
		// procedimiento por que ya es alfanumerico.
		if ($("#txtNumProcedimiento").val() != undefined
				&& $("#txtNumProcedimiento").val() != null
				&& $("#txtNumProcedimiento").val() != '') {
			numProcedimiento = $("#txtNumProcedimiento").val();
		} else if ($("#txtNumAviso").val() != undefined
				&& $("#txtNumAviso").val() != null
				&& $("#txtNumAviso").val() != '') {
			numProcedimiento = $("#txtNumAviso").val();
		}
		

		var commentario = $("#comment").val(),
		passCvePrivada = $("#passCvePrivada").val(),
		cadenaOriginal = "",
		cveTipoPersona = $("#idCveTipoPersona").val(),
		jsonDomiFinal = {};

		jsonDomiFinal = registroObraCtrl.validaDomicilio();


		cadenaOriginal = "||Invocante:portalimssdigital%NOMBRE_TRAMITE%|%FECHA_ACTUAL%"
		cadenaOriginal = cadenaOriginal + "%NUM_REG_OBRA%" + "|RFC: ";
		
		if (rfcSinNumReg != '') {
			cadenaOriginal = cadenaOriginal + rfcSinNumReg;
		} else {
			cadenaOriginal = cadenaOriginal + cveRfc;
		}
		cadenaOriginal = cadenaOriginal + "|Nombre o razÃ³n social: ";
		cadenaOriginal = cadenaOriginal + refRazonSocial + "|";
		

		if (cveTipoPersona == 2) {
			cadenaOriginal = cadenaOriginal + "CURP: " + $("#idCurp").val() + "|";
		}
		cadenaOriginal = cadenaOriginal + "Registro patronal: "	+ cveRegPatronal + "||";
		var rpAmparo = $("#patronAmparo").val();
		datosRegObra = {
			subDelegacionDTO : {
				cveSubdelegacion : cveSubDelegacion,
				nomSubdelegacion : desSubDelegacion,
				cveCodigo : 0,
				delegacionDTO : {
					cveDelegacion : cveDelegacion,
					nomDelegacion : desDelegacion
				}
			},
			cveRegistroObra : numRegistro,
			cveRegistroAvisoObra : numRegistro,
			cveRegistroObraPrincipal : numRegistroPrincipal,
			cveRfcPatron : rfcSinNumReg,
			tipoObraDTO : {
				cveTipoObra : selecTipoObra,
				desTipoObra : desSelecTipoObra,
				clasificacionObraDTO : {
					cveClasificacionObra : tipoObra,
					desClasificacionObra : desTipoObra
				}
			},
			informacionPatronDTO : {
				tipoPatronDTO : {
					cveTipoPatron : tipoPatron,
					desTipoPatron : desTipoPatron
				},
				tipoPersonaDTO : {
					cveTipoPersona : cveTipoPersona
				},
				cveRfc : cveRfc,
				cveRegPatronal : cveRegPatronal,
				refApellidoPaterno : refApellidoPaterno,
				refApellidoMaterno : refApellidoMaterno,
				nomPatron : nomPatron,
				refRazonSocial : refRazonSocial
			},
			fecIniObra : dateIni,
			fecFinObra : dateFin,
			impObra : parseFloat(monto),
			refSupConstruccion : parseFloat(superficie),
			refObservacion : commentario,
			numProcedimiento : numProcedimiento,
			numLicitacion : numLicitacion,
			fecIniContrato : dateIni,
			fecFinContrato : dateFin,
			desObjetoContratoSubEsp : desObjetoContratoSubEsp,
			objetoContratoDTO : {
				cveObjetoContrato : cveObjetoContrato,
				desObjetoContrato : desObjetoContrato
			},
			refOtroObjetoContrato : refOtroObjetoContrato,
			numAproxTrabajadores : parseFloat(numAproxTrabajadores),
			numRegStps : numRegStps,
			impEjercido : parseFloat(impEjercido),
			impContratado : parseFloat(impContratado),
			refAcuseReg : passCvePrivada,
			refCveAvisoObra: numRegistro,
			ubicacionObraDTO : jsonDomiFinal,
			estatusObraDTO : {
				cveEstatusObra : 1
			},
			refCadenaOriginal : cadenaOriginal,
			indRegPatAmparo : rpAmparo
		};

		$("#selTipoObra").val(0);
		var resulta = registroObraCtrl.enviarController(url, datosRegObra);
		return resulta;

	},
	enviarController: function(accion, data) {
		var resultado = true;
		$.blockUI();
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : accion,
			async : false,
			data : JSON.stringify(data),
			timeout : 100000,
			cache : false,
			success : function(response) {
				$('#lblEjemplo').text(response);
				$('#pnlRepAcuse').attr("src",'/sdroc_web/getRegistroObraPDF');
				navegacionCtrl.avanzar(4);
				$.unblockUI();
			},
			error : function(response) {
				$.unblockUI();
				$('#errorCodigoPostalPatron').html(response.responseText);
				crearDialogo("#errorCodigoPostalPatron", {
					"Aceptar" : function() {
						$(this).dialog("close");
						parent.WizardRegistroObraCtrl.cerrar();
					}
				}, "Error");
			}
		});

		return resultado;
	},
	validaDomicilio: function() {
		// var jsonDomi = {};
		if ((tipoObra == 1 && tipoPatron == "1") || (tipoObra == 2 && tipoPatron == "2")) {
			jsonDomi = ubicacionCtrl.construirJSONUbicacion(jsonDomi);
		} else if ((tipoObra == 1 && tipoPatron == "2" && $(
				'#chkSinRegistro').is(":checked"))
				|| (tipoObra == 1 && tipoPatron == "3" && $(
						'#chkSinRegistro').is(":checked"))
				|| (tipoObra == 1 && tipoPatron == "4" && $(
						'#chkSinRegistro').is(":checked"))
				|| (tipoObra == 1 && tipoPatron == "5" && $(
						'#chkSinRegistro').is(":checked"))
				|| (tipoObra == 2 && tipoPatron == "3" && $(
						'#chkSinRegistro').is(":checked"))
				|| (tipoObra == 2 && tipoPatron == "4" && $(
						'#chkSinRegistro').is(":checked"))
				|| (tipoObra == 2 && tipoPatron == "5" && $(
					'#chkSinRegistro').is(":checked"))) {
			jsonDomi = ubicacionCtrl.construirJSONUbicacion(jsonDomi);
		} else {
			jsonDomi = {
				refObservacion : $('#commentUbicacion').val()
			};
		}

		return jsonDomi;
	},
	avanzarATipoPatron: function() {

		if (tipoObra > 0) {	

			var url = "/sdroc_web/categoriasObras";

			$.ajax({
					type : "POST",
					contentType : "application/json",
					url : url,
					data : JSON.stringify(tipoObra),
					timeout : 100000,
					success : function(response) {
							var dataArray = response.resultado;
							var selectbox = $('#selTipoObra');
							var populateSelectBox = function(selectbox,dataArray) {
								dataArray.forEach(function(data) {
											selectbox.append('<option value="'+ data.cveTipoObra+ '">'+ data.desTipoObra+ '</option>');
										});
							};

							populateSelectBox(selectbox,dataArray);
						},
						error : function(response) {
							console.log("ERROR: " + response);

						}
					});

			var pasoActual = $(this).closest(".setup-content-default");

			navegacionCtrl.avanzarPasoActual(pasoActual, true, function() {

				crearDialogo("#tipoObraModalRespse", {
					"Aceptar" : function() {$(this).dialog("close")}
				}, "Confirmaci&oacute;n", "340px");
				$("#idMensajeModalRespse").removeClass("hidden");

				crearDialogo("#tipoObraModal", {
					"Aceptar" : function() {$(this).dialog("close")}
				}, "Confirmaci&oacute;n", "340px");
				$("#idMensajeModal").removeClass("hidden");
				
				var isRPC = $("#patronRPC").val() == 1;
				console.log("El patron es RPC ? " + isRPC);
				if (tipoObra == 1) {
					$("#pnlPropietario")[!isRPC ? "removeClass" : "addClass"]("hidden");
					$("#lblClaseObra").text('Privada');
				} else if (tipoObra == 2) {
					$("#pnlPropietario").addClass("hidden");
					$("#lblClaseObra").text('Publica');
				}
				
				$("#pnlClaseObra").removeClass("hidden");
				$("#pageResumenDatosObra").removeClass("hidden");
				
				$("#pnlContratista")[!isRPC ? "removeClass" : "addClass"]("hidden");
				$("#pnlSubcontratista")[!isRPC ? "removeClass" : "addClass"]("hidden");

			});
			
		} else {
			$("#msgObra").removeClass("hidden");
		}

	},
	eventoUbicacionObra: function() {
		var datosVal = true, datosValNum = true, 
		rfcSinNumReg = $("#rfcSinNumReg").val(), 
		varNumRegistro = $("#txtNumRegistro").val();
		
		$("#pnlDomicilioFiscal").removeClass("hidden");
		
		if ($('#chkSinRegistro').is(":checked")) {
			if (rfcSinNumReg == '') {
				$("#msgRFC").removeClass("hidden");
				$("#rfcSinNumReg").css('border-color','#a94442');
				$("#btnUbicacion").addClass("hidden");

				if (tipoPatron == "2") {
					$("#idContraRFC").removeClass("hidden");
					$("#idSubContraRFC").addClass("hidden");
					$("#idInterRFC").addClass("hidden");

				} else if (tipoPatron == "3") {
					$("#idContraRFC").addClass("hidden");
					$("#idSubContraRFC").removeClass("hidden");
					$("#idInterRFC").addClass("hidden");
				} else if (tipoPatron == "4") {
					$("#idContraRFC").addClass("hidden");
					$("#idSubContraRFC").addClass("hidden");
					$("#idInterRFC").removeClass("hidden");
				}
				datosVal = false;
			}
		} else {
			if ((tipoObra == 1 && (tipoPatron == "2" || tipoPatron == "3" || tipoPatron == "4" || tipoPatron == "5"))
				 || (tipoObra == 2 && (tipoPatron == "3" || tipoPatron == "4" || tipoPatron == "5"))) {
				if (varNumRegistro == '') {
					$("#msgNumRegistro").removeClass("hidden");
					$("#txtNumRegistro").css('border-color','#a94442');
					$("#btnUbicacion").addClass("hidden");
					datosValNum = false;
				} else {
					$("#pnlDatosUbicacionObra").removeClass("hidden");
					var cadenaRegObra = $("#lblUbicacionObraRes").text();
					$("#lblUbicacionObraCap").text(cadenaRegObra.toUpperCase());
				}
			} else {
				$("#pnlDatosUbicacionObra").removeClass("hidden");
				var direccionTmp = ubicacionCtrl.construyeDireccion(jsonDomi);
				$("#lblUbicacionObraCap").text(direccionTmp.toUpperCase());
			}
		}

		var cpValidar = $('#cpValidar').val(),
		$pasoActualFuncion = $(this).closest(".setup-content");
		console.log("Datos val " + datosVal + " datosvalnum " + datosValNum + " Antes de entras");
		
		if (cpValidar != null && cpValidar != "") {
			var cveRegPatronal = $("#lblRegPatron").text();
			console.log("El tipo de patron es " + tipoPatron + " ");

			if(tipoPatron != TIPO_PATRON.INTERMEDIARIO || $("#patronRPC").val() != 1) {
				console.log("voy a validar la ubicacion ya que no es RPC");
				ubicacionCtrl.validarCodigoPostal(cveRegPatronal, cpValidar);
			}

		}

		if (datosVal && datosValNum) {
			cargaEtiquetas();

			var pasoActual = $(this).closest(".setup-content");
			navegacionCtrl.avanzarPasoActual(pasoActual);
		}
	},
	eventoValidarRegObra: function() {
		
		$("#msgValAlfanum").addClass("hidden");

		if ($("#txtNumRegistro").val() == '') {
			if ($('#chkSinRegistro').is(":checked")) {
				$("#txtNumRegistro").css('border-color', '#ccc');
			} else {
				$("#msgSinSeleccion").removeClass("hidden");
				$("#txtNumRegistro").css('border-color','#a94442');
				$("#btnUbicacion").addClass("hidden");
			}

		} else {
			if (/^[A-Za-z0-9]+$/.test($("#txtNumRegistro").val())) {

				var numRegObra = $("#txtNumRegistro").val();
				if (numRegObra.length == 8) {
					$("#msgLongitudIncorrecta").addClass("hidden");
					$("#txtNumRegistro").css('border-color','#ccc');

					var accion = "/sdroc_web/consultaPorNumReg";
					$.ajax({
						type : "POST",
						contentType : "application/json",
						url : accion,
						data : numRegObra,
						timeout : 100000,
						success : function(e) {
							if (e.resultado == null) {
								$("#pnlNumRegObra").removeClass("hidden");
								$("#datosObraGenerales").addClass("hidden");
								$("#txtObervacionesUbicacion").addClass("hidden");
								$("#pnlDatosObraRegistrado").addClass("hidden");
								$("#btnUbiContratista").addClass("hidden");
								$("#msgSinCoincidencias").removeClass("hidden");
								$("#msgSinCoincidencias").html(e.mensaje);
								$("#btnUbicacion").addClass("hidden");
								$('#lblMsgObraNE').css("border-color","#a94442");
								$('#cpValidar').text("");

							} else {
								
								$("#btnUbicacion").removeClass("hidden");
								$("#btnUbicacion").html("Validar Ubicaci&oacute;n");
								$("#pnlDatosObraRegistrado").removeClass("hidden");
								$("#datosObraGenerales").removeClass("hidden");
								$("#txtObervacionesUbicacion").removeClass("hidden");
								$('#lblMsgObraNE').text('');
								$("#pnlDatosObraNoEncontrado").addClass("hidden");
								$("#btnUbiContratista").removeClass("hidden");
								$("#idCveRegistroObraPrincipal").val(e.resultado.cveInformacionObra);
								$("#idCveRegistroObraPrincipal").text(e.resultado.cveInformacionObra);
								$('#cpValidar').val(e.resultado.ubicacionObraDTO.codigoPostal);
								var direccionTmp = ubicacionCtrl.construyeDireccion(e.resultado.ubicacionObraDTO);
								$('#lblUbicacionObraRes').text(direccionTmp.toUpperCase());
								$('#commentUbicacion').text('');
							}

							$("#msgNumRegistro").addClass("hidden");
							$("#txtNumRegistro").css('border-color','#ccc');
						},
						error : function(e) {
							console.log("ERROR: ",e);
						}
					});
				} else {
					$("#msgLongitudIncorrecta").removeClass("hidden");
					$("#txtNumRegistro").css('border-color','#a94442');
				}

			} else {
				$("#msgValAlfanum").removeClass("hidden");
				$("#msgNumRegistro").addClass("hidden");
				$("#msgErrorAjax").addClass("hidden");
				$("#msgSinSeleccion").addClass("hidden");
				$("#msgSinCoincidencias").addClass("hidden");
				$("#msgFormatoIncorrecto").addClass("hidden");
				$("#msgLongitudIncorrecta").addClass("hidden");
			}

		}
	},
	validarRFC: function() {
		$("#msgRFC").addClass("hidden");
		var rfcPatron = $("#lblRFCPatron").text().toUpperCase(),
		rfcCapturado = $("#rfcSinNumReg").val().toUpperCase();
		
		console.log("El rfc capturado es: " + rfcCapturado + " y el rfc del patron es " + rfcPatron);
		
		if(rfcPatron == rfcCapturado) {
			$('#errorCodigoPostalPatron').html("El RFC capturado no puede ser igual al del solicitante. Debe de capturar el RFC del patr&oacute;n que lo contrat&oacute;");
			crearDialogo("#errorCodigoPostalPatron", {
				"Aceptar" : function() {
					$(this).dialog("close");
				}
			}, "Error");
		} else if (rfcCapturado != 'IMS421231I45') {

			var accion = "/sdroc_web/validaRfc/"+ rfcCapturado;
			$.ajax({
				type : "GET",
				contentType : "application/json",
				async : false,
				url : accion,
				timeout : 100000,
				cache : false,
				success : function(response) {
					
					$("#rfcSinNumReg").css('border-color','#ccc');
					$("#msgRFC").addClass("hidden");
					$("#btnUbicacion").removeClass("hidden");
					$("#lblRazonSocial").removeClass("hidden");
					$("#btnValidarRFC").removeClass("btn-primary");
					$("#btnValidarRFC").addClass("btn-default");
					$("#btnUbicacion").addClass("hidden");
					
					if (response == '') {
						$("#rfcSinNumReg").css('border-color','#a94442');
						$("#msgRFC").removeClass("hidden");
						$("#lblRazonSocial").addClass("hidden");
						$("#btnUbicacion").addClass("hidden");
						
						if (tipoPatron == "2") {
							$("#idContraRFC").removeClass("hidden");
							$("#idSubContraRFC").addClass("hidden");
							$("#idInterRFC").addClass("hidden");
						} else if (tipoPatron == "3") {
							$("#idContraRFC").addClass("hidden");
							$("#idSubContraRFC").removeClass("hidden");
							$("#idInterRFC").addClass("hidden");
						} else if (tipoPatron == "4") {
							$("#idContraRFC").addClass("hidden");
							$("#idSubContraRFC").addClass("hidden");
							$("#idInterRFC").removeClass("hidden");
						}
					} else {
						cargaEtiquetas();
						parent.DomicilioCtrl.localizar();
					}

				},
				error : function(response) {
					$("#rfcSinNumReg").css('border-color','#a94442');
					$("#msgRFC").removeClass("hidden");
					$("#lblRazonSocial").addClass("hidden");
					$("#btnUbicacion").addClass("hidden");
					
					if (tipoPatron == "2") {
						$("#idContraRFC").removeClass("hidden");
						$("#idSubContraRFC").addClass("hidden");
						$("#idInterRFC").addClass("hidden");
						
					} else if (tipoPatron == "3") {
						$("#idContraRFC").addClass("hidden");
						$("#idSubContraRFC").removeClass("hidden");
						$("#idInterRFC").addClass("hidden");
					} else if (tipoPatron == "4") {
						$("#idContraRFC").addClass("hidden");
						$("#idSubContraRFC").addClass("hidden");
						$("#idInterRFC").removeClass("hidden");
					}
				}
			});

		}
	}
}
