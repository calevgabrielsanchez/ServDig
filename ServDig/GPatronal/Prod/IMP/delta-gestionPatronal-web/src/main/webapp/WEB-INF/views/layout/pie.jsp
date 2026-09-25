
<%@ include file="../general/taglibs.jsp" %>
<c:set var="staticResourcesPath"
	value='<%=request.getSession().getServletContext()
					.getInitParameter("STATIC_RESOURCES_PATH")%>' />
<c:set var="staticLogoutPath" value='<%=request.getSession().getServletContext().getInitParameter("STATIC_LOGOUT_PATH")%>' />
<!-- Inicio Footer -->
<br>
<div style="text-align: center;">
	<p style="text-align: center">
	<br>
		<img alt="" src="${staticResourcesPath}/imagenes/logo_70_final.jpg" width="150px" height="100px"/>
	</p>
</div>

<div class="copyright">
	<p style="text-align: center">
		Reforma 476, Col. Ju&aacute;rez, M&eacute;xico DF - Tel 01 800 623 23 23 - <br/>
		ALGUNOS DERECHOS RESERVADOS &copy; IMSS - 2012 
	</p>
</div>
<!-- fin footer -->
