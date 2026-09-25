<!-- JSP Contenido del Portlet de Patrones asociados a la persona. -->

<%@ include file="../../../general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.PortalContextEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum"%>

<c:set var="modalidad34" value="<%=ModalidadEnum.TREINTAYCUATRO.getNumModalidad()%>" />

<script>
var tipoTramiteModificacionSRT = <%=TipoSolicitudEnum.ACTUALIZACION_DE_CLASIFICACION.getValor() %>;
var tipoTramiteModificacionCentroTrabajo = <%=TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO.getValor() %>;
var portletEnumEmpresaId = <%=PortalContextEnum.EMPRESA.getId()%>;

</script>

<style>

label {
	display: inline;
}
.table_form table {
	margin: 15px auto;
}
table{max-width:100%;background-color:transparent;border-collapse:collapse;border-spacing:0;}
.table-word-wrap-fixed{word-wrap:break-word !important; table-layout: fixed;}

</style>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<c:choose>
	<c:when test="${not empty patronesAsociados }">
		<div id="patronesAsociadosWrapper"
			style="width: 100%; margin: 0 auto;">
			<table id="tblPatronesAsociados" style="width: 100%;"
				class="table table-striped table-bordered table-word-wrap-fixed" cellpadding="0"
				cellspacing="0" border="0">
				<thead>
					<tr>
						<th>Registro patronal</th>
						<th>Nombre comercial</th>
						<th>Fecha de alta</th>
						<th></th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${patronesAsociados}" var="patron"
						varStatus="indice">
						<tr>
							<td>
								<c:choose>
									<c:when test="${(not empty patron.descSituacionBaja) or (patron.modalidad.numModalidad eq modalidad34)}">
										${patron.numeroRegistroPatronal}
										${patron.modalidad.numModalidad} ${patron.digVerificador}
									</c:when>
									<c:otherwise>
									<a href="#" class="patronAsociado link"
									numeroRegistroPatronal="${patron.numeroRegistroPatronal}${patron.modalidad.numModalidad}${patron.digVerificador}"
									modalidadPatron="${patron.modalidad.numModalidad}" indRegPatClase="${patron.clasificacion.indRegPatClase }" 
									idTipoRegPatron="${patron.idTipoRegPatron}">
										${patron.numeroRegistroPatronal}
										${patron.modalidad.numModalidad} ${patron.digVerificador} </a>
									</c:otherwise>
								</c:choose>
							</td>
								
							<td>${patron.nombreComercial}</td>
							<td><fmt:formatDate value="${patron.fechaAlta}"
									pattern="dd/MM/yyyy" /></td>
							<td>${patron.fechaAlta}</td>
							<td>
							<c:choose>
								<c:when test="${not empty patron.descSituacionBaja}">
								<c:set var="statusPatron" value="0" />
								</c:when>
								<c:otherwise>
								<c:set var="statusPatron" value="1" />
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${(patron.modalidad.numModalidad eq modalidad34)}">
										<span class="label label-danger label-imss label-danger-imss">${patron.descSituacionBaja}</span>
								</c:when>
								<c:otherwise>
									<div class="btn-group" id="wantToolNext">
										<button class="btn btn-primary btn-xs"><spring:message code="label.menus.opciones" /></button>
										<a class="btn btn-primary btn-xs dropdown-toggle"
											data-toggle="dropdown" href="#"´
											id="spanPatron${patron.numeroRegistroPatronal}${patron.modalidad.numModalidad}${patron.digVerificador}"
										><span class="caret" ></span></a>
										<ul class="dropdown-menu pull-right" id="ulsubmenuPatron${patron.numeroRegistroPatronal}${patron.modalidad.numModalidad}${patron.digVerificador}">
											<li>
												Cargando Acciones...
											<li>
										</ul>
										<script type="text/javascript">
											$("#spanPatron${patron.numeroRegistroPatronal}${patron.modalidad.numModalidad}${patron.digVerificador}").live(
												"click",
												function() {
													$.post("/portal-web/utility/menu/submenu/patrones/${patron.numeroRegistroPatronal}${patron.modalidad.numModalidad}${patron.digVerificador}/${patron.clasificacion.indRegPatClase}/${patron.modalidad.numModalidad}/${patron.idTipoRegPatron}/${statusPatron}/${persona.rfc}",null,function(data) {
														$("#ulsubmenuPatron${patron.numeroRegistroPatronal}${patron.modalidad.numModalidad}${patron.digVerificador}").html(data);
													});
												}
											);
										</script>
									</div>
									
									<c:if test="${statusPatron == 0}">
										<span class="label label-danger label-imss label-danger-imss">${patron.descSituacionBaja}</span>
									</c:if>
								</c:otherwise>
							</c:choose>
							
							</td>
							
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</c:when>
	<c:otherwise>

		<!-- Contenedor del estado vacio de la lista de patrones -->
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
					<spring:message
						code="label.portlet.sin.resultados.patrones.asociados.persona" />
				</div>
			</div>
			
			</br>

			<!-- Opciones del empty state, si en el properties de opciones estan activas, aquí es donde se pondrán -->
			<div id="opcNavEmptyStateMisPatrones">
				
			</div>
		</div>
	</c:otherwise>
</c:choose>


<form id="formPatronAsociado" action="#" method="post">
	<input id="hdnRegistroPatronal" type="hidden" value="" name="numeroRegistroPatronal">
	<input id="hdnModalidadPatron" type="hidden" value="" name="modalidad.numModalidad">
	<input id="hdnIndRegPatClase" type="hidden" value="" name="clasificacion.indRegPatClase">
	<input id="hdnIdEmpresaFisica" type="hidden" value="" name="fisica.idPersona">
	<input id="hdnRFCEmpresaFisica" type="hidden" value="" name="fisica.rfc">
	<input id="hdnNombreEmpresaFisica" type="hidden" value="" name="fisica.nombre">
	<input id="hdnCurpEmpresaFisica" type="hidden" value="" name="fisica.curp">
	<input id="hdnIdFiscalEmpresaFisica" type="hidden" value="" name="fisica.cveFisica">
	
	<input id="hdnIdEmpresaMoral" type="hidden" value="" name="moral.idPersona"> 
	<input id="hdnRFCEmpresaMoral" type="hidden" value="" name="moral.rfc">
	<input id="hdnNombreEmpresaMoral" type="hidden" value="" name="moral.razonSocial">
	<input id="hdnIdFiscalEmpresaMoral" type="hidden" value="" name="moral.cveMoral">
	<input id="hdnIdTipoRegPatron" type="hidden" value="" name="idTipoRegPatron">
	
</form>

<form id="formPersonaSession" action="#" method="post">
	<input id="hdnCveTipoPersona" type="hidden"
		value="${persona.tipoPersona.idTipoPersona}" name="cveTipoPersona">
	<input id="hdnCveIdPersona" type="hidden" value="${persona.idPersona}"
		name="cveIdPersona">
</form>

<script id="initPortlet">

	$.post("/portal-web/utility/menu/opciones/7/1",null,function(data) {
		$("#opcNavEmptyStateMisPatrones").html(data);
	}); 
	
	$('#tblPatronesAsociados').dataTable({
		"bDestroy": true,
		"bLengthChange": false,
		"bAutoWidth": false,
		"sPaginationType": "bootstrap",
		"aoColumnDefs": [{"sSortDataType": "html", "sType": "html", "aTargets": [0]}, 
		                 {"iDataSort": 3, "aTargets": [2]}, 
		                 {"bVisible": false, "aTargets": [3]}],
         "aoColumns" : [{ "sWidth": "20%"},
                        { "sWidth": "25%"},
                        { "sWidth": "15%"},
                        { "sWidth": "0%"},
                        { "sWidth": "20%"}]
	});

	$('.patronAsociado').live( 'click' , function(){
		$.blockUI();
		var numeroRegistroPatronal = $(this).attr('numeroRegistroPatronal');
		var modalidadPatron = $(this).attr('modalidadPatron');
		var indRegPatClase = $(this).attr('indRegPatClase');
		var idTipoRegPatron = $(this).attr('idTipoRegPatron');

		$('#hdnRegistroPatronal').val(numeroRegistroPatronal);
		$('#hdnModalidadPatron').val(modalidadPatron);
		$('#hdnIndRegPatClase').val(indRegPatClase);
		$('#hdnIdTipoRegPatron').val(idTipoRegPatron);
		
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
				$('input#hdnNombreEmpresaFisica').val($('input#hdnNombrePersonaRepresentada').val());
				$('input#hdnCurpEmpresaFisica').val($('input#hdnCurpPersonaRepresentada').val());
			} else {
				$('input#hdnIdEmpresaMoral').val($('input#hdnIdPersonaRepresentada').val());
				$('input#hdnRFCEmpresaMoral').val($('input#hdnRfcPersonaRep').val());
				$('input#hdnNombreEmpresaMoral').val($('input#hdnNombrePersonaRepresentada').val());
				$('input#hdnIdFiscalEmpresaMoral').val($('input#idPersonaFisicaMoral').val());
			}
		} else {
			$('input#hdnIdEmpresaFisica').val($('input#hdnIdPersonaRepresentada').val());
			$('input#hdnRFCEmpresaFisica').val($('input#hdnRfcPersonaRep').val());
			$('input#hdnIdFiscalEmpresaFisica').val($('input#idPersonaFisicaMoral').val());
			$('input#hdnNombreEmpresaFisica').val($('input#hdnNombrePersonaRepresentada').val());
			$('input#hdnCurpEmpresaFisica').val($('input#hdnCurpPersonaRepresentada').val());
		}
		
		document.getElementById('formPatronAsociado').action =  context_path + '/portal/patron/ingresar/';
		document.getElementById('formPatronAsociado').submit();
	});
			
</script>

<div id="dialogoMensajes">
	<p><span id="textoMensaje"></span></p>
</div>