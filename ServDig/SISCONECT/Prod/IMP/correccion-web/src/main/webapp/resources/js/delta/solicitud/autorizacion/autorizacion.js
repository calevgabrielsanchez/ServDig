/**
 * JS para el soporte del catalogo de division.
 */

var idDataTable 	= "#dtPatronesInscritos";
var idDgModificar 	= "#dgPatInsModificar";


var oDtPatronesIscritos;

$(document).ready(function() {
	/**
	 * Inicializacion del data table
	 */
//	oDtPatronesIscritos = $(idDataTable).dataTable({
//		bJQueryUI : true,
//		bFilter : false,
//		bInfo:true,
//		bSort: false,
//		"bPaginate": true,
//		"bAutoWidth" : true,
//		"bServerSide" :	true,
//		"aoColumns" : [ {
//			fnRender :function(oObj){
//				var retVal = '<input type="radio" value="' + oObj.aData['nuFolio'] +'" id="radioTable" class="radioClase" name="radioTable" onclick=""/> ';
//				return retVal;
//			}, 
//			aTargets: [0]
//			},{
//				"sTitle" : "Folio" ,
//				"mDataProp" : "nuFolio",
//				"sClass": "dtCenterClassColumn"
//			}, {
//				"sTitle" : "Registro Patronal a Corregir" ,
//				"mDataProp" : "patronCorregir.registroPatronalSD",
//				"sClass": "dtCenterClassColumn"
//			}, {
//				"sTitle" : "Raz\u00f3n Social" ,
//				"mDataProp" : "patronCorregir.razonSocial",
//				"sClass": "dtCenterClassColumn"
//			}, {
//				"sTitle" : "Domicilio",
//				"mDataProp" : "patronPrincipal.domicilioCompleto",
//				"sClass": "dtCenterClassColumn"
//			}, {
//				"sTitle" : "Fecha Inicial",
//				"mDataProp" : "fechaInicial",
//				"sClass":"dtCenterClassColumn"
//			}, {
//				"sTitle" : "Fecha Final",
//				"mDataProp" : "fechaFinal",
//				"sClass":"dtCenterClassColumn"
//			}
//			],"bProcessing" : true,
//			"sAjaxSource" : 'autorizacion/paginar.do',
//			"fnServerData" : function(sSource, aoData, fnCallback) {
//				
//				
//				var wrapper = new Object();
//				wrapper.aoData = aoData;
//
//				var oForm = $("#autorizacionForm").toObject({mode:'first'});
//				wrapper.oForm = oForm;
//				
//				$.postJSON(sSource, wrapper, function(data) {
//					fnCallback(data);
//				}).error(function(data){
//					validarSesionExpirada(data);				 
//					desbloquear();
//					alert("error" + data);
//				}).complete(function(){
//					//Instrucciones para el 'complete'
//				});
//			}
//		});

	
	generaTablaAutorizaCorreccion();
	
	$("a#btnAutoriza").click(function(event){
		event.preventDefault();
		
		//var radios = document.getElementsByName("radio");
		
		
		
		var idSol = $('input[name=radioTable]:checked').val() 
		
		var sSol = '{"nuFolio":'+'"'+idSol+'"}';
		var crtSoicitud = jQuery.parseJSON(sSol);
		
		bloquear();
		
		$.postJSON("autorizacion/setSolicitud.do", crtSoicitud, function(data) {
			if(data!=null)
			{
				document.forms[0].submit();
			}
		}).error(function(data){ 
			desbloquear();
			alert("error" + data);
		}).complete(function(){
		});

		
	});


});//$(document).ready(function()

//Inicializa el paginador
function inicializaPosicionPaginador(){
	oDtPatronesIscritos.fnDisplayStart(0);
}

function paginar(){
	oDtPatronesIscritos.fnDraw();
}



function generaTablaAutorizaCorreccion(){
	
	$.postJSON_Sync("autorizacion/paginar.do", null, function(data) {		
			$(idDataTable).dataTable({
			"aaData": data.aaData,
			"bAutoWidth" : true,
			bFilter : false,
			bJQueryUI : true,
			"bDestroy": true,
			bSort: false,
			"aoColumns" : [ {
				fnRender :function(oObj){
					var retVal = '<input type="radio" value="' + oObj.aData['nuFolio'] +'" id="radioTable" class="radioClase" name="radioTable" onclick=""/> ';
					return retVal;
				}, 
				aTargets: [0]
				},{
					"sTitle" : "Folio" ,
					"mDataProp" : "nuFolio",
					"sClass": "dtCenterClassColumn"
				}, {
					"sTitle" : "Registro Patronal a Corregir" ,
					"mDataProp" : "patronCorregir.registroPatronalSD",
					"sClass": "dtCenterClassColumn"
				}, {
					"sTitle" : "Raz\u00f3n Social" ,
					"mDataProp" : "patronCorregir.razonSocial",
					"sClass": "dtCenterClassColumn"
				}, {
					"sTitle" : "Domicilio",
					"mDataProp" : "patronPrincipal.domicilioCompleto",
					"sClass": "dtCenterClassColumn"
				}, {
					"sTitle" : "Fecha Inicial",
					"mDataProp" : "fechaInicial",
					"sClass":"dtCenterClassColumn"
				}, {
					"sTitle" : "Fecha Final",
					"mDataProp" : "fechaFinal",
					"sClass":"dtCenterClassColumn"
				}
				]
			});
		
	});
}




