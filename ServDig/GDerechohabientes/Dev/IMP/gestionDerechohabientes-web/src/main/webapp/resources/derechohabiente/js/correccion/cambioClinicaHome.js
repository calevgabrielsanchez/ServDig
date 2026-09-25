/**
 * Mario Teran Blanco
 * IMSS (Instituto Mexicano del Seguro Social)
 * 01/02/2013
 * Script relacionado con el tramite de cambio de clinica
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
		
		/*
		 * Activamos o desactivamos todos los checkbox en caso
		 * de que el checkbox todos sea seleccionado o no
		 */
		$("#todos").change(
			function() {
				var checado = $(this).is(":checked");
				$("input[name='candidato']").each(function() {
					 $(this).attr("checked",checado);
				 });
			}	
		);
		
		//Si no existen los candidatos no mostramos el boton aceptar
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
	        }
		);
		
		//Establecemos la funcion del boton aceptar
		$('#aceptar').click(function() {
				var derechohabiente = new Array();
				//Llenamos el array derechohabiente con los ids de las personas seleccionadad
				 $("input[@name='candidato']:checked").each(function() {
					 if($.trim($(this).val()).length > 0)
						 derechohabiente.push($(this).val());
				 });
				//En caso de que el arreglo no este vacio mandamos la lista de los derechohabientes seleccionados 
				if(derechohabiente.length != 0)
					correccionDerechohabiente(derechohabiente);
				//En caso de que el arreglo este vacio mostramos un error
				else
					errorNoSeleccionado();
			}
		);
		
		//Establecemos la funcion del boton cancelar
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

/**
 * Funcion para verificar que ninguno de los derechohabientes tengaun tramite de cualquier tipo abierto
 * En caso de que sea seleccionado el asegurado tambien se checa que ningun derechohabiente con estado
 * de baja tenga algun tramite abierto
 * @param derechohabientes
 */
function correccionDerechohabiente(derechohabientes) {
	var url = context_path + "/derechohabiente/correccion/verificarTramites";
	var parametros = {
		"candidatos": derechohabientes
	}
	
	$.postJSON(url,parametros, function(result) {
		if(result != null && result.length > 0) {
			errorTramite(result);
		} else {
			$("#formCandidatos").on("submit",function(){$.blockUI();});
			$("#formCandidatos").submit();
		}
	});
}

function getTexto(texto) {
	if(texto == null || texto == undefined) {
		return "";
	}
	
	return texto;
}

function errorTramite(tramites) {
	var tramitesc = "<strong>Favor de finalizar los siguientes tramites: </strong><br><br>";
	
	for(var i=0; i<tramites.length;i++) {
		tramitesc += "La persona <strong>"+getTexto(tramites[i].persona.nombre)+" " +getTexto(tramites[i].persona.primerApellido)+" " +getTexto(tramites[i].persona.segundoApellido)+"</strong> ya cuenta con un tramite de tipo <strong>" + tramites[i].tipoTramite.descripcion+"</strong><br>";
	}
	var mensajeError = '<div class="ui-widget">' +
	'<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">'+
	'<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>' +
	tramitesc + '</p></div></div>';

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
