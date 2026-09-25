function ConsultaSolicitudUI(module) {
  this.module = module;
  this.init();
}

ConsultaSolicitudUI.prototype.tamanioBotonesSolicitud = function() {
	// Cambiar el tamanio de botones barra
	$("#btnCancelar").prop('style',"width:20%;white-space: normal;");
	$("#btnSolicitarInformacion").prop('style',"width:20%;white-space: normal;");
	$("#btnGenerar").prop('style', "width:16.66%;white-space: normal;");
	$("#btnCambiosAutorizar").prop('style',"width:20%;white-space: normal;");
	$("#btnReasignar").prop('style',"height:73.4333px;width:20%;white-space: normal;");
};

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
          postFetch : "postFetchConsultaSolUI",
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
													components : [
																	{
																		type : "ButtonComponent",
																		id : "btnCambiosAutorizar",
																		label : "Consulta previa",
																		command : "consultaSolicitudController.iniciar",
																		className : "btn-default"
																	}]
												},{// index 1 : [ Cancelar, Solicitar info, Consulta previa, Reasignar]   
													type : "ButtonGroupComponent",
													components : [
																	{
																		type : "ButtonComponent",
																		id : "btnCancelar",
																		label : "Cancelar solicitud",
																		command : "consultaSolicitudController.cancelar",
																		className : "btn-danger"
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
																		id : "btnCambiosAutorizar",
																		label : "Consulta previa",
																		command : "consultaSolicitudController.iniciar",
																		className : "btn-default"
																	},
																	{
																		type : "ButtonComponent",
																		id : "btnReasignar",
																		label : "Reasignar",
																		command : "consultaSolicitudController.reasignar",
																		className : "btn-default"
																	}]
												},{// index 2 : [ Cancelar, Consulta previa ]   
													type : "ButtonGroupComponent",
													components : [
																	{
																		type : "ButtonComponent",
																		id : "btnCancelar",
																		label : "Cancelar solicitud",
																		command : "consultaSolicitudController.cancelar",
																		className : "btn-danger"
																	},
																	{
																		type : "ButtonComponent",
																		id : "btnCambiosAutorizar",
																		label : "Consulta previa",
																		command : "consultaSolicitudController.iniciar",
																		className : "btn-default"
																	}]
												},{// index 3 : [ Consulta previa, Reasignar ]
													type : "ButtonGroupComponent",
													components : [
																	{
																		type : "ButtonComponent",
																		id : "btnCambiosAutorizar",
																		label : "Consulta previa",
																		command : "consultaSolicitudController.iniciar",
																		className : "btn-primary"
																	},
																	{
																		type : "ButtonComponent",
																		id : "btnReasignar",
																		label : "Reasignar",
																		command : "consultaSolicitudController.reasignar",
																		className : "btn-primary"
																	}]
												},{// index 3 : [ Consulta previa, Reasignar ]
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
														id : "btnCambiosAutorizar",
														label : "Consulta previa",
														command : "consultaSolicitudController.iniciar",
														className : "btn-default"
													},
													{
														type : "ButtonComponent",
														id : "btnReasignar",
														label : "Reasignar",
														command : "consultaSolicitudController.reasignar",
														className : "btn-default"
													}]
								}]
                }],
              layout: [[{span: 12}]]
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
              type: "ConsultaSolicitudComponent",
              model: "solicitud"
            },
            {
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
			}],
			layout : [ [ {span : 5}, {span : 4}, {span : 5}, {span : 6} ], [ {span : 12} ], [ {span : 12} ], [ {span : 12} ], [ {span : 12} ] ]
        }
      ],
      layout: [[{span: 12}]]
    }
  };
};