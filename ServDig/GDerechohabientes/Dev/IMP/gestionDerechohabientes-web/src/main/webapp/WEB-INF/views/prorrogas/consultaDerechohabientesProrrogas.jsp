<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>

<%@ include file="/WEB-INF/views/general/GuiaTramite/guiaTramiteImport.jsp" %>
<script>
	function registroProrroga(idDerechohabiente,tipoTramite) {
		location.href = "/${mvn.web.app.root}/prorroga/beneficiario/prorrogas/"+idDerechohabiente+"/"+tipoTramite;
	}
</script>

<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/prorroga/prorroga.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/prorroga/generarSav011.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/prorroga/resultado.js" htmlEscape="true" />"></script>

<div class="form-comment">
<br>
	<div id="tituloVigenciaPermanente" style="display:none"><h4 align="center"><strong><spring:message code="titulo.prorrogaFallecimiento" /></strong></h4></div>
	<div id="tituloAcuerdo" style="display:none"><h4 align="center"><strong><spring:message code="titulo.prorrogaAcuerdo" /></strong></h4></div>
	<div id="tituloObtetrico" style="display:none">
	<c:if test="${hijo.parentesco.idParentesco == 3}">
		<h4 align="center"><strong><spring:message code="titulo.prorrogaObstetricosBeneficiaria" /></strong></h4>
	</c:if>
	<c:if test="${hijo.parentesco.idParentesco == 4}">
		<h4 align="center"><strong><spring:message code="titulo.prorrogaObstetricosBeneficiariaConcubina" /></strong></h4>
	</c:if>

	<c:if test="${hijo.parentesco.idParentesco == 0}">
		<h4 align="center"><strong><spring:message code="titulo.prorrogaObstetricosBeneficiariaConcubina" /></strong></h4>
	</c:if>
	</div>
	<div id="tituloEnfermedad" style="display:none"><h4 align="center"><strong><spring:message code="titulo.prorrogaIncapacidad" /></strong></h4></div>
	<div id="tituloEstudios" style="display:none"><h4 align="center"><strong><spring:message code="titulo.prorrogaEstudios" /></strong></h4></div>
	<div id="tituloLaudo" style="display:none"><h4 align="center"><strong><spring:message code="titulo.prorrogaLaudo" /></strong></h4></div>
	<div id="tituloVigenciaTemporal" style="display:none"><h4 align="center"><strong><spring:message code="titulo.prorrogaPension" /></strong></h4></div>
	
<jsp:include page="/WEB-INF/views/general/encabezadoGF.jsp"></jsp:include>
<input type="hidden" id="tipoTramite" name="tipoTramite" value="<c:out value="${tipoTramite}"/>"></input>
<c:choose>	
<c:when test="${empty errores}">
<form:form commandName="list" id="prorrogas">	
	<table width="100%" id="candidatos">
		<thead>		
			<tr id="subMenuAcuerdo" style="display:none">
				<th align="center" colspan="8"><spring:message code="subtitulo.prorrogaAcuerdo" /></th>				
			</tr>
			<tr id="subMenuLaudo" style="display:none">
				<th align="center" colspan="8"><spring:message code="subtitulo.prorrogaLaudo" /></th>				
			</tr>
			<tr id="subMenuIncapacidad" style="display:none">
				<th align="center" colspan="8"><spring:message code="subtitulo.prorrogaIncapacidad" /></th>				
			</tr>
			<tr align="left">
				<th  style="width: 40px">Selecci&oacute;n</th>
				<th><spring:message code="label.nombre" /></th>
				<th><spring:message code="label.primerApe" /></th>
				<th><spring:message code="label.segundoApe" /></th>
				<th><spring:message code="label.fechaNac" /></th>
				<th><spring:message code="label.sexo" /></th>
				<th><spring:message code="label.curp" /></th>
				<th><spring:message code="label.parentesco" /></th>
		
			</tr>
		<thead>
		<tbody>	
			<c:forEach items="${hijos}" var="hijo">
				<tr>
					<td align="center">
						<input type="radio" style="width: 20px" id="idCandidato" name="idCandidato" value="${hijo.derechohabiente.idPersona}">
					</td>				
					<td>
						<c:out value="${hijo.derechohabiente.nombre}"></c:out>
					</td>
					<td>
						<c:out value="${hijo.derechohabiente.primerApellido}"></c:out>
					</td>
					<td>
						<c:out value="${hijo.derechohabiente.segundoApellido}"></c:out>
					</td>
					<td>
						<fmt:formatDate pattern="dd/MM/yyyy" value="${hijo.derechohabiente.fechaNacimiento}"/>
					</td>
					<td>
						<c:out value="${hijo.derechohabiente.sexo.descripcion}"></c:out>
					</td>
					<td>
						<c:out value="${hijo.derechohabiente.curp}"></c:out></td>
					<td>
						<c:out value="${hijo.parentesco.descripcion}"></c:out>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<br>
	<div align="center">
		<table>
			<tr>
				<td>
					<input  id="aceptar"  type="button"  value='<spring:message  code="button.aceptar"/>'  class="mboton" />
				</td>
				<td>&nbsp;</td>
				<td>
					<input  id="regresar"  type="button"  value='<spring:message  code="button.regresar"/>'  class="mboton" />
				</td>
				<td>&nbsp;</td>
				<!--<td><input type="button" value="Guia de Tramite" class="mboton" onclick="showGuiaTramite(0,0)" style="display: none;"/>
				</td>-->
				
				<!--  <td><input type="button" value="Imprimir SAV 011" class="mboton" onclick="generarSAV011Persona();"/> 
				</td> -->
			</tr>
		</table>
	</div>
</form:form>
</c:when>
<c:otherwise>
	<div class="ui-widget-content ui-corner-all">
		<div class="ui-state-error ui-corner-all" align="center">
			<div class="ui-icon ui-icon-alert"></div>
			<p class="ui-helper-reset ui-state-error-text"><spring:message code="${errores}"/></p>
			<h3><span style="font-size:.7em" class="ui-helper-reset ui-state-error-text">Detalle: ${error}</span></h3>
		</div>
		
	</div>
	<div align="center">
		<form>
			<table>
				<tr><td><br></td></tr>
				<tr>
					<td>
						<input  id="regresar"  type="button"  value='<spring:message  code="button.aceptar"/>'  class="mboton" />
					</td>
				</tr>		
			</table>
		</form>
	</div>
</c:otherwise>
</c:choose>

</div>
