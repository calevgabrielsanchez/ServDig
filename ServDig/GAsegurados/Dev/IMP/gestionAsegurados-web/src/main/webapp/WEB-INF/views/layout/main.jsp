<%@ include file="../general/taglibs.jsp"%>

<!-- This is the main page -->

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

<jsp:include page="staticResources.jsp">
	<jsp:param value="true" name="isGobMxIncluded"/>
</jsp:include>

<script>
	var context_path = '<%=request.getContextPath()%>';
	var server_scheme = '<%=request.getScheme()%>';
	var server_port = '<%=request.getServerPort()%>';
	var server_name = '<%=request.getServerName()%>';

	$(document).ready(function() {
		$('form:not(.formNotBlock)').submit(function() {
			$.blockUI();
		});

		if (server_port != '80') {
			server_name = server_scheme + '://' + server_name + ':'
					+ server_port + '/';
		} else {
			server_name = server_scheme + '://' + server_name + '/';
		}
	});
</script>

<style>
	#info-paso {
	    margin-bottom: 40px;
	}
</style>
</head>

<body>
	<!-- Contenido -->
	<main class="page">
		<div class="container">	
			<tiles:insertAttribute name="submenu" />
			<tiles:insertAttribute name="subencabezado" />
			<tiles:insertAttribute name="contenido" />
		</div>
		
	</main>
	
	<div style="display: none;">
		<c:set var="contextpath" value="<%=request.getContextPath()%>" />
		<form id="formCerrarSesion" action="${staticLogoutPath}" method="get"></form>
	</div>
	<div id="dialogEndOfSession" style="display: none"
		title="Sesi&oacute;n terminada por inactividad">
		<span>Su sesi&oacute;n se ha desactivado debido a inactividad.</span>
	</div>
	
	<div>
		<!-- GobMx -->
		<jsp:include page="footer.jsp"/>
	</div>
	
	<script>
	 	$gmx(document).ready(function() {  
	 		/* 
	 		 * Fix para que los dialogos creados con jQueryUI
	 		 * no tengan conflictos con bootstrap
	 		 */
			$.fn.button.noConflict();
		});
	</script>
</body>
</html>