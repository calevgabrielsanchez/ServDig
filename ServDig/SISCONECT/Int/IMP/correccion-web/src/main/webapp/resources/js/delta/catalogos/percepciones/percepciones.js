/**
 * JS para el soporte del catalogo de clase.
 */


var idDataTable 	= "#dtPercepcion";
var idDgRegistro	= "#dgPercepcionesRegistro";
var idDgBorrarRegistro	= "#dgPercepcionBorrar";

	
// Objeto del DataTable
var oDtPercepcion;
// Dialogos
var oDgRegistro;
var oDgBorrarRegistro;


/**
 * Iniciamos la configuracion de los componentes visuales de jQuery.
 */
$(document).ready(function() {
	
	
	
	$("#labelFolioCorreccion").html('');

	/**
	 * Inicializacion del data table
	 */
	oDtPercepcion = $(idDataTable).dataTable({
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
				oObj.aData['cvePercepcion'] +'" id="radioTable" class="radioDeteccion" name="radio"/> ';
				return retVal;
			}, 
			aTargets: [0],
			"sWidth": "30px"
			},{
				"sTitle" : "Percepci&oacute;n ",
				"mDataProp" : "txRemuneracion",
				"sClass": "dtCenterClassColumn"
			}
			],"bProcessing" : true,
			"sAjaxSource" : 'percepciones/paginar.do',
			"fnServerData" : function(sSource, aoData, fnCallback) {				
				
				var wrapper = new Object();
				wrapper.aoData = aoData;								
		
				var oForm = $("#percepcionesForm").toObject({mode:'first'});
				wrapper.oForm = oForm;
				
				$.postJSON(sSource, wrapper, function(data) {
						
					validaEstadoSolicitudCorreccion(data,"dgPromocionBotones");
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
		    limpiarFormulario("#dgPercepcionesRegistro");
		},
		buttons: {
		
			"Guardar": function() {
				
				$("#labelFolioRegistro").html('');
				
				if($("#remuneracionRegistro").val()==""){
					alert("Ingrese un nuevo valor para la percepcion");
					return false;
				}		
				
				
				var crtPercepciones = $("#percepcionesFormRegistro").serializeObject(true);				
				$.postJSON("percepciones/agregar.do", crtPercepciones, function(data) {
					if (data == null){
						alert('Verificar el Folio de la Solicitud de Correcci\u00F3n');
					} else {
						
						validaEstadoSolicitudCorreccion(data,"dgPromocionBotones");
						verifyCustomDataError(data);

						$("form#percepcionesFormRegistro #txRemuneracion").val("");
						$("form#percepcionesFormRegistro #cvePercepcion").val("");
						desbloquear();
						
						inicializaPosicionPaginador();	
					}
				}).error(function(data){ 
					validarSesionExpirada(data);
				});
				$(this).dialog("close"); 
			}, 
			"Cancelar": function() { 
				$("form#percepcionesFormRegistro #cvePercepcion").val("");
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
				bloquear();
				$("#labelFolioCorreccion").html('');
				var clase = $("#percepcionesFormBorrar").toObject({mode:'first'});
				
				$.postJSON("percepciones/eliminar.do", clase, function(data) {
					
					if(data.error!=""){
						alert("Error:"+data.error);
					}else{
						verifyCustomDataError(data);
					}
							
					inicializaPosicionPaginador();
					desbloquear();			
				}).error(function(data){ 
					validarSesionExpirada(data);
					alert("error-- intentaramos procesar" + data);
					fnProcesarErrores(data);
				});
				$(this).dialog("close"); 
			}, 
			"Cancelar": function() { 
				$("form#percepcionesFormRegistro #cvePercepcion").val("");
				$(this).dialog("close"); 
			} 
		}
	});	 
	 
	 triggerPatronInternet('','mainFolio','btnBuscaPercepcion',undefined,true);
});

//Inicializa el paginador
function inicializaPosicionPaginador(){
	oDtPercepcion.fnDisplayStart(0);
}


function registra(){
	
	guardarPercepcion();
	
//	var cveSubDelOficial = $("form#percepcionesForm #cveSubdelegcionOficialHtml").val();
//	var folio = $("form#percepcionesForm #mainFolio").val();
//	
//	if($("form#percepcionesForm #mainFolio").val() != null && $("form#percepcionesForm #mainFolio").val()!= undefined && $("form#percepcionesForm #mainFolio").val()!=''){
//		if(folio.substring(0,4) == cveSubDelOficial){
//			$("#labelFolioMain").html('');
//			$("form#percepcionesFormRegistro #folioRegistro").prop('readOnly','');
//			pasaParametroFolio();
//			oDgRegistro.dialog('open');
//		}else{
//			alert("El Folio de Correcci\u00F3n NO corresponde a la Subdelegaci\u00F3n");
//		}
//		
//		
//	}else {
//		alert("Debe ingresar un folio ");
//	}

}

function buscar(){
	var cveSubDelOficial = $("form#percepcionesForm #cveSubdelegcionOficialHtml").val();
	var folio = $("form#percepcionesForm #mainFolio").val();
	
	$("#labelFolioMain").html('');
	if($("#mainFolio").val() != ''){

		if(folio.substring(0,4) == cveSubDelOficial){
			$("#percepcionButtons").show("fast");
			resetDisplayStart(oDtPercepcion);
			oDtPercepcion.fnDraw();
		}else{
			alert("El Folio de Correcci\u00F3n NO corresponde a la Subdelegaci\u00F3n");
		}
		
		
	}else{
		$("#labelFolioMain").html('<label style="color: red;"> Campo Requerido </label>');
	}
	
}

function borrar(){
	$("#labelFolioMain").html('');
	bloquear();
	var idClase = $('#:checked').val();

	if(idClase==undefined){
		desbloquear();
		return;
	}
	var sClase = '{"cvePercepcion":'+idClase+'}';
	
	var clase = jQuery.parseJSON(sClase);
	$("#labelFolioCorreccion").html('');
	// Buscamos el elemento
	
	
	$.postJSON("percepciones/consultaPorClave.do", clase, function(data) {
		$('#wrapperDialogBorrar #percepcionesFormBorrar #cvePercepcion').val(data.cvePercepcion);
	
		oDgBorrarRegistro.dialog('open');
		desbloquear();			
	}).error(function(data){ 
		validarSesionExpirada(data);
		alert("error" + data);
	});
}

function modificar(){
	
	$("#labelFolioMain").html('');	
	var idClase = $('#:checked').val();
	$("#labelFolioCorreccion").html('');
	if(idClase!=undefined){
		bloquear();
		var sClase = '{"cvePercepcion":'+idClase+'}';
		var clase = jQuery.parseJSON(sClase);
		
		// Buscamos el elemento
		$("form#percepcionesFormRegistro #folioRegistro").attr('readOnly','true');
		
		$.postJSON("percepciones/consultaPorClave.do", clase, function(data) {
			
			$("form#percepcionesFormRegistro #txRemuneracion").val(data.txRemuneracion);
			$("form#percepcionesFormRegistro #cvePercepcion").val(data.cvePercepcion);
			$("form#percepcionesFormRegistro #cveSolicitudCorr").val(data.cveSolicitudCorr);
			$("form#percepcionesFormRegistro #folioCorreccion").val(data.folioCorreccion);
			$("#folioRegistro").val(data.folioCorreccion);
			$("#remuneracionRegistro").val(data.txRemuneracion);
			
			oDgRegistro.dialog('open');
			desbloquear();
		}).error(function(data){ 
			validarSesionExpirada(data);
			alert("error" + data);
		});
	}else alert("Seleccione un elemento de la lista");
	
}

function jsValidaGuardar(){
	
	var regreso      = true;
	var folio        = $('#mainFolio').val();
	var remuneracion = $('#txRemuneracion').val();
	
	if(folio == ''){
		alert("El folio es requerido");		
		regreso = false;
	}if(remuneracion == ''){
		alert("Ingrese un concepto de remuneracion");		
		regreso = false;
	}	
	return regreso;
	
}

function limpiar(){
	
	$("form#percepcionesFormRegistro #txRemuneracion").val('');
	$("form#percepcionesFormRegistro #cvePercepcion").val('');
	$("form#percepcionesFormRegistro #cveSolicitudCorr").val('');
	$("form#percepcionesFormRegistro #folioCorreccion").val('');
	$("#folioRegistro").val('');
	$("#remuneracionRegistro").val('');
	$("#mainFolio").val('');
	$("#labelFolioMain").html('');
	oDtPercepcion.fnDraw();
	
}

function pasaParametroFolio(){
	//alert("pasaParametroFolio");
	var folioParam = $("form#percepcionesForm #mainFolio").val();
	//alert("folioRegistro : "+ folioParam);
	$("form#percepcionesFormRegistro #folioRegistro").val(folioParam);
	
}

function guardarPercepcion(){
	$("#labelFolioRegistro").html('');
	if(!jsValidaGuardar()){
		return false;
	}		
	
	
	var crtPercepciones = $("#percepcionesForm").serializeObject(true);				
	$.postJSON("percepciones/agregar.do", crtPercepciones, function(data) {
		if (data == null){
			alert('Verificar el Folio de la Solicitud de Correcci\u00F3n');
		} else {
			
			validaEstadoSolicitudCorreccion(data,"dgPromocionBotones");
			verifyCustomDataError(data);

			$("form#percepcionesFormRegistro #txRemuneracion").val("");
			$("form#percepcionesFormRegistro #cvePercepcion").val("");
			desbloquear();
			
			inicializaPosicionPaginador();	
		}
	}).error(function(data){ 
		validarSesionExpirada(data);
	});
	 
}
