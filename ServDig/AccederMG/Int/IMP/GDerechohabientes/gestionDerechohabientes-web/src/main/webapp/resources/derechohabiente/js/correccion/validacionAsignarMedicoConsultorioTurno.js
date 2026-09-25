/**
*Guillermo Hernandez Dolores
*/
var guardarValidacion=false;
var mandaGrupoFamiliar =false;

$(document).ready(
		function(){
			
			if($("#validacion").val()==1){
				$("#aceptar").hide();
				$("#cancelar").hide();
				$("#regresar").hide();
				$("#aceptarValidacion").show();
				$("#rechazarValidacion").show();
				$("#regresarGrupoFamiliar").show();
				$("#guiaTramite").show();
				$("#regresarValidacion").hide();
				$("#cagarDocProbDiv").show();
				
				$("#observaciones").removeAttr("disabled");
				//Botones de validacion de datos
				$("#aceptarValidacion").click(
						function() {
							if(guardarValidacion){
								doSaveDocSinTramite($("#idTramite").val());
								guardarCambioMedico();
							}else{
								if(validarCombosValidacion() && fileUploadFinish){
									aceptarValidacion()
									$("#regresar").show();
									habilitarCombos(false);
									// Oculta el div de carga de documentos
									$("#cagarDocProbDiv").hide();
									// Llama al componente de mostrar documentos
									$("#docProbTramDiv").show();
									// document.getElementById('docProbTramDiv').style.display='block';
									initMuestraDocumentosTramiteSession();
									$("#observaciones").attr("disabled","disabled");
								}
								
								if(!fileUploadFinish){
									mensageConfirmacion('Debe completar la documentaci\u00F3n probatoria para poder guardar la validaci\u00F3n');
								}
							}
						}
					);
				
				$("#rechazarValidacion").click(
						function() {
							cargarRazonRechazo();;
						}
					);
				$("#guiaTramiteValidacion").click(
						function() {
							alert('En construccion');
						}
					);
				
				//termino del if
			}else{
				$("#aceptarValidacion").hide();
				$("#rechazarValidacion").hide();
				$("#regresarGrupoFamiliar").hide();
				$("#guiaTramite").hide();
				$("#regresarValidacion").hide();
			}
			
			$("#regresarGrupoFamiliar").click(
					function() {
						cancelarCorreccion();
					}
				);
		
});

function guardarCambioMedico(){
	$decision = $('<div></div');

	$decision.dialog({
		autoOpen : false,
		resizable : false,
		height : 160,
		title : 'Selecciona una opci\u00F3n',
		modal : true,
		buttons : {
			"Si" : function() {
				$("#observaciones").removeAttr("disabled");
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

function aceptarValidacion(){
	var mensaje= '<div class="ui-widget">' +
	'<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> ' +
	'<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>' +
	'Por favor verifique la informaci\u00F3n proporcionada y de clic en Aceptar para continuar con el registro.'+
	'Si requiere corregir datos de clic en Regresar.</p></div></div>';
	mensajeConfirmacion(mensaje);
	guardarValidacion=true;
	$("#mensajeConfirmacion").html(mensaje);
    $("#regresarGrupoFamiliar").hide()
    $("#regresarValidacion").show()
    $("#guiaTramite").hide()
}

function cancelarCorreccion() {
	
	if($("#validacion").val()==1){
		$decision = $('<div></div');
	
		$decision.dialog({
			autoOpen : false,
			resizable : false,
			height : 160,
			title : 'Advertencia',
			modal : true,
			buttons : {
				"Si" : function() {
					location.href = "" + context_path + "/inicio/grupoFamiliar";
				},
				"No" : function() {
					cierraDialogo($(this));
				}
			}
		}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
		$decision.text('\u00BF Est\u00E1 seguro que desea cancelar el tr\u00E1mite de cambio de m\u00E9dico?');
		$decision.dialog('open');
	
	}else{
		location.href = "" + context_path + "/derechohabiente/correccion/asignacionMedico";
	}	
	
}



function cargarRazonRechazo() {
	
	$razonRechazo = $('<div></div');
	$razonRechazo.html('Cargando Razones de rechazo...')
	$razonRechazo.dialog({
		autoOpen : false,
		title: 'Raz\u00F3n rechazo',
		show: "blind",
		hide: "explode",
		resizable: false,
		modal: true,
		width: 480,
		buttons: {
			"Si": function() {
				var razon = $('#idRazonRechazo').val();
				rechazarSolicitud(razon,$(this));
			},
			"No": function() {
				cierraDialogo($(this));
			}
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	var ajaxResource = context_path + '/solicitud/cargarRazonRechazo';
	$razonRechazo.load(ajaxResource);
	$razonRechazo.dialog('open');
}


function rechazarSolicitud(razonRechazo, $dialogo) {
	
	var formulario = $("form");
	formulario.attr("id","rechazoF");
	formulario.attr("name","rechazoF");
	formulario.attr("method","POST");
	formulario.attr("action",context_path + "/tramite/rechazar");
	formulario.append("<input type='hidden' name='idSolicitud' value='"+$('#idSolicitud').val()+"'>");
	formulario.append("<input type='hidden' name='idPersona' value='"+$('#idPersona').val()+"'>");
	formulario.append("<input type='hidden' name='idTipoTramite' value='41'>");
	formulario.append("<input type='hidden' name='idRazonRechazo' value='"+razonRechazo+"'>");
	formulario.append("<input type='hidden' name='idTramite' value='"+$('#idTramite').val()+"'>");
	formulario.append("<input type='hidden' name='observaciones' value='"+$('#observacionesRechazo').val()+"'>");
	formulario.submit();
	
/*	var solicitud = {
		'idSolicitud': $('#idSolicitud').val(),
		'idPersona': $('#idPersona').val(),
		'idRazonRechazo': razonRechazo,
		'idTramite' : $('#idTramite').val(),
		'observaciones':$('#observacionesRechazo').val()
	};
	
	var ajax_source = context_path + "/derechohabiente/baja/rechazar";
	
	$.postJSON(ajax_source, solicitud, function(result) {
		
		if(result.errores == null) {
			$dialogo.html("<center>La solicitud a sido rechazada satisfactoriamente por la siguiente raz\u00F3n: <br>" + result.modelo.razonResultado.descripcion + "</center>" );
			$dialogo.dialog(parametrosRespuestaRechazo($dialogo,solicitud,null));
		} else {
			$dialogo.html(result.errores[0]);
			$dialogo.dialog(parametrosRespuestaRechazo($dialogo,null,null));
		}
	});*/
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
					showComprobanteValidacionCircunscripcion("AUTORIZACIÓN DE CIRCUNSCRIPCIÓN FORÁNEA");
					location.href= context_path + "/inicio/grupoFamiliar";
				}
			}
	};

	return opciones;
}

function habilitarCombos(estado){
		$("fieldset#medicoEnTurno select").each(
				function(index) {
					if(estado)
						$(this).removeAttr("disabled");
					else
						$(this).attr("disabled","disabled");
				}
			);
	
}


function showComprobanteValidacionCircunscripcion(titulo){
	var direccion=context_path + "/documentos/rechazoSolicitud?titulo="+titulo+"&tipoTramite=";
	var page=context_path + "/resources/js/delta/viewPdf.html";
	window.showModalDialog(page,direccion,"resizable:1;dialogHeight:550px;dialogwidth:985px;scroll:yes;status=no");	
}

function cierraDialogo($dialogo){
	$dialogo.dialog('close');
	$dialogo.dialog('destroy');
	$dialogo.html('');
}

function validarCombosValidacion(){
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
