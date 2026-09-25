var validacion;
var guardarRegistro=false;
var mensaje= '<div class="ui-widget">' +
'<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> ' +
'<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>' +
'Por favor verifique la informaci\u00F3n proporcionada y de clic en Aceptar para continuar con el registro.'+
'Si requiere corregir datos de clic en Regresar.</p></div></div>';

$(document).ready(
	function() {
		$.ajaxSetup({ cache: false }); 
		
		setValidacion($("#validacion").val());  
		$("#medicoEnTurno\\.turno\\.idTurno").change(function() {
			var idUmf = $('#idUmfU').val();
			var idTurno = $('#medicoEnTurno\\.turno\\.idTurno').val();
			$("#medicoEnTurno\\.consultorio\\.idConsultorio")[0].selectedIndex = 0;
			limpiarMedico();
			setConsultorios(idUmf,idTurno);
			setConsultorios(idUmf,idTurno);
			
		});
		
		$("#medicoEnTurno\\.consultorio\\.idConsultorio").change(function() {
			
			var idUmf = $('#idUmfU').val();
			var idTurno = $('#medicoEnTurno\\.turno\\.idTurno').val();
			var idConsultorio = $('#medicoEnTurno\\.consultorio\\.idConsultorio').val();
			setMedico(idUmf,idTurno,idConsultorio);
		
		});
		
		$("#medicoEnTurno\\.medicoFamiliar\\.nombre").attr("disabled","disabled");
		$("#cancelar").hide();
		$("#regresar").click(
				function(){
					$("#regresar").hide();
					$("#regresarGrupoFamiliar").show();
					guardarValidacion=false;
					habilitarCombos(true);
					$("#mensajeConfirmacion").html('');
					if($("#validacion").val()==1){
						$("#observaciones").removeAttr("disabled");
						// Oculta el div de carga de documentos
						$("#cagarDocProbDiv").show();
						// Llama al componente de mostrar documentos
						$("#docProbTramDiv").hide();
					}
				}
		);
	   //botones
		$("#aceptar").click(
				function() {
					if(guardarValidacion){
						guardarMedico();
					}else{
						if(validarCombos()){
							mensajeConfirmacion(mensaje);
							$("#mensajeConfirmacion").html(mensaje);
							guardarValidacion=true;
							habilitarCombos(false);
							$("#regresarGrupoFamiliar").hide();
							$("#regresar").show();
						}
					}
				}
			);
		
	  //finde botones
		
		
	});//fin del metodo  ready


function guardarMedico() {

	$decision = $('<div></div');

	$decision.dialog({
		autoOpen : false,
		resizable : false,
		height : 160,
		title : 'Seleccione una opci\u00F3n',
		modal : true,
		buttons : {
			"Si" : function() {
				$("fieldset#medicoEnTurno select").each(
						function(index) {
							$(this).removeAttr("disabled");
						}
					)
				$("#correccionDatos").submit();
			},
			"No" : function() {
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.text('\u00BF Est\u00E1 seguro que desea guardar el tr\u00E1mite de asignar m\u00E9dico?');
	$decision.dialog('open');
}


function limpiarMedico() {
	$("#medicoEnTurno\\.medicoFamiliar\\.idMedicoFamiliar").val("");
	$("#medicoEnTurno\\.medicoFamiliar\\.noMatricula").val("");
	$("#medicoEnTurno\\.medicoFamiliar\\.nombre").val("");
	$("#medicoEnTurno\\.medicoEspecialidad\\.idMedicoEspecialidad").val("");
	$("#medicoEnTurno\\.medicoEspecialidad\\.descripcion").val("");
}

function setConsultorios(idUmf , idTurno, idConsultorio) {
	
	var url = context_path + "/umf/getConsultorios";
	
	if(idTurno != "") {
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
	
	if(idTurno!= "") {
	$.postJSON(url, parametros, function(result) {
		var medico = result[0];
		$("#medicoEnTurno\\.idMedicoContultorioTurno").val(medico.idMedicoContultorioTurno);
		$("#medicoEnTurno\\.medicoFamiliar\\.idMedicoFamiliar").val(medico.medicoFamiliar.idMedicoFamiliar);
		$("#medicoEnTurno\\.medicoFamiliar\\.noMatricula").val(medico.medicoFamiliar.noMatricula);
		$("#medicoEnTurno\\.medicoFamiliar\\.nombre").val(medico.medicoFamiliar.nombre+" "+medico.medicoFamiliar.primerApellido+" "+medico.medicoFamiliar.segundoApellido);
		$("#medicoEnTurno\\.medicoEspecialidad\\.idMedicoEspacialidad").val(medico.medicoEspecialidad.idMedicoEspacialidad);
		$("#medicoEnTurno\\.medicoEspecialidad\\.descripcion").val(medico.medicoEspecialidad.descripcion);
	})
	}
}

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

function mensajeConfirmacion(mensajea){
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

function cierraDialogo($dialogo){
	$dialogo.dialog('close');
	$dialogo.dialog('destroy');
	$dialogo.html('');
}

function setValidacion(isValidacion) {
	
	validacion = isValidacion;
	if(validacion != 1) {
		$("#correccionDatos").attr("action",""+context_path+"/derechohabiente/correccion/asignarMedico/guardar");
		
	} else {
		$("#correccionDatos").attr("action",""+context_path+"/derechohabiente/correccion/asignarMedico/validacion/guardar");
	}
}

function validarCombos(){
	var aceptado=true;
	
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
	
	return aceptado;
}

