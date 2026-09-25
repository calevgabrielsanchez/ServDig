<%@ include file="../general/taglibs.jsp"%>
<!-- This is the main page -->

<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="max-age=100, must-revalidate" />
<meta name="description" content="Instituto Mexicano del Seguro Social Portal DELTA" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><tiles:insertAttribute name="title" ignore="true" /></title>

<jsp:include page="staticResources.jsp"></jsp:include>

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
<link href="https://framework-gb.cdn.gob.mx/assets/styles/main.css" rel="stylesheet" />
</head>

<body>
	<tiles:insertAttribute name="contenido" />
</body>
</html>