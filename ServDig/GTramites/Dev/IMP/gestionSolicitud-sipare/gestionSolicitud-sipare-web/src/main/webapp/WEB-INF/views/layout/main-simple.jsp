<%@ include file="../general/taglibs.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="max-age=100, must-revalidate" />
<meta name="description" content="Instituto Mexicano del Seguro Social Portal DELTA" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta lang="es">
<title><tiles:insertAttribute name="title" ignore="true" /></title>

<jsp:include page="staticResources.jsp"></jsp:include>

<script>
	var context_path = '<%=request.getContextPath()%>';
</script>

</head>

<body>
	<div class="site_position_center">
		<c:set var="contextpath" value="<%=request.getContextPath()%>" />
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
		<form id="formCerrarSesion"
			action="http://ssoimssdigd.imss.gob.mx:7001/openam_10.0.0/UI/Logout"
			method="get"></form>
	</div>
</body>
</html>