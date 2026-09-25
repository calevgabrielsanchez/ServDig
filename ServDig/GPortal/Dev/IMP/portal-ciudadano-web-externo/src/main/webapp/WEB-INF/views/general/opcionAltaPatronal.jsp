<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>

<ul id="opcionesPrincipalesCiudadano">

	<c:if test="${opciones.ind_registro_patronal_ciudadano_sat}">
		<li titulo="Alta Patronal Persona F&iacute;sica" 
			icono="f0b1"
			desc="Solicitud de registro de alguna empresa en el Seguro de Riesgos de Trabajo (SRT)."
			id="registroAltaPatronal">
		</li>
	</c:if>

</ul>