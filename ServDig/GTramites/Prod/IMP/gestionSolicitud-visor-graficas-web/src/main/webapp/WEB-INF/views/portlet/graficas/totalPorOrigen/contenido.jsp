<%@ include file="../../../general/taglibs.jsp"%>

<script id="initPortlet">
	var _chartTotalPorOrigen;
	var urlTotalPorOrigen = "/gestionSolicitud-visor-graficas-web/portlet/grafica/totalPorOrigen"
	
	var labelsArrayTotalPorOrigen = new Array();
	var colorsArrayTotalPorOrigen = new Array();
	var totalsArrayTotalPorOrigen = new Array();
	
	setTimeout(function() {
		$.ajax({
			url : urlTotalPorOrigen,
			dataType : 'json',
			type : 'post',
			data : {
				fechaInicio : $('input#fechaInicioTO').val(),
				fechaFin : $('input#fechaFinTO').val()
			},
			success : function(objData) {
							
				$('div#totalPorOrigenChart').css("height","300px");
				
				_chartTotalPorOrigen = new Morris.Bar({
					element : 'totalPorOrigenChart',
					data : objData,
					xkey : 'label',
					ykeys : ['value'],
					labels : ['Movimientos Totales'],
					barColors: function (row, series, type) {
						var color = null;
						if (type === 'bar') {
							if (row.x % 2 == 0) {
								color = '#0B62A4';
								labelsArrayTotalPorOrigen.push(row.label);
								colorsArrayTotalPorOrigen.push(color);
								totalsArrayTotalPorOrigen.push(row.y);
								return color;
							} else {
								color = '#042135';
								labelsArrayTotalPorOrigen.push(row.label);
								colorsArrayTotalPorOrigen.push(color);
								totalsArrayTotalPorOrigen.push(row.y);
								return color;
							}
						} else {
							return '#000';
						}
					 }
				});
				
				$('div#totalPorOrigenLoading').remove();
				
				var totalMov = 0;				
				$.each( objData, function( key, value ) {
					totalMov += value.value;
				});
				
				generarLabels('totalPorOrigenChart', labelsArrayTotalPorOrigen, colorsArrayTotalPorOrigen, totalsArrayTotalPorOrigen);
				setTotalMovimientos('totalPorOrigenChart',totalMov);
			},
			error : function() {
				$('div#totalPorOrigenChart').html("<div style='text-align: center; vertical-align: middle;'>Ocurrió un error inesperado</div>");
			}
		});
	}, 500);
	
	$(window).resize(function() {
		_chartTotalPorOrigen.redraw();
	});
</script>

<div id="totalPorOrigenChart">
	<div style='text-align: center; vertical-align: middle;'
		id="totalPorOrigenLoading">
		<img alt='' src='${staticResourcesPath}/imagenes/loading.gif' />
	</div>
</div>