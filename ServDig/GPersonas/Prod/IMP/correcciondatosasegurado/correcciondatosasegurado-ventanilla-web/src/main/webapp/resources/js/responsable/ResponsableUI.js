function ResponsableUI(module){
  this.module=module;  
  this.bandejaSolicitudesUI = new BandejaSolicitudesUI(module);
  this.bitacoraUI = new BitacoraUI(module);
  this.consultaSolicitudUI = new ConsultaSolicitudUI(module);
  this.correccionDatosUI = new CorreccionDatosUI(module);
  this.confirmarUI = new ConfirmarUI(module);
  this.cancelarUI = new CancelarUI(module);
  this.solicitarInformacionUI = new SolicitarInformacionUI(module);
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
                type:"PanelComponent",
                label:"Atención de solicitudes por responsable",
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
                  this.correccionDatosUI.metadata.ui.components[0],
                  this.confirmarUI.panel,
                  this.cancelarUI.panel,
                  this.bitacoraUI.metadata.ui.components[0],
                  this.solicitarInformacionUI.panel,
                ]
              },
              {
                type:"HRComponent"                
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
                      id:"modalSalirInicioMen",
                      title:"Mensaje de sistema",
                      body:{
                        components:[
                          { type: "LabelComponent", label: "El NSS ingresado en la solicitud no tiene antecedentes en las fuentes de informaci\u00f3n del instituto: SINDO, CANASE, Hist\u00f3rico Central y BDTU.<br/><br/> Por el momento, solamente se puede realizar la regularizaci\u00f3n y/o correcci\u00f3n de datos del Asegurado, cuando el NSS asociado a la Solicitud exista en las fuentes de Informaci\u00f3n del Instituto."},
                          {
                              type:"ButtonGroupComponent",
                              components:[
                                {
                                  type:"ButtonComponent",
                                  command:"salirInicio",
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
                        id:"modalcertificadoNSS",
                        title:"Mensaje de sistema",
                        body:{
                          components:[
                            { type: "LabelComponent", 
                              label: "Por el momento, solamente se puede realizar la regularizaci\u00f3n y/o correcci\u00f3n de datos del Asegurado, cuando el NSS asociado a la solicitud no es Convencional.<br/> Se identifica un NSS Convencional cuando:<br/><br/> - La posici\u00f3n 1 y 2 del NSS tiene el valor 36 o 77 o 79 o 80 o 97.<br/> - La posici\u00f3n 1 y 2 del NSS tiene el valor 89 y la posici\u00f3n 3 y 4 tiene el valor 97 o 98 o 99 o 00 o 01 o 02."},
                            {
                                type:"ButtonGroupComponent",
                                components:[
                                  {
                                    type:"ButtonComponent",
                                    command:"aceptarCertificadoNSS",
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
                      id:"modalAvanzarResponsable",
                      title:"Mensaje de sistema",
                      body:{
                        components:[
                          { type: "LabelComponent", label: "Los cambios realizados ser\u00e1n guardados y  enviados para autorizaci\u00f3n de la persona titular de la Jefatura de Departamento de Afiliaci\u00f3n Vigencia o en su caso de la Jefatura de Oficina de Afiliaci\u00f3n. \u00bfDesea continuar?"},
                          {
                              type:"ButtonGroupComponent",
                              components:[
                                {
                                  type:"ButtonComponent",
                                  command:"avanzarAutorizar",
                                  label:"Aceptar",
                                  className:"btn-primary"
                                },
                                {
                                    type:"ButtonComponent",
                                    command:"cancelarAutorizar",
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
			    name: "TipoRegularizacionNSS",
			    fields: [
			      { name:"detalle.grupoCorreccion.nombre", domain:"tipoRegularizacionNSS"}
			    ]
			},
			{
			    name: "CapturaCorreo",
			    fields: [
			      { name:"correo", domain:"capturaCorreo"}
			    ]
			}
        ],
        domains:[
            {
	        	name:"tipoRegularizacionNSS", 
	            rules:[
	              { type: "Required", message: "El tipo de regularizaci\u00f3n  es requerido" }
	            ] 
	        },
            { name:"capturaCorreo", 
                rules:[
                  { type: "Required", message: "Campo requerido." }
                ] 
              }
        ]
    }
  };
};