var dialogoListaTramitesClasificacion;
var index=-1;
var oTableRFC;
var oTableTramites;
var dialogoError;
var idDialogoError = "#dgErrorSinSeleccion";
var idFiltroDef = "#gridPersona_filter";
var idFiltroDefSolicitudes = "#gridSolicitudesProceso_filter";
var existeTramiteCentroTrabajoEnCurso=false;
var columnas = [ 
				 {mDataProp: "rfc", sTitle : "RFC"},
				 {sTitle :"Nombre Denominaci\u00F3n Raz\u00F3n Social", fnRender:fnRenderNombreRazonSocial}
				];

var columnasSol = [
                 {mDataProp: "tramiteId", bVisible: false},
                 {mDataProp: "solicitud.solicitudId", bVisible: false},
				 {mDataProp: "solicitud.noFolioSolicitud", sTitle : "Folio de Solicitud"},
				 {mDataProp: "solicitud.fechaSolicitud", sTitle :"Fecha de Solicitud", sType:"date", fnRender:rederFecha},
				 {mDataProp: "solicitud.estadoSolicitud.descripcion", sTitle :"Estado de Solicitud"},
				 {mDataProp: "tipoTramite.descripcion", sTitle :"Descripci&oacute;n del Tramite"},
				 {mDataProp: "solicitud.solicitudId", bVisible: false},
				 {mDataProp: "tipoTramite.idTipoTramite", bVisible: false}
				];


var columnasSolicitud = [ {
	mDataProp : "solicitudId",
	bVisible : false
}, {
	mDataProp : "tipoSolicitud.idTipoSolicitud",
	bVisible : false
}, {
	mDataProp : "tramites",
	bVisible : false
}, {
	mDataProp : "noFolioSolicitud",
	sTitle : "Folio"
}, {
	mDataProp : "fechaSolicitudParse",
	sTitle : "Fecha de Creaci\u00F3n\n(dd/mm/aaaa)",
}, {
	mDataProp : "tipoSolicitud.descripcion",
	sTitle : "Tipo"
}, {
	sTitle : "Propietario (RP/RFC)",
	"fnRender" : renderOwner
}, {
	sTitle : "Informaci\u00F3n a Modificar",
	"fnRender" : renderTramites
}, {
	mDataProp : "estadoSolicitud.descripcion",
	sTitle : "Estado"
} ];


function renderOwner(oObj) {
	if (oObj.aData.sujetoObligado != null
			&& oObj.aData.sujetoObligado != undefined) {
		if(oObj.aData.sujetoObligado.numeroRegistroPatronal!=undefined)
			return oObj.aData.sujetoObligado.numeroRegistroPatronal+oObj.aData.sujetoObligado.modalidad.numModalidad+oObj.aData.sujetoObligado.digVerificador;
		else if(oObj.aData.sujetoObligado.fisica!=undefined)
			return oObj.aData.sujetoObligado.fisica.rfc!=undefined ? oObj.aData.sujetoObligado.fisica.rfc : "Sin RFC";
		else if(oObj.aData.sujetoObligado.moral!=undefined)
			return oObj.aData.sujetoObligado.moral.rfc!=undefined ? oObj.aData.sujetoObligado.moral.rfc : "Sin RFC";
		else
			return "Sin propietario";
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

function fnRenderNombreRazonSocial(oObj){
	if (oObj.aData.nombre != null
			&& oObj.aData.nombre != undefined) {
		return oObj.aData.nombre+" " + oObj.aData.primerApellido + " " + oObj.aData.segundoApellido;
	} else {
		return oObj.aData.razonSocial;
	}
}

function inicializarGridDeSolicitudesEnProceso() {
	
	oTableTramites = $('#gridSolicitudesProceso').dataTable({
		"bJQueryUI" : false,
		"bPaginate" : true,
		"bLengthChange" : false,
		"iDisplayLength" : 10,
		"bServerSide" : false,
		"sPaginationType" : "full_numbers",
		"bFilter" : true,
		"bSort" : false,
		"bInfo" : false,
		"bAutoWidth" : true,
		"aoColumns" : columnasSolicitud,
		"sAjaxSource" : context_path + "/afiliacion/listarTotalDeSolicitudesEnProceso",
		"fnServerData" : cargarGridSolicitudesProceso
	});

	inicializaEstiloGrid($("#gridSolicitudesProceso tbody"), oTableTramites);
}


function cargarGridSolicitudesProceso(sSource, aoData, fnCallback) {
	var wrapper = new Object();
	wrapper.oForm = new Object();
	wrapper.oForm.sujetoObligado = new Object();
	wrapper.aoData = aoData;
	$.postJSON(sSource, wrapper, function(data) {
		gridCustomCallback(data, fnCallback);
	});
}

function gridCustomCallback(data, fnCallback){
	fnCallback(data);
	$("#oTableTramites");
}


$(document).ready(function() {
	if(!esRepresentante)
		inicializaMenuLateral();
	else{
		
		$("#gridRepresentados").show();
	}
	
	inicializarGridDeSolicitudesEnProceso();
	inicializaDialogoTramitesClasificacion()
	inicializaGridPersona();
	inicializaFiltrosDeGrids();
	
	dialogoError =  $(idDialogoError).dialog({
		autoOpen:false,
		resizable: false,
		height: 175,
		width: 300,
		modal: true,
		buttons: {'Aceptar': function(){ $( this ).dialog( "close" ); }}
	});
	
	$( "#selectable" ).selectable({
		selected: function(event, ui) {
			$(ui.selected).siblings().removeClass("ui-selected");
		},
		stop: function() {
			$( ".ui-selected", this ).each(function() {
				index = $( "#selectable li" ).index(this);
			});
		}
	});
});

function inicializaGridPersona(){
	oTableRFC = $('#gridPersona').dataTable( {
		"bJQueryUI" : false,
		"bPaginate": true,
		"bLengthChange": false,
		"bServerSide" : false,
		"iDisplayLength": 5,
		"sPaginationType": "full_numbers",
		"bFilter": true,
		"bSort": false,
		"bInfo": false,
		"bAutoWidth": true,
		"aoColumns" : columnas,
		"sAjaxSource": context_path + "/sujetoObligado/cargaRFC",
		"fnServerData": cargarGrid
	});
	
	$("#gridPersona tbody").hover(
		function(){
			$(this).css('cursor', 'pointer');
		}
	);
	
	$("#gridPersona tbody").click( function(event) {
		$(oTableRFC.fnSettings().aoData).each(function (){ 
			$(this.nTr).removeClass('row_selected'); 
		});
		if($(event.target.parentNode).hasClass('row_selected')){
			$(event.target.parentNode).removeClass('row_selected');
		}else{
			$(event.target.parentNode).addClass('row_selected');
			var obRowSelected = fnGetRowSelected(oTableRFC);
			$("#hrfc").val(obRowSelected.rfc);
		}
    });
}

function inicializaFiltrosDeGrids(){
	$("#txtBuscar").keyup(function(){
		oTableRFC.fnFilter($("#txtBuscar").val()); 
	});
		
	$("#txtBuscarSol").keyup(function(){
		oTableTramites.fnFilter($("#txtBuscarSol").val()); 
	});
	
	$(idFiltroDef).dialog({
		autoOpen:false
	});
	
	$(idFiltroDefSolicitudes).dialog({
		autoOpen:false
	});
	
}

function inicializaDialogoTramitesClasificacion(){
	dialogoListaTramitesClasificacion =  $("#listaTramitesClasificacion").dialog({
		autoOpen:false,
		resizable: false,
		height: 550,
		width: 400,
		modal: true,
		buttons: {'Continuar': validaSeleccionDeTramiteClasificacion}
	});
}

function cargarGrid(sSource, aoData, fnCallback) {
	var wrapper = new Object();
	wrapper.oForm = new Object();
	wrapper.aoData = aoData;
	$.postJSON(sSource, wrapper, function(data) {
		fnCallback(data);
	});
}

function cargarGridSol(sSource, aoData, fnCallback) {
	var wrapper = new Object();
	wrapper.oForm = new Object();
	wrapper.oForm.cveIdPatronSujetoObligado=document.getElementById('cveIdSujetoObligado').value;
	wrapper.aoData = aoData;
	$.postJSON(sSource, wrapper, function(data) {
		fnCallback(data);
	});
}

function validaSeleccion() {
	var obRowSelected = fnGetRowSelected(oTableRFC);
	if(obRowSelected == undefined){
		dialogoError.dialog('open');
	}else{
		document.getElementById('patronForm').action= context_path +'/sujetoObligado/detalleSujetoObligado';
		document.getElementById('patronForm').submit();
	}
}

function rederFecha(registro){
	var sFecha = new String(registro.aData.solicitud.fechaSolicitud);
	var valores = sFecha.split('T');
	var valores = valores[0].split('-');
	return valores[2]+"/"+valores[1]+"/"+valores[0];
}

function validaSeleccionSol() {
	
	$("#patronForm1 #idTramite").val("");
	$("#patronForm1 #idSolicitud").val("");
	var obRowSelected = fnGetRowSelected(oTableTramites);
	if(obRowSelected == undefined){
		dialogoError.dialog('open');
	}else{
		var solicitudId = obRowSelected.solicitudId;
		
		
		switch(obRowSelected.tipoSolicitud.idTipoSolicitud){
			case actualizacionDatosPatronales:
				if(obRowSelected.estadoSolicitud.idEstadoSolicitud == estadoPresentarseVentailla
						|| obRowSelected.estadoSolicitud.idEstadoSolicitud == estadoProcesarEnBackOffice ){
					$("#detalleSolicitudForm #idSolicitud").val(obRowSelected.solicitudId);
					document.getElementById('detalleSolicitudForm').submit();
				}else{
					$("#patronSolicitud #tipoPersonaFiscal").val(obRowSelected.sujetoObligado.tipoPersonaFiscal);
					if(obRowSelected.sujetoObligado.fisica!=undefined){
						$("#patronSolicitud #fisica\\.rfc").val(obRowSelected.sujetoObligado.fisica.rfc);	
					}
					if(obRowSelected.sujetoObligado.moral!=undefined){
						$("#patronSolicitud #moral\\.rfc").val(obRowSelected.sujetoObligado.moral.rfc);
					}
					document.getElementById('patronSolicitud').action=context_path +'/afiliacion/mostrarTramites?idSolicitud='+solicitudId;
					document.getElementById('patronSolicitud').submit();
				}
				break;
			case modificacionSRT:
				var arrayTramites = obRowSelected.tramites;
				var maxValues = obRowSelected.tramites.length;
				var i = 0;
				var idTramite=0;
				for (i = 0; i < maxValues; i++) {
					 idTramite = arrayTramites[i].tramiteId;
				}
				$("#patronForm1 #idSolicitud").val(solicitudId);
				$("#patronForm1 #idTramite").val(idTramite);
				document.getElementById('patronForm1').action= context_path +'/clasificacion';
				document.getElementById('patronForm1').submit();
				break;	
			case centroTrabajo:
				if(obRowSelected.estadoSolicitud.idEstadoSolicitud == estadoPresentarseVentailla
						|| obRowSelected.estadoSolicitud.idEstadoSolicitud == estadoProcesarEnBackOffice ){
					$("#detalleSolicitudForm #idSolicitud").val(obRowSelected.solicitudId);
					document.getElementById('detalleSolicitudForm').submit();
				}else{
					navegarDetalleCT(solicitudId, obRowSelected);
				}
				break;	
			case altaPatronal:
				document.getElementById('patronSolicitud').action=context_path +'/afiliacion/alta/cargarSolicitud?idSolicitud='+solicitudId;
				document.getElementById('patronSolicitud').submit();
				break;
		}
		
	}
}

function navegarDetalleCT(idSolicitud,obRowSelected) {
	var so = obRowSelected.sujetoObligado;	
	var regPatronal = so.numeroRegistroPatronal + so.modalidad.numModalidad;
	$('#centroTrabajoInvokerForm #numeroRegistroPatronal').val(regPatronal);
//		$('#centroTrabajoInvokerForm #tipoPersonaFiscal').value = obRowSelected.tipoPersonaFiscal;
//		if(obRowSelected.fisica!=undefined)
//			$('#centroTrabajoInvokerForm #fisica\\.idPersona').value = obRowSelected.fisica.idPersona;
//		if(obRowSelected.moral!=undefined)
//			$('#centroTrabajoInvokerForm #moral\\.idPersona').value = obRowSelected.moral.idPersona;
		
	$.blockUI();
//		navegarTo('/afiliacion/cargarTramiteCentroTrabajo?idSolicitud='+idSolicitud,
//				'centroTrabajoInvokerForm');
		
	navegarTo('/clasificacion?idTramite='+nombreTramiteCentroTrabajo+'&idSolicitud='+idSolicitud,
				'centroTrabajoInvokerForm');
}

function mostrarPantallaEspera(){
	$.blockUI();
}

function invokarCUConsultaSolicitud(){
	document.getElementById('patronForm').action= context_path +'/solicitud';
	document.getElementById('patronForm').submit();
}

function crearSolicitudAfiliacion(){
	var rfc = $("#patronForm #rfc").val();
	if(rfc==""){
		var oDialogo;
		construirDialogoGenerico("#dialogoMensajes", oDialogo, "Error", "De proporcionar un Registro Federal de Causantes.", true, undefined, undefined, 150, 450)
		return false;
	}else{
		mostrarPantallaEspera();
		sendToServer("/sujetoObligado/validaRFCExistente", rfc, callbackCrearSolicitud,false);
	}
}

function callbackCrearSolicitud(response){
	if(response.mensajeError!=undefined){
		var oDialogo;
		construirDialogoGenerico("#dialogoMensajes", oDialogo, "Error", response.mensajeError, true, undefined, undefined, 150, 450)
		$.unblockUI();
		return;
	}else{
		$("#solicitudAfiliacionForm #tipoPersonaFiscal").val(response.sujetoObligado.tipoPersonaFiscal);
		if(response.sujetoObligado.tipoPersonaFiscal=="FISICA"){
			$("#solicitudAfiliacionForm #fisica\\.rfc").val(response.sujetoObligado.fisica.rfc);
		}else{
			$("#solicitudAfiliacionForm #moral\\.rfc").val(response.sujetoObligado.moral.rfc);
		}
		$("#solicitudAfiliacionForm").submit();
	}
}

function crearTramiteClasificacion(){
	var valorCampoRegistroPatronal = $("#numRegistroPatronal").val().toUpperCase();
	
	if(valorCampoRegistroPatronal.length==10 || valorCampoRegistroPatronal.length==11){
		$("#solicitudClasificacionForm #numeroRegistroPatronal").val(valorCampoRegistroPatronal);
		var sujeto = new Object();
		sujeto.numeroRegistroPatronal = valorCampoRegistroPatronal;
		sendToServer('/afiliacion/validaRegistroPatronalExistente', 
				sujeto, callbackValidaRP, false);
		
	}else{
		var oDialogoGenerico;
		construirDialogoGenerico("#dialogoMensajes", oDialogoGenerico, "Aviso", "El registro patronal proporcionado es inv\u00E1lido. <br>Proporcione un registro v\u00E1lido a 10 u 11 posiciones.", true, undefined, undefined, 150, 400);
		return false;
	}
}

function callbackValidaRP(response){
	if(response.mensajeError != undefined
			&& response.mensajeError != null) {
		titulo = "Operaci&oacute;n Erronea";
		error = true;
		if (response.mensajeError == "") {
			mensaje = "Ocurrio un error con el servidor.";
		} else {
			mensaje = response.mensajeError;
		}
		var oDialogoGenerico;
		construirDialogoGenerico("#dialogoMensajes", oDialogoGenerico, titulo, mensaje, error, undefined, undefined, 150, 400);
	} else {
		
		validaTramiteClasificacionExiste();
		
		
	}
}

function validaSeleccionDeTramiteClasificacion() {
	if(index != -1){
		var idSolicitud="";
		var idTramite = $( "#selectable li" )[index].id;
		dialogoListaTramitesClasificacion.dialog('close');
		navegarTo('/clasificacion?idTramite='+ idTramite +'&idSolicitud=', 'solicitudClasificacionForm');
		$.blockUI();	
	}else{
		var oDialogoGenerico;
		construirDialogoGenerico("#dialogoMensajes", oDialogoGenerico, "Aviso", "Debe seleccionar el tr\u00E1mite que desea realizar.", true, undefined, undefined, 150, 400);
	}
}

function desplegarCentroTrabajo(){
	var valorCampoRegistroPatronal = $('#numRegistroPatronal').val().toUpperCase();
	if(valorCampoRegistroPatronal.length==8 || valorCampoRegistroPatronal.length==10 || valorCampoRegistroPatronal.length==11){
		var sujeto = new Object();
		sujeto.numeroRegistroPatronal = valorCampoRegistroPatronal;
		sendToServer('/afiliacion/validaRegistroPatronalExistente', 
				sujeto, callbackValidaRPCentroTrabajo, false);
		
	}else{
		var oDialogoGenerico;
		construirDialogoGenerico("#dialogoMensajes", oDialogoGenerico, "Aviso", "El registro patronal proporcionado es inv\u00E1lido. <br>Proporcione un registro v\u00E1lido a 10 u 11 posiciones.", true, undefined, undefined, 150, 400);
		return false;
	}
	
	
}


function callbackValidaRPCentroTrabajo(response){
	
	if(response.mensajeError != undefined
			&& response.mensajeError != null) {
		titulo = "Operaci&oacute;n Erronea";
		error = true;
		if (response.mensajeError == "") {
			mensaje = "Ocurrio un error con el servidor.";
		} else {
			mensaje = response.mensajeError;
		}
		var oDialogoGenerico;
		construirDialogoGenerico("#dialogoMensajes", oDialogoGenerico, titulo, mensaje, error, undefined, undefined, 150, 400);
	} else {
		var valorCampoRegistroPatronal = $('#numRegistroPatronal').val();
		$('#centroTrabajoInvokerForm #numeroRegistroPatronal').val(valorCampoRegistroPatronal);
		navegarTo('/afiliacion/mostrarCentroTrabajo', 'centroTrabajoInvokerForm');		
	}
}



function invocarDetalleRFC(){
	var rfc = $("#patronForm #rfc").val().toUpperCase();
	if(rfc==""){
		var oDialogo;
		construirDialogoGenerico("#dialogoMensajes", oDialogo, "Error", "De proporcionar un Registro Federal de Causantes.", true, undefined, undefined, 150, 450);
		return false;
	}else{
		if(rfc.lenght!=12 && rfc.lenght!=13){
			$.blockUI();
			sendToServer("/sujetoObligado/validaRFCExistente", rfc, callbackInvocarDetalleRFC,false);
		}else{
			var oDialogoGenerico;
			construirDialogoGenerico("#dialogoMensajes", oDialogoGenerico, "Aviso", "El registro federal de causantes debe contener 12 o 13 caracteres. <br>Proporcione un registro v\u00E1lido.", true, undefined, undefined, 150, 400);
			return false;
		}
	}
}

function callbackInvocarDetalleRFC(response){
	if(response.mensajeError!=undefined){
		var oDialogo;
		construirDialogoGenerico("#dialogoMensajes", oDialogo, "Error", response.mensajeError, true, undefined, undefined, 150, 450)
		$.unblockUI();
		return;
	}else{
		var rfcMayus = $("#patronForm #rfc").val().toUpperCase();
		$("#patronForm #rfc").val(rfcMayus);
		$("#patronForm").submit();
	}
}

function validaTramiteClasificacionExiste() {
	var sSource = '/sujetoObligado/validarTramiteClasificacionEnCurso';
	
	var valorCampoRegistroPatronal = $("#numRegistroPatronal").val().toUpperCase();
	
	if(valorCampoRegistroPatronal.length==10 || valorCampoRegistroPatronal.length==11){
		sendToServer(sSource, 
				valorCampoRegistroPatronal, callbackValidaTramiteClasificacionEnCurso, false);
	}else{
		var oDialogoGenerico;
		construirDialogoGenerico(
				"#dialogoMensajes",
				oDialogoGenerico,
				"Aviso",
				"El registro patronal proporcionado es inv\u00E1lido. <br>Proporcione un registro v\u00E1lido a 10 u 11 posiciones.",
				true, undefined, undefined, 150, 400);
		return false;
	}
	
}

function callbackValidaTramiteClasificacionEnCurso(response){
		if(!response.existeTramiteClasificacion){
			index=-1;
			dialogoListaTramitesClasificacion.dialog('open');
		}else{
			var oDialogoGenerico;
			construirDialogoGenerico(
					"#dialogoMensajes",
					oDialogoGenerico,
					"Error",
					"Existe un tr\u00E1mite de modificaci\u00F3n al SRT en curso espere a su conclusi\u00F3n para iniciar un nuevo tr\u00E1mite.",
					true, undefined, undefined, 150, 400);
		}
			
}

function navegarADetalleDeRP(){
	var valorCampoRegistroPatronal = $("#numRegistroPatronal").val().toUpperCase();
	
	if(valorCampoRegistroPatronal.length==10 || valorCampoRegistroPatronal.length==11){
		$("#solicitudClasificacionForm #numeroRegistroPatronal").val(valorCampoRegistroPatronal);
		var sujeto = new Object();
		sujeto.numeroRegistroPatronal = valorCampoRegistroPatronal;
		sendToServer('/afiliacion/validaRegistroPatronalExistente', 
				sujeto, callbackValidaRPDetalle, false);
		
	}else{
		var oDialogoGenerico;
		construirDialogoGenerico(
				"#dialogoMensajes",
				oDialogoGenerico,
				"Aviso",
				"El registro patronal proporcionado es inv\u00E1lido. <br>Proporcione un registro v\u00E1lido a 10 u 11 posiciones.",
				true, undefined, undefined, 150, 400);
		return false;
	}
}

function callbackValidaRPDetalle(response) {
	if (response.mensajeError != undefined && response.mensajeError != null) {
		titulo = "Operaci&oacute;n Erronea";
		error = true;
		var height=150;
		var width=400;
		if (response.mensajeError == "") {
			mensaje = "Ocurrio un error con el servidor.";
		} else {
			mensaje = response.mensajeError;
		}
		
		if(response.mensajeError.length > 100){
			height=200;
			width=550;
		}
		
		var oDialogoGenerico;
		construirDialogoGenerico("#dialogoMensajes", oDialogoGenerico, titulo,
				mensaje, error, undefined, undefined, height, width);
	} else {
		$('#registrosPatronalesForm #numeroRegistroPatronal').val(
				$('#numRegistroPatronal').val());
		navegarTo('/afiliacion/mostrarDetalleRegistroPatronal',
				'registrosPatronalesForm');
	}
}

function despliegaBusquedaRFC(){
	$("#divInformacionRFC").show();
	$("#btnInvDetalleRFC").show();
	$("#btnCrearSolAfil").hide();
	$("#divRp").hide();
	$("#divSolicitudes").hide();
}

function despliegaCrearSolicitudAfiliacion(){
	$("#divInformacionRFC").show();
	$("#btnInvDetalleRFC").hide();
	$("#btnCrearSolAfil").show();
	$("#divRp").hide();
	$("#divSolicitudes").hide();
}


function despliegaBusquedaRP(){
	$("#divInformacionRFC").hide();
	$("#btnInvDetalleRFC").hide();
	$("#btnCrearSolAfil").hide();
	$("#divRp").show();
	$("#btnConsultarDetalleRp").show();
	$("#btnCrearTramiteModSRT").hide();
	$("#btnModificarCentroTrabajo").hide();
	$("#divSolicitudes").hide();
}

function despliegaModificacionSRT(){
	$("#divInformacionRFC").hide();
	$("#btnInvDetalleRFC").hide();
	$("#btnCrearSolAfil").hide();
	$("#divRp").show();
	$("#btnConsultarDetalleRp").hide();
	$("#btnCrearTramiteModSRT").show();
	$("#btnModificarCentroTrabajo").hide();
	$("#divSolicitudes").hide();
}

function despliegaModificacionCentroTrabajo(){
	$("#divInformacionRFC").hide();
	$("#btnInvDetalleRFC").hide();
	$("#btnCrearSolAfil").hide();
	$("#divRp").show();
	$("#btnConsultarDetalleRp").hide();
	$("#btnCrearTramiteModSRT").hide();
	$("#btnModificarCentroTrabajo").show();
	$("#divSolicitudes").hide();
}

function despliegaBusquedaSolicitud(){
	$("#divInformacionRFC").hide();
	$("#btnInvDetalleRFC").hide();
	$("#btnCrearSolAfil").hide();
	$("#divRp").hide();
	$("#btnConsultarDetalleRp").hide();
	$("#btnCrearTramiteModSRT").hide();
	$("#btnModificarCentroTrabajo").hide();
	$("#divSolicitudes").show();
}