function CancelarUI(module) {
  this.module = module;
  this.init();
}


CancelarUI.prototype.init = function () {
  this.panel={
          type: "FormPanelComponent",
          id: "cancelar",
          name: "cancelar",
          model: "cancelar",          
          label: "Cancelar solicitud de regularizaci\u00F3n y/o correcci\u00F3n de datos",
          level: 3,
          components: [
            {type: "TextAreaFieldComponent", field: "detalle", label: "Especifique el motivo de cancelaci\u00F3n de la solicitud<span>*<span>:", upperCase:true, className:"control-label"},
            {
              type: "ButtonGroupComponent",              
              components: [                
                {
                  type: "ButtonComponent",
                  id: "btnRegresarCorreccion",
                  label: "Salir",
                  command: "cancelarController.regresar",
                  className: "btn-default"
                },
                {
                  type: "ButtonComponent",
                  label: "Cancelar",
                  command: "cancelarController.cancelar",
                  className: "btn-primary"
                }
              ]
            }
          ],
          layout: [[{span:12}],[{span:12}]]
        };
};