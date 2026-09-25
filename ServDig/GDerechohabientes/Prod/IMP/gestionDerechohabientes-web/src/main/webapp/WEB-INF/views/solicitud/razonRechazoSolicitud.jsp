<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>
<div id="razonRegistro">
	<spring:message code="msg12" />

	<br> <br>
	<c:if test="${!empty razones}">
		<table>
			<tr>
				<td><spring:message code="label.rechazo" />:</td>
				<td><select id="idRazonRechazo">
						<c:forEach items="${razones}" var="razon">
							<option value="${razon.idRazonResultado}">${razon.descripcion}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td><spring:message code="label.obsevacionTramte" />:</td>
				<td><textarea id="observacionesRechazo" cols="2"
						style="width: 80%; height: 45px"></textarea></td>
			</tr>
		</table>
	</c:if>

</div>