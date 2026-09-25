function ReporteGridSolicitudesUI(module){
	this.module = module;
	this.init();

	
}

ReporteGridSolicitudesUI.prototype.init = function(){
	this.metadata = {
			ui: {
				components: [                        
				             {
				            	 type:"PanelComponent",
				            	 id:"panelTramitesAsignados",
				            	 components:[
				            	             {type: "FormPanelComponent",
				            	            	 collapsed: false,
				            	            	 level: 5,
				            	            	 id: "grid",
				            	            	 model : "gridTramites",
				            	            	 name : "fetchTramites",
				            	            	 components:  [
				            	            	               {
				            	            	            	   type:"GridComponent",
				            	            	            	   title:"Tr\u00E1mites Asignados",
				            	            	            	   id:"gridTramites",
				            	            	            	   model:"gridTramites",              
				            	            	            	   data:"fetchTramites", 
				            	            	            	   desfasado:true,
				            	            	            	   fontSize: "73%",
				            	            	            	   columns:[ 
				            	            	            	            { label:"Folio",name:"folio",href:"selectTramite"},
				            	            	            	            { label:"CURP",name:"curp"},
				            	            	            	            { label:"NSS involucrados",name:"nssInvolucrados"},
				            	            	            	            { label:"Vencida",name:"vencida"},
				            	            	            	            { label:"Delegaci\u00f3n",name:"delegacion"},
				            	            	            	            { label:"Subdelegaci\u00f3n",name:"subdelegacion"},
				            	            	            	            { label:"Autoriz\u00f3",name:"autorizo"},
				            	            	            	            { label:"Responsable",name:"responsable"},
				            	            	            	            { label:"Origen",name:"origen"},
				            	            	            	            { label:"Tipo de tr\u00e1mite",name:"tipo"},
				            	            	            	            { label:"Fecha de solicitud",name:"fechaSolicitud"},
				            	            	            	            { label:"Fecha de finalizaci\u00f3n",name:"fechaFinalizacion"},
				            	            	            	            { label:"\u00daltima actualizaci\u00f3n",name:"ultimaActualizacion"},
				            	            	            	            { label:"Estado",name:"estatus"}
				            	            	            	            ]
				            	            	               },
															   {type: "LabelComponent"},
				            	            	               {
				            	            	            	   type:"ButtonGroupComponent",
				            	            	            	   components:[
				            	            	            	               {
				            	            	            	            	   type:"ButtonComponent",
				            	            	            	            	   command:"regresarCorreccion",
				            	            	            	            	   label:"Regresar",
				            	            	            	            	   className:"btn-default"
				            	            	            	               },
				            	            	            	               {
				            	            	            	            	   type:"ButtonComponent",
				            	            	            	            	   command:"generaReporte",
				            	            	            	            	   label:"Generar Reporte",
				            	            	            	            	   className:"btn-primary"
				            	            	            	               },
				            	            	            	               {
				            	            	            	            	   type:"ButtonComponent",
				            	            	            	            	   command:"exportaExcel",
				            	            	            	            	   label:"Exportar Excel",
				            	            	            	            	   className:"btn-primary"
				            	            	            	               },
				            	            	            	               {
				            	            	            	            	   type:"ButtonComponent",
				            	            	            	            	   command:"salir",
				            	            	            	            	   label:"Salir",
				            	            	            	            	   className:"btn-danger"
				            	            	            	               }
				            	            	            	               ]
				            	            	               }
				            	            	               ],layout:[[{span:12}],[[{span:12}]],[{span:12}]]
				            	             }

				            	             ],
				            	             layout:[[{span:12}],[{span:12}],[{span:12}]]
				             }            
				             ]

			}
	};
	
};