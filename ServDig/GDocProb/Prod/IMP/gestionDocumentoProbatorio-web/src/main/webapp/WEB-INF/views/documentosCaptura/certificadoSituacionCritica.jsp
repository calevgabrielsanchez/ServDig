<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>


<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/additional-methods.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/documentos/certificadoSitCritica.js" htmlEscape="true" />"></script>
<div class="form-comment">
<form id="formdocument">
	<input type="hidden" id="idUmf" value="${idUmf}"/>
	<fieldset>
		<table class="table table-striped table-bordered">
			<tr >
				<td align="left">
					<label class="control-label" for="enfermedadPadecida">Enfermedad que padece (diagn&oacute;stico)<span class="required">*</span>:</label>
				</td>
				<td align="left">
					<textarea class="alfanumerico_espacios form-control"  name="enfermedadPadecida"  ></textarea>
				</td>
			</tr>
			
			<tr >
				<td align="left">
					<label class="control-label" for="fechaProbableInicio">Fecha probable de inicio<span class="required">*</span>:</label>
				</td>
				<td align="left">
				<input type="text" class="form-control" id="fechaProbableInicio" name="fechaProbableInicio" value="" readonly="readonly" onclick=" $( this ).datepicker();"/>
					
				</td>
			</tr>
			<tr >
				<td align="left">
					<label class="control-label" for="fechaTerminoIncapacidad">Fecha probable de t&eacute;rmino<span class="required">*</span>:</label>
				</td>
				<td align="left">
				<input type="text" class="form-control" id="fechaTerminoIncapacidad" name="fechaTerminoIncapacidad" value="" readonly="readonly" onclick=" $( this ).datepicker();"/>
					
				</td>
			</tr>
			<tr >
				<td align="left">
					<label class="control-label" for="selectMedico">Nombre del m&eacute;dico que certific&oacute;<span class="required">*</span>:</label>
				</td>
				<td align="left">
					<select id="selectMedico" name="medicoFamiliar" class="form-control" onchange="showMatricula();"></select>
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
					<label class="control-label" for="fechaExpedicion">Fecha de expedici&oacute;n<span class="required">*</span>:</label>
				</td>
				<td>
					<input type="text" id="fechaExpedicion" class="form-control" name="fechaExpedicionString" value="" readonly="readonly" />	
				</td>
			</tr>
			
		</table>
	</fieldset>
	
</form>


</div>		