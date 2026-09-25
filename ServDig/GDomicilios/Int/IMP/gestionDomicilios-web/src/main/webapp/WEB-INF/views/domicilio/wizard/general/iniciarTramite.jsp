<%@ include file="../../../general/taglibs.jsp"%>

<script type="text/javascript">
	var desOrigen = '${origenApp}';
	
	var codigoTipoSolicitud = "${codigoTipoSolicitud}";
	var arrayCodigoTipoTramite = ${codigoTipoTramite};
	var descripcionTipoSolicitud = '${descripcionTipoSolicitud}';

	if(desOrigen == 2){
		var datosEntradaFirma = {
			fechaElectronica : '${datosFirmaElectronica.fechaElectronicaFormateada}',
			nombreCompleto : '${datosFirmaElectronica.nombreCompleto}',
			registroPatronal : '${datosFirmaElectronica.registroPatronal}',
			rfc : '${datosFirmaElectronica.rfc}',
			curp : '${datosFirmaElectronica.curp}'
		};
	}
	
</script> 

<script type="text/javascript" src="/portalDerechohabiente-ciudadano/static/resources/js/delta/common/combosUmfMedicoConsultorio.js"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/domicilios/wizard/general/domicilios.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/domicilios/wizard/general/funcionesComunes.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/domicilios/wizard/general/datosUmf.js" htmlEscape="true" />"></script>

<style>

table.tbl-domIniciarTramite input{
	width: 100%;
}

</style>

<div class="contenedor col-sm-12">

	<input type="hidden" value="${domicilio.codigoPostal.codigoPostal}" id="cpBusquedaUmf"/>
	<input type="hidden" value="${umfSeleccionada}" id="umfSeleccionada"/>
	<input type="hidden" value="${turnoSeleccionado}" id="turnoSeleccionado"/>
	<input type="hidden" value="${consultorioSeleccionado}" id="consultorioSeleccionado"/>
	<input type="hidden" value="${requiereDocs && origenApp != 6? 1 : 0}" id="requiereDocs"/>
	
	<input type="hidden" id="idSolicitud" value="${solicitudRegistro.solicitudId}" />
	<input type="hidden" id="hdnFolioSolicitud" value="${solicitudRegistro.noFolioSolicitud}" />
	<input type="hidden" id="hdnFolioSolicitudCifrado" value="${solicitudRegistro.solicitudFolioHashed}"/>
	<input type="hidden" id="contenidoFirmar" value="${contenidoFirmar}" />
	<input type="hidden" id="idTipoTramite" value="${KEY_DOMICILIOS_ID_TIPO_TRAMITE}" />
	
	
	<div class="contenido row">
		<div class="col-sm-12">
		<c:if test="${not empty error}">
			<div class="alert alert-danger">
				<button type="button" class="close" data-dismiss="alert"
					onclick="uid_call('imss.gestion.domicilios.general.btn_cerrar','clickin')">×</button>
				<strong>Error: </strong>${error}
			</div>
		</c:if>
		<div class="alert alert-danger" style="display: none" id="divErrorCampos"></div>
		<c:if test="${empty error}">
			
			<!-- Inicio Mensaje de folio de solicitud -->
			<div class="alert alert-info">
				<c:choose>
					<c:when test="${!isRetomar}">
						<spring:message code="label.solicitud.iniciada" arguments="${solicitudRegistro.noFolioSolicitud}"/>
					</c:when>
					<c:otherwise>
						<spring:message code="label.solicitud.retomando" arguments="${solicitudRegistro.noFolioSolicitud}"/>
					</c:otherwise>
				</c:choose>
			</div>
			<!-- Fin Mensaje de folio de solicitud -->
			
			<form:form id="formRegistro" method="post" modelAttribute="domicilio" role="form">

				<div id="seccionDomicilioRegistro" class="table_form">

					<div class="separadorseccion"><span> Domicilio </span></div>
					
					<table width="100%" class="table table-striped table-bordered tbl-domIniciarTramite">
						<tr>
							<td class="label_patrones" style="width: 230px !important;"><spring:message code="label.codigoPostal"/><span class="required">*</span>:</td>
							<td class="label_patrones" colspan="2">Estado<span class="required">*</span>:</td>
							<td class="label_patrones" colspan="2">Municipio o Alcald&iacute;a<span class="required">*</span>:</td>
						</tr>
						<tr>
							<td class="label_patrones_data">
								<form:input readonly="true" path="codigoPostal.codigoPostal" cssClass="campoObligatorio" />
								<form:hidden path="vialidadPrimaria.tipoVialidad.descripcion" />	
								<span style="display: none" class="error"></span>
							</td>
							<td class="label_patrones_data" colspan="2">
								<form:input readonly="true" path="asentamiento.localidad.municipio.entidadFederativa.nombre"  cssClass="campoObligatorio"/> 
								<form:hidden path="asentamiento.localidad.municipio.entidadFederativa.clave" />
								<span style="display: none" class="error"></span>
							</td>
							<td class="label_patrones_data" colspan="2">
								<form:input readonly="true" path="asentamiento.localidad.municipio.nombre" cssClass="campoObligatorio"/> 
								<form:hidden path="asentamiento.localidad.municipio.clave" />
								<span style="display: none" class="error"></span>
							</td>
						</tr>
						<tr>
							<td class="label_patrones" colspan="2">Localidad<span class="required">*</span>:</td>
							<td class="label_patrones" colspan="3">Colonia<span class="required">*</span>:</td>
						</tr>
						<tr>
							<td class="label_patrones_data" colspan="2">
								<form:input readonly="true" path="asentamiento.localidad.nombre"  cssClass="campoObligatorio"/> 
								<form:hidden path="asentamiento.localidad.clave" />
								<span style="display: none" class="error"></span>
							</td>
							<td class="label_patrones_data" colspan="3">
								<form:input readonly="true" path="asentamiento.nombre"  cssClass="campoObligatorio"/> 
								<form:hidden path="asentamiento.clave" />
								<span style="display: none" class="error"></span>
							</td>			
						</tr>
						<tr>
							<td class="label_patrones" colspan="3">Calle<span class="required">*</span>:</td>
							<td class="label_patrones">N&uacute;mero y/o Letra exterior<span class="required">*</span>:</td>
							<td class="label_patrones">N&uacute;mero y/o Letra interior:</td>
											
						</tr>
						<tr>
							<td class="label_patrones_data" colspan="3">
								<form:input readonly="true" path="vialidadPrimaria.nombre" cssClass="campoObligatorio"/> 
								<span style="display: none" class="error"></span>
								<form:hidden path="vialidadPrimaria.clave" />
								<form:hidden path="vialidadPrimaria.tipoVialidad.clave" />
								
								<form:hidden path="calle"/>
								<form:hidden path="tipoBusquedaVialidad"/>
								
							</td>
							<td colspan="1">
								<form:input readonly="true" path="numExteriorAlf" cssClass="campoObligatorio"/>
								<span style="display: none" class="error"></span>
							</td>
							<td class="label_patrones" colspan="1" >
								<form:input readonly="true" path="numInteriorAlf"/>
							</td>	
						</tr>
						</table>
				</div>
			</form:form>
		
			<c:if test="${cambioClinica}">
				<form:form id="formUMF" method="post" modelAttribute="KEY_DOMICILIOS_TRAMITE_CLINICA" role="form">
					<div id="seccionUmfRegistro" style="width: 830px" class="table_form">
						<form:hidden path="paso"/>
						<form:hidden path="parentesco.idParentesco"/>
						<form:hidden path="tipoTramite.idTipoTramite"/>
						<form:hidden path="fisica.curp"/>
						<form:hidden path="tramiteId"/>
						<form:hidden path="indSeleccionMedico"/>
						<form:hidden path="fechaCambioMedico"/>
						<form:hidden path="idAsignacionNss"/>
						
						
						<div class="separadorseccion">
							<span>Datos de adscripci&oacute;n del integrante del grupo familiar</span>
						</div>
						
						<div id="mensajeIntegranteUmf" class="ui-widget" align="center" style="display:none;">
							<div class="ui-state-highlight ui-corner-all" style="margin-top: 12px; padding: 0 .5em;">
								<p>
									<i class="glyphicon glyphicon-info-sign"></i></span>
									<span id="mensajeUmfIntegrante"></span>
								</p>
							</div>
						</div>
						
						
						<table width="100%" class="table table-striped table-bordered" >
							<tr>
								<td>
									<form:label class="control-label" path="medicoEnTurno.unidadMedicaFamiliar.idUMF">
										&nbsp;UMF<span class="required" id="medicoEnTurno.unidadMedicaFamiliar.idUMFReq">*</span>:
									</form:label>
								</td>
								<td colspan="3">
									<form:hidden path="medicoEnTurno.idMedicoContultorioTurno" />	
									<select id="medicoEnTurno.unidadMedicaFamiliar.idUMF" name="medicoEnTurno.unidadMedicaFamiliar.idUMF" class="form-control">
									  	<option value="-1">--Selecciona por favor--</option>
									 </select>	
									 <span id="medicoEnTurno.unidadMedicaFamiliar.idUMFError" class="error hiddenElement"></span>
								</td>
							</tr>
							<tr>
								<td>
									<label class="control-label" for="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.descripcion">
										Delegaci&oacute;n :
									</label>
								</td>
								<td>
									<form:hidden path="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.id"/>
									<form:input path="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.descripcion" cssClass="form-control ns_"/>
								</td>
								<td>
									<label class="control-label" for="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.descripcion">
										Subdelegaci&oacute;n :
									</label>
								</td>
								<td>
									<form:hidden path="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.id"/>
									<form:input path="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.descripcion" cssClass="form-control ns_"/>
								</td>
							</tr>
							<tr>
								<td>
									<label class="control-label" for="medicoEnTurno.turno.idTurno">
									&nbsp;Turno<span class="required" id="medicoEnTurno.turno.idTurnoReq">*</span>:
									</label>
								</td>
								<td>
									<select id="medicoEnTurno.turno.idTurno" name="medicoEnTurno.turno.idTurno" class="form-control">
										 <option value="-1">--Selecciona por favor--</option>
									</select>
									
									<span id="medicoEnTurno.turno.idTurnoError" class="error hiddenElement"></span>
								</td>
								<td>
									<label class="control-label" for="medicoEnTurno.consultorio.idConsultorio">
										&nbsp;Consultorio<span class="required" id="medicoEnTurno.consultorio.idConsultorioReq" >*</span>:
									</label>
								</td>
								<td>
									<select id="medicoEnTurno.consultorio.idConsultorio" name="medicoEnTurno.consultorio.idConsultorio" class="form-control">
										 <option value="-1">--Selecciona por favor--</option>
									</select>
									
									<span id="medicoEnTurno.consultorio.idConsultorioError" class="error hiddenElement"></span>
								</td>
							</tr>
							<tr>
								<td>
									<label class="control-label" for="medicoEnTurno.medicoFamiliar.idMedicoFamiliar">
										&nbsp;M&eacute;dico :
									</label>
								</td>
								<td colspan="3">
									<form:hidden path="medicoEnTurno.medicoFamiliar.idMedicoFamiliar"/>
									<form:input path="medicoEnTurno.medicoFamiliar.nombre" cssClass="form-control"/>
								</td>
							</tr>
							<tr>
								<td>
									<label class="control-label" for="medicoEnTurno.medicoFamiliar.noMatricula">
										Matr&iacute;cula :
									</label>
								</td>
								<td>
									<form:input path="medicoEnTurno.medicoFamiliar.noMatricula" cssClass="form-control ns_"/>
								</td>
								<td>
									<label class="control-label" for="medicoEnTurno.medicoEspecialidad.descripcion">
										Especialidad :
									</label>
								</td>
								<td>
									<form:hidden path="medicoEnTurno.medicoEspecialidad.idMedicoEspacialidad"/>
									<form:input path="medicoEnTurno.medicoEspecialidad.descripcion" cssClass="form-control ns_"/>
								</td>
							</tr>
						</table>
						
						<br>
					</div>
					
				</form:form>
			
			</c:if>
			
			<c:if test="${!cambioClinica}">
				<form:form id="formaDatosTramite" method="post" modelAttribute="KEY_DOMICILIOS_TRAMITE_CLINICA" role="form">
					<form:hidden path="paso"/>
					<form:hidden path="parentesco.idParentesco"/>
					<form:hidden path="tipoTramite.idTipoTramite"/>
					<form:hidden path="fisica.curp"/>
					<form:hidden path="tramiteId"/>
					<form:hidden path="indSeleccionMedico"/>
					<form:hidden path="fechaCambioMedico"/>
					<form:hidden path="idAsignacionNss"/>
				</form:form>
			</c:if>
			
			<c:if test="${requiereDocs && origenApp != 6}">
			<div class="separadorseccion"> <span> Documentos probatorios </span> </div>
			<div class="row">
				<div class="col-sm-12">
					<p>
						Este tr&aacute;mite requiere la captura de documentos probatorios,
						da clic en el bot&oacute;n "Captura de documentos probatorios"
						para proceder a la misma, no podr&aacute;s finalizar el
						tr&aacute;mite hasta completarla.<br> <br> 
						<a id="capturarDocumentos" class="btn btn-default">
							<i class="glyphicon glyphicon-file"></i> Captura de documentos probatorios
						</a>
					</p>
				</div>
			</div>
			</c:if>
		
		</c:if>
		</div>
	</div>
	
	<div class="row">
	<div class="col-sm-4" style="padding-top:10px"><span class="required" id="labelCamposObligatoriosGeneral">*</span> <spring:message code="label.campos.obli"/></div>
		<div class="col-sm-8 pull-right">
			<c:if test="${not empty error}">
				<button class="btn btn-secondary pull-right" id="cerrarWizard" onclick="uid_call('imss.gestion.domicilios.general.btn_cerrar','clickout')"><spring:message code="label.btn.cerrar"/></button>
			</c:if>
			<div class="btn-group dropup pull-right" style="margin-right:15px;">
				<a href="#" class="btn btn-primary"><spring:message code="label.menus.opciones" /></a> 
				<a href="#" data-toggle="dropdown" class="btn btn-primary dropdown-toggle"><span class="caret"></span></a>
				<ul class="dropdown-menu">
					
					<!-- Si las banderas KEY_CIRCUNSCRIPCION_FORANEA(Para beneficiario) y KEY_AUTORIZACION_PERMANENTE (Para asegurado) son falsas para cuando
					la clinica esta fuera de la circunscripcion se manda un mensaje indicando que no se puede finalizar el tramite-->
					
					<c:if test="${empty error}">
						<li><a id="finalizarTramite"><i class="glyphicon glyphicon-ok"></i><spring:message code="label.acciones.finalizar"/></a></li>
						<li><a id="btnGuardarTramite"><i class="glyphicon glyphicon-download-alt"></i><spring:message code="label.acciones.guardar"/></a></li>
						<li><a id="guardarCerrarTramite"><i class="glyphicon glyphicon-trash"></i><spring:message code="label.acciones.guardarCerrar"/></a></li>
					</c:if>
					<li><a id="btnCancelarTramite"><i class="glyphicon glyphicon-trash"></i><spring:message code="label.acciones.cancelar"/></a></li>
					
				</ul>
				
			</div>
		</div>
	</div>
	
</div>

<%@ include file="wizardPie.jsp"%>

<div id="dialog-confirm-existentes" title="Mensaje confirmaci&oacute;n">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span> <label
			id="mensajeDialogoExistentes"></label>
	</p>
</div>

<div id="dialog-finalizar" title="Mensaje confirmaci&oacute;n">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span> <label
			id="mensajeDialogoFinalizar"></label>
	</p>
</div>
<script language="JavaScript1.2" src="${staticResourcesPath}/js/comscore/Form.js"></script>