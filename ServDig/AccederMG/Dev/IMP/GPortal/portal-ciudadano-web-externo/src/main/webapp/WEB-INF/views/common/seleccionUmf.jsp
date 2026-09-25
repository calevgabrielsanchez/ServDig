<%@ include file="../general/taglibs.jsp"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.enums.TipoTramiteEnum"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="tipoTramiteModifDatosGrales" value="<%=TipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES.getCodigo()%>" />

<script type="text/javascript">
			history.go(1);
</script>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/home/home.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="/portalDerechohabiente-ciudadano/static/resources/js/delta/common/combosUmfMedicoConsultorio.js"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/common/seleccionDomicilio.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/common/seleccionUmf.js" htmlEscape="true" />"></script>

<script type="text/javascript">
	$(function(){
		$('input[id="domicilio.codigoPostal.codigoPostal"]').focus();
	});
</script>

<style>
.ui-widget-overlay {
	position: fixed;
}

.ui-dialog-titlebar {
    display:none;
}

span.error-custom {
	float: none !important;
	vertical-align: super;
}

.required {
	color: red;
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
	<input type="hidden" id="tipoTramite" value="${tramite}"/>
	<input type="hidden" id="codigoPostalSeleccionado" value=""/>
	<c:choose>
		<c:when test="${error}">
			<div class="alert alert-danger">
				<strong>Error: </strong>${error}
			</div>
		</c:when>
		<c:otherwise>
			<div class="row">
				<div class="col-sm-12">
					<h2><spring:message code="${tituloTramite}" /></h2>
					<hr class="red">
				</div>
			</div>
			<jsp:include page="../common/paginadorClinica.jsp">
				<jsp:param name="paso" value="2" />
			</jsp:include>
			<div class="row">
				<div class="col-md-7 col-sm-12">
					<h4>
						<strong>Bienvenido(a) ${ciudadano.nombreCompleto}</strong>
					</h4>
				</div>
				<div class="col-md-5 col-sm-12">
					<h4>
						<strong>CURP: ${ciudadano.curp}</strong>
					</h4>
				</div>
			</div>
			<div class="row m-t-md">
				<div class="col-sm-12">
					<h4>PASO 2: Selecciona tu cl&iacute;nica</h4>
				</div>
			</div>
			<div class="row m-t-md">
				<div class="col-sm-12">
					<span>
						Para ubicar tu cl&iacute;nica, ingresa los datos de tu domicilio.
					</span>
				</div>
			</div>
			
			<div class="row m-t-xl">
				<div class="col-sm-12">
					<form id="datosDomicilioUmfForm" action="#" class="form-horizontal" method="post" role="form">

						<div class="row m-b-xl">
							<label class="col-md-2 col-md-offset-1 col-sm-3 col-xs-12 control-label m-b-xs" for="domicilio.codigoPostal.codigoPostal">
								<span class="required">*</span>
								C&oacute;digo Postal:
							</label>
							<div class="col-md-3 col-sm-3 col-xs-12 m-b-xs">
								<input class="form-control" id="domicilio.codigoPostal.codigoPostal" name="domicilio.codigoPostal.codigoPostal"
									type="text" maxlength="5" />
								<span id="domicilio.codigoPostal.codigoPostalError" class="error hiddenElement"></span>
							</div>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<button class="btn btn-primary m-b-xs" type="button" id="busquedaCp">
									<span class="glyphicon glyphicon-ok"></span>
									Aceptar
								</button>

								<button class="btn btn-primary m-b-xs" id="limpiarForm">
									<span class="glyphicon glyphicon-refresh"></span>
									Limpiar
								</button>
							</div>
						</div>

						<div class="row m-b-lg">
							<label class="col-md-2 col-sm-5 col-xs-12 control-label" for="domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre">
								<span class="required">*</span>
								Estado:
							</label>
							<div class="col-md-4 col-sm-7 col-xs-12 m-b-sm">
								<input id="domicilio.asentamiento.localidad.clave" name="domicilio.asentamiento.localidad.clave" type="hidden" />
								<input id="domicilio.asentamiento.localidad.municipio.entidadFederativa.clave"
									name="domicilio.asentamiento.localidad.municipio.entidadFederativa.clave" type="hidden" />
								<input class="form-control" id="domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre"
									name="domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre" type="text" readonly="readonly" />

								<span id="domicilio.asentamiento.localidad.municipio.entidadFederativa.nombreError" class="error hiddenElement"></span>

							</div>
							<label class="col-md-2 col-sm-5 col-xs-12 control-label" for="domicilio.asentamiento.localidad.municipio.nombre">
								<span class="required">*</span>
								Municipio o Delegaci&oacute;n:
							</label>
							<div class="col-md-4 col-sm-7 col-xs-12">
								<input id="domicilio.asentamiento.localidad.municipio.clave"
									name="domicilio.asentamiento.localidad.municipio.clave" type="hidden" />

								<input class="form-control" id="domicilio.asentamiento.localidad.municipio.nombre"
									name="domicilio.asentamiento.localidad.municipio.nombre" type="text" readonly="readonly" />

								<span id="domicilio.asentamiento.localidad.municipio.nombreError" class="error hiddenElement"></span>
							</div>
						</div>

						<div class="row m-b-md">
							<div class="col-sm-12">
								Selecciona la Cl&iacute;nica o UMF y el turno de tu preferencia.
							</div>
						</div>

						<div class="row">
							<div class="col-md-6 col-sm-12">
								 <h4 class="hidden-sm hidden-xs">
									<strong>&nbsp;</strong>
								</h4>
								<div class="form-group">
									<label class="col-sm-4 control-label" for="medicoEnTurno.unidadMedicaFamiliar.idUMF" style="text-align: left">
										<span class="required">*</span>
										Cl&iacute;nica o UMF:
									</label>
									<div class="col-sm-8">
										<select id="medicoEnTurno.unidadMedicaFamiliar.idUMF" name="medicoEnTurno.unidadMedicaFamiliar.idUMF"
											class="form-control">
											<option value="-1">Proporcione C.P.</option>
										</select>
										<span id="medicoEnTurno.unidadMedicaFamiliar.idUMFError" class="error hiddenElement"></span>
										<input type="hidden" id="medicoEnTurno.unidadMedicaFamiliar.descripcion"
											name="medicoEnTurno.unidadMedicaFamiliar.descripcion" value="" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label" for="medicoEnTurno.turno.idTurno" style="text-align: left">
										<span class="required">*</span>
										Turno:
									</label>
									<div class="col-sm-8">
										<select id="medicoEnTurno.turno.idTurno" name="medicoEnTurno.turno.idTurno" class="form-control">
											<option value="-1">Proporcione C.P.</option>
										</select>
										<span id="medicoEnTurno.turno.idTurnoError" class="error hiddenElement"></span>
										<input type="hidden" id="medicoEnTurno.turno.descripcion" name="medicoEnTurno.turno.descripcion" value="" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label" for="medicoEnTurno.consultorio.idConsultorio" style="text-align: left">
										<span class="required">*</span>
										Consultorio:
									</label>
									<div class="col-sm-8">
										<input class="form-control" id="medicoEnTurno.consultorio.idConsultorio" name="" type="text"
											readonly="readonly" />
										<input type="hidden" id="medicoEnTurno.idMedicoContultorioTurno" name="medicoEnTurno.idMedicoContultorioTurno" />
										<span id="medicoEnTurno.turno.idTurnoError" class="error hiddenElement"></span>
									</div>
								</div>
							</div>
							<div class="col-md-6 col-sm-12">
								<h4>
									<strong>Datos de la cl&iacute;nica:</strong>
								</h4>
								<div class="alert alert-temp col-sm-12" style="text-align: left;">
									<strong>Direcci&oacute;n </strong>
									<span id="nom_corto"></span>
									:
									<br>
									<span id="direccionUmf"></span>
									<br>
									<strong>Horarios</strong>
									<br>
									Turno Matutino :
									<span id="horarioMatutino">N/A</span>
									<br>
									Turno Vespertino :
									<span id="horarioVespertirno">N/A</span>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6 text-left">
								Los campos marcados con <span class="required">*</span> son obligatorios.
							</div>
							<div class="col-sm-6 text-right">
								<div>
									<button class="btn btn-default" type="button" id="cancelarTramite">Cancelar</button>
									<button class="btn btn-primary" type="button" id="validarInfo">Continuar</button>
								</div>
							</div>
						</div>
					</form>
				</div>	
			</div>
			<div class="row">
				<div class="col-sm-12">
					<jsp:include page="pieUmf.jsp">
						<jsp:param name="tipoTramite" value="true" />
					</jsp:include>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
</div>
<div id="dialog-confirm"></div>
<div id="dialog-error"></div>