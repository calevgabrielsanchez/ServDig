<%@ include file="../../general/taglibs.jsp"%>

<form:hidden path="fisica.idPersona"/>
<fieldset class="fsInterno">
	<label style="width:25%">
		<spring:message code="label.primer.apellido" />
	</label>
	<form:input readonly="true" path="fisica.primerApellido" maxlength="14" cssStyle="width:70%"/>
</fieldset>
<fieldset class="fsInterno">
	<label style="width:25%">
		<spring:message code="label.segundo.apellido" />
	</label>
	<form:input readonly="true" path="fisica.segundoApellido" maxlength="14" cssStyle="width:70%"/>
</fieldset>
<fieldset class="fsInterno">
	<label style="width:25%">
		<spring:message 
			code="label.nombres" />
	</label>
	<form:input readonly="true" path="fisica.nombre" maxlength="14" cssStyle="width:70%"/>
</fieldset>
<fieldset class="fsInterno">
	<label style="width:25%">
		<spring:message 
			code="label.nombre.comercial" />
	</label>
	<form:input readonly="true" path="fisica.nombreComercial" maxlength="14" cssStyle="width:70%"/>
</fieldset>
