<%@ include file="../../../../general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum"%>

<c:set var="idOrigenInternet" value="<%=OrigenSolicitudEnum.INTERNET.getId()%>" />

<c:choose>
	<c:when test="${idOrigenSolicitud eq idOrigenInternet}">
		<c:set var="descCompl" value="Internet" />
	</c:when>
	<c:otherwise>
		<c:set var="descCompl" value="Ventanilla" />
	</c:otherwise>
</c:choose>

<script id="initPortlet">
	var _chartTotalRissPorFechaEstado${descCompl};
	var urlTotalRissPorFechaEstado${descCompl} = "/gestionSolicitud-visor-graficas-web/portlet/grafica/riss/totalFechaEstado"
	
	var labelsArrayTotalRissPorFechaEstado${descCompl} = ['ATENDIDAS', 'RECHAZADAS', 'REGISTRADAS', 'CANCELADAS'];
	var colorsArrayTotalRissPorFechaEstado${descCompl} = ['#157164', '#CA5D2C', '#95BBD7', '#042135'];
	var totalsArrayTotalRissPorFechaEstado${descCompl} = [0,0,0,0];
	
	setTimeout(function() {
		$.ajax({
			url : urlTotalRissPorFechaEstado${descCompl},
			dataType : 'json',
			type : 'post',
			data : {
				fechaInicio : $('input#fechaInicioRissFechaEdo').val(),
				fechaFin : $('input#fechaFinRissFechaEdo').val(),
				idOrigenSolicitud : ${idOrigenSolicitud}
			},
			success : function(objData) {
								
				$('div#TotalRissPorFechaEstado${descCompl}Chart').css("height","300px");

				_chartTotalRissPorFechaEstado${descCompl} = new Morris.Line({
					element : 'TotalRissPorFechaEstado${descCompl}Chart',
					data : objData,
					xkey: 'fecha',
					ykeys: ['solicRifAtendidas', 'solicRifRechazadas', 'solicRifRegistradas', 'solicRifCanceladas'],
					labels: labelsArrayTotalRissPorFechaEstado${descCompl},
					smooth : false,
					lineColors : colorsArrayTotalRissPorFechaEstado${descCompl},
					dateFormat : function(date){
						return new Date(date).toLocaleDateString();
					},
					xLabelFormat : function(date){
						return new Date(date).toLocaleDateString();
					}
				});
				
				$('div#TotalRissPorFechaEstado${descCompl}Loading').remove();
				
				var totalMov = 0;				
				$.each( objData, function( key, value ) {
					totalMov += value.solicRifAtendidas + value.solicRifRechazadas;
					totalsArrayTotalRissPorFechaEstado${descCompl}[0] += value.solicRifAtendidas;
					totalsArrayTotalRissPorFechaEstado${descCompl}[1] += value.solicRifRechazadas;
					totalsArrayTotalRissPorFechaEstado${descCompl}[2] += value.solicRifRegistradas;
					totalsArrayTotalRissPorFechaEstado${descCompl}[3] += value.solicRifCanceladas;
				});
				
				generarLabels('TotalRissPorFechaEstado${descCompl}Chart', 
						labelsArrayTotalRissPorFechaEstado${descCompl}, 
						colorsArrayTotalRissPorFechaEstado${descCompl}, 
						totalsArrayTotalRissPorFechaEstado${descCompl});
				setTotalMovimientos('TotalRissPorFechaEstado${descCompl}Chart',totalMov);
			},
			error : function() {
				$('div#TotalRissPorFechaEstado${descCompl}Chart').html("<div style='text-align: center; vertical-align: middle;'>Ocurrió un error inesperado</div>");
			}
		});
	}, 500);
	
	$(window).resize(function() {
		_chartTotalRissPorFechaEstado${descCompl}.redraw();
	});
	
</script>

<div id="TotalRissPorFechaEstado${descCompl}Chart">
	<div style='text-align: center; vertical-align: middle;'
		id="TotalRissPorFechaEstado${descCompl}Loading">
		<img alt='' src='${staticResourcesPath}/imagenes/loading.gif' />
	</div>
</div>