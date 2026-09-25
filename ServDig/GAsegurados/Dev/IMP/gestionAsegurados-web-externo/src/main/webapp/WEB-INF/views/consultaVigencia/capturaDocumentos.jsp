<%@ include file="../general/taglibs.jsp"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/tramite/consultaVigencia/capturaDocumentos.js" htmlEscape="true" />"></script>


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
			<spring:message code="label.tramite.instrucciones.documentacion"/>:<br>
			<ul>
				<li><spring:message code="label.tramite.instrucciones.INE"/></li>
				<li><spring:message code="label.tramite.instrucciones.tiposFormatos"/></li>
				<li><spring:message code="label.tramite.instrucciones.tamañoArchivo"/></li>
			</ul>
			
		</div>
		<div><spring:message code="label.tramite.instrucciones.ingresa"/></div>
		
  		<form:form modelAttribute="opciones" action="${contextpath}/homeNormativo/asignaPerfil" method="post" id="capturaDocumentosForm">
 		<br>			
		<center>		
		<table id="capturaDocs">	
	
			<tr>
				<td>&nbsp;</td>
				<td>
					<input type="radio" name="opcion" id="curpBeneficiario" value="1" checked="checked" class="radioVentanilla"/>
					<spring:message code="mensaje.radio.curp.beneficiario.legal"/>
				</td>
				<td>&nbsp;</td>
				<td>
					<input id="curpBeneficiario" name="curpBen" class="alfanumerico_espacios" type="text" value=""> 
				</td>
			</tr>
			

			<tr>
				<td>&nbsp;</td>
				<td>
					<input type="radio" name="opcion" id="umfAdscripcion" value="2" class="radioUMF"/>
					<spring:message code="mensaje.radio.seleccione.unidad.adscripcion"/>
				</td>
				<td>&nbsp;</td>
				<td>
					<input id="umfAdscripcion" name="umfAds" class="alfanumerico_espacios" type="text" value=""> 
				</td>
			</tr>
			
	
			<tr>
				<td>&nbsp;</td>
				<td>
					<input type="radio" name="opcion" id="rfcPatronal" value="3" class="radioRFC"/>
					<spring:message code="mensaje.radio.registro_patronal.rfc.empresa"></spring:message>
				</td>
				<td>&nbsp;</td>
				<td>
					<input id="rfcPatronal" name="rfcPat" class="alfanumerico_espacios" type="text" value=""> 
				</td>
			</tr>
			
			
			<tr>
				<td>&nbsp;</td>
				<td>
					<input type="radio" name="opcion" id="infDesconocida" value="4" class="radioInfoDesconocida"/>
					<spring:message code="mensaje.radio.desconozco.opcion"></spring:message>
				</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>			

		</table>
		</center>
 		</form:form>
		
		<div class="row">
			
			<div class="col-sm-5 text-right">
				<button type="button" id="validarDatos" class="btn btn-primary">
					<spring:message code="label.tramite.finalizar"></spring:message>
				</button>
			</div>
		</div>
		
		<br><br>
		<br><br>
		
		<br>
		<br>
		
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
