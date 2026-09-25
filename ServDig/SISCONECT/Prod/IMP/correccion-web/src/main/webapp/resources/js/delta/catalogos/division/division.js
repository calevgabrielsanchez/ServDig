/**
 * JS para el soporte del catalogo de division.
 */


var idDataTable 	= "#dtDivision";
var idDgNuevo 		= "#dgDivisionNuevo";
var idDgModificar 	= "#dgDivisionModificar";
var idDgBorrar 		= "#dgDivisionBorrar";
var idDgAyuda		= "#dgDivisionAyuda";

// Objeto del DataTable
var oDtDivision;
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
	 $( "#fecRegistroAlta, #fecRegistroBaja " ).datepicker( { dateFormat: 'yy-mm-dd' });

	/**
	 * Inicializacion del data table
	 */
	oDtDivision = $(idDataTable).dataTable({
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
				oObj.aData['cveIdDivision'] +'" id="radioTable" class="radioDivision" name="radio" onclick=""/> ';
				return retVal;
			}, 
			aTargets: [0]
			},
		        {
				"sTitle" : "Clave Division",
				"mDataProp" : "cveIdDivision",
				"sClass": "dtCenterClassColumn"
			}, {
				"sTitle" : "Descripci&oacute;n",
				"mDataProp" : "desDivision",
				"sClass": "dtCenterClassColumn"
			}, {
				"sTitle" : "N&uacute;mero Division",
				"mDataProp" : "numDivision",
				"sClass":"dtCenterClassColumn"
			}, {
				"sTitle" : "Fecha Alta",
				"mDataProp" : "fecRegistroAlta",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Fecha Baja",
				"mDataProp" : "fecRegistroBaja",
				"sClass": "dtCenterClassColumn"
			}
			],"bProcessing" : true,
			"sAjaxSource" : 'division/paginar.do',
			"fnServerData" : function(sSource, aoData, fnCallback) {
				aoData.push({
					"name" : "sSearch",
					"value" : $('#filtroDescripcion').val()
				});
				$.postJSON(sSource, aoData, function(data) {
					fnCallback(data);
				}).error(function(datas){ 
						validarSesionExpirada(datas);					
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
		    limpiarFormulario("#divisionForm");
		},
		buttons: {
			"Aceptar": function() { 
				var division = $("#divisionForm").serializeObject(true);				
				$.postJSON("division/agregar.do", division, function(data) {
				}).error(function(data){ 
					alert("error" + data);
				}).complete(function(){
					alert("La division ha sido agregada...");
					inicializaPosicionPaginador();					
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
		    limpiarFormulario("#divisionFormModificar");
		},		
		buttons: {
			"Aceptar": function() { 
				var division = $("#divisionFormModificar").serializeObject(true);				
				$.postJSON("division/modificar.do", division, function(data) {					
				}).error(function(data){ 
					alert("error" + data);
				}).complete(function(){
				
					alert("La division ha sido modificada...");
					inicializaPosicionPaginador();					
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
				var division = $("#divisionFormBorrar").serializeObject(true);
				$.postJSON("division/eliminar.do", division, function(data) {
				}).error(function(data){ 
					alert("error" + data);
				}).complete(function(){
					//Instrucciones para el complete
					alert("La division ha sido eliminada...");		
					inicializaPosicionPaginador();					
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
	oDtDivision.fnDisplayStart(0);
}


function nuevo(){
	oDgNuevo.dialog('open');
}

function paginar(){
	oDtDivision.fnDraw();
}

function modificar(){
	var idDivision = $('#:checked').val();
	var sDivision = '{"cveIdDivision":'+idDivision+'}';
	var division = jQuery.parseJSON(sDivision);
	// Buscamos el elemento
	$.postJSON("division/consultaPorClave.do", division, function(data) {
		$('#wrapperDialogModif,#divisionFormModificar,#cveIdDivision').val(data.cveIdDivision);
		$('#wrapperDialogModif,#divisionFormModificar,#desDivision').val(data.desDivision);
		$('#wrapperDialogModif,#divisionFormModificar,#fecRegistroAlta').val(data.fecRegistroAlta);
		$('#wrapperDialogModif,#divisionFormModificar,#fecRegistroBaja').val(data.fecRegistroBaja);
		oDgModificar.dialog('open');
	}).error(function(data){ 
		alert("error" + data);
	}).complete(function(){
		//Instrucciones para el 'complete'
	});
}

function borrar(){
	var idDivision = $('#:checked').val();
	var sDivision = '{"cveIdDivision":'+idDivision+'}';
	var division = jQuery.parseJSON(sDivision);
	
	// Buscamos el elemento
	$.postJSON("division/consultaPorClave.do", division, function(data) {
		$('#wrapperDialogBorrar,#divisionFormBorrar,#cveIdDivision').val(data.cveIdDivision);
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




