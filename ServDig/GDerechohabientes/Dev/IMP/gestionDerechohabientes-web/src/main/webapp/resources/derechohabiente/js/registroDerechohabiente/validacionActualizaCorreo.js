/**
 * @author Mario Teran Blanco
 * Script para la validacion de la baja de derechohabiente
 * 28/05/2012
 */

CONFIRMACION = false;
DOCUMENTACION = false;
let banderaValido = false;

$(document).ready(function() {	
    var curp = $('#curp').val().toUpperCase();
    console.log("La CURP que trae el sistema es: ", curp);
    validarInput(curp);
    
    if (!banderaValido) {
        curpNoVAlida();
        const btnFinaliza = document.getElementById('btnInciaTramite');
        btnFinaliza.disabled = true;
    }

    $('#btnInciaTramite').click(function() {
        verificaCorreo();
    });

    var a = $("#idActualizaCorreo").validate({ 
        rules: {
            correo: {
                required: true,
                email: true
            },
            correoActualizado: {
                required: true,
                email: true,
                equalTo: "#correo"
            }
        }, 
        errorLabelContainer: "#warning", 
        messages: {
            correo: {
                required: "Obligatorio",
                email: 'El formato de Correo electr&oacute;nico es inv&aacute;lido'
            },
            correoActualizado: {
                required: "Obligatorio",
                email: 'El formato de Correo electr&oacute;nico es inv&aacute;lido',
                equalTo: 'Los correos deben ser los mismos...'
            }
        } 
    }); 



    DOCUMENTACION = isDefined("fileUploadFinish") ? true : false;

    $("#btnCancelarTramite").click(function() {
        cancelarBusqueda();
    });

    
});

function verificaCorreo() {
    var correo1 = document.getElementById('correo');
    var correo2 = document.getElementById('correoActualizado');

    if (correo1.value != correo2.value) {
        document.getElementById("error").classList.add("mostrar");
        alert("¡Los correos no coinciden!");
        return false;
    } else {
        confirmacionValidacBa();
    }
}

// Función para validar una CURP
function validarInput(curp) {
    var resultado = document.getElementById("resultado");
    var valido = "No v\u00E1lido";
    banderaValido = false;

    if (curpValida(curp)) {
        valido = "V\u00E1lido";
        banderaValido = true;
        resultado.classList.add("ok");
    } else {
        resultado.classList.remove("ok");
    }

    resultado.innerText = "CURP: " + curp + "\nFormato: " + valido;
}

function curpValida(curp) {
	console.log("se validara la curp: ", curp);
    var re = /^([A-Z][AEIOUX][A-Z]{2}\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[12]\d|3[01])[HM](?:AS|B[CS]|C[CLMSH]|D[FG]|G[TR]|HG|JC|M[CNS]|N[ETL]|OC|PL|Q[TR]|S[PLR]|T[CSL]|VZ|YN|ZS)[B-DF-HJ-NP-TV-Z]{3}[A-Z\d])(\d)$/,
    validado = curp.match(re);
    
    console.log("Resultado de la validación:", validado);
	
    if (!validado) return false;

    return true; 
}


function cancelarBusqueda() {
	
	$decision = $('<div></div');
	
	$decision.dialog({
		autoOpen : false,
	    resizable: true,  
	    height: 'auto',  
	    width: 'auto',
		title: 'Selecciona una opci\u00F3n',
		modal: true,
		buttons: {
			"Si": function() {
				location.href = "" + context_path + "/inicio/grupoFamiliar";
				cierraDialogo($(this));
			},
			"No": function() {
				cierraDialogo($(this));
			}
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	$decision.text('\u00BFEst\u00E1 seguro de cancelar el tr\u00E1mite de actualizaci\u00F3n de correo electr\u00F3nico?');
	$decision.dialog('open');
}
function curpNoVAlida() {
	
	$decision = $('<div></div');
	
	$decision.dialog({
		autoOpen : false,
	    resizable: true,  
	    height: 'auto',  
	    width: 'auto',
		title: 'Mensaje',
		modal: true,
		buttons: {
			"Entendido": function() {
				cierraDialogo($(this));
			}
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	$decision.text('La CURP ingresada no cumple con el formato establecido, favor de validar e intentar nuevamente.');
	$decision.dialog('open');
}

function regresarMenuAnt() {
	
				location.href = "" + context_path + "/inicio/grupoFamiliar";
				
}

function cierraDialogo($dialogo){
	$dialogo.dialog('close');
	$dialogo.dialog('destroy');
	$dialogo.html('');
}

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
	
	$decision.text('\u00BF Est\u00E1 seguro que desea finalizar el tr\u00E1mite de  actualizaci\u00F3n de correo?');
	$decision.dialog('open');
}

var aceptar = function () {
	//if(!CONFIRMACION)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
		irConfirmacion();
	/*else
		procesarValidacionBaja();*/
};




function irConfirmacion() {
			var validacionform = $("form#idActualizaCorreo").valid();
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
				location.href = "" + context_path + "/inicio/grupoFamiliar";
			},
			"No" : function() {
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	//$decision.text('\u00BF Est\u00E1 seguro que desea salir del tr\u00E1mite de baja?');
	//$decision.dialog('open');
}

function procesarValidacionBaja() {
	guardarBaja();
}

function guardarBaja() {
	
	
	$("#idActualizaCorreo").attr("action",""+context_path+"/tramite/registro/finalizaSolicitudActualizacionCorreo");
	$("#idActualizaCorreo").submit();
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
				location.href = "" + context_path + "/inicio/grupoFamiliar";;
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