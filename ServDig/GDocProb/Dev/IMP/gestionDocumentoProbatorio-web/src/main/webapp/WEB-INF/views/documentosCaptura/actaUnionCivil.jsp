<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/additional-methods.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/documentos/actaUnionCivil.js" htmlEscape="true" />"></script>	
	

<div class="form-comment">
<form id="formdocument">
	<fieldset>
		<table class="table table-striped table-bordered">
			<tr >
				<td align="left">
					<label class="control-label" for="lugarEmision">Lugar de emisi&oacute;n<span class="required">*</span>: </label>
				</td>
				<td align="left">
					<input type="text" class= "alfanumerico_espacios" id = "lugarEmision" name="lugarEmision" value="" maxlength="50"/>
				</td>
			</tr>
			<tr >
				<td align="left">
					<label class="control-label" for="fechaEmision">Fecha de emisi&oacute;n<span class="required">*</span>:</label>
				 </td>
				<td align="left">
					<input type="text" class="form-control" id="fechaEmision" name="fechaEmision" value="" readonly="readonly" />
				</td>
			</tr>
			<tr >
				<td align="left">
					<label class="control-label" for="autoridadEmisora">Autoridad Emisora<span class="required">*</span>:</label>
				</td>
				<td align="left">
					<combo:creaCombo 
						idHtml="autoridadEmisora" 
						idHtmlContenedor="formdocument"
						entidad="mx.gob.imss.ctirss.delta.persistence.DicAutoridadEmisora"
						mostrarSoloActivos="true"  
						cssClassname="form-control"
					/>
					<input type="hidden" id="DicAutoridadEmisora" name="nombreAutoridadEmisora" />			
				</td>
			</tr>
			<tr >
				<td align="left">
					<label class="control-label" for="entidad">Estado<span class="required">*</span>:</label>
				</td>
				<td align="left">
					<combo:creaCombo 
						idHtml="entidad" 
						idHtmlContenedor="formdocument"
						entidad="mx.gob.imss.ctirss.delta.persistence.DgCatEstado"
						mostrarSoloActivos="true"  
						cssClassname="form-control"
					/>
					<input type="hidden" id="entidadHidden" name="nombreEntidad" />			
				</td>
			</tr>
			<tr >
				<td align="left">
					<label class="control-label" for="noReferencia">N&uacute;mero de referencia<span class="required">*</span>: </label>
				</td>
				<td align="left">
					<input type="text" class= "alfanum" id = "noReferencia" name="noReferencia" value="" maxlength="20"/>
				</td>
			</tr>
		</table>
	</fieldset>
	
</form>


</div>		