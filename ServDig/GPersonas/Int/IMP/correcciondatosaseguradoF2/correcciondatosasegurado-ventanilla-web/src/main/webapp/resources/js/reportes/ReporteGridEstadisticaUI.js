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
											  label:"<h4>Generaci\u00f3n de Reportes</h4>",
				            	            	 collapsed: false,
				            	            	 level: 5,
				            	            	 //id: "generaReporte",
				            	            	 id: "variablesFilter",
				            	            	 model : "gridReportes",
				            	            	 name : "fetchGeneracionReporte",
				            	            	 //name : "variablesFilter",
				            	            	 components:  [
															   {type: "SelectFieldComponent", label: "Variables", id: "variableSelectField",onchange:"selectVariable", field: "variable",
																tamanoAnchoSelect:12, data: "fetchVariableCombo"},
																{type: "linea", margen: "10"},
															   {type: "LabelComponent", label: "Resultado", name:"resultado", className:"h3"},
															   {type: "TextFieldComponent", field: "delegacionReporteGrid", label:"Delegaci\u00f3n", disabled: true, labelStyle:true},
															   {type: "TextFieldComponent", field: "subdelegacionReporteGrid", label:"Subdelegaci\u00f3n", disabled: true, labelStyle:true},
															   {type: "TextFieldComponent", field: "variableSeleccionadaReporte", label:"Variable", disabled: true, labelStyle:true},
															  
															     {type: "LabelComponent", label: "Origen", name:"origen", className:"h5"},
															  {
				            	            	            	   type:"GridComponent",
																   title:"",
				            	            	            	   id:"gridOrigen",
				            	            	            	   model:"gridOrigen",              
				            	            	            	   //data:"fetchTramites", 
				            	            	            	   fontSize: "73%",
				            	            	            	   columns:[ 
				            	            	            	            {label:"INTERNET",name:"numeroInternet"},
				            	            	            	            {label:"VENTANILLA", name:"numeroVentanilla"}
				            	            	            	            ]
				            	            	               },
															   
									            	            	               
															   {
				            	            	            	   type:"GridComponent",
																   title:"",
				            	            	            	   id:"gridReportes",
				            	            	            	   model:"gridReportes",              
				            	            	            	   //data:"fetchTramites", 
				            	            	            	   fontSize: "73%",
				            	            	            	   columns:[ 
				            	            	            	            {label:"&nbsp;",name:"descripcion"},
				            	            	            	            {label:"Total registros", name:"cantidad"}
				            	            	            	            ]
				            	            	               },
				            	            	               {type: "LabelComponent", field: "estadisticaReporte", label:"", disabled: true},
															   {type: "TextFieldComponent", field: "estadisticaReporteGrid", label:"Estadistica", disabled: true, labelStyle:true},
															   {type: "LabelComponent", field: "estadisticaReporte", label:"", disabled: true},
				            	            	               {
				            	            	            	   type:"ButtonGroupComponent",
				            	            	            	   components:[
				            	            	            	               {
				            	            	            	            	   type:"ButtonComponent",
				            	            	            	            	   command:"regresarGrid",
				            	            	            	            	   label:"Regresar",
				            	            	            	            	   className:"btn-default"
				            	            	            	               },
				            	            	            	               {
				            	            	            	            	   type:"ButtonComponent",
				            	            	            	            	   id:'buttonObtenerReporte',
				            	            	            	            	   command:"obtenerReporte",
				            	            	            	            	   label:"Obtener Reporte",
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
				            	            	               ],layout:[[{span:4}],[{span:12}],
															   [{span:4}],
															   [{span:4}],
															   [{span:6},{span:6}], 
															   [{span:1},{span:5},{span:6}],
															   [{span:6},{span:2},{span:4}],
															   [{span:12}]
															   ]
				            	             
											 
											 }

				            	             ],
				            	             layout:[[{span:12}],[{span:12}],[{span:12}]]
				             }            
				             ]

			}
	};
	
};