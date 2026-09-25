<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/serie/serie-detalle.js" htmlEscape="true" />"></script>

<div class="page_holder_no_height">
	<div class="post_entry_wide no-border">

		<div class="form-comment">
			<c:set var="contextpath" value="<%=request.getContextPath()%>" />

			<h2 style="font-size: 1.0em !important;">
				<c:out value='${mensaje}' />
			</h2>
			<br />
			<form:form modelAttribute="asignacion" action="">
				<form:label path="serie.numSerie" class="wide">Numero de Serie</form:label>
				<form:input path="serie.numSerie" id="numSerie" cssStyle="width: 300px" />
				<br /><br /><br />

				<form:label path="serie.anioRegistro" class="wide">A&ntilde;o de Registro</form:label>
				<form:input path="serie.anioRegistro" id="anioRegistro" cssStyle="width: 300px" />
				<br /><br /><br />

				<form:label path="serie.tipoSerie.descripcion" class="wide">Tipo de Serie</form:label>
				<form:input path="serie.tipoSerie.descripcion" id="tipoSerie" cssStyle="width: 300px" />
				<br /><br /><br />

				<form:label path="delegacion.descripcion" class="wide">Delegaci&oacute;n</form:label>
				<form:input path="delegacion.descripcion" id="delegacion" cssStyle="width: 300px" />
				<br /><br /><br />

				<form:label path="subdelegacion.descripcion" class="wide">Subelegaci&oacute;n</form:label>
				<form:input path="subdelegacion.descripcion" id="subdelegacion" cssStyle="width: 300px" />
				<br /><br /><br />
			</form:form>
		</div>
	</div>
</div>
