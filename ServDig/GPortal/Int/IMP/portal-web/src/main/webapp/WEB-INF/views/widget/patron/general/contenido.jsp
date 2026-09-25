
<!-- JSP Contenido del Widget de Datos Basicos del Centro de Trabajo. -->
<%@ include file="../../../general/taglibs.jsp"%>

<c:set var="centroTrabajo" value="${sujetoObligado.cntroTrabajo}" />
<c:set var="staticResourcesPath"
	value='<%=request.getSession().getServletContext().getInitParameter("STATIC_RESOURCES_PATH") %>' />
<c:set var="staticLogoutPath" value='<%=request.getSession().getServletContext().getInitParameter("STATIC_LOGOUT_PATH")%>' />
<c:set var="fisica" value="${patron.fisica}" />
<c:set var="moral" value="${patron.moral}" />

<script type="text/javascript">
var infoCentroTrabajo = '<p>En esta secci&oacute;n se muestra la <span style="font-style: italic;">informaci&oacute;n general</span> del Centro de Trabajo de la siguiente forma:</p>' +
	'<address>' +
	'<i class="icon-minus" style="margin-right: 3px; vertical-align: middle; line-height: 11px ! important; font-weight: lighter; font-size: 9px ! important;"></i><strong><span>N&uacute;mero de Registro Patronal</span></strong><br>' +
	'<i class="icon-minus" style="margin-right: 3px; vertical-align: middle; line-height: 11px ! important; font-weight: lighter; font-size: 9px ! important;"></i><span>Nombre completo o Raz&oacute;n Social</span><br>' +
	'<i class="icon-minus" style="margin-right: 3px; vertical-align: middle; line-height: 11px ! important; font-weight: lighter; font-size: 9px ! important;"></i><span>Nombre Comercial</span><br>' +
	'<i class="icon-minus" style="margin-right: 3px; vertical-align: middle; line-height: 11px ! important; font-weight: lighter; font-size: 9px ! important;"></i><span>Tipo de Perona Fiscal</span><br>' +
	'<i class="icon-minus" style="margin-right: 3px; vertical-align: middle; line-height: 11px ! important; font-weight: lighter; font-size: 9px ! important;"></i><span>RFC</span><br>' +
	'<i class="icon-minus" style="margin-right: 3px; vertical-align: middle; line-height: 11px ! important; font-weight: lighter; font-size: 9px ! important;"></i><span>CURP (Solo si es persona f&iacute;sica)</span><br>' +
	'</address>';
$('#idPopoverDatosCentroTrabajo').popover({
	animation : true,
	html: true,
	title : "Datos Generales del Centro de Trabajo",
	content : infoCentroTrabajo,
	trigger: 'hover',
	container : 'body'
});

var infoDomicilioCT =  '<p>En esta secci&oacute;n se muestra el <span style="font-style: italic;">Domicilio</span> del Centro de Trabajo</p>';
$('#idPopoverDomicilioCT').popover({
	animation : true,
	html: true,
	title : "Domicilio del Centro de Trabajo",
	content : infoDomicilioCT,
	trigger: 'hover',
	container : 'body'
});

var infoMediosCT = '<p>En esta secci&oacute;n se muestran los <span style="font-style: italic;">Medios de Contacto</span> del Centro de Trabajo, pueden estar registrado los siguientes: '+
'<p>Correo Electr&oacute;nico<br />Tel&eacute;fono Fijo<br />Tel&eacute;fono M&oacute;vil</p>';
$('#idPopoverMediosContactoCT').popover({
	animation : true,
	html: true,
	title : "Medios de Contacto del Centro de Trabajo",
	content : infoMediosCT,
	trigger: 'hover',
	container : 'body'
});
</script>

<div class="widget-section">
	<div style="float: right;">
		<a class="btn btn-sm icono-help" id="idPopoverDatosCentroTrabajo" data-toggle="popover">
		</a>
	</div>
	<address>
		<strong> <span>${patron.numeroRegistroPatronal}${patron.modalidad.numModalidad}${patron.digVerificador}</span>
		</strong><br>
		<c:if test="${fisica != null}">
			<span>${fisica.nombre}
				${fisica.primerApellido} ${fisica.segundoApellido}</span>
		</c:if>
		<c:if test="${moral != null}">
			<span>${moral.razonSocial}</span>
		</c:if>
		<br> 
		<c:choose>
			<c:when test="${patron.nombreComercial eq null }">
				<span class="no-data"> Sin Nombre Comercial</span>
			</c:when>
			<c:otherwise>
				<span>${patron.nombreComercial}</span>
			</c:otherwise>
		</c:choose>
		<br> <span>${patron.tipoPersonaFiscal}</span><br>
		<c:if test="${fisica != null}">
			
			<c:choose>
				<c:when test="${fisica.rfc eq null }">
					<span class="no-data"> No tiene RFC</span>
				</c:when>
				<c:otherwise>
					<span>${fisica.rfc}</span>
				</c:otherwise>
			</c:choose>
			<br>
			
			<span>${fisica.curp}</span>
			<br>
		</c:if>
		<c:if test="${moral != null}">
			
			<span>${moral.rfc}</span>
			<br>
		</c:if>
	</address>
</div>
<br />
<div class="widget-section">
	<input type="hidden" id="hdnNumeroRegistroPatronal"
		value="${sujetoObligado.numeroRegistroPatronal}${sujetoObligado.modalidad.numModalidad}${sujetoObligado.digVerificador}" />
	<input type="hidden" id="hdnEsRPC"
		value="${sujetoObligado.clasificacion.indRegPatClase}" />
	<c:choose>
		<c:when test="${centroTrabajo != null}">
			<div style="float: right;">
				<a class="btn btn-sm icono-help" id="idPopoverDomicilioCT" data-toggle="popover">
				</a>
			</div>
			<address>
				<c:if test="${centroTrabajo.vialidadPrimaria != null}">
					<spring:message code="label.calle.num" />
					: ${centroTrabajo.vialidadPrimaria.nombre}
					${centroTrabajo.numExterior1} ${centroTrabajo.numExteriorAlf} ,
					${centroTrabajo.numInterior} ${centroTrabajo.numInteriorAlf}<br>
					<spring:message code="label.colonia" />
					: ${centroTrabajo.asentamiento.nombre}<br>
					<spring:message code="label.municipio" />
					: ${centroTrabajo.asentamiento.localidad.municipio.nombre}<br>
					<spring:message code="label.entidad.federativa" />
					:
					${centroTrabajo.asentamiento.localidad.municipio.entidadFederativa.nombre}<br>
					C.P. ${centroTrabajo.codigoPostal.codigoPostal}<br>
				</c:if>
				<c:if test="${centroTrabajo.vialidadPrimaria == null}">
					<!-- <div class="alert alert-warning" style="width: 65%;">
						Se recomienda actualizar su domicilio.
					</div> -->
					${centroTrabajo.descripcion}<br>
				</c:if>
				<spring:message code="label.delegacion" />
				: ${sujetoObligado.subdelegacion.delegacion.descripcion}<br>
				<spring:message code="label.subdelegacion" />
				: ${sujetoObligado.subdelegacion.descripcion}<br>
				<c:if test="${ sujetoObligado.municipioIMSS != null }">
					<spring:message code="label.municipio.imss" />: ${sujetoObligado.municipioIMSS.descMunicipio} (${sujetoObligado.municipioIMSS.cvecMunicipioSINDO})<br>
				</c:if>
			</address>
		</c:when>
		<c:otherwise>
			<p>
				<span class="no-data">No cuenta con domicilio.</span>
			</p>
		</c:otherwise>
	</c:choose>
</div>
<br />
<div class="widget-section">
	<c:choose>
		<c:when test="${centroTrabajo != null and not empty centroTrabajo.mediosContacto}">
			<div style="float: right;">
				<a class="btn btn-sm icono-help" id="idPopoverMediosContactoCT" data-toggle="popover">
				</a>
			</div>
			<div>
				<c:forEach items="${centroTrabajo.mediosContacto}" var="medio"
					varStatus="indice">
					<address>
						<strong>${medio.tipoMedioContacto.descripcion}</strong> <br>
						${medio.desFormaContacto}<br>
					</address>
				</c:forEach>
			</div>
		</c:when>
		<c:otherwise>
			<p>
				<span class="no-data">No cuenta con medios de contacto.</span>
			</p>
		</c:otherwise>
	</c:choose>
</div>
