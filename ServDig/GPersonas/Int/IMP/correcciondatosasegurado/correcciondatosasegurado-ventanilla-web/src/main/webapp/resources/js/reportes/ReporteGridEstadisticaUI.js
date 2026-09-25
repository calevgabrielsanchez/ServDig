function ReporteGridEstadisticaUI(module){
	this.module = module;
	this.init();

	
}

ReporteGridEstadisticaUI.prototype.init = function(){
	this.metadata = {
			ui: {
				components: [                        
				             {
				            	 type:"PanelComponent",
				            	 id:"panelTramitesAsignados",
				            	 components:[
				            	             {
											 type: "FormPanelComponent",
											  label:"Generaci\u00f3n de Reportes",
				            	            	 collapsed: false,
				            	            	 level: 5,
				            	            	 //id: "generaReporte",
				            	            	 id: "variablesFilter",
				            	            	 model : "gridReportes",
				            	            	 //name : "fetchTramites",
				            	            	 name : "variablesFilter",
				            	            	 components:  [
															   {type: "SelectFieldComponent", label: "Variable", id: "variableSelectField",onchange:"selectVariable", field: "variable",
																tamanoAnchoSelect:12, data: "fetchVariableCombo"},
															   {type: "LabelComponent", label: "Resultado", name:"resultado", className:"h5"},
															   {type: "TextFieldComponent", field: "delegacionReporte", label:"Delegaci\u00f3n", disabled: true, labelStyle:true},
															   {type: "TextFieldComponent", field: "subdelegacionReporte", label:"Subdelegaci\u00f3n", disabled: true, labelStyle:true},
															   {type: "TextFieldComponent", field: "estadisticaReporte", label:"Est\u00E1distica", disabled: true, labelStyle:true},
															  
															  
				            	            	               {
				            	            	            	   type:"GridComponent",
																   title:"",
				            	            	            	   id:"gridReportes",
				            	            	            	   model:"gridReportes",              
				            	            	            	   data:"fetchTramites", 
				            	            	            	   //breakWord:true,
				            	            	            	   columns:[ 
				            	            	            	            {label:"&nbsp;",name:"descripcion"},
				            	            	            	            {label:"Total registros", name:"cantidad"}
				            	            	            	            ]
				            	            	               },
				            	            	               {
				            	            	            	   type:"ButtonGroupComponent",
				            	            	            	   components:[
				            	            	            	               {
				            	            	            	            	   type:"ButtonComponent",
				            	            	            	            	   command:"regresarGrid",
				            	            	            	            	   label:"regresar",
				            	            	            	            	   className:"btn-primary"
				            	            	            	               },
				            	            	            	               {
				            	            	            	            	   type:"ButtonComponent",
				            	            	            	            	   id:'buttonObtenerReporte',
				            	            	            	            	   command:"obtenerReporte",
				            	            	            	            	   label:"Obtener reporte",
				            	            	            	            	   className:"btn-primary"
				            	            	            	               },				            	            	            	               
				            	            	            	               {
				            	            	            	            	   type:"ButtonComponent",
				            	            	            	            	   command:"salir",
				            	            	            	            	   label:"Salir",
				            	            	            	            	   className:"btn-primary"
				            	            	            	               }
				            	            	            	               ]
				            	            	               }
				            	            	               ],layout:[[{span:4}],[{span:4}],[{span:4},{span:4},{span:4}],[{span:4}],[{span:12}]]
				            	             
											 
											 }

				            	             ],
				            	             layout:[[{span:12}],[{span:12}],[{span:12}]]
				             }            
				             ]

			}
	};
	
};