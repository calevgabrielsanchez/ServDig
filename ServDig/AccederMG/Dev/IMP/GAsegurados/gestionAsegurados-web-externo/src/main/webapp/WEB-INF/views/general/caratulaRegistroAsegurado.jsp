<%@ include file="../general/taglibs.jsp"%>

<div>
	<c:set var="contextpath" value="<%=request.getContextPath()%>" />

	<h2 class="title-clasificacion">
		<spring:message code="registro.asegurado.titulo" />
	</h2>
	
	<span>
		<spring:message code="registro.asegurado.descripcion" />
	</span>

	<form action="${contextpath}/tramite/iniciar" id="formIniciar"
		style="display: none;"></form>
</div>