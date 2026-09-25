<%@ include file="taglibs.jsp" %>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/personas/generalPersonas.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/personas/domicilio-mediosContacto-registro.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/general.js" htmlEscape="true" />"></script>

<script>

$(window).load(function() {
	$('#documentosProbatoriosDiv select#actaNacimiento\\.municipio\\.entidadFederativa\\.clave').trigger('change');
});

$(document).ready(function() {
		
	$.ajaxSetup({async:false});
	
	deshabilitarCamposDatosBasicos();
	deshabilitarCamposDocumentosProbatorios();
	deshabilitarCamposDomicilio();
	deshabilitarCamposMediosDeContacto();
	
	$('#registrar').click(function(){
		// Justo antes de hacer el submit, 'habilitamos' los combos para que sus respectivos valores puedan ir al controller
		$('input').removeAttr("disabled");
		$('select').removeAttr("disabled");
		$('textarea').removeAttr("disabled");
// 		$('#sexo\\.idSexo').removeAttr("disabled");
// 		$('#lugarNacimiento\\.clave').removeAttr("disabled");
// 	 	$('#actaNacimiento\\.municipio\\.entidadFederativa\\.clave').removeAttr("disabled");
// 	 	$('#actaNacimiento\\.municipio\\.clave').removeAttr("disabled");

	 	fnProcessAceptar();
		return false;
	});
		
});

function deshabilitarCamposDatosBasicos(){
	
	// Este bloque es para la busqueda unificada
// 	if('<c:out value="${botonOprimido}" />' == 'unificada'){
		
// 		if('<c:out value="${fisica.curp}" />' != ''){
		if($('#registroCurp').val() != ''){
 			$('#registroCurp').fadeTo('slow', 0.5);
			$('#registroCurp').attr("disabled", "disabled");
		}
		
// 		if('<c:out value="${fisica.rfc}" />' != ''){
		if($('#registroRfc').val() != ''){
			$('#registroRfc').fadeTo('slow', 0.5);
			$('#registroRfc').attr("disabled", "disabled");
		}
		
// 		if('<c:out value="${fisica.nombre}" />' != ''){
		if($('#registroNombres').val() != ''){
			$('#registroNombres').fadeTo('slow', 0.5);
			$('#registroNombres').attr("disabled", "disabled");
		}

//		if('<c:out value="${fisica.primerApellido}" />' != ''){
		if($('#registroPrimerApellido').val() != ''){
			$('#registroPrimerApellido').fadeTo('slow', 0.5);
			$('#registroPrimerApellido').attr("disabled", "disabled");
		}
		
// 		if('<c:out value="${fisica.segundoApellido}" />' != ''){
		if($('#registroSegundoApellido').val() != ''){
			$('#registroSegundoApellido').fadeTo('slow', 0.5);
			$('#registroSegundoApellido').attr("disabled", "disabled");
		}
		
		if('<c:out value="${fisica.sexo.idSexo}" />' != '-1'){
// 		if($('#sexo\\.idSexo').val() != '-1'){ asi deberia quedar, pero hasta que se resuelva el problema de la sincronizacion
			$('#sexo\\.idSexo').fadeTo('slow', 0.5);
			$('#sexo\\.idSexo').attr("disabled", "disabled");
		}
		
// 		if('<c:out value="${fisica.fechaNacimiento}" />' != ''){
		if($('#registroFechaNacimientoC').val() != ''){
			$('#registroFechaNacimientoC').fadeTo('slow', 0.5);
			$('#registroFechaNacimientoC').attr("disabled", "disabled");
		}else{
			$("#registroFechaNacimientoC").datepicker({
				showOn: 'both',
				dateFormat: 'dd/mm/yy',
				changeMonth: true,
				changeYear: true,
				yearRange: '-112:+0'
			});
		}
		
		if('<c:out value="${fisica.lugarNacimiento.clave}" />' != '-1'){
// 		if($('#lugarNacimiento\\.clave').val() != '-1'){ asi deberia quedar, pero hasta que se resuelva el problema de la sincronizacion
			$('#lugarNacimiento\\.clave').fadeTo('slow', 0.5);
			$('#lugarNacimiento\\.clave').attr("disabled", "disabled");
		}
		
// 	}
				
}

function deshabilitarCamposDocumentosProbatorios(){
	
	// Los datos del documento probatorio se ponen como disabled
	$('#tipoDocumento').fadeTo('slow', 0.5);
	$('#tipoDocumento').attr("disabled", "disabled");
	
	$('#anio').fadeTo('slow', 0.5);
	$('#anio').attr("disabled", "disabled");
	
	$('#tomo').fadeTo('slow', 0.5);
	$('#tomo').attr("disabled", "disabled");
	
	$('#crip').fadeTo('slow', 0.5);
	$('#crip').attr("disabled", "disabled");
	
	$('#foja').fadeTo('slow', 0.5);
	$('#foja').attr("disabled", "disabled");
	
	$('#libro').fadeTo('slow', 0.5);
	$('#libro').attr("disabled", "disabled");
	
	$('#numeroActa').fadeTo('slow', 0.5);
	$('#numeroActa').attr("disabled", "disabled");
	
	$('#actaNacimiento\\.municipio\\.entidadFederativa\\.clave').fadeTo('slow', 0.5);
	$('#actaNacimiento\\.municipio\\.entidadFederativa\\.clave').attr("disabled", "disabled");
	
	$('#actaNacimiento\\.municipio\\.clave').fadeTo('slow', 0.5);
	$('#actaNacimiento\\.municipio\\.clave').attr("disabled", "disabled");
	
	$('#numFolioExtranjero').fadeTo('slow', 0.5);
	$('#numFolioExtranjero').attr("disabled", "disabled");
	
	$('#anioRegistro').fadeTo('slow', 0.5);
	$('#anioRegistro').attr("disabled", "disabled");
}

function fnProcessAceptar() {
	// DATOS BASICOS -- Se setean las propiedades de descripcion que obtendremos a partir de la opcion seleccionada en el combo respectivo
	var desSexo = $('select#sexo\\.idSexo option:selected').text();
	$('#sexo\\.descripcion').attr('value', desSexo);
	var desEntidadNacimiento = $('select#lugarNacimiento\\.clave option:selected').text();
	$('#lugarNacimiento\\.nombre').attr('value', desEntidadNacimiento);
	
	// DOCUMENTOS PROBATORIOS -- Se setean las propiedades de descripcion que obtendremos a partir de la opcion seleccionada en el combo respectivo
	var desEntidadRegistro = $('select#actaNacimiento\\.municipio\\.entidadFederativa\\.clave option:selected').text();
	$('#actaNacimiento\\.municipio\\.entidadFederativa\\.nombre').attr('value', desEntidadRegistro);
	var desMunicipioRegistro = $('select#actaNacimiento\\.municipio\\.entidadFederativa\\.clave option:selected').text();
	$('#actaNacimiento\\.municipio\\.entidadFederativa\\.nombre').attr('value', desMunicipioRegistro);

	// Como las propiedades del formulario ya son clases mas complejas que a su vez tienen otras propiedades, ya no podemos usar el metodo 'serializeObject'. En vez de,
	// usaremos el metodo 'toObject'
	var oForm = $("form#registroPersonaFisicaForm").toObject();
	var url = context_path +"/persona/fisica/registro-captura/validaciones";

	fnHideErrores("form#registroPersonaFisicaForm");
	
	$.postJSON(url, oForm, function(data) {

		var urlActualizarLst = context_path + '/persona/fisica/registro-persona-solicitud';
		$.postJSON(urlActualizarLst, oForm, function(data2) {
			$('form#irAContenedorTramitesForm').submit();
		}).error(function(data){
			fnProcesarErrores(data, "form#registroPersonaFisicaForm");
		});

	}).error(function(data){
		fnProcesarErrores(data, "form#registroPersonaFisicaForm");
		// despues de que se hace el submit, si es que hay errores de validaciones en el formulario, hay que regresar a esta misma pagina para corregir 
		// los errores, pero quien sabe por que chingados los campos que eran disabled ya no lo son, entonces hay que llamar nuevamente a este metodo
		// para que se deshabiliten los campos a webo... el pedo ahora esta en los combos, porque parece haber nuevamente el problema de que se ejecutan
		
		deshabilitarCamposDatosBasicos();
		deshabilitarCamposDocumentosProbatorios();
		deshabilitarCamposDomicilio();
		deshabilitarCamposMediosDeContacto();
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
 			<form:form modelAttribute="fisica" id="registroPersonaFisicaForm" action="" > 

				<div class="separadorseccion">Datos de la Persona F&iacute;sica</div>
							
				<div id="acordeon">
				
					<h3><a href='#'>DATOS BASICOS</a></h3>
					<div id="datosBasicosDiv">		
						
						<form:label path="curp" cssClass="wide">CURP</form:label>
						<form:input path="curp" id="registroCurp" cssStyle="width: 300px" maxlength="18" />
						<span id="curpError" class="error hiddenElement"></span>
						<br/><br/><br/>
						
						<form:label path="rfc" cssClass="wide">RFC</form:label>
						<form:input path="rfc" id="registroRfc" cssStyle="width: 300px" maxlength="13" />
						<span id="rfcError" class="error hiddenElement"></span>
						<br/><br/><br/>					
						
						<form:label path="nombre" cssClass="wide">Nombre(s)</form:label>
						<form:input path="nombre" id="registroNombres" cssStyle="width: 300px" maxlength="50" />
						<br/><br/><br/>
						
						<form:label path="primerApellido" cssClass="wide">Primer Apellido</form:label>
						<form:input path="primerApellido" id="registroPrimerApellido" cssStyle="width: 300px" maxlength="50" />
						<br/><br/><br/>
	
						<form:label path="segundoApellido" cssClass="wide">Segundo Apellido</form:label>
						<form:input path="segundoApellido" id="registroSegundoApellido"	cssStyle="width: 300px" maxlength="50" />
						<br/><br/><br/>
						
						<form:label path="sexo.idSexo" cssClass="wide">Sexo</form:label>
						<combo:creaCombo idHtml="sexo.idSexo" idHtmlContenedor="registroPersonaFisicaForm"
						 entidad="mx.gob.imss.ctirss.delta.persistence.DicSexo" idHtmlValor="${fisica.sexo.idSexo}" 
						 mostrarSoloActivos="true"/>
						<span id="sexo.idSexoError" class="error hiddenElement"></span>
						<br/><br/>
						
						<form:label path="fechaNacimiento" cssClass="wide">Fecha de Nacimiento</form:label>
						<form:input path="fechaNacimiento" id="registroFechaNacimientoC" style="width: 70px" maxlength="10" />
						<span id="fechaNacimientoError" class="error hiddenElement"></span>
						<br/><br/><br/>
						
						<form:label path="lugarNacimiento.clave" cssClass="wide">Lugar de Nacimiento</form:label>
						<combo:creaCombo idHtml="lugarNacimiento.clave" idHtmlContenedor="registroPersonaFisicaForm" 
						entidad="mx.gob.imss.ctirss.delta.persistence.DgCatEstado"  idHtmlValor="${fisica.lugarNacimiento.clave}" 
						mostrarSoloActivos="true"/>
						<span id="lugarNacimiento.claveError" class="error hiddenElement"></span>
						<br/><br/>
						
					</div>

					<jsp:include page="detalleDocumentosProbatorios.jsp"></jsp:include>

					<h3><a href='#'>DOMICILIO</a></h3>
					<div id="domicilioDiv">
						
						<jsp:include page="detalleDomicilio.jsp"></jsp:include>							
						
						<div style="text-align: right; float: right;">
							<input type="button" value="Registrar Domicilio" class="mboton" id="domicilio" />
						</div>
						
					</div>
					
					<h3><a href='#'>MEDIOS DE CONTACTO</a></h3>
					<div id="mediosConactoDiv">

						<jsp:include page="detalleMediosContacto.jsp"></jsp:include>
												
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

<form:hidden path="sexo.descripcion" id="sexo.descripcion" />
<form:hidden path="lugarNacimiento.nombre" id="lugarNacimiento.nombre" />
<form:hidden path="actaNacimiento.municipio.entidadFederativa.nombre" id="desEntidadRegistro" />
<form:hidden path="actaNacimiento.municipio.nombre" id="desMunicipioRegistro" />

<form:hidden path="domicilios[0].asentamiento.localidad.municipio.entidadFederativa.clave" id="claveEntidadFederativa" />
				<form:hidden path="domicilios[0].asentamiento.localidad.municipio.clave" id="claveMunicipio" />
				<form:hidden path="domicilios[0].asentamiento.localidad.clave" id="claveLocalidad" />
				<form:hidden path="domicilios[0].asentamiento.clave" id="claveAsentamiento" />

				<!-- Se ponen los indices 0 y 1 para el caso de que se consulte una persona por SAT y como se trajo la curp entonces se busca de nuevo pero
					 ahora en RENAPO. En esta caso, hay 2 calificaciones. Para los demas casos en los que solo existe 1 calificacion, no afecta que exista
					 el idice 1 -->
				<form:hidden path="personaCalificaciones[0].calificacion.idCalificacion" />
				<form:hidden path="personaCalificaciones[0].calificacion.descripcion" />
				
				<form:hidden path="personaCalificaciones[1].calificacion.idCalificacion" />
				<form:hidden path="personaCalificaciones[1].calificacion.descripcion" />
				<!--  -->
				
				<!-- Lista de identificadores que necesitamos que viaje por el request, aunque aqui no se procese para ni madres -->
<%-- 				<c:forEach var="identificadorActual" varStatus="indice" items="${fisica.identificadores}"> --%>
<%-- 					Identificador ${indice.index}: id = ${identificadorActual.id}, descripcion = ${identificadorActual.descripcion} <br> --%>
<%-- 					<form:hidden path="identificadores[${indice.index}].identificadora" /> --%>
<%-- 					<form:hidden path="identificadores[${indice.index}].vigente" /> --%>
<%-- 					<form:hidden path="identificadores[${indice.index}].tipoIdentificador.idTipoIdentificador" /> --%>
<%-- 					<form:hidden path="identificadores[${indice.index}].tipoIdentificador.desIdentificador" /> --%>
<%-- 				</c:forEach> --%>
				
									 				
			</form:form>

			<!-- Este formulario se usa unicamente para redireccionar al contenedor de tramites mediante un submit -->
			<form id="irAContenedorTramitesForm" action="${contextpath}/persona/tramites/agregar/fisica" method="get"></form>
			
		</div>
	</div>
</div>
