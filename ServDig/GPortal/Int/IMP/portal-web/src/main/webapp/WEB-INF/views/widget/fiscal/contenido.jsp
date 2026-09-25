<!-- JSP Contenido del Widget de los Medios Fiscales. -->
<%@ include file="../../general/taglibs.jsp"%>

<script type="text/javascript">
var infoDomicilio = '<p>En esta secci&oacute;n se muestra el <span style="font-style: italic;">Domicilio Fiscal</span> de la persona</p>';
$('#idPopoverDomicilioFiscal').popover({
	animation : true,
	html: true,
	title : "Domicilio Fiscal",
	content : infoDomicilio,
	trigger: 'hover',
	container : 'body'
});

var infoMedios = '<p>En esta secci&oacute;n se muestra los <span style="font-style: italic;">Medios de Contacto Fiscales</span> de la persona, pueden estar registrado los siguientes: '+
'<p>Correo Electr&oacute;nico<br />Facebook<br />Tel&eacute;fono Fijo<br />Tel&eacute;fono M&oacute;vil<br />Twitter</p>';
$('#idPopoverMediosContactoFiscales').popover({
	animation : true,
	html: true,
	title : "Medios de Contacto Fiscales",
	content : infoMedios,
	trigger: 'hover',
	container : 'body'
});
</script>

<p>
	<spring:message code="label.widget.descripcion.domicilios.fiscales" />
</p>
<div class="widget-section">
	<c:choose>
		<c:when test="${not empty domFiscal }">
			<input type="hidden" id="idDomiclioFiscalWidget" value="${domFiscal.clave}" />
			<div style="float: right;">
				<a class="btn btn-sm icono-help delta-popover" id=idPopoverDomicilioFiscal data-toggle="popover">
				</a>
			</div>
			<address>
				<c:if test="${not empty domFiscal.calle}">
							${domFiscal.calle}
						</c:if>
				<c:if
					test="${not empty domFiscal.numExteriorAlf || not empty domFiscal.numExterior1}">
					<br> ${domFiscal.numExteriorAlf} ${domFiscal.numExterior1},
						</c:if>
				<c:if
					test="${not empty domFiscal.numInteriorAlf || not empty domFiscal.numInterior}"> 
							${domFiscal.numInteriorAlf} ${domFiscal.numInterior}
						</c:if>
				<c:if test="${not empty domFiscal.colonia}">
					<br>${domFiscal.colonia}
						</c:if>
				<c:if
					test="${not empty domFiscal.asentamiento.localidad.municipio.nombre}">
					<br>${domFiscal.asentamiento.localidad.municipio.nombre}
						</c:if>
				<c:if
					test="${not empty domFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre}">
					<br>${domFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre}<br>
				</c:if>
				<c:if
					test="${not empty domFiscal.asentamiento.codigoPostal.codigoPostal}">
							C.P. ${domFiscal.asentamiento.codigoPostal.codigoPostal}
						</c:if>
			</address>
		</c:when>
		<c:otherwise>
			<p>No cuenta con domicilio fiscal.</p>
		</c:otherwise>
	</c:choose>
</div>
<br />
<div class="widget-section">
	<c:choose>
		<c:when test="${not empty mediosFiscales }">
			<div style="float: right;">
				<a class="btn btn-sm icono-help delta-popover" id="idPopoverMediosContactoFiscales" data-toggle="popover">
				</a>
			</div>
			<div>
				<c:forEach items="${mediosFiscales}" var="medio" varStatus="indice">
					<address>
						<strong>${medio.tipoMedioContacto.descripcion}</strong> <br>
						${medio.desFormaContacto}<br>
					</address>
				</c:forEach>
			</div>
		</c:when>
		<c:otherwise>
			<p>No cuenta con medios de contacto fiscales.</p>
		</c:otherwise>
	</c:choose>
</div>
