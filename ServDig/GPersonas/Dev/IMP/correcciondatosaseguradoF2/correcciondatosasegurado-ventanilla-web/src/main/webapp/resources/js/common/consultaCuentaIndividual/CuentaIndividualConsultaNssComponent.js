function CuentaIndividualConsultaNssComponent(render) {
  this.render = render;
}
CuentaIndividualConsultaNssComponent.prototype.draw = function (component) {
  this.component = component;
  var html = '<div id="cuentaIndividual_' + this.render.toId(this.component.id) + '" >';
  html += this.drawBody();
  html += '</div>';
  return html;
};
CuentaIndividualConsultaNssComponent.prototype.drawBody = function () {
  this.component.state = "edit";
  var model = this.render.getModel(this.component.model);
  var j;
  if (model === undefined) {
    model = {};
  }
  var html = "";


  


    var metadata = {
      type: "PanelComponent",
      components: [
        {type: "HRComponent", className: "primary"},
        {
          type: "PanelComponent", style: " height:0px",
          components: [
            {type: "LabelComponent", className: "label-cda", label: "" + model.numeroRegistroPatronal + " - " + model.nombreRegistroPatronal},
            {type: "LabelComponent", className: "label-cda", label: model.claveDelegacionOrigen + " - " + model.nombreDelegacionOrigen},
            {type: "LabelComponent", className: "label-cda", label: "Clave CIZ: " + model.claveCiz}
          ],
          layout: [[{span: 3}, {span: 3}, {span: 6}]]
        },
        {type: "HRComponent", className: "default"},
        {
          type: "PanelComponent", layout: [[{span: 5}, {span: 2}, {span: 5}]],
          components: [
            {type: "PanelComponent", layout: [[{span: 3}, {span: 2}, {span: 1}, {span: 2}, {span: 2}, {span: 2}]],
              components: [
                {type: "LabelComponent", className: "label-cda", label: "NSS Destino"},
                {type: "LabelComponent", className: "label-cda", label: "F RECEP"},
                {type: "LabelComponent", className: "label-cda", label: "O"},
                {type: "LabelComponent", className: "label-cda", label: "T"},
                {type: "LabelComponent", className: "label-cda", label: "FECHA"},
                {type: "LabelComponent", className: "label-cda", label: "SBC"}
              ]
            },
            {type: "PanelComponent", layout: [[{span: 2}, {span: 2}, {span: 2}, {span: 2}, {span: 2}, {span: 2}]],
              components: [
                {type: "LabelComponent", className: "label-cda", label: "T SBC"},
                {type: "LabelComponent", className: "label-cda", label: "TT"},
                {type: "LabelComponent", className: "label-cda", label: "EXT"},
                {type: "LabelComponent", className: "label-cda", label: "SS"},
                {type: "LabelComponent", className: "label-cda", label: "O"},
                {type: "LabelComponent", className: "label-cda", label: "T"}
              ]
            },
            {type: "PanelComponent", layout: [[{span: 2}, {span: 2}, {span: 2}, {span: 2}, {span: 2}, {span: 2}]],
              components: [
                {type: "LabelComponent", className: "label-cda", label: "FECHA"},
                {type: "LabelComponent", className: "label-cda", label: "JOR"},
                {type: "LabelComponent", className: "label-cda", label: "Regularizaci\u00f3n"},
                {type: "LabelComponent", className: "label-cda", label: "Estado"},
                {type: "LabelComponent", className: "label-cda", label: "Fec Proceso"},
                {type: "LabelComponent", className: "label-cda", label: "Consecutivo"}
              ]
            }
          ]
        },
        {
          type: "IteratorComponent", indexName: "index",
          id: this.component.id + ".periodos",
          model: this.component.model + ".periodos",
          entry: {
            type: "PeriodoCuentaIndividualConsultaComponent",
            parentId: this.component.id, element: "index",
            id: this.component.id + ".periodos[index]",
            model: this.component.model + ".periodos[index]"
          }
        },
        {
          type: "IteratorComponent", indexName: "index",
          id: this.component.id + ".periodosNuevos",
          model: this.component.model + ".periodosNuevos",
          entry: {
            type: "PeriodoCuentaIndividualConsultaComponent",
            parentId: this.component.id, element: "index",
            id: this.component.id + ".periodosNuevos[index]",
            model: this.component.model + ".periodosNuevos[index]"
          }
        }
      ],
      layout: [[{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}] ]
    };


    var panel = new PanelComponent(this.render);
    html += panel.draw(metadata);
    this.render.module.render.volatileComponents[this.render.module.render.volatileComponents.length] = metadata;
  
  return html;
};


CuentaIndividualConsultaNssComponent.prototype.notify = function (metadata, modelName) {
  this.component = metadata;
  var html = this.drawBody();
  $("#cuentaIndividual_" + this.render.toId(this.component.id)).html(html);
};
CuentaIndividualConsultaNssComponent.prototype.add = function () {
  var model = this.render.getModel(this.component.model);
  if (this.render.isEmpty(model.periodosNuevos)) {
    model.periodosNuevos = [];
  }
  // Almacenar los cambios de los periodos nuevos
  for (i = 0; i < model.periodosNuevos.length; i++) {
    this.render.module.validator.formToModel(
            this.render.toId("FormPanelComponent-" + this.component.model + ".periodosNuevos[" + i + "]"),
            model.periodosNuevos[i]
            );
  }
  // Valores por default del periodo nuevo
  model.periodosNuevos[model.periodosNuevos.length] = {state: "add",
    tipoMovimientoInicial: "0",
    origenMovimientoInicial: "0",
    tipoSalario: "0",
    eventual: "0",
    extemporaneoConvenioSuspension: "0",
    subrogacionServicio: "0",
    tipoMovimientoFinal: "0",
    origenMovimientoFinal: "0",
    jornadaSemanal: "0",
    tipoRegularizacionPeriodo: "A"

  };
  this.render.notify("IteratorComponent", this.component.id + ".periodosNuevos");
};


CuentaIndividualConsultaNssComponent.prototype.remove = function (index) {
  var model = this.render.getModel(this.component.model);
  var i;
  var element = JSON.parse(index);

  // Almacenar los cambios de los periodos nuevos
  for (i = 0; i < model.periodosNuevos.length; i++) {
    this.render.module.validator.formToModel(
            this.render.toId("FormPanelComponent-" + this.component.model + ".periodosNuevos[" + i + "]"),
            model.periodosNuevos[i]
            );
  }
  model.periodosNuevos.splice(element, 1);
  this.render.notify("IteratorComponent", this.component.id + ".periodosNuevos");
};

CuentaIndividualConsultaNssComponent.prototype.triggerEvent = function (event, parameters) {
  switch (event) {
    case "add":
      this.add();
      break;
    case "remove":
      this.remove(parameters);
      break;
  }
};