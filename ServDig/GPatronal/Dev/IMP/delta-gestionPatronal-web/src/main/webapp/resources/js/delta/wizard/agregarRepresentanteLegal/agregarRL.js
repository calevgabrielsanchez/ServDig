var urlAgregarRL	= context_path + '/wizard/alta/representeLegal/';
var rfc = 'rfc';
var rfcError = 'rfcError';
var curp = 'curp';
var curpError = 'curpError';
var cveTipoPoder = 'cveTipoPoder';
var idTipoPoder = 'idTipoPoder';
var idTipoPoderError = 'idTipoPoderError';
var errorNegocio = 'errorNegocio';
var busquedaPersona = 'busquedaPersona';
var msgRequerido = 'Campo requerido';
	
$(document).ready(function() {
	
	$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
	
	$('form#'+busquedaPersona+' input#'+rfc).live('blur', function() {
		quitarEspaciosRfc();
	});
	
	$('form#'+busquedaPersona+' input#'+curp).live('blur', function() {
		quitarEspaciosCurp();
	});
	
	$('form#'+busquedaPersona+' input').keypress(function(event) {
	    if (event.which == 13) {
	        event.preventDefault();
	        quitarEspaciosRfc();
	        quitarEspaciosCurp();
	    }
	});
	
	$('#localizarRL').click(function() {
		buscarRfc();
	});
	
	$('#cerrarWizard').click(function() {
		cerrarWizard();
	});
	
	$('#concluirWizard').click(function() {
		concluirWizard();
	});
	
	fnHideErrores('form#'+busquedaPersona);
	
});

function quitarEspaciosRfc() {
	var valRfc = $.trim($('#'+rfc).val().toUpperCase());
	$('#'+rfc).val(valRfc);
}

function quitarEspaciosCurp() {
	var valCurp = $.trim($('#'+curp).val().toUpperCase());
	$('#'+curp).val(valCurp);
}

function buscarRfc() {
	$.blockUI();
	fnHideErrores('form#'+busquedaPersona);
	$('#'+errorNegocio).html("");	
	var url = urlAgregarRL + 'validaciones';	
	var vTipoPoder = ''+$('#'+idTipoPoder).val();
	var vRfc = $('#'+rfc).val().trim();
	var vCurp = $('#'+curp).val().trim();
	var error=false;
	
	if(vRfc == ''){
		fnShowError('#'+rfcError , msgRequerido);
		error=true;
	}
	if(vCurp == ''){
		fnShowError('#'+curpError , msgRequerido);
		error=true;
	}	
	if(vTipoPoder == '-1'){
		fnShowError('#'+idTipoPoderError , msgRequerido);
		error=true;
	}
	if(error){
		$.unblockUI();
		return;		
	}
	
	$("#tipoPoder\\.idTipoPoder").val(vTipoPoder); 
	var oForm = $('form#'+busquedaPersona).toObject();
	$.postJSON(url, oForm, function(data2) {
		if(data2.negocio.errorFormGeneral == null){
			
			submitBusquedaRL();
			
		} else {
			muestraErrorNegocio(data2.negocio.errorFormGeneral);
			$.unblockUI();
		}
	}).error(function(data){
		fnProcesarErrores(data, 'form#'+busquedaPersona);
		parent.WizardAgregarRLCtrl.limpiarElementosSesion();
		$.unblockUI();
	});
}

function muestraErrorNegocio(mensaje) {
	
	var errorG = '<div class="alert alert-danger">' +
	'<button type="button" class="close" data-dismiss="alert">x</button>' +
	'<strong>Error: </strong>' + mensaje + '</div>';
	
	$("#errorNegocio").html(errorG);
}

function submitBusquedaRL() {
	setTimeout(function(){		
		$("#busquedaPersona").submit();
	}, 200);
}

function cerrarWizard() {
	parent.WizardAgregarRLCtrl.cerrar();
}

function concluirWizard() {	
	//Los datos se obtiene de "agregarRL.jsp"
	var datos = {
		idPersona :  $("#rlIdPersona").val(),
	    rfc :  $("#rlRfc").val(),
	    //curp : $("#rlCurp").val(), 
	    nombre :  $("#rlNombre").val(),
		primerApellido :  $("#rlAPaterno").val(),
		segundoApellido :  $("#rlAMaterno").val()
	};	
	var _dataTableRLs = parent.WizardAgregarRLCtrl.config.dataTable;
	agregarRL(_dataTableRLs, datos);
	cerrarWizard();
}
