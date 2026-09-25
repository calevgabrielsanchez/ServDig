<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="staticResourcesPath"
	value='<%=request.getSession().getServletContext().getInitParameter("STATIC_RESOURCES_PATH")%>' />
<c:set var="staticLogoutPath" value='<%=request.getSession().getServletContext().getInitParameter("STATIC_LOGOUT_PATH")%>' />

<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="max-age=0, no-cache, no-store, must-revalidate" />
<meta http-equiv="expires" content="0" />
<meta name="description" content="Instituto Mexicano del Seguro Social Portal DELTA" />
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta lang="es">
<title><tiles:insertAttribute name="title" ignore="true" /></title>

<jsp:include page="staticResources.jsp"></jsp:include>

<script>
	var context_path = '<%= request.getContextPath()%>';
</script>

<!-- JS del control de mensajes de exito  -->
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/controlmensaje/ctrlMensaje.js" htmlEscape="true" />"></script>
</head>

<body>
	<div class="site_position_center" style="margin: 0px !important; width: 650px">
		<div id="cuerpo_principal" style="margin: 0px !important;">
			<div id="cuerpo">
				<tiles:insertAttribute name="contenido" />
			</div>
		</div>
	</div>

	<div style="display: none;">
		<c:set var="contextpath" value="<%=request.getContextPath()    %>" />
		<form id="formCerrarSesion"
			action="${staticLogoutPath}"
			method="get"></form>
	</div>
</body>
</html>