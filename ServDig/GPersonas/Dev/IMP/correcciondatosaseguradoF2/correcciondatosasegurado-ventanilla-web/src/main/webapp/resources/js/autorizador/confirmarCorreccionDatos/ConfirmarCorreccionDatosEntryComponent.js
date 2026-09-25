function ConfirmarCorreccionDatosEntryComponent(render) {
  this.render = render;
}
ConfirmarCorreccionDatosEntryComponent.prototype.draw = function (component) {
  this.component = component;

  
  var html = '<div id="correccionDatosEntry_' + this.render.toId(this.component.id) + '" >';
  html += this.drawBody();
  html += '</div>';
  return html;
};
ConfirmarCorreccionDatosEntryComponent.prototype.drawBody = function () {
  var model = this.render.getModel(this.component.model);
  var i;
  var tabla = "";
  
  tabla += '<table class="table table-striped">';
  tabla += '<thead><tr>';
  tabla += '<th>Origen de informaci\u00f3n</th>';
  tabla += '<th>Dato</th>';
  tabla += '<th>Informaci\u00f3n en el IMSS</th>';
  tabla += '<th>Informaci\u00f3n de actualizaci\u00f3n</th>';
  tabla += '<th>Estatus del cambio</th>';
  tabla += '<th>Fecha de proceso</th>';
  tabla += '</tr></thead>';
  tabla += '<tbody>';
  if( !this.render.isEmpty( model.data ) ){
    for(i=0;i< model.data.length;i++){
      var className = "bg-dark";
      if( !this.render.isEmpty( model.data[i].informacionIMSS ) ){
        className = "bg-danger";
      }
      
      tabla += '<tr>';
      tabla += '<td>'+ this.render.htmlEncode(model.data[i].origenInformacion)+'</td>';
      tabla += '<td>'+ this.render.htmlEncode(model.data[i].dato)+'</td>';
      tabla += '<td class="'+className+'">'+ this.render.htmlEncode(model.data[i].informacionIMSS)+'</td>';
      tabla += '<td>'+ this.render.htmlEncode(model.data[i].informacionActualizacion)+'</td>';
      tabla += '<td>'+ this.render.htmlEncode(model.data[i].estatus)+'</td>';
      tabla += '<td>'+ this.render.htmlEncode(model.data[i].fechaProceso)+'</td>';
      tabla += '</tr>';
    }
  }
  
  tabla += '</tbody>';
  tabla += '</table>';

  var metadata = {type: "PanelComponent",
    layout: [[{span: 3}, {span: 3}, {span: 3}, {span: 3}], [{span: 12}]],
    components: [

      {type: "LabelComponent",className:"pull-right text-right", tag:"label", label: "NSS"},
      {type: "LabelComponent", label: model.nss},
      {type: "LabelComponent",className:"pull-right text-right", tag:"label", label: "Detalle"},
      {type: "LabelComponent", label: model.detalle},
      {type: "HTMLComponent", html: tabla}
    ]
  };

  var html = "";
  var panel = new PanelComponent(this.render);
  html += panel.draw(metadata);
//  this.render.module.render.volatileComponents[this.render.module.render.volatileComponents.length] = metadata;
  return html;
};


ConfirmarCorreccionDatosEntryComponent.prototype.notify = function (metadata, modelName) {
  this.component = metadata;
  var html = this.drawBody();
  $("#correccionDatosEntry_" + this.render.toId(this.component.id)).html(html);
};
