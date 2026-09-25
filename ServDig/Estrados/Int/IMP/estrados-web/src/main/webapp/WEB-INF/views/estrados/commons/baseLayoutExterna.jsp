<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<%@ include file="taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv="expires" content="0">
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="max-age=100, must-revalidate" />
		
		<link rel="icon" href="/delta/resources/iconos/favicon.ico" />
		<!-- Jquery--> 
		<link type="text/css" href="/delta/resources/estilos/jquery/ui-lightness/jquery-ui.css" rel="stylesheet" /> 
		<!-- Bootstrap --> 
		<link type="text/css" href="/delta/resources/estilos/bootstrap/bootstrap.min.css" rel="stylesheet" /> 
		<link type="text/css" href="/delta/resources/estilos/bootstrap/bootstrap-tooltip.css" rel="stylesheet" /> 
		<link type="text/css" href="/delta/resources/estilos/bootstrap/bootstrap-popover.css" rel="stylesheet" /> 
		<link type="text/css" href="/delta/resources/estilos/bootstrap/DT_bootstrap.css" rel="stylesheet" /> 
		<!-- Fonts --> 
		<link type="text/css" href="/delta/resources/estilos/font-awesome/css/font-awesome.css" rel="stylesheet" /> 
		<!-- JGrowl --> 
		<link type="text/css" href="/delta/resources/estilos/jquery/jquery.jgrowl.css" rel="stylesheet" /> 
		<!-- Jqplot --> 
		<link type="text/css" href="/delta/resources/estilos/jquery/jquery.jqplot.min.css" rel="stylesheet" /> 
		<link type="text/css" href="/delta/resources/estilos/jquery/morris.css" rel="stylesheet" /> 
		<!-- IMSS --> 
		<link type="text/css" href="/delta/resources/estilos/imss/reset.css" rel="stylesheet" /> 
		<link type="text/css" href="/delta/resources/estilos/imss/style.css" rel="stylesheet" /> 
		<link type="text/css" href="/delta/resources/estilos/imss/portal.css" rel="stylesheet" /> 
		<!--[if IE]> <link href="/delta/resources/estilos/imss/ie.css" type="text/css" rel="stylesheet" /><![endif]--> 
		<!-- jQuery --> 
		<script type="text/javascript" src="/delta/resources/js/jquery/jquery.js"></script> 
		<script type="text/javascript" src="/delta/resources/js/jquery/jquery.ui.datepicker-es.js"></script> 
		<script type="text/javascript" src="/delta/resources/js/jquery/jquery-post-json.js"></script> 
		<script type="text/javascript" src="/delta/resources/js/jquery/jquery-ui.js"></script> 
		<script type="text/javascript" src="/delta/resources/js/jquery/dtable/jquery.dataTables.js"></script> 
		<script type="text/javascript" src="/delta/resources/js/jquery/dtable/jquery.dataTables.pagination.js"></script> 
		<script type="text/javascript" src="/delta/resources/js/jquery/dtable/jquery.dataTables.sort.date.plugin.js"></script>
		<script type="text/javascript" src="/delta/resources/js/jquery/form2Object/form2object.js"></script> 
		<script type="text/javascript" src="/delta/resources/js/jquery/form2Object/jquery.toObject.js"></script> 
		<script type="text/javascript" src="/delta/resources/js/jquery/jquery.alphanum.js"></script> 
		<!-- Bootstrap --> 
		<script type="text/javascript" src="/delta/resources/js/bootstrap/bootstrap.min.js"></script> 
		<script type="text/javascript" src="/delta/resources/js/bootstrap/DT_bootstrap.js"></script> 
		<script type="text/javascript" src="/delta/resources/js/bootstrap/bootstrap-tooltip.js"></script> 
		<script type="text/javascript" src="/delta/resources/js/bootstrap/bootstrap-popover.js"></script> 
		<!-- JSON --> 
		<script type="text/javascript" src="/delta/resources/js/json/json2.js"></script> 
		<script type="text/javascript" src="/delta/resources/js/json/json.min.js"></script> 
		<!-- Cometd --> 
		<script type="text/javascript" src="/delta/resources/js/org/cometd.js"></script> 
		<script type="text/javascript" src="/delta/resources/js/org/cometd/AckExtension.js"></script> 
		<script type="text/javascript" src="/delta/resources/js/org/cometd/ReloadExtension.js"></script> 
		<script type="text/javascript" src="/delta/resources/js/org/cometd/TimeStampExtension.js"></script> 
		<script type="text/javascript" src="/delta/resources/js/org/cometd/TimeSyncExtension.js"></script> 
		<!-- Cometd and Jquery--> 
		<script type="text/javascript" src="/delta/resources/js/jquery/comet/jquery.cookie.js"></script> 
		<script type="text/javascript" src="/delta/resources/js/jquery/comet/jquery.cometd.js"></script> 
		<script type="text/javascript" src="/delta/resources/js/jquery/comet/jquery.cometd-ack.js"></script> 
		<script type="text/javascript" src="/delta/resources/js/jquery/comet/jquery.cometd-reload.js"></script> 
		<script type="text/javascript" src="/delta/resources/js/jquery/comet/jquery.cometd-timestamp.js"></script> 
		<script type="text/javascript" src="/delta/resources/js/jquery/comet/jquery.cometd-timesync.js"></script> 
		<!-- JGrowl --> 
		<script type="text/javascript" src="/delta/resources/js/jquery/comet/jquery.jgrowl.js"></script> 
		<!-- JQPlot --> 
		<script type="text/javascript" src="/delta/resources/js/jqplot/jquery.jqplot.min.js"></script> 
		<script type="text/javascript" src="/delta/resources/js/jqplot/jqplot.pieRenderer.js"></script> 
		<script type="text/javascript" src="/delta/resources/js/morris/morris.js"></script> 
		<script type="text/javascript" src="/delta/resources/js/morris/raphael.min.js"></script> 
		<!-- Generales DELTA --> 
		<script type="text/javascript" src="/delta/resources/js/delta/general.js"></script> 
		<script type="text/javascript" src="/delta/resources/js/delta/CometConector.js"></script> 
		<script type="text/javascript" src="/delta/resources/js/delta/gestionCtrlSelect.js"></script> 
		<script type="text/javascript" src="/delta/resources/js/delta/procesaErrores.js"></script> 
		<script type="text/javascript" src="/delta/resources/js/delta/limpiaFormularios.js"></script> 
		<!-- html5 enabled --> 
		<script type="text/javascript" src="/delta/resources/js/delta/html5.js"></script> 
		<!-- Scripts de "Procesando..." --> 
		<script type="text/javascript" src="/delta/resources/js/jquery/blockUI/jquery.blockUI.js"></script> 
		
		<!-- Script para la prueba del arquetipo -->
<%-- 		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/test.js"></script>  --%>
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/commons/jquery-post-json.js"></script> 
		
		<script language="JavaScript">
			var appContextenJS = "<%=request.getContextPath()%>";
			function getAppContextParaJS() {
				return appContextenJS;
			}
		</script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/commons/jquery.validate.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/commons/jquery.form.js"></script>
		<style type="text/css" media="screen">
			<%-- @import "<%=request.getContextPath()%>/resources/css/demo_table.css"; --%>
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
			#example_wrapper .fg-toolbar {
				font-size: 0.8em
			}
			#theme_links span {
				float: left;
				padding: 2px 10px;
			}
		</style>
		<title><tiles:insertAttribute name="title" ignore="true" /></title> 
	</head>
    
	<body>
		<div align="center">
			<table border="1" cellpadding="0" cellspacing="0" align="center">
				<tr valign="top">
					<td>
						<tiles:insertAttribute name="body"/>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>