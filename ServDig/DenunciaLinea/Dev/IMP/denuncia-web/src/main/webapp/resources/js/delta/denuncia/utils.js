// Al entrar al sistema
// sacar a fecha del sistema
// validar día habil / no festivo
// si día inhábil o festivo -> mostrar alerta de que no se puede generar una denuncia, ni ratificar.
       //mandar alerta / mensaje en pantallas.
	   //mostrar la información con los campos deshabilitados.


24-07-2012


function jsValidaDiaHabil(fecha){
	var evalFecha = $(fecha).val();
	if(evalFecha != ''){
	   var elemFecha = evalFecha.split("/");                                       //que el día no sea 0 ni 6, sábado ni domingo.
	   var hFecha = new Date(elemFecha[2],elemFecha[1]-1, elemFecha[0]);
	   var diaFecha = hFecha.getDay();
	   alert(diaFecha);
	}
    //return true;-h
	//return false;-nh
}

function jsValidaDiaFestivo(){
    var fechaIncial = $("#fechaIncial").val();  //obtener día, obtener, año.
												//validar día dentro del arreglo
	
}



function jsCalculaVigenciaDenuncia(fechaRegistroDenuncia){ //sumar 10 días a partir del fecha de generación, quitando días inhábiles, ni festivos.
     //obtener fecha de registro
	 //{sigDia = sumar uno a la fecha de registro,   
	   //sigDia es día hábil o festivo ¿?
	    // si -> sigDia = sumar uno a la fecha de registro
	 // aumentar inc,
	 //}
	 // inc es 10 ¿?
}

utils.js - Validaciones de fecha, formatos de cadenas, correos.
denunciaLinea.js - Generales / Utilerias
datosTrabajador.js - Validaciones al registro de datos de 
datosPatron.js
datosTrabajo.js