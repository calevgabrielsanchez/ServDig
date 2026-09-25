<%@ include file="../../../general/taglibs.jsp"%>

<script id="initPortlet">

	var _chartTotalImpresionCfdi;
	var urlTotalImpresionCfdi = "/gestionSolicitud-visor-graficas-web/portlet/grafica/movImpresionCfdi/totalFecha";
	
	var labelsArrayImpresionCfdi = ['DESCARGA DE COMPROBANTES FISCALES'];
	var colorsArrayImpresionCfdi = ['#0B62A4'];
	var totalsArrayImpresionCfdi = [0];
	
	setTimeout(function() {
		$.ajax({
			url : urlTotalImpresionCfdi,
			dataType : 'json',
			type : 'post',
			data : {
				fechaInicio : $('input#fechaInicioImpresionCfdi').val(),
				fechaFin : $('input#fechaFinImpresionCfdi').val()
			},
			success : function(objData) {
								
				$('div#totalImpresionCfdiChart').css("height","300px");

				_chartTotalImpresionCfdi = new Morris.Line({
					element : 'totalImpresionCfdiChart',
					data : objData,
					xkey: 'fecha',
					ykeys: ['value'],
					labels: labelsArrayImpresionCfdi,
					smooth : false,
					lineColors : colorsArrayImpresionCfdi,
					dateFormat : function(date){
						return new Date(date).toLocaleDateString();
					},
					xLabelFormat : function(date){
						return new Date(date).toLocaleDateString();
					}
				});
				
				$('div#totalImpresionCfdiLoading').remove();
				
				var totalMov = 0;				
				$.each( objData, function( key, value ) {
					totalMov += value.value;
					totalsArrayImpresionCfdi[0] += value.value;
				
				});
				
				generarLabels('totalImpresionCfdiChart', 
						labelsArrayImpresionCfdi, 
						colorsArrayImpresionCfdi, 
						totalsArrayImpresionCfdi);
				setTotalMovimientos('totalImpresionCfdiChart',totalMov);
			},
			error : function() {
				$('div#totalImpresionCfdiChart').html("<div style='text-align: center; vertical-align: middle;'>Ocurrió un error inesperado</div>");
			}
		});
	}, 500);
	
	$(window).resize(function() {
		_chartTotalImpresionCfdi.redraw();
	});
	
</script>

<div id="totalImpresionCfdiChart">
	<div style='text-align: center; vertical-align: middle;'
		id="totalImpresionCfdiLoading">
		<img alt='' src='${staticResourcesPath}/imagenes/loading.gif' />
	</div>
</div>