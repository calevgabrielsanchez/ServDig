<%@ include file="../general/taglibs.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="/${mvn.web.app.root}/resources/derechohabiente/js/cuestionario/js/cuestionario.js">
</script>
<script type="text/javascript" src="/${mvn.web.app.root}/resources/derechohabiente/js/cuestionario/js/corroborarCuestionario.js">
</script>
<script>
	$(document).ready(
			function() {

			$("#invocar").click(function() {
				getCuestionario(651) ;	
			})
			
			$("#pdf").click(function() {
				getPDF(651) ;	
			})
			
			$("#resultado").click(function() {
				getVistaCuestionario(1,1,490) ;	
			})
			
			
		})
 
	history.go(1);

	
</script>

<div class="form-comment">
	 
	<input type="button" id="invocar" value="<spring:message code="button.capturar"/>">
	<input type="button" id="pdf" value="<spring:message code="button.imprimir"/>" > 
	<input type="button" id="resultado" value="Corroborar resultado" > 
</div>


<div id="documento" >
	<!--  iframe style="width: 600px" id="contenedor"></iframe-->
</div>