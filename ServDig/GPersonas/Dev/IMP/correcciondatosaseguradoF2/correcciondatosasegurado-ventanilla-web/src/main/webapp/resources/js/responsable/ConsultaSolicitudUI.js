function ConsultaSolicitudUI(module) {
	this.module = module;
	this.init();
}

ConsultaSolicitudUI.prototype.init = function() {
	this.metadata = {
		ui : {
			components : [ {
				type : "FormPanelComponent",
				label : "<h4>Informaci\u00F3n de la solicitud<h4>",
				level : 4,
				id : "panelConsultaSolicitudComponent",
				name : "consultaSolicitud",
				model : "consultaSolicitud",
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
            { type:"ConsultaSolicitudButtonGroupComponent", model:"consultaSolicitud.idEstadoSolicitud", position:"top", id:"tb1" },
						
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
            { type:"ConsultaSolicitudButtonGroupComponent", model:"consultaSolicitud.idEstadoSolicitud", position:"bottom", id:"tb2" },
            /*
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
						},*/
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