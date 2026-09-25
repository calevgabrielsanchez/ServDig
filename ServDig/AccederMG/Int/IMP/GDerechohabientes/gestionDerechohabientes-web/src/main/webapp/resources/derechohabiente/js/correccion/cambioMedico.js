/**
 * 
 */

var validacion;
var guardarValidacion=false;
var mensaje= '<div class="ui-widget">' +
'<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> ' +
'<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>' +
'Por favor verifique la informaci\u00F3n proporcionada y de clic en Aceptar para continuar con el registro.'+
'Si requiere corregir datos de clic en Regresar.</p></div></div>';

$(document).ready(
	function() {
		$.ajaxSetup({ cache: false }); 
		
		$("#regresar").hide();
		
		
		$("#medicoEnTurno\\.turno\\.idTurno").change(function() {
			var idUmf = $('#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF').val();
			var idTurno = $('#medicoEnTurno\\.turno\\.idTurno').val();
			
			if($("#medicoEnTurno\\.turno\\.idTurno")[0].selectedIndex != 0){
				$("#errorIdTurno").html('');
			}
			
			setConsultorios(idUmf,idTurno);
			limpiarMedico();
		});

		$("#medicoEnTurno\\.consultorio\\.idConsultorio").change(function() {
			
			var idUmf = $('#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF').val();
			var idTurno = $('#medicoEnTurno\\.turno\\.idTurno').val();
			var idConsultorio = $('#medicoEnTurno\\.consultorio\\.idConsultorio').val();
			
			if($("#medicoEnTurno\\.consultorio\\.idConsultorio")[0].selectedIndex!=0){
				$("#errorIdConsultorio").html('');
			}

			setMedico(idUmf,idTurno,idConsultorio);
			
		});
		
		//para todos deshabilitamos los campos de umf y solo dejamos habilitados
		//los campos para poder cambiar de medico
		$("fieldset#medicoEnTurno input:text").each(
			function(index) {
				$(this).attr("disabled","disabled");
			}
		);
		
		$("#aceptar").click(
			function() {
				if(validarCombos()){
					guardarValidacion=true;
					$("#regresarGrupoFamiliar").hide();
					$("#regresar").show();
					$("#cancelar").hide();
					$("#aceptar").hide();
					guardarCorreccion();
				}
			}
		);
		
		$("#regresar").click(
				function() {
					$("#regresar").hide();
					$("#regresarGrupoFamiliar").show();
					$("#mensajeConfirmacion").text('');
					guardarValidacion=false;
					habilitarCombos(true);
				}
			);
		
		$("#cancelar").click(
			function() {
				regresarGrupo();
			}
		);
	}
);

function muestraMensajeSeleccionConsultorio(mensajea){
	$confirmacion = $('<div></div>');
	$confirmacion.html(mensajea);
	$confirmacion.dialog({
		autoOpen : false,
		title: 'Error',
		show: "blind",
		hide: "explode",
		resizable: false,
		modal: true,
		width: 500,
		buttons: {
			'Cerrar' : function () {
				cierraDialogo($(this));
			}
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	$confirmacion.dialog('open');
}

function mensajeConfirmacion(mensajea){
	$confirmacion = $('<div></div>');
	$confirmacion.html(mensajea);
	$confirmacion.dialog({
		autoOpen : false,
		title: 'Mensaje del sistema',
		show: "blind",
		hide: "explode",
		resizable: false,
		modal: true,
		width: 500,
		buttons: {
			'Cerrar' : function () {
				cierraDialogo($(this));
			}
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	$confirmacion.dialog('open');
}

function limpiarMedico() {
	$("#medicoEnTurno\\.medicoFamiliar\\.idMedicoFamiliar").val("");
	$("#medicoEnTurno\\.medicoFamiliar\\.noMatricula").val("");
	$("#medicoEnTurno\\.medicoFamiliar\\.nombre").val("");
	$("#medicoEnTurno\\.medicoEspecialidad\\.idMedicoEspecialidad").val("");
	$("#medicoEnTurno\\.medicoEspecialidad\\.descripcion").val("");
}

/**
 * MEtodo para establecer la accion dependiendo si es la validacion o no
 * @param isValidacion
 */

function habilitarCombos(estado){
		$("fieldset#medicoEnTurno select").each(
				function(index) {
					if(estado)
						$(this).removeAttr("disabled");
					else
						$(this).attr("disabled","disabled");
				}
			)
		
}


function setValidacion(isValidacion) {
	
	validacion = isValidacion;
	if(validacion != 1) {
		$("#correccionDatos").attr("action",""+context_path+"/derechohabiente/correccion/cambioMedico/guardar");
	} else {
		$("#correccionDatos").attr("action",""+context_path+"/derechohabiente/correccion/cambioMedico/validacion/guardar");
	}
}

function setMedico(idUmf,idTurno,idConsultorio) {
	var url = context_path + "/umf/getMedicosUmfTurno";
	var parametros = {
		'unidadMedicaFamiliar': {
			'idUMF': idUmf
		},
		'turno': {
			'idTurno': idTurno
		},
		'consultorio': {
			'idConsultorio': idConsultorio
		}
	};
	
	$.postJSON(url, parametros, function(result) {
		var medico = result[0];
		$("#medicoEnTurno\\.idMedicoContultorioTurno").val(medico.idMedicoContultorioTurno);
		if(medico.medicoFamiliar != null && medico.medicoFamiliar != undefined) {
			$("#medicoEnTurno\\.medicoFamiliar\\.idMedicoFamiliar").val(medico.medicoFamiliar.idMedicoFamiliar);
			$("#medicoEnTurno\\.medicoFamiliar\\.noMatricula").val(medico.medicoFamiliar.noMatricula);
			$("#medicoEnTurno\\.medicoFamiliar\\.nombre").val(medico.medicoFamiliar.nombre+" "+medico.medicoFamiliar.primerApellido+" "+medico.medicoFamiliar.segundoApellido);
		} 
		
		if(medico.medicoEspecialidad != null && medico.medicoEspecialidad != undefined) {
			$("#medicoEnTurno\\.medicoEspecialidad\\.idMedicoEspacialidad").val(medico.medicoEspecialidad.idMedicoEspacialidad);
			$("#medicoEnTurno\\.medicoEspecialidad\\.descripcion").val(medico.medicoEspecialidad.descripcion);
		}
	});
}

function setConsultorios(idUmf , idTurno, idConsultorio) {
	
	var url = context_path + "/umf/getConsultorios";
	var parametros = {
		'unidadMedicaFamiliar': {
			'idUMF': idUmf
		},
		'turno': {
			'idTurno': idTurno
		}
	};
	
	if(idUmf != null && idUmf != undefined && idTurno != null && idTurno != undefined && $.trim(idTurno).length != 0) {
		$.postJSON(url, parametros, function(result) {
			var options = "<option value=''> -- Por favor seleccione -- </option>";
			
			for(var i = 0 ; i < result.length ; i++){
				if(idConsultorio != null || idConsultorio != undefined) {
					if(idConsultorio == result[i].idConsultorio)
						options += "<option value='" + result[i].idConsultorio + "' selected='selected'>" + result[i].descripcion + "</option>";
					else
						options += "<option value='" + result[i].idConsultorio + "'>" + result[i].descripcion + "</option>";
				}
				else
					options += "<option value='" + result[i].idConsultorio + "'>" + result[i].descripcion + "</option>";	
			}
			
			$("#medicoEnTurno\\.consultorio\\.idConsultorio").html(options);
		});
	}
	$("select#medicoEnTurno\\.consultorio\\.idConsultorio option[value='1']").hide();

}

function regresarGrupo() {
	$.blockUI();
	location.href = "" + context_path + "/derechohabiente/correccion/cambioMedico/";

}

function guardarCorreccion() {

	
	$.blockUI();
	
	$("fieldset#medicoEnTurno select").each(
			function(index) {
				$(this).removeAttr("disabled");
			}
	);
	$("#correccionDatos").submit();
}

/**
 * MEtodo para cerrar un dialogo y borrar su contenido
 * @param $dialogo
 */
function cierraDialogo($dialogo){
	$dialogo.dialog('close');
	$dialogo.dialog('destroy');
	$dialogo.html('');
}

function validarCombos(){
	var aceptado=true;
	
	var idTurnoAnterior = $("#idturnoAnterior").val();
	var idConsultorioAnterior = $("#idConsultorioAnterior").val();
	var idTurnoActual = $("#medicoEnTurno\\.turno\\.idTurno").val();
	var idConsultorioActual = $("#medicoEnTurno\\.consultorio\\.idConsultorio").val();
	
	$("#errorIdTurno").html('');
	$("#errorIdConsultorio").html('');

	if($("#medicoEnTurno\\.turno\\.idTurno")[0].selectedIndex==0){
		aceptado=false;
		$("#errorIdTurno").html('Obligatorio');
	}
	if($("#medicoEnTurno\\.consultorio\\.idConsultorio")[0].selectedIndex==0){
		aceptado=false;
		$("#errorIdConsultorio").html('Obligatorio');
	}
	
	if(!aceptado) {
		return aceptado;
	}
	
	if(idTurnoAnterior == idTurnoActual && idConsultorioAnterior == idConsultorioActual) {
		aceptado = false;
		muestraMensajeSeleccionConsultorio("Es necesario modificar al menos un dato para continuar con el tr&aacute;mite.");
	}
	
	return aceptado;
}


