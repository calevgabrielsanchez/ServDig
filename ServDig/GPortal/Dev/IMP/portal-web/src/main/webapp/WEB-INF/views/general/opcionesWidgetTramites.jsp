<%@ include file="../general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.PortalContextEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum"%>

<c:set var="asegurado" value="<%=ParentescoEnum.ASEGURADO.getId()%>"></c:set>
<c:set var="padres" value="<%=ParentescoEnum.PADRES.getId()%>"></c:set>
<c:set var="conyuge" value="<%=ParentescoEnum.CONYUGE.getId()%>"></c:set>
<c:set var="concubina" value="<%=ParentescoEnum.CONCUBINARIO.getId()%>"></c:set>
<c:set var="hijos" value="<%=ParentescoEnum.HIJOS.getId()%>"></c:set>
<c:set var="pensionado" value="<%=ParentescoEnum.PENSIONADO.getId()%>"></c:set>
<c:set var="unionCivil" value="<%=ParentescoEnum.PERSONA_EN_UNION_CIVIL.getId()%>"></c:set>


<c:set var="individuo" value="<%=PortalContextEnum.INDIVIDUO.getId()%>" />
<c:set var="empresa" value="<%=PortalContextEnum.EMPRESA.getId()%>" />
<c:set var="patron" value="<%=PortalContextEnum.PATRONAL.getId()%>" />
<c:set var="asegurado" value="<%=PortalContextEnum.ASEGURADO.getId()%>" />
<c:set var="derechohabiente" value="<%=PortalContextEnum.DERECHOHABIENTE.getId()%>" />

<c:if test="${not empty opciones}">
	<c:if test="${idTipoContenedor eq 4 }">
	<ul class="nuevosTramites">
	</c:if>
		<!-- Si el contenedor es el widget de tramites -->
		<c:if test="${idContenedor eq individuo }">
			
				<c:if test="${opciones.ind_reg_patron}">
					<li><a id="registroAltaPatronal">Alta de registro patronal
					</a></li>
				</c:if>

				<c:if test="${opciones.ind_rec_patron}">
				<li><a id="recuperarPatron">Recuperar registro patronal </a></li>
				</c:if>
				
				<c:if test="${opciones.ind_baja_repte}">
				<li><a id="bajaRepresentante" href="#">Eliminar representante legal</a></li>
				</c:if>
				
				<c:if test="${opciones.ind_reg_repdo}">
				<li><a id="registrarRPL">Registro de empresa representada
				</a></li>
				</c:if>
				
				<c:if test="${opciones.ind_baja_repdo}">
				<li><a id="bajaRPL">Eliminar empresa representada</a></li>
				</c:if>
				
				<c:if test="${opciones.ind_datos_per}">
				<li><a id="editarPersonaFisica">Actualizar datos personales </a></li>
				</c:if>
				
				<c:if test="${opciones.ind_cambio_dom && muestraDomicilio}">
				<li><a id="editarDomicilio">Actualizar domicilio </a></li>
				</c:if>

				<c:if test="${opciones.ind_medios_con && muestraMedios}">
					<li><a id="editarMedios">Actualizar medios de contacto </a></li>
				</c:if>
				
				<c:if test="${opciones.ind_rif}">
					<li><a id="solicitarRif">Solicitar RISS</a></li>
				</c:if>
				
				<c:if test="${!opciones.ind_reg_patron && !opciones.ind_rec_patron && !opciones.ind_baja_repte && !opciones.ind_reg_repdo && !opciones.ind_baja_repdo && !opciones.ind_datos_per && !opciones.ind_cambio_dom && !opciones.ind_medios_con && !opciones.ind_rif}">
					<li>
						<%@ include file="sinOpciones.jsp" %>
					</li>
				</c:if>
				
				<c:if test="${opciones.ind_alta_seguro_voluntario}">
					<li><a id="ivroAltaSeguroDomestico"><spring:message code="label.portlet.titulo.ivro.altaSeguroDomestico" /></a></li>
				</c:if>
<!--
				<c:if test="${opciones.ind_solicitud_cartilla}">
					<li><a id="imprimirCartilla">Reimpresi&oacute;n de cartilla nacional de salud</a></li>
				</c:if>
-->				
		</c:if>

		<!-- Menu de tipos de tramite por portal empresa -->
		<c:if test="${idContenedor eq empresa }">
				<c:if test="${opciones.ind_datos_per}">
				<c:choose>
					<c:when test="${tipoPersona eq 2}">
						<li><a id="editarPersonaMoral"> Actualizar datos particulares </a></li>
					</c:when>
					<c:otherwise>
						<li><a id="editarPersonaFisica"> Actualizar datos particulares </a></li>
					</c:otherwise>
				</c:choose>
				</c:if>

				<c:if test="${opciones.ind_baja_repte}">
				<li><a id="bajaRepresentante" href="#">Eliminar representante legal</a></li>
				</c:if>
				
				<c:if test="${opciones.ind_rec_patron}">
				<li><a id="recuperarPatron"> Recuperar registro patronal</a></li>
				</c:if>

				<c:if test="${opciones.ind_descarga_cfdi}">
				<li><a id="obtenerComprobanteFiscalPorRFC">Obtener comprobantes fiscales</a></li>		
				</c:if>
		</c:if>

		<!-- Menu de tipos de tramite por portal patronal -->
		<c:if test="${idContenedor eq patron}">
				<c:if test="${opciones.ind_mod_srt}">
					<li><a id="modificarClasificacionPatron">Actualizar clasificaci&oacute;n </a></li>
				</c:if>
				<c:if test="${opciones.ind_cambio_dom_patron}">
					<li><a id="editarDomicilioCentroTrabajo">Actualiza centro de trabajo </a></li>
				</c:if>
				
				<c:if test="${opciones.ind_comprobante_fiscal}">
					<li><a id="obtenerComprobanteFiscal">Obtener comprobantes fiscales</a></li>
				</c:if>
				<c:if test="${opciones.ind_riesgo_trabajo_terminado}">
					<li><a id="entrarRiesgoTrabajo">Consultar riesgos de trabajo terminados</a></li>
				</c:if>
				<c:if test="${opciones.ind_riesgo_trabajo_terminado_rfc}">
					<li><a id="entrarRiesgoTrabajoRfc">Consultar riesgos de trabajo terminados por RFC</a></li>
				</c:if>
				
				<c:if test="${opciones.ind_escrito_des}">
					<li><a id="registrarEscritoDesacuerdo">Presentaci&oacute;n de Escrito de desacuerdo</a></li>
				</c:if>
				
				<c:if test="${opciones.ind_solicitud_convenio}">
					<li><a id="idLnkSolicitudConvenio">
						<spring:message code="label.portlet.button.convenios.registro" /></a></li>
				</c:if>
				<c:if test="${opciones.ind_consulta_convenio}">
					<li><a id="idLnkConsultaConvenio">
						<spring:message	code="label.portlet.button.convenios.busqueda" /></a></li>
				</c:if>
				<c:if test="${opciones.ind_aviso_convenio}">
					<li><a id="idLnkAvisoConvenio">
						<spring:message	code="label.portlet.button.convenios.avisos" /></a></li>
				</c:if>
				<c:if test="${idPatronPlataforma eq 1 || idPatronListaBlanca eq 1 }">
					<c:if test="${opciones.ind_sist_acceso_dev}">
						<li><a  id="idLnkSISTAccesoDev" >
							<spring:message	code="label.portlet.button.sist.acceso.dev" /></a></li>
						<li><a id="idLnkSISTAccesoQa" >
							<spring:message	code="label.portlet.button.sist.acceso.qa" /></a></li>
						<li><a id="idLnkSISTAccesoUat">
							<spring:message	code="label.portlet.button.sist.acceso.uat" /></a></li>
					</c:if>
					<c:if test="${opciones.ind_sist_acceso_prod}">
						<li><a id="idLnkSISTAccesoProd">
							<spring:message	code="label.portlet.button.sist.acceso.prod" /></a></li>
					</c:if>
				</c:if>
				
		</c:if>
		
		<!-- Menu de tipos de tramite para el portal de asegurado -->
		<c:if test="${idContenedor eq asegurado }">
				<c:if test="${idEstado ne 3 && idEstado ne 6}">
					<c:if test="${opciones.ind_registro_derechohabiente}">
							<li><a id="iniciarRegistroBeneficiario"> Registro de derechohabiente</a></li>
					</c:if>
					<c:if test="${opciones.ind_correccion_datos_dhabiente}">
						<li><a id="iniciarCorreccionDatosDHabiente"> Correcci&oacute;n de datos del derechohabiente</a></li>
					</c:if>
					<c:if test="${opciones.ind_cambio_domicilio}">
						<li><a id="iniciarCambioDomicilio"> Cambio de domicilio y/o UMF</a></li>
					</c:if>
					<c:if test="${opciones.ind_baja_defuncion}">
						<li><a id="iniciarBajaDefuncion"> Baja por defunci&oacute;n</a></li>
					</c:if>
					<c:if test="${opciones.ind_baja_dependencia}">
						<li><a id="iniciarBajaDependencia"> Baja por t&eacute;rmino de dependencia</a></li>
					</c:if>
					<c:if test="${opciones.ind_baja_concubina}">
						<li><a id="iniciarBajaConcubinato"> Baja por t&eacute;rmino de concubinato</a></li>
					</c:if>
					<c:if test="${opciones.ind_baja_divorcio}">
						<li><a id="iniciarBajaDivorcio"> Baja por divorcio</a></li>
					</c:if>
					<c:if test="${opciones.ind_baja_divorcio}">
						<li><a id="iniciarBajaUnionCivil"> Baja por t&eacute;rmino de uni&oacute;n civil</a></li>
					</c:if>
					<!-- Inicio de prorrogas -->
					<c:if test="${opciones.ind_prorroga_permanente}">
						<li><a id="iniciarProrrogaPermanente"> Pr&oacute;rroga por vigencia permanente</a></li>
					</c:if>
					<c:if test="${opciones.ind_prorroga_acuerdo}">
						<li><a id="iniciarProrrogaAcuerdo"> Pr&oacute;rroga por acuerdo</a></li>
					</c:if>
					<c:if test="${opciones.ind_prorroga_obstetrica}">
						<li><a id="iniciarProrrogaObstetrica"> Pr&oacute;rroga por incapacidad obst&eacute;trica</a></li>
					</c:if>
					<c:if test="${opciones.ind_prorrga_fisica_psiquica}">
						<li><a id="iniciarProrrogaFisica"> Pr&oacute;rroga por incapacidad f&iacute;sica o ps&iacute;quica</a></li>
					</c:if>
					<c:if test="${opciones.ind_prorroga_estudios}">
						<li><a id="iniciarProrrogaEstudios"> Pr&oacute;rroga por estudios</a></li>
					</c:if>
					<c:if test="${opciones.ind_prorrga_laudo}">
						<li><a id="iniciarProrrogaLaudo"> Pr&oacute;rroga por laudo</a></li>
					</c:if>
					<c:if test="${opciones.ind_prorroga_temporal}">
						<li><a id="iniciarProrrogaTemporal"> Pr&oacute;rroga por vigencia permanente</a></li>
					</c:if>
					
					<c:if test="${opciones.ind_solicitud_pension}">
							<li><a id="iniciarSolicitudPension"> Solicitud de Pensi&oacute;n </a></li>
					</c:if>

					<c:if test="${opciones.ind_solicitud_cartilla}">
						<li><a id="imprimirCartilla">Reimpresi&oacute;n de cartilla nacional de salud</a></li>
					</c:if>
					
					<c:if test="${!opciones.ind_registro_derechohabiente && !opciones.ind_baja_defuncion && 
					!opciones.ind_correccion_datos_dhabiente && !opciones.ind_baja_dependencia && 
					!opciones.ind_baja_concubina && !opciones.ind_baja_divorcio && !opciones.ind_baja_persona_en_union_civil
					&& !opciones.ind_prorroga_permanente && !opciones.ind_prorroga_acuerdo && !opciones.ind_prorroga_obstetrica 
					&& !opciones.ind_prorrga_fisica_psiquica
					&& !opciones.ind_prorroga_estudios && !opciones.ind_prorrga_laudo 
					&& !opciones.ind_prorroga_temporal && !opciones.ind_cambio_domicilio}">
						<li>
							<%@ include file="sinOpciones.jsp" %>
						</li>
					</c:if>
				</c:if>
				<c:if test="${idEstado eq 3 || idEstado eq 6}">
					<c:if test="${opciones.ind_baja_defuncion}">
						<li><a id="iniciarBajaDefuncion"> Baja por defunci&oacute;n</a></li>
					</c:if>
					<c:if test="${opciones.ind_baja_dependencia}">
						<li><a id="iniciarBajaDependencia"> Baja por t&eacute;rmino de dependencia</a></li>
					</c:if>
					<c:if test="${opciones.ind_baja_concubina}">
						<li><a id="iniciarBajaConcubinato"> Baja por t&eacute;rmino de concubinato</a></li>
					</c:if>
					<c:if test="${opciones.ind_baja_divorcio}">
						<li><a id="iniciarBajaDivorcio"> Baja por divorcio</a></li>
					</c:if>
					<c:if test="${opciones.ind_baja_divorcio}">
						<li><a id="iniciarBajaUnionCivil"> Baja por t&eacute;rmino de uni&oacute;n civil</a></li>
					</c:if>
				</c:if>
		</c:if>
		<c:if test="${idContenedor eq derechohabiente}">
			<jsp:include page="opcionesMenuAdscripcion.jsp"></jsp:include>
		</c:if>
	<c:if test="${idTipocontenedor eq 4}">
	</ul>
	</c:if>
</c:if>
