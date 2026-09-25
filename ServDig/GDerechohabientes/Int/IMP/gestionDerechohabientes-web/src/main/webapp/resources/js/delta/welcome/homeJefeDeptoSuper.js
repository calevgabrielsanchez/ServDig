

function limpiarDatos(){
	$("#detailsSection").prop("style", "display: none");
	document.getElementById("normativoCEForm").reset();
}

function validaDatosPerfil(){
	var comboTramite = document.getElementById("tramite");
	var fechaInicio = document.getElementById("fechaInicio");
	var fechaFin = document.getElementById("fechaFin");
	var folioReporte = $("#folioReporte").val();
	var perfilOK = true;
	var formPerfil= document.getElementById("normativoCEForm");

	if(comboTramite.value == 0){
		alert("Es necesario seleccionar un Tipo de Tramite");
		perfilOK=false;
		$.unblockUI();
		return;
	}
	
	if (folioReporte == "") {
		if(fechaInicio.value == ""){
			alert("Es necesario seleccionar una Fecha de Inicio");
			perfilOK=false;
			$.unblockUI();
			return;
		} else {
			if(!validateDate(fechaInicio.value)){
				alert("La fecha de inicio es incorrecta.");
				perfilOK=false;
				$.unblockUI();
	 			return;
			}
		}

		if(fechaFin.value == ""){
			alert("Es necesario seleccionar una Fecha de Fin");
			perfilOK=false;
			$.unblockUI();
			return;
		} else {
			if(!validateDate(fechaFin.value)){
				alert("La fecha de fin es incorrecta.");
				perfilOK=false;
				$.unblockUI();
	 			return;
			}
		}

		var x = fechaInicio.value.split("/");
	    var z = fechaFin.value.split("/");
		var inicio = new Date(x[1] + "/" + x[0] + "/" + x[2]);
		var fin = new Date(z[1] + "/" + z[0] + "/" + z[2]);

		if(inicio > fin){
			alert("La fecha de inicio no puede ser mayor a la fecha de fin.");
			perfilOK=false;
			$.unblockUI();
			 return;
		}

		var fechaInicioDate = new Date(fechaInicio.value.split("/").reverse().join("/"));
		var fechaFinDate = new Date(fechaFin.value.split("/").reverse().join("/"));
		if(comboTramite.value == 1 && esMayorAUnMes(fechaInicioDate, fechaFinDate)){
            alert("Solo se puede realizar la consulta del reporte hasta un mes como m\u00e1ximo");
            perfilOK=false;
            $.unblockUI();
            return;
        }
	} else {
		var pattern = /^[a-zA-Z0-9]+$/;
		if (!pattern.test(folioReporte)) {
			alert("El formato de folio no es v\u00e1lido.");
			perfilOK = false;
			$.unblockUI();
			return;
		}
 	}

	if (perfilOK){
		formPerfil.submit();
	}
	
}

const DATE_REGEX = /^(0[1-9]|[1-2]\d|3[01])(\/)(0[1-9]|1[012])\2(\d{4})$/
const CURRENT_YEAR = new Date().getFullYear()

const validateDate = (birthDate) => {
    
  /* Comprobar formato dd/mm/yyyy, que el no sea mayor de 12 y los días mayores de 31 */
  if (!birthDate.match(DATE_REGEX)) {
    return false
  }
  
  /* Comprobar los días del mes */
  const day = parseInt(birthDate.split('/')[0])
  const month = parseInt(birthDate.split('/')[1])
  const year = parseInt(birthDate.split('/')[2])
  const monthDays = new Date(year, month, 0).getDate()
  if (day > monthDays) {
    return false
  }
  
  /* Comprobar que el año no sea superior al actual*/
  if (year > CURRENT_YEAR) {
    return false
  }
  return true

}

$(document).ready(function(){
	$('#cancelar').click(function(){
		limpiarDatos();
	});
	
	$('#aceptarVal').click(function(){
		$.blockUI();
		validaDatosPerfil();
	});
	
});

function esMayorAUnMes(fechaI, fechaF) {
    var difM = fechaF - fechaI;
    var difD = difM / (1000 * 60 * 60 * 24);
    if (difD > 30) {
        return true;
    } else {
        return false;
    }
}

