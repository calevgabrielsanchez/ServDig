<%@ include file="../../general/taglibs.jsp"%>    


<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/moduleCuentaIndividual.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/controller.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/service.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/inicioCuentaIndividual.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/BandejaCuentaIndividualUI.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/BandejaCuentaIndividualTestUI.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/BandejaCuentaIlogicaUI.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/CancelarController.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/ConsultaSolicitudController.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/SolicitarInformacionController.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/BandejaCuentaIndividualResumenUI.js'/>"></script>

<form name="aux" method="POST" ></form>

<script type="text/javascript">

	var folioTramite = "${folioTramite}";
	
	var paramsCuentaIndividual = [];
	
	var paramsCuentaIlogica = [];
	
	$(document).ready(function() {
		module = new ResponsableModule( 'module', 'workingArea' );
        module.metadata.ui.components[0].components[3].current=1;
        module.controller.init();            
	});
	
</script>