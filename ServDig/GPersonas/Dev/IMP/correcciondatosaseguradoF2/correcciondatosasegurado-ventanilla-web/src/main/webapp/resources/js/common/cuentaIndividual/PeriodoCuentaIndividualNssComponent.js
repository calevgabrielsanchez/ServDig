function PeriodoCuentaIndividualNssComponent(render) {
  this.render = render;
}
PeriodoCuentaIndividualNssComponent.prototype.draw = function (component) {
  this.component = component;
  var html = '<div id="periodoCuentaIndividual_' + this.render.toId(this.component.id) + '"  class="'  + '"  >';
  html += this.drawBody();
  html += '</div>';
  return html;
};
PeriodoCuentaIndividualNssComponent.prototype.notify = function (metadata, modelName) {
  this.component = metadata;
  var html = this.drawBody();
  $("#periodoCuentaIndividual_" + this.render.toId(this.component.id)).html(html);
};

PeriodoCuentaIndividualNssComponent.prototype.drawBody = function () {

  var model = this.render.getModel(this.component.model);
  var html = "";
  var metadata;
  
  
  switch(this.component.state){
      case "edit":
          switch (model.tipoRegularizacionPeriodo) {
              case "I":
                  metadata = this.readOnlyComponent();
              break;
              default:
                metadata = this.editComponent();
          }
          break;
      case "nss":
          switch (model.tipoRegularizacionPeriodo) {
              case "E":
              case "M":
              case "A":
                  metadata = this.readOnlyComponent();
              break;
              default:
                metadata = this.nssComponent();
          }
          break;
      case "add":
            metadata = this.addComponent();
          break;
      default:
        metadata = this.readOnlyComponent();
  }
  
  if( this.component.state === "edit" ){
      switch (model.tipoRegularizacionPeriodo) {
          case "I":
              metadata = this.readOnlyComponent();
          break;
          default:
            metadata = this.editComponent();
      }
      
  }else if(this.component.state === "nss") {
      switch (model.tipoRegularizacionPeriodo) {
          case "E":
          case "M":
              metadata = this.readOnlyComponent();
          break;
          default:
            metadata = this.nssComponent();
      }
  }
  
  var panelComponent = new FormPanelComponent(this.render);
  html += panelComponent.draw(metadata);
  this.render.module.render.addVolatileComponent(metadata);
  return html;
};



PeriodoCuentaIndividualNssComponent.prototype.readOnlyComponent = function () {
  var model = this.render.getModel(this.component.model);

  var descripcionTipoRegularizacion = "";
  var className;

  switch (model.tipoRegularizacionPeriodo) {
    case "E":
      className = "bg-danger";
      descripcionTipoRegularizacion = "Eliminar";
      break;
    case "A":
      className = "bg-success";
      descripcionTipoRegularizacion = "Agregar";
      break;
    case "M":
      className = "bg-warning";
      descripcionTipoRegularizacion = "Modificar";
      break;
    case "I":
      className = "bg-info";
      descripcionTipoRegularizacion = "Incluir";
  }
  
  if( model.fechaFinalMovimiento === "31/12/9999" ){
    model.fechaFinalMovimiento = "00/00/0000";
  }

  var metadata = {
    type: "FormPanelComponent", entity: "periodo",
    name: this.component.model,
    model: this.component.model,
    className: "form " + className,
    id: this.render.toId(this.component.model),
    components: [
      {
        type: "PanelComponent",
        components: [
          {type: "LabelComponent", label: model.fechaRecepcionMovimiento},
          {type: "LabelComponent", label: model.origenMovimientoInicial},
          {type: "LabelComponent", label: model.tipoMovimientoInicial},          
          {type: "LabelComponent", label: model.fechaInicioMovimiento},
          {type: "LabelComponent", label: model.salarioBase},
          {type: "LabelComponent", label: model.tipoSalario},
          {type: "LabelComponent", label: model.eventual},
          {type: "LabelComponent", label: model.extemporaneoConvenioSuspension},
          {type: "LabelComponent", label: model.subrogacionServicio}          
        ],
        layout: [[{span:2},{span:1},{span:1},{span:2},{span:2},{span:1},{span:1},{span:1},{span:1}]]
      },
      {
        type: "PanelComponent",
        components: [
          {type: "LabelComponent", label: model.origenMovimientoFinal},
          {type: "LabelComponent", label: model.fechaFinalMovimiento === "12/31/9999" ? "" : model.tipoMovimientoFinal},          
          {type: "LabelComponent", label: model.fechaFinalMovimiento === "12/31/9999" ? "00/00/0000" : model.fechaFinalMovimiento },
          {type: "LabelComponent", label: model.jornadaSemanal},          
          {type: "LabelComponent", label: model.cuentaIndividualNss.nss},
          {type: "LabelComponent", label: descripcionTipoRegularizacion},
          {type: "LabelComponent", label: model.indicadorConsecutivoMovimiento}
        ],
        layout: [[{span:1},{span:1},{span:2},{span:1},{span:3},{span:2},{span:2}]]
      }
    ],
    layout: [[{span: 6}, {span: 6}]]
  };
  return metadata;
};

PeriodoCuentaIndividualNssComponent.prototype.nssComponent = function () {
  var model = this.render.getModel(this.component.model);
  var parentModel = this.render.getModel(this.component.parentId);

  var descripcionTipoRegularizacion = "";
  var className;

  switch (model.tipoRegularizacionPeriodo) {
    case "E":
      className = "bg-danger";
      descripcionTipoRegularizacion = "Eliminar";
      break;
    case "A":
      className = "bg-success";
      descripcionTipoRegularizacion = "Agregar";
      break;
    case "M":
      className = "bg-warning";
      descripcionTipoRegularizacion = "Modificar";
      break;
    case "I":
      className = "bg-info";
      descripcionTipoRegularizacion = "Incluir";
  }
  
  console.log("ClassName: " + className );
  
  if( model.fechaFinalMovimiento === "31/12/9999" ){
    model.fechaFinalMovimiento = "00/00/0000";
  }

  var metadata = {
    type: "FormPanelComponent", entity: "periodo",
    name: this.component.model,
    model: this.component.model,
    className: "form " + className,
    id: this.render.toId(this.component.model),
    components: [
      {
        type: "PanelComponent",
        components: [
          {type: "LabelComponent", label: model.fechaRecepcionMovimiento},
          {type: "LabelComponent", label: model.origenMovimientoInicial},
          {type: "LabelComponent", label: model.tipoMovimientoInicial},          
          {type: "LabelComponent", label: model.fechaInicioMovimiento},
          {type: "LabelComponent", label: model.salarioBase},
          {type: "LabelComponent", label: model.tipoSalario},
          {type: "LabelComponent", label: model.eventual},
          {type: "LabelComponent", label: model.extemporaneoConvenioSuspension},
          {type: "LabelComponent", label: model.subrogacionServicio}          
        ],
        layout: [[{span:2},{span:1},{span:1},{span:2},{span:2},{span:1},{span:1},{span:1},{span:1}]]
      },
      {
        type: "PanelComponent",
        components: [
          {type: "LabelComponent", label: model.origenMovimientoFinal},
          {type: "LabelComponent", label: model.tipoMovimientoFinal},
          {type: "LabelComponent", label: model.fechaFinalMovimiento},
          {type: "LabelComponent", label: model.jornadaSemanal},          
          {type: "SelectFieldComponent", className:"selectCI",  field: "cuentaIndividualNss.cveIdDetalleNssCda", elements: parentModel.elements,
            id: this.render.toId(this.component.id + ".cuentaIndividualNss.cveIdDetalleNssCda"),
            onChangeEvent: {
              type: "PeriodoCuentaIndividualNssComponent",
              event: "changeNss",
              key: this.component.id,
              model: this.component.model
            }
          },
          {type: "LabelComponent", label: descripcionTipoRegularizacion},
          {type: "LabelComponent", label: model.indicadorConsecutivoMovimiento}
        ],
        layout: [[{span:1},{span:1},{span:2},{span:1},{span:3},{span:2},{span:2}]]
      }
    ],
    layout: [[{span: 6}, {span: 6}]]
  };
  return metadata;
};

PeriodoCuentaIndividualNssComponent.prototype.editComponent = function () {
  var model = this.render.getModel(this.component.model);
  var parentModel = this.render.getModel(this.component.parentId);

  var descripcionTipoRegularizacion = "";
  var className;
  this.render.module.controller.model[this.render.toId(this.component.id + ".buttonGroup")] = [false,true];
  switch (model.tipoRegularizacionPeriodo) {
    case "E":
      className = "bg-danger";
      descripcionTipoRegularizacion = "Eliminar";
      this.render.module.controller.model[this.render.toId(this.component.id + ".buttonGroup")] = [true,false];
      break;
    case "A":
      className = "bg-success";
      descripcionTipoRegularizacion = "Agregar";
      this.render.module.controller.model[this.render.toId(this.component.id + ".buttonGroup")] = [false,true];
      break;
    case "M":
      className = "bg-warning";
      descripcionTipoRegularizacion = "Modificar";
      this.render.module.controller.model[this.render.toId(this.component.id + ".buttonGroup")] = [true,false];
      break;
    case "I":
      className = "bg-darkgray";
      descripcionTipoRegularizacion = "Incluir";
  }
  
  if( model.fechaFinalMovimiento === "31/12/9999" ){
    model.fechaFinalMovimiento = "00/00/0000";
  }

  var metadata = {
    type: "FormPanelComponent", entity: "periodo",
    name: this.component.model,
    model: this.component.model,
    className: "form " + className,
    id: this.render.toId(this.component.model),
    components: [
      {
        type: "PanelComponent",
        components: [
          {type: "DatePickerFieldComponent", id: this.render.toId(this.component.id + ".fechaRecepcionMovimiento"), disabled: "" + (model.state !== "add"), field: "fechaRecepcionMovimiento"},          
          {type: "SelectFieldComponent", className:"selectCI",  field: "origenMovimientoInicial",
            id: this.render.toId(this.component.id + ".origenMovimientoInicial"),
            onChangeEvent: {
              type: "PeriodoCuentaIndividualNssComponent",
              event: "change",
              key: this.component.id,
              model: "origenMovimientoInicial"
            },
            elements: [
              {label: "0", value: "0"},
              {label: "1", value: "1"},
              {label: "2", value: "2"},
              {label: "3", value: "3"},
              {label: "4", value: "4"},
              {label: "5", value: "5"},
              {label: "6", value: "6"},
              {label: "7", value: "7"},
              {label: "8", value: "8"},
              {label: "9", value: "9"}
            ]},
          {type: "SelectFieldComponent", className:"selectCI", 
            id: this.render.toId(this.component.id + ".tipoMovimientoInicial"),
            onChangeEvent: {
              type: "PeriodoCuentaIndividualNssComponent",
              event: "change",
              key: this.component.id,
              model: "tipoMovimientoInicial"
            },
            field: "tipoMovimientoInicial", elements: [{label: "1", value: "1"}, {label: "8", value: "8"}, {label: "7", value: "7"}]},
          {type: "DatePickerFieldComponent", id: this.render.toId(this.component.id + ".fechaInicioMovimiento"), disabled: "" + (model.state !== "add"), field: "fechaInicioMovimiento"},
          {type: "TextFieldComponent", disabled: "" + (model.state !== "add"), field: "salarioBase"},
          {type: "SelectFieldComponent", className:"selectCI",  field: "tipoSalario",
            id: this.render.toId(this.component.id + ".tipoSalario"),
            onChangeEvent: {
              type: "PeriodoCuentaIndividualNssComponent",
              event: "change",
              key: this.component.id,
              model: "tipoSalario"
            },
            elements: [
              {label: "0", value: "0"},
              {label: "1", value: "1"},
              {label: "2", value: "2"},
              {label: "3", value: "3"},
              {label: "4", value: "4"},
              {label: "5", value: "5"},
              {label: "6", value: "6"},
              {label: "7", value: "7"},
              {label: "8", value: "8"},
              {label: "9", value: "9"}
            ]},
          {type: "SelectFieldComponent", className:"selectCI",  field: "eventual", id: this.render.toId(this.component.id + ".eventual"),
            onChangeEvent: {
              type: "PeriodoCuentaIndividualNssComponent",
              event: "change",
              key: this.component.id,
              model: "eventual"
            },
            elements: [
              {label: "0", value: "0"},
              {label: "1", value: "1"},
              {label: "2", value: "2"},
              {label: "3", value: "3"},
              {label: "4", value: "4"}
            ]
          },
          {type: "SelectFieldComponent", className:"selectCI",  field: "extemporaneoConvenioSuspension",
            id: this.render.toId(this.component.id + ".extemporaneoConvenioSuspension"),
            onChangeEvent: {
              type: "PeriodoCuentaIndividualNssComponent",
              event: "change",
              key: this.component.id,
              model: "extemporaneoConvenioSuspension"
            },
            elements: [
              {label: "0", value: "0"},
              {label: "1", value: "1"},
              {label: "2", value: "2"},
              {label: "3", value: "3"},
              {label: "4", value: "4"},
              {label: "5", value: "5"},
              {label: "6", value: "6"},
              {label: "7", value: "7"},
              {label: "8", value: "8"},
              {label: "9", value: "9"}
            ]},
          {type: "SelectFieldComponent", className:"selectCI",  field: "subrogacionServicio",
            id: this.render.toId(this.component.id + ".subrogacionServicio"),
            onChangeEvent: {
              type: "PeriodoCuentaIndividualNssComponent",
              event: "change",
              key: this.component.id,
              model: "subrogacionServicio"
            },
            elements: [
              {label: "0", value: "0"},
              {label: "1", value: "1"},
              {label: "2", value: "2"},
              {label: "3", value: "3"},
              {label: "4", value: "4"},
              {label: "5", value: "5"},
              {label: "6", value: "6"},
              {label: "7", value: "7"},
              {label: "8", value: "8"},
              {label: "9", value: "9"}
            ]}
          
        ],
        layout: [[{span:2},{span:1},{span:1},{span:2},{span:2},{span:1},{span:1},{span:1},{span:1}]]
      },
      {
        type: "PanelComponent",
        components: [
          {type: "SelectFieldComponent", className:"selectCI",  field: "origenMovimientoFinal",
            id: this.render.toId(this.component.id + ".origenMovimientoFinal"),
            onChangeEvent: {
              type: "PeriodoCuentaIndividualNssComponent",
              event: "change",
              key: this.component.id,
              model: "origenMovimientoFinal"
            },
            elements: [
              {label: "0", value: "0"},
              {label: "1", value: "1"},
              {label: "2", value: "2"},
              {label: "3", value: "3"},
              {label: "4", value: "4"},
              {label: "5", value: "5"},
              {label: "6", value: "6"},
              {label: "7", value: "7"},
              {label: "8", value: "8"},
              {label: "9", value: "9"}
            ]},
          {type: "SelectFieldComponent", className:"selectCI",  field: "tipoMovimientoFinal",
            id: this.render.toId(this.component.id + ".tipoMovimientoFinal"),
            onChangeEvent: {
              type: "PeriodoCuentaIndividualNssComponent",
              event: "change",
              key: this.component.id,
              model: "tipoMovimientoFinal"
            },
            elements: [
              {label: "0", value: "0"},
              {label: "2", value: "2"},
              {label: "7", value: "7"}
            ]},          
            {type: "DatePickerFieldComponent", id: this.render.toId(this.component.id + ".fechaFinalMovimiento"), field: "fechaFinalMovimiento"
        , onChangeEvent: {
          type: "PeriodoCuentaIndividualNssComponent",
          event: "change",
          key: this.component.id,
          model: "fechaFinalMovimiento"
        }
      },
      {type: "SelectFieldComponent", className:"selectCI",  field: "jornadaSemanal",
                    id: this.render.toId(this.component.id + ".jornadaSemanal"),
                    onChangeEvent: {
                      type: "PeriodoCuentaIndividualNssComponent",
                      event: "change",
                      key: this.component.id,
                      model: "jornadaSemanal"
                    },
                    elements: [
                      {label: "0", value: "0"},
                      {label: "1", value: "1"},
                      {label: "2", value: "2"},
                      {label: "3", value: "3"},
                      {label: "4", value: "4"},
                      {label: "5", value: "5"},
                      {label: "6", value: "6"},
                      {label: "7", value: "7"},
                      {label: "8", value: "8"},
                      {label: "9", value: "9"}
                    ]},      
      {type: "LabelComponent", label: model.cuentaIndividualNss.nss},
      {type: "TextFieldComponent", disabled: "true",
        id: this.render.toId(this.component.id + ".tipoRegularizacionPeriodo"),
        field: "tipoRegularizacionPeriodo"},
      {type: "ButtonGroupComponent", id: this.render.toId(this.component.id + ".buttonGroup"),
        components: [
          {type: "ButtonComponent", 
            label: "", icon: "remove", className: "btn-danger btn-sm",
            trigger: {
              type: "PeriodoCuentaIndividualNssComponent",
              event: "delete",
              key: this.component.id,
              model: this.component.id }
          },
          {type: "ButtonComponent", label: "", icon: "circle-arrow-left", className: "btn-warning btn-sm",
            trigger: {
              type: "PeriodoCuentaIndividualNssComponent",
              event: "undo",
              key: this.component.id,
              model: this.component.id }
          }
        ]
      }
        ],
        layout: [[{span:1},{span:1},{span:2},{span:1},{span:3},{span:2},{span:2}]]
      }
    ],
    layout: [[{span: 6}, {span: 6}]]
  };
  return metadata;
};

PeriodoCuentaIndividualNssComponent.prototype.addComponent = function () {
  var model = this.render.getModel(this.component.model);
  var parentModel = this.render.getModel(this.component.parentId);

  var descripcionTipoRegularizacion = "Agregar";
  var className = "bg-success";
  
  var metadata = {
    type: "FormPanelComponent", entity: "periodo",
    name: this.component.model,
    model: this.component.model,
    className: "form " + className,
    id: this.render.toId(this.component.model),
    components: [
      {
        type: "PanelComponent",
        components: [
          {type: "DatePickerFieldComponent", id: this.render.toId(this.component.id + ".fechaRecepcionMovimiento"),  field: "fechaRecepcionMovimiento"},          
          {type: "SelectFieldComponent", className:"selectCI",  field: "origenMovimientoInicial",
            id: this.render.toId(this.component.id + ".origenMovimientoInicial"),
            elements: [
              {label: "0", value: "0"},
              {label: "1", value: "1"},
              {label: "2", value: "2"},
              {label: "3", value: "3"},
              {label: "4", value: "4"},
              {label: "5", value: "5"},
              {label: "6", value: "6"},
              {label: "7", value: "7"},
              {label: "8", value: "8"},
              {label: "9", value: "9"}
            ]},
          {type: "SelectFieldComponent", className:"selectCI",  id: this.render.toId(this.component.id + ".tipoMovimientoInicial"),
            field: "tipoMovimientoInicial", elements: [{label: "1", value: "1"}, {label: "8", value: "8"}, {label: "7", value: "7"}]},
          {type: "DatePickerFieldComponent", id: this.render.toId(this.component.id + ".fechaInicioMovimiento"),  field: "fechaInicioMovimiento"},
          {type: "TextFieldComponent", field: "salarioBase"},
          {type: "SelectFieldComponent", className:"selectCI",  field: "tipoSalario",
            id: this.render.toId(this.component.id + ".tipoSalario"),
            elements: [
              {label: "0", value: "0"},
              {label: "1", value: "1"},
              {label: "2", value: "2"},
              {label: "3", value: "3"},
              {label: "4", value: "4"},
              {label: "5", value: "5"},
              {label: "6", value: "6"},
              {label: "7", value: "7"},
              {label: "8", value: "8"},
              {label: "9", value: "9"}
            ]},
          {type: "SelectFieldComponent", className:"selectCI",  field: "eventual", id: this.render.toId(this.component.id + ".eventual"),
            elements: [
              {label: "0", value: "0"},
              {label: "1", value: "1"},
              {label: "2", value: "2"},
              {label: "3", value: "3"},
              {label: "4", value: "4"}
            ]
          },
          {type: "SelectFieldComponent", className:"selectCI",  field: "extemporaneoConvenioSuspension",
            id: this.render.toId(this.component.id + ".extemporaneoConvenioSuspension"),
            elements: [
              {label: "0", value: "0"},
              {label: "1", value: "1"},
              {label: "2", value: "2"},
              {label: "3", value: "3"},
              {label: "4", value: "4"},
              {label: "5", value: "5"},
              {label: "6", value: "6"},
              {label: "7", value: "7"},
              {label: "8", value: "8"},
              {label: "9", value: "9"}
            ]},
          {type: "SelectFieldComponent", className:"selectCI",  field: "subrogacionServicio",
            id: this.render.toId(this.component.id + ".subrogacionServicio"),
            elements: [
              {label: "0", value: "0"},
              {label: "1", value: "1"},
              {label: "2", value: "2"},
              {label: "3", value: "3"},
              {label: "4", value: "4"},
              {label: "5", value: "5"},
              {label: "6", value: "6"},
              {label: "7", value: "7"},
              {label: "8", value: "8"},
              {label: "9", value: "9"}
            ]}
          
        ],
        layout: [[{span:2},{span:1},{span:1},{span:2},{span:2},{span:1},{span:1},{span:1},{span:1}]]
      },
      {
        type: "PanelComponent",
        components: [
          {type: "SelectFieldComponent", className:"selectCI",  field: "origenMovimientoFinal",
            id: this.render.toId(this.component.id + ".origenMovimientoFinal"),
            elements: [
              {label: "0", value: "0"},
              {label: "1", value: "1"},
              {label: "2", value: "2"},
              {label: "3", value: "3"},
              {label: "4", value: "4"},
              {label: "5", value: "5"},
              {label: "6", value: "6"},
              {label: "7", value: "7"},
              {label: "8", value: "8"},
              {label: "9", value: "9"}
            ]},
          {type: "SelectFieldComponent", className:"selectCI",  field: "tipoMovimientoFinal",
            id: this.render.toId(this.component.id + ".tipoMovimientoFinal"),
            onChangeEvent: {
              type: "PeriodoCuentaIndividualNssComponent",
              event: "change",
              key: this.component.id,
              model: "tipoMovimientoFinal"
            },
            elements: [
              {label: "0", value: "0"},
              {label: "2", value: "2"},
              {label: "7", value: "7"}
            ]},
          
            {type: "DatePickerFieldComponent", id: this.render.toId(this.component.id + ".fechaFinalMovimiento"), field: "fechaFinalMovimiento"},
            {type: "SelectFieldComponent", className:"selectCI",  field: "jornadaSemanal",
                    id: this.render.toId(this.component.id + ".jornadaSemanal"),                    
                    elements: [
                      {label: "0", value: "0"},
                      {label: "1", value: "1"},
                      {label: "2", value: "2"},
                      {label: "3", value: "3"},
                      {label: "4", value: "4"},
                      {label: "5", value: "5"},
                      {label: "6", value: "6"},
                      {label: "7", value: "7"},
                      {label: "8", value: "8"},
                      {label: "9", value: "9"}
                    ]},      
      {type: "LabelComponent", label: model.cuentaIndividualNss.nss},
      {type: "TextFieldComponent", disabled: "true",
        id: this.render.toId(this.component.id + ".tipoRegularizacionPeriodo"),
        field: "tipoRegularizacionPeriodo"},
      {type: "ButtonGroupComponent", id: this.render.toId(this.component.id + ".buttonGroup"),
        components: [
          {type: "ButtonComponent", 
            label: "", icon: "remove", className: "btn-danger btn-sm",
            trigger: {
              type: "RegistroPatronalComponent",
              event: "remove",
              key: this.component.parentId,
              model: this.component.element }
          }
        ]
      }
        ],
        layout: [[{span:1},{span:1},{span:2},{span:1},{span:3},{span:2},{span:2}]]
      }
    ],
    layout: [[{span: 6}, {span: 6}]]
  };
  return metadata;
};


PeriodoCuentaIndividualNssComponent.prototype.changed = function (field) {
  var i;
  // Se va invocar durante el render, por lo que hay que descartar el
  // evento si se tiene una marca de I,E
  var model = this.render.getModel(this.component.model);  
  var registroPatronal = this.render.getModel( this.component.parentId );
  
  if( model.tipoRegularizacionPeriodo === "E" || 
      model.tipoRegularizacionPeriodo === "I"   ){
          return;
  }
  // El campo tipoMovimientoFinal controla el fechaFinalMovimiento
  if (field === "tipoMovimientoFinal") {
    var tipoMovimientoFinal = $("#" + this.render.toId(this.component.id + "." + field)).val();
    if (tipoMovimientoFinal === "0") {
      $("#" + this.render.toId(this.component.id + ".fechaFinalMovimiento")).prop('disabled', true);
    } else {
      $("#" + this.render.toId(this.component.id + ".fechaFinalMovimiento")).prop('disabled', false);
    }
  }
  // Para evitar validar eventos del render se compara si el valor del campo del modelo
  // Y el de la forma tienen el mismo valor
  // El campo es igual al modelo, aun no hay cambios, es solo el render
  console.log("Validando el cambio: " + this.component.state);
  console.log( $("#" + this.render.toId(this.component.id + "." + field)).val() );
  console.log( ""+model[field] );
  
  if($("#" + this.render.toId(this.component.id + "." + field)).val() === ""+model[field] ){ return; }      
  if( this.render.isEmpty( model.changed ) ){ model.changed = []; }  
  if( this.render.isEmpty(model[field]) || model[field] === " " ){ model[field] = "";}
  //Actividades para el estado de edicion
  if( this.component.state === "edit" ||  this.component.state === "nss" ){
    //Controlar el cambio de color de los componentes y llevar un arreglo con los
    //campos que cambiaron
    var index;
    if("" + model[field] !== $("#" + this.render.toId(this.component.id + "." + field)).val()) {          
      $("#" + this.render.toId(this.component.id + "." + field)).css('background-color', "#fcf8e3");
      index = model.changed.indexOf(field);
      if(index === -1){
        model.changed[model.changed.length] = field;
      }
    } else {
      $("#" + this.render.toId(this.component.id + "." + field)).css('background-color', "");
      index = model.changed.indexOf(field);
      if(index > -1){
        model.changed.splice(index, 1);
      }
    }
    //Actualizar la estructura de correccion
    if (model.changed.length > 0) {
      // Buscar que no este ya incluido en la lista de modificados.
      var founded = false;
      for( i=0; i< registroPatronal.modificados.length; i++){
        if(registroPatronal.modificados[i].origen.cuentaIndividualPeriodo.cveIdPeriodoCuentaIndividual === model.cveIdPeriodoCuentaIndividual ){
          founded = true;
          break;
        }
      }
      var origen ={};
      for( i=0; i< registroPatronal.data.length; i++){
        if(registroPatronal.data[i].cveIdPeriodoCuentaIndividual === model.cveIdPeriodoCuentaIndividual ){
          origen = registroPatronal.data[i];
          break;
        }
      }
      if( !founded ){      
        var periodoModificado = { 
          origen:{ 
            cuentaIndividualNss: origen.cuentaIndividualNss, 
            cuentaIndividualPeriodo: origen, 
            tipoRegularizacionPeriodo: "M" }, 
          destino:{ 
            cuentaIndividualNss:{ cveIdDetalleNssCda: model.cuentaIndividualNss.cveIdDetalleNssCda , nss: model.cuentaIndividualNss.nss }, 
            cuentaIndividualPeriodo: model, 
            tipoRegularizacionPeriodo: "M" } 
        };      
        registroPatronal.modificados[registroPatronal.modificados.length] = periodoModificado;  
        model.tipoRegularizacionPeriodo = "M";      
      }
      model.state = "edit";
      model.tipoRegularizacionPeriodo = "M";
    }else{
      // Se modifico a mano y se regreso al original, por lo tanto se elimina
      // del listado de modificados.
      for( i=0; i< registroPatronal.modificados.length; i++){
        if(registroPatronal.modificados[i].origen.cuentaIndividualPeriodo.cveIdPeriodoCuentaIndividual === model.cveIdPeriodoCuentaIndividual ){
          registroPatronal.modificados.splice(i,1);
          break;
        }
      }
      model.state = "pristine";
      model.tipoRegularizacionPeriodo = "";
    }
  }
  //Almacenar el valor de la forma en el modelo
  model[field] = $("#" + this.render.toId(this.component.id + "." + field)).val();
  
  if( this.component.state === "add" ){
    // Actualizando modelo con el valor de la forma
    console.log(model);
    this.render.module.validator.formToModel(
      this.render.toId("FormPanelComponent-" + this.render.toId(this.component.model)),
      model );     
    model.tipoRegularizacionPeriodo = "A";
  }
  var myparameters = { type: 'RegistroPatronalComponent', event: 'change', key: this.component.parentId, model:"" };
  this.render.triggerEvent( window.btoa( JSON.stringify( myparameters )) );    
};


PeriodoCuentaIndividualNssComponent.prototype.changeNss = function (parameters) {
  var model = this.render.getModel(parameters);
  var i;
  if( this.render.isEmpty( model.incluidos ) ){
    model.incluidos = [];
  }
  var cveIdDetalleNssCdaSelected = $("#" + this.render.toId(parameters + ".cuentaIndividualNss.cveIdDetalleNssCda") ).val();
  console.log("#" + this.render.toId(parameters + ".cuentaIndividualNss.cveIdDetalleNssCda"));
  if( cveIdDetalleNssCdaSelected === undefined ){
    return;
  }
  
  var nssSelected = $("#" + this.render.toId(parameters + ".cuentaIndividualNss.cveIdDetalleNssCda" + " option:selected") ).text();
  cveIdDetalleNssCdaSelected = JSON.parse(cveIdDetalleNssCdaSelected);
  //model.cveIdDetalleNssCda = JSON.parse( model.cveIdDetalleNssCda );
  
  // Validar que realmente sea un cambio, es decir vista diferente al modelo
  if ( cveIdDetalleNssCdaSelected !== model.cuentaIndividualNss.cveIdDetalleNssCda) {
    model.cuentaIndividualNss.cveIdDetalleNssCda = cveIdDetalleNssCdaSelected;
    // Comparar el NSS del periodo con el NSS del registro Patronal
    var registroPatronal = this.render.getModel(this.component.parentId);
    registroPatronal.cveIdDetalleNssCda = JSON.parse( registroPatronal.cveIdDetalleNssCda );
    // Independiente del resultado si esta en Incluidos se debe de quitar
    console.log("Cambio de NSS: ");
    console.log("Origen:");
    console.log( registroPatronal.cveIdDetalleNssCda );
    console.log("Destino:");
    console.log( cveIdDetalleNssCdaSelected );
    if( registroPatronal.cveIdDetalleNssCda !== cveIdDetalleNssCdaSelected ){
      console.log("Diferencias con el registro patronal: ");
      // Son diferentes entonces se debe crear la estructura de Incluido
      // aunque ya puede existir en el arreglo ( con otro NSS ) lo buscamos por
      // la clave del registro Padre
      for(i=0;i<registroPatronal.incluidos.length;i++){
        console.log( model.cveIdPeriodoAnterior );
        console.log( registroPatronal.incluidos[i].destino.cuentaIndividualPeriodo.cveIdPeriodoAnterior );
        if( model.cveIdPeriodoAnterior === registroPatronal.incluidos[i].destino.cuentaIndividualPeriodo.cveIdPeriodoAnterior){
          // Solo cambiar el NSS
          registroPatronal.incluidos[i].destino.cuentaIndividualNss.cveIdDetalleNssCda = cveIdDetalleNssCdaSelected;
          registroPatronal.incluidos[i].destino.cuentaIndividualNss.nss = nssSelected;
          // Ya se tiene el modelo actualizado, se termina
          return;
        }
      }
      // Si no se tiene en la lista de periodos incluidos se debe crear el registro
      // No se encontro en los incluidos
      console.log(registroPatronal.incluidos);
      var origen ={};
      for( i=0; i< registroPatronal.data.length; i++){
        if(registroPatronal.data[i].cveIdPeriodoCuentaIndividual === model.cveIdPeriodoCuentaIndividual ){
          origen = registroPatronal.data[i];
          break;
        }
      }
      var periodoCopia = JSON.parse( JSON.stringify(model) );
      periodoCopia.cuentaIndividualNss = null;
      periodoCopia.cveIdPeriodoAnterior = model.cveIdPeriodoCuentaIndividual;
      var periodoIncluido = { 
        origen:{ 
          cuentaIndividualNss: origen.cuentaIndividualNss, 
          cuentaIndividualPeriodo: origen, 
          tipoRegularizacionPeriodo: "E" }, 
        destino:{ 
          cuentaIndividualNss:{ cveIdDetalleNssCda: cveIdDetalleNssCdaSelected , nss: nssSelected }, 
          cuentaIndividualPeriodo: periodoCopia, 
          tipoRegularizacionPeriodo: "A" } 
      };      
      registroPatronal.incluidos[registroPatronal.incluidos.length] = periodoIncluido;
      console.log(registroPatronal.incluidos);
      // Se actualiza los atributos de vista
      //$("#panel_" + this.render.toId(this.component.model)).addClass("bg-info");
      //$("#panel_" + this.render.toId( this.component.model)).children().addClass("bg-info");
      model.tipoRegularizacionPeriodo = "I"; 
      model.cveIdPeriodoAnterior = model.cveIdPeriodoCuentaIndividual;
      $("#FormPanelComponent-" + this.render.toId( this.component.model)).addClass("bg-info");
    }else{
      // Son iguales entonces se debe colocar el periodo original y quitarlo de lo incluidos
      model.tipoRegularizacionPeriodo = "";
      console.log("Buscando el periodo en los incluidos: " + registroPatronal.incluidos.length);
      for( i=0; i<registroPatronal.incluidos.length; i++){
        if( model.cveIdPeriodoAnterior === registroPatronal.incluidos[i].destino.cuentaIndividualPeriodo.cveIdPeriodoAnterior){
          // Solo cambiar el NSS
          console.log("Encontrado, actualizando y eliminando");
          model = registroPatronal.incluidos[i].origen.cuentaIndividualPeriodo;
          registroPatronal.incluidos.splice(i, 1);          
        }
      }
      console.log("Periodos Incluidos final:" + registroPatronal.incluidos.length );
      console.log("#FormPanelComponent-" + this.render.toId( this.component.model));
      $("#FormPanelComponent-" + this.render.toId( this.component.model)).removeClass("bg-info");      
      $("#" + this.render.toId( this.component.model)).removeClass("bg-info");
      $("#panel_" + this.render.toId( this.component.model)).children().removeClass("bg-info");
      
    }
    console.log("Notificando al componente: " + this.component.id );
    console.log("Tipo de regularizacion: " + model.tipoRegularizacionPeriodo);
    console.log( model );
    var myparameters = { type: 'RegistroPatronalComponent', event: 'change', key: this.component.parentId, model:"" };
    this.render.triggerEvent( window.btoa( JSON.stringify( myparameters )) );
    
  }  
};

PeriodoCuentaIndividualNssComponent.prototype.undo = function (parameters) {
  var model = this.render.getModel(this.component.model);
  var i;
  var eliminados = this.render.getModel( this.component.parentId + ".eliminados");
  var modificados = this.render.getModel( this.component.parentId + ".modificados");
  var periodos = this.render.getModel( this.component.parentId + ".periodos");
  var data = this.render.getModel( this.component.parentId + ".data");
  // Determinar el tipo de cambio a deshacer
  switch( model.tipoRegularizacionPeriodo ){
    case "E":
      if( !this.render.isEmpty( eliminados ) ){
        for( i=0; i< eliminados.length; i++){
          if(eliminados[i].origen.cuentaIndividualPeriodo.cveIdPeriodoCuentaIndividual  === model.cveIdPeriodoCuentaIndividual ){
            eliminados.splice(i,1);
            
            periodos[i] = JSON.parse( JSON.stringify( data[i]));
            break;
          }
        }
      }
    break;
    case "M":
      if( !this.render.isEmpty( modificados ) ){
        console.log("Eliminar los modificados");        
        for( i=0; i< modificados.length; i++){
          console.log( modificados[i].origen.cuentaIndividualPeriodo.cveIdPeriodoCuentaIndividual );
          console.log( model.cveIdPeriodoCuentaIndividual );
          if(modificados[i].destino.cuentaIndividualPeriodo.cveIdPeriodoCuentaIndividual  === model.cveIdPeriodoCuentaIndividual ){
            
            var periodo = modificados[i].origen.cuentaIndividualPeriodo;
            // console.log(JSON.stringify( periodo ));
            model.tipoMovimientoIncial = periodo.tipoMovimientoInicial;
            model.origenMovimientoIncial = periodo.origenMovimientoInicial;
            model.tipoSalario = periodo.tipoSalario;
            model.eventual = periodo.eventual;
            model.extemporaneoConvenioSuspension = periodo.extemporaneoConvenioSuspension;
            model.subrogacionServicio = periodo.subrogacionServicio;
            model.tipoMovimientoFinal = periodo.tipoMovimientoFinal;
            model.origenMovimientoFinal = periodo.origenMovimientoFinal;
            model.fechaFinalMovimiento = periodo.fechaFinalMovimiento;
            model.jornadaSemanal = periodo.jornadaSemanal;
            modificados.splice(i,1);
            
            console.log( modificados );
            periodos[i] = JSON.parse( JSON.stringify( data[i]));
            break;
          }
        }
      }
    break;
  }
  model.tipoRegularizacionPeriodo="";
  this.component.state = "edit";
  this.render.notify("PeriodoCuentaIndividualNssComponent", this.component.id);
  var myparameters = { type: 'RegistroPatronalComponent', event: 'change', key: this.component.parentId, model:"" };
  this.render.triggerEvent( window.btoa( JSON.stringify( myparameters )) ); 
  
};
PeriodoCuentaIndividualNssComponent.prototype.delete = function () {
  var model = this.render.getModel(this.component.model);  
  var registroPatronal = this.render.getModel( this.component.parentId );
  
  var periodoEliminado = { 
    origen:{ 
      cuentaIndividualNss:{ cveIdDetalleNssCda: model.cuentaIndividualNss.cveIdDetalleNssCda , nss: model.cuentaIndividualNss.nss }, 
      cuentaIndividualPeriodo: model, 
      tipoRegularizacionPeriodo: "E" }, 
    destino:{ 
      cuentaIndividualNss:{ }, 
      cuentaIndividualPeriodo: {}, 
      tipoRegularizacionPeriodo: "" } 
  };      
  registroPatronal.eliminados[registroPatronal.eliminados.length] = periodoEliminado;  
  model.tipoRegularizacionPeriodo = "E";
  
  this.render.notify("PeriodoCuentaIndividualNssComponent", this.component.id);
  var myparameters = { type: 'RegistroPatronalComponent', event: 'change', key: this.component.parentId, model:"" };
  this.render.triggerEvent( window.btoa( JSON.stringify( myparameters )) ); 
  
};
PeriodoCuentaIndividualNssComponent.prototype.triggerEvent = function (event, parameters) {
  switch (event) {
    case "change": this.changed(parameters); break;
    case "changeNss": this.changeNss(parameters); break;
    case "undo": this.undo(parameters); break;
    case "delete": this.delete(parameters); break;    
  }
};

