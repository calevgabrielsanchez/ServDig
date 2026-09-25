<%@ include file="../general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.TipoContenedorEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.PortalContextEnum"%>

<c:set var="portlet" value="<%=TipoContenedorEnum.PORTLET.getId()%>" />
<c:set var="widget" value="<%=TipoContenedorEnum.WIDGET.getId()%>" />
<c:set var="menu" value="<%=TipoContenedorEnum.MENU.getId()%>" />
<c:set var="widgetTramite" value="<%=TipoContenedorEnum.WIDGET_TRAMITE.getId()%>" />
<c:set var="widgetPersona" value="<%=TipoContenedorEnum.WIDGET_PERSONA.getId()%>" />
<c:set var="widgetPersonaAsegurado" value="<%=TipoContenedorEnum.WIDGET_PERSONA_ASEGURADO.getId()%>" />
<c:set var="widgetPersonaDerechohabiente" value="<%=TipoContenedorEnum.WIDGET_PERSONA_DERECHOHABIENTE.getId()%>" />
<c:set var="emptyState" value="<%=TipoContenedorEnum.EMPTY_STATE.getId()%>" />
<c:set var="individuo" value="<%=PortalContextEnum.INDIVIDUO.getId()%>" />
<c:set var="empresa" value="<%=PortalContextEnum.EMPRESA.getId()%>" />
<c:set var="patron" value="<%=PortalContextEnum.PATRONAL.getId()%>" />
<c:set var="asegurado" value="<%=PortalContextEnum.ASEGURADO.getId()%>" />

<c:if test="${not empty opciones}">

<c:choose>
	<c:when test="${idTipoContenedor == portlet}">
		<!--Verificamos si el portlet es el de patrones-->
		<c:if test="${idContenedor == 1}">
			<c:if test="${(reqTipoPM == '2' and opciones.ind_reg_patron_pm =='true')
					or (opciones.ind_reg_patron== 'true')}">
				<li class="removable">
					<a id="registroAltaPatronal">
						<spring:message code="label.button.inciarRegistroPatronal" />
					</a>
				</li>
			</c:if>
			<c:if test="${opciones.ind_rec_patron}">
				<li>
					<a id="recuperarPatron"> 
						<spring:message code="label.button.recuperarRegistroPatronal" />
					</a>
				</li>
			</c:if>
			<c:if test="${opciones.ind_reg_mov_afil}">
				<li>
					<a id="registroMovimientosAfiliatorios"> 
						<spring:message code="label.button.registroMovimientosAfiliatorios" />
					</a>
				</li>
			</c:if>
			
			
			<c:if test="${!opciones.ind_reg_patron && !opciones.ind_rec_patron}">
				<li>
					<%@ include file="sinOpciones.jsp" %>
				</li>
			</c:if>
		</c:if>
		<!-- Verificamos si el portlet es el de representantes legales -->
		<c:if test="${idContenedor == 2}">
			<c:if test="${opciones.ind_baja_repte}">
				<li>
					<a id="bajaRepresentante">
						<spring:message code="label.button.eliminarRepresentanteLegal" />
					</a>
				</li>
			</c:if>
			<c:if test="${!opciones.ind_baja_repte}">
				<li>
					<%@ include file="sinOpciones.jsp" %>
				</li>
			</c:if>
		</c:if>
		<!-- Verificamos si el portlet es el de representados -->
		<c:if test="${idContenedor == 3}">
			<c:if test="${opciones.ind_reg_repdo}">
				<li>
					<a id="registrarRPL"> 
						<spring:message code="label.button.registrarEmpresaRepresentada"/>
					</a>
				</li>
			</c:if>
			<c:if test="${opciones.ind_baja_repdo}">
				<li>
					<a id="bajaRPL"> 
						<spring:message code="label.button.eliminarEmpresaRepresentada"/>
					</a>
				</li>
			</c:if>
			<c:if test="${!opciones.ind_reg_repdo && !opciones.ind_baja_repdo}">
				<li>
					<%@ include file="sinOpciones.jsp" %>
				</li>
			</c:if>
		</c:if>
		<!-- Verificamos si es el portlet de clasificacion -->
		<c:if test="${idContenedor == 4}">
			<c:if test="${opciones.ind_mod_srt}">
				<li>
					<a id="modificarClasificacionPatron">
						<spring:message code="label.button.modificacionSRT" />
					</a>
				</li>
			</c:if>
			<c:if test="${!opciones.ind_mod_srt}">
				<li>
					<%@ include file="sinOpciones.jsp" %>
				</li>
			</c:if>
		</c:if>
		<!-- Personas Autorizadas -->
		<c:if test="${idContenedor == 8}">
			<c:if test="${opciones.ind_personas_aut}">
				<li><a id="registrarPersonaAutorizada">
					<spring:message code="label.portlet.titulo.personasAutorizadas.registrarPersona" /></a></li>
				<li class="removable"><a id="eliminarPersonaAutorizada">
					<spring:message code="label.portlet.titulo.personasAutorizadas.eliminarPersona" /></a></li>
			</c:if>
			<c:if test="${!opciones.ind_personas_aut}">
				<li>
					<%@ include file="sinOpciones.jsp" %>
				</li>
			</c:if>
		</c:if>
		<!-- Socios -->
		<c:if test="${idContenedor == 9}">
			<c:if test="${opciones.ind_socios_pm}">
				<li><a id="registrarSocio">
					<spring:message code="label.portlet.titulo.socios.registrarSocios" /></a></li>
				<!--  li class="removable"><a id="eliminarSocios">	
					<spring:message code="label.portlet.titulo.socios.eliminarSocios" /></a></li> -->
			</c:if>
			<c:if test="${!opciones.ind_socios_pm}">
				<li>
					<%@ include file="sinOpciones.jsp" %>
				</li>
			</c:if>
		</c:if>		

		<!-- IVRO - SEGURO DOMESTICO -->
		<c:if test="${idContenedor == 10}">
				<li><a id="ivroAltaSeguroDomestico">
					<spring:message code="label.portlet.titulo.ivro.altaSeguroDomestico" /></a></li>
				<!-- li>
					<%@ include file="sinOpciones.jsp" %></li -->
		</c:if>		
	</c:when>
	
	<c:when test="${idTipoContenedor == widget}">
		<c:if test="${idContenedor == 3}"> <!-- Verificamos si el widget es el de patron -->
			<c:if test="${opciones.ind_cambio_dom_patron}">
				<li>
					<a id="editarDomicilioCentroTrabajo">
						<spring:message code="label.button.cambioDomicilioPatron" />
					</a>
				</li>
			</c:if>
			<c:if test="${opciones.ind_medios_patron}">
				<li>
					<a id="editarDatosContactoCentroTrabajo">
						<spring:message code="label.button.cambioMediosPatron" />
					</a>
				</li>
			</c:if>
			<c:if test="${!opciones.ind_cambio_dom_patron && !opciones.ind_medios_patron}">
				<li>
					<%@ include file="sinOpciones.jsp" %>
				</li>
			</c:if>
		</c:if>
		<c:if test="${idContenedor == 4}"><!-- Verificamos si el widget es el de estado de adeudo -->
			<c:if test="${opciones.ind_edo_cta_imss}">
				<li>
					<a id="reporteMot">
						<spring:message code="label.button.edoAdeudoImss" />
					</a>
				</li>
			</c:if>
			<c:if test="${opciones.ind_edo_cta_rcv}">
				<li>
					<a id="reporteMotRCV">
						<spring:message code="label.button.edoAdeudoRCV" />
					</a>
				</li>
			</c:if>
			<c:if test="${!opciones.ind_edo_cta_imss && !opciones.ind_edo_cta_rcv}">
				<li>
					<%@ include file="sinOpciones.jsp" %>
				</li>
			</c:if>
		</c:if>
		<c:if test="${idContenedor == 5}"><!-- Verificamos si el widget es el de beneficios -->
			<c:if test="${opciones.ind_rif}">
				<li><a id="solicitarRif">Solicitar RISS</a></li>
			</c:if>
			
			<c:if test="${!opciones.ind_rif}">
				<li>
					<%@ include file="sinOpciones.jsp" %>
				</li>
			</c:if>
		</c:if>
		
		<c:if test="${idContenedor == 6}"><!-- Verificamos si el widget es el adscripcion y vigencia-->
			<c:if test="${opciones.ind_registro_derechohabiente}">
				<li><a id="iniciarRegistroBeneficiario"> Registro de derechohabiente</a></li>
			</c:if>
			<c:if test="${opciones.ind_baja_defuncion}">
				<li><a id="iniciarBajaDefuncion"> Baja por defunci&oacute;n</a></li>
			</c:if>
			<c:if test="${opciones.ind_correccion_datos_dhabiente}">
				<li><a id="iniciarCorreccionDatosDHabiente"> Correcci&oacute;n de datos del derechohabiente</a></li>
			</c:if>
			<c:if test="${opciones.ind_baja_dependencia}">
				<li><a id="iniciarBajaDependencia"> Baja por termino de dependencia</a></li>
			</c:if>
			<c:if test="${opciones.ind_baja_concubina}">
				<li><a id="iniciarBajaConcubinato"> Baja por termino de concubinato</a></li>
			</c:if>
			<c:if test="${opciones.ind_baja_divorcio}">
				<li><a id="iniciarBajaUnionCivil"> Baja por t\u00E9rmino de uni&oacute;nn civil</a></li>
			</c:if>
			<c:if test="${opciones.ind_baja_divorcio}">
				<li><a id="iniciarBajaDivorcio"> Baja por divorcio</a></li>
			</c:if>
			<c:if test="${opciones.ind_baja_divorcio}">
				<li><a id="iniciarBajaUnionCivil"> Baja por t&eacute;rmino de uni&oacute;n civil</a></li>
			</c:if>
			
			<!-- Inicio de prorrogas -->
			<c:if test="${opciones.ind_prorroga_permanente}">
				<li><a id="iniciarProrrogaPermanente"> Prorroga por vigencia permanente</a></li>
			</c:if>
			<c:if test="${opciones.ind_prorroga_acuerdo}">
				<li><a id="iniciarProrrogaAcuerdo"> Prorroga por acuerdo</a></li>
			</c:if>
			<c:if test="${opciones.ind_prorroga_obstetrica}">
				<li><a id="iniciarProrrogaObstetrica"> Prorroga por incapacidad obst&eacute;trica</a></li>
			</c:if>
			<c:if test="${opciones.ind_prorrga_fisica_psiquica}">
				<li><a id="iniciarProrrogaFisica"> Prorroga por incapacidad f&iacute;sica o ps&iacute;quica</a></li>
			</c:if>
			<c:if test="${opciones.ind_prorroga_estudios}">
				<li><a id="iniciarProrrogaEstudios"> Prorroga por estudios</a></li>
			</c:if>
			<c:if test="${opciones.ind_prorrga_laudo}">
				<li><a id="iniciarProrrogaLaudo"> Prorroga por laudo</a></li>
			</c:if>
			<c:if test="${opciones.ind_prorroga_temporal}">
				<li><a id="iniciarProrrogaTemporal"> Prorroga por vigencia permanente</a></li>
			</c:if>
			<c:if test="${opciones.ind_cambio_domicilio}">
				<li><a id="iniciarCambioDomicilio"> Cambio de domicilio</a></li>
			</c:if>
			<c:if test="${opciones.ind_cambio_clinica}">
				<li><a id="iniciarCambioClinica"> Cambio de cl&iacute;nica</a></li>
			</c:if>
			
			<c:if test="${!opciones.ind_registro_derechohabiente && !opciones.ind_baja_defuncion && !opciones.ind_correccion_datos_dhabiente && !opciones.ind_baja_dependencia && opciones.ind_baja_concubina && opciones.ind_baja_divorcio 
			&& !opciones.ind_baja_union_civil && opciones.ind_prorroga_permanente && opciones.ind_prorroga_acuerdo && opciones.ind_prorroga_obstetrica && opciones.ind_prorrga_fisica_psiquica
			&& opciones.ind_prorroga_estudios && opciones.ind_prorrga_laudo && opciones.ind_prorroga_temporal}">
				<li>
					<%@ include file="sinOpciones.jsp" %>
				</li>
			</c:if>
		</c:if>
		
		<c:if test="${idContenedor == 7}"><!-- Verificamos si el widget es el de Comprobante Fiscal-->
			<c:if test="${opciones.ind_comprobante_fiscal}">
				<li><a id="obtenerComprobanteFiscal">Obtener comprobantes fiscales</a></li>
			</c:if>
			
			<c:if test="${!opciones.ind_comprobante_fiscal}">
				<li>
					<%@ include file="sinOpciones.jsp" %>
				</li>
			</c:if>
		</c:if>

		<c:if test="${idContenedor == 8}"><!-- Verificamos si el widget es el de Alta IVRO-->
			<c:if test="${opciones.ind_alta_seguro_voluntario}">
				<li><a id="ivroAltaSeguroDomestico"><spring:message code="label.portlet.titulo.ivro.altaSeguroDomestico" /></a></li>
			</c:if>

			<c:if test="${!opciones.ind_alta_seguro_voluntario}">
				<li>
					<%@ include file="sinOpciones.jsp" %>
				</li>
			</c:if>
		</c:if>
		
		<c:if test="${idContenedor == 9}"><!-- Verificamos si el widget es DATOS FISCALES-->
			<c:if test="${opciones.ind_carta_no_adeudo}">
				<li><a id="solicitudCartaNoAdeudo">Opini&oacute;n de cumplimiento</a></li>		
			</c:if>
			<c:if test="${opciones.ind_descarga_cfdi}">
				<li><a id="obtenerComprobanteFiscalPorRFC">Obtener comprobantes fiscales</a></li>		
			</c:if>
			
			<c:if test="${!opciones.ind_carta_no_adeudo && !opciones.ind_descarga_cfdi}">
				<li>
					<%@ include file="sinOpciones.jsp" %>
				</li>
			</c:if>
		</c:if>
		
		<c:if test="${idContenedor == 10}"><!-- Verificamos si el widget de vigencia-->
			<c:if test="${opciones.ind_detalle_vigencia}">
				<li id="abrirAseguradoli"><a id="abrirAsegurado">Ver detalle de vigencia</a></li>
				<c:if test="${opciones.ind_nmps_asegurado}">
					<li id="abrirlinmpsClabe"><a id="abrirnmpsClabe">Registra/Actualiza Cuenta CLABE</a></li>
					<li id="abrirlinmpsMovimientos"><a id="abrirnmpsMovimientos">Hist&oacute;rico de movimientos</a></li>
					<li id="abrirlinmpsEstatusPago"><a id="abrirnmpsEstatusPago">Estatus de pago</a></li>
				</c:if>				
			</c:if>
			
			
			<c:if test="${!opciones.ind_detalle_vigencia}">
				<li>
					<%@ include file="sinOpciones.jsp" %>
				</li>
			</c:if>
			
			<c:if test="${opciones.ind_nmps_asegurado && !opciones.ind_detalle_vigencia}">
					<li id="abrirlinmpsClabe"><a id="abrirnmpsClabe">Registra/Actualiza Cuenta CLABE</a></li>
					<li id="abrirlinmpsMovimientos"><a id="abrirnmpsMovimientos">Hist&oacute;rico de movimientos</a></li>
					<li id="abrirlinmpsEstatusPago"><a id="abrirnmpsEstatusPago">Estatus de pago</a></li>
				</c:if>
			</c:if>
		
	</c:when>
	<c:when test="${idTipoContenedor == menu}">
	
	</c:when>
	<c:when test="${idTipoContenedor == widgetPersona}">
		<c:if test="${opciones.ind_datos_per}">
			<li>
				<c:if test="${idContenedor eq 1 }">
					<a id="editarPersonaFisica">
				</c:if> 
				<c:if test="${idContenedor eq 2 }">
					<a id="editarPersonaMoral">
				</c:if>
				<c:choose>
					<c:when test="${idPersonaTercero eq '0'}">Actualizar datos personales</c:when>
					<c:otherwise>Actualizar Datos Particulares</c:otherwise>
				</c:choose> 
				</a>
			</li>
		</c:if>
		<c:if test="${opciones.ind_cambio_dom && muestraDomicilio}">
		<li>
			<a id="editarDomicilio">
				Actualizar domicilio 
			</a>
		</li>
		</c:if>
		<c:if test="${opciones.ind_medios_con && muestraMedios}">
			<li><a id="editarMedios">
				Administrar medios de contacto
				</a>
			</li>
		</c:if>
		
		<c:if test="${!opciones.ind_datos_per && (!opciones.ind_cambio_dom || !muestraDomicilio) && (!opciones.ind_medios_con || !muestraMedios)}">
			<li>
				<%@ include file="sinOpciones.jsp" %>
			</li>
		</c:if>
	</c:when>
	


	<c:when test="${idTipoContenedor == widgetPersonaAsegurado}">
		<c:if test="${opciones.ind_cambio_dom && muestraDomicilio}">
		<li>
			<a id="editarDomicilio">
				Actualizar domicilio 
			</a>
		</li>
		</c:if>
		<c:if test="${opciones.ind_medios_con && muestraMedios}">
			<li><a id="editarMedios">
				Administrar medios de contacto
				</a>
			</li>
		</c:if>
		
		<c:if test="${(!opciones.ind_cambio_dom || !muestraDomicilio) && (!opciones.ind_medios_con || !muestraMedios)}">
			<li>
				<%@ include file="sinOpciones.jsp" %>
			</li>
		</c:if>
	</c:when>
	
	<c:when test="${idTipoContenedor == widgetPersonaDerechohabiente}">
		<c:if test="${opciones.ind_cambio_dom && muestraDomicilio}">
		<li>
			<a id="editarDomicilio">
				Actualizar domicilio 
			</a>
		</li>
		</c:if>
		<c:if test="${opciones.ind_medios_con && muestraMedios}">
			<li><a id="editarMedios">
				Administrar medios de contacto
				</a>
			</li>
		</c:if>
		
		<c:if test="${(!opciones.ind_cambio_dom || !muestraDomicilio) && (!opciones.ind_medios_con || !muestraMedios)}">
			<li>
				<%@ include file="sinOpciones.jsp" %>
			</li>
		</c:if>
	</c:when>
	
		<c:when test="${idTipoContenedor == emptyState }">
			<!--Verificamos si el portlet es el de patrones-->
			<c:if test="${idContenedor == 1 && (opciones.ind_reg_patron || opciones.ind_rec_patron) }">
				<div class="row opciones">
					<c:choose>
						<c:when test="${opciones.ind_reg_patron && opciones.ind_rec_patron}">
							<c:set var="cssRowClass" value="col-xs-6"/>
						</c:when>
						<c:otherwise>
							<c:set var="cssRowClass" value="col-xs-12"/>
						</c:otherwise>	
					</c:choose>
					
					<c:if test="${opciones.ind_reg_patron}">
						<!-- Opcion -->
						<div class="${cssRowClass}">
								<div class=" opcion">
								<div class="">
									<i class="glyphicon glyphicon-plus-sign"></i>
								</div>
								<div class="">
									<p class="nombre" id="registroAltaPatronal">
										<spring:message code="label.button.inciarRegistroPatronal" />
									</p>
									<p class="descripcion">
										<spring:message
											code="label.portlet.desc.iniciarRegistroPatronal" />
									</p>
								</div>
							</div>
						</div>
					</c:if>
					<c:if test="${opciones.ind_rec_patron}">
						<!-- Opcion -->
						<div class="${cssRowClass} ">
		
							<div class=" opcion">
		
								<div class="">
									<i class="fa fa-exchange"></i>
								</div>
								<div class="">
									<p class="nombre" id="recuperarPatron">
										<spring:message code="label.button.recuperarRegistroPatronal" />
									</p>
									<p class="descripcion">
										<spring:message
											code="label.portlet.desc.recuperarRegistroPatronal" />
									</p>
								</div>
		
							</div>
						</div>
					</c:if>
				</div>
				<!-- Notas  -->
				<div class="row notas">
					<div class="col-xs-12">
						<span class="nota"> <spring:message
								code="label.portlet.emptystate.patrones.nota" /> <i
							class="fa fa-share fa-rotate-90"></i>
						</span>
					</div>
				</div>
			</c:if>
			<c:if test="${idContenedor == 2 && opciones.ind_reg_repdo}">
				<div class="row opciones">
					<!-- Opcion -->
					<div class="col-xs-12">
						<div class=" opcion">
							<div class="">
								<i class="glyphicon glyphicon-plus-sign"></i>
							</div>
							<div class="">
								<p class="nombre" id="registroEmpresaRepresentada">
									<spring:message
										code="label.button.registrarEmpresaRepresentada" />
								</p>
								<p class="descripcion">
									<spring:message
										code="label.portlet.desc.iniciarRegistroEmpresaRepresentada" />
								</p>
							</div>
						</div>
					</div>
				</div>
				
				<!-- Notas  -->
				<div class="row notas">
					<div class="col-xs-12">
						<span class="nota"> <spring:message
								code="label.portlet.emptystate.patrones.nota" /> <i
							class="fa fa-share fa-rotate-90"></i>
						</span>
					</div>
				</div>
			</c:if>
		</c:when>
</c:choose>
</c:if>
