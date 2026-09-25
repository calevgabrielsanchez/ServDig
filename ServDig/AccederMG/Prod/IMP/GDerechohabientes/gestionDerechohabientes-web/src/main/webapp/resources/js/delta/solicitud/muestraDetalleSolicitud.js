
function detalleSolicitudReg() {
	
	var ajaxSource = context_path + '/solicitud/detalleReg/';
	var solicitud = {'idSolicitud': idsolicitud};
	
	$('#documento').load(ajaxSource,solicitud);
	
	
}

