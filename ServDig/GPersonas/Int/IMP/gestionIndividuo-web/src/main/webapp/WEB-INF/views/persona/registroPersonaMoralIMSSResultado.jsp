<%@ include file="taglibs.jsp" %>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/personas/generalPersonas.js" htmlEscape="true" />"></script>

<div class="page_holder_no_height">
	<div class="post_entry_wide no-border">

		<div class="form-comment">
			<c:set var="contextpath" value="<%=request.getContextPath()%>" />

			<h2 style="font-size: 1.0em !important;" >
				<c:out value='La persona ha sido localizada en el IMSS' />
			</h2>
			<br />
			<%-- DEBIDO A QUE SE ESPERA QUE NO HALLA MUCHAS REPETICIONES DE REGISTROS
			(PUES DE HECHO SE TRATA YA SEA DE HOMONIMIAS O DE ERRORES DE REDUNDANCIA
			EN LA BDU) POR AHORA NO SE VAN A PAGINAR LOS RESULTADOS --%>
			<table id="personasFisicasFoundIMSSTable">
				<thead>
					<tr>
						<th></th>
						<th>Identificador</th>
						<th>RFC</th>
						<th>Raz&oacute;n Social</th>
						<th>Tipo Sociedad</th>
						<th>Acta Constitutiva</th>
						<th>Fecha de Creaci&oacute;n</th>
						<th>Estatus</th>
						<th>Calificaci&oacute;n</th>						
					</tr>
				</thead>
				<c:forEach items="${personaMoralLst}" var="personaMoral" varStatus="index">
					<tr class='${(index.count % 2) == 0 ? "odd" : "even"}'>
						<td><c:out value="${index.count}"/></td>
						<td><c:out value="${personaMoral.idPersona}"/></td>
						<td><c:out value="${personaMoral.rfc}"/></td>
						<td><c:out value="${personaMoral.razonSocial}"/></td>
						<td><c:out value="${personaMoral.tipoSociedad.descripcion}"/></td>
						<td><c:out value="${personaMoral.actaConstitutiva}"/></td>
						<td><c:out value="${personaMoral.fechaCreacionFormateada}"/></td>
						<td><c:out value="${personaMoral.estadosFormateados}"/></td>
						<td><c:out value="${personaMoral.subEstadosFormateados}"/></td>
					</tr>
				</c:forEach>
			</table>		
			
			<form>
				<div style="text-align: right; float: right;">
					<input type="button" value="Regresar" class="mboton" id="regresar" />
				</div>
			</form>
				
		</div>
	</div>
</div>
