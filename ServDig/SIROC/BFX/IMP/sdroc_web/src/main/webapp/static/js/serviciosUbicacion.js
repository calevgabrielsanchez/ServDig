/**
 * Funciones para usar el componente de domicilio
 */
var ubicacionCtrl = {
	contextApp: '/sdroc_web',
	init: function() {
		if(parent.DomicilioCtrl) {
			parent.DomicilioCtrl.init("domiciliosComponent");
		} else {
			//alert("No fue posible iniciar el componente de doimicilio");
		}
	}, 
	setDomicilioSinSepomex: function() {
		var domicilio = this;
		jsonDomi = domicilio;
		ubicacionCtrl.setDomicilio(domicilio);
		navegacionCtrl.avanzar(3);
	},
	validarDelegacion: function() {
		var domicilio = this,
		codigoPostal = domicilio.codigoPostal.codigoPostal, 
		registroPatronal = $("#lblRegPatron").text();
		jsonDomi = domicilio;
		cargaEtiquetas();
		$("#pnlDomicilioFiscal").removeClass("hidden");
		//Seteamos el domicilio en el panel
		ubicacionCtrl.setDomicilio(domicilio);
		//se valida la correspondencia entre el CP y el RP
		ubicacionCtrl.validarCodigoPostal(registroPatronal, codigoPostal);
	},
	validarCodigoPostal: function(RP, CP) {
		$.blockUI();
		$.ajax({
			//url: ubicacionCtrl.contextApp + "/validaCodigoPostal/"+ RP + "/" + CP,
			url: ubicacionCtrl.contextApp + "/validarCodigoPostal/"+ CP,
			type: "GET",
			contentType : "application/json",
			cache : false,
			success: function(codigoPostalValido) {
				$.unblockUI();
				
				if(codigoPostalValido != null) {
					if(codigoPostalValido) {
						navegacionCtrl.avanzar(3);
					} else {
						ubicacionCtrl.mostrarMensajeErrorDomicilio(CP);
					}
				} else {
					ubicacionCtrl.errorValidarCP();
				}
			},
			error: function() {
				ubicacionCtrl.errorValidarCP();
			}
		})
	},
	errorValidarCP: function() {
		console.log("Entro a la funcion error al validar el cp");
		$('#errorCodigoPostalPatron').html("No fue posible validar el C&oacute;digo Postal, intenta mas tarde.");
		crearDialogo("#errorCodigoPostalPatron", {
			"Aceptar" : function() {
				$(this).dialog("close");
				parent.WizardRegistroObraCtrl.cerrar();
			}
		}, "Error");
	},
	setDomicilio: function(domicilio) {
		
		//mostramos el area donde se encuentra la direccion
		$("#pnlDatosUbicacionObra").removeClass("hidden");
		var cadenaDireccion = ubicacionCtrl.construyeDireccion(domicilio);
		//ponemos el rexto en el label indicado
		$("#lblUbicacionObraCap").text(cadenaDireccion.toUpperCase());
		
		return domicilio;
	}, 
	existeVariable: function(variable) {
		if(typeof variable === 'undefined' || variable == null || variable == "null") {
			return false;
		}
		return true;
	},
	construirJSONUbicacion: function(jsonDomicilio) {
		var jsonUbicacion = {};
		var colonia = jsonDomicilio.asentamiento.tipoAsentamiento.descripcion + ' ' + jsonDomicilio.asentamiento.nombre;
		var calle = "";
		if (typeof jsonDomicilio.vialidadPrimaria.tipoVialidad == 'undefined') {
			calle = jsonDomicilio.calle;
		} else {
			calle = jsonDomicilio.vialidadPrimaria.tipoVialidad.descripcion + ' ' + jsonDomicilio.vialidadPrimaria.nombre
		}
		jsonUbicacion = {
			calle : calle,
			numExterior : jsonDomicilio.numExterior1,
			codigoPostal : jsonDomicilio.codigoPostal.codigoPostal,
			refEntidad : jsonDomicilio.asentamiento.localidad.municipio.entidadFederativa.nombre,
			refMunicipio : jsonDomicilio.asentamiento.localidad.municipio.nombre,
			cveEntidad : jsonDomicilio.asentamiento.localidad.municipio.entidadFederativa.clave,
			cveMunicipio : jsonDomicilio.asentamiento.localidad.municipio.clave,
			cveLocalidad : jsonDomicilio.asentamiento.localidad.clave,
			refColonia : colonia.toUpperCase(),
			numExteriorAlf : jsonDomicilio.numExteriorAlf == null ? '' : jsonDomicilio.numExteriorAlf.toUpperCase(),
			numInterior : jsonDomicilio.numInterior == null ? 0 : jsonDomicilio.numInterior,
			numInteriorAlf : jsonDomicilio.numInteriorAlf == null ? '' : jsonDomicilio.numInteriorAlf.toUpperCase(),
			numExteriorDos : jsonDomicilio.numExterior2 == null ? 0 : jsonDomicilio.numExterior2,
			refObservacion : jsonDomicilio.descripcion == null ? '' : jsonDomicilio.descripcion.toUpperCase(),
			refPosterior : jsonDomicilio.vialidadReferenciaPosterior == null ? ''
					: jsonDomicilio.vialidadReferenciaPosterior.tipoVialidad.descripcion.toUpperCase() + ' ' + jsonDomicilio.vialidadReferenciaPosterior.nombre,
			refPrimaria : jsonDomicilio.vialidadReferenciaPrimaria == null ? ''
					: jsonDomicilio.vialidadReferenciaPrimaria.tipoVialidad.descripcion.toUpperCase() + ' ' + jsonDomicilio.vialidadReferenciaPrimaria.nombre,
			refSecundaria : jsonDomicilio.vialidadReferenciaSecundaria == null ? ''
					: jsonDomicilio.vialidadReferenciaSecundaria.tipoVialidad.descripcion.toUpperCase()
							+ ' ' + jsonDomicilio.vialidadReferenciaSecundaria.nombre .toUpperCase()

		};

		return jsonUbicacion;

	},
	construyeDireccion: function(domicilio) {
		var direccion = '';
		var colonia = '';
		
		if (domicilio.vialidadPrimaria == undefined) {
			direccion += domicilio.calle;
		} else {
			direccion += domicilio.vialidadPrimaria.nombre;
		}
		
		direccion += ' Ext.'
			+ (!ubicacionCtrl.existeVariable(domicilio.numExterior) ? '' : ' ' + domicilio.numExterior)
			+ (!ubicacionCtrl.existeVariable(domicilio.numExteriorAlf) ? 
					(!ubicacionCtrl.existeVariable(domicilio.numExterior) ? ' SN': '') : ' ' + domicilio.numExteriorAlf)
			+ ' Int.'+ (!ubicacionCtrl.existeVariable(domicilio.numInterior) || domicilio.numInterior == '0' ? '' : ' ' + domicilio.numInterior)
			+ (!ubicacionCtrl.existeVariable(domicilio.numInteriorAlf) ? 
					(!ubicacionCtrl.existeVariable(domicilio.numInterior) || domicilio.numInterior == '0' ? ' SN': '')
					: ' ' + domicilio.numInteriorAlf) + ', '
		
		if (domicilio.vialidadPrimaria == undefined) { // ubicacionDTO
			colonia = domicilio.refColonia;
			direccion += domicilio.refColonia.toUpperCase() + ', ' + domicilio.refMunicipio
					+ ', ' + domicilio.refEntidad + ',  CP ' + domicilio.codigoPostal;
		} else {
			direccion += domicilio.asentamiento.tipoAsentamiento.descripcion.toUpperCase()
					+ ' '+ domicilio.asentamiento.nombre.toUpperCase()
					+ ', '+ domicilio.asentamiento.localidad.municipio.nombre.toUpperCase()
					+ ', '+ domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre
					+ ', CP ' + domicilio.codigoPostal.codigoPostal;
		}
		
		return direccion;
	},
	mostrarMensajeErrorDomicilio: function(codigoPostal) {
		var url = "/sdroc_web/obtenerSubDelegacionPorCodigoPostal/" + codigoPostal;
		$.blockUI();
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : url,
			cache : false,
			success : function(response) {
				$.unblockUI();
				$('#desSubdelegacionText').text($('#idDesSubDelegacion').val().toLowerCase());
				$('#listaSubdelegacionesText').text(response);
				crearDialogo("#errorCodigoPostalPatron", {
					"Aceptar" : function() {
						$(this).dialog("close");
						parent.WizardRegistroObraCtrl.cerrar();
					}
				}, "Error");
			}
		});

	}
}

var navegacionCtrl = {
	avanzar: function(paso){
		console.log("voy a avanzar al paso " + paso)
		var curStep = $("#step-"+paso);
		navegacionCtrl.avanzarPasoActual(curStep, false, null);
	},
	avanzarPasoActual: function($pasoActual, validarCampos, funcionOK) {
		var curStep = $pasoActual, 
		curStepBtn = curStep.attr("id"), 
		nextStepWizard = $('ul.wizard-steps-extensive li a[href="#'+ curStepBtn+ '"]').parent().next().children("a"), 
		curInputs = curStep.find("input[type='text'],input[type='url']"), 
		isValid = true;
		
		if(typeof validarCampos !== 'undefined' && validarCampos) {
			$(".form-group").removeClass("has-error");
			for (var i = 0; i < curInputs.length; i++) {
				if (!curInputs[i].validity.valid) {
					isValid = false;
					$(curInputs[i]).closest(".form-group").addClass("has-error");
				}
			}
		}

		if(isValid) {
			
			if(typeof funcionOK !== 'undefined' && $.isFunction(funcionOK)) {
				console.log("Voy a ejecutar la funcion") ;
				funcionOK();
			}
			nextStepWizard.removeAttr('disabled').trigger('click');
	
			var stepLi = curStepBtn + '0';
			$("#"+ stepLi).children('a').remove();
			$("#"+ stepLi).addClass("completed");
		}
	}
}


