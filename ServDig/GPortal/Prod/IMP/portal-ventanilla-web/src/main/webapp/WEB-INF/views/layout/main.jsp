<%@ include file="../general/taglibs.jsp"%>

<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="max-age=100, must-revalidate" />
<meta name="description" content="Instituto Mexicano del Seguro Social Portal DELTA" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta lang="es">
<title><tiles:insertAttribute name="title" ignore="true" /></title>

<jsp:include page="staticResources.jsp">
	<jsp:param value="true" name="isGobMxIncluded"/>
</jsp:include>

<script>
	var context_path = '<%=request.getContextPath()%>';
	
	$(function(){
		
		$('form:not(.formNotBlock)').submit(function() {
			$.blockUI();
		}); 
	});
	
</script>
</head>

<body>
	<!-- Contenido -->
	<main class="page">
		<div class="container">
			<tiles:insertAttribute name="infoUsuario" />
			<tiles:insertAttribute name="subencabezado" />
			<tiles:insertAttribute name="contenido" />
		</div>
	</main>
	
	<div style="display: none;">
		<form id="formCerrarSesion" action="${pageContext.servletContext.contextPath}/j_spring_security_logout" method="get"></form>
	</div>
	
	<div id="dialogEndOfSession" style="display: none" title="Sesi&oacute;n terminada por inactividad">
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