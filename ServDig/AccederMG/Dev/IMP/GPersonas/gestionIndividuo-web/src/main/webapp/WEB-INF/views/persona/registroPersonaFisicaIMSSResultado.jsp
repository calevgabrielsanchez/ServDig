<%@ include file="taglibs.jsp" %>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/personas/generalPersonas.js" htmlEscape="true" />"></script>

<div class="page_holder_no_height">
	<div class="post_entry_wide no-border">

		<div class="form-comment">
			<c:set var="contextpath" value="<%=request.getContextPath()%>" />

			<h2 style="font-size: 1.0em !important;" >
				<c:out value='${mensaje}' />
			</h2>
			<br />

			<div style="width: 100%; overflow: auto;">
				<table id="personasFisicasFoundIMSSTable">
					<thead>
						<tr>
							<th></th>
							<th>Identificador</th>
							<th>RFC</th>
							<th>CURP</th>
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
							<td><c:out value="${index.count}"/></td>
							<td><c:out value="${personaFisica.idPersona}"/></td>
							<td><c:out value="${personaFisica.rfc}"/></td>
							<td><c:out value="${personaFisica.curp}"/></td>
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
			
			<form>
				<div style="text-align: right; float: right;">
					<input type="button" value="Regresar" class="mboton" id="regresar" />
				</div>
			</form>
			
		</div>
	</div>
</div>
