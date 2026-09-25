<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/documentos/actaComun.js" htmlEscape="true" />"></script>	


<div class="form-comment">
<form id="formdocument">
	<fieldset>
		<!-- <legend><strong><spring:message code="label.datosGrupo"/> </strong></legend> -->
		<legend><strong><spring:message code="${tituloComp}"/></strong></legend>
		<center>
		<table>
		
			<tr >
				<td align="left">N&uacute;mero de acta:</td>
				<td align="left">
					<input type="text" class="entero_15" name="noActa" value="" style="width: 200px" maxlength="16"/>
				</td>
			</tr>
			
			<tr >
				<td align="left">N&uacute;mero de foja:</td>
				<td align="left">
					<input type="text" class="entero_8" name="noFoja"  value="" style="width: 200px" maxlength="8"/>
				</td>
			</tr>
			
			<tr >
				<td align="left">N&uacute;mero de libro:</td>
				<td align="left">
					<input type="text" class="entero_8"  name = "noLibro" value="" style="width: 200px" maxlength="8"/>
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
					<input type="hidden" id="entidadHidden" name="nombreEntidad" 
					/>			
				</td>
			</tr>
			<tr >
				<td><br></td>
				<td><br></td>
			</tr>
			<tr >
				<td align="left">Municipio:</td>
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
				<td><br></td>
				<td><br></td>
			</tr>
			<tr >
				<td align="left">Fecha de <spring:message code="${tituloComp}"/>:</td>
				<td align="left">
					<input type="text"  id="fechaSuceso" name="fechaSuceso" value="" style="width: 200px" readonly="readonly" onclick=" $( this ).datepicker();"/>
				</td>
			</tr>
			<tr >
				<td align="left">Fecha de expedici&oacute;n: </td>
				<td align="left">
					<input type="text" id="fechaExpedicion" name="fechaExpedicionString" value="" style="width: 200px" readonly="readonly" onclick=" $( this ).datepicker();"/>
				</td>
			</tr>
			<tr >
				<td align="left">N&uacute;mero de juzgado:</td>
				<td align="left">
					<input type="text" class="entero_8" name="noJuzgado" value="" style="width: 200px" maxlength="8"/>
				</td>
			</tr>
						
		</table>
		</center>
	</fieldset>
	
</form>


</div>		