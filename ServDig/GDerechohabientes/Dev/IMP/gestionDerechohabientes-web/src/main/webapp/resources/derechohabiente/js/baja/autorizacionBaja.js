/**
 * @author Mario Teran Blanco
 * Script para la validacion de la baja de derechohabiente
 * 28/05/2012
 */


$(document).ready(
	function() {
		$('#rechazar').click(cargarRazonRechazo);
		
		$('#regresar').click(regresarSolicitudesPendientes);
		
		$('#autorizar').click(procesarAutorizacionBaja);
	}	
);

var procesarAutorizacionBaja = function() {
	
	$decision = $('<div></div');
	
	$decision.dialog({
		autoOpen : false,
		resizable: false,
		height: 140,
		title: 'Selecciona una opci\u00F3n',
		modal: true,
		buttons: {
			"Si": function() {
				guardarAutorizacionBaja($(this));
			},
			"No": function() {
				cierraDialogo($(this));
			}
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	$decision.text('\u00BF Est\u00E1 seguro que desea autorizar el tr\u00E1mite de baja?');
	$decision.dialog('open');
}

function guardarAutorizacionBaja($dialog) {

	if(typeof($('#fechaDefuncion').val()) != "undefined")
		$('#fechaDefuncion').removeAttr("disabled");
	
	$('#observaciones').removeAttr("disabled");
	
	cierraDialogo($dialog);
	fnAbrirMensajeEsperePorFavor();
	$("#validacion").attr("action",""+context_path+"/derechohabiente/baja/guardar/autorizacion");
	$("#validacion").submit();
}

var cargarRazonRechazo = function() {
	
	$razonRechazo = $('<div></div');
	$razonRechazo.html('Cargando Razones de rechazo...')
	$razonRechazo.dialog({
		autoOpen : false,
		title: 'Raz\u00F3n rechazo',
		show: "blind",
		hide: "explode",
		resizable: false,
		modal: true,
		width: 500,
		buttons: {
			"Si": function() {
				var razon = $('#idRazonRechazo').val();
				var observaciones = $('#observacionesRechazo').val();
				rechazarSolicitud(razon,observaciones,$(this));
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

function rechazarSolicitud(razonRechazo, observaciones, $dialogo) {
	
	cierraDialogo($dialogo);
	fnAbrirMensajeEsperePorFavor();
	
	var formulario = $("form");
	formulario.attr("id","rechazoF");
	formulario.attr("name","rechazoF");
	formulario.attr("method","POST");
	formulario.attr("action",context_path + "/tramite/rechazar");
	formulario.append("<input type='hidden' name='idSolicitud' value='"+$('#idSolicitud').val()+"'>");
	formulario.append("<input type='hidden' name='idPersona' value='"+$('#idPersona').val()+"'>");
	formulario.append("<input type='hidden' name='idTipoTramite' value='"+$('#idTipoTramite').val()+"'>");
	formulario.append("<input type='hidden' name='idRazonRechazo' value='"+razonRechazo+"'>");
	formulario.append("<input type='hidden' name='idTramite' value='"+$('#idTramite').val()+"'>");
	formulario.append("<input type='hidden' name='observaciones' value='"+observaciones+"'>");
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
				if(solicitud != null) {
					showComprobanteRechazo();
				}
				if(tramite!=null && tramite != undefined) {
					if(tramite.modelo.estadoTramite.idEstadoTramitePersona == 2) {
						showComprobanteValidacionBaja($('#idTramite').val(),"BAJA DERECHOHABIENTE")
					}
				}
				location.href= context_path + "/welcome/uno/valida";
			}
		}
	};

	return opciones;
}

var regresarSolicitudesPendientes = function() {

	$decision = $('<div></div');

	$decision.dialog({
		autoOpen : false,
		resizable : false,
		height : 140,
		title : 'Selecciona una opci\u00F3n',
		modal : true,
		buttons : {
			"Si" : function() {
				location.href = "" + context_path + "/welcome/uno/busqueda";
			},
			"No" : function() {
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.text('\u00BF Est\u00E1 seguro que desea salir?');
	$decision.dialog('open');
}

function showComprobanteValidacionBaja(idTramite, titulo){
	var direccion=context_path + "/documentos/documentosBaja?idTramite="+idTramite+"&titulo="+titulo+"";
	var page=context_path + "/resources/js/delta/viewPdf.html";
	window.showModalDialog(page,direccion,"resizable:1;dialogHeight:550px;dialogwidth:985px;scroll:yes;status=no");	
}

function showComprobanteRechazo(){
	var direccion=context_path + "/documentos/rechazoSolicitud?titulo=BAJA DERECHOHABIENTE&tipoTramite=";
	var page=context_path + "/resources/js/delta/viewPdf.html";
	window.showModalDialog(page,direccion,"resizable:1;dialogHeight:550px;dialogwidth:985px;scroll:yes;status=no");	
}

function cierraDialogo($dialogo){
	$dialogo.dialog('close');
	$dialogo.dialog('destroy');
	$dialogo.html('');
}