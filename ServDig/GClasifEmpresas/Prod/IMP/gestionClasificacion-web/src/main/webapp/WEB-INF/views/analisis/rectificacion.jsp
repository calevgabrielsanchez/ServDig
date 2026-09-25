<%@ include file="../general/taglibs.jsp"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<c:set var="esMovimientoPatronal" value="<%=session.getAttribute(\"grupoTramite\") %>" />


<%-- <script type="text/javascript" src="${staticResourcesPath}/js/clasificador/clasificacion.js"></script></script> --%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/general/clasificador.js" htmlEscape="true" />"></script>
	

<script type="text/javascript">
	String.prototype.trim = function() {return this.replace(/^\s+|\s+$/g, '')};
	
	var errActDetectada;
	var errComentario;
	var errPrimaSugerida;
	var esMovimientoPatronal = '${esMovimientoPatronal}';
 	
	function asignaValores() {
		fraccionActual = document.getElementById("cveIdFraccionAct").value;
		fraccionPropuesta = document.getElementById("cveFraccionId").innerHTML;
		patronDictamen = document.getElementById("cveIdPatronDictamen").value;

		console.log("Asignando propuesta, fraccionActual:: " + fraccionActual
				+ ", fraccionPropuesta:: " + fraccionPropuesta
				+ ", patronDictamen: " + patronDictamen);

		/* 		alert("Asignando propuesta, fraccionActual:: " + fraccionActual
		 + ", fraccionPropuesta:: " + fraccionPropuesta + ", patronDictamen: " + patronDictamen);
		 */
		if (fraccionActual == fraccionPropuesta && (patronDictamen == '') && esMovimientoPatronal==1) {
			alert("La Clasificación seleccionada es la misma que el Patrón ha declarado"
					+ "\nPor favor, seleccione una distinta");
		} else if (validaVacios()) {
			errActDetectada = document.getElementById('errActDetectada');
			errComentario = document.getElementById('errComentario');
			errPrimaSugerida = document.getElementById('errPrimaSugerida');
			
			errActDetectada.style.display = 'none';
			errComentario.style.display = 'none';
			if(errPrimaSugerida) {
				errPrimaSugerida.style.display = 'none';
			}
			
			document.forms['rectificacionMovimiento'].clase.value = document
					.getElementById("cveClaseId").innerHTML;
			document.forms['rectificacionMovimiento'].cveIdFraccion.value = document
					.getElementById("cveFraccionId").innerHTML;
			document.forms['rectificacionMovimiento'].cveIdDivision.value = document
					.getElementById("cveDivisionId").innerHTML;
			document.forms['rectificacionMovimiento'].cveIdGrupo.value = document
					.getElementById("cveGrupoId").innerHTML;
			document.forms['rectificacionMovimiento'].primaSRTPro.value = document
					.getElementById("cveprimaDes").innerHTML;
			$.blockUI();
			document.getElementById('rectificacionMovimiento').submit();
		}
	}

	function validaVacios() {
		var bool = false;
	
		actividadDetectada = document.getElementById('actividadDetectada');
		comentarios = document.getElementById('comentarios');

		errActDetectada = document.getElementById('errActDetectada');
		errComentario = document.getElementById('errComentario');
		
		errActDetectada.style.display = 'none';
		errComentario.style.display = 'none';
		
		actividadDetectada2 = document.getElementById('actividadDetectada').value;
		comentarios2 = document.getElementById('comentarios').value;
		
		if(validaPrimaVacio() == false){
			bool = false
		}else if (actividadDetectada.value == null
				|| actividadDetectada2.trim() == "") {
			errActDetectada.value = "Campo Obligatorio";
			errActDetectada.style.display = 'block';
			bool = false;
		} else if (comentarios.value == null || comentarios2.trim() == "") {
			errComentario.value = "Campo Obligatorio";
			errComentario.style.display = 'block';
			bool = false;
		} else {

			if (actividadDetectada2.trim().length > 255) {
				errComentario.value = "El campo Actividad Detectada excede a la longitud permitida de 255 caracteres";
				errComentario.style.display = 'block';
				bool = false;
			} else if (comentarios2.trim().length > 2500) {
				errComentario.value = "El campo de Comentarios excede a la longitud permitida de 2500 caracteres";
				errComentario.style.display = 'block';
				bool = false;
			} else {
				bool = true;
			}
		}
		
		
		return bool;

	}
	
	function validaPrimaVacio() {
		
		primaSugerida = document.getElementById('primaSugerida');
		var trPrimaSugeridaInfo = document.getElementById('trPrimaSugerida');
		bool = true;
		if(primaSugerida && trPrimaSugeridaInfo && trPrimaSugeridaInfo.style.display == 'block') {
			console.log("ENTRO A VALIDAR LA PRIMA")
			errPrimaSugerida = document.getElementById('errPrimaSugerida');
			errPrimaSugerida.style.display = 'none';
			primaSugerida2 = document.getElementById('primaSugerida').value;
			
			if(primaSugerida2 == null || primaSugerida.value.trim() == "") {
				errPrimaSugerida.value = "Campo Obligatorio";
				errPrimaSugerida.style.display = 'block';
				bool = false;
			}
			
			if(parseFloat(primaSugerida2) < 0.5 || parseFloat(primaSugerida2) > 15.0){ //Valida valor de la prima
				bool=false;
				alert("El valor de la prima no puede ser menor a 0.5 y no puede ser mayor a 15.0 ");
			}
		}
		
		return bool;
	}

	$(document).ready(function() {
		limitarCampo('comentarios', 2500);
	});

	/*Valida Caracteres*/
	var campoGiroNegado = /[^\sa-zA-Z\d\u00F1\u00E1\u00E9\u00ED\u00F3\u00FA\u00D1\u00C1\u00C9\u00CD\u00D3\u00DA\#\%\(\)\,\-\.\/\?\@\*\']/g;

	function validaCaracteresKeyUp(event) {
		event.target.value = event.target.value.replace(campoGiroNegado, "");
	}
</script>

<!-- JS de la pagina -->

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/general/general.js" htmlEscape="true" />"></script>
<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="cveIdDelegacion" value="${cveIdDelegacion}" />
<c:set var="cveIdSubdelegacion" value="${cveIdSubdelegacion}" />
<c:set var="tipoTramite" value="${tipoTramite}" />

<form id="clasificacionForm">
	<input type="hidden" id="idDivision" value="vacio"> <input
		type="hidden" id="idGrupo" value="vacio"> <input type="hidden"
		id="idFraccion" value="vacio"> <input type="hidden" id="clase"
		value="vacio"> <input type="hidden"
		id="descActividadEconomicaDetectada" value="vacio">
</form>

<form id="formClasificacionSolicitud">
	<!-- Hiddens para el control y manejo de la clasificacion -->
	<input type="hidden" id="idDivisionb" name="idDivisionb" value="" /> <input
		type="hidden" id="idGrupob" name="idGrupob" value="" /> <input
		type="hidden" id="idFraccionb" name="idFraccionb" value="" /> <input
		type="hidden" id="claseb" name="claseb" value="" /> <input
		type="hidden" id="cveIdClasificacionb" name="cveIdClasificacionb"
		value="" />
</form>

<div align="center">
	<table style="width: 100%">
		<thead>
			<tr valign="top" class="par">
				<td align="center" style="padding-bottom: .5em; padding-top: .5em;">
					<div class="separadorseccion">Rectificación de la
						clasificación de las empresas en el seguro de riesgos de trabajo</div>
				</td>
			</tr>
		</thead>

		<tr>
			<td colspan="1"><center>
					<div id="msgAuxiliar" style="display: 'none'; color: #8B0000;"></div>
				</center></td>
		</tr>

		<tr>
			<td align="center" style="padding-bottom: 10px;">
				<div id="wrapperIntsAnterior" class="ui-widget"
					style="width: 700px !important;">
					<div class="ui-state-highlight ui-corner-all"
						style="margin-top: 20px; padding: 0 .5em;" align="center">
						<p>
							<span class="ui-icon ui-icon-info"
								style="float: left; margin-right: .3em;"></span> <strong>Seleccione
								la nueva clasificaci&oacute;n conforme al cat&aacute;logo para
								la clasificaci&oacute;n de las empresas en el Seguro de Riesgo
								de Trabajo:</strong> <strong><a
								href="javascript:fnOpenClasificador();"> Aqu&iacute;</a></strong>
						</p>
					</div>
				</div>
			</td>
		</tr>
	</table>
	<div id="dgEliminarProductos"></div>
	<form:form id="rectificacionMovimiento" name="rectificacionMovimiento"
		action="${contextpath}/rectificacion/${cveIdAnalisis}/rectificadoPendiente"
		method="post">

		<input type="hidden" id="idTipoPersona" name="idTipoPersona"
			value="${tipoPersona}" />
		<input type="hidden" id="regPatron" name="regPatron"
			value="${regPatronal}" />
		<input type="hidden" id="indRegPatClase" name="indRegPatClase"
			value="" />
		<input type="hidden" id="cveIdDivision" name="cveIdDivision" value="" />
		<input type="hidden" id="cveIdGrupo" name="cveIdGrupo" value="" />
		<input type="hidden" id="cveIdFraccion" name="cveIdFraccion" value="" />
		<input type="hidden" id="clase" name="clase" value="" />
		<input type="hidden" id="cveIdDelegacion" name="cveIdDelegacion"
			value="${cveIdDelegacion}" />
		<input type="hidden" id="cveIdSubdelegacion" name="cveIdSubdelegacion"
			value="${cveIdSubdelegacion}" />
			<input type="hidden" id="cveIdClaseAct" name="cveIdClaseAct"
			value="${cveIdClaseAct}" />
		<!--  -->
		<input type="hidden" id="cveIdFraccionAct" name="cveIdFraccionAct"
			value="${cveIdFraccionAct}" />
		<input type="hidden" id="cveIdFraccionAnt" name="cveIdFraccionAnt"
			value="${cveIdFraccionAnt}" />
		<input type="hidden" id="primaSRTAct" name="primaSRTAct"
			value="${primaSRTAct}" />
		<!-- primaSRTPro se llena  al hacer submit -->
		<input type="hidden" id="primaSRTPro" name="primaSRTPro" />
		<input type="hidden" id="primaSRTAnt" name="primaSRTAnt"
			value="${primaSRTAnt}" />
		<input type="hidden" id="cveIdPatronDictamen"
			name="cveIdPatronDictamen" value="${cveIdPatronDictamen}" />

		<table style="width: 85%" cellpadding="0px;">
			<tr class="fielsetgris2">
				<td style="display: none;"><span>ids</span></td>
				<td valign="top"><span class="etiqueta">
						Divisi&oacute;n:</span> <span id="idDivisionError"
					class="error hiddenElement"> </span></td>
				<td valign="top"><span class="etiqueta"> Grupo:</span><span
					id="idGrupoError" class="error hiddenElement"> </span></td>
				<td valign="top"><span class="etiqueta">
						Fracci&oacute;n:</span><span id="idFraccionError"
					class="error hiddenElement"> </span></td>
				<td valign="top"><span class="etiqueta"> Clase:</span></td>
				<td valign="top"><span class="etiqueta"> Prima SRT:</span></td>
			</tr>
			<tr>
				<td style="display: none;"><span class="dato"
					id="cveFraccionId">${clasificacion.fraccion.id}</span> <span
					class="dato" id="cveDivisionId">${clasificacion.fraccion.grupo.division.id}</span>
					<span class="dato" id="cveGrupoId">${clasificacion.fraccion.grupo.id}
				</span> <span class="dato" id="cveClaseId">${clasificacion.fraccion.clase.clave}</span>
				</td>
				<td valign="top"><span class="dato" id="cvedivisionDes">
						${clasificacion.fraccion.grupo.division.descripcion} </span></td>
				<td valign="top"><span class="dato" id="cvegrupoDes">
						${clasificacion.fraccion.grupo.descripcion} </span></td>
				<td valign="top"><span class="dato" id="cvefraccionDes">
						${clasificacion.fraccion.descripcion} </span></td>
				<td valign="top"><span class="dato" id="cveclaseDes">
						${clasificacion.fraccion.clase.descripcion} </span></td>
				<td valign="top"><span class="dato" id="cveprimaDes">
						${clasificacion.fraccion.primaSRT}</span></td>
			</tr>
			<tr>
				<td colspan="5">&nbsp;</td>
			</tr>
		</table>
			<c:if test="${(tipoTramite != 1 || tipoTramite != 120 || tipoTramite != 7) && esMovimientoPatronal ne 1}">
			<table id="trPrimaSugerida" style="display:none; width: 82%;">
			<!--
				Solo funcionará con modificaciones patronales
				Prima sugerida Tramite de alta es el  Si
				Si (Tramite != 1 && tramite != 120 (&& cambio de domicilio) && y la clase que seleccionaron es la misma)
				se muestra campo de captura
			-->
				<tr >
					<td><label id="errPrimaSugerida" class="wide"
						style="color: red; display: none;">Dato obligatorio</label> <label
						class="wide">Prima sugerida:&nbsp;&nbsp;</label></td>
						<td colspan="1" align="left"><input name="primaSugerida"
						id="primaSugerida" type="text" maxlength="8"
						style="border: 1px !important; width: 546px !important; height: 20px !important; background-color: rgb(255, 255, 255) !important; border-color: rgb(211, 211, 211) !important; border-left-style: solid !important; border-right-style: solid !important; border-bottom-style: solid !important; border-top-style: solid !important; border-left-width: 0.5pt !important; border-right-width: 0.5pt !important; border-top-width: 0.5pt !important; border-bottom-width: 0.5pt !important; text-transform: uppercase !important;"
						onkeyup="validaCaracteresKeyUp(event);"
						onblur="validaCaracteresKeyUp(event)" /></td>
				</tr>
				</table>
			</c:if>
			
			<table>
			<tr>
				<td><label id="errActDetectada" class="wide"
					style="color: red; display: none;">Dato obligatorio</label> <label
					class="wide">Actividad detectada:&nbsp;&nbsp;</label></td>
				<td colspan="1" align="left"><input name="actividadDetectada"
					id="actividadDetectada" type="text" maxlength="200"
					style="border: 1px !important; width: 546px !important; height: 20px !important; background-color: rgb(255, 255, 255) !important; border-color: rgb(211, 211, 211) !important; border-left-style: solid !important; border-right-style: solid !important; border-bottom-style: solid !important; border-top-style: solid !important; border-left-width: 0.5pt !important; border-right-width: 0.5pt !important; border-top-width: 0.5pt !important; border-bottom-width: 0.5pt !important; text-transform: uppercase !important;"
					onkeyup="validaCaracteresKeyUp(event);"
						onkeypress="return checkCaracterEspecial(event)"
					onblur="validaCaracteresKeyUp(event)" /></td>
			</tr>
			<!-- 
			<tr><td colspan="2">&nbsp;< f orm : e rrors path="comentario.descripcion" cssClass="error">< / fo rm : e rrors></td></tr>
			 -->
			<tr>
				<td><label id="errComentario" class="wide"
					style="color: red; display: none;">Dato obligatorio</label> <label
					class="mwide">Comentarios:&nbsp;&nbsp;</label></td>

				<td><textarea id="comentarios" name="comentarios" cols="90"
						rows="4" style="text-transform: uppercase;" maxlength="2500"
						onkeypress="return checkCaracterEspecial(event)"></textarea></td>
			</tr>


			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>

			<tr>
				<td colspan="2" align="right"><input type="button"
					id="rectificacionAceptar" name="rectificacionAceptar"
					class="mboton" style="width: 120px;" value="Aceptar"
					onclick="asignaValores()" /> <!--input type="button" class="mboton" style="width: 120px;" id="btnRegresar" name="btnRegresar" value="Regresar" /-->
				</td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
		</table>
	</form:form>
</div>
<c:if test="${(tipoTramite != 1 || tipoTramite != 120 || tipoTramite != 7) && esMovimientoPatronal ne 1}">
<script type="text/javascript">
	$(function() {		
		$("input[name='primaSugerida']").numeric({
			maxDigits: 8,
			maxDecimalPlaces: 5,
			maxPreDecimalPlaces: 3					
		});
	});
</script>
</c:if>
<c:if var="popUp" test="${popUp==1}">
	<script>
		$.blockUI();
		fnOpenClasificador();
		$.unblockUI();
	</script>
</c:if>
