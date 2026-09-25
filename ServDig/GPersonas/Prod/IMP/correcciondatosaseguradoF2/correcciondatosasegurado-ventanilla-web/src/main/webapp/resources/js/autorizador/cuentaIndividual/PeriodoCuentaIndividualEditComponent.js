function PeriodoCuentaIndividualEditComponent(render) {
  this.render = render;
  this.dangerStyle = "#fcf8e3";
  this.successStyle = "#dff0d8";
}
PeriodoCuentaIndividualEditComponent.prototype.draw = function (component) {
  this.component = component;
  var html = '<div id="periodoCuentaIndividual_' + this.render.toId(this.component.id) + '" >';
  html += this.drawBody();
  html += '</div>';
  this.render.addLinker(new Link(component.id, "PeriodoCuentaIndividualEditComponent", component, 1));
  return html;
};
PeriodoCuentaIndividualEditComponent.prototype.digest = function (link) {
  this.component = link.metadata;
  var model = this.render.getModel( this.component.model);
  this.drawState( model.state );
};

PeriodoCuentaIndividualEditComponent.prototype.drawBody = function () {
  
  var model = this.render.getModel( this.component.model);  
  if( model === undefined ){
    model = {};
  }
  if( model.changed === undefined ){
    model.changed = [];
  }
  var html = "";
  
  var parentModel = this.render.getModel( this.component.parentId);
  
  var metadata = {
    type: "FormPanelComponent", entity:"periodo",
    name: this.component.model, 
    model: this.component.model,
    className: "form", style: "height:18px",
    components:[
      {
        type:"PanelComponent",
        components:[
          {type:"DatePickerFieldComponent", id: this.render.toId(this.component.id + ".fechaRecepcionMovimiento"), disabled: ""+(model.state !== "add"), field:"fechaRecepcionMovimiento"},
          {type:"SelectFieldComponent", 
            id: this.render.toId( this.component.id + ".tipoMovimientoInicial"),
            onChangeEvent:{
              type: "PeriodoCuentaIndividualEditComponent",
              event: "change",
              key: this.component.id,
              model: "tipoMovimientoInicial"
            },
            field:"tipoMovimientoInicial", elements:[{label:"1", value:"1"},{label:"8", value:"8"},{label:"7", value:"7"}] },
          {type:"SelectFieldComponent", field:"origenMovimientoInicial",
            id: this.render.toId( this.component.id + ".origenMovimientoInicial"),
            onChangeEvent:{
              type: "PeriodoCuentaIndividualEditComponent",
              event: "change",
              key: this.component.id,
              model: "origenMovimientoInicial"
            },
              elements:[
                {label:"0", value:"0"},
                {label:"1", value:"1"},
                {label:"2", value:"2"},
                {label:"3", value:"3"},
                {label:"4", value:"4"},
                {label:"5", value:"5"},
                {label:"6", value:"6"},
                {label:"7", value:"7"},
                {label:"8", value:"8"},
                {label:"9", value:"9"}
              ]},
          {type:"DatePickerFieldComponent", id: this.render.toId(this.component.id + ".fechaInicioMovimiento"), disabled:""+(model.state !== "add"), field:"fechaInicioMovimiento"},
          {type:"TextFieldComponent", disabled:""+(model.state !== "add"), field:"salarioBase"},
          {type:"SelectFieldComponent", field:"tipoSalario",
            id: this.render.toId( this.component.id + ".tipoSalario"),
            onChangeEvent:{
              type: "PeriodoCuentaIndividualEditComponent",
              event: "change",
              key: this.component.id,
              model: "tipoSalario"
            },
          elements:[
                {label:"0", value:"0"},
                {label:"1", value:"1"},
                {label:"2", value:"2"},
                {label:"3", value:"3"},
                {label:"4", value:"4"},
                {label:"5", value:"5"},
                {label:"6", value:"6"},
                {label:"7", value:"7"},
                {label:"8", value:"8"},
                {label:"9", value:"9"}
              ]},
          {type:"SelectFieldComponent", field:"eventual", id: this.render.toId(this.component.id + ".eventual"),
            onChangeEvent:{
              type: "PeriodoCuentaIndividualEditComponent",
              event: "change",
              key: this.component.id,
              model: "eventual"
            },
            elements:[                
                {label:"1", value:"1"},
                {label:"2", value:"2"},
                {label:"3", value:"3"},
                {label:"4", value:"4"}                
              ]
          },
          {type:"SelectFieldComponent", field:"extemporaneoConvenioSuspencion",
            id: this.render.toId(this.component.id + ".extemporaneoConvenioSuspencion"),
            onChangeEvent:{
              type: "PeriodoCuentaIndividualEditComponent",
              event: "change",
              key: this.component.id,
              model: "extemporaneoConvenioSuspencion"
            },
          elements:[
                {label:"0", value:"0"},
                {label:"1", value:"1"},
                {label:"2", value:"2"},
                {label:"3", value:"3"},
                {label:"4", value:"4"},
                {label:"5", value:"5"},
                {label:"6", value:"6"},
                {label:"7", value:"7"},
                {label:"8", value:"8"},
                {label:"9", value:"9"}
              ]},
          {type:"SelectFieldComponent", field:"subrogacionServicio",
            id: this.render.toId(this.component.id + ".subrogacionServicio"),
            onChangeEvent:{
              type: "PeriodoCuentaIndividualEditComponent",
              event: "change",
              key: this.component.id,
              model: "subrogacionServicio"
            },
          elements:[
                {label:"0", value:"0"},
                {label:"1", value:"1"},
                {label:"2", value:"2"},
                {label:"3", value:"3"},
                {label:"4", value:"4"},
                {label:"5", value:"5"},
                {label:"6", value:"6"},
                {label:"7", value:"7"},
                {label:"8", value:"8"},
                {label:"9", value:"9"}
              ]},
            {type:"SelectFieldComponent", field:"tipoMovimientoFinal",
              id: this.render.toId(this.component.id + ".tipoMovimientoFinal"),
            onChangeEvent:{
              type: "PeriodoCuentaIndividualEditComponent",
              event: "change",
              key: this.component.id,
              model: "tipoMovimientoFinal"
            },
          elements:[
                {label:"0", value:"0"},
                {label:"2", value:"2"},
                {label:"7", value:"7"}               
              ]}
        ],
        layout:[[{span: 2}, {span: 1}, {span: 1}, {span: 2}, {span: 2}, {span: 1}, {span: 1}, {span: 1}, {span: 1}]]
      },
      {
        type:"PanelComponent",
        components:[
          
          {type:"SelectFieldComponent", field:"origenMovimientoFinal",
            id: this.render.toId(this.component.id + ".origenMovimientoFinal"),
            onChangeEvent:{
              type: "PeriodoCuentaIndividualEditComponent",
              event: "change",
              key: this.component.id,
              model: "origenMovimientoFinal"
            },
          elements:[
                {label:"0", value:"0"},
                {label:"1", value:"1"},
                {label:"2", value:"2"},
                {label:"3", value:"3"},
                {label:"4", value:"4"},
                {label:"5", value:"5"},
                {label:"6", value:"6"},
                {label:"7", value:"7"},
                {label:"8", value:"8"},
                {label:"9", value:"9"}
              ]},
          {type:"DatePickerFieldComponent", id: this.render.toId(this.component.id + ".fechaFinalMovimiento"), field:"fechaFinalMovimiento"
             ,onChangeEvent:{
              type: "PeriodoCuentaIndividualEditComponent",
              event: "change",
              key: this.component.id,
              model: "fechaFinalMovimiento"
            }
          }
          
          ,
          {type:"SelectFieldComponent", field:"jornadaSemanal",
            id: this.render.toId(this.component.id + ".jornadaSemanal"),
            onChangeEvent:{
              type: "PeriodoCuentaIndividualEditComponent",
              event: "change",
              key: this.component.id,
              model: "jornadaSemanal"
            },
          elements:[
                {label:"0", value:"0"},
                {label:"1", value:"1"},
                {label:"2", value:"2"},
                {label:"3", value:"3"},
                {label:"4", value:"4"},
                {label:"5", value:"5"},
                {label:"6", value:"6"},
                {label:"7", value:"7"},
                {label:"8", value:"8"},
                {label:"9", value:"9"}
              ]},
          {type:"TextFieldComponent", field:"regularizacion"},
          {type:"TextFieldComponent", 
            id: this.render.toId(this.component.id + ".status"),
            field:"status", disabled:"true"},
          {type: "ButtonGroupComponent",            
            components: [
              {type: "ButtonComponent",id: this.render.toId(this.component.id + ".buttonRemove"),
                label: "", icon: "trash", className: "btn-danger btn-sm",                
                trigger:{
                  type: "PeriodoCuentaIndividualEditComponent",
                  event: "delete",
                  key: this.component.id,
                  model: ""}
                },
              {type: "ButtonComponent",id: this.render.toId(this.component.id + ".buttonDelete"),
                label: "", icon: "remove", className: "btn-danger btn-sm",                
                trigger:{
                  type: "CuentaIndividualEditComponent",
                  event: "remove",
                  key: this.component.parentId,
                  model: ""+this.component.element}
                },
              {type: "ButtonComponent",id: this.render.toId(this.component.id + ".buttonUndo"), label: "", icon: "circle-arrow-left", className: "btn-warning btn-sm",                
                trigger:{
                  type: "PeriodoCuentaIndividualEditComponent",
                  event: "undo",
                  key: this.component.id,
                  model: ""}
              }
            ]
          }
        ],
        layout:[[{span:2},{span:2},{span:2},{span:2},{span:3},{span:1}]]
      }
    ],
    layout:[[{span:6},{span:6}]]
  };
  
  
  var panelComponent = new FormPanelComponent(this.render);
  html += panelComponent.draw(metadata);
  this.render.module.render.volatileComponents[this.render.module.render.volatileComponents.length] = metadata;
  
  return html;
};

PeriodoCuentaIndividualEditComponent.prototype.drawState = function (state) {
  switch(state){
    case "edit":
      $("#"+this.render.toId(this.component.id +".status")).css('background-color', this.dangerStyle );
      $("#"+this.render.toId(this.component.id +".status")).css('color', "#8a6d3b" );
      $("#"+this.render.toId(this.component.id +".status")).val("Modificado");
      $("#"+this.render.toId(this.component.id +".buttonRemove")).hide();
      $("#"+this.render.toId(this.component.id +".buttonUndo")).show();
      $("#"+this.render.toId(this.component.id +".buttonDelete")).hide();
      break;
    case "pristine":      
      $("#"+this.render.toId(this.component.id +".status")).css('background-color', "" );
      $("#"+this.render.toId(this.component.id +".status")).val("");
      $("#"+this.render.toId(this.component.id +".buttonUndo")).hide();
      $("#"+this.render.toId(this.component.id +".buttonRemove")).show();
      $("#"+this.render.toId(this.component.id +".buttonDelete")).hide();
      break;
    case "add":
      $("#"+this.render.toId(this.component.id +".status")).css('background-color', this.successStyle );
      $("#"+this.render.toId(this.component.id +".status")).val("Agregado");
      $("#"+this.render.toId(this.component.id +".status")).css('color', "#3c763d" );      
      $("#"+this.render.toId(this.component.id +".buttonUndo")).hide();
      $("#"+this.render.toId(this.component.id +".buttonRemove")).hide();
      $("#"+this.render.toId(this.component.id +".buttonDelete")).show();
      break;
    case "delete":
      $("#"+this.render.toId(this.component.id +".status")).css('background-color', "#f2dede" );
      $("#"+this.render.toId(this.component.id +".status")).val("Eliminado");
      $("#"+this.render.toId(this.component.id +".status")).css('color', "#a94442" );      
      $("#"+this.render.toId(this.component.id +".buttonUndo")).show();      
      $("#"+this.render.toId(this.component.id +".buttonRemove")).hide();
      $("#"+this.render.toId(this.component.id +".buttonDelete")).hide();
      break;
    default:
      $("#"+this.render.toId(this.component.id +".buttonRemove")).hide();
      $("#"+this.render.toId(this.component.id +".buttonUndo")).hide();      
      $("#"+this.render.toId(this.component.id +".buttonDelete")).hide();
      break;
    
  }
};

PeriodoCuentaIndividualEditComponent.prototype.changed = function (field) {
  var model = this.render.getModel( this.component.model);
  if( model.state !== "add" && model.state !== "delete"){
    var index;
    if( ""+model[field] !== $("#"+this.render.toId(this.component.id +"." + field)).val() ){    
      $("#"+this.render.toId(this.component.id +"." + field)).css('background-color', this.dangerStyle );
      index = model.changed.indexOf(field);
      if( index === -1){
        model.changed[model.changed.length] = field;
      }    
    }else{
      $("#"+this.render.toId(this.component.id +"." + field)).css('background-color', "" );
      index = model.changed.indexOf(field);
      if( index > -1){
        model.changed.splice(index,1);
      }
    }
    if( model.changed.length > 0 ){
      if(model.state !== "add"){
        model.state = "edit";
        var parameters = { type: 'CuentaIndividualComponent', event: 'edit', key: "cuentaIndividualAsegurado", model:"" };
        this.render.triggerEvent( window.btoa( JSON.stringify( parameters )) );          
      }

    }else{
      if(model.state !== "add"){
        model.state = "pristine";
      }
    }    
  }
  this.drawState(model.state);
  
};

PeriodoCuentaIndividualEditComponent.prototype.undo = function () {
  var model = this.render.getModel( this.component.model);  
  model.changed = [];
  model.state = "pristine";
  this.render.notify("PeriodoCuentaIndividualEditComponent", this.component.id);  
};
PeriodoCuentaIndividualEditComponent.prototype.delete = function () {
  var model = this.render.getModel( this.component.model);  
  model.changed = [];
  model.state = "delete";  
  this.render.notify("PeriodoCuentaIndividualEditComponent", this.component.id);  
};

PeriodoCuentaIndividualEditComponent.prototype.validate = function () {
  //var model = this.render.getModel( this.component.model );  
  var auxModel = {};
  
  this.render.module.validator.formToModel(
    this.render.toId("FormPanelComponent-" + this.render.toId(this.component.model) ),
    auxModel);
  
  
    
  
  
  var valid = this.render.module.validator.validForm({ entity:"periodo", name: this.render.toId(this.component.model)  } );
  
  
  
  
  
    
};


PeriodoCuentaIndividualEditComponent.prototype.notify = function (metadata, modelName) {
  this.component = metadata;
  var html = this.drawBody();  
  $("#periodoCuentaIndividual_" + this.render.toId(this.component.id)).html(html);
};

PeriodoCuentaIndividualEditComponent.prototype.triggerEvent = function (event, parameters) {
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

