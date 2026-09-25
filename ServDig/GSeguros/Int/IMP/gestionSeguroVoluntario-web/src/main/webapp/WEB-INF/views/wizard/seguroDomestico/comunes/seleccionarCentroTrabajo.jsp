<%@ include file="../../../general/taglibs.jsp"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/seguroDomestico/comunes/seleccionarCentroTrabajo.js" htmlEscape="true" />"></script>

<c:set var="contextPath" value="<%=request.getContextPath()%>" />

<style>
	.ui-selectable li {
	    padding: 15px 25px;
	}
</style>

<script type="text/javascript">
	<!--
	ventanilla = ${esVentanilla};
	var tieneSeguros = ${tieneSeguros};
	//-->
</script>
<div class="contenedor col-sm-12">
	<div class="contenido row">
		<c:if test="${tipoOperacion != 'RENOVACION' }">
        <jsp:include page="pasosDomestico.jsp">
            <jsp:param name="paso" value="1" />
        </jsp:include>
		</c:if>
		<div class="col-sm-12">
			<div class="titulo separadorseccion">
				<span>Centro de trabajo</span>
			</div>
			<div class="alert alert-danger" style="display: none;" id="validacion">
				<span id="mensaje-validacion">Selecciona un Centro de trabajo</span>
			</div>
			<c:choose>
				<c:when test="${empty patron.registrosPatronales || empty patron.registrosPatronales.registrosPatronal}">
					<p>No tienes registrado ning&uacute;n centro de trabajo</p>
				</c:when>
				<c:otherwise>
					<p>
						Selecciona el Centro de trabajo:
					</p>
					<ol id="listCentroTrabajo" class="m-b-lg">
						<c:forEach items="${patron.registrosPatronales.registrosPatronal}" var="registroPatronal" varStatus="status">
							<li class="ui-state-default"
								idCentroTrabajo="${registroPatronal.centrotrabajo.idDomicilio}"
								numeroRegistroPatronal="${registroPatronal.numeroRegistroPatronal}${registroPatronal.modalidad.numModalidad}${registroPatronal.digitoVerificador}"
								idModalidad="${registroPatronal.modalidad.idModalidad}"
								numModalidad="${registroPatronal.modalidad.numModalidad}"
								digitoVerificador="${registroPatronal.digitoVerificador}"
								claveAsentamiento="${registroPatronal.centrotrabajo.asentamiento.clave}"
								claveLocalidad="${registroPatronal.centrotrabajo.asentamiento.localidad.clave}"
								claveMunicipio="${registroPatronal.centrotrabajo.asentamiento.localidad.municipio.clave}"
								claveEntidadFederativa="${registroPatronal.centrotrabajo.asentamiento.localidad.municipio.entidadFederativa.clave}"
								>
								<h5>${registroPatronal.centrotrabajo.descripcion}</h5>
							</li>
						</c:forEach>
					</ol>
				</c:otherwise>
			</c:choose>
			<form:form id="nextStepForm"
				cssClass="form-horizontal"
				modelAttribute="registroPatronal"
				action="${contextPath}/wizard/seguroDomestico/comunes/seleccionarCentroTrabajo">
				<form:hidden id="inputIdCentroTrabajo" path="centrotrabajo.idDomicilio" />
				<form:hidden id="inputNumeroRegistroPatronal" path="numeroRegistroPatronal"/>
				<form:hidden id="inputIdModalidad" path="modalidad.idModalidad"/>
				<form:hidden id="inputNumModalidad" path="modalidad.numModalidad"/>
				<form:hidden id="inputDigitoVerificador" path="digitoVerificador"/>
				<form:hidden id="inputClaveAsentamiento" path="centrotrabajo.asentamiento.clave"/>
				<form:hidden id="inputClaveLocalidad" path="centrotrabajo.asentamiento.localidad.clave"/>
				<form:hidden id="inputClaveMunicipio" path="centrotrabajo.asentamiento.localidad.municipio.clave"/>
				<form:hidden id="inputClaveEntidadFederativa" path="centrotrabajo.asentamiento.localidad.municipio.entidadFederativa.clave"/>
			</form:form>
			<form:form id="agregarCentroTrabajoForm"
				action="${contextPath}/wizard/seguroDomestico/comunes/agregarCentroTrabajo">
			</form:form>
		</div>
	</div>
	<div class="pie row">
		<div class="opciones col-sm-6">
			<div class="btn-group dropup">
				<button type="button" class="btn btn-primary">Acciones</button>
				<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
					<span class="caret"></span>
				</button>
				<ul class="dropdown-menu">
					<li><a id="agregarDomicilio" href="#">Agregar domicilio</a></li>
				</ul>
			</div>
		</div>
		<div class="controles col-sm-6">
			<div class="pull-right">
				<button id="cancelarTramite" class="btn btn-default">
					<c:choose>
						<c:when test="${esVentanilla and tieneSeguros}">Regresar</c:when>
						<c:otherwise>Cerrar</c:otherwise>
					</c:choose>
				</button>
				<a id="siguientePaso" class="btn btn-primary"><i class="glyphicon glyphicon-step-forward"></i> Siguiente</a>
			</div>
		</div>
	</div>
</div>
