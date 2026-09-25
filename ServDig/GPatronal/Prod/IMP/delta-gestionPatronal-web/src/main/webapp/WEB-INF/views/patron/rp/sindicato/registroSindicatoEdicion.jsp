<%@ include file="../../../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/registroSindicato.js" htmlEscape="true" />"></script>
	
<form:form id="formActualizarRegistroSindicato"
	modelAttribute="registroSindicatoTramite">

	<fieldset style="margin: 20px !important;">
		<legend>
			<strong> <spring:message code="titulo.sindicato" /></strong>
		</legend>
		<form:hidden path="cveRegistroSindicato" />
		<form:hidden path="cveIdPersonaMoral" />
		<form:hidden path="cveIdPatronSujetoObligado"/>
		<form:hidden path="numeroRegistroPatronal"/>
		<c:set var="sol" value="${idSolicitud}" />
		<div id="divActualizarRegistroSindicato">
			<fieldset class="fsInterno">
				<span id="errorNegocioLabel" class=" hiddenElement error"></span> 
				<span id="numReferenciadocRegistroError" class="error hiddenElement"></span>
				<label style="width:40%"><spring:message code="label.sindicato.num.ref" /></label>
				<form:input id="numReferenciadocRegistro" readonly="false" path="numReferenciadocRegistro" cssStyle="width:50%" maxlength="20"/>
			</fieldset>
			
			<fieldset class="fsInterno">
				<span id="fechaRegistroError" class="error hiddenElement"></span>
				<label style="width:40%"><spring:message code="label.sindicato.fecha" /></label>
				<input type="text" readonly="readonly" id="txtFechaRegistroEdicion" style="width:50%"
					value='<fmt:formatDate pattern="dd/MM/yyyy" value="${registroSindicatoTramite.fechaRegistro}"/>'/>
				<form:hidden path="fechaRegistro"/>
			</fieldset>
			
			<fieldset class="fsInterno">
				<span id="autoridadLaboralError" class="error hiddenElement"></span>
				<label style="width:40%"><spring:message code="label.sindicato.autoridad" /></label>
				<form:input readonly="false" path="autoridadLaboral" cssStyle="width:50%" maxlength="100"/>
			</fieldset>
			<c:if test="${sol=='vacio'}">
				<div class="derecha">
					<input type="button" onclick="actualizarRegistroSindicato('activar');"
							class="mboton" name="aDatosGenerales"
							value="<spring:message code="label.finalizar"/>"/>
				</div>
			</c:if>
			<c:if test="${sol!='vacio'}">
				<div class="derecha">
					<input type="button" onclick="actualizarRegistroSindicato('finalizar');"
							class="mboton" name="aDatosGenerales"
							value="<spring:message code="label.finalizar"/>"/>
				</div>
			</c:if>
		</div>
	</fieldset>
</form:form>
<div id="dgActualizarRegistroSindicato" title="<spring:message code="label.exito"/>">
	<p><span class="ui-icon ui-icon-alert"
		style="float: left; margin: 0 7px 20px 0;"> </span>
		<spring:message code="label.sindicato.actualizado" />
	</p>
</div>