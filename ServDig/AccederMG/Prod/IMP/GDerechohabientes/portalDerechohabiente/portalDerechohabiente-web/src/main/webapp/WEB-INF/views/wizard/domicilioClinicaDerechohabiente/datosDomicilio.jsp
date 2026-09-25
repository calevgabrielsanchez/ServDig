<%@ include file="../../general/taglibs.jsp" %>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/wizard/domicilioClinicaDerechohabiente/funcionesComunes.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/wizard/domicilioClinicaDerechohabiente/datosDomicilio.js" htmlEscape="true" />"></script>

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


<div class="contenedor">
	<input type="hidden" value="${requiereDocs ? 1 : 0}" id="requiereDocs"/>
	<input type="hidden" value="${solicitud.solicitudId}" id="idSolicitud"/>
	<input type="hidden" id="contenidoFirmar" value="${contenidoFirmar}" />
	<div class="contenido" style="width: 100%;">
	
		<c:if test="${not empty error}">
			<div class="alert alert-error">
				<button type="button" class="close" data-dismiss="alert">×</button>
				<strong>Error: </strong>${error}
			</div>
		</c:if>
		
		<c:if test="${empty error}">
		<input type="hidden" id="hdnFolioSolicitud" value="${solicitud.noFolioSolicitud}"/>
			<input type="hidden" id="contenidoFirmar" value="${contenidoFirmar}" />
			<div class="alert alert-success">
				<c:choose>
					<c:when test="${!isRetomar}">
						Su solicitud ha sido iniciado correctamente y se le ha asignado a dicha solicitud el folio: <strong>${solicitud.noFolioSolicitud}</strong>
					</c:when>
					<c:otherwise>
						El folio de la solicitud que esta retomando es: <strong>${solicitud.noFolioSolicitud}</strong>
					</c:otherwise>
				</c:choose>
			</div>
			
			<div class="titulo" align="center">
				<span> Información del domicilio actual</span>
			</div>
		<form:form id="formRegistro" method="post" modelAttribute="tramite" role="form">
			
			<div id="seccionDomicilioRegistro" class="table_form">
				<center>
					<div id="wrapperDomicilio" class="ui-widget" align="center" style="width: 75% !important;">
						<div class="ui-state-highlight ui-corner-all" style="margin-top: 12px; padding: 0 .5em;">
							<p>
								<i class="glyphicon glyphicon-info-sign"></i></span>
									Si desea asignar un nuevo domicilio, seleccione:
								<strong>
									<a href="#" id="ubicarDomicilioDer"> aqu&iacute;</a>
								</strong>
							</p>
						</div>
					</div>
				</center>
				<div class="separadorseccion">
					<span>
						Domicilio del integrante del grupo familiar
					</span>
				</div>
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
							<form:hidden path="parentesco.idParentesco"/>
							<%-- <form:hidden path="paso"/> --%>
							<form:hidden path="tramiteId"/>
							<form:hidden path="tipoTramite.idTipoTramite"/>
						
							<form:input path="domicilio.vialidadPrimaria.nombre" maxlength="100"  cssClass="form-control"/> 
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
							<form:input path="domicilio.numExterior1" maxlength="5"  cssClass="form-control"/>
							<span id="domicilio.numExterior1Error" class="error hiddenElement"></span>
						</td>
						<td colspan="1">
							<form:input path="domicilio.numExteriorAlf" maxlength="35" cssClass="form-control"/>
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
							<form:input path="domicilio.numInterior" maxlength="3" cssClass="form-control"/>
							<span id="domicilio.numInteriorError" class="error hiddenElement"></span>
						</td>
						<td class="label_patrones" colspan="1" align="center">
							<form:input path="domicilio.numInteriorAlf" maxlength="35" cssClass="form-control"/>
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
							<form:input path="domicilio.vialidadReferenciaPrimaria.nombre" maxlength="100" cssClass="form-control"/> 
							<span id="domicilio.vialidadReferenciaPrimaria.nombreError" class="error hiddenElement"></span>
																
							<form:hidden path="domicilio.vialidadReferenciaPrimaria.clave" />
							<form:hidden path="domicilio.vialidadReferenciaPrimaria.tipoVialidad.clave" />
						</td>
						<td class="label_patrones_data" colspan="2">
							<form:input path="domicilio.vialidadReferenciaSecundaria.nombre" maxlength="100" cssClass="form-control"/> 
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
							<form:input path="domicilio.asentamiento.nombre" maxlength="50"  cssClass="form-control"/> 
							<span id="domicilio.asentamiento.nombreError" class="error hiddenElement"></span>
							
							<form:hidden path="domicilio.asentamiento.clave" />
						</td>			
						<td class="label_patrones_data" colspan="2">
							<form:input path="domicilio.asentamiento.localidad.nombre" maxlength="50"  cssClass="form-control"/> 
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
							<form:input path="domicilio.asentamiento.localidad.municipio.nombre"  cssClass="form-control"/> 
							<span id="domicilio.asentamiento.localidad.municipio.nombreError" class="error hiddenElement"></span>
							
							<form:hidden path="domicilio.asentamiento.localidad.municipio.clave" />
						</td>
						<td class="label_patrones_data" colspan="2">
							<form:input path="domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre" maxlength="50" cssClass="form-control"/> 
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
							<form:input path="domicilio.codigoPostal.codigoPostal" maxlength="5"  cssClass="form-control"/>
							<span id="domicilio.codigoPostal.codigoPostalError" class="error hiddenElement"></span>
							
							<form:hidden path="domicilio.vialidadPrimaria.tipoVialidad.descripcion" />	
						</td>
					</tr>
				</table>
			</div>
			<c:if test="${requiereDocs}">
					<div class="separadorseccion">
						<span>
							Documentos Probatorios
						</span>
					</div>
					<div class="alert alert-success">
						Este tr&aacute;mite requiere la captura de documentos probatorios, de clic en el bot&oacute;n "Captura de documentos Probatorios" para proceder a la misma, no podr&aacute; 
						finalizar el tr&aacute;mite hasta completarla.<br><br>
						<a id="capturarDocumentosProrroga" class="btn btn-default"><i class="glyphicon glyphicon-file"></i> Captura de documentos probatorios</a>
					</div>
				</c:if>
		</form:form>
		</c:if>
	</div>

	<div class="pie">
		<div class="opciones">
			<div class="btn-group">
				<a href="#" class="btn btn-primary"><spring:message code="label.menus.opciones" /></a> <a href="#"
					data-toggle="dropdown" class="btn btn-primary dropdown-toggle"><span
					class="caret"></span></a>
				<ul class="dropdown-menu">
					<li><a id="finalizarTramite"><i class="glyphicon glyphicon-ok"></i> Finalizar Tr&aacute;mite</a></li>
					<li><a id="guardarTramite"><i class="glyphicon glyphicon-download-alt"></i> Guardar Tr&aacute;mite</a></li>
					<li><a id="guardarCerrarTramite"><i class="glyphicon glyphicon-remove"></i> Guardar y Cerrar Tr&aacute;mite</a></li>
					<li><a id="cancelarTramite"><i class="glyphicon glyphicon-trash"></i> Cancelar Tr&aacute;mite</a></li>
				</ul>
			</div>
		</div>
	</div>
</div>

<!-- Divs para dialogos de mensajes -->
<div id="dialog-confirm-cancelar" title="Confirmar cancelaci&oacute;n de solicitud">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>
			¿Desea cancelar la solicitud pendiente con folio: <strong>${folioSolicitud}</strong>?
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
	<p><span id="textoConfirmacion"></span></p>
</div>

<div id="dialog-error" title="Error">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span> <label
			id="mensajeError"></label>
	</p>
</div>