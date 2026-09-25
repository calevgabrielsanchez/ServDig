
<!-- JSP Contenido del Porlet de Consulta de  Persona Fisica. -->
<%@ include file="../../../general/taglibs.jsp" %>
<c:set var="staticResourcesPath" value='<%=request.getSession().getServletContext().getInitParameter("STATIC_RESOURCES_PATH") %>' />
<c:set var="staticLogoutPath" value='<%=request.getSession().getServletContext().getInitParameter("STATIC_LOGOUT_PATH")%>' />


<div class="encabezado"></div>
<div class="cuerpo">

	<div class="titulo"> 
	
							
		<span><spring:message code="label.widget.titulo.persona.fisica" /> </span>
 
	</div>
	<div class="descripcion"> 
		<p>
			<spring:message code="label.widget.descripcion.persona.fisica" />
		</p>
	</div>
	<div class="contenido">
		<input type="hidden" id="idPersona" value='${fisica.idPersona}'/>
		<address>
									<i class="icon-user" style="margin-right: 15px;"></i><strong>${fisica.nombre } ${fisica.primerApellido } ${fisica.segundoApellido } </strong><br>
									<i class="glyphicon glyphicon-tag" style="margin-right: 15px;"></i>${fisica.curp }<br>
									<i class="glyphicon glyphicon-tags" style="margin-right: 15px;"></i> 
										<c:choose>
										 <c:when test="${fisica.rfc eq null }">
										 	<span class="no-data">No tiene RFC</span>
										 </c:when>
										 <c:otherwise>
										 	${fisica.rfc }
										 </c:otherwise>
										
										</c:choose>
									<br>
									<i class="icon-calendar" style="margin-right: 15px;"></i>${fisica.fechaNacimientoFormateada }<br>
									<i class="glyphicon glyphicon-globe" style="margin-right: 15px;"></i>${fisica.lugarNacimiento.nombre }<br>
									
		</address>
	</div>
</div>
<div class="estado"></div>
<div class="pie">
	
	<div class="opciones">
		
		
	</div>
	
	<div class="controles">
		

	</div>
</div>
