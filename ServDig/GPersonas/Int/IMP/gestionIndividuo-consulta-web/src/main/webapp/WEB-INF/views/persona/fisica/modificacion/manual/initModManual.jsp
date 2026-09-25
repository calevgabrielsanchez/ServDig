<%@ include file="/WEB-INF/views/layout/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/personas/fisica/modificacion/manual/init-modificacion-manual.js" htmlEscape="true" />"></script>

<style type="text/css">
.site_position_center {
	width: 100% !important;
}
</style>

<div class="container"
	style="margin: 0px !important; width: 100% !important;">

	<div class="contenedor" style="margin-top: 50px;">

		<c:set var="contextpath" value="<%=request.getContextPath()%>" />

		<div id="loadingDiv" style="text-align: center;">
			<img alt="Cargando..."
				src="<spring:url value="/static/resources/imagenes/loading.gif" htmlEscape="true" />">
		</div>

		<form:form modelAttribute="mdmDatosEntrada" id="forma" method="post"
			action="${contextpath}/persona/fisica/modificacion-manual/capturar"
			cssClass="formNotBlock">

			<span id="errorNegocioLabel" class="error hiddenElement"></span>

			<form:hidden path="personaFisica.idPersona" id="busquedaIdPersona" />

			<form:hidden path="indCapturaNombre" id="indCapturaNombre" />
			<form:hidden path="indCapturaCURP" id="indCapturaCURP" />
			<form:hidden path="indCapturaSexo" id="indCapturaSexo" />
			<form:hidden path="indCapturaFechaNacimiento"
				id="indCapturaFechaNacimiento" />
			<form:hidden path="indCapturaLugarNacimiento"
				id="indCapturaLugarNacimiento" />
			<form:hidden path="indCapturaDocumentoProbatorio"
				id="indCapturaDocumentoProbatorio" />

			<form:hidden path="indCapturaRFC" id="indCapturaRFC" />
			<form:hidden path="indCapturaDomicilioFiscal"
				id="indCapturaDomicilioFiscal" />
			<form:hidden path="indCapturaMediosContactoFiscales"
				id="indCapturaMediosContactoFiscales" />

			<form:hidden path="indCapturaDomicilioParticular"
				id="indCapturaDomicilioParticular" />
			<form:hidden path="indCapturaMediosContactoParticular"
				id="indCapturaMediosContactoParticular" />

			<form:hidden path="indAutorizacion" id="indAutorizacion" />
		</form:form>

	</div>
</div>
</div>