<%@ include file="../../../../../general/taglibs.jsp"%>

<script id="initPortlet">

	var _chartTotalUsuariosSipare;
	var urlTotalUsuariosSipare = "/gestionSolicitud-sipare-web/graficas/total/usuarios";
	
	var labelsArrayTotalUsuariosSipare = ['ALTA DE USUARIO SIPARE'];
	var colorsArrayTotalUsuariosSipare = ['#0B62A4'];
	var totalsArrayTotalUsuariosSipare = [0];
	
	setTimeout(function() {
		$.ajax({
			url : urlTotalUsuariosSipare,
			dataType : 'json',
			type : 'post',
			data : {
				fechaInicio : $('input#fechaInicioTotalUsuariosSipare').val(),
				fechaFin : $('input#fechaFinTotalUsuariosSipare').val()
			},
			success : function(objData) {
								
				$('div#totalUsuariosSipareChart').css("height","300px");

				_chartTotalUsuariosSipare = new Morris.Line({
					element : 'totalUsuariosSipareChart',
					data : objData,
					xkey: 'fecha',
					ykeys: ['value'],
					labels: labelsArrayTotalUsuariosSipare,
					smooth : false,
					lineColors : colorsArrayTotalUsuariosSipare,
					dateFormat : function(date){
						return new Date(date).toLocaleDateString();
					},
					xLabelFormat : function(date){
						return new Date(date).toLocaleDateString();
					}
				});
				
				$('div#totalUsuariosSipareLoading').remove();
				
				var totalMov = 0;				
				$.each( objData, function( key, value ) {
					totalMov += value.value;
					totalsArrayTotalUsuariosSipare[0] += value.value;
				
				});
				
				generarLabels('totalUsuariosSipareChart', 
						labelsArrayTotalUsuariosSipare, 
						colorsArrayTotalUsuariosSipare, 
						totalsArrayTotalUsuariosSipare);
				setTotalMovimientos('totalUsuariosSipareChart',totalMov);
			},
			error : function() {
				$('div#totalUsuariosSipareChart').html("<div style='text-align: center; vertical-align: middle;'>Ocurrió un error inesperado</div>");
			}
		});
	}, 500);
	
	$(window).resize(function() {
		_chartTotalUsuariosSipare.redraw();
	});
	
</script>

<div id="totalUsuariosSipareChart">
	<div style='text-align: center; vertical-align: middle;'
		id="totalUsuariosSipareLoading">
		<img alt='' src='${staticResourcesPath}/imagenes/loading.gif' />
	</div>
</div>