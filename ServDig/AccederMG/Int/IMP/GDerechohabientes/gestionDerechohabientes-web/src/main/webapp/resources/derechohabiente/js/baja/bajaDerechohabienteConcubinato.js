/**
 * Mario Teran Blanco
 * IMSS (Instituto Mexicano del Seguro Social)
 * 16/04/2012
 */

$(document).ready(
	function() {
		
		$('#aceptar').click(function() {
			var derechohabiente = $("#idPersona").val();
			//var url = context_path + "/tramite/tramitePosible";
			var url = context_path + "/tramite/tramiteAbierto";
			var parametros = {
					/*'persona' : {*/
						'idPersona' : derechohabiente
					/*} ,
					'tipoTramite' : {
						'idTipoTramite' : 26
					}*/
			}
			$.postJSON( url, parametros, 
				function(result) {
					if(result.modelo != null) {
						errorTramite(result.modelo.tipoTramite.descripcion);
					} else {
						bajaDerechohabienteConcubinato(derechohabiente);
					}
				}	
			);
			
		});
		
		$("#cancelar").click(
			function() {
				cancelarBaja();
			}	
		);
	}
);

function errorTramite(tipoTramite) {
	var mensajeError = '<div class="ui-widget">' +
	'<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">'+
	'<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>' +
	'<strong>Esta persona ya cuenta con un tr&aacute;mite de ' + tipoTramite+', para realizar cualquier tr&aacute;mite finalizar el tr&aacute;mite abierto</strong></p></div></div>';

	$razonRechazo = $('<div></div');
	$razonRechazo.html(mensajeError);
	$razonRechazo.dialog({
		autoOpen : false,
		title: '',
		show: "blind",
		hide: "explode",
		resizable: false,
		modal: true,
		width: 500,
		buttons: {
			"Cerrar": function() {
				cierraDialogo($(this));
			}
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	$razonRechazo.dialog('open')
}

function cancelarBaja() {

	$decision = $('<div></div');

	$decision.dialog({
		autoOpen : false,
		resizable : false,
		height : 140,
		title : 'Selecciona una opci\u00F3n',
		modal : true,
		buttons : {
			"Si" : function() {
				location.href = "" + context_path + "/inicio/grupoFamiliar";
			},
			"No" : function() {
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.text('\u00BF Est\u00E1 seguro que desea salir del tr\u00E1mite?');
	$decision.dialog('open');
}

function mostrarConprobanteSolcitudInternet(idSolicitud,titulo){
	var direccion=context_path + "/documentos/comprobanteSolicitud?idSolicitud="+idSolicitud+"&titulo="+titulo;
	var page=context_path + "/resources/js/delta/viewPdf.html";
	window.showModalDialog(page,direccion,"resizable: yes,dialogwidth: 1000,dialogheight:1000,scroll:on");	
}



function parametrosRespuesta($dialogo){
	
	opciones = {
		autoOpen : false,
		resizable: false,
		height: 140,
		title: 'Resultado',
		modal: true,
		buttons: {
			"Aceptar" : function() {
				cierraDialogo($dialogo);
			}
		}
	};
	
	return opciones;
}

function parametrosEspera(){
	
	opciones = {
		autoOpen : false,
		resizable: false,
		height: 140,
		title: 'Esperando...',
		modal: true,
		buttons: {
			"" : function() {}
		}
	};
	
	return opciones;
}