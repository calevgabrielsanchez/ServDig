<!-- JSP Contenido del Widget de los Medios Particulares. -->
<%@ include file="../../../general/taglibs.jsp"%>


<c:choose>
	<c:when test="${not empty mediosContacto }">
		<c:forEach items="${mediosContacto}" var="medio" varStatus="indice">
			<address>
				<strong>${medio.tipoMedioContacto.descripcion}</strong> <br>
				${medio.desFormaContacto}<br>
			</address>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<p>No cuenta con medios de contacto particulares.</p>
	</c:otherwise>
</c:choose>

