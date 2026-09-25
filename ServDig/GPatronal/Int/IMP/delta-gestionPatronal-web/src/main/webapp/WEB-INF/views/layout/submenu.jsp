<%@ include file="../general/taglibs.jsp" %>
<!--Inicio Breadcrumb -->
<div class="breadcrumb">
<table style="width: 950px">
	<tr>
		<td style="width: 200px">
			<span class="etiqueta" style="padding: 5px !important;"> Fecha : </span> 
			<span class="dato"> <c:out value='${fechaSistema}' />  </span>
		</td>
		<td style="width: 20px;">
			<img  src="<spring:url value="/static/resources/imagenes/system-users.png" htmlEscape="true" />" title="Usuario"  />
		</td>
		<td style="width: 200px; vertical-align: center;">
			<span class="etiqueta" style="padding: 5px !important;"> Usuario : </span> 
			<span class="dato"> <c:out value='${usuario.usuario}' />  </span>
		</td>
		
		<td style="width: 250px; vertical-align: center;">
			<span class="etiqueta" style="padding: 5px !important;"> Subdelegaci&oacute;n : </span> 
			<span class="dato"> 
				<c:if test="${ usuario.usuarioFuncionario!=null && usuario.usuarioFuncionario.subdelegacion !=null }">
					<c:out value='${usuario.usuarioFuncionario.subdelegacion.descripcion}' />
<!-- 					
<c:out value='${usuario.usuarioFuncionario.subdelegacion.id}' />
<c:out value='${usuario.usuarioFuncionario.subdelegacion.clave}' />
-->
				</c:if>
			</span>
		</td>
		
		<td align="right">
			<a href="<%= request.getContextPath()%>/home"><img  src="<spring:url value="/static/resources/imagenes/go-home.png" htmlEscape="true" />" title="Inicio"  /></a>
		</td>
		<td style="width: 40px" align="right">
			<a href="#" onclick="fnAbrirDialogoCerrarSesion();"><img  src="<spring:url value="/static/resources/imagenes/system-log-out.png" htmlEscape="true" />" title="Salir"  /></a>
		</td>
	</tr>
</table>

			
<!--
	<ul style="float: left !important;">
	
		<li>
			<span class="etiqueta" style="padding: 5px !important;"> Fecha : </span> 
			<span class="dato"> <c:out value='${fechaSistema}' />  </span>
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
	
		<li>
			<a href="<%= request.getContextPath()%>/home"><img  src="<spring:url value="/static/resources/imagenes/go-home.png" htmlEscape="true" />" title="Inicio"  /></a>
		</li>
	</ul>
	-->
</div>
<!--Termino Breadcrumb -->

