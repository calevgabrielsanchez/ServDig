function CuentaIndividualEditComponent(render) {
  this.render = render;
}
CuentaIndividualEditComponent.prototype.draw = function (component) {
  this.component = component;
  var html = '<div id="cuentaIndividual_' + this.render.toId(this.component.id) + '" >';
  html += this.drawBody();
  html += '</div>';
  return html;
};
CuentaIndividualEditComponent.prototype.drawBody = function () {
  this.component.state = "edit";
  var model = this.render.getModel(this.component.model);
  var j;
  if (model === undefined) {
    model = {};
  }
  var metadata = {
    type: "PanelComponent",
    components: [
      { type:"HRComponent", className:"header"},
      {
        type: "FormPanelComponent", entity: "cuentaIndividual",
        model: this.component.model, name: this.component.model,
        className: "form", style: " height:0px",
        components: [
          {type: "ButtonComponent", icon: "plus", className:"btn-primary btn-sm",
            trigger: {
              type: "CuentaIndividualEditComponent",
              event: "add",
              key: this.component.id,
              model: ""
            }},
          {type: "LabelComponent", className:"label-cda",  label: ""+model.numeroRP+" - " + model.nombreRP},
          {type: "LabelComponent", className:"label-cda",  label: model.claveDelegacionOrigen + " - " + model.nombreDelegacionOrigen},
          {type: "LabelComponent", className:"label-cda",  label: "Clave CIZ: " + model.claveCiz }
        ],
        layout: [[{span: 1}, {span: 3}, {span: 3}, {span: 5}]]
      },
      { type:"HRComponent", className:"header"},
      {
        type: "PanelComponent",
        components: [
          {type: "LabelComponent", className:"label-cda",  label: "Recep Mov Ini"},
          {type: "LabelComponent", className:"label-cda",  label: "Mov Ini"},
          {type: "LabelComponent", className:"label-cda",  label: "Ori Mov"},
          {type: "LabelComponent", className:"label-cda",  label: "Fec Mov Ini"},
          {type: "LabelComponent", className:"label-cda",  label: "SBC"},
          {type: "LabelComponent", className:"label-cda",  label: "T SBC"},
          {type: "LabelComponent", className:"label-cda",  label: "TT"},
          {type: "LabelComponent", className:"label-cda",  label: "EXT"},
          {type: "LabelComponent", className:"label-cda",  label: "SS"},
          {type: "LabelComponent", className:"label-cda",  label: "Mov Fin"}
        ],
        layout: [[{span: 2}, {span: 1}, {span: 1}, {span: 2}, {span: 2}, {span: 1}, {span: 1}, {span: 1}, {span: 1}]]
      },
      {
        type: "PanelComponent",
        components: [
          {type: "LabelComponent", className:"label-cda",  label: "Ori Mov Fin"},
          {type: "LabelComponent", className:"label-cda",  label: "Fec Mov Fin"},
          {type: "LabelComponent", className:"label-cda",  label: "Jornada Semanal"},
          {type: "LabelComponent", className:"label-cda",  label: "Regularizaci\u00f3n"},
          {type: "LabelComponent", className:"label-cda",  label: "Cambio"},
          {type: "LabelComponent", className:"label-cda",  label: "Acci\u00f3n"}
        ],
        layout: [[{span: 2}, {span: 2}, {span: 2}, {span: 2}, {span: 3}, {span: 1}]]
      },
      {
        type: "IteratorComponent", indexName: "index",
        id: this.component.id + ".periodos",
        model: this.component.model + ".periodos",
        entry: {
          type: "PeriodoCuentaIndividualEditComponent",
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
          type: "PeriodoCuentaIndividualEditComponent",
          parentId: this.component.id, element: "index",
          id: this.component.id + ".periodosNuevos[index]",
          model: this.component.model + ".periodosNuevos[index]"
        }
      },
      { type:"HRComponent", className:"header"}
    ],
    layout: [ [{span: 12}], [{span: 12}], [{span: 12}], [{span: 6}, {span: 6}], [{span: 12}] , [{span: 12}] , [{span: 12}] ]
  };

  var html = "";
  var panel = new PanelComponent(this.render);
  html += panel.draw(metadata);
  this.render.module.render.volatileComponents[this.render.module.render.volatileComponents.length] = metadata;
  return html;
};


CuentaIndividualEditComponent.prototype.notify = function (metadata, modelName) {
  this.component = metadata;  
  var html = this.drawBody();
  $("#cuentaIndividual_" + this.render.toId(this.component.id)).html(html);
};
CuentaIndividualEditComponent.prototype.add = function () {
  var model = this.render.getModel(this.component.model);
  if ( this.render.isEmpty( model.periodosNuevos ) ) {
    model.periodosNuevos = [];
  }
  // Almacenar los cambios de los periodos nuevos
  for (i = 0; i < model.periodosNuevos.length; i++) {
    this.render.module.validator.formToModel(
            this.render.toId("FormPanelComponent-" + this.component.model + ".periodosNuevos[" + i + "]"),
            model.periodosNuevos[i]
            );
  }
  model.periodosNuevos[model.periodosNuevos.length] = {state: "add"};
  this.render.notify("IteratorComponent", this.component.id + ".periodosNuevos");
};


CuentaIndividualEditComponent.prototype.remove = function (index) {
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

CuentaIndividualEditComponent.prototype.triggerEvent = function (event, parameters) {
  switch (event) {
    case "add":
      this.add();
      break;
    case "remove":
      this.remove(parameters);
      break;    
  }
};