<%@ include file="../general/taglibs.jsp"%>

<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="max-age=100, must-revalidate" />
<meta name="description" content="Instituto Mexicano del Seguro Social Portal DELTA" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta lang="es">
<title><tiles:insertAttribute name="title" ignore="true" /></title>

<jsp:include page="staticResources.jsp"></jsp:include>

<link type="text/css" href="${staticResourcesPath}/estilos/jquery/demo_page.css" rel="stylesheet" />
<link type="text/css" href="${staticResourcesPath}/estilos/jquery/demo_table.css" rel="stylesheet" />

<style type="text/css" media="screen">
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

<script>
	var context_path = '<%=request.getContextPath()%>';
	var sessionId = '<%=request.getSession().getId()%>';
</script>
<link href="https://framework-gb.cdn.gob.mx/assets/styles/main.css" rel="stylesheet" />
</head>

<body>
	<div class="site_position_center">
		<c:set var="contextpath" value="<%=request.getContextPath()%>" />
		<div class="main_wrap">
			<div id="cuerpo_principal">
				<div id="encabezado"></div>
				<div id="subencabezado">
					<tiles:insertAttribute name="subencabezado" />
				</div>
				<div id="cuerpo">
					<tiles:insertAttribute name="contenido" />
				</div>
				<div id="pie"></div>
			</div>
		</div>
	</div>

	<div style="display: none;">
		<form id="formCerrarSesion"
			action="${staticLogoutPath}"
			method="get"></form>
	</div>
</body>
</html>