function ReportesUI(module){
  this.module=module;  
  this.reporteGridSolicitudesUI = new ReporteGridSolicitudesUI(module);
  this.reporteGridEstadisticaUI = new ReporteGridEstadisticaUI(module);
  this.reporteFiltroBusquedaUI = new ReporteFiltroBusquedaUI(module);
  this.detalleSolicitud = new detalleSolicitud(module);
  this.init();
}

ReportesUI.prototype.init = function(){
  this.metadata = {
    ui: {
        components: [
          {
            type:"PanelComponent",            
            id:"panelConsultaSolicitud",
            components:[
              {
                type:"UserProfileComponent",
                id:"userProfile",
                model:"userProfile"
              },
              {
               type:"PanelComponent",
               //label:"",
               components:[], layout:[]
              },
              {
                type:"AlertComponent",
                id:"alert"
              },
              {
                type:"CardLayoutComponent",
                id:"responsableCardLayout",
                components:[
                  this.reporteFiltroBusquedaUI.metadata.ui.components[0],//0
                  //this.reporteGridSolicitudesUI.metadata.ui.components[0],
                  this.reporteGridEstadisticaUI.metadata.ui.components[0],//1
                  this.detalleSolicitud.metadata.ui.components[0]//2
                ]
              },
              {
                type:"HRComponent"                
              },
			  
              {
                type:"ModalComponent",
                id:"modalReporte",
                title:"Mensaje de sistema",
                body:{
                  components:[
                    { type: "LabelComponent", label: "Su petici\u00f3n se est\u00E1 procesando... Espere un momento"}
                  ],
                  layout:[[{span:12}]]
                }
              },
              {
                  type:"ModalComponent",
                  id:"modalSinFiltrosReporte",
                  title:"Mensaje de sistema",
                  body:{
                    components:[
                      { type: "LabelComponent", label: "Debe seleccionar al menos un filtro para realizar la b&uacutesqueda."},
                      {
                          type:"ButtonGroupComponent",
                          components:[
                            {
                              type:"ButtonComponent",
							  command:"cerrarModalFiltros",
                              label:"Aceptar",
                              className:"btn-primary"
                            }
                          ]
                        }
                    ],
                    layout:[[{span:12}],[{span:12}]]
                  }
                },
              {
                  type:"ModalComponent",
                  id:"modalSalir",
                  title:"Mensaje de sistema",
                  body:{
                    components:[
                      { type: "LabelComponent", label: "\u00bfDesea salir del Sistema?"},
                      {
                          type:"ButtonGroupComponent",
                          components:[
                            {
                              type:"ButtonComponent",
                              command:"salirAceptar",
                              label:"Aceptar",
                              className:"btn-primary"
                            },
                            {
                                type:"ButtonComponent",
                                command:"salirCancelar",
                                label:"Cancelar",
                                className:"btn-primary"
                              }
                          ]
                        }
                    ],
                    layout:[[{span:12}],[{span:12}]]
                  }
                },
                {
                    type:"ModalComponent",
                    id:"modalCancelar",
                    title:"Mensaje de sistema",
                    body:{
                      components:[
                        { type: "LabelComponent", label: "\u00bfEst\u00e1 seguro que desea cancelar la solicitud?"},
                        {
                            type:"ButtonGroupComponent",
                            components:[
                              {
                                type:"ButtonComponent",
                                command:"cancelarController.cancelarAccion",
                                label:"Aceptar",
                                className:"btn-primary"
                              },
                              {
                                  type:"ButtonComponent",
                                  command:"cancelarController.cancelarModal",
                                  label:"Cancelar",
                                  className:"btn-primary"
                                }
                            ]
                          }
                      ],
                      layout:[[{span:12}],[{span:12}]]
                    }
                  },
                  {
                      type:"ModalComponent",
                      id:"modalEnvioSindo",
                      title:"Mensaje de sistema",
                      body:{
                        components:[
                          { type: "LabelComponent", label: "Esta acci\u00f3n modificar\u00E1 los datos del asegurado enviando los movimientos correspondientes para su proceso en SINDO. \n \u00bfDesea autorizar la solicitud?"},
                          {
                              type:"ButtonGroupComponent",
                              components:[
                                {
                                  type:"ButtonComponent",
                                  command:"envioSindo",
                                  label:"Aceptar",
                                  className:"btn-primary"
                                },
                                {
                                    type:"ButtonComponent",
                                    command:"cancelarEnvioSindo",
                                    label:"Cancelar",
                                    className:"btn-primary"
                                  }
                              ]
                            }
                        ],
                        layout:[[{span:12}],[{span:12}]]
                      }
                    },					
              {
                  type:"ModalComponent",
                  id:"modalNoAGeneradoUnReporte",
                  title:"Mensaje de sistema",
                  body:{
                    components:[
                      { type: "LabelComponent", label: "No has realizado alguna consulta."},
                      {
                          type:"ButtonGroupComponent",
                          components:[
                            {
                              type:"ButtonComponent",
							  command:"cerrarNoAGeneradoUnReporte",
                              label:"Aceptar",
                              className:"btn-primary"
                            }
                          ]
                        }
                    ],
                    layout:[[{span:12}],[{span:12}]]
                  }
                }
            ],
            layout:[
			  [{span:12}],
			  [{span:12}],
              [{span:12}],
              [{span:12}],
              [{span:12}],
              [{span:12}],
              [{span:12}]
            ]
          }
        ],
        layout: [[{span:12}]]
    },
    model:{
      entities:[
        {
          name: "SeguimientoTramite",
          fields: [
            { name:"resumen", domain:"resumen"},
            { name:"detalle", domain:"detalle"}
          ]
        }
      ],
      domains:[
        { name:"resumen", 
          rules:[
            { type: "Required", message: "El campo resumen es requerido" }
          ] 
        },
        { name:"detalle", 
          rules:[
            { type: "Required", message: "El campo detalle es requerido" }
          ] 
        }
      ]
    }
  };
}; 