<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/additional-methods.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/documentos/curp.js" htmlEscape="true" />"></script>
<div class="form-comment">
<form id="formdocument">
	<fieldset>
		<legend><strong>CURP</strong></legend>
		<table class="table table-striped table-bordered">
				
			<tr >
				<td align="left">
					<label class="control-label" for="curp">CURP:</label>
				</td>
				<td align="left">
					<input type="text"  id = "curp" class="alfanum form-control" name="curp" value="" style="width: 200px" maxlength="18"/>
				</td>
			</tr>
			<tr >
				<td align="left">
					<label class="control-label" for="entidadFederativa">Estado:</label>
				</td>
				<td align="left">
					<combo:creaCombo
						 idHtml="entidadFederativa" 
						 idHtmlContenedor="formdocument"
						 entidad="mx.gob.imss.ctirss.delta.persistence.DgCatEstado"
						 mostrarSoloActivos="false" 
						 cssClassname="form-control"
					/>
				</td>
			</tr>
			<tr >
				<td  align="left">
					<label class="control-label" for="municipio">Municipio o alcald&iacute;a:</label>
				</td>
				<td align="left">
					<combo:creaCombo 		
						entidad="mx.gob.imss.ctirss.delta.persistence.DgCatMunicipio" 
		                idHtml="municipio" 
		                entidadPadre="dgCatEstado.cveEnt"
		                idHtmlPadre	="entidadFederativa"
		                idHtmlContenedor="formdocument"
		                mostrarSoloActivos="false" 
		                cssClassname="form-control"
		            />
				</td>
			</tr>
			<tr >
				<td align="left">
					<label class="control-label" for="fechaInscripcion">Fecha inscripci&oacute;n:</label>
				</td>
				<td align="left">
					<input type="text" class="form-control" id="fechaInscripcion" name="fechaInscripcion" value="" style="width: 200px" readonly="readonly" onclick=" $( this ).datepicker();"/>
				</td>
			</tr>
			<tr >
				<td align="left">
					<label class="control-label" for="folio">N&uacute;mero de folio:</label>
				</td>
				<td align="left">
					<input type="text"  class="entero_15 form-control" name="folio" value="" style="width: 200px" maxlength="15"/>
				</td>
			</tr>
			
			<tr id="anioRegRow">
				<td align="left">
					<label class="control-label" for="anioRegistro">A&ntilde;o de registro:</label>
				</td>
				<td align="left">
					<input type="text"  name="anioRegistro" value="" style="width: 200px" class="entero_4 form-control" maxlength="4"/>
				</td>
			</tr>
			
			<tr id="libroRow">
				<td align="left">
					<label class="control-label" for="noLibro">N&uacute;mero de libro:</label>
				</td>
				<td align="left">
					<input type="text"  name="noLibro" value="" class="entero_8 form-control" style="width: 200px"  maxlength="8"/>
				</td>
			</tr>
			
			<tr id="actaRow">
				<td align="left">
					<label class="control-label" for="noActa">N&uacute;mero de acta:</label>
				</td>
				<td align="left">
					<input type="text"  name="noActa" value="" class="entero_8 form-control" style="width: 200px" maxlength="8"/>
				</td>
			</tr>
			<tr id="tomoRow">
				<td align="left">
					<label class="control-label" for="noTomo">N&uacute;mero de tomo:</label>
				</td>
				<td align="left">
					<input type="text"  name="noTomo" value="" class="entero_8 form-control" style="width: 200px" maxlength="8"/>
				</td>
			</tr>
			<tr id="cripRow">
				<td align="left">
					<label class="control-label" for="crip">CRIP:</label>
				</td>
				<td align="left">
					<input type="text"  name="crip" value="" class="alfanum form-control" style="width: 200px" maxlength="18"/>
				</td>
			</tr>
		
			<tr id="fojaRow">
				<td align="left">
					<label class="control-label" for="noFoja">N&uacute;mero de foja:</label>
				</td>
				<td align="left">
					<input type="text"  name="noFoja" value="" style="width: 200px" class="alfanum form-control" maxlength="8"/>
				</td>
			</tr>				
		</table>
	</fieldset>
	
</form>


</div>		