<%@ include file="taglibs.jsp" %>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/solicitud/resultadoBusquedaSolicitud.js" htmlEscape="true" />"></script>		

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<br/><br/><br/><br/><br/>
<div class="separadorseccion">Tr&aacute;mites por validar</div>

<form:form modelAttribute="solicitud" id="solicitudForm" method="post" action="${contextpath}/solicitud/procesar_tramites">
	<form:errors path="*" cssClass="error" />

	<div style="overflow-x: scroll; overflow-y: scroll; width: 100%; height: 70%;">
		<table id="solicitudFoundTable"  style="margin: 0px auto !important;">
			<thead>
				<tr>
					<th>Id Tr&aacute;mite</th>
					<th>RFC</th>
					<th>Raz&oacute;n Social</th>
					<th>Tipo de Sociedad</th>
					<th>Acta Constitutiva</th>
					<th>Fecha de Creaci&oacute;n</th>
					<th>Aceptado</th>
					<th>Raz&oacute;n Rechazo</th>
					<th>Observaciones</th>
				</tr>
			</thead>
			<c:forEach items="${solicitud.tramite}" var="tramite" varStatus="index">
				<tr class='${(index.count % 2) == 0 ? "odd" : "even"}'>
					<td><c:out value="${tramite.idTramite}"/></td>
					<td><c:out value="${tramite.personaMoral.rfc}"/></td>
					<td><c:out value="${tramite.personaMoral.razonSocial}"/></td>
					<td><c:out value="${tramite.personaMoral.tipoSociedad.descripcionAbreviada}"/></td>
					<td><c:out value="${tramite.personaMoral.actaConstitutiva}"/></td>
					<td><c:out value="${tramite.personaMoral.fechaCreacionFormateada}"/></td>
					<td>
						<form:radiobutton path="tramite[${index.count - 1}].tramitadorValidaDatos" id="tramitadorValidaDatos" style="width: 20px" label="Aceptado" value="true" onchange="deshabilitarComboRazonRechazo(${index.count - 1})"/>
						<form:radiobutton path="tramite[${index.count - 1}].tramitadorValidaDatos" id="tramitadorRechazaDatos" style="width: 20px" label="Rechazado" value="false" checked="checked" onchange="habilitarComboRazonRechazo(${index.count - 1})"/>
					</td>
					<td>
						<select id="tramite${index.count - 1}idRazonResultado" name="tramite[${index.count - 1}].idRazonResultado">
							<option value="-1">--Por favor seleccione--</option>
							<option value="2">Documentos probatorios incompletos</option>
							<option value="3">Documentos ap&oacute;crifos</option>
							<option value="4">Improcedencia</option>
							<option value="5">Solicitud Cancelada</option>
						</select>
					</td>
					<td><form:input path="tramite[${index.count - 1}].observacion" /></td>
				</tr>
				<input type="hidden" name="tramite[${index.count - 1}].idTramite" value="${tramite.idTramite}" />
			</c:forEach>
		</table>
		<form:hidden path="idSolicitud" value="${solicitud.idSolicitud}"/>
	</div>
	<div style="float: right;">
		<input type="button" value="Procesar" class="mboton" id="procesarBtn"/>
		<input type="button" value="Regresar" class="mboton" id="regresarBtn"/>
	</div>

</form:form>
