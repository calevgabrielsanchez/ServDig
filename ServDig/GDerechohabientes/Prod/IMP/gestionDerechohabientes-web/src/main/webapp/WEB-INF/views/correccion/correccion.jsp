<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>
<jsp:include page="../common/llenaParentescos.jsp"></jsp:include>

<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/correccion/correccionHome.js" htmlEscape="true" />"></script>

<br>
<h4 align="center">CANDIDATOS A ${descripcionTipoTramite}</h4>
<div class="form-comment">
<br>
<jsp:include page="/WEB-INF/views/general/encabezadoGF.jsp"></jsp:include>
<c:choose>
<c:when test="${empty errores}">
<form>
<table width="100%" id="candidatos">
<thead>
	<tr>
		<th align="center" colspan="8">
			CANDIDATOS DEL GRUPO A ${descripcionTipoTramite}
			<input type="hidden" value="${idTipoTramite}" id="idTipoTramite" name="idTipoTramite">
		</th>
	</tr>
	<tr>
		<th  style="width: 40px">Selecci&oacute;n</th>
		<th><spring:message code="label.nombre" /></th>
		<th><spring:message code="label.primerApe" /></th>
		<th><spring:message code="label.segundoApe" /></th>
		<th><spring:message code="label.fechaNac" /></th>
		<th><spring:message code="label.sexo" /></th>
		<th><spring:message code="label.curp" /></th>
		<th><spring:message code="label.parentesco" /></th>
	</tr>
</thead>
<tbody>
	<c:forEach items="${candidatos}" var="candidato">
		<tr>
			<td>
				<input data-parentesco="${candidato.parentesco.idParentesco}" data-patronimss="${patronImss}" 
					data-dom="${empty candidato.domicilio?0:1}" type="radio" style="width: 20px" id="candidato" name="candidato" value="${candidato.derechohabiente.idPersona}">
			</td>
			<td>${candidato.derechohabiente.nombre}</td>
			<td>${candidato.derechohabiente.primerApellido}</td>
			<td>${candidato.derechohabiente.segundoApellido}</td>
			<td><fmt:formatDate pattern="dd/MM/yyyy" value="${candidato.derechohabiente.fechaNacimiento}"/></td>
			<td>${candidato.derechohabiente.sexo.descripcion}</td>
			<td>${candidato.derechohabiente.curp}</td>
			<td>${candidato.parentesco.descripcion}</td>
		</tr>
	</c:forEach>
</tbody>
</table>
</form>
</c:when>
<c:otherwise>
	<div class="ui-widget-content ui-corner-all">
		<div class="ui-state-error ui-corner-all" align="center">
			<div class="ui-icon ui-icon-alert"></div>
			<p class="ui-helper-reset ui-state-error-text"><spring:message code="${errores}" /></p>
		</div>
	</div>
</c:otherwise>
</c:choose>
<br>
<br>

<div align="center">
<form>
<table>
	<tr>
		<td align="center"> 
			<input id="aceptar" type="button" value="Aceptar" class="mboton">
			<!--<input id="guiaTramite" type="button" value="Guia de Tramite" class="mboton"/>-->
			<input id="cancelar" type="button" value="<spring:message code="button.regresar"/>" class="mboton"/>
		</td>
	</tr>
</table>

</form>
</div>
</div>
