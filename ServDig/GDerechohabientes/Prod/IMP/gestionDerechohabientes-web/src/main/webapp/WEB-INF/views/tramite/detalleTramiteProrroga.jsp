<%@ include file="../general/taglibs.jsp"%>
		<%@ include file="detalleTramiteDerechohabiente.jsp" %>
		<br>
		<fieldset><legend><strong><spring:message
			code="tramite.detalle.tituloProrroga" /></strong></legend>
		<table>
			<tr>
				<td align="right"><spring:message code="tramite.detalle.fechaInicioProrroga" />: 
				</td>
				<td><input type="text" readonly="readonly" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${prorroga.fechaInicioProrroga}"/>"
					style="width: 150px" /></td>
				<td><br></td>
				<td align="right"><spring:message code="tramite.detalle.fechaFinProrroga" />: </td>
				<td><input type="text" readonly="readonly" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${prorroga.fechaFinProrroga}"/>"
					style="width: 150px" /></td>

			</tr>
			
		</table>
		</fieldset>
		<br>
		
				
	