<%@ include file="../general/taglibs.jsp"%>    
<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/ResumenCorreccionComponent.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/ConsultaSolicitudComponent.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/SolicitarInformacionUI.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/CancelarUI.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/ConfirmarUI.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/CorreccionDatosUI.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/ConsultaSolicitudUI.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/BandejaSolicitudesUI.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/ResponsableUI.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/CancelarController.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/ConsultaSolicitudController.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/SolicitarInformacionController.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/controller.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/service.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/SeguimientoSolicitud.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/BitacoraUI.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/NavegacionPersonaNSSComponent.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/moduleSeguimiento.js'/>"></script>
<form name="aux" method="POST" ></form>
 
<script type="text/javascript">

	var idTramite = "${idTramite}";
	var propietarioTarea = "${propietarioTarea}";
	var idTarea = "${idTarea}";
	
	$(document).ready(function() {
		module = new ResponsableModule( 'module', 'workingArea' );
        module.metadata.ui.components[0].components[3].current=1;
        module.controller.init();
        selectTramite(idTramite, propietarioTarea, idTarea);

	});
	
</script>




		