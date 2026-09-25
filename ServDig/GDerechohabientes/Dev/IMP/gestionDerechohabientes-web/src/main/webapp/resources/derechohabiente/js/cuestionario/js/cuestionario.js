 function getCuestionario(idTramite) {
	$cuestionario = $('<div></div');
	
	//petiocion json
	var url = context_path + "/cuestionario/pregunta";
	var	solicitud = {
		'idTramite' :idTramite
	};
	
	$cuestionario.dialog({
		autoOpen : false,
		title: 'Registro de la calificaci\u00F3n del cuestionario',
		show: "blind",	
		hide: "explode",
		modal: true,
		height: 200,
		width: 500,
		buttons: {
			"Guardar": function(){
				var urlGuardar= context_path + "/cuestionario/actualizar";
				var	registroDerechohabiente = {
						'tramiteId' : $("#idTramite").val(),
						'evaluacionCuestionario':$('#evaluacionCuestionario').val()
					};
				var valida=$('#frmRegistro').valid();
				//Peticion json
				if(valida){
					$.postJSON(urlGuardar, registroDerechohabiente, function(result) {
						cerrarVentana($cuestionario);
						mensageConfirmacion("La calificaci\u00F3n del cuestionario ha sido actualizado.");
						$('#capturar').hide();
					});
				}
			},
			"Cancelar": function() {
				cerrarVentana($(this));
			}
		}
	
		}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	$cuestionario.load(url,solicitud);
	$cuestionario.dialog('open');
}

function getPDF(idTramite){
	var direccion=context_path +'/documentos/cuestionarioCD?idTramite='+idTramite;
	var page=context_path + "/resources/js/delta/viewPdf.html";
	
	window.showModalDialog(page,direccion,"resizable:1;dialogHeight:550px;dialogwidth:985px;scroll:yes;status=no");
}

function mensageConfirmacion(mensaje){
	$ventana = $('<div></div');
	
	$ventana.append(mensaje);	
	$ventana.dialog({
		autoOpen : false,
		title: 'Mensaje',
		show: "blind",
		hide: "explode",
		modal: true,
		height: 200,
		width: 500,
		buttons: {
			"Aceptar": function() {
				cerrarVentana($(this));
			}
		}	
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	
	$ventana.dialog('open');
}


function cerrarVentana($dialog){
	$dialog.dialog('close');
	$dialog.dialog('destroy');
	$dialog.html('');
}
