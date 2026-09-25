/**
 *Fecha UM    : 21 de Octubre del 2021
 *Version UM  : 3.2
 *Autor UM    : Erika Gutierrez
 *Descripcion : : Cambio para consultar acuses que no se pueden visualizar en SIROC
 *
 */
$(document).ready(function() {

	var tableConfig = {
		"lengthMenu" : "Mostrar _MENU_ registros por página",
		"zeroRecords" : "No se han encontrado registros",
		"info" : "Mostrando página _PAGE_ de _PAGES_",
		"infoEmpty" : "No hay registros disponibles",
		"infoFiltered" : "(filtrada a partir de _MAX_ registros totales)",
		"search" : "Buscar:",
		"paginate" : {
			"previous" : "Anterior",
			"next" : "Siguiente",
			"first" : "Primer página",
			"last" : "Última página"
		}
	};
	
	$('#tblSubcontratos').DataTable({
		"language" : tableConfig
	});
	
	if($('#tblSubcontratos').length){
		$('#tblSubcontratos').removeClass('dataTable');
		$('.table thead tr th').css('border-bottom','1px solid #000');
	}
	
	$('#tblRegistrosPatronales').DataTable({
		"language" : tableConfig
	});
	
	$('#btnReporteDatosObra').click(function() {
		
//		var accion = "/sdroc_web/resumenObra";
		var path = "/sdroc_web/getResumenObraPDF";
		var cveObra = $("#cveInformacionObra").val();
//		var data = {};
//		data = {
//				cveInformacionObra : cveObra				
//		};
//		$.ajax({
//			type : "POST",
//			contentType : "application/json",
//			async : false,
//			url : accion,
//			data : JSON.stringify(data),
//			timeout : 100000,
//			success:function(response) { 
//				console.log(">>>Reporte:  "+response);
				$("#pnlReporteDatosObra").removeClass("hidden");
				$('#pnlRepResumenObra').attr("src", path);	
				$("#pnlRepResumenObra").removeClass("hidden");
				$("#btnReporteDatosObra").addClass("hidden");

				
//			},
//			error:function(response){
//				resultado =  false;
//			}
//		});
	});

});

function mostrarAcusesContrato(select){
	console.log("cambio el select")
		var accion = null;
		var numRegObra = null;
		var $selectAtual = $(select);
		
		var tds = $selectAtual.parent().parent().children();
		var numeroObra = $(tds[1]).find('a').text();
		console.log("#OBRA:"+numeroObra);
		
		var $optionSelected = $selectAtual.find(":selected");
		accion = $optionSelected.attr("value");

		var idaction = obtenerAccion(accion);
		if (idaction > 0) {

			if (idaction == 1) {
				$("#visorAcusesReporte").addClass("hidden");
				var secuenciaNotaria = $optionSelected.attr("id");
				console.log("la secuencia de notaria es " + secuenciaNotaria);
				$selectAtual[0].selectedIndex = 0;
				location = accion;
			} else {
				numRegObra = $optionSelected.attr("id");
				var path = accion + numRegObra + "/" + idaction;
				console.log("accion : " + " " + path);
				$.ajax({
					type : "POST",
					contentType : "plain/text",
					url : "/sdroc_web/guardarNumeroObra/"+numeroObra,
					timeout : 100000,
					cache: false,
					success : function() {
						console.log("SUCCESS: guardarNumeroObra");
					},
					error : function() {
						console.log("ERROR: guardarNumeroObra");
					}
				});
				$.ajax({
					type : "POST",
					contentType : "application/json",
					url : path,
					// data : JSON.stringify(numRegObra),
					timeout : 100000,
					cache: false,
					success : function() {
						console.log("SUCCESS: ");
						location = path;
					},
					error : function() {
						console.log("ERROR: ");
					}
				});
			}

		}
		
		function obtenerAccion(accion) {

			if (accion.search('Selecci') != -1) {
				return 0;
			} else if (accion.search('acu') != -1) {
				return 1;
			} else if (accion.search('sub') != -1) {
				return 2;
			}
		}
	}

function mostrarAcusesNotaria(secuenciaNotaria) {

	if (!secuenciaNotaria) {
		alert("No Se cuenta con datos notariales");
		return;
	}

	var baseUrl = window.location.origin;
	console.log("la url del server name es: " + baseUrl);

	var urlServicio;
	var visorBase;

	if (baseUrl.includes('serviciosdigitales-stage')) {
		urlServicio = "http://serviciosdigitales-stage.imss.gob.mx/firmaElectronicaWeb/chfecynWSArchivos/rest/consultarListaArchivosSeguimiento";
		visorBase = "http://firmadigitalqa.imss.gob.mx";
	} else {
		urlServicio = "https://serviciosdigitales.imss.gob.mx/firmaElectronicaWeb/chfecynWSArchivos/rest/consultarListaArchivosSeguimiento";
		visorBase = "https://firmadigitalssl.imss.gob.mx";
	}
	console.log("**URL servicio:  ", urlServicio);

	$.ajax({
		type: "POST",
		url: urlServicio,
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		data: {
			params: JSON.stringify({
				tramite: secuenciaNotaria
			})
		},
		timeout: 360000,
		success: function(response) {
			var data;

			try {
				data = (typeof response === "string") ? JSON.parse(response) : response;
			} catch (e) {
				console.error("Error parseando respuesta", e);
				alert("Error al procesar los acuses");
				return;
			}

			if (!data || data.length === 0) {
				alert("No se encontraron acuses");
				return;
			}

			var idSeguimiento = null;
            var ultimaEtapa = 0;
            
			for (var i = 0; i < data.length; i++) {

				var etapa = parseInt(data[i].etapa, 10);

				if (!isNaN(etapa) && etapa > ultimaEtapa) {

					var urlOriginal = data[i].urlVisorDocumento;
					var posicion = urlOriginal.indexOf("idSeguimiento=");

					if (posicion != -1) {

						idSeguimiento = urlOriginal.substring(
							posicion + "idSeguimiento=".length
						);
						ultimaEtapa = etapa;
					}
				}
			}
            
            console.log("Última etapa mayor a 0: " + ultimaEtapa);
			console.log("idSeguimiento seleccionado: " + idSeguimiento);

			if (!idSeguimiento) {
				alert("No se encontró acuse con etapa válida");
				return;
			}

			var urlFinal = visorBase + "/firmaElectronicaWeb/chfecynAcuseApp/view?idSeguimiento=" + idSeguimiento;
			console.log("URL final visor: ", urlFinal);
			window.open(urlFinal, "_blank");

		},
		error: function(xhr, status, error) {

			console.error("status:", status);
			console.error("error:", error);
			console.error("response:", xhr.responseText);

			if(status === "timeout"){
				alert("El servicio tardó demasiado en responder");
			}else{
				alert("No fue posible consultar los acuses");
			}
		}
	});
}

