
var dialogoConfirmarCancelar;
var dialogoConfirmar;
var isChrome = window.chrome;


$(document).ready(function(){
	
	FirmaDigitalCtrl.init('firmaDigitalComponent', 'doctosRequeridosTramite');
	
	$("#renovacionFiel").click(function() {
		WizardRenovacionFielCtrl.init('wizardRenovacionFiel', '','','','');
		WizardRenovacionFielCtrl.abrir();
		
	});
	
	$("#enviarForm").click(
		function() {
			
			FirmaDigitalCtrl.setOnCloseCallback(
				function() {
					if(FirmaDigitalCtrl.datosSalida && FirmaDigitalCtrl.datosSalida != null
							&& FirmaDigitalCtrl.datosSalida.Resultado == 0) {
						
						AuthenticateSSO.authenticate(FirmaDigitalCtrl.datosSalida.curp, FirmaDigitalCtrl.datosSalida.serie_cert);
						AuthenticateSSO.setOnCloseCallback(function(){
							sessionStorage.setItem('rfcCertificado', FirmaDigitalCtrl.datosSalida.rfc);
							$("#formlogin").submit();
						});
					} else {
						var mensajeError = "La validaci\u00f3n de la autenticaci\u00f3n no pudo ser realizada";
						if(FirmaDigitalCtrl.datosSalida.texto) {
						 mensajeError = "["+	FirmaDigitalCtrl.datosSalida.Resultado + "] " + FirmaDigitalCtrl.datosSalida.texto;
						}
						$('#dialog-mensajes').text(mensajeError);
						dialogMensajes.dialog('open');
					}
				}
			);

			var componenteFirma = {
				idTipoSolicitud : 3,
				descripcionTipoSolicitud : null,
				idTipoTramite : [0],
				rfc : null,
				validarRFC : true,
				cad_original : null,
				tipo_operacion : 'autentica',
				firma_archivo : false,
				min_archivos : 0,
				max_archivos : 0
			};
			iniciarFirmaDigital(componenteFirma);
		}
	);
	
});

var detectarBrowser = function() {
	
	var version = $.browser.version;
	
	if(isChrome) {
		activarLogueo(false);
	} else {
		if($.browser.msie) {
			if(version < 9) {
				activarLogueo(false);
			} else {
				activarLogueo(true);
			}
		} else if($.browser.mozilla) {
			if(version < 25) {
				activarLogueo(false);
			} else {
				activarLogueo(true);
			}
		} else if($.browser.webkit){
			if(version < 6) {
				activarLogueo(false);
			} else {
				activarLogueo(true);
			}
		} else {
			activarLogueo(false);
		}
	}
}

var activarLogueo = function(activar) {
	if(activar) {
		$("#browserSoportado").show();
		$("#divRenovacionFiel").show();
		$("#browserNoSoportado").hide();
	} else {
		$("#browserSoportado").hide(); 
		$("#divRenovacionFiel").hide();
		$("#browserNoSoportado").show();
	}
}
// Funcion inicializar la firma digital
var iniciarFirmaDigital = function (componenteFirma) {
	//Se settean los valores de entrada
	FirmaDigitalCtrl.datosEntrada.rfc = componenteFirma.rfc;
	FirmaDigitalCtrl.datosEntrada.val_rfc = componenteFirma.validarRFC;
	FirmaDigitalCtrl.datosEntrada.cad_original = componenteFirma.cad_original;
	FirmaDigitalCtrl.datosEntrada.firma_archivo = componenteFirma.firma_archivo;
	FirmaDigitalCtrl.datosEntrada.min_archivos = componenteFirma.min_archivos;
	FirmaDigitalCtrl.datosEntrada.max_archivos = componenteFirma.max_archivos;
	
	FirmaDigitalCtrl.datosEntrada.origen = server_name;
	FirmaDigitalCtrl.datosEntrada.operacion = componenteFirma.tipo_operacion;//"autentica";
	FirmaDigitalCtrl.datosEntrada.aplicacion="portalimssdigital";
	FirmaDigitalCtrl.datosEntrada.salida="rfc,curp,rfc_rl,curp_rl,serie_cert,contenedores,acuse,firmas,vigencias,vigIni,vigFin";   
	FirmaDigitalCtrl.datosEntrada.acuse = 'AcuseV1.0';

	FirmaDigitalCtrl.datosEntrada.idTipoSolicitud = componenteFirma.idTipoSolicitud;
	FirmaDigitalCtrl.datosEntrada.descripcionTipoSolicitud = componenteFirma.descripcionTipoSolicitud;
	FirmaDigitalCtrl.datosEntrada.idTipoTramite = componenteFirma.idTipoTramite;

	FirmaDigitalCtrl.datosEntrada.folioSolicitud = componenteFirma.folioSolicitud;
	FirmaDigitalCtrl.datosEntrada.curp = componenteFirma.curp;
	FirmaDigitalCtrl.datosEntrada.registroPatronal = componenteFirma.registroPatronal;
	FirmaDigitalCtrl.datosEntrada.nombreCompleto = componenteFirma.nombreCompleto;
	FirmaDigitalCtrl.datosEntrada.fechaElectronica = componenteFirma.fechaElectronica;
	FirmaDigitalCtrl.datosEntrada.afectado = componenteFirma.afectado;
    FirmaDigitalCtrl.datosEntrada.representados = componenteFirma.representados;
    FirmaDigitalCtrl.datosEntrada.domicilioActualizado= componenteFirma.domicilioActualizado;
    FirmaDigitalCtrl.datosEntrada.afectadoNuevosValores= componenteFirma.afectadoNuevosValores;
    FirmaDigitalCtrl.datosEntrada.mediosContacto= componenteFirma.mediosContacto;
    FirmaDigitalCtrl.datosEntrada.tipoAcuse= componenteFirma.tipoAcuse;
    FirmaDigitalCtrl.datosEntrada.acuse= componenteFirma.acuse;

	
	if(componenteFirma.mostrarCartaTerminos){
		FirmaDigitalCtrl.datosEntrada.mostrarCartaTerminos = componenteFirma.mostrarCartaTerminos;
	} else {
		FirmaDigitalCtrl.datosEntrada.mostrarCartaTerminos = false;
	}

	FirmaDigitalCtrl.firmaDigital();
	
};

