<!-- JSP Contenido del Widget de Persona Fisica. -->

<%@ include file="../../general/taglibs.jsp"%>
<c:set var="staticResourcesPath"
	value='<%=request.getSession().getServletContext()
					.getInitParameter("STATIC_RESOURCES_PATH")%>' />
<c:set var="staticLogoutPath" value='<%=request.getSession().getServletContext().getInitParameter("STATIC_LOGOUT_PATH")%>' />

<input type="hidden" id="nrp" value="${nrp}" />
<input type="hidden" id="rfc" value="${rfc}" />
<address></address>

