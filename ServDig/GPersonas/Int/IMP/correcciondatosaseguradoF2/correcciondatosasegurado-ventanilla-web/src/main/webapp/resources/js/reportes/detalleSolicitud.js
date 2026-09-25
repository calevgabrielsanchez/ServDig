function detalleSolicitud(module){
	this.module = module;
	this.init();

	
}

detalleSolicitud.prototype.init = function(){
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
				            	            	 model : "gridTramitesDetalle",
				            	            	 name : "gridTramitesDetalle",
												 label : "<h4>Seguimiento de la solicitud de tr&aacutemite<h4>",
				            	            	 components:  [
																{
																	type : "textlater",
																	label : "Datos del solicitante"
																},
																{
																	type : "TextoenlineaComponent",
																	labelNSS : "CURP",
																	fielduno : "curpReporte",
																	labelDETALLE : "Nombre",
																	fielddos : "nombreReporte"
																},
				            	            	               {
				            	            	            	   type:"GridComponent",
				            	            	            	   id:"gridTramitesDetalle",
				            	            	            	   model:"gridTramitesDetalle",              
				            	            	            	   //data:"fetchTramites", 
				            	            	            	   desfasado:true,
				            	            	            	   fontSize: "73%",
				            	            	            	   columns:[ 
				            	            	            	            { label:"Folio",name:"folio"},
																			{ label:"Fecha de solicitud",name:"fechaSolicitud"},
																			{ label:"NSS involucrados",name:"nssInvolucrados"},
				            	            	            	            { label:"Subdelegaci\u00f3n",name:"subdelegacion"},
				            	            	            	            { label:"Estado",name:"estatus"}
				            	            	            	            ]
				            	            	               },
															   {type: "LabelComponent"},
															   {
				            	            	            	            	   type:"ButtonComponent",
				            	            	            	            	   command:"regresarGrid",
				            	            	            	            	   label:"Regresar",
				            	            	            	            	   className:"btn-default"
				            	            	            	               },
				            	            	               ],layout:[[{span:12}],[[{span:12}]],[{span:12}]]
				            	             },
											 {
				            	            	            	   type:"ButtonGroupComponent",
				            	            	            	   components:[
											 {
				            	            	            	            	   type:"ButtonComponent",
				            	            	            	            	   command:"regresarGrid",
				            	            	            	            	   label:"Salir",
				            	            	            	            	   className:"btn-default"
				            	            	            	               }, ]
				            	            	               },

				            	             ],
				            	             layout:[[{span:12}],[{span:12}],[{span:12}],[{span:12}]]
				             }            
				             ]

			}
	};
	
};