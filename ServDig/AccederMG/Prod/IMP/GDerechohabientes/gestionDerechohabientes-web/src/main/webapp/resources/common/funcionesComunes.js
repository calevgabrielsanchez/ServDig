/**
 * Metodo que setea el texto seleccionado de un combo a un campo
 * @param idCombo - El id del select
 * @param idDescripcion -  el id del elemento que contendra la descripcion
 */
function setDescripcionCombo(idCombo,idDescripcion) {
	var texto = $("select#"+idCombo+" option:selected").html();
	$("#"+idDescripcion).val(texto);
}

/**
 * metodo para calcular la edad
 * @param fechaNac string con formato dd/mm/yyyy
 * @returns
 */
function calcularEdad(fechaNacimiento) {
	
	var edad = 0;
	
	if(fechaNacimiento != null && fechaNacimiento != "") {
		
		var fecha = fechaNacimiento.toString().split("/");
	    var dia = fecha[0];
	    var mes = fecha[1];
	    var anio = fecha[2];
	    
	    // cogemos los valores actuales
	    var fecha_hoy = new Date();
	    var ahora_anio = fecha_hoy.getYear();
	    var ahora_mes = fecha_hoy.getMonth()+1;
	    var ahora_dia = fecha_hoy.getDate();
	    
	    // realizamos el calculo
	   	edad = (ahora_anio + 1900) - anio;
	    if ( ahora_mes < mes )
	    {
	        edad--;
	    }
	    if ((mes == ahora_mes) && (ahora_dia < dia))
	    {
	        edad--;
	    }
	    if (edad > 1900)
	    {
	        edad -= 1900;
	    }
	
	    // calculamos los meses
	    var meses=0;
	    if(ahora_mes>mes)
	        meses=ahora_mes-mes;
	    if(ahora_mes<mes)
	        meses=12-(mes-ahora_mes);
	}
    
	return edad;
}

/**
 * Metodo que obtiene la diferencia entre fechas
 * @param date1
 * @param date2
 * @returns
 */
function datediff(date1, date2) {

	var y1 = date1.getFullYear(), m1 = date1.getMonth(), d1 = date1.getDate(),
	y2 = date2.getFullYear(), m2 = date2.getMonth(), d2 = date2.getDate();

	//console.debug("Fechas 1: %s,%s,%d,%s,%s,%s",y1,m1,d1,y2,m2,d2);

	if (d1 < d2) {
		m1--;
		d1 += DaysInMonth(y2, m2);
	}
	if (m1 < m2) {
		y1--;
		m1 += 12;
	}

	return [y1 - y2, m1 - m2, d1 - d2];
}


function DaysInMonth(Y, M) {
	with (new Date(Y, M, 1, 12)) {
		setDate(0);
		return getDate();
	}
}