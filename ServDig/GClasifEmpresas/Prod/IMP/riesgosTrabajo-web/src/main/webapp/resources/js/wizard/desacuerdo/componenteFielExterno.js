var componenteFirmaElectronica = {
	defaults: {
		tipo_operacion :'firmaCMS',
		acuse:'AcuseV1.0',
		rfc: "",
		validarRFC :true,
		curp: "",
		firma_archivo : false,
		min_archivos : 0,
		max_archivos : 0,
		fechaElectronica : null,
		cad_original:"",
		registroPatronal : "",
		nombreCompleto : "",
		idTipoSolicitud : "",
		descripcionTipoSolicitud : "",
		folioSolicitud : "",
		idTipoTramite : []
	}, 
	callback: null,
	mensajeError: {
		titulo: 'Error',
		mensaje:"La validaci&oacute;n de la firma no pudo ser realizada",
		buttons: {
			'Aceptar': dialogosCtrl.close
		}
	},
	firmarTramite: function(opciones) {
		
		var existeComponenteFirma = parent.FirmaDigitalCtrl != undefined,
		componenteFirma= $.extend({}, componenteFirmaElectronica.defaults, opciones);

		parent.FirmaDigitalCtrl.setOnCloseCallback(function() {

			if(parent.FirmaDigitalCtrl.datosSalida == null) {
				dialogosCtrl.abrirDialogo(componenteFirmaElectronica.mensajeError);
			}else {
				if(parent.FirmaDigitalCtrl.datosSalida.Resultado == 0) {
					var firmaResponse = {
							cadenaOriginal               : parent.FirmaDigitalCtrl.datosSalida.contenedores[0].cadori,
							recibo                       : parent.FirmaDigitalCtrl.datosSalida.firmas[0],
							reciboNotarial               : parent.FirmaDigitalCtrl.datosSalida.folio,
							urlAcuseFirma                : parent.FirmaDigitalCtrl.datosSalida.acuse,
							serialCertificado            : parent.FirmaDigitalCtrl.datosSalida.serie_cert,
							strIniciaVigenciaCertificado : parent.FirmaDigitalCtrl.datosSalida.vigIni,
							strFinVigenciaCertificado    : parent.FirmaDigitalCtrl.datosSalida.vigFin
					};

					componenteFirmaElectronica.callback(firmaResponse);
				} else {
					dialogosCtrl.abrirDialogo(componenteFirmaElectronica.mensajeError);
				}
			}
		});


		parent.iniciarFirmaDigital(componenteFirma);
	}
		
}