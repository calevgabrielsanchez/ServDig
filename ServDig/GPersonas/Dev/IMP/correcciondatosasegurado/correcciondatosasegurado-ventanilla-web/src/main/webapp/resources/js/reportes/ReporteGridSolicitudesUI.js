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
				            	             {
				            	            	 type: "FormPanelComponent",
				            	            	 collapsed: false,
				            	            	 level: 5,
				            	            	 id: "filter",
				            	            	 model : "filtrosReportes",
				            	            	 name : "filter",
				            	            	 components: [                

				            	            	              {type: "SelectFieldComponent", label:"Delegaci\u00f3n",onchange:"cargarSubdelegaciones", id: "delegacionSelectField", field:"delegacion", tamanoAnchoSelect:12, data: "fetchDelegacionCombo"},
				            	            	              {type: "SelectFieldComponent", label:"Subdelegaci\u00f3n",onchange:"cargarResponsables", id: "subdelegacionSelectField", field:"subdelegacion", tamanoAnchoSelect:12, data: "fetchSubdelegacionCombo"},
				            	            	              {type: "SelectFieldComponent", label:"Autoriz\u00f3", id: "autorizoSelectField", field: "autorizo", tamanoAnchoSelect:12, data: "fetchAutorizoCombo"},

				            	            	              {type: "SelectFieldComponent", label:"Responsable",id: "responsableSelectField",field: "responsable", tamanoAnchoSelect:12,data: "fetchResponsableCombo"},
				            	            	              {type: "SelectFieldComponent", label:"Origen", id: "origenSelectField", field: "origen", tamanoAnchoSelect:12, data: "fetchOrigenCombo"},
				            	            	              {type: "TextFieldComponent", field: "folio", label: "Folio", maxlength:"25", tipo:"number"},

				            	            	              {type: "TextFieldComponent", field: "curp", label: "CURP", maxlength:"18"},
				            	            	              {type: "TextFieldComponent", field: "nssInvolucrado", label: "NSS", maxlength:"11", tipo:"number"},
				            	            	              {type: "SelectFieldComponent", label: "Tipo tramite", id: "tipoTramiteSelectField", field: "tipoTramite", tamanoAnchoSelect:12, data: "fetchTipoTramiteCombo"},

				            	            	              {type: "SelectFieldComponent", label: "Estado", id: "estadoSelectField", field: "estado", tamanoAnchoSelect:12, data: "fetchCombo"},
				            	            	              {type: "TextFieldComponent", field: "curpBeneficiario", label: "CURP beneficiario/ Representante legal", maxlength:"18", tipo:"number"},
				            	            	              {type: "CheckBoxFieldComponent", field: "vencida", label: "Vencida",inLineWithLabel:true},

				            	            	              {type:"LabelComponent", label: "Fecha de solicitud ", name:"fechaSoicitud", className:"h5"},
				            	            	              {type:"LabelComponent", label:"Fecha de finalizaci\u00f3n", name:"fechaFinalizacion", className:"h5"},
				            	            	              {type:"LabelComponent", label:"Fecha de actualizaci\u00f3n ", name:"fechaActualizacion", className:"h5"},

				            	            	              {type: "DatePickerFieldComponent", field: "fechaSolicitudDesde", label: "desde"},
				            	            	              {type: "DatePickerFieldComponent", field: "fechaFinalizacionDesde", label: "desde"},
				            	            	              {type: "DatePickerFieldComponent", field: "fechaActualizacionDesde", label: "desde"},

				            	            	              {type: "DatePickerFieldComponent", field: "fechaSolicitudHasta", label: "hasta"},
				            	            	              {type: "DatePickerFieldComponent", field: "fechaFinalizacionHasta", label: "hasta"},
				            	            	              {type: "DatePickerFieldComponent", field: "fechaActualizacionHasta", label: "hasta"},
				            	            	              //{type: "LabelComponent", label: ""},

				            	            	              //,inLineWithLabel:true
				            	            	              // pull-right

				            	            	              {type:"ButtonGroupComponent",
				            	            	            	  components:[
				            	            	            	              {type:"ButtonComponent",command:"clean",label:"Limpiar",className:"btn-default"},
				            	            	            	              {type:"ButtonComponent",command:"filtrosReportes",label:"B\u00fasqueda",className:"btn-default"}

				            	            	            	              ]}

				            	            	              ],
				            	            	              layout: [[{span:4},{span:4},{span:4}],
				            	            	                       [{span:4},{span:4},{span:4}],
				            	            	                       [{span:4},{span:4},{span:4}],
				            	            	                       [{span:4},{span:5},{span:3}],
				            	            	                       [{span:4},{span:4},{span:4}],
				            	            	                       [{span:4},{span:4},{span:4}],
				            	            	                       [{span:4},{span:4},{span:4}],
				            	            	                       [{span:12}]]

				            	             },{type: "FormPanelComponent",
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
				            	            	            	   //breakWord:true,
				            	            	            	   columns:[ 
				            	            	            	            { label:"Folio",name:"folio",href:"selectTramite"},
				            	            	            	            { label:"CURP",name:"fechaSolicitud"},
				            	            	            	            { label:"NSS involucrados",name:"nssInvolucrados"},
				            	            	            	            { label:"Vencida",name:"origen"},
				            	            	            	            { label:"Delegaci\u00f3n",name:"delegacion"},
				            	            	            	            { label:"Subdelegaci\u00f3n",name:"estatus",href:"estatus"},
				            	            	            	            { label:"Autoriz\u00f3",name:"ultimaActualizacion"},
				            	            	            	            { label:"Responsable",name:"tipo"},
				            	            	            	            { label:"Origen",name:"tipo"},
				            	            	            	            { label:"Tipo de tr\u00e1mite",name:"tipo"},
				            	            	            	            { label:"Fecha de solicitud",name:"tipo"},
				            	            	            	            { label:"Fecha de finalizaci\u00f3n",name:"tipo"},
				            	            	            	            { label:"\u00daltima actualizaci\u00f3n",name:"tipo"},
				            	            	            	            { label:"Estado",name:"tipo"}
				            	            	            	            ]
				            	            	               },
				            	            	               {
				            	            	            	   type:"ButtonGroupComponent",
				            	            	            	   components:[
				            	            	            	               {
				            	            	            	            	   type:"ButtonComponent",
				            	            	            	            	   command:"regresarCorreccion",
				            	            	            	            	   label:"regresar",
				            	            	            	            	   className:"btn-primary"
				            	            	            	               },
				            	            	            	               {
				            	            	            	            	   type:"ButtonComponent",
				            	            	            	            	   command:"generaReporte",
				            	            	            	            	   label:"Generar reporte",
				            	            	            	            	   className:"btn-primary"
				            	            	            	               },
				            	            	            	               {
				            	            	            	            	   type:"ButtonComponent",
				            	            	            	            	   command:"exportaExcel",
				            	            	            	            	   label:"Exportar excel",
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
				            	            	               ],layout:[[{span:12}],[{span:12}]]
				            	             }

				            	             ],
				            	             layout:[[{span:12}],[{span:12}],[{span:12}]]
				             }            
				             ]

			}
	};
	
};