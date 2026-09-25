var idDgConfirmacion = "#dgGuardarInfoTrabajoConfirmacion";
var idPaso = "#hdIdPasoDenuncia";
var oDgConfirmacion;
var forma = "#frmReenviar";




$(document).ready(function() {
	
		
	oDgConfirmacion = $(idDgConfirmacion).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 930,
		beforeClose :function(event,ui){
		},
		buttons: {
			"Enviar denuncia": function() {
				
							
					var actionO =$("#infoTrabajoForm").attr("action");
					$("#infoTrabajoForm").attr("action",getAppContextParaJS() + actionO );				
					$("#infoTrabajoForm").submit();
					
						$.postJSON( getAppContextParaJS() +"/denunciaLinea/datosTrabajo/guardarInfoTrabajo.do", "", function(data) {
							  $("#infoTrabajoForm").attr("action",getAppContextParaJS() + "/denunciaLinea/datosTrabajo/guardarInfoTrabajo.do" ); 
							  $("#infoTrabajoForm").submit();
						  }).error(function(data){
								alert("error" + data);
						  });
								
							
			}, 
			"Salir": function() { 
				
				$(this).dialog("close"); 
			} 
		}
	}).error(function(data){
		alert("error" + data);
	});
});




function abrirDialogoConfirmacion(){	
	oDgConfirmacion.dialog('open');
}