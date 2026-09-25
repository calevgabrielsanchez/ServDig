var aseguradoCometConn;

$(document).ready(function() {
	var urlComet = "/delta-comet-web/static/resources/js/delta/comet/CometConector.js";

	$.getScript(urlComet).done(function(script, textStatus) {
		aseguradoCometConn = new CometCtrl();
								
		var avisoProcesamientoSuscripcion = {
			channel : '/asignacionMasiva/avisoProcesamiento',
			action : avisoProcesamiento
		};
		var getResultadoParcialSuscripcion = {
			channel : '/asignacionMasiva/resultadoParcial',
			action : getResultadoParcial
		};
		var getRegistroIndividualSuscripcion = {
			channel : '/asignacionMasiva/registroIndividual',
			action : getRegistroIndividual
		};

		aseguradoCometConn.listInitialSubscription.push(avisoProcesamientoSuscripcion);
		aseguradoCometConn.listInitialSubscription.push(getResultadoParcialSuscripcion);
		aseguradoCometConn.listInitialSubscription.push(getRegistroIndividualSuscripcion);

		aseguradoCometConn.init();
		var _idRegistroPatronal = $('#idRegistroPatronal').val();
		var _cveIdUsuario = $('#cveIdUsuario').val();
		var _usuario = $('#usuario').val();
								
		var registroPatronalKey = {
			idRegistroPatronal : _idRegistroPatronal,
			cveIdUsuario : _cveIdUsuario,
			usuario : _usuario
		};
		aseguradoCometConn.publish('/iniciarProcesamiento', registroPatronalKey);
	})
	.fail(function(jqxhr, settings, exception) {
		alert('Error al cargar el script ' + urlComet);
	});
});

avisoProcesamiento = function(message) {
	var aviso = message.data.aviso;
	$('#dgMensajeSistema').text(aviso);
	oDialogoCerrarSesion.dialog('open');

	$('#btnCarga').removeClass('hiddenElement');
	$('#btnCarga').addClass('showElement');

	aseguradoCometConn.disconnect();
};

getResultadoParcial = function(message) {
	var numErrores = message.data.numErrores;
	var numExitos = message.data.numExitos;

	$('#numeroErrores').text(numErrores);
	$('#numeroExitos').text(numExitos);

	$loaderDiv.dialog('close');
	$('#loader').hide();
};

getRegistroIndividual = function (message) {
	var nombre = message.data.nombreCompleto;
	var exitoAlta = message.data.exitoAlta;
	var nssAsignado = message.data.nssAsignado;
	var msgAlta = '<span class="ui-icon ui-icon-closethick"></span> Alta fallido';

	if (exitoAlta) {
		msgAlta = '<span class="ui-icon ui-icon-check"></span> Alta exitoso';
	}

	dtDetalleAsignacion.fnAddData([nombre,  nssAsignado, msgAlta]);
};
