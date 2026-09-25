/**
 * Guillermo Hernandez Dolores
 * 
 */

//Indice requiere docs aqui estuvo Dieguin 

var banValidacion=false;
$(document).ready(
	function() {
		
	if($("#validacion").val() == 1) {
		$("fieldset#medicoEnTurno select").each(
				function(index) {
					$(this).attr("disabled","disabled");
				}
			);
	}
	
	//Botones de validacion de datos
	$("#aceptarValidacion").click(
			function() {
				guardarValidacion();
			}
		);
	$("#regresarGrupoFamiliar").click(
			function() {
				cancelarCorreccion();
			}
		);
	$("#rechazarValidacion").click(
			function() {
				cargarRazonRechazo();
			}
		);

	$("#regresarValidacion").click(
			function() {
				$("#regresarGrupoFamiliar").show();
				$("#regresarValidacion").hide();
				$("fieldset#medicoEnTurno select").each(
						function(index) {
							$(this).removeAttr("disabled");
						}
					);
				banValidacion=false;
			}
		);
	$("#guiaTramiteValidacion").click(
			function() {
				alert('En construccion');
			}
		);		
	
	
	}
);

function aceptarValidacion(){
	var mensaje="Por favor verifique la informaci\u00F3n proporcionada y de clic en guardar para continuar con el registro. " +
	"Si requiere corregir datos de clic en regresar.";
	banValidacion=true;
	aceptarValidacionC();
	$("#mensajeConfirmacion").val(mensaje);
	$("#regresarGrupoFamiliar").hide();
    $("#regresarValidacion").show()
    $("select").each(
			function(index) {
				$(this).attr("disabled","disabled");
			}
		);
   
}

function guardarValidacion(){
	
	
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
				$("#autCircunscripcionFor").on("submit",function(){$.blockUI();});
				$("#autCircunscripcionFor").attr("action",""+context_path+"/derechohabiente/correccion/circunscripcion/autorizar/validar");
				$("#autCircunscripcionFor").submit();
				
			},
			"No" : function() {
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.text('\u00BF Est\u00E1 seguro que desea aprobar la circunscripci\u00F3n for\u00E1nea?');
	$decision.dialog('open');

}

function guardarAutCirc(solicitud, $div) {
	var urlGuardar= context_path + "/derechohabiente/correccion/circunscripcion/autorizar/validar"
	$.postJSON(urlGuardar, solicitud, function(result) {
		cierraDialogo($div);
		mostrarResultado(result.mensaje, result.modelo != null ? true : false);
	})
}

function mostrarResultado(mensaje,resultado) {
	$decision = $('<div></div');
	$decision.dialog({
		autoOpen : false,
		resizable : false,
		height : 160,
		width:300,
		title : 'Resultado',
		modal : true,
		buttons : {
			"Aceptar" : function() {
				if(resultado)
					showComprobanteSuspencion($("#idTramite").val(),$("#idPersona").val(), "AUTORIZACI�N DE CIRCUNSCRIPCI�N FOR�NEA");
				
				cierraDialogo($(this));
				location.href = "" + context_path + "/inicio/grupoFamiliar";
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.html(mensaje);
	$decision.dialog('open');
}

function showComprobanteSuspencion(idTramite,idPersona, titulo){
	var direccion= context_path + "/documentos/circunscripcionA?idTramite="+idTramite+"&titulo="+titulo+"";
	var page= context_path + "/resources/js/delta/viewPdf.html";
	window.showModalDialog(page,direccion,"resizable:1;dialogHeight:550px;dialogwidth:985px;scroll:yes;status=no");	
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
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.html(mensaje);
	$decision.dialog('open');
}


function cancelarCorreccion() {

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

	$decision.text('\u00BF Est\u00E1 seguro que desea cancelar el tr\u00E1mite de autorizacion de circunscripci\u00F3n for\u00E1nea?');
	$decision.dialog('open');
}


function cargarRazonRechazo() {
	
	$razonRechazo = $('<div></div');
	$razonRechazo.html('Cargando Razones de rechazo...')
	$razonRechazo.dialog({
		autoOpen : false,
		title: 'Raz\u00F3n rechazo',
		show: "blind",
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
	formulario.append("<input type='hidden' name='idSolicitud' value='"+$('#solicitudId').val()+"'>");
	formulario.append("<input type='hidden' name='idPersona' value='"+$('#idPersona').val()+"'>");
	formulario.append("<input type='hidden' name='idTipoTramite' value='38'>");
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
					showComprobanteValidacionCircunscripcion("AUTORIZACI�N DE CIRCUNSCRIPCI�N FOR�NEA");
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

function aceptarValidacionC(){
	var mensaje= '<div class="ui-widget">' +
	'<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> ' +
	'<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>' +
	'Por favor verifique la informaci\u00F3n proporcionada y de clic en Aceptar para continuar con el registro.'+
	'Si requiere corregir datos de clic en Regresar.</p></div></div>';
	
	mensajeConfirmacion(mensaje);
}

function mensajeConfirmacion(mensaje) {
	
	$confirmacion = $('<div></div');
	$confirmacion.html(mensaje);
	$confirmacion.dialog({
		autoOpen : false,
		title: 'Mensaje del sistema',
		show: "blind",
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