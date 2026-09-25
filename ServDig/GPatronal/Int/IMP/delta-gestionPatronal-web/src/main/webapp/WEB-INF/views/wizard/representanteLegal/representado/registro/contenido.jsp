<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.enums.EstatusPersona"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/common/commonMethods.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/date.format.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/procesaErrores.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/messages_es.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/wizard/representanteLegal/representado/registro/contenidoRegistroRepresentado.js" htmlEscape="true" />"></script>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="origenINTERNET" value="<%=OrigenSolicitudEnum.INTERNET.getId()%>" />
<c:set var="origenVENTANILLA" value="<%=OrigenSolicitudEnum.VENTANILLA.getId()%>" />
<c:set var="idTipoTramite" value="<%=TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.getCodigo()%>" />

<script>
	var tipoSolicitud = <%=TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES.getValor()%>;
	var codigoPersonaInexistente = '<%=EstatusPersona.Inexistente.getCodigo()%>';
	var codigoPersonaSinRP = '<%=EstatusPersona.Sin_Registros_Patronales.getCodigo()%>';
	var idTipoTramite = ${idTipoTramite};
		
	if(parent.WizardRegistroRepresentadoLegalCtrl.config.idOrigen == 2){
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



<style type="text/css">
	.dataTables_wrapper table thead {
		display: none;
	}
</style>

<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="col-sm-12">

			<div id="errorNegocio"></div>

			<c:if test="${!solicitudCreada}">
				<div>
					<div class="alert alert-info">Proporciona el RFC y el tipo de persona del representado</div>

					<form:form modelAttribute="busquedaPersona" id="busquedaPersona" method="post" cssClass="form-horizontal m-t-lg"
						role="form" action="${contextpath}/wizard/tramite/representado/registro/crear/solicitud">
						<input type="hidden" name="representantesLegales[0].personaFisica.rfc" value="" id="rfcRepresentante" />

						<div class="form-group">
							<label for="rfc" class="col-sm-3 col-sm-offset-1 control-label">
								RFC <span class="required">*</span>: 
								<a class="btn btn-xs icono-help" data-toggle="tooltip" title="Registro Federal de Contribuyentes" ></a>
							</label>
							<div class="col-sm-6">
								<form:input path="rfc" maxlength="13" cssClass="form-control" />
								<span id="rfcError" class="error hiddenElement"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-3 col-sm-offset-1 control-label">
								Tipo persona <span class="required">*</span>:
							</label>
							<div class="col-sm-6">
								<select name="tipoPersona.idTipoPersona" id="tipoPersona.idTipoPersona" class="form-control">
									<option value="0">-- Selecciona por favor --</option>
									<option value="1">F&iacute;sica</option>
									<option value="2">Moral</option>
								</select>
								<span id="tipoPersona.idTipoPersonaError" class="error hiddenElement"></span>
							</div>
						</div>
					</form:form>
				</div>

			</c:if>

			<c:if test="${solicitudCreada}">
				<c:if test="${empty solicitud.errorFormGeneral}">
					<input type="hidden" id="idSolicitud" value="${solicitud.solicitudId}" />
					<div class="alert alert-info">
						<c:choose>
								<c:when test="${!isRetomar}">
								<spring:message code="label.solicitud.iniciada" arguments="${solicitud.noFolioSolicitud}"/>
								</c:when>
								<c:otherwise>
								<spring:message code="label.solicitud.retomando" arguments="${solicitud.noFolioSolicitud}"/>
								</c:otherwise>
						</c:choose>
						<input type="hidden" id="contenidoFirmar" value="${contenidoFirmar}" />
						<input type="hidden" id="folioSolicitud" value="${solicitud.noFolioSolicitud}" />
					</div>

					<div>
						<h4>Datos de la empresa a representar</h4>
						<hr style="margin-bottom: 10px;" class="red"/>
						<form:form modelAttribute="tramite" id="forma" method="post" cssClass="form-horizontal" role="form"
							cssStyle="margin: 25px auto 30px;">
							<c:if test="${tramite.fisicaRepresentada != null}">
							<div class="row">
								<div class="col-sm-6">
									<label for="rfc" class="control-label">RFC : 
									<a class="btn btn-xs icono-help" data-toggle="tooltip" title="Registro Federal de Contribuyentes" ></a>
									</label>
									<form:input disabled="true" path="fisicaRepresentada.rfc" cssClass="form-control ns_" />
								</div>

								<div class="col-sm-6">
									<label for="rfc" class="control-label">CURP:</label>
									<form:input disabled="true" cssClass="form-control ns_" path="fisicaRepresentada.curp" />
								</div>
							</div>
							
							<div class="row">
								<div class="col-sm-6">
									<label for="rfc" class="control-label">Nombre:</label>
									<form:input disabled="true" cssClass="form-control ns_" path="fisicaRepresentada.nombre" />
								</div>

								<div class="col-sm-6">
									<label for="rfc" class="control-label">Primer apellido:</label>
									<form:input disabled="true" cssClass="form-control ns_" path="fisicaRepresentada.primerApellido" />
								</div>
							</div>
							
								
							</c:if>
							<c:if test="${tramite.moralRepresentada != null}">
								<div class="row">
									<div class="col-sm-6">
										<label for="rfc" class="control-label">RFC: 
										<a class="btn btn-xs icono-help" data-toggle="tooltip" title="Registro Federal de Contribuyentes" ></a>
										</label>
										<form:input cssClass="form-control ns_" disabled="true" path="moralRepresentada.rfc" />
									</div>
	
									<div class="col-sm-6">
										<label for="rfc" class="control-label">Raz&oacute;n Social:</label>
										<form:input cssClass="form-control ns_" disabled="true" path="moralRepresentada.razonSocial" />
									</div>
								</div>

									
							</c:if>
							
							<div class="row">
							<div class="col-sm-6">
								<c:if test="${tramite.fisicaRepresentada != null}">
									<label for="rfc" class="control-label">Segundo apellido:</label>
									<form:input disabled="true" cssClass="form-control ns_" path="fisicaRepresentada.segundoApellido" />
								</c:if>
								<c:if test="${tramite.moralRepresentada != null}">
									<label for="rfc" class="control-label">Tipo de soociedad:</label>
									<form:input cssClass="form-control ns_" disabled="true" path="moralRepresentada.tipoSociedad.descripcion" />
								</c:if>
							</div>
							
							<div class="col-sm-6">
								<label for="tipoPoder" class="control-label">
									Tipo de poder <span class="required">*</span>:
								</label>
								<combo:creaCombo idHtml="idTipoPoder" idHtmlContenedor="forma"
									entidad="mx.gob.imss.ctirss.delta.persistence.DicTipoPoder" idHtmlValor="${fisica.tipoPoder.idTipoPoder}"
									mostrarSoloActivos="true" cssClassname="form-control ns_" />
								<div id="idTipoPoderError"><span id="idTipoPoderErrorZ" class="error">El tipo de poder es requerido</span></div>
							</div>
							
							</div>
						</form:form>
						<form action="" method="post" id="soForm"></form>
					</div>
				</c:if>
				<c:if test="${not empty solicitud.errorFormGeneral}">
					<div class="alert alert-danger">
						<button type="button" class="close" data-dismiss="alert" />
						<strong>Error: </strong>
						${solicitud.errorFormGeneral}
					</div>
				</c:if>
			</c:if>
		</div>
	</div>

	<div class="pie row">
		<div class="col-sm-4">
			<div style="float: left; padding: 11px 0px;"><span class="required" id="labelCamposObligatoriosGeneral">*</span><spring:message code="label.camposObligatorios" /></div>
		</div>
		<div class="col-sm-8">
			<div class="pull-right">
				<button class="btn btn-default" id="cerrarWizard">Cerrar</button>
				<c:if test="${!solicitudCreada}">
					<a id="siguienteFirRepresentado" class="btn btn-primary">
						<i class="glyphicon glyphicon-step-forward"></i>
						Siguiente
					</a>
				</c:if>
				<c:if test="${origenApp eq origenVENTANILLA}">
					<c:if test="${solicitudCreada}">
						<a id="mostrarRLVentanilla" class="btn btn-primary">
							<i class="glyphicon glyphicon-step-forward"></i>
							Siguiente
						</a>
					</c:if>
				</c:if>
				
				<c:if test="${solicitudCreada}">
				<c:if test="${empty solicitud.errorFormGeneral}">
					<div class="btn-group  dropup">
						<a href="#" class="btn btn-primary">
							<spring:message code="label.menus.opciones" />
						</a>
						<a href="#" data-toggle="dropdown" class="btn btn-primary dropdown-toggle">
							<span class="caret"></span>
						</a>
						<ul class="dropdown-menu  pull-right">
							<c:if test="${origenApp eq origenINTERNET}">
								<li>
									<a id="finalizarTramite">
										<i class="glyphicon glyphicon-ok"></i>
										Finalizar tr&aacute;mite
									</a>
								</li>
							</c:if>
							<li>
								<a id="cancelarTramite">
									<i class="glyphicon glyphicon-trash"></i>
									Cancelar tr&aacute;mite
								</a>
							</li>
						</ul>
					</div>
				</c:if>

			</c:if>
			</div>
		</div>
	</div>
</div>




<input type="hidden" id="folioSolicitud" value="${solicitud.noFolioSolicitud}">
<!-- Divs para dialogos de mensajes -->
<div id="dialog-confirm-cancelar" title="Confirmar cancelaci&oacute;n de solicitud">
	<p>
		<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>
		¿Deseas cancelar la solicitud pendiente con folio:
		<strong>${solicitud.noFolioSolicitud}</strong>
		?
	</p>
</div>

<div id="dialog-confirm" title="Mensaje">
	<p>
		<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>
		<label id="mensajeDialogo"></label>
	</p>
</div>

<div id="doctosRequeridosTramite"></div>
<div id="wizardAltaPatronal"></div>