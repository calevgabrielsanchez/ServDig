<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
   
<head>
<?xml version="1.0" encoding="ISO-8859-1" ?>
<meta http-equiv="X-UA-Compatible" content="IE=8" >
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="expires" content="-1" >
<title>Clasificador de Empresas</title>




<link rel="icon" href="<spring:url value="/resources/images/favicon.ico" htmlEscape="true" />">
<link rel="stylesheet" type="text/css"
	href="<spring:url value="/resources/estilos/estilo.css" htmlEscape="true" />">
<link type="text/css"
	href="<spring:url value="/resources/css/ui-lightness/jquery-ui-1.8.14.custom.css" htmlEscape="true" />"
	rel="stylesheet" />

<style type="text/css" media="screen">
@import "<spring:url value="/resources/css/demo_table.css" htmlEscape="true" />";

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

<script type="text/javascript"
	src="<spring:url value="/resources/js/json/json2.js" htmlEscape="true" />"></script>
	
<script type="text/javascript"
	src="<spring:url value="/resources/js/jquery/jquery-1.5.1.min.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/resources/js/jquery/jquery-post-json.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/resources/js/jquery/jquery-ui-1.8.14.custom.min.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/resources/js/jquery/dtable/jquery.dataTables.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/resources/js/jquery/dtable/jquery.dataTables.pagination.js" htmlEscape="true" />"></script>


<script type="text/javascript"
	src="<spring:url value="/resources/js/jquery/text-highlight/jquery.highlight-3.js" htmlEscape="true" />"></script>

<script type="text/javascript"
	src="<spring:url value="/resources/js/clasificador/general.js" htmlEscape="true" />"></script>


<script type="text/javascript"
	src="<spring:url value="/resources/js/clasificador/catalogos.js" htmlEscape="true" />"></script>

<script type="text/javascript"
	src="<spring:url value="/resources/js/clasificador/anterior.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/clasificador/actual.js" htmlEscape="true" />"></script>

<script type="text/javascript" src="<spring:url value="/resources/js/clasificador/enAnterior.js" htmlEscape="true" />"></script>
	
<script type="text/javascript"
	src="<spring:url value="/resources/js/clasificador/divisionCtrl.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/resources/js/clasificador/grupoCtrl.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/resources/js/clasificador/fraccionCtrl.js" htmlEscape="true" />"></script>	
	
	
	
<script>
	var context_path = '<%= request.getContextPath()%>';
	
</script>

</head>