<%@ include file="../../../../general/taglibs.jsp"%>

<script id="initPortlet">
	var _chartTotalPorOrigenFecha;
	var urlTotalPorOrigenFecha = "/gestionSolicitud-visor-graficas-web/portlet/grafica/totalPorOrigenFecha/asignacion"
	
	var labelsArrayTotalPorOrigenFecha = ['VENTANILLA', 'INTERNET'];
	var colorsArrayTotalPorOrigenFecha = ['#95BBD7','#052C48'];
	var totalsArrayTotalPorOrigenFecha = [0,0];
	
	setTimeout(function() {
		$.ajax({
			url : urlTotalPorOrigenFecha,
			dataType : 'json',
			type : 'post',
			data : {
				fechaInicio : $('input#fechaInicioTOFNss').val(),
				fechaFin : $('input#fechaFinTOFNss').val()
			},
			success : function(objData) {
							
				$('div#totalPorOrigenFechaChart').css("height","300px");

				_chartTotalPorOrigenFecha = new Morris.Line({
					element : 'totalPorOrigenFechaChart',
					data : objData,
					xkey: 'fecha',
					ykeys: ['movimientosVentanilla', 'movimientosInternet'],
					labels: labelsArrayTotalPorOrigenFecha,
					smooth : false,
					lineColors : colorsArrayTotalPorOrigenFecha,
					dateFormat : function(date){
						return new Date(date).toLocaleDateString();
					},
					xLabelFormat : function(date){
						return new Date(date).toLocaleDateString();
					}
				});
				
				$('div#totalPorOrigenFechaLoading').remove();
				
				var totalMov = 0;				
				$.each( objData, function( key, value ) {
					totalMov += value.movimientosInternet + value.movimientosVentanilla;
					totalsArrayTotalPorOrigenFecha[0] += value.movimientosVentanilla;
					totalsArrayTotalPorOrigenFecha[1] += value.movimientosInternet;
				});
				
				generarLabels('totalPorOrigenFechaChart', 
						labelsArrayTotalPorOrigenFecha, 
						colorsArrayTotalPorOrigenFecha, 
						totalsArrayTotalPorOrigenFecha);
				setTotalMovimientos('totalPorOrigenFechaChart',totalMov);
			},
			error : function() {
				$('div#totalPorOrigenFechaChart').html("<div style='text-align: center; vertical-align: middle;'>Ocurrió un error inesperado</div>");
			}
		});
	}, 500);
	
	$(window).resize(function() {
		_chartTotalPorOrigenFecha.redraw();
	});
	
</script>

<div id="totalPorOrigenFechaChart">
	<div style='text-align: center; vertical-align: middle;'
		id="totalPorOrigenFechaLoading">
		<img alt='' src='${staticResourcesPath}/imagenes/loading.gif' />
	</div>
</div>