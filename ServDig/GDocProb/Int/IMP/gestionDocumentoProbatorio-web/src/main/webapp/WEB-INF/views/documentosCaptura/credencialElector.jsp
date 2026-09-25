<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>


<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
	<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
	
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/additional-methods.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/documentos/credencialElector.js" htmlEscape="true" />"></script>
<div class="form-comment">
<form id="formdocument">
	<fieldset>
		<div id="divErrorIfe" class="alert alert-danger" style="display:none">
		</div>
		<div id="divTipoCredencial">
			<table class="table table-striped table-bordered">
				<tr >
					<td style="width:250px">
						<label class="control-label" for="tipoCredencial">Tipo credencial<span class="required">*</span>:</label>
					</td>
					<td>
						<select class = "form-control" id="tipoCredencial" name = "tipoCredencial">
							<option value="">Elige por favor</option>
							<option value="A">A</option>
							<option value="B">B</option>
							<option value="C">C</option>
							<option value="D">D</option>
							<option value="E">E</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						Si no conoces el tipo de credencial que tienes verificalo 
						<a href="http://listanominal.ife.org.mx/consulta_permanente_ln.htm" class="btn-link" target="_blank">aqu&iacute;</a>.
					</td>
				</tr>
			</table>
		</div>
		
		<div id="camposAnioNumero" style="display:none">
			<table class="table table-striped table-bordered">
				<tr>
					<td style="width:250px">
						<label class="control-label" for="anioRegistro">A&ntilde;o de expedici&oacute;n<span class="required">*</span>:</label>
					</td>
					<td>
						<input type="text"  class="entero_4 form-control" name="anioRegistro" id = "anioRegistro" maxlength="4"/>
					</td>
				</tr>
				<tr>
					<td>
						<label id = "labelCodigo" class="control-label" for="codigoSeguridad">N&uacute;mero(OCR)<span class="required">*</span>:</label>
					</td>
					<td align="left">
						<input type="text"  class="alfanumerico_15 form-control" name="codigoSeguridad" id = "codigoSeguridad" value="" maxlength="13"/>
					</td>
				</tr>
			</table>
		</div>		
		<div id="camposComplementarios" style="display:none">
			<table class="table table-striped table-bordered">
				<tr>
					<td style="width:250px">
						<label class="control-label" for="emision">N&uacute;mero de emisi&oacute;n<span class="required">*</span>:</label>
					</td>
					<td>
						<input type="text" class="form-control" name="emision" id="emision" value="" maxlength="2"/>
					</td>
				</tr>
				<tr >
					<td align="left">
						<label class="control-label" for="claveElector">Clave de elector<span class="required">*</span>:</label>
					</td>
					<td align="left">
						<input type="text" class="alfanumerico_15 form-control" name="claveElector" id="claveElector" value="" maxlength="18"/>
					</td>
				</tr>			
			</table>
		</div>
	</fieldset>
	
</form>


</div>		