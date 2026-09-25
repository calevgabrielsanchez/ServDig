<%@ include file="../../../../../general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/wizard/modificacion/patron/centroTrabajo/contacto/tramiteMedioContacto.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/afiliacion/common/commonMethods.js" htmlEscape="true" />"></script>


<style>
input.wauto-input {
	width: auto;
}
</style>


<input type="hidden" id="contenidoFirmar" value="${contenidoFirmar}">
<input type="hidden" id="hdnIdSolicitud" value="${idSolicitud}" />
<input type="hidden" id="folioSolicitud" value="${folioSolicitud}" />
<input type="hidden" id="idSolicitud" value="${idSolicitud}" />

<div class="contenedor col-sm-12">
	<div class="contenido row">
		<c:choose>
			<c:when test="${empty error }">
			
				<script>
					var context='${contextpath}';
					var telefonoPrincipalCompleto  = '${ctTelefonoFijo}';
					var telefonoSecundarioCompleto = '${ctTelefonoFijo2}';
					var tipoContactoTelefonoFijo = <%=TipoMedioContacto.TIPO_TELEFONO_FIJO%>;
					var tipoContactoCorreoElectronico = <%=TipoMedioContacto.TIPO_CORREO_ELECTRONICO%>;
					
					var codigoTipoSolicitud = ${codigoTipoSolicitud};
					var descripcionTipoSolicitud = '${descripcionTipoSolicitud}';
					var idTipoTramite=${idTipoTramite};
					var arrayCodigoTipoTramite = [idTipoTramite];
	
					var datosEntradaFirma = {
						fechaElectronica : '${datosFirmaElectronica.fechaElectronicaFormateada}',
						nombreCompleto : '${datosFirmaElectronica.nombreCompleto}',
						registroPatronal : '${datosFirmaElectronica.registroPatronal}',
						rfc : '${datosFirmaElectronica.rfc}',
						curp : '${datosFirmaElectronica.curp}'
					};							
				</script>

				<div class="col-sm-12">
					<form:form modelAttribute="sujetoTramite" action="">
						<form:hidden path="cveIdSujetoObligado" />

						<div class="alert alert-success">
							<c:choose>
								<c:when test="${!isRetomar}">
									<spring:message code="label.solicitud.iniciada" arguments="${folioSolicitud}"></spring:message>
								</c:when>
								<c:otherwise>
									<spring:message code="label.solicitud.retomando" arguments="${folioSolicitud}"></spring:message>
								</c:otherwise>
							</c:choose>
						</div>

						<table id="mediosContactoTable" class="table table-striped table-bordered">
							<tr>
								<td colspan="2" style="text-align: center !important;">
									<b>Raz&oacute;n Social</b>
								</td>
								<td colspan="2" style="text-align: center !important;">
									<b>Registro Federal de Contribuyentes</b>
								</td>
								<td colspan="2" style="text-align: center !important;">
									<b>N&uacute;mero de Registro Patronal</b>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<c:if test="${ sujetoTramite.fisica !=  null}">
										${sujetoTramite.fisica.nombre} ${sujetoTramite.fisica.primerApellido} ${sujetoTramite.fisica.segundoApellido}
									</c:if>
									<c:if test="${ sujetoTramite.moral !=  null}">
										${sujetoTramite.moral.razonSocial}
									</c:if>
								</td>

								<td colspan="2">
									<c:if test="${ sujetoTramite.fisica !=  null}">
										${sujetoTramite.fisica.rfc}
										<input type="hidden" id="rfcSujetoObligado" value="${sujetoTramite.fisica.rfc}" />
									</c:if>
									<c:if test="${ sujetoTramite.moral !=  null}">
										${sujetoTramite.moral.rfc}
										<input type="hidden" id="rfcSujetoObligado" value="${sujetoTramite.moral.rfc}" />
									</c:if>

								</td>

								<td colspan="2">
									<span id="nrp">${sujetoTramite.numeroRegistroPatronal}${sujetoTramite.modalidad.numModalidad}${sujetoTramite.digVerificador}</span>
								</td>
							</tr>
							<tr>
								<td colspan="6" style="text-align: center !important;">
									<b>Domicilio de centro de trabajo</b>
								</td>
							</tr>
							<tr>
								<td colspan="6" style="text-align: center !important;">${sujetoTramite.cntroTrabajo.descripcion}</td>
							</tr>
							<tr>
								<td colspan="3" style="text-align: center !important;">
									<span class="required">*</span>
									<b>Tel&eacute;fono Fijo (Principal)</b>
								</td>
								<td colspan="3" style="text-align: center !important;">
									<b>Tel&eacute;fono Fijo (Secundario)</b>
								</td>
							</tr>
							<tr>
								<td style="text-align: center !important;">
									<input class="wauto-input ns_" type="text" value="${ ctLada }" id="ctLada" onkeydown="validarNumeros(event)"
										onkeypress="validarNumeros(event)" onkeyup="validarNumeros(event)" maxlength="3" size="4">
									<br />
									(Lada)
								</td>
								<td style="text-align: center !important;">
									<input class="wauto-input ns_" type="text" value="${ ctTelefonoFijo }" id="ctTelefonoFijo" maxlength="8" size="8"
										onkeydown="validarNumeros(event)" onkeypress="validarNumeros(event)" onkeyup="validarNumeros(event)">
									<br />
									(N&uacute;mero)
								</td>
								<td style="text-align: center !important;">
									<input class="wauto-input ns_" type="text" value="${ ctExtension }" id="ctExtension" maxlength="6" size="6"
										onkeydown="validarNumeros(event)" onkeypress="validarNumeros(event)" onkeyup="validarNumeros(event)">
									<br />
									(Extensi&oacute;n)
								</td>
								<td style="text-align: center !important;">
									<input class="wauto-input ns_" type="text" value="${ ctLada2 }" id="ctLada2" maxlength="3" size="4"
										onkeydown="validarNumeros(event)" onkeypress="validarNumeros(event)" onkeyup="validarNumeros(event)">
									<br />
									(Lada)
								</td>
								<td style="text-align: center !important;">
									<input class="wauto-input ns_" type="text" value="${ ctTelefonoFijo2 }" id="ctTelefonoFijo2" maxlength="8" size="8"
										onkeydown="validarNumeros(event)" onkeypress="validarNumeros(event)" onkeyup="validarNumeros(event)">
									<br />
									(N&uacute;mero)
								</td>
								<td style="text-align: center !important;">
									<input class="wauto-input ns_" type="text" value="${ ctExtension2 }" id="ctExtension2" maxlength="6" size="6"
										onkeydown="validarNumeros(event)" onkeypress="validarNumeros(event)" onkeyup="validarNumeros(event)">
									<br />
									(Extensi&oacute;n)
								</td>
							</tr>
							<tr>
								<td colspan="3" style="text-align: center !important;">
									<div id="telPrincipalError"></div>
								</td>
								<td colspan="3" style="text-align: center !important;">
									<div id="telSecuendarioError"></div>
								</td>
							</tr>
							<tr>
								<td colspan="6" style="text-align: center !important;">
									<span class="required">*</span>
									<b>Correo Electr&oacute;nico</b>
								</td>
							</tr>
							<tr>
								<td colspan="6" style="text-align: center !important;">
									<input class="wauto-input ns_" type="text" style="text-transform: none !important;" value="${ctCorreoElectronico}"
										id="ctCorreoElectronico" maxlength="50" size="100">
								</td>
							</tr>
							<tr>
								<td colspan="6" style="text-align: center !important;">
									<div id="correoElectronicoError"></div>
								</td>
							</tr>
						</table>
					</form:form>
				</div>
			</c:when>
			<c:otherwise>
				<div class="container-fluid empty-state">
					<div class="row">
						<!-- Imagen -->
						<div class="col-xs-12 imagen">
							<i class="glyphicon glyphicon-remove-sign"></i>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 titulo">LO SENTIMOS HA OCURRIDO EL SIGUIENTE ERROR:</div>
					</div>
					<div class="row">
						<div class="alert alert-danger alert-danger-riss">${error}</div>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
	</div>

	<div class="pie row">
		<div class="col-sm-4">
			<div style="float: left; padding: 11px 0px;"><span class="required" id="labelCamposObligatoriosGeneral">*</span><spring:message code="label.camposObligatorios"/></div>
		</div>
		<div class="col-sm-8">
			<div class="pull-right">
			<button class="btn btn-default" id="cerrarWizard" onclick="uid_call('imss.patrones.modificacion.centro_trabajo.contenido.btn_cerrar','clickin')">Cerrar</button>
			<c:if test="${empty error}">
				
				<div class="btn-group dropup">
					<a href="#" class="btn btn-primary">
						<spring:message code="label.menus.opciones" />
					</a>
					<a href="#" data-toggle="dropdown" class="btn btn-primary dropdown-toggle">
						<span class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						<li>
							<a id="finalizarTramiteDatosContacto" onclick="uid_call('imss.patrones.modificacion.centro_trabajo.contenido.btn_finalizarTramite','clickin')">
								<i class="glyphicon glyphicon-ok"></i>
								<spring:message code="wizard.button.finalizarTramite"></spring:message>
							</a>
						</li>
						<li>
							<a id="cancelarTramite" onclick="uid_call('imss.patrones.modificacion.centro_trabajo.contenido.btn_cancelarTramite','clickin')">
								<i class="glyphicon glyphicon-trash"></i>
								<spring:message code="wizard.button.cancelarTramite"/>
							</a>
						</li>
					</ul>
				</div>
			</c:if>
			</div>
		</div>
	</div>
</div>

<div id="dialogoMensajes">
	<p><span id="textoMensaje"></span></p>
</div>

<div id="dialog-confirm" title="Mensaje">
	<p>
		<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>
		<label id="mensajeDialogo"></label>
	</p>
</div>

<div id="dialog-confirm-cancelar" title="Confirmar cancelaci&oacute;n de solicitud">
	<p>
		<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>
		¿Desea cancelar la solicitud pendiente con folio:
		<strong>${folioSolicitud}</strong>
		?
	</p>
</div>

<script language="JavaScript1.2" src="${staticResourcesPath}/js/comscore/Form.js"></script>