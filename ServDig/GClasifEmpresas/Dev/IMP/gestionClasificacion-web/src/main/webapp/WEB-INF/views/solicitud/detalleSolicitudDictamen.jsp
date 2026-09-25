<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ include file="../general/taglibs.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="/WEB-INF/tag/combo.tld" prefix="combo"%>
<!-- zona de estilos CSS -->
<style type="text/css" media="screen">
textarea {
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

input, textarea, .uneditable-input {
	width: auto;
}

table {
	max-width: 100%;
	background-color: transparent;
	border-collapse: collapse;
	border-spacing: 0;
}

.table-word-wrap-fixed {
	word-wrap: break-word !important;
	table-layout: fixed;
}
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


  function modificarClem(){
	  $.blockUI();
	  document.getElementById('modificarClemForm').action="<%=request.getContextPath()%>/clem/${analisis.cveIdAnalisis}/modificarClem/capturaDatos";
		document.getElementById('modificarClemForm').submit();
	}
</script>


<script>
	$(document).ready(function() {
		$("#verDictamen").click(mostrarDictamen);
	})

	function mostrarDictamen() {
		$("#formPatronDictamen").submit();
	}
</script>

<!--No  Borrar Codigo Google analytics -->
<script type="text/javascript">
	var analizandoDictamen = parseInt('${analizandoDictamen ? 1 : 0}', 10);
	function rectificar() {
		document.getElementById("rectificaForm").submit();
	}
	function rectificarDictamen() {
		document.getElementById("rectificaFormDictamen").submit();
	}

	/* Funcion para desechar tramite  
	 function desechar(){
	 document.getElementById("desecharForm").submit();
	 }
	 */

	$(document)
			.ready(
					function() {
						
						  $('#autorizarRectificacion').click(function autorizarRectificacion(){
							  console.log("TEST")
							  document.getElementById('clemForm').action="<%=request.getContextPath()%>/rectificacion/${analisis.cveIdAnalisis}/autorizarRectificacion/capturaDatosClem/${cveIdPatronDictamen}";
							  $.blockUI();
							  document.getElementById('clemForm').submit();
						  }
						  );
						  

						$("#tabs").tabs();
						
						document.getElementById("cenefa").innerHtml = "Inicio» Detalle de la Solicitud";

						if ($("#menuRepAnalisis").length) {
							document.getElementById('menuRepAnalisis').style.display = 'none';
						}
						if ($("#menuRepBitacora").length) {
							document.getElementById('menuRepBitacora').style.display = 'none';
						}
						if ($("#menuRepConcentrado").length) {
							document.getElementById('menuRepConcentrado').style.display = 'none';
						}
						$.unblockUI();

						oDialogoErrorRectificacion = $('#dgError').dialog({
							autoOpen : false,
							resizable : false,
							height : 240,
							width : 350,
							modal : true,
							buttons : {
								"Aceptar" : function(data) {
									$(this).dialog("close");
								}
							}
						});

						if (document.getElementById('errorRectificacion').value != null
								&& document
										.getElementById('errorRectificacion').value != '') {
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


<c:set var="tramite" value="${tipoTramite}" />
<c:set var="sujetoObligado" value="${sujetoObligado}" />
<c:set var="idSolicitud" value="${idSolicitud}" />
<c:set var="regPatronal" value="${regPatronal}" />
<c:set var="persona" value="${persona}" />
<c:set var="analisis" value="${analisis}" />
<c:set var="tipoPersona" value="${tipoPersona}" />
<c:set var="mensaje" value="${mensaje}" />
<c:set var="configuracion" value="${configuracionCE}" />
<c:set var="bCancelado" value="${bCancelado}" />
<c:set var="lstHistTipoCausa" value="${lstHistTipoCausa}" />
<c:set var="esInscripcionInicial"
	value="<%=session.getAttribute(\"grupoTramite\")%>" />
<c:set var="foto" value="${estatusAnalisisModel}" />



<c:set var="domicilioFiscal" value="${sujetoObligado.domicilioFiscal}" />
<c:set var="centroTrabajo" value="${sujetoObligado.cntroTrabajo}" />
<c:set var="clasificacion" value="${sujetoObligado.clasificacion}" />
<c:set var="productos" value="${sujetoObligado.productos}" />
<c:set var="materiaPrimaMateriales"
	value="${sujetoObligado.materiaPrimaMateriales}" />
<c:set var="equipos" value="${sujetoObligado.equipos}" />
<c:set var="equiposTransporte"
	value="${sujetoObligado.equiposTransporte}" />
<c:set var="proceso" value="${sujetoObligado.proceso}" />
<c:set var="personal" value="${sujetoObligado.personal}" />
<c:set var="bienes" value="${sujetoObligado.bienes}" />
<c:set var="delegacion"
	value="${sujetoObligado.subdelegacion.delegacion}" />
<c:set var="subdelegacion" value="${sujetoObligado.subdelegacion}" />
<c:set var="clasificacionAnterior"
	value="${analisis.clasificacionAnterior}" />
<c:set var="clasificacionPropuesta"
	value="${analisis.clasificacionPropuesta}" />
<c:set var="adjList" value="${adjList}" />

<input type="hidden" id="errorRectificacion"
	value="${errorRectificacionMsg}" />


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
		<!-- 	Empieza el contenido -->
		<div class="page_holder_no_height">
			<div class="post_entry_wide no-border">
				<!-- 			Fecha de presentacion -->
				<div style="text-align: right">
					<span class="etiqueta" style="float: none"><spring:message
							code="label.detalle.fecha.presentacion" />:</span> <span class="dato"
						style="float: none"> <fmt:formatDate
							value="${analisis.fechaPresentacion }" pattern="dd/MM/yyyy" />
					</span>&nbsp; <span class="etiqueta" style="float: none"><spring:message
							code="label.detalle.fecha.surte.efecto" />:</span> <span class="dato"
						style="float: none"><fmt:formatDate
							value="${analisis.fechaEfecto }" pattern="dd/MM/yyyy" /></span>
				</div>

				<!-- 			Termina fecha presentacion -->
				<div class="marcointerno">
					<div class="separadorseccion">
						<spring:message code="label.detalle.datos.generales.titulo"></spring:message>
					</div>
					<div class="cuerpo_seccion" id="cuerpo_datosgenerales">
						<fieldset>
							<legend style="text-aling: center">
								<b><spring:message code="label.detalle.datos.generales"></spring:message></b>
							</legend>
							<!-- 						INICIA RENGLON DATOS GENERALES -->
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
							<!-- 						FINALIZ RENGLON DE DATOS GENERALES -->

							<!-- 						INICIA RENGLON TIPO DE SOCIEDAD -->
							<div class="div-1seccion">
								<span class="etiqueta"><spring:message
										code="label.detalle.tipo.sociedad" />:</span> <span class="dato"
									id="TIPOPERSONA_PATRON"> <c:if
										test="${tipoPersona eq personaMoral}">
									${persona.tipoSociedad.descripcionAbreviada}
								</c:if>
								</span>
							</div>
							<!-- 						FINALIZA RENGLON TIPO DE SOCIEDAD -->

							<!-- 						INCIA RENGLON RAZON SOCIAL -->
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
							<!-- 						FINALIZA RENGLON NOMBRE Y RAZON SOCIAL -->

							<!-- 						INICIA RENGLON PARA EL TIPO DE TRAMITE -->
							<div class="div-1seccion">
								<span class="etiqueta"><spring:message
										code="label.detalle.tipo.modificacion" />:</span> <span class="dato"
									id="TIPO_TRAMITE">${tramite.tipoTramite.descripcion}</span>
							</div>
							<!-- 						TERMINA RENGLON PARA EL TIPO DE TRAMITE -->

						</fieldset>

						<!-- 					INICIA SECCION PARA EL DOMICILIO FISCAL -->
						<div class="div-1seccion fielsetgris">
							<fieldset>
								<legend style="text-aling: center">
									<b><spring:message code="label.detalle.domicilio.fiscal" /></b>
								</legend>

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

								<div class="div-2secciones">
									<span class="etiqueta"><spring:message
											code="label.detalle.entre.calle" />:</span> <span class="dato">${domicilioFiscal.vialidadReferenciaPrimaria.nombre}</span>
								</div>
								<div class="div-2secciones">
									<span class="etiqueta"><spring:message
											code="label.detalle.y.calle" />:</span> <span class="dato">${domicilioFiscal.vialidadReferenciaSecundaria.nombre}</span>
								</div>

								<div class="div-2secciones">
									<span class="etiqueta"><spring:message
											code="label.detalle.colonia" />:</span> <span class="dato">${domicilioFiscal.asentamiento.nombre}</span>
								</div>
								<div class="div-2secciones">
									<span class="etiqueta"><spring:message
											code="label.detalle.localidad" />:</span> <span class="dato">${domicilioFiscal.asentamiento.localidad.nombre}</span>
								</div>

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

								<div class="div-2secciones">
									<span class="etiqueta"><spring:message
											code="label.detalle.codigo.postal" />:</span> <span class="dato">${domicilioFiscal.codigoPostal.codigoPostal}</span>
								</div>

							</fieldset>
							<!-- 						TERMINA SECCION DEL DOMICILIO FISCAL -->
						</div>
						<!-- 						INICIA SECCION PARA EL CENTRO DE TRABAJO -->

						<div class="div-1seccion">
							<fieldset>
								<legend style="text-align: center">
									<b><spring:message code="label.detalle.centro.trabajo" /></b>
								</legend>
								<!-- 								INICIA VALIDACION CENTRO DE TRABAJO -->
								<c:if test="${centroTrabajo.vialidadPrimaria.nombre!=null}">
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

									<div class="div-2secciones">
										<span class="etiqueta"><spring:message
												code="label.detalle.entre.calle" />:</span> <span class="dato">${centroTrabajo.vialidadReferenciaPrimaria.nombre}</span>
									</div>
									<div class="div-2secciones">
										<span class="etiqueta"><spring:message
												code="label.detalle.y.calle" />:</span> <span class="dato">${centroTrabajo.vialidadReferenciaSecundaria.nombre}</span>
									</div>

									<div class="div-2secciones">
										<span class="etiqueta"><spring:message
												code="label.detalle.colonia" />:</span> <span class="dato">${centroTrabajo.asentamiento.nombre}</span>
									</div>
									<div class="div-2secciones">
										<span class="etiqueta"><spring:message
												code="label.detalle.localidad" />:</span> <span class="dato">${centroTrabajo.asentamiento.localidad.nombre}</span>
									</div>

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

									<div class="div-2secciones">
										<span class="etiqueta"><spring:message
												code="label.detalle.codigo.postal" />:</span> <span class="dato">${centroTrabajo.codigoPostal.codigoPostal}</span>
									</div>

								</c:if>
								<!-- 								FINALIZA PRIMERA VALIDACION -->

								<!--                                INICIA SEGUNDA VALIDACION -->
								<c:if test="${centroTrabajo.vialidadPrimaria.nombre==null}">
									<div class="div-1seccion">
										<span class="etiqueta">Domicilio:</span> <span class="dato">${centroTrabajo.descripcion}</span>
									</div>
								</c:if>
							</fieldset>
						</div>

						<c:if test="${analizandoDictamen}">
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
						</c:if>
					</div>
				</div>

				<!-- Inicio de seccion de clasificacion -->
				<div class="marcointerno">
					<div class="separadorseccion">
						<spring:message
							code="label.detalle.clasificacion.actual.titulo.GCE.dictamen" />
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
						<div class="div-2secciones">
							<span class="etiqueta_extra "><spring:message
									code="label.detalle.clase.riesgo" />:</span> <span class="dato">${foto.fraccionActual.clase.descripcion}</span>
						</div>
						<div class="div-2secciones">
							<span class="etiqueta_extra "><spring:message
									code="label.detalle.prima.media.srt" />:</span> <span class="dato">${foto.primaDec}</span>
						</div>
					</div>
				</div>
				<!--  -->
				<c:if test="${esInscripcionInicial ne 1}">
					<c:if test="${foto.fraccionAnterior != null}">
						<div class="marcointerno">
							<div class="separadorseccion">
								<spring:message
									code="label.detalle.clasificacion.anterior.titulo.dictamen" />

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

								<!-- *****************  Inicia Renglon  ********************** -->
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
				<!-- Historico de estatus -->
				<div class="marcointerno">
					<div class="separadorseccion">
						<spring:message code="label.detalle.historico.estatus.titulo" />
					</div>
					<div class="cuerpo_seccion">
						<div>
							<table style="width: 100%"
								style="width: 100%; vertical-align: top;" cellpadding="0"
								cellspacing="0"
								class="table table-striped table-bordered table-word-wrap-fixed">
								<tr>
									<th width="25%"><spring:message
											code="label.detalle.estatus" /></th>
									<th width="40%"><spring:message
											code="label.detalle.observaciones" /></th>
									<th width="20%"><spring:message code="label.detalle.fecha" /></th>
									<th><spring:message code="label.detalle.usuario" /></th>
								</tr>
								<c:forEach var="comentario"
									items="${analisis.comentariosDetalle}">
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
				<!--Historico Omisiones-->
				<div class="marcointerno">
					<div class="separadorseccion">
						Bit&aacute;cora de Omisiones Detectadas
					</div>
					<div class="cuerpo_seccion">
						<div>
							<table style="width: 100%"
								style="width: 100%; vertical-align: top;" cellpadding="0"
								cellspacing="0"
								class="table table-striped table-bordered table-word-wrap-fixed">
								<tr>
									<th width="35%">Omisi&oacute;n</th>
									<th width="40%">Justificaci&oacute;n</th>
									<th width="20%">Fecha analisis</th>
								</tr>
								<c:forEach var="omisiones" items="${analisis.histOmisiones}">
																<tr align="center">
																	<td width="40%">
																		<c:choose>
																			<c:when
																				test="${omisiones.cveIdOmision == '3'}">
																				${omisiones.omision}
																				<br />
																				<c:if test="${omisiones.pago == '1'}">
																					<p>Con pago</p>
																				</c:if>
																				<c:if test="${omisiones.pago == '2'}">
																					<p>Sin pago</p>
																				</c:if>
																			</c:when>
																			<c:when
																				test="${omisiones.cveIdOmision == 2}">
																				${omisiones.omision}
																				<br />
																				${omisiones.fecSurteEfecto}
																			</c:when>
																			<c:otherwise>
																				${omisiones.omision}
																			</c:otherwise>
																		</c:choose>
																	</td>
																	<td width="40%">${omisiones.justificacion}</td>
																	<td width="20%">${omisiones.fecAnalisis}</td>
																</tr>
															</c:forEach>
							</table>
						</div>
					</div>
				</div>

				<!-- Se agrega bloque para pestania de  "descarga de archivos" -->
				<c:if test="${existAdj}">	
					<!-- Documentos adjuntos al tramite -->
					<div class="marcointerno">	
						<div class="separadorseccion">Descarga de archivos</div> <!-- <spring:message code="label.detalle.documentos.adjuntos" /></div> --> 
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


			<c:if test="${analisis.cveIdEstatus == '6' || analisis.cveIdEstatus == '4'}">
											
				<div class="marcointerno">
					<div class="separadorseccion">
						Omision Detectada
					</div>
					<div class="cuerpo_seccion">
						<div>
							<table style="width: 100%"
								style="width: 100%; vertical-align: top;" cellpadding="0"
								cellspacing="0"
								class="table table-striped table-bordered table-word-wrap-fixed">
								<tr>
									<th width="35%">Omisi&oacute;n</th>
									<th width="40%">Justificaci&oacute;n</th>
									<th width="20%">Fecha analisis</th>
								</tr>
								<c:forEach var="omision" items="${analisis.omisionActual}"
																begin="0" end="0">
																<tr align="center">
																	<td width="40%">
																		<c:choose>
																			<c:when
																				test="${omision.cveIdOmision == '3'}">
																				${omision.omision}
																				<br />
																				<c:if test="${omision.pago == '1'}">
																					<p>Con pago</p>
																				</c:if>
																				<c:if test="${omision.pago == '2'}">
																					<p>Sin pago</p>
																				</c:if>
																			</c:when>
																			<c:when
																				test="${omision.cveIdOmision == 2}">
																				${omision.omision}
																				<br />
																				${omision.fecSurteEfecto}
																			</c:when>
																			<c:otherwise>
																				${omision.omision}
																			</c:otherwise>
																		</c:choose>
																	</td>
																	<td width="40%">${omision.justificacion}</td>
																	<td width="20%">${omision.fecAnalisis}</td>
																</tr>
															</c:forEach>
							</table>
						</div>
					</div>
				</div>
			</c:if>
				<!-- Se agrega bloque para pestania de  "descarga de archivos" -->
				
				
				<div class="marcointerno" style="text-align: center;">
					<c:if test="${bReclasificado}">
						<p>
							<font color="#B22222"><b><spring:message
										code="label.detalle.reclasificacion" /></b></font>
						</p>
					</c:if>
					<c:if test="${!analisis.indActivo && bCancelado}">
						<p>
							<font color="#B22222"><b><spring:message
										code="label.detalle.modificacion.no.disponible" /></b></font>
						</p>
					</c:if>

					<p align="center">
						<b> <spring:message
								code="label.detalle.estatus.actual.analisis" /><br>
							${mensaje}<br>
						</b>
					</p>
					<c:if test="${configuracion.boRatificar}">
						<input type="button" class="botonDDiv" id="Ratificar"
							value="Ratificar" />
					</c:if>
					<c:if test="${configuracion.boRectificar}">
						<button type="button" class="botonDDiv" id="RectificarDictamen"
							onclick="rectificarDictamen()">Enviar a revisi&oacute;n</button>
					</c:if>
					<c:if test="${configuracion.boAutoRatificarN1}">
						<input type="button" class="botonDDiv" id="AutorizarRatificacion"
							value="Autorizar Ratificaci&oacute;n" />
					</c:if>
					<c:if test="${configuracion.boRechRatificarN1}">
						<input type="button" class="botonDDiv" id="RechazarRatificacion"
							value="Rechazar la Ratificaci&oacute;n" />
					</c:if>
					<c:if test="${configuracion.boAutoRectificarN1}">
						<button type="button" class="botonDDiv" id="autorizarRectificacion">Autorizar
							Enviar a revisi&oacute;n</button>
					</c:if>
					<c:if test="${configuracion.boRechRectificarN1}">
						<button type="button" class="botonDDiv" id="RechazarRectificacion">
							Rechazar Enviar a revisi&oacute;n</button>
					</c:if>
					<c:if test="${configuracion.boModificarAuto}">
						<input type="button" class="botonDDiv" id="modificarAutorizacion1"
							value="Modificar Autorizaci&oacute;n" />
					</c:if>
					<input type="button" class="botonDDiv" name="btnRegresar"
						id="Regresar" value="Regresar" />
				</div>
				<!--  -->
				<div id="dialog-form-3" title="Ratificaci&oacute;n"
					style="visibility: hidden">
					<form id="ratificaForm"
						action="<%=request.getContextPath()%>/analisis/ratificarSolicitud/dictamen">
						<input type="hidden" id="cveIdAnalisis" name="cveIdAnalisis"
							value="${analisis.cveIdAnalisis}" /> <input type="hidden"
							id="cveIdSolicitud" name="cveIdSolicitud" value="${idSolicitud}" />
						<input type="hidden" id="regPatronal" name="regPatronal"
							value="${regPatronal}" /> <input type="hidden" id="tipoPersona"
							name="tipoPersona" value="${tipoPersona}" /> <input
							type="hidden" id="cveIdDelegacion" name="cveIdDelegacion"
							value="${delegacion.id}" /> <input type="hidden"
							id="cveIdSubdelegacion" name="cveIdSubdelegacion"
							value="${subdelegacion.id}" /> <input type="hidden"
							id="cveIdFraccionAct" name="cveIdFraccionAct"
							value="${clasificacion.fraccion.id}" /> <input type="hidden"
							id="primaSRTAct" name="primaSRTAct"
							value="${clasificacion.fraccion.primaSRT}" /> <input
							type="hidden" id="cveIdFraccionPro" name="cveIdFraccionPro"
							value="${clasificacionPropuesta.fraccion.id}" /> <input
							type="hidden" id="primaSRTPro" name="primaSRTPro"
							value="${clasificacionPropuesta.fraccion.primaSRT}" /> <input
							type="hidden" id="cveIdFraccionAnt" name="cveIdFraccionAnt"
							value="${clasificacionAnterior.fraccion.id}" /> <input
							type="hidden" id="primaSRTAnt" name="primaSRTAnt"
							value="${clasificacionAnterior.fraccion.primaSRT}" /> <input
							type="hidden" id="cveIdPatronDictamen" name="cveIdPatronDictamen"
							value="${cveIdPatronDictamen}" />
						<fieldset class="fsInterno">
							<label class="mwide">Comentarios:</label>
							<textarea id="comentarios" name="comentarios" cols="72" rows="5"
								maxlength="2500"></textarea>
						</fieldset>
					</form>
				</div>

				<form id="autorizaRatificacionForm"
					action="<%=request.getContextPath()%>/analisis/ratificarSolicitud/autorizar/dictamen">
					<input type="hidden" id="cveIdAnalisis" name="cveIdAnalisis"
						value="${analisis.cveIdAnalisis}" /> <input type="hidden"
						id="cveIdSolicitud" name="cveIdSolicitud" value="${idSolicitud}" />
					<input type="hidden" id="regPatronal" name="regPatronal"
						value="${regPatronal}" /> <input type="hidden" id="tipoPersona"
						name="tipoPersona" value="${tipoPersona}" /> <input type="hidden"
						id="cveIdDelegacion" name="cveIdDelegacion"
						value="${delegacion.id}" /> <input type="hidden"
						id="cveIdSubdelegacion" name="cveIdSubdelegacion"
						value="${subdelegacion.id}" /> <input type="hidden"
						id="cveIdFraccionAct" name="cveIdFraccionAct"
						value="${clasificacion.fraccion.id}" /> <input type="hidden"
						id="primaSRTAct" name="primaSRTAct"
						value="${clasificacion.fraccion.primaSRT}" /> <input
						type="hidden" id="cveIdFraccionPro" name="cveIdFraccionPro"
						value="${clasificacionPropuesta.fraccion.id}" /> <input
						type="hidden" id="primaSRTPro" name="primaSRTPro"
						value="${clasificacionPropuesta.fraccion.primaSRT}" /> <input
						type="hidden" id="cveIdFraccionAnt" name="cveIdFraccionAnt"
						value="${clasificacionAnterior.fraccion.id}" /> <input
						type="hidden" id="primaSRTAnt" name="primaSRTAnt"
						value="${clasificacionAnterior.fraccion.primaSRT}" /> <input
						type="hidden" id="classe" name="classe"
						value="${foto.fraccionActual.clase.clave}" /> <input
						type="hidden" id="giro" name="giro" value="${clasificacion.giro}" />
					<input type="hidden" id="ciz" name="ciz"
						value="${sujetoObligado.subdelegacion.delegacion.ciz}" /> <input
						type="hidden" id="cveNumDelegacion" name="cveNumDelegacion"
						value="${delegacion.clave}" /> <input type="hidden"
						id="cveNumSubdelegacion" name="cveNumSubdelegacion"
						value="${subdelegacion.clave}" /> <input type="hidden"
						id="cveIdPatronDictamen" name="cveIdPatronDictamen"
						value="${cveIdPatronDictamen}" />
				</form>

				<div id="dialog-form-1"
					title="Rechazar Ratificaci&oacute;n Pendiente"
					style="visibility: hidden">
					<form id="rechazarForm1" name="rechazarForm"
						action="<%=request.getContextPath()%>/analisis/rechazarAnalisis/dictamen/rechazarRatificacionPendAut">
						<input type="hidden" id="cveIdSolicitud" name="cveIdSolicitud"
							value="${idSolicitud}" /> <input type="hidden"
							id="cveIdAnalisis" name="cveIdAnalisis"
							value="${analisis.cveIdAnalisis}" /> <input type="hidden"
							id="regPatronal" name="regPatronal" value="${regPatronal}" /> <input
							type="hidden" id="tipoPersona" name="tipoPersona"
							value="${tipoPersona}" /> <input type="hidden"
							id="cveIdDelegacion" name="cveIdDelegacion"
							value="${delegacion.id}" /> <input type="hidden"
							id="cveIdSubdelegacion" name="cveIdSubdelegacion"
							value="${subdelegacion.id}" /> <input type="hidden"
							id="cveIdFraccionAct" name="cveIdFraccionAct"
							value="${clasificacion.fraccion.id}" /> <input type="hidden"
							id="primaSRTAct" name="primaSRTAct"
							value="${clasificacion.fraccion.primaSRT}" /> <input
							type="hidden" id="cveIdFraccionPro" name="cveIdFraccionPro"
							value="${clasificacionPropuesta.fraccion.id}" /> <input
							type="hidden" id="primaSRTPro" name="primaSRTPro"
							value="${clasificacionPropuesta.fraccion.primaSRT}" /> <input
							type="hidden" id="cveIdFraccionAnt" name="cveIdFraccionAnt"
							value="${clasificacionAnterior.fraccion.id}" /> <input
							type="hidden" id="primaSRTAnt" name="primaSRTAnt"
							value="${clasificacionAnterior.fraccion.primaSRT}" /> <input
							type="hidden" id="cveIdPatronDictamen" name="cveIdPatronDictamen"
							value="${cveIdPatronDictamen}" />
						<fieldset class="fsInterno">
							<label class="mwide">Comentarios:</label>
							<textarea id="comentarios" name="comentarios" cols="72" rows="5"
								maxlength="2500"></textarea>
						</fieldset>
					</form>
				</div>
				<div id="dialog-form-modificacion"
					title="Modificar Autorizaci&oacute;n" style="visibility: hidden">
					<form id="modificacionForm" name="modificacionForm"
						action="<%=request.getContextPath()%>/analisis/rechazarAnalisis/dictamen/rechazarAutorizacion">
						<input type="hidden" id="cveIdSolicitud" name="cveIdSolicitud"
							value="${idSolicitud}" /> <input type="hidden"
							id="cveIdAnalisis" name="cveIdAnalisis"
							value="${analisis.cveIdAnalisis}" /> <input type="hidden"
							id="idEstatus" name="idEstatus" value="${analisis.cveIdEstatus}" />
						<input type="hidden" id="regPatronal" name="regPatronal"
							value="${regPatronal}" /> <input type="hidden" id="tipoPersona"
							name="tipoPersona" value="${tipoPersona}" /> <input
							type="hidden" id="cveIdDelegacion" name="cveIdDelegacion"
							value="${delegacion.id}" /> <input type="hidden"
							id="cveIdSubdelegacion" name="cveIdSubdelegacion"
							value="${subdelegacion.id}" /> <input type="hidden"
							id="cveIdFraccionAct" name="cveIdFraccionAct"
							value="${clasificacion.fraccion.id}" /> <input type="hidden"
							id="primaSRTAct" name="primaSRTAct"
							value="${clasificacion.fraccion.primaSRT}" /> <input
							type="hidden" id="cveIdFraccionPro" name="cveIdFraccionPro"
							value="${clasificacionPropuesta.fraccion.id}" /> <input
							type="hidden" id="primaSRTPro" name="primaSRTPro"
							value="${clasificacionPropuesta.fraccion.primaSRT}" /> <input
							type="hidden" id="cveIdFraccionAnt" name="cveIdFraccionAnt"
							value="${clasificacionAnterior.fraccion.id}" /> <input
							type="hidden" id="primaSRTAnt" name="primaSRTAnt"
							value="${clasificacionAnterior.fraccion.primaSRT}" /> <input
							type="hidden" id="tTramite" name="tTramite" value="${tTramite}" />
						<input type="hidden" id="cveNumSubdelegacion"
							name="cveNumSubdelegacion" value="${subdelegacion.clave}" /> <input
							type="hidden" id="cveIdPatronDictamen" name="cveIdPatronDictamen"
							value="${cveIdPatronDictamen}" />

						<fieldset class="fsInterno">
							<label class="mwide">Comentarios:</label>
							<textarea id="comentarios" name="comentarios" cols="72" rows="5"
								maxlength="2500"></textarea>
						</fieldset>
					</form>
				</div>
				<form id="rectificaFormDictamen"
					action="<%=request.getContextPath()%>/rectificacion/${analisis.cveIdAnalisis}/rectificarMovimiento/omisiondetectada"
					method="post">
					<input type="hidden" id="regPatronal" name="regPatronal"
						value="${regPatronal}" /> <input type="hidden" id="rfc"
						name="rfc" value="${persona.rfc}" /> <input type="hidden"
						id="popUp" name="popUp" value="1" /> <input type="hidden"
						id="cveIdDelegacion" name="cveIdDelegacion"
						value="${delegacion.id}" /> <input type="hidden"
						id="cveIdSubdelegacion" name="cveIdSubdelegacion"
						value="${subdelegacion.id}" /> <input type="hidden"
						id="cveIdFraccionAct" name="cveIdFraccionAct"
						value="${clasificacion.fraccion.id}" /> <input type="hidden"
						id="cveIdFraccionPro" name="cveIdFraccionPro"
						value="${clasificacionPropuesta.fraccion.id}" /> <input
						type="hidden" id="cveIdFraccionAnt" name="cveIdFraccionAnt"
						value="${clasificacionAnterior.fraccion.id}" /> <input
						type="hidden" id="primaSRTAct" name="primaSRTAct"
						value="${clasificacion.fraccion.primaSRT}" /> <input
						type="hidden" id="primaSRTPro" name="primaSRTPro"
						value="${clasificacionPropuesta.fraccion.primaSRT}" /> <input
						type="hidden" id="primaSRTAnt" name="primaSRTAnt"
						value="${clasificacionAnterior.fraccion.primaSRT}" /> <input
						type="hidden" id="cveIdPatronDictamen" name="cveIdPatronDictamen"
						value="${cveIdPatronDictamen}" />
						<input type="hidden" id="ejercicio" name="ejercicio" value="${ejercicio}" />
				</form>

				<div id="dialog-form-2" title="Rechazar Rectificaci&oacute;n"
					style="visibility: hidden">
					<form id="rechazarForm2" name="rechazarForm2"
						action="<%=request.getContextPath()%>/analisis/rechazarAnalisis/rechazarRectificacionDictamenPendAut">
						<input type="hidden" id="cveIdSolicitud" name="cveIdSolicitud"
							value="${idSolicitud}" /> <input type="hidden"
							id="cveIdAnalisis" name="cveIdAnalisis"
							value="${analisis.cveIdAnalisis}" /> <input type="hidden"
							id="regPatronal" name="regPatronal" value="${regPatronal}" /> <input
							type="hidden" id="tipoPersona" name="tipoPersona"
							value="${tipoPersona}" /> <input type="hidden"
							id="cveIdDelegacion" name="cveIdDelegacion"
							value="${delegacion.id}" /> <input type="hidden"
							id="cveIdSubdelegacion" name="cveIdSubdelegacion"
							value="${subdelegacion.id}" /> <input type="hidden"
							id="cveIdFraccionAct" name="cveIdFraccionAct"
							value="${clasificacion.fraccion.id}" /> <input type="hidden"
							id="primaSRTAct" name="primaSRTAct"
							value="${clasificacion.fraccion.primaSRT}" /> <input
							type="hidden" id="cveIdFraccionPro" name="cveIdFraccionPro"
							value="${clasificacionPropuesta.fraccion.id}" /> <input
							type="hidden" id="primaSRTPro" name="primaSRTPro"
							value="${clasificacionPropuesta.fraccion.primaSRT}" /> <input
							type="hidden" id="cveIdFraccionAnt" name="cveIdFraccionAnt"
							value="${clasificacionAnterior.fraccion.id}" /> <input
							type="hidden" id="primaSRTAnt" name="primaSRTAnt"
							value="${clasificacionAnterior.fraccion.primaSRT}" /> <input
							type="hidden" id="cveIdPatronDictamen" name="cveIdPatronDictamen"
							value="${cveIdPatronDictamen}" />
						<fieldset class="fsInterno">
							<label class="mwide">Comentarios:</label>
							<textarea id="comentarios" name="comentarios" cols="72" rows="5"
								maxlength="2500"></textarea>
						</fieldset>
					</form>
				</div>
				<form:form modelAttribute="reporteClemBean" id="modificarClemForm"
					name="modificarClemForm" method="post">
					<input type="hidden" id="tipoTramite" name="tipoTramite"
						value="${tramite.tipoTramite.idTipoTramite}" />
					<input type="hidden" id="psp" name="psp"
						value="${clasificacion.indPrestaServicioPersonal}" />
					<input type="hidden" id="fechaTramite" name="fechaTramite"
						value="<fmt:formatDate value="${analisis.fechaPresentacion}" pattern="dd/MM/yyyy"/>" />
					<input type="hidden" id="fechaSurteEfecto" name="fechaSurteEfecto"
						value="<fmt:formatDate value="${analisis.fechaEfecto}" pattern="dd/MM/yyyy"/>" />
					<input type="hidden" id="cveIdDelegacion" name="cveIdDelegacion"
						value="${delegacion.id}" />
					<input type="hidden" id="cveIdSubdelegacion"
						name="cveIdSubdelegacion" value="${subdelegacion.id}" />
					<input type="hidden" id="tipoPersona" name="tipoPersona"
						value="${tipoPersona}" />
					<input type="hidden" id="cveSolicitud" name="cveSolicitud"
						value="${idSolicitud}" />
					<input type="hidden" id="insMod" name="insMod" value="${tTramite}" />
				</form:form>

				<form:form modelAttribute="reporteClemBean" id="clemForm"
					name="clemForm" method="post">
					<input type="hidden" id="tipoTramite" name="tipoTramite"
						value="${tramite.tipoTramite.idTipoTramite}" />
					<input type="hidden" id="psp" name="psp"
						value="${clasificacion.indPrestaServicioPersonal}" />
					<input type="hidden" id="fechaTramite" name="fechaTramite"
						value="<fmt:formatDate value="${analisis.fechaPresentacion}" pattern="dd/MM/yyyy"/>" />
					<input type="hidden" id="fechaSurteEfecto" name="fechaSurteEfecto"
						value="<fmt:formatDate value="${analisis.fechaEfecto}" pattern="dd/MM/yyyy"/>" />
					<input type="hidden" id="cveIdDelegacion" name="cveIdDelegacion"
						value="${delegacion.id}" />
					<input type="hidden" id="cveIdSubdelegacion"
						name="cveIdSubdelegacion" value="${subdelegacion.id}" />
					<input type="hidden" id="tipoPersona" name="tipoPersona"
						value="${tipoPersona}" />
					<input type="hidden" id="cveSolicitud" name="cveSolicitud"
						value="${idSolicitud}" />
					<input type="hidden" id="insMod" name="insMod" value="${tTramite}" />
				</form:form>


				<form id="regresaForm"
					action="<%=request.getContextPath()%>/analisis/viene/detalle"
					method="POST">
					<input type="hidden" id="cveIdPatronDictamen"
						name="cveIdPatronDictamen" value="${cveIdPatronDictamen}" />
				</form>
				<form
					action="${mvn.url.firmadigital}/firmaElectronicaWeb/widget/chfecyn/imss/buscaArchivos"
					class="formNotBlock" method="post" target="firmaIframe" id="forma">
					<input type="hidden" name="params"
						value="{&quot;tramite&quot;:&quot;${secuenciaDeNotaria}&quot;}" />
				</form>
				<form
					action="${mvn.url.firmadigital}/firmaElectronicaWeb/widget/chfecyn/imss/buscaSeguimiento"
					class="formNotBlock" method="post" target="firmaIframeResultantes"
					id="formaResultantes">
					<input type="hidden" name="params"
						value="{&quot;tramite&quot;:&quot;${secuenciaDeNotaria}&quot;}" />
				</form>

			</div>
		</div>
	</div>
</div>