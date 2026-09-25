<!-- JSP Contenido del Widget de los Medios Particulares. -->
<%@ include file="../../../general/taglibs.jsp"%>

<c:set var="centroTrabajo" value="${sujetoObligado.cntroTrabajo}" />

<c:choose>
	<c:when
		test="${centroTrabajo != null and not empty centroTrabajo.mediosContacto}">
		<c:forEach items="${centroTrabajo.mediosContacto}" var="medio"
			varStatus="indice">
			<address>
				<strong>${medio.tipoMedioContacto.descripcion}</strong> <br>
				${medio.desFormaContacto}<br>
			</address>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<p>
			<span class="no-data">No cuenta con medios de contacto.</span>
		</p>
	</c:otherwise>
</c:choose>

