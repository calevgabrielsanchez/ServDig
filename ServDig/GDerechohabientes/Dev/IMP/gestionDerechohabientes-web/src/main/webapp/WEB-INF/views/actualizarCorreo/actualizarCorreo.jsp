<%@ include file="../general/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/llenarTipoDocumentoProbatorio.jsp" %>
<%@ include file="/WEB-INF/views/common/llenaTipoTramite.jsp" %>

<meta http-equiv="expires" content="-1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"	src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>	
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/login/login.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/registroDerechohabiente/validacionActualizaCorreo.js" htmlEscape="true" />"></script>
<%@ include file="/WEB-INF/views/general/fileUpload/FileUploadImport.jsp" %>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/fileRead/fileRead.js" htmlEscape="true" />"></script>

<script type="text/javascript">

$(document).ready(function() {
loadFileUpload(177,undefined,undefined,'${tipoDocsNoMostrar}');
	$('#btnInciaTramite').click(function(){
			iniciarTramiteRegistroPersonaAutorizada();
		});
	
	
	
	function iniciarTramiteRegistroPersonaAutorizada() {
		$('#formIniciaTramiteActualizacion').submit();	
	}
});
</script>
<head>
	
	<link href="<c:url value="/resources/estilos/imss/estilo.css" />" rel="stylesheet"  type="text/css" />
	<style>
	.ocultar {display: none;}
 	.mostrar {display: block;}
 	
	</style>
				
</head>

<div class="form-comment" align="center">
	<br>
	<fieldset style="width:800px" >
	<c:set var="contextpath" value="<%=request.getContextPath()%>" />
	
	<div style="width: 90%; float: center;" align="justify">
		
			<h2 id="cabecero">Actualizaci&oacute;n de correo electr&oacute;nico</h2>
			 
	</div>
	 <br>	
	<h4 id="infoNormaito" ><spring:message code="label.instrucciones.normativoCE"/></h4>	
	
	<form id="idActualizaCorreo" name="idActualizaCorreo" action="#" method="POST">		
		<center>		
		<table id="ventanillaAdminTable">
			<tr>
				<td >
					<label class="control-label" for="fisica.curp" style="width: 150px">
					<spring:message code="label.curp" />:&nbsp;</label>
					
				</td>
				<td>
				 <c:if test = "${not empty miGrupoFamiliar.derechohabiente.curp}" > 
					
					<input id="curp" name="curp" type="text"
								style="width: 140px"
								 class="alfanumerico_espacios" readonly="readonly" oninput="validarInput(this)" value="<c:out value='${miGrupoFamiliar.derechohabiente.curp}' />" maxlength="18">
					<td>
					<pre id="resultado"></pre>
					</td>			  
				</c:if>
				<c:if test = "${empty miGrupoFamiliar.derechohabiente.curp}" >
					<input id="curp" name="curp" class="alfanumerico_espacios" readonly="readonly" oninput="validarInput(this)" type="text" value="" maxlength="18">
					<td>
					<pre id="resultado"></pre>
					</td>
				</c:if>				
				</td>
			</tr>
			
			<tr>
				<td >
					<label class="control-label" for="actualizacion.correo" style="width: 150px">
					<spring:message code="label.correoElectronico" />:&nbsp;
					</label>
				</td>
				<td>
					<input id="correo" name="correo" class="alfanumerico_espacios" type="text" value="" maxlength="50" style="text-transform: none;"> 
				</td>
			</tr>
			
 			<tr>
				<td >
					<label class="control-label" for="actualizacion.correo.confirmacion" style="width: 150px">
					<spring:message code="label.confimacionCorreoElectronico" />:&nbsp;
					</label>
				</td>
				<td>
					<input id="correoActualizado" name="correoActualizado" class="alfanumerico_espacios" type="text" value="" maxlength="50" style="text-transform: none;">
				</td>
			</tr>
					<input id="requiereDocs" type="hidden" value="${!requiereDocs? 0 : 1}"/>
					<input id="tipoTramite" type="hidden" value="177"/> <!--${registro.tipoTramite.idTipoTramite}-->
					<input id="idTramite" type="hidden" value="${registro.tramiteId}"/>
					<input type="hidden" value="${solicitudActiva.solicitudId}" id="idSolicitud"/>
			
			<br><br>
			<br><br>
			
			<input type="hidden" value="0" id="mostrarMensajeActas"/>
		
		</table>
		</center>
		
	</form>	
	
			<div class="ui-widget" id="divMensajeDocumentos">
				<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
					<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span><span id="mensajeDocumentos">
						El tr&aacute;mite actual requiere la captura de documentos probatorios, no podr&aacute; finalizar el tramite hasta completar
						la documentaci&oacute;n.
					</span>
					</p>
				</div>
			</div>
			
			<div id="cagarDocProbDiv">
				<jsp:include page="/WEB-INF/views/general/fileUpload/fileUpload.jsp"/>
			</div>	
					
			<div id="msgDocumentosProb" title ="<spring:message code="titulo.mensajeAviso"/>" style="display:none"> 
				<spring:message code="msgDocumentosProb"/>		
			</div>
			
			
			    <div style="text-align: center; width: 100%;">
			        <table style="display: inline-block;">
			            <tr>
			                <td align="center">
			                    <button class="btn btn-primary btn-block" role="button" aria-disabled="false" id="btnInciaTramite">
			                        Finalizar Tr&aacute;mite
			                    </button>
			                </td>
			                <td>&nbsp;</td>
<!-- 			                <td align="center">
			                    <button class="btn btn-primary btn-block" role="button" aria-disabled="false" id="btnCancelarTramite">
			                        Cancelar Tr&aacute;mite
			                    </button>
			                </td> -->
			                <td>&nbsp;</td>
			                <td align="center">
			                    <button class="btn btn-primary btn-block" role="button" aria-disabled="false" id="btnRegresar" onclick="window.location.href = '${contextpath}/inicio/grupoFamiliar'">
			                        Regresar
			                    </button>
			                </td>
			            </tr>
			        </table>
			    </div>
			


	
		<table id="nssTable">
		</table>
				
	</fieldset>
	<div id="contenedorHomeNormativoCE"></div>
	<div id="mensajes"></div>
</div>

