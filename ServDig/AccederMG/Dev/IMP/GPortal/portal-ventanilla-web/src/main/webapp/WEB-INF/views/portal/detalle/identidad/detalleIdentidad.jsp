<%@ include file="../../../general/taglibs.jsp"%>

<div class="well">
	<div id="identidadWarningsContainer"></div>
	<c:if test="${moral.idPersona eq null }">
		<div class="row">
			<div class="col-md-4">
				<fieldset>
					<legend>DATOS GENERALES</legend>
					<address>
						<span><strong> Nombre </strong></span><br>
						<span> ${fisica.nombre} ${fisica.primerApellido } ${fisica.segundoApellido } </span><br>
						<span><strong> CURP </strong></span><br>
						<span> ${fisica.curp }</span><br>
						<span><strong> RFC </strong></span><br>
						<c:choose>
							<c:when test="${fisica.rfc eq null }">
								<span class="no-data">No tiene RFC</span>
							</c:when>
							<c:otherwise><span>${fisica.rfc }</span></c:otherwise>
						</c:choose>
						<br>
						<span><strong> Fecha de nacimiento </strong></span><br>
						<span> ${fisica.fechaNacimientoFormateada}</span><br>
						<span><strong> Lugar de nacimiento </strong></span><br>
						<span> ${fisica.lugarNacimiento.nombre}</span><br>
						<c:if test="${empty idPersonaTercero || idPersonaTercero eq '0'}">
							<c:choose>
								<c:when test="${NSS_RECUPERADO eq null }">
									&nbsp;
								</c:when>
								<c:otherwise>
									<span><strong> NSS </strong></span><br>
									<span>${NSS_RECUPERADO}</span>
								</c:otherwise>
							</c:choose>
						</c:if>
					</address>
				</fieldset>
			</div>
			
			<div class="col-md-4">
				<fieldset>
					<legend>DOMICILIO PARTICULAR</legend>
					<c:choose>
						<c:when test="${not empty domicilio }">
							<address>
								<strong> Vialidad </strong><br>
								${domicilio.vialidadPrimaria.nombre}<br>
								<strong> N&uacute;mero interior, N&uacute;mero exterior</strong><br>
								${domicilio.numExteriorAlf} ${domicilio.numExterior1},
								${domicilio.numInteriorAlf} ${domicilio.numInterior}<br>
								<strong> Asentamiento </strong><br>
								${domicilio.asentamiento.nombre}<br>
								<strong> Municipio </strong><br>
								${domicilio.asentamiento.localidad.municipio.nombre}<br>
								<strong> Estado </strong><br>
								${domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre}<br>
								<strong> C&oacute;digo postal </strong><br>
								C.P. ${domicilio.asentamiento.codigoPostal.codigoPostal}<br>
							</address>
						</c:when>
						<c:otherwise>
							<p>No cuenta con domicilio particular.</p>
						</c:otherwise>
					</c:choose>
				</fieldset>
			</div>
		
			<div class="col-md-4">
				<fieldset>
					<legend>MEDIOS DE CONTACTO</legend>
					<c:choose>
						<c:when test="${not empty mediosContacto }">
							<div>
								<c:forEach items="${mediosContacto}" var="medio" varStatus="indice">
									<address>
										<strong>${medio.tipoMedioContacto.descripcion}</strong> <br>
										${medio.desFormaContacto}<br>
									</address>
								</c:forEach>
							</div>
						</c:when>
						<c:otherwise>
							<p>No cuenta con medios de contacto particulares.</p>
						</c:otherwise>
					</c:choose>
				</fieldset>
			</div>
		</div>
		
		<div class="row">
			<div class="col-md-6">
				<jsp:include page="detalleDomicilioFiscal.jsp" />
			</div>
			<div class="col-md-6">
				<jsp:include page="detalleMediosFiscales.jsp" />
			</div>
		</div>
	</c:if>
		
	<c:if test="${fisica.idPersona eq null }">
		<div class="row">
			<div class="col-md-4">
				<fieldset>
					<legend>DATOS GENERALES</legend>
					<address>
						<strong> <span>${moral.razonSocial}</span></strong><br>
						<span><strong> RFC </strong></span><br>
						<c:choose>
							<c:when test="${moral.rfc eq null}">
								<span class="no-data">No tiene RFC</span>
							</c:when>
							<c:otherwise>
								<span>${moral.rfc}</span>
							</c:otherwise>
						</c:choose>
						<br>
						<span><strong> Tipo de Sociedad </strong></span><br>
						<span>${moral.tipoSociedad.descripcionAbreviada}</span><br>
						<span><strong> Escritura</strong></span><br>
						<c:choose>
							<c:when test="${moral.escrituraConstitutiva eq null}">
								<span class="no-data">No tiene Escritura </span>
							</c:when>
							<c:otherwise>
								<label>N&uacutemero de Escritura:</label>
									<span>${moral.escrituraConstitutiva.numEscritura}</span>
								<br>
								<label>N&uacutemero de Notar&iacutea o Correduria: </label>
									<span>${moral.escrituraConstitutiva.numNotaria}</span>
								<br>
								<label>Estado: </label>
									<span>${moral.escrituraConstitutiva.lugarExpedicion.entidadFederativa.nombre}</span>
								<br>
								<label>Municipio o delegaci&oacuten: </label>
									<span>${moral.escrituraConstitutiva.lugarExpedicion.nombre}</span>
								<br>
								
								<label>Fecha de Expedici&oacuten: </label>
								<c:if test="${moral.escrituraConstitutiva!=null && moral.escrituraConstitutiva.fechaExpedicion!=null}">
									<span><fmt:formatDate pattern="dd/MM/yyyy" value="${moral.escrituraConstitutiva.fechaExpedicion}"/> </span>
								</c:if>
								<br>
								<label>Folio Mercantil: </label>
									<span>${moral.escrituraConstitutiva.folioMercantil}</span>
								<br>
								<label>Secci&oacuten: </label>
									<span>${moral.escrituraConstitutiva.seccion}</span>
								<br>
								<label>Partida: </label>
									<span>${moral.escrituraConstitutiva.partida}</span>
								<br>
								<label>Volumen: </label>
									<span>${moral.escrituraConstitutiva.volumen}</span>
								<br>
								<label>Foja: </label>
									<span>${moral.escrituraConstitutiva.foja}</span>
								<br>
								
							</c:otherwise>
						</c:choose>
						<br>
						<span><strong>Sindicato</strong></span>
						<br>
						<c:choose>
							<c:when test="${moral.registroSindicato eq null}">
								<span class="no-data">No tiene sindicato</span>
							</c:when>
							<c:otherwise>
								<label>N° Referencia Registro:</label>
								<span>${moral.registroSindicato.numReferenciadocRegistro}</span>
								<br>
								<label>Fecha Registro: </label>
								<c:if test="${moral.registroSindicato!=null && moral.registroSindicato.fechaRegistro!=null}">
									<span>
										<fmt:formatDate pattern="dd/MM/yyyy" value="${moral.registroSindicato.fechaRegistro}" />
									</span>
								</c:if>
								<br>
								<label>Autoridad Laboral:</label>
								<span>
									<font style="text-transform: uppercase;"> ${moral.registroSindicato.autoridadLaboral}</font>
								</span>
	
							</c:otherwise>
						</c:choose>
						<br>
	
						<span><strong> Fecha de alta </strong></span><br>
						<span>${moral.fechaCreacionFormateada}</span><br>
					</address>
				</fieldset>
			</div>
			<div class="col-md-4">
				<jsp:include page="detalleDomicilioFiscal.jsp" />
			</div>
			<div class="col-md-4">
				<jsp:include page="detalleMediosFiscales.jsp" />
			</div>
		</div>
	</c:if>
</div>