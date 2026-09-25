<%@page import="java.text.SimpleDateFormat"%>
<%@page import="mx.imss.ctirss.session.UserSession"%>
<%@page import="mx.imss.ctirss.session.ConstantesSession"%>
<%@page import="java.util.Date"%>
<%@page import="mx.imss.ctirss.model.DltUsuarioden"%>
<%@page import="mx.imss.ctirss.catalogos.model.DlcUsuario"%>
<%@ include file="../general/taglibs.jsp" %>
	
<!--Inicio Breadcrumb -->
<div class="breadcrumb">
	<ul style="float: left !important;">	
		<li>
		<%Date fecha = new Date();
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		%>
			<span class="etiqueta" style="padding: 5px !important;"> Fecha : </span> 
			<span class="dato"><%=formato.format(fecha) %></label> </span>
		</li>	
		<li>
			<span class="etiqueta" style="padding: 5px !important;"> Usuario : <%= ((UserSession)request.getSession().getAttribute(ConstantesSession.USR_SESSION)).getNomUsuarioSistema() %></span> 
			<span class="dato"> </span>
		</li>
		<li>
			<img  src="<spring:url value="/resources/images/system-users.png" htmlEscape="true" />" title="Usuario"  />
		</li>
	</ul>
	<ul>
	<%-- <li>
			<a href="<%= request.getContextPath()%>/session/terminateSession"><img  src="<spring:url value="/resources/images/system-log-out.png" htmlEscape="true" />" title="Salir"  /></a>
		</li>
	
		<li>
			<a href="<%= request.getContextPath()%>/welcome/uno/busqueda"><img  src="<spring:url value="/resources/images/go-home.png" htmlEscape="true" />" title="Inicio"  /></a>
		</li> --%>
	</ul>
</div>
<!--Termino Breadcrumb -->

