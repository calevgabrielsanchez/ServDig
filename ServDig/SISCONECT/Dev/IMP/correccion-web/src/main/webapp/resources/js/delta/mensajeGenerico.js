var idDgExitoGenericoSITAB = "#dgExitoGenericoSITAB";
var idDgConfirmaGenericoSITAB = "#dgConfirmaGenericoSITAB";
var idDgAvisoGenericoSITAB = "#dgAvisoGenericoSITAB";

var oDgExitoGenericoSITAB;
var oDgConfirmaGenericoSITAB;
var oDgAvisoGenericoSITAB;

$(document).ready(function() {
	
	$("#dgConfirmaGenericoSITAB").css("display", "none");
	$("#dgAvisoGenericoSITAB").css("display", "none");
	
	oDgExitoGenericoSITAB = $(idDgExitoGenericoSITAB).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		height: 150,
		width: 700,
		closeOnEscape: false,
		buttons: {
			"Aceptar": function() {									
				$(this).dialog("close");
			}
		}
	});

});


function abrirConfirmacionGenerica(mensaje, funcionEjecuta) {
	$("#dgConfirmaGenericoSITAB").css("display", "block");
	$("form#formMensajesGenericos #mensajeGenericoSITAB").html('<label>' + mensaje  + '</label>');
	oDgConfirmaGenericoSITAB = $(idDgConfirmaGenericoSITAB).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		height: 150,
		width: 700,
		closeOnEscape: false,
		buttons: {
			"Aceptar": function() {	
				$(this).dialog("close");				
				funcionEjecuta();			
			}, "Cancelar": function() {
				$(this).dialog("close");
			}
		}
	}); 
	
	oDgConfirmaGenericoSITAB.dialog("open");
}

function abrirAvisoGenericoSITAB(mensajeAviso) {
	$("#dgAvisoGenericoSITAB").css("display", "block");
	$("form#formMensajeAvisoGenerico #mensajeGenericoAvisoSITAB").html('<label>' + mensajeAviso  + '</label>');
	oDgAvisoGenericoSITAB = $(idDgAvisoGenericoSITAB).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		height: 150,
		width: 700,
		closeOnEscape: false,
		buttons: {
			"Aceptar": function() {	
				$(this).dialog("close");				
						
			}
		}
	}); 
	
	oDgAvisoGenericoSITAB.dialog("open");
}
 