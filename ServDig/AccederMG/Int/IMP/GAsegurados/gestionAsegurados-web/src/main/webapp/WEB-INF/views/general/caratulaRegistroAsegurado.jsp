<%@ include file="../general/taglibs.jsp"%>

<div class="page_holder_no_height" style="width: 100% !important; margin: 0px;">
	<div class="post_entry_wide no-border" style="width:100% !important;">
		<div class="form-comment">

			<c:set var="contextpath" value="<%=request.getContextPath()%>" />

			<h2>
				<spring:message code="registro.asegurado.titulo" />
			</h2>
			<p>
				<spring:message code="registro.asegurado.descripcion" />
			</p>

		</div>
	</div>

	<c:set var="contextpath" value="<%=request.getContextPath()    %>" />
	<form action="${contextpath}/tramite/iniciar" id="formIniciar"
		style="display: none;"></form>

</div>
