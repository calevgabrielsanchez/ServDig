/**
 * indica el tab seleccionado
 */
activeTab = '#seguimiento';

/**
 * Indica la forma seleccionada
 */
FORMA_ACTUAL ='seguimientoForm';

/**Variables indicativas de las secciones
 *disponibles. 
 */
var SEGUIMIENTO=1;
var DERIVA_SUBDELEGACION=2;
var REASIGNA_REVISOR=3;
var CEDULA_REVISION=4;
var REQ_DOCUMENTACION=5;
var CEDULA_VALIDACION=6;
var OF_DIFERENCIAS=7;
var DERIVA_PAI=8;
var DERIVA_DICTAMEN=9;
var CANCELACION=10;
var CONCLUSION=11;


/**
 * Controla el valor del hidden el cual indica
 * en que sección se encuentra el usuario.
 * Esta variable es utilizada por JAVA.
 */
function setToFormSeccionActual(){

	var seccionPeticion;
	
	if(FORMA_ACTUAL=='seguimientoForm'){
		seccionPeticion = SEGUIMIENTO;
	}else if(FORMA_ACTUAL=='devSubDelegacionForm'){
		seccionPeticion = REASIGNA_REVISOR;
	}else if(FORMA_ACTUAL=='reasignarRevisorForm'){
		seccionPeticion = REASIGNA_REVISOR;
	}else if(FORMA_ACTUAL=='cedulaRevisionForm'){
		seccionPeticion = CEDULA_REVISION;
	}else if(FORMA_ACTUAL=='reqDocumentacionForm'){
		seccionPeticion = REQ_DOCUMENTACION;
	}else if(FORMA_ACTUAL=='cedulaValidacionForm'){
		seccionPeticion = CEDULA_VALIDACION;
	}else if(FORMA_ACTUAL=='ofDiferenciasForm'){
		seccionPeticion = OF_DIFERENCIAS;
	}else if(FORMA_ACTUAL=='derivacionFiscalizacionForm'){
		seccionPeticion = DERIVA_PAI;
	}else if(FORMA_ACTUAL=='derivacionDictamenForm'){
		seccionPeticion = DERIVA_DICTAMEN;
	}else if(FORMA_ACTUAL=='cancelacionForm'){
		seccionPeticion = CANCELACION;
	}else if(FORMA_ACTUAL=='conclusionForm'){
		seccionPeticion = CONCLUSION;
	}
	
	try{
		$("form#"+FORMA_ACTUAL +" #seccionPeticion").val(seccionPeticion);
	}catch(error){
		alert("El campo seccionPeticion no se encuentra disponible dentro de la forma "+FORMA_ACTUAL);
	}
	
}
