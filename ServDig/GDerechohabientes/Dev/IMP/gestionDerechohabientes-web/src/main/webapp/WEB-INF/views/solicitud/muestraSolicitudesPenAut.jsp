<script>
	var contextPath = "<%=request.getContextPath()%>";
</script>
<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>
<meta http-equiv="expires" content="-1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<jsp:include page="../general/llenaTipoTramite.jsp"></jsp:include>

<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/solicitud.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/solicitud/solicitudesPenAut/solicitudesPenAut.js" htmlEscape="true" />"></script>

<div class="form-comment">
<form>
<table style="width: 100%" id="solicitudesPenAutTable">
	<caption><spring:message code="titulo.solicitudPenAut" /></caption>
</table>
</form>
</div>