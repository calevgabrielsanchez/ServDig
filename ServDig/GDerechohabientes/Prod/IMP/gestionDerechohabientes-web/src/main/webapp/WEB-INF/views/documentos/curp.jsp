<%@ include file="../general/taglibs.jsp"%>
<%@taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/documentos/curp.js" htmlEscape="true" />"></script>
<div class="form-comment">
<form id="formdocument">
	<fieldset>
		<!-- <legend><strong><spring:message code="label.datosGrupo"/> </strong></legend> -->
		<legend><strong>CURP</strong></legend>
		<center>
		<table>
				
			<tr >
				<td align="left">CURP:</td>
				<td align="left">
					<input type="text"  id = "curp" class="alfanum" name="curp" value="" style="width: 200px" maxlength="18"/>
				</td>
			</tr>
			<tr >
				<td><br></td>
				<td><br></td>
			</tr>
			<tr >
				<td align="left">Entidad federativa:</td>
				<td align="left">
					<combo:creaCombo idHtml="entidadFederativa" idHtmlContenedor="formdocument"
								entidad="mx.gob.imss.ctirss.delta.persistence.DgCatEstado"
								mostrarSoloActivos = "true" />
				</td>
			</tr>
			
			
			<tr >
				<td><br></td>
				<td><br></td>
			</tr>
			<tr >
				<td  align="left">Municipio:</td>
				<td align="left">
					<combo:creaCombo 		
						entidad="mx.gob.imss.ctirss.delta.persistence.DgCatMunicipio" 
		                idHtml="municipio" 
		                entidadPadre="dgCatEstado.cveEnt"
		                idHtmlPadre	="entidadFederativa"
		                idHtmlContenedor="formdocument"
		                mostrarSoloActivos = "false" 
		            />
				</td>
			</tr>
			<tr >
				<td align="left">Fecha Inscripci&oacute;n:</td>
				<td align="left">
					<input type="text" id="fechaInscripcion" name="fechaInscripcion" value="" style="width: 200px" readonly="readonly" onclick=" $( this ).datepicker();"/>
				</td>
			</tr>
	
			
			<tr >
				<td align="left">N&uacute;mero de folio:</td>
				<td align="left">
					<input type="text"  class="entero_15" name="folio" value="" style="width: 200px" maxlength="15"/>
				</td>
			</tr>
			
			<tr id="anioRegRow">
				<td align="left">A&ntilde;o de registro:</td>
				<td align="left">
					<input type="text"  name="anioRegistro" value="" style="width: 200px" class="entero_4" maxlength="4"/>
				</td>
			</tr>
			
			<tr id="libroRow">
				<td align="left">N&uacute;mero de libro:</td>
				<td align="left">
					<input type="text"  name="noLibro" value="" class="entero_8" style="width: 200px"  maxlength="8"/>
				</td>
			</tr>
			
			<tr id="actaRow">
				<td align="left">N&uacute;mero de acta:</td>
				<td align="left">
					<input type="text"  name="noActa" value="" class="entero_8" style="width: 200px" maxlength="8"/>
				</td>
			</tr>
			<tr id="tomoRow">
				<td align="left">N&uacute;mero de tomo:</td>
				<td align="left">
					<input type="text"  name="noTomo" value="" class="entero_8" style="width: 200px" maxlength="8"/>
				</td>
			</tr>
			<tr id="cripRow">
				<td align="left">CRIP:</td>
				<td align="left">
					<input type="text"  name="crip" value="" class="alfanum" style="width: 200px" maxlength="18"/>
				</td>
			</tr>
		
			<tr id="fojaRow">
				<td align="left">N&uacute;mero de foja:</td>
				<td align="left">
					<input type="text"  name="noFoja" value="" style="width: 200px" class="alfanum" maxlength="8"/>
				</td>
			</tr>
			
			
			<tr >
				<td>
					<br> 
				</td>
				<td>
					<br>
				</td>
			</tr>
		
			
								
		</table>
		</center>
	</fieldset>
	
</form>


</div>		