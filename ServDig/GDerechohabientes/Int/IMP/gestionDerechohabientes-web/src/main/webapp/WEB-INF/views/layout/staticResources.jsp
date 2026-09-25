<%@ include file="../general/taglibs.jsp"%>

<link rel="icon" href="${staticResourcesPath}/iconos/favicon.ico" />

<!-- Estilos -->
<link rel="stylesheet" type="text/css" href="<spring:url value="/static/resources/estilos/imss/reset.css" htmlEscape="true" />" />
<link rel="stylesheet" type="text/css" href="<spring:url value="/static/resources/estilos/imss/style.css" htmlEscape="true" />" />
<link type="text/css" href="<spring:url value="/static/resources/estilos/jquery/ui-lightness/jquery-ui.css" htmlEscape="true" />" rel="stylesheet" />
<link type="text/css" href="<spring:url value="/static/resources/estilos/jquery/demo_page.css" htmlEscape="true" />" rel="stylesheet" />
<link type="text/css" href="<spring:url value="/static/resources/estilos/jquery/demo_table.css" htmlEscape="true" />" rel="stylesheet" />

<!-- Javascripts -->

<script>
	var context_path = '<%=request.getContextPath()%>';
	var sessionId = '<%= request.getSession().getId()%>';
</script>

<!-- JQUERY -->
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/jquery.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/jquery-post-json.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/jquery-ui.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/jquery.ui.datepicker-es.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/date.format.js" htmlEscape="true" />"></script>

<!-- PLUGINS DE JQUERY -->
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/attrchange.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/textarea/jquery.maxlength.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/jquery.alphanum.js" htmlEscape="true" />"></script>

<!-- Datatables -->
<script type="text/javascript" src=" <spring:url value="/static/resources/js/jquery/dtable/jquery.dataTables_1.9.2.js" htmlEscape="true" />"></script> 
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/dtable/jquery.dataTables.pagination.js" htmlEscape="true" />"></script>

<!-- JSON -->
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/form2Object/form2object.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/form2Object/jquery.toObject.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/json/json2.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/json/json.min.js" htmlEscape="true" />"></script>

<!-- COmbos -->
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/gestionCtrlSelect.js" htmlEscape="true" />"></script>

<!-- BlockUI -->
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/blockUI/jquery.blockUI.js" htmlEscape="true" />"></script>

<!-- JS General de la aplicacion  -->
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/limpiaFormularios.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/procesaErrores.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/general.js" htmlEscape="true" />"></script>
