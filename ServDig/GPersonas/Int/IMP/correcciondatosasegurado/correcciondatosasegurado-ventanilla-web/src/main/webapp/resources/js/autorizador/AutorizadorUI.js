function ResponsableUI(module){
  this.module=module;  
  this.bandejaSolicitudesUI = new BandejaSolicitudesUI(module);
  this.bitacoraUI = new BitacoraUI(module);
  this.consultaSolicitudUI = new ConsultaSolicitudUI(module);
  this.consultaSolicitudReasignarUI = new ConsultaSolicitudReasignarUI(module);
  this.cambiosAutorizarUI = new CambiosAutorizarUI(module);
  this.reasignacionUI = new ReasignacionUI(module);
  this.solicitarInformacionUI = new SolicitarInformacionUI(module);
  this.rechazarUI = new RechazarUI(module);
  this.correccionDatosUI = new CorreccionDatosUI(module);
  this.cancelarUI= new CancelarUI(module);
  this.init();
}

ResponsableUI.prototype.init = function(){
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
               label:"Atenci\u00F3n de solicitudes por autorizador",
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
                  this.bandejaSolicitudesUI.metadata.ui.components[0],
                  this.consultaSolicitudUI.metadata.ui.components[0],
                  this.cambiosAutorizarUI.panel,
                  this.reasignacionUI.panel,
                  this.solicitarInformacionUI.panel,
                  this.rechazarUI.panel,
                  this.consultaSolicitudReasignarUI.metadata.ui.components[0],
                  this.correccionDatosUI.metadata.ui.components[0],//7
                  this.bitacoraUI.metadata.ui.components[0],
                  this.cancelarUI.panel
                ]
              },
              {
                type:"HRComponent"                
              },
              {
                type:"ModalComponent",
                id:"modal",
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
                        type: "ModalComponent",
                        title: "Mensaje de sistema",
                        id: "modalInfoCorreo",
                        body: {
                          type: "PanelComponent",
                          components: [
                            { type: "LabelComponent", 
                              label: "No existe el correo electr\u00f3nico del Asegurado, favor de ingresarlo para poder solicitar informaci\u00f3n."}
                          ],
                          layout: [[{span: 12}]]
                        },
                        footer: {
                          type: "PanelComponent",
                          components: [
                            {type: "LabelComponent"}, 
                            {type: "ButtonComponent", 
                             className: "btn-primary", 
                             label: "Continuar", 
                             command:"solicitarInformacionController.mostrarCapturaCorreo"}
                          ],
                          layout: [[{span: 8}, {span: 4}]]
                        }
                      },
                      
                      {     type: "FormPanelComponent",
							id: "panelGuardarCorreoComponent",
							name: "guardarCorreo",
							model: "guardarCorreo", 
							//level: 3,
							entity: "CapturaCorreo",
							components: [

						{ type:"ModalComponent",
                          id:"modalCapturaCorreo",
                          title:"Mensaje de sistema",
                          body:{
                            components:[
											{type: "LabelComponent", label: "Correo electr\u00f3nico*:"},
											{type: "TextFieldComponent", field: "correoAsegurado"},
											{
											    type:"ButtonGroupComponent",
											    components:[
											      {
											        type:"ButtonComponent",
											        command:"solicitarInformacionController.guardarCapturaCorreo",
											        label:"Continuar",
											        className:"btn-primary"
											      },
											      {
											          type:"ButtonComponent",
											          command:"solicitarInformacionController.cancelarCapturaCorreo",
											          label:"Cancelar",
											          className:"btn-danger"
											        }
											    ]
											  }

                                        ],
              					        layout: [[{span:5},{span:7}],[{span:12}]]
                      
                                 }
						    }
						],
					      layout: [[{span:12}]]
                     }
            ],
            layout:[
              [{span:12}],
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