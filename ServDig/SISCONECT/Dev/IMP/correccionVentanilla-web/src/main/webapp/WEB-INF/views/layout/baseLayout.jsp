<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd"> 
<html> 
	<head> 
		<link rel="icon" href="<%=request.getContextPath()%>/resources/images/favicon.ico">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/estilos/estilo.css">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/correcion.css">
		<link type="text/css"
				href="<%=request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.14.custom.css" rel="stylesheet" />
				
		<link rel="stylesheet" type="text/css" 	href="<%=request.getContextPath()%>/resources/estilos/estilo.css">
		<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.14.custom.css" >

		<style type="text/css" media="screen">
		@import "<%=request.getContextPath()%>/resources/css/demo_table.css";
		
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


		<script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/delta/jquery1.7.js"></script>
		<script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/delta/bootstrap.min.js"></script>


<%-- 		<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/jquery/jquery-1.6.2.js"></script> --%>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/resources/js/jquery/jquery-post-json.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/resources/js/jquery/jquery-ui-1.8.14.custom.min.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/resources/js/jquery/dtable/jquery.dataTables.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/resources/js/jquery/dtable/jquery.dataTables.fnDisplayStart.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/resources/js/jquery/json.min.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/resources/js/jquery/form2Object/form2object.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/resources/js/jquery/form2Object/jquery.toObject.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/resources/js/delta/gestionCtrlSelect.js"></script>	
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/resources/js/jquery/jquery.blockUI.1.33.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/resources/js/jquery/validation/validator/jquery.validate.js"></script>	
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/resources/js/jquery/validation/validator/additional-methods.js"></script>	
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/resources/js/jquery/validation/validator/messages_es.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/resources/js/jquery/jquery.maskedinput-1.2.1.pack.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/resources/js/jquery/mask/jquery.maskedinput-1.3.js"></script>
			
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/resources/js/delta/funcionesComunes.js"></script>
		<script type="text/javascript"
				src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
		<script type="text/javascript"
				src="<%=request.getContextPath()%>/resources/js/delta/KeyPressed.js"></script>
		<script type="text/javascript"
				src="<%=request.getContextPath()%>/resources/js/delta/jquery.formatCurrency-1.4.0.pack.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/resources/js/delta/calendarios.js"></script>
		
		<!--Scripts para el cerrado de la sesssion -->
		
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
				<script type="text/javascript" src="/delta/resources/js/delta/CometConector.js"></script>		
		
			<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/home.js"></script>
		
		
		<title><tiles:insertAttribute name="title" ignore="true" /></title> 
	</head> 
    
	<body> 
		<jsp:include page="../agregaContextoJS.jsp" />
		<jsp:include page="../agregaFechaServidor.jsp" />
		
		<table width="900"  border="0" cellspacing="0" cellpadding="0" align="center">
		  <tr>
		    <td colspan="2" height="30"><tiles:insertAttribute name="header" /></td>
		  </tr>
		  <tr valign="top">
		    <td width="130" align="left" bgcolor="#FFFFFF"><tiles:insertAttribute name="menu" /></td>
		    <td width="770"><tiles:insertAttribute name="body" /></td>
		  </tr>
		  <tr>
		    <td colspan="2" height="30" ><tiles:insertAttribute name="footer" /></td>
		  </tr>
		</table>


		 <div id="dialogEndOfSession" style="display: none" title="Sesi&oacute;n terminada por inactividad">
		    <span>Su sesi&oacute;n se ha desactivado debido a inactividad en el portal</span>
		</div>
		<div id="componenteDomiciliosCorreccion"></div>
	</body> 
</html>