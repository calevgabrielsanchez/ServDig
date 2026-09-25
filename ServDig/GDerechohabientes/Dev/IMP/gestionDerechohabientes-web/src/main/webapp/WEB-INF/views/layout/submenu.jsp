<%@ include file="../general/taglibs.jsp" %>
	
<!--Inicio Breadcrumb -->
<div class="breadcrumb">

	<ul style="float: left !important;">
	
		
		<li>
			<span class="etiqueta" style="padding: 5px !important;"> Hora : </span> 
			<span class="dato"> <fmt:formatDate type="time" value="${usuario.fechaSistema}"/></span>
		</li>
		<li>
			<span class="etiqueta" style="padding: 5px !important;"> Fecha : </span> 
			<span class="dato"> <fmt:formatDate pattern="dd/MM/yyyy" value="${usuario.fechaSistema}"/></span>
		</li>	
		<li>
			<span class="etiqueta" style="padding: 5px !important;"> Usuario : </span> 
			<span class="dato"> <c:out value='${usuario.usuario}' />  </span>
		</li>		
		<li>
			<img  src="<spring:url value="/static/resources/imagenes/system-users.png" htmlEscape="true" />" title="Usuario"  />
		</li>						
	</ul>	
	<ul>
		<li>
			<a href="#" onclick="fnAbrirDialogoCerrarSesion();"><img  src="<spring:url value="/static/resources/imagenes/system-log-out.png" htmlEscape="true" />" title="Salir"  /></a>
		</li>
		
		<c:if test="${usuarioObj.perfilUsuario.idPerfilUsuario != 12 }">
		<li>
			<c:if test="${usuarioObj.perfilUsuario.idPerfilUsuario == 13 }">
				<a href="<%= request.getContextPath()%>/tramita">
			</c:if>
			<c:if test="${usuarioObj.perfilUsuario.idPerfilUsuario != 13 }">
				<a href="<%= request.getContextPath()%>/welcome/uno/busqueda">
			</c:if>
			<img  src="<spring:url value="/static/resources/imagenes/go-home.png" htmlEscape="true" />" title="Inicio"  /></a>
		</li>
		</c:if>
		<c:if test="${normativoCE == 1 }">
			<li>
				<a href="<%= request.getContextPath()%>/tramita"><img  src="<spring:url value="/static/resources/imagenes/system-users.png" htmlEscape="true" />" title="Cambiar Perfil"  /></a>
			</li>
		</c:if>
	</ul>	
	<c:if test="${usuarioObj.perfilUsuario.idPerfilUsuario == 1 }">
		<br>
		<ul style="float: left !important;">
			<li>
				<span class="etiqueta" style="padding: 5px !important;"> UMF: </span> 
				<span class="dato"> <c:out value='${usuario.umf}' />  </span>
			</li>
			<li>
				<span class="etiqueta" style="padding: 5px !important;"> Delegaci&oacute;n  : </span> 
				<span class="dato"> <c:out value='${usuario.delegacion}' />  </span>
			</li>
		</ul>
	</c:if>
	<c:if test="${usuarioObj.perfilUsuario.idPerfilUsuario == 8 || usuarioObj.perfilUsuario.idPerfilUsuario == 9}">
		<br>
		<ul style="float: left !important;">
			
			<li>
				<span class="etiqueta" style="padding: 5px !important;"> Delegaci&oacute;n  : </span> 
				<span class="dato"> <c:out value='${usuarioObj.usuarioFuncionario.delegacion.descripcion}' />  </span>
			</li>
			<li>
				<span class="etiqueta" style="padding: 5px !important;"> Subdelegaci&oacute;n: </span> 
				<span class="dato"> <c:out value='${usuarioObj.usuarioFuncionario.subdelegacion.descripcion}' />  </span>
				
			</li>
		</ul>
	</c:if>
	
	
</div>
<!--Termino Breadcrumb -->

