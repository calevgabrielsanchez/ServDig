
/** JS para la carga de archiv de persona fisica */

$(document).ready(function() {
//	var validaCaptura = $("#uploadFileForm").validate({
//		  rules: {
//			  fileData: {
//		 		  required: true,
//		 		  alphanumeric: true
//			  }
//		  },
//		messages:{
//			fileData:"Debe ingresar una ruta v\u00e1lida"
//		  }
//	});
		
	// CUADRO DE DIALOGO DE ERROR AL HACER SUBMIT
	var oDialogoCerrarSesion;
	
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
	
	$('#cargarArchivo').click(function(){
		if($('#fileData').val() != ''){
			$('#uploadFileForm').submit();
		}else{
			oDialogoCerrarSesion.dialog('open');
			return false;
		}

	});
	
	// Primera implementacion del datateibol (sin paginacion)
	/*
	var datateibolPersonasMorales = $('#tablaPersonasMorales').dataTable({
		
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
	                           "sTitle" : "NRP",
	                           "mDataProp" : "nrp",
	                           "sClass":"dtJustifyClassColumn"
	                       },
	                       { 
	                           "sTitle" : "Raz&oacute;n Social",
	                           "mDataProp" : "razonSocial",
	                           "sClass":"dtJustifyClassColumn"
	                       },
	                       { 
	                           "sTitle" : "Tipo Sociedad",
	                           "mDataProp" : "tipoSociedad.descripcion",
	                           "sClass":"dtJustifyClassColumn"
	                       },
	                       { 
	                           "sTitle" : "Acta constitutiva",
	                           "mDataProp" : "actaConstitutiva",
	                           "sClass":"dtJustifyClassColumn"
	                       },
	                       { 
	                           "sTitle" : "Fecha de Creaci&oacute;n",
	                           "mDataProp" : "fechaCreacion",
	                           "sClass":"dtJustifyClassColumn"
	                       }
	                   ],

			   
			"sAjaxSource" : context_path + '/persona/moral/registro-masivo/recuperaArchivo',
			
			   "fnServerData" : function(sSource, aoData, fnCallback) {
	                
	                
	                aoData.push({
	                    "name" : "sSearch",
	                    "value" : ''
	                });
	                var wrapper = new Object();
	                wrapper.aoData = aoData;
	                var oForm = $('#personaMoral').serializeObject(true);
	                wrapper.oForm = oForm;
	                
	                $.postJSON(sSource, wrapper, function(data) {
	                    fnCallback(data);
	                }).error(function(data) {
	                    
	                    fnProcesarErrores(data, "#personaMoral");
	                    fnCallback(dataEmpty);
	                });
	                
	            }
	});				     
	*/
	// Fin primera implementacion
	 
	// Segunda implementacion del datateibol (con paginacion)
	 var datateibolPersonasMorales = $('#tablaPersonasMorales').dataTable({
		 								sScrollX: "100%",					// dibuja la tabla en el % especificado; siempre deberia ser 100
										sPaginationType: "full_numbers",	// pone todos los numeros de todas las paginas que salieron del calculo
										bJQueryUI: true,					// usa los estilos por omision de jquery
								        bPaginate: true,					// permite que aiga paginacion
								        bAutoWidth: true,
								        iDisplayLength: 10,					// registros mostrados por pagina
								        bFilter: false,						// caja de buscar arriba
								        bSort: false,						// flechitas castrosas para ordenar por columnas
//								        bLengthChange: true					// permite elegir el numero de items para paginar
									});
	 // Fin segunda implementacion
	
	 // Se pinta el datateibol
	datateibolPersonasMorales.fnDraw();	

});

