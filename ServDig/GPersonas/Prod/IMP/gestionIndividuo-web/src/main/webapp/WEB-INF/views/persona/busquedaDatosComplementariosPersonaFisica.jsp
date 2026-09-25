<%@ include file="taglibs.jsp" %>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/personas/generalPersonas.js" htmlEscape="true" />"></script>

<script>

//CUADRO DE DIALOGO DE CONFIRMACION DE CERRAR VENTANA
var oDialogoConfirmacion;

$(document).ready(function() {
	
	/* documento probatorio */
	$('#tipoDocumento').fadeTo('slow', 0.5);
	$('#tipoDocumento').attr("readonly", "readonly");
	
	$('#anio').fadeTo('slow', 0.5);
	$('#anio').attr("readonly", "readonly");
	
	$('#tomo').fadeTo('slow', 0.5);
	$('#tomo').attr("readonly", "readonly");
	
	$('#crip').fadeTo('slow', 0.5);
	$('#crip').attr("readonly", "readonly");
	
	$('#foja').fadeTo('slow', 0.5);
	$('#foja').attr("readonly", "readonly");
	
	$('#libro').fadeTo('slow', 0.5);
	$('#libro').attr("readonly", "readonly");
	
	$('#numeroActa').fadeTo('slow', 0.5);
	$('#numeroActa').attr("readonly", "readonly");

	
	
	$('#desEntidadRegistro').fadeTo('slow', 0.5);
	$('#desEntidadRegistro').attr("readonly", "readonly");
	
	$('#desMunicipioRegistro').fadeTo('slow', 0.5);
	$('#desMunicipioRegistro').attr("readonly", "readonly");
	
	$('#numFolioExtranjero').fadeTo('slow', 0.5);
	$('#numFolioExtranjero').attr("disabled", "disabled");
	
	$('#anioRegistro').fadeTo('slow', 0.5);
	$('#anioRegistro').attr("disabled", "disabled");
	/**/
	
	/* domicilio */
	$('#codigoPostal').fadeTo('slow', 0.5);
	$('#codigoPostal').attr("readonly", "readonly");

	$('#entidadFederativa').fadeTo('slow', 0.5);
	$('#entidadFederativa').attr("readonly", "readonly");
	
	$('#municipio').fadeTo('slow', 0.5);
	$('#municipio').attr("readonly", "readonly");
	
	$('#localidad').fadeTo('slow', 0.5);
	$('#localidad').attr("readonly", "readonly");
	
	
	$('#tipoAsentamiento').fadeTo('slow', 0.5);
	$('#tipoAsentamiento').attr("readonly", "readonly");
	
	$('#asentamiento').fadeTo('slow', 0.5);
	$('#asentamiento').attr("readonly", "readonly");
	
	$('#tipoVialidadPrimaria').fadeTo('slow', 0.5);
	$('#tipoVialidadPrimaria').attr("readonly", "readonly");
	
	$('#vialidadPrimaria').fadeTo('slow', 0.5);
	$('#vialidadPrimaria').attr("readonly", "readonly");
	
	$('#numeroExteriorPrincipal').fadeTo('slow', 0.5);
	$('#numeroExteriorPrincipal').attr("readonly", "readonly");
	
	$('#numeroExteriorAlfanumerico').fadeTo('slow', 0.5);
	$('#numeroExteriorAlfanumerico').attr("readonly", "readonly");
	
	$('#numeroExteriorSecundario').fadeTo('slow', 0.5);
	$('#numeroExteriorSecundario').attr("readonly", "readonly");
	
	$('#numeroInterior').fadeTo('slow', 0.5);
	$('#numeroInterior').attr("readonly", "readonly");
	
	$('#numeroInteriorAlfanumerico').fadeTo('slow', 0.5);
	$('#numeroInteriorAlfanumerico').attr("readonly", "readonly");
	
	
	$('#tipoVialidadReferenciaPrimaria').fadeTo('slow', 0.5);
	$('#tipoVialidadReferenciaPrimaria').attr("readonly", "readonly");
	
	$('#vialidadReferenciaPrimaria').fadeTo('slow', 0.5);
	$('#vialidadReferenciaPrimaria').attr("readonly", "readonly");
	
	$('#tipoVialidadReferenciaSecundaria').fadeTo('slow', 0.5);
	$('#tipoVialidadReferenciaSecundaria').attr("readonly", "readonly");
	
	$('#vialidadReferenciaSecundaria').fadeTo('slow', 0.5);
	$('#vialidadReferenciaSecundaria').attr("readonly", "readonly");
	
	$('#tipoVialidadReferenciaPosterior').fadeTo('slow', 0.5);
	$('#tipoVialidadReferenciaPosterior').attr("readonly", "readonly");
	
	$('#vialidadReferenciaPosterior').fadeTo('slow', 0.5);
	$('#vialidadReferenciaPosterior').attr("readonly", "readonly");
	/**/
	
	/* Medios de contacto */
 	$('#numeroTelefonicoParticular').fadeTo('slow', 0.5);
 	$('#numeroTelefonicoParticular').attr("readonly", "readonly");
 	
 	$('#claveLada').fadeTo('slow', 0.5);
	$('#claveLada').attr("readonly", "readonly");
	
	$('#extension').fadeTo('slow', 0.5);
	$('#extension').attr("readonly", "readonly");
	
	$('#numeroTelefonicoMovil').fadeTo('slow', 0.5);
	$('#numeroTelefonicoMovil').attr("readonly", "readonly");
	
	$('#correoElectronico').fadeTo('slow', 0.5);
	$('#correoElectronico').attr("readonly", "readonly");
	/**/
	
	/* se iniciliza el componente del acordeon con la opcion 'autoHeight: false' para que cada DIV colapsable tenga la altura de acuerdo a su contenido */
	$('#acordeon').accordion({autoHeight: false});
	
	//Descripcion del domicilio
// 	$('#descripcion').val(objetoDomicilio.descripcion);
	$('#descripcion').fadeTo('slow', 0.5);
	$('#descripcion').attr("readonly", "readonly");
	
});

</script>

<div class="page_holder_no_height">
	<div class="post_entry_wide no-border">
		<div class="form-comment">
			<c:set var="contextpath" value="<%=request.getContextPath()%>" />

			<h2 style="font-size: 1.0em !important;">
				<c:out value='${mensaje}' />
			</h2>
			<br />
			<form:form modelAttribute="fisica" action="">

				<div id="acordeon">
					<jsp:include page="detalleDocumentosProbatorios.jsp"></jsp:include>

					<h3>
						<a href='#'>DOMICILIO</a>
					</h3>
					<div>
						<jsp:include page="detalleDomicilio.jsp"></jsp:include>
					</div>

					<h3>
						<a href='#'>MEDIOS DE CONTACTO</a>
					</h3>
					<div>
						<jsp:include page="detalleMediosContacto.jsp"></jsp:include>
					</div>
				</div>
				<!-- acordeon -->

			</form:form>
		</div>
	</div>
</div>
