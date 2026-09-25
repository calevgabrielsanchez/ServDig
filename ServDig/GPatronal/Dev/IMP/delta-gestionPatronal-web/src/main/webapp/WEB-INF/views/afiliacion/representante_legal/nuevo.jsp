<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>



	<h2 style="font-size: 1.0em !important;">Agregar Representante
		Legal</h2>

	<div class="derecha">
		<p>
			<span class="indicador_campo_requerido">*</span>
			<span class="ui-icon ui-icon-info"	style="float: left; margin-right: .3em;"></span> 
				<strong><a href="javascript:fnOpenBuscarPersonaFisica();">Cargar datos de Persona F&iacute;sica</a>
			</strong>
		</p>
	</div>
	
	<br>
	<br>
	<div id="personaFisica"></div>

	<c:set var="contextpath" value="<%=request.getContextPath()%>" />
	<form id="representanteLegalFormNuevo">
		<table>
			<tr>
				<td>
					
						<input type="hidden" id="representanteLegalAux.personaFisica.idPersona"/>
					
				</td>
			</tr>					
			<tr>
				<td width=400>					
					<table>
						<tr>
							<td class="label_patrones">
								<label style="width:35%"><span class="indicador_campo_requerido">*</span>RFC:</label>
							</td>
							<td class="label_patrones_data">
								<input type="text" readonly="true" id="representanteLegalAux.personaFisica.rfc" maxlength="14"/>
							</td>
						</tr>
						<tr>
							<td class="label_patrones">
								<label style="width:35%"><span class="indicador_campo_requerido">*</span>CURP:</label>
							</td>
							<td class="label_patrones_data">
								<input type="text" readonly="readonly" id="representanteLegalAux.personaFisica.curp" maxlength="14"/>
							</td>
						<tr>
							<td class="label_patrones">
								<label style="width:35%"><span class="indicador_campo_requerido">*</span>Primer Apellido:</label>
							</td>
							<td class="label_patrones_data">
								<input type="text" readonly="readonly" id="representanteLegalAux.personaFisica.primerApellido" maxlength="14"/>
							</td>														
						</tr>
						<tr>
							<td class="label_patrones">
								<label style="width:35%"><span class="indicador_campo_requerido">*</span>Segundo Apellido:</label>
							</td>
							<td class="label_patrones_data">
								<input type="text" readonly="readonly" id="representanteLegalAux.personaFisica.segundoApellido" maxlength="14"/>
							</td>
						</tr>	
						<tr>
							<td class="label_patrones">
								<label style="width:35%"><span class="indicador_campo_requerido">*</span>Nombre(s):</label>
							</td>
							<td class="label_patrones_data">
								<input type="text" readonly="readonly" id="representanteLegalAux.personaFisica.nombre" maxlength="14"/>
							</td>
						</tr>								
					</table>
					<table>
						<tr>
							<td colspan="2">
								<input type="checkbox" name="indActAdmonDominio1" id="indActAdmonDominio1" />&iquest;Poder para Actos de administraci&oacute;n o dominio? <br />
							</td>
						</tr>
					</table>											
				</td>
			</tr>
		</table>							
	</form>
	
	<div id="divMediosContactoRepLegalPersonaFisica"></div>


