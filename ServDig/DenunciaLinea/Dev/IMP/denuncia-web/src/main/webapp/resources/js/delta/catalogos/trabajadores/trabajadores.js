/**
 * JS para el soporte del catalogo de clase.
 */


var idDataTable 	= "#dtTrabajadores";
var idDgRegistro	= "#dgTrabajadoresRegistro";
var idDgBorrarRegistro	= "#dgTrabajadoresBorrar";


	
// Objeto del DataTable
var oDtTrabajadores;
// Dialogos
var oDgRegistro;
var oDgBorrarRegistro;


/**
 * Iniciamos la configuracion de los componentes visuales de jQuery.
 */

$(document).ready(function() {
	
//	$( "form#trabajadoresFormRegistro #fecIngreso").datepicker( { dateFormat: 'yy-mm-dd' });
	$( "#fecIngreso, form#trabajadoresFormRegistro #fec").datepicker( { dateFormat: 'yy-mm-dd' });
	/**
	 * Inicializacion del data table
	 */
	oDtTrabajadores = $(idDataTable).dataTable({
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
				oObj.aData['cveTrabajador'] +'" id="radioTable" class="radioDeteccion" name="radio"/> ';
				return retVal;
			}, 
			aTargets: [0]
			},{
				"sTitle" : "Numero Seguro Social ",
				"mDataProp" : "nuNss",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Nombre",
				"mDataProp" : "nombreAsegurado",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Apellido Paterno",
				"mDataProp" : "apPaternoAsegurado",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Apellido Materno",
				"mDataProp" : "apMaternoAsegurado",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Registro Patronal",
				"mDataProp" : "registroPatronal",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Per&iacute;odo",
				"mDataProp" : "periodo",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Prueba Selectiva",
				"mDataProp" : "pruebaSelectiva",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Salarios Topados",
				"mDataProp" : "salariosTopados",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Tiempo Extra",
				"mDataProp" : "tiempoExtra",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Honorarios",
				"mDataProp" : "honorarios",
				"sClass": "dtCenterClassColumn"
			}
			],"bProcessing" : true,
			"sAjaxSource" : 'trabajadores/paginar.do',
			"fnServerData" : function(sSource, aoData, fnCallback) {				
				
				var wrapper = new Object();
				wrapper.aoData = aoData;								
		
				var oForm = $("#trabajadoresForm").toObject({mode:'first'});
				wrapper.oForm = oForm;
				
				$.postJSON(sSource, wrapper, function(data) {				
					
					validaEstadoSolicitudCorreccion(data,"dgTrabajadoresBotones");
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
		    limpiarFormulario("#trabajadoresFormRegistro");
		},
		buttons: {
		
			"Guardar": function() {
				if(validaCaptura.form())
				{
					var crtTrabajadores = $("#trabajadoresFormRegistro").serializeObject(true);
					
					var numNSS =$("form#trabajadoresFormRegistro #nuNss").val();
										
					if(Number(numNSS)<0){
						alert("NSS incorrecto, digite un NSS valido");
						return false;
					}				
					
					if($("form#trabajadoresFormRegistro #apPaternoAsegurado").val()=="" && $("form#trabajadoresFormRegistro #apMaternoAsegurado").val()==""){
						alert("Debe de ingresar al menos un apellido");
						return false;
					}
					
					$.postJSON("trabajadores/agregar.do", crtTrabajadores, function(data) {
						if(data==null){
							alert('Folio de Correcion No valido'); }
						else{
							validaEstadoSolicitudCorreccion(data,"dgTrabajadoresBotones");
							verifyCustomDataError(data);
							inicializaPosicionPaginador();
							$("form#trabajadoresFormRegistro #cveTrabajador").val("");
						}
							
					}).error(function(data){ 
						validarSesionExpirada(data);
						alert("error" + data);
					});
					
					$(this).dialog("close"); 
				}
			}, 
			"Cancelar": function() { 
				$("form#trabajadoresFormRegistro #cveTrabajador").val("");
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

				var clase = $("#trabajadoresFormBorrar").toObject({mode:'first'});
				
				$.postJSON("trabajadores/eliminar.do", clase, function(data) {
					validaEstadoSolicitudCorreccion(data,"dgTrabajadoresBotones");
					$("form#trabajadoresFormRegistro #cveTrabajador").val("");
					
					verifyCustomDataError(data);		
					inicializaPosicionPaginador();
					
				}).error(function(data){ 
					validarSesionExpirada(data);
					alert("error" + data);
				});
				$(this).dialog("close"); 
			}, 
			"Cancelar": function() { 
				$("form#trabajadoresFormRegistro #cveTrabajador").val("");
				$(this).dialog("close"); 
			} 
		}
	});	 
	 
	 var validaCaptura = $("#trabajadoresFormRegistro").validate({
		  rules: {
		   folioCorreccion: {
		      required: true,
		      alphanumeric: true
		  },
		    nombreAsegurado: {
	   		  required: true,
	   		  alphanumeric: true
	  	  },
		    nuNss: {
	   		  required: true,
	   		  alphanumeric: true
	  	  }  
		  },
		messages:{
			folioCorreccion:"Se requiere Ingresar un Folio de Correccion",
			nombreAsegurado:"Se requiere del Nombre del Asegurado",
	  		nuNss:"Se requiere del Numero de Seguro Social"
		  }
	});	 
	 
	 
	 var validaBusqueda = $("#trabajadoresForm").validate({
		  rules: {
		   folioCorreccion: {
		      required: true,
		      alphanumeric: true
		  }
		  },
		messages:{
			folioCorreccion:"Se requiere Ingresar un Folio de Correccion",
		  }
	});	 
	 
	 
}); // FINALIZA DOCUMENT READY

//Inicializa el paginador
function inicializaPosicionPaginador(){
	oDtTrabajadores.fnDisplayStart(0);
}


function registra(){
	
	$("form#trabajadoresFormRegistro #folioCorreccion").prop('readOnly','');
	
	oDgRegistro.dialog('open');
}

function buscar(){
		
	if($("#trabajadoresForm").validate().element("#folioCorreccion")){	
		
		var indicador = $('#indicadorTrabajador').val();
		var periodo = $('#periodo').val();

		if(indicador!=-1&&periodo==""){
			alert("Al filtrar por Indicador es necesario el campo Periodo");
		    return false;
		    }
		$("#trabajadoresButtons").show("fast");
		oDtTrabajadores.fnDraw();
	}

}

function borrar(){
	var idClase = $('#:checked').val();
	
	if(idClase!=undefined){
		var sClase = '{"cveTrabajador":'+idClase+'}';
		var clase = jQuery.parseJSON(sClase);
		
		
		// Buscamos el elemento
		$.postJSON("trabajadores/consultaPorClave.do", clase, function(data) {
			$('#wrapperDialogBorrar #trabajadoresFormBorrar #cveTrabajador').val(data.cveTrabajador);
			oDgBorrarRegistro.dialog('open');
			$("form#trabajadoresFormRegistro #cveTrabajador").val("");
		}).error(function(data){ 
			validarSesionExpirada(data);
			alert("error" + data);
		});
	
     }else alert("Seleccione un elemento de la lista");
}

function modificar(){
	var idClase = $('#:checked').val();
	
	if(idClase!=undefined){
		var sClase = '{"cveTrabajador":'+idClase+'}';
		var clase = jQuery.parseJSON(sClase);
		
		
		$("form#trabajadoresFormRegistro #folioCorreccion").prop('readOnly','readOnly');
		
		// Buscamos el elemento
		$.postJSON("trabajadores/consultaPorClaveDatos.do", clase, function(data) {
			$("form#trabajadoresFormRegistro #folioCorreccion").val(data.folioCorreccion);
			$("form#trabajadoresFormRegistro #nuNss").val(data.nuNss);
			$("form#trabajadoresFormRegistro #txRfc").val(data.txRfc);
			$("form#trabajadoresFormRegistro #nombreAsegurado").val(data.nombreAsegurado);
			$("form#trabajadoresFormRegistro #apPaternoAsegurado").val(data.apPaternoAsegurado);
			$("form#trabajadoresFormRegistro #apMaternoAsegurado").val(data.apMaternoAsegurado);
			$("form#trabajadoresFormRegistro #cveTrabajador").val(data.cveTrabajador);
	
			
			oDgRegistro.dialog('open');
		}).error(function(data){ 
			validarSesionExpirada(data);
			alert("error" + data);
		});
		
	}else alert("Seleccione un elemento de la lista");
	
}
