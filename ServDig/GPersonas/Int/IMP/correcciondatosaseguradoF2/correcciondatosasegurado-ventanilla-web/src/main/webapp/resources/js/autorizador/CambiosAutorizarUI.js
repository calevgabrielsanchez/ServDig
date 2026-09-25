function CambiosAutorizarUI(module) {
  this.module = module;
  this.init();
  this.TAMANIO_TEXTAREA = "2em";
  this.TAMANIO_TEXTAREA_CADENA = 20;
}

CambiosAutorizarUI.prototype.tamanioGrid = function () {
	for(var i = 2;i< 5;i++){
		if($("#" + this.module.render.replaceAll(this.panel.components[i].field, ".", "_")).val().length<this.TAMANIO_TEXTAREA_CADENA){
			$("#" + this.module.render.replaceAll(this.panel.components[i].field, ".", "_")).css("height", this.TAMANIO_TEXTAREA);
		}else{
			$("#" + this.module.render.replaceAll(this.panel.components[i].field, ".", "_")).css("word-wrap", "break-word");
		}
	}
	if($("#" + this.module.render.replaceAll(this.panel.components[12].field, ".", "_")).val() === ""){
		$("#" + this.module.render.replaceAll(this.panel.components[12].field, ".", "_")).parent().parent().parent().parent().parent().remove();
	}
};

CambiosAutorizarUI.prototype.init = function () {
	this.panel={
	          type: "FormPanelComponent",
	          id: "panelCorreccionDatos",
	          name: "correccionDatos",
	          model: "detalle",
	          postFetch: "tamanioGrid",
	          label: "",
	          level: 3,
	          components: [
				{type:"LabelComponent", label:"Informaci&#243n de la Solicitud", name:"infoSolicitud", className:"h5"},
				{type:"linea"},			
	            {type: "TextFieldComponent", field: "informacionRENAPO.folio", label: "Folio", disabled: true, labelStyle:true},
				{type: "TextFieldComponent", field: "tipotramitesolicitud", label: "Tipo de tramite de la solicitud", disabled: true, labelStyle:true},
				{type:"linea"},	
				{
	            	type:"PanelCheckBoxComponent",
	            	id:"DatosModificacion",
	            	 model:"detalle",
	            	components: [
	            	    {
							type:"PanelComponent",
	            	    id:"panelDatosRenapo1",            	   
	            	    components:[
						{type: "textlater", field: "informacionRENAPO.curp", label: "CURP", disabled: true},
						{type: "textlater", field: "informacionRENAPO.nombre", label: "Nombre", disabled: true},
						{type: "textlater", field: "informacionRENAPO.apellidoPaterno", label: "Primer apellido", disabled: true},
						{type: "textlater", field: "informacionRENAPO.apellidoMaterno", label: "Segundo apellido", disabled: true}
						], 
							layout: [[{span:12}],[{span:12}],[{span:12}],[{span:12}]]
						},
						{
							type:"PanelComponent",
	            	    id:"panelDatosRenapo2",            	   
	            	    components:[
						{type: "textlater", field: "informacionRENAPO.lugarNacimiento", label: "Lugar de nacimiento", disabled: true},
						{type: "textlater", field: "informacionRENAPO.sexo", label: "Sexo", disabled: true},
						{type: "textlater", field: "informacionRENAPO.nacionalidad", label: "Nacionalidad", disabled: true},
						{type: "textlater", field: "informacionRENAPO.fechaNacimiento", label: "Fecha de nacimiento", disabled: true}
						], 
							layout: [[{span:12}],[{span:12}],[{span:12}],[{span:12}],[{span:12}]]
						},
						{
							type:"PanelComponent",
	            	    id:"panelDatosRenapo3",            	   
	            	    components:[
						{type: "textArealater", field: "informacionRENAPO.datosDocumentoProbatorio", label: "Datos del documento probatorio", disabled: true,labelStyle:true, rows: 8},            
						{type:"textArealater", field:"informacionRENAPO.curpsHistoricas",label:"CURPS Hist\u00F3ricas",disabled:true,labelStyle:true,rows:3},
						
						], 
							layout: [[{span:12}],[{span:12}]]
						}
						
						],
						layout: [[{span:12},{span:8},{span:8}]]
	            	    },
						{type:"EspacioComponent"},
	//-------------------------------------------------------Tabcomponent	
						
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
	            	                    	              {type: "TextoenlineaComponent", fielduno: "informacionNSScertificador_NSS", fielddos: "informacionNSScertificador_NSS", labelNSS: "NSS",labelDETALLE: "DETALLE", disabled: true},
	            	                    	              {type:"linea"},		
	            	                    	              {type: "tablaResumenComponentAsegurado"},
	            	                    	              ],
	            	                    	              layout: [[{span: 12}],[{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}]]
	            	                      	},
	            	                      	{
	              	                    	  type:"PanelComponent", 
	              	                    	  id:"asociadosalasegurado",
	              	                    	  components:[
	              	                    	              {type:"LabelComponent", label:"Informaci&#243n de los n&#250meros de seguridad social que ser&aacuten cancelados por duplicidad.", name:"infoSolicitud", className:"h5"},
	            	                    	              {type:"linea"},		
	            	                    	              {type: "TextoenlineaComponent", fielduno: "informacionNSSAsociado_NSS", fielddos: "informacionNSSAsociado_NSS", labelNSS: "NSS",labelDETALLE: "DETALLE", disabled: true},
	            	                    	              {type:"linea"},
	            	                    	              {type:"tablaResumenComponentAsociadoAsegurado"},
//														  {type: "TextFieldComponent", field: "informacionCIZ1.fechaNacimiento", label: "Fecha de nacimiento", disabled: false},
	              	                    	              
	              	                    	              ],
	              	                    	              layout: [[{span: 12}],[{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}]]
	              	                      	},
	              	                      {
	              	                    	  type:"PanelComponent", 
	              	                    	  id:"nocorrespondealasegurado",
	              	                    	  components:[
	              	                    	              {type:"LabelComponent", label:"Informaci&#243n de los n&#250meros de seguridad social que no pertenecen al asegurado. ", name:"infoSolicitud", className:"h5"},
	            	                    	              {type:"linea"},		
	            	                    	              {type: "TextoenlineaComponent", fielduno: "informacionNSSAsociadoOtrapersona_NSS",fielddos: "informacionNSSAsociadoOtrapersona_NSS", labelNSS: "NSS",labelDETALLE: "DETALLE", disabled: true},
	            	                    	              {type:"linea"},		
//	              	                    	              {type: "TextFieldComponent", field: "informacionCIZ2.sexo", label: "Sexo",disabled: false},
	              	                    	              
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
				                  type: "ButtonComponent",
				                  id: "btnRegresarCorreccion",
				                  label: "Regresar",
				                  command: "regresarCorreccion",
				                  className: "btn-default"
				                },
				                {
				                  type: "ButtonComponent",
				                  label: "Siguiente",
				                  command: "confirmar",
				                  className: "btn-primary"
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