function getVistaCuestionario(idTramite){
	
	var url = context_path + "/cuestionario/corroborarResultado";
	var	datos = {
		'idTramite' : idTramite
	};
	
	$div=$('<div></div>');
	
	$div.dialog(getOpciones($div)).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	
	$div.load(url,datos,function(){
		var existeDiv =$('#fsBeneficiario');
		if(existeDiv.length>0){
			$(this).dialog(
					getOpcionesGuardar($(this))
					);
		}
		
	});
	$div.dialog('open');
}

function getOpciones($dialogo){
	var opciones={
		autoOpen : false,
		resizable: false,
		position: 'center',
		width: 600,
		height: 200,
		title: 'Corroborar resultado de cuestionario',
		modal: true,
		buttons: {
			"Aceptar" : function() {
				$dialogo.dialog('close');
				$dialogo.dialog('destroy');
				$dialogo.html('');
			}
		}
	};
	
	return opciones;
}

function getOpcionesGuardar($dialogo){
	var opciones={
			autoOpen : false,
			title: 'Corroborar resultado cuestionario',
			show: "blind",
			hide: "explode",
			modal: true,
			height: 180,
			width: 400,
			buttons: {
				"Aceptar": function() {
					var urlGuardar= context_path + "/cuestionario/actualizarRegistro";
					var	registroDerechohabiente = {
							'tramiteId' : $("#cveIdTramite").val(),
							'resultado':true,
							'fisica':{
								'idPersona':$("#cveIdPersona").val()
							}
							
						};
					//Peticion json
					$.postJSON(urlGuardar, registroDerechohabiente, function(result) {
						$div.dialog('close');
						mensageConfirmacion("El registro del derechohabiente ha sido actualizado.");						
						$('#registrar').show();
						$('#capturado').val("1");
						$('#resultado').hide();
					});
				},
				"Rechazar": function() {
					var urlGuardar= context_path + "/cuestionario/actualizarRegistro";
					var	registroDerechohabiente = {
							'tramiteId' : $("#cveIdTramite").val(),
							'resultado' : false,
							'fisica':{
								'idPersona':$("#cveIdPersona").val()
							}
							
						};
					//Peticion json
					$.postJSON(urlGuardar, registroDerechohabiente, function(result) {
						$div.dialog('close');
						mensageConfirmacion("Confimación  de resultado Guardado");						
						$('#registrar').hide();
						$('#capturado').val("2");
						$('#resultado').hide();
					});
				},
				"Cancelar": function() {
					$('#registrar').show();
					$(this).dialog('close');
					$(this).dialog('destroy');
				}		
			}
		};
	
	return opciones;
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


