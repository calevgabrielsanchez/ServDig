<%@ include file="../../general/taglibs.jsp"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.enums.TipoTramiteEnum"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="tipoTramiteModifDatosGrales" value="<%=TipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES.getCodigo()%>" />
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/actualizacion/actualizacion.js" htmlEscape="true" />"></script>

<script type="text/javascript">
	history.go(1);
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
			<div class="row">
				<div class="col-sm-12">
					<h2><spring:message code="asegurado.tramite.correccion.title" /></h2>
					<hr class="red">
				</div>
			</div>
			<c:if test="${not empty error}">
				<div class="alert alert-danger">
					<strong>Error: </strong>${error}
				</div>
			</c:if>
			<jsp:include page="../../common/paginadorGenerico.jsp">
				<jsp:param name="pasos" value="1,2,3" />
				<jsp:param name="activo" value="2" />
				<jsp:param name="mensaje" value="Confirmacion" />
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
					<h4>PASO 2: Confirmaci&oacute;n de datos</h4>
				</div>
			</div>
			<input type="hidden" value="${keyFormConNSS?'nss':'sinNSS'}" id="conSinNSS"/>
			<div class="row m-t-xl">
				<div class="col-sm-12">
					<form id="datosPersonaForm" action="#" class="form-horizontal" method="post" role="form">

						<div class="row">
							<div class="col-md-6 col-sm-12">
								<h4>
									<strong>Datos localizados en el instituto:</strong>
								</h4>
								<div class="form-group">
									<label class="col-sm-5 control-label" style="text-align: left;" for="nssPersona">
										NSS :
									</label>
									<div class="col-sm-7">
										<input class="form-control" id="nssPersona" type="text" value="${ciudadano.strNss}" readonly="readonly" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-5 control-label" style="text-align: left;" for="fisicaAnterior.curp">
										CURP :
									</label>
									<div class="col-sm-7">
										<input class="form-control" id="fisicaAnterior.curp"
											name="fisicaAnterior.curp" type="text" readonly="readonly" 
											value="${empty keyTramiteCambioCurp.fisicaAnterior.curp ? "Sin dato": keyTramiteCambioCurp.fisicaAnterior.curp}"/>
		
									</div>
								</div>
							</div>
							<div class="col-md-6 col-sm-12">
								<h4>
									<strong>Datos a actualizar:</strong>
								</h4>
								
								<div class="form-group">
									<label class="col-sm-5 control-label" style="text-align: left;" for="fisicaNueva.fechaNacimiento">
										Fecha nacimiento :
									</label>
									<div class="col-sm-7">
										<input class="form-control" value="<fmt:formatDate value="${keyTramiteCambioCurp.fisicaNueva.fechaNacimiento}" pattern="dd/MM/yyyy" />" 
										name= "fisicaNueva.fechaNacimiento" id="fisicaNueva.fechaNacimiento" type="text" readonly="readonly" />
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-5 control-label" style="text-align: left;" for="fisicaNueva.curp">
										CURP :
									</label>
									<div class="col-sm-7">
										<input class="form-control" id="fisicaNueva.curp"
											name="fisicaNueva.curp" type="text" readonly="readonly" 
											value="${keyTramiteCambioCurp.fisicaNueva.curp}"/>
									</div>
								</div>
								
							</div>
						</div>
						

						
						<div class="row">
							<div class="col-sm-12 text-right">
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
					<jsp:include page="../../common/pieUmf.jsp">
						<jsp:param name="tipoTramite" value="true" />
					</jsp:include>
				</div>
			</div>
		
</div>
<div id="dialog-confirm"></div>
<div id="dialog-error"></div>