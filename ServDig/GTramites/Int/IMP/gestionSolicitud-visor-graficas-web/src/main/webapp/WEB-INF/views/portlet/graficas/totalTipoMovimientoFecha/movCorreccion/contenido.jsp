<%@ include file="../../../../general/taglibs.jsp"%>

<script id="initPortlet">
	var _chartTotalMovCorrecPorTipoMovFecha;
	var urlTotalMovCorrecPorTipoMovFecha = "/gestionSolicitud-visor-graficas-web/portlet/grafica/movCorreccion/totalTipoFecha"
	
	var labelsArrayTotalMovCorrecPorTipoMovFecha = ['SOLICITUD DE CORRECCIÓN PATRONAL', 'PRESENTACIÓN DE LA CORRECCIÓN PATRONAL', 'PRÓRROGA PARA LA PRESENTACIÓN DE LA CORRECCIÓN PATRONAL'];
	var colorsArrayTotalMovCorrecPorTipoMovFecha = ['#0B62A4', '#95BBD7', '#095085'];
	var totalsArrayTotalMovCorrecPorTipoMovFecha = [0,0,0];
	
	setTimeout(function() {
		$.ajax({
			url : urlTotalMovCorrecPorTipoMovFecha,
			dataType : 'json',
			type : 'post',
			data : {
				fechaInicio : $('input#fechaInicioMovCorreccionTMovF').val(),
				fechaFin : $('input#fechaFinMovCorreccionTMovF').val()
			},
			success : function(objData) {
								
				$('div#totalMovCorrecPorTipoMovFechaChart').css("height","300px");

				_chartTotalMovCorrecPorTipoMovFecha = new Morris.Line({
					element : 'totalMovCorrecPorTipoMovFechaChart',
					data : objData,
					xkey: 'fecha',
					ykeys: ['solicitudCorreccion', 'presentacionCorreccion', 'prorrogaCorreccion'],
					labels: labelsArrayTotalMovCorrecPorTipoMovFecha,
					smooth : false,
					continousLine: true,
					lineColors : colorsArrayTotalMovCorrecPorTipoMovFecha,
					dateFormat : function(date){
						return new Date(date).toLocaleDateString();
					},
					xLabelFormat : function(date){
						return new Date(date).toLocaleDateString();
					}
				});
				
				$('div#totalMovCorrecPorTipoMovFechaLoading').remove();
				
				var totalMov = 0;				
				$.each( objData, function( key, value ) {
					totalMov += value.solicitudCorreccion + value.presentacionCorreccion + value.prorrogaCorreccion;
					totalsArrayTotalMovCorrecPorTipoMovFecha[0] += value.solicitudCorreccion;
					totalsArrayTotalMovCorrecPorTipoMovFecha[1] += value.presentacionCorreccion;
					totalsArrayTotalMovCorrecPorTipoMovFecha[2] += value.prorrogaCorreccion;
				});
				
				generarLabels('totalMovCorrecPorTipoMovFechaChart', 
						labelsArrayTotalMovCorrecPorTipoMovFecha, 
						colorsArrayTotalMovCorrecPorTipoMovFecha, 
						totalsArrayTotalMovCorrecPorTipoMovFecha);
				setTotalMovimientos('totalMovCorrecPorTipoMovFechaChart',totalMov);
			},
			error : function() {
				$('div#totalMovCorrecPorTipoMovFechaChart').html("<div style='text-align: center; vertical-align: middle;'>Ocurrió un error inesperado</div>");
			}
		});
	}, 500);
	
	$(window).resize(function() {
		_chartTotalMovCorrecPorTipoMovFecha.redraw();
	});
	
</script>

<div id="totalMovCorrecPorTipoMovFechaChart">
	<div style='text-align: center; vertical-align: middle;'
		id="totalMovCorrecPorTipoMovFechaLoading">
		<img alt='' src='${staticResourcesPath}/imagenes/loading.gif' />
	</div>
</div>