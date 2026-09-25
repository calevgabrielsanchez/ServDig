function ConfirmarCorreccionDatosAsociadoEntryComponent(render) {
  this.render = render;
}
ConfirmarCorreccionDatosAsociadoEntryComponent.prototype.draw = function (component) {
  this.component = component;

  
  var html = '<div id="correccionDatosAsociadoEntry_' + this.render.toId(this.component.id) + '" >';
  html += this.drawBody();
  html += '</div>';
  return html;
};
ConfirmarCorreccionDatosAsociadoEntryComponent.prototype.drawBody = function () {
  var model = this.render.getModel(this.component.model);
  
  var parentModel = this.render.getModel(this.component.parentModel);
  
  var i;
  var tabla = "";
  
  tabla += '<table class="table table-striped">';
  tabla += '<thead><tr>';
  tabla += '<th>Origen de informaci\u00f3n</th>';
  tabla += '<th>CURP</th>';
  tabla += '<th>Nombre</th>';
  tabla += '<th>Lugar de nacimiento</th>';
  tabla += '<th>Fecha de nacimiento</th>';
  tabla += '<th>Sexo</th>';
  tabla += '<th>Estatus del cambio</th>';
  tabla += '<th>Fecha de proceso</th>';
  tabla += '</tr></thead>';
  tabla += '<tbody>';
  
  tabla += this.drawRow( parentModel.renapo, model.bdtu, "BDTU");
  tabla += this.drawRow( parentModel.renapo, model.cizUno, "CIZ1");
  tabla += this.drawRow( parentModel.renapo, model.cizDos, "CIZ2");
  tabla += this.drawRow( parentModel.renapo, model.cizTres, "CIZ3");
  tabla += this.drawRow( parentModel.renapo, model.canase, "CANASE");
  tabla += this.drawRow( parentModel.renapo, model.historico, "HISTORICO");
  
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

ConfirmarCorreccionDatosAsociadoEntryComponent.prototype.drawRow = function (renapo, model, name) {
  var row = "";
  var className = "";
  
  if( model !== undefined ){
  
    row += '<tr>';
    row += '<td>'+ name +'</td>';

    row += '<td class="danger">'+ this.render.htmlEncode(model.curp)+'</td>';
    className = "bg-danger";
    if( renapo.nombre === model.nombre && 
        renapo.apellidoPaterno === model.apellidoPaterno && 
        renapo.apellidoMaterno === model.apellidoMaterno) {        
      className = "";
    }
    row += '<td class="'+className+'">'+ this.render.htmlEncode(this.render.nvl(model.apellidoPaterno,"") + " " + this.render.nvl(model.apellidoMaterno,"") + " " + this.render.nvl(model.nombre,""))+'</td>';

    className = "bg-danger";
    if( renapo.lugarNacimiento === model.lugarNacimiento) {        
      className = "";
    }
    row += '<td class="'+className+'">'+ this.render.htmlEncode(model.lugarNacimiento)+'</td>';
    className = "bg-danger";
    if( renapo.fechaNacimiento === model.fechaNacimiento) {        
      className = "";
    }
    row += '<td class="'+className+'">'+ this.render.htmlEncode(model.fechaNacimiento)+'</td>';
    className = "bg-danger";
    if( renapo.sexo === model.sexo.toUpperCase()) {
      className = "";
    }
    row += '<td class="'+className+'">'+ this.render.htmlEncode(model.sexo)+'</td>';
    row += '<td>'+ this.render.htmlEncode(model.estatus)+'</td>';
    row += '<td>'+ this.render.htmlEncode(model.fechaProceso)+'</td>';
    row += '</tr>';
  }
  return row;
};


ConfirmarCorreccionDatosAsociadoEntryComponent.prototype.notify = function (metadata, modelName) {
  this.component = metadata;
  var html = this.drawBody();
  $("#correccionDatosAsociadoEntry_" + this.render.toId(this.component.id)).html(html);
};
