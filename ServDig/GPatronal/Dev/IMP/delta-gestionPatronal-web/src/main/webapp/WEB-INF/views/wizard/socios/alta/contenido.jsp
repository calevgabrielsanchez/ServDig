<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="idTipoTramite" value="<%=TipoTramiteEnum.ACTUALIZACION_SOCIO.getCodigo()%>" />
<c:set var="origenINTERNET" value="<%=OrigenSolicitudEnum.INTERNET.getId()%>" />
<c:set var="origenVENTANILLA" value="<%=OrigenSolicitudEnum.VENTANILLA.getId()%>" />

<script type="text/javascript" 
	src="<spring:url value="/static/resources/js/delta/afiliacion/common/commonMethods.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/wizard/socios/alta/contenido.js" htmlEscape="true" />"></script>

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
															
					if(parent.WizardAltaSociosCtrl.config.idOrigen == 2){
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
				
				<input type="hidden" id="contenidoFirmar" value="${contenidoFirmar}">
				
				<form:form modelAttribute="socio" id="soForm" method="post">
				
					<form:hidden path="rfcPersonaMoralPatron" id="rfcPersonaMoralPatron" />
					<form:hidden path="idPersonaMoralPatron" id="idPersonaMoralPatron" />
					<input type="hidden" id="idSolicitud" value="${solicitud.solicitudId}" />
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
								<strong>Informaci&oacute;n localizada:</strong>
							</div>
						</div>
						<div class="form-horizontal">						
							<c:choose>
								<c:when test="${socio.tipoSocio.idTipoPersona eq '1' }">
									<!-- INI FISICA -->
									<div class="form-group">
										<label class="col-sm-4 col-sm-offset-1 control-label">RFC:</label>
										<div class="col-sm-6">
											<p class="form-control-static">${socio.personaFisica.rfc}</p>
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-4 col-sm-offset-1 control-label">CURP:</label>
										<div class="col-sm-6">
											<p class="form-control-static">${socio.personaFisica.curp}</p>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-4 col-sm-offset-1 control-label">Nombre:</label>
										<div class="col-sm-6">
											<p class="form-control-static">${socio.personaFisica.nombre}</p>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-4 col-sm-offset-1 control-label">Apellido paterno:</label>
										<div class="col-sm-6">
											<p class="form-control-static">${socio.personaFisica.primerApellido}</p>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-4 col-sm-offset-1 control-label">Apellido materno:</label>
										<div class="col-sm-6">
											<p class="form-control-static">${socio.personaFisica.segundoApellido}</p>
										</div>
									</div>								
									<!-- FIN FISICA -->
								</c:when>
								<c:otherwise>
									<!-- INI MORAL -->
									<div class="form-group">
										<label class="col-sm-4 col-sm-offset-1 control-label">RFC:</label>
										<div class="col-sm-6">
											<p class="form-control-static">${socio.personaMoral.rfc}</p>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-4 col-sm-offset-1 control-label">Raz&oacute;n Social:</label>
										<div class="col-sm-6">
											<p class="form-control-static">${socio.personaMoral.razonSocial}</p>
										</div>
									</div>
									<!-- FIN MORAL -->
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</form:form>			
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
	
	<div class="pie row">
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
						<a id="mostrarRLVentanilla" class="btn btn-primary"> <i
							class="glyphicon glyphicon-step-forward"></i> Siguiente
						</a>
					</c:if>
				</c:if>
			</div>
		</div>
	</div>
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
