<%@ include file="../general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum"%>

<c:set var="asegurado" value="<%=ParentescoEnum.ASEGURADO.getId()%>"></c:set>
<c:set var="padres" value="<%=ParentescoEnum.PADRES.getId()%>"></c:set>
<c:set var="conyuge" value="<%=ParentescoEnum.CONYUGE.getId()%>"></c:set>
<c:set var="concubina" value="<%=ParentescoEnum.CONCUBINARIO.getId()%>"></c:set>
<c:set var="hijos" value="<%=ParentescoEnum.HIJOS.getId()%>"></c:set>
<c:set var="union civil" value="<%=ParentescoEnum.PERSONA_EN_UNION_CIVIL.getId()%>"></c:set>

<c:if test="${not empty opciones}">

	<c:if test="${idParentesco eq asegurado || idParentesco eq pensionado || idParentesco eq 0}">
		<c:if test="${opciones.ind_cambio_domicilio}">
			<li><a id="iniciarCambioDomicilio"> Cambio de domicilio y/o UMF</a></li>
		</c:if>
		<c:if test="${!opciones.ind_cambio_domicilio}">
			<%@ include file="sinOpciones.jsp" %>
		</c:if>
	</c:if>
	<c:if test="${idParentesco eq conyuge}">
		<c:if test="${idEstado ne 3 }">
			<c:if test="${opciones.ind_correccion_datos_dhabiente}">
				<li><a id="iniciarCorreccionDatosDHabiente"> Correcci&oacute;n de datos del derechohabiente</a></li>
			</c:if>
			<c:if test="${opciones.ind_cambio_domicilio}">
				<li><a id="iniciarCambioDomicilio"> Cambio de domicilio y/o UMF</a></li>
			</c:if>
			<c:if test="${opciones.ind_baja_defuncion}">
				<li><a id="iniciarBajaDefuncion"> Baja por defunci&oacute;n</a></li>
			</c:if>
			<c:if test="${opciones.ind_baja_divorcio}">
				<li><a id="iniciarBajaDivorcio"> Baja por divorcio</a></li>
			</c:if>
			<c:if test="${opciones.ind_baja_divorcio}">
				<li><a id="iniciarBajaUnionCivil"> Baja por t&eacute;rmino de uni&oacute;n civil</a></li>
			</c:if>
			<c:if test="${!opciones.ind_correccion_datos_dhabiente && !opciones.ind_baja_defuncion && !opciones.ind_baja_divorcio && !opciones.ind_baja_union_civil && !opciones.ind_cambio_domicilio}">
				<%@ include file="sinOpciones.jsp" %>
			</c:if>
		</c:if>
		<c:if test="${idEstado eq 3}">
			<%@ include file="sinOpciones.jsp" %>
		</c:if>
	</c:if>
	<c:if test="${idParentesco eq padres}">
		<c:if test="${idEstado ne 3 }">
			<c:if test="${opciones.ind_baja_defuncion}">
				<li><a id="iniciarBajaDefuncion"> Baja por defunci&oacute;n</a></li>
			</c:if>
			<c:if test="${opciones.ind_baja_dependencia}">
					<li><a id="iniciarBajaDependencia"> Baja por t&eacute;rmino de dependencia</a></li>
			</c:if>
			<c:if test="${!opciones.ind_baja_defuncion && !opciones.ind_baja_dependencia}">
				<%@ include file="sinOpciones.jsp" %>
			</c:if>
		</c:if>
		<c:if test="${idEstado eq 3}">
			<%@ include file="sinOpciones.jsp" %>
		</c:if>
	</c:if>
	<c:if test="${idParentesco eq concubina}">
		<c:if test="${idEstado ne 3 }">
			<c:if test="${opciones.ind_baja_defuncion}">
				<li><a id="iniciarBajaDefuncion"> Baja por defunci&oacute;n</a></li>
			</c:if>
			<c:if test="${opciones.ind_baja_concubina}">
				<li><a id="iniciarBajaConcubinato"> Baja por t&eacute;rmino de concubinato</a></li>
			</c:if>
			<c:if test="${!opciones.ind_baja_defuncion && !opciones.ind_baja_concubina}">
				<%@ include file="sinOpciones.jsp" %>
			</c:if>
		</c:if>
		<c:if test="${idEstado eq 3}">
			<%@ include file="sinOpciones.jsp" %>
		</c:if>
	</c:if>
	<c:if test="${idParentesco eq hijos}">
		<c:if test="${idEstado ne 3 }">
			<c:if test="${opciones.ind_correccion_datos_dhabiente}">
				<li><a id="iniciarCorreccionDatosDHabiente"> Correcci&oacute;n de datos del derechohabiente</a></li>
			</c:if>
			<c:if test="${opciones.ind_cambio_domicilio}">
				<li><a id="iniciarCambioDomicilio"> Cambio de domicilio y/o UMF</a></li>
			</c:if>
			<c:if test="${opciones.ind_baja_defuncion}">
				<li><a id="iniciarBajaDefuncion"> Baja por defunci&oacute;n</a></li>
			</c:if>
			<c:if test="${!opciones.ind_correccion_datos_dhabiente && !opciones.ind_baja_defuncion && !opciones.ind_cambio_domicilio}">
				<%@ include file="sinOpciones.jsp" %>
			</c:if>
		</c:if>
		<c:if test="${idEstado eq 3}">
			<c:if test="${opciones.ind_prorrga_fisica_psiquica}">
				<li><a id="iniciarProrrogaFisica"> Pr&oacute;rroga por incapacidad f&iacute;sica o ps&iacute;quica</a></li>
			</c:if>
			<c:if test="${opciones.ind_prorroga_estudios}">
				<li><a id="iniciarProrrogaEstudios"> Pr&oacute;rroga por estudios</a></li>
			</c:if>
			<c:if test="${!opciones.ind_prorrga_fisica_psiquica && !opciones.ind_prorroga_estudios}">
				<%@ include file="sinOpciones.jsp" %>
			</c:if>
		</c:if>
	</c:if>
</c:if>