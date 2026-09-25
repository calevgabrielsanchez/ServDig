<%@ include file="/WEB-INF/views/layout/taglibs.jsp"%>
<c:if test="${encontradoMoral != ''}">
			<div class="dataTables_wrapper">
	<table id="resultadoDatosMoral" style="width: 836px;"
			class="table table-striped table-bordered">
		<thead>
			<tr role="row">
				<th></th>
				<th>Id</th>
				<th>RFC</th>
				<th>Nombre o Raz&oacute;n Social</th>
				<th>Tipo de Sociedad</th>
				<th>Calificaci&oacute;n</th>
				<th>Fecha de &Uacute;ltima Actualizaci&oacute;n</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="persona" items='${listaPersonas}'>
				<tr role="row">
					<td>
						<input type="radio" id="radioPersona" name="radio"
							value="${persona.rfc}_${persona.idPersona}">
					</td>
					<td>
						<c:out value="${persona.idPersona}"></c:out>
					</td>
					<td>
						<c:out value="${persona.rfc}"></c:out>
					</td>
					<td>
						<c:out value="${persona.razonSocial}"></c:out>
					</td>
					<td>
						<c:out value="${persona.tipoSociedad.descripcion}"></c:out>
					</td>
					<td style="text-align: center;">
					<c:forEach var="cal" items='${persona.personaCalificaciones}'>
							<c:out value="${cal.calificacion.descripcion}"></c:out>
					</c:forEach>
					</td>
					<td>
						<fmt:formatDate value="${persona.fechaRegistro}" pattern="dd/MM/yyyy"/>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<input type="hidden" id="rfcMoral" name="rfcMoral" value="${rfc}">
</div>	
</c:if>