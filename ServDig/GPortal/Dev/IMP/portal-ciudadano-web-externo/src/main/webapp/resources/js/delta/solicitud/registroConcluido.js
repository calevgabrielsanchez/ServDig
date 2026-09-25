function mostrarDocumentos(div, folio){
	if (folio == null || folio == '' || typeof folio === 'undefined') {
		return false;
	}
	if (div == null || div == '' || typeof div === 'undefined') {
		return false;
	}
	DetalleSolicitudCtrl.setEsFolioNormal(false);
	DetalleSolicitudCtrl.init(div,folio);
	DetalleSolicitudCtrl.abrir();
}