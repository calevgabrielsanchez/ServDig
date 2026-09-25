<%@ include file="../general/taglibs.jsp"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="baseHost" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}/" />
<c:set var="semanasCotizadasUrl" value="${baseHost}semanascotizadas-web/" />

<style>

span.error-custom {
	float: none !important;
	vertical-align: super;
}

.required {
	color: red;
}

.filtros-busqueda .row {
	margin-bottom: 12px;
}

.filtros-busqueda .filtros .etiqueta {
	width: 25%;
}

input[type="text"] {
	margin-bottom: 0px;
}

.alert-temp {
	background-color: #f8f8f8;
	border-color: #d9d9d9;
	color: #black;
}

.icono-tramite {
	font-size: 2em;
}

.icono-tramite a {
	color: #545454;
	text-decoration: none;
}

.icono-tramite a:hover {
	color: black;
}

</style>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/tramite/consultaVigencia/solicitudFinalizada.js" htmlEscape="true" />"></script>
<script>

    function redireccionar() {
        var origenExterno = document.getElementById('origenExterno').value;
        console.log('el valor de origenExterno es: ', origenExterno);
        if (origenExterno === 'true') {
            window.location.href = '${semanasCotizadasUrl}';
        } else {
            window.location.href = '${contextpath}/vigencia';
        }
    }
    
</script>

<input type="hidden" id="origenExterno" value="${origenExterno}" />
<input type="hidden" value="${solicitudVigencia.tipoSolicitud.idTipoSolicitud}" id="valortiposolicitud"/>
<input type="hidden" value="${solicitudVigencia.estadoSolicitud.idEstadoSolicitud}" id="valortipoestadosolicitud"/>

<div class="contenedor">

	<input type="hidden" value="${homoclaveTramite}" id="homoclaveTramite"/>

	<c:choose>
	    <c:when test="${solicitudVigencia.tipoSolicitud.idTipoSolicitud == 67}">
	        <jsp:include page="encabezadoActualizacionCorreo.jsp">
	            <jsp:param name="paso" value="2" />
	        </jsp:include>
	    </c:when>
	    <c:otherwise>
	        <jsp:include page="encabezado.jsp">
	            <jsp:param name="paso" value="2" />
	        </jsp:include>
	    </c:otherwise>
	</c:choose>
	
	<h4><spring:message code="label.doctos"/></h4>
	<hr class="red" style="margin-bottom:25px">
	
	<div class="row" style="margin-bottom: 25px;">
	    <div class="col-md-12">
	        <c:choose>
	            <c:when test="${solicitudVigencia.tipoSolicitud.idTipoSolicitud == 67 
	            && solicitudVigencia.estadoSolicitud.idEstadoSolicitud == 5}">
	                <p>Se le informa que se recibi&oacute; la solicitud de actualizaci&oacute;n de correo electr&oacute;nico, 
	                se enviar&aacute; respuesta dentro de un plazo de 5 d&iacute;as h&aacute;biles.</p>
	            </c:when>
	            
	            <c:when test="${solicitudVigencia.tipoSolicitud.idTipoSolicitud == 67 
				    && solicitudVigencia.estadoSolicitud.idEstadoSolicitud != 5}">
				        <p>Tu comprobante de actualizaci&oacute;n de correo electr&oacute;nico ha sido enviado a tu correo electr&oacute;nico.</p>
				 </c:when>
				 
	            <c:otherwise>
	                <p><spring:message code="label.tramite.consultaVigencia.indicaciones.final"/></p>
	            </c:otherwise>
	        </c:choose>
	    </div>
	</div>

	
	<div class="row m-t-md"  style="margin-bottom: 50px;">
		<div class="col-sm-12">
			<div>
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th><spring:message code="label.folio"/></th>
						<th><spring:message code="label.fecha"/></th>
						<th><spring:message code="label.documento"/></th>
						<th></th>
						<th></th>
						<th></th>
					</tr>	
				</thead>
				<tbody>
					<tr>
						<td>${solicitudVigencia.noFolioSolicitud}</td>
						<td>
						    <c:choose>
						        <c:when test="${solicitudVigencia.tipoSolicitud.idTipoSolicitud == 67}">
						            <fmt:formatDate pattern="dd/MM/yyyy" value="${solicitudVigencia.fechaSolicitud}"/>
						        </c:when>
						        <c:otherwise>
						            <fmt:formatDate pattern="dd/MM/yyyy" value="${solicitudVigencia.fechaConclusion}"/>
						        </c:otherwise>
						    </c:choose>
						</td>
						<td>
						    <c:choose>
						        <c:when test="${solicitudVigencia.tipoSolicitud.idTipoSolicitud == 67}">
						            Acuse de actualizaci&oacute;n de correo electr&oacute;nico
						        </c:when>
						        <c:otherwise>
						            <spring:message code="label.tramite.consultaVigencia.docto.comprobante"/>
						        </c:otherwise>
						    </c:choose>
						</td>

						<td>
								<div class="row icono-tramite">
									<div class="col-sm-12  text-center">
										<a class="glyphicon glyphicon-envelope" id="enviarReporte"  onclick="uid_call('imss.asegurados.consulta_vigencia.mail_vigencia','clickin')"></a>
									</div>
								</div>
						</td>
						<td>
								<div class="row icono-tramite">
									<div class="col-sm-12  text-center">
										<a class="icon-printing" id="imprimirReporte" onclick="uid_call('imss.asegurados.consulta_vigencia.imprimir_vigencia','PDF')"></a>
									</div>
								</div>
						</td>
						<td>
								<div class="row icono-tramite">
									<div class="col-sm-12  text-center">
										<a class="glyphicon glyphicon-download-alt" id="descargarReporte" onclick="uid_call('imss.asegurados.consulta_vigencia.descargar_vigencia','download')"></a>
									</div>
								</div>
						</td>
					</tr>
				</tbody>
			</table>
			</div>
		</div>
	</div>
	
	<div class="row" >
		<div class="col-sm-12 text-right">
			<button class="btn btn-primary salir" id="finalizaTramite"
				onclick="redireccionar()">
				<spring:message code="label.tramite.btn.finalizar" />
			</button>
		</div>
	</div>
	
	<%--
	<jsp:include page="../common/pieTramites.jsp">
		<jsp:param name="tipoTramite" value="true" />
	</jsp:include> --%>
	
	<form id="formVerReporte" action="viewReport" method="post" target="_blank" >
		<input type="hidden" value="${tramiteId}" id="tramiteId"/>
	</form>
	
<!-- 	<form id="formSalir" action="salir" method="get">
	</form> -->
	
	<div id="mensajes"></div>
</div>
<script src="${mvn.url.encuesta}"></script>