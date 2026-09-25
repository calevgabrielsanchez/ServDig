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
		"aoColumns" : [ {
			fnRender :function(oObj){
				var retVal = '<input type="radio" value="' +
				oObj.aData['cvePercepcion'] +'" id="radioTable" class="radioDeteccion" name="radio"/> ';
				return retVal;
			}, 
			aTargets: [0]
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
				if(!jsValidaGuardar()){
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
		height: 140,
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
});

//Inicializa el paginador
function inicializaPosicionPaginador(){
	oDtPercepcion.fnDisplayStart(0);
}


function registra(){
	$("#labelFolioMain").html('');
	$("form#percepcionesFormRegistro #folioRegistro").prop('readOnly','');
	oDgRegistro.dialog('open');
}

function buscar(){
	$("#labelFolioMain").html('');
	if($("#mainFolio").val() != ''){
		
		$("#percepcionButtons").show("fast");
		oDtPercepcion.fnDraw();
	}else{
		$("#labelFolioMain").html('<label style="color: red;"> Campo Requerido </label>');
	}
	
}

function borrar(){
	$("#labelFolioMain").html('');
	bloquear();
	var idClase = $('#:checked').val();
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
	var folio        = $('#folioRegistro').val();
	var remuneracion = $('#remuneracionRegistro').val();
	
	if(folio == ''){
		 $('#labelFolioRegistro').html('<label style="color: red;"> Campo Requerido </label>');		
		regreso = false;
	}if(remuneracion == ''){
		$('#labelRemuneraRegistro').html('<label style="color: red;"> Campo Requerido </label>');		
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
