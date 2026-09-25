<%@ include file="../general/taglibs.jsp"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<script type="text/javascript" src="<spring:url value="${contextpath}/static/resources/js/delta/portal.js" htmlEscape="true" />"> </script>

<script type="text/javascript">
  history.go(1);
  tipoTramite = '${tramite}';
  
  $("#registroCurp").disabled = true;
  $("#correoInput").disabled = true;
  $("#correoConfirmacionInput").disabled = true;
  
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
	
	<jsp:include page="encabezadoActualizacionCorreo.jsp">
		<jsp:param name="paso" value="1" />
	</jsp:include>
	
		<div>

			<ul>
				<li><spring:message code="label.tramite.instrucciones.correo"/></li>
			</ul>
			
		</div>
		<div><spring:message code="label.tramite.instrucciones.ingresa"/></div>
		    <!-- Muesta mensaje al usuario informando la confirmacion via correo -->
		<c:if test="${exito != null }">
			<div  align="center">
				<div >
					<div class="alert alert-success">
						<p>
							<span style="float: left; margin-right: .3em;"></span>
							${exito}.
						</p>
					</div>
				</div>
			</div>
		</c:if>
		<br>
		<br>
		<%--incluimos el jsp de login y seteamos los parametros, no se usa jsp:include com params porque pinta el contenido tal cual y no lo interpreta --%>
		<c:set var="mostrarNSS" value="true" />
		<c:set var="rutaController" value="${contextpath}/vigencia/guardarActualizacion"/>
		<c:set var="clickContinuar" value="onclick=\"uid_call('imss.asegurados.consulta_vigencia.inicio.btn_continuar','clickin')\""/>
		
		<%@ include file="../common/logActualizacion.jsp"%>
		<c:choose>
			<c:when test="${origenExterno == true}">
				<div class="alert alert-info" style="margin-top: 20px">
					<p><strong>Aviso de privacidad simplificado</strong></p>
					El Instituto Mexicano del Seguro Social (IMSS) es responsable del tratamiento de los datos personales que nos proporciones, los cuales ser&aacute;n protegidos conforme a lo dispuesto por la Ley General de
					Protecci&oacute;n de Datos Personales en Posesi&oacute;n de Sujetos Obligados. Los datos personales que se recaben ser&aacute;n utilizados para actualizar tu correo electr&oacute;nico el cual es requisito indispensable para
					obtener en l&iacute;nea tu Constancia de Vigencia de Derechos para recibir servicio m&eacute;dico ante el IMSS, tr&aacute;mite con Homoclave
					IMSS-02-020-B y tu Constancia de semanas cotizadas en el IMSS, tr&aacute;mite con Homoclave IMSS-02-025. Si deseas conocer nuestro aviso de privacidad integral, lo podr&aacute;s consultar
					en la siguiente direcci&oacute;n electr&oacute;nica liga:<a href="https://www.gob.mx/privacidadintegral" target="_blank">https://www.gob.mx/privacidadintegral</a>
				</div>
			</c:when>
			<c:otherwise>
				<div class="alert alert-info" style="margin-top: 20px">
					<p><strong>Aviso de privacidad simplificado</strong></p>
					La recolecci&oacute;n de datos personales se lleva a cabo a trav&eacute;s de la p&aacute;gina
					electr&oacute;nica <a href="${mvn.avisos.contexto}/gestionAsegurados-web-externo/vigencia"
										  target="_blank">${mvn.avisos.contexto}/gestionAsegurados-web-externo/vigencia</a> cuyo administrador y responsable del tratamiento
					es la Coordinaci&oacute;n de Clasificaci&oacute;n de Empresas y Vigencia de Derechos del Instituto Mexicano del Seguro Social.
					Los datos personales que se recaban ser&aacute;n utilizados con la finalidad de generar y obtener la Constancia de
					vigencia de derechos para recibir servicio m&eacute;dico ante el IMSS con Homoclave IMSS-02-020-B.
					Si deseas conocer nuestro aviso de privacidad integral, lo podr&aacute;s consultar en el portal:
					<a href="${mvn.avisos.contexto}/gestionAsegurados-web-externo/avisoVigencia.jsp"
					   target="_blank">${mvn.avisos.contexto}/gestionAsegurados-web-externo/avisoVigencia.jsp</a>
				</div>
			</c:otherwise>
		</c:choose>
		
		<%-- 
		<jsp:include page="../common/pieTramites.jsp">
			<jsp:param name="tipoTramite" value="true" />
		</jsp:include>
		--%>
</div>

<script language="JavaScript1.2" src="${staticResourcesPath}/js/comscore/Form.js"></script>
