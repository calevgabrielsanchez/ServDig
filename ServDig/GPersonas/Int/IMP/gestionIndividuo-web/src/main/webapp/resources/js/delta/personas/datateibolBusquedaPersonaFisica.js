
var dtSolicitudesConcluidas;
/* Configuracion del data table de busqueda concluida */
dtSolicitudesConcluidas = $('#tablaResultadoIndividuos').dataTable({
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
//                         "mDataProp" : "fechaNacimiento",
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
                       { 
                           "sTitle" : "Datos Complementarios",
                           "mDataProp" : "nada",
                           "sClass":"dtJustifyClassColumn"
                       }              
    			        
   			        ], "aoColumnDefs": [
										{
										    "fnRender": function ( oObj ) {
										        var idBoton = 'btnControl' + oObj.aData['idPersona'];
												var retVal = '<input type="button" id="' + idBoton + '" class="mboton" name="btnDatosComplementarios" value="Mostrar" onclick="mostrarPopUpDatosComplementarios(' +  oObj.aData['idPersona'] + ');" />';
										        return retVal;
										    },
										    "aTargets": [ 13 ]
										}
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
			resetDisplayStart(dtSolicitudesConcluidas);
			//Solicita de nuevo la busqueda
			dtSolicitudesConcluidas.fnDraw();
			return false;
		});
