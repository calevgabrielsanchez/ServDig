function ConsultaSolicitudUI(module) {
  this.module = module;
  this.init();
}

ConsultaSolicitudUI.prototype.init = function () {
  this.metadata = {
    ui: {
      components: [
        {
          type: "FormPanelComponent",
          label : "Informaci\u00F3n de la solicitud",
		  level : 4,
          id: "panelConsultaSolicitudComponent",
          name: "consultaSolicitud",
          model: "consultaSolicitud",
          components: [            
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
            /*
            {
              type: "PanelComponent",
              collapsed: false,
              label: "",
	          classNameHR: "",
              id: "panelTipoRegularizacion",
              components: [                
                {// BOTONES ACCIONES SOBRE SOLICITUDES
										type : "CardLayoutComponent",
										id : "autorizadorAccionesButtonsCardLayout",
										components : [
												{// index 0 : [ Consulta previa]   
													type : "ButtonGroupComponent",
													direction: "left",
													components : [
																	{
																		type : "ButtonComponent",
																		id : "btnCancelar",
																		label : "Cancelar solicitud",
																		className : "btn-danger btn-menuSolicitud",
																		disabled: true,
																		breakWord : true
																	},
																	{
																		type : "ButtonComponent",
																		id : "btnSolicitarInformacion",
																		label : "Solicitar informaci\u00F3n",
																		className : "btn-default btn-menuSolicitud",
																		disabled: true,
																		breakWord : true
																	},
																	{
																		type : "ButtonComponent",
																		id : "btnGenerar",
																		label : "Obtener Certificaci\u00F3n",
																		className : "btn-default btn-menuSolicitud",
																		disabled: true, 
																		breakWord : true
																	},
																	{
																		type : "ButtonComponent",
																		id : "btnConsultaPrevio",
																		label : "Consulta previa",
																		//command : "consultaSolicitudController.iniciar",
																		className : "btn-default btn-menuSolicitud",
																		breakWord : true
																	},
																	{
																		type : "ButtonComponent",
																		id : "btnReasignar",
																		label : "Reasignar",
																		className : "btn-default btn-menuSolicitud",
																		disabled: true,
																		breakWord : true
																	}]
												},{// index 1 : [ Cancelar, Solicitar info, Consulta previa, Reasignar]   
													type : "ButtonGroupComponent",
													direction: "left",
													components : [
																	{
																		type : "ButtonComponent",
																		id : "btnCancelar",
																		label : "Cancelar solicitud",
																		command : "consultaSolicitudController.cancelar",
																		className : "btn-danger btn-menuSolicitud",
																		breakWord : true
																	},
																	{
																		type : "ButtonComponent",
																		id : "btnSolicitarInformacion",
																		label : "Solicitar informaci\u00F3n",
																		command : "consultaSolicitudController.solicitarInformacion",
																		className : "btn-default btn-menuSolicitud",
																		breakWord : true
																	},
																	{
																		type : "ButtonComponent",
																		id : "btnGenerar",
																		label : "Obtener Certificaci\u00F3n",
																		className : "btn-default btn-menuSolicitud",
																		disabled: true,
																		breakWord : true
																	},
																	{
																		type : "ButtonComponent",
																		id : "btnConsultaPrevio",
																		label : "Consulta previa",
																		//command : "consultaSolicitudController.consultaPrevia",
																		className : "btn-default btn-menuSolicitud",
																		estadoAtendidaOperada: true,
																		breakWord : true
																	},
																	{
																		type : "ButtonComponent",
																		id : "btnReasignar",
																		label : "Reasignar",
																		command : "consultaSolicitudController.reasignar",
																		className : "btn-default btn-menuSolicitud",
																		breakWord : true
																	}]
												},{// index 2 : [ Cancelar, Consulta previa ]   
													type : "ButtonGroupComponent",
													direction: "left",
													components : [
																	{
																		type : "ButtonComponent",
																		id : "btnCancelar",
																		label : "Cancelar solicitud",
																		command : "consultaSolicitudController.cancelar",
																		className : "btn-danger btn-menuSolicitud",
																		breakWord : true
																	},
																	{
																		type : "ButtonComponent",
																		id : "btnSolicitarInformacion",
																		label : "Solicitar informaci\u00F3n",
																		className : "btn-default btn-menuSolicitud",
																		disabled: true,
																		breakWord : true
																	},
																	{
																		type : "ButtonComponent",
																		id : "btnGenerar",
																		label : "Obtener Certificaci\u00F3n",
																		className : "btn-default btn-menuSolicitud",
																		disabled: true,
																		breakWord : true
																	},
																	{
																		type : "ButtonComponent",
																		id : "btnConsultaPrevio",
																		label : "Consulta previa",
																		//command : "consultaSolicitudController.iniciar",
																		className : "btn-default btn-menuSolicitud",
																		breakWord : true
																	},
																	{
																		type : "ButtonComponent",
																		id : "btnReasignar",
																		label : "Reasignar",
																		className : "btn-default btn-menuSolicitud",
																		disabled: true,
																		breakWord : true
																	}]
												},{// index 3 : [ Consulta previa, Reasignar ]
													type : "ButtonGroupComponent",
													direction: "left",
													components : [
																	{
																		type : "ButtonComponent",
																		id : "btnCancelar",
																		label : "Cancelar solicitud",
																		className : "btn-danger btn-menuSolicitud",
																		disabled: true,
																		breakWord : true
																	},
																	{
																		type : "ButtonComponent",
																		id : "btnSolicitarInformacion",
																		label : "Solicitar informaci\u00F3n",
																		className : "btn-default btn-menuSolicitud",
																		disabled: true,
																		breakWord : true
																	},
																	{
																		type : "ButtonComponent",
																		id : "btnGenerar",
																		label : "Obtener Certificaci\u00F3n",
																		className : "btn-default btn-menuSolicitud",
																		disabled: true,
																		breakWord : true
																	},
																	{
																		type : "ButtonComponent",
																		id : "btnConsultaPrevio",
																		label : "Consulta previa",
																		//command : "consultaSolicitudController.iniciar",
																		className : "btn-primary btn-menuSolicitud",
																		breakWord : true
																	},
																	{
																		type : "ButtonComponent",
																		id : "btnReasignar",
																		label : "Reasignar",
																		command : "consultaSolicitudController.reasignar",
																		className : "btn-primary btn-menuSolicitud",
																		breakWord : true
																	}]
												},{// index 4 : [ CertificacionConsulta previa, Reasignar ]
														type : "ButtonGroupComponent",
														direction: "left",
														components : [
																	{
																		type : "ButtonComponent",
																		id : "btnCancelar",
																		label : "Cancelar solicitud",
																		className : "btn-danger btn-menuSolicitud",
																		disabled: true,
																		breakWord : true
																	},
																	{
																		type : "ButtonComponent",
																		id : "btnSolicitarInformacion",
																		label : "Solicitar informaci\u00F3n",
																		className : "btn-default btn-menuSolicitud",
																		disabled: true,
																		breakWord : true
																	},
																	{
																		type : "ButtonComponent",
																		id : "btnGenerar",
																		command : "consultaSolicitudController.generar",
																		label : "Obtener Certificaci\u00F3n",
																		className : "btn-default btn-menuSolicitud",
																		breakWord : true
																	},
																	{
																		type : "ButtonComponent",
																		id : "btnConsultaPrevio",
																		label : "Consulta previa",
																		//command : "consultaSolicitudController.iniciar",
																		className : "btn-default btn-menuSolicitud",
																		breakWord : true
																	},
																	{
																		type : "ButtonComponent",
																		id : "btnReasignar",
																		label : "Reasignar",
																		command : "consultaSolicitudController.reasignar",
																		className : "btn-default btn-menuSolicitud",
																		breakWord : true
																	}]
								}]
                }],
              layout: [[{span: 12}]]
            },*/
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
              type: "ConsultaSolicitudComponent",
              model: "solicitud"
            },
            { type:"ConsultaSolicitudButtonGroupComponent", model:"consultaSolicitud.idEstadoSolicitud", position:"bottom", id:"tb2" },
            /*{
				type : "CardLayoutComponent",
				id : "autorizadorButtonsCardLayout",
				components : [
				{//index 0: [ Salir ]
					type : "ButtonGroupComponent",
					components : [
					{
						type : "ButtonComponent",
						command : "bandeja",
						label : "Salir",
						className : "btn-danger"
					}]
				},
				{//index 1: [ Salir, Consultar Cambios]
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
						id : "btnCambiosAutorizar",
						label : "Consultar cambios",
						command : "consultaSolicitudController.iniciar",
						className : "btn-primary"
					}]
				}]
			}*/
    ],
			layout : [ [ {span : 5}, {span : 4}, {span : 5}, {span : 6} ], [ {span : 12} ], [ {span : 12} ], [ {span : 12} ], [ {span : 12} ] ]
        }
      ],
      layout: [[{span: 12}]]
    }
  };
};