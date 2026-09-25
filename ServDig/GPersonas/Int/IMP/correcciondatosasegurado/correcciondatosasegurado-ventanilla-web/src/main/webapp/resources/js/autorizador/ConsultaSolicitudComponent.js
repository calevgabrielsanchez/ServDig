function ConsultaSolicitudComponent( render ){
  this.render = render;  
  this.init();
  this.TAMANIO_TEXTAREA = "2em";
  this.TAMANIO_TEXTAREA_CADENA = 20;
}

ConsultaSolicitudComponent.prototype.draw = function ( component ) {  
  var panel = new FormPanelComponent( this.render );
  this.model = component.model;
  component.components = this.metadata.components;
  return panel.draw(this.metadata);
};
ConsultaSolicitudComponent.prototype.notify = function (metadata, value) {     
    this.render.modelToForm("FormPanelComponent-consultaSolicitud",
    this.render.module.controller.model["consultaSolicitud"]);
};

ConsultaSolicitudComponent.prototype.tamanioGridConsultaSolicitud = function () {
	for(var i = 3;i< 6;i++){
		if($("#" + this.render.replaceAll(this.metadata.components[0].components[i].field, ".", "_")).val().length<this.TAMANIO_TEXTAREA_CADENA){
			$("#" + this.render.replaceAll(this.metadata.components[0].components[i].field, ".", "_")).css("height", this.TAMANIO_TEXTAREA);
		}else{
			$("#" + this.render.replaceAll(this.metadata.components[0].components[i].field, ".", "_")).css("word-wrap", "break-word");
		}
	}
	if($("#" + this.render.replaceAll(this.metadata.components[0].components[8].field, ".", "_")).val() === ""){
		$("#" + this.render.replaceAll(this.metadata.components[0].components[8].field, ".", "_")).parent().parent().parent().parent().parent().remove();
	}
};

ConsultaSolicitudComponent.prototype.init = function () {
  this.metadata = {
    type:"PanelComponent",    
    id:"panelConsultaSolicitud",
    postFetch: "tamanioGridConsultaSolicitud",
    components:[      
      {
    	  type:"PanelTabComponent",
          id:"panelTabsAsegurado",
          tabs: ["renapo", "domicilio" , "aclaracion", "nssDoc", "nssDocVentanilla", "infoAdicional", "historia", "contacto"],
          labels: ["Información de RENAPO", 
                   "Domicilio particular",
                   "Motivo de aclaración",
                   "Números de Seguridad Social involucrados en el trámite y documentos probatorios",
                   "Números de Seguridad Social y Documentos agregados en Ventanilla",
                   "Información y documentación adicional proporcionada por el Asegurado",
                   "Historia laboral",
                   "Datos de contacto y observaciones"],
          components:[  
						{type:"PanelComponent", id:"renapo" , //PANEL 1
						    components:[  
										{type:"TextFieldComponent", field:"informacionRENAPO.curp",label:"CURP",disabled:true, labelStyle:true},
										{type:"TextFieldComponent", field:"informacionRENAPO.fechaNacimiento",label:"Fecha de nacimiento",disabled:true, labelStyle:true},
										{type:"TextFieldComponent", field:"informacionRENAPO.sexo",label:"Sexo",disabled:true, labelStyle:true},
										
										{type:"TextAreaFieldComponent", field:"informacionRENAPO.apellidoPaterno",label:"Primer apellido",disabled:true, labelStyle:true},
										{type:"TextAreaFieldComponent", field:"informacionRENAPO.apellidoMaterno",label:"Segundo apellido",disabled:true, labelStyle:true},
										{type:"TextAreaFieldComponent", field:"informacionRENAPO.nombre",label:"Nombre(s)",disabled:true, labelStyle:true},
										
										{type:"TextFieldComponent", field:"informacionRENAPO.lugarNacimiento",label:"Lugar de nacimiento",disabled:true, labelStyle:true},
										{type:"TextFieldComponent", field:"informacionRENAPO.nacionalidad",label:"Nacionalidad",disabled:true, labelStyle:true},
										
										{type:"TextAreaFieldComponent", field:"informacionRENAPO.curpsHistoricas",label:"CURPS Hist\u00F3ricas",disabled:true,rows:3},
										
										{type:"TextAreaFieldComponent", field:"informacionRENAPO.datosDocumentoProbatorio",label:"Datos del documento probatorio",disabled:true,rows:3},
						      ],
						      layout:[[{span:3},{span:3},{span:3}],[{span:3},{span:3},{span:4}],[{span:3},{span:3}],[{span:12}],[{span:12}]]
						},
						{type:"PanelComponent", id:"domicilio",//PANEL 2
			        	    components:[  
			        	               {type:"TextFieldComponent", field:"domicilioParticular.calle",label:"Calle",disabled:true,labelStyle:true},
						               {type:"TextFieldComponent", field:"domicilioParticular.cp",label:"C\u00F3digo postal",disabled:true,labelStyle:true},                          
						              
						               {type:"TextFieldComponent", field:"domicilioParticular.colonia",label:"Colonia",disabled:true,labelStyle:true},
						               {type:"TextFieldComponent", field:"domicilioParticular.numeroExterior",label:"N\u00FAmero ext",disabled:true,labelStyle:true},
						               {type:"TextFieldComponent", field:"domicilioParticular.numeroInterior",label:"Int",disabled:true,labelStyle:true},                 
						              
						               {type:"TextFieldComponent", field:"domicilioParticular.delegacion",label:"Municipio/Alcald\u00eda",disabled:true,labelStyle:true},
						               {type:"TextFieldComponent", field:"domicilioParticular.entidadFederativa",label:"Entidad federativa",disabled:true,labelStyle:true}
			        	      ],
			        	      layout:[[{span:6},{span:2}],[{span:6},{span:2},{span:2}],[{span:6},{span:6}]]
			          },
			          {type:"PanelComponent", id:"aclaracion",//PANEL 3
			        	   components:[  
			        	               { type:"PanelComponent",
						              id:"panel5",
						              className:"",
						              components:[
										{type:"LabelComponent", label:"IMSS", name:"imss", className:"h5"},
										{type:"CheckBoxFieldComponent", field:"motivoAclaracion.cobroIncapacidad",label:"COBRO DE INCAPACIDAD",disabled:true},
										{type:"CheckBoxFieldComponent", field:"motivoAclaracion.pension",label:"PENSI\u00D3N",disabled:true},
										{type:"CheckBoxFieldComponent", field:"motivoAclaracion.retiroDesempleo",label:"RETIRO POR DESEMPLEO",disabled:true},
										{type:"CheckBoxFieldComponent", field:"motivoAclaracion.registroBeneficiarios",label:"REGISTRO DE BENEFICIARIOS",disabled:true},
										{type:"CheckBoxFieldComponent", field:"motivoAclaracion.adscripcionUMF",label:"ADSCRIPCI\u00D3N A UMF",disabled:true},
										{type:"CheckBoxFieldComponent", field:"motivoAclaracion.cambioUMF",label:"CAMBIO DE UMF",disabled:true},
										{type:"CheckBoxFieldComponent", field:"motivoAclaracion.gastosMatrimonio",label:"GASTOS DE MATRIMONIO",disabled:true},
										{type:"CheckBoxFieldComponent", field:"motivoAclaracion.gastosFuneral",label:"GASTOS DE FUNERAL",disabled:true}
										],
										layout:[[{span:12}],[{span:12}],[{span:12}],[{span:12}],[{span:12}],[{span:12}],[{span:12}],[{span:12}],[{span:12}]]
										},
										{ type:"PanelComponent",                        
										id:"panel6",
										components:[  
										{type:"LabelComponent", label:"INFONAVIT", name:"infonavit", className:"h5"},
										{type:"CheckBoxFieldComponent", field:"motivoAclaracion.obtenerCredito",label:"OBTENER CR\u00C9DITO",disabled:true},
										{type:"CheckBoxFieldComponent", field:"motivoAclaracion.conclusionCredito",label:"CONCLUSI\u00D3N DE CR\u00C9DITO",disabled:true},
										{type:"CheckBoxFieldComponent", field:"motivoAclaracion.prorrogaRestructuraCredito",label:"PR\u00D3RROGA O REESTRUCTURA DE CR\u00C9DITO",disabled:true},
										{type:"CheckBoxFieldComponent", field:"motivoAclaracion.descuentoIndebidoCredito",label:"DESCUENTO INDEBIDO DE CR\u00C9DITO",disabled:true},              
										{type:"TextFieldComponent", field:"motivoAclaracion.numeroCredito", label:"No. DE CR\u00C9DITO QUE LE EST\u00C1N DESCONTANDO", disabled:true, className:"h5"}
										],
										layout:[[{span:12}],[{span:12}],[{span:12}],[{span:12}],[{span:12}],[{span:12}]]
										},
										{ type:"PanelComponent",            
										id:"panel7",
										components:[  
										{type:"LabelComponent", label:"AFORE", name:"afore", className:"h5"},				  
										{type:"CheckBoxFieldComponent", field:"motivoAclaracion.registroAfore",label:"REGISTRO EN AFORE",disabled:true},
										{type:"CheckBoxFieldComponent", field:"motivoAclaracion.aclaracionSaldoSupuestaVivienda",label:"ACLARACI\u00D3N DE SALDO SUBCUENTA VIVIENDA",disabled:true},
						                {
						                  type:"PanelComponent",id:"panel8", 
						                  components:[    
						          {type:"CheckBoxFieldComponent", field:"motivoAclaracion.otro",label:"OTRO",disabled:true},
						                    {type:"TextAreaFieldComponent", field:"motivoAclaracion.otroMotivo",disabled:true, rows:5}
						                  ],
						                  layout:[[{span:12}],[{span:12}]]
						                }                                                    
						              ],
						              layout:[[{span:12}],[{span:12}],[{span:12}],[{span:12}]]
						            }
			        	      ],
			        	      layout:[[{span:4},{span:4},{span:4}]]
			          },
			          {type:"PanelComponent", id:"nssDoc", //PANEL 4
			        	    components:[ {
			        	    	type:"GridComponent",
			                    title:"",
			                    id:"gridDocumentos",
			                    model:"gridDocumentos",                        
			                    data:"fetchDocumentos", 
			                    index:true,
			                    columns:[
			                    	{label:"Documentos Asegurado",name:"tipoDocumento",href:"mostrarDocumento"}
			                    ]
			                  },
			        	      {
			                	type:"CheckBoxFieldComponent", field:"motivoAclaracion.obtenerCredito",label:"Defunción",disabled:true, //cambiar field
			                  },
			                  {//LISTA DE NSS
			                      type:"GridGroupComponent",
			                      id: "gridGroupNss",
			                      size : "getSizeGridNss",
			                      data: "fetchNSS",
			                      components:[
			                         {
			                          type:"GridComponent",
			   	                      title:"",
			   	                      id:"gridNSS",
			   	                      model:"gridNSS",                        
			   	                      columns:[
			   	                        {label:"NSS Involucrados",name:"nss"}
			   	                      ]
			                        	 
			                         }         
			                                  
			                      ]
			                   },
			                   {//LISTA DE DOCUMENTOS NSS
				                      type:"GridGroupComponent",
				                      id: "gridGroupDocumentosNss",
				                      size : "getSizeGridDocumentosNss",
				                      data: "fetchDocumentosNss",
				                      components:[
				                         {
				                          type:"GridComponent",
				   	                      title:"",
				   	                      id:"gridDocumentosnss",
				   	                      model:"gridDocumentos",
				   	                      index:true,
				   	                      columns:[
				   	                        {label:"Documentos probatorios del NSS",name:"tipoDocumento",href:"mostrarDocumento"}
				   	                      ]
				                        	 
				                         }         
				                                  
				                      ]
				                   },
			                  {
			                      type:"LabelComponent"
			                  }
			                 ],
			                 layout:[[{span:12}],[{span:12}],[{span:6},{span:6}]]
			               },
			          
			          {type:"PanelComponent", id:"nssDocVentanilla", //PANEL 5
			        	    components:[ 
										{//LISTA DE DOCUMENTOS NSS ORIGEN
										    type:"GridGroupComponent",
										    id: "gridGroupDocumentosNss",
										    size : "getSizeGridDocumentosNssOrigen",
										    data: "fetchDocumentosNssOrigen",
										    components:[
										       {
											    type:"GridComponent",
											    title:"",
											    id:"gridDocumentosVentanilla",
											    model:"gridDocumentosNssOrigen",                        
											    columns:[
											    	{label:"NSS Involucrados",name:"nss"},
											    	{label:"Agregado por",name:"origen"},
											    	{label:"Documentos probatorios del NSS",name:"tipoDocumento",href:"mostrarDocumento"}
											    ]
										       }        
										    ]
										 } 	       
			        	      ],
			          layout:[[{span:12}]]
			          },
			          
			          {type:"PanelComponent", id:"infoAdicional", //PANEL 6
			        	    components:[
										{
										    type:"GridComponent",
										    title:"",
										    id:"gridDoctosAdicionalesAsegurado",
										    model:"gridDocumentos",                        
										    data:"fetchDocumentos",//CREAR CONSULTA
										    columns:[
										    	{label:"Tipo de Documento",name:"tipoDocumento"},
										    	{label:"Documentos Adicionales",name:"tipoDocumento",href:"mostrarDocumento"}
										    ]
										 },
										 {
											    type:"GridComponent",
											    title:"",
											    id:"gridDoctosAdicionalesNss",
											    model:"gridDocumentos",                        
											    data:"fetchDocumentos",//CREAR CONSULTA
											    columns:[
											    	{label:"Tipo de Documento",name:"tipoDocumento"},
											    	{label:"NSS Involucrados",name:"tipoDocumento"},
											    	{label:"Documentos Adicionales",name:"tipoDocumento",href:"mostrarDocumento"}
											    ]
									      },
										  {//checar antiguo grid
												 type:"GridComponent",
												 title:"",
												 id:"gridDoctosAdicionalesBeneficiario",
												 model:"gridDocumentos",                        
												 data:"fetchDocumentos",
												 
												  /* id:"gridDocumentosBeneficiario",REVISAR SI SE REUSA ESTA CONSULTA
										           model:"gridDocumentosBeneficiario",                        
										           data:"fetchDocumentosBeneficiario"*/
												 
												 columns:[
												   	{label:"Tipo de Documento",name:"tipoDocumento"},
											    	{label:"Parentesco",name:"tipoDocumento"},
											    	{label:"Documentos Adicionales",name:"tipoDocumento",href:"mostrarDocumento"}
												 ]
										    },
			        	      ],
			          layout:[[{span:12}],[{span:12}],[{span:12}]]
			          },
			          
			          {type:"PanelComponent", id:"historia",//PANEL 7
			        	  components:[                      
			        	              {
			        	                type:"GridComponent",
			        	                title:"",
			        	                id:"gridHistoriaLaboral",
			        	                model:"gridHistoriaLaboral",                        
			        	                data:"fetchHistoriaLaboral",
			        	                styled:true,
			        	                breakWord:true,
			        	                columns:[
			        	                  { label:"Nombre o raz\u00F3n social",name:"nombrePatron",breakWord:true},
			        	                  { label:"Entidad federativa",name:"entidadFederativa"},
			        	                  { label:"Fecha de inscripci\u00F3n",name:"fechaInscripcion"},
			        	                  { label:"Fecha de baja",name:"fechaBaja"},
			        	    			  { label:"N\u00FAmero de registro patronal",name:"numeroRegistroPatronal"},
			        	                  { label:"Actividad de la empresa",name:"actividadEmpresa",breakWord:true},
			        	                  { label:"Domicilio de la empresa",name:"domicilioEmpresa",breakWord:true}
			        	                ]
			        	              }
			        	            ],
			        	            layout:[[{span:12},{span:12}]]
			              },
			          
			          {type:"PanelComponent", id:"contacto", //PANEL 8
			        	    components:[
								  {
								    type:"PanelComponent",
								    label:"Datos de contacto",
								    id:"panelDatosContacto",                    
								    components:[
								      {type:"TextFieldComponent", field:"informacionRENAPO.telefonoFijo",label:"Tel\u00E9fono fijo",disabled:true, labelStyle:true},
								      {type:"TextFieldComponent", field:"informacionRENAPO.telefonoMovil",label:"Tel\u00E9fono m\u00F3vil",disabled:true, labelStyle:true},
								      {type:"TextFieldComponent", field:"informacionRENAPO.correoElectronico",label:"Correo electr\u00F3nico",disabled:true, labelStyle:true}
								    ],
								    layout:[[{span:6},{span:6}],[{span:6}]]
								  },
								  {
								    type:"PanelComponent",
								    label:"Observaciones",
								    id:"panelObservaciones",                    
								    components:[
								      {type:"TextAreaFieldComponent", field:"observacion",disabled:true,rows:6}
								    ],
								    layout:[[{span:12}]]                                                                
								  },
								  {
								      type:"PanelComponent",
								      label:"Observaciones Subdelegaci\u00F3n",
								      id:"panelObservacionesSubdelegacion",                    
								      components:[
								        {type:"TextAreaFieldComponent", field:"observacionSubdelegacion",disabled:true,rows:6}
								      ],
								      layout:[[{span:12}]]                                                                
								   },
			        	             
			        	      ],
			          layout:[[{span:12}],[{span:12}],[{span:12}]]
			          },
						
                      
                     ]
      }//TERMINA COMPONENTES DE TABS
    	
    ],
    layout:[[{span:12}],[{span:12}],[{span:12}],[{span:12}],[{span:12}],[{span:12}],[{span:12}],[{span:12}]]
  };
};
