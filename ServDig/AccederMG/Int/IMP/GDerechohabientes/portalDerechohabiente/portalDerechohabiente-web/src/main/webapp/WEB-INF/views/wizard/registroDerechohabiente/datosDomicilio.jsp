<%@ include file="../../general/taglibs.jsp" %>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/wizard/registroDerechohabiente/funcionesComunes.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/wizard/registroDerechohabiente/datosDomicilio.js" htmlEscape="true" />"></script>

<!-- dependencias para componente de domicilio -->
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/common/domicilio/domicilioRegistroDerechohabienteInit.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/common/domicilio/setDomicilioCommon.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="/gestionDomicilios-web/static/resources/js/delta/domicilios/recortado/DomicilioRecortadoCtrl.js"></script>




<div class="contenedor col-sm-12">
	<input type="hidden" value="${solicitudRegistro.solicitudId}" id="idSolicitud"/>
	<div class="contenido row" >
	<div class="col-sm-12">

		<jsp:include page="encabezadoRegistro.jsp">
			<jsp:param name="paso" value="2" />
		</jsp:include>
		<input type="hidden" id="permiteCaputuraDom"  value="${validacionesDom.permiteUbicarDomcilio?1:0}"/>
		
		<c:if test="${empty error}">
		<form:form id="formRegistro" method="post" modelAttribute="registro" role="form">
			
			<div id="seccionDomicilioRegistro" class="table_form">
					
				<c:if test="${validacionesDom.error && validacionesDom.mensajeError ne '0'}">
					<div class="alert alert-danger">
						${validacionesDom.mensajeError}
					</div>
				</c:if>
				<c:if test="${!validacionesDom.error && validacionesDom.mensajeError ne '0'}">
					<div class="alert alert-info">
						<p>
							<c:if test="${!validacionesDom.permiteUbicarDomcilio}">
								<i class="glyphicon glyphicon-info-sign"></i>
								Para el parentesco a registrar no es posible tener un domicilio distinto al del asegurado / pensionado. Da clic en <strong>&quot;Siguiente&quot;</strong> para continuar.
							</c:if>
						</p>
					</div>
				</c:if>
				
				
				
							
							<form:hidden path="cvePersonaDomicilio"/>
							<form:hidden path="parentesco.idParentesco"/>
							<form:hidden path="paso"/>
						
		<!-- datos de la calle -->
		<form:hidden path="domicilio.calle"/>
		<form:hidden path="domicilio.vialidadPrimaria.nombre"/>
		<form:hidden path="domicilio.vialidadPrimaria.clave" />
		<form:hidden path="domicilio.vialidadPrimaria.tipoVialidad.clave" />
		<form:hidden path="domicilio.vialidadPrimaria.tipoVialidad.descripcion" />
		<!-- tipo de busqueda realizada -->
		<form:hidden path="domicilio.tipoBusquedaVialidad"/>
		
		<form:hidden path="domicilio.codigoPostal.codigoPostal"/>
		<!-- Datos del asentamiento -->
		<form:hidden path="domicilio.asentamiento.nombre"/>
		<form:hidden path="domicilio.asentamiento.clave" />
		<!-- Datos de la localidad -->
		<form:hidden path="domicilio.asentamiento.localidad.nombre"/>
		<form:hidden path="domicilio.asentamiento.localidad.clave" />
		<!-- Datos del municipio -->
		<form:hidden path="domicilio.asentamiento.localidad.municipio.nombre"/>
		<form:hidden path="domicilio.asentamiento.localidad.municipio.clave" />
		<!-- Datos de la entidad federativa -->
		<form:hidden path="domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre"/>
		<form:hidden path="domicilio.asentamiento.localidad.municipio.entidadFederativa.clave" />
		
		<form:hidden path="domicilio.numExterior1"/>
		<form:hidden path="domicilio.numExteriorAlf" />
		<form:hidden path="domicilio.numInterior"/>
		<form:hidden path="domicilio.numInteriorAlf"/>
							
							
			</div>
		</form:form>
			<div class="separadorseccion">
					<span>
						<spring:message code="label.wizard.registro.titulo.dom"/>
					</span>
				</div>
				
				
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

	<div class="pie row">
		<div class="col-sm-4">
			<div style="float: left; padding: 11px 0px;"><span class="required" id="labelCamposObligatoriosGeneral">*</span><spring:message code="label.camposRequeridos" /></div>
		</div>
		<div class="col-sm-8">
			<div class=" pull-right">
				<button id="regresar" class="btn btn-default" type="button" onclick="uid_call('imss.derechohabientes.registro.datosDomicilio.btn_anterior','clickin')">
				  <span class="glyphicon glyphicon-step-backward"></span>
				  <spring:message code="wizard.button.anterior" />
				</button>
				<c:if test="${!validacionesDom.error && validacionesDom.mensajeError ne '0'}">
					<button id="continuarAUmf" class="btn btn-primary" type="button" onclick="uid_call('imss.derechohabientes.registro.datosDomicilio.btn_siguiente','clickin')">
					  <spring:message code="wizard.button.siguiente" />
					  <span class="glyphicon glyphicon-step-forward"></span>
					</button>
				</c:if>
					
				<div class="btn-group dropup">
					<a href="#" class="btn btn-primary"><spring:message code="label.menus.opciones" /></a> <a href="#"
						data-toggle="dropdown" class="btn btn-primary dropdown-toggle"><span
						class="caret"></span></a>
					<ul class="dropdown-menu pull-right">
						<li><a id="guardarTramiteDom" onclick="uid_call('imss.derechohabientes.registro.datosDomicilio.link_guardar','clickin')"><i class="glyphicon glyphicon-download-alt"></i><spring:message code="wizard.button.guardarTramite"/></a></li>
						<li><a id="guardarCerrarTramiteDom" onclick="uid_call('imss.derechohabientes.registro.datosDomicilio.link_guardarCerrar','clickin')"><i class="glyphicon glyphicon-remove"></i><spring:message code="wizard.button.guardarCerrar"/></a></li>
						<li><a id="cancelarTramite" onclick="uid_call('imss.derechohabientes.registro.datosDomicilio.link_cancelar','clickin')"><i class="glyphicon glyphicon-trash"></i><spring:message code="wizard.button.cancelarTramite"/></a></li>
					</ul>
				</div>
			</div>
		</div>


	</div>
</div>

<jsp:include page="pieRegistro.jsp"/>