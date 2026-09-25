function ConsultaDetalleAtencionResponsableUI(module) {
	this.module = module;
	this.init();
	this.TAMANIO_TEXTAREA = "2em";
	this.TAMANIO_TEXTAREA_CADENA = 20;
}

ConsultaDetalleAtencionResponsableUI.prototype.tamanioGrid = function () {
	for(var i = 2;i< 5;i++){
		if($("#" + this.module.render.replaceAll(this.panel.components[i].field, ".", "_")).val().length<this.TAMANIO_TEXTAREA_CADENA){
			$("#" + this.module.render.replaceAll(this.panel.components[i].field, ".", "_")).css("height", this.TAMANIO_TEXTAREA);
		}else{
			$("#" + this.module.render.replaceAll(this.panel.components[i].field, ".", "_")).css("word-wrap", "break-word");
		}
	}
//	if($("#" + this.module.render.replaceAll(this.panel.components[9].field, ".", "_")).val() === ""){
//		$("#" + this.module.render.replaceAll(this.panel.components[9].field, ".", "_")).parent().parent().parent().parent().parent().remove();
//	}
};

ConsultaDetalleAtencionResponsableUI.prototype.init = function() {
	this.panel={
			type: "FormPanelComponent",
			id: "panelCorreccionDatos",
			name: "correccionDatos",
			model: "detalle",
			postFetch: "tamanioGridConsulta",
			label: "",
			level: 3,
			components: [
			             {type:"LabelComponent", label:"Informaci&#243n de la Solicitud", name:"infoSolicitud", className:"h5"},
			             {type:"linea"},	
						 {type: "TextFieldComponent", field: "informacionRENAPO.folio", label: "Folio", disabled: true, labelStyle:true},
			             {type: "TextFieldComponent", field: "tipotramitesolicitud", label: "Tipo de tramite de la solicitud", disabled: true, labelStyle:true},						 
			            
			             {type:"linea"},
			             {type:"LabelComponent", label:"Cuenta Individual", name:"infoSolicitud", className:"h5"},
			             {type:"linea"},
			             
			           
			             {type:"EspacioComponent"},
//			             -------------------------------------------------------Tabcomponent	

			             {
			            	 type:"PanelTabComponent",
			            	 id:"panelTabsAsegurado",
			            	 //class: definir clase
			            	 tabs: ["certificador", "asociadosalasegurado" , "nocorrespondealasegurado"],
			            	 labels: ["CERTIFICADOR", "ASOCIADO AL ASEGURADO", "NO CORRESPONDE AL ASEGURADO"],
			            	 components:[
			            	             {
			            	            	 type:"PanelComponent", 	
			            	            	 id:"certificador",
			            	            	 model:"detalle",
			            	            	 components:[
			            	            	             {type:"LabelComponent", label:"Informaci&#243n de los n&#250meros de seguridad social con el que se certificara el tr&#225mite. ", name:"infoSolicitud", className:"h5"},
			            	            	             {type:"linea"},		
			            	            	             {type: "TextoenlineaComponent", field: "informacionRENAPO.curp", labelNSS: "NSS",labelDETALLE: "DETALLE", disabled: true},
			            	            	             {type:"linea"},
														 {type: "tablaDetalleCuentaIndComponent"},
			            	            	             {

			            	            	            	   type:"GridComponent",
			            	            	            	   id:"gridPreviaDetalle",
			            	            	            	   model:"gridTramites",              
			            	            	            	   data:"fetchTramites", 
			            	            	            	   desfasado:true,
			            	            	            	   //breakWord:true,
			            	            	            	   columns:[ 
			            	            	            	            { label:"Origen de informaci�n",name:"origen"},
			            	            	            	            { label:"Dato",name:"curp"},
			            	            	            	            { label:"Informacion previa en el IMSS",name:"nssInvolucrados"},
			            	            	            	            { label:"Informacion de RENAPO actualizada en el IMSS",name:"vencida"},
			            	            	            	            { label:"Estatus de cambio",name:"delegacion"},
			            	            	            	            { label:"Fecha de proceso",name:"subdelegacion",href:"estatus"}
			            	            	            	            ]
			            	            	               
			            	            	             },
			            	            	             ],
			            	            	             layout: [[{span: 12}],[{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}]]
			            	             },
			            	             {
			            	            	 type:"PanelComponent", 
			            	            	 id:"asociadosalasegurado",
			            	            	 components:[
			            	            	             {type:"LabelComponent", label:"Informaci&#243n de los n&#250meros de seguridad social que ser&aacuten cancelados por duplicidad.", name:"infoSolicitud", className:"h5"},
			            	            	             {type:"linea"},		
			            	            	             {type: "TextoenlineaComponent", field: "informacionRENAPO.curp", labelNSS: "NSS",labelDETALLE: "DETALLE", disabled: true},
			            	            	             {type:"linea"},		
			            	            	             {type: "TextFieldComponent", field: "informacionCIZ1.fechaNacimiento", label: "Fecha de nacimiento", disabled: false},

			            	            	             ],
			            	            	             layout: [[{span: 12}],[{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}]]
			            	             },
			            	             {
			            	            	 type:"PanelComponent", 
			            	            	 id:"nocorrespondealasegurado",
			            	            	 components:[
			            	            	             {type:"LabelComponent", label:"Informaci&#243n de los n&#250meros de seguridad social que no pertenecen al asegurado. ", name:"infoSolicitud", className:"h5"},
			            	            	             {type:"linea"},		
			            	            	             {type: "TextoenlineaComponent", field: "informacionRENAPO.curp", labelNSS: "NSS",labelDETALLE: "DETALLE", disabled: true},
			            	            	             {type:"linea"},		
//			            	            	             {type: "TextFieldComponent", field: "informacionCIZ2.sexo", label: "Sexo",disabled: false},

			            	            	             ],
			            	            	             layout: [[{span: 12}],[{span: 12}], [{span: 12}], [{span: 12}]]
			            	             },

			            	             ],layout:[[{span:12}],[{span:12}],[{span:12}],[{span:12}],[{span:12}],[{span:12}],[{span:12}],[{span:12}]]

			             },
			             {
			            	 type : "CardLayoutComponent",
			            	 id : "responsableConfirmarButtonsCardLayout",
			            	 components : [
			            	               {
			            	            	   type: "ButtonGroupComponent",              
			            	            	   components: [
														   {
																type : "ButtonComponent",
																command : "bandeja",
																label : "Salir",
																className : "btn-danger"
															} ,
			            	            	                {
			            	            	                	type: "ButtonComponent",
			            	            	                	id: "btnRegresarCorreccion",
			            	            	                	label: "Regresar",
			            	            	                	command: "consultaSolicitudController.regresarConsultaPrevia",
			            	            	                	className: "btn-default"
			            	            	                }
			            	            	                ]
			            	               }, 
			            	               {
			            	            	   type: "ButtonGroupComponent",
			            	            	   components: [                
			            	            	                {
			            	            	                	type: "ButtonComponent",
			            	            	                	id: "btnRegresarCorreccion",
			            	            	                	label: "Regresar",
			            	            	                	command: "regresarCorreccion",
			            	            	                	className: "btn-default"
			            	            	                }
			            	            	                ]
			            	               }
			            	               ]
			             }
			             ],
			             layout: [[{span:12}],[{span:12}],[{span:5},{span:5}],[{span:12}],[{span:12},{span:12},{span:12}],[{span:12}],[{span:12}],[{span:12}],[{span:12}],[{span:12}],[{span:12}]]
	};

};
