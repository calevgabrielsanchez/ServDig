<%@ include file="../general/taglibs.jsp"%>
<%@taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<c:set var="scheme" value="<%=request.getScheme()%>" />
<c:set var="server" value="<%=request.getServerName()%>" />
<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var= "recurso" value="${scheme}://${server}"/>
<c:set var="urlDatosHistoriaLaboral" value="${contextpath}/wizard/correccionDatosAsegurado/documentosProbatorios/datosNSS"/>
<c:set var="paginaAnterior" value="${contextpath}/wizard/correccionDatosAsegurado/informacionHistoriaLaboral"></c:set>

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

.error{
	font-size: 18px;
}
</style>

<script type="text/javascript">
	var contextPath="${contextpath}";
	var claveDocumentoNSS = "${documentoNSSClave}";
	var tipoDocActas = "${documentosActa}";
	var tipoDocId = "${documentosIdentificacion}";
	var personaSolicitante= "${datosHistoriaLaboral.tipoSolicitante}";
	var personaBeneficiario = "${datosHistoriaLaboral.tipoBeneficiario}";
	var arregloTipoDocs = [];
	
	$(document).ready(function() {
		
		var infoDocProbatorio = '<div style="font-size:11px"><p >Adjunte los documentos probatorios escaneados necesarios para el tr&aacute;mite.</p>';
	    infoDocProbatorio += '<ol>';
	    infoDocProbatorio += '<li>Acta de nacimiento.</li>';
	    
	    infoDocProbatorio += '<li>Identificaci&oacute;n oficial:';
	    infoDocProbatorio +=  '<ol type="a">';
	    infoDocProbatorio +=  '<li>Credencial para votar vigente</li>';
	    infoDocProbatorio +=  '<li>Pasaporte vigente, mexicano o extranjero</li>';
	    infoDocProbatorio +=  '<li>Cartilla del servicio militar nacional</li>';
	    infoDocProbatorio +=  '<li>C&eacute;dula profesional</li>';
	    infoDocProbatorio +=  '<li>Matr&iacute;cula consular (documento de identidad que expide una oficina consular a favor de un connacional)</li>';
	    infoDocProbatorio +=  '<li>Tarjeta/c&eacute;dula/carnet de identidad para extranjeros (en caso de extranjeros)</li>';
	    infoDocProbatorio +=  '<li>Documento migratorio vigente que corresponda, emitido por autoridad competente (en su caso pr&oacute;rroga o refrendo migratorio)</li>';
	    infoDocProbatorio +=  '</ol>'; 
	    infoDocProbatorio += '</li>';
		
            var infoNSS='<div style="font-size:11px"><p>Debe ingresar el o los NSS que sean necesarios para la regularizaci&oacute;n de sus datos ante el IMSS.</p></div>';
		
            //PENDIENTE CONFIRMACION
            var infoAsegurado = '<div style="font-size:11px"><p >Adjunte los siguientes documentos:</p>';
            infoAsegurado += '<li>Asegurado</li>';
            infoAsegurado += '- Acta de nacimiento.<br>';
            infoAsegurado += '- Identificaci&oacute;n oficial.<br>';
            infoAsegurado += '- Documentos con NSS.<br>';
            infoAsegurado += '- Acta de defunci&oacute;n.(Aplica para los cuatro)<br>';
            infoAsegurado += '- Acta de matrimonio (Esposa/Esposo).<br>';
            infoAsegurado += '- Formato.<br>';
            infoAsegurado += '- Acta de nacimiento(Hijo/Hija).<br>';
            infoAsegurado += '- Constancia que acredite concubinato(Concubino/Concubina).<br>';
            infoAsegurado += '<li>Beneficiario</li>'
            infoAsegurado += '- Identificaci&oacute;n oficial.(Aplica para los cuatro)<br>';
            infoAsegurado += '<li>Representante Legal</li>'
            infoAsegurado += '- Acta de nacimiento.<br>';
            infoAsegurado += '- Poder notarial.<br>';
            infoAsegurado +='</div>';
		
		$('#ayudaDocProbatorio').popover({
			animation : true,
			html : true,
			title : 'Ayuda',
			content : infoDocProbatorio,
			trigger : 'hover',
			placement : 'right',
			container : 'body'
		});
		
		$('#ayudaNss').popover({
			animation : true,
			html : true,
			title : 'Ayuda',
			content : infoNSS,
			trigger : 'hover',
			placement : 'right',
			container : 'body'
		});
		
		//PENDIENTE CONFIRMACIÓN
		$('#ayudaDoctos').popover({
			animation : true,
			html : true,
			title : 'Ayuda',
			content : infoAsegurado,
			trigger : 'hover',
			placement : 'right',
			container : 'body'
		});
		
		$('[data-toggle="tooltip"]').tooltip();
		
	});
	
</script>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/solicitud/documentosAsegurado.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/common/tablesCommon.js" htmlEscape="true" />"></script>
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
		id="datosHistoriaLaboralForm" action="${urlDatosHistoriaLaboral}" accept-charset="ISO-8859-1">
		<form:errors cssClass="alert alert-danger" element="div"/>
		<%@ include file="datosHistoriaLaboral/documentosProbAseg.jsp"%>		
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