<%@ include file="../../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/nombreComercial.js" htmlEscape="true" />"></script>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<form:form id="razonSocialForm" modelAttribute="sujetoTramite" action="${contextpath}/sujetoObligado/detalleRP">
	<fieldset style="margin: 20px !important;">
		<legend style="width:35%">
			<strong><spring:message code="titulo.razon.social" /> EN TR&Aacute;MITE</strong>
		</legend>
		<form:hidden path="cveIdSujetoObligado" />
		<form:hidden path="tipoPersonaFiscal" />	
		<form:hidden path="moral.idPersona"/>
		<form:hidden path="numeroRegistroPatronal"/>
		<c:set var="sol" value="${idSolicitud}" />							
		<div id="divActualizarNombreComercial">
			<fieldset class="fsInterno">
				<label style="width:25%">
					<spring:message code="label.razon.social"/>
				</label>
				<form:input readonly="true" path="moral.razonSocial" maxlength="14" cssStyle="width:70%"/>
			</fieldset>
			<fieldset class="fsInterno">
				<label style="width:25%">
					<spring:message code="label.tipo.sociedad" />
				</label>
				<form:input readonly="true" path="moral.tipoSociedad.descripcion" maxlength="14" cssStyle="width:70%"/>
			</fieldset>
			<fieldset class="fsInterno">
				<span id="moral.nombreComercialError" class="error hiddenElement"></span>
				<label style="width:25%">
					<spring:message code="label.nombre.comercial" />
				</label>
				<form:input path="moral.nombreComercial" id="moral.nombreComercial" maxlength="120" cssStyle="width:70%"/>
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
<div id="numFolioSolicitud" title="<spring:message code="label.exito"/>">
	<p><span class="ui-icon ui-icon-alert"
		style="float: left; margin: 0 7px 20px 0;"> </span>
		<spring:message code="label.nombre.comercial.actualizado" />
	</p>
</div>