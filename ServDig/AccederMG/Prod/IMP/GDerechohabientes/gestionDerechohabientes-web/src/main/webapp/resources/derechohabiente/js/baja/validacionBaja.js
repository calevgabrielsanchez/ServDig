/**
 * @author Mario Teran Blanco
 * Script para la validacion de la baja de derechohabiente
 * 28/05/2012
 */

CONFIRMACION = false;
DOCUMENTACION = false;
BAJA_DEFUNCION = 25;
BAJA_CONCUBINATO = 26;
BAJA_DIVORCIO = 27;
BAJA_CONVIVENCIA = 28;

$(document).ready(
	function() {
		
		$('#aceptar').click(function() {
			confirmacionValidacBa();
		});
		
		if(typeof($('#fechaDefuncion').val()) != "undefined") {
			
			var a = $("#validacion").validate({ 
				rules: {
					fechaDefuncion: {
						required:true,
						validDate : true,
						validDateToDay : true
					},
					observaciones: {
						required: true,
						maxlength: 255
					}
				}, 
				errorLabelContainer: "#warning", 
				messages: { 
					fechaDefuncion: {required:"Obligatorio", validDate : "Fecha inv\u00e1lida", validDateToDay : "La fecha no puede ser mayor a hoy."},
					observaciones:{required: "Obligatorio", maxlength: "Debe ser maximo de 255 caracteres(tomando en cuenta espacios)"}
				} 
			}); 
			
			
			$.datepicker.setDefaults({
				onClose: function(){
					$(this).valid();
				}
			});
			
			$("#fechaDefuncion").mask("99/99/9999");
			
			$("#fechaDefuncion").datepicker({
				dateFormat : 'dd/mm/yy',
				changeMonth : true,
				changeYear : true,
				maxDate: new Date(), 
				yearRange : '-112:+0'
			});
			
			
		} else {
			var a = $("#validacion").validate({ 
				rules: {
					observaciones: {
						required: true,
						maxlength: 255
					}
				}, 
				errorLabelContainer: "#warning", 
				messages: { 
					observaciones:{required: "Obligatorio", maxlength: "Debe ser maximo de 255 caracteres(tomando en cuenta espacios)"}
				} 
			}); 
			
		}
		
		DOCUMENTACION = isDefined("fileUploadFinish") ? true : false;
		
		asignartextAreaLimites("observaciones");
			
	}	
);

function confirmacionValidacBa() {
	
	$decision = $('<div></div');
	
	$decision.dialog({
		autoOpen : false,
		resizable: false,
		height: 180,
		title: 'Selecciona una opci\u00F3n',
		modal: true,
		buttons: {
			"Si": function() {
				cierraDialogo($(this));
				irConfirmacion();
			},
			"No": function() {
				cierraDialogo($(this));
			}
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	$decision.text('\u00BF Est\u00E1 seguro que desea finalizar el tr\u00E1mite de baja?');
	$decision.dialog('open');
}

var aceptar = function () {
	//if(!CONFIRMACION)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
		irConfirmacion();
	/*else
		procesarValidacionBaja();*/
};




function irConfirmacion() {
	if($('#idTipoTramite').val() == 25) {
		if($('#fechaDefuncion').val() != undefined) {
			var validacionform = $("form#validacion").valid();
			if(validacionform){
				if(DOCUMENTACION) {
					if(fileUploadFinish)
						//confirmacion();
						procesarValidacionBaja();
					else
						errorDocumentacion('Debe completar la documentaci\u00F3n probatoria para poder guardar la validaci\u00F3n');
				} else {
					//confirmacion();
					procesarValidacionBaja();
				}
			}
		}
	}else {
		var validacionform = $("form#validacion").valid();
		if(validacionform) {
			if(DOCUMENTACION) {
			
				if(fileUploadFinish) {
					//confirmacion();
					procesarValidacionBaja();
				}
				else
					errorDocumentacion('Debe completar la documentaci\u00F3n probatoria para poder guardar la validaci\u00F3n');
			}else {
				//confirmacion();
				procesarValidacionBaja();
			}
		}
	}
}

function confirmacion() {
	CONFIRMACION = true;
	if($('#fechaDefuncion').val() != undefined) {
		$("#fechaDefuncion").attr("disabled","disabled");
	}
	$("#observaciones").attr("disabled","disabled");
	$("#guia").hide();
	aceptarValidacion();
	if(DOCUMENTACION) {
		cargaFinalizada();
	}
}

function regresarValidacion() {
	CONFIRMACION = false;
	if($('#fechaDefuncion').val() != undefined) {
		$("#fechaDefuncion").removeAttr("disabled");
		$("#fechaDefuncion").attr("readonly","readonly");
	}
	$("#observaciones").removeAttr("disabled");
	$("#guia").show();
	$("#mensaje").html("");
	if(DOCUMENTACION)
		regresarDocumentacion();
}

function inicializarValidacionBaja() {
	
	
	if($('#idTipoTramite').val() == 25) {
		if($('#fechaDefuncion').val() != undefined) {
			var validacionform = $("form#validacion").valid();
			if(validacionform) {
				if(fileUploadFinish)
					procesarValidacionBaja();
				else
					errorDocumentacion('Debe completar la documentaci\u00F3n probatoria para poder guardar la validaci\u00F3n');
			}
		}
	}else {
		if(DOCUMENTACION) {
			
			if(fileUploadFinish) {
				procesarValidacionBaja();
			}
			else
				errorDocumentacion('Debe completar la documentaci\u00F3n probatoria para poder guardar la validaci\u00F3n');
		}else {
			procesarValidacionBaja();
		}
	}
}

function errorDocumentacion(mensaje) {
	$documentacion = $('<div></div');

	$documentacion.dialog(
		opcionesError($documentacion)	
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$documentacion.text(mensaje);
	$documentacion.dialog('open');
}

function opcionesError($dialogo) {
	var opciones = {
			autoOpen : false,
			resizable : false,
			height : 200,
			title : 'Error',
			modal : true,
			buttons : {
				"Aceptar" : function() {
					cierraDialogo($dialogo);
				}
			}
		}
	
	return opciones;
}

function regresarGrupoFamiliar() {

	$decision = $('<div></div');

	$decision.dialog({
		autoOpen : false,
		resizable : false,
		height : 140,
		title : 'Selecciona una opci\u00F3n',
		modal : true,
		buttons : {
			"Si" : function() {
				cierraDialogo($(this));
				$.blockUI();
				location.href = context_path + "/inicio/grupoFamiliar";
			},
			"No" : function() {
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.text('\u00BF Est\u00E1 seguro que desea salir del tr\u00E1mite de baja?');
	$decision.dialog('open');
}

function procesarValidacionBaja() {
	guardarBaja();
}

function guardarBaja() {
	
	$.blockUI();
	if(typeof($('#fechaDefuncion').val()) != "undefined")
		$('#fechaDefuncion').removeAttr("disabled");
	
	$('#observaciones').removeAttr("disabled");
	
	$("#validacion").attr("action",""+context_path+"/derechohabiente/baja/guardar/validacion");
	$("#validacion").submit();
}

function regresarDocumentacion(){

	var lenght=doctosCargadosLenght+1;
	$('#thAccion').show();
	for(var i=0;i<lenght;i++){
		$('#eliminaDoc'+i).show();
	}
	
	$('#fileUploadMessages').html("");
}

function aceptarValidacion(){
	var mensaje= '<div class="ui-widget">' +
	'<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> ' +
	'<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>' +
	'Por favor verifique la informaci\u00F3n proporcionada y de clic en Aceptar para continuar con el registro.'+
	'Si requiere corregir datos de clic en Regresar.</p></div></div>';
	
	mensajeConfirmacion(mensaje);
	$("#mensaje").html(mensaje);
}

function mensajeConfirmacion(mensaje) {
	
	$confirmacion = $('<div></div');
	$confirmacion.html(mensaje);
	$confirmacion.dialog({
		autoOpen : false,
		title: 'Mensaje del sistema',
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




function parametrosRespuestaRechazo($dialogo,solicitud,tramite) {
	
	opciones = {
		autoOpen : false,
		resizable: false,
		width: 480,
		title: 'Resultado',
		modal: true,
		buttons: {
			"Aceptar" : function() {
				cierraDialogo($dialogo);
				if(solicitud != null) {
					showComprobanteRechazo();
				}
				if(tramite!=null && tramite != undefined) {
					if(tramite.modelo.estadoTramite.idEstadoTramitePersona == 2) {
						//showComprobanteValidacionBaja($('#idTramite').val(),"BAJA DERECHOHABIENTE")
					}
				}
				location.href= context_path + "/inicio/grupoFamiliar";
			}
		}
	};

	return opciones;
}

function showComprobanteValidacionBaja(idTramite, titulo){
	var direccion=context_path + "/documentos/documentosBaja?idTramite="+idTramite+"&titulo="+titulo+"";
	var page=context_path + "/resources/js/delta/viewPdf.html";
	window.showModalDialog(page,direccion,"resizable:1;dialogHeight:550px;dialogwidth:985px;scroll:yes;status=no");	
}

function showComprobanteRechazo(){
	var direccion=context_path + "/documentos/rechazoSolicitud?titulo=BAJA&tipoTramite=";
	var page=context_path + "/resources/js/delta/viewPdf.html";
	window.showModalDialog(page,direccion,"resizable:1;dialogHeight:550px;dialogwidth:985px;scroll:yes;status=no");	
}

function cierraDialogo($dialogo){
	$dialogo.dialog('close');
	$dialogo.dialog('destroy');
	$dialogo.html('');
}

function isDefined(variable) {
	return (typeof(window[variable]) != "undefined");
}