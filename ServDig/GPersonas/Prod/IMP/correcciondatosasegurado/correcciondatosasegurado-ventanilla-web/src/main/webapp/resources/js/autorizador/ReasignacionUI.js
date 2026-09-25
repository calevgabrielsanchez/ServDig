function ReasignacionUI(module) {
  this.module = module;
  this.init();
}

ReasignacionUI.prototype.init = function () {
  this.panel ={
    type: "FormPanelComponent",
    id: "panelReasignacionComponent",
    name: "reasignacion",
    model: "reasignacion",
    components: [
      {
        type: "SelectFieldComponent",
        label: "Responsable",
        id: "responsableSelectField",
        field: "responsable",
        data: "fetchResponsables"
      },
      {type: "TextAreaFieldComponent", field: "detalle", label: "Observaciones<span>*<span>:", upperCase:true, rows:4},
      {
        type:"ButtonGroupComponent",
        components:[            
          {
            type: "ButtonComponent",
            label: "Cancelar",
            className: "btn-default",
            command: "reasignacionController.regresar"
          },
          {
            type: "ButtonComponent",
            id: "btnReasignar",
            label: "Reasignar",              
            command: "reasignacionController.reasignar",
            className: "btn-primary"
          }
        ]
      }
    ],
    layout: [[{span: 6}, {span: 6}],[{span: 12}]]
  };
};