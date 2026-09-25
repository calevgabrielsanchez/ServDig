var idDtListaDomicilios = "#dtListaDomicilios";

var oDtListaDomicilios;

$(document).ready(function() {
	
});


function jsCargaUbicacionesRPS(cveInvitacionRPS) {
	
//	alert("Aca :: "+$("form#formDetalleSegInvitacion #idDetalleInvitacion").val());
	if(oDtListaDomicilios != undefined){
		oDtListaDomicilios.fnDestroy();
		  
	}
	
	oDtListaDomicilios = $(idDtListaDomicilios).dataTable(
	{
		bJQueryUI : true,
		bFilter : false,
		bInfo : true,
		bSort : false,			
		"bPaginate" : true,
		"bAutoWidth" : false,
		"bServerSide" : false,
		"bDestroy" : true,
		"aoColumns" : [ {
				"sWidth": "20%",
				"sTitle" : "Registro Patronal",
				"mDataProp" : "regPatronal",
				"sClass": "dtCenterClassColumn"
			},{
				"sWidth": "80%",
				"sTitle" : "Domicilio",
				"mDataProp" : "domicilio",
				"sClass": "dtCenterClassColumn"
			}],
				"bProcessing" : true,
				"sAjaxSource" : 'seginvitacion/consultaInvitacionesRP.do',
				"fnServerData" : function(sSource,aoData,fnCallback) {
					
					bloquear();
										
//					var sVarSeg = '{"cveInvitacion":'+cveInvitacionRPS+'}';
//					var clase = jQuery.parseJSON(sVarSeg);
//					$.postJSON("seginvitacion/detalleInvitacion.do", clase, function(data)
					
					var wrapper = new Object();
					wrapper.aoData = aoData;

					var oForm = $("#formDetalleSegInvitacion").toObject({mode : 'first'});
					wrapper.oForm = oForm;
					wrapper.oForm.folioInvitacion = cveInvitacionRPS;					

					$.postJSON(sSource,wrapper,function(data) { 
						fnCallback(data);
						desbloquear();
					 }).error(function(datas){ 
						validarSesionExpirada(datas);				 
					});
				}
			});
}

function cargaFuncionarioAutorizaCanSITAB(){	
	$.postJSON(getAppContextParaJS()+"/promocion/seguimiento/generico/consultaFuncionarioAutoriza.do", null, function(data) {		
		var comboFuncAutCanGen=document.getElementById("selFuncionarioAutCancelacionSITAB");
		if (comboFuncAutCanGen != null) {
			comboFuncAutCanGen.options.length = 1;
			// alert ("data.length="+data.length);			
			for(var i = 0 ; i < data.length ; i++){
				comboFuncAutCanGen.add(new Option(data[i][1], data[i][0]));
			}
		}
	
	});
}

function salirDetalleSeguimientoInvitacion() {
	eliminaDomicilioSesion();
	oGenDetalleSegInvitacion.dialog("close");
	consultaInvitaciones();
}



