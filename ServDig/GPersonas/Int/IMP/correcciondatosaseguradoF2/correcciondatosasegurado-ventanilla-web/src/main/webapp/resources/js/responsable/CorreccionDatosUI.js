function CorreccionDatosUI(module) {
  this.module = module;
  this.init();
}

CorreccionDatosUI.prototype.init = function () {
  this.metadata = {
    ui: {
      components: [
        {
          type: "FormPanelComponent",
          id: "panelCorreccionDatos",
          name: "correccionDatos",
          model: "detalle",
          postFetch: "postFetchCorreccionDatosUI",
          label: "Correcci\u00F3n de datos",
          level: 3,
          entity: "TipoRegularizacionNSS",
          components: [
            {
            	type:"PanelCheckBoxComponent",
            	id:"DatosModificacion",
            	 model:"detalle",

            	components: [
            	    {type:"PanelComponent",
            	    id:"panelDatosRenapoo",
            	   
            	    components:[
            	            {type:"EspacioComponent", numero:"1"},
            	            {type:"LabelComponent", label:"Informaci&oacuten de RENAPO", name:"infoRENAPO", className:"h5"},
            	            {type: "linea", margen: "-10"},
            	            {type: "TextFieldComponent", field: "informacionRENAPO.curp", label: "CURP", disabled: true},
                            {type: "TextFieldComponent", field: "informacionRENAPO.apellidoPaterno", label: "Primer apellido", disabled: true},
                            {type: "TextFieldComponent", field: "informacionRENAPO.apellidoMaterno", label: "Segundo apellido", disabled: true},
                            {type: "TextFieldComponent", field: "informacionRENAPO.nombre", label: "Nombre(s)", disabled: true},
                            {type: "TextFieldComponent", field: "informacionRENAPO.sexo", label: "Sexo", disabled: true},
                            {type: "TextFieldComponent", field: "informacionRENAPO.fechaNacimiento", label: "Fecha de nacimiento", disabled: true},
                            {type: "TextFieldComponent", field: "informacionRENAPO.lugarNacimiento", label: "Lugar de nacimiento", disabled: true},
                            {type: "TextFieldComponent", field: "informacionRENAPO.nacionalidad", label: "Nacionalidad", disabled: true},
                            {type: "TextAreaFieldComponent", field: "informacionRENAPO.datosDocumentoProbatorio", label: "Datos del documento probatorio", disabled: true, rows: 8}
            	    	       ],
            	    	       layout: [[{span: 12}],[{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}]]
            	    },

            	    	{
            	    	 type:"PanelTabComponent",
            	          id:"panelTabsAsegurado",
            	          //class: definir clase
            	          tabs: ["canase", "ciz1" , "ciz2", "ciz3", "historico", "bdtu"],
            	          labels: ["CANASE", "CIZ1", "CIZ2","CIZ3", "HISTORICO", "BDTU"],
            	          components:[
            	                      {
            	                    	  type:"PanelComponent", 
            	                    	  id:"canase",
            	                    	  model:"detalle",
            	                    	  components:[
            	                    	              {type:"LabelComponent", label:"Informaci&oacuten en el  IMSS", name:"infoRENAPO", className:"h5"},
            	                    	              {type: "linea", margen: "-10"},
            	                    	              {type: "TextFieldComponent", field: "informacionCANASE.curp", label: "CURP", disabled: false},
            	                    	              {type: "TextFieldComponent", field: "informacionCANASE.apellidoPaterno", label: "Primer apellido", disabled: false},
            	                    	              {type: "TextFieldComponent", field: "informacionCANASE.apellidoMaterno", label: "Segundo apellido", disabled: false},
            	                    	              {type: "TextFieldComponent", field: "informacionCANASE.nombre", label: "Nombre(s)", disabled: false},
            	                    	              {type: "TextFieldComponent", field: "informacionCANASE.sexo", label: "Sexo",disabled: false},
            	                    	              {type: "TextFieldComponent", field: "informacionCANASE.fechaNacimiento", label: "Fecha de nacimiento", disabled: false},
            	                    	              {type: "TextFieldComponent", field: "informacionCANASE.lugarNacimiento", label: "Lugar de nacimiento", disabled: false},
            	                    	              {type: "TextFieldComponent", field: "informacionCANASE.nacionalidad", label: "Nacionalidad", disabled: false},
            	                    	              {type: "TextAreaFieldComponent", field: "informacionCANASE.datosDocumentoProbatorio", label: "Datos del documento probatorio", disabled: false, rows: 8}
            	                    	              ],
            	                    	              layout: [[{span: 12}],[{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}]]
            	                      	},
            	                      	{
              	                    	  type:"PanelComponent", 
              	                    	  id:"ciz1",
              	                    	  components:[
              	                    	              {type:"LabelComponent", label:"Informaci&oacuten en el  IMSS", name:"infoRENAPO", className:"h5"},
              	                    	              {type: "linea", margen: "-10"},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ1.curp", label: "CURP", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ1.apellidoPaterno", label: "Primer apellido", disabled: false },
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ1.apellidoMaterno", label: "Segundo apellido", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ1.nombre", label: "Nombre(s)", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ1.sexo", label: "Sexo",disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ1.fechaNacimiento", label: "Fecha de nacimiento", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ1.lugarNacimiento", label: "Lugar de nacimiento", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ1.nacionalidad", label: "Nacionalidad", disabled: false},
              	                    	              {type: "TextAreaFieldComponent", field: "informacionCIZ1.datosDocumentoProbatorio", label: "Datos del documento probatorio", disabled: false, rows: 8}
              	                    	              ],
              	                    	              layout: [[{span: 12}],[{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}],[{span: 12}]]
              	                      	},
              	                      {
              	                    	  type:"PanelComponent", 
              	                    	  id:"ciz2",
              	                    	  components:[
              	                    	              {type:"LabelComponent", label:"Informaci&oacuten en el  IMSS", name:"infoRENAPO", className:"h5"},
              	                    	              {type: "linea", margen: "-10"},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ2.curp", label: "CURP", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ2.apellidoPaterno", label: "Primer apellido", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ2.apellidoMaterno", label: "Segundo apellido", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ2.nombre", label: "Nombre(s)", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ2.sexo", label: "Sexo",disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ2.fechaNacimiento", label: "Fecha de nacimiento", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ2.lugarNacimiento", label: "Lugar de nacimiento", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ2.nacionalidad", label: "Nacionalidad", disabled: false},
              	                    	              {type: "TextAreaFieldComponent", field: "informacionCIZ2.datosDocumentoProbatorio", label: "Datos del documento probatorio", disabled: false, rows: 8}
              	                    	              ],
              	                    	              layout: [[{span: 12}],[{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}]]
              	                      	},
              	                      {
              	                    	  type:"PanelComponent", 
              	                    	  id:"ciz3",
              	                    	  components:[
              	                    	              {type:"LabelComponent", label:"Informaci&oacuten en el  IMSS", name:"infoIMSS", className:"h5"},
              	                    	              {type: "linea", margen: "-10"},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ3.curp", label: "CURP", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ3.apellidoPaterno", label: "Primer apellido", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ3.apellidoMaterno", label: "Segundo apellido", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ3.nombre", label: "Nombre(s)", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ3.sexo", label: "Sexo",disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ3.fechaNacimiento", label: "Fecha de nacimiento", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ3.lugarNacimiento", label: "Lugar de nacimiento", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ3.nacionalidad", label: "Nacionalidad", disabled: false},
              	                    	              {type: "TextAreaFieldComponent", field: "informacionCIZ3.datosDocumentoProbatorio", label: "Datos del documento probatorio", disabled: false, rows: 8}
              	                    	              ],
              	                    	              layout: [[{span: 12}],[{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}],[{span: 12}]]
              	                      	},
              	                      {
              	                    	  type:"PanelComponent", 
              	                    	  id:"historico",
              	                    	  components:[
              	                    	              {type:"LabelComponent", label:"Informaci&oacuten en el  IMSS", name:"infoIMSS", className:"h5"},
              	                    	              {type: "linea", margen: "-10"},
              	                    	              {type: "TextFieldComponent", field: "informacionHISTORICO.curp", label: "CURP", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionHISTORICO.apellidoPaterno", label: "Primer apellido", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionHISTORICO.apellidoMaterno", label: "Segundo apellido", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionHISTORICO.nombre", label: "Nombre(s)", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionHISTORICO.sexo", label: "Sexo",disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionHISTORICO.fechaNacimiento", label: "Fecha de nacimiento", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionHISTORICO.lugarNacimiento", label: "Lugar de nacimiento", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionHISTORICO.nacionalidad", label: "Nacionalidad", disabled: false},
              	                    	              {type: "TextAreaFieldComponent", field: "informacionHISTORICO.datosDocumentoProbatorio", label: "Datos del documento probatorio", disabled: false, rows: 8}
              	                    	              ],
              	                    	              layout: [[{span: 12}],[{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}]]
              	                      	},
              	                      {
              	                    	  type:"PanelComponent", 
              	                    	  id:"bdtu",
              	                    	  components:[
              	                    	              {type:"LabelComponent", label:"Informaci&oacuten en el  IMSS", name:"infoRENAPO", className:"h5"},
              	                    	              {type: "linea", margen: "-10"},
              	                    	              {type: "TextFieldComponent", field: "informacionBDTU.curp", label: "CURP", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionBDTU.apellidoPaterno", label: "Primer apellido", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionBDTU.apellidoMaterno", label: "Segundo apellido", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionBDTU.nombre", label: "Nombre(s)", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionBDTU.sexo", label: "Sexo",disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionBDTU.fechaNacimiento", label: "Fecha de nacimiento", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionBDTU.lugarNacimiento", label: "Lugar de nacimiento", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionBDTU.nacionalidad", label: "Nacionalidad", disabled: false},
              	                    	              {type: "TextAreaFieldComponent", field: "informacionBDTU.datosDocumentoProbatorio", label: "Datos del documento probatorio", disabled: false, rows: 8}
              	                    	              ],
              	                    	              layout: [[{span: 12}],[{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}],[{sapn: 12}]]
              	                      	}            	                      	
            	                      ],layout:[[{span:12}],[{span:12}],[{span:12}],[{span:12}],[{span:12}],[{span:12}],[{span:12}],[{span:12}]]
            	    	
            	    		},
                	    {
                            type: "PanelComponent",
                            id: "panelcheckbox",
                            components: [
                                         {type:"LabelComponent", label:"<center><h5>N&uacutemero de Seguro Social<br> Registrado en solicitud<h4/></center>", name:"infonavit", className:"h5"},
                                         {type:"LabelComponent", label:"<center><label id=\"numeroNSS\"></label></center>", name:"infonavit", className:"h5"},
                                         {type:"LabelComponent", label:"<center><h5>Localizado por CURP,<br> incluido por sistema</h5></center>", name:"infonavit", className:"h5"},
                                         {type:"linea"},
                                         {type:"CheckBoxComponent", id:"slide1" , field:"motivoAclaracion.obtenerCredito",label:"<span style='font-weight:normal'>Certificador</span>"},
                                         {type:"CheckBoxComponent", id:"slide2" , field:"motivoAclaracion.obtenerCredito",label:"<span style='font-weight:normal'>Asociado al Certificador</span>"},
                                         {type:"CheckBoxComponent", id:"slide3" , field:"motivoAclaracion.obtenerCredito",label:"<span style='font-weight:normal'>Corresponde a otra<br> persona</span>"},
                                         {type:"CheckBoxComponent", id:"slide4" , field:"motivoAclaracion.obtenerCredito",label:"<span style='font-weight:normal'>No existe en CANASE</span>"},
                                         {type:"linea"},
                                         {type:"LabelComponent", label:"<center><h5>Tipo de Regularizaci&oacuten</h4></center>", name:"infonavit", className:"h5"},
                                         {type:"CheckBoxComponent", id:"slide5" , field:"motivoAclaracion.obtenerCredito",label:"<span style='font-weight:normal'>Cancelado por<br> duplicidad</span>"},
                                         {type:"CheckBoxComponent", id:"slide6" , field:"motivoAclaracion.obtenerCredito",label:"<span style='font-weight:normal'>Corresponde a un<br> hom&oacutenimo</span>"},
                                         {type:"CheckBoxComponent", id:"slide7" , field:"motivoAclaracion.obtenerCredito",label:"<span style='font-weight:normal'>No existe en CANASE</span>"},
                                         {type:"CheckBoxComponent", id:"slide8" , field:"motivoAclaracion.obtenerCredito",label:"<span style='font-weight:normal'>Corresponde a otro<br> asegurado</span>"},
                                         {type:"CheckBoxComponent", id:"slide9" , field:"motivoAclaracion.obtenerCredito",label:"<span style='font-weight:normal'>Correcci&oacuten de nombre</span>"},
                                         {type:"CheckBoxComponent", id:"slide10" , field:"motivoAclaracion.obtenerCredito",label:"<span style='font-weight:normal'>Correcci&oacuten de datos<br> estadisticos</span>", rows: 12},
                            ],
                            layout: [[{span: 12}],[{span: 12}],[{span: 12}],[{span: 12}],[{span: 12}],[{span: 12}],[{span: 12}],[{span: 12}],[{span: 12}],[{span: 12}],[{span: 12}],[{span: 12}],[{span: 12}],[{span: 12}],[{span: 12}],[{span: 12}]]
                	    },            	      
            	    ]
            },
            {
    	    	type: "SliderComponent",
                id: "slaider",
                size : "getSizeNss",
                data: "fetNSS",
                components: [
                             {type:"cargarValores", 
                            	 data:"fetNSS ",
                            	 size: "getSizeNss ",
                            	 id:"sliderNSS"                        
                             }                                        
                             
                             ]
                             
	      },		
           

        
            {
            	type : "CardLayoutComponent",
				id : "responsableCorreccionButtonsCardLayout",
				components : [
		              {
		            	  type: "ButtonGroupComponent",              
		                  components: [      
		                       {
		                       	type: "ButtonComponent",
		                       	id: "btnBandeja",
		                       	label: "Bandeja",
		                       	command: "bandeja",
		                       	className: "btn-default"
		                       },        
                               {
                                 type: "ButtonComponent",
                                 id: "btnRegresarCorreccion",
                                 label: "Regresar",
                                 command: "regresarInicioCorreccion",
                                 className: "btn-default"
                               },
                               {
                                 type: "ButtonComponent",
                                 label: "Siguiente",
                                 command: "siguienteCorreccion",
                                 className: "btn-primary",
                                 href:"top",
                                 id: "btnSiguiente"
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
								  command: "regresarInicioCorreccion",
								  className: "btn-default"
							    }
							]
		              },
		        
		              
				],
		          layout: [{span: 12}]
            },
            {type:"LabelComponent", label:"<label style='color:red;'>*Informaci&oacuten direfente respecto a RENAPO o a los datos b&aacutesicos del Asegurado.</label>", name:"infoRENAPO", className:"h5"},
            {
              type: "ModalComponent",
              title: "Confirmar operaci\u00F3n",
              id: "confirmarModal",
              size: "modal-lg",
              body: {
                type: "PanelComponent",
                components: [
                  {type: "ResumenCorreccionComponent", id:"resumenCorreccion",model:"resumenCorreccion"}
                ],
                layout: [[{span: 12}]]
              },
              footer: {
                type: "PanelComponent",
                components: [
                  {type: "LabelComponent"}, 
                  {type: "ButtonComponent", className: "btn-primary", label: "Aceptar", command: "confirmar"}
                ],
                layout: [[{span: 8}, {span: 4}]]
              }
            }
          ],
          layout: [ [{span: 12}], [{span: 12}, {span: 12}], [{span: 12}],[{span: 12}],[{span: 12}]]
        },
        
      ],
      layout: [[{span: 12}]]
    }
  };
};
