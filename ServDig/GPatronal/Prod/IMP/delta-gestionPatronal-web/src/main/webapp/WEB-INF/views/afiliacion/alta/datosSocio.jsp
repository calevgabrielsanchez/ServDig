<%@ include file="../../general/taglibs.jsp"%>

<div id="seccionSocio" style="display: none;">
	<legend class="separadorseccion" style="width:920px">
		<spring:message code="titulo.socio"/>
	</legend>
	<table id="gridSociosAlta"
			style="width: 800px; vertical-align: top;">
		<thead>
		</thead>
		<tbody style="width: 800px;">
		</tbody>
	</table>
	
	<table>
		<tr>
			<td>
				<input type="button"
				onclick="fnAbrirDialogoAgregarSocioAlta();"
				class="mboton" value="Agregar"
				style="font-size: .8em !important;">
			
				<input type="button"
				onclick="fnAbrirDialogoEliminarSocioAlta();"
				class="mboton" value="Eliminar"
				style="font-size: .8em !important;">
				
				<input type="button"
				onclick="fnAbrirDialogoModificarSocioAlta();"
				class="mboton" value="Modificar"
				style="font-size: .8em !important;">
				
			</td>
		</tr>
	</table>
</div>

<!-- Seccion para agregar un nuevo elemento de socios para el Alta-->
<div id="dgAgregarSocioAlta" title="Agregar Socio" >
	
	<label style="width:35%"><span class="indicador_campo_requerido">*</span>RFC:</label>
 	<form:input path="socioAux.personaFisica.rfc" maxlength="14" value=""/> <br />
	
<!-- 	<label style="width:35%"><span class="indicador_campo_requerido">*</span>CURP:</label> -->
<%-- 	<form:input path="representanteLegalAux.personaFisica.curp" maxlength="20" value=""/> <br /> --%>
	
<!-- 	<label style="width:35%"><span class="indicador_campo_requerido">*</span>Nombre(s):</label> -->
<%-- 	<form:input path="representanteLegalAux.personaFisica.nombre" maxlength="50" value=""/> <br /> --%>
	
<!-- 	<label style="width:35%"><span class="indicador_campo_requerido">*</span>Apellido Paterno:</label> -->
<%-- 	<form:input path="representanteLegalAux.personaFisica.primerApellido" maxlength="50" value=""/> <br /> --%>
	
<!-- 	<label style="width:35%"><span class="indicador_campo_requerido">*</span>Apellido Materno:</label> -->
<%-- 	<form:input path="representanteLegalAux.personaFisica.segundoApellido" maxlength="50" value=""/> <br /> --%>
	
<!-- 	Indique si cuenta con poder para Actos de administraci&oacute;n o dominio:   <input type="checkbox" name="indActAdmonDominioAltaAgregar" id="indActAdmonDominioAltaAgregar" /> <br /> -->
</div>

<!-- Seccion para modificarar un elemento de socios para el Alta-->
<div id="dgModificarSocioAlta" title="Modificar Socio" >
	
	<label style="width:35%"><span class="indicador_campo_requerido">*</span>campo para capturar info:</label>
<%-- 	<form:input path="representanteLegalAux.personaFisica.rfc" maxlength="14" value=""/> <br /> --%>
	
<!-- 	<label style="width:35%"><span class="indicador_campo_requerido">*</span>CURP:</label> -->
<%-- 	<form:input path="representanteLegalAux.personaFisica.curp" maxlength="20" value=""/> <br /> --%>
	
<!-- 	<label style="width:35%"><span class="indicador_campo_requerido">*</span>Nombre(s):</label> -->
<%-- 	<form:input path="representanteLegalAux.personaFisica.nombre" maxlength="50" value=""/> <br /> --%>
	
<!-- 	<label style="width:35%"><span class="indicador_campo_requerido">*</span>Apellido Paterno:</label> -->
<%-- 	<form:input path="representanteLegalAux.personaFisica.primerApellido" maxlength="50" value=""/> <br /> --%>
	
<!-- 	<label style="width:35%"><span class="indicador_campo_requerido">*</span>Apellido Materno:</label> -->
<%-- 	<form:input path="representanteLegalAux.personaFisica.segundoApellido" maxlength="50" value=""/> <br /> --%>
	
<!-- 	Indique si cuenta con poder para Actos de administraci&oacute;n o dominio:   <input type="checkbox" name="indActAdmonDominioAltaModificar" id="indActAdmonDominioAltaModificar" /> <br /> -->
</div>