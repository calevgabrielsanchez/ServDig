function ConsultaSolicitudReasignarUI(module) {
  this.module = module;
  this.init();
}

ConsultaSolicitudReasignarUI.prototype.init = function () {
  this.metadata = {
    ui: {
      components: [
        {
          type: "FormPanelComponent",
          id: "panelConsultaSolicitudComponent",
          name: "consultaSolicitud",
          model: "consultaSolicitud",
          components: [            
            {type: "TextFieldComponent", field: "folio", label: "Folio", disabled: true},
            {type: "TextFieldComponent", field: "estatus", label: "Estatus", disabled: true},
            {type: "TextFieldComponent", field: "fechaInicio", label: "Fecha de solicitud", disabled: true},
            {type: "TextFieldComponent", field: "responsable", label: "Responsable", disabled: true},
            {
              type: "PanelComponent",
              collapsed: false,
              label: "Tipo de Regularizaci\u00F3n y/o Correcci\u00F3n",
              level: 5,
              id: "panelTipoRegularizacion",
              components: [                
                {type: "CheckBoxFieldComponent", checked: true, disabled: true, field: "tipoRegularizacion.correccionDatosBasicos", label: "Correcci\u00F3n de datos b\u00E1sicos"},                
              ],
              layout: [[{span: 12}]]
            },
            {
              type: "ConsultaSolicitudComponent",
              model: "solicitud"
            },            
            {
              type:"ButtonGroupComponent",
              components:[
                {
                  type: "ButtonComponent",
                  command: "bandeja",
                  label: "Salir",
                  className: "btn-default"
                },
                {
                  type: "ButtonComponent",
                  id: "btnReasignar",
                  label: "Reasignar",              
                  command: "consultaSolicitudController.reasignar",
                  className: "btn-primary"
                }                  
              ]
            }
          ],
          layout: [[{span: 3},{span: 3},{span: 3}],[{span: 3}],[{span: 12}],[{span: 12}], [{span: 12}]]
        }
      ],
      layout: [[{span: 12}]]
    }
  };
};