function ConsultaAtencionResponsableUI(module) {
this.module = module;
        this.init();
        this.TAMANIO_TEXTAREA = "2em";
        this.TAMANIO_TEXTAREA_CADENA = 20;
        }

ConsultaAtencionResponsableUI.prototype.tamanioGrid = function () {
for (var i = 2; i < 5; i++){
if ($("#" + this.module.render.replaceAll(this.panel.components[i].field, ".", "_")).val().length < this.TAMANIO_TEXTAREA_CADENA){
$("#" + this.module.render.replaceAll(this.panel.components[i].field, ".", "_")).css("height", this.TAMANIO_TEXTAREA);
} else{
$("#" + this.module.render.replaceAll(this.panel.components[i].field, ".", "_")).css("word-wrap", "break-word");
}
}
if ($("#" + this.module.render.replaceAll(this.panel.components[9].field, ".", "_")).val() === ""){
$("#" + this.module.render.replaceAll(this.panel.components[9].field, ".", "_")).parent().parent().parent().parent().parent().remove();
}
};
        ConsultaAtencionResponsableUI.prototype.init = function() {
        this.panel = {
        type: "FormPanelComponent",
                id: "panelCorreccionDatos",
                name: "correccionDatos",
                model: "detalle",
                postFetch: "tamanioGridConsulta",
                label: "",
                level: 3,
                components: [
                {type:"LabelComponent", label:"Informaci&#243n de la Solicitud", name:"infoSolicitud", className:"h5"},
                {type:"linea"},
                {type: "TextFieldComponent", field: "informacionRENAPO.folio", label: "Folio", disabled: true, labelStyle:true},
                {type: "TextFieldComponent", field: "tipotramitesolicitud", label: "Tipo de tramite de la solicitud", disabled: true, labelStyle:true},
                {type:"linea"},
                {type:"LabelComponent", label:"Cuenta individual", name:"infoSolicitud", className:"h5"},
                {type:"EspacioComponent"},
//			             -------------------------------------------------------Tabcomponent	

                {
                type:"PanelTabComponent",
                        id:"panelTabsAsegurado",
                        //class: definir clase
                        tabs: ["certificador", "asociadosalasegurado", "nocorrespondealasegurado"],
                        labels: ["CERTIFICADOR", "ASOCIADO AL ASEGURADO", "NO CORRESPONDE AL ASEGURADO"],
                        components:[
                        {
                        type:"PanelComponent",
                                id:"certificador",
                                model:"detalle",
                                components:[
                                {
                                type : "LabelComponent",
                                        label : "Informaci\u00f3n  de cuenta individual del n\u00damero de seguridad social con el que se certificar\u00E1 el tr\u00E1mite"
                                },
                                {
                                type : "LabelComponent",
                                        label : "NSS"
                                },
                                {
                                type : "TextFieldComponent",
                                        field : "nss",
                                        disabled : true,
                                        labelStyle : true
                                },
                                {
                                type : "LabelComponent",
                                        label : "Tipo de Regularizaci\u00f3n"
                                },
                                {
                                type : "TextFieldComponent",
                                        field : "regularizacion",
                                        disabled : true,
                                        labelStyle : true
                                },
                                {
                                type : "LabelComponent",
                                        label : "Informaci\u00f3n  regularizada en el IMSS"
                                },
                                { type: "IteratorComponent", indexName: "index",
                                  id: "consultaSolicitud.cuentaIndividual.data",
                                  entry:{
                                    type: "CuentaIndividualComponent", id: "cuentas-index",
                                    model: "consultaSolicitud.cuentaIndividual.data[index]"
                                  }
                                },
                                {
                                type : "LabelComponent",
                                        label : "Informaci\u00f3n  previa en el IMSS"
                                },
                                { type: "IteratorComponent", indexName: "index",
                                  id: "consultaSolicitud.cuentaIndividualPrevia.data",
                                  entry:{
                                    type: "CuentaIndividualComponent", id: "cuentaIndividualPrevia-index",
                                    model: "consultaSolicitud.cuentaIndividualPrevia.data[index]"
                                  }
                                }
                        ],
                        layout : [ [ {
                        span : 12
                        } ], [ {
                        span : 3
                        }, {
                        span : 3
                        }, {
                        span : 3
                        }, {
                        span : 3
                        } ], [ {
                        span : 12
                        } ], [ {
                        span : 12
                        } ], [ {
                        span : 12
                        } ], [ {
                        span : 12
                        } ]]
                },
                {
                type:"PanelComponent",
                        id:"asociadosalasegurado",
                        components:[
                        {type:"LabelComponent", label:"Informaci&#243n de los n&#250meros de seguridad social que ser&aacuten cancelados por duplicidad.", name:"infoSolicitud", className:"h5"},
                        {type:"linea"},
                        {type: "TextoenlineaComponent", field: "informacionRENAPO.curp", labelNSS: "NSS", labelDETALLE: "DETALLE", disabled: true},
                        {type:"linea"},
                        {type: "TextFieldComponent", field: "informacionCIZ1.fechaNacimiento", label: "Fecha de nacimiento", disabled: false},
                        ],
                        layout: [[{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}]]
                },
                {
                type:"PanelComponent",
                        id:"nocorrespondealasegurado",
                        components:[
                        {type:"LabelComponent", label:"Informaci&#243n de los n&#250meros de seguridad social que no pertenecen al asegurado. ", name:"infoSolicitud", className:"h5"},
                        {type:"linea"},
                        {type: "TextoenlineaComponent", field: "informacionRENAPO.curp", labelNSS: "NSS", labelDETALLE: "DETALLE", disabled: true},
                        {type:"linea"}
//			            	            	             {type: "TextFieldComponent", field: "informacionCIZ2.sexo", label: "Sexo",disabled: false},

                        ],
			            	            	             layout: [[{span: 12}],[{span: 12}], [{span: 12}], [{span: 12}]]
                },
                ], layout:[[{span:12}], [{span:12}], [{span:12}], [{span:12}], [{span:12}], [{span:12}], [{span:12}]]

        },
        {
        type : "CardLayoutComponent",
                id : "responsableConfirmarButtonsCardLayout",
                components : [
                {
                type: "ButtonGroupComponent",
                        components: [
                        {
                        type : "ButtonComponent",
                                command : "bandeja",
                                label : "Salir",
                                className : "btn-danger"
                        },
                        {
                        type: "ButtonComponent",
                                id: "btnRegresarCorreccion",
                                label: "Regresar",
                                command: "consultaSolicitudController.regresarPreviaDetalle",
                                className: "btn-default"
                        },
                        {
                        type: "ButtonComponent",
                                label: "Continuar",
                                command: "consultaSolicitudController.continuarDetalleAtencionResponsable",
                                className: "btn-primary"
                        }
                        ]
                },
                {
                type: "ButtonGroupComponent",
                        components: [
                        {
                        type: "ButtonComponent",
                                id: "btnRegresarCorreccion",
                                label: "Regresar",
                                command: "regresarCorreccion",
                                className: "btn-default"
                        }
                        ]
                }
                ]
        }
        ],
                layout: [[{span:12}], [{span:12}], [{span:5}, {span:5}], [{span:12}], [{span:12}, {span:12}, {span:12}], [{span:12}], [{span:12}], [{span:12}], [{span:12}], [{span:12}], [{span:12}]]
        };
        };
