<%@ include file="../general/taglibs.jsp"%>
<style>
	.obligatorio {
		color: red
	}
</style>

<div class="form-comment" id="cuerpo">
<br>
<h4 align="center" id="tituloTramite">
<c:choose>
	<c:when test="${tramiteDtoSession.idTipoTramite == 134}">
		BAJA 
	</c:when>
	<c:when test="${tramiteDtoSession.idTipoTramite == 135}">
		SUSPENSI&Oacute;N 
	</c:when>
	<c:otherwise>
		REACTIVACI&Oacute;N 
	</c:otherwise>
</c:choose>
 ADMINISTRATIVA
</h4>
<%@ include file="../general/taglibs.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/tramitesAdministrativos/capturaDatos.js" htmlEscape="true" />"></script>

<%@ include file="/WEB-INF/views/prorrogas/grupoFamiliar.jsp" %>

<fieldset>
		<legend><strong>Datos del tr&aacute;mite</strong></legend>
		
		<input type="hidden" value="${!requiereDocs? 0 : 1}" id="requiereDocs"/>
		
		<form id="tramite" name="tramite" action="#" method="POST">
		<input type="hidden" value="${tramiteDtoSession.idPersona}" id="idPersona" name="idPersona">
		<input type="hidden" value="${tramiteDtoSession.idTipoTramite}" id="idTipoTramite" name="idTipoTramite">
		<input type="hidden" value="${tramiteDtoSession.idSolicitud}" id="idSolicitud" name="idSolicitud">
		<table style="width: 100%">
			<tbody>
				<tr>
				<td align="right" style="width: 150px">
				<!-- 
					<span class="obligatorio">*</span>Matr&iacute;cula del personal de la UMF que solicita la
					<c:choose> 
					<c:when test="${tramiteDtoSession.idTipoTramite==136}">reactivaci&oacute;n</c:when>
					<c:otherwise>eliminaci&oacute;n</c:otherwise>
					</c:choose>:
				 -->
				 &nbsp; 
				</td>
				<td align="left" colspan="5">
				<!-- 	<input type="text" id="matricula" name="matricula" value="${tramiteDtoSession.matricula}" style="width: 140px" maxlength="15"/> -->
				&nbsp;
				</td>
				</tr>
			<tr>
				<td align="right">
					<span class="obligatorio">*</span>Motivo:
			</td>
				<td colspan="5">
				<textarea id="motivo" name="motivo" style="height: 40px; width: 800px;">${tramiteDtoSession.motivo}</textarea>
				</td>
			</tr>
			<tr>
				<td align="right">
					<span class="obligatorio">*</span>Fundamento legal:
			</td>
				<td colspan="5">
				<textarea id="fundamentoLegal" name="fundamentoLegal" style="height: 40px; width: 800px;">${tramiteDtoSession.fundamentoLegal}</textarea>
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
