function CuentaIndividualNssComponent(render) {
  this.render = render;
}
CuentaIndividualNssComponent.prototype.draw = function (component) {
  this.component = component;
  var html = '<div id="cuentaIndividualNss_' + this.render.toId(this.component.id) + '" >';
  html += this.drawBody();
  html += '</div>';
  return html;
};
CuentaIndividualNssComponent.prototype.drawBody = function () {
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
  
  
  if( model !== undefined && model.listaPeriodosRegistroPatronal !== undefined && model.listaPeriodosRegistroPatronal.length > 0 ){
    
    if (model.listaTipoRegularizacion !== undefined){
      
      for(i=0;i<model.listaTipoRegularizacion.length;i++){
        if( tipoRegularizacion.indexOf( model.listaTipoRegularizacion[i] ) === -1 ){
          tipoRegularizacion += model.listaTipoRegularizacion[i]+ ",";
        }        
      }
      tipoRegularizacion = tipoRegularizacion.substring(0,tipoRegularizacion.length-1);
      
    }
    
    var metadata = {
      type: "PanelComponent", level:4,      
      components: [  
        { type:"LabelComponent", className:"label-cda", label: "NSS"},
          { type:"LabelComponent", label: model.nss },
          { type:"LabelComponent", className:"label-cda", label: "Tipo de Regularizaci\u00f3n"},
          { type:"LabelComponent", label: tipoRegularizacion },
          { type: "IteratorComponent", indexName: "index",
            id: this.component.id +"["+index+"]"+ ".listaPeriodosRegistroPatronal",
            model: this.component.model +"["+index+"]"+ ".listaPeriodosRegistroPatronal",
            entry: {
              type: "RegistroPatronalComponent", 
              parentId: this.component.id , element: "index", indexNameParent: this.component.indexName,
              id: this.component.id +"["+index+"]"+ ".listaPeriodosRegistroPatronal[index]",
              model: this.component.model +"["+index+"]"+ ".listaPeriodosRegistroPatronal[index]"
            }
          }
      ],
      layout: [[{span: 1},{span: 5},{span: 1},{span: 5}],  [{span: 12}] ]
    };


    var panel = new PanelComponent(this.render);
    html += panel.draw( metadata );
  }else{
    var metadata = {
      type:"AlertComponent", level:"info", message:"No hay periodos que se puedan modificar para este tipo de NSS", id:"alert_01"
    };
    var alertComponent = new AlertComponent(this.render);
    this.render.module.controller.model["alert_01"] = { level:"warning", message:"No hay periodos que se puedan modificar para este tipo de NSS" };
    html += alertComponent.draw(metadata);
  }
  return html;

};

CuentaIndividualNssComponent.prototype.notify = function (metadata, modelName) {
  this.component = metadata;
  var html = this.drawBody();
  $("#cuentaIndividualNss_" + this.render.toId(this.component.id)).html(html);  
  
};


CuentaIndividualNssComponent.prototype.triggerEvent = function (event, parameters) {
  switch (event) {
    case "page":
      this.page(parameters);
      break;
    case "loadPage":
      this.loadPage(parameters);
      break;
  }
};

CuentaIndividualNssComponent.prototype.loadPage = function(){
  this.page( this.component.page );
};
CuentaIndividualNssComponent.prototype.page = function (page) {
  
  this.render.module.controller.model[this.component.indexName] = JSON.parse(page);
  
  this.render.notify( "CuentaIndividualNssComponent", this.component.id, this.component.model );
  this.render.notify( "NssPagerComponent", this.component.indexName, [] );
  var model = this.render.getModel(this.component.model);
  console.log(model[page]);
  this.component.page = page;
  
  var modelName = this.component.id +"["+page+"]";
  var cveIdDetalleNssCda = model[page].cveIdDetalleNssCda;
  
  this.render.module.service.cuentaIndividualService.readListaCuentaIndividualRegistroPatronal( this.component.id, modelName, this.render.module, cveIdDetalleNssCda, model[page].listaNssDestino);
  
};


