function ReportesController(module) {
	this.module = module;
	this.model = {};
	this.ESTADO_ATENDIDA = "ATENDIDA";
	this.ESTADO_OPERADA = "OPERADA";
	this.ESTADO_OPERADAV = "OPERADA - VENCIDA";

}

ReportesController.prototype.cerrarModalFiltros = function() {
	module.service.removeModalSinFiltros();
};

ReportesController.prototype.cerrarNoAGeneradoUnReporte = function() {
	module.service.modalNoAGeneradoUnReporte();
};

ReportesController.prototype.mostrarDocumentoBeneficiario = function(index) {
	var documento = this.model.consultaSolicitud.gridDocumentosBeneficiario.data[index];
	if (!this.module.render.isEmpty(documento)) {
		document.forms['auxForm'].action = "atencionAutorizador/obtenerDocumento/"
				+ documento.idPersona
				+ "/"
				+ documento.folio
				+ "/"
				+ documento.extension
				+ "/"
				+ documento.nombreArchivo
				+ "/"
				+ documento.idDocBoveda;
		document.forms['auxForm'].submit();
	}
};

ReportesController.prototype.bandeja = function() {
	this.module.updateModel("CardLayoutComponent", "responsableCardLayout", 0);
	this.module.controller.model.paginaTramitesActualTramites = this.module.controller.model.gridTramites.currentPage;
	this.module.controller.model.paginaTramitesActualHistorico = this.module.controller.model.gridHistorico.currentPage;
	this.module.updateModel("FormPanelComponent", "filter", "filtros");
};

ReportesController.prototype.confirmar = function() {
	// Cerrar la modal, regresar a la bandeja y mostrar el mensaje de respuesta
	// del server
	$("#modalEnvioSindo").modal('show');
};

ReportesController.prototype.cancelar = function() {
	$('#cancelarModal').modal('show');
};

ReportesController.prototype.cancelarSolicitud = function() {
	$('#cancelarModal').modal('hide');
	this.module.service.cancelar();
};

ReportesController.prototype.checkMotivoAclaracion = function() {
	// Si esta seleccionado algun motivo de aclaracion se habilita el boton de
	// iniciar.
	var panel = this.module.render.getComponentById("panelTipoRegularizacion");
	var disabled = true;
	var i;
	for (i = 0; i < panel.components.length; i++) {
		if ($(
				"#"
						+ this.module.render.replaceAll(
								panel.components[i].field, ".", "_")).prop(
				'checked')) {
			disabled = false;
			break;
		}
	}
	$("#btnIniciar").prop('disabled', disabled);
};

ReportesController.prototype.pintar = function() {
	if (this.model.consultaSolicitud.estatus == this.ESTADO_ATENDIDA
			|| this.model.consultaSolicitud.estatus == this.ESTADO_OPERADA
			|| this.model.consultaSolicitud.estatus == this.ESTADO_OPERADAV) {
		return true;
	} else
		return false;
};

ReportesController.prototype.checkEstadoAutorizacion = function(data,
		componentId) {
	this.module.updateModel("CardLayoutComponent", "responsableCardLayout", 1);
	data.estadoAutorizacion = parseInt(data.estadoAutorizacion);
	var buttonCardIndex = 1;
	if (data.estadoAutorizacion == 1) {
		buttonCardIndex = 0;
	}
	this.module.updateModel("CardLayoutComponent",
			"autorizadorButtonsCardLayout", data.estadoAutorizacion);
	this.module.updateModel("CardLayoutComponent",
			"autorizadorCorreccionButtonsCardLayout", buttonCardIndex);
};

ReportesController.prototype.colorCampos = function() {
	// this.module.view.correccionDatosUI.colorCampos();
};
ReportesController.prototype.mostrarDiferentes = function() {
	// this.module.view.correccionDatosUI.mostrarSinCambios();
};
ReportesController.prototype.tamanioGrid = function() {
	// this.module.view.cambiosAutorizarUI.tamanioGrid();
};

ReportesController.prototype.tamanioGridConsultaSolicitud = function() {
	// this.module.render.componentTemplates['ConsultaSolicitudComponent'].tamanioGridConsultaSolicitud();
};

ReportesController.prototype.cambioTipoNSS = function() {
	// RN42 Tipo de NSS vs Tipo de Correccion
	var tipoNSS = $("#tipoCorreccion").val();
	this.module.updateModel("CardLayoutComponent", "correccionesCardLayout",
			tipoNSS);
	// Si se cambia el tipo de NSS se limpian las opciones de los checks
	var that = this;
	$
			.each(
					this.model.gruposCorreccionLabels,
					function(key, value) {
						var matches = $
								.find("#FormPanelComponent-correccionDatos :input[name='grupoCorreccion."
										+ key + "']");
						if (matches.length === 1) {
							if (matches[0].type === "checkbox") {
								$("#" + matches[0].id).prop('checked', false);
								$("#" + matches[0].id).change();
								if (!that.module.render
										.isEmpty(that.model.consultaSolicitud.gridNSS.data[that.model.currentNSSIndex].grupoCorreccion)) {
									that.model.consultaSolicitud.gridNSS.data[that.model.currentNSSIndex].grupoCorreccion[key] = false;
								}
							}
						}
					});
};

ReportesController.prototype.iniciar = function() {
	// Actualizar los tipos de regularizacion, si es correcto
	// mostrar la pantalla de inicio tramite

	this.module.validator.formToModel("FormPanelComponent-consultaSolicitud",
			this.model.consultaSolicitud);

	this.model.currentNSSIndex = 0;
	this.model.detalle = this.model.consultaSolicitud.gridNSS.data[this.model.currentNSSIndex];
	// this.module.view.correccionDatosUI.prepararGruposCorrecion();

	this.module.updateModel("CardLayoutComponent", "responsableCardLayout", 2);

};

ReportesController.prototype.regresarCorreccion = function() {

	this.module.updateModel("CardLayoutComponent", "responsableCardLayout", 0);
	$("#modal").modal('hide');
};

ReportesController.prototype.mostrarReportes = function() {
	// $("#modal").modal('show');
	// this.module.updateModel("CardLayoutCompongeneraReporteent","responsableCardLayout",
	// 10);
	window.location.href = contextPath
			+ "/wizard/correccionDatosAsegurado/datosAdicionalesHistoriaLaboral";
	// $("#modal").modal('hide');
};

ReportesController.prototype.siguienteCorreccion = function() {
	// Guardar el modelo los datos actuales de la forma antes de hacer el cambio
	this.module.validator
			.formToModel(
					"FormPanelComponent-correccionDatos",
					this.model.consultaSolicitud.gridNSS.data[this.model.currentNSSIndex]);

	if (this.model.consultaSolicitud.gridNSS.data[this.model.currentNSSIndex].tipoNSS.idTipoNSSCorreccion < 1) {
		this.module.updateModel("AlertComponent", "alert", {
			level : "danger",
			message : "Indique el tipo de NSS"
		});
		return;
	}
	// Validar que tenga alguna opcion seleccionada
	var valid = false;
	if (this.model.consultaSolicitud.gridNSS.data[this.model.currentNSSIndex].grupoCorreccion !== null
			&& typeof this.model.consultaSolicitud.gridNSS.data[this.model.currentNSSIndex].grupoCorreccion === 'object') {
		var that = this;
		$
				.each(
						this.model.consultaSolicitud.gridNSS.data[this.model.currentNSSIndex].grupoCorreccion,
						function(key, value) {
							if (that.model.consultaSolicitud.gridNSS.data[that.model.currentNSSIndex].grupoCorreccion[key] === true
									|| that.model.consultaSolicitud.gridNSS.data[that.model.currentNSSIndex].grupoCorreccion[key] === "true") {
								valid = true;
							}
						});
	}
	if (!valid) {
		this.module.updateModel("AlertComponent", "alert", {
			level : "danger",
			message : "Seleccione un tipo de correci\u00F3n"
		});
		return;
	} else {
		this.module.updateModel("AlertComponent", "alert", {
			level : "danger",
			message : ""
		});
	}

	if (this.model.consultaSolicitud.gridNSS.data.length > (this.model.currentNSSIndex + 1)) {
		this.model.currentNSSIndex++;
		this.model.detalle = this.model.consultaSolicitud.gridNSS.data[this.model.currentNSSIndex];
		this.module.render.notify("FormPanelComponent", "panelCorreccionDatos",
				"detalle");
	} else {
		this.model.resumenCorreccion = this.model.consultaSolicitud.gridNSS.data;
		// this.module.render.notify("ResumenCorreccionComponent",
		// "resumenCorreccion", "resumenCorreccion");
		this.module.render.notify("FormPanelComponent", "panelCorreccionDatos",
				"detalle");
		$('#confirmarModal').modal();
	}
};

// pjjt
ReportesController.prototype.iniciarCorreccion = function() {
	// FIX: Solo se usa el tipoNSS Correccion
	this.model.detalle.tipoNSS = "1";
	this.module.updateModel("CardLayoutComponent", "responsableCardLayout", 3);
	if (this.model.consultaSolicitud.gridNSS.data.length === (this.model.currentNSSIndex + 1)) {
		$("#btnSiguiente").html("Finalizar");
	}
};

ReportesController.prototype.selectTramite = function(index) {
    folio = this.module.controller.model.gridTramites.data[index].folio;
    document.location = "atencionAutorizador?folio=" + folio;

};

ReportesController.prototype.selectHistorico = function(index) {
	this.module.service.fetchSolicitudHistorico("consultaSolicitud",
			this.module, index);
	this.module.updateModel("AlertComponent", "alert", {
		level : "info",
		message : ""
	});
};

ReportesController.prototype.nuevaSolicitud = function() {

};

ReportesController.prototype.init = function() {
	this.model = {
		userProfile : {},
		tiposNSS : [ {
			key : "1",
			value : "Certificador"
		} ],
		tiposCorreccion : [ "Certificador", "Corresponde a otra persona",
				"Asociado al titular" ],
		gruposCorreccionLabels : {
			nombre : "Correcci\u00F3n de nombre",
			datosEstadisticos : "Correcci\u00F3n de datos estad\u00EDsticos",
			regularizarCuentaIndividual : "Regularizar cuenta individual",
			duplicidad : "Cancelado por duplicidad",
			noExisteCanase : "No existe en CANASE",
			homonimio : "Corresponde a un Hom\u00F3nimio",
			otroAsegurado : "Corresponde a otro asegurado"
		},
		cancelar : {},
		solicitarInformacion : {},
		reasignacion : {},
		rechazar : {}
	};
	// this.consultaSolicitudController.model = this.model;
	// this.reasignacionController.model = this.model;
	// this.solicitarInformacionController.model = this.model;
	// this.cambiosAutorizarController.model = this.model;
	// this.rechazarController.model = this.model;
	this.model.perfil = {};
	this.module.render.draw();
	this.module.service.getUserProfile(this.module);
	// this.cancelarController.model = this.model;
};

ReportesController.prototype.salir = function() {
	this.module.service.salir();
};
ReportesController.prototype.salirCancelar = function() {
	this.module.service.salirCancelar();
};

ReportesController.prototype.salirAceptar = function() {
	module.updateModel("UserProfileComponent", "userProfile", {});
	this.module.service.salirAceptar();
};

ReportesController.prototype.estatus = function(index) {
	this.module.service.fetchEstatus("bitacora", this.module, index);
	this.module.updateModel("AlertComponent", "alert", {
		level : "info",
		message : ""
	});
};

ReportesController.prototype.estatusHistorico = function(index) {
	this.module.service.fetchEstatusHistorico("bitacora", this.module, index);
	this.module.updateModel("AlertComponent", "alert", {
		level : "info",
		message : ""
	});
};

ReportesController.prototype.filtrosReportes = function() {
	$("#modalReporte").modal('show');
	var auxModel = {};

	$.validator.addMethod("validaFecha",function(value,element){
		
	});
	
  
  this.module.validator.formToModel("FormPanelComponent-filter", auxModel);

	var campoValido = this.module.validator.validForm({
		entity:"filter",
		name:"filter"
	});
	// console.log("campo valido"+campoValido);	
	
		if (campoValido) {

			this.module.service.fetchFiltros("gridTramites", this.module,
					1);
			this.module.updateModel("AlertComponent", "alert", {
				level : "info",
				message : ""
			});
			
//			module.updateModel( "GridComponent", gridId, data );
		}
	
};

ReportesController.prototype.selectVariable = function() {
	$("#modalReporte").modal('show');
	this.module.service.fetchEstadistica("gridReportes");
	this.module.service.fetchEstadistica("gridOrigen");
	//$('div.modal-backdrop.fade').remove();
};

ReportesController.prototype.obtenerReporte = function() {

	this.module.service.fetchGeneraPDF("gridReportes");
};

ReportesController.prototype.exportaExcel = function() {

	this.module.service.fetchGeneraXLS("gridReportes", this.model);
};

ReportesController.prototype.generaReporte = function() {
	if(filtro){
		$("#modalNoAGeneradoUnReporte").modal();
	}
	else{
		this.module.updateModel("CardLayoutComponent", "responsableCardLayout", 1);
	}
	
};

ReportesController.prototype.regresarGrid = function() {

	this.module.updateModel("CardLayoutComponent", "responsableCardLayout", 0);
};

ReportesController.prototype.finalRenderComponents = function() {
	//$("#curpBeneficiario").css('width', '80%');
};

ReportesController.prototype.siguientePersona = function() {
	var isLast = false;
	var isFirst = true;
	if (this.model.currentPersonaNSSIndex + 1 < this.model.detalle.informacionFuentesNSS.length) {
		this.model.currentPersonaNSSIndex++;
		if ($("#datosOriginales").prop('checked')) {
			this.model.detalle.informacionBDTU = this.model.detalle.informacionFuentesNSSXML[this.model.currentPersonaNSSIndex];
		} else
			this.model.detalle.informacionBDTU = this.model.detalle.informacionFuentesNSS[this.model.currentPersonaNSSIndex];
		this.module.render.notify("FormPanelComponent", "panelCorreccionDatos",
				"detalle");
		// this.module.updateModel("NavegacionPersonaNSSComponent","navegacionPersonaNSS",
		// "detalle");
		isFirst = false;
	}
	if (this.model.currentPersonaNSSIndex + 1 >= this.model.detalle.informacionFuentesNSS.length) {
		isLast = true;
	}
	$("#btnSiguientePersona").prop('disabled', isLast);
	$("#btnAnteriorPersona").prop('disabled', isFirst);
};

ReportesController.prototype.anteriorPersona = function() {
	var isLast = true;
	var isFirst = false;
	if (this.model.currentPersonaNSSIndex - 1 >= 0) {
		this.model.currentPersonaNSSIndex--;
		if ($("#datosOriginales").prop('checked')) {
			this.model.detalle.informacionBDTU = this.model.detalle.informacionFuentesNSSXML[this.model.currentPersonaNSSIndex];
		} else
			this.model.detalle.informacionBDTU = this.model.detalle.informacionFuentesNSS[this.model.currentPersonaNSSIndex];
		this.module.render.notify("FormPanelComponent", "panelCorreccionDatos",
				"detalle");
		// this.module.updateModel("NavegacionPersonaNSSComponent","navegacionPersonaNSS",
		// "detalle");
		isLast = false;
	}
	if (this.model.currentPersonaNSSIndex - 1 < 0) {
		isFirst = true;
	}
	$("#btnSiguientePersona").prop('disabled', isLast);
	$("#btnAnteriorPersona").prop('disabled', isFirst);
};

ReportesController.prototype.datosXML = function() {
	var form = {};
	if ($("#datosOriginales").prop('checked')) {
		this.model.detalle.informacionBDTU = this.model.detalle.informacionFuentesNSSXML[this.model.currentPersonaNSSIndex];
	} else
		this.model.detalle.informacionBDTU = this.model.detalle.informacionFuentesNSS[this.model.currentPersonaNSSIndex];

	if (this.module.render.isEmpty(this.model.detalle.datosOriginales)) {
		this.module.validator.formToModel("FormPanelComponent-correccionDatos",
				this.model.detalle);
	} else {
		this.module.validator.formToModel("FormPanelComponent-correccionDatos",
				form);
	}

	if (this.module.render.isEmpty(form.datosOriginales)
			|| form.datosOriginales != this.model.detalle.datosOriginales) {
		if (!this.module.render.isEmpty(form.datosOriginales)) {
			this.model.detalle.datosOriginales = form.datosOriginales;
		}
		this.module.render.notify("FormPanelComponent", "panelCorreccionDatos",
				"detalle");
	}
	// this.module.updateModel("NavegacionPersonaNSSComponent","navegacionPersonaNSS",
	// "detalle");
	if (this.model.currentPersonaNSSIndex == 0) {
		$("#btnAnteriorPersona").prop('disabled', true);
	}
	if (this.model.currentPersonaNSSIndex + 1 >= this.model.detalle.informacionFuentesNSS.length) {
		$("#btnSiguientePersona").prop('disabled', true);
	}
};

ReportesController.prototype.cancelarEnvioSindo = function() {
	this.module.service.cancelarEnvioSindo();
};

ReportesController.prototype.envioSindo = function() {
	this.model.confirmar = {
		folio : this.module.controller.model.consultaSolicitud.folio
	};
	this.model.confirmar.idTramite = this.module.controller.model.consultaSolicitud.idTramite;
	this.model.confirmar.idTarea = this.module.controller.model.consultaSolicitud.idTarea;
	this.model.confirmar.folio = this.module.controller.model.consultaSolicitud.folio;
	this.model.confirmar.fechaInicio = this.module.controller.model.consultaSolicitud.fechaInicio;
	this.model.confirmar.nombreCompletoAutorizador = this.module.controller.model.userProfile.nombreCompleto;
	this.model.confirmar.nss = this.module.controller.model.consultaSolicitud.nss;
	this.model.confirmar.informacionRENAPO = this.module.controller.model.consultaSolicitud.gridNSS.data[0].informacionRENAPO;
	this.module.service.confirmar();
};

ReportesController.prototype.clean = function() {
	window.location.reload(true);
	this.model.filtros = {};
	this.model.filtrosBusqueda = this.model.filtros;
	this.module.validator.formToModel("FormPanelComponent-filter",
			this.model.filtros);
	this.module.service.cleanFiltros("gridTramites", "gridHistorico",
			this.model);
	this.module.updateModel("AlertComponent", "alert", {
		level : "info",
		message : ""
	});
	window.location.reload(true);
};

ReportesController.prototype.disableNombre = function() {
	if ($("#foliosAsociados").prop('checked')) {
		$("#curp").val("");
		$("#curp").prop('disabled', true);
	} else {
		$("#curp").prop('disabled', false);
	}
};

ReportesController.prototype.disableFilter = function() {
	this.model.filtros = {};
	this.module.validator.formToModel("FormPanelComponent-filter",
			this.model.filtros);
	this.module.service.disableElement(this.model.filtros);
};

ReportesController.prototype.cargarSubdelegaciones = function() {

	var delegacion = $('#delegacionSelectField').val();
	if (delegacion != -1)
		this.module.service.fetchSubdelegacionCombo(delegacion, this.module,
				this.module);

};

ReportesController.prototype.cargarResponsables = function() {
	var delegacion = $('#delegacionSelectField').val();
	var subdelegacion = $('#subdelegacionSelectField').val();
	if (subdelegacion != -1) {
		this.module.service.fetchAutorizoCombo(delegacion, subdelegacion,
				this.module, this.module);
		this.module.service.fetchResponsableCombo(delegacion, subdelegacion,
				this.module, this.module);
	}

};