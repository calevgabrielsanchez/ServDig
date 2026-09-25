function ConfrontaPeriodoCuentaIndividualComponent(render) {
  this.render = render;
}
ConfrontaPeriodoCuentaIndividualComponent.prototype.draw = function (component) {
  this.component = component;
  var html = '<div id="periodoCuentaIndividual_' + this.render.toId(this.component.id) + '" >';
  html += this.drawBody();
  html += '</div>';
  return html;
};
ConfrontaPeriodoCuentaIndividualComponent.prototype.getClassName = function ( inicial, final ) {
  if( inicial !== final){
    return "bg-warning";
  }
  return "";
};
ConfrontaPeriodoCuentaIndividualComponent.prototype.drawBody = function () {
  
  var model = this.render.getModel( this.component.model );
  var finalModel = this.render.getModel( this.component.finalModel );
  var parentModel = this.render.getModel( this.component.parentId );
  if( model === undefined ){
    model = {};
  }
  var html = "";
  
  var descripcionTipoRegularizacion = "";
  switch(model.tipoRegularizacionPeriodo){
    case "A":
        descripcionTipoRegularizacion = "Agregar";
      break;
    case "E":
        descripcionTipoRegularizacion = "Eliminar";
      break;
    case "M":
        descripcionTipoRegularizacion = "Modificar";
      break;
    case "I":
        descripcionTipoRegularizacion = "Incluir";
      break;
  }
  

  var metadata = {
    type: "PanelComponent",  style: " height:12px",  
    components: [
      {
        type: "PanelComponent", layout: [[{span: 5}, {span: 2},{span: 5}]],
        components: [
          {type: "PanelComponent", layout: [[{span: 3}, {span: 2}, {span: 1},{span: 2}, {span: 2}, {span: 2}]],
            components: [
              {type: "LabelComponent", className:this.getClassName("nss"), label: model.nss},
              {type: "LabelComponent", className:this.getClassName("fechaRecepcionMovimiento"), label: model.fechaRecepcionMovimiento}, 
              {type: "LabelComponent", className:this.getClassName("origenMovimientoInicial"), label: model.origenMovimientoInicial },
              {type: "LabelComponent", className:this.getClassName("tipoMovimientoInicial"), label: model.tipoMovimientoInicial },              
              {type: "LabelComponent", className:this.getClassName("fechaInicioMovimiento"), label: model.fechaInicioMovimiento },
              {type: "LabelComponent", className:this.getClassName("salarioBase"), label: model.salarioBase }
            ]
          },
          {type: "PanelComponent", layout: [[{span: 2}, {span: 2}, {span: 2},{span: 2}, {span: 2}, {span: 2}]],
             components: [
             {type: "LabelComponent", className:this.getClassName("tipoSalario"), label: model.tipoSalario },
             {type: "LabelComponent", className:this.getClassName("eventual"), label: model.eventual },
             {type: "LabelComponent", className:this.getClassName("extemporaneoConvenioSuspension"), label: model.extemporaneoConvenioSuspension },
             {type: "LabelComponent", className:this.getClassName("subrogacionServicio"), label: model.subrogacionServicio },
             {type: "LabelComponent", className:this.getClassName("origenMovimientoFinal"), label: model.origenMovimientoFinal},
              {type: "LabelComponent", className:this.getClassName("tipoMovimientoFinal"), label: model.fechaFinalMovimiento === "12/31/9999" ? "" : model.tipoMovimientoFinal }
             
              ]
          },
          {type: "PanelComponent", layout: [[{span: 2}, {span: 2}, {span: 2},{span: 2}, {span: 2}, {span: 2}]],
            components: [
              {type: "LabelComponent", className:this.getClassName("fechaFinalMovimiento"), label: model.fechaFinalMovimiento === "12/31/9999" ? "00/00/0000" : model.fechaFinalMovimiento },
              {type: "LabelComponent", className:this.getClassName("jornadaSemanal"), label: model.jornadaSemanal },
              {type: "LabelComponent", className:this.getClassName("tipoRegularizacionPeriodo"), label: descripcionTipoRegularizacion },
              {type: "LabelComponent", className:"", label: model.status },                  
              {type: "LabelComponent", className:"", label: model.fechaProceso },                  
              {type: "LabelComponent", className:"", label: model.indicadorConsecutivoMovimiento}
            ]              
          }
        ]
      }  
    ],
    layout: [[{span: 12}]]
  };


  var panelComponent = new PanelComponent(this.render);
  html += panelComponent.draw(metadata);
  return html;
};

ConfrontaPeriodoCuentaIndividualComponent.prototype.getClassName = function (attribute) {
  
  // periodo
  var model = this.render.getModel(this.component.model);
  
  if( model.classNames !== undefined && model.classNames[attribute] !== undefined ){
    return model.classNames[attribute];
  }
  
  
  return "";
};


ConfrontaPeriodoCuentaIndividualComponent.prototype.triggerEvent = function (event, parameters) {
  switch (event) {
    case "change":      
      this.changed(parameters);
    break;    
  }
};

