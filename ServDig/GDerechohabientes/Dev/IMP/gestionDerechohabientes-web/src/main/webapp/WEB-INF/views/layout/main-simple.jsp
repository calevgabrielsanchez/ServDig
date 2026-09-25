<%@ include file="../general/taglibs.jsp" %>

<!DOCTYPE html>
<html lang="es">
<head>

<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="max-age=100, must-revalidate" />
<meta name="description" content="Instituto Mexicano del Seguro Social Portal DELTA" />
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>


<title><tiles:insertAttribute name="title" ignore="true" /></title>

<jsp:include page="staticResources.jsp"></jsp:include>

<style type="text/css">

/*
* Override styles needed due to the mix of three different CSS sources! For proper examples
* please see the themes example in the 'Examples' section of this site
*/
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


</head>
<body>

<div class="site_position_center">
<c:set var="contextpath" value="<%=request.getContextPath()    %>" />
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
		<c:set var="contextpath" value="<%=request.getContextPath()    %>" />
		<form id="formCerrarSesion" action="${staticLogoutPath}" method="get"></form>
	</div>
	
	<div id="dgCerrarSesion" title="Cerrar Sesion" >
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"> </span>
			  &iquest;Esta Ud. seguro de cerrar su sesi&oacute;n?
		</p>
		<br>
		 
		<span id="errorNegocioLabel" class=" hiddenElement error"></span>
	</div>
	
</body>
</html>