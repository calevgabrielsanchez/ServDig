function EditCorreccionDatosComponent(render) {
  this.render = render;
}
EditCorreccionDatosComponent.prototype.draw = function (component) {
  this.component = component;
  var html = '<div id="editCorreccionDatos_' + this.render.toId(this.component.id) + '" >';
  html += this.drawBody();
  html += '</div>';
  return html;
};
EditCorreccionDatosComponent.prototype.drawBody = function () {
  var model = this.render.getModel(this.component.model);
  var html = "";
  var currentNss = this.render.getModel("currentNss");
  if (currentNss === undefined) {
    currentNss = 0;
  }
  currentNss = parseInt(currentNss);

  if (model !== undefined) {
    var tipoNssDisabled = this.render.module.model.correccionDatos.getDisabledTipoNss(currentNss);
    var metadata = {
      type: "PanelComponent",
      components: [
        {type: "FormPanelComponent",
          name: this.component.model + ".listaNss[" + currentNss + "].tipoNss", model: this.component.model + ".listaNss[" + currentNss + "].tipoNss", entity: "tipoNss",
          label: "Tipo de NSS",
          components: [
            {type: "RadioGroupFieldComponent",
              field: "tipo",
              elements: [
                {value: "certificador", label: "Certificador", disabled: true,
                 

                },
                {value: "asociado", label: "Asociado al Certificador", disabled: true,
                  onChangeEvent: {
                    type: "EditCorreccionDatosComponent",
                    event: "changeCertificador",
                    key: this.component.id,
                    model: ""
                  }

                },
                {value: "corresOtraPersona", label: "Corresponde a otra persona", disabled: true,
                onChangeEvent: {
                    type: "EditCorreccionDatosComponent",
                    event: "changeCertificador",
                    key: this.component.id,
                    model: ""
                  }
                },
                {value: "noExisteCanase", label: "No existe en CANASE", disabled: true,
                onChangeEvent: {
                    type: "EditCorreccionDatosComponent",
                    event: "changeCertificador",
                    key: this.component.id,
                    model: ""
                  }
                }
              ]
            }
            
          ], layout: [[{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}]]}
        ,
        {
          type: "EditTipoRegularizacionComponent",
          id: this.component.model + ".listaNss[" + currentNss + "].tipoAclaracion",
          model: this.component.model + ".listaNss[" + currentNss + "].tipoAclaracion"
        }
      ],
      layout: [[{span: 12}], [{span: 12}]]
    };

    var panel = new PanelComponent(this.render);
    html += panel.draw(metadata);
    this.render.module.render.volatileComponents[this.render.module.render.volatileComponents.length] = metadata;
  }
  return html;
};

EditCorreccionDatosComponent.prototype.notify = function (metadata, modelName) {
  this.component = metadata;
  var html = this.drawBody();
  $("#editCorreccionDatos_" + this.render.toId(this.component.id)).html(html);
};


EditCorreccionDatosComponent.prototype.changeCertificador = function () {
  var index = this.render.module.controller.model.currentNss;
  if (index === undefined) {
    index = 0;
  }
  index = parseInt(index);

  var model = this.render.getModel(this.component.model);
  if (model.listaNss[index].tipoNss === undefined) {
    model.listaNss[index].tipoNss = {};
  }
  var row = model.listaNss[index].tipoNss;

  var forma = this.render.toId(this.component.model + ".listaNss[" + index + "].tipoNss");
  this.render.module.validator.formToModel("FormPanelComponent-" + forma, row);

  model.listaNss[index].tipoNss.certificador = false;
  model.listaNss[index].tipoNss.asociado = false;
  model.listaNss[index].tipoNss.corresOtraPersona = false;
  model.listaNss[index].tipoNss.noExisteCanase = false;

  model.listaNss[index].tipoNss[row.tipo] = true;
  model.listaNss[index].tipoNss.tipo = row.tipo;
  this.render.module.updateModel("EditCorreccionDatosComponent", "editCorreccionDatos", row);

};

EditCorreccionDatosComponent.prototype.triggerEvent = function (event, parameters) {
  switch (event) {
    case "changeCertificador":
      this.changeCertificador();
      break;    
  }
};





