<%@ include file="../general/taglibs.jsp"%>
<style>
	.obligatorio {
		color: red
	}
</style>

<div class="form-comment" id="cuerpo">
<br>
<h4 align="center" id="tituloTramite">
	<spring:message code="tramite.acuerdoPadres.titulo"/>
</h4>
<%@ include file="../general/taglibs.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/acuerdo/capturaDatos.js" htmlEscape="true" />"></script>

<%@ include file="/WEB-INF/views/prorrogas/grupoFamiliar.jsp" %>

<fieldset>
		<legend><strong>Datos del tr&aacute;mite</strong></legend>
		
		
		<form id="tramite" name="tramite" action="#" method="POST">
		<table style="width: 100%">
			<tbody>
			<tr>
				<td align="right">
					<span class="obligatorio">*</span>N&uacute;mero de acuerdo:
				</td>
				<td>
					<input type="text" id="numeroAcuerdo" name="numeroAcuerdo"/>
				</td>
				<td align="right">
					<span class="obligatorio">*</span>Fecha de acuerdo:
				</td>
				<td>
					<input type="text" id="fechaAcuerdo" name="fechaAcuerdo" readOnly="readonly"/>
				</td>
			</tr>
			<tr>
				<td align="right">
					<span class="obligatorio">*</span>Observaciones:
			</td>
				<td colspan="3">
				<textarea id="observacion" name="observacion" style="height: 40px; width: 800px;">${tramiteDtoSession.fundamentoLegal}</textarea>
				</td>
			</tr>
			<tr>
				<td colspan="6"><span class="obligatorio">*</span> Campos obligatorios</td>
			</tr>
			</tbody>
		</table>
		</form>
	</fieldset>
	<br><br>
	<div align="center">
		<form>
			<table>
				<tr>
					<td align="center">
						<input id="aceptar" type="button" value="Aceptar" class="mboton"> 
						<input id="cancelarTramiteAdmin" type="button" value="Cancelar" class="mboton"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
