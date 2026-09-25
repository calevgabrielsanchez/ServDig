function ConsultaSolicitudUI(module) {
	this.module = module;
	this.init();
}

ConsultaSolicitudUI.prototype.inhabilitarControles = function() {
	var checks = this.metadata.ui.components[0].components[4].components;
	for (var i = 0; i < checks.length; i++) {
		$("#" + this.module.render.replaceAll(checks[i].field, ".", "_")).prop(
				"disabled", true);
	}
};

ConsultaSolicitudUI.prototype.tamanioBotonesSolicitud = function() {
	// Cambiar el tamanio de botones barra
	$("#btnCancelar").prop('style',"height:100%;width:16.66%;white-space: normal;");
	$("#btnNssDocumentos").prop('style',"height:100%;width:16.66%;white-space: normal;");
	$("#btnSolicitarInformacion").prop('style',"height:100%;width:16.66%;white-space: normal;");
	$("#btnGenerar").prop('style', "height:100%;width:16.66%;white-space: normal;");
	$("#btnConsultaPrevio").prop('style',"height:73.4333px;width:16.66%;white-space: normal;");
	$("#btnDescargaComprobante").prop('style',"height:100%;width:16.66%;white-space: normal;");
};

ConsultaSolicitudUI.prototype.init = function() {
	this.metadata = {
		ui : {
			components : [ {
				type : "FormPanelComponent",
				label : "Informaci\u00F3n de la solicitud",
				level : 4,
				id : "panelConsultaSolicitudComponent",
				name : "consultaSolicitud",
				model : "consultaSolicitud",
				postFetch : "postFetchConsultaSolUI",
				components : [
						{
							type : "TextFieldComponent",
							field : "folio",
							label : "Folio",
							disabled : true,
							labelStyle : true
						},
						{
							type : "TextFieldComponent",
							field : "estatus",
							label : "Estado",
							disabled : true,
							labelStyle : true
						},
						{
							type : "TextFieldComponent",
							field : "fechaInicio",
							label : "Fecha de solicitud",
							disabled : true,
							labelStyle : true
						},
						{
							type : "TextFieldComponent",
							field : "subDelegacion.descripcion",
							label : "Subdelegaci\u00f3n",
							disabled : true,
							labelStyle : true
						},
						{
							type : "PanelComponent",
							collapsed : false,
							label: "",
				            classNameHR: "",
							id : "panelTipoRegularizacion",
							components : [ 
									{// BOTONES ACCIONES SOBRE SOLICITUDES
										type : "CardLayoutComponent",
										id : "responsableAccionesButtonsCardLayout",
										components : [
												{// index 0 : [ Consulta previa, Descargar comprobante]  
													type : "ButtonGroupComponent",
													components : [
																{
																	type : "ButtonComponent",
																	id : "btnConsultaPrevio",
																	label : "Consulta previo",
																	command : "consultaSolicitudController.iniciar", // Verificar funcionalidad
																	className : "btn-default"
																},
																{
																	type : "ButtonComponent",
																	id : "btnDescargaComprobante",
																	command : "bandeja", // Cambia
																	label : "Descargar Comprobante",
																	className : "btn-default"
																}]
												},{// index 1 : [ Cancelar, NSS Documentos,Solicitar info, Consulta previa, Descargar comprobante] 
													type : "ButtonGroupComponent",
													components : [
																{
																	type : "ButtonComponent",
																	id : "btnCancelar",
																	label : "Cancelar solicitud",
																	className : "btn-danger",
																	command : "consultaSolicitudController.cancelar"
																},
																{
																	type : "ButtonComponent",
																	id : "btnNssDocumentos",
																	label : "Agregar NSS y Documentos",
																	className : "btn-default",
																	command : "consultaSolicitudController.cancelar" // nueva// funcionalidad
																},
																{
																	type : "ButtonComponent",
																	id : "btnSolicitarInformacion",
																	label : "Solicitar informaci\u00F3n",
																	command : "consultaSolicitudController.solicitarInformacion",
																	className : "btn-default"
																},
																{
																	type : "ButtonComponent",
																	id : "btnConsultaPrevio",
																	label : "Consulta previo",
																	command : "consultaSolicitudController.iniciar", // Verificar
																	className : "btn-default"
																},{
																	type : "ButtonComponent",
																	id : "btnDescargaComprobante",
																	command : "bandeja", // Cambia
																	label : "Descargar Comprobante",
																	className : "btn-default"
																}]
												},{// index 2 : [ Certificación, Consulta previa, Descargar comprobante] 
													type : "ButtonGroupComponent",
													components : [
																{
																	type : "ButtonComponent",
																	id : "btnGenerar",
																	command : "consultaSolicitudController.generar", 
																	label : "Obtener Certificaci\u00F3n",
																	className : "btn-default"
																},
																{
																	type : "ButtonComponent",
																	id : "btnConsultaPrevio",
																	label : "Consulta previo",
																	command : "consultaSolicitudController.iniciar", // Verificar
																	className : "btn-default"
																},{
																	type : "ButtonComponent",
																	id : "btnDescargaComprobante",
																	command : "bandeja", // Cambia
																	label : "Descargar Comprobante",
																	className : "btn-default"
																}]
												}]
									}],
							layout : [ [ {span : 12} ] ]
						},
						{
				              type: "PanelComponent",
				              collapsed: false,
				              label: "",
				              classNameHR: "",
				              id: "panelSeparacion",
				              components: [  ],
				              layout: [[{span: 12}]]	
				        },
						{
							type : "ConsultaSolicitudComponent",
							model : "solicitud"
						},
						{
							type : "CardLayoutComponent",
							id : "responsableButtonsCardLayout",
							components : [
									{// index 0 : [ Salir ]  
										type : "ButtonGroupComponent",
										components : [
												{
													type : "ButtonComponent",
													command : "bandeja",
													label : "Salir",
													className : "btn-danger"
												}]
									},{// index 1 : [ Salir , Continuar ]
										type : "ButtonGroupComponent",
										components : [
												{
													type : "ButtonComponent",
													command : "bandeja",
													label : "Salir",
													className : "btn-danger"
												},
												{
													type : "ButtonComponent",
													id : "btnIniciar",
													label : "Continuar",
													command : "consultaSolicitudController.iniciar",
													className : "btn-primary"
												}]
									},{// index 2 : [ Salir , Consultar Cambios]
										type : "ButtonGroupComponent",
										components : [
												{
													type : "ButtonComponent",
													command : "bandeja",
													label : "Salir",
													className : "btn-danger"
												},
												{
													type : "ButtonComponent",
													label : "Consultar cambios",
													command : "consultaSolicitudController.iniciar",
													className : "btn-primary"
												} ]
									}]
						},
						{
							type : "ModalComponent",
							title : "Confirmar cancelaci\u00F3n",
							id : "cancelarModal",
							size : "modal-lg",
							body : {
								type : "PanelComponent",
								components : [ {
									type : "LabelComponent",
									label : "Bla bla bla"
								} ],
								layout : [ [ {
									span : 12
								} ] ]
							},
							footer : {
								type : "PanelComponent",
								components : [
										{
											type : "LabelComponent"
										},
										{
											type : "ButtonComponent",
											className : "btn-primary",
											label : "Aceptar",
											command : "consultaSolicitudController.cancelarSolicitud"
										} ],
								layout : [ [ {
									span : 8
								}, {
									span : 4
								} ] ]
							}
						} ],
				layout : [ [ {
					span : 5
				}, {
					span : 4
				}, {
					span : 5
				}, {
					span : 6
				} ], [ {
					span : 12
				} ], [ {
					span : 12
				} ], [ {
					span : 12
				} ], [ {
					span : 12
				} ] ]
			} ],
			layout : [ [ {
				span : 12
			} ] ]
		}
	};
};