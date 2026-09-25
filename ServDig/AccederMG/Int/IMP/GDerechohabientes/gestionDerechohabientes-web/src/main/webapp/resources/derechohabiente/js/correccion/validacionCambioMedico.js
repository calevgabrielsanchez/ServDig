/**
*Guillermo Hernandez Dolores
*/
var guardarValidacion=false;
var mandaGrupoFamiliar =false;

$(document).ready(
		function(){
		
			asignartextAreaLimites("observaciones",{styles:{}});
			if($("#validacion").val()==1){
				$("#aceptar").hide();
				$("#regresar").hide();
				$("#cancelar").hide();
				$("#aceptarValidacion").show();
				$("#rechazarValidacion").show();
				$("#regresarGrupoFamiliar").show();
				$("#guiaTramite").show();
				$("#regresarValidacion").hide();
				habilitarCombos(false);
				//Botones de validacion de datos
				$("#aceptarValidacion").click(
						function() {
							var requiereDocs = $("#requiereDocs").val() == "1";
								if(requiereDocs){
									if(!fileUploadFinish){
										var mensajed= '<div class="ui-widget-content ui-corner-all">';
										mensajed+= '<div class="ui-state-error ui-corner-all" align="center">';
										mensajed+= '<div class="ui-icon ui-icon-alert"></div>';
										mensajed+= '<p class="ui-helper-reset ui-state-error-text">Debe completar la documentaci\u00F3n probatoria para poder guardar la validaci\u00F3n</p>';
										mensajed+= '</div>';
										mensajed+= '</div>';
										mensajeConfirmacion(mensajed);
									}
									else {
										guardarCambioMedico();
									}
								}
								else {
									guardarCambioMedico();
								}
								
						}
					);
				
				$("#rechazarValidacion").click(
						function() {
							cargarRazonRechazo();
						}
					);
				$("#guiaTramiteValidacion").click(
						function() {
							alert('En construccion');
						}
					);
				$("#regresarValidacion").click(
						function() {
							$("#regresarValidacion").hide();
							$("#regresarGrupoFamiliar").show();
							regresarDocumentacion();
							$("#mensajeConfirmacion").html("");
							guardarValidacion=false;
							habilitarCombos(true);
						}
					);
				//termino del if
			}else{
				$("#rechazarValidacion").hide();
				$("#regresarGrupoFamiliar").hide();
				$("#guiaTramite").hide();
				$("#regresarValidacion").hide();
				$("#aceptarValidacion").hide();
				$("#rechazarTramite").hide();
			}
			
			$("#regresarGrupoFamiliar").click(
					function() {
						if($("#validacion").val()==1)
							cancelarCorreccion();
						else {
							$.blockUI();
							location.href = "" + context_path + "/derechohabiente/correccion/cambioMedico/";
						}
							
					}
				);
});

function guardarCambioMedico(){
	$decision = $('<div></div>');

	$decision.dialog({
		autoOpen : false,
		resizable : false,
		height : 160,
		width: 300,
		title : 'Selecciona una opci\u00F3n',
		modal : true,
		buttons : {
			"Si" : function() {
				$.blockUI();
				cierraDialogo($(this));
				
				$("fieldset#medicoEnTurno select").each(
						function(index) {
							$(this).removeAttr("disabled");
						}
				);
				
				$("#correccionDatos").on("submit",function(){$.blockUI();});
				$("#correccionDatos").submit();
				
			},
			"No" : function() {
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.text('\u00BF Est\u00E1 seguro que desea guardar el tr\u00E1mite de cambio de consultorio o turno?');
	$decision.dialog('open');
}

function aceptarValidacion(){
	
	
	var mensaje= '<div class="ui-widget">' +
	'<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> ' +
	'<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>' +
	'Por favor verifique la informaci\u00F3n proporcionada y de clic en Aceptar para continuar con el registro.'+
	'Si requiere corregir datos de clic en Regresar.</p></div></div>';
	guardarValidacion=true;
	mensajeConfirmacion(mensaje);
	$("#mensajeConfirmacion").html(mensaje);
    $("#regresarGrupoFamiliar").hide()
    $("#regresarValidacion").show()
    $("#guiaTramite").hide()
    habilitarCombos(false);
}

function cancelarCorreccion() {
	$decision = $('<div></div>');

	$decision.dialog({
		autoOpen : false,
		resizable : false,
		width : 300,
		title : 'Advertencia',
		modal : true,
		buttons : {
			"Si" : function() {
				rechazarSolicitud("",$(this));
//				cierraDialogo($(this));
//				fnAbrirMensajeEsperePorFavor();
//				location.href = "" + context_path + "/inicio/grupoFamiliar";
			},
			"No" : function() {
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.text('\u00BF Est\u00E1 seguro que desea cancelar el tr\u00E1mite de cambio de m\u00E9dico?');
	$decision.dialog('open');
}



function cargarRazonRechazo() {
	
	$razonRechazo = $('<div></div>');
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
	var observacionesRec = $('#observacionesRechazo').val();
	var idSolicitud = $('#idSolicitud').val();
	cierraDialogo($dialogo);
	$.blockUI();
	
	var formulario = $("form");
	formulario.attr("id","rechazoF");
	formulario.attr("name","rechazoF");
	formulario.attr("method","POST");
	formulario.attr("action",context_path + "/tramite/rechazar");
	formulario.append("<input type='hidden' name='idSolicitud' value='"+ idSolicitud +"'>");
	formulario.append("<input type='hidden' name='idPersona' value='"+$('#idPersona').val()+"'>");
	formulario.append("<input type='hidden' name='idTipoTramite' value='37'>");
	formulario.append("<input type='hidden' name='idRazonRechazo' value='"+razonRechazo+"'>");
	formulario.append("<input type='hidden' name='idTramite' value='"+$('#tramiteId').val()+"'>");
	formulario.append("<input type='hidden' name='observaciones' value='"+observacionesRec+"'>");
	formulario.submit();

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
					showComprobanteValidacionCircunscripcion("AUTORIZACI�N DE CIRCUNSCRIPCI�N FOR�NEA");
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

function regresarDocumentacion(){

	var lenght=doctosCargadosLenght+1;
	$('#thAccion').show();
	for(var i=0;i<lenght;i++){
		$('#eliminaDoc'+i).show();
	}
	
	$('#fileUploadMessages').html("");
}
