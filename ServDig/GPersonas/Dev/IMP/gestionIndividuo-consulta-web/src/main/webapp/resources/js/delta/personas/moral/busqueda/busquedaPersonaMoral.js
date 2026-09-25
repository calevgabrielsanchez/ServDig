
var cleanDatable = false;
/* Expresion regular para validar una fecha en formato dd/MM/yyyy */
var ER_FECHAS = /^(0[1-9]|[12][0-9]|3[01])[//](0[1-9]|1[012])[//](19|20)[0-9][0-9]$/;

$(document).ready(function(){

	// de momento se inhabilita el campo nss en pantalla de busqueda de persona moral
	$('#busquedaNrpLabel').fadeTo('slow', 0.5);
	$('#busquedaNrp').fadeTo('slow', 0.5);
	$('#busquedaNrp').attr("disabled", "disabled");
	
	$('#limpiar').click(function(){
		$('#fechaCreacionErrorCliente').hide();
		cleanDatable = true;
		$('#busquedaPersonaMoralForm').clearForm();
		dtPersonasMorales.fnDraw();
	});
	
	$('#buscar').click(function(){
		var fecha = $('#busquedaFechaCreacion').val();

		/* Solo se valida la fecha en caso de que no sea vacia. Pero si s&iacute; esta vacia, va directio al submit para que el resto de las validaciones se hagan en el controller */
		if(fecha != ''){
			/* Si la fecha es valida, entonces s&iacute; hacemos el submit */
			if(ER_FECHAS.test(fecha)){
				$('#busquedaPersonaMoralForm').submit();
				$('#fechaCreacionErrorCliente').hide();
			}else{
				$('#fechaCreacionErrorCliente').show();
			}
		}else{
			$('#fechaCreacionErrorCliente').hide();
 			$('#busquedaPersonaMoralForm').submit();
		}
		
	});

	var dtPersonasMorales;
	/* Configuracion del data table de pers morales*/
	dtPersonasMorales = $('#tablaResultadoMorales').dataTable({
		"sPaginationType": "full_numbers",
		sScrollX: "100%",
        bJQueryUI : true,
        bFilter : false,
        bInfo:true,
        bSort: false,
        "bPaginate": true,
        "bAutoWidth" : true,
        "bServerSide" : true,
        "iDeferLoading" : 0,
        //"sPaginationType": "full_numbers",
        "aoColumns" : [
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
                           "mDataProp" : "tipoSociedad.descripcionAbreviada",
                           "sClass":"dtJustifyClassColumn"
                       },
                       { 
                           "sTitle" : "Acta Constitutiva",
                           "mDataProp" : "actaConstitutiva",
                           "sClass":"dtJustifyClassColumn"
                       },
                       { 
                           "sTitle" : "Fecha de Creaci&oacute;n",
//                         "mDataProp" : "fechaCreacion",
							"mDataProp" : "fechaCreacionFormateada",
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
/* HABILITAR ESTE COMENTARIO HASTA QUE EXISTAN LAS TABLAS EN LA BDU *
                       { 
                           "sTitle" : "Situaci&oacute;n",
                           "mDataProp" : "desSituacion",
                           "sClass":"dtJustifyClassColumn"
                       },                      
                       { 
                           "sTitle" : "Est&aacute;tus",
                           "mDataProp" : "desEstatus",
                           "sClass":"dtJustifyClassColumn"
                       }
/**/
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
										    "aTargets": [ 9 ]
										}
				],

            "bProcessing" : true,
            "sAjaxSource" : context_path + '/persona/moral/busqueda/bdu', 		// url original
            "fnServerData" : function(sSource, aoData, fnCallback) {
                
                fnHideErrores("#busquedaPersonaMoralForm");
                
                if(cleanDatable == false){
                	 aoData.push({
                         "name" : "sSearch",
                         "value" : ''
                     });
                     var wrapper = new Object();
                     wrapper.aoData = aoData;
                     var oForm = $('#busquedaPersonaMoralForm').toObject();
                     wrapper.oForm = oForm;
                     
                     $.postJSON(sSource, wrapper, function(data) {
                         fnCallback(data);
                     }).error(function(data) {
                         
                         fnProcesarErrores(data, "#busquedaPersonaMoralForm");
                         fnCallback(dataEmpty);
                     });
                }else{
                    fnCallback(dataEmpty);
                }
               
            }
	});
	
	$('#tramiteRegistroPersonasMorales').click(function(){
		/* Con esto abrimos la pagina de registro de personas morales yendo primero al controller para setear en la sesion el rol "ventanilla" */
		window.open(context_path + "/persona/tramites/agregar/moral/ventanilla"); // rol "ventanilla"
	});

	$('#busquedaPersonaMoralForm').submit(function() {
		cleanDatable = false;
		//Previene el error de las pantallas adelantadas de la busqueda
		resetDisplayStart(dtPersonasMorales);
		//Solicita de nuevo la busqueda
		dtPersonasMorales.fnDraw();
		return false;
	});

});

var objDialogoCtrl = {
	dialogo : {}
};

function mostrarPopUpDatosComplementarios(idPersona){

	var url = context_path + "/persona/moral/datos-complementarios/busqueda/" + idPersona;
	var d = $('#dgModalDatosComplementarios').html('<iframe id="site" src="' + url + '" width="100%" height="100%" frameborder="0" />');
	
     // Configuracion del dialogo
     objDialogoCtrl.dialogo = d.dialog({
        title: 'Datos Complementarios de Persona Moral',
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
