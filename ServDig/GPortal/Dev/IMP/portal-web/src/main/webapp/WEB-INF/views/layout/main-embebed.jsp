<%@ include file="../general/taglibs.jsp"%>
<!-- This is the main page embebed -->

<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="max-age=100, must-revalidate" />
<meta name="description" content="Instituto Mexicano del Seguro Social Portal DELTA" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><tiles:insertAttribute name="title" ignore="true" /></title>

<jsp:include page="staticResources.jsp"></jsp:include>

<link type="text/css" href="${staticResourcesPath}/estilos/jquery/demo_page.css" rel="stylesheet" />
<link type="text/css" href="${staticResourcesPath}/estilos/jquery/demo_table.css" rel="stylesheet" />

<script>
	var context_path = '<%= request.getContextPath()%>';
	var server_name = '<%= request.getServerName()%>';

	$(document).ready(function(){
		$('form:not(.formNotBlock)').submit(function(){
			$.blockUI();
		});	
	});
</script>
<link href="https://framework-gb.cdn.gob.mx/assets/styles/main.css" rel="stylesheet" />
</head>

<body>
	<div class="site_position_center_fixed">
		<div id="cuerpo_principal" class="main_wrap">
			<div id="encabezado">
				<tiles:insertAttribute name="encabezado" />
			</div>
			<div id="funciones">
				<tiles:insertAttribute name="funciones" />
			</div>
			<div id="cuerpo">
				<tiles:insertAttribute name="contenido" />
			</div>
			<div id="controles">
				<tiles:insertAttribute name="controles" />
			</div>
			<div id="pie">
				<tiles:insertAttribute name="pie" />
			</div>
		</div>
	</div>

	<div style="display: none;">
		<c:set var="contextpath" value="<%=request.getContextPath()    %>" />
		<form id="formCerrarSesion"
			action="<spring:url value="/j_spring_security_logout"/>" method="get"></form>
	</div>
</body>
</html>