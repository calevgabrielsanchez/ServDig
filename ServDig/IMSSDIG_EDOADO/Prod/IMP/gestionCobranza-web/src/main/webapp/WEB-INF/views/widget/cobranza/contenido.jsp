<!-- JSP Contenido del Widget de Persona Fisica. -->

<%@ include file="../../general/taglibs.jsp"%>
<c:set var="staticResourcesPath"
	value='<%=request.getSession().getServletContext()
					.getInitParameter("STATIC_RESOURCES_PATH")%>' />
<c:set var="staticLogoutPath" value='<%=request.getSession().getServletContext().getInitParameter("STATIC_LOGOUT_PATH")%>' />

	
		<input type="hidden" id="nrp" value="${nrp}"/>
		<address>
			
			<c:if test="${empty error}">
				<strong>Total IMSS:</strong> 
				<span style="font-size:12px; ">
					<c:choose>
						<c:when test="${not empty totalesImss}">
							$ <fmt:formatNumber type="number" minFractionDigits="2" value="${totalesImss.sumaTotales}" />
						</c:when>
						<c:otherwise>
							<spring:message code="label.sin.adeudo" />
						</c:otherwise>
					</c:choose>
				</span> <br>
				<strong>Total RCV:</strong>  <span style="font-size:12px; ">
					<c:choose>
						<c:when test="${not empty totalesRcv}">
							$ <fmt:formatNumber type="number" minFractionDigits="2" value="${totalesRcv.sumaTotales}" />
						</c:when>
						<c:otherwise>
							<spring:message code="label.sin.adeudo" />
						</c:otherwise>
					</c:choose>
				</span> <br>
            </c:if>
            <c:if test="${not empty error}">
            	<strong>Total IMSS:</strong> <span>${error}</span> <br>
				<strong>Total RCV:</strong>  <span>${error}</span> <br>
            </c:if>
		</address>
	



