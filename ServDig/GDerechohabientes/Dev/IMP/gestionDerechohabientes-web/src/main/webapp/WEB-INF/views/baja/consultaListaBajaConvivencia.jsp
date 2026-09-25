<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/general/GuiaTramite/guiaTramiteImport.jsp"%>

<c:choose>
<c:when test="${empty errores}">
	<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/baja/procesosBaja.js" htmlEscape="true" />"></script>
	<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/baja/bajaGeneral.js" htmlEscape="true" />"></script>
	<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/baja/bajaDerechohabienteConvivencia.js" htmlEscape="true" />"></script>
	
	<!--<script type="text/javascript">
		$(document).ready(function() {
			$("#guiaTramite").click(function() {
				showGuiaTramite(${tipoTramite},${perfil});
			});
		});
	</script>
--></c:when>
<c:otherwise>
	<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/baja/sinInformacion.js" htmlEscape="true" />"></script>
</c:otherwise>
</c:choose>
<br><br>
<h4 align="center">BAJA DE DERECHOHABIENTE POR T&Eacute;RMINO DE DEPENDENCIA</h4>
<div class="form-comment">
<jsp:include page="/WEB-INF/views/general/encabezadoGF.jsp"></jsp:include>
<br>
<c:choose>
<c:when test="${empty errores}">
<form>
<table width="100%" id="candidatos">
<thead>
	<tr>
		<th align="center" colspan="8"><spring:message code="candidatos.baja.dependencia" /></th>
	</tr>
	<tr>
		<th  style="width: 40px"><spring:message code="candidatos.seleccion"/></th>
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
				<input type="radio" style="width: 20px" id="candidato" name="candidato" value="${candidato.derechohabiente.idPersona}">
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
<br><br>
<div align="center">
	<form>
		<table>
			<tr>
				<td align="center">
				<input id="aceptar" type="button" value="Aceptar" class="mboton"/>
				<!--  <input id="guiaTramite" type="button" value="Guia de Tramite" class="mboton"/>-->
				<input id="cancelar" type="button" value="Regresar" class="mboton"/>
				</td>
			</tr>
		</table>
	</form>
</div>
</c:when>
<c:otherwise>
	<div class="ui-widget-content ui-corner-all">
		<div class="ui-state-error ui-corner-all" align="center">
			<div class="ui-icon ui-icon-alert"></div>
			<p class="ui-helper-reset ui-state-error-text"><spring:message code="${errores}" /></p>
		</div>
	</div>
	<div align="center">
		<form>
			<table>
				<tr>
					<td align="center">
					<br>
						<input id="aceptar" type="button" value="Regresar" class="mboton"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
</c:otherwise>
</c:choose>
</div>