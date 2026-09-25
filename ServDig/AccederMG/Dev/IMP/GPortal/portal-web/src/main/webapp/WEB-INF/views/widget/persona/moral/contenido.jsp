
<!-- JSP Contenido del Widget de Persona Fisica. -->
<%@ include file="../../../general/taglibs.jsp"%>
<c:set var="staticResourcesPath"
	value='<%=request.getSession().getServletContext().getInitParameter("STATIC_RESOURCES_PATH") %>' />
<c:set var="staticLogoutPath" value='<%=request.getSession().getServletContext().getInitParameter("STATIC_LOGOUT_PATH")%>' />

<input type="hidden" id="idPersonaSesionFM"
	value='${usuario.cveIdUsuario}' />
<input type="hidden" id="idPersonaFM" valud='${moral.cveMoral}' />
<input type="hidden" id="idPersona" value='${moral.idPersona}' />
<input type="hidden" id="cveMoral" value='${moral.cveMoral}' />
<input type="hidden" id="rfcPersona" value='${moral.rfc}' />

<address>
	<i class="icon-user" style="margin-right: 15px;"></i><strong>
		${moral.razonSocial}</strong><br> <i class="glyphicon glyphicon-tag"
		style="margin-right: 15px;"></i>
	<c:choose>
		<c:when test="${moral.rfc eq null}">
			<span class="no-data">No tiene RFC</span>
		</c:when>
		<c:otherwise>
					${moral.rfc}
				</c:otherwise>
	</c:choose>
	<br> <i class="glyphicon glyphicon-tags" style="margin-right: 15px;"></i>
	${moral.tipoSociedad.descripcionAbreviada}<br> <i
		class="icon-calendar" style="margin-right: 15px;"></i>${moral.fechaCreacionFormateada}<br>
</address>

