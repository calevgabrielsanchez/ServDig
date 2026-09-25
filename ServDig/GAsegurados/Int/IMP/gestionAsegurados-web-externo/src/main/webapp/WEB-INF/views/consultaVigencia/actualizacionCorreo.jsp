<%@ include file="../general/taglibs.jsp"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<script type="text/javascript" src="/gestionSolicitud-web/static/resources/js/delta/firma-digital/FirmaDigital.js"></script>

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
	
	<jsp:include page="encabezado.jsp">
		<jsp:param name="paso" value="1" />
	</jsp:include>
 	
		<div>
			<spring:message code="label.tramite.instrucciones.tenermano"/>:<br>
			<ul>
				<li><spring:message code="label.curp"/></li>
				<li><spring:message code="label.nss"/></li>
				<li><spring:message code="label.tramite.instrucciones.correo"/></li>
			</ul>
			
		</div>
		<div><spring:message code="label.tramite.instrucciones.ingresa"/></div>
		  
		<div class="form-group">
			<label for="curp" class="col-sm-6 control-label">
			CURP<span class="required">*</span>:</label>
			
			<div class="col-md-4 col-sm-7 col-xs-12">
				<input id="curp" name="curp" class="form-control ns_" 
				placeholder="CURP" type="text" value="" maxlength="18">
			</div>
		</div>  
		
		<div class="form-group">
			<label for="correoElectronico.correo" class="col-sm-6 control-label">
			Correo electr&oacute;nico<span class="required">*</span>: 
			<!-- AJUSTE_REGLA: aqui debe haber una función para validar el correo  -->
			</label>
			
			<div class="col-md-4 col-sm-7 col-xs-12">
				<input id="correo" name="correo" class="form-control ns_" 
				placeholder="Correo electronico" type="text" value="" maxlength="18">
			</div>
			
		</div>
		
		<div class="form-group">
			<label for="correoElectronicoFiscal.correo" class="col-sm-6 control-label">
			Confirme su correo electr&oacute;nico<span class="required">*</span>: 
			</label>
			
			<div class="col-md-4 col-sm-7 col-xs-12">
				<input id="registroCorreoConfirmacion" name="correoConfirmacion" class="form-control ns_" 
				placeholder="Confirmacion de correo electronico" type="text" value="" maxlength="18">
			</div>
			
		</div>
		
		<br>
		<br>
		
		<div class="row">
			<div class="col-sm-5 text-left" >
				<button type="button" id="enviarForm" class="btn btn-primary" style="width:450px">
					<spring:message code="label.tramite.con.efirma"/>
				</button>
			</div>
			
			<div class="col-sm-5 text-right"> 
				<button type="button" id="FinalizarSineFirma" ${clickFinalizarSineFirma} class="btn btn-primary"><spring:message code="label.tramite.sin.efirma"/>
			</div> 
		</div>
		
		

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

<!-- div para la forma de firma -->
<div id="firmaDigitalComponent"></div>
<div id="doctosRequeridosTramite"></div>		
		
		<%-- 
		<jsp:include page="../common/pieTramites.jsp">
			<jsp:param name="tipoTramite" value="true" />
		</jsp:include>
		--%>
</div>

<script language="JavaScript1.2" src="${staticResourcesPath}/js/comscore/Form.js"></script>
