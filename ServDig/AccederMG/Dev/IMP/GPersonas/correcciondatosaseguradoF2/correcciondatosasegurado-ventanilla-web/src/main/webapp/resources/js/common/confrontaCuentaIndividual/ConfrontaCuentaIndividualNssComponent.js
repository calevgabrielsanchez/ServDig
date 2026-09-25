function ConfrontaCuentaIndividualNssComponent(render) {
  this.render = render;
}
ConfrontaCuentaIndividualNssComponent.prototype.draw = function (component) {
  this.component = component;
  var html = '<div id="cuentaIndividual_' + this.render.toId(this.component.id) + '" >';
  html += this.drawBody();
  html += '</div>';
  return html;
};
ConfrontaCuentaIndividualNssComponent.prototype.drawBody = function () {
  var model = this.render.getModel(this.component.model);
  var j;
  if (model === undefined) {
    model = {};
  }
  model.elements = [];
  for (j = 0; j < model.listaNss.length; j++) {
    model.elements[model.elements.length] = {label: model.listaNss[j], value: model.listaNss[j]};
  }
  var metadata = {
    type: "PanelComponent",
    components: [
      { type:"HRComponent", className:"primary"},
      {
        type: "PanelComponent", entity: "cuentaIndividual",        
        style: " height:0px",
        components: [              
          {type: "LabelComponent", className:"label-cda",  label: "Registro Patronal: "+model.numeroRegistroPatronal+" - " + this.render.nvl(model.nombreRegistroPatronal,"")},
          {type: "LabelComponent", className:"label-cda",  label: "Delegaci\u00f3n: "+model.claveDelegacionOrigen + " - " + model.nombreDelegacionOrigen},
          {type: "LabelComponent", className:"label-cda",  label: "CIZ: " + model.claveCiz }
        ],
        layout: [[{span: 6}, {span: 4}, {span: 2}]]
      },
      { type:"HRComponent", className:"default"},
      {
        type: "PanelComponent", layout: [[{span: 5}, {span: 2},{span: 5}]],
        components: [
          {type: "PanelComponent", layout: [[{span: 3}, {span: 2}, {span: 1},{span: 2}, {span: 2}, {span: 2}]],
            components: [
              {type: "LabelComponent", className:"label-cda",  label: "NSS Destino"},
              {type: "LabelComponent", className: "label-cda", label: "F RECEP"},
                {type: "LabelComponent", className: "label-cda", label: "O"},
                {type: "LabelComponent", className: "label-cda", label: "T"},
                {type: "LabelComponent", className: "label-cda", label: "FECHA"},
                {type: "LabelComponent", className: "label-cda", label: "SBC"}
            ]
          },
          {type: "PanelComponent", layout: [[{span: 2}, {span: 2}, {span: 2},{span: 2}, {span: 2}, {span: 2}]],
             components: [
             {type: "LabelComponent", className: "label-cda", label: "T SBC"},
                {type: "LabelComponent", className: "label-cda", label: "TT"},
                {type: "LabelComponent", className: "label-cda", label: "EXT"},
                {type: "LabelComponent", className: "label-cda", label: "SS"},
                {type: "LabelComponent", className: "label-cda", label: "O"},
                {type: "LabelComponent", className: "label-cda", label: "T"}
              ]
          },
          {type: "PanelComponent", layout: [[{span: 2}, {span: 2}, {span: 2},{span: 2}, {span: 2}, {span: 2}]],
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
          type: "ConfrontaPeriodoCuentaIndividualComponent",
          parentId: this.component.id, element: "index",
          id: this.component.id + ".periodos[index]",
          model: this.component.model + ".periodos[index]"
        }
      }
    ],
    layout: [ [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}] ]
  };

  var html = "";  
  var panel = new PanelComponent(this.render);
  html += panel.draw( metadata );
  return html;

};

ConfrontaCuentaIndividualNssComponent.prototype.notify = function (metadata, modelName) {
  this.component = metadata;
  var html = this.drawBody();
  $("#cuentaIndividual_" + this.render.toId(this.component.id)).html(html);
};
