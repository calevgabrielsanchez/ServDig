<!-- JSP Contenido del Widget de Persona Fisica. -->
<%@ include file="../../../general/taglibs.jsp"%>


<input type="hidden" id="idPersonaSesionFM"
	value='${usuario.cveIdUsuario}' />
<input type="hidden" id="idPersonaFM" value='${fisica.cveFisica}' />
<input type="hidden" id="idPersona" value='${fisica.idPersona}' />
<input type="hidden" id="rfcPersona" value='${fisica.rfc}' />
<input type="hidden" id="curpPersona" value='${fisica.curp}' />

<address>
	<i class="icon-user" style="margin-right: 15px;"></i><strong>${fisica.nombre}
		${fisica.primerApellido } ${fisica.segundoApellido } </strong><br> <i
		class="glyphicon glyphicon-tag" style="margin-right: 15px;"></i>${fisica.curp }<br>
	<i class="glyphicon glyphicon-tags" style="margin-right: 15px;"></i>
	<c:choose>
		<c:when test="${fisica.rfc eq null }">
			<span class="no-data">No tiene RFC</span>
		</c:when>
		<c:otherwise>${fisica.rfc }</c:otherwise>
	</c:choose>
	<br> <i class="icon-calendar" style="margin-right: 15px;"></i>${fisica.fechaNacimientoFormateada}<br>
	<i class="glyphicon glyphicon-globe" style="margin-right: 15px;"></i>${fisica.lugarNacimiento.nombre}<br>

</address>




