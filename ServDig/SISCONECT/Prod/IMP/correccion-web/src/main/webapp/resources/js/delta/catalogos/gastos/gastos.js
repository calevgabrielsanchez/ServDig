/**
 * JS para el soporte del catalogo de clase.
 */


var idDataTable 	= "#dtGasto";
var idDgRegistro	= "#dgGastosRegistro";
var idDgBorrarRegistro	= "#dgGastosBorrar";

	
// Objeto del DataTable
var oDtGasto;
// Dialogos
var oDgRegistro;
var oDgBorrarRegistro;


/**
 * Iniciamos la configuracion de los componentes visuales de jQuery.
 */
$(document).ready(function() {
	 	 	

	/**
	 * Inicializacion del data table
	 */
	oDtGasto = $(idDataTable).dataTable({
		bJQueryUI : true,
		bFilter : false,
		bInfo:true,
		bSort: false,
		"bPaginate": true,
		"bAutoWidth" : true,
		"bServerSide" :	true,
		"iDeferLoading": 0,	
		"aoColumns" : [ {
			fnRender :function(oObj){
				var retVal = '<input type="radio" value="' +
				oObj.aData['cveGasto'] +'" id="radioTable" class="radioDeteccion" name="radio"/> ';
				return retVal;
			}, 
			aTargets: [0],
			"sWidth": "30px"
			},{
				"sTitle" : "Gastos ",
				"mDataProp" : "txGasto",
				"sClass": "dtCenterClassColumn"
			}
			],"bProcessing" : true,
			"sAjaxSource" : 'gastos/paginar.do',
			"fnServerData" : function(sSource, aoData, fnCallback) {				
				
				var wrapper = new Object();
				wrapper.aoData = aoData;								
		
				var oForm = $("#gastosForm").toObject({mode:'first'});
				wrapper.oForm = oForm;
				
				$.postJSON(sSource, wrapper, function(data) {			
					
					validaEstadoSolicitudCorreccion(data,"dgGastoBotones");
					verifyCustomDataError(data);
					
					fnCallback(data);
				 }).error(function(datas){ 
						validarSesionExpirada(datas);
				});
			}
		});
	
	
	// Dialog de Elemento Nuevo			
	 oDgRegistro = $(idDgRegistro).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 930,
		beforeClose :function(event,ui){
		    limpiarFormulario("#dgGastosRegistro");
		},
		buttons: {
		
			"Guardar": function() {
				$("#labelFolioCorreccion").html('');
				if(!jsValidaGuardar()){
					return false;
				}		
				bloquear();
				var crtGastos = $("#gastosFormRegistro").serializeObject(true);				
				$.postJSON("gastos/agregar.do", crtGastos, function(data) {
					if(data == null){
						alert('Verificar Folio Correccion');
					}else{
						validaEstadoSolicitudCorreccion(data,"dgGastoBotones");
						verifyCustomDataError(data);
						
						$("form#gastosFormRegistro #txGasto").val("");
						$("form#gastosFormRegistro #cveGasto").val("");
						inicializaPosicionPaginador();	
						desbloquear();
					}
				}).error(function(data){ 
					validarSesionExpirada(data);
					alert("error" + data);
				});
				$(this).dialog("close"); 
			}, 
			"Cancelar": function() { 
				$("form#gastosFormRegistro #cveGasto").val("");
				$(this).dialog("close"); 
			} 
		}
	});
	 
	// Dialog de Elemento a Borrar			
	 oDgBorrarRegistro = $(idDgBorrarRegistro).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		height: 190,
		buttons: {
			"Aceptar": function() { 

				var clase = $("#gastosFormBorrar").toObject({mode:'first'});
				bloquear();
				$.postJSON("gastos/eliminar.do", clase, function(data) {
					verifyCustomDataError(data);		
					inicializaPosicionPaginador();
					desbloquear();			
				}).error(function(data){ 
					validarSesionExpirada(data);
					alert("error" + data);
				});
				$(this).dialog("close"); 
			}, 
			"Cancelar": function() { 
				$("form#gastosFormRegistro #cveGasto").val("");
				$(this).dialog("close"); 
			} 
		}
	});	 
	 
	 
	 triggerPatronInternet('','folioMain','btnGastosBusqueda',undefined,true);
});

//Inicializa el paginador
function inicializaPosicionPaginador(){
	oDtGasto.fnDisplayStart(0);
}


function registra(){
	guardarGasto();
//	var cveSubDelOficial = $("form#gastosForm #cveSubdelegcionOficialGastos").val();
//	var folio = $("form#gastosForm #folioMain").val();
//	
//	if($("form#gastosForm #folioMain").val() != null && $("form#gastosForm #folioMain").val()!= undefined && $("form#gastosForm #folioMain").val()!=''){
//		if(folio.substring(0,4) == cveSubDelOficial){
//			$("form#gastosFormRegistro #folioRegistro").prop('readOnly','');
//			oDgRegistro.dialog('open');
//			pasaParametroFolioGastos();
//		}else{
//			alert("El Folio de Correcci\u00F3n NO corresponde a la Subdelegaci\u00F3n");
//		}
//	}else {
//		alert("Debe ingresar un folio ");
//	}
}

function buscar(){
	var cveSubDelOficial = $("form#gastosForm #cveSubdelegcionOficialGastos").val();
	var folio = $("form#gastosForm #folioMain").val();
	
	
	$("#labelFolioMain").html('');
	if($("#folioMain").val() != ''){
		if(folio.substring(0,4) == cveSubDelOficial){
			$("#gastoButtons").show("fast");
			resetDisplayStart(oDtGasto);
			oDtGasto.fnDraw();
		}else{
			alert("El Folio de Correcci\u00F3n NO corresponde a la Subdelegaci\u00F3n");
		}
	}else{
		
	
		$("#labelFolioMain").html('<label style="color: red;"> Campo Requerido </label>');
	}	
}


function borrar(){
	var idClase = $('#:checked').val();
	var sClase = '{"cveGasto":'+idClase+'}';
	var clase = jQuery.parseJSON(sClase);
	
	// Buscamos el elemento
	bloquear();
	$.postJSON("gastos/consultaPorClave.do", clase, function(data) {
		$('#wrapperDialogBorrar #gastosFormBorrar #cveGasto').val(data.cveGasto);
		oDgBorrarRegistro.dialog('open');
		desbloquear();
	}).error(function(data){ 
		validarSesionExpirada(data);
		alert("error" + data);
	});
}

function modificar(){
	$("#labelFolioMain").html('');
	$("#labelFolioRegistro").html('');
	var idClase = $('#:checked').val();
	
	if(idClase!=undefined){
		bloquear();
		var sClase = '{"cveGasto":'+idClase+'}';
		var clase = jQuery.parseJSON(sClase);
		
		// Buscamos el elemento
		$("form#gastosFormRegistro #folioRegistro").prop('readOnly','true');
		
		$.postJSON("gastos/consultaPorClave.do", clase, function(data) {
			$("form#gastosFormRegistro #txGasto").val(data.txGasto);
			$("form#gastosFormRegistro #cveGasto").val(data.cveGasto);
			$("form#gastosFormRegistro #cveSolicitudCorr").val(data.cveSolicitudCorr)
			$("form#gastosFormRegistro #folioCorreccion").val(data.folioCorreccion);
			$("#folioRegistro").val(data.folioCorreccion);
			$("#gastoRegistro").val(data.txGasto);
			
			oDgRegistro.dialog('open');
			desbloquear();
		}).error(function(data){ 
			validarSesionExpirada(data);
			alert("error" + data);
		});
		
	}else alert("Seleccione un elemento de la lista");
	
}

function jsValidaGuardar(){
	
	var regresa = true;
	var folio        = $('#folioRegistro').val();
	var gasto        = $('#gastoRegistro').val();
	
	if(folio == ''){
		 $('#labelFolioRegistro').html('<label style="color: red;"> Campo Requerido </label>');		
		 regresa = false;
	}if(gasto == ''){
		$('#labelGastoRegistro').html('<label style="color: red;"> Campo Requerido </label>');		
		 regresa = false;
	}	
	return regresa;
	
}

function limpiar(){
	
	$("form#gastosFormRegistro #txGasto").val('');
	$("form#gastosFormRegistro #cveGasto").val('');
	$("form#gastosFormRegistro #cveSolicitudCorr").val('')
	$("form#gastosFormRegistro #folioCorreccion").val('');
	$("#folioMain").val('');
	$("#txGastoMain").val('');
	$("#labelFolioMain").html('');
	
	
	oDtGasto.fnDraw();
	
}

function pasaParametroFolioGastos(){
	//alert("pasaParametroFolio");
	var folioParam = $("form#gastosForm #folioMain").val();
	//alert("folioRegistro : "+ folioParam);
	$("form#gastosFormRegistro #folioRegistro").val(folioParam);
	
}


function guardarGasto(){
	$("#labelFolioCorreccion").html('');
	if(!jsValidaGuardarGasto()){
		return false;
	}		
	bloquear();
	var crtGastos = $("#gastosFormRegistro").serializeObject(true);		
	
	crtGastos.folioCorreccion= $("#folioMain").val();
	crtGastos.txGasto=$("#txGastoMain").val();
	
	
	
	$.postJSON("gastos/agregar.do", crtGastos, function(data) {
		if(data == null){
			alert('Verificar Folio Correccion');
		}else{
			validaEstadoSolicitudCorreccion(data,"dgGastoBotones");
			verifyCustomDataError(data);
			
			$("form#gastosFormRegistro #txGasto").val("");
			$("form#gastosFormRegistro #cveGasto").val("");
//			inicializaPosicionPaginador();	
			desbloquear();
			$("#txGastoMain").val("");
			$("#btnGastosBusqueda").trigger('click');
			
		}
	}).error(function(data){ 
		validarSesionExpirada(data);
		alert("error" + data);
	});
}


function jsValidaGuardarGasto(){
	
	var regresa = true;
	var folio        = $('#folioMain').val();
	var gasto        = $('#txGastoMain').val();
	
	if(folio == ''){
		 $('#labelFolioMain').html('<label style="color: red;"> Campo Requerido </label>');		
		 regresa = false;
	}if(gasto == ''){
		$('#labelGastoRegistro').html('<label style="color: red;"> Campo Requerido </label>');		
		 regresa = false;
	}	
	return regresa;
	
}