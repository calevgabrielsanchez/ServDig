<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>

<link rel="stylesheet" href="${staticResourcesPath}/estilos/imss/menu-grid.css" />

<script src="${staticResourcesPath}/js/delta/MenuCtrl.js"></script>
<script src="<spring:url value="/static/resources/js/delta/menu.js" htmlEscape="true" />"></script>

<div id="homecontenido">
	<div style="text-align: center">
		<h4>SELECCIONA UN TR&Aacute;MITE</h4>
	</div>

	<jsp:include page="opcionesMenuPrincipal.jsp" />

	<div id="menu-wrapper"></div>

	<form role="form" id="formTramite" method="post" action="${contextpath}/portal-ventanilla-web/portal/tramite"
		style="display: none;">
		<input id="tramite" type="hidden" value="" name="tramite" />
		<input id="filtro" type="hidden" value="" name="filtro" />
	</form>
</div>