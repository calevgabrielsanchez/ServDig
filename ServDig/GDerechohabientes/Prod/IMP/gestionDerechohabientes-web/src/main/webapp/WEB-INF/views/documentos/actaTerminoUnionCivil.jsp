<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/documentos/actaUnionCivil.js" htmlEscape="true" />"></script>	
	
	
<div class="form-comment">
<form id="formdocument">

	<fieldset>
		<legend><strong>Acta de t&eacute;rmino de uni&oacute;n civil</strong></legend>
		<center>
		<table>
			<tr >
				<td align="left">Lugar de emisi&oacute;n: </td>
				<td align="left">
					<input type="text" id="lugarEmision" class="alfanumerico_espacios" name="lugarEmision" value="" style="width: 250px" maxlength="50"/>  
				</td>
			<td><br></td>
			<tr >
				<td align="left">Fecha de emisi&oacute;n: </td>
				<td align="left">
					<input type="text" id="fechaEmision" name="fechaEmision" value="" style="width: 250px" readonly="readonly" onclick=" $( this ).datepicker();"/>
				</td>
			</tr>
			<td><br></td>
			<tr >
				<td align="left">Autoridad Emisora: </td>
				<td align="left">
					<combo:creaCombo idHtml="autoridadEmisora" idHtmlContenedor="formdocument"
								entidad="mx.gob.imss.ctirss.delta.persistence.DicAutoridadEmisora" 
								mostrarSoloActivos = "true" />	
					<input type="hidden" id="autoridadEmisoraHidden" name="nombreAutoridadEmisora"/> 
				</td>
			</tr>	
			<td><br></td>	
			<tr >
				<td align="left">Entidad: </td>
				<td align="left">
					<combo:creaCombo idHtml="entidad" idHtmlContenedor="formdocument"
								entidad="mx.gob.imss.ctirss.delta.persistence.DgCatEstado" 
								mostrarSoloActivos = "true" />	
					<input type="hidden" id="entidadFederativaHidden" name="nombreEntidad"/> 
				</td>
			</tr>
			<td><br></td>
			<tr >
				<td align="left">N&uacute;mero de referencia/Folio/N&uacute;mero de control:</td>
				<td align="left">
					<input type="text" class= "alfanum" id = "noReferencia" name="noReferencia" value="" style="width: 250px" maxlength="20"/>
				</td>
			</tr>				
		</table>
		</center>
	</fieldset>
	
</form>


</div>		