<%@ include file="../../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/nombreComercial.js" htmlEscape="true" />"></script>
	
<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<form:form modelAttribute="sujetoTramite" id="razonSocialForm" action="${contextpath}/sujetoObligado/detalleRP">
	<fieldset style="margin: 20px !important;">
		<legend style="width:35%">
			<strong><spring:message code="titulo.razon.social" /> EN TR&Aacute;MITE</strong>
		</legend>
		<form:hidden path="cveIdSujetoObligado" />
		<form:hidden path="tipoPersonaFiscal" />
		<form:hidden path="numeroRegistroPatronal"/>
		<c:set var="sol" value="${idSolicitud}" />
		<div id="divActualizarNombreComercial">
			<form:hidden path="fisica.idPersona"/>
			<fieldset class="fsInterno">
				<label style="width:40%">
					<spring:message code="label.primer.apellido" />
				</label>
				<form:input readonly="true" path="fisica.primerApellido" maxlength="14" cssStyle="width:50%"/>
			</fieldset>
			<fieldset class="fsInterno">
				<label style="width:40%">
					<spring:message code="label.segundo.apellido" />
				</label>
				<form:input readonly="true" path="fisica.segundoApellido" maxlength="14" cssStyle="width:50%"/>
			</fieldset>
			<fieldset class="fsInterno">
				<label style="width:40%">
					<spring:message code="label.nombres" />
				</label>
				<form:input readonly="true" path="fisica.nombre" maxlength="14" cssStyle="width:50%"/>
			</fieldset>
			<fieldset class="fsInterno">
				<label style="width:40%">
					<spring:message code="label.nombre.comercial" />
				</label>
				<form:input path="fisica.nombreComercial" maxlength="120" cssStyle="width:50%"/>
			</fieldset>
			<c:if test="${sol=='vacio'}">
				<div class="derecha">
					<input type="button" onclick="actualizarNombreComercial('activar');"
							class="mboton" name="aDatosGenerales"
							value="<spring:message code="label.finalizar"/>"/>
				</div>
			</c:if>
			<c:if test="${sol!='vacio'}">
				<div class="derecha">
					<input type="button" onclick="actualizarNombreComercial('finalizar');"
							class="mboton" name="aDatosGenerales"
							value="<spring:message code="label.finalizar"/>"/>
				</div>
			</c:if>
		</div>
	</fieldset>
</form:form>
<div id="dgActualizarNombreComercial" title="<spring:message code="label.exito"/>">
	<p><span class="ui-icon ui-icon-alert"
		style="float: left; margin: 0 7px 20px 0;"> </span>
		<spring:message code="label.nombre.comercial.actualizado" />
		</p>
</div>