<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.PropietarioMedioContactoEnum"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/nuevoSocio.js" htmlEscape="true" />"></script>	

<!-- script>
var tpPropietarioSocioPersonaFisica;
var idPropietarioSocioPersonaFisica;

tpPropietarioSocioPersonaFisica = <%=PropietarioMedioContactoEnum.SOCIO.getCodigo()%>;
idPropietarioSocioPersonaFisica = 0;

</script-->

<div id="divGralAgregarSocio" style="float: left; width: 100% !important; padding-top: 10px;" class="page_holder dialogo_holder">	
	<h2 style="font-size: 1.0em !important;">Agregar Socios</h2>
	<c:set var="contextpath" value="<%=request.getContextPath()%>" />		
	<form:form modelAttribute="socio" id="formNuevoSocio" action="">
		<fieldset>
			<legend>
				<strong>Personalidad fiscal</strong>
			</legend>
			<form:radiobutton path="tipoSocio.idTipoPersona" id="tipoPersonaFisica" value="1" checked class="show_hide_fisica"/>
			Persona F&iacute;sica
			<form:radiobutton path="tipoSocio.idTipoPersona"  id="tipoPersonaMoral" value="2" class="show_hide_moral"/>
			Persona Moral
			<form:radiobutton path="tipoSocio.idTipoPersona"  id="tipoFideicomiso" value="3" class="show_hide_moral"/>
			Fideicomiso
		</fieldset>
		
		<fieldset>
			<legend>
				<strong>Nacionalidad</strong>
			</legend>
			<label>&iquest;Es socio extranjero?</label>
			<form:checkbox path="esNacional" id="esSocioExtranjero" onchange="validaSocioExtranjero()"/>
		</fieldset>
		
		<fieldset>
			<legend>
				<strong>Residencia</strong>
			</legend>
			<form:radiobutton path="esDomicilioNacional" id="radioDomNacional"  value="1" checked="checked" class="show_hide_domicilio_nacional"/>
			Domicilio Nacional
			<form:radiobutton path="esDomicilioNacional" id="radioDomExtranjero" value="0" class="show_hide_domicilio_extranjero"/>
			Domicilio Extranjero
		</fieldset>
		<table style="width: 100%; border: none !important;">
			<tr>
				<td align="center" style="border: none !important">
					<input type="button" class="mboton" value="Aceptar" style="font-size: .8em !important;" onclick="validaDatosNuevoSocio()">
					<input type="button" class="mboton" value="Cancelar" style="font-size: .8em !important;" onclick="cerrarDialogoSeleccionNuevoSocio()">
				</td>
			</tr>
		</table>		
	</form:form>
</div>

<div id="divNueoSocioPersonaFisica">
		<jsp:include page="nuevoSocioPersonaFisica.jsp" />
</div>
<div id="divNueoSocioPersonaMoral">
		<jsp:include page="nuevoSocioPersonaMoral.jsp" />
</div>
<div id="divNueoSocioPersonaFideicomiso">
		<jsp:include page="nuevoSocioFideicomiso.jsp" />
</div>