<%@ include file="../general/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="expires" content="-1" >
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=9" >
<title>
<tiles:insertAttribute name="title" ignore="true" /></title>
<link rel="icon" href="<spring:url value="/resources/iconos/favicon.ico" htmlEscape="true" />" />

<!-- Estilos -->

<link rel="stylesheet" type="text/css" 	href="<spring:url value="/resources/estilos/imss/reset.css" htmlEscape="true" />" />
<link rel="stylesheet" type="text/css" 	href="<spring:url value="/resources/estilos/imss/style.css" htmlEscape="true" />" />
<link type="text/css"	href="<spring:url value="/resources/estilos/jquery/ui-lightness/jquery-ui.css" htmlEscape="true" />" 	rel="stylesheet" />
<link type="text/css"
	href="<spring:url value="/resources/estilos/jquery/demo_page.css" htmlEscape="true" />"
	rel="stylesheet" />
<link type="text/css"
	href="<spring:url value="/resources/estilos/jquery/demo_table.css" htmlEscape="true" />"
	rel="stylesheet" />	
	
<!-- Javascripts -->
<script>
	var context_path = '<%= request.getContextPath()%>';
</script>

<script type="text/javascript"
	src="<spring:url value="/resources/js/jquery/jquery.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/resources/js/jquery/jquery-post-json.js" htmlEscape="true" />"></script>	
<script type="text/javascript"
	src="<spring:url value="/resources/js/jquery/jquery-ui.js" htmlEscape="true" />"></script>

<script type="text/javascript"
	src=" <spring:url value="/resources/js/jquery/dtable/jquery.dataTables.js" htmlEscape="true" />"></script>
	
<script type="text/javascript"
	src="<spring:url value="/resources/js/jquery/dtable/jquery.dataTables.pagination.js" htmlEscape="true" />"></script>
			
<script type="text/javascript"
	src="<spring:url value="/resources/js/jquery/form2Object/form2object.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/resources/js/jquery/form2Object/jquery.toObject.js" htmlEscape="true" />"></script>	
<script type="text/javascript"
	src="<spring:url value="/resources/js/json/json2.js" htmlEscape="true" />"></script>

<script type="text/javascript"
	src="<spring:url value="/resources/js/json/json.min.js" htmlEscape="true" />"></script>
	
<script type="text/javascript"
	src="<spring:url value="/resources/js/delta/gestionCtrlSelect.js" htmlEscape="true" />"></script>
<!-- JS General de la aplicacion  -->
	<script type="text/javascript"
		src="<spring:url value="/resources/js/delta/general.js" htmlEscape="true" />"></script>
</head>
<body>
<jsp:include page="../agregaContextoJS.jsp" />
<div class="site_position_center">   
		<div id="cuerpo_principal" class="main_wrap">
					<div id="encabezado">
						<tiles:insertAttribute name="encabezado" />
					</div>
					<div id="menu" style="margin: 0px 0px 0px 0px;">
						<tiles:insertAttribute name="menu" />
					</div>	
	                <div id="submenu" style="margin: 0px 0px 0px 0px;">
						<tiles:insertAttribute name="submenu" />
					</div>				
					<div id="cuerpo">
						<tiles:insertAttribute name="contenido" />
					</div>
					<div id="pie" align="center">
						<tiles:insertAttribute name="pie" />
					</div>				
		</div>	
</div>
	<div style="display: none;">
		<c:set var="contextpath" value="<%=request.getContextPath() %>" />
		<form id="formCerrarSesion" action="${contextpath}/logout" method="get"></form>
	</div>		
</body>
</html>