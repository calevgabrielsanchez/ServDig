var urlWizardSocios		= context_path + '/wizard/tramite/socios/';
var idFormaSocios		= 'form#socio';
$(document).ready(function() {
	
	// Se incializa el blockUI para las peticiones AJAX
	$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
	
	$('#siguienteBusqueda').click(function() {
		validarFiltrosBusqueda();
	});
	
	$('#cerrarWizard').click(function() {
		fnHideErrores(idFormaSocios);
		inicializarValoresFiltros();
		cerrarWizard();
	});
	
	$("form#socios input#rfc").live('blur', function() {
		quitarEspaciosRfc();
	});
	
	$("form#socios input#curp").live('blur', function() {
		quitarEspaciosCurp();
	});
	
});

$(function() {
	$('#tipoSocio\\.idTipoPersona').change(function () {  
    	fnHideErrores(idFormaSocios);
    	$('div#seccionRfc').hide();
    	$('div#seccionCurp').hide();
    	if($(this).val() == ""){
    		inicializarValoresFiltros();
        }else{
        	$('input#rfc').focus();
        	if($(this).val() == "1"){
        		$('div#seccionRfc').show();
        		$('div#seccionCurp').show();        		
        	}else{
        		$('div#seccionRfc').show();
        		$("#curp").val('');
        	}        	
        }
    	setSizeWithinIframe(document);
    });
});



function validarFiltrosBusqueda() {
	var socio = $(idFormaSocios).toObject();
	var url = urlWizardSocios+'validaciones';
	var socios = new Object();	
	fnHideErrores(idFormaSocios);
	$.postJSON(url, socio, function(data2) {
		//No existen errores de validación
		setTimeout(function(){
			$.blockUI();
			$(idFormaSocios).submit();
		}, 200);			
	}).error(function(data){		
		fnProcesarErrores(data, idFormaSocios);
	});
}


function cerrarWizard() {	
	parent.WizardAltaSociosCtrl.cerrar();
}

function quitarEspaciosRfc() {
	var valRfc = $.trim($("#rfc").val().toUpperCase());
	$("#rfc").val(valRfc);
}

function quitarEspaciosCurp() {
	var valRfc = $.trim($("#curp").val().toUpperCase());
	$("#curp").val(valRfc);
}

function inicializarValoresFiltros(){
	$("#rfc").val('');
	$("#curp").val('');
	$("#tipoSocio\\.idTipoPersona").val('');
}
