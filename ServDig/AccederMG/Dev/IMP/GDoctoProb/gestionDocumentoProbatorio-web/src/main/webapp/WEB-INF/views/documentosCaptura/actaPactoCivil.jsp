<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/additional-methods.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/documentos/actaPactoCivil.js" htmlEscape="true" />"></script>
<div class="form-comment">
<form id="formdocument">
	<fieldset>
		<table class="table table-striped table-bordered">
		
			<tr >
				<td align="left">
					<label class="control-label" for="noActa">N&uacute;mero de acta<span class="required">*</span>:</label>
				</td>
				<td align="left">
					<input type="text" name="noActa" class="entero_15 form-control" value="" maxlength="16"/>
				</td>
			</tr>
			
			<tr >
				<td align="left">
					<label class="control-label" for="noFoja">N&uacute;mero de foja<span class="required">*</span>:</label>
				</td>
				<td align="left">
					<input type="text" name="noFoja" class="entero_15 form-control" value="" maxlength="8"/>
				</td>
			</tr>
			
			<tr >
				<td align="left">
					<label class="control-label" for="noLibro">N&uacute;mero de libro<span class="required">*</span>:</label>
				</td>
				<td align="left">
					<input type="text"  name = "noLibro" class="entero_15 form-control" value="" maxlength="8"/>
				</td>
			</tr>
			<tr >
				<td align="left">
					<label class="control-label" for="entidad">Estado<span class="required">*</span>:</label>
				</td>
				<td align="left">
					<combo:creaCombo 
						idHtml="entidad" idHtmlContenedor="formdocument"
						entidad="mx.gob.imss.ctirss.delta.persistence.DgCatEstado"
						mostrarSoloActivos="true" 
						cssClassname="form-control"
					/>
					<input type="hidden" id="entidadHidden" name="nombreEntidad" />		
				</td>
			</tr>
			<tr >
				<td  align="left">
					<label class="control-label" for="municipio">Municipio o alcald&iacute;a<span class="required">*</span>:</label>
				</td>
				<td align="left">
					<combo:creaCombo 		
						entidad="mx.gob.imss.ctirss.delta.persistence.DgCatMunicipio" 
		                idHtml="municipio" 
		                entidadPadre="dgCatEstado.cveEnt"
		                idHtmlPadre	="entidad"
		                idHtmlContenedor="formdocument"
		                mostrarSoloActivos="false" 
		                cssClassname="form-control"
		            />
		            <input type="hidden" id="municipioHidden" name="nombreMunicipio" />	
				</td>
			</tr>
			<tr >
				<td align="left">
					<label class="control-label" for="fechaSuceso">Fecha expedici&oacute;n<span class="required">*</span>:</label>
				</td>
				<td align="left">
					<input type="text" class="form-control" id="fechaSuceso" name="fechaSuceso" value="" readonly="readonly" onclick=" $( this ).datepicker();"/>
				</td>
			</tr>
		
			<tr >
				<td align="left">
					<label class="control-label" for="tomo"><span class="required">*</span>:</label>
				</td>
				<td align="left">
					<input type="text" class="entero_15 form-control" name = "tomo" value="" maxlength="20"/>
				</td>
			</tr>
			
			<tr >
				<td align="left">
					<label class="control-label" for="noJuzgado">N&uacute;mero de juzgado u oficial&iacute;a<span class="required">*</span>:</label>
				</td>
				<td align="left">
					<input type="text" name="noJuzgado" value="" class="entero_15 form-control" maxlength="8"/>
				</td>
			</tr>
		</table>
	</fieldset>
	
</form>


</div>		