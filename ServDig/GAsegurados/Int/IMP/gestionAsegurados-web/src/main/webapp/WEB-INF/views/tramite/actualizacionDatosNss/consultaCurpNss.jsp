<%@ include file="../../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/tramite/tramite-capturaDatosBasicos.js" htmlEscape="true" />"></script>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<style>
input,textarea,.uneditable-input {
	text-transform: uppercase;
}

.required {
	color: red;
}
</style>

<div>
	<div>
		<div id="info-paso">
			<h3 style="font-size: 1.8em !important">Paso 1: Consulta de personas f&iacute;sicas por NSS</h3>
		</div>

		<div class="contenedor filtros-busqueda">

			<form:form modelAttribute="fisica" cssClass="form-horizontal" id="registroAseguradoDatosBasicosForm"
				action="${contextpath}/tramite/actualiza/datos/consultar" method="post">

				<fieldset>
					<legend> NSS a actualizar </legend>
					<!-- Seccion de errores -->
					<form:errors path="errorFormGeneral" cssClass="error" />
					<div class="form-group">
						<form:label path="nss" cssClass="control-label col-sm-2 col-sm-offset-2">
							<span class="required">*</span>NSS</form:label>
						<div class="col-sm-5">
							<form:input path="nss" id="nss" maxlength="11" cssClass="numerico form-control" />
							<form:errors path="nss" cssClass="error" />
						</div>
					</div>
					<!-- Controles -->
					<div class="form-group">
						<div class="col-sm-9 text-right">
							<button type="button" id="limpiar" class="btn btn-default">LIMPIAR</button>
							<button type="submit" id="buscar" class="btn btn-primary">BUSCAR</button>
						</div>
					</div>
				</fieldset>
			</form:form>

		</div>
	</div>
</div>
<!-- Div del dialogo de localizar persona -->
<div id="dgLocalizarPersona"></div>