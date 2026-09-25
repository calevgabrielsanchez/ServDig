<%@ include file="../general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.PortalContextEnum"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<c:set var="portalPersona" value="<%=PortalContextEnum.INDIVIDUO.getId()%>" />
<c:set var="portalEmpresa" value="<%=PortalContextEnum.EMPRESA.getId()%>" />
<c:set var="portalPatron" value="<%=PortalContextEnum.PATRONAL.getId()%>" />
<c:set var="portalAsegurado" value="<%=PortalContextEnum.ASEGURADO.getId()%>" />
<c:set var="portalDerechohabiente" value="<%=PortalContextEnum.DERECHOHABIENTE.getId()%>" />

<style>
.info-gral .row {
    display: table;
    width: 100%;
}

.info-gral [class*="col-"] {
    float: none;
    display: table-cell;
    vertical-align: top;
}
</style>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/menu.js" htmlEscape="true" />"></script>

<!-- Hiddens para el control de la navegacion -->
<input type="hidden" id="portalContext" value="${portalContext}" />
<input type="hidden" id="cvePortalPersona" value="<%=PortalContextEnum.INDIVIDUO.getId()%>" />
<input type="hidden" id="cvePortalEmpresa" value="<%=PortalContextEnum.EMPRESA.getId()%>" />
<input type="hidden" id="cvePortalPatron" value="<%=PortalContextEnum.PATRONAL.getId()%>" />
<input type="hidden" id="cvePortalAsegurado" value="<%=PortalContextEnum.ASEGURADO.getId()%>" />
<input type="hidden" id="cvePortalDerechohabiente" value="<%=PortalContextEnum.DERECHOHABIENTE.getId()%>" />

<div>
	<nav role="navigation" class="navbar navbar-inverse sub-navbar navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button data-target="#navBarImssCollapse" data-toggle="collapse" class="navbar-toggle collapsed" type="button">
					<span class="sr-only">Interruptor de Navegaci�n</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a href="${contextpath}/portal/ingresar" class="navbar-brand" style="text-transform: none;">
					IMSS - Escritorio virtual
				</a>
			</div>
			<div id="navBarImssCollapse" class="collapse navbar-collapse">
				<ul class="nav navbar-nav navbar-right">
					<li>
						<p class="navbar-text">
							<spring:message code="label.version" text="Version" />
							:
							<spring:message code="version" text="2.2-SNAPSHOT" />
						</p>
					</li>
					<li>
						<a href="${contextpath}/portal/ingresar">
							<span class="sr-only">Inicio</span>
							<i class="icon-home"></i>
						</a>
					</li>
					<li class="dropdown">
						<a class="dropdown-toggle" style="text-shadow: none;"
							data-toggle="dropdown" href="#">
							<span class="sr-only">Nuevo Tr&aacute;mite</span>
							<i class="icon-tramite"></i>
						</a> 
						<ul class="dropdown-menu" id="opcionesNavegacion">
						</ul>
					</li>
					<li class="dropdown" id="nav-menu">
						<a class="dropdown-toggle" style="text-shadow: none;"
							data-toggle="dropdown" href="#">
							<c:choose>
								<c:when test="${portalContext eq portalPersona}">
									Zona personal
								</c:when>
								<c:when test="${portalContext eq portalEmpresa}">
									Zona empresarial <span class="caret"></span>
								</c:when>
								<c:when test="${portalContext eq portalPatron}">
									Zona patronal <span class="caret"></span>
								</c:when>
								<c:when test="${portalContext eq portalAsegurado}">
									Zona asegurado <span class="caret"></span>
								</c:when>
								<c:when test="${portalContext eq portalDerechohabiente}">
									Zona derechohabiente <span class="caret"></span>
								</c:when>
							</c:choose>
						</a> 
						<ul class="dropdown-menu">
							<c:if test="${portalContext ne portalPersona }">
								<li id="portalPersonaLink"><a
									href="${contextpath}/portal/ingresar">Zona personal</a></li>
							</c:if>
							
							<c:if test="${not empty nss && portalContext ne portalAsegurado }">
								<li id="portalAseguradoLink"><a
									href="#">Zona asegurado</a></li>
							</c:if>
							
							<c:if test="${not empty rfc && portalContext ne portalEmpresa }">
								<li id="portalEmpresaLink"><a
									href="#">Zona empresarial</a></li>
							</c:if>
			
							<c:if test="${not empty numeroRegistroPatronal && portalContext ne portalPatron }">
								<li id="portalPatronLink"><a
									href="#">Zona patronal</a></li>
							</c:if>
						</ul>
					</li>
				</ul>
			</div>
		</div>
	</nav>
</div>
<div style="margin-bottom: 30px;">
	<div class="row">
	<div class="col-md-2 col-sm-2 hidden-xs">
			<img alt="" src="${staticResourcesPath}/imagenes/logo_imss_digital.png" style="float: left;"  width="120px"/>
		</div>
		<div class="col-md-2 hidden-sm hidden-xs">
			<!-- 
				<button type="button" class="btn btn-default btn-sm" id="ayuda-rapida">
		  			<i class="glyphicon glyphicon-info-sign"></i> Ayuda r&aacute;pida
	  			</button>
  			 -->
		</div>
		<div class="col-md-8 col-sm-10 col-xs-12">
			<div class="row">
				<div class="col-md-12">
					<div class="pull-right" style="border: 1px solid #ccc; padding: 10px">
						<span style="margin-right: 40px; float: left">
							<img alt="" src="${staticResourcesPath}/imagenes/logo_d.png"/> 
								<c:if test="${portalContext eq 1 }">
									${objFisica.nombre}&nbsp;${objFisica.primerApellido}&nbsp;${objFisica.segundoApellido}
								</c:if>
						
								<c:if test="${portalContext eq 2 }">
									${rfc}
								</c:if>
						
								<c:if test="${portalContext eq 3 }">
									${numeroRegistroPatronal}
								</c:if>
								
								<c:if test="${portalContext eq portalAsegurado }">
									${nss}
								</c:if>
								
								<c:if test="${portalContext eq portalDerechohabiente }">
									${parentesco.descripcion}&nbsp;-&nbsp;${nombre}
								</c:if>
							</span>
	  					<a style="float: right" id="cerrarSesionLink" href="#">Salir</a>
					</div>
				</div>
			</div>
		</div>
		
		
	</div>
</div>

<!-- 
<div id="espacio"></div>

<div class="row">
	<div class="col-xs-4">
		<img alt="" src="${staticResourcesPath}/imagenes/logoescri.png" />
	</div>
	<div id="" class="col-xs-8">
		<ul class="navegacion">
			<li id="portalPersonaLink"><a
				href="${contextpath}/portal/ingresar"> Persona: ${usuario.usuario} </a></li>
			<c:if test="${not empty rfc }">
				<li id="portalEmpresaLink"><a href="#">Empresa: ${rfc }</a></li>
			</c:if>
			<c:if test="${not empty numeroRegistroPatronal }">
				<li id="portalPatronLink"><a href="#">Patr&oacute;n: ${numeroRegistroPatronal}</a>
				</li>
			</c:if>
		</ul>
		<button type="button" class="btn btn-inverse btn-sm" style="float: right;" id="cerrarSesionLink">Cerrar Sesi&oacute;n</button>
	</div>
</div>
 -->

<div class="menu_holder" align="right" style="display: none;">
	<div id="dgCerrarSesion" title="Cerrar Sesi&oacute;n">
		<p style="margin-bottom: 0px;">
			&iquest;Est&aacute;s seguro de cerrar tu sesi&oacute;n?
		</p>
		<span id="errorNegocioLabel" class=" hiddenElement error"></span>
	</div>

	<div id="dgBuzonTributario" title="Buz&oacute;n Tributario">
		<p style="margin-bottom: 0px;">
		<div id="msjBuzon"> </div>
		<input id="razonSocial" type="hidden">
		</p>
		<span id="errorNegocioLabel2" class=" hiddenElement error"></span>
	</div>
	
	<div id="dgBuzonTributarioHtml" title="Importante">
		<p style="margin-bottom: 0px;">
			<iframe src="${staticResourcesPath}/html/dialog/dialogMsgBuzonTributario.html"  width="280px" height="440px" ></iframe>
  		</p>
	</div>
	
	
	
	

	<!-- Div para generar el di�logo de la consulta del detalle de una notificaci�n -->
	<div id="detalleNotificacionComponent"></div>
</div>


<c:if test="${not empty rfc }">
	<form id="formPortalEmpresaNavAux" action="" method="post">
		<input type="hidden" value="${idPersonaEmpresa}" name="idPersona">
		<input type="hidden" value="${rfc}" name="rfc">
		<c:choose>
			<c:when test="${empty idFiscalEmpresaFisica}">
				<input id="idFiscalEmpresaMoral" type="hidden"
					value="${idFiscalEmpresaMoral }" name="cveMoral">
			</c:when>
			<c:otherwise>
				<input id="idFiscalEmpresaFisica" type="hidden"
					value="${idFiscalEmpresaFisica }" name="cveFisica">
			</c:otherwise>
		</c:choose>
	</form>
</c:if>

<c:if test="${not empty patronPlataforma }">
		<input type="hidden" id="patronPlataforma" name="patronPlataforma" value = "${patronPlataforma}">
</c:if>
<c:if test="${not empty patronListaBlanca }">
		<input type="hidden" id="patronListaBlanca" name="patronListaBlanca" value = "${patronListaBlanca}">
</c:if>

<c:if test="${not empty numeroRegistroPatronal }">
	<form id="formPortalPatronalNavAux"
		action="${contextpath}/portal/patron/ingresar/" method="post">
		<input id="hdnRegistroPatronal" type="hidden" value=""
			name="numeroRegistroPatronal"> <input id="hdnModalidadPatron"
			type="hidden" value="" name="modalidad.numModalidad">
	</form>
</c:if>

<c:if test="${not empty asignacion }">
	<form id="formPortalAseguradoAux" name="formPortalAseguradoAux" method="post" action="${contextpath}/portal/asegurado/ingresar">
		<input type="hidden" id="asignacionNSS.idPersona" name="asignacionNSS.idPersona" value="${asignacion.idPersona}">
		<input type="hidden" id="asignacionNSS.idAsignacionNSS" name="asignacionNSS.idAsignacionNSS" value ="${asignacion.idAsignacionNSS}">
		<input type="hidden" id="asignacionNSS.nss" name="asignacionNSS.nss" value = "${asignacion.nss}">
		<input type="hidden" id="asignacionNSS.nombre" name="asignacionNSS.nombre" value = "${asignacion.nombre}">
		<input type="hidden" id="asignacionNSS.primerApellido" name="asignacionNSS.primerApellido" value = "${asignacion.primerApellido}">
		<input type="hidden" id="asignacionNSS.segundoApellido" name="asignacionNSS.segundoApellido" value = "${asignacion.segundoApellido}">
		<input type="hidden" id="asignacionNSS.curp" name="asignacionNSS.curp" value = "${asignacion.curp}">
		
		<input type="hidden" id="fechaInicioVigencia" name="fechaInicioVigencia" value = "<fmt:formatDate  pattern="dd/MM/yyyy"  value="${asignacion.fechaRegistro}"/>">
		<input type="hidden" id="fechaFinVigencia" name="fechaFinVigencia" value = "<fmt:formatDate  pattern="dd/MM/yyyy"  value="${asignacion.fechaBaja}"/>">
			<input type="hidden" id="parentesco.idParentesco" name="parentesco.idParentesco" value = "${asignacion.sexo.idSexo}"/>
			<input type="hidden" id="estadoDerechohabiente.idEstadoDerechohabiente" name="estadoDerechohabiente.idEstadoDerechohabiente" value = "${asignacion.estadoCivil.idEstadoCivil}"/>
			
	</form>
</c:if>

<script type="text/javascript">
	$(document).ready(function() {
		//parametros que sirven para mostrar los tramites de derechohabientes de acuerdo al parentesco y estado
		var idParentesco = 0;
		var idEstado = 0;
		var patronPlataforma = false;
		var patronListaBlanca = false;

		//verificamos si existen los campos de parentesco y estado de ser asi los seteamos
		if($("#hdnIdParentesco").length) {
			idParentesco = $("#hdnIdParentesco").val();
		}
		if($("#hdnIdEstadoDerechohabiente").length) {
			idEstado = $("#hdnIdEstadoDerechohabiente").val();
		}
		
		if($("#patronPlataforma").length) {
			patronPlataforma = $("#patronPlataforma").val();
		}
		
		if($("#patronListaBlanca").length) {
			patronListaBlanca = $("#patronListaBlanca").val();
		}
		
		$.post("/portal-web/utility/menu/opciones/widgetNavegacion/tramite/6/${portalContext}/"+idParentesco+"/"+idEstado+"/"+patronPlataforma+"/"+patronListaBlanca,null,function(data) {
			$("#opcionesNavegacion").html(data);
		});
	});
</script>
