function BandejaCuentaIlogicaUI(module) {
	this.module = module;
	this.init();
}

BandejaCuentaIlogicaUI.prototype.init = function() {
	this.metadata = {
		ui : {
			components : [ {
				type : "PanelComponent",
				id : "panelCuentaIlogica",
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
							tabs : [ "certificador"],
							labels : [ "CERTIFICADOR"],
							components : [
									{
										type : "PanelComponent",
										id : "certificador",
										components : [
												{
													type : "LabelComponent",
													label : "Informaci\u00f3n  de cuenta individual del n&uacutemero de seguridad social con el que se certificar\u00E1 el tr\u00E1mite"
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
													type : "LabelComponent",
													label : "Tipo de Regularizaci\u00f3n"
												},
												
												{
													type : "HeaderComponent",
													id : "encabezadoIlogica",
													model : "encabezadoIlogica",
													data : "readCuentaIlogica",
													styled : true,
													fontSize : "10px",
													icon : "glyphicon glyphicon-plus",
													iconaction : "addPeriodoCuentaIlogica",
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
																name : "tipoMovimientoIniintcial",
																editable : true,
																type: "select",
																valores: [{clave:1,valor:1},{clave:7,valor:7},{clave:8,valor:8}],
																evento: "registrarCambiosIlogica"
															},
															{
																label : "Ori Mov",
																name : "origenMovimientoInicial",
																editable : "true",
																type: "select",
																valores: [{clave:0,valor:0},{clave:1,valor:1},{clave:2,valor:2},{clave:3,valor:3},{clave:4,valor:4},{clave:5,valor:5},{clave:6,valor:6},{clave:7,valor:7},{clave:8,valor:8},{clave:9,valor:9}],
																evento: "registrarCambiosIlogica"
															},
															{
																label : "Fec Mov Ini",
																name : "fechaInicioMovimiento",
																editable : "true",
																type: "text"
															},
															{
																label : "SBC",
																name : "salarioBase",
																editable : "true",
																type: "text"
															},
															{
																label : "T SBC",
																name : "tipoSalario",
																editable : "true",
																type: "select",
																valores: [{clave:0,valor:0},{clave:1,valor:1},{clave:2,valor:2},{clave:3,valor:3},{clave:4,valor:4},{clave:5,valor:5},{clave:6,valor:6},{clave:7,valor:7},{clave:8,valor:8},{clave:9,valor:9}],
																evento: "registrarCambiosIlogica"
															},
															{
																label : "TT",
																name : "eventual",
																editable : "true",
																type: "select",
																valores: [{clave:1,valor:1},{clave:2,valor:2},{clave:3,valor:3},{clave:4,valor:4}],
																evento: "registrarCambiosIlogica"
															},
															{
																label : "EXT",
																name : "extemporaneoConvenioSuspencion",
																editable : "true",
																type: "select",
																valores: [{clave:0,valor:0},{clave:1,valor:1},{clave:2,valor:2},{clave:3,valor:3},{clave:4,valor:4},{clave:5,valor:5},{clave:6,valor:6},{clave:7,valor:7},{clave:8,valor:8},{clave:9,valor:9}],
																evento: "registrarCambiosIlogica"
															},
															{
																label : "SS",
																name : "subrogacionServicio",
																editable : "true",
																type: "select",
																valores: [{clave:0,valor:0},{clave:1,valor:1},{clave:2,valor:2},{clave:3,valor:3},{clave:4,valor:4},{clave:5,valor:5},{clave:6,valor:6},{clave:7,valor:7},{clave:8,valor:8},{clave:9,valor:9}],
																evento: "registrarCambiosIlogica"
															},
															{
																label : "Mov Fin",
																name : "tipoMovimientoFinal",
																editable : "true",
																type: "select",
																valores: [{clave:0,valor:0},{clave:2,valor:2},{clave:7,valor:7}],
																evento: "registrarCambiosIlogica"
															},
															{
																label : "Ori Mov",
																name : "origenMovimientoFinal",
																editable : "true",
																type: "select",
																valores: [{clave:0,valor:0},{clave:1,valor:1},{clave:2,valor:2},{clave:3,valor:3},{clave:4,valor:4},{clave:5,valor:5},{clave:6,valor:6},{clave:7,valor:7},{clave:8,valor:8},{clave:9,valor:9}],
																evento: "registrarCambiosIlogica"
															},
															{
																label : "Fec Mov Fin",
																name : "fechaFinalMovimiento",
																editable : "true",
																type: "text"
															},
															{
																label : "Jornada Semanal.",
																name : "jornada",
																editable : "true",
																type: "select",
																valores: [{clave:0,valor:0},{clave:1,valor:1},{clave:2,valor:2},{clave:3,valor:3},{clave:4,valor:4},{clave:5,valor:5},{clave:6,valor:6},{clave:7,valor:7},{clave:8,valor:8},{clave:9,valor:9}],
																evento: "registrarCambiosIlogica"
															},
															{
																label : "Regularizaci\u00f3n",
																name : "z"
															},
															{
																label : "Cambio",
																name : "a"
															},
															{
																label : "Acci\u00f3n",
																name : "z",
																editable : "true",
																type: "link",
																links:[{nombre:"Eliminar",accion:"eliminar"},{nombre:"Descartar",accion:"descartar"}]
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
									} ]

						}, {
							type : "LabelComponent",
							label : ""
						}, {
							type : "ButtonGroupComponent",
							components : [ {
								type : "ButtonComponent",
								command : "salir",
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