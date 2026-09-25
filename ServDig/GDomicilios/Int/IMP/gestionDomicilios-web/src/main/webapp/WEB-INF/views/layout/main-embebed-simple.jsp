<%@ include file="../general/taglibs.jsp"%>

<!-- This is the main-embebed-simple page -->
<!DOCTYPE html>
<html lang="es">
<head>

<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="max-age=100, must-revalidate" />
<meta name="description" content="Instituto Mexicano del Seguro Social Portal DELTA" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><tiles:insertAttribute name="title" ignore="true" /></title>

<jsp:include page="staticResources.jsp"></jsp:include>

<script>
	var context_path = '<%= request.getContextPath()%>';
	$(document).ready(function(){
		
		
		$('form:not(.formNotBlock)').submit(function(){
			$.blockUI();
		});	
	});
</script>
<script type="text/javascript" id="User1st_Loader" src="/portal-ciudadano-web-externo/static/resources/js/head.js"></script>
<script type="text/javascript" id="User1st_Loader" src="/portal-ciudadano-web-externo/static/resources/js/Loader.js"></script>
</head>

<body class="p-t-none">
	<main class="page m-t-none">
		<div class="container-fluid">	
			<div class="wizard">
				<div id="funciones">
					<tiles:insertAttribute name="funciones" />
				</div>
				<tiles:insertAttribute name="contenido" />
			</div>
		</div>
	</main>
	
	<!-- GobMx -->
	<script src="${staticResourcesPath}/js/gobmx/gobmx-fonts.js"></script>
</body>	
</html>