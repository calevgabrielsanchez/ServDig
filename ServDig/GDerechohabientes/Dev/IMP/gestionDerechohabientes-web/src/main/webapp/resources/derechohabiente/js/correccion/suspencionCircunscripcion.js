/**
 * Mario Teran Blanco
 */
$.ajaxSetup({ cache: false }); 
var guardarValidacion=0;
var guardarRegistro = false;
$(document).ready(
	function() {
		$("#regresar").hide();
		$("#aceptar").click(
			function() {
				if(guardarRegistro)
					guardarCircunscripcion();
				else{
					if($("#validacion").val() == 0){
						var requiereDocs = $("#requiereDocs").val() == "1";
						if(requiereDocs){
							if(fileUploadFinish)
								guardarCircunscripcion();
							else
								mensajeConfirmacion('Debe completar la documentaci\u00F3n probatoria para poder guardar la validaci\u00F3n');
						}else{
							guardarCircunscripcion();
						}
						
					} else {
						aceptarConfirmacion();
					}
					
				}
					
			}
		);
		
		$("#regresar").click(
			function(){
				$("#regresar").hide();
				guardarRegistro=false;
				$("#regresarGrupoFamiliar").show();	
				$("#mensajeConfirmacion").text('');
				if($("#validacion").val() == 0)
					regresarDocumentacion();
			}	
		);
		
		$("#cancelar").click(
			function() {
				cancelarCorreccion();
			}
		);
		
		var validar = $("#validacion").val();
		if(validar==0){
			guardarRegistro=false;
			colocarBotones(false);
		}else{
			guardarRegistro=true;
			colocarBotones(true);
		}	
		
		deshabilitaCampos();
	  //muestro botones de registro o validacion
		
		// ------------------------------------------
		// Limites para text area de observaciones
		// ------------------------------------------
		asignartextAreaLimites("observacion",{styles:{}});
			
		initDomiciliosSuspension();
	}
	
);

function aceptarConfirmacion(){
	aceptarValidacionC();
	guardarValidacion=true;
	guardarRegistro=true;
	
    $("#regresarGrupoFamiliar").hide();
    $("#regresar").show();
    $("#cancelar").hide();
    
    //$("#guiaTramite").hide()
}


function deshabilitaCampos() {
	$("form#correccionDatos input:text").each(
			function(index) {
				$(this).attr("disabled","disabled");
			}
		);
		
		$("form#correccionDatos select").each(
				function(index) {
					$(this).attr("disabled","disabled");
				}
			);
}

function habilitaCampos() {
	$("form#correccionDatos input:text").each(
			function(index) {
				$(this).removeAttr("disabled");
			}
		);
		
		$("form#correccionDatos select").each(
				function(index) {
					$(this).removeAttr("disabled");
				}
			);
}

function guardarCircunscripcion() {

	$decision = $('<div></div');

	$decision.dialog({
		autoOpen : false,
		resizable : false,
		height : 160,
		title : 'Seleccione una opci\u00F3n',
		modal : true,
		buttons : {
			"Si" : function() {
				$("#regresar").hide();
				cierraDialogo($(this));
				fnAbrirMensajeEsperePorFavor();
				procesarSuspencion();
			},
			"No" : function() {
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.text('\u00BF Est\u00E1 seguro que desea guardar el tr\u00E1mite de circunscripci\u00F3n for\u00E1nea?');
	$decision.dialog('open');
}

function procesarSuspencion() {
	var ajax_source = context_path + "/derechohabiente/correccion/circunscripcion/suspencion/guardar";
	
	$("form#correccionDatos").attr("action",ajax_source);
	$("#observacion").removeAttr("disabled");
	$("form#correccionDatos").submit();

}

function colocarBotones(validacion){
	if(validacion){
		$("#aceptarValidacion").show();
		$("#rechazarValidacion").show();
		$("#regresarGrupoFamiliar").show();
		$("#guiaTramite").show();
		$("#aceptar").hide();
		$("#cancelar").hide();
	}else{
		$("#aceptarValidacion").hide();
		$("#rechazarValidacion").hide();
		$("#regresarGrupoFamiliar").hide();
		$("#guiaTramite").hide();
		$("#aceptar").show();
		$("#cancelar").show();
	}
	
}

function botonesValidacion(){
	if(guardarValidacion==0){
		$("#aceptarValidacion").hide();
		$("#rechazarValidacion").hide();
		$("#regresarGrupoFamiliar").hide();
		$("#guiaTramite").hide();
	}
	
}

function cancelarCorreccion() {
	
	location.href = "" + context_path + "/derechohabiente/correccion/circunscripcion/suspencion";
	
}

function showComprobanteSuspencion(idTramite, idPersona){
	
	var direccion= "";
	if($("#idUmfDest").val() != $("#umfUsuario").val())
		direccion = context_path + "/documentos/circunscripcionS?idTramite="+idTramite+"&idPersona="+idPersona+"&titulo=Suspensi\u00F3n de Servicios en circunscripci\u00F3n for\u00E1nea";
	else
		direccion = context_path + "/documentos/documentosAutorizacion?idPersona="+idPersona+"&ind=0";
	
	var page=context_path + "/resources/js/delta/viewPdf.html";
	window.showModalDialog(page,direccion,"resizable:1;dialogHeight:550px;dialogwidth:985px;scroll:yes;status=no");	
}

function finalizarTramiteSuspencion(mensaje, idTramite, idTramiteSuspencion) {
	$decision = $('<div></div');
	$decision.dialog({
		autoOpen : false,
		resizable : false,
		height : 160,
		width:300,
		title : 'Resultado',
		modal : true,
		buttons : {
			"Aceptar" : function() {
				if(idTramite != null || idTramite != undefined) {
					doSaveDocSinTramite(idTramiteSuspencion);
					cierraDialogo($(this));
					showComprobanteSuspencion(idTramite,$("#idPersona").val());
					location.href = "" + context_path + "/inicio/grupoFamiliar";
				} else {
					location.href = "" + context_path + "/inicio/grupoFamiliar";
				}
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.html(mensaje);
	$decision.dialog('open');
}

function colocarMensaje(mensaje){
	
	$decision = $('<div></div');
	$decision.dialog({
		autoOpen : false,
		resizable : false,
		height : 160,
		width:300,
		title : 'Advertencia',
		modal : true,
		buttons : {
			"Aceptar" : function() {
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.html(mensaje);
	$decision.dialog('open');
}


function aceptarValidacionC(){
	var mensaje= '<div class="ui-widget">' +
	'<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> ' +
	'<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>' +
	'Por favor verifique la informaci\u00F3n proporcionada y de clic en Aceptar para continuar con el registro.'+
	'Si requiere corregir datos de clic en Regresar.</p></div></div>';
	$("#mensajeConfirmacion").html(mensaje);
	mensajeConfirmacion(mensaje);
	if($("#validacion").val() == 0)
		cargaFinalizada();
}

function mensajeConfirmacion(mensaje) {
	
	$confirmacion = $('<div></div');
	$confirmacion.html(mensaje);
	$confirmacion.dialog({
		autoOpen : false,
		title: '',
		show: "blind",
		hide: "explode",
		resizable: false,
		modal: true,
		width: 500,
		buttons: {
			'Aceptar' : function () {
				cierraDialogo($(this));
			}
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	$confirmacion.dialog('open');
}

function regresarDocumentacion(){

	var lenght=doctosCargadosLenght+1;
	$('#thAccion').show();
	for(var i=0;i<lenght;i++){
		$('#eliminaDoc'+i).show();
	}
	
	$('#fileUploadMessages').html("");
}

function cierraDialogo($dialogo){
	$dialogo.dialog('close');
	$dialogo.dialog('destroy');
	$dialogo.html('');
}