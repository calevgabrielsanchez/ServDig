<%@ include file="../general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum"%>

<script type="text/javascript" src="${staticResourcesPath}/js/widget/widget.js"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/portlet/portlet.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/home.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="/portalDerechohabiente-ventanilla/static/resources/js/delta/wizard/comprobanteVigencia/ComprobanteVigenciaWizard.js"></script>


<c:set var="tipoPersonaFisica"><%=TipoPersonaEnum.FISICA.getId()%></c:set>

<script type="text/javascript">
	
	var tiempoEspera = '${tiempoEspera}';	
	var tiempoIntervaloEspera = '${tiempoIntervaloEspera}'; 
	
	var idAsignacionNss = ${idAsignacionNss};
	var idPersonaAsegurado = ${idPersona};
	var nssAsegurado = '${nss}';
	var usuarioConsulta = '${usuarioConsulta}';

    $(document).ready(function () {
        $.blockUI();
        setTimeout(function () {
            peticionReporte(false);
        }, 25000); //Se espera 25segs para mandar la peticion del reporte
        $("#imprimirReporte").click(function () {
            peticionReporte(true);
            $.ui.dialog.maxZ = 10000;
        });
    });

	function peticionReporte(mostrarReporte) {
		let asignacion_nss = {
			'idAsignacion' : idAsignacionNss,
			'idPersona': idPersonaAsegurado,
			'nss': nssAsegurado,
			'usuario':usuarioConsulta,
			'noDestroy': true
		};
		console.log("Entra a imprimir el reporte!");
		WizardComprobanteVigenciaCtrl.init('divComprobanteVigencia',asignacion_nss);
		WizardComprobanteVigenciaCtrl.abrir(mostrarReporte);
		if (!mostrarReporte){
			setTimeout(function () {
			    //Para IExplorer
				$.unblockUI();
			}, 20000);
		}
	}
</script>


<div id="homecontenido" class="container">
	<c:set var="idPersonaPrincipal" value="${idPersona}" scope="session"/>
	<c:if test="${registrado eq 0}"><!--
	
	<div class = "row">
		<div class="alert alert-danger">
			El asegurado / pensionado con n&uacute;mero de seguridad social <strong>${nss}</strong> no se encuentra registrado
			a&uacute;n como derechohabiente, por lo tanto no podr&aacute; obtener su comprobante de vigencia de derechos.
		</div>
	</div>
	--></c:if>
	<div class="row">
		<div class="contenedor-widget col-xs-4">
			
				
			<div>
				<button class="btn btn-primary btn-lg btn-block" style="padding:15px" id="imprimirReporte">Reporte de Vigencia</button>
				<br><br>
			</div>
			<c:if test="${asignacion.estadoInconsistencia == 0}">
			<div class="widget" widget-url="/portalDerechohabiente-ventanilla/widget/vigencia/${idPersona}/0/1/${nss}"></div>
			</c:if>
			
			<!-- <div class="widget" widget-url="/portalDerechohabiente-web/widget/servicios/${idAsignacionNss}"></div> -->
		</div>
  
		<div class="contenedor-portlet col-xs-8">
			<div class="portlets">
			
				<div class="portlet" portlet-url="/portalDerechohabiente-ventanilla/portlet/detalle/integrante/${nss}/${idPersona}/${idAsignacionNss}/${asignacion.estadoInconsistencia}"></div>
				<%--se checa si el nss esta inconsistente, en caso de no estarlo se muestran los portlets --%>
				<c:if test="${asignacion.estadoInconsistencia == 0}">
					<div class="portlet" portlet-url="/portalDerechohabiente-ventanilla/portlet/grupoFamiliar/${idAsignacionNss}/0"></div>
				
					<div class="portlet" portlet-url="/portalDerechohabiente-ventanilla/portlet/datosPatron/${idAsignacionNss}/${nss}"></div>
				</c:if>
				
				
			</div>
		</div>
	</div>
</div>



<!-- Divs de soporte para abrir los dialogos de las aplicaciones utilitarias -->
<div id="divDetalleDerechohabiente"></div>
<div id="divComprobanteVigencia"></div>

<!-- Elementos de soporte para el control de los Identificadores generales -->
<input type="hidden" id="portalContext" value="${portalContext}" />


<div id="waitingDivCommon" style="display: none;">
	<div style="text-align: center; vertical-align: middle;">
		<img alt="" src="${staticResourcesPath}/imagenes/loading.gif" />
	</div>
</div>
