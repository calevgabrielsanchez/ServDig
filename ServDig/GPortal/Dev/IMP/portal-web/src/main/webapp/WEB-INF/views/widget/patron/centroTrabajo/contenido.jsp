
<!-- JSP Contenido del Widget de Datos Basicos del Centro de Trabajo. -->
<%@ include file="../../../general/taglibs.jsp"%>
<c:set var="staticResourcesPath"
	value='<%=request.getSession().getServletContext().getInitParameter("STATIC_RESOURCES_PATH") %>' />
<c:set var="staticLogoutPath" value='<%=request.getSession().getServletContext().getInitParameter("STATIC_LOGOUT_PATH")%>' />
<c:set var="fisica" value="${patron.fisica}" />
<c:set var="moral" value="${patron.moral}" />


<address>
	<i class="icon-briefcase" style="margin-right: 15px;"></i><strong>
		${patron.numeroRegistroPatronal}${patron.modalidad.numModalidad}${patron.digVerificador}
	</strong><br> <i class="icon-user" style="margin-right: 15px;"></i>
	<c:if test="${fisica != null}">
				${fisica.nombre} ${fisica.primerApellido} ${fisica.segundoApellido}
			</c:if>
	<c:if test="${moral != null}">
				${moral.razonSocial}
			</c:if>
	<br> <i class="glyphicon glyphicon-tag" style="margin-right: 15px;"></i>
	<c:choose>
		<c:when test="${patron.nombreComercial eq null }">
			<span class="no-data"> Sin Nombre Comercial</span>
		</c:when>
		<c:otherwise>
					${patron.nombreComercial}
				</c:otherwise>
	</c:choose>
	<br> <i class="glyphicon glyphicon-tag" style="margin-right: 15px;"></i>
	${patron.tipoPersonaFiscal}<br>
	<c:if test="${fisica != null}">
		<i class="glyphicon glyphicon-tags" style="margin-right: 15px;"></i>
		<c:choose>
			<c:when test="${fisica.rfc eq null }">
				<span class="no-data"> No tiene RFC</span>
			</c:when>
			<c:otherwise>
						${fisica.rfc}
					</c:otherwise>
		</c:choose>
		<br>
		<i class="glyphicon glyphicon-tags" style="margin-right: 15px;"></i> ${fisica.curp}<br>
	</c:if>
	<c:if test="${moral != null}">
		<i class="glyphicon glyphicon-tags" style="margin-right: 15px;"></i> ${moral.rfc}<br>
	</c:if>
</address>




