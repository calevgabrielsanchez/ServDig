<%@ include file="../../../general/taglibs.jsp"%>

<script id="initPortlet">
	var _chartTotalPorOrigenTipoMovimiento;
	var urlTotalPorOrigenTipoMovimiento = "/gestionSolicitud-visor-graficas-web/portlet/grafica/totalPorOrigenTipoMovimiento"
	
	var labelsArrayTotalPorOrigenTipoMovimiento = new Array();
	var colorsArrayTotalPorOrigenTipoMovimiento = ['#770077', '#0B62A4', '#95BBD7', '#095085', '#042135', '#0B62A4', '#95BBD7', '#095085', '#042135'];
	var totalsArrayTotalPorOrigenTipoMovimiento = new Array();
	
	setTimeout(function() {
		$.ajax({
			url : urlTotalPorOrigenTipoMovimiento,
			dataType : 'json',
			type : 'post',
			data : {
				fechaInicio : $('input#fechaInicioTOTMov').val(),
				fechaFin : $('input#fechaFinTOTMov').val()
			},
			success : function(objData) {
								
				$('div#totalPorOrigenTipoMovimientoChart').css("height","300px");
				
				_chartTotalPorOrigenTipoMovimiento = new Morris.Bar({
					element : 'totalPorOrigenTipoMovimientoChart',
					data : objData,
					xkey: 'label',
					ykeys: ['value'],
					labels: ['Movimientos Totales'],
					barColors: function (row, series, type) {
						var color;
						if (type === 'bar') {							 
							 labelsArrayTotalPorOrigenTipoMovimiento.push(row.label);
							 totalsArrayTotalPorOrigenTipoMovimiento.push(row.y);
							 return colorsArrayTotalPorOrigenTipoMovimiento[row.x];
						} else {
							return '#000';
						}
					 }
				});
				
				$('div#totalPorOrigenTipoMovimientoLoading').remove();
				
				var totalMov = 0;				
				$.each( objData, function( key, value ) {
					totalMov += value.value;
				});
				
				generarLabels('totalPorOrigenTipoMovimientoChart', labelsArrayTotalPorOrigenTipoMovimiento, colorsArrayTotalPorOrigenTipoMovimiento, totalsArrayTotalPorOrigenTipoMovimiento);
				setTotalMovimientos('totalPorOrigenTipoMovimientoChart',totalMov);
			},
			error : function() {
				$('div#totalPorOrigenTipoMovimientoChart').html("<div style='text-align: center; vertical-align: middle;'>Ocurrió un error inesperado</div>");
			}
		});
	}, 500);
	
	$(window).resize(function() {
		_chartTotalPorOrigenTipoMovimiento.redraw();
	});
</script>

<div id="totalPorOrigenTipoMovimientoChart">
	<div style='text-align: center; vertical-align: middle;'
		id="totalPorOrigenTipoMovimientoLoading">
		<img alt='' src='${staticResourcesPath}/imagenes/loading.gif' />
	</div>
</div>
