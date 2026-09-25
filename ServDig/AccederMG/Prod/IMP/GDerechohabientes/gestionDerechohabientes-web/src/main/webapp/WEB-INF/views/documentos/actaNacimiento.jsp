<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
	<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/documentos/actaNacimiento.js" htmlEscape="true" />"></script>
<div class="form-comment">
<form id="formdocument">
	<fieldset>
		<!-- <legend><strong><spring:message code="label.datosGrupo"/> </strong></legend> -->
		<legend><strong>Acta de Nacimiento</strong></legend>
		<center>
		<table>
		
			<tr >
				<td align="left">N&uacute;mero de acta:</td>
				<td align="left">
					<input type="text" name="noActa" class="entero_15" value="" style="width: 200px" maxlength="16"/>
				</td>
			</tr>
			
			<tr >
				<td align="left">N&uacute;mero de foja:</td>
				<td align="left">
					<input type="text" name="noFoja" class="entero_15" value="" style="width: 200px" maxlength="8"/>
				</td>
			</tr>
			
			<tr >
				<td align="left">N&uacute;mero de libro:</td>
				<td align="left">
					<input type="text"  name = "noLibro" class="entero_15" value="" style="width: 200px" maxlength="8"/>
				</td>
			</tr>
			<tr >
				<td><br></td>
				<td><br></td>
			</tr>
			<tr >
				<td align="left">Entidad federativa:</td>
				<td align="left">
					<combo:creaCombo idHtml="entidad" idHtmlContenedor="formdocument"
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
		                idHtmlPadre	="entidad"
		                idHtmlContenedor="formdocument"
		                mostrarSoloActivos = "false" 
		            />
				</td>
			</tr>
			<tr >
				<td><br></td>
				<td><br></td>
			</tr>
			<tr >
				<td align="left">Fecha expedici&oacute;n:</td>
				<td align="left">
					<input type="text"  id="fechaSuceso" name="fechaSuceso" value="" style="width: 200px" readonly="readonly" onclick=" $( this ).datepicker();"/>
				</td>
			</tr>
				<tr >
			<td align="left">A&ntilde;o de registro:</td>
				<td align="left">
					<input type="text" name="anio" value="" class="entero_4" style="width: 200px" maxlength="4"/>
				</td>
			</tr>
		
			<tr >
				<td align="left">Tomo:</td>
				<td align="left">
					<input type="text" class="entero_15" name = "tomo" value="" style="width: 200px" maxlength="20"/>
				</td>	<tr >
				<td align="left">CRIP:</td>
				<td align="left">
					<input type="text"  name="crip" value="" class="entero_15" style="width: 200px" maxlength="18"/>
				</td>
			</tr>
			<tr >
				<td align="left">N&uacute;mero de juzgado u oficial&iacute;a:</td>
				<td align="left">
					<input type="text" name="noJuzgado" value="" class="entero_15" style="width: 200px" maxlength="8"/>
				</td>
			</tr>
						
			
		</table>
		</center>
	</fieldset>
	
</form>


</div>		