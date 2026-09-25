<%@ include file="../../general/taglibs.jsp" %>
<script type="text/javascript"
	src="<spring:url value="/resources/js/delta/wizard/comprobanteVigencia/error.js" htmlEscape="true" />"></script>
<c:set var="contextPath" value="<%=request.getContextPath()%>" />
<div class="contenedor">

	<div class="contenido" style="width: 100%;">
			<div class="alert alert-danger">
					<button type="button" class="close" data-dismiss="alert">�</button>
					${error}
			</div>
	</div>
	<br>
	<div class="pie">
		<div class="opciones">
			<button class="btn btn-default" id="cerrarWizardVigencia">CERRAR</button>
		</div>
		<div class="controles"></div>

	</div>
</div>
