//$(document).ready(
//		function() {
//			var index;
//			for (index = 0; index < 2; index++) {
//				var tramValDatos = '#tramitadorValidaDatos' + index;
//
//				$(tramValDatos).click(
//						function() {
//							var tramiteRazonResultado = '#tramite' + index
//									+ 'idRazonResultado';
//							$(tramiteRazonResultado).fadeTo('slow', 0.5);
//							$(tramiteRazonResultado).attr('disabled',
//									'disabled');
//						});
//				var tramRechDatos = '#tramitadorRechazaDatos' + index;
//				$(tramRechDatos).click(
//						function() {
//							var tramiteRazonResultado = '#tramite' + index
//									+ 'idRazonResultado';
//							$(tramiteRazonResultado).fadeTo('slow', 1.0);
//							$(tramiteRazonResultado).removeAttr('disabled');
//						});
//
//			}
//
//		});

function deshabilitarComboRazonRechazo(index) {
	var tramiteRazonResultado = '#tramite' + index + 'idRazonResultado';
	$(tramiteRazonResultado).fadeTo('slow', 0.5);
	$(tramiteRazonResultado).attr('disabled', 'disabled');
}

function habilitarComboRazonRechazo(index) {
	var tramiteRazonResultado = '#tramite' + index + 'idRazonResultado';
	$(tramiteRazonResultado).fadeTo('slow', 1.0);
	$(tramiteRazonResultado).removeAttr('disabled');
}
