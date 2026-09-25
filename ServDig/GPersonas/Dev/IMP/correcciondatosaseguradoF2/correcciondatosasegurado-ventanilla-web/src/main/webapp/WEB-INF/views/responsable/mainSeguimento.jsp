<%@ include file="../general/taglibs.jsp"%>    
<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/cuentaIndividual/CuentaIndividualAseguradoComponent.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/cuentaIndividual/CuentaIndividualComponent.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/cuentaIndividual/CuentaIndividualEditComponent.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/cuentaIndividual/CuentaIndividualNssComponent.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/cuentaIndividual/PeriodoCuentaIndividualEditComponent.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/cuentaIndividual/PeriodoCuentaIndividualNssComponent.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/cuentaIndividual/PeriodoCuentaIndividualModel.js'/>"></script>

<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/confirmacionCuentaIndividual/CuentaIndividualConfirmarComponent.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/confirmacionCuentaIndividual/PeriodoCuentaIndividualConfirmarComponent.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/confirmacionCuentaIndividual/CuentaIndividualConfirmarMainComponent.js'/>"></script>

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
<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/AgregarNSSDocumentoUI.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/AgregarNSSDocumentoController.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/consultaPrevia/consultaPreviaUI.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/consultaPrevia/consultaPreviaDetalleUI.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/consultaPrevia/consultaAtencionResponsableUI.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/consultaPrevia/consultaPreviaController.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/consultaPrevia/consultaDetalleAtencionResponsableUI.js'/>"></script>

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




		