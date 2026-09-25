var personasEncontradas = null;

$(document).ready(function() {
	//console.debug("se iniciara la consulta por curp");
	//console.debug("la curp que se buscara es la siguiente: %s", $("#curpBusqueda").val());
	
	$.blockUI();
	$.ajax({
		async: true,
    	url : context_path + "/derechohabiente/buscarPersona/resultados",
        type: 'post',
        data : {
			'curp' : $("#curpBusqueda").val()
		},
        dataType: 'json',
        success: function (result) {
        	if(!result.estado) {
    			mostrarError(result.mensaje);
    			
    		} else {
    			personasEncontradas = result.modelo;
    			pintarTabla(personasEncontradas);
    		}
        	
        	$.unblockUI();
        }
    });
	
});

function mostrarError(mensaje) {
	$("#mensajeError").html(mensaje);
	$("#errores").show();
	$("#resultados").hide();
}

function pintarTabla(personas) {
	$("#mensajeError").html("");
	$("#errores").hide();
	$("#resultados").show();
	
	var tabla = $("#personasFisicasFoundIMSSTable");
	
	for(var i=0; i< personas.length;i++) {
		
		var fila = "<tr>";
		fila += "<td>" + personas[i].idPersona == null ? 'RENAPO': personas[i].idPersona + "</td>";
		fila += "<td>" + getTexto(personas[i].rfc)+ "</td>";
		fila += "<td>" + getTexto(personas[i].curp)+ "</td>";
		fila += "<td>" + getTexto(personas[i].nss) + "</td>";
		fila += "<td>" + getTexto(personas[i].nombre) + "</td>";
		fila += "<td>" + getTexto(personas[i].primerApellido) + "</td>";
		fila += "<td>" + getTexto(personas[i].segundoApellido) + "</td>";
		fila += "<td>" + getTexto(personas[i].sexo.descripcion) + "</td>";
		fila += "<td>" + getTexto(personas[i].fechaNacimientoFormateada) + "</td>";
		fila += "<td>" + getTexto(personas[i].anioRegistroNac) + " / " + getTexto(personas.mesRegistroNac) + "</td>";
		fila += "<td>" + getTexto(personas[i].lugarNacimiento.nombre) + "</td>";
		fila += "<td>" + getTexto(personas[i].subEstadosFormateados) + "</td>";
		fila += "<td> <button type='button' class='btn btn-inverse' onclick='seleccionarPersona("+i+")'> Seleccionar </button></td>";
		fila += "</tr>";
		//Agregamos la fila al tbody
		$(tabla).find('tbody').append(fila);
	}
}

function getTexto(propiedad) {
	return propiedad == null ? '': propiedad;
}

function seleccionarPersona(indice) {
	
	parent.BusquedaPersonaIntegranteCtrl.setPersona(personasEncontradas[indice]);
	parent.BusquedaPersonaIntegranteCtrl.cerrar();
}