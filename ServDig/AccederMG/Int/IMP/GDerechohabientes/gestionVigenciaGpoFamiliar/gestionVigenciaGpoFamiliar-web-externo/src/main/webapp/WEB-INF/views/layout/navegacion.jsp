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

<div class="container">
	<nav role="navigation" class="navbar navbar-inverse sub-navbar navbar-fixed-top">
		<div class="container">
			<!-- <div class="navbar-header"> -->
			<div class="navbar-left" style="height: 51px;">
				<span class="helper"></span>
				<a href="${contextpath}/busqueda/home" class="navbar-brand">
					IMSS - Consulta de vigencia para segundo y tercer nivel
				</a>
			</div>
			
			<div id="navBarImssCollapse" class="collapse navbar-collapse">
				<ul class="nav navbar-nav navbar-right">
					<li>
						<p class="navbar-text"><spring:message code="label.version" />: <spring:message code="version" /></p>
					</li>
					<li>
						<a href="${contextpath}/busqueda/home">
							<i class="glyphicon glyphicon-home"></i>
						</a>
					</li>
					<li>
						<a data-toggle="dropdown" class="dropdown-toggle" href="#" id="cerrarSesionLink">
							<i class="glyphicon glyphicon-off"></i>
						</a>
					</li>
					
				</ul>
			</div>
		</div>
	</nav>
</div>

<div class="info-gral" style="margin: 20px">
	<div class="row">
		<div class="col-md-8 col-sm-10 col-xs-12">
			<img alt="" src="${staticResourcesPath}/imagenes/logo_d.png" />
			<span class="cajatitulo">${usuario.usuario}</span>
		</div>
		<div class="col-md-2 hidden-sm hidden-xs">
			 
			<!-- 
			<a class="btn btn-default btn-sm  " href="#" id="ayuda-rapida">
	  			<i class="icon-info-sign "></i> Ayuda r&aacute;pida.</a>
			 -->
		</div>
		<div class="col-md-2 hidden-sm hidden-xs">
			<img alt="" src="${staticResourcesPath}/imagenes/logo_imss_digital.png" style="float: right;" />
		</div>
	</div>
</div>


<div class="menu_holder" align="right" style="display: none;">
	<div id="dgCerrarSesion" title="Cerrar Sesion">
		<p style="margin-bottom: 0px;">
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin-right: 7px; margin-top: 3px;"> </span> ¿Esta Ud.
			seguro de cerrar su sesi&oacute;n?
		</p>
		<span id="errorNegocioLabel" class=" hiddenElement error"></span>
	</div>

	<!-- Div para generar el diálogo de la consulta del detalle de una notificación -->
	<div id="detalleNotificacionComponent"></div>
</div>


		<form id="frmBase" action="">
				<input name="fechaAvisoSession" type="hidden" id="fechaAvisoSession"  value="${infoSession.fechaAvisoSession}"/>
				<input name="fechaFinSession" type="hidden" id="fechaFinSession"  value="${infoSession.fechaFinSession}"/>
				<input name="intervaloValidacionSession"  type="hidden" id="intervaloValidacionSession" value ="10000"/>
				<input name="validaAviso"  type="hidden" id="validaAviso" value ="${infoSession.validaAvisoSession}"/>
			</form>
			
			<div id="dialog-Aviso-Session" title="Cierre de Sesi&oacute;n" style="display: none;">
			<p>
				<span class="ui-icon ui-icon-alert"
					style="float: left; margin: 0 7px 20px 0;"></span>
					<label id="mensajeDialogoSession"></label>
			</p>
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

<script>
var oDialogoCerrarSesion;

$(document).ready(function() {
	/*Inicializacion del dialogo
	 * de confirmacion de cerrar sesion*/
	oDialogoCerrarSesion = $(idDialogoCerrarSesion).dialog({
		autoOpen : false,
		resizable : false,
		height : 180,
		modal : true,
		buttons : {
			"Continuar" : function(data) {
				$.postJSON(context_path + "/busqueda/limpiarSesion",{},function(data) {
					$("#formCerrarSesion").submit();
		    	}).error(
			    		function(data) {
			    			
			    	//		$("#formCerrarSesion").submit();
			    		}
			    	);
				
			},
			'Cancelar' : function() {
				$(this).dialog("close");
			}
		}
	});
	
	$('#cerrarSesionLink').live( 'click' , function(){
		fnAbrirDialogoCerrarSesion();
	});
	
});

var fnAbrirDialogoCerrarSesion = function() {
	oDialogoCerrarSesion.dialog('open');
};
</script>
