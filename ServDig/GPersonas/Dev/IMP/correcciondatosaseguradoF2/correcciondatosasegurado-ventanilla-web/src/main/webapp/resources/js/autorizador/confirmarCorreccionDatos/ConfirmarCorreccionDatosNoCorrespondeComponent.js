function ConfirmarCorreccionDatosNoCorrespondeComponent(render) {
  this.render = render;
}
ConfirmarCorreccionDatosNoCorrespondeComponent.prototype.draw = function (component) {
  this.component = component;

  
  var html = '<div id="correccionDatosNoCorrespondeEntry_' + this.render.toId(this.component.id) + '" >';
  html += this.drawBody();
  html += '</div>';
  return html;
};
ConfirmarCorreccionDatosNoCorrespondeComponent.prototype.drawBody = function () {
  var model = this.render.getModel(this.component.model);
  
  var metadata = {type: "PanelComponent",
    layout: [[{span: 3}, {span: 3}, {span: 3}, {span: 3}], [{span: 12}]],
    components: [

      {type: "LabelComponent",className:"pull-right text-right", tag:"label", label: "NSS"},
      {type: "LabelComponent", label: model.nss},
      {type: "LabelComponent",className:"pull-right text-right", tag:"label", label: "Detalle"},
      {type: "LabelComponent", label: model.detalle}
    ]
  };

  var html = "";
  var panel = new PanelComponent(this.render);
  html += panel.draw(metadata);
  return html;
};

ConfirmarCorreccionDatosNoCorrespondeComponent.prototype.notify = function (metadata, modelName) {
  this.component = metadata;
  var html = this.drawBody();
  $("#correccionDatosNoCorrespondeEntry_" + this.render.toId(this.component.id)).html(html);
};
