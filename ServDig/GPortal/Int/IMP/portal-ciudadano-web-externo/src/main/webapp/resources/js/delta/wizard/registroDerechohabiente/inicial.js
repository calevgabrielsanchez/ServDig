var dialogoConfirmarCancelar;
var dialogoConfirmar;
var dialogoConfirmarSalida;
var index=-1;

$(document).ready(function(){
	
	$("#selectable").selectable({
		selected : function(event, ui) {
			$(ui.selected).siblings().removeClass("ui-selected");
		},
		stop : function() {
			$(".ui-selected", this).each(function() {
				index = $("#selectable li").index(this);
			});
		}
	});
	
	$('#btnInciaTramite').click(function(){
		iniciarTramite();
	});

	if($("#dialog-confirm-cancelar").length > 0) {
		dialogoConfirmarCancelar = $( "#dialog-confirm-cancelar" ).dialog({
			resizable: false,
			height:'auto',
			modal: true,
			autoOpen: false,
			buttons: {
				"ACEPTAR": function() {
					$( this ).dialog( "close" );
					cancelarSolicitud();
				},
				"CANCELAR": function() {
					$( this ).dialog( "close" );
				}
			}
		});

		$('#btnIniciocancelarYContinuar').click(function(){
			dialogoConfirmarCancelar.dialog( "open" );
		});	
	}
	
	if($("#dialog-confirm-salir").length > 0) {
		dialogoConfirmarSalida = $( "#dialog-confirm-salir" ).dialog({
			resizable: false,
			height:'auto',
			modal: true,
			autoOpen: false,
			buttons: {
				"CONTINUAR": function() {
					$( this ).dialog( "close" );
					parent.WizardRegistroDerechohabienteCtrl.cerrar();
				}
			}
		});
	}

	$('#btnInicioCancelarTramite').click(function(){
		cancelar();
	});
	
	
	
	dialogoConfirmar = $( "#dialog-confirm" ).dialog({
		resizable: false,
		height:160,
		modal: true,
		autoOpen: false,
		buttons: {
			"ACEPTAR": function() {
				parent.WizardRegistroDerechohabienteCtrl.cerrar();
		 	}
		 }
	 });
});

function iniciarTramiteRegistro() {
	var parentescoARegistrar = $("#parentescoARegistrar").val();
	//cuando no es el pensionado checamos que parentesco esta seleccionado
	if(parentescoARegistrar == 0) {
		parentescoARegistrar = $("#selectable li")[index].id;
	}
	$('#registroBeneficiarios').attr("action", "/portalDerechohabiente-web/wizard/registro/capturaDatosPersonales/"+curp+"/"+parentescoARegistrar);
	$('#registroBeneficiarios').submit();	
}

function iniciarTramite() {
	
	if(index != -1) {
		var parentescoARegistrar = $("#selectable li")[index].id;
		$("#parentesco\\.idParentesco").val(parentescoARegistrar);
	}
	
	FORMULARIO_REGISTRO = $("#registroBeneficiarios");
	var oForm = FORMULARIO_REGISTRO.toObject();
	var url = '/portal-ciudadano-web-externo/wizard/registro/validacionesIniciales';
	fnHideErrores("form#registroBeneficiarios");
	
	$.blockUI();
	$.postJSON(url, oForm, function(data2) {
		FORMULARIO_REGISTRO.attr("action","/portal-ciudadano-web-externo/wizard/registro/capturaDatosPersonales");
		FORMULARIO_REGISTRO.submit();
	}).error(function(data){
		$.unblockUI();
		fnProcesarErrores(data, "form#registroBeneficiarios");
	});
}

/**
 * Metodo para mostrar mensaje de error en pantalla 
 * @param seleccionM - Bandera que indica si la pantalla es para seleccionar a un candidato o a varios
 */
function errorNoSeleccionado() {
	$noSeleccionado = $('<div></div');

	$noSeleccionado.dialog({
		autoOpen : false,
		resizable : false,
		title : 'Error',
		modal : true,
		buttons : {
			"Aceptar" : function() {
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	$noSeleccionado.html('Debe seleccionar el tipo de tr&aacute;mite a realizar');
	$noSeleccionado.dialog('open');
}
function retomar() {
	var url = '/portalDerechohabiente-web/wizard/registro/retomar';
	
	$('#solicitudForm').attr('action',url);
	$('#solicitudForm').submit();
}


function cancelarSolicitud() {
	var idSolicitudPendiente = $('#idSolicitudPendiente').val();
	var url = '/portal-ciudadano-web-externo/wizard/registro/solicitud/cancelar';
	
	$.blockUI();
	
	$.postJSON(url, {solicitudId : idSolicitudPendiente}, function(data) {
		$.unblockUI();
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog( "open" );
	}).error(function(data){
		$.unblockUI();
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog( "open" );
	}).done(function(data){
		$.unblockUI();
	});

}

function cancelar() {
	if($("#dialog-confirm-salir").length > 0) {
		dialogoConfirmarSalida.dialog("open");
	} else {
		parent.WizardRegistroDerechohabienteCtrl.cerrar();
	}
}

/**
 * Metodo para cerrar un dialogo
 * @param $dialogo - dialogo que se cerrara
 */
function cierraDialogo($dialogo){
	$dialogo.dialog('close');
	$dialogo.dialog('destroy');
	$dialogo.html('');
}