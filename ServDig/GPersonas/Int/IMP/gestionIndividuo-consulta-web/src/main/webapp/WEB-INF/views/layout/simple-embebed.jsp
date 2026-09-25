<%@ include file="taglibs.jsp"%>

<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="max-age=100, must-revalidate" />
<meta name="description"
	content="Instituto Mexicano del Seguro Social Portal DELTA" />

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta lang="es">

<title><tiles:insertAttribute name="title" ignore="true" /></title>

<jsp:include page="staticResources.jsp"></jsp:include>

<link type="text/css" href="${staticResourcesPath}/estilos/jquery/demo_page.css" rel="stylesheet" />
<link type="text/css" href="${staticResourcesPath}/estilos/jquery/demo_table.css" rel="stylesheet" />

<script type="text/javascript">
	var context_path = '<%= request.getContextPath()%>';
	var sessionId = '<%= request.getSession().getId()%>';
	
	$(document).ready(function(){
		$('form:not(.formNotBlock)').submit(function(){
			$.blockUI();
		});	
	});
</script>


<!-- JS del control de mensajes de exito  -->
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/controlmensaje/ctrlMensaje.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/personas/generalPersonas.js" htmlEscape="true" />"></script>
</head>

<body>
	<tiles:insertAttribute name="contenido" />
</body>
</html>