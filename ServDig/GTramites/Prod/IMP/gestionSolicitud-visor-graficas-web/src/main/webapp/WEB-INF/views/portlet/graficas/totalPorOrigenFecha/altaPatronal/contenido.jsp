<%@ include file="../../../../general/taglibs.jsp"%>

<script id="initPortlet">
	var _chartTotalPorOrigenAltaPatFecha;
	var urlTotalPorOrigenAltaPatFecha = "/gestionSolicitud-visor-graficas-web/portlet/grafica/totalPorOrigenFecha/altaPatronal";
	
	var labelsArrayTotalPorOrigenAltaPatFecha = ['INTERNET'];
	var colorsArrayTotalPorOrigenAltaPatFecha = ['#052C48'];
	var totalsArrayTotalPorOrigenAltaPatFecha = [0];
	
	setTimeout(function() {
		$.ajax({
			url : urlTotalPorOrigenAltaPatFecha,
			dataType : 'json',
			type : 'post',
			data : {
				fechaInicio : $('input#fechaInicioTOFAP').val(),
				fechaFin : $('input#fechaFinTOFAP').val()
			},
			success : function(objData) {
							
				$('div#totalPorOrigenAltaPatFechaChart').css("height","300px");

				_chartTotalPorOrigenAltaPatFecha = new Morris.Line({
					element : 'totalPorOrigenAltaPatFechaChart',
					data : objData,
					xkey: 'fecha',
					ykeys: ['movimientosInternet'],
					labels: labelsArrayTotalPorOrigenAltaPatFecha,
					smooth : false,
					lineColors : colorsArrayTotalPorOrigenAltaPatFecha,
					dateFormat : function(date){
						return new Date(date).toLocaleDateString();
					},
					xLabelFormat : function(date){
						return new Date(date).toLocaleDateString();
					}
				});
				
				$('div#totalPorOrigenAltaPatFechaLoading').remove();
				
				var totalMov = 0;				
				$.each( objData, function( key, value ) {
					totalMov += value.movimientosInternet;
					totalsArrayTotalPorOrigenAltaPatFecha[0] += value.movimientosInternet;
				});
				
				generarLabels('totalPorOrigenAltaPatFechaChart', 
						labelsArrayTotalPorOrigenAltaPatFecha, 
						colorsArrayTotalPorOrigenAltaPatFecha, 
						totalsArrayTotalPorOrigenAltaPatFecha);
				setTotalMovimientos('totalPorOrigenAltaPatFechaChart',totalMov);
			},
			error : function() {
				$('div#totalPorOrigenAltaPatFechaChart').html("<div style='text-align: center; vertical-align: middle;'>Ocurrió un error inesperado</div>");
			}
		});
	}, 500);
	
	$(window).resize(function() {
		_chartTotalPorOrigenAltaPatFecha.redraw();
	});
	
</script>

<div id="totalPorOrigenAltaPatFechaChart">
	<div style='text-align: center; vertical-align: middle;'
		id="totalPorOrigenAltaPatFechaLoading">
		<img alt='' src='${staticResourcesPath}/imagenes/loading.gif' />
	</div>
</div>