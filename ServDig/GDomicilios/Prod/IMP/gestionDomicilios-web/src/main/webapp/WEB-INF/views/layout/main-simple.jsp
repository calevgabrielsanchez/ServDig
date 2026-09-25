<%@ include file="../general/taglibs.jsp"%>

<!-- This is the main simple -->

<!DOCTYPE html>
<html lang="es">
<head>

<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="max-age=100, must-revalidate" />
<meta name="description"
	content="Instituto Mexicano del Seguro Social Portal DELTA" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><tiles:insertAttribute name="title" ignore="true" /></title>

<jsp:include page="staticResources.jsp"></jsp:include>

<link rel="icon" href="${staticResourcesPath}/iconos/favicon.ico" />

<script>
	var context_path = '<%=request.getContextPath()%>';
	$(document).ready(function(){
		
		
		$('form:not(.formNotBlock)').submit(function(){
			$.blockUI();
		});	
	});
</script>
<script type="text/javascript" id="User1st_Loader" src="/portal-ciudadano-web-externo/static/resources/js/head.js"></script>
<script type="text/javascript" id="User1st_Loader" src="/portal-ciudadano-web-externo/static/resources/js/Loader.js"></script>
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
			action="${staticLogoutPath}"
			method="get"></form>
	</div>

</body>
</html>