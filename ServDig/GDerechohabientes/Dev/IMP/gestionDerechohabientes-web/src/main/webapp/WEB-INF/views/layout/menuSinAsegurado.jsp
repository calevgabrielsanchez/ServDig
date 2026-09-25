<%@ include file="../general/taglibs.jsp" %>

	
<div class="menu_holder" align="right">
	
	
	<div id="dgCerrarSesion" title="Cerrar Sesion" >
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"> </span>
			  &iquest;Esta Ud. seguro de cerrar su sesi&oacute;n?
		</p>
		</br>
		 
		<span id="errorNegocioLabel" class=" hiddenElement error"></span>
	</div>


	<!--Inicio Menu -->
	
<div class="menu_holder">	
	<ul class="menu">
	<c:if test="${usuarioObj.perfilUsuario.idPerfilUsuario==1}">
		<li ><a href="/${mvn.web.app.root}/tramite/registro/iniciarTramite">Registro</a>		
		</li>
	</c:if>
</ul>
</div>
<!-- Termino Menu -->

	
</div>



