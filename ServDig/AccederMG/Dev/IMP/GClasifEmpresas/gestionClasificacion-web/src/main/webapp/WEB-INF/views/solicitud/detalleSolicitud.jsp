<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<style type="text/css" media="screen">

textarea{
text-transform: uppercase;
}

label {
	display: inline;
}

.table_form table {
	margin: 15px auto;
}

.table_form table tr td {
	padding: 5px 10px;
}

textarea {
	height: 100%;
}

.row_selected {
	background-color: #E78F08 !important;
	color: #EFFAEF !important;
}

.row_selected td {
	background-color: #E78F08 !important;
	color: #EFFAEF !important;
}

input,textarea,.uneditable-input {
	width: auto;
}
table{max-width:100%;background-color:transparent;border-collapse:collapse;border-spacing:0;}
.table-word-wrap-fixed{word-wrap:break-word !important; table-layout: fixed;}


</style>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/clasificacion/detalle/detalleSolicitud.js" htmlEscape="true" />"></script>
		
<!--No  Borrar código google analytics -->

<script type="text/javascript">
$.blockUI();

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-4135554-1']); 
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

  function autorizarRectificacion(){
	  <c:if test="${analizandoDictamen}">
	  document.getElementById('clemForm').action="<%=request.getContextPath()%>/rectificacion/${analisis.cveIdAnalisis}/autorizarRectificacion/capturaDatosClem/${cveIdPatronDictamen}";
	  </c:if>
	  <c:if test="${!analizandoDictamen}">
	  document.getElementById('clemForm').action="<%=request.getContextPath()%>/rectificacion/${analisis.cveIdAnalisis}/autorizarRectificacion/capturaDatosClem/";
	  </c:if>
	  $.blockUI();
	  document.getElementById('clemForm').submit();
  }
  
  function modificarClem(){
	  $.blockUI();
	  document.getElementById('modificarClemForm').action="<%=request.getContextPath()%>/clem/${analisis.cveIdAnalisis}/modificarClem/capturaDatos";
	  document.getElementById('modificarClemForm').submit();
  }

  
</script>

<c:if test="${analizandoDictamen}">
<script>

	$(document).ready(function() {
		$("#verDictamen").click(mostrarDictamen);
	})

	function mostrarDictamen() {
		$("#formPatronDictamen").submit();
	}
</script>
</c:if>
<!--No  Borrar Código Google analytics -->
<script type="text/javascript">
	var analizandoDictamen = parseInt('${analizandoDictamen ? 1 : 0}',10);
	function rectificar(){
		document.getElementById("rectificaForm").submit();
	}

 	function desechar(){
		document.getElementById("desecharForm").submit();
	}
 
	$(document).ready(function(){

		$( "#tabs" ).tabs();
		if(!analizandoDictamen) {
			submitForm();
		}

		document.getElementById("cenefa").innerHtml = "Inicio» Detalle de la Solicitud";
		
		if($("#menuRepAnalisis").length) {
        	document.getElementById('menuRepAnalisis').style.display='none';
		}
		if($("#menuRepBitacora").length) {
        document.getElementById('menuRepBitacora').style.display='none';
		}
		if($("#menuRepConcentrado").length) {
        document.getElementById('menuRepConcentrado').style.display='none';
		}
		$.unblockUI();
		
		oDialogoErrorRectificacion = $('#dgError').dialog({
	     autoOpen:false,
	     resizable: false,
	     height:240,
	     width : 350,
	     modal: true,
	     buttons: {
	         "Aceptar": function(data) {
	         	 $( this ).dialog( "close" );
	         }
	    	}
		});
		
		if(document.getElementById('errorRectificacion').value != null
			&& document.getElementById('errorRectificacion').value != ''){
			oDialogoErrorRectificacion.dialog('open');
		}
	});
	
	function submitForm() {
		$("#forma").submit();
		$("#formaResultantes").submit();
 		document.getElementById("firmaIframe").style.display = "inline";
 		document.getElementById("firmaIframeResultantes").style.display = "inline";
 	}

</script>

<c:set var="muestraBienes" 				value="${muestraBienes}"/>
<c:set var="tramite"     				value="${tipoTramite}"/>
<c:set var="sujetoObligado" 			value="${sujetoObligado}"/>
<c:set var="idSolicitud" 				value="${idSolicitud}"/>
<c:set var="regPatronal" 				value="${regPatronal}"/>
<c:set var="regPatronAnterior" 		    value="${regPatronAnterior}"/>
<c:set var="persona" 					value="${persona}"/>
<c:set var="analisis"           		value="${analisis}"/>
<c:set var="tipoPersona" 				value="${tipoPersona}"/>
<c:set var="mensaje"            		value="${mensaje}"/>
<c:set var="foto" 						value="${estatusAnalisisModel}"/>
<c:set var="configuracion" 				value="${configuracionCE}"/>
<c:set var="bCancelado" 				value="${bCancelado}"/>
<c:set var="bReclasificado" 			value="${bReclasificado}"/>
<c:set var="defaultTipoCausa" 			value="${defaultTipoCausa}"/>
<c:set var="lstHistTipoCausa" 			value="${lstHistTipoCausa}"/>
<c:set var="secuenciaDeNotaria" 	    value="${secuenciaDeNotaria}"/>
<c:set var="esInscripcionInicial" 		value="<%=session.getAttribute(\"grupoTramite\") %>" />

<c:set var="domicilioFiscal" 			value="${sujetoObligado.domicilioFiscal}"/>
<c:set var="soAnterior"                 value="${soAnterior}"/>
<c:set var="centroTrabajo" 				value="${sujetoObligado.cntroTrabajo}"/>
<c:set var="clasificacion" 				value="${sujetoObligado.clasificacion}"/>
<c:set var="productos" 					value="${sujetoObligado.productos}"/>
<c:set var="materiaPrimaMateriales" 	value="${sujetoObligado.materiaPrimaMateriales}"/>
<c:set var="equipos" 					value="${sujetoObligado.equipos}"/>
<c:set var="equiposTransporte"			value="${sujetoObligado.equiposTransporte}"/>
<c:set var="proceso"					value="${sujetoObligado.proceso}"/>
<c:set var="personal" 					value="${sujetoObligado.personal}"/>
<c:set var="bienes" 					value="${sujetoObligado.bienes}"/>
<c:set var="delegacion"     			value="${sujetoObligado.subdelegacion.delegacion}"/>
<c:set var="subdelegacion"     			value="${sujetoObligado.subdelegacion}"/>
<c:set var="clasificacionAnterior"		value="${analisis.clasificacionAnterior}"/>
<c:set var="clasificacionPropuesta"		value="${analisis.clasificacionPropuesta}"/>
<c:set var="adjList"           			value="${adjList}"/>
<c:set var="documentos"           		value="${documentos}"/>
<c:set var="documentosRequeridos"       value="${documentosRequeridos}"/>

<input type="hidden" id="errorRectificacion"  value="${errorRectificacionMsg}"/>
	
<div class="site_position_center">
    <div class="page_holder_no_height">
		<c:forEach items="${messageContext.allMessages}" var="message">
		    <c:if test="${message.severity eq 'Info'}">
		      <div class="info-msg">${message.text}</div>
		    </c:if>
		    <c:if test="${message.severity eq 'Error'}">
		      <div class="error-msg">${message.text}</div>
		    </c:if>
		</c:forEach>
	</div>
</div>

 <div class="site_position_center">
	<div class="main_wrap">
<!-- 		Empieza contenido   -->
		<div class="page_holder_no_height">
			<div class="post_entry_wide no-border">

				<!--Aquí pega tu código-->
				<div style="text-align: right">
					<span class="etiqueta" style="float: none"><spring:message
							code="label.detalle.fecha.presentacion" />:</span> <span class="dato"
						style="float: none"> <fmt:formatDate
							value="${analisis.fechaPresentacion}" pattern="dd/MM/yyyy" />
					</span>&nbsp; <span class="etiqueta" style="float: none"><spring:message
							code="label.detalle.fecha.surte.efecto" />:</span> <span class="dato"
						style="float: none"> <fmt:formatDate
							value="${analisis.fechaEfecto}" pattern="dd/MM/yyyy" />
					</span>
				</div>

				<div class="marcointerno">
					<div class="separadorseccion">
						<spring:message code="label.detalle.datos.generales.titulo" />
					</div>
					<div class="cuerpo_seccion" id="cuerpo_datosgenerales">
						<fieldset>
							<legend style="text-align: center">
								<b><spring:message code="label.detalle.datos.generales" /></b>
							</legend>

							<!-- *****************  Inicia Renglón DATOS GENERALES ********************** -->
							<div class="div-2secciones">
								<span class="etiqueta"><spring:message
										code="label.detalle.registro.patronal" />:</span> <span class="dato"
									id="RP_PATRON">${regPatronal}</span>
							</div>
							<div class="div-2secciones">
								<span class="etiqueta"><spring:message
										code="label.detalle.rfc" />:</span> <span class="dato"
									id="RFC_PATRON">${persona.rfc}</span>
							</div>
							<!-- *****************  Finaliza ********************** -->

							<!-- *****************  Inicia Renglón  ********************** -->
							<div class="div-1seccion">
								<span class="etiqueta"><spring:message
										code="label.detalle.tipo.sociedad" />:</span> <span class="dato"
									id="TIPOPERSONA_PATRON"> <c:if
										test="${tipoPersona eq personaMoral}">
										${persona.tipoSociedad.descripcionAbreviada}
									</c:if>
								</span>
							</div>
							<!-- *****************  Finaliza ********************** -->

							<!-- *****************  Inicia Renglón  ********************** -->
							<div class="div-1seccion">
								<span class="etiqueta"> <c:if
										test="${tipoPersona eq personaFisica}">
										<spring:message code="label.detalle.nombre" />:
									</c:if> <c:if test="${tipoPersona eq personaMoral}">
										<spring:message code="label.detalle.razon.social" />:
									</c:if>
								</span> <span class="dato" id="RAZON_SOCIAL"> <c:if
										test="${tipoPersona eq personaFisica}">
										${sujetoObligado.fisica.nombre} ${sujetoObligado.fisica.primerApellido} ${sujetoObligado.fisica.segundoApellido}
									</c:if> <c:if test="${tipoPersona eq personaMoral}">
										${sujetoObligado.moral.razonSocial}
									</c:if>
								</span>
							</div>
							<c:if test="${tipoPersona eq personaFisica}">
								<div class="div-2secciones">
									<span class="etiqueta"><spring:message
											code="label.detalle.curp" />:</span> <span class="dato" id="CURP">${persona.curp}</span>
								</div>
							</c:if>
							<!-- *****************  Finaliza ********************** -->

							<!-- *****************  Inicia Renglón  ********************** -->
							<div class="div-1seccion">
								<span class="etiqueta"><spring:message
										code="label.detalle.tipo.modificacion" />:</span> <span class="dato"
									id="TIPO_TRAMITE">${tramite.tipoTramite.descripcion}</span>
							</div>
							<!-- *****************  Finaliza ********************** -->
						</fieldset>

						<!-- *****************  Inicia Renglón  DOMICILIO FISCAL ********************** -->
						<div class="div-1seccion fielsetgris">
							<fieldset>
								<legend style="text-align: center">
									<b><spring:message code="label.detalle.domicilio.fiscal" /></b>
								</legend>

								<!-- *****************  Inicia Renglón  ********************** -->
								<div class="div-1seccion">
									<span class="etiqueta"><spring:message
											code="label.detalle.calle" />:</span> <span class="dato">${domicilioFiscal.vialidadPrimaria.nombre}</span>
									<span class="etiqueta"><spring:message
											code="label.detalle.numero.exterior" />:</span> <span class="dato">
										<c:if test="${domicilioFiscal.numExterior1 ne 0}">${domicilioFiscal.numExterior1}</c:if>
										<c:if test="${domicilioFiscal.numExteriorAlf ne 0}">&nbsp;${domicilioFiscal.numExteriorAlf}</c:if>
									</span> <span class="etiqueta"><spring:message
											code="label.detalle.numero.interior" />:</span> <span class="dato">
										<c:if test="${domicilioFiscal.numInterior ne 0}">${domicilioFiscal.numInterior}</c:if>
										<c:if test="${domicilioFiscal.numInteriorAlf ne 0}">&nbsp;${domicilioFiscal.numInteriorAlf}</c:if>
									</span>
								</div>
								<!-- *****************  Finaliza ********************** -->

								<!-- *****************  Inicia Renglón  ********************** -->
								<div class="div-2secciones">
									<span class="etiqueta"><spring:message
											code="label.detalle.entre.calle" />:</span> <span class="dato">${domicilioFiscal.vialidadReferenciaPrimaria.nombre}</span>
								</div>
								<div class="div-2secciones">
									<span class="etiqueta"><spring:message
											code="label.detalle.y.calle" />:</span> <span class="dato">${domicilioFiscal.vialidadReferenciaSecundaria.nombre}</span>
								</div>
								<!-- *****************  Finaliza ********************** -->

								<!-- *****************  Inicia Renglón  ********************** -->
								<div class="div-2secciones">
									<span class="etiqueta"><spring:message
											code="label.detalle.colonia" />:</span> <span class="dato">${domicilioFiscal.asentamiento.nombre}</span>
								</div>
								<div class="div-2secciones">
									<span class="etiqueta"><spring:message
											code="label.detalle.localidad" />:</span> <span class="dato">${domicilioFiscal.asentamiento.localidad.nombre}</span>
								</div>
								<!-- *****************  Finaliza ********************** -->

								<!-- *****************  Inicia Renglón  ********************** -->
								<div class="div-2secciones">
									<span class="etiqueta"><spring:message
											code="label.detalle.municipio.delegacion" />:</span> <span
										class="dato">${domicilioFiscal.asentamiento.localidad.municipio.nombre}</span>
								</div>
								<div class="div-2secciones">
									<span class="etiqueta"><spring:message
											code="label.detalle.entidad.federativa" />:</span> <span
										class="dato">${domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre}</span>
								</div>
								<!-- *****************  Finaliza ********************** -->

								<!-- *****************  Inicia Renglón  ********************** -->
								<div class="div-2secciones">
									<span class="etiqueta"><spring:message
											code="label.detalle.codigo.postal" />:</span> <span class="dato">${domicilioFiscal.codigoPostal.codigoPostal}</span>
								</div>
								<!-- *****************  Finaliza ********************** -->

							</fieldset>
						</div>

						<!-- *****************  Inicia Renglón  CENTRO DE TRABAJO ********************** -->
						<div class="div-1seccion">
							<fieldset>
								<legend style="text-align: center">
									<b><spring:message code="label.detalle.centro.trabajo" /></b>
								</legend>
								<!-- *****************  Inicia validación Centrro de Trabajo ********************** -->
								<c:if test="${centroTrabajo.vialidadPrimaria.nombre!=null}">
									<!-- *****************  Inicia Renglón  ********************** -->
									<div class="div-1seccion">
										<span class="etiqueta"><spring:message
												code="label.detalle.calle" />:</span> <span class="dato">${centroTrabajo.vialidadPrimaria.nombre}</span>
										<span class="etiqueta"><spring:message
												code="label.detalle.numero.exterior" />:</span> <span class="dato">
											<c:if test="${centroTrabajo.numExterior1 ne 0}">${centroTrabajo.numExterior1}</c:if>
											<c:if test="${centroTrabajo.numExteriorAlf ne 0}">&nbsp;${centroTrabajo.numExteriorAlf}</c:if>
										</span> <span class="etiqueta"><spring:message
												code="label.detalle.numero.interior" />:</span> <span class="dato">
											<c:if test="${centroTrabajo.numInterior ne 0}">${centroTrabajo.numInterior}</c:if>
											<c:if test="${centroTrabajo.numInteriorAlf ne 0}">&nbsp;${centroTrabajo.numInteriorAlf}</c:if>
										</span>
									</div>
									<!-- *****************  Finaliza ********************** -->

									<!-- *****************  Inicia Renglón  ********************** -->
									<div class="div-2secciones">
										<span class="etiqueta"><spring:message
												code="label.detalle.entre.calle" />:</span> <span class="dato">${centroTrabajo.vialidadReferenciaPrimaria.nombre}</span>
									</div>
									<div class="div-2secciones">
										<span class="etiqueta"><spring:message
												code="label.detalle.y.calle" />:</span> <span class="dato">${centroTrabajo.vialidadReferenciaSecundaria.nombre}</span>
									</div>
									<!-- *****************  Finaliza ********************** -->

									<!-- *****************  Inicia Renglón  ********************** -->
									<div class="div-2secciones">
										<span class="etiqueta"><spring:message
												code="label.detalle.colonia" />:</span> <span class="dato">${centroTrabajo.asentamiento.nombre}</span>
									</div>
									<div class="div-2secciones">
										<span class="etiqueta"><spring:message
												code="label.detalle.localidad" />:</span> <span class="dato">${centroTrabajo.asentamiento.localidad.nombre}</span>
									</div>
									<!-- *****************  Finaliza ********************** -->

									<!-- *****************  Inicia Renglón  ********************** -->
									<div class="div-2secciones">
										<span class="etiqueta"><spring:message
												code="label.detalle.municipio.delegacion" />:</span> <span
											class="dato">${centroTrabajo.asentamiento.localidad.municipio.nombre}</span>
									</div>
									<div class="div-2secciones">
										<span class="etiqueta"><spring:message
												code="label.detalle.entidad.federativa" />:</span> <span
											class="dato">${centroTrabajo.asentamiento.localidad.municipio.entidadFederativa.nombre}</span>
									</div>
									<!-- *****************  Finaliza ********************** -->

									<!-- *****************  Inicia Renglón  ********************** -->
									<div class="div-2secciones">
										<span class="etiqueta"><spring:message
												code="label.detalle.codigo.postal" />:</span> <span class="dato">${centroTrabajo.codigoPostal.codigoPostal}</span>
									</div>
									<!-- *****************  Finaliza ********************** -->
								</c:if>
								<c:if test="${centroTrabajo.vialidadPrimaria.nombre==null}">
									<div class="div-1seccion">
										<span class="etiqueta">Domicilio:</span> <span class="dato">${centroTrabajo.descripcion}</span>
									</div>
								</c:if>
								<!-- *****************  Finaliza validación Centrro de Trabajo ********************** -->

							</fieldset>
						</div>
						<c:if test="${analizandoDictamen}">
							<!-- *****************  Inicia Renglón  Dictamen ********************** -->
							<div class="div-1seccion">
								<form method="POST" id="formPatronDictamen"
									action="${mvn.web.url.dictamen}" target="_blank">
									<fieldset>
										<legend style="text-align: center">
											<b>Dictamen electr&oacute;nico</b>
										</legend>
										<div class="div-1seccion" style="text-align: center">
											<input type="hidden" id="cveIdPatronDictamen"
												name="cveIdPatronDictamen" value="${cveIdPatronDictamen}" />
											<button type="button" id="verDictamen" class="mboton">Ver
												detalle del dictamen</button>
										</div>
									</fieldset>
								</form>
							</div>
							<!-- *****************  Finaliza validación dictamen ********************** -->
						</c:if>
					</div>
				</div>

				<!-- *****************  Inicia CLASIFICACION ACTUAL ********************** -->
				<div class="marcointerno">
					<div class="separadorseccion">
						<c:if test="${!analizandoDictamen}">
							<spring:message
								code="label.detalle.clasificacion.actual.titulo.GCE" />
						</c:if>
						<c:if test="${analizandoDictamen}">
							<spring:message
								code="label.detalle.clasificacion.actual.titulo.GCE.dictamen" />
						</c:if>
					</div>
					<div class="cuerpo_seccion">
						<div>
							<table style="width: 100%">
								<tr>
									<th width="20%"><spring:message
											code="label.detalle.clasificacion" /></th>
									<th width="10%"><spring:message code="label.detalle.clave" /></th>
									<th><spring:message code="label.detalle.descripcion" /></th>
								</tr>
								<tr>
									<th width="20%"><spring:message
											code="label.detalle.division.economica" /></th>
									<td width="10%">${foto.fraccionActual.grupo.division.numDivision}</td>
									<td>${foto.fraccionActual.grupo.division.descripcion}</td>
								</tr>
								<tr>
									<th width="20%"><spring:message code="label.detalle.grupo" /></th>
									<td width="10%">${foto.fraccionActual.grupo.division.numDivision}${foto.fraccionActual.grupo.numGrupo}</td>
									<td>${foto.fraccionActual.grupo.descripcion}</td>
								</tr>
								<tr>
									<th width="20%"><spring:message
											code="label.detalle.fraccion" /></th>
									<td width="10%">${foto.fraccionActual.grupo.division.numDivision}${foto.fraccionActual.grupo.numGrupo}${foto.fraccionActual.numFraccion}</td>
									<td>${foto.fraccionActual.descripcion}</td>
								</tr>
							</table>
						</div>
						<!-- *****************  Finaliza ********************** -->

						<!-- *****************  Inicia Renglón  ********************** -->
						<div class="div-2secciones">
							<span class="etiqueta_extra "><spring:message
									code="label.detalle.clase.riesgo" />:</span> <span class="dato">${foto.fraccionActual.clase.descripcion}</span>
						</div>
						<div class="div-2secciones">
							<span class="etiqueta_extra "><spring:message
									code="label.detalle.prima.media.srt" />:</span> <span class="dato">${foto.primaDec}</span>
						</div>
						<!-- *****************  Finaliza ********************** -->
					</div>
				</div>

				<!-- Empieza if de DICTAMEN -->
				<c:if test="${!analizandoDictamen}">
					<!-- *****************  Inicia GIRO ******************** -->
					<div class="marcointerno">
						<div class="separadorseccion">Giro</div>
						<div class="cuerpo_seccion">
							<div>

								<!-- *****************  Inicia Renglón  ********************** -->
								<!-- <div class="div-1seccion">-->


								<table style="width: 100%"
									style="width: 100%; vertical-align: top;" cellpadding="0"
									cellspacing="0"
									class="table table-striped table-bordered table-word-wrap-fixed">
									<tr class="fielsetgris">
										<th width="40%"><spring:message
												code="label.detalle.descripcion" /></th>
										<th width="20%"><spring:message
												code="label.detalle.numero.centros.trabajo" /></th>
										<th width="20%"><spring:message
												code="label.detalle.presta.servicio.personal" /></th>
										<th width="20%"><spring:message
												code="label.detalle.solicita.rpc" /></th>
									</tr>
									<tr class="fondo_datos_7">
										<td width="40%">${clasificacion.giro}</td>
										<td width="20%">${clasificacion.numCentrosTraba}</td>
										<td width="20%"><c:if
												test="${clasificacion.indPrestaServicioPersonal eq 0}">
										No
									</c:if> <c:if test="${clasificacion.indPrestaServicioPersonal eq 1}">
										Si
									</c:if></td>
										<td width="20%"><c:if
												test="${clasificacion.indRegPatClase != 1}">
										No
									</c:if> <c:if test="${clasificacion.indRegPatClase == 1}">
										Si
									</c:if></td>
									</tr>
								</table>
								<!-- </div> -->
								<!-- *****************  Finaliza ********************** -->
							</div>
						</div>
					</div>
					<!-- *****************  Finaliza GIRO *********************** -->

					<!-- *****************  Inicia ACTIVIDAD ECONOMICA ********************** -->
					<div class="marcointerno">
						<div class="separadorseccion">
							<spring:message code="label.detalle.actividad.economica.titulo" />
						</div>
						<div class="cuerpo_seccion">

							<!-- *****************  Inicia Renglón  ********************** -->
							<div>
								<table style="width: 100%"
									style="width: 100%; vertical-align: top;" cellpadding="0"
									cellspacing="0"
									class="table table-striped table-bordered table-word-wrap-fixed">
									<tr>
										<th><spring:message
												code="label.detalle.principales.productos" /></th>
									</tr>
									<c:forEach var="producto" items="${productos}">
										<tr class="fondo_datos_7">
											<td>${producto.descripcion}</td>
										</tr>
									</c:forEach>
								</table>

							</div>
							<!-- *****************  Finaliza ********************** -->

							<!-- *****************  Inicia Renglón  ********************** -->
							<div>
								<table style="width: 100%"
									style="width: 100%; vertical-align: top;" cellpadding="0"
									cellspacing="0"
									class="table table-striped table-bordered table-word-wrap-fixed">
									<tr>
										<th><spring:message
												code="label.detalle.principales.materias.primas" /></th>
									</tr>
									<c:forEach var="materiaPrimaMaterial"
										items="${materiaPrimaMateriales}">
										<tr class="fondo_datos_7">
											<td>${materiaPrimaMaterial.descripcion}</td>
										</tr>
									</c:forEach>
								</table>
							</div>
							<!-- *****************  Finaliza ********************** -->

							<!-- *****************  Inicia Renglón  ********************** -->
							<fieldset>
								<legend style="text-align: center">
									<b><spring:message
											code="label.detalle.maquinaria.equipo.utilizado" /></b>
								</legend>

								<div>
									<table style="width: 100%"
										style="width: 100%; vertical-align: top;" cellpadding="0"
										cellspacing="0"
										class="table table-striped table-bordered table-word-wrap-fixed">
										<tr class="fielsetgris">
											<th width="10%"><spring:message
													code="label.detalle.numero.unidades" /></th>
											<th width="30%"><spring:message
													code="label.detalle.nombre" /></th>
											<th width="20%"><spring:message code="label.detalle.uso" /></th>
											<th width="20%"><spring:message
													code="label.detalle.tipo.maquinaria" /></th>
											<th width="20%"><spring:message
													code="label.detalle.capacidad.potencia" /></th>
										</tr>
										<c:forEach var="equipo" items="${equipos}">
											<tr class="fondo_datos_7">
												<td width="10%">${equipo.numUnidades}</td>
												<td width="30%">${equipo.desNombre}</td>
												<td width="20%">${equipo.desUso}</td>
												<td width="20%">${equipo.tipo.descripcion}</td>
												<td width="20%">${equipo.desCapacidadPotencia}</td>
											</tr>
										</c:forEach>
									</table>
								</div>
							</fieldset>
							<!-- *****************  Finaliza ********************** -->

							<!-- *****************  Inicia Renglón  ********************** -->
							<fieldset>
								<legend style="text-align: center">
									<b><spring:message
											code="label.detalle.equipo.transporte.utilizado" /></b>
								</legend>

								<div class="div-1seccion">
									<table style="width: 100%"
										style="width: 100%; vertical-align: top;" cellpadding="0"
										cellspacing="0"
										class="table table-striped table-bordered table-word-wrap-fixed">
										<tr>
											<th width="10%"><spring:message
													code="label.detalle.numero.unidades" /></th>
											<th width="30%"><spring:message
													code="label.detalle.nombre" /></th>
											<th width="20%"><spring:message code="label.detalle.uso" /></th>
											<th width="20%"><spring:message
													code="label.detalle.combustible.energia" /></th>
											<th width="20%"><spring:message
													code="label.detalle.capacidad.potencia" /></th>
										</tr>
										<c:forEach var="equipoTransporte" items="${equiposTransporte}">
											<tr class="fondo_datos_7">
												<td width="10%">${equipoTransporte.numUnidades}</td>
												<td width="30%">${equipoTransporte.desNombre}</td>
												<td width="20%">${equipoTransporte.desUso}</td>
												<td width="20%">${equipoTransporte.tipoCombustible.desTipoCombustible}</td>
												<td width="20%">${equipoTransporte.desCapacidadPotencia}</td>
											</tr>
										</c:forEach>
									</table>
								</div>
							</fieldset>
							<!-- *****************  Finaliza ********************** -->

							<!-- *****************  Inicia Renglón  ********************** -->
							<fieldset>
								<legend style="text-align: center">
									<b><spring:message code="label.detalle.procesos.trabajo" /></b>
								</legend>

								<div class="div-1seccion">
									<table style="width: 100%"
										style="width: 100%; vertical-align: top;" cellpadding="0"
										cellspacing="0"
										class="table table-striped table-bordered table-word-wrap-fixed">
										<tr>
											<th><spring:message code="label.detalle.proceso.inicial" /></th>
											<td width="85%" bgcolor="white">${proceso.desInicial}</td>
										</tr>
										<tr>
											<th><spring:message
													code="label.detalle.proceso.intermedio" /></th>
											<td width="85%">${proceso.desIntermedio}</td>
										</tr>
										<tr>
											<th><spring:message code="label.detalle.proceso.final" /></th>
											<td width="85%">${proceso.desFinal}</td>
										</tr>
									</table>
								</div>
							</fieldset>
							<!-- *****************  Finaliza ********************** -->

							<!-- *****************  Inicia Renglón  ********************** -->
							<fieldset>
								<legend style="text-align: center">
									<b><spring:message code="label.detalle.personal" /></b>
								</legend>

								<div class="div-1seccion">
									<table style="width: 100%"
										style="width: 100%; vertical-align: top;" cellpadding="0"
										cellspacing="0"
										class="table table-striped table-bordered table-word-wrap-fixed">
										<tr>
											<th width="10%"><spring:message
													code="label.detalle.numero" /></th>
											<th><spring:message code="label.detalle.ocupacion" /></th>
										</tr>
										<c:forEach var="persona" items="${personal}">
											<tr class="fondo_datos_7">
												<td width="10%">${persona.numTrabajadores}</td>
												<td>${persona.oficioOcupacion}</td>
											</tr>
										</c:forEach>
									</table>
								</div>
							</fieldset>
							<!-- *****************  Finaliza ********************** -->

							<!-- *****************  Inicia Renglón  ********************** -->
							<div class="div-1seccion">
								<table style="width: 100%"
									style="width: 100%; vertical-align: top;" cellpadding="0"
									cellspacing="0"
									class="table table-striped table-bordered table-word-wrap-fixed">
									<tr>
										<th><spring:message
												code="label.detalle.actividades.complementarias" /></th>
									</tr>
									<c:if test="${clasificacion.indTransportePropio == 1}">
										<tr class="fondo_datos_7">
											<td><spring:message
													code="label.detalle.con.transporte.propio" /></td>
										</tr>
									</c:if>
									<c:if test="${clasificacion.indTransporteAjeno == 1}">
										<tr class="fondo_datos_7">
											<td><spring:message
													code="label.detalle.con.transporte.ajeno" /></td>
										</tr>
									</c:if>
									<c:if test="${clasificacion.indDistribuyeEntrega == 1}">
										<tr class="fondo_datos_7">
											<td><spring:message
													code="label.detalle.no.distribuye.ni.entrega" /></td>
										</tr>
									</c:if>
									<c:if test="${clasificacion.indServiciosATerceros == 1}">
										<tr class="fondo_datos_7">
											<td><spring:message
													code="label.detalle.mantenimiento.terceros" /></td>
										</tr>
									</c:if>
								</table>
							</div>

							<c:if test="${muestraBienes == 1}">
								<fieldset>
									<legend style="text-align: center">
										<b><spring:message code="label.detalle.datos.bienes" /></b>
									</legend>
									<div class="div-1seccion">
										<table style="width: 100%"
											style="width: 100%; vertical-align: top;" cellpadding="0"
											cellspacing="0"
											class="table table-striped table-bordered table-word-wrap-fixed">
											<tr>
												<th width="10%"><spring:message
														code="label.detalle.cantidad" /></th>
												<th><spring:message code="label.detalle.descripcion" /></th>
											</tr>
											<c:forEach var="bienes" items="${bienes}">
												<tr class="fondo_datos_7">
													<td>${bienes.numCantidad}</td>
													<td>${bienes.desBienes}</td>
												</tr>
											</c:forEach>
										</table>
										<table style="width: 100%"
											style="width: 100%; vertical-align: top;" cellpadding="0"
											cellspacing="0"
											class="table table-striped table-bordered table-word-wrap-fixed">
											<tr>
												<th><spring:message code="label.detalle.uso" /></th>
											</tr>
											<tr class="fondo_datos_7">
												<td>${sujetoObligado.desUsosBienes}</td>
											</tr>
										</table>
										<table style="width: 100%"
											style="width: 100%; vertical-align: top;" cellpadding="0"
											cellspacing="0"
											class="table table-striped table-bordered table-word-wrap-fixed">
											<tr>
												<th><spring:message
														code="label.detalle.afectacion.directa" /></th>
											</tr>
											<tr class="fondo_datos_7">
												<td>${sujetoObligado.desAfectacion}</td>
											</tr>
										</table>
									</div>
								</fieldset>
							</c:if>
							<!-- *****************  Finaliza ********************** -->
						</div>
					</div>
				</c:if>
				<c:if test="${esInscripcionInicial ne 1}">
					<c:if test="${foto.fraccionAnterior != null}">
						<div class="marcointerno">
							<div class="separadorseccion">
								<c:if test="${!analizandoDictamen}">
									<spring:message
										code="label.detalle.clasificacion.anterior.titulo" />
								</c:if>
								<c:if test="${analizandoDictamen}">
									<spring:message
										code="label.detalle.clasificacion.anterior.titulo.dictamen" />
								</c:if>
							</div>
							<div class="cuerpo_seccion">
								<div>
									<table style="width: 100%"
										style="width: 100%; vertical-align: top;" cellpadding="0"
										cellspacing="0"
										class="table table-striped table-bordered table-word-wrap-fixed">
										<tr>
											<th width="20%"><spring:message
													code="label.detalle.clasificacion" /></th>
											<th width="10%"><spring:message
													code="label.detalle.clave" /></th>
											<th><spring:message code="label.detalle.descripcion" /></th>
										</tr>
										<tr>
											<th width="20%"><spring:message
													code="label.detalle.division.economica" /></th>
											<td width="10%">${foto.fraccionAnterior.grupo.division.numDivision}</td>
											<td>${foto.fraccionAnterior.grupo.division.descripcion}</td>
										</tr>
										<tr>
											<th width="20%"><spring:message
													code="label.detalle.grupo" /></th>
											<td width="10%">${foto.fraccionAnterior.grupo.division.numDivision}${foto.fraccionAnterior.grupo.numGrupo}</td>
											<td>${foto.fraccionAnterior.grupo.descripcion}</td>
										</tr>
										<tr>
											<th width="20%"><spring:message
													code="label.detalle.fraccion" /></th>
											<td width="10%">${foto.fraccionAnterior.grupo.division.numDivision}${foto.fraccionAnterior.grupo.numGrupo}${foto.fraccionAnterior.numFraccion}</td>
											<td>${foto.fraccionAnterior.descripcion}</td>
										</tr>
									</table>
								</div>

								<!-- *****************  Inicia Renglón  ********************** -->
								<div class="div-2secciones">
									<span class="etiqueta_extra "><spring:message
											code="label.detalle.clase.riesgo" />:</span> <span class="dato">${foto.fraccionAnterior.clase.descripcion}</span>
								</div>
								<div class="div-2secciones">
									<span class="etiqueta_extra "><spring:message
											code="label.detalle.prima.media.srt" />:</span> <span class="dato">${foto.fraccionAnterior.primaSRT}</span>
								</div>
								<!-- *****************  Finaliza ********************** -->
							</div>
						</div>
					</c:if>
				</c:if>

				<c:if test="${clasificacionPropuesta != null}">
					<div class="marcointerno">
						<div class="separadorseccion">
							<c:if test="${!analizandoDictamen}">
								<spring:message
									code="label.detalle.clasificacion.propuesta.titulo" />
							</c:if>
							<c:if test="${analizandoDictamen}">
								<spring:message
									code="label.detalle.clasificacion.propuesta.titulo.dictamen" />
							</c:if>
						</div>
						<div class="cuerpo_seccion">
							<div>
								<table style="width: 100%"
									style="width: 100%; vertical-align: top;" cellpadding="0"
									cellspacing="0"
									class="table table-striped table-bordered table-word-wrap-fixed">
									<tr>
										<th width="20%"><spring:message
												code="label.detalle.clasificacion" /></th>
										<th width="10%"><spring:message
												code="label.detalle.clave" /></th>
										<th><spring:message code="label.detalle.descripcion" /></th>
									</tr>
									<tr>
										<th width="20%"><spring:message
												code="label.detalle.division.economica" /></th>
										<td width="10%">${clasificacionPropuesta.fraccion.grupo.division.numDivision}</td>
										<td>${clasificacionPropuesta.fraccion.grupo.division.descripcion}</td>
									</tr>
									<tr>
										<th width="20%"><spring:message
												code="label.detalle.grupo" /></th>
										<td width="10%">${clasificacionPropuesta.fraccion.grupo.division.numDivision}${clasificacionPropuesta.fraccion.grupo.numGrupo}</td>
										<td>${clasificacionPropuesta.fraccion.grupo.descripcion}</td>
									</tr>
									<tr>
										<th width="20%"><spring:message
												code="label.detalle.fraccion" /></th>
										<td width="10%">${clasificacionPropuesta.fraccion.grupo.division.numDivision}${clasificacionPropuesta.fraccion.grupo.numGrupo}${clasificacionPropuesta.fraccion.numFraccion}</td>
										<td>${clasificacionPropuesta.fraccion.descripcion}</td>
									</tr>
								</table>
							</div>

							<!-- *****************  Inicia Renglón  ********************** -->
							<div class="div-2secciones">
								<span class="etiqueta_extra "><spring:message
										code="label.detalle.clase.riesgo" />:</span> <span class="dato">${clasificacionPropuesta.fraccion.clase.descripcion}</span>
							</div>
							<div class="div-2secciones">
								<!-- Seleccionando prima que no es sugerida-->
								<c:if test="${clasificacionPropuesta.primaSugerida == null}">
									<span class="etiqueta_extra "><spring:message
											code="label.detalle.prima.media.srt" />:</span>
									<span class="dato">${clasificacionPropuesta.fraccion.primaSRT}</span>
								</c:if>
								<!-- Seleccionando prima que es sugerida-->
								<c:if test="${clasificacionPropuesta.primaSugerida != null}">
									<span class="etiqueta_extra "><spring:message
											code="label.detalle.prima.media.sugerida" />:</span>
									<span class="dato">${clasificacionPropuesta.primaSugerida}</span>
								</c:if>
							</div>
							<!-- *****************  Finaliza ********************** -->
						</div>
					</div>
				</c:if>

				<c:if test="${tramite.tipoTramite.idTipoTramite == 176}">
				<div class="marcointerno">
					<div class="separadorseccion">
						<spring:message code="label.detalle.domicilio.anterior" />
					</div>
					<div class="cuerpo_seccion">

						<table
							class="table table-striped table-bordered table-word-wrap-fixed"
							style="width: 100%;" cellspacing="0" cellpadding="0">
							<tbody>
								<tr>
									<th>Registro Patronal</th>
									<td>${ regPatronAnterior}</td>
									<th>Estatus</th>
									<td>${ soAnterior.clasificacion.indBaja == 2?'Baja':'Activo'}</td>
								</tr>

							</tbody>
						</table>
						
						<c:if test="${soAnterior.cntroTrabajo.vialidadPrimaria.nombre!=null}">
							<div class="div-1seccion">
								<span class="etiqueta"><spring:message
										code="label.detalle.calle" />:</span> <span class="dato">${soAnterior.cntroTrabajo.vialidadPrimaria.nombre}</span>
								<span class="etiqueta"><spring:message
										code="label.detalle.numero.exterior" />:</span> <span class="dato">
									<c:if test="${soAnterior.cntroTrabajo.numExterior1 ne 0}">${soAnterior.cntroTrabajo.numExterior1}</c:if>
									<c:if test="${soAnterior.cntroTrabajo.numExteriorAlf ne 0}">&nbsp;${soAnterior.cntroTrabajo.numExteriorAlf}</c:if>
								</span> <span class="etiqueta"><spring:message
										code="label.detalle.numero.interior" />:</span> <span class="dato">
									<c:if test="${soAnterior.cntroTrabajo.numInterior ne 0}">${domicilioFiscal.numInterior}</c:if>
									<c:if test="${soAnterior.cntroTrabajo.numInteriorAlf ne 0}">&nbsp;${soAnterior.cntroTrabajo.numInteriorAlf}</c:if>
								</span>
							</div>
	
							<!-- *****************  Inicia Renglón  ********************** -->
							<!-- *****************  Inicia Renglón  ********************** -->
							<div class="div-2secciones">
								<span class="etiqueta"><spring:message
										code="label.detalle.entre.calle" />:</span> <span class="dato">${soAnterior.cntroTrabajo.vialidadReferenciaPrimaria.nombre}</span>
							</div>
							<div class="div-2secciones">
								<span class="etiqueta"><spring:message
										code="label.detalle.y.calle" />:</span> <span class="dato">${soAnterior.cntroTrabajo.vialidadReferenciaSecundaria.nombre}</span>
							</div>
							<!-- *****************  Finaliza ********************** -->
	
							<!-- *****************  Inicia Renglón  ********************** -->
							<div class="div-2secciones">
								<span class="etiqueta"><spring:message
										code="label.detalle.colonia" />:</span> <span class="dato">${soAnterior.cntroTrabajo.asentamiento.nombre}</span>
							</div>
							<div class="div-2secciones">
								<span class="etiqueta"><spring:message
										code="label.detalle.localidad" />:</span> <span class="dato">${soAnterior.cntroTrabajo.asentamiento.localidad.nombre}</span>
							</div>
							<!-- *****************  Finaliza ********************** -->
	
							<!-- *****************  Inicia Renglón  ********************** -->
							<div class="div-2secciones">
								<span class="etiqueta"><spring:message
										code="label.detalle.municipio.delegacion" />:</span> <span
									class="dato">${soAnterior.cntroTrabajo.asentamiento.localidad.municipio.nombre}</span>
							</div>
							<div class="div-2secciones">
								<span class="etiqueta"><spring:message
										code="label.detalle.entidad.federativa" />:</span> <span class="dato">${soAnterior.cntroTrabajo.asentamiento.localidad.municipio.entidadFederativa.nombre}</span>
							</div>
							<!-- *****************  Finaliza ********************** -->
	
							<!-- *****************  Inicia Renglón  ********************** -->
							<div class="div-2secciones">
								<span class="etiqueta"><spring:message
										code="label.detalle.codigo.postal" />:</span> <span class="dato">${soAnterior.cntroTrabajo.codigoPostal.codigoPostal}</span>
							</div>
						</c:if>
						<c:if test="${soAnterior.cntroTrabajo.vialidadPrimaria.nombre==null}">
									<div class="div-1seccion">
										<span class="etiqueta">Domicilio:</span> <span class="dato">${soAnterior.cntroTrabajo.descripcion}</span>
									</div>
								</c:if>
					</div>
				</div>
				</c:if>
				<c:if test="${!analizandoDictamen}">
					<!-- Inicio. Validación si es Modificación muestra el apartado de Histórico de Causa -->
					<c:if test="${tTramite ne '0'}">
						<div class="marcointerno">
							<div class="separadorseccion">
								<spring:message code="label.detalle.historico.tipo.causa.titulo" />
							</div>
							<div class="cuerpo_seccion">
								<div>
									<table style="width: 100%"
										style="width: 100%; vertical-align: top;" cellpadding="0"
										cellspacing="0"
										class="table table-striped table-bordered table-word-wrap-fixed">
										<tr>
											<th width="10%"><spring:message
													code="label.detalle.clave" /></th>
											<th width="65%"><spring:message
													code="label.detalle.descripcion" /></th>
											<th width="25%"><spring:message
													code="label.detalle.fecha" /></th>
										</tr>

										<c:if
											test="${defaultTipoCausa.desCausa ne null and defaultTipoCausa.desCausa ne ''}">
											<tr align="center">
												<td width="15%">${defaultTipoCausa.cveIdTipoCausa}</td>
												<td width="50%">${defaultTipoCausa.desCausa}</td>
												<td width="35%"><fmt:formatDate type="both"
														dateStyle="short" timeStyle="short"
														value="${analisis.fechaPresentacion}"
														pattern="dd/MM/yyyy HH:mm" /></td>
											</tr>
										</c:if>
										<c:forEach var="tipoCausa" items="${lstHistTipoCausa}">
											<tr align="center">
												<td width="15%">${tipoCausa.cveIdTipoCausa}</td>
												<td width="50%">${tipoCausa.desCausa}</td>
												<td width="35%"><fmt:formatDate type="both"
														dateStyle="short" timeStyle="short"
														value="${tipoCausa.stmpFechaActualizado}"
														pattern="dd/MM/yyyy HH:mm" /></td>
											</tr>
										</c:forEach>
									</table>
								</div>
							</div>
						</div>
					</c:if>
				</c:if>


			<!-- Inicio. Validación si es Modificación muestra el apartado de Histórico de Causa -->
		<div class="marcointerno">	
			<div class="separadorseccion"><spring:message code="label.detalle.historico.estatus.titulo" /></div>
				<div class="cuerpo_seccion" >
		 			<div>
		 			<table style="width: 100%" style="width: 100%; vertical-align: top;" cellpadding="0"
											cellspacing="0" class="table table-striped table-bordered table-word-wrap-fixed">
							<tr>
								<th width="25%"><spring:message code="label.detalle.estatus" /></th>
								<th width="40%"><spring:message code="label.detalle.observaciones" /></th>
								<th width="20%"><spring:message code="label.detalle.fecha" /></th>
			                    <th><spring:message code="label.detalle.usuario" /></th>
							</tr>
						<c:forEach var="comentario" items="${analisis.comentariosDetalle}">
							<tr align="center">
								<td width="25%">${comentario.acccionRealizada}</td>
								<td width="40%">${comentario.comentario}</td>
								<td width="20%">${comentario.fecha}</td>
			                    <td>${comentario.usuario}</td>
							</tr>
						</c:forEach>
			 			</table>		 
					</div>		
				</div>
			</div>
			<!------------- Separador para documentos requeridos ------->
			<div class="marcointerno">	
			<div class="separadorseccion"><spring:message code="label.detalle.documentos.requeridos.portramite" /></div>
				<div class="cuerpo_seccion" >
		 			<div>
		
			<table style="width: 100%" style="width: 100%; vertical-align: top;" cellpadding="0"
					cellspacing="0" class="table table-striped table-bordered table-word-wrap-fixed">
				<c:forEach var="doctoReq" items="${documentos}">
					
					<tbody>
						<c:forEach var="docto" items="${doctoReq.documentos}">
							<tr>
								<td>${docto.desDocumento}</td>
							</tr>
						</c:forEach>
					</tbody>
				</c:forEach>
			</table>
	 
					</div>		
				</div>
			</div>
			  
<c:if test="${existAdj}">	
		<!-- Documentos adjuntos al tramite -->
		<div class="marcointerno">	
			<div class="separadorseccion"><spring:message code="label.detalle.documentos.adjuntos" /></div>
				<div class="cuerpo_seccion" >
		 			<div>
		 			<table style="width: 100%" style="width: 100%; vertical-align: top;" cellpadding="0"
											cellspacing="0" class="table table-striped table-bordered table-word-wrap-fixed">
						<c:forEach var="adjunto" items="${adjList}">
							<tr align="left">
								<td>
									<a href="<%=request.getContextPath()%>/analisis/verDocumentoAdjunto?documento=${adjunto.rutaArchivo}">
										${adjunto.nombreArchivo}
									</a>
								</td>														
							</tr>
						</c:forEach>
			 			</table>		 
					</div>		
				</div>
			</div>			  
</c:if>			  
			  
			<!-- Visor de documentos para la solicitud -->	

<c:if test="${!analizandoDictamen}">		
		 	<div class="marcointerno">	
		 	
				<div class="separadorseccion"><spring:message code="label.detalle.visor.documentos.titulo" /></div>

 				<div class="cuerpo_seccion">
			
 				
					<div id="tabs">
						<ul>
							<li><a href="#tabs-1">Documentos Adjuntos</a></li>
							<li><a href="#tabs-2">Documentos Resultantes</a></li>
						</ul>
						<div id="tabs-1">
							<iframe id="firmaIframe" name="firmaIframe" height="500"
								style="display: none; width: 100%" frameborder="0"></iframe>
						</div>
						<div id="tabs-2">
							<iframe id="firmaIframeResultantes" name="firmaIframeResultantes"
								height="500" style="display: none; width: 100%" frameborder="0"></iframe>
						</div>
					</div>
 				
			 			
				</div>
					  
				
			</div> 			  
	</c:if>		<!-- Termina if de DICTAMEN -->
    		<!-- Botones de acuerdo a Status de Solicitud -->
   			<div class="marcointerno" style="text-align: center;">
   				<c:if test="${bReclasificado && esInscripcionInicial ne 1}">
   					<p><font color="#B22222"><b><spring:message code="label.detalle.reclasificacion" /></b></font></p>
   				</c:if>
   				<c:if test="${configuracion.boDesechar && !analizandoDictamen && esInscripcionInicial ne 1}">
				 
				 <a href="<%=request.getContextPath()%>/solicitud/download.do">
				 	<img  src="<spring:url value="/static/resources/imagenes/documentos.jpg" htmlEscape="true"/>" title="Plantilla de Oficio de Desechar Tr&aacute;mite"  
				 	   style="vertical-align: bottom;" width="27" height="24"/>Plantilla de Oficio de Desechar Tr&aacute;mite
				 </a>
				<br>
				<br>
				</c:if>
   				<c:if test="${!analisis.indActivo && bCancelado}">
   					<p><font color="#B22222"><b><spring:message code="label.detalle.modificacion.no.disponible" /></b></font></p>
   				</c:if>
   				
				<p align="center">
					<b>
					<spring:message code="label.detalle.estatus.actual.analisis" /><br>
					${mensaje}<br>															
					</b>
					<c:if test="${configuracion.boIndFirma && !analizandoDictamen}">
						<spring:message code="label.detalle.mensaje.clem.firmada" /><br>
					</c:if>
				</p>
				
				<c:if test="${configuracion.boRatificar}">
					<input type="button" class="botonDDiv" id="Ratificar" value="Ratificar" />
				</c:if>
				<c:if test="${configuracion.boRectificar}">
					<button type="button" class="botonDDiv" id="Rectificar" onclick="rectificar()">
					<c:if test="${analizandoDictamen}">Enviar a revisi&oacute;n</c:if>
					<c:if test="${!analizandoDictamen}">Rectificar</c:if>
					</button>
				</c:if>
				<c:if test="${configuracion.boAutoRatificarN1}">
					<input type="button" class="botonDDiv" id="AutorizarRatificacion" value="Autorizar Ratificaci&oacute;n" />
				</c:if>
				<c:if test="${configuracion.boRechRatificarN1}">
					<input type="button" class="botonDDiv" id="RechazarRatificacion" value="Rechazar la Ratificaci&oacute;n"/>
				</c:if>
				<c:if test="${configuracion.boAutoRectificarN1}">
					<button type="button" class="botonDDiv" id="capturaClemm" onclick="autorizarRectificacion()">
					<c:if test="${analizandoDictamen}">Autorizar Enviar a revisi&oacute;n</c:if>
					<c:if test="${!analizandoDictamen}">Autorizar Rectificaci&oacute;n</c:if>
					</button>
				</c:if>
				<c:if test="${configuracion.boRechRectificarN1}">
					<button  type="button" class="botonDDiv" id="RechazarRectificacion">
					<c:if test="${analizandoDictamen}">Rechazar Enviar a revisi&oacute;n</c:if>
					<c:if test="${!analizandoDictamen}">Rechazar la Rectificaci&oacute;n</c:if>
					</button>
				</c:if>
				
				
				
				<c:if test="${configuracion.boVerClem && !analizandoDictamen}">
					<%-- Se modifica la forma de imprimir la clem para abrir en nueva ventana y no se quede la pantalla en espera  				
						<input type="button" class="botonDDiv" id="VerClem" value="Ver Clem" />
					 --%>					
					<a href="<%=request.getContextPath()%>/clem/${analisis.cveIdAnalisis}/verClem" target="_blank">
						<input type="button" class="botonDDiv" id="VerClem"  value="Ver Clem">
					</a>					
				</c:if>
				
				<c:if test="${configuracion.boIndFirma && !analizandoDictamen}">
					<a href="${configuracion.urlClemFirma}" target="_blank">
						<input class="botonDDiv" id="VerClemF" type="button" value="Ver Clem">
					</a>
				</c:if>
				
				<c:if test="${configuracion.boModificarClem && !analizandoDictamen}">
					<input type="button" class="botonDDiv" id="ModificarClem" value="Modificar Clem" onclick="modificarClem()"/>
				</c:if>
				<c:if test="${configuracion.boModificarAuto}">
					<input type="button" class="botonDDiv" id="modificarAutorizacion1" value="Modificar Autorizaci&oacute;n" />
				</c:if>

				<c:if test="${configuracion.boDesechar && !analizandoDictamen && esInscripcionInicial ne 1}">
					<button type="button" class="botonDDiv" id="Desechar" onclick="desechar()">Desechar Tr&aacute;mite</button>				
				</c:if>

				<input type="button" class="botonDDiv" name ="btnRegresar" id="Regresar" value="Regresar" />
			</div>
				
			<div id="dialog-form-3" title="Ratificaci&oacute;n" style="visibility:hidden" >
				<form id="ratificaForm" action="<%=request.getContextPath()%>/analisis/ratificarSolicitud" >
					<input type="hidden" id="cveIdAnalisis" name="cveIdAnalisis" value="${analisis.cveIdAnalisis}" />
					<input type="hidden" id="cveIdSolicitud" name="cveIdSolicitud" value="${idSolicitud}" />
					<input type="hidden" id="regPatronal" name="regPatronal" value="${regPatronal}" />
					<input type="hidden" id="tipoPersona" name="tipoPersona" value="${tipoPersona}" />
					<input type="hidden" id="cveIdDelegacion" name="cveIdDelegacion" value="${delegacion.id}" />
					<input type="hidden" id="cveIdSubdelegacion" name="cveIdSubdelegacion" value="${subdelegacion.id}" />
					<input type="hidden" id="cveIdFraccionAct" name="cveIdFraccionAct" value="${clasificacion.fraccion.id}" />
<%-- 					<input type="hidden" id="primaSRTAct" name="primaSRTAct" value="${clasificacion.fraccion.primaSRT}" /> --%>
					<input type="hidden" id="primaSRTAct" name="primaSRTAct" value="${foto.primaDec}"/>
					<input type="hidden" id="cveIdFraccionPro" name="cveIdFraccionPro" value="${clasificacionPropuesta.fraccion.id}" />
					<input type="hidden" id="primaSRTPro" name="primaSRTPro" value="${clasificacionPropuesta.fraccion.primaSRT}" />
					<input type="hidden" id="cveIdFraccionAnt" name="cveIdFraccionAnt" value="${clasificacionAnterior.fraccion.id}" />
<%-- 					<input type="hidden" id="primaSRTAnt" name="primaSRTAnt" value="${clasificacionAnterior.fraccion.primaSRT}" /> --%>
					<input type="hidden" id="primaSRTAnt" name="primaSRTAnt" value="${foto.primaAnt}" /> 
					<input type="hidden" id="cveIdPatronDictamen" name="cveIdPatronDictamen" value="${cveIdPatronDictamen}" />
					<fieldset class="fsInterno">
						<label class="mwide">Comentarios:</label>
						<textarea id="comentarios" name="comentarios" cols="72" rows="5" maxlength="2500"></textarea>
					</fieldset>
				</form>
			</div>	

			<form id="autorizaRatificacionForm" action="<%=request.getContextPath()%>/analisis/ratificarSolicitud/autorizar" >
				<input type="hidden" id="cveIdAnalisis" name="cveIdAnalisis" value="${analisis.cveIdAnalisis}" />
				<input type="hidden" id="cveIdSolicitud" name="cveIdSolicitud" value="${idSolicitud}" />
				<input type="hidden" id="regPatronal" name="regPatronal" value="${regPatronal}" />
				<input type="hidden" id="tipoPersona" name="tipoPersona" value="${tipoPersona}" />
				<input type="hidden" id="cveIdDelegacion" name="cveIdDelegacion" value="${delegacion.id}" />
				<input type="hidden" id="cveIdSubdelegacion" name="cveIdSubdelegacion" value="${subdelegacion.id}" />
				<input type="hidden" id="cveIdFraccionAct" name="cveIdFraccionAct" value="${clasificacion.fraccion.id}" />
<%-- 				<input type="hidden" id="primaSRTAct" name="primaSRTAct" value="${clasificacion.fraccion.primaSRT}" /> --%>
				<input type="hidden" id="primaSRTAct" name="primaSRTAct" value="${foto.primaDec}"/>
				<input type="hidden" id="cveIdFraccionPro" name="cveIdFraccionPro" value="${clasificacionPropuesta.fraccion.id}" />
				<input type="hidden" id="primaSRTPro" name="primaSRTPro" value="${clasificacionPropuesta.fraccion.primaSRT}" />
				<input type="hidden" id="cveIdFraccionAnt" name="cveIdFraccionAnt" value="${clasificacionAnterior.fraccion.id}" />
<%-- 				<input type="hidden" id="primaSRTAnt" name="primaSRTAnt" value="${clasificacionAnterior.fraccion.primaSRT}" />	 --%>
				<input type="hidden" id="primaSRTAnt" name="primaSRTAnt" value="${foto.primaAnt}" /> 
				<input type="hidden" id="classe" name="classe" value="${foto.fraccionActual.clase.clave}" />
				<input type="hidden" id="giro" name="giro" value="${clasificacion.giro}" />
				<input type="hidden" id="ciz" name="ciz" value="${sujetoObligado.subdelegacion.delegacion.ciz}"/>
				<input type="hidden" id="cveNumDelegacion" name="cveNumDelegacion" value="${delegacion.clave}" />
				<input type="hidden" id="cveNumSubdelegacion" name="cveNumSubdelegacion" value="${subdelegacion.clave}" />
				<input type="hidden" id="cveIdPatronDictamen" name="cveIdPatronDictamen" value="${cveIdPatronDictamen}" />
			</form>

			<div id="dialog-form-1" title="Rechazar Ratificaci&oacute;n Pendiente" style="visibility:hidden" >
				<form id="rechazarForm1" name="rechazarForm" action="<%=request.getContextPath()%>/analisis/rechazarAnalisis/rechazarRatificacionPendAut">
		    		<input type="hidden" id="cveIdSolicitud" name="cveIdSolicitud" value="${idSolicitud}" />
					<input type="hidden" id="cveIdAnalisis" name="cveIdAnalisis" value="${analisis.cveIdAnalisis}" />
					<input type="hidden" id="regPatronal" name="regPatronal" value="${regPatronal}" />
					<input type="hidden" id="tipoPersona" name="tipoPersona" value="${tipoPersona}" />
					<input type="hidden" id="cveIdDelegacion" name="cveIdDelegacion" value="${delegacion.id}" />
					<input type="hidden" id="cveIdSubdelegacion" name="cveIdSubdelegacion" value="${subdelegacion.id}" />
					<input type="hidden" id="cveIdFraccionAct" name="cveIdFraccionAct" value="${clasificacion.fraccion.id}" />
<%-- 					<input type="hidden" id="primaSRTAct" name="primaSRTAct" value="${clasificacion.fraccion.primaSRT}" /> --%>
					<input type="hidden" id="primaSRTAct" name="primaSRTAct" value="${foto.primaDec}"/>
					<input type="hidden" id="cveIdFraccionPro" name="cveIdFraccionPro" value="${clasificacionPropuesta.fraccion.id}" />
					<input type="hidden" id="primaSRTPro" name="primaSRTPro" value="${clasificacionPropuesta.fraccion.primaSRT}" />
					<input type="hidden" id="cveIdFraccionAnt" name="cveIdFraccionAnt" value="${clasificacionAnterior.fraccion.id}" />
<%-- 					<input type="hidden" id="primaSRTAnt" name="primaSRTAnt" value="${clasificacionAnterior.fraccion.primaSRT}" /> --%>
					<input type="hidden" id="primaSRTAnt" name="primaSRTAnt" value="${foto.primaAnt}" /> 
					<input type="hidden" id="cveIdPatronDictamen" name="cveIdPatronDictamen" value="${cveIdPatronDictamen}" />			
					<fieldset class="fsInterno">
						<label class="mwide">Comentarios:</label>
						<textarea id="comentarios" name="comentarios" cols="72" rows="5" maxlength="2500"></textarea>
					</fieldset>
				</form>
			</div>

			<div id="dialog-form-modificacion" title="Modificar Autorizaci&oacute;n" style="visibility:hidden" >
				<form id="modificacionForm" name="modificacionForm" 
						action="<%=request.getContextPath()%>/analisis/rechazarAnalisis/rechazarAutorizacion" >
					<input type="hidden" id="cveIdSolicitud" name="cveIdSolicitud" value="${idSolicitud}" />
					<input type="hidden" id="cveIdAnalisis" name ="cveIdAnalisis" value="${analisis.cveIdAnalisis}"/>
					<input type="hidden" id="idEstatus" name ="idEstatus" value="${analisis.cveIdEstatus}"/>
					<input type="hidden" id="regPatronal" name="regPatronal" value="${regPatronal}" />
					<input type="hidden" id="tipoPersona" name="tipoPersona" value="${tipoPersona}" />
					<input type="hidden" id="cveIdDelegacion" name="cveIdDelegacion" value="${delegacion.id}" />
					<input type="hidden" id="cveIdSubdelegacion" name="cveIdSubdelegacion" value="${subdelegacion.id}" />
					<input type="hidden" id="cveIdFraccionAct" name="cveIdFraccionAct" value="${clasificacion.fraccion.id}" />
<%-- 					<input type="hidden" id="primaSRTAct" name="primaSRTAct" value="${clasificacion.fraccion.primaSRT}" /> --%>
					<input type="hidden" id="primaSRTAct" name="primaSRTAct" value="${foto.primaDec}"/>
					<input type="hidden" id="cveIdFraccionPro" name="cveIdFraccionPro" value="${clasificacionPropuesta.fraccion.id}" />
					<input type="hidden" id="primaSRTPro" name="primaSRTPro" value="${clasificacionPropuesta.fraccion.primaSRT}" />
					<input type="hidden" id="cveIdFraccionAnt" name="cveIdFraccionAnt" value="${clasificacionAnterior.fraccion.id}" />
<%-- 					<input type="hidden" id="primaSRTAnt" name="primaSRTAnt" value="${clasificacionAnterior.fraccion.primaSRT}" /> --%>
					<input type="hidden" id="primaSRTAnt" name="primaSRTAnt" value="${foto.primaAnt}" /> 
					<input type="hidden" id="tTramite" name="tTramite" value="${tTramite}"/>
					<input type="hidden" id="cveNumSubdelegacion" name="cveNumSubdelegacion" value="${subdelegacion.clave}" />
					<input type="hidden" id="cveIdPatronDictamen" name="cveIdPatronDictamen" value="${cveIdPatronDictamen}" />
					
					<fieldset class="fsInterno">
						<label class="mwide">Comentarios:</label>
						<textarea id="comentarios" name="comentarios" cols="72" rows="5" maxlength="2500"></textarea>
					</fieldset>							
				</form>
			</div>

			<form id="desecharForm" action="<%=request.getContextPath()%>/desechar/${analisis.cveIdAnalisis}/inicio" method="post">
				<input type="hidden" id="regPatronal" name="regPatronal" value="${regPatronal}"/>
				<input type="hidden" id="cveIdSolicitud" name="cveIdSolicitud" value="${idSolicitud}"/>
				<input type="hidden" id="popUp" name="popUp" value="1"/>
				<input type="hidden" id="cveIdDelegacion" name="cveIdDelegacion" value="${delegacion.id}"/>
				<input type="hidden" id="cveIdSubdelegacion" name="cveIdSubdelegacion" value="${subdelegacion.id}"/>
				<input type="hidden" id="cveIdFraccionPro" name="cveIdFraccionPro" value="${clasificacionPropuesta.fraccion.id}"/>
				<input type="hidden" id="primaSRTPro" name="primaSRTPro" value="${clasificacionPropuesta.fraccion.primaSRT}"/>
				<input type="hidden" id="idTipoTramite" name="idTipoTramite" value="${tramite.tipoTramite.idTipoTramite}"/>
				<input type="hidden" id="cveIdFraccionAct" name="cveIdFraccionAct" value="${clasificacion.fraccion.id}"/>
<%-- 				<input type="hidden" id="primaSRTAct" name="primaSRTAct" value="${clasificacion.fraccion.primaSRT}"/>  --%>
				<input type="hidden" id="primaSRTAct" name="primaSRTAct" value="${foto.primaDec}"/>
				<input type="hidden" id="cveIdFraccionAnt" name="cveIdFraccionAnt" value="${clasificacionAnterior.fraccion.id}"/>
<%-- 				<input type="hidden" id="primaSRTAnt" name="primaSRTAnt" value="${clasificacionAnterior.fraccion.primaSRT}"/> --%>
				<input type="hidden" id="primaSRTAnt" name="primaSRTAnt" value="${foto.primaAnt}" /> 
			</form>

			<form id="rectificaForm" action="<%=request.getContextPath()%>/rectificacion/${analisis.cveIdAnalisis}/rectificarMovimiento" method="post">
				<input type="hidden" id="regPatronal" name="regPatronal" value="${regPatronal}"/>
				<input type="hidden" id="rfc" name="rfc" value="${persona.rfc}"/>
				<input type="hidden" id="popUp" name="popUp" value="1"/>
				<input type="hidden" id="cveIdDelegacion" name="cveIdDelegacion" value="${delegacion.id}"/>
				<input type="hidden" id="cveIdSubdelegacion" name="cveIdSubdelegacion" value="${subdelegacion.id}"/>
				<input type="hidden" id="cveIdFraccionAct" name="cveIdFraccionAct" value="${clasificacion.fraccion.id}"/>
				<input type="hidden" id="cveIdFraccionPro" name="cveIdFraccionPro" value="${clasificacionPropuesta.fraccion.id}"/>
				<input type="hidden" id="cveIdFraccionAnt" name="cveIdFraccionAnt" value="${clasificacionAnterior.fraccion.id}"/>
<%-- 				<input type="hidden" id="primaSRTAct" name="primaSRTAct" value="${clasificacion.fraccion.primaSRT}"/> --%>
				<input type="hidden" id="primaSRTAct" name="primaSRTAct" value="${foto.primaDec}"/>
				<input type="hidden" id="primaSRTPro" name="primaSRTPro" value="${clasificacionPropuesta.fraccion.primaSRT}"/>
<%-- 				<input type="hidden" id="primaSRTAnt" name="primaSRTAnt" value="${clasificacionAnterior.fraccion.primaSRT}"/> --%>
				<input type="hidden" id="primaSRTAnt" name="primaSRTAnt" value="${foto.primaAnt}" /> 
				<input type="hidden" id=tipoTramite name="tipoTramite" value="${tramite.tipoTramite.idTipoTramite}"/>
				<input type="hidden" id="cveIdPatronDictamen" name="cveIdPatronDictamen" value="${cveIdPatronDictamen}" />
				<input type="hidden" id="cveIdClaseAct" name="cveIdClaseAct" value="${clasificacion.fraccion.clase.clave}" />									
			</form>

			<div id="dialog-form-2" title="Rechazar Rectificaci&oacute;n" style="visibility:hidden" >
				<form id="rechazarForm2" name="rechazarForm2" action="<%=request.getContextPath()%>/analisis/rechazarAnalisis/rechazarRectificacionPendAut" >
		    		<input type="hidden" id="cveIdSolicitud" name ="cveIdSolicitud" value="${idSolicitud}"/>
					<input type="hidden" id="cveIdAnalisis" name ="cveIdAnalisis" value="${analisis.cveIdAnalisis}"/>
					<input type="hidden" id="regPatronal" name="regPatronal" value="${regPatronal}" />
					<input type="hidden" id="tipoPersona" name="tipoPersona" value="${tipoPersona}" />
					<input type="hidden" id="cveIdDelegacion" name="cveIdDelegacion" value="${delegacion.id}"/>
					<input type="hidden" id="cveIdSubdelegacion" name="cveIdSubdelegacion" value="${subdelegacion.id}"/>
					<input type="hidden" id="cveIdFraccionAct" name="cveIdFraccionAct" value="${clasificacion.fraccion.id}" />
<%-- 					<input type="hidden" id="primaSRTAct" name="primaSRTAct" value="${clasificacion.fraccion.primaSRT}" /> --%>
					<input type="hidden" id="primaSRTAct" name="primaSRTAct" value="${foto.primaDec}"/>
					<input type="hidden" id="cveIdFraccionPro" name="cveIdFraccionPro" value="${clasificacionPropuesta.fraccion.id}" />
					<input type="hidden" id="primaSRTPro" name="primaSRTPro" value="${clasificacionPropuesta.fraccion.primaSRT}" />
					<input type="hidden" id="cveIdFraccionAnt" name="cveIdFraccionAnt" value="${clasificacionAnterior.fraccion.id}" />
<%-- 					<input type="hidden" id="primaSRTAnt" name="primaSRTAnt" value="${clasificacionAnterior.fraccion.primaSRT}" />	 --%>
					<input type="hidden" id="primaSRTAnt" name="primaSRTAnt" value="${foto.primaAnt}" /> 
					<input type="hidden" id="cveIdPatronDictamen" name="cveIdPatronDictamen" value="${cveIdPatronDictamen}" />								
					<fieldset class="fsInterno">
						<label class="mwide">Comentarios:</label>
						<textarea id="comentarios" name="comentarios" cols="72" rows="5" maxlength="2500"></textarea>
					</fieldset>
				</form>
			</div>
			
			<form:form modelAttribute="reporteClemBean" id="datosClemForm" name="datosClemForm" method="post">
				<input type="hidden" id="idAnalisis" name="idAnalisis" value="${analisis.cveIdAnalisis}"/>
				<input type="hidden" id="tipoTramite" name="tipoTramite" value="${tramite.tipoTramite.idTipoTramite}"/>
				<input type="hidden" id="psp" name="psp" value="${clasificacion.indPrestaServicioPersonal}"/>
				<input type="hidden" id="fechaTramite" name="fechaTramite" value="<fmt:formatDate value="${analisis.fechaPresentacion}" pattern="dd/MM/yyyy"/>"/>
				<input type="hidden" id="fechaSurteEfecto" name="fechaSurteEfecto" value="<fmt:formatDate value="${analisis.fechaEfecto}" pattern="dd/MM/yyyy"/>"/>
				<input type="hidden" id="subdelegacion" name="subdelegacion" value="${subdelegacion.id}"/>
			</form:form>

<%-- 					
			<form id="verClemForm" action="<%=request.getContextPath()%>/clem/${analisis.cveIdAnalisis}/verClem"></form>
 --%>			
			<form:form modelAttribute="reporteClemBean" id="modificarClemForm" name="modificarClemForm" method="post">
				<input type="hidden" id="tipoTramite" name="tipoTramite" value="${tramite.tipoTramite.idTipoTramite}"/>
				<input type="hidden" id="psp" name="psp" value="${clasificacion.indPrestaServicioPersonal}"/>
				<input type="hidden" id="fechaTramite" name="fechaTramite" value="<fmt:formatDate value="${analisis.fechaPresentacion}" pattern="dd/MM/yyyy"/>"/>
				<input type="hidden" id="fechaSurteEfecto" name="fechaSurteEfecto" value="<fmt:formatDate value="${analisis.fechaEfecto}" pattern="dd/MM/yyyy"/>"/>
				<input type="hidden" id="cveIdDelegacion" name="cveIdDelegacion" value="${delegacion.id}"/>
				<input type="hidden" id="cveIdSubdelegacion" name="cveIdSubdelegacion" value="${subdelegacion.id}"/>
				<input type="hidden" id="tipoPersona" name="tipoPersona" value="${tipoPersona}"/>
				<input type="hidden" id="cveSolicitud" name="cveSolicitud" value="${idSolicitud}"/>
				<input type="hidden" id="insMod" name="insMod" value="${tTramite}"/>
			</form:form>
			
			<form:form modelAttribute="reporteClemBean" id="clemForm" name="clemForm" method="post">
				<input type="hidden" id="tipoTramite" name="tipoTramite" value="${tramite.tipoTramite.idTipoTramite}"/>
				<input type="hidden" id="psp" name="psp" value="${clasificacion.indPrestaServicioPersonal}"/>
				<input type="hidden" id="fechaTramite" name="fechaTramite" value="<fmt:formatDate value="${analisis.fechaPresentacion}" pattern="dd/MM/yyyy"/>"/>
				<input type="hidden" id="fechaSurteEfecto" name="fechaSurteEfecto" value="<fmt:formatDate value="${analisis.fechaEfecto}" pattern="dd/MM/yyyy"/>"/>
				<input type="hidden" id="cveIdDelegacion" name="cveIdDelegacion" value="${delegacion.id}"/>
				<input type="hidden" id="cveIdSubdelegacion" name="cveIdSubdelegacion" value="${subdelegacion.id}"/>
				<input type="hidden" id="tipoPersona" name="tipoPersona" value="${tipoPersona}"/>
				<input type="hidden" id="cveSolicitud" name="cveSolicitud" value="${idSolicitud}"/>
				<input type="hidden" id="insMod" name="insMod" value="${tTramite}"/>
				<c:if test="${clasificacionPropuesta.primaSugerida != null}">
				 	<input type="hidden" id="primaSugerida" name="primaSugerida" value="${clasificacionPropuesta.primaSugerida}"/>
			 	</c:if>
				
			</form:form>
			
			<form id="regresaForm" action="<%=request.getContextPath()%>/analisis/viene/detalle" method="POST">
				<input type="hidden" id="cveIdPatronDictamen" name="cveIdPatronDictamen" value="${cveIdPatronDictamen}" />	
			</form>

<%-- 
			<form action="${mvn.url.firmadigital}/firmaElectronicaWeb/rest/imss/buscarArchivos"
				class="formNotBlock" method="post" target="firmaIframe" id="forma">
				<input type="hidden" name="params"
					value="{&quot;tramite&quot;:&quot;${secuenciaDeNotaria}&quot;}" />
			</form>
			<form action="${mvn.url.firmadigital}/firmaElectronicaWeb/rest/imss/buscarSeguimiento"
				class="formNotBlock" method="post" target="firmaIframeResultantes" id="formaResultantes">
				<input type="hidden" name="params"
					value="{&quot;tramite&quot;:&quot;${secuenciaDeNotaria}&quot;}" />
			</form>
 --%>			
			<form action="${mvn.url.firmadigital}/firmaElectronicaWeb/widget/chfecyn/imss/buscaArchivos"
				class="formNotBlock" method="post" target="firmaIframe" id="forma">
				<input type="hidden" name="params"
					value="{&quot;tramite&quot;:&quot;${secuenciaDeNotaria}&quot;}" />
			</form>
			<form action="${mvn.url.firmadigital}/firmaElectronicaWeb/widget/chfecyn/imss/buscaSeguimiento"
				class="formNotBlock" method="post" target="firmaIframeResultantes" id="formaResultantes">
				<input type="hidden" name="params"
					value="{&quot;tramite&quot;:&quot;${secuenciaDeNotaria}&quot;}" />
			</form>			

</div>					
</div>
</div>				
</div>

<div id="dgError" title="Aviso" >
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 10px 30px 0;"> </span>
		 ${errorRectificacionMsg}
	</p>
	<br/>
	<span id="errorNegocioLabel" class=" hiddenElement error"></span>
</div>