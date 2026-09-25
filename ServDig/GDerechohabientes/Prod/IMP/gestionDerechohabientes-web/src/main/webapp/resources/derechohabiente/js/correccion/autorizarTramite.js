$(document).ready(
	function() {
		$.ajaxSetup({ cache: false }); 
		
		$("#aceptar").click(
			function() {
				//tramite aceptado
				aceptarTramite();
			}
		);
		$("#rechazar").click(
				function() {
					//tramite rechazado
					cargarRazonRechazo();
				}
			);
		
		$("#regresar").click(regresarSolicitudesPendientes);
		
	});

function aceptarTramite(){
	var urlGuardar= context_path + "/derechohabiente/correccion/tramite/autorizar/guardar"
	
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
				$("#registro").attr("action",""+context_path+"/derechohabiente/correccion/tramite/autorizar/guardar");
				$("#registro").submit();
			},
			"No" : function() {
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.text('\u00BF Est\u00E1 seguro que desea aprobar la correcci\u00F3n de datos?');
	$decision.dialog('open');
}

function esperePorFavor() {
	$espere = $('<div></div');

	$espere.dialog({
		autoOpen : false,
		resizable : false,
		height : 140,
		title : '',
		modal : true
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$espere.text('Espere un momento por favor');
	$espere.dialog('open');
}

function cargarRazonRechazo() {
	
	$razonRechazo = $('<div></div');
	$razonRechazo.html('Cargando Razones de rechazo...')
	$razonRechazo.dialog({
		autoOpen : false,
		title: 'Razon rechazo',
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
	formulario.append("<input type='hidden' name='idSolicitud' value='"+$('#solicitudId').val()+"'>");
	formulario.append("<input type='hidden' name='idPersona' value='"+$('#idPersona').val()+"'>");
	formulario.append("<input type='hidden' name='idTipoTramite' value='24'>");
	formulario.append("<input type='hidden' name='idRazonRechazo' value='"+razonRechazo+"'>");
	formulario.append("<input type='hidden' name='idTramite' value='"+$('#idTramite').val()+"'>");
	formulario.append("<input type='hidden' name='observaciones' value='"+$('#observacionesRechazo').val()+"'>");
	formulario.submit();
	
/*	var solicitud = {
		'idSolicitud': $('#solicitud\\.idSolicitud').val(),
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
					//showComprobanteValidacionCircunscripcion("AUTORIZACIÓN DE CIRCUNSCRIPCIÓN FORÁNEA");
					location.href= context_path + "/welcome/uno/busqueda";
				}
				
			}
	};

	return opciones;
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
				location.href= context_path + "/welcome/uno/busqueda";
				cierraDialogo($(this));
				
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.html(mensaje);
	$decision.dialog('open');
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

function cierraDialogo($dialogo){
	$dialogo.dialog('close');
	$dialogo.dialog('destroy');
	$dialogo.html('');
}

