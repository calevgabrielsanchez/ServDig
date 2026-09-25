<%@ include file="/WEB-INF/views/layout/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/personas/moral/modificacion/manual/init-modificacion-manual.js" htmlEscape="true" />"></script>


<style type="text/css">
.site_position_center {
	width: 100% !important;
}

.main_wrap {
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
			action="${contextpath}/persona/moral/modificacion-manual/capturar"
			cssClass="formNotBlock">

			<span id="errorNegocioLabel" class="error hiddenElement"></span>

			<form:hidden path="personaMoral.idPersona" id="busquedaIdPersona" />

			<form:hidden path="indCapturaRFC" id="indCapturaRFC" />
			<form:hidden path="indCapturaDomicilioFiscal"
				id="indCapturaDomicilioFiscal" />
			<form:hidden path="indCapturaMediosContactoFiscales"
				id="indCapturaMediosContactoFiscales" />
			<form:hidden path="indCapturaRazonSocial" id="indCapturaRazonSocial" />
			<form:hidden path="indCapturaFechaConstitucion"
				id="indCapturaFechaConstitucion" />
			<form:hidden path="indCapturaTipoSociedad"
				id="indCapturaTipoSociedad" />

			<form:hidden path="indCapturaActaConstitutiva"
				id="indCapturaActaConstitutiva" />
			<form:hidden path="indCapturaRegistroSindicato"
				id="indCapturaRegistroSindicato" />

			<form:hidden path="indAutorizacion" id="indAutorizacion" />
		</form:form>

	</div>
</div>