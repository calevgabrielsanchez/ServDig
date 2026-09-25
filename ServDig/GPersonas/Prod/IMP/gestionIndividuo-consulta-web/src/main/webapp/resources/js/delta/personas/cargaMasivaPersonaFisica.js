
/** JS para la carga de archiv de persona fisica */

$(document).ready(function() {
	var strMensaje = $('#idMensaje').val();
	
//	var validaCaptura = $("#uploadFileForm").validate({
//	rules: {
//		fileData: {
//	 		required: true,
//	 		alphanumeric: true
//		}
//	},
//	messages: {
//		fileData:"Debe ingresar una ruta v\u00e1lida"
//	}
//});
	
	// CUADRO DE DIALOGO DE ERROR AL HACER SUBMIT
	var oDialogoCerrarSesion;
	
	$('#cargarArchivo').click(function(){
		if($('#fileData').val() != ''){
			$('#uploadFileForm').submit();
		}else{
			oDialogoCerrarSesion.dialog('open');
			return false;
		}

	});
	
	// Inicializacion del dialogo de error al crear la solicitud
	 oDialogoCerrarSesion = $('#dgError').dialog({
	        autoOpen:false,
	        resizable: false,
	        height:150,
	        modal: true,
	        buttons: {
	            "Aceptar": function(data) {
	            	$( this ).dialog( "close" );
	            }
	        }
	 });
	
	var dtSolicitudesConcluidas;
	/* Configuracion del data table de solicitudes concluidas*/
	dtSolicitudesConcluidas = $('#tablaResultadoIndividuos').dataTable({
		
			sScrollX: "100%",
//	 		sScrollXInner: "1000%",
//			bScrollCollapse: true,
			bJQueryUI : true,
	        bFilter : false,
	        bInfo:true,
	        bSort: false,
	        "bPaginate": false,
	        "bAutoWidth" : true,
	        "iDeferLoading" : 0,
	        "bServerSide" : false, 
			"bProcessing" : false,
			 "aoColumns" : [
			                   { 
		                           "sTitle" : "# L&iacute;nea",
		                           "mDataProp" : "numeroLineaArchivo",
		                           "sClass":"dtJustifyClassColumn"
		                       },
		                       { 
		                           "sTitle" : "Estatus",
		                           "mDataProp" : "subEstadosFormateados",
		                           "sClass":"dtJustifyClassColumn"
		                       },
		                       { 
		                           "sTitle" : "Alta en IMSS",
		                           "mDataProp" : "altaEnImss",
		                           "sClass":"dtJustifyClassColumn"
		                       },
		                       { 
		                           "sTitle" : "Identificador",
		                           "mDataProp" : "idPersona",
		                           "sClass":"dtJustifyClassColumn"
		                       },
		                       { 
		                           "sTitle" : "RFC",
		                           "mDataProp" : "rfc",
		                           "sClass":"dtJustifyClassColumn"
		                       },
		                       { 
		                           "sTitle" : "CURP",
		                           "mDataProp" : "curpRenapo",
		                           "sClass":"dtJustifyClassColumn"
		                       },
		                       { 
		                           "sTitle" : "Nombre(s)",
		                           "mDataProp" : "nombre",
		                           "sClass":"dtJustifyClassColumn"
		                       },
		                       { 
		                           "sTitle" : "Primer Apellido",
		                           "mDataProp" : "primerApellido",
		                           "sClass":"dtJustifyClassColumn"
		                       },
		                       { 
		                           "sTitle" : "Segundo Apellido",
		                           "mDataProp" : "segundoApellido",
		                           "sClass":"dtJustifyClassColumn"
		                       },
		                       { 
		                           "sTitle" : "Sexo",
		                           "mDataProp" : "sexo.descripcion",
		                           "sClass":"dtJustifyClassColumn"
		                       },
		                       { 
		                           "sTitle" : "Fecha de Nacimiento",
//		                           "mDataProp" : "fechaNacimiento",
		                           "mDataProp" : "fechaNacimientoFormateada",
		                           "sClass":"dtJustifyClassColumn"
		                       },
		                       { 
		                           "sTitle" : "Lugar de Nacimiento",
		                           "mDataProp" : "lugarNacimiento.nombre",
		                           "sClass":"dtJustifyClassColumn"
		                       }
		                       ],     
		       
			"sAjaxSource" : context_path + '/persona/fisica/registro-masivo/recuperaArchivo',
			
			   "fnServerData" : function(sSource, aoData, fnCallback) {
	                
	                
	                aoData.push({
	                    "name" : "sSearch",
	                    "value" : ''
	                });
	                var wrapper = new Object();
	                wrapper.aoData = aoData;
	                var oForm = $('#personaFisica').serializeObject(true);
	                wrapper.oForm = oForm;
	                
	                $.postJSON(sSource, wrapper, function(data) {
	                    fnCallback(data);
	                }).error(function(data) {
	                    
	                    fnProcesarErrores(data, "#personaFisica");
	                    fnCallback(dataEmpty);
	                });
	                
	            }
	});				     
			dtSolicitudesConcluidas.fnDraw();	

});
