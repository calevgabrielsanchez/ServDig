<%@ include file="../../taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<table id="tablaPersonasFisicas">
	
	<thead>
		<tr>
			<td>Personas localizadas</td>
		</tr>
	</thead>
	
	<tbody>
		<%-- <c:if test="${fn:length(personasFisicas) gt 0}"> --%>
			
		<c:forEach items="${personasFisicas}" var="personaFisica" varStatus="indice">
			<tr><td>
				<div class='elemento-resultado' style="display: table; width: 100%">
					
			<!--	<div style="display: table-cell; width: 90%;">	 -->
						<b>${personaFisica.nombre}&nbsp;${personaFisica.primerApellido}&nbsp;${personaFisica.segundoApellido}</b>&nbsp;<font color="blue">${personaFisica.idPersona}</font>
						<b>${personaFisica.curp}</b>&nbsp;${personaFisica.fechaNacimientoFormateada}<br>
						<c:if test="${fn:length(personaFisica.personaCalificaciones) eq 0}">
							Sin calificaci&oacute;n registrada
						</c:if>
						<c:if test="${fn:length(personaFisica.personaCalificaciones) gt 0 && personaFisica.personaCalificaciones[0].calificacion.descripcion ne ''}">
							${personaFisica.personaCalificaciones[0].calificacion.descripcion}
						</c:if>
						<input type="hidden" value="${personaFisica.idPersona}" id="idPersona"/>
						<input type="hidden" value="${personaFisica.rfc}" id="rfc"/>
						<input type="hidden" value="${personaFisica.curp}" id="curp"/>
						<input type="hidden" value="${personaFisica.nombre}" id="nombre"/>
						<input type="hidden" value="${personaFisica.primerApellido}" id="primerApellido"/>
						<input type="hidden" value="${personaFisica.segundoApellido}" id="segundoApellido"/>
						<input type="hidden" value="${personaFisica.sexo.descripcion}" id="sexo"/>
						<input type="hidden" value="${personaFisica.sexo.idSexo}" id="idSexo"/>
						<input type="hidden" value="${personaFisica.fechaNacimientoFormateada}" id="fechaNacimientoFormateada"/>
						<input type="hidden" value="${personaFisica.lugarNacimiento.nombre}" id="lugarNacimiento"/>
						<input type="hidden" value="${personaFisica.lugarNacimiento.clave}" id="idLugarNacimiento"/>
						<input type="hidden" value="${personaFisica.personaCalificaciones[0].calificacion.descripcion}" id="calificacion"/>
						<input type="hidden" value="${personaFisica.personaCalificaciones[0].calificacion.idCalificacion}" id="idCalificacion"/>
						
						<input type="hidden" value="${indice.index}" id="indice"/>
			<!-- 	</div> -->
			<!-- 	<div style="display: table-cell; width: 10%; padding-bottom: 15px; padding-top: 15px;"> -->
			<!-- 		<span class="ui-icon ui-icon-triangle-1-e"></span> -->
			<!-- 	</div> -->
					
				</div>
			</td></tr>
		</c:forEach>
		
		<%-- </c:if> --%>
		
		<%-- <c:if test="${fn:length(personasFisicas) eq 0}"> --%>
		<!-- 	Error: el tamaño de la lista de personas es 0. Por favor, refine su b&uacute;squeda  -->
		<%-- </c:if> --%>
		
	</tbody>
</table>

<%-- 
<br><b>Registros encontrados: <c:out value="${fn:length(personasFisicas)}" /></b>

<div>
	<!-- Seccion de controles para la paginacion (en caso de que algun dia a alguien se le hinchen por ponerlos) -->
</div>	
--%>