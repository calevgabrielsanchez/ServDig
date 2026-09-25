<%@ include file="../../general/taglibs.jsp" %>


<script type="text/javascript" src="/portalDerechohabiente-ciudadano/static/resources/js/delta/common/combosUmfMedicoConsultorio.js"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/delta/common/datosUmf.js" htmlEscape="true" />"></script>

<script type="text/javascript" src="<spring:url value="/resources/js/delta/wizard/registroDerechohabiente/funcionesComunes.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/delta/wizard/registroDerechohabiente/datosDomicilio.js" htmlEscape="true" />"></script>

<input type="hidden" value="${requiereDocs?1:0}" id="requiereDocs"/>
<input type="hidden" value="${registro.domicilio.codigoPostal.codigoPostal}" id="cpBusquedaUmf"/>
<input type="hidden" value="${registro.medicoEnTurno.unidadMedicaFamiliar.idUMF}" id="umfSeleccionada"/>
<input type="hidden" value="${registro.medicoEnTurno.turno.idTurno}" id="turnoSeleccionado"/>
<input type="hidden" value="${registro.medicoEnTurno.consultorio.idConsultorio}" id="consultorioSeleccionado"/>
<input type="hidden" value="${solicitudRegistro.solicitudId}" id="idSolicitud"/>
<input type="hidden" value="${registroConyuge?1:0}" id="hdnRegistroConyuge"/>
<input type="hidden" value="${mismoSexo?1:0}" id="hdnMismoSexo"/>
<input type="hidden" value="${validacionesDom.setUmfAsegurado?1:0}" id="mismaUmf">
<input type="hidden" value="${registro.datosAsegurado.idAsignacionNSS}" id="idAsignacionNss">
<input type="hidden" id="hdnFolioSolicitud" value="${solicitudRegistro.noFolioSolicitud}"/>
<input type="hidden" id="hdnFolioSolicitudCifrado" value="${solicitudRegistro.solicitudFolioHashed}"/>
<input type="hidden" value="${solicitudRegistro.solicitudId}" id="idSolicitud"/>

<div class="container-fluid"> 
	<div class="wizard row"> 
		<div class="contenedor col-sm-12"> 
			<div class="contenido row">
				<div class="col-sm-12">
				<c:if test="${not empty error}">
					<div class="alert alert-danger">
						<button type="button" class="close" data-dismiss="alert">�</button>
						<strong>Error: </strong>${error}
					</div>
				</c:if>
				
				<c:if test="${empty error}">
				
				<div class="titulo" align="center">
						<span> PASO 2/2 - Captura de domicilio y Datos de adscripci&oacute;n</span>
				</div>
					
				<form:form id="formRegistro" method="post" modelAttribute="registro" role="form">
					<form:hidden path="paso"/>
					<form:hidden path="parentesco.idParentesco"/>
					<form:hidden path="tipoTramite.idTipoTramite"/>
					<form:hidden path="fisica.curp"/>
					<form:hidden path="tramiteId"/>
					<form:hidden path="indSeleccionMedico"/>
					<form:hidden path="fechaCambioMedico"/>
					<form:hidden path="parentesco.idParentesco"/>
					
					<div id="seccionDomicilioRegistro" class="table_form">
						
							
						
						<div class="separadorseccion">
							<span>
								Domicilio del integrante del grupo familiar
							</span>
						</div>
						<center>		
								<c:if test="${validacionesDom.error && validacionesDom.mensajeError ne '0'}">
									<div class="alert alert-danger">
										<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
										${validacionesDom.mensajeError}
									</div>
								</c:if>
								<c:if test="${!validacionesDom.error && validacionesDom.mensajeError ne '0'}">
									<div class="alert alert-info">
										<p>
											<c:if test="${validacionesDom.permiteUbicarDomcilio}">
												<i class="glyphicon glyphicon-info-sign"></i></span>
													Seleccione su domicilio:
												<strong>
													<a href="#" id="ubicarDomicilioDer"> aqu&iacute;</a>
												</strong>
											</c:if>
											
											<c:if test="${!validacionesDom.permiteUbicarDomcilio}">
												<i class="glyphicon glyphicon-info-sign"></i></span>
												${validacionesDom.mensajeDomicilio}
											</c:if>
										</p>
									</div>
								</c:if>
							</center>
						<div id="domPersona" style="display:none">
						<table width="100%" class="table table-striped table-bordered" >
							<tr>
								
								<td class="label_patrones" style="width: 330px !important;">
									<label class="control-label" for="domicilio.vialidadPrimaria.nombre">
										<span class="required">*</span>Calle
									</label></td>
								<td class="label_patrones" align="center" colspan="1" style="width: 115px !important">
									<label class="control-label" for="domicilio.numExterior1">
										<span class="required">*</span>N&uacute;mero Exterior
									</label>
								</td>
								<td class="label_patrones" align="center" colspan="1" >
									<label class="control-label" for="domicilio.numExteriorAlf">
										Letra Exterior
									</label>
								</td>
							</tr>
							<tr>
								<td class="label_patrones_data" rowspan="3">
									<form:hidden path="cvePersonaDomicilio"/>
									
								
									<form:input path="domicilio.vialidadPrimaria.nombre" maxlength="100"  cssClass="form-control" disabled="true"/> 
									<span id="domicilio.vialidadPrimaria.nombreError" class="error hiddenElement"></span>
									
									<form:hidden path="domicilio.clave"/>
									<form:hidden path="domicilio.vialidadPrimaria.clave" />
									<form:hidden path="domicilio.vialidadPrimaria.tipoVialidad.clave" />
									
									<form:hidden path="domicilio.calle"/>
									<form:hidden path="domicilio.tipoBusquedaVialidad"/>
									
									<!-- Atributos de domicilio carretera -->
									<form:hidden path="domicilio.domicilioCarretera.terminoGeneral.descripcion"/>
									<form:hidden path="domicilio.domicilioCarretera.terminoGeneral.clave"/>
									<form:hidden path="domicilio.domicilioCarretera.derechoTransito.descripcion"/>
									<form:hidden path="domicilio.domicilioCarretera.derechoTransito.clave"/>
									<form:hidden path="domicilio.domicilioCarretera.origen"/>
									<form:hidden path="domicilio.domicilioCarretera.destino"/>
									<form:hidden path="domicilio.domicilioCarretera.administracion.descripcion"/>
									<form:hidden path="domicilio.domicilioCarretera.administracion.clave"/>
									<form:hidden path="domicilio.domicilioCarretera.cadenamiento"/>
									<form:hidden path="domicilio.domicilioCarretera.codigoCarretera"/>
									
									<!-- Atrbutos de domicilio camino -->
									<form:hidden path="domicilio.domicilioCamino.terminoGeneral.descripcion"/>
									<form:hidden path="domicilio.domicilioCamino.terminoGeneral.clave"/>
									<form:hidden path="domicilio.domicilioCamino.margen.descripcion"/>
									<form:hidden path="domicilio.domicilioCamino.margen.clave"/>
									<form:hidden path="domicilio.domicilioCamino.origen"/>
									<form:hidden path="domicilio.domicilioCamino.destino"/>
									<form:hidden path="domicilio.domicilioCamino.cadenamiento"/>
								</td>
								<td colspan="1">
									<form:input path="domicilio.numExterior1" maxlength="5"  cssClass="form-control"  disabled="true"/>
									<span id="domicilio.numExterior1Error" class="error hiddenElement"></span>
								</td>
								<td colspan="1">
									<form:input path="domicilio.numExteriorAlf" maxlength="35" cssClass="form-control"  disabled="true"/>
									<span id="domicilio.numExteriorAlfError" class="error hiddenElement"></span>
								</td>
							</tr>
							<tr>
								<td class="label_patrones" colspan="1" align="center">
									<label class="control-label" for="domicilio.numInterior">
										N&uacute;mero Interior
									</label>
								</td>
								<td class="label_patrones" colspan="1" align="center">
									<label class="control-label" for="domicilio.numInteriorAlf">
										Letra Interior
									</label>
								</td>	
							</tr>
							<tr>
								
								<td class="label_patrones" colspan="1" align="center">
									<form:input path="domicilio.numInterior" maxlength="3" cssClass="form-control"  disabled="true"/>
									<span id="domicilio.numInteriorError" class="error hiddenElement"></span>
								</td>
								<td class="label_patrones" colspan="1" align="center">
									<form:input path="domicilio.numInteriorAlf" maxlength="35" cssClass="form-control"  disabled="true"/>
									<span id="domicilio.numInteriorAlfError" class="error hiddenElement"></span>
								</td>	
							</tr>
							<tr>
								<td class="label_patrones">
									<label class="control-label" for="domicilio.vialidadReferenciaPrimaria.nombre">
									Entre la calle
									</label>
								</td>
								<td class="label_patrones" colspan="2">	
									<label class="control-label" for="domicilio.vialidadReferenciaSecundaria.nombre">									
									y la calle
									</label>
								</td>
							</tr>
							
							<tr>			
								<td class="label_patrones_data">
									<form:input path="domicilio.vialidadReferenciaPrimaria.nombre" maxlength="100" cssClass="form-control"  disabled="true"/> 
									<span id="domicilio.vialidadReferenciaPrimaria.nombreError" class="error hiddenElement"></span>
																		
									<form:hidden path="domicilio.vialidadReferenciaPrimaria.clave" />
									<form:hidden path="domicilio.vialidadReferenciaPrimaria.tipoVialidad.clave" />
								</td>
								<td class="label_patrones_data" colspan="2">
									<form:input path="domicilio.vialidadReferenciaSecundaria.nombre" maxlength="100" cssClass="form-control"  disabled="true"/> 
									<span id="domicilio.vialidadReferenciaSecundaria.nombreError" class="error hiddenElement"></span>
									
									<form:hidden path="domicilio.vialidadReferenciaSecundaria.clave" />
									<form:hidden path="domicilio.vialidadReferenciaSecundaria.tipoVialidad.clave" />
									<form:hidden path="domicilio.vialidadReferenciaPosterior.nombre" maxlength="14" /> 
									<form:hidden path="domicilio.vialidadReferenciaPosterior.clave" />
									<form:hidden path="domicilio.vialidadReferenciaPosterior.tipoVialidad.clave" />
								</td>
							</tr>
							<tr>		
								<td class="label_patrones">		
									<label class="control-label" for="domicilio.asentamiento.nombre">										
									<span class="required">*</span>Colonia(Asentamiento)
									</label>
								</td>
								<td class="label_patrones"colspan="2">		
									<label class="control-label" for="domicilio.asentamiento.localidad.nombre">										
									<span class="required">*</span>Localidad
									</label>
								</td>			
								
							</tr>
							<tr>
								<td class="label_patrones_data">
									<form:input path="domicilio.asentamiento.nombre" maxlength="50"  cssClass="form-control"  disabled="true"/> 
									<span id="domicilio.asentamiento.nombreError" class="error hiddenElement"></span>
									
									<form:hidden path="domicilio.asentamiento.clave" />
								</td>			
								<td class="label_patrones_data" colspan="2">
									<form:input path="domicilio.asentamiento.localidad.nombre" maxlength="50"  cssClass="form-control"  disabled="true"/> 
									<span id="domicilio.asentamiento.localidad.nombreError" class="error hiddenElement"></span>
									
									<form:hidden path="domicilio.asentamiento.localidad.clave" />
								</td>
							</tr>
							<tr>					
								<td class="label_patrones">		
									<label class="control-label" for="domicilio.asentamiento.localidad.municipio.nombre">								
									<span class="required">*</span>Municipio o delegaci&oacute;n
									</label>
								</td>
								<td class="label_patrones" colspan="2">
									<label class="control-label" for="domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre">
									<span class="required">*</span>Entidad Federativa
									</label>
								</td>
								
							</tr>
							<tr>
								<td class="label_patrones_data">
									<form:input path="domicilio.asentamiento.localidad.municipio.nombre"  cssClass="form-control"  disabled="true"/> 
									<span id="domicilio.asentamiento.localidad.municipio.nombreError" class="error hiddenElement"></span>
									
									<form:hidden path="domicilio.asentamiento.localidad.municipio.clave" />
								</td>
								<td class="label_patrones_data" colspan="2">
									<form:input path="domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre" maxlength="50" cssClass="form-control"  disabled="true"/> 
									<span id="domicilio.asentamiento.localidad.municipio.entidadFederativa.nombreError" class="error hiddenElement"></span>
									
									<form:hidden path="domicilio.asentamiento.localidad.municipio.entidadFederativa.clave" />
								</td>
								
							</tr>
							<tr>
								<td class="label_patrones" colspan="3">
								<label class="control-label" for="domicilio.codigoPostal.codigoPostal">
								<span class="required">*</span>C&oacute;digo Postal
								</label></td>
							</tr>
							<tr>
								<td colspan="3">
									<form:input path="domicilio.codigoPostal.codigoPostal" maxlength="5"  cssClass="form-control"  disabled="true"/>
									<span id="domicilio.codigoPostal.codigoPostalError" class="error hiddenElement"></span>
									
									<form:hidden path="domicilio.vialidadPrimaria.tipoVialidad.descripcion" />	
								</td>
							</tr>
						</table>
						</div>
					</div>
					
					<div id="datosAdscripcion">

					<div class="alert alert-info">
						<p>
							<i class="glyphicon glyphicon-info-sign"></i></span>
							Ahora es necesario que elija la Unidad M&eacute;dica Familiar, Turno y consultorio en donde desee que se le atienda. 
							<strong> Nota: </strong>los campos marcados con "<span class="required">*</span>" son obligatorios. 
						</p>
					</div>
					<div class="separadorseccion">
						<span>
							Datos de adscripci&oacute;n del integrante del grupo familiar
						</span>
					</div>
					<div id="mensajeIntegranteUmf" class="alert alert-info" align="center" style="display:none;">
						<p>
							<i class="glyphicon glyphicon-info-sign"></i></span>
							<span id="mensajeUmfIntegrante"></span>
						</p>
					</div>
						
					<c:if test="${validacionesDom.setUmfAsegurado}">
						<div class="alert alert-info">
						<i class="glyphicon glyphicon-info-sign"></i></span>
							${validacionesDom.mensajeUmf }
						</div>					
					</c:if>
						<table width="100%" class="table table-striped table-bordered" >
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
							<tr>
								<td>
									<label class="control-label" for="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.descripcion">
										Delegaci&oacute;n :
									</label>
								</td>
								<td>
									<form:hidden path="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.id"/>
									<form:input path="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.descripcion" cssClass="form-control"  disabled="true"/>
								</td>
								<td>
									<label class="control-label" for="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.descripcion">
										Subdelegaci&oacute;n :
									</label>
								</td>
								<td>
									<form:hidden path="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.id"/>
									<form:input path="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.descripcion" cssClass="form-control"  disabled="true"/>
								</td>
							</tr>
							<tr>
								<td>
									<label class="control-label" for="medicoEnTurno.turno.idTurno">
									<span class="required">*</span>&nbsp;Turno :
									</label>
								</td>
								<td>
									<select id="medicoEnTurno.turno.idTurno" name="medicoEnTurno.turno.idTurno" class="form-control">
										 <option value="-1">--Por favor seleccione--</option>
									</select>
									
									<span id="medicoEnTurno.turno.idTurnoError" class="error hiddenElement"></span>
								</td>
								<td>
									<label class="control-label" for="medicoEnTurno.consultorio.idConsultorio">
										<span class="required">*</span>&nbsp;Consultorio :
									</label>
								</td>
								<td>
									<select id="medicoEnTurno.consultorio.idConsultorio" name="medicoEnTurno.consultorio.idConsultorio" class="form-control">
										 <option value="-1">--Por favor seleccione--</option>
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
									<form:input path="medicoEnTurno.medicoFamiliar.nombre" cssClass="form-control"  disabled="true"/>
								</td>
							</tr>
							<tr>
								<td>
									<label class="control-label" for="medicoEnTurno.medicoFamiliar.noMatricula">
										Matr&iacute;cula :
									</label>
								</td>
								<td><form:input path="medicoEnTurno.medicoFamiliar.noMatricula" cssClass="form-control"  disabled="true"/></td>
								<td>
									<label class="control-label" for="medicoEnTurno.medicoEspecialidad.descripcion">
										Especialidad :
									</label>
								</td>
								<td>
									<form:hidden path="medicoEnTurno.medicoEspecialidad.idMedicoEspacialidad"/>
									<form:input path="medicoEnTurno.medicoEspecialidad.descripcion" cssClass="form-control"  disabled="true"/>
								</td>
							</tr>
						</table>
						<br>
					</div>
						<c:if test="${requiereDocs}">
							<div class="separadorseccion">
								<span>
									Documentos probatorios
								</span>
							</div>
							<div class="alert alert-warning">
									<span class="required">*</span> Este tr&aacute;mite requiere la captura de documentos probatorios, de clic <a id="capturarDocumentos"><i class="glyphicon glyphicon-file"></i> AQUI </a> para proceder a la misma, no podr&aacute; 
									finalizar el tr&aacute;mite hasta completarla.<br><br>
							</div>
						</c:if>
				</form:form>
				</c:if>
				</div>
			</div>
		
		<p class="alert alert-info" align="center">
	 		<b>Para concluir la solicitud, favor de dar clic en el icono de acciones y seleccionar la opci&oacute;n - Finalizar Tr&aacute;mite -.</b>
		</p>
			<div class="pie row">
				<div class="opciones col-sm-6">
					<c:if test="${empty error}">
						<div class="btn-group">
							<a href="#" class="btn btn-primary">
								<spring:message code="label.menus.opciones" />
							</a> 
							<a href="#" data-toggle="dropdown" class="btn btn-primary dropdown-toggle">
								<span class="caret"></span>
							</a>
							<ul class="dropdown-menu">
								<li>
									<a id="finalizarTramite"><i class="glyphicon glyphicon-ok"></i> 
									Finalizar Tr&aacute;mite</a>
								</li>

								<li>
									<a id="cancelarTramite"><i class="glyphicon glyphicon-trash"></i> 
									Cancelar Tr&aacute;mite</a>
								</li>
							</ul>
						</div>
					</c:if>
					
					
				</div>
				<div class="controler col-sm-6">
					<div class="pull-right">
						<c:if test="${empty error }">
							<button id="regresar" class="btn btn-default" type="button">
								<span class="glyphicon glyphicon-step-backward"></span> Anterior
							</button>
						</c:if>
						 <button class="btn btn-secondary" id="cerrarWizard">CERRAR</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="pie" class="row"> </div>
</div>

<!-- Divs para dialogos de mensajes -->
<div id="dialog-confirm-cancelar" title="Confirmar cancelaci&oacute;n de solicitud">
	<p>
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
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span> <label
			id="mensajeError"></label>
	</p>
</div>
