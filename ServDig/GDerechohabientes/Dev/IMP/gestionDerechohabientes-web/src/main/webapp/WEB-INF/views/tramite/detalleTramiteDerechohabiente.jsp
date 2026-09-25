<%@ include file="../general/taglibs.jsp"%>

<c:choose>
<c:when test="${!empty integrantes}">
		<c:forEach items="${integrantes}" var ="integrante">
		<fieldset><legend><strong><spring:message
			code="tramite.detalle.datosDerechohabiente" /></strong></legend>
		<table>
			<tr>
				<td align="left"><spring:message code="tramite.detalle.nombre" />: </td>
				<td><input type="text" readonly="readonly" style="width: 180px" value="${integrante.derechohabiente.nombre}" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.estadoCivil" /></td>
				<td><input type="text" readonly="readonly" value="${integrante.derechohabiente.estadoCivil.descripcion}" style="width: 180px" /></td>
			</tr>
			<tr>
				<td align="left"><spring:message code="tramite.detalle.aPaterno" />: </td>
				<td><input type="text" readonly="readonly" value="${integrante.derechohabiente.primerApellido}" style="width: 180px" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.curp" />: </td>
				<td colspan="3"><input type="text" readonly="readonly" value="${integrante.derechohabiente.curp}" style="width: 180px" /></td>
			</tr>
			
			<tr>
				<td align="left"><spring:message code="tramite.detalle.aMaterno" />: </td>
				<td><input type="text" readonly="readonly" value="${integrante.derechohabiente.segundoApellido}" style="width: 180px" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.fNacimiento" />: </td>
				<td><input type="text" readonly="readonly" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${integrante.derechohabiente.fechaNacimiento}"/>" style="width: 180px" /></td>
			</tr>
			<tr>
				<td align="left"><spring:message code="tramite.detalle.lNacimiento" />:</td>
				<td><input type="text" readonly="readonly" value="${integrante.derechohabiente.lugarNacimiento.nombre}" style="width: 180px" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.sexo" />: </td>
				<td colspan="3"><input type="text" readonly="readonly" value="${integrante.derechohabiente.sexo.descripcion}" style="width: 180px" /></td>
			</tr>
			<tr>
				<td align="left"><spring:message code="tramite.detalle.parentesco" />: </td>
				<td><input type="text" readonly="readonly" value="${integrante.parentesco.descripcion}" style="width: 180px" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td></td>
				<td></td>
				

			</tr>
						
				</table>
		</fieldset>
		</c:forEach>
</c:when>
<c:otherwise>
	<fieldset><legend><strong><spring:message
			code="tramite.detalle.datosDerechohabiente" /></strong></legend>
		<table>
			<tr>
				<td align="left"><spring:message code="tramite.detalle.nombre" />: </td>
				<td><input type="text" readonly="readonly" style="width: 180px" value="${integrante.derechohabiente.nombre}" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.estadoCivil" /></td>
				<td><input type="text" readonly="readonly" value="${integrante.derechohabiente.estadoCivil.descripcion}" style="width: 180px" /></td>
			</tr>
			<tr>
				<td align="left"><spring:message code="tramite.detalle.aPaterno" />: </td>
				<td><input type="text" readonly="readonly" value="${integrante.derechohabiente.primerApellido}" style="width: 180px" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.curp" />: </td>
				<td colspan="3"><input type="text" readonly="readonly" value="${integrante.derechohabiente.curp}" style="width: 180px" /></td>
			</tr>
			
			<tr>
				<td align="left"><spring:message code="tramite.detalle.aMaterno" />: </td>
				<td><input type="text" readonly="readonly" value="${integrante.derechohabiente.segundoApellido}" style="width: 180px" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.fNacimiento" />: </td>
				<td><input type="text" readonly="readonly" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${integrante.derechohabiente.fechaNacimiento}"/>" style="width: 180px" /></td>
			</tr>
			<tr>
				<td align="left"><spring:message code="tramite.detalle.lNacimiento" />:</td>
				<td><input type="text" readonly="readonly" value="${integrante.derechohabiente.lugarNacimiento.nombre}" style="width: 180px" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td align="left"><spring:message code="tramite.detalle.sexo" />: </td>
				<td colspan="3"><input type="text" readonly="readonly" value="${integrante.derechohabiente.sexo.descripcion}" style="width: 180px" /></td>
			</tr>
			<tr>
				<td align="left"><spring:message code="tramite.detalle.parentesco" />: </td>
				<td><input type="text" readonly="readonly" value="${integrante.parentesco.descripcion}" style="width: 180px" /></td>
				<td style="width: 50px"></td><td style="width: 50px"></td>
				<td></td>
				<td></td>
				

			</tr>
						
				</table>
		</fieldset>
</c:otherwise>
</c:choose>
				
	