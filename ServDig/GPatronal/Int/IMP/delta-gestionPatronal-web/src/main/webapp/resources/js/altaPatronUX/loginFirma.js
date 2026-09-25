/**
 * Script de control para el logue de alta patronal
 */
DIV_COMPONENTE_FIRMA = "firmaElectronica";
DIV_COMPONENTE_DOCTOS = "contenedorDoctos";
PARAMETROS_LOGIN_FIRMA = {idTipoSolicitud : 3,descripcionTipoSolicitud : null,idTipoTramite : [0],rfc : null,validarRFC : true,cad_original : null,tipo_operacion : 'autentica',
		firma_archivo : false,min_archivos : 0,max_archivos : 0};

$(document).ready(function(){
	
	FirmaDigitalCtrl.init(DIV_COMPONENTE_FIRMA,DIV_COMPONENTE_DOCTOS);
	FirmaDigitalCtrl.setOnCloseCallback(procesarFirma);
	var componenteFirma = 
	iniciarFirmaDigital(PARAMETROS_LOGIN_FIRMA);
});

var procesarFirma = function() {
	if(FirmaDigitalCtrl.datosSalida && FirmaDigitalCtrl.datosSalida != null
			&& FirmaDigitalCtrl.datosSalida.Resultado == 0) {
		
		console.log("El usuario es: " + FirmaDigitalCtrl.datosSalida.curp + ", " + FirmaDigitalCtrl.datosSalida.serie_cert);
		
		//AuthenticateSSO.authenticate(FirmaDigitalCtrl.datosSalida.curp, FirmaDigitalCtrl.datosSalida.serie_cert);
		/*AuthenticateSSO.setOnCloseCallback(function(){
			$("#formlogin").submit();
		});*/
	} else {
		$('#dialog-mensajes').text("La validaci\u00f3n de la autenticaci\u00f3n no pudo ser realizada");
		dialogMensajes.dialog('open');
	}
}