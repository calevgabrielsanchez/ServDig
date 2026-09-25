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
<input type="hidden" id="urlLoginCiudadano" name="urlLoginCiudadano"
						value="<c:out value="${sessionScope.urlLoginCiudadano}"/>" />
						
<div>
	<nav role="navigation" class="navbar navbar-inverse sub-navbar navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button data-target="#navBarImssCollapse" data-toggle="collapse" class="navbar-toggle collapsed" type="button">
					<span class="sr-only">Interruptor de Navegación</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a href="${contextpath}/portal/ingresar" class="navbar-brand"  style="text-transform: none;">
					IMSS - Portal ciudadano
				</a>
			</div>
			<div id="navBarImssCollapse" class="collapse navbar-collapse">
				<ul class="nav navbar-nav navbar-right">
					<li>
						<p class="navbar-text">
							<spring:message code="label.version" />
							:
							<spring:message code="version" />
						</p>
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
							
							<c:out value="${sessionScope.ciudadano.curp}"/>
							<br>
							<c:out value="${sessionScope.ciudadano.nombreCompleto}"/>		
						</span>
	  					<a style="float: right" id="cerrarSesionLink" href="#">Salir</a>
					</div>
				</div>
			</div>
		</div>
		
		
	</div>
</div>


<div class="menu_holder" align="right" style="display: none;">
	<div id="dgCerrarSesion" title="Cerrar Sesi&oacute;n">
		<p style="margin-bottom: 0px;">
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin-right: 7px; margin-top: 3px;"> </span> &iquest;Est&aacute; Ud.
			seguro de cerrar su sesi&oacute;n?
		</p>
		<span id="errorNegocioLabel" class=" hiddenElement error"></span>
	</div>

	<!-- Div para generar el diálogo de la consulta del detalle de una notificación -->
	<div id="detalleNotificacionComponent"></div>
</div>

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
			'Cancelar' : function() {
				$(this).dialog("close");
			},
			"Aceptar" : function(data) {
				$(this).dialog("close");
				$.blockUI();
				$.postJSON("${contextpath}/home/limpiar-session",null,
					function() {
					$.blockUI();
					var url = $('#urlLoginCiudadano').val();
					//console.debug(url);
					location.href = "${contextpath}/home"+url;
				});
				
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