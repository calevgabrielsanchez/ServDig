<%@ include file="../general/taglibs.jsp"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum" %>

<c:set var="tiempoEspera"><spring:message code="msg.time.wait" /></c:set>
<c:set var="tiempoIntervaloEspera"><spring:message code="msg.time.interval" /></c:set>

<link rel="stylesheet" href="<spring:url value="/static/resources/css/menuIdentidadSujeto.css" htmlEscape="true" />"/>

<jsp:include page="tipoTramiteEnum.jsp"></jsp:include>

<script>
	var _tiempoEspera = ${tiempoEspera};	
	var _tiempoIntervaloEspera = ${tiempoIntervaloEspera};
	var _ttc = '${tipoTramiteCifrado}';
	var _tf = '${tipoFiltro}';
	var _tramite = ${tramite};
</script>

<jsp:include page="scriptsVistaTramite.jsp"></jsp:include>

<div id="mainWrapper">
	<div id="busquedaContainer"></div>

	<div id="identidadSujetoContainer">
		<div class="row">
			<div class="col-md-12">
				<div id="accionesIdentidadContainer">
					<nav id="navbarIdentidad" class="navbar navbar-default navbar-static" role="navigation">
						<div class="container-fluid" style="background: gray; color: white;">
							<div class="navbar-header">
								<a style="color: white; pointer-events: none;" class="navbar-brand">Identidad</a>
							</div>
							<div class="collapse navbar-collapse bs-js-navbar-collapse">
								<ul class="nav navbar-nav navbar-right" id="accionesIdentidadWrapper" style="margin-right: -15px;">
									<li style="margin-right: 10px;">
										<a style="color: white; margin-right: -15px;" aria-expanded="false" role="" aria-haspopup="true"
											id="refrescarIdentidad">
											<i style="font-size: large;" class="glyphicon glyphicon-refresh"></i>
										</a>
									</li>
									<li id="accionesIdentidad" class="dropdown">
										<a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" role="" aria-expanded="false"
											style="color: white; margin-right: -15px;">
											Acciones
											<span class="caret"></span>
										</a>
										<ul class="dropdown-menu" role="menu" aria-labelledby="drop" style="margin-right: -16px;"
											id="menuAccionesIdentidad">
										</ul>
									</li>
								</ul>
							</div>
						</div>
					</nav>
				</div>
				<div id="identidadContainer" style="margin-top: 12.5px;"></div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div id="accionesSujetoContainer">
					<nav id="navbarSujeto" class="navbar navbar-default navbar-static" role="navigation">
						<div class="container-fluid" style="background: gray; color: white;">
							<div class="navbar-header">
								<a style="color: white; pointer-events: none;" class="navbar-brand">
								<c:choose>
									<c:when test="${changeTittleRP}">
										Registros Patronales
									</c:when>
									<c:otherwise>
										Sujeto
									</c:otherwise>
								</c:choose>
								</a>
							</div>
							<div class="collapse navbar-collapse bs-js-navbar-collapse">
								<ul class="nav navbar-nav navbar-right" id="accionesSujetoWrapper" style="margin-right: -15px;">
									<li style="margin-right: 10px;">
										<a style="color: white; margin-right: -15px;" aria-expanded="false" role="" aria-haspopup="true"
											id="refrescarSujeto">
											<i style="font-size: large;" class="glyphicon glyphicon-refresh"></i>
										</a>
									</li>
									<li id="accionesSujeto" class="dropdown">
										<a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" role="" aria-expanded="false"
											style="color: white; margin-right: -15px;">
											Acciones
											<span class="caret"></span>
										</a>
										<ul class="dropdown-menu" role="menu" aria-labelledby="drop" style="margin-right: -16px;"
											id="menuAccionesSujeto">
										</ul>
									</li>
								</ul>
							</div>
						</div>
					</nav>
				</div>
				<div id="sujetoContainer" style="margin-top: 12.5px;"></div>
			</div>
		</div>
	</div>

	<div id="infoTramiteContainer">
		<div class="row">
			<div class="col-md-6">
				<div id="descTramiteContainer">
					<h1>DESCRIPCI&Oacute;N DEL TR&Aacute;MITE</h1>
					<div class="well">
						<c:choose>
							<c:when test="${ not empty tramiteInfo}">
								<h4 class="separadorseccion text-center m-t-none">
									${tramiteInfo.tipoTramite.descripcion}
								</h4>
								<p>${tramiteInfo.descripcionTramite}</p>
							</c:when>
							<c:otherwise>
								<div class="alert alert-info" style="margin-bottom: 0px;">
									Este tr&aacute;mite no cuenta con descripci&oacute;n
								</div>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div id="docsRequeridosContainer">
					<h1>DOCUMENTOS REQUERIDOS</h1>
					<div class="well">
						<c:choose>
							<c:when test="${not empty documentos}">
								<table id="tablaDocumentos" style="width: 100%;" 
									class="table table-striped table-bordered" cellpadding="0"
									cellspacing="0" border="0">
									<c:forEach var="doctoReq" items="${documentos}">
										<thead>
											<tr>
												<th align="center">${doctoReq.titulo}</th>
											<tr>
										</thead>
										<tbody>
											<c:forEach var="docto" items="${doctoReq.documentos}">
												<tr>
													<td>${docto.desDocumento}</td>
												</tr>
											</c:forEach>
										</tbody>
									</c:forEach>
								</table>
							</c:when>
							<c:otherwise>
								<div class="alert alert-info" style="margin-bottom: 0px;">
									Para conocer los documentos requeridos, sonsulta la siguiente liga:
									<a href="http://www.imss.gob.mx/tramites/empleoincorporacion">http://www.imss.gob.mx/tramites/empleoincorporacion</a>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="iniciarTramiteContaniner">
		<div class="row">
			<div class="col-md-12">
				<div class="col-md-4 col-md-offset-4">
					<button type="button" id="buttonIniciarTramite"
						class="btn btn-primary btn-block"
						disabled="disabled" tabindex="4">INICIAR TR&Aacute;MITE</button>
				</div>
			</div>
		</div>
	</div>

	<div id="dialogoTramite"></div>
	<div id="domiciliosComponent"></div>
	<div id="procesandoSolicitudComponent"></div>
	<div id="detalleSolicitudComponent"></div>
	<div id="divCapturaDocs"></div>
	<div id="wizardDetalleSeguroComponent"></div>
	<div id="wizardDetalleSeguroDomesticoComponent"></div>
	<div id="wizardAltaSeguroVoluntario"></div>
	
	<%-- <input id="tiempo" type="hidden" value="00:00:00" name="tiempo" />
	<input id="urlServicio" type="hidden" value="${urlServicio}" name="urlServicio" />
	<input id="tiempoServicio" type="hidden" value="" name="tiempoServicio" /> --%>
</div>

<div id="dialogoMensajesGeneral">
	<p><span id="textoMensajeGeneral"></span></p>
</div>