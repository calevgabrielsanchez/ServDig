<!-- JSP Contenido del Widget Dummy. -->
<%@ include file="../../general/taglibs.jsp"%>


<c:choose>
	<c:when test="${not empty DATOS_DUMMY }">
		${DATOS_DUMMY }
	</c:when>
	<c:otherwise>
		<p>
			<spring:message code="label.widget.sin.resultados.dummy" />
		</p>
	</c:otherwise>
</c:choose>
