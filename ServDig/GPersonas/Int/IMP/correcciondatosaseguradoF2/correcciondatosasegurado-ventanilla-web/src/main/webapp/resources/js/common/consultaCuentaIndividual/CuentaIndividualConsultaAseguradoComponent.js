function CuentaIndividualConsultaAseguradoComponent(render) {
  this.render = render;
}
CuentaIndividualConsultaAseguradoComponent.prototype.draw = function (component) {
  this.component = component;
  var html = '<div id="cuentaIndividualAsegurado_' + this.render.toId(this.component.id) + '" >';
  html += this.drawBody();
  html += '</div>';
  return html;
};
CuentaIndividualConsultaAseguradoComponent.prototype.drawBody = function () {
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
  if( model !== undefined ){
  
    if (model.listaTipoRegularizacion !== undefined){
      
      for(i=0;i<model.listaTipoRegularizacion.length;i++){
        if( tipoRegularizacion.indexOf( model.listaTipoRegularizacion[i] ) === -1 ){
          tipoRegularizacion += model.listaTipoRegularizacion[i]+ ",";
        }        
      }
      tipoRegularizacion = tipoRegularizacion.substring(0,tipoRegularizacion.length-1);
      
    }
  
  
    var data = this.render.getModel( this.component.model +"["+index+"]" + ".listaPeriodosRegistroPatronal");
    if( !this.render.isEmpty(data)  && data.length > 0 ){      
      var metadata = {
        type: "PanelComponent",        
        components: [
          { type:"LabelComponent", className:"label-cda", label: "Informaci\u00f3n del n\u00famero de seguridad social con el que se certificar\u00e1 el tr\u00e1mite"},
          { type:"LabelComponent", className:"label-cda", label: "NSS"},
          { type:"LabelComponent", label: model.nss },
          { type:"LabelComponent", className:"label-cda", label: "Tipo de Regularizaci\u00f3n"},
          { type:"LabelComponent", label: tipoRegularizacion },
          {
            type: "IteratorComponent", indexName: "index",
            id: this.component.id +"["+index+"]"+ ".listaPeriodosRegistroPatronal",
            model: this.component.model +"["+index+"]" + ".listaPeriodosRegistroPatronal",
            entry: {
              type: "CuentaIndividualConsultaNssComponent",
              parentId: this.component.id, element: "index",
              id: this.component.id +"["+index+"]"+ ".listaPeriodosRegistroPatronal[index]",
              model: this.component.model +"["+index+"]"+ ".listaPeriodosRegistroPatronal[index]"
            }
          }
        ],
        layout: [ [{span: 12}], [{span: 1},{span: 5},{span: 1},{span: 5}], [{span: 12}] ]
      };


      var panel = new PanelComponent(this.render);
      html += panel.draw( metadata );
    }else{
      
      var metadata = { type:"AlertComponent", level:"info", message:"No existe informaci\u00f3n", id:"alert_01"};
    var alertComponent = new AlertComponent(this.render);
    this.render.module.controller.model["alert_01"] = { level:"warning", message:"No se selecciono ning\u00FAn movimiento afiliatorio de este tipo de NSS para moverlo a otro" };
      html += alertComponent.draw(metadata);
      
    }
  
  }else{
    var metadata = { type:"AlertComponent", level:"info", message:"No existe informaci\u00f3n", id:"alert_01"};
    var alertComponent = new AlertComponent(this.render);
    this.render.module.controller.model["alert_01"] = { level:"warning", message:"No se selecciono ning\u00FAn movimiento afiliatorio de este tipo de NSS para moverlo a otro" };
      html += alertComponent.draw(metadata);
  }
  return html;

};

CuentaIndividualConsultaAseguradoComponent.prototype.notify = function (metadata, modelName) {
  this.component = metadata;
  var html = this.drawBody();
  $("#cuentaIndividualAsegurado_" + this.render.toId(this.component.id)).html(html);
};

CuentaIndividualConsultaAseguradoComponent.prototype.edit = function () {
  var model = this.render.getModel(this.component.parentId);
  this.component.state = "CuentaIndividualEditComponent";
  // Indicar en el modelo que se esta en modo de edicion.
  model.state = "edit";
  var parameters = { type: 'CuentaIndividualComponent', event: 'edit', key: "cuentaIndividual", model:"" };
  this.render.triggerEvent( window.btoa( JSON.stringify( parameters )) );  
  
  this.render.notify("CuentaIndividualConsultaAseguradoComponent", this.component.id);
};

CuentaIndividualConsultaAseguradoComponent.prototype.nss = function () {  
  var model = this.render.getModel(this.component.parentId);
  this.component.state = "CuentaIndividualNssComponent";
  // Indicar en el modelo que se esta en modo de nss.
  model.state = "nss";
  this.render.notify("CuentaIndividualConsultaAseguradoComponent", this.component.id);
};


CuentaIndividualConsultaAseguradoComponent.prototype.triggerEvent = function (event, parameters) {
  switch (event) {
    case "edit":
      this.edit(parameters);
      break;
    case "nss":
      this.nss(parameters);
      break;
    case "page":
      this.page(parameters);
      break;
  }
};

CuentaIndividualConsultaAseguradoComponent.prototype.page = function (page) {
  var model = this.render.getModel(this.component.model);
  if( model.state !== "edit"){
  
    this.render.module.controller.model[ this.component.indexName ] = page;
    // console.log( JSON.stringify( this.component ));
    // console.log("tab-" + this.component.indexName);
    this.render.notify("PanelComponent", "tab-" + this.component.indexName );  
  }else{
    // Indicar mensaje de que debe terminar la edicion
  }
};


