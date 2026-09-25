<%@ include file="../../../general/taglibs.jsp"%>

<script id="initPortlet">

	var _chartTotalRegistroUsuario;
	var urlTotalRegistroUsuario = "/gestionSolicitud-visor-graficas-web/portlet/grafica/movRegistroUsuario/totalFecha";
	
	var labelsArrayRegistroUsuario = ['REGISTRO DE USUARIO'];
	var colorsArrayRegistroUsuario = ['#0B62A4'];
	var totalsArrayRegistroUsuario = [0];
	
	setTimeout(function() {
		$.ajax({
			url : urlTotalRegistroUsuario,
			dataType : 'json',
			type : 'post',
			data : {
				fechaInicio : $('input#fechaInicioRegistroUsuario').val(),
				fechaFin : $('input#fechaFinRegistroUsuario').val()
			},
			success : function(objData) {
								
				$('div#totalRegistroUsuarioChart').css("height","300px");

				_chartTotalRegistroUsuario = new Morris.Line({
					element : 'totalRegistroUsuarioChart',
					data : objData,
					xkey: 'fecha',
					ykeys: ['value'],
					labels: labelsArrayRegistroUsuario,
					smooth : false,
					lineColors : colorsArrayRegistroUsuario,
					dateFormat : function(date){
						return new Date(date).toLocaleDateString();
					},
					xLabelFormat : function(date){
						return new Date(date).toLocaleDateString();
					}
				});
				
				$('div#totalRegistroUsuarioLoading').remove();
				
				var totalMov = 0;				
				$.each( objData, function( key, value ) {
					totalMov += value.value;
					totalsArrayRegistroUsuario[0] += value.value;
				
				});
				
				generarLabels('totalRegistroUsuarioChart', 
						labelsArrayRegistroUsuario, 
						colorsArrayRegistroUsuario, 
						totalsArrayRegistroUsuario);
				setTotalMovimientos('totalRegistroUsuarioChart',totalMov);
			},
			error : function() {
				$('div#totalRegistroUsuarioChart').html("<div style='text-align: center; vertical-align: middle;'>Ocurrió un error inesperado</div>");
			}
		});
	}, 500);
	
	$(window).resize(function() {
		_chartTotalRegistroUsuario.redraw();
	});
	
</script>

<div id="totalRegistroUsuarioChart">
	<div style='text-align: center; vertical-align: middle;'
		id="totalRegistroUsuarioLoading">
		<img alt='' src='${staticResourcesPath}/imagenes/loading.gif' />
	</div>
</div>