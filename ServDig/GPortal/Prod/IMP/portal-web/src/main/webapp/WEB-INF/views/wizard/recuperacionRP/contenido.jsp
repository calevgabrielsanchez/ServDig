<%@ include file="../../general/taglibs.jsp" %>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<c:set var="contextPath" value="<%=request.getContextPath()%>" />

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/wizard/recuperacionRP/contenido.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/procesaErrores.js" htmlEscape="true" />"></script>

<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="col-sm-12">
		<c:choose>
			<c:when test="${empty error }">			
				<script>
					
					var codigoTipoSolicitud = ${codigoTipoSolicitud};
					var descripcionTipoSolicitud = '${descripcionTipoSolicitud}';
					var arrayCodigoTipoTramite = ${codigoTipoTramite};
					var PATRONES_AGREGADOS = ${numeroPatrones};

					var datosEntradaFirma = {
						fechaElectronica : '${datosFirmaElectronica.fechaElectronicaFormateada}',
						nombreCompleto : '${datosFirmaElectronica.nombreCompleto}',
						registroPatronal : '${datosFirmaElectronica.registroPatronal}',
						rfc : '${datosFirmaElectronica.rfc}',
						curp : '${datosFirmaElectronica.curp}'
					};
										
				</script>			
			
				<input type="hidden" id="idSolicitud" value="${solicitud.solicitudId}" />
				<input type="hidden" id="contenidoFirmar" value="${contenidoFirmar}" /> 
				<input type="hidden" id="folioSolicitud" value="${solicitud.noFolioSolicitud}" />
					
				<div class="alert alert-success">
					<c:choose>
						<c:when test="${!isRetomar}">
						Su solicitud ha iniciado correctamente y su n&uacute;mero de folio es <strong>${solicitud.noFolioSolicitud}</strong>
						</c:when>
						<c:otherwise>
						El folio de la solicitud que esta retomando es: <strong>${solicitud.noFolioSolicitud}</strong>
						</c:otherwise>
					</c:choose>

				</div>

				<p>A continuaci&oacute;n introduzca la informaci&oacute;n del
					registro patronal a recuperar:</p>
				
				<div id="patronesWrapper">
					<fieldset>
						<div id="seccionPatr&oacute;n">
							<div class="separadorseccion">
								<span> Datos del patr&oacute;n</span>
							</div>
						</div>
						
						<br>
						<div>
							<form:form modelAttribute="sujetoObligadoBusqueda" id="forma"
								method="post" class="form-horizontal" role="form">

								<div class="form-group">
									<label for="numeroRegistroPatronalError"
										class="col-sm-3 control-label"> <span class="required">*</span>N&uacute;mero
										de Registro Patronal (NRP):
									</label>
									<div class="col-sm-8">
										<form:input path="numeroRegistroPatronal" maxlength="11"
											cssClass="form-control" />
										<span id="numeroRegistroPatronalError"
											class="error hiddenElement"></span>
									</div>
								</div>
								<div class="form-group">
									<label for="subdelegacion.delegacion.id"
										class="col-sm-3 control-label"> <span class="required">*</span>Delegaci&oacute;n:
									</label>
									<div class="col-sm-8">
										<combo:creaCombo idHtml="subdelegacion.delegacion.id"
											idHtmlContenedor="forma"
											entidad="mx.gob.imss.ctirss.delta.persistence.DicDelegacion"
											mostrarSoloActivos="true" cssClassname="form-control" />
										<span id="subdelegacion.delegacion.idError" class="error hiddenElement"></span>
									</div>
								</div>
							
								<div class="form-group">
									<label for="subdelegacion.id" class="col-sm-3 control-label">
										<span class="required">*</span>Subdelegaci&oacute;n:
									</label>
									<div class="col-sm-8">
										<combo:creaCombo idHtml="subdelegacion.id"
											idHtmlContenedor="forma"
											entidad="mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion"
											idHtmlPadre="subdelegacion.delegacion.id"
											entidadPadre="dicDelegacion.cveIdDelegacion"
											mostrarSoloActivos="true" cssClassname="form-control"/>
										<span class="error hiddenElement"id="subdelegacion.idError"></span>
									</div>
								</div>
								<div class="form-group">
									<label for="stringClasificacion" class="col-sm-3 control-label">
										<span class="required">*</span>Clasificaci&oacute;n:
									</label>
									<div class="col-sm-8">
										<form:input path="stringClasificacion" maxlength="4"
											cssClass="numericoSinPunto form-control" />
										<span id="stringClasificacionError"
											class="error hiddenElement"></span>
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-offset-3 col-sm-9" >
										<input type="button" class="btn btn-primary"
											id="agregarPatronRecuperar" value="AGREGAR PATRON"
											style="float: right;" />
									</div>
								</div>

								
							</form:form>
						</div>
					</fieldset>

					<br>
					<fieldset>
			
						<div id="seccionPatronesaRecuperar">
							<div class="separadorseccion">
								<span> Patrones a recuperar</span>
							</div>
						</div>
						<br>
						<div id="sujetosObligados">
							<table id="tabla-patrones" style="width: 100%;"
								class="table table-striped table-bordered" cellpadding="0"
								cellspacing="0" border="0">
								<thead>
									<tr>
										<th>NRP</th>
										<th>Nombre comercial</th>
										<th>Quitar</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${sujetos}" var="sujetoO">
										<tr>
											<td>${sujetoO.numeroRegistroPatronal}
												${sujetoO.modalidad.numModalidad} ${sujetoO.digVerificador}
											</td>
											<td><c:if test="${sujetoO.nombreComercial ne null }">
												${sujetoO.nombreComercial}
											</c:if></td>
											<td><input type='button' value='Quitar'
												class="btn btn-secondary"
												onclick='eliminarPatronLista(${sujetoO.cveIdSujetoObligado})' />
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</fieldset>
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
	<br>
	<div class="pie row">
		<div class="opciones col-sm-6">
			<c:if test="${empty error}">
				<div class="btn-group dropup">
					<a href="#" class="btn btn-primary"><spring:message
							code="label.menus.opciones" /> </a> <a href="#"
						data-toggle="dropdown" class="btn btn-primary dropdown-toggle"><span
						class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a id="finalizarTramite"><i class="glyphicon glyphicon-ok"></i>
								Finalizar Tr&aacute;mite</a></li>
						<li><a id="guardarTramite"><i class="glyphicon glyphicon-download-alt"></i>
								Guardar Tr&aacute;mite</a></li>
						<li><a id="cancelarTramite"><i class="glyphicon glyphicon-trash"></i>
								Cancelar Tr&aacute;mite</a></li>
					</ul>
				</div>
			</c:if>
			
		</div>
		
		
	<div class="controles col-sm-6">
		<div class="pull-right">

			<button class="btn btn-default" id="cerrarWizard">CERRAR</button>

		</div>
	</div>
	
	</div>

</div>

<input type="hidden" id="folioSolicitud" value="${solicitud.noFolioSolicitud}">

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

<div id="setNombreComercialDiv"></div>