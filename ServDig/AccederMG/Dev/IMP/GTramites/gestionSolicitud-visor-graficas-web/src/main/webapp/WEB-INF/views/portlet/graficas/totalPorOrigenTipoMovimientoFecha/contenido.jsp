<%@ include file="../../../general/taglibs.jsp"%>

<script id="initPortlet">
	var _chartTotalPorOrigenTipoMovimientoFecha;
	var urlTotalPorOrigenTipoMovimientoFecha = "/gestionSolicitud-visor-graficas-web/portlet/grafica/totalPorOrigenTipoMovimientoFecha"
	
	var labelsArrayTotalPorOrigenTipoMovimientoFecha = ['ASIGNACIONES VENTANILLA', 'ASIGNACIONES INTERNET', 'LOCALIZACIONES VENTANILLA', 'LOCALIZACIONES INTERNET'];
	var colorsArrayTotalPorOrigenTipoMovimientoFecha = ['#0B62A4', '#95BBD7', '#095085', '#042135'];
	var totalsArrayTotalPorOrigenTipoMovimientoFecha = [0,0,0,0];
	
	setTimeout(function() {
		$.ajax({
			url : urlTotalPorOrigenTipoMovimientoFecha,
			dataType : 'json',
			type : 'post',
			data : {
				fechaInicio : $('input#fechaInicioTOTMovF').val(),
				fechaFin : $('input#fechaFinTOTMovF').val()
			},
			success : function(objData) {
								
				$('div#totalPorOrigenTipoMovimientoFechaChart').css("height","300px");

				_chartTotalPorOrigenTipoMovimientoFecha = new Morris.Line({
					element : 'totalPorOrigenTipoMovimientoFechaChart',
					data : objData,
					xkey: 'fecha',
					ykeys: ['asignacionesVentanilla', 'asignacionesInternet', 'localizacionesVentanilla', 'localizacionesInternet'],
					labels: labelsArrayTotalPorOrigenTipoMovimientoFecha,
					smooth : false,
					lineColors : colorsArrayTotalPorOrigenTipoMovimientoFecha,
					dateFormat : function(date){
						return new Date(date).toLocaleDateString();
					},
					xLabelFormat : function(date){
						return new Date(date).toLocaleDateString();
					}
				});
				
				$('div#totalPorOrigenTipoMovimientoFechaLoading').remove();
				
				var totalMov = 0;				
				$.each( objData, function( key, value ) {
					totalMov += value.asignacionesVentanilla + value.asignacionesInternet + value.localizacionesVentanilla + value.localizacionesInternet;
					totalsArrayTotalPorOrigenTipoMovimientoFecha[0] += value.asignacionesVentanilla;
					totalsArrayTotalPorOrigenTipoMovimientoFecha[1] += value.asignacionesInternet;
					totalsArrayTotalPorOrigenTipoMovimientoFecha[2] += value.localizacionesVentanilla;
					totalsArrayTotalPorOrigenTipoMovimientoFecha[3] += value.localizacionesInternet;
				});
				
				generarLabels('totalPorOrigenTipoMovimientoFechaChart', 
						labelsArrayTotalPorOrigenTipoMovimientoFecha, 
						colorsArrayTotalPorOrigenTipoMovimientoFecha, 
						totalsArrayTotalPorOrigenTipoMovimientoFecha);
				setTotalMovimientos('totalPorOrigenTipoMovimientoFechaChart',totalMov);
			},
			error : function() {
				$('div#totalPorOrigenTipoMovimientoFechaChart').html("<div style='text-align: center; vertical-align: middle;'>Ocurrió un error inesperado</div>");
			}
		});
	}, 500);
	
	$(window).resize(function() {
		_chartTotalPorOrigenTipoMovimientoFecha.redraw();
	});
	
</script>

<div id="totalPorOrigenTipoMovimientoFechaChart">
	<div style='text-align: center; vertical-align: middle;'
		id="totalPorOrigenTipoMovimientoFechaLoading">
		<img alt='' src='${staticResourcesPath}/imagenes/loading.gif' />
	</div>
</div>