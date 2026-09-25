<%@ include file="../../../../general/taglibs.jsp"%>

<script id="initPortlet">
	var _chartTotalEdoAdeudoPorTipoMovFecha;
	var urlTotalEdoAdeudoPorTipoMovFecha = "/gestionSolicitud-visor-graficas-web/portlet/grafica/edoAdeudo/totalTipoFecha"
	
	var labelsArrayTotalEdoAdeudoPorTipoMovFecha = ['POR SITUACION DE COBRO', 'POR SITUACION DE COBRO RCV', 'POR MOTIVO DE COBRO', 'POR MOTIVO DE COBRO RCV'];
	var colorsArrayTotalEdoAdeudoPorTipoMovFecha = ['#0B62A4', '#95BBD7', '#095085', '#042135'];
	var totalsArrayTotalEdoAdeudoPorTipoMovFecha = [0,0,0,0];
	
	setTimeout(function() {
		$.ajax({
			url : urlTotalEdoAdeudoPorTipoMovFecha,
			dataType : 'json',
			type : 'post',
			data : {
				fechaInicio : $('input#fechaInicioEdoAdeudoTMovF').val(),
				fechaFin : $('input#fechaFinEdoAdeudoTMovF').val()
			},
			success : function(objData) {
								
				$('div#totalEdoAdeudoPorTipoMovFechaChart').css("height","300px");

				_chartTotalEdoAdeudoPorTipoMovFecha = new Morris.Line({
					element : 'totalEdoAdeudoPorTipoMovFechaChart',
					data : objData,
					xkey: 'fecha',
					ykeys: ['situacionCobro', 'situacionCobroRCV', 'motivoCobro', 'motivoCobroRCV'],
					labels: labelsArrayTotalEdoAdeudoPorTipoMovFecha,
					smooth : false,
					lineColors : colorsArrayTotalEdoAdeudoPorTipoMovFecha,
					dateFormat : function(date){
						return new Date(date).toLocaleDateString();
					},
					xLabelFormat : function(date){
						return new Date(date).toLocaleDateString();
					}
				});
				
				$('div#totalEdoAdeudoPorTipoMovFechaLoading').remove();
				
				var totalMov = 0;				
				$.each( objData, function( key, value ) {
					totalMov += value.situacionCobro + value.situacionCobroRCV + value.motivoCobro + value.motivoCobroRCV;
					totalsArrayTotalEdoAdeudoPorTipoMovFecha[0] += value.situacionCobro;
					totalsArrayTotalEdoAdeudoPorTipoMovFecha[1] += value.situacionCobroRCV;
					totalsArrayTotalEdoAdeudoPorTipoMovFecha[2] += value.motivoCobro;
					totalsArrayTotalEdoAdeudoPorTipoMovFecha[3] += value.motivoCobroRCV;
				});
				
				generarLabels('totalEdoAdeudoPorTipoMovFechaChart', 
						labelsArrayTotalEdoAdeudoPorTipoMovFecha, 
						colorsArrayTotalEdoAdeudoPorTipoMovFecha, 
						totalsArrayTotalEdoAdeudoPorTipoMovFecha);
				setTotalMovimientos('totalEdoAdeudoPorTipoMovFechaChart',totalMov);
			},
			error : function() {
				$('div#totalEdoAdeudoPorTipoMovFechaChart').html("<div style='text-align: center; vertical-align: middle;'>Ocurrió un error inesperado</div>");
			}
		});
	}, 500);
	
	$(window).resize(function() {
		_chartTotalEdoAdeudoPorTipoMovFecha.redraw();
	});
	
</script>

<div id="totalEdoAdeudoPorTipoMovFechaChart">
	<div style='text-align: center; vertical-align: middle;'
		id="totalEdoAdeudoPorTipoMovFechaLoading">
		<img alt='' src='${staticResourcesPath}/imagenes/loading.gif' />
	</div>
</div>