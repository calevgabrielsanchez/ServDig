 
<c:set var="contextpath" value="<%=request.getContextPath()%>" />

	<div class="contenedor">
		<h3>
			<spring:message code="label.titulo.informacion.renapo" />
		</h3>
		<hr class="red" style="margin-bottom: 20px;">
	</div>
	<br/>
<div class="row">
	<div class="col-md-6">
		<div class="row">
			<div class="col-md-5">
				<label class="control-label"> 
					<spring:message	code="label.curp" />
				</label>
			</div>
			<div class="col-md-6">${solicitud.personaInteresada.curp}</div>
		</div>
        <div class="row">
			<div class="col-md-5">
				<label class="control-label"> <spring:message
						code="label.primer.apellido" />
				</label>
			</div>
			<c:choose>
            <c:when test="${fn:length(solicitud.personaInteresada.primerApellido) > 25}">
			<div Style="font-size:14px" class="col-md-6">${solicitud.personaInteresada.primerApellido}</div>
			 </c:when>
			<c:otherwise>
            <div class="col-md-6">${solicitud.personaInteresada.primerApellido}</div>
            </c:otherwise>
			</c:choose>
		</div>
		<div class="row">
			<div class="col-md-5">
				<label class="control-label"> <spring:message
						code="label.segundo.apellido" />
				</label>
			</div>
			<c:choose>
            <c:when test="${fn:length(solicitud.personaInteresada.segundoApellido) > 25}">
			<div Style="font-size:14px" class="col-md-6">${solicitud.personaInteresada.segundoApellido}</div>
			 </c:when>
			<c:otherwise>
            <div class="col-md-6">${solicitud.personaInteresada.segundoApellido}</div>
            </c:otherwise>
			</c:choose>
		</div>
		<div class="row">
			<div class="col-md-5">
				<label class="control-label"> <spring:message
						code="label.nombre" />
				</label>
			</div>
			<c:choose>
			<c:when test="${fn:length(solicitud.personaInteresada.nombre) > 25}">
			<div Style="font-size:14px" class="col-md-6">${solicitud.personaInteresada.nombre}</div>
			</c:when>
			<c:otherwise>
            <div class="col-md-6">${solicitud.personaInteresada.nombre}</div>
            </c:otherwise>
			</c:choose>
		</div>
		<div class="row">
			<div class="col-md-5">
				<label class="control-label"> <spring:message
						code="label.sexo"></spring:message>
				</label>
			</div>
			<div class="col-md-6">${solicitud.personaInteresada.sexo.descripcion}</div>
		</div>
		<div class="row">
			<div class="col-md-5">
				<label class="control-label"> <spring:message
						code="label.fecha.nacimiento"></spring:message>
				</label>
			</div>
			<div class="col-md-6">${solicitud.personaInteresada.fechaNacimientoFormateada}</div>
		</div>
		<div class="row">
			<div class="col-md-5">
				<label class="control-label"> <spring:message	
						code="label.lugar.nacimiento" />
				</label>
			</div>
			<div class="col-md-6">${solicitud.personaInteresada.lugarNacimiento.nombre}</div>
		</div>
		<div class="row">
			<div class="col-md-5">
				<label class="control-label"> <spring:message
						code="label.nacionalidad" />
				</label>
			</div>
			<div class="col-md-6">${solicitud.personaInteresada.pais.nacionalidad}</div>
		</div>
	</div>
		
		
	<div class="col-md-6">
		<div class="row">
			<label class="control-label"> <spring:message
					code="label.documentos.probatorio" />
			</label>
		</div>
		<div class="row">
			<div class="col-md-4">
				<label class="control-label"> <spring:message
						code="label.entidad" />
				</label>
			</div>
			<div class="col-md-8">
				${solicitud.personaInteresada.actaNacimiento.municipio.entidadFederativa.nombre}
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<label class="control-label"> <spring:message
						code="label.municipio"></spring:message>
				</label>
			</div>
			<div class="col-md-8">${solicitud.personaInteresada.actaNacimiento.municipio.nombre}</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<label class="control-label"> <spring:message
						code="label.anio"></spring:message>
				</label>
			</div>
			<c:if test="${solicitud.personaInteresada.actaNacimiento.anio > 0}">
				<div class="col-md-6">
					${solicitud.personaInteresada.actaNacimiento.anio}
				</div>
			</c:if>
		</div>
		<div class="row">
			<div class="col-md-4">
				<label class="control-label"> <spring:message
					code="label.tomo"></spring:message>
				</label>
			</div>
			<c:if test="${solicitud.personaInteresada.actaNacimiento.tomo > 0}">
			    <div class="col-md-8">${solicitud.personaInteresada.actaNacimiento.tomo}</div>
			</c:if>
		</div>
		<div class="row">
			<div class="col-md-4">
				<label class="control-label"> <spring:message
						code="label.acta"></spring:message>
				</label>
			</div>
			<c:if test="${solicitud.personaInteresada.actaNacimiento.noActa > 0}">
				<div class="col-md-6">${solicitud.personaInteresada.actaNacimiento.noActa}</div>
			</c:if>
		</div>
		<div class="row">
			<div class="col-md-4">
				<label class="control-label"> <spring:message
						code="label.crip"></spring:message>
				</label>
			</div>
			<c:if test="${fisica.actaNacimiento.crip != null && not empty fisica.actaNacimiento.crip}">
				<div class="col-md-6">${solicitud.personaInteresada.actaNacimiento.crip}</div>
			</c:if>
		</div>
		<div class="row">
			<div class="col-md-4">
				<label class="control-label"> <spring:message
						code="label.libro"></spring:message>
				</label>
			</div>
			<c:if test="${solicitud.personaInteresada.actaNacimiento.noLibro > 0}">
				<div class="col-md-8">${solicitud.personaInteresada.actaNacimiento.noLibro}</div>
			</c:if>
		</div>
		<div class="row">
			<div class="col-md-4">
				<label class="control-label"> <spring:message
						code="label.foja"></spring:message>
				</label>
			</div>
			<c:if test="${solicitud.personaInteresada.actaNacimiento.noFoja > 0}">
				<div class="col-md-6">
					${solicitud.personaInteresada.actaNacimiento.noFoja}
				</div>
			</c:if>
		</div>
	</div>
	<div class="col-md-6">
		<c:if test="${not empty solicitud.personaInteresada.curpsHistoricas}">
		<div class="row">
			<div class="col-md-5">
				<label class="control-label"> <spring:message
						code="label.curps.historicas.confirmacion" />
				</label>
			</div>
		
		<c:forEach var="historica"  varStatus="contador"
							items="${solicitud.personaInteresada.curpsHistoricas}">
			<c:if test="${contador.index gt 0}">
				<div class="row">
				<div class="col-md-5"></div>			
			</c:if>
			<div class="col-md-6">${historica}</div>			
			</div>
		</c:forEach>
		</c:if>
	</div>

</div>
