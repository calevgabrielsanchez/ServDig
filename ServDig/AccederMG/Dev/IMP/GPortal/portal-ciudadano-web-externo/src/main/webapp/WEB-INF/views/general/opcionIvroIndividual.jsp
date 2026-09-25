<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>

<ul id="opcionesPrincipalesCiudadano">

	<c:if test="${opciones.ind_ivro_individual_ciudadano_sat}">
		<li titulo="Incorporaci&oacute;n Voluntaria al R&eacute;gimen Obligatorio" 
			icono="f0f1"
			desc="Modalidad 35, 43 &oacute; 44."
			id="ivroIndividual">
		</li>
	</c:if>

</ul>