<%@ include file="taglibs.jsp" %>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/personas/generalPersonas.js" htmlEscape="true" />"></script>

<script>

$(document).ready(function() {
		
	// Domicilio
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
	//
	
	// Medios de contacto
	
		// Cuando unicamente se captura el numero de telefono particular, y no se captura a clave lada ni la extension, el componente de medios de contacto
		// regresa esos campos en null. Habra que quitarlos manualmente:
		if($('#claveLada').attr('value') == 'null'){
			$('#claveLada').attr('value', '')
		}
		
		if($('#extension').attr('value') == 'null'){
			$('#extension').attr('value', '')
		}
		//
	
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
	//
	
	// se iniciliza el componente del acordeon con la opcion 'autoHeight: false' para que cada DIV colapsable tenga la altura de acuerdo a su contenido
	$('#acordeon').accordion({autoHeight: false});
	
	$('#descripcion').fadeTo('slow', 0.5);
	$('#descripcion').attr("readonly", "readonly");
});

</script>

<div class="page_holder_no_height">
	<div class="post_entry_wide no-border">

		<div class="form-comment">
			<c:set var="contextpath" value="<%=request.getContextPath()%>" />

			<h2 style="font-size: 1.0em !important;" >
				<c:out value='${mensaje}' />
			</h2>
			<br/>
 			<form:form modelAttribute="personaMoral" id="registroPersonaMoralForm" action="" > 

				<div id="acordeon">
							
					<h3><a href='#'>DOMICILIO</a></h3>
					<div>
											
						<jsp:include page="detalleDomicilio.jsp"></jsp:include>
						
					</div>
					
					<h3><a href='#'>MEDIOS DE CONTACTO</a></h3>
					<div>
											
						<form:label path="telefonoFijo.numero" cssClass="wide">N&uacute;mero tel&eacute;fono particular</form:label>
						<form:input path="telefonoFijo.numero" id="numeroTelefonicoParticular" style="width: 200px;" maxlength="8" />
						<br/><br/><br/>	
	
						<form:label path="telefonoFijo.claveLada" cssClass="wide">Clave lada</form:label>
						<form:input path="telefonoFijo.claveLada" id="claveLada" style="width: 100px;" maxlength="3" />
						<br/><br/><br/>	
	
						<form:label path="telefonoFijo.extension" cssClass="wide">Extensi&oacute;n</form:label>
						<form:input path="telefonoFijo.extension" id="extension" style="width: 100px;" maxlength="5" />
						<br/><br/><br/>																									
						
						<form:label path="telefonoMovil.numero" cssClass="wide">N&uacute;mero telef&oacute;nico movil</form:label>
						<form:input path="telefonoMovil.numero" id="numeroTelefonicoMovil" style="width: 200px;" maxlength="10" />
						<br/><br/><br/>	
													
						<form:label path="correoElectronico.correo" cssClass="wide">Correo electr&oacute;nico</form:label>
						<form:input path="correoElectronico.correo" id="correoElectronico" style="width: 200px;" maxlength="50" />
						<br/><br/><br/>
									
					</div>
	
				</div><!-- acordeon -->
				
			</form:form>

		</div>
	</div>
</div>
