<%@ include file="../../../general/taglibs.jsp"%>

<script type="text/javascript"
        src="<spring:url value="/static/resources\js\wizard\rtt\inciarRtt.js" htmlEscape="true" />">	
</script>

<div class="contenedor col-sm-12">
	<br />
	<p style="text-align: justify;">
		En atenci&oacute;n a su consulta, con motivo de la revisi&oacute;n
		anual de la siniestralidad
		<fmt:formatDate value="${fecSiniestra}" pattern="yyyy" />, se le informa que no se localizaron riesgos de trabajo terminados,
		asociados a este registro patronal, por el periodo que va del ${periodoConsulta}.
	</p>
	<br />
	<p style="text-align: justify;">En caso de que el patr&oacute;n
		cuente en sus registros con riesgos de trabajo terminados,
		deber&aacute; comunicarlos en su determinaci&oacute;n anual de la
		prima del Seguro de Riesgos de Trabajo, con motivo de la
		revisi&oacute;n de su siniestralidad, de conformidad con lo
		establecido en los artículos 72 y 74 de la Ley del Seguro Social y 32,
		fracciones I y V, y 34 del Reglamento de la Ley del Seguro Social en
		Materia de Afiliaci&oacute;n, Clasificaci&oacute;n de Empresas,
		Recaudaci&oacute;n y Fiscalizaci&oacute;n.</p>
</div>
<br />        
<div class="pull-right">
    <button id="cerrar" onclick="cerrar()"
            class="btn btn-default">
        Cerrar
    </button>
</div>