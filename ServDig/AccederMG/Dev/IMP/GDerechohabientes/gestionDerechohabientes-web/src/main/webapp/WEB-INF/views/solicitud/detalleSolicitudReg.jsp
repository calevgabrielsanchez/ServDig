<%@ include file="../general/taglibs.jsp"%>
<div>

<fieldset>
	<legend><strong><spring:message code="label.tramitesSolicitud"/></strong></legend>
	<center>
	<table>
	<c:forEach items="${solicitud.tramite}" var="tramite">

			
		
			<tr>
				<th><spring:message code="label.tipo"/></th>
				<td>
					<input type="text" disabled="disabled" value="${tramite.tipoTramite.descripcion}" style="width: 80px" />
				</td>
				<th><spring:message code="label.fechaAlta"/></th>
				<td>
					<input type="text" disabled="disabled" value="${tramite.fechaTramite}" style="width: 80px" />
				</td>
				<th><spring:message code="label.estado"/></th>
				<td>
					<input type="text" disabled="disabled" value="${tramite.estadoTramite.descripcion}" style="width: 80px" />
				</td>
			</tr>
	</c:forEach>
	</table>
	</center>
</fieldset>
</div>