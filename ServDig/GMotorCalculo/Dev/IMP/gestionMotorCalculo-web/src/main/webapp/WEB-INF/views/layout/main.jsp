<%@ include file="../general/taglibs.jsp"%>


<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="max-age=100, must-revalidate" />
<meta name="description"
	content="Instituto Mexicano del Seguro Social Portal DELTA" />

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta lang="es">
<title><tiles:insertAttribute name="title" ignore="true" /></title>

<jsp:include page="staticResources.jsp"></jsp:include>

<link type="text/css" href="${staticResourcesPath}/estilos/jquery/demo_page.css" rel="stylesheet" />
<link type="text/css" href="${staticResourcesPath}/estilos/jquery/demo_table.css" rel="stylesheet" />

<script>
	var context_path = '<%= request.getContextPath()%>';
	var sessionId = '<%= request.getSession().getId()%>';

	$(function(){
		//C�digo que elimina la funcionalidad de hisotry.back() del backspace;
		$(document).keydown(function(e){ 
			var elid = $(document.activeElement).is("input:focus, textarea:focus"); 
	        if(e.keyCode === 8 && !elid){ 
	           e.preventDefault(); 
	        }; 
	    });
	});
</script>

</head>

<body>
	<div class="site_position_center">
		<div id="cuerpo_principal" class="main_wrap">
			<div id="encabezado">
				<tiles:insertAttribute name="encabezado" />
			</div>
			<div id="subencabezado">
				<tiles:insertAttribute name="subencabezado" />
			</div>
			<%-- <div id="menu" style="margin: 0px;">
				<tiles:insertAttribute name="menu" />
			</div> --%>
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
</body>
</html>