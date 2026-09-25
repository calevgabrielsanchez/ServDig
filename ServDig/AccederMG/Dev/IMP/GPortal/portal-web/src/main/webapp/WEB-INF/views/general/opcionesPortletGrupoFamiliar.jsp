<%@ include file="../general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum"%>

<c:set var="asegurado" value="<%=ParentescoEnum.ASEGURADO.getId()%>"></c:set>
<c:set var="padres" value="<%=ParentescoEnum.PADRES.getId()%>"></c:set>
<c:set var="conyuge" value="<%=ParentescoEnum.CONYUGE.getId()%>"></c:set>
<c:set var="concubina" value="<%=ParentescoEnum.CONCUBINARIO.getId()%>"></c:set>
<c:set var="hijos" value="<%=ParentescoEnum.HIJOS.getId()%>"></c:set>
<c:set var="pensionado" value="<%=ParentescoEnum.PENSIONADO.getId()%>"></c:set>
<c:set var="unionCivil" value="<%=ParentescoEnum.PERSONA_EN_UNION_CIVIL.getId()%>"></c:set>


<c:if test="${not empty opciones}">

	<c:if test="${idParentesco eq asegurado || idParentesco eq pensionado || idParentesco eq 0}">
		<c:if test="${idEstado ne 3 && idEstado ne 6}">
			<c:if test="${opciones.ind_registro_derechohabiente}">
					<li><a id="iniciarRegistroBeneficiario"> Registro de derechohabiente</a></li>
				</c:if>
				<c:if test="${opciones.ind_correccion_datos_dhabiente}">
					<li><a id="iniciarCorreccionDatosDHabiente"> Correcci&oacute;n de datos del derechohabiente</a></li>
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
				<c:if test="${opciones.ind_cambio_domicilio}">
					<li><a id="iniciarCambioDomicilio"> Cambio de domicilio y/o UMF</a></li>
				</c:if>
				<c:if test="${opciones.ind_solicitud_cartilla}">
					<li><a id="imprimirCartillagrupo">Reimpresi&oacute;n de cartilla nacional de salud</a></li>
				</c:if>
				
				<c:if test="${
				!opciones.ind_registro_derechohabiente 
				&& !opciones.ind_baja_defuncion && !opciones.ind_correccion_datos_dhabiente 
				&& !opciones.ind_baja_dependencia && !opciones.ind_baja_concubina && !opciones.ind_baja_divorcio && !opciones.ind_baja_persona_en_union_civil
				&& !opciones.ind_prorroga_permanente && !opciones.ind_prorroga_acuerdo && !opciones.ind_prorroga_obstetrica && !opciones.ind_prorrga_fisica_psiquica
				&& !opciones.ind_prorroga_estudios && !opciones.ind_prorrga_laudo && !opciones.ind_prorroga_temporal && !opciones.ind_cambio_domicilio}">
					<li>
						<%@ include file="sinOpciones.jsp" %>
					</li>
				</c:if>
		</c:if>
		<c:if test="${idEstado eq 3 || idEstado eq 6}">
			<c:if test="${opciones.ind_baja_defuncion}">
				<li><a id="iniciarBajaDefuncion"> Baja por defunci&oacute;n</a></li>
			</c:if>
			<c:if test="${!opciones.ind_baja_defuncion}">
				<%@ include file="sinOpciones.jsp" %>
			</c:if>
		</c:if>
	</c:if>
</c:if>
