<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
	
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/additional-methods.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/documentos/dictamenIncapacitado.js" htmlEscape="true" />"></script>

<div class="form-comment">
	<form id="formdocument">
		<fieldset>
			<table class="table table-striped table-bordered">
				<tr>
					<td align="left">
						<label class="control-label" for="gradoIncapacidad">Grado de incapacidad<span class="required">*</span>:</label>
					</td>
					<td align="left">
						<input type="hidden" name="existeEstadoIncapacidad" id="existeEstadoIncapacidad" value="1"/>
						<input class="entero_10 form-control" type="text" id="gradoIncapacidad"  name="gradoIncapacidad" maxlength="15"/>
					</td>
				</tr>
				<tr >
					<td align="left"">
						<label class="control-label" for="diagnosticoPadecimiento">Enfermedad que padece (diagn&oacute;stico)<span class="required">*</span>:</label>
					</td>
					<td align="left">
						<textarea class="alfanumerico_espacios form-control" id="diagnosticoPadecimiento"  name="diagnosticoPadecimiento" ></textarea>  
					</td>
				</tr>
				
				<tr >
					<td align="left">
						<label class="control-label" for="fechaInicioEnfermedad">Fecha de inicio del estado incapacitante<span class="required">*</span>:</label>
					</td>
					<td align="left">
						<input type="text" id="fechaInicioEnfermedad" class="form-control" name="fechaInicioEnfermedad" value=""/>
					</td>
				</tr>
				<tr >
					<td align="left">
						<label class="control-label" for="selectMedico">Nombre del m&eacute;dico que certific&oacute;<span class="required">*</span>:</label>
					</td>
					<td align="left">
						<select id="selectMedico" name="medicoFamiliar"  onchange="showMatricula();" class="form-control"></select>
						<input type="hidden" name="nombreMed" id="nombreMed"/>
						<input type="hidden" name="primerApeidoMed" id="primerApeidoMed"/>
						<input type="hidden" name="segundoApeidoMed" id="segundoApeidoMed"/>
						<input type="hidden" name="matriculaMed" id="matriculaMed"/>
						<input type="hidden" name="idMedicoEspecialidad" id="idMedicoEspecialidad" value="-1"/>
						
						 
					</td>
				</tr>
				<tr >
					<td align="left">
						<label class="control-label" for="noActa">Matr&iacute;cula<span class="required">*</span>:</label>
					</td>
					<td align="left"><div id=noMatricula>N/A</div></td>
				</tr>
				<tr >
					<td align="left">
						<label class="control-label" for="unidadMedicaFamiliar">Unidad de medicina familiar<span class="required">*</span>:</label>
					</td>
					
					<td>	
						<input type="hidden" id="unidadMedicaFamiliar" name="unidadMedicaFamiliar" value="${hijo.medicoEnTurno.unidadMedicaFamiliar.idUMF}"></input>	
						<input type="hidden" id="nombreUMFHiddenId" name="nombreUMF" value="${hijo.medicoEnTurno.unidadMedicaFamiliar.nombreCorto}"/>
						<input type="text" value="${hijo.medicoEnTurno.unidadMedicaFamiliar.nombreCorto}" 
						disabled="disabled" class="form-control"/>
					</td>	
				</tr>
				<tr >
					<td><br></td>
				</tr>
				<tr >
					<td align="left">
						<label class="control-label" for="nombreDelegacionHiddenId">Delegaci&oacute;n<span class="required">*</span>:</label>
					</td>
					
					 <td>
					 	<input type="hidden" id="delegacion" name="delegacion" value="${hijo.medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.id}"></input>
					 	<input type="hidden" id="nombreDelegacionHiddenId" name="nombreDelegacion" value="${hijo.medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.descripcion}"/>
					 	<input type="text" value="${hijo.medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.descripcion}" 
						disabled="disabled" class="form-control"/>
					 </td>
				</tr>
				<tr >
					<td align="left">
						<label class="control-label" for="fechaExpedicion">Fecha de expedici&oacute;n<span class="required">*</span>:</label>
					</td>
					<td align="left">
						<input type="text" id="fechaExpedicion" class="form-control" name="fechaExpedicionString" value=""/>	
					</td>
				</tr>
				
			</table>
		</fieldset>
	</form>
</div>		