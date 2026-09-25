var idUMFInicial;
var fnCallback;
var umfSeleccionada;

function initComponenteUMFs() {
	if (idUMFInicial != null && typeof idUMFInicial !== 'undefined'
			&& idUMFInicial != '') {
		$("input[name='idUmfRadio'][value='" +  idUMFInicial + "']").attr('checked', 'checked');
		ejecutarCallback();
	} else if ($( "input[name=idUmfRadio]:radio").length == 1) {
		$( "input[name=idUmfRadio]:radio").attr('checked', 'checked');
		ejecutarCallback();
	}
	
}

function setFnCallback(funcion) {
	fnCallback = funcion;
}

function ejecutarCallback(){
	armarValorUMF();
	fnCallback();
}

// Función que arma el valor de la UMF seleccionada
function armarValorUMF() {
	var idUMF  = $("input[name='idUmfRadio']:checked").val();
	
	if(typeof idUMF !== 'undefined' && idUMF != null && idUMF != "") {
		var noEconomico = $('#noEconomicoUMF' + idUMF).val(); 
		
		var idDelegacion = $('#idDelegacion' + idUMF).val();
		var cveDelegacion = $('#cveDelegacion' + idUMF).val();
		
		var idSubdelegacion = $('#idSubdelegacion' + idUMF).val();
		var cveSubdelegacion = $('#cveSubdelegacion' + idUMF).val();
		
		var cveCiz = $('#cveCiz' + idUMF).val();
		
		umfSeleccionada = idUMF + '|' + noEconomico + '|' + idDelegacion + '|'
		+ cveDelegacion + '|' + idSubdelegacion + '|' + cveSubdelegacion
		+ '|' + cveCiz;
	} else {
		umfSeleccionada = null;
	}
		
}