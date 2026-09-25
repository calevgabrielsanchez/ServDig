/**
 * Contiene los mensajes de la cuenta individual e ilógica
 */

function MensajesCuentaIndividualUI(module) {
  this.module = module;
  this.init();
}

MensajesCuentaIndividualUI.prototype.init = function(){
  this.metadata = {
    ui: {
        components: [
          {
            type:"PanelComponent",            
            id:"panelMensajesCuentaIndividual",
            components:[
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
                        }
            ],
            layout:[
              [{span:12}],
              [{span:12}],
              [{span:12}],
              [{span:12}],
              [{span:12}]
            ]
          }
        ],
        layout: [[{span:12}]]
    }
  };
};