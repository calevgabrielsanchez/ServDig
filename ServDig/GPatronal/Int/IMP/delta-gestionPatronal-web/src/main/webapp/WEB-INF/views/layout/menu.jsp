<%@ include file="../general/taglibs.jsp" %>
<%@page import="mx.gob.imss.ctirss.delta.gestion.patronal.web.utils.CodigoRolTemporal"%>
<%@page import="mx.gob.imss.ctirss.delta.model.Usuario"%>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/menu.js" htmlEscape="true" />"></script>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<% Usuario usuario = (Usuario)session.getAttribute("usuario"); %>
<div class="menu_holder" align="right">
	
	
	<div id="dgCerrarSesion" title="Cerrar Sesion" style="display: none;" >
		<p>
			<span class="ui-icon ui-icon-alert"style="float: left; margin: 0 7px 20px 0;"> </span>
			 &iquest;Est&aacute; Ud. seguro de cerrar su sesi&oacute;n?
		</p>
		</br>
		 
		<span id="errorNegocioLabel" class=" hiddenElement error"></span>
	</div>


	<!--Inicio Menu -->
    <div class="menu_holder">
      <ul class="menu">
      	<li><a href="#">Movimientos</a> 
          <ul class="sub-menu">
            <li><a href="${contextpath}/afiliacion/alta">Alta Patronal</a></li>   
            <li><a href="${contextpath}/afiliacion/cargarPantallaDeBaja">Baja Patronal</a></li>
          </ul>
        </li>
      </ul>
    </div>
    <!-- Termino Menu -->
	

	
</div>



