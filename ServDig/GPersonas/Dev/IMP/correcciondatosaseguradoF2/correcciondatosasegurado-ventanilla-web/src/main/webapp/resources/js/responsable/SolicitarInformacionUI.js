function SolicitarInformacionUI(module) {
  this.module = module;
  this.init();
}
$(document).ready(function(){ 
    $('#mytooltip').tooltip();
  });

SolicitarInformacionUI.prototype.init = function () {
  this.panel ={
    type: "FormPanelComponent",
    id: "panelSolicitarInformacionComponent",
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
               label: "Especifique la información que el Asegurado debe proporcionar<span>:<span>* <span style='width: 100px; height: 100px; -moz-border-radius: 100%;  -webkit-border-radius: 100%; border-radius: 100%; background: #000;color:#fff'>&nbsp;?&nbsp;</span>",
			   popover: {title:"Indique además comentarios adicionales que se le enviarán por correo al Asegurado" },
         	},
            {type: "TextAreaFieldComponent", 
                field: "detalle",  
                className:"control-label", 
                upperCase:true,
                maxlength:"500",
                oncopy:"return false",
                oncut:"return false",
                onpaste:"return false"
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
    layout: [[{span:12},{span: 12},{span: 12}],[{span: 12}]]
  };
};
