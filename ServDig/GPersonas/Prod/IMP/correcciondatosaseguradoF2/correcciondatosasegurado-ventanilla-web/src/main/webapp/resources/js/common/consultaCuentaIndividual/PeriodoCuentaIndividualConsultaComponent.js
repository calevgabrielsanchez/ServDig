function PeriodoCuentaIndividualConsultaComponent(render) {
  this.render = render;
  this.dangerStyle = "#fcf8e3";
  this.successStyle = "#dff0d8";
}
PeriodoCuentaIndividualConsultaComponent.prototype.draw = function (component) {
  this.component = component;
  var html = '<div id="periodoCuentaIndividual_' + this.render.toId(this.component.id) + '" >';
  html += this.drawBody();
  html += '</div>';
  this.render.addLinker(new Link(component.id, "PeriodoCuentaIndividualConsultaComponent", component, 1));
  return html;
};
PeriodoCuentaIndividualConsultaComponent.prototype.digest = function (link) {
  this.component = link.metadata;
  var model = this.render.getModel(this.component.model);
  this.drawState(model.state);
};

PeriodoCuentaIndividualConsultaComponent.prototype.drawBody = function () {

  var model = this.render.getModel(this.component.model);
  if (model === undefined) {
    model = {};
  }
  if (model.changed === undefined) {
    model.changed = [];
  }
  var html = "";

  var parentModel = this.render.getModel(this.component.parentId);


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
             {type: "LabelComponent", className:this.getClassName("tipoMovimientoFinal"), label: model.tipoMovimientoFinal}
             
              ]
          },
          {type: "PanelComponent", layout: [[{span: 2}, {span: 2}, {span: 2},{span: 2}, {span: 2}, {span: 2}]],
            components: [
              {type: "LabelComponent", className:this.getClassName("fechaFinalMovimiento"), label: model.fechaFinalMovimiento },
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
  this.render.module.render.volatileComponents[this.render.module.render.volatileComponents.length] = metadata;

  return html;
};

PeriodoCuentaIndividualConsultaComponent.prototype.getClassName = function (attribute) {
  
  // periodo
  var model = this.render.getModel(this.component.model);
  
  if( model.classNames !== undefined && model.classNames[attribute] !== undefined ){
    return model.classNames[attribute];
  }
  
  
  return "";
};


PeriodoCuentaIndividualConsultaComponent.prototype.drawState = function (state) {
  switch (state) {
    case "edit":
      $("#" + this.render.toId(this.component.id + ".status")).css('background-color', this.dangerStyle);
      $("#" + this.render.toId(this.component.id + ".status")).css('color', "#8a6d3b");      
      $("#" + this.render.toId(this.component.id + ".tipoRegularizacionPeriodo")).val("M");
      $("#" + this.render.toId(this.component.id + ".status")).val("Modificado");
      $("#" + this.render.toId(this.component.id + ".buttonRemove")).hide();
      $("#" + this.render.toId(this.component.id + ".buttonUndo")).show();
      $("#" + this.render.toId(this.component.id + ".buttonDelete")).hide();
      break;
    case "pristine":
      $("#" + this.render.toId(this.component.id + ".status")).css('background-color', "");
      $("#" + this.render.toId(this.component.id + ".status")).val("");
      $("#" + this.render.toId(this.component.id + ".buttonUndo")).hide();
      $("#" + this.render.toId(this.component.id + ".buttonRemove")).show();
      $("#" + this.render.toId(this.component.id + ".buttonDelete")).hide();
      break;
    case "add":
      $("#" + this.render.toId(this.component.id + ".status")).css('background-color', this.successStyle);
      $("#" + this.render.toId(this.component.id + ".status")).val("Agregado");
      $("#" + this.render.toId(this.component.id + ".status")).css('color', "#3c763d");
      $("#" + this.render.toId(this.component.id + ".buttonUndo")).hide();
      $("#" + this.render.toId(this.component.id + ".buttonRemove")).hide();
      $("#" + this.render.toId(this.component.id + ".buttonDelete")).show();
      break;
    case "delete":
      $("#" + this.render.toId(this.component.id + ".status")).css('background-color', "#f2dede");
      $("#" + this.render.toId(this.component.id + ".status")).val("Eliminado");
      $("#" + this.render.toId(this.component.id + ".status")).css('color', "#a94442");
      $("#" + this.render.toId(this.component.id + ".buttonUndo")).show();
      $("#" + this.render.toId(this.component.id + ".buttonRemove")).hide();
      $("#" + this.render.toId(this.component.id + ".buttonDelete")).hide();
      break;
    default:
      $("#" + this.render.toId(this.component.id + ".buttonRemove")).hide();
      $("#" + this.render.toId(this.component.id + ".buttonUndo")).hide();
      $("#" + this.render.toId(this.component.id + ".buttonDelete")).hide();
      break;

  }
};

PeriodoCuentaIndividualConsultaComponent.prototype.changed = function (field) {
  var model = this.render.getModel(this.component.model);
  var i;
  var periodosModificados = this.render.getModel( this.component.parentId + ".periodosModificados");
  if( this.render.isEmpty( periodosModificados ) ){    
    var periodosRegistroPatronal = this.render.getModel( this.component.parentId );
    periodosRegistroPatronal.periodosModificados = [];        
    periodosModificados = periodosRegistroPatronal.periodosModificados;
  }
    
  if (field === "tipoMovimientoFinal") {

    var tipoMovimientoFinal = $("#" + this.render.toId(this.component.id + "." + field)).val();
    if (tipoMovimientoFinal === "0") {
      $("#" + this.render.toId(this.component.id + ".fechaFinalMovimiento")).prop('disabled', true);
    } else {
      $("#" + this.render.toId(this.component.id + ".fechaFinalMovimiento")).prop('disabled', false);
    }

  }

  if (model.state !== "add" && model.state !== "delete") {
    var index;
    if ("" + model[field] !== $("#" + this.render.toId(this.component.id + "." + field)).val()) {
      $("#" + this.render.toId(this.component.id + "." + field)).css('background-color', this.dangerStyle);
      index = model.changed.indexOf(field);
      if (index === -1) {
        model.changed[model.changed.length] = field;
      }
    } else {
      $("#" + this.render.toId(this.component.id + "." + field)).css('background-color', "");
      index = model.changed.indexOf(field);
      if (index > -1) {
        model.changed.splice(index, 1);
      }
    }
    if (model.changed.length > 0) {
      if (model.state !== "add") {
        
        
        
    // console.log( "periodosModificados:" + periodosModificados );
    
    
    // Buscar que no este ya incluido en la lista de modificados.
    var founded = false;
    for( i=0; i< periodosModificados.length; i++){
      if(periodosModificados[i].cveIdPeriodoCuentaIndividual === model.cveIdPeriodoCuentaIndividual ){
        founded = true;
        break;
      }
    }
    if( !founded ){
      model = this.render.getModel( this.component.model );
      var periodoOriginalCopia = JSON.parse( JSON.stringify( model ) );
      periodosModificados[periodosModificados.length] = periodoOriginalCopia;
    }
        
        // Almacenar en el modelo la forma, ya que para restaurar se usa el 
        // backUp del listado de modificados
        //this.render.module.validator.formToModel(
        //  this.render.toId("FormPanelComponent-" + this.render.toId(this.component.model)),
        //  model);
        
        
        model.state = "edit";
        model.tipoRegularizacionPeriodo = "M";
        var parameters = { type: 'CuentaIndividualComponent', event: 'edit', key: "cuentaIndividual", model:"" };
        this.render.triggerEvent( window.btoa( JSON.stringify( parameters )) );            
      }

    } else {
      if (model.state !== "add") {
        
        // Se modifico a mano y se regreso al original, por lo tanto se elimina
        // del listado de modificados.
        for( i=0; i< periodosModificados.length; i++){
          if(periodosModificados[i].cveIdPeriodoCuentaIndividual === model.cveIdPeriodoCuentaIndividual ){
            periodosModificados.splice(i,1);
            break;
          }
        }
        
        
        model.state = "pristine";
        model.tipoRegularizacionPeriodo = "";
      }
    }
  }
  this.drawState(model.state);

};

PeriodoCuentaIndividualConsultaComponent.prototype.undo = function () {
  var model = this.render.getModel(this.component.model);
  var i;
  var periodosEliminados = this.render.getModel( this.component.parentId + ".periodosEliminados");
  var periodosModificados = this.render.getModel( this.component.parentId + ".periodosModificados");
  
  // Determinar el tipo de cambio a deshacer
  switch( model.tipoRegularizacionPeriodo ){
    case "E":
      if( !this.render.isEmpty( periodosEliminados ) ){
        for( i=0; i< periodosEliminados.length; i++){
          if(periodosEliminados[i].cveIdPeriodoCuentaIndividual === model.cveIdPeriodoCuentaIndividual ){
            periodosEliminados.splice(i,1);
            break;
          }
        }
      }
    break;
    case "M":
      if( !this.render.isEmpty( periodosModificados ) ){
        for( i=0; i< periodosModificados.length; i++){
          if(periodosModificados[i].cveIdPeriodoCuentaIndividual === model.cveIdPeriodoCuentaIndividual ){
            
            var periodo = periodosModificados[i];
            // console.log(JSON.stringify( periodo ));
            model.tipoMovimientoIncial = periodo.tipoMovimientoInicial;
            model.origenMovimientoIncial = periodo.origenMovimientoInicial;
            model.tipoSalario = periodo.tipoSalario;
            model.eventual = periodo.eventual;
            model.extemporaneoConvenioSuspension = periodo.extemporaneoConvenioSuspension;
            model.subrogacionServicio = periodo.subrogacionServicio;
            model.tipoMovimientoFinal = periodo.tipoMovimientoFinal;
            model.origenMovimientoFinal = periodo.origenMovimientoFinal;
            model.fechaFinalMovimiento = periodo.fechaFinalMovimiento;
            model.jornadaSemanal = periodo.jornadaSemanal;
            periodosModificados.splice(i,1);
            break;
          }
        }
      }
    break;
  }
  
  model.changed = [];
  model.state = "pristine";
  this.render.notify("PeriodoCuentaIndividualConsultaComponent", this.component.id);
};
PeriodoCuentaIndividualConsultaComponent.prototype.delete = function () {
  var model = this.render.getModel(this.component.model);
  model.changed = [];  
  
  // Incluir el registro al arreglo de eliminados.
  
  // Buscar si existe un periodo en el bloque de incluidos
    var periodosEliminados = this.render.getModel( this.component.parentId + ".periodosEliminados");
    // console.log( "periodosEliminados:" + periodosEliminados );
    if( this.render.isEmpty( periodosEliminados ) ){    
      var periodosRegistroPatronal = this.render.getModel( this.component.parentId );
      periodosRegistroPatronal.periodosEliminados = [];        
      periodosEliminados = periodosRegistroPatronal.periodosEliminados;
    }
    
    var periodoOriginalCopia = JSON.parse( JSON.stringify( model ) );
    periodosEliminados[periodosEliminados.length] = periodoOriginalCopia;
    
  model.state = "delete";
  model.tipoRegularizacionPeriodo = "E";
  
  this.render.notify("PeriodoCuentaIndividualConsultaComponent", this.component.id);
};

PeriodoCuentaIndividualConsultaComponent.prototype.validate = function () {
  //var model = this.render.getModel( this.component.model );  
  var auxModel = {};

  this.render.module.validator.formToModel(
          this.render.toId("FormPanelComponent-" + this.render.toId(this.component.model)),
          auxModel);





  var valid = this.render.module.validator.validForm({entity: "periodo", name: this.render.toId(this.component.model)});






};


PeriodoCuentaIndividualConsultaComponent.prototype.notify = function (metadata, modelName) {
  this.component = metadata;
  var html = this.drawBody();
  $("#periodoCuentaIndividual_" + this.render.toId(this.component.id)).html(html);
};

PeriodoCuentaIndividualConsultaComponent.prototype.triggerEvent = function (event, parameters) {
  switch (event) {
    case "change":
      this.changed(parameters);
      break;
    case "undo":
      this.undo(parameters);
      break;
    case "delete":
      this.delete(parameters);
      break;
    case "validate":
      this.validate(parameters);
      break;

  }
};

