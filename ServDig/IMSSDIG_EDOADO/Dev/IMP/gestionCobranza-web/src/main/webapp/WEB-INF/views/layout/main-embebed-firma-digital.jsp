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

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/firma-digital/firma-digital-common.js" htmlEscape="true" />"></script>

<script>
	var context_path = '<%=request.getContextPath()%>';

	$(document).ready(function(){
		$('form:not(.formNotBlock)').submit(function(){
			$.blockUI();
		});	
	});
</script>

<style>
	.hero-unit {
		padding: 25px;
	}
	
	.hero-unit p {
		color: inherit;
		font-size: smaller;
		line-height: normal;
		text-align: justify;
	}
	
	ol,ul {
		list-style: disc;
	}
	
	.row {
		margin-left: 0;
	}
</style>

</head>

<c:set var="titulo" value='<tiles:insertAttribute name="titulo" ignore="true" />' scope="request" />

<body>
	<div id="encabezado">
		<tiles:insertAttribute name="encabezado" />
	</div>
	<div id="subencabezado">
		<div class="titulo_sistema texto-centrado">
			<span>Proceso Firma Digital</span>

		</div>
	</div>
	<div id="cuerpo_principal" class="hero-unit">
		<div id="cuerpo">
			<tiles:insertAttribute name="contenido" />
		</div>

	</div>
</body>
</html>