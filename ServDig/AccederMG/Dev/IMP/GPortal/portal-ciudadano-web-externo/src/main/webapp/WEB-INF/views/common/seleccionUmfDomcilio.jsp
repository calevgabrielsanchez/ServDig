<%@ include file="../general/taglibs.jsp"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.enums.TipoTramiteEnum"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="tipoTramiteModifDatosGrales" value="<%=TipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES.getCodigo()%>" />

<script type="text/javascript">
			history.go(1);
</script>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/home/home.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="/gestionDomicilios-web/static/resources/js/delta/domicilios/recortado/DomicilioRecortadoCtrl.js"></script>
<script type="text/javascript" src="/portalDerechohabiente-web/static/resources/js/delta/common/combosUmfMedicoConsultorio.js"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/common/seleccionDomicilio.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/common/seleccionUmf.js" htmlEscape="true" />"></script>

<style>
.ui-widget-overlay {
	position: fixed;
}

span.error-custom {
	float: none !important;
	vertical-align: super;
}

.filtros-busqueda .row {
	margin-bottom: 12px;
}

.filtros-busqueda .filtros .etiqueta {
	width: 25%;
}

input[type="text"] {
	margin-bottom: 0px;
	text-transform: uppercase;
}

.alert-temp {
	background-color: #f8f8f8;
	border-color: #d9d9d9;
	color: #black;
}
</style>

<div>
	<input type="hidden" id="cveTipoTramite" value="${keyCveTipoTramite}"/>
	<input type="hidden" id="tipoTramite" value="${tramite}"/>
	<input type="hidden" id="codigoPostalSeleccionado" value=""/>
	<input type="hidden" id="idAsignacionHidde" value = "${keyAsignacionNSSSession.idAsignacionNSS}"/>
	<c:choose>
		<c:when test="${error}">
			<div class="alert alert-danger">
				<strong>Error: </strong>${error}
			</div>
		</c:when>
		<c:otherwise>
			<ol class="breadcrumb">
			  <li><a href="#" class="salir"><i class="icon icon-home"></i></a></li>
			  <li><a href="#" class="salir">Tr&aacute;mites</a></li>
			  <li class="active"><spring:message code="${tituloTramite}" /></li>
			</ol>
			<div class="row"> 
				<div class="col-sm-7" style="margin-top:0px">
					<h3><spring:message code="${tituloTramite}" /></h3>
				</div>
  				<div class="col-sm-5">
  					<div class="pull-right" style="border: 1px solid #ccc; padding: 10px">
						<span style="margin-right: 10px; float: left">
  							<strong>Bienvenido: </strong><br>
							${ciudadano.curp}
							<br>
							${ciudadano.nombreCompleto}
							<c:if test="${tramite eq 'cambioClinica' || tramite eq 'cambioClinicaD'}">
							<br>
							<strong>UMF:</strong> ${keyDatosAseguradoSession.medicoEnTurno.unidadMedicaFamiliar.descripcion}
							</c:if>
						</span>
	            		<button style="float: right" class="btn btn-link salir" id="logout">Salir</button>
					</div>
 			 	</div>
			</div>
			
			<jsp:include page="../common/paginadorClinica.jsp">
				<jsp:param name="paso" value="2" />
				<jsp:param name="tipoTramite" value="${keyCveTipoTramite}"/>
			</jsp:include>
			<div class="alert alert-danger" style="display: none" id="divErrorCampos"></div>
			<%-- En caso de ser registro de hijos--%>
			<c:if test="${keyCveTipoTramite == 48}">
			<h4>Datos del beneficiario</h4>
        	<hr class="red" style="margin-bottom: 30px">
			<form id="formBusquedaPersona" class="form-horizontal" role="form" action="#">
			<div class="alert alert-danger" style="display:none" id="divErrorBusquedaPersona"></div>
			<div class="row m-t-md">
				<div class="col-sm-12">
					<span>
						Proporciona los datos del beneficiario
					</span>
				</div>
			</div>
			<div class="row m-t-xl">
				<div class="col-sm-6">
					<div class="form-group">
						<label class="col-md-4" for="curp">
							<spring:message code="label.curp"/><span class="required">*</span>:
						</label>
						<div class="col-md-8">
							<input class="form-control" type="text" id="curp" name="curp"/>
							<span id="curpError" class="error hiddenElement"></span>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-6" style="margin-top:10px">
						    * <spring:message code="label.camposObligatorios"/>.
						</div>
						<div class="col-sm-6 text-right">
							<button class="btn btn-primary" type="button" id="buscarPersona"><span class="glyphicon glyphicon-search"></span>Buscar persona</button>
						</div>
					</div>
				</div>
				<div class="col-sm-6">
				</div>
			</div>
			</form>
			<div id="infoPersona" style="display:none">

					<div class="row">
						<div class="col-md-6 col-sm-12">
							<div class="alert alert-temp col-sm-12" style="text-align: left;">
								<spring:message code="label.registro.hijos.datos"/>:
								<br><br>
								<strong><spring:message code="label.curp"/>: </strong><span id="infoCurp" class="limpiable"></span>
								<br> <strong><spring:message code="tramite.detalle.nombre"/>: </strong><span id="infoNombre" class="limpiable"></span> 
								<span id="infoPApellido" class="limpiable"> 
								</span> <span id="infoSApellido"class="limpiable"></span> 
								<br> 
								<strong><spring:message code="tramite.detalle.fNacimiento"/>: </strong><span id="infoFNacimiento" class="limpiable"></span>
								<br> 
								<strong><spring:message code="tramite.detalle.lNacimiento"/>: </strong><span id="infoLNacimiento" class="limpiable"></span> 
								<br> <strong><spring:message code="tramite.detalle.sexo"/>:
								</strong><span id="infoSexo" class="limpiable"></span> <br>
							</div>
						</div>

						<div class="col-md-6 col-sm-12">
							<form id="formMediosDeContacto" class="form-horizontal" style="padding: 20px 5px">
				                <div class="form-group">
				                  <div class="col-sm-12">
				                    <spring:message code="label.registro.hijos.medios"/>:
				                  </div>
				                </div>
								<div class="form-group">
									<label class="label-control col-sm-6">
										<spring:message code="label.correoElectronico"/>:
									</label>
									<div class="col-sm-6">
										<input class="form-control limpiable correoElectronico" type="text" id="correo" name="correoElectronico.correo" maxlength="100"/>
										<span id="correoElectronico.correoError" class="error hiddenElement"></span>
									</div>
								</div>
								<div class="form-group">
									<label class="label-control col-sm-6">
										Tel&eacute;fono:
									</label>
									<div class="col-sm-6">
										<input class="form-control limpiable numerico" type="text" id="telefono" name="telefonoFijo.claveLada" maxlength="10"/>
										<span id="telefonoFijo.claveLadaError" class="error hiddenElement"></span>
									</div>
								</div>
								
								<div class="form-group">
									<div class="col-sm-12 text-right">
										<button class="btn btn-default" id="limpiarPersona" type="button"><span class="glyphicon glyphicon-refresh"></span> Buscar otra persona</button>
										<button class="btn btn-primary" id="personaCapturada" type="button"><span class="glyphicon glyphicon-ok"></span> Continuar</button>
									</div>
								</div>
							</form>
						</div>
					</div>
			</div>
			</c:if>
			<div id="capturaDomicilio"  style="display: ${keyCveTipoTramite == 48 ? "none" : "block"}">
			<h4>Datos del domicilio</h4>
        	<hr class="red" style="margin-bottom: 30px">
			<div class="row m-t-md"> 
				<div class="col-sm-12">
					<span>
						Para ubicar tu cl&iacute;nica, ingresa los datos de tu domicilio.
					</span>
				</div>
			</div>
			<div id="componenteDomicilio"></div>
			
			<div class="row m-t-xl">
				<div class="col-sm-12">
					<form id="datosDomicilioUmfForm" action="#" class="form-horizontal" method="post" role="form">
						
						<div class="row" id="infoMensajeClinica" style="display: none;">
							<div class="col-sm-12">
								<h4>Datos de adscripci&oacute;n</h4>
        						<hr class="red" style="margin-bottom: 30px">
							</div>
						</div>
						
						<div class="row" id="infoClinica" style="display: none;">
							<div class="col-md-6 col-sm-12">
								<div class="form-group">
				                  <div class="col-sm-12">Selecciona la cl&iacute;nica o UMF y el turno de tu preferencia.</div>
				                </div>
								<div class="form-group">
									<label class="col-sm-4 control-label" for="medicoEnTurno.unidadMedicaFamiliar.idUMF" style="text-align: left"
											 data-toggle="tooltip" data-placement="top" title="Seleccione la clinica mas cercana a tu domicilio">
										
										Cl&iacute;nica o UMF<span class="required">*</span>:
									</label>
									<div class="col-sm-8">
										<select id="medicoEnTurno.unidadMedicaFamiliar.idUMF" name="medicoEnTurno.unidadMedicaFamiliar.idUMF"
											class="form-control ns_">
											<option value="-1">-- Selecciona tu colonia --</option>
										</select>
										<span id="medicoEnTurno.unidadMedicaFamiliar.idUMFError" class="error hiddenElement"></span>
										<input type="hidden" id="medicoEnTurno.unidadMedicaFamiliar.descripcion" name="medicoEnTurno.unidadMedicaFamiliar.descripcion" value="" />
										
										<input type="hidden" id="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.id" name="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.id" value=""/>
										<input type="hidden" id="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.descripcion" name="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.descripcion" value=""/>
										<input type="hidden" id="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.id" name="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.id" value=""/>
										<input type="hidden" id="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.descripcion" name="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.descripcion" value=""/>
										<input type="hidden" id="medicoEnTurno.unidadMedicaFamiliar.longitud" name="medicoEnTurno.unidadMedicaFamiliar.longitud" value=""/>
										<input type="hidden" id="medicoEnTurno.unidadMedicaFamiliar.latitud" name="medicoEnTurno.unidadMedicaFamiliar.latitud" value=""/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label" for="medicoEnTurno.turno.idTurno" style="text-align: left"
										 data-toggle="tooltip" data-placement="top" title="Seleccione el turno en el que quieres recibir consulta">
										
										Turno<span class="required">*</span>:
									</label>
									<div class="col-sm-8">
										<select id="medicoEnTurno.turno.idTurno" name="medicoEnTurno.turno.idTurno" class="form-control ns_">
											<option value="-1">-- Selecciona tu colonia --</option>
										</select>
										<span id="medicoEnTurno.turno.idTurnoError" class="error hiddenElement"></span>
										<input type="hidden" id="medicoEnTurno.turno.descripcion" name="medicoEnTurno.turno.descripcion" value="" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label" for="medicoEnTurno.consultorio.idConsultorio" style="text-align: left">
										
										Consultorio:
									</label>
									<div class="col-sm-8">
										<input class="form-control" id="medicoEnTurno.consultorio.idConsultorio" name="medicoEnTurno.consultorio.idConsultorio" type="text" readonly="readonly" />
										<input id="medicoEnTurno.consultorio.descripcion" name="medicoEnTurno.consultorio.descripcion" type="hidden" value=""/>
										<input type="hidden" id="medicoEnTurno.idMedicoContultorioTurno" name="medicoEnTurno.idMedicoContultorioTurno" />
									</div>
								</div>
							</div>
							<div class="col-md-6 col-sm-12">
								<div class="form-group">
				                  <div class="col-sm-12">Datos de la cl&iacute;nica seleccionada:</div>
				                </div>
								<div class="alert alert-temp col-sm-12" style="text-align: left;">
									<strong>Direcci&oacute;n </strong>
									<span id="nom_corto"></span>:
									<br>
									<span id="direccionUmf"></span>
									<br>
									<strong>Horarios</strong>
									<br>
									Turno matutino :
									<span id="horarioMatutino">N/A</span>
									<br>
									Turno vespertino :
									<span id="horarioVespertirno">N/A</span>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6 text-left">
								<span id="labelCamposObligatoriosGeneral" class="required">*</span>Campos obligatorios.
							</div>
							<div class="col-sm-6 text-right" id="divbotones" style="display: none;">
								<div>
									<button class="btn btn-danger" type="button" id="cancelarTramite" class="salir"><span class="glyphicon glyphicon-trash"></span>Cancelar</button>
									<button class="btn btn-primary" type="button" id="validarInfo"><span class="glyphicon glyphicon-ok"></span>Continuar</button>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
			</div>
			<br><br>
			<jsp:include page="pieUmf.jsp">
				<jsp:param name="tipoTramite" value="true" />
			</jsp:include>
		</c:otherwise>
	</c:choose>
</div>
<div id="dialog-confirm"></div>
<div id="dialog-error"></div>

<script language="JavaScript1.2" src="${staticResourcesPath}/js/comscore/Form.js"></script>