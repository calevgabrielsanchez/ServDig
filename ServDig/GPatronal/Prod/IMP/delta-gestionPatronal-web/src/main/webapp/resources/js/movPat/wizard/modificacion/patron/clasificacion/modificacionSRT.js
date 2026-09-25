var fechaEfectoDatePicker,
fechaReasignacionPicker,
fechaEfectoDatePickerVentanilla,
idGridProductos = "#gridProductosServicios",
idGridMaterials = "#gridMaeriasMateriales",
idGridMqEquipos = "#gridMaquinariaEquipo",
idGrid_Personal = "#gridPersonal",
id_Grid_Bienes = "#gridBienes",
idGridTransport = "#gridTrasporte",
idGridClasificacionActual = "#gridClasificacionActual",
idGridClasificacionNueva = "#gridClasificacionNueva",
idFormaDialogo = "#formaDialogo",
idFormaProceso = "#formProcesos",
idDialogoGrids = "#dialogoGrids",
idDialogoError = "#dgErrorSinSeleccion",
dialogoError,
clasificacion,
firmaDigitalCtrl;
// Restrict user input in a text field create as many regular expressions here
// as you need:
var digitsOnly = "1234567890",
noEsDigito = /[^0-9]/g,
campoGiroPermitido = /[\sa-zA-Z\d\#\%\(\)\,\-\.\/\?\@\*\']/g,
campoGiroNegado = /[^\sa-zA-Z\d\u00F1\u00E1\u00E9\u00ED\u00F3\u00FA\u00D1\u00C1\u00C9\u00CD\u00D3\u00DA\#\%\(\)\,\-\.\/\?\@\*\']/g,
indicadorTramiteClasifExistente;
var minDate = 364,
campoToFocusOn,
clasificacionClasificador,
domicilioCentrotrabajo,
primaAsignada="",
columnasGridProductos = [ {
	mDataProp : "idVista",
	sTitle : "",
	bVisible : false,
	componente : 'texfield'
}, {
	mDataProp : "descripcion",
	sTitle : "Principales productos elaborados <a class='icono-help delta-tooltip btn btn-xs' data-toggle='tooltip' title='Es cualquier objeto tangible que se fabrica u ofrece a un mercado para su atenci&oacute;n, adquisici&oacute;n, uso o consumo y que satisface un deseo o una necesidad de un cliente.'></a> o servicios prestados",
	bVisible : true,
	componente : 'textarea',
	type : "texto",
	size : "300"
} ],
columnasGridMaterials = [ {
	mDataProp : "idVista",
	sTitle : "",
	bVisible : false,
	componente : 'texfield'
}, {
	mDataProp : "descripcion",
	sTitle : "Principales materias primas <a class='icono-help delta-tooltip btn btn-xs' data-toggle='tooltip' title='Son los materiales extra&iacute;dos de la naturaleza que sirven para transformar la misma y construir bienes de consumo.  Ejemplos: algod&oacute;n, madera, agua, el hierro, oro, grava, petr&oacute;leo, granito, etc.'></a> y materiales utilizados <a class='delta-tooltip btn btn-xs icono-help' data-toggle='tooltip' title='Son aquellos insumos que han sido transformados pero que todav&iacute;a no constituyen definitivamente un bien de consumo o producto final de una empresa.  Ejemplos: tubos, textiles, pl&aacute;sticos, etc.'></a>",
	bVisible : true,
	componente : 'textarea',
	type : "texto",
	size : "300"
} ],
columnasGridMqEquipos = [ {
	mDataProp : "idVista",
	sTitle : "",
	bVisible : false,
	componente : 'texfield'
}, {
	mDataProp : "desNombre",
	sTitle : "Descripci\u00F3n",
	bVisible : true,
	componente : 'texfield',
	type : "texto",
	sWidth : "150px",
	size : "30"
}, {
	mDataProp : "desCapacidadPotencia",
	sTitle : "Capacidad/Potencia",
	bVisible : true,
	componente : 'texfield',
	type : "texto",
	sWidth : "150px",
	size : "25"
}, {
	mDataProp : "tipo.descripcion",
	sTitle : "Tipo maquinaria <a class='icono-help delta-tooltip btn btn-xs' data-toggle='tooltip' title='No motorizados: Los que operan manualmente o artesanalmente para lograr la transformaci&oacute;n de insumos o materias primas, en productos o servicios. Motorizados no automatizados: Operados por la mano del hombre, que combinan el impulso de motores el&eacute;ctricos o de combusti&oacute;n para el procesamiento o transformaci&oacute;n, de insumos o materias primas en productos o servicios. Automatizados: son los que realizan procesos continuos de transformaci&oacute;n de insumos o materias primas, que b&aacute;sicamente son operados o programados a trav&eacute;s de computadoras.'></a>",
	bVisible : true,
	sWidth : "150px",
	componente : 'select'
}, {
	mDataProp : "desUso",
	sTitle : "Uso",
	bVisible : true,
	componente : 'texfield',
	sWidth : "180px",
	type : "texto",
	size : "100"
}, {
	mDataProp : "numUnidades",
	sTitle : "Unidades",
	bVisible : true,
	componente : 'texfield',
	sWidth : "70px",
	type : "numero",
	size : "5"
} ],
columnasGridTransport = [ {
	mDataProp : "idVista",
	sTitle : "",
	bVisible : false,
	componente : 'texfield'
}, {
	mDataProp : "desNombre",
	sTitle : "Descripci\u00F3n",
	bVisible : true,
	componente : 'texfield',
	type : "texto",
	size : "30"
}, {
	mDataProp : "desCapacidadPotencia",
	sTitle : "Capacidad/Potencia",
	bVisible : true,
	componente : 'texfield',
	type : "texto",
	size : "25"
}, {
	mDataProp : "tipoCombustible.desTipoCombustible",
	sTitle : "Tipo combustible",
	bVisible : true,
	componente : 'select',
	type : "texto"
}, {
	mDataProp : "desUso",
	sTitle : "Uso",
	bVisible : true,
	componente : 'texfield',
	type : "texto",
	size : "40"
}, {
	mDataProp : "numUnidades",
	sTitle : "Unidades",
	bVisible : true,
	componente : 'texfield',
	type : "numero",
	size : "5"
} ],
columnasGrid_Personal = [ {
	mDataProp : "idVista",
	sTitle : "",
	bVisible : false,
	componente : 'texfield'
}, {
	mDataProp : "numTrabajadores",
	sTitle : "No. de trabajadores",
	bVisible : true,
	componente : 'texfield',
	type : "numero",
	size : "5"
}, {
	mDataProp : "oficioOcupacion",
	sTitle : "Oficio u ocupaci&oacute;n",
	bVisible : true,
	componente : 'texfield',
	type : "texto",
	size : "50"
} ];

var columnas_Grid_Bienes = [ {
	mDataProp : "idVista",
	sTitle : "",
	bVisible : false,
	componente : 'texfield'
}, {
	mDataProp : "numCantidad",
	sTitle : "Cantidad",
	bVisible : true,
	componente : 'textfield',
	type : "numero",
	size : "5"
}, {
	mDataProp : "desBienes",
	sTitle : "Descripci&oacute;n",
	bVisible : true,
	componente : 'textarea',
	type : "texto",
	size : "100"
} ];

var acciones = {
	producto : {
		Agregar : 'agregarProductosServicios',
		Modificar : 'modificarProductosServicios',
		Eliminar : 'eliminarProductosServicios'
	},
	material : {
		Agregar : 'agregarMateriaMaterial',
		Modificar : 'modificarMateriaMaterial',
		Eliminar : 'eliminarMateriaMaterial'
	},
	equipo : {
		Agregar : 'agregarMaquinariaEquipo',
		Modificar : 'modificarMaquinariaEquipo',
		Eliminar : 'eliminarMaquinariaEquipo'
	},
	transporte : {
		Agregar : 'agregarEquipoTransporte',
		Modificar : 'modificarEquipoTransporte',
		Eliminar : 'eliminarEquipoTransporte'
	},
	personal : {
		Agregar : 'agregarPersonal',
		Modificar : 'modificarPersonal',
		Eliminar : 'eliminarPersonal'
	},
	bienes : {
		Agregar : 'agregarBien',
		Modificar : 'modificarBien',
		Eliminar : 'eliminarBien'
	},
	combo : {
		tipo_descripcion : "cargarComboTipoMaquinaria",
		tipoCombustible_desTipoCombustible : "cargarComboTipoCombustible"
	},
	proceso : {
		Guardar : "guardarProcesos"
	},
	clasificacion : {
		Obtener : "obtenerClasificacion",
		Guardar : "guardarClasificacion",
		GuardarRevisar : "/guardarClasificacionPrevioRevisar",
		Finalizar : "finalizarClasificacion",
		Cancelar : "carcelarClasificacion",
		finalizarInternet : "finalizarInternet"
	}
};



var grid = {
	producto : null,
	material : null,
	equipo : null,
	transporte : null,
	personal : null,
	bienes : null
};

/* Obtiene la fila seleccionada */
function fnGetRowSelected( oTableLocal )
{
    var obRowSelected = null;

    $(oTableLocal.fnSettings().aoData).each(function (){
        if( $(this.nTr).hasClass('row_selected')){
            obRowSelected = this._aData;
        }
    });

    return obRowSelected;

};

$(function() {
	console.log('::: Iniciando Funcion ::::');
	hasMensajeError = false;
	console.log('::: hasMensajeError ::: ', hasMensajeError)
	if(!hasMensajeError){
		console.log('ENTRANDO A CONSTRUIR GRIDS');

		if ($("#indRegPatClase").val() == 1) { // Si el indRegPatClase viene con valor 1
												// no dar chance de modificar
												// al patron el PSP
			if ($("#indPrestaServicioPersonal").val() == 1) {
				$("#clasificacion\\.indPrestaServicioPersonal1").attr("checked", true);

				$("#clasificacion\\.indPrestaServicioPersonal1").attr("disabled", true);
				$("#clasificacion\\.indPrestaServicioPersonal2").attr("disabled", true);

			} else if ($("#indPrestaServicioPersonal").val() == null ) {
				$("#clasificacion\\.indPrestaServicioPersonal1").attr("checked", false);
				$("#clasificacion\\.indPrestaServicioPersonal2").attr("checked", false);
			} else if($("#indPrestaServicioPersonal").val() == 0){
				$("#clasificacion\\.indPrestaServicioPersonal1").attr("checked", false);
				$("#clasificacion\\.indPrestaServicioPersonal2").attr("checked", false);
			}
		} else if ($("#indRegPatClase").val() == null || $("#indRegPatClase").val() == 0
				|| $("#indRegPatClase").val() == -1) {
			if($("#indPrestaServicioPersonal").val() == ""){
				//DO NOTHING
			}else if ($("#indPrestaServicioPersonal").val() == 1) {
	//			$("#clasificacion\\.indPrestaServicioPersonal1").attr("disabled", true);
	//			$("#clasificacion\\.indPrestaServicioPersonal2").attr("disabled", true);

				$("#clasificacion\\.indPrestaServicioPersonal1").attr("checked", true);
			} else if ($("#indPrestaServicioPersonal").val() == 0) {
				$("#clasificacion\\.indPrestaServicioPersonal2").attr("checked", true);
			}
		}

		console.log('::: fraccionAgricultura ::: ', fraccionAgricultura)

		if($("#fraccionCompleta").val()==fraccionAgricultura){
			$("#infoCaneros").show();
			if (isProductorCana!=undefined && isProductorCana == 1){
				$("#indProductorCana").attr("checked", true);
			}else{
				$("#indProductorCana").attr("checked", false);
			}
		}

//		$(idGridClasificacionActual).dataTable({
//			bJQueryUI : false,
//			bFilter : false,
//			bInfo : false,
//			bSort : false,
//			bPaginate : false,
//			bAutoWidth : false
//		});

		$(idGridClasificacionNueva).dataTable({
			bJQueryUI : false,
			bFilter : false,
			bInfo : false,
			bSort : false,
			bPaginate : false,
			bAutoWidth : false
		});

		grid['producto'] = crearGrid(idGridProductos, columnasGridProductos,
				context + "paginarProductosServicios");
		grid['material'] = crearGrid(idGridMaterials, columnasGridMaterials,
				context + "paginarMateriaMaterial");
		grid['equipo'] = crearGrid(idGridMqEquipos, columnasGridMqEquipos, context
				+ "paginarMaquinariaEquipo");
		grid['transporte'] = crearGrid(idGridTransport, columnasGridTransport,
				context + "paginarEquipoTransporte");
		grid['personal'] = crearGrid(idGrid_Personal, columnasGrid_Personal,
				context + "paginarPersonal");
		if (mostrarBienes) {
			grid['bienes'] = crearGrid(id_Grid_Bienes, columnas_Grid_Bienes,
					context + "paginarBienes");
		} else {
			if ($("#seccionBienesInmuebles").length > 0) {
				$("#seccionBienesInmuebles")[0].style.display = "none";
			}
		}

		if (mostrarCentroTrabajo) {
			configurarBusquedaDomicilio();
			$("#seccionCentroTrabajo").show();
			parseTelefonos();
			//Se redefine el minDate para centro de trabajo
			//minDate = 9999;
			minDate = 364;
		} else {
			$("#seccionCentroTrabajo").hide();
		}
		
		if (mostrarDomicilioCentroTrabajo) {
			configurarBusquedaDomicilio();
			console.log("INFORMACION CENTRO DE TRABAJO ::: ",mostrarDomicilioCentroTrabajo)
			$("#seccionCentroTrabajoCambioDomicilio").show();
			parseTelefonos();
			//Se redefine el minDate para centro de trabajo
			//minDate = 9999;
			minDate = 364;
			
		} else {
			$("#seccionCentroTrabajoCambioDomicilio").hide();
		}

		fechaEfectoDatePicker = $("#fechaEfecto").datepicker({
			// showOn: "",
			// buttonImage: context_path +
			// "/static/resources/imagenes/calendar.gif",
			// buttonImageOnly: true,
			changeMonth : true,
			changeYear : true,
			dateFormat : 'dd/mm/yy',
			maxDate: 0,
			minDate : -minDate,
			onClose : function(dateText, inst) {
				if (dateText != "") {
					runEffectHideFechaEfectoInvalidaMsg();
					$("#fechaEfecto").attr("style", "width: 100px;");
				}
			}

		});
		
		fechaReasignacionPicker = $("#fechaReasignacion").datepicker({
			showOn: "button",
			buttonImage: context_path + "/static/resources/imagenes/calendar.gif",
			buttonImageOnly: true,
			changeMonth : true,
			changeYear : true,
			dateFormat : 'dd/mm/yy',
			maxDate: 30,
			minDate : +1,
			beforeShowDay: $.datepicker.noWeekends,
			onClose : function(dateText, inst) {
				if (dateText != "") {
					//runEffectHideFechaEfectoInvalidaMsg();
					$("#fechaReasignacion").attr("style", "width: 100px;");
				}
			}

		});


		dialogoError = construirDialogoSeleccionaRegistro();

		if (idSolicitud != null && idSolicitud > 0) {
			visibilidadBotones(2);
		} else {
			visibilidadBotones(1);
		}
		toggleCuentaConTransporte(equipoTransporte);
		//inicializarComponenteFirmaDigital();
	}

	$('.delta-tooltip').tooltip({
		animation : true,
		container : 'body'
	});

	inicializarDelegacionesSubdelegaciones();

});

function parseTelefonos(){
	var arregloTelefonoPrincipal = telefonoPrincipalCompleto.split("|");

	if(arregloTelefonoPrincipal.length==3){
		var lada = telefonoPrincipalCompleto.split("|")[0];
		var numeroTelefono = telefonoPrincipalCompleto.split("|")[1];
		var extension = telefonoPrincipalCompleto.split("|")[2];
		$("#ctLada").val(lada);
		$("#ctTelefonoFijo").val(numeroTelefono);
		$("#ctExtension").val(extension);

	}else{
		//No se ha capturado nada
		$("#ctLada").val('');
		$("#ctTelefonoFijo").val('');
		$("#ctExtension").val('');
	}

	var arregloTelefonoSecundario = telefonoSecundarioCompleto.split("|");
	if(arregloTelefonoSecundario.length==3){
		var lada2 = telefonoSecundarioCompleto.split("|")[0];
		var numeroTelefono2 = telefonoSecundarioCompleto.split("|")[1];
		var extension2 = telefonoSecundarioCompleto.split("|")[2];
		$("#ctLada2").val(lada2);
		$("#ctTelefonoFijo2").val(numeroTelefono2);
		$("#ctExtension2").val(extension2);
	}else{
		$("#ctLada2").val('');
		$("#ctTelefonoFijo2").val('');
		$("#ctExtension2").val('');
	}
}

function visibilidadBotones(caso) {
	switch (caso) {
	case 1: // Nueva solicitud
		$("#btnGuardar").removeAttr("disabled");
		$("#btnFinalizar").attr("disabled", true);
		$("#btnCancelar").attr("disabled", true);
		// $("#btnCancelar").removeAttr("disabled");
		break;
	case 2:// Solicitud Guaradada
		$("#btnGuardar").removeAttr("disabled");
		$("#btnFinalizar").removeAttr("disabled");
		$("#btnCancelar").removeAttr("disabled");
		break;
	case 3:// Solicitud Finalizada
			$("#formaAcuse").submit();
		// window.open(context_path+"/clasificacion/presentarAcuse",
		// "imprimir_documento",
		// "dialogWidth:1050px;dialogHeight:700px;status=yes,toolbar=no,menubar=no,location=no,resize=no");
		// $("#formaAcuse").submit();
		// objReporte.dialog('open');
		break;
	case 4:// Solicitud Cancelada
		history.back();
		break;
	default:
		$("#btnGuardar").attr("disabled", true);
		$("#btnFinalizar").attr("disabled", true);
		$("#btnCancelar").removeAttr("disabled");
		break;
	}
}

function crearGrid(idGrid, columModel, source) {
	var grid = $(idGrid).dataTable({
		bJQueryUI : false,
		bFilter : false,
		bInfo : false,
		bSort : false,
		bPaginate : false,
		bAutoWidth : false,
		bServerSide : true,
		bProcessing : true,
		oLanguage: {"sZeroRecords": "Dar clic en la opci&oacute;n 'Agregar' para capturar la informaci&oacute;n"},
		sAjaxSource : source,
		aoColumns : columModel,
		fnServerData : cargarGrid
	});

	/* Add a click handler to the rows - this could be used as a callback */
	$(idGrid + " tbody").click(function(event) {
		var seleccionar = !$(event.target.parentNode).hasClass('row_selected');
		$(grid.fnSettings().aoData).each(function() {
			$(this.nTr).removeClass('row_selected');
		});
		if (seleccionar) {
			$(event.target.parentNode).addClass('row_selected');
		}
	});
	return grid;
}

function cargarGrid(sSource, aoData, fnCallback) {
	aoData.push({
		"name" : "sSearch",
		"value" : ''
	});
	var wrapper = new Object();
	wrapper.oForm = new Object();
	var sujetoObligado = new Object();
	sujetoObligado.cveIdSujetoObligado = idSujetoObligado;
	wrapper.oForm.sujetoObligado = sujetoObligado;
	wrapper.aoData = aoData;
	$.postJSON(sSource, wrapper, function(data) {
		fnCallback(data);
	});
}


//Se modifica esta funcion para adecuar a la nueva funcionalidad del Clasificador para bevagadores modernos
function seleccionarClasificacion() {
	console.log("::: En seleccionarClasificacion");
	if(mostrarCentroTrabajo){
		idMunicipioIMSS=$("input[name='idMunicipioImssRadio']:checked").val();
//			var municipioSeleccionado=$("#idMunicipioImssRadio").is(':checked');
		if($("input[name='idMunicipioImssRadio']:checked").val()== undefined){
			construirDialogoMensajes("Municipio requerido", "Por favor seleccione el nuevo domicilio, la subdelegaci\u00F3n y municipio antes de seleccionar su clasificaci\u00F3n.", true, undefined, 200, 500 );
			return;
		}
		idMunicipioIMSS=$("input[name='idMunicipioImssRadio']:checked").val();
	}
	
	//si el tramite es aviso de cambio de domicilio solo se permite un patron capturado
	if(codigoTramite == 176){
		patronesUbicados = $("#contenedorComponenteBusquedaPatrones").busquedaRps("get");
		if(patronesUbicados.length != 1){ 
			console.log("::: Error, no se capturo un patron para el tramite de aviso cambio domicilio");
			construirDialogoMensajes("Error", "Debe capturar un patr\u00F3n con domicilio anterior", true, undefined, 200, 500 );
			return;
		}
	}	
	
	//Funcion declarada en el Clasificador para llamarlo y obtener su respuesta
	uid_call('imss.patrones.alta_patronal.clasificacion.clasificador', 'clickout');
	clasificador2();
}

//Funcion para asignar la fraccion devuelta por el clasificador desde IE
//valorClasificador es una variable declarada en la aplicación de clasificador que contiene la respuesta
function terminaClasificadorIE(){	
	clasificacion = new Object();
	if (valorClasificador != undefined && valorClasificador != null) {
		console.log("::: Valores devueltos por el clasificador");
		console.log("::: Division: " + valorClasificador.id.cveDivision + ", Grupo: " + valorClasificador.id.cveGrupo + ", Fraccion: " + 
			valorClasificador.id.cveFraccion + ", numPrimaMedia: " + valorClasificador.numPrimaMedia);
		clasificacion.fraccion = new Object();
		clasificacion.fraccion.id = valorClasificador.id.cveFraccion;
		clasificacion.fraccion.grupo = new Object();
		clasificacion.fraccion.grupo.id = valorClasificador.id.cveGrupo;
		clasificacion.fraccion.grupo.division = new Object();
		clasificacion.fraccion.grupo.division.id = valorClasificador.id.cveDivision;
		clasificacion.sujetoObligado=new Object();
		clasificacion.sujetoObligado.cveIdSujetoObligado = idSujetoObligado;
		clasificacion.sujetoObligado.tipoPersonaFiscal=tipoPersonaFiscal;
		clasificacion.sujetoObligado.municipioIMSS=new Object();
	
		clasificacion.sujetoObligado.municipioIMSS.idMunicipio=idMunicipioIMSS;
		if (tipoPersonaFiscal == "MORAL") {
			clasificacion.sujetoObligado.moral = new Object();
			clasificacion.sujetoObligado.moral.idPersona = idPersona;
			clasificacion.sujetoObligado.moral.rfc = rfcSujetoObligado;
		} else {
			clasificacion.sujetoObligado.fisica = new Object();
			clasificacion.sujetoObligado.fisica.idPersona = idPersona;
			clasificacion.sujetoObligado.fisica.cveFisica=idPersonaFisica;
			clasificacion.sujetoObligado.fisica.rfc = rfcSujetoObligado;
		}
	
		console.log("::: Asigne valores del clasificador");
		
		//Quitamos marca de prima modificada por el patron ya que seleccionaron el Clasificador
		if(codigoTramite == 11 || codigoTramite == 12 
			|| codigoTramite == 13 || codigoTramite == 14 
			|| codigoTramite == 15 || codigoTramite == 16 
			|| codigoTramite == 17 || codigoTramite == 18
			|| codigoTramite == 19 || codigoTramite == 20
			|| codigoTramite == 21 || codigoTramite == 22
			|| codigoTramite == 175 || codigoTramite == 176
		){
			console.log("::: Quitamos marca de prima modificada por el patron ya que seleccionaron del Clasificador, codigoTramite: " + codigoTramite);						
			clasificacion.indPrimaSugerida = "0";
			if ( codigoTramite == 20 || codigoTramite == 21 || codigoTramite == 175 || codigoTramite == 176){
				document.getElementById("modPrimaPatronFS").checked = false;	
				$("#primaSRTFusionSust").prop('disabled', true);
				$("#primaSRTFusionSust").val("");
			}else{
				document.getElementById("modPrimaPatron").checked = false;
				$("#primaSRTRestoTramites").prop('disabled', true);
				$("#primaSRTRestoTramites").val("");	
			}
		}		
				
		sendToServer("clasificacion", "Obtener", clasificacion,
				callbackObtenerClasificacion);	
	}else{
		console.log("::: La respuesta del Clasificador esta vacia");
	}					
}

//Funcion para asignar la fraccion devuelta por el clasificador desde otros navegadores
//valorClasificador es una variable declarada en la aplicación de clasificador que contiene la respuesta
function terminaClasificador(){
	var clasificacion = new Object();
	console.log("::: En terminaClasificador");
	
	if (valorClasificador != undefined && valorClasificador != null) {
		console.log("::: Valores devueltos por el clasificador");
		console.log("::: Division: " + valorClasificador.id.cveDivision + ", Grupo: " + valorClasificador.id.cveGrupo + ", Fraccion: " + valorClasificador.id.cveFraccion + ", numPrimaMedia: " + valorClasificador.numPrimaMedia);
		clasificacion.fraccion = new Object();
		clasificacion.fraccion.id = valorClasificador.id.cveFraccion;
		clasificacion.fraccion.grupo = new Object();
		clasificacion.fraccion.grupo.id = valorClasificador.id.cveGrupo;
		clasificacion.fraccion.grupo.division = new Object();
		clasificacion.fraccion.grupo.division.id = valorClasificador.id.cveDivision;
		clasificacion.sujetoObligado = new Object();
		clasificacion.sujetoObligado.cveIdSujetoObligado = idSujetoObligado;
		clasificacion.sujetoObligado.tipoPersonaFiscal = tipoPersonaFiscal;
		clasificacion.sujetoObligado.municipioIMSS=new Object();

		clasificacion.sujetoObligado.municipioIMSS.idMunicipio=idMunicipioIMSS;
		if (tipoPersonaFiscal == "MORAL") {
			clasificacion.sujetoObligado.moral = new Object();
			clasificacion.sujetoObligado.moral.idPersona = idPersona;
			clasificacion.sujetoObligado.moral.rfc = rfcSujetoObligado;
		} else {
			clasificacion.sujetoObligado.fisica = new Object();
			clasificacion.sujetoObligado.fisica.idPersona = idPersona;
			clasificacion.sujetoObligado.fisica.cveFisica=idPersonaFisica;
			clasificacion.sujetoObligado.fisica.rfc = rfcSujetoObligado;
		}
		
		console.log("::: Asigne valores del clasificador");
		
		//Quitamos marca de prima modificada por el patron ya que seleccionaron el Clasificador
		if(codigoTramite == 11 || codigoTramite == 12 
			|| codigoTramite == 13 || codigoTramite == 14 
			|| codigoTramite == 15 || codigoTramite == 16 
			|| codigoTramite == 17 || codigoTramite == 18
			|| codigoTramite == 19 || codigoTramite == 20
			|| codigoTramite == 21 || codigoTramite == 22
			|| codigoTramite == 175 || codigoTramite == 176
		){
			console.log("::: Quitamos marca de prima modificada por el patron ya que seleccionaron del Clasificador, codigoTramite: " + codigoTramite);						
			clasificacion.indPrimaSugerida = "0";
			if ( codigoTramite == 20 || codigoTramite == 21 || codigoTramite == 175 || codigoTramite == 176){
				document.getElementById("modPrimaPatronFS").checked = false;	
				$("#primaSRTFusionSust").prop('disabled', true);
				$("#primaSRTFusionSust").val("");
			}else{
				document.getElementById("modPrimaPatron").checked = false;
				$("#primaSRTRestoTramites").prop('disabled', true);
				$("#primaSRTRestoTramites").val("");	
			}
		}	

		sendToServer("clasificacion", "Obtener", clasificacion,
			callbackObtenerClasificacion);
	}else{
		console.log("::: La respuesta del Clasificador esta vacia");
	}
}

function callbackObtenerClasificacion(response) {
	console.log("::: En callbackObtenerClasificacion()");
	if (response.clasificacion != undefined && response.clasificacion != null
			&& response.clasificacion != "") {
		clasificacionClasificador = response;
		var clasificacion = response.clasificacion;
		var fraccion = clasificacion.fraccion;
		if(fraccion.clase.clave==undefined || fraccion.clase.clave==0){
			construirDialogoMensajes("Error", "Esta fracci\u00F3n no es v\u00E1lida pues no cuenta con una clase asociada", true);
			return;
		}

		if(response.mensajeError!=undefined && response.mensajeError!=""){
			construirDialogoMensajes("Error", response.mensajeError, true, undefined, 200, 600);
			return;
		}

		//Se almacena de forma temporal la primaAsignada
		console.log("::: Prima obtenida del clasificador: " + clasificacion.primaSRTActual);
		primaAsignada = clasificacion.primaSRTActual;
		validarReglaRPC(fraccion.clase.clave);
		
		//Aplicamos reglas por Mm 
		if(codigoTramite == 11 || codigoTramite == 12 
			|| codigoTramite == 13 || codigoTramite == 14 
			|| codigoTramite == 15 || codigoTramite == 16
		    || codigoTramite == 17 || codigoTramite == 18
			|| codigoTramite == 19 || codigoTramite == 22
		){
			//Validamos si la clasificacion seleccionada es la misma que que tiene el patron
			console.log("Validamos si la clasificacion seleccionada es la misma que tiene el patron");
			fracPatron = $("#fraccionActual").val(); 
			primaPatron = $("#objClasPrimaSRTActual").val();
			cveclasePatron = $("#cveClaseActual").val();
			fracClasf = fraccion.grupo.division.numDivision + fraccion.grupo.numGrupo + fraccion.numFraccion; 
			cveClaseClasificador = fraccion.clase.descripcion;
			console.log("::: Clasificacion patron: " + fracPatron + ", ClasePatron: " + cveclasePatron);
			console.log("::: Clasificacion seleccionada: "  + fracClasf + ", Clase seleccionada: " + cveClaseClasificador);
			//se aplica regla de Mm para asignar prima sugerida
			aplicaReglaPrimaSugerida(cveclasePatron, cveClaseClasificador, primaPatron, primaAsignada);
		}			
		
		if(codigoTramite == 176){
			console.log("::: Tramite 176, se envia flujo a metodo para calcular la prima sugerida");
			obtienePrimaFusSust();	
		}
		
	}else{
		if(response.mensajeError!=undefined && response.mensajeError!=""){
			construirDialogoMensajes("Error", response.mensajeError, true, undefined, 200, 600);
			return;
		}
	}
}

function aplicaReglaPrimaSugerida(cveclasePatron, cveClaseClasificador,primaPatron, primaAsignada){
	if(cveclasePatron == cveClaseClasificador){
		console.log("::: La clase seleccionada es la misma que tiene el patron, se aplica la prima que tiene el patron");
		$("#primaSRTRestoTramites").val(primaPatron);
	}else{
		console.log("::: La clase seleccionada NO es la misma que tiene el patron, se aplica la prima del clasificador");
		$("#primaSRTRestoTramites").val(primaAsignada);
	}		
}

function muestraShow(){
	$("#seccionPrima").show();	
}
function OcultaHide(){
	$("#seccionPrima").hide();
}
function muestraDisplay(){
	$("#seccionPrima").css("display", "block"); 	
}
function OcultaDisplay(){
	$("#seccionPrima").css("display", "none");
}

function validarReglaRPC(claveClase) {
	console.log("::: En validarReglaRPC()");
	var sSource = context + 'evaluarReglaRPC?indReintento=' + indReintento
			+ '&indRPCInvalido=' + indRPCInvalido;
	generarObjetoClasificacion();
	clasificacion.fraccion.clase.clave = claveClase;
	prepararRequest(sSource, clasificacion, false, callbackValidaRPC);
}

function callbackValidaRPC(response) {
	console.log("::: En callbackValidaRPC()");
	if (response.mensajeError != null && response.mensajeError != undefined) {
		construirDialogoMensajes("Error", response.mensajeError, true);
		indReintento = response.reintentoRpc;
		indRPCInvalido = response.rpcInvalido;
	} else {
		console.log("::: Asignando valores");
		var clasificacion = clasificacionClasificador.clasificacion;
		var fraccion = clasificacion.fraccion;
		var grupo = fraccion.grupo;
		var division = grupo.division;
		$("#fraccion").val(fraccion.id);
		$("#claveFraccion").html(fraccion.numFraccion);
		$("#textFraccion").html(fraccion.descripcionDetallada);

		$("#grupo").val(grupo.id);
		$("#claveGrupo").html(grupo.numGrupo);
		$("#textGrupo").html(grupo.descripcion);

		$("#divison").val(division.id);
		$("#claveDivision").html(division.numDivision);
		$("#textDivison").html(division.descripcion);

		$("#textClase").html(fraccion.clase.descripcion);
		//$("#textPrimaAnt").html(fraccion.primaSRT);
		console.log("::: primaAsignada: " + primaAsignada);
		$("#textPrimaAnt").html(primaAsignada);
		$("#primaClasificador").val(primaAsignada);
		$("#claveClase").val(fraccion.clase.clave);
		var claseCompleta = '' + division.numDivision + grupo.numGrupo
		+ fraccion.numFraccion;
		$("#claveDivisionCompleta").html(
				'' + division.numDivision + grupo.numGrupo
						+ fraccion.numFraccion);
		$("#fraccionClasificador").val(
				'' + division.numDivision + grupo.numGrupo
						+ fraccion.numFraccion);
		$("#cveclaseClasificador").val(
				'' + fraccion.clase.descripcion);
		indReintento = response.reintentoRpc;
		indRPCInvalido = response.rpcInvalido;
	}
}

function guardarClasificacion() {
	enviarClasificacion("Guardar");
}

function finalizarClasificacion() {
	$("#dialogoConcluirSolicitudFirma").parent().css("z-index", "500");
	$.blockUI();
	enviarClasificacion("Finalizar");
}

function guardarClasificacionPrevioRevisar() {
	enviarClasificacionPrevioRevisar("GuardarRevisar");
}

function closeModalFinalizacion() {
	$("#dialogoConcluirSolicitudFirma").css("display", "none");
	dialogFirma.dialog('close');
}

function enviarInternet() {
	$("#dialogoConcluirSolicitudFirma").parent().css("z-index", "500");
	$.blockUI();
	generarObjetoClasificacion();
	sendToServer("clasificacion", "finalizarInternet", clasificacion, callbackEnviarInternet);
}

function callbackEnviarInternet(response) {
	console.log('**** Finalizando despues de generar la cita *****', JSON.stringify(response));
	$.unblockUI();
	if (response.folioCita != undefined) {
		$("#dialogoConcluirSolicitudFirma").css("display", "none");
		dialogFirma.dialog('close');
		
		$("#nombreCita").html(response.nombreCita);
		$("#fechaGeneracionCita").html(response.fechaGeneracionCita);
		$("#folioCita").html(response.folioCita);
		$("#subDelCita").html(response.subDelCita);
		$("#diaCita").html(response.diaCita);
		
		mostrarDialogoCita();
		$("#citaOriginal").focus();

		
	} else if (response.mensajeError) {
		construirDialogoMensajes("Error", response.mensajeError, true, undefined);
	}
}

function mostrarDialogoCita() {
	dialogCita = $("#dialogoGenerarCita").dialog({
		autoOpen : false,
		resizable : false,
		height : 600,
		width : "100%",
		modal : true,
		zindex : 500
	});
	$("#dialogoGenerarCita").css("display", "block");
	$("#dialogoGenerarCita").parent().attr( "id", "idModalDialogoGenerarCita");
	dialogCita.dialog('open');
}

function cambiarFechaCita() {
		var fechaReasignacion = $("#fechaReasignacion").val();
		if(fechaReasignacion == ''){
			construirDialogoMensajes("Error", "Debes seleccionar una fecha", true, undefined);
		}else {
		construirDialogoConfirmarCambioCita();
		}
	}
	
function construirDialogoConfirmarCambioCita() {
	var dialogo = $("#dialogoConfirmacionCambioCita").dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		width : 400,
		title : "Confirmaci&oacute;n",
		buttons : {

			"No" : function() {
				$(this).dialog("close");
				$("#fechaReasignacion").val('');
			},
			"Si" : function() {
				confirmarCambioCita();
				$(this).dialog("close");
			}
		}
	});
	dialogo.dialog('open');
}	

function confirmarCambioCita(){
	$.blockUI();
	var date = $("#fechaReasignacion").val();
	
	console.log('idSOlciitud', idSolicitud);
	console.log('fechaReasignacion cita', date);

	var sSource = context_path + "/movPat/internet/wizard/tramite/clasificacion/modificaCita/" + idSolicitud;	
	
	var form_data2 = new FormData();
	form_data2.append("fechaNuevaCita", date);
	
	var request = $.ajax({
		url : sSource,
		type : "POST",
		data : form_data2,
		dataType : "json",
		processData: false,
		cache: false,
		contentType: false,
	});
	request.done(responseCambiarCita);
	request.fail(responseCambiarCita);
}	

function responseCambiarCita(data) {
	$.unblockUI();
	$("#dialogoGenerarCita").parent().css("z-index", "1022");
	console.log('Entrando a callbakc cambiar cita', data);
	$('#mensajeDialogo').text(data.mensaje);
	
	if (data.exito) {
		$("#diaCita").html(data.fechaHora);
		$("#divCambioCita").hide();
		construirDialogoMensajesMovPat("Info", data.mensaje, false, undefined, undefined, undefined);
	} else {
		construirDialogoMensajesMovPat("Error", data.mensaje, true, undefined, undefined, undefined);
	}
}

function imprimirCita(){
	$("#dialogoGenerarCita").parent().css("z-index", "500");
	$.blockUI();
	
	console.log('idSOlciitud', idSolicitud);

	var sSource = context_path + "/movPat/internet/clasificacion/generarCitaPdf/" + idSolicitud;	
	
	var request = $.ajax({
		url : sSource,
		type : "POST",
		//data : form_data2,
		dataType : "json",
		processData: false,
		cache: false,
		contentType: false,
	});
	request.done(responseImprimeCita);
	request.fail(responseImprimeCita);
}	

function responseImprimeCita(data) {
	$.unblockUI();
	console.log('Entrando a callbakc imprimir cita', data);
	
	if(data.pdfCita != null){
		downloadPDFCita(data.pdfCita, data.idSolicitud, "cita_", enabledFinalizaInternet);
	} else {
		construirDialogoMensajesMovPat("Error", data.mensaje, true, undefined, undefined, undefined);
	}	
	//parent.startEncuesta(500);		
}

function enabledFinalizaInternet() {
	$("#idModalDialogoGenerarCita").css("z-index", "1022");
	$("#btnFinalizarInternet").removeAttr("disabled");
	objReporte.dialog('close');
}

function cerrarWizardInternet(){
	//startEncuestaHC(500,'IMSS-02-008');
	cerrarWizard();
}
	
function isEmpty(temp) {
	return (temp == undefined || temp == "");
}

function isValidDate(d) {
	if (Object.prototype.toString.call(d) !== "[object Date]") {
		return false;
	}
	return !isNaN(d.getTime());
}






function prefinalizarClasificacion() {
	console.log("::: En prefinalizarClasificacion() ::::::::: Es origen Internet " + origenInternet);
	var errors = evaluarClasificacion();
	console.log("::: En prefinalizarClasificacion errores", errors);
	console.log("::: En prefinalizarClasificacion codigo Tramite", codigoTramite);
	if (errors == "") {		
		if(codigoTramite == 11 || codigoTramite == 12 
			|| codigoTramite == 13 || codigoTramite == 14 
			|| codigoTramite == 15 || codigoTramite == 16 
			|| codigoTramite == 17 || codigoTramite == 18
			|| codigoTramite == 19 || codigoTramite == 20
			|| codigoTramite == 21 || codigoTramite == 22
		    || codigoTramite == 175 || codigoTramite == 176
		){
			if(codigoTramite == 176){
				console.log("::: Mensaje por tramite de cambio de domicilio diferente municipio");
				var msj = 'Este tr\u00E1mite solo modificar\u00E1 la clase, fracci\u00f3n y prima.<br>' + 'Es su responsabilidad realizar el tr\u00E1mite de baja del Registro Patronal del domicilio anterior.';		
				construirDialogoAceptarCancelar(msj, consultarSolicitudSimilar, callbackTramiteCancelar, 250, 400);	
			}else{
				consultarSolicitudSimilar();	
			}
		}else{
			evaluarReglasClasificacion();
		}				
	} else {
		construirDialogoErrores(errors);
	}
}

function consultarSolicitudSimilar() {
console.log("::: Se evalua si existen tramites similares");
	var nrp = $('#numeroRegistroPatronal').val();
	var fechaSurteEfecto = $("#fechaEfecto").val();	
	var form_data2 = new FormData();
	console.log(":::En consultarSolicitudSimilar(), nrp: " + nrp + ", cveIdTipoTramite: " + codigoTramite 
		+ ", fechaSurteEfecto: " + fechaSurteEfecto);
	form_data2.append("nrp", nrp);
	form_data2.append("cveIdTipoTramite", codigoTramite);
	form_data2.append("fechaSurteEfecto", fechaSurteEfecto);
	
	var urlSolicitudSimilar = context_path + "/movPat/clasificacion/consulta/solicitud/similar";
	if(origenInternet){
		urlSolicitudSimilar = context_path + "/movPat/internet/clasificacion/consulta/solicitud/similar";
	}
	
	$.ajax({
		url: urlSolicitudSimilar,
		type: 'post',
		dataType: 'json',
		processData: false,
		cache: false,
		contentType: false,
		data: form_data2,
		success: function(data) {
			if (data == "error" || data == "") {
				console.log("::: No se encontraron solicitudes similares, se procede con la firma");
				evaluarReglasClasificacion();
			} else {
				console.log("::: Se encontraron las siguientes solicitudes similares a la actual");
				console.log("::: " + data);		
				//guardamos el folio de las solicitudes similares para pasar a improcedente en MAC II
				if (clasificacion == undefined || clasificacion == null) {
					console.log("::: Creando objeto clasificacion, en consultarSolicitudSimilar");
					clasificacion = new Object();
				}	
				console.log("::: Se mostrara mensaje de confirmacion de solicitudes similares");
				clasificacion.indSolSimilares = data;
				var msj = "Ya se tiene registrado un tr&aacute;mite, con las mismas caracter&iacute;sticas "+data+". \u00bfDeseas continuar?";
				construirDialogoAceptarCancelarSimilar(msj, evaluarReglasClasificacion, callbackTramiteCancelar, 250, 400);			
			}
		},
		error: function(error) {
			console.log("::: Error interno al consultar las solicitudes similares");
			construirDialogoMensajes("ERROR", "\u00A1Error\u0021 ".bold() + "Ocurrio un error inesperado", true);
		}
	});
}


function callbackTramiteCancelar(){
	console.log("::: En callbackTramiteCancelar, se cierra el dialogo ");
}	

function evaluarReglasClasificacion(){
	console.log("::: En evaluarReglasClasificacion()");
	generarObjetoClasificacion();
	$.blockUI();
	if(clasificacion.sujetoObligado.municipioIMSS==undefined)
		clasificacion.sujetoObligado.municipioIMSS=new Object();

	clasificacion.sujetoObligado.municipioIMSS.idMunicipio=idMunicipioIMSS;
	if (tipoPersonaFiscal == "MORAL") {
		clasificacion.sujetoObligado.moral = new Object();
		clasificacion.sujetoObligado.moral.idPersona = idPersona;
		clasificacion.sujetoObligado.moral.rfc=rfcSujetoObligado;
	} else {
		clasificacion.sujetoObligado.fisica = new Object();
		clasificacion.sujetoObligado.fisica.idPersona = idPersona;
		clasificacion.sujetoObligado.fisica.cveFisica=idPersonaFisica;
		clasificacion.sujetoObligado.fisica.rfc=rfcSujetoObligado;
	}

	if(mostrarCentroTrabajo){
		clasificacion.sujetoObligado.cntroTrabajo=construirObjectoCentroTrabajo(idSujetoObligado);
		clasificacion.sujetoObligado.subdelegacion=new Object();
		clasificacion.sujetoObligado.subdelegacion.delegacion=new Object();
		clasificacion.sujetoObligado.municipioIMSS=new Object();

		clasificacion.sujetoObligado.subdelegacion.id=$("#municipioIMSS\\.subdelegacion\\.id").val();
		clasificacion.sujetoObligado.subdelegacion.clave=$("#municipioIMSS\\.subdelegacion\\.clave").val();
		clasificacion.sujetoObligado.subdelegacion.descripcion=$("#municipioIMSS\\.subdelegacion\\.descripcion").val();

		clasificacion.sujetoObligado.subdelegacion.delegacion.id=$("#municipioIMSS\\.subdelegacion\\.delegacion\\.id").val();
		clasificacion.sujetoObligado.subdelegacion.delegacion.clave=$("#municipioIMSS\\.subdelegacion\\.delegacion\\.clave").val();
		clasificacion.sujetoObligado.subdelegacion.delegacion.descripcion=$("#municipioIMSS\\.subdelegacion\\.delegacion\\.descripcion").val();
		clasificacion.sujetoObligado.subdelegacion.delegacion.ciz=$("#municipioIMSS\\.subdelegacion\\.delegacion\\.ciz").val();

		clasificacion.sujetoObligado.municipioIMSS.idMunicipio=$("#municipioIMSS\\.idMunicipio").val();
		clasificacion.sujetoObligado.municipioIMSS.cvecMunicipioSINDO=$("#municipioIMSS\\.cvecMunicipioSINDO").val();
		clasificacion.sujetoObligado.municipioIMSS.descMunicipio=$("#municipioIMSS\\.descMunicipio").val();
	}	
	
	var sSource = context_path + '/movPat/clasificacion/validarClasificacion?idTipoTramite='+identificadorTramite;
	if(origenInternet){
		sSource = context_path + '/movPat/internet/clasificacion/validarClasificacion?idTipoTramite='+identificadorTramite;
	}
	prepararRequest(sSource, clasificacion, false, callbackEvaluarReglasClasificacion);
	
}

function callbackEvaluarReglasClasificacion(response){
	if(response.error){
		if (response.mensajeError != undefined
				&& response.mensajeError != null) {
			titulo = "Clasificaci\u00F3n invalida";
			error = true;
			if (response.mensajeError == "") {
				mensaje = "Ocurrio un error con el servidor.";
			} else {
				mensaje = response.mensajeError;
			}
			construirDialogoMensajes(titulo, mensaje, error, undefined, 200, 600);
		}
	}else{
		//invocarFirmaDigital();
//		construirDialogoConfirmar('Finalizar',mostrarFirma);
		mostrarFirma();
	}
}

function invocarFirmaDigital(){
	console.log("::: Invocando  firma digital para el tramite: " + codigoTramite + "-" + descripcionTipoSolicitud);
	var num_rp = parent.WizardModificacionPatronClasificacionCtrl.config.numeroRegistroPatronal;
	var descTipSol = descripcionTipoSolicitud;
	descTipSol = adecuarDescTramite(descTipSol);
	parent.AtributosPersonaCtrl.personaPortal.registroPatronal = num_rp;

	var tipoAcuse = mostrarCentroTrabajo ? 'CDCT' : 'IA';
	
	var minArch = 1;
	var maxArch = 3;
	
	if(codigoTramite == 12){ // para el tramite Cambio por disposicion de Ley, o del RACERF
		console.log("::: Para el tramite Cambio por disposicion de Ley, no se solicitan documentos");
		minArch = 0;
		maxArch = 0;		
	}
	
	var componenteFirma = {
			idTipoSolicitud : codigoTipoSolicitud,
			descripcionTipoSolicitud : descTipSol,
			idTipoTramite : arrayCodigoTipoTramite,
			folioSolicitud : $('#hdnFolioSolicitud').val(),
			curp : datosEntradaFirma.curp,
			rfc : datosEntradaFirma.rfc,
			validarRFC : true,
			registroPatronal : num_rp,
			nombreCompleto : datosEntradaFirma.nombreCompleto,
			fechaElectronica : datosEntradaFirma.fechaElectronica,
			cad_original : $('#contenidoFirmar').val().replace(/"/g, '\\"'),
			tipo_operacion : 'firmaCMS',
			firma_archivo : true,
			min_archivos : minArch,
			max_archivos : maxArch,
			afectado: [parent.AtributosPersonaCtrl.personaPortal],
			acuse: tipoAcuse,
			tipoAcuse: '1'

		};

    //Se quita caracter de escape en caso de que venga ya en el nombre
    parent.AtributosPersonaCtrl.personaPortal.nombreRazonSocial = parent.AtributosPersonaCtrl.personaPortal.nombreRazonSocial
            .replace(/\\/g, '');

    // Se escapan las comillas dobles
    componenteFirma.afectado[0].nombreRazonSocial = parent.AtributosPersonaCtrl.personaPortal.nombreRazonSocial
            .replace(/\"/g, '\\"');

	parent.iniciarFirmaDigital(componenteFirma);
}

function adecuarDescTramite(cadena) {
	cadena = cadena.toUpperCase();
	cadena = cadena.replace(/&AACUTE;/g, "A");
	cadena = cadena.replace(/&EACUTE;/g, "E");
	cadena = cadena.replace(/&IACUTE;/g, "I");
	cadena = cadena.replace(/&OACUTE;/g, "O");
	cadena = cadena.replace(/&UACUTE;/g, "U");
	cadena = cadena.replace(/�/gi, "A");
	cadena = cadena.replace(/�/gi, "E");
	cadena = cadena.replace(/�/gi, "I");
	cadena = cadena.replace(/�/gi, "O");
	cadena = cadena.replace(/�/gi, "U");
	return cadena;
}

function premostrarFirma() {
	console.log("::: En premostrarFirma");
	var errors = evaluarClasificacion();

	if (errors == "") {
		construirDialogoConfirmar('Finalizar',mostrarFirma);
	} else {
		construirDialogoErrores(errors);
	}
}

function marcarErroresDomicilioCentro(error) {
	var cssDisplay = error ? "block" : "none";
	var cssBorder = error ? "1px solid red" : "1px solid #ccc";
	var cssColor = error ? "red" : "black";

	$("#domicilioCentroTrabajoForm").find(".required").each(function(){
		this.style.color=cssColor;
	});

	$("#domicilioCentroTrabajoForm").find(".error").each(function(){
		this.style.display=cssDisplay;
		this.innerHTML = "Este campo es obligatorio";
	});

	$("#domicilioCentroTrabajoForm").find(".campoObligatorio").each(function(){
		this.style.border = cssBorder;
	});

}

function evaluarCentroTrabajo() {
	var temp = "";
	var errors = "";
	var existeError = false;
	var $elementoValidando = null;
	var errorCampoObligarorio = "Este campo es obligatorio";

	temp = $("#cntroTrabajo\\.vialidadPrimaria\\.nombre").val();
	if (isEmpty(temp)) {
		errors += "* Los datos de la nueva direcci&oacute;n del centro de trabajo son requeridos. <br/>";
		setCampoToFocusOn("#cntroTrabajo\\.vialidadPrimaria\\.nombre");
		marcarErroresDomicilioCentro(true);
	} else {
		marcarErroresDomicilioCentro(false);
	}

	var telFijoA = $("#ctTelefonoFijo").val();
	var ladaB = $("#ctLada2").val();
	var telFijoB = $("#ctTelefonoFijo2").val();
	var extB = $("#ctExtension2").val();

	$elementoValidando = $("#ctTelefonoFijo");
	if (isEmpty(telFijoA)) {
		existeError = true;
		errors += "* Debe proporcionar un tel&eacute;fono fijo (Principal) para el nuevo domicilio. <br/>";
		setCampoToFocusOn("#ctTelefonoFijo");
	}
	mostrarMensajeErrorMovPat($elementoValidando, existeError , errorCampoObligarorio);
	existeError = false;


	if(!isEmpty(ladaB) && isEmpty(telFijoB)){
		errors += "* Debe proporcionar un tel&eacute;fono fijo (Secundario) v&aacute;lido para el nuevo domicilio. <br/>";
		setCampoToFocusOn("#ctTelefonoFijo2");
	}else if(!isEmpty(extB) && isEmpty(telFijoB)){
		errors += "* Debe proporcionar un tel&eacute;fono fijo (Secundario) v&aacute;lido para el nuevo domicilio. <br/>";
		setCampoToFocusOn("#ctTelefonoFijo2");
	}

	$elementoValidando = $("#ctCorreoElectronico");
	temp = $elementoValidando.val();
	if (isEmpty(temp)) {
		existeError=true;
		errors += "* Debe proporcionar un correo electr&oacute;nico para el nuevo domicilio. <br/>";
		setCampoToFocusOn("#ctCorreoElectronico");
	}else if(!fnValidaCorreo(temp)){
		existeError=true;
		errorCampoObligarorio="Formato inv&aacute;lido."
		errors += "* Debe proporcionar un correo electr&oacute;nico v&aacute;lido para el nuevo domicilio. <br/>";
		setCampoToFocusOn("#ctCorreoElectronico");
	}
	mostrarMensajeErrorMovPat($elementoValidando, existeError , errorCampoObligarorio);
//	temp = $("#municipioIMSS\\.subdelegacion\\.id").val();
//	if (isEmpty(temp)) {
//		errors += "* Debe proporcionar la subdelegaci&oacute;n del centro de trabajo. <br/>";
//		setCampoToFocusOn("#municipioIMSS\\.subdelegacion\\.id");
//	}

	if($("input[name='idMunicipioImssRadio']:checked").val()== undefined){
		errors += "* Debe proporcionar la subdelegaci&oacute;n y municipio del centro de trabajo. <br/>";
		setCampoToFocusOn("#cntroTrabajo\\.codigoPostal\\.codigoPostal");
//		construirDialogoMensajes("Municipio requerido", "Por favor la subdelegaci\u00F3n y municipio.", true, undefined, 200, 500 );
	}


	pintarErrorGeneral(errors != "");
	return errors;
};

var evaluarClasificacion = function() {
	console.log("::: En evaluarClasificacion(), validando datos");
	var errors = "";
	var temp = "";
	var $elementoValidando = null;
	var errorCampoObligarorio = "Este campo es obligatorio";

	if(mostrarCentroTrabajo){
		errors += evaluarCentroTrabajo();
	}

	//$("#fechaEfecto").attr("style", "width: 100px; border-color: black !important;");

	//if (esOperador) {
		$elementoValidando = $("#fechaEfecto");
		temp = $elementoValidando.val();
		var errorEnCampoFecha = isEmpty(temp);
		//if (errorEnCampoFecha) {
			//errors += "Por favor seleccione una fecha o teclee una fecha valida DD/MM/YYYY.. <br/>";
			//setCampoToFocusOn("#fechaPresentacion");
			//fechaEfectoDatePicker.datepicker('show');
			// runEffectFechaEfectoInvalidaMsg();
		//}

		mostrarMensajeErrorMovPat($elementoValidando, errorEnCampoFecha , errorCampoObligarorio);
		//temp = dateFormat(temp, "dd/mm/yyyy");//new Date(temp);

		var tempDate = new Date(temp.replace(/(\d{2})\/(\d{2})\/(\d{4})/,'$2/$1/$3'));
		//console.debug(temp);
		if (isValidDate(tempDate)) {
			var fechaMinima = new Date();
			fechaMinima.setDate(fechaMinima.getDate() - minDate - 1);
			var auditoriaFecha=$('input#auditoria').filter(":checked").val();
			if (fechaMinima > tempDate) {
				// errors += "No puedes seleccionar un dÃ­a menor a
				// "+minDate+" con respescto al d&iacute;a actual. <br/>";
				if(mostrarCentroTrabajo)
					errors += "* La fecha a partir de la cual surte efecto no puede ser anterior a 5 d\u00EDas de la fecha actual. <br/>";
				else if(identificadorTramite==idTipoTramiteAlta){
					if(auditoriaFecha==undefined){


					errors += "* La fecha a partir de la cual surte efecto no puede ser anterior a 365 d\u00EDas de la fecha actual. <br/>";
					}
				}
				else
					errors += "* La fecha a partir de la cual surte efecto no puede ser anterior a 365 d\u00EDas de la fecha actual. <br/>";


				$("#fechaEfecto").attr("style",
						"width: 100px; border-color: red !important;");
				setCampoToFocusOn("#fechaEfecto");
			}
			var fechaActual = new Date();
			if(tempDate > fechaActual){
				errors += "* La fecha a partir de la cual surte efecto no puede ser posterior a la fecha actual. <br/>";
				$("#fechaEfecto").attr("style",
						"width: 100px; border-color: red !important;");
				setCampoToFocusOn("#fechaEfecto");
			}
		} else {
			errors += "* Por favor selecione una fecha o teclee una fecha v&aacute;lida dd/mm/aaaa. <br/>";
			$("#fechaEfecto").attr("style",
					"width: 100px; border-color: red !important;");
			setCampoToFocusOn("#fechaEfecto");
		}

	//}
	var errorEnCampo = false;
	var descripcionError = "";
	$elementoValidando =  $("#giroClasificacion");
	temp = $("#giroClasificacion").val();
	if (isEmpty(temp)) {
		errors += "* El giro es un campo obligatorio. <br/>";
		descripcionError = errorCampoObligarorio;
		setCampoToFocusOn("#giroClasificacion");
		errorEnCampo = true;
	} else {
		if (temp.length > 300) {
			descripcionError = "Maximo 300 caract\u00E9res."
			errors += "* El giro no puede exceder de 300 caract\u00E9res. <br/>";
			setCampoToFocusOn("#giroClasificacion");
			errorEnCampo = true;
		}
	}

	mostrarMensajeErrorMovPat($elementoValidando, errorEnCampo , descripcionError);

	errorEnCampo = false;
	descripcionError = errorCampoObligarorio;
	$elementoValidando = $("#clasificacion\\.indPrestaServicioPersonal1");
	var tempIndPsp = $elementoValidando.is(':checked');
	if(!tempIndPsp){
		tempIndPsp = $("#clasificacion\\.indPrestaServicioPersonal2").is(':checked');
		if(!tempIndPsp){
			// Para tramites de clasificacion no debe aplicar Mm WO1610045/4351808
			if(codigoTramite == 11 || codigoTramite == 12 
				|| codigoTramite == 13 || codigoTramite == 14 
				|| codigoTramite == 15 || codigoTramite == 16 
				|| codigoTramite == 17 || codigoTramite == 18
				|| codigoTramite == 19 || codigoTramite == 20
				|| codigoTramite == 21 || codigoTramite == 22
			    || codigoTramite == 175 || codigoTramite == 176
			){
//				console.log("::: Se cambia mensaje de error en ModSRT por tramite de clasificacion");
//				errors += "* El campo presta servicios u obra especializada es obligatorio. <br/>";
				console.log("::: No aplica error PSP en ModSRT por tramite de clasificacion");
			}else{
				errors += "* El campo presta servicios especializados es obligatorio. <br/>";
				setCampoToFocusOn("#clasificacion\\.indPrestaServicioPersonal1");
				errorEnCampo = true;
			}
		}
	}
	mostrarMensajeErrorMovPat($elementoValidando, errorEnCampo , descripcionError);

	errorEnCampo = false;
	descripcionError = errorCampoObligarorio;
	$elementoValidando = $("#gridClasificacionNueva");
	if (isEmpty($("#fraccion").val()) || isEmpty($("#grupo").val())
			|| isEmpty($("#divison").val())) {
		errorEnCampo = true;
		errors += "* La clasificaci&oacute;n es un campo obligatorio. <br/>";
		setCampoToFocusOn("#clasificacionSelector");
	}	
	mostrarMensajeErrorMovPat($elementoValidando, errorEnCampo , descripcionError);

	errorEnCampo = false;
	descripcionError = errorCampoObligarorio;
	$elementoValidando =  $("#procesoInicial");
	var temp = $elementoValidando.val();
	if (isEmpty(temp)) {
		errorEnCampo = true;
		errors += "* Los procesos iniciales son un campo obligatorio. <br/>";
		setCampoToFocusOn("#procesoInicial");
	} else if (temp.length > 600) {
		errorEnCampo = true;
		descripcionError = "El campo debe tener m&iacute;nimo 600 caracteres."
		errors += "* El campo procesos iniciales no puede exceder de 600 caracteres. <br/>";
		setCampoToFocusOn("#procesoInicial");
	}

	mostrarMensajeErrorMovPat($elementoValidando, errorEnCampo , descripcionError);
	errorEnCampo = false;
	descripcionError = errorCampoObligarorio;
	$elementoValidando =  $("#procesoIntermedio");
	var temp = $elementoValidando.val();
	if (isEmpty(temp)) {
		errorEnCampo = true;
		errors += "* Los procesos intermedios son un campo obligatorio. <br/>";
		setCampoToFocusOn("#procesoIntermedio");
	}else if (temp.length > 600) {
		errorEnCampo = true;
		descripcionError = "El campo debe tener m&iacute;nimo 600 caracteres."
		errors += "* El campo procesos intermedios no puede exceder de 600 caracteres. <br/>";
		setCampoToFocusOn("#procesoIntermedio");
	}

	mostrarMensajeErrorMovPat($elementoValidando, errorEnCampo , descripcionError);
	errorEnCampo = false;
	descripcionError = errorCampoObligarorio;
	$elementoValidando =  $("#procesoFinal");
	var temp = $elementoValidando.val();
	if (isEmpty(temp)) {
		errorEnCampo = true;
		errors += "* Los procesos finales son un campo obligatorio. <br/>";
		setCampoToFocusOn("#procesoFinal");
	}else if (temp.length > 600) {
		errorEnCampo = true;
		descripcionError = "El campo debe tener m&iacute;nimo 600 caracteres."
		errors += "* El campo procesos finales no puede exceder de 600 caracteres. <br/>";
		setCampoToFocusOn("#procesoFinal");
	}
	mostrarMensajeErrorMovPat($elementoValidando, errorEnCampo , descripcionError);

	errorEnCampo = false;
	descripcionError = errorCampoObligarorio;
	$elementoValidando = $("#gridProductosServicios");
	if (grid['producto'].fnSettings().aoData.length <= 0) {
		errors += "* Es necesario agregar por lo menos un producto/servicio. <br/>";
		setCampoToFocusOn("#btnAgregarProducto");
		errorEnCampo = true;
	}

	mostrarMensajeErrorMovPat($elementoValidando, errorEnCampo , descripcionError);
	errorEnCampo=false;
	$elementoValidando = $("#gridMaeriasMateriales");
	if (grid['material'].fnSettings().aoData.length <= 0) {
		errorEnCampo=true;
		errors += "* Es necesario agregar por lo menos una materia prima/material. <br/>";
		setCampoToFocusOn("#btnAgregarMateriales");
	}

	mostrarMensajeErrorMovPat($elementoValidando, errorEnCampo , descripcionError);
	errorEnCampo=false;
	$elementoValidando = $("#gridMaquinariaEquipo");
	if (grid['equipo'].fnSettings().aoData.length <= 0) {
		errorEnCampo=true;
		errors += "* Es necesario agregar por lo menos un equipo/maquinaria. <br/>";
		setCampoToFocusOn("#btnAgregarMaquinariaEquipo");
	}

	mostrarMensajeErrorMovPat($elementoValidando, errorEnCampo , descripcionError);
	errorEnCampo=false;
	$elementoValidando = $("#gridPersonal");
	if (grid['personal'].fnSettings().aoData.length <= 0) {
		errorEnCampo=true;
		errors += "* Es necesario agregar por lo menos un grupo de personal. <br/>";
		setCampoToFocusOn("#btnAgregarPersonal");
	}
	mostrarMensajeErrorMovPat($elementoValidando, errorEnCampo , descripcionError);

	temp = $("#siCuentaConTransporte").is(':checked');
	if (temp) {
		if (grid['personal'].fnSettings().aoData.length <= 0) {
			errors += "* Es necesario agregar por lo menos un equipo de transporte. <br/>";
			setCampoToFocusOn("#agregarTransporte");
		}
		if (!$('#indTransportePropioTemp').is(':checked')
				&& !$('#indTransporteAjenoTemp').is(':checked')) {
			errors += "* Es necesario indicar la distribuci&oacute;n o entrega de mercanc&iacute;as. <br/>";
			setCampoToFocusOn("#indTransportePropioTemp");
		}
	}
	if ($('#siCuetaConTransporte').attr("checked")) {
		if (grid["transporte"] != null
				&& $(grid["transporte"].fnSettings().aoData).length <= 0) {
			errors += "* Debe agregar por lo menos un equipo de transporte. <br/>";
			setCampoToFocusOn("#agregarTransporte");
		} else if (!$('#indTransportePropioTemp').is(':checked')
				&& !$('#indTransporteAjenoTemp').is(':checked')) {
			errors += "* Es necesario indicar si cuentas con transporte propio o ajeno. <br/>";
			setCampoToFocusOn("#indTransportePropioTemp");
		} else if ($('#indTransportePropioTemp').is(':checked')
				&& $('#indTransporteAjenoTemp').is(':checked')) {
			if (grid["transporte"] != null
					&& $(grid["transporte"].fnSettings().aoData).length == 1) {
				var data = grid["transporte"].fnSettings().aoData;
				var registro = data[0]._aData;
				if (registro["numUnidades"] == 1) {
					// errors += "Una sola unidad de transporte no puede ser
					// propia y ajena, seleccione uno solo indicador. <br/>";
					errors += "* Una sola unidad de transporte no se puede asignar a transporte propio y transporte ajeno a la vez, seleccione s\u00F3lo un indicador. <br/>";
					setCampoToFocusOn("#indTransportePropioTemp");
				}
			}
		}
	}

	if(mostrarBienes){
		errorEnCampo=false;
		$elementoValidando = $("#gridBienes");
		if (grid['bienes'].fnSettings().aoData.length <= 0) {
			errorEnCampo=true;
			errors += "* Es necesario agregar por lo menos un bien mueble o inmueble. <br/>";
			setCampoToFocusOn("#btnAgregarBienes");
		}
		mostrarMensajeErrorMovPat($elementoValidando, errorEnCampo , descripcionError);

		errorEnCampo=false;
		descripcionError = errorCampoObligarorio;
		$elementoValidando =  $("#afectacionBienes");
		temp = $elementoValidando.val();
		if (isEmpty(temp)) {
			errorEnCampo=true;
			errors += "* Uso de bien mueble o inmueble es campo requerido. <br/>";
			setCampoToFocusOn("#afectacionBienes");
		}

		mostrarMensajeErrorMovPat($elementoValidando, errorEnCampo , descripcionError);

		errorEnCampo=false;
		descripcionError = errorCampoObligarorio;
		$elementoValidando =  $("#usoBienes");
		temp = $elementoValidando.val();
		if (isEmpty(temp)) {
			errorEnCampo=true;
			errors += "* Afectaci\u00F3n de bien mueble o inmueble es campo requerido. <br/>";
			setCampoToFocusOn("#usoBienes");
		}
		mostrarMensajeErrorMovPat($elementoValidando, errorEnCampo , descripcionError);

	}
	
	function mostrarMensajeErrorMovPat($campo, error , mensaje) {
		var cssDisplay = error ? "block" : "none";
		var cssBorder = error ? "1px solid red" : "1px solid #ccc";
		var cssColor = error ? "red" : "black";
		
		if($campo != undefined) {
			var idCampo = $campo.attr("id");
			
			
			var tipoElemento = $campo.prop("tagName");
			var idSpanRequired = $campo.attr("spanRequired");
			var idSpanError = $campo.attr("spanError");
			//console.log("el id del campo es: " + idCampo + " y es un " + tipoElemento + " su spanRequerido es: " + idSpanRequired + " su spanError es: " + idSpanError);
			var valorCampo = $campo.val();
			
			if(tipoElemento != "table") {
				//ponemos el campo en rojo
				$campo.css("border",cssBorder);
			}
			//se obtiene asi para evitar escapar 
			var spanError = document.getElementById(idCampo+"Error");
			var campoRequired = document.getElementById(idCampo + "Req");
			
			if(spanError != undefined && spanError != null) {
				//console.log("se encontro el span de error y su id es: " + spanError.id );
				spanError.style.display = cssDisplay;
				
				if((mensaje != undefined || mensaje != null) && error) {
					spanError.innerHTML = mensaje;
				} else {
					spanError.innerHTML = "";
				}
			}
			
			if(campoRequired != undefined && campoRequired != null) {
				//console.log("se encontro el campo required y su id es: " + campoRequired.id);
				campoRequired.style.color = cssColor;
			}
			
		}
	}

	if(mostrarFusionSustitucion) {
		errorEnCampo = false;
		descripcionError = errorCampoObligarorio;
		$elementoValidando =  $("#primaSRTFusionSust");
		var temp = $elementoValidando.val();
		if (isEmpty(temp)) {
			errorEnCampo = true;
			errors += "* La prima de las empresa fusionada o sustituida es un campo obligatorio. <br/>";
			setCampoToFocusOn("#primaFusion");
		} else if(parseFloat(temp) < 0.5 || parseFloat(temp) > 15.0){
			errorEnCampo=true;
			errors +="* El valor de la prima no puede ser menor a 0.5 y no puede ser mayor a 15.0. <br/> ";
			descripcionError = "El valor de la prima no puede ser menor a 0.5 y no puede ser mayor a 15.0 "
		}
		mostrarMensajeErrorMovPat($elementoValidando, errorEnCampo , descripcionError);

		errorEnCampo = false;
		descripcionError = errorCampoObligarorio;
		$elementoValidando =  $("#numeroRegistroPatronalBusqueda");
		var temp = $elementoValidando.val(),
		patronesUbicados = $("#contenedorComponenteBusquedaPatrones").busquedaRps("get");
		if (patronesUbicados == null || patronesUbicados.length == 0) {
			errorEnCampo = true;
			errors += "* El Registro Patronal a sustituir o fusionar es un campo obligatorio. <br/>";
			setCampoToFocusOn("#numeroRegistroPatronalBusqueda");
		}
		mostrarMensajeErrorMovPat($elementoValidando, errorEnCampo , descripcionError);
	}else if(codigoTramite == 11 || codigoTramite == 12 
			|| codigoTramite == 13 || codigoTramite == 14 
			|| codigoTramite == 15 || codigoTramite == 16
		    || codigoTramite == 17 || codigoTramite == 18
			|| codigoTramite == 19 || codigoTramite == 22
	){
		 // sino es tramite de fusion/sustitucion se valida que se tenga el valor del campo de prima
		errorEnCampo = false;
		descripcionError = errorCampoObligarorio;
		$elementoValidando =  $("#primaSRTRestoTramites");
		var temp = $elementoValidando.val();
		console.log("::: En evaluarClasificacion() Primaaa ", temp)
		if (isEmpty(temp)) {
			errorEnCampo = true;
			errors += "* La prima de las empresa es un campo obligatorio. <br/>";
			setCampoToFocusOn("#primaRestoTramites");
		} else if(parseFloat(temp) < 0.5 || parseFloat(temp) > 15.0){
			errorEnCampo=true;
			errors +="* El valor de la prima no puede ser menor a 0.5 y no puede ser mayor a 15.0. <br/> ";
			descripcionError = "El valor de la prima no puede ser menor a 0.5 y no puede ser mayor a 15.0 "
		}
		mostrarMensajeErrorMovPat($elementoValidando, errorEnCampo , descripcionError);		
	}

	if(codigoTramite == 175){
		var numDoc = $('#numDoc').val();
		console.log("Numero de docs adjuntos: " + numDoc);
		if ( numDoc == "0") {
			errors += "* Se deben adjuntar los documentos de soporte del tramite. <br/>";
			setCampoToFocusOn("#seccionDocAdjuntos");
		}
	}
	
	
	
	//Validacion Check documentos requeridos
	documentoSize = $("#documentosRequeridos").val();
	errorEnCampo = false;
	descripcionError = errorCampoObligarorio;
	//$elementoValidando = $("#documentosRequeridosCheck");
	
	var selected = [];
	$('#divDocumentosRequeridos input:checked').each(function() {
    selected.push($(this).attr('id'));
	});
	if (documentoSize != undefined && documentoSize != null){
		if(documentoSize != selected.length){
			console.log("::: Validando Documentos Requeridos");
			errors += "* El campo documentos requeridos es obligatorio. <br/>";
			setCampoToFocusOn("#divDocumentosRequeridos");
			errorEnCampo = true;
		}
	}
	mostrarMensajeErrorMovPat($elementoValidando, errorEnCampo , descripcionError);
		
	

	if(errors != "") {
		pintarErrorGeneral(true);
	} else {
		pintarErrorGeneral(false);
	}
	

	quitarErrorPrimaSRT();
	quitarErrorCampoNRP();
		
	return errors;
}

function quitarErrorPrimaSRT() {
	$elementoValidando =  $("#primaSRTRestoTramites");
	var temp = $elementoValidando.val();
	
	if (!isEmpty(temp)) {
		$("#primaSRTRestoTramitesError").hide();
		$elementoValidando.attr("style","border: 1px solid #ccc;");
	}	
}

function quitarErrorCampoNRP() {
	if ( mostrarFusionSustitucion || codigoTramite == 176) {
		patronesUbicados = $("#contenedorComponenteBusquedaPatrones").busquedaRps("get");
		if (patronesUbicados != null && patronesUbicados.length > 0) {
			$("#numeroRegistroPatronalBusquedaError").hide();
			$("#numeroRegistroPatronalBusqueda").attr("style","border: 1px solid #ccc;");
		}
	}
}

function quitarErrorCampoPatrones(clasesIguales) {
	console.log("Las clases son iguales " + clasesIguales);
	if($("#datosCalculoPrima").length) {
		PrimaCtrl.mostrarSeccionPrima(clasesIguales);
	}
	mostrarMensajeErrorMovPat($("#numeroRegistroPatronalBusqueda"), false , "");
}

function cancelarSolicitud() {
	enviarClasificacion("Cancelar");
}

function enviarClasificacion(accion, history) {
	generarObjetoClasificacion();
	$.blockUI();
	if (history) {
		sendToServer("clasificacion", accion, clasificacion,
				callbackHistoryBack);
	} else {
		sendToServer("clasificacion", accion, clasificacion,
				callbackEnviarClasificacion);
	}
}

function enviarClasificacionPrevioRevisar(accion) {
	generarObjetoClasificacion();
	$.blockUI();
	sendToServer("clasificacion", accion, clasificacion,
				revisarSRT);
}

function inhabilitarPSPRCP() {
	if ($('#indProductorCana').is(":checked")) {
		$('#indRegPatClase').attr("disabled", "disabled");
		$('#clasificacion\\.indPrestaServicioPersonal2').attr("checked", true);
		$('#clasificacion\\.indPrestaServicioPersonal1').attr("disabled", true);
		$('#clasificacion\\.indPrestaServicioPersonal2').attr("disabled", true);
		$('#indRegPatClase').attr("checked", false);

	} else {
		$('#indRegPatClase').removeAttr("disabled");
		$('#clasificacion\\.indPrestaServicioPersonal1').removeAttr("disabled");
		$('#clasificacion\\.indPrestaServicioPersonal2').removeAttr("disabled");

	}
}

function generarObjetoClasificacion() {
	console.log("::: En generarObjetoClasificacion()");
	if (clasificacion == undefined || clasificacion == null) {
		console.log("::: Se crea objeto de clasificacion en generarObjetoClasificacion()");
		clasificacion = new Object();
	}
	clasificacion.fraccion = new Object();
	clasificacion.fraccion.id = $("#fraccion").val();
	clasificacion.fraccion.numFraccion = $("#claveFraccion")[0].innerHTML;
	clasificacion.fraccion.descripcionDetallada = $("#textFraccion")[0].innerHTML
			.toUpperCase();
	clasificacion.fraccion.grupo = new Object();
	clasificacion.fraccion.grupo.id = $("#grupo").val();
	clasificacion.fraccion.grupo.numGrupo = $("#claveGrupo")[0].innerHTML
			.toUpperCase();
	clasificacion.fraccion.grupo.descripcion = $("#textGrupo")[0].innerHTML
			.toUpperCase();
	clasificacion.fraccion.grupo.division = new Object();
	clasificacion.fraccion.grupo.division.id = $("#divison").val();
	clasificacion.fraccion.grupo.division.numDivision = $("#claveDivision")[0].innerHTML
			.toUpperCase();
	clasificacion.fraccion.grupo.division.descripcion = $("#textDivison")[0].innerHTML
			.toUpperCase();
	clasificacion.fraccion.clase = new Object();

	//RPC
	clasificacion.indRegPatClase = 0;
	//Para aquellas pantallas donde se muestra checkbox
	if ($('#indRegPatClase').is(':checked')) {
		clasificacion.indRegPatClase = 1;
	}
	//Para aquellas pantallas donde se almacena RPC en atributo hidden con su valor como tal, 0 o 1.
	if($("#indRegPatClase").val()==1){
		clasificacion.indRegPatClase = 1;
	}

	clasificacion.fraccion.clase.clave = $("#claveClase").val();
	clasificacion.fraccion.clase.descripcion = $("#textClase")[0].innerHTML;
	if($("#textPrimaAnt")[0].innerHTML!=undefined && $("#textPrimaAnt")[0].innerHTML!=""){
		clasificacion.fraccion.primaSRT = $("#textPrimaAnt")[0].innerHTML;
		clasificacion.primaSRTActual=$("#textPrimaAnt")[0].innerHTML;
		//console.log("#textPrimaAnt: " + $("#textPrimaAnt")[0].innerHTML);
	}

	if ( codigoTramite == 20 || codigoTramite == 21 || codigoTramite == 175 || codigoTramite == 176) {
		if($("#primaSRTFusionSust").length) {
			console.log("::: Asignando primaSRTFusionSust: " + $("#primaSRTFusionSust").val());
			clasificacion.primaSRTFusionSust = $("#primaSRTFusionSust").val();
		}
	}

	if(codigoTramite == 11 || codigoTramite == 12 
		|| codigoTramite == 13 || codigoTramite == 14 
		|| codigoTramite == 15 || codigoTramite == 16
	    || codigoTramite == 17 || codigoTramite == 18
		|| codigoTramite == 19 || codigoTramite == 22
	){
		if($("#primaSRTRestoTramites").length) {
			console.log("Asignando primaSRTRestoTramites: " + $("#primaSRTRestoTramites").val());
			clasificacion.primaSRTSugerida = $("#primaSRTRestoTramites").val();
		}
	}

	var sujetoObligado = new Object();
	sujetoObligado.cveIdSujetoObligado = idSujetoObligado;
	var persona = new Object();
	persona.rfc = rfcSujetoObligado;
	if (tipoPersonaFiscal == "MORAL") {
		sujetoObligado.moral = persona;
	} else {
		sujetoObligado.fisica = persona;
	}
	sujetoObligado.tipoPersonaFiscal = tipoPersonaFiscal;
	var proceso = new Object();
	proceso.clave = $("#procesoClave").val();
	proceso.desInicial = $("#procesoInicial").val().toUpperCase();
	proceso.desIntermedio = $("#procesoIntermedio").val().toUpperCase();
	proceso.desFinal = $("#procesoFinal").val().toUpperCase();

	proceso.desInicial=$.trim(proceso.desInicial);
	proceso.desIntermedio=$.trim(proceso.desIntermedio);
	proceso.desFinal=$.trim(proceso.desFinal);

	sujetoObligado.proceso = proceso;
	sujetoObligado.desAfectacion = $("#usoBienes").val().toUpperCase();
	sujetoObligado.desUsosBienes = $("#afectacionBienes").val().toUpperCase();
	if ($('#siCuetaConTransporte').attr("checked")) {
		sujetoObligado.cuentaConTransporte = 1;
	} else {
		sujetoObligado.cuentaConTransporte = 0;
	}
	sujetoObligado.numeroRegistroPatronal = $('#numeroRegistroPatronal').val();
	sujetoObligado.modalidad=new Object();
	sujetoObligado.modalidad.idModalidad=$('#idModalidad').val();
	sujetoObligado.modalidad.descripcion=$('#descripcionModalidad').val();
	sujetoObligado.modalidad.numModalidad=$('#numModalidad').val();
	sujetoObligado.digVerificador=$('#digVerificador').val();

	clasificacion.sujetoObligado = sujetoObligado;
	clasificacion.id = idClasificacion;
	clasificacion.giro = replaceCaracteres($("#giroClasificacion").val().toUpperCase());

	var tempIndPsp = $("#clasificacion\\.indPrestaServicioPersonal1").is(':checked');
	if(!tempIndPsp){
		tempIndPsp = $("#clasificacion\\.indPrestaServicioPersonal2").is(':checked');
		if(!tempIndPsp){
			clasificacion.indPrestaServicioPersonal = undefined;
		}
	}
	if(tempIndPsp){
		clasificacion.indPrestaServicioPersonal = $(
			'input:radio[name=clasificacion\.indPrestaServicioPersonal]:checked')
			.val();
	}

	clasificacion.fecEfecto = $("#fechaEfecto").val();
	clasificacion.auditoria = $("#auditoria").is(':checked');
		console.log ("Valor campo: "+clasificacion.auditoria);
	if ($('#indTransportePropioTemp').is(':checked')) {
		clasificacion.indTransportePropio = 1;
	} else {
		clasificacion.indTransportePropio = 0;
	}
	if ($('#indTransporteAjenoTemp').is(':checked')) {
		clasificacion.indTransporteAjeno = 1;
	} else {
		clasificacion.indTransporteAjeno = 0;
	}
	if ($('#indNoDistribuyeTemp').is(':checked')) {
		clasificacion.indDistribuyeEntrega = 1;
	} else {
		clasificacion.indDistribuyeEntrega = 0;
	}
	if ($('#indServiciosTercerosTemp').is(':checked')) {
		clasificacion.indServiciosATerceros = 1;
	} else {
		clasificacion.indServiciosATerceros = 0;
	}
	if ($('#indProductorCana').is(':checked')) {
		clasificacion.indProductorCana = 1;
	} else {
		clasificacion.indProductorCana = 0;
	}

	if(mostrarCentroTrabajo){
		//Workaround para tener el valor del psp correcto pues el componente de municipio sobreescribe este valor con el valor del idMunicipio en todos los radios
		if($("#clasificacion\\.indPrestaServicioPersonal1").is(':checked')){
			clasificacion.indPrestaServicioPersonal = "1";
		}else if($("#clasificacion\\.indPrestaServicioPersonal2").is(':checked')){
			clasificacion.indPrestaServicioPersonal = "0";
		}else{
			clasificacion.indPrestaServicioPersonal = undefined;
		}

		clasificacion.sujetoObligado.cntroTrabajo=construirObjectoCentroTrabajo(idSujetoObligado);
		clasificacion.sujetoObligado.subdelegacion=new Object();
		clasificacion.sujetoObligado.subdelegacion.delegacion=new Object();
		clasificacion.sujetoObligado.municipioIMSS=new Object();

		clasificacion.sujetoObligado.subdelegacion.id=$("#municipioIMSS\\.subdelegacion\\.id").val();
		clasificacion.sujetoObligado.subdelegacion.clave=$("#municipioIMSS\\.subdelegacion\\.clave").val();
		clasificacion.sujetoObligado.subdelegacion.descripcion=$("#municipioIMSS\\.subdelegacion\\.descripcion").val();

		clasificacion.sujetoObligado.subdelegacion.delegacion.id=$("#municipioIMSS\\.subdelegacion\\.delegacion\\.id").val();
		clasificacion.sujetoObligado.subdelegacion.delegacion.clave=$("#municipioIMSS\\.subdelegacion\\.delegacion\\.clave").val();
		clasificacion.sujetoObligado.subdelegacion.delegacion.descripcion=$("#municipioIMSS\\.subdelegacion\\.delegacion\\.descripcion").val();
		clasificacion.sujetoObligado.subdelegacion.delegacion.ciz=$("#municipioIMSS\\.subdelegacion\\.delegacion\\.ciz").val();

		clasificacion.sujetoObligado.municipioIMSS.idMunicipio=$("#municipioIMSS\\.idMunicipio").val();
		clasificacion.sujetoObligado.municipioIMSS.cvecMunicipioSINDO=$("#municipioIMSS\\.cvecMunicipioSINDO").val();
		clasificacion.sujetoObligado.municipioIMSS.descMunicipio=$("#municipioIMSS\\.descMunicipio").val();
	}

	// Si el tramite de Sustitucion por subcontratacion se oculta el valor de la prima
	if(codigoTramite == 175){
		$('#gridClasificacionNueva tr > *:nth-child(6)').hide();
	}

	if ($('#indPrestaServicioPersonalCheck').is(':checked')) {
		clasificacion.indPrestaServicioPersonal = 2;
	}

	//Se genera identificador para guardar si la prima fue editada	
	if(codigoTramite == 11 || codigoTramite == 12 
		|| codigoTramite == 13 || codigoTramite == 14 
		|| codigoTramite == 15 || codigoTramite == 16 
		|| codigoTramite == 17 || codigoTramite == 18
		|| codigoTramite == 19 || codigoTramite == 20
		|| codigoTramite == 21 || codigoTramite == 22
	    || codigoTramite == 175 || codigoTramite == 176
	){
		var ch; 
		if ( codigoTramite == 20 || codigoTramite == 21 || codigoTramite == 175 || codigoTramite == 176){
			ch = document.getElementById("modPrimaPatronFS").checked;	
		}else{
			ch = document.getElementById("modPrimaPatron").checked;
		}				
		if (ch) {			
			console.log("::: El patron selecciono la opcion para editar su prima");
			clasificacion.indPrimaSugerida = "1";
		}else{
			console.log("::: El patron NO edito su prima");	
			clasificacion.indPrimaSugerida = "0";	
		}	
		$('#indPrimaSugerida').val(clasificacion.indPrimaSugerida);
		if(clasificacion.indSolSimilares != undefined && clasificacion.indSolSimilares != null){
			console.log("::: Se tienen solicitudes similares: " + clasificacion.indSolSimilares);
			$('#indSolSimilares').val(clasificacion.indSolSimilares);
		}				
	}
}

function replaceCaracteres(cadena) {
	console.log('La cadena antes de caracteres ' + cadena);
	cadena = cadena.replace(/[\u00E0\u00E1]/g,'a');
	cadena = cadena.replace(/[\u00C0\u00C1]/g,'A');
	cadena = cadena.replace(/[\u00E8\u00E9]/g,'e');
	cadena = cadena.replace(/[\u00C8\u00C9​]/g,'E');
	cadena = cadena.replace(/[\u00A0\u1680​]/g,'i');
	cadena = cadena.replace(/[\u00CC\u00CD]/g,'I');
	cadena = cadena.replace(/[\u00F2\u00F3​]/g,'o');
	cadena = cadena.replace(/[\u00D2\u00D3​]/g,'O');
	cadena = cadena.replace(/[\u00F9\u00FA​]/g,'u');
	cadena = cadena.replace(/[\u00D9\u00DA]/g,'U');
	console.log('La cadena despues de caracteres ' + cadena);

	return cadena;
}

function regresar() {
	var pregunta = "\u00BFDeseas guardar la informaci\u00F3n de la clasificaci\u00F3n antes de regresar?";
	$("#textoConfirmacion").html(pregunta);
	var dialogo = $("#dialogoConfirmacion").dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		height : 150,
		width : 400,
		title : "Confirmaci&oacute;n",
		buttons : {
			"No" : function() {
				$(this).dialog("close");
				parent.ModSRTCtrl.cerrar();
			},
			"Si" : function() {
				enviarClasificacion("Guardar", true);
				$(this).dialog("close");
			}
		}
	});
	dialogo.dialog('open');
}

function callbackHistoryBack() {
	$.unblockUI();
	parent.ModSRTCtrl.cerrar();
}

function toggleCuentaConTransporte(temp) {
	if (temp == 1) {
		$('#indTransportePropioTemp').removeAttr("disabled");
		$('#indTransporteAjenoTemp').removeAttr("disabled");
		$('#agregarTransporte').removeAttr("disabled");
		$('#modificarTransporte').removeAttr("disabled");
		$('#eliminarTransporte').removeAttr("disabled");
		$('#indNoDistribuyeTemp').attr("disabled", true);
		$('#indNoDistribuyeTemp').removeAttr("checked");
	} else {
		$('#indTransportePropioTemp').attr("disabled", true);
		$('#indTransporteAjenoTemp').attr("disabled", true);
		$('#indTransportePropioTemp').attr("checked", false);
		$('#indTransporteAjenoTemp').attr("checked", false);
		$('#agregarTransporte').attr("disabled", true);
		$('#modificarTransporte').attr("disabled", true);
		$('#eliminarTransporte').attr("disabled", true);
		$('#indNoDistribuyeTemp').removeAttr("disabled");
		$('#indNoDistribuyeTemp').attr("checked", true);
		var seccion = "transporte";
		if (grid[seccion] != null
				&& $(grid[seccion].fnSettings().aoData).length > 0) {
			data = grid["transporte"].fnSettings().aoData;
			var registro = null;
			for ( var i = 0; i < $(data).length; i++) {
				registro = data[i]._aData;
				sendToServer(seccion, "Eliminar", registro,
						callbackEliminarBlanco, false);
			}
			grid[seccion].fnDraw();
		}
	}
}

function callbackEliminarBlanco() {
}
var pdf;
//Para manejar el resultado de guardar y finalizar
function callbackEnviarClasificacion(response) {
	console.log('**** ENTRANDO AQUI AL RESPONSE DE GUARDAR TRAMITE*****', JSON.stringify(response));
	$.unblockUI();
	if (response.caso != undefined) {
		var caso = response.caso;
		if (caso != 3) {
			 procesarRespuestaServer(response);
			visibilidadBotones(caso);
		} else {
			var folioSolicitud = $('#hdnFolioSolicitud').val();
			//parent.ProcesandoSolicitudCtrl.abrir(folioSolicitud, idSolicitud);

			//pdf = response.documentosFinalesVentanilla;
		}
		if (response.solicitud != null) {
			idSolicitud = response.solicitud.solicitudId;
		}
		if (response.enProceso != undefined && response.enProceso != null) {
			enProceso = response.enProceso;
		}
		if (caso == 3) {
			if(response.documentosFinalesVentanilla != null){
					downloadPDF(response.documentosFinalesVentanilla, response.registroPatronal, "acuse_amsrt_", cerrarWizard);
					construirDialogoMensajes("Tramite finalizado", "El tr\u00E1mite finalizo de forma exitosa", null, cerrarWizard, 150, undefined);
					//setTimeout(function (){ cerrarWizard();}, 60000);
			} else {
				construirDialogoMensajes("Error", "Problemas al finalizar solicitud y generar documentos.", true, undefined);
			}			
		}

	} else if (response.mensajeError) {
		construirDialogoMensajes("Error", response.mensajeError, true,
				undefined);
	}
}


function recuperaCargaAcuse(){

	$("#formaRegresoDetalle").submit();

}

function regresaDetalleSO(){
	$("#formaRegresoDetalle2").submit();
}

function recuperaAvisoMod(){

	$("#formaGeneraAviso").submit();

}

function revisarSRT(){
	//$("#formaAcuse").submit(); TODO:
	var url = context + "presentarAcuse";
	$('#reporteFrame').html('<iframe id="reporteClasificacionFrame" src="' + url 
			+ '" width="100%" height="900px" '
			+ 'onload="set_size(\'reporteClasificacionFrame\'); $.unblockUI();" frameborder="0"/>');
			
	$.blockUI();
	var dialogo = $("#dialogoReporte").dialog({
		autoOpen : false,
		resizable : true,
		modal : true,
		//height : '900px',
		//maxHeight : '95%',
		width : '95%',
		title : "Revisar",
		overlay : {
			opacity : 0.5,
			background : "black"
		}

	});
	dialogo.dialog('open');		
}

function back() {
	history.back();
}

function dialogoEdicion(seccion, accion) {
	var datos = [];
	var titulo = accion;
	var nombreSeccion;
	var mostrarDialogo = true;
	var sePuedeAgregar = true;
	var cantidRgistros = 10;
	var readOnly = false;
	switch (seccion) {
	case 'producto':
		datos = columnasGridProductos;
		nombreSeccion = " producto/servicio";
		altura = 260;
		cantidRgistros = 10;
		break;
	case 'material':
		datos = columnasGridMaterials;
		nombreSeccion = " materias primas/materiales";
		altura = 260;
		cantidRgistros = 10;
		break;
	case 'equipo':
		datos = columnasGridMqEquipos;
		nombreSeccion = " maquinaria/equipo";
		altura = 455;
		cantidRgistros = 10;
		break;
	case 'transporte':
		datos = columnasGridTransport;
		nombreSeccion = " transporte";
		altura = 455;
		cantidRgistros = 10;
		break;
	case 'personal':
		datos = columnasGrid_Personal;
		nombreSeccion = " personal";
		altura = 250;
		cantidRgistros = 15;
		break;
	case 'bienes':
		datos = columnas_Grid_Bienes;
		nombreSeccion = " bienes";
		altura = 355;
		cantidRgistros = 10;
		break;
	}

	titulo += nombreSeccion;

	if (accion == "Agregar") {
		sePuedeAgregar = $(grid[seccion].fnSettings().aoData).length < cantidRgistros;
		if (!sePuedeAgregar) {
			var mensaje = "No se pueden agregar m&aacute;s de "
					+ cantidRgistros + " registros de " + nombreSeccion;
			var titulo = "Imposible " + titulo;
			construirDialogoMensajes(titulo, mensaje,undefined,undefined, 200,undefined);
			return;
		}
	}

	var obRowSelected = darFormatoDatosFormulario(fnGetRowSelected(grid[seccion]), seccion);

	if (obRowSelected != undefined || obRowSelected != null || accion == "Agregar") {
		console.log('obRowSelected ==========> es null');
		if (accion != "Eliminar") {
			crearForma(seccion, datos, readOnly);
			if (accion == "Modificar") {
				cargarDatosEnForma(obRowSelected);
			}
			var dialogo = construirDialogoGrid(titulo, seccion, accion, altura);

		} else {
			$("#textoConfirmacion")
					.html(
							"\u00BFEst\u00E1 seguro que desea eliminar el objeto seleccionado?");
			var dialogo = $("#dialogoConfirmacion").dialog(
					{
						autoOpen : false,
						resizable : false,
						modal : true,
						height : 'auto',
						width : 400,
						title : "Confirmar la eliminaci&oacute;n",
						buttons : {

							"Cancelar" : function() {
								$(this).dialog("close");
							},
							"Aceptar" : function() {
								var objeto = obRowSelected;
								sendToServer(seccion, accion, objeto,
										callbackDatosForma, false);
								grid[seccion].fnDraw();
								//evaluarClasificacion();
								$(this).dialog("close");
							}
						}
					});
			dialogo.dialog('open');
		}
		dialogo.dialog('open');

		if(accion != "eliminar") {
			var $contenidoDialog = $(dialogo.selector);
			$contenidoDialog.find('.delta-tooltip').tooltip({
				animation : true,
				container : 'body'
			});
			$contenidoDialog.parent().find(".ui-dialog-buttonpane").append("<div style=\"float:left; padding-top:10px\">* Campos obligatorios</div>");
		}
	} else {
		dialogoError.dialog('open');
	}
}

function darFormatoDatosFormulario(data, seccion) {
	if (data) {
		switch (seccion) {
		case 'producto':
			data.descripcion = darFormatoHtml(data.descripcion);
			break;
		case 'material':
			data.descripcion = darFormatoHtml(data.descripcion);
			break;
		case 'equipo':
			data.desUso = darFormatoHtml(data.desUso);
			data.desNombre = darFormatoHtml(data.desNombre);
			data.desCapacidadPotencia = darFormatoHtml(data.desCapacidadPotencia);
			break;
		case 'transporte':
			data.desUso = darFormatoHtml(data.desUso);
			data.desNombre = darFormatoHtml(data.desNombre);
			data.desCapacidadPotencia = darFormatoHtml(data.desCapacidadPotencia);
			break;
		case 'personal':
			data.oficioOcupacion = darFormatoHtml(data.oficioOcupacion);
			break;
		case 'bienes':
			data.desBienes = darFormatoHtml(data.desBienes);
			break;
		}
	}

	return data;
}

function darFormatoHtml(mensaje) {
	mensaje = mensaje.replace(/<br \/>/g, "\n");
	mensaje = mensaje.replace(/&nbsp;/g, " ");
	mensaje = mensaje.replace(/&lt;/g, "<");
	mensaje = mensaje.replace(/&gt;/g, ">");

	return mensaje;
}

function cargarDatosEnForma(data) {
	var inputs = document.getElementById("formaDialogo").getElementsByTagName(
			"input");
	var selects = document.getElementById("formaDialogo").getElementsByTagName(
			"select");
	var textareas = document.getElementById("formaDialogo")
			.getElementsByTagName("textarea");
	var campo;
	for ( var i = 0; i < inputs.length; i++) {
		campo = inputs[i];
		campo.value = data[campo.id];
	}
	var opcion;
	for ( var i = 0; i < selects.length; i++) {
		campo = selects[i];
		tipo = campo.id.split("_")[0];
		desc = campo.id.split("_")[1];
		opcion = campo.namedItem(data[tipo][desc]);
		opcion.selected = true;
	}

	for ( var i = 0; i < textareas.length; i++) {
		campo = textareas[i];
		campo.value = data[campo.id];
	}
}

function validaSize(textarea, size, event) {
	var input = document.getElementById(event.target.id);

    if (isTextSelected(input)) {
        return ;
    }

	if (textarea.value.length >= size) {
		textarea.value = textarea.value.substr(0, size);
	}
}

function validaCaracteresKeyUp(event) {

	event = event || window.event;


	var charCode = (typeof event.which == "undefined") ? event.keyCode
			: event.which;
	var charAt = String.fromCharCode(charCode);

	//alert("charAt "+ charAt);

	var characterReg = /^\s*[0-9,\s//]+\s*$/;

	if(!characterReg.test(charAt)  ){
		//alert("......");
		return (event.preventDefault()) ? event.preventDefault() : event.returnValue = false;
	}
}

function validaCaracteresBlur(event) {
	event = event || window.event;
	var target = event.target || event.srcElement;
	target.value = target.value.replace(noEsDigito, "");
}

function validarNumeros(event) {
	// Allow Only: keyboard 0-9, numpad 0-9, backspace, tab, left arrow, right
	// arrow, delete, shift, ini, fin
	event = event || window.event;
	if (!event.ctrlKey && !event.metaKey && !event.altKey && event.keyCode != 8 && event.keyCode != 46
			&& event.keyCode != 9
			&& !(event.keyCode > 34 && event.keyCode < 40)) {
		var charCode = (typeof event.which == "undefined") ? event.keyCode
				: event.which;
		if (charCode < 96 || charCode > 105) {
			if (charCode
					&& digitsOnly.indexOf(String.fromCharCode(charCode)) < 0) {
				(event.preventDefault) ? event.preventDefault() : event.returnValue = false;
				return false;
			}
		}
	}
	if (event.shiftKey) {
		(event.preventDefault) ? event.preventDefault() : event.returnValue = false;
		return false;
	}
	if (event.ctrlKey && event.altKey) {
        // Appears to be Alt Gr
    	(event.preventDefault) ? event.preventDefault() : event.returnValue = false;
        return false;
    }
	/* */
};

function crearForma(formName, campos, readOnly) {
	$(idFormaDialogo).name = formName;
	$(idFormaDialogo).append("<div id='eliminable' style='margin: 0px auto; width: 96%;'>");
	var tipoInput;
	var id;
	var nombre = "";
	var estilo;
	var item;
	for (campo in campos) {
		item = campos[campo];
		nombre = item.mDataProp;
		id = "id=\"" + nombre + "\"";
		idError = "id=\"" + nombre + "Error\"";
		if (item.bVisible) {
			$("#eliminable").append("<div id=\"fila" + campo + "\" class=\"form-group\">");

			$("#fila" + campo).append("<label id=\"celda" + campo + "sTitle\" style='font-size: 12px; margin-bottom: 6px;'></label>");
			if (item.type != undefined && item.type == 'numero') {
				$("#celda" + campo + "sTitle").append( item.sTitle + " (num&eacute;rico) *:");
			} else {
				$("#celda" + campo + "sTitle").append(item.sTitle + " *:");
			}
		} else {
			$("#eliminable").append("<div id=\"fila" + campo + "\" sytle=\"display:none;\">");
		}

		switch (item.componente) {
		case "textarea":
			estilo = "class=\"form-control textClasificacion\" style=\"height: 90px; width: 97%;\"";
			if (readOnly) {
				$("#fila" + campo)
						.append(
								"<textarea "+ id+ " "+ estilo+ " rows=3 readOnly=\"true\" onkeypress=\"validaSize(this, "
								+ item.size+ ", event)\" onkeyup=\"validaSize(this, "+ item.size + ", event)\"/>");
			} else {
				$("#fila" + campo).append(
						"<textarea " + id + " " + estilo
								+ " rows=3 onkeypress=\"validaSize(this, "
								+ item.size
								+ ", event)\" onkeyup=\"validaSize(this, "
								+ item.size + ", event)\"/>");
			}
			break;
		case "select":
			estilo = " class=\"form-control\" style=\"width:97%;\"";
			nombre = nombre.replace(".", "_");
			$("#fila" + campo).append("<select id=\"" + nombre + "\" " + estilo + ">");
			$("#" + nombre).append("<option value=\"-1\">Seleccione una opci&oacute;n</option>");
			cargarCombo(nombre);
			break;
		case "textfield":
		default:
			estilo = "class=\"textClasificacion form-control\"  style=\"width:97%;\"";
			tipoInput = "type=\"" + (item.bVisible ? "text" : "hidden") + "\"";
			if (readOnly) {
				$("#fila" + campo).append(
						"<input " + tipoInput + " " + id + " " + estilo
								+ " readOnly=\"true\" maxLength='" + item.size + "'/>");
			} else {
				$("#fila" + campo).append(
						"<input " + tipoInput + " " + id + " " + estilo+ " maxlength='" + item.size + "'/>");
			}
			break;
		}
		if (item.type != undefined && item.type == 'numero') {
			$("#" + nombre).keydown(validarNumeros);
			$("#" + nombre).keypress(validarNumeros);
			$("#" + nombre).keyup(validarNumeros);
			$("#" + nombre).blur(validaCaracteresBlur);
		}

		if (item.bVisible) {
			$("#fila" + campo).append("<span " + idError + " class=\"error hiddenElement\"></span>");
		}
	}
	$(idFormaDialogo).append("</table>");
	inicializarValidacionesCaracteresEspeciales();
}

function cargarCombo(nombre) {
	var sSource = context + acciones["combo"][nombre];
	var request = $.ajax({
		url : sSource,
		async : false,
		type : "POST",
		data : idSujetoObligado ? JSON.stringify(idSujetoObligado) : null,
		dataType : "json",
		contentType : "application/json; charset=utf-8"
	});
	request.done(function(response) {
		if (response.errors != undefined) {
			alert(response.errors);
		} else {
			var data = response.catalogo;
			var campo;
			for (index in data) {
				campo = data[index];
				if (campo.id != undefined) {
					$("#" + nombre).append(
							"<option value=\"" + campo.id + "\" id=\""
									+ campo.descripcion + "\">"
									+ campo.descripcion + "</option>");
				} else {
					$("#" + nombre).append(
							"<option value=\"" + campo.clave + "\" id=\""
									+ campo.desTipoCombustible + "\">"
									+ campo.desTipoCombustible + "</option>");
				}
			}
		}
	});

}

function obtenerDatosForma(forma) {
	var objeto = new Object();
	var inputs = document.getElementById("formaDialogo").getElementsByTagName(
			"input");
	var selects = document.getElementById("formaDialogo").getElementsByTagName(
			"select");
	var textareas = document.getElementById("formaDialogo")
			.getElementsByTagName("textarea");
	var campo;
	var tipo;
	for ( var i = 0; i < inputs.length; i++) {
		campo = inputs[i];
		objeto[campo.id] = campo.value.toUpperCase();
	}
	for ( var i = 0; i < selects.length; i++) {
		campo = selects[i];
		index = campo.selectedIndex;
		opciones = campo.options;
		if (campo.id.indexOf("_") > 0) {
			tipo = campo.id.split("_")[0];
			objeto[tipo] = new Object();
			if (tipo == "tipo") {
				objeto[tipo].id = campo.value;
				objeto[tipo].descripcion = opciones[index].text;
			} else if (tipo == "tipoCombustible") {
				objeto[tipo].clave = campo.value;
				objeto[tipo].desTipoCombustible = opciones[index].text
						.toUpperCase();
			}
		}
	}

	for ( var i = 0; i < textareas.length; i++) {
		campo = textareas[i];
		objeto[campo.id] = campo.value.toUpperCase();
	}
	objeto.sujetoObligado = new Object();
	objeto.sujetoObligado.cveIdSujetoObligado = idSujetoObligado;
	return objeto;
}

function callbackDatosForma(respuesta) {
	if (procesarRespuestaServer(respuesta)) {
		$(idDialogoGrids).dialog("close");
	} else if (respuesta.responseText != undefined
			&& respuesta.responseText != null) {
		fnProcesarErrores(respuesta, idFormaDialogo);
		if(respuesta.status==412) {
			marcarCamposConErrores(idFormaDialogo.replace("#",""),".error",".form-group")
			$("#divErrorCampos").css("display", "none");
		}
	}
}

function prepararRequest(sSource, data, async, callback) {
	var request = $.ajax({
		url : sSource,
		async : async,
		type : "POST",
		data : data ? JSON.stringify(data) : null,
		dataType : "json",
		contentType : "application/json; charset=utf-8"
	});
	request.done(callback);
	request.fail(callback);
}

function construirDialogoGrid(titulo, seccion, accion, altura) {
	return $(idDialogoGrids).dialog(
			{
				autoOpen : false,
				resizable : false,
				modal : true,
				height : 'auto',
				width : 500,
				title : titulo,
				buttons : {

					"Cancelar" : function() {
						$(this).dialog("close");
					},
					"Aceptar" : function() {
						var objeto = obtenerDatosForma(idFormaDialogo);
						fnHideErrores(idFormaDialogo);
						sendToServer(seccion, accion, objeto,
								callbackDatosForma, false);
						grid[seccion].fnDraw();
						//evaluarClasificacion();
						//$(this).dialog("close");
					}
				},
				close : function(event, ui) {
					$(idFormaDialogo).html("");
				}
			});
}

function construirDialogoConfirmar(accion, funcionEjecturar) {
	$("#textoConfirmacion").html(
			"\u00BFEst\u00E1 seguro que desea " + accion + " la solicitud?");
	var dialogo = $("#dialogoConfirmacion").dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		width : 400,
		title : "Confirmaci&oacute;n",
		buttons : {

			"Cancelar" : function() {
				$(this).dialog("close");
			},
			"Aceptar" : function() {
				funcionEjecturar();
				$(this).dialog("close");
			}
		}
	});
	dialogo.dialog('open');
}

function construirDialogoMensajes(titulo, mensaje, error, callback, pheight, pwidth) {
	if(pheight==undefined)
		pheight='auto';
	if(pwidth==undefined)
		pwidth=400;

	$("#textoMensaje").html(mensaje);
	$("#textoMensaje").removeAttr("style");
	if (error) {
		$("#textoMensaje").attr("style", "color: red;");
	} else {
		$("#textoMensaje").attr("style", "color: black;");
	}
	var dialogo = $("#dialogoMensajes").dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		height : pheight,
		width : pwidth,
		title : titulo,
		buttons : {
			"Aceptar" : function() {
				if (jQuery.isFunction(callback)) {
					callback();
				}
				$(this).dialog("close");
			}
		}
	});
	dialogo.dialog('open');
}

function construirDialogoMensajesMovPat(titulo, mensaje, error, callback, pheight, pwidth) {
	if(pheight==undefined)
		pheight='auto';
	if(pwidth==undefined)
		pwidth=400;

	$("#textoMensaje").html(mensaje);
	$("#textoMensaje").removeAttr("style");
	if (error) {
		$("#textoMensaje").attr("style", "color: red;");
	} else {
		$("#textoMensaje").attr("style", "color: black;");
	}
	var dialogo = $("#dialogoMensajes").dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		height : pheight,
		width : pwidth,
		title : titulo,
		buttons : {
			"Aceptar" : function() {
				if (jQuery.isFunction(callback)) {
					callback();
				}
				$(this).dialog("close");
			}
		}
	});
	dialogo.dialog('open');
}

function construirDialogoErrores(errores) {
	$("#textoMensaje").html(errores);
	$("#textoMensaje").removeAttr("style");
	$("#textoMensaje").attr("style", "color: red;");
	var dialogo = $("#dialogoMensajes").dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		height : 'auto',
		maxHeight : 400,
		width : 500,
		title : "Existen faltantes en su captura",
		buttons : {
			"Aceptar" : function() {
				$(this).dialog("close");
				campoToFocusOn.focus();
				if (campoToFocusOn.selector == "#fechaEfecto") {
					fechaEfectoDatePicker.datepicker('show');
				}
				campoToFocusOn = undefined;

			}
		}
	});
	dialogo.dialog('open');
}

function construirDialogoSeleccionaRegistro() {
	console.log('Construyendo Dialogo Selecciona');
	return $(idDialogoError).dialog({
		autoOpen : false,
		resizable : false,
		height : 'auto',
		width : 300,
		modal : true,
		buttons : {
			'Aceptar' : function() {
				$(this).dialog("close");
			}
		}
	});
}

function construirDialogoAceptarCancelar(mensaje, callbackAceptar, callbackCancelar, height, width) {
	if (height == undefined)
		height = 150
	if (width == undefined)
		width = 400

	$("#textoMensaje").html(mensaje);
	var dialogo = $("#dialogoMensajes").dialog({
		autoOpen: false,
		resizable: false,
		modal: true,
		height: height,
		width: width,
		title: "Confirmaci&oacute;n",
		buttons: {
			"Cancelar": function () {
				if (callbackCancelar != undefined)
					callbackCancelar();
				$(this).dialog("close");
			},
			"Aceptar": function () {
				if (callbackAceptar != undefined)
					callbackAceptar();
				$(this).dialog("close");
			}
		}
	});
	dialogo.dialog('open');
}


function construirDialogoAceptarCancelarSimilar(mensaje, callbackAceptar, callbackCancelar, height, width){
	if (height==undefined)
		height=150
	if (width==undefined)
		width=400

	$("#textoMensaje").html(mensaje);
	var dialogo = $("#dialogoMensajes").dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		height : height,
		width : width,
		title : "Confirmaci&oacute;n",
		buttons : {
			"Cancelar" : function() {
				if(callbackCancelar!=undefined)
					callbackCancelar();
				$(this).dialog("close");
			},
			"Aceptar" : function() {
				if(callbackAceptar!=undefined)
					callbackAceptar();
				$(this).dialog("close");
			}
		}
	});
	dialogo.dialog('open');
}

function sendToServer(seccion, accion, objeto, callback, async) {
	if (async == undefined) {
		async = true;
	}
	var sSource = "";
	if ((seccion == 'clasificacion' && accion == 'Guardar')
			|| (seccion == 'clasificacion' && accion == 'Finalizar'))
		sSource = context + acciones[seccion][accion] + "?indReintento="
				+ indReintento + "&indRPCInvalido=" + indRPCInvalido;
	else if(seccion == 'clasificacion' && accion == 'Obtener')
		sSource = context + acciones[seccion][accion]+'?idTipoTramite='+identificadorTramite;
	else if(seccion == 'clasificacion' && accion == 'finalizarInternet')
		sSource = context + acciones[seccion][accion]; 
	else	
		sSource = context + acciones[seccion][accion];
	if (callback == undefined) {
		prepararRequest(sSource, objeto, async, procesarRespuestaServer);
	} else {
		prepararRequest(sSource, objeto, async, callback);
	}
}

function procesarRespuestaServer(response, callback) {
	$.unblockUI();
	var mensaje = "";
	var titulo = "";
	var error = false;
	if (response.mensajeExito != undefined && response.mensajeExito != null) {
		titulo = "Operaci&oacute;n Exitosa";
		error = false;
		if (response.mensajeExito == "") {
			mensaje = "Operaci&oacute;n realizada con &eacute;xito.";
		} else {
			mensaje = response.mensajeExito;
			construirDialogoMensajes(titulo, mensaje, error, callback);
		}
		return true;
	} else if (response.mensajeError != undefined
			&& response.mensajeError != null) {
		titulo = "Operaci&oacute;n Err&oacute;nea";
		error = true;
		if (response.mensajeError == "") {
			mensaje = "Ocurrio un error con el servidor.";
		} else {
			mensaje = response.mensajeError;
		}
		construirDialogoMensajes(titulo, mensaje, error, callback);
	}
	return false;
}

function procesarRespuestaGuardarCerrar(response, callback) {
	$.unblockUI();
	var mensaje = "";
	var titulo = "";
	var error = false;
	if (response.mensajeExito != undefined && response.mensajeExito != null) {
		titulo = "Operaci&oacute;n Exitosa";
		error = false;
		if (response.mensajeExito == "") {
			mensaje = "Operaci&oacute;n realizada con &eacute;xito.";
		} else {
			mensaje = response.mensajeExito;
			construirDialogoMensajesMovPat(titulo, mensaje, error, callback, undefined, undefined);
		}
		return true;
	} else if (response.mensajeError != undefined
			&& response.mensajeError != null) {
		titulo = "Operaci&oacute;n Err&oacute;nea";
		error = true;
		if (response.mensajeError == "") {
			mensaje = "Ocurrio un error con el servidor.";
		} else {
			mensaje = response.mensajeError;
		}
		construirDialogoMensajesMovPat(titulo, mensaje, error, callback, undefined, undefined);
	}
	return false;
}

function mostrarFirma() {
	dialogFirma = $(dialogoConcluirSolicitudFirma).dialog({
		autoOpen : false,
		resizable : false,
		height : 550,
		width : 450,
		modal : true,
		zindex : 500
	});
	$("#dialogoConcluirSolicitudFirma").css("display", "block");
	dialogFirma.dialog('open');
}

function downloadPDF(pdf, identificador, nombrePDF, action) {
	const linkSource = `data:application/pdf;base64,${pdf}`;
	const downloadLink = document.createElement("a");
	const fileName = nombrePDF + identificador + ".pdf";
	downloadLink.href = linkSource;
	downloadLink.download = fileName;
	downloadLink.click();
	/**
	var d = $("#pdfcontainer").html("<iframe width='100%' height='100%' src='data:application/pdf;base64, " +
    encodeURI(pdf) + "' onload='$.unblockUI();'></iframe>");
     */
    var d = $("#pdfcontainer").html("<iframe width='100%' height='100%' src='data:application/pdf;base64, " +
    encodeURI(pdf) + "' onload='$.unblockUI();'></iframe>" + "<div style='display: inline-flex; gap: 10px; align-items: end;'>	" + 							
				"<button " +
					" class='btn btn-default btn-block'" + 
					" role='button' aria-disabled='false' onclick='btnCloseCitaInternet();'>" +
					" <span class='ui-button-text'>Finalizar tr&aacute;mite</span>" +
				"</button>" +
				"</div>");
	
	objReporte = d.dialog({

		title : 'Comprobante de tr\u00E1mite',
		autoOpen : false,
		width : "100%",
		height : 900,
		modal : false,
		resizable : false,
		autoResize : true,
		overlay : {
			opacity : 0.5,
			background : "black"
		},
		close : function(event, ui) {
			action();
		}
	}).height(900);
	objReporte.dialog('open');
	
}

function downloadPDFCita(pdf, identificador, nombrePDF, action) {
	const linkSource = `data:application/pdf;base64,${pdf}`;
	const downloadLink = document.createElement("a");
	const fileName = nombrePDF + identificador + ".pdf";
	downloadLink.href = linkSource;
	downloadLink.download = fileName;
	downloadLink.click();
	action();
	/** 
	var d = $("#pdfcontainer").html("<iframe width='100%' height='100%' src='data:application/pdf;base64, " +
    encodeURI(pdf) + "' onload='$.unblockUI();'></iframe>" + "<div style='display: inline-flex; gap: 10px; align-items: end;'>	" + 							
				"<button " +
					" class='btn btn-default btn-block'" + 
					" role='button' aria-disabled='false' onclick='btnCloseCitaInternet();'>" +
					" <span class='ui-button-text'>Finalizar tr&aacute;mite</span>" +
				"</button>" +
				"</div>");
	 
	objReporte = d.dialog({

		title : 'Comprobante de tr\u00E1mite',
		autoOpen : false,
		width : "100%",
		height : 900,
		modal : false,
		resizable : false,
		autoResize : true,
		overlay : {
			opacity : 0.5,
			background : "black"
		},
		close : function(event, ui) {
			action();
		}
	}).height(900);
	objReporte.dialog('open');
	 **/
}

function btnCloseCitaInternet(){
	cerrarWizardInternet();
}


function inicializarReporte() {
	var horizontalPadding = 15;
	var verticalPadding = 15;
	var urlFrame = context_path + '/movPat/clasificacion/mostrarAcuse';
	if(origenInternet){
		urlFrame = context_path + "/movPat/internet/clasificacion/mostrarAcuse";
	}

	var d = $('#reporteFrame').html(
			'<iframe id="site" src="' + urlFrame
					+ '" width="100%" height="100%" frameborder="0"/>');
	/*
	 * Configuracion del dialogo
	 */
	objReporte = d.dialog({

		title : 'Comprobante de tr\u00E1mite',
		autoOpen : false,
		width : 500,
		height : 500,
		modal : true,
		resizable : false,
		autoResize : true,
		overlay : {
			opacity : 0.5,
			background : "black"
		}
	}).width(500).height(500);
	objReporte.dialog('open');
}

function setCampoToFocusOn(fieldName) {
	if (campoToFocusOn == undefined)
		campoToFocusOn = $(fieldName);
}

var dayAdded = false;
var monthAdded = false;
var yearAdded = false;

function evaluar(tf, event) {
	var dayAndMonthLength = 2;
	var yearLength = 4;

	if (tf.value.length == 2) {
		dayAdded = true;
		tf.value = tf.value + '/';
	}
	if (tf.value.length == 5) {
		monthAdded = true;
		tf.value = tf.value + '/';
	}

}

function evaluarOnblur(tf, event) {

	if (tf.value.length > 0) {
		if (tf.value.length > 0 && tf.value.length < 10) {
			tf.setAttribute("style",
					"width: 100px; border-color: red !important;");
			fechaEfectoDatePicker.datepicker('show');
			runEffectFechaEfectoInvalidaMsg();
		} else {
			var date = tf.value;
			var year = date.substr(6, 4);
			var day = date.substr(0, 2);
			var month = date.substr(3, 2);

			if (year < 2000) {
				tf.value = day + '/' + month + '/2000';
				date = tf.value;
			}

			var check = false;
			var re = /^\d{1,2}\/\d{1,2}\/\d{4}$/;
			if (re.test(date)) {
				var adata = date.split('/');
				var dd = parseInt(adata[0], 10);
				var mm = parseInt(adata[1], 10);
				var yyyy = parseInt(adata[2], 10);
				var xdata = new Date(yyyy, mm - 1, dd);
				if ((xdata.getFullYear() == yyyy)
						&& (xdata.getMonth() == mm - 1)
						&& (xdata.getDate() == dd))
					check = true;
				else
					check = false;
			} else
				check = false;

			if (!check) {
				runEffectFechaEfectoInvalidaMsg();
				fechaEfectoDatePicker.datepicker('show');
			} else {
				runEffectHideFechaEfectoInvalidaMsg();
				tf.setAttribute("style",
						"width: 100px; border-color: black !important;");
			}
		}
	} else {
		runEffectHideFechaEfectoInvalidaMsg();
		tf.setAttribute("style", "width: 100px;");
	}
}

function runEffectFechaEfectoInvalidaMsg() {
	$("#fechaEfectoInvalidaMsg").fadeIn();
}

function runEffectHideFechaEfectoInvalidaMsg() {
	$("#fechaEfectoInvalidaMsg:visible").fadeOut(1000);
}

function showCalendar() {
	fechaEfectoDatePicker.datepicker('show');
}

//
function showCalendarVentanilla() {

	var valor = $('input#auditoria').filter(":checked").val();
	  if(valor == undefined){
		  showCalendar();

	  }else{
		  fechaEfectoDatePickerVentanilla.datepicker('show');
	  }

}

function mostrarMensajePSP(){
	var textoMensajePSP="La marca en 'SI', aplica para prestadoras de servicios especializados o de ejecuci\u00F3n de obras especializadas, en t\u00E9rminos de lo establecido en el art\u00edculo 13 de la Ley Federal del Trabajo.";

	construirDialogoAceptarCancelar(textoMensajePSP, callbackMensajePSPAceptar, callbackMensajePSPCancelar, 200, 600);
}

function callbackMensajePSPAceptar(){
	$("#clasificacion\\.indPrestaServicioPersonal1").attr("checked","true");
	validarHabilitarRPC();
}

function callbackMensajePSPCancelar(){
	$("#clasificacion\\.indPrestaServicioPersonal2").attr("checked","true");
	validarHabilitarRPC();
}

function validarHabilitarRPC() {
	//Validar solo si es VENTANILLA
	if(origenApp == origenVENTANILLA){
		var pspSI = $("#clasificacion\\.indPrestaServicioPersonal1").is(':checked');

		if(pspSI){
			//Si se indica SI en "psp", habilitar RPC.
			$('#indicadorRPCDiv1').removeClass('hiddenElement');
			$('#indicadorRPCDiv2').removeClass('hiddenElement');
			$('#indicadorRPCDiv1').addClass('showElement');
			$('#indicadorRPCDiv2').addClass('showElement');
		}else{
			//Si se indica NO en "psp", ocultar RPC y eliminar posible seleccion.
			if(esPatronRPC){
				//Si es Patron ya con marca Regitro Patronal por Clase, PSP y RPC no son modificables
				$("#clasificacion\\.indPrestaServicioPersonal1").attr("checked","true");
				$('#indRegPatClase').attr("checked", true);
			}else{
				$('#indicadorRPCDiv1').removeClass('showElement');
				$('#indicadorRPCDiv2').removeClass('showElement');
				$('#indicadorRPCDiv1').addClass('hiddenElement');
				$('#indicadorRPCDiv2').addClass('hiddenElement');
				$('#indRegPatClase').attr("checked", false);
			}
		}
	}

}


function inicializarComponenteFirmaDigital(){
	if((parent == undefined || parent == null) || (parent.FirmaDigitalCtrl == undefined || parent.FirmaDigitalCtrl == null)) {
		$.getScript("/gestionSolicitud-web/static/resources/js/delta/firma-digital/FirmaDigital.js", function(){
			firmaDigitalCtrl = FirmaDigitalCtrl;

			// Div para crear el di�logo
			firmaDigitalCtrl.init('firmaDigitalComponent', 'doctosRequeridosTramite');

			// Funci�n de callback
			firmaDigitalCtrl.setOnCloseCallback(procesarRespuestaFirmaDigital);
		});
	} else{
		firmaDigitalCtrl = parent.FirmaDigitalCtrl
	}

	$('#btnCmpFirmaDigital').click(function(){
		 //Se settean los valores de entrada
		firmaDigitalCtrl.datosEntrada.rfc = rfcSujetoObligado;
		firmaDigitalCtrl.datosEntrada.nrp = registroPatronal;
		//TODO armar cadena original
		firmaDigitalCtrl.datosEntrada.contenido = registroPatronal;
		firmaDigitalCtrl.datosEntrada.firmarArchivo = false;
		 // Se llama al servicio de firma digital
		firmaDigitalCtrl.firmaDigital();

	});

}

function procesarRespuestaFirmaDigital(response){
	var firmaResponse = firmaDigitalCtrl.getDatosSalida();
	if(firmaResponse!=undefined && firmaResponse!=null){
		var sSource = context + 'procesarDatosFirma';
		prepararRequest(sSource, firmaResponse, false, finalizarClasificacion);
	}else{
		construirDialogoMensajes("Error", "La operaci\u00F3n de firma electr\u00F3nica no se realiz\u00F3 satisfactoriamente", true);
	}
}

/*Funciones empleadas para el cambio de domicilio de centro de trabajo*/

function configurarBusquedaDomicilio(){
	/*configuracion para buscar un domicilio*/
	//var urlDomicilios = '/gestionDomicilios-web/static/resources/js/delta/domicilios/Domicilio.js';
	var urlDomicilios = context_path + '/static/resources/js/movPat/wizard/domicilio/Domicilio.js';
	console.log(':::Configurando js de Domicilio ::: ', urlDomicilios);
	$.getScript(urlDomicilios, function(script, textStatus){
	});

}

function fnOpenBuscarDomicilio() {
	parent.DomicilioCtrl.init('domiciliosComponent');
	parent.DomicilioCtrl.setOnCloseCallback(fnOnDomicilioReturn);
	parent.DomicilioCtrl.localizar();
}

var fnOnDomicilioReturn = function(){
	domicilioCentrotrabajo = this;

	var idSubdelegacionOrigen = $('#idSubdelegacionOrigen').val();
	var sSource = context_path + '/movPat/clasificacion/validarSubdelegacionCentroTrabajo?idSubdelegacionOrigen='+idSubdelegacionOrigen;
	if(origenInternet){
		sSource = context_path + '/movPat/internet/clasificacion/validarSubdelegacionCentroTrabajo?idSubdelegacionOrigen='+idSubdelegacionOrigen;
	}
	var objeto = new Object();
	objeto.asentamiento=new Object();
	objeto.asentamiento.nombre = domicilioCentrotrabajo.asentamiento.nombre;
	objeto.asentamiento.clave = domicilioCentrotrabajo.asentamiento.clave;
	objeto.codigoPostal = new Object();
	objeto.codigoPostal.codigoPostal = domicilioCentrotrabajo.codigoPostal.codigoPostal;
	objeto.asentamiento.localidad = new Object();
	objeto.asentamiento.localidad.municipio= new Object();
	objeto.asentamiento.localidad.municipio.entidadFederativa= new Object();
	objeto.asentamiento.localidad.clave = domicilioCentrotrabajo.asentamiento.localidad.clave;
	objeto.asentamiento.localidad.nombre= domicilioCentrotrabajo.asentamiento.localidad.nombre;
	objeto.asentamiento.localidad.municipio.clave = domicilioCentrotrabajo.asentamiento.localidad.municipio.clave;
	objeto.asentamiento.localidad.municipio.nombre=domicilioCentrotrabajo.asentamiento.localidad.municipio.nombre;
	objeto.asentamiento.localidad.municipio.entidadFederativa.clave=domicilioCentrotrabajo.asentamiento.localidad.municipio.entidadFederativa.clave;
	objeto.asentamiento.localidad.municipio.entidadFederativa.nombre = domicilioCentrotrabajo.asentamiento.localidad.municipio.entidadFederativa.nombre;

	prepararRequest(sSource, objeto, false, callbackValidaSubdelegacion);

};

function callbackValidaSubdelegacion(response){
	if(response.error){
		if (response.mensajeError != undefined
				&& response.mensajeError != null) {
			titulo = "Domicilio no permitido";
			error = true;
			if (response.mensajeError == "") {
				mensaje = "Ocurrio un error con el servidor.";
			} else {
				mensaje = response.mensajeError;
			}
			construirDialogoMensajes(titulo, mensaje, error, undefined, 400, 600);
		}
	}else{
		fnParseDomicilio();
		if(domicilioCentrotrabajo.codigoPostal.codigoPostal!=undefined && domicilioCentrotrabajo.codigoPostal.codigoPostal!='') {
			$("#hdnCveEnt").val(domicilioCentrotrabajo.asentamiento.localidad.municipio.entidadFederativa.clave);
			$("#hdnCveMun").val(domicilioCentrotrabajo.asentamiento.localidad.municipio.clave);
			$("#hdnCodigoPostal").val(domicilioCentrotrabajo.codigoPostal.codigoPostal);
			obtenerMunicipiosImss();
		}
	}

}


var fnParseDomicilio = function(){
	var d = domicilioCentrotrabajo;

	if (d!=null && d.vialidadPrimaria!=undefined){
		if ( d.vialidadReferenciaPosterior!=undefined){
			$("#cntroTrabajo\\.vialidadReferenciaPosterior\\.nombre").val(d.vialidadReferenciaPosterior.nombre);
			$("#cntroTrabajo\\.vialidadReferenciaPosterior\\.clave").val(d.vialidadReferenciaPosterior.clave);
			$("#cntroTrabajo\\.vialidadReferenciaPosterior\\.tipoVialidad\\.clave").val(d.vialidadReferenciaPosterior.tipoVialidad.clave);
		}else{
			$("#cntroTrabajo\\.vialidadReferenciaPosterior\\.nombre").val("");
			$("#cntroTrabajo\\.vialidadReferenciaPosterior\\.clave").val("");
			$("#cntroTrabajo\\.vialidadReferenciaPosterior\\.tipoVialidad\\.clave").val("");
		}

		if(d.vialidadReferenciaPrimaria!=undefined){
			$("#cntroTrabajo\\.vialidadReferenciaPrimaria\\.nombre").val(d.vialidadReferenciaPrimaria.nombre);
			$("#cntroTrabajo\\.vialidadReferenciaPrimaria\\.clave").val(d.vialidadReferenciaPrimaria.clave);
			$("#cntroTrabajo\\.vialidadReferenciaPrimaria\\.tipoVialidad\\.clave").val(d.vialidadReferenciaPrimaria.tipoVialidad.clave);
		}else{
			$("#cntroTrabajo\\.vialidadReferenciaPrimaria\\.nombre").val('');
			$("#cntroTrabajo\\.vialidadReferenciaPrimaria\\.clave").val('');
			$("#cntroTrabajo\\.vialidadReferenciaPrimaria\\.tipoVialidad\\.clave").val('');
		}

		if(typeof d.vialidadPrimaria !== 'undefined' && d.vialidadPrimaria != null){
			$("#cntroTrabajo\\.vialidadPrimaria\\.nombre").val(d.vialidadPrimaria.nombre);
			$("#cntroTrabajo\\.vialidadPrimaria\\.clave").val(d.vialidadPrimaria.clave);
			if(d.vialidadPrimaria.tipoVialidad != null) {
				$("#cntroTrabajo\\.vialidadPrimaria\\.tipoVialidad\\.clave").val(d.vialidadPrimaria.tipoVialidad.clave);
			}
		}

		if(d.vialidadReferenciaSecundaria!=undefined){
			$("#cntroTrabajo\\.vialidadReferenciaSecundaria\\.nombre").val(d.vialidadReferenciaSecundaria.nombre);
			$("#cntroTrabajo\\.vialidadReferenciaSecundaria\\.clave").val(d.vialidadReferenciaSecundaria.clave);
			$("#cntroTrabajo\\.vialidadReferenciaSecundaria\\.tipoVialidad\\.clave").val(d.vialidadReferenciaSecundaria.tipoVialidad.clave);
		}else{
			$("#cntroTrabajo\\.vialidadReferenciaSecundaria\\.nombre").val('');
			$("#cntroTrabajo\\.vialidadReferenciaSecundaria\\.clave").val('');
			$("#cntroTrabajo\\.vialidadReferenciaSecundaria\\.tipoVialidad\\.clave").val('');
		}


		if(d.domicilioCarretera!=undefined) {
			$("#cntroTrabajo\\.domicilioCarretera\\.terminoGeneral\\.descripcion").val(d.domicilioCarretera.terminoGeneral.descripcion);
			$("#cntroTrabajo\\.domicilioCarretera\\.terminoGeneral\\.clave").val(d.domicilioCarretera.terminoGeneral.clave);
			$("#cntroTrabajo\\.domicilioCarretera\\.derechoTransito\\.descripcion").val(d.domicilioCarretera.derechoTransito.descripcion);
			$("#cntroTrabajo\\.domicilioCarretera\\.derechoTransito\\.clave").val(d.domicilioCarretera.derechoTransito.clave);
			$("#cntroTrabajo\\.domicilioCarretera\\.origen").val(d.domicilioCarretera.origen);
			$("#cntroTrabajo\\.domicilioCarretera\\.destino").val(d.domicilioCarretera.destino);
			$("#cntroTrabajo\\.domicilioCarretera\\.codigoCarretera").val(d.domicilioCarretera.codigoCarretera);
			$("#cntroTrabajo\\.domicilioCarretera\\.administracion\\.descripcion").val(d.domicilioCarretera.administracion.descripcion);
			$("#cntroTrabajo\\.domicilioCarretera\\.administracion\\.clave").val(d.domicilioCarretera.administracion.clave);
			$("#cntroTrabajo\\.domicilioCarretera\\.cadenamiento").val(d.domicilioCarretera.cadenamiento);
		} else{
			$("#cntroTrabajo\\.domicilioCarretera\\.terminoGeneral\\.descripcion").val('');
			$("#cntroTrabajo\\.domicilioCarretera\\.terminoGeneral\\.clave").val('');
			$("#cntroTrabajo\\.domicilioCarretera\\.derechoTransito\\.descripcion").val('');
			$("#cntroTrabajo\\.domicilioCarretera\\.derechoTransito\\.clave").val('');
			$("#cntroTrabajo\\.domicilioCarretera\\.origen").val('');
			$("#cntroTrabajo\\.domicilioCarretera\\.destino").val('');
			$("#cntroTrabajo\\.domicilioCarretera\\.administracion\\.descripcion").val('');
			$("#cntroTrabajo\\.domicilioCarretera\\.administracion\\.clave").val('');
			$("#cntroTrabajo\\.domicilioCarretera\\.cadenamiento").val('');
		}

		if(d.domicilioCamino != undefined) {
			$("#cntroTrabajo\\.domicilioCamino\\.terminoGeneral\\.descripcion").val(d.domicilioCamino.terminoGeneral.descripcion);
			$("#cntroTrabajo\\.domicilioCamino\\.terminoGeneral\\.clave").val(d.domicilioCamino.terminoGeneral.clave);
			$("#cntroTrabajo\\.domicilioCamino\\.margen\\.descripcion").val(d.domicilioCamino.margen.descripcion);
			$("#cntroTrabajo\\.domicilioCamino\\.margen\\.clave").val(d.domicilioCamino.margen.clave);
			$("#cntroTrabajo\\.domicilioCamino\\.origen").val(d.domicilioCamino.origen);
			$("#cntroTrabajo\\.domicilioCamino\\.destino").val(d.domicilioCamino.destino);
			$("#cntroTrabajo\\.domicilioCamino\\.cadenamiento").val(d.domicilioCamino.cadenamiento);
		} else {
			$("#cntroTrabajo\\.domicilioCamino\\.terminoGeneral\\.descripcion").val('');
			$("#cntroTrabajo\\.domicilioCamino\\.terminoGeneral\\.clave").val('');
			$("#cntroTrabajo\\.domicilioCamino\\.margen\\.descripcion").val('');
			$("#cntroTrabajo\\.domicilioCamino\\.margen\\.clave").val('');
			$("#cntroTrabajo\\.domicilioCamino\\.origen").val('');
			$("#cntroTrabajo\\.domicilioCamino\\.destino").val('');
			$("#cntroTrabajo\\.domicilioCamino\\.cadenamiento").val('');
		}

		$("#cntroTrabajo\\.calle").val(d.calle);
		$("#cntroTrabajo\\.tipoBusquedaVialidad").val(d.tipoBusquedaVialidad);
		$("#cntroTrabajo\\.codigoPostal\\.codigoPostal").val(d.codigoPostal.codigoPostal);
		$("#cntroTrabajo\\.numExterior1").val(d.numExterior1);

		$("#cntroTrabajo\\.asentamiento\\.localidad\\.clave").val(d.asentamiento.localidad.clave);
		$("#cntroTrabajo\\.asentamiento\\.localidad\\.municipio\\.clave").val(d.asentamiento.localidad.municipio.clave);
		$("#cntroTrabajo\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.nombre").val(d.asentamiento.localidad.municipio.entidadFederativa.nombre);
		$("#cntroTrabajo\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.clave").val(d.asentamiento.localidad.municipio.entidadFederativa.clave);
		$("#cntroTrabajo\\.asentamiento\\.localidad\\.nombre").val(d.asentamiento.localidad.nombre);
		$("#cntroTrabajo\\.asentamiento\\.localidad\\.municipio\\.nombre").val(d.asentamiento.localidad.municipio.nombre);
		$("#cntroTrabajo\\.asentamiento\\.nombre").val(d.asentamiento.nombre);
		$("#cntroTrabajo\\.asentamiento\\.clave").val(d.asentamiento.clave);
		//$("#cntroTrabajo\\.vialidadPrimaria\\.tipoVialidad\\.descripcion").val(d.vialidadPrimaria.tipoVialidad.descripcion);

		if(d.numExteriorAlf!=undefined)
			$("#cntroTrabajo\\.numExteriorAlf").val(d.numExteriorAlf.toUpperCase());
		else
			$("#cntroTrabajo\\.numExteriorAlf").val('');

		if(d.numInterior!=undefined)
			$("#cntroTrabajo\\.numInterior").val(d.numInterior);
		else
			$("#cntroTrabajo\\.numInterior").val('');

		if(d.numInteriorAlf!=undefined)
			$("#cntroTrabajo\\.numInteriorAlf").val(d.numInteriorAlf.toUpperCase());
		else
			$("#cntroTrabajo\\.numInteriorAlf").val('');
	}
};

function construirObjectoCentroTrabajo(idSujetoObligado){
	cntroTrabajo=new Object();
	cntroTrabajo.vialidadPrimaria=new Object();
	cntroTrabajo.vialidadReferenciaPrimaria=new Object();
	cntroTrabajo.vialidadReferenciaSecundaria=new Object();

	cntroTrabajo.vialidadPrimaria.tipoVialidad=new Object();
	cntroTrabajo.vialidadReferenciaPrimaria.tipoVialidad=new Object();
	cntroTrabajo.vialidadReferenciaSecundaria.tipoVialidad=new Object();

	cntroTrabajo.asentamiento=new Object();
	cntroTrabajo.asentamiento.localidad=new Object();
	cntroTrabajo.asentamiento.codigoPostal=new Object();
	cntroTrabajo.asentamiento.localidad.municipio = new Object();
	cntroTrabajo.asentamiento.localidad.municipio.entidadFederativa = new Object();

	cntroTrabajo.vialidadReferenciaPrimaria.nombre=$("#cntroTrabajo\\.vialidadReferenciaPrimaria\\.nombre").val();
	cntroTrabajo.vialidadReferenciaSecundaria.nombre=$("#cntroTrabajo\\.vialidadReferenciaSecundaria\\.nombre").val();
	cntroTrabajo.vialidadPrimaria.nombre=$("#cntroTrabajo\\.vialidadPrimaria\\.nombre").val();

	cntroTrabajo.vialidadReferenciaPrimaria.tipoVialidad.clave=$("#cntroTrabajo\\.vialidadReferenciaPrimaria\\.tipoVialidad\\.clave").val();
	cntroTrabajo.vialidadReferenciaSecundaria.tipoVialidad.clave=$("#fv").val();
	cntroTrabajo.vialidadPrimaria.tipoVialidad.clave=$("#cntroTrabajo\\.vialidadPrimaria\\.tipoVialidad\\.clave").val();

	cntroTrabajo.vialidadReferenciaPrimaria.clave=$("#cntroTrabajo\\.vialidadReferenciaPrimaria\\.clave").val();
	cntroTrabajo.vialidadReferenciaSecundaria.clave=$("#cntroTrabajo\\.vialidadReferenciaSecundaria\\.clave").val();
	cntroTrabajo.vialidadPrimaria.clave=$("#cntroTrabajo\\.vialidadPrimaria\\.clave").val();

	if ($("#centroTrabajoForm #cntroTrabajo\\.vialidadReferenciaPosterior\\.nombre").val()!=""){
		cntroTrabajo.vialidadReferenciaPosterior=new Object();
		cntroTrabajo.vialidadReferenciaPosterior.tipoVialidad=new Object();
		cntroTrabajo.vialidadReferenciaPosterior.nombre=$("#cntroTrabajo\\.vialidadReferenciaPosterior\\.nombre").val();
		cntroTrabajo.vialidadReferenciaPosterior.tipoVialidad.clave=$("#cntroTrabajo\\.vialidadReferenciaPosterior\\.tipoVialidad\\.clave").val();
		cntroTrabajo.vialidadReferenciaPosterior.clave=$("#cntroTrabajo\\.vialidadReferenciaPosterior\\.clave").val();
	}


	if($("#cntroTrabajo\\.domicilioCamino\\.terminoGeneral\\.descripcion").val()!="") {
		cntroTrabajo.domicilioCamino = new Object();

		cntroTrabajo.domicilioCamino.terminoGeneral = new Object();
		cntroTrabajo.domicilioCamino.terminoGeneral.descripcion = $("#cntroTrabajo\\.domicilioCamino\\.terminoGeneral\\.descripcion").val();
		cntroTrabajo.domicilioCamino.terminoGeneral.clave = $("#cntroTrabajo\\.domicilioCamino\\.terminoGeneral\\.clave").val();

		cntroTrabajo.domicilioCamino.margen = new Object();
		cntroTrabajo.domicilioCamino.margen.descripcion = $("#cntroTrabajo\\.domicilioCamino\\.margen\\.descripcion").val();
		cntroTrabajo.domicilioCamino.margen.clave = $("#cntroTrabajo\\.domicilioCamino\\.margen\\.clave").val();

		cntroTrabajo.domicilioCamino.origen = $("#cntroTrabajo\\.domicilioCamino\\.origen").val();
		cntroTrabajo.domicilioCamino.destino = $("#cntroTrabajo\\.domicilioCamino\\.destino").val();
		cntroTrabajo.domicilioCamino.cadenamiento = $("#cntroTrabajo\\.domicilioCamino\\.cadenamiento").val();
	}

	if($("#cntroTrabajo\\.domicilioCarretera\\.terminoGeneral\\.descripcion").val() != "") {
		cntroTrabajo.domicilioCarretera = new Object();

		cntroTrabajo.domicilioCarretera.terminoGeneral = new Object();
		cntroTrabajo.domicilioCarretera.terminoGeneral.descripcion = $("#cntroTrabajo\\.domicilioCarretera\\.terminoGeneral\\.descripcion").val();
		cntroTrabajo.domicilioCarretera.terminoGeneral.clave = $("#cntroTrabajo\\.domicilioCarretera\\.terminoGeneral\\.clave").val();

		cntroTrabajo.domicilioCarretera.derechoTransito = new Object();
		cntroTrabajo.domicilioCarretera.derechoTransito.descripcion = $("#cntroTrabajo\\.domicilioCarretera\\.derechoTransito\\.descripcion").val();
		cntroTrabajo.domicilioCarretera.derechoTransito.clave = $("#cntroTrabajo\\.domicilioCarretera\\.derechoTransito\\.clave").val();

		cntroTrabajo.domicilioCarretera.origen = $("#cntroTrabajo\\.domicilioCarretera\\.origen").val();
		cntroTrabajo.domicilioCarretera.destino = $("#cntroTrabajo\\.domicilioCarretera\\.destino").val();
		cntroTrabajo.domicilioCarretera.cadenamiento = $("#cntroTrabajo\\.domicilioCarretera\\.cadenamiento").val();

		cntroTrabajo.domicilioCarretera.administracion = new Object();
		cntroTrabajo.domicilioCarretera.administracion.descripcion = $("#cntroTrabajo\\.domicilioCarretera\\.administracion\\.descripcion").val();
		cntroTrabajo.domicilioCarretera.administracion.clave = $("#cntroTrabajo\\.domicilioCarretera\\.administracion\\.clave").val();

	}

	cntroTrabajo.cveIdPatronSujetoObligado=idSujetoObligado;

	cntroTrabajo.codigoPostal= new Object();
	cntroTrabajo.codigoPostal.codigoPostal=$("#cntroTrabajo\\.codigoPostal\\.codigoPostal").val();
	cntroTrabajo.numExterior1=$("#cntroTrabajo\\.numExterior1").val();
	cntroTrabajo.numInterior=$("#cntroTrabajo\\.numInterior").val();
	cntroTrabajo.asentamiento.localidad.municipio.entidadFederativa.nombre=$("#cntroTrabajo\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.nombre").val();
	cntroTrabajo.asentamiento.localidad.municipio.entidadFederativa.clave=$("#cntroTrabajo\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.clave").val();

	cntroTrabajo.asentamiento.localidad.nombre=$("#cntroTrabajo\\.asentamiento\\.localidad\\.nombre").val();
	cntroTrabajo.asentamiento.localidad.clave=$("#cntroTrabajo\\.asentamiento\\.localidad\\.clave").val();
	cntroTrabajo.asentamiento.localidad.municipio.nombre=$("#cntroTrabajo\\.asentamiento\\.localidad\\.municipio\\.nombre").val();
	cntroTrabajo.asentamiento.localidad.municipio.clave=$("#cntroTrabajo\\.asentamiento\\.localidad\\.municipio\\.clave").val();
	cntroTrabajo.asentamiento.nombre=$("#cntroTrabajo\\.asentamiento\\.nombre").val();
	cntroTrabajo.asentamiento.clave=$("#cntroTrabajo\\.asentamiento\\.clave").val();
	cntroTrabajo.vialidadPrimaria.tipoVialidad.descripcion=$("#cntroTrabajo\\.vialidadPrimaria\\.tipoVialidad\\.descripcion").val();
	cntroTrabajo.numExteriorAlf=$("#cntroTrabajo\\.numExteriorAlf").val();
	cntroTrabajo.numInteriorAlf=$("#cntroTrabajo\\.numInteriorAlf").val();
	cntroTrabajo.mediosContacto=crearArrayMediosCT();

	return cntroTrabajo;
}

function crearArrayMediosCT(){
	var telefonoFijo = new Object();
	telefonoFijo.idVista = 1;
	telefonoFijo.tipoMedioContacto= new Object();
	telefonoFijo.tipoMedioContacto.idTipoMedioContacto=tipoContactoTelefonoFijo;


	var lada = $("#ctLada").val()!=undefined ? $("#ctLada").val() :"";
	var tel = $("#ctTelefonoFijo").val()!=undefined ? $("#ctTelefonoFijo").val() : "";
	var ext = $("#ctExtension").val()!=undefined ? $("#ctExtension").val() : "";
	var descripcionTelefono = 	lada+ "|"+tel+"|"+ext;

	telefonoFijo.desFormaContacto=descripcionTelefono;

	var telefonoFijo2 = new Object();
	telefonoFijo2.idVista = 2;
	telefonoFijo2.tipoMedioContacto= new Object();
	telefonoFijo2.tipoMedioContacto.idTipoMedioContacto=tipoContactoTelefonoFijo;

	var ladaB = $("#ctLada2").val()!=undefined ? $("#ctLada2").val() :"";
	var telB = $("#ctTelefonoFijo2").val()!=undefined ? $("#ctTelefonoFijo2").val() : "";
	var extB = $("#ctExtension2").val()!=undefined ? $("#ctExtension2").val() : "";
	var descripcionTelefonoB = 	ladaB+ "|"+telB+"|"+extB;

	telefonoFijo2.desFormaContacto=descripcionTelefonoB;

	var correo = new Object();
	correo.idVista = 3;
	correo.tipoMedioContacto= new Object();
	correo.tipoMedioContacto.idTipoMedioContacto=tipoContactoCorreoElectronico;
	correo.desFormaContacto=$("#ctCorreoElectronico").val()!=undefined ? $("#ctCorreoElectronico").val() : "";

	var listMedios=[telefonoFijo, telefonoFijo2, correo];

	return listMedios;
}


var inicializarDelegacionesSubdelegaciones = function(){
	cargarDelegaciones();
};

var cargarDelegaciones = function(){
	var objCT = new Object();

	objCT.asentamiento=new Object();
	objCT.asentamiento.localidad=new Object();
	objCT.asentamiento.codigoPostal=new Object();
	objCT.codigoPostal=new Object();
	objCT.asentamiento.localidad.municipio = new Object();
	objCT.asentamiento.localidad.municipio.entidadFederativa = new Object();

	objCT.asentamiento.localidad.municipio.entidadFederativa.nombre=$("#cntroTrabajo\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.nombre").val();
	objCT.asentamiento.localidad.municipio.entidadFederativa.clave=$("#cntroTrabajo\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.clave").val();
	objCT.asentamiento.localidad.nombre=$("#cntroTrabajo\\.asentamiento\\.localidad\\.nombre").val();
	objCT.asentamiento.localidad.clave=$("#cntroTrabajo\\.asentamiento\\.localidad\\.clave").val();
	objCT.asentamiento.localidad.municipio.nombre=$("#cntroTrabajo\\.asentamiento\\.localidad\\.municipio\\.nombre").val();
	objCT.asentamiento.localidad.municipio.clave=$("#cntroTrabajo\\.asentamiento\\.localidad\\.municipio\\.clave").val();
	objCT.asentamiento.nombre=$("#cntroTrabajo\\.asentamiento\\.nombre").val();
	objCT.asentamiento.clave=$("#cntroTrabajo\\.asentamiento\\.clave").val();
	objCT.asentamiento.codigoPostal.codigoPostal=$("#cntroTrabajo\\.codigoPostal\\.codigoPostal").val();
	objCT.codigoPostal.codigoPostal=$("#cntroTrabajo\\.codigoPostal\\.codigoPostal").val();

	if(objCT.codigoPostal.codigoPostal!=undefined && objCT.codigoPostal.codigoPostal!='') {
		obtenerMunicipiosImss();
	}
};


function isTextSelected(input){
   var startPos = input.selectionStart;
   var endPos = input.selectionEnd;
   var doc = document.selection;

   if(doc && doc.createRange().text.length != 0){
      return true;
   }else if (!doc && input.value.substring(startPos,endPos).length != 0){
      return true;
   }
   return false;
}