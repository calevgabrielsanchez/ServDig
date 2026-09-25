<%@ include file="../../../general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.domicilio.TipoBusquedaVialidadEnum"%>


<c:set var="contextpath" value="<%=request.getContextPath()%>" scope="request" />

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/domicilios/wizard/general/funcionesComunes.js" htmlEscape="true" />"></script>

<!-- dependencias para componente de domicilio -->
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/domicilios/wizard/derechohabiente/domicilioCambioDerechohabienteInit.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/domicilios/recortado/DomicilioRecortadoCtrl.js" htmlEscape="true" />"></script>



<c:choose>
	<c:when test="${not empty idDelegacion}">
		<c:set var="urlRegreso"
			value="${contextpath}/domicilio/nacional/ubicar/delegacion?idDelegacion=${idDelegacion}" ></c:set>
	</c:when>
	<c:when test="${not empty FROM_WIZARD}">
		<c:set var="urlRegreso"value="${contextpath}/wizard/domicilio/regresar"></c:set>
	</c:when>
	<c:when test="${not empty idUmfUsuarioSession}">
		<c:set var="urlRegreso" value="${contextpath}/domicilio/nacional/ubicar/byUmf?idUmfUsuario=${idUmfUsuarioSession}&idUmfPersona=${idUmfPersonaSession}&tipoTramite=${idTipoTramiteSession}"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="urlRegreso" value="${contextpath}/domicilio/nacional/ubicar"></c:set>
	</c:otherwise>
</c:choose>

<c:choose>
	<c:when test="${not empty FROM_WIZARD}">
		<c:set var="urlUbicarDom" value=""></c:set>
	</c:when>
	<c:when test="${not empty FROM_ADMON_DOMICILIO}">
		<c:set var="urlUbicarDom"
			value="${contextpath}/domicilio/administrar/particular/confirmar-modificacion/${indexDomicilio}"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="urlUbicarDom"
			value="${contextpath}/domicilio/nacional/ubicar/complemento/guardar"></c:set>
	</c:otherwise>
</c:choose>

<script type="text/javascript"	src="${staticResourcesPath}/js/bootstrap/bootstrap-typeahead.js"></script>

<script>	
	var TIPO_BUSQUEDA_VIALIDAD = <%=TipoBusquedaVialidadEnum.VIALIDAD.getCodigo()%>;
	var TIPO_BUSQUEDA_VIALIDAD_NL = <%=TipoBusquedaVialidadEnum.VIALIDAD_NO_LOCALIZADA.getCodigo()%>;
	var TIPO_BUSQUEDA_CARRETERA = <%=TipoBusquedaVialidadEnum.CARRETERA.getCodigo()%>;
	var TIPO_BUSQUEDA_CAMINO = <%=TipoBusquedaVialidadEnum.CAMINO.getCodigo()%>;
</script>


<form:form action="${urlRegreso}" method="POST" id="formRegreso"
			modelAttribute="domicilio">
			<c:if test="${not empty FROM_CODIGO_POSTAL }">
				<!-- Para búsqueda por código postal -->
				<form:hidden path="asentamiento.clave" />
				<form:hidden path="asentamiento.localidad.municipio.clave" />
				<form:hidden path="asentamiento.localidad.municipio.entidadFederativa.clave" />
				<form:hidden path="localidad.municipio.clave" />
				<form:hidden path="localidad.municipio.entidadFederativa.clave" />
			</c:if>
			<c:if test="${not empty FROM_MUNICIPIO }">
				<!-- Para búsqueda por municipio -->
				<form:hidden path="asentamiento.clave" />
				<form:hidden path="asentamiento.localidad.municipio.entidadFederativa.clave" />
				<form:hidden path="asentamiento.localidad.municipio.clave" />
				<form:hidden path="localidad.municipio.entidadFederativa.clave" />
				<form:hidden path="localidad.municipio.clave" />
			</c:if>
</form:form>
	


<div class="col-sm-12">
	<div>
		<div>
		<c:if test="${not empty errorFormGeneral}">
			<form:hidden path="errorFormGeneral" />
			<div class="alert alert-danger">
				<button type="button" class="close" data-dismiss="alert" onclick="uid_call('imss.gestion.domicilios.general.btn_cerrar','clickin')">×</button>
				<strong>Error: </strong>${errorFormGeneral}
			</div>
		</c:if>
		<div class="alert alert-danger" style="display: none" id="divErrorCampos"></div>
		<c:if test="${empty errorFormGeneral}">
			
			<div class="row">
				<div class="col-xs-12">
					<h3>Registrar domicilio geogr&aacute;fico</h3>
				</div>
			</div>
			<div class="alert alert-info">
				<c:choose>
					<c:when test="${!isRetomar}">
						<spring:message code="label.solicitud.iniciada" arguments="${solicitudRegistro.noFolioSolicitud}"/>
					</c:when>
					<c:otherwise>
						<spring:message code="label.solicitud.retomando" arguments="${solicitudRegistro.noFolioSolicitud}"/>
					</c:otherwise>
				</c:choose>
			</div>

			<span id="errorNegocioLabel" class="error hiddenElement"></span>
			
			
			<input type="hidden" id="nuevoDomicilio"  value="${nuevoDomicilio?1:0}"/>
		
		<form:form action="${urlUbicarDom}" method="POST"
			modelAttribute="domicilio" id="formComplemento">
		
		<form:hidden path="estadoAdministracionDomicilio" />
		<form:hidden path="clave" />
		<form:hidden path="tipoDomicilio.clave" />
		<form:hidden path="dicTipoDomicilio.clave" />
		<form:hidden path="tipoBusquedaVialidad"/>
		
			
		<!-- datos de la calle -->
		<form:hidden path="calle"/>
		<form:hidden path="vialidadPrimaria.nombre"/>
		<form:hidden path="vialidadPrimaria.clave" />
		<form:hidden path="vialidadPrimaria.tipoVialidad.clave" />
		<form:hidden path="vialidadPrimaria.tipoVialidad.descripcion" />
		<!-- tipo de busqueda realizada -->
		<form:hidden path="tipoBusquedaVialidad"/>
		
		<form:hidden path="codigoPostal.codigoPostal"/>
		<!-- Datos del asentamiento -->
		<form:hidden path="asentamiento.nombre"/>
		<form:hidden path="asentamiento.clave" />
		<!-- seteo del codigo postal en asentiamiento por logica de nogocio -->
		<form:hidden path="asentamiento.codigoPostal.codigoPostal" />
		<!-- Datos de la localidad -->
		<form:hidden path="asentamiento.localidad.nombre"/>
		<form:hidden path="asentamiento.localidad.clave" />
		<!-- Datos del municipio -->
		<form:hidden path="asentamiento.localidad.municipio.nombre"/>
		<form:hidden path="asentamiento.localidad.municipio.clave" />
		<!-- Datos de la entidad federativa -->
		<form:hidden path="asentamiento.localidad.municipio.entidadFederativa.nombre"/>
		<form:hidden path="asentamiento.localidad.municipio.entidadFederativa.clave" />
		
		<form:hidden path="numExterior1"/>
		<form:hidden path="numExteriorAlf" />
		<form:hidden path="numInterior"/>
		<form:hidden path="numInteriorAlf"/>
		</form:form>
		
			
			<br/>
		<table style="width: 100%" class="table table-striped table-bordered tbl-domCentroTrabajo" >
				 <tr>
				 	<td>
					<div id="domicilioDerechohabienteDiv"></div>
					</td>
				</tr>
		</table>	
		
		</c:if>
		</div>
	</div>
	
	<div class="row">
		<c:choose>
			<c:when test="${not empty FROM_WIZARD}">

			</c:when>
			<c:when test="${not empty FROM_ADMON_DOMICILIO}">
				<fieldset class="fsInterno">
					<button type="button" style="margin-top: 5px;"
						class="btn btn-secondary" target="regresar" id="btnModificar">
						<spring:message code="label.btn.modificar" />
					</button>
				</fieldset>
			</c:when>
			<c:otherwise>
				<div class="col-md-6 text-left" style="padding: 15px"><span class="required"  id="labelCamposObligatoriosGeneral">*</span> Campos obligatorios</div>
				<div class="col-md-6 text-right">
						<button type="button" style="margin-top: 5px;"
							class="btn btn-default" target="regresar" id="regresar">
							<spring:message code="label.btn.regresar" />
						</button>
						<button type="submit" style="margin-top: 5px;"
							class="btn btn-primary" target="seguir" id="regresar">
							<spring:message code="label.btn.ubicar" />
						</button>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
	
	<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/domicilios/domicilios.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/domicilios/autocomplete.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/domicilios/domicilios-map.js" htmlEscape="true" />"></script>

	<div class="pie row">
	<div style="float: left; padding: 15px 0px;"><span class="required" id="labelCamposObligatoriosGeneral">*</span> <spring:message code="label.campos.obli"/>.</div>
		<div class="controles col-sm">     
     		 <div class="btn-group dropup pull-right">
				<a href="#" class="btn btn-primary" onclick="uid_call('imss.gestion.domicilios.general.btn_acciones','clickin')">
				<spring:message code="label.menus.opciones"/></a>
				<a href="#" data-toggle="dropdown" class="btn btn-primary dropdown-toggle">
					<span class="caret"></span>
				</a>

				<ul class="dropdown-menu">
					<li>
						<a id="btnGuardarTramite" onclick="uid_call('imss.gestion.domicilios.general.btn_guardarTramite','clickin')">
							<i class="glyphicon glyphicon-download-alt"></i>
							<spring:message code="label.acciones.guardar"/>
						</a>
					</li>
					<li>
						<a id="btnCancelarTramite" onclick="uid_call('imss.gestion.domicilios.general.btn_cancelarTramite','clickin')">
							<i class="glyphicon glyphicon-trash"></i>
							<spring:message code="label.acciones.cancelar"/>
						</a>
					</li>
				</ul>
			</div>
      		<div class="pull-right" style="margin-right: 5px;" >
				<button class="btn btn-default" id="cerrarWizard" onclick="uid_call('imss.gestion.domicilios.general.btn_cerrar','clickin')"><spring:message code="label.btn.cerrar"/></button>
				<button class="btn btn-primary" id="siguiente" onclick="uid_call('imss.gestion.domicilios.general.btn_siguiente','clickin')">Siguiente</button>
			</div>
		</div>

	</div>
</div>

<%@ include file="wizardPie.jsp"%>
