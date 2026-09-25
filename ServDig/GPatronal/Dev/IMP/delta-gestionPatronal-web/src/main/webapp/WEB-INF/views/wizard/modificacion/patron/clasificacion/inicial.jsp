<%@ include file="../../../../general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum"%>

<style>
	#selectable .ui-selecting {
		background: #1A79A7;
		color: white;
	}
	
	#selectable .ui-selected {
		background: #428BCA;
		color: white;
	}
	
	#selectable {
		list-style-type: none;
		margin: 0;
		padding: 0;
		width: 100%;
	}
	
	#selectable li {
		margin: 3px;
		padding: 0.4em;
		color: #67666A;
	}

	a:active {
		outline: none;
	}
	
	a:focus {
		-moz-outline-style: none;
	}
	
	.icono-help {
	    font-size: 20px;
	}
</style>

<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/modificacion/patron/clasificacion/inicial.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/common/commonMethods.js" htmlEscape="true" />"></script>

<input type="hidden" id="hdnIdSolicitud" value="${idSolicitud}" />
<input type="hidden" id="hdnIdTipoTramite" value="${idTipoTramite}" />

<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="introduccion col-sm-4">
			<div class="titulo separadorseccion">
				<span> <spring:message code="label.wizard.modSRT.titulo" /></span>
			</div>

			<div class="descripcion">
				<p>	<spring:message code="label.wizard.modSRT.descripcion" /></p>
			</div>

			<div class="opciones">
					<c:if test="${modalidadValida}">
						<c:if test="${!existenEnProceso}">
							<c:choose>
								<c:when test="${!existenPendientes}">
									<button onclick="uid_call('imss.gestion.patronal.modificaciones.srt.btn_iniciarTramite','clickin');"
										class="btn btn-primary btn-block"
										role="button" aria-disabled="false" id="btnInciaTramite">
										<span class="ui-button-text"><spring:message code="label.boton.tramite.iniciar" /></span>
									</button>
								</c:when>
								<c:otherwise>
									<button
										class="btn btn-primary btn-block" onclick="uid_call('imss.gestion.patronal.modificaciones.srt.btn_retomarTramite','clickin');"
										role="button" aria-disabled="false" id="btnRetomarTramite">
										<span class="ui-button-text"><spring:message code="label.boton.tramite.retomar" /></span>
									</button>
									<button
										class="btn btn-danger btn-block" onclick="uid_call('imss.gestion.patronal.modificaciones.srt.btn_cancelarTramite','clickout');"
										role="button" aria-disabled="false" id="btnCancelarTramite">
										<span class="ui-button-text"><spring:message code="label.boton.tramite.cancelar.proceso" /></span>
									</button>
								</c:otherwise>
							</c:choose>
						</c:if>
					</c:if>
					
					<button
						class="btn btn-default btn-block" onclick="uid_call('imss.gestion.patronal.modificaciones.srt.btn_cancelarTramite','clickout');"
						role="button" aria-disabled="false" id="btnInicioCancelarTramite">
						<span class="ui-button-text"><spring:message code="label.boton.tramite.cancelar" /></span>
					</button>
				
			</div>
		</div>

		<div class="instrucciones col-sm-8">
			
			<c:choose>
				<c:when test="${existenPendientes}">
					<div class="alert alert-info">
						Ya cuentas con una solicitud para <strong>MODIFICACI&Oacute;N
							DE CLASIFICACI&Oacute;N EN EL SRT</strong> en proceso.<br>
							Folio de solicitud: ${folioSolicitud}<br>
							Tipo de Tr&aacute;mite: ${desTipoTramiteVigente}
					</div>

					<c:if test="${existeMsjBuzon}">
						<div class="alert alert-info">${msjBuzon}</div>
					</c:if>

					<ul>
						<li>
							<p align="justify">
								<spring:message code="label.wizard.modSRT.explicacion"/>							
							</p>
						</li>
						<li>
							<p align="justify">
								Antes de iniciar tu tr&aacute;mite se recomienda tener preparados los datos 
								y la informaci&oacute;n que vas ingresar esto con la finalidad de hacer m&aacute;s 
								&aacute;gil tu tr&aacute;mite en l&iacute;nea
							</p>
						</li>
					</ul>
				</c:when>
				<c:when test="${existenEnProceso}">
					<div class="alert alert-warning">
						Existe un tr&aacute;mite de <strong>MODIFICACI&Oacute;N
							DE CLASIFICACI&Oacute;N EN EL SRT</strong> en curso, espera a su
						conclusi&oacute;n para iniciar un nuevo tr&aacute;mite.
					</div>
					<c:if test="${existeMsjBuzon}">
						<div class="alert alert-info">${msjBuzon}</div>
					</c:if>

					<ul>
						<li>
							<p align="justify">
								<spring:message code="label.wizard.modSRT.explicacion"/>
							</p>
						</li>
						<li>
							<p align="justify">
								Antes de iniciar tu tr&aacute;mite se recomienda tener preparados los datos 
								y la informaci&oacute;n que vas ingresar esto con la finalidad de hacer m&aacute;s 
								&aacute;gil tu tr&aacute;mite en l&iacute;nea
							</p>
						</li>
					</ul>
				</c:when>
				<c:when test="${!modalidadValida}">
					<ul>
						<li>
							<p><b>El registro patronal capturado o seleccionado, no cuenta con una modalidad de aseguramiento v&aacute;lida, 
							por lo que no se podr&aacute;n realizar tr&aacute;mites de modificaci&oacute;n en el SRT para dicho registro</b></p>
						</li>
					</ul>				
				</c:when>		
				<c:otherwise>
					<c:if test="${existeMsjBuzon}">
						<div class="alert alert-info">${msjBuzon}</div>
					</c:if>
				
					<ul>
						<li>
							<p align="justify">
								<spring:message code="label.wizard.modSRT.explicacion"/>
							</p>
						</li>
						
						<li>
							<p><b>Para poder continuar es necesario seleccionar el tipo de modificaci&oacute;n que deseas realizar</b></p>
						</li>
					</ul>
					
					<div class="well" style="background-color: white;">
						<div id="listaTramitesClasificacion" title="Tr&aacute;mites">
							<ol id="selectable">
								<c:choose>
								<c:when test="${(not empty sujetoTramite.descSituacionBaja)}">
									<!--  
									<li class="ui-widget-content" 
										onclick="uid_call('imss.gestion.patronal.modificaciones.srt.lst_tramite','clickin');" id="<%=TipoTramiteEnum.REANUDACION_DE_ACTIVIDADES.getCodigo()%>">Reanudaci&oacute;n de actividades
										<span class="delta-tooltip icono-help" data-toggle="tooltip" 
										title="Es el aviso que debe presentar el patr&oacute;n, sujeto obligado o representante legal en caso de reanudaci&oacute;n de actividades que modifiquen los datos del registro patronal proporcionados al Instituto." ></span>
									</li>
									 -->
									 La situaci&oacute;n actual del patr&oacute;n no permite realizar ning&uacute;n tipo de tr&aacute;mite. 
									
								</c:when>
								<c:otherwise>
									<li class="ui-widget-content" 
										onclick="uid_call('imss.gestion.patronal.modificaciones.srt.lst_tramite','clickin');" id="<%=TipoTramiteEnum.ARRENDAMIENTO.getCodigo()%>">Arrendamiento
										<span class="delta-tooltip icono-help" data-toggle="tooltip" 
										title="Es el aviso que debe presentar el patr&oacute;n, sujeto obligado o representante legal en caso de arrendamiento que modifiquen los datos del registro patronal proporcionados al Instituto." ></span>
									</li>										
									<li class="ui-widget-content"
										onclick="uid_call('imss.gestion.patronal.modificaciones.srt.lst_tramite','clickin');" id="<%=TipoTramiteEnum.ACTIVIDAD_ECONOMICA.getCodigo()%>">Cambio de actividad
										<span class="delta-tooltip icono-help" data-toggle="tooltip" 
										title="Es el aviso que debe presentar el patr&oacute;n, sujeto obligado o representante legal en cualquier circunstancia que modifique la actividad de su empresa conforme a los datos del registro patronal proporcionados al Instituto." ></span>
									</li>
									<li class="ui-widget-content" 
										onclick="uid_call('imss.gestion.patronal.modificaciones.srt.lst_tramite','clickin');" id="<%=TipoTramiteEnum.CAMBIO_DE_DOMICILIO_DIFERENTE_MUNICIPIO.getCodigo()%>">Cambio de domicilio en diferente municipio
										<span class="delta-tooltip icono-help" data-toggle="tooltip" 
										title="Este tr&aacute;mite lo debe presentar el patr&oacute;n, sujeto obligado o representante legal, en el registro patronal del nuevo domicilio, el domicilio que se presenta al momento del tr&aacute;mite es el correspondiente al alta del mismo y no se podr&aacute; actualizar en el tr&aacute;mite. Es responsabilidad del patrón realizar el tr&aacute;mite de BAJA del registro patronal del domicilio anterior." ></span>									
									</li>
									
									<li class="ui-widget-content" 
										onclick="uid_call('imss.gestion.patronal.modificaciones.srt.lst_tramite','clickin');" id="<%=TipoTramiteEnum.DISPOSICION_DE_LEY.getCodigo()%>">Cambio por disposici&oacute;n de la ley o RACERF
										<span class="delta-tooltip icono-help" data-toggle="tooltip" 
										title="Es el aviso que debe presentar el patr&oacute;n, sujeto obligado o representante legal en caso de cambio por disposici&oacute;n de la ley o RACERF que modifiquen los datos del registro patronal proporcionados al Instituto." ></span>
									</li>									
									<li class="ui-widget-content" 
										onclick="uid_call('imss.gestion.patronal.modificaciones.srt.lst_tramite','clickin');" id="<%=TipoTramiteEnum.COMODATO.getCodigo()%>">Comodato
										<span class="delta-tooltip icono-help" data-toggle="tooltip" 
										title="Es el aviso que debe presentar el patr&oacute;n, sujeto obligado o representante legal en caso de comodato que modifiquen los datos del registro patronal proporcionados al Instituto." ></span>
									</li>
									<li class="ui-widget-content" 
										onclick="uid_call('imss.gestion.patronal.modificaciones.srt.lst_tramite','clickin');" id="<%=TipoTramiteEnum.COMPRA_DE_ACTIVOS.getCodigo()%>">Compra de activos
										<span class="delta-tooltip icono-help" data-toggle="tooltip" 
										title="Es el aviso que debe presentar el patr&oacute;n, sujeto obligado o representante legal en caso de compra de activos que modifiquen los datos del registro patronal proporcionados al Instituto." ></span>
									</li>
									<li class="ui-widget-content" 
										onclick="uid_call('imss.gestion.patronal.modificaciones.srt.lst_tramite','clickin');" id="<%=TipoTramiteEnum.ENAJENACION.getCodigo()%>">Enajenaci&oacute;n
										<span class="delta-tooltip icono-help" data-toggle="tooltip" 
										title="Es el aviso que debe presentar el patr&oacute;n, sujeto obligado o representante legal en caso de enajenaci&oacute;n que modifiquen los datos del registro patronal proporcionados al Instituto." ></span>
									</li>
									<li class="ui-widget-content" 
										onclick="uid_call('imss.gestion.patronal.modificaciones.srt.lst_tramite','clickin');" id="<%=TipoTramiteEnum.ESCISION.getCodigo()%>">Escisi&oacute;n
										<span class="delta-tooltip icono-help" data-toggle="tooltip" 
										title="Es el aviso que debe presentar el patr&oacute;n, sujeto obligado o representante legal en caso de escisi&oacute;n que modifiquen los datos del registro patronal proporcionados al Instituto." ></span>
									</li>					
									<li class="ui-widget-content" 
										onclick="uid_call('imss.gestion.patronal.modificaciones.srt.lst_tramite','clickin');" id="<%=TipoTramiteEnum.FIDEICOMISO_TRASLATIVO.getCodigo()%>">Fideicomiso traslativo
										<span class="delta-tooltip icono-help" data-toggle="tooltip" 
										title="Es el aviso que debe presentar el patr&oacute;n, sujeto obligado o representante legal en caso de fideicomiso traslativo que modifiquen los datos del registro patronal proporcionados al Instituto." ></span>
									</li>
									<li class="ui-widget-content" 
										onclick="uid_call('imss.gestion.patronal.modificaciones.srt.lst_tramite','clickin');" id="<%=TipoTramiteEnum.FUSION.getCodigo()%>">Fusi&oacute;n
										<span class="delta-tooltip icono-help" data-toggle="tooltip" 
										title="Es el aviso que debe presentar el patr&oacute;n, sujeto obligado o representante legal en caso de fusi&oacute;n que modifiquen los datos del registro patronal proporcionados al Instituto." ></span>
									</li>
									<li class="ui-widget-content" 
										onclick="uid_call('imss.gestion.patronal.modificaciones.srt.lst_tramite','clickin');" id="<%=TipoTramiteEnum.INCORPORACION_DE_ACTIVIDADES.getCodigo()%>">Incorporaci&oacute;n de actividades
										<span class="delta-tooltip icono-help" data-toggle="tooltip" 
										title="Es el aviso que debe presentar el patr&oacute;n, sujeto obligado o representante legal en caso de incorporar actividades y/o realice una sustituci&oacute;n patronal que modifiquen los datos del registro patronal proporcionados al Instituto." ></span>
									</li>																		
									<li class="ui-widget-content" 
										onclick="uid_call('imss.gestion.patronal.modificaciones.srt.lst_tramite','clickin');" id="<%=TipoTramiteEnum.SUSTITUCION_PATRONAL.getCodigo()%>">Sustituci&oacute;n
										<span class="delta-tooltip icono-help" data-toggle="tooltip" 
										title="Es el aviso que debe presentar el patr&oacute;n, sujeto obligado o representante legal en caso de sustituci&oacute;n que modifiquen los datos del registro patronal proporcionados al Instituto." ></span>
									</li>
<!-- Se quita tramite de sustitucion por subcontratacion Mm 4488685 / WO1677899 -->	
<!-- Se habilita de nuevo opcion a solicitud de usuario normativo solo en Stage 4608937/WO1732714 -->
<!-- Se envia cambio para deshabilitar tramite solo en Stage 4608937/WO1732714 -->							
<%-- 								
									<li class="ui-widget-content" 
										onclick="uid_call('imss.gestion.patronal.modificaciones.srt.lst_tramite','clickin');" id="<%=TipoTramiteEnum.SUSTITUCION_PATRONAL_SUBCONTRATACION.getCodigo()%>">Sustituci&oacute;n por Subcontrataci&oacute;n
										<span class="delta-tooltip icono-help" data-toggle="tooltip" 
										title="Es el aviso que debe presentar el patr&oacute;n, sujeto obligado o representante legal en caso de sustituci&oacute;n patronal por subcontrataci&oacute;n que modifiquen los datos del registro patronal proporcionados al Instituto." ></span>
									</li>
 --%>
								</c:otherwise>
								</c:choose>
							</ol>
						</div>

						<div id="dialogoMensajes">
							<p><span id="textoMensaje"></span></p>
						</div>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>

	<div class="pie row">
		<div class="controles"></div>
	</div>
</div>

<!-- Forma para invocar al servicio del Modificación Manual de Datos -->
<form:form modelAttribute="sujetoTramite" id="modificacionClasificacionForm" method="post">
	<form:hidden path="numeroRegistroPatronal" id="hdnClasifNumeroRegistroPatronal"/>
</form:form>

<!-- Divs para dialogos de mensajes -->
<div id="dialog-confirm-cancelar" title="Confirmar cancelaci&oacute;n de solicitud">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>
			¿Deseas cancelar la solicitud pendiente con folio: <strong>${folioSolicitud}</strong>?
	</p>
</div>

<div id="dialog-confirm" title="Mensaje confirmaci&oacute;n">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>
			<label id="mensajeDialogo"></label>
	</p>
</div>
