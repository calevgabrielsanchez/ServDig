var regPatronal,
tipoPersona,
delegacion,
subdelegacion,
idSolicitud,
dtSolicitudesConcluidas;

/*Se ejecuta hasta que la pagina se carga completamente*/
$(window).load(function(){
	limpiarMensaje();
	initDatable();
	$.ajaxSetup({async:true});		
});

/*Se ejecuta al momento en el que el DOM esta listo.*/
$(document).ready(function() {
	/*configuracion del submit de la forma de filtros*/
	$('#boton').click(function(){
		buscar();
	});

	document.getElementById("cenefa").innerHTML = "Inicio» Ver Clem con firma";

	$.ajaxSetup({async:false});

});

function buscar(){
	limpiarMensaje();
	
	if (dtSolicitudesConcluidas) {
		dtSolicitudesConcluidas.fnDraw();
	} else {
		var oTable = $('#tableSolicitudesConcluidas').dataTable();  
		var oSettings = oTable.fnSettings();
		oSettings._iDisplayStart = 0;
		oTable.fnDraw();
	}
	
}


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
				{"sTitle" : "Nombre o raz&oacute;n social", "mDataProp" : "nombreRS",          "sClass":"dtJustifyClassColumnTiny"},
				{"sTitle" : "Fecha de presentaci&oacute;n", "mDataProp" : "fecPresentacion",    "sClass":"dtCenterClassColumnTiny"},
				{"sTitle" : "Estado",                       "mDataProp" : "status",            "sClass":"dtCenterClassColumnTiny"},
				{"sTitle" : "Delegaci&oacute;n",            "mDataProp" : "delegacion",        "sClass":"dtCenterClassColumnTiny" },
				{"sTitle" : "Subdelegaci&oacute;n",         "mDataProp" : "subdelegacion",     "sClass":"dtCenterClassColumnTiny" },
				{"sTitle" : "Tr&aacute;mite",               "mDataProp" : "tipoTramite",       "sClass":"dtCenterClassColumnTiny" },
				{"sTitle" : "Acci&oacute;n",                "mDataProp" : "acuse",             "sClass":"dtCenterClassColumnTiny" },

				],"aoColumnDefs": [
					{								
						"fnRender": function ( oObj ) {
							
//							retVal = '<a href="' + oObj.aData['acuse']+ '"  target="_blank> <input class="botonDDiv" id="VerClemF" type="button" value="Ver Clem"></a>';

//							retVal = '<a href="' + oObj.aData['acuse']+ '"  target="_blank"> <button id="verClemF" class="mbotonSmallText" name="verClemF">VER CLEM</button></a>';

							retVal = '<a href="' + oObj.aData['acuse']+ '"  target="_blank">VER CLEM</a>';

							return retVal;
						},
						
//						var idBoton = 'btnControl' + oObj.aData['idSolicitud'],
//						retVal = '', idEstatus = oObj.aData['idStatus'];
//
//						if(idEstatus != CANCELADO_GCE && idEstatus != CANCELADO_BAJA) {
//							retVal = '<button id="' + idBoton+ '"  class="mbotonSmallText" name="btnAsignar" onclick="verDetalleDictamen('+oObj.aData['cveIdPatronDictamen']+
//							',\''+oObj.aData['registroPatronal']+'\','+oObj.aData['cveIdPatronSujetoObligado']+','+oObj.aData['idSolicitud']+',\''+oObj.aData['rfc']+'\','+oObj.aData['idEjercicio']+')"  > Ver detalle</button> ';
//						}
//						return retVal;						
						
						"aTargets": [ 7 ]
					}								
				],
				

				"bProcessing" : true,
				"sAjaxSource" : context_path + '/modulo/firma/paginar/clem/firmada',
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
						$.unblockUI()
						fnCallback(data);
					}).error(function(data) {
						$.unblockUI()
						fnProcesarErrores(data, "#formFiltros");
						fnCallback(dataEmpty);
					});

				}
		});
		
		$('#divBtn').show();
	}else{
		$('#divBtn').hide();
	}


}

function limpiarMensaje() {
	$("#mensaje").text('');
	$("#mensaje").hide();
}
