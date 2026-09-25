/**
 * Mario Teran Blanco
 * IMSS (Instituto Mexicano del Seguro Social)
 * 20/04/2012
 * Script relacionado con las operaciones comunes de los tramites de baja
 */

CORRECCION_DATOS = "1";
CAMBIO_UMF = "2";
CAMBIO_MEDICO = "3";
CIRCUNSCRIPCION_AUTORIZACION = "4";
CIRCUNSCRIPCION_SUSPENSION = "5";
ASIGNAR_CONSULTORIO_TURNO = "6";

$(document).ready(
	function() {
		$.ajaxSetup({ cache: false });
		if($("#candidato").val() == undefined) {
			$('#aceptar').hide();
		}
		
		//ponemos el estilo del grid a nuestra tabla
		$('#candidatos').dataTable( {
			bJQueryUI : true,
	        bFilter : false,
	        bInfo:true,
	        bSort: false,
	        "bPaginate": true,
	        "bAutoWidth" : true,
	        "iDeferLoading" : 0
	        });
		
		$('#aceptar').click(function() {
			
			
			var derechohabiente = $("input:radio[name=candidato]:checked").val();
			
			if(derechohabiente != undefined || derechohabiente != null){
				
				var tieneDom = 	$("input:radio[name=candidato]:checked").data('dom');
				var tienePatronImss = 	$("input:radio[name=candidato]:checked").data('patronimss');		
				var parentesco = $("input:radio[name=candidato]:checked").data('parentesco');
				
				if(tieneDom == 0){
					
					if( parentesco == PARENTESCO_ENUM.CONCUBINARIO || (
							parentesco == PARENTESCO_ENUM.PADRES && (tienePatronImss == 0 &&
							$("#aseguradoFallecido").val()!=1)) ){
						errorSinDomicilio();
						return;
					}
					
				}
				
				correccionDerechohabiente(derechohabiente);
				
				
			}else{
				errorNoSeleccionado();
			}
			
		});

		$("#guia").click(
			function() {
				showGuiaTramite(0,0);
			}
		);
		
		$("#cancelar").click(
				function() {
					cancelarCorreccion();
				}
		);
	}
);

function cancelarCorreccion() {
	$.blockUI();
	location.href = "" + context_path + "/inicio/grupoFamiliar";
}

function errorNoSeleccionado() {
	$noSeleccionado = $('<div></div');

	$noSeleccionado.dialog({
		autoOpen : false,
		resizable : false,
		height : 140,
		title : 'Error',
		modal : true,
		buttons : {
			"Aceptar" : function() {
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$noSeleccionado.text('Debe seleccionar un derechohabiente');
	$noSeleccionado.dialog('open');
}

function correccionDerechohabiente(idDerechohabiente) {
	var tipo = $("#idTipoTramite").val();
	//var url = context_path + "/tramite/tramitePosible";
	var url = context_path + "/tramite/tramiteAbierto";
	var parametros = {
		'idPersona' : idDerechohabiente
	};
	
	
	$.postJSON(url,parametros, function(result) {
		if(result.modelo != null) {
			errorTramite(result.modelo.tipoTramite.descripcion);
		} else {
			if(!result.estado) {
				errorGenerico(result.mensaje);
			} else {
				switch(tipo) {
					case CORRECCION_DATOS:
						$.blockUI();
						location.href = "" + context_path + "/derechohabiente/correccion/datosPersonales/datos/"+idDerechohabiente; 
						break;
					case CAMBIO_UMF:
						$.blockUI();
						location.href = "" + context_path + "/derechohabiente/correccion/cambioUmf/datos/"+idDerechohabiente;  ;
						break;
					case CAMBIO_MEDICO:
						$.postJSON("" + context_path + "/derechohabiente/correccion/cambioMedico/posible",{idPersona: idDerechohabiente},
								function(result) {
									if(result.modelo == 1) {
										$.blockUI();
										location.href = "" + context_path + "/derechohabiente/correccion/cambioMedico/datos/"+idDerechohabiente; 
									} else {
										errorCambioMedico();
									}
						});
						break;
					case CIRCUNSCRIPCION_AUTORIZACION:
						$.blockUI();
						location.href = "" + context_path + "/derechohabiente/correccion/circunscripcion/autorizacion/datos/"+idDerechohabiente; 
						break;
					case CIRCUNSCRIPCION_SUSPENSION:
						$.blockUI();
						location.href = "" + context_path + "/derechohabiente/correccion/circunscripcion/suspencion/datos/"+idDerechohabiente; 
						break;
					case ASIGNAR_CONSULTORIO_TURNO:
						$.blockUI();
						location.href = "" + context_path + "/derechohabiente/correccion/asignarMedico/datos/"+idDerechohabiente;
						break;
				}
			}
		}
	});
	/*switch(tipo) {
		case CORRECCION_DATOS:
			parametros.tipoTramite.idTipoTramite = 24;
			$.postJSON(url,parametros, function(result) {
				if(result.modelo == 1) {
					location.href = "" + context_path + "/derechohabiente/correccion/datosPersonales/datos/"+idDerechohabiente; 
				} else {
					errorTramite();
				}
			});
			
			break;
		case CAMBIO_UMF:
			parametros.tipoTramite.idTipoTramite = 36;
			$.postJSON(url,parametros, function(result) {
				if(result.modelo == 1) {
					location.href = "" + context_path + "/derechohabiente/correccion/cambioUmf/datos/"+idDerechohabiente;  
				} else {
					errorTramite();
				}
			});
			
			break;
		case CAMBIO_MEDICO:
			parametros.tipoTramite.idTipoTramite = 37;
			$.postJSON("" + context_path + "/derechohabiente/correccion/cambioMedico/posible",{idPersona: idDerechohabiente},
					function(result) {
						if(result.modelo == 1) {
							$.postJSON(url,parametros, function(result) {
								if(result.modelo == 1) {
									location.href = "" + context_path + "/derechohabiente/correccion/cambioMedico/datos/"+idDerechohabiente; 
								} else {
									errorTramite();
								}
							});
							
						} else {
							errorCambioMedico();
						}
			});
			break;
		case CIRCUNSCRIPCION_AUTORIZACION:
			parametros.tipoTramite.idTipoTramite = 38;
			$.postJSON(url,parametros, function(result) {
				if(result.modelo == 1) {
					location.href = "" + context_path + "/derechohabiente/correccion/circunscripcion/autorizacion/datos/"+idDerechohabiente; 
				} else {
					errorTramite();
				}
			});
			
			break;
		case CIRCUNSCRIPCION_SUSPENSION:
			parametros.tipoTramite.idTipoTramite = 39;
			$.postJSON(url,parametros, function(result) {
				if(result.modelo == 1) {
					location.href = "" + context_path + "/derechohabiente/correccion/circunscripcion/suspencion/datos/"+idDerechohabiente; 
				} else {
					errorTramite();
				}
			});
			
			break;
		case ASIGNAR_CONSULTORIO_TURNO:
			parametros.tipoTramite.idTipoTramite = 41;
			$.postJSON(url,parametros, function(result) {
				if(result.modelo == 1) {
					location.href = "" + context_path + "/derechohabiente/correccion/asignarMedico/datos/"+idDerechohabiente;
				} else {
					errorTramite();
				}
			});
			
			break;
	}*/
}

function errorTramite(tipoTramite) {
	var mensajeError = '<div class="ui-widget">' +
	'<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">'+
	'<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>' +
	'<strong>Esta persona ya cuenta con un tr&aacute;mite de ' + tipoTramite+', para realizar cualquier otro finalizar el tr&aacute;mite abierto</strong></p></div></div>';

	$razonRechazo = $('<div></div');
	$razonRechazo.html(mensajeError);
	$razonRechazo.dialog({
		autoOpen : false,
		title: '',
		show: "blind",
		hide: "explode",
		resizable: false,
		modal: true,
		width: 500,
		buttons: {
			"Cerrar": function() {
				cierraDialogo($(this));
			}
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	$razonRechazo.dialog('open');
}

function errorGenerico(tipoTramite) {
	var mensajeError = '<div class="ui-widget">' +
	'<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">'+
	'<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span><strong>' + tipoTramite+'</strong></p></div></div>';

	$razonRechazo = $('<div></div');
	$razonRechazo.html(mensajeError);
	$razonRechazo.dialog({
		autoOpen : false,
		title: "Error",
		show: "blind",
		hide: "explode",
		resizable: false,
		modal: true,
		width: 500,
		buttons: {
			"Cerrar": function() {
				cierraDialogo($(this));
			}
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	$razonRechazo.dialog('open');
}

function errorCambioMedico() {
	var mensajeError = '<div class="ui-widget">' +
	'<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">'+
	'<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>' +
	'<strong>Solo se puede realizar un tr&aacute;mite de cambio de consultorio por a&ntilde;o, el derechohabiente aun no puede solicitar un nuevo cambio de consultorio.</strong></p></div></div>';

	$razonRechazo = $('<div></div');
	$razonRechazo.html(mensajeError);
	$razonRechazo.dialog({
		autoOpen : false,
		title: '',
		show: "blind",
		hide: "explode",
		resizable: false,
		modal: true,
		width: 500,
		buttons: {
			"Cerrar": function() {
				cierraDialogo($(this));
			}
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	$razonRechazo.dialog('open');
}

function cierraDialogo($dialogo){
	$dialogo.dialog('close');
	$dialogo.dialog('destroy');
	$dialogo.html('');
}


function errorSinDomicilio() {
	$noSeleccionado = $('<div></div');

	$noSeleccionado.dialog({
		autoOpen : false,
		resizable : false,
		height : 170,
		title : 'Error',
		modal : true,
		buttons : {
			"Aceptar" : function() {
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$noSeleccionado.html('Para realizar este tr&aacute;mite es necesario capturar primero el domicilio del asegurado');
	$noSeleccionado.dialog('open');
}