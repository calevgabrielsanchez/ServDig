<%@ include file="../general/taglibs.jsp"%>
<%-- --%>
<%-- <script type="text/javascript" defer src="<spring:url value='/static/resources/js/autorizador/ResumenCorreccionComponent.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/autorizador/ConsultaSolicitudComponent.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/autorizador/SolicitarInformacionUI.js'/>"></script>  
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/autorizador/RechazarUI.js'/>"></script> 
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/autorizador/ReasignacionUI.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/autorizador/CambiosAutorizarUI.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/autorizador/ConsultaSolicitudUI.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/autorizador/ConsultaSolicitudReasignarUI.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/autorizador/BandejaSolicitudesUI.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/autorizador/AutorizadorUI.js'/>"></script>  
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/autorizador/CancelarUI.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/autorizador/CambiosAutorizarController.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/autorizador/SolicitarInformacionController.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/autorizador/ReasignacionController.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/autorizador/ConsultaSolicitudController.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/autorizador/RechazarController.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/autorizador/CancelarController.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/autorizador/CorreccionDatosUI.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/autorizador/BitacoraUI.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/autorizador/NavegacionPersonaNSSComponent.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/autorizador/ResumenCorreccionComponent.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/autorizador/ConsultaSolicitudComponent.js'/>"></script> 
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/autorizador/SolicitarInformacionUI.js'/>"></script>
--%>

<%-- necesarios --%>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/reportes/ReporteMainUI.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/reportes/ReporteGridSolicitudesUI.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/reportes/ReporteGridEstadisticaUI.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/reportes/controller.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/reportes/service.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/reportes/module.js'/>"></script>


<form name="aux" method="POST" ><input type="hidden" name="cdainfo" value="TramiteCDA" /></form>
<script>
	$('form').each(function(){ 
		$("#"+this.id).append('<input type="hidden" name="cdainfo" value="TramiteCDA" />');
	});
</script>