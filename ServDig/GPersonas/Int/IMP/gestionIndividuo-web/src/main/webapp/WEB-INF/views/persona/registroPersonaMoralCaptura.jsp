<%@ include file="taglibs.jsp" %>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/personas/generalPersonas.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/personas/domicilio-mediosContacto-registro.js" htmlEscape="true" />"></script>

<script>
$(document).ready(function() {
	if('<c:out value="${botonOprimido}" />' == 'sat') {
		
		$('#registroRfc').fadeTo('slow', 0.5);
		$('#registroRfc').attr("disabled", "disabled");
		
		if('<c:out value="${busquedaSat}" />' == 'true') {
			
			$('#registroRazonSocial').fadeTo('slow', 0.5);
			$('#registroRazonSocial').attr("disabled", "disabled");
		
 			$('#tipoSociedad\\.idTipoSociedad').fadeTo('slow', 0.5);
 			$('#tipoSociedad\\.idTipoSociedad').attr("disabled", "disabled");
			
 			if('<c:out value="${moral.fechaCreacion}" />' != ''){
 				$('#registroFechaCreacionC').fadeTo('slow', 0.5);
 				$('#registroFechaCreacionC').attr("disabled", "disabled");
 			}else{
 				$("#registroFechaCreacionC").datepicker({
 					showOn: 'both',
 					dateFormat: 'dd/mm/yy',
 					changeMonth: true,
 					changeYear: true,
 					yearRange: '-112:+0'
 				});
 			}
			
		}
	
	} else if('<c:out value="${botonOprimido}" />' == 'imss') {
		
		$('#registroRfc').fadeTo('slow', 0.5);
		$('#registroRfc').attr("disabled", "disabled");
		
		$('#registroRazonSocial').fadeTo('slow', 0.5);
		$('#registroRazonSocial').attr("disabled", "disabled");
		
		$('#tipoSociedad\\.idTipoSociedad').fadeTo('slow', 0.5);
		$('#tipoSociedad\\.idTipoSociedad').attr("disabled", "disabled");
		
		$('#registroActaConstitutiva').fadeTo('slow', 0.5);
		$('#registroActaConstitutiva').attr("disabled", "disabled");
		
		if('<c:out value="${moral.fechaCreacion}" />' != ''){
			$('#registroFechaCreacionC').fadeTo('slow', 0.5);
			$('#registroFechaCreacionC').attr("disabled", "disabled");
		}else{
			$("#registroFechaCreacionC").datepicker({
				showOn: 'both',
				dateFormat: 'dd/mm/yy',
				changeMonth: true,
				changeYear: true,
				yearRange: '-112:+0'
			});
		}
		
	}
	
	$('#registrar').click(function(){
		// Justo antes de hacer el submit, 'habilitamos' los combos para que sus respectivos valores puedan ir al controller
		$('input').removeAttr("disabled");
		$('select').removeAttr("disabled");
		$('textarea').removeAttr("disabled");
//		$('#tipoSociedad\\.idTipoSociedad').removeAttr("disabled");
	 	fnProcessAceptar();
		return false;
	});
		
});

function fnProcessAceptar() {
	var descripcionAbreviada = $('select#tipoSociedad\\.idTipoSociedad option:selected').text();
	$('#tipoSociedad\\.descripcionAbreviada').attr('value', descripcionAbreviada);
	
	// Como las propiedades del formulario ya son clases mas complejas que a su vez tienen otras propiedades, ya no podemos usar el metodo 'serializeObject'. En vez de,
	// usaremos el metodo 'toObject'
	var oForm = $("form#registroPersonaMoralForm").toObject();
	var url = context_path +"/persona/moral/registro-captura/validaciones";

	fnHideErrores("form#registroPersonaMoralForm");
	
	$.postJSON(url, oForm, function(data) {

		var urlActualizarLst = context_path + '/persona/moral/registro-persona-solicitud';
		$.postJSON(urlActualizarLst, oForm, function(data2) {
			$('form#irAContenedorTramitesForm').submit();
		}).error(function(data){
			fnProcesarErrores(data, "form#registroPersonaMoralForm");
		});

	}).error(function(data){
		fnProcesarErrores(data, "form#registroPersonaMoralForm");
	});
		
}

</script>

<div class="page_holder_no_height">
	<div class="post_entry_wide no-border">

		<div class="form-comment">
			<c:set var="contextpath" value="<%=request.getContextPath()%>" />

			<h2 style="font-size: 1.0em !important;" >
				<c:out value='${mensaje}' />
			</h2>
			<br/>
 			<form:form modelAttribute="moral" id="registroPersonaMoralForm" action="" > 

				<div class="separadorseccion">Datos de la Persona Moral</div>
							
				<div id="acordeon">
				
					<h3><a href='#'>DATOS BASICOS</a></h3>
					<div id="datosBasicosDiv">		

						<form:label path="rfc" cssClass="wide">RFC</form:label>
						<form:input path="rfc" id="registroRfc" cssStyle="width: 300px" maxlength="12" cssClass="rfc_moral" />
						<span id="rfcError" class="error"></span>
						<br /><br /><br />							
						
						<form:label path="razonSocial" cssClass="wide">Raz&oacute;n Social</form:label>
						<form:input path="razonSocial" id="registroRazonSocial" cssStyle="width: 300px" maxlength="100" />
						<form:errors path="razonSocial" cssClass="error" />
						<br /><br /><br />
						
						<form:label path="tipoSociedad.idTipoSociedad" class="wide">Tipo de Sociedad</form:label>
						<combo:creaCombo idHtml="tipoSociedad.idTipoSociedad" idHtmlContenedor="registroPersonaMoralForm" 
						entidad="mx.gob.imss.ctirss.delta.persistence.DicTipoSociedad" idHtmlValor="${moral.tipoSociedad.idTipoSociedad}" 
						mostrarSoloActivos="true"/>
						<span id="idTipoSociedadError" class="error"></span>
						<br /><br />
	
						<form:label path="actaConstitutiva" cssClass="wide">Acta Constitutiva</form:label>
						<form:input path="actaConstitutiva" id="registroActaConstitutiva" cssStyle="width: 300px" maxlength="13" />
						<span id="actaConstitutivaError" class="error"></span>
						<br /><br /><br />
	
						<form:label path="fechaCreacion" class="wide">Fecha de Creaci&oacute;n</form:label>
						<form:input path="fechaCreacion" id="registroFechaCreacionC" style="width: 70px" maxlength="10" />
						<form:errors path="fechaCreacion" cssClass="error" />
						<br /><br />
						
					</div>
				
					<h3><a href='#'>DOMICILIO</a></h3>
					<div id="domicilioDiv">
						<jsp:include page="detalleDomicilio.jsp"></jsp:include>
						<div style="text-align: right; float: right;">
							<input type="button" value="Registrar Domicilio" class="mboton" id="domicilio" />
						</div>
					</div>
					
					<h3><a href='#'>MEDIOS DE CONTACTO</a></h3>
					<div id="mediosConactoDiv">

						<form:label path="telefonoFijo.numero" cssClass="wide">N&uacute;mero Tel&eacute;fono Particular</form:label>
						<form:input path="telefonoFijo.numero" id="numeroTelefonicoParticular" style="width: 200px;" maxlength="8" />
						<br/><br/><br/>	

						<form:label path="telefonoFijo.claveLada" cssClass="wide">Clave Lada</form:label>
						<form:input path="telefonoFijo.claveLada" id="claveLada" style="width: 100px;" maxlength="3" />
						<br/><br/><br/>	

						<form:label path="telefonoFijo.extension" cssClass="wide">Extensi&oacute;n</form:label>
						<form:input path="telefonoFijo.extension" id="extension" style="width: 100px;" maxlength="5" />
						<br/><br/><br/>																									
						
						<form:label path="telefonoMovil.numero" cssClass="wide">N&uacute;mero Tel&eacute;fono M&oacute;vil</form:label>
						<form:input path="telefonoMovil.numero" id="numeroTelefonicoMovil" style="width: 200px;" maxlength="10" />
						<br/><br/><br/>	
													
						<form:label path="correoElectronico.correo" cssClass="wide">Correo Electr&oacute;nico</form:label>
						<form:input path="correoElectronico.correo" id="correoElectronico" style="width: 200px;" maxlength="50" />
						<br/><br/><br/>
												
						<div style="text-align: right; float: right;">
							<input type="button" value="Registrar Medios de Contacto" class="mboton" id="mediosContacto" />
						</div>	
									
					</div>
					
				</div><!-- acordeon -->
				
				<br />
				<div><span id="errorNegocioLabel" class="error hiddenElement"></span></div>
				<br />
				<div><span id="errorFormGeneralError" class="error hiddenElement"></span></div>
				
				<div style="text-align: right; float: right;">
					<input type="button" value="Regresar" id="regresar" class="mboton" />
					<input type="button" value="Registrar" id="registrar" class="mboton" />
				</div>
				
				<!-- estos divs seran lo que contengan el cuadro de dialogo de jquery que vienen de los componentes externos para capturar datos complementarios -->
				<div id="domicilioRegistrar"></div>
				<div id="mediosContactoRegistrar"></div>
				<!-- -->

				<form:hidden path="tipoSociedad.descripcionAbreviada"/>

				<form:hidden path="domicilios[0].asentamiento.localidad.municipio.entidadFederativa.clave" id="claveEntidadFederativa" />
				<form:hidden path="domicilios[0].asentamiento.localidad.municipio.clave" id="claveMunicipio" />
				<form:hidden path="domicilios[0].asentamiento.localidad.clave" id="claveLocalidad" />
				<form:hidden path="domicilios[0].asentamiento.clave" id="claveAsentamiento" />

				<form:hidden path="personaCalificaciones[0].calificacion.idCalificacion" id="_registroIdEstatus" />
				<form:hidden path="personaCalificaciones[0].calificacion.descripcion" id="_registroDesEstatus" />
				
			</form:form>

			<!-- Este formulario se usa unicamente para redireccionar al contenedor de tramites mediante un submit -->
			<form id="irAContenedorTramitesForm" action="${contextpath}/persona/tramites/agregar/moral" method="get"></form>
			
		</div>
	</div>
</div>
