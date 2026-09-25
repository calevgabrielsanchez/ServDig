<%@ include file="../general/taglibs.jsp"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<script type="text/javascript">
  history.go(1);
  tipoTramite = '${tramite}';

	$(document).ready(initEvent);
	
	function initEvent() {
		$("#regresarPantallaInicial").click(redireccionPaginaPrincipal);
		$("#continuar").click(continuar);
		$('#submitContinuar').click(function() {					
			$('#registroAseguradoDatosBasicosForm').val('continuar');
			document.getElementById("registroAseguradoDatosBasicosForm").submit();
		});
		$("#submitCancelar").click( function(){								 								 
			 $("#myModalActualizacion").removeClass("modal show").addClass("modal hide");
	 	}
	 );
	}
	
	function continuar() {
			$("#continuar").attr("disabled", true);	
			$('#myModal').modal({backdrop: 'static', keyboard: false})
			$("#myModal").modal('show');
			// document.getElementById("form_inicio").submit();
	}

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

<input type="hidden" id="requiereActualizacion" value="${requiereActualizacion}" />

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
	
	<!-- Muesta mensaje al usuario informando la confirmacion via correo -->
	<c:if test="${exito != null}">
		<div align="center">
			<div>
				<div class="alert alert-success">
					<p>
						<span style="float: left; margin-right: .3em;"></span> ${exito}.
					</p>
				</div>
			</div>
		</div>
	</c:if>

	<div>
		<spring:message code="label.tramite.instrucciones.ingresa" />
	</div>
	
	<div>
		<spring:message code="label.tramite.instrucciones.tenermano" />
		<br/>
		<ul>
			<li><spring:message code="label.curp" /></li>
			<li><spring:message code="label.nss" /></li>
			<li><spring:message code="label.tramite.instrucciones.correo" /></li>
		</ul>
	</div>

	<br />
	<%--incluimos el jsp de login y seteamos los parametros, no se usa jsp:include com params porque pinta el contenido tal cual y no lo interpreta --%>
	<c:set var="mostrarNSS" value="true" />

	<c:if test="${not requiereActualizacion}">
		<c:set var="rutaController" value="${contextpath}/vigencia/consultar" />
	</c:if>

	<c:if test="${requiereActualizacion}">
		<c:set var="rutaController"
			value="${contextpath}/vigencia/actualizarCorreo" />
	</c:if>

	<c:set var="clickContinuar"
		value="onclick=\"uid_call('imss.asegurados.consulta_vigencia.inicio.btn_continuar','clickin')\"" />
	<c:set var="onclick"
		value="onclick=\"uid_call('imss.asegurados.consulta_vigencia.inicio.consulta_curp','clickout')\"" />
	<c:set var="onclickNSS"
		value="onclick=\"uid_call('imss.asegurados.consulta_vigencia.inicio.consulta_nss','clickout')\"" />
	<c:set var="enviarCorreo" value="true" />

	<%@ include file="../common/login.jsp"%>

	<div id="myModalActualizacion" backdrop='static' keyboard=false
		class="modal ${showModal}" style="background: #000000ab;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					&nbsp;
					<p>Actualizaci&oacute;n necesaria</p>
				</div>
				<div class="modal-body">
					<p>
						<spring:message code="mensaje.otro.correo.vinculado" />
					</p>
				</div>
				<div class="modal-footer">
					<button id="submitCancelar" type="button" class="btn btn-secondary"
						data-dismiss="modal">Salir</button>
					<button id="submitContinuar" ${clickContinuar} type="button"
						class="btn btn-primary active ${hideRenovation}">Continuar</button>
				</div>
			</div>
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
		
		<%-- 
		<jsp:include page="../common/pieTramites.jsp">
			<jsp:param name="tipoTramite" value="true" />
		</jsp:include>
		--%>
</div>
