var regPatronal,
tipoPersona,
delegacion,
subdelegacion,
idSolicitud,
dtSolicitudesConcluidas,
sIdSelectDelegacion ="#delegacionHolder",
sIdSelectSubDelegacion ="#subdelegacionHolder",
sIdDelegacionUsuario ="#delegacionUser",
sIdSubdelegacionUsuario ="#subdelegacionUser";

/*Se ejecuta hasta que la pagina se carga completamente*/
$(window).load(function(){
	limpiarMensaje();
	initDatable();
	fnInitCombosDelegacion();
	$('form#formFiltros select#modulo').trigger('change');

	if($("#menuRepAnalisis").length) {
		document.getElementById('menuRepAnalisis').style.display='';
	}
	if($("#menuRepBitacora").length) {
		document.getElementById('menuRepBitacora').style.display='';
	}
	$.ajaxSetup({async:true});		
});

/*Se ejecuta al momento en el que el DOM esta listo.*/
$(document).ready(function() {
	/*configuracion del submit de la forma de filtros*/
	$('#boton').click(function(){
		buscar();
	});

	document.getElementById("cenefa").innerHTML = "Inicio» An&aacute;lisis y consulta";

	$.ajaxSetup({async:false});

});

function buscar(){
	limpiarMensaje();
	
	if( $('#tableSolicitudesConcluidas').length) {
		if (dtSolicitudesConcluidas) {
			dtSolicitudesConcluidas.fnDraw();
		} else {
			var oTable = $('#tableSolicitudesConcluidas').dataTable();  
			var oSettings = oTable.fnSettings();
			oSettings._iDisplayStart = 0;
			oTable.fnDraw();
		}
	} else {
		$("#formFiltros").attr("action","/gestionClasificacion-web/consulta/reportesAnalisis/generarReporteDictamen");
		$("#formFiltros").attr("method","POST");
		$("#formFiltros").submit();
	}
	
	
}


function fnSetFiltrosBusqueda(){	
	setTimeout(function(){ 
		$.blockUI();

		if($('#regPatConservarH').val() != '12345678')
			$('input:text[name="registroPatronal"]').attr('value', $('#regPatConservarH').val());			
		else
			$('input:text[name="registroPatronal"]').attr('value', '');

		var options = $('select#idEjercicio').get(0).options;
		for(index =0 ; index < options.length ; index++ ){
			var option = options[index];
			if(option.value == $('#idEjercicioConservar').val())
				option.selected = 'selected';
		}


		var rol = $("#rol").val();			
		if(rol == ROL_JEFE_DEPTO || rol == ROL_JEFE_OFICINA || rol == ROL_JEFE_VENT || rol == ROL_NORMAT
				|| ROL_DELEGADO || ROL_SUBDELEGADO || ROL_JEFE_OFICINA_COBROS
				){

			var options = $('select#idSubDelegacion').get(0).options;
			for(index =0 ; index < options.length ; index++ ){
				var option = options[index];
				if(option.value == $('#subdelegacionConservarH').val())
					option.selected = 'selected';
			}
			buscar();
		}else if( rol == ROL_NORM_CENTRAL ){

			var options = $('select#idDelegacion').get(0).options;
			for(index =0 ; index < options.length ; index++ ){
				var option = options[index];
				if(option.value == $('#delegacionConservarH').val())
					option.selected = 'selected';
			}

			if($('#delegacionConservarH').val() > 0){
				$('select#idDelegacion').change();
				setTimeout(function(){ 
					var options = $('select#idSubDelegacion').get(0).options;
					for(index =0 ; index < options.length ; index++ ){
						var option = options[index];
						if(option.value == $('#subdelegacionConservarH').val()) 
							option.selected = 'selected';
					}
					buscar();
				}, 1500);
			}else{
				buscar();
			}		     	
		}else{ // si el usuario es subdelegacional
			buscar();
		}			
		$.unblockUI();	
		return 0;
	}, 1000);		
}

function fnInitCombosDelegacion(){			
	/*inicializacion de los combos de delegacion y subdelegacion*/
	var rol = $("#rol").val();
	if(rol == ROL_JEFE_DEPTO || rol == ROL_JEFE_OFICINA ||  rol == ROL_JEFE_VENT || rol == ROL_NORMAT){
		/* Este tipo de rol son DELEGACIONALES, por lo tanto se debe de cargar el combo de las subdelegaciones
		 * 	de la delegacion que el usuario tiene asociado.
		 */
		/*Inicializamos los combos de delegacion y subdelegacion*/
		fnSetComboDelegacion($('#idDelegacion'));
	}else if( rol == ROL_NORM_CENTRAL){
		/* Este rol es de tipo NORMATIVO Nacional por lo tanto ve todo  */
		fnSetComboNormativo();
	}else{
		/* Si no es ninguno de los dos anteriores entonces es usuario Subdelegacional */
		/*Inicializamos los combos de delegacion y subdelegacion*/
		fnSetComboDelegacion($('#idDelegacion'));
		fnSetComboSubdelegacion($('#idSubDelegacion'));														
		document.getElementById('idSubDelegacion').disabled=true;	
	}				
}


/**
 * Funcion que setea las opciones de combo de delegacion y subdelegacion para los
 * usuarios con rol DELEGACIONAL, los cuales solo pueden seleccionar
 * la subdelegacion de acuerdo a la delegacion asignada
 **/
function fnSetComboDelegacion( objSelect ){
	var idDelegacion = $(sIdDelegacionUsuario).val();
	var options = objSelect.get(0).options;
	for(index =0 ; index < options.length ; index++ ){
		var option = options[index];
		if(option.value == idDelegacion){
			option.selected = 'selected';
		}
	}
	/* Mostramos el select de subdelegaciones*/
	$(sIdSelectSubDelegacion).removeClass("hiddenElement");
	$(sIdSelectSubDelegacion).addClass("showElement");
	objSelect.change();
	fnSetComboSubdelegacion($('#idSubDelegacion'));
}

/**
 * FUncion que setea las opciones de combo de delegacion y subdelegacion para los
 * usuarios con rol NORMATIVO, los cuales pueden ver tanto delegaciones como subdelegaciones
 **/
function fnSetComboNormativo(){		
	/*Mostramos los dos combos*/
	$(sIdSelectSubDelegacion).removeClass("hiddenElement");
	$(sIdSelectSubDelegacion).addClass("showElement");		
	$(sIdSelectDelegacion).removeClass("hiddenElement");
	$(sIdSelectDelegacion).addClass("showElement");		
}

/**
 * FUncion que setea las opciones de combo de delegacion y subdelegacion para los
 * usuarios con rol SUBDELEGACIONAL, los cuales no pueden seleccionar
 * delegacion o subdelegacion.
 **/
function fnSetComboSubdelegacion(objSelect){		
	var idSubDelegacion = $(sIdSubdelegacionUsuario).val();
	var options = objSelect.get(0).options;
	for(index =0 ; index < options.length ; index++ ){
		var option = options[index];
		if(option.value == idSubDelegacion){
			option.selected = 'selected';
		}
	}     	
	/* Mostramos el select de subdelegaciones*/
	$(sIdSelectSubDelegacion).removeClass("hiddenElement");
	$(sIdSelectSubDelegacion).addClass("showElement");					
}	

function verDetalleDictamen(idPatronDictamen,regPatronal, idPatronSO, idSolicitud, rfc, idEjercicio){
	console.log("el id Patorn dictamen es " + idPatronDictamen + " y el registro patronal es " + regPatronal);
	var $formularioDictamen = $("#formDictamen");
	$formularioDictamen.find("#cveIdPatronDictamen").val(idPatronDictamen);
	$formularioDictamen.find("#registroPatronal").val(regPatronal);
	$formularioDictamen.find("#cveIdPatronSujetoObligado").val(idPatronSO);
	$formularioDictamen.find("#idSolicitud").val(idSolicitud);
	$formularioDictamen.find("#rfc").val(rfc);
	$formularioDictamen.find("#idEjercicio").val(idEjercicio);
	$formularioDictamen.submit();
	$.blockUI();
};

function initDatable(){

	/* Configuracion del data table de solicitudes concluidas*/
	if( $('#tableSolicitudesConcluidas').length) {

		dtSolicitudesConcluidas = $('#tableSolicitudesConcluidas').dataTable({
			bJQueryUI : true,
			bFilter : false,
			bInfo:true,
			bSort: false,
			"bPaginate": true,
			"bAutoWidth" : false,
			"iDeferLoading": 0,
			"bServerSide" : true,
			"aoColumns" : [ 
				{"sTitle" : "Registro Patronal",            "mDataProp" : "registroPatronal",  "sClass":"dtJustifyClassColumnTiny"},
				{"sTitle" : "Nombre o raz&oacute;n social", "mDataProp" : "nombreRS",       "sClass":"dtJustifyClassColumnTiny"},
				{"sTitle" : "Ejercicio dictaminado", "mDataProp" : "ejercicio", "sClass":"dtCenterClassColumnTiny"},
				{"sTitle" : "Estado",                       "mDataProp" : "status",   "sClass":"dtCenterClassColumnTiny"},
				{"sTitle" : "Delegaci&oacute;n",            "mDataProp" : "delegacion",        "sClass":"dtCenterClassColumnTiny" },
				{"sTitle" : "Subdelegaci&oacute;n",         "mDataProp" : "subdelegacion",     "sClass":"dtCenterClassColumnTiny" },
				{"sTitle" : "Usuario",                      "mDataProp" : "usuario",           "sClass":"dtCenterClassColumnTiny" },
				{"sTitle" : "Acci&oacute;n",                "mDataProp" : "idSolicitud",    "sClass":"dtCenterClassColumnTiny" }

				],"aoColumnDefs": [ {				                	   				                	 				                	   				                	   
					"fnRender": function ( oObj ) {
						var idBoton = 'btnControl' + oObj.aData['idSolicitud'],
						retVal = '', idEstatus = oObj.aData['idStatus'];

						if(idEstatus != CANCELADO_GCE && idEstatus != CANCELADO_BAJA) {
							retVal = '<button id="' + idBoton+ '"  class="mbotonSmallText" name="btnAsignar" onclick="verDetalleDictamen('+oObj.aData['cveIdPatronDictamen']+
							',\''+oObj.aData['registroPatronal']+'\','+oObj.aData['cveIdPatronSujetoObligado']+','+oObj.aData['idSolicitud']+',\''+oObj.aData['rfc']+'\','+oObj.aData['idEjercicio']+')"  > Ver detalle</button> ';
						}
						return retVal;
					},
					"aTargets": [ 7 ]
				}
				],

				"bProcessing" : true,
				"sAjaxSource" : context_path + '/analisis/paginar/dictamenes',
				"fnServerData" : function(sSource, aoData, fnCallback) {

					fnHideErrores("#formFiltros");
					aoData.push({
						"name" : "sSearch",
						"value" : ''
					});

					var wrapper = new Object();
					wrapper.aoData = aoData;
					var oForm = $('#formFiltros').serializeObject(true);
					wrapper.oForm = oForm;
					$.blockUI();
					$.postJSON(sSource, wrapper, function(data) {
						$.unblockUI();
						fnCallback(data);
					}).error(function(data) {
						$.unblockUI();
						fnProcesarErrores(data, "#formFiltros");
						fnCallback(dataEmpty);
					});

				}
		});
	}


}

function limpiarMensaje() {
	$("#mensaje").text('');
	$("#mensaje").hide();
}