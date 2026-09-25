<%@ include file="/WEB-INF/views/layout/taglibs.jsp"%>

<c:if test="${encontradoFisica != ''}">
	<c:if test="${encontradoFisica != 'RFC_PF_NO_ENCONTRADO'}">
	<c:if test="${encontradoFisica != 'RFC_PF_RENAPO_NO_ENCONTRADO'}">
	<div class="dataTables_wrapper">
	<table id="resultadoDatosFisica" style="width: 100%;"
		class="table table-striped table-bordered">
		<thead>
			<tr>
				<th></th>
				<th>Id</th>
				<th>RFC</th>
				<th>CURP</th>
				<th>NSS</th>
				<th>Nombre(S)</th>
				<th>Primer Apellido</th>
				<th>Segundo Apellido</th>
				<th>Sexo</th>
				<th>Fecha de Nacimiento</th>
				<th>Lugar de Nacimiento</th>
				<th>Domicilio</th>
				<th>Calificaci&oacute;n</th>
				<th>Fecha de &Uacute;ltima Actualizaci&oacute;n</th>
				<th>Registrado por Portal</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="persona" items='${listaPersonas}'>
				<tr>
					<td>
						<c:if test="${encontradoFisica == 'SAT'}">
								<input type="radio" id="radioPersona" name="radio"value="<c:out value="${persona.idPersona == null ? persona.rfc : persona.idPersona}"></c:out>">
						</c:if>
						<c:if test="${encontradoFisica != 'SAT'}">
								<input type="radio" id="radioPersona" name="radio"value="<c:out value="${persona.idPersona == null ? persona.curp : persona.idPersona}"></c:out>">
						</c:if>
					</td>
					<td>
						<c:out value="${persona.idPersona}"></c:out>
					</td>
					<td>
						<c:out value="${persona.rfc}"></c:out>
					</td>
					<td>
						<c:out value="${persona.curp}"></c:out>
					</td>
					<td>
						<c:out value="${persona.nss}"></c:out>
					</td>
					<td>
						<c:out value="${persona.nombre}"></c:out>
					</td>
					<td>
						<c:out value="${persona.primerApellido}"></c:out>
					</td>
					<td>
						<c:out value="${persona.segundoApellido}"></c:out>
					</td>
					<td>
						<c:out value="${persona.sexo.descripcion}"></c:out>
					</td>
					<td>
						<c:out value="${persona.fechaNacimientoFormateada}"></c:out>
					</td>
					<td>
						<c:out value="${persona.lugarNacimiento.nombre}"></c:out>
					</td>
					<td>
						<c:out value="${persona.personaDomicilio}"></c:out>
					</td>
					<td style="text-align: center;">
					<c:forEach var="cal" items='${persona.personaCalificaciones}'>
							<p><c:out value="${cal.calificacion.descripcion}"></c:out>
					</c:forEach>
					</td>
					<td>
						<fmt:formatDate value="${persona.fechaModificacion}" pattern="dd/MM/yyyy"/>
					</td>
					<td>
						<c:forEach items="${fiels}" var="entry">
							<c:if test="${persona.idPersona == entry.key}">
									<c:choose>
										<c:when test="${entry.value}">
											<center>FIEL</center>
										</c:when>
										<c:otherwise>
											<center>-</center>
										</c:otherwise>
									</c:choose>
					        </c:if>
					    </c:forEach>		
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>	
</c:if>
</c:if>
</c:if>