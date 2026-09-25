var idMunicipioImssInicial;
var fnCallback;
var municipioSeleccionado;

function initComponenteMunicipiosImss() {
	if (idMunicipioImssInicial != null && typeof idMunicipioImssInicial !== 'undefined'
			&& idMunicipioImssInicial != '') {
		$("input[name='idMunicipioImssRadio'][value='" +  idMunicipioImssInicial + "']").attr('checked', 'checked');
	} else {
		var numeroOpciones = $("input[name='idMunicipioImssRadio']").length;
		if(numeroOpciones == 1){
			$("input[name='idMunicipioImssRadio']").attr('checked', 'checked');
			armarValorMunicipioImss();
			fnCallback();
		}
	}
}

function setFnCallback(funcion) {
	fnCallback = funcion;
}

function ejecutarCallback(){
	armarValorMunicipioImss();
	fnCallback();
}

// Función que arma el valor del Municipio Imss seleccionado
function armarValorMunicipioImss() {
	var idMunicipioImss  = $("input[name='idMunicipioImssRadio']:checked").val();
	var cvecMunicipioSINDO = $('#cvecMunicipioSINDO' + idMunicipioImss).val();
	var descMunicipio = $('#descMunicipio' + idMunicipioImss).val();
	
	var idDelegacion = $('#idDelegacion' + idMunicipioImss).val();
	var cveDelegacion = $('#cveDelegacion' + idMunicipioImss).val();
	var descDelegacion = $('#descDelegacion' + idMunicipioImss).val();
	var cveCiz = $('#cveCiz' + idMunicipioImss).val();
	
	var idSubdelegacion = $('#idSubdelegacion' + idMunicipioImss).val();
	var cveSubdelegacion = $('#cveSubdelegacion' + idMunicipioImss).val();
	var descSubdelegacion = $('#descSubdelegacion' + idMunicipioImss).val();

	municipioSeleccionado = idMunicipioImss + '|' + cvecMunicipioSINDO + '|' + descMunicipio + '|'
			+ idDelegacion + '|' + cveDelegacion + '|' + descDelegacion + '|' + cveCiz + '|'
			+ idSubdelegacion + '|' + cveSubdelegacion + '|' + descSubdelegacion;
}