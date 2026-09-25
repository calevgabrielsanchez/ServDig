var funcionesComunes = {
	contextApp: '/${mvn.web.app.root}',
	origenApp: '${mvn.web.app.origin.id}',
	elementoFolio: '#noFolioSolicitud',
	init: function() {
		$.each(eventosComunes.eventos, function(index, value){
			//verificamos que exista el elemento al que le queremos aplicar el evento
			if($(value.id).length) {
				$(value.id).on("click", value.funcion);
			}
		});
	},
	iniciarTramite: function() {
		$("#solicitudEscrito").submit();
	},
	cancelarTramite: function() {
		var opcionesDialog = dialogosComunes.cancelarTramite;
		opcionesDialog.mensaje = opcionesDialog.mensaje.replace("%FOLIO%",$(funcionesComunes.elementoFolio).val());
		dialogosCtrl.abrirDialogo(opcionesDialog);
	},
	salirTramite: function() {
		dialogosCtrl.abrirDialogo(dialogosComunes.salirTramite);
	},
	verDocumentos: function() {
		$(this).dialog('close');
		$.blockUI();
		funcionesComunes.limpiarSession(function(){
			//console.log("Voy a ver los documentos del folio " + $("#noFolioSolicitud").val());
			parent.ejecutarConsultaSolicitudPorFolio($("#noFolioSolicitud").val());
			parent.WizardEscritoCtrl.cerrar();
		});
		
	},
	procesarSalirTramite: function() {
		$(this).dialog('close');
		$.blockUI();
		funcionesComunes.limpiarSession(function(){
			if(funcionesComunes.origenApp == "1") {
				location.href = funcionesComunes.contextApp + "/escrito";
			} else {
				parent.WizardEscritoCtrl.cerrar();
			}
		});
	},
	procesarCancelar: function() {
		$.blockUI();
		$.postJSON(funcionesComunes.contextApp + "/escrito/wizard/cancelarTramite", {noFolioSolicitud: $(funcionesComunes.elementoFolio).val()}, function(data) {
			if(data.correcto) {
				dialogosCtrl.abrirDialogo(dialogosComunes.tramiteCancelado);
			} else {
				console.log("error al cancelar la solicitud");
			}
		}).error(function(){ 
		}).complete($.unblockUI);
	},
	limpiarSession: function(funcion) {
		$.blockUI();
		$.postJSON(funcionesComunes.contextApp + "/escrito/wizard/limpiarSession", null, function(data) {	
		}).error(function(){ 
		}).complete(funcion);
	}
};

var eventosComunes = {
	eventos: [{
		id: "#cancelarTramite",
		funcion: funcionesComunes.cancelarTramite
	}, {
		id: "#salirTramite",
		funcion: funcionesComunes.salirTramite
	},{
		id: "#iniciarTramite",
		funcion: funcionesComunes.iniciarTramite
	} ,
	{
		id: "#retomarTramite",
		funcion:  funcionesComunes.iniciarTramite
	}]
};

var dialogosComunes = {
	"salirTramite" : {
		titulo: 'Confirmaci&oacute;n',
		mensaje:"&iquest; Estas seguro que deseas salir ?",
		buttons: {
			'No': dialogosCtrl.close, 
			'Si': funcionesComunes.procesarSalirTramite
		}
	},
	"cancelarTramite" : {
		titulo: 'Confirmaci&oacute;n',
		mensaje:"&iquest; Estas seguro que deseas cancelar la solicitud con folio %FOLIO%?",
		buttons: {
			'No': dialogosCtrl.close, 
			'Si': funcionesComunes.procesarCancelar
		}
	},
	"tramiteCancelado": {
		titulo: 'Informaci&oacute;n',
		mensaje:"La solicitud ha sido cancelada exitosamente",
		buttons: {
			'Salir': funcionesComunes.procesarSalirTramite
		}
	}
};