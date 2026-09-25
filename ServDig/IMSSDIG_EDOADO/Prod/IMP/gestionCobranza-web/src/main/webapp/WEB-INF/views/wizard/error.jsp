<%@ include file="../general/taglibs.jsp" %>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/wizard/error.js" htmlEscape="true" />"></script>
<c:set var="contextPath" value="<%=request.getContextPath()%>" />
<div class="contenedor col-sm-12">
	<div class="contenido row">
			<div class="col-sm-12">
				<div class="alert alert-danger">
					<button type="button" class="close" data-dismiss="alert">×</button>
					<span>${error}</span>
				</div>
			</div>
	</div>
	<br>
	<div class="pie row">
		<div class="opciones col-sm-6">
		</div>
		<div class="controles col-sm-6">
			<div class="pull-right">
			<button class="btn btn-default" id="cerrarWizard">CERRAR</button>
			</div>
		</div>
	</div>
</div>
