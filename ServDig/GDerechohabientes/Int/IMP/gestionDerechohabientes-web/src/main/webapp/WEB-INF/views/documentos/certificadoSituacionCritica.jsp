<%@ include file="../general/taglibs.jsp"%>
<%@taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>


<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/documentos/certificadoSitCritica.js" htmlEscape="true" />"></script>
<div class="form-comment">
<form id="formdocument">
	<fieldset>
		<legend><strong>Certificado de situaci&oacute;n cl&iacute;nica del derechohabiente por incapacidad f&iacute;sica o ps&iacute;quica</strong></legend>
		<center>
		<table>
			<tr >
				<td align="left">Enfermedad que padece (diagn&oacute;stico):</td>
				<td align="left">
					<textarea class="alfanumerico_espacios" id="enfermedadPadecida"  name="enfermedadPadecida"  style="width: 200px" ></textarea>
				</td>
			</tr>
			
			<tr >
				<td align="left">Fecha probable de inicio :</td>
				<td align="left">
				<input type="text"  id="fechaProbableInicio" name="fechaProbableInicio" value="" style="width: 200px" readonly="readonly" onclick=" $( this ).datepicker();"/>
					
				</td>
			</tr>
			<tr >
				<td align="left">Fecha probable de t&eacute;rmino:</td>
				<td align="left">
				<input type="text"  id="fechaTerminoIncapacidad" name="fechaTerminoIncapacidad" value="" style="width: 200px" readonly="readonly" onclick=" $( this ).datepicker();"/>
					
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
				<td align="left">Fecha de expedici&oacute;n:</td>
				<td>
					<input type="text" id="fechaExpedicion" name="fechaExpedicionString" value="" style="width: 200px" readonly="readonly" />	
				</td>
			</tr>
			
		</table>
		</center>
	</fieldset>
	
</form>


</div>		