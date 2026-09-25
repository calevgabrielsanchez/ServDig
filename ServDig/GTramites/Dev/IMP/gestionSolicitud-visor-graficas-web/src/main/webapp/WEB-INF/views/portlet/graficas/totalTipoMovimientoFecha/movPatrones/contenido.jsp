<%@ include file="../../../../general/taglibs.jsp"%>

<script id="initPortlet">
	var _chartTotalMovPatPorTipoMovFecha;
	var urlTotalMovPatPorTipoMovFecha = "/gestionSolicitud-visor-graficas-web/portlet/grafica/movPatrones/totalTipoFecha"
	
	var labelsArrayTotalMovPatPorTipoMovFecha = ['CAMBIO DE ACTIVIDAD', 'INCORPORACIÓN DE ACTIVIDADES', 'COMPRA DE ACTIVOS', 'COMODATO', 'ENAJENACIÓN', 'ARRENDAMIENTO', 'FIDEICOMISO TRASLATIVO'];
	var colorsArrayTotalMovPatPorTipoMovFecha = ['#0B62A4', '#95BBD7', '#095085', '#042135', '#3B6858', '#CA5D2C', '#9B9BC5'];
	var totalsArrayTotalMovPatPorTipoMovFecha = [0,0,0,0,0,0,0];
	
	setTimeout(function() {
		$.ajax({
			url : urlTotalMovPatPorTipoMovFecha,
			dataType : 'json',
			type : 'post',
			data : {
				fechaInicio : $('input#fechaInicioMovPatronesTMovF').val(),
				fechaFin : $('input#fechaFinMovPatronesTMovF').val()
			},
			success : function(objData) {
								
				$('div#totalMovPatPorTipoMovFechaChart').css("height","300px");

				_chartTotalMovPatPorTipoMovFecha = new Morris.Line({
					element : 'totalMovPatPorTipoMovFechaChart',
					data : objData,
					xkey: 'fecha',
					ykeys: ['cambioActividad', 'incorporacionActividades', 'compraActivos', 'comodato', 'enajenacion', 'arrendamiento', 'fideicomisoTraslativo'],
					labels: labelsArrayTotalMovPatPorTipoMovFecha,
					smooth : false,
					continousLine: true,
					lineColors : colorsArrayTotalMovPatPorTipoMovFecha,
					dateFormat : function(date){
						return new Date(date).toLocaleDateString();
					},
					xLabelFormat : function(date){
						return new Date(date).toLocaleDateString();
					}
				});
				
				$('div#totalMovPatPorTipoMovFechaLoading').remove();
				
				var totalMov = 0;				
				$.each( objData, function( key, value ) {
					totalMov += value.cambioActividad + value.incorporacionActividades + value.compraActivos + value.comodato + value.enajenacion + value.arrendamiento + value.fideicomisoTraslativo;
					totalsArrayTotalMovPatPorTipoMovFecha[0] += value.cambioActividad;
					totalsArrayTotalMovPatPorTipoMovFecha[1] += value.incorporacionActividades;
					totalsArrayTotalMovPatPorTipoMovFecha[2] += value.compraActivos;
					totalsArrayTotalMovPatPorTipoMovFecha[3] += value.comodato;
					totalsArrayTotalMovPatPorTipoMovFecha[4] += value.enajenacion;
					totalsArrayTotalMovPatPorTipoMovFecha[5] += value.arrendamiento;
					totalsArrayTotalMovPatPorTipoMovFecha[6] += value.fideicomisoTraslativo;
				});
				
				generarLabels('totalMovPatPorTipoMovFechaChart', 
						labelsArrayTotalMovPatPorTipoMovFecha, 
						colorsArrayTotalMovPatPorTipoMovFecha, 
						totalsArrayTotalMovPatPorTipoMovFecha);
				setTotalMovimientos('totalMovPatPorTipoMovFechaChart',totalMov);
			},
			error : function() {
				$('div#totalMovPatPorTipoMovFechaChart').html("<div style='text-align: center; vertical-align: middle;'>Ocurrió un error inesperado</div>");
			}
		});
	}, 500);
	
	$(window).resize(function() {
		_chartTotalMovPatPorTipoMovFecha.redraw();
	});
	
</script>

<div id="totalMovPatPorTipoMovFechaChart">
	<div style='text-align: center; vertical-align: middle;'
		id="totalMovPatPorTipoMovFechaLoading">
		<img alt='' src='${staticResourcesPath}/imagenes/loading.gif' />
	</div>
</div>