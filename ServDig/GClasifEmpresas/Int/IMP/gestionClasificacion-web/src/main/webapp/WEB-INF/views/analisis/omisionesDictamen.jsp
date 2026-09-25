<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<%@ include file="../general/taglibs.jsp"%>

<%-- <script type="text/javascript" src="${staticResourcesPath}/js/clasificador/clasificacion.js"></script> --%>


<c:set var="contextpath" value="<%=request.getContextPath()%>"/>
<c:set var="cveIdDelegacion" 		value="${cveIdDelegacion}"/>
<c:set var="cveIdSubdelegacion" 	value="${cveIdSubdelegacion}"/>


<!-- JS de la pagina -->


<script>
/*Valida Caracteres*/
var campoGiroNegado = /[^\sa-zA-Z\d\u00F1\u00E1\u00E9\u00ED\u00F3\u00FA\u00D1\u00C1\u00C9\u00CD\u00D3\u00DA\#\%\(\)\,\-\.\/\?\@\*\']/g;
var omision;
var omision2;
var pago;
var fecSurteEfecto;
var fecSurteEfecto2;
var justificacion;


	function validaCaracteresKeyUp(event){
		event.target.value = event.target.value.replace(campoGiroNegado, "");
	}


	function mostrarOpciones(bool, opcion) {

		if (bool) {
			if (opcion == 1) {
				document.getElementById("fechaSurteEfecto").style.visibility = "visible";
				document.getElementById("noPresento").style.visibility = "hidden";
								
			} else if (opcion == 2) {
				document.getElementById("fechaSurteEfecto").style.visibility = "hidden";
				document.getElementById("noPresento").style.visibility = "visible";
			}
		} else {
			document.getElementById("fechaSurteEfecto").style.visibility = "hidden";
			document.getElementById("noPresento").style.visibility = "hidden";
		}

	}

	function mostrarMensaje(bool) {
		if (bool) {
			document.getElementById("msjSinPagos").style.visibility = "visible";
		} else {
			document.getElementById("msjSinPagos").style.visibility = "hidden";
		}
	}
	function rectificarDictamen() {

		if(validarVacios()){

			var omisionSeleccionada = document.querySelector('input[name="omisiones"]:checked');
			var justificacion = document.getElementById('justificacion').value.trim();
			
			var omision = omisionSeleccionada.value;
			
			document.forms['rectificaFormDictamen'].justificacion.value =  document.getElementById('justificacion').value.trim();
			document.forms['rectificaFormDictamen'].omision.value = document.querySelector('input[name="omisiones"]:checked').value;
			
			 if (omision === "1" || omision === "4"){

				 document.forms['rectificaFormDictamen'].pago.value =  "";
				 document.forms['rectificaFormDictamen'].fechaSurteEfecto.value = "";
			 
			 }
			 if (omision === "2"){

				 document.forms['rectificaFormDictamen'].pago.value =  "";
				 document.forms['rectificaFormDictamen'].fechaSurteEfecto.value = document.getElementById('fechaCorrecta').value.trim();
			 }
			 if (omision === "3"){

				 document.forms['rectificaFormDictamen'].pago.value =  document.querySelector('input[name="pago"]:checked').value;
				 document.forms['rectificaFormDictamen'].fechaSurteEfecto.value = "";
			 }
			
		document.getElementById("rectificaFormDictamen").submit();
		}
		
	}
	
	function validarVacios(){
						
		var omisionSeleccionada = document.querySelector('input[name="omisiones"]:checked');
	    var fechaCorrecta = document.getElementById('fechaCorrecta').value.trim();
	    var pagoSeleccionado = document.querySelector('input[name="pago"]:checked');
	    var justificacion = document.getElementById('justificacion').value.trim();
	    
	    // Validar que se haya seleccionado una omision
	    if (omisionSeleccionada === null || omisionSeleccionada === undefined) {
	        alert('Debe seleccionar una omision');
	        return false;
	    }
	    
	    // Obtener el valor de la omision
	    var omision = omisionSeleccionada.value;
	    
	    // Validar si la omision es 2 (Incorrecta fecha)
	    if (omision === "2") {
	        
	        
	        // Validar que la fecha no esta vacia para omision 2
	        if (fechaCorrecta === "" || fechaCorrecta === null) {
	            alert('Debe ingresar la fecha a partir de la cual surte efectos');
	            return false;
	        }
	        
	        // Validar formato de fecha (dd/MM/yyyy)
	        var regexFecha = /^(0[1-9]|[12][0-9]|3[01])\/(0[1-9]|1[012])\/\d{4}$/;
	        if (!regexFecha.test(fechaCorrecta)) {
	            alert('La fecha debe tener el formato dd/MM/aaaa');
	            bool = false;
	        }
	    }
	    
	    // Validar si la omision es 3 (No presento AM-SRT)
	    if (omision === "3") {
	        // Validar que se haya seleccionado una opcion de pago
	        if (pagoSeleccionado === null || pagoSeleccionado === undefined) {
	            alert('Debe seleccionar una opcion de pago (Con pago o Sin pago)');
	            return false;
	        }
	    }
	    
	    // Validar justificacion
	    if (justificacion === "" || justificacion === null) {
	        alert('Debe ingresar una justificacion');
	        return false;
	    }
	    
	    		
		return true;
	}
	
	$(document).ready(function(){
		$( "#fechaCorrecta" ).datepicker();
		$( "#fechaCorrecta" ).datepicker( "option", "dateFormat", 'dd/mm/yy' );
	});

</script>



<div align="center">
	<table style="width: 100%">
		<thead>
			<tr valign="top" class="par">
				<td align="center" style="padding-bottom: .5em; padding-top: .5em;">
					<div class="separadorseccion">Rectificaci&oacute;n de la
						clasificaci&oacute;n de las empresas en el seguro de riesgos de trabajo</div>
				</td>
			</tr>
		</thead>


	</table>
</div>
<div align="center">
	<form:form id="rectificacionMovimientoDictamen"
		name="rectificacionMovimientoDictamen"
		action="${contextpath}/rectificacion/${cveIdAnalisis}/rectificadoPendiente/dictamen"
		method="post">
		<table style="width: 45%" cellpadding="0px;">
		<tr>
		<th colspan="2"> Omisiones detectadas </th>
		</tr>
			<!--  Omision incorrecta clasificacion -->
			<tr>
				<td width="5%"><input type="radio" name="omisiones"
					 value="1"
					Onclick="mostrarOpciones(false,0);mostrarMensaje(false);"></td>
				<td>Incorrecta clasificaci&oacute;n.</td>
			</tr>
			<!--  Omision Incorrecta fecha -->
			<tr>
				<td width="5%"><input type="radio" name="omisiones"
					 value="2"
					Onclick="mostrarOpciones(true,1);mostrarMensaje(false);"></td>
				<td>Incorrecta fecha de inicio de vigencia de la nueva
					clasificaci&oacute;n.</td>
			</tr>
			<tr id="fechaSurteEfecto" style="visibility: hidden">
				<td width="5%"></td>
				<td>
					<table>
						<tr>
							<td width="55%">Fecha a partir de la cual surte efectos:</td>
							<td width="35%">
							<input name="strFechaSurteEfecto" id="fechaCorrecta" type="text" maxlength="10" value="01/01/${ejercicio}" title="dd/MM/aaaa" 
						style="border: 1px !important; width: 100px !important; height: 20px !important; 
						background-color: rgb(255, 255, 255) !important; border-color: rgb(211, 211, 211) !important; 
						border-left-style: solid !important; border-right-style: solid !important; 
						border-bottom-style: solid !important; border-top-style: solid !important; 
						border-left-width: 0.5pt !important; border-right-width: 0.5pt !important; 
						border-top-width: 0.5pt !important; border-bottom-width: 0.5pt !important; 
						text-transform: uppercase !important;" />
							<%-- <input type="date" id="fechaCorrecta" name="fechaCorrecta" value="${ejercicio}-01-01" > --%>

							</td>
						</tr>
					</table>
				</td>
			</tr>

			<!--  Omision no presentro AMSRT -->
			<tr>
				<td width="5%"><input type="radio" name="omisiones"
					 value="3"
					Onclick="mostrarOpciones(true,2);mostrarMensaje(false);"></td>
				<td>No present&oacute; AM-SRT.(Clasificaci&oacute;n e Inicio
					Vigencia Correctas).</td>
			</tr>

			<tr id="noPresento" style="visibility: hidden">
				<td width="5%"></td>
				<td>
					<table>
						<tr>
							<td width="5%"></td>
							<td width="20%"><input type="radio" name="pago"
								value="1" Onclick="mostrarMensaje(false);"> <label
								for="conPago"> Con pago</label></td>
							<td width="20%"><input type="radio" 
								name="pago" value="2" Onclick="mostrarMensaje(true);"> <label
								for="sinPago"> Sin pago</label></td>
							<td><label id="msjSinPagos"
								style="color: red; visibility: hidden">(Se deber&aacute;
									incluir en la Hoja de Irregularidades del Dictamen CE la falta
									de pago)</label></td>
					</table>
				</td>
			</tr>


			<!--  Omision Clasificacion distinta -->

			<tr>
				<td width="5%"><input type="radio" name="omisiones"
					 value="4"
					Onclick="mostrarOpciones(false,0);mostrarMensaje(false);"></td>


				<td>Clasificaci&oacute;n distinta entre IMSS (SINDO) Y SIDEIMSS</td>
			</tr>

			<tr>
				<td colspan="2">Justificaci&oacute;n*:</td>

			</tr>
			<tr>
			<td colspan="2" >
				<textarea id="justificacion" name="justificacion" cols="90" rows="4"  maxlength="2500" onkeyup="validaCaracteresKeyUp(event);" onblur="validaCaracteresKeyUp(event)"></textarea>
			</td>
			</tr>


			<tr>
				
			<td colspan="2" align="right"><input type="button"
					id="rectificacionAceptar" name="rectificacionAceptar"
					class="mboton" style="width: 120px;" value="Aceptar"
					onclick="rectificarDictamen()" />
			
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
		</table>
	</form:form>
</div>
<form id="rectificaFormDictamen"
					action="<%=request.getContextPath()%>/rectificacion/${cveIdAnalisis}/rectificarMovimiento/dictamen"
					method="post">
					<input type="hidden" id="regPatronal" name="regPatronal"
						value="${regPatronal}" /> <input type="hidden" id="rfc"
						name="rfc" value="${rfc}" /> <input type="hidden"
						id="popUp" name="popUp" value="1" /> <input type="hidden"
						id="cveIdDelegacion" name="cveIdDelegacion"
						value="${cveIdDelegacion}" /> <input type="hidden"
						id="cveIdSubdelegacion" name="cveIdSubdelegacion"
						value="${cveIdSubdelegacion}" /> <input type="hidden"
						id="cveIdFraccionAct" name="cveIdFraccionAct"
						value="${cveIdFraccionAct}" /> <input type="hidden"
						id="cveIdFraccionPro" name="cveIdFraccionPro"
						value="${cveIdFraccionPro}" /> <input
						type="hidden" id="cveIdFraccionAnt" name="cveIdFraccionAnt"
						value="${cveIdFraccionAnt}" /> <input
						type="hidden" id="primaSRTAct" name="primaSRTAct"
						value="${primaSRTAct}" /> <input
						type="hidden" id="primaSRTPro" name="primaSRTPro"
						value="${primaSRTPro}" /> <input
						type="hidden" id="primaSRTAnt" name="primaSRTAnt"
						value="${primaSRTAnt}" /> <input
						type="hidden" id="cveIdPatronDictamen" name="cveIdPatronDictamen"
						value="${cveIdPatronDictamen}" />
						<!-- justificacion, omision, pago, fechaSurteEfecto se llena  al hacer submit -->
						<input type="hidden" id="justificacion" name="justificacion"/>
		<input type="hidden" id="omision" name="omision"/>
		<input type="hidden" id="pago" name="pago"/>
		<input type="hidden" id="fechaSurteEfecto" name="fechaSurteEfecto"/>
				</form>

