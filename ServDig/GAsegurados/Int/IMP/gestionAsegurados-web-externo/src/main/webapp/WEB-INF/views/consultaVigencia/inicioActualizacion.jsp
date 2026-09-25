<%@ include file="../general/taglibs.jsp"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

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
	
	<jsp:include page="encabezado.jsp">
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
		
		<%@ include file="../common/loginActualizacion.jsp"%>
		
		
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
		
		<%-- 
		<jsp:include page="../common/pieTramites.jsp">
			<jsp:param name="tipoTramite" value="true" />
		</jsp:include>
		--%>
</div>

<script language="JavaScript1.2" src="${staticResourcesPath}/js/comscore/Form.js"></script>
