<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/general/GuiaTramite/guiaTramiteImport.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/documentosReportes/documentoSAV007.js" htmlEscape="true" />"></script>

<br>
<br>
<div class="form-comment">
<br>
<c:choose>
<c:when test="${empty errores}">
<form>
<table width="100%" id="candidatos">
<thead>
	<tr>
		<th align="center" colspan="8">Reimpresi&oacute;n SAV007</th>
	</tr>
	<tr>
		<td><br></td>
	</tr>
	<tr>
		<th align="center" colspan="8">Aviso de pr&oacute;rroga de servicios m&eacute;dicos</th>
	</tr>
	<tr>
		<td><br></td>
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
				<input type="radio" style="width: 20px" id="idCandidato" name="idCandidato" value="${candidato.derechohabiente.idPersona}">
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
<div align="center">
<form>
<table>
<tr>
			<td><br></td>
		</tr>
	<tr>
		<td> <input id="aceptar" type="button" value="Aceptar" class="mboton"> </td>
		<td>&nbsp;</td>
		<td> <input id="cancelar" type="button" value='<spring:message  code="button.regresar"/>' class="mboton"/></td>
	</tr>
</table>

</form>
</div>

</c:when>
<c:otherwise>

	<table width="100%">
		<tr align="center">
			<th align="center" >Reimpresi&oacute;n SAV007</th>
		</tr>
		<tr>
			<td><br></td>
		</tr>
		<tr align="center">
			<th align="center" >Aviso de pr&oacute;rroga de servicios m&eacute;dicos</th>
		</tr>
		<tr>
			<td><br></td>
		</tr>
	</table>
	<div class="ui-widget-content ui-corner-all">
		<div class="ui-state-error ui-corner-all" align="center">
			<div class="ui-icon ui-icon-alert"></div>
			<p class="ui-helper-reset ui-state-error-text"><spring:message code="${errores}" /></p>
		</div>
	</div>

<div align="center">
<form>
<table>
	<tr><td><br></td></tr>
	<tr>
		<td> <input id="cancelar" type="button" value='<spring:message  code="button.regresar"/>' class="mboton"/></td>
	</tr>
</table>

</form>
</div>

</c:otherwise>
</c:choose>
<br>
<br>

</div>
