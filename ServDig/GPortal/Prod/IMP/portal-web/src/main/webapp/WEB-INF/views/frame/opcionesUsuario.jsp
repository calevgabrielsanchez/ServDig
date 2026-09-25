<%@ include file="../general/taglibs.jsp" %>
<script type="text/javascript" src="/gestionSolicitud-web/static/resources/js/delta/firma-digital/FirmaDigital.js"></script>
<script type="text/javascript" 
	src=<spring:url value="${contextpath}/static/resources/js/wizard/registroUsuario/solicitudUsuarioWizard.js" 
	htmlEscape="true" />>
</script>
<script type="text/javascript"
	src=<spring:url value="${contextpath}/static/resources/js/delta/authenticate.js"htmlEscape="true" />>
</script>
<script type="text/javascript"
	src=<spring:url value="${contextpath}/static/resources/js/delta/portal.js"htmlEscape="true" />>
</script>

<script type="text/javascript"
	src="/gestionIndividuo-consulta-web/static/resources/js/wizard/fisica/registro-usuario/registroUsuarioWizard.js"></script>

<c:set var="contextpath" value="<%=request.getContextPath()    %>" />

<fieldset style="width: 90%">
<legend>ACCEDE A TUS SERVICIOS</legend>
<!-- Inicio de sesion -->
<form:form modelAttribute="usuario"
	action="${contextpath}/j_spring_security_check" method="get"
	id="formlogin">
	<div><input type="button" id="enviarForm" class="mboton"
		style="font-size: 10px !important;"
		value="<spring:message code="label.ingresar" />"></div>
</form:form>

<br>

<!-- Registro de usuarios -->
<form id="formRegistroUsuarioNuevo"><input type="button"
	id="registrarUsuario" class="mboton"
	style="font-size: 10px !important;"
	value="<spring:message code="label.portal.button.crear.cuenta.nueva" />">
</form>

</fieldset>

<div id="dialog-mensajes" title="Mensaje del sistema">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>
			<label id="mensajeDialogo">Para poder ingresar, debe estar registrado como usuario</label>
	</p>
</div>

<div id="wizardRegistroUsuario"></div>
<!-- div para la forma de firma -->
<div id="firmaDigitalComponent"></div>
<div id="doctosRequeridosTramite"></div>