<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<%@ include file="../../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/escrituraConstitutiva.js" htmlEscape="true" />"></script>

		<form:hidden path="cveEscrituraConstitutiva"/>
		<form:hidden path="cveIdPersonaMoral"/>
		<form:hidden path="cveIdPatronSujetoObligado"/>
		<div id="divActualizarEscrituraConstitutiva">
			<fieldset class="fsInterno">
				<span id="errorNegocioLabel" class=" hiddenElement error"></span>
				<span id="numEscrituraError" class="error hiddenElement"></span>
				<label style="width:40%"> <spring:message code="label.escritura.conts.num.esc" /></label>
				<form:input id="numEscritura" readonly="true" path="numEscritura" cssStyle="width:50%" maxlength="12"/>
			</fieldset>
			<fieldset class="fsInterno">
				<span id="numNotariaError" class="error hiddenElement"></span>
				<label style="width:40%"> <spring:message code="label.escritura.conts.notaria" /></label>
				<form:input readonly="true" path="numNotaria" cssStyle="width:50%"  maxlength="15"/>
			</fieldset>
			<fieldset class="fsInterno">
				<label style="width:40%">Entidad:</label>
				<form:input id="entidadFederativa" readonly="true" path="lugarExpedicion.entidadFederativa.nombre" cssStyle="width:50%" />
			</fieldset>
			<fieldset class="fsInterno">
				<label style="width:40%">Delegaci&oacute;n o Municipio:</label>
				<form:input id="municipio" readonly="true" path="lugarExpedicion.nombre" cssStyle="width:50%" />
			</fieldset>
			
			<fieldset class="fsInterno">
				<span id="folioMercantilError" class="error hiddenElement"></span>
				<label style="width:40%"> <spring:message code="label.escritura.conts.folio" /></label>
				<form:input id="folioMercantil" readonly="true" path="folioMercantil" cssStyle="width:50%" maxlength="13"/>
			</fieldset>
			<fieldset class="fsInterno">
				<span id="seccionError" class="error hiddenElement"></span>
				<label style="width:40%"> <spring:message code="label.seccion" /></label>
				<form:input id="seccion" readonly="true" path="seccion" cssStyle="width:50%" maxlength="15"/>
			</fieldset>
			<fieldset class="fsInterno">
				<span id="partidaError" class="error hiddenElement"></span>
				<label style="width:40%"> <spring:message code="label.partida" /></label>
				<form:input id="partida" readonly="true" path="partida" cssStyle="width:50%" maxlength="15"/>
			</fieldset>
			<fieldset class="fsInterno">
				<span id="volumenError" class="error hiddenElement"></span>
				<label style="width:40%"> <spring:message code="label.volumen" /></label>
				<form:input id="volumen" readonly="true" path="volumen" cssStyle="width:50%" maxlength="15"/>
			</fieldset>
			<fieldset class="fsInterno">
				<span id="fojaError" class="error hiddenElement"></span>
				<label style="width:40%"> <spring:message code="label.foja" /></label>
				<form:input id="foja" readonly="true" path="foja" cssStyle="width:50%" maxlength="15"/>
			</fieldset>
			
						
		</div>
	<div id="dgActualizarEscrituraConstitutiva" title="<spring:message code="label.exito"/>">
	<p><span class="ui-icon ui-icon-alert"
		style="float: left; margin: 0 7px 20px 0;"> </span>
		<spring:message code="label.escritura.conts.actualizada" />
	</p>
</div>