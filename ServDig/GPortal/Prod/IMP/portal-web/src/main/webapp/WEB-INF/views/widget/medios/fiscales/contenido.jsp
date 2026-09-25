<!-- JSP Contenido del Widget de los Medios Fiscales. -->
<%@ include file="../../../general/taglibs.jsp"%>

<c:choose>
	<c:when test="${not empty mediosFiscales }">
		<c:forEach items="${mediosFiscales}" var="medio" varStatus="indice">
			<address>
				<strong>${medio.tipoMedioContacto.descripcion}</strong> <br>
				${medio.desFormaContacto}<br>
			</address>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<p>No cuenta con medios de contacto fiscales.</p>
	</c:otherwise>
</c:choose>


