<%@ include file="../general/taglibs.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/resources/js/componenteBusquedaRP/componenteBusquedaRP.js" htmlEscape="true" />"></script>

<script type="text/javascript">
<!--
console.log("Entro al script")
//-->

$(function() {
	$("#divComponenteBusquedaaRP").busquedaRps({
		validarFusion : true,
		idSujetoObligado: 6906672,
		patronesNoElegibles: ['Y6241311']
	});
});
</script>
<div class="row">
	<div class="col-sm-12">
		<h4>Prueba de componente de busqueda de patrones</h4>
		<div class="alert alert-info">Prueba del componente de busqueda de RPS</div>
		
		<div id="divComponenteBusquedaaRP"></div>
	</div>
</div>