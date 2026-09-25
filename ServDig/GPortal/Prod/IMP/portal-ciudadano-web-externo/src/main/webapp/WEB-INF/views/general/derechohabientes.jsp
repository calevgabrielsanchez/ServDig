<%@ include file="../general/taglibs.jsp"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<script type="text/javascript">
  history.go(1);
  tipoTramite = '${tramite}';

	$(document).ready(function() {
		$("#regresarPantallaInicial").click(redireccionPaginaPrincipal);
	});

	function redireccionPaginaPrincipal() {
		if (tipoTramite == 'registro' || tipoTramite == 'registroD') {
			location.href = "/portal-ciudadano-web-externo/derechohabientes/tramite/registro";
		} else if (tipoTramite == 'cambioClinica') {
			location.href = "/portal-ciudadano-web-externo/derechohabientes/tramite/cambioClinica";
		} else {
			location.href = "/portal-ciudadano-web-externo/home/testTramites";
		}
	}
</script>

<div class="contenedor">
			<!-- Muesta los mensajes de error, este error es cuando encontro mas de un error -->
			<c:if test="${errorDatosExistentes != null }">
				<div style="width: 500px;" align="center">
					<div class="ui-widget">
						<div style="margin-top: 20px; padding: 0 .7em;" class="ui-state-highlight ui-corner-all">
							<p>
								<span style="float: left; margin-right: .3em;" class="ui-icon ui-icon-info"></span>
								${mensaje}
							</p>
						</div>
					</div>
				</div>
			</c:if>
			<ol class="breadcrumb">
			  <li><a href="#" class="salir"><i class="icon icon-home"></i></a></li>
			  <li><a href="#" class="salir">Tr&aacute;mites</a></li>
			  <li class="active"><spring:message code="derechohabientes.tramite.${tramite}.title" /></li>
			</ol>
			<!-- Forma de la consulta de personas por datos basicos. -->
			<div class="row">
				<div class="col-md-12">
				<h3>
					<spring:message code="derechohabientes.tramite.${tramite}.title" />
				</h3>
				</div>
			</div>
			<jsp:include page="../common/paginadorClinica.jsp">
				<jsp:param name="paso" value="1" />
				<jsp:param name="tipoTramite" value="${keyCveTipoTramite}"/>
			</jsp:include>
			<div>
				Tener a la mano:<br>
				<ul>
					<li>CURP</li>
					<li>C&oacute;digo postal</li>
					<li>Correo electr&oacute;nico v&aacute;lido, el cual ser&aacute; asociado a tu CURP</li>
				</ul>
				
			</div>
			<div>Favor de ingresar los siguientes datos:</div>

			<br>
			<%--incluimos el jsp de login y seteamos los parametros, no se usa jsp:include com params porque pinta el contenido tal cual y no lo 
			interpreta --%>
			<c:if test="${not empty fisica.errorFormGeneral}">
				<div class="alert alert-danger">
					${fisica.errorFormGeneral}
				</div>
			</c:if>
			<c:set var="mostrarNSS" value="false" />
			<c:set var="rutaController" value="${contextpath}/derechohabientes/tramite/validar"/>
			<%@ include file="../common/login.jsp"%>
			
			<c:if test="${keyCveTipoTramite == 48 || keyCveTipoTramite ==44 || keyCveTipoTramite == 36}">
			<c:set var="urlHijos" value="${keyCveTipoTramite == 48?'Hijos':''}"></c:set>
			<div class="alert alert-info" style="margin-top: 20px">
					<p><strong>Aviso de privacidad simplificado</strong></p>
					La recolecci&oacute;n de datos personales se lleva a cabo a trav&eacute;s de la p&aacute;gina 
					electr&oacute;nica 
					
					<c:if test="${keyCveTipoTramite == 36}">
						<a href="${mvn.avisos.contexto}/portal-ciudadano-web-externo/derechohabientes/tramite/cambioClinica"
						target="_blank">${mvn.avisos.contexto}/portal-ciudadano-web-externo/derechohabientes/tramite/cambioClinica</a> 
					</c:if>
					
					<c:if test="${keyCveTipoTramite == 48 || keyCveTipoTramite ==44}">
						<a href="${mvn.avisos.contexto}/portal-ciudadano-web-externo/derechohabientes/tramite/registro${urlHijos}"
						target="_blank">${mvn.avisos.contexto}/portal-ciudadano-web-externo/derechohabientes/tramite/registro${urlHijos}</a> 
					</c:if>
					
					cuyo administrador y responsable del tratamiento 
					es la Coordinaci&oacute;n de Clasificaci&oacute;n de Empresas y Vigencia de Derechos del Instituto Mexicano del Seguro Social. 
					Los datos personales que se recaban ser&aacute;n utilizados con la finalidad de llevar a cabo el
					<c:if test="${keyCveTipoTramite == 44 || keyCveTipoTramite ==45}">
					 Registro del asegurado (a) o pensionado(a) como  derechohabiente en el IMSS con Homoclave IMSS-02-066-M.
					</c:if>
					<c:if test="${keyCveTipoTramite == 48}">
						Registro de hijo (a) como derechohabiente en el IMSS con Homoclave IMSS-02-066-J.
					</c:if>
					<c:if test="${keyCveTipoTramite == 36}">
						tr&aacute;mite de Actualizaci&oacute;n de datos del asegurado (a) o pensionado (a) como derechohabiente en el IMSS con Homoclave IMSS-02-066-N.
					</c:if>
					
	 				Si deseas conocer nuestro aviso de privacidad integral, lo podr&aacute;s consultar en el portal: 
	 				<c:if test="${keyCveTipoTramite == 48 || keyCveTipoTramite ==44}">
					<a href="${mvn.avisos.contexto}/gestionAsegurados-web-externo/avisoRegistro.jsp"
					target="_blank">${mvn.avisos.contexto}/gestionAsegurados-web-externo/avisoRegistro.jsp</a>
					</c:if>
					
					<c:if test="${keyCveTipoTramite == 36}">
					<a href="${mvn.avisos.contexto}/gestionAsegurados-web-externo/avisoCorreccion.jsp"
					target="_blank">${mvn.avisos.contexto}/gestionAsegurados-web-externo/avisoCorreccion.jsp</a>
					</c:if>
			</div>
			</c:if>
			<jsp:include page="../common/pieUmf.jsp">
				<jsp:param name="tipoTramite" value="true" />
			</jsp:include>
</div>

<script language="JavaScript1.2" src="${staticResourcesPath}/js/comscore/Form.js"></script>