<%@ include file="../general/taglibs.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="max-age=100, must-revalidate" />
<meta name="description"
	content="Instituto Mexicano del Seguro Social Portal DELTA" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta lang="es">
<title><tiles:insertAttribute name="title" ignore="true" /></title>

<jsp:include page="staticResources.jsp"></jsp:include>

<script>
	var context_path = '<%= request.getContextPath()%>';
</script>
</head>

<body>
	<div class="main_wrap_shadow">
		<tiles:insertAttribute name="encabezado" />

		<div id="cuerpo" class="page">
			<div id="cuerpo" class="container">
				<tiles:insertAttribute name="subencabezado" />
				<tiles:insertAttribute name="submenu" />
				<tiles:insertAttribute name="contenido" />
			</div>
		</div>

		<tiles:insertAttribute name="pie" />
	</div>

	<form id="formCerrarSesion" action="${staticLogoutPath}" method="get"></form>
</body>
</html>