function BandejaSolicitudesUI(module){
  this.module = module;
  this.init();
}

BandejaSolicitudesUI.prototype.init = function(){
  this.metadata = {
    ui: {
      components: [                        
        {
          type:"PanelComponent",
          id:"panelTramitesAsignados",
          components:[
			{
				type:"SimpleCollapsableComponent",
		        id:"menuBandeja",
		        collapsed: false,
		        direction: "right",
		        command: "showBtnReportes",
		        components:[ {        
				
					type: "FormPanelComponent",
					collapsed: false,
					level: 5,
					id: "filter",
					model : "filter",
					name : "filter",
					components: [
					  
					    {type: "LabelComponent", label: ""},            
					    {type:"LabelComponent" ,label: "Folio"},
						{type:"LabelComponent", label: "Fecha de solicitud"},
						{type:"LabelComponent", label: "NSS involucrados"},
						{type:"LabelComponent",label: "Origen"},
						{type:"LabelComponent", label: "Responsable"},
					    {type: "LabelComponent", label: ""},            

					    {type: "LabelComponent", label: ""},            						
						{type: "TextFieldComponent", field: "folio", maxlength:"22", tipo:"number"},
						{type: "DatePickerFieldComponent" ,field: "fechaSolicitud"},
						{type: "TextFieldComponent", field: "nss", maxlength:"11", tipo:"number"},
						{type: "SelectFieldComponent",  id: "origenSelectField", tamanoAnchoSelect : 12,field: "origen", data: "fetchOrigen"},
						{type: "SelectFieldComponent", id: "responsableSelectField", tamanoAnchoSelect : 12, field: "responsable",  data: "fetchResponsable"},
					    {type: "LabelComponent", label: ""},            

					    {type: "LabelComponent", label: ""},            						
						{type:"LabelComponent" ,label: "Autorizó"},
						{type:"LabelComponent", label: "Estado"},
						{type:"LabelComponent", label: "CURP"},
						{type:"LabelComponent",label: "Tipo de tr\u00E1mite"},
						{type:"LabelComponent", label: "Última actualización"},
					    {type: "LabelComponent", label: ""},            

					    {type: "LabelComponent", label: ""},            						
						{type: "SelectFieldComponent", id: "autorizoSelectField", tamanoAnchoSelect : 12, field: "autorizo",  data: "fetchAutorizo"},
						{type: "SelectFieldComponent", id: "estadoSelectField",tamanoAnchoSelect : 12, field: "estado",  data: "fetchCombo"},
						{type: "TextFieldComponent", field: "curp",  maxlength:"21"},
						{type: "SelectFieldComponent",  id: "tramiteSelectField",tamanoAnchoSelect : 12, field: "tramite", data: "fetchTipoTramite"},
						{type: "DatePickerFieldComponent" ,field: "fechaActualizacion", },
					    {type: "LabelComponent", label: ""},            
						
						{type: "CheckBoxFieldComponent", field: "foliosVencidos", label: "Vencida",inLineWithLabel:true},
						{type: "LabelComponent", label: "              ",inLineWithLabel:true},
						{type: "LabelComponent", label: ""},

						{
							type: "PanelComponent",
						    collapsed: false,
							id : "menuPanelComponent",
							components : [
							{type: "LabelComponent"},
							{type:"ButtonComponent",command:"clean",label:"Limpiar",className:"btn-default pull-left",inLineWithLabel:true},				
							
							{type:"ButtonComponent",command:"filtros", argument:"'buscar'" ,label:" Buscar",className:"btn-primary pull-right", id:"button_buscar", icon:"search",inLineWithLabel:true},
							
							{type:"ButtonComponent",command:"reportes", label:"Reportes",className:"btn-primary pull-right",inLineWithLabel:true}//cambiar command
						
							] ,layout: [[{span: 6},{span: 2},{span: 2},{span: 2}]]
						},
						
						
						],
						layout: [
						    [{span: 1},{span: 2},{span: 2},{span: 2},{span: 2},{span: 2},{span: 1}],
						    [{span: 1},{span: 2},{span: 2},{span: 2},{span: 2},{span: 2},{span: 1}],
							[{span: 1},{span: 2},{span: 2},{span: 2},{span: 2},{span: 2},{span: 1}],
							[{span: 1},{span: 2},{span: 2},{span: 2},{span: 2},{span: 2},{span: 1}],
							[{span: 1},{span: 1},{span: 1},{span: 9}],
							]}
		        ],
		        layout:[[{span:12}]]
			  },
			  {             
					type:"PanelComponent", 
					id:"panelBtnReportes",
					hidden:true,
					components: [{
				      type:"ButtonComponent",
				      command:"reportes", 
				      label:"Reportes",
				      className:"btn-primary pull-right",
				      inLineWithLabel:true,
				      id: "btnReportes"}
				],
		        layout:[[{span:12}]]
		        },
			{
	              type:"LabelComponent",
	              label:""
	        }
			,{
              type: "TabPanelComponent",
              id: "panelTabsBandejas",
              tabs: [
                {title: "Tr\u00E1mites Asignados",
                  panel: {
                    type: "PanelComponent", id: "solicitud",
                    components: [
                      {
                        type: "GridComponent",
                        id: "gridTramites",
                        model: "gridTramites",
						data:"fetchTramites",
                        maxPage: 10,
                        fontSize: "73%",
                        columns: [
                          {label: "Folio", name: "folio", href: "selectTramite"},
                          {label: "Fecha de solicitud", name: "fechaSolicitud"},
                          {label: "CURP", name: "curp"},
                          {label: "NSS involucrados", listName: "nssListaInvolucrados", list: true},
                          {label: "Origen", name: "origen"},
                          {label: "Responsable", name: "nombreCompletoResponsable"},
                          {label: "Autoriz\u00f3", name: "nombreCompletoAutorizo"},
                          {label: "Estado", name: "estatus", href: "estatus"},
                          {label: "\u00daltima actualizaci\u00f3n", name: "ultimaActualizacion"},
                          {label: "Tipo de tr\u00E1mite", name: "tipo"}
                        ]
                      }

                    ],
                    layout: [[{span: 12}]]
                  }
                },
                {
                  title: "Hist\u00f3rico de solicitudes", panel: {type: "PanelComponent", id: "historicoTab",
                    components: [
                      {
                        type:"LabelComponent",
                        label:"Nota: Actualmente se limitó la consulta inicial a una semana (7 días), si es necesario consultar un día o folio en específico favor de utilizar los filtros.",
                        className:"bg-info pull-right text-right"
                      },
                      {
                        type:"LabelComponent",
                        label:""
                      },
                      {
                        type: "GridComponent",
                        id: "gridHistorico",
                        model: "gridHistorico",
                        data: "fetchHistorico",
                        maxPage: 10,
                        fontSize: "73%",
                        columns: [
                          {label: "Folio", name: "folio", href: "selectHistorico"},
                          {label: "Fecha de solicitud", name: "fechaSolicitud"},
                          {label: "CURP", name: "curp"},
                          {label: "NSS involucrados", listName: "nssListaInvolucrados", list: true},
                          {label: "Origen", name: "origen"},
                          {label: "Responsable", name: "nombreCompletoResponsable"},
                          {label: "Autoriz\u00f3", name: "nombreCompletoAutorizo"},
                          {label: "Estado", name: "estatus", href: "estatusHistorico"},
                          {label: "\u00daltima actualizaci\u00f3n", name: "ultimaActualizacion"},
                          {label: "Tipo de tr\u00E1mite", name: "tipo"}
                        ]
                      }
                    ],
                    layout: [[{span: 12},
                              {span: 12},
                              {span: 12}]]
                  }
                }
              ]
            },
            {
              type:"LabelComponent",
              label:""
            },
            {
              type:"ButtonGroupComponent",
              components:[
                {
                  type:"ButtonComponent",
                  command:"salir",
                  label:"Salir",
                  className:"btn-danger"
                }
              ]
            }
            
          ],
          layout:[[{span:12}],[{span:12}],[{span:12}],[{span:12}],[{span:12}],[{span:12}]]
        }            
      ],
      layout: [[{span:12}]]
    }
  };
};
