<%@ include file="../general/taglibs.jsp" %>

<script type="text/javascript">
	var objCtrl = parent.FirmaDigitalCtrl;

	$(document).ready(function() {
	
		$('#rfc').val(objCtrl.datosEntrada.rfc);
		$('#registroPatronal').val(objCtrl.datosEntrada.nrp);
		$('#cadenaOriginal').val(objCtrl.datosEntrada.contenido);
		$('#firmarArchivo').val(objCtrl.datosEntrada.firmarArchivo);
		
		$('#forma').submit();
	});
</script>

<div class="container"
	style="margin: 0px !important; width: 100% !important;">
	<div class="hero-unit">
		<div class="form-comment" style="padding-right: 20px;">

			<c:set var="contextpath" value="<%=request.getContextPath()%>" />

			<div id="loadingDiv" style="text-align: center;">
				<img alt="Cargando..."
					src="<spring:url value="/static/resources/imagenes/loading.gif" htmlEscape="true" />">
			</div>

			<form:form modelAttribute="firmaElectronica" id="forma" method="post"
				action="${contextpath}/firma-digital/seleccionar-tipo-firma">

				<span id="errorNegocioLabel" class="error hiddenElement"></span>

				<form:hidden path="rfc" id="rfc" />
				<form:hidden path="registroPatronal" id="registroPatronal" />
				<form:hidden path="cadenaOriginal" id="cadenaOriginal" />
				<form:hidden path="firmarArchivo" id="firmarArchivo" />

			</form:form>

		</div>
	</div>
</div>