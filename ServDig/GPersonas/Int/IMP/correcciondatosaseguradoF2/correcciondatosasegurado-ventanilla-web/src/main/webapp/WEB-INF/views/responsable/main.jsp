<%@ include file="../general/taglibs.jsp"%> 

<link rel="icon" href="${staticResourcesPath}/iconos/favicon.ico" />

<!-- GobMx -->
<link href="https://framework-gb.cdn.gob.mx/assets/styles/main.css" rel="stylesheet">

<!-- Bootstrap -->
<link type="text/css" href="${staticResourcesPath}/estilos/bootstrap/DT_bootstrap.css" rel="stylesheet" />

<!-- Jquery-->
<link type="text/css" href="${staticResourcesPath}/estilos/jquery/ui-lightness/jquery-ui.css" rel="stylesheet" />

<!-- Fonts -->
<link type="text/css" href="${staticResourcesPath}/estilos/font-awesome/css/font-awesome.css" rel="stylesheet" />


<!-- IMSS -->
<link type="text/css" href="${staticResourcesPath}/estilos/imss/portal.css" rel="stylesheet" />
<!-- estilo para que los cuadros de dialogo se vean en negro -->
<link type="text/css" href="${staticResourcesPath}/estilos/imss/fixGobMx.css" rel="stylesheet" />

<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/consultaSolicitud/ConsultaSolicitudModel.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/responsable/consultaSolicitud/ConsultaSolicitudButtonGroupComponent.js'/>"></script>
 

<script type="text/javascript" src="<spring:url value='/static/resources/js/common/confirmarCorreccionDatos/ConfirmarCorreccionDatosAsociadoComponent.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/common/confirmarCorreccionDatos/ConfirmarCorreccionDatosComponent.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/common/confirmarCorreccionDatos/ConfirmarCorreccionDatosEntryComponent.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/common/confirmarCorreccionDatos/ConfirmarCorreccionDatosNoCorrespondeComponent.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/common/confirmarCorreccionDatos/ConfirmarCorreccionDatosService.js'/>"></script>

<script type="text/javascript" src="<spring:url value='/static/resources/js/common/correccionDatos/CorreccionDatosComponent.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/common/correccionDatos/CorreccionDatosModel.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/common/correccionDatos/CorreccionDatosService.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/common/correccionDatos/EditCorreccionDatosComponent.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/common/correccionDatos/EditTipoRegularizacionComponent.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/common/correccionDatos/FormaCorreccionDatosComponent.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/common/nssDocumentos/NssDocumentsEntryComponent.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/common/nssDocumentos/NssDocumentsComponent.js'/>"></script>

<script type="text/javascript" src="<spring:url value='/static/resources/js/common/NssPagerComponent.js'/>"></script>

<script type="text/javascript" src="<spring:url value='/static/resources/js/common/cuentaIndividual/CuentaIndividualService.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/common/cuentaIndividual/CuentaIndividualNssComponent.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/common/cuentaIndividual/CuentaIndividualComponent.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/common/cuentaIndividual/RegistroPatronalComponent.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/common/cuentaIndividual/PeriodoCuentaIndividualNssComponent.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/common/cuentaIndividual/PeriodoCuentaIndividualModel.js'/>"></script>

<%--
<script type="text/javascript" src="<spring:url value='/static/resources/js/common/cuentaIndividual/CuentaIndividualService.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/common/cuentaIndividual/CuentaIndividualAseguradoComponent.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/common/cuentaIndividual/CuentaIndividualComponent.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/common/cuentaIndividual/CuentaIndividualEditComponent.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/common/cuentaIndividual/CuentaIndividualNssComponent.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/common/cuentaIndividual/PeriodoCuentaIndividualEditComponent.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/common/cuentaIndividual/PeriodoCuentaIndividualNssComponent.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/common/cuentaIndividual/PeriodoCuentaIndividualModel.js'/>"></script>
--%>

<script type="text/javascript" src="<spring:url value='/static/resources/js/common/consultaCuentaIndividual/CuentaIndividualConsultaService.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/common/consultaCuentaIndividual/CuentaIndividualConsultaAseguradoComponent.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/common/consultaCuentaIndividual/CuentaIndividualConsultaComponent.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/common/consultaCuentaIndividual/CuentaIndividualConsultaNssComponent.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/common/consultaCuentaIndividual/PeriodoCuentaIndividualConsultaComponent.js'/>"></script>

<script type="text/javascript" src="<spring:url value='/static/resources/js/common/confrontaCuentaIndividual/ConfrontaCuentaIndividualAseguradoComponent.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/common/confrontaCuentaIndividual/ConfrontaCuentaIndividualComponent.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/common/confrontaCuentaIndividual/ConfrontaCuentaIndividualNssComponent.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/common/confrontaCuentaIndividual/ConfrontaPeriodoCuentaIndividualComponent.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/common/confrontaCuentaIndividual/ConfrontaCuentaIndividualService.js'/>"></script>

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
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/responsable/AgregarNSSDocumentoUI.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/responsable/AgregarNSSDocumentoController.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/responsable/NavegacionPersonaNSSComponent.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/consultaPrevia/consultaPreviaUI.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/consultaPrevia/consultaPreviaDetalleUI.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/consultaPrevia/consultaAtencionResponsableUI.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/consultaPrevia/consultaPreviaController.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/consultaPrevia/consultaDetalleAtencionResponsableUI.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/responsable/BandejaCuentaIlogicaUI.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/responsable/BandejaCuentaIndividualResumenUI.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/responsable/BandejaCuentaIndividualUI.js'/>"></script>
<script type="text/javascript" defer src="<spring:url value='/static/resources/js/responsable/ProcesandoUI.js'/>"></script>

<script type="text/javascript" defer src="<spring:url value='/static/resources/js/delta/common/common.js'/>"></script>

<form name="aux" method="POST" ><input type="hidden" name="cdainfo" value="TramiteCDA" /></form>
<script>
var folioTramite = "${param.folio}";
	$('form').each(function(){ 
		$("#"+this.id).append('<input type="hidden" name="cdainfo" value="TramiteCDA" />');
	});
</script>