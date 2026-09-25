/**
 * JS para el soporte del catalogo de clase.
 */


var idDataTable 	= "#dtGrupoCategoria";
var idDgRegistro	= "#dgGrupoCategoriaRegistro";
var idDgBorrarRegistro	= "#dgGrupoCategoriaBorrar";

	
// Objeto del DataTable
var oDtGrupoCategoria;
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
	oDtGrupoCategoria = $(idDataTable).dataTable({
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
				oObj.aData['cveGrupoCategoria'] +'" id="radioTable" class="radioDeteccion" name="radio"/> ';
				return retVal;
			}, 
			aTargets: [0]
			},{
				"sTitle" : "GrupoCategoria ",
				"mDataProp" : "txGrupoCategoria",
				"sClass": "dtCenterClassColumn"
			}
			],"bProcessing" : true,
			"sAjaxSource" : 'grupoCategoria/paginar.do',
			"fnServerData" : function(sSource, aoData, fnCallback) {				
				
				var wrapper = new Object();
				wrapper.aoData = aoData;								
		
				var oForm = $("#grupoCategoriaForm").toObject({mode:'first'});
				wrapper.oForm = oForm;
				
				$.postJSON(sSource, wrapper, function(data) {										
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
		    limpiarFormulario("#dgGrupoCategoriaRegistro");
		},
		buttons: {
		
			"Guardar": function() {
				$("#labelFolioCorreccion").html('');
				if(!jsValidaGuardar()){
					return false;
				}	
				bloquear();
				var crtGrupoCategoria = $("#grupoCategoriaFormRegistro").serializeObject(true);				
				$.postJSON("grupoCategoria/agregar.do", crtGrupoCategoria, function(data) {
					if(data == null){
						alert('Verificar Folio Correccion');
					}else{
						alert("Operaci\u00f3n Exitosa");
					}
				}).error(function(data){ 
					alert("error" + data);
				}).complete(function(){
					
					$("form#grupoCategoriaFormRegistro #txGrupoCategoria").val("");
					$("form#grupoCategoriaFormRegistro #cveGrupoCategoria").val("");
					inicializaPosicionPaginador();	
					desbloquear();
				});
				$(this).dialog("close"); 
			}, 
			"Cancelar": function() { 
				$("form#grupoCategoriaFormRegistro #cveGrupoCategoria").val("");
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

				var clase = $("#grupoCategoriaFormBorrar").toObject({mode:'first'});
				bloquear();
				$.postJSON("grupoCategoria/eliminar.do", clase, function(data) {
					alert("Operación Exitosa");		
					inicializaPosicionPaginador();							
				}).error(function(data){ 
					alert("error" + data);
				}).complete(function(){
					desbloquear();			
				});
				$(this).dialog("close"); 
			}, 
			"Cancelar": function() { 
				$("form#grupoCategoriaFormRegistro #cveGrupoCategoria").val("");
				$(this).dialog("close"); 
			} 
		}
	});	 
});

//Inicializa el paginador
function inicializaPosicionPaginador(){
	oDtGrupoCategoria.fnDisplayStart(0);
}


function registra(){
	oDgRegistro.dialog('open');
}

function buscar(){
	$("#labelFolioMain").html('');
	if($("#folioMain").val() != ''){
		
		$("#grupoCategoriaButtons").show("fast");
		oDtGrupoCategoria.fnDraw();
	}else{
		$("#labelFolioMain").html('<label style="color: red;"> Campo Requerido </label>');
	}		
}

function borrar(){
	var idClase = $('#:checked').val();
	var sClase = '{"cveGrupoCategoria":'+idClase+'}';
	var clase = jQuery.parseJSON(sClase);
	
	// Buscamos el elemento
	bloquear();
	$.postJSON("grupoCategoria/consultaPorClave.do", clase, function(data) {
		$('#wrapperDialogBorrar #grupoCategoriaFormBorrar #cveGrupoCategoria').val(data.cveGrupoCategoria);
		oDgBorrarRegistro.dialog('open');
	}).error(function(data){ 
		alert("error" + data);
	}).complete(function(){
		desbloquear();
	});
}

function modificar(){
	$("#labelFolioMain").html('');
	$("#labelFolioCorreccion").html('');
	var idClase = $('#:checked').val();
	
	if(idClase!=undefined){
		bloquear();
		var sClase = '{"cveGrupoCategoria":'+idClase+'}';
		var clase = jQuery.parseJSON(sClase);
		
		// Buscamos el elemento
		$.postJSON("grupoCategoria/consultaPorClave.do", clase, function(data) {
			$("form#grupoCategoriaFormRegistro #txGrupoCategoria").val(data.txGrupoCategoria);
			$("form#grupoCategoriaFormRegistro #cveGrupoCategoria").val(data.cveGrupoCategoria);
			$("form#grupoCategoriaFormRegistro #cveSolicitudCorr").val(data.cveSolicitudCorr)
			$("form#grupoCategoriaFormRegistro #folioCorreccion").val(data.folioCorreccion);
			$("#folioRegistro").val(data.folioCorreccion);
			$("#txGrupoCategoria").val(data.txGrupoCategoria);
			
			oDgRegistro.dialog('open');
		}).error(function(data){ 
			alert("error" + data);
		}).complete(function(){
			desbloquear();
		});
		
	}else alert("Seleccione un elemento de la lista");
	
}

function jsValidaGuardar(){
	
	var regresa = true;
	var folio          = $('#folioRegistro').val();
	var grupoCategoria = $('#txGrupoCategoria').val();
	
	if(folio == ''){
		 $('#labelFolioCorreccion').html('<label style="color: red;"> Campo Requerido </label>');		
		 regresa = false;
	}if(grupoCategoria == ''){
		$('#labelGrupoCategoriaRegistro').html('<label style="color: red;"> Campo Requerido </label>');		
		regresa = false;
	}	
	return regresa;
	
}

function limpiar(){
	
	$("form#grupoCategoriaFormRegistro #txGrupoCategoria").val('');
	$("form#grupoCategoriaFormRegistro #cveGrupoCategoria").val('');
	$("form#grupoCategoriaFormRegistro #cveSolicitudCorr").val('')
	$("form#grupoCategoriaFormRegistro #folioCorreccion").val('');
	$("#folioMain").val('');
	$("#txGrupoCategoriaMain").val('');
	$("#labelFolioMain").html('');
	
	oDtGrupoCategoria.fnDraw();
	
}

