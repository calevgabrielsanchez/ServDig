<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.portal.web.model.TipoFiltroEnum"%>

<!-- LISTA DE OPCIONES PARA EL MENÚ VENTANILLA. Se checa por cada opción
si está activa en el properties, también se podrá validar si el rol de 
usuario puede ver la opción. El orden en que se pongan las opciones en esta
lista será el mismo en que se muesten en la pantalla. El atributo 'icono'
representa el unicode del ícono fontawesome que se desea poner como fondo de
la opción (referencia http://fontawesome.io/3.2.1/cheatsheet) -->

<ul id="opcionesPrincipalesVentanilla">
	<c:if test="${opc:habilitada('ind_alta_patronal_PF')}">
		<%-- <sec:authorize ifAnyGranted="ROLE_VENTANILLA"> --%>
			<li tram="<%=TipoTramiteEnum.ALTA_SRT.getCodigo()%>" 
				filtro="<%=TipoFiltroEnum.RFC_FISICA.getId()%>"
				titulo="Alta Patronal Persona F&iacute;sica" 
				icono="f0f6"
				desc=""
				id="altaSrtFisica">
			</li>
		<%-- </sec:authorize> --%>
	</c:if>
		
	<c:if test="${opc:habilitada('ind_alta_patronal_PM')}">
		<li tram="<%=TipoTramiteEnum.ALTA_SRT_PM.getCodigo()%>" 
			filtro="<%=TipoFiltroEnum.RFC_MORAL.getId()%>"
			titulo="Alta Patronal Persona Moral" 
			icono="f0f6"
			desc=""
			id="altaSrtMoral">
		</li>
	</c:if>
		
	<c:if test="${opc:habilitada('ind_alta_socio')}">
		<li tram="<%=TipoTramiteEnum.ACTUALIZACION_SOCIO.getCodigo()%>" 
			filtro="<%=TipoFiltroEnum.RFC_MORAL.getId()%>"
			titulo="Alta de Socio" 
			icono="f0c0"
			desc=""
			id="altaSocio">
		</li>
	</c:if>
		
	<c:if test="${opc:habilitada('ind_baja_socio')}">		
		<li tram="<%=TipoTramiteEnum.BAJA_SOCIO.getCodigo()%>" 
			filtro="<%=TipoFiltroEnum.RFC_MORAL.getId()%>"
			titulo="Baja de Socio" 
			icono="f0c0"
			desc=""
			id="bajaSocio">
		</li>
	</c:if>
		
	<c:if test="${opc:habilitada('ind_baja_RL_PF')}">		
		<li tram="<%=TipoTramiteEnum.BAJA_REPRESENTANTE_LEGAL.getCodigo()%>" 
			filtro="<%=TipoFiltroEnum.RFC_FISICA.getId()%>"
			titulo="Baja Representante Legal Persona F&iacute;sica" 
			icono="f0e3"
			desc=""
			id="bajaRepLegalFisica">
		</li>
	</c:if>
		
	<c:if test="${opc:habilitada('ind_baja_RL_PM')}">		
		<li tram="<%=TipoTramiteEnum.BAJA_REPRESENTANTE_LEGAL.getCodigo()%>" 
			filtro="<%=TipoFiltroEnum.RFC_MORAL.getId()%>"
			titulo="Baja Representante Legal Persona Moral" 
			icono="f0e3"
			desc=""
			id="bajaRepLegalMoral">
		</li>
	</c:if>
		
	<c:if test="${opc:habilitada('ind_IVRO_individual')}">		
		<li tram="<%=TipoTramiteEnum.COMPRA_SEGURO_INDIVIDUAL.getCodigo()%>" 
			filtro="<%=TipoFiltroEnum.NSS.getId()%>"
			titulo="Incorporaci&oacute;n Voluntaria al R&eacute;gimen Obligatorio" 
			icono="f0f1"
			desc="Modalidad 35,43 &oacute; 44."
			id="ivroIndividual">
		</li>
	</c:if>
		
	<c:if test="${opc:habilitada('ind_IVRO_domestico')}">		
		<li tram="<%=TipoTramiteEnum.COMPRA_SEGURO_DOMESTICO.getCodigo()%>" 
			filtro="<%=TipoFiltroEnum.RFC_FISICA.getId()%>"
			titulo="Incorporaci&oacute;n Voluntaria de Trabajador Dom&eacute;stico" 
			icono="f0f1"
			desc=""
			id="ivroDomestico">
		</li>
	</c:if>
		
	<c:if test="${opc:habilitada('ind_alta_representado_legal')}">		
		<li tram="<%=TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.getCodigo()%>" 
			filtro="<%=TipoFiltroEnum.RFC_FISICA.getId()%>"
			titulo="Alta de Representante Legal" 
			icono="f0e3"
			desc=""
			id="altaRepLegal">
		</li>
	</c:if>
		
	<c:if test="${opc:habilitada('ind_recuperacion_patron')}">		
		<li tram="<%=TipoTramiteEnum.RECUPERACION_REGISTRO_PATRONAL.getCodigo()%>" 
			filtro="<%=TipoFiltroEnum.NRP.getId()%>"
			titulo="Recuperaci&oacute;n Patronal" 
			icono="f0f2"
			desc=""
			id="recuperacionNRP">
		</li>	
	</c:if>

	<c:if test="${opc:habilitada('ind_carta_no_adeudo_pf_32d')}">		
		<li tram="<%=TipoTramiteEnum.CARTA_NO_ADEUDO.getCodigo()%>" 
			filtro="<%=TipoFiltroEnum.RFC_FISICA.getId()%>"
			titulo="Carta de No Adeudo Art. 32D" 
			icono="f0f6"
			desc="Persona f&iacute;sica"
			id="cartaNoAdeudo">
			
		</li>	
	</c:if>
	
	<c:if test="${opc:habilitada('ind_carta_no_adeudo_pm_32d')}">		
		<li tram="<%=TipoTramiteEnum.CARTA_NO_ADEUDO.getCodigo()%>" 
			filtro="<%=TipoFiltroEnum.RFC_MORAL.getId()%>"
			titulo="Carta de No Adeudo Art. 32D" 
			icono="f0f6"
			desc="Persona moral"
			id="cartaNoAdeudoMoral">
		</li>	
	</c:if>
</ul>