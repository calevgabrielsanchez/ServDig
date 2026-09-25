<%@ include file="../general/taglibs.jsp" %>
<!-- Main Wizard page template -->


<!DOCTYPE html>
<html>
<head>

<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="max-age=100, must-revalidate" />
<meta name="description" content="Instituto Mexicano del Seguro Social Portal DELTA" />
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title><tiles:insertAttribute name="title" ignore="true" /></title>


<link rel="icon" href="${staticResourcesPath}/iconos/favicon.ico" />



<c:set var="staticResourcesPath" value='<%=request.getSession().getServletContext().getInitParameter("STATIC_RESOURCES_PATH") %>'/>


<!-- Estilos Jquery-->


<link type="text/css" href="${staticResourcesPath}/estilos/jquery/ui-lightness/jquery-ui.css" rel="stylesheet" />
<link type="text/css" href="${staticResourcesPath}/estilos/jquery/demo_page.css" rel="stylesheet" />
<link type="text/css" href="${staticResourcesPath}/estilos/jquery/demo_table.css" rel="stylesheet" />
<link type="text/css" href="${staticResourcesPath}/estilos/jquery/demo_table.css" rel="stylesheet" />

<!-- Bootstrap -->	
<link type="text/css" href="${staticResourcesPath}/estilos/bootstrap/bootstrap.min.css" rel="stylesheet" />

<!-- JGrowl -->
<link type="text/css" href="${staticResourcesPath}/estilos/jquery/jquery.jgrowl.css" rel="stylesheet" />

<!-- IMSS -->
<link type="text/css" href="${staticResourcesPath}/estilos/imss/reset.css" rel="stylesheet" />
<link type="text/css" href="${staticResourcesPath}/estilos/imss/style.css" rel="stylesheet" />
	
<!-- Javascripts -->
<script>
	var context_path = '<%= request.getContextPath()%>';
</script>




<!-- jQuery -->
<script type="text/javascript" src="${staticResourcesPath}/js/jquery/jquery.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/jquery/jquery-post-json.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/jquery/jquery-ui.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/jquery/dtable/jquery.dataTables.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/jquery/dtable/jquery.dataTables.pagination.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/jquery/form2Object/form2object.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/jquery/form2Object/jquery.toObject.js"></script>
<!-- Bootstrap -->
<script type="text/javascript" src="${staticResourcesPath}/js/bootstrap/bootstrap.min.js"></script>
<!-- JSON -->
<script type="text/javascript" src="${staticResourcesPath}/js/json/json2.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/json/json.min.js"></script>


<!-- Cometd -->
<script type="text/javascript" src="${staticResourcesPath}/js/org/cometd.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/org/cometd/AckExtension.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/org/cometd/ReloadExtension.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/org/cometd/TimeStampExtension.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/org/cometd/TimeSyncExtension.js"></script>

<!-- Cometd and Jquery-->

<script type="text/javascript" src="${staticResourcesPath}/js/jquery/comet/jquery.cookie.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/jquery/comet/jquery.cometd.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/jquery/comet/jquery.cometd-ack.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/jquery/comet/jquery.cometd-reload.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/jquery/comet/jquery.cometd-timestamp.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/jquery/comet/jquery.cometd-timesync.js"></script>

<!-- JGrowl -->
<script type="text/javascript" src="${staticResourcesPath}/js/jquery/comet/jquery.jgrowl.js"></script>


<!-- Generales DELTA -->		
<script type="text/javascript" src="${staticResourcesPath}/js/delta/gestionCtrlSelect.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/delta/general.js"></script>

<!-- html5 enabled -->
<script type="text/javascript" src="${staticResourcesPath}/js/delta/html5.js"></script>

<!-- Scripts de "Procesando..." -->
	<script type="text/javascript" src="${staticResourcesPath}/js/jquery/blockUI/jquery.blockUI.js"></script>
	<script>
		$(document).ready(function(){
			
			
			$('form:not(.formNotBlock)').submit(function(){
				$.blockUI();
			});	
		});
		</script>



</head>


<body>

<div class="site_position_center_fixed">
<c:set var="contextpath" value="<%=request.getContextPath()    %>" />
	<div>


	<div>
		
		
		<div class="wizard">
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
		<form id="formCerrarSesion" action="${contextpath}/logout" method="get"></form>
	</div>
	
	
	
</body>
</html>