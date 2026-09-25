function ConsultaSolicitudComponent(render){
    this.render = render;
    this.init();
    this.TAMANIO_TEXTAREA = "2em";
    this.TAMANIO_TEXTAREA_CADENA = 20;
}

ConsultaSolicitudComponent.prototype.draw = function (component) {
    var panel = new FormPanelComponent(this.render);
    this.model = component.model;
    component.components = this.metadata.components;
    return panel.draw(this.metadata);
};
ConsultaSolicitudComponent.prototype.notify = function (metadata, value) {
    this.render.modelToForm("FormPanelComponent-consultaSolicitud",
            this.render.module.controller.model["consultaSolicitud"]);
};

ConsultaSolicitudComponent.prototype.tamanioCamposConsultaSolicitud = function () {
    for (var i = 0; i < this.metadata.components[0].components.length; i++) {
        for (var j = 0; j < this.metadata.components[0].components[i].components.length; j++) {
            var componente = this.metadata.components[0].components[i].components[j];
            if (componente.type === "TextAreaFieldComponent") {
                var component = this.render.replaceAll(componente.field, ".", "_");
                if (componente.rows != null) {
                    $("#" + component).css("word-wrap", "break-word");
                } else if ($("#" + component).val().length < this.TAMANIO_TEXTAREA_CADENA) {
                    $("#" + component).css("height", this.TAMANIO_TEXTAREA);
                } else {
                    $("#" + component).css("word-wrap", "break-word");
                }
            }
        }
    }
};

ConsultaSolicitudComponent.prototype.validarCurpsHistoricas = function (gridsNss, informacionBeneficiario) {
    var infoRenapo;
    for (var i = 0; i < gridsNss.length; i++) {
        if (gridsNss[i].data[0].informacionRENAPO != null) {
            infoRenapo = gridsNss[i].data[0].informacionRENAPO;
            if (infoRenapo.curpsHistoricas === "") {
                //$("#curpsHistoricasText").hide();
            }
            break;
        }
    }

    if (informacionBeneficiario != null) {
        if (informacionBeneficiario.curpsHistoricas === null || informacionBeneficiario.curpsHistoricas === "") {
            $("#curpsHistoricasBeneficiarioText").hide();
            $('label[for="informacionBeneficiario.curpsHistoricas"]').hide();
            $("#informacionBeneficiario_curpsHistoricas").hide();
        }
    }

};

  ConsultaSolicitudComponent.prototype.validarCurpsHistoricas = function () {
	  if(this.render){		 
		if(this.render.module.controller.consultaSolicitudController.model.consultaSolicitud.informacionRENAPO.curpsHistoricas === "")
	  {
		  return "hide";
	  }else{
		  return "";
	  }
	  }
  };
ConsultaSolicitudComponent.prototype.init = function () {  
  
 var curpHistorica = ConsultaSolicitudComponent.prototype.validarCurpsHistoricas();
   
    this.metadata = {
        type: "FormPanelComponent",
        id: "panelConsultaSolicitud",
        postFetch: "postFetchConsultaSolicitudComponent",
        components: [
            {
                type: "PanelTabComponent",
                id: "panelTabsAsegurado",
                tabs: ["renapo", "domicilio", "aclaracion", "historia", "nssDoc", "nssDocVentanilla", "beneficiario", "contacto", "infoAdicional"],
                labels: ["Informaci\u00F3n de RENAPO",
                    "Domicilio",
                    "Motivo de aclaraci\u00F3n",
					"Historia laboral",
                    "NSS y Documentos",
                    "NSS y Documentos en Ventanilla",
                    "Información del Solicitante",
                    
                    "Datos de contacto y observaciones",
                    "Información y documentación adicional", ],
                components: [
                    {type: "PanelComponent", id: "renapo", //PANEL 1
                        components: [
                            {type: "TextFieldComponent", field: "informacionRENAPO.curp", label: "CURP", disabled: true, labelStyle: true},
                            {type: "TextFieldComponent", field: "informacionRENAPO.fechaNacimiento", label: "Fecha de nacimiento", disabled: true, labelStyle: true},
                            {type: "TextFieldComponent", field: "informacionRENAPO.sexo", label: "Sexo", disabled: true, labelStyle: true},

                            {type: "TextAreaFieldComponent", field: "informacionRENAPO.apellidoPaterno", label: "Primer apellido", disabled: true, labelStyle: true},
                            {type: "TextAreaFieldComponent", field: "informacionRENAPO.apellidoMaterno", label: "Segundo apellido", disabled: true, labelStyle: true},
                            {type: "TextAreaFieldComponent", field: "informacionRENAPO.nombre", label: "Nombre(s)", disabled: true, labelStyle: true},

                            {type: "TextAreaFieldComponent", field: "informacionRENAPO.lugarNacimiento", label: "Lugar de nacimiento", disabled: true, labelStyle: true},
                            {type: "TextFieldComponent", field: "informacionRENAPO.nacionalidad", label: "Nacionalidad", disabled: true, labelStyle: true},

                            {type: "TextAreaFieldComponent", field: "informacionRENAPO.curpsHistoricas", label: "CURPS Hist\u00F3ricas", disabled: true, rows: 3, id: "curpsHistoricasText"},

                            {type: "TextAreaFieldComponent", field: "informacionRENAPO.datosDocumentoProbatorio", label: "Datos del documento probatorio", disabled: true, rows: 3},
                        ],
                        layout: [[{span: 3}, {span: 3}, {span: 3}], [{span: 3}, {span: 3}, {span: 4}], [{span: 3}, {span: 3}], [{span: 12}], [{span: 12}]]
                    },
                    {type: "PanelComponent", id: "domicilio", //PANEL 2
                        components: [
                            {type: "TextFieldComponent", field: "domicilioParticular.calle", label: "Calle", disabled: true, labelStyle: true},
                            {type: "TextFieldComponent", field: "domicilioParticular.cp", label: "C\u00F3digo postal", disabled: true, labelStyle: true},

                            {type: "TextFieldComponent", field: "domicilioParticular.colonia", label: "Colonia", disabled: true, labelStyle: true},
                            {type: "TextFieldComponent", field: "domicilioParticular.numeroExterior", label: "N\u00FAmero ext", disabled: true, labelStyle: true},
                            {type: "TextFieldComponent", field: "domicilioParticular.numeroInterior", label: "Int", disabled: true, labelStyle: true},

                            {type: "TextFieldComponent", field: "domicilioParticular.delegacion", label: "Municipio/Alcald\u00eda", disabled: true, labelStyle: true},
                            {type: "TextFieldComponent", field: "domicilioParticular.entidadFederativa", label: "Entidad federativa", disabled: true, labelStyle: true}
                        ],
                        layout: [[{span: 6}, {span: 2}], [{span: 6}, {span: 2}, {span: 2}], [{span: 6}, {span: 6}]]
                    },
                    {type: "PanelComponent", id: "aclaracion", //PANEL 3
                        components: [
                            {type: "PanelComponent",
                                id: "panel5",
                                className: "",
                                components: [
                                    {type: "LabelComponent", label: "IMSS", name: "imss", className: "h5"},
                                    {type: "CheckBoxFieldComponent", field: "motivoAclaracion.cobroIncapacidad", label: "COBRO DE INCAPACIDAD", disabled: true},
                                    {type: "CheckBoxFieldComponent", field: "motivoAclaracion.pension", label: "PENSI\u00D3N", disabled: true},
                                    {type: "CheckBoxFieldComponent", field: "motivoAclaracion.retiroDesempleo", label: "RETIRO POR DESEMPLEO", disabled: true},
                                    {type: "CheckBoxFieldComponent", field: "motivoAclaracion.registroBeneficiarios", label: "REGISTRO DE BENEFICIARIOS", disabled: true},
                                    {type: "CheckBoxFieldComponent", field: "motivoAclaracion.adscripcionUMF", label: "ADSCRIPCI\u00D3N A UMF", disabled: true},
                                    {type: "CheckBoxFieldComponent", field: "motivoAclaracion.cambioUMF", label: "CAMBIO DE UMF", disabled: true},
                                    {type: "CheckBoxFieldComponent", field: "motivoAclaracion.gastosMatrimonio", label: "GASTOS DE MATRIMONIO", disabled: true},
                                    {type: "CheckBoxFieldComponent", field: "motivoAclaracion.gastosFuneral", label: "GASTOS DE FUNERAL", disabled: true}
                                ],
                                layout: [[{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}]]
                            },
                            {type: "PanelComponent",
                                id: "panel6",
                                components: [
                                    {type: "LabelComponent", label: "INFONAVIT", name: "infonavit", className: "h5"},
                                    {type: "CheckBoxFieldComponent", field: "motivoAclaracion.obtenerCredito", label: "OBTENER CR\u00C9DITO", disabled: true},
                                    {type: "CheckBoxFieldComponent", field: "motivoAclaracion.conclusionCredito", label: "CONCLUSI\u00D3N DE CR\u00C9DITO", disabled: true},
                                    {type: "CheckBoxFieldComponent", field: "motivoAclaracion.prorrogaRestructuraCredito", label: "PR\u00D3RROGA O REESTRUCTURA DE CR\u00C9DITO", disabled: true},
                                    {type: "CheckBoxFieldComponent", field: "motivoAclaracion.descuentoIndebidoCredito", label: "DESCUENTO INDEBIDO DE CR\u00C9DITO", disabled: true},
                                    {type: "TextFieldComponent", field: "motivoAclaracion.numeroCredito", label: "No. DE CR\u00C9DITO QUE LE EST\u00C1N DESCONTANDO", disabled: true, className: "h5"}
                                ],
                                layout: [[{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}]]
                            },
                            {type: "PanelComponent",
                                id: "panel7",
                                components: [
                                    {type: "LabelComponent", label: "AFORE", name: "afore", className: "h5"},
                                    {type: "CheckBoxFieldComponent", field: "motivoAclaracion.registroAfore", label: "REGISTRO EN AFORE", disabled: true},
                                    {type: "CheckBoxFieldComponent", field: "motivoAclaracion.aclaracionSaldoSupuestaVivienda", label: "ACLARACI\u00D3N DE SALDO SUBCUENTA VIVIENDA", disabled: true},
                                    {
                                        type: "PanelComponent", id: "panel8",
                                        components: [
                                            {type: "CheckBoxFieldComponent", field: "motivoAclaracion.otro", label: "OTRO", disabled: true},
                                            {type: "TextAreaFieldComponent", field: "motivoAclaracion.otroMotivo", disabled: true, rows: 5}
                                        ],
                                        layout: [[{span: 12}], [{span: 12}]]
                                    }
                                ],
                                layout: [[{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}]]
                            }
                        ],
                        layout: [[{span: 4}, {span: 4}, {span: 4}]]
                    },
					{type: "PanelComponent", id: "historia", //PANEL 7
                        components: [
                            {
                                type: "GridComponent",
                                title: "",
                                id: "gridHistoriaLaboral",
                                model: "gridHistoriaLaboral",
                                data: "fetchHistoriaLaboral",
                                styled: true,
                                breakWord: true,
                                columns: [
                                    {label: "Nombre o raz\u00F3n social", name: "nombrePatron", breakWord: true},
                                    {label: "Entidad federativa", name: "entidadFederativa"},
                                    {label: "Fecha de inscripci\u00F3n", name: "fechaInscripcion"},
                                    {label: "Fecha de baja", name: "fechaBaja"},
                                    {label: "N\u00FAmero de registro patronal", name: "numeroRegistroPatronal"},
                                    {label: "Actividad de la empresa", name: "actividadEmpresa", breakWord: true},
                                    {label: "Domicilio de la empresa", name: "domicilioEmpresa", breakWord: true}
                                ]
                            }
                        ],
                        layout: [[{span: 12}, {span: 12}]]
                    },
                    {type: "PanelComponent", id: "nssDoc", //PANEL 4
                        components: [{
                                type: "GridComponent",
                                title: "",
                                id: "gridDocumentos",
                                model: "gridDocumentos",
                                data: "fetchDocumentos",
                                index: true,
                                columns: [
                                    {label: "Documentos Asegurado", name: "tipoDocumento", href: "mostrarDocumento"}
                                ]
                            },
                            {
                                type: "CheckBoxFieldComponent", field: "defuncion", label: "Defunción", disabled: true
                            },
                            { //LISTA NSS
                                type: "NssDocumentsComponent", id: "gridNssSolicitudInfoSol", model: "gridNssSolicitud"
                            },
                            {
                                type: "LabelComponent"
                            }
                        ],
                        layout: [[{span: 12}], [{span: 12}], [{span: 12}, {span: 12}]]
                    },

                    {type: "PanelComponent", id: "nssDocVentanilla", //PANEL 5
                        components: [
                            //LISTA DE DOCUMENTOS NSS ORIGEN
                            //Agregar cambio
                            {type: "NssDocumentsComponent", id: "gridNssVentanillaInfoSol", model: "gridNssVentanilla", eliminar: "false", agregado:"true", observaciones : "true", editable: "false"}
                        ],
                        layout: [[{span: 12}]]
                    },

                    {type: "PanelComponent", id: "beneficiario", label: "Beneficiario o Representante Legal", //PANEL 6
                        components: [
                            {type: "TextFieldComponent", field: "informacionBeneficiario.parentesco", label: "Beneficiario", disabled: true, labelStyle: true, id: "beneficiarioText"},
                            {type: "LabelComponent", label: "Representante Legal", className: "control-label", id: "representanteLabel"},

                            {type: "TextFieldComponent", field: "informacionBeneficiario.curp", label: "CURP", disabled: true, labelStyle: true},
                            {type: "TextAreaFieldComponent", field: "informacionBeneficiario.apellidoPaterno", label: "Primer apellido", disabled: true, labelStyle: true},

                            {type: "TextAreaFieldComponent", field: "informacionBeneficiario.apellidoMaterno", label: "Segundo apellido", disabled: true, labelStyle: true},
                            {type: "TextAreaFieldComponent", field: "informacionBeneficiario.nombre", label: "Nombre(s)", disabled: true, labelStyle: true},

                            {type: "TextFieldComponent", field: "informacionBeneficiario.sexo", label: "Sexo", disabled: true, labelStyle: true},

                            {type: "TextAreaFieldComponent", field: "informacionBeneficiario.curpsHistoricas", label: "CURPS Hist\u00F3ricas", disabled: true, rows: 3, id: "curpsHistoricasBeneficiarioText"},

                            {
                                type: "GridComponent",
                                title: "",
                                id: "gridDocumentosBeneficiario",
                                model: "gridDocumentosBeneficiario",
                                data: "fetchDocumentosBeneficiario",
                                index: true,
                                columns: [
                                    {label: "Documentos Beneficiario/Representante Legal", href: "mostrarDocumento", name: "tipoDocumento"}
                                ]

                            },
                        ],
                        layout: [[{span: 12}], [{span: 12}], [{span: 6}, {span: 6}], [{span: 6}, {span: 6}], [{span: 12}], [{span: 12}], [{span: 12}]]
                    },                   

                    {type: "PanelComponent", id: "contacto", //PANEL 8
                        components: [
                            {
                                type: "PanelComponent",
                                label: "Datos de contacto",
                                id: "panelDatosContacto",
                                components: [
                                    {type: "TextFieldComponent", field: "informacionRENAPO.telefonoFijo", label: "Tel\u00E9fono fijo", disabled: true, labelStyle: true},
                                    {type: "TextFieldComponent", field: "informacionRENAPO.telefonoMovil", label: "Tel\u00E9fono m\u00F3vil", disabled: true, labelStyle: true},
                                    {type: "TextFieldComponent", field: "informacionRENAPO.correoElectronico", label: "Correo electr\u00F3nico", disabled: true, labelStyle: true}
                                ],
                                layout: [[{span: 6}, {span: 6}], [{span: 6}]]
                            },
                            {
                                type: "PanelComponent",
                                label: "Observaciones",
                                id: "panelObservaciones",
                                components: [
                                    {type: "TextAreaFieldComponent", field: "observacion", disabled: true, rows: 6}
                                ],
                                layout: [[{span: 12}]]
                            },
                            {
                                type: "PanelComponent",
                                label: "Observaciones Subdelegaci\u00F3n",
                                id: "panelObservacionesSubdelegacion",
                                components: [
                                    {type: "TextAreaFieldComponent", field: "observacionSubdelegacion", disabled: true, rows: 6}
                                ],
                                layout: [[{span: 12}]]
                            },
                        ],
                        layout: [[{span: 12}], [{span: 12}], [{span: 12}]]
                    },
                    {type: "PanelComponent", id: "infoAdicional", //PANEL 9
                        components: [
                            {
                                type: "GridComponent",
                                title: "",
                                id: "gridDoctosAdicionalesAsegurado",
                                model: "gridDoctosAdicionalesAsegurado",
                                data: "fetchDocumentosAdicionales",
                                columns: [
                                    {label: "Tipo de Documento", text: "Documentos probatorios del Asegurado"},
                                    {label: "Documentos Adicionales", name: "tipoDocumento", href: "mostrarDocumento"}
                                ]
                            },
                            {
                                type: "GridComponent",
                                title: "",
                                id: "gridDocumentosNssAdicional",
                                model: "gridDocumentosNssAdicional",
                                data: "fetchDocumentosNssAdicionales",
                                columns: [
                                    {label: "Tipo de Documento", text: "Documentos probatorios del NSS"},
                                    {label: "NSS Involucrados", name: "nss"},
                                    {label: "Documentos Adicionales", href: "mostrarDocumento", listName: "documentos", name: "tipoDocumento", list: true}
                                ]
                            },
                            {
                                type: "GridComponent",
                                title: "",
                                id: "gridDocumentosBeneficiarioAdicionales",
                                model: "gridDocumentosBeneficiarioAdicionales",
                                data: "fetchDocumentosBeneficiarioAdicionales",
                                columns: [
                                    {label: "Tipo de Documento", text: "Documentos probatorios del Beneficiario / Representante Legal"},
                                    {label: "Parentesco", name: "parentesco"},
                                    {label: "Documentos Adicionales", href: "mostrarDocumento", listName: "documentos", name: "tipoDocumento", list: true}
                                ]
                            },
                        ],
                        layout: [[{span: 12}], [{span: 12}], [{span: 12}]]
                    },
                ]
            }//TERMINA COMPONENTES DE TABS


        ], //TERMINA COMPONENTES DE PANEL GENERAL
        layout: [[{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}]]
    };
    
    
    var model = this.render.getModel("consultaSolicitud");
    // console.log("Grid de Beneficiarios, " + JSON.stringify(model));
    if( !this.render.isEmpty( model ) && model.gridDocumentosBeneficiario){
      this.metadata.components[0].tabs.splice(5,1);
      this.metadata.components[0].labels.splice(5,1);
      this.metadata.components[0].components.splice(5,1);
    }
};