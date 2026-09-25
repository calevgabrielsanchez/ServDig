<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/additional-methods.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/documentos/actaPactoCivil.js" htmlEscape="true" />"></script>
<div class="form-comment">
<form id="formdocument">
	<fieldset>
		<legend><strong>Aca de pacto civil de solidaridad</strong></legend>
		<center>
		<table>
		
			<tr >
				<td align="left">
					<label class="control-label" for="noActa">N&uacute;mero de acta:</label>
				</td>
				<td align="left">
					<input type="text" name="noActa" class="entero_15 form-control" value="" style="width: 200px" maxlength="16"/>
				</td>
			</tr>
			
			<tr >
				<td align="left">
					<label class="control-label" for="noFoja">N&uacute;mero de foja:</label>
				</td>
				<td align="left">
					<input type="text" name="noFoja" class="entero_15 form-control" value="" style="width: 200px" maxlength="8"/>
				</td>
			</tr>
			
			<tr >
				<td align="left">
					<label class="control-label" for="noLibro">N&uacute;mero de libro:</label>
				</td>
				<td align="left">
					<input type="text"  name = "noLibro" class="entero_15 form-control" value="" style="width: 200px" maxlength="8"/>
				</td>
			</tr>
			<tr >
				<td align="left">
					<label class="control-label" for="entidad">Entidad federativa:</label>
				</td>
				<td align="left">
					<combo:creaCombo idHtml="entidad" idHtmlContenedor="formdocument"
								entidad="mx.gob.imss.ctirss.delta.persistence.DgCatEstado"
								mostrarSoloActivos = "true" />
					<input type="hidden" id="entidadHidden" name="nombreEntidad" 
					/>		
				</td>
			</tr>
			<tr >
				<td  align="left">
					<label class="control-label" for="municipio">Municipio:</label>
				</td>
				<td align="left">
					<combo:creaCombo 		
						entidad="mx.gob.imss.ctirss.delta.persistence.DgCatMunicipio" 
		                idHtml="municipio" 
		                entidadPadre="dgCatEstado.cveEnt"
		                idHtmlPadre	="entidad"
		                idHtmlContenedor="formdocument"
		                mostrarSoloActivos = "false" 
		            />
		            <input type="hidden" id="municipioHidden" name="nombreMunicipio" />	
				</td>
			</tr>
			<tr >
				<td align="left">
					<label class="control-label" for="fechaSuceso">Fecha expedici&oacute;n:</label>
				</td>
				<td align="left">
					<input type="text" class="form-control" id="fechaSuceso" name="fechaSuceso" value="" style="width: 200px" readonly="readonly" onclick=" $( this ).datepicker();"/>
				</td>
			</tr>
		
			<tr >
				<td align="left">
					<label class="control-label" for="tomo">Tomo:</label>
				</td>
				<td align="left">
					<input type="text" class="entero_15 form-control" name = "tomo" value="" style="width: 200px" maxlength="20"/>
				</td>
			</tr>
			
			<tr >
				<td align="left">
					<label class="control-label" for="noJuzgado">N&uacute;mero de juzgado u oficial&iacute;a:</label>
				</td>
				<td align="left">
					<input type="text" name="noJuzgado" value="" class="entero_15 form-control" style="width: 200px" maxlength="8"/>
				</td>
			</tr>
		</table>
		</center>
	</fieldset>
	
</form>


</div>		