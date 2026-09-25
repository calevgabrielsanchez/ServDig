<%@ include file="../general/taglibs.jsp"%> 
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/responsable/ResumenCorreccionComponent.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/responsable/ConsultaSolicitudComponent.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/responsable/SolicitarInformacionUI.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/responsable/CancelarUI.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/responsable/ConfirmarUI.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/responsable/CorreccionDatosUI.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/responsable/ConsultaSolicitudUI.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/responsable/BandejaSolicitudesUI.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/responsable/ResponsableUI.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/responsable/CancelarController.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/responsable/ConsultaSolicitudController.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/responsable/SolicitarInformacionController.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/responsable/controller.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/responsable/service.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/responsable/module.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/responsable/BitacoraUI.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/responsable/NavegacionPersonaNSSComponent.js'/>"></script>
<form name="aux" method="POST" ><input type="hidden" name="cdainfo" value="TramiteCDA" /></form>
<script>
	$('form').each(function(){ 
		$("#"+this.id).append('<input type="hidden" name="cdainfo" value="TramiteCDA" />');
	});
</script>