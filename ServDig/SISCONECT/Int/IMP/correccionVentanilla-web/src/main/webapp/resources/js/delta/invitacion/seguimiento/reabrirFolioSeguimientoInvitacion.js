function deshabilitaCamposReabirFolioSITAB() {
	$("form#formReabrirFolioSITAB #reqRefenciaReaFolioSITAB").css("display", "none");
	$("form#formReabrirFolioSITAB #reqFechaReaFolioSITAB").css("display", "none");	
}

function validaGuardarReabrirFolioSITAB() {
	$("form#formReabrirFolioSITAB #reqRefenciaReaFolioSITAB").css("display", "none");
	$("form#formReabrirFolioSITAB #reqFechaReaFolioSITAB").css("display", "none");
	
	var refReaperturaSITAB = $("form#formReabrirFolioSITAB #referenciaReaFolioSITAB").val();
	var fechaReaperturaSITAB = $("form#formReabrirFolioSITAB #fechaReaFolioSITAB").val();
	
	if (refReaperturaSITAB=='') {
		$("form#formReabrirFolioSITAB #reqRefenciaReaFolioSITAB").css("display", "block");
	} else if (fechaReaperturaSITAB=='') {
		$("form#formReabrirFolioSITAB #reqFechaReaFolioSITAB").css("display", "block");
	} else {
		guardarReabrirFolioSITAB();
	}
	
}

function guardarReabrirFolioSITAB() {
	if(confirm("La acción que se va a realizar anulara la información de la derivación a Fiscalización ¿Desea continuar con el proceso?")) {
		
	} 	
}