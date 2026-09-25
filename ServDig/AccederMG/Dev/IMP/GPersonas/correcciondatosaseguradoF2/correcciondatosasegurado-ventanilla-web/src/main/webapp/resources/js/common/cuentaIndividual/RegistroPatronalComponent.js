function RegistroPatronalComponent(render) {
  this.render = render;
  this.buttonDisabledState = {
      collapsed :        [false , true,  true, true, true, true, true, true, true ],
      readyToConfirmRP: [true , true,  false, true, true, true, true, true, false ],
      expandedNss:      [true , false,  true, false, true, false, true, true, true ],
      readyToConfirmNss: [true , true,  true, false, true, true, true, true, false ],
      editPeriodos: [true , false,  true, true, false, true, false, false, true ],
      readyToConfirm: [true , true,  true, true, false, true, true, true, false ]
  };
}
RegistroPatronalComponent.prototype.change = function () {
 this.component.changed = true;
};
RegistroPatronalComponent.prototype.updateToolBarButtons = function () {
    if( this.render.isEmpty( this.component.state ) ){
        this.component.state = "collapsed";
    }
    var index = this.render.getModel(this.component.indexNameParent);
    if( this.render.isEmpty( index) ){
      index = 0;
    }
    index = parseInt( index );
    var nssPadre = this.render.getModel( this.component.parentId + "[" + index +"]" );
    console.log(this.component.parentId + "[" + index +"]");
    if( nssPadre !== undefined && nssPadre.movimientoAclaracionCuentaIlogica > 0 ){
      this.buttonDisabledState.expandedNss = [true , false,  true, false, true, false, true, true, true ];      
    }else{
      this.buttonDisabledState.expandedNss = [true , false,  true, false, true, true, true, true, true ];
    }
    
    
    var state = this.component.state;
    this.render.module.updateModel("ButtonGroupComponent", this.render.toId( this.component.id + "-buttonGroup") , this.buttonDisabledState[state] );
};
RegistroPatronalComponent.prototype.draw = function (component) {
  this.component = component;
  this.component.state = "collapsed";
  this.component.changed = false;
  this.component.pageNumber = 1;
  var html = '<div id="cuentaIndividual_' + this.render.toId(this.component.id) + '" >';
  html += this.drawBody();
  html += '</div>';
  return html;
};
RegistroPatronalComponent.prototype.drawBody = function () {
  var model = this.render.getModel(this.component.model);
  var j;
  if (model === undefined) {
    model = {};
  }
  model.elements = [];
  if( !this.render.isEmpty( model.listaNss) ){
    for (j = 0; j < model.listaNss.length; j++) {
      model.elements[model.elements.length] = {label: model.listaNss[j].nss, value: model.listaNss[j].cveIdDetalleNssCda};
    }
  }
  var metadata = {
    type: "PanelComponent", id: this.render.toId(this.component.id + ".panel1"),
    components: [
      {
        type: "FormPanelComponent", entity: "cuentaIndividual",
        model: this.component.model, name: this.component.model,
        className: "form",
        components: [
          {type: "HTMLComponent", html:"<a name='" +this.render.toId( this.component.id + "-anchor") + "'  href='#" +this.render.toId( this.component.id + "-anchor") + "' id='" +this.render.toId( this.component.id + "-anchor") + "'>.</a>" },
          { type:"HRComponent", className: "primary" },          
          {type: "LabelComponent", className:"label-cda",  label: "Registro Patronal: "+model.numeroRegistroPatronal+" - " + this.render.nvl(model.nombreRegistroPatronal,"")},
          {type: "LabelComponent", className:"label-cda",  label: "CIZ: " + model.claveCiz },          
          {type: "LabelComponent", className:"label-cda",  label: "NSS Destino: "},
          {type: "SelectFieldComponent", field: "nssDestino", label: "", elements: model.elements,
            id: this.render.toId( this.component.id + ".nssDestino"),
             onChangeEvent:{
               type: "RegistroPatronalComponent",
               event: "changeNssRp",
               key: this.component.id,
               model: this.component.model
             }
           },
           
           {type: "LabelComponent", className:"label-cda",  label: "Delegaci\u00f3n: "+model.claveDelegacionOrigen + " - " + this.render.nvl(model.nombreDelegacionOrigen,"")},
           {type:"PanelComponent", components:[
           {type: "LabelComponent", tag:"span", className:"label label-info",  label: model.incluidos },
           {type: "LabelComponent", tag:"span", className:"label label-success",  label: model.nuevos }, 
           {type: "LabelComponent", tag:"span", className:"label label-warning",  label: model.modificados },           
           {type: "LabelComponent", tag:"span", className:"label label-danger",  label: model.eliminados } ], layout:[[{span:3},{span:3},{span:3},{span:3}]] },
           {type:"ButtonGroupComponent", id:this.render.toId( this.component.id + "-buttonGroup"), components:[
            {type: "ButtonComponent", icon: "zoom-in",className:"btn-sm btn-default", trigger: { type: "RegistroPatronalComponent", event: "detail", key: this.component.id, model: this.component.model }},
            {type: "ButtonComponent", icon: "zoom-out",className:"btn btn-sm btn-default", trigger: { type: "RegistroPatronalComponent", event: "close", key: this.component.id, model: this.component.model }},
            {type: "ButtonComponent", icon: "save",className:"btn btn-sm btn-primary", trigger: { type: "RegistroPatronalComponent", event: "preSaveRP", key: this.component.id, model: this.component.model }},
            {type: "ButtonComponent", icon: "save",className:"btn btn-sm btn-primary", trigger: { type: "RegistroPatronalComponent", event: "save", key: this.component.id, model: this.component.model }},
            {type: "ButtonComponent", icon: "save",className:"btn btn-sm btn-primary", trigger: { type: "RegistroPatronalComponent", event: "save", key: this.component.id, model: this.component.model }},
            {type: "ButtonComponent", icon: "pencil",className:"btn btn-sm btn-warning", trigger: { type: "RegistroPatronalComponent", event: "edit", key: this.component.id, model: this.component.model }},
            {type: "ButtonComponent", icon: "user",className:"btn btn-sm btn-info", trigger: { type: "RegistroPatronalComponent", event: "nss", key: this.component.id, model: this.component.model }},
            {type: "ButtonComponent", icon: "plus",className:"btn btn-sm btn-success", trigger: { type: "RegistroPatronalComponent", event: "add", key: this.component.id, model: this.component.model }},
            {type: "ButtonComponent", icon: "chevron-left",className:"btn btn-sm btn-danger", trigger: { type: "RegistroPatronalComponent", event: "undo", key: this.component.id, model: this.component.model }}
            ]},          
         {
          type:"ModalComponent",
          id:this.render.toId( this.component.id + "-Modal"),
          title:"Mensaje de sistema",
          body:{
            components:[
              { type: "LabelComponent", label: "Los cambios realizados anteriormente a la cuenta individual de este registro patronal ser\u00e1n eliminados."},
              { type: "LabelComponent", label: "\u00BFDesea continuar?"},
              {
                type:"ButtonGroupComponent",
                components:[
                  {
                    type:"ButtonComponent",
                    trigger: {type: "RegistroPatronalComponent", event: "saveRP", key: this.component.id, model: this.component.model },
                    label:"Aceptar",
                    href:"#top",
                    className:"btn btn-primary"
                  },
                  {
                      type:"ButtonComponent",
                      trigger: {type: "RegistroPatronalComponent", event: "closeModal", key: this.component.id, model: {alert:this.render.toId( this.component.id + "-Modal")} },
                      label:"Cancelar",
                      href:"#top",
                      className:"btn btn-primary"
                    }
                ]
              }
            ],
            layout:[[{span:12}],[{span:12}],[{span:12}]]
          }},
        {
          type:"ModalComponent",
          id:this.render.toId( this.component.id + "-Modal2"),
          title:"Mensaje de sistema",
          body:{
            components:[
              { type: "LabelComponent", label: "Los cambios sin confirmar ser\u00e1n eliminados."},
              { type: "LabelComponent", label: "\u00BFDesea continuar?"},
              {
                type:"ButtonGroupComponent",
                components:[
                  {
                    type:"ButtonComponent",
                    trigger: {type: "RegistroPatronalComponent", event: "postPage", key: this.component.id, model: this.component.pageNumber },
                    label:"Aceptar",
                    href:"#top",
                    className:"btn btn-primary"
                  },
                  {
                      type:"ButtonComponent",
                      trigger: {type: "RegistroPatronalComponent", event: "closeModal", key: this.component.id, model: {alert:this.render.toId( this.component.id + "-Modal2")} },
                      label:"Cancelar",
                      href:"#top",
                      className:"btn btn-primary"
                    }
                ]
              }
            ],
            layout:[[{span:12}],[{span:12}],[{span:12}]]
          }}
        ],
        layout: [
          [{span: 12}],[{span: 12}],
            [{span: 6}, {span: 3}, {span: 1}, {span: 2}],
            [{span: 5}, {span: 4}, {span: 3}],
            [{span: 12}],[{span: 12}]
        ]
      },
      { type:"PanelComponent", id: this.render.toId(this.component.id +"-detail"), components:[], layout:[] }
    ],
    layout: [ [{span: 12}],  [{span: 12}] ]
  };

  var html = "";  
  var panel = new PanelComponent(this.render);  
  html += panel.draw( metadata );
  this.render.module.render.addVolatileComponent( metadata );
  this.render.addLinker( new Link( this.component.id, "RegistroPatronalComponent", this.component ) );  
  return html;

};

RegistroPatronalComponent.prototype.digest = function (link) {  
  this.component = link.metadata;
  var model = this.render.getModel( this.component.model );
  $("#" + this.render.toId( this.component.model + ".nssDestino") ).val(-1);
  $("#" + this.render.toId( this.component.model + ".nssDestino") ).change();
  this.component.state = "collapsed";
  this.updateToolBarButtons();
};

RegistroPatronalComponent.prototype.notify = function (metadata, modelName) {
  this.component = metadata;
  var html = this.drawBody();
  $("#cuentaIndividual_" + this.render.toId(this.component.id)).html(html);
  
};

RegistroPatronalComponent.prototype.changeNssRp = function (parameters) {
  if ($("#" + this.render.toId( parameters + ".nssDestino") ).val() !== null ){
    this.component.state = "readyToConfirmRP";
    this.updateToolBarButtons();
  }
};
RegistroPatronalComponent.prototype.undo = function (parameters) {
  switch( this.component.state ){
    
    case "readyToConfirmRP":
        $("#" + this.render.toId( JSON.parse(parameters) + ".nssDestino") ).val(-1);
        this.component.state = "collapsed";
        this.updateToolBarButtons();
    break;
    
    case "readyToConfirmNss":
      this.component.state = "expandedNss";
      this.updateToolBarButtons();      
      break;
    
    case "readyToConfirm":
      this.component.state = "editPeriodos";
      this.updateToolBarButtons();      
      break;
      
  }
  
};
RegistroPatronalComponent.prototype.saveRP = function (parameters) {
  this.component.state = "collapsed";
  this.updateToolBarButtons();
  $("#" + this.render.toId( this.component.id + "-Modal") ).modal("hide");
  var model = this.render.getModel( this.component.model );
  console.log( model );
  var selected = JSON.parse($("#" + this.render.toId( JSON.parse(parameters) + ".nssDestino") ).val());
  console.log( selected );
  for(var i=0; i< model.listaNss.length; i++ ){
      console.log( model.listaNss[i].cveIdDetalleNssCda );
      if( model.listaNss[i].cveIdDetalleNssCda === selected){
          model.nssDestino = model.listaNss[i];
          this.render.module.service.cuentaIndividualService.createCuentaIndividualCorreccionNssRegistroPatronal(model, this.component.parentId);
          break;
      }
  }
  $("#" + this.render.toId( JSON.parse(parameters) + ".nssDestino") ).val(-1);
  this.component.changed = false;
};
RegistroPatronalComponent.prototype.add = function (parameters) {
  // Añadir un elemento en el arreglo del listado de nuevos.
  var registroPatronal = this.render.getModel( this.component.model );
  
  var periodoNuevo = { 
    destino:{ 
      cuentaIndividualNss: {}, 
      cuentaIndividualPeriodo: {}, 
      tipoRegularizacionPeriodo: "" }, 
    origen:{ 
      cuentaIndividualNss:{ cveIdDetalleNssCda: registroPatronal.data[0].cuentaIndividualNss.cveIdDetalleNssCda , nss: registroPatronal.data[0].cuentaIndividualNss.nss }, 
      cuentaIndividualPeriodo: {}, 
      tipoRegularizacionPeriodo: "A" } 
  };
  periodoNuevo.origen.cuentaIndividualPeriodo.cuentaIndividualNss = periodoNuevo.origen.cuentaIndividualNss;
  registroPatronal.nuevos[registroPatronal.nuevos.length] = periodoNuevo;  
  this.render.notify("IteratorComponent" , this.component.model + ".nuevos");
  this.component.changed = true;
};
RegistroPatronalComponent.prototype.edit = function (parameters) {
  this.component.state = "editPeriodos";
  this.updateToolBarButtons();
  
  // Obtener el panel de detalle, actualizar el metadata y redibujarlo
  var panelId = this.render.toId( JSON.parse(parameters) + "-detail" );
  var panel = this.render.getComponentById( panelId , "PanelComponent" );
  
  var modelName = JSON.parse(parameters);
  
  panel.components = [
      { type:"HRComponent", className: "default" },
      {
        type: "PanelComponent",
        components: [          
          {type: "LabelComponent", className:"label-cda",  label: "F RECEP"},
          {type: "LabelComponent", className:"label-cda",  label: "O"},
          {type: "LabelComponent", className:"label-cda",  label: "T"},
          {type: "LabelComponent", className:"label-cda",  label: "FECHA"},
          {type: "LabelComponent", className:"label-cda",  label: "SBC"},
          {type: "LabelComponent", className:"label-cda",  label: "T SBC"},
          {type: "LabelComponent", className:"label-cda",  label: "TT"},
          {type: "LabelComponent", className:"label-cda",  label: "EXT"},
          {type: "LabelComponent", className:"label-cda",  label: "SS"}
          
        ],
        layout: [[{span:2},{span:1},{span:1},{span:2},{span:2},{span:1},{span:1},{span:1},{span:1}]]
      },
      {
        type: "PanelComponent",
        components: [          
          {type: "LabelComponent", className:"label-cda",  label: "O"},
          {type: "LabelComponent", className:"label-cda",  label: "T"},
          {type: "LabelComponent", className:"label-cda",  label: "FECHA"},
          {type: "LabelComponent", className:"label-cda",  label: "JOR"},          
          {type: "LabelComponent", className:"label-cda",  label: "NSS Destino"},
          {type: "LabelComponent", className:"label-cda",  label: "Regularizaci\u00f3n"},
          {type: "LabelComponent", className:"label-cda",  label: "Consecutivo"}
        ],
        layout: [[{span:1},{span:1},{span:2},{span:1},{span:3},{span:2},{span:2}]]
      },
      {
        type: "IteratorComponent", indexName: "index",
        id: modelName + ".periodos",
        model: modelName + ".periodos",
        entry: {
          type: "PeriodoCuentaIndividualNssComponent",
          parentId: modelName, element: "index", state:"edit",
          id: modelName + ".periodos[index]",
          model: modelName + ".periodos[index]"
        }
      },
      { type:"NssPagerComponent", componentName:"RegistroPatronalComponent", parentId: modelName, numberOfElements:5 , id: this.render.toId(modelName + ".pager"),  model:modelName + ".pager"},
      {
        type: "IteratorComponent", indexName: "index",
        id: modelName + ".nuevos",
        model: modelName + ".nuevos",
        entry: {
          type: "PeriodoCuentaIndividualNssComponent",
          parentId: modelName, element: "index", state:"add",
          id: modelName + ".nuevos[index].origen.cuentaIndividualPeriodo",
          model: modelName + ".nuevos[index].origen.cuentaIndividualPeriodo"
        }
      },
      { type:"HRComponent", className: "default" }
      
      ];
  panel.layout = [[{span:12}],[{span:6},{span:6}],[{span:12}],[{span:12}],[{span:12}]];
  this.render.notify("PanelComponent", panelId, "" );
  
  
};

RegistroPatronalComponent.prototype.nss = function (parameters) {
    
  this.component.state = "expandedNss";
  this.updateToolBarButtons();

  // Obtener el panel de detalle, actualizar el metadata y redibujarlo
  var panelId = this.render.toId( JSON.parse(parameters) + "-detail" );
  var panel = this.render.getComponentById( panelId , "PanelComponent" );
  
  var modelName = JSON.parse(parameters);
  
  panel.components = [
    { type:"HRComponent", className: "default" },
    {
        type: "PanelComponent",
        components: [          
          {type: "LabelComponent", className:"label-cda",  label: "F RECEP"},
          {type: "LabelComponent", className:"label-cda",  label: "O"},
          {type: "LabelComponent", className:"label-cda",  label: "T"},
          {type: "LabelComponent", className:"label-cda",  label: "FECHA"},
          {type: "LabelComponent", className:"label-cda",  label: "SBC"},
          {type: "LabelComponent", className:"label-cda",  label: "T SBC"},
          {type: "LabelComponent", className:"label-cda",  label: "TT"},
          {type: "LabelComponent", className:"label-cda",  label: "EXT"},
          {type: "LabelComponent", className:"label-cda",  label: "SS"}
          
        ],
        layout: [[{span:2},{span:1},{span:1},{span:2},{span:2},{span:1},{span:1},{span:1},{span:1}]]
      },
      {
        type: "PanelComponent",
        components: [          
          {type: "LabelComponent", className:"label-cda",  label: "O"},
          {type: "LabelComponent", className:"label-cda",  label: "T"},
          {type: "LabelComponent", className:"label-cda",  label: "FECHA"},
          {type: "LabelComponent", className:"label-cda",  label: "JOR"},          
          {type: "LabelComponent", className:"label-cda",  label: "NSS Destino"},
          {type: "LabelComponent", className:"label-cda",  label: "Regularizaci\u00f3n"},
          {type: "LabelComponent", className:"label-cda",  label: "Consecutivo"}
        ],
        layout: [[{span:1},{span:1},{span:2},{span:1},{span:3},{span:2},{span:2}]]
      },
      {
        type: "IteratorComponent", indexName: "index",
        id: modelName + ".periodos",
        model: modelName + ".periodos",
        entry: {
          type: "PeriodoCuentaIndividualNssComponent",
          parentId: modelName, element: "index", state:"nss",
          id: modelName + ".periodos[index]",
          model: modelName + ".periodos[index]"
        }
      },
      { type:"NssPagerComponent", componentName:"RegistroPatronalComponent", parentId: modelName, numberOfElements:5 , id: this.render.toId(modelName + ".pager"),  model:modelName + ".pager"},
      {
        type: "IteratorComponent", indexName: "index",
        id: modelName + ".nuevos",
        model: modelName + ".nuevos",
        entry: {
          type: "PeriodoCuentaIndividualNssComponent",
          parentId: modelName, element: "index", state:"read",
          id: modelName + ".nuevos[index]",
          model: modelName + ".nuevos[index]"
        }
      },
      { type:"HRComponent", className: "default" }
      
      ];
  panel.layout = [[{span:12}],[{span:6},{span:6}],[{span:12}],[{span:12}],[{span:12}]];
  this.render.notify("PanelComponent", panelId, "" );
};
RegistroPatronalComponent.prototype.detail = function (parameters) {
  $("#" + this.render.toId( JSON.parse(parameters) + ".nssDestino") ).prop('disabled', true);
  this.nss(parameters);
  var modelName = JSON.parse(parameters);
  var model = this.render.getModel(modelName);
  this.render.module.service.cuentaIndividualService.readPageCuentaIndividualPeriodo(modelName, 1, model);
};
RegistroPatronalComponent.prototype.close = function (parameters) {
  this.component.state = "collapsed";
  this.updateToolBarButtons();
  var model = this.render.getModel(this.component.model);  
  $("#" + this.render.toId( JSON.parse(parameters) + ".nssDestino") ).prop('disabled', false);
  // Obtener el panel de detalle, actualizar el metadata y redibujarlo
  var panelId = this.render.toId( JSON.parse(parameters) + "-detail" );
  var panel = this.render.getComponentById( panelId , "PanelComponent" );
  panel.components = [ ];
  panel.layout = [];
  this.render.notify("PanelComponent", panelId, "" );
};
RegistroPatronalComponent.prototype.remove = function (parameters) {
  var modelName = this.component.model;
  var model = this.render.getModel(modelName); 
  var index = JSON.parse(parameters);
  model.nuevos.splice(index,1);
  this.render.notify("IteratorComponent", modelName + ".nuevos", "" );
  this.component.changed = true;
};
RegistroPatronalComponent.prototype.page = function (parameters) {
  window.location.hash = this.render.toId( this.component.id + "-anchor");
  var modelName = this.component.model;
  var model = this.render.getModel(modelName);  
  this.render.module.service.cuentaIndividualService.readPageCuentaIndividualPeriodo(modelName, model.pager[parameters].nss, model);
  console.log("Llevando el has a: "+ this.render.toId( this.component.id + "-anchor")); 
  $("#" + this.render.toId( this.component.id + "-anchor") ).click();
  window.location.hash = this.render.toId( this.component.id + "-anchor");
  console.log("WLH: " + window.location.hash ); 
};
RegistroPatronalComponent.prototype.save = function (parameters) {
  var modelName = this.component.model;
  var model = this.render.getModel(modelName);
  var valid = true;
  for( var i=0; i< model.periodos.length; i++){
    /*this.render.module.validator.formToModel(
      this.render.toId("FormPanelComponent-" + this.component.model + ".periodos[" + i + "]"),
      model.periodos[i]
    );*/    
    this.render.module.model.periodoCuentaIndividual.prepareValidator( model.periodos[i], this.render.toId( this.component.model + ".periodos[" + i + "]" ) );
    valid = valid && this.render.module.validator.validForm({entity: "periodo", name: this.render.toId(this.component.model+".periodos[" + i + "]")});
  }
  // Validar las formas de los componentes nuevos
  for( var i=0; i< model.nuevos.length; i++){
    this.render.module.validator.formToModel(
      this.render.toId("FormPanelComponent-" + this.component.model + ".nuevos[" + i + "].origen.cuentaIndividualPeriodo"),
      model.nuevos[i].origen.cuentaIndividualPeriodo
    );
    console.log(model.nuevos[i].origen.cuentaIndividualPeriodo);
    this.render.module.model.periodoCuentaIndividual.prepareValidator( model.nuevos[i].origen.cuentaIndividualPeriodo, this.render.toId( this.component.model + ".nuevos[" + i + "].origen.cuentaIndividualPeriodo" ) );
    valid = valid && this.render.module.validator.validForm({entity: "periodo", name: this.render.toId(this.component.model+".nuevos[" + i + "].origen.cuentaIndividualPeriodo")});
  }
  if( valid ){
    this.render.module.service.cuentaIndividualService.createCuentaIndividualListaCorreccion(model,this.component.parentId, this.component.id );
    this.component.state = "collapsed";
    this.updateToolBarButtons();
    this.close( '"'+this.component.id+'"' );
    this.component.changed = false;
  }
};
RegistroPatronalComponent.prototype.triggerEvent = function (event, parameters) {
  switch (event) {
    case "changeNssRp" : this.changeNssRp( parameters ); break;
    case "undo" : this.undo( parameters ); break;
    case "saveRP" : this.saveRP( parameters ); break;
    case "detail": this.detail(parameters); break;
    case "nss": this.nss(parameters);break;
    case "close": this.close(parameters); break;
    case "edit": this.edit(parameters); break;
    case "page": 
      this.component.pageNumber = JSON.parse(parameters);
      if( this.component.changed === true ){
        $("#" + this.render.toId( this.component.id + "-Modal2") ).modal();
      }else{
        this.page(this.component.pageNumber); 
      }
      break;
    case "postPage": 
      $("#" + this.render.toId( this.component.id + "-Modal2") ).modal("hide");
      this.page(this.component.pageNumber); 
      this.component.changed = false;
      break;
    case "change": this.change(parameters); break;
    case "add": this.add(parameters); break;
    case "save": this.save(parameters); break;
    case "remove": this.remove(parameters); break;
    case "preSaveRP" : $("#" + this.render.toId( this.component.id + "-Modal") ).modal(); break;
    case "closeModal":
      var modalName = JSON.parse(parameters).alert;
      $("#"+modalName).modal("hide");
    break; 
  }
};

