<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<%@ page import="mx.gob.imss.ctirss.delta.gestion.documento.probatorio.web.controller.CapturaDatosDocsController"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/additional-methods.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/documentos/certificadoNacimiento.js" htmlEscape="true" />"></script>


<div class="form-comment">
	<form id="formdocument">
		<fieldset>
			<table class="table table-striped table-bordered">
				<tr>
					<td align="left">
						<label class="control-label" for="noFolio">Folio<span class="required">*</span>:</label>
					</td>
					<td align="left">
						<input class="form-control" type="text" name="noFolio" value="" maxlength="8"/>
					</td>
				</tr>
				<tr>
					<td align="left">
						<label class="control-label" for="fechaAlumbramiento">Fecha de alumbramiento<span class="required">*</span>:</label>
					</td>
					<td align="left">
						<input type="text" id="fechaAlumbramiento" name="fechaAlumbramiento" value="" readonly="readonly"  class="form-control"/>
					</td>
				</tr>
				
				<tr>
					<td align="left"><label class="control-label" for="idSexo">Sexo<span class="required">*</span>:</label></td>
						<td><combo:creaCombo idHtml="idSexo" idHtmlContenedor="formdocument" 
							entidad="mx.gob.imss.ctirss.delta.persistence.DicSexo" mostrarSoloActivos = "true" 
							cssClassname="form-control"/></td>
					</tr>
					
				<tr>
					<td align="left"><label class="control-label" for="desLugarAlumbramiento">Lugar de alumbramiento<span class="required">*</span>:</label></td>
					<td align="left"><input type="text" name="desLugarAlumbramiento" value="" maxlength="<%= CapturaDatosDocsController.UMF_NOMBRE_TAMANO %>" class="form-control"/></td>
					</tr>
				
				<tr>
					<td align="left">
						<label class="control-label" for="fechaExpedicion">Fecha de expedici&oacute;n<span class="required">*</span>:</label>
					</td>
					<td align="left">
						<input type="text" class="form-control" id="fechaExpedicion" name="fechaExpedicion" value="" readonly="readonly" />	
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</div>
