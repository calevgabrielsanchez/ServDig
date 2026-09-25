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

<style>
.alert-info {
    background-color: #d9edf7 !important;
    border-color: #bce8f1 !important;
    color: #3a87ad !important;
    border: 1px solid #fbeed5;
    border-radius: 4px;
    margin-bottom: 20px;
    padding: 8px 35px 8px 14px;
    text-shadow: 0 1px 0 rgba(255, 255, 255, 0.5);
}


</style>

</head>
<body>
	<iframe src="<%=request.getContextPath()%>/header.jsp" height="50" style="width: 100%"> </iframe>
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
	<iframe src="<%=request.getContextPath()%>/footer.jsp" scrolling="no" height="480" style="width: 100%"> </iframe>
</body>
</html>