function RechazarUI(module) {
  this.module = module;
  this.init();
}

RechazarUI.prototype.init = function () {
  this.panel ={
    type: "FormPanelComponent",
    id: "rechazarFormPanelComponent",
    name: "rechazar",
    model: "rechazar", 
    label: "Rechazar solicitud y devolver para atenci\u00F3n al Responsable:",
    level: 3,
    entity: "SeguimientoTramite",
    components: [
      {type:"LabelComponent", label:"", className:"h5"},
      {type: "TextAreaFieldComponent", field: "detalle", label: "Motivo rechazo<span>*<span>:", className:"control-label", upperCase:true, rows: 8},
      {
        type:"ButtonGroupComponent",
        components:[
          {
            type: "ButtonComponent",
            label: "Regresar",
            className: "btn-default",
            command: "rechazarController.regresar"
          },
          {
            type: "ButtonComponent",        
            label: "Rechazar Solicitud",              
            command: "rechazarController.guardar",
            className: "btn-primary"
          }
        ]
      }
    ],
    layout: [[{span:12}],[{span:12}],[{span:12}]]
  };
};