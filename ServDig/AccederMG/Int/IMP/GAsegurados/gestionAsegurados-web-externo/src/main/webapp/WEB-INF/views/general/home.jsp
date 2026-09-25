<%@ include file="../general/taglibs.jsp"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/home.js" htmlEscape="true" />"></script>

<div id="homecontenido">
	<div>
		<h3>Asignaci&oacute;n
			de N&uacute;mero de Seguridad Social</h3>
		
		<div class="m-b-xl">
			<spring:message code="label.informacion.asignacion" />
		</div>

		<div class="text-left">
			<form action="" id="formConfirm" class="formNotBlock">
				<button type="submit" class="btn btn-primary">Iniciar</button>
			</form>
		</div>

	</div>
</div>

<div id="dgInicioSolicitud"
	title="Tr&aacute;mite de Asignaci&oacute;n de N&uacute;mero de Seguridad Social">
	<jsp:include page="caratulaRegistroAsegurado.jsp"></jsp:include>
</div>
