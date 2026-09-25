<%@ include file="../general/taglibs.jsp"%>

<!DOCTYPE html>

<html lang="es">
<head>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="max-age=100, must-revalidate" />
<meta name="description"
	content="Instituto Mexicano del Seguro Social Portal DELTA" />
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title><tiles:insertAttribute name="title" ignore="true" /></title>

<jsp:include page="staticResources.jsp"></jsp:include>

<script>
	var context_path = '<%= request.getContextPath()%>';
</script>
</head>

<body>
	<div class="site_position_center" style="width: 500px !important;">
		<div id="cuerpo_principal" class="main_wrap"
			style="width: 500px !important;">
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