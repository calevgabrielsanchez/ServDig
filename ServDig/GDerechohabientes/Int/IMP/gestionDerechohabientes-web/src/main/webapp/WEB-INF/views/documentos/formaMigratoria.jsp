<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/documentos/formaMigratoria.js" htmlEscape="true" />"></script>	


<div class="form-comment">
<form id="formdocument">
	<fieldset>
		<legend><strong>Forma Migratoria</strong></legend>
		<center>
		<table>
			<tr >
				<td align="left">N&uacute;mero de documento: </td>
				<td align="left">
					<input type="text" class= "entero_20" id = "numeroDoc" name="numeroDoc" value="" style="width: 250px" maxlength="20"/>
				</td>
			</tr>
			
			<tr >
				<td><br></td>
				<td><br></td>
			</tr>
			
			<tr >
				<td align="left">Pa&iacute;s de origen: </td>
				<td align="left">
					<combo:creaCombo idHtml="paisOrigen" idHtmlContenedor="formdocument"
								entidad="mx.gob.imss.ctirss.delta.persistence.DicPai" 
								mostrarSoloActivos = "true" />	
					<input type="hidden" id="paisOrigenHidden" name="nombrePaisOrigen"/> 
				</td>
			</tr>
			
			<tr >
				<td><br></td>
				<td><br></td>
			</tr>
			
			<tr >
				<td align="left">Fecha de expedici&oacute;n: </td>
				<td align="left">
					<input type="text" id="fechaExpedicion" name="fechaExpedicion" value="" style="width: 250px"  readonly="readonly" onclick=" $( this ).datepicker();"/>
				</td>
			</tr>
			<tr >
				<td align="left">Fecha de vencimiento: </td>
				<td align="left">
					<input type="text" id="fechaVencimiento" name="fechaVencimiento" value="" style="width: 250px"  readonly="readonly" onclick=" $( this ).datepicker();"/>
				</td>
			</tr>
			
			<tr >
				<td><br></td>
				<td><br></td>
			</tr>
			
			<tr >
				<td align="left">Calidad migratoria: </td>
				<td align="left">
					<combo:creaCombo idHtml="calidadMigratoria" idHtmlContenedor="formdocument"
								entidad="mx.gob.imss.ctirss.delta.persistence.DicCalidadCaracMigrat" 
								mostrarSoloActivos = "true" />	
					<input type="hidden" id="calidadMigratoriaHidden" name="nombreCalidadMigratoria"/> 
				</td>
			</tr>
		</table>
		</center>
	</fieldset>
	
</form>


</div>		