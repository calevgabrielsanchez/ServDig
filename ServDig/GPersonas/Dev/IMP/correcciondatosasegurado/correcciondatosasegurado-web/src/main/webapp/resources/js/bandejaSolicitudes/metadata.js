var metadata = {
    ui: {
        components: [
          {
            type:"PanelComponent",
            label:"Bandeja de Solicitudes",
            id:"panelBandejaSolicitudes",
            components:[
              {
                type:"UserProfileComponent",
                id:"userProfile",
                model:"userProfile"
              },
              {
                type:"AlertComponent",
                id:"alert",
                model:"alert"
              },              
              {
                type:"PanelComponent",
                label:"",
                id:"panelTramitesAsignados",
                components:[
                  {
                    type:"GridComponent",
                    title:"Tramites Asignados",
                    id:"gridTramites",
                    model:"gridTramites",
                    onclick:"selectTramite",
                    data:"fetchTramites",
                    columns:[
                      { label:"Folio",name:"folio"},
                      { label:"Fecha de solicitud",name:"fechaSolicitud"},
                      { label:"NSS involucrados",name:"nssInvolucrados"},
                      { label:"Origen",name:"origen"},
                      { label:"Responsable",name:"responsable"},
                      { label:"Estatus",name:"estatus"},
                      { label:"Última actualización",name:"ultimaActualizacion"},
                      { label:"Tipo de regularización y/o correción",name:"tipo"}
                    ]
                  },
                  {
                    type:"LabelComponent",
                    label:""
                  },
                  {
                    type:"ButtonComponent",
                    command:"nuevaSolicitud",
                    label:"Ingresar Nueva Solicitud",
                    className:"pull-right btn-primary"
                  },                  
                  {
                    type:"GridComponent",
                    title:"Histórico de solicitudes",
                    id:"gridHistorico",
                    model:"gridHistorico",
                    onclick:"selectHistorico",
                    data:"fetchHistorico",
                    columns:[
                      { label:"Folio",name:"folio"},
                      { label:"Fecha de solicitud",name:"fechaSolicitud"},
                      { label:"NSS involucrados",name:"nssInvolucrados"},
                      { label:"Origen",name:"origen"},
                      { label:"Responsable",name:"responsable"},
                      { label:"Autorizó",name:"autorizo"},
                      { label:"Estatus",name:"estatus"},
                      { label:"Última actualización",name:"ultimaActualizacion"}
                    ]
                  },
                  {
                    type:"LabelComponent",
                    label:""
                  },
                  {
                    type:"ButtonComponent",
                    command:"salir",
                    label:"Salir",
                    className:"pull-right btn-primary"
                  }
                ],
                layout:[[{span:12}],[{span:8},{span:4}],[{span:12}],[{span:8},{span:4}]]
              }
            ],
            layout:[[{span:12}],[{span:12}],[{span:12}]]
          }
        ],
        layout: [[{span:12}]]
    }
};