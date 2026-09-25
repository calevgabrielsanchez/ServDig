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
					             
					    {type:"LabelComponent" ,label: "Folio"},
						{type:"LabelComponent", label: "Fecha de solicitud"},
						{type:"LabelComponent", label: "NSS involucrados"},
						{type:"LabelComponent",label: "Origen"},
						{type:"LabelComponent", label: "Responsable"},
						
						{type: "TextFieldComponent", field: "folio", maxlength:"22", tipo:"number"},
						{type: "DatePickerFieldComponent" ,field: "fechaSolicitud"},
						{type: "TextFieldComponent", field: "nss", maxlength:"11", tipo:"number"},
						{type: "SelectFieldComponent",  id: "origenSelectField", tamanoAnchoSelect : 12,field: "origen", data: "fetchOrigen"},
						{type: "SelectFieldComponent", id: "responsableSelectField", tamanoAnchoSelect : 12, field: "responsable",  data: "fetchResponsable"},
						
						{type:"LabelComponent" ,label: "Autorizó"},
						{type:"LabelComponent", label: "Estado"},
						{type:"LabelComponent", label: "CURP"},
						{type:"LabelComponent",label: "Tipo de tr\u00E1mite"},
						{type:"LabelComponent", label: "Última actualización"},
						
						{type: "SelectFieldComponent", id: "autorizoSelectField", tamanoAnchoSelect : 12, field: "autorizo",  data: "fetchAutorizo"},
						{type: "SelectFieldComponent", id: "estadoSelectField",tamanoAnchoSelect : 12, field: "estado",  data: "fetchCombo"},
						{type: "TextFieldComponent", field: "curp",  maxlength:"21"},
						{type: "SelectFieldComponent",  id: "tramiteSelectField",tamanoAnchoSelect : 12, field: "tramite", data: "fetchTipoTramite"},
						{type: "DatePickerFieldComponent" ,field: "fechaActualizacion", },
						
						{type: "CheckBoxFieldComponent", field: "foliosVencidos", label: "Vencida",inLineWithLabel:true},
														
						{
							type: "PanelComponent",
						    collapsed: false,
							id : "menuPanelComponent",
							components : [
							{type: "LabelComponent"},
							{type:"ButtonComponent",command:"clean",label:"Limpiar",className:"btn-default pull-right",inLineWithLabel:true},				
							
							{type:"ButtonComponent",command:"filtros",label:" Buscar",className:"btn-primary pull-right", icon:"search",inLineWithLabel:true},
							
							{type:"ButtonComponent",command:"nuevaSolicitud", label:"Reportes",className:"btn-primary pull-right",inLineWithLabel:true}//cambiar command
						
							] ,layout: [[{span: 5},{span: 3},{span: 2},{span: 2}]]
						},
						
						
						],
						layout: [
						    [{span: 2},{span: 2},{span: 2},{span: 3},{span: 3}],
						    [{span: 2},{span: 2},{span: 2},{span: 3},{span: 3}],
							[{span: 2},{span: 2},{span: 2},{span: 3},{span: 3}],
							[{span: 2},{span: 2},{span: 2},{span: 3},{span: 3}],
							[{span: 4},{span: 8}],
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
				      command:"nuevaSolicitud", 
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
			  type:"PanelTabComponent",
			  subtype:"grid",
	          id:"panelTabsBandejas",
	          tabs: ["solicitud", "historico" ],
	          labels: ["Tr\u00E1mites Asignados", 
	                   "Hist\u00f3rico de solicitudes"],
	          components:[  
						{type:"PanelComponent", id:"solicitud" , 
						    components:[  
										{
										    type:"GridComponent",
										    id:"gridTramites",
										    model:"gridTramites",              
										    data:"fetchTramites",
										    styled:true,
										    fontSize: "16px",
										    columns:[
										      { label:"Folio",name:"folio",href:"selectTramite"},
										      { label:"Fecha de solicitud",name:"fechaSolicitud"},
										      { label:"NSS involucrados",name:"nssInvolucrados"},
										      { label:"Origen",name:"origen"},
										      { label:"Responsable",name:"nombreCompleto"},
										      { label:"Estatus",name:"estatus",href:"estatus"},
										      { label:"\u00daltima actualizaci\u00f3n",name:"ultimaActualizacion"},
										      { label:"Tipo de tr\u00E1mite",name:"tipo"}
										    ]
										  }		
										
						      ],
						      layout:[[{span:12}]]
						},
						{type:"PanelComponent", id:"historico",
						    components:[  
						               
												{
												    type:"GridComponent",
												    id:"gridHistorico",
												    model:"gridHistorico",
												    data:"fetchHistorico",
												    styled:true,
												    fontSize: "16px",
												    columns:[
												      { label:"Folio",name:"folio",href:"selectHistorico"},
												      { label:"Fecha de solicitud",name:"fechaSolicitud"},
												      { label:"NSS involucrados",name:"nssInvolucrados"},
												      { label:"Origen",name:"origen"},
												      { label:"Responsable",name:"nombreCompletoResponsable"},
												      { label:"Autoriz\u00f3",name:"nombreCompletoAutorizo"},
												      { label:"Estatus",name:"estatus",href:"estatusHistorico"},
												      { label:"\u00daltima actualizaci\u00f3n",name:"ultimaActualizacion"},
												      { label:"Tipo de tr\u00E1mite",name:"tipo"}
												    ]
												  }          
						      ],
						      layout:[[{span:12}]]
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
          layout:[[{span:12}],[{span:12}],[{span:12}],[{span:12}],[{span:12}]]
        }            
      ],
      layout: [[{span:12}]]
    }
  };
};