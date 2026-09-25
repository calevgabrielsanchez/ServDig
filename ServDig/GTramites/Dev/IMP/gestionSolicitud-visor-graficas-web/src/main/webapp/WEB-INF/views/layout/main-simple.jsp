<%@ include file="../general/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black-translucent" names="apple-mobile-web-app-status-bar-style">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="max-age=100, must-revalidate" />
<meta name="description"
	content="Instituto Mexicano del Seguro Social Portal DELTA" />
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />

<link rel="apple-touch-icon" href="<spring:url value="/static/resources/imagenes/icon/ios7/Icon-60.png" htmlEscape="true" />" />
<link rel="apple-touch-icon" sizes="76x76" href="<spring:url value="/static/resources/imagenes/icon/ios7/Icon-76.png" htmlEscape="true" />" />
<link rel="apple-touch-icon" sizes="120x120" href="<spring:url value="/static/resources/imagenes/icon/ios7/Icon-60@2x.png" htmlEscape="true" />" />
<link rel="apple-touch-icon" sizes="152x152" href="<spring:url value="/static/resources/imagenes/icon/ios7/Icon-76@2x.png" htmlEscape="true" />" />
<link rel="apple-touch-icon-precomposed" href="<spring:url value="/static/resources/imagenes/icon/android/drawable-xxhdpi/ic_launcher.png" htmlEscape="true" />" />

<title><tiles:insertAttribute name="title" ignore="true" /></title>

<jsp:include page="staticResources.jsp"></jsp:include>

<link type="text/css" href="<spring:url value="/static/resources/estilos/visor-graficas.css" htmlEscape="true" />" rel="stylesheet" />

<script>
	var context_path = '<%= request.getContextPath()%>';
</script>

<script src="<spring:url value="/static/resources/js/delta/home.js" htmlEscape="true" />"></script>
</head>

<body>
	<tiles:insertAttribute name="contenido" />
</body>
</html>