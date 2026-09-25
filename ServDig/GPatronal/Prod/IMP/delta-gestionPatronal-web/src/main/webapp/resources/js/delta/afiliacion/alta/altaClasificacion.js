var fechaEfectoDatePicker;
var idGridProductos = "#gridProductosServicios";
var idGridMaterials = "#gridMaeriasMateriales";
var idGridMqEquipos = "#gridMaquinariaEquipo";
var idGrid_Personal = "#gridPersonal";
var id_Grid_Bienes = "#gridBienes";
var idGridTransport = "#gridTransporte";
var idFormaDialogo = "#formaDialogo";
var idFormaProceso = "#formProcesos";
var idDialogoGrids = "#dialogoGrids";
var idDialogoError = "#dgErrorSinSeleccion";
var dialogoError;
var clasificacion;
//Restrict user input in a text field create as many regular expressions here as you need:
var digitsOnly = "1234567890";
var noEsDigito = /[^0-9]/g;
var campoGiroPermitido = /[\sa-zA-Z\d\#\%\(\)\,\-\.\/\?\@\*\']/g;
var campoGiroNegado = /[^\sa-zA-Z\d\u00F1\u00E1\u00E9\u00ED\u00F3\u00FA\u00D1\u00C1\u00C9\u00CD\u00D3\u00DA\#\%\(\)\,\-\.\/\?\@\*\']/g;
var idSujetoObligado;
var mostrarBienes=true;
var equipoTransporte=false;

var indicadorTramiteClasifExistente;
var minDate = 364;
var campoToFocusOn;

var columnasGridProductos = [ 
                              {mDataProp : "idVista", sTitle : "", 
                               bVisible : false, componente : 'texfield'}, 
                              {mDataProp : "descripcion",sTitle : "Principales productos elaborados o servicios prestados", 
                               bVisible : true, componente : 'textarea',type : "texto", size:"300"} 
							];

var columnasGridMaterials = [ {
	mDataProp : "idVista",
	sTitle : "",
	bVisible : false,
	componente : 'texfield'
}, {
	mDataProp : "descripcion",
	sTitle : "Principales materias primas y materiales utilizados",
	bVisible : true,
	componente : 'textarea',
	type : "texto", 
	size:"300"
} ];

var columnasGridMqEquipos = [ {
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
	sWidth: "250px",
	size:"30"
}, {
	mDataProp : "desCapacidadPotencia",
	sTitle : "Capacidad/Potencia",
	bVisible : true,
	componente : 'texfield',
	type : "texto", 
	sWidth: "200px",
	size:"25"
}, {
	mDataProp : "tipo.descripcion",
	sTitle : "Tipo Maquinaria",
	bVisible : true,
	sWidth: "200px",
	componente : 'select'
}, {
	mDataProp : "desUso",
	sTitle : "Uso",
	bVisible : true,
	componente : 'texfield',
	sWidth: "300px",
	type : "texto", 
	size:"40"
}, {
	mDataProp : "numUnidades",
	sTitle : "Unidades",
	bVisible : true,
	componente : 'texfield',
	sWidth: "50px",
	type : "numero", 
	size:"5"
} ];

var columnasGridTransport = [ {
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
	size:"30"
}, {
	mDataProp : "desCapacidadPotencia",
	sTitle : "Capacidad/Potencia",
	bVisible : true,
	componente : 'texfield',
	type : "texto", 
	size:"25"
}, {
	mDataProp : "tipoCombustible.desTipoCombustible",
	sTitle : "Tipo Combustible",
	bVisible : true,
	componente : 'select',
	type : "texto"
}, {
	mDataProp : "desUso",
	sTitle : "Uso",
	bVisible : true,
	componente : 'texfield',
	type : "texto", 
	size:"40"
}, {
	mDataProp : "numUnidades",
	sTitle : "Unidades",
	bVisible : true,
	componente : 'texfield',
	type : "numero", 
	size:"5"
} ];

var columnasGrid_Personal = [ {
	mDataProp : "idVista",
	sTitle : "",
	bVisible : false,
	componente : 'texfield'
}, {
	mDataProp : "numTrabajadores",
	sTitle : "No. Trabajadores",
	bVisible : true,
	componente : 'texfield',
	type : "numero", 
	size:"5"
}, {
	mDataProp : "oficioOcupacion",
	sTitle : "Oficio u Ocupaci&oacute;n",
	bVisible : true,
	componente : 'texfield',
	type : "texto", 
	size:"50"
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
	size:"5"
}, {
	mDataProp : "desBienes",
	sTitle : "Descripci&oacute;n",
	bVisible : true,
	componente : 'textarea',
	type : "texto",
	size:"100"
}];

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
		Finalizar : "finalizarClasificacion",
		Cancelar : "carcelarClasificacion"
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

function inicializaPaginaClasificacion() {
	
	if ($("#indRegPatClase").val() >= 0){ // Si el indRegPatClase viene nulo hay que dar chance de modificar al patron el PSP
		$("#clasificacion\\.indPrestaServicioPersonal1").attr("disabled", true);
		$("#clasificacion\\.indPrestaServicioPersonal2").attr("disabled", true);
		
		if ($("#indRegPatClase").val() == 1){
			$("#clasificacion\\.indPrestaServicioPersonal1").attr("checked", true);
		} else {
			$("#clasificacion\\.indPrestaServicioPersonal2").attr("checked", true);
		}
	}

	fechaEfectoDatePicker = $("#fechaEfecto").datepicker({
		//showOn: "",
		//buttonImage: context_path + "/static/resources/imagenes/calendar.gif",
		//buttonImageOnly: true,
		changeMonth: true,
		changeYear: true,
		minDate: -minDate,
		onClose: function(dateText, inst) {
			if (dateText != ""){
				runEffectHideFechaEfectoInvalidaMsg();
				$("#fechaEfecto").attr("style","width: 70px;");
			}
		}

		
	});
	
	grid['producto'] = crearGrid(idGridProductos, columnasGridProductos,
			context_path + "/afiliacion/alta/paginarProductosServicios");
	grid['material'] = crearGrid(idGridMaterials, columnasGridMaterials,
			context_path + "/afiliacion/alta/paginarMateriaMaterial");
	grid['equipo'] = crearGrid(idGridMqEquipos, columnasGridMqEquipos, context_path
			+ "/afiliacion/alta/paginarMaquinariaEquipo");
	grid['transporte'] = crearGrid(idGridTransport, columnasGridTransport,
			context_path + "/afiliacion/alta/paginarEquipoTransporte");
	grid['personal'] = crearGrid(idGrid_Personal, columnasGrid_Personal,
			context_path + "/afiliacion/alta/paginarPersonal");
	if (mostrarBienes) {
		grid['bienes'] = crearGrid(id_Grid_Bienes, columnas_Grid_Bienes,
				context_path + "/afiliacion/alta/paginarBienes");
	} else {
		if ($("#seccionBienesInmuebles").length > 0) {
			$("#seccionBienesInmuebles")[0].style.display = "none";
		}
	}

	dialogoError = construirDialogoSeleccionaRegistro();

	if (idSolicitud != null && idSolicitud > 0) {
		visibilidadBotones(2);
	} else {
		visibilidadBotones(1);
	}
	toggleCuentaConTransporte(equipoTransporte);
}

function visibilidadBotones(caso) {
	switch (caso) {
	case 1: // Nueva solicitud
		$("#btnGuardar").removeAttr("disabled");
		$("#btnFinalizar").attr("disabled", true);
		$("#btnCancelar").attr("disabled", true);
//		$("#btnCancelar").removeAttr("disabled");
		break;
	case 2:// Solicitud Guaradada
		$("#btnGuardar").removeAttr("disabled");
		$("#btnFinalizar").removeAttr("disabled");
		$("#btnCancelar").removeAttr("disabled");
		break;
	case 3:// Solicitud Finalizada
		$("#formaAcuse").submit();
//		window.open(context_path+"/clasificacion/presentarAcuse", "imprimir_documento", "dialogWidth:1050px;dialogHeight:700px;status=yes,toolbar=no,menubar=no,location=no,resize=no");
//		$("#formaAcuse").submit();
//		objReporte.dialog('open');
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
		bPaginate : true,
		bAutoWidth : false,
		bServerSide : true,
		bProcessing : true,
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
		if (seleccionar){
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
	//sujetoObligado.cveIdSujetoObligado = idSujetoObligado;
	wrapper.oForm.sujetoObligado = sujetoObligado;
	wrapper.aoData = aoData;
	$.postJSON(sSource, wrapper, function(data) {
		fnCallback(data);
	});
}

function seleccionarClasificacion() {
	var oSendData = new Object();
	oSendData.origen = 'delta';
	oSendData.tipoBusqueda = 3;
	oSendData.showAnterior = true;
	oSendData.showClase = false;
	oSendData.session = sessionId;
	try{
		var valor = window
				.showModalDialog(
						"/clasificador",
						oSendData,
						"dialogWidth:900px;dialogHeight:800px;status=yes,toolbar=no,menubar=no,location=no");
		clasificacion = new Object();
		if (valor != undefined && valor != null) {
			clasificacion.fraccion = new Object();
			clasificacion.fraccion.id = valor.id.cveFraccion;
			clasificacion.fraccion.grupo = new Object();
			clasificacion.fraccion.grupo.id = valor.id.cveGrupo;
			clasificacion.fraccion.grupo.division = new Object();
			clasificacion.fraccion.grupo.division.id = valor.id.cveDivision;
			sendToServerAlta("clasificacion", "Obtener", clasificacion, callbackObtenerClasificacion);
		}
	}catch(e){
		alert("Sucedio un error inesperado en la seleccion de la clasificaci\u00F3n");
	}
}

function callbackObtenerClasificacion(response){
	if(response.clasificacion != undefined && response.clasificacion != null && response.clasificacion != ""){
		var clasificacion = response.clasificacion;
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
		
		$("#claveClase").val(fraccion.clase.clave);
		$("#textClase").html(fraccion.clase.descripcion);
		$("#textPrimaAnt").html(fraccion.primaSRT);
		
		$("#claveDivisionCompleta").html(''+division.numDivision+grupo.numGrupo+fraccion.numFraccion);
	}
}

function guardarClasificacion() {	
	enviarClasificacion("Guardar");
}

function isEmpty(temp){
	return (temp == undefined || temp == "");
}

function isValidDate(d) {
	if ( Object.prototype.toString.call(d) !== "[object Date]" ){
	    return false;
	}
	return !isNaN(d.getTime());
}

function finalizarClasificacion() {
	console.log(";;;;;En finalizarClasificacion, validando datos");
	var errors = "";
	var temp = "";
	$("#fechaEfecto").attr("style","width: 70px; border-color: black !important;");
	if(esOperador){
		var temp = $("#fechaEfecto").val();
		if(isEmpty(temp)){
			errors += "* La Fecha a partir de la cual surte efecto no puede ser anterior a 364 d\u00EDas de la fecha actual. <br/>";
			$("#fechaEfecto").attr("style", "width: 70px; border-color: red !important;");
			setCampoToFocusOn("#fechaEfecto");
			fechaEfectoDatePicker.datepicker('show');
			//runEffectFechaEfectoInvalidaMsg();
		}
		temp = new Date(temp);
		if(isValidDate(temp)){
			var fechaMinima=new Date();
			fechaMinima.setDate(fechaMinima.getDate() - minDate-1);
			if(fechaMinima > temp){
			//errors += "No puedes seleccionar un día menor a "+minDate+" con respescto al d&iacute;a actual. <br/>";
			errors += "* La Fecha a partir de la cual surte efecto no puede ser anterior a 364 d\u00EDas de la fecha actual. <br/>";
			$("#fechaEfecto").attr("style", "width: 70px; border-color: red !important;");
			setCampoToFocusOn("#fechaEfecto");
			}
		}else{
			errors += "* Por favor selecione una fecha o teclee una fecha valida dd/MM/yyyy. <br/>";
			$("#fechaEfecto").attr("style", "width: 70px; border-color: red !important;");
			setCampoToFocusOn("#fechaEfecto");
		}
		
	}
	temp = $("#giroClasificacion").val();
	if(isEmpty(temp)){
		errors += "* El giro es un campo obligatorio. <br/>";
		setCampoToFocusOn("#giroClasificacion");
	}else{
		if(temp.length > 300){
			errors += "* El giro no puede exceder de 300 caract\u00E9res. <br/>";
			setCampoToFocusOn("#giroClasificacion");
		}
	}
	temp = $('input:radio[name=clasificacion\.indPrestaServicioPersonal]:checked').val();
	if(isEmpty(temp)){
		errors += "* El indicador de prestaci&oacute;n de servicos de personal es un campo obligatorio. <br/>";
		setCampoToFocusOn("#labelServicioDePersonal");
	}
	if( isEmpty($("#fraccion").val()) || isEmpty($("#grupo").val()) || isEmpty($("#divison").val()) ){
		errors += "* La clasificaci&oacute;n es un campo obligatorio. <br/>";
		setCampoToFocusOn("#fraccion");
	}
	var temp = $("#procesoInicial").val();
	if(isEmpty(temp)){
		errors += "* Los Procesos Iniciales son un campo obligatorio. <br/>";
		setCampoToFocusOn("#procesoInicial");
	}
	var temp = $("#procesoIntermedio").val();
	if(isEmpty(temp)){
		errors += "* Los Procesos Intermedios son un campo obligatorio. <br/>";
		setCampoToFocusOn("#procesoIntermedio");
	}
	var temp = $("#procesoFinal").val();
	if(isEmpty(temp)){
		errors += "* Los Procesos Finales son un campo obligatorio. <br/>";
		setCampoToFocusOn("#procesoFinal");
	}
	if(grid['producto'].fnSettings().aoData.length <= 0){
		errors += "* Es necesario agregar por lo menos un producto/servico. <br/>";
		setCampoToFocusOn("#seccionProductosMaeriales");
	}
	if(grid['material'].fnSettings().aoData.length <= 0){
		errors += "* Es necesario agregar por lo menos una materia prima/material. <br/>";
		setCampoToFocusOn("#seccionProductosMaeriales");
	}
	if(grid['equipo'].fnSettings().aoData.length <= 0){
		errors += "* Es necesario agregar por lo menos un equipo/maquinaria. <br/>";
		setCampoToFocusOn("#seccionMaquinariaEquipo");
	}
	if(grid['personal'].fnSettings().aoData.length <= 0){
		errors += "* Es necesario agregar por lo menos un grupo de personal. <br/>";
		setCampoToFocusOn("#seccionPersonal");
	}
	
	temp = $("#siCuentaConTransporte").is(':checked');
	if(temp){
		if(grid['personal'].fnSettings().aoData.length <= 0){
			errors += "* Es necesario agregar por lo menos un equipo de transporte. <br/>";
			setCampoToFocusOn("#seccionTransporte");
		}
		if(!$('#indTransportePropioTemp').is(':checked') && !$('#indTransporteAjenoTemp').is(':checked') ){
			errors += "* Se debe seleccionar una forma de distribuci&oacute;n o entrega de mercanc&iacute;as. <br/>";
			setCampoToFocusOn("#seccionActividades");
		}
	}
	if($('#siCuetaConTransporte').attr("checked")){
		if(grid["transporte"] != null && $(grid["transporte"].fnSettings().aoData).length <= 0){
			errors += "* Debe agregar por lo menos un equipo de transporte. <br/>";
			setCampoToFocusOn("#seccionTransporte");
		}else if (!$('#indTransportePropioTemp').is(':checked') && !$('#indTransporteAjenoTemp').is(':checked')) {
			errors += "* Es necesario indicar si cuenta con transporte propio o ajeno. <br/>";
			setCampoToFocusOn("#seccionActividades");
		}else if ($('#indTransportePropioTemp').is(':checked') && $('#indTransporteAjenoTemp').is(':checked')) {
			if(grid["transporte"] != null && $(grid["transporte"].fnSettings().aoData).length == 1){
			var data = grid["transporte"].fnSettings().aoData;
			var registro = data[0]._aData;
				if(registro["numUnidades"] == 1){
					//errors += "Una sola unidad de transporte no puede ser propia y ajena, seleccione uno solo indicador. <br/>";
					errors += "* Una sola unidad de transporte no se puede asignar a transporte propio y transporte ajeno a la vez, seleccione s\u00F3lo un indicador. <br/>";
					setCampoToFocusOn("#seccionTransporte");
				}
			}
		}
	}

	if(errors == ""){
		enviarClasificacion("Finalizar");
	}else{
		construirDialogoErrores(errors);
	}
}

function cancelarSolicitud() {
	enviarClasificacion("Cancelar");
}

function enviarClasificacion(accion, history) {
	if (clasificacion == undefined || clasificacion == null) {
		clasificacion = new Object();
	}
	clasificacion.fraccion = new Object();
	clasificacion.fraccion.id = $("#fraccion").val();
	clasificacion.fraccion.numFraccion = $("#claveFraccion")[0].innerHTML;
	clasificacion.fraccion.descripcionDetallada = $("#textFraccion")[0].innerHTML.toUpperCase();
	clasificacion.fraccion.grupo = new Object();
	clasificacion.fraccion.grupo.id = $("#grupo").val();
	clasificacion.fraccion.grupo.numGrupo = $("#claveGrupo")[0].innerHTML.toUpperCase();
	clasificacion.fraccion.grupo.descripcion = $("#textGrupo")[0].innerHTML.toUpperCase();
	clasificacion.fraccion.grupo.division = new Object();
	clasificacion.fraccion.grupo.division.id = $("#divison").val();
	clasificacion.fraccion.grupo.division.numDivision = $("#claveDivision")[0].innerHTML.toUpperCase();
	clasificacion.fraccion.grupo.division.descripcion = $("#textDivison")[0].innerHTML.toUpperCase();
	clasificacion.fraccion.clase = new Object();
	clasificacion.indRegPatClase = $("#indRegPatClase").val();
	clasificacion.fraccion.clase.clave = $("#claveClase").val();
	clasificacion.fraccion.clase.descripcion = $("#textClase")[0].innerHTML;
	clasificacion.fraccion.primaSRT = $("#textPrimaAnt")[0].innerHTML;
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
	sujetoObligado.proceso = proceso;
	sujetoObligado.desAfectacion = $("#usoBienes").val().toUpperCase();
	sujetoObligado.desUsosBienes = $("#afectacionBienes").val().toUpperCase();
	if($('#siCuetaConTransporte').attr("checked")){
		sujetoObligado.cuentaConTransporte = 1;
	}else{
		sujetoObligado.cuentaConTransporte = 0;
	}
	sujetoObligado.numeroRegistroPatronal = registroPatronal;
	
	clasificacion.sujetoObligado = sujetoObligado;
	clasificacion.id = idClasificacion;
	clasificacion.giro = $("#giroClasificacion").val().toUpperCase();
	clasificacion.indPrestaServicioPersonal = $('input:radio[name=clasificacion\.indPrestaServicioPersonal]:checked').val();
	clasificacion.fecEfecto = $("#fechaEfecto").val();

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
	
	$.blockUI();
	if(history){
		sendToServerAlta("clasificacion", accion, clasificacion,callbackHistoryBack);
	}else{
		sendToServerAlta("clasificacion", accion, clasificacion,callbackEnviarClasificacion);
	}
}



function attachObjetoClasificacion(sujetoTramite) {
	if (clasificacion == undefined || clasificacion == null) {
		clasificacion = new Object();
	}
	clasificacion.fraccion = new Object();
	clasificacion.fraccion.id = $("#fraccion").val();
	clasificacion.fraccion.numFraccion = $("#claveFraccion")[0].innerHTML;
	clasificacion.fraccion.descripcionDetallada = $("#textFraccion")[0].innerHTML.toUpperCase();
	clasificacion.fraccion.grupo = new Object();
	clasificacion.fraccion.grupo.id = $("#grupo").val();
	clasificacion.fraccion.grupo.numGrupo = $("#claveGrupo")[0].innerHTML.toUpperCase();
	clasificacion.fraccion.grupo.descripcion = $("#textGrupo")[0].innerHTML.toUpperCase();
	clasificacion.fraccion.grupo.division = new Object();
	clasificacion.fraccion.grupo.division.id = $("#divison").val();
	clasificacion.fraccion.grupo.division.numDivision = $("#claveDivision")[0].innerHTML.toUpperCase();
	clasificacion.fraccion.grupo.division.descripcion = $("#textDivison")[0].innerHTML.toUpperCase();
	clasificacion.fraccion.clase = new Object();
	clasificacion.indRegPatClase = $("#indRegPatClase").val();
	clasificacion.fraccion.clase.clave = $("#claveClase").val();
	clasificacion.fraccion.clase.descripcion = $("#textClase")[0].innerHTML;
	clasificacion.fraccion.primaSRT = $("#textPrimaAnt")[0].innerHTML;
	
	var proceso = new Object();
	proceso.clave = $("#procesoClave").val();
	proceso.desInicial = $("#procesoInicial").val().toUpperCase();
	proceso.desIntermedio = $("#procesoIntermedio").val().toUpperCase();
	proceso.desFinal = $("#procesoFinal").val().toUpperCase();
	sujetoTramite.proceso = proceso;
	sujetoTramite.desAfectacion = $("#usoBienes").val().toUpperCase();
	sujetoTramite.desUsosBienes = $("#afectacionBienes").val().toUpperCase();
	if($('#siCuetaConTransporte').attr("checked")){
		sujetoTramite.cuentaConTransporte = 1;
	}else{
		sujetoTramite.cuentaConTransporte = 0;
	}
	
	clasificacion.giro = $("#giroClasificacion").val().toUpperCase();
	clasificacion.indPrestaServicioPersonal = $('input:radio[name=clasificacion\.indPrestaServicioPersonal]:checked').val();
	clasificacion.fecEfecto = $("#fechaEfecto").val();

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
	sujetoTramite.clasificacion=clasificacion;
	return sujetoTramite;
}


function regresar() {
	var pregunta ="\u00BFDesea guardar la informaci\u00F3n de la clasificaci\u00F3n antes de regresar?";
	$("#textoConfirmacion").html(pregunta);
	var dialogo = $("#dialogoConfirmacion").dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		height : 150,
		width : 400,
		title : "Confirmaci&oacute;n",
		buttons : {
			"Si" : function() {
				enviarClasificacion("Guardar", true);
				$(this).dialog("close");
			},
			"No" : function() {
				$(this).dialog("close");
				history.back();
			}
		}
	});
	dialogo.dialog('open');
}

function callbackHistoryBack(){
	$.unblockUI();
	history.back();
}

function toggleCuentaConTransporte(temp){
	if(temp == 1){
		$('#indTransportePropioTemp').removeAttr("disabled");
		$('#indTransporteAjenoTemp').removeAttr("disabled");
		$('#agregarTransporte').removeAttr("disabled");
		$('#modificarTransporte').removeAttr("disabled");
		$('#eliminarTransporte').removeAttr("disabled");
		$('#indNoDistribuyeTemp').attr("disabled", true);
		$('#indNoDistribuyeTemp').removeAttr("checked");
	}else{
		$('#indTransportePropioTemp').attr("disabled", true);
		$('#indTransporteAjenoTemp').attr("disabled", true);
		$('#indTransportePropioTemp').attr("checked", false);
		$('#indTransporteAjenoTemp').attr("checked", false);
		$('#agregarTransporte').attr("disabled", true);
		$('#modificarTransporte').attr("disabled", true);
		$('#eliminarTransporte').attr("disabled", true);
		$('#indNoDistribuyeTemp').removeAttr("disabled");
		$('#indNoDistribuyeTemp').attr("checked",true);
		var seccion = "transporte";
		if(grid[seccion] != null && $(grid[seccion].fnSettings().aoData).length > 0){
			data = grid["transporte"].fnSettings().aoData;
			var registro = null;
			for(var i = 0; i < $(data).length; i++){
				registro = data[i]._aData;
				sendToServerAlta(seccion, "Eliminar", registro, callbackEliminarBlanco, false);
			}
			grid[seccion].fnDraw();
		}
	}
}

function callbackEliminarBlanco(){
}

function callbackEnviarClasificacion(response) {
	$.unblockUI();
	if (response.caso != undefined) {
		var caso = response.caso;
		if (caso != 3) {
			procesarRespuestaServer(response);
			visibilidadBotones(caso);
		} else {
			visibilidadBotones(caso);
			procesarRespuestaServer(response, back);

		}
		if (response.solicitud != null) {
			idSolicitud = response.solicitud.solicitudId;
		}
		if (response.enProceso != undefined && response.enProceso != null) {
			enProceso = response.enProceso;
		}
	}else if(response.mensajeError){
		construirDialogoMensajes("Error", response.mensajeError, true, undefined);
	}
}

function back(){
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
		nombreSeccion = " Producto/Servicio";
		altura = 260;
		cantidRgistros = 10;
		break;
	case 'material':
		datos = columnasGridMaterials;
		nombreSeccion = " Materias primas/Materiales";
		altura = 260;
		cantidRgistros = 10;
		break;
	case 'equipo':
		datos = columnasGridMqEquipos;
		nombreSeccion = " Maquinaria/Equipo";
		altura = 455;
		cantidRgistros = 10;
		break;
	case 'transporte':
		datos = columnasGridTransport;
		nombreSeccion = " Transporte";
		altura = 455;
		cantidRgistros = 10;
		break;
	case 'personal':
		datos = columnasGrid_Personal;
		nombreSeccion = " Personal";
		altura = 250;
		cantidRgistros = 12;
		break;
	case 'bienes':
		datos = columnas_Grid_Bienes;
		nombreSeccion = " Bienes";
		altura = 355;
		cantidRgistros = 10;
		break;
	}

	titulo += nombreSeccion;
	
	if(accion == "Agregar"){
		sePuedeAgregar = $(grid[seccion].fnSettings().aoData).length < cantidRgistros;
		if(!sePuedeAgregar){
			var mensaje = "No se pueden agregar m&aacute;s de "+cantidRgistros+" registros de "+nombreSeccion;
			var titulo = "Imposible "+titulo;
			construirDialogoMensajes(titulo, mensaje);
			return;
		}
	}
	
	var obRowSelected = fnGetRowSelected(grid[seccion]);	
	if(obRowSelected != undefined || accion == "Agregar"){
		if(accion != "Eliminar"){
			crearForma(seccion, datos, readOnly);
			if (accion == "Modificar") {
				cargarDatosEnForma(obRowSelected);
			}
			var dialogo = construirDialogoGrid(titulo, seccion, accion, altura);
		}else{
			$("#textoConfirmacion").html("\u00BFEst\u00E1 seguro que desea eliminar el objeto seleccionado?");
			var dialogo = $("#dialogoConfirmacion").dialog({
				autoOpen : false,
				resizable : false,
				modal : true,
				height : 150,
				width : 400,
				title : "Confirmar la eliminaci&oacute;n",
				buttons : {
					"Aceptar" : function() {
						var objeto = obRowSelected;
						sendToServerAlta(seccion, accion, objeto, callbackDatosForma, false);
						grid[seccion].fnDraw();
						$(this).dialog("close");
					},
					"Cancelar" : function() {
						$(this).dialog("close");
					}
				}
			});
			dialogo.dialog('open');
		}
		dialogo.dialog('open');
	}else {
		dialogoError.dialog('open');
	}
}

function cargarDatosEnForma(data) {
	var inputs = document.getElementById("formaDialogo").getElementsByTagName("input");
	var selects = document.getElementById("formaDialogo").getElementsByTagName("select");
	var textareas = document.getElementById("formaDialogo").getElementsByTagName("textarea");
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

function validaSize(textarea, size, event){
	if(textarea.value.length >= size){
		textarea.value = textarea.value.substr(0, size);
	}
}

function validaCaracteresKeyUp(event){
	if ( !(event.keyCode == 37 || event.keyCode == 38 || event.keyCode == 39 || event.keyCode == 40 || event.keyCode == 8 || event.keyCode == 186 || (event.keyCode > 64 && event.keyCode < 91))){
		event.target.value = event.target.value.replace(campoGiroNegado, "");
	}
	
}

function validaCaracteresBlur(event){
	event.target.value = event.target.value.replace(noEsDigito, "");
}

function validarNumeros(event) {
	// Allow Only: keyboard 0-9, numpad 0-9, backspace, tab, left arrow, right
	// arrow, delete, shift, ini, fin
 	event = event || window.event;
    if(!event.ctrlKey 
    && !event.metaKey 
    && !event.altKey 
    && event.keyCode != 8
	&& event.keyCode != 9
    && !(event.keyCode >  34 && event.keyCode <  40)) {
        var charCode = (typeof event.which == "undefined") ? event.keyCode : event.which;
        if(charCode < 96 || charCode > 105){
	        if (charCode && digitsOnly.indexOf(String.fromCharCode(charCode)) < 0) {
	    		event.preventDefault();
	            return false;
	        }
        }
    }
    if(event.shiftKey){
        event.preventDefault();
        return false;
    }
/* */
};

function crearForma(formName, campos, readOnly) {
	$(idFormaDialogo).name = formName;
	$(idFormaDialogo).append("<fieldset id='eliminable'>");
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
			$("#eliminable").append(
					"<fieldset id=\"fila" + campo + "\" class=\"fsInterno\">");
			$("#fila" + campo).append(
					"<span " + idError
							+ " class=\"error hiddenElement\"></span>");
			$("#fila" + campo).append(
					"<legend id=\"celda" + campo + "sTitle\">");
			if (item.type != undefined && item.type == 'numero') {
				$("#celda" + campo + "sTitle").append(item.sTitle + "(num&eacute;rico):");
			}else{
				$("#celda" + campo + "sTitle").append(item.sTitle + ":");
			}
		} else {
			$("#eliminable").append(
					"<div id=\"fila" + campo + "\" sytle=\"display:none;\">");
		}

		switch (item.componente) {
		case "textarea":
			estilo = "style=\"height: 90px; width: 97%;\"";
			if (readOnly) {
				$("#fila" + campo).append(
						"<textarea " + id + " " + estilo
								+ " rows=3 readOnly=\"true\" onkeypress=\"validaSize(this, "+item.size+", event)\" onkeyup=\"validaSize(this, "+item.size+", event)\"/>");
			} else {
				$("#fila" + campo).append(
						"<textarea " + id + " " + estilo + " rows=3 onkeypress=\"validaSize(this, "+item.size+", event)\" onkeyup=\"validaSize(this, "+item.size+", event)\"/>");
			}
			break;
		case "select":
			estilo = "";
			nombre = nombre.replace(".", "_");
			$("#fila" + campo).append(
					"<select id=\"" + nombre + "\" " + estilo + ">");
			$("#" + nombre)
					.append(
							"<option value=\"-1\">Selecione una opci&oacute;n</option>");
			cargarCombo(nombre);
			break;
		case "textfield":
		default:
			estilo = "style=\"width:97%;\"";
			tipoInput = "type=\"" + (item.bVisible ? "text" : "hidden") + "\"";
			if (readOnly) {
				$("#fila" + campo).append(
						"<input " + tipoInput + " " + id + " " + estilo+ " readOnly=\"true\" maxLength='"+item.size+"'/>");
			} else {
				$("#fila" + campo).append(
						"<input " + tipoInput + " " + id + " " + estilo + " maxlength='"+item.size+"'/>");
			}
			break;
		}
		if (item.type != undefined && item.type == 'numero') {
			$("#" + nombre).keydown(validarNumeros);
			$("#" + nombre).keypress(validarNumeros);
			$("#" + nombre).keyup(validarNumeros);
			$("#" + nombre).blur(validaCaracteresBlur);
		}
	}
	$(idFormaDialogo).append("</table>");
}

function cargarCombo(nombre) {
	var sSource = context_path +'/afiliacion/alta/'+ acciones["combo"][nombre];
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
				objeto[tipo].desTipoCombustible = opciones[index].text.toUpperCase();
			}
		}
	}

	for ( var i = 0; i < textareas.length; i++) {
		campo = textareas[i];
		objeto[campo.id] = campo.value.toUpperCase();
	}
	objeto.sujetoObligado = new Object();
	objeto.sujetoObligado.cveIdSujetoObligado = idSujetoObligado!= undefined ? idSujetoObligado : 0;
	return objeto;
}

function callbackDatosForma(respuesta) {
	if (procesarRespuestaServer(respuesta)) {
		$(idDialogoGrids).dialog("close");
	} else if (respuesta.responseText != undefined
			&& respuesta.responseText != null) {
		fnProcesarErrores(respuesta, idFormaDialogo);
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
				height : altura,
				width : 500,
				title : titulo,
				buttons : {
					"Aceptar" : function() {
						var objeto = obtenerDatosForma(idFormaDialogo);
						sendToServerAlta(seccion, accion, objeto,
								callbackDatosForma, false);
						grid[seccion].fnDraw();
					},
					"Cancelar" : function() {
						$(this).dialog("close");
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
		height : 150,
		width : 400,
		title : "Confirmaci&oacute;n",
		buttons : {
			"Aceptar" : function() {
				funcionEjecturar();
				$(this).dialog("close");
			},
			"Cancelar" : function() {
				$(this).dialog("close");
			}
		}
	});
	dialogo.dialog('open');
}

function construirDialogoMensajes(titulo, mensaje, error, callback) {
	$("#textoMensaje").html(mensaje);
	$("#textoMensaje").removeAttr("style");
	if (error) {
		$("#textoMensaje").attr("style", "color: red;");
	} else {
		$("#textoMensaje").attr("style", "color: blue;");
	}
	var dialogo = $("#dialogoMensajes").dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		height : 150,
		width : 400,
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
		height : 275,
		width : 500,
		title : "Existen faltantes en su captura.",
		buttons : {
			"Aceptar" : function() {
				$(this).dialog("close");
				campoToFocusOn.focus();
				if(campoToFocusOn.selector == "#fechaEfecto"){
					fechaEfectoDatePicker.datepicker('show');
				}
				campoToFocusOn=undefined;
				
			}
		}
	});
	dialogo.dialog('open');
}

function construirDialogoSeleccionaRegistro() {
	return $(idDialogoError).dialog({
		autoOpen : false,
		resizable : false,
		height : 175,
		width : 300,
		modal : true,
		buttons : {
			'Aceptar' : function() {
				$(this).dialog("close");
			}
		}
	});
}

function sendToServerAlta(seccion, accion, objeto, callback, async) {
	if (async == undefined) {
		async = true;
	}
	var sSource = context_path + '/afiliacion/alta/'+acciones[seccion][accion];
	if (callback == undefined) {
		prepararRequest(sSource, objeto, async, procesarRespuestaServer);
	} else {
		prepararRequest(sSource, objeto, async, callback);
	}
}

function procesarRespuestaServer(response, callback) {
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
	} else if (response.mensajeError != undefined && response.mensajeError != null) {
		titulo = "Operaci&oacute;n Erronea";
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

function mostrarFirma(){	
	dialogFirma =  $(dialogoConcluirSolicitudFirma).dialog({
		autoOpen:false,
		resizable: false,
		height: 550,
		width: 400,
		modal: true		
	});
	$("#dialogoConcluirSolicitudFirma").css("display", "block");	
	dialogFirma.dialog('open');
}

function inicializarReporte(){
	var horizontalPadding = 15; 
    var verticalPadding = 15; 
    var urlFrame  = context_path+'/clasificacion/presentarAcuse'; 
	        
    var d = $('#reporteFrame').html( 
                    '<iframe id="site" src="' + urlFrame 
                                    + '" width="100%" height="100%" frameborder="0"/>'); 
        /* 
         * Configuracion del dialogo 
         */ 
     objReporte = d.dialog({ 
            
            title: 'Comprobante de tr\u00E1mite', 
            autoOpen: false, 
            width: 500, 
            height: 500, 
            modal: true, 
            resizable: false, 
            autoResize: true, 
            overlay: { 
                opacity: 0.5, 
                background: "black" 
            } 
        }).width(500).height(500); 
}

function setCampoToFocusOn(fieldName){
	if(campoToFocusOn == undefined )
		campoToFocusOn = $(fieldName);
}

var dayAdded = false;
var monthAdded = false;
var yearAdded= false;

function evaluar(tf, event){
	var dayAndMonthLength = 2;
	var yearLength = 4;
	
	if (tf.value.length == 2){
		dayAdded = true;
		tf.value = tf.value + '/';
	}
	if (tf.value.length == 5){
		monthAdded = true;
		tf.value = tf.value + '/';
	}
	
}

function evaluarOnblur(tf, event){

	if(tf.value.length > 0){
		if(tf.value.length > 0 && tf.value.length < 10){
			tf.setAttribute("style","width: 70px; border-color: red !important;");
			fechaEfectoDatePicker.datepicker('show');
			runEffectFechaEfectoInvalidaMsg();
		} else {
			var date = tf.value;
			var year = date.substr(6,4); 
			var day = date.substr(0,2);
			var month = date.substr(3,2);
			
			if (year < 2000){
				tf.value = day +'/'+ month + '/2000';
				date = tf.value;
			}
			
			var check = false;
			var re = /^\d{1,2}\/\d{1,2}\/\d{4}$/;
			if( re.test(date)){
				var adata = date.split('/');
				var dd = parseInt(adata[0],10);
				var mm = parseInt(adata[1],10);
				var yyyy = parseInt(adata[2],10);
				var xdata = new Date(yyyy,mm-1,dd);
				if ( ( xdata.getFullYear() == yyyy ) && ( xdata.getMonth () == mm - 1 ) && ( xdata.getDate() == dd ) )
					check = true;
				else
					check = false;
			} else
				check = false;
			
			if(!check){
				runEffectFechaEfectoInvalidaMsg();
				fechaEfectoDatePicker.datepicker('show');
			}else{
				runEffectHideFechaEfectoInvalidaMsg();
				tf.setAttribute("style","width: 70px; border-color: black !important;");
			}
		}
	} else {
		runEffectHideFechaEfectoInvalidaMsg();
		tf.setAttribute("style","width: 70px;");
	}
}

function runEffectFechaEfectoInvalidaMsg(){
	$( "#fechaEfectoInvalidaMsg" ).fadeIn();
}


function runEffectHideFechaEfectoInvalidaMsg() {
		$( "#fechaEfectoInvalidaMsg:visible" ).fadeOut(1000);
}

function showCalendar(){
	fechaEfectoDatePicker.datepicker('show');
}
