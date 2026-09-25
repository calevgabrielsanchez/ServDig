<%@ include file="../general/taglibs.jsp"%>

<!DOCTYPE html>

<html lang="es">
<head>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="max-age=0, no-cache, no-store, must-revalidate" />
<meta http-equiv="expires" content="0" />
<meta name="description" content="Instituto Mexicano del Seguro Social Portal DELTA" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta lang="es">
<title><tiles:insertAttribute name="title" ignore="true" /></title>

<jsp:include page="staticResources.jsp"></jsp:include>

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
	var context_path = '<%= request.getContextPath()%>';
	var sessionId = '<%= request.getSession().getId()%>';
</script>
</head>

<body>
	<div class="site_position_center">
		<c:set var="contextpath" value="<%=request.getContextPath() %>" />
		<div class="main_wrap">
			<div id="cuerpo_principal">
				<div id="cuerpo">
					<tiles:insertAttribute name="contenido" />
				</div>
			</div>
		</div>
	</div>
</body>
</html>