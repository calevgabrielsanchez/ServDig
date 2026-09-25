<%@ include file="../general/taglibs.jsp"%>
<%@taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<%@ page import="mx.gob.imss.ctirss.delta.derechohabientes.web.controller.CapturaDatosDocsController"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/documentos/certificadoNacimiento.js" htmlEscape="true" />"></script>


<div class="form-comment">
	<form id="formdocument">
		<fieldset>
		<legend><strong>Certificado de nacimiento</strong></legend>
			<div align="center">
				<table>
					<tr>
						<td align="left">Folio:</td>
						<td align="left"><input type="text" name="noFolio" value=""
							style="width: 200px" maxlength="8"/></td>
					</tr>
					<tr>
						<td align="left">Fecha de alumbramiento:</td>
						<td align="left"><input type="text" id="fechaAlumbramiento"
							name="fechaAlumbramiento" value="" style="width: 200px"
							readonly="readonly"  /></td>
					</tr>
					<tr>
						<td><br>
						</td>
						<td><br>
						</td>
					</tr>
					
					<tr>
						<td align="left">Sexo:</td>
						<td align="left">
							<combo:creaCombo idHtml="idSexo" idHtmlContenedor="formdocument" 
							entidad="mx.gob.imss.ctirss.delta.persistence.DicSexo" 
							mostrarSoloActivos = "true" />
							<input type="hidden" id="sexoHidden" name="descripcionSexo"/>
						</td>
					</tr>
					
					<tr>
						<td align="left">Lugar de alumbramiento:</td>
						<td align="left"><input type="text" name="desLugarAlumbramiento" value="" style="width: 300px" maxlength="<%= CapturaDatosDocsController.UMF_NOMBRE_TAMANO %>" /></td>
					</tr>
					
					
					<tr >
						<td align="left">Fecha de expedici&oacute;n:</td>
						<td align="left">
							<input type="text" id="fechaExpedicion" name="fechaExpedicion" value="" style="width: 200px" readonly="readonly" />	
						</td>
					</tr>
				</table>
			</div>
			
		</fieldset>
	</form>
</div>
