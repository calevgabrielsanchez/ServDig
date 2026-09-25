<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<%@ include file="../../general/taglibs.jsp"%>

<div id="seccionRepresentante" style="display: none;">
	<legend class="separadorseccion" style="width:920px">
		<spring:message code="titulo.representante"/>
	</legend>
	<table id="gridRepresentantesAlta"
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
				onclick="fnAbrirDialogoAgregarRLAlta();"
				class="mboton" value="Agregar"
				style="font-size: .8em !important;">
			
				<input type="button"
				onclick="fnAbrirDialogoEliminarRLAlta();"
				class="mboton" value="Eliminar"
				style="font-size: .8em !important;">
				
				<input type="button"
				onclick="fnAbrirDialogoModificarRLAlta();"
				class="mboton" value="Modificar"
				style="font-size: .8em !important;">
				
			</td>
		</tr>
	</table>
	
</div>

<!-- Seccion para agregar un nuevo elemento de representante legal para el Alta (verificar si puede moverse esto a otro jsp) -->
<div id="dgAgregarRepresentanteLegalAlta" title="Agregar Representante Legal" class="page_holder contenedor row cell" style="width: 90% !important;">
	
	<label style="width:35%"><span class="indicador_campo_requerido">*</span>RFC:</label>
	<form:input path="representanteLegalAux.personaFisica.rfc" maxlength="14" value=""/> <br />
	
	<label style="width:35%"><span class="indicador_campo_requerido">*</span>CURP:</label>
	<form:input path="representanteLegalAux.personaFisica.curp" maxlength="20" value=""/> <br />
	
	<label style="width:35%"><span class="indicador_campo_requerido">*</span>Nombre(s):</label>
	<form:input path="representanteLegalAux.personaFisica.nombre" maxlength="50" value=""/> <br />
	
	<label style="width:35%"><span class="indicador_campo_requerido">*</span>Apellido Paterno:</label>
	<form:input path="representanteLegalAux.personaFisica.primerApellido" maxlength="50" value=""/> <br />
	
	<label style="width:35%"><span class="indicador_campo_requerido">*</span>Apellido Materno:</label>
	<form:input path="representanteLegalAux.personaFisica.segundoApellido" maxlength="50" value=""/> <br />
	
	Indique si cuenta con poder para Actos de administraci&oacute;n o dominio:   <input type="checkbox" name="indActAdmonDominioAltaAgregar" id="indActAdmonDominioAltaAgregar" /> <br />
	
	<label>Secci&oacute;n medios de Contacto</label> <br /><br /><br />
	<center>
	<div style="width: 500px !important;">
		<table id="gridMediosContactoRLAltaEnAgregar"
			style="width: 500px !important; vertical-align: top;">
		<thead>
		</thead>
		<tbody style="width: 500px !important;">
		</tbody>
		<tfoot></tfoot>
	</table>
	</div>
	</center>
	
	
	
	<br />
	
	<table>
		<tr>
			<td>
				<input type="button"
				onclick="fnAbrirDialogoAgregarMedioContactoRLAlta();"
				class="mbutton" value="Agregar"
				style="font-size: .8em !important; color: white !important;">
			
				<input type="button"
				onclick="fnAbrirDialogoEliminarMedioContactoRLAlta('fromRLAltaAgregar');"
				class="mbutton" value="Eliminar"
				style="font-size: .8em !important; color: white !important;">
				
				<input type="button"
				onclick="fnAbrirDialogoModificarMedioContactoRLAlta('fromRLAltaAgregar');"
				class="mbutton" value="Modificar"
				style="font-size: .8em !important; color: white !important;">
				
			</td>
		</tr>
	</table>
	
</div>

<!-- Seccion para agregar un nuevo elemento de representante legal para el Alta (verificar si puede moverse esto a otro jsp) -->
<div id="dgModificarRepresentanteLegalAlta" title="Modificar Representante Legal"  class="page_holder dialogo_holder">
	
	<label style="width:35%"><span class="indicador_campo_requerido">*</span>RFC:</label>
	<form:input path="representanteLegalAux.personaFisica.rfc" maxlength="14" value=""/> <br />
	
	<label style="width:35%"><span class="indicador_campo_requerido">*</span>CURP:</label>
	<form:input path="representanteLegalAux.personaFisica.curp" maxlength="20" value=""/> <br />
	
	<label style="width:35%"><span class="indicador_campo_requerido">*</span>Nombre(s):</label>
	<form:input path="representanteLegalAux.personaFisica.nombre" maxlength="50" value=""/> <br />
	
	<label style="width:35%"><span class="indicador_campo_requerido">*</span>Apellido Paterno:</label>
	<form:input path="representanteLegalAux.personaFisica.primerApellido" maxlength="50" value=""/> <br />
	
	<label style="width:35%"><span class="indicador_campo_requerido">*</span>Apellido Materno:</label>
	<form:input path="representanteLegalAux.personaFisica.segundoApellido" maxlength="50" value=""/> <br />
	
	Indique si cuenta con poder para Actos de administraci&oacute;n o dominio:   <input type="checkbox" name="indActAdmonDominioAltaModificar" id="indActAdmonDominioAltaModificar" /> <br /><br /><br />
	
	<table id="gridMediosContactoRLAltaEnModificar"
			style="width: 800px; vertical-align: top;">
		<thead>
		</thead>
		<tbody style="width: 800px;">
		</tbody>
	</table>
	
	<br />
	
	<table>
		<tr>
			<td>
				<input type="button"
				onclick="fnAbrirDialogoAgregarMedioContactoRLAlta();"
				class="mbutton" value="Agregar"
				style="font-size: .8em !important; color: white !important;">
			
				<input type="button"
				onclick="fnAbrirDialogoEliminarMedioContactoRLAlta('fromRLAltaModificar');"
				class="mbutton" value="Eliminar"
				style="font-size: .8em !important; color: white !important;">
				
				<input type="button"
				onclick="fnAbrirDialogoModificarMedioContactoRLAlta('fromRLAltaModificar');"
				class="mbutton" value="Modificar"
				style="font-size: .8em !important; color: white !important;">
				
			</td>
		</tr>
	</table>
	
</div>