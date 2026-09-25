function BandejaCuentaIndividualUI(module) {
	this.module = module;
	this.init();
}

BandejaCuentaIndividualUI.prototype.init = function() {
	this.metadata = {
		ui : {
			components : [ {
				type : "PanelComponent",
				id : "panelCuentaIndividual",
				components : [											
						{
							type : "PanelTabComponent",
							subtype : "grid",
							id : "panelTabsBandejas",
							tabs : [ "certificador", "asociado", "asegurado" ],
							labels : [ "CERTIFICADOR",
									"ASOCIADOS AL ASEGURADO",
									"NO CORRESPONDEN AL ASEGURADO" ],
							components : [
									{
										type : "PanelComponent",
										id : "certificador",
										components : [
												{
													type : "LabelComponent",
													label : "Informaci\u00f3n  de cuenta individual del n\u00FAmero de seguridad social con el que se certificar\u00E1 el tr\u00E1mite"
												},
												{
													type : "LabelComponent",
													label : "NSS"
												},
												{
													type : "LabelComponent",
													label : folioTramite
												},
												{
													type : "LabelComponent",
													label : "Tipo de Regularizaci\u00f3n"
												},
												{
													type : "TextFieldComponent",
													field : "regularizacion",
													disabled : true,
													labelStyle : true
												},
                        { type: "IteratorComponent", indexName: "index",
                          id: "consultaSolicitud.cuentaIndividual.data", 
                          model: "consultaSolicitud.cuentaIndividual.data",
                          entry:{
                            type: "CuentaIndividualNssComponent", 
                            id: "consultaSolicitud.cuentaIndividual.data[index]",
                            model: "consultaSolicitud.cuentaIndividual.data[index]", state:"nss"
                          }
                        }
												/*
												{
													type : "HeaderComponent",
													id : "encabezadoCI",
													model : "encabezadoCI",
													data : "fetchCuentaIndividual",
													styled : true,
													fontSize : "10px",
													icon : "glyphicon glyphicon-pencil",
													iconaction : "readCuentaIlogica",
													columns : [
															{
																label : "Registro Patronal",
																name : "registroPatronal"
															},
															{
																label : "Delegaci\u00f3n",
																name : "claveDelegacionOrigen"
															},
															{
																label : "CIZ",
																name : "claveCiz"
															},
															{
																label : "NSS Destino",
																name : "nssDestino",
																editable : true,
																type: "select"
															}],
													subcolumns : [
															{
																label : "Recep Mov Ini",
																name : "fechaRecepcionMovimiento"															
															},
															{
																label : "Mov Ini",
																name : "tipoMovimientoIniintcial"
															},
															{
																label : "Ori Mov",
																name : "origenMovimientoInicial"
															},
															{
																label : "Fec Mov Ini",
																name : "fechaInicioMovimiento"
															},
															{
																label : "SBC",
																name : "salarioBase"
															},
															{
																label : "T SBC",
																name : "tipoSalario"
															},
															{
																label : "TT",
																name : "eventual"
															},
															{
																label : "EXT",
																name : "extemporaneoConvenioSuspencion"
															},
															{
																label : "SS",
																name : "subrogacionServicio"
															},
															{
																label : "Mov Fin",
																name : "tipoMovimientoFinal"
															},
															{
																label : "Ori Mov",
																name : "origenMovimientoFinal"
															},
															{
																label : "Fec Mov Fin",
																name : "fechaFinalMovimiento"
															},
															{
																label : "N\u00damero consecutivo de periodos.",
																name : "numeroConsecutivoPeriodos"
															},
															{
																label : "NSS Destino (Por periodo).",
																name : "nss",
																editable : true,
																type: "select",																
																evento: "registrarCambios"
															},
															{
																label : "Regularizaci\u00f3n.",
																name : "regularizacionAux"
															},
															{
																label : "Consecutivo.",
																name : "consecutivoAux"
															},]
												}
*/
										],
										layout : [ [ {
											span : 12
										} ], [ {
											span : 3
										}, {
											span : 3
										}, {
											span : 3
										}, {
											span : 3
										} ], [ {
											span : 12
										} ]]
									},
									{
										type : "PanelComponent",
										id : "asociado",
										components : [
												{
													type : "LabelComponent",
													label : "Informaci\u00f3n de los n\u00FAmeros de seguridad social que ser\u00E1n cancelados por duplicidad"
												},												
												{
													type : "LabelComponent",
													label : "NSS"
												},
												{
													type : "TextFieldComponent",
													field : "nss",
													disabled : true,
													labelStyle : true
												},
												{
													type : "LabelComponent",
													label : "Tipo de Regularizaci\u00f3n"
												},
												{
													type : "TextFieldComponent",
													field : "regularizacion",
													disabled : true,
													labelStyle : true
												},
                        { type: "IteratorComponent", indexName: "index",
                          id: "consultaSolicitud.cuentaIndividual.data", 
                          model: "consultaSolicitud.cuentaIndividual.data",
                          entry:{
                            type: "CuentaIndividualNssComponent", 
                            id: "consultaSolicitud.cuentaIndividual.data[index]",
                            model: "consultaSolicitud.cuentaIndividual.data[index]", state:"nss"
                          }
                        }
												/*
												{
													type : "HeaderComponent",
													id : "gridTramitesL",
													model : "gridTramitesL",
													data : "fetchCuentaIndividual",
													styled : true,
													fontSize : "10px",
													icon : "glyphicon glyphicon-pencil",
													iconaction : "readCuentaIlogica",
													columns : [
															{
																label : "Registro Patronal",
																name : "registroPatronal"
															},
															{
																label : "Delegaci\u00f3n",
																name : "claveDelegacionOrigen"
															},
															{
																label : "CIZ",
																name : "claveCiz"
															},
															{
																label : "NSS Destino",
																name : "nssDestino"
															}],
													subcolumns : [
															{
																label : "Recep Mov Ini",
																name : "fechaRecepcionMovimiento"															
															},
															{
																label : "Mov Ini",
																name : "tipoMovimientoIniintcial"
															},
															{
																label : "Ori Mov",
																name : "origenMovimientoInicial"
															},
															{
																label : "Fec Mov Ini",
																name : "fechaInicioMovimiento"
															},
															{
																label : "SBC",
																name : "salarioBase"
															},
															{
																label : "T SBC",
																name : "tipoSalario"
															},
															{
																label : "TT",
																name : "eventual"
															},
															{
																label : "EXT",
																name : "extemporaneoConvenioSuspencion"
															},
															{
																label : "SS",
																name : "subrogacionServicio"
															},
															{
																label : "Mov Fin",
																name : "tipoMovimientoFinal"
															},
															{
																label : "Ori Mov",
																name : "origenMovimientoFinal"
															},
															{
																label : "Fec Mov Fin",
																name : "fechaFinalMovimiento"
															},
															{
																label : "N\u00damero consecutivo de periodos.",
																name : "numeroConsecutivoPeriodos"
															},
															{
																label : "NSS Destino (Por periodo).",
																name : "nss",
																editable : true,
																type: "select",																
																evento: "registrarCambios"
															},
															{
																label : "Regularizaci\u00f3n.",
																name : "regularizacionAux"
															},
															{
																label : "Consecutivo.",
																name : "consecutivoAux"
															},]
												} 
                      
                  */
                  ],
										layout : [ [ {
											span : 12
										} ], [ {
											span : 3
										}, {
											span : 3
										}, {
											span : 3
										}, {
											span : 3
										} ], [ {
											span : 12
										} ]]
									},
									{
										type : "PanelComponent",
										id : "asegurado",
										components : [
												{
													type : "LabelComponent",
													label : "Informaci\u00f3n de los n\u00FAmeros de seguridad social que no pertenence al asegurado"
												},
												{
													type : "LabelComponent",
													label : "NSS"
												},
												{
													type : "TextFieldComponent",
													field : "nss",
													disabled : true,
													labelStyle : true
												},
												{
													type : "LabelComponent",
													label : "Tipo de Regularizaci\u00f3n"
												},
												{
													type : "TextFieldComponent",
													field : "regularizacion",
													disabled : true,
													labelStyle : true
												},
                        { type: "IteratorComponent", indexName: "index",
                          id: "consultaSolicitud.cuentaIndividual.data", 
                          model: "consultaSolicitud.cuentaIndividual.data",
                          entry:{
                            type: "CuentaIndividualNssComponent", 
                            id: "consultaSolicitud.cuentaIndividual.data[index]",
                            model: "consultaSolicitud.cuentaIndividual.data[index]", state:"nss"
                          }
                        }
									/*			
												{
													type : "HeaderComponent",
													id : "gridTramitesZ",
													model : "gridTramitesZ",
													data : "fetchCuentaIndividual",
													styled : true,
													fontSize : "10px",
													icon : "glyphicon glyphicon-pencil",
													iconaction : "readCuentaIlogica",
													columns :  [
															{
																label : "Registro Patronal",
																name : "registroPatronal"
															},
															{
																label : "Delegaci\u00f3n",
																name : "claveDelegacionOrigen"
															},
															{
																label : "CIZ",
																name : "claveCiz"
															},
															{
																label : "NSS Destino",
																name : "nssDestino"
															}],
													subcolumns : [
															{
																label : "Recep Mov Ini",
																name : "fechaRecepcionMovimiento"															
															},
															{
																label : "Mov Ini",
																name : "tipoMovimientoIniintcial"
															},
															{
																label : "Ori Mov",
																name : "origenMovimientoInicial"
															},
															{
																label : "Fec Mov Ini",
																name : "fechaInicioMovimiento"
															},
															{
																label : "SBC",
																name : "salarioBase"
															},
															{
																label : "T SBC",
																name : "tipoSalario"
															},
															{
																label : "TT",
																name : "eventual"
															},
															{
																label : "EXT",
																name : "extemporaneoConvenioSuspencion"
															},
															{
																label : "SS",
																name : "subrogacionServicio"
															},
															{
																label : "Mov Fin",
																name : "tipoMovimientoFinal"
															},
															{
																label : "Ori Mov",
																name : "origenMovimientoFinal"
															},
															{
																label : "Fec Mov Fin",
																name : "fechaFinalMovimiento"
															},
															{
																label : "N\u00damero consecutivo de periodos.",
																name : "numeroConsecutivoPeriodos"
															},
															{
																label : "NSS Destino (Por periodo).",
																name : "nss",
																editable : true,
																type: "select",																
																evento: "registrarCambios"
															},
															{
																label : "Regularizaci\u00f3n.",
																name : "regularizacionAux"
															},
															{
																label : "Consecutivo.",
																name : "consecutivoAux"
															},]
												} 
                  */    
                  ],
										layout : [ [ {
											span : 12
										} ], [ {
											span : 3
										}, {
											span : 3
										}, {
											span : 3
										}, {
											span : 3
										} ], [ {
											span : 12
										} ]]
									} ]

						}, {
							type : "LabelComponent",
							label : ""
						}, 
						{
							type : "ButtonGroupComponent",
							components : [ 
							               {
								type : "ButtonComponent",
								command : "volverBandeja",
								label : "Bandeja",
								className : "btn btn-default"
							}, 
							{
								type : "ButtonComponent",
								command : "regresardeCuentaIlogica",
								label : "Regresar",
								className : "btn btn-default"
							}, 
							{
								type : "ButtonComponent",
								command : "cuentaIndividualGuardar",
								label : "Guardar",
								className : "btn btn-primary"
							}, 
							{
								type : "ButtonComponent",
								command : "readCuentaIndividualResumen",
								label : "Siguiente",
								className : "btn btn-danger"
							} ]
						}

				],
				layout : [ [ {
					span : 12
				} ], [ {
					span : 12
				} ], [ {
					span : 12
				} ], [ {
					span : 12
				} ], [ {
					span : 12
				} ] ]
			} ],
			layout : [ [ {
				span : 12
			} ] ]
		}
	};
};