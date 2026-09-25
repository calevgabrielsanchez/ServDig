function CuentaIndividualNssComponent(render) {
  this.render = render;
}
CuentaIndividualNssComponent.prototype.draw = function (component) {
  this.component = component;
  var html = '<div id="cuentaIndividual_' + this.render.toId(this.component.id) + '" >';
  html += this.drawBody();
  html += '</div>';
  return html;
};
CuentaIndividualNssComponent.prototype.drawBody = function () {
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
      { type:"HRComponent", className:"header"},
      {
        type: "FormPanelComponent", entity: "cuentaIndividual",
        model: this.component.model, name: this.component.model,
        className: "form", style: " height:0px",
        components: [
          {type: "ButtonComponent", icon: "pencil",className:"btn-primary btn-sm",
            trigger: {
              type: "CuentaIndividualAseguradoComponent",
              event: "edit",
              key: this.component.parentId,
              model: ""
            }},          
          {type: "LabelComponent", className:"label-cda",  label: ""+model.numeroRP + " - " + model.nombreRP},          
          {type: "LabelComponent", className:"label-cda",  label: model.claveDelegacionOrigen + " - " + model.nombreDelegacionOrigen},
          {type: "LabelComponent", className:"label-cda",  label: "CIZ: " + model.claveCiz},          
          {type: "LabelComponent", className:"label-cda",  label: "NSS Destino: "},
          {type: "SelectFieldComponent", field: "nssDestino", label: "", elements: model.elements,
            id: this.render.toId( this.component.id + ".nssDestino"),
             onChangeEvent:{
               type: "CuentaIndividualNssComponent",
               event: "change",
               key: this.component.id,
               model: this.component.model
             }
           }
        ],
        layout: [[{span: 1}, {span: 3}, {span: 3}, {span: 2}, {span: 1}, {span: 2}]]
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
        layout: [[{span:2},{span:1},{span:1},{span:2},{span:1},{span:1},{span:1},{span:1},{span:1},{span:1}]]
      },
      {
        type: "PanelComponent",
        components: [          
          {type: "LabelComponent", className:"label-cda",  label: "Ori Mov"},
          {type: "LabelComponent", className:"label-cda",  label: "Fec Mov Fin"},
          {type: "LabelComponent", className:"label-cda",  label: "Consec"},
          {type: "LabelComponent", className:"label-cda",  label: "NSS Destino"},
          {type: "LabelComponent", className:"label-cda",  label: "Regularizaci\u00f3n"},
          {type: "LabelComponent", className:"label-cda",  label: "Consecutivo"}
        ],
        layout: [[{span:1},{span:2},{span:2},{span:3},{span:2},{span:2}]]
      },
      {
        type: "IteratorComponent", indexName: "index",
        id: this.component.id + ".periodos",
        model: this.component.model + ".periodos",
        entry: {
          type: "PeriodoCuentaIndividualNssComponent",
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
          type: "PeriodoCuentaIndividualNssComponent",
          parentId: this.component.id, element: "index",
          id: this.component.id + ".periodosNuevos[index]",
          model: this.component.model + ".periodosNuevos[index]"
        }
      },
      { type:"HRComponent", className:"header"}
    ],
    layout: [ [{span: 12}], [{span: 12}], [{span: 12}], [{span: 6}, {span: 6}], [{span: 12}], [{span: 12}], [{span: 12}] ]
  };

  var html = "";  
  var panel = new PanelComponent(this.render);
  html += panel.draw( metadata );
  return html;

};

CuentaIndividualNssComponent.prototype.notify = function (metadata, modelName) {
  this.component = metadata;
  var html = this.drawBody();
  $("#cuentaIndividual_" + this.render.toId(this.component.id)).html(html);
};

CuentaIndividualNssComponent.prototype.edit = function () {
  
  
  var model = this.render.getModel(this.component.model);

  this.component.state = "edit";
  // Almacenar el cambio del NSS

  this.render.module.validator.formToModel(
          this.render.toId("FormPanelComponent-" + this.component.model),
          model);

  
  
  this.render.notify("CuentaIndividualNssComponent", this.component.id);
};

CuentaIndividualNssComponent.prototype.change = function (parameters) {
  var model = this.render.getModel(parameters);
  var i;
  model.nssDestino = $("#" + this.render.toId( parameters + ".nssDestino") ).val();
  for(i=0;i<model.periodos.length;i++){
    $("#" + this.render.toId( parameters + ".periodos["+i+"].nss") ).val(model.nssDestino);
  }
  
};

CuentaIndividualNssComponent.prototype.triggerEvent = function (event, parameters) {
  switch (event) {
    case "edit":
      this.edit(parameters);
    break;
    case "change":
      this.change(parameters);
    break;
  }
};

