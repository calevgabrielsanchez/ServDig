/**
 * JS para el soporte del catalogo de clase.
 */


var idDataTable 	= "#dtFoliosPromocion";
var idDataTablaAPatrones = "#dtAgregaPatrones";
var idDataTrabajadoresA = "#dtAgregaTrabajadoresA";
var idDataTrabajadoresR = "#dtAgregaTrabajadoresR";
var idDataConceptos = "#dtAgregaConceptos";
var idDataConceptosC = "#dtAgregaConceptosC";
var idDataPagos = "#dtAnexoPagos";
var idDataPagosR = "#dtAnexoPagosR";
var idDgNuevo 		= "#dgCorreccionNuevo";
var idDgModificar 	= "#dgCorreccionModificar";
var idDgBorrar 		= "#dgCorreccionBorrar";
var idDgAyuda		= "#dgCorreccionAyuda";
var idDgAnexoPagos 	= "#dgCorreccionAnexoPagos";
var idDgAnexoPagosR	= "#dgCorreccionAnexoPagosR";
var idDgBuscar		= "#dgCorreccionBuscar";
var idDgFoliosPromocion = "#dgFoliosPromocion";
var idDgAgregaPatrones = "#dgAgregaPatrones";
var idDgAgregaTrabajadoresA = "#dgAgregaTrabajadoresA";
var idDgAgregaTrabajadoresR = "#dgAgregaTrabajadoresR";
var idDgAgregaConceptos = "#dgAgregaConceptos";	
var idDgAgregaConceptosC = "#dgAgregaConceptosC";
var idDgAgregaPagos = "#dgAnexoPagos";
var idDgAgregaPagosR = "#dgAnexoPagosR";
var idFolio = "#txtFolio"; 
var idBuscaTable    = "#dtBuscarCorrecciones";
var oDtBuscaCorrreccion;

// Objeto del DataTable
var dtFoliosPromocion;
var dtAgregaPatrones;
var dtAgregaTrabajadoresA;
var dtAgregaTrabajadoresR;
var dtAgregaPagos;
var dtAgregaPagosR;
var dtAgregaConceptos;
var dtAgregaConceptosC;
// Dialogos
var oDgNuevo;
var oDgBorrar;
var oDgModificar;
var oDgAyuda;
var oDtBuscaCorreccion;
var oDgAnexoPagos;
var oDgAnexoPagosR;
var oDgBuscar;
var oDgFoliosPromocion;
var oDgAgregaPatrones;
var oDgAgregaTrabajadoresA;
var oDgAgregaTrabajadoresR;
var oDgAgregaConceptos;
var oDgAgregaConceptosC;
var oDgAgregaPagos;
var oDgAgregaPagosR;
var fnGuardarCorreccion;
var fnGeneraFolioFinal;
var oTxtFolio;

var idDgBuscar		= "#dgCorreccionBuscar";
var oDgBuscar;

/**
 * Iniciamos la configuracion de los componentes visuales de jQuery.
 */



$(document).ready(function() {
	
	
});//$(document).ready(function()




function buscaAvisoNotaria(){
	var aviso = $('form#formNotaria input#txtAviso').val(); 
	
	
	  //alert (reglaNegocio.id.nombrecontrol);
	  $.postJSON("notaria/consultaAviso.do", aviso, function(data) {
		  window.open('http://11.254.13.248:7001/dictamenview-web/show/notaria.do?pdf=si');
		}).error(function(data){ 
			alert('1');
		}).complete(function(){
			
		});
	  
}
