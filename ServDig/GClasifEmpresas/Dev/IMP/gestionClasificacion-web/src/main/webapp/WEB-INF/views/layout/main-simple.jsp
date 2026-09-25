<%@ include file="../general/taglibs.jsp" %>

<!DOCTYPE html> 

<html lang="es">
<head> 

<meta http-equiv="pragma" content="no-cache" /> 
<meta http-equiv="cache-control" content="max-age=100, must-revalidate" /> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title><tiles:insertAttribute name="title" ignore="true" /></title>


<link rel="icon" href="<spring:url value="/static/resources/iconos/favicon.ico" htmlEscape="true" />" />


<!-- Estilos -->

<link rel="stylesheet" type="text/css" 	href="<spring:url value="/static/resources/estilos/imss/reset.css" htmlEscape="true" />" />
<link rel="stylesheet" type="text/css" 	href="<spring:url value="/static/resources/estilos/imss/style.css" htmlEscape="true" />" />
<link type="text/css"
	href="<spring:url value="/static/resources/estilos/jquery/ui-lightness/jquery-ui.css" htmlEscape="true" />"
	rel="stylesheet" />
<link type="text/css"
	href="<spring:url value="/static/resources/estilos/jquery/demo_page.css" htmlEscape="true" />"
	rel="stylesheet" />
<link type="text/css"
	href="<spring:url value="/static/resources/estilos/jquery/demo_table.css" htmlEscape="true" />"
	rel="stylesheet" />	
<style type="text/css" media="screen">


/*
			 * Override styles needed due to the mix of three different CSS sources! For proper examples
			 * please see the themes example in the 'Examples' section of this site
			 */
.dataTables_info {
	padding-top: 0;
}

.dataTables_paginate {
	padding-top: 0;
}

.css_right {
	float: right;
}



#theme_links span {
	float: left;
	padding: 2px 10px;
}
</style>




<!-- Javascripts -->

<script>
	var context_path = '<%= request.getContextPath()%>';
	var sessionId = '<%= request.getSession().getId()%>';
</script>	
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/jquery.js" htmlEscape="true" />"></script>
	<script type="text/javascript"
		src="<spring:url value="/static/resources/js/jquery/jquery-post-json.js" htmlEscape="true" />"></script>
		
	<script type="text/javascript"
		src="<spring:url value="/static/resources/js/jquery/jquery-ui.js" htmlEscape="true" />"></script>

	<script type="text/javascript"
		src=" <spring:url value="/static/resources/js/jquery/dtable/jquery.dataTables.js" htmlEscape="true" />"></script>
		
	<script type="text/javascript"
		src="<spring:url value="/static/resources/js/jquery/dtable/jquery.dataTables.pagination.js" htmlEscape="true" />"></script>
		
			
	<script type="text/javascript"
		src="<spring:url value="/static/resources/js/jquery/form2Object/form2object.js" htmlEscape="true" />"></script>
	<script type="text/javascript"
		src="<spring:url value="/static/resources/js/jquery/form2Object/jquery.toObject.js" htmlEscape="true" />"></script>	
	<script type="text/javascript"
		src="<spring:url value="/static/resources/js/json/json2.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/json/json.min.js" htmlEscape="true" />"></script>		
<script type="text/javascript" src="${staticResourcesPath}/js/delta/gestionCtrlSelect.js"></script>	<!-- JS General de la aplicacion  -->
	<script type="text/javascript"
		src="<spring:url value="/static/resources/js/delta/general.js" htmlEscape="true" />"></script>

	


</head>
<body>

<div class="site_position_center">
<c:set var="contextpath" value="<%=request.getContextPath()    %>" />
	<div class="main_wrap">


	<div id="cuerpo_principal">
		
		<div id="encabezado">
				<tiles:insertAttribute name="encabezado" />
		</div>
		<div id="subencabezado">
				<tiles:insertAttribute name="subencabezado" />
		</div>
		<div id="cuerpo">
					<tiles:insertAttribute name="contenido" />
		</div>
	<div id="pie">
					<tiles:insertAttribute name="pie" />
				</div>
	</div> 

	
	</div>
	
	</div>
	
	
	<div style="display: none;">
		<c:set var="contextpath" value="<%=request.getContextPath()    %>" />
		<form id="formCerrarSesion" action="${staticLogoutPath}" method="get"></form>
	</div>
	
	
	
</body>
</html>