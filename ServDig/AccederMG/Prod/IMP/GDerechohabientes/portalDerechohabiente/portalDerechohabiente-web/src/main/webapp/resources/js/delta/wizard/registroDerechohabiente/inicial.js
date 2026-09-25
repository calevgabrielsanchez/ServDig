var dialogoConfirmarCancelar,
dialogoConfirmar,
index=-1, idTipoParentesco = 5;

CONTEXT_PATH_APLICACION = '/${mvn.web.app.root}';

$(document).ready(function(){
	
	if($("#listaTramitesBeneficiarios").length == 0) {
		mostrarAvisoPrivacidad();
	}
	
	$("#selectable").selectable({
		selected : function(event, ui) {
			$(ui.selected).siblings().removeClass("ui-selected");
		},
		stop : function() {
			$(".ui-selected", this).each(function() {
				index = $("#selectable li").index(this);
				idTipoParentesco = $("#selectable li")[index].id;
				mostrarAvisoPrivacidad();
			});
		}
	});
	
	$('#btnInciaTramite').click(function(){
		iniciarTramite();
	});
	
	$('#btnRetomarTramite').click(function(){
		retomar();
	});
	
	$('#btnCancelarTramite').click(function(){
		dialogoConfirmarCancelar.dialog( "open" );
	});
	
	$('#btnInicioCancelarTramite').click(function(){
		cancelar();
	});
	
	dialogoConfirmarCancelar = $( "#dialog-confirm-cancelar" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false,
		buttons: {
			
		 	"Cancelar": function() {
		 		$( this ).dialog( "close" );
		 	},
		 	"Aceptar": function() {
				$( this ).dialog( "close" );
				cancelarSolicitud();
		 	}
		 }
	 });
	
	dialogoConfirmar = $( "#dialog-confirm" ).dialog({
		resizable: false,
		height:160,
		modal: true,
		autoOpen: false,
		buttons: {
			"Aceptar": function() {
				parent.WizardRegistroDerechohabienteCtrl.cerrar();
		 	}
		 }
	 });
});

function mostrarAvisoPrivacidad() {
	if(idTipoParentesco) {
		var $divAviso = $("#avisoPrivacidad");
		var $spanTipoTramite = $divAviso.find("#tituloTramite"),
		$spanHomoclave = $divAviso.find("#homoclaveTramite"),
		desTramite = "", desHomoclave = "";
		$divAviso.show();
		
		if( idTipoParentesco == 5 || idTipoParentesco == 6) {
			desTramite = "Registro del asegurado (a) o pensionado(a)";
			desHomoclave = "IMSS-02-066-M";
		} else if(idTipoParentesco == 4) {
			desTramite = "Registro de concubina (rio)";
			desHomoclave = "IMSS-02-066-D";
		} else if(idTipoParentesco == 3) {
			desTramite = "Registro de esposa (o)";
			desHomoclave = "IMSS-02-066-A";
		} else if(idTipoParentesco == 2) {
			desTramite = "Registro de hijo (a)";
			desHomoclave = "IMSS-02-066-J";
		} else if(idTipoParentesco == 1) {
			desTramite = "Registro de padre y/o madre";
			desHomoclave = "IMSS-02-066-G";
		}
		
		$spanTipoTramite.html(desTramite);
		$spanHomoclave.html(desHomoclave)
	}
}

function iniciarTramiteRegistro() {
	var parentescoARegistrar = $("#parentescoARegistrar").val();
	//cuando no es el pensionado checamos que parentesco esta seleccionado
	if(parentescoARegistrar == 0) {
		parentescoARegistrar = $("#selectable li")[index].id;
	}
	$('#formIniciaTramite').attr("action", CONTEXT_PATH_APLICACION + "/wizard/registro/iniciarTramite/"+parentescoARegistrar);
	$('#formIniciaTramite').submit();	
}

function iniciarTramite() {
	
	var parentescoARegistrar = $("#parentescoARegistrar").val();
	
	if(parentescoARegistrar == 0) {
		
		if(index != -1) {
			iniciarTramiteRegistro();
		}
		else 
			errorNoSeleccionado();
	} else {
		iniciarTramiteRegistro();
	}
}

function setParentescoARegistrar(radio) {
	var parentesco = radio.value;
	//console.debug("El parentesco seleccionado es : %s",parentesco);
	$("#parentescoARegistrar").val(parentesco);
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
	var url = CONTEXT_PATH_APLICACION + '/wizard/registro/retomar';
	
	$('#solicitudForm').attr('action',url);
	$('#solicitudForm').submit();
}


function cancelarSolicitud() {
	var idSolicitudPendiente = $('#idSolicitudPendiente').val();
	var url = CONTEXT_PATH_APLICACION + '/wizard/registro/solicitud/cancelar';
	
	$.blockUI();
	
	$.postJSON(url, {solicitudId : idSolicitudPendiente}, function(data) {
		$.blockUI();
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog( "open" );
	}).error(function(data){
		$.blockUI();
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog( "open" );
	}).done(function(data){
		$.unblockUI();
	});

}

function cancelar() {
	parent.WizardRegistroDerechohabienteCtrl.cerrar();
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