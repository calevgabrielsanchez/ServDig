/**
 * @author mario.teran
 * Todo lo necesario para provesar la baja de un derechohabiente
 */

function bajaDerechohabienteConcubinato(idDerechohabiente) {
	
	$decision = $('<div></div');
	
	$decision.dialog({
		autoOpen : false,
		resizable: false,
		height: 240,
		title: 'Selecciona una opci\u00F3n',
		modal: true,
		buttons: {
			"Si": function() {
				procesarBajaDerechohabienteConcubinato(idDerechohabiente,$(this))
			},
			"No": function() {
				cierraDialogo($(this));
			}
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	$decision.text('\u00BF Est\u00E1 seguro que desea registrar el tr\u00E1mite de baja por t\u00e9rmino de concubinato?');
	$decision.dialog('open');
}

function procesarBajaDerechohabienteConcubinato(idDerechohabiente,$dialogo) {
	
	var derechohabiente = {'idDerechohabiente': idDerechohabiente};
	var url = context_path + "/derechohabiente/baja/concubinato/guardar";
	
	$dialogo.dialog(parametrosEspera());
	$dialogo.html('Esperando Respuesta');
	
	$dialogo.load(url, derechohabiente, function(){
		parametrosCitaoValidacion($dialogo);
	});
}


function bajaDerechohabienteDefuncion(idDerechohabiente,nombre, curp) {
	
	$decision = $('<div id="proceso"></div');
	
	$decision.dialog({
		autoOpen : false,
		resizable: false,
		height: 200,
		width: 400,
		title: 'Selecciona una opci\u00F3n',
		modal: true,
		buttons: {
			"Si": function() {
				procesarBajaDerechohabienteDefuncion(idDerechohabiente,$(this))
			},
			"No": function() {
				cierraDialogo($(this));
			}
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	$decision.html('\u00BFEst\u00E1 seguro que desea dar de <b>BAJA POR DEFUNCI\u00D3N</b> al derechohabiente seleccionado? ' +
		'<br><br><b>CURP:</b> ' + curp +
		"<br><b>Nombre:</b> " + nombre);
	$decision.dialog('open');
}

function bajaDerechohabienteAdministrativa(idDerechohabiente) {
	
	$decision = $('<div id="proceso"></div');
	
	$decision.dialog({
		autoOpen : false,
		resizable: false,
		height: 140,
		title: 'Selecciona una opci\u00F3n',
		modal: true,
		buttons: {
			"Si": function() {
				procesarBajaDerechohabienteAdministrativa(idDerechohabiente,$(this))
			},
			"No": function() {
				cierraDialogo($(this));
			}
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	$decision.text('\u00BF Est\u00E1 seguro que desea dar de baja al derechohabiente seleccionado?');
	$decision.dialog('open');
}

function procesarBajaDerechohabienteDefuncion(idDerechohabiente,$dialogo) {
	var derechohabiente = {'idDerechohabiente': idDerechohabiente};
	var url = context_path + "/derechohabiente/baja/defuncion/guardar";
	
	$dialogo.dialog(parametrosEspera());
	$dialogo.html('Esperando Respuesta');
	
	$dialogo.load(url, derechohabiente, function(){
		parametrosCitaoValidacion($dialogo);
	});
}

function procesarBajaDerechohabienteAdministrativa(idDerechohabiente,$dialogo) {
	var derechohabiente = {'idDerechohabiente': idDerechohabiente};
	var url = context_path + "/derechohabiente/baja/administrativa/guardar";
	
	$dialogo.dialog(parametrosEspera());
	$dialogo.html('Esperando Respuesta');
	
	$dialogo.load(url, derechohabiente, function(){
		parametrosCitaoValidacion($dialogo);
	});
}

function bajaDerechohabienteConvivencia(idDerechohabiente) {
	
	$decision = $('<div></div');
	
	$decision.dialog({
		autoOpen : false,
		resizable: false,
		height: 140,
		title: 'Selecciona una opci\u00F3n',
		modal: true,
		buttons: {
			"Si": function() {
				procesarBajaDerechohabienteConvivencia(idDerechohabiente,$(this))
			},
			"No": function() {
				cierraDialogo($(this));
			}
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	$decision.text('\u00BF Est\u00E1 seguro que desea dar de baja al derechohabiente seleccionado?');
	$decision.dialog('open');
}

function procesarBajaDerechohabienteConvivencia(idDerechohabiente,$dialogo) {
	var derechohabiente = {'idDerechohabiente': idDerechohabiente};
	var url = context_path + "/derechohabiente/baja/convivencia/guardar";
	
	$dialogo.dialog(parametrosEspera());
	$dialogo.html('Esperando Respuesta');
	
	$dialogo.load(url, derechohabiente, function(){
		parametrosCitaoValidacion($dialogo);
	});
}

function bajaDerechohabienteDivorcio(idDerechohabiente) {
	
	$decision = $('<div></div');
	
	$decision.dialog({
		autoOpen : false,
		resizable: false,
		height: 140,
		title: 'Selecciona una opci\u00F3n',
		modal: true,
		buttons: {
			"Si": function() {
				procesarBajaDerechohabienteDivorcio(idDerechohabiente,$(this))
			},
			"No": function() {
				cierraDialogo($(this));
			}
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	$decision.text('\u00BF Est\u00E1 seguro que desea dar de baja al derechohabiente seleccionado?');
	$decision.dialog('open');
}

function procesarBajaDerechohabienteDivorcio(idDerechohabiente,$dialogo) {
	var derechohabiente = {'idDerechohabiente': idDerechohabiente};
	var url = context_path + "/derechohabiente/baja/divorcio/guardar";
	
	$dialogo.dialog(parametrosEspera());
	$dialogo.html('Esperando Respuesta');
	
	$dialogo.load(url, derechohabiente, function(){
		parametrosCitaoValidacion($dialogo);
	});
}

function bajaDerechohabienteUnionCivil(idDerechohabiente) {
	
	$decision = $('<div></div');
	
	$decision.dialog({
		autoOpen : false,
		resizable: false,
		height: 240,
		title: 'Selecciona una opci\u00F3n',
		modal: true,
		buttons: {
			"Si": function() {
				procesarBajaDerechohabienteUnionCivil(idDerechohabiente,$(this))
			},
			"No": function() {
				cierraDialogo($(this));
			}
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	$decision.text('\u00BF Est\u00E1 seguro que desea registrar el tr\u00E1mite de baja por t\u00e9rmino de Uni\u00F3n Civil?');
	$decision.dialog('open');
}

function procesarBajaDerechohabienteUnionCivil(idDerechohabiente,$dialogo) {
	
	var derechohabiente = {'idDerechohabiente': idDerechohabiente};
	var url = context_path + "/derechohabiente/baja/unionCivil/guardar";
	
	$dialogo.dialog(parametrosEspera());
	$dialogo.html('Esperando Respuesta');
	
	$dialogo.load(url, derechohabiente, function(){
		parametrosCitaoValidacion($dialogo);
	});
}


function irValidacionBaja() {
	var solicitud = $('#idSolicitudVal').val();
	var persona = $('#idPersona').val();
	var tramite = $('#idTramite').val();
	
	location.href = context_path + "/derechohabiente/baja/validar/"+solicitud+"/"+persona;
}

function cierraDialogo($dialogo){
	$dialogo.dialog('close');
	$dialogo.dialog('destroy');
	$dialogo.html('');
}

function parametrosCitaoValidacion($dialogo) {
	
	if($("#idSolicitud").val() != undefined) {
		$dialogo.dialog(parametrosCita($dialogo));
	} 
	else if($('#idSolicitudVal').val() != undefined) {
		$dialogo.dialog(parametrosContinuacionValidacion());
		$dialogo.dialog("option","buttons",{});
		irValidacionBaja();
	}
	else {
		$dialogo.dialog(parametrosRespuesta($dialogo));
		
	}
}

function parametrosCita($dialogo) {
	opciones = {
			autoOpen : false,
			resizable: false,
			position: 'center',
			width: 800,
			height: 400,
			title: 'Datos cita',
			modal: true,
			buttons: {
				"Aceptar" : function() {
					var valor = $("#idSolicitud").val();
					cierraDialogo($dialogo);
					fnAbrirMensajeEsperePorFavor();
					location.href = "" + context_path + "/tramite/comprobanteInternet?idSolicitud="+valor+"&titulo=BAJA DE DERECHOHABIENTE";
				}
			}
		};
		
		return opciones;
}

function parametrosContinuacionValidacion() {
	opciones = {
			autoOpen : false,
			resizable: false,
			width: 350,
			height: 140,
			title: 'Continuando...',
			modal: true,
			buttons: []
	};
}
