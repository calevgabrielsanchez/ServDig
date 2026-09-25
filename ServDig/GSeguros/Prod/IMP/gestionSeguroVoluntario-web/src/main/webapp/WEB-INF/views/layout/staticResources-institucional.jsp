<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<%--
    ============================================================================
    1. LÓGICA DINÁMICA DE RUTAS (POM / WEB.XML)
    ============================================================================
--%>
<c:set var="envPath" value='<%=request.getSession().getServletContext().getInitParameter("STATIC_RESOURCES_PATH")%>' />
 
<c:choose>
    <c:when test="${not empty envPath and envPath ne 'null'}">
        <%-- EN PRODUCCIÓN: Usamos la ruta configurada en el POM --%>
        <c:set var="dynamicPath" value="${envPath}" />
    </c:when>
    <c:otherwise>
        <%-- EN LOCAL: Fallback a ruta relativa estándar --%>
        <c:set var="dynamicPath" value="${pageContext.request.contextPath}/static/resources" />
    </c:otherwise>
</c:choose>
 
<%-- Exportamos la variable para uso global --%>
<c:set var="staticResourcesPath" value="${dynamicPath}" scope="request" />
<script>var staticResourcesPath = '${staticResourcesPath}';</script>
 
<link rel="icon" href="${staticResourcesPath}/iconos/favicon.ico" />
 
 
<%--
    ============================================================================
    2. CARGA DE ESTILOS (ORDEN CRÍTICO: TUS ESTILOS -> LUEGO GOBMX)
    ============================================================================
--%>
 
<link type="text/css" href="${staticResourcesPath}/estilos/bootstrap/DT_bootstrap.css" rel="stylesheet" />
<link type="text/css" href="${staticResourcesPath}/estilos/jquery/ui-lightness/jquery-ui.css" rel="stylesheet" />
<link type="text/css" href="${staticResourcesPath}/estilos/font-awesome/css/font-awesome.css" rel="stylesheet" />
<link type="text/css" href="${staticResourcesPath}/estilos/imss/portal.css" rel="stylesheet" />
 
<link type="text/css" href="${staticResourcesPath}/estilos/jquery/jquery.jgrowl.css" rel="stylesheet" />
<link type="text/css" href="${staticResourcesPath}/estilos/jquery/jquery.jqplot.min.css" rel="stylesheet" />
<link type="text/css" href="${staticResourcesPath}/estilos/jquery/morris.css" rel="stylesheet" />
<link href="https://framework-gb.cdn.gob.mx/assets/styles/main.css" rel="stylesheet">
 
 
<%--
    ============================================================================
    3. CARGA DE SCRIPTS LEGACY (FUNCIONALIDAD)
    ============================================================================
--%>
<script type="text/javascript" src="${staticResourcesPath}/js/jquery/jquery.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/jquery/dtable/jquery.dataTables.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/jquery/dtable/jquery.dataTables.pagination.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/jquery/dtable/jquery.dataTables.sort.date.plugin.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/jquery/jquery-post-json.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/jquery/form2Object/form2object.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/jquery/form2Object/jquery.toObject.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/jquery/jquery.alphanum.js"></script>
 
<script type="text/javascript" src="${staticResourcesPath}/js/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/bootstrap/DT_bootstrap.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/bootstrap/bootstrap-tooltip.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/bootstrap/bootstrap-popover.js"></script>
 
<script type="text/javascript" src="${staticResourcesPath}/js/jquery/jquery-ui.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/jquery/jquery.ui.datepicker-es.js"></script>
 
<script type="text/javascript" src="${staticResourcesPath}/js/json/json2.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/json/json.min.js"></script>
 
<script type="text/javascript" src="${staticResourcesPath}/js/org/cometd.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/org/cometd/AckExtension.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/org/cometd/ReloadExtension.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/org/cometd/TimeStampExtension.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/org/cometd/TimeSyncExtension.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/jquery/comet/jquery.cookie.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/jquery/comet/jquery.cometd.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/jquery/comet/jquery.cometd-ack.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/jquery/comet/jquery.cometd-reload.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/jquery/comet/jquery.cometd-timestamp.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/jquery/comet/jquery.cometd-timesync.js"></script>
 
<script type="text/javascript" src="${staticResourcesPath}/js/jquery/comet/jquery.jgrowl.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/jqplot/jquery.jqplot.min.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/jqplot/jqplot.pieRenderer.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/morris/morris.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/morris/raphael.min.js"></script>
 
<script type="text/javascript" src="${staticResourcesPath}/js/delta/general.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/delta/CometConector.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/delta/gestionCtrlSelect.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/delta/procesaErrores.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/delta/limpiaFormularios.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/delta/html5.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/jquery/blockUI/jquery.blockUI.js"></script>
 
<script src="https://framework-gb.cdn.gob.mx/assets/scripts/main.js"></script>
 
 
<%--
    ============================================================================
    4. CORRECCIONES VISUALES (LAYOUT + COLOR)
    ============================================================================
--%>
<style>
    /* A. ARREGLO DEL FOOTER FLOTANTE / ESPACIO BLANCO */
    html {
        height: 100%;
    }
    
    body {
        min-height: 100vh; /* Fuerza al body a medir AL MENOS el 100% de la ventana */
        display: flex;
        flex-direction: column;
        margin: 0;
        background-color: #f5f5f5;
    }
 
    /* El contenido principal debe crecer para empujar el footer */
    main.page-flex, .main_wrap_shadow, .site_position_center {
        flex: 1 0 auto;
        width: 100%;
        display: flex;
        flex-direction: column;
    }
    
    /* El footer solo ocupa su espacio, no crece */
    #footerWrapper {
        flex-shrink: 0;
        width: 100%;
        margin-top: auto; /* Seguridad extra para empujar al fondo */
    }
 
    /* IMPORTANTE: Mata el "buffer" que inyecta GobMx y causa el espacio blanco */
    .bottom-buffer-footer {
        display: none !important;
        height: 0 !important;
        padding: 0 !important;
        margin: 0 !important;
    }
 
    /* B. ARREGLO DE COLOR (BOTÓN GUINDA) */
    .mboton, .btn-primary {
        background-color: #9d2449 !important;
        border-color: #821d3c !important;
        color: white !important;
        background-image: none !important;
        text-shadow: none !important;
    }
    .mboton:hover, .btn-primary:hover {
        background-color: #821d3c !important;
    }
    
    nav.navbar { margin-bottom: 0 !important; border-radius: 0 !important; }
</style>
 
<script>
    // Variables AJAX usando la misma ruta dinámica
    var AJAX_BASE_PATH = '${staticResourcesPath}';
    console.log("Sistema Institucional Dinámico. Resources: " + AJAX_BASE_PATH);
</script>