<%@ include file="../general/taglibs.jsp"%>
<%@taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<c:set var="scheme" value="<%=request.getScheme()%>" />
<c:set var="server" value="<%=request.getServerName()%>" />
<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var= "recurso" value="${scheme}://${server}"/>
<c:set var="urlNSSDocumento" value="${contextpath}/wizard/correccionDatosAsegurado/documentosProbatorios/validar/datosNSSDocumentos"/>
<c:set var="paginaAnterior" value="${contextpath}/wizard/correccionDatosAsegurado/datosHistoriaLaboral"></c:set>

<style type="text/css">

.image-upload > input {
    display: none;
}

.image-upload img {
    overflow: hidden;
	cursor: pointer;
	vertical-align: middle;
}

label img{
    pointer-events: none;
}

.icono-help {
    color: black;
    font-family: FontAwesome;
    font-size: 27px;
    padding: 0 10px;
    text-decoration: none;
}

.icono-help::before {
    content: "\f059";
}

.popover{
        max-width:500px;
}

#registroNSS .tooltip .tooltip-inner { width: 350px; }

</style>

<script type="text/javascript">
	var contextPath="${contextpath}";
	var claveDocumentoNSS = "${documentoNSSClave}";
	
	$(document).ready(function() {
	
	var infoDocProbatorio = '<div style="font-size:11px"><p >Adjunte los documentos probatorios escaneados necesarios para el tr&aacute;mite.</p>';
        infoDocProbatorio += '<ol>';
        infoDocProbatorio += '<li>Documento expedido por el IMSS que contenga el N&uacute;mero de Seguridad Social, uno por cada n&uacute;mero involucrado:';
        infoDocProbatorio +=  '<ol type="a">';
        infoDocProbatorio +=  '<li>Avisos Afiliatorios (Forma 2-A, AFIL-02, AFIL-03, AFIL-04, IDSE-03, AFIL-06 y DST-002).</li>';
        infoDocProbatorio +=  '<li>Tarjeta de afiliaci&oacute;n</li>';
        infoDocProbatorio +=  '<li>Certificado de incapacidad</li>';
        infoDocProbatorio +=  '<li>Cartilla de citas m&eacute;dicas</li>';
        infoDocProbatorio +=  '<li>Credencial ADIMSS</li>';
        infoDocProbatorio +=  '<li>Liquidaciones pagadas</li>';
        infoDocProbatorio +=  '<li>Comprobantes de pago</li>';
        infoDocProbatorio +=  '<li>Carta de renuncia o finiquito</li>';
        infoDocProbatorio +=  '<li>Comprobantes SAR-03, SAR-04 o estado de cuenta de la AFORE</li>';
        infoDocProbatorio +=  '<li>Otros</li>';
        infoDocProbatorio +=  '</ol>'; 
        infoDocProbatorio += '</li>';
        infoDocProbatorio += '</ol>';
	infoDocProbatorio +='</div>';
	
	$('#ayudaDocProbatorio').popover({
		animation : true,
		html : true,
		title : 'Ayuda',
		content : infoDocProbatorio,
		trigger : 'hover',
		placement : 'top',
		container : 'body'
	});
	
	$('[data-toggle="tooltip"]').tooltip();
	
});
</script>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/common/tablesCommon.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/solicitud/documentosNssInvolucrado.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/common/procesaErrores.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/common/correccionDatosCurpFileUpload.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/jquery.dateSelectBoxes.js" htmlEscape="true" />"></script>



<div class="contenedor">
	<jsp:include page="encabezado.jsp">
		<jsp:param name="paso" value="3" />
	</jsp:include>
	<form:form method="post" modelAttribute="datosHistoriaLaboral"
		id="datosNSSDocumentoForm" action="${urlNSSDocumento}" accept-charset="ISO-8859-1">
                    <%@ include file="datosHistoriaLaboral/documentosNss.jsp"%>
		<!-- Controles -->
	</form:form>
		<div class="row col-md-12">
			
			<div class="pull-right" >
				<button type="button" id="continuarDatosHistoriaLaboral"
					class="btn btn-primary">
					<spring:message code="label.continuar" />
				</button>			
			</div>
			<div class="pull-right" style="margin-right:10px;">
				<%@ include file="regresar.jsp"%>	
			</div>
			<div class="pull-right" style="margin-right:10px;">
				<jsp:include page="cancelarSolicitud.jsp"/>
			</div>
	   </div>
</div>
