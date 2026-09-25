<%@ include file="../../../../general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/common/commonMethods.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/modificacion/patron/altaPatronal/domicilioCentroTrabajo.js?v=1" htmlEscape="true" />"></script>

<c:set var="tipoContactoTelefonoFijo" value="<%=TipoMedioContacto.TIPO_TELEFONO_FIJO%>" />
<c:set var="tipoContactoCorreoElectronico" value="<%=TipoMedioContacto.TIPO_CORREO_ELECTRONICO%>" />

<script>
	var idSolicitud = 0;
	<c:if test="${idSolicitud != null}">
		idSolicitud = ${idSolicitud};
	</c:if>
		
	var telefonoPrincipalCompleto  = '${ctTelefonoFijo}';
	var telefonoSecundarioCompleto = '${ctTelefonoFijo2}';
</script>

<style>
label {
	display: inline;
}

.table_form table {
	margin: 15px auto;
}

.table_form table tr td {
	padding: 5px 10px;
}

textarea {
	height: 100%;
}

input, textarea, .uneditable-input {
    width: auto;
}

table.tbl-domCentroTrabajo input{
	width: 100%;
}

</style>

<div class="contenedor col-sm-12">

	<div class="contenido row">
		<div class="col-sm-12">
		<c:if test="${not empty solicitudTramite.errorFormGeneral}">
			<div class="alert alert-danger">
				<button type="button" class="close" data-dismiss="alert">×</button>
				<strong>Error: </strong>${solicitudTramite.errorFormGeneral}
			</div>
		</c:if>

		<c:if test="${not empty folioSolicitud}">
			<input type="hidden" id="hdnFolioSolicitud" value="${folioSolicitud}"/>
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
		</c:if>
		
		<div class="alert alert-danger" style="display: none" id="divErrorCampos"></div>

		<c:if test="${empty solicitudTramite.errorFormGeneral}">
		<form:form id="domicilioCentroTrabajoForm" method="post" modelAttribute="sujetoTramite">
			<div id="seccionCentroTrabajo" class="table_form">
				<div class="separadorseccion">
					<span>
						Centro de trabajo
						<!--  spring:message code="titulo.datos.generales.patron"/ -->
					</span>
				</div>

				<div class="row">
					<div id="wrapperDomicilio" class="col-sm-8 col-sm-offset-2">
						<div class="alert alert-info">
							<i class="glyphicon glyphicon-exclamation-sign" style="margin-right: 20px;"></i>
							
							Selecciona tu nuevo domicilio
							<a href="javascript:fnOpenBuscarDomicilio();" class="alert-link" onclick="uid_call('imss.patrones.alta_patronal.domicilio.link_domicilio','clickout')"> aqu&iacute;</a><span class="required">*</span>
						</div>
					</div>
				</div>
				<table width="100%" class="table table-striped table-bordered tbl-domCentroTrabajo" >	
				<tr>
						<td class="label_patrones" style="width: 230px !important;"><spring:message code="label.codigo.postal"></spring:message><span class="required">*</span>:</td>
						<td class="label_patrones" colspan="2"><spring:message code="label.entidad.federativa"></spring:message><span class="required">*</span>:</td>
						<td class="label_patrones" colspan="2"><spring:message code="label.municipio"></spring:message><span class="required">*</span>:</td>
					</tr>
					<tr>
						<td class="label_patrones_data">
							<form:input readonly="true" path="cntroTrabajo.codigoPostal.codigoPostal" cssClass="campoObligatorio ns_"/>
							<form:hidden path="cntroTrabajo.vialidadPrimaria.tipoVialidad.descripcion" />	
							<span style="display: none" class="error"></span>
						</td>
						<td class="label_patrones_data" colspan="2">
							<form:input readonly="true" path="cntroTrabajo.asentamiento.localidad.municipio.entidadFederativa.nombre" cssClass="campoObligatorio ns_"/> 
							<form:hidden path="cntroTrabajo.asentamiento.localidad.municipio.entidadFederativa.clave" />
							<span style="display: none" class="error"></span>
						</td>
						<td class="label_patrones_data" colspan="2">
							<form:input readonly="true" path="cntroTrabajo.asentamiento.localidad.municipio.nombre" cssClass="campoObligatorio ns_"/> 
							<form:hidden path="cntroTrabajo.asentamiento.localidad.municipio.clave" />
							<span style="display: none" class="error"></span>
						</td>
					</tr>
					<tr>
						<td class="label_patrones" colspan="2">Localidad<span class="required">*</span>:</td>
						<td class="label_patrones" colspan="3">Colonia<span class="required">*</span>:</td>
					</tr>
					<tr>
						<td class="label_patrones_data" colspan="2">
							<form:input readonly="true" path="cntroTrabajo.asentamiento.localidad.nombre" cssClass="campoObligatorio ns_"/> 
							<form:hidden path="cntroTrabajo.asentamiento.localidad.clave" />
							<span style="display: none" class="error"></span>
						</td>
						<td class="label_patrones_data" colspan="3">
							<form:input readonly="true" path="cntroTrabajo.asentamiento.nombre" cssClass="campoObligatorio ns_"/> 
							<form:hidden path="cntroTrabajo.asentamiento.clave" />
							<span style="display: none" class="error"></span>
						</td>			
					</tr>
					<tr>
						<td class="label_patrones">Calle<span class="required">*</span>:</td>
						<td class="label_patrones">N&uacute;mero exterior<span class="required">*</span>:</td>
						<td class="label_patrones">Letra exterior:</td>
						<td class="label_patrones">N&uacute;mero interior:</td>
						<td class="label_patrones">Letra interior:</td>				
					</tr>
					<tr>
						<td class="label_patrones_data">
							<form:input readonly="true" path="cntroTrabajo.vialidadPrimaria.nombre" cssClass="campoObligatorio ns_"/> 
							<span style="display: none" class="error"></span>
							<form:hidden path="cntroTrabajo.vialidadPrimaria.clave" />
							<form:hidden path="cntroTrabajo.vialidadPrimaria.tipoVialidad.clave" />
							
							<form:hidden path="cntroTrabajo.calle"/>
							<form:hidden path="cntroTrabajo.tipoBusquedaVialidad"/>
							
							<!-- Atributos de domicilio carretera -->
							<form:hidden path="cntroTrabajo.domicilioCarretera.terminoGeneral.descripcion"/>
							<form:hidden path="cntroTrabajo.domicilioCarretera.terminoGeneral.clave"/>
							<form:hidden path="cntroTrabajo.domicilioCarretera.derechoTransito.descripcion"/>
							<form:hidden path="cntroTrabajo.domicilioCarretera.derechoTransito.clave"/>
							<form:hidden path="cntroTrabajo.domicilioCarretera.origen"/>
							<form:hidden path="cntroTrabajo.domicilioCarretera.destino"/>
							<form:hidden path="cntroTrabajo.domicilioCarretera.administracion.descripcion"/>
							<form:hidden path="cntroTrabajo.domicilioCarretera.administracion.clave"/>
							<form:hidden path="cntroTrabajo.domicilioCarretera.cadenamiento"/>
							<form:hidden path="cntroTrabajo.domicilioCarretera.codigoCarretera"/>
							
							<!-- Atrbutos de domicilio camino -->
							<form:hidden path="cntroTrabajo.domicilioCamino.terminoGeneral.descripcion"/>
							<form:hidden path="cntroTrabajo.domicilioCamino.terminoGeneral.clave"/>
							<form:hidden path="cntroTrabajo.domicilioCamino.margen.descripcion"/>
							<form:hidden path="cntroTrabajo.domicilioCamino.margen.clave"/>
							<form:hidden path="cntroTrabajo.domicilioCamino.origen"/>
							<form:hidden path="cntroTrabajo.domicilioCamino.destino"/>
							<form:hidden path="cntroTrabajo.domicilioCamino.cadenamiento"/>
						</td>
						<td colspan="1">
							<form:input readonly="true" path="cntroTrabajo.numExterior1" cssClass="campoObligatorio ns_"/>
							<span style="display: none" class="error"></span>
						</td>
						<td colspan="1">
							<form:input readonly="true" path="cntroTrabajo.numExteriorAlf"/>
						</td>
						<td class="label_patrones" colspan="1" >
							<form:input readonly="true" path="cntroTrabajo.numInterior"/>
						</td>
						<td class="label_patrones" colspan="1" >
							<form:input readonly="true" path="cntroTrabajo.numInteriorAlf"/>
						</td>	
					</tr>
					<tr>
						<td class="label_patrones" colspan="2">Entre la calle:</td>
						<td class="label_patrones" colspan="3">Y la calle:</td>
					</tr>
					<tr>
						<td class="label_patrones_data" colspan="2">
							<form:input readonly="true" path="cntroTrabajo.vialidadReferenciaPrimaria.nombre"/> 									
							<form:hidden path="cntroTrabajo.vialidadReferenciaPrimaria.clave" />
							<form:hidden path="cntroTrabajo.vialidadReferenciaPrimaria.tipoVialidad.clave" />
						</td>
						<td class="label_patrones_data" colspan="3">
							<form:input readonly="true" path="cntroTrabajo.vialidadReferenciaSecundaria.nombre"/> 
							<form:hidden path="cntroTrabajo.vialidadReferenciaSecundaria.clave" />
							<form:hidden path="cntroTrabajo.vialidadReferenciaSecundaria.tipoVialidad.clave" />
							<form:hidden path="cntroTrabajo.vialidadReferenciaPosterior.nombre" maxlength="14" /> 
							<form:hidden path="cntroTrabajo.vialidadReferenciaPosterior.clave" />
							<form:hidden path="cntroTrabajo.vialidadReferenciaPosterior.tipoVialidad.clave" />
						</td>
					</tr>
				</table>
				
				<div class="separadorseccion">
					<span>
						Datos de contacto del centro de trabajo
					</span>
				</div>
				<!-- 
				<table width="100%" cellpadding="0;" cellspacing="0" style="margin: 0px !important; border-right: 0px none; border-left: 0px none;">
					<tr>					
						<td class="label_patrones" style="width: 200px">										
							Tel&eacute;fono Fijo 1
						</td>
						<td class="label_patrones" style="width: 200px">
							Tel&eacute;fono Fijo 2
						</td>
						<td class="label_patrones">
							Correo Electr&oacute;nico
						</td>
					</tr>
					<tr>
						<td>
							<input type="text" id="ctTelefonoFijo" name="cntroTrabajo.mediosContacto[0].desFormaContacto" value="${ ctTelefonoFijo }" maxlength="12"  size="15"
							onkeydown="validarNumeros(event)" onkeypress="validarNumeros(event)" onkeyup="validarNumeros(event)"/> 
							<input type="hidden" name="cntroTrabajo.mediosContacto[0].tipoMedioContacto.idTipoMedioContacto" value="${tipoContactoTelefonoFijo}" />
							<input type="hidden" name="cntroTrabajo.mediosContacto[0].idVista" value="1" />
						</td>
						<td>
							<input type="text" id="ctTelefonoFijo2" name="cntroTrabajo.mediosContacto[1].desFormaContacto"  maxlength="12"  size="15" value="${ ctTelefonoFijo2 }" 
							onkeydown="validarNumeros(event)" onkeypress="validarNumeros(event)" onkeyup="validarNumeros(event)"/> 
							<input type="hidden" name="cntroTrabajo.mediosContacto[1].tipoMedioContacto.idTipoMedioContacto" value="${tipoContactoTelefonoFijo}" />
							<input type="hidden" name="cntroTrabajo.mediosContacto[1].idVista" value="2" />
						</td>
						<td>
							<input type="text" id="ctCorreoElectronico" name="cntroTrabajo.mediosContacto[2].desFormaContacto"   maxlength="50" size="60" value="${ ctCorreoElectronico }" style="text-transform: none !important;" />
							<input type="hidden" name="cntroTrabajo.mediosContacto[2].tipoMedioContacto.idTipoMedioContacto" value="${tipoContactoCorreoElectronico}" />
							<input type="hidden" name="cntroTrabajo.mediosContacto[2].idVista" value="3" />
						</td>
					</tr>
				</table>
				 -->
				<input type="hidden" id="idTipoContactoTelefonoFijo" value="${tipoContactoTelefonoFijo}" />
				<input type="hidden" id="idTipoContactoCorreo" value="${tipoContactoCorreoElectronico}" />
				<table width="100%" class="table table-striped table-bordered" >
					<tr>
						<td class="label_patrones" colspan="3" style="width: 200px" align="center">
							Tel&eacute;fono fijo (principal)<span class="required" id="ctTelefonoFijoReq">*</span>:</td>
						<td class="label_patrones" style="width: 200px" align="center" colspan="3">
							Tel&eacute;fono fijo (secundario):</td>
					</tr>
					<tr></tr>
					<tr>
						<td>Lada:</td>
						<td>Tel&eacute;fono:</td>
						<td>Extensi&oacute;n:</td>
						<td>Lada:</td>
						<td>Tel&eacute;fono:</td>
						<td>Extensi&oacute;n:</td>
					</tr>
					<tr>
						<td>
							<input type="text" id="ctLada" class="ns_"
									value="${ ctLada }" maxlength="3" size="4"
									onkeydown="validarNumeros(event)"
									onkeypress="validarNumeros(event)"
									onkeyup="validarNumeros(event)" />
						</td>
						<td>
							<input type="text" id="ctTelefonoFijo" class="ns_"
									value="${ ctTelefonoFijo }" maxlength="8" size="8"
									onkeydown="validarNumeros(event)"
									onkeypress="validarNumeros(event)"
									onkeyup="validarNumeros(event)" />
							<span style="display: none" class="error" id="ctTelefonoFijoError"></span>
						</td>
						<td>
							<input type="text" id="ctExtension" class="ns_"
									value="${ ctExtension }" maxlength="6" size="6"
									onkeydown="validarNumeros(event)"
									onkeypress="validarNumeros(event)"
									onkeyup="validarNumeros(event)" />
							<input type="hidden" id="desTelefonoPrimario" name="cntroTrabajo.mediosContacto[0].desFormaContacto" />
							<input type="hidden" name="cntroTrabajo.mediosContacto[0].tipoMedioContacto.idTipoMedioContacto" value="${tipoContactoTelefonoFijo}" />
							<input type="hidden" name="cntroTrabajo.mediosContacto[0].idVista" value="1" />
						</td>
						<td>
							<input type="text" id="ctLada2" class="ns_"
									value="${ ctLada2 }" maxlength="3" size="4"
									onkeydown="validarNumeros(event)"
									onkeypress="validarNumeros(event)"
									onkeyup="validarNumeros(event)" />
						</td>
						<td>
							<input type="text" id="ctTelefonoFijo2" class="ns_"
									value="${ ctTelefonoFijo2 }" maxlength="8" size="8" 
									onkeydown="validarNumeros(event)"
									onkeypress="validarNumeros(event)"
									onkeyup="validarNumeros(event)" />
						</td>
						<td>
							<input type="text" id="ctExtension2" class="ns_"
									value="${ ctExtension2 }" maxlength="6" size="6"
									onkeydown="validarNumeros(event)"
									onkeypress="validarNumeros(event)"
									onkeyup="validarNumeros(event)" />
							<input type="hidden" id="desTelefonoSecundario" name="cntroTrabajo.mediosContacto[1].desFormaContacto" />
							<input type="hidden" name="cntroTrabajo.mediosContacto[1].tipoMedioContacto.idTipoMedioContacto" value="${tipoContactoTelefonoFijo}" />
							<input type="hidden" name="cntroTrabajo.mediosContacto[1].idVista" value="2" />
						</td>
					</tr>
					<tr>
						<td class="label_patrones" colspan="3" style="text-align: right !important;">
							Correo electr&oacute;nico<span class="required" id="ctCorreoElectronicoReq">*</span>:
						</td>
						<td colspan="3">
							<input type="text" id="ctCorreoElectronico" class="ns_"
							maxlength="50" size="60" value="${ ctCorreoElectronico }" name="cntroTrabajo.mediosContacto[2].desFormaContacto"
							style="text-transform: none !important;" />
							<input type="hidden" name="cntroTrabajo.mediosContacto[2].tipoMedioContacto.idTipoMedioContacto" value="${tipoContactoCorreoElectronico}" />
							<input type="hidden" name="cntroTrabajo.mediosContacto[2].idVista" value="3" />
							<span style="display: none" class="error" id="ctCorreoElectronicoError"></span>
						</td>
					</tr>
				</table>
				
				
				
			</div>
		</form:form>
		</c:if>
		</div>
	</div>

	<div class="pie row">
		<div class="col-sm-4">
			<div style="float: left; padding: 11px 0px;"><span class="required" id="labelCamposObligatoriosGeneral">*</span><spring:message code="label.camposObligatorios"/></div>
		</div>
		<div class="col-sm-8">
			<div class="pull-right">
			<c:if test="${empty solicitudTramite.errorFormGeneral}">
				<a id="siguientePaso" class="btn btn-primary" onclick="uid_call('imss.patrones.alta_patronal.domicilio.btn_siguiente','clickin')"><i class="glyphicon glyphicon-step-forward"></i>Siguiente</a>
				
				<div class="btn-group dropup">
					<a href="#" class="btn btn-primary"><spring:message code="label.menus.opciones" /></a> <a href="#"
						data-toggle="dropdown" class="btn btn-primary dropdown-toggle"><span
						class="caret"></span></a>
					<ul class="dropdown-menu pull-right">
						<li><a id="guardarTramite" onclick="uid_call('imss.patrones.alta_patronal.domicilio.link_guardar','clickin')"><i class="glyphicon glyphicon-download-alt"></i><spring:message code="wizard.button.guardarTramite"/></a></li>
						<li><a id="guardarCerrarTramite" onclick="uid_call('imss.patrones.alta_patronal.domicilio.link_guardarCerrar','clickin')"><i class="glyphicon glyphicon-download-alt"></i><spring:message code="wizard.button.guardarCerrar"/></a></li>
						<li><a id="cancelarTramite" onclick="uid_call('imss.patrones.alta_patronal.domicilio.link_cancelar','clickin')"><i class="glyphicon glyphicon-trash"></i><spring:message code="wizard.button.cancelarTramite"/></a></li>
					</ul>
				</div>
				
				
			</c:if>
			</div>
		</div>

	</div>
</div>

<!-- Divs para dialogos de mensajes -->
<div id="dialog-confirm-cancelar" title="Confirmar cancelaci&oacute;n de solicitud">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>
			<spring:message code="label.cancelar.solicitud" arguments="${folioSolicitud}"/>
	</p>
</div>

<div id="dialog-confirm" title="Mensaje">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>
			<label id="mensajeDialogo"></label>
	</p>
</div>

<div id="dialogoConfirmacion">
	<p>
		<span id="textoConfirmacion"></span>
	</p>
</div>

<div id="dialogoMensajes">
	<p><span id="textoMensaje"></span></p>
</div>

<div id="domiciliosComponent"></div>

<script language="JavaScript1.2" src="${staticResourcesPath}/js/comscore/Form.js"></script>