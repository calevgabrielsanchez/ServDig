<%@ include file="../general/taglibs.jsp"%>
<!-- This is the main page -->

<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="max-age=100, must-revalidate" />
<meta name="description" content="Instituto Mexicano del Seguro Social Portal DELTA" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><tiles:insertAttribute name="title" ignore="true" /></title>

<jsp:include page="staticResources.jsp"></jsp:include>

<script>
	var context_path = '<%= request.getContextPath()%>';
	
	$(document).ready(function(){
		$('form:not(.formNotBlock)').live('submit', function(){
			$.blockUI();
		});	
	});
</script>

<script type="text/javascript" src="${staticResourcesPath}/js/widget/widget.js"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/portlet.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/home.js" htmlEscape="true" />"></script>
</head>

<body>
	<div class="site_position_center_fixed">
		<div id="cuerpo_principal" class="main_wrap_shadow" style="float: none;">
			<div id="encabezado">
				<tiles:insertAttribute name="encabezado" />
			</div>
			<div id="subencabezado" style="margin-bottom: 15px;">
				<tiles:insertAttribute name="subencabezado" />
			</div>
			<div id="menu" style="margin: 0px;">
				<tiles:insertAttribute name="menu" />
			</div>
			<div id="submenu" style="margin: 0px;">
				<tiles:insertAttribute name="submenu" />
			</div>
			<div id="cuerpo">
				<tiles:insertAttribute name="contenido" />
			</div>
			<div id="pie">
				<tiles:insertAttribute name="pie" />
			</div>
		</div>
	</div>

	<div style="display: none;">
		<c:set var="contextpath" value="<%=request.getContextPath()    %>" />
		<form id="formCerrarSesion"
			action="${staticLogoutPath}"
			method="get"></form>
	</div>

	<div id="dialogEndOfSession" style="display: none"
		title="Sesi&oacute;n terminada por inactividad">
		<span>Su sesi&oacute;n se ha desactivado debido a inactividad.</span>
	</div>

	<div id="waitingDivCommon" style="display: none;">
		<div style="text-align: center; vertical-align: middle;">
			<img alt="" src="${staticResourcesPath}/imagenes/loading.gif" />
		</div>
	</div>
</body>
</html>