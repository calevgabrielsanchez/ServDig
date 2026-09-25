<%@ include file="../general/taglibs.jsp"%>
<!-- Main simple page template -->


<!DOCTYPE html>
<html lang="es">
<head>

<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="max-age=100, must-revalidate" />
<meta name="description" content="Instituto Mexicano del Seguro Social Portal DELTA" />
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><tiles:insertAttribute name="title" ignore="true" /></title>

<jsp:include page="staticResources.jsp"></jsp:include>

<script>
	var context_path = '<%=request.getContextPath()%>';

	$(document).ready(function(){	
		$('form:not(.formNotBlock)').submit(function(){
			$.blockUI();
		});	
	});
</script>

</head>

<body>

	<div class="site_position_center_fixed">
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