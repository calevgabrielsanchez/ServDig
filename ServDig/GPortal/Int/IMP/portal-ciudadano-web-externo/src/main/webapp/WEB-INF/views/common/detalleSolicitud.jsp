<%@ include file="../general/taglibs.jsp"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="idEstadoAtendida" value="<%=EstadoSolicitudEnum.ATENDIDA.getCodigo()%>" />
<c:set var="contadorTramites" value="0" scope="page" />

<script type="text/javascript">
	history.go(1);
</script>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/common/detalleSolicitud.js" htmlEscape="true" />"></script>

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
}

.alert-temp {
	background-color: #f8f8f8;
	border-color: #d9d9d9;
	color: #black;
}

.icono-tramite {
	font-size: 2em;
}

.icono-tramite a {
	color: #545454;
	text-decoration: none;
}

.icono-tramite a:hover {
	color: black;
}

</style>

<div>
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
						</span>
	            		<button style="float: right" class="btn btn-link salir" id="logout">Salir</button>
					</div>
 			 	</div>
			</div>
		
			<jsp:include page="../common/paginadorClinica.jsp">
					<jsp:param name="paso" value="${keyCveTipoTramite == 48 ? 5:4}" />
					<jsp:param name="tipoTramite" value="${keyCveTipoTramite}"/>
			</jsp:include>

			
			<div class="row m-t-sm">
				<div class="col-sm-12">
					<div class="alert alert-success">
						<strong>Haz finalizado tu tr&aacute;mite con &eacute;xito</strong>
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="col-sm-6">
					Estos son los datos de tu tr&aacute;mite en l&iacute;nea:
				</div>
				<div class="col-sm-6" style="text-align: right;">
					<span><a onclick="imprimirAcuse()">Imprimir comprobante</a></span>
					<span class="icono-tramite" id="iconImprimeAcuse"><a onclick="imprimirAcuse()" class="icon-printing"></a></span>
				</div>
			</div>
			<input type="hidden" id="keyHomoclaveTramite" value="${keyHomoclaveTramite}"/>
			<div class="row m-t-md">
				<div class="col-sm-12">
					<div class="table-responsive">
						<table id="tblDetalleSolicitud" class="table table-bordered table-striped" cellpadding="0"
							cellspacing="0" border="0">
							<thead>
								<tr>
									<th>Folio</th>
									<th>Fecha y hora</th>
									<th>Cl&iacute;nica asignada</th>
									<th>Direcci&oacute;n de la cl&iacute;nica o UMF</th>
									<th>Turno</th>
									<th>Consultorio</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>${keySolicitudSession.noFolioSolicitud}</td>
									<td>
										<fmt:formatDate value="${keySolicitudSession.fechaSolicitud}" pattern="dd/MM/yyyy hh:mm a" />
									</td>
									<td>${keyDatosUmfDom.medicoEnTurno.unidadMedicaFamiliar.descripcion}</td>
									<td>
										<div class="row">
											<div class="col-sm-12">
												${keyDatosUmfDom.medicoEnTurno.unidadMedicaFamiliar.desDireccion}
											</div>
										</div>
										<div class="row m-t-sm">
											<div class="col-sm-12 text-center">
												
													<a class="btn btn-primary" target="_blank"
														id="btnUbicarClinica" onclick="return localizarUmf('${keyLatitudUmf}','${keyLongitudUmf}')">
														<span class="glyphicon glyphicon-map-marker"></span>
														Direcci&oacute;n UMF
													</a>
												
											</div>
										</div>
									</td>
									<td>${keyDatosUmfDom.medicoEnTurno.turno.descripcion}</td>
									<td>${keyDatosUmfDom.medicoEnTurno.consultorio.idConsultorio}</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-sm-12">
					<h3>Siguientes pasos:</h3>
					Imprime los documentos generados y pres&eacute;ntate directamente en tu consultorio asignado.
				</div>
			</div>


			<div class="row m-t-lg">
				<div class="col-sm-6 col-xs-12 m-b-sm">
					<c:if test="${keySolicitudSession.estadoSolicitud.idEstadoSolicitud == idEstadoAtendida}">
						<table id="tblDetalleSolicitud" style="width: 100%;" class="table table-bordered table-striped" cellpadding="0"
							cellspacing="0" border="0">
							<thead>
								<tr>
									<th>#</th>
									<th>Documento</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="tramite" items="${keySolicitudSession.tramites}">
									<c:forEach var="documentoPT" items="${tramite.documentoPorTipos}">
										<tr>
											<td class="text-center"><c:set var="contadorTramites" value="${contadorTramites + 1}" scope="page"/>${contadorTramites}</td>
											<td>
												<div class="container-fluid">
													<div class="row">
														<div class="col-sm-12">${documentoPT.documento.desDocumento}</div>
													</div>
													<div class="row m-t-xs">
														<div class="col-xs-6 text-center">
															<div class="row icono-tramite">
																<div class="col-sm-12">
																	<a class="icon-printing"
																		onclick="abrirDocumentoResultante('${tramite.tramiteIdHashed}','${documentoPT.idDocumentoPorTipoHashed}')"></a>
																</div>
															</div>
															<div class="row link-tramite">
																<div class="col-sm-12">
																	<a
																		onclick="abrirDocumentoResultante('${tramite.tramiteIdHashed}','${documentoPT.idDocumentoPorTipoHashed}')">Imprimir</a>
																</div>
															</div>
														</div>
														<div class="col-xs-6 text-center">
															<div class="row icono-tramite">
																<div class="col-sm-12">
																	<a class="glyphicon glyphicon-download-alt"
																		onclick="abrirDocumentoResultante('${tramite.tramiteIdHashed}','${documentoPT.idDocumentoPorTipoHashed}')"></a>
																</div>
															</div>
															<div class="row link-tramite">
																<div class="col-sm-12">
																	<a
																		onclick="abrirDocumentoResultante('${tramite.tramiteIdHashed}','${documentoPT.idDocumentoPorTipoHashed}')">Descargar</a>
																</div>
															</div>
														</div>
													</div>
												</div>
											</td>
										</tr>
									</c:forEach>
								</c:forEach>
							</tbody>
						</table>
					</c:if>
					<form id="solicitudDocumentoForm" name="solicitudDocumentoForm" method="get" target="_blank" class="formNotBlock"
						action="${contextpath}/solicitud/mostrarDocumentoResultante">
						<input type="hidden" name="idTramite" id="idTramite" />
						<input type="hidden" name="tipoDocumento" id="tipoDocumento" />
					</form>
				</div>
				<div class="col-sm-6 col-xs-12">
					<div class="alert alert-info col-sm-12" style="text-align: left;">
						<h4>
							<strong>Servicios digitales relacionados:</strong>
						</h4> 
						<ul class="nuevosTramites">
							<li>
								<a id="cambioClinica" class="alert-link"
									href="/portal-ciudadano-web-externo/derechohabientes/tramite/registroHijos">Registro de hijos </a>
							</li>
							<li>
								<a id="cambioClinica" class="alert-link"
									href="/portal-ciudadano-web-externo/derechohabientes/tramite/cambioClinica">Cambio
									de cl&iacute;nica </a>
							</li>
							<li>
								<a id="consultaVigencia"  class="alert-link" href="/gestionAsegurados-web-externo/consultaVigencia/homeVigencia">Consulta tu vigencia de derechos</a>
							</li>
						</ul>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-sm-12" style="text-align: right;">
						<button class="btn btn-danger" type="button" id="cerrarDetalle">
              <span class="glyphicon glyphicon-log-out"></span> Salir del tr&aacute;mite
						</button>
				</div>
			</div>

			<jsp:include page="pieUmf.jsp">
				<jsp:param name="tipoTramite" value="true" />
			</jsp:include>
		</c:otherwise>
	</c:choose>
</div>
<div id="dialog-error"></div>
<div id="dialog-info"></div>

<script language="JavaScript1.2" src="${staticResourcesPath}/js/comscore/Form.js"></script>