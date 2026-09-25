
$(document).ready(function() {

	$('a[data-toggle="tab"]').on('shown.bs.tab', function(e) {
//		console.log($(e.target).attr('id'));
	});

	$('#btnRepAnualExcel').click(function(e) {
		
		var rfc = $("#lblRFCPatron").text();
		var anio = $("#tabs").parent().find("ul li.active").text()
		generarExcelReporteGeneral(rfc, anio);
	});

	$('#btnReporteGeneral').click(function(e) {
		location = '/sdroc_web/reporteGeneral';
	});

	
});

/**
 * Construccion de panel por año
 */
function buildPanel() {
	var rfc = $("#lblRFCPatron").text();

	var lustroActual = calcularLustro();
	for (var i = 0, l = lustroActual.length; i < l; i++) {

		var anio = lustroActual[i];
		var nextTab = $('#tabs li').size() + 1;
		var obrasRegistradas = consultarObrasRegistradas(rfc, anio);

		$(
				'<li><a href="#tab' + nextTab + '" data-toggle="tab">' + anio
						+ '</a></li>').appendTo('#tabs');
		$(
				'<div class="tab-pane" id="tab'
						+ nextTab
						+ '"><div style="height: 100px;"><h5><b>Reporte anual de registro de obras </b><hr></hr></h5> <div class="col-xs-12"><h6>Obras registradas durante el periodo: '
						+ obrasRegistradas
						+ '</h6></div></div></div>')
				.appendTo('.tab-content');
		$('#tabs a:last').tab('show');
	}
}

/**
 * Calcula lustro
 * 
 * @returns {Array}
 */
function calcularLustro() {
	var fecha = getDate();
	var anioActual = (fecha).getFullYear();
	var lustro = [];
	for (var i = 0, l = 5; i < l; i++) {
		lustro.push(anioActual--);
	}

	lustro.sort(function(a, b) {
		return a - b
	});
	return lustro;
}

/**
 * Consulta el total de obras registradas por año y rfc
 * 
 * @param rfc
 * @param anio
 * @returns {Number}
 */
function consultarObrasRegistradas(rfc, anio) {
	var resultado = 0;
	var path_service = "/sdroc_web/consultaObrasRegistradasAnualesPorRFC/";
	$.ajax({
		type : "GET",
		contentType : "application/json",
		async : false,
		url : path_service + rfc + "/" + anio,
		timeout : 100000,
		cache: false,
		success : function(response) {
			console.log(response.resultado);
			resultado = response.resultado;
		},
		error : function(errorResponse) {
			console.log(errorResponse);

		}
	});

	return resultado;
}

/**
 * generarExcelReporteGeneral
 */
function generarExcelReporteGeneral(rfc, anio) {
	var url = '/sdroc_web/reporteGeneralObras/exportaExcel/'+ rfc + '/' + anio;
	window.open(url, '_blank');
}