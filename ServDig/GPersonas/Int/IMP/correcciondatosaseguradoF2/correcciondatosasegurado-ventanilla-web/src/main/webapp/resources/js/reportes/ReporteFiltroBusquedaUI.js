function ReporteFiltroBusquedaUI(module) {
	this.module = module;
	this.init();

}

ReporteFiltroBusquedaUI.prototype.init = function() {
	this.metadata = {
		ui : {
			components : [ {
				type : "PanelComponent",
				id : "panelTramitesAsignados",
				components : [ {
					type : "FormPanelComponent",
					label:"<h4>Visor de reportes</h4>",
					collapsed : false,
					level : 5,
					id : "filter",
					model : "filtrosReportes",
					name : "filter",
					entity:"filter",
					postFetch : "finalRenderComponents",
					components : [

					{
						type : "SelectFieldComponent",
						label : "Delegaci\u00f3n",
						onchange : "cargarSubdelegaciones",
						id : "delegacionSelectField",
						field : "delegacion",
						tamanoAnchoSelect : 12,
						data : "fetchDelegacionCombo"
					}, {
						type : "SelectFieldComponent",
						label : "Subdelegaci\u00f3n",
						onchange : "cargarResponsables",
						id : "subdelegacionSelectField",
						field : "subdelegacion",
						tamanoAnchoSelect : 12,
						data : "fetchSubdelegacionCombo"
					}, {
						type : "SelectFieldComponent",
						label : "Autoriz\u00f3",
						id : "autorizoSelectField",
						field : "autorizo",
						tamanoAnchoSelect : 12,
						data : "fetchAutorizoCombo"
					},

					{
						type : "SelectFieldComponent",
						label : "Responsable",
						id : "responsableSelectField",
						field : "responsable",
						tamanoAnchoSelect : 12,
						data : "fetchResponsableCombo"
					}, {
						type : "SelectFieldComponent",
						label : "Origen",
						id : "origenSelectField",
						field : "origen",
						tamanoAnchoSelect : 12,
						data : "fetchOrigenCombo"
					}, {
						type : "TextFieldComponent",
						field : "folio",
						label : "Folio",
						maxlength : "25",
						tipo : "number",
						sinA : "a"
					},

					{
						type : "TextFieldComponent",
						field : "curp",
						label : "CURP",
						maxlength : "18"
					}, {
						type : "TextFieldComponent",
						field : "nssInvolucrado",
						label : "NSS",
						maxlength : "11",
						tipo : "number"
					}, {
						type : "SelectFieldComponent",
						label : "Tipo tramite",
						id : "tipoTramiteSelectField",
						field : "tipoTramite",
						tamanoAnchoSelect : 12,
						data : "fetchTipoTramiteCombo"
					},

					{
						type : "SelectFieldComponent",
						label : "Estado",
						id : "estadoSelectField",
						field : "estado",
						tamanoAnchoSelect : 12,
						data : "fetchCombo"
					}, {
						type : "TextFieldComponent",
						field : "curpBeneficiario",
						label : "CURP beneficiario/ Representante legal",
						maxlength : "18"
					}, {
						type : "CheckBoxFieldComponent",
						field : "vencida",
						label : "Vencida",
						inLineWithLabel : true
					},

					{
						type : "LabelComponent",
						label : "Fecha de solicitud ",
						name : "fechaSoicitud",
						className : "h5"
					}, {
						type : "LabelComponent",
						label : "Fecha de finalizaci\u00f3n",
						name : "fechaFinalizacion",
						className : "h5"
					}, {
						type : "LabelComponent",
						label : "Fecha de actualizaci\u00f3n ",
						name : "fechaActualizacion",
						className : "h5"
					},

					{
						type : "DatePickerFieldComponent",
						field : "fechaSolicitudDesde",
						label : "Desde:"
					}, {
						type : "DatePickerFieldComponent",
						field : "fechaFinalizacionDesde",
						label : "Desde:"
					}, {
						type : "DatePickerFieldComponent",
						field : "fechaActualizacionDesde",
						label : "Desde:"
					},

					{
						type : "DatePickerFieldComponent",
						field : "fechaSolicitudHasta",
						label : "Hasta:"
					}, {
						type : "DatePickerFieldComponent",
						field : "fechaFinalizacionHasta",
						label : "Hasta:"
					}, {
						type : "DatePickerFieldComponent",
						field : "fechaActualizacionHasta",
						label : "Hasta:"
					},

					{
						type : "ButtonGroupComponent",
						components : [ {
							type : "ButtonComponent",
							command : "clean",
							label : "Limpiar",
							className : "btn-default"
						}, {
							type : "ButtonComponent",
							command : "filtrosReportes",
							label : "Buscar",
							className : "btn-primary",
							icon : "search",
							inLineWithLabel : true
						}

						]
					}

					],
					layout : [ [ {
						span : 4
					}, {
						span : 4
					}, {
						span : 4
					} ], [ {
						span : 4
					}, {
						span : 4
					}, {
						span : 4
					} ], [ {
						span : 4
					}, {
						span : 4
					}, {
						span : 4
					} ], [ {
						span : 4
					}, {
						span : 4
					}, {
						span : 4
					} ], [ {
						span : 4
					}, {
						span : 4
					}, {
						span : 4
					} ], [ {
						span : 4
					}, {
						span : 4
					}, {
						span : 4
					} ], [ {
						span : 4
					}, {
						span : 4
					}, {
						span : 4
					} ], [ {
						span : 12
					} ] ]

				}, {
					type : "FormPanelComponent",
					collapsed : false,
					level : 5,
					id : "grid",
					model : "gridTramites",
					name : "fetchTramites",
					components : [ {
						type : "GridComponent",
						title : "Tr\u00E1mites Asignados",
						id : "gridTramites",
						model : "gridTramites",
						desfasado : true,
						fontSize : "73%",
						data:"fetchTramites",
						maxPage : 10,
						columns : [ {
							label : "Folio",
							name : "folio",
							href : perilUsuario =="AUTORIZADOR"? "selectTramite": undefined 
						}, {
							label : "CURP",
							name : "curp"
						}, {
							label : "NSS involucrados",
							name : "nssInvolucrados"
						}, {
							label : "Vencida",
							name : "vencida"
						}, {
							label : "Delegaci\u00f3n",
							name : "delegacion"
						}, {
							label : "Subdelegaci\u00f3n",
							name : "subdelegacion"
						}, {
							label : "Autoriz\u00f3",
							name : "autorizo"
						}, {
							label : "Responsable",
							name : "responsable"
						}, {
							label : "Origen",
							name : "origen"
						}, {
							label : "Tipo de tr\u00e1mite",
							name : "tipo"
						}, {
							label : "Fecha de solicitud",
							name : "fechaSolicitud"
						}, {
							label : "Fecha de finalizaci\u00f3n",
							name : "fechaFinalizacion"
						}, {
							label : "\u00daltima actualizaci\u00f3n",
							name : "ultimaActualizacion"
						}, {
							label : "Estado",
							name : "estatus"
						} ]
					}, {
						type : "LabelComponent"
					}, {
						type : "ButtonGroupComponent",
						components : [ {
							type : "ButtonComponent",
							command : "regresarCorreccion",
							label : "Regresar",
							className : "btn-default"
						}, {
							type : "ButtonComponent",
							command : "generaReporte",
							label : "Generar Reporte",
							className : "btn-primary"
						}, {
							type : "ButtonComponent",
							command : "exportaExcel",
							label : "Exportar Excel",
							className : "btn-primary"
						}, {
							type : "ButtonComponent",
							command : "salir",
							label : "Salir",
							className : "btn-danger"
						} ]
					} ],
					layout : [ [ {
						span : 12
					} ], [ [ {
						span : 12
					} ] ], [ {
						span : 12
					} ] ]
				}

				],
				layout : [ [ {
					span : 12
				} ], [ {
					span : 12
				} ], [ {
					span : 12
				} ], [ [ {
					span : 12
				} ], [ {
					span : 12
				} ], [ {
					span : 12
				} ] ] ]
			} ]

		}
	};

};