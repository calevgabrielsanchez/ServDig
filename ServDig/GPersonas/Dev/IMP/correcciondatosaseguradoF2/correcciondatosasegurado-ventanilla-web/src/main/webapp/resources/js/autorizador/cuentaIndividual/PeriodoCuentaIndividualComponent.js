function PeriodoCuentaIndividualComponent(render) {
  this.render = render;
  this.dangerStyle = "#fcf8e3";
  this.successStyle = "#dff0d8";
}
PeriodoCuentaIndividualComponent.prototype.draw = function (component) {
  this.component = component;
  var html = '<div id="periodoCuentaIndividual_' + this.render.toId(this.component.id) + '" >';
  html += this.drawBody();
  html += '</div>';
  this.render.addLinker(new Link(component.id, "PeriodoCuentaIndividualComponent", component, 1));
  return html;
};
PeriodoCuentaIndividualComponent.prototype.digest = function (link) {
  this.component = link.metadata;
  var model = this.render.getModel( this.component.model);
  this.drawState( model.state );
};

PeriodoCuentaIndividualComponent.prototype.drawBody = function () {
  
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
    className: "form",
    components:[
      {
        type:"PanelComponent",
        components:[
          {type:"DatePickerFieldComponent", id: this.render.toId(this.component.id + ".fechaRecepcionMovimiento"), disabled: ""+(model.state !== "add"), field:"fechaRecepcionMovimiento"},
          {type:"SelectFieldComponent", 
            id: this.render.toId( this.component.id + ".tipoMovimientoInicial"),
            onChangeEvent:{
              type: "PeriodoCuentaIndividualComponent",
              event: "change",
              key: this.component.id,
              model: "tipoMovimientoInicial"
            },
            field:"tipoMovimientoInicial", elements:[{label:"1", value:"1"},{label:"8", value:"8"},{label:"7", value:"7"}] },
          {type:"SelectFieldComponent", field:"origenMovimientoInicial",
            id: this.render.toId( this.component.id + ".origenMovimientoInicial"),
            onChangeEvent:{
              type: "PeriodoCuentaIndividualComponent",
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
              type: "PeriodoCuentaIndividualComponent",
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
          {type:"SelectFieldComponent", field:"eventual",
            elements:[                
                {label:"1", value:"1"},
                {label:"2", value:"2"},
                {label:"3", value:"3"},
                {label:"4", value:"4"}                
              ]
          },
          {type:"SelectFieldComponent", field:"extemporaneoConvenioSuspencion",
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
              ]}
        ],
        layout:[[{span:2},{span:1},{span:1},{span:2},{span:2},{span:1},{span:1},{span:1},{span:1}]]
      },
      {
        type:"PanelComponent",
        components:[
          {type:"SelectFieldComponent", field:"tipoMovimientoFinal",
          elements:[
                {label:"0", value:"0"},
                {label:"2", value:"2"},
                {label:"7", value:"7"}               
              ]},
          {type:"SelectFieldComponent", field:"origenMovimientoFinal",
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
          {type:"DatePickerFieldComponent", id: this.render.toId(this.component.id + ".fechaFinalMovimiento"), field:"fechaFinalMovimiento"},
          {type:"SelectFieldComponent", field:"numeroConsecutivoPeriodos",
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
          {type:"SelectFieldComponent", field:"nss",
          elements:parentModel.elements
                  },
          {type:"TextFieldComponent", field:"regularizacion"},
          {type:"TextFieldComponent", 
            id: this.render.toId(this.component.id + ".status"),
            field:"status", disabled:"true"},
          {type: "ButtonGroupComponent",            
            components: [
              {type: "ButtonComponent",id: this.render.toId(this.component.id + ".buttonRemove"),
                label: "", icon: "trash", className: "btn-link",                
                trigger:{
                  type: "PeriodoCuentaIndividualComponent",
                  event: "delete",
                  key: this.component.id,
                  model: ""}
                },
              {type: "ButtonComponent",id: this.render.toId(this.component.id + ".buttonDelete"),
                label: "", icon: "remove", className: "btn-link",                
                trigger:{
                  type: "CuentaIndividualComponent",
                  event: "remove",
                  key: this.component.parentId,
                  model: ""+this.component.element}
                },
              {type: "ButtonComponent",id: this.render.toId(this.component.id + ".buttonUndo"), label: "", icon: "circle-arrow-left", className: "btn-link",                
                trigger:{
                  type: "PeriodoCuentaIndividualComponent",
                  event: "undo",
                  key: this.component.id,
                  model: ""}
              },
              {type: "ButtonComponent",id: this.render.toId(this.component.id + ".buttonValidate"), label: "", icon: "ok", className: "btn-link",                
                trigger:{
                  type: "PeriodoCuentaIndividualComponent",
                  event: "validate",
                  key: this.component.id,
                  model: ""}
              }
              
            ]
          }
        ],
        layout:[[{span:1},{span:1},{span:2},{span:1},{span:2},{span:2},{span:2},{span:1}]]
      }
    ],
    layout:[[{span:6},{span:6}]]
  };
  
  
  var panelComponent = new FormPanelComponent(this.render);
  html += panelComponent.draw(metadata);
  this.render.module.render.volatileComponents[this.render.module.render.volatileComponents.length] = metadata;
  
  return html;
};

PeriodoCuentaIndividualComponent.prototype.drawState = function (state) {
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

PeriodoCuentaIndividualComponent.prototype.changed = function (field) {
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
      }

    }else{
      if(model.state !== "add"){
        model.state = "pristine";
      }
    }    
  }
  this.drawState(model.state);
};

PeriodoCuentaIndividualComponent.prototype.undo = function () {
  var model = this.render.getModel( this.component.model);  
  model.changed = [];
  model.state = "pristine";
  this.render.notify("PeriodoCuentaIndividualComponent", this.component.id);  
};
PeriodoCuentaIndividualComponent.prototype.delete = function () {
  var model = this.render.getModel( this.component.model);  
  model.changed = [];
  model.state = "delete";  
  this.render.notify("PeriodoCuentaIndividualComponent", this.component.id);  
};

PeriodoCuentaIndividualComponent.prototype.validate = function () {
  //var model = this.render.getModel( this.component.model );  
  var auxModel = {};
  
  this.render.module.validator.formToModel(
    this.render.toId("FormPanelComponent-" + this.render.toId(this.component.model) ),
    auxModel);
  
  $.validator.addMethod("9Sep", function(value, element) {
      return auxModel.fechaInicioMovimiento === value;
      //return  /09\/09\/2018/.test(value);
    });
  
  $.validator.addMethod("custom3", function(value, element) {
    
    var result = auxModel.eventual + auxModel.extemporaneoConvenioSuspencion;
      return result === "";
      //return  /09\/09\/2018/.test(value);
    });
  
    
  
  
  var valid = this.render.module.validator.validForm({ entity:"periodo", name: this.render.toId(this.component.model)  } );
  
  
  
  
  
    
};


PeriodoCuentaIndividualComponent.prototype.notify = function (metadata, modelName) {
  this.component = metadata;
  var html = this.drawBody();  
  $("#periodoCuentaIndividual_" + this.render.toId(this.component.id)).html(html);
};

PeriodoCuentaIndividualComponent.prototype.triggerEvent = function (event, parameters) {
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

