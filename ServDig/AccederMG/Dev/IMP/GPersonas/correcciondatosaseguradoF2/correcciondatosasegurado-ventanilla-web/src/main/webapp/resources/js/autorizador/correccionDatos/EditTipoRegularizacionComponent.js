function EditTipoRegularizacionComponent(render) {
  this.render = render;
}
EditTipoRegularizacionComponent.prototype.draw = function (component) {
  this.component = component;
  var html = '<div id="editTipoRegularizacion_' + this.render.toId(this.component.id) + '" >';
  html += this.drawBody();
  html += '</div>';
  return html;
};
EditTipoRegularizacionComponent.prototype.drawBody = function () {
  var model = this.render.getModel(this.component.model);
  var html = "";
  var currentNss = this.render.getModel("currentNss");
  if (currentNss === undefined) {
    currentNss = 0;
  }
  currentNss = parseInt(currentNss);

  if (model !== undefined) {
    
    var tipoAclaracionDisabled = this.render.module.model.correccionDatos.getDisabledTipoAclaracion(currentNss);
    // console.log( tipoAclaracionDisabled );
    var metadata = {
      type: "FormPanelComponent",
          name: this.component.model , model: this.component.model , entity: "tipoAclaracion",
          label: "Tipo de Regularizaci\u00f3n",
          components: [
            {type: "CheckBoxFieldComponent", field: "canceladoDup", 
              disabled: true, label: "Cancelado por duplicidad"},
            {type: "CheckBoxFieldComponent", field: "homonimio",
              disabled: true, label: "Corresponde a un hom\u00f3nimo"},
            {type: "CheckBoxFieldComponent", field: "noExisteCanase",
              disabled: true, label: "No existe en CANASE"},
            {type: "CheckBoxFieldComponent", field: "otroAsegurado",
              disabled: true,  label: "Corresponde a otro asegurado"},
            {type: "CheckBoxFieldComponent", field: "correccionNombre",
              disabled: true, label: "Correcci\u00f3n de nombre"},
            {type: "CheckBoxFieldComponent", field: "correccionEstadis", 
              disabled: true, label: "Correcci\u00f3n de datos estad\u00EDsticos"}
          ], layout: [[{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}]]

        };

    var panel = new FormPanelComponent(this.render);
    html += panel.draw(metadata);
    this.render.module.render.volatileComponents[this.render.module.render.volatileComponents.length] = metadata;
  }
  return html;
};

EditTipoRegularizacionComponent.prototype.notify = function (metadata, modelName) {
  this.component = metadata;
  // console.log( metadata );
  var html = this.drawBody();
  $("#editTipoRegularizacion_" + this.render.toId(this.component.id)).html(html);
};






