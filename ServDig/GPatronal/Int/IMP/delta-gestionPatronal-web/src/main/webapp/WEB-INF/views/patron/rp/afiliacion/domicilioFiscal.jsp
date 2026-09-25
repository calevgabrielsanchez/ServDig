<%@ include file="../../../general/taglibs.jsp"%>

<fieldset class="fsInterno">
	<label style="font-weight: bold; width:25%;">
		<spring:message code="label.calle" />
	</label>
	<label style="width:25%">
		<c:out value="${sujetoObligado.domicilioFiscal.calle}"/>
	</label>
</fieldset>
<fieldset class="fsInterno">
	<label style="font-weight: bold; width:25%;">
		<spring:message code="label.numero.ext" />
	</label>
	<label style="width:25%">
		<c:out value="${sujetoObligado.domicilioFiscal.numExteriorAlf}"/>
	</label>	
</fieldset>
<fieldset class="fsInterno">
	<label style="font-weight: bold; width:25%;">
		<spring:message code="label.numero.int" />
	</label>
	<label style="width:25%">
		<c:out value="${sujetoObligado.domicilioFiscal.numInteriorAlf}"/>
	</label>	
</fieldset>
<fieldset class="fsInterno">
	<label style="font-weight: bold; width:25%;">
		<spring:message code="label.ref.primaria" />
	</label>
	<label style="width:25%">
		<c:out value="${sujetoObligado.domicilioFiscal.vialidadReferenciaPrimaria.nombre}"/>
	</label>	
</fieldset>
<fieldset class="fsInterno">
	<label style="font-weight: bold; width:25%;">
		<spring:message code="label.ref.secundaria" />
	</label>
	<label style="width:25%">
		<c:out value="${sujetoObligado.domicilioFiscal.vialidadReferenciaSecundaria.nombre}"/>
	</label>	
</fieldset>
<fieldset class="fsInterno">
	<label style="font-weight: bold; width:25%;">
		<spring:message code="label.ref.posterior" />
	</label>
	<label style="width:25%">
		<c:out value="${sujetoObligado.domicilioFiscal.vialidadReferenciaPosterior.nombre}"/>
	</label>	
</fieldset>
<fieldset class="fsInterno">
	<label style="font-weight: bold; width:25%;">
		<spring:message code="label.colonia" />
	</label>
	<label style="width:25%">
		<c:out value="${sujetoObligado.domicilioFiscal.colonia}"/>
	</label>	
</fieldset>
<fieldset class="fsInterno">
	<label style="font-weight: bold; width:25%;">
		<spring:message code="label.localidad" />
	</label>
	<label style="width:25%">
		<c:out value="${sujetoObligado.domicilioFiscal.asentamiento.localidad.nombre}"/>
	</label>	
</fieldset>
<fieldset class="fsInterno">
	<label style="font-weight: bold; width:25%;">
		<spring:message code="label.municipio" />
	</label>
	<label style="width:25%">
		<c:out value="${sujetoObligado.domicilioFiscal.asentamiento.localidad.municipio.nombre}"/>
	</label>	
</fieldset>
<fieldset class="fsInterno">
	<label style="font-weight: bold; width:25%;">
		<spring:message code="label.entidad.federativa" />
	</label>
	<label style="width:25%">
		<c:out value="${sujetoObligado.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre}"/>
	</label>	
</fieldset>
<fieldset class="fsInterno">
	<label style="font-weight: bold; width:25%;">
		<spring:message code="label.codigo.postal" />
	</label>
	<label style="width:25%">
		<c:out value="${sujetoObligado.domicilioFiscal.codigoPostal.codigoPostal}"/>
	</label>	
</fieldset>
<!--<fieldset class="fsInterno">-->
<!--	<label style="font-weight: bold; width:25%;">-->
<!--		<spring:message code="label.nombre.comercial" />-->
<!--	</label>-->
<!--	<form:input readonly="true" path="moral.nombreComercial" maxlength="14" cssStyle="width:70%"/>-->
<!--</fieldset>-->
