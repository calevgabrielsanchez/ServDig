/**
 * Mario Teran Blanco
 * IMSS (Instituto Mexicano del Seguro Social)
 * 16/04/2012
 */

$(document).ready(
	function() {
		
		if($("#candidato").val() == undefined) {
			$('#aceptar').hide();
		}
		
		$('#aceptar').click(function() {
			
			var derechohabiente = $("input:radio[name=candidato]:checked").val();
			
			if(derechohabiente != undefined || derechohabiente != null) {
				//var url = context_path + "/tramite/tramitePosible";
				var url = context_path + "/tramite/tramiteAbierto";
				var parametros = {
						//'persona' : {
							'idPersona' : derechohabiente
/*						} ,
						'tipoTramite' : {
							'idTipoTramite' : 25
						}*/
				}
				$.postJSON( url, parametros, 
					function(result) {
						/*if(result.modelo == 0) {
							errorTramite();*/
						if(result.modelo != null) {
							errorTramite(result.modelo.tipoTramite.descripcion);
						} else {
							bajaDerechohabienteUnionCivil(derechohabiente);
						}
					}	
				);
			}
			else
				errorNoSeleccionado();
		});
	}
);
