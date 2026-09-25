<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<%@ include file="../../../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/escrituraConstitutiva.js" htmlEscape="true" />"></script>

<form:form id="formActualizarEscrituraConstitutiva"
	modelAttribute="escrituraConstitutivaTramite">

	<fieldset style="margin: 20px !important;">
		<legend>
			<strong><spring:message code="titulo.escritura.const" /></strong>
		</legend>
		<form:hidden path="cveEscrituraConstitutiva"/>
		<form:hidden path="cveIdPersonaMoral"/>
		<form:hidden path="cveIdPatronSujetoObligado"/>
		<form:hidden path="numeroRegistroPatronal"/>
		<c:set var="sol" value="${idSolicitud}" />
		<div id="divActualizarEscrituraConstitutiva">
			<fieldset class="fsInterno">
				<span id="errorNegocioLabel" class=" hiddenElement error"></span>
				<span id="numEscrituraError" class="error hiddenElement"></span>
				<label style="width:40%"> <spring:message code="label.escritura.conts.num.esc" /></label>
				<form:input id="numEscritura" readonly="false" path="numEscritura" cssStyle="width:50%" maxlength="12"/>
			</fieldset>
			<fieldset class="fsInterno">
				<span id="numNotariaError" class="error hiddenElement"></span>
				<label style="width:40%"> <spring:message code="label.escritura.conts.notaria" /></label>
				<form:input readonly="false" path="numNotaria" cssStyle="width:50%"  maxlength="15"/>
			</fieldset>
			
			<fieldset class="fsInterno">
				<label style="width:40%">Entidad:</label>
				<combo:creaCombo idHtml="lugarExpedicion.entidadFederativa.clave" idHtmlContenedor="formActualizarEscrituraConstitutiva"
					entidad="mx.gob.imss.ctirss.delta.persistence.DgCatEstado" idHtmlValor="${claveEdo}" 
					mostrarSoloActivos = "true"/>
			</fieldset>
			<fieldset class="fsInterno">
				<label style="width:40%">Delegaci&oacute;n o Municipio:</label>
				<combo:creaCombo
					entidad="mx.gob.imss.ctirss.delta.persistence.DgCatMunicipio"
					idHtml="lugarExpedicion.clave"
					entidadPadre="id.cveEnt"
					idHtmlPadre="lugarExpedicion.entidadFederativa.clave" 
					idHtmlContenedor="formActualizarEscrituraConstitutiva"
					idHtmlValor="${claveMun}"
					mostrarSoloActivos = "false"/>
			</fieldset>
			
			<fieldset class="fsInterno">
				<span id="folioMercantilError" class="error hiddenElement"></span>
				<label style="width:40%"> <spring:message code="label.escritura.conts.folio" /></label>
				<form:input id="folioMercantil" readonly="false" path="folioMercantil" cssStyle="width:50%" maxlength="13" onkeydown="inhabilitarCapura1();"/>
			</fieldset>
			<fieldset class="fsInterno">
				<span id="seccionError" class="error hiddenElement"></span>
				<label style="width:40%"> <spring:message code="label.seccion" /></label>
				<form:input id="seccion" readonly="false" path="seccion" cssStyle="width:50%" maxlength="15" onkeydown="inhabilitarCapura2();"/>
			</fieldset>
			<fieldset class="fsInterno">
				<span id="partidaError" class="error hiddenElement"></span>
				<label style="width:40%"> <spring:message code="label.partida" /></label>
				<form:input id="partida" readonly="false" path="partida" cssStyle="width:50%" maxlength="15" onkeydown="inhabilitarCapura2();"/>
			</fieldset>
			<fieldset class="fsInterno">
				<span id="volumenError" class="error hiddenElement"></span>
				<label style="width:40%"> <spring:message code="label.volumen" /></label>
				<form:input id="volumen" readonly="false" path="volumen" cssStyle="width:50%" maxlength="15" onkeydown="inhabilitarCapura2();"/>
			</fieldset>
			<fieldset class="fsInterno">
				<span id="fojaError" class="error hiddenElement"></span>
				<label style="width:40%"> <spring:message code="label.foja" /></label>
				<form:input id="foja" readonly="false" path="foja" cssStyle="width:50%" maxlength="15" onkeydown="inhabilitarCapura2();"/>
			</fieldset>
			
			<c:if test="${sol=='vacio'}">
				<div class="derecha">
					<input type="button" onclick="actualizarEscrituraConstitutiva('activar');"
							class="mboton" name="aDatosGenerales"
							value="<spring:message code="label.finalizar"/>"/>
				</div>
			</c:if>
			<c:if test="${sol!='vacio'}">
				<div class="derecha">
					<input type="button" onclick="actualizarEscrituraConstitutiva('finalizar');"
							class="mboton" name="aDatosGenerales"
							value="<spring:message code="label.finalizar"/>"/>
				</div>
			</c:if>
		</div>
	</fieldset>
</form:form>
<div id="dgActualizarEscrituraConstitutiva" title="<spring:message code="label.exito"/>">
	<p><span class="ui-icon ui-icon-alert"
		style="float: left; margin: 0 7px 20px 0;"> </span>
		<spring:message code="label.escritura.conts.actualizada" />
	</p>
</div>