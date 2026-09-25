function BandejaCuentaIndividualResumenUI(module) {
	this.module = module;	
	this.init();
}

BandejaCuentaIndividualResumenUI.prototype.init = function() {
	this.metadata = {
		ui : {
			components : [ {
				type : "PanelComponent",
				id : "panelCuentaIndividual",
				components : [
						{
							type : "PanelComponent",
							id : "panelNuevaSolicitud",
							hidden : true,
							components : [ {
								type : "ButtonComponent",
								command : "nuevaSolicitud",
								label : "Nueva",
								className : "btn-primary pull-right",
								inLineWithLabel : true,
								id : "btnNuevaSolicitud"
							} ],
							layout : [ [ {
								span : 12
							} ] ]
						},						
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
													label : "Informaci\u00f3n hola  de cuenta individual del n&uacutemero de seguridad social con el que se certificar\u00E1 el tr\u00E1mite"
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
												
												{
													type : "HeaderComponent",
													id : "encabezadoResumenI",
													model : "encabezadoResumenI",
													data : "fetchCuentaIndividualResumen",
													styled : true,
													fontSize : "10px",
													columns : [
															{
																label : "Registro Patronal",
																name : "nombreRP"
															},
															{
																label : "Delegaci\u00f3n",
																name : "nombreDelegacionOrigen"
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
																name : "nss"															
															}]
												}

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
													id : "gridTramitesResumenL",
													model : "gridTramitesResumenL",
													data : "fetchCuentaIndividual",
													styled : true,
													fontSize : "10px",
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
																label : "Fecha recepci\u00f3n movimiento.",
																name : "fechaRecepcionMovimiento"
															},
															{
																label : "Tipo de movimiento inicial.",
																name : "tipoMovimientoIniintcial"
															},
															{
																label : "Origen movimiento inicial.",
																name : "origenMovimientoInicial"
															},
															{
																label : "Fecha inicio movimiento.",
																name : "fechaInicioMovimiento"
															},
															{
																label : "Salario base de cotizaci\u00f3n.",
																name : "salarioBase"
															},
															{
																label : "Tipo de salario base de cotizaci\u00f3n.",
																name : "tipoSalario"
															},
															{
																label : "Tipo de trabajador.",
																name : "eventual"
															},
															{
																label : "Extempor\u00E1neo.",
																name : "extemporaneoConvenioSuspencion"
															},
															{
																label : "Subrogaci\u00f3n de servicios.",
																name : "subrogacionServicio"
															},
															{
																label : "Tipo de movimiento final.",
																name : "tipoMovimientoFinal"
															},
															{
																label : "Origen movimiento final.",
																name : "origenMovimientoFinal"
															},
															{
																label : "Fecha final movimiento.",
																name : "fechaFinalMovimiento"
															},
															{
																label : "N\u00damero consecutivo de periodos.",
																name : "numeroConsecutivoPeriodos"
															},
															{
																label : "NSS Destino (Por periodo).",
																name : "nss"
															}]
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
												
												{
													type : "HeaderComponent",
													id : "gridTramitesResumenZ",
													model : "gridTramitesResumenZ",
													data : "fetchCuentaIndividual",
													styled : true,
													fontSize : "10px",													
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
																label : "Fecha recepci\u00f3n movimiento.",
																name : "fechaRecepcionMovimiento"
															},
															{
																label : "Tipo de movimiento inicial.",
																name : "tipoMovimientoIniintcial"
															},
															{
																label : "Origen movimiento inicial.",
																name : "origenMovimientoInicial"
															},
															{
																label : "Fecha inicio movimiento.",
																name : "fechaInicioMovimiento"
															},
															{
																label : "Salario base de cotizaci\u00f3n.",
																name : "salarioBase"
															},
															{
																label : "Tipo de salario base de cotizaci\u00f3n.",
																name : "tipoSalario"
															},
															{
																label : "Tipo de trabajador.",
																name : "eventual"
															},
															{
																label : "Extempor\u00E1neo.",
																name : "extemporaneoConvenioSuspencion"
															},
															{
																label : "Subrogaci\u00f3n de servicios.",
																name : "subrogacionServicio"
															},
															{
																label : "Tipo de movimiento final.",
																name : "tipoMovimientoFinal"
															},
															{
																label : "Origen movimiento final.",
																name : "origenMovimientoFinal"
															},
															{
																label : "Fecha final movimiento.",
																name : "fechaFinalMovimiento"
															},
															{
																label : "N\u00damero consecutivo de periodos.",
																name : "numeroConsecutivoPeriodos"
															},
															{
																label : "NSS Destino (Por periodo).",
																name : "nss"
															}]
												} ],
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
						}, {
							type : "ButtonGroupComponent",
							components : [ {
								type : "ButtonComponent",
								command : "salir",
								label : "Salir",
								className : "btn btn-default"
							}]
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