<script>
	var contextPath = "<%=request.getContextPath()%>";
</script>
<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>
<meta http-equiv="expires" content="-1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/solicitud.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/solicitud/solicitudesAtendidas.js" htmlEscape="true" />"></script>

<div class="form-comment">
<form>
<table style="width: 100%" id="solicitudesAtendidasTable">
	<caption><spring:message code="titulo.solicitudAtendidas" /></caption>
</table>
</form>
</div>