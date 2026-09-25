<%@ include file="../../general/taglibs.jsp" %>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum"%>

<c:set var="OrigenSolicitudEnumVentanilla" value="<%=OrigenSolicitudEnum.VENTANILLA.getId()%>"/>
<c:set var="OrigenSolicitudEnumInternet" value="<%=OrigenSolicitudEnum.INTERNET.getId()%>"/>
<c:set var="OrigenSolicitudEnumCiudadano" value="<%=OrigenSolicitudEnum.PORTAL_CIUDADANO.getId()%>"/>

<script type="text/javascript">
	var codigoTipoSolicitud = ${codigoTipoSolicitud};
	var descripcionTipoSolicitud = '${descripcionTipoSolicitud}';
	var arrayCodigoTipoTramite = ${codigoTipoTramite};

	var datosEntradaFirma = {
		fechaElectronica : '${datosFirmaElectronica.fechaElectronicaFormateada}',
		nombreCompleto : '${datosFirmaElectronica.nombreCompleto}',
		registroPatronal : '${datosFirmaElectronica.registroPatronal}',
		rfc : '${datosFirmaElectronica.rfc}',
		curp : '${datosFirmaElectronica.curp}'
	};
	
</script>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/wizard/bajaDerechohabiente/contenido.js" htmlEscape="true" />"></script>
<c:set var="contextPath" value="<%=request.getContextPath()%>" />
<div class="contenedor col-sm-12">
	
	<input type="hidden" value="${idOrigenSolicitud}" id="idOrigenSolicitud"/>
	<input type="hidden" value="${solicitud.solicitudId}" id="idSolicitud"/>
	<input type="hidden" value="${requiereDocs ? 1 : 0}" id="requiereDocs"/>
	<input type="hidden" value="${tramite.tipoTramite.homoclave}" id="homoclaveTramite"/>
	
	<div class="contenido row">
	<div class="col-sm-12">
		<div class="separadorseccion">
			<span>
				${fn:toUpperCase(fn:substring(descripcionTipoSolicitud, 0, 1))}${fn:toLowerCase(fn:substring(descripcionTipoSolicitud, 1,fn:length(descripcionTipoSolicitud)))}
			</span>
		</div>
		<c:choose>
		<c:when test="${empty error }">
				<input type="hidden" id="idSolicitud" value="${solicitud.solicitudId}"/>
				<div class="alert alert-info">
				<c:choose>
					<c:when test="${!isRetomar}">
						<spring:message code="label.solicitudiniciada" arguments="${solicitud.noFolioSolicitud}"/>
					</c:when>
					<c:otherwise>
						<spring:message code="label.solicitudRetomada" arguments="${solicitud.noFolioSolicitud}"/>
					</c:otherwise>
				</c:choose>
				<input type="hidden" id="contenidoFirmar" value="${contenidoFirmar}" />
				<input type="hidden" id="folioSolicitud" value="${solicitud.noFolioSolicitud}" />
				</div>
				<div class="alert alert-danger" style="display: none" id="divErrorCampos"></div>
				<div id="patronesWrapper" style="width: 100%; margin: 0 auto;">
					
				<jsp:include page="../../common/datosDerechohabiente.jsp"></jsp:include>
				
				<div class="separadorseccion">
					<span>
						Datos de la baja
					</span>
				</div>
				<div>
					<form id="formularioBajaDerechohabiente" name="formularioBajaDerechohabiente" action="#" method="POST" rol="form">
					<input type="hidden" value="${tramite.persona.idPersona}" id="fisica.idPersona" name="fisica.idPersona">
					<input type="hidden" value="${tramite.tipoTramite.idTipoTramite}" id="tipoTramite.idTipoTramite" name="tipoTramite.idTipoTramite">
					<input type="hidden" value="${tramite.tramiteId}" id="tramiteId" name="tramiteId">
					<table class="table table-striped table-bordered" style="width: 100%">
						<tbody>
						<c:if test="${tramite.tipoTramite.idTipoTramite == 25}">
							<tr>
								<td align="left">
									<label class="control-label" for="fechaDefuncion">Fecha de defunci&oacute;n<span class="required">*</span>:</label>
								</td>
								<td align="left">
									<input type="text" id="fechaDefuncion" name="fechaDefuncion" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${tramite.fechaDefuncion}"/>" style="width: 140px"  class="form-control ns_"/>
									<span id="fechaDefuncionError" class="error hiddenElement"></span>
								</td>
							</tr>
						</c:if>
						<tr>
							<td align="left">
								<label class="control-label" for="observaciones">Observaciones<span class="required">*</span>: </label>
							</td>
							<td >
								<textarea id="observaciones" name="observaciones" style="height: 40px; width: 90%;"  class="form-control ns_">${tramite.observaciones}</textarea>
								<span id="observacionesError" class="error hiddenElement"></span>
							</td>
						</tr>
						</tbody>
					</table>
					</form>
				</div>
				
				<c:if test="${requiereDocs}">
					<div class="separadorseccion">
						<span>
							Documentos Probatorios
						</span>
					</div>
					<div class="row">	
					<div class="col-sm-12">
					<p>
						Este tr&aacute;mite requiere la captura de documentos probatorios, da clic en el bot&oacute;n "Captura de documentos probatorios" para proceder a la misma, no podr&aacute;s
						finalizar el tr&aacute;mite hasta completarla.<br><br>
						<a id="capturarDocumentosBaja" class="btn btn-default"  onclick="uid_call('imss.derechohabientes.baja.datosBaja.link_documentos','clickin')">
							<i class="glyphicon glyphicon-file"></i>
							Captura de documentos probatorios
						</a>
					</p>
					</div>
					</div>
				</c:if>
					
				</div>
		</c:when>
		<c:otherwise>
			<div class="alert alert-error">
					<button type="button" class="close" data-dismiss="alert">×</button>
					${error}
			</div>
		</c:otherwise>
		</c:choose>
	</div>
	</div>
	
	<div class="pie row">
		<div class="col-sm-4">
			<div style="float: left; padding: 11px 0px;"><span class="required" id="labelCamposObligatoriosGeneral">*</span><spring:message code="label.camposRequeridos" /></div>
		</div>
		<div class="col-sm-8">
			<div class="pull-right">
				<button class="btn btn-default" id="cerrarWizard"  onclick="uid_call('imss.derechohabientes.baja.datosBaja.link_cancelar','clickout')"><spring:message code="wizard.button.cerrar"/></button>
				<c:if test="${empty error}">
					<div class="btn-group dropup">
						<a href="#" class="btn btn-primary"><spring:message code="label.menus.opciones" /></a> 
						<a href="#" data-toggle="dropdown" class="btn btn-primary dropdown-toggle"><span class="caret"></span></a>
						<ul class="dropdown-menu pull-right">
							<li><a id="finalizarTramite" onclick="uid_call('imss.derechohabientes.baja.datosBaja.link_finalizar','clickin')"><i class="glyphicon glyphicon-ok"></i><spring:message code="wizard.button.finalizarTramite" /></a></li>
						<c:if test="${idOrigenSolicitud eq OrigenSolicitudEnumInternet}">
							<li><a id="guardarTramite" onclick="uid_call('imss.derechohabientes.baja.datosBaja.link_guardar','clickin')"><i class="glyphicon glyphicon-download-alt"></i><spring:message code="wizard.button.guardarTramite" /></a></li>
						</c:if>
							<li><a id="cancelarTramite" onclick="uid_call('imss.derechohabientes.baja.datosBaja.link_cancelar','clickin')"><i class="glyphicon glyphicon-trash"></i><spring:message code="wizard.button.cancelarTramite" /></a></li>
						</ul>
					</div>
				</c:if>
			</div>
			
		</div>
	</div>
</div>

<input type = "hidden" id="folioSolicitud" value="${solicitud.noFolioSolicitud}">

<div id="dialog-confirm-cancelar" title="Confirmar cancelaci&oacute;n de solicitud">
	<p>
		<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>
		<spring:message code="label.solicitud.mensaje.cancelar" arguments="${solicitud.noFolioSolicitud}"/>
	</p>
</div>

<div id="dialog-confirm" title="Mensaje">
	<p>
		<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>
		<label id="mensajeDialogo"></label>
	</p>
</div>

<div id="dialogoConfirmacion">
	<p><span id="textoConfirmacion"></span></p>
</div>

<div id="dialog-error" title="Error">
	<p>
		<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span> 
		<label id="mensajeError"></label>
	</p>
</div>

<script language="JavaScript1.2" src="${staticResourcesPath}/js/comscore/Form.js"></script>