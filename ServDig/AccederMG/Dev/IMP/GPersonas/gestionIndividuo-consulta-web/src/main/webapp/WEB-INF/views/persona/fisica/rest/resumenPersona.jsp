<%@ include file="../../../layout/taglibs.jsp" %>

<div class="imss-widget">



	<c:choose>
		<c:when test="${not empty persona}">
		
			<address class="resumen-text resumen-persona">
									<i class="glyphicon glyphicon-user" style="margin-right: 15px;"></i><strong>${persona.nombre } ${persona.primerApellido } ${persona.segundoApellido } </strong><br>
									<i class="glyphicon glyphicon-tag" style="margin-right: 15px;"></i>${persona.curp }<br>
									<i class="glyphicon glyphicon-calendar" style="margin-right: 15px;"></i>${persona.fechaNacimientoFormateada }<br>
									
			</address>
			<div>
			
				<a id="cerrarSesionLink" href="#"> <i class="icon-off" style="margin-right: 15px;"></i>Cerrar Sesi&oacute;n</a>
			</div>
			
		</c:when>
		<c:otherwise>
			<span class="error-widget"> ${error}</span>		
		</c:otherwise>
	
	</c:choose>


	

</div>