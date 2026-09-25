var objeto;
var USUARIO_INTERNET=5;
var NORMATIVO=12;
$(document).ready(function(){
	var rolUsuario=recuperarRolUsuario();
	if(rolUsuario==USUARIO_INTERNET || rolUsuario==NORMATIVO){
		$("#anexaDocumentos").hide();
	}else{
		$("#anexaDocumentos").show();
	}
});


function buscaTramite(){
	
	var visorDialog = $('#visor').dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 890,
		height: 400,
		buttons: {
			"Aceptar": function() {
				$(this).dialog("close"); 
				$('#firmaIframe').hide(); 
			}
		}
	});

	
	if($("#idFolio").val()!="" && $( "input:checked" ).val() != undefined){
		var tramitePresentado = new Object();
		tramitePresentado.nuFolio = $("#idFolio").val();
		tramitePresentado.cveTramite = $("input:checked").val();
		
		var idTramite = 0;
		
		$.postJSON("visor/buscaTramitePresentado.do", tramitePresentado, function(data){
			if(data != null){
				idTramite = data.idTramiteRefNotaria;
				$('form#forma #idTramite').val('{"tramite":"'+ idTramite+'"}');				
				$('#forma').submit();
			}else{
				alert("No se encontraron tramites para este folio");
			}
		}).error(function(data){
			
		}).complete(function(data){
			if(idTramite != 0){
				visorDialog.dialog('open');
				$('#firmaIframe').show();
			}
		});
	}else{
		alert("Debe introducir el numero de folio y seleccionar una opcion");
	}
}


function buscaTramiteAcuse(){
	var visorDialog = $('#visor').dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 890,
		height: 400,
		buttons: {
			"Aceptar": function() {
				$(this).dialog("close"); 
				$('#firmaIframe').hide(); 
			}
		}
	});

	
	if($("#idFolio").val()!="" && $( "input:checked" ).val() != undefined){
		var tramitePresentado = new Object();
		tramitePresentado.nuFolio = $("#idFolio").val();
		tramitePresentado.cveTramite = $("input:checked").val();
		
		var idTramite = 0;
		
		$.postJSON("visor/buscaTramitePresentado.do", tramitePresentado, function(data){
			if(data != null){
				idTramite = data.idTramiteRefNotaria;
				$('form#formaAcuse #idTramite').val('{"tramite":"'+ idTramite+'"}');				
				$('#formaAcuse').submit();
				
				if(data.urlAcuseFirma!=null && data.urlAcuseFirma!='null'){
					window.open(data.urlAcuseFirma,'', "scrollbars=1,height=500,width=700");
				}				
			}else{
				alert("No se encontraron tramites para este folio");
			}
		}).error(function(data){
			
		}).complete(function(data){
			if(idTramite != 0){
				visorDialog.dialog('open');
				$('#firmaIframe').show();
			}
		});
	}else{
		alert("Debe introducir el numero de folio y seleccionar una opcion");
	}
}



function recuperarRolUsuario(){
	
	var cveRol;
	$.postJSON_Sync("solicitud/correcion/consultarRolUsuario.do", null, function(data) {
		cveRol=data.cveRol;
		desbloquear();
		
	});
	return cveRol;
}