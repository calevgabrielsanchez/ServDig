var index=-1;
var oTableTramites;
var idTramites = "#tramites";
var indicadorTramiteClasifExistente;
var dialogoError;
var idDialogoError = "#dgErrorSinSeleccion";
var idDialogoTramiteExistente="#tramitesClasificacionExistentes";
var dialogoNoProcedeTramite;
var idDialogoNoProcedeTramite = "#dgDialogoNoProcedeTramite";
var idFiltroDef = "#gridSolicitudes_filter";
var columnas = [
                 {mDataProp: "tramiteId", bVisible: false},
                 {mDataProp: "solicitud.solicitudId", bVisible: false},
				 {mDataProp: "solicitud.noFolioSolicitud", sTitle : "Folio de Solicitud"},
				 {mDataProp: "solicitud.fechaSolicitud", sTitle :"Fecha de Solicitud", sType:"date", fnRender:rederFecha},
				 {mDataProp: "solicitud.estadoSolicitud.descripcion", sTitle :"Estado de Solicitud"},
				 {mDataProp: "tipoTramite.descripcion", sTitle :"Descripci&oacute;n del Tramite"},
				 {mDataProp: "solicitud.solicitudId", bVisible: false},
				 {mDataProp: "tipoTramite.idTipoTramite", bVisible: false}
				];

function rederFecha(registro){
	var sFecha = new String(registro.aData.solicitud.fechaSolicitud);
	var valores = sFecha.split('T');
	var valores = valores[0].split('-');
	return valores[2]+"/"+valores[1]+"/"+valores[0];
}

$(document).ready(function() {

	oTableTramites = $('#gridSolicitudes').dataTable( {
		"bJQueryUI": false,
		"bPaginate": true,
		"bLengthChange": false,
		"iDisplayLength": 5,
		"sPaginationType": "full_numbers",
		"bFilter": true,
		"bSort": false,
		"bInfo": false,
		"bAutoWidth": true,
		"aoColumns" : columnas,
		"sAjaxSource": "cargaSolicitudes",
		"fnServerData": cargarGrid
	});
	
	$("#gridSolicitudes tbody").click( function(event) {
		$(oTableTramites.fnSettings().aoData).each(function (){ 
			$(this.nTr).removeClass('row_selected'); 
		});
		if($(event.target.parentNode).hasClass('row_selected')){
			$(event.target.parentNode).removeClass('row_selected');
		}else{
			$(event.target.parentNode).addClass('row_selected');
			var obRowSelected = fnGetRowSelected(oTableTramites);
			$("#numSolicitud").val(obRowSelected.tramiteId);
			$("#idSolicitud").val(obRowSelected.solicitudId);
		}
    });
	
	$("#txtBuscar").keyup(function(){
		oTableTramites.fnFilter($("#txtBuscar").val()); 
	});
	
	$(idFiltroDef).dialog({
		autoOpen:false
	});
	
	dialogoError =  $(idDialogoError).dialog({
		autoOpen:false,
		resizable: false,
		height: 175,
		width: 300,
		modal: true,
		buttons: {'Aceptar': function(){ $( this ).dialog( "close" ); }}
	});
	
	dialogoNoProcedeTramite =  $(idDialogoNoProcedeTramite).dialog({
		autoOpen:false,
		resizable: false,
		height: 300,
		width: 300,
		modal: true,
		buttons: {'Aceptar': function(){ $( this ).dialog( "close" ); }}
	});

	dialogoTramites =  $(idTramites).dialog({
		autoOpen:false,
		resizable: false,
		height: 550,
		width: 400,
		modal: true,
		buttons: {'Continuar': validaSeleccion}
	});
	
	dialogoTramiteClasifExistente = $(idDialogoTramiteExistente).dialog({
		autoOpen:false,
		resizable: false,
		height: 175,
		width: 400,
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

function abrirTramites(){
	validaTramiteExiste();
	if(indicadorTramiteClasifExistente=='true' || indicadorTramiteClasifExistente==true){
		dialogoTramiteClasifExistente.dialog('open');
	}else{
		dialogoTramites.dialog('open');
	}
}

function validaSeleccion() {
	if(index != -1){
		$("#idSolicitud").val("");
		$("#idTramite").val($( "#selectable li" )[index].id);
		dialogoTramites.dialog('close');
		navegar(context_path +'/clasificacion/');
		
	}
}

function navegar(url, tipoTramiteCodigo){

var proceder = true;

	if (tipoTramiteCodigo != undefined){
		$(oTableTramites.fnSettings().aoData).each(function (){ 
			//alert("codigo: " + tipoTramiteCodigo);
			if (this._aData.tipoTramite.idTipoTramite == tipoTramiteCodigo){
						//alert("id de tramite para actualizar rep legal: " + this._aData.tipoTramite.idTipoTramite + ", no se puede proceder ya que hay un tamite en curso");
						$('#dgDialogoNoProcedeTramite').attr('tittle', this._aData.tipoTramite.descripcion);
						proceder = false;
						return;
					}
		});
	}
	
		
		
		if (proceder){
			
			document.getElementById('patronForm').action=url;
			document.getElementById('patronForm').submit();
		}else{
			dialogoNoProcedeTramite.dialog('open');
		}
		

}

function cargarGrid(sSource, aoData, fnCallback) {
	var wrapper = new Object();
	wrapper.oForm = new Object();
	wrapper.oForm.cveIdPatronSujetoObligado=document.getElementById('cveIdSujetoObligado').value;
	wrapper.aoData = aoData;
	$.postJSON(sSource, wrapper, function(data) {
		fnCallback(data);
	});
}

function validaSeleccionSol() {
	$("#idTramite").val("");
	$("#idSolicitud").val("");
	var obRowSelected = fnGetRowSelected(oTableTramites);
	if(obRowSelected == undefined){
		dialogoError.dialog('open');
	}else{
		$("#idTramite").val(obRowSelected.tipoTramite.idTipoTramite);
		$("#idSolicitud").val(obRowSelected.solicitud.solicitudId);
		
		switch(obRowSelected.tipoTramite.idTipoTramite){
		
			case denominacionSocial:
				document.getElementById('patronForm').action= context_path +'/sujetoObligado/tramiteDenominacionSocial';
				break;
			case datosContacto:
				document.getElementById('patronForm').action= context_path +'/sujetoObligado/tramiteDatosContacto';
				break;
			case escrituraConstitutiva:
				document.getElementById('patronForm').action= context_path +'/sujetoObligado/tramiteActaconstitutiva';
				break;
			case registroSindicato:
				document.getElementById('patronForm').action= context_path +'/sujetoObligado/tramiteRegistroSindicato';
				break;
			case socio:
				document.getElementById('patronForm').action= context_path +'/socios';
				break;
			case representanteLegal:
				document.getElementById('patronForm').action= context_path +'/representanteLegal';
				break;
			default:
				document.getElementById('patronForm').action= context_path +'/clasificacion';
				break;	
		}
		document.getElementById('patronForm').submit();
	}
}


function validaTramiteExiste(){
	var sSource = context_path +'/sujetoObligado/validaTramiteClasificacionExistente';	
	var request = $.ajax({
		url: sSource,
		async : false,
		type: "POST",
		data: idSujetoObligado ? JSON.stringify(idSujetoObligado) : null,
		dataType: "json",
        contentType: "application/json; charset=utf-8",
	});
	request.done(function(response){
		if(response.errors != undefined){
			alert( response.errors );
		}else{
			indicadorTramiteClasifExistente = response.existeTramiteClasificacion;
		}
	});
	
}
