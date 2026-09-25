<%@ include file="/WEB-INF/views/layout/taglibs.jsp"%>

<script type="text/javascript">
	$(function(){
		$('div.main_wrap').removeClass('main_wrap');
		$('div.site_position_center').removeClass('site_position_center');
	});
</script>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/personas/fisica/identificar/cambios-automaticos/ingresar-datos.js" htmlEscape="true" />"></script>

<div class="container" style="width: 100%;">
	<div class="form-comment" style="padding-right: 20px;">

		<c:set var="contextpath" value="<%=request.getContextPath()%>" />

		<div id="loadingDiv" style="text-align: center;">
			<img alt="Cargando..."
				src="<spring:url value="/static/resources/imagenes/loading.gif" htmlEscape="true" />">
		</div>

		<form:form modelAttribute="icaDatosConsulta" id="forma" method="post"
			action="${contextpath}/persona/fisica/identificar/cambios-automaticos/consultar-comparar"
			cssClass="formNotBlock">

			<span id="errorNegocioLabel" class="error hiddenElement"></span>

			<form:hidden path="indicadorMostrarPantalla"
				id="indicadorMostrarPantalla" />
			<form:hidden path="isUsuarioExterno" id="isUsuarioExterno" />


			<form:hidden path="personaFisica.idPersona" id="busquedaIdPersona" />
			<form:hidden path="personaFisica.curp" id="busquedaCurp" />
			<form:hidden path="personaFisica.rfc" id="busquedaRfc" />

			<form:hidden path="personaFisica.nombre" id="busquedaNombre" />

			<form:hidden path="personaFisica.primerApellido"
				id="busquedaPrimerApellido" />

			<form:hidden path="personaFisica.segundoApellido"
				id="busquedaSegundoApellido" />
			<form:hidden path="indicadorConsultaRENAPO" id="busquedaIndRENAPO" />
			<form:hidden path="indicadorConsultaSAT" id="busquedaIndSAT" />
			
			<div id="btnAceptarDiv" style="display: none;">
				<br/> <br/>
				<button type="button" class="btn btn-secondary" id="btnAceptar">Aceptar</button>
			</div>
		</form:form>

	</div>
</div>