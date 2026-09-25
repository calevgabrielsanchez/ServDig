<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>

<!-- LISTA DE OPCIONES PARA EL MENÚ PORTAL CIUDADANO. Se checa por cada opción
si está activa en el properties. El orden en que se pongan las opciones en esta
lista será el mismo en que se muesten en la pantalla. El atributo 'icono'
representa el unicode del ícono fontawesome que se desea poner como fondo de
la opción (referencia http://fontawesome.io/3.2.1/cheatsheet) -->

<ul id="opcionesPrincipalesCiudadano">
	<c:if test="${opciones.ind_asignacion_nss}">
		<li titulo="<spring:message code="portal.opciones.asigacion.titulo"/>" 
			icono="f09d"
			desc="<spring:message code="portal.opciones.asigacion.descripcion"/>"
			id="asignacionNSS">
		</li>
	</c:if>

	<c:if test="${opciones.ind_consulta_vigencia}">
		<li titulo="<spring:message code="portal.opciones.consultaVigencia.titulo"/>" 
			icono="f046"
			desc="<spring:message code="portal.opciones.consultaVigencia.descripcion"/>"
			id="consultaVig">
		</li>
	</c:if>
	
	<c:if test="${opciones.ind_registro_asegurado}">
		<li titulo="<spring:message code="portal.opciones.registroDer.titulo"/>" 
			icono="f183"
			desc="<spring:message code="portal.opciones.registroDer.descripcion"/>"
			id="registroAseg">
		</li>
	</c:if>
	
	<c:if test="${opciones.ind_cambio_clinica_asegurado || opciones.ind_cambio_clinica_beneficiarios}">
		<li titulo="<spring:message code="portal.opciones.cambioClin.titulo"/>" 
			icono="f0f7"
			desc="<spring:message code="portal.opciones.cambioClin.descripcion"/>"
			id="cambioClinica">
		</li>
	</c:if>
	
	<c:if test="${opciones.ind_registro_beneficiarios}">
		<li titulo="<spring:message code="portal.opciones.registroBeneficiarios.titulo"/>" 
			icono="f0c0"
			desc="<spring:message code="portal.opciones.registroBeneficiarios.descripcion"/>"
			id="registroBen">
		</li>
	</c:if>
	

	<c:if test="${opciones.ind_baja_defuncion_c || opciones.ind_baja_concubinato_c || opciones.ind_baja_divorcio_c || opciones.ind_baja_dependencia_c}">
		<li titulo="<spring:message code="portal.opciones.bajaBeneficiarios.titulo"/>"
		icono="f068"
		desc= "<spring:message code="portal.opciones.bajaBeneficiarios.descripcion"/>"
		id="bajaDerechohabiente">
		</li>
	</c:if>
	
	<c:if test="${opciones.ind_prorroga_estudios_p}">
		<li titulo="<spring:message code="portal.opciones.prorrogaEstudios.titulo"/>" 
			icono="f067"
			desc="<spring:message code="portal.opciones.prorrogaEstudios.descripcion"/>"
			id="tramiteProrroga">
		</li>
	</c:if>

	<c:if test="${opciones.ind_act_dom_ciudadano}">
		<li titulo="<spring:message code="portal.opciones.registroDom.titulo"/>" 
			icono="f015"
			desc="<spring:message code="portal.opciones.registroDom.descripcion"/>"
			id="registroDomicilio">
		</li>
	</c:if>
			
	<c:if test="${opciones.ind_ivro_individual}">
		<li titulo="<spring:message code="portal.opciones.ivro.titulo"/>" 
			icono="f0f1"
			desc="<spring:message code="portal.opciones.ivro.descripcion"/>"
			id="ivroIndividual">
		</li>
	</c:if>
	
	<c:if test="${opciones.ind_ivro_domestico}">
		<li titulo="<spring:message code="portal.opciones.ivroDom.titulo"/>" 
			icono="f055"
			desc="<spring:message code="portal.opciones.ivroDom.descripcion"/>"
			id="ivroDomestico">
		</li>
	</c:if>
	
	<c:if test="${opciones.ind_registro_patronal_ciudadano}">
		<li titulo="<spring:message code="portal.opciones.altaPatronal.titulo"/>" 
			icono="f0b1"
			desc="<spring:message code="portal.opciones.altaPatronal.descripcion"/>"
			id="registroAltaPatronal">
		</li>
	</c:if>
		
	<c:if test="${opciones.ind_beneficios_riss_ciudadano}">
		<li titulo="<spring:message code="portal.opciones.riss.titulo"/>" 
			icono="f016"
			desc="<spring:message code="portal.opciones.riss.descripcion"/>"
			id="beneficiosRiss">
		</li>
	</c:if>
	
	<c:if test="${opciones.ind_seguro_familiar_ciudadano}">
		<li titulo="<spring:message code="portal.opciones.seguroSalud.titulo"/>" 
			icono="f0c0"
			desc="<spring:message code="portal.opciones.seguroSalud.descripcion"/>"
			id="seguroFamiliar">
		</li>
	</c:if>
	
	<c:if test="${opciones.ind_inscripcion_cvro_ciudadano}">
		<li titulo="<spring:message code="portal.opciones.cvro.titulo"/>" 
			icono="f016"
			desc="<spring:message code="portal.opciones.cvro.descripcion"/>"
			id="inscripcionCVRO">
		</li>
	</c:if>
</ul>