function NssDocumentsComponent(render) {
  this.render = render;
}
NssDocumentsComponent.prototype.draw = function (component) {
  this.component = component;
  var html = '<div id="nssDocuments_' + this.component.id + '" >';
  html += this.drawBody();
  html += '</div>';
  return html;
};
NssDocumentsComponent.prototype.drawBody = function () {
  var html = "";
  var model = this.render.getModel(this.component.model);

  if (!this.render.isEmpty(model)) {


    var metadata = {type: "PanelComponent",
      layout: [[{span: 12}]],
      components: [
        {
          type: "IteratorComponent", indexName: "index",
          idGrid: this.component.id,
          id: "nssDocumentIterator",
          model: this.component.model,
          entry: {
            type: "NssDocumentsEntryComponent",
			idGrid: this.component.id,
            id: this.component.id + "[index]",
            model: this.component.model + "[index]",
			elemento : "index",
            eliminar : this.component.eliminar,
			agregado : this.component.agregado,
			observaciones : this.component.observaciones,
            editable : this.component.editable,
			numerarDcotos : this.component.numerarDcotos
          }
        }
      ]
    };


    var panel = new PanelComponent(this.render);
    html += panel.draw(metadata);    
  }
  return html;
};


NssDocumentsComponent.prototype.notify = function (metadata, modelName) {
  this.component = metadata;
  var html = this.drawBody();
  $("#nssDocuments_" + this.component.id).html(html);
};

