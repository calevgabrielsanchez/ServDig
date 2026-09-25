function SolicitarInformacionUI(module) {
  this.module = module;
  this.init();
}

SolicitarInformacionUI.prototype.init = function () {
  this.panel ={
    type: "FormPanelComponent",
    id: "panelReasignacionComponent",
    name: "solicitarInformacion",
    model: "solicitarInformacion",
    components: [ 
    {
              type: "PanelComponent",
              collapsed: false,
              label: "Requerimiento de informaci\u00F3n adicional para el Asegurado",
              level: 3,
              id: "panelTipoRegularizacion",
              components: [                                
              ],
              layout: [[{span: 12}]]
            },
     {type:"LabelComponent" ,
      label: "Especifique la información que el Asegurado debe proporcionar<span>*<span>:",
      popover: {title:"Indique además comentarios adicionales que se le enviarán por correo al Asegurado" },
     },
     {type: "TextAreaFieldComponent", 
      field: "detalle",  
      className:"control-label", 
      upperCase:true,
      },
       {
        type:"ButtonGroupComponent",
        components:[
          {
            type: "ButtonComponent",
            label: "Cancelar",
            className: "btn-default",
            command: "solicitarInformacionController.regresar"
          },
          {
            type: "ButtonComponent",        
            label: "Enviar",              
            command: "solicitarInformacionController.guardar",
            className: "btn-primary"
          }
        ]
      }
    ],
    layout: [[{span:12},{span: 12}, {span: 12}],[{span: 12}]]
  };
};