function ResponsableUI(module){
  this.module=module;  
  this.bandejaSolicitudesUI = new BandejaSolicitudesUI(module);
  this.bitacoraUI = new BitacoraUI(module);
  this.consultaSolicitudUI = new ConsultaSolicitudUI(module);
  this.correccionDatosUI = new CorreccionDatosUI(module);
  this.confirmarUI = new ConfirmarUI(module);
  this.cancelarUI = new CancelarUI(module);
  this.solicitarInformacionUI = new SolicitarInformacionUI(module);
  this.consultaPreviaUI= new ConsultaPreviaUI(module);
  this.consultaPreviaDetalleUI = new ConsultaPreviaDetalleUI(module);
  this.consultaDetalleAtencionResponsableUI = new ConsultaDetalleAtencionResponsableUI(module);
  this.consultaAtencionResponsableUI= new ConsultaAtencionResponsableUI(module);
  this.agregarNssDocumentoUI= new AgregarNSSDocumentoUI(module);
  this.bandejaCuentaIndividualUI= new BandejaCuentaIndividualUI(module);
  this.bandejaCuentaIndividualResumenUI = new BandejaCuentaIndividualResumenUI(module);
  this.bandejaCuentaIlogicaUI = new BandejaCuentaIlogicaUI(module);
  this.procesandoUI=new ProcesandoUI(module);
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
                            this.procesandoUI.metadata.ui.components[0],//0
                  this.bandejaSolicitudesUI.metadata.ui.components[0],//1
                  this.consultaSolicitudUI.metadata.ui.components[0],//2
                  { type: "CorreccionDatosComponent", id: "correccionDatosLectura", model: "correccionDatosLectura", readOnly:true, screen:"correccionDatosLectura" },//3
                  this.confirmarUI.panel,//4
                  this.cancelarUI.panel,//5
                  this.bitacoraUI.metadata.ui.components[0],//6
                  this.solicitarInformacionUI.panel,//7
                  { type: "CorreccionDatosComponent", id: "correccionDatos", model: "correccionDatos", readOnly:false, screen:"correccionDatos"},//8
                  { type: "ConfirmarCorreccionDatosComponent", id: "confirmarCorreccionDatosLectura", model: "confirmarCorreccionDatosLectura", readOnly:true, screen: "confirmarCorreccionDatosLectura" },//9
                  this.consultaAtencionResponsableUI.panel,//10               
				  this.consultaDetalleAtencionResponsableUI.panel,//11
                  this.agregarNssDocumentoUI.panel,//12
                  { type: "CuentaIndividualComponent", id: "cuentaIndividualAsegurado", model:"cuentaIndividualAsegurado" },//13
                  { type: "CuentaIndividualConsultaComponent", id: "cuentaIndividualConsulta", model:"cuentaIndividualConsulta" , screen:"consultaCuentaIndividual" },//14
//                  this.bandejaCuentaIndividualResumenUI.metadata.CorreccionDatosComponent.ui.components[0],//14
                  { type: "ConfirmarCorreccionDatosComponent", id: "confirmarCorreccionDatos", model: "confirmarCorreccionDatos", readOnly:false, screen:"confirmarCorreccionDatos" },//15
//                  this.bandejaCuentaIlogicaUI.metadata.ui.components[0],//15
                  
                  { type: "HTMLComponent", html: "<a class='col-md-12 text-center' href='javascript:document.location.reload()'>Volver a inicio</a>" },//16
                  { type: "ConfrontaCuentaIndividualComponent", id: "confrontaCuentaIndividual", model:"confrontaCuentaIndividual" },//17
                  { type: "CorreccionDatosComponent", id: "correccionDatosConsultaPrevia", model: "correccionDatosConsultaPrevia", readOnly:true, screen:"correccionDatosConsultaPrevia" },//18
                  { type: "ConfirmarCorreccionDatosComponent", id: "confirmarCorreccionConsultaPrevia", model: "confirmarCorreccionConsultaPrevia", readOnly:true, screen: "confirmarCorreccionConsultaPrevia" },//19
                  { type: "ConfrontaCuentaIndividualComponent", id: "confrontaCuentaIndividualResponsable", model: "confrontaCuentaIndividualResponsable",  screen: "confrontaCuentaIndividualResponsable" },//20
                  { type: "CuentaIndividualConsultaComponent", id: "consultaCuentaIndividualResponsable", model: "consultaCuentaIndividualResponsable",  screen: "consultaCuentaIndividualResponsable" , autorizador:false},//21,
				  { type: "ConfrontaCuentaIndividualComponent", id: "confrontaCuentaIndividualConsultaPrevia", model: "confrontaCuentaIndividualConsultaPrevia",  screen: "confrontaCuentaIndividualConsultaPrevia" },//22
                  { type: "CuentaIndividualConsultaComponent", id: "consultaCuentaIndividualConsultaPrevia", model: "consultaCuentaIndividualConsultaPrevia",  screen: "consultaCuentaIndividualConsultaPrevia" , autorizador:false}//23
                ]
              },
              {
                type:"HRComponent"                
              },
              {
                      type:"ModalComponent",
                      id:"modalFinResponsable",
                      title:"Mensaje de sistema",
                      body:{
                        components:[
                          { type: "LabelComponent", id:"labelM02_022",  label: "Se ha registrado ..."},
                          {
                              type:"ButtonGroupComponent",
                              components:[
                                {
                                  type:"ButtonComponent",
                                  command:"reload",
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
                    id:"modalAConsulta",
                    title:"Mensaje de sistema",
                    body:{
                      components:[
                        { type: "LabelComponent", label: "\u00bfEst\u00e1 seguro que desea salir? Se perder\u00e1 la informaci\u00f3n capturada."},
                        {
                            type:"ButtonGroupComponent",
                            components:[
                              {
                                type:"ButtonComponent",
                                command:"irAConsulta",
                                label:"Aceptar",
                                className:"btn-primary"
                              },
                              {
                                  type:"ButtonComponent",
                                  command:"salirAgregarCancelar",
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
                        id: "ErrorCANASE",
                        body: {
                          type: "PanelComponent",
                          components: [
                            { type: "LabelComponent", 
                              label: "El NSS Certificador involucrado en la solicitud es de tipo Convencional, por lo que no es posible atender la solicitud, por favor verifique que el NSS sea Ordinario."}
                          ],
                          layout: [[{span: 12}]]
                        },
                        footer: {
                          type: "PanelComponent",
                          components: [
                            {type: "LabelComponent"}, 
                            {type: "ButtonComponent", 
                             className: "btn-primary", 
                             label: "Aceptar", 
                             command:"solicitarInformacionController.ocultarCanase"}
                          ],
                          layout: [[{span: 8}, {span: 4}]]
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
											{type: "TextFieldComponent", field: "correoAsegurado" , id:"correoAseguradoText"},
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
                     },
                     //M02-029
                     {
                         type:"ModalComponent",
                         id:"modalCuentaIndividualCambiosCuenta",
                         title:"Mensaje de sistema",
                         body:{
                           components:[
                             { type: "LabelComponent", label: "No se han identificado cambios realizados a los periodos de cuenta individual de los NSS involucrados. \u00bfDesea continuar?"},                             
                             {
                                 type:"ButtonGroupComponent",
                                 components:[
                                   {
                                     type:"ButtonComponent",
                                     command:"confirmarSinCambios",
                                     label:"Aceptar",
                                     className:"btn-primary"
                                   },
                                   {
                                       type:"ButtonComponent",
                                       command:"cancelarCuentaIndividual",
                                       label:"Cancelar",
                                       className:"btn-primary"
                                     }
                                 ]
                               }
                           ],
                           layout:[[{span:12}],[{span:12}]]
                         }
                    },
                    
                  //Mensajes de Cuenta Individual M02-030
                    {
                        type:"ModalComponent",
                        id:"modalCuentaIndividualCambiosCuentas",
                        title:"Mensaje de sistema",
                        body:{
                          components:[
                            { type: "LabelComponent", label: "Los cambios realizados a las cuentas individuales de los NSS involucrados serán registrados. \u00bfDesea continuar?"},                             
                            {
                                type:"ButtonGroupComponent",
                                components:[
                                  {
                                    type:"ButtonComponent",
                                    command:"confirmarCambiosFinales",
                                    label:"Aceptar",
                                    className:"btn-primary"
                                  },
                                  {
                                      type:"ButtonComponent",
                                      command:"cancelarCuentaIndividual",
                                      label:"Cancelar",
                                      className:"btn-primary"
                                    }
                                ]
                              }
                          ],
                          layout:[[{span:12}],[{span:12}]]
                        }
                   },
                    
                    // M02-031
                    {
                        type:"ModalComponent",
                        id:"modalCuentaIndividualCambios",
                        title:"Mensaje de sistema",
                        body:{
                          components:[
                            { type: "LabelComponent", label: "No se han identificado cambios realizados a los periodos de cuenta individual. \u00bfDesea continuar?"},                             
                            {
                                type:"ButtonGroupComponent",
                                components:[
                                  {
                                    type:"ButtonComponent",
                                    //command:"avanzarAutorizar",
                                    label:"Aceptar",
                                    className:"btn-primary"
                                  },
                                  {
                                      type:"ButtonComponent",
                                      command:"cancelarCuentaIndividual",
                                      label:"Cancelar",
                                      className:"btn-primary"
                                    }
                                ]
                              }
                          ],
                          layout:[[{span:12}],[{span:12}]]
                        }
                   },
                    
                    //M02-032
                    {
                        type:"ModalComponent",
                        id:"modalCuentaIndividualRegistro",
                        title:"Mensaje de sistema",
                        body:{
                          components:[
                            { type: "LabelComponent", label: "Los cambios realizados a la cuenta individual del NSS involucrado serán registrados. \u00bfDesea continuar?"},
                            {
                                type:"ButtonGroupComponent",
                                components:[
                                  {
                                    type:"ButtonComponent",
                                    //command:"avanzarAutorizar",
                                    label:"Aceptar",
                                    className:"btn-primary"
                                  },
                                  {
                                      type:"ButtonComponent",
                                      command:"cancelarCuentaIndividual",
                                      label:"Cancelar",
                                      className:"btn-primary"
                                    }
                                ]
                              }
                          ],
                          layout:[[{span:12}],[{span:12}]]
                        }
                      },
                      
                      //M02-033
                      {
                          type:"ModalComponent",
                          id:"modalCuentaIndividualPeriodos",
                          title:"Mensaje de sistema",
                          body:{
                            components:[
                              { type: "LabelComponent", label: "Los periodos asociados al NRP [NRP - NOMBRE o RAZON SOCIAL] no fueron modificados en su totalidad. \u00bfDesea continuar?"},
                              {
                                  type:"ButtonGroupComponent",
                                  components:[
                                    {
                                      type:"ButtonComponent",
                                      command:"confirmarPeriodos",
                                      label:"Aceptar",
                                      className:"btn-primary"
                                    },
                                    {
                                        type:"ButtonComponent",
                                        command:"cancelarCuentaIndividual",
                                        label:"Cancelar",
                                        className:"btn-primary"
                                      }
                                  ]
                                }
                            ],
                            layout:[[{span:12}],[{span:12}]]
                          }
                        },
                      //M02-020
                        {
                            type:"ModalComponent",
                            id:"modalCorreccionDatos",
                            title:"Mensaje de sistema",
                            body:{
                              components:[
                                { type: "LabelComponent", label: "Para poder continuar, debe seleccionar el tipo de NSS y tipo de regularizaci&oacuten por cada NSS involucrado en la solicitud."},
                                {
                                    type:"ButtonGroupComponent",
                                    components:[
                                      {
                                        type:"ButtonComponent",
                                        command:"modalCoreeccionDatos",
                                        label:"Aceptar",
                                        className:"btn-primary"
                                      },                                     
                                    ]
                                  }
                              ],
                              layout:[[{span:12}],[{span:12}]]
                            }
                          },
                          {
                            type:"ModalComponent",
                            id:"modalCorreccionDatosOtraPersona",
                            title:"Mensaje de sistema",
                            body:{
                              components:[
                                { type: "LabelComponent", label: "Solo debe seleccionar un Tipo de Regularizaci\u00f3n para el Tipo de NSS Corresponde a otra persona."},
                                {
                                    type:"ButtonGroupComponent",
                                    components:[
                                                {
                                                type:"ButtonComponent",
                                                command:"modaltipoRtipoN",
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
                            id:"modalDebeHaberCertificador",
                            title:"Mensaje de sistema",
                            body:{
                              components:[
                                { type: "LabelComponent", label: "Debe existir al menos un certificador"},
                                {
                                    type:"ButtonGroupComponent",
                                    components:[
                                                {
                                                type:"ButtonComponent",
                                                command:"closeModal",
                                                argument:"'modalDebeHaberCertificador'",
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
                            id:"modalMov5",
                            title:"Mensaje de sistema",
                            body:{
                              components:[
                                { type: "LabelComponent", label: "No se mostrará la cuenta individual."},
                                {
                                    type:"ButtonGroupComponent",
                                    components:[
                                      {
                                        type:"ButtonComponent",
                                        command:"modalMoviminto5",
                                        label:"Aceptar",
                                        className:"btn-primary"
                                      },                                     
                                    ]
                                  }
                              ],
                              layout:[[{span:12}],[{span:12}]]
                            }
                          },
						   {
                            type:"ModalComponent",
                            id:"modalCorreccionNo",
                            title:"Mensaje de sistema",
                            body:{
                              components:[
                                { type: "LabelComponent", label: "Usted no seleccion\u00f3 correcci\u00f3n de datos estad\u00EDsticos y/o correcci\u00f3n de nombre."},
                                {
                                    type:"ButtonGroupComponent",
                                    components:[
                                      {
                                        type:"ButtonComponent",
                                        command:"modalCorreccionNo",
                                        label:"Aceptar",
                                        className:"btn-primary"
                                      },                                     
                                    ]
                                  }
                              ],
                              layout:[[{span:12}],[{span:12}]]
                            }
                          },
						  {
                            type:"ModalComponent",
                            id:"noCANSE",
                            title:"Mensaje de sistema",
                            body:{
                              components:[
                                { type: "LabelComponent", label: "No existe informaci\u00f3n en CANASE."},
                                {
                                    type:"ButtonGroupComponent",
                                    components:[
                                      {
                                        type:"ButtonComponent",
                                        command:"modalNoCANASE",
                                        label:"Aceptar",
                                        className:"btn-primary"
                                      },                                     
                                    ]
                                  }
                              ],
                              layout:[[{span:12}],[{span:12}]]
                            }
                          },
                          {
                              type:"ModalComponent",
                              id:"borradoCI",
                              title:"Mensaje de sistema",
                              body:{
                                components:[
                                  { type: "LabelComponent", label: "Existen movimientos de cuenta individual, si realiza alg\u00FAn cambio ser\u00E1n eliminados."},
                                  {
                                      type:"ButtonGroupComponent",
                                      components:[
                                        {
                                          type:"ButtonComponent",
                                          command:"acptarBorrado",
                                          label:"Aceptar",
                                          className:"btn-primary"
                                        },
                                        {
                                            type:"ButtonComponent",
                                            command:"rechazarBorrado",
                                            label:"Cancelar",
                                            className:"btn-primary"
                                          },      
                                      ]
                                    }
                                ],
                                layout:[[{span:12}],[{span:12}],[{span:12}]]
                              }
                            },
                            
            ],
            layout:[
              [{span:12}],
			  [{span:12}],
			  [{span:12}],
              [{span:12}],
              [{span:12}],
              [{span:12}],
              [{span:12}],
              [{span:12}],
              [{span:12}],
              [{span:12}],
              [{span:12}],
              [{span:12}],
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
