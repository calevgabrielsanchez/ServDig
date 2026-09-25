<%@ include file="../../../general/taglibs.jsp"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" scope="request" />

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/domicilios/wizard/general/funcionesComunes.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/domicilios/wizard/registrar/particular/contenido.js" htmlEscape="true" />"></script>

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

			<div id="domParticularDiv">
				<span id="errorNegocioLabel" class="error hiddenElement"></span>
				<div id="admonDomParticularDiv">
					
					
					<jsp:include page="../../common/datosComplementariosCommon.jsp"></jsp:include>
				</div>
			</div>
			<br />
		</c:if>
		</div>
	</div>

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
