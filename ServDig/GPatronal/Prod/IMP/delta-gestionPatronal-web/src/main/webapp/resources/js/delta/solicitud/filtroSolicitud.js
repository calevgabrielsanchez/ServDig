/**
 * 
 */
var gridSolicitud;
var dialogoError;

var columnasSolicitud = [ {
	mDataProp : "solicitudId",
	bVisible : false
}, {
	mDataProp : "tipoSolicitud.idTipoSolicitud",
	bVisible : false
}, {
	mDataProp : "noFolioSolicitud",
	sTitle : "Folio"
}, {
	mDataProp : "tipoSolicitud.descripcion",
	sTitle : "Tipo"
}, {
	sTitle : "RFC",
	fnRender : renderRFC
}, {
	sTitle : "Reg Patronal",
	fnRender : renderOwner
}, {
	sTitle : "Fecha de Presentaci\u00F3n",
	mDataProp : "fechaPresentacionParse"
	//fnRender : renderFechaPresentacion
}, {
	sTitle : "Fecha de Conclusi\u00F3n",
	mDataProp : "fechaConclusionParse"
//	fnRender : renderFechaConclusion
}, {
	sTitle : "Tramites",
	fnRender : renderTramites
}, {
	sTitle : "Delegaci\u00F3n",
	fnRender : renderDelegacion
}, {
	sTitle : "Subdelegaci\u00F3n",
	fnRender : renderSubdelegacion
}, {
	mDataProp : "estadoSolicitud.descripcion",
	sTitle : "Estado"
} ];

function renderOwner(oObj) {
	if (oObj.aData.sujetoObligado != null
			&& oObj.aData.sujetoObligado != undefined 
			&& oObj.aData.sujetoObligado.numeroRegistroPatronal!=null 
			&& oObj.aData.sujetoObligado.numeroRegistroPatronal!=undefined) {
		var rowSujeto = oObj.aData.sujetoObligado;
		var texto = rowSujeto.modalidad != undefined && rowSujeto.modalidad != null ? rowSujeto.numeroRegistroPatronal+rowSujeto.modalidad.numModalidad+rowSujeto.digVerificador : rowSujeto.numeroRegistroPatronal; 
		return rowSujeto.numeroRegistroPatronal+rowSujeto.modalidad.numModalidad+rowSujeto.digVerificador;
	} else {
		return "";
	}
}

function renderTramites(oObj) {
	var arrayTramites = oObj.aData.tramites;
	var maxValues = oObj.aData.tramites.length;
	var descripcionTramites = '';
	var i = 0;
	for (i = 0; i < maxValues; i++) {
		descripcionTramites += arrayTramites[i].tipoTramite.descripcion
				+ ".<br>";
	}
	return descripcionTramites;
}

function renderRFC(oObj){
	if (oObj.aData.sujetoObligado != null
			&& oObj.aData.sujetoObligado != undefined) {
		var sujeto = oObj.aData.sujetoObligado;
		var fisica = sujeto.fisica;
		var moral = sujeto.moral;
		var rfc;
		if(fisica!=null && fisica!= undefined){
			rfc=fisica.rfc;
		}else if(moral!=null && moral!=undefined){
			rfc=moral.rfc;
		}else{
			rfc="";
		}
		return rfc;
	} else {
		return "";
	}
}

function renderSubdelegacion(oObj) {
	var numRP = renderOwner(oObj);
	if(numRP == ""){
		return "Por determinar";
	}else{
		if (oObj.aData.sujetoObligado != null
				&& oObj.aData.sujetoObligado != undefined) {
			var sujeto = oObj.aData.sujetoObligado; 
			if(sujeto.subdelegacion!= null && sujeto.subdelegacion!= undefined){
				return sujeto.subdelegacion.descripcion;
			}else{
				return "";
			}
		}else{
			return "";
		}
	}
}

function renderDelegacion(oObj) {
	var numRP = renderOwner(oObj);
	if(numRP == ""){
		return "Por determinar";
	}else{
		if (oObj.aData.sujetoObligado != null
				&& oObj.aData.sujetoObligado != undefined) {
			var sujeto = oObj.aData.sujetoObligado; 
			if(sujeto.subdelegacion!= null && sujeto.subdelegacion!= undefined){
				var subdelegacion = sujeto.subdelegacion;
				if(subdelegacion.delegacion!=null && sujeto.subdelegacion!= undefined)
					return subdelegacion.delegacion.descripcion;
				else
					return "";
			}else{
				return "";
			}
		}else{
			return "";
		}
	}
}


function enviarConsulta(){
	upperCaseDatosCapturados();
	document.getElementById('filtroSolicitudForm').submit();
}

function upperCaseDatosCapturados(){
	//Se ponen en mayúsculas los elementos que son tecleado y no númericos
	var rfc = $('#rfc').val();
	var rp = $('#rp').val();
	if(rfc!=undefined){
		rfc = rfc.toUpperCase();
		$('#rfc').val(rfc);
	}
	if(rp!=undefined){
		rp = rp.toUpperCase();
		$('#rp').val(rp);
	}
}

function renderFechaPresentacion(oObj) {
	var arrayTramites = oObj.aData.tramites;
	var maxValues = oObj.aData.tramites.length;
	var fechaPresentacion = null;
	var i = 0;
	var fechaPresentacionAMostrar = "";
	for (i = 0; i < maxValues; i++) {
		if(arrayTramites[i].fechaPresentacionParse != null && arrayTramites[i].fechaPresentacionParse != undefined){
			fechaPresentacionAMostrar = new String(arrayTramites[i].fechaPresentacionParse);
			break;
		}
	}
	return fechaPresentacionAMostrar;
}

function renderFechaConclusion(oObj) {
	var arrayTramites = oObj.aData.tramites;
	var maxValues = oObj.aData.tramites.length;
	var fechaConclusion = null;
	var i = 0;
	var fechaConclusionAMostrar = "";
	for (i = 0; i < maxValues; i++) {
		if(arrayTramites[i].fechaConclusionParse != null && arrayTramites[i].fechaConclusionParse != undefined){
			fechaConclusionAMostrar = new String(arrayTramites[i].fechaConclusionParse);
			break;
		}
	}
	return fechaConclusionAMostrar;
}

function creaGridSolicitudesConsultadas(){
	gridSolicitud = $('#gridSolicitudesConsultadas').dataTable({
		"bJQueryUI" : false,
		"bPaginate" : true,
		"bLengthChange" : false,
		"iDisplayLength" : 5,
		"bServerSide" : true,
		"bProcessing" : true,
		"sPaginationType" : "full_numbers",
		"bFilter" : false,
		"bSort" : true,
		"bInfo" : true,
		"bAutoWidth" : false,
		"aoColumns" : columnasSolicitud,
		"sAjaxSource" : context_path + "/solicitud/consultarSolicitudes",
		"fnServerData" : cargarGridSolicitudesConsultadas
	});

	inicializaEstiloGrid($("#gridSolicitudesConsultadas tbody"), gridSolicitud);
	
}

function cargarGridSolicitudesConsultadas(sSource, aoData, fnCallback) {
	upperCaseDatosCapturados();
	var wrapper = new Object();
	wrapper.oForm = new Object();
	wrapper.oForm = $('#filtroSolicitudForm').serializeObject(true);
	
	
	wrapper.aoData = aoData;
	$.postJSON(sSource, wrapper, function(data) {
		fnCallback(data);
		$('#divResultadosBusqueda').show();
		$.unblockUI();
	});
}

function consultarSolicitudesPorFiltros(){
	if(validaDatosRequeridos()){
		
		if($("#folio").val()!=undefined && $("#folio").val()!='' ){
			//mostrarDetalleSolicitudPorFolio();
//			$("#folio").val("");//Se elimina el folio para no ser contemplado
			refreshGridSolicitudesConsultadas();
		}else{
			refreshGridSolicitudesConsultadas();
		}
		
	}
}

function consultarSolicitudesPorFolio(){
	if(validaDatosRequeridos()){
		
		if($("#folio").val()!=undefined && $("#folio").val()!='' ){
			mostrarDetalleSolicitudPorFolio();
		}else{
			var objDialogo;
			construirDialogoGenerico("#dialogoMensajes", objDialogo, 'Error', 
					'El n\u00FAmero de folio es requerido para la realizar la b\u00FAsqueda.', 
					true, undefined, undefined, 200, 650);
		}
		
	}
}


function validaDatosRequeridos(){
	
	var campoFechaInicioPresentacion = $('#fechaInicioPresentacion');
	var campoFechaFinPresentacion = $('#fechaFinPresentacion');
	var campoFechaInicioConclusion = $('#fechaInicioConclusion');
	var campoFechaFinConclusion = $('#fechaFinConclusion')
	
	var campoFechaPresentacionError = $('#fechaPresentacionError');
	var campoFechaConclusionError = $('#fechaConclusionError');
	
	
	var fechaInicioPresentacion=campoFechaInicioPresentacion.val();
	var fechaFinPresentacion=campoFechaFinPresentacion.val();
	var fechaInicioConclusion=campoFechaInicioConclusion.val();
	var fechaFinConclusion=campoFechaFinConclusion.val();
	
	fnOcultaErrores(campoFechaPresentacionError);
	fnOcultaErrores(campoFechaConclusionError);
	
	var valido=true;
	
	if(!validaFormaFiltroSolicitud())
		valido=false;
//	if(!validaFormatoFechaConclusionFiltroSolicitud())
//		valido=false;
	
	
	
//	if(fechaInicioPresentacion!= undefined && fechaInicioPresentacion!=null
//			&& fechaInicioPresentacion!=""){
//		var temp = new Date(fechaInicioPresentacion);
//		
//		if(!validaFormatoFechaPresentacionFiltroSolicitud()){
////			fnDespliegaError(campoFechaPresentacionError, "Proporcione una fecha de inicio con un formato v&aacute;lido (dd/MM/yyyy), puede seleccionarla mediante el calendario");
//			valido=false;
//		}else if(fechaFinPresentacion==undefined || fechaFinPresentacion==null || fechaFinPresentacion==""){
//			fnDespliegaError(campoFechaPresentacionError, "Proporcione una fecha de fin de captura");
//			valido=false;
//		}
//		
//	}
//	
//	if(fechaFinPresentacion!= undefined && fechaFinPresentacion!=null
//			&& fechaFinPresentacion!=""){
//		var temp = new Date(fechaFinPresentacion);
//		if(!validaFormatoFechaPresentacionFiltroSolicitud()){
//			fnDespliegaError(campoFechaPresentacionError, "Proporcione una fecha de fin con un formato v&aacute;lido (dd/MM/yyyy), puede seleccionarla mediante el calendario");
//			valido=false;
//		}else if(fechaInicioPresentacion==undefined || fechaInicioPresentacion==null || fechaInicioPresentacion==""){
//			fnDespliegaError(campoFechaPresentacionError, "Proporcione una fecha de inicio de captura");
//			valido=false;
//		}
//	}
//	
//	if(fechaInicioConclusion!= undefined && fechaInicioConclusion!=null
//			&& fechaInicioConclusion!=""){
//		var temp = new Date(fechaInicioConclusion);
//		if(!validaFormatoFechaConclusionFiltroSolicitud()){
//			fnDespliegaError(campoFechaConclusionError, "Proporcione una fecha de inicio con un formato v&aacute;lido (dd/MM/yyyy), puede seleccionarla mediante el calendario");
//			valido=false;
//		}else if(fechaFinConclusion==undefined || fechaFinConclusion==null || fechaFinConclusion==""){
//			fnDespliegaError(campoFechaConclusionError, "Proporcione una fecha de fin de conclusi&oacute;n");
//			valido=false;
//		}
//	}
//	
//	if(fechaFinConclusion!= undefined && fechaFinConclusion!=null
//			&& fechaFinConclusion!=""){
//		var temp = new Date(fechaFinConclusion);
//		if(!validaFormatoFechaConclusionFiltroSolicitud()){
//			fnDespliegaError(campoFechaConclusionError, "Proporcione una fecha de fin con un formato v&aacute;lido (dd/MM/yyyy), puede seleccionarla mediante el calendario");
//			valido=false;
//		}else if(fechaInicioConclusion==undefined || fechaInicioConclusion==null || fechaInicioConclusion==""){
//			fnDespliegaError(campoFechaConclusionError, "Proporcione una fecha de inicio de conclusi&oacute;n");
//			valido=false;
//		}
//	}
	
	return valido;
}

function refreshGridSolicitudesConsultadas(){
	$.blockUI();
	if(gridSolicitud==undefined){
		creaGridSolicitudesConsultadas();
	}else{
		gridSolicitud.fnDraw();
	}
}

function crearSolicitudDatePickers(){
	crearPicker($("#fechaInicioPresentacion"));
	crearPicker($("#fechaFinPresentacion"));
	crearPicker($("#fechaInicioConclusion"));
	crearPicker($("#fechaFinConclusion"));
}

function crearPicker(objToAddPicker){
	objToAddPicker.datepicker({
		maxDate: "+1D",
		showOn: "button",
		buttonImage: context_path + "/static/resources/imagenes/calendar.gif",
		buttonImageOnly: true,
		dateFormat: "dd/mm/yy",
		changeMonth: true,
		changeYear: true,
		yearRange: '-112:+0'
	});
}

function creaDialogo(idDialogo, dialogo){
	dialogo = $(idDialogo).dialog({
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

function mostrarDetalleSolicitudSeleccionada(){
	var obRowSelected = fnGetRowSelected(gridSolicitud);
	if (obRowSelected == undefined) {
		dialogoError.dialog('open');
	}else {
		$("#idSolicitud").val(obRowSelected.solicitudId);
		var url = "/solicitud/mostrarDetalleSolicitud";
		document.getElementById('filtroSolicitudForm').action = 
									context_path + url;
		document.getElementById('filtroSolicitudForm').submit();
	}
}

function mostrarDetalleSolicitudPorFolio(){
	$.blockUI();
	var solicitud = new Object();
	solicitud.noFolioSolicitud=$("#folio").val();
	var rfc = $("#rfc").val();
	if(rfc != undefined && rfc != ''){
		solicitud.sujetoObligado = new Object();
		if(rfc.length==12){
			solicitud.sujetoObligado.moral = new Object();
			solicitud.sujetoObligado.moral.rfc=rfc;
			solicitud.sujetoObligado.tipoPersonaFiscal='MORAL';
		}else if(rfc.length==13){
			solicitud.sujetoObligado.fisica = new Object();
			solicitud.sujetoObligado.fisica.rfc=rfc;
			solicitud.sujetoObligado.tipoPersonaFiscal='FISICA';
		}else{
			var objDialogo;
			construirDialogoGenerico("#dialogoMensajes", objDialogo, 'Error', 
					'El rfc capturado no es v\u00E1lido, este debe contener 12 caracteres si pertenece a una persona moral o 13 caracteres si pertenece a una persona f\u00EDsica.', 
					true, undefined, undefined, 200, 650);
			return;
		}
	}
	sendToServer('/solicitud/validarFolioSolicitud',solicitud,callbackDetalleSolicitudPorFolio, true);
}

function callbackDetalleSolicitudPorFolio(response){
	if(!response.error){
		$("#idSolicitud").val(response.idSolicitud);
		var url = "/solicitud/mostrarDetalleSolicitud";
		document.getElementById('filtroSolicitudForm').action = 
									context_path + url;
		document.getElementById('filtroSolicitudForm').submit();
	}else{
		var oDialogo;
		construirDialogoGenerico("#dialogoMensajes", oDialogo, "Notificaci\u00F3n", response.mensajeError, false);
	}
	$.unblockUI();
}

function inicializaCombosManuales(){
	$("#idEstadoSolicitud").val("-1");
}

$(function() {	
	//creaGridSolicitudesConsultadas();
	$('#divResultadosBusqueda').hide();
	crearSolicitudDatePickers();
	creaDialogo("#dgErrorSinSeleccion", dialogoError);
	inicializaCombosManuales();
});


