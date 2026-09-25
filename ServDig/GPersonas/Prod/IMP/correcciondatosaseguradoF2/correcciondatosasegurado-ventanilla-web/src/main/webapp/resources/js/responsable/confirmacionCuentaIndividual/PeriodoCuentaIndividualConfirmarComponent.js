function PeriodoCuentaIndividualConfirmarComponent(render) {
  this.render = render;
}
PeriodoCuentaIndividualConfirmarComponent.prototype.draw = function (component) {
  this.component = component;
  var html = '<div id="periodoCuentaIndividual_' + this.render.toId(this.component.id) + '" >';
  html += this.drawBody();
  html += '</div>';
  return html;
};

PeriodoCuentaIndividualConfirmarComponent.prototype.drawBody = function () {
  
  var model = this.render.getModel( this.component.model );
  var parentModel = this.render.getModel( this.component.parentId );
  if( model === undefined ){
    model = {};
  }
  var html = "";

  var metadata = {
    type: "FormPanelComponent", entity:"periodo",
    name: this.component.model, 
    model: this.component.model,
    className: "form", style:"height:0px",
    components:[
      {
        type:"PanelComponent",
        components:[
          {type: "LabelComponent", label: model.fechaRecepcionMovimiento},
          {type: "LabelComponent", label: model.tipoMovimientoInicial},
          {type: "LabelComponent", label: model.origenMovimientoInicial},
          {type: "LabelComponent", label: model.fechaInicioMovimiento},
          {type: "LabelComponent", label: model.salarioBase},
          {type: "LabelComponent", label: model.tipoSalario},
          {type: "LabelComponent", label: model.eventual},
          {type: "LabelComponent", label: model.extemporaneoConvenioSuspencion},
          {type: "La belComponent", label: model.subrogacionServicio},
          
        ],
        layout:[[{span:2},{span:1},{span:1},{span:2},{span:1},{span:2},{span:1},{span:1},{span:1}]]
      },
      {
        type:"PanelComponent",
        components:[
          {type: "LabelComponent", label: model.tipoMovimientoFinal},
          {type: "LabelComponent", label: model.origenMovimientoFinal},
          {type: "LabelComponent", label: model.fechaFinalMovimiento},
          {type: "LabelComponent", label: model.numeroConsecutivoPeriodos},
          {type: "LabelComponent", label: model.nss},
          {type: "LabelComponent", label: model.regularizacion},
          {type: "LabelComponent", label: model.estatus},
          {type: "LabelComponent", label: model.fechaProceso}
        ],
        layout:[[{span:2},{span:2},{span:2},{span:2},{span:2},{span:2},{span:2},{span:2}]]
      }
    ],
    layout:[[{span:6},{span:6}]]
  };
  var panelComponent = new FormPanelComponent(this.render);
  html += panelComponent.draw(metadata);
  return html;
};

PeriodoCuentaIndividualConfirmarComponent.prototype.changed = function (parameters) {  
  var model = this.render.getModel( parameters );
  model.nss = $("#" + this.render.toId( parameters + ".nss") ).val();
};
PeriodoCuentaIndividualConfirmarComponent.prototype.triggerEvent = function (event, parameters) {
  switch (event) {
    case "change":      
      this.changed(parameters);
    break;    
  }
};

