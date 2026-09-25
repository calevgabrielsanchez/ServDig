<%@ include file="../general/taglibs.jsp" %>
<c:set var="esInscripcionInicial" value="<%=session.getAttribute(\"grupoTramite\") %>" />
<div class="titulo_sistema texto-centrado">
	<span>
	    <spring:message code="label.analisis.clasificacion" />
	    <c:if test="${grupoTramite != null}">
	        <br>
		        <c:if test="${grupoTramite == '1'}">
			        <spring:message code="label.inscripcion.inicial" />
			    </c:if>
			    <c:if test="${grupoTramite == '2'}">
			        <spring:message code="label.modificacion.patronal" />
			    </c:if>
		</c:if>
	</span>
</div>
