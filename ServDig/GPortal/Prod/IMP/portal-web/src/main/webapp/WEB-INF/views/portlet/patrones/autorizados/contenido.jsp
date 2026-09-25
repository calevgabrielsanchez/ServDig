<!-- JSP Contenido del Portlet de Patrones asociados a la persona. -->

<%@ include file="../../../general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.PortalContextEnum"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<c:choose>
	<c:when test="${not empty patronesAsociados }">
		<div id="patronesAutorizadosWrapper"
			style="width: 100%; margin: 0 auto;">
			<table id="tblPatronesAutorizados" style="width: 100%;"
				class="table table-striped table-bordered" cellpadding="0"
				cellspacing="0" border="0">
				<thead>
					<tr>
						<th>Registro Patronal</th>
						<th>Nombre Comercial</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${patronesAsociados}" var="patron"
						varStatus="indice">
						<tr>
							<td><!--  a href="#" class="patronAutorizado link"-->
								<!--  numeroRegistroPatronal="${patron.numeroRegistroPatronal}${patron.modalidad.numModalidad}${patron.digVerificador}"
								modalidadPatron="${patron.modalidad.numModalidad}"> -->
									${patron.numeroRegistroPatronal}
									${patron.modalidad.numModalidad} ${patron.digVerificador} <!-- </a>--></td>
							<td>${patron.nombreComercial}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</c:when>
	<c:otherwise>
	
		<div class="container-fluid empty-state">
			<div class="row">
				<!-- Imagen -->
				<div class="col-xs-12 imagen">
					<i class="glyphicon glyphicon-exclamation-sign"></i>
				</div>
			</div>

			<div class="row">
				<!-- Titulo -->
				<div class="col-xs-12 titulo">
					<spring:message code="label.portlet.sin.resultados.patrones.asociados.autorizados" />
				</div>
			</div>
		</div>
		
	</c:otherwise>
</c:choose>


<form id="formPatronAsociado" action="#" method="post">
	<input id="hdnRegistroPatronal" type="hidden" value="" name="numeroRegistroPatronal">
	<input id="hdnModalidadPatron" type="hidden" value="" name="modalidad.numModalidad">
	
	<input id="hdnIdEmpresaFisica" type="hidden" value="" name="fisica.idPersona">
	<input id="hdnRFCEmpresaFisica" type="hidden" value="" name="fisica.rfc">
	<input id="hdnIdFiscalEmpresaFisica" type="hidden" value="" name="fisica.cveFisica">
	
	<input id="hdnIdEmpresaMoral" type="hidden" value="" name="moral.idPersona"> 
	<input id="hdnRFCEmpresaMoral" type="hidden" value="" name="moral.rfc">
	<input id="hdnIdFiscalEmpresaMoral" type="hidden" value="" name="moral.cveMoral">
</form>

<form id="formPersonaSession" action="#" method="post">
	<input id="hdnCveTipoPersona" type="hidden"
		value="${persona.tipoPersona.idTipoPersona}" name="cveTipoPersona">
	<input id="hdnCveIdPersona" type="hidden" value="${persona.idPersona}"
		name="cveIdPersona">
</form>

<script id="initPortlet">
	$('#tblPatronesAutorizados').dataTable({
		"bDestroy": true,
		"bLengthChange": false,
		"sPaginationType": "bootstrap",
		"aoColumnDefs": [{"sSortDataType": "html", "sType": "html", "aTargets": [0]}],
		"bAutoWidth": false , 
		"aoColumns" : [{ 'sWidth': '20%' },{ 'sWidth': '80%' }]
	});

	$('.patronAutorizado').live( 'click' , function(){
		$.blockUI();
		var numeroRegistroPatronal = $(this).attr('numeroRegistroPatronal');
		var modalidadPatron = $(this).attr('modalidadPatron');

		$('#hdnRegistroPatronal').val(numeroRegistroPatronal);
		$('#hdnModalidadPatron').val(modalidadPatron);
		
		/* 
		 * Se realiza esta validación para saber si estamos en el portal
		 * empresa y poder mandar la información necesaria de la 
		 * empresa para mostrarlo en la barra de navegación
		 */
		if (portalContext == <%=PortalContextEnum.EMPRESA.getId()%>){
			if ($('input#hdnCveTipoPersona').val() == 1) {
				$('input#hdnIdEmpresaFisica').val($('input#hdnIdPersonaRepresentada').val());
				$('input#hdnRFCEmpresaFisica').val($('input#hdnRfcPersonaRep').val());
				$('input#hdnIdFiscalEmpresaFisica').val($('input#idPersonaFisicaMoral').val());
			} else {
				$('input#hdnIdEmpresaMoral').val($('input#hdnIdPersonaRepresentada').val());
				$('input#hdnRFCEmpresaMoral').val($('input#hdnRfcPersonaRep').val());
				$('input#hdnIdFiscalEmpresaMoral').val($('input#idPersonaFisicaMoral').val());
			}
		}
		
		document.getElementById('formPatronAsociado').action =  context_path + '/portal/patron/ingresar/';
		document.getElementById('formPatronAsociado').submit();
	});
</script>