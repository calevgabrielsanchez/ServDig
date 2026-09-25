function CuentaIndividualComponent(render) {
  this.render = render;
}
CuentaIndividualComponent.prototype.draw = function (component) {
  this.component = component;

  //Configuracion de los botones iniciales
  var disabled = [false, false, false, false, false];
  this.render.module.controller.model.cuentaIndividualButtonGroup = disabled;
  //Tab inicial
  this.render.module.controller.model.tabs_cuentaIndividual = 0;

  var html = '<div id="mainCuentaIndividual_' + this.component.id + '" >';
  html += this.drawBody();
  html += '</div>';
  this.render.addLinker( new Link( this.component.id, "CuentaIndividualComponent", this.component ) ); 
  return html;
};
CuentaIndividualComponent.prototype.drawBody = function () {
  var model = this.render.getModel(this.component.model);
  var html = "";
  var i;
  var htmlAux = "";
  if (model !== undefined) {
    //this.prepareModel();
    
    if( model.listaTipoTramites !== undefined && model.listaTipoTramites !== null ){
      htmlAux += "<ul>";
      for(i=0; i<model.listaTipoTramites.length;i++){
        htmlAux +="<li>"+model.listaTipoTramites[i]+"</li>";        
      }
      htmlAux += "</ul>";
    }
    if( model.tipoTramite !== undefined ){
      htmlAux += model.tipoTramite;
    }

    var metadata = {type: "PanelComponent", 
      label:"Regularizar Cuenta Individual",
      layout: [[{span: 12}],[{span: 12}],[{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}]],
      components: [
        { type:"PanelComponent", level:4, label:"Informaci\u00f3n de la solicitud", layout:[[{span:6},{span:6}],[{span:6},{span:6}]], 
          components:[
            { type:"LabelComponent", className:"label-cda", label:"Folio"}, { type:"LabelComponent", className:"label-cda", label:"Tipo de tr\u00e1mite de la solicitud"},
            { type:"LabelComponent", label: model.folioSolicitud }, { type:"HTMLComponent", html: htmlAux }
          ]},
        {type: "TabPanelComponent", id: "cuentaIndividual",
          tabs: [
            {title: "CERTIFICADOR",
              panel: {type: "PanelComponent", id: "tab-certificadorPager", className:"scrolled",                
                components: [
                  {
                    type:"LabelComponent", tag:"h5", label:"Informaci\u00f3n de cuenta individual del n\u00famero de seguridad social con el que se certificar\u00e1 el tr\u00e1mite"
                  },
                  {
                    type: "CuentaIndividualNssComponent", indexName: "certificadorPager",
                    parentId: this.component.model,
                    id: this.component.model + ".listaNssTipoCertificador",
                    model: this.component.model + ".listaNssTipoCertificador"
                  },
                  {type: "NssPagerComponent", componentName: "CuentaIndividualNssComponent",
                    parentId: this.component.model + ".listaNssTipoCertificador",
                    id: "certificadorPager", model: this.component.model + ".listaNssTipoCertificador", numberOfElements: 5}
                ], layout: [[{span: 12}],[{span: 12}], [{span: 12}]]}},
            {title: "ASOCIADO AL ASEGURADO",
              panel: {type: "PanelComponent", id: "tab-asociadoPager", className:"scrolled",
                components: [
                  {
                    type: "CuentaIndividualNssComponent", indexName: "asociadoPager",
                    parentId: this.component.model,
                    id: this.component.model + ".listaNssTipoAsociadoAsegurado",
                    model: this.component.model + ".listaNssTipoAsociadoAsegurado"
                  },
                  {type: "NssPagerComponent", componentName: "CuentaIndividualNssComponent",
                    parentId: this.component.model + ".listaNssTipoAsociadoAsegurado",
                    id: "asociadoPager", model: this.component.model + ".listaNssTipoAsociadoAsegurado", numberOfElements: 5}
                ], layout: [[{span: 12}], [{span: 12}]]}},
            {title: "NO CORRESPONDEN AL ASEGURADO", panel: {type: "PanelComponent", id: "tab-noPertenecePager", className:"scrolled",
                components: [
                  {
                      type: "CuentaIndividualNssComponent", indexName: "noPertenecePager",
                      parentId: this.component.model,
                      id: this.component.model + ".listaNssTipoNoCorrespondeAsegurado",
                      model: this.component.model + ".listaNssTipoNoCorrespondeAsegurado"
                    },
                    {type: "NssPagerComponent", componentName: "CuentaIndividualNssComponent",
                    parentId: this.component.model + ".listaNssTipoNoCorrespondeAsegurado",
                    id: "noPertenecePager", model: this.component.model + ".listaNssTipoNoCorrespondeAsegurado", numberOfElements: 5}
                ], layout: [[{span: 12}], [{span: 12}]]}}
          ]
        },
        {type: "ButtonGroupComponent", id: "cuentaIndividualButtonGroup",
          components: [
            {type: "ButtonComponent", id: this.render.toId(this.component.id + ".buttonBack"),
              label: "Regresar", icon: "", className: "btn-default",
              trigger: {type: "CuentaIndividualComponent", event: "back", key: this.component.id, model: ""}
            },
            {type: "ButtonComponent", id: this.render.toId(this.component.id + ".informacion"),
              label: "Informaci\u00f3n de la solicitud", icon: "", className: "btn-default",
              command: "transition", argument:"'informacionSolicitud'"
            },            
            {type: "ButtonComponent", id: this.render.toId(this.component.id + ".buttonTray"),
              label: "Bandeja de Solicitudes", icon: "", className: "btn-default",
              trigger: {type: "CuentaIndividualComponent", event: "tray", key: this.component.id, model: ""}
            },
            {type: "ButtonComponent", id: this.render.toId(this.component.id + ".buttonNext"),
              label: "Siguiente", icon: "", className: "btn-primary",
              trigger: {type: "CuentaIndividualComponent", event: "next", key: this.component.id, model: ""}
            }
          ]
        },
        {
          type:"ModalComponent",
          id:"cuentaIndividualModal1",
          title:"Mensaje de sistema",
          body:{
            components:[
              { type: "LabelComponent", label: "No se han identificado cambios realizados a los periodos de cuenta individual de los NSS involucrados."},
              { type: "LabelComponent", label: "\u00BFDesea continuar?"},
              {
                type:"ButtonGroupComponent",
                components:[
                  {
                    type:"ButtonComponent",
                    trigger: {type: "CuentaIndividualComponent", event: "complete", key: this.component.id, model: ""},
                    label:"Aceptar",
                    href:"#top",
                    className:"btn btn-primary"
                  },
                  {
                      type:"ButtonComponent",
                      trigger: {type: "CuentaIndividualComponent", event: "closeModal", key: this.component.id, model: {alert:"cuentaIndividualModal1"} },
                      label:"Cancelar",
                      href:"#top",
                      className:"btn btn-primary"
                    }
                ]
              }
            ],
            layout:[[{span:12}],[{span:12}],[{span:12}]]
          }
         },
         {
          type:"ModalComponent",
          id:"cuentaIndividualModal3",
          title:"Mensaje de sistema",
          body:{
            components:[
              { type: "LabelComponent", id:"label_02_033", label: "Los periodos asociados al NRP [NRP - NOMBRE o RAZON SOCIAL] no fueron modificados en su totalidad."},
              { type: "LabelComponent", label: "\u00BFDesea continuar?"},
              {
                type:"ButtonGroupComponent",
                components:[
                  {
                    type:"ButtonComponent",
                    trigger: {type: "CuentaIndividualComponent", event: "send", key: this.component.id, model: ""},
                    label:"Aceptar",
                    href:"#top",
                    className:"btn btn-primary"
                  },
                  {
                      type:"ButtonComponent",
                      trigger: {type: "CuentaIndividualComponent", event: "closeModal", key: this.component.id, model: {alert:"cuentaIndividualModal3"} },
                      label:"Cancelar",
                      href:"#top",
                      className:"btn btn-primary"
                    }
                ]
              }
            ],
            layout:[[{span:12}],[{span:12}],[{span:12}]]
          }
         },
         {
          type:"ModalComponent",
          id:"cuentaIndividualModal2",
          title:"Mensaje de sistema",
          body:{
            components:[
              { type: "LabelComponent", label: "No se han identificado cambios realizados a los periodos de cuenta individual."},
              { type: "LabelComponent", label: "\u00BFDesea continuar?"},
              {
                type:"ButtonGroupComponent",
                components:[
                  {
                    type:"ButtonComponent",
                    trigger: {type: "CuentaIndividualComponent", event: "send", key: this.component.id, model: ""},
                    label:"Aceptar",
                    href:"#top",
                    className:"btn btn-primary"
                  },
                  {
                      type:"ButtonComponent",
                      trigger: {type: "CuentaIndividualComponent", event: "closeModal", key: this.component.id, model: {alert:"cuentaIndividualModal2"} },
                      label:"Cancelar",
                      href:"#top",
                      className:"btn btn-primary"
                    }
                ]
              }
            ],
            layout:[[{span:12}],[{span:12}],[{span:12}]]
          }
         },
         {
          type:"ModalComponent",
          id:"cuentaIndividualModal4",
          title:"Mensaje de sistema",
          body:{
            components:[
              { type: "LabelComponent", label: "Los cambios realizados a la cuenta individual del NSS involucrado ser\u00e1n registrados."},
              { type: "LabelComponent", label: "\u00BFDesea continuar?"},
              {
                type:"ButtonGroupComponent",
                components:[
                  {
                    type:"ButtonComponent",
                    trigger: {type: "CuentaIndividualComponent", event: "send", key: this.component.id, model: ""},
                    label:"Aceptar",
                    href:"#top",
                    className:"btn btn-primary"
                  },
                  {
                      type:"ButtonComponent",
                      trigger: {type: "CuentaIndividualComponent", event: "closeModal", key: this.component.id, model: {alert:"cuentaIndividualModal4"} },
                      label:"Cancelar",
                      href:"#top",
                      className:"btn btn-primary"
                    }
                ]
              }
            ],
            layout:[[{span:12}],[{span:12}],[{span:12}]]
          }
         },
         {
          type:"ModalComponent",
          id:"cuentaIndividualModal5",
          title:"Mensaje de sistema",
          body:{
            components:[
              { type: "LabelComponent", id:"labelModal5", label: ""},              
              {
                type:"ButtonGroupComponent",
                components:[                  
                  {
                      type:"ButtonComponent",
                      trigger: {type: "CuentaIndividualComponent", event: "closeModal", key: this.component.id, model: {alert:"cuentaIndividualModal5"} },
                      label:"Cerrar",
                      href:"#top",
                      className:"btn btn-primary"
                    }
                ]
              }
            ],
            layout:[[{span:12}],[{span:12}]]
          }
         }

      ]
    };


    var panel = new PanelComponent(this.render);
    html += panel.draw(metadata);
    this.render.module.render.volatileComponents[this.render.module.render.volatileComponents.length] = metadata;
  }
   
  return html;
};

CuentaIndividualComponent.prototype.digest = function (link) {  
  this.component = link.metadata;
  //this.render.module.service.cuentaIndividualService.readCuentaIndividualNss( this.component.id, this.render.module );
};


CuentaIndividualComponent.prototype.notify = function (metadata, modelName) {
  this.component = metadata;  
  var html = this.drawBody();
  $("#mainCuentaIndividual_" + this.component.id).html(html);
};


CuentaIndividualComponent.prototype.triggerEvent = function (event, parameters) {
  switch (event) {
   
    case "edit":
      this.edit();
      break;
    case "complete":
      this.render.module.service.cuentaIndividual.complete( this.component.model );
      $("#cuentaIndividualModal1").modal("hide");
      break;
    case "closeModal":
      var modalName = JSON.parse(parameters).alert;
      $("#"+modalName).modal("hide");
      break;    
    case "send":
      $("#cuentaIndividualModal3").modal("hide");
      $("#cuentaIndividualModal4").modal("hide");
      this.send();
      break;    
    case "next":
      this.render.module.service.consultaCuentaIndividual.fetch( this.render.module, "cuentaIndividualConsulta");
      //this.render.module.service.cuentaIndividual.complete( this.component.model );
      break;
    case "back":
      this.render.module.controller.transition("confirmarCorreccionDatos");     
      break;
    case "tray":
      this.render.module.controller.transition("bandejaSolicitudesUI");
      break;
      
  }
};
