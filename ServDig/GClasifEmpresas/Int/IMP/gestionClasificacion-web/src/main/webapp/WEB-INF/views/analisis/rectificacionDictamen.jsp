<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<%@ include file="../general/taglibs.jsp"%>

<%-- <script type="text/javascript" src="${staticResourcesPath}/js/clasificador/clasificacion.js"></script> --%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/general/clasificador.js" htmlEscape="true" />"></script>

<script>
	String.prototype.trim = function(){ return this.replace(/^\s+|\s+$/g,'') }
	var errActDetectada;
	var errComentario;
	
	function asignaValores(){
		fraccionActual=document.getElementById("cveIdFraccionAct").value;
		fraccionPropuesta=document.getElementById("cveFraccionId").innerHTML;
		patronDictamen=document.getElementById("cveIdPatronDictamen").value;

		console.log("Asignando propuesta, fraccionActual:: " + fraccionActual
				+ ", fraccionPropuesta:: " + fraccionPropuesta + ", patronDictamen: " + patronDictamen);

/* 		alert("Asignando propuesta, fraccionActual:: " + fraccionActual
				+ ", fraccionPropuesta:: " + fraccionPropuesta + ", patronDictamen: " + patronDictamen);
 */
		//Obtener referencias, se sacan del if validaVacios() y se agrega un err nuevo
		 errActDetectada=document.getElementById('errActDetectada');
		 errComentario=document.getElementById('errComentario');
		 errDocumento=document.getElementById('errDocumento');	//Nuevo error para documento
		 
		 // Limpiar errores, se sacan del if validaVacios() y se agrega un nuevo limpiador
		 errActDetectada.style.display='none';
		 errComentario.style.display='none';
		 errDocumento.style.display='none';	//limpia el display del documento
		 
		 // Validar si hay archivo seleccionado
		 var fileUpload = document.getElementById('fileUpload');
		 if (fileUpload.files.length === 0) {
			errDocumento.textContent = 'Documento obligatorio';
		    errDocumento.style.display='block';
		    return false;
		 }
		 
		 if(validaVacios()){
			errActDetectada=document.getElementById('errActDetectada');
			errComentario=document.getElementById('errComentario');
			errActDetectada.style.display='none';
			errComentario.style.display='none';
			document.forms['rectificacionMovimientoDictamen'].clase.value=document.getElementById("cveClaseId").innerHTML;
			document.forms['rectificacionMovimientoDictamen'].cveIdFraccion.value=document.getElementById("cveFraccionId").innerHTML;
			document.forms['rectificacionMovimientoDictamen'].cveIdDivision.value=document.getElementById("cveDivisionId").innerHTML;
			document.forms['rectificacionMovimientoDictamen'].cveIdGrupo.value=document.getElementById("cveGrupoId").innerHTML;
			document.forms['rectificacionMovimientoDictamen'].primaSRTPro.value=document.getElementById("cveprimaDes").innerHTML;
			$.blockUI();
		    document.getElementById('rectificacionMovimientoDictamen').submit();
		}
	}

	function validaVacios(){
		var bool=false;
		actividadDetectada=document.getElementById('actividadDetectada');
		comentarios=document.getElementById('comentarios');
		errActDetectada=document.getElementById('errActDetectada');
		errComentario=document.getElementById('errComentario');

		errActDetectada.style.display='none';
		errComentario.style.display='none';

		actividadDetectada2=document.getElementById('actividadDetectada').value;
		comentarios2=document.getElementById('comentarios').value;
		
		if(actividadDetectada.value==null || actividadDetectada2.trim()==""){
			errActDetectada.value="Campo Obligatorio";
			errActDetectada.style.display='block';
			bool=false;
		}else if(comentarios.value==null || comentarios2.trim()==""){
			errComentario.value="Campo Obligatorio";
			errComentario.style.display='block';
			bool=false;
		}else{
			
			if(actividadDetectada2.trim().length > 255){
				errComentario.value="El campo Actividad Detectada excede a la longitud permitida de 255 caracteres";
				errComentario.style.display='block';
				bool=false;				
			}else if(comentarios2.trim().length > 2500){
				errComentario.value="El campo de Comentarios excede a la longitud permitida de 2500 caracteres";
				errComentario.style.display='block';
				bool=false;				
			}else{
				bool=true;
			}
		}
		return bool;
		
	}

	$(document).ready(function(){
		limitarCampo('comentarios', 2500);
	});

	/*Valida Caracteres*/
	var campoGiroNegado = /[^\sa-zA-Z\d\u00F1\u00E1\u00E9\u00ED\u00F3\u00FA\u00D1\u00C1\u00C9\u00CD\u00D3\u00DA\#\%\(\)\,\-\.\/\?\@\*\']/g;
	
	function validaCaracteresKeyUp(event){
		event.target.value = event.target.value.replace(campoGiroNegado, "");
	}
</script>
<!-- JS de la pagina -->

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/general/general.js" htmlEscape="true" />"></script>
<c:set var="contextpath" value="<%=request.getContextPath()%>"/>
<c:set var="cveIdDelegacion" 		value="${cveIdDelegacion}"/>
<c:set var="cveIdSubdelegacion" 	value="${cveIdSubdelegacion}"/>

<form id="clasificacionForm">
	<input type="hidden" id="idDivision" value="vacio">
	<input type="hidden" id="idGrupo" value="vacio">
	<input type="hidden" id="idFraccion" value="vacio">
	<input type="hidden" id="clase" value="vacio">
	<input type="hidden" id="descActividadEconomicaDetectada" value="vacio">
</form>

<form id="formClasificacionSolicitud"><!-- Hiddens para el control y manejo de la clasificacion -->
	<input type="hidden" id="idDivisionb" name="idDivisionb" value="" />
	<input type="hidden" id="idGrupob" name="idGrupob" value="" />
	<input type="hidden" id="idFraccionb" name="idFraccionb" value="" />
	<input type="hidden" id="claseb" name="claseb" value="" />
	<input type="hidden" id="cveIdClasificacionb" name="cveIdClasificacionb" value="" />
</form>

<div align="center">
	<table style="width: 100%">	
		<thead>
			<tr valign="top" class="par">
				<td align="center" style="padding-bottom: .5em; padding-top: .5em;">
						<div class="separadorseccion">Rectificaci&oacute;n de la clasificaci&oacute;n de las empresas en el seguro de riesgos de trabajo</div>
				</td>
			</tr>
		</thead>
		
		<tr><td colspan="1"><center><div id="msgAuxiliar" style="display:'none'; color:#8B0000;"></div></center></td></tr>
		
		<tr>
			<td align="center" style="padding-bottom: 10px;">
				<div id="wrapperIntsAnterior" class="ui-widget" style="width: 700px !important;">
				<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .5em;" align="center">
					<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
					<strong>Seleccione la nueva clasificaci&oacute;n conforme al cat&aacute;logo para la clasificaci&oacute;n de las empresas en el Seguro de Riesgo de Trabajo:</strong> 
					<strong><a href="javascript:fnOpenClasificador();"> Aqu&iacute;</a></strong>
					</p>
				</div>
				</div>
			</td>
		</tr>		
	</table>
	<div id="dgEliminarProductos"></div>
	<form:form id="rectificacionMovimientoDictamen" name="rectificacionMovimientoDictamen" 
        action="${contextpath}/rectificacion/${cveIdAnalisis}/rectificadoPendiente/dictamen" method="post" enctype="multipart/form-data">

		<input type="hidden" id="idTipoPersona" name="idTipoPersona" value="${tipoPersona}"/>
		<input type="hidden" id="regPatron" name="regPatron" value="${regPatronal}"/>
		<input type="hidden" id="indRegPatClase" name="indRegPatClase" value=""/>
		<input type="hidden" id="cveIdDivision" name="cveIdDivision" value=""/>
		<input type="hidden" id="cveIdGrupo" name="cveIdGrupo" value=""/>
		<input type="hidden" id="cveIdFraccion" name="cveIdFraccion" value=""/>
		<input type="hidden" id="clase" name="clase" value=""/>
		<input type="hidden" id="cveIdDelegacion" name="cveIdDelegacion" value="${cveIdDelegacion}"/>
		<input type="hidden" id="cveIdSubdelegacion" name="cveIdSubdelegacion" value="${cveIdSubdelegacion}"/>
		<!--  -->
		<input type="hidden" id="cveIdFraccionAct" name="cveIdFraccionAct" value="${cveIdFraccionAct}"/>
		<input type="hidden" id="cveIdFraccionAnt" name="cveIdFraccionAnt" value="${cveIdFraccionAnt}"/>
		<input type="hidden" id="primaSRTAct" name="primaSRTAct" value="${primaSRTAct}"/>
		<!-- primaSRTPro se llena  al hacer submit -->
		<input type="hidden" id="primaSRTPro" name="primaSRTPro"/>
		<input type="hidden" id="primaSRTAnt" name="primaSRTAnt" value="${primaSRTAnt}"/>
		<input type="hidden" id="cveIdPatronDictamen" name="cveIdPatronDictamen" value="${cveIdPatronDictamen}"/>
		<input type="hidden" id="justificacion" name="justificacion" value="${justificacion}"/>
		<input type="hidden" id="omision" name="omision" value="${omision}"/>
		<input type="hidden" id="pago" name="pago" value="${pago}"/>
		<input type="hidden" id="fechaSurteEfecto" name="fechaSurteEfecto" value="${fechaSurteEfecto}"/>
		
		<table style="width: 85%" cellpadding="0px;">
			<tr class="fielsetgris2">
				<td style="display: none;"><span>ids</span></td>
				<td valign="top"><span class="etiqueta"> Divisi&oacute;n:</span> <span id="idDivisionError" class="error hiddenElement"> </span></td>
				<td valign="top"><span class="etiqueta"> Grupo:</span><span id="idGrupoError" class="error hiddenElement"> </span></td>
				<td valign="top"><span class="etiqueta"> Fracci&oacute;n:</span><span id="idFraccionError" class="error hiddenElement"> </span></td>
				<td valign="top"><span class="etiqueta"> Clase:</span></td>
				<td valign="top"><span class="etiqueta"> Prima SRT:</span></td>	
			</tr>
			<tr>
				<td style="display: none;">
				<span class="dato" id="cveFraccionId">${clasificacion.fraccion.id}</span>
				<span class="dato" id="cveDivisionId">${clasificacion.fraccion.grupo.division.id}</span>
				<span class="dato" id="cveGrupoId">${clasificacion.fraccion.grupo.id}
				</span>
				<span class="dato" id="cveClaseId">${clasificacion.fraccion.clase.clave}</span>
				</td>
				<td valign="top"><span class="dato" id="cvedivisionDes">
					${clasificacion.fraccion.grupo.division.descripcion}
				</span></td>
				<td valign="top"><span class="dato" id="cvegrupoDes">
					${clasificacion.fraccion.grupo.descripcion}
				</span></td>
				<td valign="top"><span class="dato" id="cvefraccionDes">
					${clasificacion.fraccion.descripcion}
				</span></td>
				<td valign="top"><span class="dato" id="cveclaseDes">
					${clasificacion.fraccion.clase.descripcion}
				</span></td>
				<td valign="top"><span class="dato" id="cveprimaDes">
					${clasificacion.fraccion.primaSRT}
				</span></td>
			</tr>
			<tr><td colspan="5">&nbsp;</td></tr>		
		</table>		

		<table>
			<tr>
				<td>
					<label id="errActDetectada" class="wide" style="color:red; display: none;">Dato obligatorio</label>
					<label class="wide">Actividad detectada:&nbsp;&nbsp;</label>
				</td>
				<td colspan="1" align="left">
					<input name="actividadDetectada" id="actividadDetectada" type="text" maxlength="200" style="border:1px !important;width: 546px !important;height: 20px !important;background-color:rgb(255, 255, 255) !important;border-color: rgb(211, 211, 211) !important;border-left-style:solid !important;border-right-style:solid !important;border-bottom-style:solid !important;border-top-style:solid !important;border-left-width:0.5pt !important;border-right-width:0.5pt !important;border-top-width:0.5pt !important;border-bottom-width:0.5pt !important;text-transform:uppercase !important;" onkeyup="validaCaracteresKeyUp(event);" onblur="validaCaracteresKeyUp(event)"/>
				</td>
			</tr>
			<!-- 
			<tr><td colspan="2">&nbsp;< f orm : e rrors path="comentario.descripcion" cssClass="error">< / fo rm : e rrors></td></tr>
			 -->
			<tr>
				<td>
			 		<label id="errComentario" class="wide" style="color:red; display: none;">Dato obligatorio</label>
			 		<label class="mwide">Comentarios:&nbsp;&nbsp;</label>
			 	</td>
			 			
			 	<td><textarea id="comentarios" name="comentarios" cols="90" rows="4" style="text-transform: uppercase;" maxlength="2500"></textarea></td>
			</tr>

			<tr><td colspan="2">&nbsp;</td></tr>

			<!--Agregado para seleccionar documentos-->
			<tr>
				<td colspan="2" style="text-align: center; padding: 20px 0;">
					<h2 style="margin: 0 0 20px 0; color: #000;">Adjuntar Documentos</h2>
					<table style="width: 100%; margin: 0 auto; text-align: center;">
						<tr>
							<td colspan="2" style="text-align: center; padding-right: 10px;">
								<label style="font-weight: bold;">Documento:</label>
							<!--</td>
							<td style="text-align: center;">-->
								<input type="text" id="documentoEjemplo" readonly 
								style="border: 1px !important; width: 245px !important; height: 25px !important;" /> 
								<!--background-color: rgb(255, 255, 255) !important; border-color: rgb(211, 211, 211) !important; 
								border-left-style: solid !important; border-right-style: solid !important; 
								border-bottom-style: solid !important; border-top-style: solid !important; 
								border-left-width: 0.5pt !important; border-right-width: 0.5pt !important; 
								border-top-width: 0.5pt !important; border-bottom-width: 0.5pt !important;" />-->
							</td>
						</tr>

						<tr style="height: 20px;">
							<td colspan="2"></td>
						</tr>
						
						<!-- Etiqueta de error para documentos -->
						<tr>
							<td colspan="2" style="text-align: center; padding: 5px 0;">
						    	<label id="errDocumento" class="wide" style="color:red; display: none;">Tipo de documento incorrecto</label>
							</td>
						</tr>

						<tr>
							<td colspan="2" style="text-align: center; padding-right: 10px 0;">
								<label style="font-weight: bold;">Documento a Adjuntar:</label>
							</td>
						</tr>
						<tr>
							<td colspan="2" style="text-align: center; padding: 10px 0;">
								<input type="file" id="fileUpload" name="docRect" accept=".docx" style="display: none;">
								<button type="button" id="btnSeleccionarArchivo" 
								style="padding: 2px 8px;
								border: 1px solid #767676;
								background-color: #f0f0f0; 
								color: #000;
								font-family: Arial, Helvetica, sans-serif;
								font-size: 13px;
								font-weight: normal;
								cursor: pointer;">Seleccionar archivo</button>
								<span id="nombreArchivoSeleccionado" style="margin-left: 10px; color: #999;">Ning&uacute;n archivo seleccionado</span>
							</td>
						</tr>
					</table>
				</td>
			</tr>

			<script>
			//Colocar el nombre de ejemplo que debe tener el archivo
			var registroPatronalTratado = document.getElementById('regPatron').value;
			var ejemploDocumentoNombre = "Hoja_Irregularidades_CE_" + registroPatronalTratado + ".docx";
			document.getElementById('documentoEjemplo').value = ejemploDocumentoNombre;
			
			// Al hacer clic en el boton, abre el explorador de archivos
			document.getElementById('btnSeleccionarArchivo').addEventListener('click', function () {
				document.getElementById('fileUpload').click();
			});

			// Al seleccionar un archivo, valida que sea .docx y si si actualiza el texto
			document.getElementById('fileUpload').addEventListener('change', function (event) {
				var fileNameSpan = document.getElementById('nombreArchivoSeleccionado');
				var btnSeleccionar = document.getElementById('btnSeleccionarArchivo');
				var errDocumento = document.getElementById('errDocumento');

				//Cambiar si hay archivo seleccionado
				if (this.files.length > 0) {
        			var file = this.files[0];
        			var fileName = file.name;
        			var fileExtension = fileName.substring(fileName.lastIndexOf('.')).toLowerCase();
        			var fileSizeMB = file.size / (1024 * 1024);
        
        			// Si no es .docx, mostrar error de tipo incorrecto
        			if (fileExtension !== '.docx') {
        				mostrarErrorDocumento('Tipo de documento incorrecto', 'Archivo no válido')
			            return;
			        } 
        			// valida el nombre correcto
        	        if (fileName !== ejemploDocumentoNombre) {
        	            mostrarErrorDocumento('Nombre de archivo incorrecto', 'Nombre no válido');
        	            return;
        	        }
        	        //Tamanio maximo 4 MB
        	        if (fileSizeMB > 4) {
        	            mostrarErrorDocumento('El archivo debe tener un tamaño menor a 4 MB. Tamaño actual: ' + fileSizeMB.toFixed(2) + ' MB', 'Archivo mayor a 4 MB');
        	            return;
        	        }
        			else{
			        	// Si es .docx, ocultar error y procesar
				        errDocumento.style.display = 'none';
				        
				        // Actualizar el span con el nombre del archivo
				        fileNameSpan.textContent = fileName;
				        fileNameSpan.style.color = '#333';
				
				        // Deshabilitar el boton
				        btnSeleccionar.disabled = true;
				        btnSeleccionar.style.opacity = '0.5';
				        btnSeleccionar.style.cursor = 'not-allowed';
			        }
			    } else {
			        // Si no hay archivo, limpiar todo
			        errDocumento.style.display = 'none';
			        fileNameSpan.textContent = 'Ningún archivo seleccionado';
			        fileNameSpan.style.color = '#999';

			        // Habilitar el boton
			        btnSeleccionar.disabled = false;
			        btnSeleccionar.style.opacity = '1';
			        btnSeleccionar.style.cursor = 'pointer';
			    }
				
				// Funcion para mostrar error
				function mostrarErrorDocumento(mensajeError, textoSpan) {
				    var errDocumento = document.getElementById('errDocumento');
				    var fileNameSpan = document.getElementById('nombreArchivoSeleccionado');
				    var btnSeleccionar = document.getElementById('btnSeleccionarArchivo');
				    
				    errDocumento.textContent = mensajeError;
				    errDocumento.style.display = 'block';
				    fileNameSpan.textContent = textoSpan;
				    fileNameSpan.style.color = '#d00';
				    btnSeleccionar.disabled = false;
				    btnSeleccionar.style.opacity = '1';
				    btnSeleccionar.style.cursor = 'pointer';
				    document.getElementById('fileUpload').value = '';
				}
			});
		</script>
		<!--Agregado para seleccionar documentos-->
			
			<tr>
				<td colspan="2" align="right">
					<input type="button" id="rectificacionAceptar" name ="rectificacionAceptar" class="mboton" style="width:120px;" value="Aceptar" onclick="asignaValores()"/>
					<!--input type="button" class="mboton" style="width: 120px;" id="btnRegresar" name="btnRegresar" value="Regresar" /-->
				</td>
			</tr>
			<tr><td colspan="2">&nbsp;</td></tr>
		</table>
	</form:form>
</div>
<c:if var="popUp" test="${popUp==1}">
	<script>
		$.blockUI();
		fnOpenClasificador();
		$.unblockUI();
	</script>
</c:if>