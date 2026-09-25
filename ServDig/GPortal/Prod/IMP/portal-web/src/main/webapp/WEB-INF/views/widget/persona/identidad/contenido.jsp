<!-- JSP Contenido del Widget de Persona Fisica. -->
<%@ include file="../../../general/taglibs.jsp"%>
<c:set var="staticResourcesPath"
	value='<%=request.getSession().getServletContext()
					.getInitParameter("STATIC_RESOURCES_PATH")%>' />
<c:set var="staticLogoutPath" value='<%=request.getSession().getServletContext().getInitParameter("STATIC_LOGOUT_PATH")%>' />
<script type="text/javascript">
var infoDatosGeneralesFisicaFiscal = '<p>En esta secci&oacute;n se muestra la <span style="font-style: italic;">informaci&oacute;n general</span> de la persona de la siguiente forma:</p>' +
	'<address>' +
	'<i class="icon-minus" style="margin-right: 3px; vertical-align: middle; line-height: 11px ! important; font-weight: lighter; font-size: 9px ! important;"></i><span><strong>Nombre completo de la persona </strong></span><br>' +
	'<i class="icon-minus" style="margin-right: 3px; vertical-align: middle; line-height: 11px ! important; font-weight: lighter; font-size: 9px ! important;"></i><span>CURP</span><br>' +
	'<i class="icon-minus" style="margin-right: 3px; vertical-align: middle; line-height: 11px ! important; font-weight: lighter; font-size: 9px ! important;"></i><span>RFC</span><br>' +
	'<i class="icon-minus" style="margin-right: 3px; vertical-align: middle; line-height: 11px ! important; font-weight: lighter; font-size: 9px ! important;"></i><span>Fecha de nacimiento</span><br>' +
	'<i class="icon-minus" style="margin-right: 3px; vertical-align: middle; line-height: 11px ! important; font-weight: lighter; font-size: 9px ! important;"></i><span>Lugar de nacimiento</span><br>' +
	'<i class="icon-minus" style="margin-right: 3px; vertical-align: middle; line-height: 11px ! important; font-weight: lighter; font-size: 9px ! important;"></i><span>N&uacute;mero de Seguro Social</span>' +
	'</address>';
$('#idPopoverDatosGeneralesFisica').popover({
	animation : true,
	html: true,
	content : infoDatosGeneralesFisicaFiscal,
	trigger: 'hover',
	container : 'body'
});

var infoDatosGeneralesMoralFiscal = '<p>En esta secci&oacute;n se muestran los datos principales de la persona moral de la siguiente forma:</p>' +
	'<address>' +
	'<i class="icon-minus" style="margin-right: 3px; vertical-align: middle; line-height: 11px ! important; font-weight: lighter; font-size: 9px ! important;"></i><strong><span>Nombre o raz&oacute;n social</span></strong><br>' +
	'<i class="icon-minus" style="margin-right: 3px; vertical-align: middle; line-height: 11px ! important; font-weight: lighter; font-size: 9px ! important;"></i><span>RFC</span><br>' +
	'<i class="icon-minus" style="margin-right: 3px; vertical-align: middle; line-height: 11px ! important; font-weight: lighter; font-size: 9px ! important;"></i><span>Tipo de sociedad</span><br>' +
	'<i class="icon-minus" style="margin-right: 3px; vertical-align: middle; line-height: 11px ! important; font-weight: lighter; font-size: 9px ! important;"></i><span>Fecha de creaci&oacute;n</span><br>' +
	'</address>';

$('#idPopoverDatosGeneralesMoral').popover({
	animation : true,
	html: true,
	content : infoDatosGeneralesMoralFiscal,
	trigger: 'hover',
	container : 'body'
});

var infoDomicilioFiscal = '<p>En esta secci&oacute;n se muestra el <span style="font-style: italic;">domicilio particular</span> de la persona</p>';
$('#idPopoverDomicilio').popover({
	animation : true,
	html: true,
	title : "Domicilio particular",
	content : infoDomicilioFiscal,
	trigger: 'hover',
	container : 'body'
});

var infoMediosFiscal = '<p>En esta secci&oacute;n se muestran los <span style="font-style: italic;">medios de contacto particulares</span> de la persona, pueden estar registrados los siguientes: '+
'<p>Correo electr&oacute;nico<br />Facebook<br />Tel&eacute;fono fijo<br />Tel&eacute;fono m&oacute;vil<br />Twitter</p>';
$('#idPopoverMediosContacto').popover({
	animation : true,
	html: true,
	title : "Medios de contacto",
	content : infoMediosFiscal,
	trigger: 'hover',
	container : 'body'
});

if ($('input#idDomiclioPartGralWidget').length > 0 && typeof AtributosPersonaCtrl != "undefined") {
	AtributosPersonaCtrl.personaPortal.cveDomicilioParticular = $('input#idDomiclioPartGralWidget').val();
}

</script>
<c:if test="${moral.idPersona eq null }">
	<div class="widget-section">
		<input type="hidden" id="idPersonaSesionFM" value='${usuario.cveIdUsuario}' />
		<input type="hidden" id="idPersonaFM" value='${fisica.cveFisica}' />
		<input type="hidden" id="idPersona" value='${fisica.idPersona}' />
		<input type="hidden" id="rfcPersona" value='${fisica.rfc}' />
		<input type="hidden" id="curpPersona" value='${fisica.curp}' />
		<div style="float: right;">
			<c:choose>
				<c:when test="${idPersonaTercero eq '0'}">
					<a class="btn btn-sm icono-help" id="idPopoverDatosGeneralesFisica" data-toggle="popover" title="Datos personales">
					</a>
				</c:when>
				<c:otherwise>
					<a class="btn btn-sm icono-help" id="idPopoverDatosGeneralesFisica" data-toggle="popover" title="Datos particulares">
					</a>
				</c:otherwise>
			</c:choose>
		</div>
		<address>
			<span><strong> Nombre </strong></span><br>
			<span> ${fisica.nombre} ${fisica.primerApellido } ${fisica.segundoApellido } </span><br>
			<span><strong> CURP </strong></span><br>
			<span> ${fisica.curp }</span><br>
			<span><strong> RFC </strong></span><br>
			<c:choose>
				<c:when test="${fisica.rfc eq null }">
					<span class="no-data">No tiene RFC</span>
				</c:when>
				<c:otherwise><span>${fisica.rfc }</span></c:otherwise>
			</c:choose>
			<br>
			<span><strong> Fecha de nacimiento </strong></span><br>
			<span> ${fisica.fechaNacimientoFormateada}</span><br>
			<span><strong> Lugar de nacimiento </strong></span><br>
			<span> ${fisica.lugarNacimiento.nombre}</span><br>
			<c:if test="${empty idPersonaTercero || idPersonaTercero eq '0'}">
				<c:choose>
					<c:when test="${NSS_RECUPERADO eq null }">
						&nbsp;
					</c:when>
					<c:otherwise>
						<span><strong> NSS </strong></span><br>
						<span>${NSS_RECUPERADO}</span>
					</c:otherwise>
				</c:choose>
			</c:if>
		</address>
	</div>
</c:if>

<c:if test="${fisica.idPersona eq null }">
	<div class="widget-section">
		<input type="hidden" id="idPersonaSesionFM" value='${usuario.cveIdUsuario}' />
		<input type="hidden" id="idPersonaFM" value='${moral.cveMoral}' />
		<input type="hidden" id="idPersona" value='${moral.idPersona}' />
		<input type="hidden" id="cveMoral" value='${moral.cveMoral}' />
		<input type="hidden" id="rfcPersona" value='${moral.rfc}' />
		<div style="float: right;">
			<c:choose>
				<c:when test="${idPersonaTercero eq '0'}">
					<a class="btn   btn-sm icono-help" id="idPopoverDatosGeneralesMoral" data-toggle="popover" title="Datos personales">
						
					</a>
				</c:when>
				<c:otherwise>
					<a class="btn   btn-sm icono-help" id="idPopoverDatosGeneralesMoral" data-toggle="popover" title="Datos particulares">
					</a>
				</c:otherwise>
			</c:choose>
		</div>
		<address>
			<strong> <span>${moral.razonSocial}</span></strong><br>
			<span><strong> RFC </strong></span><br>
			<c:choose>
				<c:when test="${moral.rfc eq null}">
					<span class="no-data">No tiene RFC</span>
				</c:when>
				<c:otherwise>
					<span>${moral.rfc}</span>
				</c:otherwise>
			</c:choose>
			<br>
			<span><strong> Tipo de Sociedad </strong></span><br>
			<span>${moral.tipoSociedad.descripcionAbreviada}</span><br>
			<span><strong> Escritura</strong></span><br>
			<c:choose>
				<c:when test="${moral.escrituraConstitutiva eq null}">
					<span class="no-data">No tiene escritura </span>
				</c:when>
				<c:otherwise>
					<label>N&uacutemero de escritura:</label>
						<span>${moral.escrituraConstitutiva.numEscritura}</span>
					<br>
					<label>N&uacutemero de notar&iacutea o correduria: </label>
						<span>${moral.escrituraConstitutiva.numNotaria}</span>
					<br>
					<label>Estado: </label>
						<span>${moral.escrituraConstitutiva.lugarExpedicion.entidadFederativa.nombre}</span>
					<br>
					<label>Municipio o alcald&iacute;a: </label>
						<span>${moral.escrituraConstitutiva.lugarExpedicion.nombre}</span>
					<br>
					
					<label>Fecha de expedici&oacuten: </label>
					<c:if test="${moral.escrituraConstitutiva!=null && moral.escrituraConstitutiva.fechaExpedicion!=null}">
						<span><fmt:formatDate pattern="dd/MM/yyyy" value="${moral.escrituraConstitutiva.fechaExpedicion}"/> </span>
					</c:if>
					<br>
					<label>Folio mercantil: </label>
						<span>${moral.escrituraConstitutiva.folioMercantil}</span>
					<br>
					<label>Secci&oacuten: </label>
						<span>${moral.escrituraConstitutiva.seccion}</span>
					<br>
					<label>Partida: </label>
						<span>${moral.escrituraConstitutiva.partida}</span>
					<br>
					<label>Volumen: </label>
						<span>${moral.escrituraConstitutiva.volumen}</span>
					<br>
					<label>Foja: </label>
						<span>${moral.escrituraConstitutiva.foja}</span>
					<br>
					
				</c:otherwise>
			</c:choose>
			<br>
			
			
			<span><strong>Sindicato</strong></span><br>
						<c:choose>
				<c:when test="${moral.registroSindicato eq null}">
					<span class="no-data">No tiene sindicato</span>
				</c:when>
				<c:otherwise>
					<label>N° referencia  registro:</label>
					<span>${moral.registroSindicato.numReferenciadocRegistro}</span>
					<br>
					<label>Fecha registro: </label>
					<c:if test="${moral.registroSindicato!=null && moral.registroSindicato.fechaRegistro!=null}">
						<span><fmt:formatDate pattern="dd/MM/yyyy" value="${moral.registroSindicato.fechaRegistro}"/> </span>
					</c:if>
					<br>
					<label>Autoridad laboral:</label>
					<span> <font style= "text-transform:uppercase;"> ${moral.registroSindicato.autoridadLaboral}</font></span>
					
				</c:otherwise>
			</c:choose>
			<br>
			
			
			
			<span><strong> Fecha de alta </strong></span><br>
			<span>${moral.fechaCreacionFormateada}</span><br>
		</address>
	</div>
</c:if>

<c:if test="${muestraDomicilio}">
	<br />
	<div class="widget-section">
		<c:choose>
			<c:when test="${not empty domicilio }">
				<input type="hidden" id="idDomiclioPartGralWidget" value="${domicilio.clave}" />
				<div style="float: right;">
					<a class="btn   btn-sm icono-help" id="idPopoverDomicilio" data-toggle="popover">
					</a>
				</div>
				<address>
					<strong> Vialidad </strong><br>
					${domicilio.vialidadPrimaria.nombre}<br>
					<strong> N&uacute;mero interior, N&uacute;mero exterior</strong><br>
					${domicilio.numExteriorAlf} ${domicilio.numExterior1},
					${domicilio.numInteriorAlf} ${domicilio.numInterior}<br>
					<strong> Asentamiento </strong><br>
					${domicilio.asentamiento.nombre}<br>
					<strong> Municipio o alcald&iacute;a </strong><br>
					${domicilio.asentamiento.localidad.municipio.nombre}<br>
					<strong> Estado </strong><br>
					${domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre}<br>
					<strong> C&oacute;digo postal </strong><br>
					C.P. ${domicilio.asentamiento.codigoPostal.codigoPostal}<br>
				</address>
			</c:when>
			<c:otherwise>
				<p>No cuenta con domicilio particular.</p>
			</c:otherwise>
		</c:choose>
	</div>
</c:if>

<c:if test="${muestraMedios}">
	<br />
	<div class="widget-section">
		<c:choose>
			<c:when test="${not empty mediosContacto }">
				<div style="float: right;">
					<a class="btn   btn-sm icono-help" id="idPopoverMediosContacto" data-toggle="popover">

					</a>
				</div>
				<div>
					<c:forEach items="${mediosContacto}" var="medio" varStatus="indice">
						<address>
							<strong>${medio.tipoMedioContacto.descripcion}</strong> <br>
							${medio.desFormaContacto}<br>
						</address>
					</c:forEach>
				</div>
			</c:when>
			<c:otherwise>
				<p>No cuenta con medios de contacto particulares.</p>
			</c:otherwise>
		</c:choose>
	</div>
</c:if>




