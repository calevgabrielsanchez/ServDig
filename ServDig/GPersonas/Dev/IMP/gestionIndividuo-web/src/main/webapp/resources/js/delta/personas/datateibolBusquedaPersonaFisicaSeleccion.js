
var cleanDatable = false;
/* Expresion regular para validar una fecha en formato dd/MM/yyyy */
var ER_FECHAS = /^(0[1-9]|[12][0-9]|3[01])[//](0[1-9]|1[012])[//](19|20)[0-9][0-9]$/;
var dtBusquedasSeleccionadas;
var registroSeleccionado;

$(document).ready(function(){
	
	$('#limpiar').click(function(){
 		$('#fechaNacimientoError').hide();
		cleanDatable = true;
		$('#busquedaPersonaFisicaForm').clearForm();
		dtSolicitudesConcluidas.fnDraw();
	});
	
	$('#buscar').click(function(){
		
		var fecha = $('#busquedaFechaNacimiento').val();
		
		/* Solo se valida la fecha en caso de que no sea vacia. Pero si sí esta vacia, va directio al submit para que el resto de las validaciones se hagan en el controller */
		if(fecha != ''){
			/* Si la fecha es valida, entonces sí hacemos el submit */
			if(ER_FECHAS.test(fecha)){
				$('#busquedaPersonaFisicaForm').submit();
				$('#fechaNacimientoError').hide();
			}else{
				$('#fechaNacimientoError').show();
			}
		}else{
			$('#fechaNacimientoError').hide();
 			$('#busquedaPersonaFisicaForm').submit();
		}
		
	});
	
	$('#tramiteRegistroPersonasFisicas').click(function(){
		
		/* Con esto abrimos la pagina de registro de personas fisicas yendo primero al controller para setear en la sesion el rol "ventanilla" */
		window.open(context_path + "/persona/tramites/agregar/fisica/ventanilla"); // rol "ventanilla"
		
	});
	
	/* Como aun no se sabe de donde se sacaran los datos del NSS, quedara deshabilitado */
	$('#busquedaNss').attr("readonly", "readonly");
	$('#busquedaNss').fadeTo('slow', 0.5);
	$('#labelNss').fadeTo('slow', 0.5);
		
	/* Inicializacion del dialogo de confirmacion de los datod de la persona */
	 oDialogoConfirmacion = $('#dgConfirmacion').dialog({
	        autoOpen:false,
	        resizable: false,
	        height:550,
	        width:450,
	        modal: true,
	        buttons: {
	            "Aceptar": function(data) {
	            	$( this ).dialog( "close" );

	        		window.returnValue = registroSeleccionado; // esta variable esta en datateibolBusquedaPersonaFisicaSeleccion.js
	        		window.close();
	        		
	            },
	            "Cancelar": function(data) {
	            	$( this ).dialog( "close" );
					return false;
	            }
	        }
	 });
	 
	 /* Configuracion del data table de busqueda concluida */
	 dtBusquedasSeleccionadas = $('#tablaResultadoIndividuos').dataTable({
	 		sScrollX: "100%",
	 		bJQueryUI : true,
	         bFilter : false,
	         bInfo: true,
	         bSort: false,
	         "bPaginate": true,
	         "bAutoWidth" : true,
	         "iDeferLoading" : 0,
	         "bServerSide" : true,
	         "aoColumns" : [
	                        { 
	                            "sTitle" : "Identificador",
	                            "mDataProp" : "idPersona",
	                            "sClass":"dtJustifyClassColumn"
	                        },
	                         { 
	                            "sTitle" : "NSS",
	                            "mDataProp" : "nss",
	                            "sClass":"dtJustifyClassColumn"
	                        },
	                        { 
	                            "sTitle" : "RFC",
	                            "mDataProp" : "rfc",
	                            "sClass":"dtJustifyClassColumn"
	                        },
	                        { 
	                            "sTitle" : "CURP",
	                            "mDataProp" : "curp",
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
	                            "mDataProp" : "fechaNacimientoFormateada",
	                            "sClass":"dtJustifyClassColumn"
	                        },
	                         { 
	                            "sTitle" : "Lugar de Nacimiento",
	                            "mDataProp" : "lugarNacimiento.nombre",
	                            "sClass":"dtJustifyClassColumn"
	                        },
	                         { 
	                            "sTitle" : "Nacionalidad",
	                            "mDataProp" : "pais.nacionalidad",
	                            "sClass":"dtJustifyClassColumn"
	                        },
	                         { 
	                            "sTitle" : "Estados",
	                            "mDataProp" : "estadosFormateados",
	                            "sClass":"dtJustifyClassColumn"
	                        },
	                         { 
	                            "sTitle" : "Calificaciones",
	                            "mDataProp" : "subEstadosFormateados",
	                            "sClass":"dtJustifyClassColumn"
	                        },
            
	    			        ], 
	 			"bProcessing" : true,
	             "sAjaxSource" : context_path + '/persona/fisica/busqueda/bdu',
	             "fnServerData" : function(sSource, aoData, fnCallback) {
	                 
	                 fnHideErrores("#busquedaPersonaFisicaForm");
	                 
	                 if(cleanDatable == false){
	 	                aoData.push({
	 	                    "name" : "sSearch",
	 	                    "value" : ''
	 	                });
	 	                var wrapper = new Object();
	 	                wrapper.aoData = aoData;
	 	                var oForm = $('#busquedaPersonaFisicaForm').toObject();
	 	                wrapper.oForm = oForm;
	 	                
	 	                $.postJSON(sSource, wrapper, function(data) {
	 	                    fnCallback(data);
	 	                }).error(function(data) {
	 	                    
	 	                    fnProcesarErrores(data, "#busquedaPersonaFisicaForm");
	 	                    fnCallback(dataEmpty);
	 	                });
	                 }else{
	                 	fnCallback(dataEmpty);
	                 }
	                 
	             }
	         });

	 		$('#busquedaPersonaFisicaForm').submit(function() {
	 			cleanDatable = false;
	 			//Previene el error de las pantallas adelantadas de la busqueda
	 			resetDisplayStart(dtBusquedasSeleccionadas);
	 			//Solicita de nuevo la busqueda
	 			dtBusquedasSeleccionadas.fnDraw();
	 			return false;
	 		});
	 		
	 		/* Codigo que capta el evento de dar clic en un renglon del datateibol */
	 		$('#tablaResultadoIndividuos tbody').click(function(event){
	 			
	 		    registroSeleccionado = dtBusquedasSeleccionadas.fnGetData(event.target.parentNode);
	 		    
	 		    $('#idPersona_').val(registroSeleccionado.idPersona);
	 		    $('#rfc_').val(registroSeleccionado.rfc);
	 		    $('#curp_').val(registroSeleccionado.curp);
	 		    $('#nombre_').val(registroSeleccionado.nombre);
	 		    $('#primerApellido_').val(registroSeleccionado.primerApellido);
	 		    $('#segundoApellido_').val(registroSeleccionado.segundoApellido);
	 		    $('#sexo_').val(registroSeleccionado.sexo.descripcion);
	 		    $('#fechaNacimiento_').val(registroSeleccionado.fechaNacimientoFormateada);
	 		    $('#lugarNacimiento_').val(registroSeleccionado.lugarNacimiento.nombre);
	 		    $('#nacionalidad_').val(registroSeleccionado.pais.nacionalidad);
	 		    $('#estados_').val(registroSeleccionado.estadosFormateados);
	 		    $('#calificaciones_').val(registroSeleccionado.subEstadosFormateados);
	 		    
	 		    oDialogoConfirmacion.dialog('open');
	 		    
	 		});
	 		
	 		/* Script para marcar un renglon seleccionado en un datateibol */
	 		/* Add a click handler to the rows - this could be used as a callback */
	         $("#tablaResultadoIndividuos tbody").mouseover(function(event) {
	             $(dtBusquedasSeleccionadas.fnSettings().aoData).each(function (){
	                 $(this.nTr).removeClass('row_selected');
	             });
	            
	             if($(event.target.parentNode).hasClass('row_selected')){
	                 $(event.target.parentNode).removeClass('row_selected');
	             }
	             else{
	                 $(event.target.parentNode).addClass('row_selected');
	             }
	                 
	         });	
	
});

