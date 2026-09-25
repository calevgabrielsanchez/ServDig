<%@ include file="../../general/taglibs.jsp"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum"%>

<form:hidden path="tipoPersonaFiscal"/>
<table style="width: 920px; border:none;" id="seccionBusquedaPersonas">
	<tr>
		<td bgcolor="#C0504D" style="border: none; width: 180px;">
			<img alt="Personas Fisicas" src="<spring:url value="/static/resources/imagenes/alta/pFisica.jpg" htmlEscape="true" />" onclick="fnOpenBuscarPersonaFisica()">
		</td>
		<td bgcolor="#C0504D" valign="middle" style="color:white; font-family: monospace; font-size: medium; border: none;" onclick="fnOpenBuscarPersonaFisica()">
			<b>Buscar Persona F&iacute;sica</b>
		</td>
		<td bgcolor="#9BBB59" style="width: 180px;">
			<img alt="Personas Morales" src="<spring:url value="/static/resources/imagenes/alta/pMoral.jpg" htmlEscape="true" />" onclick="fnOpenBuscarPersonaMoral()">
		</td>
		<td bgcolor="#9BBB59" valign="middle"  style="color:white; font-family: monospace; font-size: medium; border: none;" onclick="fnOpenBuscarPersonaMoral()">
			<b>Buscar Persona Moral</b>
		</td>
	</tr>
</table>

<div id="seccionPersonaFisica" style="display: none;">
	<legend class="separadorseccion" style="width:920px">
		<spring:message code="titulo.persona.fisica"/>
	</legend>
	
	<table style="width: 920px; border: none;">
		<tr>
			<td class="label_patrones" style="width: 150px;">
				<spring:message code="label.primer.apellido" />
			</td>
			<td class="label_patrones_data">
				<form:input readonly="true" path="fisica.primerApellido" maxlength="14" cssStyle="width:50%"/>
			</td>
			<td class="label_patrones">
				<spring:message code="label.segundo.apellido" />
			</td>
			<td class="label_patrones_data">
				<form:input readonly="true" path="fisica.segundoApellido" maxlength="14" cssStyle="width:50%"/>
			</td>
		</tr>
		<tr>
			<td class="label_patrones">
				<spring:message code="label.nombres" />
			</td>
			<td class="label_patrones_data">
				<form:input readonly="true" path="fisica.nombre" maxlength="14" cssStyle="width:50%"/>
			</td>
			<td class="label_patrones">
				<spring:message code="label.curp" />
			</td>
			<td class="label_patrones_data">
				<form:input readonly="true" path="fisica.curp" maxlength="14" cssStyle="width:50%"/>
			</td>
		</tr>
		<tr>
			<td class="label_patrones">
				<spring:message code="label.rfc" />
			</td>
			<td class="label_patrones_data">
				<form:input readonly="true" path="fisica.rfc" maxlength="14" cssStyle="width:50%"/>
			</td>
			<td class="label_patrones">
				<spring:message code="label.nombre.comercial" />
			</td>
			<td class="label_patrones_data">
				<form:input path="nombreComercial" maxlength="120" cssStyle="width:50%"/>
			</td>
		</tr>
	</table>
</div>
<div id="seccionPersonaMoral" style="display: none;">
	<legend class="separadorseccion" style="width:920px">
		<spring:message code="titulo.persona.moral"/>
	</legend>
	<table style="width: 920px; border: none;">
		<tr>
			<td class="label_patrones" style="width: 200px;">
				<spring:message code="label.razon.social"/>
			</td>
			<td class="label_patrones_data">
				<form:input readonly="true" path="moral.razonSocial" maxlength="120" />	
			</td>
		</tr>
		<tr>
			<td class="label_patrones">
				<spring:message code="label.tipo.sociedad" />
			</td>
			<td class="label_patrones_data">
				<form:input readonly="true" path="moral.tipoSociedad.descripcion" maxlength="14"/>
			</td>	
		</tr>
		<tr>
			<td class="label_patrones">
				<spring:message code="label.nombre.comercial" />
			</td>
			<td class="label_patrones_data">
				<form:input path="nombreComercial" maxlength="120" cssStyle="width:70%"/>
			</td>
		</tr>
	</table>
</div>
<div id="seccionMedios" style="display: none;">
	<legend class="separadorseccion" style="width:920px">
		<spring:message code="titulo.medio.contacto"/>
	</legend>
	
	<table id="gridMedios"
			style="width: 500px; vertical-align: top;">
		<thead>
		</thead>
		<tbody style="width: 500px;">
		</tbody>
	</table>
</div>

<div id="personaFisica"></div>
<div id="personaMoral"></div>