function ConfirmarCorreccionDatosComponent(render) {
  this.render = render;
}
ConfirmarCorreccionDatosComponent.prototype.draw = function (component) {
  this.component = component;

  //Configuracion de los botones iniciales
//  var disabled = [false,false, true,true,true];
  var disabled = [false,false, true,false];
  if( this.component.readOnly !== true ){    
      disabled = [true,false, false,true];
  
  }else{
    if( this.component.autorizador === true){
      disabled = [true,false, true,false];
    }
    
  }
  this.render.module.controller.model.correccionDatosButtonGroup = disabled;
  //Tab inicial
  this.render.module.controller.model.tabs_correccionDatos = 0;

  var html = '<div id="mainCorreccionDatos_' + this.render.toId(this.component.id) + '" >';
  html += this.drawBody();
  html += '</div>';
  return html;
};
ConfirmarCorreccionDatosComponent.prototype.drawBody = function () {
  var html = "";
  var model = this.render.getModel(this.component.model);

  if (!this.render.isEmpty(model)) {


    var metadata = {type: "PanelComponent",
      layout: [[{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}]],
      components: [

        {type: "PanelComponent", label: "Informaci\u00f3n de la solicitud", level: 4,
          components: [
            {type: "LabelComponent", tag:"label", label: "Folio"},
            {type: "LabelComponent", tag:"label", label: "Tipo de tr\u00e1mite de la solicitud"},
            {type: "LabelComponent", label: model.solicitud.folio},
            {type: "LabelComponent", label: model.solicitud.tipoTramite}
          ],
          layout: [[{span: 6}, {span: 6}], [{span: 6}, {span: 6}]]
        },

        {type: "PanelComponent", label: "Confirmar cambios de actualizaci\u00f3n de datos", level: 4,
          components: [
            {type: "FormPanelComponent", label: "Informaci\u00f3n en RENAPO", level: 5, name: this.component.model+".renapo", model: this.component.model+".renapo", entity: "correccionDatos",
              horizontal: {label: 5, control: 7},
              layout: [[{span: 4}, {span: 4}, {span: 4}]],
              components: [
                {type: "PanelComponent", components: [
                    {type: "TextFieldComponent", readOnly: "true",aling:"true", field: "curp", label: "CURP:", className: "bg-dark text-center"},
                    {type: "TextFieldComponent", readOnly: "true",aling:"true", field: "nombre", className: "bg-dark text-center", label: "Nombre(s):"},
                    {type: "TextFieldComponent", readOnly: "true",aling:"true", field: "apellidoPaterno", className: "bg-dark text-center", label: "Primer apellido:"},
                    {type: "TextFieldComponent", readOnly: "true",aling:"true", field: "apellidoMaterno", className: "bg-dark text-center", label: "Segundo apellido:"}
                  ], layout: [[{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}]]},
                {type: "PanelComponent", components: [
                    {type: "TextFieldComponent", readOnly: "true",aling:"true", field: "lugarNacimiento", className: "bg-dark text-center", label: "Lugar de nacimiento:"},
                    {type: "TextFieldComponent", readOnly: "true",aling:"true", field: "sexo", className: "bg-dark text-center", label: "Sexo:"},
                    {type: "TextFieldComponent", readOnly: "true",aling:"true", field: "nacionalidad", className: "bg-dark text-center", label: "Nacionalidad:"},
                    {type: "TextFieldComponent", readOnly: "true",aling:"true", field: "fechaNacimiento", className: "bg-dark text-center", label: "Fecha de nacimiento:"}
                  ], layout: [[{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}]]},
                {type: "PanelComponent", components: [
                    {type: "TextAreaFieldComponent", readOnly: "true", field: "datosDocumentoProbatorio", className: "bg-dark", label: "Documento Probatorios:", rows: 3},
                    {type: "TextAreaFieldComponent", readOnly: "true", field: "curpHistoricas", className: "bg-dark", label: "CURP Hist\u00f3ricas:", rows: 3}
                  ], layout: [[{span: 12}], [{span: 12}]]}

              ]
            }
          ],
          layout: [[{span: 12}]]
        },

        {type: "TabPanelComponent", id: "correccionDatos",
          tabs: [
            {title: "CERTIFICADOR", panel: {type: "PanelComponent",
                label: "Informaci\u00f3n del n\u00famero de seguridad social con el que se certificar\u00e1 el tr\u00e1mite", layout: [[{span: 12}]],
                components: [
                  {
                    type: "IteratorComponent", indexName: "index",
                    id: this.component.id + ".certificador",
                    model: this.component.model + ".certificador",
                    entry: {
                      type: "ConfirmarCorreccionDatosEntryComponent",
                      parentId: this.component.id, element: "index",
                      id: this.component.id + ".certificador[index]",
                      model: this.component.model + ".certificador[index]"
                    }
                  }
                ]}},
            {title: "ASOCIADOS AL ASEGURADO", panel: {type: "PanelComponent", layout:[[{span:12}]],
                label: "Informaci\u00f3n de los n\u00fameros de seguridad social que seran cancelados por duplicidad",
                components: [
                  {
                    type: "IteratorComponent", indexName: "index",
                    id: this.component.id + ".asociado",
                    model: this.component.model + ".asociado",
                    entry: {
                      type: "ConfirmarCorreccionDatosAsociadoEntryComponent",
                      parentModel: this.component.model, element: "index",
                      id: this.component.id + ".asociado[index]",
                      model: this.component.model + ".asociado[index]"
                    }
                  }
                ]}},
            {title: "NO CORRESPONDEN AL ASEGURADO", panel: {type: "PanelComponent", layout: [[{span: 12}]], 
                label: "Informaci\u00f3n de los n\u00fameros de seguridad social que no pertenecen al asegurado",
                components: [
                  {
                    type: "IteratorComponent", indexName: "index",
                    id: this.component.id + ".noCorresponde",
                    model: this.component.model + ".noCorresponde",
                    entry: {
                      type: "ConfirmarCorreccionDatosNoCorrespondeComponent",
                      parentModel: this.component.model, element: "index",
                      id: this.component.id + ".noCorresponde[index]",
                      model: this.component.model + ".noCorresponde[index]"
                    }
                  }
                ]
              }
            }
            
          ]
        },

        {type: "ButtonGroupComponent", id: "correccionDatosButtonGroup",
          components: [
            {type: "ButtonComponent", id: this.render.toId(this.component.id + ".buttonExit"),
              label: "Salir", icon: "", className: "btn-danger",
              trigger: {type: "ConfirmarCorreccionDatosComponent", event: "exit", key: this.component.id, model: ""}
            },
            {type: "ButtonComponent", id: this.render.toId(this.component.id + ".buttonBack"),
              label: "Regresar", icon: "", className: "btn-default",
              trigger: {type: "ConfirmarCorreccionDatosComponent", event: "back", key: this.component.id, model: ""}
            },
            {type: "ButtonComponent", id: this.render.toId(this.component.id + ".buttonNext"),
              label: "Siguiente", icon: "", className: "btn-primary",
              trigger: {type: "ConfirmarCorreccionDatosComponent", event: "save", key: this.component.id, model: ""}
            },
            /*{type: "ButtonComponent", 
              label: "Autorizar", icon: "", className: "btn-primary", command:"envioSindo"
              
            },*/
            {type: "ButtonComponent", 
              label: "Continuar", icon: "", className: "btn-primary",
              trigger: {type: "ConfirmarCorreccionDatosComponent", event: "next", key: this.component.id, model: ""}
            }/*,
            {type: "ButtonComponent", 
              label: "Rechazar", icon: "", className: "btn-danger", command:"rechazarController.mostrarRechazar"
              
            }*/
            
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


ConfirmarCorreccionDatosComponent.prototype.notify = function (metadata, modelName) {
  this.component = metadata;
  var html = this.drawBody();
  $("#mainCorreccionDatos_" + this.render.toId(this.component.id)).html(html);
};

ConfirmarCorreccionDatosComponent.prototype.save = function () {
//  this.render.module.service.fetchCuentaIndividual();
  this.render.module.service.confirmarCorreccionDatos.save( this.render.module );  
  
};

ConfirmarCorreccionDatosComponent.prototype.triggerEvent = function (event, parameters) {
  switch (event) {  
    case "exit":
      document.location.reload();
      break;
    case "save":
      this.save();
      break;
    case "next":
      this.render.module.controller.next(this.component.screen);
      break;
    case "back":
      this.render.module.controller.back(this.component.screen);
      //this.render.module.controller.transition("correccionDatos");
      break;
	
  }
};

