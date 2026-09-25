<%@ include file="../../general/taglibs.jsp" %>
<!-- This is the main page -->

<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="max-age=100, must-revalidate" />
<meta name="description" content="Instituto Mexicano del Seguro Social Portal DELTA" />
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><tiles:insertAttribute name="title" ignore="true" /></title>

<jsp:include page="../staticResources.jsp"></jsp:include>

<script>
	var context_path = '<%= request.getContextPath()%>';
	
	$(document).ready(function(){
		$('form:not(.formNotBlock)').live('submit', function(){
			$.blockUI();
		});	
	});
</script>
</head>

<body>
	<!-- Contenido -->
	<main class="page">
		<div class="container">
			<div class="row">
				<tiles:insertAttribute name="contenido" />
			</div>
		</div>
	</main>
	
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