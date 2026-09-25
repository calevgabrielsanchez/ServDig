function CorreccionDatosComponent(render) {
  this.render = render;
}
CorreccionDatosComponent.prototype.draw = function (component) {
  this.component = component;
  
  
  //Configuracion de los botones iniciales
  var disabled = [false, false, false];
  this.render.module.controller.model.correccionDatosButtonGroup = disabled;
  //Tab inicial
  this.render.module.controller.model.tabs_correccionDatos = 0;

  var html = '<div id="mainCorreccionDatos_' + this.render.toId(this.component.id) + '" >';
  html += this.drawBody();
  html += '</div>';
  return html;
};
CorreccionDatosComponent.prototype.drawBody = function () {
  var html = "";
  var model = this.render.getModel(this.component.model);
  var currentNss = this.render.getModel("currentNss");
  if( currentNss === undefined ){
    currentNss = 0;
  
  currentNss = parseInt(currentNss);}
  if (model !== undefined) {
    
  // Aplicar reglas de negocio
  //this.render.module.model.correccionDatos.RNFUE16( currentNss );
  
  
  
  

  var metadata = {type: "PanelComponent", label:"Correcci\u00f3n de datos" ,  
    layout: 
    [ [{span: 3},{span: 6},{span: 3}],
      [{span: 3},{span: 9}],
      [{span: 12}] 
    ],
    components: [            
      {type:"TabPanelComponent", id:"correccionDatosRenapo",
        tabs:[
          { title:"RENAPO", panel:{ type:"PanelComponent", components:[{type:"FormaCorreccionDatosComponent", id:"renapo",  model: this.component.model+".renapo", source: this.component.model+".renapo",  label:"Informaci\u00f3n de RENAPO"}], layout:[[{span: 12}]] } }
        ]
      },
      {type:"TabPanelComponent", id:"correccionDatos",
        tabs:[
          { title:"CANASE", panel:{ type:"PanelComponent", components:[{type:"FormaCorreccionDatosComponent", id:"canase", source: this.component.model+".renapo", model: this.component.model+".listaNss["+currentNss+"].canase",  label:"Informaci\u00f3n del IMSS - CANASE"}], layout:[[{span: 12}]] } },
          { title:"CIZ1", panel:{ type:"PanelComponent", components:[{type:"FormaCorreccionDatosComponent", id:"cizUno", source: this.component.model+".renapo", model: this.component.model+".listaNss["+currentNss+"].cizUno", label:"Informaci\u00f3n del IMSS - CIZ1"}], layout:[[{span: 12}]] } },
          { title:"CIZ2", panel:{ type:"PanelComponent", components:[{type:"FormaCorreccionDatosComponent", id:"cizDos", source: this.component.model+".renapo", model: this.component.model+".listaNss["+currentNss+"].cizDos", label:"Informaci\u00f3n del IMSS - CIZ2"}], layout:[[{span: 12}]] } },
          { title:"CIZ3", panel:{ type:"PanelComponent", components:[{type:"FormaCorreccionDatosComponent", id:"cizTres", source: this.component.model+".renapo", model: this.component.model+".listaNss["+currentNss+"].cizTres",label:"Informaci\u00f3n del IMSS - CIZ3"}], layout:[[{span: 12}]] } },
          { title:"HIST\u00d3RICO", panel:{ type:"PanelComponent", components:[{type:"FormaCorreccionDatosComponent", source: this.component.model+".renapo", id:"historico", model: this.component.model+".listaNss["+currentNss+"].historico", label:"Informaci\u00f3n del IMSS - Hist\u00f3rico"}], layout:[[{span: 12}]] } },
          { title:"BDTU", panel:{ type:"PanelComponent", components:[{type:"FormaCorreccionDatosComponent", id:"bdtu", source: this.component.model+".renapo", model: this.component.model+".listaNss["+currentNss+"].bdtu", label:"Informaci\u00f3n del IMSS - BDTU"}], layout:[[{span: 12}]] } }
        ]
      } ,
      {type:"PanelComponent",
        components:[
          { type:"LabelComponent", label:"N\u00famero de Seguro Social registrado en solicitud", className:"text-center" },
          { type:"LabelComponent", className:"text-center", tag:"h3",  label: this.render.getModel( this.component.model+".listaNss["+currentNss+"].nss" )  },
          { type:"LabelComponent", label:"Localizado por CURP, incluido por sistema", className:"text-center" },
          { type:"EditCorreccionDatosComponent", id:"editCorreccionDatos", model: this.component.model}],
        layout:[[{span:12}],[{span:12}],[{span:12}],[{span:12}]]},
      {type: "LabelComponent", label:""},
      {type: "NssPagerComponent", parentId: this.component.id, id:"currentNss", numberOfElements: 5, model: this.component.model+".listaNss" },
      {type: "ButtonGroupComponent", id: "correccionDatosButtonGroup",
        components: [          
          {type: "ButtonComponent", id: this.render.toId(this.component.id + ".buttonNext"),
            label: "Siguiente", icon: "", className: "btn-primary",
            trigger: {type: "CorreccionDatosComponent", event: "save", key: this.component.id, model: ""}
          },
          {type: "ButtonComponent", id: this.render.toId(this.component.id + ".buttonBack"),
            label: "Regresar", icon: "", className: "btn-default",
            trigger: {type: "CorreccionDatosComponent", event: "back", key: this.component.id, model: ""}
          },
          {type: "ButtonComponent", id: this.render.toId(this.component.id + ".buttonTray"),
            label: "Bandeja de Solicitudes", icon: "", className: "btn-default",
            trigger: {type: "CorreccionDatosComponent", event: "tray", key: this.component.id, model: ""}
          }
        ]
      }

    ]
  };
  var panel = new PanelComponent(this.render);
  html += panel.draw(metadata);
  this.render.module.render.volatileComponents[this.render.module.render.volatileComponents.length] = metadata;
  }
  
  return html;
};


CorreccionDatosComponent.prototype.notify = function (metadata, modelName) {
  this.component = metadata;
  var html = this.drawBody();
  $("#mainCorreccionDatos_" + this.render.toId(this.component.id)).html(html);
};

CorreccionDatosComponent.prototype.page = function (page) {
  var index = this.render.module.controller.model.currentNss;
  if( index === undefined ){
    index = 0;
  }
  index = parseInt(index);  
  this.persist(index);  
  this.render.module.controller.model.currentNss = parseInt(page);      
  this.render.notify("CorreccionDatosComponent", this.component.id );  
};

CorreccionDatosComponent.prototype.persist = function( index ) {
  
  
  var model = this.render.getModel( this.component.model );  
  if(model.listaNss[index].tipoNss === undefined ){
    model.listaNss[index].tipoNss = {};
  }
  if(model.listaNss[index].tipoAclaracion === undefined ){
    model.listaNss[index].tipoAclaracion = {};
  }
  var row = model.listaNss[index].tipoNss;
  
  var forma = this.render.toId(this.component.model + ".listaNss[" + index + "].tipoNss");  
  this.render.module.validator.formToModel("FormPanelComponent-"+forma, row );  
  model.listaNss[index].tipoNss = row;
  
  var tipoAclaracion = {};
  forma = this.render.toId(this.component.model + ".listaNss[" + index + "].tipoAclaracion");  
  this.render.module.validator.formToModel("FormPanelComponent-"+forma, tipoAclaracion );  
  model.listaNss[index].tipoAclaracion = tipoAclaracion;
  
  
};


CorreccionDatosComponent.prototype.save = function (page) {
  var index = this.render.module.controller.model.currentNss;
  if( index === undefined ){
    index = 0;
  }
  index = parseInt(index);
  this.persist(index);
  var model = this.render.getModel( this.component.model );    
  this.render.module.model.correccionDatos.prepareValidator( model.listaNss[index].tipoNss );
  
  var forma = this.render.toId(this.component.model + ".listaNss[" + index + "].tipoNss");  
  //var valid = this.render.module.validator.validForm({entity: "tipoNss", name: forma});
  
  //if( valid ){
    
  this.render.module.service.confirmarCorreccionDatos.fetch( this.render.module );
  
  //}
  
  
};



CorreccionDatosComponent.prototype.triggerEvent = function (event, parameters) {
  switch (event) {
    case "page":
      this.page(parameters);
      break;
    case "save":
      this.save();
      break;
    case "tray":      
      document.location.reload();
      break; 
    case "back":
      // console.log("Regresando...");
      this.render.module.controller.transition("informacionSolicitud");
      break;
  }
};

