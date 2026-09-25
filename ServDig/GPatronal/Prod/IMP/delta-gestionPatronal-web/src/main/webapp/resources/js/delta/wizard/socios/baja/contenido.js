var urlWizardSociosB	= context_path + '/wizard/tramite/bajaSocios/';
var idFormaBSocios		= 'soForm';
var dialogoConfirmarCancelar;
var dialogoConfirmarCommon;

$(document).ready(function() {
	
	// Se incializa el blockUI para las peticiones AJAX
	$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
	
	$('#cerrarWizard').click(function() {
		cerrarWizard();
	});
	
	$('#cancelarTramite').click(function() {
		dialogoConfirmarCancelar.dialog( "open" );
	});
	
	$('#guardarTramite').click(function() {
		guardarTramite();
	});
	
	//Siguiente - Seleccion de RL
	$('#mostrarRLVentanilla').click(function() {
		
		var sociosSelected = getSociosSeleccionados();		
		if(sociosSelected.length == 0) {
			mostrarMensajeSeleccionarSocios();
		} else {			
			//Validar socio activo antes de finalizar solicitud
			var socios = getSociosSeleccionados();
			var persona = {
				'socios' : socios		 
			};			
			var url = urlWizardSociosB + 'validarSolicitud';
			prepararRequest(url, persona, true, validarSocioActivo);	
			
		}
	});
	
	$('#finalizarTramite').click(function() {
		
		var sociosSelected = getSociosSeleccionados();		
		if(sociosSelected.length == 0) {
			mostrarMensajeSeleccionarSocios();
		} else {
			//Validar socio activo antes de finalizar solicitud
			var socios = getSociosSeleccionados();
			var persona = {
				'socios' : socios		 
			};			
			var url = urlWizardSociosB + 'validarSolicitud';
			prepararRequest(url, persona, true, validarSocioActivo);	
		}		
	});
	
	dialogoConfirmarCancelar = $( "#dialog-confirm-cancelar" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false,
		buttons: {
			"ACEPTAR": function() {
				cancelarTramite();
		 	},
		 	"CANCELAR": function() {
		 		$( this ).dialog( "close" );
		 	}
		 }
	 });
	
	dialogoConfirmarCommon = $( "#dialog-confirm-common" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false
	 });
	
});

function validarSocioActivo(response) {
	if (response.mensaje != undefined && response.mensaje != null) {		
		
		if(parent.WizardBajaSociosCtrl.config.idOrigen == 2){
			//Finalizar tramite Internet
			crearArraySocios();
		}else{
			//Finalizar tramite Ventanilla
			procesarSolicitudVentanilla();
		}
		
	}else{
		//Error de validación de Socio Activo
		inicializarDialogo();		
		mostrarDialogoH(response.error);
	}
}


function getSociosSeleccionados() {
	var socios = new Array();
	$("input[@name='socioL']:checked").each(function() {
		var idSoc = $(this).val();
		 if($.trim(idSoc).length > 0)
			 socios.push({"idSocio": idSoc});
	 });	
	return socios;
}

function mostrarMensajeSeleccionarSocios() {
	inicializarDialogo();
	mostrarDialogo("Debe seleccionar al menos un socio a dar de baja.");	
}

function crearArraySocios() {
	var aSocios = new Array();
	
	var url = urlWizardSociosB + 'get/seleccionados';	
	var socios = getSociosSeleccionados();
	var persona = {
		'socios' : socios		 
	};
	
	if(socios.length > 0) {
		$.postJSON(url,persona,function(data) {
			
			var sociosActuales = data.rLSeleccionados;
			
			for(var i= 0;i<sociosActuales.length;i++) {
				for(var j=0; j < socios.length; j++) {
					if(sociosActuales[i].idSocio == socios[j].idSocio) {
						var socioHelper = sociosActuales[i];
						var socioRec = new Object();
						
						socioRec.nombreRazonSocial= socioHelper.nombreRazonSocial;
						socioRec.rfc = socioHelper.rfc;
						socioRec.curp = socioHelper.curp;
						
						aSocios.push(socioRec);
					}
				}
			}
			if(parent.WizardBajaSociosCtrl.config.idOrigen == 2){
				//INTERNET
				firmarBajaSocios(aSocios);
			}			
		});
		
	}
}


function firmarBajaSocios(aSocios) {	
	
	parent.FirmaDigitalCtrl.setOnCloseCallback(function() {
		
		inicializarDialogo();
		if(parent.FirmaDigitalCtrl.datosSalida == null) {
			mostrarDialogo("La validaci\u00f3n de la firma no pudo ser realizada");
		}else {
			if(parent.FirmaDigitalCtrl.datosSalida.Resultado == 0) {
				var firmaResponse = {
					cadenaOriginal               : parent.FirmaDigitalCtrl.datosSalida.contenedores[0].cadori,
					recibo                       : parent.FirmaDigitalCtrl.datosSalida.contenedores[0].cms,
					reciboNotarial               : parent.FirmaDigitalCtrl.datosSalida.folio,
					urlAcuseFirma                : parent.FirmaDigitalCtrl.datosSalida.acuse,
					serialCertificado            : parent.FirmaDigitalCtrl.datosSalida.serie_cert,
					strIniciaVigenciaCertificado : parent.FirmaDigitalCtrl.datosSalida.vigIni,
					strFinVigenciaCertificado    : parent.FirmaDigitalCtrl.datosSalida.vigFin
				};

				firmarTramite(firmaResponse);
			} else {
				mostrarDialogo("La validaci\u00f3n de la firma no pudo ser realizada");
			}
		}
	});

	var componenteFirma = {			
			idTipoSolicitud : codigoTipoSolicitud,
			descripcionTipoSolicitud : descripcionTipoSolicitud,
			idTipoTramite : arrayCodigoTipoTramite,
			folioSolicitud : $('#folioSolicitud').val(),
			curp : parent.FirmanteCtrl.curp,
			rfc : parent.FirmanteCtrl.rfc,
			validarRFC : true,
			registroPatronal : parent.AtributosPersonaCtrl.personaPortal.registroPatronal,
			nombreCompleto : parent.FirmanteCtrl.nombreRazonSocial,
			fechaElectronica : datosEntradaFirma.fechaElectronica,
			cad_original : $('#contenidoFirmar').val(),
			tipo_operacion : 'firmaCMS',
			firma_archivo : false,
			min_archivos : 0,
			max_archivos : 0,
			representados: aSocios,
			afectado: [parent.AtributosPersonaCtrl.personaPortal],
			tipoAcuse: '1',
			acuse: 'CDCT'
	};
	
	parent.iniciarFirmaDigital(componenteFirma);
}

function firmarTramite(firmaResponse) {
	var url = urlWizardSociosB+'procesarDatosFirma';
	
	$.postJSON(url, firmaResponse, function(data) {
		finalizarTramite();
	}).error(function(data){		
		mostrarDialogo(data.mensaje);
	});
}

function finalizarTramite() {
	$.blockUI();	
	var idSolicitudPendiente = $('#idSolicitud').val();
	var folioSolicitudPendiente = $('#folioSolicitud').val();
	var url = urlWizardSociosB+'finalizar/solicitud/'+idSolicitudPendiente;
	
	var socios = getSociosSeleccionados();
	var persona = {
		'socios' : socios
	};
		
	$.postJSON(url, persona, function(data) {		
		parent.ProcesandoSolicitudCtrl.abrir(folioSolicitudPendiente);	
		cerrarWizard();		
	}).error(function(data){		
		mostrarDialogo(data.error);
	});
}

function guardarTramite() {
	
	var sociosSelected = getSociosSeleccionados();
	
	if(sociosSelected.length == 0) {
		mostrarMensajeSeleccionarSocios();
	} else {
		var url = urlWizardSociosB+'solicitud/guardar';
		var persona = {
			'socios' : sociosSelected
		};
		inicializarDialogo();

		$.postJSON(url, persona , function(data) {
			mostrarDialogo(data.mensaje);
		}).error(function(data){
			mostrarDialogo(data.mensaje);
		});
	}

}

function cancelarTramite() {
	var idSolicitudPendiente = $('#idSolicitud').val();
	var url = urlWizardSociosB+'cancelar/solicitud/'+ idSolicitudPendiente;
	
	inicializarDialogoCancelacion();
		
	$.postJSON(url, null, function(data) {
		mostrarDialogo(data.mensaje);
	}).error(function(data){
		mostrarDialogo(data.mensaje);
	});
}

function cerrarWizard() {	
	parent.WizardBajaSociosCtrl.cerrar();
}

function inicializarDialogoCancelacion() {
	dialogoConfirmarCommon.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
			cerrarWizard();
		}
	}]);
}

function inicializarDialogo() {
	dialogoConfirmarCommon.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
		}
	}]);
}

function mostrarDialogo(mensaje) {
	$('#mensajeDialogo').text(mensaje);
	dialogoConfirmarCommon.dialog('open');
}

function mostrarDialogoH(mensaje) {	
	$('#mensajeDialogo').html(mensaje);
	dialogoConfirmarCommon.dialog('open');
}

//Funciones para mostrar seleccion de Representantes Legales
function procesarSolicitudVentanilla() {
	var idSolicitudPendiente = $('#idSolicitud').val();
	var folioSolicitudPendiente = $('#folioSolicitud').val();
	var url = urlWizardSociosB+'finalizar/solicitud/'+idSolicitudPendiente;
	
	var socios = getSociosSeleccionados();
	var persona = {
		'socios' : socios
	};
	
	prepararRequest(url, persona, true, mostrarRepresentantesLegales);
	
}

function mostrarRepresentantesLegales(response){
	if (response.mensaje != undefined && response.mensaje != null) {
		var idSolicitudPendiente = $('#idSolicitud').val();
		setTimeout(function(){
			var urlAction = parent.representantesLegalesCtrl.config.urlRLVentanilla + idSolicitudPendiente+"/"+idTipoTramite;
			$.blockUI();
			$('form#'+idFormaBSocios).attr('action', urlAction);
			$('form#'+idFormaBSocios).submit();
		}, 90);
	}else{
		var mensaje = "Ocurrio un error al procesar la solicitud.";
		if (response.error == "") {			
		} else {
			mensaje = response.error;
		}
		mostrarDialogoH(mensaje);
	}	
}