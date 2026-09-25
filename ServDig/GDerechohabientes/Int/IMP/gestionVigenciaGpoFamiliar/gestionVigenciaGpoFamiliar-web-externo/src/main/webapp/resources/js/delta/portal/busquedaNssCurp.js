/**
 * 
 */

$(document).ready(
	function() {
		
		$("#buscar").click(function() {
			validarFormulario();
		}); 
		
		$("#buscarPersona").click(function() {
			$.ui.dialog.maxZ = 20000;
			BusquedaNssCtrl.init("busquedaNss");
			BusquedaNssCtrl.setOnCloseCallback(callBackDatosPersona); 
			BusquedaNssCtrl.abrir();
		});
		
		$("#limpiar").click(function() {
			limpiar();
		});
		
	}
);

function validarFormulario() {
	fnHideErrores("form#asignacion");
	var asignacion = $("#asignacion").toObject();
	var url= '/gestionVigenciaGpoFamiliar-web-externo/busqueda/validaciones';
	
	$.blockUI();
	$.postJSON(url, asignacion, function(data2) {
		$("#asignacion").attr("action","/gestionVigenciaGpoFamiliar-web-externo/busqueda/asegurado");
		$("#asignacion").submit();
	}).error(function(data){
		$.unblockUI();
		fnProcesarErrores(data, "form#asignacion");
	});
}

function limpiar() {
	if($("#error").length) {
		$("#error").hide();
	}
	$("#asignacion").clearForm();
}

var callBackDatosPersona = function(){
    
	var p = this;
	
	if(!jQuery.isEmptyObject(p)){
		setPersonaCommon(p);
		p=null;		
	}			
} 

function setPersonaCommon(asignacion) {
	
	$("#idPersona").val(asignacion.idPersona);
	//console.debug("idpersona : %s", asignacion.idPersona);
	$("#idAsignacionNSS").val(asignacion.idAsignacionNSS);
	//console.debug("isAdignacion : %s", asignacion.idAsignacionNSS);
	$("#curp").val(asignacion.curp);
	//console.debug("curp : %s", asignacion.curp);
	$("#nss").val(asignacion.nss);
	//console.debug("nss : %s", asignacion.nss);
	$("#asignacion").attr("action","/gestionVigenciaGpoFamiliar-web-externo/busqueda/asegurado");
	
	$("#asignacion").submit();
}