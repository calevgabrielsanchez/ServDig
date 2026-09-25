<%@ include file="../../general/taglibs.jsp"%>

<form:hidden path="moral.idPersona"/>
<fieldset class="fsInterno">
	<label style="width:25%">
		<spring:message code="label.razon.social" />
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
	<label style="width:25%">
		<spring:message code="label.nombre.comercial" />
	</label>
	<form:input readonly="true" path="moral.nombreComercial" maxlength="14" cssStyle="width:70%"/>
</fieldset>
