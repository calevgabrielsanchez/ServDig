<%@ include file="../general/taglibs.jsp"%>
<%@taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
	
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
	
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/documentos/dictamenIncapacitado.js" htmlEscape="true" />"></script>

<div class="form-comment">
	<form id="formdocument">
		<fieldset>
			<legend><strong>Dictamen para beneficiario incapacitado</strong></legend>
			<center>
			<table>
				<tr>
					<td align="left">Grado de incapacidad:</td>
					<td align="left">
						<input type="hidden" name="existeEstadoIncapacidad" id="existeEstadoIncapacidad" value="1"/>
						<input class="entero_10" type="text" id="gradoIncapacidad"  style="width: 200px" name="gradoIncapacidad" maxlength="4"/>
					</td>
				</tr>
				<tr >
					<td align="left"">Enfermedad que padece (diagn&oacute;stico):</td>
					<td align="left">
						<textarea class="alfanumerico_espacios" id="diagnosticoPadecimiento"  style="width: 200px" name="diagnosticoPadecimiento" ></textarea>  
					</td>
				</tr>
				
				<tr >
					<td align="left">Fecha de inicio del estado incapacitante:</td>
					<td align="left">
						<input type="text" id="fechaInicioEnfermedad"  name="fechaInicioEnfermedad" value="" style="width: 200px"/>
					</td>
				</tr>
				
				<tr >
					<td><br></td>
					<td><br></td>
				</tr>
				
				<tr >
					<td align="left">Nombre del m&eacute;dico que certific&oacute;:</td>
					<td align="left">
						<select id="selectMedico" name="medicoFamiliar"  onchange="showMatricula();"></select>
						<input type="hidden" name="nombreMed" id="nombreMed"/>
						<input type="hidden" name="primerApeidoMed" id="primerApeidoMed"/>
						<input type="hidden" name="segundoApeidoMed" id="segundoApeidoMed"/>
						<input type="hidden" name="matriculaMed" id="matriculaMed"/>
						<input type="hidden" name="idMedicoEspecialidad" id="idMedicoEspecialidad" value="-1"/>
						
						 
					</td>
				</tr>
				<tr >
					<td align="left">Matr&iacute;cula:</td>
					<td align="left"><div id=noMatricula>N/A</div></td>
				</tr>
				
				<tr >
					<td><br></td>
					<td><br></td>
				</tr>
				
				
				<tr >
					<td align="left">Unidad de medicina familiar:</td>
					<td>	
						<input type="hidden" id="unidadMedicaFamiliar" name="unidadMedicaFamiliar" value="${hijo.medicoEnTurno.unidadMedicaFamiliar.idUMF}"></input>	
						<input type="hidden" id="nombreUMFHiddenId" name="nombreUMF" value="${hijo.medicoEnTurno.unidadMedicaFamiliar.nombreCorto}"/>
						<input type="text" value="${hijo.medicoEnTurno.unidadMedicaFamiliar.nombreCorto}" 
						disabled="disabled" style="width: 200px"/>
					</td>	
				</tr>
				<tr >
					<td><br></td>
				</tr>
				<tr >
					<td align="left">Delegaci&oacute;n:</td>
					 <td>
					 	<input type="hidden" id="delegacion" name="delegacion" value="${hijo.medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.id}"></input>
					 	<input type="hidden" id="nombreDelegacionHiddenId" name="nombreDelegacion" value="${hijo.medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.descripcion}"/>
					 	<input type="text" value="${hijo.medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.descripcion}" 
						disabled="disabled" style="width: 200px"/>
					 </td>
				</tr>
				<tr >
					<td align="left">Fecha de expedici&oacute;n:</td>
					<td align="left">
						<input type="text" id="fechaExpedicion" name="fechaExpedicionString" value="" style="width: 200px"/>	
					</td>
				</tr>
				
			</table>
			</center>
		</fieldset>
	</form>
</div>		