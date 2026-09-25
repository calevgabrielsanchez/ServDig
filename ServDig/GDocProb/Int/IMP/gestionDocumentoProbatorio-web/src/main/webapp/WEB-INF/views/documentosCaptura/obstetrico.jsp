<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/additional-methods.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/documentos/obstetrico.js" htmlEscape="true" />"></script>


<div class="form-comment">
<form id="formdocument">
	<input type="hidden" id="idUmf" value="${idUmf}"/>
	<fieldset>
		<table class="table table-striped table-bordered">
			<tr >
				<td align="left">
					<label class="control-label" for="fechaExpedicion">Fecha de expedici&oacute;n<span class="required">*</span>:</label>
				</td>
				<td>
					<input type="text" class="form-control" id="fechaExpedicion" name="fechaExpedicion" value="" readonly="readonly"/>	
				</td>
			</tr>
			<tr >
				<td align="left">
					<label class="control-label" for="fechaConcepcion">Fecha probable de concepci&oacute;n<span class="required">*</span>:</label>
				</td>
				<td>
					<input type="text" id="fechaConcepcion" class="form-control" name="fechaConcepcion" value="" readonly="readonly" />	
				</td>
			</tr>
			<tr >
				<td align="left">
					<label class="control-label" for="fechaParto">Fecha de parto<span class="required">*</span>:</label>
				</td>
				<td>
					<input type="text" id="fechaParto" class="form-control" name="fechaParto" value="" readonly="readonly"/>	
				</td>
			</tr>
			
			<tr >
				<td align="left">
					<label class="control-label" for="fechaCertificacionMedico">Fecha de certificaci&oacute;n m&eacute;dica<span class="required">*</span>:</label>
				</td>
				<td>
				<input type="text" class="form-control" id="fechaCertificacionMedico" name="fechaCertificacionMedico" value="" readonly="readonly" />
					
				</td>
			</tr>
			<tr >
				<td align="left">
					<label class="control-label" for="selectMedico">Nombre del m&eacute;dico familiar que certific&oacute;<span class="required">*</span>:</label>
				</td>
				<td align="left">
					<select class="form-control" id="selectMedico" name="medicoFamiliar"  onchange="showMatricula();"></select>
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
			
		</table>
	</fieldset>
	
</form>


</div>		