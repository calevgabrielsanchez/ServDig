var sIdRepLegal = "#repLegal";
var dialogoTramiteRazonSocial;
var idDialogoTramiteDenominacionSocial = "#tramiteDenominacionSocial";
var idDialogoTramiteDatosContacto = "#tramiteDatosContacto";
var idDialogoTramiteEscrituraConstitutiva = "#tramiteEscrituraConstitutiva"
var idDialogoTramiteSindicato ="#tramiteSindicato";


$(document).ready(function() {
	dialogoTramiteRazonSocial =  $(idDialogoTramiteDenominacionSocial).dialog({
		autoOpen:false,
		resizable: false,
		height: 530,
		width: 620,
		modal: true
	});
	
	dialogoTramiteDatosContacto =  $(idDialogoTramiteDatosContacto).dialog({
		autoOpen:false,
		resizable: false,
		height: 530,
		width: 620,
		modal: true
	});
	
	dialogoEscrituraConstitutiva =  $(idDialogoTramiteEscrituraConstitutiva).dialog({
		autoOpen:false,
		resizable: false,
		height: 630,
		width: 620,
		modal: true
	});
	
	dialogoTramiteSiondicato = $(idDialogoTramiteSindicato).dialog({
		autoOpen:false,
		resizable: false,
		height: 530,
		width: 620,
		modal: true
	});
});

var fnLoadRepLegal = function(){
	// referencia a cveIdpatronSujetoObligado
	var cveIdPatronSujetoObligadoHidden = $("#datosGeneralesFrom1 #cveIdPatronSujetoObligadoHidden").val();
	//var urlRepLegal =  cveIdPatronSujetoObligadoHidden + "/representanteLegal" // ruta del controller
	var urlRepLegal =  "representanteLegal?regPat="+ cveIdPatronSujetoObligadoHidden;// ruta del controller
		$.get(urlRepLegal , function(data){
			$(sIdRepLegal).html(data);
		})
}

function abrirTramiteDenominacionSocial(){
	dialogoTramiteRazonSocial.dialog('open');
}

function abrirTramiteDatosContacto(){
	dialogoTramiteDatosContacto.dialog('open');
}

function abrirTramiteEscrituraConstitutiva(){
	dialogoEscrituraConstitutiva.dialog('open');
}

function abrirTramiteSindicato(){
	dialogoTramiteSiondicato.dialog('open');
}