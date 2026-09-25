
var cleanDatable = false;
/* Expresion regular para validar una fecha en formato dd/MM/yyyy */
var ER_FECHAS = /^(0[1-9]|[12][0-9]|3[01])[//](0[1-9]|1[012])[//](19|20)[0-9][0-9]$/;

$(document).ready(function(){
	
	// de momento se inhabilita el campo nss en pantalla de busqueda de persona fisica
	$('#busquedaNssLabel').fadeTo('slow', 0.5);
	$('#busquedaNss').fadeTo('slow', 0.5);
	$('#busquedaNss').attr("disabled", "disabled");
	
	$('#limpiar').click(function(){
 		$('#fechaNacimientoErrorCliente').hide();
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
				$('#fechaNacimientoErrorCliente').hide();
			}else{
				$('#fechaNacimientoErrorCliente').show();
			}
		}else{
			$('#fechaNacimientoErrorCliente').hide();
 			$('#busquedaPersonaFisicaForm').submit();
		}
		
	});
	
	var dtSolicitudesConcluidas;
	jQuery.fn.dataTableExt.oPagination.iFullNumbersShowPages = 5;
	/* Configuracion del data table de busqueda concluida */
	dtSolicitudesConcluidas = $('#tablaResultadoIndividuos').dataTable({
		"sPaginationType": "full_numbers",
		sScrollX: "100%",
// 		sScrollXInner: "1000%",
//		bScrollCollapse: true,
		bJQueryUI : true,
        bFilter : false,
        bInfo: true,
        bSort: false,
        "bPaginate": true,
        "bAutoWidth" : true,
        "iDeferLoading" : 0,
        "bServerSide" : true,
        //"sPaginationType": "full_numbers",
        "aoColumns" : [
                       { 
                           "sTitle" : "Identificador",
                           "mDataProp" : "idPersona",
                           "sClass":"dtJustifyClassColumn"
                       },
//                        { 
//                           "sTitle" : "NSS",
//                           "mDataProp" : "nss",
//                           "sClass":"dtJustifyClassColumn"
//                       },
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
                           "sTitle" : "Otros Atributos",
//                         "mDataProp" : "nada",
                           "sClass":"dtJustifyClassColumn"
                       }              
    			        
   			        ], "aoColumnDefs": [
										{
										    "fnRender": function ( oObj ) {
										        var idBoton = 'btnControl' + oObj.aData['idPersona'];
												var retVal = '<input type="button" id="' + idBoton + '" class="mboton" name="btnDatosComplementarios" value="Mostrar" onclick="mostrarPopUpDatosComplementarios(' +  oObj.aData['idPersona'] + ');" />';
										        return retVal;
										    },
//											como se quito la columna NSS, el indice baja en 1 posicion (entonces de la posicion 13 baja a las 12)										    
//										    "aTargets": [ 13 ]
										    "aTargets": [ 12 ]
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

	$('#tramiteRegistroPersonasFisicas').click(function(){
		/* Con esto abrimos la pagina de registro de personas fisicas yendo primero al controller para setear en la sesion el rol "ventanilla" */
		window.open(context_path + "/persona/tramites/agregar/fisica/ventanilla"); // rol "ventanilla"
	});
	

	$('#busquedaPersonaFisicaForm').submit(function() {
		cleanDatable = false;
		//Previene el error de las pantallas adelantadas de la busqueda
		resetDisplayStart(dtSolicitudesConcluidas);
		//Solicita de nuevo la busqueda
		dtSolicitudesConcluidas.fnDraw();
		return false;
	});
	
});

var objDialogoCtrl = {
	dialogo : {}
};

function mostrarPopUpDatosComplementarios(idPersona){
	// Configuracion de los dialogos
	var url = context_path + "/persona/fisica/datos-complementarios/busqueda/" + idPersona;
	var d = $('#dgModalDatosComplementarios').html('<iframe id="site" src="' + url + '" width="100%" height="100%" frameborder="0" />');
	
     // Configuracion del dialogo
     objDialogoCtrl.dialogo = d.dialog({
        title: 'Registro de Persona F&iacute;sica',
        autoOpen: false,
        width: 980,
        height: 900,
        modal: true,
        resizable: false,
        autoResize: true,
        overlay: {
            opacity: 0.5,
            background: "black"
        }
    }).width(900).height(900);
     
	objDialogoCtrl.dialogo.dialog('open');
	
};
