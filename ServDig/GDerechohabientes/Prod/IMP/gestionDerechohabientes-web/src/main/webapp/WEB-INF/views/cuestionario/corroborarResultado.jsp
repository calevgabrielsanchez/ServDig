<%@ include file="../general/taglibs.jsp" %>

 <script type="text/javascript">
	history.go(1);
</script>
<form>
<c:choose>
<c:when test="${empty errores}">
<div class="form-comment">


	<fieldset id="fsBeneficiario" >
		<center>			
			${derechohabiente.tipoTramite.descripcion}<br>
			${derechohabiente.fisica.nombre} ${derechohabiente.fisica.primerApellido} ${derechohabiente.fisica.segundoApellido}<br>	
			<spring:message code="label.calificacion"/>: ${derechohabiente.evaluacionCuestionario}							
		</center>
		
	</fieldset>
	</div>
	<input id="cveIdPersona" value="${derechohabiente.fisica.idPersona}" type="hidden">
	<input id="cveIdTipoTramite" value="${derechohabiente.tipoTramite.idTipoTramite}" type="hidden">
	<input id="cveIdTramite" value="${derechohabiente.tramiteId}" type="hidden">
	<input type="hidden" id="modo" name="modo" value="modo">
	
</c:when>
<c:otherwise>
	<div class="ui-widget-content ui-corner-all">
		<div class="ui-state-error ui-corner-all" align="center">
			<div class="ui-icon ui-icon-alert"></div>
			<p class="ui-helper-reset ui-state-error-text"><spring:message code="${errores}" /></p>
		</div>
	</div>
</c:otherwise>
</c:choose>
</form>	