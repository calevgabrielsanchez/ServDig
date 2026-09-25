function ConfrontaCuentaIndividualAseguradoComponent(render) {
  this.render = render;
}
ConfrontaCuentaIndividualAseguradoComponent.prototype.draw = function (component) {
  this.component = component;
  var html = '<div id="cuentaIndividualAsegurado_' + this.render.toId(this.component.id) + '" >';
  html += this.drawBody();
  html += '</div>';
  return html;
};
ConfrontaCuentaIndividualAseguradoComponent.prototype.drawBody = function () {
  var html = "";  
  var i;
  var index = this.render.getModel(this.component.indexName);
  if( this.render.isEmpty( index) ){
    index = 0;
  }
  index = parseInt( index );
  // console.log(this.component.model+"["+index+"]");
  var model = this.render.getModel(this.component.model+"["+index+"]");
  var tipoRegularizacion = " ";
  if (model !== undefined && model.listaPeriodosRegistroPatronal !== null && model.listaPeriodosRegistroPatronal.length > 0) {
    
    if (model.listaTipoRegularizacion !== undefined){
      
      for(i=0;i<model.listaTipoRegularizacion.length;i++){
        if( tipoRegularizacion.indexOf( model.listaTipoRegularizacion[i] ) === -1 ){
          tipoRegularizacion += model.listaTipoRegularizacion[i]+ ",";
        }        
      }
      tipoRegularizacion = tipoRegularizacion.substring(0,tipoRegularizacion.length-1);
      
    }
  
  var metadata = {
    type: "PanelComponent",
    label: this.render.nvl(model.nss,"") + " - " + this.render.nvl(tipoRegularizacion,""),
    components: [      
      {
        type: "IteratorComponent", indexName: "index",
        id: this.component.id +"["+index+"]" + ".listaPeriodosRegistroPatronal",
        model: this.component.model+"["+index+"]" + ".listaPeriodosRegistroPatronal",
        entry: {
          type: "ConfrontaCuentaIndividualNssComponent",
          parentId: this.component.id+"["+index+"]" , element: "index",
          id: this.component.id +"["+index+"]" + ".listaPeriodosRegistroPatronal[index]",
          model: this.component.model +"["+index+"]" + ".listaPeriodosRegistroPatronal[index]"
        }
      }
    ],
    layout: [ [{span: 12}] ]
  };
    var panel = new PanelComponent(this.render);
    html += panel.draw( metadata );
  }else{
      
      var metadata = { type:"AlertComponent", level:"info", message:"No existe informaci\u00f3n", id:"alert_01"};
    var alertComponent = new AlertComponent(this.render);
    this.render.module.controller.model["alert_01"] = { level:"warning", message:"No existe informaci\u00f3n" };
      html += alertComponent.draw(metadata);
      
    }
  
  
  return html;

};

ConfrontaCuentaIndividualAseguradoComponent.prototype.notify = function (metadata, modelName) {
  this.component = metadata;
  var html = this.drawBody();
  $("#cuentaIndividualAsegurado_" + this.render.toId(this.component.id)).html(html);
};

ConfrontaCuentaIndividualAseguradoComponent.prototype.triggerEvent = function (event, parameters) {
  switch (event) {
    case "page":
      this.page(parameters);
      break;    
  }
};

ConfrontaCuentaIndividualAseguradoComponent.prototype.page = function (page) {
  this.render.module.controller.model[ this.component.indexName ] = page;
  // console.log( JSON.stringify( this.component ));
  // console.log("tab-" + this.component.indexName);
  this.render.notify("PanelComponent", "tab-" + this.component.indexName );  
};


