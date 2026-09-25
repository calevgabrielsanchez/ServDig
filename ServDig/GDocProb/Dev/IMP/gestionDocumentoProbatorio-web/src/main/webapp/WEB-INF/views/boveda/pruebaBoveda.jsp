<%@ include file="../general/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Boveda</title>
	<link href="https://framework-gb.cdn.gob.mx/assets/styles/main.css" rel="stylesheet">
	
<link type="text/css" href="${staticResourcesPath}/estilos/imss/portal.css" rel="stylesheet" />
	
	<script type="text/javascript" src="${staticResourcesPath}/js/jquery/jquery.js"></script>
	
<link type="text/css" href="${staticResourcesPath}/estilos/jquery/ui-lightness/jquery-ui.css" rel="stylesheet" />
	
<script type="text/javascript" src="${staticResourcesPath}/js/jquery/jquery-ui.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/jquery/jquery.ui.datepicker-es.js"></script>

<script type="text/javascript" src="${staticResourcesPath}/js/jquery/jquery.alphanum.js"></script>

<script type="text/javascript" src="${staticResourcesPath}/js/jquery/blockUI/jquery.blockUI.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/delta/general.js"></script>


<script type="text/javascript" src="${staticResourcesPath}/js/jquery/bootstrap-filestyle.min.js"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
	<script type="text/javascript" src="<spring:url value="/static/resources/js/boveda/boveda.js" htmlEscape="true" />"></script>
	<script>
		$(document).ready(function(){
			$("#contenedorBoveda").boveda({
				idTramite: 81487542,
				tipoTramite: 155,
				folio: '8148754281487542',
				rutaBoveda: "/rtt",
				tipoDocumental: "D:RTT:escrito_desacuerdo"/* ,
				datosAdicionales: {
					"registroPatronal" : "H991841913"
				} */
			});
		});
	</script>
</head>
<body>
	<main class="page">
		<div class="container">
			<h4>Prueba boveda</h4>
			<hr class="red"/>
			
			<div id="contenedorBoveda">
			
			</div>
		</div>
	</main>
	<script src="https://framework-gb.cdn.gob.mx/gobmx.js"></script>
</body>
</html>