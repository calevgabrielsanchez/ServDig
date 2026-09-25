<%@ include file="../general/taglibs.jsp"%>

<div>
	<c:set var="contextpath" value="<%=request.getContextPath()%>" />

	<h2 class="title-clasificacion">
		<spring:message code="registro.asegurado.titulo" />
	</h2>
	
	<span>
		<spring:message code="registro.asegurado.cartilla" />
	</span>

	<form action="${contextpath}/consultaCartilla/iniciar" id="formIniciar"
		style="display: none;"></form>
</div>