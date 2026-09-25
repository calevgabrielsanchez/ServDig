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

			var data = $("input:radio[name=candidato]:checked").val();



			if(data !== undefined) {
				const values = data.split("-");
				var derechohabiente = values[0];
				var nombre =  values[1];
				var curp =  values[2];
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
						    console.log("derechohabiente: " + derechohabiente);
							bajaDerechohabienteDefuncion(derechohabiente, nombre, curp);
						}
					}	
				);
			}
			else
				errorNoSeleccionado();
		});
	}
);
