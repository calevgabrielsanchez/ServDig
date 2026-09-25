/**
 * Guillermo Hernandez Dolores
 * 
 */
var registroValidacion=false;
var mandaGrupoFamiliar =false;
$(document).ready(
	function() {
		$("#regresar").hide();
		if($("#validacion").val()==1){
			$("#regresarGrupoFamiliar").show();
			$("#medicoEnTurno//.turno//.idTurno").val($("#idTurno").val());
		}
		$("#regresarValidacion").hide();
	//Botones de validacion de datos
	$("#aceptarValidacion").click(
			function() {
				//if(registroValidacion){
					guardarSuspension();
/*				}else{
					aceptarValidacion();
				}*/
			}
		);
	$("#regresarGrupoFamiliar").click(
			function() {
				cancelarValidacion();
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
	
	$("#regresarValidacion").click(
			function() {
			$("#regresarValidacion").hide();
			registroValidacion=false;
			$("#regresarGrupoFamiliar").show();	
			$("#mensajeConfirmacion").text('');		
	
		});
	
	}
);

function regresarValidacion(){
	$("#regresarGrupoFamiliar").show()
    $("#guiaTramite").show()
    $("#regresar").hide()
    guardarValidacion=false;
}

function aceptarValidacion(){
	
	guardarValidacion=true;
	registroValidacion=true;
	aceptarValidacionC();
    $("#regresarGrupoFamiliar").hide()
    $("#regresarValidacion").show();
    $("#guiaTramite").hide()
}

function mensageConfirmacion(mensaje){
	$ventana = $('<div></div');
	
	$ventana.append(mensaje);	
	$ventana.dialog({
		autoOpen : false,
		title: 'Mensaje',
		show: "blind",
		hide: "explode",
		modal: true,
		height: 200,
		width: 500,
		buttons: {
			"Aceptar": function() {
				cierraDialogo($(this));
			}
		}
	
		}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	
	$ventana.dialog('open');
}

function guardarSuspension(){
	
	
	$decision = $('<div></div');
	
	$decision.dialog({
		autoOpen : false,
		resizable : false,
		height : 160,
		title : 'Informaci\u00F3n',
		modal : true,
		buttons : {
			"Si" : function() {
				cierraDialogo($(this));
				esperePorFavor();
				guardaSusp();
				
			},
			"No" : function() {
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.text('\u00BF Est\u00E1 seguro que desea aprobar la suspensión de la circunscripci\u00F3n f\u00F3ranea?');
	$decision.dialog('open');

}

function guardaSusp() {
	var urlGuardar= context_path + "/derechohabiente/correccion/circunscripcion/suspension/validar";
	$("form#correccionDatos").attr("action",urlGuardar);
	$("#observacion").removeAttr("disabled");
	$("form#correccionDatos").submit();
/*	$.postJSON(urlGuardar,tramite, function(result) {
		cierraDialogo($div);
		if(result.modelo != null)
			mostrarResultado(result.mensaje);
		
	})*/
}

function mostrarResultado(mensaje){
	$finali = $('<div></div');
	$finali.dialog({
		autoOpen : false,
		resizable : false,
		height : 160,
		width:300,
		title : '',
		modal : true,
		buttons : {
			"Aceptar" : function() {
				showComprobanteSuspencion($("#idTramite").val(),$("#idPersona").val());
				location.href = "" + context_path + "/inicio/grupoFamiliar";
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$finali.html(mensaje);
	$finali.dialog('open');
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
				if(mandaGrupoFamiliar){
					location.href = "" + context_path + "/inicio/grupoFamiliar";
				}
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.html(mensaje);
	$decision.dialog('open');
}


function cancelarValidacion() {
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
					cierraDialogo($(this));
					esperePorFavor();
					location.href = "" + context_path + "/inicio/grupoFamiliar";
				},
				"No" : function() {
					cierraDialogo($(this));
				}
			}
		}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
		$decision.text('\u00BF Est\u00E1 seguro que desea cancelar el tr\u00E1mite de autorizaci\u00F3n de circunscripci\u00F3n f\u00F3ranea?');
		$decision.dialog('open');
	}else{
		esperePorFavor()
		location.href = "" + context_path + "/derechohabiente/correccion/circunscripcion/suspencion";
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
	formulario.append("<input type='hidden' name='idSolicitud' value='"+$('#tramiteSuspension\\.solicitud\\.idSolicitud').val()+"'>");
	formulario.append("<input type='hidden' name='idPersona' value='"+$('#idPersona').val()+"'>");
	formulario.append("<input type='hidden' name='idTipoTramite' value='39'>");
	formulario.append("<input type='hidden' name='idRazonRechazo' value='"+razonRechazo+"'>");
	formulario.append("<input type='hidden' name='idTramite' value='"+$('#idTramite').val()+"'>");
	formulario.append("<input type='hidden' name='observaciones' value='"+$('#observacionesRechazo').val()+"'>");
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
					showComprobanteValidacionCircunscripcion("AUTORIZACIÓN DE CIRCUNSCRIPCIÓN FORÁNEA");
					location.href= context_path + "/inicio/grupoFamiliar";
				}
				
			}
	};

	return opciones;
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


function esperePorFavor() {
	$decision = $('<div></div>');

	$decision.dialog({
		autoOpen : false,
		resizable : false,
		height : 140,
		title : '',
		modal : true
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.text('Espere un momento por favor');
	$decision.dialog('open');
}
