<%@ include file="../general/taglibs.jsp" %>
<%@page import="mx.gob.imss.ctirss.delta.model.clasificacion.CodigoRolClasificacion"%>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/menu.js" htmlEscape="true" />"></script>

<c:set var="grupoTramite" value="<%=session.getAttribute(\"grupoTramite\") %>" />
<!-- Obtenemos el rol del usuario firmado -->
<c:set var="rol"                        value="${usuario.perfilUsuario.idPerfilUsuario}" />
<c:set var="codigoNivelCentral"         value="<%=CodigoRolClasificacion.NORMATIVO_CENTRAL.getCodigo().toString()%>" />
<c:set var="codigoDelegado"             value="<%=CodigoRolClasificacion.DELEGADO_DEL.getCodigo().toString()%>" />
<c:set var="codigoSubDelegadoSD"        value="<%=CodigoRolClasificacion.SUBDELEGADO_SUBDEL.getCodigo().toString()%>" />
<c:set var="codigoJefeOfnaCobrosSD"     value="<%=CodigoRolClasificacion.JEFE_OFICINA_COBROS_SUBDEL.getCodigo().toString()%>" />

<div class="menu_holder" align="right">
	<div id="dgCerrarSesion" title="Cerrar Sesion" >
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"> </span>
			 ¿Est&aacute; Ud. seguro de cerrar su sesi&oacute;n?
		</p>
		<br/>
		<span id="errorNegocioLabel" class=" hiddenElement error"></span>
	</div>

	<!--Inicio Menu -->
    <div class="menu_holder">
        <ul class="menu">
            <c:if test="${grupoTramite != null && grupoTramite != 0}">
	            <li><a href="<%=request.getContextPath()%>/home">Inicio</a></li>	            
            	<%String menuDecoration=session.getAttribute("menuDecoration").toString().trim();%>            	
	            <li><a href="<%=request.getContextPath()%>/analisis" id="liga1" name="liga1" <%=menuDecoration.equals("1")?"style=\"text-decoration: underline;\"":""%>>An&aacute;lisis y consulta</a></li>
	            <c:if test="${grupoTramite != 3}">
	            <li><a href="<%=request.getContextPath()%>/consulta/reportesAnalisis" id="menuRepAnalisis" name="menuRepAnalisis" <%=menuDecoration.equals("2")?"style=\"text-decoration: underline;\"":""%>>Reportes</a></li>
	            </c:if>
	            <!-- Si el usuario es normativo nivel central se muestra la opcion -->
	            <c:if test="${rol == codigoNivelCentral}">	            
	            	<li><a href="<%=request.getContextPath()%>/consulta/concentradoNacional" id="menuRepConcentrado" name="menuRepConcentrado" <%=menuDecoration.equals("4")?"style=\"text-decoration: underline;\"":""%>>Consolidado</a></li>
			    </c:if>
	            <c:if test="${rol != codigoNivelCentral}">	            
	            	<li><a href="" id="menuRepConcentrado" name="menuRepConcentrado"></a></li>
			    </c:if>
			    <c:if test="${grupoTramite != 3}">
			    <li><a href="<%=request.getContextPath()%>/consulta/reportesBitacoras" id="menuRepBitacora" name="menuRepBitacora" <%=menuDecoration.equals("3")?"style=\"text-decoration: underline;\"":""%>>Bit&aacute;cora</a></li>
	        	</c:if>
	        </c:if>
           	<c:if test="${grupoTramite != null && grupoTramite == 0}">
	            <li><a href="<%=request.getContextPath()%>/home">Inicio</a></li>	            
            	<%String menuDecoration=session.getAttribute("menuDecoration").toString().trim();%>  
            	<c:if test="${rol == codigoDelegado || rol == codigoSubDelegadoSD  || rol == codigoJefeOfnaCobrosSD}">	             		          	
	            	<li><a href="<%=request.getContextPath()%>/modulo/firma" id="liga1" name="liga1" <%=menuDecoration.equals("1")?"style=\"text-decoration: underline;\"":""%>>Firmar Clem</a></li>
				    <li><a href="<%=request.getContextPath()%>/modulo/firma/ver/firmadas" id="menuRepFirmadas" name="menuRepFirmadas" <%=menuDecoration.equals("3")?"style=\"text-decoration: underline;\"":""%>>Ver Clem con firma</a></li>
	            </c:if>
	            
	            <c:if test="${rol != codigoDelegado && rol != codigoSubDelegadoSD  && rol != codigoJefeOfnaCobrosSD}">
				    <li><a href="<%=request.getContextPath()%>/modulo/firma/ver/firmadas" id="menuRepFirmadas" name="menuRepFirmadas" <%=menuDecoration.equals("1")?"style=\"text-decoration: underline;\"":""%>>Ver Clem con firma</a></li>
	            </c:if>
	            
           	</c:if>
	        
        </ul>
    </div>
    <!-- Termino Menu -->
</div>