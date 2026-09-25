<%@ include file="../general/taglibs.jsp"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/home.js" htmlEscape="true" />">
	
</script>

<div id="homecontenido" class="contenedor">
	<div>
		<h3>Consulta de reporte de vigencia de derechos.</h3>
		<div style="margin: 18px 0px;">
			<spring:message code="label.informacion.cartilla" />
		</div>

		<div style="float: left;">
			<form action="" id="formConfirm" class="formNotBlock">
				<button type="submit" class="btn btn-primary">Iniciar</button>
			</form>
		</div>

	</div>
</div>

<div id="dgInicioSolicitud"
	title="Tr&aacute;mite de consulta de reporte de vigencia de derechos.">
	<jsp:include page="caratulaConsultaCartilla.jsp"></jsp:include>
</div>

