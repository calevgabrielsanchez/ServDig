<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../general/taglibs.jsp"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/tramite/consultaVigencia/capturaDocumentos.js" htmlEscape="true" />"></script>

<script type="text/javascript">
  
	history.go(1);
	tipoTramite = '${tramite}';

	$(document).ready(function() {
		$("#regresarPantallaInicial").click(redireccionPaginaPrincipal);
		
		$("#rfcPat").prop('disabled', true);
		$("#curpBen").prop('disabled', true);
		$("#umfAdsc").prop('disabled', true);
		$("#validarDatos").prop('disabled', true);
		
		$('#curpBen').bind("blur", function(e) {
			$("#erroresCaptura").hide();
		});
		/*
		 * Funcion que convierte en MAYUSCULAS el valor 
		 * de campo CURP al perder el foco 
		 */
		$('input[type="text"]#curpBen').blur(function() {
			this.value = this.value.toUpperCase();
			if (!curpValida(this.value)) {
				$("#erroresCaptura").html('El formato de la CURP no es v&aacute;lido, verif&iacute;quelo.').show();	
				$('#correoConfirmacionInput').val("");
				e.preventDefault();
			} else if ($("#curpAsegurado").val().toUpperCase() == this.value) {
				$("#erroresCaptura").html('La CURP de su beneficiario legal registrado no puede ser la misma que la del asegurado.').show();	
				$('#correoConfirmacionInput').val("");
				e.preventDefault();
			}
		});
		
		function clearInputs() {
			$("#curpBen").val("");
			$("#umfAdsc").val(0).change();
			$("#rfcPat").val("");
		}
		
		$('#curpBeneficiario').click(function () {
			   $('#curpBeneficiario').prop('checked', true);
			   $('#umfAdscripcion').prop('checked', false);
			   $('#rfcPatronal').prop('checked', false);
			   $('#infDesconocida').prop('checked', false);
			   $("#curpBen").prop('disabled', false);
			   $("#umfAdsc").prop('disabled', true);
			   $("#rfcPat").prop('disabled', true);
			   $("#validarDatos").prop('disabled', false);
			   $("#validarDatos").prop('class', 'btn btn-primary');
			   clearInputs();
			 });
		
		$('#umfAdscripcion').click(function () {
			   $('#curpBeneficiario').prop('checked', false);
			   $('#umfAdscripcion').prop('checked', true);
			   $('#rfcPatronal').prop('checked', false);
			   $('#infDesconocida').prop('checked', false);
			   $("#curpBen").prop('disabled', true);
			   $("#umfAdsc").prop('disabled', false);
			   $("#rfcPat").prop('disabled', true);
			   $("#validarDatos").prop('disabled', false);
			   $("#validarDatos").prop('class', 'btn btn-primary');
			   clearInputs();
			 });
		
		$('#rfcPatronal').click(function () {
			   $('#curpBeneficiario').prop('checked', false);
			   $('#umfAdscripcion').prop('checked', false);
			   $('#rfcPatronal').prop('checked', true);
			   $('#infDesconocida').prop('checked', false);
			   $("#curpBen").prop('disabled', true);
			   $("#umfAdsc").prop('disabled', true);
			   $("#rfcPat").prop('disabled', false);
			   $("#validarDatos").prop('disabled', false);
			   $("#validarDatos").prop('class', 'btn btn-primary');
			   clearInputs();
			 });
		
		$('#infDesconocida').click(function () {
			   $('#curpBeneficiario').prop('checked', false);
			   $('#umfAdscripcion').prop('checked', false);
			   $('#rfcPatronal').prop('checked', false);
			   $('#infDesconocida').prop('checked', true);
			   $("#curpBen").prop('disabled', true);
			   $("#umfAdsc").prop('disabled', true);
			   $("#rfcPat").prop('disabled', true);
			   $("#validarDatos").prop('disabled', false);
			   $("#validarDatos").prop('class', 'btn btn-primary');
			   clearInputs();
			 });
	});
	
	//Función para validar una CURP
	function curpValida(curp) {
		
		if (curp == "") return true;
		
	    var re = /^([A-Z][AEIOUX][A-Z]{2}\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[12]\d|3[01])[HM](?:AS|B[CS]|C[CLMSH]|D[FG]|G[TR]|HG|JC|M[CNS]|N[ETL]|OC|PL|Q[TR]|S[PLR]|T[CSL]|VZ|YN|ZS)[B-DF-HJ-NP-TV-Z]{3}[A-Z\d])(\d)$/,
	        validado = curp.match(re);
		
	    if (!validado)  //Coincide con el formato general?
	    	return false;
	    
	    //Validar que coincida el digito verificador
	    function digitoVerificador(curp17) {
	        //Fuente https://consultas.curp.gob.mx/CurpSP/
	        var diccionario  = "0123456789ABCDEFGHIJKLMNÑOPQRSTUVWXYZ",
	            lngSuma      = 0.0,
	            lngDigito    = 0.0;
	        for(var i=0; i<17; i++)
	            lngSuma = lngSuma + diccionario.indexOf(curp17.charAt(i)) * (18 - i);
	        lngDigito = 10 - lngSuma % 10;
	        if (lngDigito == 10) return 0;
	        return lngDigito;
	    }
	  
	    if (validado[2] != digitoVerificador(validado[1])) 
	    	return false;
	        
	    return true; //Validado
	}

	function redireccionPaginaPrincipal() {
		if (tipoTramite == 'registro' || tipoTramite == 'registroD') {
			location.href = "/portal-ciudadano-web-externo/derechohabientes/tramite/registro";
		} else if (tipoTramite == 'cambioClinica') {
			location.href = "/portal-ciudadano-web-externo/derechohabientes/tramite/cambioClinica";
		} else {
			location.href = "/portal-ciudadano-web-externo/home/testTramites";
		}
	}
</script>

<input type="hidden" id="curpAsegurado" value="${fisica.curp}" />
<input type="hidden" id="solicitudPendiente" value="${solicitudPendiente}" />
<input type="hidden" id="opcionUMF1" value="${opcionUMF1}" />
<input type="hidden" id="opcionUMF2" value="${opcionUMF2}" />
<input type="hidden" id="opcionUMF3" value="${opcionUMF3}" />
<input type="hidden" id="opcionUMF4" value="${opcionUMF4}" />
<input type="hidden" id="opcionUMF5" value="${opcionUMF5}" />
<input type="hidden" id="opcionUMF6" value="${opcionUMF6}" />
<input type="hidden" id="opcionUMF7" value="${opcionUMF7}" />
<input type="hidden" id="opcionUMF8" value="${opcionUMF8}" />
<input type="hidden" id="opcionUMF9" value="${opcionUMF9}" />
<input type="hidden" id="opcionUMF10" value="${opcionUMF10}" />

<input type="hidden" id="bloqueoFormulario" value="${bloqueoFormulario}" />
<input type="hidden" id="seleccionarSubdelegacion" value="${seleccionarSubdelegacion}" />

<div class="contenedor">

	<!-- Muesta los mensajes de error, este error es cuando encontro mas de un error -->
	<c:if test="${errorDatosExistentes != null }">
		<div style="width: 500px;" align="center">
			<div class="ui-widget">
				<div style="margin-top: 20px; padding: 0 .7em;" class="ui-state-highlight ui-corner-all">
					<p>
						<span style="float: left; margin-right: .3em;" class="ui-icon ui-icon-info"></span>
						${mensaje}
					</p>
				</div>
			</div>
		</div>
	</c:if>
	
	<jsp:include page="encabezado.jsp">
		<jsp:param name="paso" value="1" />
	</jsp:include>
	
	<div class="alert alert-danger" style="display:none" id="erroresCaptura"></div>
	
		<div>
			<spring:message code="label.tramite.instrucciones.documentacion"/>:<br>
			<ul>
				<li><spring:message code="label.tramite.instrucciones.carga"/>
					<ul>
					 	<li><spring:message code="label.tramite.instrucciones.carga.opciones1"/></li>
					 	<li><spring:message code="label.tramite.instrucciones.carga.opciones2"/></li>
					</ul>
				</li>
			</ul>
		</div>
		
<!--  		<form action="procesarArchivo.jsp" method="post" enctype="multipart/form-data">
		    <input id="uploadfile" type="file" name="uploadfile" accept=".pdf,.jpg">
		</form> -->

		<br/>

		<div><spring:message code="label.tramite.instrucciones.documentacion.selec_una_opcion"/></div>
		
		<div class="text-align:center">
		
  		<form:form modelAttribute="opciones" id="capturaDocumentosForm"enctype="multipart/form-data">
 		
 		<br />		
 		
 		<input id="uploadfile" type="file" name="uploadfile" accept=".pdf,.jpg">
 			
				
		<table id="capturaDocs">	
	
			<tr>
				<td>&nbsp;</td>
				<td>
					<input type="radio" name="opcion" id="curpBeneficiario" value=1 class="radioVentanilla"/>
					<spring:message code="mensaje.radio.curp.beneficiario.legal"/>
				</td>
				<td>&nbsp;</td>
				<td>
					<input id="curpBen" name="curpBen" class="alfanumerico_espacios" 
					type="text" value="" maxlength="18"> 
				</td>
			</tr>
			

			<tr>
				<td>&nbsp;</td>
				<td>
					<input type="radio" name="opcion" id="umfAdscripcion" value=2 class="radioUMF"/>
					<spring:message code="mensaje.radio.seleccione.unidad.adscripcion"/>
				</td>
				<td>&nbsp;</td>
			    <td>
			        <select id="umfAdsc" name="umfAdsc"
						class="alfanumerico_espacios"
						style="width: 250px; height: 30px; font-size: 14px;">
					</select>
					</td>
			</tr>
			
	
			<tr>
				<td>&nbsp;</td>
				<td>
					<input type="radio" name="opcion" id="rfcPatronal" value=3 class="radioRFC"/>
					<spring:message code="mensaje.radio.registro_patronal.rfc.empresa"></spring:message>
				</td>
				<td>&nbsp;</td>
				<td>
					<input id="rfcPat" name="rfcPat" class="alfanumerico_espacios" type="text" value=""> 
				</td>
			</tr>
			
			
			<tr>
				<td>&nbsp;</td>
				<td>
					<input type="radio" name="opcion" id="infDesconocida" value=4 class="radioInfoDesconocida"/>
					<spring:message code="mensaje.radio.desconozco.opcion"></spring:message>
				</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>			

		</table>
		
 		</form:form>
 		
 		</div>
 		
 		<br />
 	
		<div style="text-align: center">
			<button type="button" id="btnRegresar" class="btn btn-secondary" role="button" onclick="window.location.href = '${contextpath}/vigencia'">
				<spring:message code="label.btn.regresar" />
			</button>
			<button type="button" id="validarDatos" class="btn btn-primary">
				<spring:message code="label.btn.finalizar.tramite"></spring:message>
			</button>
		</div>
				
				<!-- Datos del domicilio -->
	<c:if test="${seleccionarSubdelegacion}">
		<br>
		<br>
		
		<div>
			<h3>Ingrese su c&oacute;digo postal y seleccione la subdelegaci&oacute;n para finalizar su tr&aacute;mite</h3>
		    <form id="busquedaCP" action="#" class="form-horizontal" role="form" method="post">
		        <table class="page_holder_no_height" style="width: 95%">
		            <tr>
		                <td style="width: 20%"><strong>C&oacute;digo Postal<span class="required labelObligatorio">*</span>:</strong></td>
		                <td><input id="codigoPostal" name="codigoPostal" maxlength="5" value="" type="text" style="width: 50%"></td>
		                <td>
		                    <span id="domicilio.codigoPostal.codigoPostalError" class="error hiddenElement text-left"></span>
		                    <span id="errorNegocioLabel" class="error hiddenElement"></span>
		                </td>
		                <td colspan="2" style="width: 50%">
		                    <div class="divOcultoRecortado">
		                        <button class="mboton" type="button" id="busquedaCpBtn">
		                            <span>Aceptar</span>
		                        </button>
		                        <button class="mboton" type="button" id="limpiarForm">
		                            <span>Limpiar</span>
		                        </button>
		                    </div>
		                </td>
		            </tr>
		       	</table>
		    </form>
		</div>

		<br>

		<div>
		    <form id="datosDomicilioRecortadoForm" action="${contextpath}/vigencia/finalizarTramite" class="form-horizontal" role="form" method="post">
		        <table class="page_holder_no_height" style="width: 95%">
		            <tr>
		                <td style="width: 80%"><strong>Subdelegaci&oacute;n:</strong></td>
		            </tr>
		            <tr>
		                <td>
		                    <select id="subdelegacion" name="subdelegacion" class="alfanumerico_espacios" style="width: 250px; height: 30px; font-size: 14px;"></select>
		                </td>
		            </tr>
		            <tr>
		                <td>
		                    <button class="btn btn-primary" type="button" id="finalizarBtn">
		                        <span>Finalizar</span>
		                    </button>
		                </td>
		            </tr>
		        </table>
		    </form>
		</div>

	
	</c:if>

		
		<div class="alert alert-info" style="margin-top: 20px">
				<p><strong>Aviso de privacidad simplificado</strong></p>
				La recolecci&oacute;n de datos personales se lleva a cabo a trav&eacute;s de la p&aacute;gina 
				electr&oacute;nica <a href="${mvn.avisos.contexto}/gestionAsegurados-web-externo/vigencia"
				target="_blank">${mvn.avisos.contexto}/gestionAsegurados-web-externo/vigencia</a> cuyo administrador y responsable del tratamiento 
				es la Coordinaci&oacute;n de Clasificaci&oacute;n de Empresas y Vigencia de Derechos del Instituto Mexicano del Seguro Social. 
				Los datos personales que se recaban ser&aacute;n utilizados con la finalidad de generar y obtener la Constancia de 
				vigencia de derechos para recibir servicio m&eacute;dico ante el IMSS con Homoclave IMSS-02-020-B. 
 				Si deseas conocer nuestro aviso de privacidad integral, lo podr&aacute;s consultar en el portal: 
				<a href="${mvn.avisos.contexto}/gestionAsegurados-web-externo/avisoVigencia.jsp"
				target="_blank">${mvn.avisos.contexto}/gestionAsegurados-web-externo/avisoVigencia.jsp</a>
		</div>
		
		<%-- 
		<jsp:include page="../common/pieTramites.jsp">
			<jsp:param name="tipoTramite" value="true" />
		</jsp:include>
		--%>
</div>

<script language="JavaScript1.2" src="${staticResourcesPath}/js/comscore/Form.js"></script>
