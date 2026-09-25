<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="idTipoTramite" value="<%=TipoTramiteEnum.BAJA_SOCIO.getCodigo()%>" />	
<c:set var="origenINTERNET" value="<%=OrigenSolicitudEnum.INTERNET.getId()%>" />
<c:set var="origenVENTANILLA" value="<%=OrigenSolicitudEnum.VENTANILLA.getId()%>" />

<script type="text/javascript" 
	src="<spring:url value="/static/resources/js/delta/afiliacion/common/commonMethods.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/wizard/socios/baja/contenido.js" htmlEscape="true" />"></script>

<style>
.empty-state .titulo {
    font-size: 15px !important;
    margin-bottom: 20px;
}

.contenedor .pie .opciones {
    float: left;
    width: 60%;
}

.contenedor .pie .controles {
    float: right;
    width: 40%;
}

</style>

<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="col-sm-12">
	
		<c:choose>
			<c:when test="${empty error }">
			
				<script>
					var idTipoTramite = ${idTipoTramite};
					var tipoSolicitud = <%=TipoSolicitudEnum.ACTUALIZACION_DATOS_GENERALES.getValor()%>;
													
					if(parent.WizardBajaSociosCtrl.config.idOrigen == 2){
						var codigoTipoSolicitud = ${codigoTipoSolicitud};
						var descripcionTipoSolicitud = '${descripcionTipoSolicitud}';
						var arrayCodigoTipoTramite = ${codigoTipoTramite};

						var datosEntradaFirma = {
							fechaElectronica : '${datosFirmaElectronica.fechaElectronicaFormateada}',
							nombreCompleto : "${datosFirmaElectronica.nombreCompleto}",
							registroPatronal : '${datosFirmaElectronica.registroPatronal}',
							rfc : '${datosFirmaElectronica.rfc}',
							curp : '${datosFirmaElectronica.curp}'
						};
					}
				</script>
				
				<form action="" method="post" id="soForm"></form>
				<input type="hidden" id="idSolicitud" value="${solicitud.solicitudId}"/>
				<input type="hidden" id="contenidoFirmar" value="${contenidoFirmar}">
				<input type="hidden" id="folioSolicitud" value="${solicitud.noFolioSolicitud}" />
								
				<div class="alert alert-success">
					<c:choose>
						<c:when test="${!isRetomar}">
							Su solicitud ha sido iniciada correctamente y se le ha asignado a dicha solicitud el folio: <strong>${solicitud.noFolioSolicitud}</strong>
						</c:when>
						<c:otherwise>
							El folio de la solicitud que esta retomando es: <strong>${solicitud.noFolioSolicitud}</strong>
						</c:otherwise>
					</c:choose>
				</div>
				
				<div class="container-fluid empty-state">
					<div class="row">
						<!-- Imagen -->
						<div class="col-xs-12 imagen">
							<i class="glyphicon glyphicon-ok-sign"></i>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 titulo">
							<strong>Selecciona a los socios a dar de baja:</strong>
						</div>
					</div>						
					
					<div id="sociosWrapper" style="width: 100%; margin: 0 auto;">
						<table id="tblSociosResumen" style="width: 100%;"
							class="table table-striped" cellpadding="0"
							cellspacing="0" border="0">
							<thead>
								<tr>
									<th colspan="4" align="center">Socios</th>
								</tr>
								<tr>
									<th>Selecci&oacute;n</th>
									<th>RFC</th>
									<th>CURP</th>
									<th>Nombre</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${listaSocios}" var="vSocio" varStatus="indice">
									<tr>
										<td>
											<c:choose>
												<c:when test="${vSocio.checked}">
													<input type="checkbox" id="socioL" name = "socioL" value="${vSocio.idSocio}" checked="checked">
												</c:when>
												<c:otherwise>
													<input type="checkbox" id="socioL" name = "socioL" value="${vSocio.idSocio}">
												</c:otherwise>
											</c:choose>
										</td>
										<td>${vSocio.rfc}</td>
										<td>${vSocio.curp}</td>
										<td>${vSocio.nombreRazonSocial }</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>											
				</div>
			</c:when>
			<c:otherwise>
				<div class="container-fluid empty-state">
					<div class="row"><div class="col-xs-12 imagen"><i class="glyphicon glyphicon-remove-sign"></i></div></div>
					<div class="row"><div class="alert alert-danger">${error}</div></div>
				</div>
			</c:otherwise>
		</c:choose>	
		</div>		
	</div>
	
	<div div class="pie row">
		<div class="opciones col-sm-6">
			<c:if test="${empty error}">
				<div class="btn-group dropup">
					<a href="#" class="btn btn-primary">Acciones</a> <a href="#"
						data-toggle="dropdown" class="btn btn-primary dropdown-toggle"><span
						class="caret"></span></a>
					<ul class="dropdown-menu">
						<c:if test="${origenApp eq origenINTERNET}">
							<li><a id="finalizarTramite"><i class="glyphicon glyphicon-ok"></i>
								Finalizar Tr&aacute;mite</a></li>
						</c:if>
						<li><a id="guardarTramite"><i class="glyphicon glyphicon-download-alt"></i>
							Guardar Tr&aacute;mite</a></li>
						<li><a id="cancelarTramite"><i class="glyphicon glyphicon-trash"></i>
							Cancelar Tr&aacute;mite</a></li>
					</ul>
				</div>
			</c:if>
		</div>
		<div class="controles col-sm-6">
			<div class="pull-right">
				<button class="btn btn-default" id="cerrarWizard">CERRAR</button>
				<c:if test="${empty error}">
					<c:if test="${origenApp eq origenVENTANILLA}">
						<a id="mostrarRLVentanilla" class="btn btn-primary">
							<i class="glyphicon glyphicon-step-forward"></i>
							Siguiente
						</a>
					</c:if>
				</c:if>
			</div>
		</div>
	</div>
	<div id="pie" class="row"> </div>
</div>

<!-- Divs para dialogos de mensajes -->
<div id="dialog-confirm-cancelar"
	title="Confirmar cancelaci&oacute;n de solicitud">
	<p><span class="ui-icon ui-icon-alert"
		style="float: left; margin: 0 7px 20px 0;"></span> ¿Desea cancelar la
		solicitud pendiente con folio: <strong>${solicitud.noFolioSolicitud}</strong>?</p>
</div>

<div id="dialog-confirm-common" title="Mensaje">
	<p><span class="ui-icon ui-icon-alert"
		style="float: left; margin: 0 7px 20px 0;"></span> <label
		id="mensajeDialogo"></label></p>
</div>
