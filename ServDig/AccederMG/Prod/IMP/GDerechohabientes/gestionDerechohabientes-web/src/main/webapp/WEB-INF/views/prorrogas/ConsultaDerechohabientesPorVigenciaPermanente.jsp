<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>

<%@ include file="/WEB-INF/views/general/GuiaTramite/guiaTramiteImport.jsp" %>

<script>
	function registroProrroga(idDerechohabiente) {
		location.href = "/${mvn.web.app.root}/prorroga/beneficiario/permanente/"+idDerechohabiente;
	}
</script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/prorroga/prorroga.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/prorroga/generarSav011.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/prorroga/resultado.js" htmlEscape="true" />"></script>

<div class="form-comment">
<br>
	<h4 align="center"><strong><spring:message code="titulo.prorrogaFallecimiento" /></strong></h4>
	<jsp:include page="/WEB-INF/views/general/encabezadoGF.jsp"></jsp:include>
<c:choose>	
<c:when test="${empty errores}">
	
<table width="100%" id="candidatos">
<thead>
	<tr>
		<th align="center" colspan="8"><spring:message code="subtitulo.prorrogaLaudo" /></th>
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
	<c:forEach items="${hijos}" var="integrante">
		<tr>
			<td align="center">
				<input type="radio" style="width: 20px" id="idCandidato" name="idCandidato" value="${integrante.derechohabiente.idPersona}">
			</td>
			<!-- <td>
				<a href="/${mvn.web.app.root}/prorroga/beneficiario/permanente/${integrante.derechohabiente.idPersona}"> 
					<c:out	value="${integrante.derechohabiente.nombre}"></c:out> 
				</a>
			</td> -->
			<td>
				<c:out value="${integrante.derechohabiente.nombre}"></c:out>
			</td>
			<td>
				<c:out value="${integrante.derechohabiente.primerApellido}"></c:out>
			</td>
			<td>
				<c:out value="${integrante.derechohabiente.segundoApellido}"></c:out>
			</td>
			<td>
				<fmt:formatDate pattern="dd/MM/yyyy" value="${integrante.derechohabiente.fechaNacimiento}"/>
			</td>
			<td>
				<c:out value="${integrante.derechohabiente.sexo.descripcion}"></c:out>
			</td>
			<td>
				<c:out value="${integrante.derechohabiente.curp}"></c:out></td>
			<td>
				<c:out value="${integrante.parentesco.descripcion}"></c:out>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<br>


<form>
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
		
		<td><input type="button" value="Imprimir SAV 011" class="mboton" onclick="generarSAV011Persona();"/>
	</tr>
</table>

</div>
</form>

</c:when>
<c:otherwise>
	<div class="ui-widget-content ui-corner-all">
		<div class="ui-state-error ui-corner-all" align="center">
			<div class="ui-icon ui-icon-alert"></div>
			<p class="ui-helper-reset ui-state-error-text"><spring:message code="${errores}" /></p>
			<h3><span style="font-size:.7em" class="ui-helper-reset ui-state-error-text">Detalle: ${error}</span></h3>
		</div>
		
	</div>
	<div align="center">
		<form>
			<table>
				<tr><td><br></td></tr>
				<tr>
					<td>
						<input  id="regresar"  type="button"  value='<spring:message  code="button.regresar"/>'  class="mboton" />
					</td>
				</tr>		
			</table>
		</form>
	</div>
</c:otherwise>
</c:choose>

</div>
