<%@ include file="../general/taglibs.jsp"%>
<!-- This is the main page -->

<!DOCTYPE html>
<html>
<head>

<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="max-age=100, must-revalidate" />
<meta name="description"
	content="Instituto Mexicano del Seguro Social Portal DELTA" />
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><tiles:insertAttribute name="title" ignore="true" /></title>


<link rel="icon" href="${staticResourcesPath}/iconos/favicon.ico" />

<c:set var="staticResourcesPath"
	value='<%=request.getSession().getServletContext().getInitParameter("STATIC_RESOURCES_PATH") %>' />

<!-- Estilos Jquery-->
<link type="text/css"
	href="${staticResourcesPath}/estilos/jquery/ui-lightness/jquery-ui.css"
	rel="stylesheet" />

<!-- Bootstrap -->
<link type="text/css"
	href="${staticResourcesPath}/estilos/bootstrap/bootstrap.min.css"
	rel="stylesheet" />
<link type="text/css"
	href="${staticResourcesPath}/estilos/bootstrap/DT_bootstrap.css"
	rel="stylesheet" />

<!-- JGrowl -->
<link type="text/css"
	href="${staticResourcesPath}/estilos/jquery/jquery.jgrowl.css"
	rel="stylesheet" />

<!-- IMSS -->
<link type="text/css"
	href="${staticResourcesPath}/estilos/imss/reset.css" rel="stylesheet" />
<link type="text/css"
	href="${staticResourcesPath}/estilos/imss/style.css" rel="stylesheet" />

<!-- Javascripts -->
<script>
	var context_path = '<%= request.getContextPath()%>';
</script>

<!-- jQuery -->
<script type="text/javascript"
	src="${staticResourcesPath}/js/jquery/jquery.js"></script>
<script type="text/javascript"
	src="${staticResourcesPath}/js/jquery/jquery-post-json.js"></script>
<script type="text/javascript"
	src="${staticResourcesPath}/js/jquery/jquery-ui.js"></script>
<script type="text/javascript"
	src="${staticResourcesPath}/js/jquery/dtable/jquery.dataTables.js"></script>
<script type="text/javascript"
	src="${staticResourcesPath}/js/jquery/dtable/jquery.dataTables.pagination.js"></script>
<script type="text/javascript"
	src="${staticResourcesPath}/js/jquery/form2Object/form2object.js"></script>
<script type="text/javascript"
	src="${staticResourcesPath}/js/jquery/form2Object/jquery.toObject.js"></script>
<script type="text/javascript" 
	src="${staticResourcesPath}/js/jquery/validation/validator/jquery.validate.js"></script>

<!-- Bootstrap -->
<script type="text/javascript"
	src="${staticResourcesPath}/js/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript"
	src="${staticResourcesPath}/js/bootstrap/DT_bootstrap.js"></script>

<!-- JSON -->
<script type="text/javascript"
	src="${staticResourcesPath}/js/json/json2.js"></script>
<script type="text/javascript"
	src="${staticResourcesPath}/js/json/json.min.js"></script>

<!-- Generales DELTA -->
<script type="text/javascript"
	src="${staticResourcesPath}/js/delta/gestionCtrlSelect.js"></script>
<script type="text/javascript"
	src="${staticResourcesPath}/js/delta/general.js"></script>
<script type="text/javascript"
	src=" <spring:url value="/static/resources/js/widget/widget.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src=" <spring:url value="/static/resources/js/portlet/portlet.js" htmlEscape="true" />"></script>

<!-- Scripts de "Procesando..." -->
<script type="text/javascript"
	src="${staticResourcesPath}/js/jquery/blockUI/jquery.blockUI.js"></script>
<script>
	$(document).ready(function(){
		$('form:not(.formNotBlock)').submit(function(){
			$.blockUI();
		});	
	});
</script>

</head>
<body>
	
	<input id="idPersonaCommon" type="hidden" value="${idPersona}"/>
	
	<div class="site_position_center_fixed">

		<div id="cuerpo_principal" class="main_wrap">

			<div id="encabezado">
				<tiles:insertAttribute name="encabezado" />
			</div>
			<div id="subencabezado">
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
			action="http://sso-delta.imss.gob.mx:11000/openam_10.0.0/UI/Logout"
			method="get"></form>
	</div>

</body>
</html>