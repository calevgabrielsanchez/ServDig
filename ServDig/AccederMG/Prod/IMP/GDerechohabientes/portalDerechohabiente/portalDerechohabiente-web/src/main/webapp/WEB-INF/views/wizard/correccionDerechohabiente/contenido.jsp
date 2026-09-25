<%@ include file="../../general/taglibs.jsp" %>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<%-- <%@taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%> --%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/wizard/correccionDerechohabiente/integracionICACorreccion.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/wizard/correccionDerechohabiente/contenido.js" htmlEscape="true" />"></script>
	
<jsp:include page="../../common/llenaSexo.jsp"></jsp:include>
<jsp:include page="../../common/llenarRazonRegistro.jsp"></jsp:include>

<jsp:include page="../general/llenaParentescos.jsp"></jsp:include>
<jsp:include page="../general/llenaEstadoCivil.jsp"></jsp:include>



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


<c:set var="contextPath" value="<%=request.getContextPath()%>" />
<div class="contenedor">
	
	<input type="hidden" value="${solicitud.solicitudId}" id="idSolicitud">
	<input type="hidden" value="${requiereDocs ? 1 : 0}" id="requiereDocs"/>
	
	<div class="contenido" style="width: 100%;">
		<div class="titulo" align="center">
			<span> ${descripcionTipoSolicitud}</span>
		</div>
		<c:choose>
		<c:when test="${empty error }">
				<input type="hidden" id="idSolicitud" value="${solicitud.solicitudId}"/>
				<div class="alert alert-success">
				<c:choose>
					<c:when test="${!isRetomar}">
						Su solicitud ha iniciado correctamente y su n&uacute;mero de folio es <strong>${solicitud.noFolioSolicitud}</strong>
					</c:when>
					<c:otherwise>
						El folio de la solicitud que esta retomando es: <strong>${solicitud.noFolioSolicitud}</strong>
					</c:otherwise>
				</c:choose>
				<input type="hidden" id="contenidoFirmar" value="${contenidoFirmar}" />
				<input type="hidden" id="folioSolicitud" value="${solicitud.noFolioSolicitud}" />
				</div>
					
				<jsp:include page="../../common/datosDerechohabiente.jsp"></jsp:include>

				<div class="separadorseccion">
					<span>
						Datos de la correcci&oacute;n de datos
					</span>
				</div>
				
				<form:form id="formularioCorreccionDatosDerechohabiente" method="POST" commandName="derechohabiente" role="form">
					<form:hidden path="idPersona" />
					<form:hidden path="nss" />
					<form:hidden path="tramiteId" />
					<form:hidden path="tipoTramite.idTipoTramite" />
					<form:hidden path="domicilio.clave" />
					<table id="registroD" class="table table-striped table-bordered" >
						<tr>
							<td>
								<label class="control-label" for="curpCap">
									<span class="required">*</span>&nbsp;<spring:message code="label.curp" /> :
								</label>
							</td>
							<td>
								<form:input path="curpCap" cssClass="form-control" disabled="true"/> 
								<span><form:errors path="curpCap" cssStyle="color:red" /></span>
							</td>
							<td colspan="2">
								<input type="button" class="btn btn-primary"
													value="Identificar Cambios" id="llamarIca1">
							</td>
						</tr>
						<tr>
							<td>
								<label class="control-label" for="fisica.nombre">
									<span class="required">*</span>&nbsp;<spring:message code="label.nombre" /> :
								</label>
							</td>
							<td>
								<form:input path="nombre" cssClass="form-control" disabled="true"/>
								<span id="nombreError" class="error hiddenElement"></span>
							</td>
							
							<td>
								<label class="control-label" for="fisica.lugarNacimiento.clave">
									<span class="required">*</span>&nbsp;<spring:message code="label.lugarNac" /> :
								</label>
							</td>
							<td>
								<combo:creaCombo idHtml="lugarNacimiento.clave"
									idHtmlContenedor="formularioCorreccionDatosDerechohabiente"
									entidad="mx.gob.imss.ctirss.delta.persistence.DgCatEstado"
									idHtmlValor="${derechohabiente.lugarNacimiento.clave}" 
									mostrarSoloActivos="true"
									cssClassname="form-control"
								/>
							</td>
						</tr>
						<tr>
						<td>
							<label class="control-label" for="fisica.primerApellido">
							<span class="required">*</span>&nbsp;<spring:message code="label.primerApe" /> :
							</label>
						</td>
						<td>
							<form:input path="primerApellido" cssClass="form-control" disabled="true"/>
							<span id="primerApellidoError" class="error hiddenElement"></span>
						</td>
						<td>
							<label class="control-label" for="fisica.sexo.idSexo">
							<span class="required">*</span>&nbsp;<spring:message code="label.sexo" /> :
							</label>
						</td>
						<td>
							<combo:creaCombo idHtml="sexo.idSexo"
								idHtmlContenedor="formularioCorreccionDatosDerechohabiente"
								entidad="mx.gob.imss.ctirss.delta.persistence.DicSexo"
								idHtmlValor="${derechohabiente.sexo.idSexo}" 
								mostrarSoloActivos="true" 
								cssClassname="form-control"
							/>
						</td>
					</tr>
					<tr>
						<td>
							<label class="control-label" for="fisica.segundoApellido">
								<spring:message code="label.segundoApe" /> :
							</label>
						</td>
						<td>
							<form:input path="segundoApellido" cssClass="form-control" disabled="true"/>
							<span id="segundoApellidoError" class="error hiddenElement"></span>
						</td>
						<td>
							<label class="control-label" for="fisica.fechaNacimiento">
								<span class="required">*</span>&nbsp;<spring:message code="label.fechaNac" /> : 
							</label>
						</td>
						<td>
							<input type="text" id="fechaNacimiento"
								name="fechaNacimiento"
								value="<fmt:formatDate pattern="dd/MM/yyyy" value="${derechohabiente.fechaNacimiento}"/>"
								class=" form-control" disabled="disabled"
							/>
							<span id="fechaNacimientoError" class="error hiddenElement"></span>
						</td>
					</tr>
					</table>
					
				<div class="separadorseccion">
					<span>
						<spring:message code="titulo.mediosContacto" />
					</span>
				</div>
				<table class="table table-striped table-bordered">
					<tr>
						<td>
							<label class="control-label" for="fisica.correoElectronico.correo">
								<span class="required">*</span>&nbsp;<spring:message code="label.correo" /> :
							</label>
						</td>
						<td colspan="3">
							<form:hidden path="correoElectronico.clave"/>
							<form:input path="correoElectronico.correo" cssClass="form-control"/>
							<span id="correoElectronico.correoError" class="error hiddenElement"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class="control-label" for="facebook.cuenta">
								<spring:message code="label.face" /> :
							</label>
						</td>
						<td>
							<form:hidden path="facebook.clave"/>
							<form:input path="facebook.cuenta" cssClass = "form-control alfanumerico"  maxlength="45"/>
						</td>
						<td>
							<label class="control-label" for="twitter.cuenta">
								<spring:message code="label.twitter" /> :
							</label>
						</td>
						<td>
							<form:hidden path="twitter.clave"/>
							<form:input path="twitter.cuenta" cssClass = "form-control alfanumerico"  maxlength="45"/>
						</td>
					</tr>
					<tr>
						<td>
							<label class="control-label" for="telefonoFijo.claveLada">
								<spring:message code="label.telefono" /> :
							</label>
						</td>
						<td>
							<form:hidden path="telefonoFijo.clave"/>
							<form:input path="telefonoFijo.claveLada" cssClass="form-control numericoSinPunto" maxlength="10"/>
						</td>
						<td>
							<label class="control-label" for="telefonoMovil.numero">
								<spring:message code="label.movil" /> :
							</label>
						</td>
						<td>
							<form:hidden path="telefonoMovil.clave"/>
							<form:input path="telefonoMovil.numero" cssClass="form-control numericoSinPunto" maxlength="10"/>
						</td>
					</tr>
				</table>
									
			</form:form>

				<c:if test="${requiereDocs}">
					<div class="separadorseccion">
						<span>
							Documentos Probatorios
						</span>
					</div>
					<div class="alert alert-success">
						Este tr&aacute;mite requiere la captura de documentos probatorios, de clic en el bot&oacute;n "Captura de documentos Probatorios" para proceder a la misma, no podr&aacute; 
						finalizar el tr&aacute;mite hasta completarla.<br><br>
						<a id="capturarDocumentosCorreccionDatos" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"><i class="glyphicon glyphicon-file"></i> Captura de documentos probatorios</a>
					</div>
				</c:if>
		</c:when>
		<c:otherwise>
			<div class="alert alert-error">
					<button type="button" class="close" data-dismiss="alert">×</button>
					${error}
			</div>
		</c:otherwise>
		</c:choose>
	</div>
	<br>
	<div class="pie">
		<div class="opciones">
			<c:if test="${empty error}">
				<div class="btn-group">
					<a href="#" class="btn btn-primary"><spring:message code="label.menus.opciones" /> </a> <a href="#"
						data-toggle="dropdown" class="btn btn-primary dropdown-toggle"><span
						class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a id="finalizarTramite"><i class="glyphicon glyphicon-ok"></i>Finalizar Tr&aacute;mite</a></li>
						<li><a id="guardarTramite"><i class="glyphicon glyphicon-download-alt"></i> Guardar Tr&aacute;mite</a></li>
						<li><a id="guardarCerrarTramite"><i class="glyphicon glyphicon-remove"></i> Guardar y Cerrar Tr&aacute;mite</a></li>
						<li><a id="cancelarTramite"><i class="glyphicon glyphicon-trash"></i> Cancelar Tr&aacute;mite</a></li>
					</ul>
				</div>
			</c:if>
			<button class="btn btn-default" id="cerrarWizard">CERRAR</button>
		</div>
		<div class="controles"></div>

	</div>
</div>

<input type = "hidden" id="folioSolicitud" value="${solicitud.noFolioSolicitud}">

<!-- Divs para dialogos de mensajes -->
<div id="dialog-confirm-cancelar"
	title="Confirmar cancelaci&oacute;n de solicitud">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span> ¿Desea cancelar la
		solicitud pendiente con folio: <strong>${solicitud.noFolioSolicitud}</strong>?
	</p>
</div>

<div id="dialog-confirm" title="Mensaje">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span> <label
			id="mensajeDialogo"></label>
	</p>
</div>

<div id="dialog-error" title="Error">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span> <label
			id="mensajeError"></label>
	</p>
</div>

<div id="dialogICACorreccionDatosWeb1"></div>