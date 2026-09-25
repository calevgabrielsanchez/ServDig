<%@ include file="../general/taglibs.jsp" %>

<!--Inicio Breadcrumb -->
<div class="breadcrumb">
	<ul style="float: left !important;"> 
		<li>
		    <table>
		        <tr>
		          <td>
					<span style="padding: 5px !important; vertical-align: bottom; text-align:right"> <b>Fecha :</b></span> 
					<span style="padding: 5px !important; vertical-align: bottom;"> <c:out value='${fechaSistema}' /> </span>
			      </td>
        		</tr>
        		<tr>
		          <td>
					<span style="padding: 5px !important;vertical-align: bottom; text-align:right"> 					
						<c:if test="${usuario.usuarioFuncionario.delegacion.id ne null}">
							<b>Delegaci&oacute;n:</b> <c:out value='${usuario.usuarioFuncionario.delegacion.id}'/> 
						</c:if>
						
						<c:if test="${usuario.usuarioFuncionario.subdelegacion.id ne null
										&& usuario.usuarioFuncionario.subdelegacion.id > 0 }">						
							&nbsp;&nbsp; <b>Subdelegaci&oacute;n:</b> <c:out value='${subdelegacion.clave}'/>
						</c:if>
					</span>
					<input type="hidden" id="sistemasOAM" value="${sistemasOAM}" />
					<input type="hidden" id="perfilesOAM" value="${perfilesOAM}" />
			      </td>
        		</tr>
			</table>
		</li>
	
		<li>
		    <table>
		        <tr>
		          <td>
					<span style="padding: 5px !important;vertical-align: bottom; text-align:right"> <b>Usuario :</b></span> 
					<span style="padding: 5px !important; vertical-align: bottom;"> <c:out value='${usuario.usuario}' /></span>
			      </td>
        		</tr>
        		<tr>
		          <td>
          			<span style="padding: 5px !important;vertical-align: bottom; text-align:right"><b>Rol:</b> <c:out value='${usuario.perfilUsuario.descripcion}'/></span>
			      </td>
        		</tr>
        	</table>			
		</li>
		
		<li>
			<img  src="<spring:url value="/static/resources/imagenes/system-users.png" htmlEscape="true" />" title="Usuario"  style="vertical-align: bottom;"/>
		</li>
		
		<li>
			<span style="padding-right: 25px; vertical-align: bottom;" >
					<div id="cenefa" style="vertical-align: bottom;">Bienvenido!!!</div>
			</span> 
		</li>
		
		
	</ul>

	<ul>
		<li style="margin-right:10px;">
			<a href="#" onclick="fnAbrirDialogoCerrarSesion();"><img  src="<spring:url value="/static/resources/imagenes/system-log-out.png" htmlEscape="true" />" title="Salir" style="vertical-align: bottom;"  /></a>
		</li>
	
		<li style="margin-right:10px;">
			<a href="<%=request.getContextPath()%>/home"><img  src="<spring:url value="/static/resources/imagenes/go-home.png" htmlEscape="true" />" title="Inicio"  style="vertical-align: bottom;"/></a>
		</li>
		
		<li style="margin-right:10px;">
			<a href="http://${mvn.url.imssdigital}/delta/resources/pdf/clasificacion/ManualUsuario.pdf" target="_blank" ><img  src="<spring:url value="/static/resources/imagenes/ayuda.png" htmlEscape="true"/>" title="Ayuda"  style="vertical-align: bottom;" width="27" height="24"/></a>
		</li>

	</ul>
</div>
<!--Termino Breadcrumb -->

