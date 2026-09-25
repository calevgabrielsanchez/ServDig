<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>

<%@ include file="/WEB-INF/views/general/GuiaTramite/guiaTramiteImport.jsp" %>


<jsp:include page="/WEB-INF/views/general/encabezadoGF.jsp"></jsp:include>
<fieldset class="form-comment">

<table width="100%">
	<tr>
		<th align="center" colspan="8">Candidatos a obtener pr&oacute;rroga por invalidez</th>
	</tr>
	<tr align="left">

		<th><spring:message code="label.nombre" /></th>
		<th><spring:message code="label.primerApe" /></th>
		<th><spring:message code="label.segundoApe" /></th>
		<th><spring:message code="label.fechaNac" /></th>
		<th><spring:message code="label.sexo" /></th>
		<th><spring:message code="label.curp" /></th>
		<th><spring:message code="label.parentesco" /></th>

	</tr>
	<c:forEach items="${hijos}" var="hijo">
		<tr>
			<td>
				<a href="../beneficiario/estudios/${hijo.derechohabiente.idPersona}"> 
					<c:out	value="${hijo.derechohabiente.nombre}"></c:out> 
				</a>
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
</table>
<br>

<br>
<form>
<div align="center">
<table>

	<tr>
		<!--<td><input type="button" value="Guia de Tramite" class="mboton" onclick="showGuiaTramite(0,0)" style="display: none;"/>
		</td>-->
		<td><input id="cancelar"  type="button" value="Cancelar" class="mboton" /></td>
	</tr>
</table>

</div>
</form>
</fieldset>
