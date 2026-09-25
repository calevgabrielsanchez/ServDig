<div class="contenedor">
	<h3>
		<spring:message code="label.domicilio.particular" />
	</h3>
	<hr class="red" style="margin-bottom: 20px;"/>
</div>
<br/>

<div class="row">

	<div class="col-md-3">
		<label class="control-label">
			<spring:message code="label.codigoPostal" />
		</label>
	</div>
	<div class="col-md-3">
		${solicitud.personaInteresada.domicilios[0].codigoPostal.codigoPostal}
	</div>
				
	<div class="col-md-3">
		<label class="control-label">
			<spring:message code="label.asentamiento" />
		</label>
	</div>
	<div class="col-md-3">
		${solicitud.personaInteresada.domicilios[0].asentamiento.nombre}
	</div>
				
</div>
			
<div class="row">
	<div class="col-md-3">
		<label class="control-label"> 
			<spring:message code="label.domicilio.particular.calle" />
		</label>
	</div>
	<div class="col-md-3" style="word-wrap: break-word;"> 
		${solicitud.personaInteresada.domicilios[0].calle}
	</div>
				
	<div class="col-md-3">
		<label class="control-label"> 
			<spring:message code="label.domicilio.particular.delegacion.municipio" />
		</label>
	</div>
	<div class="col-md-3"> 
		${solicitud.personaInteresada.domicilios[0].asentamiento.localidad.municipio.nombre}
	</div>
</div>
			
<div class="row">
	<div class="col-md-3">
		<label class="control-label"> 
			<spring:message code="label.registrar.domicilio.particular.numero.int.ext" />
		</label>
	</div>
	<div class="col-md-3">
	<c:choose>
		<c:when test="${not empty solicitud.personaInteresada.domicilios[0].numInteriorAlf}">
		${solicitud.personaInteresada.domicilios[0].numExteriorAlf}/${solicitud.personaInteresada.domicilios[0].numInteriorAlf}</c:when>
		<c:otherwise>
		${solicitud.personaInteresada.domicilios[0].numExteriorAlf} </c:otherwise>
	</c:choose>
	</div>
	
	<div class="col-md-3">
		<label class="control-label"> 
			<spring:message code="label.registrar.domicilio.particular.entidad.federativa" />
		</label>
	</div>
	<div class="col-md-3"> 
		${solicitud.personaInteresada.domicilios[0].asentamiento.localidad.municipio.entidadFederativa.nombre}
	</div>
</div>
			
<div class="row">
	<div class="col-md-3">
		<label class="control-label"> 
			<spring:message code="label.subdelegacion" />
		</label>
	</div>
	<div class="col-md-3"> 
		${solicitud.subdelegacion.clave}-${solicitud.subdelegacion.descripcion}
	</div>
</div>
<br/>
<div class="contenedor">
<h3> 
	<spring:message code="label.motivo.aclaracion" />
</h3>	
<hr class="red" style="margin-bottom: 20px;"/>
</div>
<br/>
<div class="row">
		<c:if test="${(motivosIMSS != null) &&(not empty motivosIMSS)}">
			<div class="col-md-3">
				<div class="row">
					<label class="control-label"> 
						<spring:message code="label.motivo.aclaracion.imss" />
					</label>
				</div>
				<div>
					<c:forEach var="motivosImss" varStatus="contador" items="${motivosIMSS}">
						<p>${motivosImss}</p>
					</c:forEach>
				</div>
			</div>
		</c:if>
		
		<c:if test="${(motivosINFONAVIT != null) &&(not empty motivosINFONAVIT)}">
			<div class="col-md-4">
				<div class="row">
					<label class="control-label"> 
						<spring:message code="label.motivo.aclaracion.infonavit" />
					</label>
				</div>
				<div>
					<c:forEach var="motivosInfonavit" varStatus="contador" items="${motivosINFONAVIT}">
						<p>${motivosInfonavit}</p>
					</c:forEach>
					<c:if test="${not empty domicilioAclaracion.motivoAclaracionVO.creditoDescontado}">
						<label class="control-label"> 
							<spring:message code="label.motivo.aclaracion.infonavit.numero.credito" />
						</label>
						<p>${domicilioAclaracion.motivoAclaracionVO.creditoDescontado}</p>
					</c:if>
				</div>
			</div>
		</c:if>
		
		<c:if test="${(motivosAFORE != null) && (not empty motivosAFORE)}">
			<div class="col-md-5">
				<div class="row">
					<label class="control-label"> 
						<spring:message code="label.motivo.aclaracion.afore" />
					</label>
				</div>
				<div>
					<c:forEach 	var="motivosAfore" varStatus="contador" items="${motivosAFORE}">
						<p>${motivosAfore}</p>
					</c:forEach>
				</div>
			</div>
		</c:if>
			
		<c:if test="${not empty domicilioAclaracion.motivoAclaracionVO.otro}">
			<div class="col-md-5">
				<div class="row">
					<label class="control-label">
						<spring:message code="label.registrar.motivo.aclaracion.otro" />
					</label>
					</br>
					<p style="max-width:500px; word-wrap:break-word;">${domicilioAclaracion.motivoAclaracionVO.especificacion}</p>
				</div>
			</div>
		</c:if>
</div>