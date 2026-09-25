<!-- Vista que se emplea para informar al usuario que la persona
 ya se encuentra registrada en el IMSS, dependiendo del PERFIL
 del usuario (INTERNO, EXTERNO) se debera mostrar 
 diferente informacion. -->
 
 <%@ include file="../general/taglibs.jsp" %>
 
 
<div>
<div title="Personas encontradas en el Instituto" id="dgPersonas">
				<p> Detalle de las personas encontradas en el Instituto </p>
				
				
					<table id="personasFisicasFoundIMSSTable" style="width: 1050px !important;">
					<thead>
						<tr>
							
							<th>Identificador</th>
							<th>RFC</th>
							<th>CURP</th>
							<th>NSS</th>
							<th>Nombre(s)</th>
							<th>Primer Apellido</th>
							<th>Segundo Apellido</th>
							<th>Sexo</th>
							<th>Fecha de Nacimiento</th>
							<th>Entidad de Nacimiento</th>
							<th>Estatus</th>
							<th>Calificaci&oacute;n</th>
						</tr>
					</thead>
					<c:forEach items="${personaFisicaLst}" var="personaFisica" varStatus="index">
						<tr class='${(index.count % 2) == 0 ? "odd" : "even"}'>
							<td><c:out value="${personaFisica.idPersona}"/></td>
							<td><c:out value="${personaFisica.rfc}"/></td>
							<td><c:out value="${personaFisica.curp}"/></td>
							<td><c:out value="${personaFisica.nss}"/></td>
							<td><c:out value="${personaFisica.nombre}"/></td>
							<td><c:out value="${personaFisica.primerApellido}"/></td>
							<td><c:out value="${personaFisica.segundoApellido}"/></td>
							<td><c:out value="${personaFisica.sexo.descripcion}"/></td>
							<td><c:out value="${personaFisica.fechaNacimientoFormateada}"/></td>
							<td><c:out value="${personaFisica.lugarNacimiento.nombre}"/></td>
							<td><c:out value="${personaFisica.estadosFormateados}"/></td>
							<td><c:out value="${personaFisica.subEstadosFormateados}"/></td>
						</tr>
					</c:forEach>
				</table>
			</div> 
 
 </div>
 