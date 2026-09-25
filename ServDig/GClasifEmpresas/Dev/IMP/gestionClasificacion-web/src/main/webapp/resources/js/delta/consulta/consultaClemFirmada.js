var regPatronal,
tipoPersona,
delegacion,
subdelegacion,
idSolicitud,
dtSolicitudesConcluidas;

var muestraAvisos = '0';

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

	document.getElementById("cenefa").innerHTML = "Inicio� Ver Clem con firma";

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
							
							if(muestraAvisos == '1'){
								retVal = '<a href="' + oObj.aData['acuse']+ '" onclick="imprimeAvisos()" target="_blank">VER CLEM</a>';								
							}else{
								retVal = '<a href="' + oObj.aData['acuse'] + '"  target="_blank">VER CLEM</a>';								
							}

							return retVal;
						},						
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

  function imprimeAvisos(){
	sleep(2000);
    //var url = context_path + '/modulo/firma/ver/downloadAvisos';
    var url = context_path + '/solicitud/downloadAvisos';
    console.log("::: En imprimeAvisos, url: " + url);
 //   var avisos = "downloadAvisos";
    window.open(url, '_blank', 'width=500,height=500,top=100,left=100,resizable=1,scrollbars=1');
  }
 
  function sleep(milliseconds) {
	  var start = new Date().getTime();
	  for (var i = 0; i < 1e7; i++) {
	    if ((new Date().getTime() - start) > milliseconds){
	      break;
	    }
	  }
  }
  
  	  function popup(mylink, windowname)
	  {
	  if (! window.focus)return true;
	  var href;
	  if (typeof(mylink) == 'string')
	     href=mylink;
	  else
	     href=mylink.href;
	  window.open(href, windowname, 'width=400,height=200,scrollbars=yes');
	  return false;
	  }	