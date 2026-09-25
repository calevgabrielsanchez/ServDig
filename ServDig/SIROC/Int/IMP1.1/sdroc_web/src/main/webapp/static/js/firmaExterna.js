/**
 * 
 */
var firmaExternaCtrl = {
	firmarRegistroObra: function(callBack, tramite) {
		console.log("llamo a la firma con el tramite " + tramite);
		parent.FirmaDigitalCtrl.init("firmaDigitalComponent","");
		parent.FirmaDigitalCtrl.setOnCloseCallback(function() {
			if (parent.FirmaDigitalCtrl.datosSalida == null) {
				alert("La validacion de la firma no pudo ser realizada");
			} else {
				if (parent.FirmaDigitalCtrl.datosSalida.Resultado == 0) {
					var firmaResponse = {
							cadenaOriginal : parent.FirmaDigitalCtrl.datosSalida.contenedores[0].cadori,
							recibo : parent.FirmaDigitalCtrl.datosSalida.firmas[0],
							reciboNotarial : parent.FirmaDigitalCtrl.datosSalida.folio,
							urlAcuseFirma : parent.FirmaDigitalCtrl.datosSalida.acuse,
							serialCertificado : parent.FirmaDigitalCtrl.datosSalida.serie_cert,
							strIniciaVigenciaCertificado : parent.FirmaDigitalCtrl.datosSalida.vigIni,
							strFinVigenciaCertificado : parent.FirmaDigitalCtrl.datosSalida.vigFin,
							rfc : parent.FirmaDigitalCtrl.datosSalida.rfc
					};

					callBack(firmaResponse);
				} else {
					alert("La validacion de la firma no pudo ser realizada");
				}
			}
		});
        
        var fechaActual = getDate();
		var numObra = $("#idCveRegistroObraPrincipal").val(); 

		var cveRfc = $("#lblRFCPatron").text(),
		cveRegPatronal = $("#lblRegPatron").text(),
		refRazonSocial = $("#lblRazonSocialPatron").text().replace(/"/g,"\\\""),
		numRegistro = 0, numLicitacion = 0, cveObjetoContrato = 1,
		impEjercido = 0, impContratado = 0, cadenaOriginal = "";

		cadenaOriginal = "||Invocante:portalimssdigital%NOMBRE_TRAMITE%|%FECHA_ACTUAL1%"
		cadenaOriginal = cadenaOriginal + "%NUM_REG_OBRA%"+ "|RFC: ";
		cadenaOriginal = cadenaOriginal + cveRfc;
		cadenaOriginal = cadenaOriginal+ "|Nombre o razon social: ";
		cadenaOriginal = cadenaOriginal + refRazonSocial + "|";
		cadenaOriginal = cadenaOriginal + "Registro patronal: "+ cveRegPatronal + "||";
		
		if(tramite){
			cadenaOriginal = cadenaOriginal.replace("%NOMBRE_TRAMITE%", tramite);
		}
        
        cadenaOriginal = cadenaOriginal.replace("%FECHA_ACTUAL1%", fechaActual);
		cadenaOriginal = cadenaOriginal.replace("%NUM_REG_OBRA%", numObra);
        
		var componenteFirma = {
			tipo_operacion : 'firmaCMS', // debe ser fijo
			acuse : 'AcuseV1.0', // este es fijo
			rfc : cveRfc, // rfc de la persona que firma
			validarRFC : true,
			firma_archivo : false, // este es false
			min_archivos : 0,
			max_archivos : 0,
			fechaElectronica : getDate(),
			cad_original : cadenaOriginal,
			registroPatronal : cveRegPatronal,
			nombreCompleto : refRazonSocial,
			idTipoSolicitud : 1,
			descripcionTipoSolicitud : 'Alta',
			folioSolicitud : '',
			idTipoTramite : [ 1 ]
		};
        
        console.log("========================================== CADENA ANTES DE FIRMAR ============================================");
		console.log(cadenaOriginal);
		console.log("============================================= OBJETO COMPONENTE FIRMA =============================================");
		console.log(componenteFirma);

		parent.iniciarFirmaDigital(componenteFirma);

	}
}