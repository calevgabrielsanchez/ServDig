<%@ include file="../../general/taglibs.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/wizard/cambioClinica/funcionesComunes.js" htmlEscape="true" />"></script>

<div class="contenedor col-sm-12">

	<input type="hidden" value="${domicilio.asentamiento.codigoPostal.codigoPostal}" id="cpBusquedaUmf"/>
	<input type="hidden" value="${umfSeleccionada}" id="umfSeleccionada"/>
	<input type="hidden" value="${turnoSeleccionado}" id="turnoSeleccionado"/>
	<input type="hidden" value="${consultorioSeleccionado}" id="consultorioSeleccionado"/>
	<span id="muestraUmf" data-value="${muestraUmf}"></span> 
	<span id="enCircunscipcionForanea" data-value="${enCircunscipcionForanea}" data-message="<spring:message code="DomicilioEnCircunscripcionForaneaMessage" />"></span>
	
	<input type="hidden" id="idSolicitud" value="${solicitud.solicitudId}" />
	<input type="hidden" id="hdnFolioSolicitud" value="${solicitud.noFolioSolicitud}" />
	<input type="hidden" id="hdnFolioSolicitudCifrado" value="${solicitud.solicitudFolioHashed}"/>
	
	
	<div class="contenido row">		
		<div class="col-sm-12">
		
			<c:if test="${not empty error}">
				<div class="alert alert-info">
					<button type="button" class="close" data-dismiss="alert">×</button>
					<strong>Importante: </strong>${error}
				</div>
				<button class="btn btn-default" id="btnInicioCancelarTramiteClinica">CERRAR</button>
			</c:if>
			
			<c:if test="${empty error}">
				<div class="alert alert-info">
					<p>
						<i class="glyphicon glyphicon-info-sign m-r-xs"></i>
						<spring:message code="seleccioneSuDomicilio" />
						<strong>
							<a id="ubicarDomicilioDer" href="javascript:" class="alert-link">
								<spring:message code="aqui" />
							</a>
						</strong>
					</p>
				</div>

				
				<script type="text/javascript" src="/portalDerechohabiente-ciudadano/static/resources/js/delta/common/combosUmfMedicoConsultorio.js"></script>
				<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/common/datosUmf.js" htmlEscape="true" />"></script>
	
				<form:form id="domicilio" method="post" modelAttribute="domicilio" role="form" style="display:none">
	
					<div id="seccionDomicilioRegistro" class="table_form">
	
						<div class="separadorseccion">
							<span> Domicilio </span>
						</div>
	
						<table  class="table table-striped table-bordered">
							<tr>
								<td class="label_patrones" style="width: 330px !important;">
									<label class="control-label" for="vialidadPrimaria.nombre">
										<span class="required">*</span>Calle
								</label>
								</td>
	
								<td class="label_patrones" align="center" colspan="1"
									style="width: 115px !important"><label
									class="control-label" for="numExterior1"> <span
										class="required">*</span>N&uacute;mero Exterior
								</label></td>
	
								<td class="label_patrones" align="center" colspan="1"><label
									class="control-label" for="numExteriorAlf">Letra
										Exterior </label></td>
							</tr>
	
							<tr>
								<td class="label_patrones_data" rowspan="3"><form:input
										path="vialidadPrimaria.nombre" maxlength="100"
										cssClass="form-control" /> <span
									id="vialidadPrimaria.nombreError" class="error hiddenElement"></span>
									<form:hidden path="clave" /> <form:hidden
										path="vialidadPrimaria.clave" /> <form:hidden
										path="vialidadPrimaria.tipoVialidad.clave" /> <form:hidden
										path="calle" /> <form:hidden path="tipoBusquedaVialidad" /> <!-- Atributos de domicilio carretera -->
									<form:hidden
										path="domicilioCarretera.terminoGeneral.descripcion" /> <form:hidden
										path="domicilioCarretera.terminoGeneral.clave" /> <form:hidden
										path="domicilioCarretera.derechoTransito.descripcion" /> <form:hidden
										path="domicilioCarretera.derechoTransito.clave" /> <form:hidden
										path="domicilioCarretera.origen" /> <form:hidden
										path="domicilioCarretera.destino" /> <form:hidden
										path="domicilioCarretera.administracion.descripcion" /> <form:hidden
										path="domicilioCarretera.administracion.clave" /> <form:hidden
										path="domicilioCarretera.cadenamiento" /> <form:hidden
										path="domicilioCarretera.codigoCarretera" /> <!-- Atrbutos de domicilio camino -->
									<form:hidden path="domicilioCamino.terminoGeneral.descripcion" />
									<form:hidden path="domicilioCamino.terminoGeneral.clave" /> <form:hidden
										path="domicilioCamino.margen.descripcion" /> <form:hidden
										path="domicilioCamino.margen.clave" /> <form:hidden
										path="domicilioCamino.origen" /> <form:hidden
										path="domicilioCamino.destino" /> <form:hidden
										path="domicilioCamino.cadenamiento" /></td>
	
								<td colspan="1"><form:input path="numExterior1"
										maxlength="5" cssClass="form-control" /> <span
									id="numExterior1Error" class="error hiddenElement"></span></td>
	
								<td colspan="1"><form:input path="numExteriorAlf"
										maxlength="35" cssClass="form-control" /> <span
									id="numExteriorAlfError" class="error hiddenElement"></span></td>
							</tr>
	
							<tr>
								<td class="label_patrones" colspan="1" align="center"><label
									class="control-label" for="numInterior">N&uacute;mero
										Interior </label></td>
								<td class="label_patrones" colspan="1" align="center"><label
									class="control-label" for="numInteriorAlf">Letra
										Interior </label></td>
							</tr>
	
							<tr>
	
								<td class="label_patrones" colspan="1" align="center"><form:input
										path="numInterior" maxlength="3" cssClass="form-control" /> <span
									id="numInteriorError" class="error hiddenElement"></span></td>
	
								<td class="label_patrones" colspan="1" align="center"><form:input
										path="numInteriorAlf" maxlength="35" cssClass="form-control" />
									<span id="numInteriorAlfError" class="error hiddenElement"></span>
								</td>
							</tr>
	
							<tr>
								<td class="label_patrones"><label class="control-label"
									for="vialidadReferenciaPrimaria.nombre">Entre la calle </label>
								</td>
								<td class="label_patrones" colspan="2"><label
									class="control-label" for="vialidadReferenciaSecundaria.nombre">
										y la calle </label></td>
							</tr>
	
							<tr>
								<td class="label_patrones_data"><form:input
										path="vialidadReferenciaPrimaria.nombre" maxlength="100"
										cssClass="form-control" /> <span
									id="vialidadReferenciaPrimaria.nombreError"
									class="error hiddenElement"></span> <form:hidden
										path="vialidadReferenciaPrimaria.clave" /> <form:hidden
										path="vialidadReferenciaPrimaria.tipoVialidad.clave" /></td>
	
								<td class="label_patrones_data" colspan="2"><form:input
										path="vialidadReferenciaSecundaria.nombre" maxlength="100"
										cssClass="form-control" /> <span
									id="vialidadReferenciaSecundaria.nombreError"
									class="error hiddenElement"></span> <form:hidden
										path="vialidadReferenciaSecundaria.clave" /> <form:hidden
										path="vialidadReferenciaSecundaria.tipoVialidad.clave" /> <form:hidden
										path="vialidadReferenciaPosterior.nombre" maxlength="14" /> <form:hidden
										path="vialidadReferenciaPosterior.clave" /> <form:hidden
										path="vialidadReferenciaPosterior.tipoVialidad.clave" /></td>
							</tr>
	
							<tr>
								<td class="label_patrones"><label class="control-label"
									for="asentamiento.nombre"> <span class="required">*</span>Colonia(Asentamiento)
								</label></td>
								<td class="label_patrones" colspan="2"><label
									class="control-label" for="asentamiento.localidad.nombre">
										<span class="required">*</span>Localidad
								</label></td>
							</tr>
	
							<tr>
								<td class="label_patrones_data"><form:input
										path="asentamiento.nombre" maxlength="50"
										cssClass="form-control" /> <span id="asentamiento.nombreError"
									class="error hiddenElement"></span> <form:hidden
										path="asentamiento.clave" /></td>
								<td class="label_patrones_data" colspan="2"><form:input
										path="asentamiento.localidad.nombre" maxlength="50"
										cssClass="form-control" /> <span
									id="asentamiento.localidad.nombreError"
									class="error hiddenElement"></span> <form:hidden
										path="asentamiento.localidad.clave" /></td>
							</tr>
	
							<tr>
								<td class="label_patrones"><label class="control-label"
									for="asentamiento.localidad.municipio.nombre"> <span
										class="required">*</span>Municipio o delegaci&oacute;n
								</label></td>
								<td class="label_patrones" colspan="2"><label
									class="control-label"
									for="asentamiento.localidad.municipio.entidadFederativa.nombre">
										<span class="required">*</span>Entidad Federativa
								</label></td>
	
							</tr>
	
							<tr>
								<td class="label_patrones_data"><form:input
										path="asentamiento.localidad.municipio.nombre"
										cssClass="form-control" /> <span
									id="asentamiento.localidad.municipio.nombreError"
									class="error hiddenElement"></span> <form:hidden
										path="asentamiento.localidad.municipio.clave" /></td>
								<td class="label_patrones_data" colspan="2"><form:input
										path="asentamiento.localidad.municipio.entidadFederativa.nombre"
										maxlength="50" cssClass="form-control" /> <span
									id="asentamiento.localidad.municipio.entidadFederativa.nombreError"
									class="error hiddenElement"></span> <form:hidden
										path="asentamiento.localidad.municipio.entidadFederativa.clave" />
								</td>
							</tr>
	
							<tr>
								<td class="label_patrones" colspan="3"><label
									class="control-label"
									for="codigoPostal.codigoPostal"> <span
										class="required">*</span>C&oacute;digo Postal
								</label></td>
							</tr>
							<tr>
								<td colspan="3"><form:input
										path="codigoPostal.codigoPostal" maxlength="5"
										cssClass="form-control" /> <span
									id="asentamiento.codigoPostal.codigoPostalError"
									class="error hiddenElement"></span> <form:hidden
										path="vialidadPrimaria.tipoVialidad.descripcion" /></td>
							</tr>
						</table>
					</div>
				</form:form>
	
	
	
				<form:form id="formUMF" method="post" 	modelAttribute="tramite" role="form" style="display:none;">
						<div id="seccionUmfRegistro" class="table_form">
							<form:hidden path="paso" />
							<form:hidden path="parentesco.idParentesco" />
							<form:hidden path="tipoTramite.idTipoTramite" />
							<form:hidden path="fisica.curp" />
							<form:hidden path="tramiteId" />
							<form:hidden path="indSeleccionMedico" />
							<form:hidden path="fechaCambioMedico" />
							<form:hidden path="idAsignacionNss" />
				
				
							<div class="separadorseccion">
								<span><spring:message code="datosAdscripcionGrupoFamiliar" /></span>
							</div>
				
							<div id="mensajeIntegranteUmf" class="ui-widget" align="center" style="display: none;">
								<div class="ui-state-highlight ui-corner-all" style="margin-top: 12px; padding: 0 .5em;">
									<p>
										<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span> <span
											id="mensajeUmfIntegrante"></span>
									</p>
								</div>
							</div>
				
							<table class="table table-striped table-bordered">
								<!-- UMF -->
								<tr>
									<td>
										<label class="control-label" for="medicoEnTurno.unidadMedicaFamiliar.idUMF"> 
											<span class="required">*</span>&nbsp;UMF:
										</label>
									</td>
									
									<td colspan="3">
										<form:hidden path="medicoEnTurno.idMedicoContultorioTurno" /> 
										<select id="medicoEnTurno.unidadMedicaFamiliar.idUMF" name="medicoEnTurno.unidadMedicaFamiliar.idUMF" class="form-control">
											<option value="-1">--Por favor seleccione--</option>
										</select> 
										<span id="medicoEnTurno.unidadMedicaFamiliar.idUMFError" class="error hiddenElement"></span>
									</td>
								</tr>
								
								
								<!-- MEDICO -->
								<tr>
									<td>
										<label class="control-label" for="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.descripcion">
											<spring:message code="delegacion" />
										</label>
									</td>
									
									<td>
										<form:hidden path="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.id" />
										<form:input path="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.descripcion" cssClass="form-control" />
									</td>
									
									<td>
										<label class="control-label" for="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.descripcion">
											<spring:message code="subdelegacion" />
										</label>
									</td>
									
									<td>
										<form:hidden path="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.id" /> 
										<form:input path="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.descripcion" cssClass="form-control" />
									</td>
								</tr>
								
								
							<!-- TURNO -->
								<tr>
									<td>
										<label class="control-label" for="medicoEnTurno.turno.idTurno"> <span class="required">*</span>
										&nbsp;<spring:message code="turno" />
										</label>
									</td>
									
									<td>
										<select id="medicoEnTurno.turno.idTurno" name="medicoEnTurno.turno.idTurno" class="form-control">
											<option value="-1"><spring:message code="porFavorSeleccione" /></option>
									</select> <span id="medicoEnTurno.turno.idTurnoError"
										class="error hiddenElement"></span></td>
									<td><label class="control-label"
										for="medicoEnTurno.consultorio.idConsultorio"> <span
											class="required">*</span>&nbsp;Consultorio :
									</label></td>
									<td><select id="medicoEnTurno.consultorio.idConsultorio"
										name="medicoEnTurno.consultorio.idConsultorio"
										class="form-control">
											<option value="-1">--Por favor seleccione--</option>
									</select> <span id="medicoEnTurno.consultorio.idConsultorioError"
										class="error hiddenElement"></span></td>
								</tr>
								<tr>
									<td><label class="control-label"
										for="medicoEnTurno.medicoFamiliar.idMedicoFamiliar">
											&nbsp;M&eacute;dico : </label></td>
									<td colspan="3"><form:hidden
											path="medicoEnTurno.medicoFamiliar.idMedicoFamiliar" /> <form:input
											path="medicoEnTurno.medicoFamiliar.nombre"
											cssClass="form-control" /></td>
								</tr>
								<tr>
									<td><label class="control-label"
										for="medicoEnTurno.medicoFamiliar.noMatricula">
											Matr&iacute;cula : </label></td>
									<td><form:input
											path="medicoEnTurno.medicoFamiliar.noMatricula"
											cssClass="form-control" /></td>
									<td><label class="control-label"
										for="medicoEnTurno.medicoEspecialidad.descripcion">
											Especialidad : </label></td>
									<td><form:hidden
											path="medicoEnTurno.medicoEspecialidad.idMedicoEspacialidad" /> <form:input
											path="medicoEnTurno.medicoEspecialidad.descripcion"
											cssClass="form-control" /></td>
								</tr>
							</table>
				
							<br>
						</div>
				
					</form:form>

					<div style="display:none;" id="documentosProbatorios" >
						<div class="separadorseccion">
							<span> Documentos probatorios </span>
						</div>
						<div class="alert alert-warning m-b-sm">
							<span class="required">*</span>
							Este trámite requiere la captura de documentos probatorios, de clic
							<a id="capturarDocumentos" class="alert-link">
								<i class="glyphicon glyphicon-file"></i>
								AQUI
							</a>
							para proceder a la misma, no podrá finalizar el trámite hasta completarla.
						</div>
						<div id="listaDocCargadosClinica" >
							<table width="100%" id="listaDocCargadosTable"
								class="table table-striped table-bordered">
								<thead>
									<tr align='left'>
										<th align='left'>Tipo Documento</th>
										<th align='left'>Documento</th>
									</tr>
								</thead>
								<tbody></tbody>
							</table>
						</div>
										
					<div class="alert alert-info m-t-xl"100%;">
						<b>Para concluir la solicitud, favor de dar clic en el icono de
							acciones y seleccionar la opción - Finalizar Trámite.</b>
					</div>
				</div>
			</c:if>
		</div><!--// col-sm-12  -->		
	</div><!--// contenido  -->
</div><!--// contenedor  -->
