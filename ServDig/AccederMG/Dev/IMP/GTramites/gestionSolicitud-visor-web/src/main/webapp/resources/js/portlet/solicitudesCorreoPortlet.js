/**
 * Portlet de solicitudes de correo
 */

var solicitudesPortlet = {
	
};

$(document).ready(function(){
	
	// -----------------------------------------------
	// Carga las razones de rechazo para el tramite
	// -----------------------------------------------
	$('#rechazarSolicitud').click(function(){
		mostrarDialogoRazones();
	});

		

});

function mostrarDialogoRazones() {
	
	var idSolicitud = $("#idSolicitud").val();
	var idTramite = $("#idTramite").val();
	
	console.log('el id solicitud a mandar es: ', idSolicitud);
	console.log('el idTramite a mandar es: ', idTramite);
	
	$decision = $('<div></div');
	
	$decision.dialog({
		autoOpen : false,
	    resizable: true,  
	    height: 'auto',  
	    width: 'auto',
		title: 'Ingrese el motivo del rechazo',
		modal: true,
        buttons: [
                  {
                      text: "Si",
                      click: function() {
                          // Acción a realizar cuando se hace clic en el botón "Si"
                          var motivoRechazo = $("#motivoRechazo").val();  
                          if (motivoRechazo.trim() !== "") {
                              abrirDocumentoResultante(idSolicitud, idTramite, 'RECHAZAR', motivoRechazo);
                              cierraDialogo($(this));  
                          } else {
                              alert("Por favor, ingrese un motivo de rechazo antes de continuar.");
                          }
                      }
                  },
                  {
                      text: "No",
                      click: function() {
                          // Acción a realizar cuando se hace clic en el botón "No"
                          cierraDialogo($(this));  // Llama a la función cierraDialogo y le pasa el diálogo actual como argumento
                      }
                  }
              ]
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	$decision.html('<label for="motivoRechazo">Motivo de rechazo:</label><input type="text" id="motivoRechazo" style="width:100%; height:100px;">');
	
	$decision.dialog('open');
}
		

function cierraDialogo($dialogo){
	$dialogo.dialog('close');
	$dialogo.dialog('destroy');
	$dialogo.html('');
}

function abrirDocumentoResultante(idSolicitud, idTramite, accion, motivo) {
	$('form#solicitudDocumentoForm input#idSolicitud').val(idSolicitud);
	$('form#solicitudDocumentoForm input#idTramite').val(idTramite);
	$('form#solicitudDocumentoForm input#accion').val(accion);
	$('form#solicitudDocumentoForm input#motivo').val(motivo);
	$('form#solicitudDocumentoForm').submit();
}

function mostrarErrorCaptura(msg) {
	
	$('#msgError').text(msg);
	$('#divMsgErrores').show();
	$('#msgError').show();
	window.location.hash = '#divMsgErrores';
}

function ocultarErrorCaptura() {
	fnHideErrores('form#formBusquedaSolictiudes');
	$('#msgError').text('');
	$('#divMsgErrores').hide();
}

