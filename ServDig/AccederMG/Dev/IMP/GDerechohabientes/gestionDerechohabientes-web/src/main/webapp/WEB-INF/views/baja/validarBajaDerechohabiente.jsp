<%@ include file="../general/taglibs.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>

<style>
	.obligatorio {
		color: red
	}
</style>

<br><br><br>
<div class="form-comment" id="cuerpo">
<c:choose>
<c:when test="${empty errores}">
	<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/baja/validacionBaja.js" htmlEscape="true" />"></script>

	<c:if test="${requiereDocs}">
		<%@ include file="/WEB-INF/views/general/fileUpload/FileUploadImport.jsp" %>
		<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/fileRead/fileRead.js" htmlEscape="true" />"></script>
		<script>
			$(document).ready(
				function() {
					loadFileUpload(${tramite.tipoTramite.idTipoTramite},undefined,undefined,'${tipoDocsNoMostrar}');
					$("#guia").click(
						function() {
							showGuiaTramite(${tramite.tipoTramite.idTipoTramite},${usuarioObj.perfilUsuario.idPerfilUsuario});
						}	
					);
				}	
			)
		</script>
	</c:if>
	<h4 align="center">VALIDACI&Oacute;N DE ${tramite.tipoTramite.descripcion}</h4>
	<div id="mensaje"></div>
	<%@ include file="/WEB-INF/views/prorrogas/grupoFamiliar.jsp" %>
	<fieldset>
		<legend><strong>Datos de la baja</strong></legend>
		
		<input type="hidden" value="${!requiereDocs? 0 : 1}" id="requiereDocs"/>
		
		<form id="validacion" name="validacion" action="#" method="POST">
		<input type="hidden" value="${tramite.persona.idPersona}" id="idPersona" name="idPersona">
		<input type="hidden" value="${solicitud.solicitudId}" id="idSolicitud" name="idSolicitud">
		<input type="hidden" value="${tramite.tipoTramite.idTipoTramite}" id="idTipoTramite" name="idTipoTramite">
		<input type="hidden" value="${tramite.tramiteId}" id="idTramite" name="idTramite">
		<table style="width: 100%">
			<tbody>
			<c:if test="${tramite.tipoTramite.idTipoTramite == 102}">
				<tr>
				<td align="right" style="width: 150px">
					Fecha baja : 
				</td>
				<td align="left" colspan="5">
					<input type="text" id="fechaDefuncion" name="fechaDefuncion" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${fechaDefuncion}"/>" disabled="disabled" style="width: 140px" />
				</td>
				</tr>
			</c:if>
			<c:if test="${tramite.tipoTramite.idTipoTramite == 25}">
				<tr>
				<td align="right" style="width: 150px">
					<span class="obligatorio">*</span><spring:message code="label.fechaDefuncion"/>:
				</td>
				<td align="left" colspan="5">
					<input type="text" id="fechaDefuncion" name="fechaDefuncion" value="${tramite.fechaDefuncion}" readonly="readonly" style="width: 140px" />
				</td>
				</tr>
			</c:if>
			<tr>
				<td align="right">
					<span class="obligatorio">*</span><spring:message code="label.observaciones"/>:
			</td>
				<td colspan="5">
				<textarea id="observaciones" name="observaciones" style="height: 40px; width: 800px;">${tramite.observacion}</textarea>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td colspan="6"><span class="obligatorio">*</span> Campos obligatorios</td>
			</tr>
			</tbody>
		</table>
		</form>
	</fieldset>
	<br>
	<c:if test="${requiereDocs}">
		<div id="cagarDocProbDiv">
		<jsp:include page="/WEB-INF/views/general/fileUpload/fileUpload.jsp"/>	
		<div id="msgDocumentosProb" title ="<spring:message code="titulo.mensajeAviso"/>" style="display:none"> 
			<spring:message code="msgDocumentosProb"/>		
		</div>
		</div>
		<div id="docProbTramDiv">
		</div>
		<br><br>
	</c:if>
	<div align="center">
	<form>
			<table>
				<tr>
					<td align="center">
						<input id="aceptar" type="button" value="Aceptar" class="mboton"> 
						<input id="rechazarTramite" type="button" value="Cancelar" class="mboton"/>
						<input id="rechazarTramiteSinRazones" type="button" value="Regresar" class="mboton"/>
						<!--<input id="guia" type="button" value="Guia de Tramite" class="mboton"/>-->
					</td>
				</tr>
			</table>
			</form>
	</div>
</c:when>
<c:otherwise>
	<%@ include file="/WEB-INF/views/error/paginaExcepcion.jsp" %>
</c:otherwise>
</c:choose>
</div>