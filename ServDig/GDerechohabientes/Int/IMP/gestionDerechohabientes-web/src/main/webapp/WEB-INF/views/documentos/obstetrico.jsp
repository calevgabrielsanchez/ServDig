<%@ include file="../general/taglibs.jsp"%>
<%@taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/documentos/obstetrico.js" htmlEscape="true" />"></script>


<div class="form-comment">
<form id="formdocument">
	<fieldset>
		<!-- <legend><strong><spring:message code="label.datosGrupo"/> </strong></legend> -->
		<legend><strong>Obst&eacute;trico</strong></legend>
		<center>
		<table>
			<tr >
				<td align="left">Fecha de expedici&oacute;n:</td>
				<td>
					<input type="text" id="fechaExpedicion" name="fechaExpedicion" value="" style="width: 200px" readonly="readonly"/>	
				</td>
			</tr>
			<tr >
				<td align="left">Fecha probable de concepci&oacute;n:</td>
				<td>
					<input type="text" id="fechaConcepcion" name="fechaConcepcion" value="" style="width: 200px" readonly="readonly" />	
				</td>
			</tr>
			<tr >
				<td align="left">Fecha de parto:</td>
				<td>
					<input type="text" id="fechaParto" name="fechaParto" value="" style="width: 200px" readonly="readonly"/>	
				</td>
			</tr>
			
			<tr >
				<td align="left">Fecha de certificaci&oacute;n m&eacute;dica:</td>
				<td>
				<input type="text" id="fechaCertificacionMedico" name="fechaCertificacionMedico" value="" style="width: 200px" readonly="readonly" />
					
				</td>
			</tr>
			
			<tr >
				<td><br> </td>
				<td><br> </td>
			</tr>
			
			<tr >
				<td align="left">Nombre del m&eacute;dico familiar que certific&oacute; :</td>
				<td align="left">
					<select id="selectMedico" name="medicoFamiliar"  onchange="showMatricula();"></select>
					<input type="hidden" name="nombreMed" id="nombreMed"/>
					<input type="hidden" name="primerApeidoMed" id="primerApeidoMed"/>
					<input type="hidden" name="segundoApeidoMed" id="segundoApeidoMed"/>
					<input type="hidden" name="matriculaMed" id="matriculaMed"/>
					<input type="hidden" name="idMedicoEspecialidad" id="idMedicoEspecialidad" value="-1"/>
				</td>
			</tr>
			<tr >
				<td align="left">Matr&iacute;cula :</td>
				<td align="left"><div id=noMatricula>N/A</div></td>
			</tr>
			
		</table>
		</center>
	</fieldset>
	
</form>


</div>		