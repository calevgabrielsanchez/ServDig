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
	
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/resources/js/jquery/jquery-1.6.2.js"></script>
		
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/resources/js/jquery/jquery-ui-1.8.14.custom.min.js"></script>
			
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/resources/js/jquery/jquery.blockUI.1.33.js"></script>
					
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/resources/js/delta/funcionesComunes.js?v=2"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/resources/js/delta/calendarios.js"></script>
				 
		<title><tiles:insertAttribute name="title" ignore="true" /></title> 
	</head> 
    
	<body> 
		
		
		<table width="200"  border="0" cellspacing="0" cellpadding="0" align="center">
		  <tr valign="top">
		    <td width="200"><tiles:insertAttribute name="body" /></td>
		  </tr>
		</table>

	</body> 
</html>