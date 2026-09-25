/**
 * JS para el soporte del catalogo de clase.
 */


var idDataTable 	= "#dtClase";
var idDgNuevo 		= "#dgClaseNuevo";
var idDgModificar 	= "#dgClaseModificar";
var idDgBorrar 		= "#dgClaseBorrar";
var idDgAyuda		= "#dgClaseAyuda";

// Objeto del DataTable
var oDtClase;
// Dialogos
var oDgNuevo;
var oDgBorrar;
var oDgModificar;
var oDgAyuda;

/**
 * Iniciamos la configuracion de los componentes visuales de jQuery.
 */
$(document).ready(function() {

	 // Fecha de Inicio y Termino
	 $( "#fecIni, #fecFin " ).datepicker( { dateFormat: 'yy-mm-dd' });

	/**
	 * Inicializacion del data table
	 */
	oDtClase = $(idDataTable).dataTable({
		bJQueryUI : true,
		bFilter : false,
		bInfo:true,
		bSort: false,
		"bPaginate": true,
		"bAutoWidth" : false,
		"bServerSide" :	true,
		"aoColumns" : [ {
			fnRender :function(oObj){
				var retVal = '<input type="radio" value="' +
				oObj.aData['cveIdClase'] +'" id="radioTable" class="radioClase" name="radio" onclick=""/> ';
				return retVal;
			}, 
			aTargets: [0]
			},
		        {
				"sTitle" : "Clave Clase",
				"mDataProp" : "cveIdClase",
				"sClass": "dtCenterClassColumn"
			}, {
				"sTitle" : "Descripci&oacute;n",
				"mDataProp" : "desClase",
				"sClass": "dtCenterClassColumn"
			}, {
				"sTitle" : "Fecha Inicio",
				"mDataProp" : "fecIni",
				"sClass":"dtJustifyClassColumn"
			}, {
				"sTitle" : "Fecha Fin",
				"mDataProp" : "fecFin",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Prima Media",
				"mDataProp" : "indPrimaMedia",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Grado de Riesgo",
				"mDataProp" : "numGradoRiesgo",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Porcentaje",
				"mDataProp" : "numPorcentaje",
				"sClass": "dtCenterClassColumn"
			}
			],"bProcessing" : true,
			"sAjaxSource" : 'clase/paginar.do',
			"fnServerData" : function(sSource, aoData, fnCallback) {
				aoData.push({
					"name" : "sSearch",
					"value" : $('#desClase').val()
				});
				
				var wrapper = new Object();
				wrapper.aoData = aoData;

//				var oForm = $("#claseFiltrosForm").serializeObject(true);		
				var oForm = $("#claseFiltrosForm").toObject({mode:'first'});
				wrapper.oForm = oForm;
				
				$.postJSON(sSource, wrapper, function(data) {
					fnCallback(data);
				});
			}
		});
	
	
	// Dialog de Elemento Nuevo			
	 oDgNuevo = $(idDgNuevo).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 600,
		beforeClose :function(event,ui){
		    limpiarFormulario("#claseForm");
		},
		buttons: {
			"Aceptar": function() { 
//				var clase = $("#claseForm").serializeObject(true);				
				var clase = $("#claseForm").toObject({mode:'first'});
				$.postJSON("clase/agregar.do", clase, function(data) {
					alert("La clase ha sido agregada...");
					inicializaPosicionPaginador();						
				}).error(function(data){ 
					alert("error" + data);
				}).complete(function(){
				//Código para el complete
				});
				$(this).dialog("close"); 
			}, 
			"Cancelar": function() { 
				$(this).dialog("close"); 
			} 
		}
	});
	
	
	// Dialog de Elemento a Modificar			
	 oDgModificar = $(idDgModificar).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 600,
		beforeClose :function(event,ui){
		    limpiarFormulario("#claseFormModificar");
		},		
		buttons: {
			"Aceptar": function() { 
//				var clase = $("#claseFormModificar").serializeObject(true);
				var clase = $("#claseFormModificar").toObject({mode:'first'});
				$.postJSON("clase/modificar.do", clase, function(data) {		
					alert("La clase ha sido modificada...");
					inicializaPosicionPaginador();							
				}).error(function(data){ 
					alert("error" + data);
				}).complete(function(){
				//Código para el complete			
				});
				$(this).dialog("close"); 
			}, 
			"Cancelar": function() { 
				$(this).dialog("close"); 
			} 
		}
	});
	

	// Dialog de Elemento a Borrar			
	 oDgBorrar = $(idDgBorrar).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		height: 140,
		buttons: {
			"Aceptar": function() { 
//				var clase = $("#claseFormBorrar").serializeObject(true);
				var clase = $("#claseFormBorrar").toObject({mode:'first'});
				
				$.postJSON("clase/eliminar.do", clase, function(data) {
					alert("La clase ha sido eliminada...");		
					inicializaPosicionPaginador();							
				}).error(function(data){ 
					alert("error" + data);
				}).complete(function(){
					//Instrucciones para el complete			
				});
				$(this).dialog("close"); 
			}, 
			"Cancelar": function() { 
				$(this).dialog("close"); 
			} 
		}
	});
	 
	 
	// Dialog de Elemento Ayuda		
	oDgAyuda = $(idDgAyuda).dialog({
		modal:		true,
		buttons: {
			"Aceptar": function() {
				$(this).dialog("close"); 
			}
		}
	});	 	
	 
});//$(document).ready(function()

//Inicializa el paginador
function inicializaPosicionPaginador(){
	oDtClase.fnDisplayStart(0);
}


function nuevo(){
	oDgNuevo.dialog('open');
}

function paginar(){
	oDtClase.fnDraw();
}

function modificar(){
	var idClase = $('#:checked').val();
	var sClase = '{"cveIdClase":'+idClase+'}';
	var clase = jQuery.parseJSON(sClase);
	// Buscamos el elemento
	$.postJSON("clase/consultaPorClave.do", clase, function(data) {
		$('#wrapperDialogModif,#claseFormModificar,#cveIdClase').val(data.cveIdClase);
		$('#wrapperDialogModif,#claseFormModificar,#desClase').val(data.desClase);
		$('#wrapperDialogModif,#claseFormModificar,#fecIni').val(data.fecIni);
		$('#wrapperDialogModif,#claseFormModificar,#fecFin').val(data.fecFin);
		$('#wrapperDialogModif,#claseFormModificar,#indPrimaMedia').val(data.indPrimaMedia);
		$('#wrapperDialogModif,#claseFormModificar,#numGradoRiesgo').val(data.numGradoRiesgo);
		$('#wrapperDialogModif,#claseFormModificar,#numPorcentaje').val(data.numPorcentaje);
		oDgModificar.dialog('open');
	}).error(function(data){ 
		alert("error" + data);
	}).complete(function(){
		//Instrucciones para el 'complete'
	});
}

function borrar(){
	var idClase = $('#:checked').val();
	var sClase = '{"cveIdClase":'+idClase+'}';
	var clase = jQuery.parseJSON(sClase);
	
	// Buscamos el elemento
	$.postJSON("clase/consultaPorClave.do", clase, function(data) {
		$('#wrapperDialogBorrar,#claseFormBorrar,#cveIdClase').val(data.cveIdClase);
		oDgBorrar.dialog('open');
	}).error(function(data){ 
		alert("error" + data);
	}).complete(function(){
		//Instrucciones para el 'complete'
	});
}


function ayuda(){
	alert("ayuda");
	oDgAyuda.dialog('open');
}




